package com.whaty.platform.entity.web.action.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.StudentBatch;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.basic.PaymentBatchService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class PaymentBatchAction extends MyBaseAction {

	private String method = "";
	private String id = "";
	private String eid;// 机构id
	private String loginId;// 登陆id
	private PeBzzOrderInfo peBzzOrderInfo;
	private PeBzzInvoiceInfo peBzzInvoiceInfo;
	private EnumConstService enumConstService;
	private String payMethod;
	private String isInvoice;
	private List<PrBzzTchStuElective> electiveList;
	private List<PrBzzTchStuElectiveBack> electiveBackList;
	private PeBzzBatch peBzzBatch;
	private List<StudentBatch> listSb = new ArrayList<StudentBatch>();
	private PaymentBatchService paymentBatchService;
	private String merorderid; // 订单号
	private List list;
	private String discription;
	private String postType;

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getMerorderid() {
		return merorderid;
	}

	public void setMerorderid(String merorderid) {
		this.merorderid = merorderid;
	}

	public PaymentBatchService getPaymentBatchService() {
		return paymentBatchService;
	}

	public void setPaymentBatchService(PaymentBatchService paymentBatchService) {
		this.paymentBatchService = paymentBatchService;
	}

	public List<StudentBatch> getListSb() {
		return listSb;
	}

	public void setListSb(List<StudentBatch> listSb) {
		this.listSb = listSb;
	}

	public PeBzzBatch getPeBzzBatch() {
		return peBzzBatch;
	}

	public void setPeBzzBatch(PeBzzBatch peBzzBatch) {
		this.peBzzBatch = peBzzBatch;
	}

	public List<PrBzzTchStuElective> getElectiveList() {
		return electiveList;
	}

	public void setElectiveList(List<PrBzzTchStuElective> electiveList) {
		this.electiveList = electiveList;
	}

	public String getIsInvoice() {
		return isInvoice;
	}

	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice;
	}

	public PeBzzOrderInfo getPeBzzOrderInfo() {
		return peBzzOrderInfo;
	}

	public void setPeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo) {
		this.peBzzOrderInfo = peBzzOrderInfo;
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

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	/**
	 * 初始化列表
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		if (method.equals("myStudent")) {
			this.getGridConfig().setShowCheckBox(true);
			this.getGridConfig().setTitle("学员列表");
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("系统编号"), "peStudent.regNo");
			this.getGridConfig().addColumn(this.getText("姓名"), "peStudent.trueName");
			this.getGridConfig().addColumn(this.getText("证件号码"), "peStudent.cardNo");
			this.getGridConfig().addColumn(this.getText("所在机构"), "peStudent.peEnterprise.name");
			this.getGridConfig().addColumn(this.getText("支付状态"), "enumConstByFlagBatchPayState.name");
			this.getGridConfig().addColumn(this.getText("已报名课程数"), "enumConstByFlagBatchPayState.name");
			this.getGridConfig().addColumn(this.getText("已报名学时数"), "enumConstByFlagBatchPayState.name");
			this
					.getGridConfig()
					.addRenderScript(
							this.getText("查看已报名课程"),
							"{return '<a href=/entity/basic/paymentBatch.action?id="
									+ this.id
									+ "&eid='+record.data['enterpriseId']+'&loginId='+record.data['loginId']+'&method=myStudent ><font color=#0000ff ><u>查看学员</u></font></a>';}",
							"id");
			this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back();}");
		} else if (method.equals("myStudentPay")) { // 集体端查看学员
			this.getGridConfig().setTitle("学员列表");
			this.getGridConfig().addColumn(this.getText("ID"), "ID", false, true, true, "");
			this.getGridConfig().addColumn(this.getText("专项ID"), "BATCH_ID", false, false, false, "");
			this.getGridConfig().addColumn(this.getText("学员ID"), "STU_ID", false, false, false, "");
			this.getGridConfig().addColumn(this.getText("系统编号"), "reg_no");
			this.getGridConfig().addColumn(this.getText("姓名"), "name");
			this.getGridConfig().addColumn(this.getText("证件号码"), "card_no");
			this.getGridConfig().addColumn(this.getText("从事业务"), "work");
			this.getGridConfig().addColumn(this.getText("职务"), "position");
			this.getGridConfig().addColumn(this.getText("所在机构"), "ENTERPRISE_NAME");
			ColumnConfig batchPayState = new ColumnConfig(this.getText("支付状态"), "Combobox_QualificationsType", true, false, true,
					"TextField", false, 100, "");
			String sql = "SELECT a.id ,a.name FROM ENUM_CONST a WHERE a.NAMESPACE = 'FlagBatchPayState' ";
			batchPayState.setComboSQL(sql);
			this.getGridConfig().addColumn(batchPayState);
			this.getGridConfig().addColumn(this.getText("已报名课程数"), "courseNum", false, false, true, "");
			this.getGridConfig().addColumn(this.getText("已报名学时数"), "courseTime", false, false, true, "");
			this
					.getGridConfig()
					.addRenderScript(
							this.getText("查看已选课程"),
							"{return '<a href=/entity/basic/paymentBatch.action?id='+record.data['STU_ID']+'&batchId='+record.data['BATCH_ID']+'&method=myOwnCourse><font color=#0000ff ><u>查看已选课程</u></font></a>';}",
							"id");
			this
					.getGridConfig()
					.addMenuScript(
							this.getText("添加自选课程 "),
							"{var m = grid.getSelections();  "
									+ "if(m.length > 0){	         "
									+ " Ext.MessageBox.alert('提示','提示:先为学员添加自选课程，然后选中学员进行支付',function(){ "
									+ "		var jsonData = '';       "
									+ "		var bId = '';       "
									+ "		for(var i = 0, len = m.length; i < len; i++){"
									+ "			var ss =  m[i].get('STU_ID');"
									+ "			var bId =  m[i].get('BATCH_ID');"
									+ "			if(i==0)	jsonData = jsonData + ss ;"
									+ "			else	jsonData = jsonData + ',' + ss;"
									+ "		}                     "
									+ "		document.getElementById('user-defined-content').style.display='none'; "
									+ "		document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/paymentBatchAddCourse.action?id=\"+bId+\"&stuId=\"+jsonData+\"' method='post' name='formx1' style='display:none'><input type=hidden name=ids value='\"+jsonData+\"' ><input type=hidden name=id value='\"+bId+\"' ></form>\";"
									+ "		document.formx1.submit();" + "	document.getElementById('user-defined-content').innerHTML=\"\";"
									+ "});} else {                    " + " Ext.MessageBox.alert('错误', '请至少选择一条数据');  " + "}}             ");
			this
					.getGridConfig()
					.addMenuScript(
							this.getText("支付选中学员"),
							"{var m = grid.getSelections();  "
									+ "if(m.length > 0){	         "
									+ " Ext.MessageBox.alert('提示','提示:先为学员添加自选课程，然后选中学员进行支付',function(){ "
									+ "	var jsonData = '';       "
									+ "	var bId = '';       "
									+ "	for(var i = 0, len = m.length; i < len; i++){"
									+ "		var ss =  m[i].get('STU_ID');"
									+ "		var bId =  m[i].get('BATCH_ID');"
									+ "		if(i==0)	jsonData = jsonData + ss ;"
									+ "		else	jsonData = jsonData + ',' + ss;"
									+ "	}                        "
									+ "	document.getElementById('user-defined-content').style.display='none'; "
									+ "	document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/paymentBatch_toConfirmStu.action' method='post' name='formx1' style='display:none'><input type=hidden name=ids value='\"+jsonData+\"' ><input type=hidden name=id value='\"+bId+\"' ></form>\";"
									+ "	document.formx1.submit();" + "	document.getElementById('user-defined-content').innerHTML=\"\";"
									+ "});} else {                    " + "Ext.MessageBox.alert('错误', '请至少选择一条数据');  " + "}}             ");
			this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back();}");
		} else if (method.equals("addCourse")) {
			this.getGridConfig().setShowCheckBox(false);
			this.getGridConfig().setTitle("添加课程");
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("课程编号"), "peBzzTchCourse.code");
			this.getGridConfig().addColumn(this.getText("课程名称"), "peBzzTchCourse.name");
			ColumnConfig columnConfigType = new ColumnConfig(this.getText("课程性质"), "enumConstByFlagCourseType.name", true, true, true,
					"TextField", false, 100, "");
			String sql = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseType'";
			columnConfigType.setComboSQL(sql);
			this.getGridConfig().addColumn(columnConfigType);
			this.getGridConfig().addColumn(this.getText("课程单价(元)"), "peBzzTchCourse.price");
			this.getGridConfig().addColumn(this.getText("学时数"), "peBzzTchCourse.time");
			this.getGridConfig().addColumn(this.getText("主讲人"), "peBzzTchCourse.teacher");
			this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back();}");
		} else if (method.equals("myCourse")) {
			this.getGridConfig().setShowCheckBox(false);
			this.getGridConfig().setTitle("课程列表");
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("课程编号"), "peBzzTchCourse.code");
			this.getGridConfig().addColumn(this.getText("课程名称"), "peBzzTchCourse.name");
			this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back();}");
		} else if (method.equals("myMustCourse")) {// 集体端查看必选课程
			this.getGridConfig().setShowCheckBox(false);
			this.getGridConfig().setTitle("课程列表");
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("课程编号"), "code");
			this.getGridConfig().addColumn(this.getText("课程名称"), "name");
			ColumnConfig columnCourseType = new ColumnConfig(this.getText("课程性质"), "enumConstByFlagCourseType.name", true, true, true,
					"TextField", false, 100, "");
			String sql1 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseType'";
			columnCourseType.setComboSQL(sql1);
			this.getGridConfig().addColumn(columnCourseType);
			this.getGridConfig().addColumn(this.getText("课程单价(元)"), "price", false, false, true, "TextField", false, 100, "");
			this.getGridConfig().addColumn(this.getText("学时数"), "time");
			this.getGridConfig().addColumn(this.getText("主讲人"), "teacher");
			this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back();}");
		} else if (method.equals("myOwnCourse")) {// 集体端查看学员已选课程
			this.getGridConfig().setShowCheckBox(true);
			this.getGridConfig().setTitle("查看已报名课程");
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("课程编号"), "code");
			this.getGridConfig().addColumn(this.getText("课程名称"), "name");
			ColumnConfig columnCourseType = new ColumnConfig(this.getText("课程性质"), "enumConstByFlagCourseType.name", true, true, true,
					"TextField", false, 100, "");
			String sql1 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseType'";
			columnCourseType.setComboSQL(sql1);
			this.getGridConfig().addColumn(columnCourseType);
			this.getGridConfig().addColumn(this.getText("课程单价(元)"), "price", false, false, true, "TextField", false, 100, "");
			this.getGridConfig().addColumn(this.getText("学时数"), "time");
			this.getGridConfig().addColumn(this.getText("主讲人"), "teacher");
			ColumnConfig columnQualificationsType = new ColumnConfig(this.getText("建议学习人群"), "enumConstByFlagQualificationsType.name",
					true, false, true, "TextField", false, 100, "");
			String sql3 = "select a.id ,a.name from enum_const a where a.namespace='FlagQualificationsType' and name not in ('面向所有人投票','面向学员投票','面向集体管理员投票')";
			columnQualificationsType.setComboSQL(sql3);
			this.getGridConfig().addColumn(columnQualificationsType);
			ColumnConfig columnConfigCategory = new ColumnConfig(this.getText("按业务分类"), "enumConstByFlagCourseCategory.name", true, false,
					false, "TextField", false, 100, "");
			String sql2 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseCategory'";
			columnConfigCategory.setComboSQL(sql2);
			this.getGridConfig().addColumn(columnConfigCategory);
			ColumnConfig columnCourseItemType = new ColumnConfig(this.getText("按大纲分类"), "enumConstByFlagCourseItemType.name", true, false,
					false, "TextField", false, 100, "");
			String sql7 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseItemType'";
			columnCourseItemType.setComboSQL(sql7);
			this.getGridConfig().addColumn(columnCourseItemType);
			ColumnConfig columnContentProperty = new ColumnConfig(this.getText("按内容属性分类"), "enumConstByFlagContentProperty.name", true,
					false, false, "TextField", false, 100, "");
			String sql9 = "select a.id ,a.name from enum_const a where a.namespace='FlagContentProperty'";
			columnContentProperty.setComboSQL(sql9);
			this.getGridConfig().addColumn(columnContentProperty);
			ColumnConfig columnFlagChoose = new ColumnConfig(this.getText("是否自选"), "enumConstByFlagChoose.name", true, false, true,
					"TextField", false, 100, "");
			String sql10 = "select a.id ,a.name from enum_const a where a.namespace='FlagChoose'";
			columnFlagChoose.setComboSQL(sql10);
			this.getGridConfig().addColumn(columnFlagChoose);
			this.getGridConfig().addMenuFunction(this.getText("删除课程"), "deleteCourse.true");
			this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back();}");
		} else if (method.equals("myChooseCourse")) {// 集体端查看自选课程
			this.getGridConfig().setShowCheckBox(false);
			this.getGridConfig().setTitle("课程列表");
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("课程编号"), "code");
			this.getGridConfig().addColumn(this.getText("课程名称"), "name");
			ColumnConfig columnCourseType = new ColumnConfig(this.getText("课程性质"), "enumConstByFlagCourseType.name", true, true, true,
					"TextField", false, 100, "");
			String sql1 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseType'";
			columnCourseType.setComboSQL(sql1);
			this.getGridConfig().addColumn(columnCourseType);
			this.getGridConfig().addColumn(this.getText("课程单价(元)"), "price", false, false, true, "TextField", false, 100, "");
			this.getGridConfig().addColumn(this.getText("学时数"), "time");
			this.getGridConfig().addColumn(this.getText("主讲人"), "teacher");
			this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back();}");
		} else if (method.equals("myEnterprise")) {// 查看自己机构和下属二级机构，完成以及可以给下属二级支付的需要。
			this.getGridConfig().setShowCheckBox(false);
			this.getGridConfig().setTitle("选择要支付的机构");
			this.getGridConfig().addColumn(this.getText("机构ID"), "id", false, false, false, "");
			this.getGridConfig().addColumn(this.getText("登陆ID"), "loginId");
			this.getGridConfig().addColumn(this.getText("机构编号"), "enterpriseCode");
			this.getGridConfig().addColumn(this.getText("机构名称"), "enterpriseName");
			this.getGridConfig().addColumn(this.getText("专项ID"), "sid", false, false, false, "");
			this
					.getGridConfig()
					.addRenderScript(
							this.getText("查看学员"),
							"{return '<a href=/entity/basic/paymentBatch.action?id="
									+ this.id
									+ "&eid='+record.data['enterpriseId']+'&loginId='+record.data['loginId']+'&method=myStudent ><font color=#0000ff ><u>查看学员</u></font></a>';}",
							"id");
			this
					.getGridConfig()
					.addRenderScript(
							this.getText("查看课程"),
							"{return '<a href=/entity/basic/paymentBatch.action?id="
									+ this.id
									+ "&eid='+record.data['enterpriseId']+'&loginId='+record.data['loginId']+'&method=myCourse><font color=#0000ff ><u>查看课程</u></font></a>';}",
							"id");
			this.getGridConfig().addRenderScript(this.getText("支付状态"),
					"{" + this.getScriptOne() + ";return '<div id=a_'+record.data['id']+'>请稍后...</div>';}", "id");
			this
					.getGridConfig()
					.addRenderScript(
							this.getText("按机构支付"),
							"{return '<a href=/entity/basic/paymentBatch_toConfirmOrder.action?id="
									+ this.id
									+ "&eid='+record.data['enterpriseId']+'&loginId='+record.data['loginId']+'&method=myCourse target=\"_blank\"><font color=#0000ff ><u>支付</u></font></a>';}",
							"id");
			this
					.getGridConfig()
					.addRenderScript(
							this.getText("单独支付"),
							"{return '<a href=/entity/basic/paymentBatch.action?id="
									+ this.id
									+ "&eid='+record.data['enterpriseId']+'&loginId='+record.data['loginId']+'&method=myStudentPay target=\"_blank\"><font color=#0000ff ><u>选择学员</u></font></a>';}",
							"id");
			this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back();}");
		} else {// 一级集体的列表查询
			this.getGridConfig().setTitle("专项培训报名支付");
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("专项培训名称"), "name");
			this.getGridConfig().addColumn(this.getText("报名开始时间"), "startDate");
			this.getGridConfig().addColumn(this.getText("报名结束时间"), "endDate");
			this.getGridConfig().addColumn(this.getText("建议学时数"), "suggTime", false, true, true, Const.fee_for_extjs);
			this
					.getGridConfig()
					.addRenderScript(
							this.getText("查看描述"),
							"{return '<a href=/entity/basic/paymentBatch_showDiscription.action?id='+record.data['id']+'&method=myStudentPay ><font color=#0000ff ><u>查看描述</u></font></a>';}",
							"id");
			this
					.getGridConfig()
					.addRenderScript(
							this.getText("查看学员"),
							"{return '<a href=/entity/basic/paymentBatch.action?id='+record.data['id']+'&method=myStudentPay ><font color=#0000ff ><u>查看学员</u></font></a>';}",
							"id");
			this
					.getGridConfig()
					.addRenderScript(
							this.getText("查看必选课程"),
							"{return '<a href=/entity/basic/paymentBatch.action?id='+record.data['id']+'&method=myMustCourse><font color=#0000ff ><u>查看必选课程</u></font></a>';}",
							"id");
			this
					.getGridConfig()
					.addRenderScript(
							this.getText("查看自选课程"),
							"{return '<a href=/entity/basic/paymentBatch.action?id='+record.data['id']+'&method=myChooseCourse><font color=#0000ff ><u>查看自选课程</u></font></a>';}",
							"id");
		}
	}

	// 增加查看专项描述的方法
	public String showDiscription() {
		String sql = "select p.batch_note from PE_BZZ_BATCH p where p.id='" + id + "'";
		try {
			List list = this.getGeneralService().getBySQL(sql);
			if (list.size() > 0) {
				discription = list.get(0).toString();
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "showDiscription";
	}

	private StringBuffer getScriptAll() {
		StringBuffer script = new StringBuffer();
		script.append("	Ext.Ajax.request({");
		script.append(" url : '/entity/basic/paymentBatch_isAllPay.action',");
		script.append(" params : {sid:record.data['id'],topay:'all'},");
		script.append("	method : 'post',");
		script.append("	success : function(response) {");
		script.append("		var result = response.responseText; ");
		script.append("		document.getElementById('a_'+record.data['id']).innerHTML=result;");
		script.append("		");
		script.append("		} ");
		script.append("		}); ");
		return script;
	}

	private StringBuffer getScriptOne() {
		StringBuffer script = new StringBuffer();
		script.append("	Ext.Ajax.request({");
		script.append(" url : '/entity/basic/paymentBatch_isAllPay.action',");
		script.append(" params : {id:record.data['id'],loginId:record.data['loginId'],sid:record.data['sid'],topay:'one'},");
		script.append("	method : 'post',");
		script.append("	success : function(response) {");
		script.append("		var result = response.responseText; ");
		script.append("		document.getElementById('a_'+record.data['id']).innerHTML=result;");
		script.append("		");
		script.append("		} ");
		script.append("		}); ");
		return script;
	}

	public String isAllPay() {
		this.setDoLog(false);// 不保存日志
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter writer = null;
		String sid = request.getParameter("sid");
		String topay = request.getParameter("topay");
		String sresult = "";
		if ("all".equalsIgnoreCase(topay)) {
			sresult = this.getResult(sid);
		} else {
			sresult = this.getResultOne(sid);
		}

		try {
			writer = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.print(sresult);
		writer.flush();
		writer.close();
		return "grid";
	}

	public String getResult(String sid) {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sresult = "可支付";
		id = sid;
		try {
			String lid = this.loginId;
			if (lid == null || "".equals(lid)) {// 如果this.loginId为空则支付自己的专项。
				lid = us.getLoginId();
			}
			PeEnterprise peEnterprise = null;
			String enterpriseID = "";
			PeEnterpriseManager peEnterpriseManager = this.getGeneralService().getEnterpriseManagerByLoginId(lid);
			peEnterprise = peEnterpriseManager.getPeEnterprise();
			enterpriseID = peEnterprise.getId();

			// 判断是否支付
			// batchPayCheck(us, enterpriseID,sid);

			// 判断专项时间
			PeBzzBatch peBzzBatch = batchDateCheck();

			// 判断此机构下是否存在学员
			list = batchStuCheck(lid);
			// ServletActionContext.getRequest().getSession().setAttribute("studentList",
			// list);

			// 判断此专项内是否有课程
			batchCourseCheck();
		} catch (EntityException e) {
			sresult = this.exErrorMsg(e.getMessage(), 1);// 转换错误信息
		} catch (Exception ex) {
			ex.printStackTrace();
			sresult = "获取失败";

		}
		return sresult;
	}

	public String getResultOne(String sid) {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sresult = "可支付";
		id = sid;
		try {
			String lid = this.loginId;
			if (lid == null || "".equals(lid)) {// 如果this.loginId为空则支付自己的专项。
				lid = us.getLoginId();
			}
			PeEnterprise peEnterprise = null;
			String enterpriseID = "";
			PeEnterpriseManager peEnterpriseManager = this.getGeneralService().getEnterpriseManagerByLoginId(lid);
			peEnterprise = peEnterpriseManager.getPeEnterprise();
			enterpriseID = peEnterprise.getId();

			// 判断是否支付
			// batchPayCheck(us, enterpriseID, sid);

			// 判断专项时间
			peBzzBatch = batchDateCheck();

			// 判断此机构下是否存在学员
			list = batchStuCheck(lid);
			// ServletActionContext.getRequest().getSession().setAttribute("studentList",
			// list);

			// 判断此专项内是否有课程
			batchCourseCheck();
		} catch (EntityException e) {
			sresult = this.exErrorMsg(e.getMessage(), 1);// 转换错误信息
		} catch (Exception ex) {
			ex.printStackTrace();
			sresult = "获取失败";

		}
		return sresult;
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/paymentBatch";
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
		DetachedCriteria dc = null;
		DetachedCriteria expcetdc = null;

		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String lid = this.loginId;
		if (lid == null || "".equals(lid)) {// 如果this.loginId不为空则支付自己的专项。
			lid = us.getLoginId();
		}
		if (method.equals("myStudent")) {
			DetachedCriteria sdc = DetachedCriteria.forClass(PeBzzStudent.class);
			sdc.createCriteria("peEnterprise", "peEnterprise");
			dc = DetachedCriteria.forClass(StudentBatch.class);
			DetachedCriteria manDc = DetachedCriteria.forClass(PeEnterpriseManager.class);
			manDc.createCriteria("ssoUser", "ssoUser");
			manDc.createCriteria("peEnterprise", "peEnterprise");
			manDc.add(Restrictions.eq("ssoUser.loginId", lid));
			manDc.setProjection(Property.forName("peEnterprise"));
			// dc.add(Restrictions.or(Property.forName("peEnterprise").in(manDc),Property.forName("peEnterprise.peEnterprise").in(manDc)));//
			// 学员所属机构
			dc.createCriteria("peBzzBatch", "peBzzBatch");
			dc.createCriteria("peStudent", "peStudent");
			dc.createCriteria("peStudent.peEnterprise", "pe");
			dc.createCriteria("pe.peEnterprise", dc.LEFT_JOIN);
			dc.createCriteria("enumConstByFlagBatchPayState", "enumConstByFlagBatchPayState");
			// dc.setProjection(Property.forName("peStudent.id"));
			dc.add(Restrictions.or(Property.forName("peStudent.peEnterprise").in(manDc), Property.forName("pe.peEnterprise").in(manDc)));
			dc.add(Restrictions.eq("peBzzBatch.id", this.id));
			// dc.add(Property.forName("id").in(sbDc)); // 专项中存在的学员
		} else if (method.equals("addCourse")) {
			/*
			 * String[] studentIds = (String[])
			 * ServletActionContext.getRequest()
			 * .getSession().getAttribute("params_ids");
			 * 
			 * expcetdc =
			 * DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
			 * expcetdc.createCriteria("prBzzTchOpencourse",
			 * "prBzzTchOpencourse");
			 * expcetdc.add(Restrictions.eq("prBzzTchOpencourse.peBzzBatch.id",
			 * id));
			 * expcetdc.add(Restrictions.in("prBzzTchOpencourse.peBzzStudent.id",
			 * studentIds)); dc =
			 * DetachedCriteria.forClass(PeBzzTchCourse.class);
			 * dc.createCriteria("enumConstByFlagCourseType",
			 * "enumConstByFlagCourseType");
			 * dc.add(Subqueries.propertyNotIn("id", expcetdc));
			 */
			dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
			dc.createCriteria("peBzzBatch", "peBzzBatch");
			dc.createCriteria("peBzzTchCourse", "peBzzTchCourse");
			dc.createCriteria("enumConstByFlagChoose", "enumConstByFlagChoose");
			dc.add(Restrictions.eq("peBzzBatch.id", id));
			dc.add(Restrictions.eq("PrBzzTchOpencourse.enumConstByFlagChoose.code", "0"));
		} else if (method.equals("myCourse")) {
			dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
			dc.createCriteria("peBzzBatch", "peBzzBatch");
			dc.createCriteria("peBzzTchCourse", "peBzzTchCourse");
			dc.add(Restrictions.eq("peBzzBatch.id", id));
		} else if (method.equals("myMustCourse")) {

		} else if (method.equals("myChooseCourse")) {
			dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
			dc.createCriteria("peBzzBatch", "peBzzBatch");
			dc.createCriteria("peBzzTchCourse", "peBzzTchCourse");
			dc.createCriteria("enumConstByFlagChoose", "enumConstByFlagChoose", DetachedCriteria.LEFT_JOIN);
			dc.add(Restrictions.eq("enumConstByFlagChoose.code", "0"));
			dc.add(Restrictions.eq("peBzzBatch.id", id));
		} else if (method.equals("myEnterprise")) {
			// 附属集体列表用sql查，不用生成DC
		} else {
			dc = DetachedCriteria.forClass(PeBzzBatch.class);
			// dc.add(Restrictions.ne("id",
			// "40288a7b394d676d01394dad824c003b"));
			dc
					.add(Restrictions
							.sqlRestriction(" id in ( select distinct sb.batch_id" + " from"
									+ "		stu_batch             sb,"
									+ "     pe_bzz_student        pbs,"
									+ "     pe_enterprise         pe,"
									+ "     pe_enterprise_manager pem "
									+ " where sb.stu_id = pbs.id"
									+ " and pbs.fk_enterprise_id = pe.id"
									+ " and pe.id = pem.fk_enterprise_id "
									+
									// " and pem.login_id =
									// '"+us.getLoginId()+"'"+
									"  and pem.login_id in ("
									+ us.getSsoUser().getSubManagersStr()
									+ ")"
									+ // 能查看二级机构的专项
									" and sb.batch_id in (select id from pe_bzz_batch where FLAG_BATCH_TYPE =(SELECT ID FROM ENUM_CONST WHERE NAMESPACE = 'FlagBatchType' AND CODE = '0'))) and  this_.flag_deploy<>'deploy0' "));// 判断
			// 专项培训是否发布
		}
		return dc;
	}

	/**
	 * 重写框架方法：专项信息（带sql条件）
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public Page list() {
		Page page = null;
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (method.equals("myEnterprise")) {
			String sql = "select * from (" + "  select  distinct" + " sb.batch_id as id," + "    pe.id as id,"
					+ "    pem.login_id as loginId," + "    pe.code as enterpriseCode," + "    pe.name as enterpriseName,"
					+ "    sb.batch_id as sid" + " from" + "	stu_batch             sb," + "   pe_bzz_student        pbs,"
					+ "   pe_enterprise         pe," + "   pe_enterprise_manager pem " + " where sb.stu_id = pbs.id"
					+ " and pbs.fk_enterprise_id = pe.id" + " and pe.id = pem.fk_enterprise_id " + " and sb.batch_id='" + this.id + "' " +
					// " and pem.login_id = '"+us.getLoginId()+"'"+
					"  and pem.login_id in (" + us.getSsoUser().getSubManagersStr() + ")" + // 能查看二级机构的专项
					" and sb.batch_id <> '40288a7b394d676d01394dad824c003b') where 1=1 ";
			StringBuffer sb = new StringBuffer(sql);
			this.setSqlCondition(sb);
			try {
				page = this.getGeneralService().getByPageSQL(sb.toString(), Integer.parseInt(this.getLimit()),
						Integer.parseInt(this.getStart()));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (method.equals("myMustCourse")) {
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append("SELECT * ");
			sqlBuffer.append("FROM  ( select pbtc.ID,pbtc.code,                                                                          ");
			sqlBuffer.append("        pbtc.name,                                                                          ");
			sqlBuffer.append("        ec4.name flag_coursetype,                                                           ");
			sqlBuffer.append("        pbtc.price,                                                                         ");
			sqlBuffer.append("        pbtc.time,                                                                          ");
			sqlBuffer.append("        pbtc.teacher,                                                                       ");
			sqlBuffer.append("        listagg(ec2.name, ',') within GROUP(order by pbtc.id) qualificationsType            ");
			sqlBuffer.append("   from PR_BZZ_TCH_OPENCOURSE pbto                                                          ");
			sqlBuffer.append("  inner join PE_BZZ_BATCH pbb                                                               ");
			sqlBuffer.append("     on pbto.FK_BATCH_ID = pbb.ID                                                           ");
			sqlBuffer.append("  inner join PE_BZZ_TCH_COURSE pbtc                                                         ");
			sqlBuffer.append("     on pbto.FK_COURSE_ID = pbtc.ID                                                         ");
			sqlBuffer.append("   left join ENUM_CONST ec3                                                                 ");
			sqlBuffer.append("     on pbto.FLAG_CHOOSE = ec3.ID                                                           ");
			sqlBuffer.append("   left join ENUM_CONST ec4                                                                 ");
			sqlBuffer.append("     on pbtc.flag_coursetype = ec4.ID                                                       ");
			sqlBuffer.append("   left join PE_BZZ_TCH_COURSE_SUGGEST pbtcs                                                ");
			sqlBuffer.append("     on pbtc.id = pbtcs.fk_course_id                                                        ");
			sqlBuffer.append("   left join enum_const ec2                                                                 ");
			sqlBuffer.append("     on pbtcs.fk_enum_const_id = ec2.id                                                     ");
			sqlBuffer.append("  where ec3.CODE = '1'                                                                      ");
			sqlBuffer.append("    and pbb.ID = '" + id + "'                                         ");
			sqlBuffer.append("  group by pbtc.ID,pbtc.code,                                                                       ");
			sqlBuffer.append("           pbtc.name,                                                                       ");
			sqlBuffer.append("           ec4.name,                                                                        ");
			sqlBuffer.append("           pbtc.price,                                                                      ");
			sqlBuffer.append("           pbtc.time,                                                                       ");
			sqlBuffer.append("           pbtc.teacher                                                                    ) ");
			sqlBuffer.append(" where 1=1 ");
			try {
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
					if (name.equals("enumConstByFlagQualificationsType.name")) {
						name = "qualificationsType";
					}
					if (name.equals("enumConstByFlagCourseType.name")) {
						name = "courseType";
					}
					if (name.equals("qualificationsType")) {
						sqlBuffer.append(" and INSTR(qualificationsType,'" + value + "',1,1)>0");
					} else {
						sqlBuffer.append(" and " + name + " like '%" + value + "%'");
					}
				} while (true);
				page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()),
						Integer.parseInt(this.getStart()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (EntityException e) {
				e.printStackTrace();
			}
		} else if (method.equals("myChooseCourse")) {
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append("SELECT * ");
			sqlBuffer.append("FROM  ( select pbtc.ID,pbtc.code,                                                                          ");
			sqlBuffer.append("        pbtc.name,                                                                          ");
			sqlBuffer.append("        ec4.name flag_coursetype,                                                           ");
			sqlBuffer.append("        pbtc.price,                                                                         ");
			sqlBuffer.append("        pbtc.time,                                                                          ");
			sqlBuffer.append("        pbtc.teacher,                                                                       ");
			sqlBuffer.append("        listagg(ec2.name, ',') within GROUP(order by pbtc.id) qualificationsType            ");
			sqlBuffer.append("   from PR_BZZ_TCH_OPENCOURSE pbto                                                          ");
			sqlBuffer.append("  inner join PE_BZZ_BATCH pbb                                                               ");
			sqlBuffer.append("     on pbto.FK_BATCH_ID = pbb.ID                                                           ");
			sqlBuffer.append("  inner join PE_BZZ_TCH_COURSE pbtc                                                         ");
			sqlBuffer.append("     on pbto.FK_COURSE_ID = pbtc.ID                                                         ");
			sqlBuffer.append("   left join ENUM_CONST ec3                                                                 ");
			sqlBuffer.append("     on pbto.FLAG_CHOOSE = ec3.ID                                                           ");
			sqlBuffer.append("   left join ENUM_CONST ec4                                                                 ");
			sqlBuffer.append("     on pbtc.flag_coursetype = ec4.ID                                                       ");
			sqlBuffer.append("   left join PE_BZZ_TCH_COURSE_SUGGEST pbtcs                                                ");
			sqlBuffer.append("     on pbtc.id = pbtcs.fk_course_id                                                        ");
			sqlBuffer.append("   left join enum_const ec2                                                                 ");
			sqlBuffer.append("     on pbtcs.fk_enum_const_id = ec2.id                                                     ");
			sqlBuffer.append("  where ec3.CODE = '0'                                                                      ");
			sqlBuffer.append("    and pbb.ID = '" + id + "'                                         ");
			sqlBuffer.append("  group by pbtc.ID,pbtc.code,                                                                       ");
			sqlBuffer.append("           pbtc.name,                                                                       ");
			sqlBuffer.append("           ec4.name,                                                                        ");
			sqlBuffer.append("           pbtc.price,                                                                      ");
			sqlBuffer.append("           pbtc.time,                                                                       ");
			sqlBuffer.append("           pbtc.teacher                                                                    ) ");
			sqlBuffer.append(" where 1=1 ");
			try {
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

					if (name.equals("enumConstByFlagQualificationsType.name")) {
						name = "qualificationsType";
					}
					if (name.equals("enumConstByFlagCourseType.name")) {
						name = "courseType";
					}

					if (name.equals("qualificationsType")) {
						sqlBuffer.append(" and INSTR(qualificationsType,'" + value + "',1,1)>0");
					} else {
						sqlBuffer.append(" and " + name + " like '%" + value + "%'");
					}

				} while (true);
				page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()),
						Integer.parseInt(this.getStart()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (EntityException e) {
				e.printStackTrace();
			}
		} else if (method.equals("myOwnCourse")) {
			StringBuffer sqlBuffer = new StringBuffer();
			String batchId = ServletActionContext.getRequest().getParameter("batchId");
			String stuId = ServletActionContext.getRequest().getParameter("id");
			sqlBuffer.append("SELECT * ");
			sqlBuffer
					.append("FROM  (  select pbtc.id || '-' || fk_stu_id||'-'||FK_BATCH_ID id,                                                                         ");
			sqlBuffer
					.append("                               pbtc.code,                                                                        ");
			sqlBuffer
					.append("                               pbtc.name, ec_CourseType.name ec_CourseType,                                                                       ");
			sqlBuffer
					.append("                               pbtc.price,                                                                       ");
			sqlBuffer
					.append("                               pbtc.time,                                                                        ");
			sqlBuffer
					.append("                               pbtc.teacher,                                                                     ");
			sqlBuffer
					.append("                               listagg(ec2.name, ',') within GROUP(order by pbtc.id) qualificationsType,         ");
			sqlBuffer
					.append("                               ec3.name courseCategory,                                                          ");
			sqlBuffer
					.append("                               ec4.name courseItemType,                                                          ");
			sqlBuffer
					.append("                               ec5.name contentProperty,                                                         ");
			sqlBuffer
					.append("                               ec6.name flagchoose                                                               ");
			sqlBuffer
					.append("                          from pr_bzz_tch_stu_elective_back pbtseb                                               ");
			sqlBuffer
					.append("                         inner join pr_bzz_tch_opencourse pbto                                                   ");
			sqlBuffer
					.append("                            on pbtseb.fk_tch_opencourse_id = pbto.id                                             ");
			sqlBuffer
					.append("                         inner join pe_bzz_tch_course pbtc                                                       ");
			sqlBuffer
					.append("                            on pbto.fk_course_id = pbtc.id                                                       ");
			sqlBuffer
					.append("                         inner join enum_const ec1                                                               ");
			sqlBuffer
					.append("                            on pbtc.flag_coursetype = ec1.id                                                     ");
			sqlBuffer
					.append("                          left join PE_BZZ_TCH_COURSE_SUGGEST pbtcs                                              ");
			sqlBuffer
					.append("                            on pbtc.id = pbtcs.fk_course_id                                                      ");
			sqlBuffer
					.append("                          left join enum_const ec2                                                               ");
			sqlBuffer
					.append("                            on pbtcs.fk_enum_const_id = ec2.id                                                   ");
			sqlBuffer
					.append("                          left join enum_const ec3                                                               ");
			sqlBuffer
					.append("                            on pbtc.flag_coursecategory = ec3.id                                                 ");
			sqlBuffer
					.append("                          left join enum_const ec4                                                               ");
			sqlBuffer
					.append("                            on pbtc.flag_course_item_type = ec4.id                                               ");
			sqlBuffer
					.append("                          left join enum_const ec5                                                               ");
			sqlBuffer
					.append("                            on pbtc.flag_content_property = ec5.id                                               ");
			sqlBuffer
					.append("                          left join enum_const ec6                                                               ");
			sqlBuffer
					.append("                            on pbto.FLAG_CHOOSE = ec6.id left join enum_const ec_CourseType     on pbtc.flag_coursetype =ec_CourseType.id                                                        ");
			sqlBuffer.append("                         where fk_stu_id = '" + stuId
					+ "'                                                              ");
			sqlBuffer
					.append("                           and fk_tch_opencourse_id in                                                           ");
			sqlBuffer
					.append("                               (select id                                                                        ");
			sqlBuffer
					.append("                                  from pr_bzz_tch_opencourse                                                     ");
			sqlBuffer
					.append("                                 where fk_batch_id =                                                             ");
			sqlBuffer.append("                                       '" + batchId + "')                                       ");
			sqlBuffer
					.append("                         group by pbtc.id || '-' || fk_stu_id||'-'||FK_BATCH_ID,                                                   ");
			sqlBuffer
					.append("                                  pbtc.code,                                                                     ");
			sqlBuffer
					.append("                                  pbtc.name, ec_CourseType.name,                                                                    ");
			sqlBuffer
					.append("                                  ec1.name,                                                                      ");
			sqlBuffer
					.append("                                  pbtc.price,                                                                    ");
			sqlBuffer
					.append("                                  pbtc.time,                                                                     ");
			sqlBuffer
					.append("                                  pbtc.teacher,                                                                  ");
			sqlBuffer
					.append("                                  ec3.name,                                                                      ");
			sqlBuffer
					.append("                                  ec4.name,                                                                      ");
			sqlBuffer
					.append("                                  ec5.name,                                                                      ");
			sqlBuffer
					.append("                                  ec6.name   )                                                                    ");
			sqlBuffer.append(" where 1=1 ");
			try {
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
					if (name.equals("enumConstByFlagQualificationsType.name")) {
						name = "qualificationsType";
					}
					if (name.equals("enumConstByFlagCourseType.name")) {
						name = "ec_CourseType";
					}
					if (name.equals("enumConstByFlagCourseCategory.name")) {
						name = "courseCategory";
					}
					if (name.equals("enumConstByFlagCourseItemType.name")) {
						name = "courseItemType";
					}
					if (name.equals("enumConstByFlagContentProperty.name")) {
						name = "contentProperty";
					}
					if (name.equals("enumConstByFlagCourseType.name")) {
						name = "courseTypeName";
					}
					if (name.equals("enumConstByFlagChoose.name")) {
						name = "flagchoose";
					}
					if (name.equals("qualificationsType")) {
						sqlBuffer.append(" and INSTR(qualificationsType,'" + value + "',1,1)>0");
					} else {
						sqlBuffer.append(" and " + name + " like '%" + value + "%'");
					}

				} while (true);
				page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()),
						Integer.parseInt(this.getStart()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (EntityException e) {
				e.printStackTrace();
			}
		} else if (method.equals("myStudentPay")) {
			// 获取登陆集体账号的机构ID
			String enterpriseIdString = us.getPriEnterprises().get(0).getId();
			// 查询本机构及本机构下属机构ID集合
			String sql_enterprises = "SELECT ID FROM PE_ENTERPRISE WHERE ID = '" + enterpriseIdString + "' OR FK_PARENT_ID = '"
					+ enterpriseIdString + "'";
			StringBuffer sqlBuffer = new StringBuffer();
			String batchId = ServletActionContext.getRequest().getParameter("id");
			String LOGIN_ID = us.getLoginId();
			sqlBuffer.append("SELECT * ");
			sqlBuffer.append("FROM  ( select pbs.id             as id,                                                         ");
			sqlBuffer.append("        sb.BATCH_ID           as BATCH_ID,sb.STU_ID as STU_ID, su.login_id as reg_no,");
			sqlBuffer.append("        pbs.true_name         as name,                                                           ");
			sqlBuffer.append("        pbs.card_no            as card_no,                                                         ");
			sqlBuffer.append("        pbs.work            as work,                                                         ");
			sqlBuffer.append("        pbs.position            as position,                                                         ");
			sqlBuffer.append("        pe.name               as ENTERPRISE_NAME,                                                ");
			sqlBuffer
					.append("        ec_BATCHPAYSTATE.NAME as Combobox_QualificationsType,                                                       ");
			sqlBuffer
					.append("        NVL(tongji.num,0)  courseNum,                                                                              ");
			sqlBuffer
					.append("        NVL(tongji.time,0) courseTime                                                                             ");
			sqlBuffer.append("   from STU_BATCH sb                                                                             ");
			sqlBuffer.append("  inner join PE_BZZ_STUDENT pbs                                                                  ");
			sqlBuffer.append("     on sb.STU_ID = pbs.ID                                                                       ");
			sqlBuffer.append("  inner join PE_ENTERPRISE pe                                                                    ");
			sqlBuffer.append("     on pbs.FK_ENTERPRISE_ID = pe.ID                                                             ");
			sqlBuffer.append("   left outer join PE_ENTERPRISE pe_sub                                                          ");
			sqlBuffer.append("     on pe.FK_PARENT_ID = pe_sub.ID                                                              ");
			sqlBuffer.append("  inner join PE_BZZ_BATCH pbb                                                                    ");
			sqlBuffer.append("     on sb.BATCH_ID = pbb.ID                                                                     ");
			sqlBuffer.append("  inner join ENUM_CONST ec_BATCHPAYSTATE                                                         ");
			sqlBuffer.append("     on sb.FLAG_BATCHPAYSTATE = ec_BATCHPAYSTATE.ID                                              ");
			sqlBuffer.append("  LEFT join (select count(*) num, sum(time) time, fk_stu_id                                     ");
			sqlBuffer.append("                from pr_bzz_tch_stu_elective_back pbtseb                                         ");
			sqlBuffer.append("               inner join pr_bzz_tch_opencourse pbto                                             ");
			sqlBuffer.append("                  on pbtseb.fk_tch_opencourse_id = pbto.id                                       ");
			sqlBuffer.append("               inner join pe_bzz_tch_course pbtc                                                 ");
			sqlBuffer.append("                  on pbto.fk_course_id = pbtc.id                                                 ");
			sqlBuffer.append("               where FK_BATCH_ID = '" + batchId + "'                            ");
			sqlBuffer.append("               group by fk_stu_id) tongji                                                        ");
			sqlBuffer.append("     on sb.stu_id = tongji.fk_stu_id                                                             ");
			sqlBuffer.append(" inner join sso_user su on su.id = pbs.fk_sso_user_id                                            ");
			sqlBuffer.append("  where pbs.FK_ENTERPRISE_ID in (" + sql_enterprises
					+ ")                                                                      ");
			sqlBuffer.append("   and pbb.ID = '" + batchId + "'                                                           )      ");
			sqlBuffer.append(" where 1=1 ");
			try {
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
					if (name.equals("enumConstByFlagQualificationsType.name")) {
						name = "qualificationsType";
					}
					if (name.equals("enumConstByFlagCourseType.name")) {
						name = "ec_CourseType";
					}
					if (name.equals("enumConstByFlagCourseCategory.name")) {
						name = "courseCategory";
					}
					if (name.equals("enumConstByFlagCourseItemType.name")) {
						name = "courseItemType";
					}
					if (name.equals("enumConstByFlagContentProperty.name")) {
						name = "contentProperty";
					}
					if (name.equals("enumConstByFlagCourseType.name")) {
						name = "courseTypeName";
					}
					if (name.equals("enumConstByFlagChoose.name")) {
						name = "flagchoose";
					}
					if (name.equals("qualificationsType")) {
						sqlBuffer.append(" and INSTR(qualificationsType,'" + value + "',1,1)>0");
					} else {
						sqlBuffer.append(" and " + name + " like '%" + value + "%'");
					}
				} while (true);
				page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()),
						Integer.parseInt(this.getStart()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (EntityException e) {
				e.printStackTrace();
			}
		} else {
			page = super.list();
		}
		return page;
	}

	/**
	 * 支付选中学员 lipp
	 * 
	 * @return
	 */
	public String toConfirmStu() {
		this.destroyOrderSession();
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = this.getIds().split(",");
			String idsString = "";
			for (int i = 0; i < ids.length; i++) {
				idsString += "'" + ids[i] + "',";
			}
			idsString = idsString.substring(0, idsString.length() - 1);
			String lid = this.loginId;
			if (lid == null || "".equals(lid)) {// 如果this.loginId为空则支付自己的专项。
				lid = us.getLoginId();
			}
			PeEnterprise peEnterprise = null;
			String enterpriseID = "";
			try {
				// 检查选中学员中是否有未选课学员存在，有则return并提示。
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT TO_CHAR(WM_CONCAT(DISTINCT '&nbsp;'||PBS.TRUE_NAME || '/' || PBS.REG_NO||'&nbsp;')) ");
				sb.append("   FROM PE_BZZ_STUDENT PBS ");
				sb.append("  WHERE PBS.ID IN (" + idsString + ") ");
				sb.append("    AND PBS.ID NOT IN (SELECT PBTSE.FK_STU_ID ");
				sb.append("                         FROM PR_BZZ_TCH_STU_ELECTIVE_BACK PBTSE ");
				sb.append("                        INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
				sb.append("                           ON PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID ");
				sb.append("                        INNER JOIN PE_BZZ_TCH_COURSE PBTC ");
				sb.append("                           ON PBTO.FK_COURSE_ID = PBTC.ID ");
				sb.append("                        INNER JOIN PE_BZZ_STUDENT PBS ");
				sb.append("                           ON PBTSE.FK_STU_ID = PBS.ID ");
				sb.append("                        WHERE PBTO.FK_BATCH_ID = '" + id + "') ");

				List zeroList = this.getGeneralService().getBySQL(sb.toString());
				if (null != zeroList && zeroList.size() != 0 && null != zeroList.get(0) && !"".equals(zeroList.get(0))) {
					this.setMsg("学员：" + zeroList.get(0) + "未选课，请重新选择要支付的学员！");
					this.setTogo("back");
					return "pbmsg";
				}
				PeEnterpriseManager peEnterpriseManager = this.getGeneralService().getEnterpriseManagerByLoginId(lid);
				peEnterprise = peEnterpriseManager.getPeEnterprise();
				enterpriseID = peEnterprise.getId();
				String sql = "select price " + "  from pr_bzz_tch_stu_elective_back pbtseb " + "  join pr_bzz_tch_opencourse pbto  "
						+ " on pbtseb.fk_tch_opencourse_id = pbto.id " + "  join pe_bzz_tch_course pbtc "
						+ " on pbto.fk_course_id = pbtc.id " + " where pbto.fk_batch_id = '" + id + "'" + " and fk_stu_id in (" + idsString
						+ ")";
				List studentCourselist = this.getGeneralService().getBySQL(sql);
				ServletActionContext.getRequest().getSession().setAttribute("studentList", studentCourselist);
				ServletActionContext.getRequest().getSession().setAttribute("studentIds", ids);
				// 校验专项是否支付过
				batchPayCheckByIds(us, enterpriseID, id, ids);
				peBzzBatch = batchDateCheck();// 判断是否在专项时间内
				// 判断本机构下当前专项的当前选中的未支付的学员,返回学员信息及选课数、金额数
				list = batchStuCheckByIds(id, idsString, lid);
				batchCourseCheck();// 判断此专项内是否有课程
				batchDatabaseCheck();// 支付之前检查数据库连接
				BigDecimal sumPrice = new BigDecimal("0.00");
				Iterator<String> it = studentCourselist.iterator();
				while (it.hasNext()) {
					sumPrice = sumPrice.add(new BigDecimal(it.next().toString()));
				}
				ServletActionContext.getRequest().getSession().setAttribute("batchPrice", sumPrice);
				String seq = this.getGeneralService().getOrderSeq();
				this.peBzzOrderInfo = new PeBzzOrderInfo();
				this.peBzzOrderInfo.setSeq(seq);
				this.peBzzOrderInfo.setPeEnterprise(peEnterprise);
				this.peBzzOrderInfo.setPeBzzBatch(peBzzBatch);
				this.peBzzOrderInfo.setAmount(sumPrice.toString());
				ActionContext.getContext().getSession().put("peBzzOrderInfo", peBzzOrderInfo);
			} catch (EntityException e) {
				this.setMsg(this.exErrorMsg(e.getMessage(), 0));// 转换错误信息
				this.setTogo("/entity/basic/paymentBatch.action");
				return "pbmsg";
			} catch (Exception ex) {
				this.setMsg("访问异常，err_code:" + ex.getMessage());
				this.setTogo("/entity/basic/paymentBatch.action");
				return "pbmsg";
			}
			return "confirmStudentInfo";
		} else {
			this.setMsg("请至少选择一条数据!");
			this.setTogo("back");
			return "pbmsg";
		}
	}

	public String toAddCourse() {
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			/* 存储选中ids便于后续操作使用 */
			ServletActionContext.getRequest().getSession().setAttribute("params_ids", ids);
			// this.method = "addCourse";
		} else {
			this.setMsg("请至少选择一条数据!");
			this.setTogo("back");
			return "pbmsg";
		}
		return "addCourse";

	}

	/**
	 * 判断此机构下是否存在学员
	 */
	private List batchStuCheckByIds(String batchId, String ids, String lid) throws EntityException {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sql = "select pbs.id, pbs.reg_no,                                                                       "
				+ "       pbs.TRUE_NAME trueName,                                                                      "
				+ "       pbs.card_no,                                                                   "
				+ "       pe.code,                                                                       "
				+ "       pe.name,                                                                       "
				+ "       count(DISTINCT PBTO.ID) courseNum,                                                                      "
				+ "       sum(time)    TimeNum                                                                 "
				+ "  from PE_BZZ_STUDENT pbs                                                             "
				+ " inner join PE_ENTERPRISE pe                                                          "
				+ "    on pbs.FK_ENTERPRISE_ID = pe.ID                                                   "
				+ "  left outer join PE_ENTERPRISE pe2                                                   "
				+ "    on pe.FK_PARENT_ID = pe2.ID                                                       "
				+ " inner join pr_bzz_tch_stu_elective_back pbtseb                                       "
				+ "    on pbs.id = pbtseb.fk_stu_id                                                      "
				+ " inner join pr_bzz_tch_opencourse pbto                                                "
				+ "    on pbtseb.fk_tch_opencourse_id = pbto.id                                          "
				+ "  inner  join pe_bzz_tch_course pbtc                                              "
				+ "    on pbto.fk_course_id = pbtc.id                                                    "
				+ " where (PE.ID  = '"
				+ us.getPriEnterprises().get(0).getId()
				+ "' OR PE2.ID = '"
				+ us.getPriEnterprises().get(0).getId()
				+ "')"
				+ "                                                                      "
				+ "           and  pbto.FK_BATCH_ID = '"
				+ batchId
				+ "'"
				+ " group by pbs.id,  pbs.reg_no,pbs.TRUE_NAME, pbs.card_no, pe.code, pe.name  having PBS.id IN  (select DISTINCT pbs2.ID                                                                "
				+ "          from STU_BATCH sb                                                           "
				+ "         inner join PE_BZZ_STUDENT pbs2                                               "
				+ "            on sb.STU_ID = pbs2.ID                                                    "
				+ "         inner join PE_BZZ_BATCH pbb                                                  "
				+ "            on sb.BATCH_ID = pbb.ID                                                   " + "         where pbs2.ID in ("
				+ ids + ")                                        "
				+ "           and sb.flag_batchpaystate = '40288a7b39c3ac650139c3f216870005')";

		List list = null;
		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (list.size() == 0) {
			// this.setMsg("该专项内没有未支付学员，不能支付");
			this.setMsg("err_batch_paid");
		}
		return list;
	}

	/*
	 * private List batchStuCheckByIds(String[] ids, String lid) throws
	 * EntityException { String sql = "\n" + "select pe.code\n" + " from
	 * pe_enterprise pe\n" + " inner join pe_enterprise_manager pem on pe.id =
	 * pem.fk_enterprise_id\n" + " where pem.login_id = '" + lid + "'"; List
	 * listEnterprise = null; try { listEnterprise =
	 * this.getGeneralService().getBySQL(sql); } catch (EntityException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } String
	 * enterpriseId = (String) listEnterprise.get(0);
	 * 
	 * DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
	 * dc.createCriteria("peEnterprise", "peEnterprise");
	 * dc.createCriteria("peEnterprise.peEnterprise", "pe", dc.LEFT_JOIN);
	 * dc.add(Restrictions.or(Restrictions.eq("peEnterprise.code",
	 * enterpriseId), Restrictions.eq("pe.code", enterpriseId)));
	 * 
	 * EnumConst enumConstByFlagBatchPayState = this.enumConstService
	 * .getByNamespaceCode("FlagBatchPayState", "0"); // 未支付 DetachedCriteria
	 * sbDc = DetachedCriteria.forClass(StudentBatch.class);
	 * sbDc.createCriteria("peBzzBatch", "peBzzBatch");
	 * sbDc.createCriteria("peStudent", "peStudent");
	 * sbDc.setProjection(Property.forName("peStudent.id"));
	 * sbDc.add(Restrictions.eq("peBzzBatch.id", this.id));
	 * sbDc.add(Restrictions.in("peStudent.id", ids));
	 * sbDc.add(Restrictions.eq("enumConstByFlagBatchPayState",
	 * enumConstByFlagBatchPayState)); dc.add(Property.forName("id").in(sbDc)); //
	 * 专项中存在的学员
	 * 
	 * 
	 * 
	 * List list = null; try { list = this.getGeneralService().getList(dc); }
	 * catch (EntityException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * if (list.size() == 0) { // this.setMsg("该专项内没有未支付学员，不能支付");
	 * this.setMsg("err_batch_paid"); } return list; }
	 */

	/**
	 * 判断此专项是否已经支付
	 */
	private void batchPayCheckByIds(UserSession us, String enterpriseID, String batchId, String[] ids) throws EntityException {

		DetachedCriteria sDc = DetachedCriteria.forClass(StudentBatch.class);
		sDc.createCriteria("peBzzBatch", "peBzzBatch");
		sDc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
		sDc.createCriteria("peBzzOrderInfo.enumConstByFlagPaymentState", "enumConstByFlagPaymentState", sDc.LEFT_JOIN);
		sDc.createCriteria("peBzzOrderInfo.peEnterprise", "peEnterprise", sDc.LEFT_JOIN);
		sDc.createCriteria("peBzzOrderInfo.ssoUser", "ssoUser");
		sDc.add(Restrictions.in("peStudent.id", ids));
		sDc.add(Restrictions.eq("peBzzBatch.id", batchId));
		List sList = null;
		try {
			sList = this.getGeneralService().getList(sDc);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if (sList != null && sList.size() > 0) {
			for (Object o : sList) {
				PeBzzOrderInfo bzzOrderInfo = ((StudentBatch) o).getPeBzzOrderInfo();
				if (bzzOrderInfo.getPeEnterprise() != null && enterpriseID.equals(bzzOrderInfo.getPeEnterprise().getId())) {
					if (us.getLoginId().equals(bzzOrderInfo.getSsoUser().getLoginId())) {
						if (bzzOrderInfo.getEnumConstByFlagPaymentState() != null
								&& "1".equals(bzzOrderInfo.getEnumConstByFlagPaymentState().getCode())) {
							throw new EntityException("err_batch_paid");
						} else {
							throw new EntityException("err_batch_in_pay");
						}
					} else {
						throw new EntityException("err_batch_out_auth");
					}
				} else {// 学生个人支付
					if (bzzOrderInfo.getEnumConstByFlagPaymentState() != null
							&& "1".equals(bzzOrderInfo.getEnumConstByFlagPaymentState().getCode())) {
						throw new EntityException("err_batch_paid");
					} else {
						throw new EntityException("err_batch_out_auth");
					}
				}
			}
		}
	}

	/**
	 * 确认专项支付单独选择学员的订单
	 * 
	 * @return
	 */
	public String toConfirmOrderStu() {
		getInvoice();// 获取最后一次发票信息
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser user = us.getSsoUser();
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
			dc.add(Restrictions.eq("id", user.getId()));
			List tmpList = this.getGeneralService().getList(dc);
			if (tmpList.size() > 0) {
				user = (SsoUser) tmpList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		BigDecimal b1 = new BigDecimal(user.getSumAmount());
		BigDecimal b2 = new BigDecimal(user.getAmount());
		BigDecimal subAmount = b1.subtract(b2); // new
		ServletActionContext.getRequest().setAttribute("sumAmount", subAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
		ServletActionContext.getRequest().setAttribute("zxFlag", "true");
		peBzzOrderInfo = (PeBzzOrderInfo) ActionContext.getContext().getSession().get("peBzzOrderInfo");
		if (peBzzOrderInfo == null) {
			this.setMsg("订单信息错误，请重新选择支付!");
			this.setTogo("back");
			return "pbmsg";
		}
		return "confirmOrderInfo";
	}

	/**
	 * 去人专项支付订单
	 * 
	 * @return
	 */
	public String toConfirmOrder() {
		this.destroyOrderSession();
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		try {
			getInvoice();// 获取最后一次发票信息

			String lid = this.loginId;
			if (lid == null || "".equals(lid)) {// 如果this.loginId为空则支付自己的专项。
				lid = us.getLoginId();
			}
			PeEnterprise peEnterprise = null;
			String enterpriseID = "";
			PeEnterpriseManager peEnterpriseManager = this.getGeneralService().getEnterpriseManagerByLoginId(lid);
			peEnterprise = peEnterpriseManager.getPeEnterprise();
			enterpriseID = peEnterprise.getId();

			// 判断是否支付
			// batchPayCheck(us, enterpriseID,id);

			// 判断专项时间
			peBzzBatch = batchDateCheck();

			// 判断此机构下是否存在学员
			list = batchStuCheck(lid);
			ServletActionContext.getRequest().getSession().setAttribute("studentList", list);

			// 判断此专项内是否有课程
			batchCourseCheck();

			BigDecimal batchPrice = new BigDecimal(peBzzBatch.getStandards());
			BigDecimal sumPrice = batchPrice.multiply(new BigDecimal(list.size())).setScale(2, BigDecimal.ROUND_HALF_UP);

			ServletActionContext.getRequest().getSession().setAttribute("batchPrice", sumPrice);
			// BigDecimal batchPrice =
			// ((BigDecimal)ServletActionContext.getRequest().getSession().getAttribute("batchPrice")).setScale(2,
			// BigDecimal.ROUND_HALF_UP);
			batchPrice = batchPrice.setScale(2, BigDecimal.ROUND_HALF_UP);

			// 支付前检查数据库连接
			batchDatabaseCheck();

			String seq = this.getGeneralService().getOrderSeq();
			this.peBzzOrderInfo = new PeBzzOrderInfo();
			this.peBzzOrderInfo.setSeq(seq);
			this.peBzzOrderInfo.setPeEnterprise(peEnterprise);
			this.peBzzOrderInfo.setPeBzzBatch(peBzzBatch);
			this.peBzzOrderInfo.setAmount(sumPrice.toString());

			if (batchFreeCheck(batchPrice, seq)) {
				this.setMsg("0元专项已经支付成功，请至“报名付费历史”中查看订单：" + seq + " !");
				this.setTogo("/entity/basic/paymentBatch.action");
				return "pbmsg";
			}
			ActionContext.getContext().getSession().put("peBzzOrderInfo", peBzzOrderInfo);
		} catch (EntityException e) {
			this.setMsg(this.exErrorMsg(e.getMessage(), 0));// 转换错误信息
			this.setTogo("/entity/basic/paymentBatch.action");
			return "pbmsg";
		} catch (Exception ex) {
			this.setMsg("访问异常，err_code:" + ex.getMessage());
			this.setTogo("/entity/basic/paymentBatch.action");
			return "pbmsg";
		}

		// return "confirmOrderInfo";
		return "confirmStudentInfo";
	}

	/**
	 * 支付之前检查数据库连接
	 * 
	 * @throws EntityException
	 */
	private void batchDatabaseCheck() throws EntityException {
		if (!this.getPaymentBatchService().checkDatabase()) {
			throw new EntityException("数据连接失败，请返回，稍后重试！");
		}
	}

	/**
	 * 免费专项处理
	 * 
	 * @param batchPrice
	 * @param seq
	 * @return
	 * @throws EntityException
	 */
	private boolean batchFreeCheck(BigDecimal batchPrice, String seq) throws EntityException {
		if (batchPrice.compareTo(new BigDecimal(0)) == 0) {// 免费专项
			ActionContext.getContext().getSession().put("peBzzOrderInfo", peBzzOrderInfo);// 订单放入session供生成订单使用
			this.payMethod = "3";// 给一个默认值（暂用公司转账代替），生成后修改成零元订单
			toPayment();// 生成订单
			this.peBzzOrderInfo = this.getGeneralService().getOrderBySeq(seq);
			EnumConst enumConstByFlagPaymentMethod = this.enumConstService.getByNamespaceCode("FlagPaymentMethod", "4");
			this.peBzzOrderInfo.setEnumConstByFlagPaymentMethod(enumConstByFlagPaymentMethod);
			this.getGeneralService().updatePeBzzOrderInfo(this.peBzzOrderInfo, "confirm", null);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断此专项内是否有课程
	 */
	private void batchCourseCheck() throws EntityException {
		DetachedCriteria courseDc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
		courseDc.createCriteria("peBzzBatch", "peBzzBatch");
		courseDc.createCriteria("peBzzTchCourse", "peBzzTchCourse");
		courseDc.add(Restrictions.eq("peBzzBatch.id", id));
		List courseList = null;
		try {
			courseList = this.getGeneralService().getList(courseDc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if (courseList.size() == 0) {
			// 该专项内没有课程，无法支付
			throw new EntityException("err_batch_no_course");
		}
	}

	/**
	 * 重写框架方法：更新字段前的校验
	 * 
	 * @author lipp
	 * @return
	 */
	@Override
	public void checkBeforeUpdateColumn(List idList) throws EntityException {
		int count = 0;
		String sql = "";
		String batchId = idList.get(0).toString();
		String stuId = idList.get(1).toString();
		sql = " select ec_BATCHPAYSTATE.NAME as Combobox_QualificationsType        "
				+ "   from STU_BATCH sb                                                "
				+ "  inner join PE_BZZ_STUDENT pbs                                     "
				+ "     on sb.STU_ID = pbs.ID                                          "
				+ "  inner join PE_BZZ_BATCH pbb                                       "
				+ "     on sb.BATCH_ID = pbb.ID                                        "
				+ "  inner join ENUM_CONST ec_BATCHPAYSTATE                            "
				+ "     on sb.FLAG_BATCHPAYSTATE = ec_BATCHPAYSTATE.ID                 " + "  where sb.stu_id = '" + stuId
				+ "'                                        " + "    and sb.batch_id = '" + batchId + "'            ";
		list = this.getGeneralService().getBySQL(sql);
		if (list.get(0).toString().equals("已支付")) {
			throw new EntityException("所选学员" + stuId + "已经支付该专项，不能删除课程！");
		}
		if (list.get(0).toString().equals("支付中")) {
			throw new EntityException("所选学员" + stuId + "正在支付该专项，不能删除课程！");
		}

		try {
			for (int i = 2; i < idList.size(); i++) {
				sql = "select *                                                                  "
						+ "            from pr_bzz_tch_opencourse                                    "
						+ "           where FK_COURSE_ID in                                          "
						+ "                 (select FK_COURSE_ID                                     "
						+ "                    from pr_bzz_tch_opencourse                            "
						+ "                   where FLAG_CHOOSE = 'choose1')                         "
						+ "             and FK_COURSE_ID = '" + idList.get(i) + "'    " + "             and fk_batch_id = '" + batchId
						+ "'         ";

				list = this.getGeneralService().getBySQL(sql);
				if (list.size() > 0) {
					count++;
				}
			}
			if (count > 0) {
				throw new EntityException("所选课程中有必选课程，必须课程不能删除，请重新选择！");
			}
		} catch (EntityException e) {
			throw e;
		}

	}

	/**
	 * 重写框架方法：更新字段
	 * 
	 * @author linjie
	 * @return
	 */
	@SuppressWarnings( { "unchecked", "unchecked", "unchecked" })
	public Map updateColumn() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Map map = new HashMap();
		String msg = "";
		int existNum = 0;
		String action = this.getColumn();
		List idList = new ArrayList();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			String stu_id = ids[0].split("-")[1];
			String batchid = ids[0].split("-")[2];
			String courseId = "";
			idList.add(batchid);
			idList.add(stu_id);
			for (int i = 0; i < ids.length; i++) {
				courseId += "'" + ids[i].split("-")[0] + "',";
				idList.add(ids[i].split("-")[0]);
			}
			courseId = courseId.substring(0, courseId.length() - 1);

			if (action.equals("deleteCourse.true")) {
				try {
					checkBeforeUpdateColumn(idList);
				} catch (EntityException e1) {
					map.put("success", "false");
					map.put("info", e1.getMessage());
					return map;
				}
			}
			try {

				DetachedCriteria tempdc = DetachedCriteria.forClass(PeBzzTchCourse.class);
				tempdc.createCriteria("enumConstByFlagIsvalid");

				/**
				 * 删除课程
				 */
				if (action.equals("deleteCourse.true")) {

					String sql = " delete pr_bzz_tch_stu_elective_back pbtseb               " + "  where FK_STU_ID = '" + stu_id + "'  "
							+ "    and FK_TCH_OPENCOURSE_ID in                           "
							+ "        (select fk_tch_opencourse_id                      "
							+ "           from pr_bzz_tch_stu_elective_back pbtseb       "
							+ "           join pe_bzz_student pbs                        "
							+ "             on pbtseb.fk_stu_id = pbs.id                 "
							+ "           join pr_bzz_tch_opencourse pbto                "
							+ "             on pbtseb.fk_tch_opencourse_id = pbto.id     "
							+ "           join pe_bzz_tch_course pbtc                    "
							+ "             on pbto.fk_course_id = pbtc.id               " + "          where FK_STU_ID = '" + stu_id
							+ "'                      " + "            and pbtc.id in (" + courseId + "))";
					this.getGeneralService().executeBySQL(sql);
					msg = "删除课程";
				}

				map.put("success", "true");
				map.put("info", msg + "共有" + ids.length + "门课程操作成功");
			} catch (Exception e) {
				map.clear();
				map.put("success", "false");
				map.put("info", msg + "操作失败");
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

	/**
	 * 判断此机构下是否存在学员
	 */
	private List batchStuCheck(String lid) throws EntityException {
		String sql = "\n" + "select pe.code\n" + "  from pe_enterprise pe\n"
				+ " inner join pe_enterprise_manager pem on pe.id = pem.fk_enterprise_id\n" + " where pem.login_id = '" + lid + "'";
		List listEnterprise = null;
		try {
			listEnterprise = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String enterpriseId = (String) listEnterprise.get(0);

		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
		dc.createCriteria("peEnterprise", "peEnterprise");
		dc.createCriteria("peEnterprise.peEnterprise", "peEnterprise1", dc.LEFT_JOIN);
		dc.add(Restrictions.or(Restrictions.eq("peEnterprise.code", enterpriseId), Restrictions.eq("peEnterprise1.code", enterpriseId)));
		// dc.add(Restrictions.eq("peEnterprise.code", enterpriseId));

		EnumConst enumConstByFlagBatchPayState = this.enumConstService.getByNamespaceCode("FlagBatchPayState", "0"); // 未支付
		DetachedCriteria sbDc = DetachedCriteria.forClass(StudentBatch.class);
		sbDc.createCriteria("peBzzBatch", "peBzzBatch");
		sbDc.createCriteria("peStudent", "peStudent");
		sbDc.setProjection(Property.forName("peStudent.id"));
		sbDc.add(Restrictions.eq("peBzzBatch.id", this.id));
		sbDc.add(Restrictions.eq("enumConstByFlagBatchPayState", enumConstByFlagBatchPayState));
		dc.add(Property.forName("id").in(sbDc)); // 专项中存在的学员
		List list = null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (list.size() == 0) {
			// throw new EntityException("该专项内没有未支付学员，不能支付");
			// 此专项已支付
			throw new EntityException("err_batch_paid");
		}
		return list;
	}

	/**
	 * 判断是否在专项时间内
	 */
	private PeBzzBatch batchDateCheck() throws EntityException {
		DetachedCriteria batchDc = DetachedCriteria.forClass(PeBzzBatch.class);
		batchDc.add(Restrictions.eq("id", id));
		PeBzzBatch peBzzBatch = null;
		try {
			peBzzBatch = (PeBzzBatch) this.getGeneralService().getList(batchDc).get(0);
			ServletActionContext.getRequest().getSession().setAttribute("peBzzBatch", peBzzBatch);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!isRightDate(peBzzBatch.getStartDate(), peBzzBatch.getEndDate())) {
			// 当前时间不在支付时间段内
			throw new EntityException("err_batch_out_date");
		}
		return peBzzBatch;
	}

	/**
	 * 判断此专项是否已经支付
	 */
	private void batchPayCheck(UserSession us, String enterpriseID, String batchId) throws EntityException {
		DetachedCriteria sDc = DetachedCriteria.forClass(StudentBatch.class);
		sDc.createCriteria("peBzzBatch", "peBzzBatch");
		sDc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
		sDc.createCriteria("peBzzOrderInfo.enumConstByFlagPaymentState", "enumConstByFlagPaymentState", sDc.LEFT_JOIN);
		sDc.createCriteria("peBzzOrderInfo.peEnterprise", "peEnterprise", sDc.LEFT_JOIN);
		sDc.createCriteria("peBzzOrderInfo.ssoUser", "ssoUser");
		sDc.add(Restrictions.eq("peBzzBatch.id", batchId));
		List sList = null;
		try {
			sList = this.getGeneralService().getList(sDc);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if (sList.size() > 0) {
			for (Object o : sList) {
				PeBzzOrderInfo bzzOrderInfo = ((StudentBatch) o).getPeBzzOrderInfo();
				if (bzzOrderInfo.getPeEnterprise() != null && enterpriseID.equals(bzzOrderInfo.getPeEnterprise().getId())) {
					if (us.getLoginId().equals(bzzOrderInfo.getSsoUser().getLoginId())) {
						if (bzzOrderInfo.getEnumConstByFlagPaymentState() != null
								&& "1".equals(bzzOrderInfo.getEnumConstByFlagPaymentState().getCode())) {
							// 此专项已支付
							throw new EntityException("err_batch_paid");
						} else {
							// 此专项已存在订单，请到“报名付费历史查询”中查看订单并继续支付。
							throw new EntityException("err_batch_in_pay");
						}
					} else {
						// 此专项已存在订单，但订单由其他管理员提交，您不能操作！
						throw new EntityException("err_batch_out_auth");
					}
				} else {// 学生个人支付
					if (bzzOrderInfo.getEnumConstByFlagPaymentState() != null
							&& "1".equals(bzzOrderInfo.getEnumConstByFlagPaymentState().getCode())) {
						throw new EntityException("err_batch_paid");
					} else {
						throw new EntityException("err_batch_out_auth");
					}
				}
			}
		}
	}

	/**
	 * 专项时间判断
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public boolean isRightDate(Date startDate, Date endDate) {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		endDate = cal.getTime();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		endDate = cal.getTime();
		if (now.after(endDate) || now.before(startDate)) {
			return false;
		}
		return true;
	}

	/*
	 * public String toConfirmPaymentMethod() {
	 * 
	 * BigDecimal batchPrice =
	 * ((BigDecimal)ServletActionContext.getRequest().getSession().getAttribute("batchPrice")).setScale(2,
	 * BigDecimal.ROUND_HALF_UP); try {
	 * this.peBzzOrderInfo.setSeq(this.getGeneralService().getOrderSeq());
	 * 
	 * PeBzzBatch peBzzBatch =
	 * (PeBzzBatch)ServletActionContext.getRequest().getSession().getAttribute("peBzzBatch");
	 * this.peBzzOrderInfo.setAmount(batchPrice.toString()); if
	 * ("y".equalsIgnoreCase(this.isInvoice)) {
	 * this.peBzzInvoiceInfo.setPeBzzOrderInfo(this.peBzzOrderInfo);
	 * ActionContext.getContext().getSession().put("peBzzInvoiceInfo",
	 * peBzzInvoiceInfo); }else{
	 * ActionContext.getContext().getSession().put("peBzzOrderInfo",
	 * peBzzOrderInfo); } } catch (EntityException e) { e.printStackTrace(); }
	 * return "confirmPaymentMethod"; }
	 */

	/**
	 * 初始化要保存的订单和选课信息
	 */
	public void initOrderAndElective() {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		// 是否申请发票
		/**
		 * 如果直接从session中去peBzzInvoiceInfo或peBzzOrderInfo 会将本次传参覆盖掉，
		 */
		PeBzzInvoiceInfo _peBzzInvoiceInfo = (PeBzzInvoiceInfo) ActionContext.getContext().getSession().get("peBzzInvoiceInfo");
		PeBzzOrderInfo _peBzzOrderInfo = (PeBzzOrderInfo) ActionContext.getContext().getSession().get("peBzzOrderInfo");
		;
		if (_peBzzOrderInfo != null) {
			_peBzzOrderInfo.setCname(peBzzOrderInfo.getCname());
			_peBzzOrderInfo.setNum(peBzzOrderInfo.getNum());
			peBzzOrderInfo = _peBzzOrderInfo;
		}
		if ("y".equalsIgnoreCase(this.isInvoice) && !"1".equalsIgnoreCase(this.payMethod)) {// 选中开发票，并且不是预付费支付
			EnumConst enumConstByFlagFpOpenState = this.getEnumConstService().getByNamespaceCode("FlagFpOpenState", "0");// 待开发票
			EnumConst enumConstByFlagPostType = this.getEnumConstService().getByNamespaceCode("FlagPostType", postType);//邮寄方式
			if (_peBzzInvoiceInfo != null) {
				_peBzzInvoiceInfo.setAddress(peBzzInvoiceInfo.getAddress());
				_peBzzInvoiceInfo.setAddressee(peBzzInvoiceInfo.getAddressee());
				_peBzzInvoiceInfo.setNum(peBzzInvoiceInfo.getNum());
				_peBzzInvoiceInfo.setPhone(peBzzInvoiceInfo.getPhone());
				_peBzzInvoiceInfo.setTitle(peBzzInvoiceInfo.getTitle());
				_peBzzInvoiceInfo.setZipCode(peBzzInvoiceInfo.getZipCode());
				peBzzInvoiceInfo = _peBzzInvoiceInfo;
			}
			peBzzInvoiceInfo.setEnumConstByFlagFpOpenState(enumConstByFlagFpOpenState);// 待开发票
			peBzzInvoiceInfo.setEnumConstByFlagPostType(enumConstByFlagPostType);//邮寄方式
			peBzzInvoiceInfo.setPeBzzOrderInfo(peBzzOrderInfo);
		} else {
			peBzzInvoiceInfo = null;
		}

		// 初始化默认状态
		EnumConst enumConstByFlagPaymentMethod = this.getEnumConstService().getByNamespaceCode("FlagPaymentMethod", this.payMethod);// 支付方式
		EnumConst enumConstByFlagPaymentType = this.getEnumConstService().getByNamespaceCode("FlagPaymentType", "1");// 集体支付
		EnumConst enumConstByFlagOrderType = this.getEnumConstService().getByNamespaceCode("FlagOrderType", "2");// 订单类型，专项支付
		EnumConst enumConstByFlagOrderState = this.getEnumConstService().getByNamespaceCode("FlagOrderState", "1");// 订单状态，正常
		EnumConst enumConstByFlagPaymentState = this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "0");// 未支付;
		EnumConst enumConstByFlagBatchPayState = this.getEnumConstService().getByNamespaceCode("FlagBatchPayState", "0"); // 专项，未支付
		EnumConst enumConstByFlagOrderIsValid = this.getEnumConstService().getByNamespaceCode("FlagOrderIsValid", "1"); // 订单有效状态，初始为有效
		EnumConst enumConstByFlagCheckState = this.getEnumConstService().getByNamespaceCode("FlagCheckState", "0");// 对账状态
		// EnumConst enumConstByFlagElectivePayStatus = null;

		// session中取得订单金额
		BigDecimal batchPrice = ((BigDecimal) ServletActionContext.getRequest().getSession().getAttribute("batchPrice")).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		this.peBzzOrderInfo.setAmount(batchPrice.toString());

		this.peBzzOrderInfo.setEnumConstByFlagOrderIsValid(enumConstByFlagOrderIsValid); // 设为有效，初始化订单
		this.peBzzOrderInfo.setEnumConstByFlagPaymentMethod(enumConstByFlagPaymentMethod);
		this.peBzzOrderInfo.setEnumConstByFlagPaymentType(enumConstByFlagPaymentType);
		this.peBzzOrderInfo.setEnumConstByFlagPaymentState(enumConstByFlagPaymentState);
		this.peBzzOrderInfo.setEnumConstByFlagOrderType(enumConstByFlagOrderType);
		this.peBzzOrderInfo.setEnumConstByFlagOrderState(enumConstByFlagOrderState);
		this.peBzzOrderInfo.setEnumConstByFlagCheckState(enumConstByFlagCheckState);// 订单对账状态
		this.peBzzOrderInfo.setCreateDate(new Date());
		try {
			this.peBzzOrderInfo.setPayer(this.getGeneralService().getEnterpriseManagerByLoginId(us.getSsoUser().getLoginId()).getName());
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		peBzzBatch = (PeBzzBatch) ServletActionContext.getRequest().getSession().getAttribute("peBzzBatch");

		DetachedCriteria dcElective = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
		dcElective.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
		dcElective.add(Restrictions.eq("prBzzTchOpencourse.peBzzBatch", peBzzBatch));
		dcElective.createCriteria("peBzzStudent", "peBzzStudent");
		/*
		 * List list = (List) ServletActionContext.getRequest().getSession()
		 * .getAttribute("studentList"); String[] ids = new String[list.size()];
		 * for (int i = 0; i < list.size(); i++) { ids[i] = ((PeBzzStudent)
		 * list.get(i)).getId(); }
		 */
		String[] ids = (String[]) ServletActionContext.getRequest().getSession().getAttribute("studentIds");
		dcElective.add(Restrictions.in("peBzzStudent.id", ids));

		try {
			electiveBackList = this.getGeneralService().getDetachList(dcElective);
			for (int i = 0; i < electiveBackList.size(); i++) {
				electiveBackList.get(i).setPeBzzOrderInfo(this.peBzzOrderInfo);
				// electiveBackList.get(i).setEnumConstByFlagElectivePayStatus(enumConstByFlagElectivePayStatus);
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DetachedCriteria sbDc = DetachedCriteria.forClass(StudentBatch.class);
		sbDc.createCriteria("peStudent", "peStudent");
		sbDc.createCriteria("peBzzBatch", "peBzzBatch");
		sbDc.add(Restrictions.in("peStudent.id", ids));
		sbDc.add(Restrictions.eq("peBzzBatch", peBzzBatch));
		try {
			DetachedCriteria studentDc = DetachedCriteria.forClass(PeBzzStudent.class);
			studentDc.add(Restrictions.in("id", ids));

			List studentList = this.getGeneralService().getDetachList(studentDc);
			listSb = this.getGeneralService().getDetachList(sbDc);
			for (int i = 0; i < listSb.size(); i++) {
				StudentBatch sb = listSb.get(i);
				sb.setPeBzzBatch(this.peBzzBatch);
				sb.setPeBzzOrderInfo(peBzzOrderInfo);
				sb.setPeStudent(((PeBzzStudent) studentList.get(i)));
				sb.setEnumConstByFlagBatchPayState(enumConstByFlagBatchPayState);
				listSb.set(i, sb);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 最终支付处理
	 * 
	 * @return
	 */
	public String toPayment() {
		if (this.payMethod == null || "".equalsIgnoreCase(this.payMethod) || "0,1,2,3".indexOf(this.payMethod) < 0) {
			this.setMsg("请正确选择支付方式！");
			this.setTogo("close");
			return "paymentMsg";
		}
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		// String lid =
		// (String)ActionContext.getContext().getSession().get("lid");//登陆id，集体给子集体专项支付功能添加。
		DetachedCriteria dcSsoUser = DetachedCriteria.forClass(SsoUser.class);

		// if(lid != null && !"".equals(lid)){
		// dcSsoUser.add(Restrictions.eq("loginId", lid));
		// }else{
		dcSsoUser.add(Restrictions.eq("id", us.getSsoUser().getId()));
		// }
		SsoUser user = new SsoUser();
		// 获取托管状态的ssoUser实例
		try {
			// List suList = this.getGeneralService().getDetachList(dcSsoUser);
			user = (SsoUser) this.getGeneralService().getDetachList(dcSsoUser).get(0);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}

		// 初始化订单，选课
		this.initOrderAndElective();

		if ("0".equalsIgnoreCase(this.payMethod)) {// 第三方支付
			try {
				// 实例化订单
				this.generateOrder(user);
			} catch (EntityException e) {
				e.printStackTrace();
				this.setMsg("订单生成失败，请联系管理员");
				this.setTogo("close");
				return "paymentMsg";
			}

			ServletActionContext.getRequest().getSession().setAttribute(this.peBzzOrderInfo.getSeq(), "paymentBatchByGroup");
			// 新增快钱支付
			String paymentType = ServletActionContext.getRequest().getParameter("paymentType");
			ServletActionContext.getRequest().getSession().setAttribute("paymentType", paymentType);
			return "onlinePayment";
		} else if ("1".equalsIgnoreCase(this.payMethod)) {// 预付费支付
			String balString = ServletActionContext.getRequest().getSession().getAttribute("batchPrice") + "";
			// BigDecimal balance = new
			// BigDecimal(this.peBzzBatch.getStandards());
			BigDecimal balance = new BigDecimal(balString);
			String sumAmount = (user.getSumAmount() == null || user.getSumAmount().equals("")) ? "0" : user.getSumAmount();
			String amount = (user.getAmount() == null || user.getAmount().equals("")) ? "0" : user.getAmount();

			int result = new BigDecimal(sumAmount).subtract(new BigDecimal(amount)).compareTo(balance);

			if (result == -1) {
				this.setMsg("当前账户余额不足");
				this.setTogo("close");
				return "paymentMsg";
			} else {
				user.setSumAmount(new BigDecimal(sumAmount).subtract(balance).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				user.setAmount(new BigDecimal(amount).add(balance).toString());
				try {
					// 实例化订单
					this.generateOrder(user);
				} catch (EntityException e) {
					e.printStackTrace();
					this.setMsg("订单生成失败，请联系管理员");
					this.setTogo("close");
					return "paymentMsg";
				}

				try {
					this.getGeneralService().updatePeBzzOrderInfo(peBzzOrderInfo, "confirm", user);
				} catch (EntityException e) {
					this.setMsg("支付失败");
					this.setTogo("close");
					return "paymentMsg";
				}
				this.setMsg("成功支付选课");
			}
		} else {// 支票和电汇支付

			try {
				// 实例化订单
				this.generateOrder(user);
			} catch (EntityException e) {
				e.printStackTrace();
				this.setMsg("订单生成失败，请联系管理员");
				this.setTogo("close");
				return "paymentMsg";
			}

			this.setMsg("选课支付提交成功，请尽快付款");
		}

		this.destroyOrderSession();
		this.setTogo("close");
		return "paymentMsg";
	}

	/**
	 * 线上支付中断后，继续支付
	 */
	public String continuePaymentOnline() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.createCriteria("enumConstByFlagOrderType", "enumConstByFlagOrderType", dc.LEFT_JOIN);
		dc.add(Restrictions.eq("seq", this.merorderid));
		try {
			this.peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralService().getList(dc).get(0);
			ServletActionContext.getRequest().getSession().setAttribute("peBzzOrderInfo", peBzzOrderInfo);
			ServletActionContext.getRequest().getSession().setAttribute(this.peBzzOrderInfo.getSeq(), "paymentBatchByGroup");
			// 新增快钱支付
			String paymentType = ServletActionContext.getRequest().getParameter("paymentType");
			ServletActionContext.getRequest().getSession().setAttribute("paymentType", paymentType);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			this.setMsg("专项继续支付失败,请联系协会管理员");
			this.setTogo("close");
			return "paymentMsg";
		}
		return "onlinePayment";
	}

	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	/***************************************************************************
	 * 用于检测用户账户中的剩余的预付费学时
	 * 
	 * @return
	 */
	public BigDecimal checkUserAmount(SsoUser ssoUser) {
		String userSumAmount = ssoUser.getSumAmount() == null ? "0.0" : ssoUser.getSumAmount();
		BigDecimal sumAmount = new BigDecimal(userSumAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
		String userAmount = ssoUser.getAmount() == null ? "0.0" : ssoUser.getAmount();
		BigDecimal amount = new BigDecimal(userAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
		return sumAmount.subtract(amount).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 清除session中订单信息
	 */
	public void destroyOrderSession() {
		if (ServletActionContext.getRequest().getSession().getAttribute("peBzzInvoiceInfo") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("peBzzInvoiceInfo");
		}
		if (ServletActionContext.getRequest().getSession().getAttribute("peBzzOrderInfo") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("peBzzOrderInfo");
		}
		if (ServletActionContext.getRequest().getSession().getAttribute("electiveList") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("electiveList");
		}
		if (ServletActionContext.getRequest().getSession().getAttribute("electiveBackList") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("electiveBackList");
		}
		if (ServletActionContext.getRequest().getSession().getAttribute("studentList") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("studentList");
		}
		if (ServletActionContext.getRequest().getSession().getAttribute("batchPrice") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("batchPrice");
		}
		if (ServletActionContext.getRequest().getSession().getAttribute("peBzzBatch") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("peBzzBatch");
		}
	}

	/**
	 * 实例化订单
	 */
	public void generateOrder(SsoUser user) throws EntityException {

		this.peBzzOrderInfo.setSsoUser(user);
		try {
			// this.paymentBatchService.saveElectiveCourseAndPebzzOrderInfo(electiveBackList,
			// peBzzOrderInfo, peBzzInvoiceInfo, user, listSb);
			this.electiveBackList = this.getGeneralService().saveElectiveBackList(this.electiveBackList, peBzzOrderInfo, peBzzInvoiceInfo,
					null, null, listSb);
		} catch (EntityException e) {
			throw e;
		}
	}

	/**
	 * 获得登陆用户的账号资金
	 * 
	 * @author linjie
	 * @return
	 */
	public BigDecimal getSubAmount(SsoUser ssoUser) {
		String sumAmount = (ssoUser.getSumAmount() == null || ssoUser.getSumAmount().equals("")) ? "0" : ssoUser.getSumAmount();
		String amount = (ssoUser.getAmount() == null || ssoUser.getAmount().equals("")) ? "0" : ssoUser.getAmount();
		BigDecimal bdSumAmount = new BigDecimal(sumAmount);
		BigDecimal bdAmount = new BigDecimal(amount);

		return bdSumAmount.subtract(bdAmount).setScale(1, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 学时支付
	 * 
	 * @author linjie
	 * @return
	 */
	public String paymentHour(String batchPrice) {
		BigDecimal bdBatchPrice = new BigDecimal(batchPrice);
		String price = this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0").getName();
		BigDecimal bdPrice = new BigDecimal(price);
		return bdBatchPrice.divide(bdPrice).setScale(1, BigDecimal.ROUND_HALF_UP).toString();

	}

	/**
	 * 转换错误信息
	 * 
	 * @param errIdx
	 * @param typeIdx
	 * @return
	 */
	private String exErrorMsg(String errIdx, int typeIdx) {
		String re = errIdx;
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("no_error", new String[] { "可支付", "可支付" });
		map.put("err_batch_out_auth", new String[] { "提交数据已存在订单，但订单由其他用户提交，您不能操作，请重新选择！", "不能操作" });
		map.put("err_batch_paid", new String[] { "此专项已支付", "已支付" });
		map.put("err_batch_out_date", new String[] { "当前时间不在支付时间段内", "已过期" });
		map.put("err_batch_no_course", new String[] { "该专项内没有课程，无法支付", "无课程" });
		map.put("err_batch_in_pay", new String[] { "提交数据已存在订单数据，请到“报名付费历史查询”中查看订单并继续支付。", "已存在订单" });
		// map.put("", new String[]{"","可支付"});
		String[] reArr = map.get(errIdx);
		if (reArr != null && reArr.length > 0) {
			if (reArr.length >= typeIdx + 1) {
				re = reArr[typeIdx];
			} else {
				re = reArr[0];
			}
		}
		return re;
	}
}
