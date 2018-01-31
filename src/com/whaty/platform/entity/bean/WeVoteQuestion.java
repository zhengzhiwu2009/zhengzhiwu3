package com.whaty.platform.entity.bean;

/**
 * 直播投票结果表_问题内容实体
 * 
 * @author Lee 2014年6月20日
 * 
 */
public class WeVoteQuestion extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {
	/* 主键 */
	private String wvqId;
	/* 问题内容 */
	private String wvqContent;
	/* 投票的主题ID */
	private String wvqWvvid;
	public String getWvqId() {
		return wvqId;
	}
	public void setWvqId(String wvqId) {
		this.wvqId = wvqId;
	}
	public String getWvqContent() {
		return wvqContent;
	}
	public void setWvqContent(String wvqContent) {
		this.wvqContent = wvqContent;
	}
	public String getWvqWvvid() {
		return wvqWvvid;
	}
	public void setWvqWvvid(String wvqWvvid) {
		this.wvqWvvid = wvqWvvid;
	}
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}
}
