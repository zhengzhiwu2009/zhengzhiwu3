package com.whaty.platform.database.oracle.standard.vote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.vote.basic.OracleVoteActivity;
import com.whaty.platform.database.oracle.standard.vote.basic.OracleVoteList;
import com.whaty.platform.database.oracle.standard.vote.basic.OracleVotePaper;
import com.whaty.platform.database.oracle.standard.vote.basic.OracleVoteQuestion;
import com.whaty.platform.database.oracle.standard.vote.basic.OracleVoteSuggest;
import com.whaty.platform.database.oracle.standard.vote.user.OracleVoteManagerPriv;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserLog;
import com.whaty.platform.vote.VoteManage;
import com.whaty.platform.vote.basic.VotePaper;
import com.whaty.platform.vote.basic.VoteQuestion;
import com.whaty.platform.vote.basic.VoteSuggest;
import com.whaty.platform.vote.user.VoteManagerPriv;
import com.whaty.platform.vote.util.exception.VoteException;
import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.string.StrManage;
import com.whaty.util.string.StrManageFactory;

public class OracleVoteManage extends VoteManage {
	public OracleVoteManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleVoteManage(VoteManagerPriv priv) {
		this.setPriv(priv);
	}

	public void addVotePaper(HttpServletRequest request)
			throws PlatformException {
		if(this.getPriv().addVotePaper == 1){

			String paperTitle;
			String paperPicTitle;
			String paperNote;
			Date startDate;
			Date endDate;
			String type;
			String keywords = "";
			StrManage strManage = StrManageFactory.creat();
			try {
				paperTitle = strManage.fixNull(request.getParameter("paper_title"));
				paperPicTitle = strManage.fixNull(request
						.getParameter("paper_pictitle"));
				paperNote = strManage.fixNull(request.getParameter("body"));
				startDate = strManage.strToDate(request.getParameter("startDate"),
						"yyyy-MM-dd");
				endDate = strManage.strToDate(request.getParameter("endDate"),
						"yyyy-MM-dd");
				type = strManage.fixNull(request.getParameter("type"));
				if (type.equals("NORMAL"))
					keywords = strManage.fixNull(request
							.getParameter("normal_keywords"));
				else {
					String semester_id = strManage.fixNull(request
							.getParameter("semester_id"));
					String[] keywordss = request
							.getParameterValues("course_keywords");
					keywords = "|semester_" + semester_id + "|";
					for (int i = 0; i < keywordss.length; i++)
						keywords += "|" + strManage.fixNull(keywordss[i]);
					keywords += "|";
				}
			} catch (WhatyUtilException e) {
				// TODO Auto-generated catch block
				throw new PlatformException("addVotePaper error!(" + e.toString()
						+ ")");
			}
			String active = request.getParameter("active");
			boolean isactive = false;
			if (active != null && active.equals("1"))
				isactive = true;
			String canSuggest = request.getParameter("canSuggest");
			boolean isCanSuggest = false;
			if (canSuggest != null && canSuggest.equals("1"))
				isCanSuggest = true;
			String viewSuggest = request.getParameter("viewSuggest");
			boolean isViewSuggest = false;
			if (viewSuggest != null && viewSuggest.equals("1"))
				isViewSuggest = true;
			String limitIp = request.getParameter("limitIp");
			boolean isLimitIp = false;
			if (limitIp != null && limitIp.equals("1"))
				isLimitIp = true;
			String limitSession = request.getParameter("limitSession");
			boolean isLimitSession = false;
			if (limitSession != null && limitSession.equals("1"))
				isLimitSession = true;
			OracleVotePaper paper = new OracleVotePaper();
			paper.setTitle(paperTitle);
			paper.setPicTitle(paperPicTitle);
			paper.setStartDate(startDate);
			paper.setEndDate(endDate);
			paper.setActive(isactive);
			paper.setCanSuggest(isCanSuggest);
			paper.setViewSuggest(isViewSuggest);
			paper.setLimitDiffIP(isLimitIp);
			paper.setLimitDiffSession(isLimitSession);
			paper.setNote(paperNote);
			paper.setType(type);
			paper.setKeywords(keywords);
			int suc =paper.add();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getVoteManagerPriv().getMessageId()
							+ "</whaty><whaty>BEHAVIOR$|$addVotePaper</whaty><whaty>STATUS$|$"
							+ suc 
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			if (suc < 1)
				throw new PlatformException("addVotePaper fail!");
			
		}else{
			throw new PlatformException("您没有添加投票的权限");
		}
	}

