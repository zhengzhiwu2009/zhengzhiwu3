package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrDocument generated by MyEclipse Persistence Tools
 */

public class PrDocument extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private SsoUser ssoUser;
	private EnumConst enumConstByFlagRead;
	private PeDocument peDocument;
	private EnumConst enumConstByFlagDel;
	private Date readDate;

	// Constructors

	/** default constructor */
	public PrDocument() {
	}

	/** full constructor */
	public PrDocument(SsoUser ssoUser, EnumConst enumConstByFlagRead,
			PeDocument peDocument, EnumConst enumConstByFlagDel, Date readDate) {
		this.ssoUser = ssoUser;
		this.enumConstByFlagRead = enumConstByFlagRead;
		this.peDocument = peDocument;
		this.enumConstByFlagDel = enumConstByFlagDel;
		this.readDate = readDate;
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

	public EnumConst getEnumConstByFlagRead() {
		return this.enumConstByFlagRead;
	}

	public void setEnumConstByFlagRead(EnumConst enumConstByFlagRead) {
		this.enumConstByFlagRead = enumConstByFlagRead;
	}

	public PeDocument getPeDocument() {
		return this.peDocument;
	}

	public void setPeDocument(PeDocument peDocument) {
		this.peDocument = peDocument;
	}

	public EnumConst getEnumConstByFlagDel() {
		return this.enumConstByFlagDel;
	}

	public void setEnumConstByFlagDel(EnumConst enumConstByFlagDel) {
		this.enumConstByFlagDel = enumConstByFlagDel;
	}

	public Date getReadDate() {
		return this.readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

}