package com.whaty.platform.entity.bean;

/**
 * 直播用户问卷结果表_回答实体
 * 
 * @author Lee 2014年6月23日
 * 
 */
public class WeSurveyResponse extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {
	/* 主键 */
	private String wsrId;
	/* 用户名字 */
	private String wsrName;
	/* 用户id */
	private String wsrUid;
	/* 回答 */
	private String wsrResponse;
	/* 问题id */
	private String wsrWsqId;

	public String getWsrId() {
		return wsrId;
	}

	public void setWsrId(String wsrId) {
		this.wsrId = wsrId;
	}

	public String getWsrName() {
		return wsrName;
	}

	public void setWsrName(String wsrName) {
		this.wsrName = wsrName;
	}

	public String getWsrUid() {
		return wsrUid;
	}

	public void setWsrUid(String wsrUid) {
		this.wsrUid = wsrUid;
	}

	public String getWsrResponse() {
		return wsrResponse;
	}

	public void setWsrResponse(String wsrResponse) {
		this.wsrResponse = wsrResponse;
	}

	public String getWsrWsqId() {
		return wsrWsqId;
	}

	public void setWsrWsqId(String wsrWsqId) {
		this.wsrWsqId = wsrWsqId;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

}