package com.whaty.platform.database.oracle.standard.test.reexam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleOpenCourse;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourse;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.database.oracle.standard.sso.OracleSsoUser;
import com.whaty.platform.database.oracle.standard.test.OracleTestUser;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.entity.user.StudentEduInfo;
import com.whaty.platform.sso.SsoUser;
//import com.whaty.platform.test.exam.ExamActivity;
//import com.whaty.platform.test.exam.ExamCourse;
//import com.whaty.platform.test.exam.ExamSequence;
//import com.whaty.platform.test.exam.ExamUser;

import com.whaty.platform.test.reexam.ReExamActivity;
import com.whaty.platform.test.reexam.ReExamCourse;
import com.whaty.platform.test.reexam.ReExamSequence;
import com.whaty.platform.test.reexam.ReExamUser;

import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleReExamActivity extends ReExamActivity {

	public OracleReExamActivity() {
	}

	/*
	 * public int IsExist() throws PlatformException { dbpool db = new dbpool();
	 * String sql = ""; sql = "select * from test_examuser_course where
	 * user_id='" + this.getUser_id() + "' and course_id='" +
	 * this.getCourse_id() + "'"; int i = db.countselect(sql); return i; }
	 * 
	 * public int add() throws PlatformException { dbpool db = new dbpool();
	 * String sql = ""; int i = 1; if (IsExist() <= 0) { sql = "insert into
	 * test_examuser_course (id,user_id,course_id,room_id,desk_no,score,status)
	 * values (to_char(S_TEST_EXAMUSER_COURSE.nextval),'" + this.getUser_id() +
	 * "','" + this.getCourse_id() + "','" + this.getRoom_id() + "','" +
	 * this.getDesk_no() + "','" + this.getScore() + "','" + this.getStatus() +
	 * "','" + this.getStatus() + "')"; i = db.executeUpdate(sql); } return i; }
	 */
	public void courseAddUser(List examCourseList, List examUserList)
			throws PlatformException {
		ReExamCourse examCourse = null;
		ReExamUser examUser = null;
		String sql = "";
		dbpool db = new dbpool();
		List exceptionList = new ArrayList();
		int ret = 0;
		int count = 0;

		for (int i = 0; i < examCourseList.size(); i++) {
			examCourse = (ReExamCourse) examCourseList.get(i);
			for (int j = 0; j < examUserList.size(); j++) {
				examUser = (ReExamUser) examUserList.get(j);
				sql = "select * from test_examuser_course where user_id='"
						+ examUser.getId() + "' and course_id='"
						+ examCourse.getId() + "'";
				if (db.countselect(sql) > 0) {
					exceptionList.add(examUser.getId() + ",���û��Ѿ��ӵ��γ�"
							+ examCourse.getId() + "��");
					continue;
				}
				sql = "insert into test_examuser_course(id,user_id,course_id,status) values(to_char(s_test_examuser_course.nextval),'"
						+ examUser.getId()
						+ "','"
						+ examCourse.getId()
						+ "','0')";
				ret = db.executeUpdate(sql);
				UserAddLog.setDebug("OracleExamActivity.courseAddUser(List examCourseList, List examUserList) SQL=" + sql + " COUNT" + ret + " DATE=" + new Date());
				if (ret < 1) {
					exceptionList.add("�û�" + examUser.getId() + "���뿼�Կγ�"
							+ examCourse.getId() + "����");
				} else {
					count++;
				}

			}
		}
		String except = "";
		if (exceptionList.size() > 0) {
			exceptionList.add("���֤���ٲ���");
			for (int k = 0; k < exceptionList.size(); k++) {
				except += (String) exceptionList.get(k) + "<br>";
			}
			if (count > 0)
				except += "�ɹ�����" + count + "�ſγ�";
			throw new PlatformException(except);
		} else {
			except += "�ɹ�����" + count + "�ſγ�";
			throw new PlatformException(except);
		}
	}

	public void courseRemoveUser(List examCourseList, List examUserList)
			throws PlatformException {
		ReExamCourse examCourse = null;
		ReExamUser examUser = null;
		String sql = "";
		dbpool db = new dbpool();
		List exceptionList = new ArrayList();
		int ret = 0;
		int count = 0;

		for (int i = 0; i < examCourseList.size(); i++) {
			examCourse = (ReExamCourse) examCourseList.get(i);
			for (int j = 0; j < examUserList.size(); j++) {
				examUser = (ReExamUser) examUserList.get(j);
				sql = "select * from test_examuser_course where user_id='"
						+ examUser.getId() + "' and course_id='"
						+ examCourse.getId() + "'";
				if (db.countselect(sql) < 1) {
					exceptionList.add(examUser.getId() + ",���û�֮ǰ��û�мӵ��γ�"
							+ examCourse.getId() + "�У�ɾ��ʧ��");
					continue;
				}
				sql = "delete from test_examuser_course where user_id='"
						+ examUser.getId() + "' and course_id='"
						+ examCourse.getId() + "'";
				ret = db.executeUpdate(sql);
				UserDeleteLog.setDebug("OracleExamActivity.courseRemoveUser(List examCourseList, List examUserList) SQL=" + sql + " COUNT" + ret + " DATE=" + new Date());
				if (ret < 1) {
					exceptionList.add("�û�" + examUser.getId() + "�ӿ��Կγ�"
							+ examCourse.getId() + "��ɾ��ʧ�ܣ�");
				} else {
					count++;
				}
			}
		}
		String except = "";
		if (exceptionList.size() > 0) {
			exceptionList.add("���֤���ٴӴ��Կγ���ȡ���û�");
			for (int k = 0; k < exceptionList.size(); k++) {
				except += (String) exceptionList.get(k) + "<br>";
			}
			if (count > 0)
				except += "�ɹ�����" + count + "�ſγ�";
			throw new PlatformException(except);
		} else {
			except += "�ɹ�ɾ��" + count + "����";
			throw new PlatformException(except);
		}
	}

	public int courseAddUser(String user_id, String course_id)
			throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		sql = "select id from test_examuser_course where user_id='" + user_id
				+ "' and course_id='" + course_id + "'";
		int i = db.countselect(sql);
		if (i < 1) {
			sql = "insert into test_examuser_course(id,user_id,course_id,status) values(to_char(s_test_examuser_course.nextval),'"
					+ user_id + "','" + course_id + "','0')";
			i = db.executeUpdate(sql);
			UserAddLog.setDebug("OracleExamActivity.courseAddUser(String user_id, String course_id) SQL=" + sql + " COUNT" + i + " DATE=" + new Date());
		}
		return i;
	}

	public void allotStudents(List idList1, List idList2, String num)
			throws PlatformException {
		MyResultSet rs = null;
		MyResultSet rs_sort = null;
		MyResultSet rs_student = null;
		dbpool db = new dbpool();
		try {
			if ((idList1 != null && idList1.size() > 0)
					&& (idList2 != null && idList2.size() > 0)
					&& (num != null && Integer.parseInt(num) > 0)) {
				int examroom_no = 0;
				String room_no = "";
				int seat_no = 1;
				String sql_student = "";
				String sql_insroom = "";
				String sql_insstu = "";

				String userIds = "";
				String courseIds = "";
				String courseId = "";
				for (Iterator it = idList1.iterator(); it.hasNext();)
					userIds += ",'" + (String) it.next() + "'";
				userIds = userIds.substring(1);
				for (Iterator it = idList2.iterator(); it.hasNext();)
					courseIds += ",'" + (String) it.next() + "'";
				courseIds = courseIds.substring(1);
				String sql_delroom = "delete test_examroom_info where course_id in ("
						+ courseIds + ")";

				// System.out.println("sql_delroom="+sql_delroom);

				db.executeUpdate(sql_delroom);

				for (Iterator it = idList2.iterator(); it.hasNext();) {
					courseId = (String) it.next();
					sql_student = "select id from test_examuser_course where user_id in("
							+ userIds + ") and course_id='" + courseId + "'";

					// System.out.println("sql_student="+sql_student+"<br>");
					/* ��ȡ����ID��ʼ */
					String testroom_id = "";
					String sql_next_id = "select s_test_examroom_info_id.nextval as id from dual";
					rs = db.executeQuery(sql_next_id);
					if (rs != null && rs.next())
						testroom_id = rs.getString("id");
					db.close(rs);
					/* ��ȡ����ID���� */
					rs_student = db.executeQuery(sql_student);

					if (db.countselect(sql_student) != 0) {
						examroom_no++;
						if (examroom_no < 10) {
							room_no = "0" + examroom_no;
						} else {
							room_no = "" + examroom_no;
						}
						sql_insroom = "insert into test_examroom_info (id,room_no,course_id) values ('"
								+ testroom_id
								+ "','"
								+ room_no
								+ "','"
								+ courseId + "')";

						// System.out.println("sql_insroom1="+sql_insroom+"<br>");
						db.executeUpdate(sql_insroom);
					}

					while (rs_student != null && rs_student.next()) {
						if (seat_no > Integer.parseInt(num)) {
							examroom_no++;
							if (examroom_no < 10) {
								room_no = "0" + examroom_no;
							} else {
								room_no = "" + examroom_no;
							}
							/* ��ȡ����ID��ʼ */
							rs = db.executeQuery(sql_next_id);
							if (rs != null && rs.next())
								testroom_id = rs.getString("id");
							db.close(rs);
							/* ��ȡ����ID���� */
							sql_insroom = "insert into test_examroom_info (id,room_no,course_id) values ('"
									+ testroom_id
									+ "','"
									+ room_no
									+ "','"
									+ courseId + "')";

							// System.out.println("sql_insroom2="+sql_insroom+"<br>");

							db.executeUpdate(sql_insroom);
							seat_no = 1;
						}
						String seat_no_str;
						if (seat_no < 10) {
							seat_no_str = "0" + seat_no;
						} else {
							seat_no_str = "" + seat_no;
						}
						sql_insstu = "update test_examuser_course set desk_no='"
								+ seat_no_str
								+ "',room_id='"
								+ testroom_id
								+ "' where id='"
								+ rs_student.getString("id")
								+ "'";

						// System.out.println("sql_insstu="+sql_insstu+"<br>");

						db.executeUpdate(sql_insstu);
						seat_no++;
					}
					db.close(rs_student);
					seat_no = 1;
				}
				db.close(rs_sort);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db.close(rs_sort);
			db.close(rs_student);
		}
	}

	public void allotSiteStudents(String site_id, List idList1, List idList2,
			String num) throws PlatformException {
		MyResultSet rs = null;
		MyResultSet rs_sort = null;
		MyResultSet rs_student = null;
		dbpool db = new dbpool();
		try {
			if ((idList1 != null && idList1.size() > 0)
					&& (idList2 != null && idList2.size() > 0)
					&& (num != null && Integer.parseInt(num) > 0)) {
				int examroom_no = 0;
				String room_no = "";
				int seat_no = 1;
				String sql_student = "";
				String sql_insroom = "";
				String sql_insstu = "";

				String userIds = "";
				String courseIds = "";
				String courseId = "";
				for (Iterator it = idList1.iterator(); it.hasNext();)
					userIds += ",'" + (String) it.next() + "'";
				userIds = userIds.substring(1);
				for (Iterator it = idList2.iterator(); it.hasNext();)
					courseIds += ",'" + (String) it.next() + "'";
				courseIds = courseIds.substring(1);
				String sql_delroom = "delete test_examroom_info where course_id in ("
						+ courseIds + ") and site_id = '" + site_id + "'";

				// System.out.println("sql_delroom="+sql_delroom);

				db.executeUpdate(sql_delroom);

				for (Iterator it = idList2.iterator(); it.hasNext();) {
					courseId = (String) it.next();
					sql_student = "select id from test_examuser_course where user_id in("
							+ userIds + ") and course_id='" + courseId + "'";

					// System.out.println("sql_student="+sql_student+"<br>");
					/* ��ȡ����ID��ʼ */
					String testroom_id = "";
					String sql_next_id = "select s_test_examroom_info_id.nextval as id from dual";
					rs = db.executeQuery(sql_next_id);
					if (rs != null && rs.next())
						testroom_id = rs.getString("id");
					db.close(rs);
					/* ��ȡ����ID���� */
					rs_student = db.executeQuery(sql_student);

					if (db.countselect(sql_student) != 0) {
						examroom_no++;
						if (examroom_no < 10) {
							room_no = "0" + examroom_no;
						} else {
							room_no = "" + examroom_no;
						}
						sql_insroom = "insert into test_examroom_info (id,room_no,course_id,site_id) values ('"
								+ testroom_id
								+ "','"
								+ room_no
								+ "','"
								+ courseId + "','" + site_id + "')";

						// System.out.println("sql_insroom1="+sql_insroom+"<br>");
						db.executeUpdate(sql_insroom);
						UserAddLog.setDebug("OracleExamActivity.allotSiteStudents(String site_id, List idList1, List idList2,String num) SQL=" + sql_insroom + " DATE=" + new Date());
					}

					while (rs_student != null && rs_student.next()) {
						if (seat_no > Integer.parseInt(num)) {
							examroom_no++;
							if (examroom_no < 10) {
								room_no = "0" + examroom_no;
							} else {
								room_no = "" + examroom_no;
							}
							/* ��ȡ����ID��ʼ */
							rs = db.executeQuery(sql_next_id);
							if (rs != null && rs.next())
								testroom_id = rs.getString("id");
							db.close(rs);
							/* ��ȡ����ID���� */
							sql_insroom = "insert into test_examroom_info (id,room_no,course_id,site_id) values ('"
									+ testroom_id
									+ "','"
									+ room_no
									+ "','"
									+ courseId + "','" + site_id + "')";

							// System.out.println("sql_insroom2="+sql_insroom+"<br>");

							db.executeUpdate(sql_insroom);
							UserAddLog.setDebug("OracleExamActivity.allotSiteStudents(String site_id, List idList1, List idList2,String num) SQL=" + sql_insroom + " DATE=" + new Date());
							seat_no = 1;
						}
						String seat_no_str;
						if (seat_no < 10) {
							seat_no_str = "0" + seat_no;
						} else {
							seat_no_str = "" + seat_no;
						}
						sql_insstu = "update test_examuser_course set desk_no='"
								+ seat_no_str
								+ "',room_id='"
								+ testroom_id
								+ "' where id='"
								+ rs_student.getString("id")
								+ "'";

						// System.out.println("sql_insstu="+sql_insstu+"<br>");

						db.executeUpdate(sql_insstu);
						UserUpdateLog.setDebug("OracleExamActivity.allotSiteStudents(String site_id, List idList1, List idList2,String num) SQL=" + sql_insstu + " DATE=" + new Date());
						seat_no++;
					}
					db.close(rs_student);
					seat_no = 1;
				}
				db.close(rs_sort);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db.close(rs_sort);
			db.close(rs_student);
		}
	}

	public List getStudentsByCourseId(List ListcourseId, String examBatchId)
			throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		List userList = new ArrayList();
		MyResultSet rs = null;

		String courseIds = "";

		for (Iterator it = ListcourseId.iterator(); it.hasNext();)
			courseIds += ",'" + (String) it.next() + "'";
		courseIds = courseIds.substring(1);

		sql = "select distinct user_id from test_examuser_batch where batch_id='"
				+ examBatchId
				+ "'"
				+ " and user_id in (select user_id from test_examuser_course where course_id in("
				+ courseIds + "))";
		rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				String user_id = rs.getString("user_id");
				userList.add(user_id);

			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}

		return userList;
	}

	public List getSiteStudentsByCourseId(String site_id, List ListcourseId,
			String examBatchId) throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		List userList = new ArrayList();
		MyResultSet rs = null;

		String courseIds = "";

		for (Iterator it = ListcourseId.iterator(); it.hasNext();)
			courseIds += ",'" + (String) it.next() + "'";
		courseIds = courseIds.substring(1);

		sql = "select distinct user_id from test_examuser_batch where batch_id='"
				+ examBatchId
				+ "'"
				+ " and user_id in (select user_id from test_examuser_course where course_id in("
				+ courseIds
				+ ")) and user_id in (select id from entity_student_info where site_id = '"
				+ site_id + "')";
		rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				String user_id = rs.getString("user_id");
				userList.add(user_id);

			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}

		return userList;
	}

	public List getTotalTestRooms(Page page, String batchId)
			throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		List userList = new ArrayList();
		MyResultSet rs = null;
		String conditions = "";
		if (batchId != null && !batchId.equals(""))
			conditions += (" and test_batch_id='" + batchId + "'");

		sql = "select a.course_id,nums,roomnums,d.test_batch_id as batch_id,d.name,e.name as batch_name "
				+ "from (select tcid.id as course_id,nvl(nums,'0') as nums,nvl(roomnums,'0') as roomnums "
				+ "from (select distinct id from test_examcourse_info) tcid "
				+ "left join (select b.course_id,nums,roomnums from (select count(id) as nums, course_id "
				+ "from test_examuser_course group by course_id) b "
				+ "left join (select count(id) as roomnums, course_id from test_examroom_info group by course_id) c "
				+ "on b.course_id=c.course_id) a0 on tcid.id=a0.course_id )a,test_examcourse_info d,"
				+ "test_exambatch_info e where a.course_id=d.id and d.test_batch_id=e.id "
				+ conditions;

		ArrayList testRooms = new ArrayList();
		try {
			db = new dbpool();

			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {

				HashMap course = new HashMap();
				course.put("courseId", rs.getString("course_id"));
				course.put("name", rs.getString("name"));
				course.put("roomnums", rs.getString("roomnums"));
				course.put("nums", rs.getString("nums"));
				course.put("batchId", rs.getString("batch_id"));
				course.put("batchName", rs.getString("batch_name"));

				testRooms.add(course);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return testRooms;
	}

	public List getTotalTestRooms(Page page, List searchList, List orderList)
			throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		List userList = new ArrayList();
		MyResultSet rs = null;

		sql = "select a.course_id,nums,roomnums,d.test_batch_id as batch_id,d.name,e.name as batch_name "
				+ "from (select tcid.id as course_id,nvl(nums,'0') as nums,nvl(roomnums,'0') as roomnums "
				+ "from (select distinct id from test_examcourse_info) tcid "
				+ "left join (select b.course_id,nums,roomnums from (select count(id) as nums, course_id "
				+ "from test_examuser_course group by course_id) b "
				+ "left join (select count(id) as roomnums, course_id from test_examroom_info group by course_id) c "
				+ "on b.course_id=c.course_id) a0 on tcid.id=a0.course_id )a,test_examcourse_info d,"
				+ "test_exambatch_info e where a.course_id=d.id and d.test_batch_id=e.id ";
		sql += Conditions.convertToAndCondition(searchList, orderList);

		ArrayList testRooms = new ArrayList();
		try {
			db = new dbpool();

			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {

				HashMap course = new HashMap();
				course.put("courseId", rs.getString("course_id"));
				course.put("name", rs.getString("name"));
				course.put("roomnums", rs.getString("roomnums"));
				course.put("nums", rs.getString("nums"));
				course.put("batchId", rs.getString("batch_id"));
				course.put("batchName", rs.getString("batch_name"));

				testRooms.add(course);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return testRooms;
	}

	public List getSiteTotalTestRooms(Page page, List searchList,
			List orderList, String site_id) throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		List userList = new ArrayList();
		MyResultSet rs = null;

		sql = "select a.course_id,nums,roomnums,d.test_batch_id as batch_id,d.name,e.name as batch_name "
				+ "from (select tcid.id as course_id,nvl(nums,'0') as nums,nvl(roomnums,'0') as roomnums "
				+ "from (select distinct id from test_examcourse_info) tcid "
				+ "left join (select b.course_id,nums,roomnums from (select count(a.id) as nums, a.course_id "
				+ "from test_examuser_course a,entity_student_info b where a.user_id=b.id and b.site_id='"
				+ site_id
				+ "' group by a.course_id) b "
				+ "left join (select count(id) as roomnums, course_id from test_examroom_info group by course_id) c "
				+ "on b.course_id=c.course_id) a0 on tcid.id=a0.course_id )a,test_examcourse_info d,"
				+ "test_exambatch_info e where a.course_id=d.id and d.test_batch_id=e.id ";
		sql += Conditions.convertToAndCondition(searchList, orderList);

		ArrayList testRooms = new ArrayList();
		try {
			db = new dbpool();

			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {

				HashMap course = new HashMap();
				course.put("courseId", rs.getString("course_id"));
				course.put("name", rs.getString("name"));
				course.put("roomnums", rs.getString("roomnums"));
				course.put("nums", rs.getString("nums"));
				course.put("batchId", rs.getString("batch_id"));
				course.put("batchName", rs.getString("batch_name"));

				testRooms.add(course);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return testRooms;
	}

	public int getTotalTestRoomsNum(String batchId) throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		String conditions = "";
		if (batchId != null && !batchId.equals(""))
			conditions += (" and test_batch_id='" + batchId + "'");

		sql = "select a.course_id,nums,roomnums,d.test_batch_id as batch_id,d.EXAMSEQUENCE_ID as EXAMSEQUENCE_ID,d.name,e.name as batch_name "
				+ "from (select tcid.id as course_id,nvl(nums,'0') as nums,nvl(roomnums,'0') as roomnums "
				+ "from (select distinct id from test_examcourse_info) tcid "
				+ "left join (select b.course_id,nums,roomnums from (select count(id) as nums, course_id "
				+ "from test_examuser_course group by course_id) b "
				+ "left join (select count(id) as roomnums, course_id from test_examroom_info group by course_id) c "
				+ "on b.course_id=c.course_id) a0 on tcid.id=a0.course_id )a,test_examcourse_info d,"
				+ "test_exambatch_info e where a.course_id=d.id and d.test_batch_id=e.id "
				+ conditions;

		int i = 0;
		i = db.countselect(sql);
		return i;
	}

	public int getTotalTestRoomsNum(List searchList) throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();

		sql = "select a.course_id,nums,roomnums,d.test_batch_id as batch_id,d.EXAMSEQUENCE_ID as EXAMSEQUENCE_ID,d.name,e.name as batch_name "
				+ "from (select tcid.id as course_id,nvl(nums,'0') as nums,nvl(roomnums,'0') as roomnums "
				+ "from (select distinct id from test_examcourse_info) tcid "
				+ "left join (select b.course_id,nums,roomnums from (select count(id) as nums, course_id "
				+ "from test_examuser_course group by course_id) b "
				+ "left join (select count(id) as roomnums, course_id from test_examroom_info group by course_id) c "
				+ "on b.course_id=c.course_id) a0 on tcid.id=a0.course_id )a,test_examcourse_info d,"
				+ "test_exambatch_info e where a.course_id=d.id and d.test_batch_id=e.id ";

		sql += Conditions.convertToAndCondition(searchList);

		int i = 0;
		i = db.countselect(sql);
		return i;
	}

	public int getSiteTotalTestRoomsNum(List searchList, String site_id)
			throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();

		sql = "select a.course_id,nums,roomnums,d.test_batch_id as batch_id,d.EXAMSEQUENCE_ID as EXAMSEQUENCE_ID,d.name,e.name as batch_name "
				+ "from (select tcid.id as course_id,nvl(nums,'0') as nums,nvl(roomnums,'0') as roomnums "
				+ "from (select distinct id from test_examcourse_info) tcid "
				+ "left join (select b.course_id,nums,roomnums from (select count(a.id) as nums, a.course_id "
				+ "from test_examuser_course a,entity_student_info b where a.user_id=b.id and b.site_id='"
				+ site_id
				+ "' group by a.course_id) b "
				+ "left join (select count(id) as roomnums, course_id from test_examroom_info group by course_id) c "
				+ "on b.course_id=c.course_id) a0 on tcid.id=a0.course_id )a,test_examcourse_info d,"
				+ "test_exambatch_info e where a.course_id=d.id and d.test_batch_id=e.id ";

		sql += Conditions.convertToAndCondition(searchList);

		int i = 0;
		i = db.countselect(sql);
		return i;
	}

	public List getSiteTestRooms(String siteId, Page page, String batchId,
			String examsequence_id) throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		List userList = new ArrayList();
		MyResultSet rs = null;
		String conditions = "";
		if (batchId != null && !batchId.equals("") && !batchId.equals("null"))
			conditions += (" and test_batch_id='" + batchId + "'");
		if (examsequence_id != null && !examsequence_id.equals("")
				&& !examsequence_id.equals("null"))
			conditions += (" and examsequence_id='" + examsequence_id + "'");

		sql = "select a.course_id,nums,roomnums,d.test_batch_id as batch_id,d.name,e.name as batch_name,d.examsequence_id,f.title as examsequence_name "
				+ "from (select a.id as course_id,nvl(nums, '0') as nums,nvl(roomnums, '0') as roomnums from  test_examcourse_info a,(select count(a.id) as nums, course_id"
				+ " from test_examuser_course a,entity_student_info  b where a.user_id = b.id and b.site_id = '"
				+ siteId
				+ "' group by a.course_id) b, (select count(id) as roomnums, course_id  from test_examroom_info where site_id = '"
				+ siteId
				+ "' group by course_id) c where (nums>0 or roomnums>0) and a.id=b.course_id(+) and a.id=c.course_id(+))a,test_examcourse_info d,"
				+ "test_exambatch_info e,test_examsequence_info f where a.course_id=d.id "
				+ "and d.test_batch_id=e.id and d.examsequence_id=f.id(+) "
				+ conditions;

		ArrayList testRooms = new ArrayList();
		try {
			db = new dbpool();

			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {

				HashMap course = new HashMap();
				course.put("courseId", rs.getString("course_id"));
				course.put("name", rs.getString("name"));
				course.put("roomnums", rs.getString("roomnums"));
				course.put("nums", rs.getString("nums"));
				course.put("batchId", rs.getString("batch_id"));
				course.put("batchName", rs.getString("batch_name"));
				course.put("examsequence_id", rs.getString("examsequence_id"));
				course.put("examsequence_name", rs
						.getString("examsequence_name"));

				testRooms.add(course);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return testRooms;
	}

	public List getSiteTestRooms(String siteId, Page page,
			HttpServletRequest request) throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		List userList = new ArrayList();
		MyResultSet rs = null;
		String conditions = "";
		String order = "";
		order = " order by test_batch_id,examsequence_id,course_id";
		if (request.getParameter("exambatchid") != null
				&& !request.getParameter("exambatchid").equals(""))
			conditions += (" and test_batch_id='"
					+ request.getParameter("exambatchid") + "'");

		if (request.getParameter("examsequence_id") != null
				&& !request.getParameter("examsequence_id").equals(""))
			conditions += (" and examsequence_id='"
					+ request.getParameter("examsequence_id") + "'");

		sql = "select a.course_id,d.examsequence_id,nums,roomnums,d.test_batch_id as batch_id,d.name,e.name as batch_name "
				+ "from (select tcid.id as course_id,nvl(nums,'0') as nums,nvl(roomnums,'0') as roomnums "
				+ "from (select distinct a.id from test_examcourse_info a,test_examuser_course b,entity_student_info c "
				+ "where a.id = b.course_id and b.user_id = c.id and c.site_id ='"
				+ siteId
				+ "' ) tcid "
				+ "left join (select b.course_id,nums,roomnums from (select count(a.id) as nums, course_id "
				+ "from test_examuser_course a,entity_student_info b where a.user_id = b.id and b.site_id = '"
				+ siteId
				+ "' "
				+ "group by a.course_id ) b "
				+ "left join (select count(id) as roomnums, course_id from test_examroom_info where site_id = '"
				+ siteId
				+ "' group by course_id) c "
				+ "on b.course_id=c.course_id) a0 on tcid.id=a0.course_id )a,test_examcourse_info d,"
				+ "test_exambatch_info e where a.course_id=d.id and d.test_batch_id=e.id "
				+ conditions + order;

		ArrayList testRooms = new ArrayList();
		try {
			db = new dbpool();

			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {

				HashMap course = new HashMap();
				course.put("courseId", rs.getString("course_id"));
				course.put("name", rs.getString("name"));
				course.put("roomnums", rs.getString("roomnums"));
				course.put("nums", rs.getString("nums"));
				course.put("batchId", rs.getString("batch_id"));
				course.put("batchName", rs.getString("batch_name"));

				testRooms.add(course);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return testRooms;
	}

	public List getTestRooms(String siteId, Page page,
			HttpServletRequest request) throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		List userList = new ArrayList();
		MyResultSet rs = null;
		String conditions = "";
		String order = "";
		order = " order by test_batch_id,examsequence_id,course_id";
		if (request.getParameter("exambatchid") != null
				&& !request.getParameter("exambatchid").equals(""))
			conditions += (" and test_batch_id='"
					+ request.getParameter("exambatchid") + "'");

		if (request.getParameter("examsequence_id") != null
				&& !request.getParameter("examsequence_id").equals(""))
			conditions += (" and examsequence_id='"
					+ request.getParameter("examsequence_id") + "'");

		if (request.getParameter("course_id") != null
				&& !request.getParameter("course_id").equals(""))
			conditions += (" and course_id='"
					+ request.getParameter("course_id") + "'");

		String siteCon = "";
		String siteCon1 = "";
		if (siteId != null && !siteId.equals("")) {
			siteCon = " and c.site_id ='" + siteId + "' ";
			siteCon1 = " and b.site_id ='" + siteId + "' ";
		}

		sql = "select a.course_id,d.examsequence_id,nums,roomnums,d.test_batch_id as batch_id,d.name,"
				+ "e.name as batch_name,a.site_name,a.site_id from (select tcid.id as course_id,nvl(nums,'0') as nums,"
				+ "nvl(roomnums,'0') as roomnums,site_name,site_id from (select distinct a.id from test_examcourse_info a,"
				+ "test_examuser_course b,entity_student_info c where a.id = b.course_id and b.user_id = c.id  "
				+ siteCon
				+ "  ) tcid "
				+ "left join (select b.course_id,nums,roomnums,site_name,site_id from (select count(a.id) as nums, course_id,"
				+ "c.name as site_name,c.id as site_id from test_examuser_course a,entity_student_info b,entity_site_info c where a.user_id = b.id "
				+ "and b.site_id = c.id "
				+ siteCon1
				+ " group by a.course_id,c.name,c.id ) b left join (select count(id) as roomnums, course_id from test_examroom_info group by course_id) c "
				+ "on b.course_id=c.course_id) a0 on tcid.id=a0.course_id )a,test_examcourse_info d,test_exambatch_info e "
				+ "where a.course_id=d.id and d.test_batch_id=e.id "
				+ conditions + order;

		ArrayList testRooms = new ArrayList();
		try {
			db = new dbpool();

			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {

				HashMap course = new HashMap();
				course.put("courseId", rs.getString("course_id"));
				course.put("name", rs.getString("name"));
				course.put("site_name", rs.getString("site_name"));
				course.put("roomnums", rs.getString("roomnums"));
				course.put("nums", rs.getString("nums"));
				course.put("batchId", rs.getString("batch_id"));
				course.put("siteId", rs.getString("site_id"));
				course.put("batchName", rs.getString("batch_name"));

				testRooms.add(course);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return testRooms;
	}

	public int getSiteTestRoomsNum(String siteId, String batchId,
			String examsequence_id) throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		String conditions = "";
		if (batchId != null && !batchId.equals("") && !batchId.equals("null"))
			conditions += (" and test_batch_id='" + batchId + "'");
		if (examsequence_id != null && !examsequence_id.equals("")
				&& !examsequence_id.equals("null"))
			conditions += (" and examsequence_id='" + examsequence_id + "'");

		sql = "select a.course_id,nums,roomnums,d.test_batch_id as batch_id,d.name,e.name as batch_name,d.examsequence_id,f.title as examsequence_name "
				+ "from (select a.id as course_id,nvl(nums, '0') as nums,nvl(roomnums, '0') as roomnums from  test_examcourse_info a,(select count(a.id) as nums, course_id"
				+ " from test_examuser_course a,entity_student_info  b where a.user_id = b.id and b.site_id = '"
				+ siteId
				+ "' group by a.course_id) b, (select count(id) as roomnums, course_id  from test_examroom_info where site_id = '"
				+ siteId
				+ "' group by course_id) c where (nums>0 or roomnums>0) and a.id=b.course_id(+) and a.id=c.course_id(+))a,test_examcourse_info d,"
				+ "test_exambatch_info e,test_examsequence_info f where a.course_id=d.id "
				+ "and d.test_batch_id=e.id and d.examsequence_id=f.id(+) "
				+ conditions;

		int i = 0;
		i = db.countselect(sql);
		return i;
	}

	public int getSiteTestRoomsNum(String siteId, HttpServletRequest request)
			throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		String conditions = "";
		if (request.getParameter("exambatchid") != null
				&& !request.getParameter("exambatchid").equals(""))
			conditions += (" and test_batch_id='"
					+ request.getParameter("exambatchid") + "'");

		if (request.getParameter("examsequence_id") != null
				&& !request.getParameter("examsequence_id").equals(""))
			conditions += (" and test_batch_id='"
					+ request.getParameter("examsequence_id") + "'");

		sql = "select a.course_id,d.examsequence_id,nums,roomnums,d.test_batch_id as batch_id,d.name,e.name as batch_name "
				+ "from (select tcid.id as course_id,nvl(nums,'0') as nums,nvl(roomnums,'0') as roomnums "
				+ "from (select distinct a.id from test_examcourse_info a,test_examuser_course b,entity_student_info c "
				+ "where a.id = b.course_id and b.user_id = c.id and c.site_id ='"
				+ siteId
				+ "' ) tcid "
				+ "left join (select b.course_id,nums,roomnums from (select count(a.id) as nums, course_id "
				+ "from test_examuser_course a,entity_student_info b where a.user_id = b.id and b.site_id = '"
				+ siteId
				+ "' "
				+ "group by a.course_id ) b "
				+ "left join (select count(id) as roomnums, course_id from test_examroom_info where site_id = '"
				+ siteId
				+ "' group by course_id) c "
				+ "on b.course_id=c.course_id) a0 on tcid.id=a0.course_id )a,test_examcourse_info d,"
				+ "test_exambatch_info e where a.course_id=d.id and d.test_batch_id=e.id "
				+ conditions;

		int i = 0;
		i = db.countselect(sql);
		return i;
	}

	public int getTestRoomsNum(String siteId, HttpServletRequest request)
			throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		String conditions = "";
		if (request.getParameter("exambatchid") != null
				&& !request.getParameter("exambatchid").equals(""))
			conditions += (" and test_batch_id='"
					+ request.getParameter("exambatchid") + "'");

		if (request.getParameter("examsequence_id") != null
				&& !request.getParameter("examsequence_id").equals(""))
			conditions += (" and examsequence_id='"
					+ request.getParameter("examsequence_id") + "'");

		String siteCon = "";
		String siteCon1 = "";
		if (siteId != null && !siteId.equals("")) {
			siteCon = " and c.site_id ='" + siteId + "' ";
			siteCon1 = " and b.site_id ='" + siteId + "' ";
		}

		sql = "select a.course_id,d.examsequence_id,nums,roomnums,d.test_batch_id as batch_id,d.name,"
				+ "e.name as batch_name,a.site_name from (select tcid.id as course_id,nvl(nums,'0') as nums,"
				+ "nvl(roomnums,'0') as roomnums,site_name from (select distinct a.id from test_examcourse_info a,"
				+ "test_examuser_course b,entity_student_info c where a.id = b.course_id and b.user_id = c.id  "
				+ siteCon
				+ "  ) tcid "
				+ "left join (select b.course_id,nums,roomnums,site_name from (select count(a.id) as nums, course_id,"
				+ "c.name as site_name from test_examuser_course a,entity_student_info b,entity_site_info c where a.user_id = b.id "
				+ "and b.site_id = c.id "
				+ siteCon1
				+ " group by a.course_id,c.name ) b left join (select count(id) as roomnums, course_id from test_examroom_info group by course_id) c "
				+ "on b.course_id=c.course_id) a0 on tcid.id=a0.course_id )a,test_examcourse_info d,test_exambatch_info e "
				+ "where a.course_id=d.id and d.test_batch_id=e.id "
				+ conditions;

		int i = 0;
		i = db.countselect(sql);
		return i;
	}

	public int getTotalTestRoomsNum(String siteId, String batchId)
			throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List getAllotTestRooms(Page page, String batchId)
			throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		List userList = new ArrayList();
		MyResultSet rs = null;
		String conditions = "";
		if (batchId != null && !batchId.equals(""))
			conditions += (" and test_batch_id='" + batchId + "'");

		sql = "(select a.course_id,nums,roomnums,d.test_batch_id as batch_id,d.name,e.name as batch_name "
				+ "from (select tcid.id as course_id,nvl(nums,'0') as nums,nvl(roomnums,'0') as roomnums "
				+ "from (select distinct id from test_examcourse_info) tcid "
				+ "left join (select b.course_id,nums,roomnums from (select count(id) as nums, course_id "
				+ "from test_examuser_course group by course_id) b "
				+ "left join (select count(id) as roomnums, course_id from test_examroom_info group by course_id) c "
				+ "on b.course_id=c.course_id) a0 on tcid.id=a0.course_id )a,test_examcourse_info d,"
				+ "test_exambatch_info e where a.course_id=d.id and d.test_batch_id=e.id "
				+ conditions + ") out1";
		String sql1 = "(select rc.course_id,maxroomnums,room_id,room_no,rno.status "
				+ "from (select course_id,count(user_id) as maxroomnums,room_id "
				+ "from test_examuser_course group by room_id,course_id) rc,"
				+ "( select a.id,a.room_no,a.status from test_examroom_info a,"
				+ "(select max(room_no) as maxno,course_id from test_examroom_info group by course_id) b "
				+ "where a.course_id=b.course_id and a.room_no=b.maxno) rno where rc.room_id=rno.id) out2";

		sql = ("select out1.course_id,nums,roomnums,batch_id,name,batch_name,out2.maxroomnums,room_id,room_no,out2.status from "
				+ sql + "," + sql1 + " where out1.course_id=out2.course_id");

		ArrayList testRooms = new ArrayList();
		try {
			db = new dbpool();

			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {

				HashMap course = new HashMap();
				course.put("courseId", rs.getString("course_id"));
				course.put("name", rs.getString("name"));
				course.put("roomnums", rs.getString("roomnums"));
				course.put("nums", rs.getString("nums"));
				course.put("batchId", rs.getString("batch_id"));
				course.put("batchName", rs.getString("batch_name"));
				course.put("maxroomnums", rs.getString("maxroomnums"));
				course.put("room_no", rs.getString("room_no"));
				course.put("room_id", rs.getString("room_id"));
				course.put("status", rs.getString("status"));
				testRooms.add(course);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return testRooms;
	}

	public int getAllotTestRoomsNum(String batchId) throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		List conditions = new ArrayList();
		if (batchId != null && !batchId.equals(""))
			conditions.add(new SearchProperty("test_batch_id", batchId, "="));

		sql = "  select  distinct  course_id ,name, nums ,roomnums,batch_id from"
				+ " ( select a.course_id as course_id,a.name as name,nvl(b.nums,0) as nums ,nvl(c.roomnums,0) as roomnums,a.test_batch_id as batch_id from"
				+ " (select course_id,name,test_batch_id from test_examcourse_info"
				+ Conditions.convertToCondition(conditions, null)
				+ " ) a,"
				+ "(select  count(id) as nums, course_id from  test_examuser_course group by course_id) b,"
				+ "(select count(id) as roomnums,course_id from test_examroom_info group by course_id) c"
				+ " where a.course_id=b.course_id(+) and a.course_id=c.course_id(+))";

		int i = 0;
		i = db.countselect(sql);
		return i;
	}

	public int allotTestRooms(String[] room_id) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		MyResultSet rs_seat_no = null;
		MyResultSet rs_seat_no1 = null;
		int suc = 0;
		int num1 = 1;
		if (room_id == null || room_id.length < 2)
			return 0;
		String room_select = "";
		for (int i = 0; i < room_id.length; i++) {
			room_select = room_select + "'" + room_id[i] + "',";
		}
		room_select = room_select.substring(0, room_select.length() - 1);
		String sql_seat_no1 = "";
		String sql_seat_no2 = "";

		String sql_seat_no = "select max(to_number(desk_no)) as desk_no from test_examuser_course where room_id='"
				+ room_id[0] + "'";
		//System.out.print(sql_seat_no);
		rs_seat_no = db.executeQuery(sql_seat_no);
		int seat_no = 0;
		String seat_no_str = "";
		try {
			if (rs_seat_no != null && rs_seat_no.next())
				seat_no = rs_seat_no.getInt("desk_no");
			db.close(rs_seat_no);
			seat_no++;
			int status1 = 0;
			while (num1 < room_id.length) {
				sql_seat_no1 = "select distinct desk_no from test_examuser_course where room_id='"
						+ room_id[num1] + "' order by to_number(desk_no)";
				rs_seat_no1 = db.executeQuery(sql_seat_no1);
				while (rs_seat_no1 != null && rs_seat_no1.next()) {
					if (seat_no < 10) {
						seat_no_str = "0" + seat_no;
					} else {
						seat_no_str = "" + seat_no;
					}
					sql_seat_no2 = "update test_examuser_course set desk_no='"
							+ seat_no_str + "' where room_id='" + room_id[num1]
							+ "' and desk_no='"
							+ rs_seat_no1.getString("desk_no") + "'";
					status1 = db.executeUpdate(sql_seat_no2);
					if (status1 > 0) {
						seat_no++;
					}
				}
				db.close(rs_seat_no1);
				num1++;
			}
			String sql = "select room_no from test_examroom_info where id='"
					+ room_id[0] + "'";
			rs = db.executeQuery(sql);
			String room_no = "";
			if (rs != null && rs.next()) {
				room_no = rs.getString("room_no");
			}
			db.close(rs);

			sql = "update test_examroom_info set room_no='" + room_no
					+ "',status='1' where id in (" + room_select + ")";
			suc = db.executeUpdate(sql);

			// ����������
			String sql_examroom = "select distinct room_no from test_examroom_info order by to_number(room_no) asc";
			rs = db.executeQuery(sql_examroom);
			String startNoStr = "00";
			int startNo = 1;
			while (rs != null && rs.next()) {
				int curRoomNo = rs.getInt("room_no");
				if (curRoomNo > startNo) {
					if (startNo < 10) {
						startNoStr = "0" + startNo;
					} else {
						startNoStr = "" + startNo;
					}
					db.executeUpdate("update test_examroom_info set room_no='"
							+ startNoStr + "' where to_number(room_no)='"
							+ curRoomNo + "'");
					startNo++;
				}

			}
			db.close(rs);
			// �������
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
			db.close(rs_seat_no);
			db.close(rs_seat_no1);
		}
		return suc;
	}

	public List getExamRoomNoStudents(String course_id, String room_no,
			String batchId) throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		MyResultSet rs = null;
		sql = "select  distinct e.room_no as room_no,a.desk_no as desk_no,b.examcode as examcode,"
				+ "c.name as course_name,d.name  as student_name,c.s_date as s_date,c.e_date as e_date,g.name as site_name,h.name as grade_name,i.name as major_name,j.name as edutype_name "
				+ "from test_examuser_course a ,test_examuser_batch b,test_examcourse_info c,entity_student_info d,"
				+ "test_examroom_info e,entity_site_info g,entity_grade_info h,entity_major_info i,entity_edu_type j"
				+ " where a.user_id=b.user_id and a.course_id=c.id and a.user_id=d.id and a.user_id = d.id and d.site_id = g.id and d.edutype_id=j.id and d.major_id = i.id and d.grade_id=h.id and "
				+ "e.id=a.room_id and  a.course_id='"
				+ course_id
				+ "' and e.id='"
				+ room_no
				+ "' and  c.test_batch_id='"
				+ batchId + "' order by to_number(examcode)";

		ArrayList testRooms = new ArrayList();

		rs = db.executeQuery(sql);
		try {

			while (rs != null && rs.next()) {

				HashMap course = new HashMap();
				course.put("desk_no", rs.getString("desk_no"));
				course.put("examcode", rs.getString("examcode"));
				course.put("course_name", rs.getString("course_name"));
				course.put("student_name", rs.getString("student_name"));
				course.put("site_name", rs.getString("site_name"));
				course.put("grade_name", rs.getString("grade_name"));
				course.put("major_name", rs.getString("major_name"));
				course.put("edutype_name", rs.getString("edutype_name"));
				course.put("s_date", rs.getString("s_date"));
				course.put("e_date", rs.getString("e_date"));
				testRooms.add(course);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

			return testRooms;
		}
	}

	public List getSiteExamRoomNoStudents(String course_id, String room_no,
			String batchId, String site_id) throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		MyResultSet rs = null;
		sql = "select  distinct e.room_no as room_no,a.desk_no as desk_no,b.examcode as examcode,"
				+ "c.name as course_name,d.name  as student_name,c.s_date as s_date,c.e_date as e_date,g.name as site_name,h.name as grade_name,i.name as major_name,j.name as edutype_name,h.id,i.id,j.id "
				+ "from test_examuser_course a ,test_examuser_batch b,test_examcourse_info c,entity_student_info d,"
				+ "test_examroom_info e,entity_site_info g,entity_grade_info h,entity_major_info i,entity_edu_type j"
				+ " where a.user_id=b.user_id and a.course_id=c.id and a.user_id=d.id and a.user_id = d.id and d.site_id = g.id and d.edutype_id=j.id and d.major_id = i.id and d.grade_id=h.id and "
				+ "e.id=a.room_id and d.site_id='"
				+ site_id
				+ "' and a.course_id='"
				+ course_id
				+ "' and e.id='"
				+ room_no
				+ "' and  c.test_batch_id='"
				+ batchId
				+ "' order by h.id,j.id,i.id,to_number(examcode)";

		ArrayList testRooms = new ArrayList();

		rs = db.executeQuery(sql);
		try {

			while (rs != null && rs.next()) {

				HashMap course = new HashMap();
				course.put("desk_no", rs.getString("desk_no"));
				course.put("examcode", rs.getString("examcode"));
				course.put("course_name", rs.getString("course_name"));
				course.put("student_name", rs.getString("student_name"));
				course.put("site_name", rs.getString("site_name"));
				course.put("grade_name", rs.getString("grade_name"));
				course.put("major_name", rs.getString("major_name"));
				course.put("edutype_name", rs.getString("edutype_name"));
				course.put("s_date", rs.getString("s_date"));
				course.put("e_date", rs.getString("e_date"));
				testRooms.add(course);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

			return testRooms;
		}
	}

	public List getStudent(Page page, String site_id, String edutype_id,
			String major_id, String grade_id, String name, String regno)
			throws PlatformException {
		ArrayList studentlist = new ArrayList();
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		List searchProperties = new ArrayList();
		List orderProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (edutype_id != null && !edutype_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edutype_id,
					"="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("b.name", name, "like"));
		}
		if (regno != null && !regno.equals("")) {
			searchProperties.add(new SearchProperty("reg_no", regno, "like"));
		}

		orderProperties.add(new OrderProperty("login_id", "1"));

		sql = "select a.login_id as regno, b.name as name,b.id as id,b.status as status ,"
				+ "c.name as major_name,c.id as major_id,d.name as site_name,d.id as site_id,"
				+ "e.name as grade_name,e.id as grade_id,f.name as edutype_name,f.id as edutype_id  "
				+ "from test_user_info a, entity_student_info b,entity_major_info c,entity_site_info d,"
				+ "entity_grade_info e,entity_edu_type f where a.login_id=b.reg_no and b.site_id=d.id "
				+ "and b.major_id=c.id and b.grade_id=e.id and b.edutype_id=f.id and b.isgraduated='0' "
				+ "and b.status='0'";

		sql = sql
				+ Conditions.convertToAndCondition(searchProperties,
						orderProperties);
		EntityLog.setDebug(sql);
		try {
			rs = db.execute_oracle_page(sql, page.getPageInt(), page
					.getPageSize());
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setLoginId(rs.getString("regno"));

				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));

				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setGrade_name(rs.getString("grade_name"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));

				eduInfo.setReg_no(rs.getString("regno"));
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("site_name"));
				eduInfo.setStatus(rs.getString("status"));

				student.setStudentInfo(eduInfo);
				studentlist.add(student);
			}
		} catch (java.sql.SQLException e) {
			Log.setError(sql);
		} finally {
			db.close(rs);
			db = null;
			return studentlist;
		}

	}
	/**
	 * @author shu
	 * @param site_id
	 * @param edutype_id
	 * @param major_id
	 * @param grade_id
	 * @param name
	 * @param regno
	 * @param batch_id
	 * @return ͨ���������µĲμӿ��Ե�ѧ����Ϣ��û�п���Ĳ�ѯ������õ���ѯ��4�Ľ��
	 * @throws PlatformException
	 */	
	public List getStudent(Page page, String site_id, String edutype_id,
			String major_id, String grade_id, String name, String regno,
			String batch_id) throws PlatformException {
		ArrayList studentlist = new ArrayList();
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (edutype_id != null && !edutype_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edutype_id,
					"="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (regno != null && !regno.equals("")) {
			searchProperties.add(new SearchProperty("regno", regno, "like"));
		}
		String conditions = "";
		if (batch_id != null && !batch_id.equals("")
				&& !batch_id.equals("null"))
			conditions += " and g.batch_id='" + batch_id + "' ";
		sql = "select a.login_id as regno, b.name as name,b.id as id,b.status as status ,c.name as major_name,"
				+ "c.id as major_id,d.name as site_name,d.id as site_id,e.name as grade_name,e.id as grade_id,"
				+ "f.name as edutype_name,f.id as edutype_id  from test_user_info a, entity_student_info b,"
				+ "entity_major_info c,entity_site_info d,entity_grade_info e,entity_edu_type f,test_examuser_batch g where "
				+ "a.login_id=b.reg_no and b.site_id=d.id and b.major_id=c.id and b.grade_id=e.id and "
				+ "b.edutype_id=f.id and b.isgraduated='0' and b.status='0' "
				+ conditions + " and g.user_id=a.id";

		sql = sql + Conditions.convertToAndCondition(searchProperties);
		try {
			rs = db.execute_oracle_page(sql, page.getPageInt(), page
					.getPageSize());
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setLoginId(rs.getString("regno"));

				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));

				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setGrade_name(rs.getString("grade_name"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));

				eduInfo.setReg_no(rs.getString("regno"));
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("site_name"));
				eduInfo.setStatus(rs.getString("status"));

				student.setStudentInfo(eduInfo);
				studentlist.add(student);
			}
		} catch (java.sql.SQLException e) {
			Log.setError(sql);
		} finally {
			db.close(rs);
			db = null;
			return studentlist;
		}

	}
	
	/**
	 * @author shu
	 * @param kaoqu_id
	 * @param site_id
	 * @param edutype_id
	 * @param major_id
	 * @param grade_id
	 * @param name
	 * @param regno
	 * @param batch_id
	 * @return ͨ���������µĲμӿ��Ե�ѧ����Ϣ,�п���Ĳ�ѯ������õ���ѯ��4�Ľ��
	 * @throws PlatformException
	 */	
	public List getStudent(Page page, String kaoqu_id,String site_id, String edutype_id,
			String major_id, String grade_id, String name, String regno,
			String batch_id) throws PlatformException {
		ArrayList studentlist = new ArrayList();
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		List searchProperties = new ArrayList();
		if (kaoqu_id != null && !kaoqu_id.equals("")) {
			searchProperties.add(new SearchProperty("kaoqu_id", kaoqu_id, "="));
		}
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (edutype_id != null && !edutype_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edutype_id,
					"="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("b.name", name, "like"));
		}
		if (regno != null && !regno.equals("")) {
			searchProperties.add(new SearchProperty("a.login_id", regno, "like"));
		}
		String conditions = "";
		if (batch_id != null && !batch_id.equals("")
				&& !batch_id.equals("null"))
			conditions += " and g.batch_id='" + batch_id + "' ";
		sql = "select distinct a.login_id as regno, b.name as name,b.id as id,b.status as status ,c.name as major_name,"
				+ "c.id as major_id,d.name as site_name,d.id as site_id,e.name as grade_name,e.id as grade_id,ki.id as kaoqu_id,ki.name as kaoqu_name,"
				+ "f.name as edutype_name,f.id as edutype_id  from expendtest_user_info a, entity_student_info b,entity_kaoqu_info ki,"
				+ "entity_major_info c,entity_site_info d,entity_grade_info e,entity_edu_type f,expendtest_examuser_batch g where "
				+ "a.login_id=b.reg_no and b.site_id=d.id and b.major_id=c.id and b.grade_id=e.id and ki.id = b.kaoqu_id and "
				+ "b.edutype_id=f.id and b.isgraduated='0' and b.status='0' "
				+ conditions + " and g.user_id=a.id";

		sql = sql + Conditions.convertToAndCondition(searchProperties);
		try {
			rs = db.execute_oracle_page(sql, page.getPageInt(), page
					.getPageSize());
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setLoginId(rs.getString("regno"));

				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));

				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setGrade_name(rs.getString("grade_name"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));

				eduInfo.setReg_no(rs.getString("regno"));
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("site_name"));
//				eduInfo.setKaoqu_name(rs.getString("kaoqu_name"));
				eduInfo.setStatus(rs.getString("status"));

				student.setStudentInfo(eduInfo);
				studentlist.add(student);
			}
		} catch (java.sql.SQLException e) {
			Log.setError(sql);
		} finally {
			db.close(rs);
			db = null;
			return studentlist;
		}

	}

	public int getStudentNum(String site_id, String edutype_id,
			String major_id, String grade_id, String name, String regno)
			throws PlatformException {
		ArrayList studentlist = new ArrayList();
		dbpool db = new dbpool();
		String sql = "";

		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (edutype_id != null && !edutype_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edutype_id,
					"="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("b.name", name, "like"));
		}
		if (regno != null && !regno.equals("")) {
			searchProperties.add(new SearchProperty("reg_no", regno, "like"));
		}

		sql = "select a.login_id as regno, b.name as name,b.id as id,b.status as status "
				+ ",c.name as major_name,c.id as major_id,d.name as site_name,d.id as site_id,"
				+ "e.name as grade_name,e.id as grade_id,f.name as edutype_name,f.id as edutype_id  "
				+ "from test_user_info a, entity_student_info b,entity_major_info c,entity_site_info d,"
				+ "entity_grade_info e,entity_edu_type f where a.login_id=b.reg_no and b.site_id=d.id and "
				+ "b.major_id=c.id and b.grade_id=e.id and b.edutype_id=f.id and b.isgraduated='0' and b.status='0'";

		sql = sql + Conditions.convertToAndCondition(searchProperties);

		return db.countselect(sql);
	}
	/**
	 * @author shu
	 * @param site_id
	 * @param edutype_id
	 * @param major_id
	 * @param grade_id
	 * @param name
	 * @param regno
	 * @param batch_id
	 * @return ͨ���������µĲμӿ��Ե�ѧ����Ϣ��û�п���Ĳ�ѯ������õ���ѯ��4������
	 * @throws PlatformException
	 */
	public int getStudentNum(String site_id, String edutype_id,
			String major_id, String grade_id, String name, String regno,
			String batch_id) throws PlatformException {
		ArrayList studentlist = new ArrayList();
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (edutype_id != null && !edutype_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edutype_id,
					"="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("b.name", name, "like"));
		}
		if (regno != null && !regno.equals("")) {
			searchProperties.add(new SearchProperty("reg_no", regno, "like"));
		}
		String conditions = "";
		if (batch_id != null && !batch_id.equals("")
				&& !batch_id.equals("null"))
			conditions += " and g.batch_id='" + batch_id + "' ";
		sql = "select a.login_id as regno, b.name as name,b.id as id,b.status as status ,c.name as major_name,"
				+ "c.id as major_id,d.name as site_name,d.id as site_id,e.name as grade_name,e.id as grade_id,"
				+ "f.name as edutype_name,f.id as edutype_id  from test_user_info a, entity_student_info b,"
				+ "entity_major_info c,entity_site_info d,entity_grade_info e,entity_edu_type f,test_examuser_batch g where "
				+ "a.login_id=b.reg_no and b.site_id=d.id and b.major_id=c.id and b.grade_id=e.id and "
				+ "b.edutype_id=f.id and b.isgraduated='0' and b.status='0' "
				+ conditions + " and g.user_id=a.id";
		sql = sql + Conditions.convertToAndCondition(searchProperties);
		return db.countselect(sql);
	}
	/**
	 * @author shu
	 * @param kaoqu_id
	 * @param site_id
	 * @param edutype_id
	 * @param major_id
	 * @param grade_id
	 * @param name
	 * @param regno
	 * @param batch_id
	 * @return ͨ���������µĲμӿ��Ե�ѧ����Ϣ,�п���Ĳ�ѯ������õ���ѯ��4������
	 * @throws PlatformException
	 */
	public int getStudentNum(String kaoqu_id,String site_id, String edutype_id,
			String major_id, String grade_id, String name, String regno,
			String batch_id) throws PlatformException {
		ArrayList studentlist = new ArrayList();
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		List searchProperties = new ArrayList();
		if (kaoqu_id != null && !kaoqu_id.equals("")) {
			searchProperties.add(new SearchProperty("kaoqu_id", kaoqu_id, "="));
		}
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (edutype_id != null && !edutype_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edutype_id,
					"="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("b.name", name, "like"));
		}
		if (regno != null && !regno.equals("")) {
			searchProperties.add(new SearchProperty("reg_no", regno, "like"));
		}
		String conditions = "";
		if (batch_id != null && !batch_id.equals("")
				&& !batch_id.equals("null"))
			conditions += " and g.batch_id='" + batch_id + "' ";
		sql = "select distinct a.login_id as regno, b.name as name,b.id as id,b.status as status ,c.name as major_name,"
				+ "c.id as major_id,d.name as site_name,d.id as site_id,e.name as grade_name,e.id as grade_id,ki.name as kaoqu_name,"
				+ "f.name as edutype_name,f.id as edutype_id  from expendtest_user_info a, entity_student_info b,entity_kaoqu_info ki,"
				+ "entity_major_info c,entity_site_info d,entity_grade_info e,entity_edu_type f,expendtest_examuser_batch g where "
				+ "a.login_id=b.reg_no and b.site_id=d.id and b.major_id=c.id and b.grade_id=e.id and ki.id = b.kaoqu_id and "
				+ "b.edutype_id=f.id and b.isgraduated='0' and b.status='0' "
				+ conditions + " and g.user_id=a.id";
		sql = sql + Conditions.convertToAndCondition(searchProperties);
		return db.countselect(sql);
	}

	public List getBatches(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = "select id,name,to_char(s_date,'yyyy-mm-dd') as s_date,to_char(e_date,'yyyy-mm-dd') as e_date,status,to_char(examroom_s_date,'yyyy-mm-dd') as examroom_s_date,to_char(examroom_e_date,'yyyy-mm-dd') as examroom_e_date from expendtest_exambatch_info";

		sql = sql
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
				OracleReExamBatch oracleExamBatch = new OracleReExamBatch();
				oracleExamBatch.setId(rs.getString("id"));
				oracleExamBatch.setName(rs.getString("name"));
				oracleExamBatch.setStartDate(rs.getString("s_date"));
				oracleExamBatch.setEndDate(rs.getString("e_date"));
				oracleExamBatch.setExamRoomStartDate(rs
						.getString("examroom_s_date"));
				oracleExamBatch.setExamRoomEndDate(rs
						.getString("examroom_e_date"));
				oracleExamBatch.setStatus(rs.getString("status"));
				oracleExamBatches.add(oracleExamBatch);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return oracleExamBatches;
	}

	public List getExamCourses(String user_id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
/*		String sql = "select b.name as course_name,to_char(b.s_date,'yyyy-mm-dd hh24:Mi:ss') as s_date,to_char(b.e_date,'yyyy-mm-dd hh24:Mi:ss') as e_date from expendtest_examuser_course a,expendtest_examcourse_info b where a.course_id=b.id and a.user_id='"
				+ user_id + "'";
*/		String sql = 
				"select b.name as course_name,to_char(b.s_date,'yyyy-mm-dd hh24:Mi:ss') as s_date1e,to_char(b.e_date,'yyyy-mm-dd hh24:Mi:ss') as e_datee," +
				" to_char(c.enddate,'yyyy-mm-dd hh24:Mi:ss') as e_date,to_char(c.startdate,'yyyy-mm-dd hh24:Mi:ss') as s_date" + 
				" from expendtest_examuser_course a,expendtest_examcourse_info b,expendtest_examsequence_info c where a.course_id=b.id and c.testbatch_id = b.test_batch_id" + 
				" and b.basicsequence_id = c.basicsequence_id and a.user_id='"+user_id+"'";				
		ArrayList lsit = null;
		List list = new ArrayList();
		try {
			db = new dbpool();
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("course_name", rs.getString("course_name"));
				hash.put("s_date", rs.getString("s_date"));
				hash.put("e_date", rs.getString("e_date"));
				list.add(hash);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return list;
	}
	
	public List getExamCourses(String activeBatchId,String user_id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
/*		String sql = "select b.name as course_name,to_char(b.s_date,'yyyy-mm-dd hh24:Mi:ss') as s_date,to_char(b.e_date,'yyyy-mm-dd hh24:Mi:ss') as e_date from expendtest_examuser_course a,expendtest_examcourse_info b where a.course_id=b.id and b.test_batch_id='"+activeBatchId+"' and a.user_id='"
				+ user_id + "'";
*/
		String sql = 
			"select b.name as course_name,to_char(b.s_date,'yyyy-mm-dd hh24:Mi:ss') as s_date1e,to_char(b.e_date,'yyyy-mm-dd hh24:Mi:ss') as e_datee," +
			"       to_char(c.enddate,'yyyy-mm-dd hh24:Mi:ss') as e_date,to_char(c.startdate,'yyyy-mm-dd hh24:Mi:ss') as s_date" + 
			" from expendtest_examuser_course a,expendtest_examcourse_info b,expendtest_examsequence_info c where a.course_id=b.id and c.testbatch_id = b.test_batch_id" + 
			" and b.basicsequence_id = c.basicsequence_id and a.user_id='"+user_id+"'";
		ArrayList lsit = null;
		List list = new ArrayList();
		try {
			db = new dbpool();
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("course_name", rs.getString("course_name"));
				hash.put("s_date", rs.getString("s_date"));
				hash.put("e_date", rs.getString("e_date"));
				list.add(hash);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return list;
	}

	public String getTotalStudentsByCourseId(String course_id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select course_id,total from (select course_id,count(id) as total from test_examuser_course group by course_id) where course_id ='"
				+ course_id + "'";
		EntityLog.setDebug(sql);
		String total = "0";
		try {
			db = new dbpool();
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				total = rs.getString("total");
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return total;
	}

	public String getSiteTotalStudentsByCourseId(String site_id,
			String course_id) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select course_id,total from (select course_id,count(id) as total from "
				+ "test_examuser_course where user_id in (select id from entity_student_info "
				+ "where site_id = '"
				+ site_id
				+ "') group by course_id) where course_id ='" + course_id + "'";
		EntityLog.setDebug(sql);
		String total = "0";
		try {
			db = new dbpool();
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				total = rs.getString("total");
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return total;
	}

	public int addTestUserBatch(String user_id, String batch_id)
			throws PlatformException {
		OracleReExamUser userbatch = new OracleReExamUser();
		OracleReExamBatch exambatch = new OracleReExamBatch();
		exambatch.setId(batch_id);
		OracleTestUser examuser = new OracleTestUser(user_id);
		// examuser.setId(user_id);
		userbatch.setTestUser(examuser);
		userbatch.setExamBatch(exambatch);
		userbatch.setExamcode(examuser.getLoginId());
		int suc = userbatch.add();
		return suc;
	}

	public int addTestStudent(String login_id) throws PlatformException {
		OracleSsoUser ssouser = new OracleSsoUser();
		OracleTestUser testuser = new OracleTestUser();

		SsoUser getsso = ssouser.getSsoLoginUser(login_id);
		testuser.setId(getsso.getId());
		testuser.setLoginId(login_id);
		testuser.setEmail(getsso.getEmail());
		testuser.setName(ssouser.getName());
		testuser.setLoginType(ssouser.getLoginType());
		testuser.setPassword(ssouser.getPassword());
		testuser.setFtpaccount(null);
		int suc = testuser.add();
		return suc;
	}

	public int addTestCourse(String course_id, String batch_id)
			throws PlatformException {
		OracleReExamCourse examCourse = new OracleReExamCourse();
		OracleReExamBatch exambatch = new OracleReExamBatch();
		exambatch.setId(batch_id);
		examCourse.setExamBatch(exambatch);
		examCourse.setCourse_id(course_id);
		examCourse.setStartDate("1900-01-01 00:00:00");
		examCourse.setEndDate("1900-01-01 00:00:00");
		OracleCourse course = new OracleCourse(course_id);
		examCourse.setName(course.getName());
		int suc = examCourse.add();
		return suc;
	}

	public int addTestCourse(String open_course_id, String course_id,
			String batch_id) throws PlatformException {
		OracleReExamCourse examCourse = new OracleReExamCourse();
		OracleReExamBatch exambatch = new OracleReExamBatch();
		OracleOpenCourse opencourse = new OracleOpenCourse(open_course_id);
		exambatch.setId(batch_id);
		examCourse.setExamBatch(exambatch);
		examCourse.setCourse_id(course_id);
		// examCourse.setStartDate(opencourse.getExamSequence().getStartDate());
		// examCourse.setEndDate(opencourse.getExamSequence().getEndDate());
		OracleCourse course = new OracleCourse(course_id);
		examCourse.setName(course.getName());
		int suc = examCourse.add();
		return suc;
	}

	public int addTestCourse(String open_course_id, String es_id,
			String course_id, String batch_id) throws PlatformException {
		OracleReExamCourse examCourse = new OracleReExamCourse();
		OracleReExamBatch exambatch = new OracleReExamBatch();
		// OracleOpenCourse opencourse = new OracleOpenCourse(open_course_id);
		exambatch.setId(batch_id);
		examCourse.setExamBatch(exambatch);
		examCourse.setCourse_id(course_id);
		// OracleExamSequence es = new OracleExamSequence(es_id);
		ReExamSequence es = this.getExamSequence(es_id, batch_id);
		examCourse.setStartDate(es.getStartDate());
		examCourse.setEndDate(es.getEndDate());
		OracleCourse course = new OracleCourse(course_id);
		examCourse.setName(course.getName());
		examCourse.setSequence(es);
		int suc = examCourse.add();
		return suc;
	}

	public int addTestCourse(String open_course_id, String es_id,
			String course_id, String batch_id, String course_type,
			String exam_type) throws PlatformException {
		OracleReExamCourse examCourse = new OracleReExamCourse();
		OracleReExamBatch exambatch = new OracleReExamBatch();
		// OracleOpenCourse opencourse = new OracleOpenCourse(open_course_id);
		exambatch.setId(batch_id);
		examCourse.setExamBatch(exambatch);
		examCourse.setCourse_id(course_id);
		// OracleExamSequence es = new OracleExamSequence(es_id);
		ReExamSequence es = this.getExamSequence(es_id, batch_id);
		examCourse.setStartDate(es.getStartDate());
		examCourse.setEndDate(es.getEndDate());
		OracleCourse course = new OracleCourse(course_id);
		examCourse.setName(course.getName());
		examCourse.setSequence(es);
		examCourse.setExamType(exam_type);
		examCourse.setCourseType(course_type);
		int suc = examCourse.add();
		return suc;
	}

	public ReExamSequence getExamSequence(String bs_id, String batch_id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		OracleReExamSequence es = new OracleReExamSequence();
		String sql = "";
		sql = "select id,title,to_char(startdate,'yyyy-mm-dd HH24:MI:SS') as s_date,to_char(enddate,'yyyy-mm-dd HH24:MI:SS') as e_date,note,testbatch_id,basicsequence_id from test_examsequence_info where testbatch_id='"
				+ batch_id + "' and basicsequence_id='" + bs_id + "'";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				es.setId(rs.getString("id"));
				es.setTitle(rs.getString("title"));
				es.setStartDate(rs.getString("s_date"));
				es.setEndDate(rs.getString("e_date"));
				es.setNote(rs.getString("note"));
				es.setBatchId(rs.getString("testbatch_id"));
				es.setBasicSequenceId(rs.getString("basicsequence_id"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("getExamSequence(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return es;
	}

	public int addTestUserCourse(String user_id, String course_id)
			throws PlatformException {
		OracleReExamActivity usercourse = new OracleReExamActivity();
		int suc = usercourse.courseAddUser(user_id, course_id);
		return suc;
	}

	public int importStudent(String open_course_id, String course_id,
			String batch_id) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rss = null;
		String sql = "";
		sql = "select distinct c.examsequence_id as es_id,c.exam_type as course_type from entity_course_active a,entity_teachplan_course b,entity_executeplan_course c where a.id='"
				+ open_course_id
				+ "' and a.course_id=b.course_id and b.id=c.teachplan_course_id and c.examsequence_id<>'0'";
		int i = 0;
		rss = db.executeQuery(sql);
		try {
			while (rss != null && rss.next()) {
				String es_id = rss.getString("es_id");
				int testCourseId = addTestCourse(open_course_id, es_id,
						course_id, batch_id, rss.getString("course_type"), "0");

				sql = "insert into test_user_info (id,name,email,ftpaccount,login_id,login_type,password) select id,name,email,'',login_id,login_type,password from sso_user where login_id in (select distinct c.reg_no from entity_teach_class a, entity_elective b,entity_student_info c,entity_executeplan_course d,entity_teachplan_course e,entity_teachplan_info f,entity_course_active g where c.status='0' and c.isgraduated ='0' and a.id=b.teachclass_id and b.student_id=c.id and a.open_course_id='"
						+ open_course_id
						+ "' and a.open_course_id=g.id and g.course_id=e.course_id and e.teachplan_id=f.id and e.id=d.teachplan_course_id and d.examsequence_id='"
						+ es_id
						+ "' and c.major_id=f.major_id and c.edutype_id=f.edutype_id and c.grade_id=f.grade_id minus select login_id from test_user_info a,(select distinct c.reg_no from entity_teach_class a, entity_elective b,entity_student_info c,entity_executeplan_course d,entity_teachplan_course e,entity_teachplan_info f,entity_course_active g where c.status='0' and c.isgraduated ='0' and a.id=b.teachclass_id and b.student_id=c.id and a.open_course_id='"
						+ open_course_id
						+ "' and a.open_course_id=g.id and g.course_id=e.course_id and e.teachplan_id=f.id and e.id=d.teachplan_course_id and d.examsequence_id='"
						+ es_id
						+ "' and c.major_id=f.major_id and c.edutype_id=f.edutype_id and c.grade_id=f.grade_id) b where a.login_id=b.reg_no)";
				i += db.executeUpdate(sql);
				UserAddLog.setDebug("OracleExamActivity.importStudent(String open_course_id, String course_id,String batch_id) SQL=" + sql + " DATE=" + new Date());
				sql = "insert into test_examuser_batch (id,batch_id,user_id,examcode,note,status) select to_char(s_test_examuser_batch_id.nextval),'"
						+ batch_id
						+ "',id,login_id,'','' from sso_user where login_id in (select distinct c.reg_no from entity_teach_class a, entity_elective b,entity_student_info c,entity_executeplan_course d,entity_teachplan_course e,entity_teachplan_info f,entity_course_active g where c.status='0' and c.isgraduated ='0' and a.id=b.teachclass_id and b.student_id=c.id and a.open_course_id='"
						+ open_course_id
						+ "' and a.open_course_id=g.id and g.course_id=e.course_id and e.teachplan_id=f.id and e.id=d.teachplan_course_id and d.examsequence_id='"
						+ es_id
						+ "' and c.major_id=f.major_id and c.edutype_id=f.edutype_id and c.grade_id=f.grade_id minus select c.login_id from (select distinct user_id from test_examuser_batch where batch_id='"+batch_id+"') a,sso_user c,(select distinct c.reg_no from entity_teach_class a, entity_elective b,entity_student_info c,entity_executeplan_course d,entity_teachplan_course e,entity_teachplan_info f,entity_course_active g where c.status='0' and c.isgraduated ='0' and a.id=b.teachclass_id and b.student_id=c.id and a.open_course_id='"
						+ open_course_id
						+ "' and a.open_course_id=g.id and g.course_id=e.course_id and e.teachplan_id=f.id and e.id=d.teachplan_course_id and d.examsequence_id='"
						+ es_id
						+ "' and c.major_id=f.major_id and c.edutype_id=f.edutype_id and c.grade_id=f.grade_id) b where a.user_id=c.id and c.login_id=b.reg_no and a.batch_id = '"
						+ batch_id + "')";
				i += db.executeUpdate(sql);

				sql = "delete from test_examuser_course where course_id='"
						+ String.valueOf(testCourseId) + "'";
				i += db.executeUpdate(sql);
				UserDeleteLog.setDebug("OracleExamActivity.importStudent(String open_course_id, String course_id,String batch_id) SQL=" + sql + " DATE=" + new Date());
				sql = "insert into test_examuser_course(id,user_id,course_id,status) select to_char(s_test_examuser_course.nextval),id,'"
						+ String.valueOf(testCourseId)
						+ "','0' from sso_user where login_id in (select distinct c.reg_no from entity_teach_class a, entity_elective b,entity_student_info c,entity_executeplan_course d,entity_teachplan_course e,entity_teachplan_info f,entity_course_active g where c.status='0' and c.isgraduated ='0' and a.id=b.teachclass_id and b.student_id=c.id and a.open_course_id='"
						+ open_course_id
						+ "' and a.open_course_id=g.id and g.course_id=e.course_id and e.teachplan_id=f.id and e.id=d.teachplan_course_id and d.examsequence_id='"
						+ es_id
						+ "' and c.major_id=f.major_id and c.edutype_id=f.edutype_id and c.grade_id=f.grade_id minus select c.login_id from (select user_id from test_examuser_course where course_id='"
						+ String.valueOf(testCourseId)
						+ "') a,sso_user c,(select distinct c.reg_no from entity_teach_class a, entity_elective b,entity_student_info c,entity_executeplan_course d,entity_teachplan_course e,entity_teachplan_info f,entity_course_active g where c.status='0' and c.isgraduated ='0' and a.id=b.teachclass_id and b.student_id=c.id and a.open_course_id='"
						+ open_course_id
						+ "' and a.open_course_id=g.id and g.course_id=e.course_id and e.teachplan_id=f.id and e.id=d.teachplan_course_id and d.examsequence_id='"
						+ es_id
						+ "' and c.major_id=f.major_id and c.edutype_id=f.edutype_id and c.grade_id=f.grade_id) b where a.user_id=c.id and c.login_id=b.reg_no)";
				i += db.executeUpdate(sql);
				UserAddLog.setDebug("OracleExamActivity.importStudent(String open_course_id, String course_id,String batch_id) SQL=" + sql + " DATE=" + new Date());
			}
		} catch (Exception e) {
		} finally {
			db.close(rss);
			db = null;
		}
		return i;
	}

	/**
	 * @author shu
	 * @param open_course_id
	 * @param course_id
	 * @param batch_id
	 * @param basicsequence_id
	 * @return ���γ���ӵ����Կγ��б���ȥ��
	 * @throws PlatformException
	 */
	public int importStudent(String open_course_id, String course_id,
			String batch_id,String basicsequence_id) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rss = null;
		String sql = "";
		String course_type = "";
		int i = 0;
/*	sql = "select distinct c.examsequence_id as es_id,c.exam_type as course_type from entity_course_active a,entity_teachplan_course b,entity_executeplan_course c where a.id='"
				+ open_course_id
				+ "' and a.course_id=b.course_id and b.id=c.teachplan_course_id and c.examsequence_id<>'0'";
		int i = 0;
		rss = db.executeQuery(sql);
		����ԭϵͳͨ��ִ�мƻ�4�õ����κͿ������͵ģ���ϵͳ����Ҫ�������ͣ�������Լ�4��ӵġ�es_id�ǳ��Σ�
*/		
	
	 	
		try {
//			while (rss != null && rss.next()) {
//				String es_id = rss.getString("es_id");
//				int testCourseId = addTestCourse(open_course_id, es_id,
//						course_id, batch_id, rss.getString("course_type"), "0");
//			String course_name = "";
//			while(rss!=null&&rss.next()){
//				 course_name = rss.getString("id");
//			}
			int testCourseId = addTestCourse(open_course_id, basicsequence_id,
						course_id, batch_id, course_type, "0");
				sql = "insert into test_user_info (id,name,email,ftpaccount,login_id,login_type,password) select id,name,email,'',login_id,login_type,password from sso_user where login_id in (select distinct c.reg_no from entity_teach_class a, entity_elective b,entity_student_info c,entity_teachplan_course e,entity_teachplan_info f,entity_course_active g where c.status='0' and c.isgraduated ='0' and a.id=b.teachclass_id and b.student_id=c.id and a.open_course_id='"
						+ open_course_id
						+ "' and a.open_course_id=g.id and g.course_id=e.course_id and e.teachplan_id=f.id  "
						+ " and c.major_id=f.major_id and c.edutype_id=f.edutype_id and c.grade_id=f.grade_id minus select login_id from test_user_info a,(select distinct c.reg_no from entity_teach_class a, entity_elective b,entity_student_info c,entity_teachplan_course e,entity_teachplan_info f,entity_course_active g where c.status='0' and c.isgraduated ='0' and a.id=b.teachclass_id and b.student_id=c.id and a.open_course_id='"
						+ open_course_id
						+ "'  and a.open_course_id=g.id and g.course_id=e.course_id and e.teachplan_id=f.id  "
						+ " and c.major_id=f.major_id and c.edutype_id=f.edutype_id and c.grade_id=f.grade_id) b where a.login_id=b.reg_no)";
				i += db.executeUpdate(sql);
				UserAddLog.setDebug("OracleExamActivity.importStudent(String open_course_id, String course_id,String batch_id) SQL=" + sql + " DATE=" + new Date());
				sql = "insert into test_examuser_batch (id,batch_id,user_id,examcode,note,status) select to_char(s_test_examuser_batch_id.nextval),'"
						+ batch_id
						+ "',id,login_id,'','' from sso_user where login_id in (select distinct c.reg_no from entity_teach_class a, entity_elective b,entity_student_info c,entity_teachplan_course e,entity_teachplan_info f,entity_course_active g where c.status='0' and c.isgraduated ='0' and b.mianxiu_flag = '0' and b.delay = '0' and a.id=b.teachclass_id and b.student_id=c.id and a.open_course_id='"
						+ open_course_id
						+ "' and a.open_course_id=g.id and g.course_id=e.course_id and e.teachplan_id=f.id "

						+ " and c.major_id=f.major_id and c.edutype_id=f.edutype_id and c.grade_id=f.grade_id minus select c.login_id from (select distinct user_id,batch_id from test_examuser_batch where batch_id='"+batch_id+"') a,sso_user c,(select distinct c.reg_no from entity_teach_class a, entity_elective b,entity_student_info c,entity_teachplan_course e,entity_teachplan_info f,entity_course_active g where c.status='0' and c.isgraduated ='0' and b.mianxiu_flag = '0' and b.delay = '0' and a.id=b.teachclass_id and b.student_id=c.id and a.open_course_id='"
						+ open_course_id
						+ "' and a.open_course_id=g.id and g.course_id=e.course_id and e.teachplan_id=f.id "
						
						+ " and c.major_id=f.major_id and c.edutype_id=f.edutype_id and c.grade_id=f.grade_id) b where a.user_id=c.id and c.login_id=b.reg_no and a.batch_id = '"
						+ batch_id + "')";
				i += db.executeUpdate(sql);

				sql = "delete from test_examuser_course where course_id='"
						+ String.valueOf(testCourseId) + "'";
				i += db.executeUpdate(sql);
				UserDeleteLog.setDebug("OracleExamActivity.importStudent(String open_course_id, String course_id,String batch_id) SQL=" + sql + " DATE=" + new Date());

				sql = "select id,startdate,enddate from  test_examsequence_info tei where tei.testbatch_id ='"+batch_id + "' and tei.basicsequence_id='" + basicsequence_id + "'";
				 rss = db.executeQuery(sql);
				 String test_examsequence_info = "";
				 String s_date = "";
				 String e_date = "";
					while(rss!=null&&rss.next()){
						test_examsequence_info = rss.getString("id");
						s_date = rss.getString("startdate");
						e_date = rss.getString("enddate");
						s_date = s_date.substring(0, 19);
						e_date = e_date.substring(0, 19);
					}
				
				
				
				sql = "insert into test_examcourse_info "
					+ " (id,name,s_date,e_date,test_batch_id,open_course_id,basicsequence_id,exam_type,course_type)"
				//	+ " select to_char(s_test_examcourse_info.nextval),name,'"+ s_date +"','"+ e_date+"','" //,s_date,e_date,'"
					+ " select to_char(s_test_examcourse_info.nextval),name,to_date('"+ s_date +"','yyyy-mm-dd HH24:MI:SS'),to_date('"+ e_date+"','yyyy-mm-dd HH24:MI:SS'),'"
					+ batch_id
					+"','"
					+ open_course_id
					+"','"
					+ basicsequence_id
					+"','','' from(select eci.name as name from entity_course_info eci,entity_course_active eca "
					+ "where eca.id = '"
					+ open_course_id
					+ "' and eca.course_id = eci.id)";
				i += db.executeUpdate(sql);	
				UserAddLog.setDebug("OracleExamActivity.importStudent(String open_course_id, String course_id,String batch_id) SQL=" + sql + " DATE=" + new Date());
				
				 sql = "select id from test_examcourse_info tei where tei.open_course_id=" + open_course_id ;
				 rss = db.executeQuery(sql);
				 String course_name = "";
					while(rss!=null&&rss.next()){
						 course_name = rss.getString("id");
					}
					
				sql = "insert into test_examuser_course(id,user_id,course_id,status) select to_char(s_test_examuser_course.nextval),id,'"
						//+ String.valueOf(testCourseId)
						+ course_name
						+ "','0' from sso_user where login_id in (select distinct c.reg_no from entity_teach_class a, entity_elective b,entity_student_info c,entity_teachplan_course e,entity_teachplan_info f,entity_course_active g where c.status='0' and c.isgraduated ='0' and b.mianxiu_flag = '0' and b.delay = '0' and a.id=b.teachclass_id and b.student_id=c.id and a.open_course_id='"
						+ open_course_id
						+ "' and a.open_course_id=g.id and g.course_id=e.course_id and e.teachplan_id=f.id "
		
						+ " and c.major_id=f.major_id and c.edutype_id=f.edutype_id and c.grade_id=f.grade_id minus select c.login_id from (select user_id from test_examuser_course where course_id='"
						//+ String.valueOf(testCourseId)
						+ course_name
						+ "select id from test_examcourse_info tei where tei.open_course_id=" + open_course_id + ""
						+ "') a,sso_user c,(select distinct c.reg_no from entity_teach_class a, entity_elective b,entity_student_info c,entity_teachplan_course e,entity_teachplan_info f,entity_course_active g where c.status='0' and c.isgraduated ='0' and b.mianxiu_flag = '0' and b.delay = '0' and a.id=b.teachclass_id and b.student_id=c.id and a.open_course_id='"
						+ open_course_id
						+ "' and a.open_course_id=g.id and g.course_id=e.course_id and e.teachplan_id=f.id "

						+ " and c.major_id=f.major_id and c.edutype_id=f.edutype_id and c.grade_id=f.grade_id) b where a.user_id=c.id and c.login_id=b.reg_no)";
						
				i += db.executeUpdate(sql);
				UserAddLog.setDebug("OracleExamActivity.importStudent(String open_course_id, String course_id,String batch_id) SQL=" + sql + " DATE=" + new Date());
			
//			}
		} catch (Exception e) {
		} finally {
			db.close(rss);
			db = null;
		}
		return i;
	}
	
	/**
	 * @author shu
	 * @param open_course_id
	 * @param course_id
	 * @param batch_id
	 * @param basicsequence_id
	 * @return ���γ���ӵ������γ��б���ȥ��
	 * @throws PlatformException
	 */
	public int importReStudent(String open_course_id, String course_id,
			String batch_id,String basicsequence_id) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rss = null;
		String sql = "";
		String course_type = "";
		int i = 0;
/*	sql = "select distinct c.examsequence_id as es_id,c.exam_type as course_type from entity_course_active a,entity_teachplan_course b,entity_executeplan_course c where a.id='"
				+ open_course_id
				+ "' and a.course_id=b.course_id and b.id=c.teachplan_course_id and c.examsequence_id<>'0'";
		int i = 0;
		rss = db.executeQuery(sql);
		����ԭϵͳͨ��ִ�мƻ�4�õ����κͿ������͵ģ���ϵͳ����Ҫ�������ͣ�������Լ�4��ӵġ�es_id�ǳ��Σ�
*/		
	
	 	
		try {
/*		while (rss != null && rss.next()) {
				String es_id = rss.getString("es_id");
				int testCourseId = addTestCourse(open_course_id, es_id,
						course_id, batch_id, rss.getString("course_type"), "0");
			String course_name = "";
			while(rss!=null&&rss.next()){
				 course_name = rss.getString("id");
			}
 *		 �����γ����
 * 		1.���γ�����ͬ������entity_elective��expend_score_student_status��״ֵ̬Ϊ1�Ĳ���entity_elective��teachclass_id��ֵ��entity_teach_class��idһ��
 * 			Ȼ��entity_teach_class�еĿγ����entity_course_info�������ͬ������entity_course_info �ʹ���4��һ�����Щ���ҳ�4���ŵ�expendtest_user_info�С�
 * 		2.
 * 		3����ע��
 */
			int testCourseId = addTestCourse(open_course_id, basicsequence_id,
						course_id, batch_id, course_type, "0");
		
			sql = "insert into expendtest_examuser_batch (id,batch_id,user_id,examcode,note,status) select to_char(s_expendtest_examuser_batch_id.nextval),'"
				+ batch_id
				+ "',id,login_id,'','' from sso_user where login_id in (select distinct c.reg_no from entity_teach_class a, entity_elective b,entity_student_info c,entity_teachplan_course e,entity_teachplan_info f, entity_course_info  g,entity_course_active  h  where c.status='0' and c.isgraduated ='0' and b.mianxiu_flag = '0' and b.delay = '0' and b.expend_score_student_status='1' and a.id=b.teachclass_id and b.student_id=c.id "
				+ " and  e.teachplan_id=f.id "
				+ " and c.major_id=f.major_id and c.edutype_id=f.edutype_id and c.grade_id=f.grade_id "
				+ "  and  h.id = a.open_course_id   and  h.course_id = g.id and g.id = '"+course_id +"' minus select distinct ei.login_id from expendtest_user_info ei,expendtest_examuser_batch eb  where eb.user_id = ei.id and eb.batch_id='"+batch_id+"')";

				//+ "  and  h.id = a.open_course_id   and  h.course_id = g.id and g.id = '"+course_id +"' minus select distinct login_id from expendtest_user_info ei)";
			
		i += db.executeUpdate(sql);//System.out.println("sql2->"+sql);
			
		sql ="insert into expendtest_user_info  (id, name, email, ftpaccount, login_id, login_type, password)  select id, name, email, '', login_id, login_type, password from sso_user " + 
			"  where login_id in (select distinct c.reg_no from entity_teach_class  a, entity_elective   b,  entity_student_info c, entity_teachplan_course e, entity_teachplan_info  f," + 
			" entity_course_info  g,entity_course_active  h  where c.status = '0'  and c.isgraduated = '0' and b.mianxiu_flag = '0'" + 
			"  and b.delay = '0'   and b.expend_score_student_status = '1'  and a.id = b.teachclass_id    and b.student_id = c.id" + 
			"  and e.teachplan_id = f.id  and c.major_id = f.major_id   and c.edutype_id = f.edutype_id  and c.grade_id = f.grade_id" + 
			"  and  h.id = a.open_course_id   and  h.course_id = g.id and g.id = '"+course_id +"' minus select distinct ei.login_id from expendtest_user_info ei    )";
		
				i += db.executeUpdate(sql);//System.out.println("sql1->"+sql);
				UserAddLog.setDebug("OracleExamActivity.importReStudent(String open_course_id, String course_id,String batch_id) SQL=" + sql + " DATE=" + new Date());
				

/*				sql = "delete from expendtest_examuser_course where course_id='"
						+ String.valueOf(testCourseId) + "'";
*/    	/*		sql ="delete from expendtest_examuser_course where id in(" +
					"select eteuc.id as id from expendtest_examuser_course eteuc,expendtest_examuser_batch eteub" + 
					" where eteuc.user_id = eteub.user_id" + 
					" and eteuc.course_id = '" + course_id+"'" + 
					" and eteub.batch_id = '"+batch_id+"')";
						
				i += db.executeUpdate(sql);
				UserDeleteLog.setDebug("OracleExamActivity.importReStudent(String open_course_id, String course_id,String batch_id) SQL=" + sql + " DATE=" + new Date());
 		*/
				sql = "select id,startdate,enddate from  expendtest_examsequence_info tei where tei.testbatch_id ='"+batch_id + "' and tei.basicsequence_id='" + basicsequence_id + "'";
				 rss = db.executeQuery(sql);//System.out.println("sql3->"+sql);
				 String test_examsequence_info = "";
				 String s_date = "";
				 String e_date = "";
					while(rss!=null&&rss.next()){
						test_examsequence_info = rss.getString("id");
						s_date = rss.getString("startdate");
						e_date = rss.getString("enddate");
						s_date = s_date.substring(0, 19);
						e_date = e_date.substring(0, 19);
					}
					
				sql = "insert into expendtest_examcourse_info "
					+ " (id,name,s_date,e_date,test_batch_id,course_id,basicsequence_id,exam_type,course_type)"
					+ " select to_char(s_test_examcourse_info.nextval),name,to_date('"+ s_date +"','yyyy-mm-dd HH24:MI:SS'),to_date('"+ e_date+"','yyyy-mm-dd HH24:MI:SS'),'"
					+ batch_id
					+"','"
					+ course_id
					+"','"
					+ basicsequence_id
					+"','','' from(select eci.name as name from entity_course_info eci where eci.id ='" + course_id+"')";
				i += db.executeUpdate(sql);//	System.out.println("sql4->"+sql);
				UserAddLog.setDebug("OracleExamActivity.importReStudent(String open_course_id, String course_id,String batch_id) SQL=" + sql + " DATE=" + new Date());
				
				 sql = "select id from expendtest_examcourse_info tei where tei.course_id=" + course_id +"and tei.test_batch_id='"+batch_id+"'";
				 rss = db.executeQuery(sql);// System.out.println("sql5->"+sql);
				 String course_name = "";
					while(rss!=null&&rss.next()){
						 course_name = rss.getString("id");
				}
					
				sql = "insert into expendtest_examuser_course(id,user_id,course_id,status) select to_char(s_test_examuser_course.nextval),id,'"
						//+ String.valueOf(testCourseId)
						+ course_name
						+ "','0' from sso_user where login_id in (select distinct c.reg_no from entity_teach_class a, entity_elective b,entity_student_info c,entity_teachplan_course e,entity_teachplan_info f,entity_course_info  g,entity_course_active  h  where c.status='0' and c.isgraduated ='0' and b.mianxiu_flag = '0' and (b.delay = '1' or b.expend_score_student_status='1') and a.id=b.teachclass_id and b.student_id=c.id "
						+ " and e.teachplan_id=f.id "
		
						+ " and c.major_id=f.major_id and c.edutype_id=f.edutype_id and c.grade_id=f.grade_id "
						+" and  h.id = a.open_course_id   and  h.course_id = g.id and g.id = '"+course_id +"')";
						
				i += db.executeUpdate(sql);//System.out.println("sql6->"+sql);
				UserAddLog.setDebug("OracleExamActivity.importReStudent(String open_course_id, String course_id,String batch_id) SQL=" + sql + " DATE=" + new Date());
			
//			}
		} catch (Exception e) {
		} finally {
			db.close(rss);
			db = null;
		}
		return i;
	}
	
	
	
	
	/**
	 * @author shu
	 * @param open_course_id
	 * @param course_id
	 * @param batch_id
	 * @param basicsequence_id
	 * @return ���γ���ӵ����Կγ��б���ȥ��
	 * @throws PlatformException
	 */
	public int deleteExamCourseStudent(String open_course_id, String course_id,
			String batch_id,String basicsequence_id) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rss = null;
		
		String sql = "";
		String course_type = "";
		int i = 0;

	
		try {
/*		sql1 = "insert into test_examcourse_info "
					+ " (id,name,s_date,e_date,test_batch_id,open_course_id,basicsequence_id,exam_type,course_type)"
					+ " select to_char(s_test_examcourse_info.nextval),name,'','','"
					+ batch_id
					+"','"
					+ open_course_id
					+"','"
					+ basicsequence_id
					+"','','' from(select eci.name as name from entity_course_info eci,entity_course_active eca "
					+ "where eca.id = '"
					+ open_course_id
					+ "' and eca.course_id = eci.id)";//ͨ���id�鵽�洢�ڿ��Կγ̱���id��
*/	//			 sql = "select id from expendtest_examcourse_info tei where tei.open_course_id=" + open_course_id ;
			sql = "select id as examcourse_id from expendtest_examcourse_info where test_batch_id='"+batch_id+"' and basicsequence_id='"+basicsequence_id+"' and course_id='"+course_id+"'";

					rss = db.executeQuery(sql);
					String course_id1 = "";
						while(rss!=null&&rss.next()){
							 course_id1 = rss.getString("examcourse_id");//�õ����Ǵ洢��test��ر��е�course_id.
						
				
							 String sql1 = "delete from expendtest_examuser_course where course_id='" + course_id1+"'";
							 String sql2 = "delete from expendtest_examcourse_info where id = '"+ course_id1+"'";
							 List sqll = new ArrayList(2);
							 sqll.add(sql1);sqll.add(sql2);
							 i+= db.executeUpdateBatch(sqll);
							 //i += db.executeUpdate(sql);	
				UserAddLog.setDebug("OracleExamActivity.deleteExamCourseStudent(String open_course_id, String course_id,String batch_id) SQL=" + sql + " DATE=" + new Date());
						}
		} catch (Exception e) {
		} finally {
			db.close(rss);
			db = null;
		}
		return i;
	}
	/**
	 * @author shu
	 * @param stu_reg
	 * @param open_course_id
	 * @param course_id
	 * @param batch_id
	 * @param basicsequence_id
	 * @return ���ѧ��reg_no4��ѧ����Ϊ���������ӿ��Ա��еĳ�ȥ��
	 * @throws PlatformException
	 */
	public int delayStudent(String stu_reg,String open_course_id, String course_id,
			String batch_id,String basicsequence_id) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rss = null;
		
	
		
		String course_type = "";
		int i = 0;

	
		try {
/*		sql1 = "insert into test_examcourse_info "
					+ " (id,name,s_date,e_date,test_batch_id,open_course_id,basicsequence_id,exam_type,course_type)"
					+ " select to_char(s_test_examcourse_info.nextval),name,'','','"
					+ batch_id
					+"','"
					+ open_course_id
					+"','"
					+ basicsequence_id
					+"','','' from(select eci.name as name from entity_course_info eci,entity_course_active eca "
					+ "where eca.id = '"
					+ open_course_id
					+ "' and eca.course_id = eci.id)";
					1.��test_examuser_batch��ȥuser
					2����test_examuser_courseuser
					3.��test_user_info��ȥ
					4.��entity_elective�е�ͨ��reg_no��j��44�ѻ���״̬��Ϊ����������ͨ��opencourseid4��j��
*/
			String  sql = "select id from expendtest_user_info tui where tui.login_id='" + stu_reg +"'";
			 rss = db.executeQuery(sql);
			 String user_id = "";//����test_user_info���е�user_id
				while(rss!=null&&rss.next()){
					user_id = rss.getString("id");
				}

//				String sql1 = "delete from expendtest_examuser_course where user_id=" + user_id;
				String sql1 = 
					"delete from expendtest_examuser_course  where id in (select t.id from expendtest_examuser_course t,expendtest_examcourse_info te" +
					" where t.user_id = '"+user_id+"' and te.course_id = '"+open_course_id+"' and t.course_id = te.id)";

//				String sql2 = "delete from expendtest_examuser_batch where user_id=" + user_id;
				//sql = "delete from test_user_info where login_id = '"+ stu_reg+"'";
				 List sqll=new ArrayList(3);
				 sqll.add(sql1);
				 //sqll.add(sql2);
			//	 sqll.add(sql);
				//i += db.executeUpdate(sql);
				 i = db.executeUpdateBatch(sqll);
				UserAddLog.setDebug("OracleReExamActivity.delayStudent(String open_course_id, String course_id,String batch_id) SQL=" + sql + " DATE=" + new Date());
		} catch (Exception e) {
		} finally {
			db.close(rss);
			db = null;
		}
		return i;
	}
	
	
	public int importStudentExpend(String open_course_id, String course_id,
			String batch_id) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rss = null;
		String sql = "";
		sql = "select distinct c.examsequence_id as es_id,c.exam_type from entity_course_active a,entity_teachplan_course b,entity_executeplan_course c where a.id='"
				+ open_course_id
				+ "' and a.course_id=b.course_id and b.id=c.teachplan_course_id and c.examsequence_id<>'0'";
		int i = 0;
		rss = db.executeQuery(sql);
		try {
			while (rss != null && rss.next()) {
				String es_id = rss.getString("es_id");
				int testCourseId = addTestCourse(open_course_id, es_id,
						course_id, batch_id, rss.getString("exam_type"), "1");
				sql = "insert into test_user_info (id,name,email,ftpaccount,login_id,login_type,password) select id,name,email,'',login_id,login_type,password from sso_user where login_id in (select distinct c.reg_no from entity_teach_class a, entity_elective b,entity_student_info c,entity_executeplan_course d,entity_teachplan_course e,entity_teachplan_info f,entity_course_active g where c.status='0' and c.isgraduated ='0' and a.id=b.teachclass_id and b.student_id=c.id and a.open_course_id='"
						+ open_course_id
						+ "' and a.open_course_id=g.id and g.course_id=e.course_id and e.teachplan_id=f.id and e.id=d.teachplan_course_id and d.examsequence_id='"
						+ es_id
						+ "' and c.major_id=f.major_id and c.edutype_id=f.edutype_id and c.grade_id=f.grade_id and b.expend_score_student_status = '1' minus select login_id from test_user_info a,(select distinct c.reg_no from entity_teach_class a, entity_elective b,entity_student_info c,entity_executeplan_course d,entity_teachplan_course e,entity_teachplan_info f,entity_course_active g where c.status='0' and c.isgraduated ='0' and a.id=b.teachclass_id and b.student_id=c.id and a.open_course_id='"
						+ open_course_id
						+ "' and a.open_course_id=g.id and g.course_id=e.course_id and e.teachplan_id=f.id and e.id=d.teachplan_course_id and d.examsequence_id='"
						+ es_id
						+ "' and c.major_id=f.major_id and c.edutype_id=f.edutype_id and c.grade_id=f.grade_id and b.expend_score_student_status = '1') b where a.login_id=b.reg_no)";
				i += db.executeUpdate(sql);
				
				UserAddLog.setDebug("OracleExamActivity.importStudentExpend(String open_course_id, String course_id,String batch_id) SQL=" + sql + " DATE=" + new Date());
				
				sql = "insert into test_examuser_batch (id,batch_id,user_id,examcode,note,status) select to_char(s_test_examuser_batch_id.nextval),'"
						+ batch_id
						+ "',id,login_id,'','' from sso_user where login_id in (select distinct c.reg_no from entity_teach_class a, entity_elective b,entity_student_info c,entity_executeplan_course d,entity_teachplan_course e,entity_teachplan_info f,entity_course_active g where c.status='0' and c.isgraduated ='0' and a.id=b.teachclass_id and b.student_id=c.id and a.open_course_id='"
						+ open_course_id
						+ "' and a.open_course_id=g.id and g.course_id=e.course_id and e.teachplan_id=f.id and e.id=d.teachplan_course_id and d.examsequence_id='"
						+ es_id
						+ "' and c.major_id=f.major_id and c.edutype_id=f.edutype_id and c.grade_id=f.grade_id and b.expend_score_student_status = '1' minus select c.login_id from (select distinct user_id from test_examuser_batch where batch_id='"+batch_id+"') a,sso_user c,(select distinct c.reg_no from entity_teach_class a, entity_elective b,entity_student_info c,entity_executeplan_course d,entity_teachplan_course e,entity_teachplan_info f,entity_course_active g where c.status='0' and c.isgraduated ='0' and a.id=b.teachclass_id and b.student_id=c.id and a.open_course_id='"
						+ open_course_id
						+ "' and a.open_course_id=g.id and g.course_id=e.course_id and e.teachplan_id=f.id and e.id=d.teachplan_course_id and d.examsequence_id='"
						+ es_id
						+ "' and c.major_id=f.major_id and c.edutype_id=f.edutype_id and c.grade_id=f.grade_id and b.expend_score_student_status = '1') b where a.user_id=c.id and c.login_id=b.reg_no)";
				i += db.executeUpdate(sql);

				UserAddLog.setDebug("OracleExamActivity.importStudentExpend(String open_course_id, String course_id,String batch_id) SQL=" + sql + " DATE=" + new Date());
				
				sql = "delete from test_examuser_course where course_id='"
						+ String.valueOf(testCourseId) + "'";
				i += db.executeUpdate(sql);

				UserDeleteLog.setDebug("OracleExamActivity.importStudentExpend(String open_course_id, String course_id,String batch_id) SQL=" + sql + " DATE=" + new Date());
				
				sql = "insert into test_examuser_course(id,user_id,course_id,status) select to_char(s_test_examuser_course.nextval),id,'"
						+ String.valueOf(testCourseId)
						+ "','0' from sso_user where login_id in (select distinct c.reg_no from entity_teach_class a, entity_elective b,entity_student_info c,entity_executeplan_course d,entity_teachplan_course e,entity_teachplan_info f,entity_course_active g where c.status='0' and c.isgraduated ='0' and a.id=b.teachclass_id and b.student_id=c.id and a.open_course_id='"
						+ open_course_id
						+ "' and a.open_course_id=g.id and g.course_id=e.course_id and e.teachplan_id=f.id and e.id=d.teachplan_course_id and d.examsequence_id='"
						+ es_id
						+ "' and c.major_id=f.major_id and c.edutype_id=f.edutype_id and c.grade_id=f.grade_id and b.expend_score_student_status = '1' minus select c.login_id from (select user_id from test_examuser_course where course_id='"
						+ String.valueOf(testCourseId)
						+ "') a,sso_user c,(select distinct c.reg_no from entity_teach_class a, entity_elective b,entity_student_info c,entity_executeplan_course d,entity_teachplan_course e,entity_teachplan_info f,entity_course_active g where c.status='0' and c.isgraduated ='0' and a.id=b.teachclass_id and b.student_id=c.id and a.open_course_id='"
						+ open_course_id
						+ "' and a.open_course_id=g.id and g.course_id=e.course_id and e.teachplan_id=f.id and e.id=d.teachplan_course_id and d.examsequence_id='"
						+ es_id
						+ "' and c.major_id=f.major_id and c.edutype_id=f.edutype_id and c.grade_id=f.grade_id and b.expend_score_student_status = '1') b where a.user_id=c.id and c.login_id=b.reg_no)";
				i += db.executeUpdate(sql);
				
				UserAddLog.setDebug("OracleExamActivity.importStudentExpend(String open_course_id, String course_id,String batch_id) SQL=" + sql + " DATE=" + new Date());
				
			}
		} catch (Exception e) {
		} finally {
			db.close(rss);
			db = null;
		}
		return i;
	}

	public int setStudent(String course_id, String batch_id)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs1 = null;
		MyResultSet rs3 = null;
		MyResultSet rsuser = null;
		String sqluser = "";
		String sqlroom = "";
		String sql0 = "";
		String sql1 = "";
		String sql3 = "";
		String roomId = "";
		String address = "";
		int capacity = 0;
		int count = 1;

		sql0 = "update test_examuser_course  set room_id='',desk_no='' where course_id='"
				+ course_id
				+ "' and user_id in (select user_id from test_examuser_batch where batch_id='"
				+ batch_id + "')";
		db.executeUpdate(sql0);
		
		UserUpdateLog.setDebug("OracleExamActivity.setStudent(String course_id, String batch_id) SQL=" + sql0 + " DATE=" + new Date());
		sqlroom = "update test_examroom_info  set room_no='' where course_id='"
				+ course_id + "'";
		EntityLog.setDebug(sqlroom);
		db.executeUpdate(sqlroom);

		sql1 = "select id,address,room_no,room_num from test_examroom_info where course_id='"
				+ course_id + "' order by id";
		EntityLog.setDebug(sql1);
		try {
			rs1 = db.executeQuery(sql1);
			if (rs1 != null && rs1.next()) {
				roomId = rs1.getString("id");
				capacity = rs1.getInt("room_num");
			}
			sqluser = "select a.id as id from test_examuser_course a,test_examuser_batch b where a.user_id=b.user_id and b.batch_id='"
					+ batch_id + "' and  a.course_id='" + course_id + "'";
			rsuser = db.executeQuery(sqluser);
			int DESK_NO = 1;
			while (rsuser != null && rsuser.next()) {
				String id = rsuser.getString("id");
				String exesql = "";
				if (DESK_NO <= capacity) {
					exesql = "update test_examuser_course set room_id='"
							+ roomId + "',desk_no='" + DESK_NO + "' where id='"
							+ id + "'";
					db.executeUpdate(exesql);
				} else {
					DESK_NO = 1;
					if (rs1.next()) {
						roomId = rs1.getString("id");
						capacity = rs1.getInt("room_num");
					}
					exesql = "update test_examuser_course set room_id='"
							+ roomId + "',desk_no='" + DESK_NO + "' where id='"
							+ id + "'";
					db.executeUpdate(exesql);
				}
				UserUpdateLog.setDebug("OracleExamActivity.setStudent(String course_id, String batch_id) SQL=" + exesql + " DATE=" + new Date());
				DESK_NO++;

			}
			db.close(rsuser);
			sql3 = "select id,address from test_examroom_info order by address";
			rs3 = db.executeQuery(sql3);
			String preaddress = "";
			int ROOM_NO = 0;
			while (rs3 != null && rs3.next()) {
				address = rs3.getString("address");
				if (!preaddress.equals(address)) {
					ROOM_NO++;
				}
				db.executeUpdate("update test_examroom_info set room_no='"
						+ ROOM_NO + "' where id='" + rs3.getString("id") + "'");
				preaddress = address;
			}

		} catch (Exception e) {
			
		} finally {
			db.close(rs1);
			db.close(rs3);
			db.close(rsuser);
			db = null;
		}
		return count;
	}

	public int setSiteStudent(String site_id, String course_id, String batch_id)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs1 = null;
		MyResultSet rs3 = null;
		MyResultSet rsuser = null;
		String sqluser = "";
		String sqlroom = "";
		String sql0 = "";
		String sql1 = "";
		String sql3 = "";
		String roomId = "";
		String address = "";
		int capacity = 0;
		int count = 1;

		sql0 = "update test_examuser_course  set room_id='',desk_no='' where course_id='"
				+ course_id
				+ "' and user_id in (select user_id from test_examuser_batch where batch_id='"
				+ batch_id
				+ "' and user_id in (select id from entity_student_info where site_id = '"
				+ site_id + "'))";
		db.executeUpdate(sql0);
		UserUpdateLog.setDebug("OracleExamActivity.setSiteStudent(String site_id, String course_id, String batch_id) SQL=" + sql0 + " DATE=" + new Date());
		sqlroom = "update test_examroom_info  set room_no='' where course_id='"
				+ course_id + "' and site_id = '" + site_id + "'";
		db.executeUpdate(sqlroom);
		UserUpdateLog.setDebug("OracleExamActivity.setSiteStudent(String site_id, String course_id, String batch_id) SQL=" + sqlroom + " DATE=" + new Date());
		sql1 = "select id,address,room_no,room_num from test_examroom_info where course_id='"
				+ course_id + "' and site_id = '" + site_id + "' order by id";
		EntityLog.setDebug(sql1);
		try {
			rs1 = db.executeQuery(sql1);
			if (rs1 != null && rs1.next()) {
				roomId = rs1.getString("id");
				capacity = rs1.getInt("room_num");
			}
			sqluser = "select a.id as id from test_examuser_course a,test_examuser_batch b,entity_student_info c where a.user_id=b.user_id and b.batch_id='"
					+ batch_id
					+ "' and  a.course_id='"
					+ course_id
					+ "' and b.user_id in (select id from entity_student_info where site_id = '"
					+ site_id
					+ "') and a.user_id=c.id order by c.grade_id,c.major_id,reg_no";
			rsuser = db.executeQuery(sqluser);
			int DESK_NO = 1;
			while (rsuser != null && rsuser.next()) {
				String id = rsuser.getString("id");
				String exesql = "";
				if (DESK_NO <= capacity) {
					exesql = "update test_examuser_course set room_id='"
							+ roomId + "',desk_no='" + DESK_NO + "' where id='"
							+ id + "'";
					db.executeUpdate(exesql);
				} else {
					DESK_NO = 1;
					if (rs1.next()) {
						roomId = rs1.getString("id");
						capacity = rs1.getInt("room_num");
					}
					exesql = "update test_examuser_course set room_id='"
							+ roomId + "',desk_no='" + DESK_NO + "' where id='"
							+ id + "'";
					db.executeUpdate(exesql);
				}
				DESK_NO++;
				UserUpdateLog.setDebug("OracleExamActivity.setSiteStudent(String site_id, String course_id, String batch_id) SQL=" + exesql + " DATE=" + new Date());

			}
			db.close(rsuser);
			sql3 = "select id,address from test_examroom_info order by address";
			rs3 = db.executeQuery(sql3);
			String preaddress = "";
			int ROOM_NO = 0;
			while (rs3 != null && rs3.next()) {
				address = rs3.getString("address");
				if (!preaddress.equals(address)) {
					ROOM_NO++;
				}
				String tmpsql = "update test_examroom_info set room_no='" + ROOM_NO + "' where id='" + rs3.getString("id") + "'";
				db.executeUpdate(tmpsql);
				UserUpdateLog.setDebug("OracleExamActivity.setSiteStudent(String site_id, String course_id, String batch_id) SQL=" + tmpsql + " DATE=" + new Date());
				preaddress = address;
			}

		} catch (Exception e) {
			
		} finally {
			db.close(rs1);
			db.close(rs3);
			db.close(rsuser);
			db = null;
		}
		return count;
	}

	public int getTestCoursesNum(HttpServletRequest request)
			throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		List searchProperties = new ArrayList();
		List orderProperties = new ArrayList();
		MyResultSet rs = null;
		if (request.getParameter("exambatchid") != null
				&& !request.getParameter("exambatchid").equals(""))
			searchProperties.add(new SearchProperty("test_batch_id", request
					.getParameter("exambatchid"), "="));
		if (request.getParameter("examsequence_id") != null
				&& !request.getParameter("examsequence_id").equals(""))
			searchProperties.add(new SearchProperty("examsequence_id", request
					.getParameter("examsequence_id"), "="));
		if (request.getParameter("examcourse_id") != null
				&& !request.getParameter("examcourse_id").equals(""))
			searchProperties.add(new SearchProperty("course_id", request
					.getParameter("examcourse_id"), "="));
		orderProperties.add(new OrderProperty("test_batch_id"));
		orderProperties.add(new OrderProperty("examsequence_id"));
		orderProperties.add(new OrderProperty("course_id"));
		String site_id = request.getParameter("site_id");
		if (site_id != null && !site_id.equals(""))
			sql = "select course_id,course_name,num,test_batch_id,examsequence_id,c_id,sequence_name from (select c.course_id,d.name as course_name,c.num,d.test_batch_id,d.examsequence_id,d.course_id as c_id,tei.title as sequence_name from (select count(a.id) as num,a.course_id from test_examuser_course a,test_examcourse_info b,(select id from entity_student_info where site_id='"
					+ site_id
					+ "') e where a.course_id=b.id and a.user_id=e.id group by a.course_id) c,test_examcourse_info d,test_examsequence_info tei where c.course_id=d.id and d.examsequence_id=tei.id)"
					+ Conditions.convertToCondition(searchProperties,
							orderProperties);
		else
			sql = "select course_id,course_name,num,test_batch_id,examsequence_id,c_id,sequence_name from (select c.course_id,d.name as course_name,c.num,d.test_batch_id,d.examsequence_id,d.course_id as c_id,tei.title as sequence_name from (select count(a.id) as num,a.course_id from test_examuser_course a,test_examcourse_info b where a.course_id=b.id group by a.course_id) c,test_examcourse_info d,test_examsequence_info tei where c.course_id=d.id and d.examsequence_id=tei.id)"
					+ Conditions.convertToCondition(searchProperties,
							orderProperties);
		int i = 0;
		i = db.countselect(sql);
		return i;
	}

	public List getTestCourses(Page page, HttpServletRequest request)
			throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		List searchProperties = new ArrayList();
		List orderProperties = new ArrayList();
		MyResultSet rs = null;
		if (request.getParameter("exambatchid") != null
				&& !request.getParameter("exambatchid").equals(""))
			searchProperties.add(new SearchProperty("test_batch_id", request
					.getParameter("exambatchid"), "="));
		if (request.getParameter("examsequence_id") != null
				&& !request.getParameter("examsequence_id").equals(""))
			searchProperties.add(new SearchProperty("examsequence_id", request
					.getParameter("examsequence_id"), "="));
		if (request.getParameter("examcourse_id") != null
				&& !request.getParameter("examcourse_id").equals(""))
			searchProperties.add(new SearchProperty("course_id", request
					.getParameter("examcourse_id"), "="));
		orderProperties.add(new OrderProperty("test_batch_id"));
		orderProperties.add(new OrderProperty("examsequence_id"));
		orderProperties.add(new OrderProperty("course_id"));
		String site_id = request.getParameter("site_id");
		if (site_id != null && !site_id.equals(""))
			sql = "select course_id,course_name,num,test_batch_id,examsequence_id,c_id,sequence_name from (select c.course_id,d.name as course_name,c.num,d.test_batch_id,d.examsequence_id,d.course_id as c_id,tei.title as sequence_name from (select count(a.id) as num,a.course_id from test_examuser_course a,test_examcourse_info b,(select id from entity_student_info where site_id='"
					+ site_id
					+ "') e where a.course_id=b.id and a.user_id=e.id group by a.course_id) c,test_examcourse_info d,test_examsequence_info tei where c.course_id=d.id and d.examsequence_id=tei.id)"
					+ Conditions.convertToCondition(searchProperties,
							orderProperties);
		else
			sql = "select course_id,course_name,num,test_batch_id,examsequence_id,c_id,sequence_name from (select c.course_id,d.name as course_name,c.num,d.test_batch_id,d.examsequence_id,d.course_id as c_id,tei.title as sequence_name from (select count(a.id) as num,a.course_id from test_examuser_course a,test_examcourse_info b where a.course_id=b.id group by a.course_id) c,test_examcourse_info d,test_examsequence_info tei where c.course_id=d.id and d.examsequence_id=tei.id)"
					+ Conditions.convertToCondition(searchProperties,
							orderProperties);

		List testCourses = new ArrayList();
		try {
			db = new dbpool();

			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {

				HashMap course = new HashMap();
				course.put("course_id", rs.getString("course_id"));
				course.put("course_name", rs.getString("course_name"));
				course.put("num", rs.getString("num"));
				course.put("test_batch_id", rs.getString("test_batch_id"));
				course.put("examsequence_id", rs.getString("examsequence_id"));
				course.put("c_id", rs.getString("c_id"));
				course.put("sequence_name", rs.getString("sequence_name"));
				testCourses.add(course);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return testCourses;
	}

	public int getSiteTestCoursesNum(HttpServletRequest request)
			throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		List searchProperties = new ArrayList();
		List orderProperties = new ArrayList();
		MyResultSet rs = null;
		if (request.getParameter("exambatchid") != null
				&& !request.getParameter("exambatchid").equals(""))
			searchProperties.add(new SearchProperty("test_batch_id", request
					.getParameter("exambatchid"), "="));
		if (request.getParameter("examsequence_id") != null
				&& !request.getParameter("examsequence_id").equals(""))
			searchProperties.add(new SearchProperty("examsequence_id", request
					.getParameter("examsequence_id"), "="));
		if (request.getParameter("examcourse_id") != null
				&& !request.getParameter("examcourse_id").equals(""))
			searchProperties.add(new SearchProperty("course_id", request
					.getParameter("examcourse_id"), "="));
		orderProperties.add(new OrderProperty("test_batch_id"));
		orderProperties.add(new OrderProperty("examsequence_id"));
		orderProperties.add(new OrderProperty("course_id"));
		String site_id = request.getParameter("site_id");
		if (site_id != null && !site_id.equals(""))
			sql = "select course_id,course_name,num,test_batch_id,examsequence_id,site_id,site_name from (select c.course_id,d.name as course_name,c.num,d.test_batch_id,d.examsequence_id,c.site_id,f.name as site_name from (select count(a.id) as num,a.course_id,e.site_id from test_examuser_course a,test_examcourse_info b,(select id,site_id from entity_student_info where site_id='"
					+ site_id
					+ "') e where a.course_id=b.id and a.user_id=e.id group by a.course_id,e.site_id) c,test_examcourse_info d,entity_site_info f where c.course_id=d.id and c.site_id=f.id)"
					+ Conditions.convertToCondition(searchProperties,
							orderProperties);
		else
			sql = "select course_id,course_name,num,test_batch_id,examsequence_id,site_id,site_name from (select c.course_id,d.name as course_name,c.num,d.test_batch_id,d.examsequence_id,c.site_id,f.name as site_name from (select count(a.id) as num,a.course_id,e.site_id from test_examuser_course a,test_examcourse_info b,(select id,site_id from entity_student_info) e where a.course_id=b.id and a.user_id=e.id group by a.course_id,e.site_id) c,test_examcourse_info d,entity_site_info f where c.course_id=d.id and c.site_id=f.id)"
					+ Conditions.convertToCondition(searchProperties,
							orderProperties);
		int i = 0;
		i = db.countselect(sql);
		return i;
	}

	public List getSiteTestCourses(Page page, HttpServletRequest request)
			throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		List searchProperties = new ArrayList();
		List orderProperties = new ArrayList();
		MyResultSet rs = null;
		if (request.getParameter("exambatchid") != null
				&& !request.getParameter("exambatchid").equals(""))
			searchProperties.add(new SearchProperty("test_batch_id", request
					.getParameter("exambatchid"), "="));
		if (request.getParameter("examsequence_id") != null
				&& !request.getParameter("examsequence_id").equals(""))
			searchProperties.add(new SearchProperty("examsequence_id", request
					.getParameter("examsequence_id"), "="));
		if (request.getParameter("examcourse_id") != null
				&& !request.getParameter("examcourse_id").equals(""))
			searchProperties.add(new SearchProperty("course_id", request
					.getParameter("examcourse_id"), "="));
		orderProperties.add(new OrderProperty("test_batch_id"));
		orderProperties.add(new OrderProperty("examsequence_id"));
		orderProperties.add(new OrderProperty("course_id"));
		orderProperties.add(new OrderProperty("site_id"));
		String site_id = request.getParameter("site_id");
		if (site_id != null && !site_id.equals(""))
			sql = "select course_id,course_name,num,test_batch_id,examsequence_id,site_id,site_name from (select c.course_id,d.name as course_name,c.num,d.test_batch_id,d.examsequence_id,c.site_id,f.name as site_name from (select count(a.id) as num,a.course_id,e.site_id from test_examuser_course a,test_examcourse_info b,(select id,site_id from entity_student_info where site_id='"
					+ site_id
					+ "') e where a.course_id=b.id and a.user_id=e.id group by a.course_id,e.site_id) c,test_examcourse_info d,entity_site_info f where c.course_id=d.id and c.site_id=f.id)"
					+ Conditions.convertToCondition(searchProperties,
							orderProperties);
		else
			sql = "select course_id,course_name,num,test_batch_id,examsequence_id,site_id,site_name from (select c.course_id,d.name as course_name,c.num,d.test_batch_id,d.examsequence_id,c.site_id,f.name as site_name from (select count(a.id) as num,a.course_id,e.site_id from test_examuser_course a,test_examcourse_info b,(select id,site_id from entity_student_info) e where a.course_id=b.id and a.user_id=e.id group by a.course_id,e.site_id) c,test_examcourse_info d,entity_site_info f where c.course_id=d.id and c.site_id=f.id)"
					+ Conditions.convertToCondition(searchProperties,
							orderProperties);

		List testCourses = new ArrayList();
		try {
			db = new dbpool();

			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {

				HashMap course = new HashMap();
				course.put("course_id", rs.getString("course_id"));
				course.put("course_name", rs.getString("course_name"));
				course.put("num", rs.getString("num"));
				course.put("test_batch_id", rs.getString("test_batch_id"));
				course.put("examsequence_id", rs.getString("examsequence_id"));
				course.put("site_id", rs.getString("site_id"));
				course.put("site_name", rs.getString("site_name"));
				testCourses.add(course);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return testCourses;
	}

	public int searchOpenCourseNum(List searchproperty, String batch_id)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select distinct course_id,opencourse_id,course_name,semester_id,semester_name,exam_type from (select a.id as course_id,b.id as opencourse_id,a.name as course_name,c.id as semester_id,c.name as semester_name,nvl(d.exam_type,'9') as exam_type from entity_course_info a,entity_course_active b,entity_semester_info c, (select course_id,exam_type from test_examcourse_info where test_batch_id='"
				+ batch_id
				+ "') d where a.id=b.course_id and b.semester_id=c.id and a.id=d.course_id(+))"
				+ Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public int searchReCourseNum(List searchproperty, String batch_id,String basicsequence_id)
	throws PlatformException {
		dbpool db = new dbpool();
		
		String con = Conditions.convertToCondition(searchproperty, null);
		String con2 = "";
		if(!"".equals(con)){
			con2 = " and" + con.substring(6);
		}
		 
		String sql ="select distinct a.id as course_id, a.name as course_name,a.credit  as credit, a.course_time as course_time" +
			"  from (select id, name, credit, course_time from entity_course_info) a," + 
			" (select eca.id  as id, eca.course_id   as course_id from entity_course_active eca" + 
			"   where eca.course_id not in (select course_id from expendtest_examcourse_info t where t.test_batch_id='"+batch_id +"' )) d" + 
			"  where a.id = d.course_id " + con2;

		
/*		String sql = "select a.id as course_id,a.name as course_name, c.id as semester_id,c.name as semester_name,a.credit as credit ,a.course_time as course_time"
			+ " from (select id ,name,credit, course_time from entity_course_info) a,entity_semester_info c,( select eca.id as id,eca.course_id as course_id,eca.semester_id as semester_id from entity_course_active eca where eca.id not in(select open_course_id from test_examcourse_info ) ) d"
			+ " where a.id = d.course_id and d.semester_id = c.id"
			+ Conditions.convertToCondition(searchproperty, null);
		
		String sql = "select distinct course_id,opencourse_id,course_name,semester_id,semester_name,exam_type from (select a.id as course_id,b.id as opencourse_id,a.name as course_name,c.id as semester_id,c.name as semester_name,nvl(d.exam_type,'9') as exam_type from entity_course_info a,entity_course_active b,entity_semester_info c, (select course_id,exam_type from expendtest_examcourse_info where test_batch_id='"
		+ batch_id
		+ "') d where a.id=b.course_id and b.semester_id=c.id and a.id=d.course_id(+))"
		+ Conditions.convertToCondition(searchproperty, null);
*/		
		return db.countselect(sql);
}

	
	/**
	 * @author shu
	 * @param major_id
	 * @param semesterId
	 * @param course_id
	 * @param course_name
	 * @param batch_id
	 * @param examsequence_id
	 * @return �õ��Ѿ����뵽�����б�Ŀγ̵���Ŀ��
	 * @throws PlatformException
	 */
	public int searchExamCourseNum(List searchproperty, String batch_id,String basicsequence_id)
	throws PlatformException {
		dbpool db = new dbpool();
/*		String sql = "select distinct course_id,opencourse_id,course_name,semester_id,semester_name,credit,course_time,exam_type from (select a.id as course_id,b.id as opencourse_id,a.name as course_name,c.id as semester_id, a.credit as credit ,a.course_time as course_time,c.name as semester_name,nvl(d.exam_type,'9') as exam_type from (select id ,name,credit, course_time from entity_course_info) a,entity_course_active b,entity_semester_info c, (select open_course_id,exam_type,basicsequence_id  from test_examcourse_info where test_batch_id='"
			+ batch_id + "' and basicsequence_id= '" + basicsequence_id  
			+ "') d ,(select id from test_basicsequence_info  ) tbs,(select id,basicsequence_id from test_examsequence_info) tes where a.id=b.course_id and b.semester_id=c.id and b.id=d.open_course_id and tes.basicsequence_id = tbs.id and tes.id = d.basicsequence_id )"
*/
		String sql = "select distinct examcourse_id,course_id,course_name, credit, course_time,exam_type" +
		"  from (select a.id as course_id,a.name as course_name, a.credit as credit,a.course_time as course_time,d.id as examcourse_id," + 
		"               nvl(d.exam_type, '9') as exam_type" + 
		"          from (select id, name, credit, course_time from entity_course_info) a," + 
		"               (select t.id, t.course_id, t.exam_type, t.basicsequence_id" + 
		"                  from expendtest_examcourse_info t,test_basicsequence_info tbs" + 
		"                 where test_batch_id = '"+ batch_id + "' and basicsequence_id= '" + basicsequence_id+"' and tbs.id = t.basicsequence_id ) d," + 
		"                (select t.id, t.basicsequence_id from expendtest_examsequence_info t,test_basicsequence_info tbs where tbs.id = t.basicsequence_id) tes" + 
		"         where  a.id = d.course_id)"			
			+ Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
}

	/**
	 * @author shu
	 * @param major_id
	 * @param semesterId
	 * @param course_id
	 * @param course_name
	 * @param batch_id
	 * @param examsequence_id
	 * @return �õ���û�м��뵽�����б�ģ�����ӵ������б�ģ��γ̵���Ŀ��
	 * @throws PlatformException
	 */
	public int searchNotExamCourseNum(List searchproperty, String batch_id,String basicsequence_id)
	throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select a.id as course_id,d.id as opencourse_id,a.name as course_name, c.id as semester_id,c.name as semester_name,a.credit as credit ,a.course_time as course_time"
			+ " from (select id ,name,credit, course_time from entity_course_info) a,entity_semester_info c,( select eca.id as id,eca.course_id as course_id,eca.semester_id as semester_id from entity_course_active eca where eca.id not in(select open_course_id from test_examcourse_info ) ) d"
			+ " where a.id = d.course_id and d.semester_id = c.id"
			+ Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
}
	
	
	public List searchOpenCourse(Page page, List searchproperty,
			List orderproperty, String batch_id) throws PlatformException {
		dbpool db = null;
		String sql = "select distinct course_id,opencourse_id,course_name,semester_id,semester_name,exam_type from (select a.id as course_id,b.id as opencourse_id,a.name as course_name,c.id as semester_id,c.name as semester_name,nvl(d.exam_type,'9') as exam_type from entity_course_info a,entity_course_active b,entity_semester_info c, (select course_id,exam_type from test_examcourse_info where test_batch_id='"
				+ batch_id
				+ "') d where a.id=b.course_id and b.semester_id=c.id and a.id=d.course_id(+))"
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
				List item = new ArrayList();
				item.add(rs.getString("course_id"));
				item.add(rs.getString("opencourse_id"));
				item.add(rs.getString("course_name"));
				item.add(rs.getString("semester_id"));
				item.add(rs.getString("semester_name"));
				item.add(rs.getString("exam_type"));
				list.add(item);
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
	 * @param major_id
	 * @param semesterId
	 * @param course_id
	 * @param course_name
	 * @param batch_id
	 * @param examsequence_id
	 * @return �õ��Ѿ����뵽�����б�Ŀγ̵���Ϣ��
	 * @throws PlatformException ����ǻ��ģ�����ʹ��
	 */
	public List searchExamCourse(Page page, List searchproperty,
			List orderproperty, String batch_id,String basicsequence_id) throws PlatformException {
		dbpool db = null;
		/*String sql = "select distinct course_id,examcourse_id,opencourse_id,course_name,semester_id,semester_name,credit,course_time,exam_type from (select d.id as examcourse_id,a.id as course_id,b.id as opencourse_id,a.name as course_name,c.id as semester_id, a.credit as credit ,a.course_time as course_time,c.name as semester_name,nvl(d.exam_type,'9') as exam_type from (select id ,name,credit, course_time from entity_course_info) a,entity_course_active b,entity_semester_info c, (select id,open_course_id,exam_type,test_batch_id,basicsequence_id  from test_examcourse_info where test_batch_id='"
			+ batch_id + "' and basicsequence_id= '" + basicsequence_id  
			+ "') d ,(select id from test_basicsequence_info  ) tbs,(select id,basicsequence_id from test_examsequence_info) tes where a.id=b.course_id and b.semester_id=c.id and b.id=d.open_course_id and tes.basicsequence_id = tbs.id and tbs.id = d.basicsequence_id )"
			*/
		String sql  = "select distinct course_id,examcourse_id,opencourse_id,course_name,semester_id,semester_name,credit,course_time,exam_type from (select d.id as examcourse_id,a.id as course_id,b.id as opencourse_id,a.name as course_name,c.id as semester_id, a.credit as credit ,a.course_time as course_time,c.name as semester_name,nvl(d.exam_type,'9') as exam_type from (select id ,name,credit, course_time from entity_course_info) a,entity_course_active b,entity_semester_info c, (select t.id,t.open_course_id,t.exam_type,t.test_batch_id,t.basicsequence_id  from test_examcourse_info t,test_basicsequence_info tbs where test_batch_id='"
			+ batch_id + "' and basicsequence_id= '" + basicsequence_id  
			+ "' and t.basicsequence_id = tbs.id) d ,(select t.id,t.basicsequence_id from test_examsequence_info t,test_basicsequence_info tbs where t.basicsequence_id = tbs.id) tes where a.id=b.course_id and b.semester_id=c.id and b.id=d.open_course_id)"// and tes.basicsequence_id = tbs.id and tbs.id = d.basicsequence_id )"//tbs.id = d.basicsequence_id )"
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
				List item = new ArrayList();
				item.add(rs.getString("course_id"));
				item.add(rs.getString("opencourse_id"));
				item.add(rs.getString("course_name"));
				item.add(rs.getString("semester_id"));
				item.add(rs.getString("semester_name"));
				item.add(rs.getString("credit"));
				item.add(rs.getString("course_time"));
				item.add(rs.getString("exam_type"));
				item.add(rs.getString("examcourse_id"));
				list.add(item);
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
	 * @param major_id
	 * @param semesterId
	 * @param course_id
	 * @param course_name
	 * @param batch_id
	 * @param examsequence_id
	 * @return �õ��Ѿ����뵽�����б�Ŀγ̵���Ϣ��
	 * @throws PlatformException
	 */
	public List searchReExamCourse(Page page, List searchproperty,
			List orderproperty, String batch_id,String basicsequence_id) throws PlatformException {
		dbpool db = null;
/*String sql = "select distinct course_id,examcourse_id,opencourse_id,course_name,semester_id,semester_name,credit,course_time,exam_type from (select d.id as examcourse_id,a.id as course_id,b.id as opencourse_id,a.name as course_name,c.id as semester_id, a.credit as credit ,a.course_time as course_time,c.name as semester_name,nvl(d.exam_type,'9') as exam_type from (select id ,name,credit, course_time from entity_course_info) a,entity_course_active b,entity_semester_info c, (select id,open_course_id,exam_type,test_batch_id,basicsequence_id  from test_examcourse_info where test_batch_id='"
			+ batch_id + "' and basicsequence_id= '" + basicsequence_id  
			+ "') d ,(select id from test_basicsequence_info  ) tbs,(select id,basicsequence_id from test_examsequence_info) tes where a.id=b.course_id and b.semester_id=c.id and b.id=d.open_course_id and tes.basicsequence_id = tbs.id and tbs.id = d.basicsequence_id )"
*//*
*String sql = "select distinct course_id,examcourse_id,opencourse_id,course_name,semester_id,semester_name,credit,course_time,exam_type from (select d.id as examcourse_id,a.id as course_id,b.id as opencourse_id,a.name as course_name,c.id as semester_id, a.credit as credit ,a.course_time as course_time,c.name as semester_name,nvl(d.exam_type,'9') as exam_type from (select id ,name,credit, course_time from entity_course_info) a,entity_course_active b,entity_semester_info c, (select id,open_course_id,exam_type,test_batch_id,basicsequence_id  from test_examcourse_info where test_batch_id='"
			+ batch_id + "' and basicsequence_id= '" + basicsequence_id  
			+ "') d ,(select id from test_basicsequence_info  ) tbs,(select id,basicsequence_id from test_examsequence_info) tes where a.id=b.course_id and b.semester_id=c.id and b.id=d.open_course_id and tes.basicsequence_id = tbs.id and tbs.id = d.basicsequence_id )"//tbs.id = d.basicsequence_id )"
			+ Conditions.convertToCondition(searchproperty, orderproperty);
*/
		/*String sql = "select distinct examcourse_id, course_id,course_name,credit,course_time,exam_type from (select a.id as course_id,a.name as course_name, a.credit as credit ,a.course_time as course_time,d.id as examcourse_id, nvl(d.exam_type,'9') as exam_type from (select id ,name,credit, course_time from entity_course_info) a, (select id,course_id,exam_type,basicsequence_id  from expendtest_examcourse_info where test_batch_id='"
			+ batch_id + "' and basicsequence_id= '" + basicsequence_id  
			+ "') d ,(select id from test_basicsequence_info  ) tbs,(select id,basicsequence_id from expendtest_examsequence_info) tes where   tes.basicsequence_id = tbs.id and tbs.id = d.basicsequence_id and a.id = d.course_id)"//tes.id = d.basicsequence_id and a.id = d.course_id)"
		*/	
		String sql  = 

			"select distinct examcourse_id,course_id,course_name, credit, course_time,exam_type" +
			"  from (select a.id as course_id,a.name as course_name, a.credit as credit,a.course_time as course_time,d.id as examcourse_id," + 
			"               nvl(d.exam_type, '9') as exam_type" + 
			"          from (select tt1.id as id, tt1.name as name, tt2.credit  as credit, tt2.coursetime as course_time from entity_course_info tt1, entity_teachplan_course tt2 where tt1.id = tt2.course_id) a," + 
			"               (select t.id, t.course_id, t.exam_type, t.basicsequence_id" + 
			"                  from expendtest_examcourse_info t,test_basicsequence_info tbs" + 
			"                 where test_batch_id = '"+ batch_id + "' and basicsequence_id= '" + basicsequence_id+"' and tbs.id = t.basicsequence_id ) d," + 
			"                (select t.id, t.basicsequence_id from expendtest_examsequence_info t,test_basicsequence_info tbs where tbs.id = t.basicsequence_id) tes" + 
			"         where  a.id = d.course_id)"
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
				List item = new ArrayList();
				item.add(rs.getString("course_id"));
		//		item.add(rs.getString("opencourse_id"));
				item.add(rs.getString("course_name"));
		//		item.add(rs.getString("semester_id"));
		//		item.add(rs.getString("semester_name"));
				item.add(rs.getString("credit"));
				item.add(rs.getString("course_time"));
				item.add(rs.getString("exam_type"));
				item.add(rs.getString("examcourse_id"));
				list.add(item);
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
	 * @param major_id
	 * @param semesterId
	 * @param course_id
	 * @param course_name
	 * @param batch_id
	 * @param examsequence_id
	 * @return �õ��Ѿ����뵽�����б�Ŀγ̵���Ϣ��
	 * @throws PlatformException
	 */
	public List searchNotExamCourse(Page page, List searchproperty,
			List orderproperty, String batch_id,String basicsequence_id) throws PlatformException {
		dbpool db = null;
		String sql = "select a.id as course_id,d.id as opencourse_id,a.name as course_name, c.id as semester_id,c.name as semester_name,a.credit as credit ,a.course_time as course_time "
			+ " from (select id ,name,credit, course_time from entity_course_info) a,entity_semester_info c,( select eca.id as id,eca.course_id as course_id,eca.semester_id as semester_id from entity_course_active eca where eca.id not in(select open_course_id from test_examcourse_info ) ) d"
			+ " where a.id = d.course_id and d.semester_id = c.id"
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
				List item = new ArrayList();
				item.add(rs.getString("course_id"));
				item.add(rs.getString("opencourse_id"));
				item.add(rs.getString("course_name"));
				item.add(rs.getString("semester_id"));
				item.add(rs.getString("semester_name"));
				item.add(rs.getString("credit"));
				item.add(rs.getString("course_time"));
				
				list.add(item);
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
	 * @param major_id
	 * @param semesterId
	 * @param course_id
	 * @param course_name
	 * @param batch_id
	 * @param examsequence_id
	 * @return �õ��Ѿ����뵽�����б�Ŀγ̵���Ϣ��
	 * @throws PlatformException
	 */
	public List searchReCourse(Page page,List searchproperty,List orderproperty,  String batch_id,String basicsequence_id) throws PlatformException {
		dbpool db = null;
		MyResultSet rs = null;
		List list = null;
		String con = Conditions.convertToCondition(searchproperty, null);
		String con2 = "";
		if(!"".equals(con)){
			con2 = " and "+ con.substring(6);
		}
		String con3 = Conditions.convertToCondition(null, orderproperty);
		
		String sql ="select distinct a.id as course_id, a.name as course_name,a.credit  as credit, a.course_time as course_time" +
		"  from (select id, name, credit, course_time from entity_course_info) a," + 
		" (select eca.id  as id, eca.course_id   as course_id from entity_course_active eca" + 
		"   where eca.course_id not in (select course_id from expendtest_examcourse_info t where t.test_batch_id='"+batch_id +"' )) d" + 
		"  where a.id = d.course_id " +con2 +con3;
/*		String sql = "select a.id as course_id,d.id as opencourse_id,a.name as course_name, c.id as semester_id,c.name as semester_name,a.credit as credit ,a.course_time as course_time "
			+ " from (select id ,name,credit, course_time from entity_course_info) a,entity_semester_info c,( select eca.id as id,eca.course_id as course_id,eca.semester_id as semester_id from entity_course_active eca where eca.id not in(select open_course_id from test_examcourse_info ) ) d"
			+ " where a.id = d.course_id and d.semester_id = c.id"
			+ Conditions.convertToCondition(searchproperty, orderproperty);
*/			
		
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
				List item = new ArrayList();
				item.add(rs.getString("course_id"));
//				item.add(rs.getString("opencourse_id"));
				item.add(rs.getString("course_name"));
//				item.add(rs.getString("semester_id"));
//				item.add(rs.getString("semester_name"));
				item.add(rs.getString("credit"));
				item.add(rs.getString("course_time"));
				
				list.add(item);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}
	
	
	public int getSiteTestRoomsNum1(String activeBatchId,String course_id,String siteId, HttpServletRequest request)
	throws PlatformException {
	
	dbpool db = new dbpool();
	String conditions = "";
	if (request.getParameter("exambatchid") != null
			&& !request.getParameter("exambatchid").equals(""))
		conditions += (" and test_batch_id='"
				+ request.getParameter("exambatchid") + "'");
	
	if (request.getParameter("examsequence_id") != null
			&& !request.getParameter("examsequence_id").equals(""))
		conditions += (" and test_batch_id='"
				+ request.getParameter("examsequence_id") + "'");
	
/*	String sql = "select teci.name,eet.name,emi.name,ki.name,t.id, t.room_no, t.course_id,t.room_num, t.kaoqu_id, t.shenfen_id, t.edu_type_id, t.major_id, t.grade_id, t.teacher, t.address" +
			" from expendtest_examroom_info t,expendtest_examcourse_info teci,entity_edu_type eet,entity_major_info emi,entity_kaoqu_info ki" + 
			"  where t.batch_id ='" + activeBatchId +"'" + 
			"  and teci.id = t.course_id" + 
			"  and eet.id = t.edu_type_id" + 
			"  and emi.id = t.major_id" + 
			"  and ki.id = t.kaoqu_id";
*/	
	String sql = 
		"select course_name,edu_type_name,major_name,kaoqu_name,room_id,room_no,course_id,room_num,kaoqu_id,shenfen_id,edu_type_id,major_id," +
		" grade_id,grade_name,teacher,address" + 
		" from (select teci.name as course_name,egi.name as grade_name, eet.name as edu_type_name, emi.name as major_name, ki.name as kaoqu_name, t.id as room_id," + 
		"       t.room_no as room_no, t.course_id as course_id, t.room_num as room_num, t.kaoqu_id as kaoqu_id, t.shenfen_id as shenfen_id," + 
		"       t.edu_type_id as edu_type_id,  t.major_id as major_id, t.grade_id as grade_id, t.teacher as teacher, t.address as address" + 
		"  from expendtest_examroom_info   t," + 
		"       expendtest_examcourse_info teci," + 
		"       entity_edu_type      eet," + 
		"       entity_major_info    emi," + 
		"       entity_kaoqu_info    ki ,entity_grade_info  egi" + 
		"  where t.batch_id = '"+activeBatchId+"'" + 
		"   and teci.id = t.course_id and t.course_id='"+ course_id+"'"+ 
		"   and eet.id = t.edu_type_id" + 
		"   and emi.id = t.major_id" + 
		"   and ki.id = t.kaoqu_id and  egi.id = t.grade_id)";
			
	
	int i = 0;
	i = db.countselect(sql);
	return i;
	}
	
	public List getSiteTestRooms1(String activeBatchId,String course_id,String siteId, Page page,
			HttpServletRequest request) throws PlatformException {
		
		dbpool db = new dbpool();
		List userList = new ArrayList();
		MyResultSet rs = null;
		String conditions = "";
		String order = "";
		order = " order by test_batch_id,basicsequence_id,course_id";
		if (request.getParameter("exambatchid") != null
				&& !request.getParameter("exambatchid").equals(""))
			conditions += (" and test_batch_id='"
					+ request.getParameter("exambatchid") + "'");

		if (request.getParameter("examsequence_id") != null
				&& !request.getParameter("examsequence_id").equals(""))
			conditions += (" and examsequence_id='"
					+ request.getParameter("examsequence_id") + "'");
		String sql = 
			"select course_name,edu_type_name,major_name,kaoqu_name,room_id,room_no,course_id,room_num,kaoqu_id,shenfen_id,edu_type_id,major_id," +
			" grade_id,grade_name,teacher,address" + 
			" from (select teci.name as course_name,egi.name as grade_name, eet.name as edu_type_name, emi.name as major_name, ki.name as kaoqu_name, t.id as room_id," + 
			"       t.room_no as room_no, t.course_id as course_id, t.room_num as room_num, t.kaoqu_id as kaoqu_id, t.shenfen_id as shenfen_id," + 
			"       t.edu_type_id as edu_type_id,  t.major_id as major_id, t.grade_id as grade_id, t.teacher as teacher, t.address as address" + 
			"  from expendtest_examroom_info   t," + 
			"       expendtest_examcourse_info teci," + 
			"       entity_edu_type      eet," + 
			"       entity_major_info    emi," + 
			"       entity_kaoqu_info    ki ,entity_grade_info  egi" + 
			"  where t.batch_id = '"+activeBatchId+"'" + 
			"   and teci.id = t.course_id and t.course_id='"+ course_id+"'"+ 
			"   and eet.id = t.edu_type_id" + 
			"   and emi.id = t.major_id" + 
			"   and ki.id = t.kaoqu_id and  egi.id = t.grade_id)";
		ArrayList testRooms = new ArrayList();
		try {
			db = new dbpool();

			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
			
				HashMap course = new HashMap();
				course.put("course_id", rs.getString("course_id"));//
				course.put("course_name", rs.getString("course_name"));//
				course.put("edu_type_id", rs.getString("edu_type_id"));//
				course.put("edu_type_name", rs.getString("edu_type_name"));//
				course.put("major_id", rs.getString("major_id"));//
				course.put("major_name", rs.getString("major_name"));//
				course.put("kaoqu_id", rs.getString("kaoqu_id"));//
				course.put("kaoqu_name", rs.getString("kaoqu_name"));//
				course.put("shenfen_id", rs.getString("shenfen_id"));//
				course.put("grade_id", rs.getString("grade_id"));//
				course.put("grade_name", rs.getString("grade_name"));
				course.put("teacher", rs.getString("teacher"));
				course.put("address", rs.getString("address"));
				course.put("room_id", rs.getString("room_id"));//
				course.put("room_no", rs.getString("room_no"));//
				course.put("room_num", rs.getString("room_num"));//
				//course.put("useres", rs.getString("useres"));
			
				testRooms.add(course);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return testRooms;
	}
	
	public List getConnect_status(String room_id,String activeBatchId,String course_id,String siteId, Page page,
			HttpServletRequest request) throws PlatformException {
		
		dbpool db = new dbpool();
		List userList = new ArrayList();
		MyResultSet rs = null;
		String conditions = "";
		String order = "";
			String sql = "select t.connect_status as connect_status from expendtest_examroom_info t where id ='"+room_id+"'";

		ArrayList testRooms = new ArrayList();
		try {
			db = new dbpool();

			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
			
				HashMap course = new HashMap();
				course.put("connect_status", rs.getString("connect_status"));//
				
			
				testRooms.add(course);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return testRooms;
	}
	
	/**
	 * @author shu
	 * @param ids
	 * @return �ϲ�������
	 * @throws PlatformException
	 */
	public int connectExamRoom(String[] ids)
	throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
	//	MyResultSet rss = null;
		StringBuffer str = new StringBuffer();
		List countnum= new ArrayList();
		int reet = 0;
		try{
		for(int i=0;i<ids.length;i++){//�Ѹ��room_Id�еĿ��Ե��������洢��4��
			String num = "";
			String sql = "select room_num as room_num from expendtest_examroom_info t where id='"+ids[i]+"'";
			rs = db.executeQuery(sql);
			try {
				while(rs!=null&&rs.next()){
					num = rs.getString("room_num");
					countnum.add(num);
						}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				
			}
			
		}
		
		for(int i=1;i<ids.length;i++){
		
			String sql ="select t.desk_no as desk_no,t.id as course_user_id  from expendtest_examuser_course t  where room_id ='"+ids[i]+"' order by t.desk_no";
			String desk_no_list ="";
			String course_user_id ="";
			String desk_no_add =""; 
			int desk_no_add2 =0;
			for(int j=0;j<i;j++){//�õ���roomid����Ҫ������λ��ʱ����λ����Ҫ��ӵĸ������ǰ��ļ���roomid�Ŀ�������ĺ͡�
				desk_no_add2	+= Integer.parseInt((String)countnum.get(j));
			}
			desk_no_add = String.valueOf(desk_no_add2);
			rs = db.executeQuery(sql);
			int ret = 0;
			try {
				while(rs!=null&&rs.next()){
					desk_no_list = rs.getString("desk_no");//�õ���ÿ�����λ�ţ�ֻҪ������Ҫ��ӵĸ����ٸ��»�ȥ�ͺ��ˡ�
					course_user_id = rs.getString("course_user_id");
					 
					int desk_no_new = desk_no_add2 + Integer.parseInt(desk_no_list);
					String desk_no_new_str = String.valueOf(desk_no_new);
					//int k = j +1;//Ϊ0ʱ����Ϊ1.
					String m = "";
					if(desk_no_new<=9){
						desk_no_new_str = "0" +String.valueOf(desk_no_new);
					}else{
						desk_no_new_str = String.valueOf(desk_no_new);
					}
					 String sql1 =  "update expendtest_examuser_course set desk_no='"+desk_no_new_str+"' where id ='"+course_user_id+"'";
					 ret = db.executeUpdate(sql1);
  
						}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				
			}
			

		}
		
		
		
		for(int i=0;i<ids.length;i++)
		{
			str.append(ids[i]).append(",");
		}
		if(str.length()>1) str.deleteCharAt(str.lastIndexOf(","));
		String sql="update  expendtest_examroom_info t set t.connect_status='1', t.room_no=(select room_no from expendtest_examroom_info where id='"+ids[0]+"') where t.id in ("+str+")";
		UserUpdateLog.setDebug("connectExamRoom,sql="+sql);
		
		reet =  db.executeUpdate(sql);
		}catch(Exception e){
			
		}finally{
			db.close(rs);
			db = null;
		}
		return reet;
}
		
/*ԭ4�ı���		
		public int connectExamRoom(String[] ids)
		throws PlatformException {
			dbpool db = new dbpool();
			StringBuffer str = new StringBuffer();
			for(int i=0;i<ids.length;i++)
			{
				str.append(ids[i]).append(",");
			}
			if(str.length()>1) str.deleteCharAt(str.lastIndexOf(","));
			String sql="update  expendtest_examroom_info t set t.connect_status='1', t.room_no=(select room_no from expendtest_examroom_info where id='"+ids[0]+"') where t.id in ("+str+")";
			UserUpdateLog.setDebug("connectReExamRoom,sql="+sql);
			return db.executeUpdate(sql);
	}
		*/
		/**
		 * ��ÿ�������Ŀ�����λ��Ϣ
		 * @param testroomId
		 * @return
		 */
			
			public List getExamroomDisplyInfo(String testroomId) {
				dbpool db = new dbpool();
				MyResultSet rs = null;
				List roomList = new ArrayList();
				//��Ҫ�г�ͬһ������������
				//String sql = "select * from recruit_test_desk d,recruit_student_info s where d.testroom_id in (select * from recruit_test_room r where r.test_course_id='' and r.room_no='') and d.recruitstudent_id=s.id";
	/*			String sql = "select  s.name,s.testcard_id,s.edu,s.major_id1,d.numbyroom,e.name as eduname,m.name as majorname  from recruit_test_desk d,recruit_student_info s,entity_major_info m, entity_edu_type e where d.testroom_id='"
					+testroomId+"' and d.recruitstudent_id=s.id and e.id=s.edu and m.id=s.major_id1 ";
	*/
				
				String sql = 	"select course_user_id,user_id,desk_no,user_name from (select t.id as course_user_id,t.user_id as user_id,t.desk_no as desk_no,u.name as user_name" +
					" from expendtest_examuser_course t ,expendtest_user_info u where room_id='"+testroomId+"' and u.id = t.user_id) order by desk_no";

				rs = db.executeQuery(sql);
				try {
					while (rs != null && rs.next()) {
						HashMap hash = new HashMap();
						hash.put("user_name", rs.getString("user_name"));
						hash.put("desk_no", rs.getString("desk_no"));
					//	hash.put("edu", rs.getString("edu"));hash.put("eduname", rs.getString("eduname"));
					//	hash.put("major_id1", rs.getString("major_id1"));
					//	hash.put("numbyroom", rs.getString("numbyroom"));hash.put("majorname", rs.getString("majorname"));
						roomList.add(hash);
					}
				} catch (SQLException e) {
					
				} finally {
					db.close(rs);
				}
				return roomList;

			}
			
			
			/**
			 * @author bob 
			 * @param testroomno
			 * @return���room_no4�õ�������Ա��Ϣ��
			 */
			public List getExamroomDisplyInfo3(String testroomno) {
				dbpool db = new dbpool();
				MyResultSet rs = null;
				List roomList = new ArrayList();
				//��Ҫ�г�ͬһ������������
				//String sql = "select * from recruit_test_desk d,recruit_student_info s where d.testroom_id in (select * from recruit_test_room r where r.test_course_id='' and r.room_no='') and d.recruitstudent_id=s.id";
	/*			String sql = "select  s.name,s.testcard_id,s.edu,s.major_id1,d.numbyroom,e.name as eduname,m.name as majorname  from recruit_test_desk d,recruit_student_info s,entity_major_info m, entity_edu_type e where d.testroom_id='"
					+testroomId+"' and d.recruitstudent_id=s.id and e.id=s.edu and m.id=s.major_id1 ";
	*/
				
	/*			String sql = 	"select course_user_id,user_id,desk_no,user_name from (select t.id as course_user_id,t.user_id as user_id,t.desk_no as desk_no,u.name as user_name" +
					" from test_examuser_course t ,test_user_info u where room_no='"+testroomno+"' and u.id = t.user_id) order by desk_no";
	*/				
				String sql = 
					" select course_user_id,user_id,desk_no,user_name from " +
					" (select t.id as course_user_id,t.user_id as user_id,t.desk_no as desk_no,u.name as user_name " + 
					"  from expendtest_examuser_course t ,expendtest_user_info u,expendtest_examroom_info r  where r.room_no='"+testroomno+"' " + 
					"         and r.id = t.room_id and u.id = t.user_id)         order by desk_no";
		
				rs = db.executeQuery(sql);
				try {
					while (rs != null && rs.next()) {
						HashMap hash = new HashMap();
						hash.put("user_name", rs.getString("user_name"));
						hash.put("desk_no", rs.getString("desk_no"));
					//	hash.put("edu", rs.getString("edu"));hash.put("eduname", rs.getString("eduname"));
					//	hash.put("major_id1", rs.getString("major_id1"));
					//	hash.put("numbyroom", rs.getString("numbyroom"));hash.put("majorname", rs.getString("majorname"));
						roomList.add(hash);
					}
				} catch (SQLException e) {
					
				} finally {
					db.close(rs);
				}
				return roomList;

			}
			

			
			public List getExamroomDisplyInfo4
			(String testroomId) {
				dbpool db = new dbpool();
				MyResultSet rs = null;
				List roomList = new ArrayList();
				//��Ҫ�г�ͬһ������������
				//String sql = "select * from recruit_test_desk d,recruit_student_info s where d.testroom_id in (select * from recruit_test_room r where r.test_course_id='' and r.room_no='') and d.recruitstudent_id=s.id";
	/*			String sql = "select  s.name,s.testcard_id,s.edu,s.major_id1,d.numbyroom,e.name as eduname,m.name as majorname  from recruit_test_desk d,recruit_student_info s,entity_major_info m, entity_edu_type e where d.testroom_id='"
					+testroomId+"' and d.recruitstudent_id=s.id and e.id=s.edu and m.id=s.major_id1 ";
	*/
				
		/*String sql = 	"select course_user_id,user_id,desk_no,user_name,major_name,grade_name,edutype_name,shenfen_id,major_id,edutype_id,reg_no from (select t.id as course_user_id, esi.shenfen_id as shenfen_id ,esi.major_id as major_id,emi.name as major_name,egi.name as grade_name,eet.name as edutype_name,esi.edutype_id as edutype_id,esi.reg_no as reg_no,t.user_id as user_id,t.desk_no as desk_no,u.name as user_name" +
				" from test_examuser_course t ,entity_major_info emi,entity_grade_info egi,entity_edu_type eet,test_user_info u,entity_student_info esi where room_id='"+testroomId+"' and emi.id = esi.major_id and esi.grade_id = egi.id and eet.id = esi.edutype_id and u.id = t.user_id  and esi.reg_no = u.login_id) order by desk_no";
			*/
				
/*				String sql = 
					"select course_user_id, " +
					"       user_id, " + 
					"       desk_no," + 
					"       user_name," + 
					"       major_name," + 
					"       grade_name," + 
					"       edutype_name," + 
					"       shenfen_id," + 
					"       major_id," + 
					"       edutype_id," + 
					"       reg_no " + 
					"  from (select t.id           as course_user_id, " + 
					"               esi.shenfen_id as shenfen_id," + 
					"               esi.major_id   as major_id," + 
					"               emi.name       as major_name," + 
					"               egi.name       as grade_name," + 
					"               eet.name       as edutype_name," + 
					"               esi.edutype_id as edutype_id," + 
					"               esi.reg_no     as reg_no," + 
					"               t.user_id      as user_id," + 
					"               t.desk_no      as desk_no," + 
					"               u.name         as user_name" + 
					"          from expendtest_examuser_course t," + 
					"               entity_major_info    emi," + 
					"               entity_grade_info    egi," + 
					"               entity_edu_type      eet," + 
					"               expendtest_user_info       u,expendtest_examroom_info r," + 
					"               entity_student_info  esi" + 
					"         where r.room_no = '"+testroomId+"' and r.id = t.room_id" + 
					"           and emi.id = esi.major_id" + 
					"           and esi.grade_id = egi.id" + 
					"           and eet.id = esi.edutype_id" + 
					"           and u.id = t.user_id" + 
					"           and esi.reg_no = u.login_id)" + 
					" order by desk_no";
*/	
				String sql = 
					"select course_user_id, " +
					"       user_id, " + 
					"       desk_no," + 
					"       user_name," + 
					"       major_name," + 
					"       grade_name," + 
					"       edutype_name," + 
					"       shenfen_id," + 
					"       major_id," + 
					"       edutype_id," + 
					"       reg_no " + 
					"  from (select t.id           as course_user_id, " + 
					"               esi.shenfen_id as shenfen_id," + 
					"               esi.major_id   as major_id," + 
					"               emi.name       as major_name," + 
					"               egi.name       as grade_name," + 
					"               eet.name       as edutype_name," + 
					"               esi.edutype_id as edutype_id," + 
					"               esi.reg_no     as reg_no," + 
					"               t.user_id      as user_id," + 
					"               t.desk_no      as desk_no," + 
					"               u.name         as user_name" + 
					"          from expendtest_examuser_course t," + 
					"               entity_major_info    emi," + 
					"               entity_grade_info    egi," + 
					"               entity_edu_type      eet," + 
					"               expendtest_user_info       u,expendtest_examroom_info r," + 
					"               entity_student_info  esi" + 
					"         where r.room_no = '"+testroomId+"' and r.id = t.room_id" + 
					"           and emi.id(+) = esi.major_id" + 
					"           and esi.grade_id = egi.id(+)" + 
					"           and eet.id(+) = esi.edutype_id" + 
					"           and u.id = t.user_id" + 
					"           and esi.reg_no(+) = u.login_id)" + 
					" order by desk_no";

				rs = db.executeQuery(sql);
				try {
					while (rs != null && rs.next()) {
						HashMap hash = new HashMap();
						hash.put("user_name", rs.getString("user_name"));
						hash.put("desk_no", rs.getString("desk_no"));
						hash.put("shenfen_id", rs.getString("shenfen_id"));
						hash.put("major_id", rs.getString("major_id"));
						hash.put("reg_no", rs.getString("reg_no"));
						hash.put("edutype_id", rs.getString("edutype_id"));
						hash.put("major_name", rs.getString("major_name"));
						hash.put("edutype_name", rs.getString("edutype_name"));
						hash.put("grade_name", rs.getString("grade_name"));
					//	hash.put("edu", rs.getString("edu"));hash.put("eduname", rs.getString("eduname"));
					//	hash.put("major_id1", rs.getString("major_id1"));
					//	hash.put("numbyroom", rs.getString("numbyroom"));hash.put("majorname", rs.getString("majorname"));
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
				//��Ҫ�г�ͬһ������������
				//String sql = "select * from recruit_test_desk d,recruit_student_info s where d.testroom_id in (select * from recruit_test_room r where r.test_course_id='' and r.room_no='') and d.recruitstudent_id=s.id";
	/*			String sql = "select  s.name,s.testcard_id,s.edu,s.major_id1,d.numbyroom,e.name as eduname,m.name as majorname  from recruit_test_desk d,recruit_student_info s,entity_major_info m, entity_edu_type e where d.testroom_id='"
					+testroomId+"' and d.recruitstudent_id=s.id and e.id=s.edu and m.id=s.major_id1 ";
	*/
				
				String sql = 	"select course_user_id,user_id,desk_no,user_name,major_name,edutype_name,shenfen_id,major_id,edutype_id,reg_no,grade_name from (select t.id as course_user_id, esi.shenfen_id as shenfen_id ,esi.major_id as major_id,emi.name as major_name,eet.name as edutype_name,esi.edutype_id as edutype_id,esi.reg_no as reg_no,t.user_id as user_id,t.desk_no as desk_no,u.name as user_name,egi.name as grade_name" +
					" from expendtest_examuser_course t ,entity_major_info emi,entity_edu_type eet,expendtest_user_info u,entity_student_info esi, entity_grade_info          egi  where room_id='"+testroomId+"' and emi.id = esi.major_id and esi.grade_id = egi.id and eet.id = esi.edutype_id and u.id = t.user_id  and esi.reg_no = u.login_id) order by desk_no";

				rs = db.executeQuery(sql);
				try {
					while (rs != null && rs.next()) {
						HashMap hash = new HashMap();
						hash.put("user_name", rs.getString("user_name"));
						hash.put("desk_no", rs.getString("desk_no"));
						hash.put("shenfen_id", rs.getString("shenfen_id"));
						hash.put("major_id", rs.getString("major_id"));
						hash.put("reg_no", rs.getString("reg_no"));
						hash.put("edutype_id", rs.getString("edutype_id"));
						hash.put("major_name", rs.getString("major_name"));
						hash.put("edutype_name", rs.getString("edutype_name"));
						hash.put("grade_name", rs.getString("grade_name"));
					//	hash.put("edu", rs.getString("edu"));hash.put("eduname", rs.getString("eduname"));
					//	hash.put("major_id1", rs.getString("major_id1"));
					//	hash.put("numbyroom", rs.getString("numbyroom"));hash.put("majorname", rs.getString("majorname"));
						roomList.add(hash);
					}
				} catch (SQLException e) {
					
				} finally {
					db.close(rs);
				}
				return roomList;

			}
		

}
