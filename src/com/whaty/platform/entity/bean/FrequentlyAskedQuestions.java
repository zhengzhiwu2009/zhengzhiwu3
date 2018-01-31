package com.whaty.platform.entity.bean;

import java.util.Date;

public class FrequentlyAskedQuestions extends com.whaty.platform.entity.bean.AbstractBean implements java.io.Serializable {
	/* 主键 */
	private String id;
	/* 问题标题 */
	private String title;
	/* 问题类型 */
	private EnumConst enumConstByFlagFrequentlyQuestionType;
	/* 关键词 */
	private String keywords;
	/* 提问者角色 */
	private String roleIds;
	/* 问题描述 */
	private String questionDescription;
	/* 解决方案 */
	private String solution;
	/* 附件地址 */
	private String fileLink;
	/* 备注 */
	private String remarks;
	/* 创建时间 */
	private Date createDate;
	private String roles;
	
	private String types;
	private String typesIds;
	//浏览次数
	private int views;
	
	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public FrequentlyAskedQuestions(String title, EnumConst enumConstByFlagFrequentlyQuestionType, String keywords, String roles, Date createDate) {
		this.title = title;
		this.enumConstByFlagFrequentlyQuestionType = enumConstByFlagFrequentlyQuestionType;
		this.keywords = keywords;
		this.roles = roles;
		this.createDate = createDate;
	}
	
	public FrequentlyAskedQuestions() {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public EnumConst getEnumConstByFlagFrequentlyQuestionType() {
		return enumConstByFlagFrequentlyQuestionType;
	}
	public void setEnumConstByFlagFrequentlyQuestionType(
			EnumConst enumConstByFlagFrequentlyQuestionType) {
		this.enumConstByFlagFrequentlyQuestionType = enumConstByFlagFrequentlyQuestionType;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getQuestionDescription() {
		return questionDescription;
	}

	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getFileLink() {
		return fileLink;
	}

	public void setFileLink(String fileLink) {
		this.fileLink = fileLink;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getTypesIds() {
		return typesIds;
	}

	public void setTypesIds(String typesIds) {
		this.typesIds = typesIds;
	}
	
}
