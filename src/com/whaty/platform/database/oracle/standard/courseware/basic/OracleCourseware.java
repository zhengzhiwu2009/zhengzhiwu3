package com.whaty.platform.database.oracle.standard.courseware.basic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.courseware.basic.Courseware;
import com.whaty.platform.courseware.exception.CoursewareException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleDep;
import com.whaty.platform.entity.basic.Dep;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleCourseware extends Courseware {

	public OracleCourseware() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleCourseware(String coursewareId) throws CoursewareException {
		super();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
//		sql = "select ID,NAME,AUTHOR,PUBLISHER,NOTE,FOUNDER_ID,FOUND_DATE,COURSEWARE_TYPE,"
//				+ "LINK,ACTIVE,courseware_dir,OPENCOURSEDEPID from PE_TCH_COURSEWARE where id = '"
//				+ coursewareId + "'";
        sql = "select ID,NAME,AUTHOR,PUBLISHER,NOTE,FOUNDER_ID,FOUND_DATE,COURSEWARE_TYPE,LINK,ACTIVE,courseware_dir,OPENCOURSEDEPID,depname" +
        		" from (select ci.ID as id,ci.NAME as NAME,ci.AUTHOR as AUTHOR,ci.PUBLISHER as PUBLISHER,ci.NOTE as NOTE,ci.FOUNDER_ID as FOUNDER_ID,ci.FOUND_DATE as FOUND_DATE,ci.COURSEWARE_TYPE as COURSEWARE_TYPE, ci.LINK as LINK,ci.ACTIVE as ACTIVE,ci.courseware_dir as courseware_dir,ci.OPENCOURSEDEPID as OPENCOURSEDEPID,edi.name as depname from PE_TCH_COURSEWARE ci,entity_dep_info edi where ci.opencoursedepid = edi.id(+))" +
        		" where id = '"+coursewareId+"'";
		try {
			rs = db.executeQuery(sql);
//			CoursewareLog.setDebug(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setAuthor(rs.getString("author"));
				this.setPublisher(rs.getString("publisher"));
				this.setFounderId(rs.getString("founder_id"));
				this.setFoundDate(rs.getString("found_date"));
				this.setCoursewareType(rs.getString("courseware_type"));
				if (rs.getInt("active") == 1)
					this.setActive(true);
				else
					this.setActive(false);
				this.setCoursewareDirId(rs.getString("courseware_dir"));

				Dep dep = new OracleDep();
				dep.setId(rs.getString("OPENCOURSEDEPID"));
				dep.setName(rs.getString("depname"));
				this.setDep(dep);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleCourseware(String coursewareId) error " + sql);
			throw new CoursewareException(
					"OracleCourseware(String coursewareId) error!");
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		if (this.getCoursewareDirId() == null
				|| this.getCoursewareDirId().length() < 1)
			this.setCoursewareDirId("root");
		int active = 0;
		if (this.isActive())
			active = 1;
		String sql = "";
//		if (this.getId() == null || this.getId().length() == 0)
//			sql = "insert into PE_TCH_COURSEWARE (ID,NAME,AUTHOR,PUBLISHER,NOTE,"
//					+ "FOUNDER_ID,FOUND_DATE,"
//					+ "ACTIVE,courseware_dir) values(to_char(s_PE_TCH_COURSEWARE_id.nextval),'"
//					+ this.getName() + "','" + this.getAuthor() + "','"
//					+ this.getPublisher() + "','" + this.getNote() + "','"
//					+ this.getFounderId() + "',to_char(sysdate,'yyyy-mm-dd'),"
//					+ active + ",'" + this.getCoursewareDirId() + "')";
//		else
//			sql = "insert into PE_TCH_COURSEWARE (ID,NAME,AUTHOR,PUBLISHER,NOTE,"
//					+ "FOUNDER_ID,FOUND_DATE,"
//					+ "ACTIVE,courseware_dir) values('" + this.getId() + "','"
//					+ this.getName() + "','" + this.getAuthor() + "','"
//					+ this.getPublisher() + "','" + this.getNote() + "','"
//					+ this.getFounderId() + "',to_char(sysdate,'yyyy-mm-dd'),"
//					+ active + ",'" + this.getCoursewareDirId() + "')";
		dbpool db;
		if (this.getId() == null || this.getId().length() == 0)
			sql = "insert into PE_TCH_COURSEWARE (ID,NAME,AUTHOR,PUBLISHER,NOTE,"
					+ "FOUNDER_ID,FOUND_DATE,"
					+ "ACTIVE,courseware_dir,"
					+ "OPENCOURSEDEPID ) values(to_char(s_PE_TCH_COURSEWARE_id.nextval),'"
					+ this.getName() + "','" + this.getAuthor() + "','"
					+ this.getPublisher() + "','" + this.getNote() + "','"
					+ this.getFounderId() + "',to_char(sysdate,'yyyy-mm-dd'),"
					+ active + ",'" + this.getCoursewareDirId()  + "','" + this.getDep().getId() + "')";
		else {
			
			db = new dbpool();
			MyResultSet rs;
			try {
				rs = db.executeQuery("select id from PE_TCH_COURSEWARE where id = '" + this.getId() + "'");
				if(rs != null && rs.next())
					return -1;
				rs = db.executeQuery("select id from PE_TCH_COURSEWARE where name ='" + this.getName() + "'");
				if(rs!=null && rs.next())
					return -2;
			} catch (SQLException e) {
				// TODO �Զ���� catch ��
				
			}
			sql = "insert into PE_TCH_COURSEWARE (ID,NAME,AUTHOR,PUBLISHER,NOTE,"
				+ "FOUNDER_ID,FOUND_DATE,"
				+ "ACTIVE,courseware_dir,"
				+ "OPENCOURSEDEPID ) values('" + this.getId() + "','"
				+ this.getName() + "','" + this.getAuthor() + "','"
				+ this.getPublisher() + "','" + this.getNote() + "','"
				+ this.getFounderId() + "',to_char(sysdate,'yyyy-mm-dd'),"
				+ active + ",'" + this.getCoursewareDirId() + "','" +this.getDep().getId()+ "')";
		}
		
		
		db = new dbpool();
		int suc = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleCourseware.add() SQL=" + sql + " COUNT=" + suc +" DATE=" + new Date());
		return suc;
	}

	public int addAndToTeachClass_id(String teachclass_id)
			throws PlatformException {
		if (this.getCoursewareDirId() == null
				|| this.getCoursewareDirId().length() < 1)
			this.setCoursewareDirId("root");
		int active = 0;
		if (this.isActive())
			active = 1;
		String sql = "insert into PE_TCH_COURSEWARE (ID,NAME,AUTHOR,PUBLISHER,NOTE,"
				+ "FOUNDER_ID,FOUND_DATE,"
				+ "ACTIVE,courseware_dir) values('"
				+ this.getId()
				+ "','"
				+ this.getName()
				+ "','"
				+ this.getAuthor()
				+ "','"
				+ this.getPublisher()
				+ "','"
				+ this.getNote()
				+ "','"
				+ this.getFounderId()
				+ "',to_char(sysdate,'yyyy-mm-dd'),"
				+ active
				+ ",'"
				+ this.getCoursewareDirId() + "')";
		String insSql = "insert into PR_TCH_OPENCOURSE_COURSEWARE (id, teachclass_id, courseware_id) values("
				+ "to_char(s_info_news_id.nextval)"
				+ ",'"
				+ teachclass_id
				+ "','" + this.getId() + "')";
		ArrayList sqlList = new ArrayList();
		sqlList.add(sql);
		sqlList.add(insSql);
		dbpool db = new dbpool();
		int suc = db.executeUpdateBatch(sqlList);
		UserAddLog.setDebug("OracleCourseware.addAndToTeachClass_id(String teachclass_id) SQL1=" + sql + "SQL2=" + insSql + " COUNT=" + suc +" DATE=" + new Date());
		return suc;
	}

	public int delete() throws PlatformException {
		String sql = "delete from  PE_TCH_COURSEWARE where id='" + this.getId()
				+ "'";
		dbpool db = new dbpool();
		int suc = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleCourseware.delete() SQL=" + sql + " COUNT=" + suc + "DATE=" + new Date());
		return suc;
	}

	public int update() throws PlatformException {
		int active = 0;
		if (this.isActive())
			active = 1;
//		String sql = "update PE_TCH_COURSEWARE set NAME='" + this.getName()
//				+ "'," + "AUTHOR='" + this.getAuthor() + "'," + "PUBLISHER='"
//				+ this.getPublisher() + "'," + "NOTE='" + this.getNote() + "',"
//				+ "FOUNDER_ID='" + this.getFounderId() + "',"
//				+ "COURSEWARE_TYPE='" + this.getCoursewareType() + "',"
//				+ "courseware_dir='" + this.getCoursewareDirId() + "',"
//				+ "ACTIVE=" + active + " where id='" + this.getId() + "'";
		String sql = "update PE_TCH_COURSEWARE set NAME='" + this.getName()
		+ "'," + "AUTHOR='" + this.getAuthor() + "'," + "PUBLISHER='"
		+ this.getPublisher() + "'," + "NOTE='" + this.getNote() + "',"
		+ "FOUNDER_ID='" + this.getFounderId() + "',"
		+ "COURSEWARE_TYPE='" + this.getCoursewareType() + "',"
		+ "courseware_dir='" + this.getCoursewareDirId() + "',"
		+ "ACTIVE= " + active  + ","
		+ "OPENCOURSEDEPID = '"+ this.getDep().getId()+"' where id='" + this.getId() + "'";
		dbpool db = new dbpool();
		int suc = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleCourseware.update() SQL=" + sql + " COUNT=" + suc + "DATE=" + new Date());
		return suc;
	}
}
