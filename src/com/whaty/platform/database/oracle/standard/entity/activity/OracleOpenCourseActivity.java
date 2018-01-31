package com.whaty.platform.database.oracle.standard.entity.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.activity.OpenCourse;
import com.whaty.platform.entity.activity.OpenCourseActivity;
import com.whaty.platform.entity.basic.Course;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;

public class OracleOpenCourseActivity extends OpenCourseActivity {

	public void openCourses() throws PlatformException {
		dbpool db = new dbpool();
		List sqlList = new ArrayList();
		for (int i = 0; i < this.getCourseList().size(); i++) {
			// жϸÿγǷѾ裬ѾͲٿ
			if (getOpenCoursesStr().indexOf(
					((OpenCourse) this.getCourseList().get(i)).getCourse()
							.getId()) < 0) {

				String sql = "insert into entity_course_active(id,course_id,semester_id,examsequence) "
						+ "values(to_char(s_course_active_id.nextval),'"
						+ ((OpenCourse) this.getCourseList().get(i))
								.getCourse().getId()
						+ "','"
						+ this.getSemester().getId()
						+ "','"
						+ ((OpenCourse) this.getCourseList().get(i))
								.getExamSequence().getId() + "')";

				// ӽѧ
				String sql1 = "insert into entity_teach_class(id,name,open_course_id) "
						+ "select to_char(s_teach_class_id.nextval),"
						+ "'"
						+ ((OpenCourse) this.getCourseList().get(i))
								.getCourse().getName()
						+ "',"
						+ "id from entity_course_active where course_id ="
						+ "'"
						+ ((OpenCourse) this.getCourseList().get(i))
								.getCourse().getId()
						+ "'"
						+ " and semester_id='"
						+ this.getSemester().getId()
						+ "'";
				UserAddLog.setDebug("OracleOpenCourseActivity.openCourses() SQL1=" + sql + " SQL2=" + sql1 + " DATE=" + new Date());
				sqlList.add(sql);
				sqlList.add(sql1);
				// System.out.println("sql1= " + sql);
				// System.out.println("sql2= " + sql1);
			}
		}
		int i = db.executeUpdateBatch(sqlList);
		
		// System.out.println("openCoursesNum = " + i);
		// return i;

	}

	public void unOpenCourses() throws PlatformException {
		String err_info = "";
		String err_info1 = "";
		String err = "";
		dbpool db = new dbpool();
		for (int i = 0; i < this.getCourseList().size(); i++) {

			String check_sql = "select id from entity_teacher_course where teachclass_id in "
					+ "(select id from entity_teach_class  where open_course_id in ("
					+ "select id from entity_course_active where course_id="
					+ "'"
					+ ((Course) this.getCourseList().get(i)).getId()
					+ "'"
					+ " and semester_id='"
					+ this.getSemester().getId()
					+ "'))";
			String check_sql1 = "select id from entity_elective where teachclass_id in "
					+ "(select id from entity_teach_class  where open_course_id in ("
					+ "select id from entity_course_active where course_id="
					+ "'"
					+ ((Course) this.getCourseList().get(i)).getId()
					+ "'"
					+ " and semester_id='"
					+ this.getSemester().getId()
					+ "'))";
			if (db.countselect(check_sql) > 0)
				err_info = err_info + "("
						+ ((Course) this.getCourseList().get(i)).getId() + ")"
						+ ((Course) this.getCourseList().get(i)).getName()
						+ ",";
			else if (db.countselect(check_sql1) > 0)
				err_info1 = err_info1 + "("
						+ ((Course) this.getCourseList().get(i)).getId() + ")"
						+ ((Course) this.getCourseList().get(i)).getName()
						+ ",";
			else {
				List sqlList = new ArrayList();
				// ɾѧ
				String sql = "delete from entity_teach_class  where open_course_id in ("
						+ "select id from entity_course_active where course_id="
						+ "'"
						+ ((Course) this.getCourseList().get(i)).getId()
						+ "'"
						+ " and semester_id='"
						+ this.getSemester().getId() + "')";

				String sql1 = "delete from entity_course_active where course_id ="
						+ "'"
						+ ((Course) this.getCourseList().get(i)).getId()
						+ "'"
						+ " and semester_id='"
						+ this.getSemester().getId() + "'";
				UserDeleteLog.setDebug("OracleOpenCourseActivity.unOpenCourses() SQL1=" + sql + " SQL2=" + sql1 + " DATE=" + new Date());
				sqlList.add(sql);
				sqlList.add(sql1);
				db.executeUpdateBatch(sqlList);

			}
		}
		if (err_info.length() > 0) {
			err = err_info.substring(0, err_info.length() - 1)
					+ "ָڿνʦɾʦڿιϵ.";
			throw new PlatformException(err);
		}
		if (err_info1.length() > 0) {
			err += err_info1.substring(0, err_info1.length() - 1)
					+ "ѽѡΣɾѡμ¼.";
			throw new PlatformException(err);
		}
	}

