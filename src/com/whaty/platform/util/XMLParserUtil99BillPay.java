package com.whaty.platform.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import com.whaty.platform.entity.bean.AutoRefund;
import com.whaty.platform.entity.bean.OnlineOrderInfo;
import com.whaty.platform.entity.bean.OnlineRefundInfo;
import com.whaty.platform.entity.bean.OnlineRefundRequestInfo;
import com.whaty.platform.entity.bean.OnlineRequestInfo;

/**
 * 将快钱返回xml字符串转换成onlineorder和requestinfo实体，方便操作
 * 
 */
public class XMLParserUtil99BillPay {

	/**
	 * 获取指定元素路径的值
	 * 
	 * @param value
	 * @param path
	 * @return
	 */
	public static String getContent(String value, String path) {
		Document doc = null;
		String content = "";
		try {
			doc = DocumentHelper.parseText(value);
		} catch (DocumentException e) {
			return "";
		}
		Node contentNode = doc.selectSingleNode(path);
		if (contentNode != null) {
			content = contentNode.getStringValue();
		}
		return content;
	}

	/**
	 * 转换xml到OnlineOrderInfo
	 * 
	 * @param value
	 * @return List<OnlineOrderInfo>
	 */
	public static List<OnlineOrderInfo> getOrderList(String value) {
		Document doc = null;
		List<OnlineOrderInfo> orderList = new ArrayList<OnlineOrderInfo>();
		try {
			doc = DocumentHelper.parseText(value);
		} catch (DocumentException e) {
			return null;
		}
		List orderNodeList = doc.selectNodes("//drawbackapi");
		if (orderNodeList != null) {
			for (Iterator nodeIter = orderNodeList.iterator(); nodeIter.hasNext();) {
				Element n = (Element) nodeIter.next();
				OnlineOrderInfo onlineOrderInfo = new OnlineOrderInfo();
				onlineOrderInfo.setPayOrderId(fixNodeNull(n.selectSingleNode("payOrderId")));
				onlineOrderInfo.setMerOrderId(fixNodeNull(n.selectSingleNode("merOrderId")));
				onlineOrderInfo.setMerSendTime(fixNodeNull(n.selectSingleNode("merSendTime")));
				// 退款金额
				onlineOrderInfo.setAmountSum(fixNodeNull(n.selectSingleNode("amount")));

				onlineOrderInfo.setPayBank(fixNodeNull(n.selectSingleNode("payBank")));
				onlineOrderInfo.setState(fixNodeNull(n.selectSingleNode("state")));
				onlineOrderInfo.setType(fixNodeNull(n.selectSingleNode("type")));
				orderList.add(onlineOrderInfo);
			}
		}
		return orderList;
	}

	/**
	 * 转换xml到OnlineRefundInfo
	 * 
	 * @param value
	 * @return List<OnlineRefundInfo>
	 */
	public static List<OnlineRefundInfo> getRefundList(String value) {
		Document doc = null;
		List<OnlineRefundInfo> refundList = new ArrayList<OnlineRefundInfo>();
		try {
			doc = DocumentHelper.parseText(value);
		} catch (DocumentException e) {
			return null;
		}
		List orderNodeList = doc.selectNodes("//refundResults/response/refund");
		if (orderNodeList != null) {
			for (Iterator nodeIter = orderNodeList.iterator(); nodeIter.hasNext();) {
				Element n = (Element) nodeIter.next();
				OnlineRefundInfo refund = new OnlineRefundInfo();
				refund.setRefundId(fixNodeNull(n.selectSingleNode("refundId")));
				refund.setMerchantId(fixNodeNull(n.selectSingleNode("merchantId")));
				refund.setPayOrderId(fixNodeNull(n.selectSingleNode("payOrderId")));
				refund.setMerOrderId(fixNodeNull(n.selectSingleNode("merOrderId")));
				refund.setAmount(fixNodeNull(n.selectSingleNode("amount")));
				refund.setRefundAmount(fixNodeNull(n.selectSingleNode("refundAmount")));
				refund.setState(fixNodeNull(n.selectSingleNode("state")));
				refund.setStartFlag(fixNodeNull(n.selectSingleNode("startFlag")));
				refund.setApplyDate(fixNodeNull(n.selectSingleNode("applyDate")));
				refundList.add(refund);
			}
		}
		return refundList;
	}

	/**
	 * 转换xml到OnlineRequestInfo
	 * 
	 * @param value
	 * @return
	 */
	public static OnlineRequestInfo getRequestInfo(String value) {
		Document doc = null;
		OnlineRequestInfo onlineRequestInfo = new OnlineRequestInfo();
		try {
			doc = DocumentHelper.parseText(value);
		} catch (DocumentException e) {
			System.out.println("Error pay value:" + value);
			e.printStackTrace();
			return null;
		}
		onlineRequestInfo.setRecordLength(fixNodeNull(doc.selectSingleNode("//accountCheckResult/recordLength")));
		onlineRequestInfo.setStatus(fixNodeNull(doc.selectSingleNode("//accountCheckResult/status")));
		onlineRequestInfo.setMac(fixNodeNull(doc.selectSingleNode("//accountCheckResult/mac")));
		onlineRequestInfo.setMerchantId(fixNodeNull(doc.selectSingleNode("//accountCheckResult/requestInfo/merchantId")));
		onlineRequestInfo.setMerOrderId(fixNodeNull(doc.selectSingleNode("//accountCheckResult/requestInfo/merOrderId")));
		onlineRequestInfo.setStartTime(fixNodeNull(doc.selectSingleNode("//accountCheckResult/requestInfo/startTime")));
		onlineRequestInfo.setEndTime(fixNodeNull(doc.selectSingleNode("//accountCheckResult/requestInfo/endTime")));
		onlineRequestInfo.setPayBank(fixNodeNull(doc.selectSingleNode("//accountCheckResult/requestInfo/payBank")));
		onlineRequestInfo.setVersion(fixNodeNull(doc.selectSingleNode("//accountCheckResult/requestInfo/version")));
		onlineRequestInfo.setType(fixNodeNull(doc.selectSingleNode("//accountCheckResult/requestInfo/type")));
		return onlineRequestInfo;
	}

