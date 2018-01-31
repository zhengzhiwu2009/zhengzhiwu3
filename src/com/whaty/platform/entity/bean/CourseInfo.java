package com.whaty.platform.entity.bean;

import java.util.Date;

public class CourseInfo extends AbstractBean implements java.io.Serializable {
	private String id;
	private String courseName; //课程名称
	private String loginId;
	private EnumConst enumConstByFlagCourseCategory; //业务分类
	private EnumConst enumConstByFlagCourseItemType; //大纲分类
	private EnumConst enumConstByFlagContentProperty; //内容属性分类
	private EnumConst enumConstByFlagIsValid;//是否有效
	private EnumConst enumConstByFlagOperationStatus;//是否处理状态
	private EnumConst enumConstByFlagIsRecommends;//是否推荐到协会
	private String createOrganizationName;//提交机构名称
	private String description; //课程描述
	private String teacherId;
	private String creater;   
	private String times;  //时长
	private Date createDate;
	public EnumConst getEnumConstByFlagIsValid() {
		return enumConstByFlagIsValid;
	}
	public void setEnumConstByFlagIsValid(EnumConst enumConstByFlagIsValid) {
		this.enumConstByFlagIsValid = enumConstByFlagIsValid;
	}
	public EnumConst getEnumConstByFlagOperationStatus() {
		return enumConstByFlagOperationStatus;
	}
	public void setEnumConstByFlagOperationStatus(
			EnumConst enumConstByFlagOperationStatus) {
		this.enumConstByFlagOperationStatus = enumConstByFlagOperationStatus;
	}
	public EnumConst getEnumConstByFlagIsRecommends() {
		return enumConstByFlagIsRecommends;
	}
	public void setEnumConstByFlagIsRecommends(EnumConst enumConstByFlagIsRecommends) {
		this.enumConstByFlagIsRecommends = enumConstByFlagIsRecommends;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public EnumConst getEnumConstByFlagCourseItemType() {
		return enumConstByFlagCourseItemType;
	}
	public void setEnumConstByFlagCourseItemType(
			EnumConst enumConstByFlagCourseItemType) {
		this.enumConstByFlagCourseItemType = enumConstByFlagCourseItemType;
	}
	public EnumConst getEnumConstByFlagContentProperty() {
		return enumConstByFlagContentProperty;
	}
	public void setEnumConstByFlagContentProperty(
			EnumConst enumConstByFlagContentProperty) {
		this.enumConstByFlagContentProperty = enumConstByFlagContentProperty;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}



	public String getTimes() {
		return times;
	}
	public EnumConst getEnumConstByFlagCourseCategory() {
		return enumConstByFlagCourseCategory;
	}
	public void setEnumConstByFlagCourseCategory(
			EnumConst enumConstByFlagCourseCategory) {
		this.enumConstByFlagCourseCategory = enumConstByFlagCourseCategory;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getCreateOrganizationName() {
		return createOrganizationName;
	}
	public void setCreateOrganizationName(String createOrganizationName) {
		this.createOrganizationName = createOrganizationName;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
}
