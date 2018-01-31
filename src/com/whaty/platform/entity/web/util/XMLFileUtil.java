package com.whaty.platform.entity.web.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;

public class XMLFileUtil {
	public static final String SAVE_BUSSINESS = "EInvDataSave";//申请发票
	public static final String UPDATE_BUSSINESS = "InvDataUpdate";//修改发票
	public static final String DELETE_BUSSINESS = "InvDataDelete";//删除发票
	public static final String QUERY_BUSSINESS = "EInvDataQuery";//查询发票信息
//	public static final String NSRSBH = "12100000754677982U";//生产
//	public static final String NSRSBH = "123456789012345";//测试电子发票
	public static final String NSRSBH = "500102010002807";//测试专用发票
//	public static final String GMFNSRSBH = "234567890112345";//购买方纳税人识别号 仅供测试使用
	
	public static String x = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
							 "<root>" + 
							 "	<head>" + 
							 "		<appSys>EIPS</appSys>" +
							 "		<requestSys>YC</requestSys>" + 
							 "		<sessionId>1234567890123456</sessionId>" + 
							 "		<business>GetInvPdf</business>" + 
							 "		<requestTime>2016-06-02 14:10:10</requestTime>" + 
							 "		<version>1.0</version>" + 
							 "		<returnCode>0000</returnCode>" +
							 "		<returnMsg>0000</returnMsg>" +
							 "	</head>" + 
							 "	<body>" + 
							 "		<dataDesc>" + 
							 "			<zipCode>0</zipCode>" + 
							 "			<encryptCode>0</encryptCode>" + 
							 "			<codeType>UTF-8</codeType>" + 
							 "			<digesData>CHINAZXTSIGNDATA</digesData>" + 
							 "		</dataDesc>" + 
							 "		<signDesc>" + 
							 "			<signCode>0</signCode>" + 
							 "			<signData></signData>" + 
							 "			<signKey></signKey>" + 
							 "		</signDesc>" + 
							 "		<content>" +
							 "			<![CDATA[" +
							 "			<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
							 "			<InvoiceInfoBatch>" + 
							 "				<InvoiceInfo>" + 
							 "					<DDJYH>111001571071</DDJYH>" + 
							 "					<DDBH>90663001</DDBH>" + 
							 "					<FPLSH>03527779231551883440</FPLSH>" + 
							 "					<RETURNCODE>0000</RETURNCODE>" +
							 "					<RETURNMSG>新增订单已存在</RETURNMSG>" +
							 "				</InvoiceInfo>" + 
							 "			</InvoiceInfoBatch>" + 
							 "			]]>" +
							 "		</content>" + 
							 "	</body>" + 
							 "</root>";
	
