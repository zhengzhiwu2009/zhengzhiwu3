/**
 * 
 */
package com.whaty.platform.database.oracle.standard.info;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.info.user.OracleInfoManager;
import com.whaty.platform.info.InfoManage;
import com.whaty.platform.info.News;
import com.whaty.platform.info.NewsScope;
import com.whaty.platform.info.NewsStatus;
import com.whaty.platform.info.NewsType;
import com.whaty.platform.info.user.InfoManagerPriv;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.sms.SmsFactory;
import com.whaty.platform.sms.SmsManage;
import com.whaty.platform.sms.SmsManagerPriv;
import com.whaty.platform.sso.SsoFactory;
import com.whaty.platform.sso.SsoManage;
import com.whaty.platform.sso.SsoManagerPriv;
import com.whaty.platform.sso.SsoUser;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.InfoLog;
import com.whaty.platform.util.log.UserLog;

/**
 * @author chenjian
 * 
 */
public class OracleInfoManage extends InfoManage {
	InfoManagerPriv basicManagePriv;

	// ManagerPriv basicManagePriv = new OracleManagerPriv();

	public OracleInfoManage() {

	}

	public OracleInfoManage(InfoManagerPriv managerPriv, String newsTypeId) {
		this.basicManagePriv = managerPriv;
		this.setNewsTypeId(managerPriv.getNewsTypeId());
	}

