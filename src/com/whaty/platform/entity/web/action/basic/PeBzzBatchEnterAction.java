package com.whaty.platform.entity.web.action.basic;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.StudentBatch;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeDetailService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.training.basic.StudyProgress;
import com.whaty.platform.util.JsonUtil;

public class PeBzzBatchEnterAction extends MyBaseAction<PeBzzBatch> {

	private String method;

	@Override
	public void initGrid() {
	}

	public String toAddStudent() {
		try {

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String sql = "";
			if (this.getIds() != null && this.getIds().length() > 0) {
				String[] ids = getIds().split(",");
				for (int i = 0; i < ids.length; i++) {
					id = ids[i];
					sql = " select end_time from pe_Bzz_Batch where id = '" + id + "' ";
					List list = this.getGeneralService().getBySQL(sql);
					String end_time = list.get(0).toString();
					if (format.parse(end_time).getTime() + 24 * 60 * 60 * 1000 < new Date().getTime()) {
						this.setMsg("报名时间已结束，不允许添加学员");
						this.setTogo("javascript:history.go(-1);");
						return "msg";
					}
					this.setId(id);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toAddStudent";
	}

	public String toImpStudents() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String sql = "";
			if (this.getId() != null && this.getId().length() > 0) {
				String[] ids = getId().split(",");
				for (int i = 0; i < ids.length; i++) {
					if (null != actionUrl && !"".equals(actionUrl)) {
						SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String nowString = sdFormat.format(new Date());
						sql = "SELECT 1 FROM PE_BZZ_BATCH WHERE END_TIME <= TO_DATE('" + nowString + "','yyyy-MM-dd hh24:mi:ss') and  id = '" + ids[i] + "' ";
						List list = this.getGeneralService().getBySQL(sql);
						if (null != list && list.size() > 0) {
							this.setMsg("报名时间已结束，不允许导入学员");
							this.setTogo("javascript:history.go(-1);");
							return "msg";
						}
					} else {
						sql = " select end_time from pe_Bzz_Batch where id = '" + ids[i] + "' ";
						List list = this.getGeneralService().getBySQL(sql);
						String end_time = list.get(0).toString();
						if (format.parse(end_time).getTime() + 24 * 60 * 60 * 1000 < new Date().getTime()) {
							this.setMsg("报名时间已结束，不允许导入学员");
							this.setTogo("javascript:history.go(-1);");
							return "msg";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServletActionContext.getRequest().getSession().setAttribute("id", id);
		return "toImpStudents";
	}

	public String impStudents() throws EntityException {
		String liveOrBatch = ServletActionContext.getRequest().getParameter("actionUrl");
		Map map = new HashMap();
		this.setDoLog(false);// 不记录日志
		if (param_id == null || "".equals(param_id)) {
			this.setMsg("导入失败！请联系管理员！");// 主键参数没接收到
			return "m_msg";
		} else {
			StringBuffer msg = new StringBuffer();
			DetachedCriteria rodc = DetachedCriteria.forClass(PeBzzBatch.class);
			rodc.add(Restrictions.eq("id", param_id));
			List<PeBzzBatch> peBzzBatchlist = this.getGeneralService().getList(rodc);
			String kc_sql = "SELECT DISTINCT FK_COURSE_ID FROM PR_BZZ_TCH_OPENCOURSE WHERE FK_BATCH_ID = '" + param_id + "'";
			List kc_list = this.getGeneralService().getBySQL(kc_sql);
			if (kc_list == null || kc_list.size() == 0) {
				msg.append("专项无课程，请先添加课程！");
				this.setMsg(msg.toString());
				return "m_msg";
			}
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);// 记录操作日志时使用
			Workbook work = null;
			try {
				File file = this.get_upload();
				work = Workbook.getWorkbook(file);
			} catch (Exception e) {
				msg.append("Excel表格读取异常！导入学员失败！");// 解析Excel异常
				this.setMsg(msg.toString());
				return "m_msg";
			}
			Sheet sheet = work.getSheet(0);
			int rows = sheet.getRows();
			int nullRow = 0;
			if (rows < 2) {
				msg.append("Excel表格异常，导入学员失败！请检查Excel文件！");// 无数据
				this.setMsg(msg.toString());
				return "m_msg";
			}
			// 人数上限判断

			// 直播收费还是免费
			String liveSqlString = "SELECT 1 FROM PE_BZZ_TCH_COURSE WHERE ID IN (SELECT FK_COURSE_ID FROM PR_BZZ_TCH_OPENCOURSE WHERE FK_BATCH_ID = '" + param_id + "') AND PRICE > 0";
			List liveList = this.getGeneralService().getBySQL(liveSqlString);
			String sql1 = "";
			if (null != liveList && liveList.size() > 0) {// 收费直播
				// 已报人数
				sql1 = "SELECT NVL(COUNT(PBTSE.ID),0) FROM PR_BZZ_TCH_OPENCOURSE PBTO INNER JOIN PR_BZZ_TCH_STU_ELECTIVE_BACK PBTSE ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID AND PBTO.FK_BATCH_ID = '" + param_id + "' AND PBTSE.FK_ORDER_ID IS NOT NULL GROUP BY PBTO.FK_BATCH_ID";
			} else {// 免费直播
				sql1 = "SELECT NVL(COUNT(ID),0) FROM STU_BATCH WHERE BATCH_ID = '" + param_id + "'";
			}
			// 已报人数
			Object nowStudents = 0;
			List list1 = this.getGeneralService().getBySQL(sql1);
			if (null != list1 && list1.size() > 0)
				nowStudents = list1.get(0);
			// 允许人数
			sql1 = "SELECT NVL(PBTC.MAXSTUS,0) FROM PE_BZZ_TCH_COURSE PBTC,PR_BZZ_TCH_OPENCOURSE PBTO,PE_BZZ_BATCH PBB WHERE PBB.ID = PBTO.FK_BATCH_ID AND PBB.ID = '" + param_id + "' AND PBTO.FK_COURSE_ID = PBTC.ID";
			Object maxStudents = this.getGeneralService().getBySQL(sql1).get(0);
			BigDecimal b1 = new BigDecimal(nowStudents.toString());
			BigDecimal b2 = new BigDecimal(maxStudents.toString());
			if (b1.subtract(b2).doubleValue() + rows - 1 > 0) {
				msg.append("报名人数上限为：" + maxStudents + "，还可以添加" + b2.subtract(b1) + "名学员，请修改后重新导入！");
				this.setMsg(msg.toString());
				return "m_msg";
			}
			String col_content = "";
			List<String> ids_list = new ArrayList<String>();
			Map<String, Map<String, String>> file_data = new HashMap<String, Map<String, String>>();
			List<String> repeat_reg_no_list = new ArrayList<String>();
			Map<Integer, String> repeat_reg_no_data = new HashMap<Integer, String>();
			Map<Integer, String> cel_data = new HashMap<Integer, String>();
			List eleBacklist = new ArrayList();
			List<StudentBatch> stuBatchList = new ArrayList<StudentBatch>();
			String content = "";
			String lower_content = "";
			int err_count = 0;
			Map<Integer, String> err_map = new HashMap<Integer, String>();
			// 固定查询，没必要放在循环里，这样只查询一次
			EnumConst enumConstByFlagBatchPayState = this.getMyListService().getEnumConstByNamespaceCode("FlagBatchPayState", "0");
			DetachedCriteria opdc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
			opdc.add(Restrictions.eq("peBzzBatch.id", id));
			opdc.createCriteria("enumConstByFlagChoose", "enumConstByFlagChoose", DetachedCriteria.LEFT_JOIN);
			opdc.add(Restrictions.eq("enumConstByFlagChoose.code", "1"));
			// 必选课程生成报名表和选课表数据
			List<PrBzzTchOpencourse> oplist = this.getGeneralService().getList(opdc);
			for (int i = 1; i < rows; i++) {
				try {
					Cell cell = sheet.getCell(0, i);
					if (cell.getType() == CellType.LABEL) {
						LabelCell lc = (LabelCell) cell;
						content = lc.getString().trim();
					} else {
						content = sheet.getCell(0, i).getContents().trim();
					}
					lower_content = content.toLowerCase();
					if (cel_data.containsValue(lower_content)) {
						msg.append("第" + (i + 1) + "行数据无效，" + "学员：" + content + " 重复出现！<br />");
						repeat_reg_no_data.put(i, lower_content);
						if (!err_map.containsKey((i + 1))) {
							err_map.put((i + 1), content);
							err_count++;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					msg.append("数据读取异常，导入学员失败！请检查Excel文件！");
					this.setMsg(msg.toString());
					return "m_msg";
				}
				String reg_no_data = content;
				if (content == null || content.trim().length() == 0) {
					nullRow++;
					continue;
				}
				String reg_no_lower = reg_no_data.toLowerCase().trim();
				String reg_no_upper = reg_no_data.toUpperCase().trim();
				String reg_no_lower_sql = "SELECT 1 FROM PE_BZZ_STUDENT INNER JOIN SSO_USER ON FK_SSO_USER_ID = SSO_USER.ID WHERE REG_NO = '" + reg_no_lower + "' AND FK_ENTERPRISE_ID IN (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId()
						+ "' UNION ALL SELECT ID FROM PE_ENTERPRISE WHERE FK_PARENT_ID IN (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "'))";
				String reg_no_upper_sql = "SELECT 1 FROM PE_BZZ_STUDENT INNER JOIN SSO_USER ON FK_SSO_USER_ID = SSO_USER.ID WHERE REG_NO = '" + reg_no_upper + "' AND FK_ENTERPRISE_ID IN (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId()
						+ "' UNION ALL SELECT ID FROM PE_ENTERPRISE WHERE FK_PARENT_ID IN (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "'))";
				List reg_no_lower_list = this.getGeneralService().getBySQL(reg_no_lower_sql);
				List reg_no_upper_list = this.getGeneralService().getBySQL(reg_no_upper_sql);
				// 大小都不存在时为不存在学员
				if ((reg_no_lower_list == null || reg_no_lower_list.size() == 0) && (reg_no_upper_list == null || reg_no_upper_list.size() == 0)) {
					msg.append("第" + (i + 1) + "行数据无效，" + "学员：" + reg_no_data + " 不存在或不是本机构学员！<br />");
					if (!err_map.containsKey((i + 1))) {
						err_map.put((i + 1), content);
						err_count++;
					}
				} else {
					String reg_no_ = reg_no_data;
					// 小写不存在
					if (reg_no_lower_list == null || reg_no_lower_list.size() == 0) {
						reg_no_ = reg_no_upper;
					}
					// 大写不存在
					if (reg_no_upper_list == null || reg_no_upper_list.size() == 0) {
						reg_no_ = reg_no_lower;
					}
					String reg_no_batch_sql = "SELECT 1 FROM PE_BZZ_STUDENT A INNER JOIN STU_BATCH B ON A.ID = B.STU_ID INNER JOIN PE_BZZ_BATCH C ON B.BATCH_ID = C.ID WHERE C.ID = '" + param_id + "' AND A.ID = '" + reg_no_ + "'";
					List reg_no_batch_data = this.getGeneralService().getBySQL(reg_no_batch_sql);
					// 学员已存在专项中
					if (reg_no_batch_data != null && reg_no_batch_data.size() != 0) {
						msg.append("第" + (i + 1) + "行数据无效，" + "学员：" + reg_no_data + " 已添加！<br />");
						if (!err_map.containsKey((i + 1))) {
							err_map.put((i + 1), content);
							err_count++;
						}
					} else {
						DetachedCriteria dc_student = DetachedCriteria.forClass(PeBzzStudent.class);
						dc_student.add(Restrictions.eq("regNo", reg_no_));
						PeBzzStudent peBzzStudent = this.getGeneralService().getStudentInfo(dc_student);
						// 标记错误使用
						int idx = 0;
						DetachedCriteria dceletived = DetachedCriteria.forClass(PrBzzTchStuElective.class);
						// dceletived.createCriteria("prBzzTchOpencourse",
						// "prBzzTchOpencourse").createAlias("peBzzTchCourse",
						// "peBzzTchCourse");
						dceletived.createAlias("peBzzStudent", "peBzzStudent");
						dceletived.add(Restrictions.eq("peBzzStudent", peBzzStudent));
						// Lee 注释↓查询选课记录方式：因为公共课程课程对应多个开课ID所以修改查询方式
						// dceletived.add(Restrictions.eq("prBzzTchOpencourse",
						// opencourse));
						// Lee↓新查询选课方式

						// dceletived.add(Restrictions.eq("peBzzTchCourse.id",
						// opencourse.getPeBzzTchCourse().getId()));

						DetachedCriteria dceletiveback = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
						// dceletiveback.createCriteria("prBzzTchOpencourse",
						// "prBzzTchOpencourse").createAlias("peBzzTchCourse",
						// "peBzzTchCourse");
						dceletiveback.createAlias("peBzzStudent", "peBzzStudent");
						dceletiveback.add(Restrictions.eq("peBzzStudent", peBzzStudent));
						// Lee 注释↓查询选课记录方式：因为公共课程课程对应多个开课ID所以修改查询方式
						// dceletiveback.add(Restrictions.eq("prBzzTchOpencourse",
						// opencourse));
						// Lee↓新查询选课方式

						// dceletiveback.add(Restrictions.eq("peBzzTchCourse.id",
						// opencourse.getPeBzzTchCourse().getId()));
						List tempList = this.getGeneralService().getList(dceletived);
						List tempBackList = this.getGeneralService().getList(dceletiveback);
						// 查询做修改，不用查询指定学员指定课程，这样需要循环查询，现在是查询学员所有课程
						// 根据查询的所有课程和现在的课程分别做比对
						for (PrBzzTchOpencourse opencourse : oplist) {
							boolean exist = false;
							if (tempList != null && tempList.size() > 0) {
								for (int j = 0; j < tempList.size(); j++) {
									PrBzzTchStuElective ele = (PrBzzTchStuElective) tempList.get(j);
									// 加判断，跳过报名表中没有开课ID的垃圾数据
									if (null != ele.getPrBzzTchOpencourse() && null != opencourse) {
										if (ele.getPrBzzTchOpencourse().getId().equals(opencourse.getId())) {
											exist = true;
											break;
										}
									}
								}
							}
							if (tempBackList != null && tempBackList.size() > 0) {
								// 如果已经存在，没必要在比对了
								if (!exist) {
									for (int j = 0; j < tempBackList.size(); j++) {
										PrBzzTchStuElectiveBack back = (PrBzzTchStuElectiveBack) tempBackList.get(j);
										// 加判断，跳过报名表中没有开课ID的垃圾数据
										if (null != back.getPrBzzTchOpencourse() && null != opencourse) {
											if (back.getPrBzzTchOpencourse().getId().equals(opencourse.getPeBzzTchCourse().getId())) {
												exist = true;
												break;
											}
										}
									}

								}
							}
							if (exist) {
								msg.append("第" + (i + 1) + "行数据无效，学员：" + reg_no_data + " 已选 " + opencourse.getPeBzzTchCourse().getCode() + " 课程！<br />");
								if (!err_map.containsKey((i + 1))) {
									err_map.put((i + 1), content);
									err_count++;
									idx++;
								}
							}

						}
						if (idx == 0) {
							for (PrBzzTchOpencourse opencourse : oplist) {
								try {
									PrBzzTchStuElectiveBack eleBack = new PrBzzTchStuElectiveBack();
									eleBack.setPeBzzStudent(peBzzStudent);
									eleBack.setPrBzzTchOpencourse(opencourse);
									int cc = 0;// 记录PrBzzTchStuElectiveBack是否重复
									for (int ii = 0; ii < eleBacklist.size(); ii++) {
										PrBzzTchStuElectiveBack eleBack2 = (PrBzzTchStuElectiveBack) eleBacklist.get(ii);
										if (eleBack.getPeBzzStudent() != eleBack2.getPeBzzStudent() || eleBack.getPrBzzTchOpencourse() != eleBack2.getPrBzzTchOpencourse()) {
											cc++;
										}
									}
									// 如果PrBzzTchStuElectiveBack和list中所有对象都不同则add否则会导致违反唯一约束
									if (cc == eleBacklist.size())
										eleBacklist.add(eleBack);
								} catch (Exception e) {
									e.printStackTrace();
									msg.append("第" + (i + 1) + "学员：" + reg_no_data + " 选课验证失败！<br />");
									if (!err_map.containsKey((i + 1))) {
										err_map.put((i + 1), content);
										err_count++;
									}
									this.setMsg(msg.toString());
									return "m_msg";
								}
							}
							StudentBatch studentBatch = new StudentBatch();
							studentBatch.setPeStudent(peBzzStudent);
							studentBatch.setPeBzzBatch(peBzzBatchlist.get(0));
							studentBatch.setEnumConstByFlagBatchPayState(enumConstByFlagBatchPayState);
							stuBatchList.add(studentBatch);
						}
					}
				}
				cel_data.put(i, lower_content);
			}
			if (eleBacklist != null && eleBacklist.size() > 0 && stuBatchList != null && stuBatchList.size() > 0)
				this.getPeDetailService().saveStuBatchBack(eleBacklist, stuBatchList);
			// this.saveFreeOrNot(peBzzBatchlist, stuBatchList, eleBacklist,
			// null,
			// this.param_id);
			int success_count = rows - nullRow - err_count - 1;
			String html = "<table width='100%' border='0'>";
			html += "<tr><td colspan='4' align='left;'>共" + (rows - nullRow - 1) + "行数据，成功导入" + success_count + "条记录！</td></tr>";
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
		return "m_msg";
	}

	private String id;// 专项id
	private String param_id;// 专项id
	private PeDetailService peDetailService;
	private String actionUrl;// 判断是从直播过来的还是从专项过来的

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParam_id() {
		return param_id;
	}

	public void setParam_id(String param_id) {
		this.param_id = param_id;
	}

	public PeDetailService getPeDetailService() {
		return peDetailService;
	}

	public void setPeDetailService(PeDetailService peDetailService) {
		this.peDetailService = peDetailService;
	}

	// Lee end

	public void setEntityClass() {
		this.entityClass = PeBzzBatch.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/basic/peBzzBatchEnter";
	}

	public void setBean(PeBzzBatch instance) {
		super.superSetBean(instance);
	}

	public PeBzzBatch getBean() {
		return super.superGetBean();
	}

	/**
	 * 重写框架方法：专项信息
	 * 
	 * @author linjie
	 * @return
	 */
	public String abstractDetail() {
		Page page = null;
		Map map = new HashMap();
		DetachedCriteria enterdc = DetachedCriteria.forClass(PeBzzBatch.class);
		enterdc.add(Restrictions.eq("id", this.getBean().getId()));
		try {
			page = this.getGeneralService().getByPage(enterdc, Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
			if (page != null) {
				page.setItems(JsonUtil.ArrayToJsonObjects(page.getItems(), this.getGridConfig().getListColumnConfig()));
				Container o = new Container();
				o.setBean(page.getItems().get(0));
				List list = new ArrayList();
				list.add(o);
				map.put("totalCount", 1);
				map.put("models", list);
			}
			this.setJsonString(JsonUtil.toJSONString(map));
			JsonUtil.setDateformat("yyyy-MM-dd");
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return json();
	}

	public Page list() {
		return null;
	}

	public String checkStatus(List idList) throws EntityException {
		int countStudent = 0;
		int countCourse = 0;
		String sql = "";
		try {
			for (int i = 0; i < idList.size(); i++) {
				sql = "select t.* from STU_BATCH t inner join PE_BZZ_BATCH p on t.BATCH_ID =p.id   where p.id ='" + idList.get(i) + "'";
				List listStudent = this.getGeneralService().getBySQL(sql);
				if (listStudent.size() < 1) {
					countStudent++;
				}
			}
			if (countStudent == 0) {
				return "所选专项培训中没有学员，请添加学员后再申请发布！";
			}
			for (int i = 0; i < idList.size(); i++) {
				sql = "select t.* from pr_bzz_tch_opencourse t inner join PE_BZZ_BATCH p on t.FK_BATCH_ID =p.id   where flag_choose = '1' and  p.id ='" + idList.get(i) + "'";
				List listCourse = this.getGeneralService().getBySQL(sql);
				if (listCourse.size() < 1) {
					countCourse++;
				}
			}
			if (countCourse == 0) {
				return "所选专项培训中没有必选课程，请添加必选课程后再申请发布！";
			}

		} catch (EntityException e) {
			throw e;
		}
		return "";
	}

	@Override
	public Map updateColumn() {
		Map map = new HashMap();
		if (this.getIds() == null || this.getIds().split(",").length > 1) {
			map.put("success", false);
			map.put("info", this.getText("只能操作一条记录!"));
			return map;
		}
		if (this.getColumn().equals("recruitSelected")) {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PeBzzBatch.class);
			List<PeBzzBatch> list = null;
			try {
				list = this.getGeneralService().getList(detachedCriteria);
			} catch (EntityException e1) {
				e1.printStackTrace();
			}
			for (PeBzzBatch peBzzBatch : list) {
				if (peBzzBatch.getId().equals(this.getIds().split(",")[0])) {

					if (null == peBzzBatch.getRecruitSelected() || "0".equals(peBzzBatch.getRecruitSelected())) {
						peBzzBatch.setRecruitSelected("1");
						try {
							this.getGeneralService().save(peBzzBatch);
						} catch (EntityException e) {
							e.printStackTrace();
						}
					}
				} else {
					if ("1".equals(peBzzBatch.getRecruitSelected())) {
						peBzzBatch.setRecruitSelected("0");
						try {
							this.getGeneralService().save(peBzzBatch);
						} catch (EntityException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String msg = "";
		String action = this.getColumn();

		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			try {
				DetachedCriteria tempdc = DetachedCriteria.forClass(PeBzzBatch.class);
				/**
				 * 发布
				 */
				if (action.equals("FlagDeploy.true")) {
					EnumConst enumConstByFlagDeploy = this.getMyListService().getEnumConstByNamespaceCode("FlagDeploy", "1"); // 课程审核状态，待审核
					tempdc.add(Restrictions.in("id", ids));
					List<PeBzzBatch> batchlist = new ArrayList<PeBzzBatch>();
					batchlist = this.getGeneralService().getList(tempdc);
					if (batchlist.get(0).getStartDate().before(new Date())) {
						map.clear();
						map.put("success", "false");
						map.put("info", "当前时间大于专项开始时间，不能发布!");
						return map;
					}
					// 先判断所选课程是否添加学员
					DetachedCriteria dc1 = DetachedCriteria.forClass(StudentBatch.class);
					dc1.createCriteria("peBzzBatch", "peBzzBatch");
					dc1.add(Restrictions.in("peBzzBatch.id", ids));
					try {
						List<StudentBatch> tlist1 = this.getGeneralService().getList(dc1);
						if (tlist1.size() == 0) {
							map.clear();
							map.put("success", "false");
							map.put("info", "该课程没有添加学员,不能发布!");
							return map;
						} else {
							;
						}
					} catch (EntityException e) {
						e.printStackTrace();
					}
					// 先判断所选课程是否添加必选课程
					DetachedCriteria dc2 = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
					dc2.createCriteria("peBzzBatch", "peBzzBatch");
					// dc2.createCriteria("enumConstByFlagChoose");
					/*
					 * dc2.createCriteria("enumConstByFlagChoose",
					 * "enumConstByFlagChoose", DetachedCriteria.LEFT_JOIN);
					 * dc2.add(Restrictions.eq("enumConstByFlagChoose.code",
					 * "1"));
					 */
					dc2.add(Restrictions.in("peBzzBatch.id", ids));
					int count = 0;
					try {
						List<PrBzzTchOpencourse> tlist2 = this.getGeneralService().getList(dc2);
						if (tlist2.size() == 0) {
							map.clear();
							map.put("success", "false");
							map.put("info", "该专项没有添加课程,不能发布!");
							return map;
						} else if (tlist2.size() > 0) {
							for (int i = 0; i < tlist2.size(); i++) {
								if (tlist2.get(i).getEnumConstByFlagChoose().getCode().equals("1"))
									count++;
							}
							if (count == 0) {
								map.clear();
								map.put("success", "false");
								map.put("info", "该专项没有添加必选课程,不能发布!");
								return map;
							}
						}
					} catch (EntityException e) {
						e.printStackTrace();
					}
					Iterator<PeBzzBatch> iterator = batchlist.iterator();
					while (iterator.hasNext()) {
						PeBzzBatch peBzzBatch = iterator.next();
						String logs = peBzzBatch.getOperationLogs();
						if (logs == null) {
							logs = "";
						}
						StringBuffer buf = new StringBuffer(logs);
						buf.append("|" + us.getLoginId() + "/" + us.getUserName() + "执行了专项课程发布操作\n");
						peBzzBatch.setOperationLogs(buf.toString());
						// peBzzTchCourse.setEnumConstByFlagCheckStatus(enumConstFlagIsvalid);
						peBzzBatch.setEnumConstByFlagDeploy(enumConstByFlagDeploy);
						peBzzBatch.setAnnounceDate(new Date());
						this.getGeneralService().save(peBzzBatch);
					}
					msg = "专项课程发布成功";
				}
				/**
				 * 取消发布
				 */
				if (action.equals("FlagDeploy.false")) {
					String ffSqlString = "SELECT 1 FROM PE_BZZ_BATCH WHERE ID ='" + ids[0] + "' AND START_TIME <= SYSDATE AND SYSDATE <= END_TIME";
					List ffList = this.getGeneralService().getBySQL(ffSqlString);
					if (ffList != null && ffList.size() > 0) {
						map.clear();
						map.put("success", "false");
						map.put("info", "操作失败！报名期间专项不允许执行取消发布操作！");
						return map;
					}
					EnumConst enumConstFlagOffline = this.getMyListService().getEnumConstByNamespaceCode("FlagDeploy", "0"); // 课程审核状态，待审核
					tempdc.add(Restrictions.in("id", ids));
					List<PeBzzBatch> batchlist = new ArrayList<PeBzzBatch>();
					batchlist = this.getGeneralService().getList(tempdc);
					// 先判断所选专项是否已经选课
					// DetachedCriteria dc3 =
					// DetachedCriteria.forClass(PrBzzTchStuElective.class);
					// dc3.createCriteria("prBzzTchOpencourse",
					// "prBzzTchOpencourse");
					// dc3.createCriteria("peBzzBatch", "peBzzBatch");
					// dc3.add(Restrictions.in("peBzzBatch.id", ids));
					String pbtseSql = "SELECT 1 FROM PR_BZZ_TCH_STU_ELECTIVE A INNER JOIN PR_BZZ_TCH_OPENCOURSE B ON A.FK_TCH_OPENCOURSE_ID = B.ID INNER JOIN PE_BZZ_BATCH C ON B.FK_BATCH_ID = C.ID AND C.ID = '" + ids[0] + "'";
					int count = 0;
					try {
						// List<PrBzzTchOpencourse> tlist3 =
						// this.getGeneralService().getList(dc3);
						List tlist3 = this.getGeneralService().getBySQL(pbtseSql);
						if (tlist3 != null && tlist3.size() > 0) {
							map.clear();
							map.put("success", "false");
							map.put("info", "该专项已经有学员选课,不能取消发布!");
							return map;
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					Iterator<PeBzzBatch> iterator = batchlist.iterator();
					/*
					 * if (batchlist.get(0).getStartDate().before( new Date())) {
					 * map.clear(); map.put("success", "false"); map.put("info",
					 * "专项已经开始，不能取消发布!"); return map; }
					 */
					while (iterator.hasNext()) {
						PeBzzBatch peBzzBatch = iterator.next();
						String logs = peBzzBatch.getOperationLogs();
						if (logs == null) {
							logs = "";
						}
						StringBuffer buf = new StringBuffer(logs);
						buf.append("|" + us.getLoginId() + "/" + us.getUserName() + "执行了专项课程取消发布操作\n");
						peBzzBatch.setOperationLogs(buf.toString());
						peBzzBatch.setEnumConstByFlagDeploy(enumConstFlagOffline);
						this.getGeneralService().save(peBzzBatch);
					}
					msg = "专项课程取消发布成功";
				}
				map.put("success", "true");
				map.put("info", msg + "共有" + ids.length + "个专项操作成功");
			} catch (Exception e) {
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", msg + "操作失败");
				return map;
			}
		} else {
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
			return map;
		}
		return map;
	}

	public void checkBeforeUpdate() throws EntityException {
		if (this.getBean().getStartDate().after(this.getBean().getEndDate())) {
			throw new EntityException("专项的报名开始时间不能晚于报名结束时间");
		}
	}

	/**
	 * 专项添加、导入学员
	 * 
	 * @param peBzzBatchlist
	 *            专项列表
	 * @param stuBatchList
	 *            学员-专项关系
	 * @param eleBacklist
	 *            学员课程报名
	 * @param peEnterprise
	 *            机构
	 * @param id
	 *            专项ID
	 * @throws EntityException
	 * @author Lee
	 */
	public void saveFreeOrNot(List<PeBzzBatch> peBzzBatchlist, List<StudentBatch> stuBatchList, List eleBacklist, PeEnterprise peEnterprise, String id) throws EntityException {
		DetachedCriteria dc3 = DetachedCriteria.forClass(EnumConst.class);
		dc3.add(Restrictions.eq("namespace", "FlagElectivePayStatus"));
		dc3.add(Restrictions.eq("code", "1"));
		List ec_list = this.getGeneralService().getDetachList(dc3);
		EnumConst enumConstByFlagElectivePayStatus = (EnumConst) ec_list.get(0);
		DetachedCriteria dFlagCheckState = DetachedCriteria.forClass(EnumConst.class);
		dFlagCheckState.add(Restrictions.eq("namespace", "FlagCheckState"));
		dFlagCheckState.add(Restrictions.eq("code", "0"));
		List ec_FlagCheckState = this.getGeneralService().getDetachList(dFlagCheckState);
		EnumConst FlagCheckState = (EnumConst) ec_FlagCheckState.get(0);
		DetachedCriteria dFlagOrderIsValid = DetachedCriteria.forClass(EnumConst.class);
		dFlagOrderIsValid.add(Restrictions.eq("namespace", "FlagOrderIsValid"));
		dFlagOrderIsValid.add(Restrictions.eq("code", "1"));
		List ec_FlagOrderIsValid = this.getGeneralService().getDetachList(dFlagOrderIsValid);
		EnumConst FlagOrderIsValid = (EnumConst) ec_FlagOrderIsValid.get(0);
		DetachedCriteria dFlagOrderState = DetachedCriteria.forClass(EnumConst.class);
		dFlagOrderState.add(Restrictions.eq("namespace", "FlagOrderState"));
		dFlagOrderState.add(Restrictions.eq("code", "1"));
		List ec_FlagOrderState = this.getGeneralService().getDetachList(dFlagOrderState);
		EnumConst FlagOrderState = (EnumConst) ec_FlagOrderState.get(0);
		DetachedCriteria dFlagPaymentState = DetachedCriteria.forClass(EnumConst.class);
		dFlagPaymentState.add(Restrictions.eq("namespace", "FlagPaymentState"));
		dFlagPaymentState.add(Restrictions.eq("code", "0"));
		List ec_FlagPaymentState = this.getGeneralService().getDetachList(dFlagPaymentState);
		EnumConst FlagPaymentState = (EnumConst) ec_FlagPaymentState.get(0);
		DetachedCriteria dFlagOrderType = DetachedCriteria.forClass(EnumConst.class);
		dFlagOrderType.add(Restrictions.eq("namespace", "FlagOrderType"));
		dFlagOrderType.add(Restrictions.eq("code", "2"));
		List ec_FlagOrderType = this.getGeneralService().getDetachList(dFlagOrderType);
		EnumConst FlagOrderType = (EnumConst) ec_FlagOrderType.get(0);
		DetachedCriteria dFlagPaymentMethod = DetachedCriteria.forClass(EnumConst.class);
		dFlagPaymentMethod.add(Restrictions.eq("namespace", "FlagPaymentMethod"));
		dFlagPaymentMethod.add(Restrictions.eq("code", "4"));
		List ec_FlagPaymentMethod = this.getGeneralService().getDetachList(dFlagPaymentMethod);
		EnumConst FlagPaymentMethod = (EnumConst) ec_FlagPaymentMethod.get(0);
		DetachedCriteria dFlagPaymentType = DetachedCriteria.forClass(EnumConst.class);
		dFlagPaymentType.add(Restrictions.eq("namespace", "FlagPaymentType"));
		dFlagPaymentType.add(Restrictions.eq("code", "0"));
		List ec_FlagPaymentType = this.getGeneralService().getDetachList(dFlagPaymentType);
		EnumConst FlagPaymentType = (EnumConst) ec_FlagPaymentType.get(0);
		EnumConst enumConstByFlagBatchPayState2 = this.getMyListService().getEnumConstByNamespaceCode("FlagBatchPayState", "3");
		Double sumHour = 0d;
		String pbtc_sql = "SELECT DISTINCT FK_COURSE_ID FROM PR_BZZ_TCH_OPENCOURSE WHERE FK_BATCH_ID = '" + id + "'";
		List pbtc_ids_list = this.getGeneralService().getBySQL(pbtc_sql);
		Object[] pbtc_ids_params = pbtc_ids_list.toArray();
		DetachedCriteria d2 = DetachedCriteria.forClass(PeBzzTchCourse.class);
		d2.add(Restrictions.in("id", pbtc_ids_params));
		List pbtc_list = this.getGeneralService().getDetachList(d2);
		for (int i = 0; i < pbtc_list.size(); i++) {
			sumHour += Double.parseDouble(((PeBzzTchCourse) pbtc_list.get(i)).getTime());
		}
		for (StudentBatch sb : stuBatchList) {
			PeBzzOrderInfo p = new PeBzzOrderInfo();
			p.setAmount("0");
			// 对账状态-不赋值
			p.setEnumConstByFlagCheckState(FlagCheckState);
			// 订单是否关闭-否
			p.setEnumConstByFlagOrderIsValid(FlagOrderIsValid);
			// 订单状态-正常
			p.setEnumConstByFlagOrderState(FlagOrderState);
			// 到账状态-未到账
			p.setEnumConstByFlagPaymentState(FlagPaymentState);
			// 订单类型-专项支付订单
			p.setEnumConstByFlagOrderType(FlagOrderType);
			// 支付方式-免费专项
			p.setEnumConstByFlagPaymentMethod(FlagPaymentMethod);
			// 支付类型-不赋值
			p.setEnumConstByFlagPaymentType(FlagPaymentType);
			// 应该可以不用赋值
			// p.setPeEnterprise(peEnterprise);
			p.setPeBzzBatch(peBzzBatchlist.get(0));
			p.setCreateDate(new Date());
			p.setPaymentDate(new Date());
			p.setClassHour(sumHour.toString());
			p.setCname("免费专项订单");
			p.setSeq(this.getGeneralService().getOrderSeq());
			p.setSsoUser(((PeBzzStudent) sb.getPeStudent()).getSsoUser());
			// 支付状态-无需支付
			sb.setEnumConstByFlagBatchPayState(enumConstByFlagBatchPayState2);
			sb.setPeBzzOrderInfo(p);
			// 订单
			try {
				this.getGeneralService().save(p);
			} catch (Exception e) {
				System.out.println(e);
			}
			// 专项
			try {
				this.getGeneralService().save(sb);
			} catch (Exception e) {
				System.out.println(e);
			}
			for (int i = 0; i < pbtc_list.size(); i++) {
				PeBzzTchCourse peBzzTchCourse1 = (PeBzzTchCourse) pbtc_list.get(i);
				PrBzzTchStuElective stuEle = new PrBzzTchStuElective();
				DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
				dc.add(Restrictions.eq("peBzzBatch.id", id));
				dc.add(Restrictions.eq("peBzzTchCourse.id", peBzzTchCourse1.getId()));
				List<PrBzzTchOpencourse> list = this.getGeneralService().getDetachList(dc);
				stuEle.setEnumConstByFlagElectivePayStatus(enumConstByFlagElectivePayStatus);
				stuEle.setPrBzzTchOpencourse(list.get(0));
				stuEle.setPeBzzStudent((PeBzzStudent) sb.getPeStudent());
				stuEle.setElectiveDate(new Date());
				stuEle.setPeBzzOrderInfo(p);
				PrBzzTchOpencourse prBzzTchOpencourse = (PrBzzTchOpencourse) list.get(0);
				TrainingCourseStudent trainingCourseStudent = new TrainingCourseStudent();
				trainingCourseStudent.setPrBzzTchOpencourse(prBzzTchOpencourse);
				trainingCourseStudent.setSsoUser(((PeBzzStudent) sb.getPeStudent()).getSsoUser());
				trainingCourseStudent.setPercent(0.00);
				trainingCourseStudent.setLearnStatus(StudyProgress.INCOMPLETE);
				trainingCourseStudent.setGetDate(new Date());
				trainingCourseStudent.setLearnStatus(StudyProgress.UNCOMPLETE);
				trainingCourseStudent.setPrBzzTchStuElective(stuEle);
				// 学习进度
				try {
					this.getGeneralService().save(trainingCourseStudent);
				} catch (Exception e) {
					System.out.println(e);
				}
				stuEle.setTrainingCourseStudent(trainingCourseStudent);
				// 学员选课
				try {
					this.getGeneralService().save(stuEle);
				} catch (Exception e) {
					System.out.println(e);
				}
				PrBzzTchStuElectiveBack eleBack = new PrBzzTchStuElectiveBack();
				eleBack.setPeBzzStudent(((PeBzzStudent) sb.getPeStudent()));
				eleBack.setPrBzzTchOpencourse(prBzzTchOpencourse);
				eleBack.setPeElectiveCoursePeriod(stuEle.getPeElectiveCoursePeriod());
				eleBack.setPeBzzOrderInfo(p);
				// 课程报名
				try {
					this.getGeneralService().save(eleBack);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
}
