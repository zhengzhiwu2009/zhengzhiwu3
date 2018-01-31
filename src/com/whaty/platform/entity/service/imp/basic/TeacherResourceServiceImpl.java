package com.whaty.platform.entity.service.imp.basic;

import com.whaty.platform.entity.bean.TeacherInfo;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.TeacherResourceService;

public class TeacherResourceServiceImpl implements TeacherResourceService {
	private GeneralDao generalDao;

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}
	@Override
	public TeacherInfo add(TeacherInfo teacherInfo) throws EntityException {
		teacherInfo = (TeacherInfo) this.generalDao.save(teacherInfo);
		return null;
	}

}
