package com.whaty.platform.sso.web.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleOpenCourse;
import com.whaty.platform.database.oracle.standard.interaction.OracleInteractionTeachClass;
import com.whaty.platform.entity.PlatformFactory;
import com.whaty.platform.entity.PlatformManage;
import com.whaty.platform.entity.StudentOperationManage;
import com.whaty.platform.entity.TeacherOperationManage;
import com.whaty.platform.entity.activity.OpenCourse;
import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.PeBzzPiciStudent;
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
import com.whaty.platform.interaction.InteractionTeachClass;
import com.whaty.platform.interaction.InteractionUserPriv;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.training.basic.StudyProgress;

/**
 * @param
 * @version 创建时间：2009-7-1 下午01:35:21
 * @return
 * @throws PlatformException
 * 类说明
 */
public class AssInteractionAction extends MyBaseAction {
	
	private String course_id;
	private String teacher_id;
	private String teachclass_id;
	private String firstPage;  //进入学习首个页面
	private String flag;       //后来添加，用于记录学员进入时，课程答疑时间是否开始
	private PeBzzStudent peBzzStudent;
	private String piciName;
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
	 * @return
	 * @throws PlatformException
	 * @author linjie
	 */
	public String InteractioManage() throws PlatformException {
		
		HttpServletRequest request =  ServletActionContext.getRequest();
		
		UserSession userSession = (UserSession) ActionContext.getContext()
		.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(userSession==null)
		{
			return "input";
		}
		
		String course_id = request.getParameter("course_id");
		String teacher_id = request.getParameter("teacher_id");
		this.setCourse_id(course_id);
		this.setTeacher_id(teacher_id);
		
		PeTeacher peTeacher=new PeTeacher();
		try {
			peTeacher=this.getTeacher();
		} catch (EntityException e1) {
			e1.printStackTrace();
			//this.setMsg(e1.getMessage());
			//return "msg";
		}
		
		try {
			this.setM4Session(peTeacher.getSsoUser(), "1","teacher");
		} catch (PlatformException e) {
			e.printStackTrace();
		}
		
		this.setTeachclass_id(course_id);
		
		
	  	Object o2 = request.getSession().getAttribute("teacher_eduplatform_priv");
		TeacherPriv session_teacherPriv;
		if(o2 != null)
			session_teacherPriv = (TeacherPriv)o2;
		else
			session_teacherPriv = (TeacherPriv)(request.getSession().getAttribute("eduplatform_priv"));
		

		PlatformFactory platformFactory = PlatformFactory.getInstance();
		TeacherOperationManage teacherOperationManage = platformFactory.creatTeacherOperationManage(session_teacherPriv);
		InteractionUserPriv interactionUserPriv = teacherOperationManage.getInteractionUserPriv(session_teacherPriv.getTeacherId(), teachclass_id);
		request.getSession().setAttribute("interactionUserPriv",interactionUserPriv);
		
		//构造opencourse
		
		OpenCourse openCourse =  new OracleOpenCourse();
		// BasicSequence examSequence = new OracleBasicSequence();
		
		openCourse.setId(this.getTeachclass_id());
		
		try {
			openCourse.setBzzCourse(this.getPeBzzCourse(course_id));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		request.getSession().setAttribute("courseId", course_id);
		request.getSession().setAttribute("openCourse",openCourse);
		request.getSession().setAttribute("userType", "teacher");
		request.getSession().setAttribute("courseware_priv",teacherOperationManage.getCoursewareManagerPriv());
		
		this.setTeachclass_id(teachclass_id);
		
		return "show_index";
	}
	/**
	 * 进入在线测试题库
	 * @return
	 * @throws PlatformException
	 * @author qiaoshijia
	 */
public String InteractiontkManage() throws PlatformException {
		
		HttpServletRequest request =  ServletActionContext.getRequest();
		
		UserSession userSession = (UserSession) ActionContext.getContext()
		.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(userSession==null)
		{
			return "input";
		}
		
		String course_id = request.getParameter("course_id");
		String teacher_id = request.getParameter("teacher_id");
		this.setCourse_id(course_id);
		this.setTeacher_id(teacher_id);
		
		PeTeacher peTeacher=new PeTeacher();
		try {
			peTeacher=this.getTeacher();
		} catch (EntityException e1) {
			e1.printStackTrace();
			//this.setMsg(e1.getMessage());
			//return "msg";
		}
		
		try {
			this.setM4Session(peTeacher.getSsoUser(), "1","teacher");
		} catch (PlatformException e) {
			e.printStackTrace();
		}
		
		this.setTeachclass_id(course_id);
		
		
	  	Object o2 = request.getSession().getAttribute("teacher_eduplatform_priv");
		TeacherPriv session_teacherPriv;
		if(o2 != null)
			session_teacherPriv = (TeacherPriv)o2;
		else
			session_teacherPriv = (TeacherPriv)(request.getSession().getAttribute("eduplatform_priv"));
		

		PlatformFactory platformFactory = PlatformFactory.getInstance();
		TeacherOperationManage teacherOperationManage = platformFactory.creatTeacherOperationManage(session_teacherPriv);
		InteractionUserPriv interactionUserPriv = teacherOperationManage.getInteractionUserPriv(session_teacherPriv.getTeacherId(), teachclass_id);
		request.getSession().setAttribute("interactionUserPriv",interactionUserPriv);
		
		//构造opencourse
		
		OpenCourse openCourse =  new OracleOpenCourse();
		// BasicSequence examSequence = new OracleBasicSequence();
		
		openCourse.setId(this.getTeachclass_id());
		
		try {
			openCourse.setBzzCourse(this.getPeBzzCourse(course_id));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		request.getSession().setAttribute("courseId", course_id);
		request.getSession().setAttribute("openCourse",openCourse);
		request.getSession().setAttribute("userType", "teacher");
		request.getSession().setAttribute("courseware_priv",teacherOperationManage.getCoursewareManagerPriv());
		
		this.setTeachclass_id(teachclass_id);
		
		return "show_index_tk";
	}
	/**
	 * 进入考试题库
	 * @return
	 * @throws PlatformException
	 * @author qiaoshijia
	 */
public String InteractionExamtkManage() throws PlatformException {
	
	HttpServletRequest request =  ServletActionContext.getRequest();
	
	UserSession userSession = (UserSession) ActionContext.getContext()
	.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
	if(userSession==null)
	{
		return "input";
	}
	
	String course_id = request.getParameter("course_id");
	String teacher_id = request.getParameter("teacher_id");
	this.setCourse_id(course_id);
	this.setTeacher_id(teacher_id);
	
	PeTeacher peTeacher=new PeTeacher();
	try {
		peTeacher=this.getTeacher();
	} catch (EntityException e1) {
		e1.printStackTrace();
		//this.setMsg(e1.getMessage());
		//return "msg";
	}
	
	try {
		this.setM4Session(peTeacher.getSsoUser(), "1","teacher");
	} catch (PlatformException e) {
		e.printStackTrace();
	}
	
	this.setTeachclass_id(course_id);
	
	
  	Object o2 = request.getSession().getAttribute("teacher_eduplatform_priv");
	TeacherPriv session_teacherPriv;
	if(o2 != null)
		session_teacherPriv = (TeacherPriv)o2;
	else
		session_teacherPriv = (TeacherPriv)(request.getSession().getAttribute("eduplatform_priv"));
	

	PlatformFactory platformFactory = PlatformFactory.getInstance();
	TeacherOperationManage teacherOperationManage = platformFactory.creatTeacherOperationManage(session_teacherPriv);
	InteractionUserPriv interactionUserPriv = teacherOperationManage.getInteractionUserPriv(session_teacherPriv.getTeacherId(), teachclass_id);
	request.getSession().setAttribute("interactionUserPriv",interactionUserPriv);
	
	//构造opencourse
	
	OpenCourse openCourse =  new OracleOpenCourse();
	// BasicSequence examSequence = new OracleBasicSequence();
	
	openCourse.setId(this.getTeachclass_id());
	
	try {
		openCourse.setBzzCourse(this.getPeBzzCourse(course_id));
	} catch (EntityException e) {
		e.printStackTrace();
	}
	
	request.getSession().setAttribute("courseId", course_id);
	request.getSession().setAttribute("openCourse",openCourse);
	request.getSession().setAttribute("userType", "teacher");
	request.getSession().setAttribute("courseware_priv",teacherOperationManage.getCoursewareManagerPriv());
	
	this.setTeachclass_id(teachclass_id);
	
	return "show_index_examtk";
}
/**
 * 学生点击进入学习后执行的方法
 * @return
 * @throws PlatformException
 * @throws UnsupportedEncodingException
 * @author qiaoshijia
 */
public String InteractionStuManage() throws PlatformException, UnsupportedEncodingException {

	HttpServletRequest request = ServletActionContext.getRequest();
	
	UserSession userSession = (UserSession) ActionContext.getContext()
	.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
	if(userSession==null)
	{
		return "input";
	}
	String userId = userSession.getSsoUser().getId();
	course_id = request.getParameter("course_id");
	/**
	 * 当点击进入学习时，课程的状态从为学习改为未完成状态,记录时间 
	 */
	
	String open_sql = "select pbto.id from pe_bzz_tch_course pbtc " + "  join pr_bzz_tch_opencourse pbto on pbtc.id = pbto.fk_course_id "
				+ "  left join pe_bzz_batch pbb on pbb.id = pbto.fk_batch_id " + " where pbtc.id = '" + course_id + "'"
				+ "   and (pbb.id = '40288a7b394d676d01394dad824c003b' or pbto.fk_batch_id is null) ";
	
	String opencourseId = "";
	try {
		List open = this.getGeneralService().getBySQL(open_sql);
		if(open.size() > 0) {
			opencourseId = (String)open.get(0);
		}
	} catch  (EntityException e) {
		e.printStackTrace();
	}
	
	String sql = "select tcs.id\n" + "  from training_course_student tcs\n" + "  inner join sso_user su on su.id=tcs.student_id\n"
				+ " inner join pr_bzz_tch_opencourse pbto on pbto.id = tcs.course_id\n" + " where  su.login_id = '" + userSession.getLoginId() + "' and pbto.id = '" + opencourseId + "'";


	String sqlappend = "";
	try {
		List list = this.getGeneralService().getBySQL(sql);
		if(list.size() == 0) {
			String insertSql = "insert into training_course_student(id,student_id,course_id,learn_status)" + 
				"values(sys_guid(),'" + userId + "','" + opencourseId + "','" + StudyProgress.UNCOMPLETE + "')";
			this.getGeneralService().executeBySQL(insertSql);
			String training_id = (String) this.getGeneralService().getBySQL(sql).get(0);
			String elective_sql = "insert into pr_bzz_tch_user_elective(id,fk_user_id,fk_tch_opencourse_id,fk_training_id,ELECTIVE_DATE) " + 
				"values(sys_guid(),'" + userId + "','" + opencourseId + "','" + training_id + "',sysdate)";
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
	firstPage=request.getParameter("firstPage");

	this.setTeachclass_id(course_id);
	
	try {
		this.setM4Session(userSession.getSsoUser(), "0","student");
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
	//request.getSession().setAttribute("now", now);
	request.getSession().setAttribute("userType", "student");
	request.getSession().setAttribute("First", "1");
	
	request.getSession().removeAttribute("opencourseId");
	request.getSession().setAttribute("opencourseId", opencourseId);  //进入学习使用
	
	request.getSession().removeAttribute("firstPage");
	request.getSession().setAttribute("firstPage", firstPage);
	DetachedCriteria dc = DetachedCriteria.forClass(PeBzzTchCourse.class);
	dc.add(Restrictions.eq("id", course_id));
	try {
		PeBzzTchCourse p = this.getGeneralService().getPeBzzTchCourse(dc);
		Date d = new Date();
		Date b = p.getAnswerBeginDate()==null ? new Date() : p.getAnswerBeginDate();
		Date e = p.getAnswerEndDate() == null ? new Date() : new Date(p.getAnswerEndDate().getTime() + 24*60*60*1000);
		if(d.after(b) && d.before(e)) {
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
	
//	/**
//	 * 免费课程跳转到此方法
//	 * @return
//	 * @throws PlatformException
//	 * @throws UnsupportedEncodingException
//	 * @author qiaoshijia
//	 */
//	public String InteractionStuManageFree() throws PlatformException,
//	UnsupportedEncodingException {
//		
//		HttpServletRequest request = ServletActionContext.getRequest();
//		
//		UserSession userSession = (UserSession) ActionContext.getContext()
//		.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//		if(userSession==null)
//		{
//			return "input";
//		}
//		String userId = userSession.getSsoUser().getId();
//		/**
//		 * 当点击进入学习时，插入选课表和training表
//		 */
//		//1.通过拿到开课的实体对象的PrBzzTchOpencourse
//		DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
//		dc.add(Restrictions.eq("id", opencourseId));
//		List<PrBzzTchOpencourse> list1;
//		try {
//			list1 = this.getGeneralService().getList(dc);
//			PrBzzTchOpencourse bzzTchOpencourse = null;
//			if(list1.size()>0){
//				bzzTchOpencourse = list1.get(0);
//			}
//			//2.拿到当前登陆人对应的学员实体对象
//			DetachedCriteria stuDc = DetachedCriteria.forClass(PeBzzStudent.class);
//			stuDc.createCriteria("ssoUser", "ssoUser");
//			stuDc.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
//			PeBzzStudent stu = (PeBzzStudent) this.getGeneralService().getList(stuDc).get(0);
//			
//			stuDc.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
//			try {
//				this.peBzzStudent = (PeBzzStudent) this.getGeneralService().getList(stuDc).get(0);
//			} catch (EntityException e) {
//				e.printStackTrace();
//			}
//			
//			DetachedCriteria eleDc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
//			eleDc.add(Restrictions.eq("prBzzTchOpencourse", bzzTchOpencourse));
//			eleDc.add(Restrictions.eq("peBzzStudent",this.peBzzStudent));
//			List eleList = this.getGeneralService().getList(eleDc);
//			if(eleList.size()<1){//当选课表无记录时插入选课表和training表
//				//3.插入training表
//				List saveList = new ArrayList();
//				TrainingCourseStudent trainingCourseStudent = new TrainingCourseStudent();
//				trainingCourseStudent.setPrBzzTchOpencourse(bzzTchOpencourse);
//				trainingCourseStudent.setSsoUser(peBzzStudent.getSsoUser());
//				trainingCourseStudent.setPercent(0.00);
//				trainingCourseStudent.setLearnStatus(StudyProgress.INCOMPLETE);
//				trainingCourseStudent.setGetDate(new Date());
//				saveList.add(trainingCourseStudent);
//				//4.插入选课表
//				PrBzzTchStuElective ele = new PrBzzTchStuElective();
//				ele.setPrBzzTchOpencourse(bzzTchOpencourse);
//				ele.setElectiveDate(new Date());
//				ele.setPeBzzStudent(stu);
//				ele.setSsoUser(userSession.getSsoUser());
//				ele.setTrainingCourseStudent(trainingCourseStudent);
//				saveList.add(ele);
//				this.getGeneralService().saveList(saveList);
//			}
//		} catch (EntityException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
//		
//		
//		course_id = request.getParameter("course_id");
//		opencourseId= request.getParameter("opencourseId");
//		firstPage=request.getParameter("firstPage");
//		//this.setCourse_id(course_id);
//		//this.setOpencourseId(opencourseId);
//		//this.setFirstPage(firstPage);
//		
//		this.setTeachclass_id(course_id);
//		
//		try {
//			this.setM4Session(userSession.getSsoUser(), "0","student");
//		} catch (PlatformException e) {
//			e.printStackTrace();
//		}
//		//System.out.println("id:"+userSession.getSsoUser().getId());
//		
//		Object o1 = request.getSession().getAttribute(
//				"student_eduplatform_user");
//		Object o2 = request.getSession().getAttribute(
//				"student_eduplatform_priv");
//		Student session_student;
//		StudentPriv studentPriv;
//		if (o1 != null)
//			session_student = (Student) o1;
//		else
//			session_student = (Student) (request.getSession()
//					.getAttribute("eduplatform_user"));
//		
//		if (o2 != null)
//			studentPriv = (StudentPriv) o2;
//		else
//			studentPriv = (StudentPriv) (request.getSession()
//					.getAttribute("eduplatform_priv"));
//		
//		PlatformFactory platformFactory = PlatformFactory.getInstance();
//		StudentOperationManage studentOperationManage = platformFactory
//				.creatStudentOperationManage(studentPriv);
//		
//		ManagerPriv includePriv = studentOperationManage
//				.getNormalEntityManagePriv();
//		
//		InteractionUserPriv interactionUserPriv = studentOperationManage
//				.getInteractionUserPriv(studentPriv.getStudentId(), this
//						.getTeachclass_id());
//		request.getSession().setAttribute("interactionUserPriv",
//				interactionUserPriv);
//		
//		OpenCourse openCourse = new OracleOpenCourse();
//		
//		openCourse.setId(this.getTeachclass_id());
//		
//		try {
//			openCourse.setBzzCourse(this.getPeBzzCourse(course_id));
//		} catch (EntityException e) {
//			e.printStackTrace();
//			this.setMsg(e.getMessage());
//			return "msg";
//		}
//		request.getSession().removeAttribute("courseId");
//		request.getSession().setAttribute("courseId", course_id);
//		request.getSession().setAttribute("userId", userId);
//		
//		request.getSession().removeAttribute("openCourse");
//		request.getSession().setAttribute("openCourse", openCourse);
//		//request.getSession().setAttribute("now", now);
//		request.getSession().setAttribute("userType", "student");
//		request.getSession().setAttribute("First", "1");
//		
//		request.getSession().removeAttribute("opencourseId");
//		request.getSession().setAttribute("opencourseId", opencourseId);  //进入学习使用
//		
//		request.getSession().removeAttribute("firstPage");
//		request.getSession().setAttribute("firstPage", firstPage);
//		DetachedCriteria dc1 = DetachedCriteria.forClass(PeBzzTchCourse.class);
//		dc.add(Restrictions.eq("id", course_id));
//		try {
//			PeBzzTchCourse p = null;
//			List peList = this.getGeneralService().getList(dc1);
//			if(peList.size()>0){
//				p = (PeBzzTchCourse)peList.get(0);
//			}
//			Date d = new Date();
//			Date b = p.getAnswerBeginDate()==null ? new Date() : p.getAnswerBeginDate();
//			Date e = p.getAnswerEndDate() == null ? new Date() : p.getAnswerEndDate();
//			if(d.after(b) && d.before(e)) {
//				flag = "true";
//			} else {
//				flag = "false";
//			}
//			request.getSession().setAttribute("flag", flag);
//		} catch (EntityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "show_stuindex";
//		}
	
	/**
	 * 获取教师信息
	 * @return
	 * @throws EntityException
	 * @author linjie
	 */
	private PeTeacher getTeacher() throws EntityException {

		DetachedCriteria peTeacherDC = DetachedCriteria
				.forClass(PeTeacher.class);
		peTeacherDC.add(Restrictions.eq("loginId", this.getTeacher_id()));
		try {
			return (PeTeacher) this.getGeneralService().getList(peTeacherDC)
					.get(0);
		} catch (EntityException e) {
			e.printStackTrace();
			throw new EntityException("没有该教师。");
		}
	}
	
	/**
	 * 根据课程id获取课程信息
	 * @param courseId
	 * @return
	 * @throws EntityException
	 * @author linjie
	 */
	private PeBzzTchCourse getPeBzzCourse(String courseId) throws EntityException {

		DetachedCriteria peCourseDC = DetachedCriteria
				.forClass(PeBzzTchCourse.class);
		peCourseDC.add(Restrictions.eq("id", courseId));
		try { 
			if(this.getGeneralService().getList(peCourseDC).size()>0){
				return (PeBzzTchCourse) this.getGeneralService().getList(peCourseDC).get(0);	
			}else{
				return new PeBzzTchCourse();
			}
		} catch (EntityException e) {
			e.printStackTrace();
			throw new EntityException("没有所选的课程");
		}
	}
	/**
	 * 将用户信息写入session
	 * @param user
	 * @param loginType
	 * @param type
	 * @throws PlatformException
	 * @author linjie
	 */
	public void setM4Session(SsoUser user, String loginType, String type)
			throws PlatformException {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (SsoConstant.SSO_TEACHER.equals(loginType)) {

			PlatformFactory factory = PlatformFactory.getInstance();
			PlatformManage platformManage = factory.createPlatformManage();
			EntityUser euser = platformManage.getEntityUser(user.getId(),
					type);
			request.getSession().setAttribute("eduplatform_user", euser);
			TeacherPriv teacherPriv = factory.getTeacherPriv(user.getId());
			request.getSession().setAttribute("eduplatform_priv", teacherPriv);
		}
		else if(SsoConstant.SSO_STUDENT.equals(loginType)){
				
//			PlatformFactory factory=PlatformFactory.getInstance();
//			PlatformManage platformManage=factory.createPlatformManage();
//			EntityUser euser=platformManage.getEntityUser(user.getId(),type);
//			request.getSession().removeAttribute("eduplatform_user");
//			request.getSession().removeAttribute("eduplatform_priv");
//			request.getSession().setAttribute("eduplatform_user",euser);
//	  	  	StudentPriv studentPriv=factory.getStudentPriv(user.getId());
//	  	  	request.getSession().setAttribute("eduplatform_priv",studentPriv);
//	  	  	request.getSession().setAttribute("000000",user.getId()+"+"+user);
//	  	  	request.getSession().setAttribute("00000euser",euser.getId()+"+"+euser);


		}
	}
	/**
	 * qiaoshijia
	 * 在线考试
	 * @return
	 * @throws PlatformException
	 */
public String hzphExamManage() throws PlatformException {
		
		HttpServletRequest request =  ServletActionContext.getRequest();
		
		UserSession userSession = (UserSession) ActionContext.getContext()
		.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(userSession==null)
		{
			return "input";
		}
		
		String course_id = request.getParameter("batch_id");
		String teacher_id = request.getParameter("teacher_id");
		this.setCourse_id(course_id);
		this.setTeacher_id(teacher_id);
		
		PeTeacher peTeacher=new PeTeacher();
		try {
			peTeacher=this.getTeacher();
		} catch (EntityException e1) {
			e1.printStackTrace();
			//this.setMsg(e1.getMessage());
			//return "msg";
		}
		
		try {
			this.setM4Session(peTeacher.getSsoUser(), "1","teacher");
		} catch (PlatformException e) {
			e.printStackTrace();
		}
		
		this.setTeachclass_id(course_id);
		
		
	  	Object o2 = request.getSession().getAttribute("teacher_eduplatform_priv");
		TeacherPriv session_teacherPriv;
		if(o2 != null)
			session_teacherPriv = (TeacherPriv)o2;
		else
			session_teacherPriv = (TeacherPriv)(request.getSession().getAttribute("eduplatform_priv"));
		

		PlatformFactory platformFactory = PlatformFactory.getInstance();
		TeacherOperationManage teacherOperationManage = platformFactory.creatTeacherOperationManage(session_teacherPriv);
		InteractionUserPriv interactionUserPriv = teacherOperationManage.getInteractionUserPriv(session_teacherPriv.getTeacherId(), teachclass_id);
		request.getSession().setAttribute("interactionUserPriv",interactionUserPriv);
		
		//构造opencourse
		
		OpenCourse openCourse =  new OracleOpenCourse();
		// BasicSequence examSequence = new OracleBasicSequence();
		
		openCourse.setId(this.getTeachclass_id());
		
		//增加一个默认的课程
	    openCourse.setBzzCourse(getHzphExam(course_id));
		
		
		request.getSession().setAttribute("courseId", course_id);
		request.getSession().setAttribute("openCourse",openCourse);
		request.getSession().setAttribute("userType", "teacher");
		request.getSession().setAttribute("courseware_priv",teacherOperationManage.getCoursewareManagerPriv());
		
		this.setTeachclass_id(teachclass_id);
		
		return "show_index_exam";
	}

	/**
	 * 进入考试批次
	 * @param course_id
	 * @return
	 * @author linjie
	 */
    public PeBzzTchCourse getHzphExam(String course_id){
    	PeBzzTchCourse course=new PeBzzTchCourse();
    	course.setId(course_id);
    	course.setCode(course_id);
    	course.setName(this.getPiciName());
    	if(piciName!=null && piciName.indexOf("%")>-1){
    		try {
				course.setName(URLDecoder.decode(this.getPiciName(),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
    	}
    	
    	return course;
    }
    /**
     * 考试学生管理
     * @return
     * @throws PlatformException
     * @author linjie
     */
    public String hzphExamstuManage() throws PlatformException{
      HttpServletRequest request = ServletActionContext.getRequest();
		UserSession userSession = (UserSession) ActionContext.getContext()
		.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(userSession==null)
		{
			return "input";
		}
		String userId = userSession.getSsoUser().getId();
        
		String course_id = request.getParameter("batch_id");
		this.setTeachclass_id(course_id);
		
		try {
			this.setM4Session(userSession.getSsoUser(), "0","student");
		} catch (PlatformException e) {
			e.printStackTrace();
		}

		Object o1 = request.getSession().getAttribute(
				"student_eduplatform_user");
		Object o2 = request.getSession().getAttribute(
				"student_eduplatform_priv");
		Student session_student;
		StudentPriv studentPriv;
		if (o1 != null)
			session_student = (Student) o1;
		else
			session_student = (Student) (request.getSession()
					.getAttribute("eduplatform_user"));

		if (o2 != null)
			studentPriv = (StudentPriv) o2;
		else
			studentPriv = (StudentPriv) (request.getSession()
					.getAttribute("eduplatform_priv"));

		PlatformFactory platformFactory = PlatformFactory.getInstance();
		StudentOperationManage studentOperationManage = platformFactory
				.creatStudentOperationManage(studentPriv);
		ManagerPriv includePriv = studentOperationManage
				.getNormalEntityManagePriv();

		InteractionUserPriv interactionUserPriv = studentOperationManage
				.getInteractionUserPriv(studentPriv.getStudentId(), this
						.getTeachclass_id());
		request.getSession().setAttribute("interactionUserPriv",
				interactionUserPriv);
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
		//request.getSession().setAttribute("opencourseId", opencourseId);  //进入学习使用
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
