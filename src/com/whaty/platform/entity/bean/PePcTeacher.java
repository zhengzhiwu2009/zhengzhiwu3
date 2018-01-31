package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PePcTeacher entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PePcTeacher extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private EnumConst enumConstByFlagBak;
	private SsoUser ssoUser;
	private EnumConst enumConstByFlagTeacherType;
	private String name;
	private String note;
	private Set prPcOpencourseTeachers = new HashSet(0);
	private Set pePcNotes = new HashSet(0);

	// Constructors

	/** default constructor */
	public PePcTeacher() {
	}

	/** full constructor */
	public PePcTeacher(EnumConst enumConstByFlagBak, SsoUser ssoUser,
			EnumConst enumConstByFlagTeacherType, String name, String note,
			Set prPcOpencourseTeachers, Set pePcNotes) {
		this.enumConstByFlagBak = enumConstByFlagBak;
		this.ssoUser = ssoUser;
		this.enumConstByFlagTeacherType = enumConstByFlagTeacherType;
		this.name = name;
		this.note = note;
		this.prPcOpencourseTeachers = prPcOpencourseTeachers;
		this.pePcNotes = pePcNotes;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConstByFlagBak() {
		return this.enumConstByFlagBak;
	}

	public void setEnumConstByFlagBak(EnumConst enumConstByFlagBak) {
		this.enumConstByFlagBak = enumConstByFlagBak;
	}

	public SsoUser getSsoUser() {
		return this.ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	public EnumConst getEnumConstByFlagTeacherType() {
		return this.enumConstByFlagTeacherType;
	}

	public void setEnumConstByFlagTeacherType(
			EnumConst enumConstByFlagTeacherType) {
		this.enumConstByFlagTeacherType = enumConstByFlagTeacherType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPrPcOpencourseTeachers() {
		return this.prPcOpencourseTeachers;
	}

	public void setPrPcOpencourseTeachers(Set prPcOpencourseTeachers) {
		this.prPcOpencourseTeachers = prPcOpencourseTeachers;
	}

	public Set getPePcNotes() {
		return this.pePcNotes;
	}

	public void setPePcNotes(Set pePcNotes) {
		this.pePcNotes = pePcNotes;
	}

}