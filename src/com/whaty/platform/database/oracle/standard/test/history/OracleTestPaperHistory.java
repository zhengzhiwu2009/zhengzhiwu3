package com.whaty.platform.database.oracle.standard.test.history;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.google.gxp.org.apache.xerces.impl.dtd.models.DFAContentModel;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.test.history.TestPaperHistory;
import com.whaty.platform.test.question.TestQuestionType;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.string.encode.HtmlEncoder;

public class OracleTestPaperHistory extends TestPaperHistory {
	private String piciStudent;
	public String getPiciStudent() {
		return piciStudent;
	}

	public void setPiciStudent(String piciStudent) {
		this.piciStudent = piciStudent;
	}

	public OracleTestPaperHistory() {
	}

	public OracleTestPaperHistory(String id) {
		String sql = "select id, user_id,testpaper_id, to_char(test_date,'yyyy-mm-dd') as test_date,test_result,score from test_testpaper_history " + "where id='" + id + "'";
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setUserId(rs.getString("user_id"));
				this.setTestPaperId(rs.getString("testpaper_id"));
				this.setTestDate(rs.getString("test_date"));
				this.setTestResult(rs.getString("test_result"));
				this.setScore(rs.getString("score"));
			}
		} catch (SQLException e) {

		} finally {
			db.close(rs);
		}
	}

	public int add() throws PlatformException {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		dbpool db = new dbpool();
		// String delSql = "delete from test_testpaper_history where user_id='"
		// + this.getUserId() + "' and testpaper_id='"
		// + this.getTestPaperId() + "'";
		// db.executeUpdate(delSql);
		// UserDeleteLog.setDebug("OracleTestPaperHistory.add() SQL=" + delSql +
		// " DATE=" + new Date());
		String t_user_id = "";
		t_user_id = this.getUserId();
		t_user_id = t_user_id.substring(t_user_id.indexOf("(") + 1, t_user_id.indexOf(")"));
		String openTime = "";
		try {
			openTime = ServletActionContext.getRequest().getSession().getAttribute(us.getSsoUser().getId() + "startTime").toString();
		} catch (Exception e) {
			openTime = "SYSDATE";
		}
		String sql = "insert into test_testpaper_history (id, user_id,testpaper_id, test_date, test_result,score,t_user_id,test_open_date) values ("
				+ "to_char(s_test_testpaper_history_id.nextval), '" + this.getUserId() + "', '" + this.getTestPaperId() + "',sysdate, ?" + ", '" + this.getScore() + "','" + us.getId() + "','"
				+ openTime + "')";
		if ("SYSDATE".equalsIgnoreCase(openTime)) {
			sql = "insert into test_testpaper_history (id, user_id,testpaper_id, test_date, test_result,score,t_user_id,test_open_date) values (" + "to_char(s_test_testpaper_history_id.nextval), '"
					+ this.getUserId() + "', '" + this.getTestPaperId() + "',sysdate, ?" + ", '" + this.getScore() + "','" + us.getId() + "',TO_CHAR(sysdate,'yyyy-MM-dd hh24:mi:ss'))";
		}
		int suc = db.executeUpdate(sql, this.getTestResult());
		UserAddLog.setDebug("OracleTestPaperHistory.add() SQL=" + sql + " COUNT=" + suc + " DATE=" + new Date());
		// String gradeSql = "update entity_elective set test_score ='"
		// + this.getScore()
		// + "' where student_id ='"
		// + this.getUserId().substring(1, this.getUserId().indexOf(")"))
		// + "' and teachclass_id in (select group_id from test_testpaper_info
		// where id ='"
		// + this.getTestPaperId() + "')";
		// db.executeUpdate(gradeSql);
		// UserUpdateLog.setDebug("OracleTestPaperHistory.add() SQL=" + gradeSql
		// + " DATE=" + new Date());
		/**
		 * 考试通过分数根据课程通过分数比较
		 * 2016-08-17
		 * */
		String passscoreSql=" SELECT pbtc.passrole score FROM PE_BZZ_TCH_COURSE pbtc ,PR_BZZ_TCH_OPENCOURSE pbto ,PR_BZZ_TCH_STU_ELECTIVE pbtse ,TEST_TESTPAPER_INFO tti "
				+" WHERE pbtc.id = pbto.fk_course_id AND pbto.id = pbtse.fk_tch_opencourse_id AND pbto.fk_course_id = tti.group_id   and pbtse.fk_stu_id = '" + this.getUserId().substring(1, this.getUserId().indexOf(")")) + "'\n"
				+ "                   and tti.id = '" + this.getTestPaperId() + "'";
		MyResultSet rs =db.execuQuery(passscoreSql);
		String passscore = "";
		try {
			if (rs != null && rs.next()) {
				passscore =rs.getString("score");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			db.close(rs);
		}
		
		String sql1 = "";
		if (this.getScore() != null && !"".equals(this.getScore()) && Double.parseDouble(this.getScore()) >= Double.parseDouble(passscore)) {// 如果分数大于80状态改为1，完成时间改为
			sql1 = " ,e.ispass ='1',e.completed_time=sysdate ";
		}

		String scoreSql = "update pr_bzz_tch_stu_elective e\n" + "   set e.exam_times = nvl(e.exam_times,0) + 1, e.score_exam = decode(sign(nvl(e.score_exam,0)-" + this.getScore() + "),-1,"
				+ this.getScore() + ",e.score_exam) " + sql1 + "\n" + " where e.id in (select ele.id\n" + "                  from pr_bzz_tch_stu_elective ele,\n"
				+ "                       pr_bzz_tch_opencourse   opn,\n" + "                       test_testpaper_info     tti\n" + "                 where ele.fk_tch_opencourse_id = opn.id\n"
				+ "                   and opn.fk_course_id = tti.group_id\n" + "                   and ele.fk_stu_id = '" + this.getUserId().substring(1, this.getUserId().indexOf(")")) + "'\n"
				+ "                   and tti.id = '" + this.getTestPaperId() + "')";
		db.executeUpdate(scoreSql);
		UserUpdateLog.setDebug("OracleTestPaperHistory.add() SQL=" + scoreSql + " DATE=" + new Date());
		return suc;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update test_testpaper_history set testpaper_id='" + this.getTestPaperId() + "', mark_date=sysdate, test_result=?, score='" + this.getScore() + "',ischeck='" + this.getStatus()
				+ "' where id='" + this.getId() + "'";
		String gradeSql = "update entity_elective	set test_score ='" + this.getScore() + "' where student_id  ='" + this.getUserId().substring(1, this.getUserId().indexOf(")"))
				+ "' and teachclass_id in (select group_id from test_testpaper_info where id ='" + this.getTestPaperId() + "')";
		// String piciStuSql =" update pe_bzz_pici_student set score
		// ='"+this.getScore()+"' where "
		db.executeUpdate(gradeSql);
		String piciStudentSql =" update pe_bzz_pici_student set exam_struts ='examstruts2' where id ='"+this.getPiciStudent()+"' ";
		db.executeUpdate(piciStudentSql);
		UserUpdateLog.setDebug("OracleTestPaperHistory.update() SQL1=" + sql + " SQL2" + gradeSql + " DATE=" + new Date());
		return db.executeUpdate(sql, this.getTestResult());
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "delete from test_testpaper_history where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleTestPaperHistory.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public HashMap getTestResultMap() {
		HashMap map = new HashMap();
		try {
			Document doc = DocumentHelper.parseText(this.getTestResult());
			String totalScore = doc.selectSingleNode("/answers/totalscore").getText();
			map.put("totalScore", totalScore);
			String totalNote = doc.selectSingleNode("/answers/totalnote").getText();
			map.put("totalNote", totalNote);
			List answerList = doc.selectNodes("/answers/item");
			HashMap userAnswer = new HashMap();
			HashMap standardAnswer = new HashMap();
			HashMap Title = new HashMap();
			HashMap Type = new HashMap();
			HashMap standardScore = new HashMap();
			HashMap userScore = new HashMap();
			HashMap Note = new HashMap();
			List idList = new ArrayList();
			String id = "";
			String uAnswer = "";
			String sAnswer = "";
			String title = "";
			String type = "";
			String sScore = "";
			String uScore = "";
			String note = "";
			for (Iterator it = answerList.iterator(); it.hasNext();) {
				Element answer = (Element) it.next();
				Element idEle = answer.element("id");
				id = idEle.getTextTrim();
				idList.add(id);
				Element typeEle = answer.element("type");
				type = typeEle.getTextTrim();
				if (!type.equalsIgnoreCase(TestQuestionType.YUEDU)) {
					Element uAnswerEle = answer.element("uanswer");
					uAnswer = uAnswerEle.getTextTrim();
					Element sAnswerEle = answer.element("sanswer");
					sAnswer = sAnswerEle.getTextTrim();
					Element titleEle = answer.element("title");
					title = titleEle.getTextTrim();
					Element sScoreEle = answer.element("sscore");
					sScore = sScoreEle.getTextTrim();
					Element uScoreEle = answer.element("uscore");
					uScore = uScoreEle.getTextTrim();
					Element noteEle = answer.element("note");
					note = noteEle.getTextTrim();

					userAnswer.put(id, HtmlEncoder.decode(uAnswer));
					standardAnswer.put(id, HtmlEncoder.decode(sAnswer));
					Title.put(id, HtmlEncoder.decode(title));
					Type.put(id, type);
					standardScore.put(id, HtmlEncoder.decode(sScore));
					userScore.put(id, HtmlEncoder.decode(uScore));
					Note.put(id, HtmlEncoder.decode(note));
				} else {
					List uAnswerList = new ArrayList();
					List sAnswerList = new ArrayList();
					List titleList = new ArrayList();
					List sScoreList = new ArrayList();
					List uScoreList = new ArrayList();
					List noteList = new ArrayList();

					uAnswer = "";
					uAnswerList.add(HtmlEncoder.decode(uAnswer));
					sAnswer = "";
					sAnswerList.add(HtmlEncoder.decode(sAnswer));
					Element titleEle = answer.element("title");
					title = titleEle.getTextTrim();
					titleList.add(HtmlEncoder.decode(title));
					Element sScoreEle = answer.element("sscore");
					sScore = sScoreEle.getTextTrim();
					sScoreList.add(HtmlEncoder.decode(sScore));
					Element uScoreEle = answer.element("uscore");
					uScore = uScoreEle.getTextTrim();
					uScoreList.add(HtmlEncoder.decode(uScore));
					Element noteEle = answer.element("note");
					note = noteEle.getTextTrim();
					noteList.add(HtmlEncoder.decode(note));

					Element subEle = answer.element("subitem");//
					Iterator itSub = subEle.elementIterator("item");//
					while (itSub.hasNext()) {
						Element subItem = (Element) itSub.next();//
						Element idEleSub = answer.element("id");
						id = idEleSub.getTextTrim();

						Element uAnswerEleSub = subItem.element("uanswer");
						uAnswer = uAnswerEleSub.getTextTrim();
						uAnswerList.add(HtmlEncoder.decode(uAnswer));
						Element sAnswerEleSub = subItem.element("sanswer");
						sAnswer = sAnswerEleSub.getTextTrim();
						sAnswerList.add(HtmlEncoder.decode(sAnswer));
						Element titleEleSub = subItem.element("title");
						title = titleEleSub.getTextTrim();
						titleList.add(HtmlEncoder.decode(title));
						Element sScoreEleSub = subItem.element("sscore");//
						sScore = sScoreEleSub.getTextTrim();
						sScoreList.add(HtmlEncoder.decode(sScore));
						Element uScoreEleSub = subItem.element("uscore");
						uScore = uScoreEleSub.getTextTrim();
						uScoreList.add(HtmlEncoder.decode(uScore));
						Element noteEleSub = subItem.element("note");
						note = noteEleSub.getTextTrim();
						noteList.add(HtmlEncoder.decode(note));
					}
					userAnswer.put(id, uAnswerList);
					standardAnswer.put(id, sAnswerList);
					Title.put(id, titleList);
					Type.put(id, type);
					standardScore.put(id, sScoreList);
					userScore.put(id, uScoreList);
					Note.put(id, noteList);
				}
			}
			map.put("idList", idList);
			map.put("userAnswer", userAnswer);
			map.put("standardAnswer", standardAnswer);
			map.put("title", Title);
			map.put("type", Type);
			map.put("standardScore", standardScore);
			map.put("userScore", userScore);
			map.put("note", Note);
		} catch (DocumentException de) {
		}
		return map;
	}

	public void setTestResult(HashMap map) {
		String xml = "<answers>";
		List idList = (List) map.get("idList");
		HashMap userAnswer = (HashMap) map.get("userAnswer");
		HashMap standardAnswer = (HashMap) map.get("standardAnswer");
		HashMap Title = (HashMap) map.get("title");
		HashMap Type = (HashMap) map.get("type");
		HashMap standardScore = (HashMap) map.get("standardScore");
		HashMap userScore = (HashMap) map.get("userScore");
		HashMap Note = (HashMap) map.get("note");
		if (userAnswer == null)
			userAnswer = new HashMap();
		if (standardAnswer == null)
			standardAnswer = new HashMap();
		if (Title == null)
			Title = new HashMap();
		if (Type == null)
			Type = new HashMap();
		if (standardScore == null)
			standardScore = new HashMap();
		if (userScore == null)
			userScore = new HashMap();
		if (Note == null)
			Note = new HashMap();
		String totalScore = (String) map.get("totalScore");
		xml += "<totalscore>" + totalScore + "</totalscore>";
		String totalNote = (String) map.get("totalNote");
		if (totalNote == null)
			totalNote = "";
		xml += "<totalnote>" + HtmlEncoder.encode(totalNote) + "</totalnote>";
		String id = "";
		String type = "";
		String uAnswer = "";
		String sAnswer = "";
		String title = "";
		String sScore = "";
		String uScore = "";
		String note = "";
		for (Iterator it = idList.iterator(); it.hasNext();) {
			id = (String) it.next();
			type = (String) Type.get(id);
			if (!type.equalsIgnoreCase(TestQuestionType.YUEDU)) {
				uAnswer = (String) userAnswer.get(id);
				sAnswer = (String) standardAnswer.get(id);
				title = (String) Title.get(id);
				sScore = (String) standardScore.get(id);
				uScore = (String) userScore.get(id);
				note = (String) Note.get(id);
				if (uAnswer == null || uAnswer.equals("") || uAnswer.equals("null"))
					uAnswer = "";
				if (sAnswer == null || sAnswer.equals("") || sAnswer.equals("null"))
					sAnswer = "";
				if (title == null || title.equals("") || title.equals("null"))
					title = "";
				if (type == null || type.equals("") || type.equals("null"))
					type = "";
				if (sScore == null || sScore.equals("") || sScore.equals("null"))
					sScore = "0";
				if (uScore == null || uScore.equals("") || uScore.equals("null"))
					uScore = "0";
				if (note == null || note.equals("") || note.equals("null"))
					note = "";
				xml += "<item><id>" + id + "</id><type>" + type + "</type><title>" + HtmlEncoder.encode(title) + "</title><sanswer>" + HtmlEncoder.encode(sAnswer) + "</sanswer><uanswer>"
						+ HtmlEncoder.encode(uAnswer) + "</uanswer><sscore>" + HtmlEncoder.encode(sScore) + "</sscore><uscore>" + HtmlEncoder.encode(uScore) + "</uscore><note>"
						+ HtmlEncoder.encode(note) + "</note></item>";
			} else {
				xml += "<item><id>" + id + "</id><type>" + type + "</type>";
				List uAnswerList = (List) userAnswer.get(id);
				if (uAnswerList == null)
					uAnswerList = new ArrayList();
				List sAnswerList = (List) standardAnswer.get(id);
				List titleList = (List) Title.get(id);
				List sScoreList = (List) standardScore.get(id);
				List uScoreList = (List) userScore.get(id);
				if (uScoreList == null)
					uScoreList = new ArrayList();
				List noteList = (List) Note.get(id);
				if (noteList == null)
					noteList = new ArrayList();
				title = (String) titleList.get(0);
				if (title == null || title.equals("") || title.equals("null"))
					title = "";
				xml += "<title>" + HtmlEncoder.encode(title) + "</title>";
				sScore = (String) sScoreList.get(0);
				if (sScore == null || sScore.equals("") || sScore.equals("null"))
					sScore = "0";
				xml += "<sscore>" + HtmlEncoder.encode(sScore) + "</sscore>";
				try {
					uScore = (String) uScoreList.get(0);
				} catch (Exception e1) {

				}
				if (uScore == null || uScore.equals("") || uScore.equals("null"))
					uScore = "0";
				xml += "<uscore>" + HtmlEncoder.encode(uScore) + "</uscore>";
				if (noteList != null && noteList.size() > 0)
					note = (String) noteList.get(0);
				else
					note = "";
				if (note == null || note.equals("") || note.equals("null"))
					note = "";
				xml += "<note>" + HtmlEncoder.encode(note) + "</note><subitem>";

				for (int k = 1; k < titleList.size(); k++) {
					try {
						uAnswer = (String) uAnswerList.get(k);
					} catch (Exception e) {
						uAnswer = "";
					}
					sAnswer = (String) sAnswerList.get(k);
					title = (String) titleList.get(k);
					sScore = (String) sScoreList.get(k);
					try {
						uScore = (String) uScoreList.get(k);
					} catch (Exception e) {
						uScore = "";
					}
					if (noteList != null && noteList.size() > 0)
						note = (String) noteList.get(k);
					else
						note = "";
					if (uAnswer == null || uAnswer.equals("") || uAnswer.equals("null"))
						uAnswer = "";
					if (sAnswer == null || sAnswer.equals("") || sAnswer.equals("null"))
						sAnswer = "";
					if (title == null || title.equals("") || title.equals("null"))
						title = "";
					if (sScore == null || sScore.equals("") || sScore.equals("null"))
						sScore = "0";
					if (uScore == null || uScore.equals("") || uScore.equals("null"))
						uScore = "0";
					if (note == null || note.equals("") || note.equals("null"))
						note = "";
					xml += "<item><id>" + k + "</id><title>" + HtmlEncoder.encode(title) + "</title><sanswer>" + HtmlEncoder.encode(sAnswer) + "</sanswer><uanswer>" + HtmlEncoder.encode(uAnswer)
							+ "</uanswer><sscore>" + HtmlEncoder.encode(sScore) + "</sscore><uscore>" + HtmlEncoder.encode(uScore) + "</uscore><note>" + HtmlEncoder.encode(note) + "</note></item>";
				}
				xml += "</subitem></item>";
			}
		}
		xml += "</answers>";
		this.setTestResult(xml);
	}

	public int addOnlineExam() throws PlatformException {
		SimpleDateFormat simple =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dbpool db = new dbpool();
		String examStatus ="";
		String t_user_id = "";
		t_user_id = this.getUserId();
		t_user_id = t_user_id.substring(t_user_id.indexOf("(") + 1, t_user_id.indexOf(")"));
		String sql = "insert into test_testpaper_history (id, user_id,testpaper_id, test_date, test_result,score,t_user_id,test_open_date) values ("
				+ "to_char(s_test_testpaper_history_id.nextval), '" + this.getUserId() + "', '" + this.getTestPaperId() + "',to_date('"+simple.format(new Date())+"','yyyy-mm-dd hh24:mi:ss'), ?" + ", '" + this.getScore() + "','" + t_user_id + "','"
				+ this.getTestOpenDate() + "')";
		int suc = db.executeUpdate(sql, this.getTestResult());
		UserAddLog.setDebug("OracleTestPaperHistory.add() SQL=" + sql + " COUNT=" + suc + " DATE=" + new Date());
		String sql2 = "select nvl(passscore,80) from pe_bzz_pici pbp,test_testpaper_info tti where tti.id='" + this.getTestPaperId() + "' and pbp.id=tti.group_id";
		MyResultSet rs = db.executeQuery(sql2);
		String sql1 = "";
		try {
			if (null != rs && rs.next()) {
				double passScore = rs.getDouble(1);
				if (passScore <= Double.parseDouble(this.getScore())) {
					sql1 = ",p.ISPASS='1',p.COMPLETE_TIME=sysdate";
				} else {
					sql1 = ",p.ISPASS='0',p.COMPLETE_TIME=sysdate";
				}
			}
			if(this.getIsSub().equals("false")){
				examStatus = "examstruts2";
			}else{
				examStatus = "examstruts1";
			}

			String scoreSql = "update pe_bzz_pici_student p\n" + "   set p.exam_times = nvl(exam_times, 0) + 1,\n" + "   p.score ='" + this.getScore() + "' , p.sub_score ='" + this.getScore() + "' ,p.exam_struts ='"+examStatus+"' " +
					" \n" + sql1 + " where p.pc_id =(select tti.group_id from test_testpaper_info tti where tti.id='" + this.getTestPaperId() + "') and p.stu_id='" + t_user_id + "'";
			db.executeUpdate(scoreSql);
			UserUpdateLog.setDebug("OracleTestPaperHistory.add() SQL=" + scoreSql + " DATE=" + new Date());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close(rs);
		}

		return suc;
	}
}
