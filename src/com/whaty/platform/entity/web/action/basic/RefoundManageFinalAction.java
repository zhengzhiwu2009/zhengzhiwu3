package com.whaty.platform.entity.web.action.basic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeBzzRefundInfo;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveHistory;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.basic.PeBzzFinancialService;
import com.whaty.platform.entity.service.basic.RefoundManageService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.training.basic.StudyProgress;
import com.whaty.platform.util.JsonUtil;

public class RefoundManageFinalAction extends MyBaseAction<PeBzzRefundInfo> {
	private EnumConstService enumConstService;
	private PeBzzFinancialService peBzzFinancialService;
	private List<PrBzzTchStuElective> electiveList;
	private RefoundManageService refoundManageService;
	/* 第三方支付退费相关参数 */
	private String merorderid;
	private String amountsum;
	private String cause;
	private String appuser;
	private String refuseReason;

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("退费终审"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("订单号"), "seq", true);
		this.getGridConfig().addColumn(this.getText("申请时间"), "applyDate", false);
		this.getGridConfig().addColumn(this.getText("支付方名称"), "payer", true);
		this.getGridConfig().addColumn(this.getText("支付类型"), "peBzzOrderInfo.enumConstByFlagPaymentType.name");
		// this.getGridConfig().addColumn(this.getText("支付方式"),
		// "peBzzOrderInfo.enumConstByFlagPaymentMethod.name");
		ColumnConfig c_name1 = new ColumnConfig(this.getText("支付方式"), "peBzzOrderInfo.enumConstByFlagPaymentMethod.name");
		c_name1
				.setComboSQL("select id, e.name as name from enum_const e where e.namespace = 'FlagPaymentMethod' and id <> '40288a7b394207de01394212e47f0005x'UNION SELECT '10', '在线支付-个人网银'  FROM DUAL UNION SELECT '14', '在线支付-企业网银' FROM DUAL  ");
		this.getGridConfig().addColumn(c_name1);
		this.getGridConfig().addColumn(this.getText("订单类型"), "peBzzOrderInfo.enumConstByFlagOrderType.name");
		this.getGridConfig().addColumn(this.getText("支付金额"), "amount", true);
		this.getGridConfig().addColumn(this.getText("到账时间"), "paymentDate", false);
		this.getGridConfig().addColumn(this.getText("状态"), "enumConstByFlagRefundState.name", true);
		this.getGridConfig().addColumn(this.getText("申请时间起始"), "applyStartDate", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("申请时间结束"), "applyEndDate", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("到账时间起始"), "paymentStartDate", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("到账时间结束"), "paymentEndDate", true, false, false, "");
		this.getGridConfig().addRenderFunction("查看详情",
				"<a href='/entity/basic/refoundManageFinal_viewDetail.action?bean.id=${value}' >查看</a>", "id");
		if (capabilitySet.contains(this.servletPath + "_finalAudit.action")) {// 按钮权限判断
			this.getGridConfig().addMenuFunction(this.getText("终审确认"), "/entity/basic/refoundManageFinal_toRefund.action", false, true);
			// this.getGridConfig().addMenuFunction(this.getText("拒绝"),"RefundState.false");
			this.getGridConfig().addMenuScript(
					this.getText("终审拒绝"),
					"{" + "var m=grid.getSelections();" + "    var ids='';" + "	if(m.length>0){"
							+ "		for(var i=0,len=m.length;i<len;i++) {" + "			var ss=m[i].get('id');" + "			if(i==0){" + "				ids=ids+ss;"
							+ "			}else {" + "				ids=ids+','+ss;" + "			}" + "		}" + "    }else{"
							+ "     Ext.MessageBox.alert('错误','至少选择一条数据');" + "     return;" + "    }" + ""
							+ "   var reasonPanel=new Ext.form.TextArea({" + "    fieldLabel: '拒绝退费原因'," + "    valueField:'id',"
							+ "    displayField:'name'," + "    width:350," + "    heigth:500," + "    typeAhead:true,"
							+ "    name:'refuseReason',id:'refuseReason'" + "    });"
							+ "var ids=new Ext.form.Hidden({name:'ids',value:ids});" +

							"   var formPanel=new Ext.form.FormPanel({" + "    frame:true," + "    labelWidth:100,"
							+ "    defaultType:'textfield'," + "    autoScroll:true," + "    items:[reasonPanel,ids]" + "   });" + ""
							+ "   var addModelWin=new Ext.Window({" + "    title:'退费拒绝原因'," + "    width:500," + "    height:250,"
							+ "    minWidth:300," + "    minHeight:250," + "    layout:'fit'," + "    plain:true,"
							+ "    bodyStyle:'padding:5px;'," + "    items:formPanel," + "    buttonAlign:'center'," + "    buttons:[{"
							+ "     text:'拒绝'," + "     handler:function(){" + "      if(formPanel.form.isValid()){"
							+ "       formPanel.form.submit({" + "        url:'/entity/basic/refoundManageFinal_refuseRefund.action',"
							+ "        waitMsg:'处理中，请稍候...'," + "        success:function(form,action){"
							+ "         var responseArray=action.result;" + "         if(responseArray.success=='true'){"
							+ "          Ext.MessageBox.alert('提示',responseArray.info);" + "          store.load({"
							+ "           params:{start:g_start,limit:g_limit}" + "          });" + "          addModelWin.close();"
							+ "         }else{" + "          Ext.MessageBox.alert('错误',responseArray.info);"
							+ "          addModelWin.close();" + "          }" + "        }" + "       });" + "      }" + "     }"
							+ "    }," + "    {text:'取消'," + "     handler:function(){" + "      addModelWin.close();" + "     }}]"
							+ "   });" + "   addModelWin.show();" + "}");
		}
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeBzzRefundInfo.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/refoundManageFinal";
	}

	public PeBzzRefundInfo getBean() {
		return super.superGetBean();
	}

	public void setBean(PeBzzRefundInfo instance) {
		super.superSetBean(instance);
	}

	public Page list() {
		Page page = null;
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(" SELECT * FROM (");
			sb.append(" SELECT PBRI.ID, ");
			sb.append("        PBOI.SEQ, ");
			sb.append("        PBRI.APPLY_DATE   APPLYDATE, ");
			sb.append("        PBOI.PAYER, ");
			sb.append("        EC1.NAME EC1NAME, ");
			sb.append("        EC2.NAME  || '' || CASE WHEN pboi.payment_method ='10' THEN '-个人网银' WHEN pboi.payment_method ='14' THEN '-企业网银' ELSE '' END  as EC2NAME, ");
			sb.append("        EC3.NAME EC3NAME, ");
			sb.append("        PBOI.AMOUNT, ");
			sb.append("        PBOI.PAYMENT_DATE PAYMENTDATE, ");
			sb.append("        EC4.NAME EC4NAME, ");
			sb.append("        '' APPLYSTARTDATE, ");
			sb.append("        '' APPLYENDDATE, ");
			sb.append("        '' PAYMENTSTARTDATE, ");
			sb.append("        '' PAYMENTENDDATE ");
			sb.append("   FROM PE_BZZ_REFUND_INFO PBRI ");
			sb.append("  INNER JOIN PE_BZZ_ORDER_INFO PBOI ");
			sb.append("     ON PBRI.FK_ORDER_ID = PBOI.ID ");
			sb.append("  INNER JOIN ENUM_CONST EC1 ");
			sb.append("     ON PBOI.FLAG_PAYMENT_TYPE = EC1.ID ");
			sb.append("  INNER JOIN ENUM_CONST EC2 ");
			sb.append("     ON PBOI.FLAG_PAYMENT_METHOD = EC2.ID ");
			sb.append("  INNER JOIN ENUM_CONST EC3 ");
			sb.append("     ON PBOI.FLAG_ORDER_TYPE = EC3.ID ");
			sb.append("   LEFT OUTER JOIN SSO_USER SU ");
			sb.append("     ON PBOI.CREATE_USER = SU.ID ");
			sb.append("   LEFT OUTER JOIN PE_BZZ_BATCH PBB ");
			sb.append("     ON PBOI.FK_BATCH_ID = PBB.ID ");
			sb.append("   LEFT OUTER JOIN PE_ELECTIVE_COURSE_PERIOD PECP ");
			sb.append("     ON PBOI.FK_COURSE_PERIOD_ID = PECP.ID ");
			sb.append("   LEFT OUTER JOIN PE_SIGNUP_ORDER PSO ");
			sb.append("     ON PBOI.FK_SIGNUP_ORDER_ID = PSO.ID ");
			sb.append("  INNER JOIN ENUM_CONST EC4 ");
			sb.append("     ON PBRI.FLAG_REFUND_STATE = EC4.ID ");
			sb.append("     AND EC4.CODE IN ('1','2','3','4') ");
			sb.append(" ) WHERE 1 = 1 ");

			// 处理时间的查询参数
			ActionContext context = ActionContext.getContext();
			if (context.getParameters() != null) {
				Map params = this.getParamsMap();
				if (params != null) {
					if (params.get("search__applyStartDate") != null) {
						String[] startDate = (String[]) params.get("search__applyStartDate");
						String tempDate = startDate[0];
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							params.remove("search__applyStartDate");
							sb.append(" AND applyDate >= TO_DATE('" + tempDate + " 00:00:00','yyyy-MM-dd hh24:mi:ss')");
						}
					}
					if (params.get("search__applyEndDate") != null) {
						String[] startDate = (String[]) params.get("search__applyEndDate");
						String tempDate = startDate[0];
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							params.remove("search__applyEndDate");
							sb.append(" AND applyDate <= TO_DATE('" + tempDate + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
						}
					}
					if (params.get("search__paymentStartDate") != null) {
						String[] startDate = (String[]) params.get("search__paymentStartDate");
						String tempDate = startDate[0];
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							params.remove("search__paymentStartDate");
							sb.append(" AND paymentDate >= TO_DATE('" + tempDate + " 00:00:00','yyyy-MM-dd hh24:mi:ss')");
						}
					}
					if (params.get("search__paymentEndDate") != null) {
						String[] startDate = (String[]) params.get("search__paymentEndDate");
						String tempDate = startDate[0];
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							params.remove("search__paymentEndDate");
							sb.append(" AND paymentDate <= TO_DATE('" + tempDate + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
						}
					}
				}
			}

			/* 拼接查询条件 */
			Map params = this.getParamsMap();
			Iterator iterator = params.entrySet().iterator();
			do {
				if (!iterator.hasNext())
					break;
				java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
				String name = entry.getKey().toString();
				String value = ((String[]) entry.getValue())[0].toString();
				if (!name.startsWith("search__"))
					continue;
				if ("".equals(value))
					continue;
				if (name.indexOf(".") > 1 && name.indexOf(".") != name.lastIndexOf(".")) {
					name = name.substring(name.lastIndexOf(".", name.lastIndexOf(".") - 1) + 1);
				} else {
					name = name.substring(8);
				}
				/* 支付类型 */
				if (name.equals("enumConstByFlagPaymentType.name")) {
					name = "EC1NAME";
				}
				/* 支付方式 */
				if (name.equals("enumConstByFlagPaymentMethod.name")) {
					name = "EC2NAME";
				}
				/* 订单类型 */
				if (name.equals("enumConstByFlagOrderType.name")) {
					name = "EC3NAME";
				}
				/* 状态 */
				if (name.equals("enumConstByFlagRefundState.name")) {
					name = "EC4NAME";
				}
				if ("amount".equalsIgnoreCase(name)) {
					sb.append(" AND AMOUNT = TO_NUMBER('" + value + "') ");
				} else {
					if ("SEQPAYER".indexOf(name.toUpperCase()) >= 0 ||"EC2NAME".equals(name)) {
						sb.append(" and UPPER(" + name + ") LIKE UPPER('%" + value + "%')");
					} else {
						sb.append(" and UPPER(" + name + ") = UPPER('" + value + "')");
					}
				}
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				/* 支付类型 */
				if (temp.equals("enumConstByFlagPaymentType.name")) {
					temp = "EC1NAME";
				}
				/* 支付方式 */
				if (temp.equals("enumConstByFlagPaymentMethod.name")) {
					temp = "EC2NAME";
				}
				/* 订单类型 */
				if (temp.equals("enumConstByFlagOrderType.name")) {
					temp = "EC3NAME";
				}
				/* 状态 */
				if (temp.equals("enumConstByFlagRefundState.name")) {
					temp = "EC4NAME";
				}
				if (temp.equals("id")) {
					temp = "applyDate";
				}
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					if ("amount".equalsIgnoreCase(temp)) {
						sb.append(" order by to_number(" + temp + ") desc ");
					} else {
						sb.append(" order by " + temp + " desc ");
					}
				} else {
					if ("amount".equalsIgnoreCase(temp)) {
						sb.append(" order by to_number(" + temp + ") asc ");
					} else {
						sb.append(" order by " + temp + " asc ");
					}
				}
			} else {
				sb.append(" order by applyDate desc");
			}
			page = this.getGeneralService().getByPageSQL(sb.toString(), Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	/*
	 * @Override public DetachedCriteria initDetachedCriteria() { // EnumConst
	 * enumConstByFlagRefundState = //
	 * this.getEnumConstService().getByNamespaceCode("FlagRefundState", //
	 * "4");//初审通过状态 String[] types = { "1", "2", "3", "4" };//
	 * 除了待初审退费，其他状态终审均要显示 DetachedCriteria dc =
	 * DetachedCriteria.forClass(PeBzzRefundInfo.class);
	 * dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
	 * dc.createAlias("peBzzOrderInfo.enumConstByFlagPaymentType",
	 * "enumConstByFlagPaymentType");
	 * dc.createAlias("peBzzOrderInfo.enumConstByFlagPaymentMethod",
	 * "enumConstByFlagPaymentMethod");
	 * dc.createAlias("peBzzOrderInfo.enumConstByFlagOrderType",
	 * "enumConstByFlagOrderType");
	 * dc.createCriteria("enumConstByFlagRefundState",
	 * "enumConstByFlagRefundState");
	 * dc.add(Restrictions.in("enumConstByFlagRefundState.code", types)); try { //
	 * 处理时间的查询参数 ActionContext context = ActionContext.getContext(); if
	 * (context.getParameters() != null) { Map params = this.getParamsMap(); if
	 * (params != null) { if (params.get("search__applyStartDate") != null) {
	 * String[] startDate = (String[]) params.get("search__applyStartDate");
	 * String tempDate = startDate[0]; if (startDate.length == 1 &&
	 * !StringUtils.defaultString(startDate[0]).equals("")) {
	 * params.remove("search__applyStartDate"); context.setParameters(params);
	 * SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 * Date sDate = format.parse(tempDate + " 00:00:00 "); // Date sDate =
	 * format.parse(tempDate); dc.add(Restrictions.ge("applyDate", sDate)); } }
	 * if (params.get("search__applyEndDate") != null) { String[] startDate =
	 * (String[]) params.get("search__applyEndDate"); String tempDate =
	 * startDate[0]; if (startDate.length == 1 &&
	 * !StringUtils.defaultString(startDate[0]).equals("")) {
	 * params.remove("search__applyEndDate"); context.setParameters(params);
	 * SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 * Date sDate = format.parse(tempDate + " 23:59:59 "); // Date sDate =
	 * format.parse(tempDate); dc.add(Restrictions.le("applyDate", sDate)); } }
	 * if (params.get("search__paymentStartDate") != null) { String[] startDate =
	 * (String[]) params.get("search__paymentStartDate"); String tempDate =
	 * startDate[0]; if (startDate.length == 1 &&
	 * !StringUtils.defaultString(startDate[0]).equals("")) {
	 * params.remove("search__paymentStartDate"); context.setParameters(params);
	 * SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 * Date sDate = format.parse(tempDate + " 00:00:00 "); // Date sDate =
	 * format.parse(tempDate);
	 * dc.add(Restrictions.ge("peBzzOrderInfo.paymentDate", sDate)); } } if
	 * (params.get("search__paymentEndDate") != null) { String[] startDate =
	 * (String[]) params.get("search__paymentEndDate"); String tempDate =
	 * startDate[0]; if (startDate.length == 1 &&
	 * !StringUtils.defaultString(startDate[0]).equals("")) {
	 * params.remove("search__paymentEndDate"); context.setParameters(params);
	 * SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 * Date sDate = format.parse(tempDate + " 23:59:59 "); // Date sDate =
	 * format.parse(tempDate);
	 * dc.add(Restrictions.le("peBzzOrderInfo.paymentDate", sDate)); } } } } }
	 * catch (Exception e) { e.printStackTrace(); } return dc; }
	 */
	@Override
	public Map updateColumn() {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		String action = this.getColumn();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = this.getIds().split(",");
			if (ids.length > 1) {
				map.clear();
				map.put("success", "false");
				map.put("info", "只能选择一条数据");
				return map;
			}
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzRefundInfo.class);
			dc.createCriteria("enumConstByFlagRefundState", "enumConstByFlagRefundState");
			dc.add(Restrictions.eq("id", ids[0]));
			List electiveIdList = null;
			SsoUser user = null;
			try {
				List<PeBzzRefundInfo> peBzzRefundInfolist = this.getGeneralService().getDetachList(dc);
				PeBzzRefundInfo peBzzRefundInfo = peBzzRefundInfolist.get(0);
				if (peBzzRefundInfo.getEnumConstByFlagRefundState().getCode().equals("")) {
					map.clear();
					map.put("success", "false");
					map.put("info", "以通过退费，不能拒绝");
					return map;
				}
				if (action.equals("RefundState.false")) {
					this.refoundManageService.refuseRefundApply(peBzzRefundInfo);
				}
				map.put("success", "true");
				map.put("info", "拒绝退费操作成功");
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				return map;
			}
		} else {
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
			return map;
		}

		return map;
	}

	public String toRefund() {
		Map map = new HashMap();
		boolean flag = true;
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = this.getIds().split(",");
			if (ids.length > 1) {
				map.clear();
				map.put("success", "false");
				map.put("info", "只能选择一条数据");
				this.setJsonString(JsonUtil.toJSONString(map));
				JsonUtil.setDateformat("yyyy-MM-dd");
				return this.json();
			}
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzRefundInfo.class);
			dc.createCriteria("enumConstByFlagRefundState", "enumConstByFlagRefundState");
			dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
			dc.createCriteria("peBzzOrderInfo.enumConstByFlagPaymentState", "enumConstByFlagPaymentState");
			dc.createCriteria("peBzzOrderInfo.enumConstByFlagOrderType", "enumConstByFlagOrderType");
			dc.createCriteria("peBzzOrderInfo.enumConstByFlagPaymentMethod", "enumConstByFlagPaymentMethod");
			dc.createCriteria("peBzzOrderInfo.ssoUser", "ssoUser");
			dc.add(Restrictions.in("id", ids));
			List electiveIdList = null;
			SsoUser user = null;
			try {
				List<PeBzzRefundInfo> peBzzRefundInfolist = this.getGeneralService().getDetachList(dc);
				PeBzzRefundInfo peBzzRefundInfo = peBzzRefundInfolist.get(0);
				String orderId = peBzzRefundInfo.getPeBzzOrderInfo().getId();
				// 增加该订单下是否有课程开始学习了，如果有，则不允许退费
				StringBuffer sql = new StringBuffer();
				sql.append("  select pboi.id \n																		");
				sql.append("  from pe_bzz_order_info pboi, pr_bzz_tch_stu_elective pbtse \n                           ");
				sql.append("  where pboi.id = pbtse.fk_order_id  and pboi.id = '" + orderId + "'  \n ");
				sql.append("  and exists ( select tcs.id from training_course_student tcs where tcs.id     \n         ");
				sql.append("  = pbtse.fk_training_id and tcs.learn_status != '" + StudyProgress.UNCOMPLETE + "' )        \n ");
				List checkList = this.getGeneralService().getBySQL(sql.toString());
				if (checkList.size() > 0) {
					map.put("success", "false");
					map.put("info", "该订单下的课程已开始了学习,不可以退费.");
					this.setJsonString(JsonUtil.toJSONString(map));
					return this.json();
				}
				if (peBzzRefundInfo.getEnumConstByFlagRefundState().getCode().equals("1")) {
					map.clear();
					map.put("success", "false");
					map.put("info", "退费已审核通过，不能再操作");
					this.setJsonString(JsonUtil.toJSONString(map));
					JsonUtil.setDateformat("yyyy-MM-dd");
					return this.json();
				}
				PeBzzOrderInfo peBzzOrderInfo = peBzzRefundInfo.getPeBzzOrderInfo();
				if (peBzzOrderInfo.getEnumConstByFlagPaymentState().getCode().equals("0")) {
					map.put("success", "false");
					map.put("info", "订单未到账不能退款");
					this.setJsonString(JsonUtil.toJSONString(map));
					return this.json();
				}
				if (peBzzOrderInfo.getEnumConstByFlagOrderType().getCode().equals("1")) {
					map.put("success", "false");
					map.put("info", "集体管理员购买的学时不可以退费");
					this.setJsonString(JsonUtil.toJSONString(map));
					return this.json();
				}
				Date date = peBzzOrderInfo.getPaymentDate();
				long yes = date.getTime();
				Date date2 = new Date();
				long now = date2.getTime();
				/*
				 * if(((now-yes)/1000/60)<10) { map.put("success", "false");
				 * map.put("info", "支付时间离现在未到10分钟，请距离支付10分钟后再确认。");
				 * this.setJsonString(JsonUtil.toJSONString(map)); return
				 * this.json(); }
				 */
				if (peBzzOrderInfo.getEnumConstByFlagPaymentMethod().getCode().equals("0")) { // 在线支付
					this.getGeneralService().initOnlineRefund(peBzzRefundInfo);
					UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
					this.merorderid = peBzzOrderInfo.getSeq();
					this.appuser = us.getLoginId();
					this.cause = peBzzRefundInfo.getReason();
					this.amountsum = peBzzOrderInfo.getAmount();
					// Lee 支付接口切换 快钱退费
					if (true) {
						return "to99BillRefund";
					} else {
						return "toOnlineRefund";
					}
				} else { // 电汇和支票支付退款
					flag = true;
					try {
						this.getGeneralService().executeRefund(peBzzRefundInfo);// 执行退费
					} catch (Exception e) {
						flag = false;
						e.printStackTrace();
					}
				}
				if (flag) {
					map.put("success", "true");
					map.put("info", "确认退费操作成功");
				} else {
					map.put("success", "false");
					map.put("info", "操作失败");
				}
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				this.setJsonString(JsonUtil.toJSONString(map));
				JsonUtil.setDateformat("yyyy-MM-dd");
				return this.json();
			}
		} else {
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
		}

		this.setJsonString(JsonUtil.toJSONString(map));
		JsonUtil.setDateformat("yyyy-MM-dd");
		return this.json();
	}

	public String viewDetail() {
		try {
			// 查看订单的状态
			this.setBean(this.getGeneralService().getById(this.getBean().getId()));
			PeBzzOrderInfo peBzzOrderInfo = this.getBean().getPeBzzOrderInfo();
			EnumConst refundState = peBzzOrderInfo.getEnumConstByFlagRefundState();
			if (refundState != null && (refundState.getCode().equals("0") || refundState.getCode().equals("2"))) {// 待审核和已拒绝
				DetachedCriteria electiveDc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
				electiveDc.add(Restrictions.eq("peBzzOrderInfo", this.getBean().getPeBzzOrderInfo()));
				electiveDc.createAlias("peBzzStudent", "peBzzStudent");
				this.electiveList = this.getGeneralService().getList(electiveDc);
			} else if (refundState != null && refundState.getCode().equals("1")) {// 已退费
				DetachedCriteria electiveDc = DetachedCriteria.forClass(PrBzzTchStuElectiveHistory.class);
				electiveDc.add(Restrictions.eq("peBzzOrderInfo", this.getBean().getPeBzzOrderInfo()));
				electiveDc.createAlias("peBzzStudent", "peBzzStudent");
				this.electiveList = this.getGeneralService().getList(electiveDc);
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "refoundDetail";
	}

	// /**
	// * 使用在线支付退费成功后处理
	// * @return
	// */
	// public String refundEndHandle() {
	// Map map = new HashMap();
	// DetachedCriteria dc = DetachedCriteria.forClass(PeBzzRefundInfo.class);
	// dc.createCriteria("enumConstByFlagRefundState",
	// "enumConstByFlagRefundState");
	// dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
	// dc.createCriteria("peBzzOrderInfo.enumConstByFlagPaymentMethod",
	// "enumConstByFlagPaymentMethod");
	// dc.createCriteria("peBzzOrderInfo.ssoUser", "ssoUser");
	// dc.add(Restrictions.eq("peBzzOrderInfo.seq", this.merorderid));
	// try {
	// EnumConst paymentState =
	// this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagPaymentState",
	// "2");
	// EnumConst refundState =
	// this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagRefundState",
	// "1");
	// PeBzzRefundInfo peBzzRefundInfo = (PeBzzRefundInfo)
	// this.getGeneralService().getList(dc).get(0);
	// peBzzRefundInfo.setEnumConstByFlagRefundState(refundState);
	// this.getGeneralService().backupRefundData(peBzzRefundInfo);
	// //暂时手动修改，后面需要去自动确认
	// PeBzzOrderInfo peBzzOrderInfo = peBzzRefundInfo.getPeBzzOrderInfo();
	// peBzzOrderInfo.setEnumConstByFlagRefundState(refundState);
	// peBzzOrderInfo.setEnumConstByFlagPaymentState(paymentState);
	// List<PeBzzOrderInfo> list = new ArrayList<PeBzzOrderInfo>();
	// list.add(peBzzOrderInfo);
	// this.getGeneralService().saveList(list);
	// map.put("success", "true");
	// map.put("info", "退款申请成功");
	// } catch (EntityException e) {
	// // TODO Auto-generated catch block
	// map.clear();
	// map.put("success", "false");
	// map.put("info", "退款后续处理失败处理失败");
	// }
	// this.setJsonString(JsonUtil.toJSONString(map));
	// return "json";
	// }
	public String refuseRefund() {
		Map map = new HashMap();
		String action = this.getColumn();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = this.getIds().split(",");
			if (ids.length > 1) {
				map.clear();
				map.put("success", "false");
				map.put("info", "只能选择一条数据");
				this.setJsonString(JsonUtil.toJSONString(map));
				return this.json();

			}
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzRefundInfo.class);
			dc.createCriteria("enumConstByFlagRefundState", "enumConstByFlagRefundState");
			dc.add(Restrictions.eq("id", ids[0]));
			List electiveIdList = null;
			SsoUser user = null;
			try {
				List<PeBzzRefundInfo> peBzzRefundInfolist = this.getGeneralService().getDetachList(dc);
				PeBzzRefundInfo peBzzRefundInfo = peBzzRefundInfolist.get(0);
				peBzzRefundInfo.setRefauseReason(refuseReason);
				if (peBzzRefundInfo.getEnumConstByFlagRefundState().getCode().equals("1")) {
					map.clear();
					map.put("success", "false");
					map.put("info", "已通过退费，不能拒绝");
					this.setJsonString(JsonUtil.toJSONString(map));
					return this.json();
				}

				this.refoundManageService.refuseRefundApply(peBzzRefundInfo);

				map.put("success", "true");
				map.put("info", "拒绝退费操作成功");
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				this.setJsonString(JsonUtil.toJSONString(map));
				return this.json();
			}
		} else {
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
			this.setJsonString(JsonUtil.toJSONString(map));
			return this.json();
		}

		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}

	/*
	 * public String toRefund() { Map map = new HashMap(); boolean flag = true;
	 * if (this.getIds() != null && this.getIds().length() > 0) { String[] ids =
	 * this.getIds().split(","); if(ids.length>1) { map.clear();
	 * map.put("success", "false"); map.put("info", "只能选择一条数据");
	 * this.setJsonString(JsonUtil.toJSONString(map));
	 * JsonUtil.setDateformat("yyyy-MM-dd"); return this.json(); }
	 * DetachedCriteria dc = DetachedCriteria.forClass(PeBzzRefundInfo.class);
	 * dc.createCriteria("enumConstByFlagRefundState",
	 * "enumConstByFlagRefundState"); dc.createCriteria("peBzzOrderInfo",
	 * "peBzzOrderInfo");
	 * dc.createCriteria("peBzzOrderInfo.enumConstByFlagPaymentState",
	 * "enumConstByFlagPaymentState");
	 * dc.createCriteria("peBzzOrderInfo.enumConstByFlagPaymentMethod",
	 * "enumConstByFlagPaymentMethod");
	 * dc.createCriteria("peBzzOrderInfo.ssoUser", "ssoUser");
	 * dc.add(Restrictions.in("id", ids)); List electiveIdList = null; SsoUser
	 * user = null; try { List<PeBzzRefundInfo> peBzzRefundInfolist =
	 * this.getGeneralService().getDetachList(dc); PeBzzRefundInfo
	 * peBzzRefundInfo = peBzzRefundInfolist.get(0);
	 * if(peBzzRefundInfo.getEnumConstByFlagRefundState().getCode().equals("1")) {
	 * map.clear(); map.put("success", "false"); map.put("info", "已审核通过");
	 * this.setJsonString(JsonUtil.toJSONString(map));
	 * JsonUtil.setDateformat("yyyy-MM-dd"); return this.json(); }
	 * PeBzzOrderInfo peBzzOrderInfo = peBzzRefundInfo.getPeBzzOrderInfo();
	 * if(peBzzOrderInfo.getEnumConstByFlagPaymentState().getCode().equals("0")) {
	 * map.put("success", "false"); map.put("info", "订单未到账不能退款");
	 * this.setJsonString(JsonUtil.toJSONString(map)); return this.json(); }
	 * 
	 * 
	 * //预付费支付
	 * if(peBzzOrderInfo.getEnumConstByFlagPaymentMethod().getCode().equals("1")) {
	 * //预付费支付 this.refoundManageService.deleteEleCourse(peBzzRefundInfo); }else
	 * if(peBzzOrderInfo.getEnumConstByFlagPaymentMethod().getCode().equals("0")) {
	 * //在线支付 UserSession us = (UserSession) ActionContext.getContext()
	 * .getSession().get(SsoConstant.SSO_USER_SESSION_KEY); this.merorderid =
	 * peBzzOrderInfo.getSeq(); this.appuser = us.getLoginId(); this.cause =
	 * peBzzRefundInfo.getReason(); this.amountsum = peBzzOrderInfo.getAmount();
	 * 
	 * return "toOnlineRefund"; } else { //电汇和支票支付退款 flag =
	 * this.refoundManageService.saveOnlineRefund(peBzzRefundInfo); } if(flag) {
	 * map.put("success", "true"); map.put("info", "确认退费操作成功"); } else {
	 * map.put("success", "false"); map.put("info", "操作失败"); } } catch
	 * (EntityException e) { // TODO Auto-generated catch block map.clear();
	 * map.put("success", "false"); map.put("info", "操作失败");
	 * this.setJsonString(JsonUtil.toJSONString(map));
	 * JsonUtil.setDateformat("yyyy-MM-dd"); return this.json(); } } else {
	 * map.clear(); map.put("success", "false"); map.put("info", "至少一条数据被选择"); }
	 * 
	 * this.setJsonString(JsonUtil.toJSONString(map));
	 * JsonUtil.setDateformat("yyyy-MM-dd"); return this.json(); }
	 */
	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	public PeBzzFinancialService getPeBzzFinancialService() {
		return peBzzFinancialService;
	}

	public void setPeBzzFinancialService(PeBzzFinancialService peBzzFinancialService) {
		this.peBzzFinancialService = peBzzFinancialService;
	}

	public List<PrBzzTchStuElective> getElectiveList() {
		return electiveList;
	}

	public void setElectiveList(List<PrBzzTchStuElective> electiveList) {
		this.electiveList = electiveList;
	}

	public String getMerorderid() {
		return merorderid;
	}

	public void setMerorderid(String merorderid) {
		this.merorderid = merorderid;
	}

	public String getAmountsum() {
		return amountsum;
	}

	public void setAmountsum(String amountsum) {
		this.amountsum = amountsum;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getAppuser() {
		return appuser;
	}

	public void setAppuser(String appuser) {
		this.appuser = appuser;
	}

	public RefoundManageService getRefoundManageService() {
		return refoundManageService;
	}

	public void setRefoundManageService(RefoundManageService refoundManageService) {
		this.refoundManageService = refoundManageService;
	}

}
