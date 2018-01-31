/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.CampusFactory;
import com.whaty.platform.campus.CampusManage;
import com.whaty.platform.campus.CampusNormalManage;
import com.whaty.platform.campus.base.AssociationMember;
import com.whaty.platform.campus.base.ClassMember;
import com.whaty.platform.campus.user.CampusManagerPriv;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.campus.base.OracleAssociationMemberList;
import com.whaty.platform.database.oracle.standard.campus.base.OracleClassMemberList;
import com.whaty.platform.database.oracle.standard.campus.user.OracleCampusManagerPriv;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleBasicActivityList;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleChangeMajorApply;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleElective;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleElectiveActivity;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleOpenCourse;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleTeachClass;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleBasicEduList;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleBasicEntityList;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourse;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleExecutePlan;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleSemester;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleTeachPlan;
import com.whaty.platform.database.oracle.standard.entity.fee.deal.OracleDealWithFee;
import com.whaty.platform.database.oracle.standard.entity.fee.deal.OracleFeeList;
import com.whaty.platform.database.oracle.standard.entity.fee.deal.OracleUserFeeReturnApply;
import com.whaty.platform.database.oracle.standard.entity.fee.exception.FeeException;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleBasicGraduateList;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleDiscourse;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleFreeApply;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleGraduateExecPici;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleGraduatePici;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleMetaphaseCheck;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleRegBgCourse;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleRejoinRequisition;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleSubjectQuestionary;
import com.whaty.platform.database.oracle.standard.entity.user.OracleBasicUserList;
import com.whaty.platform.database.oracle.standard.entity.user.OracleManagerPriv;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteTeacherLimit;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudentPriv;
import com.whaty.platform.database.oracle.standard.info.OracleInfoList;
import com.whaty.platform.database.oracle.standard.interaction.OracleInteractionUserPriv;
import com.whaty.platform.database.oracle.standard.sso.OracleSsoUser;
import com.whaty.platform.database.oracle.standard.sso.OracleSsoUserAuthorization;
import com.whaty.platform.database.oracle.standard.test.exam.OracleExamList;
import com.whaty.platform.entity.BasicScoreManage;
import com.whaty.platform.entity.StudentOperationManage;
import com.whaty.platform.entity.activity.BasicActivityList;
import com.whaty.platform.entity.activity.ChangeMajorApply;
import com.whaty.platform.entity.activity.ElectiveActivity;
import com.whaty.platform.entity.activity.OpenCourse;
import com.whaty.platform.entity.basic.ExecutePlan;
import com.whaty.platform.entity.graduatedesign.Discourse;
import com.whaty.platform.entity.graduatedesign.FreeApply;
import com.whaty.platform.entity.graduatedesign.MetaphaseCheck;
import com.whaty.platform.entity.graduatedesign.Pici;
import com.whaty.platform.entity.graduatedesign.RegBgCourse;
import com.whaty.platform.entity.graduatedesign.RejoinRequisition;
import com.whaty.platform.entity.graduatedesign.SubjectQuestionary;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.SiteTeacherLimit;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.entity.user.StudentPriv;
import com.whaty.platform.info.News;
import com.whaty.platform.interaction.InteractionUserPriv;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.SsoLog;
import com.whaty.platform.util.log.UserLog;
import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.string.StrManage;
import com.whaty.util.string.StrManageFactory;

/**
 * @author Administrator
 * 
 */
public class OracleStudentOperationManage extends StudentOperationManage {

	public StudentPriv studentPriv;

	public OracleStudentOperationManage() {
		this.studentPriv = new OracleStudentPriv("studentPriv");
	}

