/*
 * OracleManager.java
 *
 * Created on 2005��4��6��, ����8:30
 */

package com.whaty.platform.database.oracle.standard.entity.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.user.EntityUserType;
import com.whaty.platform.entity.user.HumanPlatformInfo;
import com.whaty.platform.entity.user.Manager;
import com.whaty.platform.util.RedundanceData;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * 
 * @author Administrator
 */
public class OracleManager extends Manager {


	/** Creates a new instance of OracleManager */
	public OracleManager() {
	}

	public OracleManager(java.lang.String id) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		try {

			sql = "select m.id as id,s.login_id as login_id,m.name as name,s.fk_role_id as group_id,r.name as group_name,s.last_login_date as last_login_time,s.last_login_ip as last_login_ip,s.login_num as login_num,c.code as status,m.graduation_info,m.mobile_phone from pe_manager m,sso_user s, pe_pri_role r,enum_const c where m.id=s.id and s.fk_role_id = r.id(+) and m.flag_isvalid=c.id(+)  and m.id = '"+id+"'";

			EntityLog.setDebug(sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				RedundanceData redundance = new RedundanceData();
				HumanPlatformInfo platformInfo = new HumanPlatformInfo();
				this.setType(EntityUserType.MANAGER);
				this.setId(rs.getString("id"));
				this.setLoginId(rs.getString("login_id"));
				this.setName(rs.getString("name"));
				this.setGroup_id(rs.getString("group_id"));
				this.setStatus(rs.getString("status"));
				this.setGroup_name(rs.getString("group_name"));
				this.setLogin_num(rs.getInt("login_num"));
				platformInfo.setLastlogin_ip(rs.getString("LAST_LOGIN_IP"));
				platformInfo.setLastlogin_time(rs.getString("LAST_LOGIN_TIME"));
				this.setPlatformInfo(platformInfo);
//				this.setRedundace(redundance);
//				this.setSite(rs.getString("site"));
//				this.setMajor(rs.getString("major"));
//				this.setGrade(rs.getString("grade"));
//				this.setEduType(rs.getString("edutype"));
				this.setMobilePhone(rs.getString("mobile_phone"));

			}

		} catch (java.sql.SQLException e) {
			EntityLog.setError("construct OracleManager error!");
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public List getManagerList() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		List manager = new ArrayList();
		try {

			sql = "select m.id as id,m.login_id as login_id,m.name as name,m.group_id as group_id,m.type as type,r.name as group_name, m.last_login_time as last_login_time, m.last_login_ip as last_login_ip,"
					+ "m.login_num as login_num, m.status as status,m.site,m.major,m.grade,m.edutype,m.mobilephone from entity_manager_info m,entity_rightgroup_info r where  m.group_id = r.id(+) order by login_id";

			EntityLog.setDebug(sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleManager mana = new OracleManager();
				RedundanceData redundance = new RedundanceData();
				HumanPlatformInfo platformInfo = new HumanPlatformInfo();
				mana.setType(rs.getString("type"));
				mana.setId(rs.getString("id"));
				mana.setLoginId(rs.getString("login_id"));
				mana.setName(rs.getString("name"));
				mana.setGroup_id(rs.getString("group_id"));
				mana.setStatus(rs.getString("status"));
				mana.setGroup_name(rs.getString("group_name"));
				mana.setLogin_num(rs.getInt("login_num"));
				platformInfo.setLastlogin_ip(rs.getString("LAST_LOGIN_IP"));
				platformInfo.setLastlogin_time(rs.getString("LAST_LOGIN_TIME"));
				mana.setPlatformInfo(platformInfo);
				mana.setRedundace(redundance);
				mana.setSite(rs.getString("site"));
				mana.setMajor(rs.getString("major"));
				mana.setGrade(rs.getString("grade"));
				mana.setEduType(rs.getString("edutype"));
				mana.setMobilePhone(rs.getString("mobilephone"));
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
		int i = 0;
		if (isIdExist() > 0) {
			throw new PlatformException("��ID�Ѿ����ڣ�");

		} else {
			String sql = "insert into entity_manager_info (id,login_id,name,type,status,MOBILEPHONE,re1,re2,re3,re4,re5,re6,re7,re8,re9) values ('"
					+ this.getId()
					+ "','"
					+ this.getLoginId()
					+ "','"
					+ this.getName()
					+ "','"
					+ this.getType()
					+ "','"
					+ this.getStatus()
					+ "','"
					+ this.getMobilePhone()
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
					+ this.getRedundace().getRe9() + "')";
			i = db.executeUpdate(sql);
			UserAddLog.setDebug("OracleManager.add() SQL=" + sql + " COUNT="
					+ i + " DATE=" + new Date());
		}
		return i;
	}

	public int delete() {
		dbpool db = new dbpool();
		String sql = "";
		String sqlSso = "";
		int i = 0;
		sql = "delete from entity_manager_info where login_id = '"
				+ this.getLoginId() + "'";
		i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleManager.delete() SQL=" + sql + " COUNT="
				+ i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_manager_info set name='" + this.getName()
				+ "',status='" + this.getStatus() + "',mobilephone='"
				+ this.getMobilePhone() + "',re1='"
				+ this.getRedundace().getRe1() + "',re2='"
				+ this.getRedundace().getRe2() + "',re3='"
				+ this.getRedundace().getRe3() + "',re4='"
				+ this.getRedundace().getRe4() + "',re5='"
				+ this.getRedundace().getRe5() + "',re6='"
				+ this.getRedundace().getRe6() + "',re7='"
				+ this.getRedundace().getRe7() + "',re8='"
				+ this.getRedundace().getRe8() + "',re9='"
				+ this.getRedundace().getRe9() + "' where login_id = '"
				+ this.getLoginId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleManager.update() SQL=" + sql + " COUNT="
				+ i + " DATE=" + new Date());
		return i;
	}

	public int updateMobile() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_manager_info set mobilephone='"
				+ this.getMobilePhone() + "' where login_id = '"
				+ this.getLoginId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleManager.updateMobile() SQL=" + sql
				+ " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update(String login_id, String name, String password,
			String mobile, String status) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_manager_info set name='" + name + "',status='"
				+ status + "',mobilephone='" + mobile + "' where login_id = '"
				+ login_id + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog
				.setDebug("OracleManager.update(String login_id,String name,String password,String mobile,String status) SQL="
						+ sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.whaty.platform.entity.Manager#isIdExist()
	 */
	public int isIdExist() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select login_id from entity_manager_info where login_id='"
				+ this.getLoginId() + "'";

		int i = db.countselect(sql);
		return i;
	}

