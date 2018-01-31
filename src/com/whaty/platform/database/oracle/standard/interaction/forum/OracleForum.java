package com.whaty.platform.database.oracle.standard.interaction.forum;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.interaction.forum.Forum;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleForum extends Forum {

	public OracleForum() {

	}
	 
	public OracleForum(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id, title, body, submituser_id, to_char(publish_date,'yyyy-mm-dd hh:mi') as publish_date, course_id, click, del, reply_number, forum_list_id"
				+ ", user_name, forum_elite, read_times, user_level, user_ip, related_id from interaction_forum_info where id='"
				+ aid + "'";
		// System.out.println("sql="+sql);
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(fixNull(aid));
				this.setBody(fixNull(rs.getString("body")));
				this.setCourseId(fixNull(rs.getString("course_id")));
				this.setDate(fixNull(rs.getString("publish_date")));
				this.setForumListId(fixNull(rs.getString("forum_list_id")));
				this.setRelatedID(fixNull(rs.getString("related_id")));
				this.setSubmitUserId(fixNull(rs.getString("submitUser_id")));
				this.setTitle(fixNull(rs.getString("title")));
				this.setUserName(fixNull(rs.getString("user_name")));
				this.setUserIp(fixNull(rs.getString("user_ip")));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleForum(String aid) error: " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into interaction_forum_info (id, title, body, submituser_id,  course_id,"
				+ "click, del, reply_number, forum_list_id, user_name, forum_elite, read_times, user_level, user_ip, related_id)"
				+ " values (to_char(s_forum_info_id.nextval), '"
				+ fixNull(this.getTitle())
				+ "',?,'"
				+ fixNull(this.getSubmitUserId())
				+ "','"
				+ fixNull(this.getCourseId())
				+ "','"
				+ fixNull(this.getClick())
				+ "','"
				+ fixNull(this.getDel())
				+ "','"
				+ fixNull(this.getReplyNumber())
				+ "','"
				+ fixNull(this.getForumListId())
				+ "','"
				+ fixNull(this.getUserName())
				+ "','"
				+ fixNull(this.getForumElite())
				+ "','"
				+ fixNull(this.getReadTimes())
				+ "','"
				+ fixNull(this.getUserLevel())
				+ "','"
				+ fixNull(this.getUserIp())
				+ "','"
				+ fixNull(this.getRelatedID()) + "')";
		int i = db.executeUpdate(sql, this.getBody());
		UserAddLog.setDebug("OracleForum.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update interaction_forum_info set title ='"
				+ fixNull(this.getTitle()) + "', submituser_id='"
				+ fixNull(this.getSubmitUserId()) + "', publish_date=sysdate, course_id='"
				+ fixNull(this.getCourseId()) + "',  forum_list_id='"
				+ fixNull(this.getForumListId()) + "', user_name='"
				+ fixNull(this.getUserName()) + "', user_ip='"
				+ fixNull(this.getUserIp()) + "',related_id='" 
				+ fixNull(this.getRelatedID()) + "', body=? where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql, this.getBody());
		UserUpdateLog.setDebug("OracleForum.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from interaction_forum_info where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleForum.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int deleteTopicForum() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from interaction_forum_info where id ='" + this.getId()
				+ "' or related_id = '" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleForum.deleteTopicForum() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	private String fixNull(String s) {
		if (s == null || s.trim().equals("") || s.trim().equals("null"))
			return "";
		return s;
	}

	private int fixNull(int i) {
		return i;
	}
}