	public OracleStudentOperationManage(StudentPriv studentPriv) {
		this.studentPriv = studentPriv;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.StudentOperationManage#getStudent()
	 */
	public Student getStudent() throws PlatformException {
		if (studentPriv.getStudent != 1) {
			throw new PlatformException("no right to getStudent!");
		} else {
			return new OracleStudent(studentPriv.getStudentId());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.StudentOperationManage#getSelectTeachClass()
	 */
	public List getSelectdTeachClasses() throws PlatformException {
		if (studentPriv.getSelectTeachClasses != 1) {
			throw new PlatformException("no right to getSelectTeachClasses!");
		} else {
			OracleBasicActivityList basicActivity = new OracleBasicActivityList();
			Student student = new OracleStudent();
			student.setId(this.studentPriv.getStudentId());
			return basicActivity.searchElective(null, null, null, student);
		}
	}

	public List getCourses(Page page, String id) throws PlatformException {
		// TODO �Զ���ɷ������
		if (studentPriv.getCourses != 1) {
			throw new PlatformException("no right to getCourse!");
		} else {
			OracleBasicEduList basicEduList = new OracleBasicEduList();
			List searchproperty = new ArrayList();
			List orderproperty = new ArrayList();
			if (null != id && !id.equals("") && !id.equals("null")) {
				searchproperty.add(new SearchProperty("id", id, "="));
				orderproperty.add(new OrderProperty("id"));
			}
			return basicEduList.getCourses(page, searchproperty, orderproperty);
		}
	}

	public int getCoursesNum() throws PlatformException {
		// TODO �Զ���ɷ������
		if (studentPriv.getCourses != 1) {
			throw new PlatformException("no right to getCourse!");
		} else {
			OracleBasicEduList basicEduList = new OracleBasicEduList();
			return basicEduList.getCoursesNum(null);
		}
	}

	public List getElectives(Page page, Student student)
			throws PlatformException {
		// TODO �Զ���ɷ������
		if (studentPriv.getCourses != 1) {
			throw new PlatformException("no right to getCourse!");
		} else {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			ArrayList orderProperties = new ArrayList();
			orderProperties.add(new OrderProperty("semester_id", "1"));
			orderProperties.add(new OrderProperty("course_id"));
			return basicActivityList.searchElective(page, null,
					orderProperties, student);
		}
	}

	public List getElectives(Page page, Student student,String elective_id)
			throws PlatformException {
		// TODO �Զ���ɷ������
		if (studentPriv.getCourses != 1) {
			throw new PlatformException("no right to getCourse!");
		} else {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			List searchList = new ArrayList();
			searchList.add(new SearchProperty("id",elective_id,"="));
			ArrayList orderProperties = new ArrayList();
			orderProperties.add(new OrderProperty("semester_id", "1"));
			orderProperties.add(new OrderProperty("course_id"));
			return basicActivityList.searchElective(page, searchList,
					orderProperties, student);
		}
	}

	public String getElectiveTimes(String courseId, String student_id)
			throws PlatformException {
		OracleElective elective = new OracleElective();
		return elective.getElectiveTimes(courseId, student_id);
	}
	
	/**
	 * 判断课程是否完成学习
	 */
	public boolean isLearningCompleted(String courseId,String student_id) throws PlatformException{
		OracleElective elective = new OracleElective();
		return elective.isLearningCompleted(courseId, student_id);
	}
	
	/**
	 * 判断课程是否完成满意度调查
	 */
	public boolean isManyiCompleted(String courseId,String student_id) throws PlatformException{
		OracleElective elective = new OracleElective();
		return elective.isManyiCompleted(courseId, student_id);
	}

	public int setElectiveTimes(String courseId, String student_id,
			String time) throws PlatformException {
		OracleElective elective = new OracleElective();
		return elective.setElectiveTimes(courseId, student_id, time);
	}

	public int getElectivesNum(Student student) throws PlatformException {
		// TODO �Զ���ɷ������
		if (studentPriv.getCourses != 1) {
			throw new PlatformException("no right to getCourse!");
		} else {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.searchElectiveNum(null, student);
		}
	}

	public List getElectives(Page page, String course_id, String course_name,
			String semester_id, Student student, boolean confirmStatus)
			throws PlatformException {
		if (studentPriv.getCourses != 1) {
			throw new PlatformException("no right to getCourse!");
		} else {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			ArrayList searchProperty = new ArrayList();
			ArrayList orderProperty = new ArrayList();
			if (course_id != null && !course_id.equals("")) {
				searchProperty.add(new SearchProperty("course_id", course_id));
			}
			if (course_name != null && !course_name.equals("")) {
				searchProperty.add(new SearchProperty("course_name",
						course_name));
			}
			if (semester_id != null && !semester_id.equals("")) {
				searchProperty.add(new SearchProperty("semester_id",
						semester_id));
			}
			orderProperty.add(new OrderProperty("semester_id",
					OrderProperty.DESC));
			orderProperty.add(new OrderProperty("course_id"));
			return basicActivityList.searchElective(page, searchProperty,
					orderProperty, student, confirmStatus);
		}
	}

	public int getElectivesNum(String course_id, String course_name,
			String semester_id, Student student, boolean confirmStatus)
			throws PlatformException {
		if (studentPriv.getCourses != 1) {
			throw new PlatformException("no right to getCourse!");
		} else {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			ArrayList searchProperty = new ArrayList();
			if (course_id != null && !course_id.equals("")) {
				searchProperty.add(new SearchProperty("course_id", course_id));
			}
			if (course_name != null && !course_name.equals("")) {
				searchProperty.add(new SearchProperty("course_name",
						course_name));
			}
			if (semester_id != null && !semester_id.equals("")) {
				searchProperty.add(new SearchProperty("semester_id",
						semester_id));
			}
			return basicActivityList.searchElectiveNum(searchProperty, student,
					confirmStatus);
		}
	}

	public List getUnConfirmEleCourses(Page page, String studentId,
			String semesterId, String course_id, String course_name)
			throws PlatformException {
		if (studentPriv.getOpenCourses == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			ArrayList searchProperty = new ArrayList();
			ArrayList orderProperty = new ArrayList();
			searchProperty.add(new SearchProperty("course_id", course_id));
			searchProperty.add(new SearchProperty("name", course_name));
			orderProperty.add(new OrderProperty("course_id"));
			return basicActivityList.getUnConfirmEleCourses(page, studentId,
					semesterId, searchProperty, orderProperty);
		} else {
			throw new PlatformException("��û��ѡ�ε�Ȩ�ޣ�");
		}
	}

	public int updatePwd(String id, String oldPassword, String newPassword)
			throws PlatformException {
		if (this.studentPriv.updatePwd != 1) {
			throw new PlatformException("no right to updatePwd!");
		} else {
			OracleSsoUser ssoUser = new OracleSsoUser(id);
			OracleSsoUserAuthorization ssoUserAuthor = new OracleSsoUserAuthorization(
					ssoUser.getLoginId());

			if (oldPassword.equals(ssoUserAuthor.getRightPwd())) {
				ssoUser.setPassword(newPassword);
				int suc = ssoUser.update();
				UserLog
						.setInfo(
								"<whaty>USERID$|$"
										+ this.studentPriv.getStudentId()
										+ "</whaty><whaty>BEHAVIOR$|$updatePwd</whaty><whaty>STATUS$|$"
										+ suc
										+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
										+ LogType.STUDENT
										+ "</whaty><whaty>PRIORITY$|$"
										+ LogPriority.INFO + "</whaty>",
								new Date());
				if (suc > 0) {
					SsoLog.setDebug("updatePwd success!" + id);
					return 1;
				} else {
					throw new PlatformException("updatePwd error!");
				}
			} else {
				throw new PlatformException("ԭ���벻��ȷ!");
			}
		}
	}

	public List getInfoList(Page page) throws PlatformException {
		// TODO �Զ���ɷ������
		if (studentPriv.getNews != 1) {
			throw new PlatformException("no right to getNews!");
		} else {
			OracleInfoList infoList = new OracleInfoList();
			Student student = this.getStudent();
			return infoList.getNewsListForStudent(page, student);
		}
	}

	public int getInfoListNum() throws PlatformException {
		// TODO �Զ���ɷ������
		if (studentPriv.getNews != 1) {
			throw new PlatformException("no right to getNews!");
		} else {
			OracleInfoList infoList = new OracleInfoList();
			Student student = this.getStudent();

			return infoList.getNewsListNumForStudent(student);
		}
	}

	public int updateStudent(String studentid, String emails, String phones,
			String mobilephones, String address, String postalcode, String edu,
			String zzmm) throws PlatformException {
		if (studentPriv.updateStudent == 1) {
			String[] phone = phones.split(",");
			String[] email = emails.split(",");
			String[] mobilephone = mobilephones.split(",");
			OracleStudent oraclestudent = new OracleStudent(studentid);
			oraclestudent.getNormalInfo().setEmail(email);
			oraclestudent.getNormalInfo().setPhone(phone);
			oraclestudent.getNormalInfo().setMobilePhone(mobilephone);
			oraclestudent.getNormalInfo().getHome_address().setAddress(address);
			oraclestudent.getNormalInfo().getHome_address().setPostalcode(
					postalcode);
			oraclestudent.getNormalInfo().setZzmm(zzmm);
			oraclestudent.getNormalInfo().setEdu(edu);
			int suc = oraclestudent.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.studentPriv.getStudentId()
									+ "</whaty><whaty>BEHAVIOR$|$updateStudent</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.STUDENT
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û���޸�ѧ����Ϣ��Ȩ��");
		}
	}

	public News getNews(String news_id) throws PlatformException {
		// TODO �Զ���ɷ������
		if (studentPriv.getNews != 1) {
			throw new PlatformException("no right to getNews!");
		} else {
			OracleInfoList infoList = new OracleInfoList();
			List searchPropertys = new ArrayList();
			searchPropertys.add(new SearchProperty("id", news_id, "="));

			return (News) infoList.getNewsList(null, searchPropertys, null)
					.get(0);
		}
	}

	public List getRegSemester(Page page) throws PlatformException {
		// TODO �Զ���ɷ������
		if (studentPriv.getSemesters != 1) {
			throw new PlatformException("no right to getNews!");
		} else {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();

			return basicActivityList.searchRegSemester(page, this.getStudent());
		}
	}

	public int getRegSemesterNum() throws PlatformException {
		// TODO �Զ���ɷ������
		if (studentPriv.getSemesters != 1) {
			throw new PlatformException("no right to getNews!");
		} else {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();

			return basicActivityList.searchRegSemesterNum(this.getStudent());
		}
	}

	public List getTeachPlanCourses(Page page, String course_id,
			String course_name, String course_type, String plan_credit,
			String plan_time) throws PlatformException {
		if (studentPriv.getTeachPlanCourses != 1) {
			throw new PlatformException("û�л�ȡ��ѧ�ƻ��γ̵�Ȩ��!");
		} else {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			ArrayList searchProperty = new ArrayList();
			ArrayList orderProperty = new ArrayList();
			if (course_id != null && !course_id.equals("")) {
				searchProperty.add(new SearchProperty("course_id", course_id,
						"like"));
			}
			if (course_name != null && !course_name.equals("")) {
				searchProperty.add(new SearchProperty("course_name",
						course_name, "like"));
			}
			if (course_type != null && !course_type.equals("")) {
				searchProperty.add(new SearchProperty("course_type",
						course_type));
			}
			if (plan_credit != null && !plan_credit.equals("")) {
				searchProperty.add(new SearchProperty("credit", plan_credit));
			}
			if (plan_time != null && !plan_time.equals("")) {
				searchProperty
						.add(new SearchProperty("course_time", plan_time));
			}

			orderProperty.add(new OrderProperty("course_id"));
			return basicActivityList.getTeachPlanCourses(page, this
					.getStudent().getStudentInfo().getMajor_id(), this
					.getStudent().getStudentInfo().getEdutype_id(), this
					.getStudent().getStudentInfo().getGrade_id(), this
					.getStudent().getId(), searchProperty, orderProperty);
		}

	}

	public ExecutePlan getExecutePlan(String semesterId, String gradeId,
			String majorId, String eduTypeId) throws PlatformException {
		if (studentPriv.getExecutePlan != 1) {
			throw new PlatformException("û�л�ȡִ�мƻ���Ȩ��!");
		} else {
			OracleTeachPlan oTeachPlan = new OracleTeachPlan(majorId,
					eduTypeId, gradeId);
			return new OracleExecutePlan(semesterId, oTeachPlan.getId());
		}
	}

	public List getOpenCoursesInTeachPlan(Page page, String semesterId,
			String course_id, String course_name, String credit,
			String course_time) throws PlatformException {
		if (studentPriv.getTeachPlanCourses != 1) {
			throw new PlatformException("û�л�ȡ��ѧ�ƻ��γ̵�Ȩ��!");
		} else {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			ArrayList searchProperty = new ArrayList();
			ArrayList orderProperty = new ArrayList();
			if (course_id != null && !course_id.equals("")) {
				searchProperty.add(new SearchProperty("course_id", course_id,
						"like"));
			}
			if (course_name != null && !course_name.equals("")) {
				searchProperty.add(new SearchProperty("course_name",
						course_name, "like"));
			}
			if (credit != null && !credit.equals("")) {
				searchProperty.add(new SearchProperty("credit", credit));
			}
			if (course_time != null && !course_time.equals("")) {
				searchProperty.add(new SearchProperty("course_time",
						course_time));
			}

			orderProperty.add(new OrderProperty("course_id"));

			return basicActivityList.getOpenCoursesInTeachPlan(page,
					semesterId, this.getStudent().getStudentInfo()
							.getGrade_id(), this.getStudent().getStudentInfo()
							.getEdutype_id(), this.getStudent()
							.getStudentInfo().getMajor_id(), this.getStudent()
							.getId(), searchProperty, orderProperty);
		}
	}

	public List getExecutePlanCourseGroupsByExecutePlanId(String executePlanId)
			throws PlatformException {
		if (studentPriv.getExecutePlan == 1) {
			List searchList = new ArrayList();
			OracleBasicActivityList activityList = new OracleBasicActivityList();

			if (executePlanId != null && !"".equals(executePlanId)
					&& !"null".equalsIgnoreCase(executePlanId))
				searchList.add(new SearchProperty("executeplan_id",
						executePlanId, "="));

			return activityList.getExecutePlanCourseGroups(null, searchList,
					null);
		} else {
			throw new PlatformException("��û�в鿴ִ�мƻ��γ����Ȩ��");
		}
	}

	public List getOpenCoursesInExecutePlan(Page page, String semesterId,
			String course_id, String course_name, String credit,
			String course_time) throws PlatformException {
		if (studentPriv.getExecutePlanCourses != 1) {
			throw new PlatformException("û�л�ȡִ�мƻ��γ̵�Ȩ��!");
		} else {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			ArrayList searchProperty = new ArrayList();
			ArrayList orderProperty = new ArrayList();
			if (course_id != null && !course_id.equals("")) {
				searchProperty.add(new SearchProperty("course_id", course_id,
						"like"));
			}
			if (course_name != null && !course_name.equals("")) {
				searchProperty.add(new SearchProperty("course_name",
						course_name, "like"));
			}
			if (credit != null && !credit.equals("")) {
				searchProperty.add(new SearchProperty("credit", credit));
			}
			if (course_time != null && !course_time.equals("")) {
				searchProperty.add(new SearchProperty("course_time",
						course_time));
			}

			orderProperty.add(new OrderProperty("course_type"));
			orderProperty.add(new OrderProperty("course_id"));

			return basicActivityList.getOpenCoursesInExecutePlan(page,
					semesterId, this.getStudent().getStudentInfo()
							.getGrade_id(), this.getStudent().getStudentInfo()
							.getEdutype_id(), this.getStudent()
							.getStudentInfo().getMajor_id(), this.getStudent()
							.getId(), searchProperty, orderProperty);
		}
	}

	public int getOpenCoursesInExecutePlanNum(String semesterId,
			String course_id, String course_name, String credit,
			String course_time) throws PlatformException {
		if (studentPriv.getExecutePlanCourses != 1) {
			throw new PlatformException("û�л�ȡִ�мƻ��γ̵�Ȩ��!");
		} else {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			ArrayList searchProperty = new ArrayList();
			ArrayList orderProperty = new ArrayList();
			if (course_id != null && !course_id.equals("")) {
				searchProperty.add(new SearchProperty("course_id", course_id,
						"like"));
			}
			if (course_name != null && !course_name.equals("")) {
				searchProperty.add(new SearchProperty("course_name",
						course_name, "like"));
			}
			if (credit != null && !credit.equals("")) {
				searchProperty.add(new SearchProperty("credit", credit));
			}
			if (course_time != null && !course_time.equals("")) {
				searchProperty.add(new SearchProperty("course_time",
						course_time));
			}

			orderProperty.add(new OrderProperty("course_id"));

			return basicActivityList.getOpenCoursesInExecutePlanNum(semesterId,
					this.getStudent().getStudentInfo().getGrade_id(), this
							.getStudent().getStudentInfo().getEdutype_id(),
					this.getStudent().getStudentInfo().getMajor_id(), this
							.getStudent().getId(), searchProperty,
					orderProperty);
		}
	}

	public List getOpenCoursesNotInTeachPlan(Page page, String semesterId,
			String course_id, String course_name, String credit,
			String course_time) throws PlatformException {
		if (studentPriv.getTeachPlanCourses != 1) {
			throw new PlatformException("û�л�ȡ��ѧ�ƻ��γ̵�Ȩ��!");
		} else {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			ArrayList searchProperty = new ArrayList();
			ArrayList orderProperty = new ArrayList();
			if (course_id != null && !course_id.equals("")) {
				searchProperty.add(new SearchProperty("course_id", course_id,
						"like"));
			}
			if (course_name != null && !course_name.equals("")) {
				searchProperty.add(new SearchProperty("course_name",
						course_name, "like"));
			}
			if (credit != null && !credit.equals("")) {
				searchProperty.add(new SearchProperty("credit", credit));
			}
			if (course_time != null && !course_time.equals("")) {
				searchProperty.add(new SearchProperty("course_time",
						course_time));
			}

			orderProperty.add(new OrderProperty("course_id"));
			return basicActivityList.getOpenCoursesNotInTeachPlan(page,
					semesterId, this.getStudent().getStudentInfo()
							.getGrade_id(), this.getStudent().getStudentInfo()
							.getEdutype_id(), this.getStudent()
							.getStudentInfo().getMajor_id(), this.getStudent()
							.getId(), searchProperty, orderProperty);
		}
	}

	public List getOpenCoursesNotInExecutePlan(Page page, String semesterId,
			String course_id, String course_name, String credit,
			String course_time) throws PlatformException {
		if (studentPriv.getTeachPlanCourses != 1) {
			throw new PlatformException("û�л�ȡ��ѧ�ƻ��γ̵�Ȩ��!");
		} else {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			ArrayList searchProperty = new ArrayList();
			ArrayList orderProperty = new ArrayList();
			if (course_id != null && !course_id.equals("")) {
				searchProperty.add(new SearchProperty("course_id", course_id,
						"like"));
			}
			if (course_name != null && !course_name.equals("")) {
				searchProperty.add(new SearchProperty("course_name",
						course_name, "like"));
			}
			if (credit != null && !credit.equals("")) {
				searchProperty.add(new SearchProperty("credit", credit));
			}
			if (course_time != null && !course_time.equals("")) {
				searchProperty.add(new SearchProperty("course_time",
						course_time));
			}

			orderProperty.add(new OrderProperty("course_id"));
			return basicActivityList.getOpenCoursesNotInExecutePlan(page,
					semesterId, this.getStudent().getStudentInfo()
							.getGrade_id(), this.getStudent().getStudentInfo()
							.getEdutype_id(), this.getStudent()
							.getStudentInfo().getMajor_id(), this.getStudent()
							.getId(), searchProperty, orderProperty);
		}
	}

	public List getOpenCourses(Page page, String semester_id)
			throws PlatformException {
		// TODO �Զ���ɷ������
		if (studentPriv.getCourses != 1) {
			throw new PlatformException("no right to getNews!");
		} else {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			OracleSemester semester = new OracleSemester(semester_id);

			return basicActivityList.searchOpenCourse(page, null, null,
					semester);
		}
	}

	public int getOpenCoursesNum(String semester_id) throws PlatformException {
		// TODO �Զ���ɷ������
		if (studentPriv.getCourses != 1) {
			throw new PlatformException("no right to getNews!");
		} else {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			OracleSemester semester = new OracleSemester(semester_id);
			return basicActivityList.searchOpenCourseNum(null, semester);
		}
	}

	public int addElectives(String[] opencourse_ids, String semester_id)
			throws PlatformException {
		// TODO �Զ���ɷ������
		if (studentPriv.getCourses != 1) {
			throw new PlatformException("No right to add Electives!");
		} else {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			Student student = this.getStudent();
			int suc = basicActivityList.addElectives(opencourse_ids,
					semester_id, student);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.studentPriv.getStudentId()
									+ "</whaty><whaty>BEHAVIOR$|$addElectives</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.STUDENT
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		}
	}

	/*
	 * (non-Javadoc) ѡ��ִ�мƻ��γ�
	 * 
	 * @see com.whaty.platform.entity.StudentOperationManage#addElectives(javax.servlet.http.HttpServletRequest)
	 */
	public int addElectives(HttpServletRequest request)
			throws PlatformException {
		if (studentPriv.electiveSingle != 1) {
			throw new PlatformException("û��ѡ�ε�Ȩ��!");
		} else {
			String semester_id = request.getParameter("semester_id");
			String[] opencourse_ids = request
					.getParameterValues("opencourse_id");
			String[] major_xuanxiucourse_id = request
					.getParameterValues("major_xuanxiucourse_id");
			String[] pub_xuanxiucourse_id = request
					.getParameterValues("pub_xuanxiucourse_id");
			String[] bixiu_course_id = request
					.getParameterValues("bixiu_course_id");
			String selectStr = "";

			if (major_xuanxiucourse_id != null) {
				for (int i = 0; i < major_xuanxiucourse_id.length; i++) {
					selectStr += major_xuanxiucourse_id[i].substring(0,
							major_xuanxiucourse_id[i].indexOf("|"))
							+ ",";
				}
			}
			if (pub_xuanxiucourse_id != null) {
				for (int i = 0; i < pub_xuanxiucourse_id.length; i++) {
					selectStr += pub_xuanxiucourse_id[i].substring(0,
							pub_xuanxiucourse_id[i].indexOf("|"))
							+ ",";
				}
			}
			if (bixiu_course_id != null) {
				for (int i = 0; i < bixiu_course_id.length; i++) {
					selectStr += bixiu_course_id[i].substring(0,
							bixiu_course_id[i].indexOf("|"))
							+ ",";
				}
			}

			if (selectStr.length() > 0) {
				selectStr = selectStr.substring(0, selectStr.length() - 1);
			}
			String[] select_opencourse_ids = selectStr.split(",");

			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			String studentId = this.getStudent().getId();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.studentPriv.getStudentId()
									+ "</whaty><whaty>BEHAVIOR$|$addElectives</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.STUDENT
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return basicActivityList.addElectivesWithFee(opencourse_ids,
					select_opencourse_ids, semester_id, studentId, false);
		}

	}

	/*
	 * (non-Javadoc) ѡ��ǽ�ѧ�ƻ��γ�
	 * 
	 * @see com.whaty.platform.entity.StudentOperationManage#addNotExecutePlanElectives(javax.servlet.http.HttpServletRequest)
	 */
	public void addNotExecutePlanElectives(HttpServletRequest request)
			throws PlatformException {
		if (studentPriv.electiveSingle != 1) {
			throw new PlatformException("û��ѡ�ε�Ȩ��!");
		} else {
			String semester_id = request.getParameter("semester_id");
			String[] opencourse_ids = request
					.getParameterValues("opencourse_id");
			String[] select_opencourse_ids = request
					.getParameterValues("select_opencourse_id");
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			String studentId = this.getStudent().getId();
			basicActivityList.addElectives(opencourse_ids,
					select_opencourse_ids, semester_id, studentId, true);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.studentPriv.getStudentId()
									+ "</whaty><whaty>BEHAVIOR$|$addNotExecutePlanElectives</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.STUDENT
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		}
	}

	public OpenCourse getOpenCourse(String opend_course_id)
			throws PlatformException {
		if (studentPriv.getOpenCourse != 1) {
			throw new PlatformException("û�л�ȡ������Ϣ��Ȩ�ޣ�");
		} else {
			return new OracleOpenCourse(opend_course_id);
		}
	}

	public InteractionUserPriv getInteractionUserPriv(String user_id)
			throws PlatformException {
		if (studentPriv.addCourseware != 1) {
			throw new PlatformException("û�н���γ̽�����Ȩ�ޣ�");
		} else {
			OracleInteractionUserPriv interactionUserPriv = new OracleInteractionUserPriv(
					user_id);
			return interactionUserPriv;
		}
	}

	public InteractionUserPriv getInteractionUserPriv(String user_id,
			String teachclassId) throws PlatformException {
		if (studentPriv.addCourseware != 1) {
			throw new PlatformException("û�н���γ̽�����Ȩ�ޣ�");
		} else {
			OracleInteractionUserPriv interactionUserPriv = new OracleInteractionUserPriv(
					user_id);
			interactionUserPriv.setTeachclassId(teachclassId);
			return interactionUserPriv;
		}
	}

	public List getUserFeeRecord(Page page, String userId, String feeType,
			String payoutType, String startDate, String endDate)
			throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("user_id", userId));
		searchProperty.add(new SearchProperty("fee_type", feeType));
		searchProperty.add(new SearchProperty("payout_type", payoutType));
		searchProperty.add(new SearchProperty("date_time", startDate, "D>="));
		searchProperty.add(new SearchProperty("date_time", endDate, "D<="));
		searchProperty.add(new SearchProperty("checked", "1", "="));
		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("date_time"));
		return feeList.searchFeeRecord(page, searchProperty, orderProperty);
	}

