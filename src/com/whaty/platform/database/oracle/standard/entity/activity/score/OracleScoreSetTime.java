package com.whaty.platform.database.oracle.standard.entity.activity.score;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.activity.score.ScoreSetTime;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleScoreSetTime extends ScoreSetTime{
	
	
	public OracleScoreSetTime() {
	}
	public OracleScoreSetTime(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = "select id,semester_id,start_usual_time,end_usual_time,start_experiment_time,end_experiment_time,start_exam_time,end_exam_time,status_usual_time,status_experiment_time,status_exam_time from entity_score_uptime where id='"+id+"'";
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				
				ScoreSetTime scoreSetTime= (ScoreSetTime) new OracleScoreSetTime();
				scoreSetTime.setId(rs.getString("id"));
				scoreSetTime.setSemester_id(rs.getString("semeter_id"));
				scoreSetTime.setStart_usual_time(rs.getString("start_usual_time"));
				scoreSetTime.setEnd_usual_time(rs.getString("end_usual_time"));
				
				scoreSetTime.setStart_experiment_time(rs.getString("start_experiment_time"));
				scoreSetTime.setEnd_experiment_time(rs.getString("end_experiment_time"));
				
				scoreSetTime.setStart_exam_time(rs.getString("start_exam_time"));
				scoreSetTime.setEnd_exam_time(rs.getString("end_exam_time"));
				
				scoreSetTime.setStatus_usual_time(rs.getString("status_usual_time"));
				scoreSetTime.setStatus_experiment_time(rs.getString("status_experiment_time"));
				scoreSetTime.setStatus_exam_time(rs.getString("status_exam_time"));
				
			
				
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleScoreSetTime@Method:OracleScoreSetTime(String id) Error! (SQL = "+sql+")");
		} finally {
			db.close(rs);
			db = null;
		}
	}

	
	
	public ScoreSetTime  getScoreSetTime(String semester_id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		ScoreSetTime scoreSetTime= (ScoreSetTime) new OracleScoreSetTime();
		try {
			sql = "select id,semester_id,start_usual_time,end_usual_time,start_experiment_time,end_experiment_time,start_exam_time,end_exam_time,status_usual_time,status_experiment_time,status_exam_time from entity_score_uptime where semester_id='"+semester_id+"'";
			//System.out.println(sql);
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				
				
				scoreSetTime.setId(rs.getString("id"));
				scoreSetTime.setSemester_id(rs.getString("semester_id"));
				
				scoreSetTime.setStart_usual_time(rs.getString("start_usual_time"));
				scoreSetTime.setEnd_usual_time(rs.getString("end_usual_time"));
				
				scoreSetTime.setStart_experiment_time(rs.getString("start_experiment_time"));
				scoreSetTime.setEnd_experiment_time(rs.getString("end_experiment_time"));
				
				scoreSetTime.setStart_exam_time(rs.getString("start_exam_time"));
				scoreSetTime.setEnd_exam_time(rs.getString("end_exam_time"));
				
				scoreSetTime.setStatus_usual_time(rs.getString("status_usual_time"));
				scoreSetTime.setStatus_experiment_time(rs.getString("status_experiment_time"));
				scoreSetTime.setStatus_exam_time(rs.getString("status_exam_time"));
				
			
				
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleScoreSetTime@Method:getScoreSetTime(String id) Error! (SQL = "+sql+")");
		} finally {
			db.close(rs);
			db = null;
		}
		return scoreSetTime;
	}

	
	public int addScoreSetTime() throws PlatformException {
		int i=0;
		//if(IsExist()<=0){
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_score_uptime (id,semester_id,start_usual_time,end_usual_time,start_experiment_time,end_experiment_time,start_exam_time,end_exam_time,status_usual_time,status_experiment_time,status_exam_time) values(to_char(s_entity_score_uptime.nextval),'"+this.getSemester_id()+"','"+this.getStart_usual_time()+"','"+this.getEnd_usual_time()+"','"+this.getStart_experiment_time()+"','"+this.getEnd_experiment_time()+"','"+this.getStart_exam_time()+"','"+this.getEnd_exam_time()+"','"+this.getStatus_usual_time()+"','"+this.getStatus_experiment_time()+"','"+this.getStatus_exam_time()+"')";
		//System.out.println(sql);	
		i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleScoreSetTime.addScoreSetTime() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());

	//}
		return i;
	}
	
	
	public  int updateScoreTime() throws PlatformException {
		dbpool db = new dbpool();
	
		int count=0;
		String sql = "";
		sql = "update  entity_score_uptime set start_usual_time='"+this.getStart_usual_time()+"',end_usual_time='"+this.getEnd_usual_time()+"',start_experiment_time='"+this.getStart_experiment_time()+"',end_experiment_time='"+this.getEnd_experiment_time()+"',start_exam_time='"+this.getStart_exam_time()+"',end_exam_time='"+this.getEnd_exam_time()+"',status_usual_time='"+this.getStatus_usual_time()+"',status_experiment_time='"+this.getStatus_experiment_time()+"',status_exam_time='"+this.getStatus_exam_time()+"'   where semester_id='"+this.getSemester_id()+"'";
		count=db.executeUpdate(sql);	
		//System.out.println(sql);	
		UserUpdateLog.setDebug("OracleScoreSetTime.updateScoreTime() SQL=" + sql + " COUNT=" + count + " DATE=" + new Date());
		return count; 
	}
	

	
	public int IsExist() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select *  from entity_score_uptime where semester_id='"
				+ this.getSemester_id() + "'";
		int i = db.countselect(sql);
		return i;
	}
	
	
	public  int updateScoreTimeStatus(String semester_id,String status,String which_time) throws PlatformException {
		dbpool db = new dbpool();
	
		int count=0;
		String sql = "";
		sql = "update  entity_score_uptime set "+which_time+"='"+status+"' where semester_id='"+semester_id+"'" ;
		count=db.executeUpdate(sql);	
		UserUpdateLog.setDebug("OracleScoreSetTime.updateScoreTimeStatus() SQL=" + sql + " COUNT=" + count + " DATE=" + new Date());
		return count; 
	}
	
}
