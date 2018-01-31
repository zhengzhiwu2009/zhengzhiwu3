package com.whaty.platform.sso.service.admin;

import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;

public interface PrPriRangeRightService extends GeneralService {
	
	/**
	 * 根据用户信息修改权限管理范围
	 * @param major
	 * @param grade
	 * @param edutype
	 * @param site
	 * @param ssoUser
	 * @author houguolong 
	 * @throws EntityException
	 */
	public boolean updateRangeRight(String[] major, String[] grade, String[] edutype, String[] site, SsoUser ssoUser) throws EntityException;

}
