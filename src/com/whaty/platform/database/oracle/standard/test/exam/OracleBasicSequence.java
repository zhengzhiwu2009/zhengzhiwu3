package com.whaty.platform.database.oracle.standard.test.exam;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.test.exam.BasicSequence;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleBasicSequence extends BasicSequence {
	
	public OracleBasicSequence() {
		super();
	}

	public OracleBasicSequence(String aid) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		sql = "select id,name as title,code as note from PE_EXAMNO where id='" + aid
				+ "'";
		Log.setDebug("OracleBasicSequence@Method:OracleBasicSequence()="+sql);
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setTitle(rs.getString("title"));
				this.setNote(rs.getString("note"));
				this.setId(aid);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleBasicSequence(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into PE_EXAMNO (id,name,code) values ('"
				+ this.getId() + "','" + this.getTitle() + "','"
				+ this.getNote() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleBasicSequence.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update PE_EXAMNO set name='" + this.getTitle()
				+ "'," + "code='" + this.getNote() + "' where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleBasicSequence.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		
		String sql1  = "select t.id from PE_EXAMNO t where t.id='"+this.getId()+"'";
		int ii = db.countselect(sql1);
		if(ii>0){
			throw new PlatformException("��ǰ�������Ѱ��ſγ�");
		}
		
		String sql = "delete from PE_EXAMNO where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleBasicSequence.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
