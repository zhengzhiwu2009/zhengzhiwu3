package com.whaty.platform.sso.web.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.whaty.platform.database.oracle.standard.entity.activity.OracleOpenCourse;
import com.whaty.platform.entity.PlatformFactory;
import com.whaty.platform.entity.PlatformManage;
import com.whaty.platform.entity.StudentOperationManage;
import com.whaty.platform.entity.TeacherOperationManage;
import com.whaty.platform.entity.activity.OpenCourse;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.user.EntityUser;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.entity.user.StudentPriv;
import com.whaty.platform.entity.user.TeacherPriv;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.interaction.InteractionFactory;
import com.whaty.platform.interaction.InteractionManage;
import com.whaty.platform.interaction.InteractionUserPriv;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.test.TestManage;
import com.whaty.platform.training.basic.StudyProgress;
import com.whaty.platform.util.JsonUtil;
import com.whaty.platform.util.Page;

/**
 * @param
 * @version 创建时间：2009-7-1 下午01:35:21
 * @return
 * @throws PlatformException
 *             类说明
 */
public class BzzInteractionAction extends MyBaseAction {

	private String course_id;
	private String teacher_id;
	private String teachclass_id;
	private String opencourseId; // 进入课件学习使用
	private String firstPage; // 进入学习首个页面
	private String flag; // 后来添加，用于记录学员进入时，课程答疑时间是否开始
	private PeBzzStudent peBzzStudent;
	private String piciName;
	private String value;// 重定向传值

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public PeBzzStudent getPeBzzStudent() {
		return peBzzStudent;
	}

	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
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

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	/**
	 * 教师进入课程空间管理
	 * 
	 * @return
	 * @throws PlatformException
	 * @author linjie
	 */
	public String InteractioManage() throws PlatformException {

		HttpServletRequest request = ServletActionContext.getRequest();
		String paramString = request.getParameter("param");
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession == null) {
			return "input";
		}

		String course_id = request.getParameter("course_id");
		String teacher_id = request.getParameter("teacher_id");
		this.setCourse_id(course_id);
		this.setTeacher_id(teacher_id);

		PeTeacher peTeacher = new PeTeacher();
		try {
			peTeacher = this.getTeacher();
		} catch (EntityException e1) {
			e1.printStackTrace();
			// this.setMsg(e1.getMessage());
			// return "msg";
		}

		try {
			this.setM4Session(peTeacher.getSsoUser(), "1", "teacher");
		} catch (PlatformException e) {
			e.printStackTrace();
		}

		this.setTeachclass_id(course_id);

		Object o2 = request.getSession().getAttribute("teacher_eduplatform_priv");
		TeacherPriv session_teacherPriv;
		if (o2 != null)
			session_teacherPriv = (TeacherPriv) o2;
		else
			session_teacherPriv = (TeacherPriv) (request.getSession().getAttribute("eduplatform_priv"));

		PlatformFactory platformFactory = PlatformFactory.getInstance();
		TeacherOperationManage teacherOperationManage = platformFactory.creatTeacherOperationManage(session_teacherPriv);
		InteractionUserPriv interactionUserPriv = teacherOperationManage.getInteractionUserPriv(session_teacherPriv.getTeacherId(),
				teachclass_id);
		request.getSession().setAttribute("interactionUserPriv", interactionUserPriv);

		// 构造opencourse

		OpenCourse openCourse = new OracleOpenCourse();
		// BasicSequence examSequence = new OracleBasicSequence();

		openCourse.setId(this.getTeachclass_id());

		try {
			openCourse.setBzzCourse(this.getPeBzzCourse(course_id));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if (null != paramString && !"".equals(paramString))
			request.getSession().setAttribute("param", "live");
		else
			request.getSession().setAttribute("param", "other");
		request.getSession().setAttribute("courseId", course_id);
		request.getSession().setAttribute("openCourse", openCourse);
		request.getSession().setAttribute("userType", "teacher");
		request.getSession().setAttribute("courseware_priv", teacherOperationManage.getCoursewareManagerPriv());

		this.setTeachclass_id(teachclass_id);

		return "show_index";
	}

	/**
	 * 进入在线测试题库
	 * 
	 * @return
	 * @throws PlatformException
	 * @author qiaoshijia
	 */
	public String InteractiontkManage() throws PlatformException {

		HttpServletRequest request = ServletActionContext.getRequest();

		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession == null) {
			return "input";
		}

		String course_id = request.getParameter("course_id");
		String teacher_id = request.getParameter("teacher_id");
		this.setCourse_id(course_id);
		this.setTeacher_id(teacher_id);

		PeTeacher peTeacher = new PeTeacher();
		try {
			peTeacher = this.getTeacher();
		} catch (EntityException e1) {
			e1.printStackTrace();
			// this.setMsg(e1.getMessage());
			// return "msg";
		}

		try {
			this.setM4Session(peTeacher.getSsoUser(), "1", "teacher");
		} catch (PlatformException e) {
			e.printStackTrace();
		}

		this.setTeachclass_id(course_id);

		Object o2 = request.getSession().getAttribute("teacher_eduplatform_priv");
		TeacherPriv session_teacherPriv;
		if (o2 != null)
			session_teacherPriv = (TeacherPriv) o2;
		else
			session_teacherPriv = (TeacherPriv) (request.getSession().getAttribute("eduplatform_priv"));

		PlatformFactory platformFactory = PlatformFactory.getInstance();
		TeacherOperationManage teacherOperationManage = platformFactory.creatTeacherOperationManage(session_teacherPriv);
		InteractionUserPriv interactionUserPriv = teacherOperationManage.getInteractionUserPriv(session_teacherPriv.getTeacherId(),
				teachclass_id);
		request.getSession().setAttribute("interactionUserPriv", interactionUserPriv);

