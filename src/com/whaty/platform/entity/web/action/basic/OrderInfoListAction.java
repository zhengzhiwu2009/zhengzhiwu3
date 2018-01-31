package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class OrderInfoListAction extends MyBaseAction<PeBzzOrderInfo>{
	
	private List<PrBzzTchStuElective> electiveList = new ArrayList<PrBzzTchStuElective>();
	private PeBzzInvoiceInfo peBzzInvoiceInfo;
	private PeBzzStudent peBzzStudent;
	private SsoUser ssoUser;
	private PeEnterpriseManager peEnterpriseManager;
	private EnumConstService enumConstService;
	private double price;   //课程的价格

	private String id;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle("订单信息列表");
		this.getGridConfig().addColumn(this.getText("ID"), "id",false,false,false,"TextField",true,50);
		this.getGridConfig().addColumn(this.getText("订单号"), "seq");
		this.getGridConfig().addColumn(this.getText("备忘录"), "cname");
		this.getGridConfig().addColumn(this.getText("到账情况"), "enumConstByFlagPaymentState.name");
		this.getGridConfig().addColumn(this.getText("到账时间"), "paymentDate");
		this.getGridConfig().addColumn(this.getText("支付类型"), "enumConstByFlagPaymentType.name");
		this.getGridConfig().addColumn(this.getText("支付方式"), "enumConstByFlagPaymentMethod.name");
		this.getGridConfig().addColumn(this.getText("支付金额"), "amount");
		this.getGridConfig().addColumn(this.getText("订单状态"), "enumConstByFlagOrderState.name");
		this.getGridConfig().addColumn(this.getText("订单类型"), "enumConstByFlagOrderType.name");
		this.getGridConfig().addColumn(this.getText("订单退费状态"), "enumConstByFlagRefundState.name");
		ColumnConfig isZhuFu = new ColumnConfig(this.getText("支付状态"),
				"combobox_ZhiFuState", true, false, true, "TextField", true,
				25, "");
		isZhuFu.setComboSQL("select id, name  from enum_const where namespace = 'FlagZhiFuState' ");
		this.getGridConfig().addColumn(isZhuFu);
		this
		.getGridConfig()
		.addRenderScript(
				this.getText("订单详情"),
				"{return '<a href=/entity/basic/paymentManage_orderDetail.action?bean.id='+record.data['id']+'>订单详情</a>';}",
				"");
		
		
		this.getGridConfig().addMenuScript(this.getText("返回"),
		"{history.go(-1)}");
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/orderInfoList";
	}

