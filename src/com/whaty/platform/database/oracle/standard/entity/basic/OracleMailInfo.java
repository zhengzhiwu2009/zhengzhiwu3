package com.whaty.platform.database.oracle.standard.entity.basic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.basic.MailInfo;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleMailInfo extends MailInfo {

	public OracleMailInfo() {
	}

	public OracleMailInfo(String id) {
		String sql = "select id,targets,content,status,sender,to_char(time,'yyyy-mm-dd hh24:mi:ss') as time,"
				+ "scope,checker,site,note from mail_info where id='"
				+ id
				+ "'";
		EntityLog.setDebug(sql);
		dbpool db = new dbpool();
		MyResultSet rs = null;
		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				this.setMsgId(rs.getString("id"));
				this.setTargets(rs.getString("targets"));
				this.setContent(rs.getString("content"));
				this.setStatus(rs.getString("status"));
				this.setSender(rs.getString("sender"));
				this.setTime(rs.getString("time"));
				this.setScope(rs.getString("scope"));
				this.setChecker(rs.getString("checker"));
				this.setSiteId(rs.getString("site"));
				this.setNote(rs.getString("note"));
			}
		} catch (SQLException e) {
			
		}
		db.close(rs);
		db = null;
	}

	public int add() {
		String sql = "insert into mail_info(id,targets,content,sender,scope,site) values "
				+ "(to_char(s_mail_info_id.nextval), '"
				+ this.getTargets()
				+ "','"
				+ this.getContent()
				+ "','"
				+ this.getSender()
				+ "','"
				+ this.getScope() + "','" + this.getSiteId() + "')";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleMailInfo.add() SQL = " + sql+ " COUNT=" + i +" DATE:" + new Date());
		return i;
	}

	public int update() {
		String sql = "update mail_info set targets='" + this.getTargets()
				+ "',content='" + this.getContent() + "',status='"
				+ this.getStatus() + "',sender='" + this.getSender()
				+ "',time=to_date('" + this.getTime()
				+ "','yyyy-mm-dd hh24:mi:ss'),scope='" + this.getScope()
				+ "',checker='" + this.getChecker() + "' where id='"
				+ this.getMsgId() + "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleMailInfo.update() SQL = " + sql+ " COUNT=" + i +" DATE:" + new Date());
		return i;
	}

	public int delete() {
		String sql = "delete from mail_info where id='" + this.getMsgId() + "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleMailInfo.delete() SQL = " + sql+ " COUNT=" + i +" DATE:" + new Date());
		return i;
	}

	public int checkMailMessages(String checker, List smsMessages) {
		String ids = "";
		dbpool db = new dbpool();
		for (int i = 0; i < smsMessages.size(); i++) {
			ids = ids + (String) smsMessages.get(i) + ",";
		}
		if (ids.length() > 0)
			ids = ids.substring(0, ids.length() - 1); // ȥһ

		String sql = "update mail_info set status='1', checker='" + checker
				+ "' where id in (" + ids + ")";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleMailInfo.checkMailMessages() SQL = " + sql+ " COUNT=" + i +" DATE:" + new Date());
		return i;
	}

	public int deleteMailMessages(List smsMessages) {
		String ids = "";
		dbpool db = new dbpool();
		for (int i = 0; i < smsMessages.size(); i++) {
			ids = ids + (String) smsMessages.get(i) + ",";
		}
		if (ids.length() > 0)
			ids = ids.substring(0, ids.length() - 1); // ȥһ

		String sql = "delete from mail_info where id in (" + ids + ")";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleMailInfo.deleteMailMessages(List smsMessages)() SQL = " + sql+ " COUNT=" + i +" DATE:" + new Date());
		return i;
	}

	public List searchMailMessages(Page page, List searchproperty,
			List orderproperty) {
		List smsMessagesList = new ArrayList();
		String SQL = "select id,targets,content,status,sender,time,scope,checker,site,note from "
				+ "(select id,targets,content,status,sender,to_char(time,'yyyy-mm-dd hh24:mi:ss') as time,"
				+ "scope,checker,site,note from mail_info) ";
		String sql = SQL
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		EntityLog.setDebug(sql);
		dbpool db = new dbpool();
		MyResultSet rs;
		if (page != null) {
			int pageInt = page.getPageInt();
			int pageSize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
		} else {
			rs = db.executeQuery(sql);
		}

		try {
			while (rs != null && rs.next()) {
				OracleMailInfo sms = new OracleMailInfo();
				sms.setMsgId(rs.getString("id"));
				sms.setTargets(rs.getString("targets"));
				sms.setContent(rs.getString("content"));
				sms.setStatus(rs.getString("status"));
				sms.setSender(rs.getString("sender"));
				sms.setTime(rs.getString("time"));
				sms.setScope(rs.getString("scope"));
				sms.setChecker(rs.getString("checker"));
				sms.setSiteId(rs.getString("site"));
				sms.setNote(rs.getString("note"));
				smsMessagesList.add(sms);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return smsMessagesList;
	}

	public int addMailMessageSendStatus(String msgId, String email,
			String sendStatus) throws PlatformException {
		String sql = "insert into mail_send_status (msgid,mobile,status) values ('"
				+ msgId + "','" + email + "','" + sendStatus + "')";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleMailInfo.addMailMessageSendStatus(String msgId,String email,String sendStatus) SQL = " + sql+ " COUNT=" + i +" DATE:" + new Date());
		return i;
	}

	public int rejectMailMessages(String checker, String[] msgIds,
			String[] notes) throws PlatformException {
		dbpool db = new dbpool();
		ArrayList sqlList = new ArrayList();
		for (int i = 0; i < msgIds.length; i++) {
			String sql = "update mail_info set status='-1'," + "checker='"
					+ checker + "',note='" + notes[i] + "' where id ='"
					+ msgIds[i] + "'";
			EntityLog.setDebug("OracleMailInfo@Method:rejectMailMessages()="
					+ sql);
			sqlList.add(sql);
			UserAddLog.setDebug("OracleMailInfo.rejectMailMessages(String checker,String[] msgIds,String[] notes) SQL = " + sql+ " DATE:" + new Date());
		}
		int i = db.executeUpdateBatch(sqlList);
		return i;
	}

	public int updateMailMessageSendStatus(String id, String email,
			String sendStatus) throws PlatformException {
		String sqlCheck = "select send_status from mail_send_status where msgid='"
				+ id + "' and email='" + email + "'";
		String sql = "";
		dbpool db = new dbpool();

		int cnt = db.countselect(sqlCheck);
		if (cnt > 0) {
			sql = "update mail_send_status set send_status='" + sendStatus
					+ "' where msgid='" + id + "' and email='" + email + "'";
		} else {
			sql = "insert into mail_send_status (msgid,email,status) values ('"
					+ id + "','" + email + "','" + sendStatus + "')";
		}
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleMailInfo.updateMailMessageSendStatus(String id, String email, String sendStatus) SQL = " + sql+ " COUNT=" + i +" DATE:" + new Date());
		return i;
	}

	public int searchMailMessagesNum(List searchproperty) {
		String SQL = "select id,targets,content,status,sender,time,scope,checker,site,note from "
				+ "(select id,targets,content,status,sender,to_char(time,'yyyy-mm-dd hh24:mi:ss') as time,"
				+ "scope,checker,site,note from mail_info) ";
		String sql = SQL + Conditions.convertToCondition(searchproperty, null);
		dbpool db = new dbpool();
		return db.countselect(sql);
	}

	public int deleteMailMessage(List smsMessages) throws PlatformException {
		String ids = "";
		dbpool db = new dbpool();
		for (int i = 0; i < smsMessages.size(); i++) {
			ids = ids + (String) smsMessages.get(i) + ",";
		}
		if (ids.length() > 0)
			ids = ids.substring(0, ids.length() - 1); // ȥһ

		String sql = "delete from mail_info where id in (" + ids + ")";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleMailMessageSendStatus.deleteMailMessage(List smsMessages) SQL = " + sql+ " COUNT=" + i +" DATE:" + new Date());
		return i;
	}

	public List getMailMessageNumBySite(Page page, String site_id,
			String start_time, String end_time) {

		String timecon = "";
		String sitecon = "";
		if (start_time != null && !start_time.equals("")) {
			timecon += " and to_char(time,'yyyy-mm-dd')>= " + start_time;
		}
		if (end_time != null && !end_time.equals("")) {
			timecon += " and to_char(time,'yyyy-mm-dd')<= " + end_time;
		}
		if (site_id != null && !site_id.equals(""))
			sitecon = " and a.site='" + site_id + "'";
		String sql = "select site_id,sitename,totalnum,sendnum,waitnum,rejectnum from"
				+ "(select a.site as site_id,totalnum,nvl(sendnum,0) as sendnum,nvl(waitnum,0) as waitnum,nvl(rejectnum,0) as rejectnum,nvl(e.name,'վ') as sitename from"
				+ "(select count(id) as totalnum,site from mail_info where id<>'0'  "
				+ timecon
				+ "  group by site) a,"
				+ "(select count(id) as sendnum,site from mail_info where status = '1' "
				+ timecon
				+ "  group by site)b,"
				+ "(select count(id) as waitnum,site from mail_info where status = '0' "
				+ timecon
				+ "  group by site)c,"
				+ "(select count(id) as rejectnum,site from mail_info where status = '-1' "
				+ timecon
				+ "  group by site)d,entity_site_info e "
				+ "where a.site = b.site(+) and a.site=c.site(+) and a.site=d.site(+) and a.site = e.id(+) "
				+ sitecon + ") order by site_id";

		dbpool db = new dbpool();
		MyResultSet rs;
		if (page != null) {
			int pageInt = page.getPageInt();
			int pageSize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
		} else {
			rs = db.executeQuery(sql);
		}
		ArrayList smsNumList = new ArrayList();
		try {
			while (rs != null && rs.next()) {
				ArrayList numList = new ArrayList();
				numList.add(rs.getString("site_id"));
				numList.add(rs.getString("sitename"));
				numList.add(rs.getString("totalnum"));
				numList.add(rs.getString("sendnum"));
				numList.add(rs.getString("waitnum"));
				numList.add(rs.getString("rejectnum"));
				smsNumList.add(numList);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return smsNumList;
	}

}
