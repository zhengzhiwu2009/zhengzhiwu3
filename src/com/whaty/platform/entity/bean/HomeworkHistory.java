package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * HomeworkHistory generated by MyEclipse Persistence Tools
 */

public class HomeworkHistory extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;

	private EnumConst enumConstByFlagBak;

	private PeStudent peStudent;

	private HomeworkInfo homeworkInfo;

	private EnumConst enumConstByFlagIscheck;

	private Date testdate;

	private String note;

	private String score;

	// Constructors

	/** default constructor */
	public HomeworkHistory() {
	}

	/** full constructor */
	public HomeworkHistory(EnumConst enumConstByFlagBak, PeStudent peStudent,
			HomeworkInfo homeworkInfo, EnumConst enumConstByFlagIscheck,
			Date testdate, String note, String score) {
		this.enumConstByFlagBak = enumConstByFlagBak;
		this.peStudent = peStudent;
		this.homeworkInfo = homeworkInfo;
		this.enumConstByFlagIscheck = enumConstByFlagIscheck;
		this.testdate = testdate;
		this.note = note;
		this.score = score;
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

	public PeStudent getPeStudent() {
		return this.peStudent;
	}

	public void setPeStudent(PeStudent peStudent) {
		this.peStudent = peStudent;
	}

	public HomeworkInfo getHomeworkInfo() {
		return this.homeworkInfo;
	}

	public void setHomeworkInfo(HomeworkInfo homeworkInfo) {
		this.homeworkInfo = homeworkInfo;
	}

	public EnumConst getEnumConstByFlagIscheck() {
		return this.enumConstByFlagIscheck;
	}

	public void setEnumConstByFlagIscheck(EnumConst enumConstByFlagIscheck) {
		this.enumConstByFlagIscheck = enumConstByFlagIscheck;
	}

	public Date getTestdate() {
		return this.testdate;
	}

	public void setTestdate(Date testdate) {
		this.testdate = testdate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getScore() {
		return this.score;
	}

	public void setScore(String score) {
		this.score = score;
	}

}