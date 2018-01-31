/**
 * 
 */
package com.whaty.platform.database.oracle.standard.training.skill;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.training.basic.OracleTrainingCourse;
import com.whaty.platform.database.oracle.standard.training.basic.OracleTrainingCourseType;
import com.whaty.platform.database.oracle.standard.training.user.OracleTrainingStudent;
import com.whaty.platform.training.basic.TrainingCourse;
import com.whaty.platform.training.skill.Skill;
import com.whaty.platform.training.skill.SkillChain;
import com.whaty.platform.training.skill.StudentSkillStatus;
import com.whaty.platform.training.user.TrainingStudent;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.TrainingLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author chenjian
 * 
 */
public class OracleSkill extends Skill {

	public OracleSkill() {

	}

	public OracleSkill(String skillId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		try {
			String sql = "select id,name,note,status,chain_id from training_skill where id='"
					+ skillId + "'";
			TrainingLog.setDebug(sql);
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				if (rs.getInt("status") == 1)
					this.setActive(true);
				else
					this.setActive(false);
				SkillChain skillChain = new OracleSkillChain();
				skillChain.setId(rs.getString("chain_id"));
				this.setChain(skillChain);
				this.setNote(rs.getString("note"));
			}
		} catch (SQLException e) {

		} finally {
			db.close(rs);
			db = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.skill.Skill#checkUserSkill(java.lang.String)
	 */
	public boolean checkStudentSkill(String userId) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#add()
	 */
	public int add() {
		dbpool db = new dbpool();
		int status = 0;
		if (this.getIsActive())
			status = 1;
		String sql = "insert into training_skill(id,name, status,note,chain_id) values("
				+ "to_char(s_training_id.nextval),'"
				+ this.getName()
				+ "'"
				+ ","
				+ status
				+ ",'"
				+ this.getNote()
				+ "','"
				+ this.getChain().getId() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleSkill.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#update()
	 */
	public int update() {
		dbpool db = new dbpool();
		int status = 0;
		if (this.getIsActive())
			status = 1;
		String sql = "update training_skill set name='" + this.getName()
				+ "',status=" + status + "," + "note=	'" + this.getNote()
				+ "',chain_id='" + this.getChain().getId() + "' where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSkill.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#delete()
	 */
	public int delete() {
		dbpool db = new dbpool();
		String sql = "delete from  training_skill  where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleSkill.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.skill.Skill#addPreSkill(java.util.List)
	 */
	public void addPreSkill(List skillList) throws PlatformException {
		if (skillList.size() > 0) {
			String skillIds = "";
			for (int i = 0; i < skillList.size(); i++) {
				skillIds = skillIds + "'" + ((String) skillList.get(i)) + "',";
			}
			if (skillIds.length() >= 3)
				skillIds = skillIds.substring(0, skillIds.length() - 1);
			String sql = "insert into  training_skill_pre(skill_id,preskill_id)  "
					+ "select '"
					+ this.getId()
					+ "',id from training_skill where id in(" + skillIds + ")";
			dbpool db = new dbpool();
			int i = db.executeUpdate(sql);
			UserAddLog.setDebug("OracleSkill.addRreSkill(List skillList) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.skill.Skill#removePreSkill(java.util.List)
	 */
	public void removePreSkill(List skillList) throws PlatformException {
		if (skillList.size() > 0) {
			String skillIds = "";
			for (int i = 0; i < skillList.size(); i++) {
				skillIds = skillIds + "'" + ((String) skillList.get(i)) + "',";
			}
			if (skillIds.length() >= 3)
				skillIds = skillIds.substring(0, skillIds.length() - 1);
			String sql = "delete from   training_skill_pre where skill_id='"
					+ this.getId() + "' " + "and  preskill_id in(" + skillIds
					+ ")";
			dbpool db = new dbpool();
			int i = db.executeUpdate(sql);
			UserDeleteLog.setDebug("OracleSkill.removeRreSkill(List skillList) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.skill.Skill#getPreSkillNum()
	 */
	public int getPreSkillNum() throws PlatformException {
		dbpool db = new dbpool();
		int num = 0;
		String sql = "select id,name,note,status,chain_id from training_skill a,"
				+ "(select preskill_id from training_skill_pre where skill_id='"
				+ "" + this.getId() + "') b where  a.id=b.preskill_id";
		num = db.countselect(sql);
		
		return num;
	}
	
	public int getPreSkillNum(String id, String name) throws PlatformException {
		dbpool db = new dbpool();
		int num = 0;
		String sql = "select id,name,note,status,chain_id from training_skill a,"
				+ "(select preskill_id from training_skill_pre where skill_id='"
				+ "" + this.getId() + "') b where  a.id=b.preskill_id";
		String conn = "";
		if(id != null && !id.equals(""))
			conn += " and id='" + id + "'";
		if(name != null && !name.equals(""))
			conn += " and name='" + name + "'";
		sql += conn;
		num = db.countselect(sql);
		
		return num;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.skill.Skill#getPreSkillList()
	 */
	public List getPreSkillList() throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List preSkillList = null;
		try {
			preSkillList = new ArrayList();
			String sql = "select id,name,note,status,chain_id from training_skill a,"
					+ "(select preskill_id from training_skill_pre where skill_id='"
					+ "" + this.getId() + "') b where  a.id=b.preskill_id";
			TrainingLog.setDebug(sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				Skill skill = new OracleSkill();
				skill.setId(rs.getString("id"));
				skill.setName(rs.getString("name"));
				if (rs.getInt("status") == 1)
					skill.setActive(true);
				else
					skill.setActive(false);
				SkillChain skillChain = new OracleSkillChain();
				skillChain.setId(rs.getString("chain_id"));
				skill.setChain(skillChain);
				skill.setNote(rs.getString("note"));
				preSkillList.add(skill);
			}
		} catch (SQLException e) {

		} finally {
			db.close(rs);
			db = null;
			return preSkillList;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.skill.Skill#getPreSkillList(Page)
	 */
	public List getPreSkillList(Page page) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List preSkillList = null;
		try {
			preSkillList = new ArrayList();
			String sql = "select id,name,note,status,chain_id from training_skill a,"
					+ "(select preskill_id from training_skill_pre where skill_id='"
					+ "" + this.getId() + "') b where  a.id=b.preskill_id";
			TrainingLog.setDebug(sql);
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				Skill skill = new OracleSkill();
				skill.setId(rs.getString("id"));
				skill.setName(rs.getString("name"));
				if (rs.getInt("status") == 1)
					skill.setActive(true);
				else
					skill.setActive(false);
				OracleSkillChain skillChain = new OracleSkillChain(rs.getString("chain_id"));
				//skillChain.setId(rs.getString("chain_id"));
				skill.setChain(skillChain);
				skill.setNote(rs.getString("note"));
				preSkillList.add(skill);
			}
		} catch (SQLException e) {

		} finally {
			db.close(rs);
			db = null;
			return preSkillList;
		}
	}
	
	public List getPreSkillList(Page page, String id, String name) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List preSkillList = null;
		try {
			preSkillList = new ArrayList();
			String sql = "select id,name,note,status,chain_id from training_skill a,"
					+ "(select preskill_id from training_skill_pre where skill_id='"
					+ "" + this.getId() + "') b where  a.id=b.preskill_id";
			String conn = "";
			if(id != null && !id.equals(""))
				conn += " and id='" + id + "'";
			if(name != null && !name.equals(""))
				conn += " and name='" + name + "'";
			sql += conn;
			TrainingLog.setDebug(sql);
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				Skill skill = new OracleSkill();
				skill.setId(rs.getString("id"));
				skill.setName(rs.getString("name"));
				if (rs.getInt("status") == 1)
					skill.setActive(true);
				else
					skill.setActive(false);
				SkillChain skillChain = new OracleSkillChain();
				skillChain.setId(rs.getString("chain_id"));
				skill.setChain(skillChain);
				skill.setNote(rs.getString("note"));
				preSkillList.add(skill);
			}
		} catch (SQLException e) {

		} finally {
			db.close(rs);
			db = null;
			return preSkillList;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.skill.Skill#addCourse(java.util.List)
	 */
	public void addCourse(List courseList) throws PlatformException {
		if (courseList.size() > 0) {
			String courseIds = "";
			for (int i = 0; i < courseList.size(); i++) {
				courseIds = courseIds + "'" + ((String) courseList.get(i))
						+ "',";
			}
			if (courseIds.length() >= 3)
				courseIds = courseIds.substring(0, courseIds.length() - 1);
			String sql = "insert into  training_skill_course(skill_id,course_id)  "
					+ "select '"
					+ this.getId()
					+ "',id from training_course where id in("
					+ courseIds
					+ ")";
			dbpool db = new dbpool();
			int i = db.executeUpdate(sql);
			UserAddLog.setDebug("OracleSkill.addCourse(List courseList) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.skill.Skill#removeCourse(java.util.List)
	 */
	public void removeCourse(List courseList) throws PlatformException {
		if (courseList.size() > 0) {
			String courseIds = "";
			for (int i = 0; i < courseList.size(); i++) {
				courseIds = courseIds + "'" + ((String) courseList.get(i))
						+ "',";
			}
			if (courseIds.length() >= 3)
				courseIds = courseIds.substring(0, courseIds.length() - 1);
			String sql = "delete from   training_skill_course where skill_id='"
					+ this.getId() + "' " + "and  course_id in(" + courseIds
					+ ")";
			dbpool db = new dbpool();
			int i = db.executeUpdate(sql);
			UserDeleteLog.setDebug("OracleSkill.removeCourse(List courseList) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.skill.Skill#getCourseNum()
	 */
	public int getCourseNum() throws PlatformException {
		dbpool db = new dbpool();
		int num = 0;
		String sql = "select id,name,status,note from training_course a,"
				+ "(select course_id from training_skill_course where skill_id='"
				+ "" + this.getId() + "') b where  a.id=b.course_id";
		num = db.countselect(sql);

		return num;
	}
	
	public int getCourseNum(String id, String name) throws PlatformException {
		dbpool db = new dbpool();
		int num = 0;
		String sql = "select id,name,status,note from training_course a,"
				+ "(select course_id from training_skill_course where skill_id='"
				+ "" + this.getId() + "') b where  a.id=b.course_id";
		String conn = "";
		if(id != null && !id.equals(""))
			conn += " and id='" + id + "'";
		if(name != null && !name.equals(""))
			conn += " and name='" + name + "'";
		sql += conn;
		num = db.countselect(sql);

		return num;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.skill.Skill#getCourseList()
	 */
	public List getCourseList() throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List courseList = null;
		try {
			courseList = new ArrayList();
			String sql = "select id,name,status,note from training_course a,"
					+ "(select course_id from training_skill_course where skill_id='"
					+ "" + this.getId() + "') b where  a.id=b.course_id";
			TrainingLog.setDebug(sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				TrainingCourse course = new OracleTrainingCourse();
				course.setId(rs.getString("id"));
				course.setName(rs.getString("name"));
				course.setStatus(rs.getString("status"));
				course.setNote(rs.getString("note"));
				courseList.add(course);
			}
		} catch (SQLException e) {

		} finally {
			db.close(rs);
			db = null;
			return courseList;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.skill.Skill#getCourseList(Page)
	 */
	public List getCourseList(Page page) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List courseList = null;
		try {
			courseList = new ArrayList();
			String sql = "select id,name,status,note,course_typeid from training_course a,"
					+ "(select course_id from training_skill_course where skill_id='"
					+ "" + this.getId() + "') b where  a.id=b.course_id";
			TrainingLog.setDebug(sql);
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				TrainingCourse course = new OracleTrainingCourse();
				course.setId(rs.getString("id"));
				course.setName(rs.getString("name"));
				course.setStatus(rs.getString("status"));
				course.setNote(rs.getString("note"));
				OracleTrainingCourseType courseType = new OracleTrainingCourseType(rs.getString("course_typeid"));
				course.setTrainingCourseType(courseType);
				courseList.add(course);
			}
		} catch (SQLException e) {

		} finally {
			db.close(rs);
			db = null;
			return courseList;
		}
	}
	
	public List getCourseList(Page page, String id, String name) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List courseList = null;
		try {
			courseList = new ArrayList();
			String sql = "select id,name,status,note from training_course a,"
					+ "(select course_id from training_skill_course where skill_id='"
					+ "" + this.getId() + "') b where  a.id=b.course_id";
			String conn = "";
			if(id != null && !id.equals(""))
				conn += " and id='" + id + "'";
			if(name != null && !name.equals(""))
				conn += " and name='" + name + "'";
			sql += conn;
			
			TrainingLog.setDebug(sql);
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				TrainingCourse course = new OracleTrainingCourse();
				course.setId(rs.getString("id"));
				course.setName(rs.getString("name"));
				course.setStatus(rs.getString("status"));
				course.setNote(rs.getString("note"));
				courseList.add(course);
			}
		} catch (SQLException e) {

		} finally {
			db.close(rs);
			db = null;
			return courseList;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.skill.Skill#isObtained(com.whaty.platform.training.user.TrainingStudent)
	 */
	public boolean isObtained(TrainingStudent student) throws PlatformException {
		String sql = "select skill_id from training_skill_student where student_id='"
				+ student.getId()
				+ " and skill_id ='"
				+ this.getId()
				+ "' and status='" + StudentSkillStatus.OBTAINED + "'";
		dbpool db = new dbpool();
		if (db.countselect(sql) > 0)
			return true;
		else
			return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.skill.Skill#target(java.util.List)
	 */
	public void target(List studentIds) throws PlatformException {
		if (studentIds.size() > 0) {
			String students = "";
			for (int i = 0; i < studentIds.size(); i++) {
				students = students + "'" + ((String) studentIds.get(i)) + "',";
			}
			if (students.length() >= 3)
				students = students.substring(0, students.length() - 1);
			String sql = "insert into  training_skill_student(id,skill_id,student_id,status)  "
					+ "select to_char(s_training_id.nextval),'"
					+ this.getId()
					+ "',"
					+ "id,'"
					+ StudentSkillStatus.TARGET
					+ "' from training_student where id in(" + students + ")";
			dbpool db = new dbpool();
			int i = db.executeUpdate(sql);
			UserAddLog.setDebug("OracleSkill.target(List studentIds) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.skill.Skill#unTarget(java.util.List)
	 */
	public void unTarget(List studentIds) throws PlatformException {
		if (studentIds.size() > 0) {
			String students = "";
			for (int i = 0; i < studentIds.size(); i++) {
				students = students + "'" + ((String) studentIds.get(i)) + "',";
			}
			if (students.length() >= 3)
				students = students.substring(0, students.length() - 1);
			String sql = "delete from training_skill_student where "
					+ "status='" + StudentSkillStatus.TARGET + "' "
					+ "and student_id in(" + students + ") and skill_id='"
					+ this.getId() + "'";
			dbpool db = new dbpool();
			int i = db.executeUpdate(sql);
			UserDeleteLog.setDebug("OracleSkill.unTarget(List studentIds) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.skill.Skill#getRewardStudents()
	 */
	public List getRewardStudents() throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.skill.Skill#reward(java.util.List)
	 */
	public void reward(List studentIds) throws PlatformException {

		if (studentIds.size() > 0) {
			String students = "";
			for (int i = 0; i < studentIds.size(); i++) {
				students = students + "'" + ((String) studentIds.get(i)) + "',";
			}
			if (students.length() >= 3)
				students = students.substring(0, students.length() - 1);
			String sql = "update training_skill_student set status='"
					+ StudentSkillStatus.OBTAINED + "' " + "where skill_id='"
					+ this.getId() + "' and  student_id in(" + students + ")";
			dbpool db = new dbpool();
			int i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleSkill.reward(List studentIds) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.skill.Skill#unRward(java.util.List)
	 */
	public void unRward(List studentIds) throws PlatformException {
		if (studentIds.size() > 0) {
			String students = "";
			for (int i = 0; i < studentIds.size(); i++) {
				students = students + "'" + ((String) studentIds.get(i)) + "',";
			}
			if (students.length() >= 3)
				students = students.substring(0, students.length() - 1);
			String sql = "update training_skill_student set status='"
					+ StudentSkillStatus.UNOBTAINED + "' " + "where skill_id='"
					+ this.getId() + "' and  student_id in(" + students + ")";
			dbpool db = new dbpool();
			int i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleSkill.unReward(List studentIds) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}

	}

	public int getUnCheckedStudentNum() throws PlatformException {
		dbpool db = new dbpool();
		int num = 0;
		String sql = "select id,name,status,note from training_student a,"
				+ "(select student_id from training_skill_student where skill_id='"
				+ "" + this.getId() + "' and status='"
				+ StudentSkillStatus.CHECKAPPLY
				+ "') b where  a.id=b.student_id";
		num = db.countselect(sql);
		return num;
	}
	
	public int getUnCheckedStudentNum(String id, String name) throws PlatformException {
		dbpool db = new dbpool();
		int num = 0;
		String sql = "select id,name,status,note from training_student a,"
				+ "(select student_id from training_skill_student where skill_id='"
				+ "" + this.getId() + "' and status='"
				+ StudentSkillStatus.CHECKAPPLY
				+ "') b where  a.id=b.student_id";
		String conn = "";
		if(id != null && !id.equals(""))
			conn += " and id='" + id + "'";
		if(name != null && !name.equals(""))
			conn += " and name='" + name + "'";
		sql += conn;
		num = db.countselect(sql);
		return num;
	}

	public List getUnCheckedStudentList() throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List studentList = null;
		try {
			studentList = new ArrayList();
			String sql = "select id,name,status,note from training_student a,"
					+ "(select student_id from training_skill_student where skill_id='"
					+ "" + this.getId() + "' and status='"
					+ StudentSkillStatus.CHECKAPPLY
					+ "') b where  a.id=b.student_id";
			TrainingLog.setDebug(sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				TrainingStudent student = new OracleTrainingStudent();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setNote(rs.getString("note"));
				studentList.add(student);
			}
		} catch (SQLException e) {

		} finally {
			db.close(rs);
			db = null;
			return studentList;
		}
	}

	public List getUnCheckedStudentList(Page page) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List studentList = null;
		try {
			studentList = new ArrayList();
			String sql = "select id,name,status,note from training_student a,"
					+ "(select student_id from training_skill_student where skill_id='"
					+ "" + this.getId() + "' and status='"
					+ StudentSkillStatus.CHECKAPPLY
					+ "') b where  a.id=b.student_id";
			TrainingLog.setDebug(sql);
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				TrainingStudent student = new OracleTrainingStudent();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setNote(rs.getString("note"));
				studentList.add(student);
			}
		} catch (SQLException e) {

		} finally {
			db.close(rs);
			db = null;
			return studentList;
		}
	}
	
	public List getUnCheckedStudentList(Page page, String id, String name) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List studentList = null;
		try {
			studentList = new ArrayList();
			String sql = "select id,name,status,note from training_student a,"
					+ "(select student_id from training_skill_student where skill_id='"
					+ "" + this.getId() + "' and status='"
					+ StudentSkillStatus.CHECKAPPLY
					+ "') b where  a.id=b.student_id";
			String conn = "";
			if(id != null && !id.equals(""))
				conn += " and id='" + id + "'";
			if(name != null && !name.equals(""))
				conn += " and name='" + name + "'";
			sql += conn;
			TrainingLog.setDebug(sql);
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				TrainingStudent student = new OracleTrainingStudent();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setNote(rs.getString("note"));
				studentList.add(student);
			}
		} catch (SQLException e) {

		} finally {
			db.close(rs);
			db = null;
			return studentList;
		}
	}

	public void removeStudent(List studentList) throws PlatformException {
		if (studentList.size() > 0) {
			String studentIds = "";
			for (int i = 0; i < studentList.size(); i++) {
				studentIds = studentIds + "'" + ((String) studentList.get(i))
						+ "',";
			}
			if (studentIds.length() >= 3)
				studentIds = studentIds.substring(0, studentIds.length() - 1);
			String sql = "delete from   training_skill_student where skill_id='"
					+ this.getId()
					+ "' "
					+ "and  student_id in("
					+ studentIds
					+ ")";			
			dbpool db = new dbpool();
			int i = db.executeUpdate(sql);
			UserDeleteLog.setDebug("OracleSkill.removeStudent(List studentList) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}
	}

	public int getCheckedStudentNum() throws PlatformException {
		dbpool db = new dbpool();
		int num = 0;
		String sql = "select id,name,status,note from training_student a,"
				+ "(select student_id from training_skill_student where skill_id='"
				+ "" + this.getId() + "' and status='"
				+ StudentSkillStatus.APPLYED + "') b where  a.id=b.student_id";
		num = db.countselect(sql);

		return num;
	}
	
	public int getCheckedStudentNum(String id, String name) throws PlatformException {
		dbpool db = new dbpool();
		int num = 0;
		String sql = "select id,name,status,note from training_student a,"
				+ "(select student_id from training_skill_student where skill_id='"
				+ "" + this.getId() + "' and status='"
				+ StudentSkillStatus.APPLYED + "') b where  a.id=b.student_id";
		String conn = "";
		if(id != null && !id.equals(""))
			conn += " and id='" + id + "'";
		if(name != null && !name.equals(""))
			conn += " and name='" + name + "'";
		sql += conn;
		
		num = db.countselect(sql);

		return num;
	}

	public List getCheckedStudentList() throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List studentList = null;
		try {
			studentList = new ArrayList();
			String sql = "select id,name,status,note from training_student a,"
					+ "(select student_id from training_skill_student where skill_id='"
					+ "" + this.getId() + "' and status='"
					+ StudentSkillStatus.APPLYED
					+ "') b where  a.id=b.student_id";
			TrainingLog.setDebug(sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				TrainingStudent student = new OracleTrainingStudent();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setNote(rs.getString("note"));
				studentList.add(student);
			}
		} catch (SQLException e) {

		} finally {
			db.close(rs);
			db = null;
			return studentList;
		}
	}

	public List getCheckedStudentList(Page page) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List studentList = null;
		try {
			studentList = new ArrayList();
			String sql = "select id,name,status,note from training_student a,"
					+ "(select student_id from training_skill_student where skill_id='"
					+ "" + this.getId() + "' and status='"
					+ StudentSkillStatus.APPLYED
					+ "') b where  a.id=b.student_id";
			TrainingLog.setDebug(sql);
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				TrainingStudent student = new OracleTrainingStudent();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setNote(rs.getString("note"));
				studentList.add(student);
			}
		} catch (SQLException e) {

		} finally {
			db.close(rs);
			db = null;
			return studentList;
		}
	}
	
	public List getCheckedStudentList(Page page, String id, String name) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List studentList = null;
		try {
			studentList = new ArrayList();
			String sql = "select id,name,status,note from training_student a,"
					+ "(select student_id from training_skill_student where skill_id='"
					+ "" + this.getId() + "' and status='"
					+ StudentSkillStatus.APPLYED
					+ "') b where  a.id=b.student_id";
			String conn = "";
			if(id != null && !id.equals(""))
				conn += " and id='" + id + "'";
			if(name != null && !name.equals(""))
				conn += " and name='" + name + "'";
			sql += conn;
			
			TrainingLog.setDebug(sql);
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				TrainingStudent student = new OracleTrainingStudent();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setNote(rs.getString("note"));
				studentList.add(student);
			}
		} catch (SQLException e) {

		} finally {
			db.close(rs);
			db = null;
			return studentList;
		}
	}

}
