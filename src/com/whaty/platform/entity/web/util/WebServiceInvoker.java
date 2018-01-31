package com.whaty.platform.entity.web.util;

import javax.xml.namespace.QName;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class WebServiceInvoker {

	public static String NAME_SPACE = "http://service.tax.com/";
	public static String METHOD = "service";
//	public static String URL = "http://106.37.170.83:8080/eips/webservice/EipsWebService?wsdl"; //开发环境地址
	public static String URL = "http://192.168.70.190:7007/eips/webservice/EipsWebService?wsdl"; //测试环境地址 
//	public static String URL = "http://192.168.81.190/eips/webservice/EipsWebService?wsdl"; //生产环境地址 
	public String client(String xml) {
		return this.callWebService(this.URL, xml);
	}

	public String callWebService(String url, String method, String data) {
		return callJaxWsDynamic(url, "", method, new Object[] { data });
	}

	public String callWebService(String url, String nameSpace, String method,
			String data) {
		return callJaxWsDynamic(url, nameSpace, method, new Object[] { data });
	}

	public String callWebService(String url, String data) {
		return callJaxWsDynamic(url, this.NAME_SPACE, this.METHOD,
				new Object[] { data });
	}

	public String callJaxWsDynamic(String url, String method, Object[] data) {
		return callJaxWsDynamic(url, "", method, data);
	}

	public String callJaxWsDynamic(String url, String nameSpace, String method,
			Object[] data) {
		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory
				.newInstance();
		Client client = factory.createClient(url);
		try {
			QName qName = new QName(nameSpace, method);
			System.out.println("WebService调用URL:" + url + "-调用方式" + method
					+ "-请求数据" + data[0]);
			Object[] object = client.invoke(qName, data);
			String result = object[0].toString();
			System.out.println("WebService调用URL:" + url + "-调用方式" + method
					+ "-返回数据" + result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
