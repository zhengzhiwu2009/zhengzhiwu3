/**
 * 
 */
package com.whaty.platform.database.oracle.standard.logmanage;

import java.sql.SQLException;

import com.whaty.platform.logmanage.LogEntity;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.MyResultSet;
/**
 * @author wq
 *
 */
public class OracleLogEntity extends LogEntity {
	public OracleLogEntity() {
		
	}
	
	public OracleLogEntity(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		
		String sql = "select id,userid,to_char(operate_time,'hh24:mi:ss') as operate_time,behavior,status, " 
			+ "notes,logtype,priority from whatyuser_log4j where id='" + id + "'";
		
		rs = db.executeQuery(sql);
		try {
			while(rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setUserid(rs.getString("userid"));
				this.setOperate_time(rs.getString("operate_time"));
				this.setBehavior(rs.getString("behavior"));
				this.setStatus(rs.getString("status"));
				this.setNotes(rs.getString("notes"));
				this.setLogtype(rs.getString("logtype"));
				this.setPriority(rs.getString("priority"));
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
	}
}
