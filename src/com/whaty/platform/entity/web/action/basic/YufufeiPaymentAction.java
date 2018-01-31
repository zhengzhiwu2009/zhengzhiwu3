package com.whaty.platform.entity.web.action.basic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class YufufeiPaymentAction extends MyBaseAction<PeBzzOrderInfo> {

	/**
	 * 初始化列表
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		this.getGridConfig().setTitle(this.getText("预付费财务管理"));
		this.getGridConfig().setCapability(false, false, false);

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("订单号"), "seq", true, false,
				true, "");
		this.getGridConfig().addColumn(this.getText("支付名称"), "payer", true,
				false, true, "");
		this.getGridConfig().addColumn(this.getText("支付金额"), "amount", true,
				false, true, "");

		this.getGridConfig().addColumn(this.getText("订单生成时间"), "createDate",
				false, false, true, "");
		this.getGridConfig().addColumn(this.getText("到账时间"), "paymentDate",
				false, false, true, "");

		this.getGridConfig().addColumn(this.getText("订单类型"),
				"enumConstByFlagOrderType.name", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("到账情况"),
				"enumConstByFlagPaymentState.name", true, false, true, "");

		this.getGridConfig().addColumn(this.getText("支付账号"), "ssoUser.loginId",
				true, false, true, "");
		this.getGridConfig().addColumn(this.getText("订单备忘录"), "cname", true,
				false, true, "");

		this.getGridConfig().addColumn(this.getText("生成时间起始"),
				"createStartDate", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("生成时间结束"), "createEndDate",
				true, false, false, "");
		this.getGridConfig().addColumn(this.getText("到账时间起始"),
				"paymentStartDate", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("到账时间结束"),
				"paymentEndDate", true, false, false, "");
		// this.getGridConfig().addMenuFunction(this.getText("到账确认"),"creditState.true");
		// this.getGridConfig().addMenuFunction(this.getText("取消到账"),"creditState.false");
		this
				.getGridConfig()
				.addRenderScript(
						this.getText("订单详情"),
						"{if( record.data['combobox_orderType'] != '购买学时订单' ) {"
								+ "   return '<a href=\"/entity/basic/paymentManage_orderDetail.action?bean.id='+record.data['id']+'\">详情</a>'"
								+ "} else {" + "   return '--'" + "}}", "id");
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/yufufeiPayment";
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

	/**
	 * 框架方法：或者查询列表所需数据
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public DetachedCriteria initDetachedCriteria() {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.createCriteria("enumConstByFlagPaymentType",
				"enumConstByFlagPaymentType", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagPaymentState",
				"enumConstByFlagPaymentState", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagPaymentMethod",
				"enumConstByFlagPaymentMethod", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagOrderState",
				"enumConstByFlagOrderState", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagOrderType",
				"enumConstByFlagOrderType", DetachedCriteria.LEFT_JOIN);
		// Lee start 原版
		// dc.createCriteria("ssoUser");
		// Lee end
		// Lee start 新版
		dc.createCriteria("ssoUser", "ssoUser", DetachedCriteria.LEFT_JOIN);
		// Lee end
		dc.add(Restrictions.eq("enumConstByFlagPaymentMethod.code", "1"));
		try {
			// 处理时间的查询参数
			ActionContext context = ActionContext.getContext();
			if (context.getParameters() != null) {
				Map params = this.getParamsMap();
				if (params != null) {
					if (params.get("search__createStartDate") != null) {
						String[] startDate = (String[]) params
								.get("search__createStartDate");
						String tempDate = startDate[0];
						if (startDate.length == 1
								&& !StringUtils.defaultString(startDate[0])
										.equals("")) {
							params.remove("search__createStartDate");
							context.setParameters(params);
							SimpleDateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							Date sDate = format.parse(tempDate + " 00:00:00 ");
							// Date sDate = format.parse(tempDate);
							dc.add(Restrictions.ge("createDate", sDate));
						}
					}
					if (params.get("search__createEndDate") != null) {
						String[] startDate = (String[]) params
								.get("search__createEndDate");
						String tempDate = startDate[0];
						if (startDate.length == 1
								&& !StringUtils.defaultString(startDate[0])
										.equals("")) {
							params.remove("search__createEndDate");
							context.setParameters(params);
							SimpleDateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							Date sDate = format.parse(tempDate + " 23:59:59 ");
							// Date sDate = format.parse(tempDate);
							dc.add(Restrictions.le("createDate", sDate));
						}
					}
					if (params.get("search__paymentStartDate") != null) {
						String[] startDate = (String[]) params
								.get("search__paymentStartDate");
						String tempDate = startDate[0];
						if (startDate.length == 1
								&& !StringUtils.defaultString(startDate[0])
										.equals("")) {
							params.remove("search__paymentStartDate");
							context.setParameters(params);
							SimpleDateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							Date sDate = format.parse(tempDate + " 00:00:00 ");
							// Date sDate = format.parse(tempDate);
							dc.add(Restrictions.ge("paymentDate", sDate));
						}
					}
					if (params.get("search__paymentEndDate") != null) {
						String[] startDate = (String[]) params
								.get("search__paymentEndDate");
						String tempDate = startDate[0];
						if (startDate.length == 1
								&& !StringUtils.defaultString(startDate[0])
										.equals("")) {
							params.remove("search__paymentEndDate");
							context.setParameters(params);
							SimpleDateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							Date sDate = format.parse(tempDate + " 23:59:59 ");
							// Date sDate = format.parse(tempDate);
							dc.add(Restrictions.le("paymentDate", sDate));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dc;
	}

	// public Map updateColumn() {
	// Map map = new HashMap();
	// String msg = "";
	// String action = this.getColumn();
	// if (this.getIds() != null && this.getIds().length() > 0) {
	// String[] ids = this.getIds().split(",");
	// List idList = new ArrayList();
	// for (int i = 0; i < ids.length; i++) {
	// idList.add(ids[i]);
	// }
	// DetachedCriteria dc = DetachedCriteria
	// .forClass(PeBzzOrderInfo.class);
	// dc.createCriteria("enumConstByFlagOrderType");
	// dc.createCriteria("enumConstByFlagPaymentType");
	// dc.createCriteria("enumConstByFlagPaymentMethod");
	// dc.createCriteria("ssoUser", "ssoUser");
	// dc.add(Restrictions.in("id", ids));
	// try {
	// List<PeBzzOrderInfo> list = this.getGeneralService().getList(dc);
	// if (action.equals("creditState.true")) {
	// try {
	// checkBeforeUpdateColumn(idList);
	// } catch (EntityException e1) {
	// map.put("success", "false");
	// map.put("info", e1.getMessage());
	// return map;
	// }
	// for (PeBzzOrderInfo order : list) {
	// this.getGeneralService().updatePeBzzOrderInfo(order,"confirm",null);
	// }
	// msg = "到账确认";
	// } else if (action.equals("creditState.false")) {
	// for (PeBzzOrderInfo order : list) {
	// this.getGeneralService().updatePeBzzOrderInfo(order,"cancel",null);
	// }
	// msg = "取消到账";
	// }
	// map.put("success", "true");
	// map.put("info", ids.length+"条记录"+msg+"操作成功");
	// } catch (EntityException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// map.clear();
	// map.put("success", "false");
	// map.put("info", "操作失败");
	// return map;
	// }
	// } else {
	// map.clear();
	// map.put("success", "false");
	// map.put("info", "至少一条数据被选择");
	// return map;
	// }
	//
	// return map;
	// }
	// @Override
	// public void checkBeforeUpdateColumn(List idList) throws EntityException {
	// DetachedCriteria dc = DetachedCriteria
	// .forClass(PeBzzOrderInfo.class);
	// dc.createCriteria("enumConstByFlagOrderType");
	// dc.createCriteria("enumConstByFlagPaymentType");
	// dc.createCriteria("enumConstByFlagPaymentMethod");
	// dc.createCriteria("ssoUser", "ssoUser");
	// dc.add(Restrictions.in("id", idList));
	// try{
	// List<PeBzzOrderInfo> list = this.getGeneralService().getList(dc);
	// for (PeBzzOrderInfo order : list) {
	// if(order.getEnumConstByFlagPaymentState().getCode()!=null&&"1".equals(order.getEnumConstByFlagPaymentState().getCode())){
	// throw new EntityException("所选订单存在已到账的的订单，不能重复到账确认！");
	// }else
	// if(order.getEnumConstByFlagPaymentState().getCode()!=null&&"2".equals(order.getEnumConstByFlagPaymentState().getCode())){
	// throw new EntityException("所选订单存在已退费的的订单，不能进行到账确认操作！");
	// } else if(order.getEnumConstByFlagPaymentMethod().getCode().equals("2")
	// || order.getEnumConstByFlagPaymentMethod().getCode().equals("3")) {
	// if(order.getNum()==null || order.getNum().equals("")) {
	// throw new EntityException("所选订单为电汇或支票支付订单，尚未填写单据号码，不能进行到账确认操作！");
	// }
	// }
	//				
	// }
	// }catch (EntityException e) {
	// throw e;
	// }
	// }
}
