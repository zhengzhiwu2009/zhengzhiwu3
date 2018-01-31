package com.whaty.platform.entity.bean;

public class OnlineRefundRequestInfo {
	private String recordLength;
	private String status;
	private String mac;
	private String merchantId;
	private String merOrderId;
	private String beginDate;
	private String endDate;
	private String refundId;
	
	
	public String getRecordLength() {
		return recordLength;
	}
	public void setRecordLength(String recordLength) {
		this.recordLength = recordLength;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerOrderId() {
		return merOrderId;
	}
	public void setMerOrderId(String merOrderId) {
		this.merOrderId = merOrderId;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	
}
