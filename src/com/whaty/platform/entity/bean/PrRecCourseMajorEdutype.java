package com.whaty.platform.entity.bean;

/**
 * PrRecCourseMajorEdutype generated by MyEclipse Persistence Tools
 */

public class PrRecCourseMajorEdutype extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;

	private PeMajor peMajor;

	private PeRecExamcourse peRecExamcourse;

	private PeEdutype peEdutype;

	// Constructors

	/** default constructor */
	public PrRecCourseMajorEdutype() {
	}

	/** full constructor */
	public PrRecCourseMajorEdutype(PeMajor peMajor,
			PeRecExamcourse peRecExamcourse, PeEdutype peEdutype) {
		this.peMajor = peMajor;
		this.peRecExamcourse = peRecExamcourse;
		this.peEdutype = peEdutype;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeMajor getPeMajor() {
		return this.peMajor;
	}

	public void setPeMajor(PeMajor peMajor) {
		this.peMajor = peMajor;
	}

	public PeRecExamcourse getPeRecExamcourse() {
		return this.peRecExamcourse;
	}

	public void setPeRecExamcourse(PeRecExamcourse peRecExamcourse) {
		this.peRecExamcourse = peRecExamcourse;
	}

	public PeEdutype getPeEdutype() {
		return this.peEdutype;
	}

	public void setPeEdutype(PeEdutype peEdutype) {
		this.peEdutype = peEdutype;
	}

}