/**
 * 
 */
package com.whaty.platform.database.oracle.standard.training.user;

import java.sql.SQLException;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.training.user.TrainingStudent;
import com.whaty.platform.util.log.TrainingLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author chenjian
 * 
 */
public class OracleTrainingStudent extends TrainingStudent {
	
	public OracleTrainingStudent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleTrainingStudent(String studentId) {
		super();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		try {
			String sql = "select id,name,nick_name,email,mobilephone,phone, status, note, reg_date from training_student where id='"
					+ studentId + "'";
			TrainingLog.setDebug(sql);
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setNickName(rs.getString("nick_name"));
				this.setEmail(rs.getString("email"));
				this.setMobilePhone(rs.getString("mobilephone"));
				this.setPhone(rs.getString("phone"));
				this.setNote(rs.getString("note"));
				this.setRegDate(rs.getString("reg_date"));
				this.setActive(rs.getString("status").equals("1") ? true
						: false);
			}
		} catch (SQLException e) {

		} finally {
			db.close(rs);
			db = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#add()
	 */
	public int add() throws PlatformException {
		dbpool db = new dbpool();
		int status = this.getIsActive() ? 1 : 0;
		String sql = "insert into training_student(id,name,nick_name, email, mobilephone, status, note) values("
				+ "'"
				+ this.getId()
				+ "','"
				+ this.getName()
				+ "'"
				+ ",'"
				+ this.getNickName()
				+ "','"
				+ this.getEmail()
				+ "','"
				+ this.getMobilePhone()
				+ "','"
				+ status
				+ "','"
				+ this.getNote() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleTrainingStudent.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#update()
	 */
	public int update() throws PlatformException {
		String status = getIsActive() ? "1" : "0";
		dbpool db = new dbpool();
		String sql = "update training_student set name='" + this.getName()
				+ "'," + "nick_name='" + this.getNickName() + "', email='"
				+ this.getEmail() + "'" + ", mobilephone='"
				+ this.getMobilePhone() + "', status='" + status + "', note='"
				+ this.getNote() + "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleTrainingStudent.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#delete()
	 */
	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "delete from  training_student where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleTrainingStudent.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