/*	@Override
	public DetachedCriteria initDetachedCriteria() {
		// TODO Auto-generated method stub
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
		dc.createCriteria("enumConstByFlagRefundState", dc.LEFT_JOIN);
		dc.add(Restrictions.eq("ssoUser.loginId", loginId));
		return dc;
	}
	*/
	

	/**
	 * 订单详情
	 * @author linjie
	 * @return
	 */
	public String orderDetail(){
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		PeBzzOrderInfo peBzzOrderInfo = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			this.setBean(this.getGeneralService().getById(this.getBean().getId()));
			DetachedCriteria dcInvoice = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
			dcInvoice.add(Restrictions.eq("peBzzOrderInfo", this.getBean()));
			List peBzzInvoiceInfoList = this.getGeneralService().getList(dcInvoice);
			if(peBzzInvoiceInfoList != null &&peBzzInvoiceInfoList.size() > 0){
				this.peBzzInvoiceInfo = (PeBzzInvoiceInfo) peBzzInvoiceInfoList.get(0);
				peBzzOrderInfo = this.peBzzInvoiceInfo.getPeBzzOrderInfo();
				this.ssoUser = this.peBzzInvoiceInfo.getPeBzzOrderInfo().getSsoUser();
			} else {
				String hql ="from PeBzzOrderInfo p where p.id='"+this.getBean().getId()+"'" ;
				peBzzOrderInfo  = (PeBzzOrderInfo)this.getGeneralService().getByHQL(hql).get(0);
				this.ssoUser = peBzzOrderInfo.getSsoUser();
				String abc = peBzzOrderInfo.getSsoUser().getAmount();
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
//		orderDetailElective();
			DetachedCriteria stuDc = DetachedCriteria.forClass(PeBzzStudent.class);
			stuDc.add(Restrictions.eq("ssoUser", this.ssoUser));
			try {
				List stuList = this.getGeneralService().getList(stuDc);
				if(stuList != null && stuList.size()>0){
					this.peBzzStudent = (PeBzzStudent)stuList.get(0);
				}
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		try {
			Page page = this.getGeneralService().getOrderDetailSub(peBzzOrderInfo,null,10000,0);
			if(page != null){
				this.electiveList = page.getItems(); 
			}
			/*EnumConst ec = this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0");
			price = Double.parseDouble(ec.getName());*/
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(this.peBzzStudent==null) {
			DetachedCriteria dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
			dc.add(Restrictions.eq("ssoUser", this.ssoUser));
			dc.createCriteria("peEnterprise", "peEnterprise");
			try {
				List list = this.getGeneralService().getList(dc);
				if(list.size()>0) {
					this.peEnterpriseManager = (PeEnterpriseManager)list.get(0);
				}
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		return "orderDetailCommon";
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
		
		try {
			StringBuffer  sqlTemp = new StringBuffer();
			
			sqlTemp.append("SELECT * ");
			sqlTemp.append("FROM   (select pboi.ID                as ID,                         ");
			sqlTemp.append("        pboi.SEQ               as SEQ,                        ");
			sqlTemp.append("        pboi.NAME              as cname,                       ");
			sqlTemp.append("        ec_payment_state.NAME  as PaymentState,              ");
			sqlTemp.append("        pboi.PAYMENT_DATE     as paymentDate,                  ");
			sqlTemp.append("        ec_payment_type.NAME   as PaymentType,               ");
			sqlTemp.append("        ec_payment_method.NAME as PaymentMethod,             ");
			sqlTemp.append("        pboi.AMOUNT            as AMOUNT,                     ");
			sqlTemp.append("        ec_prder_type.NAME     as OrderState,                 ");
			sqlTemp.append("        ec_order_type.NAME     as OrderType,                 ");
			sqlTemp.append("        ec_refund_state.NAME   as RefundState,               ");
			sqlTemp.append("        case  when ec_payment_state.code = '1' then      '已支付'   when ec_payment_state.code = '2' then     '已支付'     when 1 <> 1 then 'a'      else      '未支付' end as combobox_ZhiFuState                                 ");
			sqlTemp.append("   from PE_BZZ_ORDER_INFO pboi                                ");
			sqlTemp.append("  inner join ENUM_CONST ec_payment_state                      ");
			sqlTemp.append("     on pboi.FLAG_PAYMENT_STATE = ec_payment_state.ID         ");
			sqlTemp.append("  inner join ENUM_CONST ec_payment_type                       ");
			sqlTemp.append("     on pboi.FLAG_PAYMENT_TYPE = ec_payment_type.ID           ");
			sqlTemp.append("  inner join ENUM_CONST ec_prder_type                         ");
			sqlTemp.append("     on pboi.FLAG_ORDER_STATE = ec_prder_type.ID              ");
			sqlTemp.append("  inner join ENUM_CONST ec_payment_method                     ");
			sqlTemp.append("     on pboi.FLAG_PAYMENT_METHOD = ec_payment_method.ID       ");
			sqlTemp.append("  inner join ENUM_CONST ec_order_type                         ");
			sqlTemp.append("     on pboi.FLAG_ORDER_TYPE = ec_order_type.ID               ");
			sqlTemp.append("   left outer join ENUM_CONST ec_refund_state                 ");
			sqlTemp.append("     on pboi.FLAG_REFUND_STATE = ec_refund_state.ID           ");
			sqlTemp.append("   left outer join ENUM_CONST ec_zhifu_state                 ");
			sqlTemp.append("     on pboi.FLAG_ZHIFU_STATE = ec_zhifu_state.ID           ");
			sqlTemp.append("  inner join SSO_USER ssouser                                 ");
			sqlTemp.append("     on pboi.CREATE_USER = ssouser.ID                         ");
			sqlTemp.append("   left outer join PE_BZZ_BATCH pbb                           ");
			sqlTemp.append("     on pboi.FK_BATCH_ID = pbb.ID                             ");
			sqlTemp.append("   left outer join PE_ELECTIVE_COURSE_PERIOD pecp             ");
			sqlTemp.append("     on pboi.FK_COURSE_PERIOD_ID = pecp.ID                    ");
			sqlTemp.append("   left outer join PE_SIGNUP_ORDER pso                        ");
			sqlTemp.append("     on pboi.FK_SIGNUP_ORDER_ID = pso.ID                      ");
			sqlTemp.append("  where ssouser.LOGIN_ID = '"+loginId+"' ");
			sqlTemp.append(" order by pboi.ID desc        )                                ");
			sqlTemp.append("WHERE  1 = 1 ");
			Map params = this.getParamsMap();
			Iterator iterator = params.entrySet().iterator();
			do {
				if (!iterator.hasNext())
					break;
				java.util.Map.Entry entry = (java.util.Map.Entry) iterator
						.next();
				String name = entry.getKey().toString();
				String value = ((String[]) entry.getValue())[0].toString();
				if (!name.startsWith("search__"))
					continue;
				if ("".equals(value))
					continue;
				if (name.indexOf(".") > 1
						&& name.indexOf(".") != name.lastIndexOf(".")) {
					name = name.substring(name.lastIndexOf(".", name
							.lastIndexOf(".") - 1) + 1);
				} else {
					name = name.substring(8);
				}

				if (name.equals("enumConstByFlagPaymentState.name")) {
					name = "PaymentState";
				}
				if (name.equals("enumConstByFlagPaymentType.name")) {
					name = "PaymentType";
				}
				if (name.equals("enumConstByFlagPaymentMethod.name")) {
					name = "PaymentMethod";
				}
				if (name.equals("enumConstByFlagOrderState.name")) {
					name = "OrderState";
				}
				if (name.equals("enumConstByFlagOrderType.name")) {
					name = "OrderType";
				}
				if (name.equals("enumConstByFlagRefundState.name")) {
					name = "RefundState";
				}
				
				sqlTemp.append(" and " + name + " like '%" + value + "%'");
				

			} while (true);
			page = this.getGeneralService().getByPageSQL(sqlTemp.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}

		return page;
	}

	public PeBzzInvoiceInfo getPeBzzInvoiceInfo() {
		return peBzzInvoiceInfo;
	}

	public void setPeBzzInvoiceInfo(PeBzzInvoiceInfo peBzzInvoiceInfo) {
		this.peBzzInvoiceInfo = peBzzInvoiceInfo;
	}

	public PeBzzStudent getPeBzzStudent() {
		return peBzzStudent;
	}

	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
	}

	public SsoUser getSsoUser() {
		return ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	public PeEnterpriseManager getPeEnterpriseManager() {
		return peEnterpriseManager;
	}

	public void setPeEnterpriseManager(PeEnterpriseManager peEnterpriseManager) {
		this.peEnterpriseManager = peEnterpriseManager;
	}

	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeBzzOrderInfo.class;
	}
	
	public PeBzzOrderInfo getBean() {
		return super.superGetBean();
	}
	
	public void setBean(PeBzzOrderInfo instance) {
		super.superSetBean(instance);
	}
	
	
	
	
}
