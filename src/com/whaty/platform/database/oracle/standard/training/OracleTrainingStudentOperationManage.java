/**
 * 
 */
package com.whaty.platform.database.oracle.standard.training;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.training.activity.OracleTrainingSelectClassActivity;
import com.whaty.platform.database.oracle.standard.training.activity.OracleTrainingSelectCourseActivity;
import com.whaty.platform.database.oracle.standard.training.activity.OracleTrainingSelectSkillActivity;
import com.whaty.platform.database.oracle.standard.training.basic.OracleStudentCourse;
import com.whaty.platform.database.oracle.standard.training.basic.OracleStudentStudyStatus;
import com.whaty.platform.database.oracle.standard.training.basic.OracleTrainingBasicList;
import com.whaty.platform.database.oracle.standard.training.basic.OracleTrainingClass;
import com.whaty.platform.database.oracle.standard.training.basic.OracleTrainingCourse;
import com.whaty.platform.database.oracle.standard.training.basic.OracleTrainingCourseType;
import com.whaty.platform.database.oracle.standard.training.skill.OracleSkill;
import com.whaty.platform.database.oracle.standard.training.skill.OracleSkillChain;
import com.whaty.platform.database.oracle.standard.training.skill.OracleSkillList;
import com.whaty.platform.database.oracle.standard.training.user.OracleTrainingStudent;
import com.whaty.platform.standard.aicc.operation.AiccCourseManage;
import com.whaty.platform.standard.aicc.operation.AiccFactory;
import com.whaty.platform.standard.aicc.operation.AiccUserDataManage;
import com.whaty.platform.standard.aicc.operation.AiccUserDataManagePriv;
import com.whaty.platform.standard.scorm.operation.ScormFactory;
import com.whaty.platform.standard.scorm.operation.ScormStudentManage;
import com.whaty.platform.standard.scorm.operation.ScormStudentPriv;
import com.whaty.platform.training.TrainingStudentOperationManage;
import com.whaty.platform.training.basic.StudentClass;
import com.whaty.platform.training.basic.StudentCourse;
import com.whaty.platform.training.basic.StudentCourseStatus;
import com.whaty.platform.training.basic.StudentStudyStatus;
import com.whaty.platform.training.basic.StudyProgress;
import com.whaty.platform.training.basic.TrainingClass;
import com.whaty.platform.training.basic.TrainingCourse;
import com.whaty.platform.training.basic.TrainingCourseType;
import com.whaty.platform.training.basic.TrainingCourseware;
import com.whaty.platform.training.skill.Skill;
import com.whaty.platform.training.skill.SkillChain;
import com.whaty.platform.training.skill.StudentSkill;
import com.whaty.platform.training.skill.StudentSkillStatus;
import com.whaty.platform.training.user.TrainingStudent;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;

/**
 * @author chenjian
 * 
 */