	public void addVoteQuestion(String votePaperId, HttpServletRequest request)
			throws PlatformException {
		
		if(this.getPriv().addVotePaper == 1){
			OracleVotePaper paper = new OracleVotePaper(votePaperId);
			OracleVoteQuestion question = new OracleVoteQuestion();
			StrManage strManage = StrManageFactory.creat();
			try {
				String questionBody = strManage.fixNull(request
						.getParameter("body"));
				List itemList = new ArrayList();
				itemList.add(strManage.fixNull(request.getParameter("item1")));
				itemList.add(strManage.fixNull(request.getParameter("item2")));
				itemList.add(strManage.fixNull(request.getParameter("item3")));
				itemList.add(strManage.fixNull(request.getParameter("item4")));
				itemList.add(strManage.fixNull(request.getParameter("item5")));
				itemList.add(strManage.fixNull(request.getParameter("item6")));
				itemList.add(strManage.fixNull(request.getParameter("item7")));
				itemList.add(strManage.fixNull(request.getParameter("item8")));
				itemList.add(strManage.fixNull(request.getParameter("item9")));
				itemList.add(strManage.fixNull(request.getParameter("item10")));
				itemList.add(strManage.fixNull(request.getParameter("item11")));
				itemList.add(strManage.fixNull(request.getParameter("item12")));
				itemList.add(strManage.fixNull(request.getParameter("item13")));
				itemList.add(strManage.fixNull(request.getParameter("item14")));
				itemList.add(strManage.fixNull(request.getParameter("item15")));
				int itemNum = Integer.parseInt(strManage.fixNull(request
						.getParameter("itemNum")));
				String questionType = strManage.fixNull(request
						.getParameter("question_type"));
				question.setQuestionBody(questionBody);
				question.setQuestionType(questionType);
				question.setItemTotalNum(itemNum);
				question.setQuestionItems(itemList);
				try {
					paper.addVoteQuestion(question);
					UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.getVoteManagerPriv().getMessageId()
									+ "</whaty><whaty>BEHAVIOR$|$addVoteQuestion</whaty><whaty>STATUS$|$"
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
				} catch (VoteException e) {
					throw new PlatformException("addVoteQuestion error!("
							+ e.toString() + ")");
				}
			} catch (WhatyUtilException e) {
				throw new PlatformException("addVoteQuestion error!("
						+ e.toString() + ")");

			}
			
		}else{
			throw new PlatformException("您没有添加操作权限");
		}
		

	}

	public void clearVotePaperResults(String votePaperId)
			throws PlatformException {
		// TODO Auto-generated method stub

	}

	public void clearVoteQuestionResult(String voteQuestionId)
			throws PlatformException {
		// TODO Auto-generated method stub

	}

	public void deleteVotePaper(String id) throws PlatformException {
		if(this.getPriv().addVotePaper == 1){
			
			OracleVotePaper paper = new OracleVotePaper();
			paper.setId(id);
			int suc =paper.delete();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getVoteManagerPriv().getMessageId()
							+ "</whaty><whaty>BEHAVIOR$|$addVoteQuestion</whaty><whaty>STATUS$|$"
							+ suc 
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			if (suc < 1)
				throw new PlatformException("deleteVotePaper fail!");
		}else{
			throw new PlatformException("您没有操作权限");
		}
	}

	public void deleteVoteQuestions(HttpServletRequest request)
			throws PlatformException {
		// TODO Auto-generated method stub

	}

	public void deleteVoteSuggests(HttpServletRequest request)
			throws PlatformException {

	}

	public List getVotePapers(Page page, HttpServletRequest request)
			throws PlatformException {
		OracleVoteList voteList = new OracleVoteList();
		List searchList = new ArrayList();
		List orderList = new ArrayList();
		String paper_title = request.getParameter("paper_title");
		if (paper_title != null && paper_title.length() > 0
				&& (!paper_title.equalsIgnoreCase("null")))
			searchList.add(new SearchProperty("title", paper_title, "like"));
		orderList.add(new OrderProperty("id"));
		try {
			return voteList.getVotePaperList(page, searchList, orderList);
		} catch (VoteException e) {
			throw new PlatformException("getVotePaper error!(" + e.toString()
					+ ")");
		}
	}

