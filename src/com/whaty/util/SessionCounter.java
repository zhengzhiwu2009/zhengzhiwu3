package com.whaty.util;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class SessionCounter implements HttpSessionListener {

	// private static Map<String, HttpSession> mp = new HashMap<String,
	// HttpSession>();

	// public static long getCount() {
	// long count =0;
	// dbpool db = new dbpool();
	// String sql_c="select num from COUNT_WEB where ID='1'";
	// try {
	// MyResultSet rs = db.executeQuery(sql_c);
	// if(rs.next()){
	// count = rs.getInt("num");
	// }
	// db.close(rs);
	// } catch (SQLException e) {
	// //e.printStackTrace();
	// }
	// return count;
	// }

	// public static long getOnLineCount() {
	// long count =0;
	// dbpool db = new dbpool();
	// String sql_c="select ONLINE_NUM from COUNT_WEB where ID='1'";
	// try {
	// MyResultSet rs = db.executeQuery(sql_c);
	// if(rs.next()){
	// count = rs.getInt("ONLINE_NUM");
	// }
	// db.close(rs);
	// } catch (SQLException e) {
	// //e.printStackTrace();
	// }
	// return count;
	// }

	/*
	 * 网站计数器
	 */
	public void sessionCreated(HttpSessionEvent se) {
		// dbpool db = new dbpool();
		// String sql="update COUNT_WEB set NUM = NUM+1,ONLINE_NUM=ONLINE_NUM+1
		// where ID='1'";
		//		
		// db.executeUpdate(sql);
		// activeSessions++;
	}

	/*
	 * 被动注销上次登陆用户
	 */
	public void sessionDestroyed(HttpSessionEvent se) {
		// String sql="update COUNT_WEB set ONLINE_NUM=ONLINE_NUM-1 where ID='1'
		// and ONLINE_NUM>0";
		// dbpool db = new dbpool();
		// db.executeUpdate(sql);
		// UserSession
		// user=(UserSession)se.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		// if(user!=null){
		//			
		// String loginId = user.getLoginId();
		// String de_sql="delete from sso_user_online where
		// login_id='"+loginId+"' or
		// login_session_id='"+se.getSession().getId()+"'";
		// db.executeUpdate(de_sql);
		// db=null;
		// }
	}

	/*
	 * 主动创建
	 */
	public static void Createsession(HttpSession se, String ip) {
		// UserSession user1 = (UserSession)
		// se.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		// String loginId = user1.getLoginId();
		// String sessionId = se.getId();
		// String evId = new HttpSessionEvent(se).getSession().getId();
		// System.out.println("sessionId=" + sessionId);
		// System.out.println("evId=" + evId);
//		dbpool db = new dbpool();
		// String sql_find = "select * from sso_user_online where login_id='" +
		// loginId + "'";
		// int isLogin = db.countselect(sql_find);
		// if (isLogin > 0) {// 该账号已经登录
		// String up_sql = "update sso_user_online set
		// login_date=sysdate,login_ip='" + ip + "',login_session_id='" +
		// sessionId
		// + "' where login_id='" + loginId + "'";
		// System.out.println("up_sql=" + up_sql);
		// db.executeUpdate(up_sql);
		// } else {
		// String in_sql = "insert into
		// sso_user_online(login_id,login_ip,login_session_id) values ('" +
		// loginId + "','" + ip + "','"
		// + sessionId + "')";
		// int count = db.executeUpdate(in_sql);
		// }
//		int activeSessions = getOnlineNum();
		// String sql_count = "select login_id from sso_user_online";
		// try {
		// activeSessions = db.countselect(sql_count);
		// } catch (Exception e) {
		// activeSessions = 500;
		// }

//		MyResultSet rs = null;
		// String cur_date = "";
		// String sql_date = "select to_char(sysdate,'yyyy-mm-dd') as cur_date
		// from dual";
//		try {
			// rs = db.executeQuery(sql_date);
			// if (rs != null && rs.next()) {
			// cur_date = rs.getString("cur_date");
			// }
			// db.close(rs);

//			String t_sql = "select max_num from STAT_MAX_ONLINENUM where stat_date=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')";
//			if (db.countselect(t_sql) > 0) {
//				int max_num = 0;
//				rs = db.executeQuery(t_sql);
//				if (rs != null && rs.next()) {
//					max_num = rs.getInt("max_num");
//				}
//				db.close(rs);
//				if (activeSessions > max_num) {
//					max_num = activeSessions;
//					String up_sql = "update STAT_MAX_ONLINENUM set max_num=" + max_num
//							+ ",update_date=sysdate where stat_date=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')";
//					db.executeUpdate(up_sql);
//				}
//			} else {
//				String in_sql = "insert into STAT_MAX_ONLINENUM(stat_date,max_num,update_date) values(to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd'),"
//						+ activeSessions + ",sysdate)";
//				db.executeUpdate(in_sql);
//			}
//		} catch (Exception e) {
//
//		}
		dbpool db = new dbpool();
		int activeSessions = getOnlineNum();
		String up_sql = "UPDATE STAT_MAX_ONLINENUM SET MAX_NUM=(CASE WHEN " + activeSessions + " > MAX_NUM THEN " + activeSessions
				+ " ELSE MAX_NUM END) ,UPDATE_DATE=SYSDATE WHERE STAT_DATE=TO_DATE(TO_CHAR(SYSDATE,'yyyy-mm-dd'),'yyyy-mm-dd')";
		int re = db.executeUpdate(up_sql);
		if (re < 1) {
			String in_sql = "INSERT INTO STAT_MAX_ONLINENUM(STAT_DATE,MAX_NUM,UPDATE_DATE) VALUES(TO_DATE(TO_CHAR(SYSDATE,'yyyy-mm-dd'),'yyyy-mm-dd'),"
					+ activeSessions + ",SYSDATE)";
			db.executeUpdate(in_sql);
		}
		db = null;

	}

	/*
	 * 主动注销
	 */
	// public static void destroySession(HttpSession session) {
	// UserSession
	// user=(UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	// if(user!=null){
	// dbpool db = new dbpool();
	// String loginId = user.getLoginId();
	// String de_sql="delete from sso_user_online where login_id='"+loginId+"'
	// or login_session_id='"+session.getId()+"'";
	// db.executeUpdate(de_sql);
	// db=null;
	// }
	// }
	// public static int getActiveSessions() {
	// String sql_count = "select login_id from sso_user_online";
	// dbpool db = new dbpool();
	// int activeSessions = 0;
	// try{
	// activeSessions = db.countselect(sql_count);
	// }catch (Exception e) {
	// activeSessions=500;
	// }finally{
	// db=null;
	// }
	// return activeSessions;
	// }
	public static void cleanOnlineUserTable() {
		// String sql_count = "delete from sso_user_online";
		// dbpool db = new dbpool();
		// int n = 0;
		// try{
		// n = db.executeUpdate(sql_count);
		// // System.out.println(n + " 条记录补消除");
		// }catch (Exception e) {
		// e.printStackTrace();
		// }finally{
		// db=null;
		// }
	}

	public static int getOnlineNum() {
		Cache cache = SpringUtil.getHzphCache().getCache();
		int on = 0;// 在线总数
		int webSize = 0; // web服务器数量
		webSize = 8;
		for (int i = 1; i <= webSize; i++) {
			Element et = cache.get("tomcat_" + i + "_Onlinesum");
			on += fixOnlineNum(et);
		}
		Element e0 = cache.get("Onlinesum");

		if (on < 1) {
			on = fixOnlineNum(e0);
		}
		return on;
	}

	private static int fixOnlineNum(Element ele) {
		if (ele != null) {
			return Integer.valueOf(ele.getValue().toString());
		}
		return 0;
	}
}
