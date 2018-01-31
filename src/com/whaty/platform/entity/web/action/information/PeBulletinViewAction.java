package com.whaty.platform.entity.web.action.information;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.bean.PeBulletin;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.cache.IndexInfoService;
import com.whaty.platform.entity.util.MyUtil;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;
import com.whaty.util.HzphCache;

public class PeBulletinViewAction extends MyBaseAction {

	private String userName;
	private String noLoginStuNum;
	private String lessfPercentNum;
	private String lessfgPercentNum;
	private String lessfgtPercentNum;
	private String glfPercentNum;
	private String completePercentNum;
	private String studentNum;
	private String noPhotoNum;
	private String batch;
	private IndexInfoService infoService;
	private String emailMessage;
	private List emailList;
	private String sysNoticeCount;// Lee 2013年12月26日添加 系统公告数
	private List ordersList;
	private int totalCount; // 总条数
	private int startIndex = 0; // 开始数
	private int pageSize = 10; // 页面显示数
	private Page page;
	private long[] applysum;
	private double amount;
	private HzphCache hzphCacheCacheManager;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * 查看公告详情
	 * 
	 * @return linjie
	 */
	public String viewDetail() {
		try {
			this.setBean((PeBulletin) this.getGeneralService().getById(this.getBean().getId()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		// System.out.println("********************" +
		// this.getBean().getNote());
		return "detail";
	}

	/**
	 * 列表初始化
	 * 
	 * @author linjie
	 */
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("系统公告"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("公告标题"), "title");
		this.getGridConfig().addColumn(this.getText("发布日期"), "publishDate", true);
		// this.getGridConfig().addColumn(this.getText("发布人"), "publisher");
		// this.getGridConfig().addColumn(this.getText("发布更新时间"), "updateDate");
		this.getGridConfig().addRenderFunction(this.getText("查看详情"), "<a href=\"peBulletinView_viewDetail.action?bean.id=${value}\" target=\"_blank\">查看详细信息</a>", "id");

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeBulletin.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/peBulletinView";
	}

	public void setBean(PeBulletin bean) {
		super.superSetBean(bean);
	}

	public PeBulletin getBean() {
		return (PeBulletin) super.superGetBean();
	}

	/**
	 * 查询对应权限下的公告
	 * 
	 * @author linjie
	 */
	@Override
	public String abstractList() {
		ActionContext context = ActionContext.getContext();
		Map map1 = context.getParameters();
		Iterator it = map1.entrySet().iterator();
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String cardNo = us.getRoleId();
		DetachedCriteria temdc = DetachedCriteria.forClass(PeEnterpriseManager.class);
		temdc.createCriteria("peEnterprise", "peEnterprise");
		temdc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
		String scope = "";
		try {
			List<PeEnterpriseManager> list = this.getGeneralService().getList(temdc);
			if (list.size() > 0) {
				scope = list.get(0).getPeEnterprise().getCode();

			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String sql_con = "";
		int k = 0;
		int n = 0;
		String tempsql = "";

		if (!cardNo.equals("0") && !cardNo.equals("1") && !cardNo.equals("2") && !"131AF5EC87836928E0530100007F9F54".equals(cardNo) && !"ff808081493288bd0149335225b90036".equals(cardNo)) {
			tempsql = "select id,title,publishDate, publisher from (" + " Select  p.id as id ,p.title as title , p.publish_date as publishDate, pr.name as publisher "
					+ "  from  pe_bulletin p   , pe_manager pe  ,pe_pri_role pr ,sso_user so where so.fk_role_id = pr.id "
					+ "  and pe.fk_sso_user_id = so.id  and pe.id = p.fk_manager_id and p.scope_string  like '%managers%' ) p";
		}
		if (cardNo.equals("2")) {
			tempsql = "select id,title,publishDate, publisher from (" + " Select p.id as id , p.title as title , p.publish_date as publishDate, pr.name as publisher  "
					+ "  from  pe_bulletin p  , pe_manager pe , pe_pri_role pr ,sso_user so  where  so.fk_role_id = pr.id "
					+ "  and pe.fk_sso_user_id = so.id  and pe.id = p.fk_manager_id and  p.scope_string  like '%subadmins%' and  " + "  (p.scope_string like '%<" + scope
					+ ">%' or p.scope_string like '%|site:all%')  " + "  and p.fk_manager_id = pe.id) p ";

		}
		
		if (cardNo.equals("1")) {
			tempsql = "select id,title,publishDate, publisher from (" + "  Select p.id as id , p.title as title , p.publish_date as publishDate, pr.name as publisher  "
					+ "  from  pe_bulletin p  , pe_manager pe  , pe_pri_role pr, sso_user so " + "  where pe.fk_sso_user_id = so.id and so.fk_role_id = pr.id  "
					+ "  and  p.scope_string  like '%secadmins%' and   (p.scope_string like '%<" + scope + ">%' " + "  or p.scope_string like '%|site:all%')  and p.fk_manager_id = pe.id  "
					+ "  union " + "  Select  p.id as id , p.title as title , p.publish_date as publishDate, en.name||pr.name as publisher  "
					+ "  from  pe_bulletin p  , pe_enterprise_manager pee , pe_pri_role pr , sso_user so , pe_enterprise en" + "  where p.scope_string  like '%secadmins%' and  "
					+ "  (p.scope_string like '%<" + scope + ">%' or p.scope_string like '%|site:all%')  " + " and p.fk_enterprisemanager_id = pee.id " + " and so.fk_role_id = pr.id  "
					+ " and pee.fk_sso_user_id = so.id  " + " and pee.fk_enterprise_id = en.id ) p  ";
		}

		if ("ff808081493288bd0149335225b90036".equals(cardNo) || "131AF5EC87836928E0530100007F9F54".equals(cardNo)) {
			tempsql = "select id,title,publishDate, publisher from (" + " Select  p.id as id ,p.title as title , p.publish_date as publishDate, pr.name as publisher "
					+ "  from  pe_bulletin p   , pe_manager pe  ,pe_pri_role pr ,sso_user so where so.fk_role_id = pr.id "
					+ "  and pe.fk_sso_user_id = so.id  and pe.id = p.fk_manager_id and p.scope_string  like '%" + cardNo + "%' ) p";
		}

		StringBuffer buffer = new StringBuffer(tempsql);
		boolean bFirst = true;
		while (it.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
			String name = entry.getKey().toString();
			if (name.startsWith("search__")) {
				n++;
				String value = ((String[]) entry.getValue())[0];
				if (value == "" || "".equals(value)) {
					k++;
				} else {
					name = name.substring(8);
					if (bFirst) {
						if (name.toLowerCase().endsWith("date")) {
							buffer.append(" where p." + name + " = to_date('" + value + "','yyyy-mm-dd')");
							bFirst = false;
						} else {
							buffer.append(" where p." + name + " like '%" + value + "%'");
							bFirst = false;
						}
					} else {
						if (name.toLowerCase().endsWith("date")) {
							buffer.append(" and p." + name + " = to_date('" + value + "','yyyy-mm-dd')");
						} else {
							buffer.append(" and p." + name + " like '%" + value + "%'");
						}
					}
				}
			}
		}
		String js = null;
		if (k - n == 0 ? true : false) {
			js = super.abstractList();
		} else {
			initGrid();
			Page page = null;
			buffer.append("Order by p.publishDate desc");
			String sql = buffer.toString();
			try {
				page = this.getGeneralService().getByPageSQL(sql, Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
				List jsonObjects = JsonUtil.ArrayToJsonObjects(page.getItems(), this.getGridConfig().getListColumnConfig());
				Map map = new HashMap();
				if (page != null) {
					map.put("totalCount", page.getTotalCount());
					map.put("models", jsonObjects);
				}
				this.setJsonString(JsonUtil.toJSONString(map));
				JsonUtil.setDateformat("yyyy-MM-dd");
				js = this.json();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
		return js;
	}

	/**
	 * 带条件查询公告
	 * 
	 * @author linjie
	 */
	public Page list() {
		Page page = null;
		UserSession session = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String cardNo = session.getRoleId();

		DetachedCriteria temdc = DetachedCriteria.forClass(PeEnterpriseManager.class);
		temdc.createCriteria("peEnterprise", "peEnterprise");
		temdc.add(Restrictions.eq("ssoUser", session.getSsoUser()));
		String scope = "";
		String parentScope = "";
		try {
			List<PeEnterpriseManager> list = this.getGeneralService().getList(temdc);
			if (list.size() > 0) {
				scope = list.get(0).getPeEnterprise().getCode();
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql = "";
		if (!cardNo.equals("0") && !cardNo.equals("1") && !cardNo.equals("2") && !"131AF5EC87836928E0530100007F9F54".equals(cardNo) && !"ff808081493288bd0149335225b90036".equals(cardNo)) {
			sql = " select * from ( Select  p.id as id ,p.title as title , p.publish_date as publishDate  ,p.flag_istop as isTop" // 如果没有排序
			// 默认是置顶;
					// lzh
					+ "  from  pe_bulletin p   , pe_manager pe  ,pe_pri_role pr ,sso_user so, enum_const ec where so.fk_role_id = pr.id and ec.id = p.flag_isvalid and ec.code='1' "
					+ "  and pe.fk_sso_user_id = so.id  and pe.id = p.fk_manager_id and p.scope_string  like '%managers%' order by isTop desc, p.publish_date desc ) where 1=1 ";
		}
		if (cardNo.equals("2")) {
			sql = "  select * from ( Select p.id as id , p.title as title , p.publish_date as publishDate   ,p.flag_istop as isTop" // 如果没有排序
					// 默认是置顶;
					// lzh
					+ "  from  pe_bulletin p  , pe_manager pe , pe_pri_role pr ,sso_user so, enum_const ec where  so.fk_role_id = pr.id and ec.id = p.flag_isvalid and ec.code='1' "
					+ "  and pe.fk_sso_user_id = so.id  and pe.id = p.fk_manager_id and  p.scope_string  like '%subadmins%' and  "
					+ "  (p.scope_string like '%<"
					+ scope
					+ ">%' or p.scope_string like '%|site:all%')  " + "  and p.fk_manager_id = pe.id order by isTop desc, p.publish_date desc ) where 1=1 ";
		}
		if (cardNo.equals("1")) {
			String string = "SELECT CODE FROM PE_ENTERPRISE WHERE ID IN (SELECT FK_PARENT_ID FROM PE_ENTERPRISE WHERE CODE = '" + scope + "')";
			try {
				List list =this.getGeneralService().getBySQL(string);
				if(list!=null && list.size()>0){
					scope = list.get(0) + "";
				}
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sql = "  select * from ( Select p.id as id , p.title as title , p.publish_date as publishDate   ,p.flag_istop as isTop " // 如果没有排序
					// 默认是置顶;
					// lzh
					+ "  from  pe_bulletin p  , pe_manager pe , pe_pri_role pr ,sso_user so, enum_const ec " 
					+ " where  so.fk_role_id = pr.id and ec.id = p.flag_isvalid and ec.code='1' "
					+ "  and pe.fk_sso_user_id = so.id  and pe.id = p.fk_manager_id and  p.scope_string  like '%secadmins%' and  "
					+ "  (p.scope_string like '%<"
					+ scope
					+ ">%' or p.scope_string like '%|site:all%')  " + "  and p.fk_manager_id = pe.id order by isTop desc, p.publish_date desc ) where 1=1 ";
		}
		if ("ff808081493288bd0149335225b90036".equals(cardNo) || "131AF5EC87836928E0530100007F9F54".equals(cardNo)) {
			sql = " select * from ( select id,title,publishDate from ("
					+ "  Select p.id as id , p.title as title , p.publish_date as publishDate  ,p.flag_istop as isTop " // 如果没有排序
					// 默认是置顶;
					// lzh
					+ "  from  pe_bulletin p  , pe_manager pe  , pe_pri_role pr, sso_user so, enum_const ec " 
					+ "  where pe.fk_sso_user_id = so.id and ec.id = p.flag_isvalid  "
					+ "  and ec.code='1' and so.fk_role_id = pr.id  " + "  and  p.scope_string  like '%"
					+ cardNo + "%' and p.fk_manager_id = pe.id)  " + " order by isTop desc, publishDate desc ) where 1=1 ";
		}
		try {
			if (StringUtils.defaultString(this.getSort()).equals("") || StringUtils.defaultString(this.getSort()).equals("id")) {
				this.setSort("isTop");
				this.setDir("desc");// 如果没有排序 默认是置顶; lzh
			}
			StringBuffer sqlBuff = new StringBuffer(sql);
			this.setSqlCondition(sqlBuff);
			page = this.getGeneralService().getByPageSQL(sqlBuff.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 获得信息
	 * 
	 * @return linjie
	 */
	public String firstInfo() {
		// 学员学习进度
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String priEntIds = "";
		String priSql = "";
		String stat_priSql = "";
		if (!us.getUserLoginType().equals("3")) {
			for (PeEnterprise pe : us.getPriEnterprises()) {
				priEntIds += "'" + pe.getId() + "',";
			}
			if (priEntIds.endsWith(",")) {
				priEntIds = priEntIds.substring(0, priEntIds.length() - 1);
			}

			priSql = " and ps.fk_enterprise_id in (" + priEntIds + ")";
			stat_priSql = " and erji_id in (" + priEntIds + ")";
		}
		List listt = null;
		try {

			DetachedCriteria dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
			if (us.getUserLoginType().equals("3")) {
				dc = DetachedCriteria.forClass(PeManager.class);
			}
			dc.createCriteria("ssoUser", "ssoUser");
			dc.add(Restrictions.eq("ssoUser.loginId", us.getLoginId()));
			listt = this.getGeneralService().getList(dc);
			if (listt.size() > 0) {
				String name;
				if (us.getUserLoginType().equals("3")) {
					PeManager em = (PeManager) listt.get(0);
					name = em.getTrueName();
				} else {
					PeEnterpriseManager em = (PeEnterpriseManager) listt.get(0);
					name = em.getName();
				}
				this.setUserName(name);
			}

			String sql = "select stu_num,nophoto_num from \n"
					+ " (select count(*) as stu_num from pe_bzz_student ps,sso_user u where ps.fk_sso_user_id=u.id and u.flag_isvalid='2' and ps.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and ps.fk_batch_id='"
					+ this.getBatch()
					+ "'"
					+ priSql
					+ "), \n"
					+ " (select count(*) as nophoto_num from pe_bzz_student ps,sso_user u where ps.fk_sso_user_id=u.id and u.flag_isvalid='2' and ps.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and ps.photo is null and ps.fk_batch_id='"
					+ this.getBatch() + "'" + priSql + ")";

			// 将未上传照片的人数注释掉 再次将此放开20110401
			/*
			 * sql = "select stu_num from \n" + " (select count(*) as stu_num
			 * from pe_bzz_student ps,sso_user u " + "where
			 * ps.fk_sso_user_id=u.id and u.flag_isvalid='2' and
			 * ps.flag_rank_state='402880f827f5b99b0127f5bdadc70006' " + "and
			 * ps.fk_batch_id='"+this.getBatch()+"'"+priSql+")";
			 */

			listt = this.getGeneralService().getBySQL(sql);
			// listt =
			// infoService.getInfoListFromCache(us.getSsoUser().getLoginId()+"noPhotoNum"+this.getBatch()+priSql,sql);
			Object[] temp = (Object[]) listt.get(0);
			this.setStudentNum(temp[0].toString());
			this.setNoPhotoNum(temp[1].toString());

			sql = "select count(ps.id) from pe_bzz_student ps inner join sso_user u on ps.fk_sso_user_id=u.id \n" + "where (u.login_num=0 or u.login_num is null)" + priSql + " and ps.fk_batch_id='"
					+ this.getBatch() + "' \n" + " and u.flag_isvalid='2' and ps.flag_rank_state='402880f827f5b99b0127f5bdadc70006'";
			listt = this.getGeneralService().getBySQL(sql);
			// listt =
			// infoService.getInfoListFromCache(us.getSsoUser().getLoginId()+"stuSumNum"+this.getBatch()+priSql,sql);
			this.setNoLoginStuNum(listt.get(0).toString());

			// 针对2010春季学员，企业管理概览和企业管理概览（新），班组安全管理和班组安全管理（新）中选取进度高的一门，保证基础课共16门
			String sql_training = " training_course_student tcs,";
			if (this.getBatch().equals("ff8080812824ae6f012824b0a89e0008")) {
				sql_training = "("
						+ "select s.id,s.course_id,s.percent,s.learn_status,s.student_id from training_course_student s ,pe_bzz_student bs,pr_bzz_tch_opencourse pbto where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"
						+ this.getBatch()
						+ "'\n"
						+ "and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and (s.course_id <> 'ff8080812910e7e601291150ddc70419' and s.course_id <>'ff8080812bf5c39a012bf6a1bab80820' and s.course_id <>'ff8080812910e7e601291150ddc70413' and s.course_id <>'ff8080812bf5c39a012bf6a1baba0821')"
						+ " and pbto.id=s.course_id and pbto.flag_course_type='402880f32200c249012200c780c40001' and pbto.fk_batch_id=bs.fk_batch_id "
						+ "union\n"
						+ "select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.id,b.id) as id,"
						+ "decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n"
						+ "decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,"
						+ "decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n"
						+ "select  s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"
						+ this.getBatch()
						+ "' and s.course_id='ff8080812910e7e601291150ddc70419' )a,\n"
						+ "(select  s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s ,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"
						+ this.getBatch()
						+ "' and s.course_id='ff8080812bf5c39a012bf6a1bab80820')b\n"
						+ "where a.student_id=b.student_id\n"
						+ "union\n"
						+ "select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.id,b.id) as id,"
						+ "decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n"
						+ "decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,"
						+ "decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n"
						+ "select  s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s ,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"
						+ this.getBatch()
						+ "' and s.course_id='ff8080812910e7e601291150ddc70413' )a,\n"
						+ "(select  s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"
						+ this.getBatch() + "' and s.course_id='ff8080812bf5c39a012bf6a1baba0821')b\n" + "where a.student_id=b.student_id" + ") tcs, ";
			}

			/*
			 * sql= "select sum(decode(sign(percent - 0.25), -1, 1,0)) as
			 * less25,\n" + " sum(decode(sign(percent - 0.25), 0, 1, 0)) as
			 * eq25,\n" + " sum(decode(sign(percent - 0.5), -1, 1, 0, 1, 0)) as
			 * less50,\n" + " sum(decode(sign(percent - 0.5), 0, 1, 0)) as
			 * eq50,\n" + " sum(decode(sign(percent - 0.8), -1, 1, 0, 1, 0)) as
			 * less80,\n" + " sum(decode(sign(percent - 0.8), 0, 1, 0)) as
			 * eq80,\n" + " sum(decode(sign(percent - 1), -1, 1, 0)) as
			 * less100,\n" + " sum(decode(percent, 1, 1, 0)) as e100\n" + " from
			 * (select stu_id, nvl(sum(mtime), 0) / nvl(sum(ttime), 0) as
			 * percent\n" + " from (select ps.id as stu_id,\n" + " btc.time *
			 * (tcs.percent / 100) as mtime,\n" + " btc.time as ttime\n" + "
			 * from " + sql_training+ " pr_bzz_tch_opencourse bto,\n" + "
			 * pe_bzz_tch_course btc,\n" + " pe_bzz_student ps,\n" + " sso_user
			 * u\n" + " where tcs.course_id = bto.id\n" + " and bto.fk_course_id =
			 * btc.id\n" + " and bto.flag_course_type =\n" + "
			 * '402880f32200c249012200c780c40001'\n" + " and tcs.student_id =
			 * ps.fk_sso_user_id\n" + " and ps.fk_sso_user_id = u.id\n" + " and
			 * u.flag_isvalid = '2'\n" + " and u.login_num > 0\n" + " and
			 * ps.fk_batch_id = '"+this.getBatch()+"'\n" + priSql+ " and
			 * bto.fk_batch_id = ps.fk_batch_id\n" + " and ps.flag_rank_state
			 * =\n" + " '402880f827f5b99b0127f5bdadc70006')\n" + " group by
			 * stu_id)";
			 */
			sql = "select a.less25, b.less50, c.less80, d.le100, e.eq100\n" + "  from (select count(id) as less25\n" + "          from stat_study_summary\n" + "         where batch_id = '"
					+ this.getBatch() + "'\n" + stat_priSql + "           and percent < 25) a,\n" + "       (select count(id) as less50\n" + "          from stat_study_summary\n"
					+ "         where batch_id = '" + this.getBatch() + "'\n" + stat_priSql + "           and percent <50\n" + "           and percent >= 25) b,\n"
					+ "       (select count(id) as less80\n" + "          from stat_study_summary\n" + "         where batch_id = '" + this.getBatch() + "'\n" + stat_priSql
					+ "           and percent < 80\n" + "           and percent >= 50) c,\n" + "       (select count(id) as le100\n" + "          from stat_study_summary\n"
					+ "         where batch_id = '" + this.getBatch() + "'\n" + stat_priSql + "           and percent < 100\n" + "           and percent >= 80) d,\n"
					+ "       (select count(id) as eq100\n" + "          from stat_study_summary\n" + "         where batch_id = '" + this.getBatch() + "'\n" + stat_priSql
					+ "           and percent = 100) e";

			// listt =
			// infoService.getInfoListFromCache(us.getSsoUser().getLoginId()+"all"+this.getBatch()+priSql,sql);
			dbpool db = new dbpool();
			MyResultSet rs = db.executeQuery(sql);

			try {
				while (rs != null && rs.next()) {
					this.setLessfPercentNum(rs.getString("less25")); // 小于25%
					this.setLessfgPercentNum(rs.getString("less50")); // 25<=,<50
					this.setLessfgtPercentNum(rs.getString("less80")); // 50<=,<80
					this.setGlfPercentNum(rs.getString("le100")); // 80<=,<100
					this.setCompletePercentNum(rs.getString("eq100")); // =100
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.close(rs);
			}

			/*
			 * String num_le25=""; String num_eq25=""; String num_le50="";
			 * String num_eq50=""; String num_le80=""; String num_eq80="";
			 * String num_le100=""; String num_eq100="";
			 * 
			 * try{ while (rs!=null&& rs.next()) {
			 * num_le25=rs.getString("less25"); num_eq25=rs.getString("eq25");
			 * num_le50=rs.getString("less50"); num_eq50=rs.getString("eq50");
			 * num_le80=rs.getString("less80"); num_eq80=rs.getString("eq80");
			 * num_le100=rs.getString("less100");
			 * num_eq100=rs.getString("e100"); } }catch(Exception e)
			 * {e.printStackTrace();} finally{ db.close(rs); }
			 * 
			 * int i_le25=Integer.parseInt(num_le25); int
			 * i_eq25=Integer.parseInt(num_eq25); int
			 * i_le50=Integer.parseInt(num_le50); int
			 * i_eq50=Integer.parseInt(num_eq50); int
			 * i_le80=Integer.parseInt(num_le80); int
			 * i_eq80=Integer.parseInt(num_eq80); int
			 * i_le100=Integer.parseInt(num_le100); int
			 * i_eq100=Integer.parseInt(num_eq100);
			 * 
			 * 
			 * this.setLessfPercentNum(num_le25); //小于25%
			 * this.setLessfgPercentNum((i_le50-i_le25+i_eq25)+""); //25<=,<50
			 * this.setLessfgtPercentNum((i_le80-i_le50+i_eq50)+""); //50<=,<80
			 * this.setGlfPercentNum((i_le100-i_le80+i_eq80)+""); //80<=,<100
			 * this.setCompletePercentNum(num_eq100); //=100
			 * 
			 */

			/*
			 * sql="select count(*) from (select stu_id,nvl(sum(mtime),0) /
			 * nvl(sum(ttime),0) as percent from ( \n" + " select ps.id as
			 * stu_id,btc.time*(tcs.percent/100) as mtime, btc.time as ttime
			 * from "+sql_training+"pr_bzz_tch_opencourse bto,pe_bzz_tch_course
			 * btc,pe_bzz_student ps,sso_user u \n" + " where
			 * tcs.course_id=bto.id and bto.fk_course_id=btc.id and
			 * bto.flag_course_type='402880f32200c249012200c780c40001' and
			 * tcs.student_id=ps.fk_sso_user_id and ps.fk_sso_user_id=u.id" +
			 * priSql + " and ps.fk_batch_id='"+this.getBatch()+"' \n" + " and
			 * u.flag_isvalid='2' and u.login_num>0 and
			 * ps.flag_rank_state='402880f827f5b99b0127f5bdadc70006' " + " )
			 * group by stu_id) \n" + "where percent < 0.25"; //listt =
			 * this.getGeneralService().getBySQL(sql); listt =
			 * infoService.getInfoListFromCache(us.getSsoUser().getLoginId()+"1/4"+this.getBatch()+priSql,sql);
			 * this.setLessfPercentNum(listt.get(0).toString());
			 * 
			 * sql="select count(*) from (select stu_id,nvl(sum(mtime),0) /
			 * nvl(sum(ttime),0) as percent from ( \n" + " select ps.id as
			 * stu_id,btc.time*(tcs.percent/100) as mtime, btc.time as ttime
			 * from "+sql_training+"pr_bzz_tch_opencourse bto,pe_bzz_tch_course
			 * btc,pe_bzz_student ps,sso_user u \n" + " where
			 * tcs.course_id=bto.id and bto.fk_course_id=btc.id and
			 * bto.flag_course_type='402880f32200c249012200c780c40001' and
			 * tcs.student_id=ps.fk_sso_user_id and ps.fk_sso_user_id=u.id" +
			 * priSql + " and ps.fk_batch_id='"+this.getBatch()+"' \n" + " and
			 * u.flag_isvalid='2' and u.login_num>0 and
			 * ps.flag_rank_state='402880f827f5b99b0127f5bdadc70006' " + " )
			 * group by stu_id) \n" + "where percent >= 0.25 and percent < 0.5";
			 * //listt = this.getGeneralService().getBySQL(sql); listt =
			 * infoService.getInfoListFromCache(us.getSsoUser().getLoginId()+"2/4"+this.getBatch()+priSql,sql);
			 * this.setLessfgPercentNum(listt.get(0).toString());
			 * 
			 * sql="select count(*) from (select stu_id,nvl(sum(mtime),0) /
			 * nvl(sum(ttime),0) as percent from ( \n" + " select ps.id as
			 * stu_id,btc.time*(tcs.percent/100) as mtime, btc.time as ttime
			 * from "+sql_training+"pr_bzz_tch_opencourse bto,pe_bzz_tch_course
			 * btc,pe_bzz_student ps,sso_user u \n" + " where
			 * tcs.course_id=bto.id and bto.fk_course_id=btc.id and
			 * bto.flag_course_type='402880f32200c249012200c780c40001' and
			 * tcs.student_id=ps.fk_sso_user_id and ps.fk_sso_user_id=u.id" +
			 * priSql + " and ps.fk_batch_id='"+this.getBatch()+"' \n" + " and
			 * u.flag_isvalid='2' and u.login_num>0 and
			 * ps.flag_rank_state='402880f827f5b99b0127f5bdadc70006' " + " )
			 * group by stu_id) \n" + "where percent >= 0.5 and percent < 0.8";
			 * //listt = this.getGeneralService().getBySQL(sql); listt =
			 * infoService.getInfoListFromCache(us.getSsoUser().getLoginId()+"3/4"+this.getBatch()+priSql,sql);
			 * this.setLessfgtPercentNum(listt.get(0).toString());
			 * 
			 * sql="select count(*) from (select stu_id,nvl(sum(mtime),0) /
			 * nvl(sum(ttime),0) as percent from ( \n" + " select ps.id as
			 * stu_id,btc.time*(tcs.percent/100) as mtime, btc.time as ttime
			 * from "+sql_training+"pr_bzz_tch_opencourse bto,pe_bzz_tch_course
			 * btc,pe_bzz_student ps,sso_user u \n" + " where
			 * tcs.course_id=bto.id and bto.fk_course_id=btc.id and
			 * bto.flag_course_type='402880f32200c249012200c780c40001' and
			 * tcs.student_id=ps.fk_sso_user_id and ps.fk_sso_user_id=u.id" +
			 * priSql + " and ps.fk_batch_id='"+this.getBatch()+"' \n" + " and
			 * u.flag_isvalid='2' and
			 * ps.flag_rank_state='402880f827f5b99b0127f5bdadc70006'" + " )
			 * group by stu_id) \n" + "where percent >= 0.8 and percent < 1";
			 * //listt = this.getGeneralService().getBySQL(sql); listt =
			 * infoService.getInfoListFromCache(us.getSsoUser().getLoginId()+"4/4"+this.getBatch()+priSql,sql);
			 * this.setGlfPercentNum(listt.get(0).toString());
			 * 
			 * sql="select count(*) from (select stu_id,nvl(sum(mtime),0) /
			 * nvl(sum(ttime),0) as percent from ( \n" + " select ps.id as
			 * stu_id,btc.time*(tcs.percent/100) as mtime, btc.time as ttime
			 * from "+sql_training+"pr_bzz_tch_opencourse bto,pe_bzz_tch_course
			 * btc,pe_bzz_student ps,sso_user u \n" + " where
			 * tcs.course_id=bto.id and bto.fk_course_id=btc.id and
			 * bto.flag_course_type='402880f32200c249012200c780c40001' and
			 * tcs.student_id=ps.fk_sso_user_id and ps.fk_sso_user_id=u.id" +
			 * priSql + " and ps.fk_batch_id='"+this.getBatch()+"' \n" + " and
			 * u.flag_isvalid='2' and
			 * ps.flag_rank_state='402880f827f5b99b0127f5bdadc70006'" + " )
			 * group by stu_id) \n" + "where percent = 1"; listt =
			 * infoService.getInfoListFromCache(us.getSsoUser().getLoginId()+"completed"+this.getBatch()+priSql,sql);
			 * //listt = this.getGeneralService().getBySQL(sql);
			 * this.setCompletePercentNum(listt.get(0).toString());
			 */

		} catch (EntityException e) {
			e.printStackTrace();
		}

		// session.put("mtime", mtime);
		// session.put("ttime", ttime);
		try {
			// 判断管理员的邮件信息
			DetachedCriteria dc = null;
			List list = new ArrayList();
			PeManager peManager = null;
			PeEnterpriseManager peEnterpriseManager = null;
			if ("3".equals(us.getRoleId())) { // 总管理员
				dc = DetachedCriteria.forClass(PeManager.class);
				dc.createCriteria("ssoUser", "ssoUser");
				dc.add(Restrictions.eq("ssoUser.id", us.getSsoUser().getId()));
				peManager = (PeManager) this.getGeneralService().getList(dc).get(0);
				if (null != peManager && !"".equals(peManager)) {
					String email = peManager.getEmail();
					if (null == email || "".equals(email) || "null".equals(email)) {
						this.setEmailMessage("2");
						return "firstInfo";
					}
					boolean isEmail = MyUtil.isEmail(email);
					if (isEmail) { // 邮箱格式正确
						// 校验该邮箱是否重复
						dc = DetachedCriteria.forClass(PeManager.class);
						dc.add(Restrictions.eq("email", email));
						list = this.getGeneralService().getList(dc);
						if (list != null && list.size() > 1) {
							this.setEmailMessage("1");
							return "firstInfo";
						}
						// 校验学员中是否存在该邮箱
						dc = DetachedCriteria.forClass(PeBzzStudent.class);
						dc.add(Restrictions.eq("email", email));
						list = this.getGeneralService().getList(dc);
						if (list != null && list.size() > 0) { // 该学员的邮箱重复
							this.setEmailMessage("1");
							return "firstInfo";
						}
						// 校验该邮箱是否存在于企业管理员之中
						dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
						dc.add(Restrictions.eq("email", email));
						list = this.getGeneralService().getList(dc);
						if (list != null && list.size() > 0) {
							this.setEmailMessage("1");
							return "firstInfo";
						}
					} else { // 邮箱不正确
						this.setEmailMessage("2");
						return "firstInfo";
					}
				}
			} else { // 企业管理员
				dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
				dc.createCriteria("ssoUser", "ssoUser");
				dc.add(Restrictions.eq("ssoUser.id", us.getSsoUser().getId()));
				peEnterpriseManager = (PeEnterpriseManager) this.getGeneralService().getList(dc).get(0);
				if (null != peEnterpriseManager && !"".equals(peEnterpriseManager)) {
					String email = peEnterpriseManager.getEmail();
					if (null == email || "".equals(email) || "null".equals(email)) {
						this.setEmailMessage("2");
						return "firstInfo";
					}
					boolean isEmail = MyUtil.isEmail(email);
					if (isEmail) { // 邮箱格式正确
						// 校验该邮箱是否重复
						dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
						dc.add(Restrictions.eq("email", email));
						list = this.getGeneralService().getList(dc);
						if (list != null && list.size() > 1) {
							this.setEmailMessage("1");
							return "firstInfo";
						}
						// 校验该邮箱是否存在于总管理员之中
						dc = DetachedCriteria.forClass(PeManager.class);
						dc.add(Restrictions.eq("email", email));
						list = this.getGeneralService().getList(dc);
						if (list != null && list.size() > 0) {
							this.setEmailMessage("1");
							return "firstInfo";
						}
						// 校验学员中是否存在该邮箱
						dc = DetachedCriteria.forClass(PeBzzStudent.class);
						dc.add(Restrictions.eq("email", email));
						list = this.getGeneralService().getList(dc);
						if (list != null && list.size() > 0) { // 该学员的邮箱重复
							this.setEmailMessage("1");
							return "firstInfo";
						}

					} else { // 邮箱不正确
						this.setEmailMessage("2");
					}
				}
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "firstInfo";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNoLoginStuNum() {
		return noLoginStuNum;
	}

	public void setNoLoginStuNum(String noLoginStuNum) {
		this.noLoginStuNum = noLoginStuNum;
	}

	public String getLessfPercentNum() {
		return lessfPercentNum;
	}

	public void setLessfPercentNum(String lessfPercentNum) {
		this.lessfPercentNum = lessfPercentNum;
	}

	public String getGlfPercentNum() {
		return glfPercentNum;
	}

	public void setGlfPercentNum(String glfPercentNum) {
		this.glfPercentNum = glfPercentNum;
	}

	public String getCompletePercentNum() {
		return completePercentNum;
	}

	public void setCompletePercentNum(String completePercentNum) {
		this.completePercentNum = completePercentNum;
	}

	public String getBatch() {
		if (batch == null) {
			try {
				DetachedCriteria dc = DetachedCriteria.forClass(PeBzzBatch.class);
				dc.add(Restrictions.ne("id", "52cce2fd2471ddc30124764980580131"));
				dc.add(Restrictions.eq("alertSelected", "1"));
				PeBzzBatch bzzBatch = (PeBzzBatch) this.getGeneralService().getList(dc).get(0);
				batch = bzzBatch.getId();
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
		return batch;
	}

	public String welcome() {
		this.setDoLog(false);// 不记录日志
		ActionContext ac = ActionContext.getContext();
		UserSession us = (UserSession) ac.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		StringBuffer sql = new StringBuffer();
		sql.append("select count(s.addressee_id) as amount\n" + "  from site_email s\n" + " where s.addressee_id = '" + us.getLoginId() + "'\n" + "   and s.addressee_del = 0\n" + "union all\n"
				+ "select count(s.addressee_id) as amount\n" + "  from site_email s\n" + " where s.sender_id = '" + us.getLoginId() + "'\n" + "   and s.sender_del = 0\n" + "union all\n"
				+ "select count(s.addressee_id) as amount\n" + "  from site_email s\n" + " where s.addressee_id = '" + us.getLoginId() + "'\n" + "   and s.status = '0'\n"
				+ "   and s.addressee_del = 0");
		try {
			// Lee 2013年12月26日 系统公告数
			String cardNo = us.getRoleId();
			String sns = "";
			String scope = "";
			try {
				DetachedCriteria temdc = DetachedCriteria.forClass(PeEnterpriseManager.class);
				temdc.createCriteria("peEnterprise", "peEnterprise");
				temdc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
				List<PeEnterpriseManager> list = this.getGeneralService().getList(temdc);
				if (list.size() > 0) {
					scope = list.get(0).getPeEnterprise().getCode();
				}
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!cardNo.equals("0") && !cardNo.equals("1") && !cardNo.equals("2") && !"ff808081493288bd0149335225b90036".equals(cardNo) && !"131AF5EC87836928E0530100007F9F54".equals(cardNo)) {
				sns = "select count(*) from ( select id,title,publishDate, publisher from (" 
						+ " Select  p.id as id ,p.title as title , p.publish_date as publishDate, pr.name as publisher "
						+ "  from  pe_bulletin p   , pe_manager pe  ,pe_pri_role pr ,sso_user so, enum_const ec where so.fk_role_id = pr.id "
						+ "  and ec.id = p.flag_isvalid and ec.code='1' and pe.fk_sso_user_id = so.id  and pe.id = p.fk_manager_id and p.scope_string  like '%managers%' ) p )";
			}
			if (cardNo.equals("2")) {
				sns = "select count(*) from ( select id,title,publishDate, publisher from (" 
						+ " Select p.id as id , p.title as title , p.publish_date as publishDate, pr.name as publisher  "
						+ "  from  pe_bulletin p  , pe_manager pe , pe_pri_role pr ,sso_user so, enum_const ec where  so.fk_role_id = pr.id "
						+ "  and ec.id = p.flag_isvalid and ec.code='1' and pe.fk_sso_user_id = so.id  and pe.id = p.fk_manager_id and  p.scope_string  like '%subadmins%' and  " + "  (p.scope_string like '%<" + scope
						+ ">%' or p.scope_string like '%|site:all%')  " + "  and p.fk_manager_id = pe.id) p )";

			}
			if(cardNo.equals("1")){
				String string = "SELECT CODE FROM PE_ENTERPRISE WHERE ID IN (SELECT FK_PARENT_ID FROM PE_ENTERPRISE WHERE CODE = '" + scope + "')";
				try {
					List list =this.getGeneralService().getBySQL(string);
					if(list!=null && list.size()>0){
						scope = list.get(0) + "";
					}
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sns = "  select count(*) from ( Select p.id as id , p.title as title , p.publish_date as publishDate   ,p.flag_istop as isTop" // 如果没有排序
						// 默认是置顶;
						// lzh
						+ "  from  pe_bulletin p  , pe_manager pe , pe_pri_role pr ,sso_user so, enum_const ec where  so.fk_role_id = pr.id "
						+ "  and ec.id = p.flag_isvalid and ec.code='1' and pe.fk_sso_user_id = so.id  and pe.id = p.fk_manager_id and  p.scope_string  like '%secadmins%' and  "
						+ "  (p.scope_string like '%<"
						+ scope
						+ ">%' or p.scope_string like '%|site:all%')  " + "  and p.fk_manager_id = pe.id order by isTop desc, p.publish_date desc ) where 1=1 ";
			}
			if ("ff808081493288bd0149335225b90036".equals(cardNo) || "131AF5EC87836928E0530100007F9F54".equals(cardNo)) {
				sns = " select count(*) from ( select id,title,publishDate from ("
						+ "  Select p.id as id , p.title as title , p.publish_date as publishDate  ,p.flag_istop as isTop " // 如果没有排序
						// 默认是置顶;
						// lzh
						+ "  from  pe_bulletin p  , pe_manager pe  , pe_pri_role pr, sso_user so, enum_const ec " 
						+ "  where pe.fk_sso_user_id = so.id and so.fk_role_id = pr.id and ec.id = p.flag_isvalid and ec.code='1' " 
						+ "  and  p.scope_string  like '%"
						+ cardNo + "%' and p.fk_manager_id = pe.id)  " + " order by isTop desc, publishDate desc ) where 1=1 ";
			}
			//二级集体首页公告条数  lzh

//			if ((!cardNo.equals("3")) && (!cardNo.equals("2"))) {
//				sns = "select count(*) from (select id,title,publishDate, publisher from (" + "  Select p.id as id , p.title as title , p.publish_date as publishDate, pr.name as publisher  "
//						+ "  from  pe_bulletin p  , pe_manager pe  , pe_pri_role pr, sso_user so " + "  where pe.fk_sso_user_id = so.id and so.fk_role_id = pr.id  "
//						+ "  and  p.scope_string  like '%subadmins%' and   (p.scope_string like '%<" + scope + ">%' " + "  or p.scope_string like '%|site:all%')  and p.fk_manager_id = pe.id  "
//						+ "  union " + "  Select  p.id as id , p.title as title , p.publish_date as publishDate, en.name||pr.name as publisher  "
//						+ "  from  pe_bulletin p  , pe_enterprise_manager pee , pe_pri_role pr , sso_user so , pe_enterprise en" + "  where p.scope_string  like '%subadmins%' and  "
//						+ "  (p.scope_string like '%<" + scope + ">%' or p.scope_string like '%|site:all%')  " + " and p.fk_enterprisemanager_id = pee.id " + " and so.fk_role_id = pr.id  "
//						+ " and pee.fk_sso_user_id = so.id  " + " and pee.fk_enterprise_id = en.id ) p  )";
//			}
			this.sysNoticeCount = this.getGeneralService().getBySQL(sns).get(0).toString();
			// Lee end
			emailList = this.getGeneralService().getBySQL(sql.toString());
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if ("3".equals(us.getUserLoginType())) {
			sql = new StringBuffer();
			sql.append(" select count(t.id) as aum                 ");
			sql.append("   from pe_bzz_order_info t		       ");
			sql.append(" union all				       ");
			sql.append(" select count(t.id)			       ");
			sql.append("   from pe_bzz_order_info t, enum_const e  ");
			sql.append("  where t.flag_payment_state = e.id	       ");
			sql.append("    and e.namespace = 'FlagPaymentState'   ");
			sql.append("    and e.code in('1','2')			       ");
			try {
				ordersList = this.getGeneralService().getBySQL(sql.toString());
			} catch (EntityException e) {
				e.printStackTrace();
			}
		} else if ("4".indexOf(us.getUserLoginType()) != -1) {
			sql = new StringBuffer();
			sql.append("  select a.years,a.amount,a.amounted,a.money,b.stunum,b.coursenum from 																									  ");
			sql.append("  ( select to_char(p.create_date, 'yyyy') as years,                                                                        ");
			sql.append("         count(p.id) as amount,                                                                                            ");
			sql.append("   sum(case when p.flag_payment_state = '40288a7b394207de01394221a6ff000e' then 1 else 0 end ) as amounted,                ");
			sql.append("   sum(case when p.flag_payment_state = '40288a7b394207de01394221a6ff000e' then to_number(p.AMOUNT) else 0 end ) as money  ");
			sql.append("   from                                                                                                                    ");
			sql.append("    pe_bzz_order_info p                                                                                                    ");
			sql.append("   where p.create_user='" + us.getSsoUser().getId() + "'        	  ");
			sql.append("  group by to_char(p.CREATE_DATE, 'yyyy')) a,                                                                              ");
			sql.append("   (                                                                                                                       ");
			sql.append("      select to_char(p.create_date, 'yyyy') as years,count(distinct ele.fk_stu_id) as stunum,                              ");
			sql.append("      count(distinct opencourse.fk_course_id) as coursenum from                                                            ");
			sql.append("      pr_bzz_tch_stu_elective ele,                                                                                         ");
			sql.append("      pr_bzz_tch_opencourse opencourse,                                                                                    ");
			sql.append("      pe_bzz_order_info p                                                                                                  ");
			sql.append("      where ele.fk_tch_opencourse_id = opencourse.id and ele.fk_order_id = p.id 											  ");
			sql.append("  	 and p.create_user='" + us.getSsoUser().getId() + "' group by to_char(p.create_date, 'yyyy')    						  ");
			sql.append("   ) b where a.years = b.years(+)                                                                                          ");
			try {
				page = this.getGeneralService().getByPageSQL(sql.toString(), this.pageSize, this.startIndex);
				this.applysum(page.getItems());
			} catch (EntityException e) {
				e.printStackTrace();
			}

		} else if ("2".equals(us.getUserLoginType())) {
			sql = new StringBuffer();
			sql.append("  select a.year,nvl(a.SUMENTRY,0),nvl(a.PAYEDENTRY,0),nvl(a.PAYEDAMOUNT,0),nvl(a.PAYEDSTUDENT,0),nvl(a.PAYEDCOURSE,0) from teamEntry a where a.MANAGER_ID= '"
					+ us.getSsoUser().getId() + "' order by year");
			try {
				page = this.getGeneralService().getByPageSQL(sql.toString(), this.pageSize, this.startIndex);
				this.applysum(page.getItems());
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
		return "welcome";
	}

	public String getOnlineNum() {
		this.setDoLog(false);// 不记录日志
		Cache cache = hzphCacheCacheManager.getCache();
		int on = 0;// 在线总数
		int webSize = 0; // web服务器数量
		String ws = this.getIds();// 前台传参
		if (ws != null && ws.length() > 0) {
			try {
				webSize = Integer.parseInt(ws);
			} catch (Exception e) {
				//
			}
		} else {
			webSize = 8;
		}
		for (int i = 1; i <= webSize; i++) {
			Element et = cache.get("tomcat_" + i + "_Onlinesum");
			on += fixOnlineNum(et);
		}
		Element e0 = cache.get("Onlinesum");

		if (on < 1) {
			on = fixOnlineNum(e0);
		}
		// String onlineNum =
		// hzphCacheCacheManager.getCache().get("Onlinesum").getValue().toString();
		this.setJsonString(on + "");
		return json();
	}

	private int fixOnlineNum(Element ele) {
		if (ele != null) {
			return Integer.valueOf(ele.getValue().toString());
		}
		return 0;
	}

	public void setOnlineNum(String num) {
		Cache cache = hzphCacheCacheManager.getCache();
		cache.put(new Element("Onlinesum", Integer.valueOf(num), false, 5 * 1000, 5 * 1000));
	}

	public void applysum(List list) {
		applysum = new long[4];
		for (int i = 0; i < list.size(); i++) {
			applysum[0] += Long.parseLong(((Object[]) list.get(i))[1].toString());
			applysum[1] += Long.parseLong(((Object[]) list.get(i))[2].toString());
			if (((Object[]) list.get(i))[4] != null)
				applysum[2] += Long.parseLong(((Object[]) list.get(i))[5].toString());
			if (((Object[]) list.get(i))[5] != null)
				applysum[3] += Long.parseLong(((Object[]) list.get(i))[4].toString());
			amount += Double.parseDouble(((Object[]) list.get(i))[3].toString());
		}
		amount = new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}

	public String getNoPhotoNum() {
		return noPhotoNum;
	}

	public void setNoPhotoNum(String noPhotoNum) {
		this.noPhotoNum = noPhotoNum;
	}

	public String getLessfgPercentNum() {
		return lessfgPercentNum;
	}

	public void setLessfgPercentNum(String lessfgPercentNum) {
		this.lessfgPercentNum = lessfgPercentNum;
	}

	public String getLessfgtPercentNum() {
		return lessfgtPercentNum;
	}

	public void setLessfgtPercentNum(String lessfgtPercentNum) {
		this.lessfgtPercentNum = lessfgtPercentNum;
	}

	public void setInfoService(IndexInfoService infoService) {
		this.infoService = infoService;
	}

	public String clearCache() {
		infoService.clearUser();
		return "clearOk";
	}

	public String getEmailMessage() {
		return emailMessage;
	}

	public void setEmailMessage(String emailMessage) {
		this.emailMessage = emailMessage;
	}

	public List getEmailList() {
		return emailList;
	}

	public void setEmailList(List emailList) {
		this.emailList = emailList;
	}

	public List getOrdersList() {
		return ordersList;
	}

	public void setOrdersList(List ordersList) {
		this.ordersList = ordersList;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public long[] getApplysum() {
		return applysum;
	}

	public void setApplysum(long[] applysum) {
		this.applysum = applysum;
	}

	public HzphCache getHzphCacheCacheManager() {
		return hzphCacheCacheManager;
	}

	public void setHzphCacheCacheManager(HzphCache hzphCacheCacheManager) {
		this.hzphCacheCacheManager = hzphCacheCacheManager;
	}

	public String getSysNoticeCount() {
		return sysNoticeCount;
	}

	public void setSysNoticeCount(String sysNoticeCount) {
		this.sysNoticeCount = sysNoticeCount;
	}

}
