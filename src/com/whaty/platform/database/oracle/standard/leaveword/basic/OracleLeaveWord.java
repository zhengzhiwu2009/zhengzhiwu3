package com.whaty.platform.database.oracle.standard.leaveword.basic;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.leaveword.basic.LeaveWord;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleLeaveWord extends LeaveWord {

	public OracleLeaveWord() {
	}
	
	public OracleLeaveWord(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = "select id,title,content,createrid,to_char(createdate,'YYYY-MM-DD HH24:MI') as createdate,ip,limittimes,replytimes,type,to_char(replydate,'YYYY-MM-DD HH24:MI') as replydate from leaveword_info where id='"
					+ aid + "'";
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(aid);
				this.setTitle(rs.getString("title"));
				this.setContent(rs.getString("content"));
				this.setCreaterId(rs.getString("createrid"));
				this.setCreateDate(rs.getString("createdate"));
				this.setIp(rs.getString("ip"));
				this.setLimitTimes(rs.getInt("limittimes"));
				this.setReplyTimes(rs.getInt("replytimes"));
				this.setType(rs.getString("type"));
				this.setReplyDate(rs.getString("replydate"));
			}
		} catch (java.sql.SQLException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		int i = 0;
		sql = "insert into leaveword_info(id,title,content,createrid,createdate,ip,replydate,type) "
				+ "values(to_char(s_leaveword_info_id.nextval),'"
				+ this.getTitle()
				+ "','"
				+ this.getContent()
				+ "','"
				+ this.getCreaterId()
				+ "',to_date('"
				+ this.getCreateDate()
				+ "','YYYY-MM-DD HH24:MI'),'"
				+ this.getIp()
				+ "',to_date('"
				+ this.getCreateDate()
				+ "','YYYY-MM-DD HH24:MI'),'"
				+ this.getType() + "')";
		i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleLeaveWord.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		return 0;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from leaveword_info where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleLeaveWord.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		sql = "delete from leaveword_reply where leavewordid='" + this.getId()
				+ "'";
		db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleLeaveWord.delete() SQL=" + sql + " DATE=" + new Date());
		return i;
	}

	public int changeLeaveWordLimitTimes() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update leaveword_info set limittimes='" + this.getLimitTimes()
				+ "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleLeaveWord.changeLeaveWordLimitTimes() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	public int changeLeaveWordMessStatus() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update leaveword_info set status='" + this.getStatus()
				+ "' where id='" + this.getId() + "'";
		Log.setDebug("OracleLeaveWord@Method:changeLeaveWordMessStatus()"+sql);
		int i = db.executeUpdate(sql);
		return i;
	}

}
