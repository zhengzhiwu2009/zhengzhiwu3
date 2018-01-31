package com.whaty.platform.database.oracle.standard.entity.recruit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.recruit.RecruitEduInfo;
import com.whaty.platform.entity.recruit.RecruitTestStudent;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.util.Address;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleRecruitTestStudent extends RecruitTestStudent {
	/** Creates a new instance of OracleRecruitTestStudent */
	public OracleRecruitTestStudent() {
	}

	public OracleRecruitTestStudent(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		MyResultSet rss = null;
		String[] empty = new String[] {};
		String sql = "";
		sql = "select id,name,password,gender,folk,birthday,zzmm,edu,site_id,edutype_id,major_id,card_type,card_no,hometown,email,"
				+ "postalcode,address,mobilephone,phone,considertype,batch_id,status,testcard_id,append_score,score,score_1,score_2,score_3,score_4"
				+ " from (select id,name,password,gender,folk,to_char(birthday,'yyyy-mm-dd') as birthday,zzmm,edu,site_id,edutype_id,"
				+ "major_id,card_type,card_no,hometown,email,postalcode,address,mobilephone,phone,considertype,batch_id,status,testcard_id,append_score,score,score_1,score_2,score_3,score_4 from recruit_student_info) where id = '"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleRecruitStudent student = new OracleRecruitStudent();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setPassword(rs.getString("password"));
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
				student.setNormalInfo(normalInfo);
				RecruitEduInfo eduInfo = new RecruitEduInfo();
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setConsiderType(rs.getString("considertype"));
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("batch_id"));
				eduInfo.setBatch(batch);
				eduInfo.setStatus(rs.getString("status"));
				student.setEduInfo(eduInfo);
				this.setStudent(student);
				List searchProperties = new ArrayList();
				searchProperties.add(new SearchProperty("id", student.getId(),
						"="));
				OracleBasicRecruitList basicRecruitList = new OracleBasicRecruitList();
				List testDeskList = basicRecruitList.getTestDesks(null,
						searchProperties, null);
				this.setTestDeskList(testDeskList);
				this.setTestCardId(rs.getString("testcard_id"));
				this.setAppendScore(rs.getString("append_score"));
				this.setScore(rs.getString("score"));
				List scoreList = new ArrayList();
				scoreList.add(rs.getString("score_1"));
				scoreList.add(rs.getString("score_2"));
				scoreList.add(rs.getString("score_3"));
				scoreList.add(rs.getString("score_4"));
				this.setScoreList(scoreList);
				List searchProperties2 = new ArrayList();
				searchProperties2.add(new SearchProperty("id", student.getId(),
						"="));
				OracleBasicRecruitList recruitList = new OracleBasicRecruitList();
				List studentScoreList = recruitList.getStudentScores(null,
						searchProperties2, null);
				this.setStudentScoreList(studentScoreList);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleRecruitTestStudent(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int delete() throws PlatformException {
		return 0;
	}

	public int updateScore(String type) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		if (type.equals("single")) {
			sql = "update recruit_student_info set append_score='"
					+ this.getAppendScore() + "',score='" + this.getScore()
					+ "',score_1='" + (String) this.getScoreList().get(0)
					+ "',score_2='" + (String) this.getScoreList().get(1)
					+ "',score_3='" + (String) this.getScoreList().get(2)
					+ "',score_4='" + (String) this.getScoreList().get(3)
					+ "' where id='" + this.getStudent().getId() + "'";
			int count = db.executeUpdate(sql);
			UserUpdateLog
					.setDebug("OracleRecruitTestStudent.updateScore(String type) SQL="
							+ sql + " COUNT=" + count + " DATE=" + new Date());
			return count;
		} else {
			String year = type.substring(0, 4);
			String month = type.substring(5, 7);
			if (Integer.parseInt(month) <= 6) {
				year = Integer.toString(Integer.parseInt(year) - 1);
				type = year + "-12-31";
			} else {
				type = year + "-06-30";
			}
			sql = "update recruit_student_info set append_score='30',score='"
					+ (Float.parseFloat((String) this.getScoreList().get(3)) + 30)
					+ "',score_1='" + (String) this.getScoreList().get(0)
					+ "',score_2='" + (String) this.getScoreList().get(1)
					+ "',score_3='" + (String) this.getScoreList().get(2)
					+ "',score_4='" + (String) this.getScoreList().get(3)
					+ "' where testcard_id='" + this.getTestCardId()
					+ "' and add_months(birthday,300)<=to_date('" + type
					+ "','yyyy-mm-dd')";

			int count = db.executeUpdate(sql);
			UserUpdateLog
					.setDebug("OracleRecruitTestStudent.updateScore(String type) SQL="
							+ sql + " COUNT=" + count + " DATE=" + new Date());
			if (count < 1) {
				sql = "update recruit_student_info set append_score='0',score='"
						+ Float.parseFloat((String) this.getScoreList().get(3))
						+ "',score_1='"
						+ (String) this.getScoreList().get(0)
						+ "',score_2='"
						+ (String) this.getScoreList().get(1)
						+ "',score_3='"
						+ (String) this.getScoreList().get(2)
						+ "',score_4='"
						+ (String) this.getScoreList().get(3)
						+ "' where testcard_id='"
						+ this.getTestCardId()
						+ "' and add_months(birthday,300)>to_date('"
						+ type
						+ "','yyyy-mm-dd')";
				count = db.executeUpdate(sql);
				UserUpdateLog
						.setDebug("OracleRecruitTestStudent.updateScore(String type) SQL="
								+ sql
								+ " COUNT="
								+ count
								+ " DATE="
								+ new Date());
			}
			return count;
		}
	}

	public int update() throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getStudentCharacter(String id, String signDate)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		String year = signDate.substring(0, 4);
		String month = signDate.substring(5, 7);
		String signdate = "";
		if (Integer.parseInt(month) <= 6) {
			year = Integer.toString(Integer.parseInt(year) - 1);
			signdate = year + "-12-31";
		} else {
			signdate = year + "-06-30";
		}
		sql = " select id,birthday from recruit_student_info where id='" + id
				+ "' and  add_months(birthday,300)<=to_date('" + signdate
				+ "','yyyy-mm-dd')";
		// Log.setDebug(sql);
		return db.countselect(sql);
	}

}
