package com.whaty.platform.entity.bean;

public class LogUserInfo extends com.whaty.platform.entity.bean.AbstractBean
implements java.io.Serializable{
	private String id;
	private String userid;
	private String name;
	private String qxid;
	private String operatetime;
	private String operatetype;
	private String operatestatus;
	private String operateent;
	private String opkey;
	private String opip;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQxid() {
		return qxid;
	}
	public void setQxid(String qxid) {
		this.qxid = qxid;
	}
	public String getOperatetime() {
		return operatetime;
	}
	public void setOperatetime(String operatetime) {
		this.operatetime = operatetime;
	}
	public String getOperatetype() {
		return operatetype;
	}
	public void setOperatetype(String operatetype) {
		this.operatetype = operatetype;
	}
	public String getOperatestatus() {
		return operatestatus;
	}
	public void setOperatestatus(String operatestatus) {
		this.operatestatus = operatestatus;
	}
	public String getOperateent() {
		return operateent;
	}
	public void setOperateent(String operateent) {
		this.operateent = operateent;
	}
	public String getOpkey() {
		return opkey;
	}
	public void setOpkey(String opkey) {
		this.opkey = opkey;
	}
	public String getOpip() {
		return opip;
	}
	public void setOpip(String opip) {
		this.opip = opip;
	}
}
