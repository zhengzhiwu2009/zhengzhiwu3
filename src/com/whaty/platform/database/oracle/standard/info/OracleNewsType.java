/**
 * 
 */
package com.whaty.platform.database.oracle.standard.info;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.info.NewsStatus;
import com.whaty.platform.info.NewsType;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.InfoLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author chenjian
 * 
 */
public class OracleNewsType extends NewsType {

	public OracleNewsType() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OracleNewsType(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		// sql = "select id,name,note,parent_id,status from info_news_type where
		// id = '"
		// + id + "'";
		sql = "select a.id, a.name , a.note, a.parent_id, a.status, b.name as parent_name, a.tag from info_news_type a, info_news_type b where a.parent_id = b.id(+) and a.id = '"
				+ id + "'";
		// System.out.println(sql);
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setNote(rs.getString("note"));
				this.setParentId(rs.getString("parent_id"));
				this.setStatus(rs.getString("status"));
				this.setParentName(rs.getString("parent_name"));
				this.setTag(rs.getString("tag"));
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
	 * @see com.whaty.platform.Items#add()
	 */
	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into info_news_type (id,name,note,parent_id, status) values(to_char(S_NEWS_TYPE_ID.nextval),'"
				+ this.getName()
				+ "','"
				+ this.getNote()
				+ "','"
				+ this.getParentId() + "','" + this.getStatus() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleNewsType.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
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
		sql = "update info_news_type set name='" + this.getName() + "',note='"
				+ this.getNote() + "'," + "parent_id='" + this.getParentId()
				+ "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleNewsType.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#update()
	 */
	public int updateTag(String tag) throws PlatformException {
		if (tag == null || tag.equals("null"))
			tag = "";
		dbpool db = new dbpool();
		String sql = "";
		sql = "update  info_news_type set tag = '" + tag + "' where id = '"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleNewsType.updateTag(String tag) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
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
		sql = "select * from info_news_type where parent_id ='" + this.getId()
				+ "'";
		int count = db.countselect(sql);
		if (count < 1) {
			sql = "select * from info_news where news_type_id ='"
					+ this.getId() + "'";
			InfoLog.setDebug("OracleNewsType@Method:delete()="+sql);
			int count2 = db.countselect(sql);
			if (count2 < 1) {
				sql = "delete from info_news_type where id='" + this.getId()
						+ "'";
				int i = db.executeUpdate(sql);
				UserDeleteLog.setDebug("OracleNewsType.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
				return i;
			} else {
				throw new PlatformException("请先全部删除该类型的新闻！");
			}
		} else {
			throw new PlatformException("请首先删除子类型！");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.DirTree#getChildDirs()
	 */
	public List getChildNewsType(List searchproperty, List orderproperty)
			throws PlatformException {
		List childList = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,name,note from info_news_type where parent_id = '"
				+ this.getId() + "' ";
		sql = sql
				+ Conditions.convertToAndCondition(searchproperty,
						orderproperty);
		InfoLog.setDebug(sql);
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleNewsType newsType = new OracleNewsType();
				newsType.setId(rs.getString("id"));
				newsType.setName(rs.getString("name"));
				newsType.setNote(rs.getString("note"));
				newsType.setParentId(this.getId());
				childList.add(newsType);
			}
		} catch (java.sql.SQLException e) {
			InfoLog.setError("OracleClasse(String aid) error " + sql);
		} finally {
			db.close(rs);
			db = null;
			return childList;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.DirTree#getFatherDir()
	 */
	public NewsType getParent() {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		OracleNewsType newsType = new OracleNewsType();
		String sql = "";
		sql = "select id,name,note,parent_id, tag from info_news_type where id = '"
				+ this.getParentId() + "'";
//		InfoLog.setDebug(sql);
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				newsType.setId(rs.getString("id"));
				newsType.setName(rs.getString("name"));
				newsType.setNote(rs.getString("note"));
				newsType.setParentId(rs.getString("parent_id"));
				newsType.setTag(rs.getString("tag"));
			}
		} catch (java.sql.SQLException e) {
			InfoLog.setError("OracleClasse(String aid) error " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return newsType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.DirTree#isRootDir()
	 */
	public boolean isRoot() {
		return this.getId().equalsIgnoreCase("root");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.DirTree#moveTo(com.whaty.platform.DirTree)
	 */
	public void moveTo(NewsType parentNewsType) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update info_news_type set parent_id='" + parentNewsType.getId()
				+ "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleNewsType.moveTo(NewsType parentNewsType) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.NewsType#getManagerPrivList()
	 */
	public List getManagerPrivList() throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.NewsType#addManagerPriv(java.util.List)
	 */
	public void addManagerPriv(List managerPrivList) throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.NewsType#removeManagerPriv(java.util.List)
	 */
	public void removeManagerPriv(List managerPrivList)
			throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.NewsType#getNews(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List)
	 */
	public List getNews(Page page, List searchproperty, List orderproperty)
			throws PlatformException {
		List childList = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select ID,TITLE,REPORTER,REPORT_DATE,BODY,SUBMIT_DATE,SUBMIT_MANAGER_ID,"
				+ "NEWS_TYPE_ID,ISACTIVE,ISTOP,TOP_SEQUENCE,ISPOP from info_news where news_type_id ="
				+ " '" + this.getId() + "' ";
		sql = sql
				+ Conditions.convertToAndCondition(searchproperty,
						orderproperty);
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
			return childList;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.NewsType#getNewsNum(java.util.List)
	 */
	public int getNewsNum(List searchproperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select ID,TITLE,REPORTER,REPORT_DATE,BODY,SUBMIT_DATE,SUBMIT_MANAGER_ID,"
				+ "NEWS_TYPE_ID,ISACTIVE,ISTOP,TOP_SEQUENCE,ISPOP where news_type_id ="
				+ " '" + this.getId() + "' ";
		sql = sql + Conditions.convertToAndCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public int updateInfoRight(String[] pageManagerId,
			String[] selectedManagerId) throws PlatformException {
		if (this.getId() == null || this.getId().equals("null"))
			return 0;
		String sql1 = "";
		String sql2 = "";
		int index = 1;
		Hashtable ht = new Hashtable();
		if (pageManagerId != null && pageManagerId.length > 0) {
			sql1 = "delete from info_user_right where news_type_id = '"
					+ this.getId() + "' and user_id in (";
			for (int i = 0; i < pageManagerId.length; i++) {
				if (i < pageManagerId.length - 1)
					sql1 += "'" + pageManagerId[i] + "',";
				else
					sql1 += "'" + pageManagerId[i] + "')";
			}
			ht.put(new Integer(index++), sql1);
		}
		if (selectedManagerId != null && selectedManagerId.length > 0) {
			for (int i = 0; i < selectedManagerId.length; i++) {
				sql2 = "insert into info_user_right (id, user_id, news_type_id) values (to_char(s_user_right_id.nextval), '"
						+ selectedManagerId[i] + "', '" + this.getId() + "')";
				// System.out.println(index + "=" + sql2);
				ht.put(new Integer(index++), sql2);

			}
		}
		dbpool db = new dbpool();
		int i = db.executeUpdateBatch(ht);
		UserDeleteLog.setDebug("OracleNewsType.updateInfoRight(String[] pageManagerId,String[] selectedManagerId) SQL=" + sql1 + " DATE=" + new Date());
		UserAddLog.setDebug("OracleNewsType.updateInfoRight(String[] pageManagerId,String[] selectedManagerId) SQL=" + sql2 + " DATE=" + new Date());
		return i;
	}

	public List getRightUserIds() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select user_id from info_user_right where news_type_id = '"
				+ this.getId() + "'";
		List userIds = new ArrayList();
		MyResultSet rs = null;
		try {

			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				userIds.add(rs.getString("user_id"));
			}
		} catch (java.sql.SQLException e) {
			InfoLog.setError("OracleClasse(String aid) error " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return userIds;
	}
}
