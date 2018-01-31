/**
 * 
 */
package com.whaty.platform.database.oracle.standard.sms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.sms.basic.OracleSmsMessage;
import com.whaty.platform.database.oracle.standard.sms.basic.OracleSmsSystemPoint;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author wq
 * 
 */
public class OracleSmsList {
	private String SMSSQL = "select id,targets,content,status,sender,time,scope,checker,site,note,tea_id,type,settime from "
			+ "(select id,targets,content,status,sender,to_char(time,'yyyy-mm-dd hh24:mi:ss') as time,"
			+ "scope,checker,site,note,tea_id,type,settime from sms_info) ";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.sms.SmsList#checkSmsMessages(java.util.List)
	 */
	public int checkSmsMessages(String checker, List smsMessages) {
		String ids = "";
		dbpool db = new dbpool();
		for (int i = 0; i < smsMessages.size(); i++) {
			ids = ids + (String) smsMessages.get(i) + ",";
		}
		if (ids.length() > 0)
			ids = ids.substring(0, ids.length() - 1); // 去掉最后一个逗号

		String sql = "update sms_info set status='1',sendStatus='1', checker='" + checker
				+ "' where id in (" + ids + ")";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSmsList.checkSmsMessages(String checker, List smsMessages) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int rejectSmsMessages(String checker, String[] msgIds, String[] notes) {
		dbpool db = new dbpool();
		ArrayList sqlList = new ArrayList();
		for (int i = 0; i < msgIds.length; i++) {
			String sql = "update sms_info set status='-1'," + "checker='"
					+ checker + "',note='" + notes[i] + "' where id ='"
					+ msgIds[i] + "'";
			UserUpdateLog.setDebug("OracleSmsList.rejectSmsMessages(String checker, String[] msgIds, String[] notes) SQL=" + sql + " DATE=" + new Date());
			sqlList.add(sql);
		}

		return db.executeUpdateBatch(sqlList);
	}

	/**
	 * 由于在发送短信页面没有记录发送短信的site_id，而又根据北京石油新平台当前的特点：发送短信权限只有总站有
	 * 并不会下放到其他学习中心。根据这个情况，重新实现一个短信的统计，数据库中的短信全部统计到总站下
	 */
	public List getSmsMessageNumBySite(String start_time,String end_time){
		
		ArrayList smsNumList = new ArrayList();
		
		String timecon = "";
		String sitecon = "";
		if (start_time != null && !start_time.equals("")) {
			timecon += " and to_char(time,'yyyy-mm-dd')>= to_char('"
					+ start_time + "')";
		}
		if (end_time != null && !end_time.equals("")) {
			timecon += " and to_char(time,'yyyy-mm-dd')<= to_char('" + end_time
					+ "')";
		}
		
		String sql = " select msg.sendnum msg_sendnum, msg.totalnum msg_totalnum, msg.sendobjnum msg_sendobjnum," +
					"sysmsg.sendnum sysmsg_sendnum, sysmsg.totalnum sysmsg_totalnum, sysmsg.sendobjnum sysmsg_sendobjnum, " +
					" (msg.totalnum+sysmsg.totalnum) totalnum                        "+
					"   from (select nvl(count(m.id), 0) as sendnum,                                    "+
					"                nvl(sum(m.sendobjnum * m.splititem), 0) as totalnum,               "+
					"                nvl(sum(m.sendobjnum), 0) as sendobjnum,                           "+
					"                nvl(sum(m.splititem), 0) as splititem                              "+
					"           from sms_info m                                                         "+
					"          where m.type != '2'                                                      "
					+ timecon +				"            ) msg,         "+
					"         (select nvl(count(m.id), 0) as sendnum,                                   "+
					"                nvl(sum(m.sendobjnum * m.splititem), 0) as totalnum,               "+
					"                nvl(sum(m.sendobjnum), 0) as sendobjnum,                           "+
					"                nvl(sum(m.splititem), 0) as splititem                              "+
					"           from sms_info m                                                         "+
					"          where m.type = '2'                                                       "
					            + timecon +") sysmsg       ";
		
		dbpool db = new dbpool();
		MyResultSet rs;
		
		rs = db.executeQuery(sql);
		
		try {
			while (rs != null && rs.next()) {
				ArrayList numList = new ArrayList();
				numList.add("0");
				numList.add("总站");
				numList.add(rs.getString("msg_totalnum"));
				numList.add(rs.getString("msg_sendnum"));
				numList.add(rs.getString("msg_sendobjnum"));
				numList.add(rs.getString("sysmsg_totalnum"));
				numList.add(rs.getString("sysmsg_sendnum"));
				numList.add(rs.getString("sysmsg_sendobjnum"));
				numList.add(Integer.toString(rs.getInt("totalnum")));
				smsNumList.add(numList);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return smsNumList;
	}
	
	public List getSmsMessageNumBySite(Page page, String site_id,
			String start_time, String end_time) {
		/**
		 * 用于北京石油新平台短信统计
		 */
		if(site_id != null && "bjsy_new".equals(site_id)){
			return this.getSmsMessageNumBySite(start_time, end_time);
		}
		
		String timecon = "";
		String sitecon = "";
		if (start_time != null && !start_time.equals("")) {
			timecon += " and to_char(time,'yyyy-mm-dd')>= to_char('"
					+ start_time + "')";
		}
		if (end_time != null && !end_time.equals("")) {
			timecon += " and to_char(time,'yyyy-mm-dd')<= to_char('" + end_time
					+ "')";
		}
		if (site_id != null && !site_id.equals(""))
			sitecon = " and a.site_id='" + site_id + "'";
		String sql = "select site_id, sitename, totalnum, sendnum,sendobjnum,splititem,totalnums from (select c.id as site_id, c.name as sitename, nvl(totalnum,0) as totalnum, nvl(sendnum,0) as sendnum,nvl(sendobjnum,0) as sendobjnum,nvl(splititem,0) as splititem,nvl(totalnums, 0) as totalnums from "
				+ "(select a.site as site_id,nvl(totalnum,0) as totalnum,sendnum,nvl(sendobjnum, 0) as sendobjnum,nvl(splititem, 0) as splititem,nvl(e.name,'总站') as sitename from"
				+ "(select count(id) as sendnum,sum(sendobjnum*splititem) as totalnum,sum(sendobjnum) as sendobjnum,sum(splititem) as splititem,site from sms_info where type <> '2' "
				+ timecon
				+ "  group by site) a,"
				+ "  pe_site e "
				+ "where  a.site = e.id(+)) a,(select a.site as site_id, nvl(totalnums, 0) as totalnums"
				+ " from (select count(id) as sysnum,sum(sendobjnum*splititem) as totalnums, site from sms_info where type = '2'"
				+ timecon
				+ " group by site) a,pe_site e"
				+ " where a.site = e.id(+)) b,(select id, name from pe_site union select '0', '总站' from dual) c  where c.id = a.site_id(+) and c.id = b.site_id(+) "
				+ sitecon
				+ ") where totalnum>0 or totalnums>0 or sendnum>0 order by site_id";
		// System.out.println(sql);
		// // String sql = "select
		// site_id,sitename,totalnum,sendnum,waitnum,rejectnum from"
		// + "(select a.site as site_id,totalnum,nvl(sendnum,0) as
		// sendnum,nvl(waitnum,0) as waitnum,nvl(rejectnum,0) as
		// rejectnum,nvl(e.name,'总站') as sitename from"
		// + "(select count(id) as totalnum,site from sms_info where id<>'0' "
		// + timecon
		// + " group by site) a,"
		// + "(select count(id) as sendnum,site from sms_info where status = '1'
		// "
		// + timecon
		// + " group by site)b,"
		// + "(select count(id) as waitnum,site from sms_info where status = '0'
		// "
		// + timecon
		// + " group by site)c,"
		// + "(select count(id) as rejectnum,site from sms_info where status =
		// '-1' "
		// + timecon
		// + " group by site)d,entity_site_info e "
		// + "where a.site = b.site(+) and a.site=c.site(+) and a.site=d.site(+)
		// and a.site = e.id(+) "
		// + sitecon + ") order by site_id";
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
				numList.add(rs.getString("sendobjnum"));
				numList.add(rs.getString("splititem"));
				numList.add(rs.getString("totalnums"));
				numList.add(Integer.toString(rs.getInt("totalnum")
						+ rs.getInt("totalnums")));
				smsNumList.add(numList);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return smsNumList;
	}

	public int deleteSmsMessages(List smsMessages) {
		String ids = "";
		dbpool db = new dbpool();
		for (int i = 0; i < smsMessages.size(); i++) {
			ids = ids + (String) smsMessages.get(i) + ",";
		}
		if (ids.length() > 0)
			ids = ids.substring(0, ids.length() - 1); // 去掉最后一个逗号

		String sql = "delete from sms_info where id in (" + ids + ")";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleSmsList.deleteSmsMessages(List smsMessages) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.sms.SmsList#searchSmsMessages(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List)
	 */
	public List searchSmsMessages(Page page, List searchproperty,
			List orderproperty) {
		List smsMessagesList = new ArrayList();
		String sql = SMSSQL
				+ Conditions.convertToCondition(searchproperty, orderproperty);
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
				OracleSmsMessage sms = new OracleSmsMessage();
				sms.setMsgId(rs.getString("id"));
				sms.setType(rs.getString("type"));
				sms.setSetTime(rs.getString("settime"));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.sms.SmsList#searchSmsMessagesNum(java.util.List)
	 */
	public int searchSmsMessagesNum(List searchproperty) {
		String sql = SMSSQL
				+ Conditions.convertToCondition(searchproperty, null);
		dbpool db = new dbpool();
		return db.countselect(sql);
	}

	public int updateSmsMessageSendStatus(String id, String mobile,
			String sendStatus) {
		String sqlCheck = "select send_status from sms_send_status where msgid='"
				+ id + "' and mobile='" + mobile + "'";
		String sql = "";
		dbpool db = new dbpool();

		int cnt = db.countselect(sqlCheck);
		if (cnt > 0) {
			sql = "update sms_send_status set send_status='" + sendStatus
					+ "' where msgid='" + id + "' and mobile='" + mobile + "'";
			UserUpdateLog.setDebug("OracleSmsList.updateSmsMessageSendStatus(String id, String mobile,String sendStatus) SQL=" + sql + " DATE=" + new Date());
		} else {
			sql = "insert into sms_send_status (msgid,mobile,status) values ('"
					+ id + "','" + mobile + "','" + sendStatus + "')";
			UserAddLog.setDebug("OracleSmsList.updateSmsMessageSendStatus(String id, String mobile,String sendStatus) SQL=" + sql + " DATE=" + new Date());
		}
		return db.executeUpdate(sql);
	}

	public int addSmsMessageSendStatus(String msgId, String mobile,
			String sendStatus) {
		String sql = "insert into sms_send_status (msgid,mobile,status) values ('"
				+ msgId + "','" + mobile + "','" + sendStatus + "')";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleSmsList.addSmsMessageSendStatus(String msgId, String mobile,String sendStatus) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public List getSmsSystemPoints(Page page, List searchproperty,
			List orderproperty) {
		List smsList = new ArrayList();
		String sql = "select id,name,content,status from sms_systempoint "
				+ Conditions.convertToCondition(searchproperty, orderproperty);
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
				OracleSmsSystemPoint sms = new OracleSmsSystemPoint();
				sms.setId(rs.getString("id"));
				sms.setName(rs.getString("name"));
				sms.setContent(rs.getString("content"));
				sms.setStatus(rs.getString("status"));
				smsList.add(sms);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return smsList;
	}

	public int getSmsSystemPointsNum(List searchproperty) {
		String sql = "select id,name,content,status from sms_systempoint "
				+ Conditions.convertToCondition(searchproperty, null);
		dbpool db = new dbpool();
		return db.countselect(sql);
	}

}
