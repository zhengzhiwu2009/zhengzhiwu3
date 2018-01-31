package com.whaty.platform.entity.bean;

import java.util.Date;

public class PrBzzTchStuElectiveHistory extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	private String id;
	private EnumConst enumConstByFlagScorePub;
	private SsoUser ssoUser;
	private EnumConst enumConstByFlagScoreStatus;
	private PrBzzTchOpencourse prBzzTchOpencourse;
	private PeBzzStudent peBzzStudent;
	private Date electiveDate;
	private Double scoreExam;
	private Integer examTimes;
	private String onlineTime;
	private EnumConst enumConstByFlagElectivePayStatus;   //选课支付状态
	private PeBzzOrderInfo peBzzOrderInfo;
	private PeElectiveCoursePeriod peElectiveCoursePeriod;
	private TrainingCourseStudentHistory trainingCourseStudentHistory;

	// Constructors

	/** default constructor */
	public PrBzzTchStuElectiveHistory() {
	}

	/** minimal constructor */
	public PrBzzTchStuElectiveHistory(Date electiveDate) {
		this.electiveDate = electiveDate;
	}

	/** full constructor */
	public PrBzzTchStuElectiveHistory(EnumConst enumConstByFlagScorePub,
			SsoUser ssoUser, EnumConst enumConstByFlagScoreStatus,
			PrBzzTchOpencourse prBzzTchOpencourse, PeBzzStudent peBzzStudent,
			Date electiveDate, Double scoreExam, String onlineTime,
			PeElectiveCoursePeriod peElectiveCoursePeriod,
			Integer examTimes) {
		this.enumConstByFlagScorePub = enumConstByFlagScorePub;
		this.ssoUser = ssoUser;
		this.enumConstByFlagScoreStatus = enumConstByFlagScoreStatus;
		this.prBzzTchOpencourse = prBzzTchOpencourse;
		this.peBzzStudent = peBzzStudent;
		this.electiveDate = electiveDate;
		this.scoreExam = scoreExam;
		this.examTimes = examTimes;
		this.onlineTime = onlineTime;
		this.peElectiveCoursePeriod = peElectiveCoursePeriod;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConstByFlagScorePub() {
		return this.enumConstByFlagScorePub;
	}

	public void setEnumConstByFlagScorePub(EnumConst enumConstByFlagScorePub) {
		this.enumConstByFlagScorePub = enumConstByFlagScorePub;
	}

	public SsoUser getSsoUser() {
		return this.ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	public EnumConst getEnumConstByFlagScoreStatus() {
		return this.enumConstByFlagScoreStatus;
	}

	public void setEnumConstByFlagScoreStatus(
			EnumConst enumConstByFlagScoreStatus) {
		this.enumConstByFlagScoreStatus = enumConstByFlagScoreStatus;
	}

	public PrBzzTchOpencourse getPrBzzTchOpencourse() {
		return this.prBzzTchOpencourse;
	}

	public void setPrBzzTchOpencourse(PrBzzTchOpencourse prBzzTchOpencourse) {
		this.prBzzTchOpencourse = prBzzTchOpencourse;
	}

	public PeBzzStudent getPeBzzStudent() {
		return this.peBzzStudent;
	}

	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
	}

	public Date getElectiveDate() {
		return this.electiveDate;
	}

	public void setElectiveDate(Date electiveDate) {
		this.electiveDate = electiveDate;
	}

	public Double getScoreExam() {
		return this.scoreExam;
	}

	public void setScoreExam(Double scoreExam) {
		this.scoreExam = scoreExam;
	}

	public String getOnlineTime() {
		return this.onlineTime;
	}

	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}



	public TrainingCourseStudentHistory getTrainingCourseStudentHistory() {
		return trainingCourseStudentHistory;
	}

	public void setTrainingCourseStudentHistory(
			TrainingCourseStudentHistory trainingCourseStudentHistory) {
		this.trainingCourseStudentHistory = trainingCourseStudentHistory;
	}

	public EnumConst getEnumConstByFlagElectivePayStatus() {
		return enumConstByFlagElectivePayStatus;
	}

	public void setEnumConstByFlagElectivePayStatus(
			EnumConst enumConstByFlagElectivePayStatus) {
		this.enumConstByFlagElectivePayStatus = enumConstByFlagElectivePayStatus;
	}

	public PeBzzOrderInfo getPeBzzOrderInfo() {
		return peBzzOrderInfo;
	}

	public void setPeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo) {
		this.peBzzOrderInfo = peBzzOrderInfo;
	}

	public PeElectiveCoursePeriod getPeElectiveCoursePeriod() {
		return peElectiveCoursePeriod;
	}

	public void setPeElectiveCoursePeriod(
			PeElectiveCoursePeriod peElectiveCoursePeriod) {
		this.peElectiveCoursePeriod = peElectiveCoursePeriod;
	}

	public Integer getExamTimes() {
		return examTimes;
	}

	public void setExamTimes(Integer examTimes) {
		this.examTimes = examTimes;
	}

}
