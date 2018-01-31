package com.whaty.platform.entity.service.imp.scorm;

import java.util.List;

import net.sf.ehcache.Cache;

import com.whaty.platform.database.oracle.standard.standard.scorm.operation.OracleScormCourse;
import com.whaty.platform.entity.service.cache.scorm.FailedScormEhCacheService;
import com.whaty.platform.entity.service.cache.scorm.FailedScormService;
import com.whaty.platform.standard.scorm.Exception.ScormException;
import com.whaty.platform.standard.scorm.operation.ScormItem;
import com.whaty.platform.standard.scorm.datamodels.SCODataManager;

/**
 * Service实现类 - scorm管理
 * @author xuwenxue
 */

@SuppressWarnings("unchecked")
public class FailedScormServiceImpl implements FailedScormService {

	private FailedScormEhCacheService failedScormEhCacheService;  
	
	public FailedScormEhCacheService getFailedScormEhCacheService() {
		return failedScormEhCacheService;
	}

	public void setFailedScormEhCacheService(
			FailedScormEhCacheService failedScormEhCacheService) {
		this.failedScormEhCacheService = failedScormEhCacheService;
	}

	public SCODataManager getScormListFromCache(String courseId){
		SCODataManager scormItem = failedScormEhCacheService.getFromCache(courseId);
		if(scormItem==null){

			try {
				//OracleScormCourse course = new OracleScormCourse(courseId);
				//scormItemList = course.getItems();
			} catch (Exception e) {
				e.printStackTrace();
			}
			failedScormEhCacheService.putToCache(courseId, scormItem);
		}
	
		return scormItem;
	}

	@Override
	public void clearScorm() {
		// TODO Auto-generated method stub
		failedScormEhCacheService.clear();
	}

	@Override
	public void saveFailedScorm(String key,Object value ) {
		// TODO Auto-generated method stub
		failedScormEhCacheService.putToCache(key, value);
	}

	@Override
	public Cache getCache() {
		// TODO Auto-generated method stub
		return failedScormEhCacheService.getCache();
	}

	@Override
	public void removeScorm(String key) {
		// TODO Auto-generated method stub
		failedScormEhCacheService.removeScorm(key);
	}
}
