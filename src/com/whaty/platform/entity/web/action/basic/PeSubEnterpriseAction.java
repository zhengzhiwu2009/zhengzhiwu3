package com.whaty.platform.entity.web.action.basic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.service.basic.PeEnterpriseBacthService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.entity.util.MyUtil;

/**
 * 二级机构分配剥离学时
 * 
 * @author Administrator
 * 
 */
public class PeSubEnterpriseAction extends MyBaseAction {

	private String id;
	private String name;
	private String fzrDepart;
	private String operateresult;
	private PeEnterpriseManager manager;

	private EnumConstService enumConstService;
	private PeEnterpriseBacthService enterpriseBacthService;
	private GeneralService generalService;

	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}

	public PeEnterpriseBacthService getEnterpriseBacthService() {
		return enterpriseBacthService;
	}

	public void setEnterpriseBacthService(PeEnterpriseBacthService enterpriseBacthService) {
		this.enterpriseBacthService = enterpriseBacthService;
	}

	/**
	 * 初始化列表
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if ("2".equalsIgnoreCase(us.getUserLoginType()) || "5".equals(us.getUserLoginType())) {
			this.getGridConfig().setCapability(true, true, false);
		} else {
			this.getGridConfig().setCapability(false, false, false);
		}

		this.getGridConfig().setTitle("二级机构单位列表");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false, false, false, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("用户名"), "loginId", true, false, true, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("二级机构名称"), "name", true, true, true, "TextField", false, 100);
		this.getGridConfig().addColumn(this.getText("管理员姓名"), "pename", true, true, true, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("所在部门"), "fzrDepart", false, true, false, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("联系电话"), "phone", true, true, true, Const.phone_number_for_extjs);
		this.getGridConfig().addColumn(this.getText("学员人数"), "num", false, false, true, "");
		if (!"5".equals(us.getUserLoginType())) {
			this.getGridConfig().addColumn(this.getText("已分配学时"), "sumAmount", false, false, true, "TextField", false, 50);
			this.getGridConfig().addColumn(this.getText("已使用学时"), "amount", false, false, true, "TextField", false, 50);
			this.getGridConfig().addColumn(this.getText("剩余学时"), "balance", false, false, true, "TextField", true, 50);
		}
		this.getGridConfig().addColumn(this.getText("机构ID"), "pid", false, false, false, "TextField", true, 50);

		ColumnConfig c_name2 = new ColumnConfig(this.getText("是否有效"), "combobox_elective", false, false, false, "TextField", true, 25, "");

		c_name2.setComboSQL("select b.id,b.name from enum_const b where b.namespace='FlagEnterpriseState' order by b.code");

		this.getGridConfig().addColumn(c_name2);

		this.getGridConfig().addColumn(this.getText("移动电话"), "mophone", false, true, false, "TextField", true, 20,
				"regex:new RegExp(/^(\\+86)?0?1[3|5|8]\\d{9}$/),regexText:'请输入手机号码',");
		this.getGridConfig().addColumn(this.getText("电子邮箱"), "pememail", false, true, false, "TextField", false, 50,
				"regex:new RegExp(/^\\w+([-+.]\\w+)*@\\w+([-]\\w+)*(\\.\\w+([-]\\w+)*){1,3}$/),regexText:'请输入正确格式邮箱',");

		this.getGridConfig().addRenderScript(this.getText("学员信息"),
				"{return '<a href=peDetail.action?id='+record.data['pid']+'&type=enterprise>查看</a>';}", "");
		this.getGridConfig().addRenderScript(this.getText("二级机构单位信息"),
				"{return '<a href=peSubEnterprise_viewDetail.action?id='+record.data['pid']+'>查看详情</a>';}", "");

		if (us.getUserLoginType().equals("2") || "5".equals(us.getUserLoginType())) {

			if ("inner".equals(this.getFlag())) {
				this.getGridConfig().addMenuScript(
						this.getText("分配学时"),
						"{" +

						"var m=grid.getSelections();" + "    var ids='';"
								+ "	if(m.length>0){"
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
								+ "    fieldLabel: '分配学时数',"
								+ "    valueField:'id',"
								+ "    displayField:'name',"
								+ "    width:250,"
								+ "    regex:new RegExp(/^\\d{1,8}(\\.\\d{1,2})?$/),"
								+ // Lee 修改金额验证
								"    regexText:'输入格式：学时'," + "    typeAhead:true," + "    name:'score',id:'score'" + "    });"
								+ "var ids=new Ext.form.Hidden({name:'ids',value:ids});"
								+ "var style=new Ext.form.Hidden({name:'style',value:'0'});" + "   var formPanel=new Ext.form.FormPanel({"
								+ "    frame:true," + "    labelWidth:100," + "    defaultType:'textfield'," + "    autoScroll:true,"
								+ "    items:[classHour,ids, style]" + "   });" + "" + "   var addModelWin=new Ext.Window({"
								+ "    title:'分配集体管理员学时'," + "    width:450," + "    height:250," + "    minWidth:300,"
								+ "    minHeight:250," + "    layout:'fit'," + "    plain:true," + "    bodyStyle:'padding:5px;',"
								+ "    items:formPanel," + "    buttonAlign:'center'," + "    buttons:[{" + "     text:'分配',"
								+ "     handler:function(){" + "      if(formPanel.form.isValid()){" + "       formPanel.form.submit({"
								+ "        url:'/entity/basic/assignStudyHourEnt_assignHour.action'," + "        waitMsg:'处理中，请稍候...',"
								+ "        success:function(form,action){" + "         var responseArray=action.result;"
								+ "         if(responseArray.success=='true'){"
								+ "          Ext.MessageBox.alert('提示',responseArray.info);" + "          store.load({"
								+ "           params:{start:g_start,limit:g_limit}" + "          });" + "          addModelWin.close();"
								+ "         }else{" + "          Ext.MessageBox.alert('错误',responseArray.info);"
								+ "          addModelWin.close();" + "          }" + "        }" + "       });" + "      }" + "     }"
								+ "    }," + "    {text:'取消'," + "     handler:function(){" + "      addModelWin.close();" + "     }}]"
								+ "   });" + "   addModelWin.show();" + "}");

				this.getGridConfig().addMenuScript(
						this.getText("剥离学时"),
						"{" +

						"var m=grid.getSelections();" + "    var ids='';"
								+ "	if(m.length>0){"
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
								+ "    fieldLabel: '剥离学时数',"
								+ "    valueField:'id',"
								+ "    displayField:'name',"
								+ "    width:250,"
								+ "    regex:new RegExp(/^\\d{1,8}(\\.\\d{1,2})?$/),"
								+ // Lee 修改金额验证
								"    regexText:'输入格式：学时'," + "    typeAhead:true," + "    name:'score',id:'score'" + "    });"
								+ "var ids=new Ext.form.Hidden({name:'ids',value:ids});"
								+ "var style=new Ext.form.Hidden({name:'style',value:'1'});" + "   var formPanel=new Ext.form.FormPanel({"
								+ "    frame:true," + "    labelWidth:100," + "    defaultType:'textfield'," + "    autoScroll:true,"
								+ "    items:[classHour,ids, style]" + "   });" + "" + "   var addModelWin=new Ext.Window({"
								+ "    title:'剥离集体管理员学时'," + "    width:450," + "    height:250," + "    minWidth:300,"
								+ "    minHeight:250," + "    layout:'fit'," + "    plain:true," + "    bodyStyle:'padding:5px;',"
								+ "    items:formPanel," + "    buttonAlign:'center'," + "    buttons:[{" + "     text:'剥离',"
								+ "     handler:function(){" + "      if(formPanel.form.isValid()){" + "       formPanel.form.submit({"
								+ "        url:'/entity/basic/assignStudyHourEnt_stripHour.action'," + "        waitMsg:'处理中，请稍候...',"
								+ "        success:function(form,action){" + "         var responseArray=action.result;"
								+ "         if(responseArray.success=='true'){"
								+ "          Ext.MessageBox.alert('提示',responseArray.info);" + "          store.load({"
								+ "           params:{start:g_start,limit:g_limit}" + "          });" + "          addModelWin.close();"
								+ "         }else{" + "          Ext.MessageBox.alert('错误',responseArray.info);"
								+ "          addModelWin.close();" + "          }" + "        }" + "       });" + "      }" + "     }"
								+ "    }," + "    {text:'取消'," + "     handler:function(){" + "      addModelWin.close();" + "     }}]"
								+ "   });" + "   addModelWin.show();" + "}");
				this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back()}");
			} else {
				this.getGridConfig().addRenderScript(this.getText("修改信息"),
						"{return '<a href=peSubEnterprise_updateDetail.action?id='+record.data['pid']+'>修改</a>';}", "");
				this.getGridConfig().addMenuFunction("重置密码（默认" + Const.defaultPwd + "）", "resetpwd");
			}
			// this.getGridConfig().addMenuFunction(
			// this.getText("设为有效"),"elective.on");
			// this.getGridConfig().addMenuFunction(
			// this.getText("设为无效"),"elective.off");
		} else {
			this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back()}");
		}
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeEnterpriseManager.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/peSubEnterprise";
	}

	public PeSubInner getBean() {
		// TODO Auto-generated method stub
		return (PeSubInner) super.superGetBean();
	}

	public void setBean(PeSubInner peSubInner) {
		// TODO Auto-generated method stub
		super.superSetBean(peSubInner);
	}

	/**
	 * 修改信息
	 * 
	 * @author linjie
	 * @return
	 */
	public String updateDetail() {
		this.setInfo();
		return "updateInfo";
	}

	/**
	 * 获得当前登录用户所在的机构
	 * 
	 * @author linjie
	 * @return
	 */
	private PeEnterprise getPeEnterpriseByUs(UserSession us) {
		DetachedCriteria dc = null;
		dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
		dc.createCriteria("peEnterprise");
		dc.createCriteria("ssoUser", "ssoUser");
		dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
		List<PeEnterpriseManager> list = null;
		PeEnterpriseManager peEnterpriseManager = null;
		try {
			list = this.getGeneralService().getList(dc);
			if (null != list && list.size() > 0) {
				peEnterpriseManager = list.get(0);
			} else {
				peEnterpriseManager = new PeEnterpriseManager();
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return peEnterpriseManager.getPeEnterprise();
	}

	/**
	 * 重写框架方法--列更新
	 * 
	 * @author linjie
	 * @return
	 */
	public Map updateColumn() {
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			List<String> idList = new ArrayList();
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			String acrion = this.getColumn();
			boolean istrue = true;
			if (acrion.equals("elective.on")) {
				istrue = this.getEnterpriseBacthService().setValivd(idList);
				map.put("success", "true");
				map.put("info", "有" + ids.length + "个机构被设置成功！");
			} else if (acrion.equals("elective.off")) {
				istrue = this.getEnterpriseBacthService().setInValivd(idList);
				if (!istrue) {
					map.clear();
					map.put("success", "false");
					map.put("info", this.getText("所选机构下已存在学员或报名信息，不能设为无效!"));
				} else if (istrue) {
					map.clear();
					map.put("success", "true");
					map.put("info", this.getText("设置成功," + ids.length + "个机构被设为无效!"));
				}
			} else if ("resetpwd".equals(acrion)) {
				istrue = this.getEnterpriseBacthService().resetPassword(idList);
				map.put("success", "true");
				map.put("info", "有" + ids.length + "个二级机构被重置密码成功！");
			}
		} else {
			map.put("success", "false");
			map.put("info", "操作失败");
		}
		return map;
	}

	/**
	 * 查看详情
	 * 
	 * @author linjie
	 * @return
	 */
	public String viewDetail() {
		this.setInfo();
		return "viewDetail";
	}

	/**
	 * 设置信息
	 * 
	 * @author linjie
	 * @return
	 */
	public void setInfo() {
		DetachedCriteria viwedc = DetachedCriteria.forClass(PeEnterpriseManager.class);
		viwedc.createCriteria("peEnterprise", "peEnterprise");
		viwedc.add(Restrictions.eq("peEnterprise.id", id));
		PeEnterpriseManager peEnterpriseManager = null;
		try {
			if (this.getGeneralService().getDetachList(viwedc) != null && this.getGeneralService().getDetachList(viwedc).size() > 0) {

				peEnterpriseManager = (PeEnterpriseManager) this.getGeneralService().getDetachList(viwedc).get(0);
			} else {

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setManager(peEnterpriseManager);
	}

	/**
	 * 编辑信息页面
	 * 
	 * @author linjie
	 * @return
	 */
	public String editInfo() {
		DetachedCriteria viwedc = DetachedCriteria.forClass(PeEnterpriseManager.class);
		viwedc.createAlias("peEnterprise", "peEnterprise");
		viwedc.add(Restrictions.eq("peEnterprise.id", id));
		try {

			PeEnterpriseManager peEnterpriseManager = (PeEnterpriseManager) this.getGeneralService().getList(viwedc).get(0);
			PeEnterprise peEnterprise = peEnterpriseManager.getPeEnterprise();
			// 设置二级机构的名称
			DetachedCriteria dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
			dc.createCriteria("ssoUser", "ssoUser");
			dc.createCriteria("peEnterprise", "peEnterprise");
			UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
			dc.add(Restrictions.eq("ssoUser.id", us.getSsoUser().getId()));
			PeEnterpriseManager parentEnterpriseManager = (PeEnterpriseManager) this.getGeneralService().getList(dc).get(0);
			String subName = parentEnterpriseManager.getPeEnterprise().getName() + "-" + name;
			peEnterprise.setName(subName);

			peEnterprise.setSubName(name);
			peEnterprise.setFzrDepart(fzrDepart);
			peEnterpriseManager.setPeEnterprise(peEnterprise);
			peEnterpriseManager.setEmail(manager.getEmail());
			peEnterpriseManager.setName(manager.getName());
			peEnterpriseManager.setPhone(manager.getPhone());
			peEnterpriseManager.setMobilePhone(manager.getMobilePhone());
			this.getGeneralService().updateEnterpriseManager(peEnterpriseManager);
			operateresult = "1";
		} catch (Exception e) {
			operateresult = "2";
			e.printStackTrace();
		}
		return "editInfo";
	}

	/**
	 * 重写框架方法：二级机构信息（带sql条件）
	 * 
	 * @author linjie
	 * @return
	 */
	public Page list() {
		Page page = null;
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String pid = "";
		String sql_pid = "";
		if (null != this.getBean() && !"".equals(this.getBean()) && this.getBean().getPeEnterprise() != null) {
			pid = this.getBean().getPeEnterprise().getId();
		} else {
			pid = this.getPeEnterpriseByUs(us).getId();
		}
		sql_pid = " and p.fk_parent_id = '" + pid + "' ";
		try {
			StringBuffer sql_temp = new StringBuffer();
			sql_temp.append("SELECT * ");
			sql_temp.append("FROM   (SELECT p.id                   AS id, ");
			sql_temp.append("               pem.login_id           AS loginId, ");
			sql_temp.append("               p.name                 AS name, ");
			sql_temp.append("               pem.name               AS pename, ");
			sql_temp.append("               p.fzr_depart           AS fzrDepart, ");
			sql_temp.append("               pem.phone              AS phone, ");
			// sql_temp.append(" p.code AS code, ");
			// sql_temp.append(" pp.name AS p_name, ");
			sql_temp.append("               Count(DISTINCT ps.id)  AS num, ");
			if ("56".indexOf(us.getUserLoginType()) == -1) {
				sql_temp.append("               round(pem.sum_amount,2) AS sumAmount, ");
				sql_temp.append("               round(pem.amount,2) AS amount, ");
				sql_temp.append("               round(nvl(pem.sum_amount,0.0)-nvl(pem.amount,0.0),2) AS balance, ");
			}
			sql_temp.append("				pem.fk_enterprise_id as pid,");
			// sql_temp.append(" Count(DISTINCT pem.id) AS mngnum, ");
			sql_temp.append("               ec.name                AS combobox_elective, ");
			// sql_temp.append(" pem.mobile_phone AS mophone, ");
			sql_temp.append("               pem.email              AS pememail, ");
			sql_temp.append("               p.industry             AS industry, ");
			sql_temp.append("               p.address              AS address, ");
			sql_temp.append("               p.zipcode              AS zipcode, ");
			sql_temp.append("               p.fax                  AS fax, ");
			sql_temp.append("               p.info_sheng           AS infoSheng, ");
			sql_temp.append("               p.info_shi             AS infoShi, ");
			sql_temp.append("               p.info_jiedao          AS infoJiedao, ");
			sql_temp.append("               p.fzr_name             AS fzrName, ");
			sql_temp.append("               p.fzr_xb               AS fzrXb, ");
			// sql_temp.append(" p.fzr_depart AS fzrDepart, ");
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
			sql_temp.append("        FROM   enum_const ec, ");
			sql_temp
					.append("               pe_enterprise p  INNER JOIN PE_ENTERPRISE_MANAGER m  ON p.id =m.fk_enterprise_id  AND m.flag_isvalid !='3' ");
			// sql_temp.append(" INNER JOIN pe_enterprise pp ");
			// sql_temp.append(" ON p.fk_parent_id = pp.id ");
			sql_temp.append("               LEFT OUTER JOIN (SELECT ps.id, ");
			sql_temp.append("                                       ps.fk_enterprise_id ");
			sql_temp.append("                                FROM   pe_bzz_student ps, ");
			sql_temp.append("                                       sso_user su ");
			// sql_temp.append(" pe_bzz_batch b ");
			sql_temp.append("                                WHERE  ps.fk_sso_user_id = su.id ");
			sql_temp.append("                                       AND su.flag_isvalid = '2' ");
			// sql_temp.append(" AND ps.flag_rank_state =
			// '402880f827f5b99b0127f5bdadc70006' ");
			sql_temp.append("                                      )ps ");
			sql_temp.append("                 ON p.id = ps.fk_enterprise_id ");
			sql_temp.append("               JOIN (SELECT DISTINCT pem.id, ");
			sql_temp.append("                                                pem.login_id, ");
			sql_temp.append("                                                pem.name, ");
			sql_temp.append("                                                pem.mobile_phone, ");
			sql_temp.append("                                                pem.phone, ");
			sql_temp.append("                                                pem.email, ");
			sql_temp.append("                                                pem.fk_enterprise_id,u.sum_amount,u.amount ");
			sql_temp.append("                                FROM   pe_enterprise_manager pem ");
			sql_temp.append("                                       INNER JOIN sso_user u ");
			sql_temp.append("                                         ON pem.fk_sso_user_id = u.id ");
			// sql_temp.append(" WHERE u.fk_role_id =
			// '402880f322736b760122737a968a0010' ");
			// sql_temp.append(" OR u.fk_role_id =
			// '402880f322736b760122737a60c40008'" );
			sql_temp.append(") pem ");
			sql_temp.append("                 ON p.id = pem.fk_enterprise_id ");
			sql_temp.append("        WHERE  p.FLAG_ELECTIVE = ec.id ");
			sql_temp.append(sql_pid);
			sql_temp.append("               AND p.fk_parent_id IS NOT NULL ");
			sql_temp.append("        GROUP  BY p.id, ");
			sql_temp.append("                  pem.login_id, ");
			sql_temp.append("                  pem.fk_enterprise_id, ");
			sql_temp.append("                  pem.name, ");
			sql_temp.append("                  pem.phone, ");
			sql_temp.append("                  pem.email, ");
			sql_temp.append("                  p.name, ");
			sql_temp.append("                  p.code, ");
			// sql_temp.append(" pp.name, ");
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
			sql_temp.append("                  pem.amount, ");
			sql_temp.append("                  pem.sum_amount, ");
			sql_temp.append("                  ec.name) p ");
			sql_temp.append("WHERE  1 = 1 ");
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
				sql_temp.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					if (temp.toLowerCase().indexOf("balance") != -1 && temp.toLowerCase().indexOf("amount") != -1) {
						sql_temp.append(" ORDER BY TO_NUMBER(" + temp + ") DESC ");
					} else {
						sql_temp.append(" ORDER BY " + temp + " DESC ");
					}
				} else {
					if (temp.toLowerCase().indexOf("amount") != -1) {
						sql_temp.append(" ORDER BY TO_NUMBER(" + temp + ") ASC ");
					} else {
						sql_temp.append(" ORDER BY " + temp + " ASC ");
					}
				}
			} else {
				sql_temp.append(" ORDER BY LOGINID ASC");
			}
			page = this.getGeneralService().getByPageSQL(sql_temp.toString(), Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}

		return page;
	}

	/**
	 * 重写框架方法：添加数据
	 * 
	 * @author linjie
	 * @return
	 */
	public Map add() {
		Map map = new HashMap();
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if ("2".equals(us.getUserLoginType()) || "5".equals(us.getUserLoginType())) { // 2普通一级集体
			// 5监管一级集体
			this.getBean().setPeEnterprise(this.getPeEnterpriseByUs(us));
		}
		String pcode = (String) ServletActionContext.getContext().getSession().get("pcode");
		if (("".equals(pcode) || null == pcode || "null".equalsIgnoreCase(pcode)) && null != this.getBean().getPeEnterprise()
				&& !"".equals(this.getBean().getPeEnterprise())) {
			pcode = this.getBean().getPeEnterprise().getCode();
		}
		this.setBean((PeSubInner) super.setSubIds(this.getBean()));
		PeEnterpriseManager instance = null;
		boolean flag = false;
		try {
			checkBeforeAdd();
		} catch (EntityException e) {
			map.put("success", "false");
			map.put("info", e.getMessage());
			return map;
		}
		try {
			instance = this.savepem(this.getBean(), pcode);
			map.put("success", "true");
			map.put("info", "添加成功");
			logger.info("添加成功! id= " + instance.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return super.checkAlternateKey(e, "添加");
		}
		return map;
	}

	/**
	 * 保存机构管理员
	 * 
	 * @author linjie
	 * @return
	 */
	public PeEnterpriseManager savepem(PeSubInner bean, String code) {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String subcode = this.codenum(code, 1);
		PeEnterprise peEnterprise = new PeEnterprise();
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
			dc.createCriteria("ssoUser", "ssoUser");
			dc.createCriteria("peEnterprise", "peEnterprise");
			dc.add(Restrictions.eq("ssoUser.id", us.getSsoUser().getId()));
			PeEnterpriseManager peEnterpriseManager = (PeEnterpriseManager) this.getGeneralService().getList(dc).get(0);
			String subName = peEnterpriseManager.getPeEnterprise().getName() + "-" + this.getBean().getName();
			peEnterprise.setCode(subcode);
			peEnterprise.setName(subName);
			peEnterprise.setPeEnterprise(bean.getPeEnterprise());
			peEnterprise.setFzrDepart(bean.getFzrDepart());
			peEnterprise.setLuruDate(bean.getLuruDate());
			peEnterprise.setLuruPeople(bean.getLuruPeople());
			peEnterprise.setEnumConstByFlagEnterpriseState(getDefaultFlagElective());
			peEnterprise.setEnumConstByFlagEnterpriseType(bean.getPeEnterprise().getEnumConstByFlagEnterpriseType());
			peEnterprise.setSubName(this.getBean().getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			peEnterprise = (PeEnterprise) this.getGeneralService().save(peEnterprise);
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SsoUser ssoUser = new SsoUser();
		PePriRole pePriRole = new PePriRole();// 二级集体管理员
		if ("V".equals(bean.getPeEnterprise().getEnumConstByFlagEnterpriseType().getCode())) {
			pePriRole.setId("145FC734AB5133B5E0530100007FA784");
		} else {
			pePriRole.setId("1");
		}
		EnumConst enumConstByFlagIsvalid = this.getEnumConstService().getByNamespaceCode("FlagIsvalid", "1");
		// ssoUser.setPassword("111111");
		ssoUser.setPasswordBk(MyUtil.md5(Const.defaultPwd));
		ssoUser.setPePriRole(pePriRole);
		ssoUser.setLoginId(subcode);
		ssoUser.setEnumConstByFlagIsvalid(enumConstByFlagIsvalid);
		try {
			ssoUser = (SsoUser) this.getGeneralService().save(ssoUser);
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PeEnterpriseManager manager = new PeEnterpriseManager();
		manager.setLoginId(subcode);
		manager.setSsoUser(ssoUser);
		manager.setPeEnterprise(peEnterprise);
		manager.setEnumConstByFlagIsvalid(enumConstByFlagIsvalid);
		manager.setMobilePhone(bean.getMophone());
		manager.setPhone(bean.getPhone());
		manager.setEmail(bean.getPememail());
		manager.setName(bean.getPename());
		manager.setPePriRole(pePriRole);
		try {
			manager = (PeEnterpriseManager) this.getGeneralService().save(manager);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return manager;
	}

	private EnumConst getDefaultFlagElective() {
		EnumConst ec = new EnumConst();
		ec.setId("402880f827f5b99b0127f5bdadc70007");
		return ec;
	}

	/**
	 * 获得二级机构号
	 * 
	 * @author linjie
	 * @return
	 */
	private String codenum(String code, int n) {
		String tempcode = null;
		DetachedCriteria codedc = DetachedCriteria.forClass(PeEnterprise.class);
		codedc.createCriteria("peEnterprise", "peEnterprise");
		codedc.add(Expression.neProperty("id", "peEnterprise.id"));
		codedc.setProjection(Projections.max("code"));
		codedc.add(Restrictions.ilike("code", code, MatchMode.START));
		List list;
		try {
			list = this.getGeneralService().getList(codedc);
			if (list.size() == 0 || list.get(0) == null) {
				if (n < 10) {
					tempcode = code + "00" + n;
				}
				if (n < 100 && 10 <= n) {
					tempcode = code + "0" + n;
				}
				if (n < 1000 && 100 <= n) {
					tempcode = code + n;
				}
			} else {
				String temp = "1" + list.get(0).toString().substring(4, 7);
				temp = Integer.parseInt(temp) + n + "";
				tempcode = code + temp.trim().substring(1, temp.length());
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tempcode;
	}

	/**
	 * 重写框架方法：添加数据前的校验
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void checkBeforeAdd() throws EntityException {
		DetachedCriteria criteria = null;
		List pList = null;
		// 判断企业名称不重复
		criteria = DetachedCriteria.forClass(PeEnterprise.class);
		criteria.add(Restrictions.eq("name", this.getBean().getName()));
		pList = this.getGeneralService().getList(criteria);
		if (pList != null && pList.size() > 0) {
			throw new EntityException("该机构名称已经存在，请重新输入机构名称");
		}
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sql = "SELECT 1 FROM PE_ENTERPRISE WHERE NAME = (SELECT NAME||'-" + this.getBean().getName()
				+ "' FROM PE_ENTERPRISE WHERE ID = (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER WHERE LOGIN_ID = '"
				+ us.getSsoUser().getLoginId() + "'))";
		List list = this.getGeneralService().getBySQL(sql);
		if (list != null && list.size() > 0) {
			throw new EntityException("该机构名称已经存在，请重新输入机构名称");
		}
		if (us == null) {
			throw new EntityException("无操作权限");
		}
		String luru_people = us.getLoginId();

		this.getBean().setLuruDate(this.getCurSqlDate());
		this.getBean().setLuruPeople(luru_people);
	}

	/**
	 * 重写框架方法：更新数据前的校验
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void checkBeforeUpdate() throws EntityException {
		List pList = null;
		// 判断企业名称不重复
		DetachedCriteria criteria = DetachedCriteria.forClass(PeEnterprise.class);
		criteria.add(Restrictions.eq("id", this.getBean().getId()));
		criteria.setProjection(Property.forName("code"));
		List codeList = this.getGeneralService().getList(criteria);

		criteria = DetachedCriteria.forClass(PeEnterprise.class);
		criteria.add(Restrictions.eq("name", this.getBean().getName()));
		criteria.add(Restrictions.not(Restrictions.in("code", codeList)));
		pList = this.getGeneralService().getList(criteria);
		if (pList != null && pList.size() > 0) {
			PeEnterprise e = (PeEnterprise) pList.get(0);

			throw new EntityException("该机构名称已经存在，请重新输入机构名称");
		}

		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (us == null) {
			throw new EntityException("无操作权限");
		}
		String luru_people = us.getLoginId();

		this.getBean().setLuruDate(this.getCurSqlDate());
		this.getBean().setLuruPeople(luru_people);
	}

	/**
	 * 重写框架方法：删除数据前的校验
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void checkBeforeDelete(List idList) throws EntityException {
		// 判断是否有其管理范围之外的学员
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (!"3".equals(us.getRoleId())) {
			PeEnterprise peEnterprise = null;
			DetachedCriteria dc = null;
			dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
			dc.createCriteria("ssoUser", "ssoUser");
			dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
			List<PeEnterpriseManager> listPeEnterpriseManager = null;
			listPeEnterpriseManager = this.getGeneralService().getList(dc);
			if (null != listPeEnterpriseManager && listPeEnterpriseManager.size() > 0) {
				peEnterprise = listPeEnterpriseManager.get(0).getPeEnterprise(); // 当前管理员所在企业
			}
			dc = DetachedCriteria.forClass(PeEnterprise.class);
			dc.createCriteria("peEnterprise", "peEnterprise");
			dc.add(Restrictions.ne("peEnterprise", peEnterprise));
			dc.add(Restrictions.in("id", idList));
			List list = null;
			list = this.getGeneralService().getList(dc);
			if (null != list && list.size() > 0) {
				throw new EntityException("您只能删除您管理范围之内的单位！");
			}
		}

		// 判断该企业下是否有学员信息
		DetachedCriteria criteria = DetachedCriteria.forClass(PeBzzStudent.class);
		criteria.add(Restrictions.in("peEnterprise.id", idList));
		List<PeBzzStudent> stulist = this.getGeneralService().getList(criteria);
		if (stulist != null && stulist.size() > 0) {
			throw new EntityException("该机构下已经存在学员，无法删除");
		}

		// 判断该企业下是否有报名信息
		/*
		 * criteria = DetachedCriteria.forClass(PeBzzRecruit.class);
		 * criteria.add(Restrictions.in("peEnterprise.id", idList)); List<PeBzzRecruit>
		 * recruitList = this.getGeneralService().getList(criteria);
		 * if(recruitList!=null && recruitList.size()>0) { throw new
		 * EntityException("该机构下已经存在报名信息，无法删除"); }
		 */
	}

	/**
	 * 重写框架方法：删除数据
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public Map delete() {
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List idList = new ArrayList();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}
				try {
					checkBeforeDelete(idList);
				} catch (EntityException e) {
					map.put("success", "false");
					map.put("info", e.getMessage());
					return map;
				}
				try {
				if(this.del(idList)>0){
						map.put("success", "true");
						map.put("info", "删除成功");
					}else{
						map.put("success", "true");
						map.put("info", "删除成功");
					}
					/*if (this.enterpriseBacthService.deletePe(idList)) {
						map.put("success", "true");
						map.put("info", "删除成功");
					} else {
						map.put("success", "false");
						map.put("info", "删除失败");
					}
					*/

				} catch (Exception e) {
					this.delLogic(idList);
					map.put("success", "true");
					map.put("info", "删除成功");
					//return this.checkForeignKey(e);
				}
			} else {
				map.put("success", "false");
				map.put("info", "请选择操作项");
			}
		}
		return map;
	}

	/**
	 * 删除失败的机构处理 改为逻辑删除
	 */
	public void delLogic(List list){
		String condition = "";
		if (list !=null && list.size()>0 ){
			for(int i =0 ; i< list.size() ; i++ ){
				condition +="'"+ list.get(i)+"',";
			}
			condition = condition.substring(0, condition.length()-1);
		}
		String sql =" update pe_enterprise_manager m  set m.FLAG_ISVALID ='3' where m.FK_ENTERPRISE_ID in ("+condition+") " ;
		try {
			this.generalService.getBySQL(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public int del(List list) throws EntityException{
		int sum =0;
		String condition = "";
		if (list !=null && list.size()>0 ){
			for(int i =0 ; i< list.size() ; i++ ){
				condition +="'"+ list.get(i)+"',";
			}
			condition = condition.substring(0, condition.length()-1);
		}
		String sql =" delete pe_enterprise where id in("+condition+") " ;
		String sql1 ="  delete  FROM  SSO_USER s WHERE s.login_id IN (SELECT p.code  FROM PE_ENTERPRISE P WHERE  p.id in ("+condition+") )";
		String sql2 =" delete pe_enterprise_manager m where m.FK_ENTERPRISE_ID in("+condition+") ";
		this.getGeneralService().executeBySQL(sql2);
		this.getGeneralService().executeBySQL(sql1);
		sum = this.getGeneralService().executeBySQL(sql);	
		return sum ;
	}

	/**
	 * action为二级企业添加管理员前的准备
	 * 
	 * @return
	 */
	public String setManager() {

		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(PeEnterprise.class);
			criteria.add(Restrictions.eq("id", this.getBean().getId()));
			PeEnterprise peEnterprise = (PeEnterprise) this.getGeneralService().getList(criteria).get(0);

			if (peEnterprise.getLxrName() == null) {
				this.setMsg("网络学习管理员信息不存在，无法完成设置");
				return "setManagerFail";
			}
			this.getBean().setPeEnterprise(peEnterprise);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "setManager";
	}

	public PeEnterpriseManager getManager() {
		return manager;
	}

	public void setManager(PeEnterpriseManager manager) {
		this.manager = manager;
	}

	/**
	 * action为二级企业添加管理员
	 * 
	 * @return
	 */
	public String addManager() {
		// 设置登陆信息
		EnumConst ec = new EnumConst();
		ec.setId("2");
		manager.getSsoUser().setEnumConstByFlagIsvalid(ec);
		manager.getSsoUser().setLoginId(manager.getLoginId());
		manager.getSsoUser().setPassword(generatePass());
		manager.getSsoUser().setLoginNum(0L);

		manager.setEnumConstByFlagIsvalid(ec);

		try {
			// 设置性别
			DetachedCriteria dc = DetachedCriteria.forClass(EnumConst.class);
			dc.add(Restrictions.eq("namespace", "Gender"));
			dc.add(Restrictions.like("name", manager.getPeEnterprise().getLxrXb()));
			List<EnumConst> gender = null;

			gender = this.getGeneralService().getList(dc);
			manager.setEnumConstByGender(gender.get(0));

			// 设置基本信息
			manager.setEmail(manager.getPeEnterprise().getLxrEmail());
			manager.setMobilePhone(manager.getPeEnterprise().getLxrMobile());
			manager.setName(manager.getPeEnterprise().getLxrName());
			manager.setPhone(manager.getPeEnterprise().getLxrPhone());
			manager.setPosition(manager.getPeEnterprise().getLxrPosition());

			manager.getSsoUser().setLoginNum(0L);

			// 验证账号是否存在
			dc = DetachedCriteria.forClass(SsoUser.class);
			dc.add(Restrictions.eq("loginId", manager.getLoginId()));
			List list = this.getGeneralService().getList(dc);
			if (list != null && list.size() != 0) { // 存在
				this.setMsg("登录账号已存在，请重新输入");
				return "setManagerFail";
			}

			// 获得企业信息
			dc = DetachedCriteria.forClass(PeEnterprise.class);
			dc.add(Restrictions.eq("id", manager.getPeEnterprise().getId()));
			list = this.getGeneralService().getList(dc);
			PeEnterprise ent = (PeEnterprise) list.get(0);
			manager.setPeEnterprise(ent);

			// 判断该管理员是否存在

			String sql = "select 1 from pe_enterprise_manager em inner join sso_user su on em.fk_sso_user_id=su.id \n"
					+ "where em.fk_enterprise_id='" + manager.getPeEnterprise().getId() + "' and em.name='" + manager.getName() + "' \n"
					+ "and em.mobile_phone='" + manager.getMobilePhone() + "' and su.fk_role_id='" + manager.getPePriRole().getId() + "'";
			list = this.getGeneralService().getBySQL(sql);

			if (list != null && list.size() != 0) {
				this.setMsg("当前企业下该网络学习管理员已有该类型管理员身份，不能重复录入！");
				return "setManagerFail";
			}
			// 保存
			manager = (PeEnterpriseManager) this.getGeneralService().save(manager);

			String roleId = manager.getPePriRole().getId();
			sql = null;
			// 一级企业管理员
			if (roleId.equals("402880a92137be1c012137db62100006") || roleId.equals("2")) {
				String entId = manager.getPeEnterprise().getId();

				// 用户在二级企业
				dc = DetachedCriteria.forClass(PeEnterprise.class);
				dc.createCriteria("peEnterprise");
				dc.add(Restrictions.eq("id", entId));
				List<PeEnterprise> tpeList = (List<PeEnterprise>) this.getGeneralService().getList(dc);
				if (tpeList != null && tpeList.size() != 0) {
					PeEnterprise tpe = tpeList.get(0);
					entId = tpe.getPeEnterprise().getId();
				}

				sql = "insert into pr_bzz_pri_manager_enterprise " + "values(to_char(s_training_id.nextval),'"
						+ manager.getSsoUser().getId() + "','" + entId + "')";
				this.getGeneralService().executeBySQL(sql);
				sql = "insert into pr_bzz_pri_manager_enterprise \n" + "select to_char(s_training_id.nextval),'"
						+ manager.getSsoUser().getId() + "',e.id from pe_enterprise e " + "where e.fk_parent_id='" + entId + "'";

				// 二级企业管理员
			} else if (roleId.equals("402880f322736b760122737a968a0010") || roleId.equals("402880f322736b760122737a60c40008")) {
				String entId = manager.getPeEnterprise().getId();

				sql = "insert into pr_bzz_pri_manager_enterprise " + "values(to_char(s_training_id.nextval),'"
						+ manager.getSsoUser().getId() + "','" + entId + "')";
			}

			int n = this.getGeneralService().executeBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}

		return "addManagerSuccess";
	}

	/**
	 * 生成密码（8位小写字母）
	 * 
	 * @return
	 */
	private String generatePass() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 8; i++) {
			char n = (char) ('a' + (int) (Math.random() * 50) % 25);
			sb.append(n);
		}
		return sb.toString();
	}

	private java.sql.Date getCurSqlDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		Date currentTime_2 = null;
		try {
			currentTime_2 = formatter.parse(dateString);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.sql.Date d2 = new java.sql.Date(currentTime_2.getTime());
		return d2;
	}

	/*
	 * public DetachedCriteria initDetachedCriteria() {
	 * 
	 * UserSession us = (UserSession) ServletActionContext.getRequest()
	 * .getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	 * 
	 * DetachedCriteria criteria = DetachedCriteria
	 * .forClass(PeEnterprise.class);
	 * criteria.createCriteria("enumConstByFlagElective",
	 * "enumConstByFlagElective", DetachedCriteria.LEFT_JOIN);
	 * criteria.createCriteria("peEnterprise", "peEnterprise",
	 * DetachedCriteria.LEFT_JOIN); return criteria; }
	 */

	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	class PeSubInner extends AbstractBean {

		private String id;
		private String loginId;
		private String name;
		private String pename;
		private String phone;
		private String num;
		private String combobox_elective;
		private String mophone;
		private String pememail;
		private String fzrDepart;
		private PeEnterprise peEnterprise;
		private Date luruDate;
		private String luruPeople;

		public PeEnterprise getPeEnterprise() {
			return peEnterprise;
		}

		public void setPeEnterprise(PeEnterprise peEnterprise) {
			this.peEnterprise = peEnterprise;
		}

		@Override
		public String getId() {
			// TODO Auto-generated method stub
			return null;
		}

		public String getLoginId() {
			return loginId;
		}

		public void setLoginId(String loginId) {
			this.loginId = loginId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPename() {
			return pename;
		}

		public void setPename(String pename) {
			this.pename = pename;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public String getCombobox_elective() {
			return combobox_elective;
		}

		public void setCombobox_elective(String combobox_elective) {
			this.combobox_elective = combobox_elective;
		}

		public String getMophone() {
			return mophone;
		}

		public void setMophone(String mophone) {
			this.mophone = mophone;
		}

		public String getPememail() {
			return pememail;
		}

		public void setPememail(String pememail) {
			this.pememail = pememail;
		}

		public String getFzrDepart() {
			return fzrDepart;
		}

		public void setFzrDepart(String fzrDepart) {
			this.fzrDepart = fzrDepart;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Date getLuruDate() {
			return luruDate;
		}

		public void setLuruDate(Date luruDate) {
			this.luruDate = luruDate;
		}

		public String getLuruPeople() {
			return luruPeople;
		}

		public void setLuruPeople(String luruPeople) {
			this.luruPeople = luruPeople;
		}
	}

	public String getOperateresult() {
		return operateresult;
	}

	public void setOperateresult(String operateresult) {
		this.operateresult = operateresult;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFzrDepart() {
		return fzrDepart;
	}

	public void setFzrDepart(String fzrDepart) {
		this.fzrDepart = fzrDepart;
	}

}
