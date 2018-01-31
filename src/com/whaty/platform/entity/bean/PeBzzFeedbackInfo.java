package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeBzzFeedbackInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBzzFeedbackInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private String id;				//自动编号
	private String title;			//标题
	private String content;			//反馈内容
	private SsoUser ssoUser;		//添加人
	private Date addDate;			//添加日期
	private String phone;			//联系电话
//	private String status;			//状态1为有效0为无效
	private EnumConst enumConstByFlagFeedbackStatus;	//状态
	private String author;	//发布人
	private Set<PeBzzReplyInfo> peBzzReplyInfos = new HashSet<PeBzzReplyInfo>(0);

	// Constructors

	/** default constructor */
	public PeBzzFeedbackInfo() {
	}

	/** full constructor */
	public PeBzzFeedbackInfo(String title, String content, SsoUser ssoUser,
			Date addDate, String phone, EnumConst enumConstByFlagFeedbackStatus, Set<PeBzzReplyInfo> peBzzReplyInfos) {
		this.title = title;
		this.content = content;
		this.ssoUser = ssoUser;
		this.addDate = addDate;
		this.phone = phone;
		this.enumConstByFlagFeedbackStatus = enumConstByFlagFeedbackStatus;
		this.peBzzReplyInfos = peBzzReplyInfos;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<PeBzzReplyInfo> getPeBzzReplyInfos() {
		return this.peBzzReplyInfos;
	}

	public void setPeBzzReplyInfos(Set<PeBzzReplyInfo> peBzzReplyInfos) {
		this.peBzzReplyInfos = peBzzReplyInfos;
	}

	public SsoUser getSsoUser() {
		return ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	public EnumConst getEnumConstByFlagFeedbackStatus() {
		return enumConstByFlagFeedbackStatus;
	}

	public void setEnumConstByFlagFeedbackStatus(
			EnumConst enumConstByFlagFeedbackStatus) {
		this.enumConstByFlagFeedbackStatus = enumConstByFlagFeedbackStatus;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}