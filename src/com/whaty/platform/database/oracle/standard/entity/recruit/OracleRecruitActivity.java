package com.whaty.platform.database.oracle.standard.entity.recruit;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.sms.OracleSmsManagerPriv;
import com.whaty.platform.entity.recruit.RecruitActivity;
import com.whaty.platform.entity.recruit.RecruitTestCourse;
import com.whaty.platform.sms.SmsFactory;
import com.whaty.platform.sms.SmsManage;
import com.whaty.platform.sms.SmsSendThread;
import com.whaty.platform.sms.basic.SmsSystemPoint;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleRecruitActivity implements RecruitActivity {

	public int addMajorToSort(List majorList) throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int addRecruitTestStudents(List studentList,
			RecruitTestCourse testCourse) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		int i = 0;
		List sql_group = new ArrayList();
		try {
			for (int t = 0; t < studentList.size(); t++) {
				OracleRecruitStudent recruitStudent = (OracleRecruitStudent) studentList
						.get(t);
				sql = "insert into recruit_test_desk (id,recruitstudent_id,testcourse_id) values(to_char(s_recruit_test_desk_id.nextval),'"
						+ recruitStudent.getId()
						+ "','"
						+ testCourse.getId()
						+ "')";
				sql_group.add(sql);
				UserAddLog.setDebug("OracleRecruitActive.addRecruitTestStudents(List studentList,RecruitTestCourse testCourse) SQL=" + sql + " DATE=" + new Date());
			}
			i = db.executeUpdateBatch(sql_group);
		} catch (Exception e) {
			
		}
		return i;
	}

	public int deleteRecruitTestStudents(List studentList)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		String con = "";
		int i = 0;
		try {
			for (int t = 0; t < studentList.size(); t++) {
				OracleRecruitStudentScore recruitStudentScore = (OracleRecruitStudentScore) studentList
						.get(t);
				con = con + "'" + recruitStudentScore.getId() + "',";
			}
			sql = "delete from recruit_test_desk where id in('" + con + "')";
			i = db.executeUpdate(sql);
			UserDeleteLog.setDebug("OracleRecruitActive.deleteRecruitTestStudents(List studentList) SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		} catch (Exception e) {
			
		}
		return i;
	}

	public void allotStudents(String batch_id, String site_id, String num)
			throws PlatformException {
		dbpool db = new dbpool();
		int examroom_no = 0;
		String room_no = "";
		int seat_no = 1;
		String sort_id = "";
		String sql_sort = "";
		String sql_student = "";
		String sql_insroom = "";
		String sql_insstu = "";
		int sequence = 1;

		MyResultSet rs = null;
		MyResultSet rs_sort = null;
		MyResultSet rs_student = null;
		MyResultSet rs_year = null;
		MyResultSet rs_edutype = null;

		String sql_delroom = "delete recruit_test_room where testsite_id='"
				+ site_id + "' and batch_id='" + batch_id + "'";
		db.executeUpdate(sql_delroom);
		UserDeleteLog.setDebug("OracleRecruitActive.allotStudent(String batch_id,String site_id,String num) SQL=" + sql_delroom + " DATE=" + new Date());

		String sql_uproom = "update recruit_test_desk set testroom_id='' where recruitstudent_id "
				+ "in (select id from recruit_student_info where site_id='"
				+ site_id + "' and batch_id='" + batch_id + "')";
		db.executeUpdate(sql_uproom);
		UserUpdateLog.setDebug("OracleRecruitActive.allotStudent(String batch_id,String site_id,String num) SQL=" + sql_uproom + " DATE=" + new Date());

		sql_sort = "select distinct m.sort_id as sort_id from recruit_student_info s,recruit_majorsort_relation m,recruit_sort_info r where s.edutype_id=r.edutype_id and r.id=m.sort_id and  s.major_id=m.major_id and s.site_id='"
				+ site_id
				+ "' and s.batch_id='"
				+ batch_id
				+ "' and s.status='1' and s.considertype_status<>'2'";
		EntityLog.setDebug("OracleRecruitActivity@Method:allotStudents()/sql_sort="+sql_sort);
		rs_sort = db.executeQuery(sql_sort);

		try {
			while (rs_sort != null && rs_sort.next()) {

				sort_id = rs_sort.getString("sort_id");

				sql_student = "select distinct s.id as student_id,s.major_id,s.edutype_id from recruit_student_info s,recruit_majorsort_relation m,"
						+ "recruit_sortcourse_relation rsr,recruit_sort_info r where s.edutype_id=r.edutype_id and r.id=m.sort_id  and s.major_id=m.major_id and s.site_id='"
						+ site_id
						+ "' and s.batch_id='"
						+ batch_id
						+ "' and m.sort_id='"
						+ sort_id
						+ "' and s.considertype_status<>'2' and s.status='1' and rsr.sort_id=m.sort_id  "
						+ "and rsr.course_id in (select course_id from recruit_test_course where testsequence_id='"
						+ batch_id + "') order by s.major_id,s.edutype_id";

				rs_student = db.executeQuery(sql_student);
				EntityLog.setDebug("sql_student=" + sql_student);
				// System.out.println(sql_student);
				/*
				 * ȡIDʼ
				 */
				String testroom_id = "";
				String sql_next_id = "select s_recruit_test_room_id.nextval as id from dual";
				rs = db.executeQuery(sql_next_id);
				if (rs != null && rs.next())
					testroom_id = rs.getString("id");
				db.close(rs);
				// ȡID

				if (db.countselect(sql_student) != 0) {
					examroom_no++;
					if (examroom_no < 10) {
						room_no = "0" + examroom_no;
					} else {
						room_no = "" + examroom_no;
					}
					sql_insroom = "insert into recruit_test_room (id,testsite_id,num,sort_id,batch_id,room_no) values ('"
							+ testroom_id
							+ "','"
							+ site_id
							+ "','"
							+ num
							+ "','"
							+ sort_id
							+ "','"
							+ batch_id
							+ "','"
							+ room_no + "')";
					db.executeUpdate(sql_insroom);
					UserAddLog.setDebug("OracleRecruitActive.allotStudent(String batch_id,String site_id,String num) SQL=" + sql_insroom + " DATE=" + new Date());
				}
				while (rs_student != null && rs_student.next()) {
					if (seat_no > Integer.parseInt(num)) {
						examroom_no++;
						if (examroom_no < 10) {
							room_no = "0" + examroom_no;
						} else {
							room_no = "" + examroom_no;
						}
						/*
						 * ȡIDʼ
						 */
						rs = db.executeQuery(sql_next_id);
						if (rs != null && rs.next())
							testroom_id = rs.getString("id");
						db.close(rs);
						// ȡID
						sql_insroom = "insert into recruit_test_room (id,testsite_id,num,sort_id,batch_id,room_no) values ('"
								+ testroom_id
								+ "','"
								+ site_id
								+ "','"
								+ num
								+ "','"
								+ sort_id
								+ "','"
								+ batch_id
								+ "','"
								+ room_no + "')";						
						db.executeUpdate(sql_insroom);
						UserAddLog.setDebug("OracleRecruitActive.allotStudent(String batch_id,String site_id,String num) SQL=" + sql_insroom + " DATE=" + new Date());
						seat_no = 1;
					}

					String seat_no_str;
					if (seat_no < 10) {
						seat_no_str = "0" + seat_no;
					} else {
						seat_no_str = "" + seat_no;
					}

					sql_insstu = "update  recruit_test_desk set numbyroom='"
							+ seat_no_str + "',  testroom_id='" + testroom_id
							+ "' where recruitstudent_id='"
							+ rs_student.getString("student_id") + "'";					
					db.executeUpdate(sql_insstu);
					UserUpdateLog.setDebug("OracleRecruitActive.allotStudent(String batch_id,String site_id,String num) SQL=" + sql_insstu + " DATE=" + new Date());
					// System.out.println(sql_insstu);
					String sql_year = "select sysdate  as year from dual";
					String year = "";
					rs_year = db.executeQuery(sql_year);
					if (rs_year != null && rs_year.next()) {
						year = rs_year.getString("year");
					}
					db.close(rs_year);
					year = year.substring(2, 4);

					String sql_edutype_id = "select edutype_id,major_id from recruit_student_info where id='"
							+ rs_student.getString("student_id") + "'";
					String edutype_id = "";
					String major_id = "";
					rs_edutype = db.executeQuery(sql_edutype_id);
					if (rs_edutype != null && rs_edutype.next()) {
						edutype_id = rs_edutype.getString("edutype_id");
						major_id = rs_edutype.getString("major_id");
					}
					db.close(rs_edutype);

					String testcard_id = "";

					String str_sequence = "";

					if (sequence < 10)
						str_sequence = "00" + sequence;
					else if (sequence < 100)
						str_sequence = "0" + sequence;
					else
						str_sequence = sequence + "";

					// testcard_id = year + batch_id + edutype_id + site_id+
					// sort_id + str_sequence;
					String str_siteId = "";
					String str_majorId = "";
					if (site_id.length() > 2) {
						str_siteId = site_id.substring(site_id.length() - 2);
					} else {

						str_siteId = site_id;

					}
					if (major_id.length() > 2) {
						str_majorId = major_id.substring(major_id.length() - 2);
					} else {

						str_majorId = major_id;

					}

					testcard_id = batch_id + str_siteId + edutype_id
							+ str_majorId + str_sequence;

					sql_insstu = "update  RECRUIT_STUDENT_INFO set testcard_id='"
							+ testcard_id
							+ "' where id='"
							+ rs_student.getString("student_id") + "'";

					// db.executeUpdate(sql_insstu);

					sequence++;
					seat_no++;
				}

				db.close(rs_student);
				seat_no = 1;
			}

			db.close(rs_sort);
		} catch (Exception e) {
			
		}
	}

	public void recruitStudents(List studentList) throws PlatformException {

	}

	public void unRecruitStudents(List studentList) throws PlatformException {

	}

	public int releaseScore(List searchProperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_student_info set score_status='1' "
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitActive.releaseScore(List searchProperty) SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int unreleaseScore(List searchProperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_student_info set score_status='0' "
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitActive.unreleaseScore(List searchProperty) SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}


	public int unreleaseMatriculate(List searchProperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_student_info set pass_status='0' "
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitActive.unreleaseMatriculate(List searchProperty) SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public void updateRecruitStudentScoreBatch(List courseList, List scoreList)
			throws NoRightException, PlatformException {
		dbpool db = new dbpool();
		int count = 0;
		String sql = "";
		String siteId = "";
		String majorId = "";
		String eduTypeId = "";
		String testCardId = "";
		String name = "";
		String appendScore = "";
		float totalScore = 0f;
		List exceptionList = new ArrayList();
		String except = "";
		for (int i = 0; i < scoreList.size(); i++) {
			totalScore = 0f;
			HashMap score = (HashMap) scoreList.get(i);
			siteId = (String) score.get("siteId");
			majorId = (String) score.get("majorId");
			eduTypeId = (String) score.get("eduTypeId");
			testCardId = (String) score.get("testCardId");
			name = (String) score.get("name");
			appendScore = (String) score.get("appendScore");
			totalScore = totalScore + Float.parseFloat(appendScore);
			String field;
			for (int j = 0; j < courseList.size(); j++) {
				field = (String) courseList.get(j);
				totalScore = totalScore
						+ Float
								.parseFloat((String) score
										.get("course" + field));
				sql = "update recruit_test_desk set score='"
						+ score.get("course" + field)
						+ "' where testcourse_id='"
						+ field
						+ "' and "
						+ "recruitstudent_id =(select id from recruit_student_info "
						+ "where site_id='" + siteId + "' and major_id='"
						+ majorId + "' and edutype_id='" + eduTypeId
						+ "' and name='" + name + "' and testcard_id='"
						+ testCardId + "')";
				int suc = db.executeUpdate(sql);
				UserUpdateLog.setDebug("OracleRecruitActive.updateRecruitStudentScoreBatch(List courseList, List scoreList) SQL=" + sql + "COUNT=" + suc + " DATE=" + new Date());
				if (suc < 1) {
					exceptionList.add("ѧ " + name + " Կγ̱" + field
							+ "Ŀγ¼ɼ");
				}
			}
			sql = "update recruit_student_info set append_score='"
					+ appendScore + "',score='" + totalScore
					+ "' where site_id='" + siteId + "' and major_id='"
					+ majorId + "' and edutype_id='" + eduTypeId
					+ "' and name='" + name + "' and testcard_id='"
					+ testCardId + "'";
			int suc = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleRecruitActive.updateRecruitStudentScoreBatch(List courseList, List scoreList) SQL=" + sql + "COUNT=" + suc + " DATE=" + new Date());
			if (suc < 1) {
				exceptionList.add("ѧ " + name + "  Ըӷּܷ¼");
			} else
				count++;
		}
		if (exceptionList.size() > 0) {
			exceptionList.add("֤¼");
			for (int i = 0; i < exceptionList.size(); i++) {
				except += (String) exceptionList.get(i) + "<br>";
			}
			if (count > 0)
				except += "ɹ¼" + count + "ѧɼ";
			throw new PlatformException(except);
		} else {
			except += "ɹ¼" + count + "ѧɼ";
			throw new PlatformException(except);
		}
	}

	public List getExamroomDisply(Page page, String siteId, String batchId,
			String sortId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List roomList = new ArrayList();
		StringBuffer str = new StringBuffer();
		if (siteId != null && !siteId.equals("") && !siteId.equals("null")) {
			str.append(" and rtr.testsite_id='").append(siteId).append("'");

		}
		if (batchId != null && !batchId.equals("") && !batchId.equals("null")) {
			str.append(" and rtr.batch_id='").append(batchId).append("'");

		}
		if (sortId != null && !sortId.equals("") && !sortId.equals("null")) {
			str.append(" and rtr.sort_id='").append(sortId).append("'");

		}
		String st = str.toString();
		String sql = "";
		sql = "select c.room_no as room_no,c.testroom_id as testroom_id, a.name as sort_name,b.title as title,c.num as num,d.name as edutype_name ,c.startdate as startdate from  recruit_sort_info a,recruit_test_room b, (select rtd.testroom_id,rtr.room_no,count(distinct rtd.recruitstudent_id) as num , to_char(e.startdate,'yyyy-mm-dd hh24:mi:ss') as startdate from recruit_test_course e ,recruit_test_desk rtd,recruit_test_room rtr,recruit_student_info rsi  where  rtd.testroom_id=rtr.id  and e.id = rtd.testcourse_id "
				+ st
				+ "  group by (rtd.testroom_id,rtr.room_no,e.startdate) ) c,entity_edu_type d  where a.edutype_id=d.id and  a.id=b.sort_id and b.id=c.testroom_id  order by c.room_no asc";
		/*
		 * sql = "select c.room_no as room_no,c.testroom_id as testroom_id,
		 * a.name as sort_name,b.title as title,c.num as num from
		 * recruit_sort_info a,recruit_test_room b, (select
		 * rtd.testroom_id,rtr.room_no,count(distinct rtd.recruitstudent_id) as
		 * num from recruit_test_desk rtd,recruit_test_room
		 * rtr,recruit_student_info rsi where rtd.testroom_id=rtr.id and
		 * rtr.testsite_id='" + siteId + "' and rtr.batch_id='" + batchId + "'
		 * and rtr.sort_id='" + sortId + "' group by
		 * (rtd.testroom_id,rtr.room_no) ) c where a.id=b.sort_id and
		 * b.id=c.testroom_id order by c.room_no asc"; ======= if
		 * (sortId.equals("")) sql = "select c.room_no as room_no,c.testroom_id
		 * as testroom_id, a.name as sort_name,b.title as title,c.num as num
		 * from recruit_sort_info a,recruit_test_room b, (select
		 * rtd.testroom_id,rtr.room_no,count(distinct rtd.recruitstudent_id) as
		 * num from recruit_test_desk rtd,recruit_test_room
		 * rtr,recruit_student_info rsi where rtd.testroom_id=rtr.id and
		 * rtr.testsite_id='" + siteId + "' and rtr.batch_id='" + batchId + "'
		 * group by (rtd.testroom_id,rtr.room_no) ) c where a.id=b.sort_id and
		 * b.id=c.testroom_id order by c.room_no asc"; else sql = "select
		 * c.room_no as room_no,c.testroom_id as testroom_id, a.name as
		 * sort_name,b.title as title,c.num as num from recruit_sort_info
		 * a,recruit_test_room b, (select
		 * rtd.testroom_id,rtr.room_no,count(distinct rtd.recruitstudent_id) as
		 * num from recruit_test_desk rtd,recruit_test_room
		 * rtr,recruit_student_info rsi where rtd.testroom_id=rtr.id and
		 * rtr.testsite_id='" + siteId + "' and rtr.batch_id='" + batchId + "'
		 * and rtr.sort_id='" + sortId + "' group by
		 * (rtd.testroom_id,rtr.room_no) ) c where a.id=b.sort_id and
		 * b.id=c.testroom_id order by c.room_no asc"; >>>>>>> .r5805
		 */
		EntityLog.setDebug(sql);
		if (page != null) {
			int pageInt = page.getPageInt();
			int pageSize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
		} else {
			rs = db.executeQuery(sql);
		}
		try {
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("room_no", rs.getString("room_no"));
				hash.put("testroom_id", rs.getString("testroom_id"));
				hash.put("sort_name", rs.getString("sort_name"));
				hash.put("title", rs.getString("title"));
				hash.put("num", rs.getString("num"));
				hash.put("edutype_name", rs.getString("edutype_name"));
				hash.put("startdate", rs.getString("startdate"));
				roomList.add(hash);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return roomList;

	}

	public int getExamroomDisplyNum(String siteId, String batchId, String sortId) {
		dbpool db = new dbpool();
		int count = 0;
		String sql = "";

		/*
		 * sql = "select c.room_no as room_no,c.testroom_id as testroom_id,
		 * a.name as sort_name,b.title as title,c.num from recruit_sort_info
		 * a,recruit_test_room b, (select
		 * rtd.testroom_id,rtr.room_no,count(distinct rtd.recruitstudent_id) as
		 * num from recruit_test_desk rtd,recruit_test_room
		 * rtr,recruit_student_info rsi where rtd.testroom_id=rtr.id and
		 * rtr.testsite_id='" + siteId + "' and rtr.batch_id='" + batchId + "'
		 * and rtr.sort_id='" + sortId + "' group by
		 * (rtd.testroom_id,rtr.room_no) ) c where a.id=b.sort_id and
		 * b.id=c.testroom_id";
		 * 
		 * if (sortId.equals("")) sql = "select c.room_no as
		 * room_no,c.testroom_id as testroom_id, a.name as sort_name,b.title as
		 * title,c.num from recruit_sort_info a,recruit_test_room b, (select
		 * rtd.testroom_id,rtr.room_no,count(distinct rtd.recruitstudent_id) as
		 * num from recruit_test_desk rtd,recruit_test_room
		 * rtr,recruit_student_info rsi where rtd.testroom_id=rtr.id and
		 * rtr.testsite_id='" + siteId + "' and rtr.batch_id='" + batchId + "'
		 * group by (rtd.testroom_id,rtr.room_no) ) c where a.id=b.sort_id and
		 * b.id=c.testroom_id"; else sql = "select c.room_no as
		 * room_no,c.testroom_id as testroom_id, a.name as sort_name,b.title as
		 * title,c.num from recruit_sort_info a,recruit_test_room b, (select
		 * rtd.testroom_id,rtr.room_no,count(distinct rtd.recruitstudent_id) as
		 * num from recruit_test_desk rtd,recruit_test_room
		 * rtr,recruit_student_info rsi where rtd.testroom_id=rtr.id and
		 * rtr.testsite_id='" + siteId + "' and rtr.batch_id='" + batchId + "'
		 * and rtr.sort_id='" + sortId + "' group by
		 * (rtd.testroom_id,rtr.room_no) ) c where a.id=b.sort_id and
		 * b.id=c.testroom_id";
		 * 
		 */
		StringBuffer str = new StringBuffer();
		if (siteId != null && !siteId.equals("") && !siteId.equals("null")) {
			str.append(" and rtr.testsite_id='").append(siteId).append("'");

		}
		if (batchId != null && !batchId.equals("") && !batchId.equals("null")) {
			str.append(" and rtr.batch_id='").append(batchId).append("'");

		}
		if (sortId != null && !sortId.equals("") && !sortId.equals("null")) {
			str.append(" and rtr.sort_id='").append(sortId).append("'");

		}
		String st = str.toString();
		sql = "select c.room_no as room_no,c.testroom_id as testroom_id, a.name as sort_name,b.title as title,c.num as num from  recruit_sort_info a,recruit_test_room b, (select rtd.testroom_id,rtr.room_no,count(distinct rtd.recruitstudent_id) as num from recruit_test_desk rtd,recruit_test_room rtr,recruit_student_info rsi  where rtd.testroom_id=rtr.id "
				+ st
				+ "  group by (rtd.testroom_id,rtr.room_no) ) c  where a.id=b.sort_id and b.id=c.testroom_id order by c.room_no asc";
		count = db.countselect(sql);
		return count;

	}

	public List getExamroomDisply(Page page, String siteId, String batchId,
			String sortId,String edutype_id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List roomList = new ArrayList();
		StringBuffer str = new StringBuffer();
		if (siteId != null && !siteId.equals("") && !siteId.equals("null")) {
			str.append(" and rtr.testsite_id='").append(siteId).append("'");

		}
		if (batchId != null && !batchId.equals("") && !batchId.equals("null")) {
			str.append(" and rtr.batch_id='").append(batchId).append("'");

		}
		if (sortId != null && !sortId.equals("") && !sortId.equals("null")) {
			str.append(" and rtr.sort_id='").append(sortId).append("'");

		}
		if (edutype_id != null && !edutype_id.equals("") && !edutype_id.equals("null")) {
			str.append(" and rsi.edutype_id='").append(edutype_id).append("'");
		}
		String st = str.toString();
		String sql = "";
		sql = "select c.room_no as room_no,c.testroom_id as testroom_id, a.name as sort_name,b.title as title,c.num as num,d.name as edutype_name ,c.startdate as startdate from  recruit_sort_info a,recruit_test_room b, (select rtd.testroom_id,rtr.room_no,count(distinct rtd.recruitstudent_id) as num , to_char(e.startdate,'yyyy-mm-dd hh24:mi:ss') as startdate from recruit_test_course e ,recruit_test_desk rtd,recruit_test_room rtr,recruit_student_info rsi  where rsi.id = rtd.recruitstudent_id and rtd.testroom_id=rtr.id  and e.id = rtd.testcourse_id "
				+ st
				+ "  group by (rtd.testroom_id,rtr.room_no,e.startdate) ) c,entity_edu_type d  where a.edutype_id=d.id and  a.id=b.sort_id and b.id=c.testroom_id  order by c.room_no asc";

		EntityLog.setDebug(sql);
		if (page != null) {
			int pageInt = page.getPageInt();
			int pageSize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
		} else {
			rs = db.executeQuery(sql);
		}
		try {
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("room_no", rs.getString("room_no"));
				hash.put("testroom_id", rs.getString("testroom_id"));
				hash.put("sort_name", rs.getString("sort_name"));
				hash.put("title", rs.getString("title"));
				hash.put("num", rs.getString("num"));
				hash.put("edutype_name", rs.getString("edutype_name"));
				hash.put("startdate", rs.getString("startdate"));
				roomList.add(hash);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return roomList;

	}

	public int getExamroomDisplyNum2(String siteId, String batchId, String sortId,String edutype_id) {
		dbpool db = new dbpool();
		int count = 0;
		String sql = "";


		StringBuffer str = new StringBuffer();
		if (siteId != null && !siteId.equals("") && !siteId.equals("null")) {
			str.append(" and rtr.testsite_id='").append(siteId).append("'");

		}
		if (batchId != null && !batchId.equals("") && !batchId.equals("null")) {
			str.append(" and rtr.batch_id='").append(batchId).append("'");

		}
		if (sortId != null && !sortId.equals("") && !sortId.equals("null")) {
			str.append(" and rtr.sort_id='").append(sortId).append("'");

		}
		if (edutype_id != null && !edutype_id.equals("") && !edutype_id.equals("null")) {
			str.append(" and rsi.edutype_id='").append(edutype_id).append("'");
		}
		String st = str.toString();
		sql = "select c.room_no as room_no,c.testroom_id as testroom_id, a.name as sort_name,b.title as title,c.num as num from  recruit_sort_info a,recruit_test_room b, (select rtd.testroom_id,rtr.room_no,count(distinct rtd.recruitstudent_id) as num from recruit_test_desk rtd,recruit_test_room rtr,recruit_student_info rsi  where rsi.id = rtd.recruitstudent_id and rtd.testroom_id=rtr.id "
				+ st
				+ "  group by (rtd.testroom_id,rtr.room_no) ) c  where a.id=b.sort_id and b.id=c.testroom_id order by c.room_no asc";
		count = db.countselect(sql);
		return count;

	}
	public List getExamroomDisply2(Page page, String siteId, String batchId,
			String sortId,String edutype_id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List roomList = new ArrayList();
		StringBuffer str = new StringBuffer();
		if (siteId != null && !siteId.equals("") && !siteId.equals("null")) {
			str.append(" and rtr.testsite_id='").append(siteId).append("'");

		}
		if (batchId != null && !batchId.equals("") && !batchId.equals("null")) {
			str.append(" and rtr.batch_id='").append(batchId).append("'");

		}
		if (sortId != null && !sortId.equals("") && !sortId.equals("null")) {
			str.append(" and rtr.sort_id='").append(sortId).append("'");

		}
		if (edutype_id != null && !edutype_id.equals("") && !edutype_id.equals("null")) {
			str.append(" and rsi.edutype_id='").append(edutype_id).append("'");
		}
		String st = str.toString();
		String sql = "";
		sql = "select c.room_no as room_no,c.testroom_id as testroom_id, a.name as sort_name,b.title as title,c.num as num,d.name as edutype_name  from  recruit_sort_info a,recruit_test_room b, (select rtd.testroom_id,rtr.room_no,count(distinct rtd.recruitstudent_id) as num  from recruit_test_course e ,recruit_test_desk rtd,recruit_test_room rtr,recruit_student_info rsi  where rsi.id = rtd.recruitstudent_id and rtd.testroom_id=rtr.id  and e.id = rtd.testcourse_id "
				+ st
				+ "  group by (rtd.testroom_id,rtr.room_no) ) c,entity_edu_type d  where a.edutype_id=d.id and  a.id=b.sort_id and b.id=c.testroom_id  order by c.room_no asc";

		EntityLog.setDebug(sql);
		if (page != null) {
			int pageInt = page.getPageInt();
			int pageSize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
		} else {
			rs = db.executeQuery(sql);
		}
		try {
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("room_no", rs.getString("room_no"));
				hash.put("testroom_id", rs.getString("testroom_id"));
				hash.put("sort_name", rs.getString("sort_name"));
				hash.put("title", rs.getString("title"));
				hash.put("num", rs.getString("num"));
				hash.put("edutype_name", rs.getString("edutype_name"));
				roomList.add(hash);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return roomList;

	}

	public int getExamroomDisplyNum(String siteId, String batchId, String sortId,String edutype_id) {
		dbpool db = new dbpool();
		int count = 0;
		String sql = "";


		StringBuffer str = new StringBuffer();
		if (siteId != null && !siteId.equals("") && !siteId.equals("null")) {
			str.append(" and rtr.testsite_id='").append(siteId).append("'");

		}
		if (batchId != null && !batchId.equals("") && !batchId.equals("null")) {
			str.append(" and rtr.batch_id='").append(batchId).append("'");

		}
		if (sortId != null && !sortId.equals("") && !sortId.equals("null")) {
			str.append(" and rtr.sort_id='").append(sortId).append("'");

		}
		if (edutype_id != null && !edutype_id.equals("") && !edutype_id.equals("null")) {
			str.append(" and rsi.edutype_id='").append(edutype_id).append("'");
		}
		String st = str.toString();
		sql = "select c.room_no as room_no,c.testroom_id as testroom_id, a.name as sort_name,b.title as title,c.num as num,d.name as edutype_name ,c.startdate as startdate from  recruit_sort_info a,recruit_test_room b, (select rtd.testroom_id,rtr.room_no,count(distinct rtd.recruitstudent_id) as num , to_char(e.startdate,'yyyy-mm-dd hh24:mi:ss') as startdate from recruit_test_course e ,recruit_test_desk rtd,recruit_test_room rtr,recruit_student_info rsi  where rsi.id = rtd.recruitstudent_id and rtd.testroom_id=rtr.id  and e.id = rtd.testcourse_id "
				+ st
				+ "  group by (rtd.testroom_id,rtr.room_no,e.startdate) ) c,entity_edu_type d  where a.edutype_id=d.id and  a.id=b.sort_id and b.id=c.testroom_id  order by c.room_no asc";
		count = db.countselect(sql);
		return count;

	}

	
	public List getExamroomDisplyInfo(String testroomId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List roomList = new ArrayList();
		String sql = "";

		/*
		 * sql = "select a.testcard_id as testcard_id, a.name as
		 * stu_name,b.numbyroom as numbyroom from recruit_student_info a,
		 * (select distinct recruitstudent_id,numbyroom from recruit_test_desk
		 * where testroom_id='" + testroomId + "') b where
		 * a.id=b.recruitstudent_id order by b.numbyroom";
		 */
		sql = "select distinct a.testcard_id as testcard_id, a.name as stu_name,b.numbyroom as numbyroom,to_char(c.startdate,'yyyy-mm-dd hh24:mi:ss') startdate,to_char(c.enddate,'yyyy-mm-dd hh24:mi:ss') enddate from  recruit_student_info a,"
				+ " (select distinct  recruitstudent_id,numbyroom,testcourse_id from recruit_test_desk where testroom_id='"
				+ testroomId
				+ "') b,"
				+ " recruit_test_course c where c.id=b.testcourse_id and a.id=b.recruitstudent_id  order by b.numbyroom";
		rs = db.executeQuery(sql);
		EntityLog.setDebug("getExamroomDisplyInfo=" + sql);
		try {
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("testcard_id", rs.getString("testcard_id"));
				hash.put("stu_name", rs.getString("stu_name"));
				hash.put("numbyroom", rs.getString("numbyroom"));
				hash.put("startdate", rs.getString("startdate"));
				hash.put("enddate", rs.getString("enddate"));
				roomList.add(hash);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return roomList;

	}

	public List getExamroomDisplyInfo1(String testroomId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List roomList = new ArrayList();
		String sql = "";

		sql = "select a.testcard_id as testcard_id, a.name as stu_name,b.numbyroom as numbyroom from  recruit_student_info a, (select distinct  recruitstudent_id,numbyroom from recruit_test_desk where testroom_id='"
				+ testroomId
				+ "') b where a.id=b.recruitstudent_id order by b.numbyroom";

		rs = db.executeQuery(sql);
		EntityLog.setDebug("getExamroomDisplyInfo1=" + sql);
		try {
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("testcard_id", rs.getString("testcard_id"));
				hash.put("stu_name", rs.getString("stu_name"));
				hash.put("numbyroom", rs.getString("numbyroom"));
				roomList.add(hash);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return roomList;

	}

	public List getExamroomDisplyInfo2(String testroomId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List roomList = new ArrayList();
		String sql = "";

		/*
		 * sql = "select a.testcard_id as testcard_id, a.name as
		 * stu_name,b.numbyroom as numbyroom from recruit_student_info a,
		 * (select distinct recruitstudent_id,numbyroom from recruit_test_desk
		 * where testroom_id='" + testroomId + "') b where
		 * a.id=b.recruitstudent_id order by b.numbyroom";
		 */
		sql = "select distinct  a.testroom_id as testroom_id,b.room_no as room_no,c.name as sort_name ,d.name as edutype_name "
				+ "from   recruit_test_desk a,recruit_test_room b,recruit_sort_info c,entity_edu_type d "
				+ "where a.testroom_id=b.id and b.sort_id=c.id and c.edutype_id=d.id and a.testroom_id='"
				+ testroomId + "'";
		rs = db.executeQuery(sql);
		EntityLog.setDebug("getExamroomDisplyInfo2=" + sql);
		try {
			if (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("room_no", rs.getString("room_no"));
				hash.put("sort_name", rs.getString("sort_name"));
				hash.put("edutype_name", rs.getString("edutype_name"));
				roomList.add(hash);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return roomList;

	}

}
