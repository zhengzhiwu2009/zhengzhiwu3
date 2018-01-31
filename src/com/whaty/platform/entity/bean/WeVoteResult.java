package com.whaty.platform.entity.bean;

/**
 * 直播投票结果表_问题的回答实体
 * 
 * @author Lee 2014年6月20日
 * 
 */
public class WeVoteResult extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {
	/* 主键 */
	private String wvrId;
	/* 昵称 */
	private String wvrNickname;
	/* 回答（当非选择题） */
	private String wvrAnswer;
	/* 用户ID */
	private String wvrUid;
	/* 问题值ID */
	private String wvrWvoId;

	public String getWvrId() {
		return wvrId;
	}

	public void setWvrId(String wvrId) {
		this.wvrId = wvrId;
	}

	public String getWvrNickname() {
		return wvrNickname;
	}

	public void setWvrNickname(String wvrNickname) {
		this.wvrNickname = wvrNickname;
	}

	public String getWvrAnswer() {
		return wvrAnswer;
	}

	public void setWvrAnswer(String wvrAnswer) {
		this.wvrAnswer = wvrAnswer;
	}

	public String getWvrUid() {
		return wvrUid;
	}

	public void setWvrUid(String wvrUid) {
		this.wvrUid = wvrUid;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getWvrWvoId() {
		return wvrWvoId;
	}

	public void setWvrWvoId(String wvrWvoId) {
		this.wvrWvoId = wvrWvoId;
	}

}
