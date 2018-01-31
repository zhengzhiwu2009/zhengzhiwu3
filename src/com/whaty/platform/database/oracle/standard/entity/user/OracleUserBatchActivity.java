package com.whaty.platform.database.oracle.standard.entity.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.config.SubSystemType;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleRecruitStudent;
import com.whaty.platform.database.oracle.standard.sso.OracleSsoUser;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.entity.user.Teacher;
import com.whaty.platform.entity.user.UserBatchActivity;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleUserBatchActivity implements UserBatchActivity {

	public void studentAddBatch(List studentList) throws PlatformException {
		dbpool db = new dbpool();
		ArrayList exceptionList = new ArrayList();
		String except = "";
		int count = 0;
		Calendar c = Calendar.getInstance();
		String date = Integer.toString(c.get(Calendar.YEAR)).substring(2, 4);
		for (int i = 0; i < studentList.size(); i++) {
			ArrayList sqlList = new ArrayList();
			Student student = (Student) studentList.get(i);
			/*
			 * OracleMajor major = new OracleMajor();
			 * major.setId(student.getStudentInfo().getMajor_id()); List
			 * sortList = major.getSorts(); OracleRecruitSort sort =
			 * (OracleRecruitSort) sortList.get(0); String sort_id =
			 * sort.getId(); String reg_no = date + sort_id +
			 * student.getStudentInfo().getEdutype_id() +
			 * student.getStudentInfo().getSite_id() +
			 * student.getStudentInfo().getMajor_id(); OracleStudent oStudent =
			 * new OracleStudent(); reg_no = oStudent.getNextRegNo(reg_no);
			 */
			/*
			 * String sql = "select id from entity_student_info where reg_no='" +
			 * reg_no + "'"; if (db.countselect(sql) > 0) {
			 * exceptionList.add("ѧ��" + reg_no + "�Ѵ���"); continue; }
			 */
			String sql = "select id from (select 'site'||id as id from entity_site_info where id='"
					+ student.getStudentInfo().getSite_id()
					+ "' union select 'grade'||id as id from entity_grade_info where id='"
					+ student.getStudentInfo().getGrade_id()
					+ "' union select 'edutype'||id as id from entity_edu_type where id='"
					+ student.getStudentInfo().getEdutype_id()
					+ "' union select 'major'||id as id from entity_major_info where id='"
					+ student.getStudentInfo().getMajor_id() + "')";

			if (db.countselect(sql) < 4) {
				exceptionList.add("(" + student.getStudentInfo().getReg_no()
						+ ")" + student.getName() + ",վ�㡢�꼶����λ�רҵID��д���󣬲������");
				continue;
			}
			String ssoSql = "insert into sso_user (id,login_id,password,name,email,ADD_SUBSYSTEM) values (to_char(s_sso_id.nextval),'"
					+ student.getStudentInfo().getReg_no()
					+ "','"
					+ student.getPassword()
					+ "','"
					+ student.getName()
					+ "','"
					+ student.getEmail() + "','" + SubSystemType.ENTITY + "')";
			String feeSql = "insert into entity_userfee_level(user_id,type1_name,type1_value) select to_char(s_sso_id.currval),'PERCREDIT',type1_value from entity_fee_level where site_id='"
					+ student.getStudentInfo().getSite_id()
					+ "' and grade_id='"
					+ student.getStudentInfo().getGrade_id()
					+ "' and edutype_id='"
					+ student.getStudentInfo().getEdutype_id()
					+ "' and major_id='"
					+ student.getStudentInfo().getMajor_id() + "'";
			String eduSql = "insert into entity_student_info (id,name,id_card,reg_no,class_id,edutype_id,grade_id,site_id,major_id,study_form,study_status,status,entrance_date,photo_link) values "
					+ "(to_char(s_sso_id.currval),'"
					+ student.getName()
					+ "','"
					+ student.getNormalInfo().getIdcard()
					+ "','"
					+ student.getStudentInfo().getReg_no()
					+ "','"
					+ student.getStudentInfo().getClass_id()
					+ "','"
					+ student.getStudentInfo().getEdutype_id()
					+ "',"
					+ "'"
					+ student.getStudentInfo().getGrade_id()
					+ "','"
					+ student.getStudentInfo().getSite_id()
					+ "','"
					+ student.getStudentInfo().getMajor_id()
					+ "','"
					+ student.getStudentInfo().getStudy_form()
					+ "',"
					+ "'"
					+ student.getStudentInfo().getStudy_status()
					+ "','"
					+ student.getStudentInfo().getStatus()
					+ "','"
					+ student.getStudentInfo().getEntrance_date()
					+ "','"
					+ student.getStudentInfo().getPhoto_link() + "')";
			String normalSql = "insert into entity_usernormal_info (id,name,birthday,gender,idcard,edu,folk,hometown,workplace,position,title,zzmm,graduated_major,graduated_sch,graduated_time,email,phone,mobilephone,"
					+ "fax,work_address,work_postalcode,home_address,home_postalcode,degree,note) values (to_char(s_sso_id.currval),'"
					+ student.getName()
					+ "','"
					+ student.getNormalInfo().getBirthday()
					+ "','"
					+ student.getNormalInfo().getGender()
					+ "','"
					+ student.getNormalInfo().getIdcard()
					+ "','"
					+ student.getNormalInfo().getEdu()
					+ "',"
					+ "'"
					+ student.getNormalInfo().getFolk()
					+ "','"
					+ student.getNormalInfo().getHometown()
					+ "','"
					+ student.getNormalInfo().getWorkplace()
					+ "','"
					+ student.getNormalInfo().getPosition()
					+ "','"
					+ student.getNormalInfo().getTitle()
					+ "','"
					+ student.getNormalInfo().getZzmm()
					+ "',"
					+ "'"
					+ student.getNormalInfo().getGraduated_major()
					+ "','"
					+ student.getNormalInfo().getGraduated_sch()
					+ "','"
					+ student.getNormalInfo().getGraduated_time()
					+ "','"
					+ student.getNormalInfo().getEmails()
					+ "','"
					+ student.getNormalInfo().getPhones()
					+ "','"
					+ student.getNormalInfo().getMobilePhones()
					+ "',"
					+ "'"
					+ student.getNormalInfo().getFaxs()
					+ "','"
					+ student.getNormalInfo().getWork_address().getAddress()
					+ "','"
					+ student.getNormalInfo().getWork_address().getPostalcode()
					+ "','"
					+ student.getNormalInfo().getHome_address().getAddress()
					+ "',"
					+ "'"
					+ student.getNormalInfo().getHome_address().getPostalcode()
					+ "','"
					+ student.getNormalInfo().getDegree()
					+ "','"
					+ student.getNormalInfo().getNote() + "')";

			sqlList.add(ssoSql);
			sqlList.add(feeSql);
			sqlList.add(eduSql);
			sqlList.add(normalSql);
			if (db.executeUpdateBatch(sqlList) < 1) {
				exceptionList.add("(" + student.getStudentInfo().getReg_no()
						+ ")" + student.getName() + "�������");
			} else
				count++;
			UserAddLog
					.setDebug("OracleUserBatchActivity.studentAddBatch(List studentList) SQL1="
							+ ssoSql
							+ " SQL2="
							+ feeSql
							+ " SQL3="
							+ eduSql
							+ " SQL4=" + normalSql + " DATE=" + new Date());
		}
		db = null;
		if (exceptionList.size() > 0) {
			exceptionList.add("���֤���ٲ���");
			for (int i = 0; i < exceptionList.size(); i++) {
				except += (String) exceptionList.get(i) + "<br>";
			}
			if (count > 0)
				except += "�ɹ�����" + count + "��ѧ��";
			throw new PlatformException(except);
		} else {
			except += "�ɹ�����" + count + "��ѧ��";
			throw new PlatformException(except);
		}
	}

	public void studentDeleteBatch(String[] studentId) throws PlatformException {
		dbpool db = new dbpool();
		int count = 0;
		ArrayList sqlList = new ArrayList();
		ArrayList exceptionList = new ArrayList();
		String except = "";
		String regSql = "";
		String electiveSql = "";
		String ssoSql = "";
		String eduSql = "";
		String normalSql = "";
		for (int i = 0; i < studentId.length; i++) {
			regSql = "delete from entity_register_info where user_id = '"
					+ studentId[i] + "'";
			electiveSql = "delete from entity_elective where student_id = '"
					+ studentId[i] + "'";
			ssoSql = "delete from sso_user where id = '" + studentId[i] + "'";
			eduSql = "delete from entity_student_info where id = '"
					+ studentId[i] + "'";
			normalSql = "delete from entity_usernormal_info where id = '"
					+ studentId[i] + "'";

			sqlList.add(regSql);
			sqlList.add(electiveSql);
			sqlList.add(ssoSql);
			sqlList.add(eduSql);
			sqlList.add(normalSql);
			if (db.executeUpdateBatch(sqlList) < 1) {
				exceptionList.add("" + studentId[i] + "ɾ�����");
			} else
				count++;
			UserDeleteLog
					.setDebug("OracleUserBatchActivity.studentDeleteBatch(String[] studentId) SQL1="
							+ regSql
							+ " SQL2="
							+ electiveSql
							+ "SQL3=:"
							+ ssoSql
							+ " SQL4="
							+ eduSql
							+ " SQL5="
							+ normalSql
							+ " DATE=" + new Date());
		}
		db = null;
		if (exceptionList.size() > 0) {
			exceptionList.add("���֤����ɾ��");
			for (int i = 0; i < exceptionList.size(); i++) {
				except += (String) exceptionList.get(i) + "<br>";
			}
			if (count > 0)
				except += "�ɹ�ɾ��" + count + "��ѧ��";
			throw new PlatformException(except);
		} else {
			except += "�ɹ�ɾ��" + count + "��ѧ��";
			throw new PlatformException(except);
		}
	}

	public void teacherAddBatch(List teacherList) throws PlatformException {
		dbpool db = new dbpool();
		ArrayList exceptionList = new ArrayList();
		String except = "";
		int count = 0;
		for (int i = 0; i < teacherList.size(); i++) {
			ArrayList sqlList = new ArrayList();
			Teacher teacher = (Teacher) teacherList.get(i);
			if (OracleSsoUser.isExist(teacher.getTeacherInfo().getGh()) > 0) {
				exceptionList.add("����" + teacher.getTeacherInfo().getGh()
						+ ",��ͳһ��½ϵͳ�û��Ѿ����ڣ�");
				continue;
			}
			if("".equals(teacher.getTeacherInfo().getGh())) {
				exceptionList.add("���Ϊ��Ϊ�� ");
				continue;
			}
			if("".equals(teacher.getName())) {
				exceptionList.add("����Ϊ�� ");
				continue;
			}
			if("".equals(teacher.getNormalInfo().getGender())) {
				exceptionList.add("�Ա�Ϊ�� ");
				continue;
			}
			if("".equals(teacher.getPassword())) {
				exceptionList.add("����Ϊ�� ");
				continue;
			}
			if("".equals(teacher.getEmail())) {
				exceptionList.add("����Ϊ�� ");
				continue;
			}
			if("".equals(teacher.getTeacherInfo().getResearchDirection())) {
				exceptionList.add("רҵ����Ϊ�� ");
				continue;
			}

			if("".equals(teacher.getNormalInfo().getMobilePhones())) {
				exceptionList.add("�ƶ��绰Ϊ�� ");
				continue;
			}
			if("".equals(teacher.getNormalInfo().getPosition())) {
				exceptionList.add("ְ��Ϊ�� ");
				continue;
			}
			if("".equals(teacher.getNormalInfo().getTitle())) {
				exceptionList.add("ѧ��Ϊ�� ");
				continue;
			}
			String sql = "select id from entity_teacher_info where gh='"
					+ teacher.getTeacherInfo().getGh() + "'";
			if (db.countselect(sql) > 0) {
				exceptionList.add("����" + teacher.getTeacherInfo().getGh()
						+ "�Ѵ���");
				continue;
			}
			String ssoSql = "insert into sso_user (id,login_id,password,name,email,ADD_SUBSYSTEM) values "
					+ "(to_char(s_sso_id.nextval),'"
					+ teacher.getTeacherInfo().getGh()
					+ "','"
					+ teacher.getPassword()
					+ "','"
					+ teacher.getName()
					+ "','"
					+ teacher.getEmail() + "','" + SubSystemType.ENTITY + "')";
			String eduSql = "insert into entity_teacher_info (id,gh,name,password,email,phone,address,zip_code,gender,workplace,position,title,status,note,teach_level,type,work_kind,teach_time,dep_id,ID_CARD,MOBILEPHONE,RESEARCH_DIRECTION) "
					+ "values (to_char(s_sso_id.currval),'"
					+ teacher.getTeacherInfo().getGh()
					+ "','"
					+ teacher.getName()
					+ "','"
					+ teacher.getPassword()
					+ "','"
					+ teacher.getNormalInfo().getEmails()
					+ "',"
					+ "'"
					+ teacher.getNormalInfo().getPhones()
					+ "','"
					+ teacher.getNormalInfo().getWork_address().getAddress()
					+ "','"
					+ teacher.getNormalInfo().getWork_address().getPostalcode()
					+ "',"
					+ "'"
					+ teacher.getNormalInfo().getGender()
					+ "','"
					+ teacher.getNormalInfo().getWorkplace()
					+ "','"
					+ teacher.getNormalInfo().getPosition()
					+ "','"
					+ teacher.getNormalInfo().getTitle()
					+ "',"
					+ "'"
					+ teacher.getTeacherInfo().getStatus()
					+ "','"
					+ teacher.getNormalInfo().getNote()
					+ "','"
					+ teacher.getTeacherInfo().getTeach_level()
					+ "','"
					+ teacher.getTeacherInfo().getType()
					+ "','"
					+ teacher.getTeacherInfo().getWork_kind()
					+ "',"
					+ "'"
					+ teacher.getTeacherInfo().getTeach_time()
					+ "','"
					+ teacher.getTeacherInfo().getDep_id()
					+ "','"
					+ teacher.getNormalInfo().getIdcard()
					+ "','"
					+ teacher.getNormalInfo().getMobilePhones()
					+ "','"
					+ teacher.getTeacherInfo().getResearchDirection() + "')";
			sqlList.add(ssoSql);
			sqlList.add(eduSql);
			if (db.executeUpdateBatch(sqlList) < 1) {
				exceptionList.add("(" + teacher.getTeacherInfo().getGh() + ")"
						+ teacher.getName() + "�������");
			} else
				count++;
			UserAddLog
					.setDebug("OracleUserBatchActivity.teacherAddBatch(List teacherList) SQL1="
							+ ssoSql
							+ " SQL2="
							+ eduSql
							+ " DATE="
							+ new Date());
		}
		db = null;
		if (exceptionList.size() > 0) {
			exceptionList.add("���֤���ٲ���<br>");

			for (int i = 0; i < exceptionList.size(); i++) {
				except += (String) exceptionList.get(i) + "<br>";
			}
			if (count > 0)
				except += "�ɹ�����" + count + "���ʦ<br>";

			throw new PlatformException(except);
		} else {
			except += "�ɹ�����" + count + "���ʦ<br>";

			throw new PlatformException(except);
		}
	}

	public void siteTeacherAddBatch(List teacherList) throws PlatformException {
		dbpool db = new dbpool();
		ArrayList exceptionList = new ArrayList();
		String except = "";
		int count = 0;
		for (int i = 0; i < teacherList.size(); i++) {
			ArrayList sqlList = new ArrayList();
			SiteTeacher teacher = (SiteTeacher) teacherList.get(i);
			if (OracleSsoUser.isExist(teacher.getTeacherInfo().getGh()) > 0) {
				exceptionList.add("����" + teacher.getTeacherInfo().getGh()
						+ ",��ͳһ��½ϵͳ�û��Ѿ����� ");
				continue;
			}
			if("".equals(teacher.getName())) {
				exceptionList.add("����Ϊ�� ");
				continue;
			}
			if("".equals(teacher.getNormalInfo().getGender())) {
				exceptionList.add("�Ա�Ϊ�� ");
				continue;
			}
			if("".equals(teacher.getPassword())) {
				exceptionList.add("����Ϊ�� ");
				continue;
			}
			if("".equals(teacher.getEmail())) {
				exceptionList.add("����Ϊ�� ");
				continue;
			}
			if("".equals(teacher.getTeacherInfo().getResearchDirection())) {
				exceptionList.add("רҵ����Ϊ�� ");
				continue;
			}

			if("".equals(teacher.getNormalInfo().getMobilePhones())) {
				exceptionList.add("�ƶ��绰Ϊ�� ");
				continue;
			}
			if("".equals(teacher.getNormalInfo().getPosition())) {
				exceptionList.add("ְ��Ϊ�� ");
				continue;
			}
			if("".equals(teacher.getNormalInfo().getTitle())) {
				exceptionList.add("ѧ��Ϊ�� ");
				continue;
			}
			String sql = "select id from entity_siteteacher_info where gh='"
					+ teacher.getTeacherInfo().getGh() + "'";
			if (db.countselect(sql) > 0) {
				exceptionList.add("����" + teacher.getTeacherInfo().getGh()
						+ "�Ѵ���");
				continue;
			}
			String ssoSql = "insert into sso_user (id,login_id,password,name,email,ADD_SUBSYSTEM) values "
					+ "(to_char(s_sso_id.nextval),'"
					+ teacher.getTeacherInfo().getGh()
					+ "','"
					+ teacher.getPassword()
					+ "','"
					+ teacher.getName()
					+ "','"
					+ teacher.getEmail() + "','" + SubSystemType.ENTITY + "')";
			String eduSql = "insert into entity_siteteacher_info (id,site_id, gh,name,password,email,phone,address,zip_code,gender,workplace,position,title,status,note,teach_level,work_kind,teach_time,dep_id,mobilephone,research_direction,id_card,photo_link,type) "
					+ "values (to_char(s_sso_id.currval),'"
					+ teacher.getSiteId()
					+ "','"
					+ teacher.getTeacherInfo().getGh()
					+ "','"
					+ teacher.getName()
					+ "','"
					+ teacher.getPassword()
					+ "','"
					+ teacher.getEmail()
					+ "',"
					+ "'"
					+ teacher.getNormalInfo().getPhones()
					+ "','"
					+ teacher.getNormalInfo().getWork_address().getAddress()
					+ "','"
					+ teacher.getNormalInfo().getWork_address().getPostalcode()
					+ "',"
					+ "'"
					+ teacher.getNormalInfo().getGender()
					+ "','"
					+ teacher.getNormalInfo().getWorkplace()
					+ "','"
					+ teacher.getNormalInfo().getPosition()
					+ "','"
					+ teacher.getNormalInfo().getTitle()
					+ "',"
					+ "'"
					+ teacher.getTeacherInfo().getStatus()
					+ "','"
					+ teacher.getNormalInfo().getNote()
					+ "','"
					+ teacher.getTeacherInfo().getTeach_level()
					+ "','"
					+ teacher.getTeacherInfo().getWork_kind()
					+ "','"
					+ teacher.getTeacherInfo().getTeach_time()
					+ "','"
					+ teacher.getTeacherInfo().getDep_id()
					+ "','"
					+ teacher.getNormalInfo().getMobilePhones()
					+ "','"
					+ teacher.getTeacherInfo().getResearchDirection()
					+ "','"
					+ teacher.getNormalInfo().getIdcard()
					+ "','"
					+ teacher.getTeachereduInfo().getPhoto_link()
					+ "','"
					+ teacher.getTeacherInfo().getType() + "')";

			sqlList.add(ssoSql);
			sqlList.add(eduSql);
			if (db.executeUpdateBatch(sqlList) < 1) {
				exceptionList.add("(" + teacher.getTeacherInfo().getGh() + ")"
						+ teacher.getName() + "�������");
			} else
				count++;
			UserAddLog
					.setDebug("OracleUserBatchActivity.siteTeacherAddBatch(List teacherList) SQL1="
							+ ssoSql
							+ " SQL2="
							+ eduSql
							+ " DATE="
							+ new Date());
		}
		db = null;
		if (exceptionList.size() > 0) {
			exceptionList.add("���֤���ٲ���<br>");

			for (int i = 0; i < exceptionList.size(); i++) {
				except += (String) exceptionList.get(i) + "<br>";
			}
			if (count > 0)
				except += "�ɹ�����" + count + "���ʦ<br>";

			throw new PlatformException(except);
		} else {
			except += "�ɹ�����" + count + "���ʦ<br>";

			throw new PlatformException(except);
		}
	}

	public void newStudentRegBatch(String[] studentId, String gradeId)
			throws PlatformException {
		dbpool db = new dbpool();
		ArrayList exceptionList = new ArrayList();
		String except = "";
		int count = 0;
		String sql = "";
		/*
		 * Calendar c = Calendar.getInstance(); String date =
		 * Integer.toString(c.get(Calendar.YEAR)).substring(2, 4);
		 * 
		 */
		// System.out.println("studentId.length="+studentId.length);
		for (int i = 0; i < studentId.length; i++) {
			List sqlList = new ArrayList();
			OracleRecruitStudent student = new OracleRecruitStudent(
					studentId[i]);
			// System.out.println("studentId[i]="+studentId[i]);
			/*
			 * OracleMajor major = new OracleMajor();
			 * major.setId(student.getEduInfo().getMajor_id()); List sortList =
			 * major.getSorts(); OracleRecruitSort sort = (OracleRecruitSort)
			 * sortList.get(0); String sort_id = sort.getId(); String reg_no =
			 * date + sort_id + student.getEduInfo().getEdutype_id() +
			 * student.getEduInfo().getSite_id() +
			 * student.getEduInfo().getMajor_id(); OracleStudent oStudent = new
			 * OracleStudent(); reg_no = oStudent.getNextRegNo(reg_no);
			 * 
			 * 
			 * String sql = "select id from entity_student_info where reg_no='" +
			 * reg_no + "'"; if (db.countselect(sql) > 0) {
			 * exceptionList.add("ѧ��" + reg_no + "�Ѵ���"); continue; }
			 */
			String ssoSql = "insert into sso_user (id,login_id,password,name,email,ADD_SUBSYSTEM) values (to_char(s_sso_id.nextval),'"
					+ student.getReg_no()
					+ "','"
					+ student.getPassword()
					+ "','"
					+ student.getName()
					+ "','"
					+ student.getEmail()
					+ "','" + SubSystemType.ENTITY + "')";

			String eduSql = "insert into entity_student_info (id,name,id_card,reg_no,class_id,edutype_id,grade_id,site_id,major_id,study_form,study_status,status,entrance_date,photo_link) values "
					+ "(to_char(s_sso_id.currval),'"
					+ student.getName()
					+ "','"
					+ student.getNormalInfo().getIdcard()
					+ "','"
					+ student.getReg_no()
					+ "','"
					+ student.getEduInfo().getClass_id()
					+ "','"
					+ student.getEduInfo().getEdutype_id()
					+ "','"
					+ student.getEduInfo().getGrade_id()
					+ "','"
					+ student.getEduInfo().getSite_id()
					+ "','"
					+ student.getEduInfo().getMajor_id()
					+ "','"
					+ student.getEduInfo().getStudy_form()
					+ "','"
					+ student.getEduInfo().getStudy_status()
					+ "','0','"
					+ student.getEduInfo().getEntrance_date()
					+ "','../recruit/photo/"
					+ student.getEduInfo().getPhoto_link() + "')";

			String normalSql = "insert into entity_usernormal_info (id,name,birthday,gender,idcard,edu,folk,hometown,workplace,position,title,zzmm,graduated_major,graduated_sch,graduated_time,email,phone,mobilephone,"
					+ "fax,work_address,work_postalcode,home_address,home_postalcode,degree,note) values (to_char(s_sso_id.currval),'"
					+ student.getName()
					+ "','"
					+ student.getNormalInfo().getBirthday()
					+ "','"
					+ student.getNormalInfo().getGender()
					+ "','"
					+ student.getNormalInfo().getIdcard()
					+ "','"
					+ student.getNormalInfo().getEdu()
					+ "','"
					+ student.getNormalInfo().getFolk()
					+ "','"
					+ student.getNormalInfo().getHometown()
					+ "','"
					+ student.getNormalInfo().getWorkplace()
					+ "','"
					+ student.getNormalInfo().getPosition()
					+ "','"
					+ student.getNormalInfo().getTitle()
					+ "','"
					+ student.getNormalInfo().getZzmm()
					+ "','"
					+ student.getNormalInfo().getGraduated_major()
					+ "','"
					+ student.getNormalInfo().getGraduated_sch()
					+ "','"
					+ student.getNormalInfo().getGraduated_time()
					+ "','"
					+ student.getNormalInfo().getEmails()
					+ "','"
					+ student.getNormalInfo().getPhones()
					+ "','"
					+ student.getNormalInfo().getMobilePhones()
					+ "','"
					+ student.getNormalInfo().getFaxs()
					+ "','"
					+ student.getNormalInfo().getWork_address().getAddress()
					+ "','"
					+ student.getNormalInfo().getWork_address().getPostalcode()
					+ "','"
					+ student.getNormalInfo().getHome_address().getAddress()
					+ "','"
					+ student.getNormalInfo().getHome_address().getPostalcode()
					+ "','"
					+ student.getNormalInfo().getDegree()
					+ "','"
					+ student.getNormalInfo().getNote() + "')";
			sqlList.add(ssoSql);
			sqlList.add(eduSql);
			sqlList.add(normalSql);
			UserAddLog
					.setDebug("OracleUserBatchActivity.newStudentRegBatch(String[] studentId, String gradeId) SQL1="
							+ ssoSql
							+ " SQL2="
							+ eduSql
							+ "SQL3="
							+ normalSql
							+ " DATE=" + new Date());
			if (db.executeUpdateBatch(sqlList) < 1) {
				exceptionList.add("(" + student.getReg_no() + ")"
						+ student.getName() + "ע�����");
			} else {
				sql = "update recruit_student_info set register_status='1' where id='"
						+ studentId[i] + "'";
				db.executeUpdate(sql);
				UserUpdateLog
						.setDebug("OracleUserBatchActivity.newStudentRegBatch(String[] studentId, String gradeId) SQL="
								+ sql + " DATE=" + new Date());
				// �������ѧ�ѱ�׼
				OracleSsoUser ssoUser = new OracleSsoUser();
				String ssoId = ssoUser.getSsoLoginUser(student.getReg_no())
						.getId();
				String isExist = "select user_id from entity_userfee_level where user_id='"
						+ ssoId + "'";
				if (db.countselect(isExist) < 1) {
					sql = "insert into entity_userfee_level(user_id,type1_name,type1_value,type2_name,type2_value"
							+ ",type3_name,type3_value,type4_name,type4_value,type5_name,type5_value) select '"
							+ ssoId
							+ "',type1_name,type1_value,type2_name,type2_value"
							+ ",type3_name,type3_value,type4_name,type4_value,type5_name,type5_value "
							+ "from entity_recruit_userfee_level where user_id='"
							+ student.getId() + "'";
					db.executeUpdate(sql);
					UserAddLog
							.setDebug("OracleUserBatchActivity.newStudentRegBatch(String[] studentId, String gradeId) SQL="
									+ sql + " DATE=" + new Date());
				}
				count++;
			}
		}
		db = null;
		if (exceptionList.size() > 0) {
			exceptionList.add("���֤����ע��");
			for (int i = 0; i < exceptionList.size(); i++) {
				except += (String) exceptionList.get(i) + "<br>";
			}
			if (count > 0)
				except += "�ɹ�ע��" + count + "��ѧ��";
			throw new PlatformException(except);
		} else {
			except += "�ɹ�ע��" + count + "��ѧ��";
			throw new PlatformException(except);
		}
	}

	public void newStudentRegBatch(String[] studentId, String gradeId,
			String ssoId) throws PlatformException {
		dbpool db = new dbpool();
		ArrayList exceptionList = new ArrayList();
		String except = "";
		int count = 0;
		String sql = "";
		for (int i = 0; i < studentId.length; i++) {
			List sqlList = new ArrayList();
			OracleRecruitStudent student = new OracleRecruitStudent(
					studentId[i]);

			String ssoSql = "insert into sso_user (id,login_id,password,name,email,ADD_SUBSYSTEM) values ('"
					+ ssoId
					+ "','"
					+ student.getReg_no()
					+ "','"
					+ student.getPassword()
					+ "','"
					+ student.getName()
					+ "','"
					+ student.getEmail() + "','" + SubSystemType.ENTITY + "')";
			String eduSql = "insert into entity_student_info (id,name,id_card,reg_no,class_id,edutype_id,grade_id,site_id,major_id,study_form,study_status,status,entrance_date,photo_link) values ("
					+ "'"
					+ ssoId
					+ "','"
					+ student.getName()
					+ "','"
					+ student.getNormalInfo().getIdcard()
					+ "','"
					+ student.getReg_no()
					+ "','"
					+ student.getEduInfo().getClass_id()
					+ "','"
					+ student.getEduInfo().getEdutype_id()
					+ "','"
					+ student.getEduInfo().getGrade_id()
					+ "','"
					+ student.getEduInfo().getSite_id()
					+ "','"
					+ student.getEduInfo().getMajor_id()
					+ "','"
					+ student.getEduInfo().getStudy_form()
					+ "','"
					+ student.getEduInfo().getStudy_status()
					+ "','0','"
					+ student.getEduInfo().getEntrance_date()
					+ "','../recruit/photo/"
					+ student.getEduInfo().getPhoto_link() + "')";
			String normalSql = "insert into entity_usernormal_info (id,name,birthday,gender,idcard,edu,folk,hometown,workplace,position,title,zzmm,graduated_major,graduated_sch,graduated_time,email,phone,mobilephone,"
					+ "fax,work_address,work_postalcode,home_address,home_postalcode,degree,note) values ('"
					+ ssoId
					+ "','"
					+ student.getName()
					+ "','"
					+ student.getNormalInfo().getBirthday()
					+ "','"
					+ student.getNormalInfo().getGender()
					+ "','"
					+ student.getNormalInfo().getIdcard()
					+ "','"
					+ student.getNormalInfo().getEdu()
					+ "','"
					+ student.getNormalInfo().getFolk()
					+ "','"
					+ student.getNormalInfo().getHometown()
					+ "','"
					+ student.getNormalInfo().getWorkplace()
					+ "','"
					+ student.getNormalInfo().getPosition()
					+ "','"
					+ student.getNormalInfo().getTitle()
					+ "','"
					+ student.getNormalInfo().getZzmm()
					+ "','"
					+ student.getNormalInfo().getGraduated_major()
					+ "','"
					+ student.getNormalInfo().getGraduated_sch()
					+ "','"
					+ student.getNormalInfo().getGraduated_time()
					+ "','"
					+ student.getNormalInfo().getEmails()
					+ "','"
					+ student.getNormalInfo().getPhones()
					+ "','"
					+ student.getNormalInfo().getMobilePhones()
					+ "','"
					+ student.getNormalInfo().getFaxs()
					+ "','"
					+ student.getNormalInfo().getWork_address().getAddress()
					+ "','"
					+ student.getNormalInfo().getWork_address().getPostalcode()
					+ "','"
					+ student.getNormalInfo().getHome_address().getAddress()
					+ "','"
					+ student.getNormalInfo().getHome_address().getPostalcode()
					+ "','"
					+ student.getNormalInfo().getDegree()
					+ "','"
					+ student.getNormalInfo().getNote() + "')";

			sqlList.add(ssoSql);
			sqlList.add(eduSql);
			sqlList.add(normalSql);
			UserAddLog
					.setDebug("OracleUserBatchActivity.newStudentRegBatch(String[] studentId, String gradeId,String ssoId) SQL1="
							+ ssoSql
							+ " SQL2="
							+ eduSql
							+ " SQL3="
							+ normalSql
							+ " DATE=" + new Date());
			if (db.executeUpdateBatch(sqlList) < 1) {
				exceptionList.add("(" + student.getReg_no() + ")"
						+ student.getName() + "ע�����");
			} else {
				sql = "update recruit_student_info set register_status='1' where id='"
						+ studentId[i] + "'";
				db.executeUpdate(sql);
				UserUpdateLog
						.setDebug("OracleUserBatchActivity.newStudentRegBatch(String[] studentId, String gradeId,String ssoId) SQL="
								+ sql + " DATE=" + new Date());
				// �������ѧ�ѱ�׼
				String isExist = "select user_id from entity_userfee_level where user_id='"
						+ ssoId + "'";
				if (db.countselect(isExist) < 1) {
					sql = "insert into entity_userfee_level(user_id,type1_name,type1_value,type2_name,type2_value"
							+ ",type3_name,type3_value,type4_name,type4_value,type5_name,type5_value) select '"
							+ ssoId
							+ "',type1_name,type1_value,type2_name,type2_value"
							+ ",type3_name,type3_value,type4_name,type4_value,type5_name,type5_value "
							+ "from entity_recruit_userfee_level where user_id='"
							+ student.getId() + "'";
					db.executeUpdate(sql);
					UserAddLog
							.setDebug("OracleUserBatchActivity.newStudentRegBatch(String[] studentId, String gradeId,String ssoId) SQL="
									+ sql + " DATE=" + new Date());
				}
				count++;
			}
		}
		db = null;
		if (exceptionList.size() > 0) {
			exceptionList.add("���֤����ע��");
			for (int i = 0; i < exceptionList.size(); i++) {
				except += (String) exceptionList.get(i) + "<br>";
			}
			if (count > 0)
				except += "�ɹ�ע��" + count + "��ѧ��";
			throw new PlatformException(except);
		} else {
			except += "�ɹ�ע��" + count + "��ѧ��";
			throw new PlatformException(except);
		}
	}

	public String[] checkExistRegIdCard(String[] studentId, String gradeId)
			throws PlatformException {
		if (studentId == null || studentId.length < 1)
			return null;
		dbpool db = new dbpool();
		ArrayList exceptionList = new ArrayList();
		String except = "";
		int count = 0;
		String sql = "";
		sql = "select a.id from recruit_student_info a, entity_student_info b "
				+ "where  a.card_no = b.id_card and a.id in (";
		for (int i = 0; i < studentId.length; i++) {
			if (i == 0)
				sql += "'" + studentId[i] + "'";
			else
				sql += ",'" + studentId[i] + "'";
		}
		sql += ")";
		// Log.setDebug("checkExistRegIdCard: " + sql);
		MyResultSet rs = db.executeQuery(sql);
		try {
			while (rs.next()) {
				exceptionList.add(rs.getString("id"));
			}
		} catch (SQLException e) {

		} finally {
			db = null;
		}
		if (exceptionList == null || exceptionList.size() < 1)
			return null;
		else {
			String[] result = new String[exceptionList.size()];
			for (int i = 0; i < result.length; i++)
				result[i] = (String) exceptionList.get(i);
			return result;
		}

	}

	public void newStudentUnRegBatch(String[] studentId)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		int count = 0;
		ArrayList sqlList = new ArrayList();
		ArrayList exceptionList = new ArrayList();
		String id = "";
		String except = "";
		String regSql = "";
		String electiveSql = "";
		String recruitSql = "";
		String ssoSql = "";
		String eduSql = "";
		String normalSql = "";
		for (int i = 0; i < studentId.length; i++) {
			recruitSql = "select b.id from recruit_student_info a,entity_student_info b "
					+ "where a.reg_no=b.reg_no and a.id='" + studentId[i] + "'";
			rs = db.executeQuery(recruitSql);
			try {
				if (rs != null && rs.next())
					id = rs.getString("id");
			} catch (SQLException e) {
				
			}
			db.close(rs);
			regSql = "delete from entity_register_info where user_id = '" + id
					+ "'";
			electiveSql = "delete from entity_elective where student_id = '"
					+ id + "'";
			// recruitSql = "update recruit_student_info set register_status='0'
			// , reg_no='' "
			// + "where id= '" + studentId[i] + "'";

			recruitSql = "update recruit_student_info set register_status='0' "
					+ "where id= '" + studentId[i] + "'";
			ssoSql = "delete from sso_user where id = '" + id + "'";
			eduSql = "delete from entity_student_info where id = '" + id + "'";
			normalSql = "delete from entity_usernormal_info where id = '" + id
					+ "'";
			sqlList.add(regSql);
			sqlList.add(electiveSql);
			sqlList.add(recruitSql);
			sqlList.add(ssoSql);
			sqlList.add(eduSql);
			sqlList.add(normalSql);
			UserDeleteLog
					.setDebug("OracleUserBatchActivity.newStudentUnRegBatch(String[] studentId) SQL1="
							+ regSql
							+ " SQL2="
							+ electiveSql
							+ " SQL3="
							+ ssoSql
							+ " SQL4="
							+ eduSql
							+ " SQL5="
							+ normalSql
							+ " DATE=" + new Date());
			UserUpdateLog
					.setDebug("OracleUserBatchActivity.newStudentUnRegBatch(String[] studentId) SQL="
							+ recruitSql + " DATE=" + new Date());
			if (db.executeUpdateBatch(sqlList) == 0) {
				exceptionList.add("" + id + "ȡ��ע�����");
			} else
				count++;
		}
		db = null;
		if (exceptionList.size() > 0) {
			exceptionList.add("���֤����ȡ��ע��");
			for (int i = 0; i < exceptionList.size(); i++) {
				except += (String) exceptionList.get(i) + "<br>";
			}
			if (count > 0)
				except += "�ɹ�ȡ��ע��" + count + "��ѧ��";
			throw new PlatformException(except);
		} else {
			except += "�ɹ�ȡ��ע��" + count + "��ѧ��";
			throw new PlatformException(except);
		}
	}
}
