package com.whaty.platform.database.oracle.standard.campus.user;

import java.sql.SQLException;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.user.Docent;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.util.log.CampusLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleDocent extends Docent {
	public OracleDocent() {
		this.setId("");
		this.setName("");
		this.setDetail("");
		this.setPhoto_link("");
	}

	public OracleDocent(String id) {
		dbpool pool = new dbpool();
		String sql = "select id,name,link,detail from entity_docent_info where id ='"
				+ id + "'";
		MyResultSet rs = null;
		try {
			rs = pool.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setDetail(rs.getString("detail"));
				this.setPhoto_link(rs.getString("link"));
			}

		} catch (SQLException e) {
			CampusLog.setError("OracleDocent error!   SQL = " + sql);
		} finally {
			pool.close(rs);
			pool = null;
		}

	}

	public int add() throws PlatformException {
		dbpool pool = new dbpool();
		String sql = "insert into entity_docent_info (id,name,detail,link) values ('" +this.getId()
				+ "','"
				+ this.getName()
				+ "',?,'"
				+ this.getPhoto_link() + "')";
		int suc = pool.executeUpdate(sql,this.getDetail());
		UserAddLog.setDebug("OracleDocent.add() SQL="+ sql + " COUNT=" + suc + " DATE=" + new Date());
		return suc;
	}

	public int update() throws PlatformException {
		dbpool pool = new dbpool();
		String sql = "update entity_docent_info set name = '"
				+ this.getName() + "', detail = '" + this.getDetail()
				+ "' ,link = '" + this.getPhoto_link() + "' where id = '"
				+ this.getId() + "'";
		int suc = pool.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleDocent.update() SQL="+ sql + " COUNT=" + suc + " DATE=" + new Date());
		return suc;
	}

	public int delete() throws PlatformException {
		dbpool pool = new dbpool();
		String sql = "delete from entity_docent_info where id ='"
				+ this.getId() + "'";
		int suc = pool.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleDocent.delete() SQL="+ sql + " COUNT=" + suc + " DATE=" + new Date());
		return suc;
	}

	public int isExist(String docentId) {
		dbpool pool = new dbpool();
		String sql = "select * from entity_docent_info where id ='"+docentId+"'";
		return pool.countselect(sql);
	}

}
