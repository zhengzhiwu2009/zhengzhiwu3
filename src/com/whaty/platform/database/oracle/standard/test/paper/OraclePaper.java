package com.whaty.platform.database.oracle.standard.test.paper;

import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.test.paper.Paper;
import com.whaty.platform.util.log.UserDeleteLog;

public class OraclePaper extends Paper {

	public List getPaperQuestion() throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	public void addPaperQuestion(List PaperQuestion) throws PlatformException {
		// TODO Auto-generated method stub

	}

	public void removePaperQuestion(List PaperQuestion)
			throws PlatformException {
		// TODO Auto-generated method stub

	}

	public int setActive() {
		// TODO �Զ���ɷ������
		return 0;
	}

	public int cancelActive() {
		// TODO �Զ���ɷ������
		return 0;
	}

	public int reverseActive() {
		// TODO �Զ���ɷ������
		return 0;
	}

	public int removePaperQuestions() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from test_paperquestion_info where testpaper_id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OraclePaper.removePaperQuestions() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int removePaperQuestions(String questionIds)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		if (questionIds.equals(""))
			sql = "delete from test_paperquestion_info where testpaper_id='"
					+ this.getId() + "'";
		else
			sql = "delete from test_paperquestion_info where testpaper_id='"
					+ this.getId() + "' and id not in (" + questionIds + ")";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OraclePaper.removePaperQuestions(String questionIds) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
