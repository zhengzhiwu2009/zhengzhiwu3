package com.whaty.platform.entity.web.action.basic;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.AssignRecord;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeElectiveCoursePeriod;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.basic.PeElectiveCoursePeriodService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

/**
 * 选课期管理
 * 
 * @author
 * 
 */
public class PeElectiveCoursePeriodAction extends MyBaseAction<PeElectiveCoursePeriod> {

	private List<PrBzzTchStuElective> electiveList;
	private List<PrBzzTchStuElectiveBack> electiveBackList;
	private String id;
	private BigDecimal allTime = new BigDecimal(0.0).setScale(2, BigDecimal.ROUND_HALF_UP);
	private BigDecimal allAmount = new BigDecimal(0.0).setScale(2, BigDecimal.ROUND_HALF_UP);
	private BigDecimal bTimes =new BigDecimal(0.0).setScale(1, BigDecimal.ROUND_HALF_UP);
	private BigDecimal xTimes =new BigDecimal(0.0).setScale(1, BigDecimal.ROUND_HALF_UP);
	private int num = 0;
	private String isInvoice;
	private String payMethod;
	private String sso_user_amount;
	private PeBzzOrderInfo peBzzOrderInfo;
	private PeBzzInvoiceInfo peBzzInvoiceInfo;
	private EnumConstService enumConstService;
	private String merorderid; // 由银行生成的单据号码
	private String periodId; // 由系统生成的订单号
	private PeElectiveCoursePeriodService peElectiveCoursePeriodService;

	private String regNo;
	private String stuName;
	private String courseCode;
	private String courseName;
	private boolean isFormedOrder;
	private String courseType;

	private int totalCount; // 总条数
	private int startIndex = 0; // 开始数
	private int pageSize = 10; // 页面显示数
	private Page page;
	private String sysno;// 系统编号
	private String username;// 用户名称
	private String zige;// 资格类型
	private String iszige;// 是否有资格
	private String isyx;// 是否有效
	private String ydphone;// 移动电话
	private String orgid;// 机构id
	private String idcard;// 证件号码

	private List studentlist;// 学员列表

	private List courseTypeList;// 课程性质列表

	private String method;

	private String stuid;

	private String delIds;

	private List zigelist;// 资格列表

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public PeElectiveCoursePeriodService getPeElectiveCoursePeriodService() {
		return peElectiveCoursePeriodService;
	}

