package com.whaty.platform.entity.service.cache.scorm;

import java.util.List;

import com.whaty.platform.standard.scorm.datamodels.SCODataManager;
import com.whaty.platform.standard.scorm.operation.ScormItem;
import net.sf.ehcache.Cache;

public interface FailedScormService {
	
/*	public Integer getAssessTimes(String entityId);
	public Integer getAvgScore(String entityId);
	
	public void saveNewAssess(String entityId,SsoUser user);
*/	
	public SCODataManager getScormListFromCache(String courseId);
	
	public void saveFailedScorm(String key,Object value);
	
	public void clearScorm();
	
	public Cache getCache();
	
	public void removeScorm(String key);
}
