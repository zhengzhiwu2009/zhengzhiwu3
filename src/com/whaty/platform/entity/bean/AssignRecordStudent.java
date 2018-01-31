package com.whaty.platform.entity.bean;

public class AssignRecordStudent extends com.whaty.platform.entity.bean.AbstractBean implements java.io.Serializable {

	private String id;
	private PeBzzStudent student;
	private PeEnterpriseManager manager;
	private AssignRecord assignRecord;
	private String classNum; // 分配的学时数（单个）
	private String subAmount;// 被操作人当前余额;

	public AssignRecordStudent() {
	}

	public AssignRecordStudent(String id, PeBzzStudent student, AssignRecord assignRecord) {
		this.id = id;
		this.student = student;
		this.assignRecord = assignRecord;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeBzzStudent getStudent() {
		return student;
	}

	public void setStudent(PeBzzStudent student) {
		this.student = student;
	}

	public AssignRecord getAssignRecord() {
		return assignRecord;
	}

	public void setAssignRecord(AssignRecord assignRecord) {
		this.assignRecord = assignRecord;
	}

	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public PeEnterpriseManager getManager() {
		return manager;
	}

	public void setManager(PeEnterpriseManager manager) {
		this.manager = manager;
	}

	public String getSubAmount() {
		return subAmount;
	}

	public void setSubAmount(String subAmount) {
		this.subAmount = subAmount;
	}

}
