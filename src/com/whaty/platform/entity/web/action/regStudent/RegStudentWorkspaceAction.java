package com.whaty.platform.entity.web.action.regStudent;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.courseware.basic.Courseware;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleOpenCourse;
import com.whaty.platform.entity.PlatformFactory;
import com.whaty.platform.entity.PlatformManage;
import com.whaty.platform.entity.activity.OpenCourse;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.regStudent.RegStudentWorkspaceService;
import com.whaty.platform.entity.user.EntityUser;
import com.whaty.platform.entity.user.StudentPriv;
import com.whaty.platform.entity.user.TeacherPriv;
import com.whaty.platform.entity.util.MyUtil;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.training.basic.StudyProgress;

public class RegStudentWorkspaceAction extends MyBaseAction {
	private RegStudentWorkspaceService regStudentWorkspaceService = null;
	private PeBzzStudent peBzzStudent;
	private List courseTypeList;// 课程类别列表
	private List courseCategoryList;// 课程性质列表
	// Lee start 2014年03月12日 添加内容属性查询条件
	private List courseContentList;// 内容属性列表
	private List suggestrenList;// 建议学习人群
	private String suggestren;// 建议学习人群
	private String coursearea;// 课程所属区域
	private List courseareaList;// 课程所属区域
	private String courseContent;// 内容属性ID
	private List<EnumConst> courseItemTypeList; // 20121128, 按大纲分类
	private String courseType;// 课程类别id
	private String courseCategory;// 课程性质id
	private String courseCode; // 20121128添加，用于选课单搜索
	private String courseName; // 课程名称
	private String time;// 20140620 dgh 学时
	private String courseItemType; // 20121128, 大纲id
	private int totalCount; // 总条数
	private int startIndex = 0; // 开始数
	private int pageSize = 10; // 页面显示数
	private Page page;
	private String teacher;// 20140620 dgh 讲师
	private String zhanneixinNum;// 个人主页未读站内信数量
	private String diaochawenjuanNum;// 个人主页可填写调查问卷数量
	private String daikaoshikechengNum;// 待考试课程
	private String tongzhigonggaoNum;
	private String classHour_all;
	private String course_id;
	private String firstPage; // 进入学习首个页面
	private String teachclass_id;
	private String flag; // 后来添加，用于记录学员进入时，课程答疑时间是否开始
	private String classHour;// 课程学时
	private String selSendDateStart; // 用于站内信箱时间查询开始时间
	private String selSendDateEnd; // 用于站内信箱时间产寻结束时间
	private String operateresult;
	private String password_old;
	private String password_new;
	private List learnStutusList;// 学习状态List
	private String learnStatus;// 学习状态
	
	private String scormType;
	private String indexFile;
	private long myDate;
	
	private String selTitle;
	private String selSendName;
	private String selSendDate;
	
	private String orderInfo;
	private String orderType;//课程学习各菜单中排序方式
	

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public RegStudentWorkspaceService getRegStudentWorkspaceService() {
		return regStudentWorkspaceService;
	}

	public void setRegStudentWorkspaceService(RegStudentWorkspaceService RegStudentWorkspaceService) {
		this.regStudentWorkspaceService = RegStudentWorkspaceService;
	}

