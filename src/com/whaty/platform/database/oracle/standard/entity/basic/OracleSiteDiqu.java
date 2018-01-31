package com.whaty.platform.database.oracle.standard.entity.basic;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.basic.SiteDiqu;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleSiteDiqu extends SiteDiqu {

	public OracleSiteDiqu(String id){
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id,name,rightcode from entity_zddq_info where id = '"+id+"'";
		try{
			rs = db.executeQuery(sql);
			while(rs!=null && rs.next())
			{
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setRightCode(rs.getString("rightcode"));
			}
		}
		catch(Exception e){
			
		}
		finally{
			db.close(rs);
			db = null;
		}
		
	}
	
	public OracleSiteDiqu() {
		// TODO Auto-generated constructor stub
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "insert into entity_zddq_info (id,name,rightcode) values (S_ENTITY_ZDDQ_ID.nextval,'"
				+ this.getName() + "','" + this.getRightCode() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleSiteDiqu.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "delete from  entity_zddq_info where id = '"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleSiteDiqu.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_zddq_info set name='" + this.getName()
				+ "',rightcode='" + this.getRightCode() + "' where id = '"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSiteDiqu.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
}
