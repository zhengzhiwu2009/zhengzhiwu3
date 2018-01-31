package com.whaty.platform.entity.bean;

import java.util.Date;

public class UserReply  extends com.whaty.platform.entity.bean.AbstractBean
implements java.io.Serializable{

	private String id;//主键
	private UserIssue UserIssueuuid;//关联问题表ID
	private String reply;//回复信息
	private Date replydate;//回复时间
	private SsoUser ssoUser;// 回复人
	
	public UserReply() {
		super();
	}

	public UserReply(String id, UserIssue userIssueuuid, String reply,
			Date replydate, SsoUser ssoUser) {
		super();
		this.id = id;
		UserIssueuuid = userIssueuuid;
		this.reply = reply;
		this.replydate = replydate;
		this.ssoUser = ssoUser;
	}

	public String getId() {
		return this.id;
	}

	public UserIssue getUserIssueuuid() {
		return UserIssueuuid;
	}
	public void setUserIssueuuid(UserIssue userIssueuuid) {
		UserIssueuuid = userIssueuuid;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public Date getReplydate() {
		return replydate;
	}
	public void setReplydate(Date replydate) {
		this.replydate = replydate;
	}
	public SsoUser getSsoUser() {
		return ssoUser;
	}
	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}
	public void setId(String id) {
		this.id = id;
	}

}
