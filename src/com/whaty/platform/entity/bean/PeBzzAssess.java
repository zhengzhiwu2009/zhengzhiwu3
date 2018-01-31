package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeBzzAssess entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBzzAssess extends AbstractBean implements java.io.Serializable {

	// Fields

	private String id;
	private PrBzzTchOpencourse prBzzTchOpencourse;
	private PeBzzCourseFeedback peBzzCourseFeedback;
	private SsoUser ssoUser;
	private Date datadate;
	private String assess;

	// Constructors

	/** default constructor */
	public PeBzzAssess() {
	}

	/** full constructor */
	public PeBzzAssess(PrBzzTchOpencourse prBzzTchOpencourse,
			PeBzzCourseFeedback peBzzCourseFeedback, SsoUser ssoUser,
			Date datadate, String assess) {
		this.prBzzTchOpencourse = prBzzTchOpencourse;
		this.peBzzCourseFeedback = peBzzCourseFeedback;
		this.ssoUser = ssoUser;
		this.datadate = datadate;
		this.assess = assess;
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

	public PeBzzCourseFeedback getPeBzzCourseFeedback() {
		return this.peBzzCourseFeedback;
	}

	public void setPeBzzCourseFeedback(PeBzzCourseFeedback peBzzCourseFeedback) {
		this.peBzzCourseFeedback = peBzzCourseFeedback;
	}

	public SsoUser getSsoUser() {
		return this.ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}


	public Date getDatadate() {
		return datadate;
	}

	public void setDatadate(Date datadate) {
		this.datadate = datadate;
	}

	public String getAssess() {
		return this.assess;
	}

	public void setAssess(String assess) {
		this.assess = assess;
	}

}