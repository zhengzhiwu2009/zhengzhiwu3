package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeBzzSuggestion entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBzzSuggestion extends AbstractBean implements java.io.Serializable {

	// Fields

	private String id;
	private PrBzzTchOpencourse prBzzTchOpencourse;
	private SsoUser ssoUser;
	private String content;
	private Date dataDate;
	private String yaoQiu;

	// Constructors

	/** default constructor */
	public PeBzzSuggestion() {
	}

	/** full constructor */
	public PeBzzSuggestion(PrBzzTchOpencourse prBzzTchOpencourse,
			SsoUser ssoUser, String content, Date dataDate,String yaoQiu) {
		this.prBzzTchOpencourse = prBzzTchOpencourse;
		this.ssoUser = ssoUser;
		this.content = content;
		this.dataDate = dataDate;
		this.yaoQiu = yaoQiu;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PrBzzTchOpencourse getPrBzzTchOpencourse() {
		return this.prBzzTchOpencourse;
	}

	public void setPrBzzTchOpencourse(PrBzzTchOpencourse prBzzTchOpencourse) {
		this.prBzzTchOpencourse = prBzzTchOpencourse;
	}

	public SsoUser getSsoUser() {
		return this.ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDataDate() {
		return dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public String getYaoQiu() {
		return yaoQiu;
	}

	public void setYaoQiu(String yaoQiu) {
		this.yaoQiu = yaoQiu;
	}


}