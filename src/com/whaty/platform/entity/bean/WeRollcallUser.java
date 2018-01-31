package com.whaty.platform.entity.bean;

/**
 * 直播用户点名签到明细表_USERS实体
 * 
 * @author Lee 2014年6月23日
 * 
 */
public class WeRollcallUser extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {
	/* 主键 */
	private String wruId;
	/* 用户id */
	private String wruUid;
	/* 用户名字 */
	private String wruName;
	/* 是否出席 */
	private String wruPresent;
	/* 签到表id */
	private String wruWrId;

	public String getWruId() {
		return wruId;
	}

	public void setWruId(String wruId) {
		this.wruId = wruId;
	}

	public String getWruUid() {
		return wruUid;
	}

	public void setWruUid(String wruUid) {
		this.wruUid = wruUid;
	}

	public String getWruName() {
		return wruName;
	}

	public void setWruName(String wruName) {
		this.wruName = wruName;
	}

	public String getWruPresent() {
		return wruPresent;
	}

	public void setWruPresent(String wruPresent) {
		this.wruPresent = wruPresent;
	}

	public String getWruWrId() {
		return wruWrId;
	}

	public void setWruWrId(String wruWrId) {
		this.wruWrId = wruWrId;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}
}