/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.activity.Elective;
import com.whaty.platform.entity.activity.ScoreActivity;
import com.whaty.platform.entity.activity.score.ElectiveScoreType;
import com.whaty.platform.entity.activity.score.ScoreDef;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author chenjian
 * 
 */

public class OracleScoreActivity implements ScoreActivity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.ScoreActivity#generateTotalScore(java.util.Map)
	 */
	public void generateTotalScore(Map teachClassMap) throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.ScoreActivity#updateCoreScores(java.util.Map)
	 */
	
	public void updateCoreScores(Map electiveScoreMap) throws PlatformException {
		dbpool db = new dbpool();
		Iterator electiveScores = electiveScoreMap.entrySet().iterator();
		List sqlGroup = new ArrayList();
		while (electiveScores.hasNext()) {
			String sql = "";
			Elective elective = (Elective) ((Map.Entry) electiveScores.next())
					.getKey();
			List newScoreList = (List) ((Map.Entry) electiveScores.next())
					.getValue();
			ScoreDef newScore = null;
			String scoreType = null;
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
					sql = sql + "update entity_elective set usual_score="
							+ newScore.getScore() + "," + "usual_scorelevel='"
							+ newScore.getScoreLevel() + "',"
							+ "usual_score_viewlevel=" + viewLevel + ","
							+ "usual_scoredes='" + newScore.getScoreDes()
							+ "'," + "usual_score_zerodes='"
							+ newScore.getZeroDes() + "',"
							+ "usual_score_zerocaus='"
							+ newScore.getZeroCause() + "'";
					isFirst = false;
					hasNext = true;
				} else if (scoreType.equals(ElectiveScoreType.EXAM)) {
					if (isFirst) {
						sql = sql + "update entity_elective set exam_score="
								+ newScore.getScore() + ","
								+ "exam_scorelevel='"
								+ newScore.getScoreLevel() + "',"
								+ "exam_score_viewlevel=" + viewLevel + ","
								+ "exam_scoredes='" + newScore.getScoreDes()
								+ "'," + "exam_score_zerodes='"
								+ newScore.getZeroDes() + "',"
								+ "exam_score_zerocaus='"
								+ newScore.getZeroCause() + "'";
						isFirst = false;
						hasNext = true;
					} else {
						sql = sql + ", exam_score=" + newScore.getScore() + ","
								+ "exam_scorelevel='"
								+ newScore.getScoreLevel() + "',"
								+ "exam_score_viewlevel=" + viewLevel + ","
								+ "exam_scoredes='" + newScore.getScoreDes()
								+ "'," + "exam_score_zerodes='"
								+ newScore.getZeroDes() + "',"
								+ "exam_score_zerocaus='"
								+ newScore.getZeroCause() + "'";
						hasNext = true;
					}
				} else if (scoreType.equals(ElectiveScoreType.TOTAL)) {
					if (isFirst) {
						sql = sql + "update entity_elective set total_score="
								+ newScore.getScore() + ","
								+ "total_scorelevel='"
								+ newScore.getScoreLevel() + "',"
								+ "total_score_viewlevel=" + viewLevel + ","
								+ "total_scoredes='" + newScore.getScoreDes()
								+ "'," + "total_score_zerodes='"
								+ newScore.getZeroDes() + "',"
								+ "total_score_zerocaus='"
								+ newScore.getZeroCause() + "'";
						isFirst = false;
						hasNext = true;
					} else {
						sql = sql + ", total_score=" + newScore.getScore()
								+ "," + "total_scorelevel='"
								+ newScore.getScoreLevel() + "',"
								+ "total_score_viewlevel=" + viewLevel + ","
								+ "total_scoredes='" + newScore.getScoreDes()
								+ "'," + "total_score_zerodes='"
								+ newScore.getZeroDes() + "',"
								+ "total_score_zerocaus='"
								+ newScore.getZeroCause() + "'";
						hasNext = true;
					}
				} else if (scoreType.equals(ElectiveScoreType.EXPEND)) {
					if (isFirst) {
						sql = sql + "update entity_elective set expend_score="
								+ newScore.getScore() + ","
								+ "expend_scorelevel='"
								+ newScore.getScoreLevel() + "',"
								+ "expend_score_viewlevel=" + viewLevel + ","
								+ "expend_scoredes='" + newScore.getScoreDes()
								+ "'," + "expend_score_zerodes='"
								+ newScore.getZeroDes() + "',"
								+ "expend_score_zerocaus='"
								+ newScore.getZeroCause() + "'";
						isFirst = false;
						hasNext = true;
					} else {
						sql = sql + ", expend_score=" + newScore.getScore()
								+ "," + "expend_scorelevel='"
								+ newScore.getScoreLevel() + "',"
								+ "expend_score_viewlevel=" + viewLevel + ","
								+ "expend_scoredes='" + newScore.getScoreDes()
								+ "'," + "expend_score_zerodes='"
								+ newScore.getZeroDes() + "',"
								+ "expend_score_zerocaus='"
								+ newScore.getZeroCause() + "'";
						hasNext = true;
					}
				} else if (scoreType.equals(ElectiveScoreType.RENEW)) {
					if (isFirst) {
						sql = sql + "update entity_elective set renew_score="
								+ newScore.getScore() + ","
								+ "renew_scorelevel='"
								+ newScore.getScoreLevel() + "',"
								+ "renew_score_viewlevel=" + viewLevel + ","
								+ "renew_scoredes='" + newScore.getScoreDes()
								+ "'," + "renew_score_zerodes='"
								+ newScore.getZeroDes() + "',"
								+ "renew_score_zerocaus='"
								+ newScore.getZeroCause() + "'";
						isFirst = false;
						hasNext = true;
					} else {
						sql = sql + ", renew_score=" + newScore.getScore()
								+ "," + "renew_scorelevel='"
								+ newScore.getScoreLevel() + "',"
								+ "renew_scoredes='" + newScore.getScoreDes()
								+ "'," + "renew_score_viewlevel=" + viewLevel
								+ "," + "renew_score_zerodes='"
								+ newScore.getZeroDes() + "',"
								+ "renew_score_zerocaus='"
								+ newScore.getZeroCause() + "'";
						hasNext = true;
					}
				}

			}
			sql = sql + " where id='" + elective.getId() + "'";
			if (hasNext) {
				sqlGroup.add(sql);
			}
			int count = db.executeUpdateBatch(sqlGroup);
			UserUpdateLog.setDebug("OracleScoreActivity.updateCoreScores(Map electiveScoreMap) SQL=" + sql + " COUNT=" + count + " DATE=" + new Date());
		}

	}

	public Map getCoreScores(Page page, ElectiveScoreType scoreType,
			String open_course_id) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		List scoreList = new ArrayList();
		Map electiveScoreMap;
		try {
			sql = "select id,student_id,total_score,total_scorelevel,total_scoredes,total_score_viewlevel,total_scoredes,total_score_zerodes,total_score_zerocause "
					+ " ,usual_score,usual_scorelevel,usual_scoredes,usual_score_viewlevel,usual_scoredes,usual_score_zerodes,usual_score_zerocause"
					+ ",exam_score,exam_scorelevel,exam_scoredes,exam_score_viewlevel,exam_scoredes,exam_score_zerodes,exam_score_zerocause"
					+ ",expend_score,expend_scorelevel,expend_scoredes,expend_score_viewlevel,expend_scoredes,expend_score_zerodes,expend_score_zerocause"
					+ ",renew_score,renew_scorelevel,renew_scoredes,renew_score_viewlevel,renew_scoredes,renew_score_zerodes,renew_score_zerocause"
					+ "from(select e.id as id,student_id,total_score,total_scorelevel,total_scoredes,total_score_viewlevel,total_scoredes,total_score_zerodes,total_score_zerocause"
					+ " ,usual_score,usual_scorelevel,usual_scoredes,usual_score_viewlevel,usual_scoredes,usual_score_zerodes,usual_score_zerocause"
					+ ",exam_score,exam_scorelevel,exam_scoredes,exam_score_viewlevel,exam_scoredes,exam_score_zerodes,exam_score_zerocause"
					+ ",expend_score,expend_scorelevel,expend_scoredes,expend_score_viewlevel,expend_scoredes,expend_score_zerodes,expend_score_zerocause"
					+ ",renew_score,renew_scorelevel,renew_scoredes,renew_score_viewlevel,renew_scoredes,renew_score_zerodes,renew_score_zerocause"
					+ "from entity_elective e,entity_teach_class t,entity_course_active a where t.open_course_id = a.id and e.teachclass_id = t.id and a.id = '"
					+ open_course_id + "')";
			if (page != null) {
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleElective elective = new OracleElective();
				elective.setId(rs.getString("id"));

			}

		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return null;
	}

}
