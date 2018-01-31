package com.whaty.log;

import java.util.Date;

import com.whaty.platform.entity.bean.AbstractBean;

public class Loginfo extends AbstractBean implements java.io.Serializable{
	
	private String id; //主键id
	
	private String sessionId; //sessionId
	
	private String userCode; //登录账号
	
	private String modeType; //操作类型
	
	private String executeDetail; //执行动作
	
	private String writeValue; //输入值
	
	private String readValue; //输出值
	
	private String ipAdress; //IP地址
	
	private String executeStatus; //执行状态
	
	private String loginPost; //登录端
	
	private Date executeDate; //登录时间
	
	private String url; //请求路径
	
	private String logConfigId;
	
	private String userName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLogConfigId() {
		return logConfigId;
	}

	public void setLogConfigId(String logConfigId) {
		this.logConfigId = logConfigId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getModeType() {
		return modeType;
	}

	public void setModeType(String modeType) {
		this.modeType = modeType;
	}

	public String getExecuteDetail() {
		return executeDetail;
	}

	public void setExecuteDetail(String executeDetail) {
		this.executeDetail = executeDetail;
	}

	public String getWriteValue() {
		return writeValue;
	}

	public void setWriteValue(String writeValue) {
		this.writeValue = writeValue;
	}

	public String getReadValue() {
		return readValue;
	}

	public void setReadValue(String readValue) {
		this.readValue = readValue;
	}

	public String getIpAdress() {
		return ipAdress;
	}

	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}

	public String getLoginPost() {
		return loginPost;
	}

	public void setLoginPost(String loginPost) {
		this.loginPost = loginPost;
	}

	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public String getExecuteStatus() {
		return executeStatus;
	}

	public void setExecuteStatus(String executeStatus) {
		this.executeStatus = executeStatus;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
