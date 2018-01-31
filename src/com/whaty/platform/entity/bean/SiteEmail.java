package com.whaty.platform.entity.bean;

import java.util.Date;

public class SiteEmail extends com.whaty.platform.entity.bean.AbstractBean implements java.io.Serializable{
	private String id;
	private String addresseeId;
	private String addresseeName;
	private String senderId;
	private String senderName;
	private String content;
	private Date senddate;
	private String status;
	private String title;
	private Long addresseeDel;
	private Long senderDel;
	public Long getAddresseeDel() {
		return addresseeDel;
	}
	public void setAddresseeDel(Long addresseeDel) {
		this.addresseeDel = addresseeDel;
	}
	public Long getSenderDel() {
		return senderDel;
	}
	public void setSenderDel(Long senderDel) {
		this.senderDel = senderDel;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public SiteEmail(){
		
	}
	public SiteEmail(String id,String addresseeId,String addresseeName,String senderId,String senderName,String content,Date senddate,String status){
		this.id=id;
		this.addresseeId=addresseeId;
		this.addresseeName=addresseeName;
		this.senderId=senderId;
		this.senderName=senderName;
		this.content=content;
		this.senddate=senddate;
		this.status=status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddresseeId() {
		return addresseeId;
	}
	public void setAddresseeId(String addresseeId) {
		this.addresseeId = addresseeId;
	}
	public String getAddresseeName() {
		return addresseeName;
	}
	public void setAddresseeName(String addresseeName) {
		this.addresseeName = addresseeName;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getSenddate() {
		return senddate;
	}
	public void setSenddate(Date senddate) {
		this.senddate = senddate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
