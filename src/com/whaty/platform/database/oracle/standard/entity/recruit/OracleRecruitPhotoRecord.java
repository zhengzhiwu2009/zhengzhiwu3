package com.whaty.platform.database.oracle.standard.entity.recruit;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.recruit.RecruitPhotoRecord;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleRecruitPhotoRecord extends RecruitPhotoRecord {
	public OracleRecruitPhotoRecord() {
	}

	public OracleRecruitPhotoRecord(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select user_id,photo_link,photo_status,note from recruit_photo_record where user_id = '"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setUser_id(rs.getString("user_id"));
				this.setPhoto_link(rs.getString("photo_link"));
				this.setPhoto_status(rs.getString("photo_status"));
				this.setNote(rs.getString("note"));
			}
		} catch (Exception e) {
			EntityLog.setError("OracleRecruitPhotoRecord(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "insert into recruit_photo_record (user_id,photo_link,photo_status,note) values ('"
				+ this.getUser_id()
				+ "','"
				+ this.getPhoto_link()
				+ "','"
				+ this.getPhoto_status() + "','" + this.getNote() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRecruitPhotoRecord.add() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		return 0;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "delete from recruit_photo_record where user_id = '"
				+ this.getUser_id() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitPhotoRecord.update() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
