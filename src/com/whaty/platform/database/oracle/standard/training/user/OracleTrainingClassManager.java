/**
 * 
 */
package com.whaty.platform.database.oracle.standard.training.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.training.basic.OracleTrainingClass;
import com.whaty.platform.training.basic.TrainingClass;
import com.whaty.platform.training.user.TrainingClassManager;
import com.whaty.platform.util.log.TrainingLog;

/**
 * @author chenjian
 * 
 */
public class OracleTrainingClassManager extends TrainingClassManager {

	public OracleTrainingClassManager() {
		super();
	}

	public OracleTrainingClassManager(String classManagerId) {
		super();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		try {
			String sql = "select id,name,nick_name,email,mobilephone,phone, note from training_chief where id='"
					+ classManagerId + "'";
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setNickName(rs.getString("nick_name"));
				this.setEmail(rs.getString("email"));
				this.setMobilePhone(rs.getString("mobilephone"));
				this.setPhone(rs.getString("phone"));
				this.setNote(rs.getString("note"));
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
	 * @see com.whaty.platform.training.user.TrainingClassManager#getTrainingClassList()
	 */
	public List getTrainingClassList() throws PlatformException {
		String sql = "select b.id,b.name from training_class_chief a,training_class b "
				+ "where a.class_id=b.id and a.chief_id='" + this.getId() + "'";
		dbpool db = new dbpool();
		MyResultSet rs = null;
		rs = db.executeQuery(sql);
		List trainingClasses = new ArrayList();
		try {
			while (rs != null && rs.next()) {
				TrainingClass theClass = new OracleTrainingClass();
				theClass.setId(rs.getString("id"));
				theClass.setName(rs.getString("name"));
				trainingClasses.add(theClass);
			}
		} catch (SQLException e) {
			throw new PlatformException("getTrainingClasses error!");
		} finally {
			db.close(rs);
			db = null;
			return trainingClasses;
		}
	}
	
	public int update() {
		String sql = "update training_chief set name='"
			+ this.getName() + "', nick_name='" + this.getNickName() + "', email='" 
			+ this.getEmail() + "', note='" + this.getNote() + "', mobilephone='" + this.getMobilePhone() 
			+ "' where id='" + this.getId() + "'";
		dbpool db = new dbpool();
		TrainingLog.setDebug("OracleTrainingClassManager@Method:update()="+sql);
		return db.executeUpdate(sql);
	}

}