	public List getVoteQuestions(String votePaperId, HttpServletRequest request)
			throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateVotePaper(String id, HttpServletRequest request)
			throws PlatformException {
		
		if(this.getPriv().addVotePaper == 1){
			
			String paperTitle;
			String paperPicTitle;
			String paperNote;
			Date startDate;
			Date endDate;
			String type;
			String keywords = "";
			StrManage strManage = StrManageFactory.creat();
			try {
				paperTitle = strManage.fixNull(request.getParameter("paper_title"));
				paperPicTitle = strManage.fixNull(request
						.getParameter("paper_pictitle"));
				paperNote = strManage.fixNull(request.getParameter("body"));
				startDate = strManage.strToDate(request.getParameter("startDate"),
						"yyyy-MM-dd");
				endDate = strManage.strToDate(request.getParameter("endDate"),
						"yyyy-MM-dd");
				type = strManage.fixNull(request.getParameter("type"));
				if (type.equals("NORMAL"))
					keywords = strManage.fixNull(request
							.getParameter("normal_keywords"));
				else {
					String semester_id = strManage.fixNull(request
							.getParameter("semester_id"));
					String[] keywordss = request
							.getParameterValues("course_keywords");
					keywords = "|semester_" + semester_id + "|";
					for (int i = 0; i < keywordss.length; i++)
						keywords += "|" + strManage.fixNull(keywordss[i]);
					keywords += "|";
				}
			} catch (WhatyUtilException e) {
				// TODO Auto-generated catch block
				throw new PlatformException("addVotePaper error!(" + e.toString()
						+ ")");
			}
			String active = request.getParameter("active");
			boolean isactive = false;
			if (active != null && active.equals("1"))
				isactive = true;
			String canSuggest = request.getParameter("canSuggest");
			boolean isCanSuggest = false;
			if (canSuggest != null && canSuggest.equals("1"))
				isCanSuggest = true;
			String viewSuggest = request.getParameter("viewSuggest");
			boolean isViewSuggest = false;
			if (viewSuggest != null && viewSuggest.equals("1"))
				isViewSuggest = true;
			String limitIp = request.getParameter("limitIp");
			boolean isLimitIp = false;
			if (limitIp != null && limitIp.equals("1"))
				isLimitIp = true;
			String limitSession = request.getParameter("limitSession");
			boolean isLimitSession = false;
			if (limitSession != null && limitSession.equals("1"))
				isLimitSession = true;

			OracleVotePaper paper = new OracleVotePaper(id);
			paper.setTitle(paperTitle);
			paper.setPicTitle(paperPicTitle);
			paper.setStartDate(startDate);
			paper.setEndDate(endDate);
			paper.setActive(isactive);
			paper.setCanSuggest(isCanSuggest);
			paper.setViewSuggest(isViewSuggest);
			paper.setLimitDiffIP(isLimitIp);
			paper.setLimitDiffSession(isLimitSession);
			paper.setNote(paperNote);
			paper.setType(type);
			paper.setKeywords(keywords);
			int suc =paper.update(); 
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getVoteManagerPriv().getMessageId()
							+ "</whaty><whaty>BEHAVIOR$|$updateVotePaper</whaty><whaty>STATUS$|$"
							+ suc 
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			if (suc<1)
				throw new PlatformException("updateVotePaper fail!");
		}else{
			throw new PlatformException("您没有更新操作权限");
		}
		
	

	}

	public void updateVoteQuestion(String question_id,
			HttpServletRequest request) throws PlatformException {
		
		if(this.getPriv().addVotePaper==1){
			OracleVoteQuestion question = new OracleVoteQuestion(question_id);
			StrManage strManage = StrManageFactory.creat();
			try {
				String questionBody = strManage.fixNull(request
						.getParameter("body"));
				List itemList = new ArrayList();
				itemList.add(strManage.fixNull(request.getParameter("item1")));
				itemList.add(strManage.fixNull(request.getParameter("item2")));
				itemList.add(strManage.fixNull(request.getParameter("item3")));
				itemList.add(strManage.fixNull(request.getParameter("item4")));
				itemList.add(strManage.fixNull(request.getParameter("item5")));
				itemList.add(strManage.fixNull(request.getParameter("item6")));
				itemList.add(strManage.fixNull(request.getParameter("item7")));
				itemList.add(strManage.fixNull(request.getParameter("item8")));
				itemList.add(strManage.fixNull(request.getParameter("item9")));
				itemList.add(strManage.fixNull(request.getParameter("item10")));
				itemList.add(strManage.fixNull(request.getParameter("item11")));
				itemList.add(strManage.fixNull(request.getParameter("item12")));
				itemList.add(strManage.fixNull(request.getParameter("item13")));
				itemList.add(strManage.fixNull(request.getParameter("item14")));
				itemList.add(strManage.fixNull(request.getParameter("item15")));
				int itemNum = Integer.parseInt(strManage.fixNull(request
						.getParameter("itemNum")));
				String questionType = strManage.fixNull(request
						.getParameter("question_type"));
				question.setQuestionBody(questionBody);
				question.setQuestionType(questionType);
				question.setItemTotalNum(itemNum);
				question.setQuestionItems(itemList);
				try {
					question.update();
					UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.getVoteManagerPriv().getMessageId()
									+ "</whaty><whaty>BEHAVIOR$|$updateVoteQuestion</whaty><whaty>STATUS$|$"
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
				} catch (VoteException e) {
					throw new PlatformException("updateVoteQuestion error!("
							+ e.toString() + ")");
				}
			} catch (WhatyUtilException e) {
				throw new PlatformException("addVoteQuestion error!("
						+ e.toString() + ")");

			}
		}else{
			throw new PlatformException("您没有操作权限");
		}

	}

