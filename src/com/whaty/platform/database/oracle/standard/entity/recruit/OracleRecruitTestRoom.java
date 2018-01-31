package com.whaty.platform.database.oracle.standard.entity.recruit;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.recruit.RecruitTestRoom;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleRecruitTestRoom extends RecruitTestRoom {
	/** Creates a new instance of OracleRecruitTestRoom */
	public OracleRecruitTestRoom() {
	}
	
	public OracleRecruitTestRoom(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,title,note,testsite_id,num,sort_id,batch_id,room_no from recruit_test_room where id = '"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				this.setNote(rs.getString("note"));
				OracleRecruitTestSite testSite = new OracleRecruitTestSite();
				testSite.setId(rs.getString("testsite_id"));
				this.setTestSite(testSite);
				OracleRecruitSort sort = new OracleRecruitSort();
				sort.setId(rs.getString("sort_id"));
				this.setSort(sort);
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("batch_id"));
				this.setBatch(batch);
				this.setNum(rs.getInt("num"));
				this.setRoomNo(rs.getString("room_no"));
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleRecruitTestRoom(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	
	public RecruitTestRoom getRecruitTestRoom(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,title,note,testsite_id,num,sort_id,batch_id,room_no from recruit_test_room where id = '"
				+ aid + "'";
		OracleRecruitTestRoom room=new OracleRecruitTestRoom();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				room.setId(rs.getString("id"));
				room.setTitle(rs.getString("title"));
				room.setNote(rs.getString("note"));
				OracleRecruitTestSite testSite = new OracleRecruitTestSite();
				testSite.setId(rs.getString("testsite_id"));
				room.setTestSite(testSite);
				OracleRecruitSort sort = new OracleRecruitSort();
				sort.setId(rs.getString("sort_id"));
				room.setSort(sort);
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("batch_id"));
				room.setBatch(batch);
				room.setNum(rs.getInt("num"));
				room.setRoomNo(rs.getString("room_no"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleRecruitTestRoom(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return room;
	}
	
	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into recruit_test_room (id,title,note,testsite_id,sort_id,batch_id,num,room_no) values(to_char(s_recruit_test_room_id.nextval),'"
				+ this.getTitle()
				+ "','"
				+ this.getNote()
				+ "','"
				+ this.getTestSite().getId()
				+ "','"
				+ this.getSort().getId()
				+ "','"
				+ this.getBatch().getId()
				+ "','"
				+ this.getNum()
				+ "','" + this.getRoomNo() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRecruitTestRoom.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_test_room set title='" + this.getTitle()
				+ "',note='" + this.getNote() + "',testsite_id='"
				+ this.getTestSite().getId() + "',sort_id='"
				+ this.getSort().getId() + "',batch_id='"
				+ this.getBatch().getId() + "',num='" + this.getNum()
				+ "',room_no='" + this.getRoomNo() + "' where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitTestRoom.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		if (isHasTestDesks() > 0)
			throw new PlatformException(
					"The RecruitTestRoom has recruitTestDesks");
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from recruit_test_room where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRecruitTestRoom.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int isHasTestDesks() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select testroom_id from recruit_test_desk where testroom_id ='"
				+ this.getId() + "'";
		int i = db.countselect(sql);
		return i;
	}

	public String getNumByBatchId(String batch_id) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		String num = "";
		sql = "select distinct num from recruit_test_room where batch_id ='"
				+ batch_id + "'";
		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				num = rs.getString("num");
			} else {
				num = "";
			}
		} catch (Exception e) {
			
		}
		db.close(rs);
		return num;
	}

	public int updateSite(String id,String site) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_test_room set title='" + site
				+"' where id='"
				+ id+ "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitTestRoom.updateSite() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	
	
}
