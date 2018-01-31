package com.whaty.platform.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class ParameterRequestWrapper extends HttpServletRequestWrapper {

	private Map params;   
	/**
	 * 带参数的构造方法
	 * @param request
	 * @param newParams
	 * @author huguolong
	 */
	public ParameterRequestWrapper(HttpServletRequest request,Map newParams) {   
	  super(request);   
	  this.params=newParams;   
	}   
	public Map getParameterMap() {   
	  return params;   
	}   

	/**
	 * 得到枚举类型的参数名字
	 * @return Enumeration
	 * @author huguolong
	 */
	public Enumeration getParameterNames() {   
	  Vector l=new Vector(params.keySet());   
	  return l.elements();   
	}   
	
	/**
	 * 根据名字取得参数值数组
	 * @param name
	 * @return String[]
	 * @author huguolong
	 */
	public String[] getParameterValues(String name) {
	  Object v = params.get(name);   
	  if(v==null){   
	    return null;   
	  }else if(v instanceof String[]){   
	    return (String[]) v;   
	  }else if(v instanceof String){   
	    return new String[]{(String) v};   
	  }else{   
	    return new String[]{v.toString()};   
	  }   
	}   
	
	/**
	 * 根据名字取得参数值
	 * @param name
	 * @return String
	 * @author huguolong
	 */
	public String getParameter(String name) {   
	  Object v = params.get(name);   
	  if(v==null){   
	    return null;   
	  }else if(v instanceof String[]){             
	    String []strArr=(String[]) v;   
	    if(strArr.length>0){   
	      return strArr[0];   
	    }else{   
	      return null;   
	    }   
	  }else if(v instanceof String){   
	    return (String) v;   
	  }else{   
	    return v.toString();   
	  }   
	} 
	
	/**
	 * request参数报装工具取得字符串
	 * @return String
	 * @author huguolong
	 */
	@Override
	public String getQueryString() {
		Set<String> keys = params.keySet();
		String queryString = "";
		for(String key : keys) {
			queryString +=key+"="+params.get(key)+"&";
		}
		if(queryString.endsWith("&")) {
			queryString = queryString.substring(0, queryString.length()-1);
		}
		return queryString;
	}

}

