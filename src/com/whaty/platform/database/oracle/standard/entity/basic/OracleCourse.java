/*
 * OracleCourse.java
 *
 * Created on 2005��5��3��, ����9:59
 */

package com.whaty.platform.database.oracle.standard.entity.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.basic.Course;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

/**
 * 
 * @author Administrator
 */
public class OracleCourse extends Course {

	/** Creates a new instance of OracleCourse */
	public OracleCourse() {
	}

	public OracleCourse(String aid) {
		dbpool dbCourse = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		try {
			sql = "select name,credit,course_time,major_id,exam_type,course_type,teaching_type,course_status,ref_book,note,standard_fee,drift_fee,text_book,text_book_price,re0,re1,re2,re3,re4 from entity_course_info where id='"
					+ aid + "'";
			rs = dbCourse.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(aid);
				this.setName(rs.getString("name"));
				this.setCredit(rs.getFloat("credit"));
				this.setCourse_time(rs.getFloat("course_time"));
				this.setMajor_id(rs.getString("major_id"));
				this.setExam_type(rs.getString("exam_type"));
				this.setCourse_type(rs.getString("course_type"));
				this.setTeaching_type(rs.getString("teaching_type"));
				this.setCourse_status(rs.getString("course_status"));
				this.setRef_book(rs.getString("ref_book"));
				this.setNote(rs.getString("note"));
				this.setStandard_fee(rs.getString("standard_fee"));
				this.setDrift_fee(rs.getString("drift_fee"));
				this.setText_book(rs.getString("text_book"));
				this.setText_book_price(rs.getString("text_book_price"));
				this.setRedundance0(rs.getString("re0"));
				this.setRedundance1(rs.getString("re1"));
				this.setRedundance2(rs.getString("re2"));
				this.setRedundance3(rs.getString("re3"));
				this.setRedundance4(rs.getString("re4"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("");
		} finally {
			dbCourse.close(rs);
			dbCourse = null;
		}
	}

	public int add() throws PlatformException {
		if (isIdExist() != 0)
			throw new PlatformException("�γ̱���Ѵ��ڣ�");
		dbpool dbcourse = new dbpool();
		String sql = "";
		sql = "insert into entity_course_info (id,name,credit,course_time,major_id,exam_type,course_type,teaching_type,course_status,ref_book,note,standard_fee,drift_fee,text_book,text_book_price,re0,re1,re2,re3,re4) values ('"
				+ this.getId()
				+ "','"
				+ this.getName()
				+ "',"
				+ this.getCredit()
				+ ",'"
				+ this.getCourse_time()
				+ "','"
				+ this.getMajor_id()
				+ "','"
				+ this.getExam_type()
				+ "','"
				+ this.getCourse_type()
				+ "','"
				+ this.getTeaching_type()
				+ "','"
				+ this.getCourse_status()
				+ "','"
				+ this.getRef_book()
				+ "',?,'"
				+ this.getStandard_fee()
				+ "','"
				+ this.getDrift_fee()
				+ "','"
				+ this.getText_book()
				+ "','"
				+ this.getText_book_price()
				+ "','"
				+ this.getRedundance0()
				+ "','"
				+ this.getRedundance1()
				+ "','"
				+ this.getRedundance2()
				+ "','"
				+ this.getRedundance3()
				+ "','"
				+ this.getRedundance4()
				+ "')";
		int i = dbcourse.executeUpdate(sql, this.getNote());
		UserAddLog.setDebug("OracleCourse.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/**
	 * Ϊ�γ���ӿμ� public int addCoursewares(List coursewares) { dbpool db = new
	 * dbpool(); String sql = ""; sql = "delete from entity_course_cware where
	 * course_id='"+this.getId()+"'"; int count = db.executeUpdate(sql);
	 * if(coursewares!=null) { sql = "insert into entity_course_cware
	 * (id,course_id,courseware_id) select
	 * to_char(s_lrn_course_cware_id.nextval),'"+this.getId()+"',id from
	 * lrn_PE_TCH_COURSEWARE where id in ("; for(int i=0;i<coursewares.size();i++) {
	 * Courseware cware = (Courseware)coursewares.get(i); if (i !=
	 * coursewares.size() - 1) { sql = sql + "'" + cware.getId() + "',"; } else {
	 * sql = sql + "'" + cware.getId() + "')"; } } } int i =
	 * db.executeUpdate(sql); return i; }
	 * 
	 * @throws PlatformException
	 */
	public int delete() throws PlatformException {
		if (isOpenCourse() != 0)
			throw new PlatformException(
					"<script language='javascript'>alert('�ÿγ��Ѿ�����,����ɾ��');window.history.back();</script>");
		dbpool dbcourse = new dbpool();
		String sql = "";
		sql = "delete entity_course_info where id='" + this.getId() + "'";
		int i = dbcourse.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleCourse.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/**
	 * �õ���ÿγ̹�j�����пμ� public List getCoursewares() { dbpool dbcourse = new
	 * dbpool(); String sql = ""; ArrayList cwarelist = new ArrayList();
	 * MyResultSet rs = null; sql = "select
	 * w.id,w.title,w.author,w.publisher,w.founder from select
	 * id,title,author,publisher,founder from lrn_PE_TCH_COURSEWARE w,
	 * lrn_course_cware cw where cw.course_id='"+this.getId()+"' and
	 * cw.coursware_id=w.id"; try { rs = dbcourse.executeQuery(sql);
	 * while(rs!=null && rs.next()) { OracleCourseware oraclecware = new
	 * OracleCourseware(); oraclecware.setId(rs.getString("id"));
	 * oraclecware.setName(rs.getString("title"));
	 * oraclecware.setAuthor(rs.getString("author"));
	 * oraclecware.setPublisher(rs.getString("publisher"));
	 * oraclecware.setFounder(rs.getString("founder"));
	 * cwarelist.add(oraclecware); } } catch (java.sql.SQLException e) {
	 * Log.setError("Course.getCoursewares() error()" + sql); } finally {
	 * dbcourse.close(rs); dbcourse = null; return cwarelist; } }
	 * 
	 * public List getSemesters() { ArrayList semesterlist = new ArrayList();
	 * return semesterlist; }
	 * 
	 * ɾ����ÿγ̹�j�����пμ� public void removeCoursewares(List coursewares) { dbpool db =
	 * new dbpool(); String sql = ""; if(coursewares!=null) { sql = "delete from
	 * lrn_course_cware where course_id in ("; for(int i=0;i<coursewares.size();i++) {
	 * Courseware cware = (Courseware)coursewares.get(i); if (i !=
	 * coursewares.size() - 1) { sql = sql + "'" + cware.getId() + "',"; } else {
	 * sql = sql + "'" + cware.getId() + "')"; } } } int count =
	 * db.executeUpdate(sql); }
	 */
	public int update() {
		dbpool dbcourse = new dbpool();
		String sql = "";
		sql = "update entity_course_info set name='" + this.getName()
				+ "', credit='" + this.getCredit() + "', course_time='"
				+ this.getCourse_time() + "',major_id='" + this.getMajor_id()
				+ "',exam_type='" + this.getExam_type() + "', course_type='"
				+ this.getCourse_type() + "',teaching_type='"
				+ this.getTeaching_type() + "', course_status='"
				+ this.getCourse_status() + "', ref_book='"
				+ this.getRef_book() + "', note=?, standard_fee='"
				+ this.getStandard_fee() + "',drift_fee='"
				+ this.getDrift_fee() + "', text_book='" + this.getText_book()
				+ "', text_book_price='" + this.getText_book_price()
				+ "', re0='" + this.getRedundance0() + "', re1='"
				+ this.getRedundance1() + "', re2='" + this.getRedundance2()
				+ "', re3='" + this.getRedundance3() + "', re4='"
				+ this.getRedundance4() + "' where id='" + this.getId() + "'";
		// System.out.print(sql);
		int i = dbcourse.executeUpdate(sql, this.getNote());
		UserUpdateLog.setDebug("OracleCourse.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/** �жϸÿγ�ID�Ƿ���ڣ�0Ϊ�����ڣ�����0Ϊ���� */
	public int isIdExist() {
		dbpool dbcourse = new dbpool();
		String sql = "";
		sql = "select id from entity_course_info where id='" + this.getId()
				+ "'";
		int i = dbcourse.countselect(sql);
		return i;
	}

	/** �жϸÿγ��Ƿ��Ѿ����ڿ��ογ̣�0Ϊû�п��Σ�����0Ϊ�Ѿ��ǿ��ογ� */
	public int isOpenCourse() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from entity_course_active where course_id='"
				+ this.getId() + "'";
		int i = db.countselect(sql);
		return i;
	}

	public int removeTeachBookFromCourse(String[] teachbook_ids) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_teachbook_course where course_id = '"
				+ this.getId() + "' and  teachbook_id in (";
		if (teachbook_ids == null)
			return 0;
		for (int i = 0; i < teachbook_ids.length; i++) {
			if (i < teachbook_ids.length - 1) {
				sql = sql + "'" + teachbook_ids[i] + "',";
			} else {
				sql = sql + "'" + teachbook_ids[i] + "')";
			}
		}
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleCourse.removeTeachBookFromCourse(String[] teachbook_ids) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int addTeachbookToCourse(String[] teachbook_ids) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_teachbook_course(id,teachbook_id,course_id) select to_char(s_entity_teachbook_course_id.nextval),id,'"
				+ this.getId() + "' from entity_teachbook_info where id in (";
		if (teachbook_ids == null)
			return 0;
		for (int i = 0; i < teachbook_ids.length; i++) {
			if (i < teachbook_ids.length - 1) {
				sql = sql + "'" + teachbook_ids[i] + "',";
			} else {
				sql = sql + "'" + teachbook_ids[i] + "')";
			}
		}
		sql = sql
				+ "and id not in (select teachbook_id from entity_teachbook_course where course_id='"
				+ this.getId() + "')";
		// System.out.println();
		// System.out.println("add=" + sql);
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleCourse.addTeachbookToCourse() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public List selectTeachBookId(String[] teachbook_ids) {
		dbpool db = new dbpool();
		List list = new ArrayList();
		String sql = "";
		sql = "select t.teachbook_id from entity_teachbook_course t where t.course_id='"
				+ this.getId() + "' and t.teachbook_id in('";
		for (int i = 0; i < teachbook_ids.length; i++) {
			sql = sql + teachbook_ids[i] + "'";
			if (i != teachbook_ids.length - 1) {
				sql = sql + ",'";
			} else {
				sql = sql + ")";
			}
		}
		MyResultSet rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				list.add(rs.getString("teachbook_id"));
			}
			return list;
		} catch (java.sql.SQLException e) {
			Log.setError("Course.selectTeachBookId() error()" + sql);
			return null;
		} finally {
			db.close(rs);
			db = null;
		}
	}

}
