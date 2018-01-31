/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleBasicEduList;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourse;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourseType;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleSemester;
import com.whaty.platform.entity.BasicSiteEduManage;
import com.whaty.platform.entity.basic.Course;
import com.whaty.platform.entity.basic.CourseType;
import com.whaty.platform.entity.basic.Semester;
import com.whaty.platform.entity.user.SiteManagerPriv;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserLog;
import com.whaty.util.log.Log;

/**
 * @author wq
 * 
 */
public class OracleBasicSiteEduManage extends BasicSiteEduManage {
	SiteManagerPriv basicManagePriv;

	public OracleBasicSiteEduManage(SiteManagerPriv amanagerpriv) {
		this.basicManagePriv = amanagerpriv;
	}

	/***************************************************************************
	 * ���²���ΪCourse���� *
	 **************************************************************************/

	public Course getCourse(String tid) throws NoRightException {
		if (basicManagePriv.getCourse == 1) {
			return new OracleCourse(tid);
		} else {
			throw new NoRightException("��û�в鿴�γ���Ϣ��Ȩ�ޣ�");
		}
	}

	public int deleteCourse(String tid) throws NoRightException,
			PlatformException {
		if (basicManagePriv.deleteCourse == 1) {
			int suc = new OracleCourse(tid).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteCourse</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("��û��ɾ��γ���Ϣ��Ȩ�ޣ�");
		}
	}

	public int addCourse(String id, String name, String credit,
			String course_time, String major_id, String exam_type,
			String course_type, String teaching_type, String course_status,
			String ref_book, String note, String standard_fee,
			String drift_fee, String text_book, String text_book_price,
			String redundance0, String redundance1, String redundance2,
			String redundance3, String redundance4) throws NoRightException,
			PlatformException {
		if (basicManagePriv.addCourse == 1) {
			OracleCourse course = new OracleCourse();
			course.setId(id);
			course.setName(name);
			course.setCredit(credit);
			course.setCourse_time(course_time);
			course.setMajor_id(major_id);
			course.setExam_type(exam_type);
			course.setCourse_type(course_type);
			course.setTeaching_type(teaching_type);
			course.setCourse_status(course_status);
			course.setRef_book(ref_book);
			course.setNote(note);
			course.setStandard_fee(standard_fee);
			course.setDrift_fee(drift_fee);
			course.setText_book(text_book);
			course.setText_book_price(text_book_price);
			course.setRedundance0(redundance0);
			course.setRedundance1(redundance1);
			course.setRedundance2(redundance2);
			course.setRedundance3(redundance3);
			course.setRedundance4(redundance4);
			int suc = course.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addCourse</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("��û����ӿγ���Ϣ��Ȩ�ޣ�");
		}
	}

	public int updateCourse(String id, String name, String credit,
			String course_time, String major_id, String exam_type,
			String course_type, String teaching_type, String course_status,
			String ref_book, String note, String standard_fee,
			String drift_fee, String text_book, String text_book_price,
			String redundance0, String redundance1, String redundance2,
			String redundance3, String redundance4) throws NoRightException {
		if (basicManagePriv.updateCourse == 1) {
			OracleCourse course = new OracleCourse();
			course.setId(id);
			course.setName(name);
			course.setCredit(credit);
			course.setCourse_time(course_time);
			course.setMajor_id(major_id);
			course.setExam_type(exam_type);
			course.setCourse_type(course_type);
			course.setTeaching_type(teaching_type);
			course.setCourse_status(course_status);
			course.setRef_book(ref_book);
			course.setNote(note);
			course.setStandard_fee(standard_fee);
			course.setDrift_fee(drift_fee);
			course.setText_book(text_book);
			course.setText_book_price(text_book_price);
			course.setRedundance0(redundance0);
			course.setRedundance1(redundance1);
			course.setRedundance2(redundance2);
			course.setRedundance3(redundance3);
			course.setRedundance4(redundance4);
			int suc = course.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$updateCourse</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("��û���޸Ŀγ���Ϣ��Ȩ�ޣ�");
		}
	}

