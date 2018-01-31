/**
 * 
 */
package com.whaty.platform.entity.activity;

import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author Administrator
 * 
 */
public abstract class ElectiveActivity {

	/**
	 * ��������map������ѧ����γ̵���Ϣ ��map��studentΪ���ѧ��ѡ��Ľ�ѧ��Id�б�Ϊֵ
	 * 
	 */
	private Map studentTeachClassMap;

	public Map getStudentTeachClassMap() {
		return studentTeachClassMap;
	}

	public void setStudentTeachClassMap(Map studentTeachClassMap) {
		this.studentTeachClassMap = studentTeachClassMap;
	}

	/**
	 * �÷���ΪstudentTeachClassMap�е�ѧ��ѡ�ж�Ӧ�Ľ�ѧ���б�
	 * 
	 * @throws PlatformException
	 */
	public abstract void selectTeachClass() throws PlatformException;

	/**
	 * �÷���ΪstudentTeachClassMap�е�ѧ����ѡ��Ӧ�Ľ�ѧ���б�
	 * 
	 * @return TODO
	 * @throws PlatformException
	 */
	public abstract int unSelectTeachClass() throws PlatformException;

	public abstract void electiveCoursesByUserId(String[] idSet,
			String semester_id, String student_id);

	public abstract int checkElectiveByFee(String regNo, String ssoUserId);

	/**
	 * ���ս�ѧվ��רҵ���꼶�������ѡ��,ͬʱ����ѡ��ȷ�ϲ��۷�
	 * ���Ҫ��ĳ�ſ�,���Խ�selectIds�óɿ�,allIdsΪҪ�˿γ̵�teachclass_id
	 * @param idSet
	 * @param semester_id
	 * @param site_id
	 * @param edu_type_id
	 * @param major_id
	 * @param grade_id
	 * @throws PlatformException
	 */
	public abstract void electiveWithFee(String[] selectIds, String[] allIds,
			String semester_id, String site_id, String edu_type_id,
			String major_id, String grade_id) throws PlatformException;

	/**
	 * ���ѧ��ѡ��,ͬʱ����ѡ��ȷ�ϲ��۷�
	 * ���Ҫ��ĳ�ſ�,���Խ�selectIds�óɿ�,allIdsΪҪ�˿γ̵�teachclass_id
	 * 
	 * @param idSet
	 * @param semester_id
	 * @param student_id
	 * @throws PlatformException
	 */
	public abstract void electiveWithFee(String[] selectIds, String[] allIds,
			String semester_id, String student_id) throws PlatformException;

	/**
	 * ���ѧ�����ѡ��,δ����ѡ��ȷ�Ϲʲ��۷�
	 * ���Ҫ��ĳ�ſ�,���Խ�selectIds�óɿ�,allIdsΪҪ�˿γ̵�teachclass_id
	 * @param selectIds
	 * @param allIds
	 * @param semester_id
	 * @param student_id
	 * @throws PlatformException
	 */
	public abstract void electiveWithoutFee(String[] selectIds,
			String[] allIds, String semester_id, String student_id)
			throws PlatformException;

	/**
	 * ȷ��ѧ��ѡ�β��۷�
	 * 
	 * @param idSet
	 * @param semester_id
	 * @param student_id
	 * @throws PlatformException
	 */
	public abstract int confirmElective(String[] selectIds,	String semester_id, String student_id) throws PlatformException;
	/**
	 * ȡ��ȷ��ѧ��ѡ�β��˷�
	 * 
	 * @param idSet
	 * @param semester_id
	 * @param student_id
	 * @throws PlatformException
	 */
	public abstract int unConfirmElective(String[] selectIds, String semester_id, String student_id) throws PlatformException;
}
