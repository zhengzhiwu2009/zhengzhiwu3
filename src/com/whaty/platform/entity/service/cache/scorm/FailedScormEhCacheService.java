package com.whaty.platform.entity.service.cache.scorm;

import java.util.List;

import net.sf.ehcache.Cache;

import com.whaty.platform.standard.scorm.datamodels.SCODataManager;

/**
 * 用户登录缓存服务
 * 
 * @author xmeans
 * */
public interface FailedScormEhCacheService {

	public SCODataManager getFromCache(String productId);
	
	public void putToCache(String key, Object value);
	
	public void putToCache(String key, Object value, int liveSeconds);
	
	public void clear();
	
	public Cache getCache();
	
	public void removeScorm(String key);
}
