package com.whaty.platform.entity.web.action.teaching.basicInfo;

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
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
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
 * 专项课程发布Action 2014年07月09日
 * 
 * @author Lee
 * 
 */
public class PeBzzCourseBatManagerAction extends MyBaseAction<PeBzzTchCourse> {

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
		/* 添加按钮 */
		if (capabilitySet.contains(this.servletPath + "_add.action")) {
			canAdd = true;
		}
		/* 删除按钮 */
		if (capabilitySet.contains(this.servletPath + "_delete.action")) {
			canDelete = true;
		}
		/* 双击修改 */
		if (capabilitySet.contains(this.servletPath + "_update.action")) {
			canUpdate = true;
		}
		this.getGridConfig().setCapability(canAdd, canDelete, canUpdate);

		this.getGridConfig().setTitle("专项课程发布");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("课程编号"), "code", true, true, true, Const.coursecode_for_extjs);

		this.getGridConfig().addColumn(this.getText("课程名称"), "name", true, true, true, "TextField", false, 200, "");

		ColumnConfig columnConfigType = new ColumnConfig(this.getText("课程性质"), "enumConstByFlagCourseType.name", true, true, false, "TextField", false, 100, "");
		String sql = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseType'";
		columnConfigType.setComboSQL(sql);
		this.getGridConfig().addColumn(columnConfigType);

		ColumnConfig columnConfigCategory = new ColumnConfig(this.getText("业务分类"), "enumConstByFlagCourseCategory.name", true, true, false, "TextField", false, 100, "");
		String sql2 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseCategory'";
		columnConfigCategory.setComboSQL(sql2);
		this.getGridConfig().addColumn(columnConfigCategory);

		ColumnConfig columnCourseItemType = new ColumnConfig(this.getText("大纲分类"), "enumConstByFlagCourseItemType.name", true, true, false, "TextField", false, 100, "");
		String sql7 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseItemType'";
		columnCourseItemType.setComboSQL(sql7);
		this.getGridConfig().addColumn(columnCourseItemType);

		ColumnConfig columnContentProperty = new ColumnConfig(this.getText("内容属性分类"), "enumConstByFlagContentProperty.name", true, true, false, "TextField", false, 100, "");
		String sql9 = "select a.id ,a.name from enum_const a where a.namespace='FlagContentProperty'";
		columnContentProperty.setComboSQL(sql9);
		this.getGridConfig().addColumn(columnContentProperty);

		ColumnConfig columnConfigIsvalid = new ColumnConfig(this.getText("是否发布"), "enumConstByFlagIsvalid.name", true, false, true, "TextField", false, 100, "");
		String sql3 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsvalid'";
		columnConfigIsvalid.setComboSQL(sql3);
		this.getGridConfig().addColumn(columnConfigIsvalid);
		ColumnConfig columnConfigOffline = new ColumnConfig(this.getText("是否下线"), "enumConstByFlagOffline.name", true, false, true, "TextField", false, 100, "");
		String sql33 = "select a.id ,a.name from enum_const a where a.namespace='FlagOffline'";
		columnConfigIsvalid.setComboSQL(sql33);
		this.getGridConfig().addColumn(columnConfigOffline);

		ColumnConfig columnConfigIsExam = new ColumnConfig(this.getText("是否考试"), "enumConstByFlagIsExam.name", true, true, true, "TextField", false, 100, "");
		String sqlx = "select a.id ,a.name from enum_const a where a.namespace='FlagIsExam'";
		columnConfigIsExam.setComboSQL(sqlx);
		this.getGridConfig().addColumn(columnConfigIsExam);

		// ColumnConfig columnConfigRecommend = new
		// ColumnConfig(this.getText("推荐状态"), "enumConstByFlagIsRecommend.name",
		// false, true, false, "TextField", false, 100, "");
		// String sql5 =
		// "select a.id ,a.name from enum_const a where a.namespace='FlagIsRecommend'";
		// columnConfigRecommend.setComboSQL(sql5);
		// this.getGridConfig().addColumn(columnConfigRecommend);

		ColumnConfig columnConfigFree = new ColumnConfig(this.getText("收费状态"), "enumConstByFlagIsFree.name", true, true, false, "TextField", false, 100, "");
		String sql6 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsFree'";
		columnConfigFree.setComboSQL(sql6);
		this.getGridConfig().addColumn(columnConfigFree);

		this.getGridConfig().addColumn(this.getText("金额"), "price", true, true, true, Const.fee_for_extjs);

		this.getGridConfig().addColumn(this.getText("学时数"), "time", true, true, true, Const.fee_for_extjs);

		//this.getGridConfig().addColumn(this.getText("课程时长(分钟)"), "courseLen", false, true, true, Const.number_for_extjs);
		
		this.getGridConfig().addColumn(this.getText("课程时长(分钟)"), "courseLen", true, true, true, "TextField", true, 25);
		
		this.getGridConfig().addColumn(this.getText("主讲人"), "teacher", true, true, true, "TextField", true, 25);

		this.getGridConfig().addColumn(this.getText("课件id"), "coursewareId", false, false, false, "");

		this.getGridConfig().addColumn(this.getText("课件类型"), "scormType", false, false, false, "");

		this.getGridConfig().addColumn(this.getText("课件首页"), "indexFile", false, false, false, "");

		this.getGridConfig().addColumn(this.getText("满意度调查"), "sid", false, false, false, "");

		this.getGridConfig().addColumn(this.getText("学习期限(天)"), "studyDates", false, true, false, Const.number_for_extjs);

		this.getGridConfig().addColumn(this.getText("拟停用日期"), "endDate", false, true, false, "TextField", true, 10);

		this.getGridConfig().addColumn(this.getText("发布日期"), "announceDate", false, false, false, "TextField", false, 10);

		this.getGridConfig().addColumn(this.getText("答疑开始时间"), "answerBeginDate", false, true, false, "TextField", true, 10);

		this.getGridConfig().addColumn(this.getText("答疑结束时间"), "answerEndDate", false, true, false, "TextField", true, 10);

		this.getGridConfig().addColumn(this.getText("通过规则(百分比分数线)"), "passRole", false, true, false, Const.oneTwoNum_for_extjs);

		this.getGridConfig().addColumn(this.getText("考试限制次数"), "examTimesAllow", false, true, false, Const.oneTwoNum_for_extjs);

		ColumnConfig columnConfig = new ColumnConfig(this.getText("通过后能否继续访问"), "enumConstByFlagIsvisitAfterPass.name", false, true, false, "TextField", false, 100, "");
		String sql4 = "select b.id ,b.name from enum_const b where b.namespace='FlagIsvisitAfterPass'";
		columnConfig.setComboSQL(sql4);
		this.getGridConfig().addColumn(columnConfig);

		this.getGridConfig().addColumn(this.getText("通过规则描述"), "passRoleNote", false, true, false, "TextArea", true, 500);

		this.getGridConfig().addColumn(this.getText("学习建议"), "suggestion", false, true, false, "TextArea", true, 500);

		this.getGridConfig().addColumn(this.getText("课程图片"), "photoLink", false, true, false, "File", true, 200);

		ColumnConfig ccFlagSuggest = new ColumnConfig(this.getText("建议学习人群"), "enumConstByFlagSuggest.name", true, false, false, "TextField", false, 100, "");
		String sqlFlagSuggest = "select a.id ,a.name from enum_const a where a.namespace='FlagSuggest' and name not in ('面向所有人投票','面向学员投票','面向集体管理员投票')";
		ccFlagSuggest.setComboSQL(sqlFlagSuggest);
		this.getGridConfig().addColumn(ccFlagSuggest);

		if (us.getUserLoginType().equals("3")) {
			if (capabilitySet.contains(this.servletPath + "_review.action")) {
				this.getGridConfig()
						.addRenderScript(
								this.getText("预览"),
								"{if(record.data['coursewareId'] == ''){return '预览';}else {return '<a href=/CourseImports/'+record.data['coursewareId']+'/'+record.data['scormType']+'/'+record.data['indexFile']+'?mydate='+ new Date().getTime() +' target=\"_blank\">预览</a>';}}",
								"");
			}
			if (capabilitySet.contains(this.servletPath + "_view.action")) {
				this.getGridConfig()
						.addRenderScript(
								this.getText("课程信息"),
								"{return '<a href=\"/entity/teaching/peBzzCourseBatManager_courseInfo.action?id='+${value}+'&indexFile='+record.data['indexFile']+'\"  target=\"_blank\">课程信息</a>';}",
								"id");
			}

			if (capabilitySet.contains(this.servletPath + "_viewResult.action")) {
				this.getGridConfig()
						.addRenderScript(
								this.getText("调查结果"),
								"{return '<a href=\"/entity/information/peVotePaper_viewDetail.action?bean.id='+record.data['sid']+'&togo=1\" target=\"_blank\">查看</a>&nbsp;&nbsp;<a href=\"/entity/information/peVotePaper_resultSetToExcel.action?bean.id='+record.data['sid']+'&togo=1\" target=\"_blank\">导出</a>';}",
								"id");
			}
			if (capabilitySet.contains(this.servletPath + "_applyopen.action")) {
				this.getGridConfig().addMenuFunction(this.getText("申请发布"), "checkStatus");
			}
			if (capabilitySet.contains(this.servletPath + "_fabu.action")) {
				this.getGridConfig().addMenuFunction(this.getText("同意发布"), "FlagIsvalid.true");
			}
			if (capabilitySet.contains(this.servletPath + "_tingyong.action")) {
				this.getGridConfig().addMenuFunction(this.getText("拒绝发布"), "FlagIsvalid.false");
			}
			if (capabilitySet.contains(this.servletPath + "_applyOffline.action")) {
				this.getGridConfig().addMenuFunction(this.getText("申请下线"), "applyOffline");
			}
			if (capabilitySet.contains(this.servletPath + "_agreeOffline.action")) {
				this.getGridConfig().addMenuFunction(this.getText("同意下线"), "FlagOffline.true");
			}
			if (capabilitySet.contains(this.servletPath + "_refuseOffline.action")) {
				this.getGridConfig().addMenuFunction(this.getText("拒绝下线"), "FlagOffline.false");
			}
			if (capabilitySet.contains(this.servletPath + "_setTeaImg.action")) {
				this.getGridConfig().addMenuFunction(this.getText("设置讲师图片"), "/entity/teaching/peBzzCourseBatManager_setTeacherImg.action", false, false);
			}
			if (capabilitySet.contains(this.servletPath + "_removeTeaImg.action")) {
				this.getGridConfig().addMenuFunction(this.getText("删除讲师图片"), "/entity/teaching/peBzzCourseBatManager_removeImg.action?param=t", false, false);
			}
			if (capabilitySet.contains(this.servletPath + "_setSuggest.action")) {
				this.getGridConfig().addMenuFunction(this.getText("设置建议学习人群"), "/entity/teaching/peBzzCourseBatManager_setSuggest.action", false, false);
			}
			if (capabilitySet.contains(this.servletPath + "_removeSuggest.action")) {
				this.getGridConfig().addMenuFunction(this.getText("删除建议学习人群"), "/entity/teaching/peBzzCourseBatManager_removeSuggest.action", false, false);
			}
			if (capabilitySet.contains(this.servletPath + "_manageSource.action")) {
				this.getGridConfig().addRenderFunction(this.getText("学习资源"),
						"<a href=\"/sso/bzzinteraction_InteractioManage.action?course_id=${value}&teacher_id=teacher1\" target='_blank'>学习资源</a>", "id");
			}
			if (capabilitySet.contains(this.servletPath + "_impCourse.action") && capabilitySet.contains(this.servletPath + "_manageCourse.action")) {
				this.getGridConfig()
						.addRenderScript(
								this.getText("课件文件管理"),
								"{if(record.data['coursewareId'] == ''){return '&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"/entity/teaching/peBzzCourseBatManager_toUploadCourseware.action?bean.id='+record.data['id']+'\" target=\"_blank\">导入</a>';} else return '<a href=\"#\" onclick=if(true){window.open(\"/entity/manager/course/courseware/filemanager/filemanager_main02.jsp?courseware_id='+record.data['code']+'\")} >管理</a>&nbsp;&nbsp;<a href=\"#\" onclick=if(confirm(\"确定要删除吗\")){window.open(\"/entity/manager/course/courseware/scorm12/delete_scorm_info.jsp?courseware_id='+record.data['code']+'\")} >删除</a>'}",
								"id");
			}
			if (capabilitySet.contains(this.servletPath + "_impCourse.action") && !capabilitySet.contains(this.servletPath + "_manageCourse.action")) {
				this.getGridConfig()
						.addRenderScript(
								this.getText("课件文件管理"),
								"{if(record.data['coursewareId'] == ''){return '&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"/entity/teaching/peBzzCourseBatManager_toUploadCourseware.action?bean.id='+record.data['id']+'\" target=\"_blank\">导入</a>';} else return '<a href=\"#\" onclick=if(confirm(\"确定要删除吗\")){window.open(\"/entity/manager/course/courseware/scorm12/delete_scorm_info.jsp?courseware_id='+record.data['code']+'\")} >删除</a>'}",
								"id");
			}
			if (!capabilitySet.contains(this.servletPath + "_impCourse.action") && capabilitySet.contains(this.servletPath + "_manageCourse.action")) {
				this.getGridConfig()
						.addRenderScript(
								this.getText("课件文件管理"),
								"{if(record.data['coursewareId'] ==''){return '-';}else return '<a href=\"#\" onclick=if(true){window.open(\"/entity/manager/course/courseware/filemanager/filemanager_main02.jsp?courseware_id='+record.data['code']+'\")} >课件文件管理</a>'}",
								"id");
			}
			if (capabilitySet.contains(this.servletPath + "_satified.action")) {
				this.getGridConfig().addRenderFunction(this.getText("满意度调查"),
						"<center><a href=\"/entity/teaching/addSatisfactionPaper.action?courseId=${value}&flag=addPaper&area=peBzzCourseBatManager\" >添加</a></center>", "id");
			}
			if (capabilitySet.contains(this.servletPath + "_updateIndex.action")) {
				this.getGridConfig().addRenderFunction(this.getText("修改首页"), "<a href='#' onclick=\"" + this.getScript().toString() + "\">修改首页</a>", "code");
			}

		}
		Map<String, String> defaultValueMap = new HashMap<String, String>();
		defaultValueMap.put("passRole", "80");
		defaultValueMap.put("examTimesAllow", "5");
		defaultValueMap.put("studyDates", "180");
		defaultValueMap.put("enumConstByFlagIsvisitAfterPass.name", "能");
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
		this.servletPath = "/entity/teaching/peBzzCourseBatManager";
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
		/* 是否有效-否 */
		EnumConst ec = this.getEnumConstService().getByNamespaceCode("FlagIsvalid", "0");
		this.getBean().setEnumConstByFlagIsvalid(ec);
		/* 是否下线-否 */
		EnumConst ec4 = this.getEnumConstService().getByNamespaceCode("FlagOffline", "0");
		this.getBean().setEnumConstByFlagOffline(ec4);
		/* 课程审核状态-未申请 */
		EnumConst ec3 = this.getEnumConstService().getByNamespaceCode("FlagCheckStatus", "2");
		this.getBean().setEnumConstByFlagCheckStatus(ec3);
		/* 建议学习人群 */
		EnumConst ecFlagSuggestSet = this.getEnumConstService().getByNamespaceCode("FlagSuggestSet", "0");
		this.getBean().setEnumConstFlagSuggestset(ecFlagSuggestSet);
		/* 课程图片状态 */
		EnumConst ecFlagImg = this.getEnumConstService().getByNamespaceCode("FlagImg", "1");
		this.getBean().setEnumConstFlagImg(ecFlagImg);
		/* 主讲人照片状态 */
		EnumConst ecFlagTeaimg = this.getEnumConstService().getByNamespaceCode("FlagTeaimg", "0");
		this.getBean().setEnumConstFlagTeaimg(ecFlagTeaimg);
		/* 课程所属区域-专项培训课程 */
		EnumConst ecFlagCoursearea = this.getEnumConstService().getByNamespaceCode("FlagCoursearea", "2");
		this.getBean().setEnumConstByFlagCoursearea(ecFlagCoursearea);
		/* 推荐状态-否 */
		EnumConst enumConstByFlagIsRecommend = this.getEnumConstService().getByNamespaceCode("FlagIsRecommend", "0");
		this.getBean().setEnumConstByFlagIsRecommend(enumConstByFlagIsRecommend);
		/* 创建日期 */
		this.getBean().setCreateDate(new Date());
		PrBzzTchOpencourse prBzzTchOpencourse = new PrBzzTchOpencourse();
		prBzzTchOpencourse.setPeBzzTchCourse(this.getBean());
		try {
			this.setBean(this.getPeTchCourseService().add(prBzzTchOpencourse).getPeBzzTchCourse());
			map.put("success", "true");
			map.put("info", "一门课程添加成功");
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
				String sql = "SELECT COUNT(DISTINCT ID)  FROM PE_BZZ_BATCH WHERE ID IN (SELECT FK_BATCH_ID FROM PR_BZZ_TCH_OPENCOURSE " + "WHERE FK_COURSE_ID IN (" + idss + ")) "
						+ "and FLAG_BATCH_TYPE IS NOT NULL AND FLAG_BATCH_TYPE != '40288a7b394d676d01394db30248003d' AND END_TIME > SYSDATE";
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
		String flagIsFree = this.getBean().getEnumConstByFlagIsFree().getName();
		String price = this.getBean().getPrice();
		Double priceDbl = Double.parseDouble(price);
		if ("免费".equals(flagIsFree) && 0 != priceDbl) {// 免费课程+价格不等于0
			throw new EntityException("免费课程价格请输入0");
		}
		if (!"免费".equals(flagIsFree) && 0 == priceDbl) {// 收费课程+价格等于0
			throw new EntityException("收费课程价格不允许为0");
		}
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
		if (this.get_upload() != null && isImageFile(this.get_upload().getName().toLowerCase())) {
			throw new EntityException("照片不是图片文件，请选择一个图片文件！");
		}
		if (null != this.getBean().getEndDate() && !"".equals(this.getBean().getEndDate()) && null != this.getBean().getAnnounceDate()
				&& !"".equals(this.getBean().getAnnounceDate())) {
			if (this.getBean().getEndDate().before(this.getBean().getAnnounceDate())) {
				throw new EntityException("拟停用日期早于发布日期，请修改！");
			}
		}
	}

	/**
	 * 重写框架方法：更新数据前的校验
	 * 
	 * @author Lee
	 * @return
	 */
	public void checkBeforeUpdate() throws EntityException {
		String flagIsFree = this.getBean().getEnumConstByFlagIsFree().getName();
		String price = this.getBean().getPrice();
		Double priceDbl = Double.parseDouble(price);
		if ("免费".equals(flagIsFree) && 0 != priceDbl) {// 免费课程+价格不等于0
			throw new EntityException("免费课程价格请输入0");
		}
		if (!"免费".equals(flagIsFree) && 0 == priceDbl) {// 收费课程+价格等于0
			throw new EntityException("收费课程价格不允许为0");
		}
		PeBzzTchCourse c = this.getGeneralService().getById(this.getBean().getId());
		String sql = "select distinct scorm_type from scorm_sco_launch where course_id ='" + c.getCode() + "'";
		List list = this.getGeneralService().getBySQL(sql);
		if (c.getCode() != null && !c.getCode().equals(this.getBean().getCode()) && list.size() > 0) {
			throw new EntityException("已经上传课件，不能修改课程编号，若要修改请先将课件删除");
		}
		if (this.get_upload() != null && !isImageFile(this.get_uploadFileName().toLowerCase())) {
			throw new EntityException("照片不是图片文件，请选择一个图片文件！");
		}
		if (null != this.getBean().getEndDate() && !"".equals(this.getBean().getEndDate()) && null != this.getBean().getAnnounceDate()
				&& !"".equals(this.getBean().getAnnounceDate())) {
			if (this.getBean().getEndDate().before(this.getBean().getAnnounceDate())) {
				throw new EntityException("拟停用日期早于发布日期，请修改！");
			}
		}
		this.getBean().setEditDate(new Date());
	}

	/**
	 * 重写框架方法：更新数据
	 * 
	 * @author Lee
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

			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			dbInstance = this.getGeneralService().getById(this.getBean().getId());

		} catch (EntityException e1) {
			map.put("success", "false");
			map.put("info", "更新失败");
			return map;
		}
		this.superSetBean((PeBzzTchCourse) setSubIds(dbInstance, this.getBean()));

		PeBzzTchCourse instance = null;

		try {
			// instance = this.getGeneralService().save(this.getBean());
			// 讲师校验
			List list = null;
			TeacherInfo teacherInfo = null;
			if (this.getBean().getTeacher() != null && !"".equals(this.getBean().getTeacher()) && "1".equals(this.getBean().getEnumConstByFlagIsvalid().getCode())) {
				String note_sql = "select t.content  as content from interaction_teachclass_info t where t.teachclass_id = '" + this.getBean().getId() + "' and t.type = 'JSJJ'";
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
			this.getPeTchCourseService().saveCourseAndCourseware(dbInstance, this.getBean());
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
	 * @author Lee
	 * @return
	 */
	private boolean isImageFile(String fileName) {
		if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".gif") || fileName.toLowerCase().endsWith(".png")
				|| fileName.toLowerCase().endsWith(".bmp") || fileName.toLowerCase().endsWith(".jpeg") || fileName.toLowerCase().endsWith(".tiff")) {
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
					String sql = "select distinct scorm_type from scorm_sco_launch where course_id in (" + ids_s + ")";
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

					String sql = "delete from pe_bzz_tch_courseware where FK_COURSE_ID in (" + idss + ")";
					this.getGeneralService().executeBySQL(sql);
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
		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		dc.createCriteria("enumConstByFlagOffline", "enumConstByFlagOffline");
		dc.createCriteria("enumConstByFlagCourseType", "enumConstByFlagCourseType");
		dc.createCriteria("enumConstByFlagCourseCategory", "enumConstByFlagCourseCategory", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagIsvisitAfterPass", "enumConstByFlagIsvisitAfterPass", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagIsFree", "enumConstByFlagIsFree", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagIsRecommend", "enumConstByFlagIsRecommend", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagCourseItemType", "enumConstByFlagCourseItemType", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagContentProperty", "enumConstByFlagContentProperty", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagCheckStatus", "enumConstByFlagCheckStatus", DetachedCriteria.LEFT_JOIN);
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
	 * @author Lee
	 * @return
	 */
	public Page list() {
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("SELECT * FROM (SELECT A.ID, A.CODE, A.NAME, EC1.NAME FLAGCOURSETYPE, EC2.NAME FLAGCOURSECATEGORY, EC3.NAME FLAGCOURSEITEMTYPE, ");
			sqlBuffer.append(" EC4.NAME FLAGCONTENTPROPERTY, EC8.NAME FLAGISVALID, EC9.NAME FLAGOFFLINE, EC5.NAME FLAGISEXAM, ");// EC.NAME
																																	// FLAGISRECOMMEND,
			sqlBuffer.append(" EC6.NAME FLAGISFREE, A.PRICE, A.TIME, COURSE_LEN ,A.TEACHER, ");
			sqlBuffer.append(" C.COURSE_ID COURSEWAREID, C.SCORM_TYPE SCORMTYPE, C.INDEXFILE, SATISFACTION_ID SID, ");
			sqlBuffer.append(" A.STUDYDATES, A.END_DATE ENDDATE, A.ANNOUNCE_DATE ANNOUNCEDATE, ");
			sqlBuffer.append(" A.ANSWER_BEGINDATE ANSWERBEGINDATE, A.ANSWER_ENDDATE ANSWERENDDATE, A.PASSROLE, A.EXAMTIMES_ALLOW EXAMTIMESALLOW, ");
			sqlBuffer.append(" EC7.NAME FLAGISVISITAFTERPASS, A.PASSROLE_NOTE PASSROLENOTE, A.SUGGESTION, ");
			sqlBuffer.append(" A.CREATE_DATE CREATEDATE, TO_CHAR(WM_CONCAT(EC10.NAME)) FS ");
			sqlBuffer.append(" FROM PE_BZZ_TCH_COURSE A ");
			sqlBuffer.append(" INNER JOIN ENUM_CONST EC1 ON A.FLAG_COURSETYPE = EC1.ID ");
			sqlBuffer.append(" INNER JOIN ENUM_CONST EC2 ON A.FLAG_COURSECATEGORY = EC2.ID ");
			sqlBuffer.append(" INNER JOIN ENUM_CONST EC3 ON A.FLAG_COURSE_ITEM_TYPE = EC3.ID ");
			sqlBuffer.append(" INNER JOIN ENUM_CONST EC4 ON A.FLAG_CONTENT_PROPERTY = EC4.ID ");
			sqlBuffer.append(" INNER JOIN ENUM_CONST EC5 ON A.FLAG_IS_EXAM = EC5.ID ");
			sqlBuffer.append(" LEFT JOIN ENUM_CONST EC ON A.FLAG_ISRECOMMEND = EC.ID");
			sqlBuffer.append(" INNER JOIN ENUM_CONST EC6 ON A.FLAG_ISFREE = EC6.ID ");
			sqlBuffer.append(" INNER JOIN ENUM_CONST EC7 ON A.FLAG_ISVISITAFTERPASS = EC7.ID ");
			sqlBuffer.append(" INNER JOIN ENUM_CONST EC8 ON A.FLAG_ISVALID = EC8.ID ");
			sqlBuffer.append(" INNER JOIN ENUM_CONST EC9 ON A.FLAG_OFFLINE = EC9.ID ");
			sqlBuffer.append(" LEFT JOIN PE_BZZ_TCH_COURSE_SUGGEST B ON A.ID = B.FK_COURSE_ID and TABLE_NAME = 'PE_BZZ_TCH_COURSE'");
			sqlBuffer.append(" LEFT JOIN ENUM_CONST EC10 ON B.FK_ENUM_CONST_ID = EC10.ID ");
			sqlBuffer.append(" LEFT JOIN SCORM_SCO_LAUNCH C ON A.CODE = C.COURSE_ID");
			sqlBuffer.append(" LEFT JOIN SCORM_TYPE D ON C.SCORM_TYPE = D.CODE");
			sqlBuffer.append(" WHERE A.FLAG_COURSEAREA = 'Coursearea2'");
			sqlBuffer.append(" GROUP BY A.ID,A.CODE,A.NAME,EC1.NAME,EC2.NAME,EC3.NAME,EC4.NAME, ");
			sqlBuffer.append(" EC8.NAME,EC9.NAME,EC5.NAME,EC6.NAME,EC1.NAME,EC2.NAME,EC3.NAME, ");
			sqlBuffer.append(" EC4.NAME,EC8.NAME,EC9.NAME,EC5.NAME,EC6.NAME,FLAG_ISRECOMMEND,EC.NAME, ");
			sqlBuffer.append(" A.PRICE,A.TIME,A.TEACHER,A.STUDYDATES,A.COURSE_LEN,A.END_DATE,A.ANNOUNCE_DATE, ");
			sqlBuffer.append(" A.ANSWER_BEGINDATE,A.ANSWER_ENDDATE,A.PASSROLE,A.EXAMTIMES_ALLOW, ");
			sqlBuffer.append(" EC7.NAME,A.PASSROLE_NOTE,A.SUGGESTION,A.CREATE_DATE,C.COURSE_ID, ");
			sqlBuffer.append(" C.SCORM_TYPE, C.INDEXFILE, SATISFACTION_ID,TABLE_NAME  ");
			sqlBuffer.append(" ) WHERE 1 = 1 ");
			/* 拼接查询条件 */
			Map params = this.getParamsMap();
			Iterator iterator = params.entrySet().iterator();
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
				/* 课程性质 */
				if (name.equals("enumConstByFlagCourseType.name")) {
					name = "FlagCourseType";
				}
				/* 业务分类 */
				if (name.equals("enumConstByFlagCourseCategory.name")) {
					name = "FlagCourseCategory";
				}
				/* 大纲分类 */
				if (name.equals("enumConstByFlagCourseItemType.name")) {
					name = "FlagCourseItemType";
				}
				/* 内容属性分类 */
				if (name.equals("enumConstByFlagContentProperty.name")) {
					name = "FlagContentProperty";
				}
				/* 是否有效-发布 */
				if (name.equals("enumConstByFlagIsvalid.name")) {
					name = "FlagIsvalid";
				}
				/* 下线 */
				if (name.equals("enumConstByFlagOffline.name")) {
					name = "FlagOffline";
				}
				/* 是否考试 */
				if (name.equals("enumConstByFlagIsExam.name")) {
					name = "FlagIsExam";
				}
				/* 课程收费状态 */
				if (name.equals("enumConstByFlagIsFree.name")) {
					name = "FlagIsFree";
				}
				if (!name.equals("enumConstByFlagSuggest.name")) {
					// 课程性质、业务分类、大纲分类、内容属性分类用=其他用like
					if ("FlagCourseType".equals(name) || "FlagCourseCategory".equals(name) || "FlagCourseItemType".equals(name) || "FlagContentProperty".equals(name)) {
						sqlBuffer.append(" and UPPER(" + name + ") = UPPER('" + value + "')");
					} else {
						sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");
					}
				}
				/* 建议学习人群 */
				if (name.equals("enumConstByFlagSuggest.name")) {
					sqlBuffer.append(" AND INSTR(FS, '" + value + "', 1, 1) > 0 ");
				}
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				/* 课程性质 */
				if (temp.equals("enumConstByFlagCourseType.name")) {
					temp = "FlagCourseType";
				}
				/* 业务分类 */
				if (temp.equals("enumConstByFlagCourseCategory.name")) {
					temp = "FlagCourseCategory";
				}
				/* 大纲分类 */
				if (temp.equals("enumConstByFlagCourseItemType.name")) {
					temp = "FlagCourseItemType";
				}
				/* 内容属性分类 */
				if (temp.equals("enumConstByFlagContentProperty.name")) {
					temp = "FlagContentProperty";
				}
				/* 是否有效-发布 */
				if (temp.equals("enumConstByFlagIsvalid.name")) {
					temp = "FlagIsvalid";
				}
				/* 下线 */
				if (temp.equals("enumConstByFlagOffline.name")) {
					temp = "FlagOffline";
				}
				/* 是否考试 */
				if (temp.equals("enumConstByFlagIsExam.name")) {
					temp = "FlagIsExam";
				}
				/* 课程收费状态 */
				if (temp.equals("enumConstByFlagIsFree.name")) {
					temp = "FlagIsFree";
				}
				if (temp.equals("id")) {
					temp = "createDate";
				}
				if(temp.equals("courseLen")){
					temp = "course_len";
				}
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					if ("time".equalsIgnoreCase(temp) || "price".equalsIgnoreCase(temp)) {
						sqlBuffer.append(" order by to_number(" + temp + ") desc ");
					} else {
						if (!temp.equals("enumConstByFlagSuggest.name"))
							sqlBuffer.append(" order by " + temp + " desc ");
					}
				} else {
					if ("time".equalsIgnoreCase(temp) || "price".equalsIgnoreCase(temp)) {
						sqlBuffer.append(" order by to_number(" + temp + ") asc ");
					} else {
						if (!temp.equals("enumConstByFlagSuggest.name"))
							sqlBuffer.append(" order by " + temp + " asc ");
					}
				}
			} else {
				sqlBuffer.append(" order by createDate desc");
			}
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
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
				sql = "select 1 from pe_bzz_tch_course pbtc where pbtc.flag_check_status = '40288acf3d62b37f013d62b9aeec0002' and pbtc.id = '" + idList.get(i) + "'";
				List list = this.getGeneralService().getBySQL(sql);
				if (list.size() > 0) {
					count++;
				}
			}
			if (count > 0) {
				throw new EntityException("所选课程中有未申请发布课程，请申请发布后再执行发布操作！");
			}
			for (int i = 0; i < idList.size(); i++) {
				sql = "select t.* from scorm_course_info t inner join pe_bzz_tch_course p on t.id=p.code where p.id ='" + idList.get(i) + "'";
				List list = this.getGeneralService().getBySQL(sql);
				if (list.size() < 1) {
					count++;
				}
			}
			if (count > 0) {
				throw new EntityException("所选课程中有课程无课件，请上传课件后再发布！");
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
	@SuppressWarnings({ "unchecked", "unchecked", "unchecked" })
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
					map.put("info", "不能做此操作，正在进行专项培训");
				} else {
					if (action.equals("FlagIsvalid.true")) {
						EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1");
						tempdc.add(Restrictions.in("id", ids));
						tempdc.add(Restrictions.isNotNull("satisfactionId"));
						List<PeBzzTchCourse> courselist = new ArrayList<PeBzzTchCourse>();
						courselist = this.getGeneralService().getList(tempdc);
						if (null == courselist || courselist.size() != ids.length) {
							map.put("success", "false");
							map.put("info", "所选课程中有课程未添加满意度调查,请添加满意度调查后再发布");
							return map;
						}
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
						}
						msg = "拒绝发布";
					}
					if (action.equals("FlagOffline.true")) {
						EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagOffline", "1");
						tempdc.add(Restrictions.in("id", ids));
						tempdc.createCriteria("enumConstByFlagOffline", DetachedCriteria.LEFT_JOIN);
						tempdc.add(Restrictions.isNotNull("satisfactionId"));
						List<PeBzzTchCourse> courselist = new ArrayList<PeBzzTchCourse>();
						courselist = this.getGeneralService().getList(tempdc);
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
							buf.append("|" + us.getLoginId() + "/" + us.getUserName() + "执行了课程下线操作\n");
							peBzzTchCourse.setOperationLogs(buf.toString());
							peBzzTchCourse.setEnumConstByFlagOffline(enumConst);
							peBzzTchCourse.setOfflineDate(new Date());
							peBzzTchCourse.setEditDate(new Date());
							this.getGeneralService().save(peBzzTchCourse);
							String sqlc = "SELECT 1\n" + "  FROM PE_BZZ_TCH_COURSE PBTC, INTERACTION_TEACHCLASS_INFO ITI\n" + " WHERE PBTC.ID = ITI.TEACHCLASS_ID\n"
									+ "   AND ITI.FK_ZILIAO IN (SELECT FK_ZILIAO\n" + "                           FROM INTERACTION_TEACHCLASS_INFO\n"
									+ "                          WHERE TEACHCLASS_ID = '" + peBzzTchCourse.getId() + "')\n" + "   AND TEACHCLASS_ID != '" + peBzzTchCourse.getId()
									+ "'\n" + "   AND PBTC.FLAG_OFFLINE != '11'";// 空可以更新资料为下线
							List listsqlc = this.getGeneralService().getBySQL(sqlc);
							if (listsqlc == null || listsqlc.size() <= 0) {
								String upsql = "UPDATE PE_RESOURCE PR\n" + "   SET PR.FLAG_OFFLINE = '2'\n" + " WHERE PR.ID IN (SELECT FK_ZILIAO\n"
										+ "                   FROM INTERACTION_TEACHCLASS_INFO\n" + "                  WHERE TEACHCLASS_ID = '" + peBzzTchCourse.getId() + "')";
								this.getGeneralService().executeBySQL(upsql);
							}
						}
						msg = "同意下线";
					}
					if (action.equals("FlagOffline.false")) {

						EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagOffline", "0");
						tempdc.add(Restrictions.in("id", ids));
						tempdc.createCriteria("enumConstByFlagOffline", DetachedCriteria.LEFT_JOIN);

						List<PeBzzTchCourse> courselist = new ArrayList<PeBzzTchCourse>();
						courselist = this.getGeneralService().getList(tempdc);
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
							buf.append("|" + us.getLoginId() + "/" + us.getUserName() + "执行了课程下线操作\n");
							peBzzTchCourse.setOperationLogs(buf.toString());
							peBzzTchCourse.setEnumConstByFlagOffline(enumConst);
							peBzzTchCourse.setEditDate(new Date());
							this.getGeneralService().save(peBzzTchCourse);
						}
						msg = "拒绝下线";
					}
					/**
					 * 课程审核
					 */
					if (action.equals("checkStatus")) {
						EnumConst enumConstFlagIsvalid = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "2"); // 课程审核状态，待审核
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
							// peBzzTchCourse.setEnumConstByFlagCheckStatus(enumConstFlagIsvalid);
							peBzzTchCourse.setEnumConstByFlagIsvalid(enumConstFlagIsvalid);
							peBzzTchCourse.setEditDate(new Date());
							this.getGeneralService().save(peBzzTchCourse);
						}
						msg = "申请发布";
					}
					/**
					 * 申请下线
					 */
					if (action.equals("applyOffline")) {
						EnumConst enumConstFlagOffline = this.getMyListService().getEnumConstByNamespaceCode("FlagOffline", "2"); // 课程审核状态，待审核
						tempdc.add(Restrictions.in("id", ids));
						// tempdc.add(Restrictions.isNotNull("satisfactionId"));
						List<PeBzzTchCourse> courselist = new ArrayList<PeBzzTchCourse>();
						courselist = this.getGeneralService().getList(tempdc);
						Iterator<PeBzzTchCourse> iterator = courselist.iterator();
						while (iterator.hasNext()) {
							PeBzzTchCourse peBzzTchCourse = iterator.next();
							String logs = peBzzTchCourse.getOperationLogs();
							if (logs == null) {
								logs = "";
							}
							// 未发布课程不允许下线 lwq 2014-12-16
							EnumConst ec1 = peBzzTchCourse.getEnumConstByFlagIsvalid();
							if (!"1".equals(ec1.getCode())) {
								map.clear();
								map.put("success", "false");
								map.put("info", "所选课程中有课程未发布，请重新选择后再执行操作！");
								return map;
							}
							String sql1 = "select 1 from scorm_sco_launch where course_id ='" + peBzzTchCourse.getCode() + "'";
							List list = this.getGeneralService().getBySQL(sql1);
							if (list == null || list.size() <= 0) {
								map.clear();
								map.put("success", "false");
								map.put("info", "所选课程中有课程未添加课件，请重新选择后再执行操作！");
								return map;
							}
							StringBuffer buf = new StringBuffer(logs);
							buf.append("|" + us.getLoginId() + "/" + us.getUserName() + "执行了课程申请下线操作\n");
							peBzzTchCourse.setOperationLogs(buf.toString());
							peBzzTchCourse.setEnumConstByFlagOffline(enumConstFlagOffline);
							peBzzTchCourse.setEditDate(new Date());
							this.getGeneralService().save(peBzzTchCourse);
						}
						msg = "申请下线";
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
		script.append("	var formPanel = new Ext.form.FormPanel({                                                                                                       ");
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
		script.append("	                 url:'/entity/teaching/peBzzCourseBatManager_updateIndex.action',   ");
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
		String sql = "update scorm_sco_launch t set indexfile='" + this.getIndexFile() + "' where course_id='" + this.getCourseware_code() + "'";
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
			sb.append("SELECT A.CODE,A.NAME,A.TEACHER,A.PRICE * A.TIME,A.STUDYDATES,A.END_DATE, ");
			sb.append(" A.ANSWER_BEGINDATE,A.ANSWER_ENDDATE,EC1.NAME FLAGCOURSETYPE,A.TIME,A.PASSROLE,A.EXAMTIMES_ALLOW, ");
			sb.append(" EC2.NAME FLAGISVISITAFTERPASS,EC3.NAME FLAGISEXAM,EC4.NAME FLAGCOURSECATEGORY,EC5.NAME FLAGCOURSEITEMTYPE, ");
			sb.append(" EC6.NAME FLAGCONTENTPROPERTY,LISTAGG(C.NAME, ',') WITHIN GROUP(ORDER BY C.NAME) WC,EC7.NAME FLAGISRECOMMEND, ");
			sb.append(" EC8.NAME FLAGISFREE,A.PHOTO_LINK,A.TEA_IMG,A.PASSROLE_NOTE,A.SUGGESTION ");
			sb.append(" FROM (SELECT * FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "') A ");
			sb.append(" INNER JOIN ENUM_CONST EC1 ON A.FLAG_COURSETYPE = EC1.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC2 ON A.FLAG_ISVISITAFTERPASS = EC2.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC3 ON A.FLAG_IS_EXAM = EC3.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC4 ON A.FLAG_COURSECATEGORY = EC4.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC5 ON A.FLAG_COURSE_ITEM_TYPE = EC5.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC6 ON A.FLAG_CONTENT_PROPERTY = EC6.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC7 ON A.FLAG_ISRECOMMEND = EC7.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC8 ON A.FLAG_ISFREE = EC8.ID ");
			sb.append(" LEFT JOIN (SELECT * FROM PE_BZZ_TCH_COURSE_SUGGEST WHERE TABLE_NAME = 'PE_BZZ_TCH_COURSE') B ");
			sb.append(" ON A.ID = B.FK_COURSE_ID ");
			sb.append(" LEFT JOIN ENUM_CONST C ON B.FK_ENUM_CONST_ID = C.ID ");
			sb.append(" GROUP BY A.CODE,A.NAME,A.TEACHER,A.PRICE,A.STUDYDATES,A.END_DATE, ");
			sb.append(" A.ANSWER_BEGINDATE,A.ANSWER_ENDDATE,EC1.NAME,A.TIME,A.PASSROLE, ");
			sb.append(" A.EXAMTIMES_ALLOW,EC2.NAME,EC3.NAME,EC4.NAME,EC5.NAME,EC6.NAME, ");
			sb.append(" EC7.NAME,EC8.NAME,A.PHOTO_LINK,A.TEA_IMG,A.PASSROLE_NOTE,A.SUGGESTION,B.TABLE_NAME ");
			List detailList = this.getGeneralService().getBySQL(sb.toString());
			ServletActionContext.getRequest().setAttribute("courseDetail", detailList);
			this.indexFile = ServletActionContext.getRequest().getParameter("indexFile");
			// this.setBean(this.getGeneralService().getById(id));
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "courseInfo";
	}

	/**
	 * 框架方法：或者查询列表所需数据
	 * 
	 * @author Lee
	 * @return
	 */
	// @Override
	// public DetachedCriteria initDetachedCriteria() {
	// DetachedCriteria dc = DetachedCriteria.forClass(PeBzzTchCourse.class);
	// dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
	// dc.createCriteria("enumConstByFlagCourseType",
	// "enumConstByFlagCourseType");
	// dc.createCriteria("ssoUser",
	// "ssoUser").createAlias("enumConstByFlagIsvalid",
	// "enumConstByFlagIsvalid", DetachedCriteria.LEFT_JOIN);
	// dc.createCriteria("enumConstByFlagCourseCategory",
	// "enumConstByFlagCourseCategory", DetachedCriteria.LEFT_JOIN);
	// return dc;
	// }
	/**
	 * 检查是否已上传课件
	 * 
	 * @author Lee
	 * @return
	 */
	public void checkStatus(List idList) throws EntityException {
		int count = 0;
		String sql = "";
		try {
			for (int i = 0; i < idList.size(); i++) {
				sql = "select t.* from scorm_course_info t inner join pe_bzz_tch_course p on t.id=p.code where p.id ='" + idList.get(i) + "'";
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
			String[] ids = (String[]) ServletActionContext.getRequest().getSession().getAttribute("stipids");
			StringBuffer params_ids = new StringBuffer();
			for (int i = 0; i < ids.length; i++) {
				params_ids.append("'" + ids[i] + "',");
			}
			params_ids.append("''");
			String sql = "UPDATE PE_BZZ_TCH_COURSE SET FLAG_TEAIMG = 'FlagTeaimg1', TEA_IMG = '" + linkUrl + "' WHERE ID IN (" + params_ids.toString() + ")";
			int result = this.getGeneralService().executeBySQL(sql);
			if (get_upload() != null && linkUrl != null && result > 0) {
				this.setMsg("讲师图片上传成功！");
			} else {
				this.setMsg("讲师图片上传失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/* 清除之前选中的ids */
			ServletActionContext.getRequest().getSession().removeAttribute("stipids");
		}
		this.setTogo("back");
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
				StringBuffer params_ids = new StringBuffer();
				for (int i = 0; i < ids.length; i++) {
					params_ids.append("'" + ids[i] + "',");
				}
				params_ids.append("''");
				String sql = "UPDATE PE_BZZ_TCH_COURSE SET FLAG_TEAIMG = 'FlagTeaimg0', TEA_IMG = '' WHERE ID IN (" + params_ids.toString() + ")";
				result = this.getGeneralService().executeBySQL(sql);
				if (result != 0) {
					this.setMsg("讲师图片删除成功");
				} else {
					this.setMsg("讲师图片删除失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				/* 清除之前选中的ids */
				ServletActionContext.getRequest().getSession().removeAttribute("stipids");
			}
		}
		this.setTogo("back");
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
					sql = "DELETE FROM PE_BZZ_TCH_COURSE_SUGGEST WHERE FK_COURSE_ID = '" + ids[i] + "' AND UPPER(TABLE_NAME) = 'PE_BZZ_TCH_COURSE'";
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
}
