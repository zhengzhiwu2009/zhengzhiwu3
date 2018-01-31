package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PrepaidAmountRecord extends com.whaty.platform.entity.bean.AbstractBean
	implements java.io.Serializable {
	
	private String id;
	private String operateTime;  //操作时间
	private String operateType;  //操作类型
	private String operateAmount;  //操作金额
	private String accountAmount;  //账户余额
	private String operateUser;
	private String fkAssignRecordId;
	private String fkOrderId;
	private EnumConst enumConstByFlagOperateType;   //标识是用来记录学时分配的还是学时剥离的
	
	public PrepaidAmountRecord() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getOperateAmount() {
		return operateAmount;
	}

	public void setOperateAmount(String operateAmount) {
		this.operateAmount = operateAmount;
	}


	public String getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}

	public EnumConst getEnumConstByFlagOperateType() {
		return enumConstByFlagOperateType;
	}

	public void setEnumConstByFlagOperateType(EnumConst enumConstByFlagOperateType) {
		this.enumConstByFlagOperateType = enumConstByFlagOperateType;
	}


	public String getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(String accountAmount) {
		this.accountAmount = accountAmount;
	}

	public String getFkOrderId() {
		return fkOrderId;
	}

	public void setFkOrderId(String fkOrderId) {
		this.fkOrderId = fkOrderId;
	}

	public String getFkAssignRecordId() {
		return fkAssignRecordId;
	}

	public void setFkAssignRecordId(String fkAssignRecordId) {
		this.fkAssignRecordId = fkAssignRecordId;
	}

	
	
	
}
