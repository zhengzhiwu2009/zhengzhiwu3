/**
 * 
 */
package com.whaty.platform.database.oracle.standard.info.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.SiteManagerPriv;
import com.whaty.platform.info.user.InfoManagerPriv;
import com.whaty.platform.util.log.InfoLog;

/**
 * @author chenjian
 * 
 */
public class OracleInfoManagerPriv extends InfoManagerPriv {

	public OracleInfoManagerPriv() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleInfoManagerPriv(String managerId, String newsTypeId) {
		this.setManagerId(managerId);
		this.setNewsTypeId(newsTypeId);

	}
	

	public OracleInfoManagerPriv(String managerId) {
		this.setManagerId(managerId);
		String sql = "select id,  parent_id from info_news_type connect by prior id = parent_id "
				+ "start with id in (select news_type_id from info_user_right where user_id='"
				+ managerId + "')";
		String sql2 = "select news_type_id from info_user_right where user_id='"
				+ managerId + "'";
		dbpool db = new dbpool();
		List get = new ArrayList(), add = new ArrayList(), update = new ArrayList(), delete = new ArrayList();
		try {
			MyResultSet rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				String news_type_id = rs.getString("id");
				get.add(news_type_id);
				add.add(news_type_id);
				update.add(news_type_id);
				delete.add(news_type_id);
			}
			this.setNewsTypesGet(get);
			this.setNewsTypesAdd(add);
			this.setNewsTypesUpdate(update);
			this.setNewsTypesDelete(delete);
			db.close(rs);
			rs = db.executeQuery(sql2);
			if (rs != null && rs.next()) {
				this.setNewsTypeId(rs.getString("news_type_id"));
			}
			db.close(rs);
			this.addNews=1;
			this.confirmNews=1;
			this.copyNews=1;
			this.deleteNews=1;
			this.getNews=1;
			this.getNewsType=1;
			this.lockNews=1;
			this.topNews=1;
			this.updateNews=1;
			this.topNews=1;
			this.popNews=1;
			InfoLog.setDebug("OracleInfoManagerPriv:" + sql2);
		} catch (SQLException e) {
			InfoLog.setError("OracleInfoManagerPriv" + sql);
		}
	}
	
	
	/**
	 * 根据平台管理员权限构建信息管理权限
	 * @param managerId
	 */
	public OracleInfoManagerPriv(ManagerPriv priv) {
		
		this.setManagerId(priv.getSso_id());
		String sql = "select id,  parent_id from info_news_type connect by prior id = parent_id "
				+ "start with id in (select news_type_id from info_user_right where user_id='"
				+ priv.getSso_id() + "')";
		String sql2 = "select news_type_id from info_user_right where user_id='"
				+ priv.getSso_id() + "'";
		dbpool db = new dbpool();
		List get = new ArrayList(), add = new ArrayList(), update = new ArrayList(), delete = new ArrayList();
		try {
			MyResultSet rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				String news_type_id = rs.getString("id");
				get.add(news_type_id);
				add.add(news_type_id);
				update.add(news_type_id);
				delete.add(news_type_id);
			}
			this.setNewsTypesGet(get);
			this.setNewsTypesAdd(add);
			this.setNewsTypesUpdate(update);
			this.setNewsTypesDelete(delete);
			db.close(rs);
			rs = db.executeQuery(sql2);
			if (rs != null && rs.next()) {
				this.setNewsTypeId(rs.getString("news_type_id"));
			}
			db.close(rs);
			if(priv.getNews == 1){
				this.getNews = 1;
			}
			if(priv.addNews ==1){
				this.addNews = 1;
			}
			if(priv.updateNews == 1){
				this.updateNews = 1;
			}
			if(priv.deleteNews == 1){
				this.deleteNews = 1;
			}
			if(priv.topNews == 1){
				this.topNews = 1;
			}
			if(priv.popNews == 1){
				this.popNews = 1;
			}
			if(priv.lockNews == 1){
				this.lockNews = 1;
			}
			if(priv.confirmNews == 1){
				this.confirmNews = 1;
			}
			if(priv.copyNews == 1){
				this.copyNews = 1;
			}
			if(priv.getNewsType == 1){
				this.getNewsType = 1;
			}
			InfoLog.setDebug("OracleInfoManagerPriv:" + sql2);
		} catch (SQLException e) {
			InfoLog.setError("OracleInfoManagerPriv" + sql);
		}
		
	}
	
	/**
	 * 根据平台管理员权限构建信息管理权限
	 * @param managerId
	 */
	public OracleInfoManagerPriv(SiteManagerPriv priv) {
		
		this.setManagerId(priv.getSso_id());
		String sql = "select id,  parent_id from info_news_type connect by prior id = parent_id "
				+ "start with id in (select news_type_id from info_user_right where user_id='"
				+ priv.getSso_id() + "')";
		String sql2 = "select news_type_id from info_user_right where user_id='"
				+ priv.getSso_id() + "'";
		dbpool db = new dbpool();
		List get = new ArrayList(), add = new ArrayList(), update = new ArrayList(), delete = new ArrayList();
		try {
			MyResultSet rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				String news_type_id = rs.getString("id");
				get.add(news_type_id);
				add.add(news_type_id);
				update.add(news_type_id);
				delete.add(news_type_id);
			}
			this.setNewsTypesGet(get);
			this.setNewsTypesAdd(add);
			this.setNewsTypesUpdate(update);
			this.setNewsTypesDelete(delete);
			db.close(rs);
			rs = db.executeQuery(sql2);
			if (rs != null && rs.next()) {
				this.setNewsTypeId(rs.getString("news_type_id"));
			}
			db.close(rs);
			if(priv.getNews == 1){
				this.getNews = 1;
			}
			if(priv.addNews ==1){
				this.addNews = 1;
			}
			if(priv.updateNews == 1){
				this.updateNews = 1;
			}
			if(priv.deleteNews == 1){
				this.deleteNews = 1;
			}
			if(priv.topNews == 1){
				this.topNews = 1;
			}
			if(priv.popNews == 1){
				this.popNews = 1;
			}
			if(priv.lockNews == 1){
				this.lockNews = 1;
			}
			if(priv.confirmNews == 1){
				this.confirmNews = 1;
			}
			if(priv.copyNews == 1){
				this.copyNews = 1;
			}
			if(priv.getNewsType == 1){
				this.getNewsType = 1;
			}
			InfoLog.setDebug("OracleInfoManagerPriv:" + sql2);
		} catch (SQLException e) {
			InfoLog.setError("OracleInfoManagerPriv" + sql);
		}
		
	}
	
	

	public int putInfomanagerPriv(String userId, String siteId)
			throws PlatformException {
		String[] NewsTagsToCreateSiteType = { "分站通知", "分站最新公告", "分站招生信息",
				"分站教学安排", "分站考试安排" };
		String sql = "select id from info_news_type where tag in (";
		for (int i = 0; i < NewsTagsToCreateSiteType.length; i++) {
			sql += "concat('" + NewsTagsToCreateSiteType[i] + ":" + "','"
					+ siteId + "'),";
		}
		sql = sql.substring(0, sql.length() - 1) + ")";
		List newsTypes = new ArrayList();
		dbpool db = new dbpool();
		int count = 0;
		try {
			MyResultSet rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				String news_type_id = rs.getString("id");
				newsTypes.add(news_type_id);
			}
			db.close(rs);
			Iterator it = newsTypes.iterator();
			Hashtable ht = new Hashtable();
			int index = 1;
			sql = "delete from info_user_right where user_id = '" + userId + "'";
			InfoLog.setDebug("OracleInfoManagerPriv@Method:putInfomanagerPriv()=" + sql);
			ht.put(new Integer(index++), sql);
			while (it.hasNext()) {
				sql = "insert into info_user_right (id, user_id, news_type_id) values "
						+ " (to_char(s_user_right_id.nextval), '"
						+ userId
						+ "','" + (String) it.next() + "')";
				InfoLog.setDebug("OracleInfoManagerPriv@Method:putInfomanagerPriv()=" + sql);
				ht.put(new Integer(index++), sql);
			}
			count = db.executeUpdateBatch(ht);
		} catch (SQLException e) {
			InfoLog.setDebug("OracleInfoManagerPriv" + sql);
		}
		return count;
	}

	public static void main(String[] args) throws Exception {
		OracleInfoManagerPriv a = new OracleInfoManagerPriv();
		a.putInfomanagerPriv("delete", "1983");
	}
}