public class OracleTrainingStudentOperationManage extends
		TrainingStudentOperationManage {

	public OracleTrainingStudentOperationManage() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getTrainingStudent()
	 */
	public TrainingStudent getTrainingStudent() throws PlatformException {
		// TODO Auto-generated method stub
		String studentId = getTrainingStudentPriv().getStudentId();
		return new OracleTrainingStudent(studentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getPubCoursesNum(java.lang.String)
	 */
	public int getPubCoursesNum(String type) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		SearchProperty search = new SearchProperty("course_typeid", type);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		OrderProperty order = new OrderProperty("id");
		List orderproperty = new ArrayList();
		orderproperty.add(order);

		return basicList.searchCourseNum(searchproperty, orderproperty);
	}

	public int getPubCoursesNum(String type, String courseName,
			String courseNote, String courseTeacher) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		SearchProperty search = new SearchProperty("course_typeid", type);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		if (courseName != null)
			searchproperty.add(new SearchProperty("name", courseName, "like"));
		if (courseNote != null)
			searchproperty.add(new SearchProperty("note", courseNote, "like"));
		if (courseTeacher != null)
			searchproperty.add(new SearchProperty("teacher", courseTeacher,
					"like"));

		OrderProperty order = new OrderProperty("id");
		List orderproperty = new ArrayList();
		orderproperty.add(order);

		return basicList.searchCourseNum(searchproperty, orderproperty);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getPubCourses(java.lang.String)
	 */
	public List getPubCourses(String type) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		SearchProperty search = new SearchProperty("course_typeid", type);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		OrderProperty order = new OrderProperty("id");
		List orderproperty = new ArrayList();
		orderproperty.add(order);
		return basicList.searchCourse(null, searchproperty, orderproperty);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getPubCourses(com.whaty.platform.util.Page,
	 *      java.lang.String)
	 */
	public List getPubCourses(Page page, String type) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		SearchProperty search = new SearchProperty("course_typeid", type);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		OrderProperty order = new OrderProperty("id");
		List orderproperty = new ArrayList();
		orderproperty.add(order);
		return basicList.searchCourse(page, searchproperty, orderproperty);
	}

	public List getPubCourses(Page page, String type, String courseName,
			String courseNote, String courseTeacher) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		SearchProperty search = new SearchProperty("course_typeid", type);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		if (courseName != null)
			searchproperty.add(new SearchProperty("name", courseName, "like"));
		if (courseNote != null)
			searchproperty.add(new SearchProperty("note", courseNote, "like"));
		if (courseTeacher != null)
			searchproperty.add(new SearchProperty("teacher", courseTeacher,
					"like"));
		
		OrderProperty order = new OrderProperty("id");
		List orderproperty = new ArrayList();
		orderproperty.add(order);
		return basicList.searchCourse(page, searchproperty, orderproperty);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getSelectedCourses()
	 */
	public List getSelectedCourses() throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getCompletedCourse()
	 */
	public List getCompletedCourse() throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingStudent student = new OracleTrainingStudent();
		student.setId(this.getTrainingStudentPriv().getStudentId());
		SearchProperty search = new SearchProperty("studentlearn_status",
				StudyProgress.PASSED);
		List searchList = new ArrayList();
		searchList.add(search);
		return basicList.searchStudentCourse(null, searchList, null, student);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getUnCompletedCourse()
	 */
	public List getUnCompletedCourse() throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getClassesNum(String,
	 *      String, String)
	 */
	public int getClassesNum(String className, String classNote,
			String studentClassStatus) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		SearchProperty searchProperty = new SearchProperty("status", "1",
				"=num");
		List searchList = new ArrayList();
		searchList.add(searchProperty);
		if (className != null)
			searchList.add(new SearchProperty("name", className, "like"));
		if (classNote != null)
			searchList.add(new SearchProperty("note", classNote, "like"));
		if (studentClassStatus != null)
			searchList.add(new SearchProperty("studentclass_status",
					studentClassStatus, "="));
		// return basicList.searchClass(page, searchList, null);
		return basicList.getClassNum(null, searchList, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getClasses()
	 */
	public List getClasses() throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		SearchProperty searchProperty = new SearchProperty("status", "1",
				"=num");
		List searchList = new ArrayList();
		searchList.add(searchProperty);
		return basicList.searchClass(null, searchList, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getClasses(Page,
	 *      String, String, String)
	 */
	public List getClasses(Page page, String className, String classNote,
			String studentClassStatus) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		SearchProperty searchProperty = new SearchProperty("status", "1",
				"=num");
		List searchList = new ArrayList();
		searchList.add(searchProperty);
		if (className != null)
			searchList.add(new SearchProperty("name", className, "like"));
		if (classNote != null)
			searchList.add(new SearchProperty("note", classNote, "like"));
		if (studentClassStatus != null)
			searchList.add(new SearchProperty("studentclass_status",
					studentClassStatus, "="));
		return basicList.searchClass(page, searchList, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getSelectedClasses()
	 */
	public List getSelectedClasses() throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getTrainingCourse(java.lang.String)
	 */
	public TrainingCourse getTrainingCourse(String courseId)
			throws PlatformException {

		return new OracleTrainingCourse(courseId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getTrainingClass(java.lang.String)
	 */
	public TrainingClass getTrainingClass(String classId)
			throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getTrainingClassCourses(java.lang.String)
	 */
	public List getTrainingClassCourses(String classId)
			throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		return trainingClass.getTrainingCourse();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getStudentStudyStatus(java.lang.String)
	 */
	public StudentStudyStatus getStudentStudyStatus() throws PlatformException {
		return new OracleStudentStudyStatus(this.getTrainingStudentPriv()
				.getStudentId());
	}

	public int getSkillChainsNum(String skillName, String skillNote)
			throws PlatformException {
		OracleSkillList skillList = new OracleSkillList();
		List searchList = new ArrayList();
		if (skillName != null)
			searchList.add(new SearchProperty("skill_name", skillName, "like"));
		if (skillNote != null)
			searchList.add(new SearchProperty("skill_note", skillNote, "like"));
		return skillList.getSkillChainNum(searchList, null);
		// return skillList.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getSkillChain()
	 */
	public List getSkillChains() throws PlatformException {
		OracleSkillList skillList = new OracleSkillList();
		return skillList.searchSkillChain(null, null, null);
	}

	public List getSkillChains(Page page, String skillName, String skillNote)
			throws PlatformException {
		OracleSkillList skillList = new OracleSkillList();
		List searchList = new ArrayList();
		if (skillName != null)
			searchList.add(new SearchProperty("name", skillName, "like"));
		if (skillNote != null)
			searchList.add(new SearchProperty("note", skillNote, "like"));
		return skillList.searchSkillChain(page, searchList, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getSkills(java.lang.String)
	 */
	public List getSkills(String chainId) throws PlatformException {
		OracleSkillChain skillChain = new OracleSkillChain();
		skillChain.setId(chainId);
		return skillChain.getSkillList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getKillChain(java.lang.String)
	 */
	public SkillChain getSkillChain(String chainId) throws PlatformException {
		return new OracleSkillChain(chainId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getSkill(java.lang.String)
	 */
	public Skill getSkill(String skillId) throws PlatformException {
		return new OracleSkill(skillId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getObtainedSkills(java.lang.String)
	 */
	public List getStudentSkills(String chainId) throws PlatformException {
		SearchProperty search = new SearchProperty("chain_id", chainId);
		List searchList = new ArrayList();
		searchList.add(search);
		OracleSkillList skillList = new OracleSkillList();
		OracleTrainingStudent student = new OracleTrainingStudent();
		student.setId(this.getTrainingStudentPriv().getStudentId());
		return skillList.searchStudentSkill(null, searchList, null, student);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getStudentSkills()
	 */
	public List getStudentSkills() throws PlatformException {
		OracleSkillList skillList = new OracleSkillList();
		OracleTrainingStudent student = new OracleTrainingStudent();
		student.setId(this.getTrainingStudentPriv().getStudentId());
		return skillList.searchStudentSkill(null, null, null, student);
	}
	
	public int getStudentSkillsNum(String skillId, String skillName, String skillNote,
			String studentSkillStatus) throws PlatformException {
		OracleSkillList skillList = new OracleSkillList();
		OracleTrainingStudent student = new OracleTrainingStudent();
		student.setId(this.getTrainingStudentPriv().getStudentId());
		List searchList = new ArrayList();
		if(skillId != null)
			searchList.add(new SearchProperty("skill_id", skillId, "like"));
		if(skillName != null)
			searchList.add(new SearchProperty("skill_name", skillName, "like"));
		if(skillNote != null)
			searchList.add(new SearchProperty("skill_note", skillNote, "like"));
		if(studentSkillStatus != null)
			searchList.add(new SearchProperty("studentskill_status", studentSkillStatus, "="));
	
		return skillList.getStudentSkillNum(searchList, null, student);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getObtainedSkills()
	 */
	public List getObtainedSkills() throws PlatformException {
		OracleSkillList skillList = new OracleSkillList();
		OracleTrainingStudent student = new OracleTrainingStudent();
		student.setId(this.getTrainingStudentPriv().getStudentId());
		SearchProperty search = new SearchProperty("studentskill_status",
				StudentSkillStatus.OBTAINED);
		List searchList = new ArrayList();
		searchList.add(search);
		return skillList.searchStudentSkill(null, searchList, null, student);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getStudentSkill(java.lang.String)
	 */
	public StudentSkill getStudentSkill(String skillId)
			throws PlatformException {
		OracleSkillList skillList = new OracleSkillList();
		OracleTrainingStudent student = new OracleTrainingStudent();
		student.setId(this.getTrainingStudentPriv().getStudentId());
		SearchProperty search = new SearchProperty("skill_id", skillId);
		List searchList = new ArrayList();
		searchList.add(search);
		List studentSkills = skillList.searchStudentSkill(null, searchList,
				null, student);
		StudentSkill studentSkill = null;
		for (int i = 0; i < studentSkills.size(); i++) {
			studentSkill = (StudentSkill) studentSkills.get(i);
		}
		return studentSkill;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getCourseTypeTree()
	 */
	public List getCourseTypeTree() throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		return basicList.searchCourseTypeTree(null, null);
	}

	public int getStudentCoursesNum(String courseName, String courseNote,
			String courseTeacher, String studentCourseStatus,
			String studentLearnStatus) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingStudent student = new OracleTrainingStudent();
		student.setId(this.getTrainingStudentPriv().getStudentId());
		List searchList = new ArrayList();
		if (courseName != null)
			searchList
					.add(new SearchProperty("course_name", courseName, "like"));
		if (courseNote != null)
			searchList
					.add(new SearchProperty("course_note", courseNote, "like"));
		if (studentCourseStatus != null)
			searchList.add(new SearchProperty("studentcourse_status",
					studentCourseStatus, "="));
		if (studentCourseStatus != null)
			searchList.add(new SearchProperty("studentlearn_status",
					studentLearnStatus, "="));

		// return basicList.searchStudentCourse(page, searchList, null,
		// student);
		return basicList.searchStudentCourseNum(searchList, null, student);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getStudentCourses()
	 */
	public List getStudentCourses() throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingStudent student = new OracleTrainingStudent();
		student.setId(this.getTrainingStudentPriv().getStudentId());
		return basicList.searchStudentCourse(null, null, null, student);
	}

	public List getStudentCourses(Page page, String courseName,
			String courseNote, String courseTeacher,
			String studentCourseStatus, String studentLearnStatus)
			throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingStudent student = new OracleTrainingStudent();
		student.setId(this.getTrainingStudentPriv().getStudentId());
		List searchList = new ArrayList();
		if (courseName != null)
			searchList
					.add(new SearchProperty("course_name", courseName, "like"));
		if (courseNote != null)
			searchList
					.add(new SearchProperty("course_note", courseNote, "like"));
		if (studentCourseStatus != null)
			searchList.add(new SearchProperty("studentcourse_status",
					studentCourseStatus, "="));
		if (studentCourseStatus != null)
			searchList.add(new SearchProperty("studentlearn_status",
					studentLearnStatus, "="));

		return basicList.searchStudentCourse(page, searchList, null, student);
	}

	public List getStudentCourses(String courseName, String courseNote,
			String courseTeacher, String studentCourseStatus,
			String studentLearnStatus, int limit) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingStudent student = new OracleTrainingStudent();
		student.setId(this.getTrainingStudentPriv().getStudentId());
		List searchList = new ArrayList();
		if (courseName != null)
			searchList
					.add(new SearchProperty("course_name", courseName, "like"));
		if (courseNote != null)
			searchList
					.add(new SearchProperty("course_note", courseNote, "like"));
		if (studentCourseStatus != null)
			searchList.add(new SearchProperty("studentcourse_status",
					studentCourseStatus, "="));
		if (studentCourseStatus != null)
			searchList.add(new SearchProperty("studentlearn_status",
					studentLearnStatus, "="));
		if (limit != 0)
			searchList.add(new SearchProperty("rownum", String.valueOf(limit),
					"<="));

		return basicList.searchStudentCourse(null, searchList, null, student);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getCoursewareURL(java.lang.String,
	 *      java.util.List)
	 */
	public TrainingCourseware getCourseware(String courseId)
			throws PlatformException {
		OracleTrainingCourse course = new OracleTrainingCourse(courseId);
		return course.getTrainingCourseware();
	}

	public int getStudentClassesNum(String className, String classNote,
			String studentClassStatus) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingStudent student = new OracleTrainingStudent();
		student.setId(this.getTrainingStudentPriv().getStudentId());
		List searchList = new ArrayList();
		if (className != null)
			searchList.add(new SearchProperty("class_name", className, "like"));
		if (classNote != null)
			searchList.add(new SearchProperty("class_note", classNote, "like"));
		if (studentClassStatus != null)
			searchList.add(new SearchProperty("studentclass_status",
					studentClassStatus, "="));

		// return basicList.searchStudentClass(page, searchList, null, student);
		return basicList.getStudentClassNum(searchList, null, student);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getStudentClasses()
	 */
	public List getStudentClasses() throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingStudent student = new OracleTrainingStudent();
		student.setId(this.getTrainingStudentPriv().getStudentId());
		return basicList.searchStudentClass(null, null, null, student);
	}

	public List getStudentClasses(Page page, String className,
			String classNote, String studentClassStatus, int limit)
			throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingStudent student = new OracleTrainingStudent();
		student.setId(this.getTrainingStudentPriv().getStudentId());
		List searchList = new ArrayList();
		if (className != null)
			searchList.add(new SearchProperty("class_name", className, "like"));
		if (classNote != null)
			searchList.add(new SearchProperty("class_note", classNote, "like"));
		if (studentClassStatus != null)
			searchList.add(new SearchProperty("studentclass_status",
					studentClassStatus, "="));
		if (limit != 0)
			searchList.add(new SearchProperty("rownum", String.valueOf(limit),
					"<="));
		return basicList.searchStudentClass(page, searchList, null, student);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getClassCourses(java.lang.String)
	 */
	public List getClassCourses(String classId) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingClass newClass = new OracleTrainingClass();
		newClass.setId(classId);
		return basicList.searchClassCourses(null, null, null, newClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getStudentCourses(java.lang.String,
	 *      java.lang.String)
	 */
	public StudentCourse getStudentCourse(String courseId)
			throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getAiccUserDataManagePriv()
	 */
	public AiccUserDataManagePriv getAiccUserDataManagePriv()
			throws PlatformException {
		AiccFactory aiccFactory = AiccFactory.getInstance();
		AiccUserDataManagePriv aiccUserPriv = aiccFactory
				.creatAiccUserDataManagePriv();
		aiccUserPriv.setStudentId(this.getTrainingStudentPriv().getStudentId());
		aiccUserPriv.getAUsDataOfUser = 1;
		aiccUserPriv.getCourse = 1;
		aiccUserPriv.getCourses = 1;
		aiccUserPriv.getCoursesDataOfUser = 1;
		aiccUserPriv.getObjectivesDataOfUser = 1;
		return aiccUserPriv;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getAiccUserDataManage()
	 */
	public AiccUserDataManage getAiccUserDataManage() throws PlatformException {
		AiccFactory aiccFactory = AiccFactory.getInstance();
		return aiccFactory.creatAiccUserDataManage(getAiccUserDataManagePriv());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getAiccCourseManage()
	 */
	public AiccCourseManage getAiccCourseManage() throws PlatformException {
		AiccFactory aiccFactory = AiccFactory.getInstance();
		return aiccFactory.creatAiccCourseManage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getStudentClass(java.lang.String)
	 */
	public StudentClass getStudentClass(String classId)
			throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		return trainingClass.getStudentClass(this.getTrainingStudentPriv()
				.getStudentId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getCourseType(java.lang.String)
	 */
	public TrainingCourseType getCourseType(String typeId)
			throws PlatformException {
		return new OracleTrainingCourseType(typeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getChildCourseTypes(java.lang.String)
	 */
	public List getChildCourseTypes(String parentTypeId)
			throws PlatformException {
		OracleTrainingCourseType courseType = new OracleTrainingCourseType();
		courseType.setId(parentTypeId);
		return courseType.getChildType(null, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getScormStudentPriv()
	 */

	public ScormStudentPriv getScormStudentPriv() throws PlatformException {
		ScormFactory factory = ScormFactory.getInstance();
		ScormStudentPriv studentPriv = factory.creatScormStudentPriv();
		studentPriv.getCourseData = 1;
		studentPriv.getCourse = 1;
		return studentPriv;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#getScormStudentManage()
	 */

	public ScormStudentManage getScormStudentManage() throws PlatformException {
		ScormFactory factory = ScormFactory.getInstance();
		return factory.creatScormStudentManage(getScormStudentPriv());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingStudentOperationManage#updateStudentCourseData(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public void updateStudentCourseData(String studentId, String courseId,
			String percent, String learnStatus) throws PlatformException {
		OracleTrainingStudent student = new OracleTrainingStudent();
		student.setId(studentId);
		OracleTrainingCourse course = new OracleTrainingCourse();
		course.setId(courseId);
		OracleStudentCourse studentCourse = new OracleStudentCourse(student,
				course);
		StudyProgress learnProgress = new StudyProgress();
		learnProgress.setPercent(Float.parseFloat(percent));
		learnProgress.setLearnStatus(learnStatus);
		studentCourse.setLearnProgress(learnProgress);
		studentCourse.update();
	}

	public void applyCourse(String courseId) throws PlatformException {
		// TODO Auto-generated method stub
		OracleTrainingSelectCourseActivity activity = new OracleTrainingSelectCourseActivity();
		Map map = new HashMap();
		List list = new ArrayList();
		list.add(getTrainingStudentPriv().getStudentId());
		map.put(courseId, list);

		activity.selectCourses(map);
	}

	public void unApplyCourse(String courseId) throws PlatformException {
		// TODO Auto-generated method stub
		String studentId = getTrainingStudentPriv().getStudentId();

		OracleTrainingStudent student = new OracleTrainingStudent();
		student.setId(studentId);
		OracleTrainingCourse course = new OracleTrainingCourse();
		course.setId(courseId);

		OracleStudentCourse studentCourse = new OracleStudentCourse(student,
				course);
		if (studentCourse.getStatus() != null
				&& studentCourse.getStatus().equals(
						StudentCourseStatus.UNDERCHECK)) {
			OracleTrainingSelectCourseActivity activity = new OracleTrainingSelectCourseActivity();
			Map map = new HashMap();
			List list = new ArrayList();
			list.add(studentId);
			map.put(courseId, list);

			activity.unSelectCourses(map);
		} else {
			throw new PlatformException("��ѡ���Ѿ���ȷ�ϣ��޷���ѡ!");
		}
	}

	public void applyClass(String classId) throws PlatformException {
		String studentId = getTrainingStudentPriv().getStudentId();
		OracleTrainingSelectClassActivity activity = new OracleTrainingSelectClassActivity();
		Map map = new HashMap();
		List studentList = new ArrayList();
		studentList.add(studentId);
		map.put(classId, studentList);

		activity.selectClasses(map);
	}
	
	public void unApplyClass(String classId) throws PlatformException {
		String studentId = getTrainingStudentPriv().getStudentId();
		OracleTrainingSelectClassActivity activity = new OracleTrainingSelectClassActivity();
		Map map = new HashMap();
		List studentList = new ArrayList();
		studentList.add(studentId);
		map.put(classId, studentList);

		//activity.selectClasses(map);
		activity.unSelectClasses(map);
	}

	public void applySkill(String skillId) throws PlatformException {
		String studentId = getTrainingStudentPriv().getStudentId();
		OracleTrainingSelectSkillActivity activity = new OracleTrainingSelectSkillActivity();
		Map map = new HashMap();
		List studentList = new ArrayList();
		studentList.add(studentId);
		map.put(skillId, studentList);

		activity.selectSkill(map);
	}

	public void targetSkill(String skillId) throws PlatformException {
		String studentId = getTrainingStudentPriv().getStudentId();
		OracleTrainingSelectSkillActivity activity = new OracleTrainingSelectSkillActivity();
		Map map = new HashMap();
		List studentList = new ArrayList();
		studentList.add(studentId);
		map.put(skillId, studentList);

		activity.targetSkill(map);
	}

	public void unTargetSkill(String skillId) throws PlatformException {
		String studentId = getTrainingStudentPriv().getStudentId();
		OracleTrainingSelectSkillActivity activity = new OracleTrainingSelectSkillActivity();
		Map map = new HashMap();
		List studentList = new ArrayList();
		studentList.add(studentId);
		map.put(skillId, studentList);

		activity.unTargetSkill(map);
	}

	public int updateTrainingUser(String name, String nickName, String email,
			String mobilephone, String phone) throws PlatformException {
		String userId = getTrainingStudentPriv().getStudentId();
		OracleTrainingStudent user = new OracleTrainingStudent(userId);
		if (name != null)
			user.setName(name);
		if (nickName != null)
			user.setNickName(nickName);
		if (email != null)
			user.setEmail(email);
		if (mobilephone != null)
			user.setMobilePhone(mobilephone);
		if (phone != null)
			user.setPhone(phone);

		return user.update();
	}

}
