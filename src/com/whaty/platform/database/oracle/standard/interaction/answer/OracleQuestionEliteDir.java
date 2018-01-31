package com.whaty.platform.database.oracle.standard.interaction.answer;

import java.util.Date;
import java.util.List;

import com.whaty.platform.DirTree;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourse;
import com.whaty.platform.interaction.answer.QuestionEliteDir;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleQuestionEliteDir extends QuestionEliteDir {
	public OracleQuestionEliteDir() {
	}
	
	public OracleQuestionEliteDir(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,name,note,date,course_id,dir_father"
				+ " from (select id,name,note,to_char(date,'yyyy-mm-dd') as date,course_id,dir_father from interaction_question_elitedir where id = '"
				+ aid + "')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setNote(rs.getString("note"));
				this.setDate(rs.getString("date"));
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("course_id"));
				this.setCourse(course);
				OracleQuestionEliteDir dirFather = new OracleQuestionEliteDir();
				dirFather.setId(rs.getString("dir_father"));
				this.setDirFather(dirFather);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleQuestionEliteDir(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into interaction_question_elitedir (id,name,note,date,course_id,dir_father) values(to_char(s_question_elitedir_id.nextval),'"
				+ this.getName()
				+ "','"
				+ this.getNote()
				+ "','"
				+ this.getDate()
				+ "','"
				+ this.getCourse().getId()
				+ "','"
				+ this.getDirFather().getId() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleQuestionEliteDir.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		if (isChildDirExist() > 0) {
			throw new PlatformException("该目录存在子目录！");
		}
		if (isChildEliteQuestionExist() > 0) {
			throw new PlatformException("该目录存在题目！");
		}
		String sql = "";
		sql = "delete from interaction_question_elitedir where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleQuestionEliteDir.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update interaction_question_elitedir set name='"
				+ this.getName() + "',note='" + this.getNote()
				+ "',date=sysdate,course_id='" + this.getCourse().getId()
				+ "',dir_father='" + this.getDirFather().getId()
				+ "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleQuestionEliteDir.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public String getDirId() {
		// TODO 自动生成方法存根
		return null;
	}

	public String getDirName() {
		// TODO 自动生成方法存根
		return null;
	}

	public List getChildDirs() throws PlatformException {
		// TODO 自动生成方法存根
		return null;
	}

	public List getChildNodes() {
		// TODO 自动生成方法存根
		return null;
	}

	public boolean isRootDir() {
		// TODO 自动生成方法存根
		return false;
	}

	public void moveTo(DirTree fatherdirtree) {
		// TODO 自动生成方法存根

	}

	public DirTree getFatherDir() {
		// TODO 自动生成方法存根
		return null;
	}

	public int isChildDirExist() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from interaction_question_elitedir where dir_father='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		return i;
	}

	public int isChildEliteQuestionExist() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from interaction_elitequestion_info where dir_id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		return i;
	}

}
