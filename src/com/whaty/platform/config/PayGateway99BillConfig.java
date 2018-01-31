/**
 * 
 */
package com.whaty.platform.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import com.whaty.platform.Exception.PlatformException;

/**
 * 快钱支付
 * 
 * @author Lee 2015-11-26
 */
public class PayGateway99BillConfig {

	/**
	 * 构造函数
	 * 
	 * @param configAbsPath
	 *            配置文件路径
	 */
	public PayGateway99BillConfig(String configAbsPath) {
		this.setPlatformConfigAbsPath(configAbsPath);
	}

	// 快钱支付配置文件名
	protected static String FILENAME = "payGateway99BillConfig.xml";
	// 配置文件路径
	private String platformConfigAbsPath;
	// 人民币账号
	// 本参数用来指定接收款项的快钱用户的人民币账号
	private String merchantAcctId;
	// 客户编号所对应的密钥。。在账户邮箱中获取
	private String key;
	// 字符集 固定值：1 1代表UTF-8
	private String inputCharset;
	// 查询接口版本 固定值：v2.0注意为小写字母
	private String version;
	// 签名类型 固定值：1 1代表MD5加密签名方式
	private String signType;
	// 查询方式 固定选择值：0、1
	// 0按商户订单号单笔查询（返回该订单信息）
	// 1按交易结束时间批量查询（只返回成功订单）
	private String queryType;
	// 查询模式 固定值：1 1代表简单查询（返回基本订单信息）
	private String queryMode;
	// 交易开始时间 数字串，一共14位
	// 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]，例如：20071117020101
	private String startTime;// "20120319150000" ;
	// 交易结束时间 数字串，一共14位
	// 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]，例如：20071117020101
	private String endTime;// "20120320142624";
	// 请求记录集页码 在查询结果数据总量很大时，快钱会将支付结果分多次返回。本参数表示商户需要得到的记录集页码。
	// 默认为1，表示第1页。
	private String requestPage;
	// 组合字符串。。必须按照此顺序组串
	private String signMsgVal;
	// 需要是绝对地址，bgUrl不能同时为空 当bgUrl为空时，快钱直接将支付结果GET到pageUrl
	// 当bgUrl不为空时，按照bgUrl的方式返回
	private String pageUrl;
	// 服务器接收支付结果的后台地址
	private String bgUrl;
	/** 退款相关参数* */
	// 退款接口版本号 目前固定为此值
	private String refundVersion;
	// 操作类型 固定值001 001代表下订单请求退款
	private String commandType;
	// 加密所需的key值，线上的话发到商户快钱账户邮箱里
	private String merchantKey;
	// 退费地址
	private String bankResultValue;

