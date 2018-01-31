package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 直播投票结果表_投票的主题实体
 * 
 * @author Lee 2014年6月20日
 * 
 */
public class WeVoteVotelist extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {
	/* 主键 */
	private String wvvId;
	/* 投票的主题 */
	private String wvvSubject;
	/* 直播id */
	private String wvvWebcastid;
	/* 采集日期 */
	private Date wvvDate;

	// /* 直播投票结果表_问题内容 */
	// private Set weVoteQuestions = new HashSet(0);

	public String getWvvId() {
		return wvvId;
	}

	public void setWvvId(String wvvId) {
		this.wvvId = wvvId;
	}

	public String getWvvSubject() {
		return wvvSubject;
	}

	public void setWvvSubject(String wvvSubject) {
		this.wvvSubject = wvvSubject;
	}

	public String getWvvWebcastid() {
		return wvvWebcastid;
	}

	public void setWvvWebcastid(String wvvWebcastid) {
		this.wvvWebcastid = wvvWebcastid;
	}

	public Date getWvvDate() {
		return wvvDate;
	}

	public void setWvvDate(Date wvvDate) {
		this.wvvDate = wvvDate;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
