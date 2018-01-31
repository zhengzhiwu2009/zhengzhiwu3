package com.whaty.platform.entity.service.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.service.imp.GeneralServiceImp;
import com.whaty.platform.entity.util.Page;

/**
 * Service实现类 -系统后台首页提示信息管理
 * @author xuwenxue
 */
//754270587
@SuppressWarnings("unchecked")
public class IndexInfoServiceImpl extends GeneralServiceImp implements IndexInfoService {

	private IndexInfoEhCacheService indexInfoEhCacheService;  
public Page  getInfoPageFromCache(String key,String sql,int limit,int start){
	
		Page  page = indexInfoEhCacheService.getFromCache(key);
		if(page==null){
			//System.out.println("nocache---"+key);
			page = this.getGeneralDao().getByPageSQL(sql, limit, start);
			indexInfoEhCacheService.putToCache(key, page);
		}
		//System.out.println("cache---"+key);
		return page;
	}
	
public List  getInfoListFromCache(String key,String sql){
	
	List list = indexInfoEhCacheService.getValueFromCache(key);
	
	if(list==null){
		//System.out.println("nocache---"+key);
		list = this.getGeneralDao().getBySQL(sql);
		indexInfoEhCacheService.putToCache(key, list);
	}
	//System.out.println("cache---"+key);
	return list;
}
	public void saveNewInfo(String entityId,Object obj) {
//		List userList = indexInfoEhCacheService.getFromCache(entityId);
//		if(userList==null){
//			userList = new ArrayList<SsoUser>();
//		}
//		userList.add(0, obj);
//		indexInfoEhCacheService.putToCache(entityId, userList);
	}
	public void clearUser() {
		// TODO Auto-generated method stub
		indexInfoEhCacheService.clear();
	}

	public IndexInfoEhCacheService getIndexInfoEhCacheService() {
		return indexInfoEhCacheService;
	}

	public void setIndexInfoEhCacheService(
			IndexInfoEhCacheService indexInfoEhCacheService) {
		this.indexInfoEhCacheService = indexInfoEhCacheService;
	}
	
	
}