	public OracleInfoManage(InfoManagerPriv managerPriv) {
		// this.setManagerPriv(managerPriv);
		this.basicManagePriv = managerPriv;
		this.setNewsTypeId(managerPriv.getNewsTypeId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#getNews(java.lang.String)
	 */
	public News getNews(String newsTypeId) throws PlatformException {
		if (basicManagePriv.getNews == 1) {
			return new OracleNews(newsTypeId);
		} else {
			throw new PlatformException("没有查看新闻的权限");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#getNewsList(java.lang.String)
	 */
	public List getNewsList(String newsTypeId) throws PlatformException {
		// TODO Auto-generated method stub
		// System.out.print("List getNewsList(String newsTypeId)");
		if (basicManagePriv.getNews == 1) {
			List searchproperty = new ArrayList();
			List orderporperty = new ArrayList();
			if (null != newsTypeId && newsTypeId.equals("")
					&& newsTypeId.equals("null")) {
				searchproperty.add(new SearchProperty("t.news_type_id",
						newsTypeId, "="));
				orderporperty.add(new OrderProperty("t.news_type_id"));
			}
			OracleInfoList oracleInfoList = new OracleInfoList();
			return oracleInfoList.getNewsList(null, searchproperty,
					orderporperty);
		} else {
			throw new PlatformException("没有查看新闻的权限");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#addNews(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public int addNews(String title, String shortTitle, String reporter,
			String reportDate, String newsTypeId, String submitManagerId,
			String submitManagerName, String body, String isPop,
			String isActive, String isTop, String topSequence,
			String propertyString, String picLink) throws PlatformException {
		if (basicManagePriv.addNews == 1) {
			if (basicManagePriv.getNewsTypesAdd().contains(newsTypeId)) {
				OracleNews news = new OracleNews();
				news.setTitle(title);
				news.setShortTitle(shortTitle);
				news.setReporter(reporter);
				news.setReportDate(reportDate);
				news.setBody(body);
				news.setPicLink(picLink);
				news.setSubmitManagerId(submitManagerId);
				news.setSubmitManagerName(submitManagerName);
				NewsType newsType = getNewsType(newsTypeId);
				news.setType(newsType);
				news.setPropertyString(propertyString);
				NewsStatus newsStatus = new NewsStatus();
				if (isPop == null || isPop.length() < 1 || !isPop.equals("1")) {
					newsStatus.setPop(false);
				} else {
					newsStatus.setPop(true);
				}
				if (isActive == null || isActive.length() < 1
						|| !isActive.equals("1")) {
					newsStatus.setActive(false);
				} else {
					newsStatus.setActive(true);
				}
				if (isTop == null || isTop.length() < 1 || !isTop.equals("1")) {
					newsStatus.setTop(false);
				} else {
					newsStatus.setTop(true);
					try {
						newsStatus
								.setTopSequence(Integer.parseInt(topSequence));
					} catch (NumberFormatException nfe) {
						newsStatus.setTopSequence(0);
						// throw new PlatformException("Not input Information
						// Top
						// Sequence in write way.");
					}
				}
				news.setStatus(newsStatus);
				int i = news.add();
				return i;
			} else {
				throw new PlatformException("没有添加本新闻类型新闻的权限");
			}
		} else {
			throw new PlatformException("没有添加新闻的权限");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#updateNews(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public int updateNews(String id, String title, String shortTitle,
			String reporter, String reportDate, String newsTypeId,
			String submitManagerId, String submitManagerName, String body,
			String isPop, String isActive, String isTop, String topSequence,
			String propertyString, String picLink) throws PlatformException {
		if (basicManagePriv.addNews == 1) {
			if (basicManagePriv.getNewsTypesUpdate().contains(newsTypeId)) {
				OracleNews news = new OracleNews(id);
				news.setTitle(title);
				news.setShortTitle(shortTitle);
				news.setReporter(reporter);
				news.setReportDate(reportDate);
				news.setBody(body);
				news.setPicLink(picLink);
				news.setSubmitManagerId(submitManagerId);
				news.setSubmitManagerName(submitManagerName);
				InfoLog.setDebug(newsTypeId);
				NewsType newsType = getNewsType(newsTypeId);
				news.setType(newsType);
				news.setPropertyString(propertyString);
				NewsStatus newsStatus = new NewsStatus();
				if (isPop == null || isPop.length() < 1 || !isPop.equals("1")) {
					newsStatus.setPop(false);
				} else {
					newsStatus.setPop(true);
				}
				if (isActive == null || isActive.length() < 1
						|| !isActive.equals("1")) {
					newsStatus.setActive(false);
				} else {
					newsStatus.setActive(true);
				}
				if (isTop == null || isTop.length() < 1 || !isTop.equals("1")) {
					newsStatus.setTop(false);
				} else {
					newsStatus.setTop(true);
					try {
						newsStatus
								.setTopSequence(Integer.parseInt(topSequence));
					} catch (NumberFormatException nfe) {
						newsStatus.setTopSequence(0);
						// throw new PlatformException("Not input Information
						// Top
						// Sequence in write way.");
					}
				}
				news.setStatus(newsStatus);
				news.setConfirm("0");
				int sub = news.update();
				UserLog
						.setInfo(
								"<whaty>USERID$|$"
										+ this.basicManagePriv.getManagerId()
										+ "</whaty><whaty>BEHAVIOR$|$updateNews</whaty><whaty>STATUS$|$"
										+ sub
										+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
										+ LogType.MANAGER
										+ "</whaty><whaty>PRIORITY$|$"
										+ LogPriority.INFO + "</whaty>",
								new Date());
				return sub;
			} else {
				throw new PlatformException("没有修改本新闻类型新闻的权限");
			}
		} else {
			throw new PlatformException("没有修改新闻的权限");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#deleteNews(java.lang.String)
	 */
	public int deleteNews(String id) throws PlatformException {
		OracleNews news = new OracleNews(id);
		String newsTypeId = news.getType().getId();
		if (basicManagePriv.addNews == 1) {
			if (basicManagePriv.getNewsTypesUpdate().contains(newsTypeId)) {
				int sub = news.delete();
				UserLog
						.setInfo(
								"<whaty>USERID$|$"
										+ this.basicManagePriv.getManagerId()
										+ "</whaty><whaty>BEHAVIOR$|$deleteNews</whaty><whaty>STATUS$|$"
										+ sub
										+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
										+ LogType.MANAGER
										+ "</whaty><whaty>PRIORITY$|$"
										+ LogPriority.INFO + "</whaty>",
								new Date());
				return sub;
			} else {
				throw new PlatformException("没有修改本新闻类型新闻的权限");
			}
		} else {
			throw new PlatformException("没有修改新闻的权限");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#putTop(java.lang.String)
	 */
	public void putTop(String id) throws PlatformException {
		if (basicManagePriv.topNews == 1) {
			OracleNews news = new OracleNews(id);
			news.putTop();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$putTop</whaty><whaty>STATUS$|$"
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("没有至顶新闻的权限");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#cancelTop(java.lang.String)
	 */
	public void cancelTop(String id) throws PlatformException {
		if (basicManagePriv.topNews == 1) {
			OracleNews news = new OracleNews(id);
			news.cancelTop();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$cancelTop</whaty><whaty>STATUS$|$"
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("没有取消至顶新闻的权限");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#active(java.lang.String)
	 */
	public void active(String id) throws PlatformException {
		if (basicManagePriv.lockNews == 1) {
			OracleNews news = new OracleNews(id);
			news.undoLock();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$active</whaty><whaty>STATUS$|$"
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("没有锁定新闻的权限");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#unActive(java.lang.String)
	 */
	public void unActive(String id) throws PlatformException {
		if (basicManagePriv.lockNews == 1) {
			OracleNews news = new OracleNews(id);
			news.doLock();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$unActive</whaty><whaty>STATUS$|$"
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("没有取消锁定新闻的权限");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#putPop(java.lang.String)
	 */
	public void putPop(String id) throws PlatformException {
		if (basicManagePriv.popNews == 1) {
			OracleNews news = new OracleNews(id);
			news.setPop();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$putPop</whaty><whaty>STATUS$|$"
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("没有设定弹出新闻的权限");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#cancelPop(java.lang.String)
	 */
	public void cancelPop(String id) throws PlatformException {
		if (basicManagePriv.popNews == 1) {
			OracleNews news = new OracleNews(id);
			news.cancelPop();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$cancelPop</whaty><whaty>STATUS$|$"
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("没有取消弹出新闻的权限");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#getNewsType(java.lang.String)
	 */
	public NewsType getNewsType(String id) throws PlatformException {
		if (basicManagePriv.getNewsType == 1) {
			return new OracleNewsType(id);
		} else {
			throw new PlatformException("没有查看新闻类型的权限");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#getChildNewsType()
	 */
	public List getChildNewsType() throws PlatformException {
		OracleNewsType newsType = new OracleNewsType(this.getNewsTypeId());
		return newsType.getChildNewsType(null, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#addNewsType(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public int addNewsType(String name, String parent_id, String note,
			String status) throws PlatformException {
		OracleNewsType newsType = new OracleNewsType();
		newsType.setName(name);
		newsType.setParentId(parent_id);
		newsType.setNote(note);
		newsType.setStatus(status);
		int i = newsType.add();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$addNewsType</whaty><whaty>STATUS$|$"
								+ i
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#updateNewsType(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public int updateNewsType(String id, String name, String note,
			String status, String parent_id) throws PlatformException {
		OracleNewsType newsType = new OracleNewsType(id);
		newsType.setName(name);
		newsType.setNote(note);
		newsType.setStatus(status);
		newsType.setParentId(parent_id);
		int i = newsType.update();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$updateNewsType</whaty><whaty>STATUS$|$"
								+ i
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#deleteNewsType(java.lang.String)
	 */
	public int deleteNewsType(String id) throws PlatformException {
		OracleNewsType newsType = new OracleNewsType(id);
		int i = newsType.delete();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$deleteNewsType</whaty><whaty>STATUS$|$"
								+ i
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#moveNewsType(java.lang.String,
	 *      java.lang.String)
	 */
	public void moveNewsType(String id, String otherParentNewsTypeId)
			throws PlatformException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#putTop(java.lang.String,
	 *      java.lang.String)
	 */
	public void putTop(String id, String topSequence) throws PlatformException {
		OracleNews news = new OracleNews(id);
		news.putTop(Integer.parseInt(topSequence));
		UserLog.setInfo("<whaty>USERID$|$"
				+ this.basicManagePriv.getManagerId()
				+ "</whaty><whaty>BEHAVIOR$|$putTop</whaty><whaty>STATUS$|$"
				+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
				+ LogType.MANAGER + "</whaty><whaty>PRIORITY$|$"
				+ LogPriority.INFO + "</whaty>", new Date());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#getNewsListNum(java.lang.String)
	 */
	public int getNewsListNum(String newsTypeId) throws PlatformException {
		// TODO Auto-generated method stub
		// System.out.print("getNewsListNum(String newsTypeId)");
		if (basicManagePriv.getNews == 1) {
			List searchproperty = new ArrayList();
			if (null != newsTypeId && newsTypeId.equals("")
					&& newsTypeId.equals("null")) {
				searchproperty.add(new SearchProperty("t.news_type_id",
						newsTypeId, "="));
			}
			OracleInfoList oracleInfoList = new OracleInfoList();
			return oracleInfoList.getNewsListNum(searchproperty);
		} else {
			throw new PlatformException("没有查看新闻列表的权限");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#getNewsList(com.whaty.platform.util.Page,
	 *      java.lang.String)
	 */
	public List getNewsList(Page page, String newsTypeId)
			throws PlatformException {
		// TODO Auto-generated method stub
		// System.out.print("List getNewsList(Page page, String newsTypeId)");
		if (basicManagePriv.getNews == 1) {
			List searchproperty = new ArrayList();
			List orderporperty = new ArrayList();
			if (null != newsTypeId && newsTypeId.equals("")
					&& newsTypeId.equals("null")) {
				searchproperty.add(new SearchProperty("t.news_type_id",
						newsTypeId, "="));
				orderporperty.add(new OrderProperty("t.news_type_id"));
			}
			OracleInfoList oracleInfoList = new OracleInfoList();
			return oracleInfoList.getNewsList(page, searchproperty,
					orderporperty);
		} else {
			throw new PlatformException("没有取得新闻列表的权限");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#searchNewsList(com.whaty.platform.util.Page,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public List searchNewsList(Page page, String title, String beginDate,
			String endDate, String newsTypeId) throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoManage#setTypeManager(com.whaty.platform.info.user.InfoManagerPriv,
	 *      java.lang.String)
	 */
	public void setTypeManager(InfoManagerPriv managerpriv, String newsTypeId)
			throws PlatformException {
		// TODO Auto-generated method stub

	}

	public List getNewsTypes(Page page, String parent_id)
			throws PlatformException {
		if (basicManagePriv.getNewsType == 1) {
			OracleInfoList oracleInfoList = new OracleInfoList();
			List searchProperty = new ArrayList();
			if (parent_id == null || parent_id.equals("")
					|| parent_id.equals("null"))
				searchProperty
						.add(new SearchProperty("parent_id", "", "isNull"));
			else {
				searchProperty.add(new SearchProperty("parent_id", parent_id,
						"="));
			}
			return oracleInfoList.getNewsTypes(page, searchProperty, null);
		} else {
			throw new PlatformException("没有取得新闻类型列表的权限");
		}

	}

	public List getRightNewsTypes(Page page) throws PlatformException {
		if (basicManagePriv.getNewsType == 1) {
			return getRightNewsTypes(page, null, null);
		} else {
			throw new PlatformException("没有取得新闻类型列表的权限");
		}

	}

	public List getInfoManagerNewsTypes(Page page, List searchproperty,
			List orderporperty) throws PlatformException {
		if (basicManagePriv.getNewsType == 1) {
			OracleInfoList oracleInfoList = new OracleInfoList();
			List get_right = basicManagePriv.getNewsTypesGet();
			String toInSql = basicManagePriv.toInSql(get_right);
			if (toInSql == null)
				return oracleInfoList.getNewsTypes(page, searchproperty,
						orderporperty);
			if (searchproperty == null)
				searchproperty = new ArrayList();
			searchproperty.add(new SearchProperty("id", toInSql, "in"));
			searchproperty.add(new SearchProperty("id", basicManagePriv
					.getNewsTypeId(), "<>"));
			return oracleInfoList.getNewsTypes(page, searchproperty,
					orderporperty);
		} else {
			throw new PlatformException("没有取得新闻类型列表的权限");
		}
	}

	public List getRightNewsTypes(Page page, List searchproperty,
			List orderporperty) throws PlatformException {
		if (basicManagePriv.getNewsType == 1) {
			OracleInfoList oracleInfoList = new OracleInfoList();
			List get_right = basicManagePriv.getNewsTypesGet();
			String toInSql = basicManagePriv.toInSql(get_right);
			if (toInSql == null)
				return oracleInfoList.getNewsTypes(page, searchproperty,
						orderporperty);
			if (searchproperty == null)
				searchproperty = new ArrayList();
			 
				searchproperty.add(new SearchProperty("id", toInSql, "in"));
			 
			
			return oracleInfoList.getNewsTypes(page, searchproperty,
					orderporperty);
		} else {
			throw new PlatformException("没有取得新闻类型列表的权限");
		}

	}

	public List getNewsTypes(Page page, List searchproperty, List orderporperty)
			throws PlatformException {
		if (basicManagePriv.getNewsType == 1) {
			OracleInfoList oracleInfoList = new OracleInfoList();
			return oracleInfoList.getNewsTypes(page, searchproperty,
					orderporperty);
		} else {
			throw new PlatformException("没有取得新闻类型的权限");
		}

	}

	public int getNewsTypesNum(String parent_id) throws PlatformException {
		// TODO 自动生成方法存根
		if (basicManagePriv.getNewsType == 1) {
			OracleInfoList oracleInfoList = new OracleInfoList();
			List searchProperty = new ArrayList();
			if (parent_id == null || parent_id.equals("")
					|| parent_id.equals("null"))
				searchProperty
						.add(new SearchProperty("parent_id", "", "isNull"));
			else {
				searchProperty.add(new SearchProperty("parent_id", parent_id,
						"="));
			}
			return oracleInfoList.getNewsTypesNum(searchProperty);
		} else {
			throw new PlatformException("没有取得新闻类型的权限");
		}
	}

	public int getNewsTypesNum(List searchproperty) throws PlatformException {
		// TODO 自动生成方法存根
		if (basicManagePriv.getNewsType == 1) {
			OracleInfoList oracleInfoList = new OracleInfoList();
			return oracleInfoList.getNewsTypesNum(searchproperty);
		} else {
			throw new PlatformException("没有取得新闻类型的权限");
		}
	}

	public List getNewsList(Page page, List searchproperty, List orderporperty)
			throws PlatformException {

		if (basicManagePriv.getNews == 1) {
			OracleInfoList oracleInfoList = new OracleInfoList();
			return oracleInfoList.getNewsList(page, searchproperty,
					orderporperty);
		} else {
			throw new PlatformException("没有取得新闻的权限");
		}
	}

	public List getNewsList(Page page, String isActived_id,
			String news_type_id, String rep_date_box, String title_box,
			String isTop, String isConfirm, String isPop)
			throws PlatformException {

		if (basicManagePriv.getNews == 1) {
			OracleInfoList oracleInfoList = new OracleInfoList();
			List srhPropList = new ArrayList();
			SearchProperty searchProp = new SearchProperty("isactive",
					isActived_id, "=");
			srhPropList.add(searchProp);
			searchProp = new SearchProperty("news_type_id", news_type_id, "=");
			srhPropList.add(searchProp);
			searchProp = new SearchProperty("report_date", rep_date_box, "d=");
			srhPropList.add(searchProp);
			searchProp = new SearchProperty("title", title_box, "like");
			srhPropList.add(searchProp);
			searchProp = new SearchProperty("istop", isTop, "=");
			srhPropList.add(searchProp);
			searchProp = new SearchProperty("isconfirm", isConfirm, "=");
			srhPropList.add(searchProp);
			searchProp = new SearchProperty("ispop", isPop, "=");
			srhPropList.add(searchProp);

			List get_right = basicManagePriv.getNewsTypesGet();
			String in = basicManagePriv.toInSql(get_right);
			if (in != null && !in.equals(""))
				searchProp = new SearchProperty("news_type_id", in, "in");
			srhPropList.add(searchProp);
			List orderPropList = new ArrayList();
			orderPropList.add(new OrderProperty("report_date",
					OrderProperty.DESC));
			orderPropList.add(new OrderProperty("submit_date",
					OrderProperty.DESC));
			return oracleInfoList.getNewsList(page, srhPropList, orderPropList);
		} else {
			throw new PlatformException("没有取得新闻的权限");
		}
	}

	public int getNewsListNum(List searchproperty) throws PlatformException {
		if (basicManagePriv.getNews == 1) {
			OracleInfoList oracleInfoList = new OracleInfoList();
			return oracleInfoList.getNewsListNum(searchproperty);
		} else {
			throw new PlatformException("没有取得新闻的权限");
		}
	}

	public int getNewsListNum(String isActived_id, String news_type_id,
			String rep_date_box, String title_box, String isTop,
			String isConfirm, String isPop) throws PlatformException {

		if (basicManagePriv.getNews == 1) {
			OracleInfoList oracleInfoList = new OracleInfoList();

			List srhPropList = new ArrayList();
			SearchProperty searchProp = new SearchProperty("isactive",
					isActived_id, "=");
			srhPropList.add(searchProp);
			searchProp = new SearchProperty("news_type_id", news_type_id, "=");
			srhPropList.add(searchProp);
			searchProp = new SearchProperty("report_date", rep_date_box, "d=");
			srhPropList.add(searchProp);
			searchProp = new SearchProperty("title", title_box, "like");
			srhPropList.add(searchProp);
			searchProp = new SearchProperty("istop", isTop, "=");
			srhPropList.add(searchProp);
			searchProp = new SearchProperty("isconfirm", isConfirm, "=");
			srhPropList.add(searchProp);
			searchProp = new SearchProperty("ispop", isPop, "=");
			srhPropList.add(searchProp);
			List get_right = basicManagePriv.getNewsTypesGet();
			String in = basicManagePriv.toInSql(get_right);
			if (in != null && !in.equals(""))
				searchProp = new SearchProperty("news_type_id", in, "in");
			srhPropList.add(searchProp);
			return oracleInfoList.getNewsListNum(srhPropList);
		} else {
			throw new PlatformException("没有取得新闻的权限");
		}
	}

	public int confirmNews(String managerId, String managerName, List newsIds,
			boolean confirmFlag) throws PlatformException {
		// TODO 自动生成方法存根
		int count = 0;
		if (basicManagePriv.confirmNews == 1) {
			for (int i = 0; i < newsIds.size(); i++) {
				OracleNews news = new OracleNews((String) newsIds.get(i));
				if (news.getConfirm().equals("0"))
					news.confirmNews("1", managerId, managerName);
				else
					news.confirmNews("0", managerId, managerName);
				count++;
				// news.update();
			}
			return count;
		} else {
			throw new PlatformException("没有审核新闻的权限");
		}
	}

	public Map getNewsScope(News news) throws PlatformException {
		String propertyString = news.getPropertyString();
		return NewsScope.parse(propertyString);
	}

	public int deleteNews(List ids) throws PlatformException {
		// TODO 自动生成方法存根
		int count = 0;
		if (basicManagePriv.deleteNews == 1) {
			for (int i = 0; i < ids.size(); i++) {
				OracleNews news = new OracleNews((String) ids.get(i));
				news.delete();
				count++;
				// news.update();
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteNews</whaty><whaty>STATUS$|$"
									+ count
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new PlatformException("没有删除新闻的权限");
		}
	}

	public int active(List ids) throws PlatformException {
		// TODO 自动生成方法存根
		int count = 0;
		if (basicManagePriv.lockNews == 1) {
			for (int i = 0; i < ids.size(); i++) {
				OracleNews news = new OracleNews((String) ids.get(i));
				if (news.getStatus().getIsActive())
					news.doLock();
				else
					news.undoLock();
				count++;
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$active</whaty><whaty>STATUS$|$"
									+ count
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new PlatformException("没有解锁新闻的权限");
		}
	}

	public int cancelPop(List ids) throws PlatformException {
		// TODO 自动生成方法存根
		int count = 0;
		if (basicManagePriv.popNews == 1) {
			for (int i = 0; i < ids.size(); i++) {
				OracleNews news = new OracleNews((String) ids.get(i));
				news.cancelPop();
				count++;
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$cancelPop</whaty><whaty>STATUS$|$"
									+ count
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new PlatformException("没有取消弹出的权限");
		}
	}

	public int putPop(List ids) throws PlatformException {
		// TODO 自动生成方法存根
		int count = 0;
		if (basicManagePriv.popNews == 1) {
			for (int i = 0; i < ids.size(); i++) {
				OracleNews news = new OracleNews((String) ids.get(i));
				if(news.getStatus().getIsPop()){
					news.cancelPop();
				}else{
					news.setPop();
				}
				count++;
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$putPop</whaty><whaty>STATUS$|$"
									+ count
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new PlatformException("没有新闻弹出的权限");
		}
	}

	public int unActive(List ids) throws PlatformException {
		// TODO 自动生成方法存根
		int count = 0;
		if (basicManagePriv.lockNews == 1) {
			for (int i = 0; i < ids.size(); i++) {
				OracleNews news = new OracleNews((String) ids.get(i));
				news.doLock();
				count++;
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$unActive</whaty><whaty>STATUS$|$"
									+ count
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new PlatformException("没有新闻锁定的权限");
		}
	}

	public int putTop(List ids) throws PlatformException {
		// TODO 自动生成方法存根
		int count = 0;
		if (basicManagePriv.topNews == 1) {
			for (int i = 0; i < ids.size(); i++) {
				OracleNews news = new OracleNews((String) ids.get(i));
				if (news.getStatus().getIsTop())
					news.cancelTop();
				else
					news.putTop();
				count++;
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$putTop</whaty><whaty>STATUS$|$"
									+ count
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new PlatformException("没有新闻至顶的权限");
		}
	}

	public int deleteNewsType(List ids) throws PlatformException {
		// TODO 自动生成方法存根
		int count = 0;
		if (basicManagePriv.deleteNewsType == 1) {
			for (int i = 0; i < ids.size(); i++) {
				OracleNewsType newsType = new OracleNewsType((String) ids
						.get(i));
				count = count + newsType.delete();
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteNewsType</whaty><whaty>STATUS$|$"
									+ count
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new PlatformException("No Right to Delete the NewsType.");
		}
	}

	public int cancelTop(List ids) throws PlatformException {
		// TODO 自动生成方法存根
		int count = 0;
		if (basicManagePriv.topNews == 1) {
			for (int i = 0; i < ids.size(); i++) {
				OracleNews news = new OracleNews((String) ids.get(i));
				news.cancelTop();
				count++;
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$cancelTop</whaty><whaty>STATUS$|$"
									+ count
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new PlatformException("没有取消至顶的权限");
		}
	}

	public List getNews(Page page, String tag) throws PlatformException {
		if (basicManagePriv.getNews == 1) {
			OracleInfoList oracleInfoList = new OracleInfoList();
			return oracleInfoList.getNewsByTag(page, tag);
		} else {
			throw new PlatformException("没有取得新闻的权限");
		}
	}

	public List getNews(String tag, int num) throws PlatformException {
		if (basicManagePriv.getNews == 1) {
			OracleInfoList oracleInfoList = new OracleInfoList();
			return oracleInfoList.getNewsByTag(tag, num);
		} else {
			throw new PlatformException("没有取得新闻的权限");
		}
	}

	public int getNewsNum(String tag) throws PlatformException {
		if (basicManagePriv.getNews == 1) {
			OracleInfoList oracleInfoList = new OracleInfoList();
			return oracleInfoList.getNewsByTagNum(tag);
		} else {
			throw new PlatformException("没有取得新闻的权限");
		}
	}

	public int updateTag(String newsTypeId, String tag)
			throws PlatformException {
		if (basicManagePriv.updateNewsType == 1) {
			OracleNewsType oracleNewsType = new OracleNewsType();
			oracleNewsType.setId(newsTypeId);
			int sub = oracleNewsType.updateTag(tag);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$updateTag</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new PlatformException("No Right to Update the News.");
		}
	}

	public Map getNews(Map tagAndNumber) throws PlatformException {
		if (basicManagePriv.getNews == 1) {
			OracleInfoList oracleInfoList = new OracleInfoList();
			return oracleInfoList.getNewsByTags(tagAndNumber);
		} else {
			throw new PlatformException("没有取得新闻的权限");
		}
	}

	public int addNews(String title, String color, String short_title,
			String short_color, String reporter, String reportDate,
			String[] types, String submitManagerId, String submitManagerName,
			String body, String isPop, String isActive, String isTop,
			String topSequence, String[] person, String[] site_id,
			String[] grade_id, String[] major_id, String[] level_id,
			String picLilnk) throws PlatformException {
		if (basicManagePriv.addNews == 1) {
			int len = 0;

			String propertyString = "";
			if (person != null) {
				propertyString = propertyString + "|person,";
				for (len = 0; len < person.length - 1; len++) {
					propertyString = propertyString + person[len] + ",";
				}
				propertyString = propertyString + person[len] + "|";
			}
			if (site_id != null) {
				propertyString = propertyString + "|sites,";
				for (len = 0; len < site_id.length - 1; len++) {
					propertyString = propertyString + site_id[len] + ",";
				}
				propertyString = propertyString + site_id[len] + "|";
			}
			if (grade_id != null) {
				propertyString = propertyString + "|grades,";
				for (len = 0; len < grade_id.length - 1; len++) {
					propertyString = propertyString + grade_id[len] + ",";
				}
				propertyString = propertyString + grade_id[len] + "|";
			}
			if (major_id != null) {
				propertyString = propertyString + "|majors,";
				for (len = 0; len < major_id.length - 1; len++) {
					propertyString = propertyString + major_id[len] + ",";
				}
				propertyString = propertyString + major_id[len] + "|";
			}
			if (level_id != null) {
				propertyString = propertyString + "|edu_types,";
				for (len = 0; len < level_id.length - 1; len++) {
					propertyString = propertyString + level_id[len] + ",";
				}
				propertyString = propertyString + level_id[len] + "|";
			}
			if (color != null && !color.equals("null") && !color.equals(""))
				title = "<font color=" + color + ">" + title + "</font>";
			if (short_color != null && !short_color.equals("null")
					&& !short_color.equals(""))
				short_title = "<font color=" + short_color + ">" + short_title
						+ "</font>";

			int sub = addNews(title, short_title, reporter, reportDate, types,
					submitManagerId, submitManagerName, body, isPop, isActive,
					isTop, topSequence, propertyString, picLilnk);
			 
			return sub;
		} else {
			throw new PlatformException("没有添加新闻的权限");
		}

	}

	public int addPicNews(String title, String color, String short_title,
			String short_color, String reporter, String reportDate,
			String[] types, String submitManagerId, String submitManagerName,
			String body, String isPop, String isActive, String isTop,
			String topSequence, String[] person, String[] site_id,
			String[] grade_id, String[] major_id, String[] level_id,
			String picLink) throws PlatformException {
		if (basicManagePriv.addNews == 1) {
			int len = 0;

			String propertyString = "";
			if (person != null) {
				propertyString = propertyString + "|person,";
				for (len = 0; len < person.length - 1; len++) {
					propertyString = propertyString + person[len] + ",";
				}
				propertyString = propertyString + person[len] + "|";
			}
			if (site_id != null) {
				propertyString = propertyString + "|sites,";
				for (len = 0; len < site_id.length - 1; len++) {
					propertyString = propertyString + site_id[len] + ",";
				}
				propertyString = propertyString + site_id[len] + "|";
			}
			if (grade_id != null) {
				propertyString = propertyString + "|grades,";
				for (len = 0; len < grade_id.length - 1; len++) {
					propertyString = propertyString + grade_id[len] + ",";
				}
				propertyString = propertyString + grade_id[len] + "|";
			}
			if (major_id != null) {
				propertyString = propertyString + "|majors,";
				for (len = 0; len < major_id.length - 1; len++) {
					propertyString = propertyString + major_id[len] + ",";
				}
				propertyString = propertyString + major_id[len] + "|";
			}
			if (level_id != null) {
				propertyString = propertyString + "|edu_types,";
				for (len = 0; len < level_id.length - 1; len++) {
					propertyString = propertyString + level_id[len] + ",";
				}
				propertyString = propertyString + level_id[len] + "|";
			}
			title = "<font color=" + color + ">" + title + "</font>";
			short_title = "<font color=" + short_color + ">" + short_title
					+ "</font>";

			int sub = addNews(title, short_title, reporter, reportDate, types,
					submitManagerId, submitManagerName, body, isPop, isActive,
					isTop, topSequence, propertyString, picLink);
		 
			return sub;
		} else {
			throw new PlatformException("没有添加新闻的权限");
		}

	}

	public int addNews(String title, String shortTitle, String reporter,
			String reportDate, String[] types, String submitManagerId,
			String submitManagerName, String body, String isPop,
			String isActive, String isTop, String topSequence,
			String propertyString, String picLink) throws PlatformException {
		if (basicManagePriv.addNews == 1) {
			if (types == null || types.length == 0)
				return 0;
			else {
				int count = 0;
				for (int i = 0; i < types.length; i++) {
					if (addNews(title, shortTitle, reporter, reportDate,
							types[i], submitManagerId, submitManagerName, body,
							isPop, isActive, isTop, topSequence,
							propertyString, picLink) > 0) {
						count += 1;
					}
				}
				UserLog
						.setInfo(
								"<whaty>USERID$|$"
										+ this.basicManagePriv.getManagerId()
										+ "</whaty><whaty>BEHAVIOR$|$addNews</whaty><whaty>STATUS$|$"
										+ count
										+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
										+ LogType.MANAGER
										+ "</whaty><whaty>PRIORITY$|$"
										+ LogPriority.INFO + "</whaty>",
								new Date());
				return count;
			}
		} else {
			throw new PlatformException("没有添加新闻的权限");
		}
	}

//	public WhatyEditorConfig getWhatyEditorConfig(HttpSession session)
//			throws PlatformException {
//		ServletContext application = session.getServletContext();
//		InfoConfig config = (InfoConfig) application.getAttribute("infoConfig");
//		PlatformConfig platformConfig = (PlatformConfig) application
//				.getAttribute("platformConfig");
//		WhatyEditorConfig editorConfig = new WhatyEditorConfig();
//		editorConfig.setAppRootURI(platformConfig.getPlatformWebAppUriPath());
//		editorConfig.setEditorRefURI("WhatyEditor/");
//		editorConfig.setEditorURI(platformConfig.getPlatformWebAppUriPath()
//				+ "WhatyEditor/");
//		editorConfig.setUploadAbsPath(config.getInfoWebIncomingAbsPath()
//				+ File.separator);
//		editorConfig.setUploadURI(config.getInfoWebIncomingUriPath());
//		return editorConfig;
//	}

	public int copyNews(String news_id, String newsType_id)
			throws PlatformException {
		if (basicManagePriv.copyNews == 1) {
			OracleNews news = new OracleNews(news_id);
			int sub = news.copyTo(newsType_id);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$copyNews</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new PlatformException("没有拷贝新闻的权限");
		}
	}

	public int addInfoManager(String login_id, String name, String password,
			String nickName, String email, String status)
			throws PlatformException {

		SsoFactory sso = SsoFactory.getInstance();
		SsoManagerPriv managerPriv = sso.creatSsoManagerPriv();
		managerPriv.addUser = 1;
		SsoManage ssoManage = sso.creatSsoManage(managerPriv);

		ssoManage.addSsoUser(login_id, password, name, nickName, email);

		SsoUser ssouser = ssoManage.getSsoUserLogin(login_id);

		OracleInfoManager infomanager = new OracleInfoManager();
		infomanager.setId(ssouser.getId());
		infomanager.setLoginId(login_id);
		infomanager.setName(name);
		infomanager.setStatus(status);
		int sub = infomanager.add();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$addInfoManager</whaty><whaty>STATUS$|$"
								+ sub
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;
	}

	/**
	 * 为没有分站通知新闻类型的站点创建通知新闻类型以及标签 标签规则：site_id + "分站通知";
	 * 
	 * 分站通知的父类型为一个 tag="分站通知"的新闻类型
	 * 
	 * @param newsTypeId
	 * @return
	 * @throws PlatformException
	 */
	public int createNewsTypeForSiteNotice() throws PlatformException {
		OracleInfoList oracleInfoList = new OracleInfoList();
		int sub = oracleInfoList.createNewsTypeForSiteNotice();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$createNewsTypeForSiteNotice</whaty><whaty>STATUS$|$"
								+ sub
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;
	}

	public int createNewsTypeForSiteNews() throws PlatformException {
		OracleInfoList oracleInfoList = new OracleInfoList();
		int sub = oracleInfoList
				.createNewsTypeForSiteNews(NewsTagsToCreateSiteType);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$createNewsTypeForSiteNews</whaty><whaty>STATUS$|$"
								+ sub
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;
	}

	public int putInfomanagerPriv(String userId, String siteId)
			throws PlatformException {
		SsoFactory sso = SsoFactory.getInstance();
		SsoManagerPriv managerPriv = sso.creatSsoManagerPriv();
		managerPriv.getUser = 1;
		SsoManage ssoManage = sso.creatSsoManage(managerPriv);
		SsoUser ssoUser = ssoManage.getSsoUserByLogin(userId);
		int sub = basicManagePriv.putInfomanagerPriv(ssoUser.getId(), siteId);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$putInfomanagerPriv</whaty><whaty>STATUS$|$"
								+ sub
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;

	}

	public List getRightNewsTypeIds(String userId) throws PlatformException {
		OracleInfoList oracleInfoList = new OracleInfoList();
		return oracleInfoList.getRightNewsTypes(userId);
	}

	public int updateInfoRight(String userId, String[] pageNewsTypeIds,
			String[] newsTypeIds) throws PlatformException {
		OracleInfoList oracleInfoList = new OracleInfoList();
		int sub = oracleInfoList.updateInfoRight(userId, pageNewsTypeIds,
				newsTypeIds);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$updateInfoRight</whaty><whaty>STATUS$|$"
								+ sub
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;
	}

	public int addNewsReadCount(String newsId) throws PlatformException {
		OracleNews news = new OracleNews();
		news.setId(newsId);
		int sub = news.addReadCount();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$addNewsReadCount</whaty><whaty>STATUS$|$"
								+ sub
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;
	}

	public int deleteSiteInfomanagerPriv(String userId, String siteId)
			throws PlatformException {
		OracleInfoList oracleInfoList = new OracleInfoList();
		int sub = oracleInfoList.deleteSiteInfomanagerPriv(userId, siteId);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$deleteSiteInfomanagerPriv</whaty><whaty>STATUS$|$"
								+ sub
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;
	}

	public SmsManagerPriv getSmsManagerPriv(String managerId) throws PlatformException {
		SmsFactory smsFactory = SmsFactory.getInstance();
		SmsManagerPriv smsManagerPriv = smsFactory.getSmsManagerPriv(managerId);
		smsManagerPriv.addSms = basicManagePriv.addSms;
		smsManagerPriv.batchImportMobiles = basicManagePriv.batchImportMobiles;
		smsManagerPriv.checkSms = basicManagePriv.checkSms;
		smsManagerPriv.deleteSms = basicManagePriv.deleteSms;
		smsManagerPriv.getSms = basicManagePriv.getSms;
		smsManagerPriv.sendSms = basicManagePriv.sendSms;
		smsManagerPriv.updateSms = basicManagePriv.updateSms;
		smsManagerPriv.setManagerId(managerId);
		return smsManagerPriv;
	}

	public SmsManage getSmsManage(String managerId) throws PlatformException {
		SmsFactory smsFactory = SmsFactory.getInstance();
		SmsManagerPriv smsManagerPriv = smsFactory.getSmsManagerPriv(managerId);
		smsManagerPriv.addSms = basicManagePriv.addSms;
		smsManagerPriv.batchImportMobiles = basicManagePriv.batchImportMobiles;
		smsManagerPriv.checkSms = basicManagePriv.checkSms;
		smsManagerPriv.deleteSms = basicManagePriv.deleteSms;
		smsManagerPriv.getSms = basicManagePriv.getSms;
		smsManagerPriv.sendSms = basicManagePriv.sendSms;
		smsManagerPriv.updateSms = basicManagePriv.updateSms;
		smsManagerPriv.setManagerId(managerId);
		return smsFactory.creatSmsManage(smsManagerPriv);
	}
}
