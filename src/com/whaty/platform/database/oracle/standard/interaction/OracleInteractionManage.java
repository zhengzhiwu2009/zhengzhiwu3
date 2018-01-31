package com.whaty.platform.database.oracle.standard.interaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.whaty.platform.User;
import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.courseware.CoursewareFactory;
import com.whaty.platform.courseware.CoursewareManage;
import com.whaty.platform.courseware.CoursewareManagerPriv;
import com.whaty.platform.courseware.basic.Courseware;
import com.whaty.platform.database.oracle.standard.courseware.basic.OracleCourseware;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleBasicActivityList;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleTeachClass;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourse;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourseItem;
import com.whaty.platform.database.oracle.standard.interaction.announce.OracleAnnounce;
import com.whaty.platform.database.oracle.standard.interaction.answer.OracleAnswer;
import com.whaty.platform.database.oracle.standard.interaction.answer.OracleEliteAnswer;
import com.whaty.platform.database.oracle.standard.interaction.answer.OracleEliteQuestion;
import com.whaty.platform.database.oracle.standard.interaction.answer.OracleQuestion;
import com.whaty.platform.database.oracle.standard.interaction.exam.*;
import com.whaty.platform.database.oracle.standard.interaction.answer.OracleQuestionEliteDir;
import com.whaty.platform.database.oracle.standard.interaction.forum.OracleForum;
import com.whaty.platform.database.oracle.standard.interaction.forum.OracleForumList;
import com.whaty.platform.database.oracle.standard.interaction.homework.OracleHomework;
import com.whaty.platform.database.oracle.standard.interaction.homework.OracleHomeworkCheck;
import com.whaty.platform.database.oracle.standard.interaction.homework.OracleInHomework;
import com.whaty.platform.database.oracle.standard.interaction.homework.OracleInHomeworkCheck;
import com.whaty.platform.entity.BasicActivityManage;
import com.whaty.platform.entity.BasicScoreManage;
import com.whaty.platform.entity.PlatformFactory;
import com.whaty.platform.entity.basic.Course;
import com.whaty.platform.entity.user.Teacher;
import com.whaty.platform.interaction.InteractionManage;
import com.whaty.platform.interaction.InteractionTeachClass;
import com.whaty.platform.interaction.InteractionUserPriv;
import com.whaty.platform.interaction.announce.Announce;
import com.whaty.platform.interaction.answer.Answer;
import com.whaty.platform.interaction.answer.EliteAnswer;
import com.whaty.platform.interaction.answer.EliteQuestion;
import com.whaty.platform.interaction.answer.Question;
import com.whaty.platform.interaction.answer.QuestionEliteDir;
import com.whaty.platform.interaction.exam.ExamAnswer;
import com.whaty.platform.interaction.exam.ExamQuestion;
import com.whaty.platform.interaction.exam.ExamUserQuestion;
import com.whaty.platform.interaction.forum.Forum;
import com.whaty.platform.interaction.forum.ForumList;
import com.whaty.platform.interaction.homework.Homework;
import com.whaty.platform.interaction.homework.HomeworkCheck;
import com.whaty.platform.interaction.homework.InHomework;
import com.whaty.platform.interaction.homework.InHomeworkCheck;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.test.TestFactory;
import com.whaty.platform.test.TestManage;
import com.whaty.platform.test.TestPriv;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserLog;
import com.whaty.platform.vote.VoteFactory;
import com.whaty.platform.vote.VoteNormalManage;

public class OracleInteractionManage extends InteractionManage {
	InteractionUserPriv interactionUserPriv;

	public OracleInteractionManage(InteractionUserPriv interactionUserPriv) {
		this.interactionUserPriv = interactionUserPriv;
	}

	public int addAnnounce(String title, String body, String publisherId,
			String publisherName, String publisherType, String courseId)
			throws NoRightException, PlatformException {
		if (interactionUserPriv.addAnnounce == 1) {
			OracleAnnounce announce = new OracleAnnounce();
			announce.setTitle(title);
			announce.setBody(body);
			announce.setPublisherId(publisherId);
			announce.setPublisherName(publisherName);
			announce.setPublisherType(publisherType);
			announce.setCourseId(courseId);
			int sub = announce.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$addAnnounce</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有添加公告信息的权限！");
		}
	}

	public int deleteAnnounce(String tid) throws NoRightException,
			PlatformException {
		if (interactionUserPriv.deleteAnnounce == 1) {
			int sub = new OracleAnnounce(tid).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteAnnounce</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有删除公告信息的权限！");
		}
	}

	public int updateAnnounce(String id, String title, String body,
			String publisherId, String publisherName, String publisherType,
			String courseId) throws NoRightException {
		if (interactionUserPriv.updateAnnounce == 1) {
			OracleAnnounce announce = new OracleAnnounce(id);
			announce.setTitle(title);
			announce.setBody(body);
			announce.setPublisherId(publisherId);
			announce.setPublisherName(publisherName);
			announce.setPublisherType(publisherType);
			announce.setCourseId(courseId);
			int sub = announce.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$updateAnnounce</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有修改公告信息的权限！");
		}
	}

	public Announce getAnnounce(String tid) throws NoRightException {
		if (interactionUserPriv.getAnnounce == 1) {
			return new OracleAnnounce(tid);
		} else {
			throw new NoRightException("您没有查看公告信息的权限！");
		}
	}

	public List getAnnounces(Page page, String teachclass_id, String title,
			String publisher_name) throws NoRightException {
		if (interactionUserPriv.getAnnounces == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();

			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();

			if (title != null && !title.equals("null")) {
				searchProperty.add(new SearchProperty("title", title, "like"));
			}
			if (publisher_name != null && !publisher_name.equals("null")) {
				searchProperty.add(new SearchProperty("publisher_name",
						publisher_name, "like"));
			}
			searchProperty.add(new SearchProperty("course_id", teachclass_id));
			orderProperty.add(new OrderProperty("publish_date",
					OrderProperty.DESC));
			return interactionList.getAnnounces(page, searchProperty,
					orderProperty);
		} else {
			throw new NoRightException("您没有查看公告信息的权限");
		}
	}

	public List getAnnounces(Page page, List searchProperty, List orderProperty)
			throws NoRightException {
		if (interactionUserPriv.getAnnounces == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			return interactionList.getAnnounces(page, searchProperty,
					orderProperty);
		} else {
			throw new NoRightException("您没有查看公告信息的权限");
		}
	}

	public int getAnnouncesNum(String teachclass_id, String title,
			String publisher_name) throws NoRightException {
		if (interactionUserPriv.getAnnounces == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperty = new ArrayList();
			if (title != null && !title.equals("null")) {
				searchProperty.add(new SearchProperty("title", title, "like"));
			}
			if (publisher_name != null && !publisher_name.equals("null")) {
				searchProperty.add(new SearchProperty("publisher_name",
						publisher_name, "like"));
			}
			searchProperty.add(new SearchProperty("course_id", teachclass_id));
			return interactionList.getAnnouncesNum(searchProperty);
		} else {
			throw new NoRightException("您没有查看公告信息的权限");
		}
	}

	public int getAnnouncesNum(List searchproperty) throws NoRightException {
		if (interactionUserPriv.getAnnounces == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			return interactionList.getAnnouncesNum(searchproperty);
		} else {
			throw new NoRightException("您没有查看公告信息的权限");
		}
	}

	public int addHomework(String title, String body, String handinDate,
			String submituserId, String submituserName, String submituserType,
			String courseId, String checkStatusStr) throws NoRightException,
			PlatformException {
		if (interactionUserPriv.addHomework == 1) {
			OracleHomework homework = new OracleHomework();
			boolean checkStatus = false;
			homework.setTitle(title);
			homework.setBody(body);
			homework.setHandinDate(handinDate);
			homework.setSubmituserId(submituserId);
			homework.setSubmituserName(submituserName);
			homework.setSubmituserType(submituserType);
			OracleCourse course = new OracleCourse();
			course.setId(courseId);
			homework.setCourse(course);
			if (checkStatusStr.equals("1"))
				checkStatus = true;
			homework.setCheckStatus(checkStatus);
			int sub = homework.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$addHomework</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有添加作业的权限！");
		}
	}

