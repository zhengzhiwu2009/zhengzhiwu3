package com.whaty.platform.entity.service.imp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.framework.cache.aopcache.annotation.Cacheable;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.dao.EnumConstDAO;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.util.Page;

public class EnumConstServiceImp implements EnumConstService,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EnumConstDAO enumConstDao;
	
	public EnumConstDAO getEnumConstDao() {
		return enumConstDao;
	}

	public void setEnumConstDao(EnumConstDAO enumConstDao) {
		this.enumConstDao = enumConstDao;
	}

	public int deleteByIds(List ids) {
		return enumConstDao.deleteByIds(ids);
	}

	public List getList(DetachedCriteria detachedCriteria) {
		return enumConstDao.getList(detachedCriteria);
	}

	public EnumConst save(EnumConst instance) {
		return enumConstDao.save(instance);
	}

	public int updateColumnByIds(List ids, String column, String value) {
		return enumConstDao.updateColumnByIds(ids, column, value);
	}

	public Page getByPage(DetachedCriteria detachedCriteria, int pageSize, int startIndex) {
		return enumConstDao.getByPage(detachedCriteria, pageSize, startIndex);
	}
	
	@Cacheable(extension = "[0]", tTLSeconds = 60)
	public EnumConst getById(String id) {
		return enumConstDao.getById(id);
	}
	
	@Cacheable(extension = "[0]", tTLSeconds = 60)
	public EnumConst getDefaultByNamespace(String namespace) {
		return enumConstDao.getDefaultByNamespace(namespace);
	}
	
	@Cacheable(extension = "[0]+'_'+[1]", tTLSeconds = 60)
	public EnumConst getByNamespaceCodeBak(String namespace,String code) {
		return enumConstDao.getByNamespaceCode(namespace, code);
	}
	
	//@Cacheable(extension = "[0]+'_'+[1]", tTLSeconds = 60)
	public EnumConst getByNamespaceCode(String namespace,String code) {
		return this.getAllMap("cache").get(namespace+"_"+code);
	}
	
	public Map<String,EnumConst> getAllMap(String x) {
		return enumConstDao.getAll();
	}
	
	public EnumConst getByNamespaceName(String namespace, String name) {
		return enumConstDao.getByNamespaceName(namespace, name);
	}
}
