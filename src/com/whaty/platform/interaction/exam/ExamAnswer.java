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
public abstract class ExamAnswer implements com.whaty.platform.Items {

	private String id; 

	private ExamQuestion question;//问题ID

	private String body;//内容

	private String publishDate;//回答时间

	private String publishId;//回答者ID

	private String publishName;//回答者姓名

	private String publishType;//回答者类型

	private String isKey;//是否是最佳答案

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getPublishId() {
		return publishId;
	}

	public void setPublishId(String publishId) {
		this.publishId = publishId;
	}

	public String getPublishName() {
		return publishName;
	}

	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}

	public String getPublishType() {
		return publishType;
	}

	public void setPublishType(String publishType) {
		this.publishType = publishType;
	}

	public String getIsKey() {
		return isKey;
	}

	public void setIsKey(String isKey) {
		this.isKey = isKey;
	}

	public ExamQuestion getQuestion() {
		return question;
	}

	public void setQuestion(ExamQuestion question) {
		this.question = question;
	}

}
