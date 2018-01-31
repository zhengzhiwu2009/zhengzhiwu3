/*
 * TeacherPriv.java
 *
 * Created on 2005��4��6��, ����5:20
 */

package com.whaty.platform.entity.user;

import java.io.Serializable;

/**
 * 
 * @author Administrator
 */
public abstract class SiteTeacherPriv implements Serializable{

	private String teacherId;

	private String siteId;

	public String getTeacherId() {
		return teacherId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public int getTeachClasses = 1;

	public int getTeacher = 1;

	public int getTeachers = 1;

	public int updateTeacher = 1;

	public int updatePwd = 1;

	public int getCourses = 1;

	public int getCourseware = 1;

	public int getCoursewareType = 1;

	public int getCoursewareTypes = 1;

	public int addCourseware = 1;

	public int enterclass = 1;

	public int getOpenCourse = 1;

	public int getOpenCourses = 1;

	/***************************************************************************
	 * �������Ȩ�� *
	 **************************************************************************/

	public int getAnnounce = 1; // �Ƿ���Բ鿴����

	public int addAnnounce = 1; // �Ƿ������ӹ���

	public int updateAnnounce = 1; // �Ƿ�����޸Ĺ���

	public int deleteAnnounce = 1; // �Ƿ����ɾ���

	public int getAnnounces = 1; // �Ƿ���Բ鿴����

	public int addAnnounces = 1; // �Ƿ��������ӹ���

	public int updateAnnounces = 1; // �Ƿ�������޸Ĺ���

	public int deleteAnnounces = 1; // �Ƿ������ɾ���

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

	public int addInHomework = 1; // �Ƿ��������Ͻ�����ҵ

	public int updateInHomework = 1; // �Ƿ�����޸��Ͻ�����ҵ

	public int deleteInHomework = 1; // �Ƿ����ɾ���Ͻ�����ҵ

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

	/***************************************************************************
	 * ��ҵ������ҵ����ҵ���Ȩ�� lwx 2008-06-15
	 **************************************************************************/
	public int getGraduateDesignPici = 1; //�Ƿ������1�ҵ������
	
	public int getGraduateDesignPiciExec = 1; //�Ƿ������1�ҵ����ҵ���
	
	public int getGradeEdutypeMajor = 1;  //�Ƿ���Ի���꼶���רҵ
	
	public int getSubjectQuestionary = 1; //�Ƿ������1�ҵ����ҵ
	
	public int getStudentFreeApply = 1; //�Ƿ������1�ҵ��Ƶ���������;
	
	public int getStudentFreeApplyExec = 1; //�Ƿ������1�ҵ����ҵ����������;
	
	public int getRegBeginCourse = 1;  //�Ƿ������1�ҵ��ƿ��ⱨ��;
	
	public int getMetaphaseCheck = 1; //�Ƿ������1�ҵ������ڼ����Ϣ;
	
	public int getRejoinRequisition = 1; //�Ƿ�Ҫ����1�ҵ��ƴ�������;
	
	public int getDiscourse = 1; //�Ƿ������1�ҵ����ո�;
	
	public int getSiteTutorTeacher = 1; //�Ƿ�Ҫ����7�վָ����ʦ;
	
	public int getGradeEduTypeMajorForTeacher = 1; //�Ƿ���������վָ����ʦָ�����꼶רҵ���;
	
	public int getGraduateSiteTeacherMajors = 1; //�Ƿ������7�վָ����ʦ������רҵ;
	
	public int changeSubjectQuestionary  = 1; //�Ƿ�����޸ı�ҵ����ҵ״̬;
	
	public int changeRegBeginCourse = 1; // �Ƿ�����޸ı�ҵ��ƿ��ⱨ��״̬;
	
	public int changeMetaphaseCheck = 1; //�Ƿ�����޸ı�ҵ������ڵ�״̬;
	
}
