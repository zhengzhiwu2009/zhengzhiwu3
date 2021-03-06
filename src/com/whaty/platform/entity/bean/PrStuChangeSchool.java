package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrStuChangeSchool generated by MyEclipse Persistence Tools
 */

public class PrStuChangeSchool extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;

	private PeStudent peStudent;

	private EnumConst enumConstByFlagBak;

	private EnumConst enumConstByFlagAbortschoolType;

	private Date opDate;

	private String note;

	// Constructors

	/** default constructor */
	public PrStuChangeSchool() {
	}

	/** full constructor */
	public PrStuChangeSchool(PeStudent peStudent, EnumConst enumConstByFlagBak,
			EnumConst enumConstByFlagAbortschoolType, Date opDate, String note) {
		this.peStudent = peStudent;
		this.enumConstByFlagBak = enumConstByFlagBak;
		this.enumConstByFlagAbortschoolType = enumConstByFlagAbortschoolType;
		this.opDate = opDate;
		this.note = note;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeStudent getPeStudent() {
		return this.peStudent;
	}

	public void setPeStudent(PeStudent peStudent) {
		this.peStudent = peStudent;
	}

	public EnumConst getEnumConstByFlagBak() {
		return this.enumConstByFlagBak;
	}

	public void setEnumConstByFlagBak(EnumConst enumConstByFlagBak) {
		this.enumConstByFlagBak = enumConstByFlagBak;
	}

	public EnumConst getEnumConstByFlagAbortschoolType() {
		return this.enumConstByFlagAbortschoolType;
	}

	public void setEnumConstByFlagAbortschoolType(
			EnumConst enumConstByFlagAbortschoolType) {
		this.enumConstByFlagAbortschoolType = enumConstByFlagAbortschoolType;
	}

	public Date getOpDate() {
		return this.opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}