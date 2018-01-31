package com.whaty.platform.entity.web.action.basic;

import java.util.Iterator;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class CheckClassHourRecordAction extends MyBaseAction {
	private String flag;
	private String detail = ""; // 用于标识是剥离还是分配
	private String id;
	private String userId;
	private String forg;// Lee 2013年12月27日 分配Or购买

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
		if ("y".equals(forg) && flag.equals("old")) {// 分配记录
			this.getGridConfig().setTitle(this.getText("分配记录"));
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("操作时间"), "operateTimeDatetime", false, false, true, "TextField", false, 200, "");
			this.getGridConfig().addColumn(this.getText("操作时间开始时间"), "operateTimeSTDatetime", true, false, false, "TextField", false, 200, "");
			this.getGridConfig().addColumn(this.getText("操作时间结束时间"), "operateTimeEDDatetime", true, false, false, "TextField", false, 200, "");

			ColumnConfig columnOperateType = new ColumnConfig(this.getText("操作类型"), "enumConstByFlagOperateType.name", true, true, true, "TextField", false, 100, "");
			String sql1 = "select a.id ,a.name from enum_const a where a.namespace='FlagOperateType' and code in ('1','2')";
			columnOperateType.setComboSQL(sql1);
			this.getGridConfig().addColumn(columnOperateType);
			this.getGridConfig().addColumn(this.getText("操作学时"), "operateAmount", false, false, true, "sumAmount");
			this.getGridConfig().addColumn(this.getText("账户剩余学时数"), "accountAmount", false, false, true, "amount");
			this.getGridConfig().addColumn(this.getText("订单号"), "orderId", false, false, false, "amount");
			this.getGridConfig().addColumn(this.getText("记录号"), "recordId", false, false, false, "amount");

		} else if ("b".equals(forg) && flag.equals("old")) { // 剥离记录
			this.getGridConfig().setTitle(this.getText("剥离记录"));
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("操作时间"), "operateTimeDatetime", false, false, true, "TextField", false, 200, "");
			this.getGridConfig().addColumn(this.getText("操作时间开始时间"), "operateTimeSTDatetime", true, false, false, "TextField", false, 200, "");
			this.getGridConfig().addColumn(this.getText("操作时间结束时间"), "operateTimeEDDatetime", true, false, false, "TextField", false, 200, "");

			ColumnConfig columnOperateType = new ColumnConfig(this.getText("操作类型"), "enumConstByFlagOperateType.name", true, true, true, "TextField", false, 100, "");
			String sql1 = "select a.id ,a.name from enum_const a where a.namespace='FlagOperateType' and code in ('3','4')";
			columnOperateType.setComboSQL(sql1);
			this.getGridConfig().addColumn(columnOperateType);
			this.getGridConfig().addColumn(this.getText("操作学时"), "operateAmount", false, false, true, "sumAmount");
			this.getGridConfig().addColumn(this.getText("账户剩余学时数"), "accountAmount", false, false, true, "amount");
			this.getGridConfig().addColumn(this.getText("订单号"), "orderId", false, false, false, "amount");
			this.getGridConfig().addColumn(this.getText("记录号"), "recordId", false, false, false, "amount");
		} else if ("c".equals(forg) && flag.equals("old")) { // 充值记录
			this.getGridConfig().setTitle(this.getText("充值记录"));
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("操作时间"), "operateTimeDatetime", false, false, true, "TextField", false, 200, "");
			this.getGridConfig().addColumn(this.getText("操作时间开始时间"), "operateTimeSTDatetime", true, false, false, "TextField", false, 200, "");
			this.getGridConfig().addColumn(this.getText("操作时间结束时间"), "operateTimeEDDatetime", true, false, false, "TextField", false, 200, "");

			ColumnConfig columnOperateType = new ColumnConfig(this.getText("操作类型"), "enumConstByFlagOperateType.name", true, true, true, "TextField", false, 100, "");
			String sql1 = "select a.id ,a.name from enum_const a where a.namespace='FlagOperateType' and code in ('0')";
			columnOperateType.setComboSQL(sql1);
			this.getGridConfig().addColumn(columnOperateType);
			this.getGridConfig().addColumn(this.getText("操作学时"), "operateAmount", false, false, true, "sumAmount");
			this.getGridConfig().addColumn(this.getText("账户剩余学时数"), "accountAmount", false, false, true, "amount");
			this.getGridConfig().addColumn(this.getText("订单号"), "orderId", false, false, false, "amount");
			this.getGridConfig().addColumn(this.getText("记录号"), "recordId", false, false, false, "amount");
		} else if ("d".equals(forg) && flag.equals("old")) { // 预付费购买记录
			this.getGridConfig().setTitle(this.getText("预付费购买记录"));
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("操作时间"), "operateTimeDatetime", false, false, true, "TextField", false, 200, "");
			this.getGridConfig().addColumn(this.getText("操作时间开始时间"), "operateTimeSTDatetime", true, false, false, "TextField", false, 200, "");
			this.getGridConfig().addColumn(this.getText("操作时间结束时间"), "operateTimeEDDatetime", true, false, false, "TextField", false, 200, "");

			ColumnConfig columnOperateType = new ColumnConfig(this.getText("操作类型"), "enumConstByFlagOperateType.name", true, true, true, "TextField", false, 100, "");
			String sql1 = "select a.id ,a.name from enum_const a where a.namespace='FlagOperateType' and code in ('5')";
			columnOperateType.setComboSQL(sql1);
			this.getGridConfig().addColumn(columnOperateType);
			this.getGridConfig().addColumn(this.getText("操作学时"), "operateAmount", false, false, true, "sumAmount");
			this.getGridConfig().addColumn(this.getText("账户剩余学时数"), "accountAmount", false, false, true, "amount");
			this.getGridConfig().addColumn(this.getText("订单号"), "orderId", false, false, false, "amount");
			this.getGridConfig().addColumn(this.getText("记录号"), "recordId", false, false, false, "amount");
		} else if ("e".equals(forg) && flag.equals("old")) { // 退费记录
			this.getGridConfig().setTitle(this.getText("退费记录"));
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("操作时间"), "operateTimeDatetime", false, false, true, "TextField", false, 200, "");
			this.getGridConfig().addColumn(this.getText("操作时间开始时间"), "operateTimeSTDatetime", true, false, false, "TextField", false, 200, "");
			this.getGridConfig().addColumn(this.getText("操作时间结束时间"), "operateTimeEDDatetime", true, false, false, "TextField", false, 200, "");

			ColumnConfig columnOperateType = new ColumnConfig(this.getText("操作类型"), "enumConstByFlagOperateType.name", true, true, true, "TextField", false, 100, "");
			String sql1 = "select a.id ,a.name from enum_const a where a.namespace='FlagOperateType' and code in ('6')";
			columnOperateType.setComboSQL(sql1);
			this.getGridConfig().addColumn(columnOperateType);
			this.getGridConfig().addColumn(this.getText("操作学时"), "operateAmount", false, false, true, "sumAmount");
			this.getGridConfig().addColumn(this.getText("账户剩余学时数"), "accountAmount", false, false, true, "amount");
			this.getGridConfig().addColumn(this.getText("订单号"), "orderId", false, false, false, "amount");
			this.getGridConfig().addColumn(this.getText("记录号"), "recordId", false, false, false, "amount");
		}
		this.getGridConfig().addRenderScript(
				this.getText("查看详情"),
				"{if(record.data['enumConstByFlagOperateType.name']=='分配-学员') {return '<a href=\"/entity/basic/prepaidAccountSearch_toViewStudentAdd.action?recordId='+record.data['id']+'\"target=\"_blank\"><font color=#0000ff ><u>查看订单</u></font></a>';}"
						+ "else if(record.data['enumConstByFlagOperateType.name']=='剥离-学员'){return '<a href=\"/entity/basic/prepaidAccountSearch_toViewStudentDel.action?recordId='+record.data['id']+'\"target=\"_blank\"><font color=#0000ff ><u>查看订单</u></font></a>';}"
						+ "else if(record.data['enumConstByFlagOperateType.name']=='分配-二级集体'){return '<a href=\"/entity/basic/prepaidAccountSearch_toViewEnterpriseAdd.action?recordId='+record.data['id']+'\"target=\"_blank\"><font color=#0000ff ><u>查看订单</u></font></a>';}"
						+ "else if(record.data['enumConstByFlagOperateType.name']=='剥离-二级集体'){return '<a href=\"/entity/basic/prepaidAccountSearch_toViewEnterpriseDel.action?recordId='+record.data['id']+'\"target=\"_blank\"><font color=#0000ff ><u>查看订单</u></font></a>';}"
						+ "else if(record.data['enumConstByFlagOperateType.name']=='充值'){return '<a href=/entity/basic/buyClassHourRecord.action?tuifei=2&id='+record.data['orderId']+'&method=purseAmount><font color=#0000ff ><u>查看订单</u></font></a>';}"
						+ "else if(record.data['enumConstByFlagOperateType.name']=='消费'){return '<a href=/entity/basic/buyClassHourRecord.action?id='+record.data['orderId']+'&method=consume><font color=#0000ff ><u>查看订单</u></font></a>';}" +
						// "else
						// if(record.data['enumConstByFlagOperateType.name']=='退费'){return
						// '<a
						// href=/entity/basic/refoundManageFinal_viewDetail.action?id='+record.data['orderId']+'&method=consume><font
						// color=#0000ff ><u>查看订单</u></font></a>';}" +
						"else{}}", "id");

		// Lee end
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/checkClassHourRecord";
	}

	/**
	 * 重写框架方法：数据库查询--学时记录（带sql条件）
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public Page list() {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if ((this.userId == null || "".equalsIgnoreCase(this.userId)) && !"3".equalsIgnoreCase(us.getRoleId())) {
			this.userId = us.getLoginId();
		}
		StringBuffer sqlBuffer = new StringBuffer();
		if ("y".equals(forg) && flag.equals("old")) { // 分配记录
			sqlBuffer.append(" SELECT * FROM ( ");
			sqlBuffer.append(" SELECT  AR.ID ID, TO_CHAR(ASSIGN_DATE,'yyyy-MM-dd hh24:mi:ss') OPERATETIME, '' OPERATETIMESTDATETIME,'' OPERATETIMEEDDATETIME, ");
			sqlBuffer.append(" EC_TYPE.NAME OPERATETYPE,OPERATE_AMOUNT OPERATEAMOUNT,ACCOUNT_AMOUNT ACCOUNTAMOUNT,FK_ORDER_ID ORDERID");
			sqlBuffer.append(" FROM ASSIGN_RECORD AR JOIN SSO_USER SU ON SU.ID=AR.FK_USER_ID ");
			sqlBuffer.append(" JOIN ENUM_CONST EC_TYPE ");
			sqlBuffer.append(" ON AR.FLAG_OPERATE_TYPE = EC_TYPE.ID ");
			sqlBuffer.append(" AND NAMESPACE = 'FlagOperateType' AND EC_TYPE.CODE IN('1','2') ");
			sqlBuffer.append(" WHERE SU.LOGIN_ID = '" + userId + "' order by ASSIGN_DATE desc");
			sqlBuffer.append(") WHERE 1=1   ");
		} else if ("b".equals(forg) && flag.equals("old")) { // 剥离记录
			sqlBuffer.append(" SELECT * FROM ( ");
			sqlBuffer.append(" SELECT  AR.ID ID, TO_CHAR(ASSIGN_DATE,'yyyy-MM-dd hh24:mi:ss') OPERATETIME, '' OPERATETIMESTDATETIME,'' OPERATETIMEEDDATETIME, ");
			sqlBuffer.append(" EC_TYPE.NAME OPERATETYPE,OPERATE_AMOUNT OPERATEAMOUNT,ACCOUNT_AMOUNT ACCOUNTAMOUNT,FK_ORDER_ID ORDERID");
			sqlBuffer.append(" FROM ASSIGN_RECORD AR JOIN SSO_USER SU ON SU.ID=AR.FK_USER_ID ");
			sqlBuffer.append(" JOIN ENUM_CONST EC_TYPE ");
			sqlBuffer.append(" ON AR.FLAG_OPERATE_TYPE = EC_TYPE.ID ");
			sqlBuffer.append(" AND NAMESPACE = 'FlagOperateType' AND EC_TYPE.CODE IN('3','4') ");
			sqlBuffer.append(" WHERE SU.LOGIN_ID = '" + userId + "' order by ASSIGN_DATE desc");
			sqlBuffer.append(") WHERE 1=1   ");
		} else if ("c".equals(forg) && flag.equals("old")) { // 充值记录
			sqlBuffer.append(" SELECT * FROM ( ");
			sqlBuffer.append(" SELECT  AR.ID ID, TO_CHAR(ASSIGN_DATE,'yyyy-MM-dd hh24:mi:ss') OPERATETIME, '' OPERATETIMESTDATETIME,'' OPERATETIMEEDDATETIME, ");
			sqlBuffer.append(" EC_TYPE.NAME OPERATETYPE,OPERATE_AMOUNT OPERATEAMOUNT,ACCOUNT_AMOUNT ACCOUNTAMOUNT,FK_ORDER_ID ORDERID");
			sqlBuffer.append(" FROM ASSIGN_RECORD AR JOIN SSO_USER SU ON SU.ID=AR.FK_USER_ID ");
			sqlBuffer.append(" JOIN ENUM_CONST EC_TYPE ");
			sqlBuffer.append(" ON AR.FLAG_OPERATE_TYPE = EC_TYPE.ID ");
			sqlBuffer.append(" AND NAMESPACE = 'FlagOperateType' AND EC_TYPE.CODE IN('0') ");
			sqlBuffer.append(" WHERE SU.LOGIN_ID = '" + userId + "' order by ASSIGN_DATE desc");
			sqlBuffer.append(") WHERE 1=1   ");
		} else if ("d".equals(forg) && flag.equals("old")) {
			sqlBuffer.append(" SELECT * FROM ( ");
			sqlBuffer.append(" SELECT  AR.ID ID, TO_CHAR(ASSIGN_DATE,'yyyy-MM-dd hh24:mi:ss') OPERATETIME, '' OPERATETIMESTDATETIME,'' OPERATETIMEEDDATETIME, ");
			sqlBuffer.append(" EC_TYPE.NAME OPERATETYPE,OPERATE_AMOUNT OPERATEAMOUNT,ACCOUNT_AMOUNT ACCOUNTAMOUNT,FK_ORDER_ID ORDERID");
			sqlBuffer.append(" FROM ASSIGN_RECORD AR JOIN SSO_USER SU ON SU.ID=AR.FK_USER_ID ");
			sqlBuffer.append(" JOIN ENUM_CONST EC_TYPE ");
			sqlBuffer.append(" ON AR.FLAG_OPERATE_TYPE = EC_TYPE.ID ");
			sqlBuffer.append(" AND NAMESPACE = 'FlagOperateType' AND EC_TYPE.CODE IN('5') ");
			sqlBuffer.append(" WHERE SU.LOGIN_ID = '" + userId + "' order by assign_date desc ");
			sqlBuffer.append(") WHERE 1=1   ");
		} else if ("e".equals(forg) && flag.equals("old")) {
			sqlBuffer.append(" SELECT * FROM ( ");
			sqlBuffer.append(" SELECT  AR.ID ID, TO_CHAR(ASSIGN_DATE,'yyyy-MM-dd hh24:mi:ss') OPERATETIME, '' OPERATETIMESTDATETIME,'' OPERATETIMEEDDATETIME, ");
			sqlBuffer.append(" EC_TYPE.NAME OPERATETYPE,OPERATE_AMOUNT OPERATEAMOUNT,ACCOUNT_AMOUNT ACCOUNTAMOUNT,FK_ORDER_ID ORDERID");
			sqlBuffer.append(" FROM ASSIGN_RECORD AR JOIN SSO_USER SU ON SU.ID=AR.FK_USER_ID ");
			sqlBuffer.append(" JOIN ENUM_CONST EC_TYPE ");
			sqlBuffer.append(" ON AR.FLAG_OPERATE_TYPE = EC_TYPE.ID ");
			sqlBuffer.append(" AND NAMESPACE = 'FlagOperateType' AND EC_TYPE.CODE IN('6') ");
			sqlBuffer.append(" WHERE SU.LOGIN_ID = '" + userId + "'");
			sqlBuffer.append(") WHERE 1=1   ");
		}
		Page page = null;
		try {
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
				/*
				 * 操作时间开始时间 to_date('2014-07-25 08:00:00','yyyy-MM-dd
				 * hh24:mi:ss')
				 */
				if (name.equals("operateTimeSTDatetime")) {
					sqlBuffer.append(" and TO_DATE(OPERATETIME,'yyyy-MM-dd hh24:mi:ss') >= to_date('" + value + "','yyyy-MM-dd hh24:mi:ss')");
				}
				/* 操作时间结束时间 */
				if (name.equals("operateTimeEDDatetime")) {
					sqlBuffer.append(" and TO_DATE(OPERATETIME,'yyyy-MM-dd hh24:mi:ss') <= to_date('" + value + "','yyyy-MM-dd hh24:mi:ss')");
				}
				/* 操作类型 */
				if (name.equals("enumConstByFlagOperateType.name")) {
					sqlBuffer.append(" and OPERATETYPE like '%" + value + "%'");
				}

			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			/* 操作时间开始时间 操作时间结束时间 */
			if (temp.equals("operateTimeDatetime")) {
				this.setSort("OPERATETIME");
			}
			/* 操作类型 */
			if (temp.equals("enumConstByFlagOperateType.name")) {
				this.setSort("OPERATETYPE");
			}

			page = getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}

		return page;
	}

	/**
	 * 查看详情
	 * 
	 * @author linjie
	 * @return
	 */
	public String viewDetail() {
		String id = ServletActionContext.getRequest().getParameter("id");
		initGrid();
		return "grid";
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getForg() {
		return forg;
	}

	public void setForg(String forg) {
		this.forg = forg;
	}

}