	public int getUserFeeRecordNum(String userId, String feeType,
			String payoutType, String startDate, String endDate)
			throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("user_id", userId));
		searchProperty.add(new SearchProperty("fee_type", feeType));
		searchProperty.add(new SearchProperty("payout_type", payoutType));
		searchProperty.add(new SearchProperty("checked", "1"));
		searchProperty.add(new SearchProperty("date_time", startDate, "D>="));
		searchProperty.add(new SearchProperty("date_time", endDate, "D<="));
		return feeList.searchFeeRecordNum(searchProperty);
	}

	public List getUserLeftFee(Page page, String userId, String feeType)
			throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		List searchProperty = new ArrayList();
		if (userId != null && !userId.equals(""))
			searchProperty.add(new SearchProperty("user_id", userId));

		searchProperty.add(new SearchProperty("fee_type", feeType));
		searchProperty.add(new SearchProperty("checked", "1"));
		return feeList.searchLeftFee(page, searchProperty);
	}

//	public BankManage getBankManage() throws PlatformException {
//		BankFactory factory = BankFactory.getInstance();
//		BankManagerPriv amanagerpriv = factory.getBankManagerPriv(null);
//		amanagerpriv.onlinePay = 1;
//		return factory.creatBankManage(amanagerpriv);
//	}

	public void checkPay(String id) throws PlatformException {
		OracleDealWithFee dealwith = new OracleDealWithFee();
		try {
			boolean flag = dealwith.checkFee(id);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.studentPriv.getStudentId()
									+ "</whaty><whaty>BEHAVIOR$|$checkPay</whaty><whaty>STATUS$|$"
									+ flag
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.STUDENT
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} catch (FeeException e) {
			throw new PlatformException("checkPay error!(" + e.toString() + ")");
		}

	}

