package com.whaty.platform.database.oracle.standard.campus.base;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.base.CampusNews;
import com.whaty.platform.campus.base.CampusNewsStatus;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.util.log.CampusLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author chenjian
 * 
 */
public class OracleCampusNews extends CampusNews {

	public OracleCampusNews() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleCampusNews(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select ID,TITLE,SHORT_TITLE,REPORTER,REPORT_DATE,BODY,SUBMIT_DATE,SUBMIT_MANAGER_ID,SUBMIT_MANAGER_NAME,"
				+ "NEWS_TYPE_ID,ISACTIVE,ISTOP,TOP_SEQUENCE,ISPOP,PROPERTYSTRING, pic_link,"
				+ " confirm_manager_id, confirm_manager_name, read_count,isconfirm ,docent_id ,course_link from campus_info_news where id = '"
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
				this.setCourse_Link(rs.getString("course_link"));
				this
						.setConfirmManagerName(rs
								.getString("confirm_manager_name"));
				this.setReadCount(rs.getInt("read_count"));
				String pic_link = rs.getString("pic_link");
				if (pic_link == null || pic_link.trim().equals("null"))
					pic_link = "";
				this.setPicLink(pic_link);
				CampusNewsStatus newsStatus = new CampusNewsStatus();
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
				this.setDocent_Key(rs.getString("docent_id"));
				this.setStatus(newsStatus);
			}
		} catch (java.sql.SQLException e) {
			CampusLog.setError("OracleCampusNews(String aid) error!  SQL = " + sql);
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
		sql = "update campus_info_news set ISACTIVE=0 where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		if (i < 1) {
			throw new PlatformException("news do lock error!");
		}
		UserUpdateLog.setDebug("OracleCampusNews.doLock() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.News#undoLock()
	 */
	public void undoLock() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update campus_info_news set ISACTIVE=1 where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		if (i < 1) {
			throw new PlatformException("news undoLock error!");
		}
		UserUpdateLog.setDebug("OracleCampusNews.undoLock() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.News#putTop(int)
	 */
	public void putTop(int sequence) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update campus_info_news set TOP_SEQUENCE=TOP_SEQUENCE+1 where "
				+ "TOP_SEQUENCE>=" + sequence
				+ " and ISTOP=1";
		String sql1 = "update campus_info_news set ISTOP=1, TOP_SEQUENCE=" + sequence
				+ " " + "where id='" + this.getId() + "'";
		List sqlList = new ArrayList();
		sqlList.add(sql);
		sqlList.add(sql1);
		int i = db.executeUpdateBatch(sqlList);
		if (i < 1) {
			throw new PlatformException("news putTop error!");
		}
		UserUpdateLog.setDebug("OracleCampusNews.putTop(int sequence) SQL1=" + sql + " SQL2=" + sql1 + " COUNT=" + i + " DATE=" + new Date());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.News#cancelTop()
	 */
	public void cancelTop() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update campus_info_news set ISTOP=0 where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		if (i < 1) {
			throw new PlatformException("news cancelTop error!");
		}
		UserUpdateLog.setDebug("OracleCampusNews.cancelTop() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#add()
	 */
	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into campus_info_news (ID,TITLE ,short_title, pic_link,REPORTER,REPORT_DATE,SUBMIT_DATE,"
				+ "SUBMIT_MANAGER_ID,SUBMIT_MANAGER_NAME,ISACTIVE,ISTOP,TOP_SEQUENCE,ISPOP,BODY,docent_id,course_link) "
				+ "values(to_char(s_campus_info_news_id.nextval),'"
				+ this.getTitle()
				+ "','"
				+ this.getShortTitle()
				+ "','"
				+ this.getPicLink()
				+ "','"
				+ this.getReporter()
				+ "','"
				+ this.getReportDate()
				+ "',"
				+ "sysdate,'"
				+ this.getSubmitManagerId()
				+ "','"
				+ this.getSubmitManagerName()
				+ "',"
				+ this.getStatus().getIsActiveNum()
				+ ""
				+ ","
				+ this.getStatus().getIsTopNum()
				+ ""
				+ ","
				+ this.getStatus().getTopSequence()
				+ ""
				+ ","
				+ this.getStatus().getIsPopNum() 
				+ ",?,'"
				+ this.getDocent_Key()
				+"','"
				+ this.getCourse_Link()
				+"')";
		int i = db.executeUpdate(sql, this.getBody());
		UserAddLog.setDebug("OracleCampusNews.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#update()
	 */
	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		String pic_link = this.getPicLink();
		String pic_link_sql = "";
		if (pic_link != null && !pic_link.trim().equals("null")
				&& !pic_link.trim().equals(""))
			pic_link_sql = " pic_link='" + pic_link + "', ";
		String confirm_sql = "";
		if (this.getConfirm() != null)
			confirm_sql = " ISCONFIRM='" + this.getConfirm() + "', ";
		sql = "update campus_info_news set TITLE='" + this.getTitle()
				+ "',short_title='" + this.getShortTitle() + "',"
				+ pic_link_sql + confirm_sql + " REPORTER='"
				+ this.getReporter() + "',REPORT_DATE='" + this.getReportDate()
				+ "',SUBMIT_DATE=sysdate" + "" + ",SUBMIT_MANAGER_ID='"
				+ this.getSubmitManagerId() + "', SUBMIT_MANAGER_NAME='"
				+ this.getSubmitManagerName() +  "',ISACTIVE="
				+ this.getStatus().getIsActiveNum() + "," + "ISTOP="
				+ this.getStatus().getIsTopNum() + "," + "TOP_SEQUENCE="
				+ this.getStatus().getTopSequence() + "," + "ISPOP="
				+ this.getStatus().getIsPopNum() + "," + "BODY=?,docent_id ='"
				+ this.getDocent_Key()+"',course_link='"
				+ this.getCourse_Link()
				+ "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql, this.getBody());
		UserUpdateLog.setDebug("OracleCampusNews.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
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
		sql = "delete from campus_info_news where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleCampusNews.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
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
		sql = "update campus_info_news set ISPOP=1 where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		if (i < 1) {
			throw new PlatformException("news setPop error!");
		}
		UserUpdateLog.setDebug("OracleCampusNews.setPop() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.News#cancelPop()
	 */
	public void cancelPop() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update campus_info_news set ISPOP=0 where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		if (i < 1) {
			throw new PlatformException("news cancelPop error!");
		}
		UserUpdateLog.setDebug("OracleCampusNews.cancelPop() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
	}

	public void confirmNews(String confirm, String confirm_manager_id,
			String confirm_manager_name) throws PlatformException {
		setConfirm(confirm);
		dbpool db = new dbpool();
		String sql = "update campus_info_news set isconfirm = " + confirm
				+ ", confirm_manager_id = '" + confirm_manager_id
				+ "', confirm_manager_name='" + confirm_manager_name + "' "
				+ " where id = '" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		if (i < 1) {
			throw new PlatformException("Confirm News Wrong!");
			// System.out.print("Confirm News Wrong!");
		}
		UserUpdateLog.setDebug("OracleCampusNews.confirmNews(String confirm, String confirm_manager_id,String confirm_manager_name) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
	}

	public int addReadCount() throws PlatformException {
		String sql = "update campus_info_news set readCount = readCount + 1 where id = '"
				+ this.getId() + "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleCampusNews.addReadCount() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int updateReadCount(int count) throws PlatformException {
		String sql = "update campus_info_news set read_count = " + count
				+ " where id = '" + this.getId() + "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleCampusNews.updateReadCount() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
}
