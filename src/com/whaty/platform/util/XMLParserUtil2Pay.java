/**
 * 
 */
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
import com.whaty.platform.resource.basic.Resource;
import com.whaty.platform.resource.basic.ResourceContent;
import com.whaty.platform.resource.basic.ResourceField;
import com.whaty.platform.resource.basic.ResourceType;
import com.whaty.platform.test.question.TestQuestionType;
import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.string.StrManage;
import com.whaty.util.string.StrManageFactory;
import com.whaty.util.string.WhatyStrManage;

/**
 * 将umpay返回xml字符串转换成onlineorder和requestinfo实体，方便操作
 * @author linjie
 * 
 */
public class XMLParserUtil2Pay {
	
	/**
	 * 获取指定元素路径的值
	 * @param value
	 * @param path
	 * @return
	 */
	public static String getContent(String value,String path){
		Document doc = null;
		String content = "";
		try {
			doc = DocumentHelper.parseText(value);
		} catch (DocumentException e) {
			return "";
		}
		Node contentNode = doc.selectSingleNode(path);
		if(contentNode!= null){
			content = contentNode.getStringValue();
		}
		return content;
	}
	
	/**
	 * 转换xml到OnlineOrderInfo
	 * @author linjie
	 * @param value
	 * @return List<OnlineOrderInfo>
	 */
	public static List<OnlineOrderInfo> getOrderList(String value){
		Document doc = null;
		List<OnlineOrderInfo> orderList = new ArrayList<OnlineOrderInfo>();
		try {
			doc = DocumentHelper.parseText(value);
		} catch (DocumentException e) {
			return null;
		}
		List orderNodeList = doc.selectNodes("//accountCheckResult/payOrder");
		if(orderNodeList != null){
			for(Iterator nodeIter = orderNodeList.iterator(); nodeIter.hasNext();){
				Element n = (Element) nodeIter.next();
				OnlineOrderInfo onlineOrderInfo = new OnlineOrderInfo();
				onlineOrderInfo.setPayOrderId(fixNodeNull(n.selectSingleNode("payOrderId")));
				onlineOrderInfo.setMerOrderId(fixNodeNull(n.selectSingleNode("merOrderId")));
				onlineOrderInfo.setMerSendTime(fixNodeNull(n.selectSingleNode("merSendTime")));
				onlineOrderInfo.setAmountSum(fixNodeNull(n.selectSingleNode("amountSum")));
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
	 * @author linjie
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
		if(orderNodeList != null){
			for(Iterator nodeIter = orderNodeList.iterator(); nodeIter.hasNext();){
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
	 * @author linjie
	 * @param value
	 * @return
	 */
	public static OnlineRequestInfo getRequestInfo(String value){
		Document doc = null;
		OnlineRequestInfo onlineRequestInfo = new OnlineRequestInfo();
		try {
			doc = DocumentHelper.parseText(value);
		} catch (DocumentException e) {
			System.out.println("Error pay value:"+value);
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
	 * @author linjie
	 * @param value
	 * @return
	 */
	public static OnlineRefundRequestInfo getRefundRequestInfo(String value){
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
	
	public static AutoRefund getAutoRefund(String value) {
		Document doc = null;
		AutoRefund autoRefund = new AutoRefund();
		try {
			doc = DocumentHelper.parseText(value);
		} catch (DocumentException e) {
			return null;
		}
		autoRefund.setStatus(fixNodeNull(doc.selectSingleNode("//autoRefund/status")));
		autoRefund.setMac(fixNodeNull(doc.selectSingleNode("//autoRefund/mac")));
		autoRefund.setMerchantId(fixNodeNull(doc.selectSingleNode("//autoRefund/merchantId")));
		autoRefund.setMerOrderId(fixNodeNull(doc.selectSingleNode("//autoRefund/merOrderId")));
		autoRefund.setRefundAmount(fixNodeNull(doc.selectSingleNode("//autoRefund/refundAmount")));
		autoRefund.setRefundId(fixNodeNull(doc.selectSingleNode("//autoRefund/refundId")));
		return autoRefund;
	}
	
	/**
	 * 节点非空判断
	 * @param n
	 * @return
	 */
	public static String fixNodeNull(Node n){
		if(n!=null){
			return n.getStringValue();
		}
		return null;
	}
	
	
	/**
	 * 测试main方法
	 * @param args
	 */
	public static void main(String[] args) {
		String xStr = 
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
			"<accountCheckResult>" + 
			"<recordLength>1</recordLength>" + 
			"<status>success</status>" + 
			"<mac>52e3b023c6b943c8d119b30f48eacc95</mac>" + 
			"<requestInfo>" + 
			"<merchantId>1001</merchantId>" + 
			"<merOrderId>1000010000142342432</merOrderId>" + 
			"<version>1.00</version>" + 
			"</requestInfo>" + 
			"<payOrder>" + 
			"<payOrderId>200906161457222720141</payOrderId>" + 
			"<merOrderId>1000010000142342432</merOrderId>" + 
			"<merSendTime>2009-06-16</merSendTime>" + 
			"<amountSum>540</amountSum>" + 
			"<payBank>CCB</payBank>" + 
			"<state>1</state>" + 
			"<type>0</type>" + 
			"</payOrder>" + 
			"</accountCheckResult>";
		XMLParserUtil2Pay x = new XMLParserUtil2Pay();
		List<OnlineOrderInfo> orderList = x.getOrderList(xStr);
		OnlineRequestInfo rf = x.getRequestInfo(xStr);
		System.out.println(orderList.size());
	}

}
