/**
 * 
 */
package com.whaty.platform.database.oracle.standard.training;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.leaveword.user.OracleLeaveWordManagerPriv;
import com.whaty.platform.database.oracle.standard.training.activity.OracleTrainingBasicActivity;
import com.whaty.platform.database.oracle.standard.training.activity.OracleTrainingSelectClassActivity;
import com.whaty.platform.database.oracle.standard.training.activity.OracleTrainingSelectCourseActivity;
import com.whaty.platform.database.oracle.standard.training.activity.OracleTrainingSelectSkillActivity;
import com.whaty.platform.database.oracle.standard.training.basic.OracleTrainingBasicList;
import com.whaty.platform.database.oracle.standard.training.basic.OracleTrainingClass;
import com.whaty.platform.database.oracle.standard.training.basic.OracleTrainingCourse;
import com.whaty.platform.database.oracle.standard.training.basic.OracleTrainingCourseType;
import com.whaty.platform.database.oracle.standard.training.skill.OracleSkill;
import com.whaty.platform.database.oracle.standard.training.skill.OracleSkillChain;
import com.whaty.platform.database.oracle.standard.training.skill.OracleSkillList;
import com.whaty.platform.database.oracle.standard.training.user.OracleTrainingClassManager;
import com.whaty.platform.database.oracle.standard.training.user.OracleTrainingManager;
import com.whaty.platform.database.oracle.standard.training.user.OracleTrainingStudent;
import com.whaty.platform.database.oracle.standard.training.user.OracleTrainingUserList;
import com.whaty.platform.leaveword.LeaveWordFactory;
import com.whaty.platform.leaveword.LeaveWordManage;
import com.whaty.platform.leaveword.user.LeaveWordManagerPriv;
import com.whaty.platform.standard.aicc.Exception.AiccException;
import com.whaty.platform.standard.aicc.operation.AiccCourseManage;
import com.whaty.platform.standard.aicc.operation.AiccFactory;
import com.whaty.platform.standard.scorm.operation.ScormManage;
import com.whaty.platform.training.TrainingManage;
import com.whaty.platform.training.basic.StudentClassStatus;
import com.whaty.platform.training.basic.StudentCourseStatus;
import com.whaty.platform.training.basic.TrainingClass;
import com.whaty.platform.training.basic.TrainingCourse;
import com.whaty.platform.training.basic.TrainingCourseType;
import com.whaty.platform.training.basic.courseware.AiccTrainingCourseware;
import com.whaty.platform.training.basic.courseware.Scorm12TrainingCourseware;
import com.whaty.platform.training.skill.Skill;
import com.whaty.platform.training.skill.SkillChain;
import com.whaty.platform.training.user.TrainingClassManager;
import com.whaty.platform.training.user.TrainingStudent;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.TrainingLog;
import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.string.StrManage;
import com.whaty.util.string.StrManageFactory;

/**
 * @author chenjian
 * 
 */
