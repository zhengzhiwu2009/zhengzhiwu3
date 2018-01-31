package com.whaty.platform.entity.web.action.basic;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinFragment;

import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PrBzzPriManagerEnterprise;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

/**
 * 企业管理员企业管理范围管理
 * @author sunjicheng
 */
public class ManagerEnterpriseAction extends MyBaseAction {

	private String opTag;  //操作标志
	
	private String mLoginId;
	@Override
	public void initGrid() {
		
		String preStr = "";
//		this.getGridConfig().setCanBack(true);
		
		if (opTag == null || opTag.equals("view")) {
			this.getGridConfig().setCapability(false, true, true);
			preStr = "peEnterprise.";
			
			this.getGridConfig().addMenuScript(this.getText("添加"),
					"{window.navigate('/entity/basic/managerEnterprise.action?mLoginId=" + 
					mLoginId + "&opTag=add');}");
			
			
			this.getGridConfig().addMenuScript(this.getText("返回"),
					"{window.navigate('/entity/basic/enterpriseManager.action?backParam=true');}");
			this.setCanNavigate(true);
//			this.getGridConfig().setBackUrl(this.getBackUrl(ServletActionContext.getRequest()));
		} else if(opTag.equals("add")) {
			this.getGridConfig().setCapability(false, false, false);
			this.getGridConfig().addMenuFunction(this.getText("完成"), "/entity/basic/managerEnterprise_addEnterprise.action?mLoginId=" + 
					mLoginId, false, false);
			this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back();}");
//			this.getGridConfig().setBackUrl(null);
//			this.getGridConfig().addMenuScript(this.getText("返回"),
//			"{history.back();}");
		}

		this.getGridConfig().setTitle("管理员企业范围");
		
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("企业名称"), preStr + "name");
		
		this.getGridConfig().addColumn(this.getText("企业编号"), preStr + "code", true);
		
		this.getGridConfig().addColumn(this.getText("所在集团"), preStr + "peEnterprise.name", false);
		
	}

	
	@SuppressWarnings("unchecked")
	public void setEntityClass() {
		this.entityClass = PrBzzPriManagerEnterprise.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/managerEnterprise";
	}
	
	public PrBzzPriManagerEnterprise getBean() {
		return (PrBzzPriManagerEnterprise) super.superGetBean();
	}

	public void setBean(PrBzzPriManagerEnterprise bean) {
		super.superSetBean(bean);
	}
	
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria criteria = null;
		
		
		
		criteria = DetachedCriteria
			.forClass(PrBzzPriManagerEnterprise.class);
		
		criteria.createCriteria("peEnterprise", "peEnterprise");
		criteria.createCriteria("ssoUser", "ssoUser");
		criteria.createCriteria("peEnterprise.peEnterprise", "pEnt",JoinFragment.LEFT_OUTER_JOIN );
		
		criteria.add(Restrictions.eq("ssoUser.loginId", mLoginId));
		
		//显示管理员所有的企业管理范围
		if (opTag == null || opTag.equals("view")) {
			this.entityClass = PrBzzPriManagerEnterprise.class;
			//上面已完成该功能
			return criteria;
		
		} else if(opTag.equals("add")) {  //显示不在该管理员管理范围内的，管理员所在一级企业下的所有二级企业
			this.entityClass = PeEnterprise.class;
			//找出该管理员已有的企业管理范围
			List<PrBzzPriManagerEnterprise> rangeList = null;
			try {
				rangeList = this.getGeneralService().getList(criteria);
			} catch (EntityException e1) {
				e1.printStackTrace();
			}
			List<String> rangeEntIds = new ArrayList<String>();  //已有管理企业的id
			for(PrBzzPriManagerEnterprise mngInfo : rangeList) {
				rangeEntIds.add(mngInfo.getPeEnterprise().getId());
			}
			//找出该管理员信息
			criteria = DetachedCriteria
				.forClass(PeEnterpriseManager.class);
			
			criteria.createCriteria("peEnterprise", "peEnterprise");
			criteria.createCriteria("ssoUser", "ssoUser");
			criteria.add(Restrictions.eq("ssoUser.loginId",  mLoginId));
			
			List tList = null;
			try {
				
				tList = this.getGeneralService().getList(criteria);
			} catch (EntityException e) {
				e.printStackTrace();
				return null;
			}
			
			criteria = DetachedCriteria
				.forClass(PeEnterprise.class);
			
			PeEnterpriseManager pem = null;//管理员信息
			if(tList != null && tList.size() != 0) {
				pem = (PeEnterpriseManager) tList.get(0);
				
				//找出该管理员所在的企业
				String entId = null;
				if(pem.getPeEnterprise() != null) {
					//用户在二级企业，即存在一级企业
					if(pem.getPeEnterprise().getPeEnterprise() != null) {
						entId = pem.getPeEnterprise().getPeEnterprise().getId();
					} else {  //用户就在一级企业
						entId = pem.getPeEnterprise().getId();
					}
				}
				
				//查询管理员所在一级企业下的所有二级企业
				criteria.createCriteria("peEnterprise",DetachedCriteria.LEFT_JOIN);
				criteria.add(Restrictions.or(Restrictions.eq("peEnterprise.id", entId), Restrictions.eq("id", entId)));
				if(rangeEntIds.size() != 0) {
					criteria.add(Restrictions.not(Restrictions.in("id", rangeEntIds)));
				}
			}
			
		}
		
		
