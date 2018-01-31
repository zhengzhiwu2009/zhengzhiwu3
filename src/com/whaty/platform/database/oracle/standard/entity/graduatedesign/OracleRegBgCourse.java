package com.whaty.platform.database.oracle.standard.entity.graduatedesign;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteTeacher;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.entity.graduatedesign.RegBgCourse;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * 类描述：开课登记信息
 * 
 * @author Administrator
 * 
 */
public class OracleRegBgCourse extends RegBgCourse {
	public OracleRegBgCourse() {

	}

	public OracleRegBgCourse(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id,student_id,link,discoursename,discoursecontent,discourseplan,remark,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id,siteteacher_id from (select egr.id,egr.student_id,egr.link,egr.discoursename,egr.discoursecontent,egr.discourseplan,egr.remark,egr.teacher_note,egr.siteteacher_note,egr.status,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id,egsl.siteteacher_id as siteteacher_id  from entity_graduate_regbegincourse egr,entity_student_info    esi,entity_graduate_siteteachlimit egsl,entity_usernormal_info eui  where egr.student_id = esi.id    and egr.student_id = eui.id and egsl.student_id (+)= egr.student_id) where id ='"
				+ id + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent(rs
						.getString("student_id"));
				this.setStudent(student);

				this.setLink(rs.getString("link"));
				this.setDiscourseName(rs.getString("discoursename"));
				this.setDiscourseContent(rs.getString("discoursecontent"));
				this.setDiscoursePlan(rs.getString("discourseplan"));
				this.setRemark(rs.getString("remark"));
				this.setTeacher_note(rs.getString("teacher_note"));
				this.setSiteteacher_note(rs.getString("siteteacher_note"));
				this.setSiteTeacher(new OracleSiteTeacher(rs.getString("siteteacher_id")));
				this.setStatus(rs.getString("status"));
			}
		} catch (java.sql.SQLException es) {
			EntityLog
					.setDebug("OracleRegBgCourse@Method:OracleRegBgCourse(String id)  sql="
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "insert into entity_graduate_regbegincourse (id,student_id,link,discoursename,discoursecontent,discourseplan,remark,status) "
				+ "values (to_char(s_entity_graduate_regcourse_id.nextval),to_char('"
				+ this.getStudent().getId()
				+ "'),to_char('"
				+ this.getLink()
				+ "'),to_char('"
				+ this.getDiscourseName()
				+ "'),to_char('"
				+ this.getDiscourseContent()
				+ "'),to_char('"
				+ this.getDiscoursePlan()
				+ "'),to_char('"
				+ this.getRemark()
				+ "'),to_char('0'))";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRegBgCourse.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_regbegincourse set "
				+ "student_id=to_char('" 
				+ this.getStudent().getId()
				+ "'),link=to_char('" 
				+ this.getLink()
				+ "'),discoursename=to_char('"
				+ this.getDiscourseName()
				+ "'),discoursecontent=to_char('" 
				+ this.getDiscourseContent()
				+ "'),discourseplan=to_char('" 
				+ this.getDiscoursePlan()
				+ "'),remark=to_char('" 
				+ this.getRemark()
				+ "'),teacher_note=to_char('"
				+ this.getTeacher_note()
				+ "'),siteteacher_note=to_char('"
				+ this.getSiteteacher_note()
				+ "'),status=to_char('" 
				+ this.getStatus() 
				+ "') where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRegBgCourse.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "delete from entity_graduate_regbegincourse where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRegBgCourse.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int siteTeacherConfirm() {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_regbegincourse set status ='1',siteteacher_note='"+this.getSiteteacher_note()+"' where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRegBgCourse.siteTeacherConfirm() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int siteTeacherReject() {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_regbegincourse set status ='2',siteteacher_note='"+this.getSiteteacher_note()+"' where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRegBgCourse.siteTeacherReject() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int teacherConfirm() {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_regbegincourse set status ='3',teacher_note='"+this.getTeacher_note()+"' where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRegBgCourse.teacherConfirm() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int teacherReject() {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_regbegincourse set status ='4',teacher_note='"+this.getTeacher_note()+"' where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRegBgCourse.teacherReject() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	
	public RegBgCourse getRegBgCourse(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		RegBgCourse regBgCourse = new OracleRegBgCourse();
		String sql = "select id,student_id,link,discoursename,discoursecontent,discourseplan,remark,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id from (select egr.id,egr.student_id,egr.link,egr.discoursename,egr.discoursecontent,egr.discourseplan,egr.remark,egr.teacher_note,egr.siteteacher_note,egr.status,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id   from entity_graduate_regbegincourse egr,entity_student_info    esi,entity_usernormal_info eui  where egr.student_id = esi.id    and egr.student_id = eui.id) where student_id ='"
				+ id + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent(rs
						.getString("student_id"));
				regBgCourse.setStudent(student);
                regBgCourse.setId(rs.getString("id"));
				regBgCourse.setLink(rs.getString("link"));
				regBgCourse.setDiscourseName(rs.getString("discoursename"));
				regBgCourse.setDiscourseContent(rs.getString("discoursecontent"));
				regBgCourse.setDiscoursePlan(rs.getString("discourseplan"));
				regBgCourse.setRemark(rs.getString("remark"));
				regBgCourse.setTeacher_note(rs.getString("teacher_note"));
				regBgCourse.setSiteteacher_note(rs.getString("siteteacher_note"));
				regBgCourse.setStatus(rs.getString("status"));
			}
		} catch (java.sql.SQLException es) {
			EntityLog
					.setDebug("OracleRegBgCourse@Method:OracleRegBgCourse(String id)  sql="
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return regBgCourse;
	}
	public int upLoadGraduateDesignWord(String reg_no, String fileLink) {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_regbegincourse egs set egs.link ='"
				+ fileLink
				+ "' where egs.student_id = (select id from entity_student_info esi where esi.reg_no ='"
				+ reg_no + "')";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRegBgCourse.upLoadGraduateDesignWord(String reg_no,String fileLink) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
}
