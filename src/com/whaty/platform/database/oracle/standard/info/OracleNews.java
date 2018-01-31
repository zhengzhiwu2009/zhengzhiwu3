/**
 * 
 */
package com.whaty.platform.database.oracle.standard.info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import oracle.sql.CLOB;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.info.News;
import com.whaty.platform.info.NewsStatus;
import com.whaty.platform.util.log.InfoLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author chenjian
 * 
 */
public class OracleNews extends News {

	public OracleNews() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleNews(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select ID,TITLE,SHORT_TITLE,REPORTER,REPORT_DATE,BODY,SUBMIT_DATE,SUBMIT_MANAGER_ID,SUBMIT_MANAGER_NAME,"
				+ "NEWS_TYPE_ID,ISACTIVE,ISTOP,TOP_SEQUENCE,ISPOP,PROPERTYSTRING, pic_link,"
				+ " confirm_manager_id, confirm_manager_name, read_count,isconfirm from info_news where id = '"
				+ id + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setBody(rs.getString("body"));
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				this.setShortTitle(rs.getString("short_title"));
				this.setReporter(rs.getString("REPORTER"));
				this.setReportDate(rs.getString("REPORT_DATE"));
				this.setSubmitDate(rs.getDate("SUBMIT_DATE"));
				this.setSubmitManagerId(rs.getString("SUBMIT_MANAGER_ID"));
				this.setSubmitManagerName(rs.getString("SUBMIT_MANAGER_NAME"));
				this.setPropertyString(rs.getString("PROPERTYSTRING"));
				this.setConfirmManagerId(rs.getString("confirm_manager_id"));
				this.setConfirm(rs.getString("isconfirm"));
				this
						.setConfirmManagerName(rs
								.getString("confirm_manager_name"));
				this.setReadCount(rs.getInt("read_count"));
				String pic_link = rs.getString("pic_link");
				if (pic_link == null || pic_link.trim().equals("null"))
					pic_link = "";
				this.setPicLink(pic_link);
				OracleNewsType newsType = new OracleNewsType(rs
						.getString("news_type_id"));
				this.setType(newsType);
				NewsStatus newsStatus = new NewsStatus();
				if (rs.getInt("ISACTIVE") > 0) {
					newsStatus.setActive(true);
				} else {
					newsStatus.setActive(false);
				}
				if (rs.getInt("ISTOP") > 0) {
					newsStatus.setTop(true);
					newsStatus.setTopSequence(rs.getInt("TOP_SEQUENCE"));
				} else {
					newsStatus.setTop(false);
				}
				if (rs.getInt("ISPOP") > 0) {
					newsStatus.setPop(true);
				} else {
					newsStatus.setPop(false);
				}
				this.setStatus(newsStatus);

			}
		} catch (java.sql.SQLException e) {
			
			InfoLog.setError("OracleClasse(String aid) error " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.News#doLock()
	 */
	public void doLock() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update info_news set ISACTIVE=0 where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleNews.doLock() SQL=" + sql + " COUNT=" + i
				+ " DATE=" + new Date());
		if (i < 1) {
			throw new PlatformException("news do lock error!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.News#undoLock()
	 */
	public void undoLock() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update info_news set ISACTIVE=1 where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleNews.undoLock() SQL=" + sql + " COUNT="
				+ i + " DATE=" + new Date());
		if (i < 1) {
			throw new PlatformException("news undoLock error!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.News#putTop(int)
	 */
	public void putTop(int sequence) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update info_news set TOP_SEQUENCE=TOP_SEQUENCE+1 where "
				+ "TOP_SEQUENCE>=" + sequence
				+ " and ISTOP=1 and NEWS_TYPE_ID=" + "'"
				+ this.getType().getId() + "'";

		String sql1 = "update info_news set ISTOP=1, TOP_SEQUENCE=" + sequence
				+ " " + "where id='" + this.getId() + "'";

		List sqlList = new ArrayList();
		sqlList.add(sql);
		sqlList.add(sql1);
		int i = db.executeUpdateBatch(sqlList);
		UserUpdateLog.setDebug("OracleNews.putTop(int sequence) SQL1=" + sql
				+ " SQL2=" + sql1 + " COUNT=" + i + " DATE=" + new Date());
		if (i < 1) {
			throw new PlatformException("news putTop error!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.News#cancelTop()
	 */
	public void cancelTop() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update info_news set ISTOP=0 where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleNews.cancelTop() SQL=" + sql + " COUNT="
				+ i + " DATE=" + new Date());
		if (i < 1) {
			throw new PlatformException("news cancelTop error!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#add()
	 */
//	修改为日期型report_date;,新闻内容为clob 类型，另外处理 修改方法
	
	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet myset= null;
		Connection con = null;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		int i = 0;
		try{
			
		
		Date date = sf.parse(this.getReportDate());
		java.sql.Date d = new java.sql.Date(date.getTime());
		
		
		
		con = db.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		//开始事务
		con.setAutoCommit(false);
		
		sql = "insert into info_news (ID,TITLE ,short_title, pic_link,REPORTER,REPORT_DATE,SUBMIT_DATE,PROPERTYSTRING,"
				+ "SUBMIT_MANAGER_ID,SUBMIT_MANAGER_NAME, NEWS_TYPE_ID,ISACTIVE,ISTOP,TOP_SEQUENCE,ISPOP,BODY) "
				+ "values(to_char(s_info_news_id.nextval),'"
				+ this.getTitle()
				+ "','"
				+ this.getShortTitle()
				+ "','"
				+ this.getPicLink()
				+ "','"
				+ this.getReporter()
				+ "',"
				+ "?"
				+ ","
				+ "sysdate,'"
				+ this.getPropertyString()
				+ "','"
				+ this.getSubmitManagerId()
				+ "','"
				+ this.getSubmitManagerName()
				+ "','"
				+ this.getType().getId()
				+ "'"
				+ ","
				+ this.getStatus().getIsActiveNum()
				+ ""
				+ ","
				+ this.getStatus().getIsTopNum()
				+ ""
				+ ","
				+ this.getStatus().getTopSequence()
				+ ""
				+ ","
				+ this.getStatus().getIsPopNum() + ",?)";
		
		ps = con.prepareStatement(sql);
		ps.setDate(1, d);
		ps.setString(2, this.getBody());
//		System.out.println(sql);
		i = ps.executeUpdate();
		
		sql = "select body from info_news where title=? for update";
		
		ps = con.prepareStatement(sql);
		ps.setString(1, this.getTitle());
		rs = ps.executeQuery();
		CLOB clob = null;
		while(rs.next()){
			clob = (CLOB)rs.getClob("body");
		}
		
//		BufferedWriter write = new BufferedWriter(clob.getCharacterOutputStream());
//		StringBuffer sb = new StringBuffer(this.getBody());
//		write.write(sb.toString());
		
		con.commit();
		
		UserAddLog.setDebug("OracleNews.add() SQL=" + sql + " COUNT=" + i
				+ " DATE=" + new Date());
		}catch(SQLException e){
			try{
				con.rollback();
			}catch(SQLException ee){
				throw new PlatformException(e.getMessage());
			}
			throw new PlatformException(e.getMessage());
			
		}catch(ParseException e){
			try{
				con.rollback();
			}catch(SQLException ee){
				throw new PlatformException(e.getMessage());
			}
			throw new PlatformException(e.getMessage());
//		}
//		catch(IOException e){
//			try{
//				con.rollback();
//			}catch(SQLException ee){
//				throw new PlatformException(e.getMessage());
//			}
//			throw new PlatformException(e.getMessage());
		}finally{
			try{
				con.close();
				db.close(myset);
			}catch(SQLException e){
				throw new PlatformException(e.getMessage());
			}
		}
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#update()
	 */
	
	//修改了该方法，使用其可以读取clob的值，直接使用jdbc lwx 2008-08-14
	
	public int update() throws PlatformException {
		
		String sql = "";
		int i = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sf.parse(this.getReportDate());
			java.sql.Date d = new java.sql.Date(date.getTime());
		
		dbpool db = new dbpool();
		
		String pic_link = this.getPicLink();
		String pic_link_sql = "";
		if (pic_link != null && !pic_link.trim().equals("null")
				&& !pic_link.trim().equals(""))
			pic_link_sql = " pic_link='" + pic_link + "', ";
		String confirm_sql = "";
		if (this.getConfirm() != null)
			confirm_sql = " ISCONFIRM='" + this.getConfirm() + "', ";
		sql = "update info_news set TITLE='" + this.getTitle()
				+ "',short_title='" + this.getShortTitle() + "',"
				+ pic_link_sql + confirm_sql + " REPORTER='"
				+ this.getReporter() + "',REPORT_DATE=?"
				+",SUBMIT_DATE=sysdate" + "" + ",SUBMIT_MANAGER_ID='"
				+ this.getSubmitManagerId() + "', SUBMIT_MANAGER_NAME='"
				+ this.getSubmitManagerName() + "',PROPERTYSTRING='"
				+ this.getPropertyString() + "',NEWS_TYPE_ID='"
				+ this.getType().getId() + "'," + "ISACTIVE="
				+ this.getStatus().getIsActiveNum() + "," + "ISTOP="
				+ this.getStatus().getIsTopNum() + "," + "TOP_SEQUENCE="
				+ this.getStatus().getTopSequence() + "," + "ISPOP="
				+ this.getStatus().getIsPopNum() + "," + "BODY=?"
				+ this.getStatus().getIsPopNum()
				+ " where id='" + this.getId() + "'";
		
			conn = db.getConn();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setDate(1, d);
			ps.setString(2, this.getBody());
			i = ps.executeUpdate();
			
			conn.commit();
			
		}catch(SQLException e){
			throw new PlatformException();
//		}catch(IOException e){
//			throw new PlatformException();
		}catch(ParseException e){
			throw new PlatformException();
		}finally{
			try{
				conn.close();
				ps.close();
			}catch(SQLException e){
				throw new PlatformException();
			}
			
		}

		UserUpdateLog.setDebug("OracleNews.update() SQL=" + sql + " COUNT=" + i
				+ " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#delete()
	 */
	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from info_news where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleNews.delete() SQL=" + sql + " COUNT=" + i
				+ " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.News#setPop()
	 */
	public void setPop() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update info_news set ISPOP=1 where id='" + this.getId() + "'";
		InfoLog.setDebug("OracleNews@Method:setPop()" + sql);
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleNews.setPop() SQL=" + sql + " COUNT=" + i
				+ " DATE=" + new Date());
		if (i < 1) {
			throw new PlatformException("news setPop error!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.News#cancelPop()
	 */
	public void cancelPop() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update info_news set ISPOP=0 where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleNews.cancelPop() SQL=" + sql + " COUNT="
				+ i + " DATE=" + new Date());
		if (i < 1) {
			throw new PlatformException("news cancelPop error!");
		}
	}

	public void confirmNews(String confirm, String confirm_manager_id,
			String confirm_manager_name) throws PlatformException {
		setConfirm(confirm);
		dbpool db = new dbpool();
		if(confirm != null && confirm.equals("0")){
			confirm_manager_id = "";
			confirm_manager_name = "";
		}
		String sql = "update info_news set isconfirm = " + confirm
				+ ", confirm_manager_id = '" + confirm_manager_id
				+ "', confirm_manager_name='" + confirm_manager_name + "' "
				+ " where id = '" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog
				.setDebug("OracleNews.confirmNews(String confirm,String confirm_manager_id,String confirm_manager_name) SQL="
						+ sql + " COUNT=" + i + " DATE=" + new Date());
		if (i < 1) {
			throw new PlatformException("Confirm News Wrong!");
			// System.out.print("Confirm News Wrong!");
		}
	}

	public int copyTo(String NewsTypeId) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into info_news (ID,TITLE ,short_title, pic_link,REPORTER,REPORT_DATE,SUBMIT_DATE,PROPERTYSTRING,"
				+ "SUBMIT_MANAGER_ID,SUBMIT_MANAGER_NAME, NEWS_TYPE_ID,ISACTIVE,ISTOP,TOP_SEQUENCE,ISPOP,BODY) "
				+ "values(to_char(s_info_news_id.nextval),'"
				+ this.getTitle()
				+ "','"
				+ this.getShortTitle()
				+ "','"
				+ this.getPicLink()
				+ "','"
				+ this.getReporter()
				+ "',?"
				+ ",sysdate,'"
				+ this.getPropertyString()
				+ "','"
				+ this.getSubmitManagerId()
				+ "','"
				+ this.getSubmitManagerName()
				+ "','"
				+ NewsTypeId
				+ "'"
				+ ","
				+ this.getStatus().getIsActiveNum()
				+ ""
				+ ","
				+ this.getStatus().getIsTopNum()
				+ ""
				+ ","
				+ this.getStatus().getTopSequence()
				+ ""
				+ ","
				+ this.getStatus().getIsPopNum() + ",?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		
		int i = 0;
		try{
			Date  date = sf.parse(this.getReportDate());
			java.sql.Date d = new java.sql.Date(date.getTime());
			conn = db.getConn();
			ps = conn.prepareStatement(sql);
			ps.setDate(1, d);
			ps.setString(2, this.getBody());
			
			i = ps.executeUpdate();
		}catch(ParseException e){
			throw new PlatformException();
		}catch(SQLException e){
			throw new PlatformException();
		}finally{
			try{
				conn.close();
			}catch(SQLException e){
				throw new PlatformException();
			}
		}
		
		UserAddLog.setDebug("OracleNews.copyTo(String NewsTypeId) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int addReadCount() throws PlatformException {
		String sql = "update info_news set readCount = readCount + 1 where id = '"
				+ this.getId() + "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleNews.addReadCount() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int updateReadCount(int count) throws PlatformException {
		String sql = "update info_news set read_count = " + count
				+ " where id = '" + this.getId() + "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleNews.updateReadCount(int count) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
}
