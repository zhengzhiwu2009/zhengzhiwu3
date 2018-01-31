package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeExamMaincourseRoom entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeExamMaincourseRoom extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;

	private PrExamOpenMaincourse prExamOpenMaincourse;

	private String code;

	private PeSite peSite;

	private String invigilatorA;

	private String invigilatorB;

	private String name;

	private String examPlace;

	private Set prExamStuMaincourses = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeExamMaincourseRoom() {
	}

	/** full constructor */
	public PeExamMaincourseRoom(PrExamOpenMaincourse prExamOpenMaincourse,
			PeSite peSite, String name, String code, String examPlace,
			String invigilatorA, String invigilatorB, Set prExamStuMaincourses) {
		this.prExamOpenMaincourse = prExamOpenMaincourse;
		this.peSite = peSite;
		this.name = name;
		this.code = code;
		this.examPlace = examPlace;
		this.invigilatorA = invigilatorA;
		this.invigilatorB = invigilatorB;
		this.prExamStuMaincourses = prExamStuMaincourses;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PrExamOpenMaincourse getPrExamOpenMaincourse() {
		return this.prExamOpenMaincourse;
	}

	public void setPrExamOpenMaincourse(
			PrExamOpenMaincourse prExamOpenMaincourse) {
		this.prExamOpenMaincourse = prExamOpenMaincourse;
	}

	public PeSite getPeSite() {
		return this.peSite;
	}

	public void setPeSite(PeSite peSite) {
		this.peSite = peSite;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInvigilatorA() {
		return this.invigilatorA;
	}

	public void setInvigilatorA(String invigilatorA) {
		this.invigilatorA = invigilatorA;
	}

	public String getInvigilatorB() {
		return this.invigilatorB;
	}

	public void setInvigilatorB(String invigilatorB) {
		this.invigilatorB = invigilatorB;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExamPlace() {
		return this.examPlace;
	}

	public void setExamPlace(String examPlace) {
		this.examPlace = examPlace;
	}

	public Set getPrExamStuMaincourses() {
		return this.prExamStuMaincourses;
	}

	public void setPrExamStuMaincourses(Set prExamStuMaincourses) {
		this.prExamStuMaincourses = prExamStuMaincourses;
	}

}