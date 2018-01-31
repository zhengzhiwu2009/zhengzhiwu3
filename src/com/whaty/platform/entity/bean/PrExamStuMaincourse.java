package com.whaty.platform.entity.bean;

/**
 * PrExamStuMaincourse generated by MyEclipse Persistence Tools
 */

public class PrExamStuMaincourse extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;

	private EnumConst enumConstByFlagScoreStatus;

	private EnumConst enumConstByFlagScorePub;

	private PrExamOpenMaincourse prExamOpenMaincourse;

	private PeStudent peStudent;

	private PeExamMaincourseRoom peExamMaincourseRoom;

	private String seatNo;

	private Double score;

	// Constructors

	/** default constructor */
	public PrExamStuMaincourse() {
	}

	/** full constructor */
	public PrExamStuMaincourse(EnumConst enumConstByFlagScoreStatus,
			EnumConst enumConstByFlagScorePub,
			PrExamOpenMaincourse prExamOpenMaincourse, PeStudent peStudent,
			PeExamMaincourseRoom peExamMaincourseRoom, String seatNo,
			Double score) {
		this.enumConstByFlagScoreStatus = enumConstByFlagScoreStatus;
		this.enumConstByFlagScorePub = enumConstByFlagScorePub;
		this.prExamOpenMaincourse = prExamOpenMaincourse;
		this.peStudent = peStudent;
		this.peExamMaincourseRoom = peExamMaincourseRoom;
		this.seatNo = seatNo;
		this.score = score;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConstByFlagScoreStatus() {
		return this.enumConstByFlagScoreStatus;
	}

	public void setEnumConstByFlagScoreStatus(
			EnumConst enumConstByFlagScoreStatus) {
		this.enumConstByFlagScoreStatus = enumConstByFlagScoreStatus;
	}

	public EnumConst getEnumConstByFlagScorePub() {
		return this.enumConstByFlagScorePub;
	}

	public void setEnumConstByFlagScorePub(EnumConst enumConstByFlagScorePub) {
		this.enumConstByFlagScorePub = enumConstByFlagScorePub;
	}

	public PrExamOpenMaincourse getPrExamOpenMaincourse() {
		return this.prExamOpenMaincourse;
	}

	public void setPrExamOpenMaincourse(
			PrExamOpenMaincourse prExamOpenMaincourse) {
		this.prExamOpenMaincourse = prExamOpenMaincourse;
	}

	public PeStudent getPeStudent() {
		return this.peStudent;
	}

	public void setPeStudent(PeStudent peStudent) {
		this.peStudent = peStudent;
	}

	public PeExamMaincourseRoom getPeExamMaincourseRoom() {
		return this.peExamMaincourseRoom;
	}

	public void setPeExamMaincourseRoom(
			PeExamMaincourseRoom peExamMaincourseRoom) {
		this.peExamMaincourseRoom = peExamMaincourseRoom;
	}

	public String getSeatNo() {
		return this.seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

}