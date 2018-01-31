package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeBzzBatch entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBzzBatch extends AbstractBean implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private Date startDate;
	private Date endDate;
	private String bz;
	private String standards;
	private String suggTime;
	private String method;
	private String batchNote;
	private Date announceDate;
	private Date cancelDate;
	private Date courseDate;
	private Date evalStartDate;
	private Date evalEndDate;
	private Date askEndDate;
	private Date askStartDate;
	private int batchCode;
	private Set prBzzTchOpencourses = new HashSet(0);
	private Set peBzzStudents = new HashSet(0);
	private Set studentBatch = new HashSet(0);
	
	private EnumConst enumConstByFlagBatchType;
	
	private String recruitSelected;
	
	private String alertSelected;
	
	private PeEnterprise peSite;	//管理员所在的企业
	
	private String totalTime;
	
	private String operationLogs;
	
	private EnumConst enumConstByFlagDeploy;


	// Constructors

	public String getAlertSelected() {
		return alertSelected;
	}

	public void setAlertSelected(String alertSelected) {
		this.alertSelected = alertSelected;
	}

	/** default constructor */
	public PeBzzBatch() {
	}

	/** full constructor */
	public PeBzzBatch(String name, Date startDate, Date endDate, String bz,
			Date courseDate,String standards ,Date evalStartDate, Date evalEndDate,
			Date askEndDate, Date askStartDate,int batchCode, Set prBzzTchOpencourses,
			Set peBzzStudents,Set studentBatch,EnumConst enumConstByFlagBatchType) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.bz = bz;
		this.courseDate = courseDate;
		this.standards = standards;
		this.evalStartDate = evalStartDate;
		this.evalEndDate = evalEndDate;
		this.askEndDate = askEndDate;
		this.askStartDate = askStartDate;
		this.prBzzTchOpencourses = prBzzTchOpencourses;
		this.peBzzStudents = peBzzStudents;
		this.batchCode = batchCode;
		this.studentBatch = studentBatch;
		this.enumConstByFlagBatchType = enumConstByFlagBatchType;
	}
	
	/** full constructor */
	/*public PeBzzBatch(String name, Date startDate, Date endDate, String bz,
			Date courseDate,String standards ,Date evalStartDate, Date evalEndDate,
			Date askEndDate, Date askStartDate,int batchCode, Set prBzzTchOpencourses,
			Set peBzzStudents,Set peBzzFaxInfos,Set studentBatch,EnumConst enumConstByFlagBatchType) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.bz = bz;
		this.courseDate = courseDate;
		this.standards = standards;
		this.evalStartDate = evalStartDate;
		this.evalEndDate = evalEndDate;
		this.askEndDate = askEndDate;
		this.askStartDate = askStartDate;
		this.prBzzTchOpencourses = prBzzTchOpencourses;
		this.peBzzStudents = peBzzStudents;
		this.batchCode = batchCode;
		this.peBzzFaxInfos=peBzzFaxInfos;
		this.studentBatch = studentBatch;
		this.enumConstByFlagBatchType = enumConstByFlagBatchType;
	}*/

	// Property accessors

	public PeBzzBatch( String name, Date startDate, Date endDate,
			String bz, String standards, String suggTime, String batchNote,
			Date announceDate, Date cancelDate, Date courseDate,
			Date evalStartDate, Date evalEndDate, Date askEndDate,
			Date askStartDate, int batchCode, Set prBzzTchOpencourses,
			Set peBzzStudents, Set studentBatch,
			EnumConst enumConstByFlagBatchType, String recruitSelected,
			String alertSelected, PeEnterprise peSite, String totalTime,
			String operationLogs, EnumConst enumConstByFlagDeploy) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.bz = bz;
		this.standards = standards;
		this.suggTime = suggTime;
		this.batchNote = batchNote;
		this.announceDate = announceDate;
		this.cancelDate = cancelDate;
		this.courseDate = courseDate;
		this.evalStartDate = evalStartDate;
		this.evalEndDate = evalEndDate;
		this.askEndDate = askEndDate;
		this.askStartDate = askStartDate;
		this.batchCode = batchCode;
		this.prBzzTchOpencourses = prBzzTchOpencourses;
		this.peBzzStudents = peBzzStudents;
		this.studentBatch = studentBatch;
		this.enumConstByFlagBatchType = enumConstByFlagBatchType;
		this.recruitSelected = recruitSelected;
		this.alertSelected = alertSelected;
		this.peSite = peSite;
		this.totalTime = totalTime;
		this.operationLogs = operationLogs;
		this.enumConstByFlagDeploy = enumConstByFlagDeploy;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Date getCourseDate() {
		return this.courseDate;
	}

	public void setCourseDate(Date courseDate) {
		this.courseDate = courseDate;
	}

	public Date getEvalStartDate() {
		return this.evalStartDate;
	}

	public void setEvalStartDate(Date evalStartDate) {
		this.evalStartDate = evalStartDate;
	}

	public Date getEvalEndDate() {
		return this.evalEndDate;
	}

	public void setEvalEndDate(Date evalEndDate) {
		this.evalEndDate = evalEndDate;
	}

	public Date getAskEndDate() {
		return this.askEndDate;
	}

	public void setAskEndDate(Date askEndDate) {
		this.askEndDate = askEndDate;
	}

	public Date getAskStartDate() {
		return this.askStartDate;
	}

	public void setAskStartDate(Date askStartDate) {
		this.askStartDate = askStartDate;
	}

	public Set getPrBzzTchOpencourses() {
		return this.prBzzTchOpencourses;
	}

	public void setPrBzzTchOpencourses(Set prBzzTchOpencourses) {
		this.prBzzTchOpencourses = prBzzTchOpencourses;
	}

	public Set getPeBzzStudents() {
		return this.peBzzStudents;
	}

	public void setPeBzzStudents(Set peBzzStudents) {
		this.peBzzStudents = peBzzStudents;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getStandards() {
		return standards;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setStandards(String standards) {
		this.standards = standards;
	}

	public int getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(int batchCode) {
		this.batchCode = batchCode;
	}

	public String getRecruitSelected() {
		return recruitSelected;
	}

	public void setRecruitSelected(String recruitSelected) {
		this.recruitSelected = recruitSelected;
	}

	public PeEnterprise getPeSite() {
		return peSite;
	}

	public void setPeSite(PeEnterprise peSite) {
		this.peSite = peSite;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public Set getStudentBatch() {
		return studentBatch;
	}

	public void setStudentBatch(Set studentBatch) {
		this.studentBatch = studentBatch;
	}

	public EnumConst getEnumConstByFlagBatchType() {
		return enumConstByFlagBatchType;
	}

	public void setEnumConstByFlagBatchType(EnumConst enumConstByFlagBatchType) {
		this.enumConstByFlagBatchType = enumConstByFlagBatchType;
	}

	public String getSuggTime() {
		return suggTime;
	}

	public void setSuggTime(String suggTime) {
		this.suggTime = suggTime;
	}

	public String getBatchNote() {
		return batchNote;
	}

	public void setBatchNote(String batchNote) {
		this.batchNote = batchNote;
	}


	public Date getAnnounceDate() {
		return announceDate;
	}

	public void setAnnounceDate(Date announceDate) {
		this.announceDate = announceDate;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getOperationLogs() {
		return operationLogs;
	}

	public void setOperationLogs(String operationLogs) {
		this.operationLogs = operationLogs;
	}


	public EnumConst getEnumConstByFlagDeploy() {
		return enumConstByFlagDeploy;
	}

	public void setEnumConstByFlagDeploy(EnumConst enumConstByFlagDeploy) {
		this.enumConstByFlagDeploy = enumConstByFlagDeploy;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}


}