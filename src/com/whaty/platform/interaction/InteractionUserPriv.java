package com.whaty.platform.interaction;

import java.io.Serializable;

public abstract class InteractionUserPriv implements Serializable{
	private String userId;

	private String teachclassId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getTeachclassId() {
		return teachclassId;
	}

	public void setTeachclassId(String teachclassId) {
		this.teachclassId = teachclassId;
	}	

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
	
	/**********************************************************************
	 * 考试辅导答疑
	 **********************************************************************/
     
	public int addExamQuestion = 1; // 是否可以添加问题
	
	public int getExamQuestions = 1; // 是否可以查看问题列表
	
	public int getExamQuestion = 1; // 是否可以查看问题
	
	public int getExamAnswer = 1; // 是否可以查看问题
	
	public int getExamAnswers = 1; // 是否可以查看问题列表
	
	public int addExamAnswer = 1; // 是否可以添加问题答案
	
	public int updateExamAnswer = 1; // 是否可以修改问题答案
	
	public int addExamUserQuestion = 1; // 是否可以添加关注
	
	public int deleteExamUserQuestion = 1; // 是否可以取消关注
	
	public int getExamUserQuestion = 1; // 是否可以查看关注问题
	
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
	 * ����������ɴ𰸹���Ȩ�� *
	 **************************************************************************/

	public int getEliteAnswer = 1; // �Ƿ���Բ鿴����������ɴ�

	public int addEliteAnswer = 1; // �Ƿ������ӳ���������ɴ�

	public int updateEliteAnswer = 1; // �Ƿ�����޸ĳ���������ɴ�

	public int deleteEliteAnswer = 1; // �Ƿ����ɾ���������ɴ�

	public int getEliteAnswers = 1; // �Ƿ���Բ鿴����������ɴ�

	public int addEliteAnswers = 1; // �Ƿ��������ӳ���������ɴ�

	public int updateEliteAnswers = 1; // �Ƿ�������޸ĳ���������ɴ�

	public int deleteEliteAnswers = 1; // �Ƿ������ɾ���������ɴ�

	/***************************************************************************
	 * ��������Ŀ¼����Ȩ�� *
	 **************************************************************************/

	public int getQuestionEliteDir = 1; // �Ƿ���Բ鿴��������Ŀ¼

	public int addQuestionEliteDir = 1; // �Ƿ������ӳ�������Ŀ¼

	public int updateQuestionEliteDir = 1; // �Ƿ�����޸ĳ�������Ŀ¼

	public int deleteQuestionEliteDir = 1; // �Ƿ����ɾ�������Ŀ¼

	public int getQuestionEliteDirs = 1; // �Ƿ���Բ鿴��������Ŀ¼

	public int addQuestionEliteDirs = 1; // �Ƿ��������ӳ�������Ŀ¼

	public int updateQuestionEliteDirs = 1; // �Ƿ�������޸ĳ�������Ŀ¼

	public int deleteQuestionEliteDirs = 1; // �Ƿ������ɾ�������Ŀ¼

	/***************************************************************************
	 * ֪ʶ�����Ȩ�� *
	 **************************************************************************/
	public int getLore = 1; // �Ƿ���Բ鿴֪ʶ��

	public int addLore = 1; // �Ƿ����������֪ʶ��

	public int updateLore = 1; // �Ƿ�����޸�֪ʶ��

	public int deleteLore = 1; // �Ƿ����ɾ��֪ʶ��

	public int getLores = 1; // �Ƿ���Բ鿴֪ʶ��

	public int addLores = 1; // �Ƿ���������֪ʶ��

	public int updateLores = 1; // �Ƿ�������޸�֪ʶ��

	public int deleteLores = 1; // �Ƿ������ɾ��֪ʶ��

	/***************************************************************************
	 * ֪ʶ��Ŀ¼����Ȩ�� *
	 **************************************************************************/
	public int getLoreDir = 1; // �Ƿ���Բ鿴֪ʶ��Ŀ¼

	public int addLoreDir = 1; // �Ƿ����������֪ʶ��Ŀ¼

	public int updateLoreDir = 1; // �Ƿ�����޸�֪ʶ��Ŀ¼

	public int deleteLoreDir = 1; // �Ƿ����ɾ��֪ʶ��Ŀ¼

	public int getLoreDirs = 1; // �Ƿ���Բ鿴֪ʶ��Ŀ¼

	public int addLoreDirs = 1; // �Ƿ���������֪ʶ��Ŀ¼

	public int updateLoreDirs = 1; // �Ƿ�������޸�֪ʶ��Ŀ¼

	public int deleteLoreDirs = 1; // �Ƿ������ɾ��֪ʶ��Ŀ¼

	/***************************************************************************
	 * ���������Ȩ�� *
	 **************************************************************************/
	public int getForumList = 1;

	public int getForumLists = 1;

	public int addForumList = 1; // �Ƿ���������������

	public int updateForumList = 1; // �Ƿ�����޸���������

	public int deleteForumList = 1; // �Ƿ����ɾ����������

	public int addForumLists = 1; // �Ƿ�����������������

	public int updateForumLists = 1; // �Ƿ�������޸���������

	public int deleteForumLists = 1; // �Ƿ������ɾ����������

	public int getForum = 1;

	public int getForums = 1;

	public int addForum = 1; // �Ƿ���������������

	public int updateForum = 1; // �Ƿ�����޸���������

	public int deleteForum = 1; // �Ƿ����ɾ����������

	public int addForums = 1; // �Ƿ�����������������

	public int updateForums = 1; // �Ƿ�������޸���������

	public int deleteForums = 1; // �Ƿ������ɾ����������

}
