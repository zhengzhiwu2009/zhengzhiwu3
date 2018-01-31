package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * HomeworkInfo generated by MyEclipse Persistence Tools
 */

public class HomeworkInfo extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;

	private EnumConst enumConstByFlagBak;

	private PeTeacher peTeacher;

	private EnumConst enumConstByFlagIsvalid;

	private String title;

	private Date creatdate;

	private String note;

	private String groupId;

	private Date startdate;

	private Date enddate;

	private Set homeworkHistories = new HashSet(0);

	// Constructors

	/** default constructor */
	public HomeworkInfo() {
	}

	/** full constructor */
	public HomeworkInfo(EnumConst enumConstByFlagBak, PeTeacher peTeacher,
			EnumConst enumConstByFlagIsvalid, String title, Date creatdate,
			String note, String groupId, Date startdate, Date enddate,
			Set homeworkHistories) {
		this.enumConstByFlagBak = enumConstByFlagBak;
		this.peTeacher = peTeacher;
		this.enumConstByFlagIsvalid = enumConstByFlagIsvalid;
		this.title = title;
		this.creatdate = creatdate;
		this.note = note;
		this.groupId = groupId;
		this.startdate = startdate;
		this.enddate = enddate;
		this.homeworkHistories = homeworkHistories;
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

	public PeTeacher getPeTeacher() {
		return this.peTeacher;
	}

	public void setPeTeacher(PeTeacher peTeacher) {
		this.peTeacher = peTeacher;
	}

	public EnumConst getEnumConstByFlagIsvalid() {
		return this.enumConstByFlagIsvalid;
	}

	public void setEnumConstByFlagIsvalid(EnumConst enumConstByFlagIsvalid) {
		this.enumConstByFlagIsvalid = enumConstByFlagIsvalid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreatdate() {
		return this.creatdate;
	}

	public void setCreatdate(Date creatdate) {
		this.creatdate = creatdate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Set getHomeworkHistories() {
		return this.homeworkHistories;
	}

	public void setHomeworkHistories(Set homeworkHistories) {
		this.homeworkHistories = homeworkHistories;
	}

}