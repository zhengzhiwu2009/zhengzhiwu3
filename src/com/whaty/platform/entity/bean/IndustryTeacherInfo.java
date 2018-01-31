package com.whaty.platform.entity.bean;

import java.util.Date;

public class IndustryTeacherInfo extends AbstractBean implements java.io.Serializable {
	private String id;//主键ID
	private String name;//讲师姓名
	private PeEnterprise organization;//所在机构
	private String organization_name;//所在机构名称
	private String departmentName;//工作部门
	private String code;//证件号
	private String mobile;//联系电话
	private String email;//电子邮件
	private String description;//讲师简介
	private String tfc_id;//授课领域ID
	private String tfc_name;//授课领域中文
	private PeEnterprise createOrganization;//提交机构
	private String createOrganizationName;//提交机构名称
	private EnumConst enumConstFlagIsValid;//是否有效
	private EnumConst enumConstFlagOperationStatus;//处理状态
	private EnumConst enumConstFlagIsRecommends;//是否推荐到协会
	private Date createDate;//创建时间
	private String courseId;
	private String loginId;
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PeEnterprise getOrganization() {
		return organization;
	}
	public void setOrganization(PeEnterprise organization) {
		this.organization = organization;
	}
	public String getOrganization_name() {
		return organization_name;
	}
	public void setOrganization_name(String organization_name) {
		this.organization_name = organization_name;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTfc_id() {
		return tfc_id;
	}
	public void setTfc_id(String tfc_id) {
		this.tfc_id = tfc_id;
	}
	public String getTfc_name() {
		return tfc_name;
	}
	public void setTfc_name(String tfc_name) {
		this.tfc_name = tfc_name;
	}
	public PeEnterprise getCreateOrganization() {
		return createOrganization;
	}
	public void setCreateOrganization(PeEnterprise createOrganization) {
		this.createOrganization = createOrganization;
	}
	public String getCreateOrganizationName() {
		return createOrganizationName;
	}
	public void setCreateOrganizationName(String createOrganizationName) {
		this.createOrganizationName = createOrganizationName;
	}
	public EnumConst getEnumConstFlagIsValid() {
		return enumConstFlagIsValid;
	}
	public void setEnumConstFlagIsValid(EnumConst enumConstFlagIsValid) {
		this.enumConstFlagIsValid = enumConstFlagIsValid;
	}
	public EnumConst getEnumConstFlagOperationStatus() {
		return enumConstFlagOperationStatus;
	}
	public void setEnumConstFlagOperationStatus(
			EnumConst enumConstFlagOperationStatus) {
		this.enumConstFlagOperationStatus = enumConstFlagOperationStatus;
	}
	public EnumConst getEnumConstFlagIsRecommends() {
		return enumConstFlagIsRecommends;
	}
	public void setEnumConstFlagIsRecommends(EnumConst enumConstFlagIsRecommends) {
		this.enumConstFlagIsRecommends = enumConstFlagIsRecommends;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
