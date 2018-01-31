package com.whaty.platform.database.oracle.standard.entity.activity;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.database.oracle.standard.sms.OracleSmsManagerPriv;
import com.whaty.platform.entity.activity.Elective;
import com.whaty.platform.entity.activity.ElectiveScore;
import com.whaty.platform.entity.activity.score.ElectiveScoreType;
import com.whaty.platform.entity.activity.score.ScoreDef;
import com.whaty.platform.sms.SmsFactory;
import com.whaty.platform.sms.SmsManage;
import com.whaty.platform.sms.SmsSendThread;
import com.whaty.platform.sms.basic.SmsSystemPoint;
import com.whaty.platform.util.BatchMethod;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleElectiveScore extends ElectiveScore {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.ElectiveScore#getScores(java.lang.String)
	 */
	public OracleElectiveScore() {
	}

	public ScoreDef getCoreScore(String scoreType) throws PlatformException {
		dbpool db = null;
		MyResultSet rs = null;
		String sql = null;
		ScoreDef score = null;
		try {
			db = new dbpool();
			if (scoreType.equals(ElectiveScoreType.USUAL)) {
				sql = "select usual_score,usual_scorelevel,usual_score_viewlevel,usual_scoredes,"
						+ "usual_score_zerodes ,usual_score_zerocaus "
						+ " from entity_elective where id='"
						+ this.getElective().getId() + "'";
				rs = db.executeQuery(sql);
				if (rs != null && rs.next()) {
					score.setTitle(ElectiveScoreType
							.typeShow(ElectiveScoreType.USUAL));
					score.setType(ElectiveScoreType.USUAL);
					score.setScore(rs.getFloat("normal_score"));
					score.setScoreDes(rs.getString("normal_scoredes"));
					score.setScoreLevel(rs.getString("normal_scorelevel"));
					if (rs.getInt("usual_score_viewlevel") == 1) {
						score.setLevelScore(true);
					} else {
						score.setLevelScore(false);
					}
					score.setZeroCause(rs.getString("normal_score_zerocause"));
					score.setZeroDes(rs.getString("normal_score_zerodes"));
				}
			} else if (scoreType.equals(ElectiveScoreType.EXAM)) {
				sql = "select exam_score,exam_scorelevel,exam_score_viewlevel"
						+ "exam_scoredes,exam_score_zerodes,exam_score_zerocause "
						+ " from entity_elective where id='"
						+ this.getElective().getId() + "'";
				rs = db.executeQuery(sql);
				if (rs != null && rs.next()) {
					score.setTitle(ElectiveScoreType
							.typeShow(ElectiveScoreType.EXAM));
					score.setType(ElectiveScoreType.EXAM);
					score.setScore(rs.getFloat("exam_score"));
					score.setScoreDes(rs.getString("exam_scoredes"));
					score.setScoreLevel(rs.getString("exam_scorelevel"));
					if (rs.getInt("exam_score_viewlevel") == 1) {
						score.setLevelScore(true);
					} else {
						score.setLevelScore(false);
					}
					score.setZeroCause(rs.getString("exam_score_zerocause"));
					score.setZeroDes(rs.getString("exam_score_zerodes"));
				}
			} else if (scoreType.equals(ElectiveScoreType.EXPERIMENT)) {
				sql = "select experiment_score,experiment_scorelevel,experiment_score_viewlevel"
						+ "experiment_scoredes,experiment_score_zerodes,experiment_score_zerocause "
						+ " from entity_elective where id='"
						+ this.getElective().getId() + "'";
				rs = db.executeQuery(sql);
				if (rs != null && rs.next()) {
					score.setTitle(ElectiveScoreType
							.typeShow(ElectiveScoreType.EXPERIMENT));
					score.setType(ElectiveScoreType.EXPERIMENT);
					score.setScore(rs.getFloat("experiment_score"));
					score.setScoreDes(rs.getString("experiment_scoredes"));
					score.setScoreLevel(rs.getString("experiment_scorelevel"));
					if (rs.getInt("experiment_score_viewlevel") == 1) {
						score.setLevelScore(true);
					} else {
						score.setLevelScore(false);
					}
					score.setZeroCause(rs
							.getString("experiment_score_zerocause"));
					score.setZeroDes(rs.getString("experiment_score_zerodes"));
				}
			}

			else if (scoreType.equals(ElectiveScoreType.TOTAL)) {
				sql = "select total_score,total_scorelevel,total_score_viewlevel,total_scoredes,total_score_zerodes,"
						+ "total_score_zerocause "
						+ " from entity_elective where id='"
						+ this.getElective().getId() + "'";
				rs = db.executeQuery(sql);
				if (rs != null && rs.next()) {
					score.setTitle(ElectiveScoreType
							.typeShow(ElectiveScoreType.TOTAL));
					score.setType(ElectiveScoreType.TOTAL);
					score.setScore(rs.getFloat("total_score"));
					score.setScoreDes(rs.getString("total_scoredes"));
					score.setScoreLevel(rs.getString("total_scorelevel"));
					if (rs.getInt("total_score_viewlevel") == 1) {
						score.setLevelScore(true);
					} else {
						score.setLevelScore(false);
					}
					score.setZeroCause(rs.getString("total_score_zerocause"));
					score.setZeroDes(rs.getString("total_score_zerodes"));
				}
			} else if (scoreType.equals(ElectiveScoreType.EXPEND)) {
				sql = "select expend_score,"
						+ "expend_scorelevel,expend_score_viewlevel,expend_scoredes,expend_score_zerodes,"
						+ "expend_score_zerocause "
						+ " from entity_elective where id='"
						+ this.getElective().getId() + "'";
				rs = db.executeQuery(sql);
				if (rs != null && rs.next()) {
					score.setTitle(ElectiveScoreType
							.typeShow(ElectiveScoreType.EXPEND));
					score.setType(ElectiveScoreType.EXPEND);
					score.setScore(rs.getFloat("expand_score"));
					score.setScoreDes(rs.getString("expand_scoredes"));
					score.setScoreLevel(rs.getString("expand_scorelevel"));
					if (rs.getInt("expend_score_viewlevel") == 1) {
						score.setLevelScore(true);
					} else {
						score.setLevelScore(false);
					}
					score.setZeroCause(rs.getString("expand_score_zerocause"));
					score.setZeroDes(rs.getString("expand_score_zerodes"));
				}
			} else if (scoreType.equals(ElectiveScoreType.RENEW)) {
				sql = "select renew_score,renew_scorelevel,renew_score_viewlevel,"
						+ "renew_scoredes,renew_score_zerodes,renew_score_zerocause "
						+ " from entity_elective where id='"
						+ this.getElective().getId() + "'";
				rs = db.executeQuery(sql);
				if (rs != null && rs.next()) {
					score.setTitle(ElectiveScoreType
							.typeShow(ElectiveScoreType.RENEW));
					score.setType(ElectiveScoreType.RENEW);
					score.setScore(rs.getFloat("renew_score"));
					score.setScoreDes(rs.getString("renew_scoredes"));
					score.setScoreLevel(rs.getString("renew_scorelevel"));
					if (rs.getInt("renew_score_viewlevel") == 1) {
						score.setLevelScore(true);
					} else {
						score.setLevelScore(false);
					}
					score.setZeroCause(rs.getString("renew_score_zerocause"));
					score.setZeroDes(rs.getString("renew_score_zerodes"));
				}
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
			return score;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.ElectiveScore#getCoreScores()
	 */
	public List getCoreScores() throws PlatformException {
		dbpool db = null;
		MyResultSet rs = null;
		String sql = "select total_score,total_scorelevel,total_score_viewlevel,total_scoredes,total_score_zerodes,"
				+ "total_score_zerocause,usual_score,usual_scorelevel,usual_score_viewlevel,usual_scoredes,"
				+ "usual_score_zerodes ,usual_score_zerocause,exam_score,exam_scorelevel,exam_score_viewlevel,"
				+ "exam_scoredes,exam_score_zerodes,exam_score_zerocause,expend_score,"
				+ "expend_scorelevel,expend_score_viewlevel,expend_scoredes,expend_score_zerodes,"
				+ "expend_score_zerocause,renew_score,renew_scorelevel,renew_score_viewlevel,"
				+ "renew_scoredes,renew_score_zerodes,renew_score_zerocause "
				+ " from entity_elective where id='"
				+ this.getElective().getId() + "'";
		List scoreList = null;
		int viewLevel = 0;
		try {
			db = new dbpool();
			scoreList = new ArrayList();
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				ScoreDef normalScore = new ScoreDef();
				normalScore.setTitle(ElectiveScoreType
						.typeShow(ElectiveScoreType.USUAL));
				normalScore.setType(ElectiveScoreType.USUAL);
				normalScore.setScore(rs.getFloat("normal_score"));
				viewLevel = rs.getInt("normal_score_viewlevel");
				if (viewLevel == 1)
					normalScore.setLevelScore(true);
				else
					normalScore.setLevelScore(false);
				normalScore.setScoreLevel(rs.getString("normal_scorelevel"));
				normalScore
						.setZeroCause(rs.getString("normal_score_zerocause"));
				scoreList.add(normalScore);

				ScoreDef examScore = new ScoreDef();
				examScore.setTitle(ElectiveScoreType
						.typeShow(ElectiveScoreType.EXAM));
				examScore.setType(ElectiveScoreType.EXAM);
				examScore.setScore(rs.getFloat("exam_score"));
				examScore.setScoreLevel(rs.getString("exam_scorelevel"));
				viewLevel = rs.getInt("exam_score_viewlevel");
				if (viewLevel == 1)
					examScore.setLevelScore(true);
				else
					examScore.setLevelScore(false);
				examScore.setZeroCause(rs.getString("exam_score_zerocause"));
				scoreList.add(normalScore);

				ScoreDef experimentScore = new ScoreDef();
				experimentScore.setTitle(ElectiveScoreType
						.typeShow(ElectiveScoreType.EXPERIMENT));
				experimentScore.setType(ElectiveScoreType.EXPERIMENT);
				experimentScore.setScore(rs.getFloat("experiment_score"));
				experimentScore.setScoreLevel(rs
						.getString("experiment_scorelevel"));
				viewLevel = rs.getInt("experiment_score_viewlevel");
				if (viewLevel == 1)
					experimentScore.setLevelScore(true);
				else
					experimentScore.setLevelScore(false);
				experimentScore.setZeroCause(rs
						.getString("experiment_score_zerocause"));
				scoreList.add(experimentScore);

				ScoreDef totalScore = new ScoreDef();
				totalScore.setTitle(ElectiveScoreType
						.typeShow(ElectiveScoreType.TOTAL));
				totalScore.setType(ElectiveScoreType.TOTAL);
				totalScore.setScore(rs.getFloat("total_score"));
				totalScore.setScoreLevel(rs.getString("total_scorelevel"));
				viewLevel = rs.getInt("total_score_viewlevel");
				if (viewLevel == 1)
					totalScore.setLevelScore(true);
				else
					totalScore.setLevelScore(false);
				totalScore.setZeroCause(rs.getString("total_score_zerocause"));
				scoreList.add(normalScore);

				ScoreDef expendScore = new ScoreDef();
				expendScore.setTitle(ElectiveScoreType
						.typeShow(ElectiveScoreType.EXPEND));
				expendScore.setType(ElectiveScoreType.EXPEND);
				expendScore.setScore(rs.getFloat("expend_score"));
				expendScore.setScoreLevel(rs.getString("expend_scorelevel"));
				viewLevel = rs.getInt("expend_score_viewlevel");
				if (viewLevel == 1)
					expendScore.setLevelScore(true);
				else
					expendScore.setLevelScore(false);
				expendScore
						.setZeroCause(rs.getString("expend_score_zerocause"));
				scoreList.add(normalScore);

				ScoreDef renewScore = new ScoreDef();
				renewScore.setTitle(ElectiveScoreType
						.typeShow(ElectiveScoreType.RENEW));
				renewScore.setType(ElectiveScoreType.RENEW);
				renewScore.setScore(rs.getFloat("renew_score"));
				renewScore.setScoreLevel(rs.getString("renew_scorelevel"));
				viewLevel = rs.getInt("renew_score_viewlevel");
				if (viewLevel == 1)
					renewScore.setLevelScore(true);
				else
					renewScore.setLevelScore(false);
				renewScore.setZeroCause(rs.getString("renew_score_zerocause"));
				scoreList.add(normalScore);

			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
			return scoreList;
		}
	}

	public Elective getCoreScores(String elective_id) throws PlatformException {
		dbpool db = null;
		MyResultSet rs = null;
		String sql = "select total_score,total_scorelevel,total_score_viewlevel,total_scoredes,total_score_zerodes,"
				+ "total_score_zerocause,usual_score,usual_scorelevel,usual_score_viewlevel,usual_scoredes,"
				+ "usual_score_zerodes ,usual_score_zerocause,exam_score,exam_scorelevel,exam_score_viewlevel,"
				+ "exam_scoredes,exam_score_zerodes,exam_score_zerocause,expend_score,"
				+ "expend_scorelevel,expend_score_viewlevel,expend_scoredes,expend_score_zerodes,"
				+ "expend_score_zerocause,renew_score,renew_scorelevel,renew_score_viewlevel,"
				+ "renew_scoredes,renew_score_zerodes,renew_score_zerocause,experiment_score,expend_score,student_id,teachclass_id,total_score_rule,total_score_modify_rule,total_expend_score,total_expend_score_rule "
				+ " from entity_elective where id='" + elective_id + "'";

		List scoreList = null;
		int viewLevel = 0;
		OracleElective elect = new OracleElective();
		try {
			db = new dbpool();

			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				elect.setUsualScore(rs.getString("usual_score"));
				elect.setExamScore(rs.getString("exam_score"));
				elect.setTotalScore(rs.getString("total_score"));
				elect.setExperimentScore(rs.getString("experiment_score"));
				elect.setExpendScore(rs.getString("expend_score"));
				elect.setTotal_score_rule(rs.getString("total_score_rule"));
				elect.setTotal_score_modify_rule(rs
						.getString("total_score_modify_rule"));
				elect.setTotal_expend_score(rs.getString("total_expend_score"));
				elect.setTotal_expend_score_rule(rs
						.getString("total_expend_score_rule"));
				OracleStudent stu = new OracleStudent(rs
						.getString("student_id"));
				elect.setStudent(stu);
				OracleTeachClass teachclass = new OracleTeachClass(rs
						.getString("teachclass_id"));
				elect.setTeachClass(teachclass);

			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
			return elect;
		}
	}

	/*
	 * @param student_id @param teachclass_id return testscore
	 */

	public String getTestScore(String student_id, String teachclass_id)
			throws PlatformException {
		dbpool db = null;
		MyResultSet rs = null;
		String sql = "";
		String testscore = "0";
		sql = "select nvl(av,0) as testscore from "
				+ " (select (score*100/totalscore) as  av,testpaper_id from "
				+ "(select testpaper_id, sum(score) as score, totalscore from  "
				+ "(select m.id as id,m.user_id as user_id,m.testpaper_id as testpaper_id,m.score as score,n.score as totalscore from "
				+ "(select  distinct a.id as id,a.user_id as user_id,a.testpaper_id as testpaper_id ,a.score as score from "
				+ "test_testpaper_history a,test_testpaper_info b,test_paperquestion_info c where a.t_user_id = '"
				+ student_id
				+ "' and "
				+ " a.testpaper_id=b.id and b.group_id='"
				+ teachclass_id
				+ "' and c.testpaper_id=a.testpaper_id ) m , "
				+ "(select sum(score) as score,testpaper_id  from test_paperquestion_info  group by testpaper_id) n "
				+ "where m.testpaper_id=n.testpaper_id(+))  group by testpaper_id,totalscore))";
		try {
			db = new dbpool();
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				testscore = rs.getString("testscore");
				if (!testscore.trim().equals("0"))
					testscore = rs.getString("testscore");

			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
			return testscore;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.ElectiveScore#updateCoreScore(com.whaty.platform.entity.activity.score.ScoreDef,
	 *      java.lang.String)
	 */
	public void updateCoreScore(ScoreDef newScore) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		int viewLevel = 0;
		String scoreType = newScore.getTitle();
		if (newScore.getIsLevelScore()) {
			viewLevel = 1;
		}
		if (scoreType.equals(ElectiveScoreType.USUAL)) {
			sql = "update entity_elective set usual_score="
					+ newScore.getScore() + "," + "usual_scorelevel='"
					+ newScore.getScoreLevel() + "'," + "usual_score_viewleve="
					+ viewLevel + "," + "usual_scoredes='"
					+ newScore.getScoreDes() + "'," + "usual_score_zerodes='"
					+ newScore.getZeroDes() + "'," + "usual_score_zerocaus='"
					+ newScore.getZeroCause() + "' " + "where id='"
					+ this.getElective().getId();
		} else if (scoreType.equals(ElectiveScoreType.EXAM)) {
			sql = "update entity_elective set exam_score="
					+ newScore.getScore() + "," + "exam_scorelevel='"
					+ newScore.getScoreLevel() + "'," + "exam_score_viewleve="
					+ viewLevel + "," + "exam_scoredes='"
					+ newScore.getScoreDes() + "'," + "exam_score_zerodes='"
					+ newScore.getZeroDes() + "'," + "exam_score_zerocaus='"
					+ newScore.getZeroCause() + "' " + "where id='"
					+ this.getElective().getId();

		} else if (scoreType.equals(ElectiveScoreType.EXPERIMENT)) {
			sql = "update entity_elective set experiment_score="
					+ newScore.getScore() + "," + "experiment_scorelevel='"
					+ newScore.getScoreLevel() + "',"
					+ "experiment_score_viewleve=" + viewLevel + ","
					+ "experiment_scoredes='" + newScore.getScoreDes() + "',"
					+ "experiment_score_zerodes='" + newScore.getZeroDes()
					+ "'," + "experiment_score_zerocaus='"
					+ newScore.getZeroCause() + "' " + "where id='"
					+ this.getElective().getId();

		} else if (scoreType.equals(ElectiveScoreType.TOTAL)) {
			sql = "update entity_elective set total_score="
					+ newScore.getScore() + "," + "total_scorelevel='"
					+ newScore.getScoreLevel() + "'," + "total_score_viewleve="
					+ viewLevel + "," + "total_scoredes='"
					+ newScore.getScoreDes() + "'," + "total_score_zerodes='"
					+ newScore.getZeroDes() + "'," + "total_score_zerocaus='"
					+ newScore.getZeroCause() + "' " + "where id='"
					+ this.getElective().getId();

		} else if (scoreType.equals(ElectiveScoreType.EXPEND)) {
			sql = "update entity_elective set expend_score="
					+ newScore.getScore() + "," + "expend_scorelevel='"
					+ newScore.getScoreLevel() + "',"
					+ "expend_score_viewleve=" + viewLevel + ","
					+ "expend_scoredes='" + newScore.getScoreDes() + "',"
					+ "expend_score_zerodes='" + newScore.getZeroDes() + "',"
					+ "expend_score_zerocaus='" + newScore.getZeroCause()
					+ "' " + "where id='" + this.getElective().getId();

		} else if (scoreType.equals(ElectiveScoreType.RENEW)) {

			sql = "update entity_elective set renew_score="
					+ newScore.getScore() + "," + "renew_scorelevel='"
					+ newScore.getScoreLevel() + "'," + "renew_scorelevel='"
					+ newScore.getScoreLevel() + "'," + "renew_scoredes='"
					+ newScore.getScoreDes() + "'," + "renew_score_zerodes='"
					+ newScore.getZeroDes() + "'," + "renew_score_zerocaus='"
					+ newScore.getZeroCause() + "' " + "where id='"
					+ this.getElective().getId();
		}
		int i = db.executeUpdate(sql);
		UserUpdateLog
				.setDebug("OracleElectiveScore.updateCoreScore(ScoreDef newScore) SQL="
						+ sql + " COUNT=" + i + " DATE=" + new Date());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.ElectiveScore#updateCoreScores(java.util.Map)
	 */
	public void updateCoreScores(List newScoreList) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_elective set ";
		String scoreType = null;
		ScoreDef newScore = null;
		boolean isFirst = true;
		boolean hasNext = false;
		for (int i = 0; i < newScoreList.size(); i++) {
			newScore = (ScoreDef) newScoreList.get(i);
			scoreType = newScore.getType();
			int viewLevel = 0;
			if (newScore.getIsLevelScore()) {
				viewLevel = 1;
			}
			if (scoreType.equals(ElectiveScoreType.USUAL)) {
				sql = sql + " usual_score=" + newScore.getScore() + ","
						+ "usual_scorelevel='" + newScore.getScoreLevel()
						+ "'," + "usual_score_viewlevel=" + viewLevel + ","
						+ "usual_scoredes='" + newScore.getScoreDes() + "',"
						+ "usual_score_zerodes='" + newScore.getZeroDes()
						+ "'," + "usual_score_zerocause='"
						+ newScore.getZeroCause() + "'";
				isFirst = false;
				hasNext = true;
			} else if (scoreType.equals(ElectiveScoreType.EXAM)) {
				if (isFirst) {
					sql = sql + " exam_score=" + newScore.getScore() + ","
							+ "exam_scorelevel='" + newScore.getScoreLevel()
							+ "'," + "exam_score_viewlevel=" + viewLevel + ","
							+ "exam_scoredes='" + newScore.getScoreDes() + "',"
							+ "exam_score_zerodes='" + newScore.getZeroDes()
							+ "'," + "exam_score_zerocause='"
							+ newScore.getZeroCause() + "'";
					isFirst = false;
					hasNext = true;
				} else {
					sql = sql + ", exam_score=" + newScore.getScore() + ","
							+ "exam_scorelevel='" + newScore.getScoreLevel()
							+ "'," + "exam_score_viewlevel=" + viewLevel + ","
							+ "exam_scoredes='" + newScore.getScoreDes() + "',"
							+ "exam_score_zerodes='" + newScore.getZeroDes()
							+ "'," + "exam_score_zerocause='"
							+ newScore.getZeroCause() + "'";
					hasNext = true;
				}
			} else if (scoreType.equals(ElectiveScoreType.EXPERIMENT)) {
				if (isFirst) {
					sql = sql + " experiment_score=" + newScore.getScore()
							+ "," + "experiment_scorelevel='"
							+ newScore.getScoreLevel() + "',"
							+ "experiment_score_viewlevel=" + viewLevel + ","
							+ "experiment_scoredes='" + newScore.getScoreDes()
							+ "'," + "experiment_score_zerodes='"
							+ newScore.getZeroDes() + "',"
							+ "experiment_score_zerocause='"
							+ newScore.getZeroCause() + "'";
					isFirst = false;
					hasNext = true;
				} else {
					sql = sql + ", experiment_score=" + newScore.getScore()
							+ "," + "experiment_scorelevel='"
							+ newScore.getScoreLevel() + "',"
							+ "experiment_score_viewlevel=" + viewLevel + ","
							+ "experiment_scoredes='" + newScore.getScoreDes()
							+ "'," + "experiment_score_zerodes='"
							+ newScore.getZeroDes() + "',"
							+ "experiment_score_zerocause='"
							+ newScore.getZeroCause() + "'";
					hasNext = true;
				}
			}

			else if (scoreType.equals(ElectiveScoreType.TOTAL)) {
				if (isFirst) {
					sql = sql + " total_score=" + newScore.getScore() + ","
							+ "total_scorelevel='" + newScore.getScoreLevel()
							+ "'," + "total_score_viewlevel=" + viewLevel + ","
							+ "total_scoredes='" + newScore.getScoreDes()
							+ "'," + "total_score_zerodes='"
							+ newScore.getZeroDes() + "',"
							+ "total_score_zerocause='"
							+ newScore.getZeroCause() + "'";
					isFirst = false;
					hasNext = true;
				} else {
					sql = sql + ", total_score=" + newScore.getScore() + ","
							+ "total_scorelevel='" + newScore.getScoreLevel()
							+ "'," + "total_score_viewlevel=" + viewLevel + ","
							+ "total_scoredes='" + newScore.getScoreDes()
							+ "'," + "total_score_zerodes='"
							+ newScore.getZeroDes() + "',"
							+ "total_score_zerocause='"
							+ newScore.getZeroCause() + "'";
					hasNext = true;
				}
			} else if (scoreType.equals(ElectiveScoreType.EXPEND)) {
				if (isFirst) {
					sql = sql + " expend_score=" + newScore.getScore() + ","
							+ "expend_scorelevel='" + newScore.getScoreLevel()
							+ "'," + "expend_score_viewlevel=" + viewLevel
							+ "," + "expend_scoredes='"
							+ newScore.getScoreDes() + "',"
							+ "expend_score_zerodes='" + newScore.getZeroDes()
							+ "'," + "expend_score_zerocause='"
							+ newScore.getZeroCause() + "'";
					isFirst = false;
					hasNext = true;
				} else {
					sql = sql + ", expend_score=" + newScore.getScore() + ","
							+ "expend_scorelevel='" + newScore.getScoreLevel()
							+ "'," + "expend_score_viewlevel=" + viewLevel
							+ "," + "expend_scoredes='"
							+ newScore.getScoreDes() + "',"
							+ "expend_score_zerodes='" + newScore.getZeroDes()
							+ "'," + "expend_score_zerocause='"
							+ newScore.getZeroCause() + "'";
					hasNext = true;
				}
			} else if (scoreType.equals(ElectiveScoreType.RENEW)) {
				if (isFirst) {
					sql = sql + " renew_score=" + newScore.getScore() + ","
							+ "renew_scorelevel='" + newScore.getScoreLevel()
							+ "'," + "renew_score_viewlevel=" + viewLevel + ","
							+ "renew_scoredes='" + newScore.getScoreDes()
							+ "'," + "renew_score_zerodes='"
							+ newScore.getZeroDes() + "',"
							+ "renew_score_zerocause='"
							+ newScore.getZeroCause() + "'";
					isFirst = false;
					hasNext = true;
				} else {
					sql = sql + ", renew_score=" + newScore.getScore() + ","
							+ "renew_scorelevel='" + newScore.getScoreLevel()
							+ "'," + "renew_scoredes='"
							+ newScore.getScoreDes() + "',"
							+ "renew_score_viewlevel=" + viewLevel + ","
							+ "renew_score_zerodes='" + newScore.getZeroDes()
							+ "'," + "renew_score_zerocause='"
							+ newScore.getZeroCause() + "'";
					hasNext = true;
				}
			}

		}
		sql = sql + " where id='" + this.getElective().getId() + "'";

		if (hasNext) {
			int i = db.executeUpdate(sql);
			UserUpdateLog
					.setDebug("OracleElectiveScore.updateCoreScores(List newScoreList) SQL="
							+ sql + " COUNT=" + i + " DATE=" + new Date());
		}

	}

	public void updateCoreScoresById(List newScoreList, String elective_id)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_elective set ";
		String scoreType = null;
		ScoreDef newScore = null;
		boolean isFirst = true;
		boolean hasNext = false;
		for (int i = 0; i < newScoreList.size(); i++) {
			newScore = (ScoreDef) newScoreList.get(i);
			scoreType = newScore.getType();
			int viewLevel = 0;
			if (newScore.getIsLevelScore()) {
				viewLevel = 1;
			}
			if (scoreType.equals(ElectiveScoreType.USUAL)) {
				sql = sql + " usual_score=" + newScore.getScore() + ","
						+ "usual_scorelevel='" + newScore.getScoreLevel()
						+ "'," + "usual_score_viewlevel=" + viewLevel + ","
						+ "usual_scoredes='" + newScore.getScoreDes() + "',"
						+ "usual_score_zerodes='" + newScore.getZeroDes()
						+ "'," + "usual_score_zerocause='"
						+ newScore.getZeroCause() + "'";
				isFirst = false;
				hasNext = true;
			} else if (scoreType.equals(ElectiveScoreType.EXAM)) {
				if (isFirst) {
					sql = sql + " exam_score=" + newScore.getScore() + ","
							+ "exam_scorelevel='" + newScore.getScoreLevel()
							+ "'," + "exam_score_viewlevel=" + viewLevel + ","
							+ "exam_scoredes='" + newScore.getScoreDes() + "',"
							+ "exam_score_zerodes='" + newScore.getZeroDes()
							+ "'," + "exam_score_zerocause='"
							+ newScore.getZeroCause() + "'";
					isFirst = false;
					hasNext = true;
				} else {
					sql = sql + ", exam_score=" + newScore.getScore() + ","
							+ "exam_scorelevel='" + newScore.getScoreLevel()
							+ "'," + "exam_score_viewlevel=" + viewLevel + ","
							+ "exam_scoredes='" + newScore.getScoreDes() + "',"
							+ "exam_score_zerodes='" + newScore.getZeroDes()
							+ "'," + "exam_score_zerocause='"
							+ newScore.getZeroCause() + "'";
					hasNext = true;
				}
			} else if (scoreType.equals(ElectiveScoreType.EXPERIMENT)) {
				if (isFirst) {
					sql = sql + " experiment_score=" + newScore.getScore()
							+ "," + "experiment_scorelevel='"
							+ newScore.getScoreLevel() + "',"
							+ "experiment_score_viewlevel=" + viewLevel + ","
							+ "experiment_scoredes='" + newScore.getScoreDes()
							+ "'," + "experiment_score_zerodes='"
							+ newScore.getZeroDes() + "',"
							+ "experiment_score_zerocause='"
							+ newScore.getZeroCause() + "'";
					isFirst = false;
					hasNext = true;
				} else {
					sql = sql + ", experiment_score=" + newScore.getScore()
							+ "," + "experiment_scorelevel='"
							+ newScore.getScoreLevel() + "',"
							+ "experiment_score_viewlevel=" + viewLevel + ","
							+ "experiment_scoredes='" + newScore.getScoreDes()
							+ "'," + "experiment_score_zerodes='"
							+ newScore.getZeroDes() + "',"
							+ "experiment_score_zerocause='"
							+ newScore.getZeroCause() + "'";
					hasNext = true;
				}
			} else if (scoreType.equals(ElectiveScoreType.TOTAL)) {
				if (isFirst) {
					sql = sql + " total_score=" + newScore.getScore() + ","
							+ "total_scorelevel='" + newScore.getScoreLevel()
							+ "'," + "total_score_viewlevel=" + viewLevel + ","
							+ "total_scoredes='" + newScore.getScoreDes()
							+ "'," + "total_score_zerodes='"
							+ newScore.getZeroDes() + "',"
							+ "total_score_zerocause='"
							+ newScore.getZeroCause() + "'";
					isFirst = false;
					hasNext = true;
				} else {
					sql = sql + ", total_score=" + newScore.getScore() + ","
							+ "total_scorelevel='" + newScore.getScoreLevel()
							+ "'," + "total_score_viewlevel=" + viewLevel + ","
							+ "total_scoredes='" + newScore.getScoreDes()
							+ "'," + "total_score_zerodes='"
							+ newScore.getZeroDes() + "',"
							+ "total_score_zerocause='"
							+ newScore.getZeroCause() + "'";
					hasNext = true;
				}
			} else if (scoreType.equals(ElectiveScoreType.EXPEND)) {
				if (isFirst) {
					sql = sql + " expend_score=" + newScore.getScore() + ","
							+ "expend_scorelevel='" + newScore.getScoreLevel()
							+ "'," + "expend_score_viewlevel=" + viewLevel
							+ "," + "expend_scoredes='"
							+ newScore.getScoreDes() + "',"
							+ "expend_score_zerodes='" + newScore.getZeroDes()
							+ "'," + "expend_score_zerocause='"
							+ newScore.getZeroCause() + "'";
					isFirst = false;
					hasNext = true;
				} else {
					sql = sql + ", expend_score=" + newScore.getScore() + ","
							+ "expend_scorelevel='" + newScore.getScoreLevel()
							+ "'," + "expend_score_viewlevel=" + viewLevel
							+ "," + "expend_scoredes='"
							+ newScore.getScoreDes() + "',"
							+ "expend_score_zerodes='" + newScore.getZeroDes()
							+ "'," + "expend_score_zerocause='"
							+ newScore.getZeroCause() + "'";
					hasNext = true;
				}
			} else if (scoreType.equals(ElectiveScoreType.RENEW)) {
				if (isFirst) {
					sql = sql + " renew_score=" + newScore.getScore() + ","
							+ "renew_scorelevel='" + newScore.getScoreLevel()
							+ "'," + "renew_score_viewlevel=" + viewLevel + ","
							+ "renew_scoredes='" + newScore.getScoreDes()
							+ "'," + "renew_score_zerodes='"
							+ newScore.getZeroDes() + "',"
							+ "renew_score_zerocause='"
							+ newScore.getZeroCause() + "'";
					isFirst = false;
					hasNext = true;
				} else {
					sql = sql + ", renew_score=" + newScore.getScore() + ","
							+ "renew_scorelevel='" + newScore.getScoreLevel()
							+ "'," + "renew_scoredes='"
							+ newScore.getScoreDes() + "',"
							+ "renew_score_viewlevel=" + viewLevel + ","
							+ "renew_score_zerodes='" + newScore.getZeroDes()
							+ "'," + "renew_score_zerocause='"
							+ newScore.getZeroCause() + "'";
					hasNext = true;
				}
			} else if (scoreType.equals(ElectiveScoreType.SELFTEST)) {
				sql = sql + " test_score=" + newScore.getScore();
				hasNext = true;
			} else if (scoreType.equals(ElectiveScoreType.TOTAL_EXPEND_SCORE)) {
				sql = sql + " total_expend_score=" + newScore.getScore();
				hasNext = true;
			}

		}
		sql = sql + " where id='" + elective_id + "'";

		if (hasNext) {
			int i = db.executeUpdate(sql);
			UserUpdateLog
					.setDebug("OracleElectiveScore.updateCoreScoresById(List newScoreList, String elective_id) SQL="
							+ sql + " COUNT=" + i + " DATE=" + new Date());
		}

	}

	public int updateScoreBatch(List dataList, List searchProperties)
			throws PlatformException {
		BatchMethod batch = new BatchMethod();
		List sql_group = batch.updateBatch(dataList, searchProperties);
		dbpool db = new dbpool();
		return db.executeUpdateBatch(sql_group);
	}

	public void updateScoreBatch(List dataList, List idList, List reg_noList,
			List nameList, String open_course_id, boolean checkExpend)
			throws PlatformException {
		dbpool db = new dbpool();
		String tableName = "";
		String sql = "";
		String sql_data = "";
		String sql_check = "";
		String field = "";
		boolean isExpendScore = true;
		ArrayList exceptionList = new ArrayList();
		String except = "";
		int count = 0;
		if (idList == null) {
			for (int i = 0; i < dataList.size(); i++) {
				if (checkExpend) {
					sql_check = "select id from entity_elective where student_id = (select id from entity_student_info where reg_no = '"
							+ reg_noList.get(i)
							+ "') and SUBSTR(score_status,8,1) = '1'";
					if (db.countselect(sql_check) < 1)
						isExpendScore = false;
				}
				HashMap data = (HashMap) dataList.get(i);
				Set dataset = data.keySet();
				for (Iterator dataiter = dataset.iterator(); dataiter.hasNext();) {
					field = (String) dataiter.next();
					if (field.equals("tableName")) {
						tableName = (String) data.get(field);
						continue;
					} else if (field.equals("expend_score") && !isExpendScore) {
						sql_data = sql_data + "expend_score='"
								+ (String) data.get(field) + "',";
						continue;
					}

					sql_data = field + "='" + (String) data.get(field) + "',";
				}
				sql = "update "
						+ tableName
						+ " set "
						+ sql_data.substring(0, sql_data.length() - 1)
						+ " where student_id = (select id from entity_student_info where reg_no = '"
						+ reg_noList.get(i)
						+ "' and status = '0' and isgraduated= '0' and name='"
						+ nameList.get(i)
						+ "') and teachclass_id =(select id from entity_teach_class where open_course_id = '"
						+ open_course_id + "')";
				UserUpdateLog
						.setDebug("OracleElectiveScore.updateScoreBatch(List dataList, List idList, List reg_noList,List nameList, String open_course_id, boolean checkExpend) SQL="
								+ sql + " DATE=" + new Date());
				if (db.executeUpdate(sql) < 1) {
					exceptionList.add("(" + reg_noList.get(i) + ")"
							+ nameList.get(i) + "¼��ɼ�����");
				} else
					count++;
			}
		} else {
			for (int i = 0; i < dataList.size(); i++) {
				if (checkExpend) {
					sql_check = "select id from entity_elective where id = '"
							+ idList.get(i)
							+ "' and SUBSTR(score_status,8,1) = '1'";
					if (db.countselect(sql_check) < 1)
						isExpendScore = false;
				}
				HashMap data = (HashMap) dataList.get(i);
				Set dataset = data.keySet();
				for (Iterator dataiter = dataset.iterator(); dataiter.hasNext();) {
					field = (String) dataiter.next();
					if (field.equals("tableName")) {
						tableName = (String) data.get(field);
						continue;
					} else if (field.equals("expend_score") && !isExpendScore) {
						sql_data = sql_data + "expend_score='"
								+ (String) data.get(field) + "',";
						continue;
					}
					sql_data = sql_data + field + "='"
							+ (String) data.get(field) + "',";
				}

				sql = "update " + tableName + " set "
						+ sql_data.substring(0, sql_data.length() - 1)
						+ " where id = '" + idList.get(i) + "'";
				UserUpdateLog
						.setDebug("OracleElectiveScore.updateScoreBatch(List dataList, List idList, List reg_noList,List nameList, String open_course_id, boolean checkExpend) SQL="
								+ sql + " DATE=" + new Date());
				if (db.executeUpdate(sql) < 1) {
					if (nameList != null) {
						exceptionList.add("(" + reg_noList.get(i) + ")"
								+ nameList.get(i) + "¼��ɼ�����");
					} else {
						exceptionList.add(reg_noList.get(i) + "¼��ɼ�����");
					}
				} else
					count++;
			}
		}
		db = null;
		if (exceptionList.size() > 0) {
			exceptionList.add("���֤����¼�� ");
			for (int i = 0; i < exceptionList.size(); i++) {
				except += (String) exceptionList.get(i) + "\\n";
			}
			if (count > 0)
				except += "�ɹ�¼��" + count + "��ѧ��ɼ�  ";
			throw new PlatformException(except);
		} else {
			except += "�ɹ�¼��" + count + "��ѧ��ɼ� ";
			throw new PlatformException(except);
		}
	}

	public void updateScoreBatch(List dataList, List idList, List reg_noList,
			List nameList, String open_course_id, String free_total_score_status)
			throws PlatformException {
		dbpool db = new dbpool();
		String tableName = "";
		String sql = "";
		String sql_data = "";
		String field = "";
		ArrayList exceptionList = new ArrayList();
		String except = "";
		int count = 0;
		if (idList == null) {
			for (int i = 0; i < dataList.size(); i++) {

				HashMap data = (HashMap) dataList.get(i);
				Set dataset = data.keySet();
				for (Iterator dataiter = dataset.iterator(); dataiter.hasNext();) {
					field = (String) dataiter.next();
					if (field.equals("tableName")) {
						tableName = (String) data.get(field);
						continue;
					}
					sql_data = field + "='" + (String) data.get(field) + "',";
				}
				sql = "update "
						+ tableName
						+ " set "
						+ sql_data.substring(0, sql_data.length() - 1)
						+ ",free_total_score_status='"
						+ free_total_score_status
						+ "' where student_id = (select id from entity_student_info where reg_no = '"
						+ reg_noList.get(i)
						+ "' and status = '0' and isgraduated= '0' and name='"
						+ nameList.get(i)
						+ "') and teachclass_id =(select id from entity_teach_class where open_course_id = '"
						+ open_course_id + "')";
				UserUpdateLog
						.setDebug("OracleElectiveScore.updateScoreBatch(List dataList, List idList, List reg_noList,List nameList, String open_course_id, String free_total_score_status) SQL="
								+ sql + " DATE=" + new Date());
				if (db.executeUpdate(sql) < 1) {
					exceptionList.add("(" + reg_noList.get(i) + ")"
							+ nameList.get(i) + "¼��ɼ�����");
				} else
					count++;
			}
		} else {
			for (int i = 0; i < dataList.size(); i++) {

				HashMap data = (HashMap) dataList.get(i);
				Set dataset = data.keySet();
				for (Iterator dataiter = dataset.iterator(); dataiter.hasNext();) {
					field = (String) dataiter.next();
					if (field.equals("tableName")) {
						tableName = (String) data.get(field);
						continue;
					}
					sql_data = field + "='" + (String) data.get(field) + "',";
				}
				sql = "update "
						+ tableName
						+ " set "
						+ sql_data.substring(0, sql_data.length() - 1)
						+ ",free_total_score_status='"
						+ free_total_score_status
						+ "' where student_id = '"
						+ (String) idList.get(i)
						+ "' and teachclass_id =(select id from entity_teach_class where open_course_id = '"
						+ open_course_id + "')";
				UserUpdateLog
						.setDebug("OracleElectiveScore.updateScoreBatch(List dataList, List idList, List reg_noList,List nameList, String open_course_id, String free_total_score_status) SQL="
								+ sql + " DATE=" + new Date());
				if (db.executeUpdate(sql) < 1) {
					exceptionList.add("(" + reg_noList.get(i) + ")"
							+ nameList.get(i) + "¼��ɼ�����");
				} else
					count++;
			}
		}

		db = null;
		if (exceptionList.size() > 0) {
			exceptionList.add("���֤����¼�� ");
			for (int i = 0; i < exceptionList.size(); i++) {
				except += (String) exceptionList.get(i) + "\\n";
			}
			if (count > 0)
				except += "�ɹ�¼��" + count + "��ѧ��ɼ�  ";
			throw new PlatformException(except);
		} else {
			except += "�ɹ�¼��" + count + "��ѧ��ɼ� ";
			throw new PlatformException(except);
		}
	}

	public void updateScoreBatch(List dataList, List idList, List reg_noList,
			List nameList, String open_course_id) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		StringBuffer sql_data = new StringBuffer();
		ArrayList exceptionList = new ArrayList();
		String except = "";
		int count = 0;
		for (int i = 0; i < reg_noList.size(); i++) {
			HashMap data = (HashMap) dataList.get(i);

			if ((String) data.get("renew_score") != null) {
				sql_data.append(", renew_score=").append(
						(String) data.get("renew_score"));
			}
			if ((String) data.get("experiment_score") != null) {

				sql_data.append(", experiment_score=").append(
						(String) data.get("experiment_score"));
			}
			if ((String) data.get("expend_score") != null) {

				sql_data.append(", expend_score=").append(
						(String) data.get("expend_score"));

			}
			if ((String) data.get("exam_score") != null) {
				sql_data.append(", exam_score=").append(
						(String) data.get("exam_score"));

			}

			if ((String) data.get("usual_score") != null) {
				sql_data.append(", usual_score=").append(
						(String) data.get("usual_score"));

			}
			if ((String) data.get("total_score") != null) {

				sql_data.append(", total_score=").append(
						(String) data.get("total_score"));

			}
			if ((String) data.get("test_score") != null) {

				sql_data.append(", test_score=").append(
						(String) data.get("test_score"));

			}
			String sqldata = sql_data.toString();

			sql = "update "
					+ data.get("tableName")
					+ " set "
					+ sqldata.substring(1, sqldata.length())
					+ " where student_id = (select id from entity_student_info where reg_no = '"
					+ reg_noList.get(i)
					+ "' and status = '0' and isgraduated= '0' and name='"
					+ nameList.get(i)
					+ "') and teachclass_id =(select id from entity_teach_class where open_course_id = '"
					+ open_course_id + "')";
			UserUpdateLog
					.setDebug("OracleElectiveScore.updateScoreBatch(List dataList, List idList, List reg_noList,List nameList, String open_course_id) SQL="
							+ sql + " DATE=" + new Date());
			if (db.executeUpdate(sql) < 1) {
				exceptionList.add("(" + reg_noList.get(i) + ")"
						+ nameList.get(i) + "¼��ɼ�����");
			} else {
				count++;
			}

			sql_data = new StringBuffer();
		}
		db = null;
		if (exceptionList.size() > 0) {
			exceptionList.add("���֤����¼�� ");
			for (int i = 0; i < exceptionList.size(); i++) {
				except += (String) exceptionList.get(i) + "\\n";
			}
			if (count > 0)
				except += "�ɹ�¼��" + count + "��ѧ��ɼ�  ";
			throw new PlatformException(except);
		} else {
			except += "�ɹ�¼��" + count + "��ѧ��ɼ� ";
			throw new PlatformException(except);
		}

	}

	public void updateMaxScores(List newScoreList) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_elective set ";
		String sql_con = "";
		String scoreType = null;
		ScoreDef newScore = null;
		int count = 0;
		boolean isFirst = true;
		boolean hasNext = false;
		for (int i = 0; i < newScoreList.size(); i++) {
			newScore = (ScoreDef) newScoreList.get(i);
			scoreType = newScore.getType();
			if (scoreType.equals(ElectiveScoreType.USUAL)) {
				sql = sql + " usual_score=" + newScore.getScore();
				sql_con = sql_con + "to_number(usual_score)>"
						+ newScore.getScore() + " and ";
				isFirst = false;
				hasNext = true;
			} else if (scoreType.equals(ElectiveScoreType.EXAM)) {
				if (isFirst) {
					sql = sql + " exam_score=" + newScore.getScore();
					sql_con = sql_con + "to_number(exam_score)>"
							+ newScore.getScore() + " and ";
					isFirst = false;
					hasNext = true;
				} else {
					sql = sql + ", exam_score=" + newScore.getScore();
					sql_con = sql_con + "to_number(exam_score)>"
							+ newScore.getScore() + " and ";
					hasNext = true;
				}
			} else if (scoreType.equals(ElectiveScoreType.TOTAL)) {
				if (isFirst) {
					sql = sql + " total_score=" + newScore.getScore();
					sql_con = sql_con + " to_number(total_score)>"
							+ newScore.getScore() + " and ";
					isFirst = false;
					hasNext = true;
				} else {
					sql = sql + ", total_score=" + newScore.getScore();
					sql_con = sql_con + "to_number(total_score)>"
							+ newScore.getScore() + " and ";
					hasNext = true;
				}
			} else if (scoreType.equals(ElectiveScoreType.EXPEND)) {
				if (isFirst) {
					sql = sql + " expend_score=" + newScore.getScore();
					sql_con = sql_con + "to_number(expend_score)>"
							+ newScore.getScore() + " and ";
					isFirst = false;
					hasNext = true;
				} else {
					sql = sql + ", expend_score=" + newScore.getScore();
					sql_con = sql_con + "to_number(expend_score)>"
							+ newScore.getScore() + " and ";
					hasNext = true;
				}
			} else if (scoreType.equals(ElectiveScoreType.RENEW)) {
				if (isFirst) {
					sql = sql + " renew_score=" + newScore.getScore();
					sql_con = sql_con + "to_number(renew_score)>"
							+ newScore.getScore() + " and ";
					isFirst = false;
					hasNext = true;
				} else {
					sql = sql + ", renew_score=" + newScore.getScore();
					sql_con = sql_con + "to_number(renew_score)>"
							+ newScore.getScore() + " and ";
					hasNext = true;
				}
			}

		}
		sql = sql
				+ " where "
				+ sql_con
				+ "teachclass_id =(select id from entity_teach_class where open_course_id='"
				+ this.getElective().getTeachClass().getOpenCourse().getId()
				+ "')";
		if (hasNext) {
			count = db.executeUpdate(sql);
			UserUpdateLog
					.setDebug("OracleElectiveScore.updateMaxScores(List newScoreList) SQL="
							+ sql + " COUNT=" + count + " DATE=" + new Date());
		}
		throw new PlatformException("���޸�ƽʱ�ɼ�" + count + "��<br>");
	}

	public void makeTotalScore(String usual_score, String percent)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_elective set total_score=to_number(usual_score)/("
				+ usual_score
				+ "+0.000001)*"
				+ percent
				+ "*100+to_number(exam_score)*(1-"
				+ percent
				+ ") where teachclass_id =(select id from entity_teach_class where open_course_id='"
				+ this.getElective().getTeachClass().getOpenCourse().getId()
				+ "')";
		// System.out.println(sql);
		int count = db.executeUpdate(sql);
		UserUpdateLog
				.setDebug("OracleElectiveScore.makeTotalScore(String usual_score, String percent) SQL="
						+ sql + " COUNT=" + count + " DATE=" + new Date());
		throw new PlatformException("��������3ɼ�" + count
				+ "����<a href=\"totalscore_generate.jsp\">����</a>");
	}

	public int genarateTotalScore(String elective_id, String total_score,
			String percent, String percent1, String percent2, String percent3)
			throws PlatformException {
		dbpool db = new dbpool();
		StringBuffer rule = new StringBuffer();
		String rule1 = "";
		String sql = "";
		if (percent != null && percent1 != null && percent2 != null
				&& percent3 != null) {
			rule.append("����=").append(Double.parseDouble(percent) * 100)
					.append("%").append("��ҵ").append("+").append(
							Double.parseDouble(percent1) * 100).append("%")
					.append("ʵ��").append("+").append(
							Double.parseDouble(percent2) * 100).append("%")
					.append("����").append("+").append(
							Double.parseDouble(percent3) * 100).append("%")
					.append("�Բ�");
			rule1 = rule.toString();
			sql = "update entity_elective set total_score='" + total_score
					+ "',total_score_rule='" + rule1 + "' where id='"
					+ elective_id + "'";
		} else {
			sql = "update entity_elective set total_score='" + total_score
					+ "' where id='" + elective_id + "'";
		}
		int count = db.executeUpdate(sql);
		UserUpdateLog
				.setDebug("OracleElectiveScore.genarateTotalScore(String elective_id, String total_score,String percent, String percent1, String percent2, String percent3) SQL="
						+ sql + " COUNT=" + count + " DATE=" + new Date());
		return count;

	}

	public int genarateTotalScore(String teachclass_id, String percent,
			String percent1, String percent2, String percent3)
			throws PlatformException {
		dbpool db = new dbpool();
		StringBuffer rule = new StringBuffer();
		StringBuffer score = new StringBuffer();
		String rule1 = "";
		String sql = "";
		ArrayList sqlList = new ArrayList();
		if (percent != null && percent1 != null && percent2 != null
				&& percent3 != null) {
			rule.append("����=").append(Double.parseDouble(percent) * 100)
					.append("%").append("��ҵ").append("+").append(
							Double.parseDouble(percent1) * 100).append("%")
					.append("ʵ��").append("+").append(
							Double.parseDouble(percent2) * 100).append("%")
					.append("����").append("+").append(
							Double.parseDouble(percent3) * 100).append("%")
					.append("�Բ�");
			rule1 = rule.toString();
			score.append(percent).append("*").append("nvl(usual_score,0)")
					.append("+").append(percent1).append("*").append(
							"nvl(experiment_score,0)").append("+").append(
							percent2).append("*").append("nvl(exam_score,0)")
					.append("+").append(percent3).append("*").append(
							"nvl(test_score,0)");
			// sql = "update entity_elective set
			// total_expend_score='"+total_expend_score+"',total_expend_score_rule='"+rule1+"'
			// where id='"+ elective_id+ "'";
			sql = "update entity_elective set total_score=ceil("
					+ score.toString() + ")" + ",total_score_rule='" + rule1
					+ "' where  teachclass_id='" + teachclass_id
					+ "' and free_total_score_status='0'";
			sqlList.add(sql);
			sql = "update entity_elective set total_score=99 where  teachclass_id='"
					+ teachclass_id
					+ "' and free_total_score_status='0' and to_number(total_score) > 99";
			sqlList.add(sql);
		}
		int count = db.executeUpdateBatch(sqlList);
		UserUpdateLog
				.setDebug("OracleElectiveScore.genarateTotalScore(String teachclass_id, String percent,String percent1, String percent2, String percent3) SQL="
						+ sql + " COUNT=" + count + " DATE=" + new Date());
		return count;

	}

	public int genarateExpendScore(String teachclass_id, String percent,
			String percent1, String percent2, String percent3)
			throws PlatformException {
		dbpool db = new dbpool();
		StringBuffer rule = new StringBuffer();
		StringBuffer score = new StringBuffer();
		String rule1 = "";
		String sql = "";
		ArrayList sqlList = new ArrayList();
		if (percent != null && percent1 != null && percent2 != null
				&& percent3 != null) {
			rule.append("��������=").append(Double.parseDouble(percent) * 100)
					.append("%").append("����").append("+").append(
							Double.parseDouble(percent1) * 100).append("%")
					.append("ʵ��").append("+").append(
							Double.parseDouble(percent2) * 100).append("%")
					.append("��ҵ").append("+").append(
							Double.parseDouble(percent3) * 100).append("%")
					.append("�Բ�");
			rule1 = rule.toString();
			score.append(percent).append("*").append("nvl(expend_score,0)")
					.append("+").append(percent1).append("*").append(
							"nvl(experiment_score,0)").append("+").append(
							percent2).append("*").append("nvl(usual_score,0)")
					.append("+").append(percent3).append("*").append(
							"nvl(test_score,0)");

			// sql = "update entity_elective set
			// total_expend_score='"+total_expend_score+"',total_expend_score_rule='"+rule1+"'
			// where id='"+ elective_id+ "'";
			sql = "update entity_elective set total_expend_score= ceil(("
					+ score.toString() + ")),total_expend_score_rule='" + rule1
					+ "' where  teachclass_id='" + teachclass_id
					+ "' and expend_score_student_status='1'";
			sqlList.add(sql);
			sql = "update entity_elective set total_expend_score=99 where  teachclass_id='"
					+ teachclass_id
					+ "' and expend_score_student_status='1' and to_number(total_expend_score) > 99";
			sqlList.add(sql);
		}
		int count = db.executeUpdateBatch(sqlList);
		UserUpdateLog
				.setDebug("OracleElectiveScore.genarateExpendScore(String teachclass_id, String percent,String percent1, String percent2, String percent3) SQL="
						+ sql + " COUNT=" + count + " DATE=" + new Date());
		return count;

	}

	public int genarateModifyTotalScore(String elective_id, String total_score,
			String[] calculate) throws PlatformException {
		dbpool db = new dbpool();
		StringBuffer rule = new StringBuffer();
		String rule1 = "";
		String sql = "";
		if (calculate != null && calculate.length == 2) {
			rule.append("����=���?�").append(calculate[0]).append("�η�*").append(
					calculate[1]);
			rule1 = rule.toString();
			sql = "update entity_elective set total_score='" + total_score
					+ "',total_score_modify_rule='" + rule1 + "' where id='"
					+ elective_id + "'";
		}
		int count = db.executeUpdate(sql);
		UserUpdateLog
				.setDebug("OracleElectiveScore.genarateModifyTotalScore(String elective_id, String total_score,String[] calculate) SQL="
						+ sql + " COUNT=" + count + " DATE=" + new Date());
		return count;
	}

	public int modifyTotalScore(String teachclass_id, String[] ctype,
			String[] calculate, String total_score, String low_score,
			String heigh_score) throws PlatformException {
		dbpool db = new dbpool();
		StringBuffer rule = new StringBuffer();
		StringBuffer rule1 = new StringBuffer();
		String modify_rule = "";
		String sql = "";
		int count = 0;
		boolean istype2 = false;
		ArrayList sqlList = new ArrayList();
		if (calculate != null && calculate.length == 2) {
			rule.append("����=���?�").append(calculate[0]).append("�η�*").append(
					calculate[1]);
			sql = "update entity_elective set total_score=ceil(power(total_score,1/"
					+ calculate[0]
					+ ")*"
					+ calculate[1]
					+ "),  total_score_modify_rule='"
					+ rule.toString()
					+ "' where teachclass_id='"
					+ teachclass_id
					+ "' and free_total_score_status='0'";
			sqlList.add(sql);
			sql = "update entity_elective set total_score=99 where teachclass_id='"
					+ teachclass_id
					+ "' and free_total_score_status='0' and to_number(total_score) > 99";
			sqlList.add(sql);
			count = db.executeUpdateBatch(sqlList);
			UserUpdateLog
					.setDebug("OracleElectiveScore.modifyTotalScore(String teachclass_id, String[] ctype,String[] calculate, String total_score, String low_score,String heigh_score) SQL="
							+ sql + " COUNT=" + count + " DATE=" + new Date());
		}
		if (ctype != null) {
			for (int i = 0; i < ctype.length; i++) {
				if (ctype[i].equals("2"))
					istype2 = true;
			}
		}
		if (istype2 == true) {
			rule1.append("����=�����").append(low_score).append("�����").append(
					heigh_score).append("����").append(total_score);
			modify_rule = "total_score_modify_rule1='" + rule1.toString() + "'";
			sql = "update entity_elective set total_score=" + total_score + ","
					+ modify_rule + " where teachclass_id='" + teachclass_id
					+ "' and total_score>=" + low_score + " and total_score<="
					+ heigh_score + " and free_total_score_status='0'";
			count = db.executeUpdate(sql);
			UserUpdateLog
					.setDebug("OracleElectiveScore.modifyTotalScore(String teachclass_id, String[] ctype,String[] calculate, String total_score, String low_score,String heigh_score) SQL="
							+ sql + " COUNT=" + count + " DATE=" + new Date());
		}
		return count;
	}

	public int modifyTotalExpendScore(String teachclass_id, String[] ctype,
			String[] calculate, String total_score, String low_score,
			String heigh_score) throws PlatformException {
		dbpool db = new dbpool();
		StringBuffer rule = new StringBuffer();
		StringBuffer rule1 = new StringBuffer();
		String modify_rule = "";
		String sql = "";
		int count = 0;
		boolean istype1 = false;
		boolean istype2 = false;
		ArrayList sqlList = new ArrayList();
		if (ctype != null) {
			for (int i = 0; i < ctype.length; i++) {
				if (ctype[i].equals("1"))
					istype1 = true;
				if (ctype[i].equals("2"))
					istype2 = true;
			}
		}
		if (istype1 == true && calculate != null && calculate.length == 2) {
			rule.append("��������=�������?�").append(calculate[0]).append("�η�*")
					.append(calculate[1]);
			sql = "update entity_elective set total_expend_score=ceil(power(total_expend_score,1/"
					+ calculate[0]
					+ ")*"
					+ calculate[1]
					+ "),  total_expend_score_modify_rule='"
					+ rule.toString()
					+ "' where teachclass_id='"
					+ teachclass_id
					+ "' and free_total_score_status='0'";
			sqlList.add(sql);
			sql = "update entity_elective set total_expend_score=99 where teachclass_id='"
					+ teachclass_id
					+ "' and free_total_score_status='0' and to_number(total_expend_score) > 99";
			sqlList.add(sql);
			count = db.executeUpdateBatch(sqlList);
			UserUpdateLog
					.setDebug("OracleElectiveScore.modifyTotalExpendScore(String teachclass_id, String[] ctype,String[] calculate, String total_score, String low_score,String heigh_score) SQL="
							+ sql + " COUNT=" + count + " DATE=" + new Date());
		}
		if (istype2 == true) {
			rule1.append("��������=�����").append(low_score).append("�����").append(
					heigh_score).append("����").append(total_score);
			modify_rule = "total_expendscore_modify_rule1='" + rule1.toString()
					+ "'";
			sql = "update entity_elective set total_expend_score="
					+ total_score + "," + modify_rule
					+ " where teachclass_id='" + teachclass_id
					+ "' and total_expend_score>=" + low_score
					+ " and total_expend_score<=" + heigh_score
					+ " and free_total_score_status='0'";
			count = db.executeUpdate(sql);
			UserUpdateLog
					.setDebug("OracleElectiveScore.modifyTotalExpendScore(String teachclass_id, String[] ctype,String[] calculate, String total_score, String low_score,String heigh_score) SQL="
							+ sql + " COUNT=" + count + " DATE=" + new Date());
		}
		return count;
	}

	public void makeExpendList(List searchproperties, String total_line)
			throws PlatformException {
		dbpool db = new dbpool();

		String t_class_where = "";
		String stu_where = "";
		String cour_where = "";
		if (searchproperties != null) {
			for (int ii = 0; ii < searchproperties.size(); ii++) {
				SearchProperty prop = (SearchProperty) searchproperties.get(ii);
				String field = prop.getField();
				String val = prop.getValue();
				if (field != null && field.equals("site_id"))
					stu_where += " and site_id='" + val + "'";
				if (field != null && field.equals("major_id"))
					stu_where += " and major_id='" + val + "'";
				if (field != null && field.equals("edutype_id"))
					stu_where += " and edutype_id='" + val + "'";
				if (field != null && field.equals("grade_id"))
					stu_where += " and grade_id='" + val + "'";

				if (field != null && field.equals("course_id"))
					cour_where += " and course_id='" + val + "'";
				if (field != null && field.equals("semester_id"))
					cour_where += " and semester_id='" + val + "'";

				if (field != null && field.equals("open_course_id"))
					t_class_where += " and open_course_id='" + val + "'";
			}
		}

		String SQL = "select distinct id from (select e.id as id,s.name as student_name,s.id as student_id,s.reg_no as reg_no,s.major_id,s.site_id,s.edutype_id,s.grade_id,t.open_course_id,ca.course_id,ci.name as course_name,si.id as semester_id,si.name as semester_name,e.status,e.score_status,e.total_score,e.total_scorelevel,e.total_score_viewlevel,e.total_scoredes,e.total_score_zerodes,e.total_score_zerocause,e.usual_score,e.usual_scorelevel,e.usual_score_viewlevel,e.usual_scoredes,e.usual_score_zerodes,e.usual_score_zerocause,e.exam_score,e.exam_scorelevel,e.exam_score_viewlevel,e.exam_scoredes,e.exam_score_zerodes,e.exam_score_zerocause,e.expend_score,e.expend_scorelevel,e.expend_score_viewlevel,e.expend_scoredes,e.expend_score_zerodes,e.expend_score_zerocause,e.renew_score,e.renew_scorelevel,e.renew_score_viewlevel,e.renew_scoredes,e.renew_score_zerodes,e.renew_score_zerocause from entity_elective e,(select * from entity_student_info where 1=1 "
				+ stu_where
				+ ") s,(select * from entity_teach_class where 1=1 "
				+ t_class_where
				+ ") t,(select * from entity_course_active where 1=1 "
				+ cour_where
				+ ") ca,entity_semester_info si,entity_course_info ci where s.id=e.student_id and e.teachclass_id=t.id and t.open_course_id=ca.id and ca.semester_id=si.id and ca.course_id=ci.id and s.isgraduated='0' and s.status='0')";
		String sql = "";
		String except = "";

		sql = SQL + Conditions.convertToCondition(searchproperties, null);
		sql = "update entity_elective set score_status = CONCAT(SUBSTR(score_status,1,7),'0') where id in("
				+ sql + " and total_score>" + total_line + ")";
		UserUpdateLog
				.setDebug("OracleElectiveScore.makeExpendList(List searchproperties, String total_line) SQL="
						+ sql + " DATE=" + new Date());
		db.executeUpdate(sql);

		sql = SQL + Conditions.convertToCondition(searchproperties, null);
		sql = "update entity_elective set score_status = CONCAT(SUBSTR(score_status,1,7),'1') where id in("
				+ sql + " and total_score<=" + total_line + ")";
		int count = db.executeUpdate(sql);
		UserUpdateLog
				.setDebug("OracleElectiveScore.makeExpendList(List searchproperties, String total_line) SQL="
						+ sql + " COUNT=" + count + " DATE=" + new Date());
		if (count > 0)
			except = "�ɹ���ѧ���趨Ϊ������<a href=javascript:window.close()>�ر�</a>";
		else
			except = "û��ѧ���趨Ϊ������<a href=javascript:window.close()>�ر�</a>";
		throw new PlatformException(except);
	}

	public int updateScoreStatus(String elective_id, String usual_score_status,
			String exam_score_status, String total_score_status,
			String expend_score_status) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_elective set usual_score_status='"
				+ usual_score_status + "',exam_score_status='"
				+ exam_score_status + "',total_score_status='"
				+ total_score_status + "',expend_score_status='"
				+ expend_score_status + "' where id='" + elective_id + "'";
		int count = 0;
		count = db.executeUpdate(sql);
		UserUpdateLog
				.setDebug("OracleElectiveScore.updateScoreStatus(String elective_id, String usual_score_status,String exam_score_status, String total_score_status,String expend_score_status) SQL="
						+ sql + " COUNT=" + count + " DATE=" + new Date());
		return count;
	}

	public int updateScoreStatus(String elective_id, String strsql)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_elective set " + strsql + " where id='"
				+ elective_id + "'";
		int count = 0;
		count = db.executeUpdate(sql);
		UserUpdateLog
				.setDebug("OracleElectiveScore.updateScoreStatus(String elective_id, String strsql) SQL="
						+ sql + " COUNT=" + count + " DATE=" + new Date());
