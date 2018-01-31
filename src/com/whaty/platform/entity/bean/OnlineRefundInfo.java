package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 网银订单类，用于接收三方返回参数
 * 
 * @author linjie
 */

public class OnlineRefundInfo implements java.io.Serializable {

	// Fields
	private String refundId;//退款流水号
	private String merchantId;//商户ID
	private String payOrderId;//易宝订单
	private String merOrderId;//商户订单
	private String amount;//金额
	private String refundAmount;//退款金额
	private String state;//退款状态 0新申请 1处理中 2拒绝 3成功 4平台拒绝 5撤销申请
	private String startFlag;//开始状态 0：商户 1：平台
	private String applyDate;//格式: yyyy-MM-dd
	
	
	public String getPayOrderId() {
		return payOrderId;
	}
	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}
	public String getMerOrderId() {
		return merOrderId;
	}
	public void setMerOrderId(String merOrderId) {
		this.merOrderId = merOrderId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getStartFlag() {
		return startFlag;
	}
	public void setStartFlag(String startFlag) {
		this.startFlag = startFlag;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	
}