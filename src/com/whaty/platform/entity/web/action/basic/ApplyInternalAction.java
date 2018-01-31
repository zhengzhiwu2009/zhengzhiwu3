package com.whaty.platform.entity.web.action.basic;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

/**
 * 公司内训课程报名Action 2014年10月14日
 * 
 * @author Lzh
 * 
 */
public class ApplyInternalAction extends MyBaseAction {

	private List PrBzzTchStuElectiveBack;
	private String indexFile;
	private String stuids;
	private String id;
	private PeBzzStudent peBzzStudent;
	private String courseId;

	public PeBzzStudent getPeBzzStudent() {
		return peBzzStudent;
	}

	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
	}

	public List getPrBzzTchStuElectiveBack() {
		return PrBzzTchStuElectiveBack;
	}

	public void setPrBzzTchStuElectiveBack(List prBzzTchStuElectiveBack) {
		PrBzzTchStuElectiveBack = prBzzTchStuElectiveBack;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStuids() {
		return stuids;
	}

	public void setStuids(String stuids) {
		this.stuids = stuids;
	}

	/**
	 * 初始化列表
	 * 
	 * @author Lzh
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		boolean xiehui = true;// 判断协会端登录或者集体端登录
		if (us.getUserLoginType().equals("2") || us.getUserLoginType().equals("4")) {// 集体管理员
			// 获取登陆集体账号的机构ID
			xiehui = false;
		}
		this.getGridConfig().setTitle("公司内训课程报名");
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程名称"), "ksname");
		this.getGridConfig().addColumn(this.getText("课程编号"), "code");
		this.getGridConfig().addColumn(this.getText("主讲人"), "teacher");
		this.getGridConfig().addColumn(this.getText("课程时长（分钟）"), "courseLen", false, true, true, null);
		this.getGridConfig().addColumn(this.getText(""), "indexFile", false, false, false, null);

		ColumnConfig columnCategoryType = new ColumnConfig(this.getText("业务分类"), "enumConstByFlagCourseCategory.name", true, true, true,
				"TextField", false, 100, "");
		String sqlCategory = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseCategory'";
		columnCategoryType.setComboSQL(sqlCategory);
		this.getGridConfig().addColumn(columnCategoryType);

		ColumnConfig columnCourseItemTypeType = new ColumnConfig(this.getText("大纲分类"), "enumConstByFlagCourseItemType.name", true, true,
				true, "TextField", false, 100, "");
		String sqlCourseItem = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseItemType'";
		columnCourseItemTypeType.setComboSQL(sqlCourseItem);
		this.getGridConfig().addColumn(columnCourseItemTypeType);

		ColumnConfig columnContentProperty = new ColumnConfig(this.getText("内容属性分类"), "enumConstByFlagContentProperty.name", true, true,
				true, "TextField", false, 100, "");
		String sqlContentProperty = "select a.id ,a.name from enum_const a where a.namespace='FlagContentProperty'";
		columnContentProperty.setComboSQL(sqlContentProperty);
		this.getGridConfig().addColumn(columnContentProperty);

		ColumnConfig columnConfigOffline = new ColumnConfig(this.getText("是否下线"), "enumConstByFlagOffline.name", true, true, true,
				"TextField", false, 100, "");
		String sqly = "select a.id ,a.name from enum_const a where a.namespace='FlagOffline'";
		columnConfigOffline.setComboSQL(sqly);
		this.getGridConfig().addColumn(columnConfigOffline);

		ColumnConfig columnFlagSuggestType = new ColumnConfig(this.getText("建议学习人群"), "enumConstByFlagSuggest.name", true, true, false,
				"TextField", false, 100, "");
		String sqlSuggest = "select a.id ,a.name from enum_const a where a.namespace='FlagSuggest' and name not in ('面向所有人投票','面向学员投票','面向集体管理员投票')";
		columnFlagSuggestType.setComboSQL(sqlSuggest);
		this.getGridConfig().addColumn(columnFlagSuggestType);

		this.getGridConfig().addColumn(this.getText("课件id"), "coursewareId", false, false, false, "");

		this.getGridConfig().addColumn(this.getText("课件类型"), "scormType", false, false, false, "");

		this.getGridConfig().addColumn(this.getText("课件首页"), "indexFile", false, false, false, "");
		
		this
				.getGridConfig()
				.addRenderScript(
						this.getText("预览"),
						"{if(record.data['coursewareId'] ==''){return '预览';}else {return '<a href=/CourseImports/'+record.data['coursewareId']+'/'+record.data['scormType']+'/'+record.data['indexFile']+'?mydate='+ new Date().getTime() +' target=\"_blank\">预览</a>';}}",
						"");
		
		this.getGridConfig().addRenderFunction(this.getText("添加学员"),
				"<a  href=\"applyInternal_addStudent.action?courseId=${value}\">添加学员</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("查看学员"),
				"<a href=\"signStudentCourseInternal.action?typeflag=viewstu&courseId=${value}\">查看学员</a>", "id");
		this
				.getGridConfig()
				.addRenderScript(
						this.getText("课程信息"),
						"{return '<a href=\"/entity/teaching/peBzzCourseInternalSearch_courseInfo.action?id='+${value}+'&indexFile='+record.data['indexFile']+'\"  target=\"_blank\">课程信息</a>';}",
						"id");

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrBzzTchStuElectiveBack.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/applyInternal";
	}

	/**
	 * 课程管理列表
	 * 
	 * @author Lee
	 * @return
	 */
	public Page list() {
		Page page = null;
		try {
			StringBuffer sqlBuffer = new StringBuffer();
			String enterpriseIdString = "";
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			if (us.getUserLoginType().equals("2") || us.getUserLoginType().equals("4")) {// 集体管理员
				// 获取登陆集体账号的机构ID
				enterpriseIdString = us.getLoginId();
				// 查询本机构及本机构下属机构ID集合
			}
			sqlBuffer.append(" SELECT * FROM ( ");
			sqlBuffer.append(" SELECT /*+ rule*/PBTC.ID, ");
			sqlBuffer.append("        PBTC.NAME KSNAME, ");
			sqlBuffer.append("        PBTC.CODE, ");
			sqlBuffer.append("        PBTC.TEACHER, ");
			sqlBuffer.append("         pbtc.course_len, ");
			sqlBuffer.append("         pbtc.com_code, ");
			sqlBuffer.append("        EC2.NAME FLAGCOURSECATEGORY, ");
			sqlBuffer.append("        EC3.NAME FLAGCOURSEITEMTYPE, ");
			sqlBuffer.append("        EC4.NAME FLAGCONTENTPROPERTY,  EC8.Name FlagOffline, ");
			sqlBuffer.append("        B.FS, ");
			sqlBuffer.append("        SSL.COURSE_ID COURSEWAREID, ");
			sqlBuffer.append("        SSL.SCORM_TYPE SCORMTYPE, ");
			sqlBuffer.append("        SSL.INDEXFILE ");
			sqlBuffer.append("   FROM PE_BZZ_TCH_COURSE PBTC ");
			sqlBuffer.append("   JOIN ENUM_CONST EC2 ");
			sqlBuffer.append("     ON PBTC.FLAG_COURSECATEGORY = EC2.ID ");
			sqlBuffer.append("   JOIN ENUM_CONST EC3 ");
			sqlBuffer.append("     ON PBTC.FLAG_COURSE_ITEM_TYPE = EC3.ID ");
			sqlBuffer.append("   JOIN ENUM_CONST EC4 ");
			sqlBuffer.append("     ON PBTC.FLAG_CONTENT_PROPERTY = EC4.ID ");
			sqlBuffer.append("   JOIN ENUM_CONST EC7 ");
			sqlBuffer.append("   ON PBTC.FLAG_ISVALID = EC7.ID  AND EC7.CODE = '1'    JOIN ENUM_CONST EC8 ON PBTC.Flag_Offline = EC8.ID  ");// 有效课程
			sqlBuffer.append("   LEFT JOIN (SELECT FK_COURSE_ID, TO_CHAR(WM_CONCAT(EC.NAME)) FS ");
			sqlBuffer.append("                FROM PE_BZZ_TCH_COURSE_SUGGEST PBTCS ");
			sqlBuffer.append("               INNER JOIN ENUM_CONST EC ");
			sqlBuffer.append("                  ON PBTCS.FK_ENUM_CONST_ID = EC.ID ");
			sqlBuffer.append("                 AND PBTCS.TABLE_NAME = 'PE_BZZ_TCH_COURSE' ");
			sqlBuffer.append("               GROUP BY FK_COURSE_ID) B ");
			sqlBuffer.append("     ON PBTC.ID = B.FK_COURSE_ID ");
			sqlBuffer.append("   LEFT JOIN SCORM_SCO_LAUNCH SSL ON PBTC.CODE = SSL.COURSE_ID ");
			sqlBuffer.append("  WHERE PBTC.FLAG_COURSEAREA = 'Coursearea3'  AND EC7.CODE = '1'");// 公司内训课程
			if (us.getUserLoginType().equals("2")) {// 集体管理员
				// 获取登陆集体账号的机构ID
				sqlBuffer.append(" and pbtc.com_code like '%" + enterpriseIdString + "%'");
			} else if (us.getUserLoginType().equals("4")) {

				sqlBuffer
						.append(" and pbtc.id in ( select distinct(open.fk_course_id) from stu_internal inter "
								+ "  inner join (select stu.id   from pe_bzz_student stu   where stu.fk_enterprise_id in      (select organ.id "
								+ " from pe_enterprise organ    where organ.fk_parent_id =    (select fk_parent_id   from pe_enterprise   where id = (select fk_enterprise_id "
								+ "   from pe_enterprise_manager   where login_id = '" + enterpriseIdString
								+ "')  ))) studentid  on inter.fk_stu_id = studentid.id "
								+ " inner join PR_BZZ_TCH_OPENCOURSE open on inter.fk_tch_opencourse_id =open.id) ");
				sqlBuffer.append(" and pbtc.com_code=(" + "select login_id  from pe_enterprise_manager  " + " where fk_enterprise_id in  "
						+ " (select fk_parent_id  from pe_enterprise   where id = (select fk_enterprise_id"
						+ " from pe_enterprise_manager " + " where login_id = '" + enterpriseIdString + "'))" + ")");
			}
			sqlBuffer.append(" ) WHERE 1 = 1");
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
				/* 课程性质 */
				if (name.equals("enumConstByFlagCourseType.name")) {
					name = "FlagCourseType";
				}
				/* 业务分类 */
				if (name.equals("enumConstByFlagCourseCategory.name")) {
					name = "FlagCourseCategory";
				}
				/* 大纲分类 */
				if (name.equals("enumConstByFlagCourseItemType.name")) {
					name = "FlagCourseItemType";
				}
				/* 内容属性分类 */
				if (name.equals("enumConstByFlagContentProperty.name")) {
					name = "FlagContentProperty";
				}
				/* 建议学习人群 */
				if (name.equals("enumConstByFlagSuggest.name")) {
					sqlBuffer.append(" AND INSTR(FS, '" + value + "', 1, 1) > 0 ");
				}
				/* 是否下线 */
				if (name.equals("enumConstByFlagOffline.name")) {
					name = "FlagOffline";
				}
				/* 是否考试 */
				if (name.equals("enumConstByFlagIsExam.name")) {
					name = "FlagIsExam";
				}
				if (!name.equals("enumConstByFlagSuggest.name")) {
					if ("FlagCourseType".equals(name) || "FlagCourseCategory".equals(name) || "FlagCourseItemType".equals(name)
							|| "FlagContentProperty".equals(name) || "FlagIsvalid".equals(name) || "FlagOffline".equals(name)
							|| "FlagIsExam".equals(name)) {
						sqlBuffer.append(" and UPPER(" + name + ") = UPPER('" + value + "')");
					} else {
						sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");
					}
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
				if (temp.equals("enumConstByFlagCourseType.name")) {
					temp = "FlagCourseType";
				}
				/* 课程时长 */
				if (temp.equals("courseLen")) {
					temp = "course_len";
				}

				/* 业务分类 */
				if (temp.equals("enumConstByFlagCourseCategory.name")) {
					temp = "FlagCourseCategory";
				}
				/* 大纲分类 */
				if (temp.equals("enumConstByFlagCourseItemType.name")) {
					temp = "FlagCourseItemType";
				}
				/* 内容属性分类 */
				if (temp.equals("enumConstByFlagContentProperty.name")) {
					temp = "FlagContentProperty";
				}
				/* 是否下线 */
				if (temp.equals("enumConstByFlagOffline.name")) {
					temp = "FlagOffline";
				}

				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					sqlBuffer.append(" order by " + temp + " desc ");

				} else {
					sqlBuffer.append(" order by " + temp + " asc ");
				}
			}
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	public String preView() {
		return "noPreView";
	}

	public String getIndexFile() {
		return indexFile;
	}

	public void setIndexFile(String indexFile) {
		this.indexFile = indexFile;
	}

	public String addStudent() {
		String sql = "select 1 from pe_bzz_tch_course pe where pe.flag_offline='22' and pe.id='" + courseId + "'";
		List list = null;
		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!(list.size() > 0 && list != null)) {
			this.setMsg("该课程已下线，不能添加学员");
			return "message";
		}
		// ServletActionContext.getRequest().setAttribute("signUpId", courseId);
		return "addstudent";
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

}
