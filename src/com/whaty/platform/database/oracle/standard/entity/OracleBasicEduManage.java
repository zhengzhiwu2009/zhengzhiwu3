package com.whaty.platform.database.oracle.standard.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.courseware.CoursewareFactory;
import com.whaty.platform.courseware.CoursewareManage;
import com.whaty.platform.courseware.CoursewareManagerPriv;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleTeachClass;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleBasicEduList;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourse;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourseType;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleSemester;
import com.whaty.platform.entity.BasicEduManage;
import com.whaty.platform.entity.basic.Course;
import com.whaty.platform.entity.basic.CourseType;
import com.whaty.platform.entity.basic.Semester;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserLog;
import com.whaty.platform.vote.VoteFactory;
import com.whaty.platform.vote.VoteManage;
import com.whaty.platform.vote.user.VoteManagerPriv;

public class OracleBasicEduManage extends BasicEduManage {

	ManagerPriv basicManagePriv;

	public OracleBasicEduManage(ManagerPriv amanagerpriv) {
		this.basicManagePriv = amanagerpriv;
	}

	/***************************************************************************
	 * ���²���ΪCourse���� *
	 **************************************************************************/

	public Course getCourse(String tid) throws PlatformException {
		if (basicManagePriv.getCourse == 1) {
			return new OracleCourse(tid);
		} else {
			throw new PlatformException("��û�в鿴�γ���Ϣ��Ȩ�ޣ�");
		}
	}

	public int deleteCourse(String tid) throws PlatformException {
		if (basicManagePriv.deleteCourse == 1) {
			int suc = new OracleCourse(tid).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteCourse</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û��ɾ��γ���Ϣ��Ȩ�ޣ�");
		}
	}

