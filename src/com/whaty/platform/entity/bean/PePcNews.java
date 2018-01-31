package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PePcNews entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PePcNews extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private EnumConst enumConstByFlagBak;
	private SsoUser ssoUser;
	private EnumConst enumConstByFlagIssue;
	private String title;
	private Date issueDatetime;
	private String note;

	// Constructors

	/** default constructor */
	public PePcNews() {
	}

	/** full constructor */
	public PePcNews(EnumConst enumConstByFlagBak, SsoUser ssoUser,
			EnumConst enumConstByFlagIssue, String title, Date issueDatetime,
			String note) {
		this.enumConstByFlagBak = enumConstByFlagBak;
		this.ssoUser = ssoUser;
		this.enumConstByFlagIssue = enumConstByFlagIssue;
		this.title = title;
		this.issueDatetime = issueDatetime;
		this.note = note;
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

	public SsoUser getSsoUser() {
		return this.ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	public EnumConst getEnumConstByFlagIssue() {
		return this.enumConstByFlagIssue;
	}

	public void setEnumConstByFlagIssue(EnumConst enumConstByFlagIssue) {
		this.enumConstByFlagIssue = enumConstByFlagIssue;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getIssueDatetime() {
		return this.issueDatetime;
	}

	public void setIssueDatetime(Date issueDatetime) {
		this.issueDatetime = issueDatetime;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}