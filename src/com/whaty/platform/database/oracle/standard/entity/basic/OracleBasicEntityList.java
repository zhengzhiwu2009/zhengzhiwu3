/*
 * OracleBasicDataList.java
 *
 * Created on 2005��5��7��, ����8:14
 */

package com.whaty.platform.database.oracle.standard.entity.basic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.Exception.TypeErrorException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.basic.BasicEntityList;
import com.whaty.platform.entity.basic.Site;
import com.whaty.platform.entity.basic.TeachPlanCourseType;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.util.log.Log;

/**
 * 
 * @author Administrator
 */
public class OracleBasicEntityList implements BasicEntityList {

	
	
	private String SQLDEP = "select id,name,note,forshort from entity_dep_info where id<>'0' ";

	private String SQLMAJOR = " select m.id,m.name,m.code from pe_major m where m.id<>'0' ";

	private String SQLEDUTYPE = "select id,name,code from pe_edutype  ";

	private String SQLSTUDYTYPE = "select id,name,code from pe_edutype ";

	private String SQLGRADE = "select id,name,code,begin_date from (select id,name,code,to_char(begin_date,'yyyy-mm-dd') as begin_date,begin_date as begin_dates from pe_grade) ";

	private String SQLSITE = "select id,name,code,linkman,phone,address,email,fax,found_date,manager,note,zip_code,site_url from (select id,name,code,linkman,phone,address,email,fax,to_char(found_date, 'yyyy-mm-dd') as found_date,manager,note,zip_code,site_url from pe_site)  ";

	private String SQLSITEDIQU = "select id,name,rightcode from entity_zddq_info ";

	private String SQLCLASSES = "select id,name,homepage,enounce from entity_class_info";

	private String SQLTEACHPLAN = "select id,title,major_id,grade_id,edutype_id from entity_teachplan_info";

	private String SQLEXECUTEPLAN = "select id,title,semester_id from entity_executeplan_info";

	private String SQLTEACHBOOK = "select id,teachbook_name,publishhouse,maineditor,isbn,publish_date,price,note from entity_teachbook_info";

	private String SQLPLAN_EDUTYPE = "select batch_id,site_id,site_name,edutype_id,edutype_name,major_id,major_name from (select distinct a.batch_id,c.id as site_id,c.name as site_name,b.id as edutype_id,b.name as edutype_name,d.id as major_id,d.name as major_name from recruit_plan_info a,entity_edu_type b,entity_site_info c,entity_major_info d where a.status='1' and a.edutype_id=b.id and a.site_id=c.id and a.major_id=d.id)";

	/** Creates a new instance of OracleBasicDataList */
	public OracleBasicEntityList() {
	}

