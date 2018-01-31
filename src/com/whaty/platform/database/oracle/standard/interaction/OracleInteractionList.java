package com.whaty.platform.database.oracle.standard.interaction;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleTeachClass;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourse;
import com.whaty.platform.database.oracle.standard.interaction.announce.OracleAnnounce;
import com.whaty.platform.database.oracle.standard.interaction.answer.OracleAnswer;
import com.whaty.platform.database.oracle.standard.interaction.answer.OracleEliteAnswer;
import com.whaty.platform.database.oracle.standard.interaction.answer.OracleEliteQuestion;
import com.whaty.platform.database.oracle.standard.interaction.answer.OracleQuestion;
import com.whaty.platform.database.oracle.standard.interaction.answer.OracleQuestionEliteDir;
import com.whaty.platform.database.oracle.standard.interaction.exam.OracleExamAnswer;
import com.whaty.platform.database.oracle.standard.interaction.exam.OracleExamQuestion;
import com.whaty.platform.database.oracle.standard.interaction.forum.OracleForum;
import com.whaty.platform.database.oracle.standard.interaction.forum.OracleForumList;
import com.whaty.platform.database.oracle.standard.interaction.homework.OracleHomework;
import com.whaty.platform.database.oracle.standard.interaction.homework.OracleHomeworkCheck;
import com.whaty.platform.database.oracle.standard.interaction.homework.OracleInHomework;
import com.whaty.platform.database.oracle.standard.interaction.homework.OracleInHomeworkCheck;
import com.whaty.platform.interaction.InteractionList;
import com.whaty.platform.interaction.InteractionTeachClass;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleInteractionList implements InteractionList {
	private String SQLANNOUNCE = "select id,title,body,publisher_id,publisher_name,publisher_type,publish_date,course_id"
			+ " from (select id,title,body,publisher_id,publisher_name,publisher_type,to_char(publish_date,'yyyy-mm-dd hh24:mi:ss') as publish_date,course_id from interaction_announce_info) ";

	private String SQLTEACHCLASS = "select id,teachclass_id,title,content,type,status,publish_date,file_link"
			+ " from (select id,teachclass_id,title,content,type,status,to_char(publish_date,'yyyy-mm-dd hh24:mi:ss') as publish_date,file_link from interaction_teachclass_info) ";

	private String SQLHOMEWORKCHECK = "select id,homework_id,body,date,checkuser_id,checkuser_name,checkuser_type"
			+ " from (select id,homework_id,body,to_char(date,'yyyy-mm-dd') as date,checkuser_id,checkuser_name,checkuser_type from interaction_homework_check) ";

	private String SQLHOMEWORK = "select id,title,body,date,handin_date,course_id,check_status,submiter_id,submiter_name,submiter_type"
			+ " from (select id,title,body,to_date(date,'yyyy-mm-dd') as date,to_date(handin_date,'yyyy-mm-dd') as handin_date,course_id,check_status,submiter_id,submiter_name,submiter_type from interaction_homework_info) ";

	private String SQLINHOMEWORK = "select id,title,file,body,date,submiter_id,submiter_name,submiter_type,homework_id,check_status"
			+ " from (select id,title,file,body,to_date(date,'yyyy-mm-dd') as date,submiter_id,submiter_name,submiter_type,homework_id,check_status from interaction_inhomework_info) ";

	private String SQLINHOMEWORKCHECK = "select id,inhomework_id,body,score,date,checkuser_id,checkuserName,checkuser_type"
			+ " from (select id,homework_id,body,score,to_char(date,'yyyy-mm-dd') as date,checkuser_id,checkuserName,checkuser_type from interaction_inhomework_check) ";

	// bjsy2
	// private String SQLQUESTION = "select
	// id,title,body,key,publish_date,course_id,submituser_id,submituser_name,submituser_type,re_status,site_id
	// "+
	// "from (select iqi.id,iqi.title,body,key,to_char(publish_date,'yyyy-mm-dd
	// hh24:mi:ss') as
	// publish_date,course_id,submituser_id,submituser_name,submituser_type,re_status,site.id
	// as site_id from interaction_question_info iqi, pr_student_info
	// esi,pe_student st,pe_site site where esi.id=iqi.submituser_id and
	// st.id=esi.id and st.fk_site_id =site.id ) ";
	private String SQLQUESTION = "select id,title,body,key,publish_date,course_id,submituser_id,submituser_name,submituser_type,re_status,question_type,click_num"
			+ " from (select iqi.id,iqi.title,body,key,to_char(publish_date,'yyyy-mm-dd hh24:mi:ss') as publish_date,course_id,submituser_id,submituser_name,submituser_type,re_status,iqt.name as question_type,click_num from interaction_question_info iqi,interaction_question_type iqt"
			+ " where iqi.question_type=iqt.id(+))";
	
	private String SQLEXAMQUESTION = "select id,title,body,publish_date,exambatch_id,submituser_id,submituser_name,submituser_type,question_type,questiontype_id,click_num"
			+ " from (select eqi.id,eqi.title,body,to_char(publish_date,'yyyy-mm-dd') as publish_date,exambatch_id,submituser_id,submituser_name,"
			+ "submituser_type,eqt.name as question_type,eqt.id as questiontype_id,click_num from exam_question_info eqi,exam_question_type eqt" + " where eqi.question_type=eqt.id(+))";

	private String SQLANSWER = "select id,question_id,body,publish_date,reuser_id,reuser_name,reuser_type"
			+ " from (select id,question_id,body,to_char(publish_date,'yyyy-mm-dd') as publish_date,reuser_id,reuser_name,reuser_type from interaction_answer_info) ";

	private String SQLQUESTIONELITEDIR = "select id,name,note,date,course_id,dir_father"
			+ " from (select id,name,note,to_char(date,'yyyy-mm-dd') as date,course_id,dir_father from interaction_question_elitedir) ";

	private String SQLELITEQUESTION = "select id,title,body,key,date,course_id,dir_id,submituser_id,submituser_name,submituser_type"
			+ " from (select id,title,body,key,to_char(date,'yyyy-mm-dd') as date,course_id,dir_id,submituser_id,submituser_name,submituser_type from interaction_elitequestion_info) ";

	private String SQLELITEANSWER = "select id,elitequestion_id,body,date,reuser_id,reuser_name,reuser_type"
			+ " from (select id,elitequestion_id,body,to_char(date,'yyyy-mm-dd') as date,reuser_id,reuser_name,reuser_type from interaction_eliteanswer_info) ";

	private String SQLFORUMlIST = "select id, name, content, create_date, num, course_id, isbrowse, isspeak  from (select id, name, content, to_char(create_date,'yyyy-mm-dd') as create_date, num, course_id, isbrowse, isspeak from interaction_forumlist_info) ";

	private String SQLFORUM = "select id, title, body, submituser_id, publish_date, course_id, click, del, reply_number, forum_list_id, user_name, forum_elite, read_times, user_level, user_ip, related_id from "
			+ " (select id, title, body, submituser_id, to_char(publish_date, 'yyyy-mm-dd hh24:mi:ss') as publish_date, course_id, click, del, reply_number, forum_list_id, user_name, forum_elite, read_times, user_level, user_ip, related_id from interaction_forum_info) ";

	public int getAnnouncesNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLANNOUNCE + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getAnnounces(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLANNOUNCE + Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList announces = null;
		try {
			db = new dbpool();
			announces = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleAnnounce announce = new OracleAnnounce();
				announce.setId(rs.getString("id"));
				announce.setTitle(rs.getString("title"));
				announce.setBody(rs.getString("body"));
				announce.setPublisherId(rs.getString("publisher_id"));
				announce.setPublisherName(rs.getString("publisher_name"));
				announce.setPublisherType(rs.getString("publisher_type"));
				announce.setDate(rs.getString("publish_date"));
				announce.setCourseId(rs.getString("course_id"));
				announces.add(announce);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return announces;
	}

	public List getTeachClassList(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = SQLTEACHCLASS + Conditions.convertToCondition(searchproperty, orderproperty);
		ArrayList teachClassList = new ArrayList();
		try {
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleInteractionTeachClass teachClass = new OracleInteractionTeachClass();
				teachClass.setId(rs.getString("id"));
				teachClass.setContent(rs.getString("content"));
				teachClass.setStatus(rs.getString("status"));
				teachClass.setTeachclass_id(rs.getString("teachclass_id"));
				teachClass.setTitle(rs.getString("title"));
				teachClass.setType(rs.getString("type"));
				teachClass.setPublish_date(rs.getString("publish_date"));
				teachClass.setFileLink(rs.getString("file_link"));
				teachClassList.add(teachClass);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return teachClassList;
	}

	public int getHomeworksNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLHOMEWORK + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getHomeworks(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLHOMEWORK + Conditions.convertToAndCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		boolean checkStatus = false;
		ArrayList homeworks = null;
		try {
			db = new dbpool();
			homeworks = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				checkStatus = false;
				OracleHomework homework = new OracleHomework();
				homework.setId(rs.getString("id"));
				homework.setTitle(rs.getString("title"));
				homework.setBody(rs.getString("body"));
				homework.setDate(rs.getString("date"));
				homework.setHandinDate(rs.getString("handinDate"));
				homework.setSubmituserId(rs.getString("submiterId"));
				homework.setSubmituserName(rs.getString("submiter_name"));
				homework.setSubmituserType(rs.getString("submiter_type"));
				if (rs.getString("check_status").equals("1"))
					checkStatus = true;
				homework.setCheckStatus(checkStatus);
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("courseId"));
				homework.setCourse(course);
				homeworks.add(homework);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return homeworks;
	}

	public int getHomeworkChecksNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLHOMEWORKCHECK + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getHomeworkChecks(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLHOMEWORK + Conditions.convertToAndCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList homeworkChecks = null;
		try {
			db = new dbpool();
			homeworkChecks = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleHomeworkCheck homeworkCheck = new OracleHomeworkCheck();
				homeworkCheck.setId(rs.getString("id"));
				OracleHomework homework = new OracleHomework();
				homework.setId(rs.getString("homework_id"));
				homeworkCheck.setHomework(homework);
				homeworkCheck.setBody(rs.getString("body"));
				homeworkCheck.setDate(rs.getString("date"));
				homeworkCheck.setCheckuserId(rs.getString("checkuserId"));
				homeworkCheck.setCheckuserName(rs.getString("checkuserName"));
				homeworkCheck.setCheckuserType(rs.getString("checkuserType"));
				homeworkChecks.add(homeworkCheck);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return homeworkChecks;
	}

	public int getInHomeworksNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLINHOMEWORK + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getInHomeworks(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLINHOMEWORK + Conditions.convertToAndCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		boolean checkStatus = false;
		ArrayList inHomeworks = null;
		try {
			db = new dbpool();
			inHomeworks = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				checkStatus = false;
				OracleInHomework inHomework = new OracleInHomework();
				inHomework.setId(rs.getString("id"));
				inHomework.setTitle(rs.getString("title"));
				inHomework.setBody(rs.getString("body"));
				inHomework.setDate(rs.getString("date"));
				inHomework.setSubmituserId(rs.getString("submiterId"));
				inHomework.setSubmituserName(rs.getString("submiter_name"));
				inHomework.setSubmituserType(rs.getString("submiter_type"));
				OracleHomework homework = new OracleHomework();
				homework.setId(rs.getString("homeworkId"));
				inHomework.setHomework(homework);
				if (rs.getString("check_status").equals("1"))
					checkStatus = true;
				inHomework.setCheckStatus(checkStatus);
				inHomeworks.add(inHomework);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return inHomeworks;
	}

	public int getInHomeworkChecksNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLINHOMEWORKCHECK + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getInHomeworkChecks(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLINHOMEWORKCHECK + Conditions.convertToAndCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList inHomeworkChecks = null;
		try {
			db = new dbpool();
			inHomeworkChecks = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleInHomeworkCheck inHomeworkCheck = new OracleInHomeworkCheck();
				inHomeworkCheck.setId(rs.getString("id"));
				OracleInHomework inHomework = new OracleInHomework();
				inHomework.setId(rs.getString("inhomework_id"));
				inHomeworkCheck.setInHomework(inHomework);
				inHomeworkCheck.setBody(rs.getString("body"));
				inHomeworkCheck.setScore(rs.getString("score"));
				inHomeworkCheck.setDate(rs.getString("date"));
				inHomeworkCheck.setCheckuserId(rs.getString("checkuserId"));
				inHomeworkCheck.setCheckuserName(rs.getString("checkuserName"));
				inHomeworkCheck.setCheckuserType(rs.getString("checkusertype"));
				inHomeworkChecks.add(inHomeworkCheck);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return inHomeworkChecks;
	}

	public int getQuestionsNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLQUESTION + Conditions.convertToCondition(searchproperty, null);
//		System.out.println(sql);
		int i = db.countselect(sql);
		return i;
	}

	public List getQuestions(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLQUESTION + Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		boolean reStatus = false;
		ArrayList questions = null;
		try {
			db = new dbpool();
			questions = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				reStatus = false;
				OracleQuestion question = new OracleQuestion();
				question.setId(rs.getString("id"));
				question.setTitle(rs.getString("title"));
				question.setBody(rs.getString("body"));
				if (rs.getString("key") != null && rs.getString("key").length() > 0)
					question.setKey(rs.getString("key").split(","));
				else
					question.setKey(null);
				question.setDate(rs.getString("publish_date"));
				question.setCourseId(rs.getString("course_id"));
				question.setSubmituserId(rs.getString("submituser_id"));
				question.setSubmituserName(rs.getString("submituser_name"));
				question.setSubmituserType(rs.getString("submituser_type"));
				question.setQuestionType(rs.getString("question_type"));
				question.setClickNum(rs.getString("click_num"));
				if (rs.getString("re_status").equals("1"))
					reStatus = true;
				question.setReStatus(reStatus);
				questions.add(question);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return questions;
	}

	public int getAnswersNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLANSWER + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getAnswers(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLANSWER + Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList answers = null;
		try {
			db = new dbpool();
			answers = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleAnswer answer = new OracleAnswer();
				answer.setId(rs.getString("id"));
				OracleQuestion question = new OracleQuestion();
				question.setId(rs.getString("question_id"));
				answer.setQuestion(question);
				answer.setBody(rs.getString("body"));
				answer.setDate(rs.getString("publish_date"));
				answer.setReuserId(rs.getString("reuser_id"));
				answer.setReuserName(rs.getString("reuser_name"));
				answer.setReuserType(rs.getString("reuser_type"));
				answers.add(answer);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return answers;
	}

	public int getQuestionEliteDirsNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLQUESTIONELITEDIR + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getQuestionEliteDirs(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLQUESTIONELITEDIR + Conditions.convertToAndCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList dirs = null;
		try {
			db = new dbpool();
			dirs = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleQuestionEliteDir dir = new OracleQuestionEliteDir();
				dir.setId(rs.getString("id"));
				dir.setName(rs.getString("name"));
				dir.setNote(rs.getString("note"));
				dir.setDate(rs.getString("date"));
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("course_id"));
				dir.setCourse(course);
				OracleQuestionEliteDir dirFather = new OracleQuestionEliteDir();
				dirFather.setId(rs.getString("dir_father"));
				dir.setDirFather(dirFather);
				dirs.add(dir);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return dirs;
	}

	public int getEliteQuestionsNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLELITEQUESTION + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getEliteQuestions(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLELITEQUESTION + Conditions.convertToAndCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList eliteQuestions = null;
		try {
			db = new dbpool();
			eliteQuestions = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleEliteQuestion eliteQuestion = new OracleEliteQuestion();
				eliteQuestion.setId(rs.getString("id"));
				eliteQuestion.setTitle(rs.getString("title"));
				eliteQuestion.setBody(rs.getString("body"));
				if (rs.getString("key") != null && rs.getString("key").length() > 0)
					eliteQuestion.setKey(rs.getString("key").split(","));
				else
					eliteQuestion.setKey(null);
				eliteQuestion.setDate(rs.getString("date"));
				eliteQuestion.setCourseId(rs.getString("course_id"));
				OracleQuestionEliteDir dir = new OracleQuestionEliteDir();
				dir.setId(rs.getString("dir_id"));
				eliteQuestion.setDir(dir);
				eliteQuestion.setSubmituserId(rs.getString("submituser_id"));
				eliteQuestion.setSubmituserName(rs.getString("submituser_name"));
				eliteQuestion.setSubmituserType(rs.getString("submituser_type"));
				eliteQuestions.add(eliteQuestion);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return eliteQuestions;
	}

	public int getEliteAnswersNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLELITEANSWER + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getEliteAnswers(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLELITEANSWER + Conditions.convertToAndCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList eliteAnswers = null;
		try {
			db = new dbpool();
			eliteAnswers = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleEliteAnswer eliteAnswer = new OracleEliteAnswer();
				eliteAnswer.setId(rs.getString("id"));
				OracleEliteQuestion eliteQuestion = new OracleEliteQuestion();
				eliteQuestion.setId(rs.getString("elitequestion_id"));
				eliteAnswer.setEliteQuestion(eliteQuestion);
				eliteAnswer.setBody(rs.getString("body"));
				eliteAnswer.setDate(rs.getString("date"));
				eliteAnswer.setReuserId(rs.getString("reuser_id"));
				eliteAnswer.setReuserName(rs.getString("reuser_name"));
				eliteAnswer.setReuserType(rs.getString("reuser_type"));
				eliteAnswers.add(eliteAnswer);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return eliteAnswers;
	}

	public List getForumLists(List searchProperties) {
		dbpool db = new dbpool();
		String sql = this.SQLFORUMlIST + Conditions.convertToCondition(searchProperties, null);
		MyResultSet rs = null;
		List fromListList = null;
		try {
			db = new dbpool();
			fromListList = new ArrayList();
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleForumList forumList = new OracleForumList();
				forumList.setId(rs.getString("id"));
				forumList.setName(rs.getString("name"));
				forumList.setContent(rs.getString("content"));
				forumList.setDate(rs.getString("create_date"));
				forumList.setCourseId(rs.getString("course_id"));
				fromListList.add(forumList);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return fromListList;
	}

	public int getForumsNum(List searchProperties) {
		dbpool db = new dbpool();
		String sql = this.SQLFORUM + Conditions.convertToCondition(searchProperties, null);
		return db.countselect(sql);
	}

	public List getForums(Page page, List searchProperties) {
		dbpool db = new dbpool();
		String sql = this.SQLFORUM + Conditions.convertToCondition(searchProperties, null);
		// System.out.println("sql=" + sql);
		MyResultSet rs = null;
		List forumList = null;
		try {
			db = new dbpool();
			forumList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleForum forum = new OracleForum();
				forum.setId(rs.getString("id"));
				forum.setTitle(rs.getString("title"));
				forum.setBody(rs.getString("body"));
				forum.setDate(rs.getString("publish_date"));
				forum.setCourseId(rs.getString("course_id"));
				forum.setRelatedID(rs.getString("related_id"));
				forum.setUserName(rs.getString("user_name"));
				forum.setSubmitUserId(rs.getString("submituser_id"));
				forumList.add(forum);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return forumList;
	}

	public InteractionTeachClass getTeachClass(String teachclassId, String type) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id,teachclass_id,title,content,type,status from interaction_teachclass_info where teachclass_id = '" + teachclassId + "' and type = '" + type + "'";
		OracleInteractionTeachClass teachClass = new OracleInteractionTeachClass();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				teachClass.setId(rs.getString("id"));
				teachClass.setContent(rs.getString("content"));
				teachClass.setStatus(rs.getString("status"));
				teachClass.setTeachclass_id(rs.getString("teachclass_id"));
				teachClass.setTitle(rs.getString("title"));
				teachClass.setType(rs.getString("type"));
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return teachClass;
	}

	public List getTeachClassList(String teachclassId, String type) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id,teachclass_id,title,content,type,status from interaction_teachclass_info where teachclass_id = '" + teachclassId + "' and type = '" + type + "'";
		ArrayList teachClassList = new ArrayList();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleInteractionTeachClass teachClass = new OracleInteractionTeachClass();
				teachClass.setId(rs.getString("id"));
				teachClass.setContent(rs.getString("content"));
				teachClass.setStatus(rs.getString("status"));
				teachClass.setTeachclass_id(rs.getString("teachclass_id"));
				teachClass.setTitle(rs.getString("title"));
				teachClass.setType(rs.getString("type"));
				teachClassList.add(teachClass);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return teachClassList;
	}

	public List getTeachClassList(List searchProperty, List orderProperty) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id,teachclass_id,title,content,type,status,to_char(publish_date,'yyyy-mm-dd hh24:Mi:ss') as publish_date from interaction_teachclass_info "
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		ArrayList teachClassList = new ArrayList();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleInteractionTeachClass teachClass = new OracleInteractionTeachClass();
				teachClass.setId(rs.getString("id"));
				teachClass.setContent(rs.getString("content"));
				teachClass.setStatus(rs.getString("status"));
				teachClass.setTeachclass_id(rs.getString("teachclass_id"));
				teachClass.setTitle(rs.getString("title"));
				teachClass.setType(rs.getString("type"));
				teachClass.setPublish_date(rs.getString("publish_date"));
				teachClassList.add(teachClass);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return teachClassList;
	}

	public int getTeachClassNum(List searchProperty, List orderProperty) {
		dbpool db = new dbpool();
		String sql = "select id,teachclass_id,title,content,type,status from interaction_teachclass_info  " + Conditions.convertToCondition(searchProperty, orderProperty);
		return db.countselect(sql);
	}

	public int getTeachClassNum(String teachclassId, String type) {
		dbpool db = new dbpool();
		String sql = "select id,teachclass_id,title,content,type,status from interaction_teachclass_info where teachclass_id = '" + teachclassId + "' and type = '" + type + "'";
		return db.countselect(sql);
	}

	public int addTeachClass(HttpServletRequest request) {
		OracleInteractionTeachClass teachClass = new OracleInteractionTeachClass();
		teachClass.setContent(request.getParameter("body"));
		// Lee start 解析内容body 获取附件链接
		String fileLink = "";
//		System.out.println("======================" + teachClass.getContent());
//		<a href="/UserFiles/File/113PDF文档_20148735310.pdf">113PDF文档_20148735310.pdf</a><a href="/UserFiles/File/112word97-2003文档_20148735321.doc">112word97-2003文档_20148735321.doc</a>
		if (teachClass.getContent().length() > 0 && teachClass.getContent().toLowerCase().indexOf("</a>") != -1) {
//			int startIdx = teachClass.getContent().toLowerCase().indexOf("href=\"");
//			int endIdx = teachClass.getContent().toLowerCase().indexOf("\">");
//			fileLink = teachClass.getContent().substring(startIdx + 6, endIdx);
			int startIdx = teachClass.getContent().toLowerCase().indexOf("<");
			int endIdx = teachClass.getContent().toLowerCase().lastIndexOf(">");
			//截取多个上传附件路径并以","分隔
			fileLink = teachClass.getContent().substring(startIdx, endIdx + 1).replaceAll("<a href=\"", "").replaceAll("\\\">(.*?)\\/U", ",/U").replaceAll("\\\">(.*?)\\</a>", "");
		}
		teachClass.setFileLink(fileLink);
		// Lee end
		if (request.getParameter("type").equals("KCZL")) {
			teachClass.setTeachclass_id(request.getParameter("courseId"));
		} else {
			teachClass.setTeachclass_id(request.getParameter("teachclass_id"));
		}
		teachClass.setTitle(request.getParameter("title"));
		teachClass.setType(request.getParameter("type"));
		return teachClass.add();
	}

	public int updateTeachClass(HttpServletRequest request) {
		OracleInteractionTeachClass teachClass = new OracleInteractionTeachClass();
		teachClass.setContent(request.getParameter("body"));
		teachClass.setTitle(request.getParameter("title"));
		teachClass.setType(request.getParameter("type"));
		teachClass.setId(request.getParameter("id"));
		teachClass.setStatus(request.getParameter("status"));
		String fileLink = "";
		if (teachClass.getContent().length() > 0 && teachClass.getContent().toLowerCase().indexOf("</a>") != -1) {
			int startIdx = teachClass.getContent().toLowerCase().indexOf("<");
			int endIdx = teachClass.getContent().toLowerCase().lastIndexOf(">");
			//截取多个上传附件路径并以","分隔
			fileLink = teachClass.getContent().substring(startIdx, endIdx + 1).replaceAll("<a href=\"", "").replaceAll("\\\">(.*?)\\/U", ",/U").replaceAll("\\\">(.*?)\\</a>", "");
	
		}
		teachClass.setFileLink(fileLink);
		return teachClass.update();
	}

	public int deleteTeachClass(String id) {
		OracleInteractionTeachClass teachClass = new OracleInteractionTeachClass();
		teachClass.setId(id);
		return teachClass.delete();
	}

	public String getTeachRule(String teachclassId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String rule = "";
		String sql = "";
		sql = "select TEACH_RULE from ENTITY_TEACH_CLASS where id ='" + teachclassId + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				rule = rs.getString("TEACH_RULE");
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return rule;
	}

	public int updateTeachRule(String teachclassId, String rule) {
		dbpool db = new dbpool();

		String sql = "";
		sql = "update ENTITY_TEACH_CLASS set teach_rule = ? where id ='" + teachclassId + "'";
		int i = db.executeUpdate(sql, rule);
		UserUpdateLog.setDebug("OracleInteractionList.updateTeachRule(String teachclassId, String rule) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int getHomeworkTimes(String teachclassId) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from interaction_homework_info where course_id ='" + teachclassId + "'";
		int i = db.countselect(sql);
		return i;
	}

	public int getForumTimes(String teachclassId) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from interaction_forum_info where course_id ='" + teachclassId + "'";
		int i = db.countselect(sql);
		return i;
	}

	public int getQuestionTimes(String teachclassId) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from interaction_question_info where course_id ='" + teachclassId + "'";
		int i = db.countselect(sql);
		return i;
	}

	public int getAnswerTimes(String teachclassId) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select a.id from interaction_answer_info a, interaction_question_info b where a.question_id = b.id and  b.course_id ='" + teachclassId + "'";
		int i = db.countselect(sql);
		return i;
	}

	public int updateQuestionType(String id, String questionType) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = " select id from interaction_question_type iqt where iqt.name='" + questionType + "'";

		rs = db.executeQuery(sql);
		String questionTypeId = "";
		try {
			if (rs.next()) {
				questionTypeId = rs.getString("id");
			} else {
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
		}
		sql = "update interaction_question_info iqi set iqi.question_type='" + questionTypeId + "' where iqi.id='" + id + "'";

		int i = db.executeUpdate(sql);
		return i;
	}

	// 考试辅导答疑问题数目20101227
	public int getExamQuestionsNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLEXAMQUESTION + Conditions.convertToCondition(searchproperty, null);
		// System.out.println(sql);
		int i = db.countselect(sql);
		return i;
	}

	public String getExamNewsCount(String exambatchId, String student_id) {
		dbpool db = new dbpool();
		String news_count = "";
		String sql = "select news_count\n" + "  from (select count(eqi.id) as news_count\n" + "          from exam_question_info eqi, exam_answer_info eai\n"
				+ "         where eai.question_id = eqi.id\n" + "           and eqi.submituser_id = '" + student_id + "'\n" + "           and eqi.exambatch_id = '" + exambatchId + "'\n"
				+ "           and eai.publish_type = 'manager'\n" + "        union\n" + "        select count(eqi.id) as news_count\n" + "          from exam_question_info eqi,\n"
				+ "               exam_answer_info   eai,\n" + "               exam_user_question euq\n" + "         where eai.question_id = eqi.id\n" + "           and eqi.exambatch_id = '"
				+ exambatchId + "'\n" + "           and eai.publish_type = 'manager'\n" + "           and euq.user_id = '" + student_id + "'\n" + "           and euq.question_id = eqi.id)";

		// System.out.println(sql);
		MyResultSet rs = null;
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				news_count = rs.getString("news_count");
			}

		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return news_count;
	}

	public int getExamQuestionsNumSelf(String exambatchId, String student_id) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select distinct eqi.id              as question_id,\n" + "                eqi.title           as question_title,\n" + "                eqi.publish_date    as publish_date,\n"
				+ "                eqi.exambatch_id    as exambatch_id,\n" + "                eqi.submituser_name as submituser_name,\n" + "                eqi.submituser_type as submituser_type,\n"
				+ "                eqi.click_num       as click_num,\n" + "                eqt.name            as quesiton_type,\n" + "                eai.publish_type    as publish_type,\n"
				+ "                euq.id              as focus_id\n" + "  from exam_question_info eqi,\n" + "       exam_question_type eqt,\n" + "       exam_answer_info   eai,\n"
				+ "       exam_user_question euq\n" + " where eqi.question_type = eqt.id\n" + "   and eqi.submituser_id = '" + student_id + "'\n" + "   and eai.question_id(+) = eqi.id\n"
				+ "   and eai.publish_type(+) = 'manager'\n" + "   and euq.user_id(+) = eqi.submituser_id\n" + "   and euq.question_id(+) = eqi.id\n" + "   and eqi.exambatch_id = '" + exambatchId
				+ "'";
		// System.out.println(sql);
		int i = db.countselect(sql);
		return i;
	}

	public int getExamQuestionsNumFocus(String exambatchId, String student_id) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select distinct eqi.id              as question_id,\n" + "                eqi.title           as question_title,\n" + "                eqi.publish_date    as publish_date,\n"
				+ "                eqi.exambatch_id    as exambatch_id,\n" + "                eqi.submituser_name as submituser_name,\n" + "                eqi.submituser_type as submituser_type,\n"
				+ "                eqi.click_num       as click_num,\n" + "                eqt.name            as quesiton_type,\n" + "                eai.publish_type    as publish_type,\n"
				+ "                euq.id              as focus_id\n" + "  from exam_question_info eqi,\n" + "       exam_question_type eqt,\n" + "       exam_answer_info   eai,\n"
				+ "       exam_user_question euq\n" + " where eqi.question_type = eqt.id\n" + "   and eqi.submituser_id <> '" + student_id + "'\n" + "   and eai.question_id(+) = eqi.id\n"
				+ "   and eai.publish_type(+) = 'manager'\n" + "   and euq.user_id = '" + student_id + "'\n" + "   and euq.question_id = eqi.id\n" + "   and eqi.exambatch_id = '" + exambatchId + "'";
		// System.out.println(sql);
		int i = db.countselect(sql);
		return i;
	}

	public int getExamQuestionsNumYes(String exambatchId, String student_id) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select distinct eqi.id as question_id,\n" + "                eqi.title as question_title,\n" + "                to_char(eqi.publish_date, 'yyyy-mm-dd hh24:mi:ss') as publish_date,\n"
				+ "                eqi.exambatch_id as exambatch_id,\n" + "                eqi.submituser_name as submituser_name,\n" + "                eqi.submituser_type as submituser_type,\n"
				+ "                eqi.click_num as click_num,\n" + "                eqt.name as question_type,\n" + "                eai.publish_type as publish_type,\n"
				+ "                euq.id as focus_id\n" + "  from exam_question_info eqi,\n" + "       exam_question_type eqt,\n" + "       exam_answer_info   eai,\n"
				+ "       exam_user_question euq\n" + " where eqi.question_type = eqt.id\n" + "   and eai.question_id = eqi.id\n" + "   and eai.publish_type = 'manager'\n"
				+ "   and euq.user_id(+) = '" + student_id + "'\n" + "   and euq.question_id (+)= eqi.id\n" + "   and eqi.exambatch_id = '" + exambatchId + "'";

		// System.out.println(sql);
		int i = db.countselect(sql);
		return i;
	}

	public int getExamQuestionsNumNo(String exambatchId, String student_id) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select distinct eqi.id as question_id,\n" + "                eqi.title as question_title,\n" + "                to_char(eqi.publish_date, 'yyyy-mm-dd hh24:mi:ss') as publish_date,\n"
				+ "                eqi.exambatch_id as exambatch_id,\n" + "                eqi.submituser_name as submituser_name,\n" + "                eqi.submituser_type as submituser_type,\n"
				+ "                eqi.submituser_id as submituser_id,\n" + "                eqi.click_num as click_num,\n" + "                eqt.name as question_type,\n"
				+ "                euq.id as focus_id\n" + "  from exam_question_info eqi,\n" + "       exam_question_type eqt,\n" + "       exam_answer_info   eai,\n"
				+ "       exam_user_question euq\n" + " where eqi.question_type = eqt.id\n" + "   and eai.question_id(+) = eqi.id\n" + "   and euq.user_id(+) = '"
				+ student_id
				+ "'\n"
				+ "   and euq.question_id(+) = eqi.id\n"
				+ "   and eqi.exambatch_id = '"
				+ exambatchId
				+ "'\n"
				+ "minus\n"
				+ "select distinct eqi.id as question_id,\n"
				+ "                eqi.title as question_title,\n"
				+ "                to_char(eqi.publish_date, 'yyyy-mm-dd hh24:mi:ss') as publish_date,\n"
				+ "                eqi.exambatch_id as exambatch_id,\n"
				+ "                eqi.submituser_name as submituser_name,\n"
				+ "                eqi.submituser_type as submituser_type,\n"
				+ "                eqi.submituser_id as submituser_id,\n"
				+ "                eqi.click_num as click_num,\n"
				+ "                eqt.name as question_type,\n"
				+ "                euq.id as focus_id\n"
				+ "  from exam_question_info eqi,\n"
				+ "       exam_question_type eqt,\n"
				+ "       exam_answer_info   eai,\n"
				+ "       exam_user_question euq\n"
				+ " where eqi.question_type = eqt.id\n"
				+ "   and eai.question_id = eqi.id\n"
				+ "   and eai.publish_type = 'manager'\n"
				+ "   and euq.user_id(+) = '" + student_id + "'\n" + "   and euq.question_id(+) = eqi.id\n" + "   and eqi.exambatch_id = '" + exambatchId + "'";

		int i = db.countselect(sql);
		return i;
	}

	public int getExamNews(String exambatchId, String student_id) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select eqi.id as question_id,\n" + "       eqi.title as question_title,\n" + "       to_char(eqi.publish_date, 'yyyy-mm-dd hh24:mi:ss') as publish_date,\n"
				+ "       eqi.exambatch_id as exambatch_id,\n" + "       eqi.submituser_name as submituser_name,\n" + "       eqi.submituser_type as submituser_type,\n"
				+ "       eqi.click_num as click_num,\n" + "       eqt.name as question_type,\n" + "       eai.publish_type as publish_type\n"
				+ "  from exam_question_info eqi, exam_answer_info eai, exam_question_type eqt\n" + " where eai.question_id = eqi.id\n" + "   and eqi.submituser_id = '"
				+ student_id
				+ "'\n"
				+ "   and eqi.exambatch_id = '"
				+ exambatchId
				+ "'\n"
				+ "   and eai.publish_type = 'manager'\n"
				+ "   and eqt.id = eqi.question_type\n"
				+ "union\n"
				+ "select eqi.id as question_id,\n"
				+ "       eqi.title as question_title,\n"
				+ "       to_char(eqi.publish_date, 'yyyy-mm-dd hh24:mi:ss') as publish_date,\n"
				+ "       eqi.exambatch_id as exambatch_id,\n"
				+ "       eqi.submituser_name as submituser_name,\n"
				+ "       eqi.submituser_type as submituser_type,\n"
				+ "       eqi.click_num as click_num,\n"
				+ "       eqt.name as question_type,\n"
				+ "       eai.publish_type as publish_type\n"
				+ "  from exam_question_info eqi,\n"
				+ "       exam_answer_info   eai,\n"
				+ "       exam_user_question euq,\n"
				+ "       exam_question_type eqt\n"
				+ " where eai.question_id = eqi.id\n"
				+ "   and eqi.exambatch_id = '"
				+ exambatchId
				+ "'\n"
				+ "   and eai.publish_type = 'manager'\n"
				+ "   and euq.user_id = '"
				+ student_id
				+ "'\n"
				+ "   and euq.question_id = eqi.id\n"
				+ "   and eqt.id = eqi.question_type";

		int i = db.countselect(sql);
		return i;
	}

	public List getExamQuestions(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLEXAMQUESTION + Conditions.convertToCondition(searchproperty, orderproperty);
		// System.out.println(sql);
		MyResultSet rs = null;
		ArrayList questions = null;
		try {
			db = new dbpool();
			questions = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleExamQuestion question = new OracleExamQuestion();
				question.setId(rs.getString("id"));
				question.setTitle(rs.getString("title"));
				question.setBody(rs.getString("body"));
				question.setPublishDate(rs.getString("publish_date"));
				question.setExambatchId(rs.getString("exambatch_id"));
				question.setSubmituserId(rs.getString("submituser_id"));
				question.setSubmituserName(rs.getString("submituser_name"));
				question.setSubmituserType(rs.getString("submituser_type"));
				question.setQuestionType(rs.getString("question_type"));
				question.setClickNum(rs.getString("click_num"));
				questions.add(question);
			}

		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return questions;
	}

	public List getExamQuestionsSelf(Page page, String exambatchId, String student_id) {
		dbpool db = new dbpool();
		String sql = "select distinct eqi.id as question_id,\n" + "                eqi.title as question_title,\n"
				+ "                to_char(eqi.publish_date, 'yyyy-mm-dd hh24:mi:ss') as publish_date,\n" + "                eqi.exambatch_id as exambatch_id,\n"
				+ "                eqi.submituser_name as submituser_name,\n" + "                eqi.submituser_type as submituser_type,\n" + "                eqi.click_num as click_num,\n"
				+ "                eqt.name as question_type,\n" + "                eai.publish_type as publish_type,\n" + "                euq.id as focus_id\n" + "  from exam_question_info eqi,\n"
				+ "       exam_question_type eqt,\n" + "       exam_answer_info   eai,\n" + "       exam_user_question euq\n" + " where eqi.question_type = eqt.id\n" + "   and eqi.submituser_id = '"
				+ student_id + "'\n" + "   and eai.question_id(+) = eqi.id\n" + "   and eai.publish_type(+) = 'manager'\n" + "   and euq.user_id(+) = eqi.submituser_id\n"
				+ "   and euq.question_id(+) = eqi.id\n" + "   and eqi.exambatch_id = '" + exambatchId + "'";

		MyResultSet rs = null;
		ArrayList questions = null;
		try {
			db = new dbpool();
			questions = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleExamQuestion question = new OracleExamQuestion();
				question.setId(rs.getString("question_id"));
				question.setTitle(rs.getString("question_title"));
				question.setPublishDate(rs.getString("publish_date"));
				question.setExambatchId(rs.getString("exambatch_id"));
				question.setSubmituserName(rs.getString("submituser_name"));
				question.setSubmituserType(rs.getString("submituser_type"));
				question.setQuestionType(rs.getString("question_type"));
				question.setClickNum(rs.getString("click_num"));
				question.setPublish_type(rs.getString("publish_type"));
				question.setFocus_id(rs.getString("focus_id"));
				questions.add(question);
			}

		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return questions;
	}

	public List getExamQuestionsFocus(Page page, String exambatchId, String student_id) {
		dbpool db = new dbpool();
		String sql = "select distinct eqi.id as question_id,\n" + "                eqi.title as question_title,\n"
				+ "                to_char(eqi.publish_date, 'yyyy-mm-dd hh24:mi:ss') as publish_date,\n" + "                eqi.exambatch_id as exambatch_id,\n"
				+ "                eqi.submituser_name as submituser_name,\n" + "                eqi.submituser_type as submituser_type,\n" + "                eqi.click_num as click_num,\n"
				+ "                eqt.name as question_type,\n" + "                eai.publish_type as publish_type,\n" + "                euq.id as focus_id\n" + "  from exam_question_info eqi,\n"
				+ "       exam_question_type eqt,\n" + "       exam_answer_info   eai,\n" + "       exam_user_question euq\n" + " where eqi.question_type = eqt.id\n" + "   and eqi.submituser_id <> '"
				+ student_id + "'\n" + "   and eai.question_id(+) = eqi.id\n" + "   and eai.publish_type(+) = 'manager'\n" + "   and euq.user_id = '" + student_id + "'\n"
				+ "   and euq.question_id = eqi.id\n" + "   and eqi.exambatch_id = '" + exambatchId + "'";

		MyResultSet rs = null;
		ArrayList questions = null;
		try {
			db = new dbpool();
			questions = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleExamQuestion question = new OracleExamQuestion();
				question.setId(rs.getString("question_id"));
				question.setTitle(rs.getString("question_title"));
				question.setPublishDate(rs.getString("publish_date"));
				question.setExambatchId(rs.getString("exambatch_id"));
				question.setSubmituserName(rs.getString("submituser_name"));
				question.setSubmituserType(rs.getString("submituser_type"));
				question.setQuestionType(rs.getString("question_type"));
				question.setClickNum(rs.getString("click_num"));
				question.setPublish_type(rs.getString("publish_type"));
				question.setFocus_id(rs.getString("focus_id"));
				questions.add(question);
			}

		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return questions;
	}

	public List getExamQuestionsYes(Page page, String exambatchId, String student_id) {
		dbpool db = new dbpool();
		String sql =

		"select distinct eqi.id as question_id,\n" + "                eqi.title as question_title,\n" + "                to_char(eqi.publish_date, 'yyyy-mm-dd hh24:mi:ss') as publish_date,\n"
				+ "                eqi.exambatch_id as exambatch_id,\n" + "                eqi.submituser_name as submituser_name,\n" + "                eqi.submituser_type as submituser_type,\n"
				+ "                eqi.click_num as click_num,\n" + "                eqt.name as question_type,\n" + "                eai.publish_type as publish_type,\n"
				+ "                euq.id as focus_id\n" + "  from exam_question_info eqi,\n" + "       exam_question_type eqt,\n" + "       exam_answer_info   eai,\n"
				+ "       exam_user_question euq\n" + " where eqi.question_type = eqt.id\n" + "   and eai.question_id = eqi.id\n" + "   and eai.publish_type = 'manager'\n"
				+ "   and euq.user_id(+) = '" + student_id + "'\n" + "   and euq.question_id (+)= eqi.id\n" + "   and eqi.exambatch_id = '" + exambatchId + "'";

		MyResultSet rs = null;
		ArrayList questions = null;
		try {
			db = new dbpool();
			questions = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleExamQuestion question = new OracleExamQuestion();
				question.setId(rs.getString("question_id"));
				question.setTitle(rs.getString("question_title"));
				question.setPublishDate(rs.getString("publish_date"));
				question.setExambatchId(rs.getString("exambatch_id"));
				question.setSubmituserName(rs.getString("submituser_name"));
				question.setSubmituserType(rs.getString("submituser_type"));
				question.setQuestionType(rs.getString("question_type"));
				question.setClickNum(rs.getString("click_num"));
				question.setPublish_type(rs.getString("publish_type"));
				question.setFocus_id(rs.getString("focus_id"));
				questions.add(question);
			}

		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return questions;
	}

	public List getExamQuestionsNo(Page page, String exambatchId, String student_id) {
		dbpool db = new dbpool();
		String sql =

		"select distinct eqi.id as question_id,\n" + "                eqi.title as question_title,\n" + "                to_char(eqi.publish_date, 'yyyy-mm-dd hh24:mi:ss') as publish_date,\n"
				+ "                eqi.exambatch_id as exambatch_id,\n" + "                eqi.submituser_name as submituser_name,\n" + "                eqi.submituser_type as submituser_type,\n"
				+ "                eqi.submituser_id as submituser_id,\n" + "                eqi.click_num as click_num,\n" + "                eqt.name as question_type,\n"
				+ "                euq.id as focus_id\n" + "  from exam_question_info eqi,\n" + "       exam_question_type eqt,\n" + "       exam_answer_info   eai,\n"
				+ "       exam_user_question euq\n" + " where eqi.question_type = eqt.id\n" + "   and eai.question_id(+) = eqi.id\n" + "   and euq.user_id(+) = '"
				+ student_id
				+ "'\n"
				+ "   and euq.question_id(+) = eqi.id\n"
				+ "   and eqi.exambatch_id = '"
				+ exambatchId
				+ "'\n"
				+ "minus\n"
				+ "select distinct eqi.id as question_id,\n"
				+ "                eqi.title as question_title,\n"
				+ "                to_char(eqi.publish_date, 'yyyy-mm-dd hh24:mi:ss') as publish_date,\n"
				+ "                eqi.exambatch_id as exambatch_id,\n"
				+ "                eqi.submituser_name as submituser_name,\n"
				+ "                eqi.submituser_type as submituser_type,\n"
				+ "                eqi.submituser_id as submituser_id,\n"
				+ "                eqi.click_num as click_num,\n"
				+ "                eqt.name as question_type,\n"
				+ "                euq.id as focus_id\n"
				+ "  from exam_question_info eqi,\n"
				+ "       exam_question_type eqt,\n"
				+ "       exam_answer_info   eai,\n"
				+ "       exam_user_question euq\n"
				+ " where eqi.question_type = eqt.id\n"
				+ "   and eai.question_id = eqi.id\n"
				+ "   and eai.publish_type = 'manager'\n"
				+ "   and euq.user_id(+) = '" + student_id + "'\n" + "   and euq.question_id(+) = eqi.id\n" + "   and eqi.exambatch_id = '" + exambatchId + "'";

		// System.out.println(sql);

		MyResultSet rs = null;
		ArrayList questions = null;
		try {
			db = new dbpool();
			questions = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleExamQuestion question = new OracleExamQuestion();
				question.setId(rs.getString("question_id"));
				question.setTitle(rs.getString("question_title"));
				question.setPublishDate(rs.getString("publish_date"));
				question.setExambatchId(rs.getString("exambatch_id"));
				question.setSubmituserId(rs.getString("submituser_id"));
				question.setSubmituserName(rs.getString("submituser_name"));
				question.setSubmituserType(rs.getString("submituser_type"));
				question.setQuestionType(rs.getString("question_type"));
				question.setClickNum(rs.getString("click_num"));
				question.setFocus_id(rs.getString("focus_id"));
				questions.add(question);
			}

		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return questions;
	}

	public List getExamNews(Page page, String exambatchId, String student_id) {
		dbpool db = new dbpool();
		String sql = "select eqi.id as question_id,\n" + "       eqi.title as question_title,\n" + "       to_char(eqi.publish_date, 'yyyy-mm-dd hh24:mi:ss') as publish_date,\n"
				+ "       eqi.exambatch_id as exambatch_id,\n" + "                eqi.submituser_id as submituser_id,\n" + "       eqi.submituser_name as submituser_name,\n"
				+ "       eqi.submituser_type as submituser_type,\n" + "       eqi.click_num as click_num,\n" + "       eqt.name as question_type,\n" + "       eai.publish_type as publish_type\n"
				+ "  from exam_question_info eqi, exam_answer_info eai, exam_question_type eqt\n" + " where eai.question_id = eqi.id\n" + "   and eqi.submituser_id = '"
				+ student_id
				+ "'\n"
				+ "   and eqi.exambatch_id = '"
				+ exambatchId
				+ "'\n"
				+ "   and eai.publish_type = 'manager'\n"
				+ "   and eqt.id = eqi.question_type\n"
				+ "union\n"
				+ "select eqi.id as question_id,\n"
				+ "       eqi.title as question_title,\n"
				+ "       to_char(eqi.publish_date, 'yyyy-mm-dd hh24:mi:ss') as publish_date,\n"
				+ "       eqi.exambatch_id as exambatch_id,\n"
				+ "                eqi.submituser_id as submituser_id,\n"
				+ "       eqi.submituser_name as submituser_name,\n"
				+ "       eqi.submituser_type as submituser_type,\n"
				+ "       eqi.click_num as click_num,\n"
				+ "       eqt.name as question_type,\n"
				+ "       eai.publish_type as publish_type\n"
				+ "  from exam_question_info eqi,\n"
				+ "       exam_answer_info   eai,\n"
				+ "       exam_user_question euq,\n"
				+ "       exam_question_type eqt\n"
				+ " where eai.question_id = eqi.id\n"
				+ "   and eqi.exambatch_id = '"
				+ exambatchId
				+ "'\n"
				+ "   and eai.publish_type = 'manager'\n"
				+ "   and euq.user_id = '"
				+ student_id
				+ "'\n"
				+ "   and euq.question_id = eqi.id\n" + "   and eqt.id = eqi.question_type";
		// System.out.println(sql);
		MyResultSet rs = null;
		ArrayList questions = null;
		try {
			db = new dbpool();
			questions = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleExamQuestion question = new OracleExamQuestion();
				question.setId(rs.getString("question_id"));
				question.setTitle(rs.getString("question_title"));
				question.setPublishDate(rs.getString("publish_date"));
				question.setExambatchId(rs.getString("exambatch_id"));
				question.setSubmituserId(rs.getString("submituser_id"));
				question.setSubmituserName(rs.getString("submituser_name"));
				question.setSubmituserType(rs.getString("submituser_type"));
				question.setQuestionType(rs.getString("question_type"));
				question.setClickNum(rs.getString("click_num"));
				question.setPublish_type(rs.getString("publish_type"));
				questions.add(question);
			}

		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return questions;
	}

	public List getExamAnswers(Page page, String question_id) {
		dbpool db = new dbpool();
		String sql = "select id,\n" + "       question_id,\n" + "       body,\n" + "       publish_date,\n" + "       publish_id,\n" + "       publish_name,\n" + "       publish_type,\n"
				+ "       is_key\n" + "  from (select id,\n" + "               question_id,\n" + "               body,\n" + "               to_char(publish_date, 'yyyy-mm-dd') as publish_date,\n"
				+ "               publish_id,\n" + "               publish_name,\n" + "               publish_type,\n" + "               is_key\n" + "          from exam_answer_info\n"
				+ "         where question_id = '" + question_id + "'\n" + "           and publish_type = 'student'\n" + "           and is_key = '0')";

		MyResultSet rs = null;
		ArrayList answers = null;
		try {
			db = new dbpool();
			answers = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleExamAnswer answer = new OracleExamAnswer();
				answer.setId(rs.getString("id"));
				OracleExamQuestion question = new OracleExamQuestion();
				question.setId(rs.getString("question_id"));
				answer.setQuestion(question);
				answer.setBody(rs.getString("body"));
				answer.setPublishDate(rs.getString("publish_date"));
				answer.setPublishId(rs.getString("publish_id"));
				answer.setPublishName(rs.getString("publish_name"));
				answer.setPublishType(rs.getString("publish_type"));
				answer.setIsKey(rs.getString("is_key"));
				answers.add(answer);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return answers;
	}
}
