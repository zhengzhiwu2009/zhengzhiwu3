package com.whaty.platform.database.oracle.standard.entity.right;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.user.OracleManager;
import com.whaty.platform.entity.right.RightGroup;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleRightGroup extends RightGroup {

	public OracleRightGroup() {

	}

	public int add() throws PlatformException {

		dbpool db = new dbpool();
		String sel_sql = "select id from entity_rightgroup_info where name = '"
				+ this.getName() + "'";
		if (db.countselect(sel_sql) > 0) {
			throw new PlatformException("������Ѿ����ڣ�");
		}
		// String sql = "insert into entity_rightgroup_info (id,name,is_subsite)
		// values (to_char(s_rightgroup_info_id.nextval),'" +this.getName()+
		// "','0')";
		String sql = "insert into entity_rightgroup_info (id,name,is_subsite) values (to_char(s_rightgroup_info_id.nextval),'"
				+ this.getName() + "','0')";
		// System.out.println(sql);
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRightGroup.add() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_rightgroup_info set name ='"
				+ this.getName() + "' where id = '" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRightGroup.update() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return db.executeUpdate(sql);
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		int i = 0;
		String sql1 = "";
		String sql2 = "";
		String sel_sql = "select id from entity_manager_info where group_id='"
				+ this.getId() + "'";
		// System.out.println(sel_sql);
		if (db.countselect(sel_sql) > 0) {
			throw new PlatformException("��Ȩ�������г�Ա������ɾ��");
		} else {
			sql1 = "delete from entity_rightgroup_info where id='"
					+ this.getId() + "'";
			sql2 = "delete from entity_group_right where group_id='"
					+ this.getId() + "'";
			db.executeUpdate(sql2);
			UserDeleteLog.setDebug("OracleRightGroup.delete() SQL=" + sql1 + " DATE=" + new Date());
			i = db.executeUpdate(sql1);
			UserDeleteLog.setDebug("OracleRightGroup.delete() SQL=" + sql2 + "COUNT=" + i + " DATE=" + new Date());
		}

		return i;
	}

	public int changeGroupRights(String office_id, String[] checkgroup) {
		dbpool db = new dbpool();
		int insert_succeeded = 0;
		String sql = "delete entity_group_right where group_id = '"
				+ this.getId()
				+ "' and right_id in "
				+ "(select r.id from entity_moduleright_info r,entity_modelgroup_info m where r.modelgroup_id=m.id and m.id='"
				+ office_id + "')";
		db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRightGroup.changeGroupRights(String office_id,String[] checkgroup) SQL=" + sql + " DATE=" + new Date());
		for (int i = 0; checkgroup != null && i < checkgroup.length; i++) {
			String sqlins = "insert into entity_group_right(id,group_id,right_id) values(to_char(s_groupright_id.nextval), '"
					+ this.getId() + "', '" + checkgroup[i] + "')";
			int suc = db.executeUpdate(sqlins);
			if (suc == 1) {
				insert_succeeded++;
			}
			UserAddLog.setDebug("OracleRightGroup.changeGroupRights(String office_id,String[] checkgroup) SQL=" + sqlins + "COUNT=" + suc + " DATE=" + new Date());
		}
		return insert_succeeded;
	}

	public Hashtable getRightsHash() {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select r.id as id,r.name as name, r.modelgroup as modelgroup,g.id as flag from entity_right_info r, "
				+ "(select id,right_id from entity_group_right where group_id='"
				+ this.getId() + "') g where r.id = g.right_id(+) order by id";
		Hashtable rightgroup = new Hashtable();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				String right_id = rs.getString("id");
				String right_name = rs.getString("name");
				String modelgroup_id = rs.getString("modelgroup");
				String flag = rs.getString("flag");
				String right_index = right_id;// +modelgroup_id;
				String[] right = { modelgroup_id, right_name, flag, right_id };
				rightgroup.put(right_index, right);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return rightgroup;
	}

	public int isIdExist(String group_id, String right_id) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from entity_group_right where group_id='" + group_id
				+ "' and right_id='" + right_id + "'";
		int i = db.countselect(sql);
		return i;
	}

	public int updateRangeGroupRights(String id, String site, String grade,
			String major, String edutype) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_rightgroup_info set site='" + site + "', grade='"
				+ grade + "', major='" + major + "' , edutype='" + edutype
				+ "' where id='" + id + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRightGroup.updateRangeGroupRights(String id,String site,String grade,String major,String edutype) SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public RightGroup getRightGroup(String id) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select name,office_id,is_subsite,site,major,grade,edutype from entity_rightgroup_info where id='"
				+ id + "'";
		RightGroup rightgroup = new OracleRightGroup();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {

				rightgroup.setName(rs.getString("name"));
				rightgroup.setOffice_id(rs.getString("office_id"));
				rightgroup.setIs_subsite(rs.getString("is_subsite"));
				rightgroup.setSite(rs.getString("site"));
				rightgroup.setMajor(rs.getString("major"));
				rightgroup.setGrade(rs.getString("grade"));
				rightgroup.setEduType(rs.getString("edutype"));

			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return rightgroup;
	}

	public List getRightGroup() throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList rightGroupList = new ArrayList();
		String sql = "select * from entity_rightgroup_info order by id";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleRightGroup rightgroup = new OracleRightGroup();
				rightgroup.setId(rs.getString("id"));
				rightgroup.setName(rs.getString("name"));

				rightGroupList.add(rightgroup);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return rightGroupList;
	}

	public List getRightGroupMember(String group_id) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList managerList = new ArrayList();
		String sql = "select * from entity_manager_info where group_id='"
				+ group_id + "'";

		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleManager manager = new OracleManager();
				manager.setId(rs.getString("id"));
				manager.setName(rs.getString("name"));

				managerList.add(manager);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return managerList;
	}
}
