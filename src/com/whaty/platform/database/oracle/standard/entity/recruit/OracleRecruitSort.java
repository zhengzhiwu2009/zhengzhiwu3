package com.whaty.platform.database.oracle.standard.entity.recruit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleEduType;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleMajor;
import com.whaty.platform.entity.recruit.RecruitSort;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleRecruitSort extends RecruitSort {
	/** Creates a new instance of OracleRecruitSort */
	public OracleRecruitSort() {
	}
	
	public OracleRecruitSort(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,edutype_id,edutype_name,name,note from (select a.id,a.edutype_id,b.name as edutype_name,a.name,a.note from recruit_sort_info a,entity_edu_type b where a.edutype_id=b.id and a.id = '"
				+ aid + "')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				OracleEduType edutype = new OracleEduType();
				edutype.setId(rs.getString("edutype_id"));
				edutype.setName(rs.getString("edutype_name"));
				this.setEdutype(edutype);
				this.setName(rs.getString("name"));
				this.setNote(rs.getString("note"));
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleRecruitSort(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from recruit_sort_info where name='" + this.getName()
				+ "'";
		if (db.countselect(sql) > 0)
			throw new PlatformException("ƵרҵѾڣ");
		sql = "insert into recruit_sort_info (id,edutype_id,name,note) values(to_char(s_recruit_sort_id.nextval),'"
				+ this.getEdutype().getId()
				+ "','"
				+ this.getName()
				+ "','"
				+ this.getNote() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRecruitSort.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from recruit_sort_info where name='" + this.getName()
				+ "'";
		if (db.countselect(sql) > 0)
			throw new PlatformException("ƵרҵѾڣ");
		sql = "update recruit_sort_info set name='" + this.getName()
				+ "',note='" + this.getNote() + "',edutype_id='"
				+ this.getEdutype().getId() + "' where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitSort.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from recruit_sort_info where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRecruitSort.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		sql = "delete from recruit_majorsort_relation where sort_id='"
				+ this.getId() + "'";
		db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRecruitSort.delete() SQL=" + sql + " DATE=" + new Date());
		sql = "delete from recruit_sortcourse_relation where sort_id='"
				+ this.getId() + "'";
		db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRecruitSort.delete() SQL=" + sql + " DATE=" + new Date());
		return i;
	}

	public List getMajors(Page page, List searchProperty, List orderProperty)
			throws PlatformException {
		String status;
		dbpool db = new dbpool();
		String sql = "";
		sql = "select name,id,status from (select b.name,b.id,nvl(a.id,-1) as status from (select id,major_id,sort_id from recruit_majorsort_relation where sort_id='"
				+ this.getId()
				+ "') a,entity_major_info b where b.id not in(select distinct major_id from recruit_majorsort_relation where sort_id<>'"
				+ this.getId()
				+ "' and sort_id in(select id from recruit_sort_info where edutype_id=(select edutype_id from recruit_sort_info where id='"
				+ this.getId()
				+ "'))) and b.id=a.major_id(+)) "
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList majorList = null;
		try {
			db = new dbpool();
			majorList = new ArrayList();
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
				status = rs.getString("status");
				if (status.equals("-1"))
					major.setStatus(false);
				else
					major.setStatus(true);
				majorList.add(major);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return majorList;
	}

	public int getMajorsNum(List searchProperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select name,id,status from (select b.name,b.id,nvl(a.id,-1) as status from (select id,major_id,sort_id from recruit_majorsort_relation where sort_id='"
				+ this.getId()
				+ "') a,entity_major_info b where b.id not in(select distinct major_id from recruit_majorsort_relation where sort_id<>'"
				+ this.getId()
				+ "' and sort_id in(select id from recruit_sort_info where edutype_id=(select edutype_id from recruit_sort_info where id='"
				+ this.getId()
				+ "'))) and b.id=a.major_id(+)) "
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public int setMajors(String recruitSortId, List majorId, List majorIds)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		String majorid = "";
		int i = 0;
		if (majorIds != null && majorIds.size() > 0) {
			for (int t = 0; t < majorIds.size(); t++)
				majorid += ",'" + (String) majorIds.get(t) + "'";
			if (majorid.length() > 0) {
				sql = "delete from recruit_majorsort_relation where major_id in ("
						+ majorid.substring(1)
						+ ") and sort_id='"
						+ recruitSortId + "'";
				i = db.executeUpdate(sql);
				UserDeleteLog.setDebug("OracleRecruitSort.setMajors(String recruitSortId,List majorId,List MajorIds) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
			}
		}
		if (majorId != null && majorId.size() > 0) {
			for (int t = 0; t < majorId.size(); t++) {
				majorid = (String) majorId.get(t);
				sql = "insert into recruit_majorsort_relation (id,major_id,sort_id) values(to_char(s_majorsort_relation_id.nextval),'"
						+ majorid + "','" + recruitSortId + "')";
				i = db.executeUpdate(sql);
				UserAddLog.setDebug("OracleRecruitSort.setMajors(String recruitSortId,List majorId,List MajorIds) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
			}
		}
		return i;
	}

	public List getCourses(Page page, List searchProperty, List orderProperty)
			throws PlatformException {
		String status;
		dbpool db = new dbpool();
		String sql = "";
		sql = "select name,id,status from (select b.name,b.id,nvl(a.id,0) as status from (select id,course_id,sort_id from recruit_sortcourse_relation where sort_id='"
				+ this.getId()
				+ "') a,recruit_course_info b where b.id=a.course_id(+)) "
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList courseList = null;
		try {
			db = new dbpool();
			courseList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitCourse course = new OracleRecruitCourse();
				course.setId(rs.getString("id"));
				course.setName(rs.getString("name"));
				course.setNote(rs.getString("status"));
				courseList.add(course);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return courseList;
	}

	public int getCoursesNum(List searchProperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select name,id,status from (select b.name,b.id,nvl(a.id,0) as status from (select id,course_id,sort_id from recruit_sortcourse_relation where sort_id='"
				+ this.getId()
				+ "') a,recruit_course_info b where b.id=a.course_id(+)) "
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public int setCourses(String recruitSortId, List courseId, List courseIds)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		String courseid = "";
		int i = 0;
		if (courseIds != null && courseIds.size() > 0) {
			for (int t = 0; t < courseIds.size(); t++)
				courseid += ",'" + (String) courseIds.get(t) + "'";
			if (courseid.length() > 0) {
				sql = "delete from recruit_sortcourse_relation where course_id in ("
						+ courseid.substring(1)
						+ ") and sort_id='"
						+ recruitSortId + "'";
				i = db.executeUpdate(sql);
				UserDeleteLog.setDebug("OracleRecruitSort.setCourses(String recruitSortId,List courseId,List courseIds) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
			}
		}
		if (courseId != null && courseId.size() > 0) {
			for (int t = 0; t < courseId.size(); t++) {
				courseid = (String) courseId.get(t);
				sql = "insert into recruit_sortcourse_relation (id,course_id,sort_id) values(to_char(s_sortcourse_relation_id.nextval),'"
						+ courseid + "','" + recruitSortId + "')";
				i = db.executeUpdate(sql);
				UserAddLog.setDebug("OracleRecruitSort.setCourses(String recruitSortId,list courseId,List courseIds) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
			}
		}
		return i;
	}
}