	public int addCourse(String id, String name, String credit,
			String course_time, String major_id, String exam_type,
			String course_type, String teaching_type, String course_status,
			String ref_book, String note, String standard_fee,
			String drift_fee, String text_book, String text_book_price,
			String redundance0, String redundance1, String redundance2,
			String redundance3, String redundance4) throws PlatformException {
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addcourse</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û����ӿγ���Ϣ��Ȩ�ޣ�");
		}
	}

	public int updateCourse(String id, String name, String credit,
			String course_time, String major_id, String exam_type,
			String course_type, String teaching_type, String course_status,
			String ref_book, String note, String standard_fee,
			String drift_fee, String text_book, String text_book_price,
			String redundance0, String redundance1, String redundance2,
			String redundance3, String redundance4) throws PlatformException {
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateCourse</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û���޸Ŀγ���Ϣ��Ȩ�ޣ�");
		}
	}

	public List getCourses(Page page, List searchproperty, List orderproperty)
			throws PlatformException {
		if (basicManagePriv.getCourse == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCourses(page, searchproperty, orderproperty);
		} else {
			throw new PlatformException("��û�в鿴�γ���Ϣ��Ȩ��");
		}
	}

	public List getCourses(Page page) throws PlatformException {
		if (basicManagePriv.getCourse == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCourses(page, null, null);
		} else {
			throw new PlatformException("��û�в鿴�γ���Ϣ��Ȩ��");
		}
	}

	public List getCourses() throws PlatformException {
		if (basicManagePriv.getCourse == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCourses(null, null, null);
		} else {
			throw new PlatformException("��û�в鿴�γ���Ϣ��Ȩ��");
		}
	}

	public int getCoursesNum() throws PlatformException {
		if (basicManagePriv.getCourse == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCoursesNum(null);
		} else {
			throw new PlatformException("��û�в鿴�γ���Ϣ��Ȩ��");
		}
	}

	public int getCoursesNum(List searchproperty) throws PlatformException {
		if (basicManagePriv.getCourse == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCoursesNum(searchproperty);
		} else {
			throw new PlatformException("��û�в鿴�γ���Ϣ��Ȩ��");
		}
	}

	/***************************************************************************
	 * ���²���ΪCourseType���� *
	 **************************************************************************/

	public CourseType getCourseType(String tid) throws PlatformException {
		if (basicManagePriv.getCourseType == 1) {
			return new OracleCourseType(tid);
		} else {
			throw new PlatformException("��û�в鿴�γ����͵�Ȩ�ޣ�");
		}
	}

	public int deleteCourseType(String tid) throws PlatformException {
		if (basicManagePriv.deleteCourseType == 1) {
			int suc = new OracleCourseType(tid).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteCourseType</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û��ɾ��γ����͵�Ȩ�ޣ�");
		}
	}

	public int addCourseType(String id, String name) throws PlatformException {
		if (basicManagePriv.addCourseType == 1) {
			OracleCourseType coursetpye = new OracleCourseType();
			coursetpye.setId(id);
			coursetpye.setName(name);
			int suc = coursetpye.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addCourseType</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û����ӿγ����͵�Ȩ�ޣ�");
		}
	}

	public int updateCourseType(String id, String name)
			throws PlatformException {
		if (basicManagePriv.updateCourseType == 1) {
			OracleCourseType coursetype = new OracleCourseType();
			coursetype.setId(id);
			coursetype.setName(name);
			int suc = coursetype.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateCourseType</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û���޸Ŀγ����͵�Ȩ�ޣ�");
		}
	}

	public List getCourseTypes(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		if (basicManagePriv.getCourseType == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCourseTypes(page, searchproperty,
					orderproperty);
		} else {
			throw new PlatformException("��û�в鿴�γ����͵�Ȩ��");
		}
	}

	public List getCourseTypes(Page page) throws PlatformException {
		if (basicManagePriv.getCourseType == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCourseTypes(page, null, null);
		} else {
			throw new PlatformException("��û�в鿴�γ����͵�Ȩ��");
		}
	}

	public List getCourseTypes() throws PlatformException {
		if (basicManagePriv.getCourseType == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCourseTypes(null, null, null);
		} else {
			throw new PlatformException("��û�в鿴�γ����͵�Ȩ��");
		}
	}

	public int getCourseTypesNum() throws PlatformException {
		if (basicManagePriv.getCourseType == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCourseTypesNum(null);
		} else {
			throw new PlatformException("��û�в鿴�γ����͵�Ȩ��");
		}
	}

	public int getCourseTypesNum(List searchproperty) throws PlatformException {
		if (basicManagePriv.getCourseType == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCourseTypesNum(searchproperty);
		} else {
			throw new PlatformException("��û�в鿴�γ����͵�Ȩ��");
		}
	}

	/***************************************************************************
	 * ���²���ΪSemester���� *
	 **************************************************************************/

	public Semester getSemester(String tid) throws PlatformException {
		if (basicManagePriv.getSemester == 1) {
			return new OracleSemester(tid);
		} else {
			throw new PlatformException("��û�в鿴ѧ�ڵ�Ȩ�ޣ�");
		}
	}

	public int deleteSemester(String tid) throws PlatformException {
		if (basicManagePriv.deleteSemester == 1) {
			int suc = new OracleSemester(tid).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteSemester</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û��ɾ��ѧ�ڵ�Ȩ�ޣ�");
		}
	}

	public int addSemester(String id, String name, String start_date,
			String end_date, String start_elective, String end_elective)
			throws PlatformException {
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addSemester</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û�����ѧ�ڵ�Ȩ�ޣ�");
		}
	}

	public int updateSemester(String id, String name, String start_date,
			String end_date, String start_elective, String end_elective)
			throws PlatformException {
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateSemester</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û���޸�ѧ�ڵ�Ȩ�ޣ�");
		}
	}

	public List getSemesters(Page page, List searchproperty, List orderproperty)
			throws PlatformException {
		if (basicManagePriv.getSemester == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getSemesters(page, searchproperty,
					orderproperty);
		} else {
			throw new PlatformException("��û�в鿴ѧ�ڵ�Ȩ��");
		}
	}

	public List getSemesters(Page page) throws PlatformException {
		if (basicManagePriv.getSemester == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getSemesters(page, null, null);
		} else {
			throw new PlatformException("��û�в鿴ѧ�ڵ�Ȩ��");
		}
	}

	public List getSemesters() throws PlatformException {
		if (basicManagePriv.getSemester == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getSemesters(null, null, null);
		} else {
			throw new PlatformException("��û�в鿴ѧ�ڵ�Ȩ��");
		}
	}

	public List getActiveSemesters() throws PlatformException {
		if (basicManagePriv.getSemester == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			List searchList = new ArrayList();
			searchList.add(new SearchProperty("selected", "1", "="));
			return basicedulist.getSemesters(null, searchList, null);
		} else {
			throw new PlatformException("��û�в鿴ѧ�ڵ�Ȩ��");
		}
	}

	public int getSemestersNum() throws PlatformException {
		if (basicManagePriv.getSemester == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getSemestersNum(null);
		} else {
			throw new PlatformException("��û�в鿴ѧ�ڵ�Ȩ��");
		}
	}

	public int getSemestersNum(List searchproperty) throws PlatformException {
		if (basicManagePriv.getSemester == 1) {
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getSemestersNum(searchproperty);
		} else {
			throw new PlatformException("��û�в鿴ѧ�ڵ�Ȩ��");
		}
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

			searchProperties.add(new SearchProperty("major_id", basicManagePriv
					.getMajorList(), "in"));

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
			searchProperties.add(new SearchProperty("major_id", basicManagePriv
					.getMajorList(), "in"));

			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCoursesNum(searchProperties);
		} else {
			throw new PlatformException("��û����?γ���Ϣ��Ȩ��");
		}
	}

	public int getNoSelCourseNum(String selCourseId, String coursename,
			String courseno, String major_id) throws PlatformException {
		if (basicManagePriv.appointCourseForTeachbook == 1) {
			List searchProperties = new ArrayList();
			if (selCourseId != null && !selCourseId.equals("'")) {
				searchProperties.add(new SearchProperty("id", selCourseId,
						"notIn"));
			}
			if (coursename != null && !coursename.trim().equals("")
					&& !coursename.equals("null")) {
				searchProperties.add(new SearchProperty("name", coursename,
						"like"));
			}
			if (courseno != null && !courseno.trim().equals("")
					&& !coursename.equals("null")) {
				searchProperties
						.add(new SearchProperty("id", courseno, "like"));
			}
			if (major_id != null && !major_id.trim().equals("")
					&& !major_id.equals("null")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCoursesNum(searchProperties);
		} else {
			throw new PlatformException("��û�и�̲�ָ���γ̵�Ȩ��");
		}
	}

	public List getNoSelCourse(Page page, String selCourseId,
			String coursename, String courseno, String major_id)
			throws PlatformException {
		if (basicManagePriv.appointCourseForTeachbook == 1) {
			List searchProperties = new ArrayList();
			if (selCourseId != null && !selCourseId.equals("'")) {
				searchProperties.add(new SearchProperty("id", selCourseId,
						"notIn"));
			}
			if (coursename != null && !coursename.trim().equals("")
					&& !coursename.equals("null")) {
				searchProperties.add(new SearchProperty("name", coursename,
						"like"));
			}
			if (courseno != null && !courseno.trim().equals("")
					&& !coursename.equals("null")) {
				searchProperties
						.add(new SearchProperty("id", courseno, "like"));
			}
			if (major_id != null && !major_id.trim().equals("")
					&& !major_id.equals("null")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}
			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCourses(page, searchProperties, null);
		} else {
			throw new PlatformException("��û�и�̲�ָ���γ̵�Ȩ��");
		}
	}

	public int getListCoursesNum(String coursename, String courseno,
			String major_id) throws PlatformException {
		if (basicManagePriv.getCourse == 1) {
			List searchProperties = new ArrayList();
			if (coursename != null && !coursename.trim().equals("")) {
				searchProperties.add(new SearchProperty("name", coursename,
						"like"));
			}
			if (courseno != null && !courseno.trim().equals("")) {
				searchProperties
						.add(new SearchProperty("id", courseno, "like"));
			}
			if (major_id != null && !major_id.trim().equals("")
					&& !major_id.equals("null")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}
			searchProperties.add(new SearchProperty("major_id", basicManagePriv
					.getMajorList(), "in"));

			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCoursesNum(searchProperties);
		} else {
			throw new PlatformException("��û����?γ���Ϣ��Ȩ��");
		}
	}

	public List getListCourses(Page page, String coursename, String courseno,
			String major_id) throws PlatformException {
		if (basicManagePriv.getCourse == 1) {
			List searchProperties = new ArrayList();
			if (coursename != null && !coursename.equals("")) {
				searchProperties.add(new SearchProperty("name", coursename,
						"like"));
			}
			if (courseno != null && !courseno.equals("")) {
				searchProperties
						.add(new SearchProperty("id", courseno, "like"));
			}
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}
			searchProperties.add(new SearchProperty("major_id", basicManagePriv
					.getMajorList(), "in"));

			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCourses(page, searchProperties, null);
		} else {
			throw new PlatformException("��û����?γ���Ϣ��Ȩ��");
		}
	}

	public List getCourses(Page page, String courseId, String courseName,
			String majorId, String credit, String courseTime)
			throws PlatformException {
		if (basicManagePriv.getCourse == 1) {
			List searchProperties = new ArrayList();
			if (courseId != null && !courseId.equals("")) {
				searchProperties
						.add(new SearchProperty("id", courseId, "like"));
			}
			if (courseName != null && !courseName.equals("")) {
				searchProperties.add(new SearchProperty("name", courseName,
						"like"));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperties.add(new SearchProperty("major_id", majorId,
						"="));
			}
			if (credit != null && !credit.equals("")) {
				searchProperties.add(new SearchProperty("credit", credit, "="));
			}
			if (courseTime != null && !courseTime.equals("")) {
				searchProperties.add(new SearchProperty("course_time",
						courseTime, "="));
			}
			searchProperties.add(new SearchProperty("major_id", basicManagePriv
					.getMajorList(), "in"));

			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCourses(page, searchProperties, null);
		} else {
			throw new PlatformException("��û����?γ���Ϣ��Ȩ��");
		}
	}

	public int getCoursesNum(String courseId, String courseName,
			String majorId, String credit, String courseTime)
			throws PlatformException {
		if (basicManagePriv.getCourse == 1) {
			List searchProperties = new ArrayList();
			if (courseId != null && !courseId.equals("")) {
				searchProperties
						.add(new SearchProperty("id", courseId, "like"));
			}
			if (courseName != null && !courseName.equals("")) {
				searchProperties.add(new SearchProperty("name", courseName,
						"like"));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperties.add(new SearchProperty("major_id", majorId,
						"="));
			}
			if (credit != null && !credit.equals("")) {
				searchProperties.add(new SearchProperty("credit", credit, "="));
			}
			if (courseTime != null && !courseTime.equals("")) {
				searchProperties.add(new SearchProperty("course_time",
						courseTime, "="));
			}
			searchProperties.add(new SearchProperty("major_id", basicManagePriv
					.getMajorList(), "in"));

			OracleBasicEduList basicedulist = new OracleBasicEduList();
			return basicedulist.getCoursesNum(searchProperties);
		} else {
			throw new PlatformException("��û����?γ���Ϣ��Ȩ��");
		}
	}

	public Course createCourse() throws PlatformException {
		return new OracleCourse();
	}

	public CoursewareManage creatCoursewareManage() {
		CoursewareFactory coursewareFactory = CoursewareFactory.getInstance();
		CoursewareManagerPriv priv = coursewareFactory
				.getCoursewareManagerPriv("");
		priv.setMessageId(basicManagePriv.getSso_id());
		priv.addChapterPage = 1;
		priv.addOnlineCwInfo = 1;
		priv.addWhatyCoursewareTemplate = 1;
		priv.buildNormalHttpCourseware = 1;
		priv.buildWhatyOnlineCourseware = 1;
		priv.buildWhatyUploadCourseware = 1;
		priv.deleteOnlineCwInfo = 1;
		priv.deleteWhatyCoursewareTemplate = 1;
		priv.filemanage = 1;
		priv.getCourseware = 1;
		priv.getOnlineCwInfo = 1;
		priv.getCoursewareDir=1;
		priv.getOnlineCwRootDir = 1;
		priv.getWhatyCoursewareTemplate = 1;
		priv.updateNormalHttpLink = 1;
		priv.updateUploadEnterFile = 1;
		priv.updateWhatyCoursewareTemplate = 1;
		return coursewareFactory.creatCoursewareManage(priv);
	}

	public CoursewareManagerPriv getCoursewareManagerPriv() {
		CoursewareFactory coursewareFactory = CoursewareFactory.getInstance();
		CoursewareManagerPriv priv = coursewareFactory
				.getCoursewareManagerPriv("");
		priv.setMessageId(basicManagePriv.getSso_id());
		priv.addChapterPage = 1;
		priv.addOnlineCwInfo = 1;
		priv.addWhatyCoursewareTemplate = 1;
		priv.buildNormalHttpCourseware = 1;
		priv.buildWhatyOnlineCourseware = 1;
		priv.buildWhatyUploadCourseware = 1;
		priv.deleteOnlineCwInfo = 1;
		priv.deleteWhatyCoursewareTemplate = 1;
		priv.filemanage = 1;
		priv.getCourseware = 1;
		priv.getOnlineCwInfo = 1;
		priv.getOnlineCwRootDir = 1;
		priv.getWhatyCoursewareTemplate = 1;
		priv.updateNormalHttpLink = 1;
		priv.updateUploadEnterFile = 1;
		priv.updateWhatyCoursewareTemplate = 1;
		return priv;
	}

	public VoteManage creatVoteManage() {
		VoteFactory voteFactory = VoteFactory.getInstance();
		VoteManagerPriv priv = voteFactory.getVoteManagerPriv("");
		return voteFactory.creatVoteManage(priv);
	}

	public VoteManagerPriv getVoteManagerPriv() {
		VoteFactory voteFactory = VoteFactory.getInstance();
		VoteManagerPriv priv = voteFactory.getVoteManagerPriv("");
		return priv;
	}

	public List getCoursewares(String teachclass_id) throws PlatformException {
		if (basicManagePriv.getCourseware == 1) {
			OracleTeachClass teachclass = new OracleTeachClass();
			teachclass.setId(teachclass_id);
			return teachclass.getCoursewares();
		} else {
			throw new PlatformException("��û����?γ̿μ���Ȩ��");
		}
	}

	public int setCourseMajor(String course_id, String[] major_id,
			String[] edu_type) throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List getCourseMajor(String course_id) throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	public void elective(String Student_id, String[] OpenCourseId)
			throws PlatformException {
		// TODO Auto-generated method stub

	}

}