//		SmsFactory smsFactory = SmsFactory.getInstance();
//		OracleSmsManagerPriv smsManagerPriv = new OracleSmsManagerPriv();
//		smsManagerPriv.manageSmsSystemPoint = 1;
//		SmsManage smsManage = smsFactory.creatSmsManage(smsManagerPriv);
//		SmsSystemPoint sp = smsManage.getSmsSystemPoint("06");
//		String sms_status = sp.getStatus();
//		if (sms_status.equals("1")) {
//			String mobilePhone = "";
//			String mobileSql = "select mobilephone from entity_usernormal_info where id in (select student_id from entity_elective where id='"
//					+ elective_id + "')";
//			MyResultSet rs = db.executeQuery(mobileSql);
//			try {
//				while (rs != null && rs.next()) {
//
//					mobilePhone += rs.getString("mobilephone") + ",";
//				}
//				/***************************************************************
//				 * �����Ƕ���֪ͨ
//				 **************************************************************/
//				if (mobilePhone.length() > 0) {
//					mobilePhone = mobilePhone.substring(0,
//							mobilePhone.length() - 1);
//					smsManage.addSystemSmsMessage(mobilePhone, sp.getContent(),
//							"�ɼ�����", null, "0", "2", new SimpleDateFormat(
//									"yyyy-MM-dd HH:mm:ss").format(new Date()));
//					SmsSendThread sendThread = new SmsSendThread(mobilePhone,
//							sp.getContent());
//					sendThread.start();
//				}
//			} catch (Exception e) {
//				
//			} finally {
//				db.close(rs);
//				db = null;
//			}
//		}

		return count;
	}

	public int updateStudentExpendScoreStatus(String elective_id,
			String expend_score_status) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_elective set expend_score_student_status='"
				+ expend_score_status + "' where id='" + elective_id + "'";
		int count = 0;
		count = db.executeUpdate(sql);
		UserUpdateLog
				.setDebug("OracleElectiveScore.updateStudentExpendScoreStatus(String elective_id,String expend_score_status) SQL="
						+ sql + " COUNT=" + count + " DATE=" + new Date());
		return count;
	}

	public int modifyTotalScore(String elective_id, String total_score,
			String low_score, String heigh_score) throws PlatformException {
		dbpool db = new dbpool();
		StringBuffer rule = new StringBuffer();
		rule.append("����=�����").append(low_score).append("��").append(heigh_score)
				.append("ͳһ����").append(total_score);
		String sql = "update entity_elective set total_score='" + total_score
				+ "',total_score_modify_rule='" + rule.toString()
				+ "' where id='" + elective_id + "' and total_score<='"
				+ heigh_score + "' and total_score>='" + low_score + "'";
		int count = 0;
		count = db.executeUpdate(sql);
		UserUpdateLog
				.setDebug("OracleElectiveScore.modifyTotalScore(String elective_id, String total_score,String low_score, String heigh_score) SQL="
						+ sql + " COUNT=" + count + " DATE=" + new Date());
		return count;
	}

	public HashMap getScoreStatus(String open_course_id)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select distinct open_course_id,usual_score_status,experiment_score_status, exam_score_status,total_score_status,expend_score_status from (select  t.open_course_id as open_course_id ,e.usual_score_status as usual_score_status,e.experiment_score_status as experiment_score_status,e.exam_score_status as exam_score_status,e.total_score_status as total_score_status,e.expend_score_status as expend_score_status from entity_elective e,entity_teach_class t where e.teachclass_id=t.id  and  t.open_course_id='"
				+ open_course_id + "')";
		rs = db.executeQuery(sql);

		HashMap hash = new HashMap();
		try {

			if (rs != null && rs.next()) {

				hash.put("open_course_id", rs.getString("open_course_id"));
				hash.put("usual_score_status", rs
						.getString("usual_score_status"));
				hash
						.put("exam_score_status", rs
								.getString("exam_score_status"));
				hash.put("experiment_score_status", rs
						.getString("experiment_score_status"));
				hash.put("total_score_status", rs
						.getString("total_score_status"));
				hash.put("expend_score_status", rs
						.getString("expend_score_status"));

			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return hash;

	}

	public int updateFreeTotalScore(String elective_id,
			String free_total_score, String free_total_score_status)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_elective set total_score='"
				+ free_total_score + "',free_total_score_status='"
				+ free_total_score_status + "' where id='" + elective_id + "'";
		int count = 0;
		count = db.executeUpdate(sql);
		UserUpdateLog
				.setDebug("OracleElectiveScore.updateFreeTotalScore(String elective_id,String free_total_score, String free_total_score_status) SQL="
						+ sql + " COUNT=" + count + " DATE=" + new Date());
		return count;
	}
	/**
	 * 修改选课分数
	 * @param studentId
	 * @param opencourseId
	 * @param score
	 * @return
	 * @throws PlatformException
	 */
	public int updateScore(String studentId, String opencourseId,
			
			String score)throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update PR_BZZ_TCH_STU_ELECTIVE set SCORE_EXAM = "+score+ "WHERE S.FK_STU_ID='"+studentId+"' AND S.FK_TCH_OPENCOURSE_ID = '"+opencourseId+"'";
		int count = 0;
		count = db.executeUpdate(sql);
		return count;
	}
	
	public String getElectiveScore(String studentId, String opencourseId)throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String score = "";
		String sql = "SELECT SCORE_EXAM FROM PR_BZZ_TCH_STU_ELECTIVE WHERE S.FK_STU_ID='"+studentId+"' AND S.FK_TCH_OPENCOURSE_ID = '"+opencourseId+"'";
		rs = db.executeQuery(sql);
		try {
			while(rs != null && rs.next()){
				score = rs.getString("SCORE_EXAM");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return score;
	}
	
	public Map getOnlineExamScore(String studentId, String opencourseId)throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		Map map=new HashMap();
		String sql =
			"select nvl(pbps.score,0), nvl(t.passscore,80)\n" +
			"  from PE_BZZ_PICI t, pe_bzz_pici_student pbps\n" + 
			" where t.id = pbps.pc_id\n" + 
			"   and t.id = '"+opencourseId+"'\n" + 
			"   and pbps.stu_id = '"+studentId+"'";

		rs = db.executeQuery(sql);
		try {
			while(rs != null && rs.next()){
				map.put("stuScore", rs.getDouble(1));
				map.put("passScore", rs.getDouble(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.close(rs);
		}
		return map;
	}
	
	/**
	 * 修改在线考试批次成绩
	 * @param studentId
	 * @param opencourseId
	 * @param score
	 * @return
	 * @throws PlatformException
	 */
	public int updateOnlineExamScore(String studentId, String opencourseId,
			String score,String ispass)throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update pe_bzz_pici_student s set s.score = "+score;
		if("1".equals(ispass)){
			sql+=" , s.ispass='1'";
		}
		sql+="  WHERE S.stu_id='"+studentId+"' AND s.pc_id = '"+opencourseId+"'";
		int count = 0;
		count = db.executeUpdate(sql);
		return count;
	}
}
