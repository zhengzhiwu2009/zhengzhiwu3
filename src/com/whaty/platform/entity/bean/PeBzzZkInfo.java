package com.whaty.platform.entity.bean;

import java.io.Serializable;
import java.util.Date;

public class PeBzzZkInfo extends AbstractBean implements Serializable {

	// Fields
	
	private String id;
	private String givedPersonNum;	//给出人数
	private String receivedPersonNum;	//收到人数
	private PeEnterprise peEnterprise;	//企业ID
	private PeBzzBatch peBzzBatch;	//学期ID
	private Date luruDate;  //录入时间
	private String luruPeople; // 录入人
	private String bz; // 备注
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGivedPersonNum() {
		return givedPersonNum;
	}
	public void setGivedPersonNum(String givedPersonNum) {
		this.givedPersonNum = givedPersonNum;
	}
	public String getReceivedPersonNum() {
		return receivedPersonNum;
	}
	public void setReceivedPersonNum(String receivedPersonNum) {
		this.receivedPersonNum = receivedPersonNum;
	}
	public PeEnterprise getPeEnterprise() {
		return peEnterprise;
	}
	public void setPeEnterprise(PeEnterprise peEnterprise) {
		this.peEnterprise = peEnterprise;
	}
	public PeBzzBatch getPeBzzBatch() {
		return peBzzBatch;
	}
	public void setPeBzzBatch(PeBzzBatch peBzzBatch) {
		this.peBzzBatch = peBzzBatch;
	}
	public Date getLuruDate() {
		return luruDate;
	}
	public void setLuruDate(Date luruDate) {
		this.luruDate = luruDate;
	}
	public String getLuruPeople() {
		return luruPeople;
	}
	public void setLuruPeople(String luruPeople) {
		this.luruPeople = luruPeople;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	
	

}
