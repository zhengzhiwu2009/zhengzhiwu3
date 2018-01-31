package com.whaty.platform.entity.bean;

/**
 * 直播投票结果表_问题的值实体
 * 
 * @author Lee 2014年6月20日
 * 
 */
public class WeVoteOption extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {
	/* 主键 */
	private String wvoId;
	/* 问题的值 */
	private String wvoValue;
	/* 投票结果 */
	private String wvoResult;
	/* 百分比 */
	private String wvoPrecentage;
	/* 问题内容ID */
	private String wvoWvqId;

	public String getWvoId() {
		return wvoId;
	}

	public void setWvoId(String wvoId) {
		this.wvoId = wvoId;
	}

	public String getWvoValue() {
		return wvoValue;
	}

	public void setWvoValue(String wvoValue) {
		this.wvoValue = wvoValue;
	}

	public String getWvoResult() {
		return wvoResult;
	}

	public void setWvoResult(String wvoResult) {
		this.wvoResult = wvoResult;
	}

	public String getWvoPrecentage() {
		return wvoPrecentage;
	}

	public void setWvoPrecentage(String wvoPrecentage) {
		this.wvoPrecentage = wvoPrecentage;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getWvoWvqId() {
		return wvoWvqId;
	}

	public void setWvoWvqId(String wvoWvqId) {
		this.wvoWvqId = wvoWvqId;
	}

}
