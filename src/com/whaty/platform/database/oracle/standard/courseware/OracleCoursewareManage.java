package com.whaty.platform.database.oracle.standard.courseware;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.config.CoursewareType;
import com.whaty.platform.courseware.CoursewareManage;
import com.whaty.platform.courseware.CoursewareManagerPriv;
import com.whaty.platform.courseware.basic.Courseware;
import com.whaty.platform.courseware.basic.CoursewareActivity;
import com.whaty.platform.courseware.basic.CoursewareDir;
import com.whaty.platform.courseware.basic.CoursewareList;
import com.whaty.platform.courseware.basic.WhatyCoursewareTemplate;
import com.whaty.platform.courseware.basic.WhatyOnlineCourseware;
import com.whaty.platform.courseware.config.CoursewareConfig;
import com.whaty.platform.courseware.exception.CoursewareException;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.courseware.basic.OracleCourseware;
import com.whaty.platform.database.oracle.standard.courseware.basic.OracleCoursewareActivity;
import com.whaty.platform.database.oracle.standard.courseware.basic.OracleCoursewareDir;
import com.whaty.platform.database.oracle.standard.courseware.basic.OracleCoursewareList;
import com.whaty.platform.database.oracle.standard.courseware.basic.OracleNormalHttpCourseware;
import com.whaty.platform.database.oracle.standard.courseware.basic.OracleWhatyOnlineCourseware;
import com.whaty.platform.database.oracle.standard.courseware.basic.OracleWhatyUploadCourseware;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleTeachClass;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleTeachClassCware;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleDep;
import com.whaty.platform.entity.basic.Dep;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserLog;
import com.whaty.util.Exception.FileManageException;
//import com.whaty.util.editor.WhatyEditorConfig;
import com.whaty.util.file.FileManage;
import com.whaty.util.file.FileManageFactory;
import com.whaty.util.log.Log;

public class OracleCoursewareManage extends CoursewareManage {

	public OracleCoursewareManage(CoursewareManagerPriv amanagerPriv) {
		super();
		this.setPriv(amanagerPriv);
	}

	public void changeTemplate(String coursewareId, String templateId) {
		// TODO Auto-generated method stub

	}

	public void creatNormalHttpCourseware(String coursewareId)
			throws PlatformException {
		OracleCourseware cw1;
		try {
			cw1 = new OracleCourseware(coursewareId);
		} catch (CoursewareException e1) {
			throw new PlatformException("creatNormalHttpCourseware error!("
					+ e1.toString() + ")");
		}
		cw1.setCoursewareType(CoursewareType.NORMALHTTP);
		try {

			int i = cw1.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.getPriv().getMessageId()
									+ "</whaty><whaty>BEHAVIOR$|$creatNormalHttpCourseware</whaty><whaty>STATUS$|$"
									+ i
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			if (i < 1) {
				throw new PlatformException("creatNormalHttpCourseware error!");
			}
		} catch (PlatformException e) {
			throw new PlatformException("creatNormalHttpCourseware error!("
					+ e.toString() + ")");
		}
	}

	public void creatUploadHttpCourseware(HttpSession session,
			String coursewareId) throws PlatformException {
		ServletContext application = session.getServletContext();
		CoursewareConfig config = (CoursewareConfig) application
				.getAttribute("coursewareConfig");
		FileManage fileManage = FileManageFactory.creat();
		OracleCourseware courseware;
		try {
			courseware = new OracleCourseware(coursewareId);
		} catch (CoursewareException e1) {
			throw new PlatformException("OracleCourseware construct error!("
					+ e1.toString() + ")");
		}
		courseware.setCoursewareType(CoursewareType.UPLOADHTTP);
		try {
			fileManage.createDirectory(config.getCoursewareAbsPath(),
					coursewareId);
		} catch (FileManageException e) {
			throw new PlatformException("creatUploadHttpCourseware error!("
					+ e.toString() + ")");
		}
		int i = courseware.update();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getPriv().getMessageId()
								+ "</whaty><whaty>BEHAVIOR$|$creatUploadHttpCourseware</whaty><whaty>STATUS$|$"
								+ i
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		if (i < 1) {
			throw new PlatformException(
					"creatUploadHttpCourseware error!(set coursewareType error)");
		}
	}

