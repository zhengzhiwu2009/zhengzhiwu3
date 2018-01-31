package com.whaty.platform.entity.web.action.teaching.elective;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.training.basic.StudyProgress;

public class StudentCourseAction extends MyBaseAction {

	private String id;
	private PeBzzStudent peBzzStudent;
	private List list;
	private String courseId;
	private PeBzzTchCourse peBzzTchCourse;
	private List firstList;
	private List secondList;
	private List lastList;
	private Map<String, String> firstSiteMap = new HashMap<String, String>();
	private Map<String, String> secondSiteMap = new HashMap<String, String>();
	private String firstSiteId;
	private String secondSiteId;
	private List firstSiteList;
	private List secondSiteList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public PeBzzStudent getPeBzzStudent() {
		return peBzzStudent;
	}

	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public PeBzzTchCourse getPeBzzTchCourse() {
		return peBzzTchCourse;
	}

	public void setPeBzzTchCourse(PeBzzTchCourse peBzzTchCourse) {
		this.peBzzTchCourse = peBzzTchCourse;
	}

	public List getFirstList() {
		return firstList;
	}

	public void setFirstList(List firstList) {
		this.firstList = firstList;
	}

	public List getSecondList() {
		return secondList;
	}

	public void setSecondList(List secondList) {
		this.secondList = secondList;
	}

	public List getLastList() {
		return lastList;
	}

	public void setLastList(List lastList) {
		this.lastList = lastList;
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
		this.getGridConfig().setTitle("学员—课程");
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	//	if ("24".indexOf(us.getUserLoginType()) != -1) {
			this.getGridConfig().addColumn(this.getText("获得学时开始时间"), "csDate",
					true, true, false, "");
			this.getGridConfig().addColumn(this.getText("获得学时结束时间"), "ceDate",
					true, true, false, "");
		this.getGridConfig().addColumn(this.getText("姓名"), "name", true);
		this.getGridConfig().addColumn(this.getText("身份证号"), "cardNo", true);
		this.getGridConfig()
				.addColumn(this.getText("工作部门"), "department", true);
		this.getGridConfig().addColumn(this.getText("所在机构"), "peName", false);
		// this.getGridConfig().addColumn(this.getText("已报名学时"), "totalClass",
		// false);
		this.getGridConfig().addColumn(this.getText("已报名学时"), "ffClass", false);
		this.getGridConfig().addColumn(this.getText("已获得学时"), "hdClass", false);
		
			this
			.getGridConfig()
			.addRenderScript(
					this.getText("查看课程"),
					"{return '<a href=studentCourseViewCourse.action?id='+record.data['id']+'&csDate='+record.data['csDate']+'&ceDate='+record.data['ceDate']+' ><font color=#0000ff ><u>查看课程</u></font></a>';}",
					"");
		
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/teaching/studentCourse";
	}

