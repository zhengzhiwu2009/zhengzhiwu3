package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrCourseArrange generated by MyEclipse Persistence Tools
 */

public class PrCourseArrange extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;

	private EnumConst enumConstByFlagBak;

	private PrTchOpencourse prTchOpencourse;

	private EnumConst enumConstByFlagCourseSection;

	private Date arrangeDate;

	private String classroom;

	// Constructors

	/** default constructor */
	public PrCourseArrange() {
	}

	/** full constructor */
	public PrCourseArrange(EnumConst enumConstByFlagBak,
			PrTchOpencourse prTchOpencourse,
			EnumConst enumConstByFlagCourseSection, Date arrangeDate,
			String classroom) {
		this.enumConstByFlagBak = enumConstByFlagBak;
		this.prTchOpencourse = prTchOpencourse;
		this.enumConstByFlagCourseSection = enumConstByFlagCourseSection;
		this.arrangeDate = arrangeDate;
		this.classroom = classroom;
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

	public PrTchOpencourse getPrTchOpencourse() {
		return this.prTchOpencourse;
	}

	public void setPrTchOpencourse(PrTchOpencourse prTchOpencourse) {
		this.prTchOpencourse = prTchOpencourse;
	}

	public EnumConst getEnumConstByFlagCourseSection() {
		return this.enumConstByFlagCourseSection;
	}

	public void setEnumConstByFlagCourseSection(
			EnumConst enumConstByFlagCourseSection) {
		this.enumConstByFlagCourseSection = enumConstByFlagCourseSection;
	}

	public Date getArrangeDate() {
		return this.arrangeDate;
	}

	public void setArrangeDate(Date arrangeDate) {
		this.arrangeDate = arrangeDate;
	}

	public String getClassroom() {
		return this.classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

}