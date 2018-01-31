package com.whaty.platform.database.oracle.standard.entity.recruit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleEduType;
import com.whaty.platform.entity.recruit.RecruitCourse;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleRecruitCourse extends RecruitCourse {

	public OracleRecruitCourse() {

	}

	public OracleRecruitCourse(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id,name,note from recruit_course_info where id = '"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setNote(rs.getString("note"));
			}
		} catch (Exception e) {
			Log.setError("OracleRecruitCourse(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "insert into recruit_course_info (id,name,note) values ('"
				+ this.getId() + "','" + this.getName() + "','"
				+ this.getNote() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRecruitCourse.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update recruit_course_info set name = '" + this.getName()
				+ "',note = '" + this.getNote() + "' where id = '"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitCourse.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return db.executeUpdate(sql);
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "delete from recruit_course_info where id = '"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRecruitCourse.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		sql = "delete from recruit_sortcourse_relation where course_id='"
				+ this.getId() + "'";
		db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRecruitCourse.delete() SQL=" + sql + " DATE=" + new Date());
		return i;
	}

	public List getSorts(Page page, List searchProperty, List orderProperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id,name,status,edutype_id,edutype_name from (select b.name,b.id,nvl(a.id,0) as status,c.id as edutype_id,c.name as edutype_name from (select id,course_id,sort_id from recruit_sortcourse_relation where course_id='"
				+ this.getId()
				+ "') a,recruit_sort_info b,entity_edu_type c where b.edutype_id=c.id and b.id=a.sort_id(+))"
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList sortList = null;
		try {
			db = new dbpool();
			sortList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitSort sort = new OracleRecruitSort();
				sort.setId(rs.getString("id"));
				sort.setName(rs.getString("name"));
				sort.setNote(rs.getString("status"));
				OracleEduType edutype = new OracleEduType();
				edutype.setId(rs.getString("edutype_id"));
				edutype.setName(rs.getString("edutype_name"));
				sort.setEdutype(edutype);
				sortList.add(sort);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return sortList;
	}

	public int getSortsNum(List searchProperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id,name,status from (select b.name,b.id,nvl(a.id,0) as status from (select id,course_id,sort_id from recruit_sortcourse_relation where course_id='"
				+ this.getId()
				+ "') a,recruit_sort_info b where b.id=a.sort_id(+))"
				+ Conditions.convertToCondition(searchProperty, null);
		return db.countselect(sql);
	}

	public int setSorts(String recruitCourseId, List SortId, List SortIds)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		String sortid = "";
		int i = 0;
		if (SortIds != null && SortIds.size() > 0) {
			for (int t = 0; t < SortIds.size(); t++)
				sortid += ",'" + (String) SortIds.get(t) + "'";
			if (sortid.length() > 0) {
				sql = "delete from recruit_sortcourse_relation where sort_id in ("
						+ sortid.substring(1)
						+ ") and course_id='"
						+ recruitCourseId + "'";
				i = db.executeUpdate(sql);
				UserDeleteLog.setDebug("OracleRecruitCourse.setSorts(String recruitCourseId,List SortID,List SortIds) SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
			}
		}
		if (SortId != null && SortId.size() > 0) {
			for (int t = 0; t < SortId.size(); t++) {
				sortid = (String) SortId.get(t);
				sql = "insert into recruit_sortcourse_relation (id,course_id,sort_id) values(to_char(s_sortcourse_relation_id.nextval),'"
						+ recruitCourseId + "','" + sortid + "')";
				i = db.executeUpdate(sql);
				UserAddLog.setDebug("OracleRecruitCourse.setSorts(String recruitCourseId,List SortID,List SortIds) SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
			}
		}
		return i;
	}

}
