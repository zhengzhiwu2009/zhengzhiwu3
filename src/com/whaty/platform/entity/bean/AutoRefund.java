package com.whaty.platform.entity.bean;

/**
 * shen
 * 
 * @author linjie
 */

public class AutoRefund implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private String status;// 商户ID
	private String mac;// 商户ID
	private String merchantId;// 商户ID
	private String merOrderId;// 商户订单
	private String refundAmount;// 退款金额
	private String refundId;// 退款流水号
	/** 快钱属性* */
	private String errorCode;// 错误代码

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

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}