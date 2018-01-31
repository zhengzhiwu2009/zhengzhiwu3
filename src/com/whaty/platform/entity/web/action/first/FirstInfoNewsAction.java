package com.whaty.platform.entity.web.action.first;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeBulletin;
import com.whaty.platform.entity.bean.PeInfoNews;
import com.whaty.platform.entity.bean.PeJianzhang;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 网站前台新闻发布
 * 
 * @author 李冰
 * 
 */
public class FirstInfoNewsAction extends MyBaseAction {
    
	private String type; // 获取新闻类型、
	private List<PeBulletin> Bulletin;
	private List<PeInfoNews> wydt; // 网院动态
	private List<PeInfoNews> wjdt; // 网教动态
	private List<PeInfoNews> zcfg; // 政策法规
	private PeInfoNews peInfoNews; // 用于显示新闻的详细内容
	private PeJianzhang jianzhang; // 活动的招生简章，用于首页查看
	private String searchTitle; // 用于新闻列表页面的搜索功能
	private PeBulletin peBulletin; // 用于显示公告详细信息

	// 以下字段用于分页显示
	private List<PeInfoNews> newsList; // 新闻列表
	private List<PeInfoNews> tpnewsList; // 图片新闻列表
	private List<PeInfoNews> bzfc; // 组长风采
	private List<PeInfoNews> jsfc; // 教师风采
	private List<PeInfoNews> qhrw; //清华人文
	private List<PeInfoNews> xywy; //学员文苑
	private List<PeInfoNews> jxjw; //教学教务
	private int begin; // 列表开始的位置
	private int number; // 每页要显示的信息个数
	private int curPage; // 当前页
	private int nextPage; // 下一页
	private int prePage; // 上一页
	private int totalPage; // 总页数
	private int totalCount; // 新闻总数

	/**
	 * 左侧列表显示的新闻
	 * 
	 * @return
	 */
	public String toLeft() {
		this.setWydt(this.leftNews("网院动态"));
		this.setWjdt(this.leftNews("网教动态"));
		this.setZcfg(this.leftNews("政策法规"));
		return "left";
	}

