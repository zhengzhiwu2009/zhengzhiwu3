package com.whaty.platform.entity.web.action.basic;

import java.util.Iterator;
import java.util.Map;

import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 预付费账户变动记录
 * 
 * @author DGH
 * 
 */
public class YuFuFeeRecordAction extends MyBaseAction {

	private String userId;

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
		this.getGridConfig().setTitle(this.getText("预付费账户变动记录"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("系统编号"), "loginid", true, false, true, "TextField", false, 200, "");
		this.getGridConfig().addColumn(this.getText("姓名/机构名称"), "truename", true, false, true, "TextField", false, 200, "");
		this.getGridConfig().addColumn(this.getText("操作时间"), "operateTimeDatetime", false, false, true, "TextField", false, 200, "");
		this.getGridConfig().addColumn(this.getText("操作时间开始时间"), "operateTimeSTDatetime", true, false, false, "TextField", false, 200, "");
		this.getGridConfig().addColumn(this.getText("操作时间结束时间"), "operateTimeEDDatetime", true, false, false, "TextField", false, 200, "");

		ColumnConfig columnOperateType = new ColumnConfig(this.getText("操作类型"), "enumConstByFlagOperateType.name", true, true, true, "TextField", false, 100, "");
		String sql1 = "select a.id ,a.name from enum_const a where a.namespace='FlagOperateType'";
		columnOperateType.setComboSQL(sql1);
		this.getGridConfig().addColumn(columnOperateType);
		this.getGridConfig().addColumn(this.getText("操作学时"), "operateAmount", false, false, true, "sumAmount");
		this.getGridConfig().addColumn(this.getText("账户剩余学时数"), "accountAmount", false, false, true, "amount");
		this.getGridConfig().addColumn(this.getText("订单号"), "orderId", false, false, false, "amount");
		this.getGridConfig().addColumn(this.getText("记录号"), "recordId", false, false, false, "amount");
	}

	@Override
	public void setEntityClass() {
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/yuFuFeeRecord";
	}

