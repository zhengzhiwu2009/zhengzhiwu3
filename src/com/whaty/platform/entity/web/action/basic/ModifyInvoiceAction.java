package com.whaty.platform.entity.web.action.basic;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class ModifyInvoiceAction extends MyBaseAction{

	private String id;
	private PeBzzInvoiceInfo peBzzInvoiceInfo;
	
	/**
	 * 初始化列表
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle("发票列表");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("订单号"), "peBzzOrderInfo.seq");
		this.getGridConfig().addColumn(this.getText("收件人"), "addressee", false);
		this.getGridConfig().addColumn(this.getText("收件地址"), "address", true);
		this.getGridConfig().addColumn(this.getText("邮政编码"), "zipCode", true);
		this.getGridConfig().addColumn(this.getText("联系电话"), "phone", true);
		this.getGridConfig().addColumn(this.getText("发票抬头"), "title", false);
		this.getGridConfig().addColumn(this.getText("发票状态"), "enumConstByFlagFpOpenState.name",true);
		this.getGridConfig().addColumn(this.getText("发票号码"), "num", true);
		/*this.getGridConfig().addRenderFunction(this.getText("修改信息"),
				"<a href=\"/entity/basic/modifyInvoice_modifyInvoice.action?id=${value}\") >修改</a>",
				"id");*/
		this.getGridConfig().addRenderScript(
				this.getText("发票详情"),
				"{if(record.data['combobox_orderType'] == '购买学时订单' ||record.data['combobox_orderType'] != '购买学时订单' ) {" + "	return '<a target=\"blank\" href=\""
						+ this.getServletPath() + "_invoiceViewDeatail.action?id='+record.data['id']+'&flag=viewDetail\">发票信息</a>'" + "} else {"
						+ "	return '<label title=购买学时订单没有选课详情>--</label>'" + "}}", "id");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeBzzInvoiceInfo.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/modifyInvoice";
	}
	
	/**
	 * 课程管理列表
	 * 
	 * @author Lee
	 * @return
	 */
	public Page list() {
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("SELECT * FROM (SELECT PBII.ID, PBOI.SEQ, PBII.ADDRESSEE, PBII.ADDRESS, PBII.ZIP_CODE, PBII.PHONE, PBII.TITLE, EC1.NAME FLAGFPOPENSTATE, PBII.NUM NUM, PBII.CREATE_DATE ");
			sqlBuffer.append("  FROM PE_BZZ_INVOICE_INFO PBII ");// EC.NAME
			sqlBuffer.append("  JOIN PE_BZZ_ORDER_INFO PBOI ");
			sqlBuffer.append("    ON PBII.FK_ORDER_ID = PBOI.ID ");
			sqlBuffer.append("  JOIN ENUM_CONST EC1 ");
			sqlBuffer.append("    ON PBII.FLAG_FP_OPEN_STATE = EC1.ID ");
			sqlBuffer.append("  JOIN ENUM_CONST EC2 ");
			sqlBuffer.append("    ON PBII.FLAG_POST_TYPE = EC2.ID ");
			sqlBuffer.append(" ) WHERE 1 = 1 ");
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
				/* 订单号 */
				if (name.equals("peBzzOrderInfo.seq")) {
					name = "SEQ";
				}
				/* 发票状态 */
				if (name.equals("enumConstByFlagFpOpenState.name")) {
					name = "FLAGFPOPENSTATE";
				}
				/* 邮寄方式 */
				if (name.equals("num")) {
					name = "NUM";
				}
				
				if (!name.equals("enumConstByFlagSuggest.name")) {
					sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");
				}
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				/* 订单号 */
				if (temp.equals("peBzzOrderInfo.seq")) {
					temp = "SEQ";
				}
				/* 发票状态 */
				if (temp.equals("enumConstByFlagFpOpenState.name")) {
					temp = "FLAGFPOPENSTATE";
				}
				/* 邮寄方式 */
				if (temp.equals("enumConstByFlagPostType.name")) {
					temp = "FLAGPOSTTYPE";
				}			
				if (temp.equals("id")) {
					temp = "CREATE_DATE";
				}
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					if ("zipCode".equalsIgnoreCase(temp) || "phone".equalsIgnoreCase(temp) || "peBzzOrderInfo.seq".equalsIgnoreCase(temp)) {
						sqlBuffer.append(" order by to_number(" + temp + ") desc ");
					} else {
						sqlBuffer.append(" order by " + temp + " desc ");
					}
				} else {
					if ("zipCode".equalsIgnoreCase(temp) || "phone".equalsIgnoreCase(temp) || "peBzzOrderInfo.seq".equalsIgnoreCase(temp)) {
						sqlBuffer.append(" order by to_number(" + temp + ") asc ");
					} else {
						sqlBuffer.append(" order by " + temp + " asc ");
					}
				}
			} else {
				sqlBuffer.append(" order by CREATE_DATE desc");
			}
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 框架方法：或者查询列表所需数据
	 * @author linjie
	 * @return
	 */
	@Override
	public DetachedCriteria initDetachedCriteria() {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession()
		.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
		dc.createCriteria("enumConstByFlagFpOpenState");
		dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
		dc.createCriteria("peBzzOrderInfo.ssoUser", "ssoUser");
		dc.add(Restrictions.eq("peBzzOrderInfo.ssoUser", us.getSsoUser()));
		return dc;
	}
	
	/**
	 * 修改发票信息
	 * @author linjie
	 * @return
	 */
	public String modifyInvoice() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
		dc.createCriteria("enumConstByFlagFpOpenState");
		dc.add(Restrictions.eq("id", this.id));
		List list = null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(list.size()<1) {
			this.setMsg("操作失败，请重新操作！");
			this.setTogo("/entity/basic/modifyInvoice.action");
			return "msg";
		} else {
			peBzzInvoiceInfo = (PeBzzInvoiceInfo)list.get(0);
		}
		EnumConst ec = peBzzInvoiceInfo.getEnumConstByFlagFpOpenState();
		if(ec != null)
		if("1".equals(ec.getCode())) {
			this.setMsg("此发票已通过协会审核，状态为已开，不能修改发票信息！");
			this.setTogo("/entity/basic/modifyInvoice.action");
			return "msg";
		}
		return "modifyInvoice";
	}
	
	/**
	 * 修改发票信息
	 * @author linjie
	 * @return
	 */
	public String modifyInvoiceInfo() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
		dc.add(Restrictions.eq("id", id));
		List list = null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(list.size()<1) {
			this.setMsg("发票信息修改失败！");
			this.setTogo("/entity/basic/modifyInvoice.action");
			return "msg";
		}
		String postType = ServletActionContext.getRequest().getParameter("postType");
		EnumConst enumConstByFlagPostType = this.getEnumConstService().getByNamespaceCode("FlagPostType", postType);
		PeBzzInvoiceInfo invoice = (PeBzzInvoiceInfo)list.get(0);
		invoice.setAddress(this.peBzzInvoiceInfo.getAddress());
		invoice.setAddressee(this.peBzzInvoiceInfo.getAddressee());
		invoice.setPhone(this.peBzzInvoiceInfo.getPhone());
		invoice.setTitle(this.peBzzInvoiceInfo.getTitle());
		invoice.setZipCode(this.peBzzInvoiceInfo.getZipCode());
		invoice.setCity(this.peBzzInvoiceInfo.getCity());
		invoice.setProvince(this.peBzzInvoiceInfo.getProvince());
		invoice.setEnumConstByFlagPostType(enumConstByFlagPostType);
		try {
			this.getGeneralService().save(invoice);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setMsg("发票信息修改成功！");
		this.setTogo("/entity/basic/modifyInvoice.action");
		return "msg";
		
	}
	public String invoiceViewDeatail(){
		HttpServletRequest request = ServletActionContext.getRequest();
		List <PeBzzInvoiceInfo> list = null ;
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
		//dc.createCriteria("peBzzInvoiceInfo", "peBzzInvoiceInfo");
		dc.add(Restrictions.eq("id", this.id));
		try {
			list =  this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(null != list && list.size()>0){
			this.peBzzInvoiceInfo = (PeBzzInvoiceInfo)list.get(0);
			if(null != list.get(0).getEnumConstByInvoiceType().getCode() && "3".equals(list.get(0).getEnumConstByInvoiceType().getCode())){
			}
		}else{
			this.setMsg("暂无发票信息");
			this.setTogo("javascript:window.close();");
			return "msg";
		}
		return "invoiceView";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeBzzInvoiceInfo getPeBzzInvoiceInfo() {
		return peBzzInvoiceInfo;
	}

	public void setPeBzzInvoiceInfo(PeBzzInvoiceInfo peBzzInvoiceInfo) {
		this.peBzzInvoiceInfo = peBzzInvoiceInfo;
	}

	
}