public class OracleTrainingManage extends TrainingManage {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#addTrainingCourseType(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public void addTrainingCourseType(String name, String status, String note,
			String parentId) throws PlatformException {
		OracleTrainingCourseType courseType = new OracleTrainingCourseType();
		courseType.setName(name);
		if (status != null && status.equals("1"))
			courseType.setActive(true);
		else
			courseType.setActive(false);
		courseType.setNote(note);
		OracleTrainingCourseType parentType = new OracleTrainingCourseType();
		parentType.setId(parentId);
		courseType.setParentType(parentType);
		courseType.add();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#updateTrainingCourseType(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public void updateTrainingCourseType(String id, String name, String note,
			String status) throws PlatformException {
		OracleTrainingCourseType courseType = new OracleTrainingCourseType(id);
		courseType.setName(name);
		if (status != null && status.equals("1"))
			courseType.setActive(true);
		else
			courseType.setActive(false);
		courseType.setNote(note);
		courseType.update();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#activeTrainingCourseType(java.lang.String)
	 */
	public void activeTrainingCourseType(List typeIds, boolean flag)
			throws PlatformException {
		OracleTrainingBasicActivity basicActivity = new OracleTrainingBasicActivity();
		basicActivity.activeTrainingCourseType(typeIds, flag);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#moveTrainingCourseType(java.lang.String,
	 *      java.lang.String)
	 */
	public void moveTrainingCourseType(String id, String parentId)
			throws PlatformException {
		OracleTrainingCourseType courseType = new OracleTrainingCourseType(id);
		OracleTrainingCourseType parentType = null;
		parentType = new OracleTrainingCourseType(parentId);
		courseType.moveTo(parentType);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#deleteTrainingCourseType(java.util.List)
	 */
	public void deleteTrainingCourseType(List ids) throws PlatformException {
		OracleTrainingCourseType courseType = new OracleTrainingCourseType();
		for (int i = 0; i < ids.size(); i++) {
			courseType.setId((String) ids.get(i));
			courseType.delete();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getTrainingCourseTypes(java.lang.String)
	 */
	public List getTrainingCourseTypes(String parentId)
			throws PlatformException {
		if (parentId == null || parentId.length() < 1)
			parentId = "root";
		OracleTrainingCourseType courseType = new OracleTrainingCourseType();
		courseType.setId(parentId);
		return courseType.getChildType(null, null);
	}

	public List getTrainingCourseTypes(String parentId, String field,
			String order) throws PlatformException {
		if (parentId == null || parentId.length() < 1)
			parentId = "root";
		OracleTrainingCourseType courseType = new OracleTrainingCourseType();
		courseType.setId(parentId);

		List orderList = new ArrayList();
		orderList.add(new OrderProperty(field, order));
		return courseType.getChildType(null, orderList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getTrainingCourseType(java.lang.String)
	 */
	public TrainingCourseType getTrainingCourseType(String id)
			throws PlatformException {
		if (id == null || id.length() < 1 || id.equalsIgnoreCase("root")) {
			OracleTrainingCourseType courseType = new OracleTrainingCourseType();
			courseType.setId("root");
			courseType.setName("root");
			return courseType;

		} else
			return new OracleTrainingCourseType(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getAllCourses()
	 */
	public List getAllCourses(String typeId) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		SearchProperty search = new SearchProperty("course_typeid", typeId);
		List searchList = new ArrayList();
		searchList.add(search);
		return basicList.searchCourse(null, searchList, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getTrainingCourse(java.lang.String)
	 */
	public TrainingCourse getTrainingCourse(String id) throws PlatformException {
		return new OracleTrainingCourse(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#deleteTrainingCourse(java.lang.String)
	 */
	public void deleteTrainingCourse(String id) throws PlatformException {
		OracleTrainingCourse course = new OracleTrainingCourse(id);
		course.delete();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#addTrainingCourse(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void addTrainingCourse(String id, String name, String credit,
			String studyTime, String status, String typeId, String note)
			throws PlatformException {
		OracleTrainingCourse course = new OracleTrainingCourse();
		course.setId(id);
		course.setName(name);
		if (status.equals("1")) {
			course.setActive(true);
		} else {
			course.setActive(false);
		}
		if (credit != null)
			course.setCredit(Float.parseFloat(credit));
		if (studyTime != null)
			course.setStudyTime(Float.parseFloat(studyTime));
		OracleTrainingCourseType courseType = new OracleTrainingCourseType();
		courseType.setId(typeId);
		course.setTrainingCourseType(courseType);
		course.setNote(note);
		course.add();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#updateTrainingCourse(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void updateTrainingCourse(String id, String name, String credit,
			String studyTime, String status, String typeId, String note)
			throws PlatformException {
		OracleTrainingCourse course = new OracleTrainingCourse(id);
		course.setName(name);
		if (status.equals("1")) {
			course.setActive(true);
		} else {
			course.setActive(false);
		}
		course.setCredit(Float.parseFloat(credit));
		course.setStudyTime(Float.parseFloat(studyTime));
		OracleTrainingCourseType courseType = new OracleTrainingCourseType();
		courseType.setId(typeId);
		course.setTrainingCourseType(courseType);
		course.setNote(note);
		course.update();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#moveToOtherType(java.lang.String,
	 *      java.lang.String)
	 */
	public void moveToOtherType(String courseId, String typeId)
			throws PlatformException {
		OracleTrainingCourse course = new OracleTrainingCourse(courseId);
		OracleTrainingCourseType courseType = new OracleTrainingCourseType();
		courseType.setId(typeId);
		course.setTrainingCourseType(courseType);
		course.update();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getAllCoursesNum()
	 */
	public int getAllCoursesNum() throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		// return basicList.searchCourse(null, null, null);
		return basicList.searchCourseNum(null, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getAllCourses()
	 */
	public List getAllCourses() throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		return basicList.searchCourse(null, null, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getAllTrainingClassNum()
	 */
	public int getAllTrainingClassNum() throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		return basicList.getClassNum(null, null, null);
	}

	public int getTrainingClassNum(String classId, String className)
			throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();

		List searchList = new ArrayList();
		if (classId != null && classId.length() > 0)
			searchList.add(new SearchProperty("id", classId, "like"));
		if (className != null && className.length() > 0)
			searchList.add(new SearchProperty("name", className, "like"));

		return basicList.getClassNum(null, searchList, null);
	}

	public List getTrainingClass(Page page, String classId, String className)
			throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();

		List searchList = new ArrayList();
		if (classId != null && classId.length() > 0)
			searchList.add(new SearchProperty("id", classId, "like"));
		if (className != null && className.length() > 0)
			searchList.add(new SearchProperty("name", className, "like"));

		return basicList.searchClass(page, searchList, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getAllTrainingClass()
	 */
	public List getAllTrainingClass() throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		return basicList.searchClass(null, null, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#addTrainingClass(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public void addTrainingClass(String name, String status, String startDate,
			String endDate, String startSelectDate, String endSelectDate,
			String note) throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass();
		trainingClass.setName(name);
		if (status != null && status.equals("1"))
			trainingClass.setActive(true);
		else
			trainingClass.setActive(false);
		StrManage strManage = StrManageFactory.creat();
		try {
			trainingClass.setStartDate(strManage.strToDate(startDate, null));
			trainingClass.setStartElectCourse(strManage.strToDate(
					startSelectDate, null));
			trainingClass.setEndDate(strManage.strToDate(endDate, null));
			trainingClass.setEndElectCourse(strManage.strToDate(endSelectDate,
					null));
		} catch (WhatyUtilException e) {
			throw new PlatformException(
					"error in addTrainingClass with the error of "
							+ e.getMessage());
		}
		trainingClass.setNote(note);
		trainingClass.add();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#updateTrainingClass(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void updateTrainingClass(String id, String name, String status,
			String startDate, String endDate, String startSelectDate,
			String endSelectDate, String note) throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(id);
		trainingClass.setName(name);
		if (status != null && status.equals("1"))
			trainingClass.setActive(true);
		else
			trainingClass.setActive(false);
		StrManage strManage = StrManageFactory.creat();
		try {
			trainingClass.setStartDate(strManage.strToDate(startDate, null));
			trainingClass.setStartElectCourse(strManage.strToDate(
					startSelectDate, null));
			trainingClass.setEndDate(strManage.strToDate(endDate, null));
			trainingClass.setEndElectCourse(strManage.strToDate(endSelectDate,
					null));
		} catch (WhatyUtilException e) {
			throw new PlatformException(
					"error in addTrainingClass with the error of "
							+ e.getMessage());
		}

		trainingClass.setNote(note);
		trainingClass.update();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#deleteTrainingClass(java.util.List)
	 */
	public void deleteTrainingClass(List ids) throws PlatformException {

		OracleTrainingClass trainingClass = new OracleTrainingClass();
		for (int i = 0; i < ids.size(); i++) {
			trainingClass.setId((String) ids.get(i));
			trainingClass.delete();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#addClassManagers(java.util.List)
	 */
	public void addClassManagers(String classId, List chiefIds)
			throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		trainingClass.addClassManagers(chiefIds);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#removeClassManagers(java.util.List)
	 */
	public void removeClassManagers(String classId, List chiefIds)
			throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		trainingClass.removeClassManagers(chiefIds);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#addClassCourses(java.lang.String,
	 *      java.util.List)
	 */
	public void addClassCourses(String classId, List courseIds)
			throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		trainingClass.addTrainingCourse(courseIds);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#removeClassCourses(java.lang.String,
	 *      java.util.List)
	 */
	public void removeClassCourses(String classId, List courseIds)
			throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		trainingClass.removeTrainingCourse(courseIds);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#addClassStudents(java.lang.String,
	 *      java.util.List)
	 */
	public void addClassStudents(String classId, List studentIds)
			throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		trainingClass.addTrainingStudent(studentIds);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#removeClassStudents(java.lang.String,
	 *      java.util.List)
	 */
	public void removeClassStudents(String classId, List studentIds)
			throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		trainingClass.removeTrainingStudent(studentIds);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#checkClassStudents(java.lang.String,
	 *      java.util.List)
	 */
	public void checkClassStudents(String classId, List studentIds)
			throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		trainingClass.checkTrainingStudent(studentIds);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getAllSkillChain()
	 */
	public List getAllSkillChain() throws PlatformException {
		OracleSkillList skillList = new OracleSkillList();
		return skillList.searchSkillChain(null, null, null);

	}

	public int getSkillChainNum(String chainName, String chainNote)
			throws PlatformException {
		OracleSkillList skillList = new OracleSkillList();
		List searchList = new ArrayList();
		if (chainName != null)
			searchList.add(new SearchProperty("name", chainName, "like"));
		if (chainNote != null)
			searchList.add(new SearchProperty("note", chainNote, "like"));

		// return skillList.searchSkillChain(null, null, null);
		return skillList.getSkillChainNum(searchList, null);

	}

	public List getSkillChain(Page page, String chainName, String chainNote,
			String field, String order) throws PlatformException {
		OracleSkillList skillList = new OracleSkillList();
		List searchList = new ArrayList();
		if (chainName != null)
			searchList.add(new SearchProperty("name", chainName, "like"));
		if (chainNote != null)
			searchList.add(new SearchProperty("note", chainNote, "like"));

		List orderList = new ArrayList();
		if (field != null)
			orderList.add(new OrderProperty(field, order));

		return skillList.searchSkillChain(page, searchList, orderList);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#addSkillChain(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void addSkillChain(String name, String status, String note)
			throws PlatformException {
		OracleSkillChain skillChain = new OracleSkillChain();
		skillChain.setName(name);
		if (status != null && status.equals("1"))
			skillChain.setActive(true);
		else
			skillChain.setActive(false);
		skillChain.setNote(note);
		skillChain.add();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#updateSkillChain(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public void updateSkillChain(String id, String name, String status,
			String note) throws PlatformException {
		OracleSkillChain skillChain = new OracleSkillChain(id);
		skillChain.setName(name);
		if (status != null && status.equals("1"))
			skillChain.setActive(true);
		else
			skillChain.setActive(false);
		skillChain.setNote(note);
		skillChain.update();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#deleteSkillChain(java.util.List)
	 */
	public void deleteSkillChain(List idList) throws PlatformException {
		if (idList.size() > 0) {
			OracleSkillChain skillChain = new OracleSkillChain();
			for (int i = 0; i < idList.size(); i++) {
				skillChain.setId((String) idList.get(i));
				skillChain.delete();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getSkillChain(java.lang.String)
	 */
	public SkillChain getSkillChain(String chainId) throws PlatformException {
		// TODO Auto-generated method stub
		return new OracleSkillChain(chainId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getChainSkills(java.lang.String)
	 */
	public List getChainSkills(String chainId) throws PlatformException {
		OracleSkillChain skillChain = new OracleSkillChain(chainId);
		return skillChain.getSkillList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#addSkill()
	 */
	public void addSkill(String name, String status, String note, String chainId)
			throws PlatformException {
		OracleSkill skill = new OracleSkill();
		if (status != null && status.equals("1"))
			skill.setActive(true);
		else
			skill.setActive(false);
		skill.setName(name);
		skill.setNote(note);
		if (chainId != null) {
			OracleSkillChain skillChain = new OracleSkillChain();
			skillChain.setId(chainId);
			skill.setChain(skillChain);
		}
		skill.add();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#updateSkill()
	 */
	public void updateSkill(String id, String name, String status, String note,
			String chainId) throws PlatformException {
		OracleSkill skill = new OracleSkill(id);
		if (status != null && status.equals("1"))
			skill.setActive(true);
		else
			skill.setActive(false);
		skill.setName(name);
		skill.setNote(note);
		if (chainId != null) {
			OracleSkillChain skillChain = new OracleSkillChain();
			skillChain.setId(chainId);
			skill.setChain(skillChain);
		}
		skill.update();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#deleteSkill()
	 */
	public void deleteSkill(String id) throws PlatformException {
		OracleSkill skill = new OracleSkill(id);
		skill.delete();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#addChainSkills(java.util.List,
	 *      java.lang.String)
	 */
	public void addChainSkills(List skills, String skillChainId)
			throws PlatformException {
		OracleSkillChain skillChain = new OracleSkillChain(skillChainId);
		skillChain.addSkills(skills);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#removeChainSkills(java.util.List,
	 *      java.lang.String)
	 */
	public void removeChainSkills(List skills, String skillChainId)
			throws PlatformException {
		OracleSkillChain skillChain = new OracleSkillChain(skillChainId);
		skillChain.removeSkills(skills);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#addSkillCourses(java.util.List,
	 *      java.lang.String)
	 */
	public void addSkillCourses(List courses, String skillId)
			throws PlatformException {
		OracleSkill skill = new OracleSkill(skillId);
		skill.addCourse(courses);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#removeSkillCourses(java.util.List,
	 *      java.lang.String)
	 */
	public void removeSkillCourses(List courses, String skillId)
			throws PlatformException {
		OracleSkill skill = new OracleSkill(skillId);
		skill.removeCourse(courses);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getPreSkills(java.lang.String)
	 */
	public List getPreSkills(String skillId) throws PlatformException {
		OracleSkill skill = new OracleSkill(skillId);
		return skill.getPreSkillList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#addPreSkill(java.util.List)
	 */
	public void addPreSkill(List skills, String skillId)
			throws PlatformException {
		OracleSkill skill = new OracleSkill(skillId);
		skill.addPreSkill(skills);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#removePreSkill(java.util.List)
	 */
	public void removePreSkill(List skills, String skillId)
			throws PlatformException {
		OracleSkill skill = new OracleSkill(skillId);
		skill.removePreSkill(skills);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#addCourseStudent(java.util.List,
	 *      java.lang.String)
	 */
	public void addCourseStudent(List studentIds, String courseId)
			throws PlatformException {
		Map studentCourses = new HashMap();
		studentCourses.put(courseId, studentIds);
		OracleTrainingSelectCourseActivity activity = new OracleTrainingSelectCourseActivity();
		activity.selectCourses(studentCourses);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#removeCourseStudent(java.util.List,
	 *      java.lang.String)
	 */
	public void removeCourseStudent(List studentIds, String courseId)
			throws PlatformException {
		Map studentCourses = new HashMap();
		studentCourses.put(courseId, studentIds);
		OracleTrainingSelectCourseActivity activity = new OracleTrainingSelectCourseActivity();
		activity.unSelectCourses(studentCourses);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#addTrainingCourseware(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public void addTrainingCourseware(String courseId, String coursewareId,
			String coursewareType) throws PlatformException {
		OracleTrainingCourse course = new OracleTrainingCourse(courseId);
		course.setCoursewareId(coursewareId);
		course.setCoursewareType(coursewareType);
		course.update();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#removeTrainingCourseware(java.lang.String,
	 *      java.lang.String)
	 */
	public void removeTrainingCourseware(String courseId, String coursewareId)
			throws PlatformException {
		OracleTrainingCourse course = new OracleTrainingCourse(courseId);
		course.setCoursewareId("");
		course.setCoursewareType("");
		course.update();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getAllTrainingStudent()
	 */
	public List getAllTrainingStudent() throws PlatformException {
		OracleTrainingUserList userList = new OracleTrainingUserList();
		return userList.searchTrainingStudents(null, null, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#addTrainingStudent(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public void addTrainingStudent(String id, String name, String nickName,
			String email) throws PlatformException {
		OracleTrainingStudent student = new OracleTrainingStudent();
		student.setId(id);
		student.setName(name);
		student.setNickName(nickName);
		student.setEmail(email);
		student.add();

	}

	public void addTrainingStudent(String id, String name, String nickName,
			String email, String mobilePhone, String active, String note)
			throws PlatformException {
		OracleTrainingStudent student = new OracleTrainingStudent();
		student.setId(id);
		student.setName(name);
		student.setNickName(nickName);
		student.setEmail(email);
		student.setMobilePhone(mobilePhone);
		if (active.equals("1"))
			student.setActive(true);
		else
			student.setActive(false);
		student.setNote(note);
		student.add();

	}

	public void addTrainingManager(String id, String name, String nickName,
			String email, String note) throws PlatformException {
		OracleTrainingManager manager = new OracleTrainingManager();
		manager.setId(id);
		manager.setName(name);
		manager.setNickName(nickName);
		manager.setEmail(email);
		manager.setNote(note);
		manager.add();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#updateTrainingStudent(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public void updateTrainingStudent(String id, String name, String nickName,
			String email) throws PlatformException {
		OracleTrainingStudent student = new OracleTrainingStudent(id);
		student.setName(name);
		student.setNickName(nickName);
		student.setEmail(email);
		student.update();

	}

	public void updateTrainingStudent(String id, String name, String nickName,
			String email, String mobilePhone) throws PlatformException {
		OracleTrainingStudent student = new OracleTrainingStudent(id);
		student.setName(name);
		student.setNickName(nickName);
		student.setEmail(email);
		student.setMobilePhone(mobilePhone);
		student.update();

	}

	public void updateTrainingStudent(String id, String name, String nickName,
			String email, String mobilePhone, int status)
			throws PlatformException {
		OracleTrainingStudent student = new OracleTrainingStudent(id);
		student.setName(name);
		student.setNickName(nickName);
		student.setEmail(email);
		student.setMobilePhone(mobilePhone);
		if (status == 1)
			student.setActive(true);
		else
			student.setActive(false);

		student.update();

	}

	public void updateTrainingStudent(String id, String name, String nickName,
			String email, String mobilePhone, int status, String note)
			throws PlatformException {
		OracleTrainingStudent student = new OracleTrainingStudent(id);
		student.setName(name);
		student.setNickName(nickName);
		student.setEmail(email);
		student.setMobilePhone(mobilePhone);
		if (status == 1)
			student.setActive(true);
		else
			student.setActive(false);

		student.setNote(note);

		student.update();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#deleteTrainingStudent(java.lang.String)
	 */
	public void deleteTrainingStudent(String id) throws PlatformException {
		OracleTrainingStudent student = new OracleTrainingStudent(id);
		student.delete();

	}

	public void deleteTrainingManager(List managerIdList)
			throws PlatformException {
		dbpool db = new dbpool();
		String id_str = "";
		for (int i = 0; i < managerIdList.size(); i++) {
			String tmp_id = (String) managerIdList.get(i);
			id_str = id_str + tmp_id + ",";
		}
		if (id_str.length() > 0)
			id_str = id_str.substring(0, id_str.length() - 1);
		String sql = "delete from training_chief where id in (" + id_str + ")";
		TrainingLog.setDebug(sql);
		db.executeUpdate(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#checkCourseStudent(java.util.List,
	 *      java.lang.String)
	 */
	public void checkCourseStudent(List studentIds, String courseId)
			throws PlatformException {
		Map studentCourses = new HashMap();
		studentCourses.put(courseId, studentIds);
		OracleTrainingSelectCourseActivity activity = new OracleTrainingSelectCourseActivity();
		activity.checkSelectCourses(studentCourses);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#targetSkillStudent(java.util.List,
	 *      java.lang.String)
	 */
	public void targetSkillStudent(List studentIds, String skillId)
			throws PlatformException {
		OracleSkill skill = new OracleSkill(skillId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#unTargetSkillStudent(java.util.List,
	 *      java.lang.String)
	 */
	public void unTargetSkillStudent(List studentIds, String skillId)
			throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#rewardSkillStudent(java.util.List,
	 *      java.lang.String)
	 */
	public void rewardSkillStudent(List studentIds, String skillId)
			throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#unRewardSkillStudent(java.util.List,
	 *      java.lang.String)
	 */
	public void unRewardSkillStudent(List studentIds, String skillId)
			throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getAllSkillsNum()
	 */
	public int getAllSkillsNum() throws PlatformException {
		OracleSkillList skillList = new OracleSkillList();
		return skillList.getSkillNum(null, null, null);
	}

	public int getSkillsNum(String skill_id, String skill_name)
			throws PlatformException {
		OracleSkillList skillList = new OracleSkillList();
		List searchList = new ArrayList();
		if (skill_id != null)
			searchList.add(new SearchProperty("skill_id", skill_id, "like"));
		if (skill_name != null)
			searchList
					.add(new SearchProperty("skill_name", skill_name, "like"));
		return skillList.getSkillNum(null, searchList, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getAllSkills()
	 */
	public List getAllSkills() throws PlatformException {
		OracleSkillList skillList = new OracleSkillList();
		return skillList.searchSkill(null, null, null);
	}

	public List getSkills(Page page, String skill_id, String skill_name)
			throws PlatformException {
		OracleSkillList skillList = new OracleSkillList();
		List searchList = new ArrayList();
		if (skill_id != null)
			searchList.add(new SearchProperty("skill_id", skill_id, "like"));
		if (skill_name != null)
			searchList
					.add(new SearchProperty("skill_name", skill_name, "like"));

		return skillList.searchSkill(page, searchList, null);
	}

	public List getSkills(Page page, String skill_id, String skill_name,
			String field, String order) throws PlatformException {
		OracleSkillList skillList = new OracleSkillList();
		List searchList = new ArrayList();
		if (skill_id != null)
			searchList.add(new SearchProperty("skill_id", skill_id, "like"));
		if (skill_name != null)
			searchList
					.add(new SearchProperty("skill_name", skill_name, "like"));

		List orderList = new ArrayList();
		if (field != null)
			orderList.add(new OrderProperty(field, order));

		return skillList.searchSkill(page, searchList, orderList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getClassCoursesNum(java.lang.String)
	 */
	public int getClassCoursesNum(String classId) throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		return trainingClass.getTrainingCourseNum();
	}

	public int getClassCoursesNum(String classId, String course_id,
			String course_name) throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		return trainingClass.getTrainingCourseNum(course_id, course_name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getClassCourses(java.lang.String)
	 */
	public List getClassCourses(String classId) throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		return trainingClass.getTrainingCourse();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getClassStudentsNum(java.lang.String)
	 */
	public int getCheckedClassStudentsNum(String classId)
			throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);

		return trainingClass.getTrainingStudentsNum();
	}

	public int getCheckedClassStudentsNum(String classId, String student_id,
			String student_name) throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);

		return trainingClass.getTrainingStudentsNum(student_id, student_name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getClassStudents(java.lang.String)
	 */
	public List getCheckedClassStudents(String classId)
			throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		return trainingClass.getTrainingStudents();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getCourseCheckedStudents(java.lang.String)
	 */
	public int getCourseCheckedStudentsNum(String courseId)
			throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingCourse course = new OracleTrainingCourse(courseId);
		SearchProperty search = new SearchProperty("studentcourse_status",
				StudentCourseStatus.SELECTED);
		List searchproperty = new ArrayList();
		searchproperty.add(search);

		return basicList.searchCourseStudentsNum(searchproperty, course);
	}

	public int getCourseCheckedStudentsNum(String courseId, String student_id,
			String student_name) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingCourse course = new OracleTrainingCourse(courseId);
		SearchProperty search = new SearchProperty("studentcourse_status",
				StudentCourseStatus.SELECTED);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		if (student_id != null)
			searchproperty.add(new SearchProperty("student_id", student_id,
					"like"));
		if (student_name != null)
			searchproperty.add(new SearchProperty("student_name", student_name,
					"like"));

		return basicList.searchCourseStudentsNum(searchproperty, course);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getCourseCheckedStudents(java.lang.String)
	 */
	public List getCourseCheckedStudents(String courseId)
			throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingCourse course = new OracleTrainingCourse(courseId);
		SearchProperty search = new SearchProperty("studentcourse_status",
				StudentCourseStatus.SELECTED);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		return basicList.searchCourseStudents(null, searchproperty, null,
				course);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getCourseCheckedStudents(Page,
	 *      java.lang.String)
	 */
	public List getCourseCheckedStudents(Page page, String courseId)
			throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingCourse course = new OracleTrainingCourse(courseId);
		SearchProperty search = new SearchProperty("studentcourse_status",
				StudentCourseStatus.SELECTED);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		return basicList.searchCourseStudents(page, searchproperty, null,
				course);
	}

	public List getCourseCheckedStudents(Page page, String courseId,
			String student_id, String student_name) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingCourse course = new OracleTrainingCourse(courseId);
		SearchProperty search = new SearchProperty("studentcourse_status",
				StudentCourseStatus.SELECTED);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		if (student_id != null)
			searchproperty.add(new SearchProperty("student_id", student_id,
					"like"));
		if (student_name != null)
			searchproperty.add(new SearchProperty("student_name", student_name,
					"like"));
		return basicList.searchCourseStudents(page, searchproperty, null,
				course);
	}

	public List getCourseCheckedStudents(Page page, String courseId,
			String student_id, String student_name, String field, String order)
			throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingCourse course = new OracleTrainingCourse(courseId);
		SearchProperty search = new SearchProperty("studentcourse_status",
				StudentCourseStatus.SELECTED);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		if (student_id != null)
			searchproperty.add(new SearchProperty("student_id", student_id,
					"like"));
		if (student_name != null)
			searchproperty.add(new SearchProperty("student_name", student_name,
					"like"));

		List orderList = new ArrayList();
		orderList.add(new OrderProperty(field, order));
		return basicList.searchCourseStudents(page, searchproperty, orderList,
				course);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getCourseApplyededStudentsNum(java.lang.String)
	 */
	public int getCourseApplyededStudentsNum(String courseId)
			throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingCourse course = new OracleTrainingCourse(courseId);
		SearchProperty search = new SearchProperty("studentcourse_status",
				StudentCourseStatus.UNDERCHECK);
		List searchproperty = new ArrayList();
		searchproperty.add(search);

		return basicList.searchCourseStudentsNum(searchproperty, course);
	}

	public int getCourseApplyededStudentsNum(String courseId,
			String student_id, String student_name) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingCourse course = new OracleTrainingCourse(courseId);
		SearchProperty search = new SearchProperty("studentcourse_status",
				StudentCourseStatus.UNDERCHECK);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		if (student_id != null)
			searchproperty.add(new SearchProperty("student_id", student_id,
					"like"));
		if (student_name != null)
			searchproperty.add(new SearchProperty("student_name", student_name,
					"like"));

		return basicList.searchCourseStudentsNum(searchproperty, course);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getCourseApplyededStudents(java.lang.String)
	 */
	public List getCourseApplyededStudents(String courseId)
			throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingCourse course = new OracleTrainingCourse(courseId);
		SearchProperty search = new SearchProperty("studentcourse_status",
				StudentCourseStatus.UNDERCHECK);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		return basicList.searchCourseStudents(null, searchproperty, null,
				course);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getCourseApplyededStudents(java.lang.String)
	 */
	public List getCourseApplyededStudents(Page page, String courseId)
			throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingCourse course = new OracleTrainingCourse(courseId);
		SearchProperty search = new SearchProperty("studentcourse_status",
				StudentCourseStatus.UNDERCHECK);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		return basicList.searchCourseStudents(page, searchproperty, null,
				course);
	}

	public List getCourseApplyededStudents(Page page, String courseId,
			String student_id, String student_name) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingCourse course = new OracleTrainingCourse(courseId);
		SearchProperty search = new SearchProperty("studentcourse_status",
				StudentCourseStatus.UNDERCHECK);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		if (student_id != null)
			searchproperty.add(new SearchProperty("student_id", student_id,
					"like"));
		if (student_name != null)
			searchproperty.add(new SearchProperty("student_name", student_name,
					"like"));

		return basicList.searchCourseStudents(page, searchproperty, null,
				course);
	}

	public List getCourseApplyededStudents(Page page, String courseId,
			String student_id, String student_name, String field, String order)
			throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingCourse course = new OracleTrainingCourse(courseId);
		SearchProperty search = new SearchProperty("studentcourse_status",
				StudentCourseStatus.UNDERCHECK);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		if (student_id != null)
			searchproperty.add(new SearchProperty("student_id", student_id,
					"like"));
		if (student_name != null)
			searchproperty.add(new SearchProperty("student_name", student_name,
					"like"));

		List orderList = new ArrayList();
		orderList.add(new OrderProperty(field, order));

		return basicList.searchCourseStudents(page, searchproperty, orderList,
				course);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getTrainingClass(java.lang.String)
	 */
	public TrainingClass getTrainingClass(String classId)
			throws PlatformException {
		return new OracleTrainingClass(classId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getTrainingCourseTypeTree()
	 */
	public List getTrainingCourseTypeTree() throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		return basicList.searchCourseTypeTree(null, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#deleteTrainingCourses(java.util.List)
	 */
	public void deleteTrainingCourses(List ids) throws PlatformException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#activeTrainingClass(boolean)
	 */
	public void activeTrainingClass(List classIds, boolean flag)
			throws PlatformException {
		OracleTrainingBasicActivity basicActivity = new OracleTrainingBasicActivity();
		basicActivity.activeTrainingClass(classIds, flag);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#activeTrainingCourse(java.lang.String,
	 *      boolean)
	 */
	public void activeTrainingCourse(List courseIds, boolean flag)
			throws PlatformException {
		OracleTrainingBasicActivity basicActivity = new OracleTrainingBasicActivity();
		basicActivity.activeTrainingCourse(courseIds, flag);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#searchCourses(com.whaty.platform.util.Page,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public List searchCourses(Page page, String courseId, String courseName,
			String courseTypeId, String active) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		List searchList = new ArrayList();
		if (courseId != null && courseId.length() > 0)
			searchList.add(new SearchProperty("id", courseId, "like"));
		if (courseName != null && courseName.length() > 0)
			searchList.add(new SearchProperty("name", courseName, "like"));
		if (courseTypeId != null && courseTypeId.length() > 0)
			searchList.add(new SearchProperty("course_typeid", courseTypeId,
					"="));
		if (active != null && active.length() > 0)
			searchList
					.add(new SearchProperty("substr(status,1,1)", active, "="));
		return basicList.searchCourse(page, searchList, null);
	}

	public List searchCourses(Page page, String courseId, String courseName,
			String courseTypeId, String active, String field, String order)
			throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		List searchList = new ArrayList();
		if (courseId != null && courseId.length() > 0)
			searchList.add(new SearchProperty("id", courseId, "like"));
		if (courseName != null && courseName.length() > 0)
			searchList.add(new SearchProperty("name", courseName, "like"));
		if (courseTypeId != null && courseTypeId.length() > 0)
			searchList.add(new SearchProperty("course_typeid", courseTypeId,
					"="));
		if (active != null && active.length() > 0)
			searchList
					.add(new SearchProperty("substr(status,1,1)", active, "="));

		List orderList = new ArrayList();
		orderList.add(new OrderProperty(field, order));

		return basicList.searchCourse(page, searchList, orderList);
	}

	public int getCoursesNum(String courseId, String courseName,
			String courseTypeId, String active) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		List searchList = new ArrayList();
		if (courseId != null && courseId.length() > 0)
			searchList.add(new SearchProperty("id", courseId, "like"));
		if (courseName != null && courseName.length() > 0)
			searchList.add(new SearchProperty("name", courseName, "like"));
		if (courseTypeId != null && courseTypeId.length() > 0)
			searchList.add(new SearchProperty("course_typeid", courseTypeId,
					"="));
		if (active != null && active.length() > 0)
			searchList
					.add(new SearchProperty("substr(status,1,1)", active, "="));
		return basicList.searchCourseNum(searchList, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getTrainingStudent(java.lang.String)
	 */
	public TrainingStudent getTrainingStudent(String studentId)
			throws PlatformException {
		return new OracleTrainingStudent(studentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#searchTrainingStudents(com.whaty.platform.util.Page,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public List searchTrainingStudents(Page page, String studentId,
			String studentName, String studentNickName, String checked)
			throws PlatformException {
		OracleTrainingUserList userList = new OracleTrainingUserList();
		List searchList = new ArrayList();
		if (studentId != null && studentId.length() > 0)
			searchList.add(new SearchProperty("id", studentId, "like"));
		if (studentName != null && studentName.length() > 0)
			searchList.add(new SearchProperty("name", studentName, "like"));
		if (studentNickName != null && studentNickName.length() > 0)
			searchList.add(new SearchProperty("nick_name", studentNickName,
					"like"));
		if (checked != null && checked.length() > 0)
			searchList.add(new SearchProperty("status", checked, "=num"));
		return userList.searchTrainingStudents(page, searchList, null);
	}

	public List searchTrainingStudents(Page page, String studentId,
			String studentName, String studentNickName, String checked,
			String field, String order) throws PlatformException {
		OracleTrainingUserList userList = new OracleTrainingUserList();
		List searchList = new ArrayList();
		if (studentId != null && studentId.length() > 0)
			searchList.add(new SearchProperty("id", studentId, "like"));
		if (studentName != null && studentName.length() > 0)
			searchList.add(new SearchProperty("name", studentName, "like"));
		if (studentNickName != null && studentNickName.length() > 0)
			searchList.add(new SearchProperty("nick_name", studentNickName,
					"like"));
		if (checked != null && checked.length() > 0)
			searchList.add(new SearchProperty("status", checked, "=num"));

		List orderList = new ArrayList();
		orderList.add(new OrderProperty(field, order));

		return userList.searchTrainingStudents(page, searchList, orderList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getTrainingStudentsNum(
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public int getTrainingStudentsNum(String studentId, String studentName,
			String studentNickName, String checked) throws PlatformException {
		OracleTrainingUserList userList = new OracleTrainingUserList();
		List searchList = new ArrayList();
		if (studentId != null && studentId.length() > 0)
			searchList.add(new SearchProperty("id", studentId, "like"));
		if (studentName != null && studentName.length() > 0)
			searchList.add(new SearchProperty("name", studentName, "like"));
		if (studentNickName != null && studentNickName.length() > 0)
			searchList.add(new SearchProperty("nick_name", studentNickName,
					"like"));
		if (checked != null && checked.length() > 0)
			searchList.add(new SearchProperty("status", checked, "=num"));

		return userList.getTrainingStudentsNum(searchList, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getUnCheckedClassStudentsNum(java.lang.String)
	 */
	public int getUnCheckedClassStudentsNum(String classId)
			throws PlatformException {
		List spList = new ArrayList();
		SearchProperty sp = new SearchProperty();
		sp.setField("status");
		sp.setRelation("=");
		sp.setValue(StudentClassStatus.UNDERCHECK);
		spList.add(sp);

		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		// return basicList.searchClassStudents(null, spList, null,
		// new OracleTrainingClass(classId));
		return basicList.searchClassStudentsNum(spList,
				new OracleTrainingClass(classId));
	}

	public int getUnCheckedClassStudentsNum(String classId, String student_id,
			String student_name) throws PlatformException {
		List spList = new ArrayList();
		SearchProperty sp = new SearchProperty();
		sp.setField("status");
		sp.setRelation("=");
		sp.setValue(StudentClassStatus.UNDERCHECK);
		spList.add(sp);
		if (student_id != null)
			spList.add(new SearchProperty("student_id", student_id, "like"));
		if (student_name != null)
			spList
					.add(new SearchProperty("student_name", student_name,
							"like"));

		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		// return basicList.searchClassStudents(null, spList, null,
		// new OracleTrainingClass(classId));
		return basicList.searchClassStudentsNum(spList,
				new OracleTrainingClass(classId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getUnCheckedClassStudents(java.lang.String)
	 */
	public List getUnCheckedClassStudents(String classId)
			throws PlatformException {
		List spList = new ArrayList();
		SearchProperty sp = new SearchProperty();
		sp.setField("status");
		sp.setRelation("=");
		sp.setValue(StudentClassStatus.UNDERCHECK);
		spList.add(sp);

		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		return basicList.searchClassStudents(null, spList, null,
				new OracleTrainingClass(classId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getUnCheckedClassStudents(Page,
	 *      java.lang.String)
	 */
	public List getUnCheckedClassStudents(Page page, String classId)
			throws PlatformException {
		List spList = new ArrayList();
		SearchProperty sp = new SearchProperty();
		sp.setField("status");
		sp.setRelation("=");
		sp.setValue(StudentClassStatus.UNDERCHECK);
		spList.add(sp);

		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		return basicList.searchClassStudents(page, spList, null,
				new OracleTrainingClass(classId));
	}

	public List getUnCheckedClassStudents(Page page, String classId,
			String student_id, String student_name) throws PlatformException {
		List spList = new ArrayList();
		SearchProperty sp = new SearchProperty();
		sp.setField("status");
		sp.setRelation("=");
		sp.setValue(StudentClassStatus.UNDERCHECK);
		spList.add(sp);

		if (student_id != null)
			spList.add(new SearchProperty("student_id", student_id, "like"));
		if (student_name != null)
			spList
					.add(new SearchProperty("student_name", student_name,
							"like"));

		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		return basicList.searchClassStudents(page, spList, null,
				new OracleTrainingClass(classId));
	}

	public List getUnCheckedClassStudents(Page page, String classId,
			String student_id, String student_name, String field, String order)
			throws PlatformException {
		List spList = new ArrayList();
		SearchProperty sp = new SearchProperty();
		sp.setField("status");
		sp.setRelation("=");
		sp.setValue(StudentClassStatus.UNDERCHECK);
		spList.add(sp);

		if (student_id != null)
			spList.add(new SearchProperty("student_id", student_id, "like"));
		if (student_name != null)
			spList
					.add(new SearchProperty("student_name", student_name,
							"like"));

		List orderList = new ArrayList();
		orderList.add(new OrderProperty(field, order));

		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		return basicList.searchClassStudents(page, spList, orderList,
				new OracleTrainingClass(classId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getSkill(java.lang.String)
	 */
	public Skill getSkill(String skillId) throws PlatformException {
		return new OracleSkill(skillId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#activeSkill(java.util.List,
	 *      boolean)
	 */
	public void activeSkill(List skillIds, boolean flag)
			throws PlatformException {
		OracleTrainingBasicActivity basicActivity = new OracleTrainingBasicActivity();
		basicActivity.activeSkill(skillIds, flag);

	}

	public void activeSkillChain(List skillChainIds, boolean flag)
			throws PlatformException {
		OracleTrainingBasicActivity basicActivity = new OracleTrainingBasicActivity();
		basicActivity.activeSkillChain(skillChainIds, flag);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#deleteSkills(java.util.List)
	 */
	public void deleteSkills(List ids) throws PlatformException {
		if (ids.size() > 0) {
			OracleSkill skill = new OracleSkill();
			for (int i = 0; i < ids.size(); i++) {
				skill.setId((String) ids.get(i));
				skill.delete();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getMoveTrainingCourseTypeTree(java.lang.String)
	 */
	public List getMoveTrainingCourseTypeTree(String courseTypeId)
			throws PlatformException {
		OracleTrainingCourseType courseType = new OracleTrainingCourseType(
				courseTypeId);
		return courseType.getTypeTreeCanMove();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getAiccCourseware(java.lang.String)
	 */
	public AiccTrainingCourseware getAiccCourseware(String courseId)
			throws PlatformException {
		OracleTrainingCourse course = new OracleTrainingCourse(courseId);
		try {
			return new AiccTrainingCourseware(course.getCoursewareId());
		} catch (AiccException e) {
			throw new PlatformException(e.toString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getAiccCourseManage()
	 */
	public AiccCourseManage getAiccCourseManage() throws PlatformException {
		AiccFactory aiccFactory = AiccFactory.getInstance();
		return aiccFactory.creatAiccCourseManage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getTrainingClassManager(java.lang.String)
	 */
	public TrainingClassManager getTrainingClassManager(String classManagerId)
			throws PlatformException {
		return new OracleTrainingClassManager(classManagerId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#searchTrainingClassManagers(com.whaty.platform.util.Page,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public List searchTrainingClassManagers(Page page, String id, String name,
			String classId) throws PlatformException {
		OracleTrainingUserList userList = new OracleTrainingUserList();
		List searchList = new ArrayList();
		List orderList = new ArrayList();
		searchList.add(new SearchProperty("id", id));
		searchList.add(new SearchProperty("name", name));
		searchList.add(new SearchProperty("class_id", classId));
		orderList.add(new OrderProperty("id"));
		return userList.searchTrainingClassManager(page, searchList, orderList);
	}

	/** ******************************************** */
	public int getTrainingClassManagersNum(String managerId, String managerName)
			throws PlatformException {
		OracleTrainingUserList userList = new OracleTrainingUserList();
		List orderList = new ArrayList();
		orderList.add(new OrderProperty("id"));
		List searchList = new ArrayList();
		if (managerId != null)
			searchList.add(new SearchProperty("id", managerId, "like"));
		if (managerName != null)
			searchList.add(new SearchProperty("name", managerName, "like"));
		// return userList.searchTrainingClassManager(page, null, null);
		return userList.getTrainingClassManagerNum(searchList, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getAllTrainingClassManagers(com.whaty.platform.util.Page)
	 */
	public List getAllTrainingClassManagers(Page page) throws PlatformException {
		OracleTrainingUserList userList = new OracleTrainingUserList();
		List orderList = new ArrayList();
		orderList.add(new OrderProperty("id"));
		return userList.searchTrainingClassManager(page, null, null);
	}

	public List getAllTrainingClassManagers(Page page, String managerId,
			String managerName) throws PlatformException {
		OracleTrainingUserList userList = new OracleTrainingUserList();
		List orderList = new ArrayList();
		orderList.add(new OrderProperty("id"));
		List searchList = new ArrayList();
		if (managerId != null)
			searchList.add(new SearchProperty("id", managerId, "like"));
		if (managerName != null)
			searchList.add(new SearchProperty("name", managerName, "like"));
		return userList.searchTrainingClassManager(page, searchList, null);
	}

	public List getAllTrainingClassManagers(Page page, String managerId,
			String managerName, String field, String order)
			throws PlatformException {
		OracleTrainingUserList userList = new OracleTrainingUserList();
		// List orderList = new ArrayList();
		// orderList.add(new OrderProperty("id"));
		List searchList = new ArrayList();
		if (managerId != null)
			searchList.add(new SearchProperty("id", managerId, "like"));
		if (managerName != null)
			searchList.add(new SearchProperty("name", managerName, "like"));

		List orderList = new ArrayList();
		orderList.add(new OrderProperty(field, order));

		return userList.searchTrainingClassManager(page, searchList, orderList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#activeTrainingStudent(java.util.List,
	 *      boolean)
	 */
	public void activeTrainingStudent(List studentIds, boolean flag)
			throws PlatformException {
		for (int i = 0; i < studentIds.size(); i++) {
			String studentId = (String) studentIds.get(i);
			OracleTrainingStudent stu = new OracleTrainingStudent(studentId);
			stu.setActive(flag);
			stu.update();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#searchSkill(com.whaty.platform.util.Page,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public List searchSkill(Page page, String skillId, String skillName,
			String flag) throws PlatformException {
		OracleSkillList skillList = new OracleSkillList();
		List searchList = new ArrayList();
		if (skillId != null && skillId.length() > 0)
			searchList.add(new SearchProperty("skill_id", skillId, "like"));
		if (skillName != null && skillName.length() > 0)
			searchList.add(new SearchProperty("skill_name", skillName, "like"));
		if (flag != null && flag.length() > 0)
			searchList.add(new SearchProperty("skill_status", flag, "="));
		return skillList.searchSkill(page, searchList, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getScorm12Courseware(java.lang.String)
	 */
	public Scorm12TrainingCourseware getScorm12Courseware(String courseId)
			throws PlatformException {
		OracleTrainingCourse course = new OracleTrainingCourse(courseId);
		return new Scorm12TrainingCourseware(course.getCoursewareCode());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingManage#getScormManage()
	 */
	public ScormManage getScormManage() throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	public void unApplyCourseStudent(String courseId, List studentList)
			throws PlatformException {
		OracleTrainingSelectCourseActivity activity = new OracleTrainingSelectCourseActivity();
		Map map = new HashMap();

		map.put(courseId, studentList);

		activity.unSelectCourses(map);

	}

	public void unCheckCourseStudent(String courseId, List studentList)
			throws PlatformException {
		OracleTrainingSelectCourseActivity activity = new OracleTrainingSelectCourseActivity();
		Map map = new HashMap();

		map.put(courseId, studentList);

		activity.unCheckSelectCourses(map);
	}

	public void unCheckClassStudent(String classId, List studentList)
			throws PlatformException {
		OracleTrainingSelectClassActivity activity = new OracleTrainingSelectClassActivity();
		Map map = new HashMap();

		map.put(classId, studentList);

		activity.unCheckSelectClasses(map);
	}

	public void unApplyClassStudent(String classId, List studentList)
			throws PlatformException {
		OracleTrainingSelectClassActivity activity = new OracleTrainingSelectClassActivity();
		Map map = new HashMap();
		map.put(classId, studentList);
		activity.unSelectClasses(map);
	}

	public boolean isSkillCourseExisted(String skillId, String courseId)
			throws PlatformException {
		OracleSkill skill = new OracleSkill(skillId);
		List courseList = skill.getCourseList();
		for (int i = 0; i < courseList.size(); i++) {
			TrainingCourse course = (TrainingCourse) courseList.get(i);
			if (course.getId().equals(courseId))
				return true;
		}
		return false;
	}

	public void removeSkillStudents(List students, String skillId)
			throws PlatformException {
		OracleSkill skill = new OracleSkill(skillId);
		skill.removeStudent(students);
	}

	public void checkSkillStudent(List studentIds, String skillId)
			throws PlatformException {
		Map map = new HashMap();
		map.put(skillId, studentIds);
		OracleTrainingSelectSkillActivity activity = new OracleTrainingSelectSkillActivity();
		activity.checkSelectSkill(map);
	}

	public void unCheckSkillStudent(String skillId, List studentList)
			throws PlatformException {
		OracleTrainingSelectSkillActivity activity = new OracleTrainingSelectSkillActivity();
		Map map = new HashMap();
		map.put(skillId, studentList);
		activity.unCheckSelectSkill(map);
	}

	public List getClassCourses(Page page, String classId)
			throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		List searchList = new ArrayList();

		return basicList.searchClassCourses(page, searchList, null,
				trainingClass);
	}

	public List getClassCourses(Page page, String classId, String course_id,
			String course_name) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		List searchList = new ArrayList();
		if (course_id != null)
			searchList.add(new SearchProperty("course_id", course_id, "like"));
		if (course_name != null)
			searchList.add(new SearchProperty("course_name", course_name,
					"like"));

		return basicList.searchClassCourses(page, searchList, null,
				trainingClass);
	}

	public List getClassCourses(Page page, String classId, String course_id,
			String course_name, String field, String order)
			throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		List searchList = new ArrayList();
		if (course_id != null)
			searchList.add(new SearchProperty("course_id", course_id, "like"));
		if (course_name != null)
			searchList.add(new SearchProperty("course_name", course_name,
					"like"));

		List orderList = new ArrayList();
		orderList.add(new OrderProperty(field, order));

		return basicList.searchClassCourses(page, searchList, orderList,
				trainingClass);
	}

	public List getCheckedClassStudents(Page page, String classId)
			throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("status",
				StudentClassStatus.SELECTED, "="));
		return basicList.searchClassStudents(page, searchProperty, null,
				trainingClass);
	}

	public List getCheckedClassStudents(Page page, String classId,
			String student_id, String student_name) throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("status",
				StudentClassStatus.SELECTED, "="));
		if (student_id != null)
			searchProperty.add(new SearchProperty("student_id", student_id,
					"like"));
		if (student_name != null)
			searchProperty.add(new SearchProperty("student_name", student_name,
					"like"));
		return basicList.searchClassStudents(page, searchProperty, null,
				trainingClass);
	}

	public List getCheckedClassStudents(Page page, String classId,
			String student_id, String student_name, String field, String order)
			throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("status",
				StudentClassStatus.SELECTED, "="));
		if (student_id != null)
			searchProperty.add(new SearchProperty("student_id", student_id,
					"like"));
		if (student_name != null)
			searchProperty.add(new SearchProperty("student_name", student_name,
					"like"));

		List orderList = new ArrayList();
		orderList.add(new OrderProperty(field, order));

		return basicList.searchClassStudents(page, searchProperty, orderList,
				trainingClass);
	}

	public List getAllTrainingClass(Page page) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		return basicList.searchClass(page, null, null);
	}

	public List getAllTrainingClass(Page page, String field, String order)
			throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		List orderList = new ArrayList();
		orderList.add(new OrderProperty(field, order));
		return basicList.searchClass(page, null, orderList);
	}

	public List getAllSkills(Page page) throws PlatformException {
		OracleSkillList skillList = new OracleSkillList();
		return skillList.searchSkill(page, null, null);
	}

	public void updateTrainingClassManager(String id, String name,
			String nickName, String email, String note, String mobilePhone)
			throws PlatformException {
		OracleTrainingClassManager manager = new OracleTrainingClassManager(id);
		manager.setName(name);
		manager.setNickName(nickName);
		manager.setEmail(email);
		manager.setMobilePhone(mobilePhone);
		manager.setNote(note);

		manager.update();
	}

	public LeaveWordManagerPriv getLeaveWordManagerPriv(String id)
			throws PlatformException {
		return new OracleLeaveWordManagerPriv(id);
	}

	public LeaveWordManage getLeaveWordManage(LeaveWordManagerPriv priv)
			throws PlatformException {
		if (priv == null)
			priv = new OracleLeaveWordManagerPriv("");
		LeaveWordFactory factory = LeaveWordFactory.getInstance();
		return factory.creatLeaveWordManage(priv);
	}
}