	public List getCourses(Page page, List searchproperty, List orderproperty)
			throws NoRightException {
		if (basicManagePriv.getCourse == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCourses(page, searchproperty, orderproperty);
		} else {
			throw new NoRightException("��û�в鿴�γ���Ϣ��Ȩ��");
		}
	}

	public List getCourses(Page page) throws NoRightException {
		if (basicManagePriv.getCourse == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCourses(page, null, null);
		} else {
			throw new NoRightException("��û�в鿴�γ���Ϣ��Ȩ��");
		}
	}

	public List getCourses() throws NoRightException {
		if (basicManagePriv.getCourse == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCourses(null, null, null);
		} else {
			throw new NoRightException("��û�в鿴�γ���Ϣ��Ȩ��");
		}
	}

	public int getCoursesNum() throws NoRightException {
		if (basicManagePriv.getCourse == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCoursesNum(null);
		} else {
			throw new NoRightException("��û�в鿴�γ���Ϣ��Ȩ��");
		}
	}

	public int getCoursesNum(List searchproperty) throws NoRightException {
		if (basicManagePriv.getCourse == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCoursesNum(searchproperty);
		} else {
			throw new NoRightException("��û�в鿴�γ���Ϣ��Ȩ��");
		}
	}

	/***************************************************************************
	 * ���²���ΪCourseType���� *
	 **************************************************************************/

	public CourseType getCourseType(String tid) throws NoRightException {
		if (basicManagePriv.getCourseType == 1) {
			return new OracleCourseType(tid);
		} else {
			throw new NoRightException("��û�в鿴�γ����͵�Ȩ�ޣ�");
		}
	}

	public int deleteCourseType(String tid) throws NoRightException,
			PlatformException {
		if (basicManagePriv.deleteCourseType == 1) {
			int suc = new OracleCourseType(tid).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteCourseType</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("��û��ɾ��γ����͵�Ȩ�ޣ�");
		}
	}

	public int addCourseType(String id, String name) throws NoRightException,
			PlatformException {
		if (basicManagePriv.addCourseType == 1) {
			OracleCourseType coursetpye = new OracleCourseType();
			coursetpye.setId(id);
			coursetpye.setName(name);
			int suc = coursetpye.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addCourseType</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("��û����ӿγ����͵�Ȩ�ޣ�");
		}
	}

	public int updateCourseType(String id, String name) throws NoRightException {
		if (basicManagePriv.updateCourseType == 1) {
			OracleCourseType coursetype = new OracleCourseType();
			coursetype.setId(id);
			coursetype.setName(name);
			int suc = coursetype.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$updateCourseType</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("��û���޸Ŀγ����͵�Ȩ�ޣ�");
		}
	}

	public List getCourseTypes(Page page, List searchproperty,
			List orderproperty) throws NoRightException {
		if (basicManagePriv.getCourseType == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCourseTypes(page, searchproperty,
					orderproperty);
		} else {
			throw new NoRightException("��û�в鿴�γ����͵�Ȩ��");
		}
	}

	public List getCourseTypes(Page page) throws NoRightException {
		if (basicManagePriv.getCourseType == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCourseTypes(page, null, null);
		} else {
			throw new NoRightException("��û�в鿴�γ����͵�Ȩ��");
		}
	}

	public List getCourseTypes() throws NoRightException {
		if (basicManagePriv.getCourseType == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCourseTypes(null, null, null);
		} else {
			throw new NoRightException("��û�в鿴�γ����͵�Ȩ��");
		}
	}

	public int getCourseTypesNum() throws NoRightException {
		if (basicManagePriv.getCourseType == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCourseTypesNum(null);
		} else {
			throw new NoRightException("��û�в鿴�γ����͵�Ȩ��");
		}
	}