	/**
	 * 读取快钱支付配置文件
	 * 
	 * @throws PlatformException
	 */
	public void getConfig() throws PlatformException {
		File file = new File(this.getPlatformConfigAbsPath() + PayGateway99BillConfig.FILENAME);
		if (!file.exists()) {
			throw new PlatformException("can't find the file" + file.getAbsolutePath());
		}
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(file);
			// 人民币账号
			// 本参数用来指定接收款项的快钱用户的人民币账号
			this.setMerchantAcctId(document.selectSingleNode("//" + "merchantAcctId").getText());

			// 客户编号所对应的密钥。。在账户邮箱中获取
			this.setKey(document.selectSingleNode("//" + "key").getText());

			// 字符集 固定值：1 1代表UTF-8
			this.setInputCharset(document.selectSingleNode("//" + "inputCharset").getText());

			// 查询接口版本 固定值：v2.0注意为小写字母
			this.setVersion(document.selectSingleNode("//" + "version").getText());

			// 签名类型 固定值：1 1代表MD5加密签名方式
			this.setSignType(document.selectSingleNode("//" + "signType").getText());

			// 查询方式 固定选择值：0、1
			// 0按商户订单号单笔查询（返回该订单信息）
			// 1按交易结束时间批量查询（只返回成功订单）
			this.setQueryType(document.selectSingleNode("//" + "queryType").getText());

			// 查询模式 固定值：1 1代表简单查询（返回基本订单信息）
			this.setQueryMode(document.selectSingleNode("//" + "queryMode").getText());

			// 交易开始时间 数字串，一共14位
			// 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]，例如：20071117020101
			this.setStartTime(document.selectSingleNode("//" + "startTime").getText());

			// 交易结束时间 数字串，一共14位
			// 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]，例如：20071117020101
			this.setEndTime(document.selectSingleNode("//" + "endTime").getText());

			// 请求记录集页码 在查询结果数据总量很大时，快钱会将支付结果分多次返回。本参数表示商户需要得到的记录集页码。
			// 默认为1，表示第1页。
			this.setRequestPage(document.selectSingleNode("//" + "requestPage").getText());
			// 服务器接收支付结果的页面地址
			this.setPageUrl(document.selectSingleNode("//" + "pageUrl").getText());
			// 服务器接收支付结果的后台地址
			this.setBgUrl(document.selectSingleNode("//" + "bgUrl").getText());

			/** 退款相关参数* */
			// 退款接口版本号 目前固定为此值
			this.setRefundVersion(document.selectSingleNode("//" + "refundVersion").getText());

			// 操作类型 固定值001 001代表下订单请求退款
			this.setCommandType(document.selectSingleNode("//" + "commandType").getText());

			// 加密所需的key值，线上的话发到商户快钱账户邮箱里
			this.setMerchantKey(document.selectSingleNode("//" + "merchantKey").getText());

			// 退费地址
			this.setBankResultValue(document.selectSingleNode("//" + "bankResultValue").getText());

		} catch (DocumentException e) {
			throw new PlatformException("error in setConfig");
		}
	}

	/**
	 * 设置快钱支付配置文件
	 * 
	 * @throws PlatformException
	 */
	public void setConfig() throws PlatformException {
		String filename = this.getPlatformConfigAbsPath() + PayGatewayConfig.FILENAME;

		File file = new File(filename);
		if (!file.exists()) {
			throw new PlatformException("Can't find the file" + file.getAbsolutePath());
		}
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(file);
			// 人民币账号
			// 本参数用来指定接收款项的快钱用户的人民币账号
			((Element) document.selectSingleNode("//merchantAcctId")).setText(this.getMerchantAcctId());

			// 客户编号所对应的密钥。。在账户邮箱中获取
			((Element) document.selectSingleNode("//key")).setText(this.getKey());

			// 字符集 固定值：1 1代表UTF-8
			((Element) document.selectSingleNode("//inputCharset")).setText(this.getInputCharset());

			// 查询接口版本 固定值：v2.0注意为小写字母
			((Element) document.selectSingleNode("//version")).setText(this.getVersion());

			// 签名类型 固定值：1 1代表MD5加密签名方式
			((Element) document.selectSingleNode("//signType")).setText(this.getSignType());

			// 查询方式 固定选择值：0、1
			// 0按商户订单号单笔查询（返回该订单信息）
			// 1按交易结束时间批量查询（只返回成功订单）
			((Element) document.selectSingleNode("//queryType")).setText(this.getQueryType());

			// 查询模式 固定值：1 1代表简单查询（返回基本订单信息）
			((Element) document.selectSingleNode("//queryMode")).setText(this.getQueryMode());

			// 交易开始时间 数字串，一共14位
			// 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]，例如：20071117020101
			((Element) document.selectSingleNode("//startTime")).setText(this.getStartTime());

			// 交易结束时间 数字串，一共14位
			// 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]，例如：20071117020101
			((Element) document.selectSingleNode("//endTime")).setText(this.getEndTime());

			// 请求记录集页码 在查询结果数据总量很大时，快钱会将支付结果分多次返回。本参数表示商户需要得到的记录集页码。
			// 默认为1，表示第1页。
			((Element) document.selectSingleNode("//requestPage")).setText(this.getRequestPage());

			// 组合字符串。。必须按照此顺序组串
			((Element) document.selectSingleNode("//signMsgVal")).setText(this.getSignMsgVal());

			// 服务器接收支付结果的页面地址
			((Element) document.selectSingleNode("//pageUrl")).setText(this.getPageUrl());

			// 服务器接收支付结果的后台地址
			((Element) document.selectSingleNode("//bgUrl")).setText(this.getBgUrl());

			/** 退款相关参数* */
			// 退款接口版本号 目前固定为此值
			((Element) document.selectSingleNode("//refundVersion")).setText(this.getRefundVersion());

			// 操作类型 固定值001 001代表下订单请求退款
			((Element) document.selectSingleNode("//commandType")).setText(this.getCommandType());

			// 加密所需的key值，线上的话发到商户快钱账户邮箱里
			((Element) document.selectSingleNode("//merchantKey")).setText(this.getMerchantKey());

			// 退费地址
			((Element) document.selectSingleNode("//bankResultValue")).setText(this.getBankResultValue());
		} catch (DocumentException e) {
			throw new PlatformException("Error in setConfig");
		}

		try {
			XMLWriter output = null;
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			output = new XMLWriter(new FileWriter(new File(filename)), format);
			output.write(document);
			output.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 修改快钱支付配置文件
	 * 
	 * @throws PlatformException
	 */
	public void updateConfig() throws PlatformException {
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("root");
		rootElement.addComment("whaty platform configuration");

		// 人民币账号
		// 本参数用来指定接收款项的快钱用户的人民币账号
		Element merchantAcctIdElement = rootElement.addElement("merchantAcctId");
		merchantAcctIdElement.setText(this.getMerchantAcctId());

		// 客户编号所对应的密钥。。在账户邮箱中获取
		Element keyElement = rootElement.addElement("key");
		keyElement.setText(this.getKey());

		// 字符集 固定值：1 1代表UTF-8
		Element inputCharsetElement = rootElement.addElement("inputCharset");
		inputCharsetElement.setText(this.getInputCharset());

		// 查询接口版本 固定值：v2.0注意为小写字母
		Element versionElement = rootElement.addElement("version");
		versionElement.setText(this.getVersion());

		// 签名类型 固定值：1 1代表MD5加密签名方式
		Element signTypeElement = rootElement.addElement("signType");
		signTypeElement.setText(this.getSignType());

		// 查询方式 固定选择值：0、1
		// 0按商户订单号单笔查询（返回该订单信息）
		// 1按交易结束时间批量查询（只返回成功订单）
		Element queryTypeElement = rootElement.addElement("queryType");
		queryTypeElement.setText(this.getQueryType());

		// 查询模式 固定值：1 1代表简单查询（返回基本订单信息）
		Element queryModeElement = rootElement.addElement("queryMode");
		queryModeElement.setText(this.getQueryMode());

		// 交易开始时间 数字串，一共14位
		// 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]，例如：20071117020101
		Element startTimeElement = rootElement.addElement("startTime");
		startTimeElement.setText(this.getStartTime());

		// 交易结束时间 数字串，一共14位
		// 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]，例如：20071117020101
		Element endTimeElement = rootElement.addElement("endTime");
		endTimeElement.setText(this.getEndTime());

		// 请求记录集页码 在查询结果数据总量很大时，快钱会将支付结果分多次返回。本参数表示商户需要得到的记录集页码。
		// 默认为1，表示第1页。
		Element requestPageElement = rootElement.addElement("requestPage");
		requestPageElement.setText(this.getRequestPage());

		// 组合字符串。。必须按照此顺序组串
		Element signMsgValElement = rootElement.addElement("signMsgVal");
		signMsgValElement.setText(this.getSignMsgVal());

		
		// 服务器接收支付结果的页面地址
		Element pageUrlElement = rootElement.addElement("pageUrl");
		pageUrlElement.setText(this.getPageUrl());
		
		// 服务器接收支付结果的后台地址
		Element bgUrlElement = rootElement.addElement("bgUrl");
		bgUrlElement.setText(this.getBgUrl());

		/** 退款相关参数* */
		// 退款接口版本号 目前固定为此值
		Element refundVersionElement = rootElement.addElement("refundVersion");
		refundVersionElement.setText(this.getRefundVersion());

		// 操作类型 固定值001 001代表下订单请求退款
		Element commandTypeElement = rootElement.addElement("commandType");
		commandTypeElement.setText(this.getCommandType());

		// 加密所需的key值，线上的话发到商户快钱账户邮箱里
		Element merchantKeyElement = rootElement.addElement("merchantKey");
		merchantKeyElement.setText(this.getMerchantKey());

		// 退费地址
		Element bankResultValueElement = rootElement.addElement("bankResultValue");
		bankResultValueElement.setText(this.getBankResultValue());
		try {
			String path = this.getPlatformConfigAbsPath() + PayGatewayConfig.FILENAME;
			File file = new File(path);
			XMLWriter output = new XMLWriter(new FileWriter(file));
			output.write(document);
			output.close();
		} catch (IOException e) {
			throw new PlatformException("error in creat platform config xml file!");
		}
	}

	public static String getFILENAME() {
		return FILENAME;
	}

	public static void setFILENAME(String filename) {
		FILENAME = filename;
	}

	public String getPlatformConfigAbsPath() {
		return platformConfigAbsPath;
	}

	public void setPlatformConfigAbsPath(String platformConfigAbsPath) {
		this.platformConfigAbsPath = platformConfigAbsPath;
	}

	public String getMerchantAcctId() {
		return merchantAcctId;
	}

	public void setMerchantAcctId(String merchantAcctId) {
		this.merchantAcctId = merchantAcctId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getQueryMode() {
		return queryMode;
	}

	public void setQueryMode(String queryMode) {
		this.queryMode = queryMode;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRequestPage() {
		return requestPage;
	}

	public void setRequestPage(String requestPage) {
		this.requestPage = requestPage;
	}

	public String getSignMsgVal() {
		return signMsgVal;
	}

	public void setSignMsgVal(String signMsgVal) {
		this.signMsgVal = signMsgVal;
	}

	public String getRefundVersion() {
		return refundVersion;
	}

	public void setRefundVersion(String refundVersion) {
		this.refundVersion = refundVersion;
	}

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	public String getMerchantKey() {
		return merchantKey;
	}

	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}

	public String getBankResultValue() {
		return bankResultValue;
	}

	public void setBankResultValue(String bankResultValue) {
		this.bankResultValue = bankResultValue;
	}

	public String getBgUrl() {
		return bgUrl;
	}

	public void setBgUrl(String bgUrl) {
		this.bgUrl = bgUrl;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
}
