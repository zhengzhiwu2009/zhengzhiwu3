/**
 * 
 */
package com.whaty.platform.database.oracle.standard.info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleResultSet;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.entity.user.Teacher;
import com.whaty.platform.info.InfoList;
import com.whaty.platform.info.NewsStatus;
import com.whaty.platform.info.NewsType;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.InfoLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;

/**
 * @author chenjian
 * 
 */
public class OracleInfoList implements InfoList {

	// private String SQLNEWSTYPES = "select t.id, t.name, t.note, t.parent_id,
	// t.status from info_news_type t ";
	private String SQLNEWSTYPES = "select id, name, note, parent_id, status, parent_name, tag from (select a.id, a.name , a.note, a.parent_id, a.status, b.name as parent_name, a.tag from info_news_type a, info_news_type b where a.parent_id = b.id(+) and a.id != '0' and (a.parent_id != '0' or a.id = '1000') and a.id not in('725','729','801','739','656')) ";

	private String SQLNEWSLIST = "select t.id, t.title, t.short_title, t.reporter, t.report_date, t.body, t.submit_date, t.submit_manager_id,t.SUBMIT_MANAGER_NAME, t.news_type_id, t.isactive, t.istop, t.ispop, t.top_sequence, t.propertystring, t.isconfirm,t.pic_link, t.confirm_manager_id, t.confirm_manager_name, t.read_count from info_news t ";

	private String SQLNEWSLIST_NOTICE = "select t.id, t.title, t.short_title, t.reporter, t.report_date, t.body, t.submit_date, t.submit_manager_id,t.SUBMIT_MANAGER_NAME, t.news_type_id, t.isactive, t.istop, t.ispop, t.top_sequence, t.propertystring, t.isconfirm,t.pic_link, t.confirm_manager_id, t.confirm_manager_name, t.read_count from (select * from info_news where news_type_id in (select id from info_news_type where tag not like '%��ҳ%')) t ";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoList#getTemplatesNum(java.util.List)
	 */
	public int getTemplatesNum(List searchproperty) throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoList#getTemplates(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List)
	 */
	public List getTemplates(Page page, List searchproperty, List orderproperty)
			throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getNewsTypesNum(List searchproperty) throws PlatformException {
		// TODO �Զ���ɷ������
		// ��ȡ����������Ŀ
		dbpool db = new dbpool();
		String sql = SQLNEWSTYPES
				+ Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public List getNewsTypes(Page page, List searchproperty, List orderporperty)
			throws PlatformException {
		// TODO �Զ���ɷ������
		// ��ȡ���������б�
		dbpool db = new dbpool();
		String sql = SQLNEWSTYPES
				+ Conditions.convertToCondition(searchproperty, orderporperty);
		// Log.setDebug("newstype_lists= " + sql);
		MyResultSet rs = null;
		ArrayList records = null;
		try {
			records = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {

				OracleNewsType newsTypeRecord = new OracleNewsType();
				newsTypeRecord.setId(rs.getString("id"));
				newsTypeRecord.setName(rs.getString("name"));
				newsTypeRecord.setParentId(rs.getString("parent_id"));
				newsTypeRecord.setStatus(rs.getString("status"));
				newsTypeRecord.setNote(rs.getString("note"));
				newsTypeRecord.setParentName(rs.getString("parent_name"));
				newsTypeRecord.setTag(rs.getString("tag"));
				records.add(newsTypeRecord);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return records;
	}

	public List getPreActiveNews(String news_id, int preNum)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select t.id, t.title, t.short_title, t.pic_link, t.confirm_manager_id, t.confirm_manager_name, t.read_count, t.reporter, t.report_date, t.body, t.submit_date, t.submit_manager_id,t.SUBMIT_MANAGER_NAME, "
				+ "t.news_type_id, t.isactive, t.istop, t.ispop, t.top_sequence, t.propertystring, t.isconfirm from info_news t "
				+ "where isconfirm='1' and isactive = '1' and submit_date< (select submit_date from info_news where id = '"
				+ news_id
				+ "') "
				+ "and news_type_id = (select news_type_id from info_news where id = '"
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

				OracleNews newsRecord = new OracleNews();
				newsRecord.setBody(rs.getString("body"));
				newsRecord.setId(rs.getString("id"));
				newsRecord.setPropertyString(rs.getString("propertystring"));
				newsRecord.setReportDate(rs.getString("report_date"));
				newsRecord.setReporter(rs.getString("reporter"));
				newsRecord.setConfirm(rs.getString("isconfirm"));
				NewsStatus newsStatus = new NewsStatus();
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
				OracleNewsType newsType = new OracleNewsType(rs
						.getString("news_type_id"));
				newsRecord.setType(newsType);
				newsList.add(newsRecord);
			}
		} catch (Exception e) {
			
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
				+ "t.news_type_id, t.isactive, t.istop, t.ispop, t.top_sequence, t.propertystring, t.isconfirm from info_news t "
				+ "where isconfirm='1' and isactive = '1' and submit_date> (select submit_date from info_news where id = '"
				+ news_id
				+ "') "
				+ "and news_type_id = (select news_type_id from info_news where id = '"
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

				OracleNews newsRecord = new OracleNews();
				newsRecord.setBody(rs.getString("body"));
				newsRecord.setId(rs.getString("id"));
				newsRecord.setPropertyString(rs.getString("propertystring"));
				newsRecord.setReportDate(rs.getString("report_date"));
				newsRecord.setReporter(rs.getString("reporter"));
				newsRecord.setConfirm(rs.getString("isconfirm"));
				NewsStatus newsStatus = new NewsStatus();
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
				OracleNewsType newsType = new OracleNewsType(rs
						.getString("news_type_id"));
				newsRecord.setType(newsType);
				newsList.add(newsRecord);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return newsList;
	}
	
	
	public List getNewsList(Page page, List searchproperty, List orderporperty)
			throws PlatformException {
		// TODO �Զ���ɷ������
		dbpool db = new dbpool();
		// SQLNEWSLIST = "select t.submit_date,
		// t.isactive, t.top_sequence
		String sql = SQLNEWSLIST
				+ Conditions.convertToCondition(searchproperty, orderporperty);
		// System.out.print("Newslist: " + sql);
		MyResultSet rs = null;
		ResultSet reSet = null;
		Connection con = null;
		ArrayList records = null;
		try {
			records = new ArrayList();
			if (null != page) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
				
				con = db.getConn();
				PreparedStatement ps = con.prepareStatement(sql);
				reSet = ps.executeQuery();
				
			} else {
				rs = db.executeQuery(sql);
			}
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			while (null != rs && rs.next() && reSet != null && reSet.next()) {
				OracleNews newsRecord = new OracleNews();
				newsRecord.setBody(((OracleResultSet)reSet).getString("body"));
				newsRecord.setId(rs.getString("id"));
				newsRecord.setPropertyString(rs.getString("propertystring"));
				Date date = (Date)rs.getDate("report_date");
				newsRecord.setReportDate(sf.format(date));
				newsRecord.setReporter(rs.getString("reporter"));
				newsRecord.setConfirm(rs.getString("isconfirm"));
				NewsStatus newsStatus = new NewsStatus();
				newsStatus.setActive(rs.getString("isactive").equals("1"));
				newsStatus.setPop(rs.getString("ispop").equals("1"));
				newsStatus.setTop(rs.getString("istop").equals("1"));
				newsStatus.setTopSequence(rs.getInt("top_sequence"));
				newsRecord.setStatus(newsStatus);
				newsRecord.setSubmitDate(rs.getDate("submit_date"));
				// java.util.Date submitDate = new
				// java.util.Date(rs.getInt("submit_date"));
				// newsRecord.setSubmitDate(submitDate);
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
				OracleNewsType newsType = new OracleNewsType(rs
						.getString("news_type_id"));
				newsRecord.setType(newsType);
				records.add(newsRecord);
			}
		} catch (Exception e) {
			
		} finally {
			try{
				db.close(rs);
				con.close();
				reSet = null;
				db = null;
			}catch(SQLException  e){
				throw new  PlatformException("database error");
			}
			
		}
		return records;
	}

