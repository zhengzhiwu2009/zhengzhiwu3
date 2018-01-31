package com.whaty.platform.database.oracle.standard.campus;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.CampusNormalInfoManage;
import com.whaty.platform.campus.base.CampusInfoList;
import com.whaty.platform.campus.base.CampusNews;
import com.whaty.platform.campus.user.CampusManagerPriv;
import com.whaty.platform.campus.user.Docent;
import com.whaty.platform.database.oracle.standard.campus.base.OracleCampusInfoList;
import com.whaty.platform.database.oracle.standard.campus.base.OracleCampusNews;
import com.whaty.platform.database.oracle.standard.campus.user.OracleCampusManagerPriv;
import com.whaty.platform.database.oracle.standard.campus.user.OracleDocent;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleBasicEntityList;
import com.whaty.platform.database.oracle.standard.entity.user.OracleManagerPriv;
import com.whaty.platform.entity.BasicEntityManage;
import com.whaty.platform.entity.PlatformFactory;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;

public class OracleCampusNormalInfoManage extends CampusNormalInfoManage {

	private ManagerPriv basicManagerPriv;

	public OracleCampusNormalInfoManage() {
		super();
		basicManagerPriv = new OracleManagerPriv();
	}

	public OracleCampusNormalInfoManage(ManagerPriv basicManagerPriv) {
		this.basicManagerPriv = basicManagerPriv;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.NormalInfoManage#getActivePopNews(com.whaty.platform.util.Page)
	 */
	public List getActivePopNews(Page page) throws PlatformException {
		CampusInfoList campusInfoList =new OracleCampusInfoList();
		List searchproperty = new ArrayList();
		searchproperty.add(new SearchProperty("isactive", "1", "=num"));
		searchproperty.add(new SearchProperty("isPop", "1", "=num"));
		List orderproperty = new ArrayList();
		orderproperty.add(new OrderProperty("istop", OrderProperty.DESC));
		orderproperty.add(new OrderProperty("top_sequence", OrderProperty.ASC));
		return campusInfoList.getNewsList(page, searchproperty, orderproperty);
	}

 
//	public List searchActiveNews(Page page, String title, String beginDate,
//			String endDate) throws PlatformException {
//		OracleCampusNewsType newsType = new OracleCampusNewsType();
//		List searchproperty = new ArrayList();
//		searchproperty.add(new SearchProperty("title", title, "like"));
//		searchproperty.add(new SearchProperty("isactive", "1", "=num"));
//		searchproperty.add(new SearchProperty("isPop", "0", "=num"));
//		searchproperty.add(new SearchProperty("submit_date", beginDate, "D>="));
//		searchproperty.add(new SearchProperty("submit_date", endDate, "D<="));
//		List orderproperty = new ArrayList();
//		orderproperty.add(new OrderProperty("istop", OrderProperty.DESC));
//		orderproperty.add(new OrderProperty("top_sequence", OrderProperty.ASC));
//		return newsType.getNews(page, searchproperty, orderproperty);
//	}

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
	public CampusNews getNews(String id) throws PlatformException {
		OracleCampusNews news = new OracleCampusNews(id);
		 Docent oracleDocent = new OracleDocent(news.getDocent_Key());
		 news.setDecentInfo(oracleDocent);
		if (!news.getStatus().getIsActive()){
			throw new PlatformException("this news is not active!");
		}else{
			return news;
		}
	}

	public CampusNews getPreViewNews(String id) throws PlatformException {
		OracleCampusNews news = new OracleCampusNews(id);
		return news;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.NormalInfoManage#getRecentActiveNews(int)
	 */
	 

	public List getActiveNews(Page page) throws PlatformException {
		List searchproperty = new ArrayList();
		searchproperty.add(new SearchProperty("isactive", "1", "="));
		searchproperty.add(new SearchProperty("isconfirm", "1", "="));
		List order = new ArrayList();
		order.add(new OrderProperty("istop", OrderProperty.DESC));
		order.add(new OrderProperty("report_date", OrderProperty.DESC));
		order.add(new OrderProperty("submit_date", OrderProperty.DESC));
		OracleCampusInfoList oracleInfoList = new OracleCampusInfoList();
		return oracleInfoList.getNewsList(page, searchproperty, order);
	}

	public int getActiveNewsNum(String newsTypeId) throws PlatformException {
		List searchproperty = new ArrayList();
		searchproperty.add(new SearchProperty("news_type_id", newsTypeId, "="));
		searchproperty.add(new SearchProperty("isactive", "1", "="));
		OracleCampusInfoList oracleInfoList = new OracleCampusInfoList();
		return oracleInfoList.getNewsListNum(searchproperty);
	}

	public List getRecentActiveNews( )
			throws PlatformException {
		return new ArrayList();
	}

	public List getActivePopNews(Page page, String newsTypeId)
			throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	public List getNextActiveNews(String news_id, int nextNum)
			throws PlatformException {
		OracleCampusInfoList oracleInfoList = new OracleCampusInfoList();
		return oracleInfoList.getNextActiveNews(news_id, nextNum);
	}

	public List getPreActiveNews(String news_id, int preNum)
			throws PlatformException {
		OracleCampusInfoList oracleInfoList = new OracleCampusInfoList();
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

	public BasicEntityManage createBasicEntityManage() throws NoRightException,PlatformException {
		PlatformFactory platformfactory = PlatformFactory.getInstance();
		ManagerPriv priv = platformfactory.getManagerPriv();
		return platformfactory.creatBasicEntityManage(priv);
	}

	public int addNewsReadCount(String newsId) throws PlatformException {
		OracleCampusNews news = new OracleCampusNews();
		news.setId(newsId);
		int sub = news.addReadCount();
		return sub;
	}

	public int setNewsReadCount(String newsId, int count)
			throws PlatformException {
		OracleCampusNews news = new OracleCampusNews();
		news.setId(newsId);
		int sub = news.updateReadCount(count);
		return sub;
	}

//	public CampusNewsType getNewsType(String id) throws PlatformException {
//		return new OracleCampusNewsType(id);
//	}

	public CampusManagerPriv getBasicManagerPriv() {
		OracleCampusManagerPriv oracleManagerPriv = new OracleCampusManagerPriv();
		oracleManagerPriv.setManagerId(this.basicManagerPriv.getSso_id());
		return oracleManagerPriv;
	}

	public int getActiveNewsNum() throws PlatformException {
		List searchproperty = new ArrayList();
		searchproperty.add(new SearchProperty("isactive", "1", "="));
		OracleCampusInfoList oracleInfoList = new OracleCampusInfoList();
		return oracleInfoList.getNewsListNum(searchproperty);
	}

	public List getRecentActiveNews(int num) throws PlatformException {
		Page page = new Page();
		page.setPageSize(num);
		page.setTotalnum(num);
		page.setPageInt(1);
		return this.getActiveNews(page);

	}

	public List getTopPop() throws PlatformException {
		CampusInfoList campusInfoList =new OracleCampusInfoList();
		List searchproperty = new ArrayList();
		searchproperty.add(new SearchProperty("isactive", "1", "=num"));
		searchproperty.add(new SearchProperty("isconfirm", "1", "="));
		List orderproperty = new ArrayList();
		orderproperty.add(new OrderProperty("read_count", OrderProperty.DESC));
		orderproperty.add(new OrderProperty("istop", OrderProperty.DESC));
		orderproperty.add(new OrderProperty("top_sequence", OrderProperty.ASC));
		return campusInfoList.getNewsList(null, searchproperty, orderproperty);
	}
}
