/*
 * Question.java
 *
 * Created on 2005��1��6��, ����2:37
 */

package com.whaty.platform.interaction.exam;


/**
 * 考试专栏下辅导答疑
 * @author Administrator
 *
 */
public abstract class ExamQuestion implements com.whaty.platform.Items {

	private String id ;
	
	private String title ;
	
	private String body;
	
	private String publishDate;
	
	private String exambatchId;
	
	private String submituserId;
	
	private String submituserName;
	
	private String submituserType;
	
	private String questionType;
	
	private String clickNum;
	
	private String focus_id;
	
	private String publish_type;

	public String getFocus_id() {
		return focus_id;
	}

	public void setFocus_id(String focus_id) {
		this.focus_id = focus_id;
	}

	public String getPublish_type() {
		return publish_type;
	}

	public void setPublish_type(String publish_type) {
		this.publish_type = publish_type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getExambatchId() {
		return exambatchId;
	}

	public void setExambatchId(String exambatchId) {
		this.exambatchId = exambatchId;
	}

	public String getSubmituserId() {
		return submituserId;
	}

	public void setSubmituserId(String submituserId) {
		this.submituserId = submituserId;
	}

	public String getSubmituserName() {
		return submituserName;
	}

	public void setSubmituserName(String submituserName) {
		this.submituserName = submituserName;
	}

	public String getSubmituserType() {
		return submituserType;
	}

	public void setSubmituserType(String submituserType) {
		this.submituserType = submituserType;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getClickNum() {
		return clickNum;
	}

	public void setClickNum(String clickNum) {
		this.clickNum = clickNum;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

}
