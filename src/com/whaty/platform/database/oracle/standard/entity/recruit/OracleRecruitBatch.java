package com.whaty.platform.database.oracle.standard.entity.recruit;

import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleGrade;
import com.whaty.platform.entity.recruit.RecruitBatch;
import com.whaty.platform.entity.recruit.TimeDef;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.string.WhatyStrManage;

public class OracleRecruitBatch extends RecruitBatch {
	/** Creates a new instance of OracleRecruitBatch */
	public OracleRecruitBatch() {
	}

	public OracleRecruitBatch(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		boolean active = false;
		String sql = "";
		sql = "select id,title,plan_startdate,plan_enddate,startdate,enddate,exam_startdate,exam_enddate,grade_id,"
				+ "note,creattime,active,jianzhang,REG_STARTDATE,REG_ENDDATE  from "
				+ "(select id,title,to_char(plan_startdate,'yyyy-mm-dd') as plan_startdate,"
				+ "to_char(plan_enddate,'yyyy-mm-dd') as plan_enddate,to_char(startdate,'yyyy-mm-dd') as startdate,"
				+ "to_char(enddate,'yyyy-mm-dd') as enddate,to_char(exam_startdate,'yyyy-mm-dd') as exam_startdate,"
				+ "to_char(exam_enddate,'yyyy-mm-dd') as exam_enddate,grade_id,note,jianzhang,"
				+ " to_char(creattime,'yyyy-mm-dd') as creattime,active ,REG_STARTDATE,REG_ENDDATE from recruit_batch_info where id = '"
				+ aid + "')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				WhatyStrManage strManage1 = new WhatyStrManage(rs
						.getString("jianzhang"));
				try {
					this.setSimpleNote(strManage1.htmlDecode());
				} catch (WhatyUtilException e) {
				}
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));

				this.setReg_startdate(rs.getString("REG_STARTDATE"));
				this.setReg_enddate(rs.getString("REG_ENDDATE"));
				
				TimeDef planDate = new TimeDef();
				planDate.setStartTime(rs.getString("plan_startdate"));
				planDate.setEndTime(rs.getString("plan_enddate"));
				this.setPlanTime(planDate);

				TimeDef signDate = new TimeDef();
				signDate.setStartTime(rs.getString("startdate"));
				signDate.setEndTime(rs.getString("enddate"));
				this.setSignTime(signDate);

				TimeDef examDate = new TimeDef();
				examDate.setStartTime(rs.getString("exam_startdate"));
				examDate.setEndTime(rs.getString("exam_enddate"));
				this.setExamTime(examDate);

				OracleGrade grade = new OracleGrade();
				grade.setId(rs.getString("grade_id"));
				WhatyStrManage strManage2 = new WhatyStrManage(rs
						.getString("note"));
				try {
					this.setNote(strManage2.htmlDecode());
				} catch (WhatyUtilException e) {
				}
				this.setCreatTime(rs.getString("creattime"));
				if (rs.getString("active").equals("1"))
					active = true;
				else
					active = false;
				this.setActive(active);
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleRecruitBatch(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int setActive() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_batch_info set active='1' where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitBatch.setActive() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int cancelActive() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_batch_info set active='0'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitBatch.cancelActive() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int add() {
		dbpool db = new dbpool();
		WhatyStrManage strManage1 = new WhatyStrManage(this.getNote());
		WhatyStrManage strManage2 = new WhatyStrManage(this.getSimpleNote());
		String active = "0";
		if (this.isActive())
			active = "1";
		String sql = "";
		sql = "insert into recruit_batch_info (id,title,plan_startdate,plan_enddate,startdate,enddate,"
				+ "exam_startdate, exam_enddate,note,creattime,active,jianzhang) values ('"
				+ this.getId()
				+ "','"
				+ this.getTitle()
				+ "',to_date('"
				+ this.getPlanTime().getStartTime()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getPlanTime().getEndTime()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getSignTime().getStartTime()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getSignTime().getEndTime()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getExamTime().getStartTime()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getExamTime().getEndTime()
				+ "','yyyy-mm-dd'),'"
				+ strManage1.htmlEncode()
				+ "',sysdate,'"
				+ active + "','" + strManage2.htmlEncode() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRecruitBatch.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		WhatyStrManage strManage1 = new WhatyStrManage(this.getNote());
		WhatyStrManage strManage2 = new WhatyStrManage(this.getSimpleNote());
		String active = "0";
		if (this.isActive())
			active = "1";
		String sql = "";
		sql = "update recruit_batch_info set title='" + this.getTitle()
				+ "',plan_startdate=to_date('"
				+ this.getPlanTime().getStartTime()
				+ "','yyyy-mm-dd'),plan_enddate=to_date('"
				+ this.getPlanTime().getEndTime()
				+ "','yyyy-mm-dd'),startdate=to_date('"
				+ this.getSignTime().getStartTime()
				+ "','yyyy-mm-dd'), enddate=to_date('"
				+ this.getSignTime().getEndTime()
				+ "','yyyy-mm-dd'),exam_startdate=to_date('"
				+ this.getExamTime().getStartTime()
				+ "','yyyy-mm-dd'),exam_enddate=to_date('"
				+ this.getExamTime().getEndTime() + "','yyyy-mm-dd'), note='"
				+ strManage1.htmlEncode() + "',active='" + active
				+ "',jianzhang='" + strManage2.htmlEncode() + "' where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitBatch.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int updateNoActive() {
		dbpool db = new dbpool();
		WhatyStrManage strManage1 = new WhatyStrManage(this.getNote());
		WhatyStrManage strManage2 = new WhatyStrManage(this.getSimpleNote());
		// String active = "0";
		// if (this.isActive())
		// active = "1";
		String sql = "";
		sql = "update recruit_batch_info set title='" + this.getTitle()
				+ "',plan_startdate=to_date('"
				+ this.getPlanTime().getStartTime()
				+ "','yyyy-mm-dd'),plan_enddate=to_date('"
				+ this.getPlanTime().getEndTime()
				+ "','yyyy-mm-dd'),startdate=to_date('"
				+ this.getSignTime().getStartTime()
				+ "','yyyy-mm-dd'), enddate=to_date('"
				+ this.getSignTime().getEndTime()
				+ "','yyyy-mm-dd'),exam_startdate=to_date('"
				+ this.getExamTime().getStartTime()
				+ "','yyyy-mm-dd'),exam_enddate=to_date('"
				+ this.getExamTime().getEndTime() + "','yyyy-mm-dd'), note='"
				+ strManage1.htmlEncode() + "',jianzhang='"
				+ strManage2.htmlEncode() + "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitBatch.updateNoActive() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		if (isHasPlans() > 0)
			throw new PlatformException("此招生批次含有招生计划");
		if (isHasStudents() > 0)
			throw new PlatformException("此招生批次含有学生");
		if (isHasTestBatchs() > 0)
			throw new PlatformException(
					"此招生批次含有考试批次");
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from recruit_batch_info where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRecruitBatch.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public void addPlans(List planList) throws PlatformException {
		// TODO 自动生成方法存根

	}

	public void confirmPlans(List planList) throws PlatformException {
		// TODO 自动生成方法存根

	}

	public void unConfirmPlans(List planList) throws PlatformException {
		// TODO 自动生成方法存根

	}

	public void deletePlans(List planList) throws PlatformException {
		// TODO 自动生成方法存根

	}

	public void addStudents(List studentList) throws PlatformException {
		// TODO 自动生成方法存根

	}

	public void deleteStudents(List studentList) throws PlatformException {
		// TODO 自动生成方法存根

	}

	public void addTestBatchs(List testBatchList) throws PlatformException {
		// TODO 自动生成方法存根

	}

	public void deleteTestBatchs(List testBatchList) throws PlatformException {
		// TODO 自动生成方法存根

	}

	public int isHasPlans() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select batch_id from recruit_plan_info where batch_id ='"
				+ this.getId() + "'";
		int i = db.countselect(sql);
		return i;
	}

	public int isHasStudents() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select batch_id from recruit_student_info where batch_id ='"
				+ this.getId() + "'";
		int i = db.countselect(sql);
		return i;
	}

	public int isHasTestBatchs() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select batch_id from recruit_test_batch where batch_id ='"
				+ this.getId() + "'";
		int i = db.countselect(sql);
		return i;
	}
    public int setRegDateMethod(){
    	dbpool db = new dbpool();
    	String sql = "";
    	sql = "update recruit_batch_info set reg_startdate=to_date('"
				+ this.getReg_startdate()
				+ "','yyyy-mm-dd'),reg_enddate=to_date('"
				+ this.getReg_enddate()
				+ "','yyyy-mm-dd') where id ='"
				+this.getId()+"'";
    	EntityLog.setDebug("OracleRecruitBatch@Method:setRegDateMethod()="+sql);
    	int i = db.executeUpdate(sql);
    	return  i;
    }
}