	public void setPeElectiveCoursePeriodService(PeElectiveCoursePeriodService peElectiveCoursePeriodService) {
		this.peElectiveCoursePeriodService = peElectiveCoursePeriodService;
	}

	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}

	public String getMerorderid() {
		return merorderid;
	}

	public void setMerorderid(String merorderid) {
		this.merorderid = merorderid;
	}

	public List<PrBzzTchStuElectiveBack> getElectiveBackList() {
		return electiveBackList;
	}

	public void setElectiveBackList(List<PrBzzTchStuElectiveBack> electiveBackList) {
		this.electiveBackList = electiveBackList;
	}

	/**
	 * 初始化列表
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		Date date = new Date();
		String sysda = date.toString();
		this.getGridConfig().setTitle(this.getText("选课期方式"));
		this.getGridConfig().setCapability(true, true, true);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"), "name");
		this.getGridConfig().addColumn(this.getText("开始时间"), "beginDatetime");
		this.getGridConfig().addColumn(this.getText("结束时间"), "endDatetime");
		this.getGridConfig().addColumn(this.getText("报名学时数下限"), "compulsoryhour", false, true, false, Const.fee_for_extjs);// 加表单校验
		// lzh
		this.getGridConfig().addColumn(this.getText("报名学时数上限"), "stuTime", false, true, false, Const.fee_for_extjs);// 加表单校验
		// lzh
		this.getGridConfig().addColumn(this.getText("报名必修学时下限"), "totalhour", false, true, false, Const.fee_for_extjs);// 加表单校验
		// lzh
		// this.getGridConfig().addColumn(this.getText("报名金额上限"),
		// "amountuplimit",false,true, false,"");
		this.getGridConfig().addColumn(this.getText("各学员报名金额上限"), "amountuplimit", false, true, false, Const.fee_for_extjs);// 加表单校验
		// lzh
		this.getGridConfig().addMenuFunction(this.getText("结束选课期"), "peElectiveCoursePeriod_endPeriod.action", false, true);

		this.getGridConfig().addMenuFunction(this.getText("添加学员"), "/entity/basic/peElectiveCoursePeriod_toSelectStudent.action?method=stuAdd", true, false);
		this.getGridConfig().addMenuTips("选课期最多允许添加1000人，总选课量小于30000！", "");
		// this.getGridConfig().addColumn(this.getText("选课期状态"), "period",
		// true,false, false, "TextField", false, 3);

		ColumnConfig xkstatus = new ColumnConfig(this.getText("选课期状态"), "Combobox_period", true, false, false, "TextField", false, 100, "");
		//String sql7 = "select a.id ,a.name from enum_const a where a.namespace='FlagPeriodStatus'";
		String sql7 = "select '1','未结束' FROM DUAL UNION SELECT '2','已结束' FROM DUAL";
		xkstatus.setComboSQL(sql7);
		this.getGridConfig().addColumn(xkstatus);

		this.getGridConfig().addRenderScript(this.getText("选课期状态"), "{" + "  if(record.data['Combobox_period']==1) return '<font color=red>已结束</font>';else return '<font color=black>未结束</font>' }", "id");
		// this.getGridConfig().addRenderFunction(this.getText("查看学员"),
		// "<a
		// href=\"/entity/basic/addStudentToPeriod.action?id=${value}&flag=stuDel\">查看学员</a>",
		// "id");
		// 移至上面
		// this.getGridConfig().addRenderScript(
		// this.getText("添加学员"),
		// "{ "
		// + " if(record.data['period']==1) return '<font
		// color=red>不在操作日期</font>';else return '<a
		// href=\"/entity/basic/addStudentToPeriod.action?id='+record.data['id']+'&flag=stuAdd\">添加学员</a>'}",
		// "id");

		// this.getGridConfig().addColumn(this.getText("是否已支付"),
		// "code",true,false,false,"TextField");
		this.getGridConfig().addColumn(this.getText("支付状态"), "code", false, false, false, "");
		this.getGridConfig().addColumn(this.getText("期内人数"), "ssnum", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("已选课人数"), "snum", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("已选课数量"), "cnum", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("已选学时数"), "tnum", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("已选金额"), "mnum", false, false, true, "");
		this.getGridConfig().addRenderScript(this.getText("支付状态"),
				"{" + "if(record.data['code'] == 1)" + "	return '已支付';" + "else if(record.data['code'] == 2)" + "	return '待确认';" + "else return '<a href=\"/entity/basic/peElectiveCoursePeriod_toConfirmOrder.action?bean.id='+record.data['id']+'\", target=\"_blank\">去支付</a>';}", "id");
		this.getGridConfig().addRenderFunction(this.getText("查看详情"), "<a target=\"_blank\" href=\"/entity/basic/peElectiveCoursePeriod_viewDetail.action?bean.id=${value}\">查看选课详情</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("查看学员"), "<a href=\"/entity/basic/addStudentToPeriod.action?id=${value}&flag=stuDel\">查看学员</a>", "id");
	}

	/**
	 * 去选择学员(添加学员) dinggh
	 * 
	 * @return
	 */
	public String toSelectStudent() {
		try {
			if (this.getIds() != null && this.getIds().length() > 0) {
				String[] ids = getIds().split(",");
				for (int i = 0; i < ids.length; i++) {
					id = ids[i];
					this.setId(id);
				}
			}
			String sql = "SELECT 1 FROM PE_ELECTIVE_COURSE_PERIOD WHERE END_DATE <= SYSDATE AND ID = '" + id + "'";
			List list = this.getGeneralService().getBySQL(sql);
			if (null != list && list.size() > 0) {
				this.setMsg("选课期已结束，不能添加学员");
				this.setTogo("/entity/basic/peElectiveCoursePeriod.action");
				return "msg";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toSelectStudent";
	}

	/**
	 * 将学员从选课程期中移除
	 * 
	 * @return
	 */
	public String toDeleteStudent() {
		String delCouSql = "DELETE COURSE_PERIOD_STUDENT CPS WHERE CPS.COURSE_PERIOD_ID='" + this.getId() + "' AND CPS.STUDENT_ID='" + this.getStuid() + "'";
		String delElBack = "DELETE PR_BZZ_TCH_STU_ELECTIVE_BACK SEB WHERE SEB.FK_ELE_COURSE_PERIOD_ID='" + this.getId() + "' AND SEB.FK_STU_ID='" + this.getStuid() + "'";
		try {
			this.getGeneralService().executeBySQL(delCouSql);
			this.getGeneralService().executeBySQL(delElBack);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return viewStudentInfo();
	}

	/**
	 * 将学员从选课程期中批量移除
	 * 
	 * @return
	 */
	public String toDeleteStudentAll() {
		try {
			if (null != delIds && !"".equals(delIds)) {
				String[] strArry = delIds.split(",");
				for (int i = 0; i < strArry.length; i++) {
					this.getGeneralService().executeBySQL("DELETE COURSE_PERIOD_STUDENT CPS WHERE CPS.COURSE_PERIOD_ID='" + this.getId() + "' AND CPS.STUDENT_ID='" + strArry[i].toString() + "'");
					this.getGeneralService().executeBySQL("DELETE PR_BZZ_TCH_STU_ELECTIVE_BACK SEB WHERE SEB.FK_ELE_COURSE_PERIOD_ID='" + this.getId() + "' AND SEB.FK_STU_ID='" + strArry[i].toString() + "'");
				}
			}
			this.setId(this.getId());
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return viewStudentInfo();
	}

	/**
	 * 结束选课期
	 * 
	 * @author linjie
	 * @return
	 */
	public String endPeriod() {
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			try {
				DetachedCriteria tempdc = DetachedCriteria.forClass(PeElectiveCoursePeriod.class);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				tempdc.add(Restrictions.in("id", ids));
				List<PeElectiveCoursePeriod> sslist = new ArrayList<PeElectiveCoursePeriod>();
				sslist = this.getGeneralService().getList(tempdc);
				Iterator<PeElectiveCoursePeriod> iterator = sslist.iterator();
				while (iterator.hasNext()) {
					PeElectiveCoursePeriod peElectiveCoursePeriod = iterator.next();
					Date startDate = peElectiveCoursePeriod.getBeginDatetime();
					if (startDate.after(new Date())) {
						map.clear();
						map.put("success", "false");
						map.put("info", "此选课期未开始,不能结束!");
						this.setJsonString(JsonUtil.toJSONString(map));
						JsonUtil.setDateformat("yyyy-MM-dd");
						return json();
					}
					peElectiveCoursePeriod.setEndDatetime(df.parse(df.format(new Date())));
					this.getGeneralService().save(peElectiveCoursePeriod);
				}
			} catch (Exception e) {
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				this.setJsonString(JsonUtil.toJSONString(map));
				JsonUtil.setDateformat("yyyy-MM-dd");
				return json();
			}
			map.put("success", "true");
			map.put("info", " 结束选课期操作成功！");
		} else {
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
			this.setJsonString(JsonUtil.toJSONString(map));
			JsonUtil.setDateformat("yyyy-MM-dd");
			return json();
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		JsonUtil.setDateformat("yyyy-MM-dd");
		this.list();
		return json();
	}

	/**
	 * 重写框架方法：选课期信息（带sql条件）
	 * 
	 * @author linjie
	 * @return
	 */
	public Page list() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String rightNow = sdf.format(now);
		Page page = null;
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from  (SELECT P.ID AS ID, ");
		sqlBuffer.append("        P.NAME AS NAME, ");
		sqlBuffer.append("        TO_CHAR(P.BEGIN_DATE,'yyyy-MM-dd hh24:mi') AS begindatetime, ");
		sqlBuffer.append("        TO_CHAR(P.END_DATE,'yyyy-MM-dd hh24:mi') AS endDatetime, ");
		sqlBuffer.append("        P.COMPULSORY_HOUR AS COMPULSORYHOUR, ");
		sqlBuffer.append("        P.STU_TIME AS STUTIME, ");
		sqlBuffer.append("        P.TOTAL_HOUR AS TOTALHOUR, ");
		sqlBuffer.append("        P.AMOUNT_UP_LIMIT AS AMOUNTUPLIMIT, ");
		sqlBuffer.append("        TO_NUMBER(CASE ");
		sqlBuffer.append("                    WHEN P.END_DATE < ");
		sqlBuffer.append("                         sysdate THEN ");
		sqlBuffer.append("                     '1' ");
		sqlBuffer.append("                    ELSE ");
		sqlBuffer.append("                     '2' ");
		sqlBuffer.append("                  END) AS COMBOBOX_PERIOD, ");
		sqlBuffer.append("        P.E.CODE AS CODE, ");
		sqlBuffer.append("        (SELECT COUNT(1) ");
		sqlBuffer.append("           FROM COURSE_PERIOD_STUDENT T ");
		sqlBuffer.append("          WHERE T.COURSE_PERIOD_ID = P.ID) AS SSNUM, ");
		sqlBuffer.append("        NVL(PX.SNUM, 0) AS SNUM, ");
		sqlBuffer.append("        NVL(PX.CNUM, 0) AS CNUM, ");
		sqlBuffer.append("        NVL(PX.TNUM, 0) AS TNUM, ");
		sqlBuffer.append("        NVL(PX.MNUM, 0) AS MNUM ");
		sqlBuffer.append("   FROM PE_ELECTIVE_COURSE_PERIOD P ");
		sqlBuffer.append("   LEFT OUTER JOIN ENUM_CONST E ");
		sqlBuffer.append("     ON P.FLAG_ELEPERIODPAYSTATUS = E.ID ");
		sqlBuffer.append("   LEFT OUTER JOIN (SELECT BAK.FK_ELE_COURSE_PERIOD_ID AS PID, ");
		sqlBuffer.append("                           COUNT(DISTINCT BAK.FK_STU_ID) AS SNUM, ");
		sqlBuffer.append("                           COUNT(DISTINCT PBTC.ID) AS CNUM, ");
		sqlBuffer.append("                           SUM(PBTC.TIME) AS TNUM, ");
		sqlBuffer.append("                           SUM(PBTC.PRICE) AS MNUM ");
		sqlBuffer.append("                      FROM PR_BZZ_TCH_STU_ELECTIVE_BACK BAK, ");
		sqlBuffer.append("                           PE_ELECTIVE_COURSE_PERIOD    PECP, ");
		sqlBuffer.append("                           PE_BZZ_STUDENT               PBS, ");
		sqlBuffer.append("                           PR_BZZ_TCH_OPENCOURSE        PBTO, ");
		sqlBuffer.append("                           PE_BZZ_TCH_COURSE            PBTC ");
		sqlBuffer.append("                     WHERE PECP.ID = BAK.FK_ELE_COURSE_PERIOD_ID ");
		sqlBuffer.append("                       AND PECP.USER_ID = '" + us.getSsoUser().getId() + "' ");
		sqlBuffer.append("                       AND PBS.ID = BAK.FK_STU_ID ");
		sqlBuffer.append("                       AND BAK.FK_TCH_OPENCOURSE_ID = PBTO.ID ");
		sqlBuffer.append("                       AND PBTO.FK_COURSE_ID = PBTC.ID ");
		sqlBuffer.append("                     GROUP BY BAK.FK_ELE_COURSE_PERIOD_ID) PX ");
		sqlBuffer.append("     ON P.ID = PX.PID ");
		sqlBuffer.append("  WHERE P.USER_ID = '" + us.getSsoUser().getId() + "' ) where 1=1");
		try {
			// 处理时间的查询参数
			ActionContext context = ActionContext.getContext();
			if (context.getParameters() != null) {
				Map params = this.getParamsMap();
				if (params != null) {
					if (params.get("search__beginDatetime") != null) {
						String[] beginDate = (String[]) params.get("search__beginDatetime");
						String beginDates = beginDate[0];
						if (beginDate.length == 1 && !StringUtils.defaultString(beginDate[0]).equals("")) {
							/*
							 * beginDate[0] = ">=" + beginDate[0];
							 * params.put("search__beginDatetime", beginDate);
							 */
							params.remove("search__beginDatetime");
							context.setParameters(params);
							sqlBuffer.append("           and TO_DATE(begindatetime,'yyyy-MM-dd hh24:mi') >= to_date('" + beginDates + "','yyyy-MM-dd hh24:mi:ss') \n");
						}
					}
					if (params.get("search__endDatetime") != null) {
						String[] endDate = (String[]) params.get("search__endDatetime");
						String endDates = endDate[0];
						if (endDate.length == 1 && !StringUtils.defaultString(endDate[0]).equals("")) {
							/*
							 * endDate[0] = "<= " + endDate[0];
							 * params.put("search__endDatetime", endDate);
							 */
							params.remove("search__endDatetime");
							context.setParameters(params);
							sqlBuffer.append("           and TO_DATE(endDatetime,'yyyy-MM-dd hh24:mi') <= to_date('" + endDates + "','yyyy-MM-dd hh24:mi:ss') \n");
						}
					}
					if (params.get("search__Combobox_period") != null) {
						String[] endDate = (String[]) params.get("search__Combobox_period");
						String v = "";
						if (endDate[0].equals("已结束")) {
							String[] str = new String[] { "1" };
							v = "1";
							params.put("search__Combobox_period", str);
							sqlBuffer.append(" AND COMBOBOX_PERIOD = '" + v + "'");
						} else if (endDate[0].equals("未结束")) {
							String[] str = new String[] { "2" };
							v = "2";
							params.put("search__Combobox_period", str);
							sqlBuffer.append(" AND COMBOBOX_PERIOD = '" + v + "'");
						}
						params.remove("search__Combobox_period");
					}
					context.setParameters(params);

				}
			}
			this.setSqlCondition(sqlBuffer);
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 重写框架方法：选课期信息
	 * 
	 * @author linjie
	 * @return
	 */
	public String abstractDetail() {
		Page page = null;
		Map map = new HashMap();
		DetachedCriteria enterdc = DetachedCriteria.forClass(PeElectiveCoursePeriod.class);
		enterdc.add(Restrictions.eq("id", this.getBean().getId()));
		try {
			page = this.getGeneralService().getByPage(enterdc, Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
			if (page != null) {
				page.setItems(JsonUtil.ArrayToJsonObjects(page.getItems(), this.getGridConfig().getListColumnConfig()));
				Container o = new Container();
				o.setBean(page.getItems().get(0));
				List list = new ArrayList();
				list.add(o);
				map.put("totalCount", 1);
				map.put("models", list);
			}
			JsonUtil.setDateformat("yyyy-MM-dd HH:mm:ss");
			this.setJsonString(JsonUtil.toJSONString(map));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return json();
	}

	/**
	 * 重写框架方法：添加数据前的校验
	 * 
	 * @author linjie
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public void checkBeforeAdd() throws EntityException {
		Date endDate = this.getBean().getEndDatetime();
		this.getBean().setEndDatetime(endDate);
		if (this.getBean().getBeginDatetime().after(this.getBean().getEndDatetime())) {
			throw new EntityException("结束时间应晚于开始时间");
		}
		// 学时上限不可以小于下线
		if (Integer.parseInt(this.getBean().getStuTime()) < Integer.parseInt(this.getBean().getCompulsoryhour())) {
			throw new EntityException("学时上限不可以小于下限");
		}
		// 必修课时不能大于学时上限
		if (!(Integer.parseInt(this.getBean().getStuTime()) >= Integer.parseInt(this.getBean().getTotalhour()))) {
			throw new EntityException("必修课时不能大于学时上限");
		}
		String hql = "from PeElectiveCoursePeriod s where s.name='" + this.getBean().getName() + "'";
		List list1 = this.getGeneralService().getByHQL(hql);
		if (list1 != null && list1.size() > 0) {
			throw new EntityException("系统中存在相同名称的选课期，请换个名称！");
		}
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		this.getBean().setSsoUser(us.getSsoUser());
		String sql = " select ec.id from enum_const ec where ec.namespace='FlagElePeriodPayStatus' and ec.code='0'";
		List list = this.getGeneralService().getBySQL(sql);

		if (list != null && list.size() > 0) {
			EnumConst ec = new EnumConst();
			ec.setId((String) list.get(0));
			this.getBean().setEnumConstByFlagElePeriodPayStatus(ec);
		}
		// this.getBean().setEnumConstByFlagElePeriodPayStatus(enumConstByFlagElePeriodPayStatus);

	}

	/**
	 * 重写框架方法：更新数据前的校验
	 * 
	 * @author linjie
	 * @return
	 */
	public void checkBeforeUpdate() throws EntityException {
		DetachedCriteria dc = DetachedCriteria.forClass(PeElectiveCoursePeriod.class);
		dc.createCriteria("enumConstByFlagElePeriodPayStatus");
		dc.add(Restrictions.eq("id", this.getBean().getId()));
		List list = this.getGeneralService().getList(dc);
		EnumConst ec = null;
		if (list != null) {
			if (list.size() > 0) {
				ec = ((PeElectiveCoursePeriod) list.get(0)).getEnumConstByFlagElePeriodPayStatus();
				Date endDate = this.getBean().getEndDatetime();
				// endDate.setHours(23);
				// endDate.setMinutes(59);
				// endDate.setSeconds(59);
				this.getBean().setEndDatetime(endDate);
			}
		}
		if (ec != null) {
			if (ec.getCode().equals("1")) {
				throw new EntityException("选课期已经支付，不能修改");
			}
		}
		if (this.getBean().getBeginDatetime().after(this.getBean().getEndDatetime())) {
			throw new EntityException("结束时间应晚于开始时间");
		}
		// 学时上限不可以小于下线
		if (Integer.parseInt(this.getBean().getStuTime()) < Integer.parseInt(this.getBean().getCompulsoryhour())) {
			throw new EntityException("学时上限不可以小于下限");
		}
		// 必修课时不能大于学时上限
		if (!(Integer.parseInt(this.getBean().getStuTime()) >= Integer.parseInt(this.getBean().getTotalhour()))) {
			throw new EntityException("必修课时不能大于学时上限");
		}
	}

	/**
	 * 确认订单
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toConfirmOrder() {
		getInvoice();// 获得历史发票信息
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(PeElectiveCoursePeriod.class);
			dc.createCriteria("enumConstByFlagElePeriodPayStatus", "enumConstByFlagElePeriodPayStatus", dc.INNER_JOIN);
			dc.add(Restrictions.eq("id", this.getBean().getId()));
			List<PeElectiveCoursePeriod> periodList = this.getGeneralService().getList(dc);
			String code = periodList.get(0).getEnumConstByFlagElePeriodPayStatus().getCode();
			Date startDate = periodList.get(0).getBeginDatetime();
			Date endDate = periodList.get(0).getEndDatetime();
			if (endDate.after(new Date())) {
				this.setMsg("选课期未结束，不能支付。");
				this.setTogo("close");
				return "paymentMsg";
			}
			if (code.equals("1")) {
				this.setMsg("选课期已支付，请刷新选课期列表，不要重新提交。");
				this.setTogo("close");
				return "paymentMsg";
			} else {
				EnumConst enumConstByFlagOrderIsValid = this.getEnumConstService().getByNamespaceCode("FlagOrderIsValid", "1");
				DetachedCriteria dc1 = DetachedCriteria.forClass(PeBzzOrderInfo.class);
				dc1.createCriteria("peElectiveCoursePeriod", "peElectiveCoursePeriod", dc1.INNER_JOIN);
				dc1.createCriteria("enumConstByFlagPaymentState", "enumConstByFlagPaymentState", dc1.INNER_JOIN);
				dc1.add(Restrictions.eq("enumConstByFlagOrderIsValid", enumConstByFlagOrderIsValid));
				dc1.add(Restrictions.eq("peElectiveCoursePeriod.id", this.getBean().getId()));
				List orderList = this.getGeneralService().getList(dc1);
				if (orderList != null && orderList.size() > 0) {
					PeBzzOrderInfo orderTemp = (PeBzzOrderInfo) orderList.get(0);
					if (orderTemp != null && orderTemp.getEnumConstByFlagPaymentState() != null && "1".equals(orderTemp.getEnumConstByFlagPaymentState().getCode())) {
						this.setMsg("选课期已支付，请刷新选课期列表，不要重新提交。");
						this.setTogo("close");
						return "paymentMsg";
					} else {
						this.setMsg("选课期存在订单，请至“报名付费历史查询”中继续支付。");
						this.setTogo("close");
						return "paymentMsg";
					}
				}
			}
		} catch (EntityException e1) {
			e1.printStackTrace();
		}

		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT sum(pbtc.price) AS allAmount, ");
		sqlBuffer.append("       nvl(Sum(pbtc.time),0)  AS allTime, ");
		sqlBuffer.append("		 ec.name ");
		sqlBuffer.append("FROM   pr_bzz_tch_stu_elective_back pbtse ");
		sqlBuffer.append("       INNER JOIN pr_bzz_tch_opencourse pbto ");
		sqlBuffer.append("         ON pbtse.fk_tch_opencourse_id = pbto.id ");
		sqlBuffer.append("       INNER JOIN pe_bzz_tch_course pbtc ");
		sqlBuffer.append("         ON pbto.fk_course_id = pbtc.id ");
		sqlBuffer.append("       INNER JOIN pe_elective_course_period pecp ");
		sqlBuffer.append("         ON pbtse.fk_ele_course_period_id = pecp.id ");
		sqlBuffer.append("       LEFT JOIN enum_const ec  ");
		sqlBuffer.append("         ON ec.namespace='ClassHourRate' and ec.code='0' ");
		sqlBuffer.append("WHERE  pecp.id = '" + this.getBean().getId() + "' ");
		sqlBuffer.append("group by ec.name");

		try {
			List<Object[]> tempList = this.getGeneralService().getBySQL(sqlBuffer.toString());
			this.setBean(this.getGeneralService().getById(PeElectiveCoursePeriod.class, this.getBean().getId()));
			if (this.getBean().getEndDatetime().after(new Date())) {
				this.setMsg("选课期尚未结束，无法支付");
				this.setTogo("close");
				return "paymentMsg";
			}

			if (tempList != null && tempList.size() > 0) {
				this.allAmount = new BigDecimal((tempList.get(0)[0]).toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
				this.allTime = new BigDecimal((tempList.get(0)[1]).toString()).setScale(1, BigDecimal.ROUND_HALF_UP);
				if (this.allAmount.compareTo(BigDecimal.ZERO) == 0 && this.allTime.compareTo(BigDecimal.ZERO) == 0) {
					this.setMsg("该选课期内没有选课信息，无法支付");
					this.setTogo("close");
					return "paymentMsg";
				}
				this.peBzzOrderInfo = new PeBzzOrderInfo();
				this.peBzzOrderInfo.setSeq(this.getGeneralService().getOrderSeq());
				this.peBzzOrderInfo.setPeElectiveCoursePeriod(this.getBean());// 给订单加入选课期外键。
				this.peBzzOrderInfo.setAmount(this.allAmount.toString());
				this.peBzzOrderInfo.setClassHour(this.allTime.toString());

				ServletActionContext.getRequest().getSession().setAttribute("peBzzOrderInfo", peBzzOrderInfo);
				ServletActionContext.getRequest().getSession().setAttribute("allAmount", this.allAmount);
				ServletActionContext.getRequest().getSession().setAttribute("allTime", this.allTime);
				ServletActionContext.getRequest().getSession().setAttribute("eleCouPeriodId", this.getBean().getId());
			} else {
				this.setMsg("该选课期内没有选课信息，无法支付");
				this.setTogo("close");
				return "paymentMsg";
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "confirmOrderInfo";
	}

	/**
	 * 选课期支付方式提示
	 * 
	 * @author linjie
	 * @return
	 */
	public String toPayment() {
		if (this.payMethod == null || "".equalsIgnoreCase(this.payMethod) || "0,1,2,3".indexOf(this.payMethod) < 0) {
			this.setMsg("请正确选择支付方式！");
			this.setTogo("close");
			return "paymentMsg";
		}
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);

		DetachedCriteria dcSsoUser = DetachedCriteria.forClass(SsoUser.class);
		dcSsoUser.add(Restrictions.eq("id", us.getSsoUser().getId()));

		SsoUser user = new SsoUser();
		// 获取托管状态的ssoUser实例
		try {
			user = (SsoUser) this.getGeneralService().getDetachList(dcSsoUser).get(0);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}

		// 初始化订单，选课数据
		this.initOrderAndElective();
		// 得到学时
		String sumAmount = (user.getSumAmount() == null || user.getSumAmount().equals("")) ? "0" : user.getSumAmount();
		String amount = (user.getAmount() == null || user.getSumAmount().equals("")) ? "0" : user.getAmount();

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

			ServletActionContext.getRequest().getSession().setAttribute("electiveBackList", this.electiveBackList);
			ServletActionContext.getRequest().getSession().setAttribute(this.peBzzOrderInfo.getSeq(), "elePeriodPayment");
			// 新增快钱支付
			String paymentType = ServletActionContext.getRequest().getParameter("paymentType");
			ServletActionContext.getRequest().getSession().setAttribute("paymentType", paymentType);
			return "onlinePayment";
		} else {
			PeBzzOrderInfo _peBzzOrderInfo = (PeBzzOrderInfo) ActionContext.getContext().getSession().get("peBzzOrderInfo");
			this.setTogo("/entity/basic/peElectiveCoursePeriod.action");
			if ("1".equalsIgnoreCase(this.payMethod)) {// 预付费支付
				// Lee start 2014年11月14日 预付费支付余额比较
				EnumConst ec = this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0");
				BigDecimal balance = new BigDecimal(sumAmount).subtract(new BigDecimal(amount)).multiply(new BigDecimal(ec.getName())).setScale(2, BigDecimal.ROUND_HALF_UP);
				if (balance.compareTo(this.allAmount) == -1) {
					// Lee end
					// Lee ↓原版
					// BigDecimal balance = new
					// BigDecimal(sumAmount).subtract(new BigDecimal(amount));
					// 和学时比较
					// if (balance.compareTo(this.allTime) == -1) {
					this.setMsg("当前账户余额不足");
					this.setTogo("close");
					return "paymentMsg";
				} else {
					this.sso_user_amount = amount;
					// 转成学时
					user.setAmount(new BigDecimal(amount).add(new BigDecimal(_peBzzOrderInfo.getClassHour())).toString());

					try {
						// 实例化订单
						this.generateOrder(user);

						// 分别插入其他两张表
						// dgh--------------------------------------------------------------------------

						BigDecimal zhyAmountnew = new BigDecimal(user.getSumAmount()).subtract(new BigDecimal(user.getAmount()));// 账户余额

						EnumConst enumConstByFlagOperateType = this.getEnumConstService().getByNamespaceCode("FlagOperateType", "5"); // 金额分配记录
						List saveList = new ArrayList();
						AssignRecord assignRecord = new AssignRecord();
						assignRecord.setSsoUser(user);
						assignRecord.setAssignStyle("operate5");
						assignRecord.setAssignDate(new Date());
						assignRecord.setAccountAmount(zhyAmountnew.toString());
						// 改成学时
						assignRecord.setOperateAmount(_peBzzOrderInfo.getClassHour());
						assignRecord.setEnumConstByFlagOperateType(enumConstByFlagOperateType);
						assignRecord.setPeBzzOrderInfo(peBzzOrderInfo);
						saveList.add(assignRecord);

						this.getGeneralService().saveList(saveList);
						// 分别插入其他两张表
						// dgh--------------------------------------------------------------------------
					} catch (EntityException e) {
						e.printStackTrace();
						this.setMsg("订单生成失败，请联系管理员");
						this.setTogo("close");
						return "paymentMsg";
					}
					try {
						// 确认订单
						this.getGeneralService().updatePeBzzOrderInfo(peBzzOrderInfo, "confirm", user);
					} catch (EntityException e) {
						e.printStackTrace();
						user.setAmount(this.sso_user_amount);
						this.setMsg("支付失败:" + e.getMessage());
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
		}
		this.destroyOrderSession();
		this.setTogo("close");
		return "paymentMsg";
	}

	/**
	 * 后来添加，再次支付
	 */
	public String continuePaymentOnline() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.add(Restrictions.eq("seq", this.merorderid));
		try {
			this.peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralService().getList(dc).get(0);
			ServletActionContext.getRequest().getSession().setAttribute("peBzzOrderInfo", peBzzOrderInfo);
			ServletActionContext.getRequest().getSession().setAttribute(this.peBzzOrderInfo.getSeq(), "elePeriodPayment");
			// 新增快钱支付
			String paymentType = ServletActionContext.getRequest().getParameter("paymentType");
			ServletActionContext.getRequest().getSession().setAttribute("paymentType", paymentType);
		} catch (EntityException e) {
			this.setMsg("选课期继续支付失败,请联系协会管理员");
			this.setTogo("close");
			return "paymentMsg";
		}
		return "onlinePayment";
	}

	/**
	 * 第三方支付时首先生成订单，选课信息保存在选课临时表中
	 * 
	 * @author linjie
	 */
	public void generateOrder(SsoUser user) throws EntityException {
		this.peBzzOrderInfo.setSsoUser(user);
		try {
			this.electiveBackList = this.getGeneralService().saveElectiveBackList(this.electiveBackList, peBzzOrderInfo, peBzzInvoiceInfo, null, this.getBean(), null);
		} catch (EntityException e) {
			throw e;
		}
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

		// 支付方式
		EnumConst enumConstByFlagPaymentMethod = this.getEnumConstService().getByNamespaceCode("FlagPaymentMethod", this.payMethod);
		EnumConst enumConstByFlagPaymentType = this.getEnumConstService().getByNamespaceCode("FlagPaymentType", "1");// 集体支付
		EnumConst enumConstByFlagOrderType = this.getEnumConstService().getByNamespaceCode("FlagOrderType", "3");// 订单类型，选课期选课订单
		EnumConst enumConstByFlagOrderState = this.getEnumConstService().getByNamespaceCode("FlagOrderState", "1");// 订单状态，正常

		EnumConst enumConstByFlagElePeriodPayStatus = null;
		if (peBzzOrderInfo.getNum() != null && !"".equals(peBzzOrderInfo.getNum().trim())) {
			enumConstByFlagElePeriodPayStatus = this.getEnumConstService().getByNamespaceCode("FlagElePeriodPayStatus", "2");// 待确认状态
		} else {
			enumConstByFlagElePeriodPayStatus = this.getEnumConstService().getByNamespaceCode("FlagElePeriodPayStatus", "0");// 未支付状态
		}

		/**
		 * 订单有效状态，初始为有效
		 */
		EnumConst enumConstByFlagOrderIsValid = this.enumConstService.getByNamespaceCode("FlagOrderIsValid", "1"); // 订单为有效状态
		EnumConst enumConstByFlagPaymentState = this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "0");// 未支付
		EnumConst enumConstByFlagElectivePayStatus = this.getEnumConstService().getByNamespaceCode("FlagElectivePayStatus", "0");// 未支付状态
		EnumConst enumConstByFlagCheckState = this.getEnumConstService().getByNamespaceCode("FlagCheckState", "0");// 对账状态

		this.peBzzOrderInfo.setEnumConstByFlagOrderIsValid(enumConstByFlagOrderIsValid);
		this.peBzzOrderInfo.setEnumConstByFlagPaymentMethod(enumConstByFlagPaymentMethod);
		this.peBzzOrderInfo.setEnumConstByFlagPaymentType(enumConstByFlagPaymentType);
		this.peBzzOrderInfo.setEnumConstByFlagPaymentState(enumConstByFlagPaymentState);
		this.peBzzOrderInfo.setEnumConstByFlagOrderType(enumConstByFlagOrderType);
		this.peBzzOrderInfo.setEnumConstByFlagOrderState(enumConstByFlagOrderState);
		this.peBzzOrderInfo.setEnumConstByFlagCheckState(enumConstByFlagCheckState);// 订单对账状态
		this.peBzzOrderInfo.setSsoUser(us.getSsoUser());
		this.peBzzOrderInfo.setCreateDate(new Date());
		try {
			this.peBzzOrderInfo.setPayer(this.getGeneralService().getEnterpriseManagerByLoginId(us.getSsoUser().getLoginId()).getName());
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		this.allAmount = new BigDecimal(this.peBzzOrderInfo.getAmount()).setScale(2);
		String eleCouPeriodId = (String) ServletActionContext.getRequest().getSession().getAttribute("eleCouPeriodId");
		DetachedCriteria dcElectiveBack = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
		// dcElective.createCriteria("trainingCourseStudent");
		dcElectiveBack.createCriteria("prBzzTchOpencourse");
		dcElectiveBack.createCriteria("peBzzStudent");
		dcElectiveBack.add(Restrictions.eq("peElectiveCoursePeriod.id", eleCouPeriodId));

		DetachedCriteria dcElectivePeriod = DetachedCriteria.forClass(PeElectiveCoursePeriod.class);
		dcElectivePeriod.add(Restrictions.eq("id", eleCouPeriodId));
		try {
			PeElectiveCoursePeriod peElectiveCoursePeriod = (PeElectiveCoursePeriod) this.getGeneralService().getDetachList(dcElectivePeriod).get(0);
			peElectiveCoursePeriod.setEnumConstByFlagElePeriodPayStatus(enumConstByFlagElePeriodPayStatus);
			this.setBean(peElectiveCoursePeriod);
			this.electiveBackList = this.getGeneralService().getDetachList(dcElectiveBack);
			// for (int i = 0; i < electiveBackList.size(); i++) {
			// electiveBackList.get(i).setPeBzzOrderInfo(this.peBzzOrderInfo);
			// //electiveBackList.get(i).setEnumConstByFlagElectivePayStatus(enumConstByFlagElectivePayStatus);
			// }
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
	}

	/**
	 * 重写框架方法：删除前验证
	 * 
	 * @author linjie
	 * @return
	 */
	public void checkBeforeDelete(List idList) throws EntityException {

		DetachedCriteria dc = DetachedCriteria.forClass(PeElectiveCoursePeriod.class);
		dc.add(Restrictions.in("id", idList.toArray()));
		List list = this.getGeneralService().getList(dc);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			PeElectiveCoursePeriod pc = (PeElectiveCoursePeriod) it.next();
			if (pc.getPeriodStudent().size() != 0) {
				throw new EntityException("您要删除的选课期内存在学员，不能进行删除");
			}
		}

	}

	/**
	 * sysno;//系统编号 username;//用户名称 zige;//资格类型 iszige;//是否有资格 isyx;//是否有效
	 * ydphone;//移动电话 orgid;//机构id idcard;//证件号码
	 * 
	 * @return
	 */
	// 查看学员信息 新 dgh
	public String viewStudentInfo() {
		List mList = new ArrayList();
		mList.add("4028808c1dbe60fe011dbe7866590083");
		mList.add("4028808c1dbe60fe011dbe7866590073");
		mList.add("4028808c1dbe60fe011dbe7866590063");
		mList.add("4028808c1dbe60fe011dbe7866590053");
		mList.add("4028808c1dbe60fe011dbe7866590043");
		mList.add("4028808c1dbe60fe011dbe7866590033");
		mList.add("4028808c1dbe60fe011dbe7866590023");
		mList.add("4028808c1dbe60fe011dbe7866590013");
		mList.add("4028808c1dbe60fe011dbe7866590093");
		DetachedCriteria dcType = DetachedCriteria.forClass(EnumConst.class);
		dcType.add(Restrictions.eq("namespace", "FlagQualificationsType"));
		dcType.add(Restrictions.in("id", mList));
		try {
			this.zigelist = this.getGeneralService().getList(dcType);
			if (null == this.getId() || "".equals(this.getId())) {
				return "";
			}
			String sql = "SELECT PBS.TRUE_NAME,SSO.LOGIN_ID,PBS.ZIGE,E_ZG.NAME,PBS.IS_EMPLOYEE,IS_EMP.NAME AS IS_EMP,PBS.MOBILE_PHONE,PBS.GROUPS,PBS.CARD_TYPE,CASE WHEN K_B.B_TIME IS NULL THEN 0 ELSE  K_B.B_TIME END AS B_TIME,  CASE WHEN K_C.X_TIME IS NULL THEN 0 ELSE  K_C.X_TIME END AS X_TIME, CASE WHEN K_ALL.ALL_TIME IS NULL THEN 0 ELSE  K_ALL.ALL_TIME END AS ALL_TIME"
					+ " ,PBS.CARD_NO,PBS.FK_SSO_USER_ID FROM  PE_BZZ_STUDENT PBS  " + " JOIN  (SELECT PTSE.FK_STU_ID FROM PR_BZZ_TCH_STU_ELECTIVE_BACK PTSE WHERE PTSE.FK_ELE_COURSE_PERIOD_ID='"
					+ this.getId()
					+ "' GROUP BY PTSE.FK_STU_ID) EB_ST ON EB_ST.FK_STU_ID=PBS.ID "
					+ " JOIN ENUM_CONST E_ZG ON E_ZG.ID=PBS.ZIGE "
					+ " JOIN ENUM_CONST IS_EMP ON IS_EMP.ID=PBS.IS_EMPLOYEE "
					+ " JOIN SSO_USER SSO ON PBS.FK_SSO_USER_ID=SSO.ID "
					+ " LEFT JOIN ( "
					+ " SELECT  SUM(C.TIME) AS B_TIME,A.FK_STU_ID "
					+ " FROM PR_BZZ_TCH_STU_ELECTIVE_BACK A "
					+ " INNER JOIN PE_ELECTIVE_COURSE_PERIOD E ON A.FK_ELE_COURSE_PERIOD_ID = E.ID "
					+ " INNER JOIN PR_BZZ_TCH_OPENCOURSE B ON A.FK_TCH_OPENCOURSE_ID = B.ID "
					+ " INNER JOIN PE_BZZ_TCH_COURSE C ON B.FK_COURSE_ID = C.ID "
					+ " INNER JOIN ENUM_CONST D ON C.FLAG_COURSETYPE = D.ID AND D.ID='402880f32200c249012200c7f8b30002'  WHERE A.FK_ELE_COURSE_PERIOD_ID='"
					+ this.getId()
					+ "'"
					+ " GROUP BY A.FK_STU_ID "
					+ " ) K_B  ON K_B.FK_STU_ID=PBS.ID "
					+ " LEFT JOIN ( "
					+ " SELECT  SUM(C.TIME) X_TIME,A.FK_STU_ID "
					+ " FROM PR_BZZ_TCH_STU_ELECTIVE_BACK A "
					+ " INNER JOIN PE_ELECTIVE_COURSE_PERIOD E ON A.FK_ELE_COURSE_PERIOD_ID = E.ID "
					+ " INNER JOIN PR_BZZ_TCH_OPENCOURSE B ON A.FK_TCH_OPENCOURSE_ID = B.ID "
					+ " INNER JOIN PE_BZZ_TCH_COURSE C ON B.FK_COURSE_ID = C.ID "
					+ " INNER JOIN ENUM_CONST D ON C.FLAG_COURSETYPE = D.ID AND D.ID='402880f32200c249012200c780c40001'  WHERE A.FK_ELE_COURSE_PERIOD_ID='"
					+ this.getId()
					+ "'"
					+ " GROUP BY A.FK_STU_ID "
					+ " )K_C ON K_C.FK_STU_ID=PBS.ID "
					+ " LEFT JOIN ( "
					+ " SELECT SUM(C.TIME) ALL_TIME,A.FK_STU_ID "
					+ " FROM PR_BZZ_TCH_STU_ELECTIVE_BACK A  "
					+ " INNER JOIN PE_ELECTIVE_COURSE_PERIOD E ON A.FK_ELE_COURSE_PERIOD_ID = E.ID "
					+ " INNER JOIN PR_BZZ_TCH_OPENCOURSE B ON A.FK_TCH_OPENCOURSE_ID = B.ID " + " INNER JOIN PE_BZZ_TCH_COURSE C ON B.FK_COURSE_ID = C.ID WHERE A.FK_ELE_COURSE_PERIOD_ID='" + this.getId() + "'" + " GROUP BY A.FK_STU_ID " + " )K_ALL ON K_ALL.FK_STU_ID=PBS.ID WHERE 1=1 ";

			if (this.sysno != null && !"".equalsIgnoreCase(this.sysno)) {
				sql += (" and SSO.LOGIN_ID like '%" + this.sysno + "%'\n");
			}
			if (this.username != null && !"".equalsIgnoreCase(this.username)) {
				sql += (" and PBS.TRUE_NAME = '%" + this.username + "%'\n");
			}
			if (this.zige != null && !"".equalsIgnoreCase(this.zige)) {
				sql += (" and PBS.ZIGE = '" + this.zige + "'\n");
			}
			if (this.iszige != null && !"".equalsIgnoreCase(this.iszige)) {
				sql += (" and PBS.IS_EMPLOYEE = '" + this.iszige + "'\n");
			}
			if (this.ydphone != null && !"".equalsIgnoreCase(this.ydphone)) {
				sql += (" and PBS.MOBILE_PHONE = '%" + this.ydphone + "%'\n");
			}
			if (this.orgid != null && !"".equalsIgnoreCase(this.orgid)) {
				sql += (" and PBS.FK_ENTERPRISE_ID = '" + this.orgid + "'\n");
			}
			if (this.idcard != null && !"".equalsIgnoreCase(this.idcard)) {
				sql += (" and PBS.CARD_NO = '%" + this.idcard + "%'\n");
			}
			try {
				this.studentlist = this.getGeneralService().getBySQL(sql);
			} catch (EntityException e) {
				e.printStackTrace();
			}
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		return "viewStudentInfo";
	}

	/**
	 * 查看选课期选课详情
	 * 
	 * @return
	 */
	public String viewDetail() {
		DetachedCriteria dcType = DetachedCriteria.forClass(EnumConst.class);
		dcType.add(Restrictions.eq("namespace", "FlagCourseType"));
		try {
			courseTypeList = this.getGeneralService().getList(dcType);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}

		DetachedCriteria dcElective = DetachedCriteria.forClass(PrBzzTchStuElective.class);
		dcElective.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createCriteria("peBzzTchCourse", "peBzzTchCourse", DetachedCriteria.LEFT_JOIN);
		dcElective.createCriteria("peBzzStudent", "peBzzStudent");
		dcElective.createCriteria("peElectiveCoursePeriod", "peElectiveCoursePeriod").createAlias("peElectiveCoursePeriod.enumConstByFlagElePeriodPayStatus", "enumConstByFlagElePeriodPayStatus", DetachedCriteria.LEFT_JOIN);
		dcElective.add(Restrictions.eq("peElectiveCoursePeriod.id", this.getBean().getId()));
		if (this.getRegNo() != null && !"".equals(this.getRegNo())) {
			dcElective.add(Restrictions.like("peBzzStudent.regNo", "%" + StringUtils.trim(this.getRegNo()) + "%"));
		}
		if (this.getStuName() != null && !"".equals(this.getStuName())) {
			dcElective.add(Restrictions.like("peBzzStudent.trueName", "%" + StringUtils.trim(this.getStuName()) + "%"));
		}
		if (this.getCourseCode() != null && !"".equals(this.getCourseCode())) {
			dcElective.add(Restrictions.like("peBzzTchCourse.code", "%" + StringUtils.trim(this.getCourseCode()) + "%"));
		}
		if (this.getCourseName() != null && !"".equals(this.getCourseName())) {
			dcElective.add(Restrictions.like("peBzzTchCourse.name", "%" + StringUtils.trim(this.getCourseName()) + "%"));
		}
		
		try {
			if (this.getCourseType() != null && !"".equals(this.getCourseType())) {
				dcElective.add(Restrictions.like("peBzzTchCourse.enumConstByFlagCourseType.id", "%" + StringUtils.trim(this.getCourseType()) + "%"));
			}
			this.electiveList = this.getGeneralService().getList(dcElective);
			if (this.electiveList != null && this.electiveList.size() > 0) {
				List tempList = new ArrayList();
				for (int i = 0; i < this.electiveList.size(); i++) {
					if (!tempList.contains(electiveList.get(i).getPeBzzStudent().getId())) {
						tempList.add(electiveList.get(i).getPeBzzStudent().getId());
					}
					EnumConst ec = this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0");
					this.allAmount = this.allAmount.add(new BigDecimal(electiveList.get(i).getPrBzzTchOpencourse().getPeBzzTchCourse().getPrice()));
					this.allTime = this.allTime.add(new BigDecimal(electiveList.get(i).getPrBzzTchOpencourse().getPeBzzTchCourse().getTime()));
					if(electiveList.get(i).getPrBzzTchOpencourse().getPeBzzTchCourse().getEnumConstByFlagCourseType().getCode().equals("0")){
						this.bTimes =this.bTimes.add(new BigDecimal(electiveList.get(i).getPrBzzTchOpencourse().getPeBzzTchCourse().getTime()));
					}else{
				        this.xTimes =this.xTimes.add(new BigDecimal(electiveList.get(i).getPrBzzTchOpencourse().getPeBzzTchCourse().getTime()));	
					}
				}
				this.num = tempList.size();
			} else {
				// 查询该选课期是否产生了订单，如果是，则页面上不允许删除操作了，否则允许
				DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
				dc.createCriteria("peElectiveCoursePeriod", "peElectiveCoursePeriod");
				dc.add(Restrictions.eq("peElectiveCoursePeriod.id", this.getBean().getId()));
				List orderList = this.getGeneralService().getList(dc);
				if (orderList.size() > 0) {
					this.setIsFormedOrder(true);
				} else {
					this.setIsFormedOrder(false);
				}

				DetachedCriteria dcElectiveBack = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
				dcElectiveBack.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createCriteria("peBzzTchCourse", "peBzzTchCourse", DetachedCriteria.LEFT_JOIN);
				dcElectiveBack.createCriteria("peBzzStudent", "peBzzStudent");
				dcElectiveBack.createCriteria("peElectiveCoursePeriod", "peElectiveCoursePeriod").createAlias("peBzzTchCourse.enumConstByFlagCourseType", "enumConstByFlagCourseType", DetachedCriteria.LEFT_JOIN);
				dcElectiveBack.add(Restrictions.eq("peElectiveCoursePeriod.id", this.getBean().getId()));
				if (this.getRegNo() != null && !"".equals(this.getRegNo())) {
					dcElectiveBack.add(Restrictions.like("peBzzStudent.regNo", "%" + StringUtils.trim(this.getRegNo()) + "%"));
				}
				if (this.getStuName() != null && !"".equals(this.getStuName())) {
					dcElectiveBack.add(Restrictions.like("peBzzStudent.trueName", "%" + StringUtils.trim(this.getStuName()) + "%"));
				}
				if (this.getCourseCode() != null && !"".equals(this.getCourseCode())) {
					dcElectiveBack.add(Restrictions.like("peBzzTchCourse.code", "%" + StringUtils.trim(this.getCourseCode()) + "%"));
				}
				if (this.getCourseName() != null && !"".equals(this.getCourseName())) {
					dcElectiveBack.add(Restrictions.like("peBzzTchCourse.name", "%" + StringUtils.trim(this.getCourseName()) + "%"));
				}
				if (this.getCourseType() != null && !"".equals(this.getCourseType())) {
					dcElectiveBack.add(Restrictions.like("enumConstByFlagCourseType.id", "%" + StringUtils.trim(this.getCourseType()) + "%"));
				}
				this.electiveBackList = this.getGeneralService().getList(dcElectiveBack);
				if (this.electiveBackList != null) {
					List tempList = new ArrayList();
					for (int i = 0; i < this.electiveBackList.size(); i++) {
						if (!tempList.contains(electiveBackList.get(i).getPeBzzStudent().getId())) {
							tempList.add(electiveBackList.get(i).getPeBzzStudent().getId());
						}
						this.allAmount = this.allAmount.add(new BigDecimal(electiveBackList.get(i).getPrBzzTchOpencourse().getPeBzzTchCourse().getPrice()));
						this.allTime = this.allTime.add(new BigDecimal(electiveBackList.get(i).getPrBzzTchOpencourse().getPeBzzTchCourse().getTime()));
						if(electiveBackList.get(i).getPrBzzTchOpencourse().getPeBzzTchCourse().getEnumConstByFlagCourseType().getCode().equals("0")){
							this.bTimes =this.bTimes.add(new BigDecimal(electiveBackList.get(i).getPrBzzTchOpencourse().getPeBzzTchCourse().getTime()));
						}else{
					        this.xTimes =this.xTimes.add(new BigDecimal(electiveBackList.get(i).getPrBzzTchOpencourse().getPeBzzTchCourse().getTime()));	
						}
					}
					this.num = tempList.size();
				}
			}

		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.allAmount = this.allAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		this.allTime = this.allTime.setScale(1, BigDecimal.ROUND_HALF_UP);
		this.bTimes = this.bTimes.setScale(1, BigDecimal.ROUND_HALF_UP);
		this.xTimes = this.xTimes.setScale(1, BigDecimal.ROUND_HALF_UP);
		return "electiveCoursePeriodDetail";
	}

	/**
	 * 删除选课
	 * 
	 * @author linjie
	 * @return
	 */
	public String delElective() {
		String trainingId = "";
		String eleSql = "select ele.FK_TRAINING_ID from pr_bzz_tch_stu_elective ele where ele.id = '" + this.id + "'";
		List eleList = null;
		try {
			eleList = this.getGeneralService().getBySQL(eleSql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if (eleList.size() > 0) {
			trainingId = eleList.get(0).toString();
			String eleDel = "delete from pr_bzz_tch_stu_elective ele where ele.id = '" + this.id + "'";
			try {
				this.getGeneralService().executeBySQL(eleDel);
			} catch (EntityException e) {
				e.printStackTrace();
				// TODO Auto-generated catch block
				this.setMsg("删除失败");
				this.setTogo("/entity/basic/peElectiveCoursePeriod.action");
				return "msg";
			}

			String trainingDel = "delete from training_course_student tcs where tcs.id = '" + trainingId + "'";
			try {
				this.getGeneralService().executeBySQL(trainingDel);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				this.setMsg("删除失败");
				this.setTogo("/entity/basic/peElectiveCoursePeriod.action");
				return "msg";
			}
		}
		return this.viewDetail();
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeElectiveCoursePeriod.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peElectiveCoursePeriod";
	}

	public void setBean(PeElectiveCoursePeriod instance) {
		super.superSetBean(instance);

	}

	public PeElectiveCoursePeriod getBean() {
		return (PeElectiveCoursePeriod) super.superGetBean();
	}

	public List<PrBzzTchStuElective> getElectiveList() {
		return electiveList;
	}

	public void setElectiveList(List<PrBzzTchStuElective> electiveList) {
		this.electiveList = electiveList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public BigDecimal getAllTime() {
		return allTime;
	}

	public void setAllTime(BigDecimal allTime) {
		this.allTime = allTime;
	}

	public BigDecimal getAllAmount() {
		return allAmount;
	}

	public void setAllAmount(BigDecimal allAmount) {
		this.allAmount = allAmount;
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

	public String getIsInvoice() {
		return isInvoice;
	}

	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getSso_user_amount() {
		return sso_user_amount;
	}

	public void setSso_user_amount(String sso_user_amount) {
		this.sso_user_amount = sso_user_amount;
	}

	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	/**
	 * 获得当前用户账户信息
	 * 
	 * @author linjie
	 * @return
	 */
	public BigDecimal balance(SsoUser user) {
		DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
		dc.add(Restrictions.eq("id", user.getId()));
		try {
			user = (SsoUser) this.getGeneralService().getList(dc).get(0);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String sumAmount = (user.getSumAmount() == null || user.getSumAmount().equals("")) ? "0" : user.getSumAmount();
		String amount = (user.getAmount() == null || user.getAmount().equals("")) ? "0" : user.getAmount();
		BigDecimal bdSumAmount = new BigDecimal(sumAmount);
		BigDecimal bdAmount = new BigDecimal(amount);
		return bdSumAmount.subtract(bdAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 账户资金是否充足
	 * 
	 * @author linjie
	 * @return
	 */
	public boolean isEnough(SsoUser user, BigDecimal allAmount) {
		try {
			// 为了得到最新的账户金额信息所以重新查1次
			DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
			dc.add(Restrictions.eq("id", user.getId()));
			List list = this.getGeneralService().getDetachList(dc);
			if (null == list || list.size() < 1) {
				return false;
			}
			SsoUser ssoUser = (SsoUser) list.get(0);
			EnumConst ec = this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0");
			BigDecimal price = new BigDecimal(ec.getName());
			String sumAmount = (ssoUser.getSumAmount() == null || ssoUser.getSumAmount().equals("")) ? "0" : ssoUser.getSumAmount();
			String amount = (ssoUser.getAmount() == null || ssoUser.getAmount().equals("")) ? "0" : ssoUser.getAmount();
			BigDecimal bdSumAmount = new BigDecimal(sumAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal bdAmount = new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal subAmount = bdSumAmount.subtract(bdAmount).multiply(price).setScale(2, BigDecimal.ROUND_HALF_UP);
			int result = subAmount.compareTo(allAmount);
			if (result >= 0) {
				return false;
			} else {
				return true;
			}
		} catch (EntityException e) {
			return false;
		}

	}

	public boolean getIsFormedOrder() {
		return isFormedOrder;
	}

	public void setIsFormedOrder(boolean isFormedOrder) {
		this.isFormedOrder = isFormedOrder;
	}

	/**
	 * 删除pr_bzz_tch_stu_elective_back信息
	 * 
	 * @author linjie
	 * @return
	 */
	public String deleteElectiveCoursePeriodBack() {
		String eleDel = "delete from pr_bzz_tch_stu_elective_back ele where ele.id = '" + this.getId() + "'";
		try {
			this.getGeneralService().executeBySQL(eleDel);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return this.viewDetail();
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getSysno() {
		return sysno;
	}

	public void setSysno(String sysno) {
		this.sysno = sysno;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getZige() {
		return zige;
	}

	public void setZige(String zige) {
		this.zige = zige;
	}

	public String getIszige() {
		return iszige;
	}

	public void setIszige(String iszige) {
		this.iszige = iszige;
	}

	public String getIsyx() {
		return isyx;
	}

	public void setIsyx(String isyx) {
		this.isyx = isyx;
	}

	public String getYdphone() {
		return ydphone;
	}

	public void setYdphone(String ydphone) {
		this.ydphone = ydphone;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public List getStudentlist() {
		return studentlist;
	}

	public void setStudentlist(List studentlist) {
		this.studentlist = studentlist;
	}

	public String getDelIds() {
		return delIds;
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}

	public String getStuid() {
		return stuid;
	}

	public void setStuid(String stuid) {
		this.stuid = stuid;
	}

	public List getZigelist() {
		return zigelist;
	}

	public void setZigelist(List zigelist) {
		this.zigelist = zigelist;
	}

	public List getCourseTypeList() {
		return courseTypeList;
	}

	public void setCourseTypeList(List courseTypeList) {
		this.courseTypeList = courseTypeList;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public BigDecimal getBTimes() {
		return bTimes;
	}

	public void setBTimes(BigDecimal times) {
		bTimes = times;
	}

	public BigDecimal getXTimes() {
		return xTimes;
	}

	public void setXTimes(BigDecimal times) {
		xTimes = times;
	}
}
