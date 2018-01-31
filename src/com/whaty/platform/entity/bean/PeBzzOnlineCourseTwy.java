package com.whaty.platform.entity.bean;

import java.util.Date;

public class PeBzzOnlineCourseTwy extends com.whaty.platform.entity.bean.AbstractBean implements java.io.Serializable {

	private String id;	
	
	private String question;
	private PeBzzOnlineCourse peBzzOnlineCourse;
	private SsoUser ssoUser;
	private Date twyDate;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public PeBzzOnlineCourse getPeBzzOnlineCourse() {
		return peBzzOnlineCourse;
	}

	public void setPeBzzOnlineCourse(PeBzzOnlineCourse peBzzOnlineCourse) {
		this.peBzzOnlineCourse = peBzzOnlineCourse;
	}

	public SsoUser getSsoUser() {
		return ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	public Date getTwyDate() {
		return twyDate;
	}

	public void setTwyDate(Date twyDate) {
		this.twyDate = twyDate;
	}
}
