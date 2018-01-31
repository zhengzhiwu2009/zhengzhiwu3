package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.StudentBatch;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.basic.PaymentBatchService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PaymentBatchAddCourseAction extends MyBaseAction {

	private String method = "";
	private String id = "";
	private String stuId = "";
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
		// TODO Auto-generated method stub
		this.getGridConfig().setCapability(false, false, false);
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();

		this.getGridConfig().setShowCheckBox(true);
		this.getGridConfig().setTitle("添加课程");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程编号"), "code");
		this.getGridConfig().addColumn(this.getText("课程名称"), "name");
		ColumnConfig columnCourseType = new ColumnConfig(this.getText("课程性质"),
				"enumConstByFlagCourseType.name", true, true, true,
				"TextField", false, 100, "");
		String sql1 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseType'";
		columnCourseType.setComboSQL(sql1);
		this.getGridConfig().addColumn(columnCourseType);

		this.getGridConfig().addColumn(this.getText("课程单价(元)"), "price", false,
				false, true, "TextField", false, 100, "");
		this.getGridConfig().addColumn(this.getText("学时数"), "time");
		this.getGridConfig().addColumn(this.getText("主讲人"), "teacher");

		ColumnConfig columnQualificationsType = new ColumnConfig(this
				.getText("建议学习人群"), "enumConstByFlagQualificationsType.name",
				true, false, true, "TextField", false, 100, "");
		String sql3 = "select a.id ,a.name from enum_const a where a.namespace='FlagQualificationsType' and name not in ('面向所有人投票','面向学员投票','面向集体管理员投票')";
		columnQualificationsType.setComboSQL(sql3);
		this.getGridConfig().addColumn(columnQualificationsType);

		ColumnConfig columnConfigCategory = new ColumnConfig(this
				.getText("按业务分类"), "enumConstByFlagCourseCategory.name", true,
				false, false, "TextField", false, 100, "");
		String sql2 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseCategory'";
		columnConfigCategory.setComboSQL(sql2);
		this.getGridConfig().addColumn(columnConfigCategory);

		ColumnConfig columnCourseItemType = new ColumnConfig(this
				.getText("按大纲分类"), "enumConstByFlagCourseItemType.name", true,
				false, false, "TextField", false, 100, "");
		String sql7 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseItemType'";
		columnCourseItemType.setComboSQL(sql7);
		this.getGridConfig().addColumn(columnCourseItemType);

		ColumnConfig columnContentProperty = new ColumnConfig(this
				.getText("按内容属性分类"), "enumConstByFlagContentProperty.name",
				true, false, false, "TextField", false, 100, "");
		String sql9 = "select a.id ,a.name from enum_const a where a.namespace='FlagContentProperty'";
		columnContentProperty.setComboSQL(sql9);
		this.getGridConfig().addColumn(columnContentProperty);
		// this.getGridConfig().addMenuFunction(this.getText("添加课程"),
		// "addCourse.true");
		this.getGridConfig().addMenuFunction(this.getText("添加课程"),
				"addCourse_" + this.getStuId() + "_" + this.getId());
		this.getGridConfig().addMenuScript(this.getText("返回"),
				"{history.back();}");
	}

	private StringBuffer getScriptAll() {
		StringBuffer script = new StringBuffer();
		script.append("	Ext.Ajax.request({");
		script.append(" url : '/entity/basic/paymentBatch_isAllPay.action',");
		script.append(" params : {sid:record.data['id'],topay:'all'},");
		script.append("	method : 'post',");
		script.append("	success : function(response) {");
		script.append("		var result = response.responseText; ");
		script
				.append("		document.getElementById('a_'+record.data['id']).innerHTML=result;");
		script.append("		");
		script.append("		} ");
		script.append("		}); ");
		return script;
	}

	private StringBuffer getScriptOne() {
		StringBuffer script = new StringBuffer();
		script.append("	Ext.Ajax.request({");
		script.append(" url : '/entity/basic/paymentBatch_isAllPay.action',");
		script
				.append(" params : {id:record.data['id'],loginId:record.data['loginId'],sid:record.data['sid'],topay:'one'},");
		script.append("	method : 'post',");
		script.append("	success : function(response) {");
		script.append("		var result = response.responseText; ");
		script
				.append("		document.getElementById('a_'+record.data['id']).innerHTML=result;");
		script.append("		");
		script.append("		} ");
		script.append("		}); ");
		return script;
	}

	/**
	 * 重写框架方法：更新字段前的校验
	 * 
	 * @author lipp
	 * @return
	 */
	public String checkBeforeAdd(String[] stuId, String[] courseId,
			String batchId) throws EntityException {
		StringBuffer msg = new StringBuffer();
		String sql = "";
		String sql1 = "";
		String sqlUser ="";
		
		
		for (int i = 0; i < stuId.length; i++) {
			sqlUser =" SELECT u.login_id FROM PE_BZZ_STUDENT S INNER JOIN SSO_USER U ON s.FK_SSO_USER_ID =u.ID AND s.id ='"+stuId[i]+"'" ;
			List userList =new ArrayList();
			userList =this.getGeneralService().getBySQL(sqlUser);
			for (int j = 0; j < courseId.length; j++) {		
				sql = " select ec_BATCHPAYSTATE.NAME as Combobox_QualificationsType        "+
				"   from STU_BATCH sb                                                "+
				"  inner join PE_BZZ_STUDENT pbs                                     "+
				"     on sb.STU_ID = pbs.ID                                          "+
				"  inner join PE_BZZ_BATCH pbb                                       "+
				"     on sb.BATCH_ID = pbb.ID                                        "+
				"  inner join ENUM_CONST ec_BATCHPAYSTATE                            "+
				"     on sb.FLAG_BATCHPAYSTATE = ec_BATCHPAYSTATE.ID                 "+
				"  where sb.stu_id = '"+stuId[i]+"'                                        "+
				"    and sb.batch_id = '"+batchId+"'            ";
				list = this.getGeneralService().getBySQL(sql);
				if(list.get(0).toString().equals("已支付")){
					msg.append(userList.get(0)+"学员已经支付该专项，不能添加课程;<br />");
				}
				if(list.get(0).toString().equals("支付中")){
					msg.append(userList.get(0)+"学员正在支付该专项，不能添加课程;<br />");
				}
				
				sql = " select code                                                            "
						+ "           from pr_bzz_tch_stu_elective_back pbtseb                     "
						+ "           join pr_bzz_tch_opencourse pbto                              "
						+ "             on pbtseb.fk_tch_opencourse_id = pbto.id                   "
						+ "           join pe_bzz_tch_course pbtc                                  "
						+ "             on pbto.fk_course_id = pbtc.id                             "
						+ "          where FK_STU_ID = '"
						+ stuId[i]
						+ "'                                    "
						+ "            and pbtc.id in ('"
						+ courseId[j]
						+ "')         "
						+ "            and FK_BATCH_ID = '"
						+ batchId + "'";
				List list = this.getGeneralService().getBySQL(sql);
				if (list.size() != 0) {
					msg.append(userList.get(0)+"学员在本专项中已经添加了课程" + list.get(0)
							+ ";<br />");
				}
				sql1 = " select code                                                              "
						+ "           from pr_bzz_tch_stu_elective_back pbtseb                     "
						+ "           join pr_bzz_tch_opencourse pbto                              "
						+ "             on pbtseb.fk_tch_opencourse_id = pbto.id                   "
						+ "           join pe_bzz_tch_course pbtc                                  "
						+ "             on pbto.fk_course_id = pbtc.id                             "
						+ "          where FK_STU_ID = '"
						+ stuId[i]
						+ "'                                    "
						+ "            and pbtc.id in ('"
						+ courseId[j]
						+ "')         "
						+ "            and FK_BATCH_ID <> '"
						+ batchId + "'";
				List list1 = this.getGeneralService().getBySQL(sql1);
				if (list1.size() != 0) {
					msg.append(userList.get(0)+"学员在非本专项中已经添加了课程" + list1.get(0)
							+ ";<br />");
				}
			}
		}
		if ("" != msg.toString()) {
			return msg.toString();
		} else {
			return "";
		}
	}

	/**
	 * 重写框架方法：更新字段
	 * 
	 * @author lipp
	 * @return
	 */
	@SuppressWarnings( { "unchecked", "unchecked", "unchecked" })
	public Map updateColumn() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Map map = new HashMap();
		String msg = "";
		String sql1 = "";
		String action = this.getColumn();

		String ap[] = action.split("_");
		String[] stuids = ap[1].split(",");
		String batchId = ap[2];

		if (action.indexOf("addCourse") >= 0) {
			if (this.getIds() != null && this.getIds().length() > 0) {
				String[] ids = getIds().split(",");

				String msgAddCourse = "";
				// 校验是否存在重复选课
				try {
					msgAddCourse = checkBeforeAdd(stuids, ids, batchId);
				} catch (EntityException e1) {
					e1.printStackTrace();
				}
				if (!"".equals(msgAddCourse)) {
					map.put("success", "false");
					map.put("info", msgAddCourse);
					return map;
				} else {
					for (int j = 0; j < stuids.length; j++) {
						for (int i = 0; i < ids.length; i++) {
							sql1 = " insert into pr_bzz_tch_stu_elective_back                                         "
									+ "   (id,                                                                           "
									+ "    fk_operator_id,                                                               "
									+ "    fk_tch_opencourse_id,                                                         "
									+ "    fk_stu_id,                                                                    "
									+ "    fk_order_id,                                                                  "
									+ "    fk_ele_course_period_id)                                                      "
									+ "   select SEQ_tch_stu_elective_back.nextval,                                      "
									+ "          '',                                                                     "
									+ "          (select id                                                              "
									+ "             from pr_bzz_tch_opencourse                                           "
									+ "            where FK_COURSE_ID =  '"
									+ ids[i]
									+ "'           and FK_BATCH_ID = '"+batchId+"'),              "
									+ "          '"
									+ stuids[j]
									+ "',                                                                     "
									+ "          '',                                                                     "
									+ "          ''                                                                      "
									+ "     from dual                                                                    ";
							try {
								this.getGeneralService().executeBySQL(sql1);
							} catch (EntityException e) {
								e.printStackTrace();
								msg = "添加课程失败";
								map.put("success", "false");
								map.put("info", msg);
								return map;
							}
							catch (Exception e) {
								e.printStackTrace();
							}
							
						}
					}
					
					msg = "添加课程成功；";
					map.put("success", "true");
					map.put("info", msg + "共有" + ids.length + "门课程操作成功");
				}
			}
		} else {
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
			return map;
		}
		return map;
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/paymentBatchAddCourse";
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

		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String lid = this.loginId;
		if (lid == null || "".equals(lid)) {// 如果this.loginId不为空则支付自己的专项。
			lid = us.getLoginId();
		}
		/*
		 * String[] studentIds = (String[]) ServletActionContext.getRequest()
		 * .getSession().getAttribute("params_ids");
		 * 
		 * expcetdc = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
		 * expcetdc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
		 * expcetdc.add(Restrictions.eq("prBzzTchOpencourse.peBzzBatch.id",
		 * id));
		 * expcetdc.add(Restrictions.in("prBzzTchOpencourse.peBzzStudent.id",
		 * studentIds)); dc = DetachedCriteria.forClass(PeBzzTchCourse.class);
		 * dc.createCriteria("enumConstByFlagCourseType",
		 * "enumConstByFlagCourseType"); dc.add(Subqueries.propertyNotIn("id",
		 * expcetdc));
		 */

		dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
		dc.createCriteria("peBzzBatch", "peBzzBatch");
		dc.createCriteria("peBzzTchCourse", "peBzzTchCourse");
		dc.createCriteria("enumConstByFlagChoose", "enumConstByFlagChoose");
		dc.add(Restrictions.eq("peBzzBatch.id", id));
		dc.add(Restrictions.eq("PrBzzTchOpencourse.enumConstByFlagChoose.code",
				"0"));
		return dc;
	}

	public Page list() {
		Page page = null;
		String id = this.id;

		String ids = this.getId();
		String stuids = this.getStuId();

		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT * ");
		sqlBuffer
				.append("FROM  (select pbtc.id id,                                                                         ");
		sqlBuffer
				.append("       pbtc.code  code,                                                                       ");
		sqlBuffer
				.append("       pbtc.name name,                                                                       ");
		sqlBuffer
				.append("       ec1.name courseType,                                                             ");
		sqlBuffer
				.append("       pbtc.price price,                                                                      ");
		sqlBuffer
				.append("       pbtc.time time,                                                                       ");
		sqlBuffer
				.append("       pbtc.teacher teacher,                                                                    ");
		sqlBuffer
				.append("       listagg(ec2.name, ',') within GROUP(order by pbtc.id) qualificationsType,        ");
		sqlBuffer
				.append("       ec3.name courseCategory,                                                         ");
		sqlBuffer
				.append("       ec4.name courseItemType,                                                         ");
		sqlBuffer
				.append("       ec5.name contentProperty                                                         ");
		sqlBuffer
				.append("  from pr_bzz_tch_opencourse pbto                                                       ");
		sqlBuffer
				.append(" inner join pe_bzz_batch pbb                                                            ");
		sqlBuffer
				.append("    on pbto.fk_batch_id = pbb.id                                                        ");
		sqlBuffer
				.append(" inner join pe_bzz_tch_course pbtc                                                      ");
		sqlBuffer
				.append("    on pbto.fk_course_id = pbtc.id                                                      ");
		sqlBuffer
				.append(" inner join enum_const ec1                                                              ");
		sqlBuffer
				.append("    on pbtc.flag_coursetype = ec1.id                                                    ");
		sqlBuffer
				.append("  left join PE_BZZ_TCH_COURSE_SUGGEST pbtcs                                             ");
		sqlBuffer
				.append("    on pbtc.id = pbtcs.fk_course_id                                                     ");
		sqlBuffer
				.append("  left join enum_const ec2                                                              ");
		sqlBuffer
				.append("    on pbtcs.fk_enum_const_id = ec2.id                                                  ");
		sqlBuffer
				.append("  left join enum_const ec3                                                              ");
		sqlBuffer
				.append("    on pbtc.flag_coursecategory = ec3.id                                                ");
		sqlBuffer
				.append("  left join enum_const ec4                                                              ");
		sqlBuffer
				.append("    on pbtc.flag_course_item_type = ec4.id                                              ");
		sqlBuffer
				.append("  left join enum_const ec5                                                              ");
		sqlBuffer
				.append("    on pbtc.flag_content_property = ec5.id                                              ");
		sqlBuffer
				.append(" where FLAG_CHOOSE = 'choose0'                                                          ");
		sqlBuffer.append("   and  pbb.id =  '" + id + "'");
		sqlBuffer.append(" and pbtc.id not in ");
		sqlBuffer.append("(  select pbtc.id                                                             ");
		  sqlBuffer.append("                          from pr_bzz_tch_stu_elective_back pbtseb                                               ");
		  sqlBuffer.append("                         inner join pr_bzz_tch_opencourse pbto                                                   ");
		  sqlBuffer.append("                            on pbtseb.fk_tch_opencourse_id = pbto.id                                             ");
		  sqlBuffer.append("                         inner join pe_bzz_tch_course pbtc                                                       ");
		  sqlBuffer.append("                            on pbto.fk_course_id = pbtc.id                                                       "); 
		  sqlBuffer.append("                         where fk_stu_id = '"+stuId+"'                                                              ");
		  sqlBuffer.append("                           and fk_tch_opencourse_id in                                                           ");
		  sqlBuffer.append("                               (select id                                                                        ");
		  sqlBuffer.append("                                  from pr_bzz_tch_opencourse                                                     ");
		  sqlBuffer.append("                                 where fk_batch_id =                                                             ");
		  sqlBuffer.append("                                       '"+id+"')                                   ");
		  sqlBuffer.append("                         group by pbtc.id)                                               ");
		sqlBuffer
				.append(" group by pbtc.id,                                                                      ");
		sqlBuffer
				.append("          pbtc.code,                                                                    ");
		sqlBuffer
				.append("          pbtc.name,                                                                    ");
		sqlBuffer
				.append("          ec1.name,                                                                     ");
		sqlBuffer
				.append("          pbtc.price,                                                                   ");
		sqlBuffer
				.append("          pbtc.time,                                                                    ");
		sqlBuffer
				.append("          pbtc.teacher,                                                                 ");
		sqlBuffer
				.append("          ec3.name,                                                                     ");
		sqlBuffer
				.append("          ec4.name,                                                                     ");
		sqlBuffer
				.append("          ec5.name                                                        )");
		sqlBuffer.append(" where 1=1 ");

		try {
			Map params = this.getParamsMap();
			Iterator iterator = params.entrySet().iterator();
			do {
				if (!iterator.hasNext())
					break;
				java.util.Map.Entry entry = (java.util.Map.Entry) iterator
						.next();
				String name = entry.getKey().toString();
				String value = ((String[]) entry.getValue())[0].toString();
				if (!name.startsWith("search__"))
					continue;
				if ("".equals(value))
					continue;
				if (name.indexOf(".") > 1
						&& name.indexOf(".") != name.lastIndexOf(".")) {
					name = name.substring(name.lastIndexOf(".", name
							.lastIndexOf(".") - 1) + 1);
				} else {
					name = name.substring(8);
				}

				if (name.equals("enumConstByFlagQualificationsType.name")) {
					name = "qualificationsType";
				}
				if (name.equals("enumConstByFlagCourseType.name")) {
					name = "courseType";
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

				if (name.equals("qualificationsType")) {
					sqlBuffer.append(" and INSTR(qualificationsType,'" + value
							+ "',1,1)>0");
				} else {
					sqlBuffer
							.append(" and " + name + " like '%" + value + "%'");
				}

			} while (true);
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	public String toAddCourse() {
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			/* 存储选中ids便于后续操作使用 */
			ServletActionContext.getRequest().getSession().setAttribute(
					"params_ids", ids);
			// this.method = "addCourse";
		} else {
			this.setMsg("请至少选择一条数据!");
			this.setTogo("back");
			return "pbmsg";
		}
		return "addCourse";

	}

	/**
	 * 判断此专项是否已经支付
	 */
	private void batchPayCheckByIds(UserSession us, String enterpriseID,
			String batchId, String[] ids) throws EntityException {

		DetachedCriteria sDc = DetachedCriteria.forClass(StudentBatch.class);
		sDc.createCriteria("peBzzBatch", "peBzzBatch");
		sDc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
		sDc.createCriteria("peBzzOrderInfo.enumConstByFlagPaymentState",
				"enumConstByFlagPaymentState", sDc.LEFT_JOIN);
		sDc.createCriteria("peBzzOrderInfo.peEnterprise", "peEnterprise",
				sDc.LEFT_JOIN);
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
				PeBzzOrderInfo bzzOrderInfo = ((StudentBatch) o)
						.getPeBzzOrderInfo();
				if (bzzOrderInfo.getPeEnterprise() != null
						&& enterpriseID.equals(bzzOrderInfo.getPeEnterprise()
								.getId())) {
					if (us.getLoginId().equals(
							bzzOrderInfo.getSsoUser().getLoginId())) {
						if (bzzOrderInfo.getEnumConstByFlagPaymentState() != null
								&& "1".equals(bzzOrderInfo
										.getEnumConstByFlagPaymentState()
										.getCode())) {
							throw new EntityException("err_batch_paid");
						} else {
							throw new EntityException("err_batch_in_pay");
						}
					} else {
						throw new EntityException("err_batch_out_auth");
					}
				} else {// 学生个人支付
					if (bzzOrderInfo.getEnumConstByFlagPaymentState() != null
							&& "1"
									.equals(bzzOrderInfo
											.getEnumConstByFlagPaymentState()
											.getCode())) {
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
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		peBzzOrderInfo = (PeBzzOrderInfo) ActionContext.getContext()
				.getSession().get("peBzzOrderInfo");
		if (peBzzOrderInfo == null) {
			this.setMsg("订单信息错误，请重新选择支付!");
			this.setTogo("back");
			return "pbmsg";
		}
		return "confirmOrderInfo";
	}

	/**
	 * 判断此机构下是否存在学员
	 */
	private List batchStuCheck(String lid) throws EntityException {
		String sql = "\n"
				+ "select pe.code\n"
				+ "  from pe_enterprise pe\n"
				+ " inner join pe_enterprise_manager pem on pe.id = pem.fk_enterprise_id\n"
				+ " where pem.login_id = '" + lid + "'";
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
		dc.createCriteria("peEnterprise.peEnterprise", "peEnterprise1",
				dc.LEFT_JOIN);
		dc.add(Restrictions.or(Restrictions.eq("peEnterprise.code",
				enterpriseId), Restrictions.eq("peEnterprise1.code",
				enterpriseId)));
		// dc.add(Restrictions.eq("peEnterprise.code", enterpriseId));

		EnumConst enumConstByFlagBatchPayState = this.enumConstService
				.getByNamespaceCode("FlagBatchPayState", "0"); // 未支付
		DetachedCriteria sbDc = DetachedCriteria.forClass(StudentBatch.class);
		sbDc.createCriteria("peBzzBatch", "peBzzBatch");
		sbDc.createCriteria("peStudent", "peStudent");
		sbDc.setProjection(Property.forName("peStudent.id"));
		sbDc.add(Restrictions.eq("peBzzBatch.id", this.id));
		sbDc.add(Restrictions.eq("enumConstByFlagBatchPayState",
				enumConstByFlagBatchPayState));
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
			peBzzBatch = (PeBzzBatch) this.getGeneralService().getList(batchDc)
					.get(0);
			ServletActionContext.getRequest().getSession().setAttribute(
					"peBzzBatch", peBzzBatch);
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
	private void batchPayCheck(UserSession us, String enterpriseID,
			String batchId) throws EntityException {
		DetachedCriteria sDc = DetachedCriteria.forClass(StudentBatch.class);
		sDc.createCriteria("peBzzBatch", "peBzzBatch");
		sDc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
		sDc.createCriteria("peBzzOrderInfo.enumConstByFlagPaymentState",
				"enumConstByFlagPaymentState", sDc.LEFT_JOIN);
		sDc.createCriteria("peBzzOrderInfo.peEnterprise", "peEnterprise",
				sDc.LEFT_JOIN);
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
				PeBzzOrderInfo bzzOrderInfo = ((StudentBatch) o)
						.getPeBzzOrderInfo();
				if (bzzOrderInfo.getPeEnterprise() != null
						&& enterpriseID.equals(bzzOrderInfo.getPeEnterprise()
								.getId())) {
					if (us.getLoginId().equals(
							bzzOrderInfo.getSsoUser().getLoginId())) {
						if (bzzOrderInfo.getEnumConstByFlagPaymentState() != null
								&& "1".equals(bzzOrderInfo
										.getEnumConstByFlagPaymentState()
										.getCode())) {
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
							&& "1"
									.equals(bzzOrderInfo
											.getEnumConstByFlagPaymentState()
											.getCode())) {
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
		map.put("err_batch_out_auth", new String[] {
				"提交数据已存在订单，但订单由其他用户提交，您不能操作，请重新选择！", "不能操作" });
		map.put("err_batch_paid", new String[] { "此专项已支付", "已支付" });
		map.put("err_batch_out_date", new String[] { "当前时间不在支付时间段内", "已过期" });
		map.put("err_batch_no_course", new String[] { "该专项内没有课程，无法支付", "无课程" });
		map.put("err_batch_in_pay", new String[] {
				"提交数据已存在订单数据，请到“报名付费历史查询”中查看订单并继续支付。", "已存在订单" });
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

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
}
