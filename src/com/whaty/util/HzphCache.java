package com.whaty.util;


import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
public class HzphCache implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String cacheName;
	
	private CacheManager ehCacheManager;
	
	private Cache cache;
	

	public Cache getCache() {
		if(cache==null){
			return ehCacheManager.getCache(cacheName);
		}
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public CacheManager getEhCacheManager() {
		return ehCacheManager;
	}

	public void setEhCacheManager(CacheManager ehCacheManager) {
		this.ehCacheManager = ehCacheManager;
	}
	//将数据放入缓存中
	public void putToCache(String key, Object value, int liveSeconds){
		remove(key);
		getCache().put(new Element(key, value, false, liveSeconds, liveSeconds));
	}
    //清除指定的数据
    public boolean remove(String key){
    	boolean result = getCache().remove(key);
    	return result;
    }
    //清空所有缓存
	public void clear(){
		getCache().removeAll();
	}
	
}
