package com.whaty.platform.database.oracle.standard.campus.base;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.base.CampusInfoList;
import com.whaty.platform.campus.base.CampusNewsStatus;
import com.whaty.platform.campus.user.Docent;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.campus.user.OracleDocent;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.CampusLog;

/**
 * @author chenjian
 * 
 */
public class OracleCampusInfoList implements CampusInfoList {

	private String SQLNEWSLIST = "select id,title,short_title,reporter,report_date,body,submit_date,submit_manager_id,SUBMIT_MANAGER_NAME,isactive,istop,ispop,top_sequence,propertystring,isconfirm,pic_link,confirm_manager_id,confirm_manager_name,read_count,docent_id,docent_name,detail,link,course_link from (select t.id,t.title,t.short_title,t.reporter,t.report_date,t.body,t.submit_date,t.submit_manager_id,t.SUBMIT_MANAGER_NAME,t.news_type_id,t.isactive,t.istop,t.ispop,t.top_sequence,t.propertystring,t.isconfirm,t.pic_link,t.confirm_manager_id,t.confirm_manager_name,t.read_count,t.docent_id as docent_id,t.course_link as course_link ,edi.name as docent_name,edi.detail as detail,edi.link as link from campus_info_news t, entity_docent_info edi where t.docent_id = edi.id)";

