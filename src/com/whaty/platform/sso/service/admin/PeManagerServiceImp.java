package com.whaty.platform.sso.service.admin;

import java.util.List;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.service.imp.GeneralServiceImp;
import com.whaty.platform.entity.util.MyUtil;
import com.whaty.platform.util.Const;

public class PeManagerServiceImp extends GeneralServiceImp {

	/**
	 * 通过id批量进行删除，并返回删除成功的个数
	 * @param ids
	 * @return int数字
	 * @author linjie 
	 */
	@Override
	public int deleteByIds(List ids) throws EntityException {
		int i = 0;
		try{
			for (Object object : ids) {
				String id = (String)object;
				PeManager instance = (PeManager)this.getGeneralDao().getById(id);
				this.getGeneralDao().delete(instance);
				i++;
			}
		}catch(RuntimeException e){
			throw new EntityException(e);
		}
		return i;
	}

	/**
	 * 通过实例获得总管理员信息
	 * @param transientInstance
	 * @return PeManager
	 * @author linjie 
	 */
	@Override
	public PeManager save(AbstractBean transientInstance) throws EntityException {
		PeManager managerInstance = null;
		PeManager instance = (PeManager)transientInstance;
		if(instance.getSsoUser() != null){
			instance.getSsoUser().setPePriRole(instance.getPePriRole());
			instance.getSsoUser().setLoginId(instance.getLoginId());
			instance.setName(instance.getLoginId()+"/"+instance.getTrueName());
			try{
				managerInstance = (PeManager)this.getGeneralDao().save(instance);
			}catch(RuntimeException e){
				throw new EntityException(e);
			}
			return managerInstance;
		}
		try{
			SsoUser ssoUser = new SsoUser();
			ssoUser.setLoginId(instance.getLoginId());
			if(ssoUser.getPassword() == null)
				ssoUser.setPasswordBk(MyUtil.md5(Const.defaultPwd));
				//ssoUser.setPassword("1111");
			ssoUser.setPePriRole(instance.getPePriRole());
			SsoUser ssoUserInstance = (SsoUser)this.getGeneralDao().save(ssoUser);
			instance.setName(instance.getLoginId()+"/"+instance.getTrueName());
			instance.setSsoUser(ssoUserInstance);
			managerInstance = (PeManager)this.getGeneralDao().save(instance);
		}catch(RuntimeException e){
			throw new EntityException(e);
		}
		return managerInstance;
	}
	
	

}
