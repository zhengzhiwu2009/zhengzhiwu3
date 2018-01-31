package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeBzzTchCourseware;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.TeacherInfo;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.teaching.basicInfo.PeTchCourseService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

/**
 * 在线直播课程发布Action 2014年11月04日
 * 
 * @author Lee
 * 
 */
public class PeBzzCourseLiveManagerAction extends MyBaseAction<PeBzzTchCourse> {

	private List peBzzTchCourses;
	private String tempFlag;
	private PeBzzTchCourseware peBzzTchCourseware;
	private PeTchCourseService peTchCourseService;
	private String courseware_id;
	private String courseware_code;
	private String courseware_name;
	private String scormType;
	private String indexFile;
	private String _uploadContentType; // 文件类型属性
	private EnumConstService enumConstService;
	private double price; // 课程的价格
	private Boolean delFlag;
	private String sacliveid;
	private String param_id;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * 初始化列表
	 * 
	 * @author Lee
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();
		boolean canAdd = false;
		boolean canUpdate = false;
		boolean canDelete = false;
		boolean canUseButton = false;
		if (capabilitySet.contains(this.servletPath + "_*.action"))
			canUseButton = true;
		/* 添加按钮 */
		if (capabilitySet.contains(this.servletPath + "_add.action") || canUseButton) {
			canAdd = true;
		}
		/* 删除按钮 */
		if (capabilitySet.contains(this.servletPath + "_delete.action") || canUseButton) {
			canDelete = true;
		}
		/* 双击修改 */
		if (capabilitySet.contains(this.servletPath + "_update.action") || canUseButton) {
			canUpdate = true;
		}
		this.getGridConfig().setCapability(canAdd, canDelete, canUpdate);
		this.getGridConfig().setTitle("在线直播课程管理");
		/* 隐藏字段用于传值 */
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		/* 显示在列表与查询条件中 */
		this.getGridConfig().addColumn(this.getText("课程编号"), "code", true, true, true, Const.coursecode_for_extjs);
		this.getGridConfig().addColumn(this.getText("课程名称"), "name", true, true, true, "TextField", false, 200, "");
		/* 是否发布 */
		ColumnConfig columnConfigIsvalid = new ColumnConfig(this.getText("是否发布"), "enumConstByFlagIsvalid.name", true, false, true,
				"TextField", false, 100, "");
		String sql3 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsvalid'";
		columnConfigIsvalid.setComboSQL(sql3);
		this.getGridConfig().addColumn(columnConfigIsvalid);
		this.getGridConfig().addColumn(this.getText("报名开始时间"), "signStartDatetime", false, true, true, Const.fee_for_extjs);
		this.getGridConfig().addColumn(this.getText("报名开始时间起"), "ssstartDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("报名开始时间止"), "ssendDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("报名结束时间"), "signEndDatetime", false, true, true, Const.fee_for_extjs);
		this.getGridConfig().addColumn(this.getText("报名结束时间起"), "sestartDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("报名结束时间止"), "seendDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("报名人数上限"), "maxStus", false, true, true, Const.oneFiveNum_for_extjs);
		ColumnConfig ccFlagSignType = new ColumnConfig(this.getText("报名范围"), "enumConstByFlagSignType.name", false, true, false,
				"TextField", false, 50, "");
		String sqlSignType = "SELECT A.ID ,A.NAME FROM ENUM_CONST A WHERE A.NAMESPACE='FlagSignType' ORDER BY TO_NUMBER(CODE) DESC";
		ccFlagSignType.setComboSQL(sqlSignType);
		this.getGridConfig().addColumn(ccFlagSignType);
		this.getGridConfig().addColumn(this.getText("预计学时数"), "estimateTime", false, false, false, "TextField", false, 10, "");
		this.getGridConfig().addColumn(this.getText("学时数"), "time", false, true, true, "TextField", false, 10, "");
		/* 仅显示在添加中的文本框 */
		this.getGridConfig().addColumn(this.getText("累计时长(分钟)"), "courseLen", false, true, false, Const.oneThreeNum_for_extjs, true, 100);
		/* 仅显示在添加中的下拉列表 */
		ColumnConfig ccFlagPassrole = new ColumnConfig(this.getText("通过规则"), "enumConstByFlagLivePassRole.name", false, true, false,
				"TextField", false, 50, "");
		String sqlFlagPassrole = "SELECT A.ID ,A.NAME FROM ENUM_CONST A WHERE A.NAMESPACE='FlagLivePassRole' ORDER BY TO_NUMBER(CODE) DESC";
		ccFlagPassrole.setComboSQL(sqlFlagPassrole);
		this.getGridConfig().addColumn(ccFlagPassrole);
		/* 显示在列表与查询条件中 */
		this.getGridConfig().addColumn(this.getText("金额(元)"), "price", false, true, true, "TextField", false, 50, "");
		this.getGridConfig().addColumn(this.getText("预计直播开始时间"), "estimateStartDatetime", false, true, true, Const.fee_for_extjs);
		this.getGridConfig().addColumn(this.getText("预计直播开始时间起"), "ysstartDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("预计直播开始时间止"), "ysendDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("预计直播结束时间"), "estimateEndDatetime", false, true, true, Const.fee_for_extjs);
		this.getGridConfig().addColumn(this.getText("预计直播结束时间起"), "yestartDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("预计直播结束时间止"), "yeendDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("直播链接(web)"), "liveUrl", false, true, false, "TextField", false, 2000, "");
		this.getGridConfig().addColumn(this.getText("直播链接(Client)"), "liveUrlClient", false, true, false, "TextField", false, 2000, "");
		/* 是否采集了数据 */
		ColumnConfig ccFlagLiveData = new ColumnConfig(this.getText("采集数据"), "enumConstByFlagLiveData.name", true, false, true,
				"TextField", false, 100, "");
		String sqlFlagLiveData = "SELECT A.ID ,A.NAME FROM ENUM_CONST A WHERE A.NAMESPACE='FlagLiveData' ORDER BY TO_NUMBER(CODE) DESC";
		ccFlagLiveData.setComboSQL(sqlFlagLiveData);
		this.getGridConfig().addColumn(ccFlagLiveData);
		/* 是否采集了数据 */
		ColumnConfig ccFlagFreeze = new ColumnConfig(this.getText("封版状态"), "enumConstByFlagFreeze.name", false, false, true, "TextField",
				false, 100, "");
		String sqlFlagFreeze = "SELECT A.ID ,A.NAME FROM ENUM_CONST A WHERE A.NAMESPACE='FlagFreeze' ORDER BY TO_NUMBER(CODE) DESC";
		ccFlagFreeze.setComboSQL(sqlFlagFreeze);
		this.getGridConfig().addColumn(ccFlagFreeze);
		this.getGridConfig().addColumn(this.getText("采集数据码值"), "LD", false, false, false, "");
		this.getGridConfig().addColumn(this.getText("封版数据码值"), "FE", false, false, false, "");
		/* 是否分配了学时 */
		ColumnConfig ccFlagLiveTime = new ColumnConfig(this.getText("分配学时"), "enumConstByFlagLiveTime.name", false, false, false,
				"TextField", false, 100, "");
		String sqlFlagLiveTime = "SELECT A.ID ,A.NAME FROM ENUM_CONST A WHERE A.NAMESPACE='FlagLiveTime' ORDER BY TO_NUMBER(CODE) DESC";
		ccFlagLiveTime.setComboSQL(sqlFlagLiveTime);
		this.getGridConfig().addColumn(ccFlagLiveTime);
		/* 课程性质-仅显示在添加中 */
		ColumnConfig ccFlagCoursetype = new ColumnConfig(this.getText("课程性质"), "enumConstByFlagCourseType.name", false, true, false,
				"TextField", false, 50, "");
		String sqlFlagCoursetype = "SELECT A.ID ,A.NAME FROM ENUM_CONST A WHERE A.NAMESPACE='FlagCourseType' ORDER BY TO_NUMBER(CODE) DESC";
		ccFlagCoursetype.setComboSQL(sqlFlagCoursetype);
		this.getGridConfig().addColumn(ccFlagCoursetype);
		/* 业务分类 */
		ColumnConfig ccFlagCoursecategory = new ColumnConfig(this.getText("业务分类"), "enumConstByFlagCourseCategory.name", false, true,
				false, "TextField", false, 50, "");
		String sqlFlagCoursecategory = "SELECT A.ID ,A.NAME FROM ENUM_CONST A WHERE A.NAMESPACE='FlagCourseCategory' ORDER BY TO_NUMBER(CODE) DESC";
		ccFlagCoursecategory.setComboSQL(sqlFlagCoursecategory);
		this.getGridConfig().addColumn(ccFlagCoursecategory);
		/* 大纲分类 */
		ColumnConfig ccFlagCourseitemtype = new ColumnConfig(this.getText("大纲分类"), "enumConstByFlagCourseItemType.name", false, true,
				false, "TextField", false, 50, "");
		String sqlFlagCourseitemtype = "SELECT A.ID ,A.NAME FROM ENUM_CONST A WHERE A.NAMESPACE='FlagCourseItemType' ORDER BY TO_NUMBER(CODE) DESC";
		ccFlagCourseitemtype.setComboSQL(sqlFlagCourseitemtype);
		this.getGridConfig().addColumn(ccFlagCourseitemtype);
		/* 内容属性分类 */
		ColumnConfig ccFlagContentProperty = new ColumnConfig(this.getText("内容属性分类"), "enumConstByFlagContentProperty.name", false, true,
				false, "TextField", false, 50, "");
		String sqlFlagContentProperty = "SELECT A.ID ,A.NAME FROM ENUM_CONST A WHERE A.NAMESPACE='FlagContentProperty' ORDER BY TO_NUMBER(CODE) DESC";
		ccFlagContentProperty.setComboSQL(sqlFlagContentProperty);
		this.getGridConfig().addColumn(ccFlagContentProperty);

