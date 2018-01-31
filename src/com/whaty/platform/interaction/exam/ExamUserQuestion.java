/*
 * Answer.java
 *
 * Created on 2005��1��6��, ����2:37
 */

package com.whaty.platform.interaction.exam;

/**
 * 考试专栏下辅导答疑
 * @author Administrator
 *
 */
public abstract class ExamUserQuestion implements com.whaty.platform.Items {

	private String id; 

	private ExamQuestion question;//问题ID

	private String userId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ExamQuestion getQuestion() {
		return question;
	}

	public void setQuestion(ExamQuestion question) {
		this.question = question;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}
