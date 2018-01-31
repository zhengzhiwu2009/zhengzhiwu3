package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class SignStudentCourseInternalAction extends MyBaseAction {

	private String signUpId;

	private int totalCount; // 总条数
	private int startIndex = 0; // 开始数
	private int pageSize = 10; // 页面显示数
	private Page page;

	private String typeflag;
	private List stucoulist;

	private String courseId;

	@Override
	public void initGrid() {
		if (null == this.typeflag || "".equals(this.typeflag))
			this.typeflag = ServletActionContext.getRequest().getSession().getAttribute("typeflagstime") + "";
		// String s=ServletActionContext.getRequest().getParameter("signUpId");
		if ("addstu".equals(this.typeflag)) {

			this.getGridConfig().setTitle("添加学员");
			this.getGridConfig().setCapability(false, false, false, true, false);
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("系统编号"), "REG_NO", true, false, true, "TextField", false, 100, true);
			this.getGridConfig().addColumn(this.getText("姓名"), "TRUE_NAME", true, false, true, "TextField", false, 100);
			this.getGridConfig().addColumn(this.getText("证件号码"), "CARD_NO", true, false, true, "TextField", false, 100);
			ColumnConfig xkstatus = new ColumnConfig(this.getText("资格类型"), "Combobox_ZIGE_NAME", true, false, true, "TextField", false, 100, "");
			String sql7 = "select a.id ,a.name from enum_const a where a.namespace='FlagSuggest'";
			xkstatus.setComboSQL(sql7);
			this.getGridConfig().addColumn(xkstatus);
			this.getGridConfig().addColumn(this.getText("从事业务"), "work", true, false, true, "TextField", false, 100);
			this.getGridConfig().addColumn(this.getText("职务"), "position", true, false, true, "TextField", false, 100);
			this.getGridConfig().addColumn(this.getText("所在部门"), "DEPARTMENT", true, false, true, "TextField", false, 100);
			this.getGridConfig().addMenuFunction(this.getText("加入内训报名"), "addStudents_" + this.getCourseId());// 加入内训时参数传递有问题，在名称中混入课程ID
			this.getGridConfig().addMenuScript(this.getText("返回"), "{window.location='/entity/basic/applyInternal.action'}");
		} else if ("viewstu".equals(this.typeflag)) {
			this.getGridConfig().setTitle("查看学员");
			this.getGridConfig().setCapability(false, false, false, true, false);
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("系统编号"), "REG_NO", true, false, true, "TextField", false, 100, true);
			this.getGridConfig().addColumn(this.getText("姓名"), "TRUE_NAME", true, false, true, "TextField", false, 100);
			this.getGridConfig().addColumn(this.getText("证件号码"), "CARD_NO", true, false, true, "TextField", false, 100);

			ColumnConfig xkstatus = new ColumnConfig(this.getText("资格类型"), "Combobox_ZIGE_NAME", true, false, true, "TextField", false, 100, "");
			String sql7 = "select a.id ,a.name from enum_const a where a.namespace='FlagSuggest'";
			xkstatus.setComboSQL(sql7);
			this.getGridConfig().addColumn(xkstatus);
			this.getGridConfig().addColumn(this.getText("从事业务"), "work", true, false, true, "TextField", false, 100);
			this.getGridConfig().addColumn(this.getText("职务"), "position", true, false, true, "TextField", false, 100);
			this.getGridConfig().addColumn(this.getText("所在部门"), "DEPARTMENT", true, false, true, "TextField", false, 100);

			String sql = "select * from pe_bzz_tch_course pe where pe.flag_offline='22' and pe.id='" + this.getCourseId() + "'";
			try {
				List list = this.getGeneralService().getBySQL(sql);
				if (!(list.size() > 0 && list != null)) {

				} else {
					this.getGridConfig().addMenuFunction(this.getText("取消内训报名"), "delStudents_" + this.getCourseId());// 加入内训时参数传递有问题，在名称中混入课程ID
				}
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this.getGridConfig().addMenuScript(this.getText("返回"), "{window.location='/entity/basic/applyInternal.action'}");
		}

	}

	@Override
	public void setEntityClass() {
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/signStudentCourseInternal";
	}

	public Page list() {
		// ServletActionContext.getRequest().setAttribute("signUpId",
		// ServletActionContext.getRequest().getParameter("signUpId"));
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sql = "";
		// 获取登陆集体账号的机构ID
		String enterpriseIdString = us.getPriEnterprises().get(0).getId();
		// 查询本机构及本机构下属机构ID集合
		String sql_enterprises = "SELECT ID FROM PE_ENTERPRISE WHERE ID = '" + enterpriseIdString + "' OR FK_PARENT_ID = '" + enterpriseIdString + "'";
		if ("addstu".equals(this.typeflag)) {
			sql = "SELECT * FROM (SELECT PBS_ID.ID,PBS_ID.REG_NO,PBS_ID.TRUE_NAME,PBS_ID.CARD_NO,EC1.NAME AS Combobox_ZIGE_NAME, PBS_ID.WORK, PBS_ID.POSITION, PBS_ID.DEPARTMENT "
					+ " FROM PE_BZZ_STUDENT PBS_ID "
					+ " JOIN PE_ENTERPRISE PEM ON PEM.ID = PBS_ID.FK_ENTERPRISE_ID "
					+ " JOIN SSO_USER SU ON PBS_ID.FK_SSO_USER_ID = SU.ID "// 关联用户表
					+ " JOIN ENUM_CONST EC ON SU.FLAG_ISVALID = EC.ID AND EC.CODE = '1'" // 用户为有效状态
					+ " LEFT JOIN ENUM_CONST EC1 ON EC1.ID = PBS_ID.ZIGE " + " WHERE PEM.ID IN (" + sql_enterprises + ")" + " AND PBS_ID.ID NOT IN ( "
					+ " SELECT   pbt.fk_stu_id  FROM STU_INTERNAL pbt " + " join PR_BZZ_TCH_OPENCOURSE pbto on pbt.fk_tch_opencourse_id=pbto.id and pbto.Fk_Course_Id='"
					+ this.getCourseId() + "'" + " )) WHERE 1=1";

		} else if ("viewstu".equals(this.typeflag)) {
			sql = "SELECT * FROM (SELECT PBS_ID.ID,PBS_ID.REG_NO,PBS_ID.TRUE_NAME,PBS_ID.CARD_NO,EC1.NAME AS Combobox_ZIGE_NAME, PBS_ID.WORK, PBS_ID.POSITION, PBS_ID.DEPARTMENT "
					+ " FROM PE_BZZ_STUDENT PBS_ID "
					+ " JOIN PE_ENTERPRISE PEM ON PEM.ID = PBS_ID.FK_ENTERPRISE_ID "
					+ " JOIN SSO_USER SU ON PBS_ID.FK_SSO_USER_ID = SU.ID "// 关联用户表
					+ " JOIN ENUM_CONST EC ON SU.FLAG_ISVALID = EC.ID AND EC.CODE = '1'" // 用户为有效状态
					+ " LEFT JOIN ENUM_CONST EC1 ON EC1.ID = PBS_ID.ZIGE " + " WHERE PEM.ID IN (" + sql_enterprises + ")" + " AND PBS_ID.ID  IN ( "
					+ " SELECT   pbt.fk_stu_id  FROM STU_INTERNAL pbt " + " join PR_BZZ_TCH_OPENCOURSE pbto on pbt.fk_tch_opencourse_id=pbto.id and pbto.Fk_Course_Id='"
					+ this.getCourseId() + "'" + " )) WHERE 1=1";
		}
		try {
			ActionContext context = ActionContext.getContext();
			if (context.getParameters() != null) {
				Map params = this.getParamsMap();
				if (params != null) {
					context.setParameters(params);
				}
			}
			StringBuffer sqlBuffer = new StringBuffer(sql);
			this.setSqlCondition(sqlBuffer);
			this.page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return page;
	}

	public String getSignUpId() {
		return signUpId;
	}

	public void setSignUpId(String signUpId) {
		this.signUpId = signUpId;
	}

	public String getTypeflag() {
		return typeflag;
	}

	public void setTypeflag(String typeflag) {
		this.typeflag = typeflag;
	}

	public List getStucoulist() {
		return stucoulist;
	}

	public void setStucoulist(List stucoulist) {
		this.stucoulist = stucoulist;
	}

	@SuppressWarnings({ "unchecked", "unchecked", "unchecked" })
	public Map updateColumn() {
		Map map = new HashMap();
		String msg = "";
		String action = this.getColumn();

		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			List idList = new ArrayList();
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			try {
				if (action.startsWith("addStudents_")) {
					this.signUpId = action.substring(action.indexOf("_") + 1);
					String sql = "select p.id from PR_BZZ_TCH_OPENCOURSE p where p.fk_course_id='" + signUpId + "'";
					List list = new ArrayList();
					try {
						list = this.getGeneralService().getBySQL(sql);
					} catch (EntityException e1) {
						e1.printStackTrace();
					}
					String opencourse_id = "";
					if (list == null && list.size() <= 0) {
						map.clear();
						map.put("success", "false");
						map.put("info", msg + "操作失败");
						return map;
					}
					opencourse_id = list.get(0).toString();
					if (!"".equals(opencourse_id)) {
						for (Object stu_id : idList) {
							String sql1 = "SELECT ID FROM STU_INTERNAL WHERE FK_TCH_OPENCOURSE_ID = '" + opencourse_id + "' AND FK_STU_ID = '" + stu_id + "'";
							List resList = this.getGeneralService().getBySQL(sql1);
							if (null == resList || resList.size() == 0) {
								String sqlString = "INSERT INTO STU_INTERNAL (ID,FK_TCH_OPENCOURSE_ID , FK_STU_ID) VALUES (SEQ_STU_INTERNAL.NEXTVAL,'" + opencourse_id + "','"
										+ stu_id + "')";
								this.getGeneralService().executeBySQL(sqlString);
							}
						}
					}
					map.clear();
					map.put("success", "true");
					map.put("info", msg + "操作成功");
					// this.setTogo("");
					return map;
				}
				if (action.startsWith("delStudents_")) {
					this.signUpId = action.substring(action.indexOf("_") + 1);
					String sql = "select p.id from PR_BZZ_TCH_OPENCOURSE p where p.fk_course_id='" + signUpId + "'";
					List list = new ArrayList();
					try {
						list = this.getGeneralService().getBySQL(sql);
					} catch (EntityException e1) {
						e1.printStackTrace();
					}
					String opencourse_id = "";
					if (list == null && list.size() <= 0) {
						map.clear();
						map.put("success", "false");
						map.put("info", msg + "操作失败");
						return map;
					}
					opencourse_id = list.get(0).toString();

					if (!"".equals(opencourse_id)) {
						String tsql = "";
						for (Object stu_id : idList) {
							String xsql = "select fk_sso_user_id from pe_bzz_student where  id='" + stu_id + "'";
							List xlist = this.getGeneralService().getBySQL(xsql);
							if (xlist == null && xlist.size() <= 0) {
								tsql = "select * from training_course_student where STUDENT_ID='" + stu_id + "'  and COURSE_ID='" + opencourse_id + "'";
							} else {

								tsql = "select * from training_course_student where STUDENT_ID='" + xlist.get(0).toString() + "'  and COURSE_ID='" + opencourse_id + "'";
							}

							String psql = "select * from PR_BZZ_TCH_STU_ELECTIVE  where FK_STU_ID='" + stu_id + "'  and FK_TCH_OPENCOURSE_ID='" + opencourse_id + "'";
							List tlist = this.getGeneralService().getBySQL(tsql);
							List plist = this.getGeneralService().getBySQL(psql);
							if (plist != null && plist.size() > 0 && tlist != null && tlist.size() > 0) {
								map.clear();
								map.put("success", "false");
								map.put("info", "选中学员已开始学习，不能进行删除操作");
								return map;

							}
							String sqlString = "delete  from STU_INTERNAL pe where pe.fk_stu_id='" + stu_id + "' and pe.fk_tch_opencourse_id='" + opencourse_id + "'";
							this.getGeneralService().executeBySQL(sqlString);
						}
					}
					map.clear();
					map.put("success", "true");
					map.put("info", msg + "操作成功");
					// this.setTogo("");
					return map;
				}
			} catch (Exception e) {
				map.clear();
				map.put("success", "false");
				map.put("info", msg + "操作失败");
				return map;
			}
		} else {
			map.clear();
			map.put("success", "false");
			map.put("info", "至少选择一名学员");
			return map;
		}
		return map;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
}