	public int deleteHomework(String tid) throws NoRightException,
			PlatformException {
		if (interactionUserPriv.deleteHomework == 1) {
			int sub = new OracleAnnounce(tid).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteHomework</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有删除作业的权限！");
		}
	}

	public int updateHomework(String id, String title, String body,
			String handinDate, String submituserId, String submituserName,
			String submituserType, String courseId, String checkStatusStr)
			throws NoRightException {
		if (interactionUserPriv.updateHomework == 1) {
			OracleHomework homework = new OracleHomework();
			boolean checkStatus = false;
			homework.setTitle(title);
			homework.setBody(body);
			homework.setHandinDate(handinDate);
			homework.setSubmituserId(submituserId);
			homework.setSubmituserName(submituserName);
			homework.setSubmituserType(submituserType);
			OracleCourse course = new OracleCourse();
			course.setId(courseId);
			homework.setCourse(course);
			if (checkStatusStr.equals("1"))
				checkStatus = true;
			homework.setCheckStatus(checkStatus);
			int sub = homework.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$updateHomework</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有修改作业的权限！");
		}
	}

	public Homework getHomework(String tid) throws NoRightException {
		if (interactionUserPriv.getHomework == 1) {
			return new OracleHomework(tid);
		} else {
			throw new NoRightException("您没有查看作业的权限！");
		}
	}

	public List getHomeworks(Page page, List searchProperty, List orderProperty)
			throws NoRightException {
		if (interactionUserPriv.getHomeworks == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			return interactionList.getHomeworks(page, searchProperty,
					orderProperty);
		} else {
			throw new NoRightException("您没有查看作业的权限");
		}
	}

	public int addHomeworkCheck(String homeworkId, String body,
			String checkuserId, String checkuserName, String checkuserType)
			throws NoRightException, PlatformException {
		if (interactionUserPriv.addHomeworkCheck == 1) {
			OracleHomeworkCheck homeworkCheck = new OracleHomeworkCheck();
			OracleHomework homework = new OracleHomework();
			homework.setId(homeworkId);
			homeworkCheck.setHomework(homework);
			homeworkCheck.setBody(body);
			homeworkCheck.setCheckuserId(checkuserId);
			homeworkCheck.setCheckuserName(checkuserName);
			homeworkCheck.setCheckuserType(checkuserType);
			int sub = homeworkCheck.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$addHomeworkCheck</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有添加作业点评的权限！");
		}
	}

	public int deleteHomeworkCheck(String tid) throws NoRightException,
			PlatformException {
		if (interactionUserPriv.deleteHomeworkCheck == 1) {
			int sub = new OracleAnnounce(tid).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteHomeworkCheck</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有删除作业点评的权限！");
		}
	}

	public int updateHomeworkCheck(String id, String homeworkId, String body,
			String checkuserId, String checkuserName, String checkuserType)
			throws NoRightException {
		if (interactionUserPriv.updateHomeworkCheck == 1) {
			OracleHomeworkCheck homeworkCheck = new OracleHomeworkCheck();
			OracleHomework homework = new OracleHomework();
			homework.setId(homeworkId);
			homeworkCheck.setHomework(homework);
			homeworkCheck.setBody(body);
			homeworkCheck.setCheckuserId(checkuserId);
			homeworkCheck.setCheckuserName(checkuserName);
			homeworkCheck.setCheckuserType(checkuserType);
			int sub = homeworkCheck.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$updateHomeworkCheck</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有修改作业点评的权限！");
		}
	}

	public HomeworkCheck getHomeworkCheck(String tid) throws NoRightException {
		if (interactionUserPriv.getHomeworkCheck == 1) {
			return new OracleHomeworkCheck(tid);
		} else {
			throw new NoRightException("您没有查看作业点评的权限！");
		}
	}

	public List getHomeworkChecks(Page page, List searchProperty,
			List orderProperty) throws NoRightException {
		if (interactionUserPriv.getHomeworkChecks == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			return interactionList.getHomeworkChecks(page, searchProperty,
					orderProperty);
		} else {
			throw new NoRightException("您没有查看作业点评的权限");
		}
	}

	public int addInHomework(String title, String file, String body,
			String submituserId, String submituserName, String submituserType,
			String homeworkId, String checkStatusStr) throws NoRightException,
			PlatformException {
		if (interactionUserPriv.addInHomework == 1) {
			OracleInHomework inHomework = new OracleInHomework();
			boolean checkStatus = false;
			inHomework.setTitle(title);
			inHomework.setFile(file);
			inHomework.setBody(body);
			inHomework.setSubmituserId(submituserId);
			inHomework.setSubmituserName(submituserName);
			inHomework.setSubmituserType(submituserType);
			OracleHomework homework = new OracleHomework();
			homework.setId(homeworkId);
			inHomework.setHomework(homework);
			if (checkStatusStr.equals("1"))
				checkStatus = true;
			inHomework.setCheckStatus(checkStatus);
			int sub = inHomework.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$addInHomework</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有添加上交的作业的权限！");
		}
	}

	public int deleteInHomework(String tid) throws NoRightException,
			PlatformException {
		if (interactionUserPriv.deleteInHomework == 1) {
			int sub = new OracleAnnounce(tid).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteInHomework</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有删除上交的作业的权限！");
		}
	}

	public int updateInHomework(String id, String title, String file,
			String body, String submituserId, String submituserName,
			String submituserType, String homeworkId, String checkStatusStr)
			throws NoRightException {
		if (interactionUserPriv.updateInHomework == 1) {
			OracleInHomework inHomework = new OracleInHomework();
			boolean checkStatus = false;
			inHomework.setTitle(title);
			inHomework.setFile(file);
			inHomework.setBody(body);
			inHomework.setSubmituserId(submituserId);
			inHomework.setSubmituserName(submituserName);
			inHomework.setSubmituserType(submituserType);
			OracleHomework homework = new OracleHomework();
			homework.setId(homeworkId);
			inHomework.setHomework(homework);
			if (checkStatusStr.equals("1"))
				checkStatus = true;
			inHomework.setCheckStatus(checkStatus);
			int sub = inHomework.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$updateInHomework</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有修改上交的作业的权限！");
		}
	}

	public InHomework getInHomework(String tid) throws NoRightException {
		if (interactionUserPriv.getInHomework == 1) {
			return new OracleInHomework(tid);
		} else {
			throw new NoRightException("您没有查看上交的作业的权限！");
		}
	}

	public List getInHomeworks(Page page, List searchProperty,
			List orderProperty) throws NoRightException {
		if (interactionUserPriv.getInHomeworks == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			return interactionList.getInHomeworks(page, searchProperty,
					orderProperty);
		} else {
			throw new NoRightException("您没有查看上交的作业的权限");
		}
	}

	public int addInHomeworkCheck(String inHomeworkId, String body,
			String score, String checkuserId, String checkuserName,
			String checkuserType) throws NoRightException, PlatformException {
		if (interactionUserPriv.addInHomeworkCheck == 1) {
			OracleInHomeworkCheck inHomeworkCheck = new OracleInHomeworkCheck();
			OracleInHomework inHomework = new OracleInHomework();
			inHomework.setId(inHomeworkId);
			inHomeworkCheck.setInHomework(inHomework);
			inHomeworkCheck.setBody(body);
			inHomeworkCheck.setScore(score);
			inHomeworkCheck.setCheckuserId(checkuserId);
			inHomeworkCheck.setCheckuserName(checkuserName);
			inHomeworkCheck.setCheckuserType(checkuserType);
			int sub = inHomeworkCheck.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$addInHomeworkCheck</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有添加上交的作业点评的权限！");
		}
	}

	public int deleteInHomeworkCheck(String tid) throws NoRightException,
			PlatformException {
		if (interactionUserPriv.deleteInHomeworkCheck == 1) {
			int sub = new OracleAnnounce(tid).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteInHomeworkCheck</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有删除上交的作业点评的权限！");
		}
	}

