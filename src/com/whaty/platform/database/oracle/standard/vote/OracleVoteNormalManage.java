package com.whaty.platform.database.oracle.standard.vote;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.vote.basic.OracleVoteActivity;
import com.whaty.platform.database.oracle.standard.vote.basic.OracleVoteList;
import com.whaty.platform.database.oracle.standard.vote.basic.OracleVotePaper;
import com.whaty.platform.database.oracle.standard.vote.basic.OracleVoteQuestion;
import com.whaty.platform.database.oracle.standard.vote.basic.OracleVoteRecord;
import com.whaty.platform.database.oracle.standard.vote.basic.OracleVoteSuggest;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.vote.VoteNormalManage;
import com.whaty.platform.vote.basic.VotePaper;
import com.whaty.platform.vote.basic.VoteQuestion;
import com.whaty.platform.vote.basic.VoteQuestionType;
import com.whaty.platform.vote.util.exception.VoteException;
import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.string.StrManage;
import com.whaty.util.string.StrManageFactory;

public class OracleVoteNormalManage extends VoteNormalManage {

	public List getVoteQuestions(String votePaperId) throws PlatformException {
		OracleVotePaper paper = new OracleVotePaper(votePaperId);
		try {
			return paper.getVoteQuestions();
		} catch (VoteException e) {
			throw new PlatformException("getVoteQuestions error!("
					+ e.toString() + ")");
		}
	}

	public List getActiveVotePapers() throws PlatformException {
		OracleVoteList voteList = new OracleVoteList();
		List searchList = new ArrayList();
		searchList.add(new SearchProperty("active", "1", "=num"));
		List orderList = new ArrayList();
		orderList.add(new OrderProperty("id"));
		try {
			return voteList.getVotePaperList(null, searchList, orderList);
		} catch (VoteException e) {
			// TODO Auto-generated catch block
			throw new PlatformException("getActiveVotePapers error!("
					+ e.toString() + ")");
		}
	}

	public List getActiveVotePapers(String voteType) throws PlatformException {
		OracleVoteList voteList = new OracleVoteList();
		List searchList = new ArrayList();
		searchList.add(new SearchProperty("active", "1", "=num"));
		searchList.add(new SearchProperty("type", voteType, "="));
		List orderList = new ArrayList();
		orderList.add(new OrderProperty("id"));
		try {
			return voteList.getVotePaperList(null, searchList, orderList);
		} catch (VoteException e) {
			// TODO Auto-generated catch block
			throw new PlatformException("getActiveVotePapers error!("
					+ e.toString() + ")");
		}
	}

	public List getActiveCourseVotePapers(Page page, String openCourseId)
			throws PlatformException {
		OracleVoteList voteList = new OracleVoteList();
		List searchList = new ArrayList();
		searchList.add(new SearchProperty("active", "1", "=num"));
		searchList.add(new SearchProperty("type", "COURSE", "="));
		searchList.add(new SearchProperty("keywords", openCourseId, "like"));
		List orderList = new ArrayList();
		orderList.add(new OrderProperty("id"));
		try {
			return voteList.getVotePaperList(page, searchList, orderList);
		} catch (VoteException e) {
			// TODO Auto-generated catch block
			throw new PlatformException("getActiveVotePapers error!("
					+ e.toString() + ")");
		}
	}

	public int getActiveCourseVotePapersNum(String openCourseId)
			throws PlatformException {
		OracleVoteList voteList = new OracleVoteList();
		List searchList = new ArrayList();
		searchList.add(new SearchProperty("active", "1", "=num"));
		searchList.add(new SearchProperty("type", "COURSE", "="));
		searchList.add(new SearchProperty("keywords", openCourseId, "like"));
		try {
			return voteList.getVotePaperNum(searchList);
		} catch (VoteException e) {
			// TODO Auto-generated catch block
			throw new PlatformException("getActiveVotePapers error!("
					+ e.toString() + ")");
		}
	}

