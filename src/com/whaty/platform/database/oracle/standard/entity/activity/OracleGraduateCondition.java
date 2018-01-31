package com.whaty.platform.database.oracle.standard.entity.activity;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleEduType;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleGrade;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleMajor;
import com.whaty.platform.entity.activity.GraduateCondition;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleGraduateCondition extends GraduateCondition {

	/** Creates a new instance of OracleGraduateCondition */
	public OracleGraduateCondition() {
	}

	public OracleGraduateCondition(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select a.id,major_id,b.name as major_name,grade_id,c.name as grade_name,edutype_id,"
				+ "d.name as edutype_name,condition from entity_graduate_record a,entity_major_info b,entity_grade_info c,entity_edu_type d where a.id = '"
				+ aid
				+ "' and a.major_id=b.id and a.grade_id=c.id and a.edutype_id=d.id";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				OracleMajor major = new OracleMajor();
				major.setId(rs.getString("major_id"));
				major.setName(rs.getString("major_name"));
				this.setMajor(major);
				OracleGrade grade = new OracleGrade();
				grade.setId(rs.getString("grade_id"));
				grade.setName(rs.getString("grade_name"));
				this.setGrade(grade);
				OracleEduType eduType = new OracleEduType();
				eduType.setId(rs.getString("edutype_id"));
				eduType.setName(rs.getString("edutype_name"));
				this.setEduType(eduType);
				this
						.setCondition(getConditionHashMap(rs
								.getString("condition")));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleGraduateCondition(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public OracleGraduateCondition(String major_id, String grade_id,
			String edutype_id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select a.id,major_id,b.name as major_name,grade_id,c.name as grade_name,edutype_id,"
				+ "d.name as edutype_name,condition from entity_graduate_record a,entity_major_info b,entity_grade_info c,"
				+ "entity_edu_type d where a.major_id = '"
				+ major_id
				+ "' and a.grade_id = '"
				+ grade_id
				+ "' and a.edutype_id = '"
				+ edutype_id
				+ "' and a.major_id=b.id and a.grade_id=c.id and a.edutype_id=d.id";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				OracleMajor major = new OracleMajor();
				major.setId(rs.getString("major_id"));
				major.setName(rs.getString("major_name"));
				this.setMajor(major);
				OracleGrade grade = new OracleGrade();
				grade.setId(rs.getString("grade_id"));
				grade.setName(rs.getString("grade_name"));
				this.setGrade(grade);
				OracleEduType eduType = new OracleEduType();
				eduType.setId(rs.getString("edutype_id"));
				eduType.setName(rs.getString("edutype_name"));
				this.setEduType(eduType);
				this
						.setCondition(getConditionHashMap(rs
								.getString("condition")));
			}
		} catch (java.sql.SQLException e) {
			Log
					.setError("OracleGraduateCondition(String major_id, String grade_id,String edutype_id) error"
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_graduate_record (id,major_id,grade_id,edutype_id,condition) values(to_char(s_graduate_record_id.nextval),'"
				+ this.getMajor().getId()
				+ "','"
				+ this.getGrade().getId()
				+ "','" + this.getEduType().getId() + "',?)";
		int i = db.executeUpdate(sql, getConditionXML(this.getCondition()));
		UserAddLog.setDebug("OracleGraduateCondition.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_graduate_record where major_id='"
				+ this.getMajor().getId() + "' and grade_id='"
				+ this.getGrade().getId() + "' and edutype_id='"
				+ this.getEduType().getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleGraduateCondition.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_graduate_record set condition=? where major_id='"
				+ this.getMajor().getId() + "' and grade_id='"
				+ this.getGrade().getId() + "' and edutype_id='"
				+ this.getEduType().getId() + "'";
		int i = db.executeUpdate(sql, getConditionXML(this.getCondition()));
		UserUpdateLog.setDebug("OracleGraduateCondition.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	private String getConditionXML(HashMap con) {
		String xmlStr = "<conditions>";
		Set conSet = con.keySet();
		for (Iterator conIter = conSet.iterator(); conIter.hasNext();) {
			String id = (String) conIter.next();
			xmlStr += "<item><id>" + id + "</id>";
			String section = "";
			String[] name = (String[]) con.get(id);
			for (int i = 0; name != null && i < name.length; i++)
				section += "|" + name[i];
			if (section.length() > 0)
				section = section.substring(1);
			xmlStr += "<con>" + section + "</con></item>";
		}
		return xmlStr + "</conditions>";
	}

	private HashMap getConditionHashMap(String xmlStr) {
		HashMap con = new HashMap();
		try {
			Document doc = DocumentHelper.parseText(xmlStr);
			List idList = doc.selectNodes("//item/id");
			List conList = doc.selectNodes("//item/con");
			String eleIdValue = "";
			String eleConValue = "";
			for (Iterator idIter = idList.iterator(), conIter = conList
					.iterator(); idIter.hasNext();) {
				Element eleId = (Element) idIter.next();
				eleIdValue = eleId.getStringValue();
				String[] cons = null;
				if (conIter.hasNext()) {
					Element eleCon = (Element) conIter.next();
					eleConValue = eleCon.getStringValue();
					cons = eleConValue.split("\\|");
				}
				con.put(eleIdValue, cons);
			}
		} catch (DocumentException de) {
		}
		return con;
	}

	public int isExist() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from entity_graduate_record where major_id='"
				+ this.getMajor().getId() + "' and grade_id='"
				+ this.getGrade().getId() + "' and edutype_id='"
				+ this.getEduType().getId() + "'";
		int i = db.countselect(sql);
		return i;
	}

	/*
	 * public List check(List studentList) throws PlatformException { // TODO
	 * Auto-generated method stub return null; }
	 * 
	 * public List checkByCredit(List studentList, int credit) throws
	 * PlatformException { dbpool db = new dbpool(); ArrayList filterStuList =
	 * new ArrayList(); MyResultSet rs = null; String sql = "select
	 * student_id,credit from (select sum(credit) as credit,student_id as
	 * student_id from " + "(select g.student_id,g.course_id,g.credit from
	 * (select a.student_id as student_id,c.id as course_id," + "c.credit as
	 * credit from lrn_elective a,lrn_course_active b,lrn_course_info c where
	 * a.open_course_id = b.id and b.course_id = c.id " + "and (a.score >= 60 or
	 * a.score < 60 and to_number(a.expend_score)>=60)) g where g.student_id in
	 * ("; for (int i = 0; i < studentList.size(); i++) { if (i <
	 * studentList.size() - 1) { sql += ((Student) studentList.get(i)).getId() +
	 * ","; } else { sql += ((Student) studentList.get(i)).getId() + ")"; } }
	 * sql += " group by student_id) where credit >= " + credit; try { rs =
	 * db.executeQuery(sql); while (rs != null && rs.next()) { OracleStudent
	 * student = new OracleStudent(); student.setId(rs.getString("student_id"));
	 * filterStuList.add(student); } } catch (SQLException e) { } finally {
	 * db.close(rs); db = null; } return filterStuList; }
	 * 
	 * public List checkByCompulsory(List studentList, String major_id, String
	 * edu_type_id, String grade_id) throws PlatformException { dbpool db = new
	 * dbpool(); ArrayList filterStuList = new ArrayList(); MyResultSet rs =
	 * null; String sql = "select student_id,credit from (select sum(credit) as
	 * credit,student_id as student_id from " + "(select
	 * g.student_id,g.course_id,g.credit from (select a.student_id as
	 * student_id,c.id as course_id,c.credit as credit " + "from lrn_elective
	 * a,lrn_course_active b,lrn_course_info c where a.open_course_id = b.id and
	 * b.course_id = c.id and a.score >= 60 " + "union select d.student_id as
	 * student_id,f.id as course_id,f.credit as credit from lrn_elective
	 * d,lrn_course_active e,lrn_course_info f " + "where d.open_course_id =
	 * e.id and e.course_id = f.id and d.score < 60 and
	 * to_number(d.expend_score)>=60) g " + "where g.student_id in ( "; for (int
	 * i = 0; i < studentList.size(); i++) { if (i < studentList.size() - 1) {
	 * sql += ((Student) studentList.get(i)).getId() + ","; } else { sql +=
	 * ((Student) studentList.get(i)).getId() + ")"; } } sql += "and g.course_id
	 * in (select course_id from mng_edu_eduplan where major_id = '" + major_id + "'
	 * and edutype_id = '" + edu_type_id + "' and grade_id = '" + grade_id + "'
	 * and instr(course_type,'޿')>0)) group by student_id" + " having count(*) >=
	 * (select count(*) from mng_edu_eduplan where major_id = '" + major_id + "'
	 * and edutype_id = '" + edu_type_id + "' and grade_id = '" + grade_id + "'
	 * and instr(course_type,'޿')>0) ";
	 * 
	 * try { rs = db.executeQuery(sql); while (rs != null && rs.next()) {
	 * OracleStudent student = new OracleStudent();
	 * student.setId(rs.getString("student_id")); filterStuList.add(student); } }
	 * catch (SQLException e) { } finally { db.close(rs); db = null; } return
	 * filterStuList; }
	 */
}