//		try {
//			List t = this.getGeneralService().getList(criteria);
//			System.out.print(t.size());
//		} catch (EntityException e) {
//			e.printStackTrace();
//		}
		return criteria;
	}
	
	
	@Override
	public String abstractDetail() {
		Map map = new HashMap();
		DetachedCriteria dc = DetachedCriteria.forClass(PrBzzPriManagerEnterprise.class);
		dc.createCriteria("ssoUser", "ssoUser");
		dc.createCriteria("peEnterprise", "peEnterprise");
		dc.add(Restrictions.eq("id", this.getBean().getId()));
		
		try {
			List meList = this.getGeneralService().getList(dc);
			if (meList != null && meList.size() !=0) {
				PrBzzPriManagerEnterprise managerEnterprise = (PrBzzPriManagerEnterprise) meList.get(0);
			
				Container o = new Container();
				o.setBean(managerEnterprise);
				List list = new ArrayList();
				list.add(o);
				map.put("totalCount", 1);
				map.put("models", list);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		this.setJsonString(JsonUtil.toJSONString(map));
		JsonUtil.setDateformat("yyyy-MM-dd");
		return json();
	}
	

	/**
	 * action 添加管理员企业管理范围
	 * @return 查看管理员企业管理范围
	 */
	public String addEnterprise() {
		
		String[] eIds = this.getIds().split(",");
		
		try {
			DetachedCriteria dc = null;
			dc = DetachedCriteria.forClass(SsoUser.class);
			dc.add(Restrictions.eq("loginId", mLoginId));
		
			SsoUser su = (SsoUser) this.getGeneralService().getList(dc).get(0);
			
			List<PrBzzPriManagerEnterprise> newMeList = new ArrayList<PrBzzPriManagerEnterprise>();
			for(String eId : eIds) {
				PeEnterprise pe = new PeEnterprise();
				pe.setId(eId);
				
				PrBzzPriManagerEnterprise me = new PrBzzPriManagerEnterprise();
				me.setPeEnterprise(pe);
				me.setSsoUser(su);
				
				newMeList.add(me);
			}
			this.getGeneralService().saveList(newMeList);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		this.setOpTag("view");
		return "addSuccess";
	}


	public String getOpTag() {
		return opTag;
	}


	public void setOpTag(String opTag) {
		this.opTag = opTag;
	}


	public String getMLoginId() {
		return mLoginId;
	}


	public void setMLoginId(String loginId) {
		mLoginId = loginId;
	}

}