	public void votePaper(String votePaperId, HttpServletRequest request)
			throws PlatformException {

		OracleVotePaper paper = new OracleVotePaper(votePaperId);
		String sessionId = request.getSession().getId();
		String Ip = request.getRemoteAddr();
		StrManage strManage = StrManageFactory.creat();
		String suggest = "";
		OracleVoteSuggest voteSuggest = new OracleVoteSuggest();
		if (paper.isCanSuggest()) {

			try {
				voteSuggest.setNote(strManage.fixNull(request
						.getParameter("suggest")));
			} catch (WhatyUtilException e) {
				// TODO Auto-generated catch block
				throw new PlatformException("votePaper error!(" + e.toString()
						+ ")");
			}
			voteSuggest.setIp(Ip);
			voteSuggest.setVotePaperId(votePaperId);
		}
		OracleVoteRecord record = new OracleVoteRecord();
		record.setIp(Ip);
		record.setPaperId(paper.getId());
		record.setSessionId(sessionId);
		try {
			List questions = paper.getVoteQuestions();

//			if (questions.size() < 1)
//				throw new PlatformException("votePaper error!(no question)");
//			else {
				boolean isSel = false;
				if(questions.size() < 1){
					isSel = true;
				}
				for (int i = 0; i < questions.size(); i++) {
					VoteQuestion question = (VoteQuestion) questions.get(i);
					int itemTotalNum = question.getItemTotalNum();
					String questionType = request.getParameter("questionType"
							+ (i + 1));
					if (questionType.equals(VoteQuestionType.SINGLE)) {
						int itemNum = Integer.parseInt((request.getParameter("radio" + (i + 1)))==null?"0":request.getParameter("radio" + (i + 1)));
						if (itemNum >= 1 && itemNum <= itemTotalNum){
							question.addQuestionResult(1, itemNum);
							isSel = true; //表明致少选了一项
						}

					} else if (questionType.equals(VoteQuestionType.MULTI)) {
						String[] abc = request.getParameterValues("checkbox"
								+ (i + 1));
						
						for (int j = 0; j < (abc!= null ?abc.length:0); j++) {
							if (abc[j] != null){
								question.addQuestionResult(1, Integer
										.parseInt(abc[j]));
								isSel = true; //表明致少选了一项.
							}
						}
					}

				}
				if (isSel && paper.isCanSuggest()) {
					String note = voteSuggest.getNote();
					if(questions.size() < 1 && (note == null || "".equals(note))){
						throw new VoteException("建议为空，添加失败");
					}
					if(note != null && !"".equals(note)){
						voteSuggest.add();
					}
				}
				if(isSel){
					record.add();
				}else{
					throw new VoteException("请至少选择一项再提交!");
				}

//			}

		} catch (VoteException e) {
			// TODO Auto-generated catch block
			throw new PlatformException(e.getMessage());
		}

	}

	public VotePaper getVotePaper(String votePaperId) throws PlatformException {
		return new OracleVotePaper(votePaperId);
	}

	public VoteQuestion getVoteQuestion(String voteQuestionId)
			throws PlatformException {
		return new OracleVoteQuestion(voteQuestionId);
	}

	public boolean checkSessionAndIp(VotePaper paper, HttpServletRequest request)
			throws PlatformException {
		boolean check = false;
		String sessionId = null;
		String Ip = null;
		if (paper.isLimitDiffSession()) {
			sessionId = request.getSession().getId();
			check = true;
		}
		if (paper.isLimitDiffIP()) {
			Ip = request.getRemoteAddr();
			check = true;
		}
		if (check) {
			OracleVoteActivity activity = new OracleVoteActivity();
			try {
				return activity.checkSessionAndIp(paper.getId(), sessionId, Ip);
			} catch (VoteException e) {
				throw new PlatformException("checkSessionAndIp error!("
						+ e.toString() + ")");
			}
		} else
			return false;

	}

}