	public void creatWhatyOnlineCourseware(String coursewareId,
			String TemplateId) {
		// TODO Auto-generated method stub

	}

	public int deleteCourseware(String coursewareId) throws PlatformException {
		OracleCourseware cw;
		try {
			cw = new OracleCourseware(coursewareId);
		} catch (CoursewareException e) {
			throw new PlatformException("construct OracleCourseware error!("
					+ e.toString() + ")");
		}
		try {
			int i = cw.delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.getPriv().getMessageId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteCourseware</whaty><whaty>STATUS$|$"
									+ i
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			if (i < 1) {
				throw new PlatformException("sql to deleteCourseware error!");
			}
			return i;
		} catch (PlatformException e) {
			throw new PlatformException("deleteCourseware error!("
					+ e.toString() + ")");
		}

	}

	public void deleteWhatyCoursewareTemplate(String templateId) {
		// TODO Auto-generated method stub

	}

	public Courseware getCourseware(String coursewareId)
			throws PlatformException {
		if (this.getPriv().getCourseware == 1) {
			CoursewareActivity activity = new OracleCoursewareActivity();
			try {
				return activity.getCourseware(coursewareId);
			} catch (CoursewareException e) {
				throw new PlatformException(
						"getCourseware error!(e.toString())");
			}
		} else {
			throw new PlatformException("no right to getCourseware!");
		}

	}

