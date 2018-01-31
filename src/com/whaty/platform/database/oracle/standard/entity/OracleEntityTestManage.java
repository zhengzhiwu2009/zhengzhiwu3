/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourse;
import com.whaty.platform.database.oracle.standard.entity.test.OracleTestDataList;
import com.whaty.platform.database.oracle.standard.entity.user.OracleBasicUserList;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.database.oracle.standard.sso.OracleSsoUser;
import com.whaty.platform.database.oracle.standard.test.OracleTestUser;
import com.whaty.platform.database.oracle.standard.test.exam.OracleExamActivity;
import com.whaty.platform.database.oracle.standard.test.exam.OracleExamBatch;
import com.whaty.platform.database.oracle.standard.test.exam.OracleExamCourse;
import com.whaty.platform.database.oracle.standard.test.exam.OracleExamList;
import com.whaty.platform.database.oracle.standard.test.exam.OracleExamRoom;
import com.whaty.platform.database.oracle.standard.test.exam.OracleExamUser;
import com.whaty.platform.entity.EntityTestManage;
import com.whaty.platform.entity.EntityTestPriv;
import com.whaty.platform.entity.test.TestBatch;
import com.whaty.platform.entity.test.TestCourse;
import com.whaty.platform.entity.test.TestSequence;
import com.whaty.platform.entity.test.TestSite;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.entity.user.StudentEduInfo;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.sso.SsoUser;
import com.whaty.platform.test.TestFactory;
import com.whaty.platform.test.TestPriv;
import com.whaty.platform.test.exam.ExamCourse;
import com.whaty.platform.test.exam.ExamUser;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserLog;

/**
 * @author chenjian
 * 
 */
public class OracleEntityTestManage extends EntityTestManage {
	EntityTestPriv testPriv;

