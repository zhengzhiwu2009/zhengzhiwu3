package com.whaty.platform.database.oracle.standard.interaction.forum;

import java.sql.SQLException;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.interaction.forum.ForumList;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleForumList extends ForumList {

	public OracleForumList() {

	}

	public OracleForumList(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id, name, content, to_char(create_date,'yyyy-mm-dd') as create_date , num, course_id, isbrowse, isspeak from interaction_forumlist_info"
				+ " where id = '" + aid + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(aid);
				this.setName(rs.getString("name"));
				this.setContent(rs.getString("content"));
				this.setDate(rs.getString("create_date"));
				this.setCourseId(rs.getString("course_id"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleForumList(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		int forumId = 0;
		String sql = "";
		sql = "select to_char(s_forumlist_info_id.nextval) as id from dual";
		MyResultSet rs = db.executeQuery(sql);
		try {
			if(rs != null && rs.next())
				forumId = rs.getInt("id");
		} catch (SQLException e) {
			
		}
		db.close(rs);
		sql = "insert into interaction_forumlist_info(id, name, content,course_id) values "
				+ " ('"+forumId+"', '"
				+ this.getName()
				+ "','" + this.getContent() + "','" + this.getCourseId() + "')";
		
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleForumList.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		if(i > 0)
			i = forumId;
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update interaction_forumlist_info set " + " name = '"
				+ this.getName() + "',content='" + this.getContent()
				+ "', course_id='" + this.getCourseId() + "' where id='"+this.getId()+"'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleForumList.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from interaction_forumlist_info where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleForumList.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