	public int getVotePaperNum(HttpServletRequest request)
			throws PlatformException {
		
		if(this.getPriv().getVotePaper==1){
			
			OracleVoteList voteList = new OracleVoteList();
			List searchList = new ArrayList();
			String paper_title = request.getParameter("paper_title");
			if (paper_title != null && paper_title.length() > 0
					&& (!paper_title.equalsIgnoreCase("null")))
				searchList.add(new SearchProperty("title", paper_title, "like"));
			try {
				return voteList.getVotePaperNum(searchList);
			} catch (VoteException e) {
				throw new PlatformException("getVotePaperNum error!("
						+ e.toString() + ")");
			}
			
		}else{
			throw new PlatformException("您没有浏览权限");
		}
		
		
	}

	public int getVoteSuggestNum(String votePaperId, HttpServletRequest request)
			throws PlatformException {
		
		if(this.getPriv().getVotePaper==1){
			
			OracleVoteList voteList = new OracleVoteList();
			List searchList = new ArrayList();
			searchList.add(new SearchProperty("paper_id", votePaperId, "="));
			searchList.add(new SearchProperty("note", request
					.getParameter("note"), "like"));
			try {
				return voteList.getVotePaperSuggestsNum(searchList);
			} catch (VoteException e) {
				throw new PlatformException("getVoteSuggestNum error!("
						+ e.toString() + ")");
			}
			
		}else{
			throw new PlatformException("您没有浏览的权限");
		}
		
	}

	public List getVoteSuggests(Page page, String votePaperId,
			HttpServletRequest request) throws PlatformException {
		
		if(this.getPriv().getVotePaper==1){
			OracleVoteList voteList = new OracleVoteList();
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			searchList.add(new SearchProperty("paper_id", votePaperId, "="));
			searchList.add(new SearchProperty("note", request
					.getParameter("note"), "like"));
			orderList.add(new OrderProperty("id","DESC"));
			try {
				return voteList.getVotePaperSuggests(page, searchList, orderList);
			} catch (VoteException e) {
				throw new PlatformException("getVoteSuggests error!("
						+ e.toString() + ")");
			}
		}else{
			throw new PlatformException("您没有浏览的权限");
		}
		
	}

	public VotePaper getVotePaper(String id) throws PlatformException {
		
		if(this.getPriv().getVotePaper==1){
			return new OracleVotePaper(id);
		}else{
			throw new PlatformException("您没有浏览的权限");
		}
	}
	
	public VoteQuestion getVoteQuestion(String id) throws PlatformException {
		
		if(this.getPriv().getVotePaper==1){
			return new OracleVoteQuestion(id);
		}else{
			throw new PlatformException("您没有浏览的权限");
		}
		
	}

	public VoteSuggest getVoteSuggest(String id) throws PlatformException {
		// TODO Auto-generated method stub
		if(this.getPriv().getVotePaper==1){
			return new OracleVoteSuggest(id);
		}else{
			throw new PlatformException("您没有浏览的权限");
		}
	}

