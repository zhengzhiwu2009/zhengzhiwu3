package com.whaty.platform.entity.bean;

import java.util.Date;

public class UserIssue extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	private String id;// 主键ID
	private String topic;// 题目
	private EnumConst enumConstByFlagUItype;// 类别
	private EnumConst enumConstByFlagUidispose;// 处理结果
	private SsoUser ssoUser;// 提问人
	private String phone;// 手机号 联系方式
	private Date whodate; // 提问时间
	private Date relydate;// 最后一次回复时间
	private String issuedescribe;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public EnumConst getEnumConstByFlagUItype() {
		return enumConstByFlagUItype;
	}
	public void setEnumConstByFlagUItype(EnumConst enumConstByFlagUItype) {
		this.enumConstByFlagUItype = enumConstByFlagUItype;
	}
	public EnumConst getEnumConstByFlagUidispose() {
		return enumConstByFlagUidispose;
	}
	public void setEnumConstByFlagUidispose(EnumConst enumConstByFlagUidispose) {
		this.enumConstByFlagUidispose = enumConstByFlagUidispose;
	}
	public SsoUser getSsoUser() {
		return ssoUser;
	}
	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getWhodate() {
		return whodate;
	}
	public void setWhodate(Date whodate) {
		this.whodate = whodate;
	}
	public Date getRelydate() {
		return relydate;
	}
	public void setRelydate(Date relydate) {
		this.relydate = relydate;
	}
	public String getIssuedescribe() {
		return issuedescribe;
	}
	public void setIssuedescribe(String issuedescribe) {
		this.issuedescribe = issuedescribe;
	}
	public UserIssue(String id, String topic, EnumConst enumConstByFlagUItype,
			EnumConst enumConstByFlagUidispose, SsoUser ssoUser, String phone,
			Date whodate, Date relydate, String issuedescribe) {
		super();
		this.id = id;
		this.topic = topic;
		this.enumConstByFlagUItype = enumConstByFlagUItype;
		this.enumConstByFlagUidispose = enumConstByFlagUidispose;
		this.ssoUser = ssoUser;
		this.phone = phone;
		this.whodate = whodate;
		this.relydate = relydate;
		this.issuedescribe = issuedescribe;
	}
	public UserIssue() {
		super();
	}

}