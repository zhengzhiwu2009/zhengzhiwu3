package com.whaty.platform.database.oracle.standard.test.exam;

import java.sql.SQLException;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.test.exam.ExamSequence;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleExamSequence extends ExamSequence {
	public OracleExamSequence() {
	}

	public OracleExamSequence(String aid) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		sql = "select title,to_char(startdate,'yyyy-mm-dd HH24:MI:SS') as s_date,to_char(enddate,'yyyy-mm-dd HH24:MI:SS') as e_date,note,testbatch_id,basicsequence_id from test_examsequence_info where id='"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setTitle(rs.getString("title"));
				this.setStartDate(rs.getString("s_date"));
				this.setEndDate(rs.getString("e_date"));
				this.setNote(rs.getString("note"));
				this.setBatchId(rs.getString("testbatch_id"));
				this.setBasicSequenceId(rs.getString("basicsequence_id"));
				this.setId(aid);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleExamBatch(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into test_examsequence_info (id,title,startdate,enddate,note,testbatch_id,basicsequence_id) values (to_char(s_test_examsequence_info_id.nextval),'"
				+ this.getTitle()
				+ "',to_date('"
				+ this.getStartDate()
				+ "','yyyy-mm-dd HH24:MI:SS'),to_date('"
				+ this.getEndDate()
				+ "','yyyy-mm-dd HH24:MI:SS'),'"
				+ this.getNote()
				+ "','"
				+ this.getBatchId() + "','" + this.getBasicSequenceId() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleExamSequence.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	
	/**
	 * @author shu
	 * @return ӿԳΣʱ䣬ΣΣ
	 */
	public int addWithBatch() throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rss = null;
		String sql1 = "";
		String sql = "";
		String thisBatchId = this.getBatchId();
		sql1 = "select name from test_exambatch_info where id="+thisBatchId+"";
		rss = db.executeQuery(sql1);
		String exambatch_name = "";
		try {
			while(rss!=null&&rss.next()){
				exambatch_name = rss.getString("name");
						}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
		}
		
		sql = "insert into test_examsequence_info (id,title,startdate,enddate,note,testbatch_id,basicsequence_id) values (to_char(s_test_examsequence_info_id.nextval),'"
				+ exambatch_name+this.getTitle()
				+ "',to_date('"
				+ this.getStartDate()
				+ "','yyyy-mm-dd HH24:MI:SS'),to_date('"
				+ this.getEndDate()
				+ "','yyyy-mm-dd HH24:MI:SS'),'"
				+ this.getNote()
				+ "','"
				+ this.getBatchId() + "','" + this.getBasicSequenceId() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleExamSequence.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rss = null;
		String sql1 = "";
		String sql = "";
		String thisBatchId = this.getBatchId();
		sql1 = "select name from test_exambatch_info where id="+thisBatchId+"";
		rss = db.executeQuery(sql1);
		String exambatch_name = "";
		try {
			while(rss!=null&&rss.next()){
				exambatch_name = rss.getString("name");
						}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
		}
		sql = "update test_examsequence_info set title='" + exambatch_name+this.getTitle()
				+ "',startdate=to_date('" + this.getStartDate()
				+ "','yyyy-mm-dd HH24:MI:SS')," + "enddate=to_date('"
				+ this.getEndDate() + "','yyyy-mm-dd HH24:MI:SS')," + "note='"
				+ this.getNote() + "',testbatch_id='" + this.getBatchId() + "'"
				+ ",basicsequence_id='" + this.getBasicSequenceId() + "'"
				+ "  where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleExamSequence.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		
		String sql1 = "select t.id from test_examcourse_info t ,test_examsequence_info t2 " +
			"where t2.basicsequence_id = t.basicsequence_id and t.test_batch_id = t2.testbatch_id " + 
			"and t2.id='"+this.getId()+"'";
		int ii = db.countselect(sql1);
		if(ii>0){
			throw new PlatformException(this.getTitle()+ "Ѱſγ̣");
				
		}
		
		
		
		String sql  = "delete from test_examsequence_info where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleExamSequence.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