	public List getNewsList(Page page, List searchproperty, List orderporperty)
			throws PlatformException {
		// TODO �Զ���ɷ������
		dbpool db = new dbpool();
		String sql = SQLNEWSLIST
				+ Conditions.convertToCondition(searchproperty, orderporperty);
		MyResultSet rs = null;
		ArrayList records = null;
		try {
			records = new ArrayList();
			if (null != page) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (null != rs && rs.next()) {
				OracleCampusNews newsRecord = new OracleCampusNews();

				newsRecord.setId(rs.getString("id"));
				newsRecord.setTitle(rs.getString("title"));
				newsRecord.setShortTitle(rs.getString("short_title"));
				newsRecord.setReporter(rs.getString("reporter"));
				newsRecord.setReportDate(rs.getString("report_date"));
				newsRecord.setBody(rs.getString("body"));
				newsRecord.setSubmitDate(rs.getDate("submit_date"));
				newsRecord
						.setSubmitManagerId(rs.getString("submit_manager_id"));
				newsRecord.setSubmitManagerName(rs
						.getString("SUBMIT_MANAGER_NAME"));

				newsRecord.setCourse_Link(rs.getString("course_link"));
				newsRecord.setPropertyString(rs.getString("propertystring"));
				newsRecord.setConfirm(rs.getString("isconfirm"));

				CampusNewsStatus newsStatus = new CampusNewsStatus();
				newsStatus.setActive(rs.getString("isactive").equals("1"));
				newsStatus.setPop(rs.getString("ispop").equals("1"));
				newsStatus.setTop(rs.getString("istop").equals("1"));
				newsStatus.setTopSequence(rs.getInt("top_sequence"));
				newsRecord.setStatus(newsStatus);
				// java.util.Date submitDate = new
				// java.util.Date(rs.getInt("submit_date"));
				// newsRecord.setSubmitDate(submitDate);

				newsRecord.setPicLink(rs.getString("pic_link"));
				newsRecord.setConfirmManagerId(rs
						.getString("confirm_manager_id"));
				newsRecord.setConfirmManagerName(rs
						.getString("confirm_manager_name"));
				newsRecord.setReadCount(rs.getInt("read_count"));

				Docent oracleDocent = new OracleDocent(rs
						.getString("docent_id"));

				newsRecord.setDecentInfo(oracleDocent);

				records.add(newsRecord);
			}
		} catch (java.sql.SQLException e) {
			CampusLog.setError("OracleCampusInfoList@Method:getNewsList(Page page, List searchproperty, List orderporperty)  error!  SQL="+sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return records;
	}

	public int getNewsListNum(List searchproperty) throws PlatformException {
		// TODO �Զ���ɷ������
		dbpool db = new dbpool();
		String sql = SQLNEWSLIST
				+ Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public List getPreActiveNews(String news_id, int preNum)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select t.id, t.title, t.short_title, t.pic_link, t.confirm_manager_id, t.confirm_manager_name, t.read_count, t.reporter, t.report_date, t.body, t.submit_date, t.submit_manager_id,t.SUBMIT_MANAGER_NAME, "
				+ "t.news_type_id, t.isactive, t.istop, t.ispop, t.top_sequence, t.propertystring, t.isconfirm,t.docent_id as docent_id,t.course_link as course_link from campus_info_news t "
				+ "where isconfirm='1' and isactive = '1' and submit_date< (select submit_date from campus_info_news where id = '"
				+ news_id
				+ "') "
				+ "and news_type_id = (select news_type_id from campus_info_news where id = '"
				+ news_id
				+ "')"
				+ "and rownum="
				+ preNum
				+ " order by submit_date desc ";
		MyResultSet rs = null;
		ArrayList newsList = null;
		try {
			newsList = new ArrayList();
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {

				OracleCampusNews newsRecord = new OracleCampusNews();
				newsRecord.setBody(rs.getString("body"));
				newsRecord.setId(rs.getString("id"));
				newsRecord.setPropertyString(rs.getString("propertystring"));
				newsRecord.setReportDate(rs.getString("report_date"));
				newsRecord.setReporter(rs.getString("reporter"));
				newsRecord.setConfirm(rs.getString("isconfirm"));
				CampusNewsStatus newsStatus = new CampusNewsStatus();
				newsStatus.setActive(rs.getString("isactive").equals("1"));
				newsStatus.setPop(rs.getString("ispop").equals("1"));
				newsStatus.setTop(rs.getString("istop").equals("1"));
				newsStatus.setTopSequence(rs.getInt("top_sequence"));
				newsRecord.setStatus(newsStatus);
				newsRecord.setSubmitDate(rs.getDate("submit_date"));
				newsRecord
						.setSubmitManagerId(rs.getString("submit_manager_id"));
				newsRecord.setCourse_Link(rs.getString("course_link"));
				newsRecord.setSubmitManagerName(rs
						.getString("SUBMIT_MANAGER_NAME"));
				newsRecord.setTitle(rs.getString("title"));
				newsRecord.setShortTitle(rs.getString("short_title"));
				newsRecord.setPicLink(rs.getString("pic_link"));
				newsRecord.setConfirmManagerId(rs
						.getString("confirm_manager_id"));
				newsRecord.setConfirmManagerName(rs
						.getString("confirm_manager_name"));
				newsRecord.setReadCount(rs.getInt("read_count"));

				Docent oracleDocent = new OracleDocent(rs
						.getString("docent_id"));
				newsRecord.setDecentInfo(oracleDocent);

				newsList.add(newsRecord);
			}
		} catch (Exception e) {
			 CampusLog.setError("OracleCampusInfoList@getPreActiveNews(String news_id, int preNum)  Error!   SQL = "+sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return newsList;
	}

	public List getNextActiveNews(String news_id, int nextNum)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select t.id, t.title,t.short_title, t.pic_link, t.confirm_manager_id, t.confirm_manager_name, t.read_count , t.reporter, t.report_date, t.body, t.submit_date, t.submit_manager_id,t.SUBMIT_MANAGER_NAME, "
				+ "t.news_type_id, t.isactive, t.istop, t.ispop, t.top_sequence, t.propertystring, t.isconfirm,t.docent_id as docent_id ,t.course_link as course_link from campus_info_news t "
				+ "where isconfirm='1' and isactive = '1' and submit_date> (select submit_date from campus_info_news where id = '"
				+ news_id
				+ "') "
				+ "and news_type_id = (select news_type_id from campus_info_news where id = '"
				+ news_id
				+ "')"
				+ "and rownum="
				+ nextNum
				+ " order by submit_date desc ";
		MyResultSet rs = null;
		ArrayList newsList = null;
		try {
			newsList = new ArrayList();
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {

				OracleCampusNews newsRecord = new OracleCampusNews();
				newsRecord.setBody(rs.getString("body"));
				newsRecord.setId(rs.getString("id"));
				newsRecord.setPropertyString(rs.getString("propertystring"));
				newsRecord.setReportDate(rs.getString("report_date"));
				newsRecord.setReporter(rs.getString("reporter"));
				newsRecord.setConfirm(rs.getString("isconfirm"));
				CampusNewsStatus newsStatus = new CampusNewsStatus();
				newsStatus.setActive(rs.getString("isactive").equals("1"));
				newsStatus.setPop(rs.getString("ispop").equals("1"));
				newsStatus.setTop(rs.getString("istop").equals("1"));
				newsStatus.setTopSequence(rs.getInt("top_sequence"));
				newsRecord.setStatus(newsStatus);
				newsRecord.setSubmitDate(rs.getDate("submit_date"));
				newsRecord
						.setSubmitManagerId(rs.getString("submit_manager_id"));
				newsRecord.setSubmitManagerName(rs
						.getString("SUBMIT_MANAGER_NAME"));
				newsRecord.setTitle(rs.getString("title"));
				newsRecord.setShortTitle(rs.getString("short_title"));
				newsRecord.setPicLink(rs.getString("pic_link"));
				newsRecord.setConfirmManagerId(rs
						.getString("confirm_manager_id"));
				newsRecord.setConfirmManagerName(rs
						.getString("confirm_manager_name"));
				newsRecord.setReadCount(rs.getInt("read_count"));
				newsRecord.setCourse_Link(rs.getString("course_link"));
				Docent oralceDocent = new OracleDocent(rs
						.getString("docent_id"));
				newsRecord.setDecentInfo(oralceDocent);
				newsList.add(newsRecord);
			}
		} catch (Exception e) {
			CampusLog.setError("OracleCampusInfoList@Method:getNextActiveNews(String news_id, int nextNum) Error!  SQL = "+sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return newsList;
	}
}
