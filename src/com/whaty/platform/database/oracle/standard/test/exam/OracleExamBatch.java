package com.whaty.platform.database.oracle.standard.test.exam;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.test.exam.ExamBatch;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.log.Log;
import com.whaty.util.string.StrManageFactory;

public class OracleExamBatch extends ExamBatch {
	public OracleExamBatch() {
	}
	
	public OracleExamBatch(String aid) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		sql = "select name,to_char(s_date,'yyyy-mm-dd') as s_date,to_char(e_date,'yyyy-mm-dd') as e_date,status,to_char(examroom_s_date,'yyyy-mm-dd') as examroom_s_date,to_char(examroom_e_date,'yyyy-mm-dd') as examroom_e_date from test_exambatch_info where id='"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setName(rs.getString("name"));
				this.setStartDate(rs.getString("s_date"));
				this.setEndDate(rs.getString("e_date"));
				this.setStatus(rs.getString("status"));
				this.setExamRoomStartDate(StrManageFactory.creat().fixNull(rs.getString("examroom_s_date")));
				this.setExamRoomEndDate(StrManageFactory.creat().fixNull(rs.getString("examroom_e_date")));
				this.setId(aid);
			}
		} catch (java.sql.SQLException e) {
			
		} catch (WhatyUtilException e) {
			Log.setError("OracleExamBatch(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		
		String sql1 = "select * from test_exambatch_info where id="+this.getId();
		int j = db.countselect(sql1);
		if(j>0){
			throw new PlatformException("编号为"
					+ this.getId() + "已经存在！");
		}
		
		String sql =  "insert into test_exambatch_info (id,name,s_date,e_date,examroom_s_date,examroom_e_date,status) values ('"
				+ this.getId()
				+ "','"
				+ this.getName()
				+ "',to_date('"
				+ this.getStartDate()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getEndDate()
				+ "','yyyy-mm-dd'),to_date("
				+ this.getExamRoomStartDate()
				+ ",'yyyy-mm-dd'),to_date("
				+ this.getExamRoomEndDate()
				+ ",'yyyy-mm-dd'),'"
				+ this.getStatus()
				+ "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleExamBatch.add() SQL=" + sql + " COUNT" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update test_exambatch_info set name='" + this.getName()
				+ "',s_date=to_date('" + this.getStartDate()
				+ "','yyyy-mm-dd')," + "e_date=to_date('" + this.getEndDate()
				+ "','yyyy-mm-dd')," + "status='" + this.getStatus() + "'"
				+ ",examroom_s_date=to_date('" + this.getExamRoomStartDate()
				+ "','yyyy-mm-dd'),examroom_e_date=to_date('" + this.getExamRoomEndDate()
				+ "','yyyy-mm-dd') where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleExamBatch.update() SQL=" + sql + " COUNT" + i + " DATE=" + new Date());
		return i;
	}
/**
 * @author shu
 * @return 通过批次id来删除批次。
 */
	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		
		String sql1 = "select t.id from test_examcourse_info t where t.test_batch_id='"+this.getId()+"'";
		int ii = db.countselect(sql1);
		if(ii>0){
			throw new PlatformException(this.getName()+"批次下已经安排课程");
		}
		
		String sql =  "delete from test_exambatch_info where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleExamBatch.delete() SQL=" + sql + " COUNT" + i + " DATE=" + new Date());
		return i;
	}

}
