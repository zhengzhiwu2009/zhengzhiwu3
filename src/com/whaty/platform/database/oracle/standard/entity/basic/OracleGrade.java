/*
 * OracleGrade.java
 *
 * Created on 2005年5月7日, 下午10:27
 */

package com.whaty.platform.database.oracle.standard.entity.basic;

import java.util.ArrayList;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.basic.Grade;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * 
 * @author Administrator
 */
public class OracleGrade extends Grade {

	/** Creates a new instance of OracleGrade */
	public OracleGrade() {
	}

	public OracleGrade(String aid) {
		dbpool db = new dbpool();
		ArrayList gradelist = new ArrayList();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,name,to_char(begin_date,'yyyy-mm-dd') as begin_date from entity_grade_info where id = '"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setBegin_date(rs.getString("begin_date"));
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleGrade(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_grade_info (id,name,begin_date) values('"
				+ this.getId() + "','" + this.getName() + "',to_date('"
				+ this.getBegin_date() + "','yyyy-mm-dd'))";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleGrade.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		if (i > 0) {
			sql = "update entity_manager_info set grade=grade||'|"
					+ this.getId() + "'";
			int suc = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleGrade.add() SQL=" + sql + " COUNT=" + suc + " DATE=" + new Date());
		}
		return i;
	}

	public int delete() throws PlatformException {
		if (isHasStudents() > 0)
			throw new PlatformException("The grade has students");
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_grade_info where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleGrade.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_grade_info set name='" + this.getName()
				+ "',begin_date=to_date('" + this.getBegin_date()
				+ "','yyyy-mm-dd') where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleGrade.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int isHasStudents() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select grade_id from entity_student_info where grade_id ='"
				+ this.getId() + "'";
		int i = db.countselect(sql);
		return i;
	}

	public int isUniqueID() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select * from entity_grade_info where id='" + this.getId() + "'";
		int i = db.countselect(sql);
		return i;
	}
}
