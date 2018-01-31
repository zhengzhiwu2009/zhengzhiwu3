package com.whaty.platform.entity.bean;

public class ReportInfo extends com.whaty.platform.entity.bean.AbstractBean
implements java.io.Serializable {

	private String id;
	private EnumConst enumConstByReportType;
	private String title;
	private String year;
	private String month;
	private String quarter;
	private String half;
	private String note;
	private SsoUser ssoUser;
	private String trueName;
	
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public SsoUser getSsoUser() {
		return ssoUser;
	}
	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public EnumConst getEnumConstByReportType() {
		return enumConstByReportType;
	}
	public void setEnumConstByReportType(EnumConst enumConstByReportType) {
		this.enumConstByReportType = enumConstByReportType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getQuarter() {
		return quarter;
	}
	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}
	public String getHalf() {
		return half;
	}
	public void setHalf(String half) {
		this.half = half;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	


}