	public void activeVotePapers(HttpServletRequest request, String active)
			throws PlatformException {
		
		if(this.getPriv().addVotePaper==1){
			
			String paperId = request.getParameter("paper_id");
			String[] paperIds = request.getParameterValues("listMultiAction");
			boolean flag = false;
			if (active != null && active.equals("1"))
				flag = true;
			List paperIdList = new ArrayList();
			if (paperId != null && paperId.length() > 0) {
				paperIdList.add(paperId);
			}
			if (paperIds != null) {
				for (int i = 0; i < paperIds.length; i++) {
					paperIdList.add(paperIds[i]);
				}
			}
			OracleVoteActivity activity = new OracleVoteActivity();
			try {
				activity.activeVotePapers(paperIdList, flag);
				UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getVoteManagerPriv().getMessageId()
								+ "</whaty><whaty>BEHAVIOR$|$activeVotePapers</whaty><whaty>STATUS$|$"
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
			} catch (VoteException e) {
				throw new PlatformException("activeVotePapers error!("
						+ e.toString() + ")");
			}
		}else{
			throw new PlatformException("您没有操作的权限");
		}
		

	}

	public void deleteVoteQuestion(String paper_id, String questionId)
			throws PlatformException {
		
		if(this.getPriv().addVotePaper==1){
			
			OracleVotePaper paper = new OracleVotePaper(paper_id);
			List questionIds = new ArrayList();
			questionIds.add(questionId);
			try {
				paper.removeVoteQuestion(questionIds);
				UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getVoteManagerPriv().getMessageId()
								+ "</whaty><whaty>BEHAVIOR$|$deleteVoteQuestion</whaty><whaty>STATUS$|$"
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
			} catch (VoteException e) {
				throw new PlatformException("deleteVoteQuestion error!("
						+ e.toString() + ")");
			}
		}else{
			throw new PlatformException("您没有操作的权限");
		}
		

	}

	public void activeVoteSuggests(HttpServletRequest request, String active)
			throws PlatformException {
		
		if(this.getPriv().addVotePaper==1){
			
			String suggestId = request.getParameter("suggest_id");
			String[] suggestIds = request.getParameterValues("listMultiAction");
			boolean flag = false;
			if (active != null && active.equals("1"))
				flag = true;
			List suggestIdList = new ArrayList();
			if (suggestId != null && suggestId.length() > 0) {
				suggestIdList.add(suggestId);
			}
			if (suggestIds != null) {
				for (int i = 0; i < suggestIds.length; i++) {
					suggestIdList.add(suggestIds[i]);
				}
			}
			OracleVoteActivity activity = new OracleVoteActivity();
			try {
				activity.activeVoteSuggests(suggestIdList, flag);
				UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getVoteManagerPriv().getMessageId()
								+ "</whaty><whaty>BEHAVIOR$|$activeVoteSuggests</whaty><whaty>STATUS$|$"
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
			} catch (VoteException e) {
				throw new PlatformException("activeVoteSuggests error!("
						+ e.toString() + ")");
			}
			
		}else{
			throw new PlatformException("您没有操作的权限");
		}
		

	}

	public void deleteVoteSuggest(String id) throws PlatformException {
		
		if(this.getPriv().addVotePaper==1){
			
			OracleVoteSuggest suggest = new OracleVoteSuggest();
			suggest.setId(id);
			int suc = suggest.delete();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getVoteManagerPriv().getMessageId()
							+ "</whaty><whaty>BEHAVIOR$|$deleteVoteSuggest</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			if (suc< 1)
				throw new PlatformException("deleteVoteSuggests fail!");

		}else{
			throw new PlatformException("您没有操作的权限");
		}
		
		
	}

//	public WhatyEditorConfig getWhatyEditorConfig(HttpSession session)
//			throws PlatformException {
//		ServletContext application = session.getServletContext();
//		VoteConfig config = (VoteConfig) application.getAttribute("voteConfig");
//		PlatformConfig platformConfig = (PlatformConfig) application
//				.getAttribute("platformConfig");
//		WhatyEditorConfig editorConfig = new WhatyEditorConfig();
//		editorConfig.setAppRootURI(platformConfig.getPlatformWebAppUriPath());
//		editorConfig.setEditorRefURI("WhatyEditor/");
//		editorConfig.setEditorURI(platformConfig.getPlatformWebAppUriPath()
//				+ "WhatyEditor/");
//		editorConfig.setUploadAbsPath(config.getVoteWebIncomingAbsPath()
//				+ File.separator);
//		editorConfig.setUploadURI(config.getVoteWebIncomingUriPath());
//		return editorConfig;
//	}

	public VoteManagerPriv getVoteManagerPriv()throws PlatformException {
		
		if(this.getPriv().addVotePaper==1){
			
			OracleVoteManagerPriv voteManagerPriv = new OracleVoteManagerPriv();
			voteManagerPriv.setMessageId(this.getPriv().getMessageId());
			return voteManagerPriv;
			
		}else{
			throw new PlatformException("您没有操作的权限");
		}
		
	}

}
