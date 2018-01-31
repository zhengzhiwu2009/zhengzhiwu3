package com.whaty.platform.entity.bean;

import java.util.Date;

public class PeBzzExamLate extends AbstractBean implements java.io.Serializable{

	private String id;
	private Date applyDate;				//缓考申请时间
	private Date firstDate;				//缓考初审时间
	private	Date finalDate;				//缓考终审时间
	private String note;				//备注
	private PeBzzStudent	peBzzStudent;   //学生
	private PeBzzExamBatch	peBzzExamBatch;	//考试批次
	private EnumConst	enumConstByFlagExamLateStatus; //状态常量
	
	private PeEnterprise peSite;	//管理员所在的企业
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public Date getFirstDate() {
		return firstDate;
	}
	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}
	public Date getFinalDate() {
		return finalDate;
	}
	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public PeBzzStudent getPeBzzStudent() {
		return peBzzStudent;
	}
	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
	}
	public EnumConst getEnumConstByFlagExamLateStatus() {
		return enumConstByFlagExamLateStatus;
	}
	public void setEnumConstByFlagExamLateStatus(
			EnumConst enumConstByFlagExamLateStatus) {
		this.enumConstByFlagExamLateStatus = enumConstByFlagExamLateStatus;
	}
	public void setId(String id) {
		this.id = id;
	}
	public PeBzzExamBatch getPeBzzExamBatch() {
		return peBzzExamBatch;
	}
	public void setPeBzzExamBatch(PeBzzExamBatch peBzzExamBatch) {
		this.peBzzExamBatch = peBzzExamBatch;
	}
	public PeEnterprise getPeSite() {
		return peSite;
	}
	public void setPeSite(PeEnterprise peSite) {
		this.peSite = peSite;
	}
	
}
