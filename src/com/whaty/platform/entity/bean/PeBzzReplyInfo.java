package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeBzzReplyInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
/**
 * @author Administrator
 * @since 2012-02-23
 * @version v1.1.1
 * 平台反馈回复信息类
 */
public class PeBzzReplyInfo extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;								//编号
	private PeBzzFeedbackInfo peBzzFeedbackInfo;	//所对应的反馈
	private String content;							//回复的内容
	private SsoUser ssoUser;						//回复人
	private Date addDate;							//回复日期
//	private String status;							//回复状态
	private EnumConst enumConstByFlagReplyStatus;	//回复状态
	private String author;	//发布人

	// Constructors

	/** default constructor */
	public PeBzzReplyInfo() {
	}

	/** full constructor */
	public PeBzzReplyInfo(PeBzzFeedbackInfo peBzzFeedbackInfo, String content,
			SsoUser ssoUser, Date addDate, EnumConst enumConstByFlagReplyStatus) {
		this.peBzzFeedbackInfo = peBzzFeedbackInfo;
		this.content = content;
		this.ssoUser = ssoUser;
		this.addDate = addDate;
		this.enumConstByFlagReplyStatus = enumConstByFlagReplyStatus;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeBzzFeedbackInfo getPeBzzFeedbackInfo() {
		return this.peBzzFeedbackInfo;
	}

	public void setPeBzzFeedbackInfo(PeBzzFeedbackInfo peBzzFeedbackInfo) {
		this.peBzzFeedbackInfo = peBzzFeedbackInfo;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getAddDate() {
		return this.addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public SsoUser getSsoUser() {
		return ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	public EnumConst getEnumConstByFlagReplyStatus() {
		return enumConstByFlagReplyStatus;
	}

	public void setEnumConstByFlagReplyStatus(EnumConst enumConstByFlagReplyStatus) {
		this.enumConstByFlagReplyStatus = enumConstByFlagReplyStatus;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}