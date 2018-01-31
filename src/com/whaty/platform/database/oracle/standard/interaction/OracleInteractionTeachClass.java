package com.whaty.platform.database.oracle.standard.interaction;

import java.util.Date;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.interaction.InteractionTeachClass;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleInteractionTeachClass extends InteractionTeachClass {

	public OracleInteractionTeachClass() {

	}

	public OracleInteractionTeachClass(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id,teachclass_id,title,content,type,status,to_char(publish_date,'yyyy-mm-dd hh24:Mi:ss') as publish_date,file_Link from interaction_teachclass_info where id = '" + id + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(id);
				this.setContent(rs.getString("content"));
				this.setStatus(rs.getString("status"));
				this.setTeachclass_id(rs.getString("teachclass_id"));
				this.setTitle(rs.getString("title"));
				this.setType(rs.getString("type"));
				this.setPublish_date(rs.getString("publish_date"));
				this.setFileLink(rs.getString("file_link"));
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() {

		dbpool db = new dbpool();
		/* 原SQL */
		// String sql = "insert into interaction_teachclass_info
		// (id,teachclass_id,title,content,type) values
		// (s_interaction_teachclass_id.nextval,'"
		// + this.getTeachclass_id()
		// + "','"
		// + this.getTitle()
		// + "',?,'"
		// + this.getType() + "')";
		/*Lee 插入添加了附件链接的操作*/
		String sql = "insert into interaction_teachclass_info (id,teachclass_id,title,content,type,file_link) values (s_interaction_teachclass_id.nextval,'" + this.getTeachclass_id() + "','"
				+ this.getTitle() + "',?,'" + this.getType() + "','" + this.getFileLink() + "')";
		int i = db.executeUpdate(sql, this.getContent());
		UserAddLog.setDebug("OracleInteractionTeachClass.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() {
		dbpool db = new dbpool();
		String sql = "delete from interaction_teachclass_info where id = '" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleInteractionTeachClass.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "update interaction_teachclass_info set title = '" + this.getTitle() + "',status = '" + this.getStatus() + "',type= '" + this.getType() + "',file_link = '" + this.getFileLink() + "',content = ? where id = '"
				+ this.getId() + "' ";
		// System.out.println(sql);
		int i = db.executeUpdate(sql, this.getContent());
		UserUpdateLog.setDebug("OracleInteractionTeachClass.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int cancelActive() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update interaction_teachclass_info set status='0' where id ='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleInteractionTeachClass.cancelActive() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int setActive() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update interaction_teachclass_info set status='1' where id ='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleInteractionTeachClass.setActive() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int reverseActive() {
		if (this.getStatus().equals("0"))
			return this.setActive();
		else
			return this.cancelActive();
	}
}
