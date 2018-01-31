package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeBzzBatch entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBzzPici extends AbstractBean implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private Date startDatetime;
	private Date endDatetime;
	private EnumConst enumConstByFlagPiciStatus;
	private String examTimes;
	private String code;
    private Double passScore;
    private Date publishDatetime;
    private String remark;
    private EnumConst enumConstByFlagExamTypes;
	
	
	public EnumConst getEnumConstByFlagExamTypes() {
		return enumConstByFlagExamTypes;
	}
	public void setEnumConstByFlagExamTypes(EnumConst enumConstByFlagExamTypes) {
		this.enumConstByFlagExamTypes = enumConstByFlagExamTypes;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	/** default constructor */
	public PeBzzPici() {
	}

	/** full constructor */

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public EnumConst getEnumConstByFlagPiciStatus() {
		return enumConstByFlagPiciStatus;
	}

	public void setEnumConstByFlagPiciStatus(EnumConst enumConstByFlagPiciStatus) {
		this.enumConstByFlagPiciStatus = enumConstByFlagPiciStatus;
	}

	public String getExamTimes() {
		return examTimes;
	}

	public void setExamTimes(String examTimes) {
		this.examTimes = examTimes;
	}
	public Double getPassScore() {
		return passScore;
	}
	public void setPassScore(Double passScore) {
		this.passScore = passScore;
	}
	public PeBzzPici(String id, String name, Date startDatetime,
			Date endDatetime, EnumConst enumConstByFlagPiciStatus,
			String examTimes, String code, Double passScore,
			Date publishDatetime, String remark) {
		super();
		this.id = id;
		this.name = name;
		this.startDatetime = startDatetime;
		this.endDatetime = endDatetime;
		this.enumConstByFlagPiciStatus = enumConstByFlagPiciStatus;
		this.examTimes = examTimes;
		this.code = code;
		this.passScore = passScore;
		this.publishDatetime = publishDatetime;
		this.remark = remark;
	}
	public Date getStartDatetime() {
		return startDatetime;
	}
	public void setStartDatetime(Date startDatetime) {
		this.startDatetime = startDatetime;
	}
	public Date getEndDatetime() {
		return endDatetime;
	}
	public void setEndDatetime(Date endDatetime) {
		this.endDatetime = endDatetime;
	}
	public Date getPublishDatetime() {
		return publishDatetime;
	}
	public void setPublishDatetime(Date publishDatetime) {
		this.publishDatetime = publishDatetime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}