package com.whaty.platform.entity.service.cache;

import java.util.List;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.util.Page;

public interface IndexInfoService extends GeneralService {
	
	public void saveNewInfo(String key,Object obj);
	
	public Page  getInfoPageFromCache(String key,String sql,int limit,int start);
	public List  getInfoListFromCache(String key,String sql);
	
	public void clearUser();
}