	public void openCoursesByImplementPlan() throws PlatformException {
		// TODO Auto-generated method stub

	}

	public String getOpenCoursesStr() throws PlatformException {
		String coursesStr = "";
		List courses = this.getSemester().getCourses();
		for (int i = 0; i < courses.size(); i++) {
			coursesStr = coursesStr + ((OpenCourse) courses.get(i)).getId()
					+ ",";
		}
		return coursesStr;
	}

	public String getOpenCourseId(String semester_id, String course_id)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		String open_course_id = "";
		sql = "select id from entity_course_active where semester_id='"
				+ semester_id + "' and course_id='" + course_id + "'";
		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {

				open_course_id = rs.getString("id");
			}
		} catch (Exception e) {
			
		}
		db.close(rs);
		return open_course_id;
	}

	public List getTeachClassByOpenCourseId(String open_course_id)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List teachClassList = new ArrayList();
		String sql = "select id,name from entity_teach_class where open_course_id = '"
				+ open_course_id + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleTeachClass teachclass = new OracleTeachClass();
				teachclass.setId(rs.getString("id"));
				teachclass.setName(rs.getString("name"));
				teachClassList.add(teachclass);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teachClassList;

	}

	public List getCourseIdBySemesterId(String semester_id)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List teachClassList = new ArrayList();
		String sql = "select distinct a.course_id as course_id,b.name as course_name,a.semester_id as semester_id from entity_course_active a,entity_course_info b where a.course_id=b.id and a.semester_id ='"
				+ semester_id + "' order by a.course_id asc";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleTeachClass teachclass = new OracleTeachClass();
				teachclass.setId(rs.getString("course_id"));
				teachclass.setName(rs.getString("course_name"));
				teachclass.setAssistanceTitle(rs.getString("semester_id"));

				teachClassList.add(teachclass);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teachClassList;

	}

	public List getCourseIDHasExperimentList(String semester_id)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List teachClassList = new ArrayList();
		String sql = "select distinct a.course_id as course_id,b.name as course_name,a.semester_id as semester_id from entity_course_active a,entity_course_info b where a.course_id=b.id  and a.id in (select x.open_course_id from entity_teach_class x, entity_course_item y where y.id = x.id and y.shiyan = '1')  and a.semester_id ='"
				+ semester_id + "' order by a.course_id asc";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleTeachClass teachclass = new OracleTeachClass();
				teachclass.setId(rs.getString("course_id"));
				teachclass.setName(rs.getString("course_name"));
				teachclass.setAssistanceTitle(rs.getString("semester_id"));

				teachClassList.add(teachclass);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teachClassList;

	}

	public List getCourseIdBySemesterId(Page page, String semester_id)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List teachClassList = new ArrayList();
		String sql = "select distinct a.course_id as course_id,b.name as course_name,a.semester_id as semester_id from entity_course_active a,entity_course_info b where a.course_id=b.id and a.semester_id ='"
				+ semester_id + "' order by a.course_id asc";
		try {
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleTeachClass teachclass = new OracleTeachClass();
				teachclass.setId(rs.getString("course_id"));
				teachclass.setName(rs.getString("course_name"));
				teachclass.setAssistanceTitle(rs.getString("semester_id"));

				teachClassList.add(teachclass);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teachClassList;

	}

	public List getCourseBySemesterId(String semester_id)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select distinct ci.id as course_id,ci.name as course_name,ci.credit as credit ,ci.course_time as course_time,m.name as major_name,e.teachclass_id as teachclass_id  from entity_elective e,entity_teach_class t ,entity_course_active ca,entity_course_info ci,entity_major_info m  where e.teachclass_id=t.id and t.open_course_id=ca.id and ca.course_id=ci.id and ci.major_id=m.id(+)  and ca.semester_id='"
				+ semester_id + "' order by ci.id asc";
		List courseList = new ArrayList();

		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("course_id", rs.getString("course_id"));
				hash.put("course_name", rs.getString("course_name"));
				hash.put("credit", rs.getString("credit"));
				hash.put("course_time", rs.getString("course_time"));
				hash.put("major_name", rs.getString("major_name"));
				hash.put("teachclass_id", rs.getString("teachclass_id"));
				courseList.add(hash);
			}

		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return courseList;

	}

	public List getCourseBySemesterId(Page page, String semester_id)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		// String sql = "select distinct ci.id as course_id,ci.name as
		// course_name,ci.credit as credit ,ci.course_time as course_time,m.name
		// as major_name,e.teachclass_id as teachclass_id,e.total_score_rule as
		// total_score_rule from entity_elective e,entity_teach_class t
		// ,entity_course_active ca,entity_course_info ci,entity_major_info m
		// where e.teachclass_id=t.id and t.open_course_id=ca.id and
		// ca.course_id=ci.id and ci.major_id=m.id and ca.semester_id='"
		// + semester_id + "' order by ci.id asc";
		// String sql = "select distinct ci.id as course_id,ci.name as
		// course_name,ci.credit as credit ,ci.course_time as
		// course_time,e.teachclass_id as teachclass_id,e.total_score_rule as
		// total_score_rule,e.total_score_modify_rule as
		// total_score_modify_rule,e.total_score_status as
		// total_score_status,e.exam_score_status as
		// exam_score_status,e.total_expend_score_rule as
		// total_expend_score_rule,e.total_score_modify_rule1 from
		// entity_elective e,entity_teach_class t ,entity_course_active
		// ca,entity_course_info ci where e.free_total_score_status='0' and
		// e.teachclass_id=t.id and t.open_course_id=ca.id and
		// ca.course_id=ci.id and ca.semester_id='"
		// + semester_id + "' order by ci.id asc";
		String sql = "select course_id,course_name,credit ,course_time,teachclass_id,total_expend_score_rule,expend_score_status  from (select distinct ci.id as course_id,ci.name as course_name,ci.credit as credit ,ci.course_time as course_time,e.teachclass_id as teachclass_id,e.total_expend_score_rule as total_expend_score_rule,e.expend_score_status as expend_score_status  from entity_elective e,entity_teach_class t ,entity_course_active ca,entity_course_info ci where e.teachclass_id=t.id and t.open_course_id=ca.id and ca.course_id=ci.id  and ca.semester_id='"
				+ semester_id
				+ "' and e.expend_score_student_status='1') order by course_id";
		List courseList = new ArrayList();
		try {
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("course_id", rs.getString("course_id"));
				hash.put("course_name", rs.getString("course_name"));
				hash.put("credit", rs.getString("credit"));
				hash.put("course_time", rs.getString("course_time"));
				hash.put("teachclass_id", rs.getString("teachclass_id"));
				hash.put("total_expend_score_rule", rs
						.getString("total_expend_score_rule"));
				hash.put("expend_score_status", rs
						.getString("expend_score_status"));

				// hash.put("total_score_modify_rule", rs
				// .getString("total_score_modify_rule"));
				// hash.put("total_score_status", rs
				// .getString("total_score_status"));
				// hash
				// .put("exam_score_status", rs
				// .getString("exam_score_status"));
				// hash.put("total_expend_score_rule", rs
				// .getString("total_expend_score_rule"));
				// hash.put("total_score_modify_rule1", rs
				// .getString("total_score_modify_rule1"));
				courseList.add(hash);

			}

		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return courseList;

	}

	public List getModifyTotalScoreCourseList(Page page, List searchproperty, List orderproperty)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select course_id,course_name,credit,course_time,teachclass_id,total_score_modify_rule,total_score_modify_rule1 from (select distinct ci.id as course_id,ci.name as course_name,ci.credit as credit ,ci.course_time as course_time,e.teachclass_id as teachclass_id,ca.semester_id as semester_id ,e.total_score_modify_rule,e.total_score_modify_rule1 from entity_elective e,entity_teach_class t ,entity_course_active ca,entity_course_info ci where e.free_total_score_status='0' and e.teachclass_id=t.id and t.open_course_id=ca.id and ca.course_id=ci.id) ";
		sql = sql
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		List courseList = new ArrayList();
 
		try {
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("course_id", rs.getString("course_id"));
				hash.put("course_name", rs.getString("course_name"));
				hash.put("credit", rs.getString("credit"));
				hash.put("course_time", rs.getString("course_time"));
				hash.put("teachclass_id", rs.getString("teachclass_id"));
				hash.put("total_score_modify_rule", rs.getString("total_score_modify_rule"));
				hash.put("total_score_modify_rule1", rs.getString("total_score_modify_rule1"));
				courseList.add(hash);

			}

		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return courseList;

	}
	public List getCourseList(Page page, List searchproperty, List orderproperty)
	throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select course_id,course_name,credit,course_time,teachclass_id,total_score_rule,exam_score_status,total_score_status from (select distinct ci.id as course_id,ci.name as course_name,ci.credit as credit ,ci.course_time as course_time,e.teachclass_id as teachclass_id,e.total_score_rule as total_score_rule ,ca.semester_id as semester_id ,e.EXAM_SCORE_STATUS as exam_score_status,e.TOTAL_SCORE_STATUS as total_score_status from entity_elective e,entity_teach_class t ,entity_course_active ca,entity_course_info ci where e.free_total_score_status='0' and e.teachclass_id=t.id and t.open_course_id=ca.id and ca.course_id=ci.id) ";
		sql = sql
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		List courseList = new ArrayList();
		
		try {
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("course_id", rs.getString("course_id"));
				hash.put("course_name", rs.getString("course_name"));
				hash.put("credit", rs.getString("credit"));
				hash.put("course_time", rs.getString("course_time"));
				hash.put("teachclass_id", rs.getString("teachclass_id"));
				hash.put("total_score_rule", rs.getString("total_score_rule"));
				hash
						.put("exam_score_status", rs
								.getString("exam_score_status"));
				hash.put("total_score_status", rs
						.getString("total_score_status"));
				courseList.add(hash);
		
			}
		
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return courseList;
		
		}

	public List getPublishCourseList(Page page, List searchproperty, List orderproperty)
	throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select course_id,course_name,credit,course_time,teachclass_id,exam_score_status,total_score_status from (select distinct ci.id as course_id,ci.name as course_name,ci.credit as credit ,ci.course_time as course_time,e.teachclass_id as teachclass_id ,ca.semester_id as semester_id ,e.EXAM_SCORE_STATUS as exam_score_status,e.TOTAL_SCORE_STATUS as total_score_status from entity_elective e,entity_teach_class t ,entity_course_active ca,entity_course_info ci where e.free_total_score_status='0' and e.teachclass_id=t.id and t.open_course_id=ca.id and ca.course_id=ci.id) ";
		sql = sql
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		List courseList = new ArrayList();
		
		try {
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("course_id", rs.getString("course_id"));
				hash.put("course_name", rs.getString("course_name"));
				hash.put("credit", rs.getString("credit"));
				hash.put("course_time", rs.getString("course_time"));
				hash.put("teachclass_id", rs.getString("teachclass_id"));
				hash
						.put("exam_score_status", rs
								.getString("exam_score_status"));
				hash.put("total_score_status", rs
						.getString("total_score_status"));
				courseList.add(hash);
		
			}
		
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return courseList;
		
		}

	public int getCourseBySemesterIdNum(String semester_id)
			throws PlatformException {
		dbpool db = new dbpool();
		// String sql = "select distinct ci.id as course_id,ci.name as
		// course_name,ci.credit as credit ,ci.course_time as course_time,m.name
		// as major_name,e.teachclass_id as teachclass_id from entity_elective
		// e,entity_teach_class t ,entity_course_active ca,entity_course_info
		// ci,entity_major_info m where e.teachclass_id=t.id and
		// t.open_course_id=ca.id and ca.course_id=ci.id and ci.major_id=m.id
		// and ca.semester_id='"
		// + semester_id + "'";
		String sql = "select distinct ci.id as course_id,ci.name as course_name,ci.credit as credit ,ci.course_time as course_time,e.teachclass_id as teachclass_id,e.total_expend_score_rule as total_expend_score_rule  from entity_elective e,entity_teach_class t ,entity_course_active ca,entity_course_info ci where e.teachclass_id=t.id and t.open_course_id=ca.id and ca.course_id=ci.id  and ca.semester_id='"
				+ semester_id
				+ "' and e.expend_score_student_status='1' order by ci.id asc";

		return db.countselect(sql);

	}

	public int getCourseIdBySemesterIdNum(String semester_id)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select distinct a.course_id as course_id,b.name as course_name,a.semester_id as semester_id from entity_course_active a,entity_course_info b where a.course_id=b.id and a.semester_id ='"
				+ semester_id + "'";

		return db.countselect(sql);
	}

	public int getCourseListNum(List searchproperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select course_id,course_name,credit,course_time,teachclass_id,total_score_rule from (select distinct ci.id as course_id,ci.name as course_name,ci.credit as credit ,ci.course_time as course_time,e.teachclass_id as teachclass_id,e.total_score_rule as total_score_rule ,ca.semester_id as semester_id from entity_elective e,entity_teach_class t ,entity_course_active ca,entity_course_info ci where e.free_total_score_status='0' and e.teachclass_id=t.id and t.open_course_id=ca.id and ca.course_id=ci.id) ";
		sql = sql + Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);

	}
	public int getModifyTotalScoreCourseListNum(List searchproperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select course_id,course_name,credit,course_time,teachclass_id,total_score_modify_rule,total_score_modify_rule1 from (select distinct ci.id as course_id,ci.name as course_name,ci.credit as credit ,ci.course_time as course_time,e.teachclass_id as teachclass_id,e.total_score_modify_rule,total_score_modify_rule1 ,ca.semester_id as semester_id from entity_elective e,entity_teach_class t ,entity_course_active ca,entity_course_info ci where e.free_total_score_status='0' and e.teachclass_id=t.id and t.open_course_id=ca.id and ca.course_id=ci.id) ";
		sql = sql + Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);

	}
	public int getPublishCourseListNum(List searchproperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select course_id,course_name,credit,course_time,teachclass_id from (select distinct ci.id as course_id,ci.name as course_name,ci.credit as credit ,ci.course_time as course_time,e.teachclass_id as teachclass_id ,ca.semester_id as semester_id from entity_elective e,entity_teach_class t ,entity_course_active ca,entity_course_info ci where e.free_total_score_status='0' and e.teachclass_id=t.id and t.open_course_id=ca.id and ca.course_id=ci.id) ";
		sql = sql + Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);

	}
	public List getCourseList1(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		//String sql = "select course_id,course_name,credit,course_time,teachclass_id,total_expend_score_rule,exam_score_status,expend_score_status from (select distinct ci.id as course_id,ci.name as course_name,ci.credit as credit ,ci.course_time as course_time,e.teachclass_id as teachclass_id,e.total_expend_score_rule as total_expend_score_rule ,ca.semester_id as semester_id ,e.EXAM_SCORE_STATUS as exam_score_status,e.expend_score_status as expend_score_status from entity_elective e,entity_teach_class t ,entity_course_active ca,entity_course_info ci where e.free_total_score_status='0' and e.teachclass_id=t.id and t.open_course_id=ca.id and ca.course_id=ci.id) ";
		String sql = "select course_id,course_name,credit,course_time,teachclass_id,total_expend_score_rule,exam_score_status,expend_score_status,total_expendscore_modify_rule1 from (select distinct ci.id as course_id,ci.name as course_name,ci.credit as credit ,ci.course_time as course_time,e.teachclass_id as teachclass_id,e.total_expend_score_rule as total_expend_score_rule ,e.total_expendscore_modify_rule1 as total_expendscore_modify_rule1,ca.semester_id as semester_id ,e.EXAM_SCORE_STATUS as exam_score_status,e.expend_score_status as expend_score_status from entity_elective e,entity_teach_class t ,entity_course_active ca,entity_course_info ci where e.free_total_score_status='0' and e.expend_score_student_status ='1' and e.exam_score_status ='1' and e.teachclass_id=t.id and t.open_course_id=ca.id and ca.course_id=ci.id) ";
		sql = sql
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		List courseList = new ArrayList(); 
		try {
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("course_id", rs.getString("course_id"));
				hash.put("course_name", rs.getString("course_name"));
				hash.put("credit", rs.getString("credit"));
				hash.put("course_time", rs.getString("course_time"));
				hash.put("teachclass_id", rs.getString("teachclass_id"));
				hash.put("total_expend_score_rule", rs
						.getString("total_expend_score_rule"));
				hash
						.put("exam_score_status", rs
								.getString("exam_score_status"));
				hash.put("expend_score_status", rs
						.getString("expend_score_status"));
				hash.put("total_expendscore_modify_rule1",
						rs.getString("total_expendscore_modify_rule1"));
				courseList.add(hash);

			}

		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return courseList;

	}

	public int getCourseListNum1(List searchproperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select course_id,course_name,credit,course_time,teachclass_id,total_expend_score_rule from (select distinct ci.id as course_id,ci.name as course_name,ci.credit as credit ,ci.course_time as course_time,e.teachclass_id as teachclass_id,e.total_expend_score_rule as total_expend_score_rule ,ca.semester_id as semester_id from entity_elective e,entity_teach_class t ,entity_course_active ca,entity_course_info ci where e.free_total_score_status='0' and e.teachclass_id=t.id and t.open_course_id=ca.id and ca.course_id=ci.id) ";
		sql = sql + Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);

	}
}
