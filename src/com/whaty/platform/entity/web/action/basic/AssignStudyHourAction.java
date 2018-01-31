package com.whaty.platform.entity.web.action.basic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import com.whaty.platform.entity.service.basic.AssignService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;

/**
 * 学生分配剥离学时
 * 
 * @author Administrator
 * 
 */
public class AssignStudyHourAction extends MyBaseAction {
	private AssignService assignService;

	/**
	 * 初始化列表
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle("学员分配剥离学时");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("系统编号"), "regNo", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("学员姓名"), "trueName", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("证件号码"), "cardNo", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("具有从业资格"), "enumConstByIsEmployee.name", true, false, true, "TextField", false, 100,
				true);
		ColumnConfig c_name = new ColumnConfig(this.getText("资格类型"), "enumConstByFlagQualificationsType.name");
		c_name.setAdd(true);
		c_name.setSearch(true);
		c_name.setList(true);
		c_name.setComboSQL("SELECT * FROM ENUM_CONST t WHERE NAMESPACE = 'FlagQualificationsType' and t.code not in('9','90','91')");
		this.getGridConfig().addColumn(c_name);
		this.getGridConfig().addColumn(this.getText("职务"), "position", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("所在地区"), "address", true, true, true, "");
		ColumnConfig c_name1 = new ColumnConfig(this.getText("所在机构"), "peEnterprise.name", true, false, true, "TextField", true, 50, "");
		if (us.getUserLoginType().equals("2") || us.getUserLoginType().equals("4")) {
			String sql = "select t.id, t.name as p_name\n" + "  from pe_enterprise t\n" + " where t.fk_parent_id in\n"
					+ "       (select s.id\n" + "          from pe_enterprise s\n" + "         inner join pe_enterprise_manager t\n"
					+ "            on s.id = t.fk_enterprise_id\n" + "           and t.login_id = '" + us.getLoginId() + "')\n"
					+ "    or t.id in (select s.id\n" + "                  from pe_enterprise s\n"
					+ "                 inner join pe_enterprise_manager t\n" + "                    on s.id = t.fk_enterprise_id\n"
					+ "                   and t.login_id = '" + us.getLoginId() + "')\n" + " order by t.code";
			c_name1.setComboSQL(sql);
		}

		this.getGridConfig().addColumn(c_name1);
		this.getGridConfig().addColumn(this.getText("所在分组"), "groups", true, true, true, "");
		this.getGridConfig().addColumn(this.getText("已使用分配学时"), "amount", false, true, true, "");
		this.getGridConfig().addColumn(this.getText("已分配学时"), "sumAmount", false, true, true, "");
		this.getGridConfig().addColumn(this.getText("未使用分配学时"), "unUseAmount", false, true, true, "");
		this.getGridConfig().addMenuScript(
				this.getText("分配学时"),
				"{" + "var m=grid.getSelections();" + "    var ids='';" + "	if(m.length>0){"
						+ "		for(var i=0,len=m.length;i<len;i++) {"
						+ "			var ss=m[i].get('id');"
						+ "			if(i==0){"
						+ "				ids=ids+ss;"
						+ "			}else {"
						+ "				ids=ids+','+ss;"
						+ "			}"
						+ "		}"
						+ "    }else{"
						+ "     Ext.MessageBox.alert('错误','至少选择一条数据');"
						+ "     return;"
						+ "    }"
						+ ""
						+ "   var classHour=new Ext.form.TextField({"
						+ "    fieldLabel: '分配学时',"
						+ "    valueField:'id',"
						+ "    displayField:'name',"
						+ "    width:250,"
						+ "    regex:new RegExp(/^\\d{1,8}(\\.\\d{1,2})?$/),"
						+ // Lee 修改金额验证
						"    regexText:'输入格式：学时'," + "    typeAhead:true," + "    name:'score',id:'score'" + "    });"
						+ "var ids=new Ext.form.Hidden({name:'ids',value:ids});"
						+ "var style=new Ext.form.Hidden({name:'style',value:'0'});" + "   var formPanel=new Ext.form.FormPanel({"
						+ "    frame:true," + "    labelWidth:100," + "    defaultType:'textfield'," + "    autoScroll:true,"
						+ "    items:[classHour,ids, style]" + "   });" + "" + "   var addModelWin=new Ext.Window({" + "    title:'分配学时',"
						+ "    width:450," + "    height:250," + "    minWidth:300," + "    minHeight:250," + "    layout:'fit',"
						+ "    plain:true," + "    bodyStyle:'padding:5px;'," + "    items:formPanel," + "    buttonAlign:'center',"
						+ "    buttons:[{" + "     text:'分配'," + "     handler:function(){" + "      if(formPanel.form.isValid()){"
						+ "       formPanel.form.submit({" + "        url:'/entity/basic/assignStudyHour_assignHour.action',"
						+ "        waitMsg:'处理中，请稍候...'," + "        success:function(form,action){"
						+ "         var responseArray=action.result;" + "         if(responseArray.success=='true'){"
						+ "          Ext.MessageBox.alert('提示',responseArray.info);" + "          store.load({"
						+ "           params:{start:g_start,limit:g_limit,backParam:'1'}" + "          });"
						+ "          addModelWin.close();" + "         }else{" + "          Ext.MessageBox.alert('错误',responseArray.info);"
						+ "          addModelWin.close();" + "          }" + "        }" + "       });" + "      }" + "     }" + "    },"
						+ "    {text:'取消'," + "     handler:function(){" + "      addModelWin.close();" + "     }}]" + "   });"
						+ "   addModelWin.show();" + "}");
		this.getGridConfig().addMenuScript(
				this.getText("剥离学时"),
				"{" + "var m=grid.getSelections();" + "    var ids='';" + "	if(m.length>0){"
						+ "		for(var i=0,len=m.length;i<len;i++) {"
						+ "			var ss=m[i].get('id');"
						+ "			if(i==0){"
						+ "				ids=ids+ss;"
						+ "			}else {"
						+ "				ids=ids+','+ss;"
						+ "			}"
						+ "		}"
						+ "    }else{"
						+ "     Ext.MessageBox.alert('错误','至少选择一条数据');"
						+ "     return;"
						+ "    }"
						+ ""
						+ "   var classHour=new Ext.form.TextField({"
						+ "    fieldLabel: '剥离学时',"
						+ "    valueField:'id',"
						+ "    displayField:'name',"
						+ "    width:250,"
						+ "    regex:new RegExp(/^\\d{1,8}(\\.\\d{1,2})?$/),"
						+ // Lee 修改金额验证
						"    regexText:'输入格式：学时'," + "    typeAhead:true," + "    name:'score',id:'score'" + "    });"
						+ "var ids=new Ext.form.Hidden({name:'ids',value:ids});"
						+ "var style=new Ext.form.Hidden({name:'style',value:'1'});" + "   var formPanel=new Ext.form.FormPanel({"
						+ "    frame:true," + "    labelWidth:100," + "    defaultType:'textfield'," + "    autoScroll:true,"
						+ "    items:[classHour,ids, style]" + "   });" + "" + "   var addModelWin=new Ext.Window({" + "    title:'剥离学时',"
						+ "    width:450," + "    height:250," + "    minWidth:300," + "    minHeight:250," + "    layout:'fit',"
						+ "    plain:true," + "    bodyStyle:'padding:5px;'," + "    items:formPanel," + "    buttonAlign:'center',"
						+ "    buttons:[{" + "     text:'剥离'," + "     handler:function(){" + "      if(formPanel.form.isValid()){"
						+ "       formPanel.form.submit({" + "        url:'/entity/basic/assignStudyHour_stripHour.action',"
						+ "        waitMsg:'处理中，请稍候...'," + "        success:function(form,action){"
						+ "         var responseArray=action.result;" + "         if(responseArray.success=='true'){"
						+ "          Ext.MessageBox.alert('提示',responseArray.info);" + "          store.load({"
						+ "           params:{start:g_start,limit:g_limit,backParam:'1'}" + "          });"
						+ "          addModelWin.close();" + "         }else{" + "          Ext.MessageBox.alert('错误',responseArray.info);"
						+ "          addModelWin.close();" + "          }" + "        }" + "       });" + "      }" + "     }" + "    },"
						+ "    {text:'取消'," + "     handler:function(){" + "      addModelWin.close();" + "     }}]" + "   });"
						+ "   addModelWin.show();" + "}");

		this.getGridConfig().addMenuScript(this.getText("返回"),
				"{window.location.href='/entity/basic/prepaidAccountManage_toAccountManage.action'}");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		// this.entityClass = PeBzzStudent.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/assignStudyHour";
	}

	/**
	 * 分配/剥离查询列表
	 * 
	 * @author Lee：替换掉原来initDetachedCriteria查询方法 原因：金额排序问题
	 * @return
	 */

