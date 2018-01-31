/**
 * 
 */
package com.whaty.platform.database.oracle.standard.info;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleBasicEntityList;
import com.whaty.platform.database.oracle.standard.entity.user.OracleManagerPriv;
import com.whaty.platform.entity.BasicEntityManage;
import com.whaty.platform.entity.PlatformFactory;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.info.News;
import com.whaty.platform.info.NewsType;
import com.whaty.platform.info.NormalInfoManage;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserLog;

/**
 * @author chenjian
 * 
 */
public class OracleNormalInfoManage extends NormalInfoManage {
	private ManagerPriv basicManagerPriv;

	public OracleNormalInfoManage() {
		super();
		basicManagerPriv = new OracleManagerPriv();
		// TODO Auto-generated constructor stub
	}

	public OracleNormalInfoManage(ManagerPriv managerPriv) {
		this.basicManagerPriv = managerPriv;
	}

	public OracleNormalInfoManage(String newsTypeId) {
		this.setNewsTypeId(newsTypeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.NormalInfoManage#getActiveNews(com.whaty.platform.util.Page)
	 */
	public List getActiveNews(Page page) throws PlatformException {
		// InfoLog.setDebug("pageInt=" + page.getPageInt());
		OracleNewsType newsType = new OracleNewsType();
		newsType.setId(this.getNewsTypeId());
		List searchproperty = new ArrayList();
		searchproperty.add(new SearchProperty("isactive", "1", "=num"));
		searchproperty.add(new SearchProperty("isPop", "0", "=num"));
		List orderproperty = new ArrayList();
		orderproperty.add(new OrderProperty("istop", OrderProperty.DESC));
		orderproperty.add(new OrderProperty("top_sequence", OrderProperty.ASC));
		orderproperty.add(new OrderProperty("report_date", OrderProperty.DESC));
		orderproperty.add(new OrderProperty("submit_date", OrderProperty.DESC));
		return newsType.getNews(page, searchproperty, orderproperty);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.NormalInfoManage#getActivePopNews(com.whaty.platform.util.Page)
	 */
	public List getActivePopNews(Page page) throws PlatformException {
		OracleNewsType newsType = new OracleNewsType();
		newsType.setId(this.getNewsTypeId());
		List searchproperty = new ArrayList();
		searchproperty.add(new SearchProperty("isactive", "1", "=num"));
		searchproperty.add(new SearchProperty("isPop", "1", "=num"));
		List orderproperty = new ArrayList();
		orderproperty.add(new OrderProperty("istop", OrderProperty.DESC));
		orderproperty.add(new OrderProperty("top_sequence", OrderProperty.ASC));
		return newsType.getNews(page, searchproperty, orderproperty);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.NormalInfoManage#searchActiveNews(com.whaty.platform.util.Page,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public List searchActiveNews(Page page, String title, String beginDate,
			String endDate) throws PlatformException {
		OracleNewsType newsType = new OracleNewsType();
		newsType.setId(this.getNewsTypeId());
		List searchproperty = new ArrayList();
		searchproperty.add(new SearchProperty("title", title, "like"));
		searchproperty.add(new SearchProperty("isactive", "1", "=num"));
		searchproperty.add(new SearchProperty("isPop", "0", "=num"));
		searchproperty.add(new SearchProperty("submit_date", beginDate, "D>="));
		searchproperty.add(new SearchProperty("submit_date", endDate, "D<="));
		List orderproperty = new ArrayList();
		orderproperty.add(new OrderProperty("istop", OrderProperty.DESC));
		orderproperty.add(new OrderProperty("top_sequence", OrderProperty.ASC));
		return newsType.getNews(page, searchproperty, orderproperty);
	}

	public String getMobilePhoneByLogin(String loginId)
			throws PlatformException {
		OracleBasicEntityList entityList = new OracleBasicEntityList();
		return entityList.getMobilePhoneByLoginId(loginId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.NormalInfoManage#getNews(java.lang.String)
	 */
	public News getNews(String id) throws PlatformException {
		OracleNews news = new OracleNews(id);
		if (!news.getStatus().getIsActive())
			throw new PlatformException("this news is not active!");
		else
			return news;
	}

	public News getPreViewNews(String id) throws PlatformException {
		OracleNews news = new OracleNews(id);
		return news;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.NormalInfoManage#getRecentActiveNews(int)
	 */
	public List getRecentActiveNews(int num) throws PlatformException {
		Page page = new Page();
		page.setPageSize(num);
		page.setTotalnum(num);
		page.setPageInt(1);

		return this.getActiveNews(page);

	}

	public List getActiveNews(Page page, String newsTypeId)
			throws PlatformException {
		List searchproperty = new ArrayList();
		searchproperty.add(new SearchProperty("news_type_id", newsTypeId, "in"));
		searchproperty.add(new SearchProperty("isactive", "1", "="));
		searchproperty.add(new SearchProperty("isconfirm", "1", "="));
		List order = new ArrayList();
		order.add(new OrderProperty("report_date", OrderProperty.DESC));
		order.add(new OrderProperty("istop", OrderProperty.DESC));
		order.add(new OrderProperty("submit_date", OrderProperty.DESC));
		OracleInfoList oracleInfoList = new OracleInfoList();
		return oracleInfoList.getNewsList(page, searchproperty, order);
	}

	public int getActiveNewsNum(String newsTypeId) throws PlatformException {
		List searchproperty = new ArrayList();
		searchproperty.add(new SearchProperty("news_type_id", newsTypeId, "="));
		searchproperty.add(new SearchProperty("isactive", "1", "="));
		searchproperty.add(new SearchProperty("isconfirm", "1", "="));
		OracleInfoList oracleInfoList = new OracleInfoList();
		return oracleInfoList.getNewsListNum(searchproperty);
	}

	public List getRecentActiveNews(int num, String newsTypeId)
			throws PlatformException {
		// TODO Auto-generated method stub
		return new ArrayList();
	}

	public List getActivePopNews(Page page, String newsTypeId)
			throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getNewsByTagNum(String tag) throws PlatformException {
		OracleInfoList oracleInfoList = new OracleInfoList();
		return oracleInfoList.getNewsByTagNum(tag);
	}

	public List getNewsByTag(Page page, String tag) throws PlatformException {
		OracleInfoList oracleInfoList = new OracleInfoList();
		return oracleInfoList.getNewsByTag(page, tag);
	}

	public int getNewsByTagListNum(List tagList) throws PlatformException {
		OracleInfoList oracleInfoList = new OracleInfoList();
		return oracleInfoList.getNewsByTagListNum(tagList);
	}

	public List getNewsByTagList(Page page, List tagList)
			throws PlatformException {
		OracleInfoList oracleInfoList = new OracleInfoList();
		return oracleInfoList.getNewsByTagList(page, tagList);
	}

	public List getNewsByTag(String tag, int num) throws PlatformException {
		OracleInfoList oracleInfoList = new OracleInfoList();
		return oracleInfoList.getNewsByTag(tag, num);
	}

	public Map getNewsByTags(Map tagAndNumber) throws PlatformException {
		OracleInfoList oracleInfoList = new OracleInfoList();
		return oracleInfoList.getNewsByTags(tagAndNumber);
	}

	public List getChildNewsTypeByTag(String tag) throws PlatformException {
		OracleInfoList oracleInfoList = new OracleInfoList();
		return oracleInfoList.getChildNewsTypeByTag(tag);
	}

	public NewsType getNewsTypeByTag(String tag) throws PlatformException {
		OracleInfoList oracleInfoList = new OracleInfoList();
		return oracleInfoList.getNewsTypeByTag(tag);
	}

	public List getNextActiveNews(String news_id, int nextNum)
			throws PlatformException {
		OracleInfoList oracleInfoList = new OracleInfoList();
		return oracleInfoList.getNextActiveNews(news_id, nextNum);
	}

	public List getPreActiveNews(String news_id, int preNum)
			throws PlatformException {
		OracleInfoList oracleInfoList = new OracleInfoList();
		return oracleInfoList.getPreActiveNews(news_id, preNum);
	}

	public List getAllSites() throws NoRightException {
		OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
		return basicdatalist.getSites(null, null, null);
	}

	public List getShowSites() throws NoRightException {
		OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
		return basicdatalist.getShowSites(null, null, null);
	}

	public List getShowSitesByDiqu(String diquId) throws NoRightException {
		OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
		List searchProperties = new ArrayList();
		searchProperties.add(new SearchProperty("diqu_id", diquId));
		List orderProperties = new ArrayList();
		orderProperties.add(new OrderProperty("to_number(right)"));
		return basicdatalist.getShowSites(null, searchProperties,
				orderProperties);
	}

	public BasicEntityManage createBasicEntityManage() throws NoRightException, PlatformException {
		PlatformFactory platformfactory = PlatformFactory.getInstance();
		ManagerPriv priv = platformfactory.getManagerPriv();
		return platformfactory.creatBasicEntityManage(priv);
	}

	public int addNewsReadCount(String newsId) throws PlatformException {
		OracleNews news = new OracleNews();
		news.setId(newsId);
		int sub = news.addReadCount();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagerPriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$addNewsReadCount</whaty><whaty>STATUS$|$"
								+ sub
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;
	}

	public int setNewsReadCount(String newsId, int count)
			throws PlatformException {
		OracleNews news = new OracleNews();
		news.setId(newsId);
		int sub = news.updateReadCount(count);
//		UserLog
//				.setInfo(
//						"<whaty>USERID$|$"
//								+ this.getBasicManagerPriv().getSso_id()
//								+ "</whaty><whaty>BEHAVIOR$|$setNewsReadCount</whaty><whaty>STATUS$|$"
//								+ sub
//								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
//								+ LogType.MANAGER
//								+ "</whaty><whaty>PRIORITY$|$"
//								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;
	}

	public NewsType getNewsType(String id) throws PlatformException {
		return new OracleNewsType(id);
	}

	public List getNewsTypes(Page page, String parent_id)
			throws PlatformException {
		OracleInfoList oracleInfoList = new OracleInfoList();
		List searchProperty = new ArrayList();
		if (parent_id == null || parent_id.equals("")
				|| parent_id.equals("null"))
			searchProperty.add(new SearchProperty("parent_id", "", "isNull"));
		else {
			searchProperty.add(new SearchProperty("parent_id", parent_id, "="));
		}
		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("id"));
		return oracleInfoList.getNewsTypes(page, searchProperty, orderProperty);
	}

	public ManagerPriv getBasicManagerPriv() {
		OracleManagerPriv oracleManagerPriv = new OracleManagerPriv();
		oracleManagerPriv.setSso_id(this.basicManagerPriv.getSso_id());
		return oracleManagerPriv;
	}

}
