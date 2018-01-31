package com.whaty.util;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.Oracle10gDialect;

public class MyDialect extends Oracle10gDialect{
	public MyDialect() {  
		super();  
//		registerHibernateType(Types.DECIMAL, Hibernate.BIG_DECIMAL.getName());  
//		registerHibernateType(-1, Hibernate.STRING.getName());  
//		registerHibernateType(Types.CHAR, Hibernate.STRING.getName()); 
	}
}
