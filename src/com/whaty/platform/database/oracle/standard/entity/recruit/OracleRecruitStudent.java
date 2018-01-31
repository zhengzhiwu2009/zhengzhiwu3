package com.whaty.platform.database.oracle.standard.entity.recruit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.recruit.RecruitEduInfo;
import com.whaty.platform.entity.recruit.RecruitStudent;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.util.Address;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleRecruitStudent extends RecruitStudent {
	/** Creates a new instance of OracleRecruitStudent */
	public OracleRecruitStudent() {
	}

	public OracleRecruitStudent(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		// sql = "select
		// id,name,password,gender,folk,birthday,zzmm,edu,site_id,edutype_id,major_id,card_type,card_no,hometown,email,"
		// +
		// "postalcode,address,mobilephone,phone,considertype,batch_id,status,testcard_id,photo_link,premajor_status,reg_no,grade_id,study_no,school_name,school_code,graduate_year,graduate_cardno"
		// + " from (select
		// id,name,password,gender,folk,to_char(birthday,'yyyy-mm-dd') as
		// birthday,zzmm,edu,site_id,edutype_id,"
		// +
		// "major_id,card_type,card_no,hometown,email,postalcode,address,mobilephone,phone,considertype,batch_id,status,testcard_id,photo_link,premajor_status,reg_no,grade_id,study_no,school_name,school_code,graduate_year,graduate_cardno
		// from recruit_student_info) where id = '"
		// + aid + "'";
		sql = "select id,name,password,gender,folk,birthday,zzmm,edu,site_id,edutype_id,major_id,card_type,card_no,hometown,email,"
				+ "postalcode,address,mobilephone,phone,considertype,batch_id,status,testcard_id,photo_link,premajor_status,reg_no,grade_id,study_no,school_name,school_code,graduate_year,graduate_cardno,old_site,IDCARD_STATUS,GRADUATECARD_STATUS,IDCARD_LINK,GRADUATECARD_LINK,PHOTO_STATUS,study_status "
				+ " from (select id,name,password,gender,folk,to_char(birthday,'yyyy-mm-dd') as birthday,zzmm,edu,site_id,edutype_id,"
				+ "major_id,card_type,card_no,hometown,email,postalcode,address,mobilephone,phone,considertype,batch_id,status,testcard_id,photo_link,premajor_status,reg_no,grade_id,study_no,school_name,school_code,graduate_year,graduate_cardno,old_site,IDCARD_STATUS,GRADUATECARD_STATUS,IDCARD_LINK,GRADUATECARD_LINK,PHOTO_STATUS,study_status from recruit_student_info) where id = '"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setStudy_no(rs.getString("study_no"));
				this.setPassword(rs.getString("password"));
				this.setPremajor_status(rs.getString("premajor_status"));
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				normalInfo.setBirthday(rs.getString("birthday"));
				normalInfo.setCardType(rs.getString("card_type"));
				normalInfo.setIdcard(rs.getString("card_no"));
				normalInfo.setEdu(rs.getString("edu"));
				normalInfo.setHometown(rs.getString("hometown"));
				if (rs.getString("email") != null
						&& rs.getString("email").length() > 0)
					normalInfo.setEmail(rs.getString("email").split(","));
				else
					normalInfo.setEmail(null);
				Address homeaddress = new Address();
				homeaddress.setAddress(rs.getString("address"));
				homeaddress.setPostalcode(rs.getString("postalcode"));
				normalInfo.setHome_address(homeaddress);
				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone")
							.split(","));
				else
					normalInfo.setMobilePhone(null);
				if (rs.getString("phone") != null
						&& rs.getString("phone").length() > 0)
					normalInfo.setPhone(rs.getString("phone").split(","));
				else
					normalInfo.setPhone(null);
				normalInfo.setFolk(rs.getString("folk"));
				normalInfo.setGender(rs.getString("gender"));
				normalInfo.setZzmm(rs.getString("zzmm"));
				this.setNormalInfo(normalInfo);
				RecruitEduInfo eduInfo = new RecruitEduInfo();
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setOld_site_id(rs.getString("old_site"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setIdcard_status(rs.getString("IDCARD_STATUS"));
				eduInfo.setIdcard_link(rs.getString("IDCARD_LINK"));
				eduInfo.setGraduatecard_status(rs
						.getString("GRADUATECARD_STATUS"));
				eduInfo.setGraduatecard_link(rs.getString("GRADUATECARD_LINK"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setConsiderType(rs.getString("considertype"));
				eduInfo.setSchool_name(rs.getString("school_name"));
				eduInfo.setSchool_code(rs.getString("school_code"));
				eduInfo.setGraduate_year(rs.getString("graduate_year"));
				eduInfo.setGraduate_cardno(rs.getString("graduate_cardno"));
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("batch_id"));
				eduInfo.setBatch(batch);
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setPhoto_link(rs.getString("photo_link"));
				eduInfo.setPhoto_status(rs.getString("PHOTO_STATUS"));
				this.setEduInfo(eduInfo);
				this.setReg_no(rs.getString("reg_no"));
				this.setStudyStatus(rs.getString("study_status"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleRecruitStudent(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public OracleRecruitStudent(String cardNo, String args) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		// sql = "select
		// id,name,password,gender,folk,birthday,zzmm,edu,site_id,edutype_id,major_id,card_type,card_no,hometown,email,"
		// +
		// "postalcode,address,mobilephone,phone,considertype,batch_id,status,testcard_id,photo_link,premajor_status,reg_no,grade_id,study_no,school_name,school_code,graduate_year,graduate_cardno"
		// + " from (select
		// id,name,password,gender,folk,to_char(birthday,'yyyy-mm-dd') as
		// birthday,zzmm,edu,site_id,edutype_id,"
		// +
		// "major_id,card_type,card_no,hometown,email,postalcode,address,mobilephone,phone,considertype,batch_id,status,testcard_id,photo_link,premajor_status,reg_no,grade_id,study_no,school_name,school_code,graduate_year,graduate_cardno
		// from recruit_student_info) where id = '"
		// + aid + "'";
		sql = "select id,name,password,gender,folk,birthday,zzmm,edu,site_id,edutype_id,major_id,card_type,card_no,hometown,email,"
				+ "postalcode,address,mobilephone,phone,considertype,batch_id,status,testcard_id,photo_link,premajor_status,reg_no,grade_id,study_no,school_name,school_code,graduate_year,graduate_cardno,old_site,IDCARD_STATUS,GRADUATECARD_STATUS,IDCARD_LINK,GRADUATECARD_LINK,PHOTO_STATUS "
				+ " from (select id,name,password,gender,folk,to_char(birthday,'yyyy-mm-dd') as birthday,zzmm,edu,site_id,edutype_id,"
				+ "major_id,card_type,card_no,hometown,email,postalcode,address,mobilephone,phone,considertype,batch_id,status,testcard_id,photo_link,premajor_status,reg_no,grade_id,study_no,school_name,school_code,graduate_year,graduate_cardno,old_site,IDCARD_STATUS,GRADUATECARD_STATUS,IDCARD_LINK,GRADUATECARD_LINK,PHOTO_STATUS from recruit_student_info) where card_no = '"
				+ cardNo + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setStudy_no(rs.getString("study_no"));
				this.setPassword(rs.getString("password"));
				this.setPremajor_status(rs.getString("premajor_status"));
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				normalInfo.setBirthday(rs.getString("birthday"));
				normalInfo.setCardType(rs.getString("card_type"));
				normalInfo.setIdcard(rs.getString("card_no"));
				normalInfo.setEdu(rs.getString("edu"));
				normalInfo.setHometown(rs.getString("hometown"));
				if (rs.getString("email") != null
						&& rs.getString("email").length() > 0)
					normalInfo.setEmail(rs.getString("email").split(","));
				else
					normalInfo.setEmail(null);
				Address homeaddress = new Address();
				homeaddress.setAddress(rs.getString("address"));
				homeaddress.setPostalcode(rs.getString("postalcode"));
				normalInfo.setHome_address(homeaddress);
				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone")
							.split(","));
				else
					normalInfo.setMobilePhone(null);
				if (rs.getString("phone") != null
						&& rs.getString("phone").length() > 0)
					normalInfo.setPhone(rs.getString("phone").split(","));
				else
					normalInfo.setPhone(null);
				normalInfo.setFolk(rs.getString("folk"));
				normalInfo.setGender(rs.getString("gender"));
				normalInfo.setZzmm(rs.getString("zzmm"));
				this.setNormalInfo(normalInfo);
				RecruitEduInfo eduInfo = new RecruitEduInfo();
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setOld_site_id(rs.getString("old_site"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setIdcard_status(rs.getString("IDCARD_STATUS"));
				eduInfo.setIdcard_link(rs.getString("IDCARD_LINK"));
				eduInfo.setGraduatecard_status(rs
						.getString("GRADUATECARD_STATUS"));
				eduInfo.setGraduatecard_link(rs.getString("GRADUATECARD_LINK"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setConsiderType(rs.getString("considertype"));
				eduInfo.setSchool_name(rs.getString("school_name"));
				eduInfo.setSchool_code(rs.getString("school_code"));
				eduInfo.setGraduate_year(rs.getString("graduate_year"));
				eduInfo.setGraduate_cardno(rs.getString("graduate_cardno"));
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("batch_id"));
				eduInfo.setBatch(batch);
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setPhoto_link(rs.getString("photo_link"));
				eduInfo.setPhoto_status(rs.getString("PHOTO_STATUS"));
				this.setEduInfo(eduInfo);
				this.setReg_no(rs.getString("reg_no"));
			}
		} catch (java.sql.SQLException e) {
			Log
					.setError("OracleRecruitStudent(String cardNo,String args) error"
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		if (isIdCardNoExist() > 0) {
			throw new PlatformException("ǰ£֤"
					+ this.getNormalInfo().getIdcard() + "Ѿڣ");
		} else {
			String study_no = getStudyNO(this.getEduInfo().getSite_id(), this
					.getEduInfo().getBatch().getId());
			dbpool db = new dbpool();
			MyResultSet rs0 = null;
			String studentId = "";
			String sql0 = "select  s_recruit_student_info_id.nextval as studentId from DUAL";
			rs0 = db.executeQuery(sql0);
			try {
				if (rs0 != null && rs0.next()) {
					studentId = rs0.getString("studentId");
				}
			} catch (SQLException e1) {
			}

			String sql1 = "";
			sql1 = "insert into recruit_student_info (id,name,password,gender,folk,birthday,zzmm,edu,site_id,grade_id,edutype_id,"
					+ "major_id,card_type,card_no,hometown,email,postalcode,address,mobilephone,phone,considertype,"
					+ "batch_id,status,study_no,reg_no,school_name,school_code,graduate_year,graduate_cardno,old_site) values('"
					+ studentId
					+ "','"
					+ this.getName()
					+ "','"
					+ this.getPassword()
					+ "','"
					+ this.getNormalInfo().getGender()
					+ "','"
					+ this.getNormalInfo().getFolk()
					+ "',to_date('"
					+ this.getNormalInfo().getBirthday()
					+ "','yyyy-mm-dd'),'"
					+ this.getNormalInfo().getZzmm()
					+ "','"
					+ this.getNormalInfo().getEdu()
					+ "','"
					+ this.getEduInfo().getSite_id()
					+ "','"
					+ this.getEduInfo().getGrade_id()
					+ "','"
					+ this.getEduInfo().getEdutype_id()
					+ "','"
					+ this.getEduInfo().getMajor_id()
					+ "','"
					+ this.getNormalInfo().getCardType()
					+ "','"
					+ this.getNormalInfo().getIdcard()
					+ "','"
					+ this.getNormalInfo().getHometown()
					+ "','"
					+ this.getNormalInfo().getEmails()
					+ "','"
					+ this.getNormalInfo().getHome_address().getPostalcode()
					+ "','"
					+ this.getNormalInfo().getHome_address().getAddress()
					+ "','"
					+ this.getNormalInfo().getMobilePhones()
					+ "','"
					+ this.getNormalInfo().getPhones()
					+ "','"
					+ this.getEduInfo().getConsiderType()
					+ "','"
					+ this.getEduInfo().getBatch().getId()
					+ "','"
					+ this.getEduInfo().getStatus()
					+ "','"
					+ study_no
					+ "','"
					+ this.getEduInfo().getReg_no()
					+ "','"
					+ this.getEduInfo().getSchool_name()
					+ "','"
					+ this.getEduInfo().getSchool_code()
					+ "','"
					+ this.getEduInfo().getGraduate_year()
					+ "','"
					+ this.getEduInfo().getGraduate_cardno()
					+ "','"
					+ this.getEduInfo().getOld_site_id() + "')";
			int i = db.executeUpdate(sql1);
			UserAddLog.setDebug("OracleRecruitStudent.add() SQL=" + sql1
					+ " COUNT=" + i + " DATE=" + new Date());
			try {
				if (this.getEduInfo().getStatus().equals("1")) {
					this.setId(studentId);
					this.confirm();
				}
				if (this.getEduInfo().getStatus().equals("2")) {
					// this.updateRegNo2();
				}
			} catch (Exception e) {
				
			} finally {
				db.close(rs0);
				db = null;
			}
			return i;
		}
	}

	public int update() throws PlatformException {
		if (isEditIdCardNoExist() > 0) {
			throw new PlatformException("ǰ£֤"
					+ this.getNormalInfo().getIdcard() + "Ѿڣ");
		} else {
			dbpool db = new dbpool();
			String sql = "";
			if (this.getEduInfo().getConsiderType() != null
					&& this.getEduInfo().getConsiderType().equals("2"))
				sql = "update recruit_student_info set name='"
						+ this.getName()
						+ "',password='"
						+ this.getPassword()
						+ "',gender='"
						+ this.getNormalInfo().getGender()
						+ "',folk='"
						+ this.getNormalInfo().getFolk()
						+ "',birthday=to_date('"
						+ this.getNormalInfo().getBirthday()
						+ "','yyyy-mm-dd'),zzmm='"
						+ this.getNormalInfo().getZzmm()
						+ "',edu='"
						+ this.getNormalInfo().getEdu()
						+ "',site_id='"
						+ this.getEduInfo().getSite_id()
						+ "',edutype_id='"
						+ this.getEduInfo().getEdutype_id()
						+ "',major_id='"
						+ this.getEduInfo().getMajor_id()
						+ "',card_type='"
						+ this.getNormalInfo().getCardType()
						+ "',card_no='"
						+ this.getNormalInfo().getIdcard()
						+ "',hometown='"
						+ this.getNormalInfo().getHometown()
						+ "',email='"
						+ this.getNormalInfo().getEmails()
						+ "',postalcode='"
						+ this.getNormalInfo().getHome_address()
								.getPostalcode() + "',address='"
						+ this.getNormalInfo().getHome_address().getAddress()
						+ "',mobilephone='"
						+ this.getNormalInfo().getMobilePhones() + "',phone='"
						+ this.getNormalInfo().getPhones() + "',considertype='"
						+ this.getEduInfo().getConsiderType() + "',status='"
						+ this.getEduInfo().getStatus() + "',school_name='"
						+ this.getEduInfo().getSchool_name()
						+ "',school_code='"
						+ this.getEduInfo().getSchool_code()
						+ "',graduate_year='"
						+ this.getEduInfo().getGraduate_year()
						+ "',graduate_cardno='"
						+ this.getEduInfo().getGraduate_cardno()
						+ "' where id='" + this.getId() + "'";
			else
				sql = "update recruit_student_info set name='"
						+ this.getName()
						+ "',password='"
						+ this.getPassword()
						+ "',gender='"
						+ this.getNormalInfo().getGender()
						+ "',folk='"
						+ this.getNormalInfo().getFolk()
						+ "',birthday=to_date('"
						+ this.getNormalInfo().getBirthday()
						+ "','yyyy-mm-dd'),zzmm='"
						+ this.getNormalInfo().getZzmm()
						+ "',edu='"
						+ this.getNormalInfo().getEdu()
						+ "',site_id='"
						+ this.getEduInfo().getSite_id()
						+ "',edutype_id='"
						+ this.getEduInfo().getEdutype_id()
						+ "',major_id='"
						+ this.getEduInfo().getMajor_id()
						+ "',card_type='"
						+ this.getNormalInfo().getCardType()
						+ "',card_no='"
						+ this.getNormalInfo().getIdcard()
						+ "',hometown='"
						+ this.getNormalInfo().getHometown()
						+ "',email='"
						+ this.getNormalInfo().getEmails()
						+ "',postalcode='"
						+ this.getNormalInfo().getHome_address()
								.getPostalcode() + "',address='"
						+ this.getNormalInfo().getHome_address().getAddress()
						+ "',mobilephone='"
						+ this.getNormalInfo().getMobilePhones() + "',phone='"
						+ this.getNormalInfo().getPhones() + "',considertype='"
						+ this.getEduInfo().getConsiderType()
						+ "',considertype_status='0',status='"
						+ this.getEduInfo().getStatus() + "',school_name='"
						+ this.getEduInfo().getSchool_name()
						+ "',school_code='"
						+ this.getEduInfo().getSchool_code()
						+ "',graduate_year='"
						+ this.getEduInfo().getGraduate_year()
						+ "',graduate_cardno='"
						+ this.getEduInfo().getGraduate_cardno()
						+ "' where id='" + this.getId() + "'";
			int i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleRecruitStudent.update() SQL=" + sql
					+ " COUNT=" + i + " DATE=" + new Date());
			return i;
		}
	}

	public int delete() throws PlatformException {
		if (isHasTestCourses() > 0)
			throw new PlatformException(
					"The recruitStudent has recruitTestCourses");
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete recruit_student_info where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRecruitStudent.delete() SQL=" + sql
				+ " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int isHasTestCourses() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select recruitstudent_id from recruit_test_desk where recruitstudent_id ='"
				+ this.getId() + "'";
		int i = db.countselect(sql);
		return i;
	}

	/** ж֤Ƿڣ0򲻴ڣ0 */
	private int isIdCardNoExist() {
		dbpool db = new dbpool();
		String sql = "select id from recruit_student_info where (card_no='"
				+ this.getNormalInfo().getIdcard().toUpperCase() + "' or card_no ='"+ this.getNormalInfo().getIdcard().toLowerCase()+"') and batch_id='"
				+ this.getEduInfo().getBatch().getId() + "'";
		int i = db.countselect(sql);
		EntityLog.setDebug("isIdCardNoExist=" + sql);
		return i;

	}
	
	private int isEditIdCardNoExist() {
		dbpool db = new dbpool();
		 
		String sql = "select id from recruit_student_info where id !='"+this.getId()+"' and (card_no='"
			+ this.getNormalInfo().getIdcard().toUpperCase() + "' or card_no ='"+ this.getNormalInfo().getIdcard().toLowerCase()+"') and batch_id='"
			+ this.getEduInfo().getBatch().getId() + "'";
		
		int i = db.countselect(sql);
		return i;

	}

	public int confirm() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";

		MyResultSet rs = null;
		sql = "select batch_id||substr(trim(site_id),length(site_id)-1,2)||edutype_id||substr(trim(major_id),length(major_id)-1,2) as t_id from recruit_student_info where id='"
				+ this.getId() + "'";
		EntityLog.setDebug(sql);
		rs = db.executeQuery(sql);
		String t_id = "";
		try {
			if (rs != null && rs.next()) {
				t_id = rs.getString("t_id");
			}
		} catch (SQLException e1) {
		}
		db.close(rs);

		sql = "select nvl(max(to_number(testcard_id)),0) as testcard_id from recruit_student_info where testcard_id like '"
				+ t_id + "___'";
		EntityLog.setDebug(sql);
		rs = db.executeQuery(sql);
		String testcard_id = "001";
		try {
			if (rs != null && rs.next()) {
				testcard_id = rs.getString("testcard_id");
				if (testcard_id.length() >= 3)
					testcard_id = testcard_id
							.substring(testcard_id.length() - 3);
			}
		} catch (SQLException e1) {
		}
		db.close(rs);
		int card = Integer.parseInt(testcard_id);
		testcard_id = "000" + (++card);
		testcard_id = t_id + testcard_id.substring(testcard_id.length() - 3);
		sql = "update recruit_student_info set status='1',testcard_id='"
				+ testcard_id + "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitStudent.confirm() SQL=" + sql
				+ " COUNT=" + i + " DATE=" + new Date());
		// ѧԶѡ
		sql = "select id as testcourse_id from recruit_test_course where testsequence_id in(select id from recruit_batch_info where active='1') and course_id in (select distinct rsr.course_id from recruit_student_info rsi,recruit_majorsort_relation  rmr,recruit_sortcourse_relation rsr,recruit_sort_info a where rsi.major_id=rmr.major_id and rmr.sort_id=rsr.sort_id and rsi.edutype_id=a.edutype_id and a.id=rsr.sort_id and a.id=rmr.sort_id and rsi.id='"
				+ this.getId() + "')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				String testcourse_id = rs.getString("testcourse_id");
				String sql_temp = "insert into recruit_test_desk(id,recruitstudent_id,testcourse_id) values(to_char(s_recruit_test_desk_id.nextval),'"
						+ this.getId() + "','" + testcourse_id + "')";
				EntityLog
						.setDebug("OracleRecruitStudent@Method:confirm()/sql_temp="
								+ sql_temp);
				i = db.executeUpdate(sql_temp);

			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return i;
	} // ȷѧϢ

	public int confirmFree() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		if (this.getEduInfo().getConsidertype_note() == null)
			sql = "update recruit_student_info set considertype_status='"
					+ this.getEduInfo().getConsidertype_status()
					+ "'  where id ='" + this.getId() + "'";
		else
			sql = "update recruit_student_info set considertype_status='"
					+ this.getEduInfo().getConsidertype_status()
					+ "',considertype_note='"
					+ this.getEduInfo().getConsidertype_note()
					+ "'  where id ='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitStudent.confirmFree() SQL=" + sql
				+ " COUNT=" + i + " DATE=" + new Date());
		if (this.getEduInfo().getConsidertype_status().equals("2")) {
			sql = "select id as testcourse_id from recruit_test_course where "
					+ "testsequence_id in(select id from recruit_batch_info where"
					+ " active='1') and course_id in (select distinct rsr.course_id from"
					+ " recruit_student_info rsi,recruit_majorsort_relation"
					+ " rmr,recruit_sortcourse_relation rsr,recruit_sort_info a where"
					+ " rsi.major_id=rmr.major_id and rmr.sort_id=rsr.sort_id and"
					+ " rsi.edutype_id=a.edutype_id and a.id=rsr.sort_id and a.id=rmr.sort_id"
					+ " and rsi.id='" + this.getId() + "')";
			MyResultSet rs = null;
			try {
				rs = db.executeQuery(sql);
				while (rs != null && rs.next()) {
					String testcourse_id = rs.getString("testcourse_id");
					String sql_temp = "delete recruit_test_desk where recruitstudent_id='"
							+ this.getId()
							+ "' and testcourse_id='"
							+ testcourse_id + "'";
					db.executeUpdate(sql_temp);
				}
			} catch (Exception e) {
				
			} finally {
				db.close(rs);
				db = null;
			}
		}
		return i;
	}

	public int unConfirm() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_student_info set status='0',testcard_id='' where 1=(select count(*) from recruit_batch_info t where t.active = 1 and (t.startdate < sysdate and t.enddate + 1 > sysdate)) and id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		if(i>0) {
		UserUpdateLog.setDebug("OracleRecruitStudent.unConfirm() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		UserUpdateLog.setDebug("OracleRecruitStudent.unConfirm() SQL=" + sql
				+ " COUNT=" + i + " DATE=" + new Date());
		// Զɾѧѡ
		sql = "select id as testcourse_id from recruit_test_course where testsequence_id in(select id from recruit_batch_info where active='1') and course_id in (select distinct rsr.course_id from recruit_student_info rsi,recruit_majorsort_relation  rmr,recruit_sortcourse_relation rsr,recruit_sort_info a where rsi.major_id=rmr.major_id and rmr.sort_id=rsr.sort_id and rsi.edutype_id=a.edutype_id and a.id=rsr.sort_id and a.id=rmr.sort_id and rsi.id='"
				+ this.getId() + "')";
		MyResultSet rs = null;
			try {
				rs = db.executeQuery(sql);
				while (rs != null && rs.next()) {
					String testcourse_id = rs.getString("testcourse_id");
					String sql_temp = "delete  recruit_test_desk where recruitstudent_id='"
							+ this.getId()
							+ "' and testcourse_id='"
							+ testcourse_id + "'";
					UserDeleteLog.setDebug("OracleRecruitStudent.unConfirm() SQL=" + sql + " DATE=" + new Date());
					db.executeUpdate(sql_temp);
	
				}
			} catch (Exception e) {
				
			} finally {
				db.close(rs);
				db = null;
	
			}
		}
		return i;
	}

	public int unConfirmFree() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		if (this.getEduInfo().getConsidertype_note() == null)
			sql = "update recruit_student_info set considertype_status='"
					+ this.getEduInfo().getConsidertype_status()
					+ "'  where id ='" + this.getId() + "'";
		else
			sql = "update recruit_student_info set considertype_status='"
					+ this.getEduInfo().getConsidertype_status()
					+ "',considertype_note='"
					+ this.getEduInfo().getConsidertype_note()
					+ "'  where id ='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitStudent.unConfirmFree() SQL="
				+ sql + " COUNT=" + i + " DATE=" + new Date());
		if (this.getEduInfo().getConsidertype_status().equals("2")) {
			MyResultSet rs = null;
			sql = "select batch_id||substr(trim(site_id),length(site_id)-1,2)||edutype_id||substr(trim(major_id),length(major_id)-1,2) as t_id from recruit_student_info where id='"
					+ this.getId() + "'";
			rs = db.executeQuery(sql);
			String t_id = "";
			try {
				if (rs != null && rs.next()) {
					t_id = rs.getString("t_id");
				}
			} catch (SQLException e1) {
			}
			db.close(rs);

			sql = "select nvl(max(to_number(testcard_id)),0) as testcard_id from recruit_student_info where testcard_id like '"
					+ t_id + "___'";
			rs = db.executeQuery(sql);
			String testcard_id = "001";
			try {
				if (rs != null && rs.next()) {
					testcard_id = rs.getString("testcard_id");
					if (testcard_id.length() >= 3)
						testcard_id = testcard_id.substring(testcard_id
								.length() - 3);
				}
			} catch (SQLException e1) {
			}
			db.close(rs);
			int card = Integer.parseInt(testcard_id);
			testcard_id = "000" + (++card);
			testcard_id = t_id
					+ testcard_id.substring(testcard_id.length() - 3);
			sql = "update recruit_student_info set testcard_id='" + testcard_id
					+ "' where id='" + this.getId() + "'";
			UserUpdateLog.setDebug("OracleRecruitStudent.unConfirmFree() SQL="
					+ sql + " DATE=" + new Date());
			db.executeUpdate(sql);

			// ѧԶѡ
			sql = "select id as testcourse_id from recruit_test_course where testsequence_id in(select id from recruit_batch_info where active='1') and course_id in (select distinct rsr.course_id from recruit_student_info rsi,recruit_majorsort_relation  rmr,recruit_sortcourse_relation rsr,recruit_sort_info a where rsi.major_id=rmr.major_id and rmr.sort_id=rsr.sort_id and rsi.edutype_id=a.edutype_id and a.id=rsr.sort_id and a.id=rmr.sort_id and rsi.id='"
					+ this.getId() + "')";
			try {
				rs = db.executeQuery(sql);
				while (rs != null && rs.next()) {
					String testcourse_id = rs.getString("testcourse_id");
					String sql_temp = "insert into recruit_test_desk(id,recruitstudent_id,testcourse_id) values(to_char(s_recruit_test_desk_id.nextval),'"
							+ this.getId() + "','" + testcourse_id + "')";
					i = db.executeUpdate(sql_temp);
					UserAddLog
							.setDebug("OracleRecruitStudent.unConfirmFree() SQL="
									+ sql
									+ " COUNT="
									+ i
									+ " DATE="
									+ new Date());
				}
			} catch (Exception e) {
				
			} finally {
				db.close(rs);
				db = null;

			}
		}

		return i;
	}

	public int rejectFree() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_student_info set considertype='0',considertype_status='0'  where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitStudent.rejectFree() SQL=" + sql
				+ " COUNT=" + i + " DATE=" + new Date());

		/*
		 * sql = "select id as testcourse_id from recruit_test_course where
		 * testsequence_id in(select id from recruit_batch_info where
		 * active='1') and course_id in (select distinct rsr.course_id from
		 * recruit_student_info rsi,recruit_majorsort_relation
		 * rmr,recruit_sortcourse_relation rsr,recruit_sort_info a where
		 * rsi.major_id=rmr.major_id and rmr.sort_id=rsr.sort_id and
		 * rsi.edutype_id=a.edutype_id and a.id=rsr.sort_id and a.id=rmr.sort_id
		 * and rsi.id='" + this.getId() + "')"; MyResultSet rs = null; try { rs =
		 * db.executeQuery(sql); while (rs != null && rs.next()) { String
		 * testcourse_id = rs.getString("testcourse_id"); String sql_temp =
		 * "delete recruit_test_desk where recruitstudent_id='" + this.getId() + "'
		 * and testcourse_id='" + testcourse_id + "'";
		 * db.executeUpdate(sql_temp); } } catch (Exception e) {
		 *  } finally { db.close(rs); db = null; }
		 */

		return i;
	}

	public RecruitStudent getRecruitInfo(String card_no, String password,
			String batchId) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";

		sql = "select id,name,password,gender,folk,birthday,zzmm,edu,site_id,edutype_id,major_id,card_type,card_no,hometown,email,pass_status,"
				+ "postalcode,address,mobilephone,phone,considertype,batch_id,status,testcard_id,study_no,edutype_name,major_name,school_name,school_code,GRADUATE_YEAR,GRADUATE_CARDNO "
				+ " from (select stu.id as id,stu.name as name,stu.password as password,stu.gender as gender,stu.folk as folk ,to_char(stu.birthday,'yyyy-mm-dd') as birthday,stu.pass_status,stu.zzmm as zzmm,stu.edu as edu,stu.site_id as site_id,stu.edutype_id as edutype_id,"
				+ "stu.major_id as major_id,stu.card_type as card_type,stu.card_no as card_no,stu.hometown as hometown,stu.email as email,stu.postalcode as postalcode ,stu.address as address,stu.mobilephone as mobilephone,stu.phone as phone,stu.considertype as considertype,stu.batch_id as batch_id,stu.status as status,stu.testcard_id as testcard_id ,stu.study_no as study_no,edutype.name as edutype_name,major.name as major_name"
				+ ",stu.school_name,stu.school_code,stu.GRADUATE_YEAR,stu.GRADUATE_CARDNO"
				+ " from recruit_student_info stu,entity_edu_type edutype,entity_major_info major  where stu.edutype_id=edutype.id and stu.major_id=major.id) where card_no = '"
				+ card_no
				+ "' and password='"
				+ password
				+ "' and batch_id='"
				+ batchId + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setPassword(rs.getString("password"));
				this.setStudy_no(rs.getString("study_no"));
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				normalInfo.setBirthday(rs.getString("birthday"));
				normalInfo.setCardType(rs.getString("card_type"));
				normalInfo.setIdcard(rs.getString("card_no"));
				normalInfo.setEdu(rs.getString("edu"));
				normalInfo.setHometown(rs.getString("hometown"));
				if (rs.getString("email") != null
						&& rs.getString("email").length() > 0)
					normalInfo.setEmail(rs.getString("email").split(","));
				else
					normalInfo.setEmail(null);
				Address homeaddress = new Address();
				homeaddress.setAddress(rs.getString("address"));
				homeaddress.setPostalcode(rs.getString("postalcode"));
				normalInfo.setHome_address(homeaddress);
				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone")
							.split(","));
				else
					normalInfo.setMobilePhone(null);
				if (rs.getString("phone") != null
						&& rs.getString("phone").length() > 0)
					normalInfo.setPhone(rs.getString("phone").split(","));
				else
					normalInfo.setPhone(null);
				normalInfo.setFolk(rs.getString("folk"));
				normalInfo.setGender(rs.getString("gender"));
				normalInfo.setZzmm(rs.getString("zzmm"));
				this.setNormalInfo(normalInfo);
				RecruitEduInfo eduInfo = new RecruitEduInfo();
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));
				eduInfo.setMajor_name(rs.getString("major_name"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setConsiderType(rs.getString("considertype"));
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("batch_id"));
				eduInfo.setBatch(batch);
				eduInfo.setPassStatus(rs.getString("pass_status")) ;
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setSchool_code(rs.getString("school_code"));
				eduInfo.setSchool_name(rs.getString("school_name"));
				eduInfo.setGraduate_cardno(rs.getString("graduate_cardno"));
				eduInfo.setGraduate_year(rs.getString("graduate_year"));
				this.setEduInfo(eduInfo);
			}
		} catch (java.sql.SQLException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return this;
	}

	public RecruitStudent getRecruitInfo(String cardNo)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,name,password,gender,folk,birthday,zzmm,edu,site_id,edutype_id,major_id,card_type,card_no,hometown,email,"
				+ "postalcode,address,mobilephone,phone,considertype,batch_id,status,testcard_id,pass_status"
				+ " from (select id,name,password,gender,folk,to_char(birthday,'yyyy-mm-dd') as birthday,zzmm,edu,site_id,edutype_id,pass_status,"
				+ "major_id,card_type,card_no,hometown,email,postalcode,address,mobilephone,phone,considertype,batch_id,status,testcard_id from recruit_student_info where card_no = '"
				+ cardNo + "')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setPassword(rs.getString("password"));
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				normalInfo.setBirthday(rs.getString("birthday"));
				normalInfo.setCardType(rs.getString("card_type"));
				normalInfo.setIdcard(rs.getString("card_no"));
				normalInfo.setEdu(rs.getString("edu"));
				normalInfo.setHometown(rs.getString("hometown"));
				if (rs.getString("email") != null
						&& rs.getString("email").length() > 0)
					normalInfo.setEmail(rs.getString("email").split(","));
				else
					normalInfo.setEmail(null);
				Address homeaddress = new Address();
				homeaddress.setAddress(rs.getString("address"));
				homeaddress.setPostalcode(rs.getString("postalcode"));
				normalInfo.setHome_address(homeaddress);
				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone")
							.split(","));
				else
					normalInfo.setMobilePhone(null);
				if (rs.getString("phone") != null
						&& rs.getString("phone").length() > 0)
					normalInfo.setPhone(rs.getString("phone").split(","));
				else
					normalInfo.setPhone(null);
				normalInfo.setFolk(rs.getString("folk"));
				normalInfo.setGender(rs.getString("gender"));
				normalInfo.setZzmm(rs.getString("zzmm"));
				this.setNormalInfo(normalInfo);
				RecruitEduInfo eduInfo = new RecruitEduInfo();
				eduInfo.setPassStatus(rs.getString("pass_status"));
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setConsiderType(rs.getString("considertype"));
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("batch_id"));
				eduInfo.setBatch(batch);
				eduInfo.setStatus(rs.getString("status"));
				this.setEduInfo(eduInfo);
			}
		} catch (java.sql.SQLException e) {
			Log
					.setError("OracleRecruitStudent(String card_no,String password) error"
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return this;
	}

	public int checkRecruitExist(String cardNo) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id,name,password,gender,folk,birthday,zzmm,edu,site_id,edutype_id,major_id,card_type,card_no,hometown,email,"
				+ "postalcode,address,mobilephone,phone,considertype,batch_id,status,testcard_id"
				+ " from (select id,name,password,gender,folk,to_char(birthday,'yyyy-mm-dd') as birthday,zzmm,edu,site_id,edutype_id,"
				+ "major_id,card_type,card_no,hometown,email,postalcode,address,mobilephone,phone,considertype,batch_id,status,testcard_id from recruit_student_info where card_no = '"
				+ cardNo + "')";

		return db.countselect(sql);
	}

	public RecruitStudent getRecruitInfo1(String regNo)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,name,password,gender,folk,birthday,zzmm,edu,site_id,edutype_id,major_id,grade_id,grade_name,card_type,card_no,hometown,email,"
				+ "postalcode,address,mobilephone,phone,considertype,batch_id,status,testcard_id,site_name,edutype_name,major_name,reg_no,register_status"
				+ " from (select a.id,a.name,password,gender,folk,to_char(birthday,'yyyy-mm-dd') as birthday,zzmm,edu,site_id,edutype_id,"
				+ "major_id,grade_id,card_type,card_no,hometown,a.email,postalcode,a.address,mobilephone,a.phone,considertype,batch_id,a.status,"
				+ "testcard_id,b.name as site_name,c.name as edutype_name,d.name as major_name,reg_no,g.name as grade_name,a.register_status from recruit_student_info a,"
				+ "entity_site_info b,entity_edu_type c,entity_major_info d,entity_grade_info g where reg_no = '"
				+ regNo
				+ "' and a.site_id=b.id and a.edutype_id=c.id and a.major_id=d.id and a.grade_id = g.id)";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setPassword(rs.getString("password"));
				this.setReg_no(rs.getString("reg_no"));
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				normalInfo.setBirthday(rs.getString("birthday"));
				normalInfo.setCardType(rs.getString("card_type"));
				normalInfo.setIdcard(rs.getString("card_no"));
				normalInfo.setEdu(rs.getString("edu"));
				normalInfo.setHometown(rs.getString("hometown"));
				if (rs.getString("email") != null
						&& rs.getString("email").length() > 0)
					normalInfo.setEmail(rs.getString("email").split(","));
				else
					normalInfo.setEmail(null);
				Address homeaddress = new Address();
				homeaddress.setAddress(rs.getString("address"));
				homeaddress.setPostalcode(rs.getString("postalcode"));
				normalInfo.setHome_address(homeaddress);
				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone")
							.split(","));
				else
					normalInfo.setMobilePhone(null);
				if (rs.getString("phone") != null
						&& rs.getString("phone").length() > 0)
					normalInfo.setPhone(rs.getString("phone").split(","));
				else
					normalInfo.setPhone(null);
				normalInfo.setFolk(rs.getString("folk"));
				normalInfo.setGender(rs.getString("gender"));
				normalInfo.setZzmm(rs.getString("zzmm"));
				this.setNormalInfo(normalInfo);
				RecruitEduInfo eduInfo = new RecruitEduInfo();
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("site_name"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));
				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setGrade_name(rs.getString("grade_name"));
				eduInfo.setConsiderType(rs.getString("considertype"));
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("batch_id"));
				eduInfo.setBatch(batch);
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setRegister_status(rs.getString("register_status"));
				this.setEduInfo(eduInfo);
			}
		} catch (java.sql.SQLException e) {
			Log
					.setError("OracleRecruitStudent(String card_no,String password) error"
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return this;
	}

	public RecruitStudent getRecruitInfo2(String name, String cardNo,
			String batchId) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,name,password,gender,folk,birthday,zzmm,edu,site_id,edutype_id,major_id,card_type,card_no,hometown,email,"
				+ "postalcode,address,mobilephone,phone,considertype,batch_id,status,testcard_id"
				+ " from (select id,name,password,gender,folk,to_char(birthday,'yyyy-mm-dd') as birthday,zzmm,edu,site_id,edutype_id,"
				+ "major_id,card_type,card_no,hometown,email,postalcode,address,mobilephone,phone,considertype,batch_id,status,testcard_id from recruit_student_info) where name='"
				+ name
				+ "' and  card_no='"
				+ cardNo
				+ "' and batch_id='"
				+ batchId + "'";
		EntityLog.setDebug(sql);
		/*
		 * if (name == null && cardNo == null) return null; if (cardNo != null)
		 * sql += "card_no = '" + cardNo + "'"; if (name != null && cardNo !=
		 * null) sql += " and name = '" + name + "'";
		 */

		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setPassword(rs.getString("password"));
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				normalInfo.setBirthday(rs.getString("birthday"));
				normalInfo.setCardType(rs.getString("card_type"));
				normalInfo.setIdcard(rs.getString("card_no"));
				normalInfo.setEdu(rs.getString("edu"));
				normalInfo.setHometown(rs.getString("hometown"));
				if (rs.getString("email") != null
						&& rs.getString("email").length() > 0)
					normalInfo.setEmail(rs.getString("email").split(","));
				else
					normalInfo.setEmail(null);
				Address homeaddress = new Address();
				homeaddress.setAddress(rs.getString("address"));
				homeaddress.setPostalcode(rs.getString("postalcode"));
				normalInfo.setHome_address(homeaddress);
				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone")
							.split(","));
				else
					normalInfo.setMobilePhone(null);
				if (rs.getString("phone") != null
						&& rs.getString("phone").length() > 0)
					normalInfo.setPhone(rs.getString("phone").split(","));
				else
					normalInfo.setPhone(null);
				normalInfo.setFolk(rs.getString("folk"));
				normalInfo.setGender(rs.getString("gender"));
				normalInfo.setZzmm(rs.getString("zzmm"));
				this.setNormalInfo(normalInfo);
				RecruitEduInfo eduInfo = new RecruitEduInfo();
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setConsiderType(rs.getString("considertype"));
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("batch_id"));
				eduInfo.setBatch(batch);
				eduInfo.setStatus(rs.getString("status"));
				this.setEduInfo(eduInfo);
			}
		} catch (java.sql.SQLException e) {
			Log
					.setError("OracleRecruitStudent(String card_no,String password) error"
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return this;
	}

	public String getStudyNO(String site_id, String batch_id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		String study_no = "";
		String no = "";
		sql = "select nvl(max(study_no),0) as maxStudyNo from recruit_student_info where site_id='"
				+ site_id
				+ "' and batch_id='"
				+ batch_id
				+ "' and study_no like '" + site_id + "_____'";
		EntityLog.setDebug(sql);
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				no = rs.getString("maxStudyNo");
				if (!no.equals("0")) {
					no = no.substring(no.length() - 5);
				} else {

					no = "00000";
				}

			}

		} catch (Exception e) {

			Log.setError("maxStudyNO(String site_id)" + sql);
		} finally {
			db.close(rs);
			db = null;
		}

		int Intno = Integer.parseInt(no);
		Intno++;
		String strIntno = Integer.toString(Intno);
		for (int i = strIntno.length(); i < 5; i++) {
			strIntno = "0" + strIntno;

		}
		study_no = site_id + strIntno;
		return study_no;

	}

	public int updateRegNo() {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		String regNo = "";
		String no = "001";
		int nos = 0;
		sql = "";
		Calendar c = Calendar.getInstance();
		String date = Integer.toString(c.get(Calendar.YEAR)).substring(2, 4);
		regNo = date
				+ this.getEduInfo().getEdutype_id()
				+ this.getEduInfo().getMajor_id()
				+ this.getEduInfo().getSite_id().substring(
						this.getEduInfo().getSite_id().length() - 2);

		sql = "select nvl(max(reg_no),0) as reg_no from entity_student_info where reg_no like '"
				+ regNo + "___'";
		EntityLog.setDebug(sql);
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				no = rs.getString("reg_no");
				no = no.substring(no.length() - 3);
			}
		} catch (Exception e) {
		}
		db.close(rs);
		sql = "select nvl(max(reg_no),0) as reg_no from recruit_student_info where reg_no like '"
				+ regNo + "___'";
		EntityLog.setDebug(sql);
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				String regArgs = rs.getString("reg_no");
				if (Integer.parseInt(regArgs.substring(regArgs.length() - 3))  > Integer
						.parseInt(no)) {
					no = rs.getString("reg_no");
					no = no.substring(no.length() - 3);
				}
			}
		} catch (Exception e) {
		}
		db.close(rs);
		nos = Integer.parseInt(no);
		no = "000" + (++nos);
		no = no.substring(no.length() - 3);
		regNo += no;
		this.setReg_no(regNo);
		sql = "update recruit_student_info set reg_no='" + regNo
				+ "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitStudent.updateRegNo() SQL=" + sql
				+ " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int updateRegNoAndPwd() {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		String regNo = "";
		String no = "001";
		int nos = 0;
		sql = "";
		String card_no = "";

		Calendar c = Calendar.getInstance();
		// String date = Integer.toString(c.get(Calendar.YEAR)).substring(2, 4);
		String date = "";
		sql = "select batch_id,card_no from recruit_student_info where id='"
				+ this.getId() + "'";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				String id = rs.getString("batch_id");
				card_no = rs.getString("card_no");
				date = id.substring(0, 2);
			}
		} catch (Exception e) {
		}
		db.close(rs);
		regNo = date
				+ this.getEduInfo().getEdutype_id()
				+ this.getEduInfo().getMajor_id()
				+ this.getEduInfo().getSite_id().substring(
						this.getEduInfo().getSite_id().length() - 2);

		if (this.getStudyStatus().equals("")
				|| this.getStudyStatus().equals("0"))
			sql = "select nvl(max(reg_no),0) as reg_no from entity_student_info where reg_no like '"
					+ regNo
					+ "___' and to_number(reg_no)<to_number('"
					+ regNo
					+ "800')";
		else
			sql = "select nvl(max(reg_no),0) as reg_no from entity_student_info where reg_no like '"
					+ regNo
					+ "___' and to_number(reg_no)>to_number('"
					+ regNo
					+ "799')";
		EntityLog.setDebug(sql);
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				no = rs.getString("reg_no");
				no = no.substring(no.length() - 3);
			}
		} catch (Exception e) {
		}
		db.close(rs);
		if (this.getStudyStatus().equals("")
				|| this.getStudyStatus().equals("0"))
			sql = "select nvl(max(reg_no),0) as reg_no from recruit_student_info where reg_no like '"
					+ regNo
					+ "___' and to_number(reg_no)<to_number('"
					+ regNo
					+ "800')";
		else
			sql = "select nvl(max(reg_no),0) as reg_no from recruit_student_info where reg_no like '"
					+ regNo
					+ "___' and to_number(reg_no)>to_number('"
					+ regNo
					+ "799')";
		EntityLog.setDebug(sql);
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				String regArgs = rs.getString("reg_no");
				if (Integer.parseInt(regArgs.substring(regArgs.length() - 3)) > Integer
						.parseInt(no)) {
					no = rs.getString("reg_no");
					no = no.substring(no.length() - 3);
				}
			}
		} catch (Exception e) {
		}
		db.close(rs);
		nos = Integer.parseInt(no);
		if (this.getStudyStatus().equals("")
				|| this.getStudyStatus().equals("0")) {
			no = "000" + (++nos);
			no = no.substring(no.length() - 3);
		} else {
			if (nos == 0)
				no = "800";
			else
				no = ++nos + "";
		}
		regNo += no;
		this.setReg_no(regNo);
		sql = "update recruit_student_info set reg_no='" + regNo
				+ "' where id='" + this.getId() + "'";
		String pwd = "1111";
		if (card_no != null && card_no.length() == 15) {
			pwd = card_no.substring(6, 12);
		} else if (card_no != null && card_no.length() == 18) {
			pwd = card_no.substring(8, 14);
		}
		String pwdSql = "update recruit_student_info set password = '" + pwd
				+ "' where id='" + this.getId() + "'";
		ArrayList sqlList = new ArrayList();
		EntityLog.setDebug("OracleRecruitStudent@Method:updateRegNoAndPwd()="
				+ sql);
		EntityLog.setDebug("OracleRecruitStudent@Method:updateRegNoAndPwd()="
				+ pwdSql);
		sqlList.add(sql);
		sqlList.add(pwdSql);
		int i = db.executeUpdateBatch(sqlList);
		return i;
	}

	public int updateRegNo2() {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		String regNo = "";
		String no = "001";
		int nos = 0;
		sql = "";
		Calendar c = Calendar.getInstance();
		String date = Integer.toString(c.get(Calendar.YEAR)).substring(2, 4);
		regNo = this.getEduInfo().getBatch().getId().substring(0, 2)
				+ this.getEduInfo().getEdutype_id()
				+ this.getEduInfo().getMajor_id()
				+ this.getEduInfo().getSite_id().substring(
						this.getEduInfo().getSite_id().length() - 2);

		sql = "select nvl(max(reg_no),0) as reg_no from entity_student_info where reg_no like '"
				+ regNo + "___'";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				no = rs.getString("reg_no");
				no = no.substring(no.length() - 3);
			}
		} catch (Exception e) {
		}
		db.close(rs);
		sql = "select nvl(max(reg_no),0) as reg_no from recruit_student_info where reg_no like '"
				+ regNo + "___'";
		EntityLog.setDebug(sql);
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				String regArgs = rs.getString("reg_no");
				if (Integer.parseInt(regArgs.substring(regArgs.length() - 3))  > Integer
						.parseInt(no)) {
					no = rs.getString("reg_no");
					no = no.substring(no.length() - 3);
				}
			}
		} catch (Exception e) {
		}
		db.close(rs);
		nos = Integer.parseInt(no);
		no = "000" + (++nos);
		no = no.substring(no.length() - 3);
		regNo += no;
		this.setReg_no(regNo);
		sql = "update recruit_student_info set reg_no='" + regNo
				+ "' where card_no='" + this.getNormalInfo().getIdcard()
				+ "' and batch_id = '" + this.getEduInfo().getBatch().getId()
				+ "'";
		EntityLog.setDebug("OracleRecruitStudent@Method:updateRegNo2()=" + sql);
		int i = db.executeUpdate(sql);
		return i;
	}

	public String updateSiteMajor() {
		dbpool db = new dbpool();
		String sql = "update recruit_student_info set site_id='"
				+ this.getEduInfo().getSite_id() + "',major_id='"
				+ this.getEduInfo().getMajor_id() + "' where id='"
				+ this.getId() + "'";
		EntityLog.setDebug("OracleRecruitStudent@Method:updateSiteMajor()="
				+ sql);
		int i = db.executeUpdate(sql);
		this.updateRegNo();
		if (i > 0)
			return this.getReg_no();
		else
			return "";
	}

	public int updateLink() {
		dbpool db = new dbpool();
		String sql = "update recruit_student_info set photo_link='"
				+ this.getEduInfo().getPhoto_link() + "',photo_status='"
				+ this.getEduInfo().getPhoto_status() + "',idcard_link='"
				+ this.getEduInfo().getIdcard_link() + "',idcard_status='"
				+ this.getEduInfo().getIdcard_status()
				+ "',graduatecard_link='"
				+ this.getEduInfo().getGraduatecard_link()
				+ "',graduatecard_status='"
				+ this.getEduInfo().getGraduatecard_status() + "' where id='"
				+ this.getId() + "'";
		EntityLog.setDebug("OracleRecruitStudent@Method:updateLink()=" + sql);
		int i = db.executeUpdate(sql);
		return i;
	}

	public int updateScore(HashMap scores) {
		dbpool db = new dbpool();
		String sql = "update recruit_student_info set ";
		Iterator it = scores.keySet().iterator();
		String con = "";
		int i = 0;
		while (it.hasNext()) {
			String key = (String) it.next();
			String value = (String) scores.get(key);
			con += "," + key + "='" + value + "'";
		}
		if (con.length() > 0) {
			sql += con.substring(1) + " where id='" + this.getId() + "'";
			EntityLog.setDebug("OracleRecruitStudent@Method:updateScore()="
					+ sql);
			i = db.executeUpdate(sql);
		}
		return i;
	}

	public int updateStudentSite() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_student_info set old_site = site_id ,site_id ='"
				+ this.getEduInfo().getSite_id() + "' where id ='"
				+ this.getId() + "'";
		EntityLog.setDebug("OracleRecruitStudent@Method:updateStudentSite()="
				+ sql);
		int i = db.executeUpdate(sql);
		return i;
	}

	public int updatePubRegNoStatus() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_student_info set REGNO_STATUS = '"
				+ this.getPubRegNo_status() + "' where id ='" + this.getId()
				+ "'";
		EntityLog.setDebug("OracleRecruitStudent@Method:updatePubRegNoStatus="
				+ sql);
		int i = db.executeUpdate(sql);
		return i;
	}

	public int updateTestCardId() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_student_info set testcard_id = '"
				+ this.getEduInfo().getTestCardId() + "' where card_no='"
				+ this.getNormalInfo().getIdcard() + "' and batch_id='"
				+ this.getEduInfo().getBatch().getId() + "'";
		EntityLog.setDebug("OracleRecruitStudent@Method:updateTestCardId="
				+ sql);
		int i = db.executeUpdate(sql);
		return i;
	}
}
