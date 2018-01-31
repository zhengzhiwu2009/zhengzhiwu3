package com.whaty.platform.database.oracle.standard.leaveword.basic;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.leaveword.basic.ReplyLeaveWord;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleReplyLeaveWord extends ReplyLeaveWord {
	
	public OracleReplyLeaveWord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleReplyLeaveWord(String id) {
		String sql = "select id,title,content,createrid,createdate,leavewordid from leaveword_reply where id = '"+id+"'";
		dbpool db = new dbpool();
		MyResultSet rs = null;
		try{
			rs = db.executeQuery(sql);
			while(rs!=null && rs.next()){
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				this.setContent(rs.getString("content"));
				this.setCreaterId(rs.getString("createrid"));
				this.setCreateDate(rs.getString("createdate"));
			}
		}
		catch(Exception e){
			
		}
		finally{
			db.close(rs);
			db = null;
		}
	}
	
	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		int i = 0;
		sql = "insert into leaveword_reply(id,title,content,createrid,createdate,leavewordid) "
				+ "values(to_char(s_leaveword_reply_id.nextval),'"
				+ this.getTitle()
				+ "','"
				+ this.getContent()
				+ "','"
				+ this.getCreaterId()
				+ "',to_date('"
				+ this.getCreateDate()
				+ "','YYYY-MM-DD HH:MI'),'" + this.getLeaveWordId() + "')";
		i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleReplyLeaveWord.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		sql = "update leaveword_info set replytimes=replytimes+1,replydate=to_date('"
				+ this.getCreateDate()
				+ "','YYYY-MM-DD HH:MI') where id='"
				+ this.getLeaveWordId() + "'";
		db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleReplyLeaveWord.add() SQL=" + sql + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update leaveword_reply set content = '"+this.getContent()+"',createrid='"+this.getCreaterId()+"',createdate=to_date('"
				+ this.getCreateDate()
				+ "','YYYY-MM-DD HH:MI') where id = '"+ this.getId()+"'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleReplyLeaveWord.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from leaveword_reply where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleReplyLeaveWord.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		sql = "select createdate from(select createdate from leaveword_reply where leavewordid='"
				+ this.getLeaveWordId()
				+ "' "
				+ "order by createdate desc) where rownum<2";
		if (db.countselect(sql) > 0) {
			sql = "update leaveword_info set replytimes=replytimes-1,"
					+ "replydate=(select createdate from(select createdate from leaveword_reply where leavewordid='"
					+ this.getLeaveWordId() + "' "
					+ "order by createdate desc) where rownum<2) where id='"
					+ this.getLeaveWordId() + "'";
			db.executeUpdate(sql);
		} else {
			sql = "update leaveword_info set replytimes=replytimes-1,"
					+ "replydate=createdate where id='" + this.getLeaveWordId()
					+ "'";
			db.executeUpdate(sql);
		}
		UserUpdateLog.setDebug("OracleReplyLeaveWord.delete() SQL=" + sql + " DATE=" + new Date());
		return i;
	}

}
