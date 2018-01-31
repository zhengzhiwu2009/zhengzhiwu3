/**
 * 
 */
package com.whaty.platform.database.oracle.standard.sms.basic;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.sms.basic.SmsMessage;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author wq
 * 
 */
public class OracleSmsMessage extends SmsMessage {

	public OracleSmsMessage() {

	}

	public OracleSmsMessage(String id) {
		String sql = "select id,targets,content,status,sender,to_char(time,'yyyy-mm-dd hh24:mi:ss') as time,"
				+ "scope,checker,site,note,type,settime from sms_info where id='"
				+ id + "'";
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
				this.setType(rs.getString("type"));
				this.setSetTime(rs.getString("setTime"));
			}
		} catch (SQLException e) {
			
		}
		db.close(rs);
	}

	public int add() {
		String[] args = { "" };
		int count = 0; // 发送对象数
		String content = this.getContent();
		String targets = this.getTargets();
		int bytes_num = content.getBytes().length / 100;
		int itemnum = content.getBytes().length % 100 == 0 ? bytes_num
				: bytes_num + 1; // 拆分短信条数

		if (targets.indexOf(",") >= 0) {
			args = targets.split(",");
		} else if (targets.indexOf("'") >= 0) {
			args = targets.split("'");
		}
		for (int i = 0; i < args.length; i++) {
			if (args[i] != null && !args[i].equals("")) {
				count++;
			} else if (args.length == 1) {
				count = 1;
				break;
			}
		}
		String sql = "insert into sms_info(id,targets,content,sender,scope,site,tea_id,sendobjnum,splititem,type,time,settime,status,sendstatus) values "
				+ "(concat('21',to_char(s_sms_info_id.nextval)),?, '"
				+ this.getContent()
				+ "','"
				+ this.getSender()
				+ "','"
				+ this.getScope()
				+ "','"
				+ this.getSiteId()
				+ "','"
				+ this.getTea_id()
				+ "',"
				+ count
				+ ","
				+ itemnum
				+ ",'"
				+ this.getType()
				+ "',"
				+ " to_date(' " +this.getTime() + " ','yyyy-mm-dd HH24:mi:ss') "
				+ ",'"
				+ this.getSetTime()
				+ "','"
				+ this.getStatus() 
				+ "','"+this.getSendStatus()
				+ "')";
		dbpool db = new dbpool();
		UserAddLog.setDebug("OracleSmsMessage.add() SQL=" + sql +  " DATE=" + new Date());
		return db.executeUpdate(sql, this.getTargets());
	}

	public int update() {
		String[] args = { "" };
		int count = 0; // 发送对象数
		String content = this.getContent();
		String targets = this.getTargets();
		int bytes_num = content.getBytes().length / 100;
		int itemnum = content.getBytes().length % 100 == 0 ? bytes_num
				: bytes_num + 1; // 拆分短信条数

		if (targets.indexOf(",") >= 0) {
			args = targets.split(",");
		} else if (targets.indexOf("'") >= 0) {
			args = targets.split("'");
		}
		for (int i = 0; i < args.length; i++) {
			if (args[i] != null && !args[i].equals("")) {
				count++;
			} else if (args.length == 1) {
				count = 1;
				break;
			}
		}
		
		
		//修改，加入time，防止为null
		String time="";
		if(this.getTime() == null || "".equals(this.getTime())){
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date(System.currentTimeMillis());
			time = sf.format(date);
		}else{
			time = this.getTime();
		}
		String sql = "update sms_info set targets=?,content='"
				+ this.getContent() + "',status='" + this.getStatus()
				+ "',sender='" + this.getSender() + "',time=to_date('"
				+ time + "','yyyy-mm-dd hh24:mi:ss'),scope='"
				+ this.getScope() + "',checker='" + this.getChecker()
				+ "',sendobjnum =" + count + ",splititem = " + itemnum
				+ ",type = '" + this.getType() + "',settime = '"
				+ this.getSetTime() + "' where id='" + this.getMsgId() + "'";

		// String sql = "update sms_info set targets=?,content='"
		// + this.getContent() + "',status='" + this.getStatus()
		// + "',sender='" + this.getSender() + "',time=to_date('"
		// + this.getTime() + "','yyyy-mm-dd hh24:mi:ss'),scope='"
		// + this.getScope() + "',checker='" + this.getChecker()
		// + "' where id='" + this.getMsgId() + "'";
		dbpool db = new dbpool();
		UserUpdateLog.setDebug("OracleSmsMessage.update() SQL=" + sql +  " DATE=" + new Date());
		return db.executeUpdate(sql, this.getTargets());
	}

	public int updateTarget() {
		String sql = "update sms_info set targets=?,content='"
				+ this.getContent() + "' where id='" + this.getMsgId() + "'";
		dbpool db = new dbpool();
		UserUpdateLog.setDebug("OracleSmsMessage.updateTarget() SQL=" + sql +  " DATE=" + new Date());
	    return db.executeUpdate(sql, this.getTargets());
	}

	public int delete() {
		String sql = "delete from sms_info where id='" + this.getMsgId() + "'";
		dbpool db = new dbpool();
		UserDeleteLog.setDebug("OracleSmsMessage.delete() SQL=" + sql +  " DATE=" + new Date());
		return db.executeUpdate(sql);
	}
	public int updateMsgStatus(){
		String sql = "update sms_info set sendstatus ='"+this.getSendStatus()+"' where id='" + this.getMsgId() + "'";
		dbpool db = new dbpool();
		UserUpdateLog.setDebug("OracleSmsMessage.updateMsgStatus() SQL=" + sql +  " DATE=" + new Date());
		return db.executeUpdate(sql);
	}
}
