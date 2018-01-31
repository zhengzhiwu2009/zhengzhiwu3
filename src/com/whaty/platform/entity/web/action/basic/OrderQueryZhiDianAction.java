package com.whaty.platform.entity.web.action.basic;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class OrderQueryZhiDianAction extends CollectiveOrderQueryAction {

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeBzzOrderInfo.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/orderQueryZhiDian";
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
	 * 重写框架方法：订单信息（带sql条件）
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public Page list() {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String tableName = "( select id,fk_order_id from ( select back.id,back.fk_order_id from pr_bzz_tch_stu_elective_back back union all "
				+ "select history.id,history.fk_order_id from elective_history history ) )";

		String sql = "";

		if ("viewDetail".equals(super.getFlag())) {
			String peSql = "select pem.fk_enterprise_id from pe_enterprise_manager pem where pem.login_id = '" + us.getLoginId() + "'";
			String peId = "";
			try {
				List list = this.getGeneralService().getBySQL(peSql);
				peId = list.get(0).toString();
			} catch (EntityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			sql = "select * from (select rownum as id,p.* from(\n" + " select --e.id          as id,\n" + "       stu.true_name as trueName,\n" + "       c.code        as code,\n"
					+ "		c.name 		  as name,\n" + "       c.time        as time\n" + "  from " + tableName + " e,\n" + "       pr_bzz_tch_opencourse   op,\n" + "       pe_bzz_tch_course       c,\n"
					+ "       pe_bzz_student          stu\n" + " where e.fk_order_id = '" + this.getId() + "'\n" + "   and op.id = e.fk_tch_opencourse_id\n" + "   and op.fk_course_id = c.id\n"
					+ "   and stu.id = e.fk_stu_id" + ") p) where 1=1 ";

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
					+ "       ec_method.name as combobox_peymentMethod,\n"
					+ "       ec_type.name as combobox_orderType,\n"
					+ "       ec_state.name as combobox_paymentState,\n"
					+ "       decode(count(prtse.id),0,'-',count(prtse.id)) as stuNum, \n"
					+ " 		case when ec_state.code = '1' or ec_state.code = '2' or pboi.num is not null then '已支付' when 1<>1 then 'a' else '未支付' end  as combobox_ZhiFuState,\n"
					+ "		count(refund.id) as combobox_refundState, \n"
					+ "		decode(ec_ref.name,'','--',ec_ref.name) as refundState, \n"
					+ "       case when pbii.id is not null then (case when pbii.flag_fp_open_state = '402880f327dc55c90127dc7131ad0001' then '已开'  when pbii.flag_fp_open_state ='402880f327dc55c90127dc71c4670002' then  '待开' else '已申请' end) else  '未申请' end  as COMBOBOX_FAPIAOSTATE,\n"
					+ "		pboi.merge_seq COMBOBOX_POSTTYPE, ec_method.code as paymentCode, \n" + "		to_date('2012-12-22','yyyy-mm-dd') as paysStartDate, \n" + "		to_date('2012-12-22','yyyy-mm-dd') as payEndDate,  \n"
					+ "		case when pbii.id is not null then 'isinvoiced' else 'isnotinvoiced' end  as isinvoice,\n" + "		pbii.flag_fp_open_state as pbiiCode \n" + "  from pe_bzz_order_info pboi\n"
					+ " left join "
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
					+ " left join enum_const ec on pbii.flag_post_type = ec.id "
					+ " where ec_type.code<>'0' and ec_type.code<>'6' and ec_valid.code <> '0' and ec_method.code in ('2','3') and pboi.create_user = '"
					+ us.getId()
					+ "'\n"
					+ sqlStart
					+ sqlEnd
					+ "\n"
					+ " group by pboi.id,\n"
					+ "		   pboi.seq,\n"
					+ "          pboi.name,\n"
					+ "          pboi.payer,\n"
					+ "		   pboi.payment_date,\n"
					+ "          pboi.amount,\n"
					+ "          pboi.merge_seq,\n"
					+ "          pboi.create_date,\n"
					+ "          ec_method.name,\n"
					+ "          ec_state.name,\n"
					+ "		   ec_state.code,\n"
					+ "          ec_type.name,\n"
					+ "          pboi.num,\n"
					+ "          pbii.flag_fp_open_state,\n" + "		   ec_cls.name, pboi.class_hour, ec_ref.name, ec_method.code, pbii.id,ec.name order by pboi.payment_date desc) where 1=1 ";

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
			page = getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;

	}

}
