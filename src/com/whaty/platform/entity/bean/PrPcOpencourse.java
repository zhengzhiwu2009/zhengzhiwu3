package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PrPcOpencourse entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrPcOpencourse extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeSemester peSemester;
	private PePcCourse pePcCourse;
	private Set prPcOpencourseTeachers = new HashSet(0);
	private Set pePcOpencourseCoursewares = new HashSet(0);
	private Set pePcOpencourseResources = new HashSet(0);
	private Set prPcElectives = new HashSet(0);
	private Set pePcExercises = new HashSet(0);
	private Set pePcNotes = new HashSet(0);
	private Set prPcBookingSeats = new HashSet(0);

	// Constructors

	/** default constructor */
	public PrPcOpencourse() {
	}

	/** full constructor */
	public PrPcOpencourse(PeSemester peSemester, PePcCourse pePcCourse,
			Set prPcOpencourseTeachers, Set pePcOpencourseCoursewares,
			Set pePcOpencourseResources, Set prPcElectives, Set pePcExercises,
			Set pePcNotes, Set prPcBookingSeats) {
		this.peSemester = peSemester;
		this.pePcCourse = pePcCourse;
		this.prPcOpencourseTeachers = prPcOpencourseTeachers;
		this.pePcOpencourseCoursewares = pePcOpencourseCoursewares;
		this.pePcOpencourseResources = pePcOpencourseResources;
		this.prPcElectives = prPcElectives;
		this.pePcExercises = pePcExercises;
		this.pePcNotes = pePcNotes;
		this.prPcBookingSeats = prPcBookingSeats;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeSemester getPeSemester() {
		return this.peSemester;
	}

	public void setPeSemester(PeSemester peSemester) {
		this.peSemester = peSemester;
	}

	public PePcCourse getPePcCourse() {
		return this.pePcCourse;
	}

	public void setPePcCourse(PePcCourse pePcCourse) {
		this.pePcCourse = pePcCourse;
	}

	public Set getPrPcOpencourseTeachers() {
		return this.prPcOpencourseTeachers;
	}

	public void setPrPcOpencourseTeachers(Set prPcOpencourseTeachers) {
		this.prPcOpencourseTeachers = prPcOpencourseTeachers;
	}

	public Set getPePcOpencourseCoursewares() {
		return this.pePcOpencourseCoursewares;
	}

	public void setPePcOpencourseCoursewares(Set pePcOpencourseCoursewares) {
		this.pePcOpencourseCoursewares = pePcOpencourseCoursewares;
	}

	public Set getPePcOpencourseResources() {
		return this.pePcOpencourseResources;
	}

	public void setPePcOpencourseResources(Set pePcOpencourseResources) {
		this.pePcOpencourseResources = pePcOpencourseResources;
	}

	public Set getPrPcElectives() {
		return this.prPcElectives;
	}

	public void setPrPcElectives(Set prPcElectives) {
		this.prPcElectives = prPcElectives;
	}

	public Set getPePcExercises() {
		return this.pePcExercises;
	}

	public void setPePcExercises(Set pePcExercises) {
		this.pePcExercises = pePcExercises;
	}

	public Set getPePcNotes() {
		return this.pePcNotes;
	}

	public void setPePcNotes(Set pePcNotes) {
		this.pePcNotes = pePcNotes;
	}

	public Set getPrPcBookingSeats() {
		return this.prPcBookingSeats;
	}

	public void setPrPcBookingSeats(Set prPcBookingSeats) {
		this.prPcBookingSeats = prPcBookingSeats;
	}

}