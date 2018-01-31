/**
 * 
 */
package com.whaty.platform.database.oracle.standard.logmanage;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.logmanage.LogList;
import com.whaty.platform.logmanage.LogManage;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;

/**
 * @author wq
 * 
 */
public class OracleLogManage extends LogManage {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.logmanage.LogManage#getLogEntities(com.whaty.platform.util.Page,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public List getLogEntities(Page page, String id, String userid,
			String operateStartTime, String operateEndTime, String behavior,
			String status, String notes, String logtype, String priority, boolean history) {
		List searchList = new ArrayList();
		if (id != null && !"".equals(id))
			searchList.add(new SearchProperty("id", id, "="));
		if (userid != null && !"".equals(userid))
			searchList.add(new SearchProperty("userid", userid, "="));
		if (operateStartTime != null && !"".equals(operateStartTime))
			searchList.add(new SearchProperty("operate_time", operateStartTime,
					">="));
		if (operateEndTime != null && !"".equals(operateEndTime))
			searchList.add(new SearchProperty("operate_time", operateEndTime,
					"<="));
		if (behavior != null && !"".equals(behavior))
			searchList.add(new SearchProperty("behavior", behavior, "like"));
		if (status != null && !"".equals(status))
			searchList.add(new SearchProperty("status", status, "="));
		if (notes != null && !"".equals(notes))
			searchList.add(new SearchProperty("notes", notes, "like"));
		if (logtype != null && !"".equals(logtype))
			searchList.add(new SearchProperty("logtype", logtype, "="));
		if (priority != null && !"".equals(priority))
			searchList.add(new SearchProperty("priority", priority, "="));

		OracleLogList logList = new OracleLogList();
		
		List orderList = new ArrayList<OrderProperty>();
		orderList.add(new OrderProperty("operate_time", "DESC"));

		return logList.searchLogEntities(page, searchList, orderList, history);
	}

	public int getLogEntitiesNum(String id, String userid,
			String operateStartTime, String operateEndTime, String behavior,
			String status, String notes, String logtype, String priority, boolean history) {
		List searchList = new ArrayList();
		if (id != null && !"".equals(id))
			searchList.add(new SearchProperty("id", id, "="));
		if (userid != null && !"".equals(userid))
			searchList.add(new SearchProperty("userid", userid, "="));
		if (operateStartTime != null && !"".equals(operateStartTime))
			searchList.add(new SearchProperty("operate_time", operateStartTime,
					">="));
		if (operateEndTime != null && !"".equals(operateEndTime))
			searchList.add(new SearchProperty("operate_time", operateEndTime,
					"<="));
		if (behavior != null && !"".equals(behavior))
			searchList.add(new SearchProperty("behavior", behavior, "like"));
		if (status != null && !"".equals(status))
			searchList.add(new SearchProperty("status", status, "="));
		if (notes != null && !"".equals(notes))
			searchList.add(new SearchProperty("notes", notes, "like"));
		if (logtype != null && !"".equals(logtype))
			searchList.add(new SearchProperty("logtype", logtype, "="));
		if (priority != null && !"".equals(priority))
			searchList.add(new SearchProperty("priority", priority, "="));

		OracleLogList logList = new OracleLogList();

		return logList.searchLogEntitiesNum(searchList, history);
	}

	public int backUpLogEntities(String id, String userid,
			String operateStartTime, String operateEndTime, String behavior,
			String status, String notes, String logtype, String priority) {
		List searchList = new ArrayList();
		if (id != null && !"".equals(id))
			searchList.add(new SearchProperty("id", id, "="));
		if (userid != null && !"".equals(userid))
			searchList.add(new SearchProperty("userid", userid, "="));
		if (operateStartTime != null && !"".equals(operateStartTime))
			searchList.add(new SearchProperty("operate_time", operateStartTime,
					">="));
		if (operateEndTime != null && !"".equals(operateEndTime))
			searchList.add(new SearchProperty("operate_time", operateEndTime,
					"<="));
		if (behavior != null && !"".equals(behavior))
			searchList.add(new SearchProperty("behavior", behavior, "like"));
		if (status != null && !"".equals(status))
			searchList.add(new SearchProperty("status", status, "="));
		if (notes != null && !"".equals(notes))
			searchList.add(new SearchProperty("notes", notes, "like"));
		if (logtype != null && !"".equals(logtype))
			searchList.add(new SearchProperty("logtype", logtype, "="));
		if (priority != null && !"".equals(priority))
			searchList.add(new SearchProperty("priority", priority, "="));
		
		OracleLogList logList = new OracleLogList();
		return logList.backUpLogEntities(searchList);
	}
	
	/**
	 * 插入日志
	 */
	public int insertLogEntities(String id, String userid,
			String operateTime, String behavior,
			String status, String notes, String logtype, String priority)throws PlatformException{
		int c = 0 ;
		LogList log = new OracleLogList();
		c = log.addLogEntities(id, userid, operateTime, behavior, status, notes, logtype, priority);
		return c;
	}

	/**
	 * 插入日志
	 */
	@Override
	public int insertLog(String behavior) throws PlatformException {
		UserSession us = (UserSession)ServletActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		return this.insertLog(behavior, us);
	}

	@Override
	public int insertLog(String behavior, UserSession us)
			throws PlatformException {
		//记录操作日志
		dbpool db = new dbpool();
		String id = String.valueOf(db.getSequenceNextId("S_WHATYUSER_LOG4J")); //日志表序列;
		return this.insertLogEntities(id, us.getLoginId(), "", behavior, "有效", "", ""/*us.getSsoUser().getEnumConstByUserType() !=null ?us.getSsoUser().getEnumConstByUserType().getName():""*/, "");
	}

	@Override
	public int insertLog(String behavior, String note) throws PlatformException {
		UserSession us = (UserSession)ServletActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		//记录操作日志
		dbpool db = new dbpool();
		String id = String.valueOf(db.getSequenceNextId("S_WHATYUSER_LOG4J")); //日志表序列;
		return this.insertLogEntities(id, us.getLoginId(), "", behavior, "有效", note, "", "");
	}
/**
 * @author wuhao
 * 记录日志
 */
	@Override
	public int insertLog(String user_id, String operate_time, String behavior, String status, String notes, String logtype, String priority) throws PlatformException {
		//记录操作日志
		dbpool db = new dbpool();
		String id = String.valueOf(db.getSequenceNextId("S_WHATYUSER_LOG4J")); //日志表序列;
		return this.insertLogEntities(id, user_id, operate_time, behavior,status, notes, logtype, priority);
	}

}
