package com.whaty.platform.database.oracle.standard.entity.fee.level;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.entity.fee.level.ChargeLevelList;
import com.whaty.platform.entity.fee.level.ChargeType;
import com.whaty.platform.entity.user.StudentEduInfo;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.util.log.Log;

public class OracleChargeLevelList extends ChargeLevelList {

	public List searchChargeLevelByType(Page page, List searchList,
			List orderList, String type) {
		dbpool db = new dbpool();
		ArrayList list = new ArrayList();
		MyResultSet rs = null;
		String sql = getChargeLevelSQL(searchList, orderList, type);
		System.out.print(sql);
		try {
			if (page != null) {
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleUserChargeLevelOfType chargeLevel = new OracleUserChargeLevelOfType();
				OracleStudent student = new OracleStudent();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("user_id"));
				student.setName(rs.getString("name"));
				student.setLoginId(rs.getString("reg_no"));
				eduInfo.setClass_id(rs.getString("class_id"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));
				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setGrade_name(rs.getString("grade_name"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("site_name"));
				student.setStudentInfo(eduInfo);
				chargeLevel.setStudent(student);
				chargeLevel.setValue(rs.getString("typevalue"));
				list.add(chargeLevel);
			}
		} catch (java.sql.SQLException e) {
			Log
					.setError("OracleChargeLevelList.searchChargeLevelByType() error! "
							+ sql);
		} finally {
			db.close(rs);
			db = null;

		}
		return list;
	}

	public int searchChargeLevelByTypeNum(List searchList, String type) {
		dbpool db = new dbpool();
		return db.countselect(getChargeLevelSQL(searchList, null, type));
	}

	private String getChargeLevelSQL(List searchList, List orderList,
			String type) {

		OracleChargeLevel oracleChargeLevel = new OracleChargeLevel();
		String field = ((String) oracleChargeLevel.chargeTypeFieldValue
				.get(type));
		String studentSQL = "select s.id as id, s.name as name,s.class_id,"
				+ "s.edutype_id,s.grade_id ,s.site_id,s.major_id,s.reg_no,c.name as class_name,"
				+ "g.name as grade_name,si.name as site_name,"
				+ "m.name as major_name,e.name as edutype_name "
				+ "from entity_student_info s,entity_site_info si,entity_grade_info g,entity_major_info m,"
				+ "entity_edu_type e,entity_class_info c where s.grade_id = g.id(+) and "
				+ "s.major_id = m.id(+) and s.site_id = si.id(+) and s.edutype_id = e.id(+) and s.class_id = c.id(+) "
				+ Conditions.convertToAndCondition(searchList);

		String sql = "select user_id,typevalue,name,reg_no,grade_id,grade_name,"
				+ "major_id,major_name,site_id,site_name,edutype_id,edutype_name,class_id,class_name"
				+ " from (select b.id as user_id,nvl(a."
				+ field
				+ ",'') as typevalue,b.name,b.reg_no,b.grade_id,b.grade_name,b.major_id,b.major_name,"
				+ "b.site_id,b.site_name,b.edutype_id,b.edutype_name,b.class_id,b.class_name "
				+ " from entity_userfee_level a, ("
				+ studentSQL
				+ ") b where a.user_id(+)=b.id"
				+ Conditions.convertToCondition(null, orderList) + ")";
		EntityLog.setDebug(sql);
		return sql;
	}

