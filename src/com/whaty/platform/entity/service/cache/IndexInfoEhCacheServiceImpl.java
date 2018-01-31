package com.whaty.platform.entity.service.cache;

import java.util.List;

import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.util.Page;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.locking.ExplicitLockingCache;

public class IndexInfoEhCacheServiceImpl implements IndexInfoEhCacheService {
	
	private CacheManager ehCacheManager;
	
	private String cacheName;
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	
	private Cache cache;
	public Cache getCache() {
		if(cache==null){
			cache = ehCacheManager.getCache(cacheName);
		}
		return cache;
	}
	
	private int defaultMaxLiveSecond = 1000 * 60 * 60;
	//private int defaultMaxLiveSecond = 1000 * 60;
	private long defaultMaxSize = 10000l;

	//将数据放入缓存中，默认生存时间
	public void putToCache(String key, Object value){
		putToCache(key,value,this.defaultMaxLiveSecond);
	}

	//将数据放入缓存中
	public void putToCache(String key, Object value, int liveSeconds){
		try {
			ExplicitLockingCache ehcache = new ExplicitLockingCache(cache);
			ehcache.acquireWriteLockOnKey(key);
			Element element = new Element(key, value);
			element.setTimeToLive(liveSeconds);
			ehcache.put(element);
			ehcache.releaseWriteLockOnKey(key);
		} catch(Exception e) {
			
		}
	}
	
    //从缓存中获取
    public Page getFromCache(String key){
    	//System.out.println(loginId);
    	this.setCacheName("indexInfoCache");
    	Element el = getCache().get(key);
    	if(el!=null&&el.getValue()!=null){
    		return (Page) el.getValue();
    	}else{
    		return null;
    	}
    }
  //从缓存中获取
    public List getValueFromCache(String key){
    	this.setCacheName("indexInfoCache");
    	Element el = getCache().get(key);
    	if(el!=null&&el.getValue()!=null){
    		return (List) el.getValue();
    	}else{
    		return null;
    	}
    }
    
    //清除指定的数据
    public void remove(String key){
    	getCache().remove(key);
    }
    
    //清空所有缓存
	public void clear(){
		getCache().removeAll();
	}

	public CacheManager getEhCacheManager() {
		return ehCacheManager;
	}

	public void setEhCacheManager(CacheManager ehCacheManager) {
		this.ehCacheManager = ehCacheManager;
	}
	
}
