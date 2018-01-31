package com.whaty.platform.database.oracle.standard.test.paper;

import java.sql.SQLException;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.test.paper.PaperPolicy;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OraclePaperPolicy extends PaperPolicy {
	public OraclePaperPolicy() {

	}

	public OraclePaperPolicy(String id) {
		String sql = "select id,title,policycore,creatuser,creatdate,status,note,type,testtime,group_id from ("
				+ " select id,title,policycore,creatuser,to_char(creatdate,'yyyy-mm-dd') as creatdate,status,note,type,testtime,group_id from test_paperpolicy_info where id='"
				+ id + "')";
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				this.setPolicyCore(rs.getString("policycore"));
				this.setCreatDate(rs.getString("creatdate"));
				this.setCreatUser(rs.getString("creatuser"));
				this.setStatus(rs.getString("status"));
				this.setNote(rs.getString("note"));
				this.setType(rs.getString("type"));
				this.setTestTime(rs.getString("testtime"));
				this.setGroupId(rs.getString("group_id"));
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
	}

	public int add() throws PlatformException {
		//todo:
		dbpool db = new dbpool();
		String id = "";
		String sql_pre = "select to_char(s_test_paperpolicy_info.nextval) as id from dual";
		MyResultSet rs = db.executeQuery(sql_pre);
		try {
			if (rs != null && rs.next())
				id = rs.getString("id");
		} catch (SQLException e) {
			throw new PlatformException();
		}
		db.close(rs);
		String sql = "insert into test_paperpolicy_info (id,title,policycore,creatuser,creatdate,status,note,type,testtime,group_id) "
				+ "values ('"
				+ id
				+ "','"
				+ this.getTitle()
				+ "',?,'"
				+ this.getCreatUser()
				+ "',sysdate,'"
				+ this.getStatus()
				+ "','"
				+ this.getNote()
				+ "','"
				+ this.getType()
				+ "','"
				+ this.getTestTime()
				+ "','"
				+ this.getGroupId() + "')";
		
		int i = db.executeUpdate(sql, this.getPolicyCore());
		UserAddLog.setDebug("OraclePaperPolicy.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		if(i==0) {
			throw new PlatformException();
		} else {
			i = Integer.parseInt(id);
		}
		return i;
	}

	public int update() throws PlatformException {
		String sql = "update test_paperpolicy_info set title='"
				+ this.getTitle() + "', creatuser='" + this.getCreatUser()
				+ "',status='" + this.getStatus() + "', note='"
				+ this.getNote() + "', type='" + this.getType()
				+ "', testtime='" + this.getTestTime() + "',group_id='"
				+ this.getGroupId() + "',policycore=? where id='"
				+ this.getId() + "'";

		dbpool db = new dbpool();
		int i = db.executeUpdate(sql, this.getPolicyCore());
		UserUpdateLog.setDebug("OraclePaperPolicy.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		String sql = "delete from test_paperpolicy_info where id='"
				+ this.getId() + "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OraclePaperPolicy.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public String getIdByTitle(String homeworkId) throws PlatformException {
		String sql = "select t.id from test_paperpolicy_info t where t.title = '" + homeworkId +"'";
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				return rs.getString("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
		}
		return "";
	}
}