		this.getGridConfig().addColumn(this.getText("通过规则描述"), "passRoleNote", false, true, false, "TextArea", true, 500);

		/* 学习建议 */
		this.getGridConfig().addColumn(this.getText("学习建议"), "suggestion", false, true, false, "TextArea", true, 500);
		/* 课程简介 */
		this.getGridConfig().addColumn(this.getText("课程简介"), "liveDesc", false, false, false, "TextArea", true, 500);
		/* 主讲人 */
		this.getGridConfig().addColumn(this.getText("主讲人"), "teacher", false, true, false, null);
		/* 主讲人简介 */
		this.getGridConfig().addColumn(this.getText("主讲人简介"), "teaNote", false, false, false, "TextArea", true, 500);
		/* 直播密码 */
		this.getGridConfig().addColumn(this.getText("直播密码"), "liveStuPwd", false, true, false, Const.coursecode_for_extjs);// 输入格式：长度为4-8位字母数字组合
		this.getGridConfig().addColumn(this.getText("直播专项id"), "batchid", false, false, false, "TextArea", true, 500);
		this.getGridConfig().addColumn(this.getText("课件id"), "coursewareId", false, false, false, "");
		this.getGridConfig().addColumn(this.getText("课件类型"), "scormType", false, false, false, "");
		this.getGridConfig().addColumn(this.getText("课件首页"), "indexFile", false, false, false, "");
		this.getGridConfig().addMenuFunction(this.getText("申请发布"), "checkStatus");
		this.getGridConfig().addMenuFunction(this.getText("同意发布"), "FlagIsvalid.true");
		this.getGridConfig().addMenuFunction(this.getText("拒绝发布"), "FlagIsvalid.false");
		this.getGridConfig().addMenuFunction(this.getText("直播封版"), "FreezeLive");
		this.getGridConfig().addMenuFunction(this.getText("设置课程图片"), "/entity/teaching/peBzzCourseLiveManager_setCourseImg.action", false,
				false);
		this.getGridConfig().addMenuFunction(this.getText("删除课程图片"), "/entity/teaching/peBzzCourseLiveManager_removeImg.action?param=c",
				false, false);
		this.getGridConfig().addMenuFunction(this.getText("设置讲师图片"), "/entity/teaching/peBzzCourseLiveManager_setTeacherImg.action", false,
				false);
		this.getGridConfig().addMenuFunction(this.getText("删除讲师图片"), "/entity/teaching/peBzzCourseLiveManager_removeImg.action?param=t",
				false, false);
		this.getGridConfig().addMenuFunction(this.getText("设置建议学习人群"), "/entity/teaching/peBzzCourseLiveManager_setSugg.action", false,
				false);
		this.getGridConfig().addMenuFunction(this.getText("删除建议学习人群"), "/entity/teaching/peBzzCourseLiveManager_removeSugg.action", false,
				false);
		if (capabilitySet.contains(this.servletPath + "_studentsManage.action")) {// 学员管理权限
			this
					.getGridConfig()
					.addRenderScript(
							this.getText("学员管理"),
							"{if(record.data['FE'] =='0'){return '<a href=\"/entity/basic/peBzzBatch_toImpStudents.action?id='+record.data['batchid']+'&actionUrl=live\" >导入</a>&nbsp;&nbsp;<a href=\"/entity/basic/peBatchDetail.action?batchid='+record.data['batchid']+'&method=student&actionUrl=live\">添加</a>&nbsp;&nbsp;<a href=\"/entity/basic/peBatchDetail.action?batchid='+record.data['batchid']+'&method=mystudent&actionUrl=live\">查看</a>';}else{return '<center><a href=\"/entity/basic/peBatchDetail.action?freeze='+record.data['FE']+'&batchid='+record.data['batchid']+'&method=mystudent&actionUrl=live\">查看</a></center>';}}",
							"id");
		}
		this
				.getGridConfig()
				.addRenderFunction(
						this.getText("学习资源"),
						"<a href=\"/sso/bzzinteraction_InteractioManage.action?course_id=${value}&teacher_id=teacher1&param=live\" target='_blank'>学习资源</a>",
						"id");
		if ("3".equals(us.getUserLoginType()) || capabilitySet.contains(this.servletPath + "_timeManage.action")) {// 协会管理员、学时管理权限
			this
					.getGridConfig()
					.addRenderScript(
							this.getText("操作"),
							"{return '<a href=\"/entity/teaching/peBzzCourseLiveManager_sacLiveView.action?id='+${value}+'\" target=\"_blank\">查看详细</a>&nbsp;&nbsp;<a href=\"/entity/basic/sacLiveDetail.action?freeze='+record.data['FE']+'&id='+${value}+'\">分配学时</a>';}",
							"id");
		}
		if ("2".equals(us.getUserLoginType()) || "4".equals(us.getUserLoginType())) {// 集体管理员
			this
					.getGridConfig()
					.addRenderScript(
							this.getText("操作"),
							"{return '<a href=\"/entity/teaching/peBzzCourseLiveManager_sacLiveView.action?id='+${value}+'\" target=\"_blank\">查看详细</a>';}",
							"id");
		}
		if (capabilitySet.contains(this.servletPath + "_dataManage.action")) {// 数据管理权限
			this
					.getGridConfig()
					.addRenderScript(
							this.getText("数据采集"),
							"{if(record.data['FE'] =='0'){return '<center><a href=\"/entity/basic/sacLive_getDataSource.action?id='+${value}+'\">采集</a>&nbsp;&nbsp;<a href=\"/entity/basic/sacLive_cleanDataSource.action?id='+${value}+'\">清空</a></center>'}else{return '<center>-</center>'}}",
							"id");
		}
		this
				.getGridConfig()
				.addRenderScript(
						this.getText("回看课件"),
						"{if(record.data['coursewareId'] == ''){return '&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"/entity/teaching/peBzzCourseLiveManager_toUploadCourseware.action?bean.id='+record.data['id']+'\" target=\"_blank\">导入</a>';} else return '<a href=/CourseImports/'+record.data['coursewareId']+'/'+record.data['scormType']+'/'+record.data['indexFile']+'?mydate='+ new Date().getTime() +' target=\"_blank\">预览</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=if(confirm(\"确定要删除吗\")){window.open(\"/entity/manager/course/courseware/scorm12/delete_scorm_info_live.jsp?courseware_id='+record.data['code']+'\")} >删除</a>'}",
						"id");
	}

	/**
	 * 课程详细信息
	 * 
	 * @author Lee
	 * @return
	 */
	public String sacLiveView() {
		String id = ServletActionContext.getRequest().getParameter("id");
		try {
			this.setBean(this.getGeneralService().getById(id));
			String sql = "SELECT TO_CHAR(WM_CONCAT(EC.NAME)) FS FROM PE_BZZ_TCH_COURSE_SUGGEST PBTCS, ENUM_CONST EC WHERE PBTCS.FK_ENUM_CONST_ID = EC.ID AND PBTCS.TABLE_NAME = 'PE_BZZ_TCH_COURSE' AND PBTCS.FK_COURSE_ID = '"
					+ id + "'";
			Object suggestName = "-";
			List suggestList = this.getGeneralService().getBySQL(sql);
			if (suggestList != null && suggestList.size() > 0)
				suggestName = suggestList.get(0);
			ServletActionContext.getRequest().setAttribute("suggestName", suggestName);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "sacLiveView";
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
			/* 设置源action用于设置讲师图片页面的返回按钮正确返回 */
			ServletActionContext.getRequest().getSession().setAttribute("fromAction", this.getServletPath());
		}
		return "setCourseImg";
	}

	/**
	 * 直播是否结束报名
	 * 
	 * @param id
	 *            直播ID
	 * @return
	 */
	private Boolean isEnd(String id) {
		String sql = "SELECT 1 FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "' AND SYSDATE BETWEEN SIGNSTARTDATE AND SIGNENDDATE";
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

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeBzzTchCourse.class;

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/teaching/peBzzCourseLiveManager";
	}

	public void setBean(PeBzzTchCourse instance) {
		super.superSetBean(instance);

	}

	public PeBzzTchCourse getBean() {
		return super.superGetBean();
	}

	/**
	 * 重写框架方法：添加数据
	 * 
	 * @author Lee
	 * @return
	 */
	public Map add() {
		Map map = new HashMap();
		String linkUrl = null;
		if (get_upload() != null) {
			linkUrl = super.saveUpload();
		}
		if (get_upload() != null && linkUrl == null) {
			map.put("success", "false");
			map.put("info", "文件上传失败");
			return map;
		}
		this.superSetBean((PeBzzTchCourse) setSubIds(this.getBean()));
		try {
			checkBeforeAdd();
			// 讲师校验
			List list = null;
			TeacherInfo teacherInfo = null;
			if (this.getBean().getTeacher() != null && !"".equals(this.getBean().getTeacher())) {
				String note_sql = "select t.content  as content from interaction_teachclass_info t where t.teachclass_id = '" + this.getBean().getId()
						+ "' and t.type = 'JSJJ'";
				String note = "";
				List note_list;
				
					note_list = this.getGeneralService().getBySQL(note_sql);
				
				if (note_list.size() > 0) {
					note = (String) note_list.get(0);
				}
				String hql = "from TeacherInfo where sg_tti_name = '" + this.getBean().getTeacher() + "' and sg_tti_is_hzph = '0'";
				try {
					list = this.getGeneralService().getByHQL(hql);
				} catch (EntityException e2) {
					e2.printStackTrace();
				}
				if (list != null && list.size() > 0) {
					teacherInfo = (TeacherInfo) list.get(0);
					if (teacherInfo.getSg_tti_is_hzph() == null || "".equals(teacherInfo.getSg_tti_is_hzph()) || "1".equals(teacherInfo.getSg_tti_is_hzph())) {
						String update_teacher = "update sg_train_terchaer_info set sg_tti_is_hzph = '0' where sg_tti_id = '" + teacherInfo.getId() + "'";
						this.getGeneralService().executeBySQL(update_teacher);
					}
				} else {
					String insert_sql = "insert into sg_train_terchaer_info(sg_tti_id,sg_tti_name,sg_tti_discription,sg_tti_data_type,"
							+ "sg_tti_is_complete,sg_tti_is_hzph,sg_tti_is_cyry,add_time) values (sys_guid(),'" + this.getBean().getTeacher() + "','" + note
							+ "','2','1','0','1',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'))";
					this.getGeneralService().executeBySQL(insert_sql);
					try {
						list = this.getGeneralService().getByHQL(hql);
					} catch (EntityException e) {
						e.printStackTrace();
					}
					teacherInfo = null;
					teacherInfo = (TeacherInfo) list.get(0);
				}
				this.getBean().setTeacherId(teacherInfo);
			}
		} catch (EntityException e1) {
			map.put("success", "false");
			map.put("info", e1.getMessage());
			return map;
		}
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		this.getBean().setCreateDate(new Date());
		this.getBean().setCreater(us.getId());
		/* 是否采集过数据 */
		EnumConst ecFlagLiveData = this.getEnumConstService().getByNamespaceCode("FlagLiveData", "0");
		this.getBean().setEnumConstByFlagLiveData(ecFlagLiveData);
		/* 是否分配完学时 */
		EnumConst ecFlagLiveTime = this.getEnumConstService().getByNamespaceCode("FlagLiveTime", "0");
		this.getBean().setEnumConstByFlagLiveTime(ecFlagLiveTime);
		/* 是否收费-是 */
		EnumConst ecFree = this.getEnumConstService().getByNamespaceCode("FlagIsFree", "0");
		this.getBean().setEnumConstByFlagIsFree(ecFree);
		/* 是否考试-否 */
		EnumConst ecExam = this.getEnumConstService().getByNamespaceCode("FlagIsExam", "0");
		this.getBean().setEnumConstByFlagIsExam(ecExam);
		/* 是否推荐-否 */
		EnumConst ecIsRecomm = this.getEnumConstService().getByNamespaceCode("FlagIsRecommend", "0");
		this.getBean().setEnumConstByFlagIsRecommend(ecIsRecomm);
		/* 是否有效-否 */
		EnumConst ec = this.getEnumConstService().getByNamespaceCode("FlagIsvalid", "0");
		this.getBean().setEnumConstByFlagIsvalid(ec);
		/* 是否下线-否 */
		EnumConst ec4 = this.getEnumConstService().getByNamespaceCode("FlagOffline", "0");
		this.getBean().setEnumConstByFlagOffline(ec4);
		/* 通过后是否可访问-否 */
		EnumConst ecIsvisitAfterPass = this.getEnumConstService().getByNamespaceCode("FlagIsvisitAfterPass", "0");
		this.getBean().setEnumConstByFlagIsvisitAfterPass(ecIsvisitAfterPass);
		/* 满意度是否必填-否 */
		EnumConst ecSatisfaction = this.getEnumConstService().getByNamespaceCode("FlagSatisfaction", "0");
		this.getBean().setEnumConstFlagSatisfaction(ecSatisfaction);
		/* 是否设置了建议学习人群-否 */
		EnumConst ecFlagSuggestSet = this.getEnumConstService().getByNamespaceCode("FlagSuggestSet", "0");
		this.getBean().setEnumConstFlagSuggestset(ecFlagSuggestSet);
		/* 课程审核状态-未申请 */
		EnumConst ec3 = this.getEnumConstService().getByNamespaceCode("FlagCheckStatus", "2");
		this.getBean().setEnumConstByFlagCheckStatus(ec3);
		/* 课程图片状态 */
		EnumConst ecFlagImg = this.getEnumConstService().getByNamespaceCode("FlagImg", "1");
		this.getBean().setEnumConstFlagImg(ecFlagImg);
		/* 主讲人照片状态 */
		EnumConst ecFlagTeaimg = this.getEnumConstService().getByNamespaceCode("FlagTeaimg", "0");
		this.getBean().setEnumConstFlagTeaimg(ecFlagTeaimg);
		/* 课程所属区域-在线直播课程 */
		EnumConst ecFlagCoursearea = this.getEnumConstService().getByNamespaceCode("FlagCoursearea", "0");
		this.getBean().setEnumConstByFlagCoursearea(ecFlagCoursearea);
		/* 直播封版状态-未封版 */
		EnumConst ecFlagFreeze = this.getEnumConstService().getByNamespaceCode("FlagFreeze", "0");
		this.getBean().setEnumConstByFlagFreeze(ecFlagFreeze);
		/* 预计学时数赋值为学时数 */
		this.getBean().setEstimateTime(this.getBean().getTime());
		/* 创建日期 */
		this.getBean().setCreateDate(new Date());
		/* 从直播链接中截取直播ID */
		String url = this.getBean().getLiveUrl();
		if (url.indexOf("join-") != -1) {
			url = url.substring(url.indexOf("join-") + 5);
		}
		this.getBean().setLiveId(url);
		try {
			/* 专项培训类型：直播专项 */
			EnumConst enumConstByFlagBatchType = this.getEnumConstService().getByNamespaceCode("FlagBatchType", "2");
			PeBzzBatch pbb = new PeBzzBatch();
			// 防止专项名称重复
			pbb.setName(this.getBean().getCode() + this.getBean().getName() + new Date());
			pbb.setEnumConstByFlagBatchType(enumConstByFlagBatchType);
			pbb.setStartDate(this.getBean().getSignStartDatetime());
			pbb.setEndDate(this.getBean().getSignEndDatetime());
			EnumConst enumConstByFlagDeploy = this.getEnumConstService().getByNamespaceCode("FlagDeploy", "0");// 专项发布状态为否
			pbb.setEnumConstByFlagDeploy(enumConstByFlagDeploy);
			/* 添加直播专项 */
			pbb = (PeBzzBatch) this.getGeneralService().save(pbb);
			PrBzzTchOpencourse prBzzTchOpencourse = new PrBzzTchOpencourse();
			prBzzTchOpencourse.setPeBzzTchCourse(this.getBean());
			prBzzTchOpencourse.setPeBzzBatch(pbb);
			// 直播为必选课程
			EnumConst enumConstByFlagChoose = this.getEnumConstService().getByNamespaceCode("FlagChoose", "1");
			prBzzTchOpencourse.setEnumConstByFlagChoose(enumConstByFlagChoose);
			/* 保存课程 */
			this.setBean(this.getPeTchCourseService().add(prBzzTchOpencourse).getPeBzzTchCourse());
			map.put("success", "true");
			map.put("info", "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return this.checkAlternateKey(e, "添加");
		}
		return map;
	}

	/**
	 * 重写框架方法：修改数据前的校验
	 * 
	 * @author lzh
	 * @return
	 * @throws EntityException
	 */
	public int training() {
		int count = 0;
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List<String> idList = new ArrayList<String>();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}
				String idss = "";
				for (int j = 0; j < idList.size(); j++) {
					if (j == idList.size() - 1) {
						idss += "'" + idList.get(j).toString() + "'";
					} else {
						idss += "'" + idList.get(j).toString() + "'" + ",";
					}

				}
				String sql = "SELECT 0 FROM DUAL WHERE 1 = 1";
				try {
					if (!"0".equals(this.getGeneralService().getBySQL(sql).get(0).toString()))
						count = 1;
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return count;
	}

	/**
	 * 重写框架方法：添加数据前的校验
	 * 
	 * @author Lee
	 * @return
	 */
	public void checkBeforeAdd() throws EntityException {
		DetachedCriteria dc2 = DetachedCriteria.forClass(PeBzzTchCourse.class);
		dc2.add(Restrictions.eq("code", this.getBean().getCode()));
		List courseList = this.getGeneralService().getList(dc2);
		if (courseList != null && courseList.size() > 0) {
			throw new EntityException("课程编号已经存在");
		}
		if (this.get_upload() != null && isImageFile(this.get_upload().getName().toLowerCase())) {
			throw new EntityException("照片不是图片文件，请选择一个图片文件！");
		}
	}

	/**
	 * 重写框架方法：更新数据前的校验
	 * 
	 * @author Lee
	 * @return
	 */
	public void checkBeforeUpdate() throws EntityException {
		if (this.get_upload() != null && !isImageFile(this.get_uploadFileName().toLowerCase())) {
			throw new EntityException("照片不是图片文件，请选择一个图片文件！");
		}
		this.getBean().setEditDate(new Date());
	}

	/**
	 * 是否图片
	 * 
	 * @author Lee
	 * @return
	 */
	private boolean isImageFile(String fileName) {
		if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".gif") || fileName.toLowerCase().endsWith(".png")
				|| fileName.toLowerCase().endsWith(".bmp") || fileName.toLowerCase().endsWith(".jpeg")
				|| fileName.toLowerCase().endsWith(".tiff")) {
			return true;
		}
		return false;
	}

	/**
	 * 重写框架方法：删除数据前的校验
	 * 
	 * @author Lee
	 * @return
	 */
	public void checkBeforeDelete(List idList) throws EntityException {
		// 检查是否已经被选
		DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
		dc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createCriteria("peBzzTchCourse", "peBzzTchCourse");
		dc.add(Restrictions.in("peBzzTchCourse.id", idList));
		try {
			List<PrBzzTchStuElective> electivelist = this.getGeneralService().getList(dc);
			if (electivelist.size() > 0) {
				throw new EntityException("所选课程中存在选课记录");
			}
		} catch (EntityException e) {
			// e.printStackTrace();
			throw e;
		}

	}

	/**
	 * 重写框架方法：删除数据
	 * 
	 * @author Lee
	 * @return
	 */
	public Map delete() {
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List<String> idList = new ArrayList<String>();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}
				try {
					checkBeforeDelete(idList);
					DetachedCriteria dcCourse = DetachedCriteria.forClass(PeBzzTchCourse.class);
					dcCourse.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
					dcCourse.add(Restrictions.in("id", idList));
					List<PeBzzTchCourse> cList = this.getGeneralService().getList(dcCourse);
					String ids_s = "";
					for (int i = 0; i < cList.size(); i++) {
						ids_s += "'" + cList.get(i).getCode().toString() + "',";
					}
					ids_s = ids_s.substring(0, ids_s.length() - 1);
					for (PeBzzTchCourse p : cList) {
						if (p.getEnumConstByFlagIsvalid().getCode().equals("1")) {
							throw new EntityException("请先将课程置于无效状态");
						}
					}
				} catch (EntityException e) {
					map.put("success", "false");
					map.put("info", e.getMessage() + "，无法删除！");
					return map;
				}

				// 执行删除
				try {
					String idss = "";
					for (int j = 0; j < idList.size(); j++) {
						idss += "'" + idList.get(j).toString() + "'";
					}
					/**
					 * 20130125添加，用于删除可能存在于统计表中的课程
					 */
					String sqlSc = "delete from statistic_courses sc where sc.fk_course_id in (" + idss + ")";
					this.getGeneralService().executeBySQL(sqlSc);
					int i = this.getPeTchCourseService().deleteCourse(idList);
					map.put("success", "true");
					map.put("info", "共有" + ids.length + "门课程删除成功");
					if (0 == i) {
						map.clear();
						map.put("success", "false");
						map.put("info", "删除失败");
					}
				} catch (RuntimeException e) {
					return this.checkForeignKey(e);
				} catch (Exception e1) {
					map.clear();
					map.put("success", "false");
					map.put("info", "删除失败");
				}
			} else {
				map.put("success", "false");
				map.put("info", "请选择操作项");
			}
		}
		return map;
	}

	/**
	 * 加载课程信息
	 * 
	 * @author Lee
	 * @return
	 */
	public String abstractDetail() {
		Page page = null;
		Map map = new HashMap();
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzTchCourse.class);
		// 报名范围
		dc.createCriteria("enumConstByFlagSignType", "enumConstByFlagSignType");
		// 通过规则
		dc.createCriteria("enumConstByFlagLivePassRole", "enumConstByFlagLivePassRole");
		// 课程性质
		dc.createCriteria("enumConstByFlagCourseType", "enumConstByFlagCourseType");
		// 业务分类
		dc.createCriteria("enumConstByFlagCourseCategory", "enumConstByFlagCourseCategory");
		// 大纲分类
		dc.createCriteria("enumConstByFlagCourseItemType", "enumConstByFlagCourseItemType");
		// 内容属性分类
		dc.createCriteria("enumConstByFlagContentProperty", "enumConstByFlagContentProperty");
		// 是否采集了采集数据
		dc.createCriteria("enumConstByFlagLiveData", "enumConstByFlagLiveData");
		// 是否分配了分配学时
		dc.createCriteria("enumConstByFlagLiveTime", "enumConstByFlagLiveTime");
		// 主键id
		dc.add(Restrictions.eq("id", this.getBean().getId()));
		try {
			page = this.getGeneralService().getByPage(dc, Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
			String id = this.getBean().getId();

			if (page != null) {
				page.setItems(JsonUtil.ArrayToJsonObjects(page.getItems(), this.getGridConfig().getListColumnConfig()));
				Container o = new Container();
				o.setBean(page.getItems().get(0));
				List list = new ArrayList();
				list.add(o);
				map.put("totalCount", 1);
				map.put("models", list);
			}
			JsonUtil.setDateformat("yyyy-MM-dd HH:mm:ss");
			this.setJsonString(JsonUtil.toJSONString(map));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return json();
	}

	/**
	 * 课程管理列表
	 * 
	 * @author Lee
	 * @return
	 */
	public Page list() {
		Page page = null;
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("SELECT * FROM (");
			sb.append(" SELECT A.ID, ");
			sb.append("        A.CODE, ");
			sb.append("        A.NAME, ");
			sb.append("        EC5.NAME VALIDNAME, ");
			sb.append("        TO_CHAR(A.SIGNSTARTDATE, 'yyyy-MM-dd hh24:mi') SIGNSTARTDATETIME, ");
			sb.append("        '' ssstartDatetime, ");
			sb.append("        '' ssendDatetime, ");
			sb.append("        TO_CHAR(A.SIGNENDDATE, 'yyyy-MM-dd hh24:mi') SIGNENDDATETIME, ");
			sb.append("        '' sestartDatetime, ");
			sb.append("        '' seendDatetime, ");
			sb.append("        A.MAXSTUS, ");
			sb.append("        EC9.NAME SIGNTYPE, ");
			sb.append("        A.ESTIMATETIME, ");
			sb.append("        A.TIME, ");
			sb.append("        A.COURSE_LEN COURSELEN, ");
			sb.append("        EC6.NAME PASSROLE, ");
			sb.append("        PRICE, ");
			sb.append("        TO_CHAR(A.ESTIMATESTARTDATE, 'yyyy-MM-dd hh24:mi') ESTIMATESTARTDATETIME, ");
			sb.append("        '' YSSTARTDATETIME, ");
			sb.append("        '' YSENDDATETIME, ");
			sb.append("        TO_CHAR(A.ESTIMATEENDDATE, 'yyyy-MM-dd hh24:mi') ESTIMATEENDDATETIME, ");
			sb.append("        '' YESTARTDATETIME, ");
			sb.append("        '' YEENDDATETIME, ");
			sb.append("        A.LIVEURL, ");
			sb.append("        A.LIVEURLCLIENT, ");
			sb.append("        EC12.NAME LIVEDATA, ");
			sb.append("        EC14.NAME FREEZE, ");
			sb.append("        EC12.CODE LD, ");
			sb.append("        EC14.CODE FE, ");
			sb.append("        EC13.NAME LIVETIME, ");
			sb.append("        EC1.NAME COURSETYPE, ");
			sb.append("        EC2.NAME COURSECATEGORY, ");
			sb.append("        EC3.NAME COURSEITEMTYPE, ");
			sb.append("        EC4.NAME CONTENTPROPERTY, ");
			sb.append("        A.PASSROLE_NOTE PASSROLENOTE, ");
			sb.append("        SUGGESTION, ");
			sb.append("        A.LIVEDESC, ");
			sb.append("        A.TEACHER, ");
			sb.append("        A.TEA_NOTE TEANOTE, ");
			sb.append("        A.LIVESTUPWD, ");
			sb.append("        PBTO.FK_BATCH_ID BATCHID, ");
			sb.append("        C.COURSE_ID COURSEWAREID, ");
			sb.append("        C.SCORM_TYPE, ");
			sb.append("        C.INDEXFILE ");
			sb.append("   FROM PE_BZZ_TCH_COURSE A ");
			sb.append("	  INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
			sb.append("	    ON A.ID = PBTO.FK_COURSE_ID ");
			sb.append("   INNER JOIN PE_BZZ_BATCH PBB ");
			sb.append("     ON PBTO.FK_BATCH_ID = PBB.ID ");
			sb.append("   INNER JOIN ENUM_CONST ECBATCH ");
			sb.append("     ON PBB.FLAG_BATCH_TYPE = ECBATCH.ID ");
			sb.append("     AND ECBATCH.CODE = '2' ");
			sb.append("   INNER JOIN ENUM_CONST EC1 ");
			sb.append("     ON A.FLAG_COURSETYPE = EC1.ID ");
			sb.append("   INNER JOIN ENUM_CONST EC2 ");
			sb.append("     ON A.FLAG_COURSECATEGORY = EC2.ID ");
			sb.append("   INNER JOIN ENUM_CONST EC3 ");
			sb.append("     ON A.FLAG_COURSE_ITEM_TYPE = EC3.ID ");
			sb.append("   INNER JOIN ENUM_CONST EC4 ");
			sb.append("     ON A.FLAG_CONTENT_PROPERTY = EC4.ID ");
			sb.append("   INNER JOIN ENUM_CONST EC5 ");
			sb.append("     ON A.FLAG_ISVALID = EC5.ID ");
			sb.append("   INNER JOIN ENUM_CONST EC6 ");
			sb.append("     ON A.FLAG_LIVEPASSROLE = EC6.ID ");
			sb.append("   INNER JOIN ENUM_CONST EC9 ");
			sb.append("     ON A.FLAG_SIGNTYPE = EC9.ID ");
			sb.append("   INNER JOIN ENUM_CONST EC10 ");
			sb.append("     ON A.FLAG_OFFLINE = EC10.ID ");
			sb.append("   INNER JOIN ENUM_CONST EC12 ");
			sb.append("     ON A.FLAG_LIVEDATA = EC12.ID ");
			sb.append("   INNER JOIN ENUM_CONST EC13 ");
			sb.append("     ON A.FLAG_LIVETIME = EC13.ID ");
			sb.append("   INNER JOIN ENUM_CONST EC14 ");
			sb.append("     ON A.FLAG_FREEZE = EC14.ID ");
			sb.append("   LEFT JOIN SCORM_SCO_LAUNCH C ");
			sb.append("     ON A.CODE = C.COURSE_ID ");
			sb.append("   LEFT JOIN SCORM_TYPE D ");
			sb.append("     ON C.SCORM_TYPE = D.CODE ");
			sb.append("  WHERE A.FLAG_COURSEAREA = 'Coursearea0' ");// 直播课程
			/* 时间查询条件 */
			ActionContext context = ActionContext.getContext();
			if (context.getParameters() != null) {
				Map params = this.getParamsMap();
				if (params != null) {
					/* 报名开始时间起止 */
					if (params.get("search__ssstartDatetime") != null) {
						String[] startDate = (String[]) params.get("search__ssstartDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sb.append(" AND A.SIGNSTARTDATE >= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__ssstartDatetime");
						}
					}
					if (params.get("search__ssendDatetime") != null) {
						String[] startDate = (String[]) params.get("search__ssendDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sb.append(" AND A.SIGNSTARTDATE <= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__ssendDatetime");
						}
					}
					/* 报名结束时间起止 */
					if (params.get("search__sestartDatetime") != null) {
						String[] startDate = (String[]) params.get("search__sestartDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sb.append(" AND A.SIGNENDDATE >= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__sestartDatetime");
						}
					}
					if (params.get("search__seendDatetime") != null) {
						String[] startDate = (String[]) params.get("search__seendDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sb.append(" AND A.SIGNENDDATE <= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__seendDatetime");
						}
					}
					/* 预计开始时间起止 */
					if (params.get("search__ysstartDatetime") != null) {
						String[] startDate = (String[]) params.get("search__ysstartDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sb.append(" AND A.ESTIMATESTARTDATE >= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__ysstartDatetime");
						}
					}
					if (params.get("search__ysendDatetime") != null) {
						String[] startDate = (String[]) params.get("search__ysendDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sb.append(" AND A.ESTIMATESTARTDATE <= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__ysendDatetime");
						}
					}
					/* 预计结束时间起止 */
					if (params.get("search__yestartDatetime") != null) {
						String[] startDate = (String[]) params.get("search__yestartDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sb.append(" AND A.ESTIMATEENDDATE >= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__yestartDatetime");
						}
					}
					if (params.get("search__yeendDatetime") != null) {
						String[] startDate = (String[]) params.get("search__yeendDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sb.append(" AND A.ESTIMATEENDDATE <= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__yeendDatetime");
						}
					}
					context.setParameters(params);
				}
			}
			sb.append(" ORDER BY A.CREATE_DATE DESC ) WHERE 1 = 1");
			/* 下拉列表查询条件 */
			Map params1 = this.getParamsMap();
			Iterator iterator = params1.entrySet().iterator();
			do {
				if (!iterator.hasNext())
					break;
				java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
				String name = entry.getKey().toString();
				String value = ((String[]) entry.getValue())[0].toString();
				if (!name.startsWith("search__"))
					continue;
				if ("".equals(value))
					continue;
				if (name.indexOf(".") > 1 && name.indexOf(".") != name.lastIndexOf(".")) {
					name = name.substring(name.lastIndexOf(".", name.lastIndexOf(".") - 1) + 1);
				} else {
					name = name.substring(8);
				}
				/* 是否发布 */
				if (name.equals("enumConstByFlagIsvalid.name")) {
					name = "VALIDNAME";
				}
				/* 采集数据 */
				if (name.equals("enumConstByFlagLiveData.name")) {
					name = "LIVEDATA";
				}
				if ("VALIDNAME".equalsIgnoreCase(name) || "LIVEDATA".equalsIgnoreCase(name) || "FLAGSUGGESTSET".equals(name))
					sb.append(" and " + name + " = '" + value + "'");
				else
					sb.append(" and UPPER(" + name + ") LIKE '%" + value.toUpperCase() + "%'");
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				if (!temp.equalsIgnoreCase("id")) {
					/* 是否发布 */
					if (temp.equals("enumConstByFlagIsvalid.name")) {
						temp = "VALIDNAME";
					}
					/* 采集数据 */
					if (temp.equals("enumConstByFlagLiveData.name")) {
						temp = "LIVEDATA";
					}
					/* 封版状态 */
					if (temp.equals("enumConstByFlagFreeze.name")) {
						temp = "FREEZE";
					}
					if (temp.equals("maxStus") || temp.equals("time") || temp.equals("price")) {
						temp = " to_number(" + temp + ")";
					}
					if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
						sb.append(" order by " + temp + " desc ");
					} else {
						sb.append(" order by " + temp + " asc ");
					}
				}
			}
			page = this.getGeneralService().getByPageSQL(sb.toString(), Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 重写框架方法：更新字段前的校验
	 * 
	 * @author Lee
	 * @return
	 */
	@Override
	public void checkBeforeUpdateColumn(List idList) throws EntityException {
		int count = 0;
		String sql = "";
		try {
			for (int i = 0; i < idList.size(); i++) {
				sql = "select 1 from pe_bzz_tch_course pbtc where pbtc.flag_check_status = '40288acf3d62b37f013d62b9aeec0002' and pbtc.id = '"
						+ idList.get(i) + "'";
				List list = this.getGeneralService().getBySQL(sql);
				if (list.size() > 0) {
					count++;
				}
			}
			if (count > 0) {
				throw new EntityException("所选课程中有未申请发布课程，请申请发布后再执行发布操作！");
			}
			for (int i = 0; i < idList.size(); i++) {
				sql = "select p.id from pe_bzz_tch_course p  where p.id ='" + idList.get(i) + "' and p.flag_isvalid='2'";
				List list = this.getGeneralService().getBySQL(sql);
				if (list.size() > 0) {
					count++;
				}
			}
			if (count > 0) {
				throw new EntityException("所选课程中有课程已经发布，请重新选择后再发布！");
			}
		} catch (EntityException e) {
			throw e;
		}
	}

	/**
	 * 重写框架方法：更新字段
	 * 
	 * @author Lee
	 * @return
	 */
	@SuppressWarnings( { "unchecked", "unchecked", "unchecked" })
	public Map updateColumn() {
		int count = training();
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Map map = new HashMap();
		String msg = "";
		int existNum = 0;
		String action = this.getColumn();

		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");

			List idList = new ArrayList();
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			try {
				DetachedCriteria tempdc = DetachedCriteria.forClass(PeBzzTchCourse.class);
				tempdc.createCriteria("enumConstByFlagIsvalid");
				if (count != 0) {
					map.put("info", "不能进行此操作");
				} else {
					// 同意发布
					if (action.equals("FlagIsvalid.true")) {
						EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1");
						tempdc.add(Restrictions.in("id", ids));
						List<PeBzzTchCourse> courselist = new ArrayList<PeBzzTchCourse>();
						courselist = this.getGeneralService().getList(tempdc);
						try {
							checkBeforeUpdateColumn(idList);
						} catch (EntityException e1) {
							map.put("success", "false");
							map.put("info", e1.getMessage());
							return map;
						}
						Iterator<PeBzzTchCourse> iterator = courselist.iterator();
						while (iterator.hasNext()) {
							PeBzzTchCourse peBzzTchCourse = iterator.next();
							if (!"2".equals(peBzzTchCourse.getEnumConstByFlagIsvalid().getCode())) {
								map.clear();
								map.put("success", "false");
								map.put("info", "操作失败,只有课程发布待审核状态才能同意发布");
								return map;
							}
							String logs = peBzzTchCourse.getOperationLogs();
							if (logs == null) {
								logs = "";
							}
							StringBuffer buf = new StringBuffer(logs);
							buf.append("|" + us.getLoginId() + "/" + us.getUserName() + "执行了课程发布操作\n");
							peBzzTchCourse.setOperationLogs(buf.toString());

							peBzzTchCourse.setEnumConstByFlagIsvalid(enumConst);
							peBzzTchCourse.setAnnounceDate(new Date());
							peBzzTchCourse.setEditDate(new Date());
							this.getGeneralService().save(peBzzTchCourse);
							String pbbSql = "UPDATE PE_BZZ_BATCH PBB SET PBB.FLAG_DEPLOY = 'deploy1' WHERE PBB.ID IN (SELECT FK_BATCH_ID FROM PR_BZZ_TCH_OPENCOURSE WHERE FK_COURSE_ID = '"
									+ peBzzTchCourse.getId() + "')";
							this.getGeneralService().executeBySQL(pbbSql);// 更新专项发布状态为是
						}
						msg = "同意发布";
					}
					// 拒绝发布
					if (action.equals("FlagIsvalid.false")) {

						try {
							checkBeforeDelete(idList);
						} catch (EntityException e) {
							map.put("success", "false");
							map.put("info", e.getMessage() + "，无法设为无效！");
							return map;
						}

						EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "0");
						tempdc.add(Restrictions.in("id", ids));
						List<PeBzzTchCourse> courselist = new ArrayList<PeBzzTchCourse>();
						courselist = this.getGeneralService().getList(tempdc);
						Iterator<PeBzzTchCourse> iterator = courselist.iterator();
						while (iterator.hasNext()) {
							PeBzzTchCourse peBzzTchCourse = iterator.next();
							if (!"2".equals(peBzzTchCourse.getEnumConstByFlagIsvalid().getCode())) {
								map.clear();
								map.put("success", "false");
								map.put("info", "操作失败,只有课程发布待审核状态才能拒绝发布");
								return map;
							}
							String logs = peBzzTchCourse.getOperationLogs();
							if (logs == null) {
								logs = "";
							}
							StringBuffer buf = new StringBuffer(logs);
							buf.append("|" + us.getLoginId() + "/" + us.getUserName() + "执行了课程停用操作\n");
							peBzzTchCourse.setOperationLogs(buf.toString());
							peBzzTchCourse.setEnumConstByFlagIsvalid(enumConst);
							peBzzTchCourse.setEditDate(new Date());
							this.getGeneralService().save(peBzzTchCourse);
							String pbbSql = "UPDATE PE_BZZ_BATCH PBB SET PBB.FLAG_DEPLOY = 'deploy0' WHERE PBB.ID IN (SELECT FK_BATCH_ID FROM PR_BZZ_TCH_OPENCOURSE WHERE FK_COURSE_ID = '"
									+ peBzzTchCourse.getId() + "')";
							this.getGeneralService().executeBySQL(pbbSql);// 更新专项发布状态为是
						}
						msg = "拒绝发布";
					}
					// 直播封版
					if (action.equals("FreezeLive")) {
						EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagFreeze", "1");
						tempdc.add(Restrictions.in("id", ids));
						List<PeBzzTchCourse> courselist = new ArrayList<PeBzzTchCourse>();
						courselist = this.getGeneralService().getList(tempdc);
						Iterator<PeBzzTchCourse> iterator = courselist.iterator();
						while (iterator.hasNext()) {
							PeBzzTchCourse peBzzTchCourse = iterator.next();
							if ("0".equals(peBzzTchCourse.getEnumConstByFlagLiveData().getCode())) {
								map.clear();
								map.put("success", "false");
								map.put("info", "操作失败,只有已采集数据的直播才可以执行封版操作");
								return map;
							}
							if ("1".equals(peBzzTchCourse.getEnumConstByFlagFreeze().getCode())) {
								map.clear();
								map.put("success", "false");
								map.put("info", "操作失败,直播已封版,请勿重复操作");
								return map;
							}
							peBzzTchCourse.setEnumConstByFlagFreeze(enumConst);
							peBzzTchCourse.setEditDate(new Date());
							this.getGeneralService().save(peBzzTchCourse);
							StringBuffer sb = new StringBuffer();
							sb.append(" UPDATE TRAINING_COURSE_STUDENT TCS ");
							sb.append("    SET TCS.LEARN_STATUS = 'INCOMPLETE' ");
							sb.append("  WHERE TCS.ID IN (SELECT FK_TRAINING_ID ");
							sb.append("                     FROM PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
							sb.append("                    WHERE FK_TCH_OPENCOURSE_ID IN ");
							sb.append("                          (SELECT ID ");
							sb.append("                             FROM PR_BZZ_TCH_OPENCOURSE ");
							sb.append("                            WHERE FK_COURSE_ID IN ('')) ");
							sb.append("                      AND PBTSE.ISPASS = '0') ");
							this.getGeneralService().executeBySQL(sb.toString());
						}
						msg = "直播封版";
					}
					// 申请发布
					if (action.equals("checkStatus")) {
						EnumConst enumConstFlagIsvalid = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "2"); // 课程审核状态，待审核
						tempdc.add(Restrictions.in("id", ids));
						List<PeBzzTchCourse> courselist = new ArrayList<PeBzzTchCourse>();
						courselist = this.getGeneralService().getList(tempdc);
						Iterator<PeBzzTchCourse> iterator = courselist.iterator();
						while (iterator.hasNext()) {
							PeBzzTchCourse peBzzTchCourse = iterator.next();
							String logs = peBzzTchCourse.getOperationLogs();
							if (logs == null) {
								logs = "";
							}
							StringBuffer buf = new StringBuffer(logs);
							buf.append("|" + us.getLoginId() + "/" + us.getUserName() + "执行了课程申请发布操作\n");
							peBzzTchCourse.setOperationLogs(buf.toString());
							peBzzTchCourse.setEnumConstByFlagIsvalid(enumConstFlagIsvalid);
							peBzzTchCourse.setEditDate(new Date());
							this.getGeneralService().save(peBzzTchCourse);
						}
						msg = "申请发布";
					}
					map.put("success", "true");
					map.put("info", "共有" + ids.length + "门课程" + msg + "操作成功");
				}
			} catch (Exception e) {
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

	@SuppressWarnings( { "unchecked", "unchecked", "unchecked" })
	public Map update() {
		Map map = new HashMap();
		String linkUrl = null;
		if (get_upload() != null) {
			linkUrl = saveUpload();
		}
		if (get_upload() != null && linkUrl == null) {
			map.put("success", "false");
			map.put("info", "文件上传失败");
			return map;
		}
		PeBzzTchCourse dbInstance = null;
		try {
			checkBeforeUpdate();
		} catch (EntityException e1) {
			try {
				this.getGeneralService().saveError();
			} catch (EntityException e2) {
			}
			map.put("success", "false");
			map.put("info", e1.getMessage());
			return map;
		}
		try {
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			dbInstance = this.getGeneralService().getById(this.getBean().getId());
		} catch (EntityException e1) {
			map.put("success", "false");
			map.put("info", "更新失败");
			return map;
		}
		this.setBean((PeBzzTchCourse) setSubIds(dbInstance, this.getBean()));
		PeBzzTchCourse instance = null;
		try {
			// 讲师校验
			List list = null;
			TeacherInfo teacherInfo = null;
			if (this.getBean().getTeacher() != null && !"".equals(this.getBean().getTeacher())
					&& "1".equals(this.getBean().getEnumConstByFlagIsvalid().getCode())) {
				String note_sql = "select t.content  as content from interaction_teachclass_info t where t.teachclass_id = '"
						+ this.getBean().getId() + "' and t.type = 'JSJJ'";
				String note = "";
				List note_list = this.getGeneralService().getBySQL(note_sql);
				if (note_list.size() > 0) {
					note = (String) note_list.get(0);
				}
				String hql = "from TeacherInfo where sg_tti_name = '" + this.getBean().getTeacher() + "'";
				try {
					list = this.getGeneralService().getByHQL(hql);
				} catch (EntityException e2) {
					e2.printStackTrace();
				}
				if (list != null && list.size() > 0) {
					teacherInfo = (TeacherInfo) list.get(0);
					if (teacherInfo.getSg_tti_is_hzph() == null || "".equals(teacherInfo.getSg_tti_is_hzph())
							|| "1".equals(teacherInfo.getSg_tti_is_hzph())) {
						String update_teacher = "update sg_train_terchaer_info set sg_tti_is_hzph = '0' where sg_tti_id = '"
								+ teacherInfo.getId() + "'";
						this.getGeneralService().executeBySQL(update_teacher);
					}
				} else {
					String insert_sql = "insert into sg_train_terchaer_info(sg_tti_id,sg_tti_name,sg_tti_discription,sg_tti_data_type,"
							+ "sg_tti_is_complete,sg_tti_is_hzph,sg_tti_is_cyry,add_time) values (sys_guid(),'"
							+ this.getBean().getTeacher() + "','" + note + "','2','1','0','1',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'))";
					this.getGeneralService().executeBySQL(insert_sql);
					try {
						list = this.getGeneralService().getByHQL(hql);
					} catch (EntityException e) {
						e.printStackTrace();
					}
					teacherInfo = null;
					teacherInfo = (TeacherInfo) list.get(0);
				}
				this.getBean().setTeacherId(teacherInfo);
			}
			// 同步专项报名结束时间和名称
			DetachedCriteria dcOpen = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
			dcOpen.createAlias("peBzzTchCourse", "peBzzTchCourse");
			dcOpen.createAlias("peBzzBatch", "peBzzBatch");
			dcOpen.add(Restrictions.eq("peBzzTchCourse.id", this.getBean().getId()));
			PrBzzTchOpencourse prBzzTchOpencourse = (PrBzzTchOpencourse) this.getGeneralService().getDetachList(dcOpen).get(0);
			PeBzzBatch pbBatch = prBzzTchOpencourse.getPeBzzBatch();
			pbBatch.setStartDate(this.getBean().getSignStartDatetime());
			pbBatch.setEndDate(this.getBean().getSignEndDatetime());
			pbBatch.setName(this.getBean().getCode() + this.getBean().getName() + new Date());
			/* 从直播链接中截取直播ID */
			String url = this.getBean().getLiveUrl();
			if (url.indexOf("join-") != -1) {
				url = url.substring(url.indexOf("join-") + 5);
			}
			this.getBean().setLiveId(url);
			// 保存课程
			instance = this.getGeneralService().save(this.getBean());
			// 保存专项
			this.getGeneralService().save(pbBatch);
			map.put("success", "true");
			map.put("info", "更新成功");
		} catch (Exception e) {
			return this.checkAlternateKey(e, "更新");
		}
		return map;
	}

	/**
	 * 上传课件
	 * 
	 * @author Lee
	 * @return
	 */
	public String toUploadCourseware() {
		if (!this.hasCourseware()) {
			this.createCourseware();
		}
		this.setCourseware_id(this.getPeBzzTchCourseware().getId());
		this.setCourseware_code(this.getPeBzzTchCourseware().getCode());
		this.setCourseware_name(this.getPeBzzTchCourseware().getName());

		return "toUploadCourseware";
	}

	/**
	 * 创建课件
	 * 
	 * @author Lee
	 * @return
	 */
	public void createCourseware() {
		this.peBzzTchCourseware = new PeBzzTchCourseware();
		this.peBzzTchCourseware.setPeBzzTchCourse(this.getBean());
		this.peBzzTchCourseware.setCode(this.getBean().getCode());
		this.peBzzTchCourseware.setName(this.getBean().getName());
		try {
			this.peBzzTchCourseware = this.getPeTchCourseService().savePeBzzTchCourseware(this.peBzzTchCourseware);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 判断课程是否有课件
	 * 
	 * @author Lee
	 * @return
	 */
	public boolean hasCourseware() {
		try {
			this.setBean(this.getGeneralService().getById(this.getBean().getId()));
			DetachedCriteria dCourseware = DetachedCriteria.forClass(PeBzzTchCourseware.class);
			dCourseware.createAlias("peBzzTchCourse", "peBzzTchCourse");
			dCourseware.add(Restrictions.eq("peBzzTchCourse.id", this.getBean().getId()));

			List coursewareList = this.getGeneralService().getList(dCourseware);
			if (coursewareList != null && coursewareList.size() > 0) {
				this.peBzzTchCourseware = (PeBzzTchCourseware) coursewareList.get(0);
				return true;
			} else {
				return false;
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * 课件页的js
	 * 
	 * @author Lee
	 * @return
	 */
	private StringBuffer getScript() {
		StringBuffer script = new StringBuffer();
		script.append("	var indexInput = new Ext.form.TextField({     ");
		script.append("                  fieldLabel: '课件首页',allowBlank:false,");
		script.append("                  name:'indexFile',blankText: ''});    ");
		script.append("	var fid = new Ext.form.Hidden({name:'courseware_code',value:'${value}'}); ");
		// script.append(" var sid = new
		// Ext.form.Hidden({name:'scormType',value:record.data['scormType']});
		// ");
		script
				.append("	var formPanel = new Ext.form.FormPanel({                                                                                                       ");
		script.append("	        frame:true,   ");
		script.append("		    labelWidth: 100,     ");
		script.append("		    defaultType: 'textfield',");
		script.append("		  	autoScroll:true,     ");
		script.append("		    items: [indexInput,fid]   ");
		script.append("	});                ");
		script.append("	var addModelWin = new Ext.Window({  ");
		script.append("	        title: '课件首页',   ");
		script.append("		    width: 450,               ");
		script.append("		    height: 225,              ");
		script.append("		    minWidth: 300,            ");
		script.append("		    minHeight: 250,           ");
		script.append("		    layout: 'fit',            ");
		script.append("		    plain:true,               ");
		script.append("		    bodyStyle:'padding:5px;', ");
		script.append("		    buttonAlign:'center',    ");
		script.append("		    items: formPanel,        ");
		script.append("		    buttons: [{               ");
		script.append("		  	  text: '提交',           ");
		script.append("		  	  handler: function() {    ");
		script.append("		  	     if (formPanel.form.isValid()) {  ");
		script.append("                 formPanel.getForm().submit({          ");
		script.append("	                 url:'/entity/teaching/peBzzCoursePubManager_updateIndex.action',   ");
		script.append("	                 waitMsg:'更新中...',     ");
		script.append("	                 success: function(form, action) {  ");
		script.append("		               var responseArray = action.result; ");
		script.append("		               if(responseArray.success=='true'){  addModelWin.close();storePageForReload.reload();   ");
		script.append("			             Ext.MessageBox.alert('成功', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');");
		script.append("		               } else {  addModelWin.close();           ");
		script.append("			             Ext.MessageBox.alert('失败', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');");
		script.append("		               }                        ");
		script.append("	                 },");
		script.append("                  failure:function(form, action){");
		script.append("		               var responseArray = action.result; ");
		script.append("		               if(responseArray.success=='false'){  addModelWin.close();storePageForReload.reload();   ");
		script.append("			             Ext.MessageBox.alert('成功', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');");
		script.append("		               } else { addModelWin.show();          ");
		script.append("			             Ext.MessageBox.alert('失败', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');");
		script.append("		               }                        ");
		script.append("                  }        ");
		script.append("                 });            ");
		script.append("		  	     }else{   ");
		script.append("		  	       Ext.MessageBox.alert('错误', '输入错误，请先修正提示的错误');  ");
		script.append("		  	     }                        ");
		script.append("		  	  }                            ");
		script.append("		  	 },{                              ");
		script.append("		  	    text: '取消',                 ");
		script.append("		  	    handler: function(){addModelWin.close();}  ");
		script.append("		  	 }]                               ");
		script.append("	});                                       ");
		script.append("	addModelWin.show();                       ");
		return script;
	}

	/**
	 * 修改课件首页
	 * 
	 * @author Lee
	 * @return
	 */
	public String updateIndex() {
		Map map = new HashMap();
		String querySql = "select * from scorm_sco_launch where course_id='" + this.getCourseware_code() + "'";
		String sql = "update scorm_sco_launch t set indexfile='" + this.getIndexFile() + "' where course_id='" + this.getCourseware_code()
				+ "'";
		try {
			List list = this.getGeneralService().getBySQL(querySql);

			if (list == null || list.size() < 1) {
				map.put("info", "请先上传课件");
				map.put("success", "false");
				this.setJsonString(JsonUtil.toJSONString(map));
				return json();
			}

			int i = this.getGeneralService().executeBySQL(sql);
			if (i > 0) {
				map.put("info", "修改成功");
				map.put("success", "true");
			} else {
				map.put("info", "修改失败");
				map.put("success", "fasle");
			}
		} catch (EntityException e) {
			map.put("info", "修改失败");
			map.put("success", "fasle");
			this.setJsonString(JsonUtil.toJSONString(map));
			return json();
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}

	/**
	 * copyHomework
	 * 
	 * @author Lee
	 * @return
	 */
	public String copyHomework() {
		List<PeBzzTchCourse> list = new ArrayList<PeBzzTchCourse>();
		Map session = ActionContext.getContext().getSession();
		for (int i = 0; i < this.getIds().split(",").length; i++) {
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzTchCourse.class);
			dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
			dc.createCriteria("enumConstByFlagCourseType", "enumConstByFlagCourseType");
			dc.createCriteria("enumConstByFlagCourseCategory", "enumConstByFlagCourseCategory", DetachedCriteria.LEFT_JOIN);
			dc.add(Restrictions.eq("id", this.getIds().split(",")[i]));
			PeBzzTchCourse peBzzTchCourse = null;
			try {
				peBzzTchCourse = this.getGeneralService().getPeBzzTchCourse(dc);
				if (peBzzTchCourse != null) {
					list.add(peBzzTchCourse);
				}

			} catch (EntityException e1) {
				e1.printStackTrace();
			}
		}
		this.setPeBzzTchCourses(list);
		session.put("peBzzTchCourses", peBzzTchCourses);
		return "copyHomework";
	}

	/**
	 * copy测验
	 * 
	 * @author Lee
	 * @return
	 */
	public String copyTestPaper() {
		List<PeBzzTchCourse> list = new ArrayList<PeBzzTchCourse>();
		Map session = ActionContext.getContext().getSession();
		for (int i = 0; i < this.getIds().split(",").length; i++) {
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzTchCourse.class);
			dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
			dc.createCriteria("enumConstByFlagCourseType", "enumConstByFlagCourseType");

			dc.createCriteria("enumConstByFlagCourseCategory", "enumConstByFlagCourseCategory", DetachedCriteria.LEFT_JOIN);
			dc.add(Restrictions.eq("id", this.getIds().split(",")[i]));
			PeBzzTchCourse peBzzTchCourse = null;
			try {
				peBzzTchCourse = this.getGeneralService().getPeBzzTchCourse(dc);
				if (peBzzTchCourse != null) {
					list.add(peBzzTchCourse);
				}

			} catch (EntityException e1) {
				e1.printStackTrace();
			}
		}
		this.setPeBzzTchCourses(list);
		session.put("peBzzTchCourses", peBzzTchCourses);
		return "copyTestPaper";
	}

	/**
	 * 课程详细信息
	 * 
	 * @author Lee
	 * @return
	 */
	public String courseInfo() {
		String id = ServletActionContext.getRequest().getParameter("id");
		EnumConst ec = this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0");
		price = Double.parseDouble(ec.getName());
		this.indexFile = ServletActionContext.getRequest().getParameter("indexFile");
		if ("undefined".equals(this.indexFile.trim()))
			this.indexFile = "未设置";
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT A.CODE,A.NAME,A.TEACHER,A.PRICE,A.STUDYDATES,A.END_DATE, ");
			sb.append(" A.ANSWER_BEGINDATE,A.ANSWER_ENDDATE,EC1.NAME FLAGCOURSETYPE,A.TIME,A.PASSROLE,A.EXAMTIMES_ALLOW, ");
			sb.append(" EC2.NAME FLAGISVISITAFTERPASS,EC3.NAME FLAGISEXAM,EC4.NAME FLAGCOURSECATEGORY,EC5.NAME FLAGCOURSEITEMTYPE, ");
			sb.append(" EC6.NAME FLAGCONTENTPROPERTY,LISTAGG(C.NAME, ',') WITHIN GROUP(ORDER BY C.NAME) WC,EC7.NAME FLAGISRECOMMEND, ");
			sb.append(" EC8.NAME FLAGISFREE,A.PHOTO_LINK,A.TEA_IMG,A.PASSROLE_NOTE,A.SUGGESTION,ssl.indexfile ");
			sb.append(" FROM (SELECT * FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "') A ");
			sb.append(" INNER JOIN ENUM_CONST EC1 ON A.FLAG_COURSETYPE = EC1.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC2 ON A.FLAG_ISVISITAFTERPASS = EC2.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC3 ON A.FLAG_IS_EXAM = EC3.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC4 ON A.FLAG_COURSECATEGORY = EC4.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC5 ON A.FLAG_COURSE_ITEM_TYPE = EC5.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC6 ON A.FLAG_CONTENT_PROPERTY = EC6.ID ");
			sb.append(" LEFT JOIN ENUM_CONST EC7 ON A.FLAG_ISRECOMMEND = EC7.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC8 ON A.FLAG_ISFREE = EC8.ID ");
			sb.append(" LEFT JOIN (SELECT * FROM PE_BZZ_TCH_COURSE_SUGGEST WHERE TABLE_NAME = 'PE_BZZ_TCH_COURSE') B ");
			sb.append(" ON A.ID = B.FK_COURSE_ID ");
			sb.append(" LEFT JOIN ENUM_CONST C ON B.FK_ENUM_CONST_ID = C.ID ");
			sb.append(" left join SCORM_SCO_LAUNCH ssl on ssl.course_id = a.code ");
			sb.append(" GROUP BY A.CODE,A.NAME,A.TEACHER,A.PRICE,A.STUDYDATES,A.END_DATE, ");
			sb.append(" A.ANSWER_BEGINDATE,A.ANSWER_ENDDATE,EC1.NAME,A.TIME,A.PASSROLE, ");
			sb.append(" A.EXAMTIMES_ALLOW,EC2.NAME,EC3.NAME,EC4.NAME,EC5.NAME,EC6.NAME, ");
			sb.append(" EC7.NAME,EC8.NAME,A.PHOTO_LINK,A.TEA_IMG,A.PASSROLE_NOTE,A.SUGGESTION,B.TABLE_NAME,ssl.indexfile ");
			List detailList = this.getGeneralService().getBySQL(sb.toString());
			ServletActionContext.getRequest().setAttribute("courseDetail", detailList);
			this.indexFile = ServletActionContext.getRequest().getParameter("indexFile");
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "courseInfo";
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
			ServletActionContext.getRequest().getSession().setAttribute("stipids", ids);
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
			String[] ids = (String[]) ServletActionContext.getRequest().getSession().getAttribute("slaids");// 设置删除课程图片
			if (null == ids || ids.length == 0)
				ids = (String[]) ServletActionContext.getRequest().getSession().getAttribute("stipids");// 设置、删除讲师图片
			StringBuffer slaids = new StringBuffer();
			for (int i = 0; i < ids.length; i++) {
				slaids.append("'" + ids[i] + "',");
			}
			slaids.append("''");
			String sql = "";
			if ("c".equalsIgnoreCase(param)) {
				sql = "UPDATE PE_BZZ_TCH_COURSE SET FLAG_IMG = 'FlagImg1', PHOTO_LINK = '" + linkUrl + "' WHERE ID IN ("
						+ slaids.toString() + ")";
				msg = "课程";
			} else {
				sql = "UPDATE PE_BZZ_TCH_COURSE SET FLAG_TEAIMG = 'FlagTeaimg1', TEA_IMG = '" + linkUrl + "' WHERE ID IN ("
						+ slaids.toString() + ")";
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
		this.setTogo("/entity/teaching/peBzzCourseLiveManager.action");
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
		this.setTogo("/entity/teaching/peBzzCourseLiveManager.action");
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
			ServletActionContext.getRequest().getSession().setAttribute("pbzpmpids", ids);
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

	/**
	 * 设置建议学习人群
	 * 
	 * @author Lee
	 * @return
	 */
	public String setSuggest() {
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			/* 存储选中ids便于后续操作使用 */
			ServletActionContext.getRequest().getSession().setAttribute("pbzpmpids", ids);
		}
		return "setSugg";
	}

	/**
	 * 删除建议学习人群
	 * 
	 * @author Lee
	 * @return
	 */
	public String removeSuggest() {
		try {
			if (this.getIds() != null && this.getIds().length() > 0) {
				String sql = "";
				String[] ids = getIds().split(",");
				for (int i = 0; i < ids.length; i++) {
					sql = "DELETE FROM PE_BZZ_TCH_COURSE_SUGGEST WHERE FK_COURSE_ID = '" + ids[i]
							+ "' AND UPPER(TABLE_NAME) = 'PE_BZZ_TCH_COURSE'";
					this.getGeneralService().executeBySQL(sql);
				}
				StringBuffer params_ids = new StringBuffer();
				for (int i = 0; i < ids.length; i++) {
					params_ids.append("'" + ids[i] + "',");
				}
				params_ids.append("''");
				/* 更新课程的设置学习人群状态 */
				sql = "UPDATE PE_BZZ_TCH_COURSE SET FLAG_SUGGESTSET = 'FlagSuggestSet0' WHERE ID IN (" + params_ids.toString() + ")";
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
		this.setTogo("back");
		return "lee";
	}

	public String preView() {
		return "noPreView";
	}

	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	public String get_uploadContentType() {
		return _uploadContentType;
	}

	public void set_uploadContentType(String contentType) {
		_uploadContentType = contentType;
	}

	public String getTempFlag() {
		return tempFlag;
	}

	public void setTempFlag(String tempFlag) {
		this.tempFlag = tempFlag;
	}

	public List getPeBzzTchCourses() {
		return peBzzTchCourses;
	}

	public void setPeBzzTchCourses(List peBzzTchCourses) {
		this.peBzzTchCourses = peBzzTchCourses;
	}

	public PeBzzTchCourseware getPeBzzTchCourseware() {
		return peBzzTchCourseware;
	}

	public void setPeBzzTchCourseware(PeBzzTchCourseware peBzzTchCourseware) {
		this.peBzzTchCourseware = peBzzTchCourseware;
	}

	public PeTchCourseService getPeTchCourseService() {
		return peTchCourseService;
	}

	public void setPeTchCourseService(PeTchCourseService peTchCourseService) {
		this.peTchCourseService = peTchCourseService;
	}

	public String getCourseware_id() {
		return courseware_id;
	}

	public void setCourseware_id(String courseware_id) {
		this.courseware_id = courseware_id;
	}

	public String getCourseware_name() {
		return courseware_name;
	}

	public void setCourseware_name(String courseware_name) {
		this.courseware_name = courseware_name;
	}

	public String getCourseware_code() {
		return courseware_code;
	}

	public void setCourseware_code(String courseware_code) {
		this.courseware_code = courseware_code;
	}

	public String getIndexFile() {
		return indexFile;
	}

	public void setIndexFile(String indexFile) {
		this.indexFile = indexFile;
	}

	public String getScormType() {
		return scormType;
	}

	public void setScormType(String scormType) {
		this.scormType = scormType;
	}

	public String getSacliveid() {
		return sacliveid;
	}

	public void setSacliveid(String sacliveid) {
		this.sacliveid = sacliveid;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	public String getParam_id() {
		return param_id;
	}

	public void setParam_id(String param_id) {
		this.param_id = param_id;
	}
}
