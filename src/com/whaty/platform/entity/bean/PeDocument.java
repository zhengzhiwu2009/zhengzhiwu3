package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeDocument generated by MyEclipse Persistence Tools
 */

public class PeDocument extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;

	private SsoUser ssoUser;

	private EnumConst enumConstByFlagBak;

	private EnumConst enumConstByFlagDel;

	private String title;

	private Date sendDate;

	private String note;

	private Set prDocuments = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeDocument() {
	}

	/** minimal constructor */
	public PeDocument(String title) {
		this.title = title;
	}

	/** full constructor */
	public PeDocument(SsoUser ssoUser, EnumConst enumConstByFlagBak,
			EnumConst enumConstByFlagDel, String title, Date sendDate,
			String note, Set prDocuments) {
		this.ssoUser = ssoUser;
		this.enumConstByFlagBak = enumConstByFlagBak;
		this.enumConstByFlagDel = enumConstByFlagDel;
		this.title = title;
		this.sendDate = sendDate;
		this.note = note;
		this.prDocuments = prDocuments;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SsoUser getSsoUser() {
		return this.ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	public EnumConst getEnumConstByFlagBak() {
		return this.enumConstByFlagBak;
	}

	public void setEnumConstByFlagBak(EnumConst enumConstByFlagBak) {
		this.enumConstByFlagBak = enumConstByFlagBak;
	}

	public EnumConst getEnumConstByFlagDel() {
		return this.enumConstByFlagDel;
	}

	public void setEnumConstByFlagDel(EnumConst enumConstByFlagDel) {
		this.enumConstByFlagDel = enumConstByFlagDel;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPrDocuments() {
		return this.prDocuments;
	}

	public void setPrDocuments(Set prDocuments) {
		this.prDocuments = prDocuments;
	}

}