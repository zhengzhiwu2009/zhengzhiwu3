package com.whaty.platform.entity.service.imp.scorm;

import java.util.List;

import com.whaty.platform.entity.service.cache.scorm.FailedScormEhCacheService;
import com.whaty.platform.standard.scorm.datamodels.SCODataManager;
import com.whaty.platform.standard.scorm.operation.ScormItem;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.locking.ExplicitLockingCache;

public class FailedScormEhCacheServiceImpl implements FailedScormEhCacheService {
	
	private CacheManager ehCacheManager;

	private String cacheName="FailedScormCache";
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	
	private Cache cache;
	public Cache getCache() {
		if(cache==null){
			System.out.println(cacheName);
			cache = ehCacheManager.getCache(cacheName);
		}
		return cache;
	}
	
	private int defaultMaxLiveSecond = 1000 * 60 * 60;
	//private int defaultMaxLiveSecond = 60; //60秒
	private long defaultMaxSize = 10000l;

	//将数据放入缓存中，默认生存时间
	public void putToCache(String key, Object value){
		this.setCacheName("FailedScormCache");
		putToCache(key,value,this.defaultMaxLiveSecond);
	}

	//将数据放入缓存中
	public void putToCache(String key, Object value, int liveSeconds){
		try{
			ExplicitLockingCache ehcache = new ExplicitLockingCache(cache);
			ehcache.acquireWriteLockOnKey(key);
			remove(key);
			Element element = new Element(key, value);
			element.setTimeToLive(liveSeconds);
			getCache().put(element);
			ehcache.releaseWriteLockOnKey(key);
		}catch(Exception e){
			
		}
		
	}
	
    //从缓存中获取
    public SCODataManager getFromCache(String courseId){
    	System.out.println(courseId);
    	this.setCacheName("FailedScormCache");
    	Element el = getCache().get(courseId);
    	if(el!=null&&el.getValue()!=null){
    		return (SCODataManager) el.getValue();
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

	@Override
	public void removeScorm(String key) {
		// TODO Auto-generated method stub
		getCache().remove(key);
	}
	
}
