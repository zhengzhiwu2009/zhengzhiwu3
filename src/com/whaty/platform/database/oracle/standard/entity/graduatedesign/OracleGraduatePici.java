package com.whaty.platform.database.oracle.standard.entity.graduatedesign;

import java.sql.SQLException;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.graduatedesign.Pici;
import com.whaty.platform.entity.recruit.TimeDef;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * 类描述：毕业设计批次的属性
 * 
 * @author zhangliang
 * 
 */
public class OracleGraduatePici extends Pici {

	public OracleGraduatePici() {

	}

	public OracleGraduatePici(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select a.id,a.name,starttime,endtime,subjectstime,subjectetime,opensubstime,endsubetime,subsubstime,subsubetime,rejoinstime,rejoinetime,reportgradestime,reportgradeetime,status,WRITEDISCOURSESTIME,WRITEDISCOURSEETIME,semester_id,b.name as semester_name from entity_graduate_pici a,entity_semester_info b where a.semester_id=b.id(+) and a.id ='"
				+ id + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				TimeDef graduateDesignTime = new TimeDef();
				graduateDesignTime.setStartTime(rs.getString("starttime"));
				graduateDesignTime.setEndTime(rs.getString("endtime"));
				this.setGraduateDesignTime(graduateDesignTime);
				
				TimeDef subjectTime = new TimeDef();
				subjectTime.setStartTime(rs.getString("subjectstime"));
				subjectTime.setEndTime(rs.getString("subjectetime"));
				this.setSubjectTime(subjectTime);
				
				TimeDef openSubTime = new TimeDef();
				openSubTime.setStartTime(rs.getString("opensubstime"));
				openSubTime.setEndTime(rs.getString("endsubetime"));
				this.setOpenSubTime(openSubTime);
				
				TimeDef subSubTime = new TimeDef();
				subSubTime.setStartTime(rs.getString("subsubstime"));
				subSubTime.setEndTime(rs.getString("subsubetime"));
				this.setSubSubTime(subSubTime);
				
				TimeDef rejoinTime = new TimeDef();
				rejoinTime.setStartTime(rs.getString("rejoinstime"));
				rejoinTime.setEndTime(rs.getString("rejoinetime"));
				this.setReJoinTime(rejoinTime);
				
				TimeDef reportGradeTime = new TimeDef();
				reportGradeTime.setStartTime(rs.getString("reportgradestime"));
				reportGradeTime.setEndTime(rs.getString("reportgradeetime"));
				this.setReportGradeTime(reportGradeTime);
				
				TimeDef writeDiscourseTime = new TimeDef();
				writeDiscourseTime.setStartTime(rs.getString("WRITEDISCOURSESTIME"));
				writeDiscourseTime.setEndTime(rs.getString("WRITEDISCOURSEETIME"));
				this.setWriteDiscourseTiem(writeDiscourseTime);
				
				this.setStatus(rs.getString("status"));
				this.setSemester_name(rs.getString("semester_name")) ;
				this.setSemester_id(rs.getString("semester_id")) ;
				if(rs.getString("status").equals("1")){
					this.setActive(true);
				}
			}
		} catch (SQLException e) {
			EntityLog
					.setDebug("OracleGraduatePici@OracleGraduatePici(String id) error !! sql="
							+ sql);
			
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";

		sql = "insert into entity_graduate_pici (id,name,starttime,endtime,subjectstime,subjectetime,opensubstime,endsubetime,subsubstime,subsubetime,rejoinstime,rejoinetime,reportgradestime,reportgradeetime,status,WRITEDISCOURSESTIME,WRITEDISCOURSEETIME,semester_id) values ('"
				+ this.getId()
				+ "','"
				+ this.getName()
				+ "',to_date('"
				+ this.getGraduateDesignTime().getStartTime()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getGraduateDesignTime().getEndTime()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getSubjectTime().getStartTime()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getSubjectTime().getEndTime()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getOpenSubTime().getStartTime()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getOpenSubTime().getEndTime()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getSubSubTime().getStartTime()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getSubSubTime().getEndTime()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getReJoinTime().getStartTime()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getReJoinTime().getEndTime()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getReportGradeTime().getStartTime()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getReportGradeTime().getEndTime()
				+ "','yyyy-mm-dd'),'0',to_date('" 
				+ this.getWriteDiscourseTiem().getStartTime()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getWriteDiscourseTiem().getEndTime()
				+ "','yyyy-mm-dd'),'"+this.getSemester_id()+"')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleGraduatePici.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_graduate_pici set" + " id = '" + this.getId()
				+ "',name = '" + this.getName() + "',starttime= to_date('"
				+ this.getGraduateDesignTime().getStartTime() + "', 'yyyy-mm-dd'),endtime = to_date('"
				+ this.getGraduateDesignTime().getEndTime()
				+ "', 'yyyy-mm-dd'),subjectstime = to_date('"
				+ this.getSubjectTime().getStartTime()
				+ "', 'yyyy-mm-dd'),subjectetime= to_date('"
				+ this.getSubjectTime().getEndTime()
				+ "', 'yyyy-mm-dd'),opensubstime = to_date('"
				+ this.getOpenSubTime().getStartTime()
				+ "', 'yyyy-mm-dd'),endsubetime = to_date('"
				+ this.getOpenSubTime().getEndTime()
				+ "', 'yyyy-mm-dd'),subsubstime = to_date('"
				+ this.getSubSubTime().getStartTime()
				+ "', 'yyyy-mm-dd'),subsubetime= to_date('"
				+ this.getSubSubTime().getEndTime()
				+ "', 'yyyy-mm-dd'),rejoinstime= to_date('"
				+ this.getReJoinTime().getStartTime()
				+ "', 'yyyy-mm-dd'),rejoinetime = to_date('"
				+ this.getReJoinTime().getEndTime()
				+ "', 'yyyy-mm-dd'),reportgradestime = to_date('"
				+ this.getReportGradeTime().getStartTime()
				+ "', 'yyyy-mm-dd'),reportgradeetime = to_date('"
				+ this.getReportGradeTime().getEndTime() + "', 'yyyy-mm-dd'),WRITEDISCOURSESTIME = to_date('"
				+ this.getWriteDiscourseTiem().getStartTime()+"','yyyy-mm-dd'),WRITEDISCOURSEETIME = to_date('"
				+ this.getWriteDiscourseTiem().getEndTime()
				+"','yyyy-mm-dd'),semester_id='"+this.getSemester_id()+"' where id ='"+this.getId()+"'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleGraduatePici.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_graduate_pici where id='" + this.getId()
				+ "'";
		EntityLog.setDebug("OracleGraduatePici@Method:delete()=" + sql);
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleGraduatePici.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int setActive() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_graduate_pici set status='1' where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleGraduatePici.setActive() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int cancelActive() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_graduate_pici set status='0' where status = '1'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleGraduatePici.cancelActive() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int graduateDesignTimeSect() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "Select * From entity_graduate_pici t  Where t.status ='1' and SYSDATE between t.starttime and t.endtime+1";
		int i = db.countselect(sql);
		return i;
	}

	public int subjectResearchTimeSect() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "Select * From entity_graduate_pici t  Where t.status ='1' and SYSDATE between t.subjectstime and t.subjectetime+1";
		int i = db.countselect(sql);
		return i;
	}

	public int regBgCourseTimeSect() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "Select * From entity_graduate_pici t  Where t.status ='1' and SYSDATE between t.opensubstime and t.endsubetime+1";
		int i = db.countselect(sql);
		return i;
	}

	public int discourseSumbitTimeSect() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "Select * From entity_graduate_pici t  Where t.status ='1' and SYSDATE between t.subsubstime and t.subsubetime+1";
		int i = db.countselect(sql);
		return i;
	}

	public int rejoinRequesTimeSect() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "Select * From entity_graduate_pici t  Where t.status ='1' and SYSDATE between t.rejoinstime and t.rejoinetime+1";
		int i = db.countselect(sql);
		return i;
	}

	public int reportGraduTimeSect() {
		dbpool db = new dbpool();
		String sql = "Select * From entity_graduate_pici t  Where t.status ='1' and  SYSDATE between t.reportgradestime and t.reportgradeetime+1";
		int i = db.countselect(sql);
		return i;
	}
	public int witeDiscourseTimeSect() {
		dbpool db = new dbpool();
		String sql = "Select * From entity_graduate_pici t  Where t.status ='1' and  SYSDATE between t.WRITEDISCOURSESTIME and t.WRITEDISCOURSEETIME+1";
		int i = db.countselect(sql);
		return i;
	}
 
}
