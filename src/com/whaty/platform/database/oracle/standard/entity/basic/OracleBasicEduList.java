package com.whaty.platform.database.oracle.standard.entity.basic;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleOpenCourse;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleTeachClass;
import com.whaty.platform.entity.basic.BasicEduList;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.util.log.Log;

public class OracleBasicEduList implements BasicEduList {

	private String SQLCOURSE = "select id,name,credit,course_time,course_status,major_id,major_name,exam_type,"
			+ "course_type,note,standard_fee,drift_fee,text_book,ref_book,text_book_price from "
			+ "(select c.id as id,c.name,c.credit,c.course_time,c.course_status,c.major_id as major_id,"
			+ "c.exam_type,c.course_type,c.note,c.standard_fee,c.drift_fee,c.text_book,c.ref_book,c.text_book_price,m.name as major_name  "
			+ "from entity_course_info c,entity_major_info m where m.id(+)=c.major_id)";

	private String SQLCOURSETYPE = "select id,name from entity_course_type";

	private String SQLCOURSEWARE = "select id,name,author,type,publisher,to_char(reg_date,'yyyy-mm-dd') as reg_date,note,link,mc,remark,pos,founder from entity_PE_TCH_COURSEWARE";

	private String SQLCOURSEWARETYPE = "select dir_id,dir_name,dir_note,dir_father,dir_date from (select dir_id,dir_name,dir_note,dir_father,to_char(dir_date,'yyyy-mm-dd') as dir_date from entity_courseware_type where dir_id<>'0')";

	private String SQLCOURSEWARETYPETREE = "select dir_id,dir_name,dir_note,dir_father,dir_date,level from (select dir_id,dir_name,dir_note,dir_father,to_char(dir_date,'yyyy-mm-dd') as dir_date from entity_courseware_type where dir_id!=0) start with dir_father = 0 connect by prior dir_id = dir_father";

	private String SQLSEMESTER = "select id,name,start_date,end_date,start_elective,end_elective,selected from (select s.id,s.name,to_char(s.start_date,'yyyy-mm-dd') as start_date,to_char(s.end_date,'yyyy-mm-dd') as end_date,to_char(s.elective_start_date,'yyyy-mm-dd') as start_elective,to_char(s.elective_end_date,'yyyy-mm-dd') as end_elective,c.code as selected from pe_semester s, enum_const c where s.flag_active=c.id(+))";

	private String SQLTEACHCLASS = "select id,name,open_course_id from entity_teachclass_info";

