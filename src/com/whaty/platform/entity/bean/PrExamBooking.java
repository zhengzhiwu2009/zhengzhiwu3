package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrExamBooking generated by MyEclipse Persistence Tools
 */

public class PrExamBooking extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private EnumConst enumConstByFlagScoreStatusb;
	private PeSemester peSemester;
	private EnumConst enumConstByFlagScoreStatusa;
	private EnumConst enumConstByFlagScoreStatus;
	private PrTchStuElective prTchStuElective;
	private PeExamRoom peExamRoom;
	private PeExamNo peExamNo;
	private Date bookingDate;
	private String seatNo;
	private Double scoreExam;
	private Double scoreExamA;
	private Double scoreExamB;

	// Constructors

	/** default constructor */
	public PrExamBooking() {
	}

	/** full constructor */
	public PrExamBooking(EnumConst enumConstByFlagScoreStatusb,
			PeSemester peSemester, EnumConst enumConstByFlagScoreStatusa,
			EnumConst enumConstByFlagScoreStatus,
			PrTchStuElective prTchStuElective, PeExamRoom peExamRoom,
			PeExamNo peExamNo, Date bookingDate, String seatNo,
			Double scoreExam, Double scoreExamA, Double scoreExamB) {
		this.enumConstByFlagScoreStatusb = enumConstByFlagScoreStatusb;
		this.peSemester = peSemester;
		this.enumConstByFlagScoreStatusa = enumConstByFlagScoreStatusa;
		this.enumConstByFlagScoreStatus = enumConstByFlagScoreStatus;
		this.prTchStuElective = prTchStuElective;
		this.peExamRoom = peExamRoom;
		this.peExamNo = peExamNo;
		this.bookingDate = bookingDate;
		this.seatNo = seatNo;
		this.scoreExam = scoreExam;
		this.scoreExamA = scoreExamA;
		this.scoreExamB = scoreExamB;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConstByFlagScoreStatusb() {
		return this.enumConstByFlagScoreStatusb;
	}

	public void setEnumConstByFlagScoreStatusb(
			EnumConst enumConstByFlagScoreStatusb) {
		this.enumConstByFlagScoreStatusb = enumConstByFlagScoreStatusb;
	}

	public PeSemester getPeSemester() {
		return this.peSemester;
	}

	public void setPeSemester(PeSemester peSemester) {
		this.peSemester = peSemester;
	}

	public EnumConst getEnumConstByFlagScoreStatusa() {
		return this.enumConstByFlagScoreStatusa;
	}

	public void setEnumConstByFlagScoreStatusa(
			EnumConst enumConstByFlagScoreStatusa) {
		this.enumConstByFlagScoreStatusa = enumConstByFlagScoreStatusa;
	}

	public EnumConst getEnumConstByFlagScoreStatus() {
		return this.enumConstByFlagScoreStatus;
	}

	public void setEnumConstByFlagScoreStatus(
			EnumConst enumConstByFlagScoreStatus) {
		this.enumConstByFlagScoreStatus = enumConstByFlagScoreStatus;
	}

	public PrTchStuElective getPrTchStuElective() {
		return this.prTchStuElective;
	}

	public void setPrTchStuElective(PrTchStuElective prTchStuElective) {
		this.prTchStuElective = prTchStuElective;
	}

	public PeExamRoom getPeExamRoom() {
		return this.peExamRoom;
	}

	public void setPeExamRoom(PeExamRoom peExamRoom) {
		this.peExamRoom = peExamRoom;
	}

	public PeExamNo getPeExamNo() {
		return this.peExamNo;
	}

	public void setPeExamNo(PeExamNo peExamNo) {
		this.peExamNo = peExamNo;
	}

	public Date getBookingDate() {
		return this.bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getSeatNo() {
		return this.seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public Double getScoreExam() {
		return this.scoreExam;
	}

	public void setScoreExam(Double scoreExam) {
		this.scoreExam = scoreExam;
	}

	public Double getScoreExamA() {
		return this.scoreExamA;
	}

	public void setScoreExamA(Double scoreExamA) {
		this.scoreExamA = scoreExamA;
	}

	public Double getScoreExamB() {
		return this.scoreExamB;
	}

	public void setScoreExamB(Double scoreExamB) {
		this.scoreExamB = scoreExamB;
	}

}