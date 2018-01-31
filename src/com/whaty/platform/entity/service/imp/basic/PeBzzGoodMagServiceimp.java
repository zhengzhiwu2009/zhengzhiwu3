package com.whaty.platform.entity.service.imp.basic;

import java.util.List;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzGoodMag;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.service.basic.PeBzzGoodMagService;

public class PeBzzGoodMagServiceimp implements PeBzzGoodMagService {
	
	private GeneralDao<PeBzzGoodMag> generalDao;
	
	private GeneralDao<PeEnterpriseManager> generalDao2;
	
	
	public PeBzzGoodMag Save(PeBzzGoodMag goodMag) {
		return generalDao.save(goodMag);
	}
	
	public boolean del(PeBzzGoodMag goodMag) {
		boolean flag = false;
		try {
			generalDao.delete(goodMag);
			flag = true;
		} catch (RuntimeException e) {
			flag = false;
		}
		return flag ;
	}
	

	public void updateFlaggoodMag(PeEnterpriseManager peEnterpriseManager,
			EnumConst enumConst) {
		
		peEnterpriseManager.setEnumConstByFlaggoodManag(enumConst);
		generalDao2.update(peEnterpriseManager);
		
	}

	public int update(List ids, String propety, String value) {
		return generalDao.updateColumnByIds(ids, propety, value);
	}

	public GeneralDao<PeBzzGoodMag> getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao<PeBzzGoodMag> generalDao) {
		this.generalDao = generalDao;
	}

	public GeneralDao<PeEnterpriseManager> getGeneralDao2() {
		return generalDao2;
	}

	public void setGeneralDao2(GeneralDao<PeEnterpriseManager> generalDao2) {
		this.generalDao2 = generalDao2;
	}

}
