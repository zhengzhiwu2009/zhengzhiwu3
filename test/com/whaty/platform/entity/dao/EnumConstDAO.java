package com.whaty.platform.entity.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate4.HibernateCallback;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.dao.hibernate.GeneralHibernateDao;

/**
 * Data access object (DAO) for domain model class C.
 * 
 * @see com.whaty.platform.entity.bean.C
 * @author MyEclipse Persistence Tools
 */

public class EnumConstDAO extends GeneralHibernateDao<EnumConst> {
	
	public EnumConstDAO() {
		this.setEntityClass(EnumConst.class);		//当前DAO操作的bean类名
		
	}
	
	public EnumConst getDefaultByNamespace(String namespace) {
		
		EnumConst enumConst = null;
		String hql = "from EnumConst where isDefault='1' and namespace='" + namespace + "'";
		
		List list = this.getByHQL(hql);
		
		if (list.size() == 1)
			enumConst = (EnumConst)list.get(0);
		
		return enumConst;
	}
	
	public EnumConst getByNamespaceCode(String namespace,String code) {
		
		EnumConst enumConst = null;
		String hql = "from EnumConst where code='"+ code +"' and namespace='" + namespace + "'";
		
		List list = this.getByHQL(hql);
		
		if (list.size() == 1)
			enumConst = (EnumConst)list.get(0);
		
		return enumConst;
	}
	
	public static Map<String,EnumConst> enumConstMap;
	
	public Map<String,EnumConst> getAll(){
		if(enumConstMap==null){
			DetachedCriteria dc = DetachedCriteria.forClass(EnumConst.class);
			List<EnumConst> enumConstList = this.getList(dc);
			Map<String,EnumConst> eMap = new HashMap();
			for(EnumConst e:enumConstList){
				eMap.put(e.getNamespace()+"_"+e.getCode(), e);
			}
			enumConstMap = eMap; 
		}
		return enumConstMap;
	}
	
	public EnumConst getByNamespaceName(String namespace, String name) {
		EnumConst enumConst = null;
		String hql = "from EnumConst where namespace='" + namespace + "' and name='" + name + "'";
		List list = this.getByHQL(hql);
		
		if(list.size() == 1)
			enumConst = (EnumConst)list.get(0);
		
		return enumConst;
	}
	
}
