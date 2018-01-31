package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class AssignRecord extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {
	
	private String id;
	private String assignStyle;  //分配方式 0统一分配 1 统一剥离 2定制分配
	private String operateAmount;  //操作金额
	private String accountAmount;  //账户余额
	private SsoUser ssoUser;  //分配人
	private Date assignDate;  //分配日期
	private Set recordAssign = new HashSet(0);
	private EnumConst enumConstByFlagRecordAssignMethod;   //标识是用来记录学时分配的还是学时剥离的
	private EnumConst enumConstByFlagOperateType;   //标识是用来记录金额操作类型的
	private PeBzzOrderInfo peBzzOrderInfo;  //订单号
	
	
	public AssignRecord(String id, String assignStyle, String operateAmount,
			String accountAmount, SsoUser ssoUser, Date assignDate,
			Set recordAssign, EnumConst enumConstByFlagRecordAssignMethod,
			EnumConst enumConstByFlagOperateType, PeBzzOrderInfo peBzzOrderInfo) {
		super();
		this.id = id;
		this.assignStyle = assignStyle;
		this.operateAmount = operateAmount;
		this.accountAmount = accountAmount;
		this.ssoUser = ssoUser;
		this.assignDate = assignDate;
		this.recordAssign = recordAssign;
		this.enumConstByFlagRecordAssignMethod = enumConstByFlagRecordAssignMethod;
		this.enumConstByFlagOperateType = enumConstByFlagOperateType;
		this.peBzzOrderInfo = peBzzOrderInfo;
	}
	public EnumConst getEnumConstByFlagRecordAssignMethod() {
		return enumConstByFlagRecordAssignMethod;
	}
	public void setEnumConstByFlagRecordAssignMethod(
			EnumConst enumConstByFlagRecordAssignMethod) {
		this.enumConstByFlagRecordAssignMethod = enumConstByFlagRecordAssignMethod;
	}
	public AssignRecord() {
		// TODO Auto-generated constructor stub
	}

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAssignStyle() {
		return assignStyle;
	}
	public void setAssignStyle(String assignStyle) {
		this.assignStyle = assignStyle;
	}
	public SsoUser getSsoUser() {
		return ssoUser;
	}
	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}
	public Date getAssignDate() {
		return assignDate;
	}
	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}
	public Set getRecordAssign() {
		return recordAssign;
	}
	public void setRecordAssign(Set recordAssign) {
		this.recordAssign = recordAssign;
	}
	public String getOperateAmount() {
		return operateAmount;
	}
	public void setOperateAmount(String operateAmount) {
		this.operateAmount = operateAmount;
	}
	public String getAccountAmount() {
		return accountAmount;
	}
	public void setAccountAmount(String accountAmount) {
		this.accountAmount = accountAmount;
	}
	public EnumConst getEnumConstByFlagOperateType() {
		return enumConstByFlagOperateType;
	}
	public void setEnumConstByFlagOperateType(EnumConst enumConstByFlagOperateType) {
		this.enumConstByFlagOperateType = enumConstByFlagOperateType;
	}
	public PeBzzOrderInfo getPeBzzOrderInfo() {
		return peBzzOrderInfo;
	}
	public void setPeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo) {
		this.peBzzOrderInfo = peBzzOrderInfo;
	}
	
	
}
