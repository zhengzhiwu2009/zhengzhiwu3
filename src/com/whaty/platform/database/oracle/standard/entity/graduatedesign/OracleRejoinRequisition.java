package com.whaty.platform.database.oracle.standard.entity.graduatedesign;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.entity.graduatedesign.RejoinRequisition;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * ¿‡√Ë ˆ£∫¥±Á£®∆¿…Û£©…Í«Î±Ì
 * 
 * @author zhangliang
 * 
 */
public class OracleRejoinRequisition extends RejoinRequisition {
	public OracleRejoinRequisition() {

	}

	public OracleRejoinRequisition(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id,student_id,link,discoursename,completestatus,requistiontype,remark,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id from (select egr.id,egr.student_id,egr.link,egr.discoursename,egr.completestatus,egr.requistiontype,egr.remark,egr.teacher_note,egr.siteteacher_note,egr.status,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id  from entity_graduate_rejoinreques egr,entity_student_info  esi,entity_usernormal_info   eui where egr.student_id = esi.id and egr.student_id = eui.id) where id ='"
				+ id + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent(rs
						.getString("student_id"));
				this.setStudent(student);
				this.setId(id);
				this.setLink(rs.getString("link"));
				this.setDiscourseName(rs.getString("discoursename"));
				this.setCompleteStatus(rs.getString("completestatus"));
				this.setRequisitionType(rs.getString("requistiontype"));
				this.setRemark(rs.getString("remark"));
				this.setTeacher_note(rs.getString("teacher_note"));
				this.setSiteteacher_note(rs.getString("siteteacher_note"));
				this.setStatus(rs.getString("status"));
			}
		} catch (java.sql.SQLException es) {
			EntityLog
					.setDebug("OracleRejoinRequisition@Mehtod:OracleRejoinRequisition(String id)  sql="
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "insert into entity_graduate_rejoinreques (id,student_id,link,discoursename,completestatus,requistiontype,remark,status) values "
				+ "(to_char(s_entity_graduate_rejoinrqs_id.nextval),to_char('"
				+ this.getStudent().getId()
				+ "'),to_char('"
				+ this.getLink()
				+ "'),to_char('"
				+ this.getDiscourseName()
				+ "'),to_char('"
				+ this.getCompleteStatus()
				+ "'),to_char('"
				+ this.getRequisitionType()
				+ "'),to_char('"
				+ this.getRemark()
				+ "'),to_char('0'))";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRejoinRequisition.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_rejoinreques set student_id = to_char('"
				+ this.getStudent().getId()
				+ "'),link= to_char('"
				+ this.getLink()
				+ "'),discoursename= to_char('"
				+ this.getDiscourseName()
				+ "'),completestatus= to_char('"
				+ this.getCompleteStatus()
				+ "'),requistiontype= to_char('"
				+ this.getRequisitionType()
				+ "'),remark= to_char('"
				+ this.getRemark()
				+ "'),teacher_note= to_char('"
				+ this.getTeacher_note()
				+ "'),siteteacher_note= to_char('"
				+ this.getSiteteacher_note()
				+ "'),status= to_char('"
				+ this.getStatus() + "') where id = '" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRejoinRequisition.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "delete from entity_graduate_rejoinreques where id = '"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRejoinRequisition.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int siteTeacherConfirm() {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_rejoinreques set status ='1',siteteacher_note = '"+this.getSiteteacher_note()+"' where id = '"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRejoinRequisition.siteTeacherConfirm() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int siteTeacherReject() {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_rejoinreques set status ='2',siteteacher_note = '"+this.getSiteteacher_note()+"' where id = '"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRejoinRequisition.siteTeacherReject() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int teacherConfirm() {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_rejoinreques set status ='3',teacher_note = '"+this.getTeacher_note()+"' where id = '"
				+ this.getId() + "'";
		String sql2 = "update entity_graduate_discourse set requisitiontype = '" + this.getRequisitionType() + "' where student_id = '" + this.getStudent().getId()  + "'";
		List lst = new ArrayList();
		lst.add(sql);
		lst.add(sql2);
		int i = db.executeUpdateBatch(lst);
		UserUpdateLog.setDebug("OracleRejoinRequisition.teacherConfirm() SQL1=" + sql + " SQL2=" + sql2 + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int teacherReject() {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_rejoinreques set status ='4',teacher_note = '"+this.getTeacher_note()+"' where id = '"
				+ this.getId() + "'";
		String sql2 = "update entity_graduate_discourse set requisitiontype = '' where student_id = '" + this.getStudent().getId()  + "'";
		List lst = new ArrayList();
		lst.add(sql);
		lst.add(sql2);
		int i = db.executeUpdateBatch(lst);
		UserUpdateLog.setDebug("OracleRejoinRequisition.teacherConfirm() SQL1=" + sql + " SQL2=" + sql2 + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	
	public RejoinRequisition getRejoinRequisition(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		RejoinRequisition rejoinRequisition = new OracleRejoinRequisition();
		String sql = "select id,student_id,link,discoursename,completestatus,requistiontype,remark,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id from (select egr.id,egr.student_id,egr.link,egr.discoursename,egr.completestatus,egr.requistiontype,egr.remark,egr.teacher_note,egr.siteteacher_note,egr.status,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id  from entity_graduate_rejoinreques egr,entity_student_info  esi,entity_usernormal_info   eui where egr.student_id = esi.id and egr.student_id = eui.id) where student_id ='"
				+ id + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent(rs
						.getString("student_id"));
				rejoinRequisition.setStudent(student);
				rejoinRequisition.setId(rs.getString("id"));
				rejoinRequisition.setLink(rs.getString("link"));
				rejoinRequisition.setDiscourseName(rs.getString("discoursename"));
				rejoinRequisition.setCompleteStatus(rs.getString("completestatus"));
				rejoinRequisition.setRequisitionType(rs.getString("requistiontype"));
				rejoinRequisition.setRemark(rs.getString("remark"));
				rejoinRequisition.setTeacher_note(rs.getString("teacher_note"));
				rejoinRequisition.setSiteteacher_note(rs.getString("siteteacher_note"));
				rejoinRequisition.setStatus(rs.getString("status"));
			}
		} catch (java.sql.SQLException es) {
			EntityLog
					.setDebug("OracleRejoinRequisition@Mehtod:getRejoinRequisition(String id)  sql="
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return rejoinRequisition;
	}
	public int upLoadGraduateDesignWord(String reg_no, String fileLink) {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_rejoinreques egs set egs.link ='"
				+ fileLink
				+ "' where egs.student_id = (select id from entity_student_info esi where esi.reg_no ='"
				+ reg_no + "')";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRejoinRequisition.upLoadGraduateDesignWord(String reg_no,String fileLink) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
}