	/**
	 * 转换xml到OnlineRefundRequestInfo
	 * 
	 * @param value
	 * @return
	 */
	public static OnlineRefundRequestInfo getRefundRequestInfo(String value) {
		Document doc = null;
		OnlineRefundRequestInfo refundRequestInfo = new OnlineRefundRequestInfo();
		try {
			doc = DocumentHelper.parseText(value);
		} catch (DocumentException e) {
			return null;
		}
		refundRequestInfo.setRecordLength(fixNodeNull(doc.selectSingleNode("//refundResults/recordLength")));
		refundRequestInfo.setStatus(fixNodeNull(doc.selectSingleNode("//refundResults/status")));
		refundRequestInfo.setMac(fixNodeNull(doc.selectSingleNode("//refundResults/mac")));
		refundRequestInfo.setMerchantId(fixNodeNull(doc.selectSingleNode("//refundResults/request/merchantId")));
		refundRequestInfo.setMerOrderId(fixNodeNull(doc.selectSingleNode("//refundResults/request/merOrderId")));
		refundRequestInfo.setBeginDate(fixNodeNull(doc.selectSingleNode("//refundResults/request/beginDate")));
		refundRequestInfo.setEndDate(fixNodeNull(doc.selectSingleNode("//refundResults/request/endDate")));
		refundRequestInfo.setRefundId(fixNodeNull(doc.selectSingleNode("//refundResults/request/refundId")));
		return refundRequestInfo;
	}

	/**
	 * 节点非空判断
	 * 
	 * @param n
	 * @return
	 */
	public static String fixNodeNull(Node n) {
		if (n != null) {
			return n.getStringValue();
		}
		return null;
	}

	public static AutoRefund getAutoRefund(String value) {
		Document doc = null;
		AutoRefund autoRefund = new AutoRefund();
		try {
			doc = DocumentHelper.parseText(value);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		// 状态
		autoRefund.setStatus(fixNodeNull(doc.selectSingleNode("//DRAWBACKAPI/RESULT")));
		// mac
		autoRefund.setMac(null);
		// 商户编号(必填): 签约后由支付平台给商户分配,最大长度20
		autoRefund.setMerchantId(fixNodeNull(doc.selectSingleNode("//DRAWBACKAPI/MERCHANT")));
		// 订单编号(必填): 商户的交易定单号,由商户网站生成,最大长度30
		autoRefund.setMerOrderId(fixNodeNull(doc.selectSingleNode("//DRAWBACKAPI/ORDERID")));
		// 退款金额
		autoRefund.setRefundAmount(fixNodeNull(doc.selectSingleNode("//DRAWBACKAPI/AMOUNT")));
		// 退款流水号，用于查询退款状态
		autoRefund.setRefundId(fixNodeNull(doc.selectSingleNode("//DRAWBACKAPI/TXORDER")));
		// 错误代码
		autoRefund.setErrorCode(fixNodeNull(doc.selectSingleNode("//DRAWBACKAPI/CODE")));
		return autoRefund;
	}

	/**
	 * 测试main方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String xStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<accountCheckResult>" + "<recordLength>1</recordLength>"
				+ "<status>success</status>" + "<mac>52e3b023c6b943c8d119b30f48eacc95</mac>" + "<requestInfo>"
				+ "<merchantId>1001</merchantId>" + "<merOrderId>1000010000142342432</merOrderId>" + "<version>1.00</version>"
				+ "</requestInfo>" + "<payOrder>" + "<payOrderId>200906161457222720141</payOrderId>"
				+ "<merOrderId>1000010000142342432</merOrderId>" + "<merSendTime>2009-06-16</merSendTime>" + "<amountSum>540</amountSum>"
				+ "<payBank>CCB</payBank>" + "<state>1</state>" + "<type>0</type>" + "</payOrder>" + "</accountCheckResult>";
		xStr = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><drawbackapi><merchant>merchant</merchant><orderid>orderid</orderid><txorder>txorder</txorder><amount>amount</amount><result>result</result><code>code</code></drawbackapi>";
		XMLParserUtil99BillPay x = new XMLParserUtil99BillPay();
		// List<OnlineOrderInfo> orderList = x.getOrderList(xStr);
		// OnlineRequestInfo rf = x.getRequestInfo(xStr);
		// System.out.println(orderList.size());
		AutoRefund autoRefund = x.getAutoRefund(xStr);
		System.exit(0);
	}
}