	public String getCoursewareType(String coursewareId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getWhatyCoursewareTemplate() throws PlatformException {
		if (this.getPriv().getWhatyCoursewareTemplate == 1) {
			CoursewareList coursewareList = new OracleCoursewareList();
			return coursewareList.searchWhatyCoursewareTemplate(null, null,
					null);
		} else {
			throw new PlatformException("��û��������߿μ�ģ���Ȩ��!");
		}

	}

	public WhatyCoursewareTemplate getWhatyCoursewareTemplate(String templateId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCoursewareType(String coursewareId, String courewareType) {
		// TODO Auto-generated method stub

	}

	public void buildOnlineCourseware(HttpSession session, String coursewareId,
			String templateId) throws PlatformException {
		if (this.getPriv().getWhatyCoursewareTemplate == 1) {
			ServletContext application = session.getServletContext();
			CoursewareConfig config = (CoursewareConfig) application
					.getAttribute("coursewareConfig");
			CoursewareActivity activity = new OracleCoursewareActivity();
			try {
				activity
						.buildOnlineCourseware(config, coursewareId, templateId);
				UserLog
						.setInfo(
								"<whaty>USERID$|$"
										+ this.getPriv().getMessageId()
										+ "</whaty><whaty>BEHAVIOR$|$buildOnlineCourseware</whaty><whaty>STATUS$|$"
										+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
										+ LogType.MANAGER
										+ "</whaty><whaty>PRIORITY$|$"
										+ LogPriority.INFO + "</whaty>",
								new Date());
			} catch (CoursewareException e) {
				throw new PlatformException("buildOnlineCourseware["
						+ e.toString() + "]");
			}
		} else {
			throw new PlatformException("��û�д����μ���Ȩ��!");
		}

	}

	public WhatyOnlineCourseware getWhatyOnlineCourseware(String coursewareId)
			throws PlatformException {
		if (this.getPriv().getCourseware == 1) {
			return new OracleWhatyOnlineCourseware(coursewareId);
		} else {
			throw new PlatformException("��û����?μ���Ȩ��!");
		}
	}

	public void addOnlineCoursewareInfo(HttpSession session,
			String coursewareId, String infoId, String infoTitle)
			throws PlatformException {

		if (this.getPriv().addOnlineCwInfo == 1) {
			ServletContext application = session.getServletContext();
			CoursewareConfig config = (CoursewareConfig) application
					.getAttribute("coursewareConfig");
			CoursewareActivity activity = new OracleCoursewareActivity();
			try {
				activity.addOnlineCoursewareInfo(config, coursewareId, infoId,
						infoTitle);
				UserLog
						.setInfo(
								"<whaty>USERID$|$"
										+ this.getPriv().getMessageId()
										+ "</whaty><whaty>BEHAVIOR$|$addOnlineCoursewareInfo</whaty><whaty>STATUS$|$"
										+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
										+ LogType.MANAGER
										+ "</whaty><whaty>PRIORITY$|$"
										+ LogPriority.INFO + "</whaty>",
								new Date());
			} catch (CoursewareException e) {
				throw new PlatformException("addOnlineCoursewareInfo["
						+ e.toString() + "]");
			}
		} else {
			throw new PlatformException("no right to addOnlineCoursewareInfo!");
		}
	}

	public void deleteOnlineCoursewareInfo(HttpSession session,
			String coursewareId, String infoUrl, String infoTitle)
			throws PlatformException {
		if (this.getPriv().deleteOnlineCwInfo == 1) {
			ServletContext application = session.getServletContext();
			CoursewareConfig config = (CoursewareConfig) application
					.getAttribute("coursewareConfig");
			CoursewareActivity activity = new OracleCoursewareActivity();
			try {
				activity.deleteOnlineCoursewareInfo(config, coursewareId,
						infoUrl, infoTitle);
				UserLog
						.setInfo(
								"<whaty>USERID$|$"
										+ this.getPriv().getMessageId()
										+ "</whaty><whaty>BEHAVIOR$|$deleteOnlineCoursewareInfo</whaty><whaty>STATUS$|$"
										+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
										+ LogType.MANAGER
										+ "</whaty><whaty>PRIORITY$|$"
										+ LogPriority.INFO + "</whaty>",
								new Date());
			} catch (CoursewareException e) {
				throw new PlatformException("deleteOnlineCoursewareInfo["
						+ e.toString() + "]");
			}
		} else {
			throw new PlatformException(
					"no right to deleteOnlineCoursewareInfo!");
		}

	}

	public List getOnlineCoursewareInfos(HttpSession session,
			String coursewareId) throws PlatformException {
		if (this.getPriv().getOnlineCwInfo == 1) {
			ServletContext application = session.getServletContext();
			CoursewareConfig config = (CoursewareConfig) application
					.getAttribute("coursewareConfig");
			CoursewareActivity activity = new OracleCoursewareActivity();
			try {
				return activity.getOnlineCoursewareInfos(config, coursewareId);
			} catch (CoursewareException e) {
				throw new PlatformException("getOnlineCoursewareInfos["
						+ e.toString() + "]");
			}
		} else {
			throw new PlatformException("no right to getOnlineCoursewareInfos!");
		}
	}

	public void addChapterPage(HttpSession session, String coursewareId,
			String remark) throws PlatformException {
		if (this.getPriv().addChapterPage == 1) {
			ServletContext application = session.getServletContext();
			CoursewareConfig config = (CoursewareConfig) application
					.getAttribute("coursewareConfig");
			CoursewareActivity activity = new OracleCoursewareActivity();

			try {
				activity.addChapterPage(config, coursewareId, remark);
				UserLog
						.setInfo(
								"<whaty>USERID$|$"
										+ this.getPriv().getMessageId()
										+ "</whaty><whaty>BEHAVIOR$|$addChapterPage</whaty><whaty>STATUS$|$"
										+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
										+ LogType.MANAGER
										+ "</whaty><whaty>PRIORITY$|$"
										+ LogPriority.INFO + "</whaty>",
								new Date());
			} catch (CoursewareException e) {
				throw new PlatformException("addChapterPage[" + e.toString()
						+ "]");
			}
		} else {
			throw new PlatformException("no right to addChapterPage!");
		}
	}

	public String getOnlineCwRootDir(HttpSession session, String coursewareId)
			throws PlatformException {
		if (this.getPriv().getOnlineCwRootDir == 1) {
			ServletContext application = session.getServletContext();
			CoursewareConfig config = (CoursewareConfig) application
					.getAttribute("coursewareConfig");
			String rootDirStr = config.getCoursewareAbsPath() + coursewareId
					+ File.separator;
			Log.setDebug(rootDirStr);
			File rootDir = new File(rootDirStr);
			if (rootDir.exists() && rootDir.isDirectory()) {
				return rootDirStr;
			} else {
				throw new PlatformException(
						"getOnlineCwRootDir error!(no this courseware id '"
								+ coursewareId + "' dir)");
			}
		} else {
			throw new PlatformException("no right to getOnlineCwRootDir!");
		}

	}

	public void updateNormalHttpLink(String coursewareId, String link)
			throws PlatformException {
		if (this.getPriv().updateNormalHttpLink == 1) {
			OracleNormalHttpCourseware courseware = new OracleNormalHttpCourseware(
					coursewareId);
			courseware.setHttpLink(link);
			int i = courseware.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.getPriv().getMessageId()
									+ "</whaty><whaty>BEHAVIOR$|$updateNormalHttpLink</whaty><whaty>STATUS$|$"
									+ i
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		}

		else {
			throw new PlatformException("no right to updateNormalHttpLink!");
		}
	}

	public void updateUploadEnterFile(String coursewareId, String link)
			throws PlatformException {
		if (this.getPriv().updateUploadEnterFile == 1) {
			OracleWhatyUploadCourseware courseware = new OracleWhatyUploadCourseware(
					coursewareId);
			courseware.setEnterFileName(link);
			int i = courseware.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.getPriv().getMessageId()
									+ "</whaty><whaty>BEHAVIOR$|$updateUploadEnterFile</whaty><whaty>STATUS$|$"
									+ i
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("no right to updateUploadEnterFile!");
		}

	}

	public int addCourseware(String id, String name, String active,
			String founderId, String foundDate, String author,
			String publisher, String note) throws PlatformException {
		OracleCourseware courseware = new OracleCourseware();
		courseware.setId(id);
		courseware.setName(name);
		courseware.setFoundDate(foundDate);
		courseware.setFounderId(founderId);
		courseware.setAuthor(author);
		courseware.setPublisher(publisher);
		courseware.setNote(note);
		if (active == null || active.equals("0") || active.length() < 1)
			courseware.setActive(false);
		else
			courseware.setActive(true);
		try {
			int i = courseware.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.getPriv().getMessageId()
									+ "</whaty><whaty>BEHAVIOR$|$addCourseware</whaty><whaty>STATUS$|$"
									+ i
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			if (i < 1) {
				throw new PlatformException("sql to addCourseware error!");
			}
			return i;
		} catch (PlatformException e) {
			throw new PlatformException("addCourseware error(" + e.toString()
					+ ")");
		}

	}
	
	public int addCourseware(String id, String name, String active,
			String founderId, String foundDate, String author,
			String publisher, String note,String openCourseDepId) throws PlatformException {
		OracleCourseware courseware = new OracleCourseware();
		courseware.setId(id);
		courseware.setName(name);
		courseware.setFoundDate(foundDate);
		courseware.setFounderId(founderId);
		courseware.setAuthor(author);
		courseware.setPublisher(publisher);
		courseware.setNote(note);
		Dep dep  = new OracleDep();
		dep.setId(openCourseDepId);
		courseware.setDep(dep);
		if (active == null || active.equals("0") || active.length() < 1)
			courseware.setActive(false);
		else
			courseware.setActive(true);
		try {
			int i = courseware.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.getPriv().getMessageId()
									+ "</whaty><whaty>BEHAVIOR$|$addCourseware</whaty><whaty>STATUS$|$"
									+ i
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			if(i == 0 || i < -2 ){
				throw new PlatformException("sql to addCourseware error!");
			}
			return i;
		} catch (PlatformException e) {
			throw new PlatformException("addCourseware error(" + e.toString()
					+ ")");
		}

	}

	public int addCourseware(String name, String active, String founderId,
			String foundDate, String author, String publisher, String note)
			throws PlatformException {
		OracleCourseware courseware = new OracleCourseware();
		courseware.setName(name);
		courseware.setFoundDate(foundDate);
		courseware.setFounderId(founderId);
		courseware.setAuthor(author);
		courseware.setPublisher(publisher);
		courseware.setNote(note);
		if (active == null || active.equals("0") || active.length() < 1)
			courseware.setActive(false);
		else
			courseware.setActive(true);
		try {
			int i = courseware.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.getPriv().getMessageId()
									+ "</whaty><whaty>BEHAVIOR$|$addCourseware</whaty><whaty>STATUS$|$"
									+ i
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			if (i < 1) {
				throw new PlatformException("sql to addCourseware error!");
			}
			return i;
		} catch (PlatformException e) {
			throw new PlatformException("addCourseware error(" + e.toString()
					+ ")");
		}

	}

	public int addCoursewareAndToTeachClass(String name, String active,
			String founderId, String foundDate, String author,
			String publisher, String note, String teachclass_id)
			throws PlatformException {
		dbpool db = new dbpool();
		int id = db.getSequenceNextId("s_PE_TCH_COURSEWARE_id");
		String idStr = String.valueOf(id);
		OracleCourseware courseware = new OracleCourseware();
		courseware.setId(idStr);
		courseware.setName(name);
		courseware.setFoundDate(foundDate);
		courseware.setFounderId(founderId);
		courseware.setAuthor(author);
		courseware.setPublisher(publisher);
		courseware.setNote(note);
		int i = courseware.addAndToTeachClass_id(teachclass_id);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getPriv().getMessageId()
								+ "</whaty><whaty>BEHAVIOR$|$addCoursewareAndToTeachClass</whaty><whaty>STATUS$|$"
								+ i
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return i;
	}

	public void updateCourseware(String coursewareId, String name,
			String active, String founderId, String foundDate, String author,
			String publisher, String note) throws PlatformException {
		OracleCourseware courseware;
		try {
			courseware = new OracleCourseware(coursewareId);
		} catch (CoursewareException e1) {
			throw new PlatformException("construct OracleCourseware error!("
					+ e1.toString() + ")");
		}
		courseware.setName(name);
		courseware.setFoundDate(foundDate);
		courseware.setFounderId(founderId);
		courseware.setAuthor(author);
		courseware.setPublisher(publisher);
		courseware.setNote(note);
		if (active == null || active.equals("0") || active.length() < 1)
			courseware.setActive(false);
		else
			courseware.setActive(true);
		try {
			int i = courseware.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.getPriv().getMessageId()
									+ "</whaty><whaty>BEHAVIOR$|$updateCourseware</whaty><whaty>STATUS$|$"
									+ i
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			if (i < 1) {
				throw new PlatformException("sql to updateCourseware error!");
			}
		} catch (PlatformException e) {
			throw new PlatformException("updateCourseware error("
					+ e.toString() + ")");
		}

	}
	
	public void updateCourseware(String coursewareId, String name,
			String active, String founderId, String foundDate, String author,
			String publisher, String note,String openCourseDepId) throws PlatformException {
		OracleCourseware courseware;
		try {
			courseware = new OracleCourseware(coursewareId);
		} catch (CoursewareException e1) {
			throw new PlatformException("construct OracleCourseware error!("
					+ e1.toString() + ")");
		}
		courseware.setName(name);
		courseware.setFoundDate(foundDate);
		courseware.setFounderId(founderId);
		courseware.setAuthor(author);
		courseware.setPublisher(publisher);
		courseware.setNote(note);
		
		Dep dep = new OracleDep();
		dep.setId(openCourseDepId);
		
		courseware.setDep(dep);
		if (active == null || active.equals("0") || active.length() < 1)
			courseware.setActive(false);
		else
			courseware.setActive(true);
		try {
			int i = courseware.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.getPriv().getMessageId()
									+ "</whaty><whaty>BEHAVIOR$|$updateCourseware</whaty><whaty>STATUS$|$"
									+ i
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			if (i < 1) {
				throw new PlatformException("sql to updateCourseware error!");
			}
		} catch (PlatformException e) {
			throw new PlatformException("updateCourseware error("
					+ e.toString() + ")");
		}

	}

	public List searchCoursewares(Page page, String coursewareId,
			String coursewareName, String active, String founderId,
			String author, String publisher, String coursewareType,
			String coursewareDir) throws PlatformException {
		if (this.getPriv().getCourseware == 1) {
			OracleCoursewareList cwlist = new OracleCoursewareList();
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			searchList.add(new SearchProperty("id", coursewareId, "like"));
			searchList.add(new SearchProperty("name", coursewareName, "like"));
			searchList.add(new SearchProperty("active", active, "num"));
			searchList.add(new SearchProperty("founder_id", founderId));
			searchList.add(new SearchProperty("author", author));
			searchList
					.add(new SearchProperty("courseware_type", coursewareType));
			searchList.add(new SearchProperty("publisher", publisher));
			searchList.add(new SearchProperty("courseware_dir", coursewareDir));
			orderList.add(new OrderProperty("id"));
			return cwlist.searchCourseware(page, searchList, orderList);
		} else {
			throw new PlatformException("��û����?μ���Ȩ��!");
		}
	}

	public List getCoursewareDirs(String parentDir) throws PlatformException {
		if (parentDir == null || parentDir.length() < 1
				| parentDir.equalsIgnoreCase("null")) {
			parentDir = "root";
		}
		try {
			OracleCoursewareDir cwDir = new OracleCoursewareDir();
			cwDir.setId(parentDir);
			return cwDir.getCoursewareDirs();
		} catch (CoursewareException e) {
			throw new PlatformException("getCoursewareDirs error!("
					+ e.toString() + ")");
		}
	}

	public List getCoursewareDirs(String parentDir, String dirName) throws PlatformException {
		if (parentDir == null || parentDir.length() < 1
				| parentDir.equalsIgnoreCase("null")) {
			parentDir = "root";
		}
		try {
			OracleCoursewareDir cwDir = new OracleCoursewareDir();
			cwDir.setId(parentDir);
			return cwDir.getCoursewareDirs(dirName);
		} catch (CoursewareException e) {
			throw new PlatformException("getCoursewareDirs error!("
					+ e.toString() + ")");
		}
	}
	
	public List getCoursewares(String parentDir) throws PlatformException {
		if (parentDir == null || parentDir.length() < 1
				| parentDir.equalsIgnoreCase("null")) {
			parentDir = "root";
		}
		OracleCoursewareDir cwDir = new OracleCoursewareDir();
		cwDir.setId(parentDir);
		return cwDir.getCoursewares();
	}
	
	public List getCoursewares(String parentDir, String coursewareName) {
		List coursewareList = new ArrayList();
		if (parentDir == null || parentDir.length() < 1
				| parentDir.equalsIgnoreCase("null")) {
			parentDir = "root";
		}
		OracleCoursewareDir cwDir = new OracleCoursewareDir();
		cwDir.setId(parentDir);
		return cwDir.getCoursewares(coursewareName);
	}

	public int searchCoursewaresNum(String coursewareId, String coursewareName,
			String active, String founderId, String author, String publisher,
			String coursewareType, String coursewareDir) {
		OracleCoursewareList cwlist = new OracleCoursewareList();
		List searchList = new ArrayList();
		searchList.add(new SearchProperty("id", coursewareId, "like"));
		searchList.add(new SearchProperty("name", coursewareName, "like"));
		searchList.add(new SearchProperty("active", active, "num"));
		searchList.add(new SearchProperty("founder_id", founderId));
		searchList.add(new SearchProperty("author", author));
		searchList.add(new SearchProperty("courseware_type", coursewareType));
		searchList.add(new SearchProperty("publisher", publisher));
		searchList.add(new SearchProperty("courseware_dir", coursewareDir));
		return cwlist.searchCoursewareNum(searchList);
	}

	public String getCoursewareEnterUrl(HttpSession session, String coursewareId)
			throws PlatformException {
		ServletContext application = session.getServletContext();
		CoursewareConfig config = (CoursewareConfig) application
				.getAttribute("coursewareConfig");
		CoursewareActivity activity = new OracleCoursewareActivity();
		try {
			return activity.getCoursewareEnterUrl(config, coursewareId);
		} catch (CoursewareException e) {
			throw new PlatformException("getCoursewareEnterUrl error!("
					+ e.toString() + ")");
		}

	}

	public CoursewareDir getCoursewareDir(String coursewareDirId)
			throws PlatformException {
		if (this.getPriv().getCoursewareDir == 1) {
			try {
				return new OracleCoursewareDir(coursewareDirId);
			} catch (CoursewareException e) {
				throw new PlatformException("getCoursewareDir error!("
						+ e.toString() + ")");
			}
		} else {
			throw new PlatformException("��û����?μ�Ŀ¼��Ȩ��!");
		}
	}

	public void activeCoursewares(List coursewareIdList, String flag)
			throws PlatformException {
		CoursewareActivity activity = new OracleCoursewareActivity();
		boolean bflag = false;
		if (flag != null && flag.equals("1"))
			bflag = true;
		try {
			activity.activeCoursewares(coursewareIdList, bflag);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.getPriv().getMessageId()
									+ "</whaty><whaty>BEHAVIOR$|$activeCoursewares</whaty><whaty>STATUS$|$"
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} catch (CoursewareException e) {
			throw new PlatformException("activeCoursewares error!("
					+ e.toString() + ")");
		}

	}

	public String getCwTemplateImageUrl(HttpSession session, String templateId,
			String templateImgFile) throws PlatformException {
		ServletContext application = session.getServletContext();
		CoursewareConfig config = (CoursewareConfig) application
				.getAttribute("coursewareConfig");
		String url = config.getCoursewareTemplateRefPath() + templateId
				+ "/templateimage/" + templateImgFile;
		return url;
	}

//	public WhatyEditorConfig getWhatyEditorConfig(HttpSession session,
//			String coursewareId) throws PlatformException {
//		ServletContext application = session.getServletContext();
//		CoursewareConfig config = (CoursewareConfig) application
//				.getAttribute("coursewareConfig");
//		WhatyEditorConfig editorConfig = new WhatyEditorConfig();
//		editorConfig.setAppRootURI("/M4/");
//		editorConfig.setEditorRefURI("WhatyEditor/");
//		editorConfig.setEditorURI("/M4/WhatyEditor/");
//		editorConfig.setUploadAbsPath(config.getCoursewareAbsPath()
//				+ coursewareId + File.separator + "upload" + File.separator);
//		editorConfig.setUploadURI(config.getCoursewareURI() + coursewareId
//				+ "/upload/");
//		return editorConfig;
//	}

	public int addTeachClassCware(String teachClassId, String coursewareId,
			String active) throws PlatformException {
		OracleTeachClassCware teachClassCware = new OracleTeachClassCware();
		OracleTeachClass teachClass = new OracleTeachClass();
		teachClass.setId(teachClassId);
		OracleCourseware courseware = new OracleCourseware();
		courseware.setId(coursewareId);
		teachClassCware.setCourseware(courseware);
		teachClassCware.setTeachClass(teachClass);
		teachClassCware.setActive(active);
		int i = teachClassCware.add();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getPriv().getMessageId()
								+ "</whaty><whaty>BEHAVIOR$|$addTeachClassCware</whaty><whaty>STATUS$|$"
								+ i
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return i;

	}

	public int addTeachClassCware(String teachClassId, String[] coursewareIds,
			String[] pageCoursewareIds, String active) throws PlatformException {
		OracleCoursewareList coursewareList = new OracleCoursewareList();
		int i = coursewareList.addTeachClassCware(teachClassId, coursewareIds,
				pageCoursewareIds, active);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getPriv().getMessageId()
								+ "</whaty><whaty>BEHAVIOR$|$addTeachClassCware</whaty><whaty>STATUS$|$"
								+ i
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return i;
	}

	public int deleteTeachClassCware(String teachClassId, String coursewareId)
			throws PlatformException {
		OracleTeachClassCware teachClassCware = new OracleTeachClassCware();
		OracleTeachClass teachClass = new OracleTeachClass();
		teachClass.setId(teachClassId);
		OracleCourseware courseware = new OracleCourseware();
		courseware.setId(coursewareId);
		teachClassCware.setCourseware(courseware);
		teachClassCware.setTeachClass(teachClass);
		int i = teachClassCware.delete();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getPriv().getMessageId()
								+ "</whaty><whaty>BEHAVIOR$|$deleteTeachClassCware</whaty><whaty>STATUS$|$"
								+ i
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return i;

	}

}