	public int getCourseTypesNum(List searchproperty) throws NoRightException {
		if (basicManagePriv.getCourseType == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCourseTypesNum(searchproperty);
		} else {
			throw new NoRightException("��û�в鿴�γ����͵�Ȩ��");
		}
	}

	/***************************************************************************
	 * ���²���ΪSemester���� *
	 **************************************************************************/

	public Semester getSemester(String tid) throws NoRightException {
		if (basicManagePriv.getSemester == 1) {
			return new OracleSemester(tid);
		} else {
			throw new NoRightException("��û�в鿴ѧ�ڵ�Ȩ�ޣ�");
		}
	}

	public int deleteSemester(String tid) throws NoRightException,
			PlatformException {
		if (basicManagePriv.deleteSemester == 1) {
			int suc = new OracleSemester(tid).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteSemester</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("��û��ɾ��ѧ�ڵ�Ȩ�ޣ�");
		}
	}

	public int addSemester(String id, String name, String start_date,
			String end_date, String start_elective, String end_elective)
			throws NoRightException {
		if (basicManagePriv.addSemester == 1) {
			OracleSemester semester = new OracleSemester();
			semester.setId(id);
			semester.setName(name);
			semester.setStart_date(start_date);
			semester.setStart_elective(start_elective);
			semester.setEnd_date(end_date);
			semester.setEnd_elective(end_elective);
			int suc = semester.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addSemester</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("��û�����ѧ�ڵ�Ȩ�ޣ�");
		}
	}

	public int updateSemester(String id, String name, String start_date,
			String end_date, String start_elective, String end_elective)
			throws NoRightException {
		if (basicManagePriv.updateSemester == 1) {
			OracleSemester semester = new OracleSemester();
			semester.setId(id);
			semester.setName(name);
			semester.setStart_date(start_date);
			semester.setStart_elective(start_elective);
			semester.setEnd_date(end_date);
			semester.setEnd_elective(end_elective);
			int suc = semester.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$updateSemester</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("��û���޸�ѧ�ڵ�Ȩ�ޣ�");
		}
	}

	public List getSemesters(Page page, List searchproperty, List orderproperty)
			throws NoRightException {
		if (basicManagePriv.getSemester == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getSemesters(page, searchproperty,
					orderproperty);
		} else {
			throw new NoRightException("��û�в鿴ѧ�ڵ�Ȩ��");
		}
	}

	public List getSemesters(Page page) throws NoRightException {
		if (basicManagePriv.getSemester == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getSemesters(page, null, null);
		} else {
			throw new NoRightException("��û�в鿴ѧ�ڵ�Ȩ��");
		}
	}

	public List getSemesters() throws NoRightException {
		if (basicManagePriv.getSemester == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getSemesters(null, null, null);
		} else {
			throw new NoRightException("��û�в鿴ѧ�ڵ�Ȩ��");
		}
	}

	public int getSemestersNum() throws NoRightException {
		if (basicManagePriv.getSemester == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getSemestersNum(null);
		} else {
			throw new NoRightException("��û�в鿴ѧ�ڵ�Ȩ��");
		}
	}

	public int getSemestersNum(List searchproperty) throws NoRightException {
		if (basicManagePriv.getSemester == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getSemestersNum(searchproperty);
		} else {
			throw new NoRightException("��û�в鿴ѧ�ڵ�Ȩ��");
		}
	}

