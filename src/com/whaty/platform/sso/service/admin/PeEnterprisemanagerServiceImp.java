package com.whaty.platform.sso.service.admin;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PrBzzPriManagerEnterprise;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.imp.GeneralServiceImp;
import com.whaty.platform.entity.util.MyUtil;
import com.whaty.util.RandomString;

/**
 * @param
 * @version 创建时间：2009-6-29 下午04:51:56
 * @return
 * @throws PlatformException
 * 类说明
 */
public class PeEnterprisemanagerServiceImp extends GeneralServiceImp {

	/**
	 * 通过id批量进行删除，并返回删除成功的个数
	 * @param ids
	 * @return int数字
	 * @author linjie 
	 */
	@Override
	public int deleteByIds(List ids) throws EntityException {
		// TODO Auto-generated method stub
		int i = 0;
		try{
			for (Object object : ids) {
				String id = (String)object;
				PeEnterpriseManager instance = (PeEnterpriseManager)this.getGeneralDao().getById(id);
				this.getGeneralDao().delete(instance);
				i++;
			}
		}catch(RuntimeException e){
			throw new EntityException(e);
		}
		return i;
	}

	/**
	 * 通过实例获得机构管理员信息
	 * @param transientInstance
	 * @return AbstractBean
	 * @author linjie 
	 */
	@Override
	public AbstractBean save(AbstractBean transientInstance) throws EntityException {
		// TODO Auto-generated method stub
		PeEnterpriseManager managerInstance = null;
		PeEnterpriseManager instance = (PeEnterpriseManager)transientInstance;
		if(instance.getSsoUser() != null){
			instance.getSsoUser().setPePriRole(instance.getPePriRole());
			instance.getSsoUser().setLoginId(instance.getLoginId());
			instance.setName(instance.getName());
			try{
				managerInstance = (PeEnterpriseManager)this.getGeneralDao().save(instance);
			}catch(RuntimeException e){
				throw new EntityException(e);
			}
			return managerInstance;
		}
		try{
			SsoUser ssoUser = new SsoUser();
			ssoUser.setLoginNum(0L);
			ssoUser.setLoginId(instance.getLoginId());
//			String pwsd = RandomString.generatePassStr();
			if(ssoUser.getPassword() == null || ssoUser.getPasswordMd5() == null)
			{
				//ssoUser.setPassword("1111");
				//ssoUser.setPassword(pwsd);
				//新增加管理员时密码设置为其登录账号
				//ssoUser.setPassword(instance.getLoginId());
				ssoUser.setPasswordMd5(MyUtil.md5(instance.getLoginId()));
				ssoUser.setPasswordBk(MyUtil.md5(instance.getLoginId()));
				
			}
			ssoUser.setPePriRole(instance.getPePriRole());
			ssoUser.setEnumConstByFlagIsvalid(instance.getEnumConstByFlagIsvalid());
			SsoUser ssoUserInstance = (SsoUser)this.getGeneralDao().save(ssoUser);
			instance.setName(instance.getName());
			instance.setSsoUser(ssoUserInstance);
			managerInstance = (PeEnterpriseManager)this.getGeneralDao().save(instance);
			//设置企业权限默认为管理员所在企业
			if(managerInstance.getPeEnterprise() != null) {
				PrBzzPriManagerEnterprise managerSite = new PrBzzPriManagerEnterprise();
				managerSite.setPeEnterprise(managerInstance.getPeEnterprise());
				managerSite.setSsoUser(ssoUserInstance);
				managerSite = (PrBzzPriManagerEnterprise)this.getGeneralDao().save(managerSite);
			}
		}catch(RuntimeException e){
			throw new EntityException(e);
		}
		return managerInstance;
	}
		
}
