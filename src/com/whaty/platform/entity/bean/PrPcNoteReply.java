package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrPcNoteReply entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrPcNoteReply extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PePcNote pePcNote;
	private PrPcElective prPcElective;
	private Date replyDatetime;
	private String note;

	// Constructors

	/** default constructor */
	public PrPcNoteReply() {
	}

	/** full constructor */
	public PrPcNoteReply(PePcNote pePcNote, PrPcElective prPcElective,
			Date replyDatetime, String note) {
		this.pePcNote = pePcNote;
		this.prPcElective = prPcElective;
		this.replyDatetime = replyDatetime;
		this.note = note;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PePcNote getPePcNote() {
		return this.pePcNote;
	}

	public void setPePcNote(PePcNote pePcNote) {
		this.pePcNote = pePcNote;
	}

	public PrPcElective getPrPcElective() {
		return this.prPcElective;
	}

	public void setPrPcElective(PrPcElective prPcElective) {
		this.prPcElective = prPcElective;
	}

	public Date getReplyDatetime() {
		return this.replyDatetime;
	}

	public void setReplyDatetime(Date replyDatetime) {
		this.replyDatetime = replyDatetime;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}