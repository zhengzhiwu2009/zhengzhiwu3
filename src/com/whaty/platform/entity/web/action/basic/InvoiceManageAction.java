package com.whaty.platform.entity.web.action.basic;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.entity.web.util.Base64Util;
import com.whaty.platform.entity.web.util.WebServiceInvoker;
import com.whaty.platform.entity.web.util.XMLFileUtil;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;

public class InvoiceManageAction extends MyBaseAction<PeBzzInvoiceInfo> {

	private String id; // 发票id号
	private PeBzzInvoiceInfo peBzzInvoiceInfo; // 填写订单人员详细信息
	protected EnumConstService enumConstService;
	private String invoiceCode ;
	private String invoiceFpdm;
	public String getInvoiceFpdm() {
		return invoiceFpdm;
	}

	public void setInvoiceFpdm(String invoiceFpdm) {
		this.invoiceFpdm = invoiceFpdm;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public PeBzzInvoiceInfo getPeBzzInvoiceInfo() {
		return peBzzInvoiceInfo;
	}

	public void setPeBzzInvoiceInfo(PeBzzInvoiceInfo peBzzInvoiceInfo) {
		this.peBzzInvoiceInfo = peBzzInvoiceInfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

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
		Set capabilitySet = us.getUserPriority().keySet();

		this.getGridConfig().setCapability(false, false, false);

		this.getGridConfig().setTitle(this.getText("发票管理"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("订单号"),
				"peBzzOrderInfo.seq", true);
		this.getGridConfig().addColumn(this.getText("到账状态"),
				"peBzzOrderInfo.enumConstByFlagPaymentState.name", true);
		this.getGridConfig().addColumn(this.getText("对账状态"),
				"peBzzOrderInfo.enumConstByFlagCheckState.name", true);
		this.getGridConfig().addColumn(this.getText("发票类型"),
				"enumConstByInvoiceType.name", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("发票抬头"), "title", false);
		// Lee 2014年3月5日 将false改为true 增加收件人搜索条件
		this.getGridConfig().addColumn(this.getText("收件人"), "addressee", true);
		this.getGridConfig().addColumn(this.getText("收件地址"), "address", true);
		this.getGridConfig().addColumn(this.getText("邮政编码"), "zipCode", true);
		this.getGridConfig().addColumn(this.getText("收件人电话"), "phone", true);
		this.getGridConfig().addColumn(this.getText("支付金额"),
				"peBzzOrderInfo.amount", true);
		//this.getGridConfig().addColumn(this.getText("支付方式"),
		//		"peBzzOrderInfo.enumConstByFlagPaymentMethod.name", true);
		ColumnConfig c_name1 = new ColumnConfig(this.getText("支付方式"), "peBzzOrderInfo.enumConstByFlagPaymentMethod.name");
		c_name1.setComboSQL("select id, e.name as name from enum_const e where e.namespace = 'FlagPaymentMethod' and id <> '40288a7b394207de01394212e47f0005x'");
		this.getGridConfig().addColumn(c_name1);
		this.getGridConfig().addColumn(this.getText("发票状态"),
				"enumConstByFlagFpOpenState.name", true);
		this.getGridConfig().addColumn(this.getText("发票号码"), "num", true);
		this.getGridConfig().addColumn(this.getText("支付时间"),
				"peBzzOrderInfo.paymentDate", false, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("开具时间"), "openDate", false);
		this.getGridConfig().addColumn(this.getText("支付时间起始"),
				"payStartDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("支付时间结束"),
				"payEndDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("开具时间起始"),
				"openStartDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("开具时间结束"),
				"openEndDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("纳税人识别号"), "gmfnsrsbh", false);
		this.getGridConfig().addColumn(this.getText("购买方地址"), "gmfdz", false);
		this.getGridConfig().addColumn(this.getText("购买方电话"), "gmfdh", false);
		this.getGridConfig().addColumn(this.getText("购买方开户行"), "gmfkhyh", false);
		this.getGridConfig().addColumn(this.getText("购买方账号"), "gmfyhzh", false);
		
		/*this.getGridConfig().addColumn(this.getText("打印状态"),
				"enumConstByflagPrintStatus.name", true, false, true,
				"TextField", true, 100, true);*/
		/*this.getGridConfig().addColumn(this.getText("邮寄方式"),
				"enumConstByFlagPostType.name", true, false, true,
				"TextField", true, 100, true);*/
		// this.getGridConfig().addColumn(this.getText("支付时间"),
		// "peBzzOrderInfo.paymentDate", false);
		/*if (capabilitySet.contains(this.servletPath + "_del.action")) {// 按钮权限判断
			this.getGridConfig().setCapability(false, true, false);// 删除功能
		}*/
//		if (capabilitySet.contains(this.servletPath + "_open.action")) {// 按钮权限判断
//
			this.getGridConfig().addMenuScript(
					this.getText("Excel批量导入发票号、发票代码"),
					"{window.location='/entity/basic/invoiceManage_toExcelCheckIn.action?flag="
							+ this.getFlag() + "';}");
//			this.getGridConfig().addMenuFunction(this.getText("开具发票"),
//					"Invoicing");
//			this
//					.getGridConfig()
//					.addMenuScript(
//							this.getText("写入发票号码、发票代码"),
//							"{ alert('写入发票号码、发票代码分别用“、”隔开');"
//									+
//
//									"var m=grid.getSelections();"
//									+ "    var ids='';"
//									+ "	if(m.length==1){"
//									+ "		var ss=m[0].get('id');"
//									+ "		ids=ids+ss;"
//									+ "    }else{"
//									+ "     Ext.MessageBox.alert('错误','必须选择一条数据');"
//									+ "     return;"
//									+ "    }"
//									+ ""
//									+ "   var invoiceCode=new Ext.form.TextField({"
//									+ "    fieldLabel: '发票号码、发票代码',"
//									+ "    valueField:'id',"
//									+ "    displayField:'name',"
//									+ "    width:250,"
//									//+ " vtype:'alphanum',"
//									//+ " vtypeTest:'只能输入字母和数字',"
//									//+ "    allowBlank:false,"
//									+ "	 blankText:'发票号码、发票代码',"
//									+ "    typeAhead:true,"
//									+ "    name:'code',id:'code'"
//									+ "    });"
//									+ "var ids=new Ext.form.Hidden({name:'ids',value:ids});"
//									+ "var style=new Ext.form.Hidden({name:'style',value:'2'});"
//									+ "   var formPanel=new Ext.form.FormPanel({"
//									+ "    frame:true,"
//									+ "    labelWidth:100,"
//									+ "    defaultType:'textfield',"
//									+ "    autoScroll:true,"
//									+ "    items:[invoiceCode,ids]"
//									+ "   });"
//									+ ""
//									+ "   var addModelWin=new Ext.Window({"
//									+ "    title:'发票号码、发票代码',"
//									+ "    width:450,"
//									+ "    height:250,"
//									+ "    minWidth:300,"
//									+ "    minHeight:250,"
//									+ "    layout:'fit',"
//									+ "    plain:true,"
//									+ "    bodyStyle:'padding:5px;',"
//									+ "    items:formPanel,"
//									+ "    buttonAlign:'center',"
//									+ "    buttons:[{"
//									+ "     text:'确定',"
//									+ "     handler:function(){"
//									+ "      if(formPanel.form.isValid()){"
//									+ "       formPanel.form.submit({"
//									+ "        url:'/entity/basic/invoiceManage_writeCode.action',"
//									+ "        waitMsg:'处理中，请稍候...',"
//									+ "        success:function(form,action){"
//									+ "         var responseArray=action.result;"
//									+ "         if(responseArray.success=='true'){"
//									+ "          Ext.MessageBox.alert('提示',responseArray.info);"
//									+ "          store.load({"
//									+ "           params:{start:g_start,limit:g_limit}"
//									+ "          });"
//									+ "          addModelWin.close();"
//									+ "         }else{"
//									+ "          Ext.MessageBox.alert('错误',responseArray.info);"
//									+ "          addModelWin.close();"
//									+ "          }" + "        }"
//									+ "       });" + "      }" + "     }"
//									+ "    }," + "    {text:'取消',"
//									+ "     handler:function(){"
//									+ "      addModelWin.close();" + "     }}]"
//									+ "   });" + "   addModelWin.show();" + "}");

			// 打印发票信息 by wuhao start
			this
					.getGridConfig()
					.addMenuScript(
							this.getText("写入发票号码、发票代码"),
							"{var m = grid.getSelections();  "
									+ "if(m.length == 1){	         "
									+ "	var jsonData = '';       "
									+ "	for(var i = 0, len = m.length; i < len; i++){"
									+ "		var ss =  m[i].get('id');"
									+ "		if(i==0)	jsonData = jsonData + ss ;"
									+ "		else	jsonData = jsonData + ',' + ss;"
									+ "	}                        "
									+ "	document.getElementById('user-defined-content').style.display='none'; "
									+ "	document.getElementById('user-defined-content').innerHTML=\"<form target='' action='/entity/basic/invoiceManage_toWriteCode.action' method='post' name='formx1' style='display:none'><input type=hidden name=ids value='\"+jsonData+\"' ></form>\";"
									+ "	document.formx1.submit();"
									+ "	document.getElementById('user-defined-content').innerHTML=\"\";"
									+ "} else {                    "
									+ "Ext.MessageBox.alert('错误', '请选择一条数据');  "
									+ "}}             ");
//
//			// 打印发票信息 end
//			this.getGridConfig().addMenuFunction(
//					this.getText("修正发票状态（批量操作前执行）"), "InvoiceType.default");
//		}

		this
				.getGridConfig()
				.addRenderFunction(
						this.getText("查看详情"),
						"<a target='_blank' href=\"/entity/basic/invoiceManage_viewDetail.action?id=${value}\") >查看</a>",
						"id");

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeBzzInvoiceInfo.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/invoiceManage";
	}

	/**
	 * 打印发票
	 * 
	 * @author linjie
	 * @return
	 */
	public String printInvoice() {
		Map map = new HashMap();
		String msg = "";
		try {
			if (this.getIds() != null && this.getIds().length() > 0) {
				String[] ids = this.getIds().split(",");
				DetachedCriteria dc = DetachedCriteria
						.forClass(PeBzzInvoiceInfo.class);
				dc.createCriteria("enumConstByFlagFpOpenState",
						"enumConstByFlagFpOpenState").add(
						Restrictions.eq("code", "1"));
				dc.add(Restrictions.in("id", ids));
				// String sqlcheck=" select pi.id from pe_bzz_invoice_info pi
				// ,enum_const ec where ec.id=pi.flag_fp_open_state "
				// + " and ec.code='1' and pi.id in ("+ids+")";
				List list = this.getGeneralService().getList(dc);

				// int k=this.getGeneralService().executeBySQL(sqlcheck);
				if (list.size() < ids.length) {
					map.clear();
					map.put("success", "false");
					map.put("info", "所选列中有未开具发票");
					this.setJsonString(JsonUtil.toJSONString(map));
					this.setMsg("所选列中有未开具发票");
					return "msg";
				}
				List<PeBzzInvoiceInfo> InvoiceList = new ArrayList<PeBzzInvoiceInfo>();
				// 更新打印状态
				for (int i = 0; i < ids.length; i++) {
					String id = ids[i];

					PeBzzInvoiceInfo invoice = null;
					invoice = this.getGeneralService().getById(id);
					invoice.setEnumConstByflagPrintStatus(this
							.getEnumConstService().getByNamespaceCode(
									"flagPrintStatus", "1"));
					InvoiceList.add(invoice);
				}
				this.getPrintInvoiceInfo();
				this.getGeneralService().saveList(InvoiceList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "printInvoice";
	}

	/**
	 * 获得打印发票的详情
	 * 
	 * @author linjie
	 * @return
	 */
	public List getPrintInvoiceInfo() {
		String[] ids = this.getIds().split(",");
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
		dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
		dc.add(Restrictions.in("id", ids));

		// String sql=" select * from pe_bzz_invoice_info pi ,pe_bzz_order_info
		// po where pi.fk_order_id=po.id and pi.id in ()";
		List<PeBzzInvoiceInfo> Invoices = new ArrayList<PeBzzInvoiceInfo>();
		List newInvoices = new ArrayList();
		try {
			Invoices = this.getGeneralService().getList(dc);
			if (Invoices != null && Invoices.size() > 0) {

				for (int j = 0; j < Invoices.size(); j++) {
					StringBuffer sb = new StringBuffer();
					String newAddress = "";
					String newCity = "";
					PeBzzOrderInfo ord = new PeBzzOrderInfo();
					PeBzzInvoiceInfo newInfo = new PeBzzInvoiceInfo();
					String newZipcode = Invoices.get(j).getZipCode();

					for (int k = 0; k < newZipcode.length(); k++) {
						sb.append(newZipcode.charAt(k) + "        ");

					}
					newInfo.setZipCode(sb.toString());
					// newInfo.setZipCode(newZipcode);
					String address = Invoices.get(j).getAddress();
					if (address.length() < 25) {
						newAddress = address;
					} else {
						newAddress = address.substring(0, 25);
						newCity = address.substring(25);

					}
					newInfo.setAddress(newAddress);
					newInfo.setCity(newCity);// 借用字段，地址长度多于25位时用到
					newInfo.setAddressee(Invoices.get(j).getAddressee());
					ord.setAmount(Invoices.get(j).getPeBzzOrderInfo()
							.getAmount());
					newInfo.setPeBzzOrderInfo(ord);
					newInvoices.add(newInfo);
				}

			}

		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newInvoices;
	}

	/**
	 * 重写框架方法--列更新
	 * 
	 * @author linjie
	 * @return
	 */
	public Map updateColumn() {
		Map map = new HashMap();
		String action = this.getColumn();
		if (action.equals("InvoiceType.default")) {
			int i = 0;
			try {
				i = this.genDefaultType("");
			} catch (EntityException e) {
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", "更新失败：" + e.getMessage());
			}
			map.clear();
			map.put("success", "true");
			map.put("info", "成功更新修正发票信息");
			try {
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			if (this.getIds() != null && this.getIds().length() > 0) {
				try {
					String[] ids = getIds().split(",");
					DetachedCriteria dc = DetachedCriteria
							.forClass(PeBzzInvoiceInfo.class);
					dc.createCriteria("enumConstByFlagFpOpenState");
					dc.add(Restrictions.in("id", ids));
					List<PeBzzInvoiceInfo> invoiceList = this
							.getGeneralService().getDetachList(dc);
					EnumConst enumConstByFlagFpOpenState = this
							.getEnumConstService().getByNamespaceCode(
									"FlagFpOpenState", "1");
					List list = new ArrayList();
					for (PeBzzInvoiceInfo invoice : invoiceList) {
						if (invoice.getNum() != null
								&& invoice.getNum().trim().length() != 0
								&& (invoice.getEnumConstByFlagFpOpenState()
										.getCode()).equals("1")) {
							map.clear();
							map.put("success", "false");
							map.put("info", "有订单已开发票，不能再开");
							return map;
						} else if (invoice.getNum() != null
								&& invoice.getNum().trim().length() != 0
								&& (invoice.getEnumConstByFlagFpOpenState()
										.getCode()).equals("0")) {
							invoice
									.setEnumConstByFlagFpOpenState(enumConstByFlagFpOpenState);
							invoice.setOpenDate(new Date());
							list.add(invoice);
						} else {
							map.clear();
							map.put("success", "false");
							map.put("info", "有订单未填写发票号码，请先填写");
							return map;
						}
					}
					this.getGeneralService().saveList(list);
					map.put("success", "true");
					map.put("info", "开具发票操作成功");
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					map.clear();
					map.put("success", "false");
					map.put("info", "操作失败");
					return map;
				}

			}
		}
		return map;

	}
	public String toWriteCode(){
		HttpServletRequest request = ServletActionContext.getRequest();
		this.id = this.getIds();
		return "writeCode";
	}
	/**
	 * 写入发票号
	 * 
	 * @author linjie
	 * @return
	 */
	public String writeCode() {
		Map map = new HashMap();
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = this.id;
		String code = this.invoiceCode;
		String fpdm = this.invoiceFpdm;

		try {
			DetachedCriteria dc = DetachedCriteria
					.forClass(PeBzzInvoiceInfo.class);
			dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
			dc.createCriteria("enumConstByFlagFpOpenState",
					"enumConstByFlagFpOpenState", DetachedCriteria.LEFT_JOIN);
			dc.createCriteria("enumConstByInvoiceType",
					"enumConstByInvoiceType", DetachedCriteria.LEFT_JOIN);
			dc.add(Restrictions.eq("id", id));
			List<PeBzzInvoiceInfo> invoiceList = this.getGeneralService()
					.getList(dc);
			PeBzzInvoiceInfo invoice = invoiceList.get(0);
			/*
			 * if (invoice.getNum() != null &&
			 * !invoice.getNum().trim().equals("")) { map.put("success",
			 * "false"); map.put("info", "发票号已存在，不能重写");
			 * this.setJsonString(JsonUtil.toJSONString(map)); return json(); }
			 */
			EnumConst enumConstByFlagFpOpenState = this.getEnumConstService()
			.getByNamespaceCode("FlagFpOpenState", "1");// 已开发票
			if(null != invoiceList.get(0) && null != invoiceList.get(0).getPeBzzOrderInfo().getMergeSeq() && !"".equals(invoiceList.get(0).getPeBzzOrderInfo().getMergeSeq())){
				DetachedCriteria dcs = DetachedCriteria
				.forClass(PeBzzInvoiceInfo.class);
				dcs.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
				dcs.createCriteria("enumConstByFlagFpOpenState",
				"enumConstByFlagFpOpenState", DetachedCriteria.LEFT_JOIN);
				dcs.createCriteria("enumConstByInvoiceType",
				"enumConstByInvoiceType", DetachedCriteria.LEFT_JOIN);
				dcs.add(Restrictions.eq("peBzzOrderInfo.mergeSeq", invoiceList.get(0).getPeBzzOrderInfo().getMergeSeq()));
				List<PeBzzInvoiceInfo> allInvoiceList = this.getGeneralService().getList(dcs);
				for(PeBzzInvoiceInfo info :allInvoiceList){
					info.setNum(code);
					info.setFpdmNum(fpdm);
					info.setEnumConstByFlagFpOpenState(enumConstByFlagFpOpenState);
					info.setOpenDate(new Date());
					this.getGeneralService().save(info);
				}
			}else{
				invoice.setNum(code);
				invoice.setFpdmNum(fpdm);
				invoice.setEnumConstByFlagFpOpenState(enumConstByFlagFpOpenState);
				invoice.setOpenDate(new Date());
				invoice = this.getGeneralService().save(invoice);
				//this.genDefaultType(invoice.getId());
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			this.setMsg("发票号码、发票代码写入失败");
			this.setTogo(this.getServletPath() + ".action");
			return "msg";
		}

		this.setMsg("发票号码、发票代码写入成功");
		this.setTogo(this.getServletPath() + ".action");
		return "msg";

	}

	/**
	 * 框架方法：或者查询列表所需数据
	 * 
	 * @author linjie
	 * @return
	 */
	public DetachedCriteria initDetachedCriteria() {
		EnumConst enumConstByFlagPaymentState = this.getEnumConstService()
				.getByNamespaceCode("FlagPaymentState", "1");// 已到账
		EnumConst enumConstByFlagPaymentMethod = this.getEnumConstService()
				.getByNamespaceCode("FlagPaymentMethod", "1");// 预付费不能开发票
		EnumConst enumConstByInvoiceType = this.getEnumConstService().getByNamespaceCode("InvoiceType", "3"); 
		// EnumConst enumConstByFlagCheckState =
		// this.getEnumConstService().getByNamespaceCode("FlagCheckState","1");//已对账
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
		dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
		dc.createCriteria("peBzzOrderInfo.enumConstByFlagPaymentMethod",
				"enumConstByFlagPaymentMethod");
		dc.createCriteria("peBzzOrderInfo.enumConstByFlagPaymentState",
				"enumConstByFlagPaymentState");
		dc.createCriteria("peBzzOrderInfo.enumConstByFlagCheckState",
				"enumConstByFlagCheckState");
		dc.createAlias("enumConstByFlagFpOpenState",
				"enumConstByFlagFpOpenState");
		dc.createAlias("enumConstByInvoiceType", "enumConstByInvoiceType",
				DetachedCriteria.LEFT_JOIN);
		dc.createAlias("enumConstByflagPrintStatus",
				"enumConstByflagPrintStatus", DetachedCriteria.LEFT_JOIN);
		dc.createAlias("enumConstByFlagPostType",
				"enumConstByFlagPostType", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peBzzOrderInfo.ssoUser", "ssoUser");
		dc.add(Restrictions.not(Restrictions.eq(
				"peBzzOrderInfo.enumConstByFlagPaymentMethod",
				enumConstByFlagPaymentMethod)));// 预付费不能开发票
		dc.add(Restrictions.eq("peBzzOrderInfo.enumConstByFlagPaymentState",
				enumConstByFlagPaymentState));
		dc.add(Restrictions.ne("enumConstByInvoiceType", enumConstByInvoiceType));
		try {
			// 处理时间的查询参数
			ActionContext context = ActionContext.getContext();
			if (context.getParameters() != null) {
				Map params = this.getParamsMap();
				if (params != null) {
					if (StringUtils.defaultString(this.getSort()).equals(
							"enumConstByFlagPaymentMethod.name")) {
						if (this.getDir().equals("ASC")) {
							dc.addOrder(Order
									.asc("enumConstByFlagPaymentMethod.name"));
						} else {
							dc.addOrder(Order
									.desc("enumConstByFlagPaymentMethod.name"));
						}
					}
					if (params.get("search__payStartDatetime") != null) {
						String[] startDate = (String[]) params
								.get("search__payStartDatetime");
						String tempDate = startDate[0];
						if (startDate.length == 1
								&& !StringUtils.defaultString(startDate[0])
										.equals("")) {
							startDate[0] = ">=" + startDate[0];
							params.remove("search__payStartDatetime");
							// params.put("search__peBzzOrderInfo.paymentDate",
							// startDate);
							context.setParameters(params);
							SimpleDateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							// Date sDate = format.parse(tempDate + " 00:00:00
							// ");
							Date sDate = format.parse(tempDate);
							dc.add(Restrictions.ge(
									"peBzzOrderInfo.paymentDate", sDate));
						}
					}
					if (params.get("search__payEndDatetime") != null) {
						String[] startDate = (String[]) params
								.get("search__payEndDatetime");
						String tempDate = startDate[0];
						if (startDate.length == 1
								&& !StringUtils.defaultString(startDate[0])
										.equals("")) {
							startDate[0] = "<=" + startDate[0];
							params.remove("search__payEndDatetime");
							// params.put("search__peBzzOrderInfo.paymentDate",
							// startDate);
							context.setParameters(params);
							SimpleDateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							// Date endDate = format.parse(tempDate + " 23:59:59
							// ");
							Date endDate = format.parse(tempDate);
							dc.add(Restrictions.le(
									"peBzzOrderInfo.paymentDate", endDate));
						}
					}
					if (params.get("search__openStartDatetime") != null) {
						String[] startDate = (String[]) params
								.get("search__openStartDatetime");
						String tempDate = startDate[0];
						if (startDate.length == 1
								&& !StringUtils.defaultString(startDate[0])
										.equals("")) {
							startDate[0] = ">=" + startDate[0];
							params.remove("search__openStartDatetime");
							// params.put("search__peBzzOrderInfo.paymentDate",
							// startDate);
							context.setParameters(params);
							SimpleDateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							// Date sDate = format.parse(tempDate + " 00:00:00
							// ");
							Date sDate = format.parse(tempDate);
							dc.add(Restrictions.ge("openDate", sDate));
						}
					}
					if (params.get("search__openEndDatetime") != null) {
						String[] startDate = (String[]) params
								.get("search__openEndDatetime");
						String tempDate = startDate[0];
						if (startDate.length == 1
								&& !StringUtils.defaultString(startDate[0])
										.equals("")) {
							startDate[0] = "<=" + startDate[0];
							params.remove("search__openEndDatetime");
							// params.put("search__peBzzOrderInfo.paymentDate",
							// startDate);
							context.setParameters(params);
							SimpleDateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							// Date endDate = format.parse(tempDate + " 23:59:59
							// ");
							Date endDate = format.parse(tempDate);
							dc.add(Restrictions.le("openDate", endDate));
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dc;
	}

	/**
	 * 查看详情
	 * 
	 * @author linjie
	 * @return
	 */
	public String viewDetail() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
		dc.createCriteria("enumConstByFlagFpOpenState",
				"enumConstByFlagFpOpenState", DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.eq("id", id));
		try {
			this.peBzzInvoiceInfo = (PeBzzInvoiceInfo) this.getGeneralService()
					.getList(dc).get(0);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "invoiceDetail";
	}

	/***************************************************************************
	 * @Override public Page list() { // TODO Auto-generated method stub String
	 *           sql = "\n" + "select * from (select i.id as id,\n" + " o.seq as
	 *           seq,\n" + " o.payment_date as paymentDate,\n" + " i.addressee
	 *           as addressee,\n" + " i.address as address,\n" + " i.title as
	 *           title,\n" + " o.amount as amount,\n" + " ec.name as
	 *           combobox_paymentMethod,\n" + " ec2.name as
	 *           combobox_fpOpenState,\n" + " i.num as num,\n" + " s.true_name
	 *           as name,\n" + " s.reg_no as loginId,\n" + " e.name as
	 *           enName,\n" + " e.code as enCode\n" + " from pe_bzz_invoice_info
	 *           i\n" + " inner join pe_bzz_order_info o on i.fk_order_id =
	 *           o.id\n" + " inner join sso_user u on u.id = o.create_user\n" + "
	 *           inner join pe_bzz_student s on s.fk_sso_user_id = u.id\n" + "
	 *           inner join pe_enterprise e on s.fk_enterprise_id = e.id\n" + "
	 *           inner join enum_const ec on ec.id = o.flag_payment_method\n" + "
	 *           inner join enum_const ec2 on ec2.id = i.flag_fp_open_state\n" +
	 *           "union\n" + "select i.id as id,\n" + " o.seq as seq,\n" + "
	 *           o.payment_date as paymentDate,\n" + " i.addressee as
	 *           addressee,\n" + " i.address as address,\n" + " i.title as
	 *           title,\n" + " o.amount as amount,\n" + " ec.name as
	 *           combobox_paymentMethod,\n" + " ec2.name as
	 *           combobox_fpOpenState,\n" + " i.num as num,\n" + " m.name as
	 *           name,\n" + " m.login_id as loginId,\n" + " e.name as enName,\n" + "
	 *           e.code as enCode\n" + " from pe_bzz_invoice_info i\n" + " inner
	 *           join pe_bzz_order_info o on i.fk_order_id = o.id\n" + " inner
	 *           join sso_user u on u.id = o.create_user\n" + " inner join
	 *           pe_enterprise_manager m on m.fk_sso_user_id = u.id\n" + " inner
	 *           join pe_enterprise e on m.fk_enterprise_id = e.id\n" + " inner
	 *           join enum_const ec on ec.id = o.flag_payment_method\n" + "
	 *           inner join enum_const ec2 on ec2.id = i.flag_fp_open_state)
	 *           where 1=1 "; StringBuffer sqlBuf = new StringBuffer();
	 *           sqlBuf.append(sql); this.setSqlCondition(sqlBuf);
	 * 
	 * Page page = null; try { page =
	 * getGeneralService().getByPageSQL(sqlBuf.toString(),
	 * Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart())); }
	 * catch (NumberFormatException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (EntityException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); }
	 * 
	 * return page; }
	 **************************************************************************/

	/**
	 * 重写框架方法：更细数据前的校验
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public List checkBeforeUpdate(List list) {
		EnumConst ec = this.enumConstService.getByNamespaceCode(
				"FlagFpOpenState", "1");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				PeBzzInvoiceInfo invoice = (PeBzzInvoiceInfo) list.get(i);
				if (invoice.getNum() != null && !"".equals(invoice.getNum())) {
					invoice.setEnumConstByFlagFpOpenState(ec);
				}
			}
		}
		return list;
	}

	/**
	 * 重写框架方法：删除数据前的校验
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void checkBeforeDelete(List idList) throws EntityException {
		// 没有开发票的（待开的）才能删除
		List iList = null;
		EnumConst ec = this.enumConstService.getByNamespaceCode(
				"FlagFpOpenState", "1");// 已开发票
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
		dc.createAlias("enumConstByFlagFpOpenState",
				"enumConstByFlagFpOpenState");
		dc.add(Restrictions.eq("enumConstByFlagFpOpenState", ec));
		dc.add(Restrictions.in("id", idList));
		iList = this.getGeneralService().getList(dc);
		if (iList != null && iList.size() > 0) {
			throw new EntityException("已开发票不能删除！");
		}
	}

	/**
	 * @author 魏慧宁 重写父类方法，去掉日期格式化
	 * 
	 * @return
	 */
	public String abstractList() {
		initGrid();

		Page page = list();
		List jsonObjects = JsonUtil.ArrayToJsonObjects(page.getItems(), this
				.getGridConfig().getListColumnConfig());
		Map map = new HashMap();
		if (page != null) {
			map.put("totalCount", page.getTotalCount());
			map.put("models", jsonObjects);
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}

	/**
	 * 更新发票类型
	 * 
	 * @author linjie
	 * @return
	 */
	private int genDefaultType(String id) throws EntityException {
		String sql = "update pe_bzz_invoice_info i\n"
				+ "   set i.invoice_type =\n"
				+ "       (select case\n"
				+ "                 when ix.num is null or ix.num = '' then\n"
				+ "                  '4028808c1e1f9f08011e1fa750cc000cx'--未录入\n"
				+ "                 when trim(ix.num) = '0' then\n"
				+ "                  '4028808c1e1f9f08011e1fa750cc000ax'--定额发票\n"
				+ "                 else\n"
				+ "                  '4028808c1e1f9f08011e1fa750cc000bx'--机打发票\n"
				+ "               end\n"
				+ "          from pe_bzz_invoice_info ix\n"
				+ "         where ix.id = i.id)";// 更新发票类型
		String sql1 = "update pe_bzz_invoice_info i set i.flag_print_status='40288acf3bb20d25013bb280c97b012d' where i.flag_print_status is null";// 更新打印状态
		String sql2 = "update pe_bzz_invoice_info i set i.open_date=sysdate where i.open_date is null and i.num is not null";// 更新开具时间
		if (id != null && !"".equals(id)) {
			sql += " where i.id='" + id + "'";
			sql2 += " and i.id='" + id + "'";
		} else {
			this.getGeneralService().executeBySQL(sql1);
		}

		this.getGeneralService().executeBySQL(sql);
		this.getGeneralService().executeBySQL(sql2);
		return 0;
	}

	/**
	 * excel批量更新
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public String update_excel() {
		Map map = new HashMap();

		super.update_excel();

		try {
			this.genDefaultType("");
		} catch (EntityException e) {
			map.put("success", "false");
			map.put("info", "文件上传失败");
			this.setJsonString(JsonUtil.toJSONString(map));
		}

		return json();
	}

	public String toExcelCheckIn() {
		return "toExcelCheckIn";
	}

	/**
	 * excel上传
	 * 
	 * @author linjie
	 * @return
	 */
	public String batchUpload() {
		int re = 0;
		StringBuffer msg = new StringBuffer();
		Workbook work = null;
		try {
			work = Workbook.getWorkbook(this.get_upload());
		} catch (Exception e) {
			e.printStackTrace();
			msg.append("Excel表格读取异常！批量更新失败！<br/>");
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows < 2) {
			msg.append("表格为空！<br/>");
		}

		Pattern pOrder = Pattern.compile("^\\d{5,15}$");// 5-15数字
		Pattern pNum = Pattern.compile("^\\w{1,30}$");// 1-30数字与字母的组合
		int kilos = (rows % 1000 == 0) ? (rows / 1000) : ((rows / 1000) + 1);
		for (int k = 0; k < kilos; k++) {// LOOP START

			for (int i = k * 1000 + 1; i < (k + 1) * 1000 + 1 && i < rows; i++) {
				String orderSeq = sheet.getCell(0, i).getContents().trim();// 订单号
				String orderNum = sheet.getCell(1, i).getContents().trim();// 发票号
				String invoiceFpdm = sheet.getCell(2, i).getContents().trim();// 发票代码
				String invoiceFpjym = sheet.getCell(3, i).getContents().trim(); // 发票校验码
				// 验证格式
				if (orderSeq == null || "".equals(orderSeq.trim())) {
					msg.append("第" + (i + 1) + "行数据，订单号不能为空！<br/>");
					continue;
				}
				if (orderNum == null || "".equals(orderNum.trim())) {
					msg.append("第" + (i + 1) + "行数据，发票号不能为空！<br/>");
					continue;
				}
				if (invoiceFpdm == null || "".equals(invoiceFpdm)){
					msg.append("第" + (i +1) + "行数据，发票代码不能为空！<br/>");
					continue;
				}
				if (!pOrder.matcher(orderSeq).matches()) {
					msg.append("第" + (i + 1) + "行数据，订单号格式不正确！<br/>");
					continue;
				}
				if (!pNum.matcher(orderNum).matches()) {
					msg.append("第" + (i + 1) + "行数据，发票号格式不正确！<br/>");
					continue;
				}
				// 验证订单和发票
				String checkSql = "select o.id from pe_bzz_order_info o,pe_bzz_invoice_info i where o.id=i.fk_order_id and o.seq= '"
						+ orderSeq.trim() + "'";
				try {
					List list = this.getGeneralService().getBySQL(checkSql);
					if (list == null || list.size() < 1) {
						msg.append("第" + (i + 1) + "行数据，不存在订单号" + orderSeq
								+ "对应的发票！<br/>");
						continue;
					} else if (list.size() > 1) {
						msg.append("第" + (i + 1) + "行数据，订单号" + orderSeq
								+ "对应多条发票信息，请联系管理员解决！<br/>");
						continue;
					}
					// 更新发票
					String exeSql =

					"update pe_bzz_invoice_info i\n"
							+ "   set i.num                = '"
							+ orderNum
							+ "',\n"
							+ "       i.flag_fp_open_state = '402880f327dc55c90127dc7131ad0001',\n"
//							+ "       i.invoice_type = (case\n"
//							+ "                          when '"
//							+ invoiceFpjym
//							+ "' is null or '"
//							+ invoiceFpjym
//							+ "' = '' then\n"
//							+ "                           '4028808c1e1f9f08011e1fa750cc000ex' --专用发票\n"
//							+ "                          when trim('"
//							+ orderNum
//							+ "') = '0' then\n"
//							+ "                           '4028808c1e1f9f08011e1fa750cc000ax' --定额发票\n"
//							+ "                          else\n"
//							+ "                           '4028808c1e1f9f08011e1fa750cc000dx' --电子发票\n"
//							+ "                        end),\n"
							+ " i.fpdm_num = '" + invoiceFpdm
							+ "' , \n "
							+ " i.fpjym_num = '" + invoiceFpjym
							+ "' , \n "
							+ "       i.open_date          = sysdate\n"
							+ " where i.fk_order_id in\n"
							+ "       (select o.id from pe_bzz_order_info o where o.seq = '"
							+ orderSeq + "')";

					re += this.getGeneralService().executeBySQL(exeSql);
					try {
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (EntityException e) {
					e.printStackTrace();
				}

			}
		}
		this.setMsg("成功更新" + re + "条发票信息<br/>" + msg.toString());
		//this.setTogo(this.servletPath + ".action");
		this.setTogo("javascript:history.go(-2)");
		return "msg";
	}
	public String getInvoiceMess(){
		List<PeBzzInvoiceInfo> peBzzInvoiceInfo = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		String seq = request.getParameter("seq");
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
		dc.createCriteria("peBzzOrderInfo","peBzzOrderInfo");
		dc.add(Restrictions.eq("peBzzOrderInfo.seq", seq));
		try {
			peBzzInvoiceInfo = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XMLFileUtil  xf =new XMLFileUtil();
		String xml = xf.getXMLQuery(peBzzInvoiceInfo);
		if (null != xml && !"".equals(xml)) {
			// 调接口
			WebServiceInvoker wsi = new WebServiceInvoker();
			String wsReturn = wsi.client(xml);
			Map<String, Object> returnMap = XMLFileUtil
					.parseQueryXml(wsReturn);
			if("0000".equals(returnMap.get("returnCode"))) {
				if (null != (List<Map<String, String>>)returnMap.get("returnInfo") && ((List<Map<String, String>>)returnMap.get("returnInfo")).size() > 0) {
					List<Map> successList = new ArrayList<Map>();
					for (Map<String, String> map : (List<Map<String, String>>)returnMap.get("returnInfo")) {
						if (map.containsKey("FPDM") && map.containsKey("FPHM")
								&& null != map.get("FPDM")
								&& !"".equals(map.get("FPDM"))
								&& null != map.get("FPHM")
								&& !"".equals(map.get("FPHM"))) {
							successList.add(map);
						}
					}
					if (null != successList && successList.size() > 0) {
						try {
							this.getGeneralService().uptInvoiceState(successList);
						} catch (EntityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			} else {
				try {
					String str = new String(Base64Util.getFromBASE64(((String)returnMap.get("returnMsg"))).getBytes(), "UTF-8");
					this.setMsg(str);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("电子发票查询xml为空");
		}
		return "msg";
	}
}
