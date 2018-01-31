package com.whaty.platform.database.oracle.standard.entity.right;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.right.Office;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleOffice extends Office {

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sel_sql = "select id from entity_office_info where name = '"
				+ this.getName() + "'";
		if (db.countselect(sel_sql) > 0) {
			throw new PlatformException("Ѿڣ");
		}
		String sql = "insert into entity_office_info (id,name,is_subsite) values (to_char(s_office_info_id.nextval),'"
				+ this.getName() + "','" + this.getName() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleOffice.add() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sel_sql = "select id from entity_office_info where name = '"
				+ this.getName() + "'";
		if (db.countselect(sel_sql) > 0) {
			throw new PlatformException("Ѿڣ");
		}
		String sql = "update lrn_office_info set name ='" + this.getName()
				+ "' where id = '" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleOffice.update() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sel_sql = "select id from  entity_modelgroup_info where office_id = '"
				+ this.getId() + "'";
		if (db.countselect(sel_sql) > 0) {
			throw new PlatformException("òȨģ飬ɾɾȨģ飡");
		}
		String sql = "delete from entity_office_info where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleOffice.delete() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public List getOfficeRightModel() throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id,name,status from entity_modelgroup_info where office_id='"
				+ this.getId() + "' and status='1111' order by id";
		ArrayList rightModelList = new ArrayList();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleRightModel rightModel = new OracleRightModel();
				rightModel.setId(rs.getString("id"));
				rightModel.setName(rs.getString("name"));
				rightModel.setStatus(rs.getString("status"));
				rightModelList.add(rightModel);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return rightModelList;
	}

	public Hashtable getModelGroupHash() {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		Hashtable modegroup = new Hashtable();
		String temp_officeid = "";
		String officeid = "";
		String modelgroup_id = "";
		String modelgroup_name = "";
		boolean isFirst = true;
		if (this.getId() == null || this.getId().length() == 0
				|| this.getId().equals("null")) {
			sql = "select id,name,office_id from entity_modelgroup_info order by office_id,id";
			try {
				rs = db.executeQuery(sql);
				while (rs != null && rs.next()) {
					temp_officeid = rs.getString("office_id");
					if (isFirst) {
						isFirst = false;
						officeid = temp_officeid;
					}
					if (officeid.equals(temp_officeid)) {
						modelgroup_id = rs.getString("id");
						modelgroup_name = rs.getString("name");
						modegroup.put(modelgroup_id, modelgroup_name);
					} else {
						break;
					}
				}
			} catch (Exception e) {
				
			} finally {
				db.close(rs);
				db = null;
			}
		} else {
			sql = "select * from entity_modelgroup_info where office_id ='"
					+ this.getId() + "' order by id";
			try {
				rs = db.executeQuery(sql);
				while (rs != null && rs.next()) {
					modelgroup_id = rs.getString("id");
					modelgroup_name = rs.getString("name");
					modegroup.put(modelgroup_id, modelgroup_name);
				}
			} catch (Exception e) {
				
			} finally {
				db.close(rs);
				db = null;
			}
		}
		return modegroup;
	}

	public List getOffices() throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select * from entity_office_info order by id";
		ArrayList officeList = new ArrayList();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleOffice office = new OracleOffice();
				office.setId(rs.getString("id"));
				office.setName(rs.getString("name"));
				officeList.add(office);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return officeList;
	}

	public List getManagerOffices() throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select * from entity_office_info where type='manager' order by id";
		ArrayList officeList = new ArrayList();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleOffice office = new OracleOffice();
				office.setId(rs.getString("id"));
				office.setName(rs.getString("name"));
				officeList.add(office);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return officeList;
	}

	public List getSiteOffice() throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select * from entity_office_info where type='submanager' order by to_number(id)";
		ArrayList officeList = new ArrayList();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleOffice office = new OracleOffice();
				office.setId(rs.getString("id"));
				office.setName(rs.getString("name"));
				officeList.add(office);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return officeList;
	}

}
