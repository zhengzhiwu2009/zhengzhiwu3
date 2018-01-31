package com.whaty.platform.database.oracle.standard.campus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.CampusInfoManage;
import com.whaty.platform.campus.base.CampusNews;
import com.whaty.platform.campus.base.CampusNewsStatus;
import com.whaty.platform.campus.user.CampusInfoManagerPriv;
import com.whaty.platform.campus.user.Docent;
import com.whaty.platform.database.oracle.standard.campus.base.OracleCampusInfoList;
import com.whaty.platform.database.oracle.standard.campus.base.OracleCampusNews;
import com.whaty.platform.database.oracle.standard.campus.user.OracleDocent;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserLog;

/**
 * @author chenjian
 * 
 */
public class OracleCampusInfoManage extends CampusInfoManage {
	
	CampusInfoManagerPriv campusInfoManagerPriv;

	public OracleCampusInfoManage() {

	}

	public OracleCampusInfoManage(CampusInfoManagerPriv campusInfoManagerPriv) {
		this.campusInfoManagerPriv = campusInfoManagerPriv;
	}

	public int addCampusNews(String title, String shortTitle, String reporter,
			String reportDate, String submitManagerId,
			String submitManagerName, String body, String isPop,
			String isActive, String isTop, String topSequence, String picLink,
			String docentId, String docentName, String detail,
			String photo_link, String courseLink) throws PlatformException {
		int i = 0;
		Docent docent = new OracleDocent();
		if (docent.isExist(docentId) > 0) {
			throw new PlatformException("�ý�ʦ���Ѿ����ڣ�");
		} else {
			docent.setId(docentId);
			docent.setName(docentName);
			docent.setDetail(detail);
			docent.setPhoto_link(photo_link);

			CampusNews news = new OracleCampusNews();
			news.setTitle(title);
			news.setShortTitle(shortTitle);
			news.setReporter(reporter);
			news.setReportDate(reportDate);
			news.setBody(body);
			news.setPicLink(picLink);
			news.setSubmitManagerId(submitManagerId);
			news.setSubmitManagerName(submitManagerName);
			news.setDocent_Key(docentId);
			news.setDecentInfo(docent);
			news.setCourse_Link(courseLink);

			CampusNewsStatus campusNewsStatus = new CampusNewsStatus();

			if (isPop == null || isPop.length() < 1 || !isPop.equals("1")) {
				campusNewsStatus.setPop(false);
			} else {
				campusNewsStatus.setPop(true);
			}
			if (isActive == null || isActive.length() < 1
					|| !isActive.equals("1")) {
				campusNewsStatus.setActive(false);
			} else {
				campusNewsStatus.setActive(true);
			}
			if (isTop == null || isTop.length() < 1 || !isTop.equals("1")) {
				campusNewsStatus.setTop(false);
			} else {
				campusNewsStatus.setTop(true);
				try {
					campusNewsStatus.setTopSequence(Integer
							.parseInt(topSequence));
				} catch (NumberFormatException nfe) {
					campusNewsStatus.setTopSequence(0);
				}
			}
			news.setStatus(campusNewsStatus);
			i = news.add() + docent.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.campusInfoManagerPriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addCampusNews</whaty><whaty>STATUS$|$"
									+ i
									+ "</whaty><whaty>NOTES$|$addCampusNews</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		}
		return i;
	}

	public int addCampusNews(String title, String color, String shortTitle,
			String short_color, String reporter, String reportDate,
			String submitManagerId, String submitManagerName, String body,
			String isPop, String isActive, String isTop, String topSequence,
			String picLink, String docentId, String docentName, String detail,
			String photo_link, String courseLink) throws PlatformException {

		if (color != null && !color.equals("null") && !color.equals(""))
			title = "<font color=" + color + ">" + title + "</font>";
		if (short_color != null && !short_color.equals("null")
				&& !short_color.equals(""))
			shortTitle = "<font color=" + short_color + ">" + shortTitle
					+ "</font>";

		int sub = addCampusNews(title, shortTitle, reporter, reportDate,
				submitManagerId, submitManagerName, body, isPop, isActive,
				isTop, topSequence, picLink, docentId, docentName, detail,
				photo_link, courseLink);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.campusInfoManagerPriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$addCampusNews</whaty><whaty>STATUS$|$"
								+ sub
								+ "</whaty><whaty>NOTES$|$addCampusNews</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;
	}

	public int updateCampusNews(String id, String shortTitle, String title,
			String reporter, String reportDate, String submitManagerId,
			String submitManagerName, String body, String isPop,
			String isActive, String isTop, String topSequence, String picLink,
			String docentId, String docentName, String detail,
			String photo_link, String courseLink) throws PlatformException {
		Docent oracleDocent = new OracleDocent(docentId);
		oracleDocent.setName(docentName);
		oracleDocent.setDetail(detail);
		if (photo_link != null && !photo_link.equals("")) {
			oracleDocent.setPhoto_link(photo_link);
		}
		OracleCampusNews news = new OracleCampusNews(id);
		news.setDocent_Key(docentId);
		news.setTitle(title);
		news.setShortTitle(shortTitle);
		news.setReporter(reporter);
		news.setReportDate(reportDate);
		news.setBody(body);
		news.setCourse_Link(courseLink);
		if (picLink != null && !picLink.equals("")) {
			news.setPicLink(picLink);
		}
		news.setSubmitManagerId(submitManagerId);
		news.setSubmitManagerName(submitManagerName);
		CampusNewsStatus newsStatus = new CampusNewsStatus();
		if (isPop == null || isPop.length() < 1 || !isPop.equals("1")) {
			newsStatus.setPop(false);
		} else {
			newsStatus.setPop(true);
		}
		if (isActive == null || isActive.length() < 1 || !isActive.equals("1")) {
			newsStatus.setActive(false);
		} else {
			newsStatus.setActive(true);
		}
		if (isTop == null || isTop.length() < 1 || !isTop.equals("1")) {
			newsStatus.setTop(false);
		} else {
			newsStatus.setTop(true);
			try {
				newsStatus.setTopSequence(Integer.parseInt(topSequence));
			} catch (NumberFormatException nfe) {
				newsStatus.setTopSequence(0);
			}
		}
		news.setStatus(newsStatus);
		news.setConfirm("0");

		int sub = news.update() + oracleDocent.update();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.campusInfoManagerPriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$updateCampusNews</whaty><whaty>STATUS$|$"
								+ sub
								+ "</whaty><whaty>NOTES$|$updateCampusNews</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;
	}

	public int updateCampusNews(String id, String title, String color,
			String shortTitle, String short_color, String reporter,
			String reportDate, String submitManagerId,
			String submitManagerName, String body, String isPop,
			String isActive, String isTop, String topSequence, String picLink,
			String docentId, String docentName, String detail,
			String photo_link, String courseLink) throws PlatformException {
		if (color != null && !color.equals("null") && !color.equals(""))
			title = "<font color=" + color + ">" + title + "</font>";
		if (short_color != null && !short_color.equals("null")
				&& !short_color.equals(""))
			shortTitle = "<font color=" + short_color + ">" + shortTitle
					+ "</font>";

		int sub = updateCampusNews(id, shortTitle, title, reporter, reportDate,
				submitManagerId, submitManagerName, body, isPop, isActive,
				isTop, topSequence, picLink, docentId, docentName, detail,
				photo_link, courseLink);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.campusInfoManagerPriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$updateCampusNews</whaty><whaty>STATUS$|$"
								+ sub
								+ "</whaty><whaty>NOTES$|$updateCampusNews</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;
	}

	public int deleteCampusNews(String id) throws PlatformException {
		OracleCampusNews news = new OracleCampusNews(id);
		int sub = 0;
		if (news != null && news.getDocent_Key() != null
				&& !news.getDocent_Key().equals("")) {
			Docent oracleDocent = new OracleDocent();
			oracleDocent.setId(news.getDocent_Key());
			sub = oracleDocent.delete() + news.delete();
		}
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.campusInfoManagerPriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$deleteCampusNews</whaty><whaty>STATUS$|$"
								+ sub
								+ "</whaty><whaty>NOTES$|$deleteCampusNews</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;
	}

	public int deleteCampusNews(List ids) throws PlatformException {
		int count = 0;
		for (int i = 0; i < ids.size(); i++) {
			OracleCampusNews news = new OracleCampusNews((String) ids.get(i));
			if (news != null && news.getDocent_Key() != null
					&& !news.getDocent_Key().equals("")) {
				Docent oracleDocent = new OracleDocent();
				oracleDocent.setId(news.getDocent_Key());
				oracleDocent.delete();
				news.delete();
			}
			count++;
		}
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.campusInfoManagerPriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$deleteCampusNews</whaty><whaty>STATUS$|$"
								+ count
								+ "</whaty><whaty>NOTES$|$deleteCampusNews</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return count;
	}

	public void putTop(String id) throws PlatformException {
		OracleCampusNews news = new OracleCampusNews(id);
		news.putTop();
	}

	public int putTop(List ids) throws PlatformException {
		int count = 0;
		for (int i = 0; i < ids.size(); i++) {
			OracleCampusNews news = new OracleCampusNews((String) ids.get(i));
			if (news.getStatus().getIsTop()) {
				news.cancelTop();
			} else {
				news.putTop();
			}
			count++;
		}
		UserLog.setInfo("<whaty>USERID$|$"
				+ this.campusInfoManagerPriv.getManagerId()
				+ "</whaty><whaty>BEHAVIOR$|$putTop</whaty><whaty>STATUS$|$"
				+ count
				+ "</whaty><whaty>NOTES$|$putTop</whaty><whaty>LOGTYPE$|$"
				+ LogType.MANAGER + "</whaty><whaty>PRIORITY$|$"
				+ LogPriority.INFO + "</whaty>", new Date());
		return count;
	}

	public void putTop(String id, String topSequence) throws PlatformException {
		OracleCampusNews news = new OracleCampusNews(id);
		news.putTop(Integer.parseInt(topSequence));
	}

	public void cancelTop(String id) throws PlatformException {
		OracleCampusNews news = new OracleCampusNews(id);
		news.cancelTop();
	}

	public int cancelTop(List ids) throws PlatformException {
		int count = 0;
		for (int i = 0; i < ids.size(); i++) {
			OracleCampusNews news = new OracleCampusNews((String) ids.get(i));
			news.cancelTop();
			count++;
		}
		UserLog.setInfo("<whaty>USERID$|$"
				+ this.campusInfoManagerPriv.getManagerId()
				+ "</whaty><whaty>BEHAVIOR$|$cancelTop</whaty><whaty>STATUS$|$"
				+ count
				+ "</whaty><whaty>NOTES$|$cancelTop</whaty><whaty>LOGTYPE$|$"
				+ LogType.MANAGER + "</whaty><whaty>PRIORITY$|$"
				+ LogPriority.INFO + "</whaty>", new Date());
		return count;
	}

	public void active(String id) throws PlatformException {
		OracleCampusNews news = new OracleCampusNews(id);
		news.undoLock();
		UserLog.setInfo("<whaty>USERID$|$"
				+ this.campusInfoManagerPriv.getManagerId()
				+ "</whaty><whaty>BEHAVIOR$|$active</whaty><whaty>STATUS$|$"
				+ "</whaty><whaty>NOTES$|$active</whaty><whaty>LOGTYPE$|$"
				+ LogType.MANAGER + "</whaty><whaty>PRIORITY$|$"
				+ LogPriority.INFO + "</whaty>", new Date());
	}

	public int active(List ids) throws PlatformException {
		int count = 0;
		for (int i = 0; i < ids.size(); i++) {
			OracleCampusNews news = new OracleCampusNews((String) ids.get(i));
			if (news.getStatus().getIsActive())
				news.doLock();
			else
				news.undoLock();
			count++;
		}
		UserLog.setInfo("<whaty>USERID$|$"
				+ this.campusInfoManagerPriv.getManagerId()
				+ "</whaty><whaty>BEHAVIOR$|$active</whaty><whaty>STATUS$|$"
				+ count
				+ "</whaty><whaty>NOTES$|$active</whaty><whaty>LOGTYPE$|$"
				+ LogType.MANAGER + "</whaty><whaty>PRIORITY$|$"
				+ LogPriority.INFO + "</whaty>", new Date());
		return count;
	}

	public void unActive(String id) throws PlatformException {
		OracleCampusNews news = new OracleCampusNews(id);
		news.doLock();
		UserLog.setInfo("<whaty>USERID$|$"
				+ this.campusInfoManagerPriv.getManagerId()
				+ "</whaty><whaty>BEHAVIOR$|$unActive</whaty><whaty>STATUS$|$"
				+ "</whaty><whaty>NOTES$|$unActive</whaty><whaty>LOGTYPE$|$"
				+ LogType.MANAGER + "</whaty><whaty>PRIORITY$|$"
				+ LogPriority.INFO + "</whaty>", new Date());
	}

	public int unActive(List ids) throws PlatformException {
		int count = 0;
		for (int i = 0; i < ids.size(); i++) {
			OracleCampusNews news = new OracleCampusNews((String) ids.get(i));
			news.doLock();
			count++;
		}
		UserLog.setInfo("<whaty>USERID$|$"
				+ this.campusInfoManagerPriv.getManagerId()
				+ "</whaty><whaty>BEHAVIOR$|$unActive</whaty><whaty>STATUS$|$"
				+ count
				+ "</whaty><whaty>NOTES$|$unActive</whaty><whaty>LOGTYPE$|$"
				+ LogType.MANAGER + "</whaty><whaty>PRIORITY$|$"
				+ LogPriority.INFO + "</whaty>", new Date());
		return count;
	}

	public void putPop(String id) throws PlatformException {
		OracleCampusNews news = new OracleCampusNews(id);
		news.setPop();
	}

	public int putPop(List ids) throws PlatformException {
		int count = 0;
		for (int i = 0; i < ids.size(); i++) {
			OracleCampusNews news = new OracleCampusNews((String) ids.get(i));
			if (news.getStatus().getIsTop())
				news.cancelTop();
			else
				news.putTop();
			count++;
		}
		UserLog.setInfo("<whaty>USERID$|$"
				+ this.campusInfoManagerPriv.getManagerId()
				+ "</whaty><whaty>BEHAVIOR$|$putPop</whaty><whaty>STATUS$|$"
				+ count
				+ "</whaty><whaty>NOTES$|$putPop</whaty><whaty>LOGTYPE$|$"
				+ LogType.MANAGER + "</whaty><whaty>PRIORITY$|$"
				+ LogPriority.INFO + "</whaty>", new Date());
		return count;
	}

	public void cancelPop(String id) throws PlatformException {
		OracleCampusNews news = new OracleCampusNews(id);
		news.cancelPop();
	}

	public int cancelPop(List ids) throws PlatformException {
		int count = 0;
		for (int i = 0; i < ids.size(); i++) {
			OracleCampusNews news = new OracleCampusNews((String) ids.get(i));
			news.cancelPop();
			count++;
		}
		return count;
	}

	public int confirmNews(String managerId, String managerName, List newsIds,
			boolean confirmFlag) throws PlatformException {
		int count = 0;
		for (int i = 0; i < newsIds.size(); i++) {
			OracleCampusNews news = new OracleCampusNews((String) newsIds
					.get(i));
			if (news.getConfirm().equals("0"))
				news.confirmNews("1", managerId, managerName);
			else
				news.confirmNews("0", managerId, managerName);
			count++;
			// news.update();
		}
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.campusInfoManagerPriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$confirmNews</whaty><whaty>STATUS$|$"
								+ count
								+ "</whaty><whaty>NOTES$|$confirmNews</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return count;
	}

	@Override
	public List getNewsList(Page page, String isactive, String rep_date_box,
			String title_box, String isTop, String isconfirm, String isPop)
			throws PlatformException {
		OracleCampusInfoList oracleInfoList = new OracleCampusInfoList();
		List srhPropList = new ArrayList();
		SearchProperty searchProp = new SearchProperty("isactive", isactive,
				"=");
		srhPropList.add(searchProp);
		searchProp = new SearchProperty("report_date", rep_date_box, "like");
		srhPropList.add(searchProp);
		searchProp = new SearchProperty("title", title_box, "like");
		srhPropList.add(searchProp);
		searchProp = new SearchProperty("istop", isTop, "=");
		srhPropList.add(searchProp);
		searchProp = new SearchProperty("isconfirm", isconfirm, "=");
		srhPropList.add(searchProp);
		searchProp = new SearchProperty("ispop", isPop, "=");
		srhPropList.add(searchProp);

		List orderPropList = new ArrayList();
		orderPropList.add(new OrderProperty("report_date", OrderProperty.DESC));
		orderPropList.add(new OrderProperty("submit_date", OrderProperty.DESC));
		return oracleInfoList.getNewsList(page, srhPropList, orderPropList);
	}

	@Override
	public int getNewsListNum(String isactive, String rep_date_box,
			String title_box, String isTop, String isconfirm, String isPop)
			throws PlatformException {
		OracleCampusInfoList oracleInfoList = new OracleCampusInfoList();

		List srhPropList = new ArrayList();
		SearchProperty searchProp = new SearchProperty("isactive", isactive,
				"=");
		srhPropList.add(searchProp);
		searchProp = new SearchProperty("report_date", rep_date_box, "like");
		srhPropList.add(searchProp);
		searchProp = new SearchProperty("title", title_box, "like");
		srhPropList.add(searchProp);
		searchProp = new SearchProperty("istop", isTop, "=");
		srhPropList.add(searchProp);
		searchProp = new SearchProperty("isconfirm", isconfirm, "=");
		srhPropList.add(searchProp);
		searchProp = new SearchProperty("ispop", isPop, "=");
		srhPropList.add(searchProp);
		return oracleInfoList.getNewsListNum(srhPropList);
	}

	public CampusNews getCampusNews(String id) throws PlatformException {
		CampusNews oracleCampusnews = new OracleCampusNews(id);
		Docent oracleDocent = new OracleDocent(oracleCampusnews.getDocent_Key());
		oracleCampusnews.setDecentInfo(oracleDocent);
		return oracleCampusnews;
	}

	public int addNewsReadCount(String newsId) throws PlatformException {
		OracleCampusNews news = new OracleCampusNews();
		news.setId(newsId);
		int sub = news.addReadCount();
		return sub;
	}

//	public WhatyEditorConfig getWhatyEditorConfig(HttpSession session)
//			throws PlatformException {
//		ServletContext application = session.getServletContext();
//		CampusInfoConfig config = (CampusInfoConfig) application
//				.getAttribute("campusInfoConfig");
//		PlatformConfig platformConfig = (PlatformConfig) application
//				.getAttribute("platformConfig");
//		WhatyEditorConfig editorConfig = new WhatyEditorConfig();
//		if(config!=null){
//			editorConfig.setUploadAbsPath(config.getInfoWebIncomingAbsPath()
//					+ File.separator);
//			editorConfig.setUploadURI(config.getInfoWebIncomingUriPath());
//		}else{
//			throw new PlatformException("������---whatyCampusCathedraInfoConfig.xml--��ز���");
//		}
//		if(platformConfig!=null){
//			editorConfig.setAppRootURI(platformConfig.getPlatformWebAppUriPath());
//			editorConfig.setEditorRefURI("WhatyEditor/");
//			editorConfig.setEditorURI(platformConfig.getPlatformWebAppUriPath()
//					+ "WhatyEditor/");
//		}else{
//			throw new PlatformException("������--whatyPlatformConfig.xml--��ز���");
//		}
//		
//		
//		
//		return editorConfig;
//	}

}