	public int getDepsNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLDEP + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getDeps(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("id"));
		} else {
			OrderProperties = orderproperty;
		}
		String sql = SQLDEP
				+ Conditions.convertToAndCondition(searchproperty,
						OrderProperties);
		MyResultSet rs = null;
		ArrayList deps = null;
		try {
			db = new dbpool();
			deps = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleDep dep = new OracleDep();
				dep.setNote(rs.getString("note"));
				dep.setId(rs.getString("id"));
				dep.setName(rs.getString("name"));
				dep.setForshort(rs.getString("forshort"));
				deps.add(dep);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return deps;
	}

	public int getMajorsNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLMAJOR + Conditions.convertToAndCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getMajors(Page page, List searchproperty, List orderproperty) {
		String status = "";
		dbpool db = new dbpool();
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("m.id"));
		} else {
			OrderProperties = orderproperty;
		}
		String sql = SQLMAJOR
				+ Conditions.convertToAndCondition(searchproperty,
						OrderProperties);
		MyResultSet rs = null;
		ArrayList majors = null;
		try {
			db = new dbpool();
			majors = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleMajor major = new OracleMajor();
				major.setId(rs.getString("id"));
				major.setName(rs.getString("name"));
				major.setCode(rs.getString("code"));
//				major.setDep_id(rs.getString("dep_id"));
//				major.setDep_name(rs.getString("dep_name"));
//				major.setMajor_alias(rs.getString("major_alias"));
//				major.setMajor_link(rs.getString("major_link"));
//				major.setNote(rs.getString("note"));
//				major.setRecruit_status(rs.getString("recruit_status"));
//				status = rs.getString("status");
				major.setStatus(true);
//				if (status.equals("1"))
//					major.setStatus(true);
//				else
//					major.setStatus(false);

				majors.add(major);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return majors;
	}

	public int getEduTypesNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLEDUTYPE + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getEduTypes(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("id"));
		} else {
			OrderProperties = orderproperty;
		}
		String sql = SQLEDUTYPE
				+ Conditions
						.convertToCondition(searchproperty, OrderProperties);
		MyResultSet rs = null;
		ArrayList edutypes = null;
		try {
			db = new dbpool();
			edutypes = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleEduType edutype = new OracleEduType();
				edutype.setId(rs.getString("id"));
				edutype.setName(rs.getString("name"));
				edutype.setCode(rs.getString("code"));
//				
//				edutype.setForshort(rs.getString("forshort"));
//				edutype.setIntroduction(rs.getString("introduction"));
//				edutype.setRecruit_status(rs.getString("recruit_status"));
				edutypes.add(edutype);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return edutypes;
	}

	public int getStudyTypesNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLSTUDYTYPE
				+ Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getStudyTypes(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("id"));
		} else {
			OrderProperties = orderproperty;
		}
		String sql = SQLSTUDYTYPE
				+ Conditions
						.convertToCondition(searchproperty, OrderProperties);
		MyResultSet rs = null;
		ArrayList studytypes = null;
		try {
			db = new dbpool();
			studytypes = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleEduType edutype = new OracleEduType();
				edutype.setId(rs.getString("id"));
				edutype.setName(rs.getString("name"));
				studytypes.add(edutype);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return studytypes;
	}

	public int getGradesNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLGRADE + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getGrades(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("id", OrderProperty.DESC));
		} else {
			OrderProperties = orderproperty;
		}
		String sql = SQLGRADE
				+ Conditions
						.convertToCondition(searchproperty, OrderProperties);
		MyResultSet rs = null;
		ArrayList grades = null;
		try {
			db = new dbpool();
			grades = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleGrade grade = new OracleGrade();
				grade.setId(rs.getString("id"));
				grade.setName(rs.getString("name"));
				grade.setCode(rs.getString("code"));
				grade.setBegin_date(rs.getString("begin_date"));
				grades.add(grade);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return grades;
	}

	public int getSitesNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLSITE + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getSites(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("id"));
		} else {
			OrderProperties = orderproperty;
		}
		String sql = SQLSITE;
		String condition = Conditions.convertToCondition(searchproperty,
				OrderProperties);
		sql = sql + condition;
		MyResultSet rs = null;
		List sites = new ArrayList();
		try {
			db = new dbpool();
			sites = new ArrayList();
			if (page != null) {
				int pageInt = page.getPageInt();
				int pageSize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageInt, pageSize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				Site site = new OracleSite();
				site.setId(rs.getString("id"));
				site.setName(rs.getString("name"));
				site.setCode(rs.getString("code"));
				site.setAddress(rs.getString("address"));
				site.setEmail(rs.getString("email"));
				site.setFax(rs.getString("fax"));
				site.setLinkman(rs.getString("linkman"));
				site.setManager(rs.getString("manager"));
				site.setNote(rs.getString("note"));
				site.setPhone(rs.getString("phone"));
//				site.setRecruit_status(rs.getString("recruit_status"));
//				site.setStatus(rs.getString("status"));
//				site.setZip_code(rs.getString("zip_code"));
				site.setURL(rs.getString("site_url"));
//				site.setIndex_show(rs.getString("index_show"));
//				site.setDiqu_id(rs.getString("diqu_id"));
//				site.setRight(rs.getString("right"));
//				site.setFound_date(rs.getString("found_date"));
//				site.setSite_company(rs.getString("site_company")) ;
//				site.setSite_type(rs.getString("site_type")) ;
//				site.setCorporation(rs.getString("corporation")) ;
//				site.setFirst_student_date(rs.getString("first_student_date")) ;
			sites.add(site);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return sites;
	}
	public Map getSitesMap() {
		dbpool db = new dbpool();
		List OrderProperties = new ArrayList();
		String sql = SQLSITE;
		Map map = new HashMap() ;
		MyResultSet rs = null;
		List sites = new ArrayList();
		try {
			db = new dbpool();
			sites = new ArrayList();
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				String siteId = rs.getString("id");
				String siteName = rs.getString("name");
				map.put(siteId,siteName);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return map;
	}

	public int getSiteDiquNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLSITEDIQU + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public int setSiteRight(String[] siteIds, String[] rights) {
		dbpool db = new dbpool();
		ArrayList sqlList = new ArrayList();
		if (siteIds != null) {
			for (int k = 0; k < siteIds.length; k++) {
				String sql = "update entity_site_info set right = '"
						+ rights[k] + "' where id = '" + siteIds[k] + "'";
				sqlList.add(sql);
			}
		}
		int i = db.executeUpdateBatch(sqlList);
		return i;
	}

	public List getSitesDiqu(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("id"));
		} else {
			OrderProperties = orderproperty;
		}
		String sql = SQLSITEDIQU;
		String condition = Conditions.convertToCondition(searchproperty,
				OrderProperties);
		sql = sql + condition;
		MyResultSet rs = null;
		List sitediqus = new ArrayList();
		try {
			db = new dbpool();
			sitediqus = new ArrayList();
			if (page != null) {
				int pageInt = page.getPageInt();
				int pageSize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageInt, pageSize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleSiteDiqu siteDiqu = new OracleSiteDiqu();
				siteDiqu.setId(rs.getString("id"));
				siteDiqu.setName(rs.getString("name"));
				siteDiqu.setRightCode(rs.getString("rightcode"));
				sitediqus.add(siteDiqu);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return sitediqus;
	}

	public List getShowSites(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		List OrderProperties = new ArrayList();
		List searchProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("id"));
		} else {
			OrderProperties = orderproperty;
		}
		if (searchproperty != null) {
			searchProperties=searchproperty;
		} 
		searchProperties.add(new SearchProperty("index_show", "��", "="));

		String sql = SQLSITE;
		String condition = Conditions.convertToCondition(searchProperties,
				OrderProperties);
		sql = sql + condition;
		// System.out.println(sql);
		MyResultSet rs = null;
		List sites = new ArrayList();
		try {
			db = new dbpool();
			sites = new ArrayList();
			if (page != null) {
				int pageInt = page.getPageInt();
				int pageSize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageInt, pageSize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				Site site = new OracleSite();
				site.setId(rs.getString("id"));
				site.setName(rs.getString("name"));
				site.setAddress(rs.getString("address"));
				site.setEmail(rs.getString("email"));
				site.setFax(rs.getString("fax"));
				site.setLinkman(rs.getString("linkman"));
				site.setManager(rs.getString("manager"));
				site.setNote(rs.getString("note"));
				site.setPhone(rs.getString("phone"));
				site.setRecruit_status(rs.getString("recruit_status"));
				site.setStatus(rs.getString("status"));
				site.setZip_code(rs.getString("zip_code"));
				site.setURL(rs.getString("URL"));
				site.setIndex_show(rs.getString("index_show"));
				sites.add(site);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return sites;
	}

	public int getClassesNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLCLASSES + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getClasses(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLCLASSES;
		String condition = Conditions.convertToCondition(searchproperty,
				orderproperty);
		sql = sql + condition;
		MyResultSet rs = null;
		List classes = null;
		try {
			db = new dbpool();
			classes = new ArrayList();
			if (page != null) {
				int pageInt = page.getPageInt();
				int pageSize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageInt, pageSize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleClasse classe = new OracleClasse();
				classe.setId(rs.getString("id"));
				classe.setName(rs.getString("name"));
				classe.setHomepage(rs.getString("homepage"));
				classe.setEnounce(rs.getString("enounce"));
				classes.add(classe);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("BasicDataList.getClasses() error " + sql);
		} finally {
			db.close(rs);
			db = null;

		}
		return classes;
	}

	public int getTeachPlansNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLTEACHPLAN
				+ Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getTeachPlans(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		ArrayList teachplanList = null;
		sql = SQLTEACHPLAN
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		try {
			db = new dbpool();
			teachplanList = new ArrayList();
			if (page != null) {
				int pageInt = page.getPageInt();
				int pageSize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageInt, pageSize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleTeachPlan teachplan = new OracleTeachPlan();
				teachplan.setId(rs.getString("id"));
				teachplan.setTitle(rs.getString("title"));
				OracleMajor major = new OracleMajor(rs.getString("major_id"));
				teachplan.setMajor(major);
				OracleGrade grade = new OracleGrade(rs.getString("grade_id"));
				teachplan.setGrade(grade);
				OracleEduType eduType = new OracleEduType(rs
						.getString("edutype_id"));
				teachplan.setEduType(eduType);
				teachplanList.add(teachplan);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("BasicDataList.getTeachPlans() error " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return teachplanList;
	}

	public int getExecutePlansNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLEXECUTEPLAN
				+ Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getExecutePlans(Page page, List searchproperty,
			List orderproperty) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		ArrayList executePlanList = null;
		sql = SQLEXECUTEPLAN
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		try {
			db = new dbpool();
			executePlanList = new ArrayList();
			if (page != null) {
				int pageInt = page.getPageInt();
				int pageSize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageInt, pageSize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleExecutePlan executePlan = new OracleExecutePlan();
				executePlan.setId(rs.getString("id"));
				executePlan.setTitle(rs.getString("title"));
				OracleSemester semester = new OracleSemester(rs
						.getString("semester_id"));
				executePlan.setSemester(semester);
				executePlanList.add(executePlan);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("BasicDataList.getExecutePlans() error " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return executePlanList;
	}

	public int getSingleTeachPlanCoursesNum(List searchproperty) {
		dbpool db = new dbpool();
		SearchProperty student = (SearchProperty) searchproperty.get(0);
//		String sql = "select course_id,course_name,credit,credit1,course_time,course_time1,type "
//				+ "from (select c.id as course_id,c.name as course_name,c.credit,b.credit as credit1,c.course_time,"
//				+ "b.coursetime as course_time1,b.type from entity_teachplan_info a,"
//				+ "entity_teachplan_course b,entity_course_info c,(select major_id,edutype_id,"
//				+ "grade_id from entity_student_info where id='"
//				+ student.getValue()
//				+ "') e where a.id=b.teachplan_id and b.course_id=c.id "
//				+ "and a.major_id=e.major_id and a.edutype_id=e.edutype_id and a.grade_id=e.grade_id "
//				+ "and c.id not in (select course_id from entity_singleteachplan_course where student_id='"
//				+ student.getValue()
//				+ "' and status='0') "
//				+ "union select c.id as course_id,c.name as course_name,c.credit,b.credit as credit1,c.course_time,"
//				+ "b.course_time as course_time1,b.type from entity_singleteachplan_course b,"
//				+ "entity_course_info c where b.course_id=c.id and b.student_id='"
//				+ student.getValue() + "' and b.status='1')";
		String sql = "select course_id,course_name,credit,credit1,course_time,course_time1,type "
			+ "from (select c.id as course_id,c.name as course_name,c.credit,b.credit as credit1,c.course_time,"
			+ "b.coursetime as course_time1,b.type from entity_teachplan_info a,"
			+ "entity_teachplan_course b,entity_course_info c,(select major_id,edutype_id,"
			+ "grade_id from entity_student_info where id='"
			+ student.getValue()
			+ "') e where a.id=b.teachplan_id and b.course_id=c.id "
			+ "and a.major_id=e.major_id and a.edutype_id=e.edutype_id and a.grade_id=e.grade_id "
			+ "and c.id not in (select course_id from entity_singleteachplan_course where student_id='"
			+ student.getValue()
			+ "' and status='0') "
			+ "union select c.id as course_id,c.name as course_name,c.credit,b.credit as credit1,c.course_time,"
			+ "b.course_time as course_time1,b.type from entity_singleteachplan_course b,"
			+ "entity_course_info c where b.course_id=c.id and b.student_id='"
			+ student.getValue() + "' and b.status='1')";
		int i = db.countselect(sql);
		return i;
	}

	public List getSingleTeachPlanCourses(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		TeachPlanCourseType type = new TeachPlanCourseType();
		SearchProperty student = (SearchProperty) searchproperty.get(0);
		ArrayList courseList = null;
		sql = "select course_id,course_name,credit,credit1,course_time,course_time1,type "
				+ "from (select c.id as course_id,c.name as course_name,c.credit,b.credit as credit1,c.course_time,"
				+ "b.coursetime as course_time1,b.type from entity_teachplan_info a,"
				+ "entity_teachplan_course b,entity_course_info c,(select major_id,edutype_id,"
				+ "grade_id from entity_student_info where id='"
				+ student.getValue()
				+ "') e where a.id=b.teachplan_id and b.course_id=c.id "
				+ "and a.major_id=e.major_id and a.edutype_id=e.edutype_id and a.grade_id=e.grade_id "
				+ "and c.id not in (select course_id from entity_singleteachplan_course where student_id='"
				+ student.getValue()
				+ "' and status in ('0','1')) "
				+ "union select c.id as course_id,c.name as course_name,c.credit,b.credit as credit1,c.course_time,"
				+ "b.course_time as course_time1,b.type from entity_singleteachplan_course b,"
				+ "entity_course_info c where b.course_id=c.id and b.student_id='"
				+ student.getValue() + "' and b.status='1')";
		try {
			db = new dbpool();
			courseList = new ArrayList();
			if (page != null) {
				int pageInt = page.getPageInt();
				int pageSize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageInt, pageSize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleSingleTeachPlanCourse courseSTP = new OracleSingleTeachPlanCourse();
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				course.setCredit(rs.getString("credit"));
				course.setCourse_time(rs.getString("course_time"));
				courseSTP.setCourse(course);
				courseSTP.setCredit(rs.getFloat("credit1"));
				courseSTP.setCourseTime(rs.getFloat("course_time1"));
				String types = rs.getString("type");
				if(types != null)
				{
					int length = types.substring(0, 1).getBytes().length;
					if(length>1){
						courseSTP.setType(rs.getString("type"));
					}else{
						courseSTP.setType(TeachPlanCourseType.typeShow(rs
								.getString("type")));
					}
				}
				else
					courseSTP.setType("");
//				char flag = types.charAt(1);
//				if((flag>'A'&&flag<'Z')||(flag>'a'&&flag<'z')){
//					courseSTP.setType(TeachPlanCourseType.typeConvert(rs
//							.getString("type")));
//				}else{
//			     	courseSTP.setType(TeachPlanCourseType.typeShow(rs
//						.getString("type")));
//				}
				
				courseList.add(courseSTP);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("BasicDataList.getSingleTeachPlanCourses() error "
					+ sql);
		} catch (TypeErrorException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return courseList;
	}

	public int getUnSingleTeachPlanCoursesNum(List searchproperty) {
		dbpool db = new dbpool();
		SearchProperty student = (SearchProperty) searchproperty.get(0);
		searchproperty.remove(0);
//		String sql = "select course_id,course_name,credit,course_time,major_id,major_name from "
//				+ "(select a.id as course_id,a.name as course_name,credit,course_time,a.major_id,b.name as major_name "
//				+ "from entity_course_info a,entity_major_info b where a.major_id=b.id and a.id not in "
//				+ "(select c.id from entity_teachplan_info a,"
//				+ "entity_teachplan_course b,entity_course_info c,entity_major_info d,(select major_id,edutype_id,"
//				+ "grade_id from entity_student_info where id='"
//				+ student.getValue()
//				+ "') e where a.id=b.teachplan_id and b.course_id=c.id "
//				+ "and c.major_id=d.id and a.major_id=e.major_id and a.edutype_id=e.edutype_id and a.grade_id=e.grade_id "
//				+ "and c.id not in (select course_id from entity_singleteachplan_course where student_id='"
//				+ student.getValue()
//				+ "' and status='0') "
//				+ "union select c.id from entity_singleteachplan_course b,"
//				+ "entity_course_info c,entity_major_info d where b.course_id=c.id and c.major_id=d.id and b.student_id='"
//				+ student.getValue()
//				+ "' and b.status='1'))"
//				+ Conditions.convertToCondition(searchproperty, null);
		String sql = "select distinct course_id,course_name,credit,course_time from "
			+ "(select a.id as course_id,a.name as course_name,credit,course_time,a.major_id,b.name as major_name "
			+ "from entity_course_info a,entity_major_info b where a.id not in "
			+ "(select c.id from entity_teachplan_info a,"
			+ "entity_teachplan_course b,entity_course_info c,entity_major_info d,(select major_id,edutype_id,"
			+ "grade_id from entity_student_info where id='"
			+ student.getValue()
			+ "') e where a.id=b.teachplan_id and b.course_id=c.id "
			+ "and a.major_id=e.major_id and a.edutype_id=e.edutype_id and a.grade_id=e.grade_id "
			+ "and c.id not in (select course_id from entity_singleteachplan_course where student_id='"
			+ student.getValue()
			+ "' and status='0') "
			+ "union select c.id from entity_singleteachplan_course b,"
			+ "entity_course_info c,entity_major_info d where b.course_id=c.id and c.major_id=d.id and b.student_id='"
			+ student.getValue()
			+ "' and b.status='1'))"
			+ Conditions.convertToCondition(searchproperty,null);
		int i = db.countselect(sql);
		return i;
	}

	public List getUnSingleTeachPlanCourses(Page page, List searchproperty,
			List orderproperty) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		SearchProperty student = (SearchProperty) searchproperty.get(0);
		searchproperty.remove(0);
		ArrayList courseList = null;
//		sql = "select course_id,course_name,credit,course_time,major_id,major_name from "
//				+ "(select a.id as course_id,a.name as course_name,credit,course_time,a.major_id,b.name as major_name "
//				+ "from entity_course_info a,entity_major_info b where a.major_id=b.id and a.id not in "
//				+ "(select c.id from entity_teachplan_info a,"
//				+ "entity_teachplan_course b,entity_course_info c,entity_major_info d,(select major_id,edutype_id,"
//				+ "grade_id from entity_student_info where id='"
//				+ student.getValue()
//				+ "') e where a.id=b.teachplan_id and b.course_id=c.id "
//				+ "and c.major_id=d.id and a.major_id=e.major_id and a.edutype_id=e.edutype_id and a.grade_id=e.grade_id "
//				+ "and c.id not in (select course_id from entity_singleteachplan_course where student_id='"
//				+ student.getValue()
//				+ "' and status='0') "
//				+ "union select c.id from entity_singleteachplan_course b,"
//				+ "entity_course_info c,entity_major_info d where b.course_id=c.id and c.major_id=d.id and b.student_id='"
//				+ student.getValue()
//				+ "' and b.status='1'))"
//				+ Conditions.convertToCondition(searchproperty, orderproperty);
		sql = "select distinct course_id,course_name,credit,course_time from "
			+ "(select a.id as course_id,a.name as course_name,credit,course_time,a.major_id,b.name as major_name "
			+ "from entity_course_info a,entity_major_info b where a.id not in "
			+ "(select c.id from entity_teachplan_info a,"
			+ "entity_teachplan_course b,entity_course_info c,entity_major_info d,(select major_id,edutype_id,"
			+ "grade_id from entity_student_info where id='"
			+ student.getValue()
			+ "') e where a.id=b.teachplan_id and b.course_id=c.id "
			+ "and a.major_id=e.major_id and a.edutype_id=e.edutype_id and a.grade_id=e.grade_id "
			+ "and c.id not in (select course_id from entity_singleteachplan_course where student_id='"
			+ student.getValue()
			+ "' and status='0') "
			+ "union select c.id from entity_singleteachplan_course b,"
			+ "entity_course_info c,entity_major_info d where b.course_id=c.id and c.major_id=d.id and b.student_id='"
			+ student.getValue()
			+ "' and b.status='1'))"
			+ Conditions.convertToCondition(searchproperty, orderproperty);
		try {
			db = new dbpool();
			courseList = new ArrayList();
			if (page != null) {
				int pageInt = page.getPageInt();
				int pageSize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageInt, pageSize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				course.setCredit(rs.getString("credit"));
				course.setCourse_time(rs.getString("course_time"));
				//course.setMajor_id(rs.getString("major_id"));
			//	course.setMajor_name(rs.getString("major_name"));
				courseList.add(course);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("BasicDataList.getUnSingleTeachPlanCourses() error "
					+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return courseList;
	}

	public List getSemesterTeachPlanCourses(Page page, List searchproperty,
			List orderproperty) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		TeachPlanCourseType type = new TeachPlanCourseType();
		ArrayList courseList = new ArrayList();
		String sql = "select course_id,course_name,credit,coursetime,type from (select d.id as course_id,d.name as course_name,b.credit,b.coursetime,b.type from entity_teachplan_info a,entity_teachplan_course b,entity_course_active c,entity_course_info d where a.id=b.teachplan_id and b.course_id=c.course_id and c.course_id=d.id "
				+ Conditions.convertToAndCondition(searchproperty,
						orderproperty) + ")";
		try {
			if (page != null)
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			else
				rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleTeachPlanCourse teachplancourse = new OracleTeachPlanCourse();
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				teachplancourse.setCourse(course);
				teachplancourse.setCredit(rs.getFloat("credit"));
				teachplancourse.setCoursetime(rs.getFloat("coursetime"));
				teachplancourse.setType(type.typeShow(rs.getString("type")));
				courseList.add(teachplancourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return courseList;
	}

	public int getTeachBookNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLTEACHBOOK
				+ Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getTeachBooks(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLTEACHBOOK
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList oracleTeachBooksList = null;
		try {
			db = new dbpool();
			oracleTeachBooksList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleTeachBook oracleTeachBook = new OracleTeachBook();
				oracleTeachBook.setId(rs.getString("id"));
				oracleTeachBook.setTeachbook_name(rs
						.getString("teachbook_name"));
				oracleTeachBook.setPublishhouse(rs.getString("publishhouse"));
				oracleTeachBook.setMaineditor(rs.getString("maineditor"));
				oracleTeachBook.setIsbn(rs.getString("isbn"));
				oracleTeachBook.setPublish_date(rs.getString("publish_date"));
				oracleTeachBook.setPrice(rs.getString("price"));
				oracleTeachBook.setNote(rs.getString("note"));
				oracleTeachBooksList.add(oracleTeachBook);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return oracleTeachBooksList;

	}

	public List getPlanEduTypes(List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		/*
		 * String sql = SQLPLAN_EDUTYPE +
		 * Conditions.convertToCondition(searchproperty, orderproperty);
		 */
		String sql = "";
		sql = "select distinct batch_id,edutype_id,edutype_name from (select distinct a.batch_id,c.id as "
				+ "site_id,c.name as site_name,b.id as edutype_id,b.name as edutype_name,d.id "
				+ "as major_id,d.name as major_name from recruit_plan_info a,entity_edu_type b,"
				+ "entity_site_info c,entity_major_info d where a.status='1' and a.edutype_id=b.id "
				+ "and a.site_id=c.id and a.major_id=d.id)"
				+ Conditions.convertToCondition(searchproperty, orderproperty);

		EntityLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList plans = null;
		try {
			db = new dbpool();
			plans = new ArrayList();
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleEduType edutype = new OracleEduType();
				edutype.setId(rs.getString("edutype_id"));
				edutype.setName(rs.getString("edutype_name"));
				plans.add(edutype);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return plans;
	}

	public List getPlanSites(List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		/*
		 * String sql = SQLPLAN_EDUTYPE +
		 * Conditions.convertToCondition(searchproperty, orderproperty);
		 */
		String sql = "";
		sql = "select distinct batch_id,site_id,site_name from (select distinct a.batch_id,c.id as "
				+ "site_id,c.name as site_name,b.id as edutype_id,b.name as edutype_name,d.id "
				+ "as major_id,d.name as major_name from recruit_plan_info a,entity_edu_type b,"
				+ "entity_site_info c,entity_major_info d where a.status='1' and a.edutype_id=b.id "
				+ "and a.site_id=c.id and a.major_id=d.id)"
				+ Conditions.convertToCondition(searchproperty, orderproperty);

		EntityLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList plans = null;
		try {
			db = new dbpool();
			plans = new ArrayList();
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleSite site = new OracleSite();
				site.setId(rs.getString("site_id"));
				site.setName(rs.getString("site_name"));
				plans.add(site);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return plans;
	}

	public List getPlanMajors(List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		/*
		 * String sql = "select id,name,batch_id,site_id,edutype_id,major_id
		 * from " + "(select distinct
		 * b.id,b.name,a.batch_id,a.site_id,a.edutype_id,a.major_id from " +
		 * "recruit_plan_info a,entity_major_info b where a.status='1' and
		 * a.major_id=b.id)" + Conditions.convertToCondition(searchproperty,
		 * orderproperty);
		 */
		String sql = "";
		sql = "select distinct batch_id,major_id,major_name from (select distinct a.batch_id,c.id as "
				+ "site_id,c.name as site_name,b.id as edutype_id,b.name as edutype_name,d.id "
				+ "as major_id,d.name as major_name from recruit_plan_info a,entity_edu_type b,"
				+ "entity_site_info c,entity_major_info d where a.status='1' and a.edutype_id=b.id "
				+ "and a.site_id=c.id and a.major_id=d.id)"
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		EntityLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList plans = null;

		try {
			db = new dbpool();
			plans = new ArrayList();
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {

				OracleMajor major = new OracleMajor();
				major.setId(rs.getString("major_id"));
				major.setName(rs.getString("major_name"));
				plans.add(major);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return plans;
	} /*
		 * (non-Javadoc)
		 * 
		 * @see com.whaty.platform.entity.basic.BasicEntityList#getMobilePhones(java.lang.String[],
		 *      java.lang.String[], java.lang.String[], java.lang.String[],
		 *      java.lang.String[])
		 */

	public String getMobilePhones(String[] persons, String[] siteIds,
			String[] gradeIds, String[] majorIds, String[] eduTypeIds) {
		String targets = "";
		dbpool db = new dbpool();
		MyResultSet rs = null;
		for (int i = 0; i < persons.length; i++) {
			if (persons[i].equalsIgnoreCase("teachers")) {
				String sql = "select mobilephone from entity_teacher_info";
				rs = db.executeQuery(sql);
				try {
					while (rs != null && rs.next()) {
						targets = targets + rs.getString("mobilephone") + ",";
					}
				} catch (SQLException e) {
					
				}
			}

			if (persons[i].equalsIgnoreCase("students")) {
				String siteId = "";
				String gradeId = "";
				String majorId = "";
				String eduTypeId = "";
				String conditions = "";
				for (int j = 0; j < siteIds.length; j++) {
					siteId = siteId + siteIds[j] + ",";
				}
				if (siteId.length() > 0) {
					siteId = siteId.substring(0, siteId.length() - 1);
					conditions = conditions + " site_id in (" + siteId + ")";
				}

				for (int j = 0; j < gradeIds.length; j++) {
					gradeId = gradeId + gradeIds[j] + ",";
				}
				if (gradeId.length() > 0) {
					gradeId = gradeId.substring(0, gradeId.length() - 1);
					if (conditions.length() > 0)
						conditions = conditions + " and grade_id in ("
								+ gradeId + ")";
					else
						conditions = conditions + " grade_id in (" + gradeId
								+ ")";
				}

				for (int j = 0; j < majorIds.length; j++) {
					majorId = majorId + majorIds[j] + ",";
				}
				if (majorId.length() > 0) {
					majorId = majorId.substring(0, majorId.length() - 1);
					if (conditions.length() > 0)
						conditions = conditions + " and major_id in ("
								+ majorId + ")";
					else
						conditions = conditions + " major_id in (" + majorId
								+ ")";
				}

				for (int j = 0; j < eduTypeIds.length; j++) {
					eduTypeId = eduTypeId + eduTypeIds[j] + ",";
				}
				if (eduTypeId.length() > 0) {
					eduTypeId = eduTypeId.substring(0, eduTypeId.length() - 1);
					if (conditions.length() > 0)
						conditions = conditions + " and edutype_id in ("
								+ eduTypeId + ")";
					else
						conditions = conditions + " edutype_id in ("
								+ eduTypeId + ")";
				}

				if (conditions.length() > 0)
					conditions = " where " + conditions;

				String sql = "select mobilephone from (select id,site_id,major_id,grade_id,edutype_id "
						+ "from entity_student_info "
						+ conditions
						+ ") esi,(select id,mobilephone from entity_usernormal_info) eui where eui.id=esi.id";
				rs = db.executeQuery(sql);
				try {
					while (rs != null && rs.next()) {
						targets = targets + rs.getString("mobilephone") + ",";
					}
				} catch (SQLException e) {
					
				}
			}
		}
		db.close(rs);
		if (targets.length() > 0)
			targets = targets.substring(0, targets.length() - 1);

		return targets;
	}

	public int getTeachBooksNumOfCourse(String courseId) {
		dbpool db = new dbpool();
		String sql = "select id,teachbook_name,publishhouse,maineditor,isbn,publish_date,price,note from entity_teachbook_info where id in "
				+ "(select teachbook_id from entity_teachbook_course where course_id='"
				+ courseId + "')";

		return db.countselect(sql);
	}

	public List getTeachBooksOfCourse(Page page, String courseId) {
		List bookList = new ArrayList();
		dbpool db = new dbpool();
		String sql = "select id,teachbook_name,publishhouse,maineditor,isbn,publish_date,price,note from entity_teachbook_info where id in "
				+ "(select teachbook_id from entity_teachbook_course where course_id='"
				+ courseId + "')";
		MyResultSet rs = null;
		if (page != null) {
			int pageInt = page.getPageInt();
			int pageSize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
		} else {
			rs = db.executeQuery(sql);
		}
		try {
			while (rs != null && rs.next()) {
				OracleTeachBook book = new OracleTeachBook();
				book.setId(rs.getString("id"));
				book.setTeachbook_name(rs.getString("teachbook_name"));
				book.setPublishhouse(rs.getString("publishhouse"));
				book.setMaineditor(rs.getString("maineditor"));
				book.setIsbn(rs.getString("isbn"));
				book.setPublish_date(rs.getString("publish_date"));
				book.setPrice(rs.getString("price"));
				book.setNote(rs.getString("note"));

				bookList.add(book);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return bookList;
	}

	public int getTeachBooksNumToAddOfCourse(String courseId) {
		dbpool db = new dbpool();
		String sql = "select id,teachbook_name,publishhouse,maineditor,isbn,publish_date,price,note from entity_teachbook_info where id not in "
				+ "(select teachbook_id from entity_teachbook_course where course_id='"
				+ courseId + "')";

		return db.countselect(sql);
	}

	public List getTeachBooksToAddOfCourse(Page page, String courseId) {
		List bookList = new ArrayList();
		dbpool db = new dbpool();
		String sql = "select id,teachbook_name,publishhouse,maineditor,isbn,publish_date,price,note from entity_teachbook_info where id not in "
				+ "(select teachbook_id from entity_teachbook_course where course_id='"
				+ courseId + "')";
		MyResultSet rs = null;
		if (page != null) {
			int pageInt = page.getPageInt();
			int pageSize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
		} else {
			rs = db.executeQuery(sql);
		}
		try {
			while (rs != null && rs.next()) {
				OracleTeachBook book = new OracleTeachBook();
				book.setId(rs.getString("id"));
				book.setTeachbook_name(rs.getString("teachbook_name"));
				book.setPublishhouse(rs.getString("publishhouse"));
				book.setMaineditor(rs.getString("maineditor"));
				book.setIsbn(rs.getString("isbn"));
				book.setPublish_date(rs.getString("publish_date"));
				book.setPrice(rs.getString("price"));
				book.setNote(rs.getString("note"));

				bookList.add(book);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return bookList;
	}

	public String getMobilePhoneByLoginId(String loginId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		String sql1 = "select mobilephone from entity_usernormal_info where id = (select id from sso_user where login_id= '"+loginId+"')";
		String sql2 = "select mobilephone from entity_teacher_info where gh = '"+loginId+"'";
		String sql3 = "select mobilephone from entity_manager_info where login_id = '"+loginId+"'";
		String sql4 = "select mobilephone from entity_sitemanager_info where login_id = '"+loginId+"'";
		if(db.countselect(sql1)>0)
			sql = sql1;
		if(db.countselect(sql2)>0)
			sql = sql2;
		if(db.countselect(sql3)>0)
			sql = sql3;
		if(db.countselect(sql4)>0)
			sql = sql4;
		String mobilephone = "";
		try{
			sql = "select mobilephone from ("+sql+")";
			rs = db.executeQuery(sql);
			while(rs!=null && rs.next())
			{
				mobilephone = rs.getString("mobilephone");
			}
		}catch(Exception e){
			
		}finally{
			db.close(rs);
			db = null;
		}
		return mobilephone;
	}

}