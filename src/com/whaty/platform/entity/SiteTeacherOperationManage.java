package com.whaty.platform.entity;

import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.courseware.CoursewareManagerPriv;
import com.whaty.platform.entity.activity.OpenCourse;
import com.whaty.platform.entity.graduatedesign.Discourse;
import com.whaty.platform.entity.graduatedesign.MetaphaseCheck;
import com.whaty.platform.entity.graduatedesign.Pici;
import com.whaty.platform.entity.graduatedesign.RegBgCourse;
import com.whaty.platform.entity.graduatedesign.RejoinRequisition;
import com.whaty.platform.entity.graduatedesign.SubjectQuestionary;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.entity.user.HumanPlatformInfo;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.SiteTeacherPriv;
import com.whaty.platform.entity.user.TeacherEduInfo;
import com.whaty.platform.interaction.InteractionUserPriv;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.RedundanceData;

public abstract class SiteTeacherOperationManage {

	private SiteTeacherPriv siteTeacherPriv;

	public SiteTeacherOperationManage() {

	}

	public SiteTeacherPriv getSiteTeacherPriv() {
		return siteTeacherPriv;
	}

	public void setSiteTeacherPriv(SiteTeacherPriv siteTeacherPriv) {
		this.siteTeacherPriv = siteTeacherPriv;
	}

	public abstract InteractionUserPriv getInteractionUserPriv(String userId)
			throws PlatformException;

	public abstract InteractionUserPriv getInteractionUserPriv(String userId,
			String teachclassId) throws PlatformException;

	public abstract SiteTeacher getSiteTeacher() throws PlatformException;

	public abstract int getTeachClassesNum() throws PlatformException;

	public abstract List getTeachClasses(Page page) throws PlatformException;

	public abstract List getCourses(Page page, String id)
			throws PlatformException;

	/**
	 * ���½�ʦ
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 */
	public abstract int updateTeacher(String teacherId, String name,
			String password, String email, HumanNormalInfo normalInfo,
			TeacherEduInfo teachereduInfo, HumanPlatformInfo platformInfo,
			RedundanceData redundace) throws PlatformException;

	/**
	 * ���½�ʦ����
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 */
	public abstract int updatePwd(String id, String oldPassword,
			String newPassword) throws PlatformException;

	/**
	 * ��ȡָ����OpenCourse
	 * 
	 * @param openCourseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract OpenCourse getOpenCourse(String openCourseId)
			throws PlatformException;

	public abstract CoursewareManagerPriv getCoursewareManagerPriv();
	
	public abstract ManagerPriv getManagerPriv();
	
	
	/**
	 * ��ñ�ҵ���ͣ�����ҵ�����ģ�
	 * @return
	 */
	public abstract Map getGraduateTypes() throws PlatformException;
	
