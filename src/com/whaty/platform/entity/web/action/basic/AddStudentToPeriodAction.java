package com.whaty.platform.entity.web.action.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.CoursePeriodStudent;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeElectiveCoursePeriod;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class AddStudentToPeriodAction extends MyBaseAction<CoursePeriodStudent> {

	private String id;
	private String flag;

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

	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("学员列表"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("系统编号"), "regNo", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("具有从业资格"), "enumConstByIsEmployee.name", true, false, true, "TextField", false, 100, true);
		ColumnConfig c_name = new ColumnConfig(this.getText("资格类型"), "enumConstByFlagQualificationsType.name");
		c_name.setAdd(true);
		c_name.setSearch(true);
		c_name.setList(true);
		c_name.setComboSQL("SELECT * FROM ENUM_CONST t WHERE NAMESPACE = 'FlagQualificationsType' and t.code not in('9','90','91')");
		this.getGridConfig().addColumn(c_name);
		this.getGridConfig().addColumn(this.getText("从事业务"), "work", true, false, true, "TextField", true, 100);
		this.getGridConfig().addColumn(this.getText("职务"), "position", true, false, true, "TextField", true, 100);
		this.getGridConfig().addColumn(this.getText("是否有效"), "ssoUser.enumConstByFlagIsvalid.name", false, false, false, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("移动电话"), "mobilePhone", true);
		ColumnConfig c_name1 = new ColumnConfig(this.getText("所在机构"), "peEnterprise.name", true, false, true, "TextField", true, 50, "");
		String sql = "select t.id, t.name as p_name\n" + "  from pe_enterprise t\n" + " where t.fk_parent_id in\n" + "       (select s.id\n" + "          from pe_enterprise s\n"
				+ "         inner join pe_enterprise_manager t\n" + "            on s.id = t.fk_enterprise_id\n" + "           and t.login_id = '" + us.getLoginId() + "')\n"
				+ "    or t.id in (select s.id\n" + "                  from pe_enterprise s\n" + "                 inner join pe_enterprise_manager t\n"
				+ "                    on s.id = t.fk_enterprise_id\n" + "                   and t.login_id = '" + us.getLoginId() + "')\n"
				+ " order by nlssort(t.name,'NLS_SORT=SCHINESE_PINYIN_M')";
		c_name1.setComboSQL(sql);
		this.getGridConfig().addColumn(c_name1);
		this.getGridConfig().addColumn(this.getText("国籍"), "cardType", false, false, true, "TextField", true, 100);
		this.getGridConfig().addColumn(this.getText("证件号码"), "cardNo", true, false, true, "TextField", true, 100);
		// 工作部门
		this.getGridConfig().addColumn(this.getText("工作部门"), "department", true, true, true, "TextField", true, 50);
		// 组别
		this.getGridConfig().addColumn(this.getText("组别"), "groups", true, true, true, "TextField", true, 50);
		if (flag.equalsIgnoreCase("stuAdd")) {
			this.getGridConfig().addMenuFunction(this.getText("加入选课期"), "StuAdd");
		} else if (flag.equalsIgnoreCase("stuDel")) {
			this.getGridConfig().addMenuFunction(this.getText("从选课期删除"), "StuDel");
		}
		this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back()}");
		ActionContext.getContext().getSession().put("id", id);
	}

	@Override
	public void setEntityClass() {
		this.entityClass = CoursePeriodStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/addStudentToPeriod";
	}

	public DetachedCriteria initDetachedCriteria() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = null;
		DetachedCriteria expcetdc = null;
		expcetdc = DetachedCriteria.forClass(CoursePeriodStudent.class);
		expcetdc.setProjection(Projections.distinct(Property.forName("peBzzStudent.id")));
		expcetdc.add(Restrictions.eq("peElectiveCoursePeriod.id", id));
		expcetdc.createCriteria("peBzzStudent", "peBzzStudent");
		expcetdc.createCriteria("peElectiveCoursePeriod", "peElectiveCoursePeriod");

		String sql = " select pe.id from pe_enterprise pe inner join pe_enterprise_manager pem on pem.fk_enterprise_id = pe.id " + " where pem.login_id = '" + us.getLoginId()
				+ "' union select pe.id\n" + " from pe_enterprise pe, pe_enterprise pePar, pe_enterprise_manager pem\n"
				+ " where pe.fk_parent_id = pePar.Id  and pePar.Id = pem.fk_enterprise_id  and pem.login_id = '" + us.getLoginId() + "'";

		List list = null;
		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		// 未知操作 先注释 20140624
		// String peIds[] = new String[list.size()];
		// for(int i=0; i<list.size(); i++) {
		// peIds[i] = list.get(i).toString();
		// }

		dc = DetachedCriteria.forClass(PeBzzStudent.class);
		dc.createCriteria("peEnterprise", "peEnterprise");
		dc.createCriteria("enumConstByIsEmployee", "enumConstByIsEmployee", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagQualificationsType", "enumConstByFlagQualificationsType", DetachedCriteria.LEFT_JOIN);

		dc.createCriteria("ssoUser", "ssoUser");
		dc.createCriteria("ssoUser.enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		dc.add(Restrictions.in("peEnterprise.id", list.toArray(new String[list.size()])));
		dc.add(Restrictions.eq("ssoUser.enumConstByFlagIsvalid.id", "2"));
		if (flag.equalsIgnoreCase("stuAdd")) {
			dc.add(Subqueries.propertyNotIn("id", expcetdc));
		} else if (flag.equalsIgnoreCase("stuDel")) {
			dc.add(Subqueries.propertyIn("id", expcetdc));
		}
		return dc;
	}

	@SuppressWarnings({ "unchecked", "unchecked", "unchecked" })
	public Map updateColumn() {
		Map map = new HashMap();
		String action = this.getColumn();
		String id = ActionContext.getContext().getSession().get("id").toString().trim();
		try {
			DetachedCriteria dc1 = DetachedCriteria.forClass(PeBzzOrderInfo.class);
			dc1.createCriteria("peElectiveCoursePeriod", "peElectiveCoursePeriod", dc1.INNER_JOIN);
			dc1.createCriteria("enumConstByFlagPaymentState", "enumConstByFlagPaymentState", dc1.INNER_JOIN);
			dc1.createCriteria("enumConstByFlagOrderIsValid", "enumConstByFlagOrderIsValid");
			dc1.add(Restrictions.ne("enumConstByFlagOrderIsValid.code", "0"));
			dc1.add(Restrictions.eq("peElectiveCoursePeriod.id", id));
			List orderList = this.getGeneralService().getList(dc1);
			if (orderList != null && orderList.size() > 0) {
				PeBzzOrderInfo orderTemp = (PeBzzOrderInfo) orderList.get(0);
				if (orderTemp != null && orderTemp.getEnumConstByFlagPaymentState() != null && "1".equals(orderTemp.getEnumConstByFlagPaymentState().getCode())) {
				} else {
					map.clear();
					map.put("success", "false");
					map.put("info", "选课期存在订单，无法添加或删除学员");
					return map;
				}
			}
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PeElectiveCoursePeriod.class);
		dc.createAlias("enumConstByFlagElePeriodPayStatus", "enumConstByFlagElePeriodPayStatus", DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.eq("id", id));
		PeElectiveCoursePeriod peElectiveCoursePeriod = null;
		try {
			peElectiveCoursePeriod = (PeElectiveCoursePeriod) this.getGeneralService().getList(dc).get(0);
			EnumConst enumConst = peElectiveCoursePeriod.getEnumConstByFlagElePeriodPayStatus();
			if (enumConst != null && "1".equalsIgnoreCase(enumConst.getCode())) {
				map.clear();
				map.put("success", "false");
				map.put("info", "该选课期已支付，无法添加或删除学员");
				return map;
			}
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			String msg = "";
			try {
				if (action.equals("StuAdd")) {
					boolean ff = false;
					if (ids == null || ids.length > 1000) {
						ff = true;
					} else {
						DetachedCriteria psDc = DetachedCriteria.forClass(CoursePeriodStudent.class);
						psDc.add(Restrictions.eq("peElectiveCoursePeriod.id", id));
						List cList = this.getGeneralService().getList(psDc);
						if (ids.length + cList.size() > 1000) {
							ff = true;
						}
					}
					if (ff) {
						msg = "每个选课期至多可添加1000名学员！";
						map.clear();
						map.put("success", "false");
						map.put("info", "每个选课期至多可添加1000名学员");
						return map;
					}
					DetachedCriteria studentDc = DetachedCriteria.forClass(PeBzzStudent.class);
					studentDc.add(Restrictions.in("id", ids));
					List<PeBzzStudent> studentList = this.getGeneralService().getList(studentDc);
					for (PeBzzStudent student : studentList) {
						CoursePeriodStudent ps = new CoursePeriodStudent();
						ps.setPeBzzStudent(student);
						ps.setPeElectiveCoursePeriod(peElectiveCoursePeriod);
						this.getGeneralService().save(ps);
					}
					msg = "加入选课期";
				} else if (action.equals("StuDel")) {
					/* 查询是否已经生成订单 是的话 不允许删除 */
					String sql = "SELECT 1 FROM PE_BZZ_ORDER_INFO PBOI WHERE PBOI.FK_COURSE_PERIOD_ID = '" + id + "' and pboi.flag_order_isvalid <> '40288acf3b0823db013b0827f02a0001'";
					List list = this.getGeneralService().getBySQL(sql);
					if (null != list && list.size() > 0) {
						map.clear();
						map.put("success", "false");
						map.put("info", "选课期已生成订单，不能删除学员");
						return map;
					}
					DetachedCriteria delDc = DetachedCriteria.forClass(CoursePeriodStudent.class);
					delDc.add(Restrictions.eq("peElectiveCoursePeriod.id", id));
					delDc.add(Restrictions.in("peBzzStudent.id", ids));
					delDc.setProjection(Projections.distinct(Projections.property("id")));
					List listIds = this.getGeneralService().getList(delDc);
					this.getGeneralService().deleteByIds(listIds);
					// 从elective_back表中也得删除
					DetachedCriteria backDc = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
					backDc.createCriteria("peElectiveCoursePeriod", "peElectiveCoursePeriod");
					backDc.createCriteria("peBzzStudent", "peBzzStudent");
					backDc.add(Restrictions.in("peBzzStudent.id", ids));
					backDc.add(Restrictions.eq("peElectiveCoursePeriod.id", id));
					backDc.setProjection(Projections.distinct(Projections.property("id")));
					List backIds = this.getGeneralService().getList(backDc);
					this.getGeneralService().getGeneralDao().deleteByIds(PrBzzTchStuElectiveBack.class, backIds);
					msg = "从选课期删除";
				}
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				return map;
			}
			map.put("success", "true");
			map.put("info", ids.length + "条记录" + msg + "操作成功");
		} else {
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
			return map;
		}

		return map;
	}

}
