package com.whaty.platform.database.oracle.standard.entity.graduatedesign;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.entity.graduatedesign.Discourse;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleDiscourse extends Discourse {
	public OracleDiscourse() {

	}

	public OracleDiscourse(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select score_note,score_status,id,student_id,link,discoursename,discoursecontent,remark,requisitiontype,requisitiongrade,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id score_status,score_note from (select egd.id,egd.student_id,egd.link,egd.discoursename,egd.discoursecontent,egd.remark,egd.requisitiontype,egd.requisitiongrade,egd.teacher_note,egd.siteteacher_note,egd.status,egd.score_status,egd.score_note ,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id from entity_graduate_discourse egd ,entity_student_info  esi,entity_usernormal_info   eui where egd.student_id = esi.id and egd.student_id = eui.id)  where id ='"+id+"'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				Student student = new OracleStudent(rs.getString("student_id"));
				this.setStudent(student);
				this.setLink(rs.getString("link"));
				this.setDiscourseName(rs.getString("discoursename"));
				this.setDiscourseContent(rs.getString("discoursecontent"));
				this.setRemark(rs.getString("remark"));
				this.setRequisitionType(rs.getString("requisitiontype"));
				this.setRequisitionGrade(rs.getString("requisitiongrade"));
				this.setTeacher_note(rs.getString("teacher_note"));
				this.setSiteTeacher_note(rs.getString("siteteacher_note"));
				this.setStatus(rs.getString("status"));
				this.setScore_status(rs.getString("score_status"));
				this.setScore_note(rs.getString("score_note"));
			}
		} catch (SQLException e) {
			EntityLog.setError("OracleDiscourse(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_graduate_discourse (id,student_id,link,discoursename,discoursecontent,remark,requisitiontype,requisitiongrade,status) values ("
				+ "to_char(s_entity_graduate_discourse_id.nextval),to_char('"
				+ this.getStudent().getId()
				+ "'),to_char('"
				+ this.getLink()
				+ "'),to_char('"
				+ this.getDiscourseName()
				+ "'),to_char('"
				+ this.getDiscourseContent()
				+ "'),to_char('"
				+ this.getRemark()
				+ "'),to_char('"
				+ this.getRequisitionType()
				+ "'),to_char('" 
				+ this.getRequisitionGrade()
				+ "'),to_char('"+this.getStatus()+"'))";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleDiscourse.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_graduate_discourse set student_id ='"
				+ this.getStudent().getId() 
				+ "',link ='" + this.getLink()
				+ "',discoursename='"
				+ this.getDiscourseName()
				+ "',discoursecontent='" 
				+ this.getDiscourseContent()
				+ "',remark='"
				+ this.getRemark()
				+ "',requisitiontype='"
				+ this.getRequisitionType() 
				+ "',requisitiongrade='"
				+ this.getRequisitionGrade()
				+ "',teacher_note='"
				+ this.getTeacher_note() 
				+ "',siteteacher_note='"
				+ this.getSiteTeacher_note()
				+ "',status='"
				+ this.getStatus()
				+ "' where id ='" 
				+ this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleDiscourse.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "delete from entity_graduate_discourse  where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleDiscourse.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int siteTeacherConfirm() {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_discourse set status ='1',siteteacher_note = '"+this.getSiteTeacher_note()+"' where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleDiscourse.siteTeacherConfirm() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int siteTeacherReject() {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_discourse set status ='2',siteteacher_note = '"+this.getSiteTeacher_note()+"' where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleDiscourse.siteTeacherReject() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int teacherConfirm() {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_discourse set status ='3',teacher_note = '"+this.getTeacher_note()+"' where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleDiscourse.teacherConfirm() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int teacherReject() {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_discourse set status ='4',teacher_note = '"+this.getTeacher_note()+"' where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleDiscourse.teacherReject() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int updateGrade() {
		dbpool db = new dbpool();
//		String sql = "update entity_graduate_discourse set REQUISITIONGRADE ='"+this.getRequisitionGrade()+"' where id ='"
//				+ this.getId() + "'";
		String sql = "update entity_graduate_discourse set requisitiongrade ='"+this.getRequisitionGrade()+"' where id ='"
		+ this.getId() + "'";
		
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleDiscourse.updateGrade() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	
	/**
	 * 添加成绩,并把成绩加到总评中
	 */

	public int updateGradeAndTotalGrade(String studentId,String courseId,String semesterId) {
		dbpool db = new dbpool();
//		String sql = "update entity_graduate_discourse set REQUISITIONGRADE ='"+this.getRequisitionGrade()+"' where id ='"
//				+ this.getId() + "'";
		String sql = "update entity_graduate_discourse set requisitiongrade ='"+this.getRequisitionGrade()+"' where id ='"
		+ this.getId() + "'";
		
		//把成绩加入到课程的总评成绩中.
		String sql2="update entity_elective ele set ele.total_score='"+this.getRequisitionGrade()+"' where ele.id= " +
				"(  select ee.id from entity_elective      ee,entity_teach_class   " +
				"etc,entity_course_active eca,entity_course_info   eci,entity_semester_info esis,entity_student_info " +
				" esi where ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id " +
				"= esis.id and ee.student_id = esi.id and esis.id='"+semesterId+"' and eci.id='"+courseId+"' and esi.id='"+studentId+"')";
		
		String sql3 ="update entity_student_info set graduate_grade = '"+this.getRequisitionGrade()+"' where id = '"+studentId+"'";
		List sqllist = new ArrayList();
		sqllist.add(sql);
		sqllist.add(sql2);
		sqllist.add(sql3);
		int i = db.executeUpdateBatch(sqllist);
		UserUpdateLog.setDebug("OracleDiscourse.updateGrade() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public Discourse getDiscourse(String studentId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		Discourse oracleDiscourse = oracleDiscourse = new OracleDiscourse();
		String sql = "";
		sql = "select id,student_id,link,discoursename,discoursecontent,remark,requisitiontype,requisitiongrade,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id  from (select egd.id,egd.student_id,egd.link,egd.discoursename,egd.discoursecontent,egd.remark,egd.requisitiontype,egd.requisitiongrade,egd.teacher_note,egd.siteteacher_note,egd.status,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id from entity_graduate_discourse egd ,entity_student_info  esi,entity_usernormal_info   eui where egd.student_id = esi.id and egd.student_id = eui.id)  where student_id ='"+studentId+"'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				
				Student student = new OracleStudent(rs.getString("student_id"));
				oracleDiscourse.setStudent(student);
				oracleDiscourse.setLink(rs.getString("link"));
				oracleDiscourse.setId(rs.getString("id"));
				oracleDiscourse.setDiscourseName(rs.getString("discoursename"));
				oracleDiscourse.setDiscourseContent(rs.getString("discoursecontent"));
				oracleDiscourse.setRemark(rs.getString("remark"));
				oracleDiscourse.setRequisitionType(rs.getString("requisitiontype"));
				oracleDiscourse.setRequisitionGrade(rs.getString("requisitiongrade"));
				oracleDiscourse.setTeacher_note(rs.getString("teacher_note"));
				oracleDiscourse.setSiteTeacher_note(rs.getString("siteteacher_note"));
				oracleDiscourse.setStatus(rs.getString("status"));
			}
		} catch (SQLException e) {
			EntityLog.setError("OracleDiscourse(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return oracleDiscourse;
	  }

	public int upLoadGraduateDesignWord(String reg_no, String fileLink) {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_discourse egs set egs.link ='"
				+ fileLink
				+ "' where egs.student_id = (select id from entity_student_info esi where esi.reg_no ='"
				+ reg_no + "')";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleDiscourse.upLoadGraduateDesignWord(String reg_no,String fileLink) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public void graduateAssessGradeAddBatch(List discourseList) throws PlatformException{
		dbpool db = new dbpool();
		ArrayList exceptionList = new ArrayList();
		String except = "";
		int count = 0;
		String siteId = "";
		for (int i = 0; i < discourseList.size(); i++) {
			ArrayList sqlList = new ArrayList();
			Discourse discourse = (OracleDiscourse) discourseList.get(i);
			String reg_no = "";
			Student student = discourse.getStudent();
            
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
						+ ")" + student.getName() + ",站点、年级、层次或专业ID填写有误，插入错误！");
				continue;
			}
			String typeSql ="select * from entity_graduate_discourse egds where " +
					"egds.requisitiontype ='2'  " +
					"and egds.student_id = (select esi.id from entity_student_info esi where esi.reg_no = '"+student.getStudentInfo().getReg_no()+"')";
			if (db.countselect(typeSql) > 0) {
				exceptionList.add("(" + student.getStudentInfo().getReg_no()
						+ ")" + student.getName() + ",该学生成绩为答辩成绩，插入错误！");
				continue;
			}
			//String updateSql ="update entity_graduate_discourse egd set egd.requisitiongrade = '"+discourse.getRequisitionGrade()+"' where egd.student_id = (select esi.id from entity_student_info esi where esi.reg_no = '"+student.getStudentInfo().getReg_no()+"')";
			String updateSql ="update entity_graduate_discourse egd set egd.requisitiongrade = '"+discourse.getRequisitionGrade()+"' where egd.student_id = (select esi.id from entity_student_info esi where esi.reg_no = '"+student.getStudentInfo().getReg_no()+"')";
			String sql3 = "update entity_student_info set GRADUATE_GRADE ='"+discourse.getRequisitionGrade()+"'  where reg_no = '"+student.getStudentInfo().getReg_no()+"'";
			
			if (db.executeUpdate(updateSql) < 1||db.executeUpdate(sql3) < 1) {
				exceptionList.add("(" + student.getStudentInfo().getReg_no() + ")" + student.getName() + "插入错误");
			} else{
				count++;
			}
		}
		db = null;
		if (exceptionList.size() > 0) {
			exceptionList.add("请查证后再插入<br>");

			for (int i = 0; i < exceptionList.size(); i++) {
				except += (String) exceptionList.get(i) + "<br>";
			}
			if (count > 0)
				except += "成功插入" + count + "名学员成绩<br>";
				throw new PlatformException(except);
		} else {
			except += "成功插入" + count + "名学员成绩<br>";
			throw new PlatformException(except);
		}
	}
	
	public void graduateRejoinGradeAddBatch(List discourseList) throws PlatformException{
		dbpool db = new dbpool();
		ArrayList exceptionList = new ArrayList();
		String except = "";
		int count = 0;
		String siteId = "";
		for (int i = 0; i < discourseList.size(); i++) {
			ArrayList sqlList = new ArrayList();
			Discourse discourse = (OracleDiscourse) discourseList.get(i);
			String reg_no = "";
			Student student = discourse.getStudent();
            
			String sql = "select * from (select 'site'||id from entity_site_info where id='"
				+ student.getStudentInfo().getSite_id()
				+ "' union select 'grade'||id from entity_grade_info where id='"
				+ student.getStudentInfo().getGrade_id()
				+ "' union select 'edutype'||id from entity_edu_type where id='"
				+ student.getStudentInfo().getEdutype_id()
				+ "' union select 'major'||id from entity_major_info where id='"
				+ student.getStudentInfo().getMajor_id() + "')";
		
			if (db.countselect(sql) < 4) {
				exceptionList.add("(" + student.getStudentInfo().getReg_no()
						+ ")" + student.getName() + ",站点、年级、层次或专业ID填写有误，插入错误！");
				continue;
			}
			String typeSql ="select * from entity_graduate_discourse egds where " +
					"egds.requisitiontype ='1'  " +
					"and egds.student_id = (select esi.id from entity_student_info esi where esi.reg_no = '"+student.getStudentInfo().getReg_no()+"')";
			if (db.countselect(typeSql) > 0) {
				exceptionList.add("(" + student.getStudentInfo().getReg_no()
						+ ")" + student.getName() + ",该学生成绩为评审成绩，插入错误！");
				continue;
			}
			String updateSql ="update entity_graduate_discourse egd set egd.requisitiongrade = '"+discourse.getRequisitionGrade()+"' where egd.student_id = (select esi.id from entity_student_info esi where esi.reg_no = '"+student.getStudentInfo().getReg_no()+"')";
			String sql3 = "update entity_student_info set GRADUATE_GRADE ='"+discourse.getRequisitionGrade()+"'  where reg_no = '"+student.getStudentInfo().getReg_no()+"'";
			
			if (db.executeUpdate(updateSql) < 1||db.executeUpdate(sql3) < 1) {
				exceptionList.add("(" + student.getStudentInfo().getReg_no() + ")" + student.getName() + "插入错误");
			} else{
				count++;
			}
		}
		db = null;
		if (exceptionList.size() > 0) {
			exceptionList.add("请查证后再插入<br>");

			for (int i = 0; i < exceptionList.size(); i++) {
				except += (String) exceptionList.get(i) + "<br>";
			}
			if (count > 0)
				except += "成功插入" + count + "名学员成绩<br>";
				throw new PlatformException(except);
		} else {
			except += "成功插入" + count + "名学员成绩<br>";
			throw new PlatformException(except);
		}
	}
	
	//------------------------批量导入成绩,并把成绩加入到总评成绩中 lwx 2008-06-07
	public void graduateAssessGradeAddBatchToTotalGrade(List discourseList,String semesterId) throws PlatformException{
		dbpool db = new dbpool();
		ArrayList exceptionList = new ArrayList();
		String except = "";
		int count = 0;
		String siteId = "";
		for (int i = 0; i < discourseList.size(); i++) {
			ArrayList sqlList = new ArrayList();
			Discourse discourse = (OracleDiscourse) discourseList.get(i);
			String reg_no = "";
			Student student = discourse.getStudent();
            
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
						+ ")" + student.getName() + ",站点、年级、层次或专业ID填写有误，插入错误！");
				continue;
			}*/
			String typeSql ="select * from entity_graduate_discourse egds where " +
					"egds.requisitiontype ='2'  " +
					"and egds.student_id = (select esi.id from entity_student_info esi where esi.reg_no = '"+student.getStudentInfo().getReg_no()+"')";
			if (db.countselect(typeSql) > 0) {
				exceptionList.add("(" + student.getStudentInfo().getReg_no()
						+ ")" + student.getName() + ",该学生成绩为答辩成绩，插入错误！");
				continue;
			}
			//String updateSql ="update entity_graduate_discourse egd set egd.requisitiongrade = '"+discourse.getRequisitionGrade()+"' where egd.student_id = (select esi.id from entity_student_info esi where esi.reg_no = '"+student.getStudentInfo().getReg_no()+"')";
			String updateSql ="update entity_graduate_discourse egd set egd.requisitiongrade = '"+discourse.getRequisitionGrade()+"' where egd.student_id = (select esi.id from entity_student_info esi where esi.reg_no = '"+student.getStudentInfo().getReg_no()+"')";
			
//			把成绩加入到课程的总评成绩中.
			String sql2="update entity_elective ele set ele.total_score='"+discourse.getRequisitionGrade()+"' where ele.id= " +
					"(  select ee.id from entity_elective      ee,entity_teach_class   " +
					"etc,entity_course_active eca,entity_course_info   eci,entity_semester_info esis,entity_student_info " +
					" esi where ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id " +
					"= esis.id and ee.student_id = esi.id and eci.id='000001'and esi.reg_no='"+student.getStudentInfo().getReg_no()+"')";
			
			String sql3 = "update entity_student_info set GRADUATE_GRADE ='"+discourse.getRequisitionGrade()+"'  where reg_no = '"+student.getStudentInfo().getReg_no()+"'";
			List sqllist = new ArrayList();
			sqllist.add(updateSql);
			sqllist.add(sql2);
			sqllist.add(sql3);
			if (db.executeUpdateBatch(sqllist) < 1) {
				exceptionList.add("(" + student.getStudentInfo().getReg_no() + ")" + student.getName() + "插入错误");
			} else{
				count++;
			}
		}
		db = null;
		if (exceptionList.size() > 0) {
			exceptionList.add("请查证后再插入<br>");

			for (int i = 0; i < exceptionList.size(); i++) {
				except += (String) exceptionList.get(i) + "<br>";
			}
			if (count > 0)
				except += "成功插入" + count + "名学员成绩<br>";
				throw new PlatformException(except);
		} else {
			except += "成功插入" + count + "名学员成绩<br>";
			throw new PlatformException(except);
		}
	}
	
	public void graduateRejoinGradeAddBatchToTotalGrade(List discourseList,String semesterId) throws PlatformException{
		dbpool db = new dbpool();
		ArrayList exceptionList = new ArrayList();
		String except = "";
		int count = 0;
		String siteId = "";
		for (int i = 0; i < discourseList.size(); i++) {
			ArrayList sqlList = new ArrayList();
			Discourse discourse = (OracleDiscourse) discourseList.get(i);
			String reg_no = "";
			Student student = discourse.getStudent();
            
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
						+ ")" + student.getName() + ",站点、年级、层次或专业ID填写有误，插入错误！");
				continue;
			}*/
			String typeSql ="select * from entity_graduate_discourse egds where " +
					"egds.requisitiontype ='1'  " +
					"and egds.student_id = (select esi.id from entity_student_info esi where esi.reg_no = '"+student.getStudentInfo().getReg_no()+"')";
			if (db.countselect(typeSql) > 0) {
				exceptionList.add("(" + student.getStudentInfo().getReg_no()
						+ ")" + student.getName() + ",该学生成绩为评审成绩，插入错误！");
				continue;
			}
			String updateSql ="update entity_graduate_discourse egd set egd.requisitiongrade = '"+discourse.getRequisitionGrade()+"' where egd.student_id = (select esi.id from entity_student_info esi where esi.reg_no = '"+student.getStudentInfo().getReg_no()+"')";
			
