package com.whaty.platform.entity.bean;

/**
 * PrTchCourseBook entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrTchCourseBook extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeTchCourse peTchCourse;
	private PeTchBook peTchBook;

	// Constructors

	/** default constructor */
	public PrTchCourseBook() {
	}

	/** full constructor */
	public PrTchCourseBook(PeTchCourse peTchCourse, PeTchBook peTchBook) {
		this.peTchCourse = peTchCourse;
		this.peTchBook = peTchBook;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeTchCourse getPeTchCourse() {
		return this.peTchCourse;
	}

	public void setPeTchCourse(PeTchCourse peTchCourse) {
		this.peTchCourse = peTchCourse;
	}

	public PeTchBook getPeTchBook() {
		return this.peTchBook;
	}

	public void setPeTchBook(PeTchBook peTchBook) {
		this.peTchBook = peTchBook;
	}

}