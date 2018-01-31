package com.whaty.platform.entity.bean;

/**
 * 直播用户问卷结果表_问题实体
 * 
 * @author Lee 2014年6月23日
 * 
 */
public class WeSurveyQuestion extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {
	/* 主键 */
	private String wsqId;
	/* 问题 */
	private String wsqQuestion;
	/* 问卷结果表_主题id */
	private String wsqWssId;

	public String getWsqId() {
		return wsqId;
	}

	public void setWsqId(String wsqId) {
		this.wsqId = wsqId;
	}

	public String getWsqQuestion() {
		return wsqQuestion;
	}

	public void setWsqQuestion(String wsqQuestion) {
		this.wsqQuestion = wsqQuestion;
	}

	public String getWsqWssId() {
		return wsqWssId;
	}

	public void setWsqWssId(String wsqWssId) {
		this.wsqWssId = wsqWssId;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

}