	public int getNewsListNum(List searchproperty) throws PlatformException {
		// TODO �Զ���ɷ������
		dbpool db = new dbpool();
		String sql = SQLNEWSLIST
				+ Conditions.convertToCondition(searchproperty, null);
		InfoLog.setDebug("OracleInfoList@Method:getNewsListNum()" + sql);
		return db.countselect(sql);
	}

	public List getNewsListForTeacher(Page page, Teacher teacher)
			throws PlatformException {
		// TODO �Զ���ɷ������
		List searchpropertys = new ArrayList();
		List orderpropertys = new ArrayList();
		SearchProperty searchProperty = new SearchProperty("t.propertystring",
				"person", "like");
		searchpropertys.add(searchProperty);
		searchProperty = new SearchProperty("t.propertystring", "teachers",
				"like");
		searchpropertys.add(searchProperty);

		searchpropertys.add(new SearchProperty("isactive", "1", "="));
		searchpropertys.add(new SearchProperty("isconfirm", "1", "="));
		searchpropertys.add(new SearchProperty("isactive", "1", "="));
		orderpropertys.add(new OrderProperty("istop", OrderProperty.DESC));
		orderpropertys
				.add(new OrderProperty("SUBMIT_DATE", OrderProperty.DESC));
		return this.getNewsList(page, searchpropertys, orderpropertys);

	}

	public int getNewsListNumForTeacher(Teacher teacher)
			throws PlatformException {
		// TODO �Զ���ɷ������
		List searchpropertys = new ArrayList();
		SearchProperty searchProperty = new SearchProperty("t.propertystring",
				"person", "like");
		searchpropertys.add(searchProperty);
		searchProperty = new SearchProperty("t.propertystring", "teachers",
				"like");
		searchpropertys.add(new SearchProperty("isactive", "1", "="));
		searchpropertys.add(new SearchProperty("isconfirm", "1", "="));
		searchpropertys.add(new SearchProperty("isactive", "1", "="));
		searchpropertys.add(searchProperty);
		return this.getNewsListNum(searchpropertys);
	}

	public List getNewsListForManager(Page page) throws PlatformException {
		// TODO �Զ���ɷ������
		List searchpropertys = new ArrayList();
		List orderpropertys = new ArrayList();
		SearchProperty searchProperty = new SearchProperty("t.propertystring",
				"person", "like");
		searchpropertys.add(searchProperty);
		searchProperty = new SearchProperty("t.propertystring", "managers",
				"like");
		searchpropertys.add(searchProperty);

		searchpropertys.add(new SearchProperty("isactive", "1", "="));
		searchpropertys.add(new SearchProperty("isconfirm", "1", "="));
		searchpropertys.add(new SearchProperty("isactive", "1", "="));
		orderpropertys.add(new OrderProperty("istop", OrderProperty.DESC));
		orderpropertys
				.add(new OrderProperty("SUBMIT_DATE", OrderProperty.DESC));
		return this.getNewsList(page, searchpropertys, orderpropertys);

	}

	public int getNewsListNumForManager() throws PlatformException {
		// TODO �Զ���ɷ������
		List searchpropertys = new ArrayList();
		SearchProperty searchProperty = new SearchProperty("t.propertystring",
				"person", "like");
		searchpropertys.add(searchProperty);
		searchProperty = new SearchProperty("t.propertystring", "managers",
				"like");
		searchpropertys.add(searchProperty);
		searchpropertys.add(new SearchProperty("isactive", "1", "="));
		searchpropertys.add(new SearchProperty("isconfirm", "1", "="));
		searchpropertys.add(new SearchProperty("isactive", "1", "="));
		return this.getNewsListNum(searchpropertys);
	}

	public List getNewsListForSubAdmin(Page page, String siteId)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = SQLNEWSLIST
				+ "where t.propertystring like '%person%' and t.propertystring like '%subadmins%' and "
				+ "( t.propertystring not like '%sites%' or (t.propertystring like '%sites%' and t.propertystring like '%"
				+ siteId + "%')) ";
		List searchpropertys = new ArrayList();
		List orderpropertys = new ArrayList();
		searchpropertys.add(new SearchProperty("isactive", "1", "="));
		searchpropertys.add(new SearchProperty("isconfirm", "1", "="));
		searchpropertys.add(new SearchProperty("isactive", "1", "="));
		orderpropertys.add(new OrderProperty("istop", OrderProperty.DESC));
		orderpropertys
				.add(new OrderProperty("SUBMIT_DATE", OrderProperty.DESC));

