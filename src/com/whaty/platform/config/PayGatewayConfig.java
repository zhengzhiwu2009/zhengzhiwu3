/**
 * 
 */
package com.whaty.platform.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.whaty.platform.Exception.PlatformException;

/**
 * 
 * 
 */
public class PayGatewayConfig {

	protected static String FILENAME = "payGatewayConfig.xml";

	private String merchantid;

	private String merkey;

	private String currencytype;

	private String url;

	private String subject;

	private String autojump;

	private String merurl;

	private String tradetype;

	private String bankInput;

	private String confirm;

	private String informmer;

	private String informurl;

	private String strInterface;

	private String waittime;

	private String version;

	private String refundUrl;

	private String singleQueryUrl;

	private String batchQueryUrl;

	private String refundQueryUrl;

	private String platformConfigAbsPath;

	private String bankcardtype;
	private String pdtdetailurl;
	private String pdtdnm;

	public static String getFILENAME() {
		return FILENAME;
	}

	public static void setFILENAME(String filename) {
		FILENAME = filename;
	}

	public PayGatewayConfig(String configAbsPath) {

		this.setPlatformConfigAbsPath(configAbsPath);
	}

	public String getPlatformConfigAbsPath() {
		return platformConfigAbsPath;
	}

	public void setPlatformConfigAbsPath(String platformConfigAbsPath) {
		this.platformConfigAbsPath = platformConfigAbsPath;
	}