	public Page list() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append(" SELECT * FROM (SELECT PBS.ID, SU.LOGIN_ID REGNO, PBS.TRUE_NAME TRUENAME, PBS.CARD_NO CARDNO, EC1.NAME CONGYEZIGE, EC2.NAME ZIGENAME, PBS.POSITION, ");
		sqlBuffer
				.append(" PBS.ADDRESS, PE.NAME JIGOUNAME, PBS.GROUPS, ROUND(SU.AMOUNT,2) AMOUNT, ROUND(SU.SUM_AMOUNT,2) SUMAMOUNT, ROUND(SU.SUM_AMOUNT - SU.AMOUNT,2) UNUSEAMOUNT ");
		sqlBuffer.append(" FROM PE_BZZ_STUDENT PBS INNER JOIN SSO_USER SU ON PBS.FK_SSO_USER_ID = SU.ID ");
		sqlBuffer
				.append(" INNER JOIN (SELECT DISTINCT PE2.ID, PE2.NAME FROM PE_ENTERPRISE PE2 INNER JOIN PE_ENTERPRISE_MANAGER PEM2 ON PE2.ID = PEM2.FK_ENTERPRISE_ID OR PE2.FK_PARENT_ID = PEM2.FK_ENTERPRISE_ID WHERE PEM2.LOGIN_ID = '"
						+ us.getLoginId() + "') PE ");
		sqlBuffer
				.append(" ON PBS.FK_ENTERPRISE_ID = PE.ID INNER JOIN ENUM_CONST EC1 ON PBS.IS_EMPLOYEE = EC1.ID LEFT JOIN ENUM_CONST EC2 ON PBS.ZIGE = EC2.ID ) WHERE 1 = 1 ");
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
			/* 从业资格 */
			if (name.equals("enumConstByIsEmployee.name")) {
				name = "CONGYEZIGE";
			}
			/* 资格类型 */
			if (name.equals("enumConstByFlagQualificationsType.name")) {
				name = "ZIGENAME";
			}
			/* 所在机构 */
			if (name.equals("peEnterprise.name")) {
				name = "JIGOUNAME";
			}
			sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");
		} while (true);
		String temp = this.getSort();
		if (temp != null && temp.indexOf(".") > 1) {
			if (temp.toLowerCase().startsWith("combobox_")) {
				temp = temp.substring(temp.indexOf(".") + 1);
			}
		}
		if (this.getSort() != null && temp != null) {
			/* 从业资格 */
			if (temp.equals("enumConstByIsEmployee.name")) {
				temp = "CONGYEZIGE";
			}
			/* 资格类型 */
			if (temp.equals("enumConstByFlagQualificationsType.name")) {
				temp = "ZIGENAME";
			}
			/* 所在机构 */
			if (temp.equals("peEnterprise.name")) {
				temp = "JIGOUNAME";
			}
			if (temp.equals("id")) {
				temp = "REGNO";
			}
			if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
				if (temp.toLowerCase().indexOf("amount") != -1) {
					sqlBuffer.append(" ORDER BY TO_NUMBER(" + temp + ") DESC ");
				} else {
					sqlBuffer.append(" ORDER BY " + temp + " DESC ");
				}
			} else {
				if (temp.toLowerCase().indexOf("amount") != -1) {
					sqlBuffer.append(" ORDER BY TO_NUMBER(" + temp + ") ASC ");
				} else {
					sqlBuffer.append(" ORDER BY " + temp + " ASC ");
				}
			}
		} else {
			sqlBuffer.append(" ORDER BY REGNO ASC");
		}
		try {
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 框架方法：或者查询列表所需数据
	 * 
	 * @author linjie
	 * @return
	 */
	/**
	 * @Override public DetachedCriteria initDetachedCriteria() { UserSession us =
	 *           (UserSession)
	 *           ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	 *           String sql = "\n" + "select pe.id\n" + " from pe_enterprise
	 *           pe\n" + " inner join pe_enterprise_manager pem on
	 *           pem.fk_enterprise_id = pe.id\n" + " where pem.login_id = '" +
	 *           us.getLoginId() + "'\n" + "\n" + "union\n" + "\n" + "select
	 *           pe.id\n" + " from pe_enterprise pe, pe_enterprise pePar,
	 *           pe_enterprise_manager pem\n" + " where pe.fk_parent_id =
	 *           pePar.Id\n" + " and pePar.Id = pem.fk_enterprise_id\n" + " and
	 *           pem.login_id = '" + us.getLoginId() + "'"; List list = null;
	 *           try { list = this.getGeneralService().getBySQL(sql); } catch
	 *           (EntityException e) { // TODO Auto-generated catch block
	 *           e.printStackTrace(); } String enterpriseId = (String)
	 *           list.get(0);
	 * 
	 * DetachedCriteria dc = null;
	 * 
	 * dc = DetachedCriteria.forClass(PeBzzStudent.class);
	 * dc.createCriteria("peEnterprise", "peEnterprise");
	 * dc.createCriteria("ssoUser", "ssoUser");
	 * dc.createCriteria("enumConstByIsEmployee", "enumConstByIsEmployee",
	 * DetachedCriteria.LEFT_JOIN);
	 * dc.createCriteria("enumConstByFlagQualificationsType",
	 * "enumConstByFlagQualificationsType", DetachedCriteria.LEFT_JOIN);
	 * dc.add(Restrictions.in("peEnterprise.id", list.toArray(new
	 * String[list.size()]))); return dc; }
	 */
	/**
	 * 统一分配学员学时
	 * 
	 * @return
	 */
	public String assignHour() {
		Map map = new HashMap();
		try {
			String result = this.getAssignService().assignHour();
			if (null == result || "".equals(result)) {
				result = "操作成功！";
				map.put("success", "true");
				map.put("info", result);
			} else {
				map.put("success", "false");
				map.put("info", result);
			}
		} catch (Exception e) {
			map.put("success", "false");
			map.put("info", "操作失败！");
			e.printStackTrace();
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}

	/**
	 * 统一剥离学员学时
	 * 
	 * @return
	 */
	public String stripHour() {
		Map map = new HashMap();
		try {
			String result = this.getAssignService().stripHour();
			if (null == result || "".equals(result)) {
				result = "操作成功！";
				map.put("success", "true");
				map.put("info", result);
			} else {
				map.put("success", "false");
				map.put("info", result);
			}
		} catch (Exception e) {
			map.put("success", "false");
			map.put("info", "操作失败！");
			e.printStackTrace();
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}

	public AssignService getAssignService() {
		return assignService;
	}

	public void setAssignService(AssignService assignService) {
		this.assignService = assignService;
	}

}
