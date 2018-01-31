package com.whaty.platform.entity.bean;
import java.util.Date;

public class PrVoteSubQuestion extends com.whaty.platform.entity.bean.AbstractBean
implements java.io.Serializable {
	private String id;
	private SsoUser ssoUser;
	private PrVoteQuestion prVoteQuestion;
	private String itemContent;
	private Date createDate;
	private String isCustom;
	public String getIsCustom() {
		return isCustom;
	}
	public void setIsCustom(String isCustom) {
		this.isCustom = isCustom;
	}
	public PrVoteSubQuestion(){};
	public PrVoteSubQuestion(String id,SsoUser ssoUser,PrVoteQuestion prVoteQuestion,String itemContent,Date createDate){
		this.id=id;
		this.ssoUser=ssoUser;
		this.prVoteQuestion=prVoteQuestion;
		this.itemContent=itemContent;
		this.createDate=createDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public SsoUser getSsoUser() {
		return ssoUser;
	}
	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}
	public PrVoteQuestion getPrVoteQuestion() {
		return prVoteQuestion;
	}
	public void setPrVoteQuestion(PrVoteQuestion prVoteQuestion) {
		this.prVoteQuestion = prVoteQuestion;
	}
	public String getItemContent() {
		return itemContent;
	}
	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
	
}
