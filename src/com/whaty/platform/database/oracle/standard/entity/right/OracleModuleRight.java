package com.whaty.platform.database.oracle.standard.entity.right;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.right.ModuleRight;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleModuleRight extends ModuleRight {

	@Override
	public int cancelActive() {
		dbpool dbsemester = new dbpool();
		String sql = "";
		sql = "update entity_moduleright_info set status='0' where id ='"
				+ this.getId() + "'";
		int i = dbsemester.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleModuleRight.cancelActive() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	@Override
	public List getModelGroups(String manager_id) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select id,name from entity_modelgroup_info where id in "
				+ "(select distinct c.modelgroup_id from entity_manager_info a,entity_group_right b,entity_moduleright_info c "
				+ "where a.id='"
				+ manager_id
				+ "' and a.group_id=b.group_id and b.right_id=c.id) and status='1111' order by id";
		MyResultSet rs = null;
		List list = new ArrayList();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleRightModel rightmodel = new OracleRightModel();
				rightmodel.setId(rs.getString("id"));
				rightmodel.setName(rs.getString("name"));
				list.add(rightmodel);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	@Override
	public List getRights(String manager_id, String modelgroup)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select b.id,b.name,nvl(a.right_id,'0') as status from (select distinct b.right_id from entity_manager_info a,entity_manager_right b where a.id='"
				+ manager_id
				+ "' and a.id=b.manager_id) a,entity_moduleright_info b where b.id=a.right_id(+) and b.modelgroup_id='"
				+ modelgroup + "' and b.status='1' order by b.id";
		MyResultSet rs = null;
		List list = new ArrayList();
		try {
			rs = db.executeQuery(sql);
			String status = "";
			while (rs != null && rs.next()) {
				OracleModuleRight right = new OracleModuleRight();
				right.setId(rs.getString("id"));
				right.setName(rs.getString("name"));
				status = rs.getString("status");
				if (status.equals("0"))
					right.setStatus("0");
				else
					right.setStatus("1");
				list.add(right);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	@Override
	public List getSiteRights(String submanager_id, String modelgroup) {
		dbpool db = new dbpool();
		String sql = "select b.id,b.name,nvl(a.right_id,'0') as status from (select distinct b.right_id from entity_sitemanager_info a,entity_manager_right b where a.id='"
				+ submanager_id
				+ "' and a.id=b.manager_id) a,entity_moduleright_info b where b.id=a.right_id(+) and b.modelgroup_id='"
				+ modelgroup + "' and b.status='1' order by b.id";
		MyResultSet rs = null;
		List list = new ArrayList();
		try {
			rs = db.executeQuery(sql);
			String status = "";
			while (rs != null && rs.next()) {
				OracleModuleRight right = new OracleModuleRight();
				right.setId(rs.getString("id"));
				right.setName(rs.getString("name"));
				status = rs.getString("status");
				if (status.equals("0"))
					right.setStatus("0");
				else
					right.setStatus("1");
				list.add(right);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	@Override
	public int setActive() {
		dbpool dbsemester = new dbpool();
		String sql = "";
		sql = "update entity_moduleright_info set status='1' where id ='"
				+ this.getId() + "'";
		int i = dbsemester.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleModuleRight.setActive() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sel_sql = "select id from entity_moduleright_info where id='"
				+ this.getId()
				+ "' and  modelgroup_id='" + this.getModelgroup_id() + "'";
		if (db.countselect(sel_sql) > 0) {
			throw new PlatformException("ģиȨIDѾڣ");
		}
		String sql = "insert into entity_moduleright_info (id,name,modelgroup_id,status) values('"
				+ this.getId()
				+ "','"
				+ this.getName()
				+ "','"
				+ this.getModelgroup_id()
				+ "','"
				+ this.getStatus() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleModuleRight.add() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "delete from entity_moduleright_info where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleModuleRight.delete() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

}
