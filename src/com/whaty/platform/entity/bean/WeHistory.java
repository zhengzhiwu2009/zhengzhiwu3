package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * 直播用户访问历史记录实体
 * 
 * @author Lee 2014年6月20日
 * 
 */
public class WeHistory extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {
	/* 主键 */
	private String whId;
	/* 学员LOGIN_ID-昵称 */
	private String whNickname;
	/* 加入时间 */
	private String whJointime;
	/* 离开时间 */
	private String whLeavetime;
	/* IP地址 */
	private String whIp;
	/* 用户ID */
	private String whUid;
	/* 用户名称 */
	private String whName;
	/* 用户邮件 */
	private String whEmail;
	/* 公司名称 */
	private String whCompany;
	/* 区域 */
	private String whAre;
	/* 直播ID */
	private String whWebcastid;
	/* 采集日期 */
	private Date whDate;

	public String getWhId() {
		return whId;
	}

	public void setWhId(String whId) {
		this.whId = whId;
	}

	public String getWhNickname() {
		return whNickname;
	}

	public void setWhNickname(String whNickname) {
		this.whNickname = whNickname;
	}

	public String getWhJointime() {
		return whJointime;
	}

	public void setWhJointime(String whJointime) {
		this.whJointime = whJointime;
	}

	public String getWhLeavetime() {
		return whLeavetime;
	}

	public void setWhLeavetime(String whLeavetime) {
		this.whLeavetime = whLeavetime;
	}

	public String getWhIp() {
		return whIp;
	}

	public void setWhIp(String whIp) {
		this.whIp = whIp;
	}

	public String getWhUid() {
		return whUid;
	}

	public void setWhUid(String whUid) {
		this.whUid = whUid;
	}

	public String getWhName() {
		return whName;
	}

	public void setWhName(String whName) {
		this.whName = whName;
	}

	public String getWhEmail() {
		return whEmail;
	}

	public void setWhEmail(String whEmail) {
		this.whEmail = whEmail;
	}

	public String getWhCompany() {
		return whCompany;
	}

	public void setWhCompany(String whCompany) {
		this.whCompany = whCompany;
	}

	public String getWhAre() {
		return whAre;
	}

	public void setWhAre(String whAre) {
		this.whAre = whAre;
	}

	public String getWhWebcastid() {
		return whWebcastid;
	}

	public void setWhWebcastid(String whWebcastid) {
		this.whWebcastid = whWebcastid;
	}

	public Date getWhDate() {
		return whDate;
	}

	public void setWhDate(Date whDate) {
		this.whDate = whDate;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
