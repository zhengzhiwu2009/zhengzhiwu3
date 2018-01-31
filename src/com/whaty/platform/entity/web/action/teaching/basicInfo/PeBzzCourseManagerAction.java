package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.jfree.util.StringUtils;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeBzzTchCourseware;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.StudentBatch;
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
 * @param
 * @version 创建时间：2009-6-21 上午10:40:06
 * @return
 * @throws PlatformException
 *             类说明
 */
public class PeBzzCourseManagerAction extends MyBaseAction<PeBzzTchCourse> {

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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * 初始化列表
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();
		boolean canAdd = false;
		boolean canUpdate = false;
		boolean canDelete = false;
		if (capabilitySet.contains(this.servletPath + "_add.action")) {
			canAdd = true;
		}
		if (capabilitySet.contains(this.servletPath + "_delete.action")) {
			canDelete = true;
		}
		if (capabilitySet.contains(this.servletPath + "_update.action")) {
			canUpdate = true;
		}
		this.getGridConfig().setCapability(canAdd, canDelete, canUpdate);
		this.getGridConfig().setTitle("课程发布管理");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("课程编号"), "code", true,
				true, true, Const.coursecode_for_extjs);
		this.getGridConfig().addColumn(this.getText("课程名称"), "name", true,
				true, true, "TextField", false, 200, "");

		ColumnConfig columnConfigType = new ColumnConfig(this.getText("课程性质"),
				"enumConstByFlagCourseType.name", true, true, true,
				"TextField", false, 100, "");
		String sql = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseType'";
		columnConfigType.setComboSQL(sql);
		this.getGridConfig().addColumn(columnConfigType);

		ColumnConfig columnConfigCategory = new ColumnConfig(this
				.getText("按业务分类"), "enumConstByFlagCourseCategory.name", true,
				true, true, "TextField", false, 100, "");
		String sql2 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseCategory'";
		columnConfigCategory.setComboSQL(sql2);
		this.getGridConfig().addColumn(columnConfigCategory);

		ColumnConfig columnCourseItemType = new ColumnConfig(this
				.getText("按大纲分类"), "enumConstByFlagCourseItemType.name", true,
				true, true, "TextField", false, 100, "");
		String sql7 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseItemType'";
		columnCourseItemType.setComboSQL(sql7);
		this.getGridConfig().addColumn(columnCourseItemType);
		
		ColumnConfig columnContentProperty = new ColumnConfig(this
				.getText("按内容属性分类"), "enumConstByFlagContentProperty.name", true,
				true, true, "TextField", false, 100, "");
		String sql9 = "select a.id ,a.name from enum_const a where a.namespace='FlagContentProperty'";
		columnContentProperty.setComboSQL(sql9);
		this.getGridConfig().addColumn(columnContentProperty);
		

		ColumnConfig columnConfigIsvalid = new ColumnConfig(this.getText("发布"),
				"enumConstByFlagIsvalid.name", true, false, true, "TextField",
				false, 100, "");
		String sql3 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsvalid'";
		columnConfigIsvalid.setComboSQL(sql3);
		this.getGridConfig().addColumn(columnConfigIsvalid);
		ColumnConfig columnConfigOffline = new ColumnConfig(this.getText("下线"),
				"enumConstByFlagOffline.name", true, false, true, "TextField",
				false, 100, "");
		String sql33 = "select a.id ,a.name from enum_const a where a.namespace='FlagOffline'";
		columnConfigIsvalid.setComboSQL(sql33);
		this.getGridConfig().addColumn(columnConfigOffline);
		// 后来添加，是否可添加到专项
		ColumnConfig columncanJoinBatch = new ColumnConfig(
				this.getText("专项课程"), "enumConstByFlagCanJoinBatch.name", true,
				false, true, "TextField", false, 100, "");
		String sqlcjb = "select a.id ,a.name from enum_const a where a.namespace='FlagCanJoinBatch'";
		columncanJoinBatch.setComboSQL(sqlcjb);
		this.getGridConfig().addColumn(columncanJoinBatch);
		// this.getGridConfig().addColumn(this.getText("专项培训课程"),
		// true, "");

		ColumnConfig columnConfigIsExam = new ColumnConfig(this.getText("考试"),
				"enumConstByFlagIsExam.name", true, true, true, "TextField",
				false, 100, "");
		String sqlx = "select a.id ,a.name from enum_const a where a.namespace='FlagIsExam'";
		columnConfigIsExam.setComboSQL(sqlx);
		this.getGridConfig().addColumn(columnConfigIsExam);

		ColumnConfig columnConfigRecommend = new ColumnConfig(this
				.getText("推荐状态"), "enumConstByFlagIsRecommend.name", false,
				true, false, "TextField", false, 100, "");
		String sql5 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsRecommend'";
		columnConfigRecommend.setComboSQL(sql5);
		this.getGridConfig().addColumn(columnConfigRecommend);

		ColumnConfig columnConfigFree = new ColumnConfig(this.getText("收费状态"),
				"enumConstByFlagIsFree.name", true, true, true, "TextField",
				false, 100, "");
		String sql6 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsFree'";
		columnConfigFree.setComboSQL(sql6);
		this.getGridConfig().addColumn(columnConfigFree);

		ColumnConfig columnConfigCheck = new ColumnConfig(this
				.getText("申请发布状态"), "enumConstByFlagCheckStatus.name", false,
				false, false, "TextField", false, 100, "");
		String sql_check = "select a.id ,a.name from enum_const a where a.namespace='FlagCheckStatus'";
		columnConfigCheck.setComboSQL(sql_check);
		this.getGridConfig().addColumn(columnConfigCheck);

		this.getGridConfig().addColumn(this.getText("主讲人"), "teacher", true,
				true, true, "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("学时"), "time", true,
				true, true, "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("图片状态"), "photoStatus",
				true, true, true, "TextField", true, 25);
		//Lee start 2014年5月22日 注释下学时数显示：原因为用了金额的提示验证fee_for_extjs
		//this.getGridConfig().addColumn(this.getText("学时数"), "time", false,
		//		true, false, Const.fee_for_extjs);
		//Lee end

		/*
		 * this.getGridConfig().addColumn(this.getText("课程条目类别"),
		 * "enumConstByFlagCourseItemType.name", false, true, true, "");
		 */
		// this.getGridConfig().addColumn(this.getText("价格"), "price", false,
		// false, true, Const.oneTwoNum_for_extjs);
		EnumConst classHourRate = this.getEnumConstService()
				.getByNamespaceCode("ClassHourRate", "0");// 学时金钱兑换比例
		this.getGridConfig().addColumn(this.getText("学习期限(天)"), "studyDates",
				false, true, false, Const.number_for_extjs);
		this.getGridConfig().addColumn(this.getText("停用日期"), "endDate", false,
				true, false, "TextField", true, 10);
		this.getGridConfig().addColumn(this.getText("发布日期"), "announceDate",
				false, true, false, "TextField", false, 10);
		this.getGridConfig().addColumn(this.getText("答疑开始时间"),
				"answerBeginDate", false, true, false, "TextField", true, 10);
		this.getGridConfig().addColumn(this.getText("答疑结束时间"), "answerEndDate",
				false, true, false, "TextField", true, 10);
		this.getGridConfig().addColumn(this.getText("通过规则(百分比分数线)"),
				"passRole", false, true, false, Const.oneTwoNum_for_extjs);
		this.getGridConfig()
				.addColumn(this.getText("考试限制次数"), "examTimesAllow", false,
						true, false, Const.oneTwoNum_for_extjs);
		ColumnConfig columnConfig = new ColumnConfig(this.getText("通过后能否继续访问"),
				"enumConstByFlagIsvisitAfterPass.name", false, true, false,
				"TextField", false, 100, "");
		String sql4 = "select b.id ,b.name from enum_const b where b.namespace='FlagIsvisitAfterPass'";
		columnConfig.setComboSQL(sql4);
		this.getGridConfig().addColumn(columnConfig);

		this.getGridConfig().addColumn(this.getText("通过规则描述"), "passRoleNote",
				false, true, false, "");
		this.getGridConfig().addColumn(this.getText("学习建议"), "suggestion",
				false, true, false, "TextArea", true, 500);
		this.getGridConfig().addColumn(this.getText("课件id"), "coursewareId",
				false, false, false, "");
		this.getGridConfig().addColumn(this.getText("课件类型"), "scormType",
				false, false, false, "");
		this.getGridConfig().addColumn(this.getText("课件首页"), "indexFile",
				false, false, false, "");
		this.getGridConfig().addColumn(this.getText("满意度调查"), "sid", false,
				false, false, "");

		// this.getGridConfig().addRenderScript(this.getText("价格"), "{return
		// record.data['time'] * "+classHourRate.getName()+"}","time");

		if (us.getUserLoginType().equals("3")) {
			if (capabilitySet.contains(this.servletPath + "_applyopen.action")) {
				this.getGridConfig().addMenuFunction(this.getText("申请发布"),
						"checkStatus");
			}

			if (capabilitySet.contains(this.servletPath + "_review.action")) {
				this
						.getGridConfig()
						.addRenderScript(
								this.getText("预览"),
								"{if(record.data['coursewareId'] ==''){return '<a href=\"/entity/teaching/peBzzCourseManager_preView.action?mydate= '+new Date().getTime()+'\" target=\"_blank\">预览</a>';}else {return '<a href=/CourseImports/'+record.data['coursewareId']+'/'+record.data['scormType']+'/'+record.data['indexFile']+'?mydate='+ new Date().getTime() +' target=\"_blank\">预览</a>';}}",
								// "{return '<a
								// href=/CourseImports/'+record.data['coursewareId']+'/'+record.data['scormType']+'/'+record.data['indexFile']+'
								// target=\"_blank\">预览</a>';}",
								"");
			}

			// this.getGridConfig().addRenderFunction(
			// this.getText("查看信息"),
			// "<a
			// href=\"/entity/teaching/peBzzCourseManager_courseInfo.action?id=${value}&teacher_id=teacher1&indexFile=record.data['indexFile']\"
			// target='_blank'>查看信息</a>",
			// "id");
			if (capabilitySet.contains(this.servletPath + "_view.action")) {
				this
						.getGridConfig()
						.addRenderScript(
								this.getText("查看"),
								"{return '<a href=\"/entity/teaching/peBzzCourseManager_courseInfo.action?id='+${value}+'&indexFile='+record.data['indexFile']+'\"  target=\"_blank\">查看</a>';}",
								"id");
			}
			if (capabilitySet.contains(this.servletPath
					+ "_manageSource.action")) {
				this
						.getGridConfig()
						.addRenderFunction(
								this.getText("学习资源管理"),
								"<a href=\"/sso/bzzinteraction_InteractioManage.action?course_id=${value}&teacher_id=teacher1\"  target='_blank'>管理学习资源</a>",
								"id");
			}
			if (capabilitySet.contains(this.servletPath + "_impCourse.action")) {
				this
						.getGridConfig()
						.addRenderScript(
								this.getText("课件"),
								"{if(record.data['coursewareId'] ==''){return '<a href=\"/entity/teaching/peBzzCourseManager_toUploadCourseware.action?bean.id='+record.data['id']+'\" target=\"_blank\">导入</a>';}else return '<a href=\"#\" onclick=if(confirm(\"确定要删除吗\")){window.open(\"/entity/manager/course/courseware/scorm12/delete_scorm_info.jsp?courseware_id='+record.data['code']+'\")} >删除</a>'}",
								"id");
			}
			// add by wuhao
			if (capabilitySet.contains(this.servletPath
					+ "_manageCourse.action")) {
				// this.getGridConfig().addRenderScript(this.getText("课件管理"),
				// "{if(record.data['coursewareId'] ==''){return '<a
				// href=\"/entity/teaching/peBzzCourseManager_toUploadCourseware.action?bean.id='+record.data['id']+'\"
				// target=\"_blank\">导入</a>';}else return '<a href=\"#\"
				// onclick=if(true){window.open(\"/entity/manager/course/courseware/filemanager/filemanager_main02.jsp?courseware_id='+record.data['code']+'\")}
				// >管理</a>'}", "id");
				this
						.getGridConfig()
						.addRenderScript(
								this.getText("课件管理"),
								"{if(record.data['coursewareId'] ==''){return '管理';}else return '<a href=\"#\" onclick=if(true){window.open(\"/entity/manager/course/courseware/filemanager/filemanager_main02.jsp?courseware_id='+record.data['code']+'\")} >管理</a>'}",
								"id");
			}// complete
			if (capabilitySet.contains(this.servletPath + "_satified.action")) {
				this
						.getGridConfig()
						.addRenderFunction(
								this.getText("满意度调查"),
								"<a href=\"/entity/teaching/addSatisfactionPaper.action?courseId=${value}&flag=addPaper\"  >添加</a>",
								"id");
			}
			if (capabilitySet
					.contains(this.servletPath + "_updateIndex.action")) {
				this.getGridConfig().addRenderFunction(
						this.getText("课件首页"),
						"<a href='#' onclick=\"" + this.getScript().toString()
								+ "\">修改</a>", "code");
			}
			if (capabilitySet.contains(this.servletPath + "_viewResult.action")) {
				this
						.getGridConfig()
						.addRenderScript(
								this.getText("调查结果"),
								"{if(record.data['sid'] !=''){return '<a href=\"/entity/information/peVotePaper_viewDetail.action?bean.id='+record.data['sid']+'&togo=1\" target=\"_blank\">查看</a>';}else return '未添加'}",
								"sid");
			}

			if (capabilitySet.contains(this.servletPath + "_fabu.action")) {
				this.getGridConfig().addMenuFunction(this.getText("同意发布"),
						"FlagIsvalid.true");
			}
			if (capabilitySet.contains(this.servletPath + "_tingyong.action")) {
				this.getGridConfig().addMenuFunction(this.getText("停用（拒绝发布）"),
						"FlagIsvalid.false");
			}
			if (capabilitySet.contains(this.servletPath
					+ "_addToSpecial.action")) {
				this.getGridConfig().addMenuFunction(this.getText("添加专项确认"),
						"FlagCanJoinBatch.true");
			}
			if (capabilitySet.contains(this.servletPath
					+ "_cancelSpecial.action")) {
				this.getGridConfig().addMenuFunction(this.getText("添加专项取消"),
						"FlagCanJoinBatch.false");
			}
			if(capabilitySet.contains(this.servletPath+"_applyOffline.action")){
				this.getGridConfig().addMenuFunction(this.getText("申请下线"),"applyOffline");
			}
			if(capabilitySet.contains(this.servletPath+"_agreeOffline.action")){
				this.getGridConfig().addMenuFunction(this.getText("同意下线"),
						"FlagOffline.true");
			}
			if(capabilitySet.contains(this.servletPath+"_refuseOffline.action")){
				this.getGridConfig().addMenuFunction(this.getText("拒绝下线"),
				"FlagOffline.false");
			}

		}

		this.getGridConfig().addColumn(this.getText("图片"), "photoLink", false,
				true, false, "File", true, 200);
		Map<String, String> defaultValueMap = new HashMap<String, String>();
		defaultValueMap.put("passRole", "80");
		defaultValueMap.put("examTimesAllow", "5");
		defaultValueMap.put("studyDates", "90");
		this.setDefaultValueMap(defaultValueMap);
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeBzzTchCourse.class;

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/teaching/peBzzCourseManager";
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
	 * @author linjie
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
		} catch (EntityException e1) {
			map.put("success", "false");
			map.put("info", e1.getMessage());
			return map;
		}
		// 获取默认专项
		DetachedCriteria dBatch = DetachedCriteria.forClass(PeBzzBatch.class);
		dBatch.createAlias("enumConstByFlagBatchType",
				"enumConstByFlagBatchType");
		dBatch.add(Restrictions.eq("enumConstByFlagBatchType.code", "1"));
		dBatch.add(Restrictions.eq("enumConstByFlagBatchType.namespace",
				"FlagBatchType"));
		PeBzzBatch peBzzBatch = null;
		try {
			peBzzBatch = (PeBzzBatch) this.getGeneralService().getDetachList(
					dBatch).get(0);
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		EnumConst ec = this.getEnumConstService().getByNamespaceCode(
				"FlagIsvalid", "0"); // 添加的课程默认为无效状态
		this.getBean().setEnumConstByFlagIsvalid(ec);
		EnumConst ec4 = this.getEnumConstService().getByNamespaceCode(
				"FlagOffline", "0"); // 添加的课程默认为无效状态
		this.getBean().setEnumConstByFlagOffline(ec4);
		EnumConst ec2 = this.getEnumConstService().getByNamespaceCode(
				"FlagCanJoinBatch", "0"); // 添加的课程默认为不可添加到专项
		this.getBean().setEnumConstByFlagCanJoinBatch(ec2);
		EnumConst ec3 = this.getEnumConstService().getByNamespaceCode(
				"FlagCheckStatus", "2"); // 添加的课程默认为未审核状态
		this.getBean().setEnumConstByFlagCheckStatus(ec3);
		this.getBean().setCreateDate(new Date());
		PrBzzTchOpencourse prBzzTchOpencourse = new PrBzzTchOpencourse();
		prBzzTchOpencourse.setPeBzzTchCourse(this.getBean());
		prBzzTchOpencourse.setPeBzzBatch(peBzzBatch);
		try {
			this.setBean(this.getPeTchCourseService().add(prBzzTchOpencourse)
					.getPeBzzTchCourse());
			map.put("success", "true");
			map.put("info", "一门课程添加成功");

		} catch (Exception e) {
			new File(ServletActionContext.getRequest().getRealPath(linkUrl))
					.delete();
			e.printStackTrace();
			return this.checkAlternateKey(e, "添加");
		}
		return map;
	}

	/**
	 * 重写框架方法：添加数据前的校验
	 * 
	 * @author linjie
	 * @return
	 */
	public void checkBeforeAdd() throws EntityException {
		DetachedCriteria dc2 = DetachedCriteria.forClass(PeBzzTchCourse.class);
		dc2.add(Restrictions.eq("code", this.getBean().getCode()));
		List courseList = this.getGeneralService().getList(dc2);
		if (courseList != null && courseList.size() > 0) {
			throw new EntityException("课程编号已经存在");
		}
		// 暂时不用名称限制
		// DetachedCriteria dc3 =
		// DetachedCriteria.forClass(PeBzzTchCourse.class);
		// dc3.add(Restrictions.eq("name", this.getBean().getName()));
		// List courseList3 = this.getGeneralService().getList(dc3);
		// if (courseList3!=null && courseList3.size() > 0) {
		// throw new EntityException("课程名称已经存在");
		// }
		if (this.get_upload() != null
				&& isImageFile(this.get_upload().getName().toLowerCase())) {
			throw new EntityException("照片不是图片文件，请选择一个图片文件！");
		}
	}

	/**
	 * 重写框架方法：更新数据前的校验
	 * 
	 * @author linjie
	 * @return
	 */
	public void checkBeforeUpdate() throws EntityException {
		// PeBzzTchCourse c =
		// this.getGeneralService().getById(this.getBean().getId());
		// DetachedCriteria dCourseware = DetachedCriteria
		// .forClass(PeBzzTchCourseware.class);
		// dCourseware.createAlias("peBzzTchCourse", "peBzzTchCourse");
		// dCourseware.add(Restrictions.eq("peBzzTchCourse.id", c.getId()));
		// String scormCourseInfoSql = "update scorm_course_info sci set
		// sci.id='',sci.title='' where sci.id=''";
		//
		// List coursewareList = this.getGeneralService().getList(dCourseware);
		// if (coursewareList != null && coursewareList.size() > 0) {
		// this.peBzzTchCourseware = (PeBzzTchCourseware) coursewareList.get(0);
		// this.peBzzTchCourseware.setName(this.getBean().getName());
		// this.peBzzTchCourseware.setCode(this.getBean().getCode());
		// this.peBzzTchCourseware =
		// this.getPeTchCourseService().savePeBzzTchCourseware(this.peBzzTchCourseware);
		// }
		PeBzzTchCourse c = this.getGeneralService().getById(
				this.getBean().getId());
		String sql = "select distinct scorm_type from scorm_sco_launch where course_id ='"
				+ c.getCode() + "'";
		// String sql = "select * from scorm_sco_launch ss where ss.course_id in
		// ("+ids_s+")";
		List list = this.getGeneralService().getBySQL(sql);
		if (c.getCode() != null
				&& !c.getCode().equals(this.getBean().getCode())
				&& list.size() > 0) {
			throw new EntityException("已经上传课件，不能修改课程编号，若要修改请先将课件删除");
		}
		if (this.get_upload() != null
				&& !isImageFile(this.get_uploadFileName().toLowerCase())) {
			throw new EntityException("照片不是图片文件，请选择一个图片文件！");
		}
		this.getBean().setEditDate(new Date());
	}

	/**
	 * 重写框架方法：更新数据
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public Map update() {

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

			UserSession us = (UserSession) ServletActionContext.getRequest()
					.getSession()
					.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			dbInstance = this.getGeneralService().getById(
					this.getBean().getId());

		} catch (EntityException e1) {
			map.put("success", "false");
			map.put("info", "更新失败");
			return map;
		}
		this
				.superSetBean((PeBzzTchCourse) setSubIds(dbInstance, this
						.getBean()));

		PeBzzTchCourse instance = null;

		try {
			// instance = this.getGeneralService().save(this.getBean());
			this.getPeTchCourseService().saveCourseAndCourseware(dbInstance,
					this.getBean());
			map.put("success", "true");
			map.put("info", "更新成功");

		} catch (Exception e) {
			return this.checkAlternateKey(e, "更新");

		}

		return map;
	}

	/**
	 * 是否图片
	 * 
	 * @author linjie
	 * @return
	 */
	private boolean isImageFile(String fileName) {
		if (fileName.toLowerCase().endsWith(".jpg")
				|| fileName.toLowerCase().endsWith(".gif")
				|| fileName.toLowerCase().endsWith(".png")
				|| fileName.toLowerCase().endsWith(".bmp")
				|| fileName.toLowerCase().endsWith(".jpeg")
				|| fileName.toLowerCase().endsWith(".tiff")) {
			return true;
		}
		return false;
	}

	/**
	 * 重写框架方法：删除数据前的校验
	 * 
	 * @author linjie
	 * @return
	 */
	public void checkBeforeDelete(List idList) throws EntityException {
		// 检查是否添加到专项
		EnumConst enumConstByFlagBatchType = this.getEnumConstService()
				.getByNamespaceCode("1", "FlagBatchType");// 默认专项
		DetachedCriteria criteria = DetachedCriteria
				.forClass(PrBzzTchOpencourse.class);
		criteria.createCriteria("peBzzTchCourse", "peBzzTchCourse");
		criteria.createCriteria("peBzzBatch", "peBzzBatch");
		criteria.add(Restrictions.ne("peBzzBatch.enumConstByFlagBatchType",
				enumConstByFlagBatchType));// 排除默认专项
		criteria.add(Restrictions.in("peBzzTchCourse.id", idList));
		List<PrBzzTchOpencourse> opencourselist = null;
		try {
			opencourselist = this.getGeneralService().getList(criteria);
			if (opencourselist.size() > 0) {
				throw new EntityException("所选课程中存在已添加到专项课程");
			}
		} catch (EntityException e) {
			e.printStackTrace();
			throw e;
		}
		// 检查是否已经被选

		DetachedCriteria dc = DetachedCriteria
				.forClass(PrBzzTchStuElective.class);
		dc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse")
				.createCriteria("peBzzTchCourse", "peBzzTchCourse");
		dc.add(Restrictions.in("peBzzTchCourse.id", idList));
		try {
			List<PrBzzTchStuElective> electivelist = this.getGeneralService()
					.getList(dc);
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
	 * @author linjie
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

					// String sql = "select distinct scorm_type from
					// scorm_sco_launch where course_id in ("+courseware_id+")"
					// ;
					// String sql = "select * from scorm_sco_launch ss where
					// ss.course_id in ("+ids_s+")";
					// List list = this.getGeneralService().getBySQL(sql);
					// if(list.size()>0) {
					// throw new EntityException("请先将课件删除");
					// }
					DetachedCriteria dcCourse = DetachedCriteria
							.forClass(PeBzzTchCourse.class);
					dcCourse.createCriteria("enumConstByFlagIsvalid",
							"enumConstByFlagIsvalid");
					dcCourse.add(Restrictions.in("id", idList));
					List<PeBzzTchCourse> cList = this.getGeneralService()
							.getList(dcCourse);
					String ids_s = "";
					for (int i = 0; i < cList.size(); i++) {
						ids_s += "'" + cList.get(i).getCode().toString() + "',";
					}
					ids_s = ids_s.substring(0, ids_s.length() - 1);
					String sql = "select distinct scorm_type from scorm_sco_launch where course_id in ("
							+ ids_s + ")";
					// String sql = "select * from scorm_sco_launch ss where
					// ss.course_id in ("+ids_s+")";
					List list = this.getGeneralService().getBySQL(sql);
					if (list.size() > 0) {
						throw new EntityException("请先将课件删除");
					}
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

					String sql = "delete from pe_bzz_tch_courseware where FK_COURSE_ID in ("
							+ idss + ")";
					this.getGeneralService().executeBySQL(sql);
					/**
					 * 20130125添加，用于删除可能存在于统计表中的课程
					 */
					String sqlSc = "delete from statistic_courses sc where sc.fk_course_id in ("
							+ idss + ")";
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
	 * @author linjie
	 * @return
	 */
	public String abstractDetail() {
		Page page = null;
		Map map = new HashMap();
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzTchCourse.class);
		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		dc.createCriteria("enumConstByFlagOffline", "enumConstByFlagOffline");
		dc.createCriteria("enumConstByFlagCourseType",
				"enumConstByFlagCourseType");
		dc.createCriteria("enumConstByFlagCourseCategory",
				"enumConstByFlagCourseCategory", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagIsvisitAfterPass",
				"enumConstByFlagIsvisitAfterPass", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagIsFree", "enumConstByFlagIsFree",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagIsRecommend",
				"enumConstByFlagIsRecommend", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagCourseItemType",
				"enumConstByFlagCourseItemType", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagContentProperty",
				"enumConstByFlagContentProperty", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagCanJoinBatch",
				"enumConstByFlagCanJoinBatch", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagCheckStatus",
				"enumConstByFlagCheckStatus", DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.eq("id", this.getBean().getId()));
		try {
			page = this.getGeneralService().getByPage(dc,
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
			String id = this.getBean().getId();

			if (page != null) {
				page.setItems(JsonUtil.ArrayToJsonObjects(page.getItems(), this
						.getGridConfig().getListColumnConfig()));
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

	/**
	 * 课程管理列表
	 * 
	 * @author linjie
	 * @return
	 */
	public Page list() {
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (us.getUserLoginType().equals("3")) {
			sqlBuffer.append("SELECT * ");
			sqlBuffer.append("FROM   (SELECT a.id, ");
			sqlBuffer.append("               a.code, ");
			sqlBuffer.append("               a.name, ");
			sqlBuffer.append("               a.courseTypeName, ");
			sqlBuffer.append("               a.categoryName, ");
			sqlBuffer.append("               a.courseItemType, ");
			sqlBuffer.append("               a.contentProperty, ");
			sqlBuffer.append("               a.validName, ");
			sqlBuffer.append("               a.isoffline, ");
			sqlBuffer.append("               a.canJoinBatch, ");
			sqlBuffer.append("               a.isExam, ");
			sqlBuffer.append("               a.isRecommend, ");
			sqlBuffer.append("               a.isFree, ");
			sqlBuffer.append("               a.isCheck, ");
			sqlBuffer.append("               a.teacher,a.time,a.photoStatus,");
			sqlBuffer.append("               a.studyDates, ");
			sqlBuffer.append("               a.endDate, ");
			sqlBuffer.append("               a.announceDate, ");
			sqlBuffer.append("               a.answerBeginDate, ");
			sqlBuffer.append("               a.answerEndDate, ");
			sqlBuffer.append("               a.passRole, ");
			sqlBuffer.append("               a.examTimesAllow, ");
			sqlBuffer.append("               a.visitName, ");
			sqlBuffer.append("               a.passRoleNote, ");
			sqlBuffer.append("               a.suggestion, ");
			// sqlBuffer.append(" a.teacherNote, ");
			// sqlBuffer.append(" a.note, ");
			sqlBuffer.append("               a.coursewareId, ");
			sqlBuffer.append("               a.scormType, ");
			sqlBuffer
					.append("               indexFile,a.sid,a.createDate,a.photoLink ");
			sqlBuffer
					.append("        FROM   (SELECT tc.id               AS id, ");
			sqlBuffer
					.append("                       tc.name             AS name, ");
			sqlBuffer
					.append("                       tc.code             AS code, ");
			sqlBuffer
					.append("                       tc.studyDates       AS studyDates, ");
			sqlBuffer
					.append("                       tc.price            AS price, ");
			sqlBuffer
					.append("                       tc.end_date         AS endDate, ");
			sqlBuffer
					.append("                       tc.announce_Date         AS announceDate, ");
			sqlBuffer
					.append("                       tc.answer_BeginDate AS answerBeginDate, ");
			sqlBuffer
					.append("                       tc.answer_EndDate   AS answerEndDate, ");
			sqlBuffer
					.append("                       tc.passRole         AS passRole, ");
			sqlBuffer
					.append("                       tc.examTimes_Allow  AS examTimesAllow, ");
			sqlBuffer
					.append("                       tc.passRole_Note    AS passRoleNote, ");
			sqlBuffer
					.append("                       tc.suggestion       AS suggestion, ");
			sqlBuffer
					.append("                       ec1.name            AS validName, ");
			sqlBuffer
					.append("                       ec8.name          	 AS courseItemType, ");
			sqlBuffer
					.append("                       ec9.name            AS isExam, ");
			sqlBuffer
					.append("                       ec7.name            AS canJoinBatch, "); // 可否添加到专项，后来添加
			sqlBuffer
					.append("                       tc.suqnum           AS suqNum, ");
			sqlBuffer
					.append("                       ec2.name            AS categoryName, ");
			sqlBuffer
					.append("                       ec3.name            AS courseTypeName, ");
			sqlBuffer
					.append("                       ec4.name            AS visitName, ");
			sqlBuffer
					.append("                       ec5.name            AS isRecommend, ");
			sqlBuffer
					.append("                       ec6.name            AS isFree, ");
			sqlBuffer.append("                       ec11.name            AS isoffline, ");
			sqlBuffer.append("                       ec12.name            AS contentProperty, ");
			sqlBuffer
					.append("                       ec10.name           AS isCheck, ");
			sqlBuffer
					.append("                       tc.time             AS time, ");
			sqlBuffer
					.append("                       scorm.indexFile     AS indexFile, ");
			sqlBuffer
					.append("                       scorm.course_id     AS coursewareId, ");
			sqlBuffer
					.append("                       scormType.code      AS scormType, ");
			sqlBuffer
					.append("                       tc.teacher          AS teacher, ");
			sqlBuffer
					.append("                       tc.teacher_note     AS teacherNote, ");
			sqlBuffer
					.append("                       tc.note             AS note ,tc.SATISFACTION_ID as sid,tc.create_date as createDate,decode(tc.photo_link,null ,'未上传' , '已上传') as photoStatus,tc.photo_link as photoLink");
			sqlBuffer.append("                FROM   pe_bzz_tch_course tc, ");
			sqlBuffer
					.append("                       (select distinct course_id as course_id, scorm_type as scorm_type, indexfile as indexfile from scorm_sco_launch)  scorm, ");
			sqlBuffer.append("                       scorm_type scormType, ");
			sqlBuffer.append("                       enum_const ec1, ");
			sqlBuffer.append("                       enum_const ec2, ");
			sqlBuffer.append("                       enum_const ec3, ");
			sqlBuffer.append("                       enum_const ec4, ");
			sqlBuffer.append("                       enum_const ec5, ");
			sqlBuffer.append("                       enum_const ec6, ");
			sqlBuffer.append("                       enum_const ec7, ");
			sqlBuffer.append("                       enum_const ec8, ");
			sqlBuffer.append("                       enum_const ec9, ");
			sqlBuffer.append("                       enum_const ec10, ");
			sqlBuffer.append("                       enum_const ec11, ");
			sqlBuffer.append("                       enum_const ec12 ");
			sqlBuffer
					.append("                WHERE  tc.flag_isvalid = ec1.id(+) ");
			sqlBuffer
					.append("                       AND tc.flag_coursecategory = ec2.id(+) ");
			sqlBuffer
					.append("                       AND tc.flag_coursetype = ec3.id(+) ");
			sqlBuffer
					.append("                       AND tc.code = scorm.course_id(+) ");
			sqlBuffer
					.append("                       AND tc.flag_isrecommend = ec5.id(+) ");
			sqlBuffer
					.append("                       AND tc.flag_isfree = ec6.id(+) ");
			sqlBuffer
					.append("                       AND tc.FLAG_CANJOINBATCH =ec7.id(+) ");
			sqlBuffer
					.append("                       AND scorm.scorm_type =scormType.code(+) ");
			sqlBuffer
					.append("                       AND tc.FLAG_ISVISITAFTERPASS = ec4.id(+)  ");
			sqlBuffer
					.append("                       AND tc.FLAG_COURSE_ITEM_TYPE = ec8.id(+)  ");
			sqlBuffer
					.append("                       AND tc.FLAG_CHECK_STATUS = ec10.id(+)  ");
			sqlBuffer.append("                       AND tc.FLAG_OFFLINE = ec11.id(+)  ");
			sqlBuffer.append("                       AND tc.FLAG_CONTENT_PROPERTY = ec12.id(+)  ");
			sqlBuffer
					.append("                       AND tc.FLAG_IS_EXAM = ec9.id(+)) a ");
			sqlBuffer
					.append("               LEFT JOIN pe_bzz_tch_courseware pbtc ");
			sqlBuffer
					.append("                 ON a.id = pbtc.fk_course_id) where 1=1");

		} else {

		}
		try {
			Map params = this.getParamsMap();
			Iterator iterator = params.entrySet().iterator();
			do {
				if (!iterator.hasNext())
					break;
				java.util.Map.Entry entry = (java.util.Map.Entry) iterator
						.next();
				String name = entry.getKey().toString();
				String value = ((String[]) entry.getValue())[0].toString();
				if (!name.startsWith("search__"))
					continue;
				if ("".equals(value))
					continue;
				if (name.indexOf(".") > 1
						&& name.indexOf(".") != name.lastIndexOf(".")) {
					name = name.substring(name.lastIndexOf(".", name
							.lastIndexOf(".") - 1) + 1);
				} else {
					name = name.substring(8);
				}

				if (name.equals("enumConstByFlagIsvalid.name")) {
					name = "validName";
				}
				if (name.equals("enumConstByFlagOffline.name")) {
					name = "isoffline";
				}
				if (name.equals("enumConstByFlagCourseItemType.name")) {
					name = "courseItemType";
				}
				if (name.equals("enumConstByFlagContentProperty.name")) {
					name = "contentProperty";
				}
				if (name.equals("enumConstByFlagCourseCategory.name")) {
					name = "categoryName";
				}
				if (name.equals("enumConstByFlagCourseType.name")) {
					name = "courseTypeName";
				}
				if (name.equals("enumConstByFlagIsvisitAfterPass.name")) {
					name = "visitName";
				}
				if (name.equals("enumConstByFlagIsFree.name")) {
					name = "isFree";
				}
				if (name.equals("enumConstByFlagCheckStatus.name")) {
					name = "isCheck";
				}
				if (name.equals("enumConstByFlagIsRecommend.name")) {
					name = "isRecommend";
				}
				if (name.equals("enumConstByFlagIsExam.name")) {
					name = "isExam";
				}
				if (name.equals("enumConstByFlagCanJoinBatch.name")) {
					name = "canJoinBatch";
				}

				if (name.equals("isRecommend")) {
					sqlBuffer.append(" and " + name + " like '" + value + "'");
				} else {
					sqlBuffer
							.append(" and " + name + " like '%" + value + "%'");
				}

			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				if (temp.equals("enumConstByFlagIsvalid.name")) {
					temp = "validName";
				}
				if (temp.equals("enumConstByFlagOffline.name")) {
					temp = "isoffline";
				}
				if (temp.equals("enumConstByFlagCourseCategory.name")) {
					temp = "categoryName";
				}
				if (temp.equals("enumConstByFlagCourseType.name")) {
					temp = "courseTypeName";
				}
				if (temp.equals("enumConstByFlagIsvisitAfterPass.name")) {
					temp = "visitName";
				}
				if (temp.equals("enumConstByFlagIsFree.name")) {
					temp = "isFree";
				}
				if (temp.equals("enumConstByFlagCheckStatus.name")) {
					temp = "isCheck";
				}
				if (temp.equals("enumConstByFlagIsRecommend.name")) {
					temp = "isRecommend";
				}
				if (temp.equals("enumConstByFlagCourseItemType.name")) {
					temp = "courseItemType";
				}
				if (temp.equals("enumConstByFlagContentProperty.name")) {
					temp = "contentProperty";
				}
				if (temp.equals("enumConstByFlagIsExam.name")) {
					temp = "isExam";
				}
				if (temp.equals("enumConstByFlagCanJoinBatch.name")) {
					temp = "canJoinBatch";
				}
				if (temp.equals("id")) {
					temp = "createDate";
				}
				if (this.getDir() != null
						&& this.getDir().equalsIgnoreCase("desc")) {
					if (temp.equals("time")) {
						sqlBuffer.append(" order by to_number(" + temp
								+ ") desc ");
					} else {
						sqlBuffer.append(" order by " + temp + " desc ");
					}
				} else {
					if (temp.equals("time")) {
						sqlBuffer.append(" order by to_number(" + temp
								+ ") asc ");
					} else {
						sqlBuffer.append(" order by " + temp + " asc ");
					}
				}
			} else {
				sqlBuffer.append(" order by createDate desc");
			}
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(),
					Integer.parseInt(this.getLimit()),
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
	 * @author linjie
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
				throw new EntityException("所选课程中有未申请发布课程，请申请发布后再执行发布或加入专项操作！");
			}
			for (int i = 0; i < idList.size(); i++) {
				sql = "select t.* from scorm_course_info t inner join pe_bzz_tch_course p on t.id=p.code where p.id ='"
						+ idList.get(i) + "'";
				List list = this.getGeneralService().getBySQL(sql);
				if (list.size() < 1) {
					count++;
				}
			}
			if (count > 0) {
				throw new EntityException("所选课程中有课程无课件，请上传课件后再发布或加入专项！");
			}
			for (int i = 0; i < idList.size(); i++) {
				sql = "select p.id from pe_bzz_tch_course p  where p.id ='"
						+ idList.get(i) + "' and p.flag_isvalid='2'";
				List list = this.getGeneralService().getBySQL(sql);
				if (list.size() > 0) {
					count++;
				}
			}
			if (count > 0) {
				throw new EntityException("所选课程中有课程已经发布，请重新选择后再发布或加入专项！");
			}
		} catch (EntityException e) {
			throw e;
		}
	}

	/**
	 * 重写框架方法：更新字段
	 * 
	 * @author linjie
	 * @return
	 */
	@SuppressWarnings( { "unchecked", "unchecked", "unchecked" })
	public Map updateColumn() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
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
			if (action.equals("FlagIsvalid.true")
					|| action.equals("FlagCanJoinBatch.true")) {
				try {
					checkBeforeUpdateColumn(idList);
				} catch (EntityException e1) {
					map.put("success", "false");
					map.put("info", e1.getMessage());
					return map;
				}
			}
			try {

				DetachedCriteria tempdc = DetachedCriteria
						.forClass(PeBzzTchCourse.class);
				tempdc.createCriteria("enumConstByFlagIsvalid");

				if (action.equals("FlagIsvalid.true")) {
					EnumConst enumConst = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagIsvalid", "1");
					EnumConst enumConstByFlagCanJoinBatch = this
							.getMyListService().getEnumConstByNamespaceCode(
									"FlagCanJoinBatch", "0"); // 课程不可添加到专项中
					tempdc.add(Restrictions.in("id", ids));
					tempdc.createCriteria("enumConstByFlagCanJoinBatch",
							DetachedCriteria.LEFT_JOIN);
					tempdc.add(Restrictions.isNotNull("satisfactionId"));
					List<PeBzzTchCourse> courselist = new ArrayList<PeBzzTchCourse>();
					courselist = this.getGeneralService().getList(tempdc);
					if (courselist.size() != 0) {
						String info = this.checkCourseCanValid(courselist);
						if (info.length() != 0) {
							map.put("success", "false");
							map.put("info", info);
							return map;
						}
					}
					if (null == courselist || courselist.size() != ids.length) {
						map.put("success", "false");
						map.put("info", "所选课程中有课程未添加满意度调查,请添加再发布");
						return map;
					}
					existNum = opBatchMsg(courselist, "1", "course");
					if (existNum > 0) {
						map.clear();
						map.put("success", "false");
						map.put("info", "所选课程中有课程已经发布，请重新选择后再执行操作！");
						return map;
					}
					existNum = opBatchMsg(courselist, "2", "course");
					if (existNum < 1) {
						map.clear();
						map.put("success", "false");
						map.put("info", "所选课程中有课程没有申请发布，请重新选择后再执行操作！");
						return map;
					}

					Iterator<PeBzzTchCourse> iterator = courselist.iterator();
					while (iterator.hasNext()) {
						PeBzzTchCourse peBzzTchCourse = iterator.next();
						EnumConst ec = peBzzTchCourse
								.getEnumConstByFlagCanJoinBatch();
						if (ec != null) {
							if (ec.getCode().equals("1")) {
								map.put("success", "false");
								map.put("info", "所选课程中有课程已经添加到专项，请先取消专项在进行发布");
								return map;
							}
						}
						String logs = peBzzTchCourse.getOperationLogs();
						if (logs == null) {
							logs = "";
						}
						StringBuffer buf = new StringBuffer(logs);
						buf.append("|" + us.getLoginId() + "/"
								+ us.getUserName() + "执行了课程发布操作\n");
						peBzzTchCourse.setOperationLogs(buf.toString());
						peBzzTchCourse.setEnumConstByFlagIsvalid(enumConst);
						peBzzTchCourse
								.setEnumConstByFlagCanJoinBatch(enumConstByFlagCanJoinBatch);
						this.getGeneralService().save(peBzzTchCourse);
					}
					msg = "课程发布";
				}

				if (action.equals("FlagIsvalid.false")) {

					try {
						checkBeforeDelete(idList);
					} catch (EntityException e) {
						map.put("success", "false");
						map.put("info", e.getMessage() + "，无法设为无效！");
						return map;
					}

					EnumConst enumConst = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagIsvalid", "0");
					tempdc.add(Restrictions.in("id", ids));
					List<PeBzzTchCourse> courselist = new ArrayList<PeBzzTchCourse>();
					courselist = this.getGeneralService().getList(tempdc);
					existNum = opBatchMsg(courselist, "0", "course");
					if (existNum > 0) {
						map.clear();
						map.put("success", "false");
						map.put("info", "所选课程中有课程已经停止，请重新选择后再执行操作！");
						return map;
					}
					Iterator<PeBzzTchCourse> iterator = courselist.iterator();
					while (iterator.hasNext()) {
						PeBzzTchCourse peBzzTchCourse = iterator.next();
						String logs = peBzzTchCourse.getOperationLogs();
						if (logs == null) {
							logs = "";
						}
						StringBuffer buf = new StringBuffer(logs);
						buf.append("|" + us.getLoginId() + "/"
								+ us.getUserName() + "执行了课程停用操作\n");
						peBzzTchCourse.setOperationLogs(buf.toString());
						peBzzTchCourse.setEnumConstByFlagIsvalid(enumConst);
						this.getGeneralService().save(peBzzTchCourse);
					}
					msg = "课程停用";
				}
				if (action.equals("FlagOffline.true")) {
					EnumConst enumConst = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagOffline", "1");
					EnumConst enumConstByFlagCanJoinBatch = this
							.getMyListService().getEnumConstByNamespaceCode
							("FlagCanJoinBatch", "0"); // 课程不可添加到专项中
					
					tempdc.add(Restrictions.in("id", ids));
					tempdc.createCriteria("enumConstByFlagOffline",
							DetachedCriteria.LEFT_JOIN);
					tempdc.createCriteria("enumConstByFlagCanJoinBatch",
							DetachedCriteria.LEFT_JOIN);
					tempdc.add(Restrictions.isNotNull("satisfactionId"));
					List<PeBzzTchCourse> courselist = new ArrayList<PeBzzTchCourse>();
					courselist = this.getGeneralService().getList(tempdc);
					if (courselist.size() != 0) {
						String info = this.checkCourseCanValid(courselist);
						if (info.length() != 0) {
							map.put("success", "false");
							map.put("info", "所选课程中有课程已经添加到专项，请先取消专项在进行下线");
							return map;
						}
					}

					Iterator<PeBzzTchCourse> iterator = courselist.iterator();
					while (iterator.hasNext()) {
						PeBzzTchCourse peBzzTchCourse = iterator.next();
						EnumConst ec = peBzzTchCourse
								.getEnumConstByFlagCanJoinBatch();
						if (ec != null) {
							if (ec.getCode().equals("1")) {
								map.put("success", "false");
								map.put("info", "所选课程中有课程已经添加到专项，请先取消专项在进行下线");
								return map;
							}
						}
						ec = peBzzTchCourse.getEnumConstByFlagOffline();
						
						if (!ec.getCode().equals("2")) {
							map.clear();
							map.put("success", "false");
							map.put("info", "所选课程中有课程没有申请下线，请重新选择后再执行操作！");
							return map;
						}
						String logs = peBzzTchCourse.getOperationLogs();
						if (logs == null) {
							logs = "";
						}
						StringBuffer buf = new StringBuffer(logs);
						buf.append("|" + us.getLoginId() + "/"
								+ us.getUserName() + "执行了课程下线操作\n");
						peBzzTchCourse.setOperationLogs(buf.toString());
						peBzzTchCourse.setEnumConstByFlagOffline(enumConst);
						peBzzTchCourse
								.setEnumConstByFlagCanJoinBatch(enumConstByFlagCanJoinBatch);
						this.getGeneralService().save(peBzzTchCourse);
					}
					msg = "课程下线";
				}

				if (action.equals("FlagOffline.false")) {

					EnumConst enumConst = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagOffline", "0");
					tempdc.add(Restrictions.in("id", ids));
					tempdc.createCriteria("enumConstByFlagOffline",
							DetachedCriteria.LEFT_JOIN);
					
					List<PeBzzTchCourse> courselist = new ArrayList<PeBzzTchCourse>();
					courselist = this.getGeneralService().getList(tempdc);
					if (courselist.size() != 0) {
						String info = this.checkCourseCanValid(courselist);
						if (info.length() != 0) {
							map.put("success", "false");
							map.put("info", "所选课程中有课程已经添加到专项，请先取消专项在进行下线");
							return map;
						}
					}
					Iterator<PeBzzTchCourse> iterator = courselist.iterator();
					while (iterator.hasNext()) {
						PeBzzTchCourse peBzzTchCourse = iterator.next();
						EnumConst ec = peBzzTchCourse.getEnumConstByFlagOffline();
						if (!ec.getCode().equals("2")) {
							map.clear();
							map.put("success", "false");
							map.put("info", "所选课程中有课程没有申请下线，请重新选择后再执行操作！");
							return map;
						}
						String logs = peBzzTchCourse.getOperationLogs();
						if (logs == null) {
							logs = "";
						}
						StringBuffer buf = new StringBuffer(logs);
						buf.append("|" + us.getLoginId() + "/"
								+ us.getUserName() + "执行了课程下线操作\n");
						peBzzTchCourse.setOperationLogs(buf.toString());
						peBzzTchCourse.setEnumConstByFlagOffline(enumConst);
						this.getGeneralService().save(peBzzTchCourse);
					}
					msg = "课程下线";
				}
				if (action.equals("FlagCanJoinBatch.true")) {
					EnumConst enumConst = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagCanJoinBatch",
									"1");
					tempdc.add(Restrictions.in("id", ids));
					tempdc.add(Restrictions.isNotNull("satisfactionId"));
					List<PeBzzTchCourse> courselist = new ArrayList<PeBzzTchCourse>();
					courselist = this.getGeneralService().getList(tempdc);
					/*
					 * 校验，如果课程未发布状态，那么就不能加入到专项中去
					 */
					if (null == courselist || courselist.size() != ids.length) {
						map.put("success", "false");
						map.put("info", "所选课程中有课程未添加满意度调查,请添加再发布");
						return map;
					}
					for (int i = 0; i < courselist.size(); i++) {
						EnumConst enumConstByFlagIsvalid = courselist.get(i)
								.getEnumConstByFlagIsvalid();
						if (enumConstByFlagIsvalid != null) {
							if (enumConstByFlagIsvalid.getCode().equals("1")) {
								map.put("success", "false");
								map.put("info", "课程为发布状态，不能添加到专项中");
								return map;
							}
						}
					}

					/*
					 * 校验，如果课程未免费的话，那么就不能加入到专项中去，需求需要，去除此限制
					 */
					/***********************************************************
					 * for(int i=0; i<courselist.size(); i++) { EnumConst
					 * enumConstByFlagIsFree =
					 * courselist.get(i).getEnumConstByFlagIsFree();
					 * if(enumConstByFlagIsFree != null) {
					 * if(enumConstByFlagIsFree.getCode().equals("1")) {
					 * map.put("success", "false"); map.put("info",
					 * "课程为免费，不能添加到专项中"); return map; } } }
					 **********************************************************/
					existNum = opBatchMsg(courselist, "1", "batch");
					if (existNum > 0) {
						map.clear();
						map.put("success", "false");
						map.put("info", "所选课程中有课程已有为专项课程，请重新选择后再执行操作！");
						return map;
					}
					Iterator<PeBzzTchCourse> iterator = courselist.iterator();

					while (iterator.hasNext()) {
						PeBzzTchCourse peBzzTchCourse = iterator.next();
						String logs = peBzzTchCourse.getOperationLogs();
						if (logs == null) {
							logs = "";
						}
						StringBuffer buf = new StringBuffer(logs);
						buf.append("|" + us.getLoginId() + "/"
								+ us.getUserName() + "执行了课程添加专项操作\n");
						peBzzTchCourse.setOperationLogs(buf.toString());
						peBzzTchCourse
								.setEnumConstByFlagCanJoinBatch(enumConst);
						this.getGeneralService().save(peBzzTchCourse);
					}
					msg = "添加专项课程操作";
				}
				if (action.equals("FlagCanJoinBatch.false")) {
					EnumConst enumConst = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagCanJoinBatch",
									"0");
					tempdc.add(Restrictions.in("id", ids));
					List<PeBzzTchCourse> courselist = new ArrayList<PeBzzTchCourse>();
					courselist = this.getGeneralService().getList(tempdc);
					if (courselist.size() != 0) {
						String info = this.checkIsSpecial(courselist);
						if (info.length() != 0) {
							map.put("success", "false");
							map.put("info", info);
							return map;
						}
					}
					existNum = opBatchMsg(courselist, "0", "batch");
					if (existNum > 0) {
						map.clear();
						map.put("success", "false");
						map.put("info", "所选课程中有课程已有不为专项课程，请重新选择后再执行操作！");
						return map;
					}
					Iterator<PeBzzTchCourse> iterator = courselist.iterator();
					while (iterator.hasNext()) {
						PeBzzTchCourse peBzzTchCourse = iterator.next();
						String logs = peBzzTchCourse.getOperationLogs();
						if (logs == null) {
							logs = "";
						}
						StringBuffer buf = new StringBuffer(logs);
						buf.append("|" + us.getLoginId() + "/"
								+ us.getUserName() + "执行了课程取消专项操作\n");
						peBzzTchCourse.setOperationLogs(buf.toString());
						peBzzTchCourse
								.setEnumConstByFlagCanJoinBatch(enumConst);
						this.getGeneralService().save(peBzzTchCourse);
					}
					msg = "取消专项课程操作";
				}
				/**
				 * 课程审核
				 */
				if (action.equals("checkStatus")) {
					EnumConst enumConstFlagIsvalid = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagIsvalid", "2"); // 课程审核状态，待审核
					try {
						this.checkStatus(idList);
					} catch (EntityException e1) {
						map.put("success", "false");
						map.put("info", e1.getMessage());
						return map;
					}
					this.checkStatus(idList);
					tempdc.add(Restrictions.in("id", ids));
					tempdc.add(Restrictions.isNotNull("satisfactionId"));
					List<PeBzzTchCourse> courselist = new ArrayList<PeBzzTchCourse>();
					courselist = this.getGeneralService().getList(tempdc);
					if (null == courselist || courselist.size() != ids.length) {
						map.put("success", "false");
						map.put("info", "所选课程中有课程未添加满意度调查,请再申请发布");
						return map;
					}
					existNum = opBatchMsg(courselist, "1", "checkStatus");
					if (existNum > 0) {
						map.clear();
						map.put("success", "false");
						map.put("info", "所选课程中有课程已经申请发布，请重新选择后再执行操作！");
						return map;
					}
					Iterator<PeBzzTchCourse> iterator = courselist.iterator();
					while (iterator.hasNext()) {
						PeBzzTchCourse peBzzTchCourse = iterator.next();
						String logs = peBzzTchCourse.getOperationLogs();
						if (logs == null) {
							logs = "";
						}
						StringBuffer buf = new StringBuffer(logs);
						buf.append("|" + us.getLoginId() + "/"
								+ us.getUserName() + "执行了课程申请发布操作\n");
						peBzzTchCourse.setOperationLogs(buf.toString());
						// peBzzTchCourse.setEnumConstByFlagCheckStatus(enumConstFlagIsvalid);
						peBzzTchCourse
								.setEnumConstByFlagIsvalid(enumConstFlagIsvalid);
						this.getGeneralService().save(peBzzTchCourse);
					}
					msg = "课程申请发布";
				}
				/**
				 * 申请下线
				 */
				if (action.equals("applyOffline")) {
					EnumConst enumConstFlagOffline = this.getMyListService().getEnumConstByNamespaceCode("FlagOffline", "2"); // 课程审核状态，待审核
					tempdc.add(Restrictions.in("id", ids));
					tempdc.add(Restrictions.isNotNull("satisfactionId"));
					List<PeBzzTchCourse> courselist = new ArrayList<PeBzzTchCourse>();
					courselist = this.getGeneralService().getList(tempdc);
					Iterator<PeBzzTchCourse> iterator = courselist.iterator();
					while (iterator.hasNext()) {
						PeBzzTchCourse peBzzTchCourse = iterator.next();
						EnumConst ec = peBzzTchCourse.getEnumConstByFlagCanJoinBatch();
					if (ec != null) {
						if (ec.getCode().equals("1")) {
							map.put("success", "false");
							map.put("info", "所选课程中有课程已经添加到专项，请先取消专项在进行下线");
							return map;
						}
					}
						String logs = peBzzTchCourse.getOperationLogs();
						if (logs == null) {
							logs = "";
						}
						StringBuffer buf = new StringBuffer(logs);
						buf.append("|" + us.getLoginId() + "/"+ us.getUserName() + "执行了课程申请下线操作\n");
						peBzzTchCourse.setOperationLogs(buf.toString());
						// peBzzTchCourse.setEnumConstByFlagCheckStatus(enumConstFlagIsvalid);
						peBzzTchCourse.setEnumConstByFlagOffline(enumConstFlagOffline);
						this.getGeneralService().save(peBzzTchCourse);
					}
					msg = "课程申请下线";
				}

				map.put("success", "true");
				map.put("info", msg + "共有" + ids.length + "门课程操作成功");
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

	/**
	 * 上传课件
	 * 
	 * @author linjie
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
	 * @author linjie
	 * @return
	 */
	public void createCourseware() {
		this.peBzzTchCourseware = new PeBzzTchCourseware();
		this.peBzzTchCourseware.setPeBzzTchCourse(this.getBean());
		this.peBzzTchCourseware.setCode(this.getBean().getCode());
		this.peBzzTchCourseware.setName(this.getBean().getName());
		try {
			this.peBzzTchCourseware = this.getPeTchCourseService()
					.savePeBzzTchCourseware(this.peBzzTchCourseware);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 判断课程是否有课件
	 * 
	 * @author linjie
	 * @return
	 */
	public boolean hasCourseware() {
		try {
			this.setBean(this.getGeneralService().getById(
					this.getBean().getId()));
			DetachedCriteria dCourseware = DetachedCriteria
					.forClass(PeBzzTchCourseware.class);
			dCourseware.createAlias("peBzzTchCourse", "peBzzTchCourse");
			dCourseware.add(Restrictions.eq("peBzzTchCourse.id", this.getBean()
					.getId()));

			List coursewareList = this.getGeneralService().getList(dCourseware);
			if (coursewareList != null && coursewareList.size() > 0) {
				this.peBzzTchCourseware = (PeBzzTchCourseware) coursewareList
						.get(0);
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
	 * @author linjie
	 * @return
	 */
	private StringBuffer getScript() {
		StringBuffer script = new StringBuffer();
		script.append("	var indexInput = new Ext.form.TextField({     ");
		script.append("                  fieldLabel: '课件首页',allowBlank:false,");
		script
				.append("                  name:'indexFile',blankText: ''});    ");
		script
				.append("	var fid = new Ext.form.Hidden({name:'courseware_code',value:'${value}'}); ");
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
		script
				.append("                 formPanel.getForm().submit({          ");
		script
				.append("	                 url:'/entity/teaching/peBzzCourseManager_updateIndex.action',   ");
		script.append("	                 waitMsg:'更新中...',     ");
		script.append("	                 success: function(form, action) {  ");
		script.append("		               var responseArray = action.result; ");
		script
				.append("		               if(responseArray.success=='true'){  addModelWin.close();storePageForReload.reload();   ");
		script
				.append("			             Ext.MessageBox.alert('成功', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');");
		script
				.append("		               } else {  addModelWin.close();           ");
		script
				.append("			             Ext.MessageBox.alert('失败', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');");
		script.append("		               }                        ");
		script.append("	                 },");
		script.append("                  failure:function(form, action){");
		script.append("		               var responseArray = action.result; ");
		script
				.append("		               if(responseArray.success=='false'){  addModelWin.close();storePageForReload.reload();   ");
		script
				.append("			             Ext.MessageBox.alert('成功', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');");
		script
				.append("		               } else { addModelWin.show();          ");
		script
				.append("			             Ext.MessageBox.alert('失败', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');");
		script.append("		               }                        ");
		script.append("                  }        ");
		script.append("                 });            ");
		script.append("		  	     }else{   ");
		script
				.append("		  	       Ext.MessageBox.alert('错误', '输入错误，请先修正提示的错误');  ");
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
	 * @author linjie
	 * @return
	 */
	public String updateIndex() {
		Map map = new HashMap();
		String querySql = "select * from scorm_sco_launch where course_id='"
				+ this.getCourseware_code() + "'";
		String sql = "update scorm_sco_launch t set indexfile='"
				+ this.getIndexFile() + "' where course_id='"
				+ this.getCourseware_code() + "'";
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
	 * @author linjie
	 * @return
	 */
	public String copyHomework() {
		List<PeBzzTchCourse> list = new ArrayList<PeBzzTchCourse>();
		Map session = ActionContext.getContext().getSession();
		for (int i = 0; i < this.getIds().split(",").length; i++) {
			DetachedCriteria dc = DetachedCriteria
					.forClass(PeBzzTchCourse.class);
			dc.createCriteria("enumConstByFlagIsvalid",
					"enumConstByFlagIsvalid");
			dc.createCriteria("enumConstByFlagCourseType",
					"enumConstByFlagCourseType");
			dc
					.createCriteria("enumConstByFlagCourseCategory",
							"enumConstByFlagCourseCategory",
							DetachedCriteria.LEFT_JOIN);
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
	 * @author linjie
	 * @return
	 */
	public String copyTestPaper() {
		List<PeBzzTchCourse> list = new ArrayList<PeBzzTchCourse>();
		Map session = ActionContext.getContext().getSession();
		for (int i = 0; i < this.getIds().split(",").length; i++) {
			DetachedCriteria dc = DetachedCriteria
					.forClass(PeBzzTchCourse.class);
			dc.createCriteria("enumConstByFlagIsvalid",
					"enumConstByFlagIsvalid");
			dc.createCriteria("enumConstByFlagCourseType",
					"enumConstByFlagCourseType");

			dc
					.createCriteria("enumConstByFlagCourseCategory",
							"enumConstByFlagCourseCategory",
							DetachedCriteria.LEFT_JOIN);
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
	 * @author linjie
	 * @return
	 */
	public String courseInfo() {

		// ServletActionContext.getRequest()
		// .getSession().setAttribute("peBzzCourse", this.getBean());
		String id = ServletActionContext.getRequest().getParameter("id");
		EnumConst ec = this.getEnumConstService().getByNamespaceCode(
				"ClassHourRate", "0");
		price = Double.parseDouble(ec.getName());
		this.indexFile = ServletActionContext.getRequest().getParameter(
				"indexFile");
		try {
			this.setBean(this.getGeneralService().getById(id));
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "courseInfo";
	}

	/**
	 * 框架方法：或者查询列表所需数据
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzTchCourse.class);
		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		dc.createCriteria("enumConstByFlagCourseType",
				"enumConstByFlagCourseType");
		dc.createCriteria("ssoUser", "ssoUser").createAlias(
				"enumConstByFlagIsvalid", "enumConstByFlagIsvalid",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagCourseCategory",
				"enumConstByFlagCourseCategory", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagCanJoinBatch",
				"enumConstByFlagCanJoinBatch", DetachedCriteria.LEFT_JOIN);
		// dc.addOrder(Order.asc("suqNum"));
		return dc;
	}

	/**
	 * 20121221添加，用于校验专项课程是否可以发布
	 * 
	 * @return
	 */
	public String checkCourseCanValid(List<PeBzzTchCourse> courselist) {
		Map map = new HashMap();
		Map info = new HashMap(); // 用于记录所有的
		String action = this.getColumn();
		DetachedCriteria dc = DetachedCriteria
				.forClass(PrBzzTchOpencourse.class);
		dc.createCriteria("peBzzBatch", "peBzzBatch");
		dc.createCriteria("peBzzBatch.enumConstByFlagBatchType",
				"enumConstByFlagBatchType");
		PeBzzTchCourse[] ss = courselist.toArray(new PeBzzTchCourse[courselist
				.size()]);
		dc.add(Restrictions.in("peBzzTchCourse", courselist
				.toArray(new PeBzzTchCourse[courselist.size()])));
		List<PrBzzTchOpencourse> opList = null;
		try {
			opList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * 将课程对应的专项全部检索出来
		 */
		for (int i = 0; i < courselist.size(); i++) {
			List list = new ArrayList();

			for (int j = 0; j < opList.size(); j++) {
				if (opList.get(j).getPeBzzTchCourse().equals(courselist.get(i))
						&& !opList.get(j).getPeBzzBatch()
								.getEnumConstByFlagBatchType().getCode()
								.equals("1")) {
					list.add(opList.get(j));
				}
			}
			map.put(courselist.get(i), list);
		}
		try {
			for (int i = 0; i < courselist.size(); i++) {
				List list = (List) map.get(courselist.get(i));
				for (int j = 0; j < list.size(); j++) {
					info.put(courselist.get(i),
							checkBatch((PrBzzTchOpencourse) list.get(j)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuffer infoBuffer = new StringBuffer();
		if (!info.isEmpty()) {
			for (int i = 0; i < courselist.size(); i++) {
				Map course = (Map) info.get(courselist.get(i));
				List<StudentBatch> sbList = (List<StudentBatch>) course
						.get("error");
				for (StudentBatch sb : sbList) {
					if (action.equals("FlagCanJoinBatch.false")) {
						infoBuffer.append("编号为：" + courselist.get(i).getCode()
								+ "的课程，已经被支付，请等待此专项报名日期结束再行取消<br>");

					} else {
						infoBuffer.append("编号为：" + courselist.get(i).getCode()
								+ "的课程，已经被《" + sb.getPeBzzBatch().getName()
								+ "》的学员“" + sb.getPeStudent().getName()
								+ "”专项支付，如需发布，请等待过了最后报名日期<br>");
					}
					infoBuffer.append("<br>");
				}

				if (sbList.size() == 0) {
					List<StudentBatch> sbStuList = (List<StudentBatch>) course
							.get("student");
					for (StudentBatch sbStu : sbStuList) {
						if (action.equals("FlagCanJoinBatch.false")) {
							infoBuffer.append("编号为："
									+ courselist.get(i).getCode() + "的课程，存在于《"
									+ sbStu.getPeBzzBatch().getName()
									+ "》的专项中，且未支付，不能取消<br>");
							break;
						} else {
							infoBuffer.append("编号为："
									+ courselist.get(i).getCode() + "的课程，存在于《"
									+ sbStu.getPeBzzBatch().getName()
									+ "》的专项中，如需发布，请先将专项中未支付的学员“"
									+ sbStu.getPeStudent().getName()
									+ "”删除<br>");
						}
						infoBuffer.append("<br>");
					}
				}
			}

		}

		return infoBuffer.toString();
	}

	/**
	 * 专项校验
	 * 
	 * @author linjie
	 * @return
	 */
	private Map checkBatch(PrBzzTchOpencourse op) {
		Map map = new HashMap();
		PeBzzBatch b = op.getPeBzzBatch();
		DetachedCriteria dc = DetachedCriteria.forClass(StudentBatch.class);
		dc.createCriteria("enumConstByFlagBatchPayState");
		dc.createCriteria("peStudent");
		dc.createCriteria("peBzzBatch");
		dc.add(Restrictions.eq("peBzzBatch", b));
		List<StudentBatch> sbList = null;
		try {
			sbList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * 专项是否已过时间
		 */
		List studentList = new ArrayList();
		List errorList = new ArrayList();
		Date endDate = b.getEndDate();
		for (int i = 0; i < sbList.size(); i++) {
			EnumConst ec = sbList.get(i).getEnumConstByFlagBatchPayState();
			if (ec.getCode().equals("0")) { // 0为未支付
				// map.put("student", sbList.get(i));
				studentList.add(sbList.get(i));
			}
			if (endDate.before(new Date())) { // 已过
				//System.out.println(endDate.after(new Date()));
			} else { // 未过
				if (!ec.getCode().equals("0")) { // 已支付
					// map.put("error", sbList.get(i));
					// //记录已支付，且未过期，此门课程将不能发布，记录所属专项
					errorList.add(sbList.get(i));
				}
			}

		}
		map.put("student", studentList);
		map.put("error", errorList);

		return map;
	}

	/**
	 * 校验专项是否可以取消
	 * 
	 * @author 魏慧宁
	 * @return
	 */
	public String checkIsSpecial(List<PeBzzTchCourse> courselist) {
		Map map = new HashMap(); // key：课程 value：专项
		Map info = new HashMap(); // key:课程 value：提示信息
		String action = this.getColumn();
		DetachedCriteria dc = DetachedCriteria
				.forClass(PrBzzTchOpencourse.class);
		dc.createCriteria("peBzzBatch", "peBzzBatch");
		dc.createCriteria("peBzzBatch.enumConstByFlagBatchType",
				"enumConstByFlagBatchType");
		PeBzzTchCourse[] ss = courselist.toArray(new PeBzzTchCourse[courselist
				.size()]);
		dc.add(Restrictions.in("peBzzTchCourse", courselist
				.toArray(new PeBzzTchCourse[courselist.size()])));
		List<PrBzzTchOpencourse> opList = null;
		try {
			opList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		/*
		 * 第一步： 查出课程对应的专项
		 */
		for (int i = 0; i < courselist.size(); i++) {
			List list = new ArrayList();

			for (int j = 0; j < opList.size(); j++) {
				if (opList.get(j).getPeBzzTchCourse().equals(courselist.get(i))
						&& !opList.get(j).getPeBzzBatch()
								.getEnumConstByFlagBatchType().getCode()
								.equals("1")) {
					list.add(opList.get(j));
				}
			}
			map.put(courselist.get(i), list);// ?没专项也放进去了。。。
		}
		/*
		 * 第二步： 检验专项是否开始： 1开始-不能取消； 2未开始-可以取消；
		 */
		try {
			for (int i = 0; i < courselist.size(); i++) {
				if (!"".equals(map.get(courselist.get(i)))) {
					List list = (List) map.get(courselist.get(i));
					if (list.size() > 0) {
						for (int j = 0; j < list.size(); j++) {
							info.put(courselist.get(i),
									checkIsStart((PrBzzTchOpencourse) list
											.get(j)));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuffer infoBuffer = new StringBuffer();
		if (!info.isEmpty()) {
			/*
			 * 第二步： 遍历课程，并取出在map-info中的，即不能被取消的，并提示。
			 */
			for (int k = 0; k < courselist.size(); k++) {
				if (info.containsKey(courselist.get(k))) {
					String msg = info.get(courselist.get(k)).toString();
					if (" ".equals(msg)) {
						break;
					}
					infoBuffer.append(msg);
				}
			}

		}

		return infoBuffer.toString();
	}

	/**
	 * 检验专项是否开始： 已开始-增加提示，不能取消，放入map中
	 * 
	 * @author linjie
	 * @return
	 */
	private String checkIsStart(PrBzzTchOpencourse op) {
		String msg = " ";
		PeBzzBatch batch = op.getPeBzzBatch();
		/*
		 * 是否存在专项
		 */
		if (batch != null) {
			msg = "课程'" + op.getPeBzzTchCourse().getCode() + "'已存在于专项'"
					+ op.getPeBzzBatch().getName() + "',不能取消。  ";
		}
		return msg;
	}

	/**
	 * 20130118添加 用于增加是否添加到专项中详细的提示信息
	 * 
	 * @return
	 */
	private int opBatchMsg(List<PeBzzTchCourse> courseList, String code,
			String flag) {
		int i = 0;
		for (PeBzzTchCourse p : courseList) {
			EnumConst ec = null;
			EnumConst ec1 = null;
			if ("batch".equals(flag)) {
				ec = p.getEnumConstByFlagCanJoinBatch();
				if (ec != null) {
					if (code.equals(ec.getCode())) {
						i++;
					}

				}
				ec1 = p.getEnumConstByFlagIsvalid();
				if (ec1 != null) {
					if (!"0".equals(ec1.getCode())) {
						i++;
					}

				}
			} else if ("course".equals(flag)) {
				ec = p.getEnumConstByFlagIsvalid();
				if (ec != null) {
					if (code.equals(ec.getCode())) {
						i++;
					}

				}
			} else if ("checkStatus".equals(flag)) {
				ec = p.getEnumConstByFlagIsvalid();
				if (ec != null) {
					if (!"0".equals(ec.getCode())) {
						i++;
					}

				}
				ec1 = p.getEnumConstByFlagCanJoinBatch();
				if (ec1 != null) {
					if ("1".equals(ec1.getCode())) {
						i++;
					}
				}
			}
		}

		return i;
	}

	/**
	 * 检查是否已上传课件
	 * 
	 * @author linjie
	 * @return
	 */
	public void checkStatus(List idList) throws EntityException {
		int count = 0;
		String sql = "";
		try {
			for (int i = 0; i < idList.size(); i++) {
				sql = "select t.* from scorm_course_info t inner join pe_bzz_tch_course p on t.id=p.code where p.id ='"
						+ idList.get(i) + "'";
				List list = this.getGeneralService().getBySQL(sql);
				if (list.size() < 1) {
					count++;
				}
			}
			if (count > 0) {
				throw new EntityException("所选课程中有课程无课件，请上传课件后再申请发布！");
			}

		} catch (EntityException e) {
			throw e;
		}
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

}
