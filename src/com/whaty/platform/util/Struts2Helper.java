package com.whaty.platform.util;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
//import org.codehaus.jackson.map.ObjectMapper;

import com.opensymphony.xwork2.ActionContext;
//import com.whaty.core.common.session.RequestWrapper;

@SuppressWarnings("unchecked")
public class Struts2Helper {
	
	/**
	 * 获取ext的  extraParam 参数值
	 * @param key
	 * @return
	 */
	public static String getExtExtraParamValue(String key){
		Map<String, String[]> param = getRequestParams();
		if(param.containsKey(key)){
			return ((String[])param.get(key))[0];
		}else{
			if(param.containsKey("extraParam")){//这个参数是为Ext 的LogicQueryPageGrid中的extraparam使用的，代表其他参数，，当然我们只取extraparam中的值
				JSONObject jsObj = JSONObject.fromObject(((String[])param.get("extraParam"))[0]);
				if(jsObj.containsKey(key)){
					Object obj = jsObj.get(key);
					return obj.toString();
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取request 参数
	 * @return
	 */
	public static Map<String, String[]> getRequestParams(){
		return getServletRequest().getParameterMap();
	}
	
	/**
	 * 获取request 参数
	 * @return
	 */
	public static Map<String, String[]> getParams(){
		return getServletRequest().getParameterMap();
	}
	
	/**
	 * 获取某个参数值
	 * @param paramName
	 * @return
	 */
	public static String getParamValue(String paramName){
		if(getServletRequest().getParameterMap().containsKey(paramName)){
			return getServletRequest().getParameter(paramName);
		}else{
			if(getServletRequest().getParameterMap().containsKey("extraParam")){
				JSONObject jsObj = JSONObject.fromObject(getServletRequest().getParameter("extraParam"));
				if(jsObj.containsKey(paramName))
					return	(String)jsObj.get(paramName);
			}
		}
		return null;
	}
	
	/**
	 * 取得HttpRequest的简化函数.
	 */
	public static HttpServletRequest getServletRequest(){
		ActionContext ctx = ActionContext.getContext();
		return (HttpServletRequest) ctx.get(StrutsStatics.HTTP_REQUEST);
	}
	
	/**
	 * 取得HttpResponse的简化函数.
	 */
	public static HttpServletResponse getServletResponse() {
		ActionContext ctx = ActionContext.getContext();
		return (HttpServletResponse) ctx.get(StrutsStatics.HTTP_RESPONSE);
	}
	
	/**
	 * 取得ServletContext的简化函数.
	 */
	public static ServletContext getServletContext(){
		ActionContext ctx = ActionContext.getContext();
		return (ServletContext)ctx.get(StrutsStatics.SERVLET_CONTEXT);
	}
	

	/**
	 * 以下是获取封装后的SessionWrapper,RequestWrapper
	 * 主要是针对于SessionWrapper缓存了的操作，这里需要已经配置了缓存、Filter等
	 */
//
//	public static RequestWrapper getRequestWrapper(){
//		return (RequestWrapper)ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
//	}
//	


	//-- header 常量定义 --//
	private static final String HEADER_ENCODING = "encoding";
	private static final String HEADER_NOCACHE = "no-cache";
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final boolean DEFAULT_NOCACHE = true;

	//-- content-type 常量定义 --//
	private static final String TEXT_TYPE = "text/plain";
	private static final String JSON_TYPE = "application/json";
	private static final String XML_TYPE = "text/xml";
	private static final String HTML_TYPE = "text/html";
	private static final String JS_TYPE = "text/javascript";

//	private static ObjectMapper mapper = new ObjectMapper();

	//-- 取得Request/Response/Session的简化函数 --//
	/**
	 * 取得HttpSession的简化函数.
	 */
	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * 取得HttpRequest的简化函数.
	 */
	public static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 取得HttpResponse的简化函数.
	 */
	public static HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 取得Request Parameter的简化方法.
	 */
	public static String getParameter(String name) {
		return getRequest().getParameter(name);
	}
	
	/**
	 * 取得Request getParameterValues的简化方法.
	 */
	public static String[] getParameterValues(String name) {
		return getRequest().getParameterValues(name);
	}

	//-- 绕过jsp/freemaker直接输出文本的函数 --//
	/**
	 * 直接输出内容的简便函数.

	 * eg.
	 * render("text/plain", "hello", "encoding:GBK");
	 * render("text/plain", "hello", "no-cache:false");
	 * render("text/plain", "hello", "encoding:GBK", "no-cache:false");
	 * 
	 * @param headers 可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
	 */
//	public static void render(final String contentType, final String content, final String... headers) {
//		HttpServletResponse response = initResponse(contentType, headers);
//		try {
//			response.getWriter().write(content);
//			response.getWriter().flush();
//		} catch (IOException e) {
//			throw new RuntimeException(e.getMessage(), e);
//		}
//	}

	/**
	 * 直接输出文本.
	 * @see #render(String, String, String...)
	 */
//	public static void renderText(final String text, final String... headers) {
//		render(TEXT_TYPE, text, headers);
//	}

	/**
	 * 直接输出HTML.
	 * @see #render(String, String, String...)
	 */
//	public static void renderHtml(final String html, final String... headers) {
//		render(HTML_TYPE, html, headers);
//	}

	/**
	 * 直接输出XML.
	 * @see #render(String, String, String...)
	 */
//	public static void renderXml(final String xml, final String... headers) {
//		render(XML_TYPE, xml, headers);
//	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param jsonString json字符串.
	 * @see #render(String, String, String...)
	 */
//	public static void renderJson(final String jsonString, final String... headers) {
//		render(JSON_TYPE, jsonString, headers);
//	}

	/**
	 * 直接输出JSON,使用Jackson转换Java对象.
	 * 
	 * @param data 可以是List<POJO>, POJO[], POJO, 也可以Map名值对.
	 * @see #render(String, String, String...)
	 */
//	public static void renderJson(final Object data, final String... headers) {
//		HttpServletResponse response = initResponse(JSON_TYPE, headers);
//		try {
//			mapper.writeValue(response.getWriter(), data);
//		} catch (IOException e) {
//			throw new IllegalArgumentException(e);
//		}
//	}

	/**
	 * 直接输出支持跨域Mashup的JSONP.
	 * @param callbackName callback函数名.
	 * @param object Java对象,可以是List<POJO>, POJO[], POJO ,也可以Map名值对, 将被转化为json字符串.
	 */
//	public static void renderJsonp(final String callbackName, final Object object, final String... headers) {
//		String jsonString = null;
//		try {
//			jsonString = mapper.writeValueAsString(object);
//		} catch (IOException e) {
//			throw new IllegalArgumentException(e);
//		}
//
//		String result = new StringBuilder().append(callbackName).append("(").append(jsonString).append(");").toString();
//
//		//渲染Content-Type为javascript的返回内容,输出结果为javascript语句, 如callback197("{html:'Hello World!!!'}");
//		render(JS_TYPE, result, headers);
//	}

	/**
	 * 分析并设置contentType与headers.
	 */
//	private static HttpServletResponse initResponse(final String contentType, final String... headers) {
//		//分析headers参数
//		String encoding = DEFAULT_ENCODING;
//		boolean noCache = DEFAULT_NOCACHE;
//		for (String header : headers) {
//			String headerName = StringUtils.substringBefore(header, ":");
//			String headerValue = StringUtils.substringAfter(header, ":");
//
//			if (StringUtils.equalsIgnoreCase(headerName, HEADER_ENCODING)) {
//				encoding = headerValue;
//			} else if (StringUtils.equalsIgnoreCase(headerName, HEADER_NOCACHE)) {
//				noCache = Boolean.parseBoolean(headerValue);
//			} else {
//				throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
//			}
//		}
//
//		HttpServletResponse response = ServletActionContext.getResponse();
//
//		//设置headers参数
//		String fullContentType = contentType + ";charset=" + encoding;
//		response.setContentType(fullContentType);
//		if (noCache) {
//			WebUtils.setNoCacheHeader(response);
//		}
//
//		return response;
//	}

	/**
	 * 下载附件
	 * @param fileName 文件名称
	 * @param path 文件所在路径
	 * @param response HttpServletResponse
	 */
	public static void downloadAttachment(String fileName, String path, HttpServletResponse response){  
	    BufferedOutputStream bos = null;  
	    FileInputStream fis = null;  
	    if(null == response){
	    	response = ServletActionContext.getResponse();
	    }
	    if (fileName != null && !"".equals(fileName)) {  
	        try {   
	        	String disposition = "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8");
	        	//注意如果要下载的文件名不做URLEcode处理，项目部署到linux服务器上的话，下载提示框会显示文件名为乱码		
	            response.setContentType("application/x-msdownload;charset=UTF-8");
	            response.setHeader("Content-disposition", disposition);  
	              
	            fis = new FileInputStream(path);  
	            bos = new BufferedOutputStream(response.getOutputStream());  
	            byte[] buffer = new byte[2048];  
	            while(fis.read(buffer) != -1){  
	                bos.write(buffer);  
	            }  
	            bos.flush();
	        } catch (IOException e) {  
	              
	        }finally {  
	            if(fis != null){try {fis.close();} catch (IOException e) {}}  
	            if(bos != null){try {bos.close();} catch (IOException e) {}}  
	        }  
	    }  
	}  
	

}
