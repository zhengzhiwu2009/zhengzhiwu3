package com.whaty.platform.database.oracle.standard.entity.graduatedesign;

import java.sql.SQLException;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.entity.graduatedesign.FreeApply;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleFreeApply extends FreeApply {

	public OracleFreeApply() {
	}

	public OracleFreeApply(String id) {
		String sql = "select t.id,t.student_id,t.production_name,t.link,t.status,t.teacher_note,to_char(t.apply_time,'yyyy-mm-dd') as apply_time from ENTITY_GRADUATE_FREE_APPLY t where id = '"
				+ id + "'";
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setStudent(new OracleStudent(rs.getString("student_id")));
				this.setProductionName(rs.getString("production_name"));
				this.setLink(rs.getString("link"));
				this.setStatus(rs.getString("status"));
				this.setApplyTime(rs.getString("apply_time"));
				this.setTeacher_note(rs.getString("teacher_note"));
				this.setStudentId(rs.getString("student_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			EntityLog.setError("OracleFreeApply(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}

	}

	public int add() throws PlatformException {
		// TODO Auto-generated method stub
		int suc = 0;
		dbpool db = new dbpool();
		String sql = "insert into ENTITY_GRADUATE_FREE_APPLY(id,Student_Id,Production_Name,Link,Apply_Time,type) values(to_char(s_graduate_free_apply_id.nextval),'"
				+ this.getStudentId()
				+ "','"
				+ this.getProductionName()
				+ "','"
				+ this.getLink()
				+ "',sysdate,'"
				+ this.getType()
				+ "')";// 加入免做申请类型
		suc = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleFreeApply.add() SQL=" + sql + " COUNT="
				+ suc + " DATE=" + new Date());
		return suc;
	}

	public int update() throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int delete() throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	// 对得到的FreeApply做了修改,增加了返回的类型.type,与成绩
	public FreeApply getFreeApplyByStudentId(String studentId) {
		String sql = "select t.id,t.type,t.score_status from ENTITY_GRADUATE_FREE_APPLY t where student_id = '"
				+ studentId + "'";
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		FreeApply apply = null;
		try {
			if (rs != null && rs.next()) {
				apply = new OracleFreeApply(rs.getString("id"));
				apply.setType(rs.getString("type"));
				apply.setScore_status(rs.getString("score_status"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			EntityLog.setError("OracleFreeApply(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return apply;
	}

	// 加入了成绩的更新
	public int UpdateFreeApplyStatus(String status) {
		String sql = "update ENTITY_GRADUATE_FREE_APPLY set status = '"
				+ status + "',teacher_note='" + this.getTeacher_note()
				+ "',score_status='" + this.getScore_status() + "' where id='"
				+ this.getId() + "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog
				.setDebug("OracleFreeApply.UpdateFreeApplyStatus(String status) SQL="
						+ sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int updateFreeApplyToTotalGrade(String studentId, String semesterId,
			String courseId, String score) {

		String sql = "update entity_elective ele set ele.total_score='"
				+ score
				+ "' where ele.id= "
				+ "(  select ee.id from entity_elective      ee,entity_teach_class   "
				+ "etc,entity_course_active eca,entity_course_info   eci,entity_semester_info esis,entity_student_info "
				+ " esi where ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id  and ee.student_id = esi.id and eci.id='" + courseId + "' and esi.id='"
				+ studentId + "')";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog
				.setDebug("OracleFreeApply.updateFreeApplyToTotalGrade() SQL="
						+ sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}