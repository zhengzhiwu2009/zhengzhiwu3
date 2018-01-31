package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PrPcElective entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrPcElective extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private EnumConst enumConstByFlagBak;
	private PrPcOpencourse prPcOpencourse;
	private PePcStudent pePcStudent;
	private EnumConst enumConstByFlagEnrol;
	private String roomNum;
	private String seatNum;
	private Double score;
	private Set prPcStuExercises = new HashSet(0);
	private Set prPcStuBookings = new HashSet(0);
	private Set prPcNoteReplies = new HashSet(0);

	// Constructors

	/** default constructor */
	public PrPcElective() {
	}

	/** full constructor */
	public PrPcElective(EnumConst enumConstByFlagBak,
			PrPcOpencourse prPcOpencourse, PePcStudent pePcStudent,
			EnumConst enumConstByFlagEnrol, String roomNum, String seatNum,
			Double score, Set prPcStuExercises, Set prPcStuBookings,
			Set prPcNoteReplies) {
		this.enumConstByFlagBak = enumConstByFlagBak;
		this.prPcOpencourse = prPcOpencourse;
		this.pePcStudent = pePcStudent;
		this.enumConstByFlagEnrol = enumConstByFlagEnrol;
		this.roomNum = roomNum;
		this.seatNum = seatNum;
		this.score = score;
		this.prPcStuExercises = prPcStuExercises;
		this.prPcStuBookings = prPcStuBookings;
		this.prPcNoteReplies = prPcNoteReplies;
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

	public PrPcOpencourse getPrPcOpencourse() {
		return this.prPcOpencourse;
	}

	public void setPrPcOpencourse(PrPcOpencourse prPcOpencourse) {
		this.prPcOpencourse = prPcOpencourse;
	}

	public PePcStudent getPePcStudent() {
		return this.pePcStudent;
	}

	public void setPePcStudent(PePcStudent pePcStudent) {
		this.pePcStudent = pePcStudent;
	}

	public EnumConst getEnumConstByFlagEnrol() {
		return this.enumConstByFlagEnrol;
	}

	public void setEnumConstByFlagEnrol(EnumConst enumConstByFlagEnrol) {
		this.enumConstByFlagEnrol = enumConstByFlagEnrol;
	}

	public String getRoomNum() {
		return this.roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public String getSeatNum() {
		return this.seatNum;
	}

	public void setSeatNum(String seatNum) {
		this.seatNum = seatNum;
	}

	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Set getPrPcStuExercises() {
		return this.prPcStuExercises;
	}

	public void setPrPcStuExercises(Set prPcStuExercises) {
		this.prPcStuExercises = prPcStuExercises;
	}

	public Set getPrPcStuBookings() {
		return this.prPcStuBookings;
	}

	public void setPrPcStuBookings(Set prPcStuBookings) {
		this.prPcStuBookings = prPcStuBookings;
	}

	public Set getPrPcNoteReplies() {
		return this.prPcNoteReplies;
	}

	public void setPrPcNoteReplies(Set prPcNoteReplies) {
		this.prPcNoteReplies = prPcNoteReplies;
	}

}