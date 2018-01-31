package com.whaty.platform.entity.bean;

import java.util.Date;

public class PrVoteUserQuestion extends com.whaty.platform.entity.bean.AbstractBean
implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private PrVoteQuestion prVoteQuestion;//所投问题
	private String voteResult;//格式为：选项1，选项2，选项3...
	private Date createDate = new Date();//投票时间
	private SsoUser ssoUser;//投票人
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public PrVoteQuestion getPrVoteQuestion() {
		return prVoteQuestion;
	}
	public void setPrVoteQuestion(PrVoteQuestion prVoteQuestion) {
		this.prVoteQuestion = prVoteQuestion;
	}
	public String getVoteResult() {
		return voteResult;
	}
	public void setVoteResult(String voteResult) {
		this.voteResult = voteResult;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public SsoUser getSsoUser() {
		return ssoUser;
	}
	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}
	
}