		sql += Conditions
				.convertToAndCondition(searchpropertys, orderpropertys);
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
				OracleNews newsRecord = new OracleNews();
				newsRecord.setBody(rs.getString("body"));
				newsRecord.setId(rs.getString("id"));
				newsRecord.setPropertyString(rs.getString("propertystring"));
				newsRecord.setReportDate(rs.getString("report_date"));
				newsRecord.setSubmitDate(rs.getDate("submit_date"));
				newsRecord.setReporter(rs.getString("reporter"));
				newsRecord.setConfirm(rs.getString("isconfirm"));
				NewsStatus newsStatus = new NewsStatus();
				newsStatus.setActive(rs.getString("isactive").equals("1"));
				newsStatus.setPop(rs.getString("ispop").equals("1"));
				newsStatus.setTop(rs.getString("istop").equals("1"));
				newsStatus.setTopSequence(rs.getInt("top_sequence"));
				newsRecord.setStatus(newsStatus);
				// java.util.Date submitDate = new
				// java.util.Date(rs.getInt("submit_date"));
				// newsRecord.setSubmitDate(submitDate);
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
				OracleNewsType newsType = new OracleNewsType(rs
						.getString("news_type_id"));
				newsRecord.setType(newsType);
				records.add(newsRecord);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return records;

	}
	
	
	
	

	
	
	public List getNewsListForSubAdmin(Page page, String siteId,
			String newsTypeId) throws PlatformException {
		dbpool db = new dbpool();
		String sql = SQLNEWSLIST
				+ "where t.propertystring like '%person%' and t.propertystring like '%subadmins%' and "
				+ "( t.propertystring not like '%sites%' or (t.propertystring like '%sites%' and t.propertystring like '%"
				+ siteId + "%')) ";
		List searchpropertys = new ArrayList();
		List orderpropertys = new ArrayList();
		searchpropertys
				.add(new SearchProperty("news_type_id", newsTypeId, "="));
		searchpropertys.add(new SearchProperty("isactive", "1", "="));
		searchpropertys.add(new SearchProperty("isconfirm", "1", "="));
		searchpropertys.add(new SearchProperty("isactive", "1", "="));
		orderpropertys.add(new OrderProperty("istop", OrderProperty.DESC));
		orderpropertys
				.add(new OrderProperty("SUBMIT_DATE", OrderProperty.DESC));

		sql += Conditions
				.convertToAndCondition(searchpropertys, orderpropertys);
		MyResultSet rs = null;
		Connection con = null;
		ResultSet reSet = null;
		ArrayList records = null;

		try {
			records = new ArrayList();
			if (null != page) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
				
				con = db.getConn();
				PreparedStatement ps = con.prepareStatement(sql);
				reSet = ps.executeQuery();
			} else {
				rs = db.executeQuery(sql);
			}
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			while (null != rs && rs.next() && reSet != null && reSet.next()) {
				OracleNews newsRecord = new OracleNews();
				newsRecord.setBody(((OracleResultSet)reSet).getString("body"));
				newsRecord.setId(rs.getString("id"));
				newsRecord.setPropertyString(rs.getString("propertystring"));
				Date date = (Date)rs.getDate("report_date");
				newsRecord.setReportDate(sf.format(date));
				newsRecord.setSubmitDate(rs.getDate("submit_date"));
				newsRecord.setReporter(rs.getString("reporter"));
				newsRecord.setConfirm(rs.getString("isconfirm"));
				NewsStatus newsStatus = new NewsStatus();
				newsStatus.setActive(rs.getString("isactive").equals("1"));
				newsStatus.setPop(rs.getString("ispop").equals("1"));
				newsStatus.setTop(rs.getString("istop").equals("1"));
				newsStatus.setTopSequence(rs.getInt("top_sequence"));
				newsRecord.setStatus(newsStatus);
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
				OracleNewsType newsType = new OracleNewsType(rs
						.getString("news_type_id"));
				newsRecord.setType(newsType);
				records.add(newsRecord);
			}
		} catch (Exception e) {
			
		}  finally {
			try{
				db.close(rs);
				con.close();
				reSet = null;
				db = null;
			}catch(SQLException  e){
				throw new  PlatformException("database error");
			}
			
		}
		return records;

	}

	public int getNewsListNumForSubAdmin(String siteId)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = SQLNEWSLIST
				+ "where t.propertystring like '%person%' and t.propertystring like '%subadmins%' and "
				+ "( t.propertystring not like '%sites%' or (t.propertystring like '%sites%' and t.propertystring like '%"
				+ siteId + "%')) ";
		List searchpropertys = new ArrayList();
		List orderpropertys = new ArrayList();
		searchpropertys.add(new SearchProperty("isactive", "1", "="));
		searchpropertys.add(new SearchProperty("isconfirm", "1", "="));
		searchpropertys.add(new SearchProperty("isactive", "1", "="));
		sql += Conditions
				.convertToAndCondition(searchpropertys, orderpropertys);
		return db.countselect(sql);
	}

	public int getNewsListNumForSubAdmin(String siteId, String newsTypeId)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = SQLNEWSLIST
				+ "where t.propertystring like '%person%' and t.propertystring like '%subadmins%' and "
				+ "( t.propertystring not like '%sites%' or (t.propertystring like '%sites%' and t.propertystring like '%"
				+ siteId + "%')) ";
		List searchpropertys = new ArrayList();
		List orderpropertys = new ArrayList();
		searchpropertys
				.add(new SearchProperty("news_type_id", newsTypeId, "="));
		searchpropertys.add(new SearchProperty("isactive", "1", "="));
		searchpropertys.add(new SearchProperty("isconfirm", "1", "="));
		searchpropertys.add(new SearchProperty("isactive", "1", "="));
		sql += Conditions
				.convertToAndCondition(searchpropertys, orderpropertys);
		return db.countselect(sql);
	}

	public List getNewsListForStudent(Page page, Student student)
			throws PlatformException {
		dbpool db = new dbpool();
		String site = student.getStudentInfo().getSite_id();
		String grade = student.getStudentInfo().getGrade_id();
		String edu_type = student.getStudentInfo().getEdutype_id();
		String major = student.getStudentInfo().getMajor_id();

		// String sql = SQLNEWSLIST
		// + "where t.propertystring like '%person%' and t.propertystring like
		// '%students%' and "
		// + "( t.propertystring not like '%sites%' or (t.propertystring like
		// '%sites%' and t.propertystring like '%"
		// + site
		// + "%')) "
		// + "and ( t.propertystring not like '%grades%' or (t.propertystring
		// like '%grades%' and t.propertystring like '%"
		// + grade
		// + "%')) "
		// + "and ( t.propertystring not like '%majors%' or (t.propertystring
		// like '%majors%' and t.propertystring like '%"
		// + major
		// + "%')) "
		// + "and ( t.propertystring not like '%edu_types%' or (t.propertystring
		// like '%edu_types%' and t.propertystring like '%"
		// + edu_type + "%')) ";// + "order by t.report_date ASC";

		String sql = SQLNEWSLIST_NOTICE
				+ "where t.propertystring like '%person%' and t.propertystring like '%students%' and "
				+ "( t.propertystring not like '%sites%' or (t.propertystring like '%sites%' and substr(t.propertystring,instr(t.propertystring,'sites')+6,instr(t.propertystring,'|',instr(t.propertystring, 'sites') + 6,1)-instr(t.propertystring, 'sites') - 6)  like '%"
				+ site
				+ "%')) "
				+ "and ( t.propertystring not like '%grades%' or (t.propertystring like '%grades%' and substr(t.propertystring,instr(t.propertystring,'grades')+7,instr(t.propertystring,'|',instr(t.propertystring, 'grades') + 7,1)-instr(t.propertystring, 'grades') - 7) like '%"
				+ grade
				+ "%')) "
				+ "and ( t.propertystring not like '%majors%' or (t.propertystring like '%majors%' and substr(t.propertystring,instr(t.propertystring,'majors')+7,instr(t.propertystring,'|',instr(t.propertystring, 'majors') + 7,1)-instr(t.propertystring, 'majors') - 7) like '%"
				+ major
				+ "%')) "
				+ "and ( t.propertystring not like '%edu_types%' or (t.propertystring like '%edu_types%' and substr(t.propertystring,instr(t.propertystring,'edu_types')+10,instr(t.propertystring,'|',instr(t.propertystring, 'edu_types') + 10,1)-instr(t.propertystring, 'edu_types') -10) like '%"
				+ edu_type + "%')) ";// + "order by t.report_date ASC";

		List searchpropertys = new ArrayList();
		List orderpropertys = new ArrayList();
		searchpropertys.add(new SearchProperty("isactive", "1", "="));
		searchpropertys.add(new SearchProperty("isconfirm", "1", "="));
		searchpropertys.add(new SearchProperty("isactive", "1", "="));
		orderpropertys.add(new OrderProperty("istop", OrderProperty.DESC));
		orderpropertys
				.add(new OrderProperty("SUBMIT_DATE", OrderProperty.DESC));

		sql += Conditions
				.convertToAndCondition(searchpropertys, orderpropertys);
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
				OracleNews newsRecord = new OracleNews();
				newsRecord.setBody(rs.getString("body"));
				newsRecord.setId(rs.getString("id"));
				newsRecord.setPropertyString(rs.getString("propertystring"));
				newsRecord.setReportDate(rs.getString("report_date"));
				newsRecord.setSubmitDate(rs.getDate("submit_date"));
				newsRecord.setReporter(rs.getString("reporter"));
				newsRecord.setConfirm(rs.getString("isconfirm"));
				NewsStatus newsStatus = new NewsStatus();
				newsStatus.setActive(rs.getString("isactive").equals("1"));
				newsStatus.setPop(rs.getString("ispop").equals("1"));
				newsStatus.setTop(rs.getString("istop").equals("1"));
				newsStatus.setTopSequence(rs.getInt("top_sequence"));
				newsRecord.setStatus(newsStatus);
				// java.util.Date submitDate = new
				// java.util.Date(rs.getInt("submit_date"));
				// newsRecord.setSubmitDate(submitDate);
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
				OracleNewsType newsType = new OracleNewsType(rs
						.getString("news_type_id"));
				newsRecord.setType(newsType);
				records.add(newsRecord);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return records;

	}

	public int getNewsListNumForStudent(Student student)
			throws PlatformException {
		// TODO �Զ���ɷ������
		dbpool db = new dbpool();
		String site = student.getStudentInfo().getSite_id();
		String grade = student.getStudentInfo().getGrade_id();
		String edu_type = student.getStudentInfo().getEdutype_id();
		String major = student.getStudentInfo().getMajor_id();

		/*
		 * String sql = SQLNEWSLIST_NOTICE + "where t.propertystring like
		 * '%person%' and t.propertystring like '%students%' and " + "(
		 * t.propertystring not like '%sites%' or (t.propertystring like
		 * '%sites%' and t.propertystring like '%" + site + "%')) " + "and (
		 * t.propertystring not like '%grades%' or (t.propertystring like
		 * '%grades%' and t.propertystring like '%" + grade + "%')) " + "and (
		 * t.propertystring not like '%majors%' or (t.propertystring like
		 * '%majors%' and t.propertystring like '%" + major + "%')) " + "and (
		 * t.propertystring not like '%edu_types%' or (t.propertystring like
		 * '%edu_types%' and t.propertystring like '%" + edu_type + "%')) ";// +
		 * "order by t.report_date ASC";
		 */
		String sql = SQLNEWSLIST_NOTICE
				+ "where t.propertystring like '%person%' and t.propertystring like '%students%' and "
				+ "( t.propertystring not like '%sites%' or (t.propertystring like '%sites%' and substr(t.propertystring,instr(t.propertystring,'sites')+6,instr(t.propertystring,'|',instr(t.propertystring, 'sites') + 6,1)-instr(t.propertystring, 'sites') - 6)  like '%"
				+ site
				+ "%')) "
				+ "and ( t.propertystring not like '%grades%' or (t.propertystring like '%grades%' and substr(t.propertystring,instr(t.propertystring,'grades')+7,instr(t.propertystring,'|',instr(t.propertystring, 'grades') + 7,1)-instr(t.propertystring, 'grades') - 7) like '%"
				+ grade
				+ "%')) "
				+ "and ( t.propertystring not like '%majors%' or (t.propertystring like '%majors%' and substr(t.propertystring,instr(t.propertystring,'majors')+7,instr(t.propertystring,'|',instr(t.propertystring, 'majors') + 7,1)-instr(t.propertystring, 'majors') - 7) like '%"
				+ major
				+ "%')) "
				+ "and ( t.propertystring not like '%edu_types%' or (t.propertystring like '%edu_types%' and substr(t.propertystring,instr(t.propertystring,'edu_types')+10,instr(t.propertystring,'|',instr(t.propertystring, 'edu_types') + 10,1)-instr(t.propertystring, 'edu_types') -10) like '%"
				+ edu_type + "%')) ";
		List searchpropertys = new ArrayList();
		List orderpropertys = new ArrayList();
		searchpropertys.add(new SearchProperty("isactive", "1", "="));
		searchpropertys.add(new SearchProperty("isconfirm", "1", "="));
		searchpropertys.add(new SearchProperty("isactive", "1", "="));
		sql += Conditions
				.convertToAndCondition(searchpropertys, orderpropertys);
		return db.countselect(sql);
	}

	public int getNewsByTagNum(String tag) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select ID,TITLE,short_title,pic_link,confirm_manager_id, confirm_manager_name, read_count, REPORTER,REPORT_DATE,BODY,SUBMIT_DATE,SUBMIT_MANAGER_ID,SUBMIT_MANAGER_NAME,"
				+ "NEWS_TYPE_ID,ISACTIVE,ISTOP,TOP_SEQUENCE,ISPOP from info_news where news_type_id in ( "
				+ "select id from info_news_type where tag ='"
				+ tag
				+ "') and isconfirm = '1' and isactive = '1'";
		return db.countselect(sql);
	}

	public List getNewsByTag(Page page, String tag) throws PlatformException {
		List childList = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select ID,TITLE,short_title,pic_link,confirm_manager_id, confirm_manager_name, read_count, REPORTER,REPORT_DATE,BODY,SUBMIT_DATE,SUBMIT_MANAGER_ID,SUBMIT_MANAGER_NAME,"
				+ "NEWS_TYPE_ID,ISACTIVE,ISTOP,TOP_SEQUENCE,ISPOP from info_news where news_type_id in ( "
				+ "select id from info_news_type where tag ='"
				+ tag
				+ "') and isconfirm = '1' and isactive = '1' order by submit_date desc";
		InfoLog.setDebug(sql);
		try {
			if (page == null)
				rs = db.executeQuery(sql);
			else
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			while (rs != null && rs.next()) {
				OracleNews news = new OracleNews();
				news.setId(rs.getString("id"));
				news.setTitle(rs.getString("title"));
				news.setShortTitle(rs.getString("short_title"));
				news.setPicLink(rs.getString("pic_link"));
				news.setConfirmManagerId(rs.getString("confirm_manager_id"));
				news
						.setConfirmManagerName(rs
								.getString("confirm_manager_name"));
				news.setReadCount(rs.getInt("read_count"));
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
				news.setStatus(newsStatus);
				childList.add(news);
			}
		} catch (java.sql.SQLException e) {
			InfoLog.setError("OracleClasse(String aid) error " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return childList;
	}

	public int getNewsByTagListNum(List tagList) throws PlatformException {
		if (tagList == null || tagList.size() == 0)
			return 0;
		String in = "'" + tagList.get(0) + "'";
		for (int i = 1; i < tagList.size(); i++) {
			in += ",'" + tagList.get(i) + "'";
		}
		dbpool db = new dbpool();
		String sql = "";
		sql = "select ID,TITLE,short_title,pic_link,confirm_manager_id, confirm_manager_name, read_count, REPORTER,REPORT_DATE,BODY,SUBMIT_DATE,SUBMIT_MANAGER_ID,SUBMIT_MANAGER_NAME,"
				+ "NEWS_TYPE_ID,ISACTIVE,ISTOP,TOP_SEQUENCE,ISPOP from info_news where news_type_id in ( "
				+ "select id from info_news_type where tag in("
				+ in
				+ ")) and isconfirm = '1' and isactive = '1' order by istop desc, submit_date desc";
		InfoLog.setDebug("getNewsByTagListNum=" + sql);
		return db.countselect(sql);
	}

	public List getNewsByTagList(Page page, List tagList)
			throws PlatformException {
		if (tagList == null || tagList.size() == 0)
			return null;
		String in = "'" + tagList.get(0) + "'";
		for (int i = 1; i < tagList.size(); i++) {
			in += ",'" + tagList.get(i) + "'";
		}
		List childList = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select  type_id, type_name, type_tag,ID,TITLE,short_title,pic_link,confirm_manager_id, confirm_manager_name, read_count, REPORTER,REPORT_DATE,BODY,SUBMIT_DATE,SUBMIT_MANAGER_ID,SUBMIT_MANAGER_NAME,"
				+ "NEWS_TYPE_ID,ISACTIVE,ISTOP,TOP_SEQUENCE,ISPOP from info_news a, ( "
				+ "select  id as type_id, name as type_name, tag as type_tag from info_news_type where tag in("
				+ in
				+ ")) b where a.news_type_id = b.type_id and isconfirm = '1' and isactive = '1' order by istop desc,  REPORT_DATE desc";
		InfoLog.setDebug("getNewsByTagList=" + sql);
		try {
			if (page == null)
				rs = db.executeQuery(sql);
			else
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			while (rs != null && rs.next()) {
				OracleNews news = new OracleNews();
				OracleNewsType newsType = new OracleNewsType();
				newsType.setId(rs.getString("type_id"));
				newsType.setName(rs.getString("type_name"));
				newsType.setTag(rs.getString("type_tag"));
				news.setId(rs.getString("id"));
				news.setTitle(rs.getString("title"));
				news.setShortTitle(rs.getString("short_title"));
				news.setPicLink(rs.getString("pic_link"));
				news.setReportDate(rs.getString("REPORT_DATE"));
				news.setConfirmManagerId(rs.getString("confirm_manager_id"));
				news
						.setConfirmManagerName(rs
								.getString("confirm_manager_name"));
				news.setReadCount(rs.getInt("read_count"));
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
				news.setStatus(newsStatus);
				news.setType(newsType);
				childList.add(news);
			}
		} catch (java.sql.SQLException e) {
			InfoLog.setError("OracleClasse(String aid) error " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return childList;
	}

	public List getNewsByTag(String tag, int num) throws PlatformException {
		List list = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select ID,TITLE,short_title,pic_link,confirm_manager_id, confirm_manager_name, read_count,REPORTER,REPORT_DATE,BODY,SUBMIT_DATE,SUBMIT_MANAGER_ID,SUBMIT_MANAGER_NAME,PROPERTYSTRING,"
				+ "NEWS_TYPE_ID,ISACTIVE,ISTOP,TOP_SEQUENCE,ISPOP from info_news where news_type_id in ( "
				+ "select id from info_news_type where tag ='"
				+ tag
				+ "') and isconfirm = '1' and isactive = '1'  order by submit_date desc";
		Page page = new Page();
		page.setPageSize(num);
		page.setTotalnum(getNewsByTagNum(tag));
		InfoLog.setDebug(sql);
		try {
			if (page == null)
				rs = db.executeQuery(sql);
			else
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			while (rs != null && rs.next()) {
				OracleNews news = new OracleNews();
				news.setBody(rs.getString("body"));
				news.setId(rs.getString("id"));
				news.setTitle(rs.getString("title"));
				news.setShortTitle(rs.getString("short_title"));
				news.setPicLink(rs.getString("pic_link"));
				news.setConfirmManagerId(rs.getString("confirm_manager_id"));
				news
						.setConfirmManagerName(rs
								.getString("confirm_manager_name"));
				news.setReadCount(rs.getInt("read_count"));
				news.setReporter(rs.getString("REPORTER"));
				news.setReportDate(rs.getString("REPORT_DATE"));
				news.setSubmitDate(rs.getDate("SUBMIT_DATE"));
				news.setSubmitManagerId(rs.getString("SUBMIT_MANAGER_ID"));
				news.setSubmitManagerName(rs.getString("SUBMIT_MANAGER_NAME"));
				news.setPropertyString(rs.getString("PROPERTYSTRING"));
				OracleNewsType newsType = new OracleNewsType(rs
						.getString("news_type_id"));
				news.setType(newsType);
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
				news.setStatus(newsStatus);

				list.add(news);
			}
		} catch (java.sql.SQLException e) {
			InfoLog.setError("OracleClasse(String aid) error " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public Map getNewsByTags(Map tagAndNumber) throws PlatformException {
		Iterator it = tagAndNumber.keySet().iterator();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		Map result = new HashMap();
		while (it.hasNext()) {
			String tag = ((String) it.next()).trim();
			result.put(tag, new ArrayList());
			Integer num = (Integer) (tagAndNumber.get(tag));
			// ��body
			sql += " union   select '"
					+ tag
					+ "' as tag, ID,TITLE,short_title,pic_link,confirm_manager_id, confirm_manager_name, read_count, REPORTER,REPORT_DATE,SUBMIT_DATE,SUBMIT_MANAGER_ID,SUBMIT_MANAGER_NAME,NEWS_TYPE_ID,ISACTIVE,"
					+ "ISTOP,TOP_SEQUENCE,ISPOP,news_type_name,news_type_note,news_type_parentid,news_type_status "
					+ "from (select inf.ID as id,inf.TITLE as title,inf.short_title as short_title,inf.pic_link as pic_link, inf.confirm_manager_id, inf.confirm_manager_name, inf.read_count, inf.REPORTER as reporter,"
					+ "inf.REPORT_DATE as report_date,inf.SUBMIT_DATE as submit_date,"
					+ "inf.SUBMIT_MANAGER_ID as submit_manager_id,SUBMIT_MANAGER_NAME as SUBMIT_MANAGER_NAME,inf.NEWS_TYPE_ID as news_type_id,"
					+ "inf.ISACTIVE as isactive,inf.ISTOP as istop,inf.TOP_SEQUENCE as top_sequence,inf.ISPOP as ispop,"
					+ "int.name as news_type_name,int.note as news_type_note,int.parent_id as news_type_parentid,"
					+ "int.status as news_type_status from info_news inf,info_news_type int where inf.news_type_id in "
					+ "(select id from info_news_type where tag ='"
					+ tag
					+ "') and inf.isconfirm = '1' and inf.isactive = '1' and inf.news_type_id=int.id "
					+ " order by istop desc, report_date desc, SUBMIT_DATE desc) where rownum<="
					+ num;
		}
		if (sql.equals(""))
			return null;
		else {
			sql = sql.substring(6);
			sql = "select tag, ID,TITLE,short_title, pic_link,confirm_manager_id, confirm_manager_name, read_count, REPORTER,REPORT_DATE,SUBMIT_DATE,SUBMIT_MANAGER_ID,SUBMIT_MANAGER_NAME,NEWS_TYPE_ID,ISACTIVE,"
					+ "ISTOP,TOP_SEQUENCE,ISPOP,news_type_name,news_type_note,news_type_parentid,news_type_status "
					+ "from ("
					+ sql
					+ " )order by istop desc, report_date desc, SUBMIT_DATE desc";
		}

		InfoLog.setDebug("sql=" + sql);
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleNews news = new OracleNews();
				// news.setBody(rs.getString("body"));
				news.setId(rs.getString("id"));
				news.setTitle(rs.getString("title"));
				news.setShortTitle(rs.getString("short_title"));
				news.setPicLink(rs.getString("pic_link"));
				news.setConfirmManagerId(rs.getString("confirm_manager_id"));
				news
						.setConfirmManagerName(rs
								.getString("confirm_manager_name"));
				news.setReadCount(rs.getInt("read_count"));
				news.setReporter(rs.getString("REPORTER"));
				news.setReportDate(rs.getString("REPORT_DATE"));
				news.setSubmitDate(rs.getDate("SUBMIT_DATE"));
				news.setSubmitManagerId(rs.getString("SUBMIT_MANAGER_ID"));
				news.setSubmitManagerName(rs.getString("SUBMIT_MANAGER_NAME"));
				OracleNewsType newsType = new OracleNewsType();
				newsType.setId(rs.getString("news_type_id"));
				newsType.setName(rs.getString("news_type_name"));
				newsType.setNote(rs.getString("news_type_note"));
				newsType.setParentId(rs.getString("news_type_parentid"));
				newsType.setStatus(rs.getString("news_type_status"));
				news.setType(newsType);
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
				news.setStatus(newsStatus);

				// ��ӵ�Map
				String tag = rs.getString("tag").trim();
				if (tag == null)
					continue;
				else {
					((List) result.get(tag)).add(news);
				}
			}
		} catch (java.sql.SQLException e) {
			InfoLog.setError("OracleClasse(String aid) error " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return result;
	}

	public List getChildNewsTypeByTag(String tag) throws PlatformException {

		List childList = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,name,note, parent_id,tag from info_news_type where parent_id in (select id from info_news_type where tag='"
				+ tag + "') order by name";
		InfoLog.setDebug(sql);
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleNewsType newsType = new OracleNewsType();
				newsType.setId(rs.getString("id"));
				newsType.setName(rs.getString("name"));
				newsType.setNote(rs.getString("note"));
				newsType.setParentId(rs.getString("parent_id"));
				newsType.setTag(rs.getString("tag"));
				childList.add(newsType);
			}
		} catch (java.sql.SQLException e) {
			InfoLog.setError("getNewsTypeByTag(String aid) error " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return childList;
	}

	public NewsType getNewsTypeByTag(String tag) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		NewsType newsType = null;
		String sql = "";
		sql = "select id,name,note, parent_id,tag from info_news_type where tag='"
				+ tag + "'";
		InfoLog.setDebug(sql);
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				newsType = new OracleNewsType();
				newsType.setId(rs.getString("id"));
				newsType.setName(rs.getString("name"));
				newsType.setNote(rs.getString("note"));
				newsType.setParentId(rs.getString("parent_id"));
				newsType.setTag(rs.getString("tag"));
			}
		} catch (java.sql.SQLException e) {
			InfoLog.setError("getNewsTypeByTag(String aid) error " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return newsType;
	}

	/**
	 * Ϊû�з�վ֪ͨ�������͵�վ�㴴��֪ͨ���������Լ���ǩ ��ǩ����site_id + "��վ֪ͨ";
	 * 
	 * ��վ֪ͨ�ĸ�����Ϊһ�� tag="��վ֪ͨ"����������
	 * 
	 * @param newsTypeId
	 * @return
	 * @throws PlatformException
	 */
	public int createNewsTypeForSiteNotice() throws PlatformException {
		String sql = "select id from info_news_type where tag='��վ֪ͨ'";
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String parent_id = "";
		InfoLog.setDebug(sql);
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				parent_id = rs.getString("id");
			}
		} catch (java.sql.SQLException e) {
			InfoLog.setError("createNewsTypeForSiteNotice() error: " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		if (parent_id == null || parent_id.equals(""))
			throw new PlatformException("û�н�b��ǩΪ'��վ֪ͨ'����������");
		// ������δ��b��վ֪ͨ���͵�վ��
		sql = "select s_news_type_id.nextval, concat(name,'��վ֪ͨ'),concat(name,'�ķ�վ֪ͨ'),'"
				+ parent_id
				+ "', '1', concat('��վ֪ͨ:', id) from entity_site_info where concat('��վ֪ͨ:', id) "
				+ "in (select concat('��վ֪ͨ:', id) as tag  from entity_site_info "
				+ "minus select tag from info_news_type)";
		sql = "insert into info_news_type (id, name, note, parent_id, status, tag) "
				+ sql;
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleInfoList.createNewsTypeForSiteNotice() SQL="
				+ sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int createNewsTypeForSiteNews(String[] existNewsTypes)
			throws PlatformException {
		if (existNewsTypes == null || existNewsTypes.length < 1)
			return 0;
		int count = 0;
		for (int i = 0; i < existNewsTypes.length; i++)
			if (createNewsTypeForSiteNews(existNewsTypes[i]) > 0)
				count++;
		return count;
	}

	private int createNewsTypeForSiteNews(String newsTag)
			throws PlatformException {
		String sql = "select id from info_news_type where tag='" + newsTag
				+ "'";
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String parent_id = "";
		InfoLog.setDebug(sql);
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				parent_id = rs.getString("id");
			}
		} catch (java.sql.SQLException e) {
			InfoLog
					.setError("createNewsTypeForSiteNews1(String newsTag) error: "
							+ sql);
		} finally {
			db.close(rs);
		}
		if (parent_id == null || parent_id.equals(""))
			throw new PlatformException("û�н�b��ǩΪ'" + newsTag + "'����������");
		// ������δ��b��վ֪ͨ���͵�վ��
		sql = "select s_news_type_id.nextval, concat(name,'" + newsTag
				+ "'),concat(name,'" + newsTag + "'),'" + parent_id
				+ "', '1', concat('" + newsTag
				+ ":', id) from entity_site_info where concat('" + newsTag
				+ ":', id) " + "in (select concat('" + newsTag
				+ ":', id) as tag  from entity_site_info "
				+ "minus select tag from info_news_type)";
		sql = "insert into info_news_type (id, name, note, parent_id, status, tag) "
				+ sql;

		return db.executeUpdate(sql);
	}

	public List getRightNewsTypes(String userId) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select news_type_id from info_user_right where user_id = '"
				+ userId + "'";
		List newsTypeIds = new ArrayList();
		MyResultSet rs = null;
		try {

			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				newsTypeIds.add(rs.getString("news_type_id"));
			}
		} catch (java.sql.SQLException e) {
			InfoLog.setError("OracleClasse(String aid) error " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return newsTypeIds;
	}

	public int updateInfoRight(String userId, String[] pageNewsTypeIds,
			String[] newsTypeIds) throws PlatformException {
		if (userId == null || userId.equals("null"))
			return 0;
		String sql1 = "";
		String sql2 = "";
		int index = 1;
		Hashtable ht = new Hashtable();
		if (pageNewsTypeIds != null && pageNewsTypeIds.length > 0) {
			sql1 = "delete from info_user_right where user_id = '" + userId
					+ "' and news_type_id in (";
			for (int i = 0; i < pageNewsTypeIds.length; i++) {
				if (i < pageNewsTypeIds.length - 1)
					sql1 += "'" + pageNewsTypeIds[i] + "',";
				else
					sql1 += "'" + pageNewsTypeIds[i] + "')";
			}

			ht.put(new Integer(index++), sql1);
		}
		if (newsTypeIds != null && newsTypeIds.length > 0) {
			for (int i = 0; i < newsTypeIds.length; i++) {
				sql2 = "insert into info_user_right (id, user_id, news_type_id) values (to_char(s_user_right_id.nextval), '"
						+ userId + "', '" + newsTypeIds[i] + "')";
				ht.put(new Integer(index++), sql2);

			}
		}
		dbpool db = new dbpool();
		UserDeleteLog
				.setDebug("OracleInfoList.updateInfoRight(String userId, String[] pageNewsTypeIds,String[] newsTypeIds) SQL="
						+ sql1 + " DATE=" + new Date());
		UserAddLog
				.setDebug("OracleInfoList.updateInfoRight(String userId, String[] pageNewsTypeIds,String[] newsTypeIds) SQL="
						+ sql2 + " DATE=" + new Date());
		return db.executeUpdateBatch(ht);
	}

	public int deleteSiteInfomanagerPriv(String userId, String siteId)
			throws PlatformException {
		String sql0 = "select id from info_user_right where user_id='" + userId
				+ "'";
		int count = 0;
		String sql = "delete from info_user_right where user_id = '" + userId
				+ "'";
		dbpool db = new dbpool();

		count = db.executeUpdate(sql);
		UserDeleteLog
				.setDebug("OracleInfoList.deleteSiteInfomanagerPriv(String userId, String siteId) SQL="
						+ sql + " COUNT=" + count + " DATE=" + new Date());
		if (count > 0)
			return count;
		else if (db.countselect(sql0) < 1)
			return -1;
		else
			return 0;
	}

	/*
	 * ԭinfomanage����� public List getNews(Page page, String tag) throws
	 * PlatformException { List childList = new ArrayList(); dbpool db = new
	 * dbpool(); MyResultSet rs = null; String sql = ""; sql = "select ID,TITLE,
	 * short_title,REPORTER,REPORT_DATE,BODY,SUBMIT_DATE,SUBMIT_MANAGER_ID,SUBMIT_MANAGER_NAME," +
	 * "NEWS_TYPE_ID,ISACTIVE,ISTOP,TOP_SEQUENCE,ISPOP from info_news where
	 * news_type_id in ( " + "select id from info_news_type where tag ='" + tag +
	 * "') and isconfirm = '1' and isactive = '1' order by submit_date desc";
	 * InfoLog.setDebug(sql); try { if (page == null) rs = db.executeQuery(sql);
	 * else rs = db.execute_oracle_page(sql, page.getPageInt(), page
	 * .getPageSize()); while (rs != null && rs.next()) { OracleNews news = new
	 * OracleNews(); news.setId(rs.getString("id"));
	 * news.setTitle(rs.getString("title"));
	 * news.setShortTitle(rs.getString("short_title")); NewsStatus newsStatus =
	 * new NewsStatus(); if (rs.getInt("ISACTIVE") > 0) {
	 * newsStatus.setActive(true); } else { newsStatus.setActive(false); } if
	 * (rs.getInt("ISTOP") > 0) { newsStatus.setTop(true);
	 * newsStatus.setTopSequence(rs.getInt("TOP_SEQUENCE")); } else {
	 * newsStatus.setTop(false); } if (rs.getInt("ISPOP") > 0) {
	 * newsStatus.setPop(true); } else { newsStatus.setPop(false); }
	 * news.setStatus(newsStatus); childList.add(news); } } catch
	 * (java.sql.SQLException e) { InfoLog.setError("OracleClasse(String aid)
	 * error " + sql); } finally { db.close(rs); db = null; } return childList; }
	 * 
	 * public List getNews(String tag, int num) throws PlatformException { List
	 * list = new ArrayList(); dbpool db = new dbpool(); MyResultSet rs = null;
	 * String sql = ""; sql = "select ID,TITLE,short_title,
	 * REPORTER,REPORT_DATE,BODY,SUBMIT_DATE,SUBMIT_MANAGER_ID,PROPERTYSTRING," +
	 * "NEWS_TYPE_ID,ISACTIVE,ISTOP,TOP_SEQUENCE,ISPOP from info_news where
	 * news_type_id in ( " + "select id from info_news_type where tag ='" + tag +
	 * "') and isconfirm = '1' and isactive = '1' order by submit_date desc";
	 * System.out.println(sql); Page page = new Page(); page.setPageSize(num);
	 * page.setTotalnum(getNewsNum(tag)); InfoLog.setDebug(sql); try { if (page ==
	 * null) rs = db.executeQuery(sql); else rs = db.execute_oracle_page(sql,
	 * page.getPageInt(), page .getPageSize()); while (rs != null && rs.next()) {
	 * OracleNews news = new OracleNews(); news.setBody(rs.getString("body"));
	 * news.setId(rs.getString("id")); news.setTitle(rs.getString("title"));
	 * news.setShortTitle(rs.getString("short_title"));
	 * news.setReporter(rs.getString("REPORTER"));
	 * news.setReportDate(rs.getString("REPORT_DATE"));
	 * news.setSubmitDate(rs.getDate("SUBMIT_DATE"));
	 * news.setSubmitManagerId(rs.getString("SUBMIT_MANAGER_ID"));
	 * news.setPropertyString(rs.getString("PROPERTYSTRING")); OracleNewsType
	 * newsType = new OracleNewsType(rs .getString("news_type_id"));
	 * news.setType(newsType); NewsStatus newsStatus = new NewsStatus(); if
	 * (rs.getInt("ISACTIVE") > 0) { newsStatus.setActive(true); } else {
	 * newsStatus.setActive(false); } if (rs.getInt("ISTOP") > 0) {
	 * newsStatus.setTop(true);
	 * newsStatus.setTopSequence(rs.getInt("TOP_SEQUENCE")); } else {
	 * newsStatus.setTop(false); } if (rs.getInt("ISPOP") > 0) {
	 * newsStatus.setPop(true); } else { newsStatus.setPop(false); }
	 * news.setStatus(newsStatus);
	 * 
	 * list.add(news); } } catch (java.sql.SQLException e) {
	 * InfoLog.setError("OracleClasse(String aid) error " + sql); } finally {
	 * db.close(rs); db = null; } return list; }
	 * 
	 * public int getNewsNum(String tag) throws PlatformException { dbpool db =
	 * new dbpool(); String sql = ""; sql = "select
	 * ID,TITLE,short_title,REPORTER,REPORT_DATE,BODY,SUBMIT_DATE,SUBMIT_MANAGER_ID," +
	 * "NEWS_TYPE_ID,ISACTIVE,ISTOP,TOP_SEQUENCE,ISPOP from info_news where
	 * news_type_id in ( " + "select id from info_news_type where tag ='" + tag +
	 * "') and isconfirm = '1' and isactive = '1'"; return db.countselect(sql); }
	 * public Map getNews(Map tagAndNumber) throws PlatformException { Iterator
	 * it = tagAndNumber.keySet().iterator(); dbpool db = new dbpool();
	 * MyResultSet rs = null; String sql = ""; Map result = new HashMap(); while
	 * (it.hasNext()) { String tag = ((String) it.next()).trim();
	 * result.put(tag, new ArrayList()); Integer num = (Integer)
	 * (tagAndNumber.get(tag)); // ��body sql += " union select '" + tag + "' as
	 * tag,
	 * ID,TITLE,short_title,REPORTER,REPORT_DATE,SUBMIT_DATE,SUBMIT_MANAGER_ID," +
	 * "NEWS_TYPE_ID,ISACTIVE,ISTOP,TOP_SEQUENCE,ISPOP from " + "(select
	 * ID,TITLE,short_title,REPORTER,REPORT_DATE,SUBMIT_DATE,SUBMIT_MANAGER_ID," +
	 * "NEWS_TYPE_ID,ISACTIVE,ISTOP,TOP_SEQUENCE,ISPOP from info_news where
	 * news_type_id in ( " + "select id from info_news_type where tag ='" + tag +
	 * "') and isconfirm = '1' and isactive = '1' order by istop desc,
	 * submit_date desc" + ") where rownum<=" + num; }
	 * System.out.println(result); if (sql.equals("")) return null; else sql =
	 * sql.substring(6); System.out.println("sql=" + sql); try { rs =
	 * db.executeQuery(sql); while (rs != null && rs.next()) { OracleNews news =
	 * new OracleNews(); // news.setBody(rs.getString("body"));
	 * news.setId(rs.getString("id")); news.setTitle(rs.getString("title"));
	 * news.setShortTitle(rs.getString("short_title"));
	 * news.setReporter(rs.getString("REPORTER"));
	 * news.setReportDate(rs.getString("REPORT_DATE"));
	 * news.setSubmitDate(rs.getDate("SUBMIT_DATE"));
	 * news.setSubmitManagerId(rs.getString("SUBMIT_MANAGER_ID"));
	 * OracleNewsType newsType = new OracleNewsType(rs
	 * .getString("news_type_id")); news.setType(newsType); NewsStatus
	 * newsStatus = new NewsStatus(); if (rs.getInt("ISACTIVE") > 0) {
	 * newsStatus.setActive(true); } else { newsStatus.setActive(false); } if
	 * (rs.getInt("ISTOP") > 0) { newsStatus.setTop(true);
	 * newsStatus.setTopSequence(rs.getInt("TOP_SEQUENCE")); } else {
	 * newsStatus.setTop(false); } if (rs.getInt("ISPOP") > 0) {
	 * newsStatus.setPop(true); } else { newsStatus.setPop(false); }
	 * news.setStatus(newsStatus); // ��ӵ�Map String tag =
	 * rs.getString("tag").trim(); System.out.println("tag='" + tag + "'"); if
	 * (tag == null) continue; else { ((List) result.get(tag)).add(news); } } }
	 * catch (java.sql.SQLException e) { // 
	 * InfoLog.setError("OracleClasse(String aid) error " + sql); } finally {
	 * db.close(rs); db = null; } return result; }
	 * 
	 */

}
