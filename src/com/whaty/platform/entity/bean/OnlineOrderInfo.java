package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 网银订单类，用于接收三方返回参数
 * 
 * @author linjie
 */

public class OnlineOrderInfo implements java.io.Serializable {

	// Fields
	private String payOrderId;
	private String merOrderId;
	private String merSendTime;
	private String amountSum;
	private String payBank;
	private String state;
	private String type;
	
	
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
	public String getMerSendTime() {
		return merSendTime;
	}
	public void setMerSendTime(String merSendTime) {
		this.merSendTime = merSendTime;
	}
	public String getAmountSum() {
		return amountSum;
	}
	public void setAmountSum(String amountSum) {
		this.amountSum = amountSum;
	}
	public String getPayBank() {
		return payBank;
	}
	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}