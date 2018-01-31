package com.whaty.platform.database.oracle.standard.test.exam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.fee.deal.OracleOtherFeeType;
import com.whaty.platform.database.oracle.standard.test.OracleTestUser;
import com.whaty.platform.test.exam.ExamList;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.util.log.Log;

public class OracleExamList implements ExamList {
	private String SQLBAT = "select id,name,to_char(s_date,'yyyy-mm-dd') as s_date,to_char(e_date,'yyyy-mm-dd') as e_date,status,to_char(examroom_s_date,'yyyy-mm-dd') as examroom_s_date,to_char(examroom_e_date,'yyyy-mm-dd') as examroom_e_date from test_exambatch_info";
	
	private String SQLBATACTIVE = "select id,name,to_char(s_date,'yyyy-mm-dd') as s_date,to_char(e_date,'yyyy-mm-dd') as e_date,status,to_char(examroom_s_date,'yyyy-mm-dd') as examroom_s_date,to_char(examroom_e_date,'yyyy-mm-dd') as examroom_e_date from test_exambatch_info where status='1'";

	private String SQLCOURSE = "select id,name,to_char(s_date,'yyyy-mm-dd hh24:Mi:ss') as s_date,to_char(e_date,'yyyy-mm-dd hh24:Mi:ss') as e_date,test_batch_id,course_id,EXAMSEQUENCE_ID from test_examcourse_info";
	
	//private String SQLCOURSE1 = "select id,name,to_char(s_date,'yyyy-mm-dd hh24:Mi:ss') as s_date,to_char(e_date,'yyyy-mm-dd hh24:Mi:ss') as e_date,test_batch_id,open_course_id,basicSEQUENCE_ID from test_examcourse_info where test_batch_id='"+ activeBatchId +"'";

	// private String SQLCOURSE="select a.id,name,to_char(a.s_date,'yyyy-mm-dd
	// hh24:Mi:ss') as s_date,to_char(a.e_date,'yyyy-mm-dd hh24:Mi:ss') as
	// e_date,a.test_batch_id,a.course_id,b.examsequence,c.title as seqname from
	// test_examcourse_info a,entity_course_active b,test_examsequence_info c
	// where a.course_id=b.course_id and b.examsequence=c.id";
	private String SQLROOM = "select id,name,address,room_no,course_id from test_examroom_info from test_examroom_info";

	private String SQLEXAMUSER = "select id,batch_id,user_id,examcode,note,status from test_examuser_batch where id <>'0'";

	private String SQLBASICSEQUENCE = "select id,title,note from test_basicsequence_info ";

