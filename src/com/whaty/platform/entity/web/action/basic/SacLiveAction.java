package com.whaty.platform.entity.web.action.basic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.CellType;
import jxl.LabelCell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.whaty.platform.GlobalProperties;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.SacLive;
import com.whaty.platform.entity.bean.WeHistory;
import com.whaty.platform.entity.bean.WeQA;
import com.whaty.platform.entity.bean.WeRollcall;
import com.whaty.platform.entity.bean.WeRollcallUser;
import com.whaty.platform.entity.bean.WeSurveyQuestion;
import com.whaty.platform.entity.bean.WeSurveyResponse;
import com.whaty.platform.entity.bean.WeSurveySurveylist;
import com.whaty.platform.entity.bean.WeVoteOption;
import com.whaty.platform.entity.bean.WeVoteQuestion;
import com.whaty.platform.entity.bean.WeVoteResult;
import com.whaty.platform.entity.bean.WeVoteVotelist;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.basicInfo.PeTchCourseService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

/**
 * 在线直播action
 * 
 * @version 创建时间：2014年6月4日
 * @author Lee
 * @return
 * @throws PlatformException
 *             类说明
 */
public class SacLiveAction extends MyBaseAction {

	private PeTchCourseService peTchCourseService;

	/**
	 * 导入学员页面
	 * 
	 * @return
	 */
	public String toImpLiveStu() {
		String id = "";
		try {
			id = ServletActionContext.getRequest().getParameter("id");
			if (this.isEnd(id)) {
				this.setMsg("直播已结束报名，不允许导入学员");
				this.setTogo("back");
				return "msg";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServletActionContext.getRequest().getSession().setAttribute("param_id", id);
		return "impLiveStu";
	}

	/**
	 * 直播是否结束报名
	 * 
	 * @param id
	 *            直播ID
	 * @return
	 */
	private Boolean isEnd(String id) {
		String sql = "SELECT 1 FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "' AND (SIGN_ENDDATE - SYSDATE) >= 0";
		try {
			List list = this.getGeneralService().getBySQL(sql);
			if (list != null && list.size() > 0) {
				return true;
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return false;
	}

	private String param_id;// 导入参数(直播ID)

	/**
	 * 解析Excel导入学员
	 * 
	 * @return
	 * @throws EntityException
	 */
	public String impLiveStudents() throws EntityException {
		this.param_id = ServletActionContext.getRequest().getSession().getAttribute("param_id") + "";
		if (isEnd(param_id)) {
			this.setMsg("直播报名已结束，不允许添加学员");
			this.setTogo("back");
			return "m_msg";
		}
		Map map = new HashMap();
		this.setDoLog(false);// 不记录日志
		if (null == param_id || "".equals(param_id) || "null".equalsIgnoreCase(param_id)) {
			this.setMsg("导入失败！请联系管理员！");// 主键参数没接收到
			this.setTogo("back");
			return "m_msg";
		} else {
			StringBuffer msg = new StringBuffer();
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);// 记录操作日志时使用
			Workbook work = null;
			try {
				File file = this.get_upload();
				work = Workbook.getWorkbook(file);
			} catch (Exception e) {
				msg.append("Excel表格读取异常！导入学员失败！");// 解析Excel异常
				this.setMsg(msg.toString());
				this.setTogo("back");
				return "m_msg";
			}
			Sheet sheet = work.getSheet(0);
			int rows = sheet.getRows();
			if (rows < 2) {
				msg.append("Excel表格异常，导入学员失败！请检查Excel文件！");// 无数据
				this.setMsg(msg.toString());
				this.setTogo("back");
				return "m_msg";
			}
			String loginId = "";
			String lower_content = "";
			int successCount = 0;
			Map<Integer, String> cel_data = new HashMap<Integer, String>();
			for (int i = 1; i < rows; i++) {
				if (rows >= 1001) {
					break;
				}
				try {
					Cell cell = sheet.getCell(0, i);
					if (cell.getType() == CellType.LABEL) {
						LabelCell lc = (LabelCell) cell;
						loginId = lc.getString().trim();
					} else {
						loginId = sheet.getCell(0, i).getContents().trim();
					}
					lower_content = loginId.toLowerCase();
					if (cel_data.containsValue(lower_content)) {
						msg.append("第" + (i + 1) + "行数据无效，" + "学员：" + loginId + " 重复出现！<br />");
					}
				} catch (Exception e) {
					e.printStackTrace();
					msg.append("数据读取异常，导入学员失败！请检查Excel文件！");
					this.setMsg(msg.toString());
					this.setTogo("back");
					return "m_msg";
				}
				StringBuffer sbLoginIds = new StringBuffer();
				if (loginId == null || loginId.trim().length() == 0) {
					continue;
				} else {
					sbLoginIds.append(loginId.toUpperCase() + ",");
				}
				String loginIdString = sbLoginIds.substring(0, sbLoginIds.length() - 1);
				// 检查学员是否存在
				String sqlStuString = "SELECT PBS.ID FROM SSO_USER SU,PE_BZZ_STUDENT PBS WHERE SU.ID = PBS.FK_SSO_USER_ID AND UPPER(SU.LOGIN_ID) = '" + loginId.toUpperCase() + "'";
				List<PeBzzStudent> stuList = (List<PeBzzStudent>) this.getGeneralService().getBySQL(sqlStuString);
				// 学员不存在
				if (stuList == null || stuList.size() == 0) {
					msg.append("第" + (i + 1) + "行，学员" + loginId + "不存在！执行跳过。<br />");
				} else {// 学员存在则执行报名操作
					String param_stu_ids = "";
					for (Object o : stuList) {
						if (null != o)
							param_stu_ids += ",'" + o.toString().trim() + "'";
					}
					param_stu_ids = param_stu_ids.substring(1);
					// 构造验证课程是否已选的SQL
					StringBuffer sbCheckElectiveBackSql = new StringBuffer();
					sbCheckElectiveBackSql.append(" SELECT PBS.REG_NO ");
					sbCheckElectiveBackSql.append(" FROM PR_BZZ_TCH_STU_ELECTIVE_BACK PBTSE, ");
					sbCheckElectiveBackSql.append(" PR_BZZ_TCH_OPENCOURSE        PBTO, ");
					sbCheckElectiveBackSql.append(" PE_BZZ_TCH_COURSE            PBTC, ");
					sbCheckElectiveBackSql.append(" PE_BZZ_STUDENT               PBS ");
					sbCheckElectiveBackSql.append(" WHERE PBTSE.FK_STU_ID = PBS.ID ");
					sbCheckElectiveBackSql.append(" AND PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID ");
					sbCheckElectiveBackSql.append(" AND PBTO.FK_COURSE_ID = PBTC.ID ");
					sbCheckElectiveBackSql.append(" AND PBS.ID IN (" + param_stu_ids + ") ");
					sbCheckElectiveBackSql.append(" AND PBTC.CODE = (SELECT CODE FROM PE_BZZ_TCH_COURSE WHERE ID = '" + this.param_id + "') ");

					StringBuffer sbCheckElectiveSql = new StringBuffer();
					sbCheckElectiveSql.append(" SELECT PBS.REG_NO ");
					sbCheckElectiveSql.append(" FROM PR_BZZ_TCH_STU_ELECTIVE PBTSE, ");
					sbCheckElectiveSql.append(" PR_BZZ_TCH_OPENCOURSE        PBTO, ");
					sbCheckElectiveSql.append(" PE_BZZ_TCH_COURSE            PBTC, ");
					sbCheckElectiveSql.append(" PE_BZZ_STUDENT               PBS ");
					sbCheckElectiveSql.append(" WHERE PBTSE.FK_STU_ID = PBS.ID ");
					sbCheckElectiveSql.append(" AND PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID ");
					sbCheckElectiveSql.append(" AND PBTO.FK_COURSE_ID = PBTC.ID ");
					sbCheckElectiveSql.append(" AND PBS.ID IN (" + param_stu_ids + ") ");
					sbCheckElectiveSql.append(" AND PBTC.CODE = (SELECT CODE FROM PE_BZZ_TCH_COURSE WHERE ID = '" + this.param_id + "') ");

					List<Object[]> eleList = this.getGeneralService().getGeneralDao().getBySQL(sbCheckElectiveSql.toString());
					List<Object[]> eleBackList = this.getGeneralService().getGeneralDao().getBySQL(sbCheckElectiveBackSql.toString());

					if ((null != eleList || null != eleBackList) && (eleList.size() > 0 || eleBackList.size() > 0)) {
						for (Object o : eleList)
							msg.append("学员" + o + "已报名，请从报名表中删除后重试！<br />");
						for (Object o : eleBackList)
							msg.append("学员" + o + "已报名，请从报名表中删除后重试！<br />");
						break;
					}
					String sql = "SELECT * FROM PR_BZZ_TCH_OPENCOURSE WHERE FK_COURSE_ID = (SELECT ID FROM PE_BZZ_TCH_COURSE WHERE CODE = (SELECT CODE FROM PE_BZZ_TCH_COURSE WHERE ID = '" + param_id
							+ "'))";
					List<PrBzzTchOpencourse> opList = (List<PrBzzTchOpencourse>) this.getGeneralService().getBySQL(sql);
					PrBzzTchOpencourse pbtOpencourse = opList.get(0);
					for (Object o : stuList) {
						DetachedCriteria dc_student = DetachedCriteria.forClass(PeBzzStudent.class);
						dc_student.add(Restrictions.eq("id", o));
						PeBzzStudent peBzzStudent = this.getGeneralService().getStudentInfo(dc_student);
						// 导入报名表
						PrBzzTchStuElectiveBack eleBack = new PrBzzTchStuElectiveBack();
						eleBack.setPrBzzTchOpencourse(pbtOpencourse);
						eleBack.setPeBzzStudent(peBzzStudent);
						this.getGeneralService().getGeneralDao().save(eleBack);
						successCount++;// 记录成功数量
					}
				}
			}
			String html = "<table width='100%' border='0'>";
			html += "<tr><td colspan='4' align='left;'>成功导入" + successCount + "条记录！</td></tr>";
			if (msg.toString().indexOf("<br />") > -1) {
				String[] err_arr = msg.toString().split("<br />");
				if (err_arr.length > 0) {
					html += "<tr><td colspan='4'>错误信息如下：</td></tr><tr><td width='10%'></td>";
					for (int i = 0; i < err_arr.length; i++) {
						if (i % 3 == 0 && i != 0) {
							html += "</tr><tr><td width='10%'></td>";
						}
						html += "<td width='30%' style='text-align:left;'>" + err_arr[i] + "</td>";
					}
					html += "</tr></table>";
				}
			}
			this.setMsg(html);
		}
		this.setTogo("/entity/basic/sacLive.action");
		return "m_msg";
	}

	/**
	 * 添加学员
	 * 
	 * @return
	 */
	public String toAddLiveStu() {
		return "addLiveStu";
	}

	/**
	 * 查看学员
	 * 
	 * @return
	 */
	public String toViewStudents() {
		return "viewStudents";
	}

	/**
	 * 重写框架方法：更新字段
	 * 
	 * @author Lee
	 * @return
	 */
	@SuppressWarnings( { "unchecked", "unchecked", "unchecked" })
	public Map updateColumn() {
		Map map = new HashMap();
		String action = this.getColumn();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			int success = 0;
			for (int i = 0; i < ids.length; i++) {
				PeBzzTchCourse pbTchCourse = new PeBzzTchCourse();
				// 课程编号
				pbTchCourse.setCode(this.getBean().getCode());
				// 课程名称
				pbTchCourse.setName(this.getBean().getName());
				// 实际学时数
				pbTchCourse.setTime(this.getBean().getSjTime());
				// 主讲人
				pbTchCourse.setTeacher(this.getBean().getTeacher());
				// 价格
				pbTchCourse.setPrice(this.getBean().getPrice());
				// 建议学习人群
				pbTchCourse.setEnumConstByFlagSuggest(this.getBean().getEnumConstByFlagSuggestSet());
				// 课程性质
				pbTchCourse.setEnumConstByFlagCourseType(this.getBean().getEnumConstByFlagCourseType());
				// 业务分类
				pbTchCourse.setEnumConstByFlagCourseCategory(this.getBean().getEnumConstByFlagCourseCategory());
				// 大纲分类
				pbTchCourse.setEnumConstByFlagCourseItemType(this.getBean().getEnumConstByFlagCourseItemType());
				// 内容属性
				pbTchCourse.setEnumConstByFlagContentProperty(this.getBean().getEnumConstByFlagContentProperty());
				// 建议学习人群
				pbTchCourse.setSuggestion(this.getBean().getSuggestion());
				// 课程图片
				pbTchCourse.setPhotoLink(this.getBean().getPhotoLink());
				// 主讲人照片
				pbTchCourse.setTeaImg(this.getBean().getTeaImg());
				/* 是否有效-否 */
				EnumConst ec = this.getEnumConstService().getByNamespaceCode("FlagIsvalid", "0");
				pbTchCourse.setEnumConstByFlagIsvalid(ec);
				/* 是否下线-否 */
				EnumConst ec4 = this.getEnumConstService().getByNamespaceCode("FlagOffline", "0");
				pbTchCourse.setEnumConstByFlagOffline(ec4);
				/* 课程审核状态-未申请 */
				EnumConst ec3 = this.getEnumConstService().getByNamespaceCode("FlagCheckStatus", "2");
				pbTchCourse.setEnumConstByFlagCheckStatus(ec3);
				/* 课程图片状态 */
				String flagImgcode = "1";
				if (null == this.getBean().getPhotoLink() || "".equals(this.getBean().getPhotoLink()))
					flagImgcode = "0";
				EnumConst ecFlagImg = this.getEnumConstService().getByNamespaceCode("FlagImg", flagImgcode);
				pbTchCourse.setEnumConstFlagImg(ecFlagImg);
				/* 主讲人照片状态 */
				String flagTeaImgCode = "1";
				if (null == this.getBean().getTeaImg() || "".equals(this.getBean().getTeaImg()))
					flagTeaImgCode = "0";
				EnumConst ecFlagTeaimg = this.getEnumConstService().getByNamespaceCode("FlagTeaimg", flagTeaImgCode);
				pbTchCourse.setEnumConstFlagTeaimg(ecFlagTeaimg);
				/* 课程所属区域-在线直播课程 */
				EnumConst ecFlagCoursearea = this.getEnumConstService().getByNamespaceCode("FlagCoursearea", "0");
				pbTchCourse.setEnumConstByFlagCoursearea(ecFlagCoursearea);
				/* 创建日期 */
				pbTchCourse.setCreateDate(this.getBean().getCreateDate());
				/* 获取默认专项 */
				DetachedCriteria dBatch = DetachedCriteria.forClass(PeBzzBatch.class);
				dBatch.createAlias("enumConstByFlagBatchType", "enumConstByFlagBatchType");
				dBatch.add(Restrictions.eq("enumConstByFlagBatchType.code", "1"));
				dBatch.add(Restrictions.eq("enumConstByFlagBatchType.namespace", "FlagBatchType"));
				PeBzzBatch peBzzBatch = null;
				try {
					peBzzBatch = (PeBzzBatch) this.getGeneralService().getDetachList(dBatch).get(0);
				} catch (EntityException e1) {
					e1.printStackTrace();
				}
				PrBzzTchOpencourse prBzzTchOpencourse = new PrBzzTchOpencourse();
				prBzzTchOpencourse.setPeBzzTchCourse(pbTchCourse);

				prBzzTchOpencourse.setPeBzzBatch(peBzzBatch);
				try {
					this.getPeTchCourseService().add(prBzzTchOpencourse).getPeBzzTchCourse();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (action.equals("getDataSource")) {
//					System.out.println("开始采集直播数据：" + ids[i]);
					success = this.saveDataSource(ids[i]);
					if (success == 1) {
						map.clear();
						map.put("success", "false");
						map.put("info", "直播数据更新失败");
					} else if (success > 1) {
						map.clear();
						map.put("success", "false");
						map.put("info", "直播数据采集失败ERR_CODE：" + success);
					} else {
						map.clear();
						map.put("success", "true");
						map.put("info", "操作成功");
					}
				}
				if (action.equals("cleanDataSource")) {
					success = this.cleanDataSource(ids[i]);
					if (success == 0) {
						map.clear();
						map.put("success", "true");
						map.put("info", "直播数据清除成功");
					} else {
						map.clear();
						map.put("success", "false");
						map.put("info", "直播数据清除失败");
					}
				}
			}
		} else {
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
			return map;
		}
		return map;
	}

	public String getDataSource() {
		String id = ServletActionContext.getRequest().getParameter("id");
		// 检查是否已经采集过数据
		String hasGetDataSql = "SELECT 1 FROM PE_BZZ_TCH_COURSE WHERE ID = '' AND FLAG_LIVEDATA = 'FlagLiveData1'";
		List hasGetDataList;
		try {
			hasGetDataList = this.getGeneralService().getBySQL(hasGetDataSql);
			if (null != hasGetDataList && hasGetDataList.size() > 0) {
				this.setMsg("请勿重复采集数据！");
				this.setTogo("back");
				return "msg";
			}
		} catch (EntityException e1) {
			e1.printStackTrace();
			this.setMsg("请勿重复采集数据！");
			this.setTogo("back");
			return "msg";
		}
		int success = 0;
		System.out.println("开始采集直播数据：" + id);
		success = this.getGeneralService().saveDataSource(id);
		// success = this.saveDataSource(id);
		if (success == 1) {
			this.setMsg("直播数据更新失败");
		} else if (success > 1) {
			this.setMsg("直播数据采集失败ERR_CODE：" + success);
		} else {
			try {
				String webcastIdSql = "SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "'";
				List webcastIdList = this.getGeneralService().getBySQL(webcastIdSql);
				Object webcastId = webcastIdList.get(0);
				/* 全部采集完毕并且没报错则更新直播表中该信息的采集状态为已采集 */
				String upt = "UPDATE PE_BZZ_TCH_COURSE SET FLAG_LIVEDATA = 'FlagLiveData1' WHERE ID = '" + id + "'";
				String successCountSql = "SELECT COUNT(DISTINCT WH.WH_UID) FROM WE_HISTORY WH INNER JOIN LIVE_SEQ_STU LSS ON WH.WH_WEBCASTID = LSS.LIVE_ID AND WH.WH_UID = LSS.ID AND WH.WH_WEBCASTID = '" + webcastId + "' GROUP BY WH.WH_WEBCASTID";
				List successCountList = this.getGeneralService().getBySQL(successCountSql);
				String successCount = "0";
				if (null != successCountList && successCountList.size() > 0)
					successCount = successCountList.get(0) + "";
				if ("0".equals(successCount)) {
					this.setMsg("采集 0 条数据，请联系管理员");
					// 如果采集0条数据则执行清空方法，防止不完整数据
					this.getGeneralService().cleanDataSource(id);
				} else {
					this.setMsg("成功采集" + successCount + "名学员数据");
					this.getGeneralService().executeBySQL(upt);
				}
			} catch (Exception e) {
				e.printStackTrace();
				this.setMsg("直播数据更新失败");
			}
		}
		this.setTogo("back");
		return "msg";
	}

	public String cleanDataSource() {
		String id = ServletActionContext.getRequest().getParameter("id");
		int success = 0;
		success = this.getGeneralService().cleanDataSource(id);
		// success = this.cleanDataSource(id);
		if (success == 0) {
			this.setMsg("直播数据清除成功");
		} else {
			this.setMsg("直播数据清除失败");
		}
		this.setTogo("back");
		return "msg";
	}

	/**
	 * 采集并保存数据
	 * 
	 * @param id
	 * @return
	 */
	private int saveDataSource(String id) {
		String sql = "SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "'";
		List result = new ArrayList();
		try {
			result = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
			return 61;
		}
		String webcastId = "";
		if (result == null || result.size() == 0) {
			return 61;
		} else {
			webcastId = result.get(0).toString();
		}
		String loginName = GlobalProperties.getProperty("sys.zhibo.user");
		String password = GlobalProperties.getProperty("sys.zhibo.pwd");
		String service_host = GlobalProperties.getProperty("sys.zhibo.url");
		

//		//测试直播地址
//		//-start
//		loginName = "admin@sactest.com";
//		password = "sactest";
//		service_host = "http://sactest.gensee.com/integration/site/webcast/";
//		//-end
//		
		
		/* 导出直播用户访问历史记录 */
		String service_method01 = GlobalProperties.getProperty("sys.zhibo.service_method01");
		/* 导出直播投票结果 */
		String service_method02 = GlobalProperties.getProperty("sys.zhibo.service_method02");
		/* 导出直播问卷结果 */
		String service_method03 = GlobalProperties.getProperty("sys.zhibo.service_method03");
		/* 导出直播用户提问数据 */
		String service_method04 = GlobalProperties.getProperty("sys.zhibo.service_method04");
		/* 导出直播点名签到数据 */
		String service_method05 = GlobalProperties.getProperty("sys.zhibo.service_method05");
		String service_params = "?webcastId=" + webcastId + "&loginName=" + loginName + "&password=" + password;

		Map dataMap1 = getReturnMap(service_host, service_method01, service_params);
		Map dataMap2 = getReturnMap(service_host, service_method02, service_params);
		Map dataMap3 = getReturnMap(service_host, service_method03, service_params);
		Map dataMap4 = getReturnMap(service_host, service_method04, service_params);
		Map dataMap5 = getReturnMap(service_host, service_method05, service_params);
		if (dataMap1.containsKey("error"))
			return 11;
		if (dataMap2.containsKey("error"))
			return 21;
		if (dataMap3.containsKey("error"))
			return 31;
		if (dataMap4.containsKey("error"))
			return 41;
		if (dataMap5.containsKey("error"))
			return 51;
		return this.saveDatatoDB(id, webcastId, dataMap1, dataMap2, dataMap3, dataMap4, dataMap5);
	}

	public int cleanDataSource(String id) {
		try {
			/* 直播用户访问历史记录表 */
			String sql = "DELETE FROM WE_HISTORY WHERE WH_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "')";
			this.getGeneralService().executeBySQL(sql);
			/* 直播用户提问数据表 */
			sql = "DELETE FROM WE_QA WHERE WQ_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "')";
			this.getGeneralService().executeBySQL(sql);
			/* 直播用户点名签到明细表_USERS */
			sql = "DELETE FROM WE_ROLLCALL_USERS WHERE WRU_WR_ID IN (SELECT WR_ID FROM WE_ROLLCALL WHERE WR_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "'))";
			this.getGeneralService().executeBySQL(sql);
			/* 直播用户点名签到表 */
			sql = "DELETE FROM WE_ROLLCALL WHERE WR_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "')";
			this.getGeneralService().executeBySQL(sql);
			/* 直播用户问卷结果表_回答 */
			sql = "DELETE FROM WE_SURVEY_RESPONSE WHERE WSR_WSQ_ID IN (SELECT WSQ_ID FROM WE_SURVEY_QUESTIONS WHERE WSQ_WSS_ID IN (SELECT WSS_ID FROM WE_SURVEY_SURVEYLIST WHERE WSS_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '"
					+ id + "')))";
			this.getGeneralService().executeBySQL(sql);
			/* 直播用户问卷结果表_问题 */
			sql = "DELETE FROM WE_SURVEY_QUESTIONS WHERE WSQ_WSS_ID IN (SELECT WSS_ID FROM WE_SURVEY_SURVEYLIST WHERE WSS_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "'))";
			this.getGeneralService().executeBySQL(sql);
			/* 直播用户问卷结果表_主题 */
			sql = "DELETE FROM WE_SURVEY_SURVEYLIST WHERE WSS_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "')";
			this.getGeneralService().executeBySQL(sql);
			/* 直播投票结果表_问题的回答 */
			sql = "DELETE FROM WE_VOTE_RESULTS WHERE WVR_WVO_ID IN (SELECT WVO_ID FROM WE_VOTE_OPTIONS WHERE WVO_WVQ_ID IN (SELECT WVQ_ID FROM WE_VOTE_QUESTIONS WHERE WVQ_WVV_ID IN (SELECT WVV_ID FROM WE_VOTE_VOTELIST WHERE WVV_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '"
					+ id + "'))))";
			this.getGeneralService().executeBySQL(sql);
			/* 直播投票结果表_问题的值 */
			sql = "DELETE FROM WE_VOTE_OPTIONS WHERE WVO_WVQ_ID IN (SELECT WVQ_ID FROM WE_VOTE_QUESTIONS WHERE WVQ_WVV_ID IN (SELECT WVV_ID FROM WE_VOTE_VOTELIST WHERE WVV_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '"
					+ id + "')))";
			this.getGeneralService().executeBySQL(sql);
			/* 直播投票结果表_问题内容 */
			sql = "DELETE FROM WE_VOTE_QUESTIONS WHERE WVQ_WVV_ID IN (SELECT WVV_ID FROM WE_VOTE_VOTELIST WHERE WVV_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "'))";
			this.getGeneralService().executeBySQL(sql);
			/* 直播投票结果表_投票的主题 */
			sql = "DELETE FROM WE_VOTE_VOTELIST WHERE WVV_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "')";
			this.getGeneralService().executeBySQL(sql);
			/* 在线直播采集数据整理表 */
			sql = "DELETE FROM SAC_LIVE_GETDATA WHERE LIVE_ID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "')";
			this.getGeneralService().executeBySQL(sql);
			/* 修改直播信息表中采集数据状态 */
			sql = "UPDATE PE_BZZ_TCH_COURSE SET FLAG_LIVEDATA = 'FlagLiveData0' WHERE ID = '" + id + "'";
			this.getGeneralService().executeBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	/**
	 * 采集返回数据
	 * 
	 * @param service_host
	 * @param service_method
	 * @param service_params
	 * @return
	 */
	private Map getReturnMap(String service_host, String service_method, String service_params) {
		try {
			URL url = new URL(service_host + service_method + service_params);
			URLConnection connection = url.openConnection();
			if (null != connection) {
				InputStream in = connection.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf8"));
				String temp = "";
				String s = "";
				while ((temp = br.readLine()) != null) {
					s = s + temp;
				}
				StringBuffer out = new StringBuffer(s);

				byte[] b = new byte[4096];
				for (int n; (n = in.read(b)) != -1;) {
					out.append(new String(b, 0, n));
				}

				String service_return = out.toString();
				JSONObject jso = JSONObject.fromObject(service_return);

				Iterator keyIter = jso.keys();
				String key;
				Object value;
				Map valueMap = new HashMap();
				while (keyIter.hasNext()) {
					key = (String) keyIter.next();
					value = jso.get(key);
					valueMap.put(key, value);
				}
				return valueMap;
			} else {
				System.out.println("获取失败!");
				return (Map) (new HashMap().put("error", "获取失败!"));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return (Map) (new HashMap().put("error", e));
		} catch (IOException e) {
			e.printStackTrace();
			return (Map) (new HashMap().put("error", e));
		}
	}

	/**
	 * 保存数据到数据库
	 * 
	 * @param map1直播用户访问历史记录
	 * @param map2直播投票结果
	 * @param map3直播问卷结果
	 * @param map4直播用户提问数据
	 * @param map5直播点名签到数据
	 * @return
	 */
	private int saveDatatoDB(String sacLiveId, String webcastId, Map map1, Map map2, Map map3, Map map4, Map map5) {
		/* 使用事务 保证数据的完整性 */
		/* 直播用户访问历史记录 */
		JSONArray json_list01 = (JSONArray) map1.get("list");
		if (json_list01 != null) {
			for (int i = 0; i < json_list01.size(); i++) {
				WeHistory wh = new WeHistory();
				/* 学员LOGIN_ID-昵称 */
				String nickname = ((JSONObject) json_list01.get(i)).containsKey("nickname") ? ((JSONObject) json_list01.get(i)).getString("nickname") : "";
				wh.setWhNickname(nickname);
				/* 加入时间 */
				String joinTime = ((JSONObject) json_list01.get(i)).containsKey("joinTime") ? ((JSONObject) json_list01.get(i)).getString("joinTime") : "";
				wh.setWhJointime(joinTime);
				/* 离开时间 */
				String leaveTime = ((JSONObject) json_list01.get(i)).containsKey("leaveTime") ? ((JSONObject) json_list01.get(i)).getString("leaveTime") : "";
				wh.setWhLeavetime(leaveTime);
				/* IP地址 */
				String ip = ((JSONObject) json_list01.get(i)).containsKey("ip") ? ((JSONObject) json_list01.get(i)).getString("ip") : "";
				wh.setWhIp(ip);
				/* 用户ID */
				String uid = ((JSONObject) json_list01.get(i)).containsKey("uid") ? ((JSONObject) json_list01.get(i)).getString("uid") : "";
				wh.setWhUid(uid);
				/* 用户名称 */
				String nick_name = ((JSONObject) json_list01.get(i)).containsKey("nickname") ? ((JSONObject) json_list01.get(i)).getString("nickname") : "";
				wh.setWhName(nick_name);
				/* 用户邮件-返回数据不包含email */
				// String email =
				// ((JSONObject)json_list01.get(i)).getString("email");
				// wh.setWhEmail(email);
				/* 公司名称-返回数据不包含email */
				// String company = ((JSONObject)
				// json_list01.get(i)).getString("company");
				// wh.setWhCompany(company);
				/* 区域-文档中为are实际为area */
				String are = ((JSONObject) json_list01.get(i)).containsKey("area") ? ((JSONObject) json_list01.get(i)).getString("area") : "";
				wh.setWhAre(are);
				/* 直播ID */
				wh.setWhWebcastid(webcastId);
				/* 采集日期 */
				wh.setWhDate(new Date());
				try {
					this.getGeneralService().save(wh);
				} catch (EntityException e) {
					e.printStackTrace();
					return 11;
				}
			}
		}
		/* 直播投票结果 */
		JSONArray json_list02 = (JSONArray) map2.get("voteList");
		if (json_list02 != null) {
			for (int i = 0; i < json_list02.size(); i++) {
				WeVoteVotelist wvvl = new WeVoteVotelist();
				/* 投票的主题 */
				String subject = ((JSONObject) json_list02.get(i)).containsKey("subject") ? ((JSONObject) json_list02.get(i)).getString("subject") : "";
				wvvl.setWvvSubject(subject);
				/* 直播ID */
				wvvl.setWvvWebcastid(webcastId);
				/* 采集日期 */
				wvvl.setWvvDate(new Date());
				try {
					wvvl = (WeVoteVotelist) this.getGeneralService().save(wvvl);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return 21;
				}
				/* 问题内容 */
				JSONArray json_list0201 = ((JSONObject) json_list02.get(i)).containsKey("questions") ? (JSONArray) ((JSONObject) json_list02.get(i)).get("questions") : null;
				if (json_list0201 != null) {
					for (int ii = 0; ii < json_list0201.size(); ii++) {
						WeVoteQuestion wvq = new WeVoteQuestion();
						/* 问题内容 */
						String content = ((JSONObject) json_list02.get(ii)).containsKey("content") ? ((JSONObject) json_list02.get(ii)).getString("content") : "";
						wvq.setWvqContent(content);
						/* 上一步save失败导致没有ID */
						if (wvvl.getWvvId() == null || "".equals(wvvl.getWvvId())) {
							return 21;
						}
						/* 问题ID */
						wvq.setWvqWvvid(wvvl.getWvvId());
						try {
							wvq = (WeVoteQuestion) this.getGeneralService().save(wvq);
						} catch (EntityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return 21;
						}
						JSONArray json_list020101 = ((JSONObject) json_list0201.get(ii)).containsKey("options") ? (JSONArray) ((JSONObject) json_list0201.get(ii)).get("options") : null;
						if (json_list020101 != null) {
							for (int iii = 0; iii < json_list020101.size(); iii++) {
								WeVoteOption wvo = new WeVoteOption();
								/* 问题的值 */
								String value = ((JSONObject) json_list020101.get(iii)).containsKey("value") ? ((JSONObject) json_list020101.get(iii)).getString("value") : "";
								wvo.setWvoValue(value);
								/* 投票结果 */
								String result = ((JSONObject) json_list020101.get(iii)).containsKey("result") ? ((JSONObject) json_list020101.get(iii)).getString("result") : "";
								wvo.setWvoResult(result);
								/* 百分比 */
								String precentage = ((JSONObject) json_list020101.get(iii)).containsKey("precentage") ? ((JSONObject) json_list020101.get(iii)).getString("precentage") : "";
								wvo.setWvoPrecentage(precentage);
								/* 上一步save失败导致没有ID */
								if (wvq.getWvqId() == null || "".equals(wvq.getWvqId())) {
									return 21;
								}
								/* 问题内容ID */
								wvo.setWvoWvqId(wvq.getWvqId());
								try {
									wvo = (WeVoteOption) this.getGeneralService().save(wvo);
								} catch (EntityException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									return 21;
								}
								JSONArray json_list02010101 = ((JSONObject) json_list020101.get(iii)).containsKey("results") ? (JSONArray) ((JSONObject) json_list020101.get(iii)).get("results")
										: null;
								if (json_list02010101 != null) {
									for (int iiii = 0; iiii < json_list02010101.size(); iiii++) {
										WeVoteResult wvr = new WeVoteResult();
										/* 学员LOGIN_ID-昵称 */
										String nickName = ((JSONObject) json_list02.get(iii)).containsKey("nickName") ? ((JSONObject) json_list02.get(iii)).getString("nickName") : "";
										/* 回答（当非选择题） */
										String answer = ((JSONObject) json_list02.get(iii)).containsKey("answer") ? ((JSONObject) json_list02.get(iii)).getString("answer") : "";
										/* 用户ID */
										String uid = ((JSONObject) json_list02.get(iii)).containsKey("uid") ? ((JSONObject) json_list02.get(iii)).getString("uid") : "";
										/* 上一步save失败导致没有ID */
										if (wvo.getWvoId() == null || "".equals(wvo.getWvoId())) {
											return 21;
										}
										/* 问题值ID */
										wvr.setWvrWvoId(wvo.getWvoId());
										try {
											wvr = (WeVoteResult) this.getGeneralService().save(wvr);
										} catch (EntityException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
											return 21;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		/* 直播问卷结果 */
		JSONArray json_list03 = (JSONArray) map3.get("surveyList");
		if (json_list03 != null) {
			for (int i = 0; i < json_list03.size(); i++) {
				WeSurveySurveylist wss = new WeSurveySurveylist();
				/* 问卷主题 */
				String subject = ((JSONObject) json_list03.get(i)).containsKey("subject") ? ((JSONObject) json_list03.get(i)).getString("subject") : "";
				wss.setWssSubject(subject);
				/* 回答时间 */
				String submitTime = ((JSONObject) json_list03.get(i)).containsKey("submitTime") ? ((JSONObject) json_list03.get(i)).getString("submitTime") : "";
				wss.setWssSubmittime(submitTime);
				/* 直播ID */
				wss.setWssWebcastid(webcastId);
				/* 采集时间 */
				wss.setWssDate(new Date());
				try {
					wss = (WeSurveySurveylist) this.getGeneralService().save(wss);
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return 31;
				}
				JSONArray json_list0301 = (JSONArray) map3.get("questions");
				if (json_list0301 != null) {
					for (int ii = 0; ii < json_list0301.size(); ii++) {
						WeSurveyQuestion wsq = new WeSurveyQuestion();
						/* 问题 */
						String question = ((JSONObject) json_list03.get(i)).containsKey("question") ? ((JSONObject) json_list03.get(i)).getString("question") : "";
						wsq.setWsqQuestion(question);
						/* 上一步save失败导致没有ID */
						if (wss.getWssId() == null || "".equals(wss.getWssId())) {
							return 31;
						}
						/* 问卷结果表_主题id */
						wsq.setWsqWssId(wss.getWssId());
						try {
							wsq = (WeSurveyQuestion) this.getGeneralService().save(wsq);
						} catch (EntityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return 31;
						}
						JSONArray json_list030101 = (JSONArray) map3.get("responses");
						if (json_list030101 != null) {
							for (int iii = 0; iii < json_list030101.size(); iii++) {
								WeSurveyResponse wsr = new WeSurveyResponse();
								/* 学员LOGIN_ID-用户名字 */
								String name = ((JSONObject) json_list03.get(i)).containsKey("name") ? ((JSONObject) json_list03.get(i)).getString("name") : "";
								wsr.setWsrName(name);
								/* 用户ID */
								String uid = ((JSONObject) json_list03.get(i)).containsKey("uid") ? ((JSONObject) json_list03.get(i)).getString("uid") : "";
								wsr.setWsrUid(uid);
								/* 回答 */
								String response = ((JSONObject) json_list03.get(i)).containsKey("response") ? ((JSONObject) json_list03.get(i)).getString("response") : "";
								wsr.setWsrResponse(response);
								/* 上一步save失败导致没有ID */
								if (wsq.getWsqId() == null || "".equals(wsq.getWsqId())) {
									return 31;
								}
								/* 问题id */
								wsr.setWsrWsqId(wsq.getWsqId());
								try {
									wsr = (WeSurveyResponse) this.getGeneralService().save(wsr);
								} catch (EntityException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									return 31;
								}
							}
						}
					}
				}
			}
		}
		/* 直播用户提问数据 */;
		JSONArray json_list04 = (JSONArray) map4.get("qaList");
		if (json_list04 != null) {
			if (json_list04 != null) {
				for (int i = 0; i < json_list04.size(); i++) {
					WeQA wq = new WeQA();
					/* 提问时间 */
					String submitTime = ((JSONObject) json_list04.get(i)).containsKey("submitTime") ? ((JSONObject) json_list04.get(i)).getString("submitTime") : "";
					wq.setWqSubmittime(submitTime);
					/* 是否发布 */
					String published = ((JSONObject) json_list04.get(i)).containsKey("published") ? ((JSONObject) json_list04.get(i)).getString("published") : "";
					wq.setWqPublished(published);
					/* 问题 */
					String question = ((JSONObject) json_list04.get(i)).containsKey("question") ? ((JSONObject) json_list04.get(i)).getString("question") : "";
					wq.setWqQuestion(question);
					/* 回答 */
					String response = ((JSONObject) json_list04.get(i)).containsKey("response") ? ((JSONObject) json_list04.get(i)).getString("response") : "";
					wq.setWqResponse(response);
					/* 提问者UserID */
					String submitter = ((JSONObject) json_list04.get(i)).containsKey("submitter") ? ((JSONObject) json_list04.get(i)).getString("submitter") : "";
					wq.setWqSubmitter(submitter);
					/* 回答者UserID */
					String answerBy = ((JSONObject) json_list04.get(i)).containsKey("answerBy") ? ((JSONObject) json_list04.get(i)).getString("answerBy") : "";
					wq.setWqAnswerby(answerBy);
					/* 回答用户的名字 */
					String name = ((JSONObject) json_list04.get(i)).containsKey("name") ? ((JSONObject) json_list04.get(i)).getString("name") : "";
					wq.setWqName(name);
					/* 直播id */
					wq.setWqWebcastid(webcastId);
					/* 采集日期 */
					wq.setWqDate(new Date());
					try {
						wq = (WeQA) this.getGeneralService().save(wq);
					} catch (EntityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return 41;
					}
				}
			}
		}
		/* 直播点名签到数据 */
		JSONArray json_list05 = (JSONArray) map5.get("rollcall");
		if (json_list05 != null) {
			for (int i = 0; i < json_list05.size(); i++) {
				WeRollcall wr = new WeRollcall();
				/* 开始点名时间 */
				String startTime = ((JSONObject) json_list05.get(i)).containsKey("startTime") ? ((JSONObject) json_list05.get(i)).getString("startTime") : "";
				wr.setWrStarttime(startTime);
				/* 结束点名时间 */
				String endTime = ((JSONObject) json_list05.get(i)).containsKey("endTime") ? ((JSONObject) json_list05.get(i)).getString("endTime") : "";
				wr.setWrEndtime(endTime);
				/* 总数 */
				String total = ((JSONObject) json_list05.get(i)).containsKey("total") ? ((JSONObject) json_list05.get(i)).getString("total") : "";
				wr.setWrTotal(total);
				/* 出席人数 */
				String present = ((JSONObject) json_list05.get(i)).containsKey("present") ? ((JSONObject) json_list05.get(i)).getString("present") : "";
				wr.setWrPresent(present);
				/* 缺席人数 */
				String absent = ((JSONObject) json_list05.get(i)).containsKey("absent") ? ((JSONObject) json_list05.get(i)).getString("absent") : "";
				wr.setWrAbsent(absent);
				/* 直播id */
				wr.setWrWebcastid(webcastId);
				/* 采集日期 */
				wr.setWrDate(new Date());
				try {
					wr = (WeRollcall) this.getGeneralService().save(wr);
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return 51;
				}
				JSONArray json_list0501 = ((JSONObject) json_list05.get(i)).getJSONArray("users");
				// (JSONArray) map5.get("users");
				if (json_list0501 != null) {
					for (int ii = 0; ii < json_list0501.size(); ii++) {
						WeRollcallUser wru = new WeRollcallUser();
						/* 用户ID */
						String id = ((JSONObject) json_list0501.get(ii)).containsKey("id") ? ((JSONObject) json_list0501.get(ii)).getString("id") : "";
						wru.setWruUid(id);
						/* 用户名字 */
						String name = ((JSONObject) json_list0501.get(ii)).containsKey("name") ? ((JSONObject) json_list0501.get(ii)).getString("name") : "";
						wru.setWruName(name);
						/* 该用户是否出席(true|false) */
						String pre = ((JSONObject) json_list0501.get(ii)).containsKey("present") ? ((JSONObject) json_list0501.get(ii)).getString("present") : "";
						wru.setWruPresent(pre);
						/* 上一步save失败导致没有ID */
						if (wr.getWrId() == null || "".equals(wr.getWrId())) {
							return 51;
						}
						/* 签到表ID */
						wru.setWruWrId(wr.getWrId());
						try {
							wru = (WeRollcallUser) this.getGeneralService().save(wru);
						} catch (EntityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return 51;
						}
					}
				}
			}
		}
		/* select into */
		String selIntoSql = "INSERT INTO SAC_LIVE_GETDATA (ID, SID, DL_TIME, DC_TIME, DM_TIMES, WJ_TIMES, DY_TIMES, LIVE_ID)"
				+ " SELECT SEQ_SAC_LIVE_GETDATA.NEXTVAL, A.SID, A.DL_TIME / (1000 * 60 * 60 * 24) + TO_DATE('1970-01-01 08:00:00', 'YYYY-MM-DD HH:MI:SS') DL_TIME, A.DC_TIME / (1000 * 60 * 60 * 24) + TO_DATE('1970-01-01 08:00:00', 'YYYY-MM-DD HH:MI:SS') DC_TIME, B.DM_TIMES, C.WJ_TIMES, D.DY_TIMES, '"
				+ webcastId
				+ "' FROM ("
				+ " SELECT WH_UID SID, WH_JOINTIME DL_TIME, WH_LEAVETIME DC_TIME FROM WE_HISTORY WHERE WH_WEBCASTID = '"
				+ webcastId
				+ "') A"
				+ " LEFT JOIN ("
				+ " SELECT COUNT(B.WRU_UID) DM_TIMES, B.WRU_UID SID FROM WE_ROLLCALL A INNER JOIN WE_ROLLCALL_USERS B ON A.WR_ID = B.WRU_WR_ID GROUP BY B.WRU_NAME,A.WR_ID,B.WRU_WR_ID,A.WR_WEBCASTID,B.WRU_PRESENT, B.WRU_UID HAVING LOWER(B.WRU_PRESENT) = 'true' AND A.WR_WEBCASTID = '"
				+ webcastId
				+ "') B"
				+ " ON A.SID = B.SID"
				+ " LEFT JOIN ("
				+ " SELECT COUNT(C.WSR_UID) WJ_TIMES, C.WSR_UID SID FROM WE_SURVEY_SURVEYLIST A INNER JOIN WE_SURVEY_QUESTIONS B ON A.WSS_ID = B.WSQ_WSS_ID INNER JOIN WE_SURVEY_RESPONSE C ON B.WSQ_ID = C.WSR_WSQ_ID GROUP BY WSR_UID, A.WSS_ID, B.WSQ_WSS_ID, B.WSQ_ID, C.WSR_WSQ_ID, A.WSS_WEBCASTID HAVING A.WSS_WEBCASTID = '"
				+ webcastId + "') C" + " ON A.SID = C.SID" + " LEFT JOIN ("
				+ " SELECT COUNT(WQ_SUBMITTER) DY_TIMES, WQ_SUBMITTER SID FROM WE_QA GROUP BY WQ_SUBMITTER,WQ_WEBCASTID HAVING WQ_WEBCASTID = '" + webcastId + "') D" + " ON A.SID = D.SID";
		/* 全部采集完毕并且没报错则更新直播表中该信息的采集状态为已采集 */
		String upt = "UPDATE PE_BZZ_TCH_COURSE SET FLAG_LIVEDATA = 'FlagLiveData1' WHERE ID = '" + sacLiveId + "'";
		try {
			/* 直播学习记录展示不用汇总，因此不用此表有记录 */
			// this.getGeneralService().executeBySQL(selIntoSql);
			this.getGeneralService().executeBySQL(upt);
		} catch (EntityException e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	public void setEntityClass() {

	}

	public void setServletPath() {
		this.servletPath = "/entity/basic/sacLive";
	}

	public void setBean(SacLive instance) {
		super.superSetBean(instance);

	}

	public SacLive getBean() {
		return null;
	}

	/**
	 * 是否图片
	 * 
	 * @author Lee
	 * @return
	 */
	private boolean isImageFile(String fileName) {
		if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".gif") || fileName.toLowerCase().endsWith(".png") || fileName.toLowerCase().endsWith(".bmp")
				|| fileName.toLowerCase().endsWith(".jpeg") || fileName.toLowerCase().endsWith(".tiff")) {
			return true;
		}
		return false;
	}

	/**
	 * 设置课程图片
	 * 
	 * @author Lee
	 * @return
	 */
	public String setCourseImg() {
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			/* 存储选中ids便于后续操作使用 */
			ServletActionContext.getRequest().getSession().setAttribute("slaids", ids);
		}
		return "setCourseImg";
	}

	/**
	 * 设置讲师图片
	 * 
	 * @author Lee
	 * @return
	 */
	public String setTeacherImg() {
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			/* 存储选中ids便于后续操作使用 */
			ServletActionContext.getRequest().getSession().setAttribute("slaids", ids);
			/* 设置源action用于设置讲师图片页面的返回按钮正确返回 */
			ServletActionContext.getRequest().getSession().setAttribute("fromAction", this.getServletPath());
		}
		return "setTeaImg";
	}

	/**
	 * 上传图片
	 * 
	 * @author Administrator
	 * @return
	 */
	public String uploadCourseImg() {
		Map map = new HashMap();
		String linkUrl = null;
		if (get_upload() != null) {
			linkUrl = super.uploadImgOnly();
		}
		try {
			/* 获取param参数用于判断是上传课程图片(c)还是讲师图片(t) */
			String param = ServletActionContext.getRequest().getParameter("param");
			/* 提示标题 */
			String msg = "";
			String[] ids = (String[]) ServletActionContext.getRequest().getSession().getAttribute("slaids");
			StringBuffer slaids = new StringBuffer();
			for (int i = 0; i < ids.length; i++) {
				slaids.append("'" + ids[i] + "',");
			}
			slaids.append("''");
			String sql = "";
			if ("c".equalsIgnoreCase(param)) {
				sql = "UPDATE PE_BZZ_TCH_COURSE SET FLAG_IMG = 'FlagImg1', PHOTO_LINK = '" + linkUrl + "' WHERE ID IN (" + slaids.toString() + ")";
				msg = "课程";
			} else {
				sql = "UPDATE PE_BZZ_TCH_COURSE SET FLAG_TEAIMG = 'FlagTeaimg1', TEA_IMG = '" + linkUrl + "' WHERE ID IN (" + slaids.toString() + ")";
				msg = "讲师";
			}
			int result = this.getGeneralService().executeBySQL(sql);
			if (get_upload() != null && linkUrl != null && result > 0) {
				this.setMsg(msg + "图片上传成功！");
			} else {
				this.setMsg(msg + "图片上传失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/* 清除之前选中的ids */
			ServletActionContext.getRequest().getSession().removeAttribute("slaids");
		}
		return "lee";
	}

	/**
	 * 删除图片
	 * 
	 * @author Lee
	 * @return
	 */
	public String removeImg() {
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			int result = 0;
			try {
				/* 获取param参数用于判断是上传课程图片(c)还是讲师图片(t) */
				String param = ServletActionContext.getRequest().getParameter("param");
				/* 提示标题 */
				String msg = "";
				StringBuffer slaids = new StringBuffer();
				for (int i = 0; i < ids.length; i++) {
					slaids.append("'" + ids[i] + "',");
				}
				slaids.append("''");
				String sql = "";
				if ("c".equalsIgnoreCase(param)) {
					sql = "UPDATE PE_BZZ_TCH_COURSE SET FLAG_IMG = 'FlagImg0', PHOTO_LINK = '' WHERE ID IN (" + slaids.toString() + ")";
					msg = "课程";
				} else {
					sql = "UPDATE PE_BZZ_TCH_COURSE SET FLAG_TEAIMG = 'FlagTeaimg0', TEA_IMG = '' WHERE ID IN (" + slaids.toString() + ")";
					msg = "讲师";
				}
				result = this.getGeneralService().executeBySQL(sql);
				if (result != 0) {
					this.setMsg(msg + "图片删除成功");
				} else {
					this.setMsg(msg + "图片删除失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				/* 清除之前选中的ids */
				ServletActionContext.getRequest().getSession().removeAttribute("slaids");
			}
		}
		return "lee";
	}

	/**
	 * 设置建议学习人群
	 * 
	 * @author Lee
	 * @return
	 */
	public String setSugg() {
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			/* 存储选中ids便于后续操作使用 */
			ServletActionContext.getRequest().getSession().setAttribute("slaids", ids);
		}
		return "setSugg";
	}

	/**
	 * 删除建议学习人群
	 * 
	 * @author Lee
	 * @return
	 */
	public String removeSugg() {
		try {
			if (this.getIds() != null && this.getIds().length() > 0) {
				String sql = "";
				String[] ids = getIds().split(",");
				for (int i = 0; i < ids.length; i++) {
					sql = "DELETE FROM PE_BZZ_TCH_COURSE_SUGGEST WHERE FK_COURSE_ID = '" + ids[i] + "'";
					this.getGeneralService().executeBySQL(sql);
				}
				StringBuffer slaids = new StringBuffer();
				for (int i = 0; i < ids.length; i++) {
					slaids.append("'" + ids[i] + "',");
				}
				slaids.append("''");
				/* 更新课程的设置学习人群状态 */
				sql = "UPDATE PE_BZZ_TCH_COURSE SET FLAG_SUGGESTSET = 'FlagSuggestSet0' WHERE ID IN (" + slaids.toString() + ")";
				int result = this.getGeneralService().executeBySQL(sql);
				if (result > 0)
					this.setMsg("删除成功");
				else
					this.setMsg("删除失败");
			} else {
				this.setMsg("删除失败");
			}
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("删除失败");
		}
		return "lee";
	}

	public String getParam_id() {
		return param_id;
	}

	public void setParam_id(String param_id) {
		this.param_id = param_id;
	}

	public PeTchCourseService getPeTchCourseService() {
		return peTchCourseService;
	}

	public void setPeTchCourseService(PeTchCourseService peTchCourseService) {
		this.peTchCourseService = peTchCourseService;
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
	}
}