	/**
	 * 读取三方支付配置文件
	 * 
	 * @author linjie
	 * @throws PlatformException
	 */
	public void getConfig() throws PlatformException {
		File file = new File(this.getPlatformConfigAbsPath() + PayGatewayConfig.FILENAME);
		if (!file.exists()) {
			throw new PlatformException("can't find the file" + file.getAbsolutePath());
		}
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(file);

			this.setMerchantid(document.selectSingleNode("//" + "merchantid").getText());
			this.setAutojump(document.selectSingleNode("//" + "autojump").getText());
			this.setBankInput(document.selectSingleNode("//" + "bankInput").getText());
			this.setConfirm(document.selectSingleNode("//" + "confirm").getText());
			this.setCurrencytype(document.selectSingleNode("//" + "currencytype").getText());
			this.setInformmer(document.selectSingleNode("//" + "informmer").getText());
			this.setInformurl(document.selectSingleNode("//" + "informurl").getText());
			this.setMerkey(document.selectSingleNode("//" + "merkey").getText());
			this.setMerurl(document.selectSingleNode("//" + "merurl").getText());
			this.setStrInterface(document.selectSingleNode("//" + "strInterface").getText());
			this.setSubject(document.selectSingleNode("//" + "subject").getText());
			this.setTradetype(document.selectSingleNode("//" + "tradetype").getText());
			this.setUrl(document.selectSingleNode("//" + "url").getText());
			this.setWaittime(document.selectSingleNode("//" + "waittime").getText());
			this.setVersion(document.selectSingleNode("//" + "version").getText());
			this.setRefundUrl(document.selectSingleNode("//" + "refundUrl").getText());
			this.setSingleQueryUrl(document.selectSingleNode("//" + "singleQueryUrl").getText());
			this.setBatchQueryUrl(document.selectSingleNode("//" + "batchQueryUrl").getText());
			this.setRefundQueryUrl(document.selectSingleNode("//" + "refundQueryUrl").getText());

			this.setBankcardtype(document.selectSingleNode("//" + "bankcardtype").getText());

		} catch (DocumentException e) {
			throw new PlatformException("error in setConfig");
		}
	}

	/**
	 * 设置三方支付配置文件
	 * 
	 * @author linjie
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

			((Element) document.selectSingleNode("//merchantid")).setText(this.getMerchantid());
			((Element) document.selectSingleNode("//merkey")).setText(this.getMerkey());
			((Element) document.selectSingleNode("//currencytype")).setText(this.getCurrencytype());
			((Element) document.selectSingleNode("//url")).setText(this.getUrl());
			((Element) document.selectSingleNode("//subject")).setText(this.getSubject());
			((Element) document.selectSingleNode("//autojump")).setText(this.getAutojump());
			((Element) document.selectSingleNode("//merurl")).setText(this.getMerurl());
			((Element) document.selectSingleNode("//tradetype")).setText(this.getTradetype());
			((Element) document.selectSingleNode("//bankInput")).setText(this.getBankInput());
			((Element) document.selectSingleNode("//confirm")).setText(this.getConfirm());
			((Element) document.selectSingleNode("//informmer")).setText(this.getInformmer());
			((Element) document.selectSingleNode("//informurl")).setText(this.getInformurl());
			((Element) document.selectSingleNode("//strInterface")).setText(this.getStrInterface());
			((Element) document.selectSingleNode("//waittime")).setText(this.getWaittime());
			((Element) document.selectSingleNode("//version")).setText(this.getVersion());
			((Element) document.selectSingleNode("//refundUrl")).setText(this.getRefundUrl());

		} catch (DocumentException e) {
			throw new PlatformException("Error in setConfig");
		}

		try {
			// XMLWriter writer = new XMLWriter(new FileWriter(new
			// File(filename)));
			// writer.write(document);
			// writer.close();
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
	 * 修改三方支付配置文件
	 * 
	 * @author linjie
	 * @throws PlatformException
	 */
	public void updateConfig() throws PlatformException {
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("root");
		rootElement.addComment("whaty platform configuration");

		Element merchantidElement = rootElement.addElement("merchantid");
		merchantidElement.setText(this.getMerchantid());
		Element merkeyElement = rootElement.addElement("merkey");
		merkeyElement.setText(this.getMerkey());
		Element currencytypeElement = rootElement.addElement("currencytype");
		currencytypeElement.setText(this.getCurrencytype());
		Element urlElement = rootElement.addElement("url");
		urlElement.setText(this.getUrl());
		Element subjectElement = rootElement.addElement("subject");
		subjectElement.setText(this.getSubject());
		Element autojumpElement = rootElement.addElement("autojump");
		autojumpElement.setText(this.getAutojump());
		Element merurlElement = rootElement.addElement("merurl");
		merurlElement.setText(this.getMerurl());
		Element tradetypeElement = rootElement.addElement("tradetype");
		tradetypeElement.setText(this.getTradetype());
		Element bankInputElement = rootElement.addElement("bankInput");
		bankInputElement.setText(this.getBankInput());
		Element confirmElement = rootElement.addElement("confirm");
		confirmElement.setText(this.getConfirm());
		Element informmerElement = rootElement.addElement("informmer");
		informmerElement.setText(this.getInformmer());
		Element informurlElement = rootElement.addElement("informurl");
		informurlElement.setText(this.getInformurl());
		Element strInterfaceElement = rootElement.addElement("strInterface");
		strInterfaceElement.setText(this.getStrInterface());
		Element waittimeElement = rootElement.addElement("waittime");
		waittimeElement.setText(this.getWaittime());
		Element versionElement = rootElement.addElement("version");
		versionElement.setText(this.getVersion());
		Element refundUrlElement = rootElement.addElement("refundUrl");
		refundUrlElement.setText(this.getRefundUrl());
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

	public String getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}

	public String getMerkey() {
		return merkey;
	}

	public void setMerkey(String merkey) {
		this.merkey = merkey;
	}

	public String getCurrencytype() {
		return currencytype;
	}

	public void setCurrencytype(String currencytype) {
		this.currencytype = currencytype;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAutojump() {
		return autojump;
	}

	public void setAutojump(String autojump) {
		this.autojump = autojump;
	}

	public String getMerurl() {
		return merurl;
	}

	public void setMerurl(String merurl) {
		this.merurl = merurl;
	}

	public String getTradetype() {
		return tradetype;
	}

	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}

	public String getBankInput() {
		return bankInput;
	}

	public void setBankInput(String bankInput) {
		this.bankInput = bankInput;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public String getInformmer() {
		return informmer;
	}

	public void setInformmer(String informmer) {
		this.informmer = informmer;
	}

	public String getInformurl() {
		return informurl;
	}

	public void setInformurl(String informurl) {
		this.informurl = informurl;
	}

	public String getStrInterface() {
		return strInterface;
	}

	public void setStrInterface(String strInterface) {
		this.strInterface = strInterface;
	}

	public String getWaittime() {
		return waittime;
	}

	public void setWaittime(String waittime) {
		this.waittime = waittime;
	}

	public String getRefundUrl() {
		return refundUrl;
	}

	public void setRefundUrl(String refundUrl) {
		this.refundUrl = refundUrl;
	}

	public String getSingleQueryUrl() {
		return singleQueryUrl;
	}

	public void setSingleQueryUrl(String singleQueryUrl) {
		this.singleQueryUrl = singleQueryUrl;
	}

	public String getBatchQueryUrl() {
		return batchQueryUrl;
	}

	public void setBatchQueryUrl(String batchQueryUrl) {
		this.batchQueryUrl = batchQueryUrl;
	}

	public String getRefundQueryUrl() {
		return refundQueryUrl;
	}

	public void setRefundQueryUrl(String refundQueryUrl) {
		this.refundQueryUrl = refundQueryUrl;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBankcardtype() {
		return bankcardtype;
	}

	public void setBankcardtype(String bankcardtype) {
		this.bankcardtype = bankcardtype;
	}

	public String getPdtdetailurl() {
		return pdtdetailurl;
	}

	public void setPdtdetailurl(String pdtdetailurl) {
		this.pdtdetailurl = pdtdetailurl;
	}

	public String getPdtdnm() {
		return pdtdnm;
	}

	public void setPdtdnm(String pdtdnm) {
		this.pdtdnm = pdtdnm;
	}

}
