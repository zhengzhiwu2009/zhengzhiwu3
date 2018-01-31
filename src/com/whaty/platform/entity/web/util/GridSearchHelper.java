package com.whaty.platform.entity.web.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;

/**
 * 目前仅仅用于Grid搜索的时候对搜索参数进行缓存，缓存在Session中
 */

public class GridSearchHelper {
	
	/**
	 * 根据uri获取缓存的搜索参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getSearchParamInSession(){
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		HttpServletRequest request= (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);
		String uri = request.getRequestURI();
		
		if(session.containsKey("paramQueue")){//对该uri缓存中已经有了搜索参数
			GridSearchParamQueue paramQueue = (GridSearchParamQueue) session.get("paramQueue");
			Map<String,Object> sessionParam = paramQueue.getParamMapInQueue(uri);//session中缓存的搜索参数
			Map<String,Object> reqParams = context.getParameters();//搜索参数
			if(!isSearchParamEmpty(reqParams)){//如果搜索参数不为空，比较session中参数和request中参数
				//如果不相等，那么返回null,在MyBaseAction中会根据是否为空从request中新的搜索
				if(!isSearchParamEquals(sessionParam,reqParams)){
					return null;
				}
			}
			return sessionParam;
		}else{
			return null;
		}
	}
	
	/**
	 * 根据uri获取缓存的搜索参数
	 * @param uri
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getSearchParamInSession(String uri){
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		if(session.containsKey("paramQueue")){//对该uri缓存中已经有了搜索参数
			GridSearchParamQueue paramQueue = (GridSearchParamQueue) session.get("paramQueue");
			return paramQueue.getParamMapInQueue(uri);//session中缓存的搜索参数
		}else{
			return null;
		}
	}
	
	
	/**
	 * 保存搜索参数
	 */
	@SuppressWarnings("unchecked")
	public static void saveSearchParamInSession(){
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		HttpServletRequest request= (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);
		
		String uri = request.getRequestURI();
		Map<String,Object> params = context.getParameters();
		
//		if(!isSearchParamEmpty(params)){//如果params不为空
		GridSearchParamQueue paramQueue;
		if(session.containsKey("paramQueue")){
			paramQueue = (GridSearchParamQueue) session.get("paramQueue");
		}else{
			paramQueue = new GridSearchParamQueue();
			session.put("paramQueue", paramQueue);
		}
		paramQueue.saveParamInQueue(uri, params);
//		}
	}
	
	/**
	 * 判断搜索条件是否为空(只判断sarch__开头的搜索条件) 
	 * @param params 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static boolean isSearchParamEmpty(Map params){
		Set<String> keys = params.keySet();
		for(String key : keys){
			if(!"limit".equals(key)&&!"start".equals(key)&&!"sort".equals(key)&&!"dir".equals(key)&&!"backParam".equals(key)){
				if(key.startsWith("search__")){
					String[] value = (String[])params.get(key);
					if(!StringUtils.isEmpty(value[0]))
						return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 判断搜索条件是否相等
	 */
	private static boolean isSearchParamEquals(Map sessionParams,Map requestParams){
		if(sessionParams == null){
			return false;
		}
		Set<String> keys = sessionParams.keySet();
		for(String key : keys){
			if(!"limit".equals(key)&&!"start".equals(key)&&!"sort".equals(key)&&!"dir".equals(key)&&!"backParam".equals(key)){
				if(key.startsWith("search__")){
					String[] value1 = (String[])requestParams.get(key);
					String[] value2 = (String[])sessionParams.get(key);
					if(!value1[0].equals(value2[0]))
						return false;
				}
			}
		}
		return true;
	}
	
}



