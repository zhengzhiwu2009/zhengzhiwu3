package com.whaty.platform.database.oracle.standard.entity.user;

import java.sql.SQLException;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.user.EntityUser;

public class OracleEntityUser extends EntityUser {
	public OracleEntityUser(String ssoId) {
		String sql = "select u.id,u.login_id,u.password,u.fk_role_id,en.id as typeId,m.name from sso_user u ,enum_const en,pe_manager m where u.login_type=en.id and m.id = u.id and u.id='"+ssoId+"'";
		
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		try {
			if(rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setLoginId(rs.getString("login_id"));
				this.setPassword(rs.getString("password"));
				this.setName(rs.getString("name"));
//				this.setEmail(rs.getString("email"));
				this.setLoginType(rs.getString("typeId"));
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
	}
	
	public OracleEntityUser(){}
}