//	public HashMap payByBank(HttpServletRequest req) throws PlatformException {
//		HashMap map = new HashMap();
//		// String userId = this.studentPriv.getStudentId();
//		String userId;
//		if (studentPriv == null
//				|| this.studentPriv.getStudentId().equals("studentPriv")) {
//			userId = req.getParameter("ssoUserId");
//		} else {
//			userId = this.studentPriv.getStudentId();
//		}
//
//		String orderNo = req.getParameter("OrderNo");
//		String feeValueStr = req.getParameter("OrderAmount");
//		double feeValue = Double.parseDouble(feeValueStr);
//		String feeType = req.getParameter("feeType");
//		String payoutType = req.getParameter("payoutType");
//		String note = req.getParameter("MerchantRemarks");
//		OracleDealWithFee dealFee = new OracleDealWithFee();
//		String recordId;
//		try {
//			recordId = dealFee.getNewRecordId();
//		} catch (FeeException e1) {
//			throw new PlatformException("payByBank error!(" + e1.toString()
//					+ ")");
//		}
//		try {
//			if (dealFee.dealWith(recordId, userId, feeValue, feeType,
//					payoutType, note, true, orderNo)) {
//				BankFactory factory = BankFactory.getInstance();
//				BankManagerPriv amanagerpriv = factory
//						.getBankManagerPriv(userId);
//				BankManage bankManage = factory.creatBankManage(amanagerpriv);
//				// boolean flag = bankManage.onlinePay(req);
//				// return flag;
//				map = bankManage.onlinePay(req);
//				UserLog
//						.setInfo(
//								"<whaty>USERID$|$"
//										+ userId
//										+ "</whaty><whaty>BEHAVIOR$|$payByBank</whaty><whaty>STATUS$|$"
//										+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
//										+ LogType.STUDENT
//										+ "</whaty><whaty>PRIORITY$|$"
//										+ LogPriority.INFO + "</whaty>",
//								new Date());
//			}
//		} catch (FeeException e) {
//			throw new PlatformException("payByBank error!(" + e.toString()
//					+ ")");
//		}
//
//		return map;
//	}

	public boolean checkPayByOrderNo(String orderNo) throws PlatformException {
		OracleDealWithFee dealwith = new OracleDealWithFee();
		boolean flag = false;
		try {
			flag = dealwith.checkFeeByOrderNo(orderNo);

			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.studentPriv.getStudentId()
									+ "</whaty><whaty>BEHAVIOR$|$checkPayByOrderNo</whaty><whaty>STATUS$|$"
									+ flag
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.STUDENT
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} catch (FeeException e) {
			throw new PlatformException("checkPayByOrderNo error!("
					+ e.toString() + ")");
		}
		return flag;
	}

	public void repealByOrderNo(String orderNo) throws PlatformException {
		OracleDealWithFee dealwith = new OracleDealWithFee();
		try {
			boolean flag = dealwith.repealByOrderNo(orderNo);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.studentPriv.getStudentId()
									+ "</whaty><whaty>BEHAVIOR$|$repealByOrderNo</whaty><whaty>STATUS$|$"
									+ flag
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.STUDENT
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} catch (FeeException e) {
			throw new PlatformException("repealByOrderNo error!("
					+ e.toString() + ")");
		}

	}

	public void unCheckPayByOrderNo(String orderNo) throws PlatformException {
		OracleDealWithFee dealwith = new OracleDealWithFee();
		try {
			boolean flag = dealwith.unCheckFeeByOrderNo(orderNo);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.studentPriv.getStudentId()
									+ "</whaty><whaty>BEHAVIOR$|$unCheckPayByOrderNo</whaty><whaty>STATUS$|$"
									+ flag
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.STUDENT
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} catch (FeeException e) {
			throw new PlatformException("checkPayByOrderNo error!("
					+ e.toString() + ")");
		}

	}

	public boolean getCheckStatusByOrderNo(String orderNo)
			throws PlatformException {
		OracleDealWithFee dealwith = new OracleDealWithFee();
		boolean status = false;
		try {
			status = dealwith.getCheckStatusByOrderNo(orderNo);

		} catch (Exception e) {
			
		}
		return status;
	}

	public void deleteByOrderNo(String orderNo) throws PlatformException {
		OracleDealWithFee dealwith = new OracleDealWithFee();
		try {
			dealwith.deleteByOrderNo(orderNo);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.studentPriv.getStudentId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteByOrderNo</whaty><whaty>STATUS$|$"
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.STUDENT
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} catch (FeeException e) {
			throw new PlatformException("deleteByOrderNo error!("
					+ e.toString() + ")");
		}

	}

	public String getOrderNo(String regNo) throws PlatformException {
		String orderNo = "";
		OracleDealWithFee dealwith = new OracleDealWithFee();
		try {
			orderNo = dealwith.getOrderNo(regNo);
		} catch (FeeException e) {
			throw new PlatformException("getOrderNo error!(" + e.toString()
					+ ")");
		}

		return orderNo;
	}

	public int addUserFeeReturnApply(String userId, double amount, String note)
			throws PlatformException {
		OracleUserFeeReturnApply apply = new OracleUserFeeReturnApply();
		apply.setUserId(userId);
		apply.setAmount(amount);
		apply.setNote(note);

		int sub = apply.add();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.studentPriv.getStudentId()
								+ "</whaty><whaty>BEHAVIOR$|$addUserFeeReturnApply</whaty><whaty>STATUS$|$"
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.STUDENT
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;
	}

	public int deleteUserFeeReturnApply(String id) throws PlatformException {
		OracleUserFeeReturnApply apply = new OracleUserFeeReturnApply();
		apply.setId(id);

		int sub = apply.delete();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.studentPriv.getStudentId()
								+ "</whaty><whaty>BEHAVIOR$|$deleteUserFeeReturnApply</whaty><whaty>STATUS$|$"
								+ sub
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.STUDENT
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;
	}

	public List getUserFeeReturnApplyList(Page page, String userId,
			String isChecked, String isReturned, String note)
			throws PlatformException {
		OracleBasicActivityList activityList = new OracleBasicActivityList();
		List searchList = new ArrayList();
		if (userId != null && !"null".equalsIgnoreCase(userId)
				&& !"".equals(userId))
			searchList.add(new SearchProperty("user_id", userId, "="));
		if (isChecked != null && !"null".equalsIgnoreCase(isChecked)
				&& !"".equals(isChecked))
			searchList.add(new SearchProperty("ischecked", isChecked, "="));
		if (isReturned != null && !"null".equalsIgnoreCase(isReturned)
				&& !"".equals(isReturned))
			searchList.add(new SearchProperty("isreturned", isReturned, "="));
		if (note != null && !"null".equalsIgnoreCase(note) && !"".equals(note))
			searchList.add(new SearchProperty("note", note, "like"));

		return activityList.getUserFeeReturnApplyList(page, searchList, null);
	}

	public int getUserFeeReturnApplyNum(String userId, String isChecked,
			String isReturned, String note) throws PlatformException {
		OracleBasicActivityList activityList = new OracleBasicActivityList();
		List searchList = new ArrayList();
		if (userId != null && !"null".equalsIgnoreCase(userId)
				&& !"".equals(userId))
			searchList.add(new SearchProperty("user_id", userId, "="));
		if (isChecked != null && !"null".equalsIgnoreCase(isChecked)
				&& !"".equals(isChecked))
			searchList.add(new SearchProperty("ischecked", isChecked, "="));
		if (isReturned != null && !"null".equalsIgnoreCase(isReturned)
				&& !"".equals(isReturned))
			searchList.add(new SearchProperty("isreturned", isReturned, "="));
		if (note != null && !"null".equalsIgnoreCase(note) && !"".equals(note))
			searchList.add(new SearchProperty("note", note, "like"));

		return activityList.getUserFeeReturnApplyNum(searchList);
	}

	public int updateUserFeeReturnApply(String id, String userId,
			double amount, String applyTime, String isChecked,
			String checkTime, String isReturned, String returnTime, String note)
			throws PlatformException {
		OracleUserFeeReturnApply apply = new OracleUserFeeReturnApply();
		apply.setId(id);
		apply.setUserId(userId);
		apply.setAmount(amount);
		apply.setApplyTime(applyTime);
		apply.setIsChecked(isChecked);
		apply.setCheckTime(checkTime);
		apply.setIsReturned(isReturned);
		apply.setReturnTime(returnTime);
		apply.setNote(note);

		int sub = apply.update();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.studentPriv.getStudentId()
								+ "</whaty><whaty>BEHAVIOR$|$updateUserFeeReturnApply</whaty><whaty>STATUS$|$"
								+ sub
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.STUDENT
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;
	}

	public List getTeachersByTeachClass(String teachclass_id)
			throws PlatformException {
		if (studentPriv.appointTeacherForCourse == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.getTeachersByTeachClass(teachclass_id);
		} else {
			throw new PlatformException("��û�и�γ�ָ����ʦ��Ȩ��");
		}
	}

	public List getExamRooms(String course_id, String user_id)
			throws PlatformException {
		OracleExamList examList = new OracleExamList();
		return examList.getExamRooms(course_id, user_id);
	}

	public List getTeachClassByOpenCourseId(String open_course_id)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List teachClassList = new ArrayList();
		String sql = "select id,name,assistance_release_status,assistance_note,assistance_title,course_id,course_name  "
				+ "from (select etc.id,etc.name,etc.assistance_release_status,etc.assistance_note,assistance_title,eci.id as course_id,eci.name as course_name "
				+ "from entity_teach_class etc,entity_course_active eca,entity_course_info eci where etc.open_course_id = '"
				+ open_course_id
				+ "' and etc.open_course_id=eca.id and eci.id=eca.course_id)";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleTeachClass teachclass = new OracleTeachClass();
				teachclass.setId(rs.getString("id"));
				teachclass.setName(rs.getString("name"));
				teachclass.setAssistanceReleaseStatus(rs
						.getString("assistance_release_status"));
				teachclass.setAssistanceNote(rs.getString("assistance_note"));
				teachclass.setAssistanceTitle(rs.getString("assistance_title"));
				OracleOpenCourse opencourse = new OracleOpenCourse();
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
//				opencourse.setCourse(course);
				teachclass.setOpenCourse(opencourse);
				teachClassList.add(teachclass);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teachClassList;
	}

	public List getChangeMajorApplys(String studentId) throws PlatformException {
		List searchPropertis = new ArrayList();
		searchPropertis.add(new SearchProperty("student_id", studentId, "="));
		if (studentId == null || studentId.trim().equals(""))
			return null;
		else {
			OracleBasicActivityList basicActivity = new OracleBasicActivityList();
			return basicActivity.getChangeMajorApplys(null, searchPropertis,
					null);
		}

	}

	public List getAllSites() throws NoRightException {
		OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
		return basicdatalist.getSites(null, null, null);
	}

	public List getAllMajors() throws NoRightException {
		OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
		List searchProperties = new ArrayList();
		searchProperties.add(new SearchProperty("m.id", "0", "<>"));
		List OrderProperties = new ArrayList();
		OrderProperties.add(new OrderProperty("id"));
		return basicdatalist.getMajors(null, searchProperties, OrderProperties);
	}

	public int addChangeMajorApply(String studentId, String old_site_id,
			String new_site_id, String old_major_id, String new_major_id,
			String old_edu_type_id, String new_edu_type_id,
			String old_grade_id, String new_grade_id) throws PlatformException {
		OracleChangeMajorApply apply = new OracleChangeMajorApply();
		apply.setStudentId(studentId);
		apply.setOldSiteId(old_site_id);
		apply.setNewSiteId(new_site_id);
		apply.setOldMajorId(old_major_id);
		apply.setNewMajorId(new_major_id);
		apply.setOldEduTypeId(old_edu_type_id);
		apply.setNewEduTypeId(new_edu_type_id);
		apply.setOldGradeId(old_grade_id);
		apply.setNewGradeId(new_grade_id);
		return apply.add();
	}

	public int updateChangeMajorApply(String id, String old_site_id,
			String new_site_id, String old_major_id, String new_major_id,
			String old_edu_type_id, String new_edu_type_id,
			String old_grade_id, String new_grade_id) throws PlatformException {
		OracleChangeMajorApply apply = new OracleChangeMajorApply(id);
		apply.setOldSiteId(old_site_id);
		apply.setNewSiteId(new_site_id);
		apply.setOldMajorId(old_major_id);
		apply.setNewMajorId(new_major_id);
		apply.setOldEduTypeId(old_edu_type_id);
		apply.setNewEduTypeId(new_edu_type_id);
		apply.setOldGradeId(old_grade_id);
		apply.setNewGradeId(new_grade_id);
		return apply.update();
	}

	public int deleteChangeMajorApply(String id) throws PlatformException {
		OracleChangeMajorApply apply = new OracleChangeMajorApply();
		apply.setId(id);
		return apply.delete();
	}

	public ChangeMajorApply getChangeMajorApply(String id)
			throws PlatformException {
		OracleChangeMajorApply apply = new OracleChangeMajorApply(id);
		return apply;

	}

	public boolean isNewStudent(String ssoId) throws NoRightException {
		OracleBasicUserList basicUserList = new OracleBasicUserList();
		return basicUserList.isNewStudent(ssoId);
	}

	public SubjectQuestionary getSubjectQuestionary(String studentId)
			throws PlatformException {
		SubjectQuestionary subjectQuestionary = new OracleSubjectQuestionary();
		subjectQuestionary = subjectQuestionary
				.getSubjectQuestionary(studentId);
		SiteTeacherLimit oracleSiteTeacherLimit = new OracleSiteTeacherLimit(
				studentId, "STUDENT");
		subjectQuestionary.setSiteTeacher(oracleSiteTeacherLimit
				.getSiteTeacher());
		return subjectQuestionary;
	}

	public RegBgCourse getRegBeginCourse(String studentId)
			throws PlatformException {
		RegBgCourse regBgCourse = new OracleRegBgCourse();
		regBgCourse = regBgCourse.getRegBgCourse(studentId);
		SiteTeacherLimit oracleSiteTeacherLimit = new OracleSiteTeacherLimit(
				studentId, "STUDENT");
		regBgCourse.setSiteTeacher(oracleSiteTeacherLimit.getSiteTeacher());
		return regBgCourse;
	}

	public SiteTeacher getSiteTutorTeacher(String studentId)
			throws PlatformException {
		SiteTeacherLimit siteTeacherLimit = new OracleSiteTeacherLimit(
				studentId, "STUDENT");

		return siteTeacherLimit.getSiteTeacher();
	}

	public MetaphaseCheck getMetaphaseCheck(String studentId)
			throws PlatformException {
		MetaphaseCheck metaphaseCheck = new OracleMetaphaseCheck();
		metaphaseCheck = metaphaseCheck.getMetaphaseCheck(studentId);
		SiteTeacherLimit oracleSiteTeacherLimit = new OracleSiteTeacherLimit(
				studentId, "STUDENT");
		metaphaseCheck.setSiteTeacher(oracleSiteTeacherLimit.getSiteTeacher());
		return metaphaseCheck;
	}

	public RejoinRequisition getRejoinRequisition(String studentId)
			throws PlatformException {
		RejoinRequisition rejoinRequisition = new OracleRejoinRequisition();
		rejoinRequisition = rejoinRequisition.getRejoinRequisition(studentId);
		SiteTeacherLimit oracleSiteTeacherLimit = new OracleSiteTeacherLimit(
				studentId, "STUDENT");
		rejoinRequisition.setSiteTeacher(oracleSiteTeacherLimit
				.getSiteTeacher());
		return rejoinRequisition;
	}

	public Discourse getDiscourse(String studentId) throws PlatformException {
		Discourse discourse = new OracleDiscourse();
		discourse = discourse.getDiscourse(studentId);
		SiteTeacherLimit oracleSiteTeacherLimit = new OracleSiteTeacherLimit(
				studentId, "STUDENT");
		discourse.setSiteTeacher(oracleSiteTeacherLimit.getSiteTeacher());
		return discourse;
	}

	public int addSubjectQuestionary(String studentid, String mainjob,
			String subjectname, String subjectcontent, String remark)
			throws PlatformException {
		SubjectQuestionary subjectQuestionary = new OracleSubjectQuestionary();
		Student student = new OracleStudent();
		student.setId(studentid);
		subjectQuestionary.setStudent(student);
		subjectQuestionary.setMain_job(mainjob);
		subjectQuestionary.setSubjectContent(subjectcontent);
		subjectQuestionary.setSubjectName(subjectname);
		subjectQuestionary.setRemark(remark);
		subjectQuestionary.setStatus("0");
		subjectQuestionary.setLink("");
		subjectQuestionary.setSiteTeacher_note("");
		subjectQuestionary.setTeacher_note("");
		return subjectQuestionary.add();
	}

	public int addSubjectQuestionary(String studentid, String mainjob,
			String subjectname, String subjectcontent, String link,
			String remark) throws PlatformException {
		SubjectQuestionary subjectQuestionary = new OracleSubjectQuestionary();
		Student student = new OracleStudent();
		student.setId(studentid);
		subjectQuestionary.setStudent(student);
		subjectQuestionary.setMain_job(mainjob);
		subjectQuestionary.setSubjectContent(subjectcontent);
		subjectQuestionary.setSubjectName(subjectname);
		subjectQuestionary.setRemark(remark);
		subjectQuestionary.setStatus("0");
		subjectQuestionary.setLink(link);
		subjectQuestionary.setSiteTeacher_note("");
		subjectQuestionary.setTeacher_note("");
		return subjectQuestionary.add();
	}

	public int editSubjectQuestionary(String studentid, String mainjob,
			String subjectname, String subjectcontent, String link,
			String remark) throws PlatformException {
		SubjectQuestionary subjectQuestionary = new OracleSubjectQuestionary().getSubjectQuestionary(studentid);
		subjectQuestionary.setSubjectName(subjectname);
		subjectQuestionary.setStatus("0");
		subjectQuestionary.setLink(link);
		subjectQuestionary.setSiteTeacher_note("");
		subjectQuestionary.setTeacher_note("");
		return subjectQuestionary.update();
	}

	
	public int updateSubjectQuestionary(String id, String mainjob,
			String subjectname, String subjectcontent, String remark)
			throws PlatformException {
		SubjectQuestionary subjectQuestionary = new OracleSubjectQuestionary(id);
		subjectQuestionary.setId(id);
		subjectQuestionary.setMain_job(mainjob);
		subjectQuestionary.setSubjectContent(subjectcontent);
		subjectQuestionary.setSubjectName(subjectname);
		subjectQuestionary.setRemark(remark);
		subjectQuestionary.setStatus("0");
		return subjectQuestionary.update();
	}

	public int addRegBeginCourse(String studentid, String discoursename,
			String discoursecontent, String discoursePlan, String remark)
			throws PlatformException {
		RegBgCourse regBeginCourse = new OracleRegBgCourse();
		Student student = new OracleStudent();
		student.setId(studentid);
		regBeginCourse.setStudent(student);
		regBeginCourse.setDiscourseName(discoursename);
		regBeginCourse.setDiscourseContent(discoursecontent);
		regBeginCourse.setDiscoursePlan(discoursePlan);
		regBeginCourse.setRemark(remark);
		regBeginCourse.setLink("");
		regBeginCourse.setSiteteacher_note("");
		regBeginCourse.setTeacher_note("");
		regBeginCourse.setStatus("0");
		return regBeginCourse.add();
	}

	public int addRegBeginCourse(String studentid, String discoursename,
			String discoursecontent, String discoursePlan, String link,
			String remark) throws PlatformException {
		RegBgCourse regBeginCourse = new OracleRegBgCourse();
		Student student = new OracleStudent();
		student.setId(studentid);
		regBeginCourse.setStudent(student);
		regBeginCourse.setDiscourseName(discoursename);
		regBeginCourse.setDiscourseContent(discoursecontent);
		regBeginCourse.setDiscoursePlan(discoursePlan);
		regBeginCourse.setRemark(remark);
		regBeginCourse.setLink(link);
		regBeginCourse.setSiteteacher_note("");
		regBeginCourse.setTeacher_note("");
		regBeginCourse.setStatus("0");
		return regBeginCourse.add();
	}

	public int updateRegBeginCourse(String id, String discoursename,
			String discoursecontent, String discoursePlan, String remark)
			throws PlatformException {
		RegBgCourse regBeginCourse = new OracleRegBgCourse(id);
		regBeginCourse.setId(id);
		regBeginCourse.setDiscourseName(discoursename);
		regBeginCourse.setDiscourseContent(discoursecontent);
		regBeginCourse.setDiscoursePlan(discoursePlan);
		regBeginCourse.setRemark(remark);
		regBeginCourse.setStatus("5");
		return regBeginCourse.update();
	}

	public int addMetaphaseCheck(String studentid, String discourseName,
			String completetask, String anaphasePlan, String remark)
			throws PlatformException {
		MetaphaseCheck metaphaseCheck = new OracleMetaphaseCheck();
		metaphaseCheck.setDiscourseName(discourseName);
		metaphaseCheck.setCompletetask(completetask);
		metaphaseCheck.setAnaphasePlan(anaphasePlan);
		metaphaseCheck.setRemark(remark);
		Student student = new OracleStudent();
		student.setId(studentid);
		metaphaseCheck.setStudent(student);
		metaphaseCheck.setLink("");
		metaphaseCheck.setTeacher_note("");
		metaphaseCheck.setSiteteacher_note("");
		metaphaseCheck.setStatus("0");
		return metaphaseCheck.add();
	}

	public int addMetaphaseCheck(String studentid, String discourseName,
			String completetask, String anaphasePlan, String link, String remark)
			throws PlatformException {
		MetaphaseCheck metaphaseCheck = new OracleMetaphaseCheck();
		metaphaseCheck.setDiscourseName(discourseName);
		metaphaseCheck.setCompletetask(completetask);
		metaphaseCheck.setAnaphasePlan(anaphasePlan);
		metaphaseCheck.setRemark(remark);
		Student student = new OracleStudent();
		student.setId(studentid);
		metaphaseCheck.setStudent(student);
		metaphaseCheck.setLink(link);
		metaphaseCheck.setTeacher_note("");
		metaphaseCheck.setSiteteacher_note("");
		metaphaseCheck.setStatus("0");
		return metaphaseCheck.add();
	}

	public int updateMetaphaseCheck(String id, String discourseName,
			String completetask, String anaphasePlan, String remark)
			throws PlatformException {
		MetaphaseCheck metaphaseCheck = new OracleMetaphaseCheck(id);
		metaphaseCheck.setId(id);
		metaphaseCheck.setDiscourseName(discourseName);
		metaphaseCheck.setCompletetask(completetask);
		metaphaseCheck.setAnaphasePlan(anaphasePlan);
		metaphaseCheck.setRemark(remark);
		metaphaseCheck.setSiteteacher_note("");
		metaphaseCheck.setStatus("0");
		return metaphaseCheck.update();
	}

	public int addRejoinRequisition(String studentid, String discourseName,
			String completeStatus, String requisitionType, String remark)
			throws PlatformException {
		RejoinRequisition rejoinRequisition = new OracleRejoinRequisition();
		Student student = new OracleStudent();
		student.setId(studentid);
		rejoinRequisition.setStudent(student);

		rejoinRequisition.setDiscourseName(discourseName);
		rejoinRequisition.setCompleteStatus(completeStatus);
		rejoinRequisition.setRequisitionType(requisitionType);
		rejoinRequisition.setRemark(remark);
		rejoinRequisition.setTeacher_note("");
		rejoinRequisition.setSiteteacher_note("");
		rejoinRequisition.setLink("");
		rejoinRequisition.setStatus("0");
		return rejoinRequisition.add();
	}

	public int addRejoinRequisition(String studentid, String discourseName,
			String completeStatus, String requisitionType, String link,
			String remark) throws PlatformException {
		RejoinRequisition rejoinRequisition = new OracleRejoinRequisition();
		Student student = new OracleStudent();
		student.setId(studentid);
		rejoinRequisition.setStudent(student);

		rejoinRequisition.setDiscourseName(discourseName);
		rejoinRequisition.setCompleteStatus(completeStatus);
		rejoinRequisition.setRequisitionType(requisitionType);
		rejoinRequisition.setRemark(remark);
		rejoinRequisition.setTeacher_note("");
		rejoinRequisition.setSiteteacher_note("");
		rejoinRequisition.setLink(link);
		rejoinRequisition.setStatus("0");
		return rejoinRequisition.add();
	}

	public int updateRejoinRequisition(String id, String discourseName,
			String completeStatus, String requisitionType, String remark)
			throws PlatformException {
		RejoinRequisition rejoinRequisition = new OracleRejoinRequisition(id);
		rejoinRequisition.setId(id);
		rejoinRequisition.setDiscourseName(discourseName);
		rejoinRequisition.setCompleteStatus(completeStatus);
		rejoinRequisition.setRequisitionType(requisitionType);
		rejoinRequisition.setRemark(remark);
		rejoinRequisition.setStatus("0");
		return rejoinRequisition.update();
	}

	public int addDiscourse(String studentid, String discourseName,
			String discourseContent, String requisitionType, String remark)
			throws PlatformException {
		Discourse discourse = new OracleDiscourse();
		Student student = new OracleStudent();
		student.setId(studentid);
		discourse.setStudent(student);

		discourse.setDiscourseName(discourseName);
		discourse.setDiscourseContent(discourseContent);
		discourse.setRequisitionType(requisitionType);
		discourse.setRemark(remark);
		discourse.setTeacher_note("");
		discourse.setSiteTeacher_note("");
		discourse.setLink("");
		discourse.setStatus("3");
		return discourse.add();
	}

	public int addDiscourse(String studentid, String discourseName,
			String discourseContent, String requisitionType, String link,
			String remark) throws PlatformException {
		Discourse discourse = new OracleDiscourse();
		Student student = new OracleStudent();
		student.setId(studentid);
		discourse.setStudent(student);

		discourse.setDiscourseName(discourseName);
		discourse.setDiscourseContent(discourseContent);
		discourse.setRequisitionType(requisitionType);
		discourse.setRemark(remark);
		discourse.setTeacher_note("");
		discourse.setSiteTeacher_note("");
		discourse.setLink(link);
		discourse.setStatus("3");
		return discourse.add();
	}

	public int updateDiscourse(String id, String discourseName,
			String discourseContent, String requisitionType, String remark)
			throws PlatformException {
		Discourse discourse = new OracleDiscourse(id);
		discourse.setId(id);
		discourse.setDiscourseName(discourseName);
		discourse.setDiscourseContent(discourseContent);
		discourse.setRequisitionType(requisitionType);
		discourse.setRemark(remark);
		discourse.setStatus("0");
		return discourse.update();
	}

	public Pici getActiveGraduateDesignBatch() throws PlatformException {
		OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("status", "1", "="));
		return basicdatalist.getGraduateDesignBatch(searchProperty);
	}

	/**
	 * ��õ�ǰ��ҵ����ҵ���
	 */
	public Pici getActiveGraduateExecDesignBatch() throws PlatformException {
		OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("status", "1", "="));
		return basicdatalist.getGraduateExecDesignBatch(searchProperty);
	}

	public Pici getGraduateDesignBatch(String aid) throws PlatformException {
		OracleGraduatePici oracleGraduatePici = new OracleGraduatePici(aid);
		return oracleGraduatePici;
	}

	/**
	 * ��ñ�ҵ����ҵ���
	 */
	public Pici getGraduateExecDesignBatch(String aid) throws PlatformException {
		OracleGraduateExecPici oracleGraduatePici = new OracleGraduateExecPici(
				aid);
		return oracleGraduatePici;
	}

	public int upLoadSubjectQuestionaryWord(String reg_no, String fileLink)
			throws PlatformException {
		SubjectQuestionary subjectQuestionaryWord = new OracleSubjectQuestionary();
		return subjectQuestionaryWord
				.upLoadGraduateDesignWord(reg_no, fileLink);
	}
	public int upLoadSubjectQuestionaryWordFinally(String reg_no, String fileLink) throws PlatformException {
		SubjectQuestionary subjectQuestionaryWord = new OracleSubjectQuestionary();
		return subjectQuestionaryWord.upLoadGraduateDesignWord(reg_no,fileLink,"5");
	}

	public int upLoadRegBeginCourseWord(String reg_no, String fileLink)
			throws PlatformException {
		RegBgCourse regBeginCourse = new OracleRegBgCourse();
		return regBeginCourse.upLoadGraduateDesignWord(reg_no, fileLink);
	}

	public int upLoadMetaphaseCheckWord(String reg_no, String fileLink)
			throws PlatformException {
		MetaphaseCheck metaphaseCheck = new OracleMetaphaseCheck();
		return metaphaseCheck.upLoadGraduateDesignWord(reg_no, fileLink);
	}

	public int upLoadRejoinRequisitionWord(String reg_no, String fileLink)
			throws PlatformException {
		RejoinRequisition rejoinRequisition = new OracleRejoinRequisition();
		return rejoinRequisition.upLoadGraduateDesignWord(reg_no, fileLink);
	}

	public int upLoadDiscourseWord(String reg_no, String fileLink)
			throws PlatformException {
		Discourse discourse = new OracleDiscourse();
		return discourse.upLoadGraduateDesignWord(reg_no, fileLink);
	}

	public int limitTime(String type) throws PlatformException {
		Pici pici = new OracleGraduatePici();
		int suc = 0;

		if (type.equals("TYPE1")) {
			suc = pici.graduateDesignTimeSect();
		} else if (type.equals("TYPE2")) {
			suc = pici.subjectResearchTimeSect();
		} else if (type.equals("TYPE3")) {
			suc = pici.regBgCourseTimeSect();
		} else if (type.equals("TYPE4")) {
			suc = pici.discourseSumbitTimeSect();
		} else if (type.equals("TYPE5")) {
			suc = pici.rejoinRequesTimeSect();
		} else if (type.equals("TYPE6")) {
			suc = pici.reportGraduTimeSect();
		} else if (type.equals("TYPE7")) {
			suc = pici.witeDiscourseTimeSect();
		}
		return suc;
	}

	@Override
	public int isGraduate(String studentId) throws PlatformException {
		OracleBasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
		return oracleBasicGraduateList.isGraduate(studentId);
	}

	@Override
	public boolean isClassManager(String studentId, String classId)
			throws PlatformException {
		boolean isManager = false;
		List searchProperty = new ArrayList();
		OracleClassMemberList classList = new OracleClassMemberList();
		searchProperty.add(new SearchProperty("member_type",
				ClassMember.STUDENT_MEMBER));
		searchProperty.add(new SearchProperty("class_id", classId));
		searchProperty.add(new SearchProperty("link_id", studentId));
		List list = classList.getClassMembers(null, searchProperty, null);
		ClassMember member = null;
		if (list.size() > 0) {
			member = (ClassMember) list.get(0);
			if (member.getRole().equals("1"))
				isManager = true;
			else
				isManager = false;
		}
		return isManager;
	}

	public boolean isClassMember(String studentId, String classId)
			throws PlatformException {
		boolean isClassMember = false;
		List searchProperty = new ArrayList();
		OracleClassMemberList classList = new OracleClassMemberList();
		searchProperty.add(new SearchProperty("member_type",
				ClassMember.STUDENT_MEMBER));
		searchProperty.add(new SearchProperty("class_id", classId));
		searchProperty.add(new SearchProperty("link_id", studentId));
		List list = classList.getClassMembers(null, searchProperty, null);
		ClassMember member = null;
		if (list.size() > 0) {
			isClassMember = true;
		}
		return isClassMember;
	}

	@Override
	public boolean isAssociationManager(String studentId, String associationId)
			throws PlatformException {
		boolean isManager = false;
		List searchProperty = new ArrayList();
		OracleAssociationMemberList associationList = new OracleAssociationMemberList();
		searchProperty.add(new SearchProperty("member_type",
				AssociationMember.STUDENT_MEMBER));
		searchProperty.add(new SearchProperty("association_id", associationId));
		searchProperty.add(new SearchProperty("link_id", studentId));
		List list = associationList.getAssociationMembers(null, searchProperty,
				null);
		AssociationMember member = null;
		if (list.size() > 0) {
			member = (AssociationMember) list.get(0);
			if (member.getRole().equals("1"))
				isManager = true;
			else
				isManager = false;
		}
		return isManager;
	}

	public boolean isAssociationMember(String studentId, String associationId)
			throws PlatformException {
		boolean isMember = false;
		List searchProperty = new ArrayList();
		OracleAssociationMemberList associationList = new OracleAssociationMemberList();
		searchProperty.add(new SearchProperty("member_type",
				AssociationMember.STUDENT_MEMBER));
		searchProperty.add(new SearchProperty("association_id", associationId));
		searchProperty.add(new SearchProperty("link_id", studentId));
		List list = associationList.getAssociationMembers(null, searchProperty,
				null);
		AssociationMember member = null;
		if (list.size() > 0) {
			isMember = true;
		}
		return isMember;
	}

	@Override
	public List getClassMemberes(Page page, String classId, String studentId)
			throws PlatformException {
		List searchProperty = new ArrayList();
		List orderProperty = new ArrayList();
		StrManage strManage = StrManageFactory.creat();
		OracleClassMemberList classList = new OracleClassMemberList();
		try {
			searchProperty.add(new SearchProperty("member_type",
					ClassMember.STUDENT_MEMBER));
			if (!(strManage.fixNull(classId)).equals(""))
				searchProperty.add(new SearchProperty("class_id", classId));
			if (!(strManage.fixNull(studentId)).equals(""))
				searchProperty.add(new SearchProperty("link_id", studentId));
		} catch (WhatyUtilException e) {
			throw new PlatformException();
		}
		return classList.getClassMembers(page, searchProperty, orderProperty);
	}

	@Override
	public List getAssociationMemberes(Page page, String associationId,
			String studentId) throws PlatformException {
		List searchProperty = new ArrayList();
		List orderProperty = new ArrayList();
		StrManage strManage = StrManageFactory.creat();
		OracleAssociationMemberList associationList = new OracleAssociationMemberList();
		try {
			searchProperty.add(new SearchProperty("member_type",
					AssociationMember.STUDENT_MEMBER));
			if (!(strManage.fixNull(associationId)).equals(""))
				searchProperty.add(new SearchProperty("association_id",
						associationId));
			if (!(strManage.fixNull(studentId)).equals(""))
				searchProperty.add(new SearchProperty("link_id", studentId));
		} catch (WhatyUtilException e) {
			throw new PlatformException();
		}
		return associationList.getAssociationMembers(page, searchProperty,
				orderProperty);
	}

	public int getClassMemberesNum(String classId, String studentId)
			throws PlatformException {
		List searchProperty = new ArrayList();
		StrManage strManage = StrManageFactory.creat();
		OracleClassMemberList classList = new OracleClassMemberList();
		try {
			searchProperty.add(new SearchProperty("member_type",
					ClassMember.STUDENT_MEMBER));
			if (!(strManage.fixNull(classId)).equals(""))
				searchProperty.add(new SearchProperty("class_id", classId));
			if (!(strManage.fixNull(studentId)).equals(""))
				searchProperty.add(new SearchProperty("link_id", studentId));
		} catch (WhatyUtilException e) {
			throw new PlatformException();
		}
		return classList.getClassMembersNum(searchProperty);
	}

	@Override
	public int getAssociationMemberesNum(String associationId, String studentId)
			throws PlatformException {
		List searchProperty = new ArrayList();
		StrManage strManage = StrManageFactory.creat();
		OracleAssociationMemberList associationList = new OracleAssociationMemberList();
		try {
			searchProperty.add(new SearchProperty("member_type",
					AssociationMember.STUDENT_MEMBER));
			if (!(strManage.fixNull(associationId)).equals(""))
				searchProperty.add(new SearchProperty("association_id",
						associationId));
			if (!(strManage.fixNull(studentId)).equals(""))
				searchProperty.add(new SearchProperty("link_id", studentId));
		} catch (WhatyUtilException e) {
			throw new PlatformException();
		}
		return associationList.getAssociationMembersNum(searchProperty);
	}

	@Override
	public CampusManage getCampusManage(CampusManagerPriv priv)
			throws PlatformException {
		if (priv == null)
			priv = new OracleCampusManagerPriv("");
		CampusFactory factory = CampusFactory.getInstance();
		return factory.creatCampusManage(priv);
	}

	@Override
	public CampusNormalManage getCampusNormalManage() throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ManagerPriv getNormalEntityManagePriv() throws PlatformException {
		OracleManagerPriv priv = new OracleManagerPriv();
		priv.getStudent = 1;
		priv.getSemester = 1;
		priv.getEduType = 1;
		priv.getMajor = 1;
		priv.getTeacher = 1;
		priv.getSiteTeacher = 1;
		return priv;
	}

	public int subjectFreeApply(String student_id, String production_name,
			String link) throws PlatformException {
		FreeApply freeApply = new OracleFreeApply();
		freeApply.setStudentId(student_id);
		freeApply.setProductionName(production_name);
		freeApply.setLink(link);
		return freeApply.add();
	}

	/**
	 * ��������, ��������
	 */
	public int subjectFreeApply(String student_id, String production_name,
			String link, String type) throws PlatformException {
		FreeApply freeApply = new OracleFreeApply();
		freeApply.setStudentId(student_id);
		freeApply.setProductionName(production_name);
		freeApply.setLink(link);
		freeApply.setType(type);// ����������������;
		return freeApply.add();
	}

	public FreeApply getSubjectFreeApply(String student_id)
			throws PlatformException {
		return new OracleFreeApply().getFreeApplyByStudentId(student_id);
	}

	public SiteTeacher getGraduateSiteTeacher(String studentId)
			throws PlatformException {
		SiteTeacherLimit siteTeacherLimit = new OracleSiteTeacherLimit(
				studentId, "STUDENT");
		SiteTeacher siteTeacher = siteTeacherLimit.getSiteTeacher();
		return siteTeacher;
	}

	public void electiveWithFee(String[] selectIds, String[] allIds,
			String semester_id, String student_id) throws PlatformException {
		ElectiveActivity electiveActivity = new OracleElectiveActivity();
		electiveActivity.electiveWithFee(selectIds, allIds, semester_id,
				student_id);
	}

	public void electiveWithoutFee(String[] selectIds, String[] allIds,
			String semester_id, String student_id) throws PlatformException {
		ElectiveActivity electiveActivity = new OracleElectiveActivity();
		electiveActivity.electiveWithoutFee(selectIds, allIds, semester_id,
				student_id);
	}

	public HashMap getElectiveRejectNote(String student_id) {
		BasicActivityList basicActivityList = new OracleBasicActivityList();
//		return basicActivityList.getElectiveRejectNote(student_id);
		return null;
	}
	public  boolean isShowManyi(String student_id, String course_id) {
		OracleElective elective = new OracleElective();
		
		return elective.isShowManyi(student_id, course_id);
	}
}
