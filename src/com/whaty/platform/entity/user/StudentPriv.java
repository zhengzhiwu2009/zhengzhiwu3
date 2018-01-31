/**
 * 
 */
package com.whaty.platform.entity.user;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public abstract class StudentPriv implements Serializable{
	/**
	 * session信息封装
	 */
	private static final long serialVersionUID = 4538822591930338135L;
	
	private String studentId;

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
		
	public int getSelectTeachClasses=1;
	
	/**
	 * �Ƿ������¼
	 */
	public int login=1;

	/**
	 * �Ƿ������޸�����
	 */
	public int updatePwd=1;
	
	/**
	 * �Ƿ������޸ĸ�����Ϣ
	 */
	public int updateInfo=1;

	
	/***************************************************************************
	 * ���Ź���Ȩ�� *
	 **************************************************************************/
	public int getNews = 1; // �Ƿ���Բ鿴����

	public int addNews = 1; // �Ƿ�����������

	public int updateNews = 1; // �Ƿ�����޸�����

	public int deleteNews = 1; // �Ƿ����ɾ������

	public int putTopNews = 1; // �Ƿ�����ö�����

	public int lockNews = 1; // �Ƿ����������

	public int getNewsType = 1; // �Ƿ���Բ鿴��������

	public int addNewsType = 1; // �Ƿ���������������

	public int updateNewsType = 1; // �Ƿ�����޸���������

	public int deleteNewsType = 1; // �Ƿ����ɾ����������

	public int setNewsTypeManagers = 1; // �Ƿ���������������͹���Ա

	public int getNewsTypeManagers = 1; // �Ƿ���Բ鿴�������͹���Ա

	/***************************************************************************
	 * ��Ҫ��Ϣ����Ȩ�� *
	 **************************************************************************/
	public int getImpnote = 1; // �Ƿ���Բ鿴��Ҫ��Ϣ

	public int addImpnote = 1; // �Ƿ���������Ҫ��Ϣ

	public int updateImpnote = 1; // �Ƿ�����޸���Ҫ��Ϣ

	public int deleteImpnote = 1; // �Ƿ����ɾ����Ҫ��Ϣ

	public int activeImpnote = 1; // �Ƿ���Է�����Ҫ��Ϣ

	/***************************************************************************
	 * ����Ϣ����Ȩ�� *
	 **************************************************************************/

	public int creatDep = 1;

	public int creatEduType = 1;

	public int creatGrade = 1;

	public int creatMajor = 1;

	public int creatSite = 1;

	public int createTemplate = 1;

	public int getDep = 1;// �Ƿ�������Ժϵ��Ϣ

	public int getEduType = 1;// �Ƿ������2����Ϣ

	public int getGrade = 1;// �Ƿ��������꼶��Ϣ

	public int getMajor = 1;// �Ƿ�������רҵ��Ϣ

	public int getSite = 1;// �Ƿ������=�ѧվ��Ϣ

	public int getSiteStudents = 1;// �Ƿ������=�ѧվ�µ�ѧ����Ϣ

	public int searchSite = 1;// �Ƿ���Բ��ҽ�ѧվ��Ϣ

	public int getTemplate = 1;

	public int getDeps = 1;// �Ƿ�������Ժϵ��Ϣ

	public int getRecruitPlans = 1;// �Ƿ�����������ƻ�

	public int getRecruitBatches = 1;// �Ƿ��������������

	public int getEduTypes = 1;// �Ƿ������2����Ϣ

	public int getGrades = 1;// �Ƿ��������꼶��Ϣ

	public int getMajors = 1;// �Ƿ�������רҵ��Ϣ

	public int getSites = 1;// �Ƿ������=�ѧվ��Ϣ

	public int getTemplates = 1;

	public int addDep = 1;// �Ƿ�������Ժϵ��Ϣ

	public int addEduType = 1;// �Ƿ������Ӳ����Ϣ

	public int addGrade = 1;// �Ƿ��������꼶��Ϣ

	public int addMajor = 1;// �Ƿ�������רҵ��Ϣ

	public int addSite = 1;// �Ƿ������ӽ�ѧվ��Ϣ

	public int addTemplate = 1;// �Ƿ�������ģ��

	public int updateDep = 1;// �Ƿ�����޸�Ժϵ��Ϣ

	public int updateRecruitBatch = 1; // �Ƿ�����޸����������Ϣ

	public int updateEduType = 1;// �Ƿ�����޸Ĳ����Ϣ

	public int updateGrade = 1;// �Ƿ�����޸��꼶��Ϣ

	public int updateMajor = 1;// �Ƿ�����޸�רҵ��Ϣ

	public int updateSite = 1;// �Ƿ�����޸Ľ�ѧվ��Ϣ

	public int updateTemplate = 1;// �Ƿ�����޸�ģ����Ϣ

	public int deleteDep = 1;// �Ƿ����ɾ��Ժϵ��Ϣ

	public int deleteRecruitBatch = 1;// �Ƿ����ɾ��Ժϵ��Ϣ

	public int deleteEduType = 1;// �Ƿ����ɾ������Ϣ

	public int deleteGrade = 1;// �Ƿ����ɾ���꼶��Ϣ

	public int deleteMajor = 1;// �Ƿ����ɾ��רҵ��Ϣ

	public int deleteSite = 1;// �Ƿ����ɾ���ѧվ��Ϣ

	public int deleteTemplate = 1;// �Ƿ����ɾ��ģ����Ϣ

	public int getRecruitCourse = 1;// �Ƿ������?��Կγ��б�

	public int deleteRecruitCourse = 1;// �Ƿ����ɾ��Ժϵ��Ϣ

	/***************************************************************************
	 * ��Ա��Ϣ����Ȩ�� *
	 **************************************************************************/

	public int creatTeacher = 1;

	public int getTeacher = 1;// �Ƿ������=�ʦ

	public int getTeachers = 1;// �Ƿ������=�ʦ

	public int addTeacher = 1;// �Ƿ������ӽ�ʦ

	public int addTeacherBatch = 1;// �Ƿ��������ӽ�ʦ

	public int updateTeacher = 1;// �Ƿ�����޸Ľ�ʦ

	public int deleteTeacher = 1;// �Ƿ����ɾ���ʦ

	public int searchTeacher = 1;// �Ƿ���Բ��ҽ�ʦ

	public int creatStudent = 1;

	public int getStudent = 1;// �Ƿ�������ѧ��

	public int getStudents = 1;// �Ƿ�������ѧ��

	public int addStudent = 1;// �Ƿ�������ѧ��

	public int updateStudent = 1;// �Ƿ�����޸�ѧ��

	public int deleteStudent = 1;// �Ƿ����ɾ��ѧ��

	public int addStudentBatch = 1;// �Ƿ���������ѧ��

	public int deleteStudentBatch = 1;// �Ƿ������ɾ��ѧ��

	public int searchStudent = 1;// �Ƿ���Բ���ѧ��

	/***************************************************************************
	 * ��ѧ��Ϣ����Ȩ�� *
	 **************************************************************************/

	public int creatCourse = 1;

	public int getCourse = 1;// �Ƿ������?γ�

	public int getCourses = 1;// �Ƿ������?γ�

	public int addCourse = 1;// �Ƿ������ӿγ�

	public int addCourseBatch = 1;// �Ƿ��������ӿγ�

	public int searchCourse = 1;// �Ƿ���Բ��ҿγ�

	public int updateCourse = 1;// �Ƿ�����޸Ŀγ�

	public int deleteCourse = 1;// �Ƿ����ɾ��γ�

	public int getCourseType = 1;// �Ƿ������?γ�����

	public int getCourseTypes = 1;// �Ƿ������?γ�����

	public int addCourseType = 1;// �Ƿ������ӿγ�����

	public int updateCourseType = 1;// �Ƿ�����޸Ŀγ�����

	public int deleteCourseType = 1;// �Ƿ����ɾ��γ�����

	public int searchCourseware = 1;// �Ƿ���Բ��ҿμ�

	public int getCourseware = 1;// �Ƿ������?μ�

	public int getCoursewares = 1;// �Ƿ������?μ�

	public int addCourseware = 1;// �Ƿ������ӿμ�

	public int updateCourseware = 1;// �Ƿ�����޸Ŀμ�

	public int deleteCourseware = 1;// �Ƿ����ɾ��μ�

	public int getCwareType = 1;// �Ƿ������?μ�����

	public int getCwareTypes = 1;// �Ƿ������?μ�����

	public int addCwareType = 1;// �Ƿ������ӿμ�����

	public int updateCwareType = 1;// �Ƿ�����޸Ŀμ�����

	public int deleteCwareType = 1;// �Ƿ����ɾ��μ�����

	public int getSemester = 1;// �Ƿ�������ѧ��

	public int activeSemester = 1;// �Ƿ���Լ���ѧ��

	public int getSemesters = 1;// �Ƿ�������ѧ��

	public int addSemester = 1;// �Ƿ�������ѧ��

	public int updateSemester = 1;// �Ƿ�����޸�ѧ��

	public int deleteSemester = 1;// �Ƿ����ɾ��ѧ��

	public int setCourseMajor = 1;

	public int getCourseMajor = 1;

	/***************************************************************************
	 * ����ֹ���Ȩ�� *
	 **************************************************************************/
	public int addRecruitBatch = 1;// �Ƿ����������

	public int addRecruitCourse = 1;// �Ƿ������ӿ��Կγ�

	public int updateRecruitStatus = 1;// �Ƿ��������

	public int updatePlanStatus = 1;// �Ƿ���˸�����ƻ�

	public int addRecruitSort = 1;// �Ƿ�������רҵ����

	public int getRecruitSorts = 1;// �Ƿ�������רҵ����

	public int getRecruitSortMajors = 1;// �Ƿ�������רҵ�������

	public int getRecruitMajorSorts = 1;// �Ƿ������?�������רҵ

	public int setRecruitSortMajors = 1;// �Ƿ��������רҵ�������

	public int getRecruitSortCourses = 1;// �Ƿ�������רҵ��������γ�

	public int setRecruitSortCourses = 1;// �Ƿ��������רҵ��������γ�

	public int getRecruitCourseSorts = 1;// �Ƿ������?γ�����רҵ����

	public int setRecruitCourseSorts = 1;// �Ƿ�������ÿγ�����רҵ����

	public int getRecruitSort = 1;// �Ƿ�������רҵ����

	public int updateRecruitSort = 1;// �Ƿ�����޸�רҵ����

	public int deleteRecruitSort = 1;// �Ƿ����ɾ��רҵ����

	public int getRecruitTestCourses = 1;// �Ƿ������?��Կγ�

	public int addRecruitTestCourses = 1;// �Ƿ������ӿ��Կγ�

	public int deleteRecruitTestCourses = 1;// �Ƿ����ɾ���Կγ�

	public int addRecruitTestCourse = 1;// �Ƿ������ӿ��Կγ�

	public int deleteRecruitTestCourse = 1;// �Ƿ����ɾ���Կγ�

	public int getRecruitTestCourse = 1;// �Ƿ������?��Կγ�

	public int getRecruitTestStudent = 1;// �Ƿ�������ѧ��

	public int getUnRecruitTestCourses = 1;// �Ƿ�������δ�趨����ʱ��Ŀ��Կγ�

	public int getUnRecruitTestCourse = 1;// �Ƿ�������δ�趨����ʱ��Ŀ��Կγ�

	public int updateRecruitStudentScore = 1;// �Ƿ�����ϱ�ѧ��ɼ�

	public int updateRecruitStudentScoreBatch = 1;// �Ƿ�������ϱ�ѧ��ɼ�

	public int getRecruitScoreStudents = 1;// �Ƿ��÷�Ϸ����ѧ������

	public int releaseStudentScore = 1;// �Ƿ���Է���ѧ��ɼ�

	public int getPassstastic = 1;// �Ƿ�������¼ȡѧ������

	public int getTotalStu = 1;// �Ƿ�������¼ȡѧ��ͳ������

	public int releaseStudentPass = 1;// �Ƿ�����Զ�¼ȡѧ��

	public int setStudentCon = 1;// �Ƿ�����Զ�¼ȡѧ��

	public int updateRecruitStudentPassstatus = 1;// �Ƿ�����ϱ�ѧ��ɼ�

	public int getRecruitStat = 1;// �Ƿ���Բ�ѯѧ��¼ȡ���

	public int getFreeRecruitStudents = 1;// �Ƿ�������ѧ������

	public int getPassRecruitStudents = 1;// �Ƿ���¼ȡѧ������

	/***************************************************************************
	 * ������̹���Ȩ�� *
	 **************************************************************************/
	public int electiveBatchBySite = 1;// �Ƿ���Խ�ѧվ��ѡ��

	public int electiveBatchByStudents = 1; // �Ƿ����ѧ����ѡ��

	public int electiveSingle = 1;// �Ƿ���Ե���ѡ��

	public int downloadElectiveInfo = 1;// �Ƿ��������ѡ����Ϣ��

	public int uploadElectiveInfo = 1;// �Ƿ��������ѡ����Ϣ��

	public int confirmElectiveInfo = 1;// �Ƿ����ȷ��ѡ��

	public int registerSingle = 1;// ����ע��ѧ��

	public int cancelRegisterSingle = 1;// ����ȡ��ע��ѧ��

	public int registerBatch = 1;// ��ע��ѧ��

	public int cancelRegisterBatch = 1;// ��ȡ��ע��ѧ��

	public int getRegisterStudents = 1;// �Ƿ���Ի����ע��ѧ����Ϣ

	public int searchRegisterStudents = 1;// �Ƿ���Բ�����ע��ѧ��

	public int openCourseBySemester = 1;// �Ƿ����ѧ�ڿ���
	
	public int getOpenCourse = 1;// �Ƿ���Ի�ȡ������Ϣ
	
	public int getOpenCourses = 1;// �Ƿ��������ȡ������Ϣ

	public int getOpenCoursesBySemester = 1;// �Ƿ�������ѧ�ڿ���γ�

	public int addExecutePlan = 1;// �Ƿ�������ִ�мƻ�

	public int getExecutePlan = 1;// �Ƿ�������ִ�мƻ�

	public int deleteExecutePlan = 1;// �Ƿ����ɾ��ִ�мƻ�

	public int addExecutePlanCourses = 1;// �Ƿ�������ִ�мƻ��γ�

	public int getExecutePlanCourses = 1;// �Ƿ�������ִ�мƻ��γ�

	public int deleteExecutePlanCourses = 1;// �Ƿ����ɾ��ִ�мƻ��γ�

	public int addTeachPlan = 1;// �Ƿ������ӽ�ѧ�ƻ�

	public int getTeachPlan = 1;// �Ƿ������=�ѧ�ƻ�

	public int deleteTeachPlan = 1;// �Ƿ����ɾ���ѧ�ƻ�

	public int updateTeachPlan = 1;// �Ƿ���Ա༭��ѧ�ƻ�

	public int addTeachPlanCourses = 1;// �Ƿ������ӽ�ѧ�ƻ��γ�

	public int getTeachPlanCourses = 1;// �Ƿ������=�ѧ�ƻ��γ�

	public int deleteTeachPlanCourses = 1;// �Ƿ����ɾ���ѧ�ƻ��γ�

	public int addSingleTeachPlanCourses = 1;// �Ƿ������Ӹ��Խ�ѧ�ƻ��γ�

	public int getSingleTeachPlanCourses = 1;// �Ƿ������8��Խ�ѧ�ƻ��γ�

	public int deleteSingleTeachPlanCourses = 1;// �Ƿ����ɾ����Խ�ѧ�ƻ��γ�

	public int updateTeachPlanCourse = 1;// �Ƿ���Ա༭��ѧ�ƻ��γ�

	public int cancelOpenCourseBySemester = 1;// �Ƿ����ȡ��ѧ�ڿ���

	public int appointTeacherForCourse = 1;// �Ƿ����Ϊ�γ��ƶ���ʦ

	public int importUsualScore = 1;// �Ƿ�����ϱ�ƽʱ�ɼ�

	public int importExamScore = 1;// �Ƿ�����ϱ����Գɼ�

	public int importExpendScore = 1;// �Ƿ�����ϱ������ɼ�

	public int importScoreSingle = 1;// �Ƿ���Ե����ϱ��ɼ�

	public int getScoreCard = 1;// �Ƿ���Բ鿴�ɼ���

	public int getSingleCourseCard = 1;// �Ƿ���Բ鿴ѧ�ڵ��Ƴɼ���

	public int modifyUsualScore = 1;// �Ƿ�����޸�ƽʱ�ɼ�

	public int confirmModifyUsualScore = 1;// �Ƿ����ȷ��ƽʱ�ɼ����޸�

	public int modifyExamScore = 1;// �Ƿ�����޸Ŀ��Գɼ�

	public int confirmModifyExamScore = 1;// �Ƿ����ȷ�Ͽ��Գɼ����޸�

	public int importScoreBatch = 1;// �Ƿ�������ϱ��ɼ�

	public int generateTotalScore = 1;// �Ƿ����������3ɼ�

	public int modifyTotalScore = 1;// �Ƿ�����޸����3ɼ�

	public int confirmModifyTotalScore = 1;// �Ƿ����ȷ�����3ɼ����޸�

	public int modifyExpendScore = 1;// �Ƿ�����޸Ĳ����ɼ�

	public int confirmModifyExpendScore = 1;// �Ƿ����ȷ�ϲ����ɼ����޸�

	public int signSingle = 1;// �Ƿ���Ե�����

	public int addSignBatch = 1;// �Ƿ������ӱ������

	public int updateSignBatch = 1;// �Ƿ�����޸ı������

	public int deleteSignBatch = 1;// �Ƿ����ɾ�������

	public int getSignBatch = 1;// �Ƿ������1������

	public int setRecruitMajor = 1;// �Ƿ�����趨����רҵ

	public int setRecruitEdutype = 1;// �Ƿ�����趨������

	public int setRecruitSite = 1;// �Ƿ�����趨����վ��

	public int recruitStudents = 1;// �Ƿ����¼ȡѧ��

	public int setRecruitCourse = 1;// �Ƿ�������������Կγ�

	public int getGraduatedStudents = 1;// �Ƿ������1�ҵѧ��

	public int checkGraduated = 1;// �Ƿ������˱�ҵ

	/***************************************************************************
	 * ������Ϣ����Ȩ�� *
	 **************************************************************************/
	public int getTestSite = 1; // �Ƿ������?���

	public int addTestSite = 1; // �Ƿ������ӿ���

	public int updateTestSite = 1; // �Ƿ�����޸Ŀ���

	public int deleteTestSite = 1; // �Ƿ����ɾ���

	public int assignTestSite = 1; // �Ƿ���Է��俼��

	public int getTestRoom = 1;// �Ƿ������?���

	public int addTestRoom = 1;// �Ƿ������ӿ���

	public int updateTestRoom = 1;// �Ƿ�����޸Ŀ���

	public int deleteTestRoom = 1;// �Ƿ����ɾ��

	public int getNormalStudents = 1; // �Ƿ�������ѧ����Ϣ

	public int getTestRoomTongji = 1; // �Ƿ���Բ鿴�����ֲ�

	public int getEdutypeMajorTestDesk = 1; // �Ƿ������2��רҵ������Ϣ

	
	
	/***************************************************************************
	 * �������Ȩ�� *
	 **************************************************************************/

	public int getAnnounce = 1; // �Ƿ���Բ鿴����

	public int addAnnounce = 0; // �Ƿ������ӹ���

	public int updateAnnounce = 0; // �Ƿ�����޸Ĺ���

	public int deleteAnnounce = 0; // �Ƿ����ɾ���

	public int getAnnounces = 1; // �Ƿ���Բ鿴����

	public int addAnnounces = 0; // �Ƿ��������ӹ���

	public int updateAnnounces = 0; // �Ƿ�������޸Ĺ���

	public int deleteAnnounces = 0; // �Ƿ������ɾ���

	/***************************************************************************
	 * ��ҵ����Ȩ�� *
	 **************************************************************************/

	public int getHomework = 1; // �Ƿ���Բ鿴��ҵ

	public int addHomework = 1; // �Ƿ���������ҵ

	public int updateHomework = 1; // �Ƿ�����޸���ҵ

	public int deleteHomework = 1; // �Ƿ����ɾ����ҵ

	public int getHomeworks = 1; // �Ƿ���Բ鿴��ҵ

	public int addHomeworks = 1; // �Ƿ�����������ҵ

	public int updateHomeworks = 1; // �Ƿ�������޸���ҵ

	public int deleteHomeworks = 1; // �Ƿ������ɾ����ҵ

	/***************************************************************************
	 * ��ҵ���9���Ȩ�� *
	 **************************************************************************/

	public int getHomeworkCheck = 1; // �Ƿ���Բ鿴��ҵ����

	public int addHomeworkCheck = 1; // �Ƿ���������ҵ����

	public int updateHomeworkCheck = 1; // �Ƿ�����޸���ҵ����

	public int deleteHomeworkCheck = 1; // �Ƿ����ɾ����ҵ����

	public int getHomeworkChecks = 1; // �Ƿ���Բ鿴��ҵ����

	public int addHomeworkChecks = 1; // �Ƿ�����������ҵ����

	public int updateHomeworkChecks = 1; // �Ƿ�������޸���ҵ����

	public int deleteHomeworkChecks = 1; // �Ƿ������ɾ����ҵ����

	/***************************************************************************
	 * �Ͻ�����ҵ����Ȩ�� *
	 **************************************************************************/

	public int getInHomework = 1; // �Ƿ���Բ鿴�Ͻ�����ҵ

	public int addInHomework = 0; // �Ƿ��������Ͻ�����ҵ

	public int updateInHomework = 0; // �Ƿ�����޸��Ͻ�����ҵ

	public int deleteInHomework = 0; // �Ƿ����ɾ���Ͻ�����ҵ

	public int getInHomeworks = 1; // �Ƿ���Բ鿴�Ͻ�����ҵ

	public int addInHomeworks = 1; // �Ƿ����������Ͻ�����ҵ

	public int updateInHomeworks = 1; // �Ƿ�������޸��Ͻ�����ҵ

	public int deleteInHomeworks = 1; // �Ƿ������ɾ���Ͻ�����ҵ

	/***************************************************************************
	 * �Ͻ�����ҵ���9���Ȩ�� *
	 **************************************************************************/

	public int getInHomeworkCheck = 1; // �Ƿ���Բ鿴�Ͻ�����ҵ����

	public int addInHomeworkCheck = 1; // �Ƿ��������Ͻ�����ҵ����

	public int updateInHomeworkCheck = 1; // �Ƿ�����޸��Ͻ�����ҵ����

	public int deleteInHomeworkCheck = 1; // �Ƿ����ɾ���Ͻ�����ҵ����

	public int getInHomeworkChecks = 1; // �Ƿ���Բ鿴�Ͻ�����ҵ����

	public int addInHomeworkChecks = 1; // �Ƿ����������Ͻ�����ҵ����

	public int updateInHomeworkChecks = 1; // �Ƿ�������޸��Ͻ�����ҵ����

	public int deleteInHomeworkChecks = 1; // �Ƿ������ɾ���Ͻ�����ҵ����

	/***************************************************************************
	 * �����������Ȩ�� *
	 **************************************************************************/

	public int getQuestion = 1; // �Ƿ���Բ鿴��������

	public int addQuestion = 1; // �Ƿ������Ӵ�������

	public int updateQuestion = 1; // �Ƿ�����޸Ĵ�������

	public int deleteQuestion = 1; // �Ƿ����ɾ���������

	public int getQuestions = 1; // �Ƿ���Բ鿴��������

	public int addQuestions = 1; // �Ƿ��������Ӵ�������

	public int updateQuestions = 1; // �Ƿ�������޸Ĵ�������

	public int deleteQuestions = 1; // �Ƿ������ɾ���������

	/***************************************************************************
	 * ���ɴ𰸹���Ȩ�� *
	 **************************************************************************/

	public int getAnswer = 1; // �Ƿ���Բ鿴���ɴ�

	public int addAnswer = 1; // �Ƿ������Ӵ��ɴ�

	public int updateAnswer = 1; // �Ƿ�����޸Ĵ��ɴ�

	public int deleteAnswer = 1; // �Ƿ����ɾ����ɴ�

	public int getAnswers = 1; // �Ƿ���Բ鿴���ɴ�

	public int addAnswers = 1; // �Ƿ��������Ӵ��ɴ�

	public int updateAnswers = 1; // �Ƿ�������޸Ĵ��ɴ�

	public int deleteAnswers = 1; // �Ƿ������ɾ����ɴ�


	/***************************************************************************
	 * ������������������Ȩ�� *
	 **************************************************************************/

	public int getEliteQuestion = 1; // �Ƿ���Բ鿴���������������

	public int addEliteQuestion = 1; // �Ƿ������ӳ��������������

	public int updateEliteQuestion = 1; // �Ƿ�����޸ĳ��������������

	public int deleteEliteQuestion = 1; // �Ƿ����ɾ��������������

	public int getEliteQuestions = 1; // �Ƿ���Բ鿴���������������

	public int addEliteQuestions = 1; // �Ƿ��������ӳ��������������

	public int updateEliteQuestions = 1; // �Ƿ�������޸ĳ��������������

	public int deleteEliteQuestions = 1; // �Ƿ������ɾ��������������	
}