	public static String getXMLFile(Map param) {
		String xml = "";
		String data_xml = "";
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		PeBzzInvoiceInfo invoice = (PeBzzInvoiceInfo)param.get("invoice");
		//数据报文
		data_xml = "<InvoiceInfoBatchRequest>"
			+ "		<InvoiceInfoRequest>"
			+ "			<DDJYH>" + param.get("seq") + "</DDJYH>"
			+ "			<DDBH>" + param.get("seq") + "</DDBH>"
			+ "			<DDRQ>" + sd.format(new Date()) + "</DDRQ>"
			+ "			<FPLX>" + param.get("fplx") + "</FPLX>"
			+ "			<KPLB>" + ("3".equals(invoice.getEnumConstByInvoiceType().getCode()) ? "1" : "2") + "</KPLB>"
			+ "			<NSRSBH>" + NSRSBH + "</NSRSBH>" ;
			if(invoice.getEnumConstByInvoiceType().getCode().equals("3")){
		 data_xml += "	<GMFNSRSBH> </GMFNSRSBH>"
			+ "			<GMFNSRMC>" + (invoice.getTitle() == null ? "" : invoice.getTitle()) + "</GMFNSRMC>"
			+ "			<GMFDZ> </GMFDZ>"
			+ "			<GMFDH> </GMFDH>"
			+ "			<GMFKHYH> </GMFKHYH>"
			+ "			<GMFYHZH> </GMFYHZH>"
			+ "			<GMFSJHM>" + (invoice.getGmfsjhm() == null  ? "" : invoice.getGmfsjhm()) + "</GMFSJHM>"
			+ "			<GMFYXDZ>" + (invoice.getEmail() == null  ? "" : invoice.getEmail()) + "</GMFYXDZ>"
			+ "			<JSHJ>" + ("1".equals(param.get("fplx")) ? "-" + param.get("amount") : param.get("amount")) + "</JSHJ>"
			+ "			<ISMAIL>1</ISMAIL>"		
			+ "			<SJRMC> </SJRMC>"
			+ "			<SJRDZSHENG> </SJRDZSHENG>"
			+ "			<SJRDZSHI> </SJRDZSHI>"
			+ "			<SJRDZQX> </SJRDZQX>"
			+ "			<SJRXXDZ> </SJRXXDZ>"
			+ "			<SJRDH> </SJRDH>"
			+ "			<SJRYZBM> </SJRYZBM>"
			+ "			<BZ>" + (invoice.getInvoiceRemark() == null ? "" : invoice.getInvoiceRemark()) + "</BZ>"
			+ "			<YDDBH>" + ("1".equals(param.get("fplx")) ? param.get("yddbh") : "") + "</YDDBH>";
			}else{
//			+ "			<GMFNSRSBH>" + (invoice.getGmfnsrsbh() == null  ? "" : invoice.getGmfnsrsbh()) + "</GMFNSRSBH>"
		data_xml += "	<GMFNSRSBH>" + (invoice.getGmfnsrsbh() == null  ? "" : invoice.getGmfnsrsbh()) + "</GMFNSRSBH>"
			+ "			<GMFNSRMC>" + (invoice.getTitle() == null ? "" : invoice.getTitle()) + "</GMFNSRMC>"
			+ "			<GMFDZ>" + (invoice.getGmfdz() == null  ? "" : invoice.getGmfdz()) + "</GMFDZ>"
			+ "			<GMFDH>" + (invoice.getGmfdh() == null  ? invoice.getGmfsjhm() : invoice.getGmfdh()) + "</GMFDH>"
			+ "			<GMFKHYH>" + (invoice.getGmfkhyh() == null  ? "" : invoice.getGmfkhyh()) + "</GMFKHYH>"
			+ "			<GMFYHZH>" + (invoice.getGmfyhzh() == null  ? "" : invoice.getGmfyhzh()) + "</GMFYHZH>"
			+ "			<GMFSJHM>" + (invoice.getGmfsjhm() == null  ? "" : invoice.getGmfsjhm()) + "</GMFSJHM>"
			+ "			<GMFYXDZ>" + (invoice.getEmail() == null  ? "" : invoice.getEmail()) + "</GMFYXDZ>"
			+ "			<JSHJ>" + ("1".equals(param.get("fplx")) ? "-" + param.get("amount") : param.get("amount")) + "</JSHJ>"
			+ "			<ISMAIL>1</ISMAIL>"		
			+ "			<SJRMC>" + (invoice.getAddressee() == null ? "" : invoice.getAddressee()) + "</SJRMC>"
			+ "			<SJRDZSHENG>" + (invoice.getProvince() == null ? "" : invoice.getProvince()) + "</SJRDZSHENG>"
			+ "			<SJRDZSHI>" + (invoice.getCity() == null ? "" : invoice.getCity()) + "</SJRDZSHI>"
			+ "			<SJRDZQX>- </SJRDZQX>"
			+ "			<SJRXXDZ>" + (invoice.getAddress() == null ? "" : invoice.getAddress()) + "</SJRXXDZ>"
			+ "			<SJRDH>" + (invoice.getPhone() == null ? "" : invoice.getPhone()) + "</SJRDH>"
			+ "			<SJRYZBM>" + (invoice.getZipCode() == null ? "" : invoice.getZipCode()) + "</SJRYZBM>"
			+ "			<BZ>" + (invoice.getInvoiceRemark() == null ? "" : invoice.getInvoiceRemark()) + "</BZ>"
			+ "			<YDDBH>" + ("1".equals(param.get("fplx")) ? param.get("yddbh") : "") + "</YDDBH>";
			}
		data_xml	+= "			<InvoiceItemList>"
			+ "				<InvoiceItemInfo>"
			+ "					<FPHXZ>0</FPHXZ>"
			+ "					<XMXH>1</XMXH>"
			+ "					<XMMC>培训费</XMMC>"
			+ "					<XMSL>" + ("1".equals(param.get("fplx")) ? -1 : 1) + "</XMSL>"
			+ "					<XMDJ>" + param.get("amount") + "</XMDJ>"
			+ "					<XMJE>" + ("1".equals(param.get("fplx")) ? "-" + param.get("amount") : param.get("amount")) + "</XMJE>"
			+ "				</InvoiceItemInfo>"
			+ "			</InvoiceItemList>"
			+ "		</InvoiceInfoRequest>"
			+ "	</InvoiceInfoBatchRequest>";
		//请求报文
		xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" 
			+"<root>"
			+"<head>"
			+"	<appSys>EIPS</appSys>"
			+"	<requestSys>YC</requestSys>"
			+"	<sessionId>1234567890123456</sessionId>"
			+"	<business>" + param.get("business") + "</business>"
			+"	<requestTime>" + sd.format(new Date()) + "</requestTime>"
			+"	<version>1.0</version>"
			+"</head>"
			+"<body>"
			+"	<dataDesc>"
			+"		<zipCode>0</zipCode>"
			+"		<encryptCode>0</encryptCode>"
			+"		<codeType>UTF-8</codeType>"
			+"		<digesData>CHINAZXTSIGNDATA</digesData>"
			+"	</dataDesc>"
			+"	<signDesc>"
			+"		<signCode>0</signCode>"
			+"		<signData></signData>"
			+"		<signKey></signKey>"
			+"	</signDesc>"
			+"	<content>"
			+	data_xml
			+"	</content>"
			+"</body>"
			+"</root>";
		return xml;
	}
	public static String getXMLQuery(String seq){
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String data_xml ="";
		String xml ="";
		data_xml="<?xml version='1.0' encoding='UTF-8'?>"
			+" 		<InvoiceQueryBatchRequest>"
			+"			<InvoiceQueryRequest> "
			+"				<FPLSH> "+ seq +" </FPLSH>"
			+"      	</InvoiceQueryRequest>"
			+"		</InvoiceQueryBatchRequest>>";
		//请求报文
		xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" 
			+"<root>"
			+"<head>"
			+"	<appSys>EIPS</appSys>"
			+"	<requestSys>YC</requestSys>"
			+"	<sessionId>1234567890123456</sessionId>"
			+"	<business>  EInvDataQuery  </business>"
			+"	<requestTime>" + sd.format(new Date()) + "</requestTime>"
			+"	<version>1.0</version>"
			+"</head>"
			+"<body>"
			+"	<dataDesc>"
			+"		<zipCode>0</zipCode>"
			+"		<encryptCode>0</encryptCode>"
			+"		<codeType>UTF-8</codeType>"
			+"		<digesData>CHINAZXTSIGNDATA</digesData>"
			+"	</dataDesc>"
			+"	<signDesc>"
			+"		<signCode>0</signCode>"
			+"		<signData></signData>"
			+"		<signKey></signKey>"
			+"	</signDesc>"
			+"	<content>"
			+	data_xml
			+"	</content>"
			+"</body>"
			+"</root>";
		return xml;
	}
	
