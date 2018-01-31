package com.whaty.platform.entity.web.action.basic;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeBzzRefundInfo;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.entity.web.util.WebServiceInvoker;
import com.whaty.platform.entity.web.util.XMLFileUtil;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class CollectiveOrderQueryAction extends MyBaseAction {

	private String flag = "";
	private String id;
	private String orderIds;
	private String forward;
	private EnumConstService enumConstService;
	private String refundReason;
	private String orderNum;// 单据
	private String operateresult;// 回填
	private String amount; // 合并金额
	private String strSeq; // 合并的订单号串
	private PeBzzRefundInfo peBzzRefundInfo;
	private PeBzzInvoiceInfo peBzzInvoiceInfo;
	private PeBzzOrderInfo peBzzOrderInfo;
	private List<PrBzzTchStuElective> electiveList;
	private String merorderid; // 订单号，用于在支付
	private String resetRemark; // 发票冲红描述信息
	private String invoiceType;// 发票类型
	private String[] colsOrder = { "id", "seq", "cname", "payer", "num", "paymentDate", "createDate", "amount", "combobox_peymentMethod",
			"combobox_orderType", "combobox_paymentState", "stuNum", "isRefund", "COMBOBOX_REFUNDSTATE", "combobox_ZhiFuState" };
	private String[] colsDetail = { "id", "trueName", "code", "name", "time" };
	protected Set<String> setOrder = new HashSet<String>(Arrays.asList(colsOrder));
	protected Set<String> setDetail = new HashSet<String>(Arrays.asList(colsDetail));

	private SsoUser ssoUser;
	private PeBzzStudent peBzzStudent;
	private PeEnterpriseManager peEnterpriseManager;

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

	public List<PrBzzTchStuElective> getElectiveList() {
		return electiveList;
	}

	public void setElectiveList(List<PrBzzTchStuElective> electiveList) {
		this.electiveList = electiveList;
	}

	public PeBzzInvoiceInfo getPeBzzInvoiceInfo() {
		return peBzzInvoiceInfo;
	}

	public void setPeBzzInvoiceInfo(PeBzzInvoiceInfo peBzzInvoiceInfo) {
		this.peBzzInvoiceInfo = peBzzInvoiceInfo;
	}

	public PeBzzRefundInfo getPeBzzRefundInfo() {
		return peBzzRefundInfo;
	}

	public void setPeBzzRefundInfo(PeBzzRefundInfo peBzzRefundInfo) {
		this.peBzzRefundInfo = peBzzRefundInfo;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
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
		if (flag.equals("viewDetail")) {
			this.getGridConfig().setTitle(this.getText("查看详细"));
			this.getGridConfig().setCapability(false, false, false);
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("学员姓名"), "trueName", true);
			this.getGridConfig().addColumn(this.getText("课程编号"), "code", true);
			this.getGridConfig().addColumn(this.getText("课程名称"), "name", true);
			this.getGridConfig().addColumn(this.getText("学时"), "time", false);
			this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back()}");

		} else {
			this.getGridConfig().setCapability(false, false, false);
			this.getGridConfig().setTitle(this.getText("订单查询"));
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("订单号"), "seq", true, false, true, "");
			this.getGridConfig().addColumn(this.getText("订单别名"), "cname", true, false, true, "");
			this.getGridConfig().addColumn(this.getText("支付人"), "payer", true, false, true, "");
			if (this.getServletPath().indexOf("collectiveOrderQuery") > -1) {// 订单查询
				this.getGridConfig().addColumn(this.getText("单据号码"), "num", false, true, false, "");
			} else {// 电汇支票查询
				this.getGridConfig().addColumn(this.getText("单据号码"), "num", false, true, true, "");
			}
			this.getGridConfig().addColumn(this.getText("支付时间"), "paymentDate", false, false, true, "");
			this.getGridConfig().addColumn(this.getText("总金额"), "amount", false, false, true, "");
			ColumnConfig c_name = new ColumnConfig(this.getText("支付方式"), "combobox_peymentMethod", true, false, true, "TextField", true,
					25, "");
			c_name
					.setComboSQL("select id, name  from enum_const where namespace = 'FlagPaymentMethod' and id <> '40288a7b394207de01394212e47f0005x' UNION SELECT '10', '在线支付-个人网银'  FROM DUAL UNION SELECT '14', '在线支付-企业网银' FROM DUAL");
			this.getGridConfig().addColumn(c_name);
			ColumnConfig c_name1 = new ColumnConfig(this.getText("报名与付费方式"), "combobox_orderType", true, false, true, "TextField", true,
					25, "");
			c_name1.setComboSQL("select id, name  from enum_const where namespace = 'FlagOrderType' and code<>'0' and code<>'6'");
			this.getGridConfig().addColumn(c_name1);
			/*
			 * this.getGridConfig().addColumn(this.getText("到账状态"),
			 * "paymentState", true, false, true, "");
			 */
			ColumnConfig isPayment = new ColumnConfig(this.getText("到账状态"), "combobox_paymentState", true, false, true, "TextField", true,
					25, "");
			isPayment.setComboSQL("select id, name  from enum_const where namespace = 'FlagPaymentState' ");
			this.getGridConfig().addColumn(isPayment);
			this.getGridConfig().addColumn(this.getText("选课总数"), "stuNum", false, false, true, "");
			ColumnConfig isZhuFu = new ColumnConfig(this.getText("支付状态"), "combobox_ZhiFuState", true, false, true, "TextField", true, 25,
					"");
			isZhuFu.setComboSQL("select id, name  from enum_const where namespace = 'FlagZhiFuState' ");
			this.getGridConfig().addColumn(isZhuFu);

			// this.getGridConfig().addColumn(this.getText("支付状态"),
			// "combobox_ZhiFuState", false, false, true, "");

			this.getGridConfig().addColumn(this.getText("退费订单"), "isRefund", false, false, false, "");

			ColumnConfig refundState = new ColumnConfig(this.getText("退费状态"), "combobox_refundState", true, false, true, "TextField", true,
					25, "");
			refundState.setComboSQL("select id, name  from enum_const where namespace = 'FlagRefundState' ");
			this.getGridConfig().addColumn(refundState);
			/*
			 * this.getGridConfig().addColumn(this.getText("发票状态"),
			 * "isinvoiceState", true, false, true, "");
			 */
			ColumnConfig isFaPiao = new ColumnConfig(this.getText("发票状态"), "combobox_FaPiaoState", true, false, true, "TextField", true,
					25, "");
			isFaPiao.setComboSQL("select id, name  from enum_const where namespace = 'FlagFaPiaoState' ");
			this.getGridConfig().addColumn(isFaPiao);

			// 修改
			/*
			 * ColumnConfig flagPostType = new
			 * ColumnConfig(this.getText("邮寄方式"), "combobox_PostType", true,
			 * false, true, "TextField", true, 25, "");
			 * flagPostType.setComboSQL("select id, name from enum_const where
			 * namespace = 'FlagPostType' ");
			 * this.getGridConfig().addColumn(flagPostType);
			 */
			ColumnConfig flagPostType = new ColumnConfig(this.getText("合并订单号"), "MERGE_SEQ", false, false, true, "TextField", true, 25, "");
			flagPostType.setComboSQL("select id, name  from enum_const where namespace = 'FlagPostType' ");
			this.getGridConfig().addColumn(flagPostType);

			this.getGridConfig().addColumn(this.getText("支付编号"), "paymentCode", false, false, false, "");
			this.getGridConfig().addColumn(this.getText("支付起始时间"), "paysStartDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("支付结束时间"), "payEndDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("是否申请了发票"), "isinvoice", false, false, false, "");
			this.getGridConfig().addMenuFunction(this.getText("关闭订单"), new StringBuffer("关闭后，将不能再看到此订单，您确定要关闭订单吗？"), "FlagIsvalid.flase");
			this
					.getGridConfig()
					.addMenuScript(
							this.getText("申请退费"),
							"{var m = grid.getSelections();  "
									+ "if(m.length ==1){	         "
									+ "	var jsonData = '';       "
									+ "	var bId = '';       "
									+ "	for(var i = 0, len = m.length; i < len; i++){"
									+ "		var ss =  m[i].get('id');"
									+ "		if(i==0)	jsonData = jsonData + ss ;"
									+ "		if(m[i].get('combobox_orderType')=='购买学时订单'){alert('购买学时订单不能申请退费');return};"
									+ "	else if(m[i].get('COMBOBOX_REFUNDSTATE')=='已退费'){alert('已退费');return};"
									+ "	else if(m[i].get('COMBOBOX_REFUNDSTATE')=='已拒绝'){alert('已拒绝');return};"
									+ "	else if(m[i].get('combobox_ZhiFuState')=='未支付'){alert('未支付');return};"
									+ "	else if(m[i].get('isRefund')=='0'&&m[i].get('combobox_ZhiFuState')=='已支付'){ document.getElementById('user-defined-content').style.display='none'; "
									+ "	document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/collectiveOrderQuery_applyRefund.action' method='post' name='formx1' style='display:none'><input type=hidden name=id value='\"+jsonData+\"' ></form>\";"
									+ "	document.formx1.submit();"
									+ "	document.getElementById('user-defined-content').innerHTML=\"\";};"
									+ "	else { document.getElementById('user-defined-content').style.display='none'; "
									+ "	document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/collectiveOrderQuery_viewReason.action' method='post' name='formx1' style='display:none'><input type=hidden name=id value='\"+jsonData+\"' ></form>\";"
									+ "	document.formx1.submit();" + "	document.getElementById('user-defined-content').innerHTML=\"\";};"
									+ "	} " + "}else if(m.length >1){"
									+ "Ext.MessageBox.alert('错误', '只能选择一条数据');}else {                    "
									+ "Ext.MessageBox.alert('错误', '请至少选择一条数据');  " + "}}             ");
			if (this.getServletPath().indexOf("collectiveOrderQuery") > -1) {// 订单查询
				// Lee 支付接口切换 如需切换回宝易互通则将下列链接中的paymentType参数删除
				this
						.getGridConfig()
						.addMenuScript(
								this.getText("去支付"),
								"{var m = grid.getSelections();  "
										+ "if(m.length ==1){	       "
										+ "	var jsonData = '';       "
										+ "	var bId = '';       "
										+ "	for(var i = 0, len = m.length; i < len; i++){"
										+ "		var ss =  m[i].get('seq');"
										+ "		if(i==0)	jsonData = jsonData + ss ;"
										+ "		else	jsonData = jsonData + ',' + ss;"
										+ "		if( m[i].get('combobox_ZhiFuState')=='未支付'&& m[i].get('combobox_peymentMethod')=='在线支付'){"
										+ "		if(m[i].get('combobox_orderType')=='购买学时订单'){ document.getElementById('user-defined-content').style.display='none'; "
										+ "	document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/buyClassHour_continuePaymentOnline.action' target='_blank' method='post' name='formx1'style='display:none'><input type=hidden name=merorderid value='\"+jsonData+\"' ><input type=hidden name=paymentType id=paymentType value='99bill'/></form>\";"
										+ "	document.formx1.submit();"
										+ "	document.getElementById('user-defined-content').innerHTML=\"\";};"
										+ "	else	if(m[i].get('combobox_orderType')=='专项支付订单' || m[i].get('combobox_orderType')=='视频直播订单'){ document.getElementById('user-defined-content').style.display='none'; "
										+ "	document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/paymentBatch_continuePaymentOnline.action'  target='_blank'  method='post' name='formx1' style='display:none'><input type=hidden name=merorderid value='\"+jsonData+\"' ><input type=hidden name=paymentType id=paymentType value='99bill'/></form>\";"
										+ "	document.formx1.submit();"
										+ "	document.getElementById('user-defined-content').innerHTML=\"\";};"
										+ "	else	if(m[i].get('combobox_orderType')=='选课期选课'){ document.getElementById('user-defined-content').style.display='none'; "
										+ "	document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/peElectiveCoursePeriod_continuePaymentOnline.action' target='_blank' method='post' name='formx1' style='display:none'><input type=hidden name=merorderid value='\"+jsonData+\"' ><input type=hidden name=paymentType id=paymentType value='99bill'/></form>\";"
										+ "	document.formx1.submit();"
										+ "	document.getElementById('user-defined-content').innerHTML=\"\";};"
										+ "	else	if(m[i].get('combobox_orderType')=='直接选课'){ document.getElementById('user-defined-content').style.display='none'; "
										+ "	document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/collectiveElective_continuePaymentOnline.action' target='_blank' method='post' name='formx1' style='display:none'><input type=hidden name=merorderid value='\"+jsonData+\"' ><input type=hidden name=paymentType id=paymentType value='99bill'/></form>\";"
										+ "	document.formx1.submit();"
										+ "	document.getElementById('user-defined-content').innerHTML=\"\";};"
										+ "	else	if(m[i].get('combobox_orderType')=='报名单选课'){ document.getElementById('user-defined-content').style.display='none'; "
										+ "	document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/collectiveElective_continuePaymentOnline.action' target='_blank' method='post' name='formx1' style='display:none'><input type=hidden name=merorderid value='\"+jsonData+\"' ><input type=hidden name=paymentType id=paymentType value='99bill'/></form>\";"
										+ "	document.formx1.submit();"
										+ "	document.getElementById('user-defined-content').innerHTML=\"\";};" + "}else{"
										+ "Ext.MessageBox.alert('错误', '不满足支付条件');  } }}" + "else if(m.length > 1){"
										+ "Ext.MessageBox.alert('错误', '只能选择一条数据'); }else {                    "
										+ "Ext.MessageBox.alert('错误', '请至少选择一条数据');  " + "}}            ");

			} else {// 电汇支票查询
				this.getGridConfig().addRenderScript(
						this.getText("填写汇款银行账号"),
						"{if(record.data['num']!=''){" + "	return '已填写'" + "}else if(record.data['combobox_peymentMethod']=='公司转账'){"
								+ "	return '<a href=\"" + this.getServletPath()
								+ "_reFillout.action?id='+record.data['id']+'\") >填写汇款银行账号</a>'" + "}else{" + "	return '--'" + "}}", "id");
			}
			this.getGridConfig().addRenderScript(
					this.getText("发票详情"),
					"{if(record.data['combobox_orderType'] == '购买学时订单' ||record.data['combobox_orderType'] != '购买学时订单' ) {" + "	return '<a target=\"blank\" href=\""
							+ this.getServletPath() + "_invoiceViewDeatail.action?id='+record.data['id']+'&flag=viewDetail\">发票信息</a>'" + "} else {"
							+ "	return '<label title=购买学时订单没有选课详情>--</label>'" + "}}", "id");
			this.getGridConfig().addRenderScript(
					this.getText("订单详情"),
					"{if(record.data['combobox_orderType'] != '购买学时订单' ) {" + "	return '<a target=\"blank\" href=\""
							+ this.getServletPath() + "_viewDeatail.action?id='+record.data['id']+'&flag=viewDetail\">订单查看</a>'" + "} else {"
							+ "	return '<label title=购买学时订单没有选课详情>--</label>'" + "}}", "id");
			// this.getGridConfig().addRenderFunction(this.getText("详情"),
			// "<a
			// href=\"/entity/basic/collectiveOrderQuery_viewDeatail.action?id=${value}&flag=viewDetail\")
			// >查看</a>",
			// "id");

			// this.getGridConfig().addRenderScript(this.getText("回填单据号码"),
			// "{if(record.data['combobox_peymentMethod']=='电汇'||record.data['combobox_peymentMethod']=='支票'){return
			// '<a
			// href=\"/entity/basic/collectiveOrderQuery_reFillout.action?id='+record.data['id']+'\")
			// >回填单据号码</a>'}}","id");
			this.getGridConfig().addColumn(this.getText("发票"), "pbiiCode", false, false, false, "");
			this
					.getGridConfig()
					.addMenuScript(
							this.getText("申请发票"),
							"{ "
									+ "var m = grid.getSelections();  "
									+ "if(m.length >0){	         "
									+ "	var jsonData = '';       "
									+ "	var bId = '';       "
									+ "	for(var i = 0, len = m.length; i < len; i++){"
									+ "		if( m[i].get('combobox_FaPiaoState')=='已开'){alert('发票已开');return};"
									+ "		if(m[i].get('combobox_peymentMethod')=='预付费账户支付'){alert('预付费账户支付不能开具发票');return};"
									+ "		if(m[i].get('pbiiCode')=='402880f327dc55c90127dc7131ad0001'){alert('已开发票');return};"
									+ "		if(m[i].get('isinvoice')=='isinvoiced'){alert('已申请，不要重复申请');return};"
									+ "     var ss =  m[i].get('id');"
									+ "		if(i==0)	jsonData = jsonData + ss ;"
									+ "		else	jsonData = jsonData + ',' + ss;"
									+ "	}                    "
									+ "	 document.getElementById('user-defined-content').style.display='none'; "
									+ "   alert('当此订单金额小于1000元时，将无法点击选择增值税专用发票，因财务制度要求如需申请增值税专用发票，则可通过多笔订单合并(金额>=1000)方式申请,12月20日-1月1日不能再申请当年度发票.');  "
									+ "  document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/collectiveOrderQuery_toInvoiceApplyJsp.action?id=\"+jsonData+\"' method='post' name='formx1' style='display:none'></form>\";"
									+ "	document.formx1.submit();" + "	document.getElementById('user-defined-content').innerHTML=\"\";"
									+ "} else if(m.length>1){}   " + "else {                    "
									+ "Ext.MessageBox.alert('错误', '请至少选择一条数据');  " + "}}             ");
			this
					.getGridConfig()
					.addMenuScript(
							this.getText("修改发票"),
							"{var m = grid.getSelections();  "
									+ "if(m.length==1){	         "
									+ "	var jsonData = '';       "
									+ "	var bId = '';       "
									+ "	for(var i = 0, len = m.length; i < len; i++){"
									+ "		if(m[i].get('combobox_peymentMethod')=='预付费账户支付'){alert('预付费账户支付不能修改发票信息');return};"
									// + "
									// if(m[i].get('isinvoice')=='isinvoiced'){alert('发票未开具，不能申请发票冲红');return};"
									+ "     var ss =  m[i].get('id');"
									+ "		if(i==0)	jsonData = jsonData + ss ;"
									+ "		else	jsonData = jsonData + ',' + ss;"
									+ "	}                    "
									+ "	 document.getElementById('user-defined-content').style.display='none'; "
									+ "  document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/collectiveOrderQuery_toInvoiceUpdate.action?id=\"+jsonData+\"' method='post' name='formx1' style='display:none'></form>\";"
									+ "	document.formx1.submit();" + "	document.getElementById('user-defined-content').innerHTML=\"\";"
									+ "} else if(m.length>1){" + "Ext.MessageBox.alert('错误', '只能选择一条数据');}else {                    "
									+ "Ext.MessageBox.alert('错误', '请至少选择一条数据');  " + "}}             ");
			this
					.getGridConfig()
					.addMenuScript(
							this.getText("取消申请"),
							"{var m = grid.getSelections();  "
									+ "if(m.length==1){	         "
									+ "	var jsonData = '';       "
									+ "	var bId = '';       "
									+ "	for(var i = 0, len = m.length; i < len; i++){"
									// + "
									// if(m[i].get('combobox_peymentMethod')=='预付费账户支付'){alert('预付费账户支付不能');return};"
									// + "
									// if(m[i].get('isinvoice')=='isinvoiced'){alert('发票未开具，不能申请发票冲红');return};"
									+ "     var ss =  m[i].get('id');"
									+ "		if(i==0)	jsonData = jsonData + ss ;"
									+ "		else	jsonData = jsonData + ',' + ss;"
									+ "	}                    "
									+ "	 document.getElementById('user-defined-content').style.display='none'; "
									+ "  document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/collectiveOrderQuery_cancelMergeInvoice.action?id=\"+jsonData+\"' method='post' name='formx1' style='display:none'></form>\";"
									+ "	document.formx1.submit();" + "	document.getElementById('user-defined-content').innerHTML=\"\";"
									+ "} else if(m.length>1){" + "Ext.MessageBox.alert('错误', '只能选择一条数据');}else {                    "
									+ "Ext.MessageBox.alert('错误', '请至少选择一条数据');  " + "}}             ");
			/*this
					.getGridConfig()
					.addMenuScript(
							this.getText("发票信息冲红"),
							"{var m = grid.getSelections();  "
									+ "if(m.length==1){	         "
									+ "	var jsonData = '';       "
									+ "	var bId = '';       "
									+ "	for(var i = 0, len = m.length; i < len; i++){"
									+ "		if(m[i].get('combobox_peymentMethod')=='预付费账户支付'){alert('预付费账户支付不能申请发票冲红');return};"
									// + "
									// if(m[i].get('isinvoice')=='isinvoiced'){alert('发票未开具，不能申请发票冲红');return};"
									+ "     var ss =  m[i].get('id');"
									+ "		if(i==0)	jsonData = jsonData + ss ;"
									+ "		else	jsonData = jsonData + ',' + ss;"
									+ "	}                    "
									+ "	 document.getElementById('user-defined-content').style.display='none'; "
									+ "  document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/collectiveOrderQuery_toInvoiceReset.action?id=\"+jsonData+\"' method='post' name='formx1' style='display:none'></form>\";"
									+ "	document.formx1.submit();" + "	document.getElementById('user-defined-content').innerHTML=\"\";"
									+ "} else if(m.length>1){" + "Ext.MessageBox.alert('错误', '只能选择一条数据');}else {                    "
									+ "Ext.MessageBox.alert('错误', '请至少选择一条数据');  " + "}}             ");*/

		}

	}

	/**
	 * 重写框架方法--列更新
	 * 
	 * @author linjie
	 * @return
	 */
	public Map updateColumn() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Map map = new HashMap();
		String msg = "";
		String action = this.getColumn();

		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");

			List idList = new ArrayList();
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}

			if (action.equals("FlagIsvalid.flase")) {
				try {
					// 批量关闭订单
					this.getGeneralService().closeOrderList(idList, us.getSsoUser());
				} catch (EntityException e1) {
					e1.printStackTrace();
					map.put("success", "false");
					map.put("info", e1.getMessage());
					return map;
				} catch (Exception e) {
					e.printStackTrace();
					map.put("success", "false");
					map.put("info", "关闭订单出现异常！");
					return map;
				}
			}
			map.put("success", "true");
			map.put("info", "成功关闭订单！");
			return map;
		} else {
			map.put("success", "false");
			map.put("info", "请选择后再操作！");
			return map;
		}
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeBzzOrderInfo.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/collectiveOrderQuery";
	}

	public PeBzzOrderInfo getBean() {
		// TODO Auto-generated method stub
		return (PeBzzOrderInfo) super.superGetBean();
	}

	public void setBean(PeBzzOrderInfo peBzzOrderInfo) {
		// TODO Auto-generated method stub
		super.superSetBean(peBzzOrderInfo);
	}

	/**
	 * 重写框架方法：订单信息（带sql条件）
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public Page list() {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		String tableName = "( select id,fk_order_id from ("
				+ "select back.id,back.fk_order_id from pr_bzz_tch_stu_elective_back back union  all "
				+ "select history.id,history.fk_order_id from elective_history history ) )";

		String sql = "";

		if (flag.equals("viewDetail")) {
			String peSql = "select pem.fk_enterprise_id from pe_enterprise_manager pem where pem.login_id = '" + us.getLoginId() + "'";
			String peId = "";
			try {
				List list = this.getGeneralService().getBySQL(peSql);
				peId = list.get(0).toString();
			} catch (EntityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			sql = "select * from (select rownum as id,p.* from(\n" + " select --e.id          as id,\n"
					+ "       stu.true_name as trueName,\n" + "       c.code        as code,\n" + "		c.name 		  as name,\n"
					+ "       c.time        as time\n" + "  from " + tableName + " e,\n" + "       pr_bzz_tch_opencourse   op,\n"
					+ "       pe_bzz_tch_course       c,\n" + "       pe_bzz_student          stu\n" + " where e.fk_order_id = '" + id
					+ "'\n" + "   and op.id = e.fk_tch_opencourse_id\n" + "   and op.fk_course_id = c.id\n" + "   and stu.id = e.fk_stu_id"
					+ ") p) where 1=1 ";

			String sortFlag = this.getSort();
			if (!setDetail.contains(sortFlag)) {
				this.setSort("trueName");
				this.setDir("asc");
			}

		} else {
			// 处理时间的查询参数
			ActionContext context = ActionContext.getContext();
			String sqlStart = " ";
			String sqlEnd = "";
			if (context.getParameters() != null) {
				Map params = this.getParamsMap();
				if (params != null) {

					if (params.get("search__paysStartDatetime") != null) {
						String[] startDate = (String[]) params.get("search__paysStartDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							// sqlStart = " and pboi.payment_date >= to_date('"+
							// startDate[0] +"','yyyy-MM-dd')";
							sqlStart = "  and pboi.payment_date  >= to_date('" + startDate[0] + "','yyyy-MM-dd HH24:mi:ss')";
							params.remove("search__paysStartDatetime");
						}
					}

					if (params.get("search__payEndDatetime") != null) {
						String[] endDate = (String[]) params.get("search__payEndDatetime");
						if (endDate.length == 1 && !StringUtils.defaultString(endDate[0]).equals("")) {
							// sqlEnd = " and pboi.payment_date <= to_date('"+
							// endDate[0] +" 23:59:59','yyyy-MM-dd
							// HH24:mi:ss')";
							sqlEnd = "	and pboi.payment_date  <= to_date('" + endDate[0] + "','yyyy-MM-dd HH24:mi:ss')";
							params.remove("search__payEndDatetime");
						}
					}

					context.setParameters(params);
				}
			}

			sql = "\n"
					+ "select * from (select pboi.id as id,\n"
					+ "       pboi.seq as seq,\n"
					+ "       pboi.name as cname,\n"
					+ "       pboi.payer as payer,\n"
					+ "		pboi.num as num,\n"
					+ "		pboi.payment_date as paymentDate,\n"
					+ "		--pboi.create_date as createDate,\n"
					+ "       pboi.amount as amount,\n"
					+ "       ec_method.name   || '' || CASE WHEN pboi.payment_method ='10' THEN '-个人网银' WHEN pboi.payment_method ='14' THEN '-企业网银' ELSE '' END  as combobox_peymentMethod,\n"
					+ "       ec_type.name as combobox_orderType,\n"
					+ "       ec_state.name as combobox_paymentState,\n"
					+ "       decode(count(prtse.id),0,'-',count(prtse.id)) as stuNum, \n"
					+ " 		case when ec_state.code = '1' then '已支付' when ec_state.code = '2' then '已支付' when 1<>1 then 'a' else '未支付' end  as combobox_ZhiFuState,\n"
					+ "		count(refund.id) as isRefund, \n"
					+ "		decode(ec_ref.name,'','--',ec_ref.name) as combobox_refundState, \n"
					+ "		 case when pbii.id is not null then (case when pbii.flag_fp_open_state = '402880f327dc55c90127dc7131ad0001' then '已开'  when pbii.flag_fp_open_state ='402880f327dc55c90127dc71c4670002' then  '待开' else '已申请' end) else  '未申请' end  as combobox_FaPiaoState,\n"
					+ "		 pboi.merge_seq AS  MERGE_SEQ, ec_method.code as paymentCode, \n"
					+ "		to_date('2012-12-22','yyyy-mm-dd') as paysStartDatetime, \n"
					+ "		to_date('2012-12-22','yyyy-mm-dd') as payEndDatetime,  \n"
					+ "		case when pbii.id is not null then 'isinvoiced' else 'isnotinvoiced' end  as isinvoice,\n"
					+ "		pbii.flag_fp_open_state as pbiiCode \n" + "   from pe_bzz_order_info pboi\n" + " left join "
					+ tableName
					+ " prtse on pboi.id = prtse.fk_order_id\n"
					+ "  left join enum_const ec_method on pboi.flag_payment_method = ec_method.id\n"
					+ "  left join enum_const ec_state on ec_state.id = pboi.flag_payment_state\n"
					+ "  left join enum_const ec_type on ec_type.id = pboi.flag_order_type\n"
					+ "  left join enum_const ec_cls on ec_cls.namespace='ClassHourRate' and ec_cls.code='0' \n"
					+ "	left join PE_BZZ_REFUND_INFO refund on pboi.id=refund.FK_ORDER_ID \n"
					+ "	left join enum_const ec_ref on ec_ref.id = pboi.FLAG_REFUND_STATE \n"
					+ "	left join pe_bzz_invoice_info pbii on pbii.fk_order_id = pboi.id \n"
					+ "	left join enum_const ec_valid on pboi.flag_order_isvalid = ec_valid.id \n"
					+ "	inner join enum_const ec_o_type on ec_o_type.id = pboi.FLAG_PAYMENT_TYPE and ec_o_type.code = '1' \n"
					+ "  LEFT JOIN ENUM_CONST EC ON PBII.FLAG_POST_TYPE = EC.ID "
					+ " where ec_valid.code <> '0' and ec_method.code in ('0','1','4') and pboi.create_user = '"
					+ us.getId()
					+ "'\n"
					+ sqlStart
					+ sqlEnd
					+ "\n"
					+ " group by pboi.id,\n"
					+ "		   pboi.seq,\n"
					+ "          pboi.name,\n"
					+ "          pboi.payer,\n"
					+ "          pboi.merge_seq,\n"
					+ "        pboi.payment_method,\n"
					+ "		   pboi.payment_date,\n"
					+ "          pboi.amount,\n"
					+ "          pboi.create_date,\n"
					+ "          ec_method.name,\n"
					+ "          ec_state.name,\n"
					+ "		   ec_state.code,\n"
					+ "          ec_type.name,\n"
					+ "          pboi.num,\n"
					+ "          pbii.flag_fp_open_state,\n"
					+ "		   ec_cls.name, pboi.class_hour, ec_ref.name, ec_method.code, pbii.id,EC.NAME order by pboi.payment_date desc) where 1=1 ";

			/*
			 * sql = "\n" + "select * from (select pboi.id as id,\n" + "
			 * pboi.seq as seq,\n" + " pboi.name as cname,\n" + " pboi.payer as
			 * payer,\n" + " pboi.num as num,\n" + " pboi.payment_date as
			 * paymentDate,\n" + " --pboi.create_date as createDate,\n" + "
			 * pboi.amount as amount,\n" + " ec_method.name as
			 * combobox_peymentMethod,\n" + " ec_type.name as
			 * combobox_orderType,\n" + " ec_state.name as paymentState,\n" + "
			 * decode(count(prtse.id),0,'-',count(prtse.id)) as stuNum, \n" + "
			 * ec_zhifu.name as combobox_ZhiFuState, \n" + " ec_fapiao.name as
			 * combobox_FaPiaoState,\n" + " count(refund.id) as isRefund, \n" + "
			 * decode(ec_ref.name,'','--',ec_ref.name) as refundState, \n" + "
			 * case when pbii.id is not null then (case when
			 * pbii.flag_fp_open_state = '402880f327dc55c90127dc7131ad0001' then
			 * '已开' else '已申请' end) else '未申请' end as isinvoiceState,\n" + "
			 * ec_method.code as paymentCode, \n" + "
			 * to_date('2012-12-22','yyyy-mm-dd') as paysStartDatetime, \n" + "
			 * to_date('2012-12-22','yyyy-mm-dd') as payEndDatetime, \n" + "
			 * case when pbii.id is not null then 'isinvoiced' else
			 * 'isnotinvoiced' end as isinvoice,\n" + " pbii.flag_fp_open_state
			 * as pbiiCode \n" + " from pe_bzz_order_info pboi\n" + " left join " +
			 * tableName + " prtse on pboi.id = prtse.fk_order_id\n" + " left
			 * join enum_const ec_method on pboi.flag_payment_method =
			 * ec_method.id\n" + " left join enum_const ec_zhifu on
			 * pboi.FLAG_ZHIFU_STATE = ec_zhifu.id\n" + " left join enum_const
			 * ec_fapiao on pboi.FLAG_FAPIAO_STATE = ec_fapiao.id\n" + " left
			 * join enum_const ec_state on ec_state.id =
			 * pboi.flag_payment_state\n" + " left join enum_const ec_type on
			 * ec_type.id = pboi.flag_order_type\n" + " left join enum_const
			 * ec_cls on ec_cls.namespace='ClassHourRate' and ec_cls.code='0'
			 * \n" + " left join PE_BZZ_REFUND_INFO refund on
			 * pboi.id=refund.FK_ORDER_ID \n" + " left join enum_const ec_ref on
			 * ec_ref.id = pboi.FLAG_REFUND_STATE \n" + " left join
			 * pe_bzz_invoice_info pbii on pbii.fk_order_id = pboi.id \n" + "
			 * left join enum_const ec_valid on pboi.flag_order_isvalid =
			 * ec_valid.id \n" + " inner join enum_const ec_o_type on
			 * ec_o_type.id = pboi.FLAG_PAYMENT_TYPE and ec_o_type.code = '1'
			 * \n" + "\n" + " where ec_valid.code <> '0' and ec_method.code in
			 * ('0','1','4') and pboi.create_user = '" + us.getId() + "'\n" +
			 * sqlStart + sqlEnd + "\n" + " group by pboi.id,\n" + "
			 * pboi.seq,\n" + " pboi.name,\n" + " pboi.payer,\n" + "
			 * pboi.payment_date,\n" + " pboi.amount,\n" + "
			 * pboi.create_date,\n" + " ec_method.name,\n" + " ec_state.name,\n" + "
			 * ec_state.code,\n" + " ec_type.name,\n" + " pboi.num,\n" + "
			 * pbii.flag_fp_open_state,\n" + " ec_cls.name, pboi.class_hour,
			 * ec_ref.name, ec_method.code, pbii.id ,ec_zhifu.name ,
			 * ec_fapiao.name order by pboi.payment_date desc) where 1=1 ";
			 */
			String sortFlag = this.getSort();
			if (!setOrder.contains(sortFlag)) {
				this.setSort("seq");
				this.setDir("desc");
			}
		}

		StringBuffer sqlBuffer = new StringBuffer(sql);
		this.setSqlCondition(sqlBuffer);
		Page page = null;
		try {
			page = getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;

	}

	/**
	 * 跳转到回填
	 * 
	 * @return
	 */
	public String reFillout() {
		forward = this.getServletPath() + "_applyFillout.action";
		String sql = "select * from pe_bzz_order_info where num is not null and id='" + id + "'";
		try {
			List list = this.getGeneralService().getBySQL(sql);
			if (list.size() > 0) {// 如果有结果不能再次修改
				return "fail";
			} else {
				return "reFillout";
			}
		} catch (EntityException e) {
			e.printStackTrace();
			return "fail";
		}
	}

	/**
	 * 回填单据提交
	 * 
	 * @return
	 */
	public String applyFillout() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.add(Restrictions.eq("id", id));
		List list = null;
		try {
			forward = this.getServletPath() + ".action";
			list = this.getGeneralService().getList(dc);
			if (list.size() > 0) {
				PeBzzOrderInfo peBzzOrderInfo = (PeBzzOrderInfo) list.get(0);
				peBzzOrderInfo.setNum(orderNum);
				peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralService().save(peBzzOrderInfo);
				if (peBzzOrderInfo != null) {
					this.operateresult = "1";
				} else {
					this.operateresult = "2";
				}
			}
		} catch (EntityException e) {
			e.printStackTrace();
			this.operateresult = "2";
		}
		return "success";
	}

	/**
	 * 查看详情
	 * 
	 * @author linjie
	 * @return
	 */
	public String viewDeatail() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		PeBzzOrderInfo peBzzOrderInfo = null;
		try {
			peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralService().getById(this.id);
			DetachedCriteria dcInvoice = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
			dcInvoice.add(Restrictions.eq("peBzzOrderInfo", peBzzOrderInfo));
			List<PeBzzInvoiceInfo> peBzzInvoiceInfoList = this.getGeneralService().getList(dcInvoice);
			if (peBzzInvoiceInfoList != null && peBzzInvoiceInfoList.size() > 0) {
				this.peBzzInvoiceInfo = (PeBzzInvoiceInfo) peBzzInvoiceInfoList.get(0);
				peBzzOrderInfo = this.peBzzInvoiceInfo.getPeBzzOrderInfo();
				this.ssoUser = this.peBzzInvoiceInfo.getPeBzzOrderInfo().getSsoUser();
			} else {
				DetachedCriteria dcOrder = DetachedCriteria.forClass(PeBzzOrderInfo.class);
				dcOrder.createCriteria("ssoUser");
				dcOrder.add(Restrictions.eq("id", this.id));
				peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralService().getList(dcOrder).get(0);
				this.ssoUser = peBzzOrderInfo.getSsoUser();

			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		// 根据订单提交人显示学员信息
		DetachedCriteria stuDc = DetachedCriteria.forClass(PeBzzStudent.class);
		stuDc.add(Restrictions.eq("ssoUser", this.ssoUser));
		try {
			List stuList = this.getGeneralService().getList(stuDc);
			if (stuList != null && stuList.size() > 0) {
				this.peBzzStudent = (PeBzzStudent) stuList.get(0);
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 不是学员订单显示机构管理员信息
		if (this.peBzzStudent == null) {
			DetachedCriteria dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
			dc.add(Restrictions.eq("ssoUser", this.ssoUser));
			dc.createCriteria("peEnterprise", "peEnterprise");
			try {
				List list = this.getGeneralService().getList(dc);
				if (list.size() > 0) {
					this.peEnterpriseManager = (PeEnterpriseManager) list.get(0);
				}
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			this.peBzzOrderInfo = peBzzOrderInfo;
			Page page = this.getGeneralService().getOrderDetailSub(peBzzOrderInfo, null, 10000, 0);
			if (page != null) {
				this.electiveList = page.getItems();
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "viewOrderSub";
	}
	public String invoiceViewDeatail(){
		HttpServletRequest request = ServletActionContext.getRequest();
		List <PeBzzInvoiceInfo> list = null ;
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
		dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
		dc.add(Restrictions.eq("peBzzOrderInfo.id", this.id));
		try {
			list =  this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 合并订单号
		List hlist = null;
		String sql = " SELECT listagg(pboi.SEQ ,',') WITHIN GROUP (ORDER BY pboi.SEQ)FROM PE_BZZ_ORDER_INFO pboi WHERE pboi.merge_seq IN (SELECT merge_seq FROM PE_BZZ_ORDER_INFO WHERE id ='"
				+ this.id + "')";
		try {
			hlist = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (hlist != null && hlist.size() > 0) {
			if (hlist.get(0) != null) {
				this.strSeq = hlist.get(0).toString();
			}
		}
		if(null != list && list.size()>0){
			this.peBzzInvoiceInfo = (PeBzzInvoiceInfo)list.get(0);
			if(null != list.get(0).getEnumConstByInvoiceType().getCode() && "3".equals(list.get(0).getEnumConstByInvoiceType().getCode())){
				return "invoiceView1";
			}
		}else{
			this.setMsg("暂无发票信息");
			this.setTogo("javascript:window.close();");
			return "msg";
		}
		return "invoiceView";
	}
	/**
	 * 申请退费
	 * 
	 * @return
	 */
	public String applyRefund() {
		String parm = "";
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.createCriteria("enumConstByFlagPaymentMethod");
		dc.add(Restrictions.eq("id", id));
		try {
			PeBzzOrderInfo order = (PeBzzOrderInfo) this.getGeneralService().getList(dc).get(0);
			parm = order.getPaymentMethod();
			EnumConst ec = order.getEnumConstByFlagPaymentMethod();
			if (ec.getCode().equals("2") || ec.getCode().equals("3")) {
				String num = order.getNum();
				if (num == null || num.equals("")) {
					this.setMsg("线下支付，未填写单据号码，不能申请退费");
					this.setTogo(this.getServletPath() + ".action");
					return "msg";
				}
			}
			// 检查退费信息
			this.getGeneralService().checkRefundPermission(order);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			this.setMsg(e.getMessage());
			this.setTogo(this.getServletPath() + ".action");
			return "msg";
		}
		forward = this.getServletPath() + "_refundReason.action";
		if (parm != null && parm.equals("14")) {
			return "refundApply_copy";
		}
		return "refundApply";
	}

	/**
	 * 申请发票中转方法
	 * 
	 * @return
	 */
	public String toRefundApplyJsp() {
		List list = null;
		String sql = " select   ec_type.name as combobox_orderType,                        "
				+ "   ec_ref.name as COMBOBOX_REFUNDSTATE,                                                "
				+ "        count(refund.id) as isRefund,                          "
				+ "   case  when ec_state.code = '1' then    '已支付'  when ec_state.code = '2' then    '已支付'"
				+ " when 1 <> 1 then   'a'     else      '未支付'   end as combobox_ZhiFuState                        "
				+ "   from pe_bzz_order_info pboi                                           "
				+ "   left join((select distinct id,                                        "
				+ "                              fk_order_id,                               "
				+ "                              fk_tch_opencourse_id,                      "
				+ "                              fk_stu_id                                  "
				+ "                from (select back.id,                                    "
				+ "                             back.fk_order_id,                           "
				+ "                             back.fk_tch_opencourse_id,                  "
				+ "                             back.fk_stu_id                              "
				+ "                        from pr_bzz_tch_stu_elective_back back           "
				+ "                      union                                              "
				+ "                      select history.id,                                 "
				+ "                             history.fk_order_id,                        "
				+ "                             history.fk_tch_opencourse_id,               "
				+ "                             history.fk_stu_id                           "
				+ "                        from elective_history history))) prtse           "
				+ "     on pboi.id = prtse.fk_order_id                                      "
				+ "   left join enum_const ec_method                                        "
				+ "     on pboi.flag_payment_method = ec_method.id                          "
				+ "   left join enum_const ec_state                                         "
				+ "     on ec_state.id = pboi.flag_payment_state                            "
				+ "   left join enum_const ec_type                                          "
				+ "     on ec_type.id = pboi.flag_order_type                                "
				+ "   left join enum_const ec_cls                                           "
				+ "     on ec_cls.namespace = 'ClassHourRate'                               "
				+ "    and ec_cls.code = '0'                                                "
				+ "   left join PE_BZZ_REFUND_INFO refund                                   "
				+ "     on pboi.id = refund.FK_ORDER_ID                                     "
				+ "   left join enum_const ec_ref                                           "
				+ "     on ec_ref.id = pboi.FLAG_REFUND_STATE                               "
				+ "   left join pe_bzz_invoice_info pbii                                    "
				+ "     on pbii.fk_order_id = pboi.id                                       "
				+ "   left join enum_const ec_valid                                         "
				+ "     on pboi.flag_order_isvalid = ec_valid.id                            "
				+ "  inner join enum_const ec_o_type                                        "
				+ "     on ec_o_type.id = pboi.FLAG_PAYMENT_TYPE                            "
				+ "    and ec_o_type.code = '1'                                             "
				+ "  where ec_valid.code <> '0'                                             "
				+ "    and ec_method.code in ('0', '1', '4')                                " + "    and pboi.id = '" + this.getId()
				+ "'                                     " + "  group by pboi.id,                                                      "
				+ "           pboi.seq,                                                     "
				+ "           pboi.name,                                                    "
				+ "           pboi.payer,                                                   "
				+ "           pboi.payment_date,                                            "
				+ "           pboi.amount,                                                  "
				+ "           pboi.create_date,                                             "
				+ "           ec_method.name,                                               "
				+ "           ec_state.name,                                                "
				+ "           ec_state.code,                                                "
				+ "           ec_type.name,                                                 "
				+ "           pboi.num,                                                     "
				+ "           pbii.flag_fp_open_state,                                      "
				+ "           ec_cls.name,                                                  "
				+ "           pboi.class_hour,                                              "
				+ "           ec_ref.name,                                                  "
				+ "           ec_method.code,                                               "
				+ "           pbii.id                                                       ";

		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}

		if (list.size() > 0) {
			Object[] orderArray = new Object[list.size()];
			orderArray = (Object[]) list.get(0);

			if (orderArray[0].equals("购买学时订单")) {
				this.setMsg("购买学时订单不能申请退费");
				this.setTogo(this.getServletPath() + ".action");
				return "msg";
			} else if ("已退费".equals(orderArray[1])) {
				this.setMsg("已退费");
				this.setTogo(this.getServletPath() + ".action");
				return "msg";
			} else if ("已拒绝".equals(orderArray[1])) {
				this.setMsg("已拒绝。");
				this.setTogo(this.getServletPath() + ".action");
				return "msg";
			} else if (orderArray[2].toString().equals("0") && orderArray[3].equals("已支付")) {
				this.setMsg("申请退费");
				this.setTogo(this.getServletPath() + "_applyRefund.action?id=" + this.getId());
				return "msg";
			} else if (orderArray[3].equals("未支付")) {
				this.setMsg("未支付");
				this.setTogo(this.getServletPath() + ".action");
				return "msg";
			} else {
				this.setMsg("待审核");
				this.setTogo(this.getServletPath() + "_viewReason.action?id=" + this.id);
				return "msg";
			}
		}

		forward = this.getServletPath() + "_applyRefund.action?id=" + this.getId();
		return "msg";

	}

	/**
	 * 保存退费申请记录
	 */
	public String refundReason() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.add(Restrictions.eq("id", this.id));
		try {
			EnumConst enumConstByFlagRefundState = this.getEnumConstService().getByNamespaceCode("FlagRefundState", "0");
			List<PeBzzOrderInfo> orderList = this.getGeneralService().getList(dc);
			/**
			 * 订单的状态也需要置为待审核的状态
			 */
			PeBzzOrderInfo PeBzzOrderInfo = orderList.get(0);
			PeBzzOrderInfo.setEnumConstByFlagRefundState(enumConstByFlagRefundState);
			this.getGeneralService().save(PeBzzOrderInfo);

			PeBzzRefundInfo peBzzRefundInfo = new PeBzzRefundInfo();
			peBzzRefundInfo.setApplyDate(new Date());
			peBzzRefundInfo.setPeBzzOrderInfo(orderList.get(0));
			peBzzRefundInfo.setReason(this.refundReason);
			peBzzRefundInfo.setEnumConstByFlagRefundState(enumConstByFlagRefundState);
			this.getGeneralService().save(peBzzRefundInfo);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.setMsg("申请失败！");
			this.setTogo(this.getServletPath() + ".action");
		}
		this.setMsg("申请成功，请等待协会管理员审核！");
		this.setTogo(this.getServletPath() + ".action");
		return "msg";
	}

	public String viewReason() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzRefundInfo.class);
		dc.createCriteria("enumConstByFlagRefundState", "enumConstByFlagRefundState");
		dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
		dc.add(Restrictions.eq("peBzzOrderInfo.id", this.id));
		try {
			List list = this.getGeneralService().getList(dc);
			if (list.size() > 0) {
				this.peBzzRefundInfo = (PeBzzRefundInfo) list.get(0);
			} else {
				this.peBzzRefundInfo = new PeBzzRefundInfo();
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "viewReason";
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getOperateresult() {
		return operateresult;
	}

	public void setOperateresult(String operateresult) {
		this.operateresult = operateresult;
	}

	/**
	 * 申请发票
	 * 
	 * @return
	 */
	public String applyInvoice() {
		Map msgMap = new HashMap();
		Map map = new HashMap();
		String err_msg = "";
		String xml ="";
		String  result = "" ;
		String[] ids = id.split(",");
		String postType = ServletActionContext.getRequest().getParameter("postType");
		EnumConst enumConstByFlagPostType = this.getEnumConstService().getByNamespaceCode("FlagPostType", postType);
		EnumConst enumConstByFlagFpOpenState = this.getEnumConstService().getByNamespaceCode("FlagFpOpenState", "0");
		EnumConst enumConstByFlagFaPiaoState = this.getEnumConstService().getByNamespaceCode("FlagFaPiaoState", "1");
		EnumConst enumConstByInvoiceType = this.getEnumConstService().getByNamespaceCode("InvoiceType", this.invoiceType);
		if (ids != null && ids.length == 1) {
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
			dc.add(Restrictions.eq("id", ids[0]));
			this.peBzzInvoiceInfo.setEnumConstByFlagPostType(enumConstByFlagPostType);
			try {
				List<PeBzzOrderInfo> orderList = this.getGeneralService().getList(dc);
				this.peBzzInvoiceInfo.setPeBzzOrderInfo(orderList.get(0));
				this.peBzzInvoiceInfo.setEnumConstByFlagFpOpenState(enumConstByFlagFpOpenState);
				orderList.get(0).setEnumConstByFlagFaPiaoState(enumConstByFlagFaPiaoState);
				this.peBzzInvoiceInfo.setEnumConstByInvoiceType(enumConstByInvoiceType);
				this.peBzzInvoiceInfo.setCreateDate(new Date());
				msgMap.put("seq", orderList.get(0).getSeq());
				msgMap.put("bussiness", "EInvDataSave");
				msgMap.put("fplx", "0");
				msgMap.put("invoice", this.peBzzInvoiceInfo);
				msgMap.put("amount", orderList.get(0).getAmount());
				msgMap.put("yddbh", orderList.get(0).getSeq());
				msgMap.put("business", "EInvDataSave");
				xml = XMLFileUtil.getXMLFile(msgMap);
				try{
				WebServiceInvoker wsi = new WebServiceInvoker();
				map =XMLFileUtil.getResult( wsi.client(xml));
				}catch(Exception e){
					err_msg ="申请失败，请稍后再试";
				}finally{
					this.setMsg(err_msg);
					if (!"".equals(err_msg)) {
						this.setTogo(this.getServletPath() + ".action");
						return "msg";
						}
					}
				result = map.get("returnCode").toString();
				List<Map<String, String>> list = (List)map.get("returnInfo");
				List<Map<String, String>> list1 = (List)map.get("invoiceCode");
				String contentReturnCode = "";
				String invoiceNum ="";
				if(list != null && list.size() > 0) {
					contentReturnCode = list.get(0).get("returnCode");
					invoiceNum = list.get(0).get("fplsh");
				}
				if(null != result && !"".equals(result)){
					if("0000".equals(result)){
						if(null != contentReturnCode && "0000".equals(contentReturnCode) ){
							this.peBzzInvoiceInfo.setEnumConstByFlagFpOpenState(enumConstByFlagFpOpenState);
							this.peBzzInvoiceInfo.setCount("0");
							this.peBzzInvoiceInfo.setInvoiceNum(invoiceNum);
							this.getGeneralService().save(this.peBzzInvoiceInfo);
							this.setMsg("发票申请成功");
							this.setTogo(this.getServletPath() + ".action");
						}else{
							this.setMsg("数据保存失败！");
							this.setTogo(this.getServletPath() + ".action");
						}
					}else{
						this.setMsg("调用接口失败！");
						this.setTogo(this.getServletPath() + ".action");
					}
				}
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				this.setMsg("申请失败！");
				this.setTogo(this.getServletPath() + ".action");
				return "msg";
			}
			return "msg";

		} else if (ids != null && ids.length > 1) { // 合并申请发票
			String mergeOrder = this.getMergeId();
			double amount = this.getMergeAmount(this.id);
			this.peBzzInvoiceInfo.setEnumConstByFlagPostType(enumConstByFlagPostType);
			this.peBzzInvoiceInfo.setEnumConstByFlagFpOpenState(enumConstByFlagFpOpenState);
			this.peBzzInvoiceInfo.setEnumConstByInvoiceType(enumConstByInvoiceType);
			msgMap.put("seq", mergeOrder);
			msgMap.put("bussiness", "EInvDataSave");
			msgMap.put("fplx", "0");
			msgMap.put("invoice", this.peBzzInvoiceInfo);
			msgMap.put("amount", amount);
			msgMap.put("yddbh", mergeOrder);
			msgMap.put("business", "EInvDataSave");
			xml = XMLFileUtil.getXMLFile(msgMap);
			try{
				WebServiceInvoker wsi = new WebServiceInvoker();
				map =XMLFileUtil.getResult( wsi.client(xml));
				}catch(Exception e){
					err_msg ="申请失败，请稍后再试";
				}finally{
					this.setMsg(err_msg);
					if (!"".equals(err_msg)) {
						this.setTogo(this.getServletPath() + ".action");
						return "msg";
						}
					}
			result = map.get("returnCode").toString();
			List<Map<String, String>> list = (List)map.get("returnInfo");
			String contentReturnCode = "";
			String invoiceNum ="";
			if(list != null && list.size() > 0) {
				contentReturnCode = list.get(0).get("returnCode");
				invoiceNum = list.get(0).get("fplsh");
			}
			
			if(null != result && "0000".equals(result)){
				if(null != contentReturnCode && "0000".equals(contentReturnCode)){
					this.margeOrder(id,mergeOrder);
					for (int i = 0; i < ids.length; i++) {
						PeBzzInvoiceInfo pbii = new PeBzzInvoiceInfo();
						pbii.setAddress(this.peBzzInvoiceInfo.getAddress());
						pbii.setAddressee(this.peBzzInvoiceInfo.getAddressee());
						pbii.setCity(this.peBzzInvoiceInfo.getCity());
						pbii.setCreateDate(pbii.getCreateDate());
						pbii.setEnumConstByFlagFpOpenState(this.peBzzInvoiceInfo.getEnumConstByFlagFpOpenState());
						pbii.setEnumConstByFlagPostType(this.peBzzInvoiceInfo.getEnumConstByFlagPostType());
						pbii.setEnumConstByflagPrintStatus(this.peBzzInvoiceInfo.getEnumConstByflagPrintStatus());
						pbii.setEnumConstByInvoiceType(this.peBzzInvoiceInfo.getEnumConstByInvoiceType());
						pbii.setNum(this.peBzzInvoiceInfo.getNum());
						pbii.setOpenDate(this.peBzzInvoiceInfo.getOpenDate());
						pbii.setPeBzzOrderInfo(this.peBzzInvoiceInfo.getPeBzzOrderInfo());
						pbii.setPhone(this.peBzzInvoiceInfo.getPhone());
						pbii.setProvince(this.peBzzInvoiceInfo.getProvince());
						pbii.setTitle(this.peBzzInvoiceInfo.getTitle());
						pbii.setZipCode(this.peBzzInvoiceInfo.getZipCode());
						pbii.setFplx(peBzzInvoiceInfo.getFplx());
						pbii.setGmfnsrsbh(peBzzInvoiceInfo.getGmfnsrsbh());
						pbii.setGmfdz(peBzzInvoiceInfo.getGmfdz());
						pbii.setGmfdh(peBzzInvoiceInfo.getGmfdh());
						pbii.setGmfkhyh(peBzzInvoiceInfo.getGmfkhyh());
						pbii.setGmfyhzh(peBzzInvoiceInfo.getGmfyhzh());
						pbii.setGmfsjhm(peBzzInvoiceInfo.getGmfsjhm());
						pbii.setEmail(peBzzInvoiceInfo.getEmail());
						pbii.setCreateDate(new Date());
						pbii.setInvoiceRemark(peBzzInvoiceInfo.getInvoiceRemark());
						pbii.setCount("0");
						DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
						dc.add(Restrictions.eq("id", ids[i].trim()));
						pbii.setEnumConstByFlagPostType(enumConstByFlagPostType);
						// this.peBzzOrderInfo.setEnumConstByFlagFaPiaoState(enumConstByFlagFaPiaoState);
						try {
							List<PeBzzOrderInfo> orderList = this.getGeneralService().getList(dc);
							pbii.setPeBzzOrderInfo(orderList.get(0));
							pbii.setEnumConstByFlagFpOpenState(enumConstByFlagFpOpenState);
							orderList.get(0).setEnumConstByFlagFaPiaoState(enumConstByFlagFaPiaoState);
							pbii.setEnumConstByInvoiceType(enumConstByInvoiceType);
							pbii.setInvoiceNum(invoiceNum);
							this.getGeneralService().save(pbii);
						} catch (EntityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							this.setMsg("发票申请失败！");
							this.setTogo(this.getServletPath() + ".action");
						}
					}
					this.setMsg("发票申请成功");
				}else{
					this.setMsg("发票申请失败");	
				}
			}else{
				this.setMsg("发票申请失败");
			}
			
			this.setTogo(this.getServletPath() + ".action");
			return "msg";
		} else {
			this.setMsg("发票申请失败");
			return "msg";
		}

	}

	/**
	 * 申请发票中转方法
	 * 
	 * @return
	 */
	public String toInvoiceApplyJsp() {
		
		getInvoice();// 获得最后一次发票信息
		String[] ids = this.id.split(",");
		List<PeBzzOrderInfo> peList = null;
		DetachedCriteria order = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		order.add(Restrictions.in("id", ids));
		double amount = 0 ;
		try {
			peList = this.getGeneralService().getList(order);
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (peList != null && peList.size() > 0) {
			for (PeBzzOrderInfo info : peList) {
				amount += Double.parseDouble(info.getAmount());
				if(info.getPaymentDate() != null){
					if(info.getPaymentDate().getYear() != new Date().getYear()){
						this.setMsg("订单跨年不能申请发票，如有疑问请联系咨询中心");
						this.setTogo(this.getServletPath() + ".action");
						return "msg";
					}
				}
				if (info.getPaymentDate() != null) {
					if (!validate(info.getPaymentDate())) {
						this.setMsg("订单支付7天内不能申请发票");
						this.setTogo(this.getServletPath() + ".action");
						return "msg";
					}
				}
				if( info.getEnumConstByFlagPaymentState() != null && !info.getEnumConstByFlagPaymentState().getCode().equals("1")){
					this.setMsg("未支付订单不能申请发票");
					this.setTogo(this.getServletPath() + ".action");
					return "msg";
				}
			}
			if(ids.length >1 && amount >100000){
				this.setMsg("合并订单总金额不能大于100000");
				this.setTogo(this.getServletPath() + ".action");
				return "msg";
			}
		}
		if (this.id != null && this.id.split(",").length == 1) {
			this.amount =peList.get(0).getAmount();
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzRefundInfo.class);
			dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
			dc.add(Restrictions.eq("peBzzOrderInfo.id", this.id));
			List<PeBzzRefundInfo> list = null;
			try {
				list = this.getGeneralService().getList(dc);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PeBzzRefundInfo r = null;

			if (list != null) {
				if (list.size() > 0) {
					r = (PeBzzRefundInfo) list.get(0);
					EnumConst ec = r.getEnumConstByFlagRefundState();
					if (!ec.getCode().equals("2")) {
						this.setMsg("订单已退费或正在等待审核，不能申请发票");
						this.setTogo(this.getServletPath() + ".action");
						return "msg";
					}
				}
			}

			forward = this.getServletPath() + "_applyInvoice.action";
			return "invoiceApply";
		} else if (this.id != null && this.id.split(",").length > 1) {
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzRefundInfo.class);
			dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
			dc.add(Restrictions.eq("peBzzOrderInfo.id", this.id));
			List<PeBzzRefundInfo> list = null;
			List orderList = null;
			try {
				list = this.getGeneralService().getList(dc);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PeBzzRefundInfo r = null;
			if (list != null) {
				if (list.size() > 0) {
					r = (PeBzzRefundInfo) list.get(0);
					EnumConst ec = r.getEnumConstByFlagRefundState();
					if (!ec.getCode().equals("2")) {
						this.setMsg("订单已退费或正在等待审核，不能申请发票");
						this.setTogo(this.getServletPath() + ".action");
						return "msg";
					}
				}
			}
			String sql = "SELECT SUM(amount) ,listagg(SEQ ,',') WITHIN GROUP (ORDER BY SEQ) FROM PE_BZZ_ORDER_INFO WHERE id IN ("
					+ this.getCondition(this.id, ",") + ")";
			try {
				orderList = this.getGeneralService().getBySQL(sql);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Object[] obj = (Object[]) orderList.get(0);
			this.amount = obj[0].toString();
			this.strSeq = obj[1].toString();
			forward = this.getServletPath() + "_applyInvoice.action";
			return "invoiceApply";
		} else {
			this.setMsg("无效的订单");
			return "msg";
		}
	}

	/**
	 * 发票信息冲红跳转
	 * 
	 */
	public String toInvoiceReset() {
		getInvoice(this.id);// 获得最后一次发票信息
		List<PeBzzInvoiceInfo> list = null;
		List resetList = null;
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
		dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
		dc.add(Restrictions.eq("peBzzOrderInfo.id", this.id));
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list != null && list.size() > 0) {
			if (!list.get(0).getEnumConstByFlagFpOpenState().getId().equals("402880f327dc55c90127dc7131ad0001")) {
				this.setMsg("非已开发票无法申请冲红");
				this.setTogo(this.getServletPath() + ".action");
				return "msg";
			}
			if (!list.get(0).getEnumConstByInvoiceType().getId().equals("4028808c1e1f9f08011e1fa750cc000dx")) {
				this.setMsg("非增值税普通电子发票，不能申请冲红");
				this.setTogo(this.getServletPath() + ".action");
				return "msg";
			}
			String sql = "SELECT * FROM PE_BZZ_INVOICE_RESET WHERE FK_INVOICE_ID ='" + list.get(0).getId() + "'";
			try {
				resetList = this.getGeneralService().getBySQL(sql);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (resetList != null && resetList.size() > 0) {
				this.setMsg("发票信息已经申请冲红，不能重复申请");
				this.setTogo(this.getServletPath() + ".action");
				return "msg";
			}

		} else {
			this.setMsg("未申请发票，不能进行发票信息冲红");
			this.setTogo(this.getServletPath() + ".action");
			return "msg";
		}
		forward = this.getServletPath() + "_applyInvoiceReset.action";
		return "invoiceReset";
	}

	/**
	 * 发票信息冲红
	 */
	public String applyInvoiceReset() {
		Map map = new HashMap();
		Map msgMap = new HashMap();
		String result = "";
		String xml ="";
		String seq ="";
		EnumConst enumConstByFlagFpOpenState = this.getEnumConstService().getByNamespaceCode("FlagFpOpenState", "0");
		EnumConst enumConstByInvoiceType = this.getEnumConstService().getByNamespaceCode("InvoiceType","3");
		this.peBzzInvoiceInfo.setEnumConstByInvoiceType(enumConstByInvoiceType);
		this.peBzzInvoiceInfo.setEnumConstByFlagFpOpenState(enumConstByFlagFpOpenState);
		String[] ids = this.id.split(",");
		if (ids != null && ids.length == 1) {
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
			dc.add(Restrictions.eq("id", ids[0]));
			try {
				List<PeBzzOrderInfo> orderList = this.getGeneralService().getList(dc);
				msgMap.put("seq", null != orderList.get(0).getMergeSeq() ? orderList.get(0).getMergeSeq() :orderList.get(0).getSeq());
				msgMap.put("bussiness", "EInvDataSave");
				msgMap.put("fplx", "1");
				msgMap.put("invoice", this.peBzzInvoiceInfo);
				msgMap.put("amount", this.getResetAmount(this.id));
				msgMap.put("yddbh",  null != orderList.get(0).getMergeSeq() ? orderList.get(0).getMergeSeq() :orderList.get(0).getSeq());
				msgMap.put("business", "EInvDataSave");
				xml = XMLFileUtil.getXMLFile(msgMap);
				WebServiceInvoker wsi = new WebServiceInvoker();
				map =XMLFileUtil.getResult( wsi.client(xml));
				result = map.get("returnCode").toString();
				List<Map<String, String>> list = (List)map.get("returnInfo");
				List<Map<String, String>> list1 = (List)map.get("invoiceCode");
				String contentReturnCode = "";
				String invoiceNum ="";
				if(list != null && list.size() > 0) {
					contentReturnCode = list.get(0).get("returnCode");
					invoiceNum = list.get(0).get("fplsh");
				}
				if(null !=result && "0000".equals("result")){
					if(null != contentReturnCode && "0000".equals("contentReturnCode")){
						this.peBzzInvoiceInfo.setPeBzzOrderInfo(orderList.get(0));						
						this.peBzzInvoiceInfo.setCreateDate(new Date());
						this.peBzzInvoiceInfo.setInvoiceNum(invoiceNum);
						this.getGeneralService().save(this.peBzzInvoiceInfo);
						invoiceReset(this.id);
						this.setTogo(this.getServletPath() + ".action");
						this.setMsg("申请冲红成功");
						return "msg";
					}else{
						this.setMsg("申请冲红失败");
						this.setTogo(this.getServletPath() + ".action");
						return "msg";					
						}
				}else{
					this.setMsg("申请冲红失败");
					this.setTogo(this.getServletPath() + ".action");
					return "msg";		
				}
			}catch(EntityException e){
				e.printStackTrace();
			}
		}else{
			this.setMsg("至少选择一条记录");
			this.setTogo(this.getServletPath() + ".action");
			return "msg";		
		}
		this.setMsg("申请冲红失败");
		this.setTogo(this.getServletPath() + ".action");
		return "msg";		
		
	}
	
	public void invoiceReset(String id){
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		List<PeBzzOrderInfo> list = null;
		List<PeBzzInvoiceInfo> listS = null;
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.add(Restrictions.eq("id", id));
	
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (list != null && list.size() > 0 && list.get(0).getMergeSeq() != null) {
			DetachedCriteria dc1 = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
			dc1.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
			dc1.add(Restrictions.eq("peBzzOrderInfo.mergeSeq", list.get(0).getMergeSeq()));
			try {
				listS = this.getGeneralService().getList(dc1);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (listS != null && listS.size() > 0) {
			for (int i = 0; i < listS.size(); i++) {
				String sql = "insert into PE_BZZ_INVOICE_RESET (ID ,FK_INVOICE_ID,OPERATOR,CREATE_DATE,STATUS ,REMARK) VALUES(sys_guid(),'"
						+ listS.get(i).getId() + "'," + "'" + us.getId() + "',sysdate,'01','" + this.resetRemark + "')";
				try {
					this.getGeneralService().executeBySQL(sql);
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 冲红发票金额
	 * 
	 * */
	public double getResetAmount(String id) {
		DecimalFormat df = new DecimalFormat("#.00");
		double amount = 0;
		String[] ids = id.split(",");
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
			dc.add(Restrictions.eq("id", ids[0].trim()));
			try {
				List<PeBzzOrderInfo> list = this.getGeneralService().getList(dc);
				if(null != list && list.size() >0){
					DetachedCriteria dc1 = DetachedCriteria.forClass(PeBzzOrderInfo.class);
					dc1.add(Restrictions.eq("mergeSeq", list.get(0).getMergeSeq()));
					List<PeBzzOrderInfo> allList = this.getGeneralService().getList(dc1);
					for(PeBzzOrderInfo pboi :allList){
						amount += Double.parseDouble(pboi.getAmount());
						}
					}
					
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return Double.parseDouble(df.format(amount));
	}
	/**
	 * 发票信息修改跳转
	 */
	public String toInvoiceUpdate() {
		getInvoice(this.id);
		//getInvoice();// 获得最后一次发票信息
		List<PeBzzInvoiceInfo> list = null;
		List resetList = null;
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
		dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
		dc.add(Restrictions.eq("peBzzOrderInfo.id", this.id));
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list != null && list.size() > 0) {
			if (list.get(0).getEnumConstByFlagFpOpenState().getCode().equals("1")){
				this.setMsg("发票已开。如需修改或重开发票，请致电远程培训咨询服务电话010-62415115。");
				this.setTogo(this.getServletPath() + ".action");
				return "msg";
			}
			if (!list.get(0).getEnumConstByFlagFpOpenState().getId().equals("402880f327dc55c90127dc71c4670002")) {
				this.setMsg("非待开发票无法申请发票信息修改");
				this.setTogo(this.getServletPath() + ".action");
				return "msg";
			}
			if (!list.get(0).getEnumConstByInvoiceType().getId().equals("4028808c1e1f9f08011e1fa750cc000ex")) {
				this.setMsg("非增值税专用发票，不能申请发票信息修改");
				this.setTogo(this.getServletPath() + ".action");
				return "msg";
			}
		} else {
			this.setMsg("未申请发票，不能进行发票信息修改");
			this.setTogo(this.getServletPath() + ".action");
			return "msg";
		}
		forward = this.getServletPath() + "_invoiceUpdate.action";
		return "invoiceUpdate";
	}
	public String invoiceUpdate(){
		Map msgMap = new HashMap();
		Map map = new HashMap();
		String xml ="";
		String result ="";
		EnumConst enumConstByFlagFpOpenState = this.getEnumConstService().getByNamespaceCode("FlagFpOpenState", "0");
		EnumConst enumConstByInvoiceType = this.getEnumConstService().getByNamespaceCode("InvoiceType","4");
		this.peBzzInvoiceInfo.setEnumConstByInvoiceType(enumConstByInvoiceType);
		this.peBzzInvoiceInfo.setEnumConstByFlagFpOpenState(enumConstByFlagFpOpenState);
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.add(Restrictions.eq("id", this.id));
		List<PeBzzOrderInfo> orderList = null;
		try {
			orderList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(null != orderList && orderList.size()>0){
			msgMap.put("seq", null != orderList.get(0).getMergeSeq() ? orderList.get(0).getMergeSeq() :orderList.get(0).getSeq());
			msgMap.put("bussiness", "InvDataUpdate");
			msgMap.put("fplx", "0");
			msgMap.put("invoice", this.peBzzInvoiceInfo);
			msgMap.put("amount", this.getMergeAmount(this.id,"update"));
			msgMap.put("yddbh",  null != orderList.get(0).getMergeSeq() ? orderList.get(0).getMergeSeq() :orderList.get(0).getSeq());
			msgMap.put("business", "InvDataUpdate");
			xml = XMLFileUtil.getXMLFile(msgMap);
			WebServiceInvoker wsi = new WebServiceInvoker();
			map =XMLFileUtil.getResult( wsi.client(xml));
			result = map.get("returnCode").toString();
	
			if(null != result && "0000".equals(result)){
	
					try {
						
						if(null != orderList.get(0).getMergeSeq()){
							DetachedCriteria dc2 = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
							dc2.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
							dc2.add(Restrictions.eq("peBzzOrderInfo.mergeSeq",orderList.get(0).getMergeSeq()));
							List<PeBzzInvoiceInfo> allOrderList = null;
							allOrderList = this.getGeneralService().getList(dc2);
							for(PeBzzInvoiceInfo pbii :allOrderList){
								pbii.setAddress(this.peBzzInvoiceInfo.getAddress());
								pbii.setAddressee(this.peBzzInvoiceInfo.getAddressee());
								pbii.setCity(this.peBzzInvoiceInfo.getCity());
								pbii.setCreateDate(pbii.getCreateDate());
								pbii.setEnumConstByFlagFpOpenState(this.peBzzInvoiceInfo.getEnumConstByFlagFpOpenState());
								pbii.setEnumConstByInvoiceType(this.peBzzInvoiceInfo.getEnumConstByInvoiceType());
								pbii.setNum(this.peBzzInvoiceInfo.getNum());
								pbii.setOpenDate(this.peBzzInvoiceInfo.getOpenDate());
								pbii.setPeBzzOrderInfo(pbii.getPeBzzOrderInfo());
								pbii.setPhone(this.peBzzInvoiceInfo.getPhone());
								pbii.setProvince(this.peBzzInvoiceInfo.getProvince());
								pbii.setTitle(this.peBzzInvoiceInfo.getTitle());
								pbii.setZipCode(this.peBzzInvoiceInfo.getZipCode());
								pbii.setFplx(peBzzInvoiceInfo.getFplx());
								pbii.setGmfnsrsbh(peBzzInvoiceInfo.getGmfnsrsbh());
								pbii.setGmfdz(peBzzInvoiceInfo.getGmfdz());
								pbii.setGmfdh(peBzzInvoiceInfo.getGmfdh());
								pbii.setGmfkhyh(peBzzInvoiceInfo.getGmfkhyh());
								pbii.setGmfyhzh(peBzzInvoiceInfo.getGmfyhzh());
								pbii.setGmfsjhm(peBzzInvoiceInfo.getGmfsjhm());
								pbii.setEmail(peBzzInvoiceInfo.getEmail());
								pbii.setCreateDate(new Date());
								pbii.setInvoiceRemark(peBzzInvoiceInfo.getInvoiceRemark());
								this.getGeneralService().save(pbii);
							}
						}else{
							DetachedCriteria dc3 = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
							dc3.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
							dc3.add(Restrictions.eq("peBzzOrderInfo.id",orderList.get(0).getId()));
							List<PeBzzInvoiceInfo> sinOrderList = null;
							sinOrderList = this.getGeneralService().getList(dc3);
							PeBzzInvoiceInfo invoice= sinOrderList.get(0);
							invoice.setAddress(this.peBzzInvoiceInfo.getAddress());
							invoice.setAddressee(this.peBzzInvoiceInfo.getAddressee());
							invoice.setCity(this.peBzzInvoiceInfo.getCity());
							invoice.setCreateDate(new Date());
							invoice.setEnumConstByFlagFpOpenState(this.peBzzInvoiceInfo.getEnumConstByFlagFpOpenState());
							invoice.setEnumConstByInvoiceType(this.peBzzInvoiceInfo.getEnumConstByInvoiceType());
							invoice.setNum(this.peBzzInvoiceInfo.getNum());
							invoice.setOpenDate(this.peBzzInvoiceInfo.getOpenDate());
							invoice.setPeBzzOrderInfo(sinOrderList.get(0).getPeBzzOrderInfo());
							invoice.setPhone(this.peBzzInvoiceInfo.getPhone());
							invoice.setProvince(this.peBzzInvoiceInfo.getProvince());
							invoice.setTitle(this.peBzzInvoiceInfo.getTitle());
							invoice.setZipCode(this.peBzzInvoiceInfo.getZipCode());
							invoice.setFplx(peBzzInvoiceInfo.getFplx());
							invoice.setGmfnsrsbh(peBzzInvoiceInfo.getGmfnsrsbh());
							invoice.setGmfdz(peBzzInvoiceInfo.getGmfdz());
							invoice.setGmfdh(peBzzInvoiceInfo.getGmfdh());
							invoice.setGmfkhyh(peBzzInvoiceInfo.getGmfkhyh());
							invoice.setGmfyhzh(peBzzInvoiceInfo.getGmfyhzh());
							invoice.setGmfsjhm(peBzzInvoiceInfo.getGmfsjhm());
							invoice.setEmail(peBzzInvoiceInfo.getEmail());
							invoice.setCreateDate(new Date());
							invoice.setInvoiceRemark(peBzzInvoiceInfo.getInvoiceRemark());
							this.getGeneralService().save(invoice);
						}
					} catch (EntityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.setMsg("发票信息修改成功");
					this.setTogo(this.getServletPath() + ".action");
					return "msg";
				}
		}
	
		this.setMsg("发票信息修改失败");
		this.setTogo(this.getServletPath() + ".action");
		return "msg";
	}

	/**
	 * 取消发票申请
	 */
	public String cancelMergeInvoice() {
		getInvoice();// 获得最后一次发票信息
		List<PeBzzInvoiceInfo> list = null;
		List<PeBzzOrderInfo> listOrder = null;
		List<PeBzzOrderInfo> listOrderAll = null;
		List resetList = null;
		Map msgMap = new HashMap();
		Map map = new HashMap();
		String xml = "";
		String result ="";
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
		dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
		dc.add(Restrictions.eq("peBzzOrderInfo.id", this.id));
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list != null && list.size() > 0) {
			if (!list.get(0).getEnumConstByFlagFpOpenState().getId().equals("402880f327dc55c90127dc71c4670002")) {
				this.setMsg("非待开发票无法申请取消发票");
				this.setTogo(this.getServletPath() + ".action");
				return "msg";
			}
			if (!list.get(0).getEnumConstByInvoiceType().getId().equals("4028808c1e1f9f08011e1fa750cc000ex")) {
				this.setMsg("非增值税专用发票，不能申请取消发票");
				this.setTogo(this.getServletPath() + ".action");
				return "msg";
			}
			
		} else {
			this.setMsg("未申请发票，不能申请取消发票");
			this.setTogo(this.getServletPath() + ".action");
			return "msg";
		}

		DetachedCriteria dc1 = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc1.add(Restrictions.eq("id", this.id));
		try {
			listOrder = this.getGeneralService().getList(dc1);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msgMap.put("seq", null != listOrder.get(0).getMergeSeq() ? listOrder.get(0).getMergeSeq() :listOrder.get(0).getSeq());
		msgMap.put("bussiness", "InvDataDelete");
		msgMap.put("fplx", "0");
		msgMap.put("invoice", list.get(0));
		msgMap.put("amount", this.getMergeAmount(this.id));
		msgMap.put("yddbh",  null != listOrder.get(0).getMergeSeq() ? listOrder.get(0).getMergeSeq() :listOrder.get(0).getSeq());
		msgMap.put("business", "InvDataDelete");
		xml = XMLFileUtil.getXMLFile(msgMap);
		WebServiceInvoker wsi = new WebServiceInvoker();
		map =XMLFileUtil.getResult( wsi.client(xml));
		result = map.get("returnCode").toString();

		if(null != result && "0000".equals(result)){
			System.out.print("取消发票成功");
		}else{
			this.setMsg("取消发票失败");
			return "msg";
		}
		if (listOrder != null && listOrder.size() > 0) {
			DetachedCriteria dc2 = DetachedCriteria.forClass(PeBzzOrderInfo.class);
			if(null != listOrder.get(0).getMergeSeq() && !"".equals(listOrder.get(0).getMergeSeq())){
				dc2.add(Restrictions.eq("mergeSeq", listOrder.get(0).getMergeSeq()));
			}else{
				dc2.add(Restrictions.eq("id", this.id));
			}
			try {
				listOrderAll = this.getGeneralService().getList(dc2);
				if (listOrderAll != null && listOrderAll.size() > 0) {
					for (PeBzzOrderInfo info : listOrderAll) {
						String sql = "delete from pe_bzz_invoice_info where fk_order_id ='" + info.getId() + "'";
						String sql1 = " update pe_bzz_order_info set merge_seq ='' where id ='" + info.getId() + "'";
						this.getGeneralService().executeBySQL(sql);
						this.getGeneralService().executeBySQL(sql1);
					}

				}
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		this.setMsg("取消发票申请成功");
		return "msg";
	}

	/**
	 * 集体端再支付功能
	 * 
	 * @return
	 */
	public String continuePaymentOnline() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.add(Restrictions.eq("seq", this.merorderid));
		try {
			this.peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralService().getList(dc).get(0);
			ServletActionContext.getRequest().getSession().setAttribute("peBzzOrderInfo", peBzzOrderInfo);
			ServletActionContext.getRequest().getSession().setAttribute(this.merorderid, "continuePayment");
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "onlinePayment";
	}

	/**
	 * 在线支付确认
	 * 
	 * @author linjie
	 * @return
	 */
	public String confirmOnlinePayment() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.createCriteria("enumConstByFlagPaymentState");
		dc.add(Restrictions.eq("seq", this.merorderid));
		try {
			this.peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralService().getList(dc).get(0);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			this.setMsg("查询失败，无此订单");
			this.setTogo(this.getServletPath() + ".action");
			return "paymentMsg";
		}

		this.setMsg("学时购买成功");
		this.setTogo(this.getServletPath() + ".action");
		return "paymentMsg";
	}

	public String getMerorderid() {
		return merorderid;
	}

	public void setMerorderid(String merorderid) {
		this.merorderid = merorderid;
	}

	public PeBzzOrderInfo getPeBzzOrderInfo() {
		return peBzzOrderInfo;
	}

	public void setPeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo) {
		this.peBzzOrderInfo = peBzzOrderInfo;
	}

	public PeEnterpriseManager getPeEnterpriseManager() {
		return peEnterpriseManager;
	}

	public void setPeEnterpriseManager(PeEnterpriseManager peEnterpriseManager) {
		this.peEnterpriseManager = peEnterpriseManager;
	}

	/**
	 * */
	public String getCondition(String arg1, String arg2) {
		String condition = "";
		if (arg1 != null && arg2 != null) {
			String[] str = arg1.split(arg2);
			for (int i = 0; i < str.length; i++) {
				condition += "'" + str[i].trim() + "',";
			}
			condition = condition.substring(0, condition.length() - 1);
		}
		return condition;
	}

	/**
	 * 合并订单方法
	 */
	public String getMergeId(){
		String id = "";
		List list = null ;
		String sql = " SELECT  replace(lpad(S_PE_BZZ_merge_SEQ.nextval,9,'0'),'','0') from dual ";
		try {
			 list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		id = "H" + list.get(0);
		return id;
	}
	/**
	 * 
	 * @param orderId 勾选的订单id
	 * @param mergeSeq 合并订单号
	 * 申请发票成功后将合并订单号保存数据库中，并且将合并的订单附上想应的订单号
	 */
	public void margeOrder(String orderId,String mergeSeq) {
		List<PeBzzOrderInfo> orderList = null ;
		String []str = orderId.split(",");
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String amount = "";
		String ids = this.getCondition(orderId, ",");
		String sqlOrder = " select sum(amount) from pe_bzz_order_info where id in (" + ids + ")";
		List list1 = null;
		try {
			list1 = this.getGeneralService().getBySQL(sqlOrder);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		amount = list1.get(0).toString();
		String insertSql = "insert into PE_BZZ_ORDER_MERGE (ID ,SEQ,OPERATOR,CREATE_DATE,AMOUNT) VALUES (sys_guid(),'" + mergeSeq + "','"
				+ us.getId() + "',sysdate,'" + amount + "')";
		//String updateSql = " update PE_BZZ_ORDER_INFO SET MERGE_SEQ ='" + mergeSeq + "' WHERE ID IN (" + ids + ") ";
		try {
			this.getGeneralService().executeBySQL(insertSql);
			//this.getGeneralService().executeBySQL(updateSql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.add(Restrictions.in("id", str));
		try {
			orderList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(null != orderList && orderList.size()>0){
			for(PeBzzOrderInfo pboi :orderList){
				pboi.setMergeSeq(mergeSeq);
				try {
					this.getGeneralService().save(pboi);
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	public double getMergeAmount(String id){		
		DecimalFormat df = new DecimalFormat("#.00");
		double amount = 0;
		String []ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.add(Restrictions.eq("id", ids[i].trim()));
		try {
			List<PeBzzOrderInfo> list = this.getGeneralService().getList(dc);
			if(null != list && list.size()>0)
				amount += Double.parseDouble(list.get(0).getAmount());
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return Double.parseDouble(df.format(amount));
	}
	public double getMergeAmount(String id ,String flag){
		DecimalFormat df = new DecimalFormat("#.00");
		List<PeBzzOrderInfo> list = null ;
		List<PeBzzOrderInfo> allList = null ;
		double amount = 0 ;
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.add(Restrictions.eq("id", id));
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(null != list && list.size() >0){
			if(null ==list.get(0).getMergeSeq()){
				amount = Double.parseDouble(list.get(0).getAmount());
			}else{
				DetachedCriteria allDc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
				allDc.add(Restrictions.eq("mergeSeq", list.get(0).getMergeSeq()));
				try {
					allList = this.getGeneralService().getList(allDc);
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(null != allList && allList.size()>0){
					for(PeBzzOrderInfo info :allList){
						amount += Double.parseDouble(info.getAmount());
					}
				}
			}
		}
		return Double.parseDouble(df.format(amount));
		
	}

	/**
	 * 生成UUID
	 */
	public String createUUID() {
		return UUID.randomUUID().toString();
	}

	public boolean validate(Date payment_date) {
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(payment_date);
		calendar.add(calendar.DATE, 7);// 把日期往后增加一天.整数往后推,负数往前移动
		payment_date = calendar.getTime();
		if (date.before(payment_date)) {
			return false;
		}
		return true;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getStrSeq() {
		return strSeq;
	}

	public void setStrSeq(String strSeq) {
		this.strSeq = strSeq;
	}

	public String getResetRemark() {
		return resetRemark;
	}

	public void setResetRemark(String resetRemark) {
		this.resetRemark = resetRemark;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

}
