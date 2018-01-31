package com.whaty.platform.entity.bean;

import java.util.Date;
/**
 * 推荐课程
 * @author zhang
 *
 */
public class RecommendCourse extends com.whaty.platform.entity.bean.AbstractBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6794288524103650778L;
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
	private String id;
	private RecommendSeries series;
	private PeBzzTchCourse course;
	private EnumConst enumConstByisShowIndex;
	private EnumConst enumConstByisAreaShowIndex;
	private String createUser;
	private Date createDate;
	private Date showDate;
	public RecommendSeries getSeries() {
		return series;
	}
	public void setSeries(RecommendSeries series) {
		this.series = series;
	}
	public PeBzzTchCourse getCourse() {
		return course;
	}
	public void setCourse(PeBzzTchCourse course) {
		this.course = course;
	}
	public EnumConst getEnumConstByisShowIndex() {
		return enumConstByisShowIndex;
	}
	public void setEnumConstByisShowIndex(EnumConst enumConstByisShowIndex) {
		this.enumConstByisShowIndex = enumConstByisShowIndex;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getShowDate() {
		return showDate;
	}
	public void setShowDate(Date showDate) {
		this.showDate = showDate;
	}
	public EnumConst getEnumConstByisAreaShowIndex() {
		return enumConstByisAreaShowIndex;
	}
	public void setEnumConstByisAreaShowIndex(EnumConst enumConstByisAreaShowIndex) {
		this.enumConstByisAreaShowIndex = enumConstByisAreaShowIndex;
	}

}
