package com.whaty.platform.entity.web.action.teaching.elective;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzAssess;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.StudentBatch;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.basic.PrBzzTchOpenCourseService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PrBzzTchOpenCourseDetailAction extends MyBaseAction<PrBzzTchOpencourse> {

	private PrBzzTchOpenCourseService prBzzTchOpenCourseService;

	private String id;
	private boolean flag;
	private String tempFlag;
	private String method;
	private EnumConstService enumConstService;

	private String batchid;

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

	/**
	 * 初始化列表
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程编号"), "code");
		this.getGridConfig().addColumn(this.getText("课程名称"), "name");
		this.getGridConfig().addColumn(this.getText("课程性质"), "enumConstByFlagCourseType.name");
		String comboSQL = "select ID,NAME from enum_const ec where ec.namespace='FlagCoursearea' and ec.id in('Coursearea1','Coursearea2')";
		ColumnConfig columnConfig = new ColumnConfig(this.getText("课程所属区域"), "enumConstByFlagCoursearea.name");
		columnConfig.setAdd(true);
		columnConfig.setSearch(true);
		columnConfig.setList(true);
		columnConfig.setComboSQL(comboSQL);
		this.getGridConfig().addColumn(columnConfig);
		ColumnConfig columnQualificationsType = new ColumnConfig(this.getText("建议学习人群"), "enumConstByFlagQualificationsType.name", true,
				false, true, "TextField", false, 100, "");
		String sql3 = "select a.id ,a.name from enum_const a where a.namespace='FlagQualificationsType' and name not in ('面向所有人投票','面向学员投票','面向集体管理员投票')";
		columnQualificationsType.setComboSQL(sql3);
		this.getGridConfig().addColumn(columnQualificationsType);
		this.getGridConfig().addColumn(this.getText("学时"), "time", false, true, true, null);
		if ("addCourseMust".equalsIgnoreCase(method) || "addCourseChoose".equalsIgnoreCase(method)) {
			this.getGridConfig().addColumn(this.getText("主讲人"), "teacher", true, true, true, null);
			this.getGridConfig().addColumn(this.getText("价格"), "price", false, true, true, null);
			ColumnConfig columnConfigCategory = new ColumnConfig(this.getText("按业务分类"), "enumConstByFlagCourseCategory.name", true, false,
					false, "TextField", false, 100, "");
			String sql2 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseCategory'";
			columnConfigCategory.setComboSQL(sql2);
			this.getGridConfig().addColumn(columnConfigCategory);
			ColumnConfig columnCourseItemType = new ColumnConfig(this.getText("按大纲分类"), "enumConstByFlagCourseItemType.name", true, false,
					false, "TextField", false, 100, "");
			String sql7 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseItemType'";
			columnCourseItemType.setComboSQL(sql7);
			this.getGridConfig().addColumn(columnCourseItemType);
			ColumnConfig columnContentProperty = new ColumnConfig(this.getText("按内容属性分类"), "enumConstByFlagContentProperty.name", true,
					false, false, "TextField", false, 100, "");
			String sql9 = "select a.id ,a.name from enum_const a where a.namespace='FlagContentProperty'";
			columnContentProperty.setComboSQL(sql9);
			this.getGridConfig().addColumn(columnContentProperty);
		}
		boolean isShow = true;
		if (us.getUserLoginType().equals("3") && "myCourse".equalsIgnoreCase(method) && "3".equalsIgnoreCase(tempFlag))
			isShow = false;// 协会端查看课程删除是否发布字段
		this.getGridConfig().addColumn(this.getText("是否发布"), "enumConstByFlagIsvalid.name", false, false, false, null);
		if ("myMustCourse".equalsIgnoreCase(method)) {
			this.getGridConfig().addMenuFunction(this.getText("从培训课程删除"), "MustCourseDel");
		} else if ("myChooseCourse".equalsIgnoreCase(method)) {
			this.getGridConfig().addMenuFunction(this.getText("从培训课程删除"), "ChooseCourseDel");
		} else if ("addCourseMust".equalsIgnoreCase(method)) {
			this.getGridConfig().addMenuFunction(this.getText("加入培训课程"), "CourseMustAdd_" + this.getBatchid());
		} else if ("addCourseChoose".equalsIgnoreCase(method)) {
			this.getGridConfig().addMenuFunction(this.getText("加入培训课程"), "CourseChooseAdd_" + this.getBatchid());
		}
		this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back()}");
		String id = "";
		if ("addCourseChoose".equalsIgnoreCase(method) || "addCourseMust".equalsIgnoreCase(method)) {
			id = this.getBatchid();
		} else {
			id = this.getId();
		}
		ActionContext.getContext().getSession().put("id", id);
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrBzzTchOpencourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/prBzzTchOpenCourseDetail";

	}

	public PrBzzTchOpencourse getBean() {
		return (PrBzzTchOpencourse) super.superGetBean();
	}

	public void setBean(PrBzzTchOpencourse prBzzTchOpencourse) {
		super.superSetBean(prBzzTchOpencourse);
	}

	private int getDaysOfTowDiffDate(Date sdate, Date edate) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdate);
		Long begin = calendar.getTimeInMillis();
		calendar.setTime(edate);
		Long end = calendar.getTimeInMillis();
		// System.out.println("begin:" + begin);
		// System.out.println("end:" + end);
		int betweenDays = (int) ((end - begin) / (1000 * 60 * 60));
		return betweenDays;
	}

	public String IsCanEval() {
		DetachedCriteria criteria = DetachedCriteria.forClass(PeBzzBatch.class);
		criteria.add(Restrictions.eq("id", id));
		try {
			PeBzzBatch tempbatch = prBzzTchOpenCourseService.getPeBzzBatch(criteria);

			Date now = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(now);
			Date currentTime = formatter.parse(dateString);
			java.sql.Date currentTime_1 = new java.sql.Date(currentTime.getTime());
			Date start = tempbatch.getEvalStartDate();
			Date end = tempbatch.getEvalEndDate();

			String startstring = formatter.format(start);
			Date startTime = formatter.parse(startstring);
			java.sql.Date startTime_1 = new java.sql.Date(startTime.getTime());

			String endstring = formatter.format(end);
			Date endTime = formatter.parse(endstring);
			java.sql.Date endTime_1 = new java.sql.Date(endTime.getTime());
			if (startTime_1.compareTo(currentTime_1) <= 0 && endTime_1.compareTo(currentTime_1) >= 0) {
				this.setFlag(true);
			} else {
				this.setFlag(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "resultinfo";
	}

	/**
	 * 重写框架方法：修改数据前的校验
	 * 
	 * @author lzh
	 * @return
	 * @throws EntityException
	 */
	public int training() {
		int count = 0;
		String id = "";
		id = (String) ActionContext.getContext().getSession().get("id");
		String sql = "SELECT count(pe.id)  from PE_BZZ_BATCH pe  " + " where pe.id = '" + id + "'" + "  and pe.flag_deploy = 'deploy1'";
		try {
			if (!"0".equals(this.getGeneralService().getBySQL(sql).get(0).toString()))
				count = 1;
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 框架方法：或者查询列表所需数据
	 * 
	 * @author linjie
	 * @return
	 */
	/*
	 * public DetachedCriteria initDetachedCriteria() {
	 * 
	 * String sql = "select pbtc.id from pr_bzz_tch_opencourse op\n" + " inner
	 * join pe_bzz_tch_course pbtc on pbtc.id = op.fk_course_id \n" + "where
	 * op.fk_batch_id <> '40288a7b394d676d01394dad824c003b'"; List list = null;
	 * try { list = this.getGeneralService().getBySQL(sql); } catch
	 * (EntityException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * String id = ""; if ("addCourseChoose".equalsIgnoreCase(method) ||
	 * "addCourseMust".equalsIgnoreCase(method)) { id = this.getBatchid(); }
	 * else { id = this.id; }
	 * 
	 * DetachedCriteria dc = null; DetachedCriteria expcetdc = null; expcetdc =
	 * DetachedCriteria.forClass(PrBzzTchOpencourse.class);
	 * expcetdc.setProjection(Projections.distinct(Property
	 * .forName("peBzzTchCourse.id")));
	 * expcetdc.add(Restrictions.eq("peBzzBatch.id", id));
	 * expcetdc.createCriteria("peBzzBatch", "peBzzBatch");
	 * expcetdc.createCriteria("peBzzTchCourse", "peBzzTchCourse");
	 * expcetdc.createCriteria("enumConstByFlagChoose", "enumConstByFlagChoose",
	 * DetachedCriteria.LEFT_JOIN);
	 * 
	 * 
	 * dc = DetachedCriteria.forClass(PeBzzTchCourse.class);
	 * dc.createCriteria("enumConstByFlagCourseType",
	 * "enumConstByFlagCourseType"); dc.createCriteria("enumConstByFlagIsvalid",
	 * "enumConstByFlagIsvalid"); dc.createCriteria("enumConstByFlagCoursearea",
	 * "enumConstByFlagCoursearea", DetachedCriteria.LEFT_JOIN); // EnumConst
	 * enumConstByFlagIsFree = //
	 * this.getEnumConstService().getByNamespaceCode("FlagIsFree", "0"); //
	 * dc.add(Restrictions.eq("enumConstByFlagIsFree", //
	 * enumConstByFlagIsFree)); if ("myMustCourse".equalsIgnoreCase(method)) {
	 * expcetdc.add(Restrictions.eq("enumConstByFlagChoose.code", "1"));
	 * dc.add(Subqueries.propertyIn("id", expcetdc)); } else if
	 * ("myChooseCourse".equalsIgnoreCase(method)) {
	 * expcetdc.add(Restrictions.eq("enumConstByFlagChoose.code", "0"));
	 * dc.add(Subqueries.propertyIn("id", expcetdc)); } else if
	 * ("addCourseMust".equalsIgnoreCase(method)) { String[] Coursearea = { "1",
	 * "2" }; if (list.size() > 0) {
	 * dc.add(Restrictions.not(Restrictions.in("id", list .toArray(new
	 * String[list.size()]))));
	 * 
	 * dc.add(Restrictions.in("enumConstByFlagCoursearea.code", Coursearea));
	 * //expcetdc.add(Restrictions.eq("enumConstByFlagChoose.code", "1"));
	 * dc.add(Subqueries.propertyNotIn("id", expcetdc)); } } else if
	 * ("addCourseChoose".equalsIgnoreCase(method)) { String[] Coursearea = {
	 * "1", "2" }; if (list.size() > 0) {
	 * dc.add(Restrictions.not(Restrictions.in("id", list .toArray(new
	 * String[list.size()]))));
	 * 
	 * dc.add(Restrictions.in("enumConstByFlagCoursearea.code", Coursearea)); //
	 * expcetdc.add(Restrictions.eq("enumConstByFlagChoose.code", "0"));
	 * dc.add(Subqueries.propertyNotIn("id", expcetdc)); } } return dc; }
	 */
	public Page list() {
		String id = "";
		if ("addCourseChoose".equalsIgnoreCase(method) || "addCourseMust".equalsIgnoreCase(method)) {
			id = this.getBatchid();
		} else {
			id = this.id;
		}
		StringBuffer sqlBuffer = new StringBuffer();
		Page page = null;
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if ("myMustCourse".equalsIgnoreCase(method)) {
			sqlBuffer.append(" SELECT * ");
			sqlBuffer.append("   FROM (SELECT PBTC.ID AS ID, ");
			sqlBuffer.append("                PBTC.CODE AS CODE, ");
			sqlBuffer.append("                PBTC.NAME AS NAME, ");
			sqlBuffer.append("                EC_COURSETYPE.NAME AS COURSETYPE, ");
			sqlBuffer.append("                EC_COURSEAREA.NAME AS COURSEAREA, ");
			sqlBuffer.append("                LISTAGG(EC_COURSE_SUGGEST.NAME, ',') WITHIN GROUP(ORDER BY PBTC.ID) QUALIFICATIONSTYPE, PBTC.TIME ");
			sqlBuffer.append("           FROM PE_BZZ_TCH_COURSE PBTC ");
			sqlBuffer.append("          INNER JOIN ENUM_CONST EC_COURSETYPE ");
			sqlBuffer.append("             ON PBTC.FLAG_COURSETYPE = EC_COURSETYPE.ID ");
			sqlBuffer.append("          INNER JOIN ENUM_CONST EC_ISVALID ");
			sqlBuffer.append("             ON PBTC.FLAG_ISVALID = EC_ISVALID.ID ");
			sqlBuffer.append("          INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
			sqlBuffer.append("             ON PBTC.ID = PBTO.FK_COURSE_ID ");
			sqlBuffer.append("            AND PBTO.FLAG_CHOOSE = 'choose1' ");
			sqlBuffer.append("            AND PBTO.FK_BATCH_ID = '" + id + "' ");
			sqlBuffer.append("           LEFT OUTER JOIN ENUM_CONST EC_COURSEAREA ");
			sqlBuffer.append("             ON PBTC.FLAG_COURSEAREA = EC_COURSEAREA.ID ");
			sqlBuffer.append("           LEFT JOIN PE_BZZ_TCH_COURSE_SUGGEST PBTCS ");
			sqlBuffer.append("             ON PBTC.ID = PBTCS.FK_COURSE_ID ");
			sqlBuffer.append("           LEFT JOIN ENUM_CONST EC_COURSE_SUGGEST ");
			sqlBuffer.append("             ON PBTCS.FK_ENUM_CONST_ID = EC_COURSE_SUGGEST.ID ");
			sqlBuffer.append("          GROUP BY PBTC.ID, ");
			sqlBuffer.append("                   PBTC.CODE, ");
			sqlBuffer.append("                   PBTC.NAME, ");
			sqlBuffer.append("                   EC_COURSETYPE.NAME, ");
			sqlBuffer.append("                   PBTC.TIME, ");
			sqlBuffer.append("                   EC_COURSEAREA.NAME) ");
			sqlBuffer.append("  WHERE 1 = 1 ");
			try {
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

					if (name.equals("enumConstByFlagCourseType.name")) {
						name = "COURSETYPE";
					}
					if (name.equals("enumConstByFlagCoursearea.name")) {
						name = "COURSEAREA";
					}
					if (name.equals("enumConstByFlagQualificationsType.name")) {
						name = "qualificationsType";
					}

					if (name.equals("qualificationsType")) {
						sqlBuffer.append(" and INSTR(qualificationsType,'" + value + "',1,1)>0");
					} else {
						sqlBuffer.append(" and " + name + " like '%" + value + "%'");
					}
				} while (true);
				String temp = this.getSort();
				// 截掉前缀 Combobox_PeXxxxx.
				if (temp != null && temp.indexOf(".") > 1) {
					if (temp.toLowerCase().startsWith("combobox_")) {
						temp = temp.substring(temp.indexOf(".") + 1);
					}
				}
				if (this.getGridConfig().getColumByDateIndex(temp) != null) {
					if (this.getSort() != null && temp != null) {
						if (temp.equals("enumConstByFlagCourseType.name")) {
							temp = "COURSETYPE";
						}
						if (temp.equals("enumConstByFlagCoursearea.name")) {
							temp = "COURSEAREA";
						}
						if (temp.equals("enumConstByFlagQualificationsType.name")) {
							temp = "qualificationsType";
						}
						if (temp.equals("time")) {
							temp = "to_number(time)";
						}
						if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
							sqlBuffer.append(" order by " + temp + " desc ");
						}

						else {
							sqlBuffer.append(" order by " + temp + " asc ");
						}

					} else {
						sqlBuffer.append(" order by id desc");
					}
				}				
				// this.setSqlCondition(sqlBuffer);
				page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()),
						Integer.parseInt(this.getStart()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (EntityException e) {
				e.printStackTrace();
			}
		} else if ("myChooseCourse".equalsIgnoreCase(method)) {
			sqlBuffer.append(" SELECT * ");
			sqlBuffer.append("   FROM (SELECT PBTC.ID AS ID, ");
			sqlBuffer.append("                PBTC.CODE AS CODE, ");
			sqlBuffer.append("                PBTC.NAME AS NAME, ");
			sqlBuffer.append("                EC_COURSETYPE.NAME AS COURSETYPE, ");
			sqlBuffer.append("                EC_COURSEAREA.NAME AS COURSEAREA, ");
			sqlBuffer.append("                LISTAGG(EC_COURSE_SUGGEST.NAME, ',') WITHIN GROUP(ORDER BY PBTC.ID) QUALIFICATIONSTYPE, PBTC.TIME ");
			sqlBuffer.append("           FROM PE_BZZ_TCH_COURSE PBTC ");
			sqlBuffer.append("          INNER JOIN ENUM_CONST EC_COURSETYPE ");
			sqlBuffer.append("             ON PBTC.FLAG_COURSETYPE = EC_COURSETYPE.ID ");
			sqlBuffer.append("          INNER JOIN ENUM_CONST EC_ISVALID ");
			sqlBuffer.append("             ON PBTC.FLAG_ISVALID = EC_ISVALID.ID ");
			sqlBuffer.append("          INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
			sqlBuffer.append("             ON PBTC.ID = PBTO.FK_COURSE_ID ");
			sqlBuffer.append("            AND PBTO.FLAG_CHOOSE = 'choose0' ");
			sqlBuffer.append("            AND PBTO.FK_BATCH_ID = '" + id + "' ");
			sqlBuffer.append("           LEFT OUTER JOIN ENUM_CONST EC_COURSEAREA ");
			sqlBuffer.append("             ON PBTC.FLAG_COURSEAREA = EC_COURSEAREA.ID ");
			sqlBuffer.append("           LEFT JOIN PE_BZZ_TCH_COURSE_SUGGEST PBTCS ");
			sqlBuffer.append("             ON PBTC.ID = PBTCS.FK_COURSE_ID ");
			sqlBuffer.append("           LEFT JOIN ENUM_CONST EC_COURSE_SUGGEST ");
			sqlBuffer.append("             ON PBTCS.FK_ENUM_CONST_ID = EC_COURSE_SUGGEST.ID ");
			sqlBuffer.append("          GROUP BY PBTC.ID, ");
			sqlBuffer.append("                   PBTC.CODE, ");
			sqlBuffer.append("                   PBTC.NAME, ");
			sqlBuffer.append("                   EC_COURSETYPE.NAME, ");
			sqlBuffer.append("                   PBTC.TIME, ");
			sqlBuffer.append("                   EC_COURSEAREA.NAME) ");
			sqlBuffer.append("  WHERE 1 = 1 ");
			try {
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

					if (name.equals("enumConstByFlagCourseType.name")) {
						name = "COURSETYPE";
					}
					if (name.equals("enumConstByFlagCoursearea.name")) {
						name = "COURSEAREA";
					}
					if (name.equals("enumConstByFlagQualificationsType.name")) {
						name = "qualificationsType";
					}

					if (name.equals("qualificationsType")) {
						sqlBuffer.append(" and INSTR(qualificationsType,'" + value + "',1,1)>0");
					} else {
						sqlBuffer.append(" and " + name + " like '%" + value + "%'");
					}
				} while (true);
				String temp = this.getSort();
				// 截掉前缀 Combobox_PeXxxxx.
				if (temp != null && temp.indexOf(".") > 1) {
					if (temp.toLowerCase().startsWith("combobox_")) {
						temp = temp.substring(temp.indexOf(".") + 1);
					}
				}
				if (this.getGridConfig().getColumByDateIndex(temp) != null) {
					if (this.getSort() != null && temp != null) {
						if (temp.equals("enumConstByFlagCourseType.name")) {
							temp = "COURSETYPE";
						}
						if (temp.equals("enumConstByFlagCoursearea.name")) {
							temp = "COURSEAREA";
						}
						if (temp.equals("enumConstByFlagQualificationsType.name")) {
							temp = "qualificationsType";
						}						
						if (temp.equals("time")) {
							temp = "to_number(time)";
						}
						if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
							sqlBuffer.append(" order by " + temp + " desc ");
						}

						else {
							sqlBuffer.append(" order by " + temp + " asc ");
						}

					} else {
						sqlBuffer.append(" order by id desc");
					}
				}				
				page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()),
						Integer.parseInt(this.getStart()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (EntityException e) {
				e.printStackTrace();
			}

		} else if ("addCourseMust".equalsIgnoreCase(method)) {// 表示专项培训必选课程
			sqlBuffer.append("SELECT * ");
			sqlBuffer
					.append("FROM  ( select  pbtc.id as ID, pbtc.CODE as CODE,                                                                               ");
			sqlBuffer.append("        pbtc.NAME as NAME,                                                                               ");
			sqlBuffer.append("        ec_COURSETYPE.NAME as COURSETYPE,                                                                ");
			sqlBuffer.append("        ec_COURSEAREA.NAME as COURSEAREA,                                                                ");
			sqlBuffer.append("        listagg(ec_COURSE_SUGGEST.name, ',') within GROUP(order by pbtc.id) qualificationsType   , PBTC.TIME,       ");
			sqlBuffer
					.append("       pbtc.teacher teacher , pbtc.price price, ec_CourseCategory.NAME as CourseCategory, ec_CourseItemType.NAME as CourseItemType, ec_ContentProperty.NAME as ContentProperty      ");
			sqlBuffer.append("   from PE_BZZ_TCH_COURSE pbtc                                                                           ");
			sqlBuffer.append("  inner join ENUM_CONST ec_COURSETYPE                                                                    ");
			sqlBuffer.append("     on pbtc.FLAG_COURSETYPE = ec_COURSETYPE.ID                                                          ");
			sqlBuffer.append("  inner join ENUM_CONST ec_ISVALID                                                                       ");
			sqlBuffer.append("     on pbtc.FLAG_ISVALID = ec_ISVALID.ID                                                                ");
			sqlBuffer.append("   left outer join ENUM_CONST ec_COURSEAREA                                                              ");
			sqlBuffer.append("     on pbtc.FLAG_COURSEAREA = ec_COURSEAREA.ID                                                          ");
			sqlBuffer.append("   left join PE_BZZ_TCH_COURSE_SUGGEST pbtcs                                                             ");
			sqlBuffer.append("     on pbtc.id = pbtcs.fk_course_id                                                                     ");
			sqlBuffer.append("   left join enum_const ec_COURSE_SUGGEST                                                                ");
			sqlBuffer.append("     on pbtcs.fk_enum_const_id = ec_COURSE_SUGGEST.id                                                    ");
			sqlBuffer
					.append("      left join enum_const ec_CourseCategory  on pbtc.flag_coursecategory = ec_CourseCategory.id left join enum_const ec_CourseItemType                                                  ");
			sqlBuffer
					.append("   on pbtc.flag_course_item_type = ec_CourseItemType.id  left join enum_const ec_ContentProperty on pbtc.flag_content_property = ec_ContentProperty.id                                                   ");
			// 专项培训不能有免费的课程
			sqlBuffer.append("   left join enum_const ec_Shoufei                                                              ");
			sqlBuffer.append("     on pbtc.flag_isfree = ec_Shoufei.id                                                ");

			sqlBuffer.append("  where pbtc.FLAG_ISVALID='2' and pbtc.flag_offline='22' and pbtc.ID not in ");// 课程为未下线状态
			sqlBuffer.append("        (select distinct pbtc2.ID as y0_                                                                 ");
			sqlBuffer.append("           from PR_BZZ_TCH_OPENCOURSE pbtc                                                               ");
			sqlBuffer.append("          inner join PE_BZZ_BATCH pbb                                                                    ");
			sqlBuffer.append("             on pbtc.FK_BATCH_ID = pbb.ID                                                                ");
			sqlBuffer.append("          inner join PE_BZZ_TCH_COURSE pbtc2                                                             ");
			sqlBuffer.append("             on pbtc.FK_COURSE_ID = pbtc2.ID                                                             ");
			sqlBuffer.append("           left outer join ENUM_CONST ec_COURSEAREA                                                      ");
			sqlBuffer.append("             on pbtc.FLAG_CHOOSE = ec_COURSEAREA.ID                                                      ");
			sqlBuffer
					.append("          where  pbb.ID = '"
							+ id
							+ "')   and ec_Shoufei.Id='40288a7b39968644013996bf8714004c'    and pbtc.FLAG_COURSEAREA IN ('Coursearea1','Coursearea2')   ");
			sqlBuffer
					.append("  group by pbtc.id,pbtc.CODE, pbtc.NAME, ec_COURSETYPE.NAME, ec_COURSEAREA.NAME , pbtc.price ,  pbtc.time , pbtc.teacher,ec_CourseCategory.NAME , ec_CourseItemType.NAME , ec_ContentProperty.NAME,END_DATE HAVING (PBTC.END_DATE > SYSDATE OR PBTC.END_DATE IS NULL) ) where 1=1                                ");
			try {
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
					if (name.equals("enumConstByFlagCourseType.name")) {
						name = "COURSETYPE";
					}
					if (name.equals("enumConstByFlagCoursearea.name")) {
						name = "COURSEAREA";
					}
					if (name.equals("enumConstByFlagQualificationsType.name")) {
						name = "qualificationsType";
					}
					if (name.equals("enumConstByFlagCourseItemType.name")) {
						name = "CourseItemType";
					}
					if (name.equals("enumConstByFlagContentProperty.name")) {
						name = "ContentProperty";
					}
					if (name.equals("enumConstByFlagCourseCategory.name")) {
						name = "CourseCategory";
					}
					if (name.equals("qualificationsType")) {
						sqlBuffer.append(" and INSTR(qualificationsType,'" + value + "',1,1)>0");
					} else {
						sqlBuffer.append(" and " + name + " like '%" + value + "%'");
					}

				} while (true);
				String temp = this.getSort();
				// 截掉前缀 Combobox_PeXxxxx.
				if (temp != null && temp.indexOf(".") > 1) {
					if (temp.toLowerCase().startsWith("combobox_")) {
						temp = temp.substring(temp.indexOf(".") + 1);
					}
				}
				if (this.getGridConfig().getColumByDateIndex(temp) != null) {
					if (this.getSort() != null && temp != null) {
						if (temp.equals("enumConstByFlagCourseType.name")) {
							temp = "COURSETYPE";
						}
						if (temp.equals("enumConstByFlagCoursearea.name")) {
							temp = "COURSEAREA";
						}
						if (temp.equals("enumConstByFlagQualificationsType.name")) {
							temp = "qualificationsType";
						}
						if (temp.equals("price")) {
							temp = "to_number(price)";
						}
						if (temp.equals("time")) {
							temp = "to_number(time)";
						}
						if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
							sqlBuffer.append(" order by " + temp + " desc ");
						}

						else {
							sqlBuffer.append(" order by " + temp + " asc ");
						}

					} else {
						sqlBuffer.append(" order by id desc");
					}
				}

				// this.setSqlCondition(sqlBuffer);
				page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()),
						Integer.parseInt(this.getStart()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (EntityException e) {
				e.printStackTrace();
			}

		} else if ("addCourseChoose".equalsIgnoreCase(method)) {

			sqlBuffer.append("SELECT * ");
			sqlBuffer
					.append("FROM  ( select  pbtc.id as ID, pbtc.CODE as CODE,                                                                               ");
			sqlBuffer.append("        pbtc.NAME as NAME,                                                                               ");
			sqlBuffer.append("        ec_COURSETYPE.NAME as COURSETYPE,                                                                ");
			sqlBuffer.append("        ec_COURSEAREA.NAME as COURSEAREA,                                                                ");
			sqlBuffer.append("        listagg(ec_COURSE_SUGGEST.name, ',') within GROUP(order by pbtc.id) qualificationsType   , pbtc.time,       ");
			sqlBuffer
					.append("        pbtc.teacher teacher , pbtc.price price,   ec_CourseCategory.NAME as CourseCategory, ec_CourseItemType.NAME as CourseItemType, ec_ContentProperty.NAME as ContentProperty      ");
			sqlBuffer.append("   from PE_BZZ_TCH_COURSE pbtc                                                                           ");
			sqlBuffer.append("  inner join ENUM_CONST ec_COURSETYPE                                                                    ");
			sqlBuffer.append("     on pbtc.FLAG_COURSETYPE = ec_COURSETYPE.ID                                                          ");
			sqlBuffer.append("  inner join ENUM_CONST ec_ISVALID                                                                       ");
			sqlBuffer.append("     on pbtc.FLAG_ISVALID = ec_ISVALID.ID                                                                ");
			sqlBuffer.append("   left outer join ENUM_CONST ec_COURSEAREA                                                              ");
			sqlBuffer.append("     on pbtc.FLAG_COURSEAREA = ec_COURSEAREA.ID                                                          ");
			sqlBuffer.append("   left join PE_BZZ_TCH_COURSE_SUGGEST pbtcs                                                             ");
			sqlBuffer.append("     on pbtc.id = pbtcs.fk_course_id                                                                     ");
			sqlBuffer.append("   left join enum_const ec_COURSE_SUGGEST                                                                ");
			sqlBuffer.append("     on pbtcs.fk_enum_const_id = ec_COURSE_SUGGEST.id                                                    ");
			sqlBuffer
					.append("      left join enum_const ec_CourseCategory  on pbtc.flag_coursecategory = ec_CourseCategory.id left join enum_const ec_CourseItemType                                                  ");
			sqlBuffer
					.append("   on pbtc.flag_course_item_type = ec_CourseItemType.id  left join enum_const ec_ContentProperty on pbtc.flag_content_property = ec_ContentProperty.id                                                   ");
			// 专项培训不能添加免费自选
			sqlBuffer.append("   left join enum_const ec_Shoufei                                                        ");
			sqlBuffer.append("     on pbtc.flag_isfree = ec_Shoufei.id                                                     ");

			sqlBuffer
					.append("  where pbtc.FLAG_ISVALID='2' and pbtc.flag_offline='22' and pbtc.ID not in                                                                                       ");
			sqlBuffer.append("        (select distinct pbtc2.ID as y0_                                                                 ");
			sqlBuffer.append("           from PR_BZZ_TCH_OPENCOURSE pbtc                                                               ");
			sqlBuffer.append("          inner join PE_BZZ_BATCH pbb                                                                    ");
			sqlBuffer.append("             on pbtc.FK_BATCH_ID = pbb.ID                                                                ");
			sqlBuffer.append("          inner join PE_BZZ_TCH_COURSE pbtc2                                                             ");
			sqlBuffer.append("             on pbtc.FK_COURSE_ID = pbtc2.ID                                                             ");
			sqlBuffer.append("           left outer join ENUM_CONST ec_COURSEAREA                                                      ");
			sqlBuffer.append("             on pbtc.FLAG_CHOOSE = ec_COURSEAREA.ID                                                      ");
			sqlBuffer
					.append("          where  pbb.ID = '"
							+ id
							+ "')       and ec_Shoufei.Id='40288a7b39968644013996bf8714004c'      and pbtc.FLAG_COURSEAREA IN('Coursearea1','Coursearea2')                             ");
			sqlBuffer
					.append("  group by pbtc.id,pbtc.CODE, pbtc.NAME, ec_COURSETYPE.NAME, ec_COURSEAREA.NAME , pbtc.price ,  pbtc.time , pbtc.teacher ,ec_CourseCategory.NAME , ec_CourseItemType.NAME , ec_ContentProperty.NAME,END_DATE HAVING (PBTC.END_DATE > SYSDATE OR PBTC.END_DATE IS NULL)) where 1=1                                ");

			try {
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

					if (name.equals("enumConstByFlagCourseType.name")) {
						name = "COURSETYPE";
					}
					if (name.equals("enumConstByFlagCoursearea.name")) {
						name = "COURSEAREA";
					}
					if (name.equals("enumConstByFlagQualificationsType.name")) {
						name = "qualificationsType";
					}
					if (name.equals("enumConstByFlagCourseItemType.name")) {
						name = "CourseItemType";
					}
					if (name.equals("enumConstByFlagContentProperty.name")) {
						name = "ContentProperty";
					}
					if (name.equals("enumConstByFlagCourseCategory.name")) {
						name = "CourseCategory";
					}

					if (name.equals("qualificationsType")) {
						sqlBuffer.append(" and INSTR(qualificationsType,'" + value + "',1,1)>0");
					} else {
						sqlBuffer.append(" and " + name + " like '%" + value + "%'");
					}

				} while (true);
				String temp = this.getSort();
				if (temp != null && temp.indexOf(".") > 1) {
					if (temp.toLowerCase().startsWith("combobox_")) {
						temp = temp.substring(temp.indexOf(".") + 1);
					}
				}
				if (this.getSort() != null && temp != null) {
					if (temp.equals("enumConstByFlagCourseType.name")) {
						temp = "COURSETYPE";
					}
					if (temp.equals("enumConstByFlagCoursearea.name")) {
						temp = "COURSEAREA";
					}
					if (temp.equals("enumConstByFlagQualificationsType.name")) {
						temp = "qualificationsType";
					}
					if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {

						sqlBuffer.append(" order by " + temp + " desc ");

					} else {
						sqlBuffer.append(" order by " + temp + " asc ");
					}
				} else {
					sqlBuffer.append(" order by CODE desc");
				}
				// this.setSqlCondition(sqlBuffer);
				page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()),
						Integer.parseInt(this.getStart()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (EntityException e) {
				e.printStackTrace();
			}

		}
		return page;

	}

	/**
	 * 重写框架方法：删除数据
	 * 
	 * @author linjie
	 * @return
	 */
	public Map delete() {
		Map map = new HashMap();
		String msg = "";
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			String strId = "";
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List idList = new ArrayList();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
					strId += "'" + ids[i] + "',";
				}
				strId = strId.substring(0, strId.length() - 1);

				// 先判断所删除课程是否已经有学员开始学习。如果没有才可以进行删除
				DetachedCriteria dc1 = DetachedCriteria.forClass(TrainingCourseStudent.class);
				dc1.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
				dc1.add(Restrictions.gt("percent", Double.parseDouble("0")));
				dc1.add(Restrictions.in("prBzzTchOpencourse.id", ids));

				try {

					List<TrainingCourseStudent> tlist1 = this.getGeneralService().getList(dc1);
					if (tlist1.size() > 0) {
						map.clear();
						map.put("success", "false");
						map.put("info", "该课程有学员开始学习,无法删除!");
						return map;
					} else {
						String sql = "delete from stuttime where fk_open_course_id in (" + strId + ")";
						int rett = this.getGeneralService().executeBySQL(sql);
					}
				} catch (EntityException e) {
					e.printStackTrace();
				}
				String id = (String) ServletActionContext.getRequest().getAttribute("id");
				DetachedCriteria dcbatch = DetachedCriteria.forClass(PeBzzBatch.class);
				dcbatch.add(Restrictions.eq("id", id));
				try {
					PeBzzBatch peBzzBatch = (PeBzzBatch) this.getGeneralService().getList(dcbatch).get(0);
					if (peBzzBatch.getStartDate().before(new Date())) {
						map.clear();
						map.put("success", "false");
						map.put("info", "专项已经开始，无法添加删除课程");
						return map;
					}
				} catch (EntityException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				// 判断是否已经有课程评估
				DetachedCriteria dc2 = DetachedCriteria.forClass(PeBzzAssess.class);
				dc2.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
				dc2.add(Restrictions.in("prBzzTchOpencourse.id", ids));

				try {

					List<TrainingCourseStudent> tlist2 = this.getGeneralService().getList(dc2);
					if (tlist2.size() > 0) {
						map.clear();
						map.put("success", "false");
						map.put("info", "该课程已有课程评估内容,无法删除!");
						return map;
					}
				} catch (EntityException e) {
					e.printStackTrace();
				}

				try {

					DetachedCriteria dc = DetachedCriteria.forClass(TrainingCourseStudent.class);
					dc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");

					dc.add(Restrictions.gt("percent", Double.parseDouble("0")));

					dc.add(Restrictions.in("prBzzTchOpencourse.id", ids));
					List<TrainingCourseStudent> tlist = this.getGeneralService().getList(dc);
					// if (tlist.size() > 0) {
					// for (int k = 0; k < tlist.size(); k++) {
					// // this.getGeneralService().delete(tlist.get(k));
					// }
					// }
					msg = "专项培训课程";
					this.prBzzTchOpenCourseService.deleteByIds(idList);
					map.put("success", "true");
					map.put("info", msg + ids.length + "条记录删除成功");
				} catch (RuntimeException e) {
					return super.checkForeignKey(e);
				} catch (Exception e1) {
					e1.printStackTrace();
					map.clear();
					map.put("success", "false");
					map.put("info", "删除失败--" + e1.getMessage());
				}

			} else {
				map.put("success", "false");
				map.put("info", "请选择操作项");
			}
		}
		return map;
	}

	public PrBzzTchOpenCourseService getPrBzzTchOpenCourseService() {
		return prBzzTchOpenCourseService;
	}

	public void setPrBzzTchOpenCourseService(PrBzzTchOpenCourseService prBzzTchOpenCourseService) {
		this.prBzzTchOpenCourseService = prBzzTchOpenCourseService;
	}

	/**
	 * 重写框架方法--列更新
	 * 
	 * @author linjie
	 * @return
	 */
	@SuppressWarnings( { "unchecked", "unchecked", "unchecked" })
	public Map updateColumn() {
		int count = this.training();
		Map map = new HashMap();
		String msg = "";
		String action = this.getColumn();
		String id = "";
		String ap[] = action.split("_");
		if (action.indexOf("CourseMustAdd") >= 0 || action.indexOf("CourseChooseAdd") >= 0) {
			id = ap[1];
		} else {
			id = ActionContext.getContext().getSession().get("id").toString().trim();
		}
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			try {
				DetachedCriteria rodc = DetachedCriteria.forClass(PeBzzBatch.class);
				rodc.add(Restrictions.eq("id", id));
				List<PeBzzBatch> peBzzBatchlist = this.getGeneralService().getList(rodc);
				// 判断当前时间是否在专项时间段
				if (peBzzBatchlist.get(0).getEndDate().before(new Date())) {
					map.clear();
					map.put("success", "false");
					map.put("info", "专项已经结束，无法添加删除课程");
					return map;
				}
				if (count != 0) {
					map.clear();
					map.put("success", "false");
					map.put("info", "不能做此操作，此专项培训已发布");
					return map;
				}
				if (action.indexOf("CourseMustAdd") >= 0) {
					EnumConst enumConstByFlagIsvalid = this.getEnumConstService().getByNamespaceCode("1", "FlagIsvalid");// 默认专项

					// 根据id获取课程列表
					DetachedCriteria coursedc = DetachedCriteria.forClass(PeBzzTchCourse.class);
					List<PeBzzTchCourse> courselist = new ArrayList<PeBzzTchCourse>();

					coursedc.add(Restrictions.in("id", ids));

					courselist = this.getGeneralService().getList(coursedc);

					// 根据专项id获取专项学生列表
					DetachedCriteria stubatDc = DetachedCriteria.forClass(StudentBatch.class);
					stubatDc.add(Restrictions.eq("peBzzBatch.id", id));
					stubatDc.createCriteria("peStudent", "peStudent");
					stubatDc.setProjection(Projections.distinct(Projections.property("peStudent.id")));

					DetachedCriteria stuDc = DetachedCriteria.forClass(PeBzzStudent.class);
					stuDc.add(Property.forName("id").in(stubatDc));
					List<PeBzzStudent> stuList = this.getGeneralService().getList(stuDc);

					// 循环课程列表，学生列表，构建开课表和选课表
					List<PrBzzTchStuElective> electiveList = new ArrayList<PrBzzTchStuElective>();
					List<PrBzzTchOpencourse> opencourseList = new ArrayList<PrBzzTchOpencourse>();

					// 未支付状态
					EnumConst enumConstByFlagElectivePayStatus = this.getEnumConstService()
							.getByNamespaceCode("FlagElectivePayStatus", "0");
					// 必选
					EnumConst enumConstByFlagChooseStatus = this.getEnumConstService().getByNamespaceCode("FlagChoose", "1");

					for (int i = 0; i < courselist.size(); i++) {
						PrBzzTchOpencourse prBzzTchOpencourse = new PrBzzTchOpencourse();
						prBzzTchOpencourse.setPeBzzBatch(peBzzBatchlist.get(0));
						prBzzTchOpencourse.setPeBzzTchCourse(courselist.get(i));
						prBzzTchOpencourse.setEnumConstByFlagChoose(enumConstByFlagChooseStatus);
						opencourseList.add(prBzzTchOpencourse);
						for (PeBzzStudent peBzzStudent : stuList) {
							/*
							 * TrainingCourseStudent trainingCourseStudent = new
							 * TrainingCourseStudent();
							 * trainingCourseStudent.setPrBzzTchOpencourse(prBzzTchOpencourse);
							 * trainingCourseStudent.setSsoUser(peBzzStudent.getSsoUser());
							 * trainingCourseStudent.setPercent(0.00);
							 * trainingCourseStudent.setLearnStatus(StudyProgress.UNCOMPLETE);
							 */
							DetachedCriteria dceletived = DetachedCriteria.forClass(PrBzzTchStuElective.class);
							dceletived.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse",
									"peBzzTchCourse");
							dceletived.createAlias("peBzzStudent", "peBzzStudent");
							dceletived.add(Restrictions.eq("peBzzStudent", peBzzStudent));
							dceletived.add(Restrictions.isNotNull("prBzzTchOpencourse.id"));

							// Lee 注释↓查询选课记录方式：因为公共课程课程对应多个开课ID所以修改查询方式
							// dceletived.add(Restrictions.eq("prBzzTchOpencourse.id",
							// prBzzTchOpencourse.getId()));
							// Lee↓新查询选课方式
							dceletived.add(Restrictions.eq("peBzzTchCourse.id", prBzzTchOpencourse.getPeBzzTchCourse().getId()));
							DetachedCriteria dceletiveback = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
							dceletiveback.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse",
									"peBzzTchCourse");
							dceletiveback.createAlias("peBzzStudent", "peBzzStudent");
							dceletiveback.add(Restrictions.eq("peBzzStudent", peBzzStudent));
							dceletiveback.add(Restrictions.isNotNull("prBzzTchOpencourse.id"));
							// Lee 注释↓查询选课记录方式：因为公共课程课程对应多个开课ID所以修改查询方式
							// dceletiveback.add(Restrictions.eq("prBzzTchOpencourse.id",
							// prBzzTchOpencourse.getId()));
							dceletiveback.add(Restrictions.eq("prBzzTchOpencourse.id", prBzzTchOpencourse.getPeBzzTchCourse().getId()));
							try {
								List tempList = this.getGeneralService().getList(dceletived);
								List tempBackList = this.getGeneralService().getList(dceletiveback);
								if ((tempList != null && tempList.size() > 0) || (tempBackList != null && tempBackList.size() > 0)) {
									map.put("success", "false");
									map.put("info", "学员：" + peBzzStudent.getRegNo() + "已选课程-"
											+ prBzzTchOpencourse.getPeBzzTchCourse().getCode());
									return map;
								}
							} catch (EntityException e) {
								e.printStackTrace();
								map.put("success", "false");
								map.put("info", "选课验证失败！");
								return map;
							}
							PrBzzTchStuElective prBzzTchStuElective = new PrBzzTchStuElective();
							prBzzTchStuElective.setPeBzzStudent(peBzzStudent);
							prBzzTchStuElective.setPrBzzTchOpencourse(prBzzTchOpencourse);
							// prBzzTchStuElective.setTrainingCourseStudent(trainingCourseStudent);
							prBzzTchStuElective.setElectiveDate(new Date());
							prBzzTchStuElective.setEnumConstByFlagElectivePayStatus(enumConstByFlagElectivePayStatus);
							electiveList.add(prBzzTchStuElective);
						}
					}
					this.prBzzTchOpenCourseService.saveElectiveListBack(opencourseList, electiveList);
					msg = "必选课程加入专项";
				} else if (action.indexOf("CourseChooseAdd") >= 0) {
					// 根据id获取课程列表
					DetachedCriteria coursedc = DetachedCriteria.forClass(PeBzzTchCourse.class);
					List<PeBzzTchCourse> courselist = new ArrayList<PeBzzTchCourse>();
					coursedc.add(Restrictions.in("id", ids));
					courselist = this.getGeneralService().getList(coursedc);
					List<PrBzzTchOpencourse> opencourseList = new ArrayList<PrBzzTchOpencourse>();
					// 自选
					EnumConst enumConstByFlagChooseStatus = this.getEnumConstService().getByNamespaceCode("FlagChoose", "0");
					for (int i = 0; i < courselist.size(); i++) {
						PrBzzTchOpencourse prBzzTchOpencourse = new PrBzzTchOpencourse();
						prBzzTchOpencourse.setPeBzzBatch(peBzzBatchlist.get(0));
						prBzzTchOpencourse.setPeBzzTchCourse(courselist.get(i));
						prBzzTchOpencourse.setEnumConstByFlagChoose(enumConstByFlagChooseStatus);
						opencourseList.add(prBzzTchOpencourse);
						this.prBzzTchOpenCourseService.saveElectiveListBack(opencourseList, null);
						msg = "自选课程加入专项";

					}
				} else if (action.equals("MustCourseDel")) {
					// 判断当前时间是否在专项时间段
					if (peBzzBatchlist.get(0).getStartDate().before(new Date()) && peBzzBatchlist.get(0).getEndDate().after(new Date())) {
						map.clear();
						map.put("success", "false");
						map.put("info", "专项已经开始，无法删除课程");
						return map;
					}
					// 先判断所删除课程是否已经有学员开始学习。如果没有才可以进行删除
					DetachedCriteria dc1 = DetachedCriteria.forClass(TrainingCourseStudent.class);
					dc1.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
					dc1.add(Restrictions.gt("percent", Double.parseDouble("0")));
					dc1.add(Restrictions.in("peBzzTchCourse.id", ids));
					dc1.add(Restrictions.eq("prBzzTchOpencourse.peBzzBatch", peBzzBatchlist.get(0)));
					try {
						List<TrainingCourseStudent> tlist1 = this.getGeneralService().getList(dc1);
						if (tlist1.size() > 0) {
							map.clear();
							map.put("success", "false");
							map.put("info", "该课程有学员开始学习,无法删除!");
							return map;
						} else {
							// String sql = "delete from stuttime where
							// fk_open_course_id in ("+id+")";
							StringBuffer buf = new StringBuffer();
							String[] cids = this.getIds().split(",");
							for (int i = 0; i < cids.length; i++) {
								cids[i] = "'" + cids[i] + "'";
								buf.append(cids[i]).append(",");
							}
							buf.deleteCharAt(buf.length() - 1);
							// System.out.println(buf.toString());
							String sql = "delete from stuttime\n" + " where fk_open_course_id in\n" + "       (select s.id\n"
									+ "          from pr_bzz_tch_opencourse s\n" + "         where s.fk_batch_id='" + id + "'\n"
									+ "           and s.fk_course_id in (" + buf.toString() + "))";
							int rett = this.getGeneralService().executeBySQL(sql);
						}
					} catch (EntityException e) {
						e.printStackTrace();
					}

					DetachedCriteria dcEletive = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
					dcEletive.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzBatch", "peBzzBatch")
							.createAlias("peBzzTchCourse", "peBzzTchCourse");
					dcEletive.add(Restrictions.eq("peBzzBatch.id", id));
					dcEletive.add(Restrictions.in("peBzzTchCourse.id", ids));
					dcEletive.setProjection(Projections.distinct(Projections.property("id")));
					List electiveIdList = this.getGeneralService().getList(dcEletive);

					// 删除back数据
					this.getPrBzzTchOpenCourseService().deleteElectiveBackByIds(electiveIdList);

					// 获取开课id列表
					DetachedCriteria dcOpencourse = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
					dcOpencourse.add(Restrictions.eq("peBzzBatch.id", id));
					dcOpencourse.add(Restrictions.in("peBzzTchCourse.id", ids));
					dcOpencourse.setProjection(Projections.distinct(Projections.property("id")));
					List opencourseIdList = this.getGeneralService().getList(dcOpencourse);

					this.getPrBzzTchOpenCourseService().deleteOpencourseByIds(opencourseIdList);
					msg = "从专项中删除";
				} else if (action.equals("ChooseCourseDel")) {

					// 判断当前时间是否在专项时间段
					if (peBzzBatchlist.get(0).getStartDate().before(new Date()) && peBzzBatchlist.get(0).getEndDate().after(new Date())) {
						map.clear();
						map.put("success", "false");
						map.put("info", "专项已经开始，无法删除课程");
						return map;
					}

					// 先判断所删除课程是否已经有学员开始学习。如果没有才可以进行删除
					DetachedCriteria dc1 = DetachedCriteria.forClass(TrainingCourseStudent.class);
					dc1.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
					dc1.add(Restrictions.gt("percent", Double.parseDouble("0")));
					dc1.add(Restrictions.in("peBzzTchCourse.id", ids));
					dc1.add(Restrictions.eq("prBzzTchOpencourse.peBzzBatch", peBzzBatchlist.get(0)));

					try {

						List<TrainingCourseStudent> tlist1 = this.getGeneralService().getList(dc1);
						if (tlist1.size() > 0) {
							map.clear();
							map.put("success", "false");
							map.put("info", "该课程有学员开始学习,无法删除!");
							return map;
						} else {
							// String sql = "delete from stuttime where
							// fk_open_course_id in ("+id+")";
							StringBuffer buf = new StringBuffer();
							String[] cids = this.getIds().split(",");
							for (int i = 0; i < cids.length; i++) {
								cids[i] = "'" + cids[i] + "'";
								buf.append(cids[i]).append(",");
							}
							buf.deleteCharAt(buf.length() - 1);
							// System.out.println(buf.toString());
							String sql = "delete from stuttime\n" + " where fk_open_course_id in\n" + "       (select s.id\n"
									+ "          from pr_bzz_tch_opencourse s\n" + "         where s.fk_batch_id='" + id + "'\n"
									+ "           and s.fk_course_id in (" + buf.toString() + "))";
							int rett = this.getGeneralService().executeBySQL(sql);
						}
					} catch (EntityException e) {
						e.printStackTrace();
					}

					DetachedCriteria dcEletive = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
					dcEletive.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzBatch", "peBzzBatch")
							.createAlias("peBzzTchCourse", "peBzzTchCourse");
					dcEletive.add(Restrictions.eq("peBzzBatch.id", id));
					dcEletive.add(Restrictions.in("peBzzTchCourse.id", ids));
					dcEletive.setProjection(Projections.distinct(Projections.property("id")));
					List electiveIdList = this.getGeneralService().getList(dcEletive);

					// 删除back数据
					this.getPrBzzTchOpenCourseService().deleteElectiveBackByIds(electiveIdList);

					// 获取开课id列表
					DetachedCriteria dcOpencourse = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
					dcOpencourse.add(Restrictions.eq("peBzzBatch.id", id));
					dcOpencourse.add(Restrictions.in("peBzzTchCourse.id", ids));
					dcOpencourse.setProjection(Projections.distinct(Projections.property("id")));
					List opencourseIdList = this.getGeneralService().getList(dcOpencourse);

					this.getPrBzzTchOpenCourseService().deleteOpencourseByIds(opencourseIdList);
					msg = "从专项中删除";

				}
				map.put("success", "true");
				map.put("info", msg + "共有" + ids.length + "条记录操作成功");
			} catch (Exception e) {
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
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

	private String sql_con() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		String sql_con = "";
		if (us.getUserLoginType().equals("2") || us.getRoleId().equals("402880a92137be1c012137db62100006")) {
			sql_con = "     and ps.fk_enterprise_id in (select p1.id\n" + "   from pe_enterprise p, pe_enterprise p1\n"
					+ "   where p.fk_parent_id is null\n" + "   and p1.fk_parent_id = p.id\n" + "   and p.id = '"
					+ us.getPriEnterprises().get(0).getId() + "'\n" + "   union\n" + "   select p.id from pe_enterprise p where p.id = '"
					+ us.getPriEnterprises().get(0).getId() + "')";
		} else if (us.getRoleId().equals("402880f322736b760122737a60c40008") || us.getRoleId().equals("402880f322736b760122737a968a0010")) {
			sql_con = " and ps.fk_enterprise_id in (select id from pe_enterprise where id='" + us.getPriEnterprises().get(0).getId() + "')";
		}
		return sql_con;
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

	// }
}