	public OracleEntityTestManage(EntityTestPriv testPriv) {
		this.testPriv = testPriv;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#addTestBatch(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public void addTestBatch(String title, String startDate, String endDate,
			String note) throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#updateTestBatch(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public void updateTestBatch(String id, String title, String startDate,
			String endDate, String note) throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#deleteTestBatch(java.util.List)
	 */
	public void deleteTestBatch(List idList) throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#getTestBatch(java.lang.String)
	 */
	public TestBatch getTestBatch(String id) throws PlatformException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#getTestBatch()
	 */
	public TestBatch getTestBatch() throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#getTestBatchs()
	 */
	public List getTestBatchs() throws PlatformException {
		OracleTestDataList testDataList = new OracleTestDataList();
		return testDataList.searchTestBatch(null, null, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#addTestSequence(java.lang.String,
	 *      java.util.Date, java.util.Date, java.lang.String,
	 *      com.whaty.platform.entity.test.TestBatch)
	 */
	public void addTestSequence(String title, Date startTime, Date endTime,
			String note, TestBatch testBatch) throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#updateTestSequence(java.lang.String,
	 *      java.lang.String, java.util.Date, java.util.Date, java.lang.String,
	 *      com.whaty.platform.entity.test.TestBatch)
	 */
	public void updateTestSequence(String id, String title, Date startTime,
			Date endTime, String note, TestBatch testBatch)
			throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#deleteTestSequence(java.util.List)
	 */
	public void deleteTestSequence(List idList) throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#getTestSequence(java.lang.String)
	 */
	public TestSequence getTestSequence(String id) throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#getTestSequence()
	 */
	public TestSequence getTestSequence() throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#getTestSequences(com.whaty.platform.entity.test.TestBatch)
	 */
	public List getTestSequences(TestBatch testBatch) throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#addCourses(java.lang.String,
	 *      java.lang.String, java.lang.String,
	 *      com.whaty.platform.entity.test.TestSequence)
	 */
	public void addCourses(String courseId, String courseName, String note,
			TestSequence testSequence) throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#deleteCourses(java.util.List,
	 *      com.whaty.platform.entity.test.TestSequence)
	 */
	public void deleteCourses(List idList, TestSequence testSequence)
			throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#getTestCourse(java.lang.String)
	 */
	public TestCourse getTestCourse(String courseId) throws PlatformException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#getTestCourse()
	 */
	public TestCourse getTestCourse() throws PlatformException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#getTestCourses(com.whaty.platform.entity.test.TestSequence)
	 */
	public List getTestCourses(TestSequence testSequence)
			throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#getTestCourses(com.whaty.platform.entity.test.TestBatch)
	 */
	public List getTestCourses(TestBatch testBatch) throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#addStudentOfCourse(com.whaty.platform.entity.test.TestCourse,
	 *      java.util.List, com.whaty.platform.entity.test.TestSite)
	 */
	public void addStudentOfCourse(TestCourse testCourse, List studentList,
			TestSite testSite) throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#deleteStudentOfCourse(com.whaty.platform.entity.test.TestCourse,
	 *      java.util.List, com.whaty.platform.entity.test.TestSite)
	 */
	public void deleteStudentOfCourse(TestCourse testCourse, List studentList,
			TestSite testSite) throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#getNumberOfStudent(com.whaty.platform.entity.test.TestCourse,
	 *      com.whaty.platform.entity.test.TestSite)
	 */
	public int getNumberOfStudent(TestCourse testCourse, TestSite testSite)
			throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#addTestRoom(java.lang.String,
	 *      java.lang.String, java.lang.String,
	 *      com.whaty.platform.entity.test.TestSite)
	 */
	public void addTestRoom(String title, String address, String note,
			TestSite testSite) throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#updateTestRoom(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      com.whaty.platform.entity.test.TestSite)
	 */
	public void updateTestRoom(String id, String title, String address,
			String note, TestSite testSite) throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#deleteTestRoom(java.util.List,
	 *      com.whaty.platform.entity.test.TestSite)
	 */
	public void deleteTestRoom(List idList, TestSite testSite)
			throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#arrangeDesk(com.whaty.platform.entity.test.TestBatch)
	 */
	public void arrangeDesk(TestBatch testBatch) throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#arrangeDesk(com.whaty.platform.entity.test.TestBatch,
	 *      com.whaty.platform.entity.test.TestSite)
	 */
	public void arrangeDesk(TestBatch testBatch, TestSite testSite)
			throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#arrangeDesk(com.whaty.platform.entity.test.TestSequence)
	 */
	public void arrangeDesk(TestSequence testSequence) throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#arrangeDesk(com.whaty.platform.entity.test.TestSequence,
	 *      com.whaty.platform.entity.test.TestSite)
	 */
	public void arrangeDesk(TestSequence testSequence, TestSite testSite)
			throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#arrangeDesk(com.whaty.platform.entity.test.TestCourse)
	 */
	public void arrangeDesk(TestCourse testCourse) throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#arrangeDesk(com.whaty.platform.entity.test.TestCourse,
	 *      com.whaty.platform.entity.test.TestSite)
	 */
	public void arrangeDesk(TestCourse testCourse, TestSite testSite)
			throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TestManage#searchTestBatch(java.lang.String)
	 */
	public List searchTestBatch(String title) throws PlatformException {
		OracleTestDataList testDataList = new OracleTestDataList();
		SearchProperty searchProperty = new SearchProperty("title", title);
		List searchpropertyList = new ArrayList();
		searchpropertyList.add(searchProperty);
		return testDataList.searchTestBatch(null, searchpropertyList, null);
	}

	public List getTestStudent(Page page, String site_id, String edutype_id,
			String major_id, String grade_id, String name, String regno)
			throws PlatformException {
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("u.site_id", site_id, "="));
		}
		if (edutype_id != null && !edutype_id.equals("")) {
			searchProperties.add(new SearchProperty("u.edutype_id", edutype_id,
					"="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties
					.add(new SearchProperty("u.major_id", major_id, "="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties
					.add(new SearchProperty("u.grade_id", grade_id, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("u.name", name, "like"));
		}
		if (regno != null && !regno.equals("")) {
			searchProperties.add(new SearchProperty("u.reg_no", regno, "like"));
		}

		OracleBasicUserList oracleBasicUserList = new OracleBasicUserList();
		return oracleBasicUserList.getStudents(page, searchProperties, null);

	}

	public int getTestStudentNum(String site_id, String edutype_id,
			String major_id, String grade_id, String name, String regno)
			throws PlatformException {
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("u.site_id", site_id, "="));
		}
		if (edutype_id != null && !edutype_id.equals("")) {
			searchProperties.add(new SearchProperty("u.edutype_id", edutype_id,
					"="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties
					.add(new SearchProperty("u.major_id", major_id, "="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties
					.add(new SearchProperty("u.grade_id", grade_id, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("u.name", name, "like"));
		}
		if (regno != null && !regno.equals("")) {
			searchProperties.add(new SearchProperty("u.reg_no", regno, "like"));
		}

		OracleBasicUserList oracleBasicUserList = new OracleBasicUserList();
		return oracleBasicUserList.getStudentsNum(searchProperties);
	}

	public int addTestStudent(String login_id) throws PlatformException {
		OracleSsoUser ssouser = new OracleSsoUser();
		OracleTestUser testuser = new OracleTestUser();

		SsoUser getsso = ssouser.getSsoLoginUser(login_id);
		testuser.setId(getsso.getId());
		testuser.setLoginId(login_id);
		testuser.setEmail(getsso.getEmail());
		testuser.setName(ssouser.getName());
		testuser.setLoginType(ssouser.getLoginType());
		testuser.setPassword(ssouser.getPassword());
		testuser.setFtpaccount(null);
		int suc = testuser.add();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.testPriv.getSsoId()
								+ "</whaty><whaty>BEHAVIOR$|$addTestStudent</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public List getTestStudentByCourse(Page page, HttpServletRequest request)
			throws PlatformException {
		String examCourse_id = request.getParameter("examCourse_id");
		String examBatch_id = request.getParameter("examBatch_id");

		String sql = "select id,user_id,course_id,batch_id,name,reg_no,id_card,site_id,site_name,edutype_id,edutype_name,major_id,major_name,grade_id,grade_name from (select euc.id id,euc.user_id user_id,euc.course_id course_id,eub.batch_id batch_id,tui.name name,tui.login_id reg_no,stu.id_card id_card,s.id site_id,s.name site_name,e.id edutype_id,e.name edutype_name,m.id major_id,m.name major_name,g.id grade_id,g.name grade_name from (select id,user_id,course_id from test_examuser_course) euc,(select batch_id,user_id from test_examuser_batch) eub,(select id,name,login_id from test_user_info) tui,entity_student_info stu,(select id,name from entity_site_info) s,(select id,name from entity_edu_type) e,(select id,name from entity_major_info) m,(select id,name from entity_grade_info) g where euc.user_id=eub.user_id and eub.user_id=tui.id	and tui.login_id=stu.reg_no	and stu.site_id=s.id and stu.edutype_id=e.id and stu.major_id=m.id and stu.grade_id=g.id) where batch_id='"
				+ examBatch_id + "' and course_id='" + examCourse_id + "'";

		if (request.getParameter("site") != null
				&& !request.getParameter("site").equals("")) {
			sql = sql + " and site_id='" + request.getParameter("site") + "'";
		}
		if (request.getParameter("edutype") != null
				&& !request.getParameter("edutype").equals("")) {
			sql = sql + " and edutype_id='" + request.getParameter("edutype")
					+ "'";
		}
		if (request.getParameter("major") != null
				&& !request.getParameter("major").equals("")) {
			sql = sql + " and major_id='" + request.getParameter("major") + "'";
		}
		if (request.getParameter("grade") != null
				&& !request.getParameter("grade").equals("")) {
			sql = sql + " and grade_id='" + request.getParameter("grade") + "'";
		}
		if (request.getParameter("name") != null
				&& !request.getParameter("name").equals("")) {
			sql = sql + " and name like '%" + request.getParameter("name")
					+ "%'";
		}
		if (request.getParameter("sreg_no") != null
				&& !request.getParameter("sreg_no").equals("")) {
			sql = sql + " and reg_no like '%" + request.getParameter("sreg_no")
					+ "%'";
		}

		List testStudentByCourseList = new ArrayList();

		dbpool db = new dbpool();
		MyResultSet rs = null;
		try {
			db = new dbpool();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			// System.out.println(sql);
			while (rs != null && rs.next()) {
				Student student = new OracleStudent();
				student.setId(rs.getString("user_id")); // �õ�id,��ssoid,Ҳ��test_examuser_info��user_id,test_user_info��id

				student.setStudentInfo(new StudentEduInfo());

				student.getStudentInfo().setReg_no(rs.getString("reg_no")); // �õ�ѧ��
				student.setName(rs.getString("name")); // �õ�����
				student.getStudentInfo().setMajor_name(
						rs.getString("major_name")); // �õ�רҵ���
				student.getStudentInfo().setMajor_id(rs.getString("major_id")); // �õ�רҵID
				student.getStudentInfo().setGrade_id(rs.getString("grade_id")); // �õ��꼶
				// id
				student.getStudentInfo().setGrade_name(
						rs.getString("grade_name")); // �õ��꼶���
				student.getStudentInfo().setEdutype_id(
						rs.getString("edutype_id")); // �õ����id
				student.getStudentInfo().setEdutype_name(
						rs.getString("edutype_name")); // �õ�������
				student.getStudentInfo().setSite_id(rs.getString("site_id")); // �õ���ѧվid
				student.getStudentInfo()
						.setSite_name(rs.getString("site_name")); // �õ���ѧվ���

				testStudentByCourseList.add(student);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return testStudentByCourseList;
	}

	public int getTestStudentByCourseNum(HttpServletRequest request)
			throws PlatformException {

		String sql = "select id,user_id,course_id,batch_id,name,reg_no,id_card,site_id,site_name,edutype_id,edutype_name,major_id,major_name,grade_id,grade_name from (select euc.id id,euc.user_id user_id,euc.course_id course_id,eub.batch_id batch_id,tui.name name,tui.login_id reg_no,stu.id_card id_card,s.id site_id,s.name site_name,e.id edutype_id,e.name edutype_name,m.id major_id,m.name major_name,g.id grade_id,g.name grade_name from (select id,user_id,course_id from test_examuser_course) euc,(select batch_id,user_id from test_examuser_batch) eub,(select id,name,login_id from test_user_info) tui,entity_student_info stu,(select id,name from entity_site_info) s,(select id,name from entity_edu_type) e,(select id,name from entity_major_info) m,(select id,name from entity_grade_info) g where euc.user_id=eub.user_id and eub.user_id=tui.id	and tui.login_id=stu.reg_no	and stu.site_id=s.id and stu.edutype_id=e.id and stu.major_id=m.id and stu.grade_id=g.id) where batch_id='"
				+ request.getParameter("examBatch_id")
				+ "' and course_id='"
				+ request.getParameter("examCourse_id") + "'";

		if (request.getParameter("site") != null
				&& !request.getParameter("site").equals("")) {
			sql = sql + " and site_id='" + request.getParameter("site")
					+ "'";
		}
		if (request.getParameter("edutype") != null
				&& !request.getParameter("edutype").equals("")) {
			sql = sql + " and edutype_id='"
					+ request.getParameter("edutype") + "'";
		}
		if (request.getParameter("major") != null
				&& !request.getParameter("major").equals("")) {
			sql = sql + " and major_id='" + request.getParameter("major")
					+ "'";
		}
		if (request.getParameter("grade") != null
				&& !request.getParameter("grade").equals("")) {
			sql = sql + " and grade_id='" + request.getParameter("grade")
					+ "'";
		}
		if (request.getParameter("name") != null
				&& !request.getParameter("name").equals("")) {
			sql = sql + " and name like '%" + request.getParameter("name")
					+ "%'";
		}
		if (request.getParameter("sreg_no") != null
				&& !request.getParameter("sreg_no").equals("")) {
			sql = sql + " and reg_no like '%" + request.getParameter("sreg_no")
					+ "%'";
		}

		dbpool db = new dbpool();
		int i = db.countselect(sql);
		return i;
	}

	public int getTestStudentForCourseNum(HttpServletRequest request)
			throws PlatformException {

		String sql = "select batch_id,user_id,name,reg_no,id,id_card,site_id,site_name,edutype_id,edutype_name,major_id,major_name,grade_id,grade_name from(select eub.batch_id batch_id,eub.user_id,tui.name name,tui.login_id reg_no,tui.id id,stu.id_card id_card,s.id site_id,s.name site_name,e.id edutype_id,e.name edutype_name,m.id major_id,m.name major_name,g.id grade_id,g.name grade_name from (select batch_id,user_id from test_examuser_batch) eub,(select id,name,login_id from test_user_info) tui,entity_student_info stu,(select id,name from entity_site_info) s,(select id,name from entity_edu_type) e,(select id,name from entity_major_info) m,(select id,name from entity_grade_info) g where eub.user_id=tui.id and tui.id=stu.id and stu.site_id=s.id and stu.edutype_id=e.id and stu.major_id=m.id and stu.grade_id=g.id and stu.id not in (select user_id from test_examuser_course where course_id='"
				+ request.getParameter("examCourse_id")
				+ "')) where  batch_id='"
				+ request.getParameter("examBatch_id") + "'";

		if (request.getParameter("site_id") != null
				&& !request.getParameter("site_id").equals("")) {
			sql = sql + " and site_id='" + request.getParameter("site_id")
					+ "'";
		}
		if (request.getParameter("edutype_id") != null
				&& !request.getParameter("edutype_id").equals("")) {
			sql = sql + " and edutype_id='"
					+ request.getParameter("edutype_id") + "'";
		}
		if (request.getParameter("major_id") != null
				&& !request.getParameter("major_id").equals("")) {
			sql = sql + " and major_id='" + request.getParameter("major_id")
					+ "'";
		}
		if (request.getParameter("grade_id") != null
				&& !request.getParameter("grade_id").equals("")) {
			sql = sql + " and grade_id='" + request.getParameter("grade_id")
					+ "'";
		}
		if (request.getParameter("name") != null
				&& !request.getParameter("name").equals("")) {
			sql = sql + " and name like '%" + request.getParameter("name")
					+ "%'";
		}
		if (request.getParameter("sreg_no") != null
				&& !request.getParameter("sreg_no").equals("")) {
			sql = sql + " and reg_no like '%" + request.getParameter("sreg_no")
					+ "%'";
		}

		dbpool db = new dbpool();
		int i = db.countselect(sql);
		return i;
	}

	public List getTestStudentForCourse(Page page, HttpServletRequest request)
			throws PlatformException {
		String sql = "select batch_id,user_id,name,reg_no,id,id_card,site_id,site_name,edutype_id,edutype_name,major_id,major_name,grade_id,grade_name from(select eub.batch_id batch_id,eub.user_id,tui.name name,tui.login_id reg_no,tui.id id,stu.id_card id_card,s.id site_id,s.name site_name,e.id edutype_id,e.name edutype_name,m.id major_id,m.name major_name,g.id grade_id,g.name grade_name from (select batch_id,user_id from test_examuser_batch) eub,(select id,name,login_id from test_user_info) tui,entity_student_info stu,(select id,name from entity_site_info) s,(select id,name from entity_edu_type) e,(select id,name from entity_major_info) m,(select id,name from entity_grade_info) g where eub.user_id=tui.id and tui.id=stu.id and stu.site_id=s.id and stu.edutype_id=e.id and stu.major_id=m.id and stu.grade_id=g.id and stu.id not in (select user_id from test_examuser_course where course_id='"
				+ request.getParameter("examCourse_id")
				+ "')) where  batch_id='"
				+ request.getParameter("examBatch_id") + "'";

		if (request.getParameter("site_id") != null
				&& !request.getParameter("site_id").equals("")) {
			sql = sql + " and site_id='" + request.getParameter("site_id")
					+ "'";
		}
		if (request.getParameter("edutype_id") != null
				&& !request.getParameter("edutype_id").equals("")) {
			sql = sql + " and edutype_id='"
					+ request.getParameter("edutype_id") + "'";
		}
		if (request.getParameter("major_id") != null
				&& !request.getParameter("major_id").equals("")) {
			sql = sql + " and major_id='" + request.getParameter("major_id")
					+ "'";
		}
		if (request.getParameter("grade_id") != null
				&& !request.getParameter("grade_id").equals("")) {
			sql = sql + " and grade_id='" + request.getParameter("grade_id")
					+ "'";
		}
		if (request.getParameter("name") != null
				&& !request.getParameter("name").equals("")) {
			sql = sql + " and name like '%" + request.getParameter("name")
					+ "%'";
		}
		if (request.getParameter("sreg_no") != null
				&& !request.getParameter("sreg_no").equals("")) {
			sql = sql + " and reg_no like '%" + request.getParameter("sreg_no")
					+ "%'";
		}

		List testStudentForCourseList = new ArrayList();

		dbpool db = new dbpool();
		MyResultSet rs = null;
		try {
			db = new dbpool();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			// System.out.println(sql);
			while (rs != null && rs.next()) {
				Student student = new OracleStudent();
				student.setId(rs.getString("user_id")); // �õ�id,��ssoid,Ҳ��test_examuser_batch��user_id,test_user_info��id

				student.setStudentInfo(new StudentEduInfo());

				student.getStudentInfo().setReg_no(rs.getString("reg_no")); // �õ�ѧ��
				student.setName(rs.getString("name")); // �õ�����
				student.getStudentInfo().setMajor_name(
						rs.getString("major_name")); // �õ�רҵ���
				student.getStudentInfo().setMajor_id(rs.getString("major_id")); // �õ�רҵID
				student.getStudentInfo().setGrade_id(rs.getString("grade_id")); // �õ��꼶
				// id
				student.getStudentInfo().setGrade_name(
						rs.getString("grade_name")); // �õ��꼶���
				student.getStudentInfo().setEdutype_id(
						rs.getString("edutype_id")); // �õ����id
				student.getStudentInfo().setEdutype_name(
						rs.getString("edutype_name")); // �õ�������
				student.getStudentInfo().setSite_id(rs.getString("site_id")); // �õ���ѧվid
				student.getStudentInfo()
						.setSite_name(rs.getString("site_name")); // �õ���ѧվ���

				testStudentForCourseList.add(student);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return testStudentForCourseList;

	}

	public int addTestUserBatch(String user_id, String batch_id)
			throws PlatformException {
		OracleExamUser userbatch = new OracleExamUser();
		OracleExamBatch exambatch = new OracleExamBatch();
		exambatch.setId(batch_id);
		OracleTestUser examuser = new OracleTestUser();
		examuser.setId(user_id);
		userbatch.setTestUser(examuser);
		userbatch.setExamBatch(exambatch);
		int suc = userbatch.add();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.testPriv.getSsoId()
								+ "</whaty><whaty>BEHAVIOR$|$addTestUserBatch</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int addTestCourse(String course_id, String batch_id)
			throws PlatformException {
		OracleExamCourse examCourse = new OracleExamCourse();
		OracleExamBatch exambatch = new OracleExamBatch();
		exambatch.setId(batch_id);
		examCourse.setExamBatch(exambatch);
		examCourse.setCourse_id(course_id);
		examCourse.setStartDate("1900-01-01 00:00:00");
		examCourse.setEndDate("1900-01-01 00:00:00");
		OracleCourse course = new OracleCourse(course_id);
		examCourse.setName(course.getName());
		int suc = examCourse.add();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.testPriv.getSsoId()
								+ "</whaty><whaty>BEHAVIOR$|$addTestCourse</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int addTestUserCourse(String user_id, String course_id)
			throws PlatformException {
		OracleExamActivity usercourse = new OracleExamActivity();
		int suc = usercourse.courseAddUser(user_id, course_id);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.testPriv.getSsoId()
								+ "</whaty><whaty>BEHAVIOR$|$addTestUserCourse</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public List getStudent(Page page, String site_id, String edutype_id,
			String major_id, String grade_id, String name, String regno)
			throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getStudent(page, site_id, edutype_id, major_id,
				grade_id, name, regno);
	}

	public List getStudent(Page page, String site_id, String edutype_id,
			String major_id, String grade_id, String name, String regno,
			String batch_id) throws PlatformException {

		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getStudent(page, site_id, edutype_id, major_id,
				grade_id, name, regno, batch_id);

	}

	public int getStudentNum(String site_id, String edutype_id,
			String major_id, String grade_id, String name, String regno)
			throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getStudentNum(site_id, edutype_id, major_id,
				grade_id, name, regno);
	}

	public int getStudentNum(String site_id, String edutype_id,
			String major_id, String grade_id, String name, String regno,
			String batch_id) throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getStudentNum(site_id, edutype_id, major_id,
				grade_id, name, regno, batch_id);
	}

	public List getBatches(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getBatches(page, searchproperty, orderproperty);
	}

	public int importStudent(String open_course_id, String course_id,
			String batch_id) throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		int suc = examActivity.importStudent(open_course_id, course_id,
				batch_id);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.testPriv.getSsoId()
								+ "</whaty><whaty>BEHAVIOR$|$importStudent</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int importStudentExpend(String open_course_id, String course_id,
			String batch_id) throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		int suc = examActivity.importStudentExpend(open_course_id, course_id,
				batch_id);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.testPriv.getSsoId()
								+ "</whaty><whaty>BEHAVIOR$|$importStudentExpend</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public void allotExamRoom(List userList, List courseList, String num)
			throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		examActivity.allotStudents(userList, courseList, num);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.testPriv.getSsoId()
								+ "</whaty><whaty>BEHAVIOR$|$allotExamRoom</whaty><whaty>STATUS$|$"
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
	}

	public void allotSiteExamRoom(String site_id, List userList,
			List courseList, String num) throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		examActivity.allotSiteStudents(site_id, userList, courseList, num);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.testPriv.getSsoId()
								+ "</whaty><whaty>BEHAVIOR$|$allotSiteExamRoom</whaty><whaty>STATUS$|$"
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
	}

	public TestPriv getTestPriv() throws PlatformException {
		TestFactory testFactory = TestFactory.getInstance();
		TestPriv testPriv = testFactory.getTestPriv();
		return testPriv;
	}

	public List getStudentsByCourseId(List CourseId, String examBatchId)
			throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();

		return examActivity.getStudentsByCourseId(CourseId, examBatchId);
	}

	public List getSiteStudentsByCourseId(String site_id, List CourseId,
			String examBatchId) throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getSiteStudentsByCourseId(site_id, CourseId,
				examBatchId);
	}

	public List getTotalTestRooms(Page page, String batch_id)
			throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getTotalTestRooms(page, batch_id);
	}

	public List getTotalTestRooms(Page page, HttpServletRequest request)
			throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		ArrayList searchList = new ArrayList();
		ArrayList orderList = new ArrayList();
		if (request.getParameter("exambatchid") != null
				&& !request.getParameter("exambatchid").equals("")) {
			searchList.add(new SearchProperty("test_batch_id", request
					.getParameter("exambatchid"), "="));
		}
		if (request.getParameter("examsequence_id") != null
				&& !request.getParameter("examsequence_id").equals("")) {
			searchList.add(new SearchProperty("EXAMSEQUENCE_ID", request
					.getParameter("examsequence_id"), "="));
		}
		orderList.add(new OrderProperty("test_batch_id"));
		orderList.add(new OrderProperty("EXAMSEQUENCE_ID"));
		return examActivity.getTotalTestRooms(page, searchList, orderList);
	}

	public List getSiteTotalTestRooms(Page page, HttpServletRequest request)
			throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		ArrayList searchList = new ArrayList();
		ArrayList orderList = new ArrayList();
		if (request.getParameter("exambatchid") != null
				&& !request.getParameter("exambatchid").equals("")) {
			searchList.add(new SearchProperty("test_batch_id", request
					.getParameter("exambatchid"), "="));
		}
		if (request.getParameter("examsequence_id") != null
				&& !request.getParameter("examsequence_id").equals("")) {
			searchList.add(new SearchProperty("EXAMSEQUENCE_ID", request
					.getParameter("examsequence_id"), "="));
		}
		orderList.add(new OrderProperty("test_batch_id"));
		orderList.add(new OrderProperty("EXAMSEQUENCE_ID"));
		return examActivity.getSiteTotalTestRooms(page, searchList, orderList,
				request.getParameter("site_id"));
	}

	public int getTotalTestRoomsNum(String batch_id) throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getTotalTestRoomsNum(batch_id);
	}

	public int getTotalTestRoomsNum(HttpServletRequest request)
			throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		ArrayList searchList = new ArrayList();
		if (request.getParameter("exambatchid") != null
				&& !request.getParameter("exambatchid").equals("")) {
			searchList.add(new SearchProperty("test_batch_id", request
					.getParameter("exambatchid"), "="));
		}
		if (request.getParameter("examsequence_id") != null
				&& !request.getParameter("examsequence_id").equals("")) {
			searchList.add(new SearchProperty("EXAMSEQUENCE_ID", request
					.getParameter("examsequence_id"), "="));
		}
		return examActivity.getTotalTestRoomsNum(searchList);
	}

	public int getSiteTotalTestRoomsNum(HttpServletRequest request)
			throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		ArrayList searchList = new ArrayList();
		if (request.getParameter("exambatchid") != null
				&& !request.getParameter("exambatchid").equals("")) {
			searchList.add(new SearchProperty("test_batch_id", request
					.getParameter("exambatchid"), "="));
		}
		if (request.getParameter("examsequence_id") != null
				&& !request.getParameter("examsequence_id").equals("")) {
			searchList.add(new SearchProperty("EXAMSEQUENCE_ID", request
					.getParameter("examsequence_id"), "="));
		}
		return examActivity.getSiteTotalTestRoomsNum(searchList, request
				.getParameter("site_id"));
	}

	public int getSiteTestRoomsNum(String site_id, String batch_id,
			String examsequence_id) throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getSiteTestRoomsNum(site_id, batch_id,
				examsequence_id);
	}

	public int getSiteTestRoomsNum(String site_id, HttpServletRequest request)
			throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getSiteTestRoomsNum(site_id, request);
	}

	public int getTestRoomsNum(String site_id, HttpServletRequest request)
			throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getTestRoomsNum(site_id, request);
	}

	public List getSiteTotalTestRooms(String site_id, Page page,
			String batch_id, String examsequence_id) throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getSiteTestRooms(site_id, page, batch_id,
				examsequence_id);
	}

	public List getSiteTotalTestRooms(String site_id, Page page,
			HttpServletRequest request) throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getSiteTestRooms(site_id, page, request);
	}

	public List getTestRooms(String site_id, Page page,
			HttpServletRequest request) throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getTestRooms(site_id, page, request);
	}

	public List getRoomNo(String course_id, String batchId)
			throws PlatformException {
		OracleExamRoom examRoom = new OracleExamRoom();
		return examRoom.getExamRoomNo(course_id, batchId);
	}

	public List getExamRooms(String course_id, String batchId, String siteId)
			throws PlatformException {
		OracleExamRoom examRoom = new OracleExamRoom();
		return examRoom.getExamRooms(course_id, batchId, siteId);
	}

	public List getExamRoomNoStudents(String course_id, String room_no,
			String batchId) throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getExamRoomNoStudents(course_id, room_no, batchId);
	}

	public List getSiteExamRoomNoStudents(String course_id, String room_no,
			String batchId, String site_id) throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getSiteExamRoomNoStudents(course_id, room_no,
				batchId, site_id);
	}

	public ExamCourse getExamInfo(String course_id, String batch_id)
			throws PlatformException {
		OracleExamCourse examcourse = new OracleExamCourse();

		return examcourse.getExamCourse(batch_id, course_id);
	}

	public void setExamCode(String batch_id) throws PlatformException {
		List searchList = new ArrayList();
		if (batch_id != null)
			searchList.add(new SearchProperty("batch_id", batch_id, "="));
		OracleExamList testList = new OracleExamList();
		List examUserList = testList.getExamUsers(null, searchList, null);
		int num = 0;
		if (examUserList != null) {
			num = examUserList.size();
		}
		for (int i = 0; i < num; i++) {
			int no = i + 1;
			ExamUser examUser = (ExamUser) examUserList.get(i);
			Student student = new OracleStudent(examUser.getTestUser().getId());
			String examCode = student.getStudentInfo().getSite_id()
					+ student.getStudentInfo().getMajor_id()
					+ student.getStudentInfo().getEdutype_id() + no;
			examUser.setExamcode(examCode);
			examUser.update();
		}
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.testPriv.getSsoId()
								+ "</whaty><whaty>BEHAVIOR$|$setExamCode</whaty><whaty>STATUS$|$"
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
	}

	public List getAllotTestRooms(Page page, String batch_id)
			throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getAllotTestRooms(page, batch_id);
	}

	public int getAllotTestRoomsNum(String batch_id) throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getAllotTestRoomsNum(batch_id);
	}

	public int allotTestRooms(String[] examroom_id) throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.allotTestRooms(examroom_id);
	}

	public List getExamCourses(String user_id) throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getExamCourses(user_id);
	}

	public String getTotalStudentsByCourseId(String course_id)
			throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getTotalStudentsByCourseId(course_id);
	}

	public String getSiteTotalStudentsByCourseId(String site_id,
			String course_id) throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getSiteTotalStudentsByCourseId(site_id, course_id);
	}

	public int addExamRoomSpotAndNum(String course_id, String address,
			String room_num) throws PlatformException {
		OracleExamRoom examRoom = new OracleExamRoom();
		int suc = examRoom.add(course_id, address, room_num);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.testPriv.getSsoId()
								+ "</whaty><whaty>BEHAVIOR$|$addExamRoomSpotAndNum</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int addExamRoomSpotAndNum(String course_id, String address,
			String room_num, String teacher) throws PlatformException {
		OracleExamRoom examRoom = new OracleExamRoom();
		int suc = examRoom.add(course_id, address, room_num, teacher, null);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.testPriv.getSsoId()
								+ "</whaty><whaty>BEHAVIOR$|$addExamRoomSpotAndNum</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int addSiteExamRoomSpotAndNum(String site_id, String course_id,
			String address, String room_num, String teacher)
			throws PlatformException {
		OracleExamRoom examRoom = new OracleExamRoom();
		int suc = examRoom.add(site_id, course_id, address, room_num, teacher,
				null);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.testPriv.getSsoId()
								+ "</whaty><whaty>BEHAVIOR$|$addSiteExamRoomSpotAndNum</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public List getExamRoom(String course_id) throws PlatformException {
		OracleExamRoom examRoom = new OracleExamRoom();
		return examRoom.getExamRoom(course_id);
	}

	public List getSiteExamRoom(String site_id, String course_id)
			throws PlatformException {
		OracleExamRoom examRoom = new OracleExamRoom();
		return examRoom.getSiteExamRoom(site_id, course_id);
	}

	public int updateExamRoom(String id, String address, String num)
			throws PlatformException {
		OracleExamRoom examRoom = new OracleExamRoom();
		int suc = examRoom.update(id, address, num);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.testPriv.getSsoId()
								+ "</whaty><whaty>BEHAVIOR$|$updateExamRoom</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int updateExamRoom(String id, String address, String num,
			String teacher) throws PlatformException {
		OracleExamRoom examRoom = new OracleExamRoom();
		int suc = examRoom.update(id, address, num, teacher);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.testPriv.getSsoId()
								+ "</whaty><whaty>BEHAVIOR$|$updateExamRoom</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int deleteExamRoom(String id) throws PlatformException {
		OracleExamRoom examRoom = new OracleExamRoom(id);
		int suc = examRoom.delete();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.testPriv.getSsoId()
								+ "</whaty><whaty>BEHAVIOR$|$deleteExamRoom</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int getTotalExamRoomNumSet(String course_id)
			throws PlatformException {
		OracleExamRoom examRoom = new OracleExamRoom();
		return examRoom.getTotalExamRoomNumSet(course_id);
	}

	public int getSiteTotalExamRoomNumSet(String site_id, String course_id)
			throws PlatformException {
		OracleExamRoom examRoom = new OracleExamRoom();
		return examRoom.getSiteTotalExamRoomNumSet(site_id, course_id);
	}

	public int setStudent(String course_id, String batch_id)
			throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		int suc = examActivity.setStudent(course_id, batch_id);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.testPriv.getSsoId()
								+ "</whaty><whaty>BEHAVIOR$|$setStudent</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int setSiteStudent(String site_id, String course_id, String batch_id)
			throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		int suc = examActivity.setSiteStudent(site_id, course_id, batch_id);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.testPriv.getSsoId()
								+ "</whaty><whaty>BEHAVIOR$|$setSiteStudent</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public List getExamStat(String batch_id, String site_id)
			throws PlatformException {
		OracleExamList list = new OracleExamList();
		return list.getExamStat(batch_id, site_id);
	}

	public List getExamStat(String batch_id, String site_id, String order)
			throws PlatformException {
		OracleExamList list = new OracleExamList();
		return list.getExamStat(batch_id, site_id, order);
	}

	public List getExamStatByCourse(String batch_id, String site_id,
			String order) throws PlatformException {
		OracleExamList list = new OracleExamList();
		return list.getExamStatByCourse(batch_id, site_id, order);
	}

	public List getExamStat2(String batch_id, String site_id)
			throws PlatformException {
		OracleExamList list = new OracleExamList();
		return list.getExamStat2(batch_id, site_id);
	}

	public List getExamStat3(String batch_id, String site_id, String course_id,
			String sequence_id) throws PlatformException {
		OracleExamList list = new OracleExamList();
		return list.getExamStat3(batch_id, site_id, course_id, sequence_id);
	}

	public int getTestCoursesNum(HttpServletRequest request)
			throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getTestCoursesNum(request);
	}

	public List getTestCourses(Page page, HttpServletRequest request)
			throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getTestCourses(page, request);
	}

	public int getSiteTestCoursesNum(HttpServletRequest request)
			throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getSiteTestCoursesNum(request);
	}

	public List getSiteTestCourses(Page page, HttpServletRequest request)
			throws PlatformException {
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.getSiteTestCourses(page, request);
	}

	public int getOpenCoursesNum(String major_id, String semesterId,
			String course_id, String course_name, String batch_id)
			throws PlatformException {
		List searchproperty = new ArrayList();
		searchproperty.add(new SearchProperty("semester_id", semesterId, "="));
		if (major_id != null && !major_id.equals("")
				&& !major_id.equals("null"))
			searchproperty.add(new SearchProperty("major_id", major_id));
		if (course_id != null && !course_id.equals("")) {
			searchproperty.add(new SearchProperty("course_id", course_id, "="));
		}
		if (course_name != null && !course_name.equals("")) {
			searchproperty.add(new SearchProperty("course_name", course_name,
					"like"));
		}
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.searchOpenCourseNum(searchproperty, batch_id);
	}

	public List getOpenCourses(Page page, String major_id, String semesterId,
			String course_id, String course_name, String batch_id)
			throws PlatformException {
		List searchproperty = new ArrayList();
		if (major_id != null && !major_id.equals("")
				&& !major_id.equals("null"))
			searchproperty.add(new SearchProperty("major_id", major_id));
		if (semesterId != null && !semesterId.equals("")
				&& !semesterId.equals("null"))
			searchproperty.add(new SearchProperty("semester_id", semesterId));
		if (course_id != null && !course_id.equals("")) {
			searchproperty.add(new SearchProperty("course_id", course_id, "="));
		}
		if (course_name != null && !course_name.equals("")) {
			searchproperty.add(new SearchProperty("course_name", course_name,
					"like"));
		}
		List orderProperties = new ArrayList();
		orderProperties.add(new OrderProperty("course_id"));
		OracleExamActivity examActivity = new OracleExamActivity();
		return examActivity.searchOpenCourse(page, searchproperty,
				orderProperties, batch_id);

	}

	public List getSiteStatus(String examBatchId) throws PlatformException {
		List sites = new ArrayList();
		dbpool db = new dbpool();
		String sql = "select s.id, s.name, nvl(s1.status, 0) as s1, nvl(s2.status, 0) as s2, nvl(s3.status, 0) as s3 "
				+ "from entity_site_info s,(select distinct c.site_id, '1' as status from test_examuser_course a,"
				+ "test_examcourse_info b,entity_student_info c where a.user_id = c.id and a.course_id = b.id "
				+ "and b.test_batch_id = '"
				+ examBatchId
				+ "') s1,(select distinct c.site_id, '2' as status "
				+ "from test_examuser_course a,test_examcourse_info b,entity_student_info c "
				+ "where a.user_id = c.id and a.course_id = b.id and b.test_batch_id = '"
				+ examBatchId
				+ "' "
				+ "and a.room_id is null) s2,(select distinct c.site_id, '3' as status "
				+ "from test_examuser_course a,test_examcourse_info b,entity_student_info c "
				+ "where a.user_id = c.id and a.course_id = b.id and b.test_batch_id = '"
				+ examBatchId
				+ "' and a.room_id is not null) s3 where s.id = s1.site_id(+) and s.id = s2.site_id(+) "
				+ "and s.id = s3.site_id(+) order by s.id";
		MyResultSet rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				List site = new ArrayList();
				site.add(rs.getString("id"));
				site.add(rs.getString("name"));
				site.add(rs.getString("s1"));
				site.add(rs.getString("s2"));
				site.add(rs.getString("s3"));
				sites.add(site);
			}
		} catch (SQLException e) {
		}
		db.close(rs);
		return sites;
	}

}
