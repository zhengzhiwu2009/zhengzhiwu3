package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeRecExamcourse generated by MyEclipse Persistence Tools
 */

public class PeRecExamcourse extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;

	private EnumConst enumConstByFlagBak;

	private EnumConst enumConstByFlagArrangeRoom;

	private String name;

	private String note;

	private Set prRecExamCourseTimes = new HashSet(0);

	private Set prRecCourseMajorEdutypes = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeRecExamcourse() {
	}

	/** minimal constructor */
	public PeRecExamcourse(String name) {
		this.name = name;
	}

	/** full constructor */
	public PeRecExamcourse(EnumConst enumConstByFlagBak,
			EnumConst enumConstByFlagArrangeRoom, String name, String note,
			Set prRecExamCourseTimes, Set prRecCourseMajorEdutypes) {
		this.enumConstByFlagBak = enumConstByFlagBak;
		this.enumConstByFlagArrangeRoom = enumConstByFlagArrangeRoom;
		this.name = name;
		this.note = note;
		this.prRecExamCourseTimes = prRecExamCourseTimes;
		this.prRecCourseMajorEdutypes = prRecCourseMajorEdutypes;
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

	public EnumConst getEnumConstByFlagArrangeRoom() {
		return this.enumConstByFlagArrangeRoom;
	}

	public void setEnumConstByFlagArrangeRoom(
			EnumConst enumConstByFlagArrangeRoom) {
		this.enumConstByFlagArrangeRoom = enumConstByFlagArrangeRoom;
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

	public Set getPrRecExamCourseTimes() {
		return this.prRecExamCourseTimes;
	}

	public void setPrRecExamCourseTimes(Set prRecExamCourseTimes) {
		this.prRecExamCourseTimes = prRecExamCourseTimes;
	}

	public Set getPrRecCourseMajorEdutypes() {
		return this.prRecCourseMajorEdutypes;
	}

	public void setPrRecCourseMajorEdutypes(Set prRecCourseMajorEdutypes) {
		this.prRecCourseMajorEdutypes = prRecCourseMajorEdutypes;
	}

}