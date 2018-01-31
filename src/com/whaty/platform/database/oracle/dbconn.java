package com.whaty.platform.database.oracle;

import java.sql.Connection;
import java.util.Date;

public class dbconn {
	
	public dbconn(String sql,Connection conn) {
		this.sql = sql;
		this.conn = conn;
		this.date = new Date();
	}
	
	private String sql;
	private Connection conn;
	private Date date;
	
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
} 
