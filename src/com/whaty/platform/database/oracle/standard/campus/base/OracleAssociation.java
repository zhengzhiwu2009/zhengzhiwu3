package com.whaty.platform.database.oracle.standard.campus.base;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.base.Association;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.util.log.CampusLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleAssociation extends Association {

	public OracleAssociation() {
	}

	public OracleAssociation(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,title,status,apply_date,check_date,note,forum_id,manager_id,creater_type,manager_name from "
				+ "(select id,title,status,"
				+ "to_char(apply_date,'yyyy-mm-dd hh24:mi:ss') as apply_date,to_char(check_date,'yyyy-mm-dd hh24:mi:ss') as check_date"
				+ ",note,forum_id,manager_id,creater_type,manager_name from campus_association_info where id = '"
				+ id + "')";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				this.setStatus(rs.getString("status"));
				this.setApplyDate(rs.getString("apply_date"));
				this.setCheckDate(rs.getString("check_date"));
				this.setForumId(rs.getString("forum_id"));
				this.setNote(rs.getString("note"));
				this.setManagerId(rs.getString("manager_id"));
				this.setCreaterType(rs.getString("creater_type"));
				this.setManagerName(rs.getString("manager_name"));
				// List members = new
				// OracleAssociationMemberList().getAssociationMembers(rs.getString("id"),null,null);
				// this.setMembers(members);
			}
		} catch (Exception e) {
			CampusLog.setError("OracleAssociation(String id) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}

	}

	public int add() throws PlatformException {
		if (isNameExist() > 0) {
			throw new PlatformException("ǰ" + this.getTitle() + "Ѿڣ");
		} else {
			dbpool db = new dbpool();
			int associationId = 0;
			String sql = "";
			sql = "select to_char(s_campus_association_info_id.nextval) as id from dual";
			MyResultSet rs = db.executeQuery(sql);
			try {
				if (rs != null && rs.next())
					associationId = rs.getInt("id");
			} catch (SQLException e) {
				
			}
			db.close(rs);
			sql = "insert into campus_association_info (id,title,status,apply_date,note,manager_id,creater_type) "
					+ "values('"
					+ associationId
					+ "'"
					+ ",'"
					+ this.getTitle()
					+ "','"
					+ this.getStatus()
					+ "',sysdate,'"
					+ this.getNote()
					+ "','"
					+ this.getManagerId()
					+ "','"
					+ this.getCreaterType() + "')";
			int i = db.executeUpdate(sql);
			
			if (i > 0)
				i = associationId;
			else
				i = 0;
			
			UserAddLog.setDebug("OracleAssociation.add() SQL=" + sql + " COUNT="+ i +" DATE=" + new Date());
			return i;
		}
	}

	public int update() throws PlatformException {
		if (isNameExist() > 1) {
			throw new PlatformException("ǰ" + this.getTitle() + "Ѿڣ");
		} else {
			dbpool db = new dbpool();
			String sql = "";
			sql = "update campus_association_info " + "set title='"
					+ this.getTitle() + "',status='" + this.getStatus()
					+ "',check_date=sysdate,note='" + this.getNote()
					+ "' where id='" + this.getId() + "'";
			int i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleAssociation.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
			return i;
		}
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from campus_association_info where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleAssociation.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int confirm() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update campus_association_info set status='1',check_date=sysdate where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleAssociation.confirm() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int unConfirm() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update campus_association_info set status='2',check_date=sysdate where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleAssociation.unConfirm() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int updateForumId() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update campus_association_info set forum_id='"
				+ this.getForumId() + "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleAssociation.updateForumId() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int updateManagerName() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update campus_association_info set manager_name='"
				+ this.getManagerName() + "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleAssociation.updateManagerName() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public Association getAssociation(String title) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,title,status,apply_date,check_date,note,forum_id,manager_id,creater_type,manager_name from "
				+ "(select id,title,status,"
				+ "to_char(apply_date,'yyyy-mm-dd hh24:mi:ss') as apply_date,to_char(check_date,'yyyy-mm-dd hh24:mi:ss') as check_date"
				+ ",note,forum_id,manager_id,creater_type,manager_name from campus_association_info where title = '"
				+ title + "')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				this.setStatus(rs.getString("status"));
				this.setApplyDate(rs.getString("apply_date"));
				this.setCheckDate(rs.getString("check_date"));
				this.setNote(rs.getString("note"));
				this.setForumId(rs.getString("forum_id"));
				this.setManagerId(rs.getString("manager_id"));
				this.setCreaterType(rs.getString("creater_type"));
				this.setManagerName(rs.getString("manager_name"));
				// List members = new
				// OracleAssociationMemberList().getAssociationMembers(rs.getString("id"),null,null);
				// this.setMembers(members);
			}
		} catch (Exception e) {
			CampusLog.setError("getAssociation(String title) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return this;
	}

	/** жǷڣ0򲻴ڣ0 */
	private int isNameExist() {
		dbpool db = new dbpool();
		String sql = "select id from campus_association_info where title='"
				+ this.getTitle() + "'";
		int i = db.countselect(sql);
		return i;
	}

	private List getAssociationMembers(String associationId, String memberType) {
		List list = new ArrayList();
		try {
			list = new OracleAssociationMemberList().getAssociationMembers(
					associationId, memberType, null);
		} catch (PlatformException e) {
			CampusLog
					.setError("OracleAssociation@Method:getAssociationMembers(String associationId, String memberType)  Error!");
		}
		return list;
	}
}
