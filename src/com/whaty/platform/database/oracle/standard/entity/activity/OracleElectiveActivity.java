/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity.activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.whaty.platform.User;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.fee.deal.OracleUserFee;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleRecruitStudent;
import com.whaty.platform.entity.BasicActivityManage;
import com.whaty.platform.entity.activity.Elective;
import com.whaty.platform.entity.activity.ElectiveActivity;
import com.whaty.platform.entity.activity.ElectiveStatus;
import com.whaty.platform.entity.activity.TeachClass;
import com.whaty.platform.entity.fee.deal.FeeType;
import com.whaty.platform.entity.fee.deal.PayoutType;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author chenjian
 * 
 */
public class OracleElectiveActivity extends ElectiveActivity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.ElectiveActivity#selectTeachClass()
	 */
	public void selectTeachClass() throws PlatformException {
		dbpool db = new dbpool();
		List sqlList = new ArrayList();
		Iterator stuTeachClasses = this.getStudentTeachClassMap().entrySet()
				.iterator();
		while (stuTeachClasses.hasNext()) {
			Map.Entry stuTeachClass = (Map.Entry) stuTeachClasses.next();
			Student student = (Student) stuTeachClass.getKey();
			List teachClassList = (List) stuTeachClass.getValue();
			String sql = "insert into entity_elective(id,	student_id,teachclass_id,status) "
					+ "select to_char(s_elective_id.nextval),'"
					+ student.getId()
					+ ","
					+ " id,'"
					+ ElectiveStatus.SELECTED
					+ "' from entity_teach_class "
					+ "where id in ("
					+ getTeachClassesStr(teachClassList) + ")";
			UserAddLog
					.setDebug("OracleElectiveActivity.selectTeachClass() SQL="
							+ sql + " DATE=" + new Date());
			sqlList.add(sql);
		}
		int i = db.executeUpdateBatch(sqlList);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.ElectiveActivity#unSelectTeachClass()
	 */
	public int unSelectTeachClass() throws PlatformException {
		dbpool db = new dbpool();
		List sqlList = new ArrayList();
		Iterator stuTeachClasses = this.getStudentTeachClassMap().entrySet()
				.iterator();
		while (stuTeachClasses.hasNext()) {
			Map.Entry stuTeachClass = (Map.Entry) stuTeachClasses.next();
			Student student = (Student) stuTeachClass.getKey();
			List teachClassList = (List) stuTeachClass.getValue();
			String sql = "delete from  entity_elective where student_id='"
					+ student.getId() + " " + "and teachclass_id in ("
					+ getTeachClassesStr(teachClassList) + ")";
			UserDeleteLog
					.setDebug("OracleElectiveActivity.unSelectTeachClass() SQL="
							+ sql + " DATE=" + new Date());
			sqlList.add(sql);
		}
		int i = db.executeUpdateBatch(sqlList);
		return i;
	}

	public void confirmElectiveByUserId(String[] idSet, String semester_id,
			String student_id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String selected = "";
		for (int i = 0; i < idSet.length; i = i + 1) {
			if (idSet[i].equals("alldelete")) {
				selected = "";
				break;
			} else {
				selected = selected + "'" + idSet[i] + "',";
			}
		}
		if (selected.equals("")) {
			String delsql = "update entity_elective set status='0' where student_id='"
					+ student_id
					+ "' "
					+ "and teachclass_id in(select t.id from entity_course_active a,entity_teach_class t "
					+ "where a.semester_id='"
					+ semester_id
					+ "' and a.id = t.open_course_id)";
			int i = db.executeUpdate(delsql);
			UserUpdateLog
					.setDebug("OracleElectiveActivity.confirmElectiveByUserId(String[] idSet, String semester_id,String student_id) SQL="
							+ delsql + " COUNT=" + i + " DATE=" + new Date());
		} else {
			String haveselected = "";
			selected = selected.substring(0, selected.length() - 1);
			String selectsql = "select teachclass_id from entity_elective where student_id='"
					+ student_id
					+ "' and "
					+ "teachclass_id in (select c.id as id from entity_course_active a,entity_teach_class c "
					+ "where c.open_course_id = a.id and a.semester_id='"
					+ semester_id + "')";
			try {
				rs = db.executeQuery(selectsql);
				while (rs != null && rs.next()) {
					haveselected = haveselected + "'"
							+ rs.getString("teachclass_id") + "',";
				}
			} catch (Exception e) {
				
			} finally {
				db.close(rs);
				db = null;
			}
			db.close(rs);
			if (!haveselected.equals("")) {
				String deletesql = "update lrn_elective set status='0' "
						+ "where student_id='"
						+ student_id
						+ "' and teachclass_id  not in("
						+ selected
						+ ") "
						+ "and teachclass_id in (select t.id from entity_course_active a,entity_teach_class t "
						+ "where a.semester_id='" + semester_id
						+ "' and a.id = t.open_course_id)";
				int suc = db.executeUpdate(deletesql);
				UserUpdateLog
						.setDebug("OracleElectiveActivity.confirmElectiveByUserId(String[] idSet, String semester_id,String student_id) SQL="
								+ deletesql
								+ " COUNT="
								+ suc
								+ " DATE="
								+ new Date());
				for (int i = 0; i < idSet.length; i++) {
					if (haveselected.indexOf("'" + idSet[i] + "'") < 0) {
						String insertsql = "update entity_elective set status='1' where student_id='"
								+ student_id
								+ "' and teachclass_id='"
								+ idSet[i] + "'";
						suc = db.executeUpdate(insertsql);
						UserUpdateLog
								.setDebug("OracleElectiveActivity.confirmElectiveByUserId(String[] idSet, String semester_id,String student_id) SQL="
										+ insertsql
										+ " COUNT="
										+ suc
										+ " DATE=" + new Date());
					}
				}
			} else {
				for (int i = 0; i < idSet.length; i++) {
					String insertsql = "update entity_elective set status='1' where student_id='"
							+ student_id
							+ "' and teachclass_id='"
							+ idSet[i]
							+ "'";
					int suc = db.executeUpdate(insertsql);
					UserUpdateLog
							.setDebug("OracleElectiveActivity.confirmElectiveByUserId(String[] idSet, String semester_id,String student_id) SQL="
									+ insertsql
									+ " COUNT="
									+ suc
									+ " DATE="
									+ new Date());
				}
			}
		}
	}

	/*
	 * (non-Javadoc) �������ѧ����ѡ��
	 * 
	 * @see com.whaty.platform.entity.activity.ElectiveActivity#electiveCoursesByUserId(java.lang.String[],
	 *      java.lang.String, java.lang.String)
	 */
	public void electiveCoursesByUserId(String[] idSet, String semester_id,
			String student_id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String selected = "";
		ArrayList sqlList = new ArrayList();
		for (int i = 0; i < idSet.length; i = i + 1) {
			if (idSet[i].equals("alldelete")) {
				selected = "";
				break;
			} else {
				selected = selected + "'" + idSet[i] + "',";
			}
		}
		if (selected.equals("")) {
			selected = "''";
			List list = new ArrayList();
			String delsql = "delete from entity_elective where student_id='"
					+ student_id
					+ "' "
					+ "and teachclass_id in (select c.id as id from entity_course_active a,entity_teach_class c "
					+ "where c.open_course_id = a.id and a.semester_id='"
					+ semester_id + "')";

			// ��ԭ�ȵ��޸ķ��ü�¼״̬��Ϊ�����������ü�¼�����һ���˷��ü�¼
			String insertSql = "insert into entity_userfee_record (id,user_id,date_time,fee_value,fee_type,payout_type,note,checked,invoice_status,teachclass_id)  select s_entity_fee_id.nextval, b.user_id,sysdate,abs(b.fee_value),b.fee_type,b.payout_type,b.note,b.checked,b.invoice_status,b.teachclass_id from (select  distinct src.user_id,sysdate,src.fee_value,src.fee_type,'"
					+ PayoutType.ROLLBACK
					+ "' as PAYOUT_TYPE,src.note,src.checked,src.invoice_status,src.teachclass_id from entity_userfee_record src where src.user_id = '"
					+ student_id
					+ "' and src.teachclass_id in (select c.id as id from entity_course_active a,entity_teach_class c ,entity_elective e where c.open_course_id = a.id and e.teachclass_id = c.id and e.student_id='"
					+ student_id
					+ "' and a.semester_id='"
					+ semester_id
					+ "') and src.payout_type = '"
					+ PayoutType.CONSUME
					+ "') b";

			list.add(insertSql);
			list.add(delsql);

			UserDeleteLog
					.setDebug("OracleElectiveActivity.electiveCoursesByUserId(String[] idSet, String semester_id,String student_id) SQL="
							+ delsql + " DATE=" + new Date());
			db.executeUpdateBatch(list);
		} else {
			String haveselected = "";
			selected = selected.substring(0, selected.length() - 1);
			String selectsql = "select teachclass_id from entity_elective where student_id='"
					+ student_id
					+ "' and "
					+ "teachclass_id in (select c.id as id from entity_course_active a,entity_teach_class c "
					+ "where c.open_course_id = a.id and a.semester_id='"
					+ semester_id + "')";
			// System.out.println("selectsql=" + selectsql);

			try {
				rs = db.executeQuery(selectsql);
				while (rs != null && rs.next()) {
					haveselected = haveselected + "'"
							+ rs.getString("teachclass_id") + "',";
				}

			} catch (Exception e) {
				
			}
			if (!haveselected.equals("")) {
				String deletesql = "delete from entity_elective where student_id='"
						+ student_id
						+ "' and teachclass_id not in "
						+ "("
						+ selected
						+ ") and teachclass_id in (select c.id as id from entity_course_active a,entity_teach_class c "
						+ "where c.open_course_id = a.id and a.semester_id='"
						+ semester_id + "')";
				UserDeleteLog
						.setDebug("OracleElectiveActivity.electiveCoursesByUserId(String[] idSet, String semester_id,String student_id) SQL="
								+ deletesql + " DATE=" + new Date());

				// ��ԭ�ȵ��޸ķ��ü�¼״̬��Ϊ�����������ü�¼�����һ���˷��ü�¼
				String updateSql = "insert into entity_userfee_record (id,user_id,date_time,fee_value,fee_type,payout_type,note,checked,invoice_status,teachclass_id)  select s_entity_fee_id.nextval, b.user_id,sysdate,abs(b.fee_value),b.fee_type,b.payout_type,b.note,b.checked,b.invoice_status,b.teachclass_id from (select distinct src.user_id,sysdate,src.fee_value,src.fee_type,'"
						+ PayoutType.ROLLBACK
						+ "' as PAYOUT_TYPE,src.note,src.checked,src.invoice_status,src.teachclass_id from entity_userfee_record src where src.user_id = '"
						+ student_id
						+ "' and src.teachclass_id not in ("
						+ selected
						+ ") and src.teachclass_id in (select c.id as id from entity_course_active a,entity_teach_class c,entity_elective e where c.open_course_id = a.id and e.teachclass_id = c.id and e.student_id = '"
						+ student_id
						+ "' and a.semester_id='"
						+ semester_id
						+ "') and src.payout_type = '"
						+ PayoutType.CONSUME
						+ "') b";

				sqlList.add(updateSql);
				sqlList.add(deletesql);
				// db.executeUpdate(deletesql);
				for (int i = 0; i < idSet.length; i++) {
					if (haveselected.indexOf("'" + idSet[i] + "'") < 0) {
						String insertsql = "insert into entity_elective (id, student_id,teachclass_id,status) "
								+ "values ( s_elective_id.nextval, '"
								+ student_id + "','" + idSet[i] + "','1') ";
						// db.executeUpdate(insertsql);
						sqlList.add(insertsql);
						UserAddLog
								.setDebug("OracleElectiveActivity.electiveCoursesByUserId(String[] idSet, String semester_id,String student_id) SQL="
										+ insertsql + " DATE=" + new Date());
						String subSql = "select a.id from entity_course_active a,entity_teach_class c where a.id = c.open_course_id and c.id = '"
								+ idSet[i] + "'";
						String feeSql = "insert into entity_userfee_record(id,user_id,fee_value,fee_type,payout_type,checked,note,TEACHCLASS_ID) "
								+ "select s_entity_fee_id.nextval,'"
								+ student_id
								+ "',value,'"
								+ FeeType.CREDIT
								+ "','"
								+ PayoutType.CONSUME
								+ "','1',note,teachclass_id "
								+ "from (select distinct -(e.credit* f.creditfee) as value,concat(concat(concat(concat(concat(concat('ѡ�� �γ���ƣ�', '('||e.course_id||')'||e.course_name),';�γ�ѧ�֣�'),e.credit),';ѧ�ֱ�׼��ÿѧ�֣�'),f.creditfee),'Ԫ') as note,teachclass_id "
								+ "from (select nvl(a.credit,b.credit) as credit,a.course_id,a.course_name, teachclass_id from (select tc.credit,tc.course_id,c.name as course_name,etc.id as teachclass_id from "
								+ "entity_course_active a,entity_course_info c,entity_teachplan_course tc,entity_teachplan_info i,entity_student_info s,entity_teach_class etc where a.id in ("
								+ subSql
								+ ")  "
								+ "and tc.course_id = c.id and a.course_id = c.id and i.edutype_id = s.edutype_id and i.major_id = s.major_id and i.grade_id = s.grade_id and a.id = etc.open_course_id and s.id = '"
								+ student_id
								+ "' "
								+ "and tc.teachplan_id = i.id)a,(select a.course_id as course_id,c.credit  from entity_course_info c,entity_course_active a where a.id in ("
								+ subSql
								+ ") and a.course_id = c.id)b "
								+ "where b.course_id = a.course_id(+))e,(select type1_value as creditfee from entity_userfee_level where user_id = '"
								+ student_id + "')f) ";
						UserAddLog
								.setDebug("OracleElectiveActivity.electiveCoursesByUserId(String[] idSet, String semester_id,String student_id) SQL="
										+ feeSql + " DATE=" + new Date());
						sqlList.add(feeSql);
						String x_sql = "update entity_elective set status = '1' where student_id ='"
								+ student_id
								+ "' and teachclass_id  = '"
								+ idSet[i] + "'";
						// System.out.println("x_sql=" + x_sql);
						sqlList.add(x_sql);
						UserUpdateLog
								.setDebug("OracleElectiveActivity.electiveCoursesByUserId(String[] idSet, String semester_id,String student_id) SQL="
										+ x_sql + " DATE=" + new Date());
					}

				}
				db.executeUpdateBatch(sqlList);
			} else {
				for (int i = 0; i < idSet.length; i++) {
					String insertsql = "insert into entity_elective (id, student_id,teachclass_id,status) "
							+ "values ( s_elective_id.nextval, '"
							+ student_id
							+ "','" + idSet[i] + "','1') ";
					String subSql = "select a.id from entity_course_active a,entity_teach_class c where a.id = c.open_course_id and c.id = '"
							+ idSet[i] + "'";
					// String feeSql = "insert into
					// entity_userfee_record(id,user_id,fee_value,fee_type,payout_type,checked,note)
					// "
					// + "select s_entity_fee_id.nextval,'"
					// + student_id
					// + "',value,'"
					// + FeeType.CREDIT
					// + "','"
					// + PayoutType.CONSUME
					// + "','1',note "
					// + "from (select -(e.credit* f.creditfee) as
					// value,concat(concat(concat(concat(concat(concat('ѡ��
					// �γ���ƣ�',
					// '('||e.course_id||')'||e.course_name),';�γ�ѧ�֣�'),e.credit),';ѧ�ֱ�׼��ÿѧ�֣�'),f.creditfee),'Ԫ')
					// as note "
					// + "from (select nvl(a.credit,b.credit) as
					// credit,a.course_id,a.course_name from (select
					// tc.credit,tc.course_id,c.name as course_name from "
					// + "entity_course_active a,entity_course_info
					// c,entity_teachplan_course tc,entity_teachplan_info
					// i,entity_student_info s where a.id in ("
					// + subSql
					// + ") "
					// + "and tc.course_id = c.id and a.course_id = c.id and
					// i.edutype_id = s.edutype_id and i.major_id = s.major_id
					// and i.grade_id = s.grade_id and s.id = '"
					// + student_id
					// + "' "
					// + "and tc.teachplan_id = i.id)a,(select a.course_id as
					// course_id,c.credit from entity_course_info
					// c,entity_course_active a where a.id in ("
					// + subSql
					// + ") and a.course_id = c.id)b "
					// + "where b.course_id = a.course_id(+))e,(select
					// type1_value as creditfee from entity_userfee_level where
					// user_id = '"
					// + student_id + "')f) ";
					String feeSql = "insert into entity_userfee_record(id,user_id,fee_value,fee_type,payout_type,checked,note,TEACHCLASS_ID) "
							+ "select s_entity_fee_id.nextval,'"
							+ student_id
							+ "',value,'"
							+ FeeType.CREDIT
							+ "','"
							+ PayoutType.CONSUME
							+ "','1',note,teachclass_id "
							+ "from (select distinct -(e.credit* f.creditfee) as value,concat(concat(concat(concat(concat(concat('ѡ�� �γ���ƣ�', '('||e.course_id||')'||e.course_name),';�γ�ѧ�֣�'),e.credit),';ѧ�ֱ�׼��ÿѧ�֣�'),f.creditfee),'Ԫ') as note,teachclass_id "
							+ "from (select nvl(a.credit,b.credit) as credit,a.course_id,a.course_name, teachclass_id from (select tc.credit,tc.course_id,c.name as course_name,etc.id as teachclass_id from "
							+ "entity_course_active a,entity_course_info c,entity_teachplan_course tc,entity_teachplan_info i,entity_student_info s,entity_teach_class etc where a.id in ("
							+ subSql
							+ ")  "
							+ "and tc.course_id = c.id and a.course_id = c.id and i.edutype_id = s.edutype_id and i.major_id = s.major_id and i.grade_id = s.grade_id and a.id = etc.open_course_id and s.id = '"
							+ student_id
							+ "' "
							+ "and tc.teachplan_id = i.id)a,(select a.course_id as course_id,c.credit  from entity_course_info c,entity_course_active a where a.id in ("
							+ subSql
							+ ") and a.course_id = c.id)b "
							+ "where b.course_id = a.course_id(+))e,(select type1_value as creditfee from entity_userfee_level where user_id = '"
							+ student_id + "')f) ";
					sqlList.add(insertsql);
					sqlList.add(feeSql);
					UserAddLog
							.setDebug("OracleElectiveActivity.electiveCoursesByUserId(String[] idSet, String semester_id,String student_id) SQL1="
									+ insertsql
									+ " SQL2="
									+ feeSql
									+ " DATE="
									+ new Date());
				}
				db.executeUpdateBatch(sqlList);
			}
		}

		db.close(rs);
	}

	private String getTeachClassesStr(List teachClasses) {
		String teachClassesStr = "";
		for (int i = 0; i < teachClasses.size(); i++) {
			teachClassesStr = "'" + teachClassesStr
					+ ((TeachClass) teachClasses.get(i)).getId() + "',";
		}
		if (teachClassesStr.length() > 1) {
			teachClassesStr = teachClassesStr.substring(0, teachClassesStr
					.length() - 1);
		}
		return teachClassesStr;
	}

	public int checkElectiveByFee(String regNo, String ssoUserId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		MyResultSet rss = null;
		String edutype_id = "";
		String major_id = "";
		String grade_id = "";
		String semester_id = "";
		String id = "";
		String site_id = "";
		double fee = 0;
		User user = new User();
		user.setId(ssoUserId);
		OracleUserFee userFee = new OracleUserFee(user);
		double leftFee = userFee.getUserTotalFee();

		try {
			OracleRecruitStudent student = new OracleRecruitStudent();
			student = (OracleRecruitStudent) student.getRecruitInfo1(regNo);
			edutype_id = student.getEduInfo().getEdutype_id();
			major_id = student.getEduInfo().getMajor_id();
			grade_id = student.getEduInfo().getGrade_id();
			site_id = student.getEduInfo().getSite_id();
			id = student.getId();
		} catch (Exception e1) {
			
		}

		String sql = "select a.credit*b.fee_level as fee from (select sum(e.credit) as credit from entity_teachplan_info a,entity_executeplan_info b,entity_executeplan_group c,"
				+ "entity_executeplan_course d,entity_teachplan_course e,entity_course_active f,entity_teach_class g "
				+ "where a.id=b.teachplan_id and b.id=c.executeplan_id and c.id=d.group_id and "
				+ "d.teachplan_course_id=e.id and a.id=e.teachplan_id and e.course_id=f.course_id and f.id=g.open_course_id and "
				+ "f.semester_id=b.semester_id and a.grade_id='"
				+ grade_id
				+ "' and a.major_id='"
				+ major_id
				+ "' and a.edutype_id='"
				+ edutype_id
				+ "' and b.semester_id in (select id from entity_semester_info where selected = '1'))a,(select type1_value as fee_level from entity_recruit_userfee_level where user_id='"
				+ id + "')b ";
		rss = db.executeQuery(sql);
		try {
			if (rss != null && rss.next()) {
				fee = rss.getDouble("fee");
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rss);
		}
		int i = 0;
		if (leftFee >= fee)
			i = 1;

		return i;
	}

	public int confirmElective(String[] selectIds, String semester_id,
			String student_id) throws PlatformException {
		String electiveIdStr = "";
		ArrayList sqlgroup = new ArrayList();

		dbpool db = new dbpool();

		for (int i = 0; i < selectIds.length; i++) {
			electiveIdStr += selectIds[i] + ",";
		}

		if (electiveIdStr.length() > 0)
			electiveIdStr = electiveIdStr.substring(0,
					electiveIdStr.length() - 1);

		String sql = "update entity_elective set status = '1' where id in ("
				+ electiveIdStr + ")";
		sqlgroup.add(sql);
		// String feeSql = "insert into
		// entity_userfee_record(id,user_id,fee_value,fee_type,payout_type,checked,note,teachclass_id)
		// "
		// + "select s_entity_fee_id.nextval,student_id,value,'"
		// + FeeType.CREDIT
		// + "','"
		// + PayoutType.CONSUME
		// + "','1',note,teachclass_id "
		// + "from (select -(e.credit* f.creditfee) as value,f.student_id as
		// student_id,concat(concat(concat(concat(concat(concat('ѡ��
		// �γ���ƣ�','('||e.course_id||')'||e.course_name),';�γ�ѧ�֣�'),e.credit),';ѧ�ֱ�׼��ÿѧ�֣�'),f.creditfee),'Ԫ')
		// as note,teachclass_id "
		// + "from (select nvl(a.credit,b.credit) as
		// credit,a.course_id,b.course_name,b.student_id,b.teachclass_id from "
		// + "(select tc.credit,tc.course_id,c.name as course_name from
		// entity_course_active a,"
		// + "entity_course_info c,entity_teachplan_course
		// tc,entity_teachplan_info i,entity_student_info s,"
		// + "entity_elective e,entity_teach_class t where e.id in ("
		// + electiveIdStr
		// + ") and tc.course_id = c.id and a.course_id = c.id "
		// + "and i.edutype_id = s.edutype_id and i.major_id = s.major_id and
		// i.grade_id = s.grade_id and s.id = e.student_id and "
		// + "tc.teachplan_id = i.id and a.id = t.open_course_id and t.id =
		// e.teachclass_id)a,(select c.name as course_name,a.course_id as
		// course_id,c.credit "
		// + ",e.student_id,t.id as teachclass_id from entity_course_info
		// c,entity_course_active a,entity_teach_class t,entity_elective e where
		// a.id = t.open_course_id and "
		// + "a.course_id = c.id and e.teachclass_id = t.id and e.id in ("
		// + electiveIdStr
		// + "))b where b.course_id = a.course_id(+))e,"
		// + "(select distinct type1_value as creditfee,e.student_id from
		// entity_userfee_level f,entity_elective e "
		// + "where f.user_id = e.student_id and e.id in ("
		// + electiveIdStr + "))f where e.student_id = f.student_id) ";
		String feeSql = "insert into entity_userfee_record(id,user_id,fee_value,fee_type,payout_type,checked,note,teachclass_id) "
				+ "select s_entity_fee_id.nextval,student_id,value,'"
				+ FeeType.CREDIT
				+ "','"
				+ PayoutType.CONSUME
				+ "','1',note,teachclass_id "
				+ "from (select -(e.credit* f.creditfee) as value,f.student_id as student_id,concat(concat(concat(concat(concat(concat('ѡ�� �γ���ƣ�',"
				+ "'('||e.course_id||')'||e.course_name),';�γ�ѧ�֣�'),e.credit),';ѧ�ֱ�׼��ÿѧ�֣�'),f.creditfee),'Ԫ') as note,teachclass_id "
				+ "from (select nvl(a.single_credit ,nvl(a.credit,b.credit)) as credit,b.course_id,b.course_name,b.student_id,b.teachclass_id from "
				+ "(select tc.credit,tc.course_id,c.name as course_name,st.credit as single_credit  from entity_course_active a,"
				+ "entity_course_info c,entity_teachplan_course tc,"
				+ "entity_teachplan_info i,(select sic.student_id,a.course_id,sic.credit from entity_singleteachplan_course sic,entity_course_active a, "
				+ "entity_teach_class   c where a.id = c.open_course_id and c.id in ('"
				+ electiveIdStr
				+ "') and sic.course_id=a.course_id  ) st,"
				+ " (select a.id,major_id,grade_id,edutype_id from entity_student_info a, entity_register_info b "
				+ "where a.isgraduated = '0' and a.status = '0' and a.id = b.user_id and a.id = '"
				+ student_id
				+ "' and"
				+ " b.semester_id = '"
				+ semester_id
				+ "') s,"
				+ "entity_elective e,entity_teach_class t where e.id in ("
				+ electiveIdStr
				+ ")  and tc.course_id = c.id and a.course_id = c.id "
				+ "and i.edutype_id = s.edutype_id and i.major_id = s.major_id and i.grade_id = s.grade_id and s.id = e.student_id and "
				+ "tc.teachplan_id = i.id and a.id = t.open_course_id and t.id = e.teachclass_id   and s.id = st.student_id(+))a,(select  c.name as course_name,a.course_id as course_id,c.credit "
				+ ",e.student_id,t.id as teachclass_id from entity_course_info c,entity_course_active a,entity_teach_class t,entity_elective e where a.id = t.open_course_id and "
				+ "a.course_id = c.id and e.teachclass_id = t.id and e.id in ("
				+ electiveIdStr
				+ "))b where b.course_id = a.course_id(+))e,"
				+ "(select distinct type1_value as creditfee,e.student_id from entity_userfee_level f,entity_elective e "
				+ "where f.user_id = e.student_id and e.id in ("
				+ electiveIdStr
				+ "))f  where e.student_id = f.student_id and e.student_id ='"
				+ student_id + "')";
		sqlgroup.add(feeSql);
		return db.executeUpdateBatch(sqlgroup);
	}

	public int unConfirmElective(String[] selectIds, String semester_id,
			String student_id) throws PlatformException {
		String electiveIdStr = "";
		ArrayList sqlgroup = new ArrayList();

		dbpool db = new dbpool();

		for (int i = 0; i < selectIds.length; i++) {
			electiveIdStr += selectIds[i] + ",";
		}

		if (electiveIdStr.length() > 0)
			electiveIdStr = electiveIdStr.substring(0,
					electiveIdStr.length() - 1);

		String sql = "update entity_elective set status = '0' where id in ("
				+ electiveIdStr + ")";
		sqlgroup.add(sql);

		String updateSql = "insert into entity_userfee_record (id,user_id,date_time,fee_value,fee_type,payout_type,note,checked,invoice_status,teachclass_id)  select s_entity_fee_id.nextval, b.user_id,sysdate,abs(b.fee_value),b.fee_type,b.payout_type,b.note,b.checked,b.invoice_status,b.teachclass_id from (select distinct src.user_id,sysdate,src.fee_value,src.fee_type,'"
				+ PayoutType.ROLLBACK
				+ "' as PAYOUT_TYPE,src.note,src.checked,src.invoice_status,src.teachclass_id from entity_userfee_record src where src.user_id = '"
				+ student_id
				+ "' and src.teachclass_id  in ("
				+ electiveIdStr
				+ ")   and src.payout_type = '" + PayoutType.CONSUME + "') b";
		sqlgroup.add(updateSql);

		return db.executeUpdateBatch(sqlgroup);
	}

	public void electiveWithFee(String[] selectIds, String[] allIds,
			String semester_id, String site_id, String edu_type_id,
			String major_id, String grade_id) throws PlatformException {

		dbpool db = new dbpool();
		MyResultSet rs = null;
		String courseactive = "";
		String sql;
		String feeUpdateSql = "";
		String delsql;

		// �õ����п�ѡ��teachclass_id
		String teachclass_str = "";
		for (int i = 0; i < allIds.length; i++) {
			teachclass_str += "'" + allIds[i] + "',";
		}
		if (teachclass_str.length() > 0)
			teachclass_str = teachclass_str.substring(0, teachclass_str
					.length() - 1);

		List sqlgroup = new ArrayList();
		if (selectIds == null) {
			if (site_id != null && !site_id.equals("null")
					&& !site_id.equals("")) {
				sql = "delete from entity_elective where student_id in "
						+ "(select id from entity_student_info where site_id = '"
						+ site_id + "' " + "and edutype_id = '" + edu_type_id
						+ "' and major_id = '" + major_id
						+ "' and grade_id = '" + grade_id + "') "
						+ "and teachclass_id in (" + teachclass_str + ")";

				feeUpdateSql = "insert into entity_userfee_record (id,user_id,date_time,fee_value,fee_type,payout_type,note,checked,invoice_status ,teachclass_id) "
						+ "select s_entity_fee_id.nextval, b.user_id, sysdate,  abs(b.fee_value), b.fee_type, b.PAYOUT_TYPE as payout_type, b.note, b.checked,  "
						+ "b.invoice_status, b.teachclass_id from (select distinct src.user_id, sysdate,  src.fee_value, src.fee_type, '"
						+ PayoutType.ROLLBACK
						+ "' as PAYOUT_TYPE, src.note,  src.checked, src.invoice_status, src.teachclass_id from entity_userfee_record  src, "
						+ "(select c.id as teach_class_id, b.id as userid from entity_course_active a,  entity_teach_class c, entity_elective e, "
						+ "(select id from entity_student_info  where site_id = '"
						+ site_id
						+ "' and edutype_id = '"
						+ edu_type_id
						+ "' and major_id =  '"
						+ major_id
						+ "' and grade_id = '"
						+ grade_id
						+ "') b where c.open_course_id = a.id  and e.teachclass_id = c.id and e.student_id = b.id and e.teachclass_id in ("
						+ teachclass_str
						+ ")) a "
						+ "where src.user_id in (a.userid) and src.teachclass_id in  (a.teach_class_id)  and src.payout_type = '"
						+ PayoutType.CONSUME + "') b";
				sqlgroup.add(feeUpdateSql);
				sqlgroup.add(sql);
			} else {
				sql = "delete from entity_elective where student_id in "
						+ "(select id from entity_student_info where  "
						+ "edutype_id = '" + edu_type_id + "' and major_id = '"
						+ major_id + "' and grade_id = '" + grade_id + "') "
						+ "and teachclass_id in (" + teachclass_str + ")";

				feeUpdateSql = "insert into entity_userfee_record (id,user_id,date_time,fee_value,fee_type,payout_type,note,checked,invoice_status ,teachclass_id) "
						+ "select s_entity_fee_id.nextval, b.user_id, sysdate,  abs(b.fee_value), b.fee_type, b.PAYOUT_TYPE as payout_type, b.note, b.checked,  "
						+ "b.invoice_status, b.teachclass_id from (select distinct src.user_id, sysdate,  src.fee_value, src.fee_type, '"
						+ PayoutType.ROLLBACK
						+ "' as PAYOUT_TYPE, src.note,  src.checked, src.invoice_status, src.teachclass_id from entity_userfee_record  src, "
						+ "(select c.id as teach_class_id, b.id as userid from entity_course_active a,  entity_teach_class c, entity_elective e, "
						+ "(select id from entity_student_info  where  edutype_id = '"
						+ edu_type_id
						+ "' and major_id =  '"
						+ major_id
						+ "' and grade_id = '"
						+ grade_id
						+ "') b where c.open_course_id = a.id  and e.teachclass_id = c.id and e.student_id = b.id and e.teachclass_id in ("
						+ teachclass_str
						+ ")) a where src.user_id in (a.userid) and src.teachclass_id in  (a.teach_class_id) and src.payout_type = '"
						+ PayoutType.CONSUME + "') b";

				sqlgroup.add(feeUpdateSql);
				sqlgroup.add(sql);
			}
			db.executeUpdateBatch(sqlgroup);

		} else {
			for (int i = 0; i < selectIds.length; i++) {
				courseactive = courseactive + "'" + selectIds[i] + "',";
			}
			courseactive = courseactive.substring(0, courseactive.length() - 1);
			if (site_id == null || site_id.equals("null") || site_id.equals("")) {
				delsql = "delete from entity_elective where teachclass_id in ("
						+ teachclass_str
						+ ")"
						+ " and teachclass_id not in("
						+ courseactive
						+ " )"
						+ "and student_id in (select id from entity_student_info where  major_id = '"
						+ major_id + "' " + "and grade_id = '" + grade_id
						+ "' and edutype_id = '" + edu_type_id
						+ "' and isgraduated = '0' and status = '0') ";

				// ��ԭ�ȵ��޸ķ��ü�¼״̬��Ϊ�����������ü�¼�����һ���˷��ü�¼

				feeUpdateSql = "insert into entity_userfee_record (id,user_id,date_time,fee_value,fee_type,payout_type,note,checked,invoice_status,teachclass_id) "
						+ " select  s_entity_fee_id.nextval,   b.user_id,  sysdate,   abs(b.fee_value),  b.fee_type,    b.payout_type,    b.note,    b.checked,    "
						+ "b.invoice_status,  b.teachclass_id from (select distinct src.user_id, sysdate,  src.fee_value,  src.fee_type,   "
						+ "'ROLLBACK'    as   payout_type,    src.note,   src.checked,  src.invoice_status,  src.teachclass_id   from  entity_userfee_record   src,  "
						+ "(select t.id as teachclass_id, stu.id as student_id from entity_teach_class  t,   entity_course_active   a,   entity_elective   e,   "
						+ "(select   id   from  entity_student_info where   major_id = '"
						+ major_id
						+ "' and grade_id  = '"
						+ grade_id
						+ "' and edutype_id  = '"
						+ edu_type_id
						+ "' and isgraduated  = '0' and status  = '0') stu  where t.id in ("
						+ teachclass_str
						+ ") and t.open_course_id = a.id and t.id not in("
						+ courseactive
						+ ") and e.teachclass_id  =  t.id   and  e.student_id   =  stu.id)   a  where  "
						+ " src.teachclass_id  in  (a.teachclass_id) and src.user_id   in (a.student_id) and  src.payout_type = 'CONSUME') b";

				sqlgroup.add(feeUpdateSql);
				sqlgroup.add(delsql);
				// db.executeUpdate(delsql);
				for (int i = 0; i < selectIds.length; i++) {

					sql = "insert into entity_elective (id,student_id,teachclass_id,status) (select s_elective_id.nextval,id,"
							+ selectIds[i]
							+ ",'1'  "
							+ "from (select a.id as id from entity_student_info a,entity_register_info b "
							+ "where a.id = b.user_id and b.semester_id = '"
							+ semester_id
							+ "' and a.major_id = '"
							+ major_id
							+ "' and a.edutype_id = '"
							+ edu_type_id
							+ "' "
							+ "and a.grade_id = '"
							+ grade_id
							+ "'  and a.isgraduated = '0' and a.status='0' ) "
							+ "where id not in (select student_id from entity_elective where teachclass_id = '"
							+ selectIds[i] + "'))";
					// db.executeUpdate(sql);
					// System.out.print(sql);

					/*
					 * ���SQLû�п����Ƿ�ѧ��ע�� *String stuSql = "select id from
					 * entity_student_info where major_id = '" + major_id + "'
					 * and grade_id = '" + grade_id + "' and edutype_id = '" +
					 * edu_type_id + "' and isgraduated='0' and status = '0' " +
					 * "minus select student_id from entity_elective where
					 * teachclass_id = '" + selectIds[i] + "'";
					 */
					// String stuSql = "select a.id from entity_student_info a,
					// entity_register_info b where a.major_id = '"
					// + major_id
					// + "' and a.grade_id = '"
					// + grade_id
					// + "' and a.edutype_id = '"
					// + edu_type_id
					// + "' and a.isgraduated='0' and a.status = '0' and
					// a.id=b.user_id and b.semester_id='"
					// + semester_id
					// + "' "
					// + "minus select student_id from entity_elective where
					// teachclass_id = '"
					// + selectIds[i] + "'";
					//
					// String subSql = "select a.id from entity_course_active
					// a,entity_teach_class c where a.id = c.open_course_id and
					// c.id = '"
					// + selectIds[i] + "'";
					// String feeSql = "insert into
					// entity_userfee_record(id,user_id,fee_value,fee_type,payout_type,checked,note,teachclass_id)
					// "
					// + "select s_entity_fee_id.nextval,student_id,value,'"
					// + FeeType.CREDIT
					// + "','"
					// + PayoutType.CONSUME
					// + "','1',note,teachclass_id "
					// + "from (select distinct -(e.credit* f.creditfee) as
					// value,concat(concat(concat(concat(concat(concat('ѡ��
					// �γ���ƣ�', "
					// +
					// "'('||e.course_id||')'||e.course_name),';�γ�ѧ�֣�'),e.credit),';ѧ�ֱ�׼��ÿѧ�֣�'),f.creditfee),'Ԫ')
					// as note,"
					// + "teachclass_id,student_id "
					// + "from (select nvl(a.credit,b.credit) as
					// credit,a.course_id,a.course_name
					// ,b.student_id,b.teachclass_id from "
					// + "(select tc.credit,tc.course_id,c.name as course_name
					// ,s.id as student_id from "
					// + "entity_course_active a,entity_course_info
					// c,entity_teachplan_course tc,entity_teachplan_info
					// i,entity_student_info s "
					// + "where a.id in ("
					// + subSql
					// + ") "
					// + "and tc.course_id = c.id and a.course_id = c.id and
					// i.edutype_id = s.edutype_id and i.major_id = s.major_id "
					// + "and i.grade_id = s.grade_id "
					// + "and tc.teachplan_id = i.id) a,(select a.course_id as
					// course_id,c.credit, e.student_id, t.id as teachclass_id "
					// + "from entity_course_info c,entity_course_active a
					// ,entity_teach_class t, entity_elective e where a.id =
					// t.open_course_id "
					// + "and a.course_id = c.id and e.teachclass_id = t.id and
					// a.id in ("
					// + subSql
					// + ") )b "
					// + "where b.course_id = a.course_id(+) and a.student_id =
					// b.student_id )e,(select type1_value as creditfee,user_id
					// from entity_userfee_level) f,("
					// + stuSql
					// + ") g where "
					// + "g.id = f.user_id and g.id = e.student_id) ";
					String feeSql = "insert into entity_userfee_record(id,user_id,fee_value,fee_type,payout_type,checked,note,teachclass_id) select s_entity_fee_id.nextval, student_id, value, '"
							+ FeeType.CREDIT
							+ "', '"
							+ PayoutType.CONSUME
							+ "', '1', note, '"
							+ selectIds[i]
							+ "' "
							+ "from (select distinct - (e.credit * f.creditfee) as value, concat(concat(concat(concat(concat(concat('ѡ�� �γ���ƣ�', "
							+ "'(' || e.course_id || ')' || e.course_name), ';�γ�ѧ�֣�'), e.credit), ';ѧ�ֱ�׼��ÿѧ�֣�'), f.creditfee), 'Ԫ') as note ,"
							+ " student_id from (select nvl(a.single_credit,nvl(a.credit, a.default_credit)) as credit, a.course_id, a.course_name, a.student_id from"
							+ " (select tc.credit, tc.course_id, c.credit as default_credit, c.name as course_name, s.id as student_id , st.credit as single_credit  "
							+ "from entity_course_active    a, entity_course_info      c, entity_teachplan_course tc, entity_teachplan_info   i, "
							+ "(select sic.student_id,a.course_id,sic.credit from entity_singleteachplan_course sic,entity_course_active a, entity_teach_class   c "
							+ "where a.id = c.open_course_id and c.id = '"
							+ selectIds[i]
							+ "' and sic.course_id=a.course_id  ) st, "
							+ "(select a.id,major_id,grade_id,edutype_id from entity_student_info a, entity_register_info b where a.major_id = '"
							+ major_id
							+ "' "
							+ "and a.grade_id = '"
							+ grade_id
							+ "'   and a.edutype_id = '"
							+ edu_type_id
							+ "' "
							+ "and a.isgraduated = '0' and a.status = '0' and a.id = b.user_id and b.semester_id = '"
							+ semester_id
							+ "') s where a.id = (select a.id from entity_course_active a, "
							+ "entity_teach_class   c where a.id = c.open_course_id and c.id = '"
							+ selectIds[i]
							+ "') and tc.course_id = c.id and a.course_id = c.id "
							+ "and i.edutype_id = s.edutype_id and i.major_id = s.major_id and i.grade_id = s.grade_id and tc.teachplan_id = i.id and s.id = st.student_id(+) ) a) e,"
							+ " (select type1_value as creditfee, user_id from entity_userfee_level) f, (select a.id from entity_student_info a, "
							+ "entity_register_info b where a.major_id = '"
							+ major_id
							+ "' and a.grade_id = '"
							+ grade_id
							+ "'   and a.edutype_id = '"
							+ edu_type_id
							+ "' "
							+ "and a.isgraduated = '0' and a.status = '0' and a.id = b.user_id and b.semester_id = '"
							+ semester_id
							+ "' minus "
							+ "select student_id from entity_elective where teachclass_id = '"
							+ selectIds[i]
							+ "') g where g.id = f.user_id and g.id = e.student_id)";

					sqlgroup.add(feeSql);
					sqlgroup.add(sql);

				}
				db.executeUpdateBatch(sqlgroup);
			} else {

				/*
				 * �˴��õ��Ĳ�ѡ�Ŀγ������⣬Ӧ����ҳ�洫��4�Ĳ���Ŷ�
				 * 
				 */
				delsql = "delete from entity_elective where teachclass_id in ("
						+ teachclass_str
						+ ")"
						+ " and teachclass_id not in("
						+ courseactive
						+ " ) and student_id in (select id from entity_student_info where  site_id='"
						+ site_id + "' and major_id = '" + major_id + "' "
						+ "and grade_id = '" + grade_id
						+ "' and edutype_id = '" + edu_type_id
						+ "' and isgraduated = '0' and status = '0') ";

				// ��ԭ�ȵ��޸ķ��ü�¼״̬��Ϊ�����������ü�¼�����һ���˷��ü�¼

				feeUpdateSql = "insert into entity_userfee_record (id,user_id,date_time,fee_value,fee_type,payout_type,note,"
						+ "checked,invoice_status,teachclass_id)  select  s_entity_fee_id.nextval,   "
						+ "b.user_id,  sysdate,   abs(b.fee_value),  b.fee_type,    b.payout_type,    "
						+ "b.note,    b.checked,    b.invoice_status,  b.teachclass_id from "
						+ "(select distinct src.user_id, sysdate,  src.fee_value,  src.fee_type,   'ROLLBACK'    as   payout_type,  "
						+ "  src.note,   src.checked,  src.invoice_status,  src.teachclass_id   from  entity_userfee_record   src,  "
						+ "(select t.id as teachclass_id, stu.id as student_id from entity_teach_class  t,   entity_course_active   a,  "
						+ " entity_elective   e,   (select   id   from  entity_student_info "
						+ "where site_id = '"
						+ site_id
						+ "' and major_id = '"
						+ major_id
						+ "' and grade_id  = '"
						+ grade_id
						+ "' "
						+ "and edutype_id  = '"
						+ edu_type_id
						+ "' and isgraduated  = '0' and status  = '0') stu  "
						+ "where t.id in ("
						+ teachclass_str
						+ ") and t.open_course_id = a.id and t.id not in("
						+ courseactive
						+ ") and e.teachclass_id  =  t.id   and  e.student_id   =  stu.id)   a  where   src.teachclass_id  in  (a.teachclass_id) and src.user_id   "
						+ "in (a.student_id)��and src.payout_type ='CONSUME') b";

				sqlgroup.add(feeUpdateSql);
				sqlgroup.add(delsql);

				for (int i = 0; i < selectIds.length; i++) {

					sql = "insert into entity_elective (id,student_id,teachclass_id,status) (select s_elective_id.nextval,id,"
							+ selectIds[i]
							+ ",'1'  "
							+ "from (select a.id as id from entity_student_info a,entity_register_info b "
							+ "where a.id = b.user_id and b.semester_id = '"
							+ semester_id
							+ "' and a.site_id='"
							+ site_id
							+ "' and a.major_id = '"
							+ major_id
							+ "' and a.edutype_id = '"
							+ edu_type_id
							+ "' "
							+ "and a.grade_id = '"
							+ grade_id
							+ "'  and a.isgraduated = '0' and a.status='0') "
							+ "where id not in (select student_id from entity_elective where teachclass_id = '"
							+ selectIds[i] + "'))";
					// String stuSql = "select a.id from entity_student_info a,
					// entity_register_info b where a.major_id = '"
					// + major_id
					// + "' and a.grade_id = '"
					// + grade_id
					// + "' and a.site_id = '"
					// + site_id
					// + "' and a.edutype_id = '"
					// + edu_type_id
					// + "' and a.isgraduated='0' and a.status = '0' and
					// a.id=b.user_id and b.semester_id='"
					// + semester_id
					// + "' "
					// + "minus select student_id from entity_elective where
					// teachclass_id = '"
					// + selectIds[i] + "'";
					//
					// String subSql = "select a.id from entity_course_active
					// a,entity_teach_class c where a.id = c.open_course_id and
					// c.id = '"
					// + selectIds[i] + "'";
					// String feeSql = "insert into
					// entity_userfee_record(id,user_id,fee_value,fee_type,payout_type,checked,note,teachclass_id)
					// "
					// + "select s_entity_fee_id.nextval,student_id,value,'"
					// + FeeType.CREDIT
					// + "','"
					// + PayoutType.CONSUME
					// + "','1',note,teachclass_id "
					// + "from (select distinct -(e.credit* f.creditfee) as
					// value,concat(concat(concat(concat(concat(concat('ѡ��
					// �γ���ƣ�', "
					// +
					// "'('||e.course_id||')'||e.course_name),';�γ�ѧ�֣�'),e.credit),';ѧ�ֱ�׼��ÿѧ�֣�'),f.creditfee),'Ԫ')
					// as note,"
					// + "teachclass_id,student_id "
					// + "from (select nvl(a.credit,b.credit) as
					// credit,a.course_id,a.course_name
					// ,b.student_id,b.teachclass_id from "
					// + "(select tc.credit,tc.course_id,c.name as course_name
					// ,s.id as student_id from "
					// + "entity_course_active a,entity_course_info
					// c,entity_teachplan_course tc,entity_teachplan_info
					// i,entity_student_info s "
					// + "where a.id in ("
					// + subSql
					// + ") "
					// + "and tc.course_id = c.id and a.course_id = c.id and
					// i.edutype_id = s.edutype_id and i.major_id = s.major_id "
					// + "and i.grade_id = s.grade_id "
					// + "and tc.teachplan_id = i.id) a,(select a.course_id as
					// course_id,c.credit, e.student_id, t.id as teachclass_id "
					// + "from entity_course_info c,entity_course_active a
					// ,entity_teach_class t, entity_elective e where a.id =
					// t.open_course_id "
					// + "and a.course_id = c.id and e.teachclass_id = t.id and
					// a.id in ("
					// + subSql
					// + ") )b "
					// + "where b.course_id = a.course_id(+) and a.student_id =
					// b.student_id )e,(select type1_value as creditfee,user_id
					// from entity_userfee_level) f,("
					// + stuSql
					// + ") g where "
					// + "g.id = f.user_id and g.id = e.student_id) ";

					String feeSql = "insert into entity_userfee_record(id,user_id,fee_value,fee_type,payout_type,checked,note,teachclass_id) select s_entity_fee_id.nextval, student_id, value, '"
							+ FeeType.CREDIT
							+ "', '"
							+ PayoutType.CONSUME
							+ "', '1', note, '"
							+ selectIds[i]
							+ "' "
							+ "from (select distinct - (e.credit * f.creditfee) as value, concat(concat(concat(concat(concat(concat('ѡ�� �γ���ƣ�', "
							+ "'(' || e.course_id || ')' || e.course_name), ';�γ�ѧ�֣�'), e.credit), ';ѧ�ֱ�׼��ÿѧ�֣�'), f.creditfee), 'Ԫ') as note ,"
							+ " student_id from (select nvl(a.single_credit,nvl(a.credit, a.default_credit)) as credit, a.course_id, a.course_name, a.student_id from"
							+ " (select tc.credit, tc.course_id, c.credit as default_credit, c.name as course_name, s.id as student_id , st.credit as single_credit  "
							+ "from entity_course_active    a, entity_course_info      c, entity_teachplan_course tc, entity_teachplan_info   i, "
							+ "(select sic.student_id,a.course_id,sic.credit from entity_singleteachplan_course sic,entity_course_active a, entity_teach_class   c "
							+ "where a.id = c.open_course_id and c.id = '"
							+ selectIds[i]
							+ "' and sic.course_id=a.course_id  ) st, "
							+ "(select a.id,major_id,grade_id,edutype_id from entity_student_info a, entity_register_info b where a.major_id = '"
							+ major_id
							+ "' "
							+ "and a.grade_id = '"
							+ grade_id
							+ "' and a.site_id = '"
							+ site_id
							+ "' and a.edutype_id = '"
							+ edu_type_id
							+ "' "
							+ "and a.isgraduated = '0' and a.status = '0' and a.id = b.user_id and b.semester_id = '"
							+ semester_id
							+ "') s where a.id = (select a.id from entity_course_active a, "
							+ "entity_teach_class   c where a.id = c.open_course_id and c.id = '"
							+ selectIds[i]
							+ "') and tc.course_id = c.id and a.course_id = c.id "
							+ "and i.edutype_id = s.edutype_id and i.major_id = s.major_id and i.grade_id = s.grade_id and tc.teachplan_id = i.id and s.id = st.student_id(+) ) a) e,"
							+ " (select type1_value as creditfee, user_id from entity_userfee_level) f, (select a.id from entity_student_info a, "
							+ "entity_register_info b where a.major_id = '"
							+ major_id
							+ "' and a.grade_id = '"
							+ grade_id
							+ "' and a.site_id = '"
							+ site_id
							+ "' and a.edutype_id = '"
							+ edu_type_id
							+ "' "
							+ "and a.isgraduated = '0' and a.status = '0' and a.id = b.user_id and b.semester_id = '"
							+ semester_id
							+ "' minus "
							+ "select student_id from entity_elective where teachclass_id = '"
							+ selectIds[i]
							+ "') g where g.id = f.user_id and g.id = e.student_id)";

					sqlgroup.add(feeSql);
					sqlgroup.add(sql);

				}
				db.executeUpdateBatch(sqlgroup);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.ElectiveActivity#electiveWithFee(java.lang.String[],
	 *      java.lang.String[], java.lang.String, java.lang.String)
	 */
	public void electiveWithFee(String[] selectIds, String[] allIds,
			String semester_id, String student_id) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList sqlList = new ArrayList();

		// ȡ��������ѡ��teachclass_id
		String selected = "";
		for (int i = 0; i < selectIds.length; i = i + 1) {
			if (selectIds[i].equals("alldelete")) {
				selected = "";
				break;
			} else {
				selected = selected + "'" + selectIds[i] + "',";
			}
		}
		if (selected.length() > 0)
			selected = selected.substring(0, selected.length() - 1);
		// �õ����п�ѡ��teachclass_id
		String teachclass_str = "";
		for (int i = 0; allIds!=null&&i < allIds.length; i++) {
			teachclass_str += "'" + allIds[i] + "',";
		}
		if (teachclass_str.length() > 0)
			teachclass_str = teachclass_str.substring(0, teachclass_str
					.length() - 1);

		if (selected.equals("")) {
			selected = "''";
			List list = new ArrayList();
			String delsql = "delete from entity_elective where student_id='"
					+ student_id + "' " + "and teachclass_id in ("
					+ teachclass_str + ")";

			// ��ԭ�ȵ��޸ķ��ü�¼״̬��Ϊ�����������ü�¼�����һ���˷��ü�¼
			String insertSql = "insert into entity_userfee_record (id,user_id,date_time,fee_value,fee_type,payout_type,note,checked,invoice_status,teachclass_id) "
					+ " select s_entity_fee_id.nextval, b.user_id,sysdate,abs(b.fee_value),b.fee_type,b.payout_type,b.note,b.checked,b.invoice_status,b.teachclass_id from "
					+ "(select  distinct src.user_id,sysdate,src.fee_value,src.fee_type,'"
					+ PayoutType.ROLLBACK
					+ "' as PAYOUT_TYPE,src.note,src.checked,src.invoice_status,src.teachclass_id from entity_userfee_record src,entity_elective ee where src.user_id = '"
					+ student_id
					+ "'   and src.user_id = ee.student_id and src.teachclass_id = ee.teachclass_id and src.teachclass_id in ("
					+ teachclass_str
					+ ") and src.payout_type = '"
					+ PayoutType.CONSUME + "') b";

			list.add(insertSql);
			list.add(delsql);

			UserDeleteLog
					.setDebug("com.whaty.platform.entity.activity.ElectiveActivity#electiveWithFee(java.lang.String[], java.lang.String[], java.lang.String, java.lang.String)  SQL="
							+ delsql
							+ "/ insertSql="
							+ insertSql
							+ " DATE="
							+ new Date());
			db.executeUpdateBatch(list);
		} else {
			String deletesql = "delete from entity_elective where student_id='"
					+ student_id + "' and teachclass_id not in (" + selected
					+ ") and teachclass_id in (" + teachclass_str + ")";
			UserDeleteLog
					.setDebug("com.whaty.platform.entity.activity.ElectiveActivity#electiveWithFee(java.lang.String[], java.lang.String[], java.lang.String, java.lang.String) SQL="
							+ deletesql + " DATE=" + new Date());

			// ��ԭ�ȵ��޸ķ��ü�¼״̬��Ϊ�����������ü�¼�����һ���˷��ü�¼
			String updateSql = "insert into entity_userfee_record (id,user_id,date_time,fee_value,fee_type,payout_type,note,checked,invoice_status,teachclass_id)  select s_entity_fee_id.nextval, b.user_id,sysdate,abs(b.fee_value),b.fee_type,b.payout_type,b.note,b.checked,b.invoice_status,b.teachclass_id from (select distinct src.user_id,sysdate,src.fee_value,src.fee_type,'"
					+ PayoutType.ROLLBACK
					+ "' as PAYOUT_TYPE,src.note,src.checked,src.invoice_status,src.teachclass_id from entity_userfee_record src,entity_elective ee where src.user_id = '"
					+ student_id
					+ "'   and src.user_id = ee.student_id and src.teachclass_id = ee.teachclass_id and src.teachclass_id not in ("
					+ selected
					+ ") and src.teachclass_id in ("
					+ teachclass_str
					+ ") and src.payout_type = '"
					+ PayoutType.CONSUME + "') b";

			sqlList.add(updateSql);
			sqlList.add(deletesql);

			String insertsql = "insert into entity_elective (id, student_id,teachclass_id,status) "
					+ "select s_elective_id.nextval, '"
					+ student_id
					+ "',id,'1' from entity_teach_class where id in ("
					+ selected
					+ ") and id not in (select teachclass_id from entity_elective where student_id ='"
					+ student_id + "')";

			UserAddLog
					.setDebug("com.whaty.platform.entity.activity.ElectiveActivity#electiveWithFee(java.lang.String[], java.lang.String[], java.lang.String, java.lang.String)  SQL="
							+ insertsql + " DATE=" + new Date());

			String feeSql = "insert into entity_userfee_record(id,user_id,fee_value,fee_type,payout_type,checked,note,TEACHCLASS_ID) select s_entity_fee_id.nextval, '"
					+ student_id
					+ "', value, '"
					+ FeeType.CREDIT
					+ "', '"
					+ PayoutType.CONSUME
					+ "', '1', note, "
					+ "teachclass_id from  (select distinct - (e.credit * f.creditfee) as value, concat(concat(concat(concat(concat(concat('ѡ�� �γ���ƣ�', "
					+ "'(' || e.course_id || ')' || e.course_name), ';�γ�ѧ�֣�'), e.credit), ';ѧ�ֱ�׼��ÿѧ�֣�'), f.creditfee), 'Ԫ') as note, "
					+ "teachclass_id from (select nvl(a.single_credit,nvl(a.credit, b.credit)) as credit, a.course_id, a.course_name, teachclass_id,"
					+ " a.student_id from (select tc.credit, tc.course_id, c.name as course_name, etc.id as teachclass_id, s.id as student_id,"
					+ "st.credit as single_credit from entity_course_active a, entity_course_info c, entity_teachplan_course tc, entity_teachplan_info i,"
					+ " (select sic.student_id, a.course_id, sic.credit from entity_singleteachplan_course sic, entity_course_active          a, entity_teach_class "
					+ "  c where a.id = c.open_course_id and c.id in ("
					+ selected
					+ ") and sic.course_id = a.course_id) st, "
					+ "(select a.id, major_id, grade_id, edutype_id from entity_student_info  a, entity_register_info b where a.isgraduated = '0' "
					+ "and a.status = '0' and a.id = b.user_id and a.id = '"
					+ student_id
					+ "') s, entity_teach_class etc where etc.id in ("
					+ selected
					+ ") and "
					+ "etc.id not in (select teachclass_id from entity_elective where student_id = '"
					+ student_id
					+ "') and tc.course_id = c.id and "
					+ "a.course_id = c.id and i.edutype_id = s.edutype_id and i.major_id = s.major_id and i.grade_id = s.grade_id and a.id = etc.open_course_id and tc.teachplan_id = i.id and s.id = st.student_id(+)) a, (select a.course_id as course_id, c.credit from entity_course_info   c, entity_course_active a, entity_teach_class   etc where a.id = etc.open_course_id and etc.id in ("
					+ selected
					+ ") and etc.id not in (select teachclass_id from entity_elective where student_id = '"
					+ student_id
					+ "') and a.course_id = c.id) b where b.course_id = a.course_id(+)) e, (select type1_value as creditfee, user_id from entity_userfee_level) f where e.student_id = f.user_id)";
			UserAddLog
					.setDebug("com.whaty.platform.entity.activity.ElectiveActivity#electiveWithFee(java.lang.String[], java.lang.String[], java.lang.String, java.lang.String) SQL="
							+ feeSql + " DATE=" + new Date());
			sqlList.add(feeSql);
			sqlList.add(insertsql);
			db.executeUpdateBatch(sqlList);

		}

		db.close(rs);
	}

	public void electiveWithoutFee(String[] selectIds, String[] allIds,
			String semester_id, String student_id) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList sqlList = new ArrayList();

		// ȡ��������ѡ��teachclass_id
		String selected = "";
		for (int i = 0; i < selectIds.length; i = i + 1) {
			if (selectIds[i].equals("alldelete")) {
				selected = "";
				break;
			} else {
				selected = selected + "'" + selectIds[i] + "',";
			}
		}
		if (selected.length() > 0)
			selected = selected.substring(0, selected.length() - 1);
		// �õ����п�ѡ��teachclass_id
		String teachclass_str = "";
		for (int i = 0; i < allIds.length; i++) {
			teachclass_str += "'" + allIds[i] + "',";
		}
		if (teachclass_str.length() > 0)
			teachclass_str = teachclass_str.substring(0, teachclass_str
					.length() - 1);

		if (selected.equals("")) {
			List list = new ArrayList();
			String delsql = "delete from entity_elective where status ='0' and  student_id='"
					+ student_id
					+ "' "
					+ "and teachclass_id in ("
					+ teachclass_str + ")";

			list.add(delsql);

			UserDeleteLog
					.setDebug("OracleElectiveActivity.electiveCoursesByUserId(String[] selectIds, String semester_id,String student_id) SQL="
							+ delsql + " DATE=" + new Date());
			db.executeUpdateBatch(list);
		} else {
			String deletesql = "delete from entity_elective where status ='0' and student_id='"
					+ student_id
					+ "' and teachclass_id not in "
					+ "("
					+ selected
					+ ") and teachclass_id in ("
					+ teachclass_str
					+ ")";
			UserDeleteLog
					.setDebug("OracleElectiveActivity.electiveCoursesByUserId(String[] selectIds, String semester_id,String student_id) SQL="
							+ deletesql + " DATE=" + new Date());

			sqlList.add(deletesql);
			db.executeUpdate(deletesql);

			String insertsql = "insert into entity_elective (id, student_id,teachclass_id,status) "
					+ "select s_elective_id.nextval, '"
					+ student_id
					+ "',id,'0' from entity_teach_class where id in ("
					+ selected
					+ ") and id not in (select teachclass_id from entity_elective where student_id ='"
					+ student_id + "')";

			UserAddLog
					.setDebug("OracleElectiveActivity.electiveCoursesByUserId(String[] selectIds, String semester_id,String student_id) SQL="
							+ insertsql + " DATE=" + new Date());

			sqlList.add(insertsql);
			db.executeUpdateBatch(sqlList);

		}

		db.close(rs);
	}
	
	public String fixnull(String str){
		if(str!=null&&str.trim().length()>0){
			
		}else{
			str ="alldelete";
		}
		return str;
	}

}
