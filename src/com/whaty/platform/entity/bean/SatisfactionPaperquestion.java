package com.whaty.platform.entity.bean;

/**
 * SatisfactionPaperquestion entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SatisfactionPaperquestion extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private SatisfactionSurveyPaperInfo satisfactionSurveyPaperInfo;
	private SatisfactionQuestionInfo satisfactionQuestionInfo;

	// Constructors

	/** default constructor */
	public SatisfactionPaperquestion() {
	}

	/** full constructor */
	public SatisfactionPaperquestion(
			SatisfactionSurveyPaperInfo satisfactionSurveyPaperInfo,
			SatisfactionQuestionInfo satisfactionQuestionInfo) {
		this.satisfactionSurveyPaperInfo = satisfactionSurveyPaperInfo;
		this.satisfactionQuestionInfo = satisfactionQuestionInfo;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SatisfactionSurveyPaperInfo getSatisfactionSurveyPaperInfo() {
		return this.satisfactionSurveyPaperInfo;
	}

	public void setSatisfactionSurveyPaperInfo(
			SatisfactionSurveyPaperInfo satisfactionSurveyPaperInfo) {
		this.satisfactionSurveyPaperInfo = satisfactionSurveyPaperInfo;
	}

	public SatisfactionQuestionInfo getSatisfactionQuestionInfo() {
		return this.satisfactionQuestionInfo;
	}

	public void setSatisfactionQuestionInfo(
			SatisfactionQuestionInfo satisfactionQuestionInfo) {
		this.satisfactionQuestionInfo = satisfactionQuestionInfo;
	}

}