package com.whaty.platform.database.oracle.standard.entity.graduatedesign;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.entity.graduatedesign.SubjectQuestionary;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * ������ѧԱ��������Ŀ�����Ϣ
 * 
 * @author zhangliang
 * 
 */
public class OracleSubjectQuestionary extends SubjectQuestionary {
	public OracleSubjectQuestionary() {

	}

	public OracleSubjectQuestionary(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		// sql = "select
		// student_id,main_job,link,subjectname,subjectcontent,remark,teacher_note,siteteacher_note,status
		// from entity_graduate_subjectsearch where id ='"+this.getId()+"'";
		sql = "select id,student_id,main_job,link,subjectname,subjectcontent,remark,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id,gender,folk,home_address,work_address,phone,mobilephone,home_postalcode, score, score_status, score_note from (select t.id,t.student_id,t.main_job,t.link,t.subjectname,t.subjectcontent,t.remark,t.teacher_note,t.siteteacher_note,t.status,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id,eui.gender,eui.folk,eui.home_address,eui.work_address,eui.phone,eui.mobilephone,eui.home_postalcode, t.score, t.score_status, t.score_note from entity_graduate_subjectsearch t,entity_student_info esi,entity_usernormal_info eui where t.student_id = esi.id and t.student_id = eui.id) where id ='"
				+ id + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent(rs
						.getString("student_id"));
				this.setStudent(student);
				this.setMain_job(rs.getString("main_job"));
				this.setLink(rs.getString("link"));
				this.setSubjectName(rs.getString("subjectname"));
				this.setSubjectContent(rs.getString("subjectcontent"));
				this.setRemark(rs.getString("remark"));
				this.setTeacher_note(rs.getString("teacher_note"));
				this.setSiteTeacher_note(rs.getString("siteteacher_note"));
				this.setStatus(rs.getString("status"));
				this.setScore(rs.getString("score"));
				this.setScore_note(rs.getString("score_status"));
				this.setScore_status(rs.getString("score_note"));
			}
		} catch (java.sql.SQLException es) {
			EntityLog
					.setDebug("OracleSubjectQuestionary@Method:OracleSubjectQuestionary(String id)  sql="
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "insert into entity_graduate_subjectsearch (id,student_id,main_job,link,subjectname,subjectcontent,remark,status) values ("
				+ "to_char(s_entity_graduate_sjtqry_id.nextval),to_char('"
				+ this.getStudent().getId()
				+ "'),to_char('"
				+ this.getMain_job()
				+ "'),to_char('"
				+ this.getLink()
				+ "'),to_char('"
				+ this.getSubjectName()
				+ "'),to_char('"
				+ this.getSubjectContent()
				+ "'),to_char('"
				+ this.getRemark()
				+ "'),to_char('0'))";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleSubjectQuestionary.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_subjectsearch set student_id = to_char('"
				+ this.getStudent().getId()
				+ "'),main_job = to_char('"
				+ this.getMain_job()
				+ "'),link = to_char('"
				+ this.getLink()
				+ "'),subjectname = to_char('"
				+ this.getSubjectName()
				+ "'),subjectcontent = to_char('"
				+ this.getSubjectContent()
				+ "'),remark = to_char('"
				+ this.getRemark()
				+ "'),teacher_note = to_char('"
				+ this.getTeacher_note()
				+ "'),siteteacher_note = to_char('"
				+ this.getSiteTeacher_note()
				+ "'),status = to_char('"
				+ this.getStatus() + "') where id ='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSubjectQuestionary.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "delete from entity_graduate_subjectsearch where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleSubjectQuestionary.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int siteTeacherConfirm() {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_subjectsearch set status ='1',siteteacher_note='"
				+ this.getSiteTeacher_note()
				+ "' where id ='"
				+ this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSubjectQuestionary.siteTeacherConfirm() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int siteTeacherReject() {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_subjectsearch set status ='2',siteteacher_note='"
				+ this.getSiteTeacher_note()
				+ "' where id ='"
				+ this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSubjectQuestionary.siteTeacherReject() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int teacherConfirm() {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_subjectsearch set status ='3',teacher_note='"
				+ this.getTeacher_note() + "' where id ='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSubjectQuestionary.teacherConfirm() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int teacherReject() {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_subjectsearch set status ='4',teacher_note='"
				+ this.getTeacher_note() + "' where id ='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSubjectQuestionary.teacherReject() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public SubjectQuestionary getSubjectQuestionary(String studentId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		SubjectQuestionary subjectQuestionary = new OracleSubjectQuestionary();
		// sql = "select
		// student_id,main_job,link,subjectname,subjectcontent,remark,teacher_note,siteteacher_note,status
		// from entity_graduate_subjectsearch where id ='"+this.getId()+"'";
		sql = "select score,score_status,score_note,id,student_id,main_job,link,subjectname,subjectcontent,remark,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id,gender,folk,home_address,work_address,phone,mobilephone,home_postalcode from (select t.score,t.score_status,t.score_note,t.id,t.student_id,t.main_job,t.link,t.subjectname,t.subjectcontent,t.remark,t.teacher_note,t.siteteacher_note,t.status,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id,eui.gender,eui.folk,eui.home_address,eui.work_address,eui.phone,eui.mobilephone,eui.home_postalcode from entity_graduate_subjectsearch t,entity_student_info esi,entity_usernormal_info eui where t.student_id = esi.id and t.student_id = eui.id) where student_id ='"
				+ studentId + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent(rs
						.getString("student_id"));

				subjectQuestionary.setStudent(student);
				subjectQuestionary.setId(rs.getString("id"));
				subjectQuestionary.setMain_job(rs.getString("main_job"));
				subjectQuestionary.setLink(rs.getString("link"));
				subjectQuestionary.setSubjectName(rs.getString("subjectname"));
				subjectQuestionary.setSubjectContent(rs
						.getString("subjectcontent"));
				subjectQuestionary.setRemark(rs.getString("remark"));
				subjectQuestionary
						.setTeacher_note(rs.getString("teacher_note"));
				subjectQuestionary.setSiteTeacher_note(rs
						.getString("siteteacher_note"));
				subjectQuestionary.setStatus(rs.getString("status"));
				subjectQuestionary.setScore(rs.getString("score"));
				subjectQuestionary.setScore_note(rs.getString("score_note"));
				subjectQuestionary.setScore_status(rs.getString("score_status"));
			}
		} catch (java.sql.SQLException es) {
			EntityLog
					.setDebug("OracleSubjectQuestionary@Method:OracleSubjectQuestionary(String id)  sql="
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return subjectQuestionary;
	}

	public int upLoadGraduateDesignWord(String reg_no, String fileLink) {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_subjectsearch egs set egs.status='0' , egs.link ='"
				+ fileLink
				+ "' where egs.student_id = (select id from entity_student_info esi where esi.reg_no ='"
				+ reg_no + "')";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSubjectQuestionary.upLoadGraduateDesignWord(String reg_no,String fileLink) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	public int upLoadGraduateDesignWord(String reg_no, String fileLink,String status) {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_subjectsearch egs set egs.status='"+status+"' , egs.link ='"
				+ fileLink
				+ "' where egs.student_id = (select id from entity_student_info esi where esi.reg_no ='"
				+ reg_no + "')";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSubjectQuestionary.upLoadGraduateDesignWord(String reg_no,String fileLink) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	@Override
	public int updateScore() {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_subjectsearch t set t.score = '" + this.getScore() + "' where t.id = '" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSubjectQuestionary.updateScore() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	@Override
	public int teacherScoreConfirm() {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_subjectsearch t set t.score_status = '1' , t.score_note = '" + this.getScore_note() + "' where t.id = '" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSubjectQuestionary.teacherScoreConfirm() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	@Override
	public int teacherScoreReject() {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_subjectsearch t set t.score_status = '0' , t.score_note = '" + this.getScore_note() + "' where t.id = '" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSubjectQuestionary.teacherScoreReject() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public void updateScore_BatchExe(List scoreList) throws PlatformException {
		dbpool db = new dbpool();
		ArrayList exceptionList = new ArrayList();
		String except = "";
		int count = 0;
		String siteId = "";
		for (int i = 0; i < scoreList.size(); i++) {
			ArrayList sqlList = new ArrayList();
			SubjectQuestionary subjectQuestionary = (SubjectQuestionary) scoreList.get(i);
			String reg_no = "";
			Student student = subjectQuestionary.getStudent();
            
			String sql = "select id from (select 'site'||id from entity_site_info where id='"
				+ student.getStudentInfo().getSite_id()
				+ "' union select 'grade'||id from entity_grade_info where id='"
				+ student.getStudentInfo().getGrade_id()
				+ "' union select 'edutype'||id from entity_edu_type where id='"
				+ student.getStudentInfo().getEdutype_id()
				+ "' union select 'major'||id from entity_major_info where id='"
				+ student.getStudentInfo().getMajor_id() + "')";
		
			if (db.countselect(sql) < 4) {
				exceptionList.add("(" + student.getStudentInfo().getReg_no()
						+ ")" + student.getName() + ",վ�㡢�꼶����λ�רҵID��д���󣬲������");
				continue;
			}
			 
			String updateSql ="update entity_graduate_subjectsearch egd set egd.score = '" + subjectQuestionary.getScore() + "'  where egd.student_id = (select esi.id from entity_student_info esi where esi.reg_no = '"+student.getStudentInfo().getReg_no()+"')";
			String sql3 = "update entity_student_info  set graduate_grade = '"+subjectQuestionary.getScore()+"' where reg_no = '"+student.getStudentInfo().getReg_no()+"'";
			
			if (db.executeUpdate(updateSql) < 1||db.executeUpdate(sql3) < 1) {
				exceptionList.add("(" + student.getStudentInfo().getReg_no() + ")" + student.getName() + "�������");
			} else{
				count++;
			}
		}
		db = null;
		if (exceptionList.size() > 0) {
			exceptionList.add("���֤���ٲ���<br>");

			for (int i = 0; i < exceptionList.size(); i++) {
				except += (String) exceptionList.get(i) + "<br>";
			}
			if (count > 0)
				except += "�ɹ�����" + count + "��ѧԱ�ɼ�<br>";
				throw new PlatformException(except);
		} else {
			except += "�ɹ�����" + count + "��ѧԱ�ɼ�<br>";
			throw new PlatformException(except);
		}
	}
	
	
	//-----------------------------------����Ӵ���ҵ,�����뵽���3ɼ��� 
	public void updateScore_BatchExeTotalGrade(List scoreList,String semesterId) throws PlatformException {
		dbpool db = new dbpool();
		ArrayList exceptionList = new ArrayList();
		String except = "";
		int count = 0;
		String siteId = "";
		for (int i = 0; i < scoreList.size(); i++) {
			ArrayList sqlList = new ArrayList();
			SubjectQuestionary subjectQuestionary = (SubjectQuestionary) scoreList.get(i);
			String reg_no = "";
			Student student = subjectQuestionary.getStudent();
            
			String sql = "select * from (select 'site'||id from entity_site_info where id='"
				+ student.getStudentInfo().getSite_id()
				+ "' union select 'grade'||id from entity_grade_info where id='"
				+ student.getStudentInfo().getGrade_id()
				+ "' union select 'edutype'||id from entity_edu_type where id='"
				+ student.getStudentInfo().getEdutype_id()
				+ "' union select 'major'||id from entity_major_info where id='"
				+ student.getStudentInfo().getMajor_id() + "')";
			/*
			if (db.countselect(sql) < 4) {
				exceptionList.add("(" + student.getStudentInfo().getReg_no()
						+ ")" + student.getName() + ",վ�㡢�꼶����λ�רҵID��д���󣬲������");
				continue;
			}
			*/
			String updateSql ="update entity_graduate_subjectsearch egd set egd.score = '" + subjectQuestionary.getScore() + "'  where egd.student_id = (select esi.id from entity_student_info esi where esi.reg_no = '"+student.getStudentInfo().getReg_no()+"')";
			
//			�ѳɼ����뵽�γ̵����3ɼ���.
			String sql2="update entity_elective ele set ele.total_score='"+subjectQuestionary.getScore()+"' where ele.id= " +
					"(  select ee.id from entity_elective      ee,entity_teach_class   " +
					"etc,entity_course_active eca,entity_course_info   eci,entity_semester_info esis,entity_student_info " +
					" esi where ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id " +
					"= esis.id and ee.student_id = esi.id and eci.id='000011' and esi.reg_no='"+student.getStudentInfo().getReg_no()+"')";
			
			String sql3 = "update entity_student_info  set graduate_grade = '"+subjectQuestionary.getScore()+"' where reg_no = '"+student.getStudentInfo().getReg_no()+"'";
			
			
			List sqllist = new ArrayList();
			sqllist.add(updateSql);
			sqllist.add(sql2);
			sqllist.add(sql3);
			
			if (db.executeUpdateBatch(sqllist) < 1) {
				exceptionList.add("(" + student.getStudentInfo().getReg_no() + ")" + student.getName() + "�������");
			} else{
				count++;
			}
		}
		db = null;
		if (exceptionList.size() > 0) {
			exceptionList.add("���֤���ٲ���<br>");

			for (int i = 0; i < exceptionList.size(); i++) {
				except += (String) exceptionList.get(i) + "<br>";
			}
			if (count > 0)
				except += "�ɹ�����" + count + "��ѧԱ�ɼ�<br>";
				throw new PlatformException(except);
		} else {
			except += "�ɹ�����" + count + "��ѧԱ�ɼ�<br>";
			throw new PlatformException(except);
		}
	}
	
	
	
	
	/**
	 * ��Ӵ���ҵ�ɼ�,���������3ɼ���
	 */
	public int updateScoreToTotalGrade(String homeworkId,String studentId,String semesterId,String courseId,String score){
		dbpool db = new dbpool();
		String sql = "update entity_graduate_subjectsearch t set t.score = '" + this.getScore() + "' where t.id = '" + this.getId() + "'";
		
//		�ѳɼ����뵽�γ̵����3ɼ���.
		String sql2="update entity_elective ele set ele.total_score='"+this.getScore()+"' where ele.id= " +
				"(  select ee.id from entity_elective      ee,entity_teach_class   " +
				"etc,entity_course_active eca,entity_course_info   eci,entity_semester_info esis,entity_student_info " +
				" esi where ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id " +
				"= esis.id and ee.student_id = esi.id and esis.id='"+semesterId+"'and eci.id='"+courseId+"'and esi.id='"+studentId+"')";
	   
		String sql3 = "update entity_student_info  set graduate_grade = '"+this.getScore()+"' where id = '"+studentId+"'";
		
		List sqllist = new ArrayList();
		sqllist.add(sql);
		sqllist.add(sql2);
		sqllist.add(sql3);
		int i = db.executeUpdateBatch(sqllist);
		
		UserUpdateLog.setDebug("OracleSubjectQuestionary.updateScore() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

//----------------------------------------end
	
}
