package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PrTchOpencourse generated by MyEclipse Persistence Tools
 */

public class PrTchOpencourse extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;

	private EnumConst enumConstByFlagBak;

	private PeSemester peSemester;

	private EnumConst enumConstByFlagExamCourse;

	private PeTchCourse peTchCourse;

	private String adviceExamNo;

	private Long courseTime;

	private Set prTchOpencourseCoursewares = new HashSet(0);

	private Set prTchOpencourseTeachers = new HashSet(0);

	private Set prTchOpencourseBooks = new HashSet(0);

	private Set prCourseArranges = new HashSet(0);

	private Set prTchStuElectives = new HashSet(0);

	// Constructors

	/** default constructor */
	public PrTchOpencourse() {
	}

	/** full constructor */
	public PrTchOpencourse(EnumConst enumConstByFlagBak, PeSemester peSemester,
			EnumConst enumConstByFlagExamCourse, PeTchCourse peTchCourse,
			String adviceExamNo, Long courseTime,
			Set prTchOpencourseCoursewares, Set prTchOpencourseTeachers,
			Set prTchOpencourseBooks, Set prCourseArranges,
			Set prTchStuElectives) {
		this.enumConstByFlagBak = enumConstByFlagBak;
		this.peSemester = peSemester;
		this.enumConstByFlagExamCourse = enumConstByFlagExamCourse;
		this.peTchCourse = peTchCourse;
		this.adviceExamNo = adviceExamNo;
		this.courseTime = courseTime;
		this.prTchOpencourseCoursewares = prTchOpencourseCoursewares;
		this.prTchOpencourseTeachers = prTchOpencourseTeachers;
		this.prTchOpencourseBooks = prTchOpencourseBooks;
		this.prCourseArranges = prCourseArranges;
		this.prTchStuElectives = prTchStuElectives;
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

	public PeSemester getPeSemester() {
		return this.peSemester;
	}

	public void setPeSemester(PeSemester peSemester) {
		this.peSemester = peSemester;
	}

	public EnumConst getEnumConstByFlagExamCourse() {
		return this.enumConstByFlagExamCourse;
	}

	public void setEnumConstByFlagExamCourse(EnumConst enumConstByFlagExamCourse) {
		this.enumConstByFlagExamCourse = enumConstByFlagExamCourse;
	}

	public PeTchCourse getPeTchCourse() {
		return this.peTchCourse;
	}

	public void setPeTchCourse(PeTchCourse peTchCourse) {
		this.peTchCourse = peTchCourse;
	}

	public String getAdviceExamNo() {
		return this.adviceExamNo;
	}

	public void setAdviceExamNo(String adviceExamNo) {
		this.adviceExamNo = adviceExamNo;
	}

	public Long getCourseTime() {
		return this.courseTime;
	}

	public void setCourseTime(Long courseTime) {
		this.courseTime = courseTime;
	}

	public Set getPrTchOpencourseCoursewares() {
		return this.prTchOpencourseCoursewares;
	}

	public void setPrTchOpencourseCoursewares(Set prTchOpencourseCoursewares) {
		this.prTchOpencourseCoursewares = prTchOpencourseCoursewares;
	}

	public Set getPrTchOpencourseTeachers() {
		return this.prTchOpencourseTeachers;
	}

	public void setPrTchOpencourseTeachers(Set prTchOpencourseTeachers) {
		this.prTchOpencourseTeachers = prTchOpencourseTeachers;
	}

	public Set getPrTchOpencourseBooks() {
		return this.prTchOpencourseBooks;
	}

	public void setPrTchOpencourseBooks(Set prTchOpencourseBooks) {
		this.prTchOpencourseBooks = prTchOpencourseBooks;
	}

	public Set getPrCourseArranges() {
		return this.prCourseArranges;
	}

	public void setPrCourseArranges(Set prCourseArranges) {
		this.prCourseArranges = prCourseArranges;
	}

	public Set getPrTchStuElectives() {
		return this.prTchStuElectives;
	}

	public void setPrTchStuElectives(Set prTchStuElectives) {
		this.prTchStuElectives = prTchStuElectives;
	}

}