	public int updateInHomeworkCheck(String id, String inHomeworkId,
			String body, String score, String checkuserId,
			String checkuserName, String checkuserType) throws NoRightException {
		if (interactionUserPriv.updateInHomeworkCheck == 1) {
			OracleInHomeworkCheck inHomeworkCheck = new OracleInHomeworkCheck();
			OracleInHomework inHomework = new OracleInHomework();
			inHomework.setId(inHomeworkId);
			inHomeworkCheck.setInHomework(inHomework);
			inHomeworkCheck.setBody(body);
			inHomeworkCheck.setScore(score);
			inHomeworkCheck.setCheckuserId(checkuserId);
			inHomeworkCheck.setCheckuserName(checkuserName);
			inHomeworkCheck.setCheckuserType(checkuserType);
			int sub = inHomeworkCheck.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$updateInHomeworkCheck</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有修改上交的作业点评的权限！");
		}
	}

	public InHomeworkCheck getInHomeworkCheck(String tid)
			throws NoRightException {
		if (interactionUserPriv.getInHomeworkCheck == 1) {
			return new OracleInHomeworkCheck(tid);
		} else {
			throw new NoRightException("您没有查看上交的作业点评的权限！");
		}
	}

	public List getInHomeworkChecks(Page page, List searchProperty,
			List orderProperty) throws NoRightException {
		if (interactionUserPriv.getInHomeworkChecks == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			return interactionList.getInHomeworkChecks(page, searchProperty,
					orderProperty);
		} else {
			throw new NoRightException("您没有查看上交的作业点评的权限");
		}
	}

	public int addQuestion(String title, String body, String key, String type,
			String submituserId, String submituserName, String submituserType,
			String courseId, String reStatusStr) throws NoRightException,
			PlatformException {
		if (interactionUserPriv.addQuestion == 1) {
			OracleQuestion question = new OracleQuestion();
			boolean reStatus = false;
			question.setTitle(title);
			question.setBody(body);
			question.setQuestionType(type);
			if (key != null && key.length() > 0)
				question.setKey(key.split(","));
			else
				question.setKey(null);
			question.setSubmituserId(submituserId);
			question.setSubmituserName(submituserName);
			question.setSubmituserType(submituserType);
			question.setCourseId(courseId);
			if (reStatusStr.equals("1"))
				reStatus = true;
			question.setReStatus(reStatus);
			int sub = question.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$addQuestion</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有添加答疑问题的权限！");
		}
	}

	public int deleteQuestion(String tid) throws NoRightException,
			PlatformException {
		if (interactionUserPriv.deleteQuestion == 1) {
			int sub = new OracleQuestion(tid).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteQuestion</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有删除答疑问题的权限！");
		}
	}

	public int updateQuestion(String id, String title, String body, String key,
			String submituserId, String submituserName, String submituserType,
			String courseId, String reStatusStr) throws NoRightException {
		if (interactionUserPriv.updateQuestion == 1) {
			OracleQuestion question = new OracleQuestion();
			boolean reStatus = false;
			question.setId(id);
			question.setTitle(title);
			question.setBody(body);
			if (key != null && key.length() > 0)
				question.setKey(key.split(",,"));
			else
				question.setKey(null);
			question.setSubmituserId(submituserId);
			question.setSubmituserName(submituserName);
			question.setSubmituserType(submituserType);
			question.setCourseId(courseId);
			if (reStatusStr.equals("1"))
				reStatus = true;
			question.setReStatus(reStatus);
			int sub = question.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$updateQuestion</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有修改答疑问题的权限！");
		}
	}

	public Question getQuestion(String tid) throws NoRightException {
		if (interactionUserPriv.getQuestion == 1) {
			return new OracleQuestion(tid);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限！");
		}
	}