	/**
	 * 根据新闻类型返回前三条新闻
	 * 
	 * @param type
	 *            新闻类型
	 * @return
	 */
	private List<PeInfoNews> leftNews(String type) {
		List<PeInfoNews> news = new ArrayList();
		DetachedCriteria dcWYDT = DetachedCriteria.forClass(PeInfoNews.class);
		DetachedCriteria dctype = dcWYDT.createCriteria("peInfoNewsType",
				"peInfoNewsType");
		dctype.add(Restrictions.eq("name", type));
		DetachedCriteria dcFlagNewsStatus = dcWYDT.createCriteria(
				"enumConstByFlagNewsStatus", "enumConstByFlagNewsStatus");
		DetachedCriteria dcFlagIsactive = dcWYDT.createCriteria(
				"enumConstByFlagIsactive", "enumConstByFlagIsactive");
		dcFlagNewsStatus.add(Restrictions.eq("code", "1"));
		dcFlagIsactive.add(Restrictions.eq("code", "1"));
		dcWYDT.addOrder(Order.desc("reportDate"));

		try {

			List<PeInfoNews> list = this.getGeneralService().getList(dcWYDT);
			
			for (int i = 0; i < list.size(); i++) {
				if (i > 2) {
					break;
				}
				//this.newsTitle(list.get(i), 13);
				this.listIsNew(list.get(i));
				news.add(list.get(i));
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return news;
	}

	/**
	 * 对新闻标题保留合适的长度
	 * 
	 * @param peInfoNews
	 *            需处理的新闻
	 * @param length
	 *            保留标题长度
	 */
	private void newsTitle(PeInfoNews peInfoNews, int length) {
		String title = peInfoNews.getTitle();
		if (title.length() > length) {
			title = title.substring(0, length) + "...";
			peInfoNews.setTitle(title);
		}

	}
	
	private void newsSummary(PeInfoNews peInfoNews, int length) {
		String summary = peInfoNews.getSummary();
		if (summary.length() > length) {
			summary = summary.substring(0, length) + "...";
			peInfoNews.setSummary(summary);
		}

	}

	/**
	 * 查看新闻详细内容页面。点击次数+1
	 * 
	 * @return
	 */
	public String toInfoNews() {
		try {
			PeInfoNews news = (PeInfoNews) this.getGeneralService().getById(
					this.getBean().getId());
			// 点击次数+1
			news.setReadCount(news.getReadCount() + 1);

			this.getGeneralService().save(news);

			this.setPeInfoNews(news);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "infoNews";
	}
	
	public String toInfoDetail() {
		try {
			PeInfoNews news = (PeInfoNews) this.getGeneralService().getById(
					this.getBean().getId());
			// 点击次数+1
			news.setReadCount(news.getReadCount() + 1);

			this.getGeneralService().save(news);

			this.setPeInfoNews(news);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "infoDetail";
	}
	
	public String toXywyDetail() {
		try {
			PeInfoNews news = (PeInfoNews) this.getGeneralService().getById(
					this.getBean().getId());
			// 点击次数+1
			news.setReadCount(news.getReadCount() + 1);

			this.getGeneralService().save(news);

			this.setPeInfoNews(news);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "xywyDetail";
	}
	
	public String toQhrwDetail() {
		try {
			PeInfoNews news = (PeInfoNews) this.getGeneralService().getById(
					this.getBean().getId());
			// 点击次数+1
			news.setReadCount(news.getReadCount() + 1);

			this.getGeneralService().save(news);

			this.setPeInfoNews(news);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "qhrwDetail";
	}

	/**
	 * 新闻列表页面
	 * 
	 * @return
	 */
	public String toNewsList() {
		if(this.formToken != null && !"".equals(this.formToken)) {
			if(!this.validateToken(this.formToken)) {
				return "toBulletinList";
			}
		}
		
		/**
		 * 设定number
		 */
		if (this.getNumber() <= 0) {
			this.setNumber(10);
		}
		/**
		 * 首先设定默认开始页为第一页
		 */
		if (this.getCurPage() == 0) {
			this.setCurPage(1);
		}
		/**
		 * 设定beain
		 */
		if (this.getCurPage() > 1) {

			int temp = (this.getCurPage() - 1) * this.getNumber();
			this.setBegin(temp);
		} else {
			this.setBegin(0);
		}
		
		Page infoPage = new Page(null, 0, 0, 0);
		// 设置查询条件
		DetachedCriteria dcNews = DetachedCriteria.forClass(PeInfoNews.class);
		DetachedCriteria dctype = dcNews.createCriteria("peInfoNewsType",
				"peInfoNewsType");
		if (this.getType() != null && this.getType().length() != 0) {
			dctype.add(Restrictions.eq("id", this.getType()));
		}
		
		DetachedCriteria dcFlagNewsStatus = dcNews.createCriteria(
				"enumConstByFlagNewsStatus", "enumConstByFlagNewsStatus");
		DetachedCriteria dcFlagIsactive = dcNews.createCriteria(
				"enumConstByFlagIsactive", "enumConstByFlagIsactive");
		dcFlagNewsStatus.add(Restrictions.eq("code", "1"));
		dcFlagIsactive.add(Restrictions.eq("code", "1"));
		if (this.getSearchTitle() != null
				&& this.getSearchTitle().length() != 0) {
			dcNews.add(Restrictions.like("title", this.getSearchTitle(),
					MatchMode.ANYWHERE));
		}
		//System.out.println("title:"+this.getSearchTitle());
		dcNews.addOrder(Order.desc("reportDate"));

		ProjectionList infoProjectionList = Projections.projectionList();
		infoProjectionList.add(Projections.property("id"));
		infoProjectionList.add(Projections.property("title"));
		infoProjectionList.add(Projections.property("reportDate"));
		infoProjectionList.add(Projections.property("readCount"));
		dcNews.setProjection(infoProjectionList);
		// 取出新闻
		try {
			infoPage = this.getGeneralService().getByPage(dcNews,
					this.getNumber(), this.getBegin());
		} catch (EntityException e) {
			// 
			e.printStackTrace();
		}
		List<PeInfoNews> peInfoNewsList = new ArrayList();
		List tempList = infoPage.getItems();
		int num = (this.getCurPage() - 1) * this.getNumber() + 1;
		// 将取出的新闻设置到list中
		for (int i = 0; i < tempList.size(); i++) {
			PeInfoNews instance = new PeInfoNews();
			instance.setId(((Object[]) tempList.get(i))[0].toString());
			instance.setTitle(((Object[]) tempList.get(i))[1].toString());
			instance
					.setReportDate(((Timestamp) ((Object[]) tempList.get(i))[2]));
			instance.setReadCount(Long
					.parseLong(((Object[]) tempList.get(i))[3].toString()));
			this.listIsNew(instance);
			instance.setConfirmManagerId(num + ""); // 设置序号，暂保持到ConfirmManagerId中
			num++;
			peInfoNewsList.add(instance);
		}
		this.setNewsList(peInfoNewsList);
		/**
		 * 求出总页数
		 */
		double tempTotalPage = (double) infoPage.getTotalCount()
				/ (double) this.getNumber();
		
		/**
		 * 设置总页数
		 */
		if (tempTotalPage <= 1.0) {
			this.setTotalPage(1);
		} else if (tempTotalPage > 1.0 && tempTotalPage < 2.0) {
			this.setTotalPage(2);
		} else {
			if (infoPage.getTotalCount() % this.getNumber() > 0) {
				this.setTotalPage(infoPage.getTotalCount() / this.getNumber()
						+ 1);
			} else
				this.setTotalPage(infoPage.getTotalCount() / this.getNumber());
		}
		// 设置新闻总数
		this.setTotalCount(infoPage.getTotalCount());
		if (this.getCurPage() < 2) {
			this.setPrePage(-1); // 当前页面为1时，页面不再显示上一页和首页

			if (this.getTotalPage() < 2) {
				this.setTotalPage(1);
				this.setNextPage(-1); // 当总页数为1的时候，不再显示下一页和末页
			} else
				this.setNextPage(this.getCurPage() + 1);
		} else {
			this.setPrePage(this.getCurPage() - 1);
			if (this.getCurPage() >= this.getTotalPage()) {
				this.setNextPage(-1); // 当前页如果是最大页码数，不再显示下一页和末页
			} else
				this.setNextPage(this.getCurPage() + 1);
		}
		return "newstest";
	}

	/*
	 * 判断报道时间，最近一天的显示new
	 * 
	 * param peInfoNews
	 */
	private void listIsNew(PeInfoNews peInfoNews) {
		Date now = new Date();
		Long time = now.getTime() - peInfoNews.getReportDate().getTime();
		if (time <= 172800000) {
			// 暂定new的备注为new
			peInfoNews.setIsNew("1");
		} else {
			peInfoNews.setIsNew("0");
		}
	}

	/**
	 * 学院公告列表 显示8条新闻
	 * 
	 * @return
	 *
	 */
	
	
	
	@SuppressWarnings("unchecked")
	public List getInfoList(String type){
		// 首页新闻
		List<PeInfoNews> infolist = new ArrayList<PeInfoNews>();
		DetachedCriteria dcWYDT = DetachedCriteria.forClass(PeInfoNews.class);
		DetachedCriteria dcFlagNewsStatus = dcWYDT.createCriteria(
			"enumConstByFlagNewsStatus", "enumConstByFlagNewsStatus");
		DetachedCriteria dcFlagIsactive = dcWYDT.createCriteria(
			"enumConstByFlagIsactive", "enumConstByFlagIsactive");
		dcFlagNewsStatus.add(Restrictions.eq("code", "1"));
		dcFlagIsactive.add(Restrictions.eq("code", "1"));
		DetachedCriteria dctype = dcWYDT.createCriteria("peInfoNewsType",
		"peInfoNewsType", DetachedCriteria.LEFT_JOIN);
		
		if(type.equals("首页新闻")){
		    dctype.add(Restrictions.eq("name", "政策新闻"));
			//dctype.add(Restrictions.eq("id", "_xygg"));
		    dcWYDT.addOrder(Order.desc("reportDate"));
		}
		if(type.equals("图片新闻")){
		    dctype.add(Restrictions.eq("name", "政策新闻"));
			//dctype.add(Restrictions.eq("id", "_xygg"));
		    dcWYDT.add(Restrictions.isNotNull("pictrue"));
		    dcWYDT.addOrder(Order.desc("reportDate"));
		}
		if(type.equals("组长风采")){
		    dctype.add(Restrictions.eq("name","优秀班组长"));
			//dctype.add(Restrictions.eq("id", "_yxbz"));
		    dcWYDT.addOrder(Order.desc("title"));
		}
		if(type.equals("教师风采")){
		    dctype.add(Restrictions.eq("name", "优秀教师"));
			//dctype.add(Restrictions.eq("id", "_yxjs"));
		    dcWYDT.addOrder(Order.desc("title"));
		}
		if(type.equals("清华人文"))
		{
			dctype.add(Restrictions.eq("name", "清华人文"));
		    dcWYDT.addOrder(Order.desc("reportDate"));
		}
		if(type.equals("学员文苑"))
		{
			dctype.add(Restrictions.eq("name", "学员文苑"));
			dcWYDT.addOrder(Order.desc("reportDate"));
		}
		if(type.equals("教学教务"))
		{
			dctype.add(Restrictions.eq("name", "教学教务"));
		    dcWYDT.addOrder(Order.desc("reportDate"));
		}
		
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("id"));
		projectionList.add(Projections.property("title"));
		projectionList.add(Projections.property("summary"));
		projectionList.add(Projections.property("reportDate"));
		projectionList.add(Projections.property("submitDate"));
		projectionList.add(Projections.property("isNew"));
		projectionList.add(Projections.property("pictrue"));
		
		dcWYDT.setProjection(projectionList);
		
		List tempList=new ArrayList();
		
		try {
			tempList =  this.getGeneralService().getDetachList(dcWYDT);
		} catch (EntityException e) {
		    e.printStackTrace();
		}
		
		for (int i = 0; i < tempList.size(); i++) {
			PeInfoNews instance = new PeInfoNews();
			instance.setId(((Object[]) tempList.get(i))[0].toString());
			instance.setTitle(((Object[]) tempList.get(i))[1].toString());
			instance.setSummary(((Object[]) tempList.get(i))[2].toString());
			instance
					.setReportDate(((Timestamp) ((Object[]) tempList.get(i))[3]));
			instance
					.setSubmitDate(((Timestamp) ((Object[]) tempList.get(i))[4]));
			instance.setIsNew(((Object[]) tempList.get(i))[5].toString());
			Object o=((Object[]) tempList.get(i))[6];
			if(o!=null)
				instance.setPictrue(((Object[]) tempList.get(i))[6].toString());
			infolist.add(instance);
		}
		
		return infolist;
	}
	

	public String toIndex() {
	    List<PeInfoNews> tmpe = new ArrayList<PeInfoNews>();
	    	//组长风采
		List bzlist  = this.getInfoList("组长风采");
		
		if (bzlist.size() >= 0 && bzlist.size() < 6) {
			for (int i = 0; i < bzlist.size(); i++) {
				if (i > 5) {
					break;
				}
			//	this.newsTitle(list.get(i), 7);
				// this.listIsNew(list.get(i));
				tmpe.add((PeInfoNews) bzlist.get(i));
			}
			for (int n = 0; n < 6 - bzlist.size(); n++) {
				PeInfoNews infoNews = new PeInfoNews();
				infoNews.setPictrue("/web/bzz_index/images/b_7.jpg");
				infoNews.setTitle("班组长");
				infoNews.setId("1");
				tmpe.add(infoNews);
			}
		} else {
			for (int i = 0; i < bzlist.size(); i++) {
				if (i > 5) {
					break;
				}
				this.newsTitle((PeInfoNews)bzlist.get(i), 7);
				this.listIsNew((PeInfoNews)bzlist.get(i));
				tmpe.add((PeInfoNews)bzlist.get(i));
			}
		}
		
		this.setBzfc(tmpe);
		
		 List<PeInfoNews> tempjslist = new ArrayList<PeInfoNews>();
		
		//教师风采 
		List jslist  = this.getInfoList("教师风采");
		int k = jslist.size();
		if (k < 6 && k >= 0) {
			for (int m = 0; m < k; m++) {
				//this.newsTitle(jslist.get(m), 7);
				// this.listIsNew(jslist.get(m));
			    tempjslist.add((PeInfoNews) jslist.get(m));
			}
			for (int n = 0; n < 6 - jslist.size(); n++) {
				PeInfoNews jsinfo = new PeInfoNews();
				jsinfo.setPictrue("/web/bzz_index/images/b_5.jpg");
				jsinfo.setTitle("优秀教师");
				jsinfo.setId("1");
				tempjslist.add(jsinfo);
			}
		} else {
			for (int m = 0; m < k; m++) {
				if (m > 7) {
					break;
				}
				this.newsTitle((PeInfoNews)jslist.get(m), 7);
				 this.listIsNew((PeInfoNews)jslist.get(m));
				 tempjslist.add((PeInfoNews) jslist.get(m));
			}
		}
		this.setJsfc(tempjslist);
		
		
		 List<PeInfoNews> temptplist = new ArrayList<PeInfoNews>();
		//图片新闻
		List tplist  = this.getInfoList("图片新闻");
		for (int i = 0; i < tplist.size(); i++) {
			if (i > 3) {
				break;
			}
			//this.newsTitle(list.get(i), 16);
			// this.listIsNew(list.get(i));
			temptplist.add((PeInfoNews) tplist.get(i));
		}
		this.setTpnewsList(temptplist);
		
		List<PeInfoNews> tempinfolist = new ArrayList<PeInfoNews>();	
		//首页新闻
		List infolist  = this.getInfoList("首页新闻");
		
		for (int i = 0; i < infolist.size(); i++) {
			if (i > 5) {
				break;
			}
			this.newsTitle((PeInfoNews) infolist.get(i), 26);
			this.listIsNew((PeInfoNews) infolist.get(i));
			tempinfolist.add((PeInfoNews) infolist.get(i));
		}
		this.setNewsList(tempinfolist);
		
		 List<PeInfoNews> tempjxjwlist = new ArrayList<PeInfoNews>();	
		//教学教务
		List jxjwlist  = this.getInfoList("教学教务");
		
		for (int i = 0; i < jxjwlist.size(); i++) {
	  		if (i > 4) {
				break;
			}
//	  		this.newsTitle((PeInfoNews) infolist.get(i), 26);
//	  		this.listIsNew((PeInfoNews) infolist.get(i));
	  		tempjxjwlist.add((PeInfoNews) jxjwlist.get(i));
		}
		this.setJxjw(tempjxjwlist);
		
		List<PeInfoNews> tempqhrwlist = new ArrayList<PeInfoNews>();	
		//清华人文
		List qhrwlist  = this.getInfoList("清华人文");
		
		for (int i = 0; i < qhrwlist.size(); i++) {
	  		if (i > 12) {
				break;
			}
	  		this.newsTitle((PeInfoNews) qhrwlist.get(i), 12);
	  		this.listIsNew((PeInfoNews) qhrwlist.get(i));
	  		tempqhrwlist.add((PeInfoNews) qhrwlist.get(i));
		}
		
		if(qhrwlist.size()<13)
		{
			for(int i=0; i< 13-qhrwlist.size();i++ )
			{
				PeInfoNews tmpnews=new PeInfoNews();
				tmpnews.setTitle(" ");
				tempqhrwlist.add(tmpnews);
			}
		}
		this.setQhrw(tempqhrwlist);
		
		List<PeInfoNews> tempxywylist = new ArrayList<PeInfoNews>();	
		//学员文苑
		List xywylist  = this.getInfoList("学员文苑");
		
		for (int i = 0; i < xywylist.size(); i++) {
	  		if (i > 4) {
				break;
			}
	  		this.newsTitle((PeInfoNews) xywylist.get(i), 12);
	  		this.newsSummary((PeInfoNews) xywylist.get(i), 18);
	  		this.listIsNew((PeInfoNews) xywylist.get(i));
	  		tempxywylist.add((PeInfoNews) xywylist.get(i));
		}
		
		if(xywylist.size()<5)
		{
			for(int i=0; i< 5-xywylist.size();i++ )
			{
				PeInfoNews tmpnews=new PeInfoNews();
				tmpnews.setTitle("征集中...");
				tempxywylist.add(tmpnews);
			}
		}
		this.setXywy(tempxywylist);

		// 首页通知------update by longyinsong
		List<PeBulletin> notes = new ArrayList<PeBulletin>();
		DetachedCriteria dcNote = DetachedCriteria.forClass(PeBulletin.class);
		dcNote.createCriteria("enumConstByFlagIstop", "enumConstByFlagIstop");
		dcNote.createCriteria("enumConstByFlagIsvalid",
				"enumConstByFlagIsvalid");
		dcNote.add(Restrictions
				.like("scopeString", "index", MatchMode.ANYWHERE));
		dcNote.add(Restrictions.eq("enumConstByFlagIsvalid.code", "1"));
		dcNote.addOrder(Order.desc("enumConstByFlagIstop.code")).addOrder(
				Order.desc("publishDate"));
		List plist = null;
		try {
			 plist = this.getGeneralService().getList(dcNote);
				 for (int i = 0; i < plist.size(); i++) {
					 if (7 < i) {
						 break;
					 }
					// this.NoteTitle((PeBulletin)plist.get(i), 18);
					 this.NoteIsNew((PeBulletin)plist.get(i));
					 notes.add((PeBulletin)plist.get(i));
				 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setBulletin(notes);

		return "index";
	}
	
	public String toIndex1 () {
		//this.toIndex();
//		System.out.println("++++++++++++");
		return "index1";
	}
	
	public String toIndexNew () {
		this.toIndex();
		return "indexNew";
	}

	private void NoteTitle(PeBulletin peBulletin, int length) {
		String title = peBulletin.getTitle();
		if (title.length() > length) {
			title = title.substring(0, length) + "...";
			peBulletin.setTitle(title);
		}
	}

	private void NoteIsNew(PeBulletin peBulletin) {
		String title = peBulletin.getTitle();
		Date now = new Date();
		Long time = now.getTime() - peBulletin.getPublishDate().getTime();
		if (time <= 86400000) {
			// 暂定new的备注为new
//			peBulletin.setNote("new");
		}
	}

	/**
	 * 新闻搜索
	 * 
	 * @return
	 */
	public String search() {

		/**
		 * 设定number
		 */
		if (this.getNumber() <= 0) {
			this.setNumber(10);
		}

		/**
		 * 首先设定默认开始页为第一页
		 */
		if (this.getCurPage() == 0) {
			this.setCurPage(1);
		}
		/**
		 * 设定beain
		 */
		if (this.getCurPage() > 1) {

			int temp = (this.getCurPage() - 1) * this.getNumber();
			//System.out.println(this.getCurPage() + "*" + this.getNumber() + "=" + temp);
			this.setBegin(temp);
		} else {
			this.setBegin(0);
		}
		
		Page infoPage = new Page(null, 0, 0, 0);
		// 设置查询条件
		DetachedCriteria dcNews = DetachedCriteria.forClass(PeInfoNews.class);
		DetachedCriteria dctype = dcNews.createCriteria("peInfoNewsType",
				"peInfoNewsType");
		if (this.getType() != null && this.getType().length() != 0) {
			dctype.add(Restrictions.eq("id", this.getType()));
		}
		DetachedCriteria dcFlagNewsStatus = dcNews.createCriteria(
				"enumConstByFlagNewsStatus", "enumConstByFlagNewsStatus");
		DetachedCriteria dcFlagIsactive = dcNews.createCriteria(
				"enumConstByFlagIsactive", "enumConstByFlagIsactive");
		dcFlagNewsStatus.add(Restrictions.eq("code", "1"));
		dcFlagIsactive.add(Restrictions.eq("code", "1"));
		if (this.getSearchTitle() != null
				&& this.getSearchTitle().length() != 0) {
			dcNews.add(Restrictions.like("title", this.getSearchTitle(),
					MatchMode.ANYWHERE));
		}
		dcNews.addOrder(Order.desc("reportDate"));

		ProjectionList infoProjectionList = Projections.projectionList();
		infoProjectionList.add(Projections.property("id"));
		infoProjectionList.add(Projections.property("title"));
		infoProjectionList.add(Projections.property("reportDate"));
		infoProjectionList.add(Projections.property("readCount"));
		dcNews.setProjection(infoProjectionList);

		// 取出新闻
		try {
			infoPage = this.getGeneralService().getByPage(dcNews,
					this.getNumber(), this.getBegin());
			//System.out.println("infoPage");
			//System.out.println(infoPage);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<PeInfoNews> peInfoNewsList = new ArrayList();
		List tempList = infoPage.getItems();
		int num = (this.getCurPage() - 1) * this.getNumber() + 1;
		// 将取出的新闻设置到list中
		for (int i = 0; i < tempList.size(); i++) {
			PeInfoNews instance = new PeInfoNews();
			instance.setId(((Object[]) tempList.get(i))[0].toString());
			instance.setTitle(this.replaceTitle(((Object[]) tempList.get(i))[1]
					.toString()));
			instance
					.setReportDate(((Timestamp) ((Object[]) tempList.get(i))[2]));
			instance.setReadCount(Long
					.parseLong(((Object[]) tempList.get(i))[3].toString()));
			this.listIsNew(instance);
			instance.setConfirmManagerId(num + ""); // 设置序号，暂保持到ConfirmManagerId中
			num++;
			peInfoNewsList.add(instance);
		}
		this.setNewsList(peInfoNewsList);

		/**
		 * 求出总页数
		 */
		double tempTotalPage = (double) infoPage.getTotalCount()
				/ (double) this.getNumber();
		/**
		 * 设置总页数
		 */
		if (tempTotalPage <= 1.0) {
			this.setTotalPage(1);
		} else if (tempTotalPage > 1.0 && tempTotalPage < 2.0) {
			this.setTotalPage(2);
		} else {
			if (infoPage.getTotalCount() % this.getNumber() > 0) {
				this.setTotalPage(infoPage.getTotalCount() / this.getNumber()
						+ 1);
			} else
				this.setTotalPage(infoPage.getTotalCount() / this.getNumber());
		}
		// 设置新闻总数
		this.setTotalCount(infoPage.getTotalCount());

		if (this.getCurPage() < 2) {
			this.setPrePage(-1); // 当前页面为1时，页面不再显示上一页和首页

			if (this.getTotalPage() < 2) {
				this.setTotalPage(1);
				this.setNextPage(-1); // 当总页数为1的时候，不再显示下一页和末页
			} else
				this.setNextPage(this.getCurPage() + 1);
		} else {
			this.setPrePage(this.getCurPage() - 1);
			if (this.getCurPage() >= this.getTotalPage()) {
				this.setNextPage(-1); // 当前页如果是最大页码数，不再显示下一页和末页
			} else
				this.setNextPage(this.getCurPage() + 1);
		}

		return "newstest";
	}

	/**
	 * 将title中搜索的关键字高亮显示
	 * 
	 * @param str
	 * @return
	 */
	private String replaceTitle(String str) {
		String title = str.replaceAll(this.getSearchTitle(),
				"<font color=\"red\" >" + this.getSearchTitle() + "</font>");
		return title;
	}

	public String turnToJianzhang() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeJianzhang.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		try {
			List<PeJianzhang> list = this.getGeneralService().getList(dc);
			if (list != null && !list.isEmpty()) {
				this.setJianzhang(list.get(0));
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "jianzhang";
	}
	
	public String  newstest() {
		DetachedCriteria dcWYDT = DetachedCriteria.forClass(PeInfoNews.class);
		DetachedCriteria dctype = dcWYDT.createCriteria("peInfoNewsType",
				"peInfoNewsType", DetachedCriteria.LEFT_JOIN);
		dctype.add(Restrictions.eq("name", "政策新闻"));
		DetachedCriteria dcFlagNewsStatus = dcWYDT.createCriteria(
				"enumConstByFlagNewsStatus", "enumConstByFlagNewsStatus");
		DetachedCriteria dcFlagIsactive = dcWYDT.createCriteria(
				"enumConstByFlagIsactive", "enumConstByFlagIsactive");
		dcFlagNewsStatus.add(Restrictions.eq("code", "1"));
		dcFlagIsactive.add(Restrictions.eq("code", "1"));
		dcWYDT.addOrder(Order.desc("reportDate"));
		try {
			this.setNewsList(this.getGeneralService().getList(dcWYDT));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "newstest";
	}

	public String allNews() {
		
		return "allnews";
	}

	@Override
	public void initGrid() {
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeInfoNews.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/first/firstInfoNews";
	}

	public void setBean(PeInfoNews peInfoNews) {
		super.superSetBean(peInfoNews);

	}

	public PeInfoNews getBean() {
		return (PeInfoNews) super.superGetBean();
	}

	public List<PeInfoNews> getWydt() {
		return wydt;
	}

	public void setWydt(List<PeInfoNews> wydt) {
		this.wydt = wydt;
	}

	public List<PeInfoNews> getWjdt() {
		return wjdt;
	}

	public void setWjdt(List<PeInfoNews> wjdt) {
		this.wjdt = wjdt;
	}

	public List<PeInfoNews> getZcfg() {
		return zcfg;
	}

	public void setZcfg(List<PeInfoNews> zcfg) {
		this.zcfg = zcfg;
	}

	public PeInfoNews getPeInfoNews() {
		return peInfoNews;
	}

	public void setPeInfoNews(PeInfoNews peInfoNews) {
		this.peInfoNews = peInfoNews;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public List<PeInfoNews> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<PeInfoNews> newsList) {
		this.newsList = newsList;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getSearchTitle() {
		return searchTitle;
	}

	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}

	public PeJianzhang getJianzhang() {
		return jianzhang;
	}

	public void setJianzhang(PeJianzhang jianzhang) {
		this.jianzhang = jianzhang;
	}

	public List<PeBulletin> getBulletin() {
		return Bulletin;
	}

	public void setBulletin(List<PeBulletin> bulletin) {
		Bulletin = bulletin;
	}

	public PeBulletin getPeBulletin() {
		return peBulletin;
	}

	public void setPeBulletin(PeBulletin peBulletin) {
		this.peBulletin = peBulletin;
	}

	public List<PeInfoNews> getTpnewsList() {
		return tpnewsList;
	}

	public void setTpnewsList(List<PeInfoNews> tpnewsList) {
		this.tpnewsList = tpnewsList;
	}

	public List<PeInfoNews> getBzfc() {
		return bzfc;
	}

	public List<PeInfoNews> getJsfc() {
		return jsfc;
	}

	public void setBzfc(List<PeInfoNews> bzfc) {
		this.bzfc = bzfc;
	}

	public void setJsfc(List<PeInfoNews> jsfc) {
		this.jsfc = jsfc;
	}

	public List<PeInfoNews> getQhrw() {
		return qhrw;
	}

	public void setQhrw(List<PeInfoNews> qhrw) {
		this.qhrw = qhrw;
	}

	public List<PeInfoNews> getXywy() {
		return xywy;
	}

	public void setXywy(List<PeInfoNews> xywy) {
		this.xywy = xywy;
	}

	public List<PeInfoNews> getJxjw() {
		return jxjw;
	}

	public void setJxjw(List<PeInfoNews> jxjw) {
		this.jxjw = jxjw;
	}

	
	// old infonews
	//{
	 // 组长风采
		/*List<PeInfoNews> tmpe = new ArrayList<PeInfoNews>();
		DetachedCriteria tmpedc = DetachedCriteria.forClass(PeInfoNews.class);
		DetachedCriteria peInfoNewsType1 = tmpedc.createCriteria(
				"peInfoNewsType", "peInfoNewsType", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria FlagNewsStatus1 = tmpedc.createCriteria(
				"enumConstByFlagNewsStatus", "enumConstByFlagNewsStatus");
		DetachedCriteria FlagIsactive1 = tmpedc.createCriteria(
				"enumConstByFlagIsactive", "enumConstByFlagIsactive");
		FlagNewsStatus1.add(Restrictions.eq("code", "1"));
		FlagIsactive1.add(Restrictions.eq("code", "1"));
		peInfoNewsType1.add(Restrictions.eq("name", "优秀班组长"));
		//tmpedc.add(Restrictions.isNotNull("pictrue"));
		tmpedc.addOrder(Order.asc("title"));

		try {
			List<PeInfoNews> list = this.getGeneralService().getList(tmpedc);
			if (list.size() >= 0 && list.size() < 6) {
				for (int i = 0; i < list.size(); i++) {
					if (i > 5) {
						break;
					}
				//	this.newsTitle(list.get(i), 7);
					// this.listIsNew(list.get(i));
					tmpe.add(list.get(i));
				}
				for (int n = 0; n < 6 - list.size(); n++) {
					PeInfoNews infoNews = new PeInfoNews();
					infoNews.setPictrue("/web/bzz_index/images/b_7.jpg");
					infoNews.setTitle("班组长");
					infoNews.setId("1");
					tmpe.add(infoNews);
				}
			} else {
				for (int i = 0; i < list.size(); i++) {
					if (i > 5) {
						break;
					}
				//	this.newsTitle(list.get(i), 7);
					// this.listIsNew(list.get(i));
					tmpe.add(list.get(i));
				}
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setBzfc(tmpe);

		// 教师风采

		DetachedCriteria jstmpedc = DetachedCriteria.forClass(PeInfoNews.class);
		DetachedCriteria peInfoNewsType2 = jstmpedc.createCriteria(
				"peInfoNewsType", "peInfoNewsType", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria FlagNewsStatus2 = jstmpedc.createCriteria(
				"enumConstByFlagNewsStatus", "enumConstByFlagNewsStatus");
		DetachedCriteria FlagIsactive2 = jstmpedc.createCriteria(
				"enumConstByFlagIsactive", "enumConstByFlagIsactive");
		FlagNewsStatus2.add(Restrictions.eq("code", "1"));
		FlagIsactive2.add(Restrictions.eq("code", "1"));
		peInfoNewsType2.add(Restrictions.eq("name", "优秀教师"));
		jstmpedc.add(Restrictions.isNotNull("pictrue"));
		jstmpedc.addOrder(Order.asc("title"));
		List<PeInfoNews> jslist = null;
		List<PeInfoNews> jstmpe = new ArrayList<PeInfoNews>();
		try {
			jslist = this.getGeneralService().getList(jstmpedc);
			int k = jslist.size();
			if (k < 6 && k >= 0) {
				for (int m = 0; m < k; m++) {
					//this.newsTitle(jslist.get(m), 7);
					// this.listIsNew(jslist.get(m));
					jstmpe.add(jslist.get(m));
				}
				for (int n = 0; n < 6 - jslist.size(); n++) {
					PeInfoNews jsinfo = new PeInfoNews();
					jsinfo.setPictrue("/web/bzz_index/images/b_5.jpg");
					jsinfo.setTitle("优秀教师");
					jsinfo.setId("1");
					jstmpe.add(jsinfo);
				}
			} else {
				for (int m = 0; m < k; m++) {
					if (m > 7) {
						break;
					}
					//this.newsTitle(jslist.get(m), 7);
					// this.listIsNew(jslist.get(m));
					jstmpe.add(jslist.get(m));
				}
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setJsfc(jstmpe);

		// 首页图片新闻
		List<PeInfoNews> tpnews = new ArrayList<PeInfoNews>();
		DetachedCriteria dctp = DetachedCriteria.forClass(PeInfoNews.class);
		DetachedCriteria peInfoNewsType = dctp.createCriteria("peInfoNewsType",
				"peInfoNewsType", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria FlagNewsStatus = dctp.createCriteria(
				"enumConstByFlagNewsStatus", "enumConstByFlagNewsStatus");
		DetachedCriteria FlagIsactive = dctp.createCriteria(
				"enumConstByFlagIsactive", "enumConstByFlagIsactive");
		FlagNewsStatus.add(Restrictions.eq("code", "1"));
		FlagIsactive.add(Restrictions.eq("code", "1"));
		peInfoNewsType.add(Restrictions.eq("name", "政策新闻"));
		dctp.add(Restrictions.isNotNull("pictrue"));
		dctp.addOrder(Order.desc("reportDate"));

		try {
			List<PeInfoNews> list = this.getGeneralService().getList(dctp);
			for (int i = 0; i < list.size(); i++) {
				if (i > 3) {
					break;
				}
				//this.newsTitle(list.get(i), 16);
				// this.listIsNew(list.get(i));
				tpnews.add(list.get(i));
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setTpnewsList(tpnews);*/
	//}
}