	private String getChargeLevelsSQL(List searchList, List orderList) {

		OracleChargeLevel oracleChargeLevel = new OracleChargeLevel();
		List chargeTypeList = ChargeType.types();
		String fields = "";
		String exfields = "";
		for (int i = 0; i < chargeTypeList.size(); i++) {
			String type = (String) chargeTypeList.get(i);
			type = ((String) oracleChargeLevel.chargeTypeFieldValue.get(type));
			exfields += type + ",";
			fields += "nvl(a." + type + ",'') as " + type + ",";
		}

		String studentSQL = "select s.id as id, s.name as name,s.class_id,"
				+ "s.edutype_id,s.grade_id ,s.site_id,s.major_id,s.reg_no,c.name as class_name,"
				+ "g.name as grade_name,si.name as site_name,"
				+ "m.name as major_name,e.name as edutype_name "
				+ "from entity_student_info s,entity_site_info si,entity_grade_info g,entity_major_info m,"
				+ "entity_edu_type e,entity_class_info c where s.grade_id = g.id(+) and "
				+ "s.major_id = m.id(+) and s.site_id = si.id(+) and s.edutype_id = e.id(+) and s.class_id = c.id(+) ";
		studentSQL = "select id, name, class_id, edutype_id, grade_id, site_id, major_id, reg_no, class_name,"
				+ "grade_name, site_name, major_name, edutype_name from("
				+ studentSQL
				+ ") "
				+ Conditions.convertToCondition(searchList, null);

		String sql = "select user_id,"
				+ exfields
				+ "name,reg_no,grade_id,grade_name,"
				+ "major_id,major_name,site_id,site_name,edutype_id,edutype_name,class_id,class_name"
				+ " from (select b.id as user_id,"
				+ fields
				+ "b.name,b.reg_no,b.grade_id,b.grade_name,b.major_id,b.major_name,"
				+ "b.site_id,b.site_name,b.edutype_id,b.edutype_name,b.class_id,b.class_name "
				+ " from entity_userfee_level a, (" + studentSQL
				+ ") b where a.user_id(+)=b.id"
				+ Conditions.convertToCondition(null, orderList) + ")";
		EntityLog.setDebug(sql);
		return sql;
	}
	private String getRecruitChargeLevelsSQL(List searchList, List orderList) {

		OracleChargeLevel oracleChargeLevel = new OracleChargeLevel();
		List chargeTypeList = ChargeType.types();
		String fields = "";
		String exfields = "";
		for (int i = 0; i < chargeTypeList.size(); i++) {
			String type = (String) chargeTypeList.get(i);
			type = ((String) oracleChargeLevel.chargeTypeFieldValue.get(type));
			exfields += type + ",";
			fields += "nvl(a." + type + ",'') as " + type + ",";
		}

		String studentSQL = "select s.id as id, s.name as name,"
				+ "s.edutype_id,s.grade_id ,s.site_id,s.major_id,s.reg_no,"
				+ "g.name as grade_name,si.name as site_name,"
				+ "m.name as major_name,e.name as edutype_name "
				+ "from recruit_student_info s,entity_site_info si,entity_grade_info g,entity_major_info m,"
				+ "entity_edu_type e where s.reg_no is not null and s.reg_no <> 'null' and register_status='0' and s.grade_id = g.id(+) and "
				+ "s.major_id = m.id(+) and s.site_id = si.id(+) and s.edutype_id = e.id(+) ";
		studentSQL = "select id, name,edutype_id, grade_id, site_id, major_id, reg_no,"
				+ "grade_name, site_name, major_name, edutype_name from("
				+ studentSQL
				+ ") "
				+ Conditions.convertToCondition(searchList, null);

		String sql = "select user_id,"
				+ exfields
				+ "name,reg_no,grade_id,grade_name,"
				+ "major_id,major_name,site_id,site_name,edutype_id,edutype_name"
				+ " from (select b.id as user_id,"
				+ fields
				+ "b.name,b.reg_no,b.grade_id,b.grade_name,b.major_id,b.major_name,"
				+ "b.site_id,b.site_name,b.edutype_id,b.edutype_name "
				+ " from entity_recruit_userfee_level a, (" + studentSQL
				+ ") b where a.user_id(+)=b.id"
				+ Conditions.convertToCondition(null, orderList) + ")";
		EntityLog.setDebug(sql);
		return sql;
	}
	public List searchChargeLevels(Page page, List searchList, List orderList) {
		dbpool db = new dbpool();
		ArrayList list = new ArrayList();
		MyResultSet rs = null;
		String sql = getChargeLevelsSQL(searchList, orderList);
		OracleChargeLevel oracleChargeLevel = new OracleChargeLevel();
		List chargeTypeList = ChargeType.types();
		try {
			if (page != null) {
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				List subList = new ArrayList();

				subList.add(rs.getString("user_id"));
				subList.add(rs.getString("name"));
				subList.add(rs.getString("reg_no"));
				subList.add(rs.getString("edutype_name"));
				subList.add(rs.getString("grade_name"));
				subList.add(rs.getString("major_name"));
				subList.add(rs.getString("site_name"));
				for (int i = 0; i < chargeTypeList.size(); i++) {
					String type = (String) chargeTypeList.get(i);
					type = ((String) oracleChargeLevel.chargeTypeFieldValue
							.get(type));
					subList.add(rs.getString(type));

				}
				list.add(subList);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleChargeLevelList.searchChargeLevels error! "
					+ sql);
		} finally {
			db.close(rs);
			db = null;

		}
		return list;
	}

	public int searchChargeLevelsNum(List searchList) {
		dbpool db = new dbpool();
		return db.countselect(getChargeLevelsSQL(searchList, null));
	}
	
	public List searchRecruitChargeLevels(Page page, List searchList, List orderList) {
		dbpool db = new dbpool();
		ArrayList list = new ArrayList();
		MyResultSet rs = null;
		String sql = getRecruitChargeLevelsSQL(searchList, orderList);
		OracleChargeLevel oracleChargeLevel = new OracleChargeLevel();
		List chargeTypeList = ChargeType.types();
		try {
			if (page != null) {
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				List subList = new ArrayList();

				subList.add(rs.getString("user_id"));
				subList.add(rs.getString("name"));
				subList.add(rs.getString("reg_no"));
				subList.add(rs.getString("edutype_name"));
				subList.add(rs.getString("grade_name"));
				subList.add(rs.getString("major_name"));
				subList.add(rs.getString("site_name"));
				for (int i = 0; i < chargeTypeList.size(); i++) {
					String type = (String) chargeTypeList.get(i);
					type = ((String) oracleChargeLevel.chargeTypeFieldValue
							.get(type));
					subList.add(rs.getString(type));

				}
				list.add(subList);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleChargeLevelList.searchRecruitChargeLevels error! "
					+ sql);
		} finally {
			db.close(rs);
			db = null;

		}
		return list;
	}

	public int searchRecruitChargeLevelsNum(List searchList) {
		dbpool db = new dbpool();
		return db.countselect(getRecruitChargeLevelsSQL(searchList, null));
	}

}