	public int getCoursesNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLCOURSE + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getCourses(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("id"));
		} else {
			OrderProperties = orderproperty;
		}
		String sql = SQLCOURSE
				+ Conditions
						.convertToCondition(searchproperty, OrderProperties);
		MyResultSet rs = null;
		ArrayList courses = null;
		try {
			courses = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleCourse course = new OracleCourse();
				course.setNote(rs.getString("note"));
				course.setId(rs.getString("id"));
				course.setName(rs.getString("name"));
				course.setCredit(rs.getString("credit"));
				course.setCourse_time(rs.getString("course_time"));
				course.setExam_type(rs.getString("exam_type"));
				course.setCourse_type(rs.getString("course_type"));
				course.setCourse_status(rs.getString("course_status"));
				course.setRef_book(rs.getString("ref_book"));
				course.setStandard_fee(rs.getString("standard_fee"));
				course.setDrift_fee(rs.getString("drift_fee"));
				course.setText_book(rs.getString("text_book"));
				course.setText_book_price(rs.getString("text_book_price"));
				course.setMajor_id(rs.getString("major_id"));
				course.setMajor_name(rs.getString("major_name"));
				courses.add(course);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return courses;
	}

	public int getCourseTypesNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLCOURSETYPE
				+ Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getCourseTypes(Page page, List searchproperty,
			List orderproperty) {
		dbpool db = new dbpool();
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("id"));
		} else {
			OrderProperties = orderproperty;
		}
		String sql = SQLCOURSETYPE
				+ Conditions
						.convertToCondition(searchproperty, OrderProperties);
		MyResultSet rs = null;
		ArrayList coursetypes = null;
		try {
			db = new dbpool();
			coursetypes = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleCourseType coursetype = new OracleCourseType();
				coursetype.setId(rs.getString("id"));
				coursetype.setName(rs.getString("name"));
				coursetypes.add(coursetype);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return coursetypes;
	}

	public int getSemestersNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLSEMESTER + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getSemesters(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLSEMESTER;
		String condition = Conditions.convertToCondition(searchproperty,
				orderproperty);
		//sql = "select id,name,start_date,end_date,start_elective,end_elective,start_test,end_test,selected from (select id,name,to_char(start_date,'yyyy-mm-dd') as start_date,to_char(end_date,'yyyy-mm-dd') as end_date,to_char(start_elective,'yyyy-mm-dd') as start_elective,to_char(end_elective,'yyyy-mm-dd') as end_elective,to_char(start_test,'yyyy-mm-dd') as start_test,to_char(end_test,'yyyy-mm-dd') as end_test,selected from ("
		//		+ sql + condition + "))";
		sql = sql + condition;
		// System.out.println(sql);
		MyResultSet rs = null;
		List semesters = new ArrayList();
		try {
			if (page == null)
				rs = db.executeQuery(sql);
			else
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			while (rs != null && rs.next()) {
				OracleSemester semester = new OracleSemester();
				semester.setId(rs.getString("id"));
				semester.setName(rs.getString("name"));
				semester.setEnd_date(rs.getString("end_date"));
				semester.setStart_date(rs.getString("start_date"));
				semester.setStart_elective(rs.getString("start_elective"));
				semester.setEnd_elective(rs.getString("end_elective"));
//				semester.setStart_test(rs.getString("start_test"));
//				semester.setEnd_test(rs.getString("end_test"));
				semester.setSelected(rs.getInt("selected"));
				semesters.add(semester);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("BasicDataList.getSemester() error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return semesters;
	}

	public List getSemesters(List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("id","1"));
		} else {
			OrderProperties = orderproperty;
		}
		String sql = SQLSEMESTER;
		String condition = Conditions.convertToCondition(searchproperty,
				OrderProperties);
		sql = sql + condition;
		MyResultSet rs = null;
		List semesters = new ArrayList();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleSemester semester = new OracleSemester();
				semester.setId(rs.getString("id"));
				semester.setName(rs.getString("name"));
				semester.setEnd_date(rs.getString("end_date"));
				semester.setStart_date(rs.getString("start_date"));
				semester.setStart_elective(rs.getString("start_elective"));
				semester.setEnd_elective(rs.getString("end_elective"));
//				semester.setStart_test(rs.getString("start_test"));
//				semester.setEnd_test(rs.getString("end_test"));
				semester.setSelected(rs.getInt("selected"));
				semesters.add(semester);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("BasicDataList.getSemester() error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return semesters;
	}

	public int getTeachClassesNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLTEACHCLASS
				+ Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getTeachClasses(Page page, List searchproperty,
			List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLTEACHCLASS;
		String condition = Conditions.convertToCondition(searchproperty,
				orderproperty);
		sql = sql + condition;
		MyResultSet rs = null;
		List teachclasses = new ArrayList();
		try {
			rs = db.execute_oracle_page(sql, page.getPageInt(), page
					.getPageSize());
			while (rs != null && rs.next()) {
				OracleTeachClass teachclass = new OracleTeachClass();
				teachclass.setId(rs.getString("id"));
				teachclass.setName(rs.getString("name"));
				OracleOpenCourse openCourse = new OracleOpenCourse();
				openCourse.setId(rs.getString("open_course_id"));
				teachclass.setOpenCourse(openCourse);
				teachclasses.add(teachclass);
			}
		} catch (java.sql.SQLException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teachclasses;
	}
}
