package com.whaty.platform.test.paper;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

public abstract class Paper {
	private String id = "";

	private String title = "";

	private String note = "";

	private String creatUser = "";

	private String creatDate = "";

	private String status = "";

	private String type = "";

	private String groupId = "";
	
	private String paper_fun ="";
	
	public String getPaper_fun() {
		return paper_fun;
	}

	public void setPaper_fun(String paper_fun) {
		this.paper_fun = paper_fun;
	}

	/**
	 * ���� creatDate �Ļ�ȡ������
	 * 
	 * @return ���� creatDate ��ֵ��
	 */
	public String getCreatDate() {
		return creatDate;
	}

	/**
	 * ���� creatDate �����÷�����
	 * 
	 * @param creatDate
	 *            ���� creatDate ����ֵ��
	 */
	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}

	/**
	 * ���� creatUser �Ļ�ȡ������
	 * 
	 * @return ���� creatUser ��ֵ��
	 */
	public String getCreatUser() {
		return creatUser;
	}

	/**
	 * ���� creatUser �����÷�����
	 * 
	 * @param creatUser
	 *            ���� creatUser ����ֵ��
	 */
	public void setCreatUser(String creatUser) {
		this.creatUser = creatUser;
	}

	/**
	 * ���� id �Ļ�ȡ������
	 * 
	 * @return ���� id ��ֵ��
	 */
	public String getId() {
		return id;
	}

	/**
	 * ���� id �����÷�����
	 * 
	 * @param id
	 *            ���� id ����ֵ��
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * ���� note �Ļ�ȡ������
	 * 
	 * @return ���� note ��ֵ��
	 */
	public String getNote() {
		return note;
	}

	/**
	 * ���� note �����÷�����
	 * 
	 * @param note
	 *            ���� note ����ֵ��
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * ���� status �Ļ�ȡ������
	 * 
	 * @return ���� status ��ֵ��
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * ���� status �����÷�����
	 * 
	 * @param status
	 *            ���� status ����ֵ��
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * ���� title �Ļ�ȡ������
	 * 
	 * @return ���� title ��ֵ��
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * ���� title �����÷�����
	 * 
	 * @param title
	 *            ���� title ����ֵ��
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public abstract List getPaperQuestion() throws PlatformException;

	public abstract void addPaperQuestion(List PaperQuestion)
			throws PlatformException;

	public abstract void removePaperQuestion(List PaperQuestion)
			throws PlatformException;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public abstract int removePaperQuestions() throws PlatformException;

	public abstract int removePaperQuestions(String questionIds)
			throws PlatformException;

	public abstract int setActive();

	public abstract int cancelActive();

	public abstract int reverseActive();
}