	/**
	 * 重写框架方法：学员信息（带sql条件）
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public Page list() {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Map params = this.getParamsMap();
		String name = "";
		String peName = "";
		String cardNo = "";
		String department = "";
		String csDate = "";
		String ceDate = "";
		Iterator iterator = params.entrySet().iterator();
		do {
			if (!iterator.hasNext())
				break;
			java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
			String key = entry.getKey().toString();
			String value = ((String[]) entry.getValue())[0].toString();
			if (!key.startsWith("search__"))
				continue;
			if ("".equals(value))
				continue;
			if (key.indexOf(".") > 1
					&& key.indexOf(".") != key.lastIndexOf(".")) {
				key = key.substring(key.lastIndexOf(".",
						key.lastIndexOf(".") - 1) + 1);
			} else {
				key = key.substring(8);
			}

			if (key.equals("name")) {
				name = value;
			}
			if (key.equals("peName")) {
				peName = value;
			}
			if (key.equals("cardNo")) {
				cardNo = value;
			}
			if (key.equals("department")) {
				department = value;
			}
			if (key.equals("csDate")) {
				csDate = value;
			}
			if (key.equals("ceDate")) {
				ceDate = value;
			}
		} while (true);

		String sqlCount = " select id from pe_bzz_student pbs where 1=1 ";
		if (!StringUtils.defaultString(name).equals("")) {
			sqlCount += " and instr(name,'" + name + "')>0 ";
		}
		if (!StringUtils.defaultString(peName).equals("")) {
			sqlCount += " and instr(peName,'" + peName + "')>0 ";
		}
		if (!StringUtils.defaultString(cardNo).equals("")) {
			sqlCount += " and instr(cardNo,'" + cardNo + "')>0 ";
		}
		if (!StringUtils.defaultString(department).equals("")) {
			sqlCount += " and instr(department,'" + department + "')>0 ";
		}
		sqlCount += "  and exists ( select t.id from sso_user t where pbs.fk_sso_user_id = t.id ) ";

		if (!StringUtils.defaultString(this.getFirstSiteId()).equals("")) {
			ServletActionContext.getContext().getSession().put(
					"student_course_firstSiteId", this.getFirstSiteId());
		}
		if (!StringUtils.defaultString(this.getSecondSiteId()).equals("")) {
			ServletActionContext.getContext().getSession().put(
					"student_course_secondSiteId", this.getSecondSiteId());
		}
		String firstSiteId = (String) ServletActionContext.getContext()
				.getSession().get("student_course_firstSiteId");
		String secondSiteId = (String) ServletActionContext.getContext()
				.getSession().get("student_course_secondSiteId");

		String sqlSite = "";
		String sqlSiteAdmin = "";
		if (!secondSiteId.equals("all")) {
			sqlSite = " ( select id,fk_enterprise_id,true_name,card_no,trim(department) as department,fk_sso_user_id from pe_bzz_student where FK_ENTERPRISE_ID = '"
					+ secondSiteId + "')  ";
			sqlSiteAdmin = sqlSite;
		} else {
			sqlSite = " pe_bzz_student ";
			// sqlSiteAdmin = " ( select
			// id,fk_enterprise_id,true_name,card_no,department,fk_sso_user_id
			// from pe_bzz_student where id = '"+ firstSiteId +"')";
			sqlSiteAdmin = " ( select pbs.id,fk_enterprise_id,true_name,card_no,department,fk_sso_user_id from pe_bzz_student pbs "
					+ " where exists ( select a.id from ( select pe.id, pe.name "
					+ " from pe_enterprise         pe, "
					+ " pe_enterprise         pe_parent "
					+ " where pe_parent.id = pe.fk_parent_id and pe_parent.id = '"
					+ firstSiteId
					+ "' "
					+ " union select pe.id, pe.name  from pe_enterprise pe where pe.id = '"
					+ firstSiteId
					+ "' ) a where a.id = pbs.fk_enterprise_id ) )  ";
		}
		String managerId = us.getLoginId();
		StringBuffer sbSql = new StringBuffer();
		// String sql =

		sbSql.append("			select * from (select t1.id as id,");
			if (!StringUtils.defaultString(csDate).equals("")) {
				sbSql.append("TO_DATE('" + csDate + "','yyyy-MM-dd') as csDate, \n		");
			} else {
				sbSql.append(" '' as csDate, \n		");
			}
			if (!StringUtils.defaultString(ceDate).equals("")) {
				sbSql.append("TO_DATE('" + ceDate + "','yyyy-MM-dd') as ceDate, \n		");
			} else {
				sbSql.append(" '' as ceDate, \n		");
			}
		
		
		sbSql.append("					t1.name as name,\n						");
		sbSql.append("					t1.cardNo as cardNo,\n					");
		sbSql.append("					trim(t1.department) as department,\n	");
		sbSql.append("					t4.name as peName,\n					");
		sbSql.append("					-- t1.totalClass as totalClass,\n		");
		sbSql.append("					nvl(t2.ffClass, '0') as ffClass,\n		");
		sbSql.append("					nvl(t3.hdClass, '0') as hdClass\n		");
		sbSql.append("			from (select stu.id as id,\n					");
		sbSql.append("						stu.true_name as name,\n			");
		sbSql.append("						stu.card_no as cardNo,\n			");
		sbSql.append("						stu.department as department,\n		");
		sbSql.append("						stu.fk_enterprise_id as enid,\n		");
		sbSql
				.append("						sum(to_number(nvl(c1.time, '0'))) as totalClass\n	");
		sbSql.append("			\n												");
		sbSql.append("					from " + sqlSite + " stu\n				");
		sbSql.append("			\n												");
		sbSql.append("					inner join sso_user u on u.login_id = '" + managerId
				+ "'\n	");
		sbSql
				.append("					inner join pe_enterprise_manager man on man.fk_sso_user_id = u.id\n	");
		sbSql
				.append("					inner join pe_enterprise en on (en.id = man.fk_enterprise_id		");
		// "or\n" +
		// " en.FK_PARENT_ID =\n" +
		// " man.fk_enterprise_id" +
		if (!secondSiteId.equals("all")) {
			sbSql.append("					or\n		");
			sbSql.append("					en.FK_PARENT_ID = man.fk_enterprise_id		");
		}
		sbSql.append("			)\n												");
		sbSql
				.append("					left join PR_BZZ_TCH_STU_ELECTIVE_back total on total.fk_stu_id = stu.id\n	");
		sbSql
				.append("					left join pr_bzz_tch_opencourse op1 on op1.id = total.fk_tch_opencourse_id\n  ");
		sbSql
				.append("					left join pe_bzz_tch_course c1 on c1.id = op1.fk_course_id\n	");
		sbSql.append("			\n			");
		sbSql.append("					where stu.fk_enterprise_id = en.id\n	");
		sbSql.append("			\n												");
		sbSql.append("					group by stu.id,\n						");
		sbSql.append("							 stu.true_name,\n				");
		sbSql.append("							 stu.card_no,\n					");
		sbSql.append("							 stu.department,\n				");
		sbSql.append("							 stu.fk_enterprise_id) t1\n		");
		sbSql.append("				left join (select stu.id as id,\n			");
		sbSql.append("			\n						");
		sbSql
				.append("								  sum(to_number(nvl(c1.time, '0'))) as ffClass\n	 ");
		sbSql.append("			\n												");
		sbSql.append("							from " + sqlSite + " stu\n");
		sbSql.append("							inner join sso_user u on u.login_id = '"
				+ managerId + "'\n	 ");
		sbSql
				.append("							inner join pe_enterprise_manager man on man.fk_sso_user_id = u.id\n	");
		sbSql
				.append("							inner join pe_enterprise en on (en.id = man.fk_enterprise_id		");
		if (!secondSiteId.equals("all")) {
			sbSql.append("					or\n		");
			sbSql.append("					en.FK_PARENT_ID = man.fk_enterprise_id		");
		}
		sbSql.append("			)\n				");
		sbSql
				.append("						  left join PR_BZZ_TCH_STU_ELECTIVE total on total.fk_stu_id = stu.id\n	");
		sbSql
				.append("						 inner join pr_bzz_tch_opencourse op1 on op1.id = total.fk_tch_opencourse_id\n	");
		sbSql
				.append("						 inner join pe_bzz_tch_course c1 on c1.id = op1.fk_course_id\n	");
		sbSql.append("			\n				");
		sbSql
				.append("						 left join enum_const ec1 on total.flag_elective_pay_status = ec1.id\n	");
		sbSql.append("			\n				");
		sbSql.append("						 where stu.fk_enterprise_id = en.id\n	");
		if (!StringUtils.defaultString(csDate).equals("")) {
			sbSql.append("					and \n		");
			sbSql.append("					to_char(total.completed_time,'yyyy-mm-dd')>= '" + csDate + "'	");
		}
		if (!StringUtils.defaultString(ceDate).equals("")) {
			sbSql.append("					and \n		");
			sbSql.append("					to_char(total.completed_time,'yyyy-mm-dd') <= '" + ceDate + "'	");
		}
		sbSql.append("			\n				");
		sbSql
				.append("						 group by stu.id, stu.true_name, stu.card_no, stu.department) t2 on t1.id =	t2.id\n ");
		sbSql.append("			left join (\n				");
		sbSql.append("			\n				");
		sbSql.append("						select stu.id as id,\n	");
		sbSql.append("			\n				");
		sbSql.append("								sum(to_number(nvl(c.time, '0'))) as hdClass\n	");
		sbSql.append("			\n				");
		sbSql.append("						 from " + sqlSite + " stu\n	");
		sbSql.append("						inner join sso_user u on u.login_id = '"
				+ managerId + "'\n	");
		sbSql
				.append("						inner join pe_enterprise_manager man on man.fk_sso_user_id = u.id\n	");
		sbSql
				.append("						inner join pe_enterprise en on (en.id = man.fk_enterprise_id	");
		if (!secondSiteId.equals("all")) {
			sbSql.append("					or\n		");
			sbSql.append("					en.FK_PARENT_ID = man.fk_enterprise_id		");
		}
		sbSql.append("			)\n				");
		sbSql.append("			\n				");
		sbSql
				.append("						inner join pr_bzz_tch_stu_elective pbtse on pbtse.fk_stu_id = stu.id\n				");
		sbSql.append("			\n				");
		sbSql
				.append("						inner join pr_bzz_tch_opencourse op on op.id = pbtse.fk_tch_opencourse_id\n	");
		sbSql
				.append("						inner join pe_bzz_tch_course c on c.id = op.fk_course_id\n	");
		sbSql.append("			\n				");
		sbSql.append("							");
		sbSql
				.append("						where /*pbtse.score_exam >= c.passrole*/pbtse.completed_time is not null\n	");
		sbSql.append("							and stu.fk_enterprise_id = en.id\n	");
		if (!StringUtils.defaultString(csDate).equals("")) {
			sbSql.append("					and \n		");
			sbSql.append("					to_char(pbtse.completed_time,'yyyy-mm-dd') >= '" + csDate + "'	");
		}
		if (!StringUtils.defaultString(ceDate).equals("")) {
			sbSql.append("					and \n		");
			sbSql.append("					to_char(pbtse.completed_time,'yyyy-mm-dd') <= '" + ceDate + "'	");
		}
		sbSql.append("						group by stu.id\n	");
		sbSql.append("			\n				");
		sbSql.append("						) t3 on t3.id = t1.id\n	");
		sbSql.append("			inner join (\n				");
		sbSql.append("			\n				");
		sbSql.append("						select pe.id as id, pe.name as name\n	");
		sbSql.append("			\n				");
		sbSql.append("						from pe_enterprise pe\n	");
		sbSql
				.append("						inner join pe_enterprise_manager pem on pem.fk_enterprise_id = pe.id\n	");
		sbSql.append("						where pem.login_id = '" + managerId + "'\n	");
		sbSql.append("			\n				");
		if (!secondSiteId.equals("all")) {
			sbSql.append("     union		");
			sbSql.append("     	   select pe.id as id, pe.name as name		");
			sbSql.append("            from pe_enterprise         pe,		");
			sbSql.append("                 pe_enterprise         pePar,		");
			sbSql.append("                 pe_enterprise_manager pem		");
			sbSql.append("           where pe.fk_parent_id = pePar.Id		");
			sbSql.append("             and pePar.Id = pem.fk_enterprise_id	");
			sbSql.append("             and pem.login_id = '" + managerId
					+ "'		");
		}
		sbSql.append("	) t4 on t4.id = t1.enid) where 1=1						");
		if (!StringUtils.defaultString(name).equals("")) {
			sbSql.append("  and  name like 	'" + name + "'");
		}
		if (!StringUtils.defaultString(cardNo).equals("")) {
			sbSql.append("  and  cardNo like  '" + cardNo + "'");
		}
		if (!StringUtils.defaultString(department).equals("")) {
			sbSql.append("  and  department like '" + department + "'");
		}
		String sql = sbSql.toString();
			/**
			 * 当选择二级机构时，忽略一级机构
			 */
			if (!secondSiteId.equals("all")) {
				this.firstSiteId = secondSiteId;
			}
			/**
			 * 查询到所有的机构编号
			 */
			String peIdsSql = "select pe.id as id\n"
					+ "\n"
					+ "                                     from pe_enterprise pe\n"
					+ "\n"
					+ "                                    where pe.id = '"
					+ this.getFirstSiteId() + "'\n";
			// 按一级机构查询时，不显示2级机构；
			// +
			// " union\n" +
			// "\n" +
			// " select pe.id as id\n" +
			// " from pe_enterprise pe,\n" +
			// " pe_enterprise pePar\n" +
			// "\n" +
			// " where pe.fk_parent_id = pePar.Id\n" +
			// "\n" +
			// " and pePar.id = '"+this.getFirstSiteId()+"'";
			List list = null;
			try {
				list = this.getGeneralService().getBySQL(peIdsSql);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String peIds = "";
			for (int i = 0; i < list.size(); i++) {
				peIds += "'" + list.get(i).toString() + "',";
			}
			if (list.size() > 0) {
				peIds = peIds.substring(0, peIds.length() - 1);
			}
			sql =

			"select * from (select t1.id as id,";
				if (!StringUtils.defaultString(csDate).equals("")) {
					sql += "TO_DATE('" + csDate + "','yyyy-MM-dd') as csDate, \n		";
				} else {
					sql += " '' as csDate, \n		";
				}
				if (!StringUtils.defaultString(ceDate).equals("")) {
					sql += "TO_DATE('" + ceDate + "','yyyy-MM-dd') as ceDate, \n		";
				} else {
					sql += " '' as ceDate, \n		";
				}
			
			sql += "       t1.name as name,\n"
					+ "       t1.cardNo as cardNo,\n"
					+ "       trim(t1.department) as department,\n"
					+ "       t4.name as peName,\n"
					+ "       -- t1.totalClass as totalClass,\n"
					+ "       nvl(t2.ffClass, '0') as ffClass,\n"
					+ "       nvl(t3.hdClass, '0') as hdClass\n"
					+ "  from (select stu.id as id,\n"
					+ "               stu.true_name as name,\n"
					+ "               stu.card_no as cardNo,\n"
					+ "               stu.department as department,\n"
					+ "               stu.fk_enterprise_id as enid,\n"
					+ "               sum(to_number(nvl(c1.time, '0'))) as totalClass\n"
					+ "\n"
					+ "          from pe_bzz_student stu\n"
					+ "\n"
					+ "          left join PR_BZZ_TCH_STU_ELECTIVE_back total on total.fk_stu_id =\n"
					+ "                                                          stu.id\n"
					+ "          left join pr_bzz_tch_opencourse op1 on op1.id =\n"
					+ "                                                 total.fk_tch_opencourse_id\n"
					+ "          left join pe_bzz_tch_course c1 on c1.id = op1.fk_course_id\n"
					+ "\n" + "          where stu.fk_enterprise_id in ("
					+ peIds
					+ ")\n"
					+ "\n"
					+ "         group by stu.id,\n"
					+ "                  stu.true_name,\n"
					+ "                  stu.card_no,\n"
					+ "                  stu.department,\n"
					+ "                  stu.fk_enterprise_id) t1\n"
					+ "  left join (select stu.id as id,\n"
					+ "\n"
					+ "                    sum(to_number(nvl(c1.time, '0'))) as ffClass\n"
					+ "\n"
					+ "               from pe_bzz_student stu\n"
					+ "\n"
					+ "               left join PR_BZZ_TCH_STU_ELECTIVE total on total.fk_stu_id =\n"
					+ "                                                          stu.id\n"
					+ "              inner join pr_bzz_tch_opencourse op1 on op1.id =\n"
					+ "                                                      total.fk_tch_opencourse_id\n"
					+ "              inner join pe_bzz_tch_course c1 on c1.id = op1.fk_course_id\n"
					+ "\n"
					+ "              left join enum_const ec1 on total.flag_elective_pay_status =\n"
					+ "                                           ec1.id\n"
					+ "\n"
					+ "             where stu.fk_enterprise_id in ("
					+ peIds + ")\n";
			if (!StringUtils.defaultString(csDate).equals("")) {
				sql += "					and \n		";
				sql += "					to_char(total.completed_time,'yyyy-mm-dd') >= '" + csDate + "'	";
			}
			if (!StringUtils.defaultString(ceDate).equals("")) {
				sql += ("					and \n		");
				sql += ("					to_char(total.completed_time,'yyyy-mm-dd') <= '" + ceDate + "'	");
			}
			sql += "              group by stu.id, stu.true_name, stu.card_no, stu.department) t2 on t1.id =\n"
					+ "                                                                                 t2.id\n"
					+ "  left join (\n"
					+ "\n"
					+ "             select stu.id as id,\n"
					+ "\n"
					+ "                     sum(to_number(nvl(c.time, '0'))) as hdClass\n"
					+ "\n"
					+ "               from pe_bzz_student stu\n"
					+ "\n"
					+ "              inner join pr_bzz_tch_stu_elective pbtse on pbtse.fk_stu_id =\n"
					+ "                                                          stu.id\n"
					+ "\n"
					+ "              inner join pr_bzz_tch_opencourse op on op.id =\n"
					+ "                                                     pbtse.fk_tch_opencourse_id\n"
					+ "              inner join pe_bzz_tch_course c on c.id = op.fk_course_id\n"
					+ "\n"
					+ "              where pbtse.completed_time is not null\n"
					+ "             and stu.fk_enterprise_id in ("
					+ peIds
					+ ")\n";
			if (!StringUtils.defaultString(csDate).equals("")) {
				sql += "					and \n		";
				sql += "					to_char(pbtse.completed_time,'yyyy-mm-dd')>= '" + csDate + "'	";
			}
			if (!StringUtils.defaultString(ceDate).equals("")) {
				sql += ("					and \n		");
				sql += ("					to_char(pbtse.completed_time,'yyyy-mm-dd') <= '" + ceDate + "'	");
			}
			sql += "              group by stu.id\n"
					+ "\n"
					+ "             ) t3 on t3.id = t1.id\n"
					+ " inner join (\n"
					+ "\n"
					+ "             select pe.id as id, pe.name as name\n"
					+ "\n"
					+ "               from pe_enterprise pe\n"
					+ "\n"
					+ "              where pe.id = '"
					+ this.getFirstSiteId()
					+ "'\n"
					+ "             union\n"
					+ "\n"
					+ "             select pe.id as id, pe.name as name\n"
					+ "               from pe_enterprise pe, pe_enterprise pePar\n"
					+ "\n"
					+ "              where pe.fk_parent_id = pePar.Id\n"
					+ "                   --and pePar.Id = pem.fk_enterprise_id\n"
					+ "                and pePar.id = '"
					+ this.getFirstSiteId() + "') t4 on t4.id = t1.enid" +

					") where 1 = 1";
			if (!StringUtils.defaultString(name).equals("")) {
				sql += "  and  name like 	'" + name + "'";
			}
			if (!StringUtils.defaultString(cardNo).equals("")) {
				sql += "  and  cardNo like  '" + cardNo + "'";
			}
			if (!StringUtils.defaultString(department).equals("")) {
				sql += "  and  department like '" + department + "'";
			}


		// String sql =
		// "\n" +
		// "select * from (select t1.id as id,\n" +
		// " t1.name as name,\n" +
		// " t1.cardNo as cardNo,\n" +
		// " trim(t1.department) as department,\n" +
		// " t1.peName as peName,\n" +
		// " t1.totalClass as totalClass,\n" +
		// " nvl(t2.ffClass,'0') as ffClass,\n" +
		// " nvl(t3.hdClass,'0') as hdClass\n" +
		// " from (select stu.id as id,\n" +
		// " stu.true_name as name,\n" +
		// " stu.card_no as cardNo,\n" +
		// " stu.department as department,\n" +
		// " en.name as peName,\n" +
		// " sum(to_number(nvl(c1.time, '0'))) as totalClass\n" +
		// "\n" +
		// " from " + sqlSite + " stu\n" +
		// " inner join sso_user u on u.login_id = '"+managerId+"'\n" +
		// " inner join pe_enterprise_manager man on man.fk_sso_user_id =
		// u.id\n" +
		// " inner join pe_enterprise en on ( en.id = man.fk_enterprise_id or
		// en.FK_PARENT_ID = man.fk_enterprise_id )\n" +
		// " left join PR_BZZ_TCH_STU_ELECTIVE total on total.fk_stu_id =\n" +
		// " stu.id\n" +
		// " left join pr_bzz_tch_opencourse op1 on op1.id =\n" +
		// " total.fk_tch_opencourse_id\n" +
		// " left join pe_bzz_tch_course c1 on c1.id = op1.fk_course_id\n" +
		// "\n" +
		// " where stu.fk_enterprise_id = en.id\n" +
		// "\n" +
		// " group by stu.id,\n" +
		// " stu.true_name,\n" +
		// " stu.card_no,\n" +
		// " stu.department,\n" +
		// " en.name\n" +
		// " ) t1\n" +
		// " left join (select stu.id as id,\n" +
		// "\n" +
		// " sum(to_number(nvl(c1.time, '0'))) as ffClass\n" +
		// "\n" +
		// " from " + sqlSite + " stu\n" +
		// " inner join sso_user u on u.login_id = '"+managerId+"'\n" +
		// " inner join pe_enterprise_manager man on man.fk_sso_user_id =
		// u.id\n" +
		// " inner join pe_enterprise en on ( en.id = man.fk_enterprise_id or
		// en.FK_PARENT_ID = man.fk_enterprise_id )\n" +
		// " left join PR_BZZ_TCH_STU_ELECTIVE total on total.fk_stu_id =\n" +
		// " stu.id\n" +
		// " inner join pr_bzz_tch_opencourse op1 on op1.id =\n" +
		// " total.fk_tch_opencourse_id\n" +
		// " inner join pe_bzz_tch_course c1 on c1.id = op1.fk_course_id\n" +
		// "\n" +
		// " inner join enum_const ec1 on total.flag_elective_pay_status =\n" +
		// " ec1.id\n" +
		// "\n" +
		// " where stu.fk_enterprise_id = en.id\n" +
		// " and ec1.namespace = 'FlagElectivePayStatus'\n" +
		// " and ec1.code = '1'\n" +
		// " group by stu.id,\n" +
		// " stu.true_name,\n" +
		// " stu.card_no,\n" +
		// " stu.department\n" +
		// " ) t2 on t1.id = t2.id\n" +
		// " left join (select stu.id as id,\n" +
		// "\n" +
		// " sum(to_number(nvl(c.time, '0'))) as hdClass\n" +
		// "\n" +
		// " from " + sqlSite + " stu\n" +
		// " inner join sso_user u on u.login_id = '"+managerId+"'\n" +
		// " inner join pe_enterprise_manager man on man.fk_sso_user_id =
		// u.id\n" +
		// " inner join pe_enterprise en on ( en.id = man.fk_enterprise_id or
		// en.FK_PARENT_ID = man.fk_enterprise_id )\n" +
		// "\n" +
		// " left join training_course_student tr on tr.learn_status =\n" +
		// " '"+StudyProgress.COMPLETED+"'\n" +
		// " inner join pr_bzz_tch_opencourse op on op.id = tr.course_id\n" +
		// " inner join pe_bzz_tch_course c on c.id = op.fk_course_id\n" +
		// " inner join (select stuel.score_exam, stuel.fk_tch_opencourse_id\n"
		// +
		// " from pr_bzz_tch_stu_elective stuel, pe_bzz_student stu\n" +
		// " where stu.FK_ENTERPRISE_ID = '"+ secondSiteId +"' and
		// stuel.fk_stu_id =
		// stu.id) ele on ele.fk_tch_opencourse_id=op.id\n "+
		// " where stu.fk_enterprise_id = en.id\n" +
		// " and tr.student_id = stu.fk_sso_user_id\n" +
		// " and ele.score_exam>=c.passrole\n "+
		// "\n" +
		// " group by stu.id, stu.true_name, stu.card_no, stu.department) t3 on
		// t1.id
		// =\n" +
		// " t3.id) where 1 = 1 ";

		// StringBuffer sbSql = new StringBuffer();
		// if(us.getUserLoginType().equals("3")) {
		// sbSql.append(" select * from ( select a.id as id , ");
		// sbSql.append(" a.true_name as name, ");
		// sbSql.append(" a.card_no as cardNo, ");
		// sbSql.append(" trim(a.department) as department, ");
		// sbSql.append(" p.name as peName, ");
		// // sbSql.append(" nvl(c.time1, '0') as totalClass, ");
		// sbSql.append(" nvl(c.time2, '0') as ffClass, ");
		// sbSql.append(" nvl(d.completedtime, '0') as hdClass ");
		// sbSql.append(" from " + sqlSiteAdmin + " ");
		// sbSql.append(" a, ");
		// sbSql.append(" ( select c1.fk_stu_id, ");
		// sbSql.append(" sum( to_number(nvl(c3.time, 0))) as time1, ");
		// sbSql.append(" sum( case ");
		// sbSql.append(" when c1.flag_elective_pay_status = ");
		// sbSql.append(" '40288a7b3981661e01398186b0f50006' then ");
		// sbSql.append(" to_number(nvl(c3.time, 0)) ");
		// sbSql.append(" else ");
		// sbSql.append(" 0 ");
		// sbSql.append(" end ) as time2 ");
		// sbSql.append(" from PR_BZZ_TCH_STU_ELECTIVE c1, ");
		// sbSql.append(" pr_bzz_tch_opencourse c2, ");
		// sbSql.append(" pe_bzz_tch_course c3 ");
		// sbSql.append(" where c2.id = c1.fk_tch_opencourse_id ");
		// sbSql.append(" and c3.id = c2.fk_course_id ");
		// sbSql.append(" group by c1.fk_stu_id ) c, ");
		// sbSql.append(" (select t1.student_id,sum(case when t1.learn_status =
		// '"+StudyProgress.COMPLETED+"' ");
		// sbSql.append(" then to_number(nvl(t3.time, '0')) else 0 end) as
		// completedtime
		// ");
		// sbSql.append(" from training_course_student t1,pr_bzz_tch_opencourse
		// t2,pe_bzz_tch_course t3,");
		// sbSql.append(" " + sqlSiteAdmin + " t4 where t1.student_id =
		// t4.fk_sso_user_id and ");
		// sbSql.append(" t1.course_id = t2.id and t2.fk_course_id = t3.id group
		// by
		// t1.student_id) d ");
		// sbSql.append(" ,pe_enterprise p ");
		// sbSql.append(" where a.id = c.fk_stu_id(+) and a.id = d.student_id(+)
		// and
		// a.fk_enterprise_id=p.id ) where 1=1 ");
		//				 
		// sql = sbSql.toString();
		//				 
		// }
		//			
		StringBuffer sqlBuffer = new StringBuffer(sql);
		// this.setSqlCondition(sqlBuffer);
		Page page = null;
		try {
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 查看课程
	 * 
	 * @author linjie
	 * @return
	 */
	public String viewCourse() {
		this.getStudentCourseInfo();
		this.getStudent();

		return "courseSearch";
	}

	/**
	 * 获得学生课程信息
	 * 
	 * @author linjie
	 * @return
	 */
	public List getStudentCourseInfo() {
		String sql =

		"select c.id as id,\n"
				+ "       c.code as code,\n"
				+ "       c.name as name,\n"
				+ "       c.time as time,\n"
				+ "       ec.name as courseType,\n"
				+ "       o.payment_date as paymentDate,\n"
				+ "       t.get_date as getDate,\n"
				+ "       t.learn_status as learnStatus,\n"
				+ "		t.score as score,\n"
				+ "       isv.name as isValid\n"
				+ "  from PR_BZZ_TCH_STU_ELECTIVE ele\n"
				+ " inner join pr_bzz_tch_opencourse op on ele.fk_tch_opencourse_id = op.id\n"
				+ " inner join pe_bzz_tch_course c on op.fk_course_id = c.id\n"
				+ "  left join enum_const ec on c.flag_coursetype = ec.id\n"
				+ "  left join pe_bzz_order_info o on ele.fk_order_id = o.id\n"
				+ "  left join training_course_student t on ele.fk_training_id = t.id\n"
				+ "  left join enum_const isv on c.flag_isvalid = isv.id\n"
				+ "\n" + " where ele.fk_stu_id = '" + this.getId() + "'";

		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获得学生信息
	 * 
	 * @author linjie
	 * @return
	 */
	public void getStudent() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
		dc.createCriteria("ssoUser", "ssoUser");
		dc.add(Restrictions.eq("id", this.getId()));
		try {
			peBzzStudent = (PeBzzStudent) this.getGeneralService().getList(dc)
					.get(0);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据页面table获得第一个table的列表信息
	 * 
	 * @author linjie
	 * @return
	 */
	public void getFirstTable() {
		String passLine = "80";
		EnumConst ec = this.getGeneralService().getGeneralDao()
				.getEnumConstByNamespaceCode("FlagTestPassLine", "0");
		if (ec != null) {
			passLine = ec.getName();
		}
		StringBuffer sqlBuff = new StringBuffer();
		/*
		 * String sql =
		 * 
		 * "select c.id,\n" + " c.name,\n" + " c.code,\n" + " ec.name as x,\n" + "
		 * c.time,\n" + " to_char(o.payment_date, 'YYYY-MM-DD HH24:MI:SS'),\n" + "
		 * to_char(ssc.first_date, 'YYYY-MM-DD HH24:MI:SS'), \n" + "
		 * to_char(ssc.last_date, 'YYYY-MM-DD HH24:MI:SS'), \n" + "
		 * to_char(c.end_date, 'YYYY-MM-DD HH24:MI:SS'),\n" + "
		 * tr.learn_status,\n" + " tr.score"+ " from pe_bzz_tch_course c\n" +
		 * "\n" + " left join scorm_stu_course ssc on c.code = ssc.course_id\n" + "
		 * left join enum_const ec on c.flag_coursetype = ec.id\n" + " left join
		 * pe_bzz_student stu on stu.id = ssc.id\n" + " left join
		 * pe_bzz_order_info o on o.create_user = stu.fk_sso_user_id\n" + " left
		 * join training_course_student tr on tr.student_id =\n" + "
		 * stu.fk_sso_user_id\n" + " where c.id = '"+this.getCourseId()+"'";
		 */
		this.getStudent();
		sqlBuff
				.append("	select distinct pbtc.id,pbtc.name,pbtc.code,ec.name as coursetype,pbtc.time,to_char(pboi.payment_date, 'YYYY-MM-DD HH24:MI:SS'),to_char(tcs.get_date, 'YYYY-MM-DD HH24:MI:SS'),to_char(tcs.complete_date, 'YYYY-MM-DD HH24:MI:SS'),pbtc.studydates,tcs.learn_status,  										");
		sqlBuff
				.append("  case when (tcs.learn_status = 'COMPLETED' and pbtse.score_exam >= "
						+ passLine
						+ ") or pbtse.completed_time is not null then 'PASSED'                                             ");
		sqlBuff
				.append("  when tcs.learn_status = 'COMPLETED' and pbtse.score_exam < "
						+ passLine
						+ " then 'FAILED'                                                   ");
		sqlBuff
				.append("  else 'UNFINISHED' end as examstatus                                                                                           ");
		sqlBuff
				.append("  from                                                                                                                          ");
		sqlBuff
				.append("  pe_bzz_tch_course pbtc,                                                                                                       ");
		sqlBuff
				.append("  ( select distinct LEARN_STATUS ,COURSE_ID,get_date,complete_date from training_course_student where STUDENT_ID = '"
						+ this.getPeBzzStudent().getSsoUser().getId()
						+ "' ) tcs,                 ");
		sqlBuff
				.append("   ( select id,FK_COURSE_ID from pr_bzz_tch_opencourse where fk_course_id = '"
						+ this.getCourseId()
						+ "') pbto,                                     ");
		sqlBuff
				.append("   ( select SCORE_EXAM,FK_ORDER_ID,FK_TCH_OPENCOURSE_ID,completed_time from  PR_BZZ_TCH_STU_ELECTIVE  where FK_STU_ID = '"
						+ this.getPeBzzStudent().getId() + "' ) pbtse,  ");
		sqlBuff
				.append("   pe_bzz_order_info pboi,                                                                                                      ");
		sqlBuff
				.append("   enum_const ec,                                                                                                               ");
		sqlBuff
				.append("   ( select student_id,last_date,course_id from scorm_stu_course where course_id = '"
						+ this.getCourseId()
						+ "' and student_id = '"
						+ this.getPeBzzStudent().getId() + "' ) ssc ");
		sqlBuff
				.append("   where pbto.id = tcs.COURSE_ID                                                                                                ");
		sqlBuff
				.append("   and pbtc.id = pbto.FK_COURSE_ID(+)                                                                                           ");
		sqlBuff
				.append("   and pbtse.fk_order_id = pboi.id(+)                                                                                           ");
		sqlBuff
				.append("   and pbtse.FK_TCH_OPENCOURSE_ID = pbto.id                                                                                     ");
		sqlBuff
				.append("   and ec.id = pbtc.flag_coursetype                                                                                             ");
		sqlBuff
				.append("   and ssc.course_id(+) = pbtc.id                                                                                               ");

		try {
			firstList = this.getGeneralService().getBySQL(sqlBuff.toString());
		} catch (EntityException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据页面table获得第二个table的列表信息
	 * 
	 * @author linjie
	 * @return
	 */
	public void getSecondTable() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzTchCourse.class);
		dc.add(Restrictions.eq("id", this.getCourseId()));
		this.getStudent();
		try {
			peBzzTchCourse = (PeBzzTchCourse) this.getGeneralService().getList(
					dc).get(0);
			String sql = "select t.total_time, t.attempt_num, to_char(t.last_date, 'YYYY-MM-DD HH24:MI:SS')\n"
					+ "  from scorm_stu_course t\n"
					+ " where t.course_id = '"
					+ peBzzTchCourse.getCode()
					+ "'\n"
					+ "   and t.student_id = '"
					+ this.peBzzStudent.getSsoUser().getId() + "'";
			secondList = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * 根据页面table获得第三个table的列表信息
	 * 
	 * @author linjie
	 * @return
	 */
	public void getLastTable() {

		String sql = "select max(pbtc.examtimes_allow) as a, max(to_number(tth.score)) as b, to_char(max(tth.test_date), 'YYYY-MM-DD HH24:MI:SS') as c\n"
				+ "  from pr_bzz_tch_stu_elective ele\n"
				+ " inner join pr_bzz_tch_opencourse pbto\n"
				+ "    on pbto.id = ele.fk_tch_opencourse_id\n"
				+ " inner join pe_bzz_tch_course pbtc\n"
				+ "    on pbtc.id = pbto.fk_course_id\n"
				+ " inner join pe_bzz_student pbs\n"
				+ "    on pbs.id = ele.fk_stu_id\n"
				+ "  inner join test_testpaper_history tth\n"
				+ "   on tth.t_user_id = pbs.id\n"
				+ "  inner join test_testpaper_info tti\n"
				+ "    on tti.id = tth.testpaper_id\n"
				+ "   and tti.group_id = '"
				+ this.peBzzTchCourse.getId()
				+ "'\n"
				+ " where ele.fk_stu_id = '"
				+ this.getPeBzzStudent().getId()
				+ "'\n"
				+ "   and pbtc.id = '"
				+ this.peBzzTchCourse.getId()
				+ "'\n"
				+ "   group by pbtc.examtimes_allow";

		try {
			lastList = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 学习详情
	 * 
	 * @author linjie
	 * @return
	 */
	public String studyDetail() {
		this.getStudent();
		this.getFirstTable();
		this.getSecondTable();
		this.getLastTable();
		return "studyDetail";
	}

	/**
	 * 是否完成
	 * 
	 * @author linjie
	 * @return
	 */
	public boolean isCompleted(String flag) {
		if (flag.equals(StudyProgress.COMPLETED)) {
			return true;
		}
		return false;
	}

	/**
	 * 按学生搜课程
	 * 
	 * @author linjie
	 * @return
	 */
	public String toStudentCourseSearch() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = us.getSsoUser();
		if (us.getUserLoginType().equals("3")) {
			String sql = " select id,'('||pe.code||')'||name from pe_enterprise pe where pe.fk_parent_id is null order by nlssort(name,'NLS_SORT=SCHINESE_PINYIN_M')";
			try {
				List<Object[]> list = (List<Object[]>) this.getGeneralService()
						.getBySQL(sql);
				this.setFirstSiteList(list);
				List<Object[]> list2 = new ArrayList<Object[]>();
				list2.add(new String[] { "all", "---全部---" });
				this.setSecondSiteList(list2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (us.getUserLoginType().equals("2")) {
			String sql = " select pe.id,'('||pe.code||')'||pe.name from pe_enterprise pe,pe_enterprise_manager pem "
					+ " where pe.id = pem.fk_enterprise_id and pem.fk_sso_user_id = '"
					+ ssoUser.getId()
					+ "' and pe.fk_parent_id is null order by nlssort(pe.name,'NLS_SORT=SCHINESE_PINYIN_M') ";
			String subSql = "  select pe.id,pe.name from pe_enterprise pe,pe_enterprise_manager pem,pe_enterprise pe_parent "
					+ "	where pe_parent.id = pem.fk_enterprise_id and pe_parent.id = pe.fk_parent_id and  pem.fk_sso_user_id = '"
					+ ssoUser.getId()
					+ "' order by nlssort(pe.name,'NLS_SORT=SCHINESE_PINYIN_M')";
			try {
				List<Object[]> list = (List<Object[]>) this.getGeneralService()
						.getBySQL(sql);
				this.setFirstSiteList(list);
				List<Object[]> list2 = (List<Object[]>) this
						.getGeneralService().getBySQL(subSql);
				list2.add(0, new String[] { "all", "---全部---" });
				this.setSecondSiteList(list2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (us.getUserLoginType().equals("4")) {
			String sql = " select pe.id,'('||pe.code||')'||pe.name from pe_enterprise pe,pe_enterprise_manager pem,pe_enterprise pe1 "
					+ " where pe1.fk_parent_id=pe.id and pe1.id = pem.fk_enterprise_id and pem.fk_sso_user_id = '"
					+ ssoUser.getId()
					+ "' and pe.fk_parent_id is null  order by nlssort(pe.name,'NLS_SORT=SCHINESE_PINYIN_M') ";
			String subSql = "  select pe.id,pe.name from pe_enterprise pe,pe_enterprise_manager pem "
					+ "	where pe.id = pem.fk_enterprise_id  and  pem.fk_sso_user_id = '"
					+ ssoUser.getId()
					+ "' order by nlssort(pe.name,'NLS_SORT=SCHINESE_PINYIN_M')";
			try {
				List<Object[]> list = (List<Object[]>) this.getGeneralService()
						.getBySQL(sql);
				this.setFirstSiteList(list);
				List<Object[]> list2 = (List<Object[]>) this
						.getGeneralService().getBySQL(subSql);
				this.setSecondSiteList(list2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "toStudentCourseSearch";
	}

	/**
	 * 子机构列表
	 * 
	 * @author linjie
	 * @return
	 */
	public String getSubEnterpriseList() {
		String sql = " select pe.id,pe.name from pe_enterprise pe where pe.fk_parent_id = '"
				+ this.getFirstSiteId()
				+ "' order by nlssort(pe.name,'NLS_SORT=SCHINESE_PINYIN_M') ";
		try {
			List<Object[]> list = (List<Object[]>) this.getGeneralService()
					.getBySQL(sql);
			StringBuffer responseText = new StringBuffer();
			responseText.append("[");
			for (int i = 0; i < list.size(); i++) {
				Object[] os = (Object[]) list.get(i);
				responseText
						.append("{'id':'" + os[0].toString() + "','name':'");
				responseText.append(os[1].toString());
				responseText.append("'");
				responseText.append("}");
				if (i < list.size() - 1) {
					responseText.append(",");
				}
			}
			responseText.append("]");
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			PrintWriter writer = response.getWriter();
			writer.print(responseText.toString());
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}

	public Map<String, String> getFirstSiteMap() {
		return firstSiteMap;
	}

	public void setFirstSiteMap(Map<String, String> firstSiteMap) {
		this.firstSiteMap = firstSiteMap;
	}

	public Map<String, String> getSecondSiteMap() {
		return secondSiteMap;
	}

	public void setSecondSiteMap(Map<String, String> secondSiteMap) {
		this.secondSiteMap = secondSiteMap;
	}

	public String getFirstSiteId() {
		return firstSiteId;
	}

	public void setFirstSiteId(String firstSiteId) {
		this.firstSiteId = firstSiteId;
	}

	public String getSecondSiteId() {
		return secondSiteId;
	}

	public void setSecondSiteId(String secondSiteId) {
		this.secondSiteId = secondSiteId;
	}

	public List getFirstSiteList() {
		return firstSiteList;
	}

	public void setFirstSiteList(List firstSiteList) {
		this.firstSiteList = firstSiteList;
	}

	public List getSecondSiteList() {
		return secondSiteList;
	}

	public void setSecondSiteList(List secondSiteList) {
		this.secondSiteList = secondSiteList;
	}

}
