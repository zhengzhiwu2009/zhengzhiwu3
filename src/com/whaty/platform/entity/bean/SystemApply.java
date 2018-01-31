package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * SystemApply entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SystemApply extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private EnumConst enumConstByApplyType;
	private EnumConst enumConstByFlagApplyStatus;
	private PeStudent peStudent;
	private Date applyDate;
	private Date checkDate;
	private String checkNote;
	private String applyNote;
	private String applyInfo;

	// Constructors

	/** default constructor */
	public SystemApply() {
	}

	/** full constructor */
	public SystemApply(EnumConst enumConstByApplyType,
			EnumConst enumConstByFlagApplyStatus, PeStudent peStudent,
			Date applyDate, Date checkDate, String checkNote, String applyNote,
			String applyInfo) {
		this.enumConstByApplyType = enumConstByApplyType;
		this.enumConstByFlagApplyStatus = enumConstByFlagApplyStatus;
		this.peStudent = peStudent;
		this.applyDate = applyDate;
		this.checkDate = checkDate;
		this.checkNote = checkNote;
		this.applyNote = applyNote;
		this.applyInfo = applyInfo;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConstByApplyType() {
		return this.enumConstByApplyType;
	}

	public void setEnumConstByApplyType(EnumConst enumConstByApplyType) {
		this.enumConstByApplyType = enumConstByApplyType;
	}

	public EnumConst getEnumConstByFlagApplyStatus() {
		return this.enumConstByFlagApplyStatus;
	}

	public void setEnumConstByFlagApplyStatus(
			EnumConst enumConstByFlagApplyStatus) {
		this.enumConstByFlagApplyStatus = enumConstByFlagApplyStatus;
	}

	public PeStudent getPeStudent() {
		return this.peStudent;
	}

	public void setPeStudent(PeStudent peStudent) {
		this.peStudent = peStudent;
	}

	public Date getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckNote() {
		return this.checkNote;
	}

	public void setCheckNote(String checkNote) {
		this.checkNote = checkNote;
	}

	public String getApplyNote() {
		return this.applyNote;
	}

	public void setApplyNote(String applyNote) {
		this.applyNote = applyNote;
	}

	public String getApplyInfo() {
		return this.applyInfo;
	}

	public void setApplyInfo(String applyInfo) {
		this.applyInfo = applyInfo;
	}

}