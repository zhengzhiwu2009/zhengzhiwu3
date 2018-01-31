package com.whaty.platform.database.oracle.standard.sms.basic;

import java.sql.SQLException;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.sms.basic.SmsSystemPoint;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleSmsSystemPoint extends SmsSystemPoint {
	public OracleSmsSystemPoint() {
	}

	public OracleSmsSystemPoint(String id) {
		String sql = "select id,name,content,status from sms_systempoint where id='"
				+ id + "'";
		dbpool db = new dbpool();
		MyResultSet rs = null;
		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setContent(rs.getString("content"));
				this.setStatus(rs.getString("status"));
			}
		} catch (SQLException e) {
			
		}
		db.close(rs);
	}

	public int add() throws PlatformException {
		// TODO Auto-generated method stub
		dbpool db = new dbpool();
		String sesql = "select * from sms_systempoint where id='"+this.getId()+"'";
		int c = db.countselect(sesql);
		if(c>0){
			throw new PlatformException("该编号已存在!");
		}
		
		String sql = "insert into sms_systempoint values ('"+this.getId()+"','"+this.getName()+"','"+this.getContent()+"','"+this.getStatus()+"')";
		
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSmsSystemPoint.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		String sql = "update sms_systempoint set content='" + this.getContent()
				+ "' where id='" + this.getId() + "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSmsSystemPoint.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int changeStatus() throws PlatformException {
		String sql = "update sms_systempoint set status='" + this.getStatus()
				+ "' where id='" + this.getId() + "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSmsSystemPoint.changeStatus() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

}
