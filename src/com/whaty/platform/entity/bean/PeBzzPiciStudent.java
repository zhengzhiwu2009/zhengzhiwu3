package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeBzzBatch entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBzzPiciStudent extends AbstractBean implements java.io.Serializable {

	// Fields

	private String id;
	private PeBzzPici peBzzPici;
	private PeBzzStudent peBzzStudent;
	private Date startDate;
	private Date completeDate;
	private EnumConst enumConstByFlagIsPass;
	private EnumConst enumConstByFlagStruts;
	private String examTimes;
	private String score;
	private String subScore; //主观题分数

	public String getSubScore() {
		return subScore;
	}

	public void setSubScore(String subScore) {
		this.subScore = subScore;
	}

	/** default constructor */
	public PeBzzPiciStudent() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public PeBzzPici getPeBzzPici() {
		return peBzzPici;
	}

	public void setPeBzzPici(PeBzzPici peBzzPici) {
		this.peBzzPici = peBzzPici;
	}

	public PeBzzStudent getPeBzzStudent() {
		return peBzzStudent;
	}

	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public EnumConst getEnumConstByFlagIsPass() {
		return enumConstByFlagIsPass;
	}

	public void setEnumConstByFlagIsPass(EnumConst enumConstByFlagIsPass) {
		this.enumConstByFlagIsPass = enumConstByFlagIsPass;
	}

	public String getExamTimes() {
		return examTimes;
	}

	public void setExamTimes(String examTimes) {
		this.examTimes = examTimes;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public EnumConst getEnumConstByFlagStruts() {
		return enumConstByFlagStruts;
	}

	public void setEnumConstByFlagStruts(EnumConst enumConstByFlagStruts) {
		this.enumConstByFlagStruts = enumConstByFlagStruts;
	}

}