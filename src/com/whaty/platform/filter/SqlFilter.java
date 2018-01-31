package com.whaty.platform.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SqlFilter implements Filter {

	private static String badStr;
	private static String insStr;
	private static String excludeMethods;
	private static String characterEncoding;

	/**
	 * sql注入过滤器的逻辑方法
	 * @param request
	 * @param response
	 * @throws IOException，ServletException
	 * @author linjie
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(characterEncoding);
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
//		System.out.println(request.getParameterMap().keySet().toString());
//		System.out.println(req.getQueryString());
////		System.out.println(req.getParameterNames().toString());
//		System.out.println(req.getParameterMap().toString());
		

		// 获得所有请求参数名
		Enumeration params = req.getParameterNames();
		boolean flagcheck = true;

		// 对于ESSH框架，可以对所配置的方法名去掉此过滤
		String reqURL = req.getRequestURI();
		String method = "";
		try {
			if (reqURL.indexOf("_") > 0 && reqURL.indexOf(".") > 0) {
				method = reqURL.substring(reqURL.indexOf("_"), reqURL
						.indexOf("."));
			}
		} catch (Exception e) {
		}
		if (method != null && !"".equals(method)) {
			if (method.matches("^(" + excludeMethods + ")$")) {
				flagcheck = false;
			}
		}
		String sql = "";
		String names="";
		if (flagcheck) {
			while (params.hasMoreElements()) {
				// 得到参数名
				String name = params.nextElement().toString();
				names+= name; 
				// 得到参数对应值
				String[] value = req.getParameterValues(name);
				for (int i = 0; i < value.length; i++) {
					sql = sql + " " + value[i];
				}
			}
			// 含有sql敏感字符的，对敏感字符做转换
			if (sqlValidate(sql)||sqlValidate(names)) {
				Map m = request.getParameterMap();
				ParameterRequestWrapper wrapRequest = new ParameterRequestWrapper(
						req, processParamsters(m));
				chain.doFilter(wrapRequest, res);
			} else {
				chain.doFilter(req, res);
			}
		} else {
			chain.doFilter(req, res);
		}
	}

	/**
	 * 对字符串进行校验
	 * @param str
	 * @author linjie
	 */
	protected static boolean sqlValidate(String str) {
		str = str.toLowerCase();
		if (str.replace("\r", " ").replace("\n", " ").matches("^.*\\s{0,}("+badStr+")(\\s){0,}.*$"))
			return true;
		return false;
	}

	/**
	 * sql注入过滤器的初始化
	 * @param filterConfig
	 * @throws ServletException
	 * @author linjie
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.badStr = filterConfig.getInitParameter("badStr");
		this.insStr = filterConfig.getInitParameter("insStr");
		this.excludeMethods = filterConfig.getInitParameter("excludeMethods");
		this.characterEncoding = filterConfig
				.getInitParameter("characterEncoding");
	}

	/**
	 * sql注入过滤器的销毁期
	 * @author linjie
	 */
	public void destroy() {
	}

	/**
	 * 对参数的转义字符进行转换
	 * @param m
	 * @return Map
	 * @author linjie
	 */
	public Map processParamsters(Map m) {
		if(m.containsKey("merchantAcctId")){
			Object[] maidValues = (Object[])m.get("merchantAcctId");
			if("1002738866301".indexOf(maidValues[0] + "") != -1){
				return m;
			}
		}
		Iterator iter = m.entrySet().iterator();
		Map m1 = new HashMap();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = entry.getKey().toString();
			Object obj = entry.getValue();
			String val = "";
			if(key.toLowerCase().matches("^[\\s\\S]*<script[\\s\\S]+</[\\s\\S]*script[\\s\\S]*>[\\s\\S]*")) {
				continue;
			}
			String[] badStrs = badStr.split("\\|");
			String[] insStrs = insStr.split("\\|");
			if (obj instanceof String[]) {
				String[] strs = (String[]) obj;
				for (int n = 0; n < strs.length; n++) {
					for (int i = 0; i < badStrs.length; i++) {
						if (strs[n].toLowerCase().replace("\r", " ").replace(
								"\n", " ").matches(
								"^[\\s\\S]*(" + badStrs[i]
										+ ")[\\s\\S]*$")) {
							// 因\r\n换行导致换行后无法匹配，特做此转换
							// 转义javascript
							System.err.println("filtered badstr: "+ badStrs[i] + " \nold: " + strs[n]);
							strs[n] = strs[n].toLowerCase().replace("\r", " ")
									.replace("\n", " ").replaceAll(
											"(.*?\\s){0,1}(" + badStrs[i]
													+ ")(\\s.*?){0,1}",
											"$1" + insStrs[i] + "$3");
							System.err.println("new: " + strs[n]);
						}
					}
				}
				m1.put(key, strs);
			} else {
				val = obj.toString();
				for (int i = 0; i < badStrs.length; i++) {
					if (val.indexOf(badStrs[i]) != -1) {
						val = val.replaceAll(badStrs[i], insStrs[i]);
					}
				}
				m1.put(key, val);
			}
		}
		return m1;
	}
}
