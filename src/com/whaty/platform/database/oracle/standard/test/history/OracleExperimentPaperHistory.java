package com.whaty.platform.database.oracle.standard.test.history;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.test.history.ExperimentPaperHistory;
import com.whaty.platform.test.question.TestQuestionType;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.string.encode.HtmlEncoder;

public class OracleExperimentPaperHistory extends ExperimentPaperHistory {
	
	public OracleExperimentPaperHistory() {
	}

	public OracleExperimentPaperHistory(String id) {
		String sql = "select id, user_id,testpaper_id, to_char(test_date,'yyyy-mm-dd') as test_date,test_result,score from test_experimentpaper_history "
				+ "where id='" + id + "'";
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
		dbpool db = new dbpool();
		String sql = "insert into test_experimentpaper_history (id, user_id,testpaper_id, test_date, test_result,score) values ("
				+ "to_char(s_test_empaper_history_id.nextval), '"
				+ this.getUserId()
				+ "', '"
				+ this.getTestPaperId()
				+ "',sysdate, ?" + ", '" + this.getScore() + "')";
		int i = db.executeUpdate(sql, this.getTestResult());
		UserAddLog.setDebug("OracleExperimentPaperHistory.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update test_experimentpaper_history set testpaper_id='"
				+ this.getTestPaperId() + "', test_date=sysdate, test_result='"
				+ this.getTestResult() + "',score='" + this.getScore()
				+ "',ischeck = '" + this.getStatus() + "' where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleExperimentPaperHistory.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "delete from test_experimentpaper_history where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleExperimentPaperHistory.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
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

					Element subEle = answer.element("subitem");
					Iterator itSub = subEle.elementIterator("item");
					while (itSub.hasNext()) {
						Element subItem = (Element) itSub.next();
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
						Element sScoreEleSub = subItem.element("sscore");
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
		xml += "<totalscore>" + totalScore
				+ "</totalscore>";
		String totalNote = (String) map.get("totalNote");
		if (totalNote == null)
			totalNote = "";
		xml += "<totalnote>" + HtmlEncoder.encode(totalNote)
				+ "</totalnote>";
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
				if (uAnswer == null || uAnswer.equals("")
						|| uAnswer.equals("null"))
					uAnswer = "";
				if (sAnswer == null || sAnswer.equals("")
						|| sAnswer.equals("null"))
					sAnswer = "";
				if (title == null || title.equals("") || title.equals("null"))
					title = "";
				if (type == null || type.equals("") || type.equals("null"))
					type = "";
				if (sScore == null || sScore.equals("")
						|| sScore.equals("null"))
					sScore = "0";
				if (uScore == null || uScore.equals("")
						|| uScore.equals("null"))
					uScore = "0";
				if (note == null || note.equals("") || note.equals("null"))
					note = "";
				xml += "<item><id>" + id
						+ "</id><type>" + type
						+ "</type><title>" + HtmlEncoder.encode(title) + "</title><sanswer>"
						+ HtmlEncoder.encode(sAnswer)
						+ "</sanswer><uanswer>"
						+ HtmlEncoder.encode(uAnswer)
						+ "</uanswer><sscore>"
						+ HtmlEncoder.encode(sScore)
						+ "</sscore><uscore>"
						+ HtmlEncoder.encode(uScore)
						+ "</uscore><note>" + HtmlEncoder.encode(note)
						+ "</note></item>";
			} else {
				xml += "<item><id>" + id + "</id><type>"
						+ type + "</type>";
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
				xml += "<title>" + HtmlEncoder.encode(title)
						+ "</title>";
				sScore = (String) sScoreList.get(0);
				if (sScore == null || sScore.equals("")
						|| sScore.equals("null"))
					sScore = "0";
				xml += "<sscore>" + HtmlEncoder.encode(sScore)
						+ "</sscore>";
				uScore = (String) uScoreList.get(0);
				if (uScore == null || uScore.equals("")
						|| uScore.equals("null"))
					uScore = "0";
				xml += "<uscore>" + HtmlEncoder.encode(uScore)
						+ "</uscore>";
				if (noteList != null && noteList.size() > 0)
					note = (String) noteList.get(0);
				else
					note = "";
				if (note == null || note.equals("") || note.equals("null"))
					note = "";
				xml += "<note>" + HtmlEncoder.encode(note)
						+ "</note><subitem>";

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
					if (uAnswer == null || uAnswer.equals("")
							|| uAnswer.equals("null"))
						uAnswer = "";
					if (sAnswer == null || sAnswer.equals("")
							|| sAnswer.equals("null"))
						sAnswer = "";
					if (title == null || title.equals("")
							|| title.equals("null"))
						title = "";
					if (sScore == null || sScore.equals("")
							|| sScore.equals("null"))
						sScore = "0";
					if (uScore == null || uScore.equals("")
							|| uScore.equals("null"))
						uScore = "0";
					if (note == null || note.equals("") || note.equals("null"))
						note = "";
					xml += "<item><id>" + k + "</id><title>"
							+ HtmlEncoder.encode(title)
							+ "</title><sanswer>"
							+ HtmlEncoder.encode(sAnswer)
							+ "</sanswer><uanswer>"
							+ HtmlEncoder.encode(uAnswer)
							+ "</uanswer><sscore>"
							+ HtmlEncoder.encode(sScore)
							+ "</sscore><uscore>"
							+ HtmlEncoder.encode(uScore)
							+ "</uscore><note>"
							+ HtmlEncoder.encode(note)
							+ "</note></item>";
				}
				xml += "</subitem></item>";
			}
		}
		xml += "</answers>";
		this.setTestResult(xml);
	}
}
