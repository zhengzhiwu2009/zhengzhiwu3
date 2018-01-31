package com.whaty.platform.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DbConnector {
	public DbConnector() {

	}

	public String connect(String jndi) {
		String connMessage = "连接成功";
		DataSource source = null;
		Connection conn = null;
		try {
			Context env = (Context) new InitialContext()
					.lookup("java:comp/env");
			source = (DataSource) env.lookup(jndi);
		} catch (NamingException e) {
			
		} catch (ClassCastException cce) {
			connMessage = "教务数据库jndi参数没有找到相应连接。请按照安装文档修正此错误。";
		}

		if (source == null)
			connMessage = "教务数据库jndi参数没有设对。请按照安装文档修正此错误。";
		else {
			try {
				conn = source.getConnection();
				if (conn == null) {
					connMessage = "教务数据库连接不能建立。请检查数据库连接池JNDI的名称是否正确 查看错误信息然后返回修正错误。";
				} else {
					Statement stmt = conn.createStatement();
					stmt
							.executeQuery("select * from sso_user where rownum<2");
					stmt.close();
				}
			} catch (SQLException e) {
				connMessage = "网梯教务数据库表没有安装正确。请按照安装文档修正此错误。";
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					
				}
			}
		}

		return connMessage;
	}
}
