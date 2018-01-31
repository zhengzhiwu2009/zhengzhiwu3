package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeBzzRefundInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBzzRefundInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeBzzOrderInfo peBzzOrderInfo;
	private EnumConst enumConstByFlagRefundState;
	private String name;
	private Date applyDate;
	private String reason;
	private String refauseReason;
	private String operationLogs;

	// Constructors

	public String getOperationLogs() {
		return operationLogs;
	}

	public void setOperationLogs(String operationLogs) {
		this.operationLogs = operationLogs;
	}

	public String getRefauseReason() {
		return refauseReason;
	}

	public void setRefauseReason(String refauseReason) {
		this.refauseReason = refauseReason;
	}

	/** default constructor */
	public PeBzzRefundInfo() {
	}

	/** full constructor */
	public PeBzzRefundInfo(PeBzzOrderInfo peBzzOrderInfo, EnumConst enumConstByFlagRefundState,
			String name, Date applyDate, String reason, String refauseReason, String operationLogs) {
		this.peBzzOrderInfo = peBzzOrderInfo;
		this.enumConstByFlagRefundState = enumConstByFlagRefundState;
		this.name = name;
		this.applyDate = applyDate;
		this.reason = reason;
		this.refauseReason = refauseReason;
		this.operationLogs = operationLogs;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeBzzOrderInfo getPeBzzOrderInfo() {
		return this.peBzzOrderInfo;
	}

	public void setPeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo) {
		this.peBzzOrderInfo = peBzzOrderInfo;
	}

	public EnumConst getEnumConstByFlagRefundState() {
		return this.enumConstByFlagRefundState;
	}

	public void setEnumConstByFlagRefundState(EnumConst enumConstByFlagRefundState) {
		this.enumConstByFlagRefundState = enumConstByFlagRefundState;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}