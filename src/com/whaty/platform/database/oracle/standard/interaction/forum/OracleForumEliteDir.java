package com.whaty.platform.database.oracle.standard.interaction.forum;

import java.util.Date;
import java.util.List;

import com.whaty.platform.DirTree;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.interaction.forum.ForumEliteDir;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleForumEliteDir extends ForumEliteDir {

	public OracleForumEliteDir() {

	}

	public OracleForumEliteDir(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id, name, note, create_date, course_id, father_id, forumlist_id from interaction_forum_elite_dir "
				+ " where id = '" + aid + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(aid);
				this.setName(rs.getString("name"));
				this.setNote(rs.getString("note"));
				this.setDate(rs.getString("create_date"));
				this.setCourseId(rs.getString("course_id"));
				this.setFatherId(rs.getString("fater_id"));
				this.setForumListId(rs.getString("forumlist_id"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleForumEliteDir(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into interaction_forum_elite_dir(id, name, note,  course_id, father_id, forumlist_id) values "
				+ " (to_char_(s_forum_elite_dir_id.nextval), '"
				+ this.getName()
				+ "','"
				+ this.getNote()
				+ "','"
				+ this.getCourseId()
				+ "','"
				+ this.getFatherId()
				+ "','"
				+ this.getForumListId() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleForumEliteDir.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update interaction_forum_elite_dir set " + " name = '"
				+ this.getName() + "',note='" + this.getNote()
				+ "', create_date=sysdate, course_id='" + this.getCourseId()
				+ "', father_id='" + this.getFatherId() + "', forumlist_id='"
				+ this.getForumListId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleForumEliteDir.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from interaction_forum_elite_dir where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleForumEliteDir.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public String getDirId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDirName() {
		// TODO Auto-generated method stub
		return null;
	}

	public List getChildDirs() throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	public List getChildNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	public DirTree getFatherDir() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isRootDir() {
		// TODO Auto-generated method stub
		return false;
	}

	public void moveTo(DirTree fatherdirtree) {
		// TODO Auto-generated method stub

	}

}
