package com.whaty.platform.entity.web.action.basic;

import java.math.BigDecimal;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PrepaidAccountManageAction extends MyBaseAction<PeEnterpriseManager> {
	private List<PeBzzStudent> stuList;
	private EnumConst enumConst;
	public List<PeBzzStudent> getStuList() {
		return stuList;
	}


	public void setStuList(List<PeBzzStudent> stuList) {
		this.stuList = stuList;
	}


	public EnumConst getEnumConst() {
		return enumConst;
	}


	public void setEnumConst(EnumConst enumConst) {
		this.enumConst = enumConst;
	}

	/**
	 * 初始化列表
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle("预付费账户查询");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("机构代码"), "code", true,false, true, "");
		this.getGridConfig().addColumn(this.getText("单位名称"), "name",true,false, true, "");
		this.getGridConfig().addColumn(this.getText("账户总额"),"sumAmount",false,false,false,"sumAmount");
		this.getGridConfig().addColumn(this.getText("已使用"), "samount",false, false, false, "amount");
		this.getGridConfig().addColumn(this.getText("账户剩余金额"), "balance",true, false, true, "balance");
		this.getGridConfig().addColumn(this.getText("用户名"),"loginId",false,false,false,"loginId");
		//this.getGridConfig().addRenderScript(this.getText("充值记录"),"{return '<a href=\"/entity/basic/buyClassHourRecord.action?userId='+record.data['loginId']+'\">充值记录</a>';}","");
		//Lee 原版
		//this.getGridConfig().addRenderScript(this.getText("消费记录"),"{return '<a href=\"/entity/basic/checkClassHourRecord.action?flag=old&userId='+record.data['loginId']+'\">消费记录</a>';}","");
		//Lee  
		//Lee 2013年12月27日
		this.getGridConfig().addRenderScript(this.getText("充值记录"),"{return '<a target=\"_blank\" href=\"/entity/basic/checkClassHourRecord.action?forg=c&flag=old&userId='+record.data['loginId']+'\">充值记录</a>';}","");
		this.getGridConfig().addRenderScript(this.getText("分配记录"),"{return '<a target=\"_blank\" href=\"/entity/basic/checkClassHourRecord.action?forg=y&flag=old&userId='+record.data['loginId']+'\">分配记录</a>';}","");
		this.getGridConfig().addRenderScript(this.getText("剥离记录"),"{return '<a target=\"_blank\" href=\"/entity/basic/checkClassHourRecord.action?forg=b&flag=old&userId='+record.data['loginId']+'\">剥离记录</a>';}","");
		this.getGridConfig().addRenderScript(this.getText("购买课程记录"),"{return '<a target=\"_blank\" href=\"/entity/basic/checkClassHourRecord.action?forg=d&flag=old&userId='+record.data['code']+'\">购买课程记录</a>';}","");
		this.getGridConfig().addRenderScript(this.getText("预付费账户变动记录"),"{return '<a target=\"_blank\" href=\"/entity/basic/yuFuFeeRecord.action?userId='+record.data['loginId']+'\">预付费账户变动记录</a>';}","");
	}
	     

	public PeEnterpriseManager getBean() {
		// TODO Auto-generated method stub
		return (PeEnterpriseManager) super.superGetBean();
	}

	public void setBean(PeEnterpriseManager enterpriseManager) {
		// TODO Auto-generated method stub
		super.superSetBean(enterpriseManager);
	}
	
	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeEnterpriseManager.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/prepaidAccountManage";
	}

	public BigDecimal calClassHour(SsoUser ssoUser) {
		BigDecimal s = (ssoUser.getSumAmount()==null || ssoUser.getSumAmount().equals("")) ? new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP) : new BigDecimal(ssoUser.getSumAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal a = (ssoUser.getAmount() ==null || ssoUser.getAmount().equals("")) ? new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP) : new BigDecimal(ssoUser.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
		return s.subtract(a).setScale(1, BigDecimal.ROUND_HALF_UP);
 	}
	
	/**
	 * 初始化bean
	 * @author linjie
	 * @return
	 */
	public void initBean() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = null;
		dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
		dc.createCriteria("ssoUser", "ssoUser");
		dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
		List<PeEnterpriseManager> list = null;
		PeEnterpriseManager peEnterpriseManager = null;
		try {
			list = this.getGeneralService().getList(dc);
			if(null != list && list.size() > 0){
				peEnterpriseManager = list.get(0);
			}else{
				peEnterpriseManager = new PeEnterpriseManager();
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setBean(peEnterpriseManager);
	}
	
	
	public String toAccountManage(){
		initBean();
		return "accountManageAction";
	}
	
	/**
	 * 购买学时
	 * @author linjie
	 * @return
	 */
	public String toPurchaseStudyHour(){
		DetachedCriteria dc = DetachedCriteria.forClass(EnumConst.class);
		dc.add(Restrictions.eq("code", "0"));
		dc.add(Restrictions.eq("namespace", "ClassHourRate"));
		try {
			enumConst = (EnumConst)this.getGeneralService().getList(dc).get(0);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "purchaseStudyHour";
	}

	/**
	 * 分配学时
	 * @author linjie
	 * @return
	 */
	public String toAssignStudyHour(){
		initBean();
		List<PeBzzStudent> stuList = this.initAssignStudyHour();
		this.setStuList(stuList);
		return "assignStudyHour";
	}
	
	public String viewPurchaseHistory(){
		
		return "viewPurchaseHistory";
	}
	
	public String viewAssignHistory(){
		return "viewAssignHistory";
	}
	
	/**
	 * 分配学时初始化
	 * @author linjie
	 * @return
	 */
	public List<PeBzzStudent> initAssignStudyHour() {
		
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
		dc.createCriteria("ssoUser","ssoUser").createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid", DetachedCriteria.LEFT_JOIN);;
		dc.createAlias("peEnterprise", "peEnterprise",DetachedCriteria.LEFT_JOIN);

		if (!us.getUserLoginType().equals("3")) {
			String peEnterpriseCode =  (String)ServletActionContext.getRequest()
			.getSession().getAttribute("peEnterprisemanager");
			
			DetachedCriteria dc2 = DetachedCriteria.forClass(PeEnterprise.class);
			dc2.add(Restrictions.eq("code", peEnterpriseCode));
			dc2.setProjection(Property.forName("id"));
			dc.add(Restrictions.or(Restrictions.eq("peEnterprise.code", peEnterpriseCode), Property.forName("peEnterprise.peEnterprise.id").in(dc2)));
		}
		List<PeBzzStudent> list = null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 重写框架方法：数据库查询（带sql条件）
	 * @author linjie
	 * @return
	 */
	@Override
	public Page list(){
		Page page = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select * from ( select pem.id as id,pe.code as code ,pe.name as name,su.SUM_AMOUNT as sumamount,\n su.amount as amount,(nvl(su.SUM_AMOUNT, 0) - nvl(su.amount, 0)) as balance \n,su.login_id as loginid from  pe_enterprise_manager pem,");
			sql.append(" pe_enterprise pe,sso_user su \n where pem.fk_sso_user_id = su.id and pe.id = pem.fk_enterprise_id order by code ) where 1 = 1 and code <>'0000' ");
			this.setSqlCondition(sql);
			page = getGeneralService().getByPageSQL(sql.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 计算学时数
	 * @author linjie
	 * @return
	 */
	public String countClassHour(int num, String price) {
		BigDecimal bnum = new BigDecimal(num);
		BigDecimal bprice = new BigDecimal(price);
		return bnum.multiply(bprice).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}
}
