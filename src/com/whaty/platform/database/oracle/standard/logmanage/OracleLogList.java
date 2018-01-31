/**
 * 
 */
package com.whaty.platform.database.oracle.standard.logmanage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.logmanage.LogList;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;

/**
 * @author wq
 * 
 */
public class OracleLogList implements LogList {

	private String logEntitySQL = "select id, userid, operate_time, behavior, status, notes, logtype, priority from "
			+ "(select id,userid,to_char(operate_time,'yyyy-mm-dd hh24:mi:ss') as operate_time,behavior,status, "
			+ "notes,logtype,priority from whatyuser_log4j) ";

	private String historyLogEntitySQL = "select id, userid, operate_time, behavior, status, notes, logtype, priority from "
			+ "(select id,userid,to_char(operate_time,'yyyy-mm-dd hh24:mi:ss') as operate_time,behavior,status, "
			+ "notes,logtype,priority from history_log4j) ";

	private String logEntityBackUpSQL = "insert into history_log4j(id, userid, operate_time, behavior, status, "
			+ "notes, logtype, priority) select id, userid, operate_time, behavior, status, notes, logtype, "
			+ "priority from whatyuser_log4j ";

	private String logEntityDeleteSQL = " delete from whatyuser_log4j ";
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.logmanage.LogList#searchLogEntitiesNum(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public int searchLogEntitiesNum(List searchproperty, boolean history) {
		int total = 0;
		dbpool db = new dbpool();
		String sql = logEntitySQL
				+ Conditions.convertToCondition(searchproperty, null);
		total = total + db.countselect(sql);
		if (history) {
			String history_sql = historyLogEntitySQL
					+ Conditions.convertToCondition(searchproperty, null);
			total += db.countselect(history_sql);
		}

		return total;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.logmanage.LogList#searchLogEntities(com.whaty.platform.util.Page,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public List searchLogEntities(Page page, List searchproperty,
			List orderporperty, boolean history) {
		dbpool db = new dbpool();
		String sql = logEntitySQL
				+ Conditions.convertToCondition(searchproperty, orderporperty);
		if (history)
			sql = "("
					+ sql
					+ ") union ("
					+ historyLogEntitySQL
					+ Conditions.convertToCondition(searchproperty,
							orderporperty) + ")";
		
//		System.out.print("\nunion: " + sql);
		MyResultSet rs = null;
		ArrayList logEntityList = null;
		try {
			logEntityList = new ArrayList();
			if (null != page) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (null != rs && rs.next()) {
				OracleLogEntity logEntiy = new OracleLogEntity();
				logEntiy.setId(rs.getString("id"));
				logEntiy.setUserid(rs.getString("userid"));
				logEntiy.setOperate_time(rs.getString("operate_time"));
				logEntiy.setBehavior(rs.getString("behavior"));
				logEntiy.setStatus(rs.getString("status"));
				logEntiy.setNotes(rs.getString("notes"));
				logEntiy.setLogtype(rs.getString("logtype"));
				logEntiy.setPriority(rs.getString("priority"));
				logEntityList.add(logEntiy);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return logEntityList;
	}

	public int backUpLogEntities(List searchproperty) {
		dbpool db = new dbpool();
		String backup_sql = logEntityBackUpSQL
				+ Conditions.convertToCondition(searchproperty, null);
		String delete_sql = logEntityDeleteSQL
				+ Conditions.convertToCondition(searchproperty, null);
		// System.out.println("\nbackup_sql:" + backup_sql);
		// System.out.println("delete_sql:" + delete_sql);
		List sqlList = new ArrayList();
		sqlList.add(backup_sql);
		sqlList.add(delete_sql);
		int i = db.executeUpdateBatch(sqlList);

		return i;
	}
	
	public int addLogEntities(String id, String userid,
			String operateTime,String behavior,
			String status, String notes, String logtype, String priority)throws PlatformException{
		
	 String addlogEntitySQL = "insert into whatyuser_log4j(id, userid, operate_time, behavior, status,notes, logtype, priority) values(?,?,sysdate,?,?,?,?,?)";
	 Connection conn = null;
	 PreparedStatement ps = null;
	 dbpool db = null;
	 int c = 0;
	 try{
		db = new dbpool();
		conn = db.getConn();
		ps = conn.prepareStatement(addlogEntitySQL);
		ps.setString(1, id);
		ps.setString(2, userid);
		ps.setString(3, behavior);
		ps.setString(4, status);
		ps.setString(5, notes);
		ps.setString(6, logtype);
		ps.setString(7, priority);
		c = ps.executeUpdate();
	 }catch(SQLException e){
		 throw new PlatformException();
	 }finally{
		 try{
			 conn.close();
			 ps = null;
		 }catch(SQLException ee){
			 throw new PlatformException();
		 }
		
	 }
	 return c;
	}

}
