package com.whaty.platform.database.oracle.standard.entity.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.user.EntityUserType;
import com.whaty.platform.entity.user.HumanPlatformInfo;
import com.whaty.platform.entity.user.SiteManager;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.RedundanceData;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleSiteManager extends SiteManager {
	
	public OracleSiteManager() {

	}

	public OracleSiteManager(String id) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		try {
			sql = "select m.id as id,m.login_id,m.name as name,s.name as s_name,s.id as site_id,m.status,"
					+ "m.login_num,m.last_login_ip,m.group_id, m.last_login_time,m.mobilephone,re1,re2,re3,re4,re5,re6,re7,re8,re9 "
					+ " from entity_sitemanager_info m,entity_site_info s where m.id = '"
					+ id + "' and m.site_id = s.id(+)";
			EntityLog.setDebug(sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				RedundanceData redundance = new RedundanceData();
				redundance.setRe1(rs.getString("re1"));
				redundance.setRe2(rs.getString("re2"));
				redundance.setRe3(rs.getString("re3"));
				redundance.setRe4(rs.getString("re4"));
				redundance.setRe5(rs.getString("re5"));
				redundance.setRe6(rs.getString("re6"));
				redundance.setRe7(rs.getString("re7"));
				redundance.setRe8(rs.getString("re8"));
				redundance.setRe9(rs.getString("re9"));
				HumanPlatformInfo platformInfo = new HumanPlatformInfo();
				this.setType(EntityUserType.SITEMANAGER);
				this.setId(rs.getString("id"));
				this.setLoginId(rs.getString("login_id"));
				this.setName(rs.getString("name"));
				this.setSite_id(rs.getString("site_id"));
				this.setStatus(rs.getString("status"));
				this.setSite_name(rs.getString("s_name"));
				this.setLogin_num(rs.getInt("login_num"));
				this.setGroup_id(rs.getString("group_id"));
				this.setRedundace(redundance);
				platformInfo.setLastlogin_ip(rs.getString("LAST_LOGIN_IP"));
				platformInfo.setLastlogin_time(rs.getString("LAST_LOGIN_TIME"));
				this.setPlatformInfo(platformInfo);
				this.setRedundace(redundance);
				this.setMobilePhone(rs.getString("mobilephone"));
			}

		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public List getSiteManagerList(Page page, List searchProperties,
			List OrderProperties) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		List manager = new ArrayList();
		try {
			sql = "select id,login_id,group_id,name,s_name,site_id,status,login_num,last_login_ip,last_login_time,mobilephone from "
					+ "(select m.id as id,m.login_id,m.group_id,m.name as name,s.name as s_name,s.id as site_id,m.status,m.login_num,m.last_login_ip,m.last_login_time,m.mobilephone"
					+ " from entity_sitemanager_info m,entity_site_info s where  m.site_id = s.id(+)) ";
			sql += Conditions.convertToCondition(searchProperties,
					OrderProperties);
			EntityLog.setDebug(sql);
			if (page != null) {
				rs = db.execute(sql, page.getPageInt(), page.getPageSize());
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleSiteManager mana = new OracleSiteManager();
				RedundanceData redundance = new RedundanceData();
				HumanPlatformInfo platformInfo = new HumanPlatformInfo();
				mana.setType(EntityUserType.SITEMANAGER);
				mana.setId(rs.getString("id"));
				mana.setLoginId(rs.getString("login_id"));
				mana.setName(rs.getString("name"));
				mana.setSite_id(rs.getString("site_id"));
				mana.setStatus(rs.getString("status"));
				mana.setSite_name(rs.getString("s_name"));
				mana.setLogin_num(rs.getInt("login_num"));
				mana.setMobilePhone(rs.getString("mobilephone"));
				platformInfo.setLastlogin_ip(rs.getString("LAST_LOGIN_IP"));
				platformInfo.setLastlogin_time(rs.getString("LAST_LOGIN_TIME"));
				mana.setPlatformInfo(platformInfo);
				mana.setRedundace(redundance);
				mana.setGroup_id(rs.getString("group_id"));
				manager.add(mana);
			}

		} catch (java.sql.SQLException e) {
			EntityLog.setError("construct OracleManager error!");
		} finally {
			db.close(rs);
			db = null;
		}
		return manager;
	}

	public int getSiteManagerListNum(String siteId, String groupId)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select m.id as id,m.login_id,m.group_id, m.name as name,s.name as s_name,s.id as site_id,m.status,m.login_num,m.last_login_ip,m.last_login_time"
				+ " from entity_sitemanager_info m,entity_site_info s where  m.site_id = s.id(+) ";
		if (siteId != null)
			sql += " and m.site_id = '" + siteId + "' ";
		if (groupId != null)
			sql += " and m.group_id = '" + groupId + "' ";
		sql += " order by m.group_id, login_id";

		return db.countselect(sql);
	}

	public List getSiteManagerList(Page page, String siteId, String groupId)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		List manager = new ArrayList();
		try {
			sql = "select m.id as id,m.login_id,m.group_id, m.name as name,s.name as s_name,s.id as site_id,m.status,m.login_num,m.last_login_ip,m.last_login_time"
					+ " from entity_sitemanager_info m,entity_site_info s where  m.site_id = s.id(+) ";
			if (siteId != null)
				sql += " and m.site_id = '" + siteId + "' ";
			if (groupId != null)
				sql += " and m.group_id = '" + groupId + "' ";
			sql += " order by m.group_id, login_id";
			EntityLog
					.setDebug("getSiteManagerList(String siteId, String groupId)="
							+ sql);
			if (page != null)
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			else
				rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleSiteManager mana = new OracleSiteManager();
				RedundanceData redundance = new RedundanceData();
				HumanPlatformInfo platformInfo = new HumanPlatformInfo();
				mana.setType(EntityUserType.SITEMANAGER);
				mana.setId(rs.getString("id"));
				mana.setLoginId(rs.getString("login_id"));
				mana.setName(rs.getString("name"));
				mana.setSite_id(rs.getString("site_id"));
				mana.setStatus(rs.getString("status"));
				mana.setSite_name(rs.getString("s_name"));
				mana.setLogin_num(rs.getInt("login_num"));
				mana.setGroup_id(rs.getString("group_id"));
				platformInfo.setLastlogin_ip(rs.getString("LAST_LOGIN_IP"));
				platformInfo.setLastlogin_time(rs.getString("LAST_LOGIN_TIME"));
				mana.setPlatformInfo(platformInfo);
				mana.setRedundace(redundance);
				manager.add(mana);
			}

		} catch (java.sql.SQLException e) {
			EntityLog.setError("construct OracleManager error!");
		} finally {
			db.close(rs);
			db = null;
		}
		return manager;
	}

	public List getSiteManagerList(String siteId, String groupId)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		List manager = new ArrayList();
		try {
			sql = "select m.id as id,m.login_id,m.group_id, m.name as name,s.name as s_name,s.id as site_id,m.status,m.login_num,m.last_login_ip,m.last_login_time"
					+ ",m.mobilephone from entity_sitemanager_info m,entity_site_info s where  m.site_id = s.id(+) ";
			if (siteId != null)
				sql += " and m.site_id = '" + siteId + "' ";
			if (groupId != null)
				sql += " and m.group_id = '" + groupId + "' ";
			sql += " order by m.group_id, login_id";
			EntityLog
					.setDebug("getSiteManagerList(String siteId, String groupId)="
							+ sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleSiteManager mana = new OracleSiteManager();
				RedundanceData redundance = new RedundanceData();
				HumanPlatformInfo platformInfo = new HumanPlatformInfo();
				mana.setType(EntityUserType.SITEMANAGER);
				mana.setId(rs.getString("id"));
				mana.setLoginId(rs.getString("login_id"));
				mana.setName(rs.getString("name"));
				mana.setSite_id(rs.getString("site_id"));
				mana.setStatus(rs.getString("status"));
				mana.setSite_name(rs.getString("s_name"));
				mana.setLogin_num(rs.getInt("login_num"));
				mana.setGroup_id(rs.getString("group_id"));
				mana.setMobilePhone(rs.getString("mobilephone"));
				platformInfo.setLastlogin_ip(rs.getString("LAST_LOGIN_IP"));
				platformInfo.setLastlogin_time(rs.getString("LAST_LOGIN_TIME"));
				mana.setPlatformInfo(platformInfo);
				mana.setRedundace(redundance);
				manager.add(mana);
			}

		} catch (java.sql.SQLException e) {
			EntityLog.setError("construct OracleManager error!");
		} finally {
			db.close(rs);
			db = null;
		}
		return manager;
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		if (isIdExist() > 0) {
			throw new PlatformException("IDѾڣ");
		}
		String sql = "insert into entity_sitemanager_info (id,login_id,name,type,mobilephone,status,site_id, group_id,re1,re2,re3,re4,re5,re6,re7,re8,re9) values ('"
				+ this.getId()
				+ "','"
				+ this.getLoginId()
				+ "','"
				+ this.getName()
				+ "','"
				+ this.getType()
				+ "','"
				+ this.getMobilePhone()
				+ "','"
				+ this.getStatus()
				+ "','"
				+ this.getSite_id()
				+ "','"
				+ this.getGroup_id()
				+ "','"
				+ this.getRedundace().getRe1()
				+ "','"
				+ this.getRedundace().getRe2()
				+ "','"
				+ this.getRedundace().getRe3()
				+ "','"
				+ this.getRedundace().getRe4()
				+ "','"
				+ this.getRedundace().getRe5()
				+ "','"
				+ this.getRedundace().getRe6()
				+ "','"
				+ this.getRedundace().getRe7()
				+ "','"
				+ this.getRedundace().getRe8() 
				+ "','"
				+ this.getRedundace().getRe9() 
				+ "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleSiteManager.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		
		if(i > 0) {
			MyResultSet rs = db.executeQuery("select id from info_news_type where tag like '%" + this.getSite_id() + "'");
			if(rs!=null) {
				try {
					ArrayList sqlList = new ArrayList();
					while(rs.next()) {
						String id = rs.getString("id");
						String insertSql = "insert info info_user_right values(to_char(s_user_right_id.nextval),'"
							+ this.getId() + "','"
							+ id + "')"
							;
						UserAddLog.setDebug("OracleSiteManager.add() SQL=" + sql + " DATE=" + new Date());
						sqlList.add(insertSql);
					}
					db.executeUpdateBatch(sqlList);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					
				}
			}
			
			
		}
		
		
		return i;
	}

	public int update(String login_id, String name, String site_id,
			String password, String mobile, String status)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_sitemanager_info set name='" + name
				+ "',site_id='" + site_id + "',mobilephone='" + mobile
				+ "',status='" + status + "' where login_id = '" + login_id
				+ "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSiteManager.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		String sqlSso = "";

		sql = "delete from entity_sitemanager_info where login_id = '"
				+ this.getLoginId() + "'";
		// sqlSso="delete from sso_user where login_id='"+this.getLoginId()+"'";

		// ArrayList sqlList=new ArrayList();
		// sqlList.add(sql);
		// sqlList.add(sqlSso);
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleSiteManager.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int updateMobile() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_sitemanager_info set mobilephone='"
				+ this.getMobilePhone() + "' where login_id = '"
				+ this.getLoginId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSiteManager.updateMobile() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int putRight() {
		dbpool db = new dbpool();
		String sql = "insert into entity_manager_right (id,manager_id,right_id) "
				+ "select to_char(s_manager_right_id.nextval),'"
				+ this.getId()
				+ "',id from entity_right_info where id in "
				+ "(select id from entity_right_info where modelgroup in (select id from entity_modelgroup_info "
				+ "where office_id in (select id from entity_office_info where type = 'submanager')))";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleSiteManager.putRight() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	private int isIdExist() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select login_id from entity_sitemanager_info where login_id='"
				+ this.getLoginId() + "'";
		int i = db.countselect(sql);
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_sitemanager_info set name='"
				+ this.getName() + "',status='" + this.getStatus()
				+ "',mobilephone='" + this.getMobilePhone() + "',site_id = '"
				+ this.getSite_id() + "',re1='" + this.getRedundace().getRe1()
				+ "',re2='" + this.getRedundace().getRe2() + "',re3='"
				+ this.getRedundace().getRe3() + "',re4='"
				+ this.getRedundace().getRe4() + "',re5='"
				+ this.getRedundace().getRe5() + "',re6='"
				+ this.getRedundace().getRe6() + "',re7='"
				+ this.getRedundace().getRe7() + "',re8='"
				+ this.getRedundace().getRe8()  + "',re9='"
				+ this.getRedundace().getRe9() 
				+ "' where login_id = '"
				+ this.getLoginId() + "'";
		EntityLog.setDebug("OracleSiteManager@Mehtod:update()=" + sql);
		return db.executeUpdate(sql);
	}
}