		// 构造opencourse

		OpenCourse openCourse = new OracleOpenCourse();
		// BasicSequence examSequence = new OracleBasicSequence();

		openCourse.setId(this.getTeachclass_id());

		try {
			openCourse.setBzzCourse(this.getPeBzzCourse(course_id));
		} catch (EntityException e) {
			e.printStackTrace();
		}

		request.getSession().setAttribute("courseId", course_id);
		request.getSession().setAttribute("openCourse", openCourse);
		request.getSession().setAttribute("userType", "teacher");
		request.getSession().setAttribute("courseware_priv", teacherOperationManage.getCoursewareManagerPriv());

		this.setTeachclass_id(teachclass_id);

		return "show_index_tk";
	}

	/**
	 * 进入考试题库
	 * 
	 * @return
	 * @throws PlatformException
	 * @author qiaoshijia
	 */
	public String InteractionExamtkManage() throws PlatformException {

		HttpServletRequest request = ServletActionContext.getRequest();

		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession == null) {
			return "input";
		}

		String course_id = request.getParameter("course_id");
		String teacher_id = request.getParameter("teacher_id");
		this.setCourse_id(course_id);
		this.setTeacher_id(teacher_id);

		PeTeacher peTeacher = new PeTeacher();
		try {
			peTeacher = this.getTeacher();
		} catch (EntityException e1) {
			e1.printStackTrace();
			// this.setMsg(e1.getMessage());
			// return "msg";
		}

		try {
			this.setM4Session(peTeacher.getSsoUser(), "1", "teacher");
		} catch (PlatformException e) {
			e.printStackTrace();
		}

		this.setTeachclass_id(course_id);

		Object o2 = request.getSession().getAttribute("teacher_eduplatform_priv");
		TeacherPriv session_teacherPriv;
		if (o2 != null)
			session_teacherPriv = (TeacherPriv) o2;
		else
			session_teacherPriv = (TeacherPriv) (request.getSession().getAttribute("eduplatform_priv"));

		PlatformFactory platformFactory = PlatformFactory.getInstance();
		TeacherOperationManage teacherOperationManage = platformFactory.creatTeacherOperationManage(session_teacherPriv);
		InteractionUserPriv interactionUserPriv = teacherOperationManage.getInteractionUserPriv(session_teacherPriv.getTeacherId(),
				teachclass_id);
		request.getSession().setAttribute("interactionUserPriv", interactionUserPriv);

		// 构造opencourse

		OpenCourse openCourse = new OracleOpenCourse();
		// BasicSequence examSequence = new OracleBasicSequence();

		openCourse.setId(this.getTeachclass_id());

		try {
			openCourse.setBzzCourse(this.getPeBzzCourse(course_id));
		} catch (EntityException e) {
			e.printStackTrace();
		}

		request.getSession().setAttribute("courseId", course_id);
		request.getSession().setAttribute("openCourse", openCourse);
		request.getSession().setAttribute("userType", "teacher");
		request.getSession().setAttribute("courseware_priv", teacherOperationManage.getCoursewareManagerPriv());

		this.setTeachclass_id(teachclass_id);

		return "show_index_examtk";
	}

	/**
	 * 课件学习时进入此方法 lzh
	 * 
	 * @return
	 */
	public String training() {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession == null) {
			return "input";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String userId = userSession.getSsoUser().getId();
		/**
		 * 当点击进入学习时，课程的状态从为学习改为未完成状态,记录时间
		 */

		String sql = "select tcs.id,tcs.learn_status,tcs.get_date\n" + "  from training_course_student tcs\n"
				+ "  inner join sso_user pbs on pbs.id=tcs.student_id\n"
				+ " inner join pr_bzz_tch_opencourse pbtc on pbtc.id = tcs.course_id\n" + " where  pbs.login_id = '"
				+ userSession.getLoginId() + "' and pbtc.id = '" + this.opencourseId + "'";

		String sqlappend = "";

		String completeSql = "SELECT 1 FROM TRAINING_COURSE_STUDENT WHERE STUDENT_ID = '" + userSession.getSsoUser().getId()
				+ "' AND COURSE_ID = '" + this.opencourseId + "' AND LEARN_STATUS = 'COMPLETED' AND COMPLETE_DATE is not null";
		Object tcshStartTime = ServletActionContext.getRequest().getSession().getAttribute(userSession.getSsoUser().getId() + "starttime");
		if (null != tcshStartTime && !"".equals(tcshStartTime))
			ServletActionContext.getRequest().getSession().removeAttribute(userSession.getSsoUser().getId() + "starttime");
		String sysDateSql = "SELECT TO_CHAR(SYSDATE,'yyyy-MM-dd hh24:mi:ss') FROM DUAL";
		/*
		 * DetachedCriteria tdc =
		 * DetachedCriteria.forClass(TrainingCourseStudent.class);
		 * tdc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
		 * tdc.add(Restrictions.eq("prBzzTchOpencourse.id", this.opencourseId));
		 */
		try {
			List completeList = this.getGeneralService().getBySQL(completeSql);
			Boolean newTcs = false;
			StringBuffer sbTcsHis = new StringBuffer();
			if (null != completeList && completeList.size() > 0)
				newTcs = false;
			else
				newTcs = true;
			if (newTcs) {// 未完成学习才更新学习记录
				List sysDateList = this.getGeneralService().getBySQL(sysDateSql);
				String tcsStartTime = sysDateList.get(0).toString();
				ServletActionContext.getRequest().getSession().setAttribute(userSession.getSsoUser().getId() + "starttime", tcsStartTime);
				/* Lee start 学习记录明细 2014年12月08日 */
				sbTcsHis.append("INSERT INTO TRAINING_COURSE_STUDENT_HIS ");
				sbTcsHis.append("  (ID, FK_STU_ID, COURSE_ID, START_TIME, FK_ORDER_ID) ");
				sbTcsHis.append("  SELECT SEQ_TCS_HIS.NEXTVAL, B.FK_SSO_USER_ID, '" + this.opencourseId + "', TO_DATE('" + tcsStartTime
						+ "','yyyy-MM-dd hh24:mi:ss'), A.FK_ORDER_ID ");
				sbTcsHis.append("    FROM PR_BZZ_TCH_STU_ELECTIVE A ");
				sbTcsHis.append("   INNER JOIN PE_BZZ_STUDENT B ");
				sbTcsHis.append("      ON A.FK_STU_ID = B.ID ");
				sbTcsHis.append("     AND A.FK_TCH_OPENCOURSE_ID = '" + this.opencourseId + "' ");
				sbTcsHis.append("     AND B.FK_SSO_USER_ID = '" + userSession.getSsoUser().getId() + "' ");
			}
			/* Lee end */
			List list = this.getGeneralService().getBySQL(sql);
			// List list = this.getGeneralService().getList(tdc);
			// String updateSql =
			if (list.size() > 0) {
				Object[] vals = (Object[]) list.get(0);
				// TrainingCourseStudent t = (TrainingCourseStudent)list.get(0);
				if (vals[2] == null)
					sqlappend = ",tcs.get_date=sysdate ";
				if (vals[1].toString().equalsIgnoreCase(StudyProgress.UNCOMPLETE)) {
					String updateSql = "update training_course_student tcs set tcs.learn_status = '" + StudyProgress.INCOMPLETE + "' "
							+ sqlappend + " where tcs.id='" + vals[0] + "'";
					this.getGeneralService().executeBySQL(updateSql);
				}
				if (newTcs)
					this.getGeneralService().executeBySQL(sbTcsHis.toString());
				/*
				 * if(t.getLearnStatus().equals(StudyProgress.UNCOMPLETE)) {
				 * t.setLearnStatus(StudyProgress.INCOMPLETE);
				 * this.getGeneralService().save(t); }
				 */
			}

		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.value = "course_id=" + this.course_id + "&opencourseId=" + this.opencourseId;
		// windows['courseWin'] =
		// window.open('<%=path%>/training/student/course/jumpCourseware.jsp?course_id=<%=courseId%>&opencourseId=<%=opencourseId%>',
		// 'courseWin',
		// 'height=680,width=1050,top=0,left=50,toolbar=no,menubar=no,scrollbars=no,
		// resizable=no,location=no, status=no');
		return "jumpCourseware";
	}

	public String interactionstumanage() throws PlatformException, UnsupportedEncodingException {
		return this.InteractionStuManage();
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
		course_id = request.getParameter("course_id");
		opencourseId = request.getParameter("opencourseId");
		firstPage = request.getParameter("firstPage");
		// this.setCourse_id(course_id);
		// this.setOpencourseId(opencourseId);
		// this.setFirstPage(firstPage);

		this.setTeachclass_id(course_id);

		try {
			this.setM4Session(userSession.getSsoUser(), "0", "student");
		} catch (PlatformException e) {
			e.printStackTrace();
		}
		// System.out.println("id:"+userSession.getSsoUser().getId());

		Object o1 = request.getSession().getAttribute("student_eduplatform_user");
		Object o2 = request.getSession().getAttribute("student_eduplatform_priv");
		Student session_student;
		StudentPriv studentPriv;
		if (o1 != null)
			session_student = (Student) o1;
		else
			session_student = (Student) (request.getSession().getAttribute("eduplatform_user"));

		if (o2 != null)
			studentPriv = (StudentPriv) o2;
		else
			studentPriv = (StudentPriv) (request.getSession().getAttribute("eduplatform_priv"));

		PlatformFactory platformFactory = PlatformFactory.getInstance();
		StudentOperationManage studentOperationManage = platformFactory.creatStudentOperationManage(studentPriv);

		ManagerPriv includePriv = studentOperationManage.getNormalEntityManagePriv();

		InteractionUserPriv interactionUserPriv = studentOperationManage.getInteractionUserPriv(studentPriv.getStudentId(), this
				.getTeachclass_id());
		request.getSession().setAttribute("interactionUserPriv", interactionUserPriv);

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
			request.getSession().removeAttribute("passStudy");
			request.getSession().setAttribute("passStudy", request.getParameter("passStudy"));
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "show_stuindex";
	}
	
	public String getTestScore(){
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		HttpServletRequest request = ServletActionContext.getRequest();
		PlatformFactory factory=PlatformFactory.getInstance();
		PlatformManage platformManage=factory.createPlatformManage();
		//System.err.println(us.getId());
		String userId =userSession.getSsoUser().getId() ;
		try{
			EntityUser euser=platformManage.getEntityUser(userSession.getId(),"student");
			userId = euser.getId();
		}catch(Exception e){
			e.printStackTrace();
		}
		course_id = request.getParameter("course_id");
		String passrole = request.getParameter("passrole");
		String sql = "select score from pretest_record where  user_id= '" + userId + "' and course_id = '" + course_id + "'" ;
		try{
			List list = this.getGeneralService().getBySQL(sql);
			Map map = new HashMap();
			map.put("passrole", passrole);
			map.put("course_id", course_id);
			
			if(list != null && list.size() > 0){				
				map.put("score", list.get(0).toString());
				
			}else{
				map.put("score", "");
			}
			this.setJsonString(JsonUtil.toJSONString(map));
		}catch(Exception e){
			e.printStackTrace();
		}
		return "json";
	}
	/**
	 * 课前测试
	 */
	public String InteractionPrivTest() throws PlatformException, UnsupportedEncodingException {

		HttpServletRequest request = ServletActionContext.getRequest();
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession == null) {
			return "input";
		}
		
		String userId = userSession.getSsoUser().getId();
	
		course_id = request.getParameter("course_id");
		opencourseId = request.getParameter("opencourseId");
		firstPage = request.getParameter("firstPage");
		String passrole = request.getParameter("passrole");
		// this.setCourse_id(course_id);
		// this.setOpencourseId(opencourseId);
		// this.setFirstPage(firstPage);

		this.setTeachclass_id(course_id);

		try {
			this.setM4Session(userSession.getSsoUser(), "0", "student");
		} catch (PlatformException e) {
			e.printStackTrace();
		}
		// System.out.println("id:"+userSession.getSsoUser().getId());

		Object o1 = request.getSession().getAttribute("student_eduplatform_user");
		Object o2 = request.getSession().getAttribute("student_eduplatform_priv");
		Student session_student;
		StudentPriv studentPriv;
		if (o1 != null)
			session_student = (Student) o1;
		else
			session_student = (Student) (request.getSession().getAttribute("eduplatform_user"));

		if (o2 != null)
			studentPriv = (StudentPriv) o2;
		else
			studentPriv = (StudentPriv) (request.getSession().getAttribute("eduplatform_priv"));

		PlatformFactory platformFactory = PlatformFactory.getInstance();
		StudentOperationManage studentOperationManage = platformFactory.creatStudentOperationManage(studentPriv);

		ManagerPriv includePriv = studentOperationManage.getNormalEntityManagePriv();

		InteractionUserPriv interactionUserPriv = studentOperationManage.getInteractionUserPriv(studentPriv.getStudentId(), this
				.getTeachclass_id());
		request.getSession().setAttribute("interactionUserPriv", interactionUserPriv);

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
		//DetachedCriteria dc = DetachedCriteria.forClass(PeBzzTchCourse.class);
		//dc.add(Restrictions.eq("id", course_id));
			//PeBzzTchCourse p = this.getGeneralService().getPeBzzTchCourse(dc);
			//Date d = new Date();
			//Date b = p.getAnswerBeginDate() == null ? new Date() : p.getAnswerBeginDate();
			//Date e = p.getAnswerEndDate() == null ? new Date() : new Date(p.getAnswerEndDate().getTime() + 24 * 60 * 60 * 1000);
		
				flag = "true";

		request.getSession().setAttribute("flag", flag);
		request.getSession().removeAttribute("passStudy");
		request.getSession().setAttribute("passStudy", request.getParameter("passStudy"));
		try{
			InteractionFactory interactionFactory = InteractionFactory.getInstance();
			InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
			TestManage testManage = interactionManage.creatTestManage();
			String testCourseId = testManage.getOnlineTestCourses(course_id);
			request.getSession().setAttribute("pretest", "1");
			request.getSession().setAttribute("passrole", passrole);
			System.out.println("passrole=" + passrole);
			Map map = new HashMap();
			map.put("testCourseId", testCourseId);
			this.setJsonString(JsonUtil.toJSONString(map));
		}catch(Exception e){
				e.printStackTrace();
		}
		return "json";
	}
	/**
	 * 免费课程跳转到此方法
	 * 
	 * @return
	 * @throws PlatformException
	 * @throws UnsupportedEncodingException
	 * @author qiaoshijia
	 */
	public String InteractionStuManageFree() throws PlatformException, UnsupportedEncodingException {

		HttpServletRequest request = ServletActionContext.getRequest();

		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession == null) {
			return "input";
		}
		String userId = userSession.getSsoUser().getId();
		/**
		 * 当点击进入学习时，插入选课表和training表
		 */
		// 1.通过拿到开课的实体对象的PrBzzTchOpencourse
		DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
		dc.add(Restrictions.eq("id", opencourseId));
		List<PrBzzTchOpencourse> list1;
		try {
			list1 = this.getGeneralService().getList(dc);
			PrBzzTchOpencourse bzzTchOpencourse = null;
			if (list1.size() > 0) {
				bzzTchOpencourse = list1.get(0);
			}
			// 2.拿到当前登陆人对应的学员实体对象
			DetachedCriteria stuDc = DetachedCriteria.forClass(PeBzzStudent.class);
			stuDc.createCriteria("ssoUser", "ssoUser");
			stuDc.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
			PeBzzStudent stu = (PeBzzStudent) this.getGeneralService().getList(stuDc).get(0);

			stuDc.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
			try {
				this.peBzzStudent = (PeBzzStudent) this.getGeneralService().getList(stuDc).get(0);
			} catch (EntityException e) {
				e.printStackTrace();
			}

			DetachedCriteria eleDc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
			eleDc.add(Restrictions.eq("prBzzTchOpencourse", bzzTchOpencourse));
			eleDc.add(Restrictions.eq("peBzzStudent", this.peBzzStudent));
			List eleList = this.getGeneralService().getList(eleDc);
			if (eleList.size() < 1) {// 当选课表无记录时插入选课表和training表
				// 3.插入training表
				List saveList = new ArrayList();
				TrainingCourseStudent trainingCourseStudent = new TrainingCourseStudent();
				trainingCourseStudent.setPrBzzTchOpencourse(bzzTchOpencourse);
				trainingCourseStudent.setSsoUser(peBzzStudent.getSsoUser());
				trainingCourseStudent.setPercent(0.00);
				trainingCourseStudent.setLearnStatus(StudyProgress.UNCOMPLETE);
				// trainingCourseStudent.setGetDate(new Date());
				saveList.add(trainingCourseStudent);
				// 4.插入选课表
				PrBzzTchStuElective ele = new PrBzzTchStuElective();
				ele.setPrBzzTchOpencourse(bzzTchOpencourse);
				ele.setElectiveDate(new Date());
				ele.setPeBzzStudent(stu);
				ele.setSsoUser(userSession.getSsoUser());
				ele.setTrainingCourseStudent(trainingCourseStudent);
				saveList.add(ele);
				this.getGeneralService().saveList(saveList);
			}
		} catch (EntityException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		course_id = request.getParameter("course_id");
		opencourseId = request.getParameter("opencourseId");
		firstPage = request.getParameter("firstPage");
		// this.setCourse_id(course_id);
		// this.setOpencourseId(opencourseId);
		// this.setFirstPage(firstPage);

		this.setTeachclass_id(course_id);

		try {
			this.setM4Session(userSession.getSsoUser(), "0", "student");
		} catch (PlatformException e) {
			e.printStackTrace();
		}
		// System.out.println("id:"+userSession.getSsoUser().getId());

		Object o1 = request.getSession().getAttribute("student_eduplatform_user");
		Object o2 = request.getSession().getAttribute("student_eduplatform_priv");
		Student session_student;
		StudentPriv studentPriv;
		if (o1 != null)
			session_student = (Student) o1;
		else
			session_student = (Student) (request.getSession().getAttribute("eduplatform_user"));

		if (o2 != null)
			studentPriv = (StudentPriv) o2;
		else
			studentPriv = (StudentPriv) (request.getSession().getAttribute("eduplatform_priv"));

		PlatformFactory platformFactory = PlatformFactory.getInstance();
		StudentOperationManage studentOperationManage = platformFactory.creatStudentOperationManage(studentPriv);

		ManagerPriv includePriv = studentOperationManage.getNormalEntityManagePriv();

		InteractionUserPriv interactionUserPriv = studentOperationManage.getInteractionUserPriv(studentPriv.getStudentId(), this
				.getTeachclass_id());
		request.getSession().setAttribute("interactionUserPriv", interactionUserPriv);

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
		DetachedCriteria dc1 = DetachedCriteria.forClass(PeBzzTchCourse.class);
		dc.add(Restrictions.eq("id", course_id));
		try {
			PeBzzTchCourse p = null;
			List peList = this.getGeneralService().getList(dc1);
			if (peList.size() > 0) {
				p = (PeBzzTchCourse) peList.get(0);
			}
			Date d = new Date();
			Date b = p.getAnswerBeginDate() == null ? new Date() : p.getAnswerBeginDate();
			Date e = p.getAnswerEndDate() == null ? new Date() : p.getAnswerEndDate();
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
		request.getSession().removeAttribute("passStudy");
		request.getSession().setAttribute("passStudy", request.getParameter("passStudy"));
		return "show_stuindex";
	}

	/**
	 * 公司内训课程跳转到此方法
	 * 
	 * @return
	 * @throws PlatformException
	 * @throws UnsupportedEncodingException
	 * @author qiaoshijia
	 */
	public String interactionStuManageINTERNAL() throws PlatformException, UnsupportedEncodingException {

		HttpServletRequest request = ServletActionContext.getRequest();
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		course_id = request.getParameter("course_id");
		String opencourse_id = "";
		String userids = null;
		String userId = userSession.getSsoUser().getId();
		List listxue = null;
		String sql = "select ID FROM PE_BZZ_STUDENT WHERE fk_sso_user_id='" + userId + "'";
		try {
			listxue = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		String xsql = "select inter.fk_tch_opencourse_id  from PR_BZZ_TCH_OPENCOURSE pbto   inner join STU_INTERNAL inter on inter.fk_tch_opencourse_id = "
				+ " pbto.id and inter.fk_stu_id  in ( select ID FROM PE_BZZ_STUDENT WHERE fk_sso_user_id='"
				+ userId
				+ "')   inner join pe_bzz_tch_course pbtc on pbtc.id=pbto.fk_course_id and  pbtc.id='" + course_id + "'";
		List list = new ArrayList();
		try {
			list = this.getGeneralService().getBySQL(xsql);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if (listxue != null && listxue.size() > 0) {
			userids = listxue.get(0).toString();
		}
		for (int i = 0; i < list.size(); i++) {

			opencourse_id = list.get(i).toString();

			if (!"".equals(opencourse_id)) {
				DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
				dc.add(Restrictions.eq("id", opencourse_id));
				List<PrBzzTchOpencourse> list1;
				try {

					list1 = this.getGeneralService().getList(dc);
					PrBzzTchOpencourse bzzTchOpencourse = null;
					if (list1.size() > 0) {
						bzzTchOpencourse = list1.get(0);
					}
					// 2.拿到当前登陆人对应的学员实体对象
					DetachedCriteria stuDc = DetachedCriteria.forClass(PeBzzStudent.class);

					stuDc.add(Restrictions.eq("id", userids));
					try {
						this.peBzzStudent = (PeBzzStudent) this.getGeneralService().getList(stuDc).get(0);
					} catch (EntityException e) {
						e.printStackTrace();
					}
					DetachedCriteria eleDc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
					eleDc.add(Restrictions.eq("prBzzTchOpencourse", bzzTchOpencourse));
					eleDc.add(Restrictions.eq("peBzzStudent", this.peBzzStudent));
					List eleList = this.getGeneralService().getList(eleDc);
					if (eleList.size() < 1) {// 当选课表无记录时插入选课表和training表
						// 3.插入training表
						List saveList = new ArrayList();
						TrainingCourseStudent trainingCourseStudent = new TrainingCourseStudent();
						trainingCourseStudent.setPrBzzTchOpencourse(bzzTchOpencourse);
						trainingCourseStudent.setSsoUser(peBzzStudent.getSsoUser());
						trainingCourseStudent.setPercent(0.00);
						trainingCourseStudent.setLearnStatus(StudyProgress.UNCOMPLETE);
						// trainingCourseStudent.setGetDate(new Date());
						saveList.add(trainingCourseStudent);
						// 4.插入选课表
						PrBzzTchStuElective ele = new PrBzzTchStuElective();
						ele.setPrBzzTchOpencourse(bzzTchOpencourse);
						ele.setElectiveDate(new Date());
						ele.setPeBzzStudent(this.peBzzStudent);
						ele.setSsoUser(userSession.getSsoUser());
						ele.setTrainingCourseStudent(trainingCourseStudent);
						saveList.add(ele);
						this.getGeneralService().saveList(saveList);
					}

				} catch (EntityException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				course_id = request.getParameter("course_id");
				opencourseId = request.getParameter("opencourseId");
				firstPage = request.getParameter("firstPage");
				// this.setCourse_id(course_id);
				// this.setOpencourseId(opencourseId);
				// this.setFirstPage(firstPage);

				this.setTeachclass_id(course_id);

				try {
					this.setM4Session(userSession.getSsoUser(), "0", "student");
				} catch (PlatformException e) {
					e.printStackTrace();
				}
				// System.out.println("id:"+userSession.getSsoUser().getId());

				Object o1 = request.getSession().getAttribute("student_eduplatform_user");
				Object o2 = request.getSession().getAttribute("student_eduplatform_priv");
				Student session_student;
				StudentPriv studentPriv;
				if (o1 != null)
					session_student = (Student) o1;
				else
					session_student = (Student) (request.getSession().getAttribute("eduplatform_user"));

				if (o2 != null)
					studentPriv = (StudentPriv) o2;
				else
					studentPriv = (StudentPriv) (request.getSession().getAttribute("eduplatform_priv"));

				PlatformFactory platformFactory = PlatformFactory.getInstance();
				StudentOperationManage studentOperationManage = platformFactory.creatStudentOperationManage(studentPriv);

				ManagerPriv includePriv = studentOperationManage.getNormalEntityManagePriv();

				InteractionUserPriv interactionUserPriv = studentOperationManage.getInteractionUserPriv(studentPriv.getStudentId(), this
						.getTeachclass_id());
				request.getSession().setAttribute("interactionUserPriv", interactionUserPriv);

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
				if ("".equals(opencourse_id)) { // 判断是否为公司内训课程
					request.getSession().setAttribute("opencourseId", opencourseId); // 进入学习使用
				} else {
					request.getSession().setAttribute("opencourseId", opencourse_id); // 公司内训进入学习使用
				}

				request.getSession().removeAttribute("firstPage");
				request.getSession().setAttribute("firstPage", firstPage);
				DetachedCriteria dc1 = DetachedCriteria.forClass(PeBzzTchCourse.class);
				dc.add(Restrictions.eq("id", course_id));
				try {
					PeBzzTchCourse p = null;
					List peList = this.getGeneralService().getList(dc1);
					if (peList.size() > 0) {
						p = (PeBzzTchCourse) peList.get(0);
					}
					Date d = new Date();
					Date b = p.getAnswerBeginDate() == null ? new Date() : p.getAnswerBeginDate();
					Date e = p.getAnswerEndDate() == null ? new Date() : p.getAnswerEndDate();
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
			}
		}
		request.getSession().removeAttribute("passStudy");
		request.getSession().setAttribute("passStudy", request.getParameter("passStudy"));
		return "show_stuindex";
	}

	/**
	 * 获取教师信息
	 * 
	 * @return
	 * @throws EntityException
	 * @author linjie
	 */
	private PeTeacher getTeacher() throws EntityException {

		DetachedCriteria peTeacherDC = DetachedCriteria.forClass(PeTeacher.class);
		peTeacherDC.add(Restrictions.eq("loginId", this.getTeacher_id()));
		try {
			return (PeTeacher) this.getGeneralService().getList(peTeacherDC).get(0);
		} catch (EntityException e) {
			e.printStackTrace();
			throw new EntityException("没有该教师。");
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
		} else if (SsoConstant.SSO_STUDENT.equals(loginType)) {

			// PlatformFactory factory=PlatformFactory.getInstance();
			// PlatformManage platformManage=factory.createPlatformManage();
			// EntityUser euser=platformManage.getEntityUser(user.getId(),type);
			// request.getSession().removeAttribute("eduplatform_user");
			// request.getSession().removeAttribute("eduplatform_priv");
			// request.getSession().setAttribute("eduplatform_user",euser);
			// StudentPriv studentPriv=factory.getStudentPriv(user.getId());
			// request.getSession().setAttribute("eduplatform_priv",studentPriv);
			// request.getSession().setAttribute("000000",user.getId()+"+"+user);
			// request.getSession().setAttribute("00000euser",euser.getId()+"+"+euser);

		}
	}

	/**
	 * qiaoshijia 在线考试
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public String hzphExamManage() throws PlatformException {

		HttpServletRequest request = ServletActionContext.getRequest();

		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession == null) {
			return "input";
		}

		String course_id = request.getParameter("batch_id");
		String teacher_id = request.getParameter("teacher_id");
		this.setCourse_id(course_id);
		this.setTeacher_id(teacher_id);

		PeTeacher peTeacher = new PeTeacher();
		try {
			peTeacher = this.getTeacher();
		} catch (EntityException e1) {
			e1.printStackTrace();
			// this.setMsg(e1.getMessage());
			// return "msg";
		}

		try {
			this.setM4Session(peTeacher.getSsoUser(), "1", "teacher");
		} catch (PlatformException e) {
			e.printStackTrace();
		}

		this.setTeachclass_id(course_id);

		Object o2 = request.getSession().getAttribute("teacher_eduplatform_priv");
		TeacherPriv session_teacherPriv;
		if (o2 != null)
			session_teacherPriv = (TeacherPriv) o2;
		else
			session_teacherPriv = (TeacherPriv) (request.getSession().getAttribute("eduplatform_priv"));

		PlatformFactory platformFactory = PlatformFactory.getInstance();
		TeacherOperationManage teacherOperationManage = platformFactory.creatTeacherOperationManage(session_teacherPriv);
		InteractionUserPriv interactionUserPriv = teacherOperationManage.getInteractionUserPriv(session_teacherPriv.getTeacherId(),
				teachclass_id);
		request.getSession().setAttribute("interactionUserPriv", interactionUserPriv);

		// 构造opencourse

		OpenCourse openCourse = new OracleOpenCourse();
		// BasicSequence examSequence = new OracleBasicSequence();

		openCourse.setId(this.getTeachclass_id());

		// 增加一个默认的课程
		openCourse.setBzzCourse(getHzphExam(course_id));

		request.getSession().setAttribute("courseId", course_id);
		request.getSession().setAttribute("openCourse", openCourse);
		request.getSession().setAttribute("userType", "teacher");
		request.getSession().setAttribute("courseware_priv", teacherOperationManage.getCoursewareManagerPriv());

		this.setTeachclass_id(teachclass_id);

		return "show_index_exam";
	}

	/** 打分过度类 */
	public String hzphExamManageScore() throws PlatformException {

		HttpServletRequest request = ServletActionContext.getRequest();

		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession == null) {
			return "input";
		}
		String piciStudent = request.getParameter("pici_student");
		String course_id = request.getParameter("batch_id");
		String teacher_id = request.getParameter("teacher_id");
		String tsId = request.getParameter("tsId");
		String piciId = request.getParameter("piciId");
		this.setCourse_id(course_id);
		this.setTeacher_id(teacher_id);

		PeTeacher peTeacher = new PeTeacher();
		try {
			peTeacher = this.getTeacher();
		} catch (EntityException e1) {
			e1.printStackTrace();
			// this.setMsg(e1.getMessage());
			// return "msg";
		}

		try {
			this.setM4Session(peTeacher.getSsoUser(), "1", "teacher");
		} catch (PlatformException e) {
			e.printStackTrace();
		}

		this.setTeachclass_id(course_id);

		Object o2 = request.getSession().getAttribute("teacher_eduplatform_priv");
		TeacherPriv session_teacherPriv;
		if (o2 != null)
			session_teacherPriv = (TeacherPriv) o2;
		else
			session_teacherPriv = (TeacherPriv) (request.getSession().getAttribute("eduplatform_priv"));

		PlatformFactory platformFactory = PlatformFactory.getInstance();
		TeacherOperationManage teacherOperationManage = platformFactory.creatTeacherOperationManage(session_teacherPriv);
		InteractionUserPriv interactionUserPriv = teacherOperationManage.getInteractionUserPriv(session_teacherPriv.getTeacherId(),
				teachclass_id);
		request.getSession().setAttribute("interactionUserPriv", interactionUserPriv);

		// 构造opencourse

		OpenCourse openCourse = new OracleOpenCourse();
		// BasicSequence examSequence = new OracleBasicSequence();

		openCourse.setId(this.getTeachclass_id());

		// 增加一个默认的课程
		openCourse.setBzzCourse(getHzphExam(course_id));

		request.getSession().setAttribute("courseId", course_id);
		request.getSession().setAttribute("openCourse", openCourse);
		request.getSession().setAttribute("userType", "teacher");
		request.getSession().setAttribute("courseware_priv", teacherOperationManage.getCoursewareManagerPriv());
		request.getSession().setAttribute("piciId", piciId);
		request.getSession().setAttribute("tsId", tsId);
		request.getSession().setAttribute("piciStudent", piciStudent);
		this.setTeachclass_id(teachclass_id);

		return "show_index_exam_score";
	}

	public String hzphExamManageDetail() throws PlatformException {

		HttpServletRequest request = ServletActionContext.getRequest();

		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession == null) {
			return "input";
		}

		String course_id = request.getParameter("batch_id");
		String teacher_id = request.getParameter("teacher_id");
		String tsId = request.getParameter("tsId");
		String piciId = request.getParameter("piciId");
		this.setCourse_id(course_id);
		this.setTeacher_id(teacher_id);
		if (tsId == null || "".equals(tsId)) {// 如果传入试卷ID
			// 为空，则通过学员ID和batchID查询试卷
			String testHistorySQL = "SELECT NVL(MAX(HIS.ID),0) AS TSID" + "  FROM TEST_TESTPAPER_HISTORY HIS, "
					+ "      TEST_TESTPAPER_INFO    INFO, " + "      PE_BZZ_STUDENT         PBS" + " WHERE HIS.T_USER_ID = PBS.ID"
					+ "   AND HIS.TESTPAPER_ID = INFO.ID" + "   AND INFO.GROUP_ID = '" + course_id + "'" + "   AND PBS.FK_SSO_USER_ID = '"
					+ userSession.getSsoUser().getId() + "'";
			try {
				List testHistoryList = this.getGeneralService().getBySQL(testHistorySQL);
				if (testHistoryList != null && testHistoryList.size() > 0) {
					tsId = testHistoryList.get(0).toString();
				}
			} catch (EntityException e) {
				// e.printStackTrace();
				this.setMsg("获得考试记录失败：" + e.getMessage());
				return "msg";
			}
			if (tsId == null && "".equals(tsId) || "0".equals(tsId)) {// 如果查询不到试卷记录信息返回出错。
				this.setMsg("没有考试记录！");
				return "msg";
			}
		}
		PeTeacher peTeacher = new PeTeacher();
		try {
			peTeacher = this.getTeacher();
		} catch (EntityException e1) {
			e1.printStackTrace();
			// this.setMsg(e1.getMessage());
			// return "msg";
		}

		try {
			this.setM4Session(peTeacher.getSsoUser(), "1", "teacher");
		} catch (PlatformException e) {
			e.printStackTrace();
		}

		this.setTeachclass_id(course_id);

		Object o2 = request.getSession().getAttribute("teacher_eduplatform_priv");
		TeacherPriv session_teacherPriv;
		if (o2 != null)
			session_teacherPriv = (TeacherPriv) o2;
		else
			session_teacherPriv = (TeacherPriv) (request.getSession().getAttribute("eduplatform_priv"));

		PlatformFactory platformFactory = PlatformFactory.getInstance();
		TeacherOperationManage teacherOperationManage = platformFactory.creatTeacherOperationManage(session_teacherPriv);
		InteractionUserPriv interactionUserPriv = teacherOperationManage.getInteractionUserPriv(session_teacherPriv.getTeacherId(),
				teachclass_id);
		request.getSession().setAttribute("interactionUserPriv", interactionUserPriv);

		// 构造opencourse

		OpenCourse openCourse = new OracleOpenCourse();
		// BasicSequence examSequence = new OracleBasicSequence();

		openCourse.setId(this.getTeachclass_id());

		// 增加一个默认的课程
		openCourse.setBzzCourse(getHzphExam(course_id));

		request.getSession().setAttribute("courseId", course_id);
		request.getSession().setAttribute("openCourse", openCourse);
		request.getSession().setAttribute("userType", "teacher");
		request.getSession().setAttribute("courseware_priv", teacherOperationManage.getCoursewareManagerPriv());
		request.getSession().setAttribute("piciId", piciId);
		request.getSession().setAttribute("tsId", tsId);

		this.setTeachclass_id(teachclass_id);
		if (userSession.getUserLoginType().equals("3")) {
			return "sudent_exam_detail";
		} else {
			return "sudent_exam_detail_stu";
		}
	}

	/**
	 * 进入考试批次
	 * 
	 * @param course_id
	 * @return
	 * @author linjie
	 */
	public PeBzzTchCourse getHzphExam(String course_id) {
		PeBzzTchCourse course = new PeBzzTchCourse();
		course.setId(course_id);
		course.setCode(course_id);
		course.setName(this.getPiciName());
		if (piciName != null && piciName.indexOf("%") > -1) {
			try {
				course.setName(URLDecoder.decode(this.getPiciName(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}

		return course;
	}

	/**
	 * 考试学生管理
	 * 
	 * @return
	 * @throws PlatformException
	 * @author linjie
	 */
	public String hzphExamstuManage() throws PlatformException {
		HttpServletRequest request = ServletActionContext.getRequest();
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession == null) {
			return "input";
		}
		String userId = userSession.getSsoUser().getId();

		String course_id = request.getParameter("batch_id");
		this.setTeachclass_id(course_id);

		try {
			this.setM4Session(userSession.getSsoUser(), "0", "student");
		} catch (PlatformException e) {
			e.printStackTrace();
		}

		Object o1 = request.getSession().getAttribute("student_eduplatform_user");
		Object o2 = request.getSession().getAttribute("student_eduplatform_priv");
		Student session_student;
		StudentPriv studentPriv;
		if (o1 != null)
			session_student = (Student) o1;
		else
			session_student = (Student) (request.getSession().getAttribute("eduplatform_user"));

		if (o2 != null)
			studentPriv = (StudentPriv) o2;
		else
			studentPriv = (StudentPriv) (request.getSession().getAttribute("eduplatform_priv"));

		PlatformFactory platformFactory = PlatformFactory.getInstance();
		StudentOperationManage studentOperationManage = platformFactory.creatStudentOperationManage(studentPriv);
		ManagerPriv includePriv = studentOperationManage.getNormalEntityManagePriv();

		InteractionUserPriv interactionUserPriv = studentOperationManage.getInteractionUserPriv(studentPriv.getStudentId(), this
				.getTeachclass_id());
		request.getSession().setAttribute("interactionUserPriv", interactionUserPriv);
		OpenCourse openCourse = new OracleOpenCourse();
		openCourse.setId(this.getTeachclass_id());
		openCourse.setBzzCourse(this.getHzphExam(course_id));
		request.getSession().removeAttribute("courseId");
		request.getSession().setAttribute("courseId", course_id);
		request.getSession().setAttribute("userId", userId);
		request.getSession().removeAttribute("openCourse");
		request.getSession().setAttribute("openCourse", openCourse);
		request.getSession().setAttribute("userType", "student");
		request.getSession().setAttribute("First", "1");
		request.getSession().removeAttribute("opencourseId");
		// request.getSession().setAttribute("opencourseId", opencourseId);
		// //进入学习使用
		return "onlineExam_list";
	}

	public String getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getTeachclass_id() {
		return teachclass_id;
	}

	public void setTeachclass_id(String teachclass_id) {
		this.teachclass_id = teachclass_id;
	}

	public String getOpencourseId() {
		return opencourseId;
	}

	public void setOpencourseId(String opencourseId) {
		this.opencourseId = opencourseId;
	}

	public String getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(String firstPage) {
		this.firstPage = firstPage;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getPiciName() {
		return piciName;
	}

	public void setPiciName(String piciName) {
		this.piciName = piciName;
	}

}