	public int setCourseMajor(String course_id, String[] major_id,
			String[] edu_type) throws NoRightException {
		dbpool dbCourse = new dbpool();
		if (basicManagePriv.setCourseMajor == 1) {
			int length = 0;
			if (major_id != null && edu_type != null) {
				length = major_id.length;
			}
			String sql = "delete lrn_course_major where course_id='"
					+ course_id + "'";
			int suc = dbCourse.executeUpdate(sql);
			for (int i = 0; i < length; i++) {
				sql = "insert into lrn_course_major (id,course_id,major_id,edu_type_id) values (to_char(s_lrn_course_major),'"
						+ course_id
						+ "','"
						+ major_id[i]
						+ "','"
						+ edu_type[i]
						+ "')";
				suc = dbCourse.executeUpdate(sql);
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$setCourseMajor</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("��û�����ÿγ�רҵ��Ȩ��");
		}
	}

	public List getCourseMajor(String course_id) throws NoRightException {
		dbpool dbcourse = new dbpool();
		MyResultSet rs = null;
		if (basicManagePriv.getCourseMajor == 1) {
			ArrayList Majors = new ArrayList();
			String sql = "select major_id,edu_type_id from lrn_course_major where course_id='"
					+ course_id + "'";
			try {
				rs = dbcourse.executeQuery(sql);
				while (rs != null && rs.next()) {
					String[] Major = { null, null };
					Major[0] = rs.getString("major_id");
					Major[1] = rs.getString("edu_type_id");
				}
			} catch (java.sql.SQLException e) {
				Log.setError("OracleBasicEduManager.getCourseMajor() error "
						+ sql);
			} finally {
				dbcourse.close(rs);
				dbcourse = null;
			}
			return Majors;
		} else {
			throw new NoRightException("��û�в鿴�γ�����רҵ��Ȩ��");
		}
	}

	public int delCourseMajor(String course_id, String[] major_id,
			String[] edu_type) throws NoRightException {
		dbpool dbCourse = new dbpool();
		if (basicManagePriv.setCourseMajor == 1) {
			int length = 0;
			if (major_id != null && edu_type != null) {
				length = major_id.length;
			}
			String sql = "delete lrn_course_major where course_id='"
					+ course_id + "'";
			int suc = dbCourse.executeUpdate(sql);
			for (int i = 0; i < length; i++) {
				sql = "insert into lrn_course_major (id,course_id,major_id,edu_type_id) values (to_char(s_lrn_course_major),'"
						+ course_id
						+ "','"
						+ major_id[i]
						+ "','"
						+ edu_type[i]
						+ "')";
				suc = dbCourse.executeUpdate(sql);
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$delCourseMajor</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("��û�����ÿγ�רҵ��Ȩ��");
		}
	}

	public void elective(String Student_id, String[] OpenCourseId)
			throws NoRightException {
		dbpool db = new dbpool();
		Hashtable sqlgroup = new Hashtable();
		String sql = "";
		for (int i = 0; i < OpenCourseId.length; i++) {
			sql = "insert into lrn_elective (id,student_id,open_course_id,status) values (to_char(s_lrn_elective_id.nextval),'"
					+ Student_id + "','" + OpenCourseId[i] + "','1'";
			sqlgroup.put(String.valueOf(i), sql);
		}
		db.executeUpdateBatch(sqlgroup);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$elective</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.SITEMANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
	}

	public List getCourses(Page page, String search_type, String search_value,
			String major_id) throws PlatformException {
		if (basicManagePriv.getCourse == 1) {
			List searchProperties = new ArrayList();
			if (search_value != null && !search_value.equals("")) {
				searchProperties.add(new SearchProperty(search_type,
						search_value, "like"));
			}
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null") && !major_id.equals("all")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCourses(page, searchProperties, null);
		} else {
			throw new PlatformException("��û����?γ���Ϣ��Ȩ��");
		}
	}

	public int getCoursesNum(String search_type, String search_value,
			String major_id) throws PlatformException {
		if (basicManagePriv.getCourse == 1) {
			List searchProperties = new ArrayList();
			if (search_value != null && !search_value.equals("")) {
				searchProperties.add(new SearchProperty(search_type,
						search_value, "like"));
			}
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null") && !major_id.equals("all")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCoursesNum(searchProperties);
		} else {
			throw new PlatformException("��û����?γ���Ϣ��Ȩ��");
		}
	}
}