	public List getQuestions(Page page, String teachclass_id, String title,
			String name) throws NoRightException {
		if (interactionUserPriv.getQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("title", title, "like"));
			searchProperty.add(new SearchProperty("submituser_name", name,
					"like"));
			searchProperty.add(new SearchProperty("course_id", teachclass_id,
					"="));
			List orderPropertys = new ArrayList();
			orderPropertys.add(new OrderProperty("publish_date",
					OrderProperty.DESC));
			return interactionList.getQuestions(page, searchProperty,
					orderPropertys);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限");
		}
	}

	public List getQuestions(Page page, String teachclass_id, String title,
			String name, String questionType, String noUse)
			throws NoRightException {
		if (interactionUserPriv.getQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("title", title, "like"));
			searchProperty.add(new SearchProperty("submituser_name", name,
					"like"));
			searchProperty.add(new SearchProperty("course_id", teachclass_id,
					"="));
			searchProperty.add(new SearchProperty("question_type",
					questionType, "="));
			List orderPropertys = new ArrayList();
			orderPropertys.add(new OrderProperty("publish_date",
					OrderProperty.DESC));
			return interactionList.getQuestions(page, searchProperty,
					orderPropertys);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限");
		}
	}

	public List getQuestions(Page page, String teachclass_id, String title,
			String name, String siteId) throws NoRightException {
		if (interactionUserPriv.getQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperty = new ArrayList();
			if (title != null && !"".equals(title)
					&& !"null".equalsIgnoreCase(title))
				searchProperty.add(new SearchProperty("title", title, "like"));
			if (name != null && !"".equals(name)
					&& !"null".equalsIgnoreCase(name))
				searchProperty.add(new SearchProperty("submituser_name", name,
						"like"));
			if (teachclass_id != null && !"".equals(teachclass_id)
					&& !"null".equalsIgnoreCase(teachclass_id))
				searchProperty.add(new SearchProperty("course_id",
						teachclass_id, "="));
			if (siteId != null && !"".equals(siteId)
					&& !"null".equalsIgnoreCase(siteId))
				searchProperty.add(new SearchProperty("site_id", siteId, "="));
			return interactionList.getQuestions(page, searchProperty, null);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限");
		}
	}

	public List getQuestions(Page page, List searchProperty, List orderProperty)
			throws NoRightException {
		if (interactionUserPriv.getQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			return interactionList.getQuestions(page, searchProperty,
					orderProperty);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限");
		}
	}

	public int getQuestionsNum(String teachclass_id, String title, String name)
			throws NoRightException {
		if (interactionUserPriv.getQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("title", title, "like"));
			searchProperty.add(new SearchProperty("submituser_name", name,
					"like"));
			searchProperty.add(new SearchProperty("course_id", teachclass_id,
					"="));
			return interactionList.getQuestionsNum(searchProperty);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限");
		}
	}

	public int getQuestionsNum(String teachclass_id, String title, String name,
			String questionType, String notuse) throws NoRightException {
		if (interactionUserPriv.getQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("title", title, "like"));
			searchProperty.add(new SearchProperty("submituser_name", name,
					"like"));
			searchProperty.add(new SearchProperty("course_id", teachclass_id,
					"="));
			searchProperty.add(new SearchProperty("question_type",
					questionType, "="));
			return interactionList.getQuestionsNum(searchProperty);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限");
		}
	}

	public int getQuestionsNum(String teachclass_id, String title, String name,
			String siteId) throws NoRightException {
		if (interactionUserPriv.getQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperty = new ArrayList();
			if (title != null && !"".equals(title)
					&& !"null".equalsIgnoreCase(title))
				searchProperty.add(new SearchProperty("title", title, "like"));
			if (name != null && !"".equals(name)
					&& !"null".equalsIgnoreCase(name))
				searchProperty.add(new SearchProperty("submituser_name", name,
						"like"));
			if (teachclass_id != null && !"".equals(teachclass_id)
					&& !"null".equalsIgnoreCase(teachclass_id))
				searchProperty.add(new SearchProperty("course_id",
						teachclass_id, "="));
			if (siteId != null && !"".equals(siteId)
					&& !"null".equalsIgnoreCase(siteId))
				searchProperty.add(new SearchProperty("site_id", siteId, "="));
			return interactionList.getQuestionsNum(searchProperty);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限");
		}
	}

	public int getQuestionsNum(List searchProperty) throws NoRightException {
		if (interactionUserPriv.getQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			return interactionList.getQuestionsNum(searchProperty);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限");
		}
	}

	public int addAnswer(String questionId, String body, String reuserId,
			String reuserName, String reuserType, Object teacher)
			throws NoRightException, PlatformException {
		if (interactionUserPriv.addAnswer == 1) {
			OracleAnswer answer = new OracleAnswer();
			OracleQuestion question = new OracleQuestion();
			question.setId(questionId);
			answer.setQuestion(question);
			answer.setBody(body);
			UserSession us = (UserSession) ServletActionContext.getRequest()
					.getSession()
					.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			answer.setReuserId(us.getSsoUser().getId());
			answer.setReuserName(us.getUserName());
			// // answer.setReuserId(reuserId);
			// answer.setReuserName(reuserName);
			answer.setReuserType(reuserType);
			int sub = answer.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$addAnswer</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有添加答疑答案的权限！");
		}
	}

	public int deleteAnswer(String tid) throws NoRightException,
			PlatformException {
		if (interactionUserPriv.deleteAnswer == 1) {
			int sub = new OracleAnswer(tid).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteAnswer</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有删除答疑答案的权限！");
		}
	}

	public int updateAnswer(String id, String questionId, String body,
			String reuserId, String reuserName, String reuserType)
			throws NoRightException {
		if (interactionUserPriv.updateAnswer == 1) {
			OracleAnswer answer = new OracleAnswer(id);
			OracleQuestion question = new OracleQuestion(questionId);
			question.setId(questionId);
			answer.setQuestion(question);
			answer.setBody(body);
			answer.setReuserId(reuserId);
			answer.setReuserName(reuserName);
			answer.setReuserType(reuserType);
			int sub = answer.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$updateAnswer</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有修改答疑答案的权限！");
		}
	}

	public Answer getAnswer(String tid) throws NoRightException {
		if (interactionUserPriv.getAnswer == 1) {
			return new OracleAnswer(tid);
		} else {
			throw new NoRightException("您没有查看答疑答案的权限！");
		}
	}

	public List getAnswers(Page page, List searchProperty, List orderProperty)
			throws NoRightException {
		if (interactionUserPriv.getAnswers == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			return interactionList.getAnswers(page, searchProperty,
					orderProperty);
		} else {
			throw new NoRightException("您没有查看答疑答案的权限");
		}
	}

	public int getAnswersNum(List serachProperty) throws NoRightException {
		if (interactionUserPriv.getAnswers == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			return interactionList.getAnswersNum(serachProperty);
		} else {
			throw new NoRightException("您没有查看答疑答案的权限");
		}
	}

	public int addEliteQuestion(String title, String body, String key,
			String submituserId, String submituserName, String submituserType,
			String courseId, String dirId) throws NoRightException,
			PlatformException {
		if (interactionUserPriv.addEliteQuestion == 1) {
			OracleEliteQuestion eliteQuestion = new OracleEliteQuestion();
			eliteQuestion.setTitle(title);
			eliteQuestion.setBody(body);
			if (key != null && key.length() > 0)
				eliteQuestion.setKey(key.split(","));
			else
				eliteQuestion.setKey(null);
			eliteQuestion.setSubmituserId(submituserId);
			eliteQuestion.setSubmituserName(submituserName);
			eliteQuestion.setSubmituserType(submituserType);
			eliteQuestion.setCourseId(courseId);
			OracleQuestionEliteDir dir = new OracleQuestionEliteDir();
			dir.setId(dirId);
			eliteQuestion.setDir(dir);
			int sub = eliteQuestion.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$addEliteQuestion</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有添加常见问题答疑问题的权限！");
		}
	}

	public int deleteEliteQuestion(String tid) throws NoRightException,
			PlatformException {
		if (interactionUserPriv.deleteEliteQuestion == 1) {
			int sub = new OracleAnnounce(tid).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteEliteQuestion</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有删除常见问题答疑问题的权限！");
		}
	}

	public int updateEliteQuestion(String id, String title, String body,
			String key, String submituserId, String submituserName,
			String submituserType, String courseId, String dirId)
			throws NoRightException {
		if (interactionUserPriv.updateEliteQuestion == 1) {
			OracleEliteQuestion eliteQuestion = new OracleEliteQuestion();
			eliteQuestion.setTitle(title);
			eliteQuestion.setBody(body);
			if (key != null && key.length() > 0)
				eliteQuestion.setKey(key.split(","));
			else
				eliteQuestion.setKey(null);
			eliteQuestion.setSubmituserId(submituserId);
			eliteQuestion.setSubmituserName(submituserName);
			eliteQuestion.setSubmituserType(submituserType);
			eliteQuestion.setCourseId(courseId);
			OracleQuestionEliteDir dir = new OracleQuestionEliteDir();
			dir.setId(dirId);
			eliteQuestion.setDir(dir);
			int sub = eliteQuestion.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$updateEliteQuestion</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有修改常见问题答疑问题的权限！");
		}
	}

	public EliteQuestion getEliteQuestion(String tid) throws NoRightException {
		if (interactionUserPriv.getEliteQuestion == 1) {
			return new OracleEliteQuestion(tid);
		} else {
			throw new NoRightException("您没有查看常见问题答疑问题的权限！");
		}
	}

	public List getEliteQuestions(Page page, List searchProperty,
			List orderProperty) throws NoRightException {
		if (interactionUserPriv.getEliteQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			return interactionList.getEliteQuestions(page, searchProperty,
					orderProperty);
		} else {
			throw new NoRightException("您没有查看常见问题答疑问题的权限");
		}
	}

	public int addEliteAnswer(String eliteQuestionId, String body,
			String reuserId, String reuserName, String reuserType)
			throws NoRightException, PlatformException {
		if (interactionUserPriv.addEliteAnswer == 1) {
			OracleEliteAnswer eliteAnswer = new OracleEliteAnswer();
			OracleEliteQuestion eliteQuestion = new OracleEliteQuestion();
			eliteQuestion.setId(eliteQuestionId);
			eliteAnswer.setEliteQuestion(eliteQuestion);
			eliteAnswer.setBody(body);
			eliteAnswer.setReuserId(reuserId);
			eliteAnswer.setReuserName(reuserName);
			eliteAnswer.setReuserType(reuserType);
			int sub = eliteAnswer.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$addEliteAnswer</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有添加常见问题答疑答案的权限！");
		}
	}

	public int deleteEliteAnswer(String tid) throws NoRightException,
			PlatformException {
		if (interactionUserPriv.deleteEliteAnswer == 1) {
			int sub = new OracleAnnounce(tid).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteEliteAnswer</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有删除常见问题答疑答案的权限！");
		}
	}

	public int updateEliteAnswer(String id, String eliteQuestionId,
			String body, String reuserId, String reuserName, String reuserType)
			throws NoRightException {
		if (interactionUserPriv.updateEliteAnswer == 1) {
			OracleEliteAnswer eliteAnswer = new OracleEliteAnswer();
			OracleEliteQuestion eliteQuestion = new OracleEliteQuestion();
			eliteQuestion.setId(eliteQuestionId);
			eliteAnswer.setEliteQuestion(eliteQuestion);
			eliteAnswer.setBody(body);
			eliteAnswer.setReuserId(reuserId);
			eliteAnswer.setReuserName(reuserName);
			eliteAnswer.setReuserType(reuserType);
			int sub = eliteAnswer.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$updateEliteAnswer</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有修改常见问题答疑答案的权限！");
		}
	}

	public EliteAnswer getEliteAnswer(String tid) throws NoRightException {
		if (interactionUserPriv.getEliteAnswer == 1) {
			return new OracleEliteAnswer(tid);
		} else {
			throw new NoRightException("您没有查看常见问题答疑答案的权限！");
		}
	}

	public List getEliteAnswers(Page page, List searchProperty,
			List orderProperty) throws NoRightException {
		if (interactionUserPriv.getEliteAnswers == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			return interactionList.getEliteAnswers(page, searchProperty,
					orderProperty);
		} else {
			throw new NoRightException("您没有查看常见问题答疑答案的权限");
		}
	}

	public int addQuestionEliteDir(String name, String note, String courseId,
			String fatherDirId) throws NoRightException, PlatformException {
		if (interactionUserPriv.addQuestionEliteDir == 1) {
			OracleQuestionEliteDir questionEliteDir = new OracleQuestionEliteDir();
			questionEliteDir.setName(name);
			questionEliteDir.setNote(note);
			OracleCourse course = new OracleCourse();
			course.setId(courseId);
			questionEliteDir.setCourse(course);
			OracleQuestionEliteDir dirFather = new OracleQuestionEliteDir();
			dirFather.setId(fatherDirId);
			questionEliteDir.setDirFather(dirFather);
			int sub = questionEliteDir.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$addQuestionEliteDir</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有添加常见问题目录的权限！");
		}
	}

	public int deleteQuestionEliteDir(String tid) throws NoRightException,
			PlatformException {
		if (interactionUserPriv.deleteQuestionEliteDir == 1) {
			int sub = new OracleAnnounce(tid).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteQuestionEliteDir</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有删除常见问题目录的权限！");
		}
	}

	public int updateQuestionEliteDir(String id, String name, String note,
			String courseId, String fatherDirId) throws NoRightException {
		if (interactionUserPriv.updateQuestionEliteDir == 1) {
			OracleQuestionEliteDir questionEliteDir = new OracleQuestionEliteDir();
			questionEliteDir.setName(name);
			questionEliteDir.setNote(note);
			OracleCourse course = new OracleCourse();
			course.setId(courseId);
			questionEliteDir.setCourse(course);
			OracleQuestionEliteDir dirFather = new OracleQuestionEliteDir();
			dirFather.setId(fatherDirId);
			questionEliteDir.setDirFather(dirFather);
			int sub = questionEliteDir.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$updateQuestionEliteDir</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有修改常见问题目录的权限！");
		}
	}

	public QuestionEliteDir getQuestionEliteDir(String tid)
			throws NoRightException {
		if (interactionUserPriv.getQuestionEliteDir == 1) {
			return new OracleQuestionEliteDir(tid);
		} else {
			throw new NoRightException("您没有查看常见问题目录的权限！");
		}
	}

	public List getQuestionEliteDirs(Page page, List searchProperty,
			List orderProperty) throws NoRightException {
		if (interactionUserPriv.getQuestionEliteDirs == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			return interactionList.getQuestionEliteDirs(page, searchProperty,
					orderProperty);
		} else {
			throw new NoRightException("您没有查看常见问题目录的权限");
		}
	}

	public int addForumList(String name, String content, String courseId)
			throws NoRightException, PlatformException {
		if (interactionUserPriv.addForumList == 1) {
			OracleForumList forumList = new OracleForumList();
			forumList.setName(name);
			forumList.setContent(content);
			forumList.setCourseId(courseId);
			int sub = forumList.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$addForumList</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有添加讨论区板块的权限");
		}
	}

	public int deleteForumList(String id) throws NoRightException,
			PlatformException {
		if (interactionUserPriv.deleteForumList == 1) {
			OracleForumList forumList = new OracleForumList();
			forumList.setId(id);
			int sub = forumList.delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteForumList</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有删除讨论区板块的权限");
		}
	}

	public ForumList getForumList(String id) throws NoRightException {
		if (interactionUserPriv.getForumList == 1) {
			return new OracleForumList(id);
		} else {
			throw new NoRightException("您没有删除讨论区板块的权限");
		}
	}

	public int addForum(String title, String body, String submitUserId,
			String forumListId, String courseId, String submitUserName,
			String relatedId) throws NoRightException, PlatformException {
		if (interactionUserPriv.addForum == 1) {
			OracleForum forum = new OracleForum();
			forum.setTitle(title);
			forum.setBody(body);
			forum.setSubmitUserId(submitUserId);
			forum.setForumListId(forumListId);
			forum.setCourseId(courseId);
			forum.setUserName(submitUserName);
			forum.setRelatedID(relatedId);
			int sub = forum.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$addForum</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有添加讨论区文章的权限");
		}
	}

	public Forum getForum(String id) throws NoRightException {
		if (interactionUserPriv.getForum == 1) {
			return new OracleForum(id);
		} else {
			throw new NoRightException("您没有删除讨论区文章的权限");
		}
	}

	public int deleteForum(String id) throws NoRightException,
			PlatformException {
		if (interactionUserPriv.deleteForum == 1) {
			OracleForum forum = new OracleForum();
			forum.setId(id);
			int sub = forum.delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteForum</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有删除讨论区文章的权限");
		}
	}

	public List getForumLists(String courseId) throws NoRightException {
		if (interactionUserPriv.getForumLists == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperties = new ArrayList();
			searchProperties
					.add(new SearchProperty("course_id", courseId, "="));
			return interactionList.getForumLists(searchProperties);
		} else {
			throw new NoRightException("您没有讨论区板块的权限");
		}
	}

	public int updateForumList(String id, String title, String content)
			throws NoRightException, PlatformException {
		if (interactionUserPriv.updateForumList == 1) {
			OracleForumList forumList = new OracleForumList(id);
			forumList.setContent(content);
			forumList.setName(title);
			int sub = forumList.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$updateForumList</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有修改讨论区板块的权限");
		}
	}

	public int updateForum(String id, String title, String body)
			throws NoRightException, PlatformException {
		if (interactionUserPriv.updateForum == 1) {
			OracleForum forum = new OracleForum(id);
			forum.setTitle(title);
			forum.setBody(body);
			int sub = forum.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$updateForum</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有修改讨论区文章的权限");
		}
	}

	public int getTopicForumsNum(String forumlistId) throws NoRightException {
		if (interactionUserPriv.getForums == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("forum_list_id",
					forumlistId, "="));
			searchProperties
					.add(new SearchProperty("related_id", "", "isNull"));
			return interactionList.getForumsNum(searchProperties);
		} else {
			throw new NoRightException("您没有修改讨论区文章的权限");
		}
	}

	public List getTopicForums(Page page, String forumlistId)
			throws NoRightException {
		if (interactionUserPriv.getForums == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("forum_list_id",
					forumlistId, "="));
			searchProperties
					.add(new SearchProperty("related_id", "", "isNull"));
			return interactionList.getForums(page, searchProperties);
		} else {
			throw new NoRightException("您没有修改讨论区文章的权限");
		}
	}

	public int getReplyForumsNum(String forumId) throws NoRightException {
		if (interactionUserPriv.getForums == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperties = new ArrayList();
			searchProperties
					.add(new SearchProperty("related_id", forumId, "="));
			return interactionList.getForumsNum(searchProperties);
		} else {
			throw new NoRightException("您没有修改讨论区文章的权限");
		}
	}

	public List getReplyForums(Page page, String forumId)
			throws NoRightException {
		if (interactionUserPriv.getForums == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperties = new ArrayList();
			searchProperties
					.add(new SearchProperty("related_id", forumId, "="));
			return interactionList.getForums(page, searchProperties);
		} else {
			throw new NoRightException("您没有修改讨论区文章的权限");
		}
	}

	public int getTotalForumsNum(String forumlistId) throws NoRightException {
		if (interactionUserPriv.getForums == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("forum_list_id",
					forumlistId, "="));
			return interactionList.getForumsNum(searchProperties);
		} else {
			throw new NoRightException("您没有修改讨论区文章的权限");
		}
	}

	public int getTopicForumsNum(String forumlistId, String searchTitle,
			String searchUserName) throws NoRightException {
		if (interactionUserPriv.getForums == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperties = new ArrayList();

			if (forumlistId != null && !forumlistId.equals("")
					&& !forumlistId.equalsIgnoreCase("null"))
				searchProperties.add(new SearchProperty("forum_list_id",
						forumlistId, "="));

			searchProperties
					.add(new SearchProperty("related_id", "", "isNull"));

			if (searchTitle != null && !searchTitle.equals("")
					&& !searchTitle.equalsIgnoreCase("null"))
				searchProperties.add(new SearchProperty("title", searchTitle,
						"like"));

			if (searchUserName != null && !searchUserName.equals("")
					&& !searchUserName.equalsIgnoreCase("null"))
				searchProperties.add(new SearchProperty("user_name",
						searchUserName, "like"));
			return interactionList.getForumsNum(searchProperties);
		} else {
			throw new NoRightException("您没有修改讨论区文章的权限");
		}
	}

	public List getTopicForums(Page page, String forumlistId,
			String searchTitle, String searchUserName) throws NoRightException {
		if (interactionUserPriv.getForums == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperties = new ArrayList();
			if (forumlistId != null && !forumlistId.equals("")
					&& !forumlistId.equalsIgnoreCase("null"))
				searchProperties.add(new SearchProperty("forum_list_id",
						forumlistId, "="));

			searchProperties
					.add(new SearchProperty("related_id", "", "isNull"));

			if (searchTitle != null && !searchTitle.equals("")
					&& !searchTitle.equalsIgnoreCase("null"))
				searchProperties.add(new SearchProperty("title", searchTitle,
						"like"));

			if (searchUserName != null && !searchUserName.equals("")
					&& !searchUserName.equalsIgnoreCase("null"))
				searchProperties.add(new SearchProperty("user_name",
						searchUserName, "like"));
			return interactionList.getForums(page, searchProperties);
		} else {
			throw new NoRightException("您没有修改讨论区文章的权限");
		}
	}

	public int deleteTopicForum(String id) throws PlatformException {
		if (interactionUserPriv.deleteForum == 1) {
			Forum forum = new OracleForum();
			forum.setId(id);
			int sub = forum.deleteTopicForum();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteTopicForum</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new PlatformException("您没有修改讨论区文章的权限");
		}
	}

	public int deleteReplyForum(String id) throws PlatformException {
		if (interactionUserPriv.deleteForum == 1) {
			Forum forum = new OracleForum();
			forum.setId(id);
			int sub = forum.delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteReplyForum</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new PlatformException("您没有修改讨论区文章的权限");
		}
	}

	public TestManage creatTestManage() {
		TestFactory testFactory = TestFactory.getInstance();
		TestPriv priv = testFactory.getTestPriv();
		return testFactory.creatTestManage(priv);
	}

	public VoteNormalManage creatVoteNormalManage() {
		VoteFactory voteFactory = VoteFactory.getInstance();
		return voteFactory.creatVoteNormalManage();
	}

	public BasicScoreManage createBasicScoreManage() {
		PlatformFactory platformfactory = PlatformFactory.getInstance();
		return platformfactory.creatBasicScoreManage();
	}

