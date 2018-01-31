package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.basic.PrBzzTchOpenCourseService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PrBzzTchOpenCourseDetailSearchAction extends MyBaseAction<PrBzzTchOpencourse> {

	private PrBzzTchOpenCourseService prBzzTchOpenCourseService;

	private String id;
	private boolean flag;
	private String tempFlag;
	private String method;// 用于区别必修课程、选修课程
	private EnumConstService enumConstService;

	private String batchid;

	private String coursename;// 课程名称
	private String coursecode;// 课程编号
	private String coursetype;// 课程性质
	private String ywfl;// 业务分类
	private String dgfl;// 大纲分类
	private String nrfl;// 内容分类
	private String isfee;// 是否收费
	private String teacher;// 主讲人
	private String jyxxrq;// 建议学习人群
	private String isks;// 是否收费

	private String discription;// 专项培训

	private List courseTypeList;
	private List courseCategoryList;
	private List courseItemTypeList;
	private List courseContentList;
	private List suggestrenList;

	public List getCourseTypeList() {
		return courseTypeList;
	}

	public void setCourseTypeList(List courseTypeList) {
		this.courseTypeList = courseTypeList;
	}

	public List getCourseCategoryList() {
		return courseCategoryList;
	}

	public void setCourseCategoryList(List courseCategoryList) {
		this.courseCategoryList = courseCategoryList;
	}

	public List getCourseItemTypeList() {
		return courseItemTypeList;
	}

	public void setCourseItemTypeList(List courseItemTypeList) {
		this.courseItemTypeList = courseItemTypeList;
	}

	public List getCourseContentList() {
		return courseContentList;
	}

	public void setCourseContentList(List courseContentList) {
		this.courseContentList = courseContentList;
	}

	public List getSuggestrenList() {
		return suggestrenList;
	}

	public void setSuggestrenList(List suggestrenList) {
		this.suggestrenList = suggestrenList;
	}

	public String getTempFlag() {
		return tempFlag;
	}

	public void setTempFlag(String tempFlag) {
		this.tempFlag = tempFlag;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
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
		if (this.getMethod().equals("bxCourse")) {
			this.getGridConfig().setTitle("必选课程");
		} else if (this.getMethod().equals("xxCourse")) {
			this.getGridConfig().setTitle("自选课程");
		}
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程名称"), "name");
		this.getGridConfig().addColumn(this.getText("课程编号"), "code");
		this.getGridConfig().addColumn(this.getText("价格(元)"), "price");
		this.getGridConfig().addColumn(this.getText("学时"), "time", false, true, true, null);
		// this.getGridConfig().addColumn(this.getText("课程性质"),"kc_type");

		ColumnConfig columnConfigType = new ColumnConfig(this.getText("课程性质"), "enumConstByFlagCourseType.name", true, true, true, "TextField", false, 100, "");
		String sql = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseType'";
		columnConfigType.setComboSQL(sql);
		this.getGridConfig().addColumn(columnConfigType);

		this.getGridConfig().addColumn(this.getText("主讲人"), "teacher", true, false, true, null);

		this.getGridConfig().addColumn(this.getText("课程报名人数"), "kcbmrs", false, true, true, null);
		// Lee start 2014年9月10日 去掉此项统计 提高效率
		// this.getGridConfig().addColumn(this.getText("已缴费金额(元)"), "yjfje",
		// false, true, true, null);
		this.getGridConfig().addColumn(this.getText("获得学时人数"), "hdxsrs", false, true, true, null);
		// this.getGridConfig().addColumn(this.getText("学习完成率"), "xxwcl", false,
		// true, true, null);
		// this.getGridConfig().addColumn(this.getText("考试通过率"), "kstgl", false,
		// true, true, null);

		ColumnConfig columnCategoryType = new ColumnConfig(this.getText("业务分类"), "enumConstByFlagCourseCategory.name", true, true, false, "TextField", false, 100, "");
		String sqlCategory = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseCategory'";
		columnCategoryType.setComboSQL(sqlCategory);
		this.getGridConfig().addColumn(columnCategoryType);

		ColumnConfig columnCourseItemTypeType = new ColumnConfig(this.getText("大纲分类"), "enumConstByFlagCourseItemType.name", true, true, false, "TextField", false, 100, "");
		String sqlCourseItem = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseItemType'";
		columnCourseItemTypeType.setComboSQL(sqlCourseItem);
		this.getGridConfig().addColumn(columnCourseItemTypeType);

		ColumnConfig columnContentProperty = new ColumnConfig(this.getText("内容属性分类"), "enumConstByFlagContentProperty.name", true, true, false, "TextField", false, 100, "");
		String sqlContentProperty = "select a.id ,a.name from enum_const a where a.namespace='FlagContentProperty'";
		columnContentProperty.setComboSQL(sqlContentProperty);
		this.getGridConfig().addColumn(columnContentProperty);

		ColumnConfig columnFlagSuggestType = new ColumnConfig(this.getText("建议学习人群"), "enumConstByFlagSuggest.name", true, true, false, "TextField", false, 100, "");
		String sqlSuggest = "select a.id ,a.name from enum_const a where a.namespace='FlagSuggest' and name not in ('面向所有人投票','面向学员投票','面向集体管理员投票')";
		columnFlagSuggestType.setComboSQL(sqlSuggest);
		this.getGridConfig().addColumn(columnFlagSuggestType);

		ColumnConfig columnConfigFree = new ColumnConfig(this.getText("收费状态"), "enumConstByFlagIsFree.name", true, true, false, "TextField", false, 100, "");
		String sql6 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsFree'";
		columnConfigFree.setComboSQL(sql6);
		this.getGridConfig().addColumn(columnConfigFree);

		ColumnConfig columnConfigIsExam = new ColumnConfig(this.getText("是否考试"), "enumConstByFlagIsExam.name", true, true, false, "TextField", false, 100, "");
		String sqlx = "select a.id ,a.name from enum_const a where a.namespace='FlagIsExam'";
		columnConfigIsExam.setComboSQL(sqlx);
		this.getGridConfig().addColumn(columnConfigIsExam);

		this.getGridConfig().addRenderScript(this.getText("课程信息"),
				"{return '<a href=\"/entity/teaching/peBzzCourseZixuanManager_courseInfo.action?id='+${value}+'&indexFile='+record.data['indexFile']+'\"  target=\"_blank\">课程信息</a>';}", "id");

		this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back()}");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrBzzTchOpencourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/prBzzTchOpenCourseDetailSearch";

	}

	public PrBzzTchOpencourse getBean() {
		return (PrBzzTchOpencourse) super.superGetBean();
	}

	public void setBean(PrBzzTchOpencourse prBzzTchOpencourse) {
		super.superSetBean(prBzzTchOpencourse);
	}

	public Page list() {
		String is_bx = "";
		if (this.getMethod().equals("bxCourse")) {
			is_bx = "1";
		} else if (this.getMethod().equals("xxCourse")) {
			is_bx = "0";
		}
		Page page = null;
		try {
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			String sql_con = "";
			if (us.getUserLoginType().equals("2") || us.getUserLoginType().equals("4")) {// 一级集体管理员、二级集体管理员
				sql_con = " and pbs.fk_enterprise_id in (SELECT ID FROM PE_ENTERPRISE WHERE ID IN (select fk_enterprise_id from pe_enterprise_manager where fk_sso_user_id='" + us.getSsoUser().getId()
						+ "') OR FK_PARENT_ID IN (select fk_enterprise_id from pe_enterprise_manager where fk_sso_user_id='" + us.getSsoUser().getId() + "'))";
			}
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append(" SELECT * FROM ( ");
			sqlBuffer.append(" SELECT AA.ID, ");
			sqlBuffer.append("        AA.NAME, ");
			sqlBuffer.append("        AA.CODE, ");
			sqlBuffer.append("        AA.PRICE, ");
			sqlBuffer.append("        AA.TIME, ");
			sqlBuffer.append("        AA.COURSETYPE, ");
			sqlBuffer.append("        AA.TEACHER, ");
			sqlBuffer.append("        NVL(BB.KCBMRS,0) KCBMRS, ");
			// Lee 2014年9月10日 去掉此项统计 提高效率
			// sqlBuffer.append(" NVL(CC.YJFJE,0) YJFJE, ");
			sqlBuffer.append("        NVL(DD.HDXSRS, 0) HDXSRS,'' A1,'' A2,'' A3,'' A4,'' A5,'' A6 ");
			sqlBuffer.append("   FROM (SELECT B.FK_BATCH_ID, ");
			sqlBuffer.append("                A.ID, ");
			sqlBuffer.append("                A.NAME, ");
			sqlBuffer.append("                A.CODE, ");
			sqlBuffer.append("                A.PRICE, ");
			sqlBuffer.append("                A.TIME, ");
			sqlBuffer.append("                ECCOURSETYPE.NAME COURSETYPE, ");
			sqlBuffer.append("                A.TEACHER ");
			// Lee 2014年9月10日 添加关联字段
			sqlBuffer.append("                ,B.ID BID ");
			sqlBuffer.append("           FROM PE_BZZ_TCH_COURSE A ");
			sqlBuffer.append("          INNER JOIN ENUM_CONST ECCOURSETYPE ");
			sqlBuffer.append("             ON A.FLAG_COURSETYPE = ECCOURSETYPE.ID ");
			sqlBuffer.append("          INNER JOIN PR_BZZ_TCH_OPENCOURSE B ");
			sqlBuffer.append("             ON A.ID = B.FK_COURSE_ID ");
			sqlBuffer.append("          INNER JOIN ENUM_CONST ECCHOOSE ");
			sqlBuffer.append("             ON B.FLAG_CHOOSE = ECCHOOSE.ID ");
			sqlBuffer.append("            AND ECCHOOSE.CODE = '" + is_bx + "' ");
			sqlBuffer.append("          INNER JOIN ENUM_CONST ECCOURSECATEGORY ");
			sqlBuffer.append("             ON A.FLAG_COURSECATEGORY = ECCOURSECATEGORY.ID ");
			sqlBuffer.append("          INNER JOIN ENUM_CONST ECCOURSEITEMTYPE ");
			sqlBuffer.append("             ON A.FLAG_COURSE_ITEM_TYPE = ECCOURSEITEMTYPE.ID ");
			sqlBuffer.append("          INNER JOIN ENUM_CONST ECCONTENTPROPERTY ");
			sqlBuffer.append("             ON A.FLAG_CONTENT_PROPERTY = ECCONTENTPROPERTY.ID ");
			sqlBuffer.append("          INNER JOIN ENUM_CONST ECISEXAM ");
			sqlBuffer.append("             ON A.FLAG_IS_EXAM = ECISEXAM.ID ");
			sqlBuffer.append("          INNER JOIN ENUM_CONST ECISFREE ");
			sqlBuffer.append("             ON A.FLAG_ISFREE = ECISFREE.ID ");
			sqlBuffer.append("           LEFT JOIN (SELECT A.FK_COURSE_ID, TO_CHAR(WM_CONCAT(B.NAME)) FS ");
			sqlBuffer.append("                       FROM PE_BZZ_TCH_COURSE_SUGGEST A ");
			sqlBuffer.append("                      INNER JOIN ENUM_CONST B ");
			sqlBuffer.append("                         ON A.FK_ENUM_CONST_ID = B.ID ");
			sqlBuffer.append("                      WHERE UPPER(TABLE_NAME) = 'PE_BZZ_TCH_COURSE' ");
			sqlBuffer.append("                      GROUP BY A.FK_COURSE_ID) PBTCS ");
			sqlBuffer.append("             ON A.ID = PBTCS.FK_COURSE_ID ");
			sqlBuffer.append("          WHERE B.FK_BATCH_ID = '" + this.getId() + "' ");
			StringBuffer searchSql = new StringBuffer();
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
				/* 课程名称 */
				if (name.equals("name")) {
					searchSql.append(" AND UPPER(A.NAME) LIKE '%" + value.toUpperCase() + "%' ");
				}
				/* 课程编号 */
				if (name.equals("code")) {
					searchSql.append(" AND UPPER(A.CODE) LIKE '%" + value.toUpperCase() + "%' ");
				}
				/* 价格 */
				if (name.equals("price")) {
					searchSql.append(" AND A.PRICE = '" + value + "' ");
				}
				/* 主讲人 */
				if (name.equals("teacher")) {
					searchSql.append(" AND UPPER(A.TEACHER) LIKE '%" + value.toUpperCase() + "%' ");
				}
				/* 课程性质 */
				if (name.equals("enumConstByFlagCourseType.name")) {
					searchSql.append(" AND ECCOURSETYPE.NAME = '" + value + "' ");
				}
				/* 业务分类 */
				if (name.equals("enumConstByFlagCourseCategory.name")) {
					searchSql.append(" AND ECCOURSECATEGORY.NAME = '" + value + "' ");
				}
				/* 大纲分类 */
				if (name.equals("enumConstByFlagCourseItemType.name")) {
					searchSql.append(" AND ECCOURSEITEMTYPE.NAME = '" + value + "' ");
				}
				/* 内容属性分类 */
				if (name.equals("enumConstByFlagContentProperty.name")) {
					searchSql.append(" AND ECCONTENTPROPERTY.NAME = '" + value + "' ");
				}
				/* 建议学习人群 */
				if (name.equals("enumConstByFlagSuggest.name")) {
					sqlBuffer.append(" AND INSTR(FS, '" + value + "', 1, 1) > 0 ");
				}
				/* 是否考试 */
				if (name.equals("enumConstByFlagIsExam.name")) {
					searchSql.append(" AND ECISEXAM.NAME = '" + value + "' ");
				}
				/* 课程收费状态 */
				if (name.equals("enumConstByFlagIsFree.name")) {
					searchSql.append(" AND ECISFREE.NAME = '" + value + "' ");
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
					temp = "COURSETYPE";
				}
			}
			sqlBuffer.append(searchSql);
			sqlBuffer.append(" ) AA ");
			// Lee 2014年10月20日报名人数查询错误问题修改
			sqlBuffer.append("  LEFT JOIN (SELECT B.BATCH_ID, A.ID, COUNT(PBTSE.ID) KCBMRS ");
			sqlBuffer.append("                FROM PR_BZZ_TCH_OPENCOURSE A ");
			sqlBuffer.append("               INNER JOIN ENUM_CONST D ");
			sqlBuffer.append("                  ON A.FLAG_CHOOSE = D.ID ");
			sqlBuffer.append("                 AND D.CODE = '" + is_bx + "' ");
			sqlBuffer.append("               INNER JOIN PE_BZZ_BATCH C ON C.ID = A.FK_BATCH_ID ");
			sqlBuffer.append("                 AND C.ID = '" + this.getId() + "' ");
			sqlBuffer.append("               INNER JOIN STU_BATCH B ");
			sqlBuffer.append("                  ON B.BATCH_ID = C.ID ");
			sqlBuffer.append("               INNER JOIN ENUM_CONST EC ON EC.ID = B.flag_batchpaystate AND EC.CODE = '1' ");
			// Lee 2014年10月20日报名人数查询错误问题修改
			sqlBuffer.append(" LEFT JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ON A.ID = PBTSE.FK_TCH_OPENCOURSE_ID AND PBTSE.FK_STU_ID = B.STU_ID ");
			if (!"3".equals(us.getUserLoginType())) {// 一级集体、二级集体 将原来注释的代码还原
				// Lee start 2014年9月10日 添加表连接及查询条件 查询集体所属学员
				sqlBuffer.append(" INNER JOIN PE_BZZ_STUDENT PBS ON B.STU_ID = PBS.ID " + sql_con);
			}
			sqlBuffer.append("               GROUP BY B.BATCH_ID, A.ID) BB ");
			sqlBuffer.append("     ON AA.FK_BATCH_ID = BB.BATCH_ID ");
			// Lee 2014年9月10日 添加关联字段
			sqlBuffer.append("     AND AA.BID = BB.ID ");
			// Lee start 2014年9月10日 去掉此项统计 提高效率
			// sqlBuffer.append(" LEFT JOIN (SELECT A.ID, SUM(PRICE) YJFJE ");
			// sqlBuffer.append(" FROM PE_BZZ_TCH_COURSE A ");
			// sqlBuffer.append(" INNER JOIN PR_BZZ_TCH_OPENCOURSE B ");
			// sqlBuffer.append(" ON A.ID = B.FK_COURSE_ID ");
			// sqlBuffer.append(" AND B.FK_BATCH_ID = '" + this.getId() + "' ");
			// sqlBuffer.append(" INNER JOIN ENUM_CONST C ");
			// sqlBuffer.append(" ON B.FLAG_CHOOSE = C.ID ");
			// sqlBuffer.append(" AND C.CODE = '" + is_bx + "' ");
			// sqlBuffer.append(" INNER JOIN PR_BZZ_TCH_STU_ELECTIVE D ");
			// sqlBuffer.append(" ON B.ID = D.FK_TCH_OPENCOURSE_ID ");
			// sqlBuffer.append(" INNER JOIN PE_BZZ_ORDER_INFO E ");
			// sqlBuffer.append(" ON D.FK_ORDER_ID = E.ID ");
			// sqlBuffer.append(" INNER JOIN ENUM_CONST F ");
			// sqlBuffer.append(" ON E.FLAG_PAYMENT_STATE = F.ID ");
			// sqlBuffer.append(" AND F.CODE = '1' ");
			// sqlBuffer.append(" GROUP BY A.ID) CC ");
			// sqlBuffer.append(" ON AA.ID = CC.ID ");
			sqlBuffer.append("   LEFT JOIN (SELECT A.ID, COUNT(DISTINCT D.FK_STU_ID) HDXSRS ");
			sqlBuffer.append("                FROM PE_BZZ_TCH_COURSE A ");
			sqlBuffer.append("               INNER JOIN PR_BZZ_TCH_OPENCOURSE B ");
			sqlBuffer.append("                  ON A.ID = B.FK_COURSE_ID ");
			sqlBuffer.append("                 AND B.FK_BATCH_ID = '" + this.getId() + "' ");
			sqlBuffer.append("               INNER JOIN ENUM_CONST C ");
			sqlBuffer.append("                  ON B.FLAG_CHOOSE = C.ID ");
			sqlBuffer.append("                 AND C.CODE = '" + is_bx + "' ");
			sqlBuffer.append("               INNER JOIN PR_BZZ_TCH_STU_ELECTIVE D ");
			sqlBuffer.append("                  ON B.ID = D.FK_TCH_OPENCOURSE_ID ");
			sqlBuffer.append("               INNER JOIN PE_BZZ_ORDER_INFO E ");
			sqlBuffer.append("                  ON D.FK_ORDER_ID = E.ID ");
			if (!"3".equals(us.getUserLoginType())) {// 一级集体、二级集体
				// lzh 添加表连接及查询条件 查询集体所属学员
				sqlBuffer.append(" INNER JOIN PE_BZZ_STUDENT PBS ON d.fk_STU_ID = PBS.ID " + sql_con);
			}
			sqlBuffer.append("               INNER JOIN ENUM_CONST F ");
			sqlBuffer.append("                  ON E.FLAG_PAYMENT_STATE = F.ID ");
			sqlBuffer.append("                 AND F.CODE = '1' ");
			sqlBuffer.append("               INNER JOIN ENUM_CONST G ");
			sqlBuffer.append("                  ON A.FLAG_IS_EXAM = G.ID ");
			sqlBuffer.append("                 AND ((G.CODE = '1' AND D.ISPASS = '1') OR ");
			sqlBuffer.append("                     (G.CODE <> '1' AND D.ISPASS <> '1')) ");
			sqlBuffer.append("                 AND D.COMPLETED_TIME IS NOT NULL ");
			sqlBuffer.append("                 AND D.START_TIME IS NOT NULL where e.fk_batch_id is not null");
			sqlBuffer.append("               GROUP BY A.ID) DD ");
			sqlBuffer.append("     ON AA.ID = DD.ID ");
			sqlBuffer.append(" ) WHERE 1 = 1");
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	public List<EnumConst> initSelectList(String namespace) {
		List<EnumConst> tempList = null;
		try {
			DetachedCriteria dcType = DetachedCriteria.forClass(EnumConst.class);
			dcType.add(Restrictions.eq("namespace", namespace));
			tempList = this.getGeneralService().getList(dcType);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return tempList;
	}

	// lwq 增加查看专项描述的方法
	public String showDiscription() {
		String sql = "select p.batch_note from PE_BZZ_BATCH p where p.id='" + id + "'";
		try {
			List list = this.getGeneralService().getBySQL(sql);
			if (list.size() > 0) {
				discription = (String) list.get(0);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "showDiscription";
	}

	public PrBzzTchOpenCourseService getPrBzzTchOpenCourseService() {
		return prBzzTchOpenCourseService;
	}

	public void setPrBzzTchOpenCourseService(PrBzzTchOpenCourseService prBzzTchOpenCourseService) {
		this.prBzzTchOpenCourseService = prBzzTchOpenCourseService;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public String getCoursecode() {
		return coursecode;
	}

	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}

	public String getCoursetype() {
		return coursetype;
	}

	public void setCoursetype(String coursetype) {
		this.coursetype = coursetype;
	}

	public String getYwfl() {
		return ywfl;
	}

	public void setYwfl(String ywfl) {
		this.ywfl = ywfl;
	}

	public String getDgfl() {
		return dgfl;
	}

	public void setDgfl(String dgfl) {
		this.dgfl = dgfl;
	}

	public String getNrfl() {
		return nrfl;
	}

	public void setNrfl(String nrfl) {
		this.nrfl = nrfl;
	}

	public String getIsfee() {
		return isfee;
	}

	public void setIsfee(String isfee) {
		this.isfee = isfee;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getJyxxrq() {
		return jyxxrq;
	}

	public void setJyxxrq(String jyxxrq) {
		this.jyxxrq = jyxxrq;
	}

	public String getIsks() {
		return isks;
	}

	public void setIsks(String isks) {
		this.isks = isks;
	}
}
