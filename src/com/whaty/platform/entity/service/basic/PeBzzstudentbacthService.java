package com.whaty.platform.entity.service.basic;

import java.io.File;
import java.util.List;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;

public interface PeBzzstudentbacthService {
	
	public int Bacthsave(File file,String id)throws EntityException;

	public PeBzzStudent save(PeBzzStudent bean) throws EntityException;

	public int deleteByIds(List idList)throws EntityException;

	public void updateSsoUser(SsoUser ssoUser);
	
	public void updateFlaggoodStu(PeBzzStudent student, EnumConst enumConst);
	
	public int deleteByIds(Class clazz,List siteIds , List ids) throws EntityException;
	
	public PeBzzStudent getById(Class entityClass,List siteIds,String id) throws EntityException;
	
	public String updatestu(String cardNo,String flag,int pageSize) ;
	
	public void check(List idList) ;
	
	public void savePiciStudent(List list);
}
