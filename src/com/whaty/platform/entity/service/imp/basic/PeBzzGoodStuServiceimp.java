package com.whaty.platform.entity.service.imp.basic;

import java.util.List;

import com.whaty.platform.entity.bean.PeBzzGoodStu;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.service.basic.PeBzzGoodStuService;

public class PeBzzGoodStuServiceimp implements PeBzzGoodStuService {
	
	private GeneralDao<PeBzzGoodStu> generalDao;
	
	
	public PeBzzGoodStu Save(PeBzzGoodStu goodStu) {
		return generalDao.save(goodStu);
	}
	
	public boolean del(PeBzzGoodStu goodStu) {
		boolean flag = false;
		try {
			generalDao.delete(goodStu);
			flag = true;
		} catch (RuntimeException e) {
			flag = false;
		}
		return flag ;
	}

	public int update(List ids, String propety, String value) {
		return generalDao.updateColumnByIds(ids, propety, value);
	}

	public GeneralDao<PeBzzGoodStu> getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao<PeBzzGoodStu> generalDao) {
		this.generalDao = generalDao;
	}

}
