/*
 * OracleTeacher.java
 *
 * Created on 2005��4��7��, ����10:00
 */

package com.whaty.platform.database.oracle.standard.entity.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.config.SubSystemType;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.sso.OracleSsoUser;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.entity.user.HumanPlatformInfo;
import com.whaty.platform.entity.user.Teacher;
import com.whaty.platform.entity.user.TeacherEduInfo;
import com.whaty.platform.util.RedundanceData;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * 
 * @author Administrator
 */
public class OracleTeacher extends com.whaty.platform.entity.user.Teacher implements Serializable{

	/** Creates a new instance of OracleTeacher */
	public OracleTeacher() {
		super();
	}

	public OracleTeacher(java.lang.String aid) {
		super();
		dbpool dbTeacher = new dbpool();
		MyResultSet rs = null;
		String sql = "";
//		sql = "select t.id,t.name,t.gh,t.password,t.email,t.phone,t.address,t.zip_code,t.gender,t.workplace,t.position,"
//				+ "t.title,t.status,t.dep_id,d.name as d_name,t.last_login_time,t.last_login_ip,t.teach_level,t.site,"
//				+ "t.teach_time,t.work_kind,t.note,t.research_direction,t.id_card,t.mobilephone,t.re0,t.re1,t.re2,t.re3,t.re4,t.re5,t.re6,t.re7,t.re8,t.re9,t.type,t.photo_link as photo_link  from entity_teacher_info t,entity_dep_info d where t.id='"
//				+ aid + "' and t.dep_id = d.id(+)";
		
//		sql ="select u.id,u.login_id,t.name,u.password,t.gender,c.code as status,t.teach_level,t.teach_time,t.work_kind from sso_user u, pe_teacher t,pe_pri_role r,enum_const c where u.id = t.id and u.fk_role_id=r.id(+) and  t.flag_active = c.id(+) and u.id='"+aid+"'";
		sql = "select pet.id,pet.true_name,sso.login_id from pe_teacher pet, sso_user sso where pet.fk_sso_user_id = sso.id and sso.id = '" + aid + "'";
		
		try {
			rs = dbTeacher.executeQuery(sql);
			if (rs != null && rs.next()) {
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				TeacherEduInfo eduInfo = new TeacherEduInfo();
				HumanPlatformInfo platformInfo = new HumanPlatformInfo();
				RedundanceData redundance = new RedundanceData();
				this.setId(rs.getString("id"));
				this.setName(rs.getString("true_name"));
				this.setLoginId(rs.getString("login_id"));
				// this.setNickname(rs.getString("nickname"));
//				this.setPassword(rs.getString("password"));
//				this.setEmail(rs.getString("email"));
				eduInfo.setGh(rs.getString("login_id"));
//				eduInfo.setDep_name(rs.getString("d_name"));
//				eduInfo.setStatus(rs.getString("status"));
//				eduInfo.setTeach_level(rs.getString("teach_level"));
//				eduInfo.setTeach_time(rs.getString("teach_time"));
//				eduInfo.setWork_kind(rs.getString("work_kind"));
//				eduInfo.setPhoto_link(rs.getString("photo_link"));
//				eduInfo
//						.setResearchDirection(rs
//								.getString("research_direction"));
//				eduInfo.setDep_id(rs.getString("dep_id"));
//				eduInfo.setDep_name(rs.getString("d_name"));
//				eduInfo.setType(rs.getString("type"));
//				normalInfo.setNote(rs.getString("note"));
//				normalInfo.setWorkplace(rs.getString("workplace"));
//				normalInfo.setPosition(rs.getString("position"));
//				normalInfo.setTitle(rs.getString("title"));
//				normalInfo.setGender(rs.getString("gender"));
//				normalInfo.setIdcard(rs.getString("id_card"));
//				if (rs.getString("email") != null) {
//					normalInfo.setEmail(rs.getString("email").split(","));
//				}
//				if (rs.getString("phone") != null
//						&& rs.getString("phone").length() > 0)
//					normalInfo.setPhone(rs.getString("phone").split(","));
//				else
//					normalInfo.setPhone(new String[0]);
				//if (rs.getString("mobilephone") != null
				//		&& rs.getString("mobilephone").length() > 0)
				//	normalInfo.setMobilePhone(rs.getString("mobilephone")
				//			.split(","));
				//else
				//	normalInfo.setMobilePhone(new String[0]);
//				Address home_address = new Address();
//				Address work_address = new Address();
//				work_address.setAddress(rs.getString("address"));
//				work_address.setPostalcode(rs.getString("zip_code"));
//				normalInfo.setWork_address(work_address);
//				platformInfo.setLastlogin_ip(rs.getString("last_login_ip"));
//				platformInfo.setLastlogin_time(rs.getString("last_login_time"));
//				redundance.setRe1(rs.getString("re1"));
//				redundance.setRe2(rs.getString("re2"));
//				redundance.setRe3(rs.getString("re3"));
//				redundance.setRe4(rs.getString("re4"));
//				redundance.setRe5(rs.getString("re5"));
//				redundance.setRe6(rs.getString("re6"));
//				redundance.setRe7(rs.getString("re7"));
//				redundance.setRe8(rs.getString("re8"));
//				redundance.setRe9(rs.getString("re9"));
				this.setNormalInfo(normalInfo);
				this.setTeacherInfo(eduInfo);
				this.setPlatformInfo(platformInfo);
				this.setRedundace(redundance);
			}
		} catch (java.sql.SQLException e) {
			EntityLog
					.setError("OracleTeacher(ava.lang.String aid) error!!!sql="
							+ sql+","+e.toString());
		} finally {
			dbTeacher.close(rs);
			dbTeacher = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		if (OracleSsoUser.isExist(this.getTeacherInfo().getGh()) > 0) {
			throw new PlatformException("��ͳһ��½ϵͳ�û��Ѿ����ڣ�");
		}
		if (isGhExist() > 0) {
			throw new PlatformException("�ù����Ѿ����ڣ�");
		}
		String ssoSql = "insert into sso_user (id,login_id,password,name,email,ADD_SUBSYSTEM) values "
				+ "(to_char(s_sso_id.nextval),'"
				+ this.getTeacherInfo().getGh()
				+ "','"
				+ this.getPassword()
				+ "','"
				+ this.getName()
				+ "','"
				+ this.getEmail()
				+ "','"
				+ SubSystemType.ENTITY + "')";
		String eduSql = "insert into entity_teacher_info (id,gh,name,password,email,phone,address,zip_code,gender,workplace,position,title,status,note,teach_level,work_kind,teach_time,dep_id,type,ID_CARD,MOBILEPHONE,RESEARCH_DIRECTION,photo_link) "
				+ "values (to_char(s_sso_id.currval),'"
				+ this.getTeacherInfo().getGh()
				+ "','"
				+ this.getName()
				+ "','"
				+ this.getPassword()
				+ "','"
				+ this.getEmail()
				+ "',"
				+ "'"
				+ this.getNormalInfo().getPhones()
				+ "','"
				+ this.getNormalInfo().getWork_address().getAddress()
				+ "','"
				+ this.getNormalInfo().getWork_address().getPostalcode()
				+ "',"
				+ "'"
				+ this.getNormalInfo().getGender()
				+ "','"
				+ this.getNormalInfo().getWorkplace()
				+ "','"
				+ this.getNormalInfo().getPosition()
				+ "','"
				+ this.getNormalInfo().getTitle()
				+ "',"
				+ "'"
				+ this.getTeacherInfo().getStatus()
				+ "','"
				+ this.getNormalInfo().getNote()
				+ "','"
				+ this.getTeacherInfo().getTeach_level()
				+ "','"
				+ this.getTeacherInfo().getWork_kind()
				+ "',"
				+ "'"
				+ this.getTeacherInfo().getTeach_time()
				+ "','"
				+ this.getTeacherInfo().getDep_id()
				+ "','"
				+ this.getTeacherInfo().getType()
				+ "','"
				+ this.getNormalInfo().getIdcard()
				+ "','"
				+ this.getNormalInfo().getMobilePhones()
				+ "','"
				+ this.getTeacherInfo().getResearchDirection() 
				+ "','"
				+ this.getTeacherInfo().getPhoto_link()
				+ "')";

		ArrayList sqlList = new ArrayList();
		sqlList.add(ssoSql);
		sqlList.add(eduSql);
		int i = 0;
		i = db.executeUpdateBatch(sqlList);
		UserAddLog.setDebug("OracleTeacher.add() SQL1=" + ssoSql + " SQL2="
				+ eduSql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() {
		dbpool db = new dbpool();
		String t_sql = "delete from entity_teacher_course where teacher_id = '"
				+ this.getId() + "'";
		String sql = "delete from entity_teacher_info where id = '"
				+ this.getId() + "'";
		String sso_sql = "delete from sso_user where login_id = '"
				+ this.getTeacherInfo().getGh() + "'";
		ArrayList sqlList = new ArrayList();
		sqlList.add(t_sql);
		sqlList.add(sql);
		sqlList.add(sso_sql);
		int i = 0;
		i = db.executeUpdateBatch(sqlList);
		UserDeleteLog.setDebug("OracleTeacher.delete() SQL1=" + t_sql + " SQL2="
				+ sql + " SQL3=" + sso_sql + " COUNT=" + i + " DATE="
				+ new Date());
		return i;
	}

	public List getCourses() {
		ArrayList courselist = new ArrayList();
		return courselist;
	}

	public List getStudentClasses() {
		ArrayList studentclasses = new ArrayList();
		return studentclasses;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		Teacher tempTeacher = new OracleTeacher(this.getId());
		if (!tempTeacher.getTeacherInfo().getGh().equals(
				this.getTeacherInfo().getGh())) {
			if (OracleSsoUser.isExist(this.getTeacherInfo().getGh()) > 0) {
				throw new PlatformException("��ͳһ��½ϵͳ�û��Ѿ����ڣ�");
			}
			if (isGhExist() > 0) {
				throw new PlatformException("�ù����Ѿ����ڣ�");
			}
		}
		String ssoSql = "update sso_user set password='" + this.getPassword()
				+ "',name='" + this.getName() + "',email='" + this.getEmail()
				+ "' where id = '" + this.getId() + "'";
		String sql = "update entity_teacher_info set name='" + this.getName()
				+ "', password='" + this.getPassword() + "'," + " email='"
				+ this.getEmail() + "',phone='"
				+ this.getNormalInfo().getPhones() + "', address='"
				+ this.getNormalInfo().getWork_address().getAddress() + "',"
				+ "zip_code='"
				+ this.getNormalInfo().getWork_address().getPostalcode()
				+ "', gender='" + this.getNormalInfo().getGender()
				+ "', workplace='" + this.getNormalInfo().getWorkplace()
				+ "', " + "position='" + this.getNormalInfo().getPosition()
				+ "', title='" + this.getNormalInfo().getTitle()
				+ "', status='" + this.getTeacherInfo().getStatus() + "', "
				+ " note='" + this.getNormalInfo().getNote()
				+ "', teach_level='" + this.getTeacherInfo().getTeach_level()
				+ "',work_kind='" + this.getTeacherInfo().getWork_kind()
				+ "', " + "teach_time='"
				+ this.getTeacherInfo().getTeach_time() + "', dep_id='"
				+ this.getTeacherInfo().getDep_id() + "', id_card='"
				+ this.getNormalInfo().getIdcard() + "', mobilephone='"
				+ this.getNormalInfo().getMobilePhones()
				+ "', research_direction='"
				+ this.getTeacherInfo().getResearchDirection()
				+ "', photo_link ='"
				+ this.getTeacherInfo().getPhoto_link()
				+ "' where  id='" + this.getId() + "'";
		ArrayList sqlList = new ArrayList();
		sqlList.add(sql);
		sqlList.add(ssoSql);
		int i = 0;
		i = db.executeUpdateBatch(sqlList);
		UserUpdateLog.setDebug("OracleTeacher.update() SQL1=" + sql + " SQL2="
				+ ssoSql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int isIdExist() {
		dbpool db = new dbpool();
		String sql = "select id from entity_teacher_info where id='"
				+ this.getId() + "'";
		int i = db.countselect(sql);
		db = null;
		return i;
	}

	public List getTeachCourse() {
		ArrayList teachcourses = new ArrayList();
		String sql = "";
		sql = "select id,course_name,credit,course_time,course_type,a_id from "
				+ "(select c.id,c.course_name,c,credit,c.course_time,c.course_type,a.id as a_id from entity_course_info c,entity_course_active a,entity_teacher_course t "
				+ "where t.teacher_id = '" + this.getId()
				+ "' and c.id = a.course_id and t.open_course_id = a.id)";
		dbpool db = new dbpool();
		MyResultSet rs = null;
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				String id = rs.getString("id");
				String course_name = rs.getString("course_name");
				String credit = rs.getString("credit");
				String course_time = rs.getString("course_time");
				String course_type = rs.getString("course_type");
				String a_id = rs.getString("a_id");
				String[] tempcourse = { id, course_time, credit, course_time,
						course_type, a_id };
				teachcourses.add(tempcourse);
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("Teacher.getTeachCourse() error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return teachcourses;
	}

	/** �жϹ����Ƿ���ڣ�����0�򲻴��ڣ�����0����� */
	private int isGhExist() {
		dbpool db = new dbpool();
		String sql = "select id from entity_teacher_info where id<>'"
				+ this.getId() + "' and gh='" + this.getTeacherInfo().getGh()
				+ "'";
		int i = db.countselect(sql);
		db = null;
		return i;
	}

}