	/**
	 * 重写框架方法：学时购买记录（带sql条件）
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public Page list() {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" SELECT * ");
		sqlBuffer.append("   FROM (SELECT AR.ID||PEM.LOGIN_ID ID, ");
		sqlBuffer.append("                PEM.LOGIN_ID LOGINID, ");
		sqlBuffer.append("                PE.NAME TRUENAME, ");
		sqlBuffer.append("                TO_CHAR(ASSIGN_DATE, 'yyyy-MM-dd hh24:mi:ss') OPERATETIME, ");
		sqlBuffer.append("                '' OPERATETIMESTDATETIME, ");
		sqlBuffer.append("                '' OPERATETIMEEDDATETIME, ");
		sqlBuffer.append("                EC_TYPE.NAME OPERATETYPE, ");
		sqlBuffer.append("                OPERATE_AMOUNT OPERATEAMOUNT, ");
		sqlBuffer.append("                ACCOUNT_AMOUNT ACCOUNTAMOUNT, ");
		sqlBuffer.append("                FK_ORDER_ID ORDERID ");
		sqlBuffer.append("           FROM ASSIGN_RECORD AR ");// 分配记录表
		sqlBuffer.append("           JOIN PE_ENTERPRISE_MANAGER PEM ");// 集体管理员表
		sqlBuffer.append("             ON PEM.FK_SSO_USER_ID = AR.FK_USER_ID ");
		sqlBuffer.append("           JOIN PE_ENTERPRISE PE ");// 机构表
		sqlBuffer.append("             ON PEM.FK_ENTERPRISE_ID = PE.ID ");
		sqlBuffer.append("           JOIN ENUM_CONST EC_TYPE ");
		sqlBuffer.append("             ON AR.FLAG_OPERATE_TYPE = EC_TYPE.ID ");
		sqlBuffer.append("            AND NAMESPACE = 'FlagOperateType' ");
		sqlBuffer.append("          WHERE PEM.LOGIN_ID = '" + userId + "' ");// 操作人
		sqlBuffer.append("         UNION ");
		sqlBuffer.append("         SELECT AR.ID||ARS.ID ID, ");
		sqlBuffer.append("                PEM.LOGIN_ID LOGINID, ");
		sqlBuffer.append("                PE.NAME TRUENAME, ");
		sqlBuffer.append("                TO_CHAR(ASSIGN_DATE, 'yyyy-MM-dd hh24:mi:ss') OPERATETIME, ");
		sqlBuffer.append("                '' OPERATETIMESTDATETIME, ");
		sqlBuffer.append("                '' OPERATETIMEEDDATETIME, ");
		sqlBuffer.append("                EC_TYPE.NAME OPERATETYPE, ");
		sqlBuffer.append("                ARS.CLASS_NUM OPERATEAMOUNT, ");
		sqlBuffer.append("                ARS.SUBAMOUNT ACCOUNTAMOUNT, ");
		sqlBuffer.append("                FK_ORDER_ID ORDERID ");
		sqlBuffer.append("           FROM ASSIGN_RECORD AR ");// 分配记录表
		sqlBuffer.append("           JOIN PE_ENTERPRISE_MANAGER PEM ");// 机构管理员表
		sqlBuffer.append("             ON PEM.FK_SSO_USER_ID = AR.FK_USER_ID ");
		sqlBuffer.append("           JOIN PE_ENTERPRISE PE ");// 机构表
		sqlBuffer.append("             ON PEM.FK_ENTERPRISE_ID = PE.ID ");
		sqlBuffer.append("           JOIN ENUM_CONST EC_TYPE ");
		sqlBuffer.append("             ON AR.FLAG_OPERATE_TYPE = EC_TYPE.ID ");
		sqlBuffer.append("            AND NAMESPACE = 'FlagOperateType' ");
		sqlBuffer.append("           JOIN ASSIGN_RECORD_STUDENT ARS ");// 分配记录明细表
		sqlBuffer.append("             ON AR.ID = ARS.FK_RECORD_ID ");
		sqlBuffer.append("           JOIN PE_ENTERPRISE_MANAGER PEM2 ");// 机构管理员表(被操作)
		sqlBuffer.append("             ON ARS.FK_ENTERPRISE_MANAGER_ID = PEM2.ID ");
		sqlBuffer.append("          WHERE PEM2.LOGIN_ID = '" + userId + "' ");
		sqlBuffer.append("         UNION ");
		sqlBuffer.append("         SELECT AR.ID||PBS.REG_NO ID, ");
		sqlBuffer.append("                PBS.REG_NO LOGINID, ");
		sqlBuffer.append("                PBS.TRUE_NAME TRUENAME, ");
		sqlBuffer.append("                TO_CHAR(ASSIGN_DATE, 'yyyy-MM-dd hh24:mi:ss') OPERATETIME, ");
		sqlBuffer.append("                '' OPERATETIMESTDATETIME, ");
		sqlBuffer.append("                '' OPERATETIMEEDDATETIME, ");
		sqlBuffer.append("                EC_TYPE.NAME OPERATETYPE, ");
		sqlBuffer.append("                OPERATE_AMOUNT OPERATEAMOUNT, ");
		sqlBuffer.append("                ACCOUNT_AMOUNT ACCOUNTAMOUNT, ");
		sqlBuffer.append("                FK_ORDER_ID ORDERID ");
		sqlBuffer.append("           FROM ASSIGN_RECORD AR ");// 分配记录表
		sqlBuffer.append("           JOIN PE_BZZ_STUDENT PBS ");// 学员表
		sqlBuffer.append("             ON PBS.FK_SSO_USER_ID = AR.FK_USER_ID ");
		sqlBuffer.append("           JOIN PE_ENTERPRISE_MANAGER PEM ");// 机构管理员表
		sqlBuffer.append("             ON PBS.FK_ENTERPRISE_ID = PEM.FK_ENTERPRISE_ID ");
		sqlBuffer.append("           JOIN ENUM_CONST EC_TYPE ");
		sqlBuffer.append("             ON AR.FLAG_OPERATE_TYPE = EC_TYPE.ID ");
		sqlBuffer.append("            AND NAMESPACE = 'FlagOperateType' ");
		sqlBuffer.append("          WHERE PEM.LOGIN_ID = '" + userId + "' ");
		sqlBuffer.append("         UNION ");
		sqlBuffer.append("         SELECT AR.ID||ARS.ID ID, ");
		sqlBuffer.append("                PBS.REG_NO LOGINID, ");
		sqlBuffer.append("                PBS.TRUE_NAME TRUENAME, ");
		sqlBuffer.append("                TO_CHAR(ASSIGN_DATE, 'yyyy-MM-dd hh24:mi:ss') OPERATETIME, ");
		sqlBuffer.append("                '' OPERATETIMESTDATETIME, ");
		sqlBuffer.append("                '' OPERATETIMEEDDATETIME, ");
		sqlBuffer.append("                EC_TYPE.NAME OPERATETYPE, ");
		sqlBuffer.append("                ARS.CLASS_NUM OPERATEAMOUNT, ");
		sqlBuffer.append("                ARS.SUBAMOUNT ACCOUNTAMOUNT, ");
		sqlBuffer.append("                FK_ORDER_ID ORDERID ");
		sqlBuffer.append("           FROM ASSIGN_RECORD AR ");// 分配记录表
		sqlBuffer.append("           JOIN ASSIGN_RECORD_STUDENT ARS ");// 分配详情表
		sqlBuffer.append("             ON AR.ID = ARS.FK_RECORD_ID ");
		sqlBuffer.append("           JOIN PE_BZZ_STUDENT PBS ");// 学员表
		sqlBuffer.append("             ON ARS.FK_STUDENT_ID = PBS.ID ");
		sqlBuffer.append("           JOIN PE_ENTERPRISE_MANAGER PEM ");// 集体管理员表
		sqlBuffer.append("             ON PBS.FK_ENTERPRISE_ID = PEM.FK_ENTERPRISE_ID ");
		sqlBuffer.append("           JOIN ENUM_CONST EC_TYPE ");
		sqlBuffer.append("             ON AR.FLAG_OPERATE_TYPE = EC_TYPE.ID ");
		sqlBuffer.append("            AND NAMESPACE = 'FlagOperateType' ");
		sqlBuffer.append("          WHERE PEM.LOGIN_ID = '" + userId + "') ");
		sqlBuffer.append("  WHERE 1 = 1 ");
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
				if ("loginid".equalsIgnoreCase(name) || "truename".equalsIgnoreCase(name)) {
					sqlBuffer.append(" AND UPPER(" + name + ") LIKE '%" + value.toUpperCase() + "%'");
				}
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (temp.equals("operateTimeDatetime")) {
				this.setSort("TO_DATE(OPERATETIME,'yyyy-MM-dd hh24:mi:ss')");
			}
			if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
				if ("id".equalsIgnoreCase(temp)) {
					sqlBuffer.append(" order by TO_DATE(OPERATETIME,'yyyy-MM-dd hh24:mi:ss') desc ");
				} else if ("operateAmount".equalsIgnoreCase(temp) || "accountAmount".equalsIgnoreCase(temp)) {
					sqlBuffer.append(" order by to_number(" + temp + ") desc ");
				} else {
					/* 操作时间 */
					if ("operateTimeDatetime".equalsIgnoreCase(temp)) {
						sqlBuffer.append(" order by TO_DATE(OPERATETIME,'yyyy-MM-dd hh24:mi:ss') desc ");
					} else if ("enumConstByFlagOperateType.name".equalsIgnoreCase(temp)) {// 操作类型
						sqlBuffer.append(" order by OPERATETYPE desc ");
					} else {
						sqlBuffer.append(" order by " + temp + " desc ");
					}
				}
			} else {
				if ("id".equalsIgnoreCase(temp)) {
					sqlBuffer.append(" order by TO_DATE(OPERATETIME,'yyyy-MM-dd hh24:mi:ss') asc ");
				} else if ("operateAmount".equalsIgnoreCase(temp) || "accountAmount".equalsIgnoreCase(temp)) {
					sqlBuffer.append(" order by to_number(" + temp + ") asc ");
				} else {
					/* 操作时间 */
					if ("operateTimeDatetime".equalsIgnoreCase(temp)) {
						sqlBuffer.append(" order by TO_DATE(OPERATETIME,'yyyy-MM-dd hh24:mi:ss') asc ");
					} else if ("enumConstByFlagOperateType.name".equalsIgnoreCase(temp)) {// 操作类型
						sqlBuffer.append(" order by OPERATETYPE asc ");
					} else {
						sqlBuffer.append(" order by " + temp + " asc ");
					}
				}
			}
			page = getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
}
