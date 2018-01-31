package com.whaty.platform.database.oracle.standard.entity.recruit;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleEduType;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleMajor;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleSite;
import com.whaty.platform.entity.recruit.RecruitLimit;
import com.whaty.platform.entity.recruit.RecruitPlan;
import com.whaty.platform.entity.recruit.TimeDef;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleRecruitPlan extends RecruitPlan {
	/** Creates a new instance of OracleRecruitPlan */
	public OracleRecruitPlan() {
	}
	
	public OracleRecruitPlan(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		boolean recruitNumValid = false;
		boolean signNumValid = false;
		boolean signTimeValid = false;
		String sql = "";
		sql = "select id,title,batch_id,edutype_id,major_id,site_id,recruitnum,recruitnumvalid,signnum,signnumvalid,startdate,enddate,signtimevalid,status,reject_note from (select id,title,batch_id,edutype_id,major_id,site_id,recruitnum,recruitnumvalid,signnum,signnumvalid,to_char(startdate,'yyyy-mm-dd') as startdate,to_char(enddate,'yyyy-mm-dd') as enddate,signtimevalid,status,reject_note from recruit_plan_info where id = '"
				+ aid + "')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				OracleRecruitBatch recruitBatch = new OracleRecruitBatch();
				recruitBatch.setId(rs.getString("batch_id"));
				OracleEduType edutype = new OracleEduType();
				edutype.setId(rs.getString("edutype_id"));
				this.setEdutype(edutype);
				OracleMajor major = new OracleMajor();
				major.setId(rs.getString("major_id"));
				this.setMajor(major);
				OracleSite site = new OracleSite();
				site.setId(rs.getString("site_id"));
				this.setSite(site);
				RecruitLimit limit = new RecruitLimit();
				limit.setRecruitNum(rs.getInt("recruitnum"));
				if (rs.getString("recruitnumvalid").equals("1"))
					recruitNumValid = true;
				limit.setRecruitNumValid(recruitNumValid);
				limit.setSignNum(rs.getInt("signnum"));
				if (rs.getString("signnumvalid").equals("1"))
					signNumValid = true;
				limit.setSignNumValid(signNumValid);
				TimeDef time = new TimeDef();
				time.setStartTime(rs.getString("startdate"));
				time.setEndTime(rs.getString("enddate"));
				limit.setSignTime(time);
				if (rs.getString("signtimevalid").equals("1"))
					signTimeValid = true;
				limit.setSignTimeValid(signTimeValid);
				this.setLimit(limit);
				this.setStatus(rs.getString("status"));
				this.setReject_note(rs.getString("reject_note"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleRecruitPlan(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int confirm() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_plan_info set status='1',recruitnum='"
				+ this.getLimit().getRecruitNum() + "',reject_note='"
				+ this.getReject_note() + "' where id ='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitPhotoRecord.confirm() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int unConfirm() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_plan_info set status='0',recruitnum='"
				+ this.getLimit().getRecruitNum() + "',reject_note='"
				+ this.getReject_note() + "' where id ='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitPhotoRecord.unConfirm() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int rejectConfirm() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_plan_info set status='2',recruitnum='"
				+ this.getLimit().getRecruitNum() + "',reject_note='"
				+ this.getReject_note() + "' where id ='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitPhotoRecord.rejectConfirm() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		if (isExist() > 0) {
			throw new PlatformException("����Ρ�վ�㡢רҵ����ε�����ƻ��Ѿ����ڣ�");
		}
		String recruitNumValid = "0";
		String signNumValid = "0";
		String signTimeValid = "0";
		if (this.getLimit().isRecruitNumValid())
			recruitNumValid = "1";
		if (this.getLimit().isSignNumValid())
			signNumValid = "1";
		if (this.getLimit().isSignTimeValid())
			signTimeValid = "1";
		String sql = "";
		sql = "insert into recruit_plan_info (id,title,batch_id,edutype_id,major_id,site_id,recruitnum,recruitnumvalid,signnum,signnumvalid,startdate,enddate,signtimevalid,status) values(to_char(s_recruit_plan_info_id.nextval),'"
				+ this.getTitle()
				+ " ','"
				+ this.getBatch().getId()
				+ "','"
				+ this.getEdutype().getId()
				+ "','"
				+ this.getMajor().getId()
				+ "','"
				+ this.getSite().getId()
				+ "',"
				+ this.getLimit().getRecruitNum()
				+ ",'"
				+ recruitNumValid
				+ "',"
				+ this.getLimit().getSignNum()
				+ ",'"
				+ signNumValid
				+ "',sysdate,sysdate,'"
				+ signTimeValid
				+ "','"
				+ this.getStatus() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRecruitPhotoRecord.add() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String recruitNumValid = "0";
		String signNumValid = "0";
		String signTimeValid = "0";
		if (this.getLimit().isRecruitNumValid())
			recruitNumValid = "1";
		if (this.getLimit().isSignNumValid())
			signNumValid = "1";
		if (this.getLimit().isSignTimeValid())
			signTimeValid = "1";
		String sql = "";
		sql = "update recruit_plan_info set title='" + this.getTitle()
				+ "',batch_id='" + this.getBatch().getId() + "',edutype_id='"
				+ this.getEdutype().getId() + "',major_id='"
				+ this.getMajor().getId() + "',site_id='"
				+ this.getSite().getId() + "',recruitnum='"
				+ this.getLimit().getRecruitNum() + "',recruitnumvalid='"
				+ recruitNumValid + "',signnum='"
				+ this.getLimit().getSignNum() + "',signnumvalid='"
				+ signNumValid + "',startdate=to_date('"
				+ this.getLimit().getSignTime().getStartTime()
				+ "','yyyy-mm-dd'),enddate=to_date('"
				+ this.getLimit().getSignTime().getEndTime()
				+ "','yyyy-mm-dd'),signtimevalid='" + signTimeValid
				+ "',status='" + this.getStatus() + "' where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitPhotoRecord.update() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from recruit_plan_info where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRecruitPhotoRecord.delete() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int updateNum() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_plan_info set recruitnum='"
				+ this.getLimit().getRecruitNum() + "',status='0' where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitPhotoRecord.updateNum() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int isExist() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from recruit_plan_info where batch_id='"
				+ this.getBatch().getId() + "' and edutype_id='"
				+ this.getEdutype().getId() + "' and major_id='"
				+ this.getMajor().getId() + "' and site_id='"
				+ this.getSite().getId() + "'";
		int i = db.countselect(sql);
		return i;
	}

}