	private String SQLOTHERFEETYPE = "select id,name from entity_otherfee_type ";
	/**
	 * @author shu
	 * @return �õ���εĸ���
	 */
	public int getBatchNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLBAT + Conditions.convertToCondition(null, null);
		int i = db.countselect(sql);
		return i;
	}
	
	
	public int getBatchNumStatus(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select count(*) as num from test_exambatch_info t where t.status='1'" + Conditions.convertToCondition(searchproperty, null);
		//int i = db.countselect(sql);
		MyResultSet rs = null;
		rs = db.executeQuery(sql);
		int i = 0;
		try {
			while (rs != null && rs.next()) {
			i = Integer.parseInt(rs.getString("num"));
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
		}
		
		return i;
	}
	
/**
 * @author shu
 * @return �õ������Ϣ��
 */
	public List getBatches(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLBAT
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList oracleExamBatches = null;
		try {
			db = new dbpool();
			oracleExamBatches = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleExamBatch oracleExamBatch = new OracleExamBatch();
				oracleExamBatch.setId(rs.getString("id"));
				oracleExamBatch.setName(rs.getString("name"));
				oracleExamBatch.setStartDate(rs.getString("s_date"));
				oracleExamBatch.setEndDate(rs.getString("e_date"));
				oracleExamBatch.setStatus(rs.getString("status"));
				oracleExamBatch.setExamRoomStartDate(rs
						.getString("examroom_s_date"));
				oracleExamBatch.setExamRoomEndDate(rs
						.getString("examroom_e_date"));
				oracleExamBatches.add(oracleExamBatch);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return oracleExamBatches;
	}
	
	
	/**
	 * @author shu
	 * @return �õ������Ϣ��
	 */
		public List getActiveBatches(Page page, List searchproperty, List orderproperty) {
			dbpool db = new dbpool();
			String sql = SQLBATACTIVE
					+ Conditions.convertToCondition(searchproperty, orderproperty);
			MyResultSet rs = null;
			ArrayList oracleExamBatches = null;
			try {
				db = new dbpool();
				oracleExamBatches = new ArrayList();
				if (page != null) {
					int pageint = page.getPageInt();
					int pagesize = page.getPageSize();
					rs = db.execute_oracle_page(sql, pageint, pagesize);
				} else {
					rs = db.executeQuery(sql);
				}
				while (rs != null && rs.next()) {
					OracleExamBatch oracleExamBatch = new OracleExamBatch();
					oracleExamBatch.setId(rs.getString("id"));
					oracleExamBatch.setName(rs.getString("name"));
					oracleExamBatch.setStartDate(rs.getString("s_date"));
					oracleExamBatch.setEndDate(rs.getString("e_date"));
					oracleExamBatch.setStatus(rs.getString("status"));
					oracleExamBatch.setExamRoomStartDate(rs
							.getString("examroom_s_date"));
					oracleExamBatch.setExamRoomEndDate(rs
							.getString("examroom_e_date"));
					oracleExamBatches.add(oracleExamBatch);
				}
			} catch (Exception e) {
				
			} finally {
				db.close(rs);
				db = null;

			}
			return oracleExamBatches;
		}

		
		public List getTableMsg6(Page page, List searchproperty, List orderproperty,String batch_id) {		
			ArrayList  ExamList = new ArrayList();
			dbpool db = new dbpool();
			if (orderproperty == null) {
				orderproperty = new ArrayList();
				orderproperty.add(new OrderProperty("edutype_id"));
			}
			
			String conditions = Conditions.convertToCondition(searchproperty, orderproperty);;
//			if(conditions!=null&&!"".equals(conditions)){
//				conditions = conditions.substring(6);
//				conditions = " and" +conditions;}

			String sql = 
				"select kaoqu_id,grade_id,semester_id,edutype_id,shenfen_id,major_id,opencourse_id,course_name,room_no,room_num,address,teacher " +
				"from ( select  t.kaoqu_id as kaoqu_id,t.grade_id as grade_id,esi.id as semester_id, t.edu_type_id as edutype_id,t.shenfen_id as shenfen_id,t.major_id as major_id, " + 
				" ti.open_course_id as opencourse_id ,ti.name as course_name,t.room_no as room_no,t.room_num  as room_num,t.address as address,t.teacher as teacher " + 
				"   from test_examroom_info t, test_examcourse_info ti,entity_course_active eca,entity_semester_info esi " + 
				"  where t.course_id = ti.id   and eca.id = ti.open_course_id and esi.id = eca.semester_id and t.batch_id = '"+batch_id+"' )";
			sql = sql +  conditions;
			
			MyResultSet rs = null;
		//	MyResultSet recruitinfo = null;
			
			try {
			   if(page!=null)
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			   else
				   rs=db.executeQuery(sql);
				while (rs != null && rs.next()) {
				List studentMsg = new ArrayList();
//kaoqu_id,grade_id,,edutype_id,shenfen_id,major_id,opencourse_id,course_name,semester_id,room_no,room_num,address,teacher 
				     studentMsg.add(rs.getString("kaoqu_id"));//0 
				     studentMsg.add(rs.getString("grade_id"));//1
				     studentMsg.add(rs.getString("edutype_id"));//2
				     studentMsg.add(rs.getString("shenfen_id"));//3
				     studentMsg.add(rs.getString("major_id"));//4
				     studentMsg.add(rs.getString("opencourse_id"));//5
				     studentMsg.add(rs.getString("course_name"));//6
				     studentMsg.add(rs.getString("semester_id"));//7
				     studentMsg.add(rs.getString("room_no"));//8
				     studentMsg.add(rs.getString("room_num"));//9
				     studentMsg.add(rs.getString("address"));//10
				     studentMsg.add(rs.getString("teacher"));//11
				   //  studentMsg.add(rs.getString("matrnum"));//12studentMsg.add(rs.getString("noexamnum"));//12��������
				   // studentMsg.add(rs.getString("examnum"));//13��������
				     ExamList.add(studentMsg);
				}
			} catch (java.sql.SQLException e) {
			//	System.out.println(e.toString());
				Log.setError("StudentList.getTableMsg() error! " + sql);
			} finally {
				db.close(rs);
				db = null;
				return ExamList;
			}

		}
		
		
	public int getCourseNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLCOURSE + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getCourses(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLCOURSE
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList oracleExamCourses = null;
		try {
			db = new dbpool();
			oracleExamCourses = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleExamCourse oracleExamCourse = new OracleExamCourse();
				oracleExamCourse.setId(rs.getString("id"));
				oracleExamCourse.setName(rs.getString("name"));
				oracleExamCourse.setStartDate(rs.getString("s_date"));
				oracleExamCourse.setEndDate(rs.getString("e_date"));
				oracleExamCourse.setExamBatch(new OracleExamBatch(rs
						.getString("test_batch_id")));
				oracleExamCourse.setCourse_id(rs.getString("course_id"));
				oracleExamCourses.add(oracleExamCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return oracleExamCourses;
	}

	public List getCourses1(String activeBatchId,Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = "select id,name,to_char(s_date,'yyyy-mm-dd hh24:Mi:ss') as s_date,to_char(e_date,'yyyy-mm-dd hh24:Mi:ss') as e_date,test_batch_id,open_course_id,basicSEQUENCE_ID from test_examcourse_info where test_batch_id='"+ activeBatchId +"'"
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList oracleExamCourses = null;
		try {
			db = new dbpool();
			oracleExamCourses = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleExamCourse oracleExamCourse = new OracleExamCourse();
				oracleExamCourse.setId(rs.getString("id"));
				oracleExamCourse.setName(rs.getString("name"));
				oracleExamCourse.setStartDate(rs.getString("s_date"));
				oracleExamCourse.setEndDate(rs.getString("e_date"));
				oracleExamCourse.setExamBatch(new OracleExamBatch(rs
						.getString("test_batch_id")));
				oracleExamCourse.setCourse_id(rs.getString("open_course_id"));
				oracleExamCourses.add(oracleExamCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return oracleExamCourses;
	}
	
	public List getSiteCourses(String site_id, Page page, List searchproperty,
			List orderproperty) {
		dbpool db = new dbpool();
		String sql = "select id,name,s_date,e_date,test_batch_id,course_id,examsequence_id,examsequence_title from (select a.id,a.name,to_char(a.s_date,'yyyy-mm-dd hh24:Mi:ss') as s_date,"
				+ "to_char(a.e_date,'yyyy-mm-dd hh24:Mi:ss') as e_date,a.test_batch_id,a.course_id,a.examsequence_id,b.title as examsequence_title "
				+ "from test_examcourse_info a,test_examsequence_info b where a.examsequence_id=b.id and a.id in (select course_id from test_examuser_course "
				+ "where user_id in (select id from entity_student_info where site_id = '"
				+ site_id + "')))";
		sql += Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList oracleExamCourses = null;
		try {
			db = new dbpool();
			oracleExamCourses = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleExamCourse oracleExamCourse = new OracleExamCourse();
				oracleExamCourse.setId(rs.getString("id"));
				oracleExamCourse.setName(rs.getString("name"));
				oracleExamCourse.setStartDate(rs.getString("s_date"));
				oracleExamCourse.setEndDate(rs.getString("e_date"));
				oracleExamCourse.setExamBatch(new OracleExamBatch(rs
						.getString("test_batch_id")));
				oracleExamCourse.setCourse_id(rs.getString("course_id"));
				OracleExamSequence examSequence = new OracleExamSequence();
				examSequence.setId(rs.getString("examsequence_id"));
				examSequence.setTitle(rs.getString("examsequence_title"));
				oracleExamCourse.setSequence(examSequence);
				oracleExamCourses.add(oracleExamCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return oracleExamCourses;
	}

/**
 * @author shu
 * @param site_id
 * @param page
 * @param searchproperty
 * @param orderproperty
 * @return �õ�վ���µ���Ҫ���俼���Ŀγ̵��б?
 */
	public List getSiteCoursesNewGroup(String site_id, Page page, List searchproperty,
			List orderproperty) {
		dbpool db = new dbpool();
/*		String sql = "select id,name,s_date,e_date,test_batch_id,course_id,examsequence_id,examsequence_title from (select a.id,a.name,to_char(a.s_date,'yyyy-mm-dd hh24:Mi:ss') as s_date,"
				+ "to_char(a.e_date,'yyyy-mm-dd hh24:Mi:ss') as e_date,a.test_batch_id,a.course_id,a.examsequence_id,b.title as examsequence_title "
				+ "from test_examcourse_info a,test_examsequence_info b where a.examsequence_id=b.id and a.id in (select course_id from test_examuser_course "
				+ "where user_id in (select id from entity_student_info where site_id = '"
				+ site_id + "')))";
*/
		String sql = 
			"select course_id,course_name,edu_type_id,edu_type_name,major_id,major_name,kaoqu_id, kaoqu_name,shenfen_id,grade_id,grade_name," +
			"count(users) as useres" + 
			"  from (select teci.id as course_id,  teci.name as course_name, teuc.user_id as users,eet.id as edu_type_id,eet.name as edu_type_name," + 
			"    emi.id as major_id,emi.name as major_name,eki.id as kaoqu_id,eki.name as kaoqu_name,esi.shenfen_id as shenfen_id," + 
			"       egi.id as grade_id,egi.name as grade_name, esi.site_id as site_id,esi.name as site_name " + 
			"            from test_examcourse_info teci, test_examuser_course teuc," + 
			"          test_examuser_batch teub,test_exambatch_info tebi,test_user_info tui," + 
			"          entity_student_info esi,entity_edu_type eet,entity_major_info emi,entity_kaoqu_info eki,entity_grade_info egi, entity_site_info esti" + 
			"         where teci.id = teuc.course_id" + 
			"        and  teub.user_id = teuc.user_id" + 
			"        and teub.batch_id = tebi.id" + 
			"        and tebi.status='1'" + 
			"        and tui.id = teuc.user_id" + 
			"        and esi.reg_no = tui.login_id" + 
			"        and eet.id = esi.edutype_id" + 
			"        and esi.major_id = emi.id" + 
			"        and esi.kaoqu_id = eki.id" + 
			"        and esi.grade_id = egi.id" + 
			"        and esi.site_id = esti.id" + 
			"         )" + 
			" group by course_id,course_name,edu_type_id,edu_type_name,major_id,major_name,kaoqu_id,kaoqu_name,shenfen_id,grade_id,grade_name";

		sql += Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList oracleExamCourses = null;
		try {
			db = new dbpool();
			oracleExamCourses = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleExamCourse oracleExamCourse = new OracleExamCourse();
			//	oracleExamCourse.setId(rs.getString("id"));
			//	oracleExamCourse.setName(rs.getString("name"));
			//	oracleExamCourse.setStartDate(rs.getString("s_date"));
			//	oracleExamCourse.setEndDate(rs.getString("e_date"));
			//	oracleExamCourse.setExamBatch(new OracleExamBatch(rs
			//			.getString("test_batch_id")));
			//	oracleExamCourse.setCourse_id(rs.getString("course_id"));
			//	OracleExamSequence examSequence = new OracleExamSequence();
			//	examSequence.setId(rs.getString("examsequence_id"));
			//	examSequence.setTitle(rs.getString("examsequence_title"));
			//	oracleExamCourse.setSequence(examSequence);
				
//				oracleExamCourse.setCourse_id(rs.getString("course_id"));	
//				oracleExamCourse.setCourse_name(rs.getString("course_name"));
//				oracleExamCourse.setEdu_type_id(rs.getString("edu_type_id"));
//				oracleExamCourse.setEdu_type_name(rs.getString("edu_type_name"));
//				oracleExamCourse.setMajor_id(rs.getString("major_id"));
//				oracleExamCourse.setMajor_name(rs.getString("major_name"));
//				oracleExamCourse.setKaoqu_id(rs.getString("kaoqu_id"));
//				oracleExamCourse.setKaoqu_name(rs.getString("kaoqu_name"));
//				oracleExamCourse.setShenfen_id(rs.getString("shenfen_id"));
////				oracleExamCourse.setCourse_id(rs.getString("course_id"));
//				oracleExamCourse.setGrade_id(rs.getString("grade_id"));
//				oracleExamCourse.setGrade_name(rs.getString("grade_name"));
//				oracleExamCourse.setUseres(rs.getString("useres"));
				
				oracleExamCourses.add(oracleExamCourse);
				
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return oracleExamCourses;
	}
	
	
	/**
	 * @author shu
	 * @param site_id
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return �õ�վ���µ���Ҫ���俼���Ŀγ̵��б?
	 */
		public List getSiteCoursesNewGroup(String activeBatchId,String site_id, Page page, List searchproperty,
				List orderproperty) {
			dbpool db = new dbpool();
	/*		String sql = "select id,name,s_date,e_date,test_batch_id,course_id,examsequence_id,examsequence_title from (select a.id,a.name,to_char(a.s_date,'yyyy-mm-dd hh24:Mi:ss') as s_date,"
					+ "to_char(a.e_date,'yyyy-mm-dd hh24:Mi:ss') as e_date,a.test_batch_id,a.course_id,a.examsequence_id,b.title as examsequence_title "
					+ "from test_examcourse_info a,test_examsequence_info b where a.examsequence_id=b.id and a.id in (select course_id from test_examuser_course "
					+ "where user_id in (select id from entity_student_info where site_id = '"
					+ site_id + "')))";
	*/
			String sql = 
				"select course_id,course_name,edu_type_id,edu_type_name,major_id,major_name,kaoqu_id, kaoqu_name,shenfen_id,grade_id,grade_name," +
				"count(users) as useres" + 
				"  from (select teci.id as course_id,  teci.name as course_name, teuc.user_id as users,eet.id as edu_type_id,eet.name as edu_type_name," + 
				"    emi.id as major_id,emi.name as major_name,eki.id as kaoqu_id,eki.name as kaoqu_name,esi.shenfen_id as shenfen_id," + 
				"       egi.id as grade_id,egi.name as grade_name, esi.site_id as site_id,esi.name as site_name " + 
				"            from test_examcourse_info teci, test_examuser_course teuc," + 
				"          test_examuser_batch teub,test_exambatch_info tebi,test_user_info tui," + 
				"          entity_student_info esi,entity_edu_type eet,entity_major_info emi,entity_kaoqu_info eki,entity_grade_info egi, entity_site_info esti" + 
				"         where teci.id = teuc.course_id" + 
				"        and  teub.user_id = teuc.user_id" + 
				"        and teub.batch_id = tebi.id" + 
				"        and tebi.status='1'" + 
				"        and tui.id = teuc.user_id" + 
				"        and esi.reg_no = tui.login_id" + 
				"        and eet.id = esi.edutype_id" + 
				"        and esi.major_id = emi.id" + 
				"        and esi.kaoqu_id = eki.id" + 
				"        and esi.grade_id = egi.id" + 
				"        and esi.site_id = esti.id and teub.batch_id='" +activeBatchId + "' " +
				"        and teci.test_batch_id = teub.batch_id  )" + 
//				"         )" + 
				" group by course_id,course_name,edu_type_id,edu_type_name,major_id,major_name,kaoqu_id,kaoqu_name,shenfen_id,grade_id,grade_name";

			sql += Conditions.convertToCondition(searchproperty, orderproperty);
			MyResultSet rs = null;
			ArrayList oracleExamCourses = null;
			try {
				db = new dbpool();
				oracleExamCourses = new ArrayList();
				if (page != null) {
					int pageint = page.getPageInt();
					int pagesize = page.getPageSize();
					rs = db.execute_oracle_page(sql, pageint, pagesize);
				} else {
					rs = db.executeQuery(sql);
				}
				while (rs != null && rs.next()) {
					OracleExamCourse oracleExamCourse = new OracleExamCourse();
				//	oracleExamCourse.setId(rs.getString("id"));
				//	oracleExamCourse.setName(rs.getString("name"));
				//	oracleExamCourse.setStartDate(rs.getString("s_date"));
				//	oracleExamCourse.setEndDate(rs.getString("e_date"));
				//	oracleExamCourse.setExamBatch(new OracleExamBatch(rs
				//			.getString("test_batch_id")));
				//	oracleExamCourse.setCourse_id(rs.getString("course_id"));
				//	OracleExamSequence examSequence = new OracleExamSequence();
				//	examSequence.setId(rs.getString("examsequence_id"));
				//	examSequence.setTitle(rs.getString("examsequence_title"));
				//	oracleExamCourse.setSequence(examSequence);
					
//					oracleExamCourse.setCourse_id(rs.getString("course_id"));	
//					oracleExamCourse.setCourse_name(rs.getString("course_name"));
//					oracleExamCourse.setEdu_type_id(rs.getString("edu_type_id"));
//					oracleExamCourse.setEdu_type_name(rs.getString("edu_type_name"));
//					oracleExamCourse.setMajor_id(rs.getString("major_id"));
//					oracleExamCourse.setMajor_name(rs.getString("major_name"));
//					oracleExamCourse.setKaoqu_id(rs.getString("kaoqu_id"));
//					oracleExamCourse.setKaoqu_name(rs.getString("kaoqu_name"));
//					oracleExamCourse.setShenfen_id(rs.getString("shenfen_id"));
////					oracleExamCourse.setCourse_id(rs.getString("course_id"));
//					oracleExamCourse.setGrade_id(rs.getString("grade_id"));
//					oracleExamCourse.setGrade_name(rs.getString("grade_name"));
//					oracleExamCourse.setUseres(rs.getString("useres"));
					
					oracleExamCourses.add(oracleExamCourse);
					
				}
			} catch (Exception e) {
				
			} finally {
				db.close(rs);
				db = null;

			}
			return oracleExamCourses;
		}
		
		
	
	public int getRoomNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLROOM + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getRooms(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLROOM
				+ Conditions.convertToAndCondition(searchproperty,
						orderproperty);
		MyResultSet rs = null;
		ArrayList oracleExamRooms = null;
		try {
			db = new dbpool();
			oracleExamRooms = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleExamRoom oracleExamRoom = new OracleExamRoom();
				oracleExamRoom.setId(rs.getString("id"));
				oracleExamRoom.setName(rs.getString("name"));
				oracleExamRoom.setAddress(rs.getString("address"));
				oracleExamRoom.setRoomNo(rs.getString("Room_no"));
				oracleExamRoom.setExamCourse(new OracleExamCourse(rs
						.getString("course_id")));
				oracleExamRooms.add(oracleExamRoom);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return oracleExamRooms;
	}

	public int getExamUserNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLEXAMUSER + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getExamUsers(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLEXAMUSER
				+ Conditions.convertToAndCondition(searchproperty,
						orderproperty);
		MyResultSet rs = null;
		ArrayList oracleExamUsers = null;
		try {
			db = new dbpool();
			oracleExamUsers = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleExamUser oracleExamUser = new OracleExamUser();
				oracleExamUser.setId(rs.getString("id"));
				oracleExamUser.setExamBatch(new OracleExamBatch(rs
						.getString("batch_id")));
				oracleExamUser.setTestUser(new OracleTestUser(rs
						.getString("user_id")));
				oracleExamUser.setExamcode(rs.getString("examcode"));
				oracleExamUser.setExamcode(rs.getString("note"));
				oracleExamUser.setExamcode(rs.getString("status"));
				oracleExamUsers.add(oracleExamUser);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return oracleExamUsers;
	}
	

	public List getExamUsersNewGroup(String course_id,String kaoqu_id,String edu_type_id,String major_id,
			String grade_id,String shenfen_id, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
	//	String sql = SQLEXAMUSER
		String sql = 
			"select users as user_id from(select course_id,course_name,edu_type_id,edu_type_name,major_id,major_name,kaoqu_id, kaoqu_name,shenfen_id,grade_id,grade_name," +
			"users" + 
			"" + 
			"  from (select teci.id as course_id, teci.name as course_name, teuc.user_id as users,eet.id as edu_type_id,eet.name as edu_type_name," + 
			"    emi.id as major_id,emi.name as major_name,eki.id as kaoqu_id,eki.name as kaoqu_name,esi.shenfen_id as shenfen_id," + 
			"       egi.id as grade_id,egi.name as grade_name, esi.site_id as site_id,esi.name as site_name" + 
			"            from test_examcourse_info teci, test_examuser_course teuc," + 
			"          test_examuser_batch teub,test_exambatch_info tebi,test_user_info tui," + 
			"          entity_student_info esi,entity_edu_type eet,entity_major_info emi,entity_kaoqu_info eki,entity_grade_info egi, entity_site_info esti" + 
			"         where teci.id = teuc.course_id" + 
			"        and  teub.user_id = teuc.user_id" + 
			"        and teub.batch_id = tebi.id" + 
			"        and tebi.status='1'" + 
			"        and tui.id = teuc.user_id" + 
			"        and esi.reg_no = tui.login_id" + 
			"        and eet.id = esi.edutype_id" + 
			"        and esi.major_id = emi.id" + 
			"        and esi.kaoqu_id = eki.id" + 
			"        and esi.grade_id = egi.id" + 
			"        and esi.site_id = esti.id" + 
			")        ) where "

				+ Conditions.convertToAndCondition(searchproperty,
						orderproperty).substring(4);
		MyResultSet rs = null;
		ArrayList oracleExamUsers = null;
		try {
			db = new dbpool();
			oracleExamUsers = new ArrayList();
/*			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
*/ //			} else {
				rs = db.executeQuery(sql);
//			}
			while (rs != null && rs.next()) {
				OracleExamUser oracleExamUser = new OracleExamUser();
			//	oracleExamUser.setId(rs.getString("id"));
			//	oracleExamUser.setExamBatch(new OracleExamBatch(rs
			//			.getString("batch_id")));
				oracleExamUser.setTestUser(new OracleTestUser(rs
						.getString("user_id")));
				
			//	oracleExamUser.setExamcode(rs.getString("examcode"));
			//	oracleExamUser.setExamcode(rs.getString("note"));
			//	oracleExamUser.setExamcode(rs.getString("status"));
				oracleExamUsers.add(oracleExamUser);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return oracleExamUsers;//�����ⷵ�ص�ʱ��Ҳȡ��ֵ�ˡ�
	}
	
	
	/**
	 * @author shu
	 * @param activeBatchId
	 * @param course_id
	 * @param kaoqu_id
	 * @param edu_type_id
	 * @param major_id
	 * @param grade_id
	 * @param shenfen_id
	 * @return �õ�������µĿ��Ե���Ա�б?
	 * @throws PlatformException
	 */	
	
	public List getExamUsersNewGroup(String activeBatchId,String course_id,String kaoqu_id,String edu_type_id,String major_id,
			String grade_id,String shenfen_id, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
	//	String sql = SQLEXAMUSER
		String sql = 
			"select users as user_id from(select course_id,course_name,edu_type_id,edu_type_name,major_id,major_name,kaoqu_id, kaoqu_name,shenfen_id,grade_id,grade_name," +
			"users" + 
			"" + 
			"  from (select teci.id as course_id, teci.name as course_name, teuc.user_id as users,eet.id as edu_type_id,eet.name as edu_type_name," + 
			"    emi.id as major_id,emi.name as major_name,eki.id as kaoqu_id,eki.name as kaoqu_name,esi.shenfen_id as shenfen_id," + 
			"       egi.id as grade_id,egi.name as grade_name, esi.site_id as site_id,esi.name as site_name" + 
			"            from test_examcourse_info teci, test_examuser_course teuc," + 
			"          test_examuser_batch teub,test_exambatch_info tebi,test_user_info tui," + 
			"          entity_student_info esi,entity_edu_type eet,entity_major_info emi,entity_kaoqu_info eki,entity_grade_info egi, entity_site_info esti" + 
			"         where teci.id = teuc.course_id" + 
			"        and  teub.user_id = teuc.user_id" + 
			"        and teub.batch_id = tebi.id" + 
			"        and tebi.status='1'" + 
			"        and tui.id = teuc.user_id" + 
			"        and esi.reg_no = tui.login_id" + 
			"        and eet.id = esi.edutype_id" + 
			"        and esi.major_id = emi.id" + 
			"        and esi.kaoqu_id = eki.id" + 
			"        and esi.grade_id = egi.id" + 
			"        and esi.site_id = esti.id and teub.batch_id = '" +activeBatchId+"'"+ 
			")        ) where "

				+ Conditions.convertToAndCondition(searchproperty,
						orderproperty).substring(4);
		MyResultSet rs = null;
		ArrayList oracleExamUsers = null;
		try {
			db = new dbpool();
			oracleExamUsers = new ArrayList();
/*			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
*/ //			} else {
				rs = db.executeQuery(sql);
//			}
			while (rs != null && rs.next()) {
				OracleExamUser oracleExamUser = new OracleExamUser();
			//	oracleExamUser.setId(rs.getString("id"));
			//	oracleExamUser.setExamBatch(new OracleExamBatch(rs
			//			.getString("batch_id")));
				oracleExamUser.setTestUser(new OracleTestUser(rs
						.getString("user_id")));
				
			//	oracleExamUser.setExamcode(rs.getString("examcode"));
			//	oracleExamUser.setExamcode(rs.getString("note"));
			//	oracleExamUser.setExamcode(rs.getString("status"));
				oracleExamUsers.add(oracleExamUser);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return oracleExamUsers;//�����ⷵ�ص�ʱ��Ҳȡ��ֵ�ˡ�
	}
	

	public List getExamSequences(Page page, List searchproperty,
			List orderproperty) {
		dbpool db = new dbpool();

		String sql = "select id,title,s_date,e_date,note, testbatch_id,batchname,basicsequence_id from "
				+ "(select a.id as id,a.title as title,to_char(a.startdate,'yyyy-mm-dd HH24:MI:SS') as s_date,"
				+ "to_char(a.enddate,'yyyy-mm-dd HH24:MI:SS') as e_date,a.note as note,a.testbatch_id as testbatch_id,"
				+ "b.name as batchname,a.basicsequence_id from test_examsequence_info a, test_exambatch_info b where a.testbatch_id=b.id and b.status='1')";
		sql = sql
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		EntityLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("id", rs.getString("id"));
				hash.put("title", rs.getString("title"));
				hash.put("s_date", rs.getString("s_date"));
				hash.put("e_date", rs.getString("e_date"));
				hash.put("note", rs.getString("note"));
				hash.put("testbatch_id", rs.getString("testbatch_id"));
				hash.put("batchname", rs.getString("batchname"));
				list.add(hash);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return list;
	}

	public int getExamSequencesNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "select id,title,s_date,e_date,note, testbatch_id from (select id,title,to_char(startdate,'yyyy-mm-dd') as s_date,to_char(enddate,'yyyy-mm-dd') as e_date,note,testbatch_id from test_examsequence_info where testbatch_id in(select id from test_exambatch_info where status='1'))";
		sql = sql + Conditions.convertToCondition(searchproperty, null);
		EntityLog.setDebug(sql);
		int i = db.countselect(sql);
		return i;
	}

	public List getActiveExamSequences(Page page, List searchproperty,
			List orderproperty) {
		dbpool db = new dbpool();
		String sql = "select id,title,startdate,enddate,note,testbatch_id from (select a.id,title,to_char(a.startdate,'yyyy-mm-dd') as startdate,to_char(a.enddate,'yyyy-mm-dd') as enddate,note,testbatch_id from test_examsequence_info a,test_exambatch_info b where a.testbatch_id=b.id and b.status='1')";
		sql = sql
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		EntityLog.setDebug(sql);
		MyResultSet rs = null;
		List list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleExamSequence es = new OracleExamSequence();
				es.setId(rs.getString("id"));
				es.setTitle(rs.getString("title"));
				es.setStartDate(rs.getString("startdate"));
				es.setEndDate(rs.getString("enddate"));
				es.setNote(rs.getString("note"));
				es.setBatchId(rs.getString("testbatch_id"));
				list.add(es);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}
/**
 * @author shu
 * @param page
 * @param searchproperty
 * @param orderproperty
 * @return ͨ����Σ����Σ��γ̺�4��ѯ����Σ��ó��εĿ��ԵĿγ̵������Ϣ�� 
 */
	public List getExamCourse(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();

		String sql = "select id ,course_name,course_credit,course_time,s_date,e_date,test_batch_id,course_id,examsequence_id,title,batchname,course_type from "
				+ "(select distinct a.id as id ,a.name as course_name,e.credit as course_credit,e.course_time as course_time,to_char(a.s_date,'yyyy-mm-dd hh24:Mi:ss') as s_date,"
				+ "to_char(a.e_date,'yyyy-mm-dd hh24:Mi:ss') as e_date,a.test_batch_id as test_batch_id,a.course_id as course_id,"
				+ "a.examsequence_id as examsequence_id,c.title as title,d.name as batchname,a.course_type from test_examcourse_info a,"
				+ "entity_course_active b,test_examsequence_info c,test_exambatch_info d,entity_course_info e where e.id = a.course_id and a.course_id=b.course_id and "
				+ "a.examsequence_id=c.id and a.test_batch_id=d.id)";
		/*
		String sql = "select id ,course_name,s_date,e_date,test_batch_id,course_id,examsequence_id,title,batchname,course_type from "
				+ "(select distinct a.id as id ,a.name as course_name,to_char(a.s_date,'yyyy-mm-dd hh24:Mi:ss') as s_date,"
				+ "to_char(a.e_date,'yyyy-mm-dd hh24:Mi:ss') as e_date,a.test_batch_id as test_batch_id,a.course_id as course_id,"
				+ "a.examsequence_id as examsequence_id,c.title as title,d.name as batchname,a.course_type from test_examcourse_info a,"
				+ "entity_course_active b,test_examsequence_info c,test_exambatch_info d where a.course_id=b.course_id and "
				+ "a.examsequence_id=c.id and a.test_batch_id=d.id)";
		 */
		sql = sql
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		EntityLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("id", rs.getString("id"));
				hash.put("course_name", rs.getString("course_name"));
				hash.put("course_credit", rs.getString("course_credit"));
			//	hash.put("course_id", rs.getString("course_id"));
				hash.put("course_time", rs.getString("course_time"));
				hash.put("s_date", rs.getString("s_date"));
				hash.put("e_date", rs.getString("e_date"));
				hash.put("test_batch_id", rs.getString("test_batch_id"));
				hash.put("course_id", rs.getString("course_id"));
				hash.put("examsequence_id", rs.getString("examsequence_id"));
				hash.put("title", rs.getString("title"));
				hash.put("batchname", rs.getString("batchname"));
				hash.put("course_type", rs.getString("course_type"));
				list.add(hash);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return list;
	}

	public List getSiteExamCourse(String site_id, Page page,
			List searchproperty, List orderproperty) {
		dbpool db = new dbpool();

		String sql = "select id ,course_name,s_date,e_date,test_batch_id,course_id,examsequence_id,"
				+ "title,batchname from (select distinct a.id as id ,a.name as course_name,"
				+ "to_char(a.s_date,'yyyy-mm-dd hh24:Mi:ss') as s_date,to_char(a.e_date,'yyyy-mm-dd hh24:Mi:ss') "
				+ "as e_date,a.test_batch_id as test_batch_id,a.course_id as course_id,b.examsequence as "
				+ "examsequence_id,c.title as title,d.name as batchname from test_examcourse_info a,"
				+ "entity_course_active b,test_examsequence_info c,test_exambatch_info d,test_examuser_batch e where a.course_id=b.course_id and "
				+ "a.examsequence_id=c.id and a.test_batch_id=d.id and d.id = e.batch_id and e.user_id in (select id from entity_student_info where site_id = '"
				+ site_id + "'))";
		sql = sql
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		EntityLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("id", rs.getString("id"));
				hash.put("course_name", rs.getString("course_name"));
				hash.put("s_date", rs.getString("s_date"));
				hash.put("e_date", rs.getString("e_date"));
				hash.put("test_batch_id", rs.getString("test_batch_id"));
				hash.put("course_id", rs.getString("course_id"));
				hash.put("examsequence_id", rs.getString("examsequence_id"));
				hash.put("title", rs.getString("title"));
				hash.put("batchname", rs.getString("batchname"));
				list.add(hash);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return list;
	}

	public int getExamCourseNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "select id ,course_name,s_date,e_date,test_batch_id,course_id,examsequence_id,"
				+ "title,batchname from (select distinct a.id as id ,a.name as course_name,"
				+ "to_char(a.s_date,'yyyy-mm-dd hh24:Mi:ss') as s_date,to_char(a.e_date,'yyyy-mm-dd hh24:Mi:ss') "
				+ "as e_date,a.test_batch_id as test_batch_id,a.course_id as course_id,a.examsequence_id as "
				+ "examsequence_id,c.title as title,d.name as batchname from test_examcourse_info a,"
				+ "entity_course_active b,test_examsequence_info c,test_exambatch_info d where a.course_id=b.course_id and "
				+ "a.examsequence_id=c.id and a.test_batch_id=d.id)";
		sql = sql + Conditions.convertToCondition(searchproperty, null);
		EntityLog.setDebug(sql);
		int i = db.countselect(sql);
		return i;
	}

	public int getSiteExamCourseNum(String site_id, List searchproperty) {
		dbpool db = new dbpool();
		String sql = "select id ,course_name,s_date,e_date,test_batch_id,course_id,examsequence_id,"
				+ "title,batchname from (select distinct a.id as id ,a.name as course_name,"
				+ "to_char(a.s_date,'yyyy-mm-dd hh24:Mi:ss') as s_date,to_char(a.e_date,'yyyy-mm-dd hh24:Mi:ss') "
				+ "as e_date,a.test_batch_id as test_batch_id,a.course_id as course_id,b.examsequence as "
				+ "examsequence_id,c.title as title,d.name as batchname from test_examcourse_info a,"
				+ "entity_course_active b,test_examsequence_info c,test_exambatch_info d,test_examuser_batch e where a.course_id=b.course_id and "
				+ "a.examsequence_id=c.id and a.test_batch_id=d.id and d.id = e.batch_id and e.user_id in (select id from entity_student_info where site_id = '"
				+ site_id + "'))";
		sql = sql + Conditions.convertToCondition(searchproperty, null);
		EntityLog.setDebug(sql);
		int i = db.countselect(sql);
		return i;
	}

	public List getExamStat(String batch_id, String site_id)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		if (site_id == null || site_id.equals(""))
			sql = "select site_name,grade_name,major_name,edutype_name,course_name,examsequence_name,"
					+ "s_date,e_date,num,site_id,grade_id,major_id,edutype_id,examsequence_id,course_id,course_type,address,teacher from"
					+ "(select c.name as site_name,d.name as grade_name,e.name as major_name,f.name as edutype_name,"
					+ "a.course_name,g.title as examsequence_name,to_char(g.startdate,'yyyy-mm-dd hh24:mi:ss') as s_date,"
					+ "to_char(g.enddate,'yyyy-mm-dd hh24:mi:ss') as e_date,a.num,a.site_id,a.grade_id,a.major_id,a.edutype_id,"
					+ "a.examsequence_id,a.course_id,b.course_type,nvl(h.address,'') as address,nvl(h.teacher,'') as teacher,nvl(h.room_no,0) as room_no from (select a.name as course_name,a.examsequence_id,a.id as course_id,"
					+ "c.site_id,c.grade_id,c.major_id,c.edutype_id,b.room_id,count(b.id) as num from test_examcourse_info a,test_examuser_course b,"
					+ "entity_student_info c where a.test_batch_id='"
					+ batch_id
					+ "' and a.id=b.course_id and b.user_id=c.id group by a.name,a.id,c.site_id,"
					+ "c.grade_id,c.major_id,c.edutype_id,a.examsequence_id,b.room_id) a,"
					+ "test_examcourse_info b,entity_site_info c,entity_grade_info d,entity_major_info e,"
					+ "entity_edu_type f,test_examsequence_info g,test_examroom_info h where a.course_id=b.id and a.site_id=c.id and "
					+ "a.grade_id=d.id and a.major_id=e.id and a.edutype_id=f.id and a.examsequence_id=g.id and a.room_id=h.id(+)) "
					+ "order by examsequence_id,grade_id,major_id,edutype_id,course_id,room_no";
		else
			sql = "select site_name,grade_name,major_name,edutype_name,course_name,examsequence_name,"
					+ "s_date,e_date,num,site_id,grade_id,major_id,edutype_id,examsequence_id,course_id,course_type,address,teacher from"
					+ "(select c.name as site_name,d.name as grade_name,e.name as major_name,f.name as edutype_name,"
					+ "a.course_name,g.title as examsequence_name,to_char(g.startdate,'yyyy-mm-dd hh24:mi:ss') as s_date,"
					+ "to_char(g.enddate,'yyyy-mm-dd hh24:mi:ss') as e_date,a.num,a.site_id,a.grade_id,a.major_id,a.edutype_id,"
					+ "a.examsequence_id,a.course_id,b.course_type,nvl(h.address,'') as address,nvl(h.teacher,'') as teacher,nvl(h.room_no,0) as room_no from (select a.name as course_name,a.examsequence_id,a.id as course_id,"
					+ "c.site_id,c.grade_id,c.major_id,c.edutype_id,b.room_id,count(b.id) as num from test_examcourse_info a,test_examuser_course b,"
					+ "entity_student_info c where a.test_batch_id='"
					+ batch_id
					+ "' and c.site_id='"
					+ site_id
					+ "' and a.id=b.course_id and b.user_id=c.id group by a.name,a.id,c.site_id,"
					+ "c.grade_id,c.major_id,c.edutype_id,a.examsequence_id,b.room_id) a,"
					+ "test_examcourse_info b,entity_site_info c,entity_grade_info d,entity_major_info e,"
					+ "entity_edu_type f,test_examsequence_info g,test_examroom_info h where a.course_id=b.id and a.site_id=c.id and "
					+ "a.grade_id=d.id and a.major_id=e.id and a.edutype_id=f.id and a.examsequence_id=g.id and a.room_id=h.id(+)) "
					+ "order by examsequence_id,grade_id,major_id,edutype_id,course_id,room_no";

		EntityLog.setDebug(sql);
		MyResultSet rs = null;
		MyResultSet rs1 = null;
		ArrayList list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			rs = db.executeQuery(sql);
			int count = 0;
			while (rs != null && rs.next()) {
				List item = new ArrayList();
				item.add(rs.getString("site_name"));
				item.add(rs.getString("grade_name"));
				item.add(rs.getString("major_name"));
				item.add(rs.getString("edutype_name"));
				item.add(rs.getString("course_name"));
				item.add(rs.getString("examsequence_name"));
				String s_date = rs.getString("s_date");
				String e_date = rs.getString("e_date");
				String s_day = "";
				String s_time = "";
				String e_time = "";
				String addressStr = "";
				if (s_date != null && s_date.length() >= 10)
					s_day = s_date.substring(0, 10);
				if (s_day.length() > 0)
					s_day = s_day.substring(0, 4) + "��" + s_day.substring(5, 7)
							+ "��" + s_day.substring(8) + "��";
				if (s_date != null && s_date.length() >= 16)
					s_time = s_date.substring(11, 16);
				if (e_date != null && e_date.length() >= 16)
					e_time = e_date.substring(11, 16);
				item.add(s_day);
				item.add(s_time + "--" + e_time);
				count += rs.getInt("num");
				item.add(rs.getString("num"));
				/*
				 * String addSql = "select distinct address as address from
				 * test_examroom_info a,test_examcourse_info b," +
				 * "test_examuser_course c,entity_student_info d where c.room_id =
				 * a.id and " + "b.id = c.course_id and c.user_id=d.id and
				 * b.EXAMSEQUENCE_ID = '" + rs.getString("examsequence_id") + "'
				 * and b.id = '" + rs.getString("course_id") + "' and d.major_id = '" +
				 * rs.getString("major_id") + "' and d.site_id = '" + site_id + "'
				 * and d.edutype_id='" + rs.getString("edutype_id") + "' and
				 * d.grade_id = '" + rs.getString("grade_id") + "' ";
				 * 
				 * rs1 = db.executeQuery(addSql); while (rs1 != null &&
				 * rs1.next()) { addressStr += rs1.getString("address") + ","; }
				 * db.close(rs1); if (addressStr.length() > 0) { addressStr =
				 * addressStr.substring(0, addressStr.length() - 1); }
				 * item.add(addressStr);
				 */
				item.add(rs.getString("course_type"));
				item.add(rs.getString("address"));
				item.add(rs.getString("teacher"));
				list.add(item);
			}
			List item = new ArrayList();
			item.add("�ϼƣ�");
			item.add(Integer.toString(count));
			list.add(item);
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return list;
	}

	public List getExamStat(String batch_id, String site_id, String order)
			throws PlatformException {
		dbpool db = new dbpool();
		if (order == null || order.equals(""))
			order = "grade_id,major_id,edutype_id,course_id,examsequence_id";
		String sql = "";
		if (site_id == null || site_id.equals(""))
			sql = "select site_name,grade_name,major_name,edutype_name,course_name,examsequence_name,"
					+ "s_date,e_date,num,site_id,grade_id,major_id,edutype_id,examsequence_id,course_id,course_type from"
					+ "(select c.name as site_name,d.name as grade_name,e.name as major_name,f.name as edutype_name,"
					+ "a.course_name,g.title as examsequence_name,to_char(b.s_date,'yyyy-mm-dd hh24:mi:ss') as s_date,"
					+ "to_char(b.e_date,'yyyy-mm-dd hh24:mi:ss') as e_date,a.num,a.site_id,a.grade_id,a.major_id,a.edutype_id,"
					+ "a.examsequence_id,a.course_id,b.course_type from (select a.name as course_name,a.examsequence_id,a.id as course_id,"
					+ "c.site_id,c.grade_id,c.major_id,c.edutype_id,count(b.id) as num from test_examcourse_info a,test_examuser_course b,"
					+ "entity_student_info c where a.test_batch_id='"
					+ batch_id
					+ "' and a.id=b.course_id and b.user_id=c.id group by a.name,a.id,c.site_id,"
					+ "c.grade_id,c.major_id,c.edutype_id,a.examsequence_id) a,"
					+ "test_examcourse_info b,entity_site_info c,entity_grade_info d,entity_major_info e,"
					+ "entity_edu_type f,test_examsequence_info g where a.course_id=b.id and a.site_id=c.id and "
					+ "a.grade_id=d.id and a.major_id=e.id and a.edutype_id=f.id and a.examsequence_id=g.id) "
					+ "order by " + order;
		else
			sql = "select site_name,grade_name,major_name,edutype_name,course_name,examsequence_name,"
					+ "s_date,e_date,num,site_id,grade_id,major_id,edutype_id,examsequence_id,course_id,course_type from"
					+ "(select c.name as site_name,d.name as grade_name,e.name as major_name,f.name as edutype_name,"
					+ "a.course_name,g.title as examsequence_name,to_char(b.s_date,'yyyy-mm-dd hh24:mi:ss') as s_date,"
					+ "to_char(b.e_date,'yyyy-mm-dd hh24:mi:ss') as e_date,a.num,a.site_id,a.grade_id,a.major_id,a.edutype_id,"
					+ "a.examsequence_id,a.course_id,b.course_type from (select a.name as course_name,a.examsequence_id,a.id as course_id,"
					+ "c.site_id,c.grade_id,c.major_id,c.edutype_id,count(b.id) as num from test_examcourse_info a,test_examuser_course b,"
					+ "entity_student_info c where a.test_batch_id='"
					+ batch_id
					+ "' and c.site_id='"
					+ site_id
					+ "' and a.id=b.course_id and b.user_id=c.id group by a.name,a.id,c.site_id,"
					+ "c.grade_id,c.major_id,c.edutype_id,a.examsequence_id) a,"
					+ "test_examcourse_info b,entity_site_info c,entity_grade_info d,entity_major_info e,"
					+ "entity_edu_type f,test_examsequence_info g where a.course_id=b.id and a.site_id=c.id and "
					+ "a.grade_id=d.id and a.major_id=e.id and a.edutype_id=f.id and a.examsequence_id=g.id) "
					+ "order by " + order;

		EntityLog.setDebug(sql);
		MyResultSet rs = null;
		MyResultSet rs1 = null;
		ArrayList list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			rs = db.executeQuery(sql);
			int count = 0;
			while (rs != null && rs.next()) {
				List item = new ArrayList();
				item.add(rs.getString("site_name"));
				item.add(rs.getString("grade_name"));
				item.add(rs.getString("major_name"));
				item.add(rs.getString("edutype_name"));
				item.add(rs.getString("course_name"));
				item.add(rs.getString("examsequence_name"));
				String s_date = rs.getString("s_date");
				String e_date = rs.getString("e_date");
				String s_day = "";
				String s_time = "";
				String e_time = "";
				String addressStr = "";
				if (s_date != null && s_date.length() >= 10)
					s_day = s_date.substring(0, 10);
				if (s_day.length() > 0)
					s_day = s_day.substring(0, 4) + "��" + s_day.substring(5, 7)
							+ "��" + s_day.substring(8) + "��";
				if (s_date != null && s_date.length() >= 16)
					s_time = s_date.substring(11, 16);
				if (e_date != null && e_date.length() >= 16)
					e_time = e_date.substring(11, 16);
				item.add(s_day);
				item.add(s_time + "~" + e_time);
				count += rs.getInt("num");
				String numStr = rs.getString("num");
				String addSql = "select address,room_num from (select distinct address as address,room_num from test_examroom_info a,test_examcourse_info b,"
						+ "test_examuser_course c,entity_student_info d where c.room_id = a.id and "
						+ "b.id = c.course_id and c.user_id=d.id and b.EXAMSEQUENCE_ID = '"
						+ rs.getString("examsequence_id")
						+ "' and b.id = '"
						+ rs.getString("course_id")
						+ "' and d.major_id = '"
						+ rs.getString("major_id")
						+ "' and d.site_id = '"
						+ site_id
						+ "' and d.edutype_id='"
						+ rs.getString("edutype_id")
						+ "' and d.grade_id = '"
						+ rs.getString("grade_id") + "')";

				rs1 = db.executeQuery(addSql);
				if (db.countselect(addSql) > 0) {
					numStr = "";
				}
				while (rs1 != null && rs1.next()) {
					addressStr += "," + rs1.getString("address");
					numStr += "," + rs1.getString("room_num");
				}
				db.close(rs1);
				if (numStr.indexOf(",") == 0) {
					numStr = numStr.substring(1);
				}
				item.add(numStr);
				if (addressStr.indexOf(",") == 0) {
					addressStr = addressStr.substring(1);
				}
				item.add(addressStr);
				item.add(rs.getString("course_type"));
				list.add(item);
			}
			List item = new ArrayList();
			item.add("�ϼƣ�");
			item.add(Integer.toString(count));
			list.add(item);
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return list;
	}

	public List getExamStatByCourse(String batch_id, String site_id,
			String order) throws PlatformException {
		dbpool db = new dbpool();
		if (order == null || order.equals(""))
			order = "course_name";
		String sql = "";
		if (site_id == null || site_id.equals(""))
			sql = "select site_name,course_id,course_name,course_type,examsequence_name,s_date,e_date,sum(num) as num,examsequence_id,site_id,address,room_num from "
					+ "(select site_name,grade_name,major_name,edutype_name,course_name,examsequence_name,"
					+ "s_date,e_date,num,x.site_id,grade_id,major_id,edutype_id,examsequence_id,x.course_id,course_type,nvl(y.address,'') as address,nvl(y.room_num,'') as room_num from"
					+ "(select c.name as site_name,d.name as grade_name,e.name as major_name,f.name as edutype_name,"
					+ "a.course_name,g.title as examsequence_name,to_char(b.s_date,'yyyy-mm-dd hh24:mi:ss') as s_date,"
					+ "to_char(b.e_date,'yyyy-mm-dd hh24:mi:ss') as e_date,a.num,a.site_id,a.grade_id,a.major_id,a.edutype_id,"
					+ "a.examsequence_id,a.course_id,b.course_type from (select a.name as course_name,a.examsequence_id,a.id as course_id,"
					+ "c.site_id,c.grade_id,c.major_id,c.edutype_id,count(b.id) as num from test_examcourse_info a,test_examuser_course b,"
					+ "entity_student_info c where a.test_batch_id='"
					+ batch_id
					+ "' and a.id=b.course_id and b.user_id=c.id group by a.name,a.id,c.site_id,"
					+ "c.grade_id,c.major_id,c.edutype_id,a.examsequence_id) a,"
					+ "test_examcourse_info b,entity_site_info c,entity_grade_info d,entity_major_info e,"
					+ "entity_edu_type f,test_examsequence_info g where a.course_id=b.id and a.site_id=c.id and "
					+ "a.grade_id=d.id and a.major_id=e.id and a.edutype_id=f.id and a.examsequence_id=g.id) x,test_examroom_info y where x.course_id=y.course_id(+) and x.site_id=y.site_id(+)) "
					+ "group by site_id,site_name,course_id,course_name,course_type,examsequence_id,examsequence_name,s_date,e_date,address,room_num "
					+ "order by site_id," + order;
		else
			sql = "select site_name,course_id,course_name,course_type,examsequence_name,s_date,e_date,sum(num) as num,examsequence_id,site_id,address,room_num, teacher from "
					+ "(select site_name,grade_name,major_name,edutype_name,course_name,examsequence_name,"
					+ "s_date,e_date,num,x.site_id,grade_id,major_id,edutype_id,examsequence_id,x.course_id,course_type,nvl(y.address,'') as address,nvl(y.room_num,'') as room_num, y.teacher from"
					+ "(select c.name as site_name,d.name as grade_name,e.name as major_name,f.name as edutype_name,"
					+ "a.course_name,g.title as examsequence_name,to_char(b.s_date,'yyyy-mm-dd hh24:mi:ss') as s_date,"
					+ "to_char(b.e_date,'yyyy-mm-dd hh24:mi:ss') as e_date,a.num,a.site_id,a.grade_id,a.major_id,a.edutype_id,"
					+ "a.examsequence_id,a.course_id,b.course_type from (select a.name as course_name,a.examsequence_id,a.id as course_id,"
					+ "c.site_id,c.grade_id,c.major_id,c.edutype_id,count(b.id) as num from test_examcourse_info a,test_examuser_course b,"
					+ "entity_student_info c where a.test_batch_id='"
					+ batch_id
					+ "' and c.site_id='"
					+ site_id
					+ "' and a.id=b.course_id and b.user_id=c.id group by a.name,a.id,c.site_id,"
					+ "c.grade_id,c.major_id,c.edutype_id,a.examsequence_id) a,"
					+ "test_examcourse_info b,entity_site_info c,entity_grade_info d,entity_major_info e,"
					+ "entity_edu_type f,test_examsequence_info g where a.course_id=b.id and a.site_id=c.id and "
					+ "a.grade_id=d.id and a.major_id=e.id and a.edutype_id=f.id and a.examsequence_id=g.id) x,test_examroom_info y where x.course_id=y.course_id(+) and x.site_id=y.site_id(+)) "
					+ "group by site_id,site_name,course_id,course_name,course_type,examsequence_id,examsequence_name,s_date,e_date,address,room_num, teacher "
					+ "order by site_id," + order;

		EntityLog.setDebug("getExamStatByCourse@sql: " + sql);
		MyResultSet rs = null;
		MyResultSet rs1 = null;
		ArrayList list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			rs = db.executeQuery(sql);
			int count = 0;
			while (rs != null && rs.next()) {
				//int num = 0;
				List item = new ArrayList();
				item.add(rs.getString("site_name"));
				item.add(rs.getString("course_name"));
				item.add(rs.getString("examsequence_name"));
				item.add(rs.getString("course_type"));
				String s_date = rs.getString("s_date");
				String e_date = rs.getString("e_date");
				String siteId = rs.getString("site_id");
				String s_day = "";
				String s_time = "";
				String e_time = "";
				String addressStr = "";
				if (s_date != null && s_date.length() >= 10)
					s_day = s_date.substring(0, 10);
				if (s_day.length() > 0)
					s_day = s_day.substring(0, 4) + "��" + s_day.substring(5, 7)
							+ "��" + s_day.substring(8) + "��";
				if (s_date != null && s_date.length() >= 16)
					s_time = s_date.substring(11, 16);
				if (e_date != null && e_date.length() >= 16)
					e_time = e_date.substring(11, 16);
				item.add(s_day);
				item.add(s_time + "~" + e_time);
				/*String numStr = rs.getString("num");
				
				 * String addSql = "select distinct address,room_num from
				 * (select distinct address as address,room_num from
				 * test_examroom_info a,test_examcourse_info b," +
				 * "test_examuser_course c where c.room_id = a.id and " + "b.id =
				 * c.course_id and b.EXAMSEQUENCE_ID = '" +
				 * rs.getString("examsequence_id") + "' and b.id = '" +
				 * rs.getString("course_id") + "')"; if (site_id != null &&
				 * !site_id.equals(""))
				 */
				/*String addSql = "select distinct address,room_num from (select distinct address as address,room_num from test_examroom_info a,test_examcourse_info b,"
						+ "test_examuser_course c,entity_student_info d where c.room_id = a.id and "
						+ "b.id = c.course_id  and b.EXAMSEQUENCE_ID = '"
						+ rs.getString("examsequence_id")
						+ "' and b.id = '"
						+ rs.getString("course_id")
						+ "' and c.user_id=d.id and d.site_id='"
						+ siteId
						+ "')";

				rs1 = db.executeQuery(addSql);
				if (db.countselect(addSql) > 0) {
					EntityLog.setDebug("getExamStatByCourse@addSql+1: "
							+ addSql);
					numStr = "";
				} else {
					EntityLog.setDebug("getExamStatByCourse@addSql+0: "
							+ addSql);
					item.add(addressStr);
					item.add(numStr);
					list.add(item);
				}
				while (rs1 != null && rs1.next()) {
					if (num > 0) {
						item = new ArrayList();
						item.add(rs.getString("site_name"));
						item.add(rs.getString("course_name"));
						item.add(rs.getString("examsequence_name"));
						item.add(rs.getString("course_type"));
						item.add(s_day);
						item.add(s_time + "~" + e_time);
					}*/
					item.add(rs.getString("address"));
					item.add(rs.getString("room_num"));
					list.add(item);
					//num++;
				//}
				//db.close(rs1);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return list;
	}

	public List getExamStat2(String batch_id, String site_id)
			throws PlatformException {
		dbpool db = new dbpool();
		String condition = "";
		if (site_id != null && !site_id.equals(""))
			condition = " and c.site_id = '" + site_id + "'";
		String sql = "select site_name,course_name,num,major_name,edutype_name,grade_name,major_id,edutype_id,"
				+ "grade_id,site_id,examsequence_id from(select c.name as site_name,a.course_name,a.num,b.examsequence_id,c.id as site_id,d.name as major_name,d.id as major_id,e.name as edutype_name, e.id as edutype_id,f.name as grade_name,f.id as grade_id from (select a.name as course_name,a.id as course_id,c.site_id,count(b.id) as num,c.major_id,c.grade_id,c.edutype_id from test_examcourse_info a,test_examuser_course b,entity_student_info c where a.test_batch_id='"
				+ batch_id
				+ "' "
				+ condition
				+ "  and a.id=b.course_id and b.user_id=c.id "
				+ "group by a.name,a.id,c.site_id,c.grade_id,c.edutype_id,c.major_id) a,test_examcourse_info b,entity_site_info c,entity_major_info d,entity_edu_type e,entity_grade_info f where a.course_id=b.id and a.site_id=c.id and d.id = a.major_id and e.id=a.edutype_id and a.grade_id = f.id) order by site_id,examsequence_id,edutype_id,grade_id,major_id";
		EntityLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			rs = db.executeQuery(sql);
			int count = 0;
			while (rs != null && rs.next()) {
				List item = new ArrayList();
				item.add(rs.getString("course_name"));
				item.add(rs.getString("site_name"));
				count += rs.getInt("num");
				item.add(rs.getString("num"));
				item.add(rs.getString("major_name"));
				item.add(rs.getString("edutype_name"));
				item.add(rs.getString("grade_name"));
				list.add(item);
			}
			List item = new ArrayList();
			item.add("�ϼƣ�");
			item.add(Integer.toString(count));
			list.add(item);
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public List getBasicSequences(Page page, List searchproperty,
			List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLBASICSEQUENCE
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		List list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleBasicSequence item = new OracleBasicSequence();
				item.setId(rs.getString("id"));
				item.setTitle(rs.getString("title"));
				item.setNote(rs.getString("note"));
				list.add(item);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}
	
	public List getBasicSequences(Page page, List searchproperty,
			List orderproperty,String batch_id) {
		dbpool db = new dbpool();
		String sql = 
			"select id,title,note from ( select t2.id as id, t2.title as title,t2.note as note " +
			"  from test_examsequence_info t, test_basicsequence_info t2 " + 
			" where t2.id = t.basicsequence_id " + 
			"   and t.testbatch_id = '"+batch_id+"') order by  to_number(id)";

		//String sql = SQLBASICSEQUENCE
			//	+ Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		List list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleBasicSequence item = new OracleBasicSequence();
				item.setId(rs.getString("id"));
				item.setTitle(rs.getString("title"));
				item.setNote(rs.getString("note"));
				list.add(item);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}
	
	
	public List getBasicSequences2(Page page, List searchproperty,
			List orderproperty,String batch_id) {
		dbpool db = new dbpool();
		String sql = 
			"select id,title,note from ( select t2.id as id, t2.title as title,t2.note as note " +
			"  from expendtest_examsequence_info t, test_basicsequence_info t2 " + 
			" where t2.id = t.basicsequence_id " + 
			"   and t.testbatch_id = '"+batch_id+"') order by  to_number(id)";

		//String sql = SQLBASICSEQUENCE
			//	+ Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		List list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleBasicSequence item = new OracleBasicSequence();
				item.setId(rs.getString("id"));
				item.setTitle(rs.getString("title"));
				item.setNote(rs.getString("note"));
				list.add(item);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}
	public List getBasicSequencesNoTime(Page page, List searchproperty,
			List orderproperty) {
		dbpool db = new dbpool();
		String sql = "select id,title,note from test_basicsequence_info where id in(select id from test_basicsequence_info minus " +
			"select basicsequence_id from test_examsequence_info )"
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		List list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleBasicSequence item = new OracleBasicSequence();
				item.setId(rs.getString("id"));
				item.setTitle(rs.getString("title"));
				item.setNote(rs.getString("note"));
				list.add(item);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	
	public List getBasicSequencesNoTime(Page page, List searchproperty,
			List orderproperty,String batchId) {
		dbpool db = new dbpool();
		String sql = "select id,title,note from test_basicsequence_info where id in(select id from test_basicsequence_info minus " +
			"select t.basicsequence_id from test_examsequence_info t where t.testbatch_id='"+batchId+"')"
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		List list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleBasicSequence item = new OracleBasicSequence();
				item.setId(rs.getString("id"));
				item.setTitle(rs.getString("title"));
				item.setNote(rs.getString("note"));
				list.add(item);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}
	
	public int getBasicSequencesNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLBASICSEQUENCE
				+ Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getOtherFeeTypes(Page page, List searchproperty,
			List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLOTHERFEETYPE
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		List list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleOtherFeeType item = new OracleOtherFeeType();
				item.setId(rs.getString("id"));
				item.setName(rs.getString("name"));
				list.add(item);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getOtherFeeTypesNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLOTHERFEETYPE
				+ Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getExamStat3(String batch_id, String site_id, String course_id,
			String sequence_id) throws PlatformException {
		dbpool db = new dbpool();
		String condition = "";
		if (batch_id != null && !batch_id.equals(""))
			condition += " and test_batch_id = '" + batch_id + "'";
		if (site_id != null && !site_id.equals(""))
			condition += " and site_id = '" + site_id + "'";
		if (course_id != null && !course_id.equals(""))
			condition += " and a.course_id = '" + course_id + "'";
		if (sequence_id != null && !sequence_id.equals(""))
			condition += " and examsequence_id = '" + sequence_id + "'";
		String sql = "select num,course_id,course_name,site_id,site_name,grade_id,grade_name,major_id,major_name,edutype_id,edutype_name,s_date,e_date,course_type from(select d.num,d.course_id,d.course_name,d.site_id,e.name as site_name,d.grade_id,f.name as grade_name,d.major_id,g.name as major_name,d.edutype_id,h.name as edutype_name,to_char(i.s_date,'yyyy-mm-dd hh24:mi:ss') as s_date,to_char(i.e_date,'yyyy-mm-dd hh24:mi:ss') as e_date,i.course_type from (select count(a.id) as num,a.course_id,c.name as course_name,b.site_id,b.grade_id,b.major_id,b.edutype_id from test_examuser_course a,entity_student_info b,test_examcourse_info c where a.user_id=b.id and a.course_id=c.id "
				+ condition
				+ " group by a.course_id,c.name,b.site_id,b.grade_id,b.major_id,b.edutype_id) d,entity_site_info e,entity_grade_info f,entity_major_info g,entity_edu_type h,test_examcourse_info i where d.site_id=e.id and d.grade_id=f.id and d.major_id=g.id and d.edutype_id=h.id and d.course_id=i.id) order by course_id,site_id,grade_id,major_id,edutype_id";
		EntityLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			rs = db.executeQuery(sql);
			int count = 0;
			while (rs != null && rs.next()) {
				List item = new ArrayList();
				item.add(rs.getString("course_name"));
				item.add(rs.getString("site_name"));
				item.add(rs.getString("major_name"));
				item.add(rs.getString("edutype_name"));
				item.add(rs.getString("grade_name"));
				count += rs.getInt("num");
				item.add(rs.getString("num"));
				item.add(rs.getString("s_date") + "~" + rs.getString("e_date"));
				item.add(rs.getString("course_type"));
				item.add(rs.getString("course_id"));
				item.add(rs.getString("site_id"));
				item.add(rs.getString("major_id"));
				item.add(rs.getString("edutype_id"));
				item.add(rs.getString("grade_id"));
				list.add(item);
			}
			List item = new ArrayList();
			item.add("�ϼƣ�");
			item.add(Integer.toString(count));
			list.add(item);
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public List getExamRooms(String course_id, String user_id)
			throws PlatformException {
		dbpool db = new dbpool();		
        String sql0="select  distinct teci.name as course_name,teri.room_no as room_no,teri.address as address,teri.name as num," +
		"teuc.desk_no as desk_no,tesi.startdate as startdate,tesi.enddate as enddate,tesi.title as title,tesi.note as note" + 
		" from entity_student_info esi,test_user_info tui,test_examroom_info teri," + 
		"test_examcourse_info teci,test_examsequence_info tesi,test_examuser_course  teuc,test_exambatch_info tebi" + 
		" where esi.id = '"+user_id+"'  and teci.open_course_id = '"+course_id+"' and tui.login_id = esi.reg_no  and teri.course_id = teci.id" + 
		" and teuc.user_id = tui.id   and teuc.course_id = teci.id   and teci.basicsequence_id = tesi.basicsequence_id and teri.batch_id = tebi.id" + 
		" and teci.test_batch_id = tesi.testbatch_id  and teci.test_batch_id = tebi.id  and tebi.status='1'";
/*		String sql = "select course_name,course_type,exam_type,s_date,e_date,room_no,address,desk_no,title from (select b.name as course_name,b.course_type,b.exam_type,to_char(s_date,'yyyy-mm-dd hh24:mi:ss') as s_date,to_char(e_date,'yyyy-mm-dd hh24:mi:ss') as e_date,nvl(c.room_no,'') as room_no,nvl(c.address,'') as address,a.desk_no,d.title from test_examuser_course a,test_examcourse_info b,test_examroom_info c,test_examsequence_info d where a.user_id='"
				+ user_id
				+ "' and a.course_id=b.id and b.course_id='"
				+ course_id
				+ "' and a.room_id=c.id(+) and b.examsequence_id = d.id )";
*/				
		EntityLog.setDebug(sql0);
		MyResultSet rs = null;
		List list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			rs = db.executeQuery(sql0);
			while (rs != null && rs.next()) {
				List item = new ArrayList();
				item.add(rs.getString("course_name"));
				item.add("");//rs.getString("course_type")
				item.add("");//(rs.getString("exam_type").equals("0") ? "����" : "����"
				item.add(rs.getString("startdate") + "~" + rs.getString("enddate"));
				item.add(rs.getString("room_no"));
				item.add(rs.getString("address"));
				item.add(rs.getString("desk_no"));
				item.add(rs.getString("title"));
				list.add(item);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

}
