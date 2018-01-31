package com.whaty.platform.entity.web.action.basic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
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
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.basic.PaymentBatchService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.training.basic.StudyProgress;
import com.whaty.platform.util.Const;

public class PaymentLiveAction extends MyBaseAction {

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
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle("在线直播报名支付");
		/* 隐藏字段用于传值 */
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		/* 显示在列表与查询条件中 */
		this.getGridConfig().addColumn(this.getText("课程编号"), "code", true, false, true, Const.coursecode_for_extjs);
		this.getGridConfig().addColumn(this.getText("课程名称"), "name", true, false, true, "TextField", false, 200, "");
		this.getGridConfig().addColumn(this.getText("报名开始时间"), "signStartDatetime", false, false, true, "TextField", false, 200, "");
		this.getGridConfig().addColumn(this.getText("报名开始时间起"), "ssstartDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("报名开始时间止"), "ssendDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("报名结束时间"), "signEndDatetime", false, false, true, "TextField", false, 200, "");
		this.getGridConfig().addColumn(this.getText("报名结束时间起"), "sestartDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("报名结束时间止"), "seendDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("预计直播开始时间"), "estimateStartDatetime", false, false, true, "TextField", false, 200, "");
		this.getGridConfig().addColumn(this.getText("预计直播开始时间起"), "ysstartDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("预计直播开始时间止"), "ysendDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("预计直播结束时间"), "estimateEndDatetime", false, false, true, "TextField", false, 200, "");
		this.getGridConfig().addColumn(this.getText("预计直播结束时间起"), "yestartDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("预计直播结束时间止"), "yeendDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("预计学时总数"), "sumEstimateTime", false, false, true, "TextField", false, 10, "");
		// this.getGridConfig().addColumn(this.getText("预计学时数"),
		// "sumEstimateTime", false, false, true, "TextField", false, 10, "");
		this.getGridConfig().addColumn(this.getText("已缴总金额(元)"), "sumPrice", false, false, true, "TextField", false, 50, "");
		this.getGridConfig().addColumn(this.getText("直播链接"), "liveUrl", false, false, false, "TextField", false, 2000, "");
		this.getGridConfig().addColumn(this.getText("直播专项id"), "batchid", false, false, false, "TextArea", true, 500);
		this.getGridConfig().addColumn(this.getText("直播价格"), "price", false, false, false, "TextArea", true, 500);
		this.getGridConfig().addColumn(this.getText("报名人数上限"), "maxstus", false, false, false, "TextArea", true, 500);
		this.getGridConfig().addColumn(this.getText("可报名人数"), "leftstus", false, false, true, "TextField", false, 50, "");
		if (capabilitySet.contains(this.servletPath + "_studentsManage.action")) {// 学员管理权限
			this
					.getGridConfig()
					.addRenderScript(
							this.getText("学员管理"),
							"{return '<a href=\"/entity/basic/peBzzBatchEnter_toImpStudents.action?id='+record.data['batchid']+'&actionUrl=live\" >导入</a>&nbsp;&nbsp;<a href=\"/entity/basic/peBatchDetailEnter.action?batchid='+record.data['batchid']+'&method=add\">添加</a>';}",
							"id");
		}
		this
				.getGridConfig()
				.addRenderScript(
						this.getText("操作"),
						"{return '<a href=\"/entity/basic/peBatchDetailEnter.action?batchid='+record.data['batchid']+'&method=del&livep='+record.data['price']+'\">报名支付</a>&nbsp;&nbsp;&nbsp;<a href=\"/entity/teaching/peBzzCourseLiveManager_sacLiveView.action?id='+${value}+'\" target=\"_blank\">查看课程信息</a>';}",
						"id");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/paymentLive";
	}

	@Override
	public Page list() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Page page = null;
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("  SELECT AA.*, ");
			sb.append("	       DECODE(AA.PRICE, ");
			sb.append("	              0, ");
			sb.append("	              CASE ");
			sb.append("	                WHEN AA.MAXSTUS - NVL(SB.CNT, 0) <= 0 THEN ");
			sb.append("	                 0 ");
			sb.append("	                ELSE ");
			sb.append("	                 AA.MAXSTUS - NVL(SB.CNT, 0) ");
			sb.append("	              END, ");
			sb.append("	              CASE ");
			sb.append("	                WHEN AA.MAXSTUS - NVL(PBTSE2.CNT, 0) <= 0 THEN ");
			sb.append("	                 0 ");
			sb.append("	                ELSE ");
			sb.append("	                 AA.MAXSTUS - NVL(PBTSE2.CNT, 0) ");
			sb.append("	              END) LEFTSTUS ");
			sb.append("	  FROM (SELECT A.ID, ");
			sb.append("	               A.CODE, ");
			sb.append("	               A.NAME, ");
			sb.append("	               TO_CHAR(A.SIGNSTARTDATE, 'yyyy-MM-dd hh24:mi') SIGNSTARTDATETIME, ");
			sb.append("	               '' SSSTARTDATETIME, ");
			sb.append("	               '' SSENDDATETIME, ");
			sb.append("	               TO_CHAR(A.SIGNENDDATE, 'yyyy-MM-dd hh24:mi') SIGNENDDATETIME, ");
			sb.append("	               '' SESTARTDATETIME, ");
			sb.append("	               '' SEENDDATETIME, ");
			sb
					.append("	               TO_CHAR(A.ESTIMATESTARTDATE, 'yyyy-MM-dd hh24:mi') ESTIMATESTARTDATETIME, ");
			sb.append("	               '' YSSTARTDATETIME, ");
			sb.append("	               '' YSENDDATETIME, ");
			sb
					.append("	               TO_CHAR(A.ESTIMATEENDDATE, 'yyyy-MM-dd hh24:mi') ESTIMATEENDDATETIME, ");
			sb.append("	               '' YESTARTDATETIME, ");
			sb.append("	               '' YEENDDATETIME, ");
			sb.append("	               NVL(STUS.PBTSESTUS, 0) * A.ESTIMATETIME SUMESTIMATETIME, ");
			sb.append("	               NVL(STUS.PBTSESTUS, 0) * PRICE SUMPRICE, ");
			sb.append("	               A.LIVEURL, ");
			sb.append("	               PBTO.FK_BATCH_ID BATCHID, ");
			sb.append("	               A.PRICE, ");
			sb.append("	               A.MAXSTUS ");
			sb.append("	          FROM PE_BZZ_TCH_COURSE A ");
			sb.append("	         INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
			sb.append("	            ON A.ID = PBTO.FK_COURSE_ID ");
			sb.append("	         INNER JOIN ENUM_CONST EC1 ");
			sb.append("	            ON A.FLAG_ISVALID = EC1.ID ");
			sb.append("	           AND EC1.CODE = '1' ");
			sb.append("	         INNER JOIN ENUM_CONST EC6 ");
			sb.append("	            ON A.FLAG_LIVEPASSROLE = EC6.ID ");
			sb.append("	         INNER JOIN ENUM_CONST EC10 ");
			sb.append("	            ON A.FLAG_OFFLINE = EC10.ID ");
			sb.append("	           AND EC10.CODE = '0' ");
			sb.append("	         INNER JOIN ENUM_CONST EC11 ");
			sb.append("	            ON A.FLAG_SATISFACTION = EC11.ID ");
			sb.append("	          LEFT JOIN (SELECT COUNT(SB.STU_ID) STUS, ");
			sb.append("	                           SB.BATCH_ID, ");
			sb.append("	                           COUNT(PBTSE.ID) PBTSESTUS ");
			sb.append("	                      FROM STU_BATCH SB ");
			sb.append("	                     INNER JOIN PE_BZZ_STUDENT PBS ");
			sb.append("	                        ON SB.STU_ID = PBS.ID ");
			sb.append("	                       AND PBS.FK_ENTERPRISE_ID IN ");
			sb.append("	                           (SELECT ID ");
			sb.append("	                              FROM PE_ENTERPRISE ");
			sb.append("	                             WHERE ID IN ");
			sb.append("	                                   (SELECT FK_ENTERPRISE_ID ");
			sb.append("	                                      FROM PE_ENTERPRISE_MANAGER ");
			sb.append("	                                     WHERE FK_SSO_USER_ID = ");
			sb.append("	                                           '" + us.getSsoUser().getId() + "') ");
			sb.append("	                                OR FK_PARENT_ID IN ");
			sb.append("	                                   (SELECT FK_ENTERPRISE_ID ");
			sb.append("	                                      FROM PE_ENTERPRISE_MANAGER ");
			sb.append("	                                     WHERE FK_SSO_USER_ID = ");
			sb.append("	                                           '" + us.getSsoUser().getId() + "')) ");
			sb.append("	                     INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
			sb.append("	                        ON SB.BATCH_ID = PBTO.FK_BATCH_ID ");
			sb.append("	                      LEFT JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
			sb.append("	                        ON PBS.ID = PBTSE.FK_STU_ID ");
			sb.append("	                       AND PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID ");
			sb.append("	                     GROUP BY SB.BATCH_ID) STUS ");
			sb.append("	            ON PBTO.FK_BATCH_ID = STUS.BATCH_ID ");
			sb.append("	         WHERE A.FLAG_COURSEAREA = 'Coursearea0') AA ");
			sb.append("	  LEFT JOIN (SELECT BATCH_ID, COUNT(STU_ID) CNT ");
			sb.append("	               FROM STU_BATCH SB ");
			sb.append("	              INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
			sb.append("	                 ON SB.BATCH_ID = PBTO.FK_BATCH_ID ");
			sb.append("	              INNER JOIN PE_BZZ_TCH_COURSE PBTC ");
			sb.append("	                 ON PBTO.FK_COURSE_ID = PBTC.ID ");
			sb.append("	                AND PBTC.FLAG_COURSEAREA = 'Coursearea0' ");
			sb.append("	              GROUP BY BATCH_ID) SB ");
			sb.append("	    ON AA.BATCHID = SB.BATCH_ID ");
			sb.append("	  LEFT JOIN (SELECT FK_BATCH_ID BATCH_ID, COUNT(FK_STU_ID) CNT ");
			sb.append("	               FROM PR_BZZ_TCH_STU_ELECTIVE_BACK PBTSE ");
			sb.append("	              INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
			sb.append("	                 ON PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID ");
			sb.append("	                 AND PBTSE.FK_ORDER_ID IS NOT NULL ");
			sb.append("	              INNER JOIN PE_BZZ_TCH_COURSE PBTC ");
			sb.append("	                 ON PBTO.FK_COURSE_ID = PBTC.ID ");
			sb.append("	                AND PBTC.FLAG_COURSEAREA = 'Coursearea0' ");
			sb.append("	              GROUP BY PBTO.FK_BATCH_ID) PBTSE2 ");
			sb.append("	    ON AA.BATCHID = PBTSE2.BATCH_ID ");
			sb.append("	 WHERE 1 = 1 ");
			/* 时间查询条件 */
			ActionContext context = ActionContext.getContext();
			if (context.getParameters() != null) {
				Map params = this.getParamsMap();
				if (params != null) {
					/* 报名开始时间起止 */
					if (params.get("search__ssstartDatetime") != null) {
						String[] startDate = (String[]) params.get("search__ssstartDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sb.append(" AND TO_DATE(AA.SIGNSTARTDATETIME,'yyyy-MM-dd hh24:mi') >= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__ssstartDatetime");
						}
					}
					if (params.get("search__ssendDatetime") != null) {
						String[] startDate = (String[]) params.get("search__ssendDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sb.append(" AND TO_DATE(AA.SIGNSTARTDATETIME,'yyyy-MM-dd hh24:mi') <= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__ssendDatetime");
						}
					}
					/* 报名结束时间起止 */
					if (params.get("search__sestartDatetime") != null) {
						String[] startDate = (String[]) params.get("search__sestartDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sb.append(" AND TO_DATE(AA.SIGNENDDATETIME,'yyyy-MM-dd hh24:mi') >= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__sestartDatetime");
						}
					}
					if (params.get("search__seendDatetime") != null) {
						String[] startDate = (String[]) params.get("search__seendDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sb.append(" AND TO_DATE(AA.SIGNENDDATETIME,'yyyy-MM-dd hh24:mi') <= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__seendDatetime");
						}
					}
					/* 预计开始时间起止 */
					if (params.get("search__ysstartDatetime") != null) {
						String[] startDate = (String[]) params.get("search__ysstartDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sb.append(" AND TO_DATE(AA.ESTIMATESTARTDATETIME,'yyyy-MM-dd hh24:mi') >= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__ysstartDatetime");
						}
					}
					if (params.get("search__ysendDatetime") != null) {
						String[] startDate = (String[]) params.get("search__ysendDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sb.append(" AND TO_DATE(AA.ESTIMATESTARTDATETIME,'yyyy-MM-dd hh24:mi') <= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__ysendDatetime");
						}
					}
					/* 预计结束时间起止 */
					if (params.get("search__yestartDatetime") != null) {
						String[] startDate = (String[]) params.get("search__yestartDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sb.append(" AND TO_DATE(AA.ESTIMATEENDDATETIME,'yyyy-MM-dd hh24:mi') >= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__yestartDatetime");
						}
					}
					if (params.get("search__yeendDatetime") != null) {
						String[] startDate = (String[]) params.get("search__yeendDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sb.append(" AND TO_DATE(AA.ESTIMATEENDDATETIME,'yyyy-MM-dd hh24:mi') <= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__yeendDatetime");
						}
					}
					context.setParameters(params);
				}
			}
			/* 下拉列表查询条件 */
			Map params1 = this.getParamsMap();
			Iterator iterator = params1.entrySet().iterator();
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
				/* 是否发布 */
				if (name.equals("enumConstByFlagIsvalid.name")) {
					name = "VALIDNAME";
				}
				/* 采集数据 */
				if (name.equals("enumConstByFlagLiveData.name")) {
					name = "LIVEDATA";
				}
				if ("VALIDNAME".equalsIgnoreCase(name) || "LIVEDATA".equalsIgnoreCase(name) || "FLAGSUGGESTSET".equals(name))
					sb.append(" and " + name + " = '" + value + "'");
				else
					sb.append(" and UPPER(" + name + ") LIKE '%" + value.toUpperCase() + "%'");
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null && !temp.equalsIgnoreCase("id")) {
				/* 是否发布 */
				if (temp.equals("enumConstByFlagIsvalid.name")) {
					temp = "VALIDNAME";
				}
				/* 采集数据 */
				if (temp.equals("enumConstByFlagLiveData.name")) {
					temp = "LIVEDATA";
				}
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					sb.append(" order by " + temp + " desc ");
				} else {
					sb.append(" order by " + temp + " asc ");
				}
			} else {
				sb.append(" order by signStartDatetime desc");
			}
			page = this.getGeneralService().getByPageSQL(sb.toString(), Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	public String toConfirmStu() {
		this.destroyOrderSession();
		String paramIds = ServletActionContext.getRequest().getParameter("ids");
		String id = ServletActionContext.getRequest().getParameter("id");// 直播专项id
		String calcLiveStus = this.calcLiveStus(id);
		if (null != calcLiveStus && !"".equals(calcLiveStus)) {
			this.setMsg(calcLiveStus);
			this.setTogo("back");
			return "pbmsg";
		}
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = paramIds.split(",");
			String idsString = "";
			for (int i = 0; i < ids.length; i++) {
				idsString += "'" + ids[i] + "',";
			}
			idsString = idsString.substring(0, idsString.length() - 1);
			String lid = this.loginId;
			if (lid == null || "".equals(lid)) {// 如果this.loginId为空则支付自己的直播专项。
				lid = us.getLoginId();
			}
			PeEnterprise peEnterprise = null;
			String enterpriseID = "";
			try {
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
				// 校验直播专项是否支付过
				batchPayCheckByIds(us, enterpriseID, id, ids);
				peBzzBatch = batchDateCheck();// 判断是否在直播专项时间内
				// 判断本机构下当前直播专项的当前选中的未支付的学员,返回学员信息及选课数、金额数
				list = batchStuCheckByIds(id, idsString, lid);
				batchCourseCheck();// 判断此直播专项内是否有课程
				batchDatabaseCheck();// 支付之前检查数据库连接
				BigDecimal sumPrice = new BigDecimal("0.00");
				Iterator<String> it = studentCourselist.iterator();
				while (it.hasNext()) {
					sumPrice = sumPrice.add(new BigDecimal(it.next().toString()));
				}
				ServletActionContext.getRequest().getSession().setAttribute("batchPrice", sumPrice);
				String seq = this.getGeneralService().getOrderSeq();
				String courseNameSql = "SELECT PBTC.NAME FROM PE_BZZ_TCH_COURSE PBTC,PR_BZZ_TCH_OPENCOURSE PBTO WHERE PBTC.ID = PBTO.FK_COURSE_ID AND PBTO.FK_BATCH_ID = '"
						+ id + "'";
				List courseNameList = this.getGeneralService().getBySQL(courseNameSql);
				Object courseName = (null == courseNameList || courseNameList.size() == 0) ? "-" : courseNameList.get(0);
				ServletActionContext.getRequest().setAttribute("courseName", courseName);
				this.peBzzOrderInfo = new PeBzzOrderInfo();
				this.peBzzOrderInfo.setSeq(seq);
				this.peBzzOrderInfo.setPeEnterprise(peEnterprise);
				this.peBzzOrderInfo.setPeBzzBatch(peBzzBatch);
				this.peBzzOrderInfo.setAmount(sumPrice.toString());
				ActionContext.getContext().getSession().put("peBzzOrderInfo", peBzzOrderInfo);
			} catch (EntityException e) {
				this.setMsg(this.exErrorMsg(e.getMessage(), 0));// 转换错误信息
				this.setTogo("/entity/basic/paymentLive.action");
				return "pbmsg";
			} catch (Exception ex) {
				this.setMsg("访问异常，err_code:" + ex.getMessage());
				this.setTogo("/entity/basic/paymentLive.action");
				return "pbmsg";
			}
			return "confirmStudentInfo";
		} else {
			this.setMsg("请至少选择一条数据!");
			this.setTogo("back");
			return "pbmsg";
		}
	}

	public String toConfirmStuFree() {
		this.destroyOrderSession();
		String paramIds = ServletActionContext.getRequest().getParameter("ids");
		String id = ServletActionContext.getRequest().getParameter("id");// 直播专项id
		String calcLiveStus = this.calcLiveStus(id);
		if (null != calcLiveStus && !"".equals(calcLiveStus)) {
			this.setMsg(calcLiveStus);
			this.setTogo("back");
			return "pbmsg";
		}
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = paramIds.split(",");
			String idsString = "";
			for (int i = 0; i < ids.length; i++) {
				idsString += "'" + ids[i] + "',";
			}
			idsString = idsString.substring(0, idsString.length() - 1);
			String lid = this.loginId;
			if (lid == null || "".equals(lid)) {// 如果this.loginId为空则支付自己的直播专项。
				lid = us.getLoginId();
			}
			try {
				peBzzBatch = batchDateCheck();// 判断是否在直播专项时间内
				// 报名参加
				this.paymentFreeLive(id, idsString);
			} catch (EntityException e) {
				this.setMsg(this.exErrorMsg(e.getMessage(), 0));// 转换错误信息
				this.setTogo("/entity/basic/paymentLive.action");
				return "pbmsg";
			} catch (Exception ex) {
				this.setMsg("访问异常，err_code:" + ex.getMessage());
				this.setTogo("/entity/basic/paymentLive.action");
				return "pbmsg";
			}
			return "msg";
		} else {
			this.setMsg("请至少选择一条数据!");
			this.setTogo("back");
			return "pbmsg";
		}
	}

	public String paymentFreeLive(String batchId, String idsString) {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		// 直播专项实体
		DetachedCriteria dcBatch = DetachedCriteria.forClass(PeBzzBatch.class);
		dcBatch.add(Restrictions.eq("id", batchId));
		PeBzzBatch peBzzBatch;
		try {
			peBzzBatch = (PeBzzBatch) this.getGeneralService().getList(dcBatch).get(0);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		StringBuffer msg = new StringBuffer();
		String stuPbtseString = "";
		StringBuffer sbPbtseBuffer = new StringBuffer();
		int res = 0;
		// 检查学员是否已经报名成功
		String pbtseSqlString = "SELECT PBS.REG_NO,PBS.ID FROM PR_BZZ_TCH_STU_ELECTIVE PBTSE, PE_BZZ_STUDENT PBS, PR_BZZ_TCH_OPENCOURSE PBTO WHERE PBTSE.FK_STU_ID = PBS.ID AND PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID AND PBTO.FK_BATCH_ID = '"
				+ batchId + "' AND PBS.ID IN (" + idsString + ")";
		List list;
		try {
			list = this.getGeneralService().getBySQL(pbtseSqlString);
			for (int i = 0; i < list.size(); i++) {
				Object[] hasRegStu = (Object[]) list.get(i);
				sbPbtseBuffer.append(hasRegStu[0] + ",");
			}
			stuPbtseString = idsString.replace("'", "");
			// 去掉已报名pbtse的学员
			for (int i = 0; i < list.size(); i++) {
				Object[] hasRegStu = (Object[]) list.get(i);
				stuPbtseString = stuPbtseString.replace(hasRegStu[1] + ",", "").replace("," + hasRegStu[1], "");
			}
			// pbtse中无所选学员
			// 1.通过拿到开课的实体对象的PrBzzTchOpencourse
			DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
			dc.createCriteria("peBzzBatch", "peBzzBatch");
			dc.add(Restrictions.eq("peBzzBatch.id", batchId));
			List<PrBzzTchOpencourse> list1;
			try {
				list1 = this.getGeneralService().getList(dc);
				PrBzzTchOpencourse bzzTchOpencourse = null;
				if (list1.size() > 0) {
					bzzTchOpencourse = list1.get(0);
				}
				// 2.拿到报名参加学员S实体对象
				DetachedCriteria stuDc = DetachedCriteria.forClass(PeBzzStudent.class);
				stuDc.add(Restrictions.in("id", stuPbtseString.split(",")));
				List<PeBzzStudent> stuList = this.getGeneralService().getList(stuDc);
				for (int i = 0; i < stuList.size(); i++) {
					PeBzzStudent pbStudent = stuList.get(i);
					DetachedCriteria eleDc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
					eleDc.add(Restrictions.eq("prBzzTchOpencourse", bzzTchOpencourse));
					eleDc.add(Restrictions.eq("peBzzStudent", pbStudent));
					List eleList = this.getGeneralService().getList(eleDc);
					if (eleList.size() < 1) {// 当选课表无记录时插入选课表和training表
						// 3.插入training表
						List saveList = new ArrayList();
						TrainingCourseStudent trainingCourseStudent = new TrainingCourseStudent();
						trainingCourseStudent.setPrBzzTchOpencourse(bzzTchOpencourse);
						trainingCourseStudent.setSsoUser(pbStudent.getSsoUser());
						trainingCourseStudent.setPercent(0.00);
						trainingCourseStudent.setLearnStatus(StudyProgress.UNCOMPLETE);
						saveList.add(trainingCourseStudent);
						// 4.插入选课表
						PrBzzTchStuElective ele = new PrBzzTchStuElective();
						ele.setPrBzzTchOpencourse(bzzTchOpencourse);
						ele.setElectiveDate(new Date());
						ele.setPeBzzStudent(pbStudent);
						ele.setSsoUser(us.getSsoUser());
						ele.setTrainingCourseStudent(trainingCourseStudent);
						saveList.add(ele);
						this.getGeneralService().saveList(saveList);
						res += 1;
					}
				}
			} catch (EntityException e2) {
				e2.printStackTrace();
				this.setMsg("直播报名失败，请联系管理员");
				this.setTogo("back");
				return "msg";
			}
			msg.append("选中学员中，成功报名 " + res + " 名学员。<br />");
			if (sbPbtseBuffer.toString().length() > 0) {
				msg.append("已报名学员有：<br />");
				String[] sbp = sbPbtseBuffer.toString().split(",");
				for (int i = 0; i < sbp.length; i++) {
					if (i > 0 && i % 8 == 0)
						msg.append("<br />");
					msg.append(sbp[i] + ",");
				}
			}
			this.setMsg(msg + "请通知学员在规定时间内参加直播课程培训。");
			this.setTogo("back");
			return "msg";
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("直播报名失败，请联系管理员");
			this.setTogo("back");
			return "msg";
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
			// this.setMsg("该直播专项内没有未支付学员，不能支付");
			this.setMsg("err_batch_paid");
		}
		return list;
	}

	/**
	 * 判断此直播专项是否已经支付
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
	 * 确认直播专项支付单独选择学员的订单
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
		String id = ServletActionContext.getRequest().getParameter("bid");
		String calcLiveStus = this.calcLiveStus(id);
		ServletActionContext.getRequest().setAttribute("bid", id);
		if (null != calcLiveStus && !"".equals(calcLiveStus)) {
			this.setMsg(calcLiveStus);
			this.setTogo("close");
			return "lee";
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
	 * 去人直播专项支付订单
	 * 
	 * @return
	 */
	public String toConfirmOrder() {
		this.destroyOrderSession();
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		try {
			getInvoice();// 获取最后一次发票信息

			String lid = this.loginId;
			if (lid == null || "".equals(lid)) {// 如果this.loginId为空则支付自己的直播专项。
				lid = us.getLoginId();
			}
			PeEnterprise peEnterprise = null;
			String enterpriseID = "";
			PeEnterpriseManager peEnterpriseManager = this.getGeneralService().getEnterpriseManagerByLoginId(lid);
			peEnterprise = peEnterpriseManager.getPeEnterprise();
			enterpriseID = peEnterprise.getId();

			// 判断是否支付
			// batchPayCheck(us, enterpriseID,id);

			// 判断直播专项时间
			peBzzBatch = batchDateCheck();

			// 判断此机构下是否存在学员
			list = batchStuCheck(lid);
			ServletActionContext.getRequest().getSession().setAttribute("studentList", list);

			// 判断此直播专项内是否有课程
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
				this.setMsg("0元直播专项已经支付成功，请至“报名付费历史”中查看订单：" + seq + " !");
				this.setTogo("/entity/basic/paymentLive.action");
				return "pbmsg";
			}
			ActionContext.getContext().getSession().put("peBzzOrderInfo", peBzzOrderInfo);
		} catch (EntityException e) {
			this.setMsg(this.exErrorMsg(e.getMessage(), 0));// 转换错误信息
			this.setTogo("/entity/basic/paymentLive.action");
			return "pbmsg";
		} catch (Exception ex) {
			this.setMsg("访问异常，err_code:" + ex.getMessage());
			this.setTogo("/entity/basic/paymentLive.action");
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
	 * 免费直播专项处理
	 * 
	 * @param batchPrice
	 * @param seq
	 * @return
	 * @throws EntityException
	 */
	private boolean batchFreeCheck(BigDecimal batchPrice, String seq) throws EntityException {
		if (batchPrice.compareTo(new BigDecimal(0)) == 0) {// 免费直播专项
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
	 * 判断此直播专项内是否有课程
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
			// 该直播专项内没有课程，无法支付
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
			throw new EntityException("所选学员" + stuId + "已经支付该直播专项，不能删除课程！");
		}
		if (list.get(0).toString().equals("支付中")) {
			throw new EntityException("所选学员" + stuId + "正在支付该直播专项，不能删除课程！");
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
		dc.add(Property.forName("id").in(sbDc)); // 直播专项中存在的学员
		List list = null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (list.size() == 0) {
			// throw new EntityException("该直播专项内没有未支付学员，不能支付");
			// 此直播专项已支付
			throw new EntityException("err_batch_paid");
		}
		return list;
	}

	/**
	 * 判断是否在直播专项时间内
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
	 * 判断此直播专项是否已经支付
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
							// 此直播专项已支付
							throw new EntityException("err_batch_paid");
						} else {
							// 此直播专项已存在订单，请到“报名付费历史查询”中查看订单并继续支付。
							throw new EntityException("err_batch_in_pay");
						}
					} else {
						// 此直播专项已存在订单，但订单由其他管理员提交，您不能操作！
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
	 * 直播专项时间判断
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
			peBzzInvoiceInfo.setPeBzzOrderInfo(peBzzOrderInfo);
		} else {
			peBzzInvoiceInfo = null;
		}

		// 初始化默认状态
		EnumConst enumConstByFlagPaymentMethod = this.getEnumConstService().getByNamespaceCode("FlagPaymentMethod", this.payMethod);// 支付方式
		EnumConst enumConstByFlagPaymentType = this.getEnumConstService().getByNamespaceCode("FlagPaymentType", "1");// 集体支付
		EnumConst enumConstByFlagOrderType = this.getEnumConstService().getByNamespaceCode("FlagOrderType", "7");// 订单类型，视频直播订单
		EnumConst enumConstByFlagOrderState = this.getEnumConstService().getByNamespaceCode("FlagOrderState", "1");// 订单状态，正常
		EnumConst enumConstByFlagPaymentState = this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "0");// 未支付;
		EnumConst enumConstByFlagBatchPayState = this.getEnumConstService().getByNamespaceCode("FlagBatchPayState", "0"); // 直播专项，未支付
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
		// (String)ActionContext.getContext().getSession().get("lid");//登陆id，集体给子集体直播专项支付功能添加。
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

		// 检查是否达到报名上限
		try {
			Object bid = ServletActionContext.getRequest().getParameter("bid");
			String maxSql = "SELECT 1 FROM PE_BZZ_TCH_COURSE PBTC INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON PBTC.ID = PBTO.FK_COURSE_ID AND PBTO.FK_BATCH_ID = '"
					+ bid
					+ "' INNER JOIN PR_BZZ_TCH_STU_ELECTIVE_BACK PBTSE ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID AND PBTSE.FK_ORDER_ID IS NOT NULL GROUP BY PBTC.MAXSTUS, PBTC.ID HAVING PBTC.MAXSTUS - NVL(COUNT(PBTSE.ID), 0) < 1";
			List maxList = this.getGeneralService().getBySQL(maxSql);
			if (null != maxList && maxList.size() > 0) {
				this.setMsg("报名人数已达上限，谢谢关注！");
				this.setTogo("javascript:window.close();");
				return "paymentMsg";
			}
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			this.setMsg("直播继续支付失败,请联系协会管理员");
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
		map.put("err_batch_paid", new String[] { "选中学员已支付", "已支付" });
		map.put("err_batch_out_date", new String[] { "当前时间不在支付时间段内", "已过期" });
		map.put("err_batch_no_course", new String[] { "直播支付异常，请联系管理员", "无课程" });
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

	private String calcLiveStus(String id) {
		try {
			// 直播收费还是免费
			String liveSqlString = "SELECT 1 FROM PE_BZZ_TCH_COURSE WHERE ID IN (SELECT FK_COURSE_ID FROM PR_BZZ_TCH_OPENCOURSE WHERE FK_BATCH_ID = '"
					+ id + "') AND PRICE > 0";
			List liveList = this.getGeneralService().getBySQL(liveSqlString);
			String sql1 = "";
			if (null != liveList && liveList.size() > 0) {// 收费直播
				// 已报人数
				sql1 = "SELECT NVL(COUNT(PBTSE.ID),0) FROM PR_BZZ_TCH_OPENCOURSE PBTO INNER JOIN PR_BZZ_TCH_STU_ELECTIVE_BACK PBTSE ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID AND PBTO.FK_BATCH_ID = '"
						+ id + "' AND PBTSE.FK_ORDER_ID IS NOT NULL GROUP BY PBTO.FK_BATCH_ID";
			} else {// 免费直播
				sql1 = "SELECT NVL(COUNT(ID),0) FROM STU_BATCH WHERE BATCH_ID = '" + id + "'";
			}
			Object nowStudents = 0;
			List list1 = this.getGeneralService().getBySQL(sql1);
			if (null != list1 && list1.size() > 0)
				nowStudents = list1.get(0);
			// 允许人数
			sql1 = "SELECT NVL(PBTC.MAXSTUS,0) FROM PE_BZZ_TCH_COURSE PBTC,PR_BZZ_TCH_OPENCOURSE PBTO,PE_BZZ_BATCH PBB WHERE PBB.ID = PBTO.FK_BATCH_ID AND PBB.ID = '"
					+ id + "' AND PBTO.FK_COURSE_ID = PBTC.ID";
			Object maxStudents = this.getGeneralService().getBySQL(sql1).get(0);
			BigDecimal b1 = new BigDecimal(nowStudents.toString());
			BigDecimal b2 = new BigDecimal(maxStudents.toString());
			if (b1.subtract(b2).doubleValue() + this.getIds().split(",").length > 0) {
				return "报名人数上限为：" + maxStudents + "，还可以报名" + (b2.subtract(b1).compareTo(BigDecimal.ZERO) == -1 ? 0 : b2.subtract(b1))
						+ "名学员，请重新选择！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "报名失败，请刷新重试！";
		}
		return null;
	}
}
