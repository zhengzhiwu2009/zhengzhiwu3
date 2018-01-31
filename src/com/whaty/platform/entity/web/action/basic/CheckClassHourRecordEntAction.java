package com.whaty.platform.entity.web.action.basic;

import org.apache.struts2.ServletActionContext;

import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class CheckClassHourRecordEntAction extends MyBaseAction {
	private String flag;
	private String detail = ""; // 用于标识是剥离还是分配
	private String id;
	private String userId;

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
		// TODO Auto-generated method stub
		this.getGridConfig().setCapability(false, false, false);
		if (flag.equals("old")) {
			// Lee 原版
			// this.getGridConfig().setTitle(this.getText("消费记录"));
			// Lee end
			// Lee 2013年12月27日
			this.getGridConfig().setTitle(this.getText("分配记录"));
			// Lee end
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("分配时间"), "assignDate",
					true);
			this.getGridConfig().addColumn(this.getText("已分配学时"), "totalNum",
					false);
			this.getGridConfig().addColumn(this.getText("分配管理员数"), "totalPem",
					false);
			this
					.getGridConfig()
					.addRenderFunction(
							"操作",
							"<a href='/entity/basic/checkClassHourRecordEnt_viewDetail.action?id=${value}&flag=detail' >查看详情</a>",
							"id");
		} else if (flag.equals("detail")) {

			if (detail.equals("strip")) {
				this.getGridConfig().setTitle(this.getText("详细剥离记录"));
			} else {
				this.getGridConfig().setTitle(this.getText("详细分配记录"));
			}
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("登陆ID"), "loginId",
					true);
			this.getGridConfig()
					.addColumn(this.getText("管理员姓名"), "pName", true);
			this.getGridConfig()
					.addColumn(this.getText("机构名称"), "peName", true);
			if (detail.equals("strip")) {
				this.getGridConfig().addColumn(this.getText("剥离学时数"), "num",
						false);
			} else {
				this.getGridConfig().addColumn(this.getText("分配学时数"), "num",
						false);
			}

		} else if (flag.equals("strip")) {
			// Lee 原版
			// this.getGridConfig().setTitle(this.getText("消费记录"));
			// Lee end
			// Lee 2013年12月27日
			this.getGridConfig().setTitle(this.getText("分配记录"));
			// Lee end
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("剥离时间"), "assignDate",
					true);
			this.getGridConfig().addColumn(this.getText("剥离学时数"), "totalNum",
					false);
			this.getGridConfig().addColumn(this.getText("剥离集体管理员数"),
					"totalStu", false);
			this
					.getGridConfig()
					.addRenderFunction(
							"操作",
							"<a href='/entity/basic/checkClassHourRecordEnt_viewDetail.action?id=${value}&flag=detail&detail=strip' >查看详情</a>",
							"id");
		}
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/checkClassHourRecordEnt";
	}

	/**
	 * 重写框架方法：学时记录信息（带sql条件）
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public Page list() {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if ((this.userId == null || "".equalsIgnoreCase(this.userId))
				&& !"3".equalsIgnoreCase(us.getRoleId())) {
			this.userId = us.getLoginId();
		}
		String sql = " select * from ( ";
		if (flag.equals("old")) {
			sql += "select ars.id as id,\n"
					+ "       ars.assign_date as assignDate,\n"
					+ "       sum(TO_NUMBER(s.class_num)) as totalNum,\n"
					+ "       count(s.fk_enterprise_manager_id) as totalPem\n"
					+ "  from assign_record_student s, assign_record ars, enum_const ec\n"
					+ " where s.fk_record_id = ars.id\n"
					+ "   and s.fk_student_id is null\n"
					+ "   and ec.namespace = 'FlagRecordAssignMethod'\n"
					+ "   and ec.code = '1'\n"
					+ "   and ec.id = ars.flag_record_assign_method\n"
					+ "   and s.fk_record_id in\n"
					+ "       (select ar.id\n"
					+ "          from assign_record ar\n"
					+ "         where ar.fk_user_id =\n"
					+ "               (select u.id from sso_user u where u.login_id = '"
					+ us.getLoginId() + "'))\n"
					+ " group by ars.assign_date, ars.id";

		} else if (flag.equals("detail")) {
			sql += "select s.id        as id,\n"
					+ "       p.login_id  as loginId,\n"
					+ "       p.name      as pName,\n"
					+ "       pe.name     as peName,\n"
					+ "       s.class_num as num\n"
					+ "  from pe_enterprise_manager p, assign_record_student s, pe_enterprise pe\n"
					+ " where s.fk_enterprise_manager_id = p.id\n"
					+ "   and p.fk_enterprise_id = pe.id\n"
					+ "   and s.fk_record_id = '" + id + "'";

		} else if (flag.equals("strip")) {
			sql += "select ars.id as id,\n"
					+ "       ars.assign_date as assignDate,\n"
					+ "       sum(TO_NUMBER(s.class_num)) as totalNum,\n"
					+ "       count(s.fk_enterprise_manager_id) as totalPem\n"
					+ "  from assign_record_student s, assign_record ars, enum_const ec\n"
					+ " where s.fk_record_id = ars.id\n"
					+ "   and s.fk_student_id is null\n"
					+ "   and ec.namespace = 'FlagRecordAssignMethod'\n"
					+ "   and ec.code = '0'\n"
					+ "   and ec.id = ars.flag_record_assign_method\n"
					+ "   and s.fk_record_id in\n"
					+ "       (select ar.id\n"
					+ "          from assign_record ar\n"
					+ "         where ar.fk_user_id =\n"
					+ "               (select u.id from sso_user u where u.login_id = '"
					+ us.getLoginId() + "'))\n"
					+ " group by ars.assign_date, ars.id";
		}
		sql += " ) where 1=1 ";

		Page page = null;
		try {
			StringBuffer sqlBuff = new StringBuffer(sql);
			this.setSqlCondition(sqlBuff);
			page = getGeneralService().getByPageSQL(sqlBuff.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return page;
	}

	/**
	 * 查看详情
	 * 
	 * @author linjie
	 * @return
	 */
	public String viewDetail() {
		String id = ServletActionContext.getRequest().getParameter("id");
		//System.out.println("*****************************id=" + id);
		initGrid();
		return "grid";
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
