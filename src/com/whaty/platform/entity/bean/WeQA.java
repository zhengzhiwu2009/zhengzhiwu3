package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * 直播用户提问数据表实体
 * 
 * @author Lee 2014年6月23日
 * 
 */
public class WeQA extends com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {
	/* 主键 */
	private String wqId;
	/* 提问时间 */
	private String wqSubmittime;
	/* 是否发布 */
	private String wqPublished;
	/* 问题 */
	private String wqQuestion;
	/* 回答 */
	private String wqResponse;
	/* 提问者userid */
	private String wqSubmitter;
	/* 回答者userid */
	private String wqAnswerby;
	/* 回答用户的名字 */
	private String wqName;
	/* 直播id */
	private String wqWebcastid;
	/* 采集日期 */
	private Date wqDate;

	public String getWqId() {
		return wqId;
	}

	public void setWqId(String wqId) {
		this.wqId = wqId;
	}

	public String getWqSubmittime() {
		return wqSubmittime;
	}

	public void setWqSubmittime(String wqSubmittime) {
		this.wqSubmittime = wqSubmittime;
	}

	public String getWqPublished() {
		return wqPublished;
	}

	public void setWqPublished(String wqPublished) {
		this.wqPublished = wqPublished;
	}

	public String getWqQuestion() {
		return wqQuestion;
	}

	public void setWqQuestion(String wqQuestion) {
		this.wqQuestion = wqQuestion;
	}

	public String getWqResponse() {
		return wqResponse;
	}

	public void setWqResponse(String wqResponse) {
		this.wqResponse = wqResponse;
	}

	public String getWqSubmitter() {
		return wqSubmitter;
	}

	public void setWqSubmitter(String wqSubmitter) {
		this.wqSubmitter = wqSubmitter;
	}

	public String getWqAnswerby() {
		return wqAnswerby;
	}

	public void setWqAnswerby(String wqAnswerby) {
		this.wqAnswerby = wqAnswerby;
	}

	public String getWqName() {
		return wqName;
	}

	public void setWqName(String wqName) {
		this.wqName = wqName;
	}

	public String getWqWebcastid() {
		return wqWebcastid;
	}

	public void setWqWebcastid(String wqWebcastid) {
		this.wqWebcastid = wqWebcastid;
	}

	public Date getWqDate() {
		return wqDate;
	}

	public void setWqDate(Date wqDate) {
		this.wqDate = wqDate;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}
}