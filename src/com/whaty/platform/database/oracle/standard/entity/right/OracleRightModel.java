package com.whaty.platform.database.oracle.standard.entity.right;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.right.RightModel;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;

public class OracleRightModel extends RightModel {

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sel_sql = "select id from entity_modelgroup_info where id='"
				+ this.getOffice_id() + this.getModel_id() + "'";
		if (db.countselect(sel_sql) > 0) {
			throw new PlatformException("ģIDѾڣ");
		}
		String sql = "insert into entity_modelgroup_info(id,name,office_id,status) values('"
				+ this.getOffice_id()
				+ this.getModel_id()
				+ "','"
				+ this.getName()
				+ "','"
				+ this.getOffice_id()
				+ "','"
				+ this.getStatus() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRightModel.add() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sel_sql = "select id  from entity_moduleright_info where modelgroup_id='"
				+ this.getId() + "'";
		if (db.countselect(sel_sql) > 0) {
			throw new PlatformException("ģȨޣɾɾȨޣ");
		}
		String sql = "delete from entity_modelgroup_info where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRightModel.delete() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public List getModelRights() throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id,name,status from entity_moduleright_info where status='1' and modelgroup_id='"
				+ this.getModel_id() + "' order by id";
		ArrayList rightlList = new ArrayList();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleModuleRight right = new OracleModuleRight();
				right.setId(rs.getString("id"));
				right.setName(rs.getString("name"));
				right.setStatus(rs.getString("status"));
				rightlList.add(right);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return rightlList;
	}

	public List getRightModels() throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList rightModelList = new ArrayList();
		String sql = "select * from entity_modelgroup_info order by id";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleRightModel rightmodel = new OracleRightModel();
				rightmodel.setId(rs.getString("id"));
				rightmodel.setName(rs.getString("name"));
				rightmodel.setOffice_id(rs.getString("office_id"));
				rightmodel.setStatus(rs.getString("status"));
				rightModelList.add(rightmodel);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return rightModelList;
	}

	public List getSiteModelRights() throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList rightModelList = new ArrayList();
		String sql = "select a.id,a.name,a.office_id,a.status from entity_modelgroup_info a,entity_office_info b "
				+ "where a.office_id = b.id and b.type ='submanager' order by a.id";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleRightModel rightmodel = new OracleRightModel();
				rightmodel.setId(rs.getString("id"));
				rightmodel.setName(rs.getString("name"));
				rightmodel.setOffice_id(rs.getString("office_id"));
				rightmodel.setStatus(rs.getString("status"));
				rightModelList.add(rightmodel);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return rightModelList;
	}

}