	public PeBzzStudent getPeBzzStudent() {
		return peBzzStudent;
	}

	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
	}

	public List getCourseTypeList() {
		return courseTypeList;
	}

	public void setCourseTypeList(List courseTypeList) {
		this.courseTypeList = courseTypeList;
	}

	public List getCourseCategoryList() {
		return courseCategoryList;
	}

	public void setCourseCategoryList(List courseCategoryList) {
		this.courseCategoryList = courseCategoryList;
	}

	public List getCourseContentList() {
		return courseContentList;
	}

	public void setCourseContentList(List courseContentList) {
		this.courseContentList = courseContentList;
	}

	public List getSuggestrenList() {
		return suggestrenList;
	}

	public void setSuggestrenList(List suggestrenList) {
		this.suggestrenList = suggestrenList;
	}

	public String getSuggestren() {
		return suggestren;
	}

	public void setSuggestren(String suggestren) {
		this.suggestren = suggestren;
	}

	public String getCoursearea() {
		return coursearea;
	}

	public void setCoursearea(String coursearea) {
		this.coursearea = coursearea;
	}

	public List getCourseareaList() {
		return courseareaList;
	}

	public void setCourseareaList(List courseareaList) {
		this.courseareaList = courseareaList;
	}

	public String getCourseContent() {
		return courseContent;
	}

	public void setCourseContent(String courseContent) {
		this.courseContent = courseContent;
	}

	public List<EnumConst> getCourseItemTypeList() {
		return courseItemTypeList;
	}

	public void setCourseItemTypeList(List<EnumConst> courseItemTypeList) {
		this.courseItemTypeList = courseItemTypeList;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getCourseCategory() {
		return courseCategory;
	}

	public void setCourseCategory(String courseCategory) {
		this.courseCategory = courseCategory;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCourseItemType() {
		return courseItemType;
	}

	public void setCourseItemType(String courseItemType) {
		this.courseItemType = courseItemType;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getZhanneixinNum() {
		return zhanneixinNum;
	}

	public void setZhanneixinNum(String zhanneixinNum) {
		this.zhanneixinNum = zhanneixinNum;
	}

	public String getDiaochawenjuanNum() {
		return diaochawenjuanNum;
	}

	public void setDiaochawenjuanNum(String diaochawenjuanNum) {
		this.diaochawenjuanNum = diaochawenjuanNum;
	}

	public String getDaikaoshikechengNum() {
		return daikaoshikechengNum;
	}

	public void setDaikaoshikechengNum(String daikaoshikechengNum) {
		this.daikaoshikechengNum = daikaoshikechengNum;
	}

	public String getClassHour_all() {
		return classHour_all;
	}

	public void setClassHour_all(String classHour_all) {
		this.classHour_all = classHour_all;
	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public String getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(String firstPage) {
		this.firstPage = firstPage;
	}

	public String getTeachclass_id() {
		return teachclass_id;
	}

	public void setTeachclass_id(String teachclass_id) {
		this.teachclass_id = teachclass_id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getClassHour() {
		return classHour;
	}

	public void setClassHour(String classHour) {
		this.classHour = classHour;
	}

	public String getSelSendDateStart() {
		return selSendDateStart;
	}

	public void setSelSendDateStart(String selSendDateStart) {
		this.selSendDateStart = selSendDateStart;
	}

	public String getSelSendDateEnd() {
		return selSendDateEnd;
	}

	public void setSelSendDateEnd(String selSendDateEnd) {
		this.selSendDateEnd = selSendDateEnd;
	}

	public String getOperateresult() {
		return operateresult;
	}

	public void setOperateresult(String operateresult) {
		this.operateresult = operateresult;
	}

	public String getPassword_old() {
		return password_old;
	}

	public void setPassword_old(String password_old) {
		this.password_old = password_old;
	}

	public String getPassword_new() {
		return password_new;
	}

	public void setPassword_new(String password_new) {
		this.password_new = password_new;
	}

	public String getTongzhigonggaoNum() {
		return tongzhigonggaoNum;
	}

	public void setTongzhigonggaoNum(String tongzhigonggaoNum) {
		this.tongzhigonggaoNum = tongzhigonggaoNum;
	}

	public String getScormType() {
		return scormType;
	}

	public void setScormType(String scormType) {
		this.scormType = scormType;
	}

	public String getIndexFile() {
		return indexFile;
	}

	public void setIndexFile(String indexFile) {
		this.indexFile = indexFile;
	}

	public long getMyDate() {
		return myDate;
	}

	public void setMyDate(long myDate) {
		this.myDate = myDate;
	}

	public String getSelTitle() {
		return selTitle;
	}

	public void setSelTitle(String selTitle) {
		this.selTitle = selTitle;
	}

	public String getSelSendName() {
		return selSendName;
	}

	public void setSelSendName(String selSendName) {
		this.selSendName = selSendName;
	}

	public String getSelSendDate() {
		return selSendDate;
	}

	public void setSelSendDate(String selSendDate) {
		this.selSendDate = selSendDate;
	}

	/**
	 * lwq 2015-03-09 进入监管学员端首页
	 * 
	 * @return
	 */
	public String toRegStudent() {
		String check_info_comp = "true";
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		this.peBzzStudent = this.regStudentWorkspaceService.initStudentInfo(ssoUser.getId());
		try {
			List znxList = this.regStudentWorkspaceService.getNum("zhanneixin", ssoUser.getLoginId(), this.peBzzStudent.getId());
			this.zhanneixinNum = (znxList == null || znxList.size() == 0) ? "0" : znxList.get(0).toString();
			List dkskcList = this.regStudentWorkspaceService.getNum("daikaoshikecheng", ssoUser.getLoginId(), this.peBzzStudent.getId());
			this.daikaoshikechengNum = (dkskcList == null || dkskcList.size() == 0 || ((Object[]) dkskcList.get(0))[0] == null) ? "0" : ((Object[]) dkskcList.get(0))[0].toString();
			List dcwjList = this.regStudentWorkspaceService.getNum("diaochawenjuan", ssoUser.getLoginId(), this.peBzzStudent.getId());
			this.diaochawenjuanNum = (dcwjList == null || dcwjList.size() == 0) ? "0" : dcwjList.get(0).toString();
			List tzggList = this.regStudentWorkspaceService.getNum("tongzhigonggao", ssoUser.getLoginId(), this.peBzzStudent.getId());
			this.tongzhigonggaoNum = (tzggList == null || tzggList.size() == 0) ? "0" : tzggList.get(0).toString();
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("zhanneixinNum", zhanneixinNum);
			request.setAttribute("diaochawenjuanNum", diaochawenjuanNum);
			request.setAttribute("daikaoshikechengNum", daikaoshikechengNum);
			if (("".equals(peBzzStudent.getTrueName()) || peBzzStudent.getTrueName() == null) || ("".equals(peBzzStudent.getGender()) || peBzzStudent.getGender() == null) || peBzzStudent.getPeEnterprise() == null || ("".equals(peBzzStudent.getDepartment()) || peBzzStudent.getDepartment() == null)
					|| ("".equals(peBzzStudent.getPhone()) || peBzzStudent.getPhone() == null) || ("".equals(peBzzStudent.getEmail()) || peBzzStudent.getEmail() == null)) {
				check_info_comp = "false";
			}
			request.setAttribute("check_info_comp", check_info_comp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "regStudent";
	}

	/**
	 * 正在学习课程
	 * 
	 * @return String
	 * @author linjie
	 */
	public String toLearningCourses() {

		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		this.peBzzStudent = this.regStudentWorkspaceService.initStudentInfo(ssoUser.getId());
		this.initCouresTypeAndCourseTypeList();
		try {
			this.page = this.regStudentWorkspaceService.initLearingCourse(this.peBzzStudent.getId(), courseCategory, courseType, courseCode, courseName, time, courseItemType, courseContent, suggestren, teacher, coursearea, learnStatus, orderInfo, orderType, pageSize, startIndex);
			List tongjiList = this.regStudentWorkspaceService.tongjiLearningCourse(this.peBzzStudent.getId(), courseCategory, courseType, courseCode, courseName, time, courseItemType, courseContent, suggestren, teacher, coursearea, learnStatus);
			BigDecimal totalNum = new BigDecimal("0.0");
			BigDecimal incomNum = new BigDecimal("0.0");
			BigDecimal uncomNum = new BigDecimal("0.0");
			if (tongjiList.size() > 0) {
				String[] tongjiArr = tongjiList.get(0).toString().split(",");
				totalNum = new BigDecimal(tongjiArr[0]).setScale(1, BigDecimal.ROUND_HALF_UP);
				incomNum = new BigDecimal(tongjiArr[1]).setScale(1, BigDecimal.ROUND_HALF_UP);
				uncomNum = new BigDecimal(tongjiArr[2]).setScale(1, BigDecimal.ROUND_HALF_UP);
			}
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("totalNum", totalNum);
			request.setAttribute("incomNum", incomNum);
			request.setAttribute("uncomNum", uncomNum);
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			this.setM4Session(this.getPeBzzStudent(), "0");
		} catch (PlatformException e) {
			e.printStackTrace();
		}
		return "learningCourses";
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub

	}

	/**
	 * 初始化检索中课程类别、性质列表
	 * 
	 * @author dgh
	 */
	public void initCouresTypeAndCourseTypeList() {
		try {
			this.courseTypeList = this.regStudentWorkspaceService.initCouresType("FlagCourseType");// 初始化课程性质
			this.courseCategoryList = this.regStudentWorkspaceService.initCourseCategory("FlagCourseCategory"); // 初始业务分类
			this.courseItemTypeList = this.regStudentWorkspaceService.initCourseItemType("FlagCourseItemType");// 初始大纲分类
			this.courseContentList = this.regStudentWorkspaceService.initCourseContent("FlagContentProperty");// 初始按内容属性
			this.suggestrenList = this.regStudentWorkspaceService.initSuggestRen("FlagSuggest");// 初始按内容属性
			this.courseareaList = this.regStudentWorkspaceService.initCourseArea("FlagCoursearea");// 课程所属区域
			this.learnStutusList = this.regStudentWorkspaceService.initLearnStatus("LearnStatus");// 课程学习状态
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置属性
	 * 
	 * @param user
	 * @param loginType
	 * @param endDate
	 * @author linjie
	 * @throws PlatformException
	 */
	public void setM4Session(PeBzzStudent user, String loginType) throws PlatformException {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (SsoConstant.SSO_STUDENT.equals(loginType)) {

			PlatformFactory factory = PlatformFactory.getInstance();
			PlatformManage platformManage = factory.createPlatformManage();
			EntityUser euser = platformManage.getEntityUser(user.getId(), "student");
			request.getSession().setAttribute("eduplatform_user", euser);
			StudentPriv studentPriv = factory.getStudentPriv(user.getId());
			request.getSession().setAttribute("eduplatform_priv", studentPriv);
		}
	}

	/**
	 * 课程报名
	 * 
	 * @return String
	 * @author lwq
	 */
	public String toFreeCourses() {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = us.getSsoUser();
		this.peBzzStudent = this.regStudentWorkspaceService.initStudentInfo(ssoUser.getId());
		// this.initStudent();
		this.initCouresTypeAndCourseTypeList();
		
		try {
			this.page = this.regStudentWorkspaceService.initFreeCourse(peBzzStudent.getId(), courseCode, courseName, courseType, courseItemType, courseCategory, courseContent, time, teacher, suggestren, coursearea, pageSize, startIndex);
			List freeTimesList = this.regStudentWorkspaceService.tongjiFreeCourse(this.peBzzStudent.getId(), courseCode, courseName, courseType, courseItemType, courseCategory, courseContent, time, teacher, suggestren, coursearea);
			BigDecimal num = new BigDecimal(0);
			if (null != freeTimesList && freeTimesList.size() > 0) {
				num = new BigDecimal(freeTimesList.get(0).toString()).setScale(1, BigDecimal.ROUND_HALF_UP);
			}
			this.classHour_all = num.setScale(1, BigDecimal.ROUND_HALF_UP).toString();
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		return "freeCourses";
	}

	/**
	 * 学生点击进入学习后执行的方法
	 * 
	 * @return
	 * @throws PlatformException
	 * @throws UnsupportedEncodingException
	 * @author qiaoshijia
	 */
	public String InteractionStuManage() throws PlatformException, UnsupportedEncodingException {

		HttpServletRequest request = ServletActionContext.getRequest();

		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession == null) {
			return "input";
		}
		String userId = userSession.getSsoUser().getId();
		String stu_sql = "select pbs.id from pe_bzz_student pbs join sso_user su on pbs.fk_sso_user_id = su.id where su.id = '" + userId + "'";
		List stuId = null;
		String stu_id = "";
		try {
			stuId = this.getGeneralService().getBySQL(stu_sql);
			if (stuId != null && stuId.size() > 0) {
				stu_id = (String) stuId.get(0);
			}
		} catch (EntityException e2) {
			e2.printStackTrace();
		}
		course_id = request.getParameter("course_id");
		/**
		 * 当点击进入学习时，课程的状态从为学习改为未完成状态,记录时间
		 */

		String open_sql = "select pbto.id from pe_bzz_tch_course pbtc " + "  join pr_bzz_tch_opencourse pbto on pbtc.id = pbto.fk_course_id " + "  left join pe_bzz_batch pbb on pbb.id = pbto.fk_batch_id " + " where pbtc.id = '" + course_id + "'"
				+ "   and (pbb.id = '40288a7b394d676d01394dad824c003b' or pbto.fk_batch_id is null) ";

		String opencourseId = "";
		try {
			List open = this.getGeneralService().getBySQL(open_sql);
			if (open.size() > 0) {
				opencourseId = (String) open.get(0);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}

		String sql = "select tcs.id\n" + "  from training_course_student tcs\n" + "  inner join sso_user su on su.id=tcs.student_id\n" + " inner join pr_bzz_tch_opencourse pbto on pbto.id = tcs.course_id\n" + " where  su.login_id = '" + userSession.getLoginId() + "' and pbto.id = '" + opencourseId
				+ "'";

		String sqlappend = "";
		try {
			List list = this.getGeneralService().getBySQL(sql);
			if (list.size() == 0) {
				String insertSql = "insert into training_course_student(id,student_id,course_id,learn_status)" + "values(sys_guid(),'" + userId + "','" + opencourseId + "','" + StudyProgress.UNCOMPLETE + "')";
				this.getGeneralService().executeBySQL(insertSql);
				String training_id = (String) this.getGeneralService().getBySQL(sql).get(0);
				String elective_sql = "insert into pr_bzz_tch_stu_elective(id,fk_stu_id,fk_tch_opencourse_id,fk_training_id,ELECTIVE_DATE) " + "values(sys_guid(),'" + stu_id + "','" + opencourseId + "','" + training_id + "',sysdate)";
				this.getGeneralService().executeBySQL(elective_sql);
				/* Lee start 学习记录明细 2014年12月08日 */
				StringBuffer sbTcsHis = new StringBuffer();
				sbTcsHis.append("INSERT INTO TRAINING_COURSE_STUDENT_HIS ");
				sbTcsHis.append("  (ID, FK_STU_ID, COURSE_ID, START_TIME, FK_ORDER_ID) ");
				sbTcsHis.append("  SELECT SEQ_TCS_HIS.NEXTVAL, B.ID, '" + opencourseId + "', SYSDATE, A.FK_ORDER_ID ");
				sbTcsHis.append("    FROM PR_BZZ_TCH_STU_ELECTIVE A ");
				sbTcsHis.append("   INNER JOIN PE_BZZ_STUDENT B ");
				sbTcsHis.append("      ON A.FK_STU_ID = B.ID ");
				sbTcsHis.append("     AND A.FK_TCH_OPENCOURSE_ID = '" + opencourseId + "' ");
				sbTcsHis.append("     AND B.FK_SSO_USER_ID = '" + userSession.getSsoUser().getId() + "' ");
				/* Lee end */
			}
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		firstPage = request.getParameter("firstPage");

		this.setTeachclass_id(course_id);

		try {
			this.setM4Session(userSession.getSsoUser(), "0", "student");
		} catch (PlatformException e) {
			e.printStackTrace();
		}

		OpenCourse openCourse = new OracleOpenCourse();

		openCourse.setId(this.getTeachclass_id());

		try {
			openCourse.setBzzCourse(this.getPeBzzCourse(course_id));
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
			return "msg";
		}
		request.getSession().removeAttribute("courseId");
		request.getSession().setAttribute("courseId", course_id);
		request.getSession().setAttribute("userId", userId);

		request.getSession().removeAttribute("openCourse");
		request.getSession().setAttribute("openCourse", openCourse);
		// request.getSession().setAttribute("now", now);
		request.getSession().setAttribute("userType", "student");
		request.getSession().setAttribute("First", "1");

		request.getSession().removeAttribute("opencourseId");
		request.getSession().setAttribute("opencourseId", opencourseId); // 进入学习使用

		request.getSession().removeAttribute("firstPage");
		request.getSession().setAttribute("firstPage", firstPage);
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzTchCourse.class);
		dc.add(Restrictions.eq("id", course_id));
		try {
			PeBzzTchCourse p = this.getGeneralService().getPeBzzTchCourse(dc);
			Date d = new Date();
			Date b = p.getAnswerBeginDate() == null ? new Date() : p.getAnswerBeginDate();
			Date e = p.getAnswerEndDate() == null ? new Date() : new Date(p.getAnswerEndDate().getTime() + 24 * 60 * 60 * 1000);
			if (d.after(b) && d.before(e)) {
				flag = "true";
			} else {
				flag = "false";
			}
			request.getSession().setAttribute("flag", flag);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "show_stuindex";
	}

	/**
	 * 将用户信息写入session
	 * 
	 * @param user
	 * @param loginType
	 * @param type
	 * @throws PlatformException
	 * @author linjie
	 */
	public void setM4Session(SsoUser user, String loginType, String type) throws PlatformException {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (SsoConstant.SSO_TEACHER.equals(loginType)) {

			PlatformFactory factory = PlatformFactory.getInstance();
			PlatformManage platformManage = factory.createPlatformManage();
			EntityUser euser = platformManage.getEntityUser(user.getId(), type);
			request.getSession().setAttribute("eduplatform_user", euser);
			TeacherPriv teacherPriv = factory.getTeacherPriv(user.getId());
			request.getSession().setAttribute("eduplatform_priv", teacherPriv);
		}
	}

	/**
	 * 根据课程id获取课程信息
	 * 
	 * @param courseId
	 * @return
	 * @throws EntityException
	 * @author linjie
	 */
	private PeBzzTchCourse getPeBzzCourse(String courseId) throws EntityException {

		DetachedCriteria peCourseDC = DetachedCriteria.forClass(PeBzzTchCourse.class);
		peCourseDC.add(Restrictions.eq("id", courseId));
		try {
			if (this.getGeneralService().getList(peCourseDC).size() > 0) {
				return (PeBzzTchCourse) this.getGeneralService().getList(peCourseDC).get(0);
			} else {
				return new PeBzzTchCourse();
			}
		} catch (EntityException e) {
			e.printStackTrace();
			throw new EntityException("没有所选的课程");
		}
	}

	/**
	 * 已完成课程
	 * 
	 * @author Lee
	 */
	public String toCompletedCourses() {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		this.peBzzStudent = this.regStudentWorkspaceService.initStudentInfo(ssoUser.getId());
		this.initCouresTypeAndCourseTypeList();
		try {
			this.page = this.regStudentWorkspaceService.initCompletedCourses(this.peBzzStudent.getId(), courseType, courseCode, courseName, time, teacher, coursearea, pageSize, startIndex);
			List list = this.regStudentWorkspaceService.tongjiCompletedCourse(this.peBzzStudent.getId(), courseType, courseCode, courseName, time, teacher, coursearea);
			BigDecimal bdList = new BigDecimal("0.0");
			if (list.size() > 0) {
				bdList = new BigDecimal(list.get(0).toString()).setScale(1, BigDecimal.ROUND_HALF_UP);
			}
			ServletActionContext.getRequest().setAttribute("list", bdList.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return "completedCourses";
	}

	public String getFirstinfo() throws EntityException {

		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		this.peBzzStudent = this.regStudentWorkspaceService.initStudentInfo(ssoUser.getId());
		this.page = this.regStudentWorkspaceService.firstBulletinInfoByPage(userSession.getLoginId(), pageSize, startIndex);

		return "studentindex";
	}

	/**
	 * 已通过课程
	 * 
	 * @return String
	 * @author linjie
	 */
	public String toPassedCourses() {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		this.peBzzStudent = this.regStudentWorkspaceService.initStudentInfo(ssoUser.getId());
		this.initCouresTypeAndCourseTypeList();
		Map params = new HashMap();
		params.put("studentId", this.peBzzStudent.getId());
		params.put("courseType", courseType);
		params.put("courseCode", this.courseCode);
		params.put("courseName", this.courseName);// 添加课程名称/编号查询条件
		params.put("examStatus", StudyProgress.PASSED);
		params.put("classHour", classHour);
		params.put("pageSize", pageSize);
		params.put("startIndex", startIndex);
		// Lee start 2014年3月5日 添加获得学时时间查询条件
		params.put("startDate", selSendDateStart);
		params.put("endDate", selSendDateEnd);
		// Lee end
		try {
			// this.page =
			// this.studentWorkspaceService.initPassedCourses(params);
			this.page = this.regStudentWorkspaceService.initPassedCourses(this.peBzzStudent.getId(), courseType, time, teacher, coursearea, selSendDateStart, selSendDateEnd, courseCode, courseName, pageSize, startIndex);
			List tongjiList = this.regStudentWorkspaceService.statisPassedCourse(this.peBzzStudent.getId(), courseType, time, teacher, coursearea, selSendDateStart, selSendDateEnd, courseCode, courseName);
			BigDecimal totalNum = new BigDecimal("0.0");
			BigDecimal bNum = new BigDecimal("0.0");
			BigDecimal xNum = new BigDecimal("0.0");
			if (tongjiList.size() > 0) {
				String[] tongjiArr = tongjiList.get(0).toString().split(",");
				totalNum = new BigDecimal(tongjiArr[0]).setScale(1, BigDecimal.ROUND_HALF_UP);
				bNum = new BigDecimal(tongjiArr[1]).setScale(1, BigDecimal.ROUND_HALF_UP);
				xNum = new BigDecimal(tongjiArr[2]).setScale(1, BigDecimal.ROUND_HALF_UP);
			}
			ServletActionContext.getRequest().setAttribute("a_list", totalNum);
			ServletActionContext.getRequest().setAttribute("b_list", bNum);
			ServletActionContext.getRequest().setAttribute("c_list", xNum);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return "toPassedCourses";
	}

	/**
	 * 学生个人信息
	 * 
	 * @return String
	 * @author linjie
	 */
	public String StudentInfo() {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);

		this.peBzzStudent = this.regStudentWorkspaceService.initStudentInfo(us.getSsoUser().getId());
		return "studentinfo";
	}

	public String toHelp() {
		return "tohelp";
	}

	/**
	 * 跳转修改信息
	 * 
	 * @return String
	 * @author linjie
	 */
	public String toModifyInfo() {
		this.StudentInfo();
		return "tomodifyinfo";
	}

	/**
	 * 修改信息
	 * 
	 * @return String
	 * @author linjie
	 */
	@SuppressWarnings("unchecked")
	public String modifyInfo() {
		try {
			DetachedCriteria PeBzzStudentCriteria = DetachedCriteria.forClass(PeBzzStudent.class);
			PeBzzStudentCriteria.add(Restrictions.idEq(peBzzStudent.getId()));
			// PeBzzStudentCriteria.createCriteria("peBzzBatch", "peBzzBatch");
			PeBzzStudentCriteria.createCriteria("peEnterprise", "peEnterprise");
			PeBzzStudentCriteria.createCriteria("peEnterprise.peEnterprise", DetachedCriteria.LEFT_JOIN);
			PeBzzStudent student = (PeBzzStudent) this.getGeneralService().getList(PeBzzStudentCriteria).get(0);

			student.setTrueName(peBzzStudent.getTrueName()); // 姓名*
			student.setCardType(peBzzStudent.getCardType()); // 国籍*
			// student.setCardNo(peBzzStudent.getCardNo()); //身份证号*
			student.setEducation(peBzzStudent.getEducation()); // 学历*
			student.setGender(peBzzStudent.getGender()); // 性别*
			// student.setFolk(peBzzStudent.getFolk()); // 民族*
			student.setMobilePhone(peBzzStudent.getMobilePhone()); // 手机*
			// student.setBirthdayDate(peBzzStudent.getBirthdayDate()); // 出生日期*
			student.setDepartment(peBzzStudent.getDepartment()); // 部门*
			// student.setTitle(this.peBzzStudent.getTitle()); //职称
			student.setPosition(peBzzStudent.getPosition()); // 职务
			student.setEmail(peBzzStudent.getEmail()); // 电子邮箱*
			// student.setZipcode(peBzzStudent.getZipcode()); //邮编
			student.setAddress(peBzzStudent.getAddress()); // 工作所在地区*
			student.setPhone(peBzzStudent.getPhone()); // 办公电话

			PeBzzStudent bzzStudent = (PeBzzStudent) this.getGeneralService().save(student);
			if (bzzStudent != null) {
				operateresult = "1";
			} else {
				operateresult = "2";
			}
		} catch (Exception e) {
			e.printStackTrace();
			operateresult = "2";
			return "modifyInfo";
		}
		return "modifyInfo";
	}

	/**
	 * 重新设置密码
	 * 
	 * @return String
	 * @author linjie
	 */
	public String toPassword() {
		this.StudentInfo();
		return "toPassword";
	}

	/**
	 * 保存修改的新密码
	 * 
	 * @return String
	 * @author linjie
	 */
	public String passwordexe() {
		Map map = new HashMap();
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		DetachedCriteria peStudentDC = DetachedCriteria.forClass(PeBzzStudent.class);
		peStudentDC.createCriteria("ssoUser", "ssoUser");
		peStudentDC.add(Restrictions.eq("ssoUser.id", ssoUser.getId()));
		List peStudentList = new ArrayList();
		try {
			peStudentList = this.getGeneralService().getList(peStudentDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		PeBzzStudent instance = (PeBzzStudent) peStudentList.get(0);

		String email = instance.getEmail();
		if (email == null || "".equals(email)) {
			this.setMsg("您的邮箱为空，邮箱可用于找回密码，请您先在修改信息页面中将邮箱完善再进行密码修改");
			this.setTogo("/entity/workspaceStudent/studentWorkspace_StudentInfo.action");
			return "stu_msg";
		}
		if (!instance.getSsoUser().getPasswordBk().equals(MyUtil.md5(this.getPassword_old()))) {
			this.setOperateresult("1");
			return "editexe";
		} else {
			instance.getSsoUser().setPassword("字段弃用");
			instance.getSsoUser().setPasswordBk(MyUtil.md5(this.getPassword_new()));
			instance.getSsoUser().setPasswordMd5(MyUtil.md5(this.getPassword_new()));
			try {
				this.getGeneralService().save(instance);
				this.setOperateresult("2");
			} catch (EntityException e) {
				e.printStackTrace();
				this.setOperateresult("3");
			}
			this.setPeBzzStudent(instance);
			// System.out.println("this.getSsoid()"+this.getSsoid());
			return "editexe";
		}

	}

	public List getLearnStutusList() {
		return learnStutusList;
	}

	public void setLearnStutusList(List learnStutusList) {
		this.learnStutusList = learnStutusList;
	}

	public String getLearnStatus() {
		return learnStatus;
	}

	public void setLearnStatus(String learnStatus) {
		this.learnStatus = learnStatus;
	}
	
	public String view() {
		String sql = "SELECT PBTC.CODE, SCL.SCORM_TYPE, SCL.INDEXFILE " + 
			"  FROM PE_BZZ_TCH_COURSE PBTC " + 
			"  JOIN SCORM_SCO_LAUNCH SCL ON PBTC.CODE = SCL.COURSE_ID WHERE PBTC.ID='" + course_id + "'";
		try {
			List list = this.getGeneralService().getBySQL(sql);
			if(list != null && list.size() > 0) {
				Object[] obj = (Object[]) list.get(0);
				courseCode = (String) obj[0];
				scormType = (String) obj[1];
				indexFile = (String) obj[2];
				myDate = new Date().getTime();
			} else {
				this.setMsg("课程不能预览!");
				return "viewFailed";
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "view";
	}
	
	public String tositeemail() {

		ActionContext ac = ActionContext.getContext();
		UserSession us = (UserSession) ac.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		StringBuffer sql = new StringBuffer();

		try {
			page = this.regStudentWorkspaceService.initSiteemail(us.getLoginId(), selTitle, selSendDateStart, selSendDateEnd, selSendName,
					pageSize, startIndex);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "tositeemail";
	}
	
}
