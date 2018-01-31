/**
 * 
 */
package com.whaty.platform.database.oracle.standard.standard.scorm.operation;

import java.sql.SQLException;
import org.apache.struts2.ServletActionContext;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.standard.scorm.Exception.ScormException;
import com.whaty.platform.standard.scorm.operation.CMIWithDB;
import com.whaty.platform.standard.scorm.util.ScormLog;
import com.whaty.platform.standard.scorm.util.ScormUtil;
import com.whaty.platform.training.basic.StudyProgress;

/**
 * @author Administrator
 * 
 */
public class OracleCMIWithDB extends CMIWithDB {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.standard.scorm.operation.CMIWithDB#putToDB()
	 */
	public void putToDB() throws ScormException {

		// System.out.println("===========STUDENT_ID==========="+this.getUserId());
		// System.out.println("===========COURSE_ID==========="+this.getCourseId());
		// System.out.println("===========CORE_LESSON==========="+this.getScoData().getSuspendData().getSuspendData().getValue());

		ScormLog.setDebug("start put to DB!!!");
		if (this.getScoData().getCore().getLessonStatus() == null || this.getScoData().getCore().getLessonStatus().getValue().equals(""))
			this.getScoData().getCore().setLessonStatus("incomplete");

		try {
			this.addCourseData();

			this.putScoData();

			this.updateCourseData();
		} catch (ScormException e) {
			throw new ScormException(e.getMessage());
		}
		try {
			if (this.getScoData().getCore().getLessonStatus().getValue().equals("completed")
					|| this.getScoData().getCore().getLessonStatus().getValue().equals("passed"))
				this.updateCourseCompleteStatus();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	private void addCourseData() throws ScormException {
		dbpool db = new dbpool();
		String sqlcourse = "select id from scorm_stu_course where student_id='" + this.getUserId() + "' " + "and course_id='"
				+ this.getCourseId() + "'";
		ScormLog.setDebug(sqlcourse);
		MyResultSet result = db.executeQuery(sqlcourse);
		try {
			// 修改查询，避免查询意外导致的没有记录
			// int countCourse = db.countselect(sqlcourse);
			if (result == null) {
				ScormLog.setError("查询数据连接异常：" + sqlcourse);
				return;
			}
			if (result.getMyrset() == null) {
				ScormLog.setError("查询数据连接异常：" + sqlcourse);
				return;
			}
			if (!result.getMyrset().next()) {
				db.close(result);
				ScormLog.setDebug("start put New CourseData to DB!!!");
				// sqlcourse="insert into
				// SCORM_STU_COURSE(ID,STUDENT_ID,COURSE_ID,CREDIT," +
				// "LESSON_LOCATION,LESSON_STATUS,RAW_SCORE,MAX_SCORE,MIN_SCORE,"
				// +
				// "TOTAL_TIME,LESSON_MODE,CORE_LESSON ,CORE_VENDOR ,HIGH_SCORE
				// ," +
				// "HIGH_STATUS,FIRST_DATE,LAST_DATE,BROWSE_SCORE,SESSION_DATE,"
				// +
				// "COMPLETE_PERCENT,ATTEMPT_NUM) values(" +
				// "to_char(s_scorm_id.nextval),'"+this.getUserId()+"'," +
				// "'"+this.getCourseId()+"','','"+this.getSystemId()+"'," +
				// "'incomplete','0','0','0','0:00:00','','','','','',sysdate,"
				// +
				// "sysdate,'',sysdate,0,1)";

				// 添加离线记录后,修改了没有记录时插入的默认参数; 都使用获得的参数值;
				sqlcourse = "insert into SCORM_STU_COURSE(ID,STUDENT_ID,COURSE_ID,CREDIT,"
						+ "LESSON_LOCATION,LESSON_STATUS,RAW_SCORE,MAX_SCORE,MIN_SCORE,"
						+ "TOTAL_TIME,LESSON_MODE,CORE_LESSON ,CORE_VENDOR ,HIGH_SCORE ,"
						+ "HIGH_STATUS,FIRST_DATE,LAST_DATE,BROWSE_SCORE,SESSION_DATE," + "COMPLETE_PERCENT,ATTEMPT_NUM) values("
						+ "to_char(s_scorm_id.nextval),'" + this.getUserId() + "'," + "'" + this.getCourseId() + "','','"
						+ this.getSystemId() + "'," + "'" + this.getScoData().getCore().getLessonStatus().getValue() + "','"
						+ this.getScoData().getCore().getScore().getRaw().getValue() + "','"
						+ this.getScoData().getCore().getScore().getMax().getValue() + "','"
						+ this.getScoData().getCore().getScore().getMin().getValue() + "','"
						+ this.getScoData().getCore().getTotalTime().getValue() + "','','','','','',sysdate," + "sysdate,'',sysdate,0,1)";
				ScormLog.setDebug(sqlcourse);
				if (db.executeUpdate(sqlcourse) < 1) {
					// System.out.println("put new coursedata into DB error in
					// addCourseData!" + sqlcourse);
					throw new ScormException("put new coursedata into DB error in addCourseData!" + sqlcourse);
				}
			} else {
				db.close(result);
			}
		} catch (Exception e) {
			throw new ScormException("addCourseData error!" + e.toString());
		} finally {
			db = null;
		}
	}

	private void putScoData() throws ScormException {
		dbpool db = new dbpool();
		String sql = "select id from scorm_stu_sco where student_id='" + this.getUserId() + "' " + "and course_id='" + this.getCourseId()
				+ "' and system_id='" + this.getSystemId() + "'";
		// int count = db.countselect(sql);

		String total_time = null;
		total_time = this.getScoData().getCore().getTotalTime().getValue();
		int index = total_time.indexOf("-");
		if (total_time == null || total_time.equals("")) {
			total_time = "00:00:00.0";
		} else if (index != -1) {
			total_time = "00:01:00.0";
		}

		// 处理过大值
		/*
		 * int index_hour=total_time.indexOf(":"); String
		 * hour_time=total_time.substring(0,index_hour);
		 * //System.out.println("hour_time>>>>>>>>>>>>>,"+hour_time+",total_time,"+total_time);
		 * try{ int i_hour=Integer.parseInt(hour_time); if(i_hour>1) {
		 * total_time="01:"+total_time.substring(index_hour+1); }
		 * 
		 * }catch(Exception e){ System.out.println("putScoData
		 * error-parseInt:"+e.toString()); }
		 */
		// 修改查询，处理查询异常情况
		MyResultSet result = db.executeQuery(sql);
		if (result == null) {
			ScormLog.setError("查询数据连接异常：" + sql);
			return;
		}
		if (result.getMyrset() == null) {
			ScormLog.setError("查询数据连接异常：" + sql);
			return;
		}
		try {
			if (!result.getMyrset().next()) {
				db.close(result);
				// sql="insert into
				// SCORM_STU_SCO(ID,SYSTEM_ID,COURSE_ID,STUDENT_ID," +
				// "FIRST_ACCESSDATE,LAST_ACCESSDATE,SESSION_ID,RAW_SCORE,MAX_SCORE,"
				// +
				// "MIN_SCORE,STATUS,TOTAL_TIME,CREDIT,ATTEMPT_NUM,LESSON_LOCATION,"
				// +
				// "CORE_LESSON,CORE_VENDOR,LESSON_MODE,HIGH_SCORE,HIGH_STATUS,BROWSE_SCORE,"
				// +
				// "SESSION_DATA,COMPLETE_PERCENT,EXIT,ENTRY) values(" +
				// "to_char(s_scorm_id.nextval),'"+this.getSystemId()+"'," +
				// "'"+this.getCourseId()+"','"+this.getUserId()+"'," +
				// "sysdate,sysdate,'','0'," +
				// "'"+this.getScoData().getCore().getScore().getMax().getValue()+"',"
				// +
				// "'"+this.getScoData().getCore().getScore().getMin().getValue()+"',"
				// +
				// "'"+this.getScoData().getCore().getLessonStatus().getValue()+"',"
				// +
				// "'0:00:00'," +
				// "'"+this.getScoData().getCore().getCredit().getValue()+"'," +
				// "1,'"+this.getScoData().getCore().getLessonLocation().getValue()+"',"
				// +
				// "'"+this.getScoData().getSuspendData().getSuspendData().getValue()+"',"
				// +
				// "'"+this.getScoData().getLaunchData().getLaunchData().getValue()+"',"
				// +
				// "'"+this.getScoData().getCore().getLessonMode().getValue()+"',"
				// +
				// "'','','',sysdate,'0','"+this.getScoData().getCore().getExit().getValue()+"',"
				// +
				// "'"+this.getScoData().getCore().getEntry().getValue()+"')";

				// 添加离线记录后,初始化时使用离线的参数;
				// 添加comments字段;用于记录离线sco位置;
				sql = "insert into SCORM_STU_SCO(ID,SYSTEM_ID,COURSE_ID,STUDENT_ID,"
						+ "FIRST_ACCESSDATE,LAST_ACCESSDATE,SESSION_ID,RAW_SCORE,MAX_SCORE,"
						+ "MIN_SCORE,STATUS,TOTAL_TIME,CREDIT,ATTEMPT_NUM,LESSON_LOCATION,"
						+ "CORE_LESSON,CORE_VENDOR,LESSON_MODE,HIGH_SCORE,HIGH_STATUS,BROWSE_SCORE,"
						+ "SESSION_DATA,COMPLETE_PERCENT,EXIT,ENTRY,comments) values(" + "to_char(s_scorm_id.nextval),'"
						+ this.getSystemId()
						+ "',"
						+ "'"
						+ this.getCourseId()
						+ "','"
						+ this.getUserId()
						+ "',"
						+ "sysdate,sysdate,'','"
						+ this.getScoData().getCore().getScore().getRaw().getValue()
						+ "',"
						+ "'"
						+ this.getScoData().getCore().getScore().getMax().getValue()
						+ "',"
						+ "'"
						+ this.getScoData().getCore().getScore().getMin().getValue()
						+ "',"
						+ "'"
						+ this.getScoData().getCore().getLessonStatus().getValue()
						+ "',"
						+ "'"
						+ total_time
						+ "',"
						+ "'"
						+ this.getScoData().getCore().getCredit().getValue()
						+ "',"
						+ "1,'"
						+ this.getScoData().getCore().getLessonLocation().getValue()
						+ "',"
						+ "'"
						+ this.getScoData().getSuspendData().getSuspendData().getValue()
						+ "',"
						+ "'"
						+ this.getScoData().getLaunchData().getLaunchData().getValue()
						+ "',"
						+ "'"
						+ this.getScoData().getCore().getLessonMode().getValue()
						+ "',"
						+ "'','','',sysdate,'0','"
						+ this.getScoData().getCore().getExit().getValue()
						+ "',"
						+ "'"
						+ this.getScoData().getCore().getEntry().getValue()
						+ "','" + this.getScoData().getComments().getComments().getValue() + "')";
			} else {
				db.close(result);
				sql = "update SCORM_STU_SCO set LAST_ACCESSDATE=sysdate," + "RAW_SCORE='"
						+ this.getScoData().getCore().getScore().getRaw().getValue() + "'," + "MAX_SCORE='"
						+ this.getScoData().getCore().getScore().getMax().getValue() + "'," + "MIN_SCORE='"
						+ this.getScoData().getCore().getScore().getMin().getValue() + "'," + "STATUS='"
						+ this.getScoData().getCore().getLessonStatus().getValue() + "'," + "TOTAL_TIME='" + total_time + "'," + "CREDIT='"
						+ this.getScoData().getCore().getCredit().getValue() + "'," + "LESSON_LOCATION='"
						+ this.getScoData().getCore().getLessonLocation().getValue() + "'," + "CORE_LESSON='"
						+ this.getScoData().getSuspendData().getSuspendData().getValue() + "'," + "CORE_VENDOR='"
						+ this.getScoData().getLaunchData().getLaunchData().getValue() + "'," + "LESSON_MODE='"
						+ this.getScoData().getCore().getLessonMode() + "'," + "SESSION_DATA=sysdate,ATTEMPT_NUM=ATTEMPT_NUM+1," + "EXIT='"
						+ this.getScoData().getCore().getExit().getValue() + "'," + "ENTRY='"
						+ this.getScoData().getCore().getEntry().getValue() + "',comments='"
						+ this.getScoData().getComments().getComments().getValue() + "' " + "where student_id='" + this.getUserId() + "' "
						+ "and course_id='" + this.getCourseId() + "' and system_id='" + this.getSystemId() + "'";

			}
			/* Lee start 学习记录明细 2014年12月08日 */
			String completeSql = "SELECT 1 FROM TRAINING_COURSE_STUDENT WHERE STUDENT_ID = '" + this.getUserId() + "' AND COURSE_ID = '"
					+ this.getOpenId() + "' AND LEARN_STATUS = 'COMPLETED' AND COMPLETE_DATE is not null";
			int completeRow = db.executeUpdate(completeSql);
			Boolean newTcs = false;
			StringBuffer sbTcsHis = new StringBuffer();
			if (completeRow > 0)
				newTcs = false;
			else
				newTcs = true;
			if (newTcs) {// 未完成学习才更新学习记录
				Object tcshStartTime = ServletActionContext.getRequest().getSession().getAttribute(this.getUserId() + "starttime");
				if (null == tcshStartTime || "".equals(tcshStartTime)) {
					System.out.println("TCSH无开始时间");
				} else {
					sbTcsHis.append(" UPDATE TRAINING_COURSE_STUDENT_HIS ");
					sbTcsHis.append("    SET END_TIME   = SYSDATE, ");
					sbTcsHis.append("        TOTAL_TIME = SUBSTR(NUMTODSINTERVAL(TO_NUMBER(SYSDATE - START_TIME) * 24 * 60 * 60, ");
					sbTcsHis.append("                                            'second'), ");
					sbTcsHis.append("                            12, ");
					sbTcsHis.append("                            11) ");
					sbTcsHis.append("  WHERE FK_STU_ID = '" + this.getUserId() + "' ");
					sbTcsHis.append("    AND COURSE_ID = '" + this.getOpenId() + "' ");
					sbTcsHis.append("    AND START_TIME = TO_DATE('" + tcshStartTime + "', 'yyyy-MM-dd hh24:mi:ss') ");
					int tcshis = db.executeUpdate(sbTcsHis.toString());
					if (tcshis <= 0) {
						//throw new ScormException("put to tcsh error!");
						System.out.println("更新学习历史记录失败 studentid=" + this.getUserId() + ",courseid=" + this.getOpenId());
					}
				}
			}
			// sbTcsHis.append("UPDATE TRAINING_COURSE_STUDENT_HIS ");
			// sbTcsHis.append(" SET END_TIME = SYSDATE, ");
			// sbTcsHis.append(" TOTAL_TIME =
			// SUBSTR(NUMTODSINTERVAL(TO_NUMBER(SYSDATE - ");
			// sbTcsHis.append(" (SELECT MAX(START_TIME) ");
			// sbTcsHis.append(" FROM TRAINING_COURSE_STUDENT_HIS ");
			// sbTcsHis.append(" WHERE FK_STU_ID = ");
			// sbTcsHis.append(" '" + this.getUserId() + "' ");
			// sbTcsHis.append(" AND COURSE_ID = ");
			// sbTcsHis.append(" '" + this.getOpenId() + "' ");
			// sbTcsHis.append(" GROUP BY FK_STU_ID, ");
			// sbTcsHis.append(" COURSE_ID)) * 24 * 60 * 60, ");
			// sbTcsHis.append(" 'second'), ");
			// sbTcsHis.append(" 12, ");
			// sbTcsHis.append(" 11) ");
			// sbTcsHis.append(" WHERE ID = (SELECT MAX(TO_NUMBER(ID)) ");
			// sbTcsHis.append(" FROM TRAINING_COURSE_STUDENT_HIS ");
			// sbTcsHis.append(" WHERE FK_STU_ID = '" + this.getUserId() + "'
			// ");
			// sbTcsHis.append(" AND COURSE_ID = '" + this.getOpenId() + "' ");
			// sbTcsHis.append(" GROUP BY FK_STU_ID, COURSE_ID) ");
			/* Lee end */
			ScormLog.setDebug(sql);
			int i = db.executeUpdate(sql);
			if (i < 1) {
				throw new ScormException("put to DB error!");
			}
		} catch (Exception e) {
			System.out.println("putScoData catch:>>>>>>>>>>" + sql + e.toString());
			throw new ScormException("putScoData error!" + e.toString());
		} finally {
			db = null;
		}
	}

	private void updateCourseCompleteStatus() throws ScormException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		MyResultSet rs1 = null;
		MyResultSet mrs = null;// Lee 2014年9月23日
		MyResultSet mrs2 = null;// 李文强 2014-11-04
		int allScos = 0;
		int completedScos = 0;
		String totalTime = "00:00:00";
		boolean isComplete = false;// 是否已完成标识
		try {
			// 查询该学生的课程的所有scos,将level为1的排除掉
			String sqlallscos = "select count(*) as allscos from scorm_course_item where  course_id='" + this.getCourseId()
					+ "' and type='sco' ";
			ScormLog.setDebug(sqlallscos);
			rs1 = db.executeQuery(sqlallscos);
			if (rs1 != null && rs1.next()) {
				allScos = rs1.getInt("allscos");
			} else
				return;
			if (allScos <= 0)
				return;
			// 状态为completed 或passed的都认为是完成的
			String sqlCompletedScos = "select count(*) as scos from scorm_stu_sco where (status='completed' or status='passed') and  student_id='"
					+ this.getUserId()
					+ "' "
					+ "and course_id='"
					+ this.getCourseId()
					+ "' and system_id in (select id from scorm_course_item where course_id='" + this.getCourseId() + "' and type='sco' )";
			ScormLog.setDebug(sqlallscos);
			rs = db.executeQuery(sqlCompletedScos);
			if (rs != null && rs.next()) {
				completedScos = rs.getInt("scos");
			} else
				return;
			double percent = (double) (completedScos * 10000 / allScos);
			String updateStatus = "update SCORM_STU_COURSE set COMPLETE_PERCENT = " + percent + "/100 where student_id='"
					+ this.getUserId() + "' " + "and course_id='" + this.getCourseId() + "'";

			ScormLog.setDebug(updateStatus);
			if (db.executeUpdate(updateStatus) < 1) {
				throw new ScormException("updateCourseCompleteStatus into DB error!sql:" + updateStatus);
			} else {
				String updateTrainingCourseStatus = "update training_course_student set PERCENT = " + percent + "/100 , LEARN_STATUS=";
				if (allScos == completedScos) {
					// 没有完成的课程才更新完成状态，避免多次学习造成最终完成时间的变动
					String checkSql = "select 1 from training_course_student " + "	where COMPLETE_DATE is not null "
							+ "		and LEARN_STATUS ='COMPLETED'" + "		and student_id='" + this.getUserId() + "' " + "		and course_id ='"
							+ this.getOpenId() + "'";
					if (db.countselect(checkSql) < 1) {
						updateTrainingCourseStatus += "'" + StudyProgress.COMPLETED + "', COMPLETE_DATE=sysdate";
					} else {
						isComplete = true; // 完成的课程做标识
					}
				} else {
					updateTrainingCourseStatus += "'" + StudyProgress.INCOMPLETE + "' ";
				}

				updateTrainingCourseStatus += " where student_id='" + this.getUserId() + "' " + "	and course_id ='" + this.getOpenId()
						+ "'";
				// 原代码恢复
				if (!isComplete) {// 没有完成过的才会去更新数据库
					ScormLog.setDebug(updateTrainingCourseStatus);
					if (db.executeUpdate(updateTrainingCourseStatus) < 1) {
						throw new ScormException("updateCourseCompleteStatus update training_course_student into DB error!sql:"
								+ updateTrainingCourseStatus);
					}
				} else {// Lee start 2014年9月23日 学习完成的免考课程 直接填写获得学时时间
					String examSql = "SELECT PBTC.ID FROM PE_BZZ_TCH_COURSE PBTC INNER JOIN ENUM_CONST EC ON PBTC.FLAG_IS_EXAM = EC.ID WHERE PBTC.ID = (SELECT FK_COURSE_ID FROM PR_BZZ_TCH_OPENCOURSE WHERE ID = '"
							+ this.getOpenId() + "') AND EC.CODE = '0'";
					mrs = db.executeQuery(examSql);
					if (null != mrs && mrs.next()) {
						String user_type_sql = "select ppr.name from sso_user su join pe_pri_role ppr on su.fk_role_id = ppr.id where su.id = '"
								+ this.getUserId() + "' and su.login_id != 'admin'";
						mrs2 = db.execuQuery(user_type_sql);
						String getTimeSql = "";
						if (mrs2.next()) {
							if (mrs2.getString("name").indexOf("协会管理员") >= 0) {
								getTimeSql = "UPDATE PR_BZZ_TCH_USER_ELECTIVE SET COMPLETED_TIME = SYSDATE,ISPASS='1' WHERE FK_USER_ID = '"
										+ this.getUserId() + "' AND FK_TCH_OPENCOURSE_ID = '" + this.getOpenId() + "'";
							} else {
								getTimeSql = "UPDATE PR_BZZ_TCH_STU_ELECTIVE SET COMPLETED_TIME = SYSDATE,ISPASS='1' WHERE FK_STU_ID = (SELECT ID FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '"
										+ this.getUserId() + "') AND FK_TCH_OPENCOURSE_ID = '" + this.getOpenId() + "'";
							}
						}
						if (db.executeUpdate(getTimeSql) <= 0) {
							System.out.println("putTime fail");
						}
					}
				}// Lee end
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ScormException("updateCourseCompleteStatus error!" + e.toString());
		} finally {
			db.close(rs1);
			db.close(mrs);// Lee 2014年9月23日
			db.close(mrs2);// 李文强 2014-11-11
			db.close(rs);
			db = null;
		}
	}

	// zlw
	private void updateCourseData() throws ScormException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		MyResultSet rs1 = null;
		MyResultSet rs2 = null;
		String id = null;
		String totalTime = "00:00:00.0";

		try {
			String time = this.getScoData().getCore().getSessionTime().getValue();
			int hour = Integer.parseInt(time.substring(0, time.indexOf(":", 0)));
			int min = Integer.parseInt(time.substring(time.indexOf(":", 0) + 1, time.indexOf(":", 0) + 3));
			String insertSql = "";
			// 查看是否有该学生的课程数据
			String sqlcourse = "select id from scorm_stu_course where student_id='" + this.getUserId() + "' " + "and course_id='"
					+ this.getCourseId() + "'";
			ScormLog.setDebug(sqlcourse);
			rs1 = db.executeQuery(sqlcourse);
			if (rs1 != null && rs1.next()) {
				id = rs1.getString("id");
			}

			// 得到该学生的总的学习时间

			String sqltime = "select total_time from scorm_stu_sco where student_id='" + this.getUserId() + "' " + "and course_id='"
					+ this.getCourseId() + "'";
			ScormLog.setDebug(sqltime);
			rs = db.executeQuery(sqltime);
			while (rs != null && rs.next()) {
				totalTime = ScormUtil.TimeAdd(totalTime, rs.getString("total_time"));
			}
			// System.out.println("sqltime:"+sqltime);
			if (totalTime == null) {
				totalTime = "00:00:00.1";
			}

			ScormLog.setDebug("update CourseData to DB!!!");
			// String sql="update SCORM_STU_COURSE set
			// LESSON_LOCATION='"+this.getSystemId()+"'," +
			// "TOTAL_TIME='"+totalTime+"',LAST_DATE=sysdate where id='"+id+"'";

			// 添加离线记录后,参数使用传进来的参数;lesson_location 使用传进来 的参数;
			// 添加判断，没有数据不跟新
			if (id != null && !"".equals(id)) {
				String sql = "update SCORM_STU_COURSE set LESSON_LOCATION='" + this.getSystemId() + "'," + "TOTAL_TIME='" + totalTime
						+ "',LAST_DATE=sysdate where id='" + id + "'";

				// if(hour<=5&&min>=15){
				// insertSql= "insert into time_course_stu
				// (id,STUDENT_ID,COURSEWARE_ID,FIRST_DATE,LAST_DATE,TOTAL_TIME,ATTEMPT_NUM,status)
				// " +
				// "values(to_char(s_scorm_id.nextval),'"+this.getUserId()+"','"
				// +
				// this.getCourseId() + "', ( select sysdate-
				// to_number(substr('"+time+"', 0, 2)) / 24-
				// to_number(substr('"+time+"', 4, 2)) / 24 / 60 -
				// to_number(substr('"+time+"', 7, 2)) / 24 / 60 / 60 from
				// dual),sysdate,'"+time.substring(0,time.length()-2)+"',1,'1')";
				// }else{
				// insertSql= "insert into time_course_stu
				// (id,STUDENT_ID,COURSEWARE_ID,FIRST_DATE,LAST_DATE,TOTAL_TIME,ATTEMPT_NUM,status)
				// " +
				// "values(to_char(s_scorm_id.nextval),'"+this.getUserId()+"','"
				// +
				// this.getCourseId() + "', ( select sysdate-
				// to_number(substr('"+time+"', 0, 2)) / 24-
				// to_number(substr('"+time+"', 4, 2)) / 24 / 60 -
				// to_number(substr('"+time+"', 7, 2)) / 24 / 60 / 60 from
				// dual),sysdate,'"+time.substring(0,time.length()-2)+"',1,'0')";
				// }
				// db.executeUpdate(insertSql);
				// ScormLog.setDebug(insertSql);
				ScormLog.setDebug(sql);
				if (db.executeUpdate(sql) < 1) {
					throw new ScormException("put new coursedata into DB error in updateCourseData!" + sql + ",user_id:" + this.getUserId()
							+ ",course_id:" + this.getCourseId());
				}
			}
		} catch (SQLException e) {
			throw new ScormException("updateCourseData error!" + e.toString());
		} catch (Exception e1) {
			throw new ScormException("updateCourseData error1!" + e1.toString());
		} finally {
			db.close(rs1);
			db.close(rs);
			db.close(rs2);
			db = null;
		}

	}
}