	public int changeGroup() {
		dbpool db = new dbpool();
		String sql = "update entity_manager_info set group_id = '"
				+ this.getGroup_id() + "' where id = '" + this.getId() + "'";
		int i = 0;

		i = db.executeUpdate(sql);

		UserUpdateLog.setDebug("OracleManager.changeGroup() SQL=" + sql
				+ " COUNT=" + i + " DATE=" + new Date());

		sql = "delete from entity_manager_right where manager_id='"
				+ this.getId() + "'";

		db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleManager.changeGroup() SQL=" + sql
				+ " DATE=" + new Date());
		sql = "insert into entity_manager_right (id,manager_id,right_id) select to_char(s_manager_right_id.nextval),'"
				+ this.getId()
				+ "',right_id from entity_group_right where group_id='"
				+ this.getGroup_id() + "'";

		db.executeUpdate(sql);
		UserAddLog.setDebug("OracleManager.changeGroup() SQL=" + sql
				+ " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int updateRangeAdminRights(String id, String site, String grade,
			String major, String edutype) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_manager_info set site='" + site + "', grade='"
				+ grade + "', major='" + major + "' , edutype='" + edutype
				+ "' where id='" + id + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog
				.setDebug("OracleManager.updateRangeAdminRights(String id, String site, String grade,String major, String edutype) SQL="
						+ sql + " DATE=" + new Date());
		return i;
	}

	public int updateRight(List right) {
		dbpool db = new dbpool();
		String sql = "";
		int i = 0;
		sql = "delete from entity_manager_right where manager_id='"
				+ this.getId() + "'";
		db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleManager.updateRight(List right) SQL="
				+ sql + " DATE=" + new Date());
		for (Iterator it = right.iterator(); it.hasNext();) {

			sql = "insert into entity_manager_right (id,manager_id,right_id) "
					+ "values(to_char(s_manager_right_id.nextval),'"
					+ this.getId() + "','" + (String) it.next() + "')";
			i += db.executeUpdate(sql);
			UserAddLog.setDebug("OracleManager.updateRight(List right) SQL="
					+ sql + " DATE=" + new Date());

		}
		return i;
	}

}
