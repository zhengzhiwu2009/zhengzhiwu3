package com.whaty.platform.entity.service.cache;

import java.util.List;

import com.whaty.platform.entity.util.Page;

/**
 * 系统后台首页提示信息缓存接口类
 * 
 * @author xmeans
 * */
public interface IndexInfoEhCacheService {

	public Page getFromCache(String key);
	public List getValueFromCache(String key);
	
	public void putToCache(String key, Object value);
	
	public void putToCache(String key, Object value, int liveSeconds);
	
	public void clear();
}
