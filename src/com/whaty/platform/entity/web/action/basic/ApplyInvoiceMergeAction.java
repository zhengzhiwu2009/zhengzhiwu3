package com.whaty.platform.entity.web.action.basic;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.sso.bean.SsoUser;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class ApplyInvoiceMergeAction extends CollectiveOrderQueryAction {
	private String seqs;
	private String orderIds; //申请合并发票订单号
	private String sumAmount; //申请合并发票总金额
	private PeBzzInvoiceInfo peBzzInvoiceInfo;
	public PeBzzInvoiceInfo getPeBzzInvoiceInfo() {
		return peBzzInvoiceInfo;
	}

	public void setPeBzzInvoiceInfo(PeBzzInvoiceInfo peBzzInvoiceInfo) {
		this.peBzzInvoiceInfo = peBzzInvoiceInfo;
	}

	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	public String getSeqs() {
		return seqs;
	}

	public void setSeqs(String seqs) {
		this.seqs = seqs;
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeBzzOrderInfo.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/applyInvoiceMerge";
	}

	@Override
	public String getServletPath() {
		// TODO Auto-generated method stub
		return this.servletPath;
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
	 * 初始化列表
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
			
			this.getGridConfig().setTitle(this.getText("发票信息"));
			this.getGridConfig().setCapability(false, false, false);
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("合并订单号"), "seq", true, false, true, "");
			this.getGridConfig().addColumn(this.getText("发票号码"), "fphm", true, false, true, "");
			this.getGridConfig().addColumn(this.getText("发票代码"), "fpdm", false, false, true, "");
			this.getGridConfig().addColumn(this.getText("创建时间"), "createDate", false, false, true, "");
			this.getGridConfig().addColumn(this.getText("开具时间"), "opeenDate", false, false, true, "");
			this.getGridConfig().addColumn(this.getText("开具状态"), "name", false, false, true, "");
			this
			.getGridConfig()
			.addMenuScript(
					this.getText("代申请专用发票"),
					"{window.location.href='/entity/basic/applyInvoiceMerge_toInvoiceApplyJsp.action' }"
					);
			this.getGridConfig().addRenderScript(
					this.getText("发票详情"),
					"{if(record.data['combobox_orderType'] == '购买学时订单' ||record.data['combobox_orderType'] != '购买学时订单' ) {" + "	return '<a target=\"blank\" href=\""
							+ this.getServletPath() + "_invoiceViewDeatail.action?id='+record.data['id']+'&flag=viewDetail\">发票信息</a>'" + "} else {"
							+ "	return '<label title=购买学时订单没有选课详情>--</label>'" + "}}", "id");
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
		StringBuffer sb = new StringBuffer();
		sb.append("  select * from (   ");
		sb.append("  SELECT pbom.id id ,PBOM.SEQ seq ,pbii.num fphm ,pbii.fpdm_num fpdm, PBOM.CREATE_DATE createDate ,pbii.open_date openDate, ec.name name  ");
		sb.append("    FROM PE_BZZ_ORDER_MERGE PBOM  ");
		sb.append("     INNER JOIN PE_BZZ_ORDER_INFO PBOI ");
		sb.append("     ON PBOM.SEQ = PBOI.MERGE_SEQ ");
		sb.append("    INNER JOIN PE_BZZ_INVOICE_INFO PBII ");
		sb.append("    ON pboi.id = pbii.fk_order_id  ");
		sb.append("    INNER JOIN ENUM_CONST ec  ");
		sb.append("   ON pbii.flag_fp_open_state = ec.id  where pbom.OPERATOR = '"+ us.getId() +"' ");
		sb.append("   GROUP BY pbom.id ,PBOM.SEQ,pbii.num,pbii.fpdm_num, PBOM.CREATE_DATE,pbii.open_date ,ec.name  ");
		sb.append("    order by pbom.CREATE_DATE desc  ");
		sb.append("    ) AA where 1 = 1");
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
			if (name.equals("seq")) {
				sb.append(" AND UPPER(AA.SEQ) LIKE '%" + value.toUpperCase() + "%' ");
			}
			/* 发票号码 */
			if (name.equals("fphm")) {
				sb.append(" AND UPPER(AA.FPHM) LIKE '%" + value.toUpperCase() + "%' ");
			}

		} while (true);
		String temp = this.getSort();
		if (temp != null && temp.indexOf(".") > 1) {
			if (temp.toLowerCase().startsWith("combobox_")) {
				temp = temp.substring(temp.indexOf(".") + 1);
			}
		}
		if (this.getSort() != null && temp != null) {
			/* 课程性质 */
			if (temp.equals("seq")) {
				temp = "seq";
			}
			if(temp.equals("fphm")){
				temp = "fphm";
			}
			
		}
		
		StringBuffer sqlBuffer = new StringBuffer(sb.toString());
		Page page = null;
		try {
			page = getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;

	}

	public String toInvoiceApplyJsp(){
		
		return "mergeApply";
	}
	/**
	 *同一个机构下订单合并申请发票
	 * */
	public String applyMergeInvoice(){
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String [] ids = this.seqs.split("\\|");
		getInvoice();// 获得最后一次发票信息
		List<PeBzzOrderInfo> peList = null;
		DetachedCriteria order = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		order.add(Restrictions.in("seq", ids));
		try {
			peList = this.getGeneralService().getList(order);
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List lists = null ;
		/**
		 * 判断订单是否申请发票
		 * 判断订单是否申请退费
		 * 判断订单跨年不能申请发票
		 * 判断订单支付7天内不能申请发票
		 * 判断订单未支付不能申请发票
		 * 判断订单金额是否超过1000元
		 * */
		String str =""; 
		if (peList != null && peList.size() > 0) {
			double amount = 0.00;
			for (PeBzzOrderInfo info : peList) {
				List<PeBzzInvoiceInfo> invoiceList = null ;
				List<PeBzzRefundInfo> refundList = null;
				List<PeBzzStudent> studentList = null ;
				DetachedCriteria invoice = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
				invoice.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
				invoice.add(Restrictions.eq("peBzzOrderInfo.id", info.getId()));
				try {
					 invoiceList = this.getGeneralService().getList(invoice);
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DetachedCriteria refund = DetachedCriteria.forClass(PeBzzRefundInfo.class);
				refund.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
				refund.add(Restrictions.eq("peBzzOrderInfo.id",info.getId()));
				try {
					refundList = this.getGeneralService().getList(refund);
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DetachedCriteria user = DetachedCriteria.forClass(PeBzzStudent.class);
				user.createCriteria("ssoUser", "ssoUser");
				user.createCriteria("peEnterprise", "peEnterprise");
				user.add(Restrictions.eq("ssoUser.id", info.getOperater()));
				try {
					studentList = this.getGeneralService().getList(user);
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(null != studentList && studentList.size()>0){
					String loginId =us.getLoginId().substring(0,4);
					if(null !=  studentList.get(0).getPeEnterprise() && studentList.get(0).getPeEnterprise().getCode().substring(0,4).indexOf(us.getLoginId().substring(0, 4)) == -1){
						this.setMsg(info.getSeq() + "订单不属于本机构下，不能申请专用发票");
						this.setTogo("back");
						return "msg";
					}
				}
				if(null != invoiceList && invoiceList.size()>0 ){
					this.setMsg(info.getSeq() + "订单已申请发票信息，不能重复申请");
					this.setTogo("back");
					return "msg";
				}
				if(null != refundList && refundList.size() >0){
					if(null != refundList.get(0).getEnumConstByFlagRefundState().getCode()&& refundList.get(0).getEnumConstByFlagRefundState().getCode().equals("2")){
						this.setMsg(info.getSeq()+ "订单已退费或正在等待审核，不能申请专用发票");
						this.setTogo("back");
						return "msg";
					}
				}
				if(info.getPaymentDate() != null){
					if(info.getPaymentDate().getYear() != new Date().getYear()){
						this.setMsg(info.getSeq()+"订单跨年不能申请发票，如有疑问请联系咨询中心");
						this.setTogo("back");
						return "msg";
					}
				}
				if (info.getPaymentDate() != null) {
					if (!validate(info.getPaymentDate())) {
						this.setMsg(info.getSeq()+"订单支付7天内不能申请专用发票");
						this.setTogo("back");
						return "msg";
					}
				}
				if( info.getEnumConstByFlagPaymentState() != null && !info.getEnumConstByFlagPaymentState().getCode().equals("1")){
					this.setMsg(info.getSeq()+ "未支付订单不能申请专用发票");
					this.setTogo("back");
					return "msg";
				}
				str += info.getId()+",";
				amount += Double.parseDouble(info.getAmount());
				this.orderIds = str.substring(0,str.length()-1);
				}
				if(amount < 1000){
					this.setMsg("合并订单总金额小于1000 ，不能申请合并专用发票");
					this.setTogo("back");
					return "msg";
				}
			}else{
				this.setMsg("没有符合条件的订单信息");
				this.setTogo(this.getServletPath() + ".action");
				return "msg";
			}
		return "applyMergeInvoice";
	}
	public String invoiceViewDeatail(){
		List orderList = null ;
		String orderId = "";
		String sql ="select id  from Pe_Bzz_Order_Info where merge_seq in (select seq from pe_bzz_order_merge where id ='"+ this.getId() +"' ) ";
		try {
			orderList = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		orderId = orderList.get(0).toString();
		List<PeBzzInvoiceInfo> list = null ;
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
		dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
		dc.add(Restrictions.eq("peBzzOrderInfo.id", orderId));
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(null != list && list.size() >0){
			this.peBzzInvoiceInfo = list.get(0);
			
		}
		return "invoiceView";
	}

	public String getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(String sumAmount) {
		this.sumAmount = sumAmount;
	}
}
