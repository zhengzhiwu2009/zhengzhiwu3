package com.whaty.platform.database.oracle.standard.entity.recruit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleSite;
import com.whaty.platform.entity.basic.Site;
import com.whaty.platform.entity.recruit.RecruitTestBatch;
import com.whaty.platform.entity.recruit.RecruitTestSite;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleRecruitTestSite extends RecruitTestSite {
	/** Creates a new instance of OracleRecruitTestCourse */
	public OracleRecruitTestSite() {
	}
	
	public OracleRecruitTestSite(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		MyResultSet rs_sub = null;
		String sql = "";
		sql = "select id,name,note,site_id from recruit_test_site where id = '"
				+ aid + "')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setNote(rs.getString("note"));
				OracleSite site = new OracleSite();
				site.setId(rs.getString("site_id"));
				this.setSite(site);
				sql = "select si.id as site_id,si.name as site_name from recruit_test_subsite ts,entity_site_info si where ts.subsite_id=si.id and ts.testsite_id='"
						+ this.getId() + "'";
				rs_sub = db.executeQuery(sql);
				ArrayList subSiteList = new ArrayList();
				while (rs_sub != null && rs_sub.next()) {
					OracleSite subsite = new OracleSite();
					subsite.setId(rs_sub.getString("site_id"));
					subsite.setName(rs.getString("site_name"));
					subSiteList.add(subsite);
				}
				this.setSubSiteList(subSiteList);
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleRecruitTestSite(String aid) error" + sql);
		} finally {
			db.close(rs);
			db.close(rs_sub);
			db = null;
		}
	}

	public int getStudentsNum(RecruitTestBatch testbatch)
			throws PlatformException {
		// TODO 自动生成方法存根
		return 0;
	}

	public int getScarcityStudentsNum(RecruitTestBatch testbatch)
			throws PlatformException {
		// TODO 自动生成方法存根
		return 0;
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into recruit_test_site (id,name,note,site_id) values(to_char(s_recruit_test_site_id.nextval),'"
				+ this.getName()
				+ "','"
				+ this.getNote()
				+ "','"
				+ this.getSite().getId() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRecruitTestSite.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_test_site set name='" + this.getName()
				+ "',note='" + this.getNote() + "',site_id='"
				+ this.getSite().getId() + "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitTestSite.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		if (isHasSubSites() > 0)
			throw new PlatformException("The recruitSite has recruitSubSites");
		if (isHasTestRooms() > 0)
			throw new PlatformException("The recruitSite has recruitTestRooms");
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from recruit_test_site where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRecruitTestSite.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int isHasSubSites() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select testsite_id from recruit_test_subsite where testsite_id ='"
				+ this.getId() + "'";
		int i = db.countselect(sql);
		return i;
	}

	public int isHasTestRooms() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select testsite_id from recruit_test_room where testsite_id ='"
				+ this.getId() + "'";
		int i = db.countselect(sql);
		return i;
	}

	public int addSubSites(List subSiteList) {
		dbpool db = new dbpool();
		String sql = "";
		ArrayList sql_group = new ArrayList();
		for (int i = 0; i < subSiteList.size(); i++) {
			Site site = (Site) subSiteList.get(i);
			sql = "insert into recruit_test_subsite (id,testsite_id,subsite_id) values(to_char(s_recruit_test_subsite_id.nextval),'"
					+ this.getId() + "','" + site.getId() + "')";
			sql_group.add(sql);
			UserAddLog.setDebug("OracleRecruitTestSite.addSubSites(List subSiteList) SQL=" + sql + " DATE=" + new Date());
		}
		int i = db.executeUpdateBatch(sql_group);
		return i;
	}

	public int deleteSubSites(List subSiteList) {
		dbpool db = new dbpool();
		String sql = "";
		String con = "";
		for (int i = 0; i < subSiteList.size(); i++) {
			Site site = (Site) subSiteList.get(i);
			con = con + "'" + site.getId() + "',";
		}
		sql = "delete from recruit_test_subsite where testsite_id='"
				+ this.getId() + "' and subsite_id in("
				+ con.subSequence(0, con.length() - 1) + ")";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRecruitTestSite.deleteSubSites(List subSiteList) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