	public static String getXMLQuery(List<PeBzzInvoiceInfo> list){
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		StringBuffer data_xml = new StringBuffer();
		String xml ="";
		if(null != list && list.size() > 0){
			data_xml.append("<InvoiceQueryBatchRequest>");
			for(PeBzzInvoiceInfo beanInfo : list){
				if(null != beanInfo && !"".equals(beanInfo.getInvoiceNum())){
					data_xml.append("			<InvoiceQueryRequest>");
					data_xml.append("				<FPLSH>" + beanInfo.getInvoiceNum() + "</FPLSH>");
					data_xml.append("      	</InvoiceQueryRequest>");
				}
			}
			data_xml.append("		</InvoiceQueryBatchRequest>");
			//请求报文
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" 
				+"<root>"
				+"<head>"
				+"	<appSys>EIPS</appSys>"
				+"	<requestSys>YC</requestSys>"
				+"	<sessionId>1234567890123456</sessionId>"
				+"	<business>" + XMLFileUtil.QUERY_BUSSINESS + "</business>"
				+"	<requestTime>" + sd.format(new Date()) + "</requestTime>"
				+"	<version>1.0</version>"
				+"</head>"
				+"<body>"
				+"	<dataDesc>"
				+"		<zipCode>0</zipCode>"
				+"		<encryptCode>0</encryptCode>"
				+"		<codeType>UTF-8</codeType>"
				+"		<digesData>CHINAZXTSIGNDATA</digesData>"
				+"	</dataDesc>"
				+"	<signDesc>"
				+"		<signCode>0</signCode>"
				+"		<signData></signData>"
				+"		<signKey></signKey>"
				+"	</signDesc>"
				+"	<content>"
				+	data_xml.toString()
				+"	</content>"
				+"</body>"
				+"</root>";
			return xml;
		}else{
			return null;
		}
	}
	/*
	 * 解析返回报文 lwq
	 */
	public static Map<String, Object> getResult(String xml) {
		Map<String, Object> result = new HashMap<String, Object>();
		StringReader reader = new StringReader(xml);
		InputSource source = new InputSource(reader);
		SAXBuilder sb = new SAXBuilder();
		try {
			Document dc = sb.build(source);
			Element root = dc.getRootElement();//获取根节点
			List<Element> elements = root.getChildren();
			if(elements != null && elements.size() > 0) {
				String business = "";
				for(Element el : elements) {
					List<Map<String, String>> returnInfo = new ArrayList<Map<String, String>>();
					if("head".equals(el.getName())) {
						result.put("returnCode", el.getChild("returnCode").getText());
						String msg = new String(el.getChild("returnMsg").getText().getBytes("iso8859-1"), "UTF-8");
						result.put("returnMsg", msg);
						business = el.getChild("business").getText();
					}
					if("body".equals(el.getName())) {
						List<Element> bodyElements = el.getChildren();
						for(Element bodys : bodyElements) {
							if("content".equals(bodys.getName())) {
								String returnXml = bodys.getText().trim();
								StringReader sr = new StringReader(returnXml);
								InputSource is = new InputSource(sr);
								SAXBuilder sax = new SAXBuilder();
								Document doc = sax.build(is);
								Element element = doc.getRootElement();//获取根节点 InvoiceInfo
								List<Element> elementList = element.getChildren();
								for(Element invoiceInfo : elementList) {
									Map<String, String> invoice = new HashMap<String, String>();
									if("InvoiceInfo".equals(invoiceInfo.getName())) {
										invoice.put("seq", invoiceInfo.getChild("DDBH").getText());
										invoice.put("fplsh", invoiceInfo.getChild("FPLSH").getText());
										if(XMLFileUtil.SAVE_BUSSINESS.equals(business)) {
											invoice.put("returnCode", invoiceInfo.getChild("RETURNCODE").getText());
											invoice.put("returnMsg", invoiceInfo.getChild("RETURNMSG").getText());
										}
										returnInfo.add(invoice);
									}
								}
							}
						}
					}
					result.put("returnInfo", returnInfo);
				}
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
//		getResult(x);
		parseQueryXml(query_xml);
	}

	public static Map<String, Object> parseQueryXml(String xml) {
		Map<String, Object> result = new HashMap<String, Object>();
		StringReader reader = new StringReader(xml);
		InputSource source = new InputSource(reader);
		SAXBuilder sb = new SAXBuilder();
		try {
			Document dc = sb.build(source);
			Element root = dc.getRootElement();//获取根节点
			List<Element> elements = root.getChildren();
			if(elements != null && elements.size() > 0) {
				for(Element el : elements) {
					List<Map<String, String>> returnInfo = new ArrayList<Map<String, String>>();
					if("head".equals(el.getName())) {
						result.put("returnCode", el.getChild("returnCode").getText());
						String msg = new String(el.getChild("returnMsg").getText().getBytes("iso8859-1"), "UTF-8");
						result.put("returnMsg", msg);
					}
					if("body".equals(el.getName())) {
						List<Element> bodyElements = el.getChildren();
						for(Element bodys : bodyElements) {
							if("content".equals(bodys.getName())) {
								String returnXml = bodys.getText().trim();
								StringReader sr = new StringReader(returnXml);
								InputSource is = new InputSource(sr);
								SAXBuilder sax = new SAXBuilder();
								Document doc = sax.build(is);
								Element element = doc.getRootElement();//获取根节点 InvoiceInfo
								List<Element> elementList = element.getChildren();
								for(Element invoiceInfo : elementList) {
									Map<String, String> invoice = new HashMap<String, String>();
									if("InvoiceQueryRequest".equals(invoiceInfo.getName())) {
										Map<String, String> map = new HashMap<String, String>();
										map.put("FPLSH", invoiceInfo.getChild("FPLSH").getText());// 发票流水号
										map.put("FPDM", invoiceInfo.getChild("FPDM").getText());// 发票代码
										map.put("FPHM", invoiceInfo.getChild("FPHM").getText());// 发票号码
										map.put("FPJYM", invoiceInfo.getChild("FPJYM").getText());// 发票校验码
										map.put("KPRQ", invoiceInfo.getChild("KPRQ").getText());// 开票日期
										returnInfo.add(map);
									}
								}
							}
						}
					}
					result.put("returnInfo", returnInfo);
				}
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String query_xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
					   "<root>" +
					   "	<head>" +
					   "		<appSys>EIPS</appSys>" +
					   "		<requestSys>YC</requestSys>" +  
					   "	    <sessionId>1234567890123456</sessionId>" +  
					   "	    <business>EInvDataQuery</business>" +  
					   "		<requestTime>2016-06-30 11:21:00</requestTime>" +  
					   "		<version>1.0</version>" +
					   "   	 	<responseTime>2016-06-30 11:21:49</responseTime>" +
					   "    	<returnCode>0000</returnCode>" +
					   "    	<returnMsg>5Y+R56Wo5p+l6K+i5oiQ5Yqf44CC</returnMsg>" +
					   "	</head>" +
					   "	<body>" + 
					   "		<dataDesc>" + 
					   "			<zipCode>0</zipCode>" +  
					   "			<encryptCode>0</encryptCode>" +  
					   "      		<codeType>UTF-8</codeType>" +  
					   "			<digesData>CHINAZXTSIGNDATA</digesData>" + 
					   "		</dataDesc>" +  
					   "    	<signDesc>" + 
					   "			<signCode>0</signCode>" +  
					   "			<signData/>" +  
					   "    		<signKey/>" + 
					   "		</signDesc>" +  
					   "    	<content>" +
					   "			<![CDATA[" +
					   "			<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
					   "			<InvoiceQueryBatchRequest>" +
					   "				<InvoiceQueryRequest>" +
					   "					<FPLSH>20160630102756139791</FPLSH>" +
					   "					<FPDM/>" +
					   "					<FPHM/>" +
					   "					<FPJYM/>" +
					   "					<KPRQ/>" +
					   "					<RETURNCODE>9999</RETURNCODE>" +
					   "					<RETURNMSG>发票开具失败：纳税人未进行电子发票资格认定，无法开票！</RETURNMSG>" +
					   "				</InvoiceQueryRequest>" +
					   "				<InvoiceQueryRequest>" +
					   "					<FPLSH>20160629160180462937</FPLSH>" +
					   "					<FPDM/>" +
					   "					<FPHM/>" +
					   "					<FPJYM/>" +
					   "					<KPRQ/>" +
					   "					<RETURNCODE>9999</RETURNCODE>" +
					   "					<RETURNMSG>未查询到对应发票.</RETURNMSG>" +
					   "				</InvoiceQueryRequest>" +
					   "			</InvoiceQueryBatchRequest>" +
					   "			]]>" +
					   "		</content>" +
					   "	</body>" +
					   "</root>";
}