//			把成绩加入到课程的总评成绩中.
			String sql2="update entity_elective ele set ele.total_score='"+discourse.getRequisitionGrade()+"' where ele.id= " +
					"(  select ee.id from entity_elective      ee,entity_teach_class   " +
					"etc,entity_course_active eca,entity_course_info   eci,entity_semester_info esis,entity_student_info " +
					" esi where ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id " +
					"= esis.id and ee.student_id = esi.id and eci.id='000001'and esi.reg_no='"+student.getStudentInfo().getReg_no()+"')";
			String sql3 = "update entity_student_info set GRADUATE_GRADE ='"+discourse.getRequisitionGrade()+"'  where reg_no = '"+student.getStudentInfo().getReg_no()+"'";
			
			List sqllist = new ArrayList();
			sqllist.add(updateSql);
			sqllist.add(sql2);
			sqllist.add(sql3);
			
			if (db.executeUpdateBatch(sqllist) < 1) {
				exceptionList.add("(" + student.getStudentInfo().getReg_no() + ")" + student.getName() + "插入错误");
			} else{
				count++;
			}
		}
		db = null;
		if (exceptionList.size() > 0) {
			exceptionList.add("请查证后再插入<br>");

			for (int i = 0; i < exceptionList.size(); i++) {
				except += (String) exceptionList.get(i) + "<br>";
			}
			if (count > 0)
				except += "成功插入" + count + "名学员成绩<br>";
				throw new PlatformException(except);
		} else {
			except += "成功插入" + count + "名学员成绩<br>";
			throw new PlatformException(except);
		}
	}
	
	//--------------------------------end 

	@Override
	public int teacherScoreConfirm() {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_discourse t set t.score_status = '1' , t.score_note = '" + this.getScore_note() + "' where t.id = '" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleDiscourse.teacherScoreConfirm() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	@Override
	public int teacherScoreReject() {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_discourse t set t.score_status = '0' , t.score_note = '" + this.getScore_note() + "' where t.id = '" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleDiscourse.teacherScoreReject() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}


}
