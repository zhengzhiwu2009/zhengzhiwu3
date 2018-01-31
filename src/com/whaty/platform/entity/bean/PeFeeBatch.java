package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeFeeBatch entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeFeeBatch extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private EnumConst enumConstByFlagBak;
	private PeSitemanager peSitemanager;
	private EnumConst enumConstByFlagFeeCheck;
	private PeSite peSite;
	private String name;
	private Double feeAmountTotal;
	private Long feeRecordNum;
	private Date inputDate;
	private String note;
	private Set prFeeDetails = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeFeeBatch() {
	}

	/** minimal constructor */
	public PeFeeBatch(String name) {
		this.name = name;
	}

	/** full constructor */
	public PeFeeBatch(EnumConst enumConstByFlagBak,
			PeSitemanager peSitemanager, EnumConst enumConstByFlagFeeCheck,
			PeSite peSite, String name, Double feeAmountTotal,
			Long feeRecordNum, Date inputDate, String note, Set prFeeDetails) {
		this.enumConstByFlagBak = enumConstByFlagBak;
		this.peSitemanager = peSitemanager;
		this.enumConstByFlagFeeCheck = enumConstByFlagFeeCheck;
		this.peSite = peSite;
		this.name = name;
		this.feeAmountTotal = feeAmountTotal;
		this.feeRecordNum = feeRecordNum;
		this.inputDate = inputDate;
		this.note = note;
		this.prFeeDetails = prFeeDetails;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConstByFlagBak() {
		return this.enumConstByFlagBak;
	}

	public void setEnumConstByFlagBak(EnumConst enumConstByFlagBak) {
		this.enumConstByFlagBak = enumConstByFlagBak;
	}

	public PeSitemanager getPeSitemanager() {
		return this.peSitemanager;
	}

	public void setPeSitemanager(PeSitemanager peSitemanager) {
		this.peSitemanager = peSitemanager;
	}

	public EnumConst getEnumConstByFlagFeeCheck() {
		return this.enumConstByFlagFeeCheck;
	}

	public void setEnumConstByFlagFeeCheck(EnumConst enumConstByFlagFeeCheck) {
		this.enumConstByFlagFeeCheck = enumConstByFlagFeeCheck;
	}

	public PeSite getPeSite() {
		return this.peSite;
	}

	public void setPeSite(PeSite peSite) {
		this.peSite = peSite;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getFeeAmountTotal() {
		return this.feeAmountTotal;
	}

	public void setFeeAmountTotal(Double feeAmountTotal) {
		this.feeAmountTotal = feeAmountTotal;
	}

	public Long getFeeRecordNum() {
		return this.feeRecordNum;
	}

	public void setFeeRecordNum(Long feeRecordNum) {
		this.feeRecordNum = feeRecordNum;
	}

	public Date getInputDate() {
		return this.inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPrFeeDetails() {
		return this.prFeeDetails;
	}

	public void setPrFeeDetails(Set prFeeDetails) {
		this.prFeeDetails = prFeeDetails;
	}

}