//	public BasicActivityManage createBasicActivityManage() {
//		PlatformFactory platformfactory = PlatformFactory.getInstance();
//		return platformfactory.creatBasicActivityManage();
//	}

	public CoursewareManage createCoursewareManage() {
		CoursewareFactory coursewareFactory = CoursewareFactory.getInstance();
		CoursewareManagerPriv priv = coursewareFactory
				.getCoursewareManagerPriv("");
		priv.addChapterPage = 1;
		priv.addOnlineCwInfo = 1;
		priv.addWhatyCoursewareTemplate = 1;
		priv.buildNormalHttpCourseware = 1;
		priv.buildWhatyOnlineCourseware = 1;
		priv.buildWhatyUploadCourseware = 1;
		priv.deleteOnlineCwInfo = 1;
		priv.deleteWhatyCoursewareTemplate = 1;
		priv.filemanage = 1;
		priv.getCourseware = 1;
		priv.getOnlineCwInfo = 1;
		priv.getOnlineCwRootDir = 1;
		priv.getWhatyCoursewareTemplate = 1;
		priv.updateNormalHttpLink = 1;
		priv.updateUploadEnterFile = 1;
		priv.updateWhatyCoursewareTemplate = 1;
		return coursewareFactory.creatCoursewareManage(priv);
	}

	public TestPriv getTestPriv() {
		TestFactory testFactory = TestFactory.getInstance();
		TestPriv priv = testFactory.getTestPriv();
		return priv;
	}

	public List getCoursewares(String teachclassId) throws PlatformException {
		OracleTeachClass teachclass = new OracleTeachClass();
		teachclass.setId(teachclassId);
		return teachclass.getCoursewares();
	}

	public Courseware getCourseware(String coursewareId)
			throws PlatformException {
		// TODO Auto-generated method stub
		try {
			return new OracleCourseware(coursewareId);
		} catch (Exception e) {
			throw new PlatformException("获取课件信息错误");
		}
	}

	public List getActiveCoursewares(String teachclassId)
			throws PlatformException {
		OracleTeachClass teachclass = new OracleTeachClass();
		teachclass.setId(teachclassId);
		return teachclass.getActiveCoursewares();
	}

	public int deleteCoursewares(String cwareId) throws PlatformException {
		OracleCourseware cware = new OracleCourseware();
		cware.setId(cwareId);
		int sub = cware.delete();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.interactionUserPriv.getUserId()
								+ "</whaty><whaty>BEHAVIOR$|$deleteCoursewares</whaty><whaty>STATUS$|$"
								+ sub
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;
	}

	public Course getCourse(String teachclassId) throws PlatformException {
		OracleTeachClass teachclass = new OracleTeachClass(teachclassId);

		// return teachclass.getOpenCourse().getCourse();
		return null;
	}

	public List getTeachers(String teachclassId) throws PlatformException {
		OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
		return basicActivityList.getTeachersByTeachClass(teachclassId);
	}

	public int addTeachClass(HttpServletRequest request)
			throws PlatformException {
		OracleInteractionList interactionList = new OracleInteractionList();
		int sub = interactionList.addTeachClass(request);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.interactionUserPriv.getUserId()
								+ "</whaty><whaty>BEHAVIOR$|$addTeachClass</whaty><whaty>STATUS$|$"
								+ sub
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;
	}

	public int deleteTeachClass(String id) throws PlatformException {
		OracleInteractionList interactionList = new OracleInteractionList();
		int sub = interactionList.deleteTeachClass(id);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.interactionUserPriv.getUserId()
								+ "</whaty><whaty>BEHAVIOR$|$deleteTeachClass</whaty><whaty>STATUS$|$"
								+ sub
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;
	}

	public InteractionTeachClass getTeachClass(String teachclassId, String type)
			throws PlatformException {
		OracleInteractionList interactionList = new OracleInteractionList();
		return interactionList.getTeachClass(teachclassId, type);
	}

	public InteractionTeachClass getTeachClass(String id)
			throws PlatformException {
		return new OracleInteractionTeachClass(id);
	}

	public List getTeachClassList(String teachclassId, String type)
			throws PlatformException {
		OracleInteractionList interactionList = new OracleInteractionList();
		ArrayList searchProperties = new ArrayList();
		ArrayList orderProperties = new ArrayList();
		searchProperties.add(new SearchProperty("teachclass_id", teachclassId));
		searchProperties.add(new SearchProperty("type", type));
		orderProperties.add(new OrderProperty("id", "1"));
		return interactionList.getTeachClassList(searchProperties,
				orderProperties);
	}

	public List getTeachClassList(Page page, String teachclassId, String type,
			String title) throws PlatformException {
		OracleInteractionList interactionList = new OracleInteractionList();
		ArrayList searchProperties = new ArrayList();
		ArrayList orderProperties = new ArrayList();
		searchProperties.add(new SearchProperty("teachclass_id", teachclassId));
		searchProperties.add(new SearchProperty("type", type));
		if (title != null && !title.equals("null")) {
			searchProperties.add(new SearchProperty("title", title, "like"));
		}
		orderProperties.add(new OrderProperty("id", "1"));
		return interactionList.getTeachClassList(page, searchProperties,
				orderProperties);
	}

	public List getActiveTeachClassList(Page page, String teachclassId,
			String type, String title) throws PlatformException {
		OracleInteractionList interactionList = new OracleInteractionList();
		ArrayList searchProperties = new ArrayList();
		ArrayList orderProperties = new ArrayList();
		searchProperties.add(new SearchProperty("teachclass_id", teachclassId));
		searchProperties.add(new SearchProperty("type", type));
		searchProperties.add(new SearchProperty("status", "1"));
		if (title != null && !title.equals("null")) {
			searchProperties.add(new SearchProperty("title", title, "like"));
		}
		orderProperties.add(new OrderProperty("id", "1"));
		return interactionList.getTeachClassList(page, searchProperties,
				orderProperties);
	}

	public List getActiveTeachClassList(String teachclassId, String type)
			throws PlatformException {
		OracleInteractionList interactionList = new OracleInteractionList();
		ArrayList searchProperties = new ArrayList();
		ArrayList orderProperties = new ArrayList();
		searchProperties.add(new SearchProperty("teachclass_id", teachclassId));
		searchProperties.add(new SearchProperty("type", type));
		searchProperties.add(new SearchProperty("status", "1"));
		orderProperties.add(new OrderProperty("id", "1"));
		return interactionList.getTeachClassList(searchProperties,
				orderProperties);
	}

	public int getActiveTeachClassListNum(String teachclassId, String type)
			throws PlatformException {
		OracleInteractionList interactionList = new OracleInteractionList();
		ArrayList searchProperties = new ArrayList();
		ArrayList orderProperties = new ArrayList();
		searchProperties.add(new SearchProperty("teachclass_id", teachclassId));
		searchProperties.add(new SearchProperty("type", type));
		searchProperties.add(new SearchProperty("status", "1"));
		orderProperties.add(new OrderProperty("id", "1"));
		return interactionList.getTeachClassNum(searchProperties,
				orderProperties);
	}

	public int getActiveTeachClassListNum(String teachclassId, String type,
			String title) throws PlatformException {
		OracleInteractionList interactionList = new OracleInteractionList();
		ArrayList searchProperties = new ArrayList();
		ArrayList orderProperties = new ArrayList();
		searchProperties.add(new SearchProperty("teachclass_id", teachclassId));
		searchProperties.add(new SearchProperty("type", type));
		searchProperties.add(new SearchProperty("status", "1"));
		if (title != null && !title.equals("null")) {
			searchProperties.add(new SearchProperty("title", title, "like"));
		}
		orderProperties.add(new OrderProperty("id", "1"));
		return interactionList.getTeachClassNum(searchProperties,
				orderProperties);
	}

	public int changeTeachClassStatus(String paperId, String status)
			throws PlatformException {
		OracleInteractionTeachClass teachClass = new OracleInteractionTeachClass();
		teachClass.setId(paperId);
		teachClass.setStatus(status);
		return teachClass.reverseActive();
	}

	public int getTeachClassListNum(String teachclassId, String type)
			throws PlatformException {
		OracleInteractionList interactionList = new OracleInteractionList();
		ArrayList searchProperties = new ArrayList();
		ArrayList orderProperties = new ArrayList();
		searchProperties.add(new SearchProperty("teachclass_id", teachclassId));
		searchProperties.add(new SearchProperty("type", type));
		orderProperties.add(new OrderProperty("id", "1"));
		return interactionList.getTeachClassNum(searchProperties,
				orderProperties);
	}

	public int getTeachClassListNum(String teachclassId, String type,
			String title) throws PlatformException {
		OracleInteractionList interactionList = new OracleInteractionList();
		ArrayList searchProperties = new ArrayList();
		ArrayList orderProperties = new ArrayList();
		searchProperties.add(new SearchProperty("teachclass_id", teachclassId));
		searchProperties.add(new SearchProperty("type", type));
		if (title != null && !title.equals("null")) {
			searchProperties.add(new SearchProperty("title", title, "like"));
		}
		orderProperties.add(new OrderProperty("id", "1"));
		return interactionList.getTeachClassNum(searchProperties,
				orderProperties);
	}

	public int updateTeachClass(HttpServletRequest request)
			throws PlatformException {
		OracleInteractionList interactionList = new OracleInteractionList();
		int sub = interactionList.updateTeachClass(request);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.interactionUserPriv.getUserId()
								+ "</whaty><whaty>BEHAVIOR$|$updateTeachClass</whaty><whaty>STATUS$|$"
								+ sub
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;
	}

	public String getTeachRule(String teachclassId) throws PlatformException {
		OracleInteractionList interactionList = new OracleInteractionList();
		return interactionList.getTeachRule(teachclassId);
	}

	public int updateTeachRule(String teachclassId, String rule)
			throws PlatformException {
		OracleInteractionList interactionList = new OracleInteractionList();
		int sub = interactionList.updateTeachRule(teachclassId, rule);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.interactionUserPriv.getUserId()
								+ "</whaty><whaty>BEHAVIOR$|$updateTeachRule</whaty><whaty>STATUS$|$"
								+ sub
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;
	}

	public int getHomeworkTimes(String teachclassId) throws PlatformException {
		OracleInteractionList interactionList = new OracleInteractionList();
		return interactionList.getHomeworkTimes(teachclassId);
	}

	public int getForumTimes(String teachclassId) throws PlatformException {
		OracleInteractionList interactionList = new OracleInteractionList();
		return interactionList.getForumTimes(teachclassId);
	}

	public int getQuestionTimes(String teachclassId) throws PlatformException {
		OracleInteractionList interactionList = new OracleInteractionList();
		return interactionList.getQuestionTimes(teachclassId);
	}

	public int getAnswerTimes(String teachclassId) throws PlatformException {
		OracleInteractionList interactionList = new OracleInteractionList();
		return interactionList.getAnswerTimes(teachclassId);
	}

	public List getTheachItem(String id) {
		List itemList = new ArrayList();
		OracleCourseItem teachItem = new OracleCourseItem();
		teachItem.setId(id);
		if (teachItem.isIdExist() > 0) {
			itemList = teachItem.getItemById(id);
		} else {
			try {
				teachItem.setPingjia("1");
				teachItem.setBoke("1");
				teachItem.setZiyuan("1");
				teachItem.setGonggao("1");
				teachItem.setDaohang("1");
				teachItem.setDaoxue("0");
				teachItem.setDayi("1");
				teachItem.setSmzuoye("0");
				teachItem.setZuoye("1");
				teachItem.setZice("1");
				teachItem.setKaoshi("0");
				teachItem.setShiyan("0");
				teachItem.setZfx("1");
				teachItem.setTaolun("1");
				teachItem.setZxdayi("0");

				teachItem.add();
				itemList = teachItem.getItemById(id);
			} catch (Exception e) {
			}
		}
		return itemList;
	}

	// public WhatyEditorConfig getWhatyEditorConfig(HttpSession session)
	// throws PlatformException {
	// ServletContext application = session.getServletContext();
	// InteractionConfig config = (InteractionConfig) application
	// .getAttribute("interactionConfig");
	// PlatformConfig platformConfig = (PlatformConfig) application
	// .getAttribute("platformConfig");
	// WhatyEditorConfig editorConfig = new WhatyEditorConfig();
	// editorConfig.setAppRootURI(platformConfig.getPlatformWebAppUriPath());
	// editorConfig.setEditorRefURI("WhatyEditor/");
	// editorConfig.setEditorURI(platformConfig.getPlatformWebAppUriPath()
	// + "WhatyEditor/");
	// editorConfig.setUploadAbsPath(config.getInteractionWebIncomingAbsPath()
	// + this.interactionUserPriv.getUserId() + "/");
	// editorConfig.setUploadURI(config.getInteractionWebIncomingUriPath()
	// + this.interactionUserPriv.getUserId() + "/");
	// return editorConfig;
	// }

	public CoursewareManagerPriv getCoursewareManagerPriv() {
		CoursewareFactory coursewareFactory = CoursewareFactory.getInstance();
		CoursewareManagerPriv priv = coursewareFactory
				.getCoursewareManagerPriv("");
		priv.addChapterPage = 1;
		priv.addOnlineCwInfo = 1;
		priv.addWhatyCoursewareTemplate = 1;
		priv.buildNormalHttpCourseware = 1;
		priv.buildWhatyOnlineCourseware = 1;
		priv.buildWhatyUploadCourseware = 1;
		priv.deleteOnlineCwInfo = 1;
		priv.deleteWhatyCoursewareTemplate = 1;
		priv.filemanage = 1;
		priv.getCourseware = 1;
		priv.getOnlineCwInfo = 1;
		priv.getOnlineCwRootDir = 1;
		priv.getWhatyCoursewareTemplate = 1;
		priv.updateNormalHttpLink = 1;
		priv.updateUploadEnterFile = 1;
		priv.updateWhatyCoursewareTemplate = 1;
		return priv;
	}

	@Override
	public int updateQuestionType(String id, String questionType)
			throws NoRightException {
		OracleInteractionList interactionList = new OracleInteractionList();
		int sub = interactionList.updateQuestionType(id, questionType);

		return sub;
	}

	// 考试辅导答疑——提问20101227
	public int addExamQuestion(String title, String body, String questionType,
			String submituserId, String submituserName, String submituserType,
			String exambatchId) throws NoRightException, PlatformException {
		if (interactionUserPriv.addExamQuestion == 1) {
			OracleExamQuestion question = new OracleExamQuestion();
			boolean reStatus = false;
			question.setTitle(title);
			question.setBody(body);
			question.setQuestionType(questionType);
			question.setSubmituserId(submituserId);
			question.setSubmituserName(submituserName);
			question.setSubmituserType(submituserType);
			question.setExambatchId(exambatchId);
			int sub = question.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$addaddExamQuestion</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有添加答疑问题的权限！");
		}
	}

	public int updateExamQuestion(String title, String body, String question_id)
			throws NoRightException, PlatformException {
		if (interactionUserPriv.addExamQuestion == 1) {
			OracleExamQuestion question = new OracleExamQuestion(question_id);
			question.setTitle(title);
			question.setBody(body);
			int sub = question.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$updateExamQuestion</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有添加答疑问题的权限！");
		}
	}

	public int getExamQuestionsNum(String exambatchId, String title,
			String questionType) throws NoRightException {
		if (interactionUserPriv.getExamQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("title", title, "like"));

			searchProperty.add(new SearchProperty("exambatch_id", exambatchId,
					"="));
			searchProperty.add(new SearchProperty("questiontype_id",
					questionType, "="));
			return interactionList.getExamQuestionsNum(searchProperty);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限");
		}
	}

	public String getExamNewsCount(String exambatchId, String student_id)
			throws NoRightException {
		if (interactionUserPriv.getExamQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			return interactionList.getExamNewsCount(exambatchId, student_id);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限");
		}
	}

	public int getExamQuestionsNumSelf(String exambatchId, String student_id)
			throws NoRightException {
		if (interactionUserPriv.getExamQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperty = new ArrayList();
			return interactionList.getExamQuestionsNumSelf(exambatchId,
					student_id);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限");
		}
	}

	public int getExamQuestionsNumFocus(String exambatchId, String student_id)
			throws NoRightException {
		if (interactionUserPriv.getExamQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperty = new ArrayList();
			return interactionList.getExamQuestionsNumFocus(exambatchId,
					student_id);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限");
		}
	}

	public int getExamQuestionsNumYes(String exambatchId, String student_id)
			throws NoRightException {
		if (interactionUserPriv.getExamQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperty = new ArrayList();
			return interactionList.getExamQuestionsNumYes(exambatchId,
					student_id);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限");
		}
	}

	public int getExamQuestionsNumNo(String exambatchId, String student_id)
			throws NoRightException {
		if (interactionUserPriv.getExamQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperty = new ArrayList();
			return interactionList.getExamQuestionsNumNo(exambatchId,
					student_id);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限");
		}
	}

	public int getExamNews(String exambatchId, String student_id)
			throws NoRightException {
		if (interactionUserPriv.getExamQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperty = new ArrayList();
			return interactionList.getExamNews(exambatchId, student_id);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限");
		}
	}

	public List getExamQuestions(Page page, String exambatchId, String title,
			String questionType) throws NoRightException {
		if (interactionUserPriv.getExamQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("title", title, "like"));
			if (exambatchId != null && !exambatchId.equals("null")) {
				searchProperty.add(new SearchProperty("exambatch_id",
						exambatchId, "="));
			}
			if (questionType != null && !questionType.equals("null")) {
				searchProperty.add(new SearchProperty("questiontype_id",
						questionType, "="));
			}
			List orderPropertys = new ArrayList();
			orderPropertys.add(new OrderProperty("publish_date",
					OrderProperty.DESC));
			return interactionList.getExamQuestions(page, searchProperty,
					orderPropertys);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限");
		}
	}

	public List getExamQuestionsSelf(Page page, String exambatchId,
			String student_id) throws NoRightException {
		if (interactionUserPriv.getExamQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperty = new ArrayList();
			return interactionList.getExamQuestionsSelf(page, exambatchId,
					student_id);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限");
		}
	}

	public List getExamQuestionsFocus(Page page, String exambatchId,
			String student_id) throws NoRightException {
		if (interactionUserPriv.getExamQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperty = new ArrayList();
			return interactionList.getExamQuestionsFocus(page, exambatchId,
					student_id);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限");
		}
	}

	public List getExamQuestionsYes(Page page, String exambatchId,
			String student_id) throws NoRightException {
		if (interactionUserPriv.getExamQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperty = new ArrayList();
			return interactionList.getExamQuestionsYes(page, exambatchId,
					student_id);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限");
		}
	}

	public List getExamQuestionsNo(Page page, String exambatchId,
			String student_id) throws NoRightException {
		if (interactionUserPriv.getExamQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperty = new ArrayList();
			return interactionList.getExamQuestionsNo(page, exambatchId,
					student_id);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限");
		}
	}

	public List getExamNews(Page page, String exambatchId, String student_id)
			throws NoRightException {
		if (interactionUserPriv.getExamQuestions == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			List searchProperty = new ArrayList();
			return interactionList.getExamNews(page, exambatchId, student_id);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限");
		}
	}

	public ExamQuestion getExamQuestion(String tid) throws NoRightException {
		if (interactionUserPriv.getExamQuestion == 1) {
			return new OracleExamQuestion(tid);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限！");
		}
	}

	public ExamAnswer getExamAnswerTeacher(String tid) throws NoRightException {
		if (interactionUserPriv.getExamAnswer == 1) {
			OracleExamAnswer answer = new OracleExamAnswer();
			return answer.getExamAnswerTeacher(tid);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限！");
		}
	}

	public ExamAnswer getExamAnswerKey(String tid) throws NoRightException {
		if (interactionUserPriv.getExamAnswer == 1) {
			OracleExamAnswer answer = new OracleExamAnswer();
			return answer.getExamAnswerKey(tid);
		} else {
			throw new NoRightException("您没有查看答疑问题的权限！");
		}
	}

	public List getExamAnswers(Page page, String question_id)
			throws NoRightException {
		if (interactionUserPriv.getExamAnswers == 1) {
			OracleInteractionList interactionList = new OracleInteractionList();
			return interactionList.getExamAnswers(page, question_id);
		} else {
			throw new NoRightException("您没有查看答疑答案的权限");
		}
	}

	public int addExamAnswer(String questionId, String body, String publishId,
			String publishName, String publishType) throws NoRightException,
			PlatformException {
		if (interactionUserPriv.addExamAnswer == 1) {
			OracleExamAnswer answer = new OracleExamAnswer();
			OracleExamQuestion question = new OracleExamQuestion();
			question.setId(questionId);
			answer.setQuestion(question);
			answer.setBody(body);
			answer.setPublishId(publishId);
			answer.setPublishName(publishName);
			answer.setPublishType(publishType);
			int sub = answer.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$addAnswer</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有添加答疑答案的权限！");
		}
	}

	public int updateExamAnswerToKey(String answer_id) throws NoRightException,
			PlatformException {
		if (interactionUserPriv.updateExamAnswer == 1) {
			OracleExamAnswer answer = new OracleExamAnswer();
			answer.setId(answer_id);
			int sub = answer.toKey();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.interactionUserPriv.getUserId()
									+ "</whaty><whaty>BEHAVIOR$|$addAnswer</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new NoRightException("您没有修改答疑答案的权限！");
		}
	}

	public int addQuestionToFocus(String question_id, String student_id)
			throws NoRightException, PlatformException {
		if (interactionUserPriv.addExamUserQuestion == 1) {
			OracleUserQuestion userquestion = new OracleUserQuestion();
			OracleExamQuestion question = new OracleExamQuestion();
			question.setId(question_id);
			userquestion.setQuestion(question);
			userquestion.setUserId(student_id);
			return userquestion.add();
		} else {
			throw new NoRightException("您没有设置关注问题的权限！");
		}
	}

	public int deleteQuestionToFocus(String question_id, String student_id)
			throws NoRightException, PlatformException {
		if (interactionUserPriv.deleteExamUserQuestion == 1) {
			OracleUserQuestion userquestion = new OracleUserQuestion();
			OracleExamQuestion question = new OracleExamQuestion();
			question.setId(question_id);
			userquestion.setQuestion(question);
			userquestion.setUserId(student_id);
			return userquestion.delete();
		} else {
			throw new NoRightException("您没有设置关注问题的权限！");
		}
	}

	public ExamUserQuestion getExamUserQuestion(String question_id,
			String user_id) throws NoRightException {
		if (interactionUserPriv.getExamUserQuestion == 1) {
			OracleUserQuestion answer = new OracleUserQuestion();
			return answer.getExamUserQuestion(question_id, user_id);
		} else {
			throw new NoRightException("您没有查看关注问题的权限！");
		}
	}
}
