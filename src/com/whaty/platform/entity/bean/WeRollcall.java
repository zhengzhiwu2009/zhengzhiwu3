package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * 直播用户点名签到表实体
 * 
 * @author Lee 2014年6月23日
 * 
 */
public class WeRollcall extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {
	/* 主键 */
	private String wrId;
	/* 开始点名时间 */
	private String wrStarttime;
	/* 结束点名时间 */
	private String wrEndtime;
	/* 总数 */
	private String wrTotal;
	/* 出席人数 */
	private String wrPresent;
	/* 缺席人数 */
	private String wrAbsent;
	/* 直播id */
	private String wrWebcastid;
	/* 采集日期 */
	private Date wrDate;

	public String getWrId() {
		return wrId;
	}

	public void setWrId(String wrId) {
		this.wrId = wrId;
	}

	public String getWrStarttime() {
		return wrStarttime;
	}

	public void setWrStarttime(String wrStarttime) {
		this.wrStarttime = wrStarttime;
	}

	public String getWrEndtime() {
		return wrEndtime;
	}

	public void setWrEndtime(String wrEndtime) {
		this.wrEndtime = wrEndtime;
	}

	public String getWrTotal() {
		return wrTotal;
	}

	public void setWrTotal(String wrTotal) {
		this.wrTotal = wrTotal;
	}

	public String getWrPresent() {
		return wrPresent;
	}

	public void setWrPresent(String wrPresent) {
		this.wrPresent = wrPresent;
	}

	public String getWrAbsent() {
		return wrAbsent;
	}

	public void setWrAbsent(String wrAbsent) {
		this.wrAbsent = wrAbsent;
	}

	public String getWrWebcastid() {
		return wrWebcastid;
	}

	public void setWrWebcastid(String wrWebcastid) {
		this.wrWebcastid = wrWebcastid;
	}

	public Date getWrDate() {
		return wrDate;
	}

	public void setWrDate(Date wrDate) {
		this.wrDate = wrDate;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}
}