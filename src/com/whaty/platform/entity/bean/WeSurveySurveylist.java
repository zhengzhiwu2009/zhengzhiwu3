package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * 直播用户问卷结果表_主题实体
 * 
 * @author Lee 2014年6月23日
 * 
 */
public class WeSurveySurveylist extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {
	/* 主键 */
	private String wssId;
	/* 问卷主题 */
	private String wssSubject;
	/* 回答时间 */
	private String wssSubmittime;
	/* 直播ID */
	private String wssWebcastid;
	/* 采集日期 */
	private Date wssDate;

	public String getWssId() {
		return wssId;
	}

	public void setWssId(String wssId) {
		this.wssId = wssId;
	}

	public String getWssSubject() {
		return wssSubject;
	}

	public void setWssSubject(String wssSubject) {
		this.wssSubject = wssSubject;
	}

	public String getWssSubmittime() {
		return wssSubmittime;
	}

	public void setWssSubmittime(String wssSubmittime) {
		this.wssSubmittime = wssSubmittime;
	}

	public String getWssWebcastid() {
		return wssWebcastid;
	}

	public void setWssWebcastid(String wssWebcastid) {
		this.wssWebcastid = wssWebcastid;
	}

	public Date getWssDate() {
		return wssDate;
	}

	public void setWssDate(Date wssDate) {
		this.wssDate = wssDate;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