	/**
	 * ��ȡ��ǰ����
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getActiveGraduateDesignBatch()
			throws PlatformException;
	/**
	 * ���aid�õ�������ζ���
	 * 
	 * @param aid
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getGraduateDesignBatch(java.lang.String aid)
			throws PlatformException;
	
	//----------------��ҵ����ҵ���� lwx 2008-05-28
	/**
	 * ��ȡ��ǰ�����ҵ���
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getActiveGraduateExecDesignBatch()
	throws PlatformException;
	/**
	 * ���aid�õ�����ҵ��ζ���
	 * 
	 * @param aid
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getGraduateExecDesignBatch(java.lang.String aid)
	throws PlatformException;
	
	//-----------------------end-------------------
   
	 /**
	  * (��վָ��Ա�Ѿ����ȷ��)ѧԱ����Ŀ�����Ϣ��
	  * 
	  * @param page
	  * @param reg_no  ѧԱѧ��
	  * @param name   ����
	  * @param site  վ��
	  * @param grade  �꼶
	  * @param eduTypeID  ���
	  * @param majorId  רҵ
	  * @param status  ״̬λ   0Ϊδȷ��;1Ϊ��վȷ�ϣ�2Ϊ��վ���أ�3Ϊ��վȷ�ϣ�4Ϊ��վ����
	  * @return
	  * @throws PlatformException
	  */
	public abstract List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String status) throws PlatformException;

	public abstract int getSubjectQuestionaryListNum(String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String status) throws PlatformException;
	
	public abstract int changeSubjectQuestionary(String id,String status,String message)throws PlatformException;

	/**
	 * (��վָ��Ա�Ѿ����ȷ��)ѧԱ�Ŀ��εǼ���Ϣ
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRegBeginCourseList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String status) throws PlatformException;

	public abstract int getRegBeginCourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String status)
			throws PlatformException;
	public abstract int changeRegBeginCourse(String id,String status,String message)throws PlatformException;

	/**
	 * ��վָ��Ա��Ҫ��˵�ָ����ʦ�б�
	 * @param page
	 * @param teacherId
	 * @param name
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSiteTutorTeacherList(Page page, String teacherId,
			String name,String status) throws PlatformException;

	public abstract int getSiteTutorTeacherListNum(String reg_no, String name,String status)
			throws PlatformException;
	public abstract int changeSiteTutorTeacher(String id,String status,String message);
	/**
	 * ��վָ��Ա�Ѿ�ȷ�ϵ�ѧԱ���ڼ����Ϣ�б�
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getMetaphaseCheckList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String status) throws PlatformException;

	public abstract int getMetaphaseCheckListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String status)
			throws PlatformException;
	public abstract int changeMetaphaseCheck(String id,String status,String message)throws PlatformException;
	/**
	 * ��վָ��Ա�Ѿ�ȷ�ϵ�ѧԱ���������Ϣ�б�
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRejoinRequisitionList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String type,String status) throws PlatformException;

	public abstract int getRejoinRequisitionListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type,String status) throws PlatformException;
	public abstract int changeRejoinRequisition(String id,String status,String message);
	/**
	 * ����������Ϣ�б�
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getDiscourseList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String type,String status) throws PlatformException;

	public abstract int getDiscourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type,String status) throws PlatformException;
	public abstract int changeDiscourse(String id,String status,String message);
	
	public abstract SubjectQuestionary getSubjectQuestionary(String id)
			throws PlatformException;

	public abstract RegBgCourse getRegBeginCourse(String id)
			throws PlatformException;

	public abstract SiteTeacher getSiteTutorTeacher(String id)
			throws PlatformException;

	public abstract MetaphaseCheck getMetaphaseCheck(String id)
			throws PlatformException;

	public abstract RejoinRequisition getRejoinRequisition(String id)
			throws PlatformException;

	public abstract Discourse getDiscourse(String id) throws PlatformException;


	/**
	 * ��ѧվ�б�
	 * 
	 * @return ��ѧվ�б�
	 * @throws PlatformException
	 */
	public abstract List getAllSites() throws PlatformException;
	
	/**
	 * �꼶�б�
	 * 
	 * @return �꼶�б�
	 * @throws PlatformException
	 */
	public abstract List getAllGrades() throws PlatformException;
	
	
	/**
	 * ����б�
	 * 
	 * @return ����б�
	 * @throws PlatformException
	 */
	public abstract List getAllEduTypes() throws PlatformException;
	
	/**
	 * רҵ�б�
	 * 
	 * @return רҵ�б�
	 * @throws PlatformException
	 */
	public abstract List getAllMajors() throws PlatformException;


	 
	
	 /**
	  * (��վָ��Ա�Ѿ����ȷ��)ѧԱ����Ŀ�����Ϣ��
	  * 
	  * @param page
	  * @param reg_no  ѧԱѧ��
	  * @param name   ����
	  * @param site  վ��
	  * @param grade  �꼶
	  * @param eduTypeID  ���
	  * @param majorId  רҵ
	  * @param status  ״̬λ   0Ϊδȷ��;1Ϊ��վȷ�ϣ�2Ϊ��վ���أ�3Ϊ��վȷ�ϣ�4Ϊ��վ����
	  * @return
	  * @throws PlatformException
	  */
	public abstract List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String status,String teacherId) throws PlatformException;

	public abstract int getSubjectQuestionaryListNum(String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String status,String teacherId) throws PlatformException;
	
	public abstract int getSubjectQuestionaryListNum2(String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String status,String teacherId,String semesterId) throws PlatformException;

	public abstract List getSubjectQuestionaryList2(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String status,String teacherId,String semesterId) throws PlatformException;

	
	/**
	 * (��վָ��Ա�Ѿ����ȷ��)ѧԱ�Ŀ��εǼ���Ϣ
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRegBeginCourseList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String status,String teacherId,String semesterId) throws PlatformException;

	public abstract int getRegBeginCourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String status,String teacherId,String semesterId)
			throws PlatformException;

 
	/**
	 * ��վָ��Ա�Ѿ�ȷ�ϵ�ѧԱ���ڼ����Ϣ�б�
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getMetaphaseCheckList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String status,String teacherId,String semesterId) throws PlatformException;

	public abstract int getMetaphaseCheckListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String status,String teacherId,String semesterId)
			throws PlatformException;
	/**
	 * ��վָ��Ա�Ѿ�ȷ�ϵ�ѧԱ���������Ϣ�б�
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRejoinRequisitionList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String type,String status,String teacherId) throws PlatformException;

	public abstract int getRejoinRequisitionListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type,String status,String teacherId) throws PlatformException;
	/**
	 * ����������Ϣ�б�
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getDiscourseList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String type,String status,String teacherId) throws PlatformException;

	public abstract int getDiscourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type,String status,String teacherId) throws PlatformException;

	/**
	*��ѯ���걨��Ŀ״̬�ı����վָ����ʦ���ͨ���
	*/
	public abstract List getSubjectSearchStatusChangedStudent(String[] ids)throws PlatformException ;
	/**
	*��ѯ���걨��Ŀ״̬�ı����վָ����ʦ���ͨ���
	*/
	public abstract List getRegBeginCourseStatusChangedStudent(String[] ids)throws PlatformException ;
	/**
	*��ѯ���걨��Ŀ״̬�ı����վָ����ʦ���ͨ���
	*/
	public abstract List getMetaphaseCheckStatusChangedStudent(String[] ids) throws PlatformException;

	/**
	*��ѯ�����ҵ����״̬�ı����վָ����ʦ���ͨ���
	*/
	public abstract List getExerciseScoreSubmitStatusChangedStudent(String[] ids)throws PlatformException;
	/**
	*��ѯ���ҵ��Ʒ���״̬�ı����վָ����ʦ���ͨ���
	*/
	public abstract List getDiscourseScoreSubmitStatusChangedStudent(String[] ids)throws PlatformException;
	/**
	*��ѯ���걨��Ŀ״̬�ı����վָ����ʦ���ͨ���
	*/
	public abstract List getRejoinRequesStatusChangedStudent(String[] ids) throws PlatformException;
	/**
	*��ѯ���걨��Ŀ״̬�ı����վָ����ʦ���ͨ���
	*/
	public abstract List getDiscourseStatusChangedStudent(String[] ids) throws PlatformException;
	
	/**
	 * ָ��ѧ���б�
	 * 
	 * @return ѧ���б�
	 */
	public abstract List getStudents(Page page,String stu_id,String name,String gradeId,String eduTypeID,String majorId)throws PlatformException ;

	/**
	 * ���ָ��ѧ����
	 * 
	 * @return ѧ���б�
	 */
	public abstract int getStudentsNum(String stu_id,String name,String gradeId,String eduTypeID,String majorId) throws PlatformException;
	
	/**
	 * ָ��ѧ���б�
	 * 
	 * @return ѧ���б�
	 */
	public abstract List getGraduateStudents(Page page,String regNo,String name,String gradeId,String eduTypeID,String majorId) throws PlatformException;

	/**
	 * ���ָ��ѧ����
	 * 
	 * @return ѧ���б�
	 */
	public abstract int getGraduateStudentsNum(String regNo,String name,String gradeId,String eduTypeID,String majorId) throws PlatformException;

	
	/**
	 * ���ָ��ѧ��רҵ�б�
	 * 
	 * @return ѧ���б�
	 */
	public abstract List getStudentsMajors() throws PlatformException;
	
	/**
	 * �ύ��ҵ����ҵ����
	 * 
	 * @return int
	 */
	public abstract int submitExerciseScore(String subjectId,String score);
	
	/**
	 * �ύ��ҵ����ҵ����
	 * 
	 * @return int
	 */
	public abstract int submitDiscourseScore(String subjectId,String score);

	/**
	 * ��ѯѧԱ���������б�
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param site
	 * @param grade
	 * @param eduTypeID
	 * @param majorId	
	 * @param piciId	��ҵ������ID
	 * @param courseId  ��ҵ��ƿγ�ID
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String piciId,String courseId,String semesterId) throws PlatformException;
	 
	public abstract int getSubjectQuestionaryListNum(String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String piciId,String courseId,String semesterId) throws PlatformException;

	
	/**
	 * ��õ�ǰѧ��
	 */
	public abstract List getActiveSemeser() throws PlatformException;

	/**
	 * �ж��Ƿ�ɲ���
	 * */
	public abstract boolean canOperateStduent(String student_id)  throws PlatformException ;
	
	/**
	 * ��ñ�ҵ����б�
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getGraduatePici() throws PlatformException;

	/**
	 * ��ô���ҵ���
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getGraduateExecPici() throws PlatformException;

}


