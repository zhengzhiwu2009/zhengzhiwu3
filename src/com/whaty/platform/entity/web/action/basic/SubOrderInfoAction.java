package com.whaty.platform.entity.web.action.basic;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.entity.web.action.basic.PeSubEnterpriseAction.PeSubInner;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class SubOrderInfoAction extends MyBaseAction{

	private String id;
	
	/**
	 * 初始化列表
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	this.getGridConfig().setCapability(false, false, false);
	this.getGridConfig().setShowCheckBox(false);
	this.getGridConfig().setTitle("二级机构单位列表");
	this.getGridConfig().addColumn(this.getText("ID"), "id",false,false,false,"TextField",true,50);
	this.getGridConfig().addColumn(this.getText("用户名"), "loginId",true,false,true,"TextField",true,50);
	this.getGridConfig().addColumn(this.getText("二级机构名称"), "name", true,false,true,"TextField",false,100);
	this.getGridConfig().addColumn(this.getText("管理员姓名"), "pename",true,false,true,"TextField",true,50);
	this.getGridConfig().addColumn(this.getText("联系电话"), "phone",true,false,true,"TextField",true,50);
	this.getGridConfig().addColumn(this.getText("学员人数"), "num", false, false, true, "");
	
	this
	.getGridConfig()
	.addRenderScript(
			this.getText("订单列表"),
			"{return '<a href=/entity/basic/orderInfoList.action?id='+record.data['id']+'>订单列表</a>';}",
			"");
	}

	public PeSubInner getBean() {
		// TODO Auto-generated method stub
		return (PeSubInner) super.superGetBean();
	}

	public void setBean(PeSubInner peSubInner) {
		// TODO Auto-generated method stub
		super.superSetBean(peSubInner);
	}
	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/subOrderInfo";
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		return super.initDetachedCriteria();
	}
	
	/**
	 * 重写框架方法：订单信息（带sql条件）
	 * @author linjie
	 * @return
	 */
	public Page list() {
		Page page = null;

		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String pid ="";
		String sql_pid = "";
		if(null != this.getBean() && !"".equals(this.getBean()) && this.getBean().getPeEnterprise()!=null){
			pid = this.getBean().getPeEnterprise().getId();
		}else{
			pid = this.getPeEnterpriseByUs(us).getId();
		}
		sql_pid = " and p.fk_parent_id = '"+pid+"' ";
		try {
			StringBuffer  sql_temp = new StringBuffer();
			sql_temp.append("SELECT * ");
			sql_temp.append("FROM   (SELECT p.id                   AS id, ");
			sql_temp.append("               pem.login_id           AS loginId, ");
			sql_temp.append("               p.name                 AS name, ");
			sql_temp.append("               pem.name               AS pename, ");
			sql_temp.append("               pem.phone              AS phone, ");
//			sql_temp.append("               p.code                 AS code, ");
//			sql_temp.append("               pp.name                AS p_name, ");
			sql_temp.append("               Count(DISTINCT ps.id)  AS num, ");
			//sql_temp.append("               Count(DISTINCT pem.id) AS mngnum, ");
			sql_temp.append("               ec.name                AS combobox_elective, ");
//			sql_temp.append("               pem.mobile_phone       AS mophone, ");
			sql_temp.append("               pem.email              AS pememail, ");
			sql_temp.append("               p.fzr_depart           AS fzrDepart, ");
			sql_temp.append("               p.industry             AS industry, ");
			sql_temp.append("               p.address              AS address, ");
			sql_temp.append("               p.zipcode              AS zipcode, ");
			sql_temp.append("               p.fax                  AS fax, ");
			sql_temp.append("               p.info_sheng           AS infoSheng, ");
			sql_temp.append("               p.info_shi             AS infoShi, ");
			sql_temp.append("               p.info_jiedao          AS infoJiedao, ");
			sql_temp.append("               p.fzr_name             AS fzrName, ");
			sql_temp.append("               p.fzr_xb               AS fzrXb, ");
//			sql_temp.append("               p.fzr_depart           AS fzrDepart, ");
			sql_temp.append("               p.fzr_position         AS fzrPosition, ");
			sql_temp.append("               p.fzr_phone            AS fzrPhone, ");
			sql_temp.append("               p.fzr_mobile           AS fzrMobile, ");
			sql_temp.append("               p.fzr_email            AS fzrEmail, ");
			sql_temp.append("               p.fzr_address          AS fzrAddress, ");
			sql_temp.append("               p.lxr_name             AS lxrName, ");
			sql_temp.append("               p.lxr_xb               AS lxrXb, ");
			sql_temp.append("               p.lxr_depart           AS lxrDepart, ");
			sql_temp.append("               p.lxr_position         AS lxrPosition, ");
			sql_temp.append("               p.lxr_phone            AS lxrPhone, ");
			sql_temp.append("               p.lxr_mobile           AS lxrMobile, ");
			sql_temp.append("               p.lxr_email            AS lxrEmail, ");
			sql_temp.append("               p.lxr_address          AS lxrAddress ");
//			sql_temp.append("               'batch_id', ");
		
			sql_temp.append("        FROM   enum_const ec, ");
			sql_temp.append("               pe_enterprise p ");
//			sql_temp.append("               INNER JOIN pe_enterprise pp ");
//			sql_temp.append("                 ON p.fk_parent_id = pp.id ");
			sql_temp.append("               LEFT OUTER JOIN (SELECT ps.id, ");
			sql_temp.append("                                       ps.fk_enterprise_id ");
			sql_temp.append("                                FROM   pe_bzz_student ps, ");
			sql_temp.append("                                       sso_user su ");
			//sql_temp.append("                                       pe_bzz_batch b ");
			sql_temp.append("                                WHERE  ps.fk_sso_user_id = su.id ");
			sql_temp.append("                                       AND su.flag_isvalid = '2' ");
			//sql_temp.append("                                       AND ps.flag_rank_state = '402880f827f5b99b0127f5bdadc70006' ");
			sql_temp.append("                                      )ps ");
			sql_temp.append("                 ON p.id = ps.fk_enterprise_id ");
			sql_temp.append("               LEFT OUTER JOIN (SELECT DISTINCT pem.id, ");
			sql_temp.append("                                                pem.login_id, ");
			sql_temp.append("                                                pem.name, ");
			sql_temp.append("                                                pem.mobile_phone, ");
			sql_temp.append("                                                pem.phone, ");
			sql_temp.append("                                                pem.email, ");
			sql_temp.append("                                                pem.fk_enterprise_id ");
			sql_temp.append("                                FROM   pe_enterprise_manager pem ");
			sql_temp.append("                                       INNER JOIN sso_user u ");
			sql_temp.append("                                         ON pem.fk_sso_user_id = u.id ");
			//sql_temp.append("                                WHERE  u.fk_role_id = '402880f322736b760122737a968a0010' ");
			//sql_temp.append("                                        OR u.fk_role_id = '402880f322736b760122737a60c40008'" );
			sql_temp.append(		                           ") pem ");
			sql_temp.append("                 ON p.id = pem.fk_enterprise_id ");
			sql_temp.append("        WHERE  p.FLAG_ELECTIVE = ec.id ");
			sql_temp.append(sql_pid);
			sql_temp.append("               AND p.fk_parent_id IS NOT NULL ");
			sql_temp.append("        GROUP  BY p.id, ");
			sql_temp.append("                  pem.login_id, ");
			sql_temp.append("                  pem.name, ");
			sql_temp.append("                  pem.phone, ");
			sql_temp.append("                  pem.email, ");
			sql_temp.append("                  p.name, ");
			sql_temp.append("                  p.code, ");
//			sql_temp.append("                  pp.name, ");
			sql_temp.append("                  p.industry, ");
			sql_temp.append("                  p.address, ");
			sql_temp.append("                  p.zipcode, ");
			sql_temp.append("                  p.fax, ");
			sql_temp.append("                  p.info_sheng, ");
			sql_temp.append("                  p.info_shi, ");
			sql_temp.append("                  p.info_jiedao, ");
			sql_temp.append("                  p.fzr_name, ");
			sql_temp.append("                  p.fzr_xb, ");
			sql_temp.append("                  p.fzr_depart, ");
			sql_temp.append("                  p.fzr_position, ");
			sql_temp.append("                  p.fzr_phone, ");
			sql_temp.append("                  p.fzr_mobile, ");
			sql_temp.append("                  p.fzr_email, ");
			sql_temp.append("                  p.fzr_address, ");
			sql_temp.append("                  p.lxr_name, ");
			sql_temp.append("                  p.lxr_xb, ");
			sql_temp.append("                  p.lxr_depart, ");
			sql_temp.append("                  p.lxr_position, ");
			sql_temp.append("                  p.lxr_phone, ");
			sql_temp.append("                  p.lxr_mobile, ");
			sql_temp.append("                  p.lxr_email, ");
			sql_temp.append("                  p.lxr_address, ");
			sql_temp.append("                  pem.fk_enterprise_id, ");
			sql_temp.append("                  ec.name) p ");
			sql_temp.append("WHERE  1 = 1 ");
			this.setSqlCondition(sql_temp);
			page = this.getGeneralService().getByPageSQL(sql_temp.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}

		return page;
	}
	
	/**
	 * 获得当前用户所在机构
	 * @author linjie
	 * @return
	 */
	private PeEnterprise getPeEnterpriseByUs(UserSession us){
		DetachedCriteria dc = null;
		dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
		dc.createCriteria("peEnterprise");
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
		return peEnterpriseManager.getPeEnterprise();
	}
	
	
	/**
	 * 查看订单
	 * @author linjie
	 * @return
	 */
	public String viewOrder() {
	
		String sql = 
			"\n" +
			"select su.login_id from sso_user su\n" + 
			"inner join pe_enterprise_manager pem on su.login_id = pem.login_id\n" + 
			"inner join pe_enterprise pe on pem.fk_enterprise_id = pe.id\n" + 
			"where pe.id = '"+id+"'";
		List list = null;
		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String loginId = "error";
		if(list != null && list.size()>0) {
			loginId = list.get(0).toString();
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.createCriteria("ssoUser", "ssoUser");
		dc.createCriteria("enumConstByFlagPaymentState");
		dc.createCriteria("enumConstByFlagPaymentType");
		dc.createCriteria("enumConstByFlagOrderState");
		dc.createCriteria("enumConstByFlagPaymentMethod");
		dc.createCriteria("enumConstByFlagOrderType");
		dc.createCriteria("enumConstByFlagRefundState");
		dc.add(Restrictions.eq("ssoUser.loginId", loginId));
		List orderList = null; 
		try {
			orderList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
