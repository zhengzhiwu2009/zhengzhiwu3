package com.whaty.platform.sso.web.servlet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.bean.PeGrade;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.PeEnterprise;

public class UserSession implements Serializable {
	
	/**
	 * session信息封装
	 */
	private static final long serialVersionUID = 4538822591930338135L;

	public UserSession(){
		
	}
	
	private SsoUser ssoUser;
	
	private String id = "";
	
	private String loginId="";
	
	private String userName="";
	
	private String userLoginType="";
	
	private String roleId="";
	
	private Map userPriority = new HashMap();
	
	private List<PeSite> priSites = new ArrayList<PeSite>();
	
	private List<PeEdutype> priEdutype = new ArrayList<PeEdutype>();
	
	private List<PeGrade> priGrade = new ArrayList<PeGrade>();
	
	private List<PeMajor> priMajor = new ArrayList<PeMajor>();
	
	private List<PeEnterprise> priEnterprises = new ArrayList<PeEnterprise>();
	
	public SsoUser getSsoUser() {
		return ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public List<PeEdutype> getPriEdutype() {
		return priEdutype;
	}

	public void setPriEdutype(List<PeEdutype> priEdutype) {
		this.priEdutype = priEdutype;
	}

	public List<PeGrade> getPriGrade() {
		return priGrade;
	}

	public void setPriGrade(List<PeGrade> priGrade) {
		this.priGrade = priGrade;
	}

	public List<PeMajor> getPriMajor() {
		return priMajor;
	}

	public void setPriMajor(List<PeMajor> priMajor) {
		this.priMajor = priMajor;
	}

	public List<PeSite> getPriSites() {
		return priSites;
	}

	public void setPriSites(List<PeSite> priSites) {
		this.priSites = priSites;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserLoginType() {
		return userLoginType;
	}

	public void setUserLoginType(String userLoginType) {
		this.userLoginType = userLoginType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Map getUserPriority() {
		return userPriority;
	}

	public void setUserPriority(Map userPriority) {
		this.userPriority = userPriority;
	}

	public List<PeEnterprise> getPriEnterprises() {
		return priEnterprises;
	}

	public void setPriEnterprises(List<PeEnterprise> priEnterprises) {
		this.priEnterprises = priEnterprises;
	}

	
}
