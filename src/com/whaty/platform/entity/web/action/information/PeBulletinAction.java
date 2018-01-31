package com.whaty.platform.entity.web.action.information;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBulletin;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PeGrade;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;

public class PeBulletinAction extends MyBaseAction {

	private boolean cando;
	private String stats;
	private String[] colsOrder = {"id","title","enumConstByFlagIstop.name","enumConstByFlagIsvalid.name","publishDate","updateDate","publisher",
			"scopeString","isStudent","isManager","isEnterprise","isIndex"};
//	private String[] colsDetail = {"id","trueName","code","name","time"};
	private Set<String> setOrder = new HashSet<String>();
	private String topFlag = "false";
//	private String flagIsTop = "1";
//	
//
//	public String getFlagIsTop() {
//		return flagIsTop;
//	}
//
//	public void setFlagIsTop(String flagIsTop) {
//		this.flagIsTop = flagIsTop;
//	}

	public Set<String> getSetOrder() {
		return setOrder;
	}

	public void setSetOrder(Set<String> setOrder) {
		this.setOrder = setOrder;
	}

	/**
	 * 初始化列表
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet=us.getUserPriority().keySet();
		
		boolean canList = true;
		if("3".equals(us.getRoleId()) 
				&& capabilitySet.contains(this.servletPath+"_bulletinBtn.action")){//按钮权限验证
			this.getGridConfig().setCapability(false, true, false);	
			canList = true;
			this.getGridConfig().addMenuFunction(this.getText("设为置顶"), "truetop");
			this.getGridConfig().addMenuFunction(this.getText("取消置顶"),"falsetop");
			this.getGridConfig().addMenuFunction(this.getText("设为有效"), "truevalid");
			this.getGridConfig().addMenuFunction(this.getText("设为无效"), "falsevalid");
			this.getGridConfig().addRenderFunction(this.getText("操作"),
					"<a href=peBulletin_edit.action?bean.id=${value}>修改</a>", "id");	
		}else{
			this.getGridConfig().setCapability(false, false, false);
			canList = false;
		}
		
		this.getGridConfig().setTitle(this.getText("通知公告"));
		
		//this.getGridConfig().addMenuFunction(this.getText("设为最新"), "trueisnew");
		//this.getGridConfig().addMenuFunction(this.getText("取消最新"), "falseisnew");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("通知标题"), "title", true,
				true, true, "TextField", false, 200);
		this.getGridConfig().addColumn(this.getText("是否置顶"),
				"enumConstByFlagIstop.name", true, true, canList, "");
		this.getGridConfig().addColumn(this.getText("是否有效"),
				"enumConstByFlagIsvalid.name", true, true, canList, "");
		//this.getGridConfig().addColumn(this.getText("是否最新"),
			//	"enumConstByFlagIsNew.name", false, true, true, "");
		this.getGridConfig().addColumn(this.getText("发布日期起始"), "publishStartDate", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("发布日期"), "publishDate", false);
		this.getGridConfig().addColumn(this.getText("发布日期结束"), "publishEndDate", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("修改日期"), "updateDate",
				false, true, canList, "");
		this.getGridConfig().addColumn(this.getText("发布人"), "publisher", false,false, canList, "TextField", false, 200);
		this.getGridConfig().addColumn(this.getText("发布范围"), "scopeString",
				false, false, false, "");
		this.getGridConfig().addColumn(this.getText("收信对象"), "scope_per", false, false, true, "");
//		this.getGridConfig().addColumn(this.getText("发布到学员"), "isStudent",
//				false, true, canList, "");
//		this.getGridConfig().addColumn(this.getText("发布到管理员"), "isManager",
//				false, true, canList, "");
//		this.getGridConfig().addColumn(this.getText("发布到集体用户管理员"), "isEnterprise",
//				false, true, canList, "");
//		this.getGridConfig().addColumn(this.getText("发布到首页"), "isIndex",
//				false, true, canList, "");
	/*	this
				.getGridConfig()
				.addRenderScript(
						"发布到学员",
						"{if((record.data['scopeString']).indexOf('student')>=0){return '是';}else{return '否'}}",
						"");
		this
				.getGridConfig()
				.addRenderScript(
						"发布到管理员",
						"{if((record.data['scopeString']).indexOf('managers')>=0){return '是';}else{return '否'}}",
						"");
		this
				.getGridConfig()
				.addRenderScript(
						"发布到集体用户管理员",
						"{if((record.data['scopeString']).indexOf('subadmins')>=0){return '是';}else{return '否'}}",
						"");
		this
				.getGridConfig()
				.addRenderScript(
						"发布到首页",
						"{if((record.data['scopeString']).indexOf('index')>=0){return '是';}else{return '否'}}",
						"");**/
		this
				.getGridConfig()
				.addRenderFunction(
						this.getText("详细内容"),
						"<a href=\"peBulletinView_viewDetail.action?bean.id=${value}\" target=\"_blank\">查看详细信息</a>",
						"id");
//		ColumnConfig c_name0 = new ColumnConfig(this.getText("公告分类"),"peSitemanager.name");
//		c_name0
//		.setComboSQL("select t.name,t.name  from pe_pri_role t, enum_const e where t.flag_role_type=e.id and e.code in ('2','3') and e.namespace='FlagRoleType' order by t.name");
//		this.getGridConfig().addColumn(c_name0);
		
	}

	/**
	 * 重写框架方法--列更新
	 * @author linjie
	 * @return
	 */
	public Map<String, String> updateColumn() {
		Map<String, String> map = new HashMap<String, String>();
		String msg = "";
		int count = 0;
		String action = this.getColumn();
		if (this.getIds() != null && this.getIds().length() > 0) {

			String[] ids = getIds().split(",");
			
			List idList = new ArrayList();
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}

			List<PeBulletin> plist = new ArrayList<PeBulletin>();
			try {
				DetachedCriteria pubdc = DetachedCriteria
						.forClass(PeBulletin.class);
				pubdc.add(Restrictions.in("id", ids));
				plist = this.getGeneralService().getList(pubdc);
				EnumConst enumConst = null;
				
				if (action.equals("truetop")) {
					if(ids.length!=1){
						map.clear();
						map.put("success", "false");
						map.put("info", "只能操作一条记录");
						return map;
				}
				for (int i = 0; i < idList.size(); i++) {
					if(idList.get(i)==null){
						map.clear();
						map.put("success", "false");
						map.put("info", "操作失败");
						return map;
					}else{
						String yxSql="select p.id from Pe_Bulletin p where p.id='"+idList.get(i)+"'  and p.flag_isvalid='3' " 	;	
						List list=this.getGeneralService().getBySQL(yxSql);
						if(list!=null && list.size()>0){					
							map.clear();
							map.put("success", "false");
							map.put("info", "此公告为无效公告,不能设置置顶");
							return map;
						}	
					}
				}
					String zdsql="select * from Pe_Bulletin p where p.flag_istop='4028826a1e1bcbd0011e1bd94f3d0005'";
					List list=this.getGeneralService().getBySQL(zdsql);
					if(list!=null && list.size()>0){					
						map.clear();
						map.put("success", "false");
						map.put("info", "已有置顶课程");
						return map;
					}	
					
					enumConst = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagIstop", "1");
					msg = "设为置顶";
					this.flagIsTop("1");
				}
				if (action.equals("falsetop")) {
					enumConst = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagIstop", "0");
					msg = "取消置顶";
					this.flagIsTop("1");
				}
				if (action.equals("truevalid")) {
					enumConst = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagIsvalid", "1");
					msg = "设为有效";
				}
				if (action.equals("falsevalid")) {
					enumConst = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagIsvalid", "0");
					msg = "设为无效";
				}
				
				if (action.equals("trueisnew")) {
					enumConst = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagIsNew", "0");
				}
				if (action.equals("falseisnew")) {
					enumConst = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagIsNew", "1");
				}

				for (int k = 0; k < plist.size(); k++) {
					if (action.contains("top")) {
						plist.get(k).setEnumConstByFlagIstop(enumConst);
					}
					if (action.contains("valid")) {
						plist.get(k).setEnumConstByFlagIsvalid(enumConst);
					}
					if (action.contains("isnew")) {
						plist.get(k).setEnumConstByFlagIsNew(enumConst);
					}
					PeBulletin bulletin = (PeBulletin) this.getGeneralService()
							.save(plist.get(k));
					count++;
				}
			} catch (EntityException e) {
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				return map;
			}
			map.clear();
			map.put("success", "true");
			map.put("info", count + "条记录"+msg+"操作成功");

		} else {
			map.put("success", "false");
			map.put("info", "parameter value error");
		}
		return map;
	}

	public void setEntityClass() {
		this.entityClass = PeBulletin.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/information/peBulletin";

	}

	public String showAddNotice() {
		String hql = "from PeBulletin where enumConstByFlagIstop.code = '1'";
		try {
			List list = this.getGeneralService().getByHQL(hql);
			if(list.size() > 0)
				topFlag = "true";
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "show_add_notice";
	}

	/**
	 * 获得机构信息
	 * @author linjie
	 * @return
	 */
	public List<PeEnterprise> getPeSites() {
		UserSession session = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssouser = session.getSsoUser();
		DetachedCriteria childdc = DetachedCriteria
				.forClass(PeEnterprise.class);
		//childdc.addOrder(Order.asc("name"));
		List<PeEnterprise> list = null;
		try {
			/*if (session.getUserLoginType().equals("2")) {
				DetachedCriteria peenterdc = DetachedCriteria
						.forClass(PeEnterpriseManager.class);
				peenterdc.createCriteria("peEnterprise", "peEnterprise");
				peenterdc.add(Restrictions.eq("ssoUser", ssouser));
				peenterdc.setProjection(Projections.property("peEnterprise"));
				String eid = ((PeEnterprise) this.getGeneralService().getList(
						peenterdc).get(0)).getId();
				childdc.createCriteria("peEnterprise", "peEnterprise",
						DetachedCriteria.LEFT_JOIN);
				childdc.add(Restrictions.or(Restrictions.eq("peEnterprise.id",
						eid), Restrictions.eq("id", eid)));
			}*/
			childdc.add(Restrictions.sqlRestriction("(fk_parent_id is null) "));		
			childdc.addOrder(Order.asc("name"));
			list = this.getGeneralService().getList(childdc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 添加通知
	 * @author linjie
	 * @return
	 */
	public String addNotice() {

		UserSession userSession = (UserSession) ActionContext.getContext()
				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		this.getBean().setId(null);
		
		if(!this.validateToken(this.getFormToken())) {
			this.setMsg("表单出错，请重试!");
			return "message";
		}
		
		String scopeString = "";
		scopeString += this.getPerson();
		if (!(this.getGrade_id() == null || this.getGrade_id() == "" || this
				.getGrade_id() == "null")) {
			String grade = this.Chuli(this.getGrade_id());
			scopeString += "|grade:" + grade;
		} else {
			if (scopeString.indexOf("index") == 0) {
				scopeString += "|grade:all";
			} else {
				scopeString += "|grade:all";
			}
		}
		DetachedCriteria dc = null;
		boolean flag = false;
		if (!userSession.getUserLoginType().equals("3")) {
			DetachedCriteria tesdc = DetachedCriteria
					.forClass(PeEnterpriseManager.class);
			tesdc.createCriteria("ssoUser", "ssoUser");
			tesdc.createCriteria("peEnterprise", "peEnterprise");
			tesdc.add(Restrictions.eq("ssoUser.id", userSession.getId()));
			try {
				PeEnterpriseManager insManager = (PeEnterpriseManager) this
						.getGeneralService().getList(tesdc).get(0);
				String code = insManager.getPeEnterprise().getCode();
				
				List entList=userSession.getPriEnterprises();
				String siteString = "";
				for(int i=0;i<entList.size();i++)
				{
					PeEnterprise e=(PeEnterprise)entList.get(i);
					siteString+=e.getCode()+",";
				}
				siteString=siteString.substring(0,siteString.length()-1);
				
				if (userSession.getUserLoginType().equals("2") && !(this.getSite_id() == null)) {
					String site = this.Chuli(this.getSite_id());
					scopeString += "|site:" + site;
				} else {
					String site=this.Chuli(siteString);
					scopeString += "|site:" + site ;
				}
			} catch (EntityException e) {
				e.printStackTrace();
			}
			dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
			flag = true;
		} else {
			if (!(this.getSite_id() == null || this.getSite_id() == "")) {
				String site = this.Chuli(this.getSite_id());
				scopeString += "|site:" + site;
			} else {
				scopeString += "|site:all";
			}
			dc = DetachedCriteria.forClass(PeManager.class);
		}
		this.getBean().setScopeString(scopeString);
		this.getBean().setPublishDate(this.getCurSqlDate());
//		String scopeId = this.getScropId(scopeString);
//		System.out.println("****************************"+scopeId);
//		this.getBean().setScopeId(scopeId);
		dc.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
		List managers = null;
		try {
			managers = this.getGeneralService().getList(dc);
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (!managers.isEmpty()) {
			if (flag) { // 如果是分站记者
				PeEnterpriseManager peEnterpriseManager = (PeEnterpriseManager) managers.get(0);
				this.getBean().setEnterpriseManager(peEnterpriseManager);
				this.getBean().setPeSite(peEnterpriseManager.getPeEnterprise());
			} else {
				this.getBean().setPeManager((PeManager) managers.get(0));
			}
		}
		
		this.setBean((PeBulletin) super.setSubIds(this.getBean()));
		this.getBean().setEnumConstByFlagIsNew(getIsNewDefualt());
		this.getBean().setPublishDate(getCurSqlDate());
		try {
			this.getGeneralService().save(this.getBean());
			this.setMsg("添加成功");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("保存失败");
		}
		this.setTogo("/entity/information/peBulletin_showAddNotice.action");
		return "message";
	}

	private EnumConst getIsNewDefualt() {
		EnumConst ec = new EnumConst();
		ec.setId("52cce2fd23412cc201234a6bd87b00a6");
		return ec;
	}

	private String Chuli(String str) {
		StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
		int m = 0;
		String grade = "".trim();
		while (stringTokenizer.hasMoreTokens()) {
			grade += "<".trim() + stringTokenizer.nextToken().trim() + ">,";
			m++;
		}
		return grade;
	}

	/**
	 * 修改通知
	 * @author linjie
	 * @return
	 */
	public String edit() {
		try {
			String hql= "from PeBulletin where enumConstByFlagIstop.code = '1' and id != '" + this.getBean().getId() + "'";
			List list = this.getGeneralService().getByHQL(hql);
			if(list.size() > 0) {
				topFlag = "true";
			}
			cando = true;
			this.setBean((PeBulletin) this.getGeneralService().getById(
					this.getBean().getId()));
			String pmid = "";
			UserSession usersession = (UserSession) ActionContext.getContext()
					.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
			PeBulletin dbInstance = null;
			if("3".equals(usersession.getRoleId())){
				dbInstance = (PeBulletin) this.getGeneralService().getById(this.getBean().getId());
			}else{
				//判断是否有修改的权限
				List<PeEnterprise> priList = usersession.getPriEnterprises();
				List siteList=new ArrayList();
				for(int i=0;i<priList.size();i++){
					siteList.add(priList.get(i).getId());
				}
				dbInstance = (PeBulletin) this.getGeneralService().getById(this.getBean().getClass(),siteList,this.getBean().getId());
				if(null == dbInstance || "".equals(dbInstance)){
					stats="noright";
					return "infobulletinmessage";
				}
			}
			PeManager manager = this.getBean().getPeManager();
			if (manager != null) {
				pmid = manager.getSsoUser().getId();
			} else {
				pmid = this.getBean().getEnterpriseManager().getSsoUser()
						.getId();
			}
			String pid = usersession.getSsoUser().getId();
			if (!pid.equals(pmid)) {
				cando = false;
			}
			if(usersession.getUserLoginType().equals("3")) {
				cando=true;
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "show_edit_notice";
	}

	/**
	 * 修改通知
	 * @author linjie
	 * @return
	 */
	public String editexe() {

		UserSession userSession = (UserSession) ActionContext.getContext()
				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		//暂时去掉重复提交验证 lin
//		if(!this.validateToken(this.getFormToken())) {
//			this.setMsg("表单出错，请重试!");
//			return "message";
//		}
		
		String scopeString = "";
		scopeString += this.getPerson();
		String site1 = "";
		String grade1 = "";
		if (!(this.getGrade_id() == null || this.getGrade_id() == "" || this
				.getGrade_id() == "null")) {
			String grade = this.Chuli(this.getGrade_id());
			scopeString += "|grade:" + grade;
		} else {
			scopeString += "|grade:all";
		}
		if (!userSession.getUserLoginType().equals("3")) {
			DetachedCriteria tesdc = DetachedCriteria
					.forClass(PeEnterpriseManager.class);
			tesdc.createCriteria("ssoUser", "ssoUser");
			tesdc.createCriteria("peEnterprise", "peEnterprise");
			tesdc.add(Restrictions.eq("ssoUser.id", userSession.getId()));
			try {
				PeEnterpriseManager insManager = (PeEnterpriseManager) this
						.getGeneralService().getList(tesdc).get(0);
				String code = insManager.getPeEnterprise().getCode();
				
				List entList=userSession.getPriEnterprises();
				String siteString = "";
				for(int i=0;i<entList.size();i++)
				{
					PeEnterprise e=(PeEnterprise)entList.get(i);
					siteString+=e.getCode()+",";
				}
				if(!siteString.equals(""))
				siteString=siteString.substring(0,siteString.length()-1);
				
				if (userSession.getUserLoginType().equals("2") && !(this.getSite_id() == null || this.getSite_id() == "")) {
					String site = this.Chuli(this.getSite_id());
					scopeString += "|site:" + site;
				} else {
					String site = this.Chuli(siteString);
					scopeString += "|site:" + site;
				}
				
			} catch (EntityException e) {
				e.printStackTrace();
			}
		} else {
			if (!(this.getSite_id() == null || this.getSite_id() == "")) {
				String site = this.Chuli(this.getSite_id());
				scopeString += "|site:" + site;
			} else {
				scopeString += "|site:all";
			}
		}
		this.getBean().setScopeString(scopeString);
		this.getBean().setUpdateDate(getCurSqlDate());
		try {
			PeBulletin peBulletin = (PeBulletin) this.getGeneralService()
					.getById(this.getBean().getId());
			this.setBean((PeBulletin) super.setSubIds(peBulletin, this
					.getBean()));
			this.getGeneralService().save(this.getBean());
			this.setMsg("修改成功");
			this.setTogo("/entity/information/peBulletin.action");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("修改失败");
			this.setTogo("back");
		}
		return "message";
	}

	// 以下4个方法用于显示添加通知时的发送范围
	// TODO 需要横向权限
	public List<PeMajor> getPeMajors() {
		List<PeMajor> list = null;
		DetachedCriteria dc = DetachedCriteria.forClass(PeMajor.class);
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PeEdutype> getPeEdutypes() {
		List<PeEdutype> list = null;
		DetachedCriteria dc = DetachedCriteria.forClass(PeEdutype.class);
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PeGrade> getPeGrades() {
		List<PeGrade> list = null;
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzBatch.class);
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return list;
	}

	public PeBulletin getBean() {
		return (PeBulletin) super.superGetBean();
	}

	public void setBean(PeBulletin bean) {
		super.superSetBean(bean);
	}

	private String person;

	private String site_id;

	private String major_id;

	private String edutype_id;

	private String grade_id;

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getSite_id() {
		return site_id;
	}

	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}

	public String getMajor_id() {
		return major_id;
	}

	public void setMajor_id(String major_id) {
		this.major_id = major_id;
	}

	public String getEdutype_id() {
		return edutype_id;
	}

	public void setEdutype_id(String edutype_id) {
		this.edutype_id = edutype_id;
	}

	public String getGrade_id() {
		return grade_id;
	}

	public void setGrade_id(String grade_id) {
		this.grade_id = grade_id;
	}

	/**
	 * 按权限取公告列表
	 * @author linjie
	 * @return
	 */
	public String abstractList() {
//		DetachedCriteria dc = initDetachedCriteria();
//		dc = setDetachedCriteria(dc, this.getBean());
		ActionContext context = ActionContext.getContext();
		Map map1 = context.getParameters();
		Iterator it = map1.entrySet().iterator();
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
//		String sql_con = "";
		int k = 0;
		int n = 0;
		String tempsql = "";
		if (us.getUserLoginType().equals("3")) {
			tempsql = "  select * from ( ( select pb.id as id ,pb.title as title ,e.name as FlagIstop ,ec.name as FlagIsvalid,"//isnew.name as FlagIsNew,"
				+ "   '' as publishStartDate ,pb.publish_date as publishDate ,'' as publishEndDate ,	pb.update_date as updateDate ,pr.name as publisher, pb.scope_string as scopeString, " +
				"	to_char(decode(instr(pb.scope_string, 'managers'),0,'','协会管理员、')|| " +
				"                        decode(instr(pb.scope_string, 'subadmins'),0,'','一级集体管理员、')|| " + 
				"                        decode(instr(pb.scope_string, 'subadminS'),0,'','二级集体管理员、')|| " + 
				"                        decode(instr(pb.scope_string, 'students'),0,'','学生、')|| " +
				"                        decode(instr(pb.scope_string, 'kcgl'),0,'','协会管理员_课程管理、')|| " +
				"                        decode(instr(pb.scope_string, 'kcsh'),0,'','协会管理员_课程审核、')|| " +
				"                        decode(instr(pb.scope_string, 'pxcx'),0,'','协会管理员_培训查询、')|| " +
				"                        decode(instr(pb.scope_string, 'cw'),0,'','协会管理员_财务、')|| " +
				"                        decode(instr(pb.scope_string, 'kcll'),0,'','协会管理员_课程浏览、')) as scope_per, " +
						"pr.name as prname "
				+ "  from Pe_Bulletin pb ,pe_manager pm ,enum_const ec ,enum_const e ,enum_const isnew, pe_pri_role pr ,sso_user so "
				+ "  where pb.fk_manager_id = pm.id and pm.fk_sso_user_id =so.id and ec.id = pb.flag_isvalid and e.id = pb.flag_istop and isnew.id=pb.flag_isnew and so.fk_role_id = pr.id)  "
				+ "  union  "
				+ "  (select pb.id as id ,pb.title as title ,e.name as FlagIstop ,ec.name as FlagIsvalid,"//isnew.name as FlagIsNew,"
				+ "   '' as publishStartDate ,pb.publish_date as publishDate ,'' as publishEndDate ,pb.update_date as updateDate , pen.name||pr.name  as publisher, pb.scope_string as scopeString, " +
				"	to_char(decode(instr(pb.scope_string, 'managers'),0,'','协会管理员、')|| " +
				"                        decode(instr(pb.scope_string, 'subadmins'),0,'','一级集体管理员、')|| " + 
				"                        decode(instr(pb.scope_string, 'subadminS'),0,'','二级集体管理员、')|| " + 
				"                        decode(instr(pb.scope_string, 'studentS'),0,'','学生、')|| " +
				"                        decode(instr(pb.scope_string, 'kcgl'),0,'','协会管理员_课程管理、')|| " +
				"                        decode(instr(pb.scope_string, 'kcsh'),0,'','协会管理员_课程审核、')|| " +
				"                        decode(instr(pb.scope_string, 'pxcx'),0,'','协会管理员_培训查询、')|| " +
				"                        decode(instr(pb.scope_string, 'cw'),0,'','协会管理员_财务、')|| " +
				"                        decode(instr(pb.scope_string, 'kcll'),0,'','协会管理员_课程浏览、')) as scope_per, " +
						"pr.name as prname "
				+ "  from Pe_Bulletin pb ,pe_enterprise_manager pe,enum_const ec ,enum_const isnew, pe_pri_role pr ,sso_user so ,enum_const e ,pe_enterprise pen "
				+ "  where pb.fk_enterprisemanager_id = pe.id and  pe.fk_enterprise_id = pen.id and pe.fk_sso_user_id = so.id  "
				+ "  and ec.id = pb.flag_isvalid and e.id = pb.flag_istop and isnew.id=pb.flag_isnew and so.fk_role_id = pr.id) ) a where 1=1 ";
		}
		if (us.getUserLoginType().equals("2")) {
			tempsql = 
				" select * from ( select pb.id as id,\n" +
				"       pb.title as title,\n" + 
				"       e.name as FlagIstop,\n" + 
				"       ec.name as FlagIsvalid,\n" + 
				"       '' as publishStartDate,\n" + 
				"       pb.publish_date as publishDate,\n" + 
				"       '' as publishEndDate,\n" + 
				"       pb.update_date as updateDate,\n" + 
				"       '协会管理员/' || pm.true_name as publisher,\n" + 
				"       pb.scope_string as scopeString,\n" +
				"	case when instr(pb.scope_string, 'student') >0 then '是' when (1<>1) then '0' else '否' end as isStudent,\n" +
				"                        case when instr(pb.scope_string, 'managers') >0 then '是' when (1<>1) then '0' else '否' end as isManager,\n" + 
				"                        case when instr(pb.scope_string, 'subadmins') >0 then '是' when (1<>1) then '0' else '否' end as isEnterprise,\n" + 
				"                        case when instr(pb.scope_string, 'index') >0 then '是' when (1<>1) then '0' else '否' end as isIndex" +
				"  from pe_bulletin pb " +
				"	inner join pe_manager pm on pb.fk_manager_id=pm.id" +
				"	inner join enum_const e on pb.flag_istop=e.id " +
				"   inner join enum_const ec on ec.id=pb.flag_isvalid" +
				"  where 1=1" + 					
				" and (pb.scope_string like\n" + 
				" '%<' || (select en.code\n" + 
				"            from pe_enterprise en, pe_enterprise_manager em, sso_user u\n" + 
				"           where em.fk_sso_user_id = u.id\n" + 
				"             and en.id = em.fk_enterprise_id\n" + 
				"             and u.id = '"+us.getId()+"') || '>%' " +
						"or pb.scope_string like '%|site:all%')\n" + 
				" and pb.scope_string like '%subadmins%' ) a where 1=1 ";

		}

		StringBuffer buffer = new StringBuffer(tempsql);
		while (it.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
			String name = entry.getKey().toString();
		
			if (name.startsWith("search__")) {
				n++;
				String value = ((String[]) entry.getValue())[0];
				if (value == "" || "".equals(value)) {
					k++;
				} else {
//					name = name.substring(8);
					//是否置顶
					if(name.equals("search__enumConstByFlagIstop.name"))
					{
						name="FlagIstop";
					}
					//是否有效
					if(name.equals("search__enumConstByFlagIsvalid.name"))
					{
						name="FlagIsvalid";
					}
					//名字
					if(name.equals("search__title"))
					{
						name="title";
					}
					if(!(name.equals("search__publishStartDate")||name.equals("search__publishEndDate"))){
						
						buffer.append(" and a." + name + " like '%" + value	+ "%'");
					}
					//日期 
					if(name.equals("search__publishStartDate"))
					{
						name="publishStartDate";
						buffer.append(" and  to_date('" + value	+ "','yyyy-MM-dd hh24:mi:ss')  <=a.publishDate");
					}
					if(name.equals("search__publishEndDate"))
					{
						name="publishEndDate";
						buffer.append(" and  to_date('" + value	+ "','yyyy-MM-dd hh24:mi:ss')  >=a.publishDate");
					}
							
					name="";
				}
			}
		}
		String flagIsTop = this.flagIsTop(null);
		if(flagIsTop.equals("0")) {
			if (this.getSort().startsWith("enumConstBy")) {
			
				this.setSort(this.getSort().substring(11,
					this.getSort().indexOf(".")));
				buffer.append("  order by a.").append(this.getSort()).append("  ").append(this.getDir());
			} else if(this.getSort().equals("id")) {
				buffer.append(" order by a.FlagIsTop desc");
			} else {
				buffer.append("  order by a.").append(this.getSort()).append("  ").append(this.getDir());
			}

		
		} else {
			buffer.append(" order by a.FlagIsTop desc");
			if (k - n == 0 ? false : true) {
				this.flagIsTop("0");
			}
		}
		String js = null;
		if (k - n == 0 ? true : false) {
			js = super.abstractList();
		} else {
			initGrid();
			Page page = null;
			String sql = buffer.toString();
			try {
				page = this.getGeneralService().getByPageSQL(sql,
						Integer.parseInt(this.getLimit()),
						Integer.parseInt(this.getStart()));
				List jsonObjects = JsonUtil.ArrayToJsonObjects(page.getItems(),
						this.getGridConfig().getListColumnConfig());
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
	 * 按权限取公告列表（带条件）
	 * @author linjie
	 * @return
	 */
	public Page list() {
//		DetachedCriteria dc = initDetachedCriteria();
//		dc = setDetachedCriteria(dc, this.getBean());
		Page page = null;
		UserSession userSession = (UserSession) ActionContext.getContext()
				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		ActionContext context = ActionContext.getContext();
		Map map1 = context.getParameters();
		Iterator it = map1.entrySet().iterator();
		String sType = userSession.getUserLoginType();
		String sid = userSession.getSsoUser().getId();
		String sql = "";
		int k = 0;
		int n = 0;
		//System.out.println(sType);
		if (sType.equals("3")) {
			sql = "  (select pb.id as id ,pb.title as title ,e.name as FlagIstop ,ec.name as FlagIsvalid,"//isnew.name as FlagIsNew,"
					+ "  '' as publishStartDate ,pb.publish_date as publishDate ,'' as publishEndDate ,pb.update_date as updateDate ,pr.name as publisher, pb.scope_string as scopeString, " +
					"	rtrim(decode(instr(pb.scope_string, 'managers'),0,'','协会管理员_内训组、')|| " +
					"                        decode(instr(pb.scope_string, 'subadmins'),0,'','一级集体管理员、')|| " + 
					"                        decode(instr(pb.scope_string, 'secadmins'),0,'','二级集体管理员、')|| " + 
					"                        decode(instr(pb.scope_string, 'students'),0,'','学生、')|| " +
					"  decode(instr(pb.scope_string, 'stuhomepage'),0,'','学员首页、')|| " +
					" 						 decode(instr(pb.scope_string, '131AF5EC87836928E0530100007F9F54'),0,'','监管机构管理员、')|| " +
					" 						 decode(instr(pb.scope_string, 'ff808081493288bd0149335225b90036'),0,'','监管机构内训学习、'),'、') as scope_per, " +
							"pr.name as prname "
					+ "  from Pe_Bulletin pb ,pe_manager pm ,enum_const ec ,enum_const e ,enum_const isnew, pe_pri_role pr ,sso_user so "
					+ "  where pb.fk_manager_id = pm.id and pm.fk_sso_user_id =so.id and ec.id = pb.flag_isvalid and e.id = pb.flag_istop and isnew.id=pb.flag_isnew and so.fk_role_id = pr.id)  "
					+ "  union  "
					+ "  (select pb.id as id ,pb.title as title ,e.name as FlagIstop ,ec.name as FlagIsvalid,"//isnew.name as FlagIsNew,"
					+ "  '' as publishStartDate ,pb.publish_date as publishDate , '' as publishEndDate,pb.update_date as updateDate , pen.name||pr.name  as publisher, pb.scope_string as scopeString, " +
					"	rtrim(decode(instr(pb.scope_string, 'managers'),0,'','协会管理员、')|| " +
					"                        decode(instr(pb.scope_string, 'subadmins'),0,'','一级集体管理员、')|| " + 
					"                        decode(instr(pb.scope_string, 'secadmins'),0,'','二级集体管理员、')|| " + 
					"                        decode(instr(pb.scope_string, 'students'),0,'','学生、')|| " +
					" 						 decode(instr(pb.scope_string, '131AF5EC87836928E0530100007F9F54'),0,'','监管机构管理员、')|| " +
					" 						 decode(instr(pb.scope_string, 'ff808081493288bd0149335225b90036'),0,'','监管机构内训学习、'),'、') as scope_per, " +
							"pr.name as prname "
					+ "  from Pe_Bulletin pb ,pe_enterprise_manager pe,enum_const ec ,enum_const isnew, pe_pri_role pr ,sso_user so ,enum_const e ,pe_enterprise pen "
					+ "  where pb.fk_enterprisemanager_id = pe.id and  pe.fk_enterprise_id = pen.id and pe.fk_sso_user_id = so.id  "
					+ "  and ec.id = pb.flag_isvalid and e.id = pb.flag_istop and isnew.id=pb.flag_isnew and so.fk_role_id = pr.id)";
		}
		if (sType.equals("4") || sType.equals("2")) {

			
			sql = 
				"select pb.id as id,\n" +
				"       pb.title as title,\n" + 
				"       e.name as FlagIstop,\n" + 
				"       ec.name as FlagIsvalid,\n" + 
				"       '' as publishStartDate,\n" + 
				"       pb.publish_date as publishDate,\n" + 
				"       '' as publishEndDate,\n" + 
				"       pb.update_date as updateDate,\n" + 
				"       '协会管理员/' || pm.true_name as publisher,\n" + 
				"       pb.scope_string as scopeString,\n" +
				"	case when instr(pb.scope_string, 'student') >0 then '是' when (1<>1) then '0' else '否' end as isStudent,\n" +
				"                        case when instr(pb.scope_string, 'managers') >0 then '是' when (1<>1) then '0' else '否' end as isManager,\n" + 
				"                        case when instr(pb.scope_string, 'subadmins') >0 then '是' when (1<>1) then '0' else '否' end as isEnterprise,\n" + 
				"                        case when instr(pb.scope_string, 'index') >0 then '是' when (1<>1) then '0' else '否' end as isIndex" +
				"  from pe_bulletin pb " +
				"	inner join pe_manager pm on pb.fk_manager_id=pm.id" +
				"	inner join enum_const e on pb.flag_istop=e.id " +
				"   inner join enum_const ec on ec.id=pb.flag_isvalid" +
				"  where 1=1" + 					
				" and (pb.scope_string like\n" + 
				" '%<' || (select en.code\n" + 
				"            from pe_enterprise en, pe_enterprise_manager em, sso_user u\n" + 
				"           where em.fk_sso_user_id = u.id\n" + 
				"             and en.id = em.fk_enterprise_id\n" + 
				"             and u.id = '"+sid+"') || '>%' " +
						"or pb.scope_string like '%|site:all%')\n" + 
				" and pb.scope_string like '%subadmins%'";




		}
		StringBuffer buffer = new StringBuffer(sql);
		while (it.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
			String name = entry.getKey().toString();
		
			if (name.startsWith("search__")) {
				n++;
				String value = ((String[]) entry.getValue())[0];
				if (value == "" || "".equals(value)) {
					k++;
				} else {
//					name = name.substring(8);
					//是否置顶
					if(name.equals("search__enumConstByFlagIstop.name"))
					{
						name="FlagIstop";
					}
					//是否有效
					if(name.equals("search__enumConstByFlagIsvalid.name"))
					{
						name="FlagIsvalid";
					}
					//名字
					if(name.equals("search__title"))
					{
						name="title";
					}
					if(!(name.equals("search__publishStartDate")||name.equals("search__publishEndDate"))){
						
						buffer.append(" and a." + name + " like '%" + value	+ "%'");
					}
					//日期 
					if(name.equals("search__publishStartDate"))
					{
						name="publishStartDate";
						buffer.append(" and  to_date('" + value	+ "','yyyy-MM-dd hh24:mi:ss')  <=a.publishDate");
					}
					if(name.equals("search__publishEndDate"))
					{
						name="publishEndDate";
						buffer.append(" and  to_date('" + value	+ "','yyyy-MM-dd hh24:mi:ss')  >=a.publishDate");
					}
							
					name="";
				
				}
			}
		}
		try {
			String flagIsTop = this.flagIsTop(null);
			if(flagIsTop.equals("0")) {
				if (this.getSort().startsWith("enumConstBy")) {
				this.setSort(this.getSort().substring(11,
						this.getSort().indexOf(".")));
				} 
				else if(this.getSort().equals("id")) {
					sql += " order by FlagIsTop desc,publishDate desc ";
				} else {
					sql = sql + "  order by " + this.getSort() + "  " + this.getDir();
				}
			} 
			 else {
			
					sql +=  " order by FlagIsTop desc,publishDate desc ";
					flagIsTop("0");
			}

			page = this.getGeneralService().getByPageSQL(sql,
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
//		this.setSort("id");
//		this.setDir("desc");
		return page;
	}

//	 public DetachedCriteria initDetachedCriteria() {
//	 UserSession userSession =
//	 (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//	 DetachedCriteria dc = DetachedCriteria.forClass(PeBulletin.class);
//	 dc.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid")
//	 .createAlias("enumConstByFlagIstop", "enumConstByFlagIstop");
//	 dc.addOrder(Order.desc("publishDate"));
//	 if(userSession.getUserLoginType().equals("2")){
//	 DetachedCriteria pedc =
//	 DetachedCriteria.forClass(PeEnterpriseManager.class);
//	 pedc.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
//	 pedc.setProjection(Projections.property("id"));
//	 dc.createAlias("enterpriseManager", "enterpriseManager");
//	 dc.add(Subqueries.propertyEq("enterpriseManager.id", pedc));
//	 }
//	 return dc;
//	 }

	/**
	 * 重写框架方法：删除数据前的校验
	 * @author linjie
	 * @return
	 */
	@Override
	public void checkBeforeDelete(List idList) throws EntityException {
		try {
			for (Object id : idList) {
				if (((PeBulletin) this.getGeneralService().getById(
						id.toString())).getEnumConstByFlagIsvalid().getCode()
						.equals("1"))
					throw new EntityException("包含有有效状态的公告信息，删除失败！");
			}
		} catch (RuntimeException e) {
			throw new EntityException("删除失败");
		}
	}

	public boolean isCando() {
		return cando;
	}

	public void setCando(boolean cando) {
		this.cando = cando;
	}

	/**
	 * 框架方法：或者查询列表所需数据
	 * @author linjie
	 * @return
	 */
	public DetachedCriteria initDetachedCriteria() {

		DetachedCriteria criteria = DetachedCriteria.forClass(PeBulletin.class);
		return criteria;
	}

	/**
	 * 获得当前服务时间
	 * @author linjie
	 * @return
	 */
	private java.sql.Date getCurSqlDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		Date currentTime_2 = null;
		try {
			currentTime_2 = formatter.parse(dateString);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		java.sql.Date d2 = new java.sql.Date(currentTime_2.getTime());
		return d2;
	}

	public String getStats() {
		return stats;
	}

	public void setStats(String stats) {
		this.stats = stats;
	}
	
	/**
	 * 是否置顶
	 * @author linjie
	 * @return
	 */
	public String flagIsTop(String flag) {
		String flagIsTop = null;
		if(flag == null) {
			flagIsTop = (String)ServletActionContext.getRequest().getSession().getAttribute("flagIsTop");
			if(flagIsTop == null) {
				flagIsTop = "1";
				ServletActionContext.getRequest().getSession().setAttribute("flagIsTop", flag);
			}
		} else {
			ServletActionContext.getRequest().getSession().setAttribute("flagIsTop", flag);
		}
		
		return flagIsTop;
	}

	public String getTopFlag() {
		return topFlag;
	}

	public void setTopFlag(String topFlag) {
		this.topFlag = topFlag;
	}
}
