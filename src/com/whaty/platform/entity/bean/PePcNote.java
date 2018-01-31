package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PePcNote entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PePcNote extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PrPcOpencourse prPcOpencourse;
	private PePcTeacher pePcTeacher;
	private Date publishDatetime;
	private String title;
	private String note;
	private Set prPcNoteReplies = new HashSet(0);

	// Constructors

	/** default constructor */
	public PePcNote() {
	}

	/** full constructor */
	public PePcNote(PrPcOpencourse prPcOpencourse, PePcTeacher pePcTeacher,
			Date publishDatetime, String title, String note, Set prPcNoteReplies) {
		this.prPcOpencourse = prPcOpencourse;
		this.pePcTeacher = pePcTeacher;
		this.publishDatetime = publishDatetime;
		this.title = title;
		this.note = note;
		this.prPcNoteReplies = prPcNoteReplies;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PrPcOpencourse getPrPcOpencourse() {
		return this.prPcOpencourse;
	}

	public void setPrPcOpencourse(PrPcOpencourse prPcOpencourse) {
		this.prPcOpencourse = prPcOpencourse;
	}

	public PePcTeacher getPePcTeacher() {
		return this.pePcTeacher;
	}

	public void setPePcTeacher(PePcTeacher pePcTeacher) {
		this.pePcTeacher = pePcTeacher;
	}

	public Date getPublishDatetime() {
		return this.publishDatetime;
	}

	public void setPublishDatetime(Date publishDatetime) {
		this.publishDatetime = publishDatetime;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPrPcNoteReplies() {
		return this.prPcNoteReplies;
	}

	public void setPrPcNoteReplies(Set prPcNoteReplies) {
		this.prPcNoteReplies = prPcNoteReplies;
	}

}