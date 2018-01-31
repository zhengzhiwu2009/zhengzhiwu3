package com.whaty.platform.database.oracle.standard.test;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.test.history.OracleExamPaperHistory;
import com.whaty.platform.database.oracle.standard.test.history.OracleExperimentPaperHistory;
import com.whaty.platform.database.oracle.standard.test.history.OracleHomeworkPaperHistory;
import com.whaty.platform.database.oracle.standard.test.history.OracleTestPaperHistory;
import com.whaty.platform.database.oracle.standard.test.lore.OracleLore;
import com.whaty.platform.database.oracle.standard.test.lore.OracleLoreDir;
import com.whaty.platform.database.oracle.standard.test.onlinetest.OracleOnlineExamCourse;
import com.whaty.platform.database.oracle.standard.test.onlinetest.OracleOnlineTestBatch;
import com.whaty.platform.database.oracle.standard.test.onlinetest.OracleOnlineTestCourse;
import com.whaty.platform.database.oracle.standard.test.paper.OracleExamPaper;
import com.whaty.platform.database.oracle.standard.test.paper.OracleExperimentPaper;
import com.whaty.platform.database.oracle.standard.test.paper.OracleHomeworkPaper;
import com.whaty.platform.database.oracle.standard.test.paper.OraclePaperPolicy;
import com.whaty.platform.database.oracle.standard.test.paper.OracleTestPaper;
import com.whaty.platform.database.oracle.standard.test.question.OraclePaperQuestion;
import com.whaty.platform.database.oracle.standard.test.question.OracleStoreQuestion;
import com.whaty.platform.test.TestList;
import com.whaty.platform.test.lore.LoreDir;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;

public class OracleTestList implements TestList {
	private String SQLLORE = "select id, name, creatdate, content, loredir, createrid, active "
			+ " from (select id, name, to_char(creatdate, 'yyyy-mm-dd') as creatdate, content, loredir, createrid, active from test_lore_info)";

	private String SQLLOREDIR = "select id,name,note,fatherdir,creatdate,group_id from " + " (select id,name,note,fatherdir,to_char(creatdate,'yyyy-mm-dd') as creatdate,group_id from test_lore_dir)";

	private String SQLSTOREQUESTION = "select id,title,creatuser,creatdate,diff,keyword,questioncore,lore,"
			+ "cognizetype,purpose,referencescore,referencetime,studentnote,teachernote,type from (select id,title,creatuser,to_char(creatdate,'yyyy-mm-dd') as creatdate,diff,keyword,questioncore,lore,"
			+ "cognizetype,purpose,referencescore,referencetime,studentnote,teachernote,type,rownum as rownums from test_storequestion_info)";

	private String SQLPAPERPOLICY = "select id,title,policycore,creatuser,creatdate,status,note,type,testtime,group_id from ("
			+ " select id,title,policycore,creatuser,to_char(creatdate,'yyyy-mm-dd') as creatdate,status,note,type,testtime,group_id from test_paperpolicy_info)";

	private String SQLONLINETESTCOURSE = "select id,title,group_id,batch_id,note,start_date,end_date,isautocheck,ishiddenanswer,status,creatuser,creatdate,fk_batch_id,batch_name "
			+ " from (select oci.id as id,oci.title as title,oci.group_id as group_id,oci.batch_id as batch_id,oci.note as note,oci.start_date as start_date,"
			+ "oci.end_date as end_date,oci.isautocheck as isautocheck,oci.ishiddenanswer as ishiddenanswer,"
			+ "oci.status as status,oci.creatuser as creatuser,to_char(oci.creatdate,'yyyy-mm-dd') as creatdate,pbb.id as fk_batch_id,pbb.name as batch_name "
			+ " from onlinetest_course_info oci,pe_bzz_batch pbb where oci.fk_batch_id=pbb.id(+))";
	
	// Lee 课后测验列表SQL
	//查询优化，采用分组 zgl
	private String SQLONLINETESTCOURSELEE = " SELECT  FK_SSO_USER_ID,EXAMTIMES_ALLOW, PASSROLE, EXAM_TIMES, ISPASS, ID, TITLE, GROUP_ID, BATCH_ID, NOTE, START_DATE, END_DATE, ISAUTOCHECK, ISHIDDENANSWER, STATUS, CREATUSER, CREATDATE, FK_BATCH_ID, BATCH_NAME "
			+ " FROM (SELECT F.FK_SSO_USER_ID, C.EXAMTIMES_ALLOW, C.PASSROLE, E.EXAM_TIMES, E.ISPASS, OCI.ID AS ID, OCI.TITLE AS TITLE, OCI.GROUP_ID AS GROUP_ID, OCI.BATCH_ID AS BATCH_ID, OCI.NOTE AS NOTE, OCI.START_DATE AS START_DATE, OCI.END_DATE AS END_DATE, OCI.ISAUTOCHECK AS ISAUTOCHECK, OCI.ISHIDDENANSWER AS ISHIDDENANSWER, OCI.STATUS AS STATUS, OCI.CREATUSER AS CREATUSER, TO_CHAR(OCI.CREATDATE, 'yyyy-mm-dd') AS CREATDATE, PBB.ID AS FK_BATCH_ID, PBB.NAME AS BATCH_NAME "
			+ " FROM ONLINETEST_COURSE_INFO OCI, PE_BZZ_BATCH PBB, PE_BZZ_TCH_COURSE C, PR_BZZ_TCH_OPENCOURSE D, PR_BZZ_TCH_STU_ELECTIVE E, PE_BZZ_STUDENT F WHERE OCI.FK_BATCH_ID = PBB.ID(+) AND OCI.GROUP_ID = C.ID AND C.ID = D.FK_COURSE_ID AND D.ID = E.FK_TCH_OPENCOURSE_ID AND E.FK_STU_ID = F.ID " +
					" GROUP BY F.FK_SSO_USER_ID, C.EXAMTIMES_ALLOW, C.PASSROLE,E.EXAM_TIMES, E.ISPASS, OCI.ID,OCI.TITLE,OCI.GROUP_ID,OCI.BATCH_ID, OCI.NOTE," +
					"OCI.START_DATE, OCI.END_DATE,OCI.ISAUTOCHECK, OCI.ISHIDDENANSWER, OCI.STATUS, OCI.CREATUSER,OCI.CREATDATE, PBB.ID,PBB.NAME) ";

	private String SQLONLINETESTCOURSELEE2 = " SELECT DISTINCT FK_SSO_USER_ID,EXAMTIMES_ALLOW, PASSROLE, EXAM_TIMES, ISPASS, ID, TITLE, GROUP_ID, BATCH_ID, NOTE, START_DATE, END_DATE, ISAUTOCHECK, ISHIDDENANSWER, STATUS, CREATUSER, CREATDATE, FK_BATCH_ID, BATCH_NAME "
			+ " FROM (SELECT DISTINCT F.FK_SSO_USER_ID, C.EXAMTIMES_ALLOW, C.PASSROLE, E.EXAM_TIMES, E.ISPASS, OCI.ID AS ID, OCI.TITLE AS TITLE, OCI.GROUP_ID AS GROUP_ID, OCI.BATCH_ID AS BATCH_ID, OCI.NOTE AS NOTE, OCI.START_DATE AS START_DATE, OCI.END_DATE AS END_DATE, OCI.ISAUTOCHECK AS ISAUTOCHECK, OCI.ISHIDDENANSWER AS ISHIDDENANSWER, OCI.STATUS AS STATUS, OCI.CREATUSER AS CREATUSER, TO_CHAR(OCI.CREATDATE, 'yyyy-mm-dd') AS CREATDATE, PBB.ID AS FK_BATCH_ID, PBB.NAME AS BATCH_NAME "
			+ " FROM ONLINETEST_COURSE_INFO OCI, PE_BZZ_BATCH PBB, PE_BZZ_TCH_COURSE C, PR_BZZ_TCH_OPENCOURSE D WHERE OCI.FK_BATCH_ID = PBB.ID(+) AND OCI.GROUP_ID = C.ID AND C.ID = D.FK_COURSE_ID) ";

	private String SQLTESTPAPER = "select id,title,creatuser,creatdate,status,note,time,type,group_id from " + "(select id,title,creatuser,to_char(creatdate,'yyyy-mm-dd') as creatdate,status,note,"
			+ "time,type,group_id from test_testpaper_info)";

	private String SQLTESTPAPERHISTORY = "select id,user_id,testpaper_id,test_date,test_result,site_id,score,ischeck from (select a.id, a.user_id,a.score,testpaper_id, to_char(test_date,'yyyy-mm-dd hh24:mi:ss') as test_date,test_result,b.group_id,c.fk_enterprise_id as site_id,a.ischeck from test_testpaper_history a,test_testpaper_info b,pe_bzz_student c where a.testpaper_id=b.id and a.user_id=('('||c.id||')'||c.true_name) ";

	private String SQLTESTPAPERHISTORY2 = "select id,user_id,testpaper_id,test_date,test_result,score,ischeck from (select a.id, a.user_id,a.score,testpaper_id, to_char(test_date,'yyyy-mm-dd hh24:mi:ss') as test_date,test_result,b.group_id,c.fk_enterprise_id as site_id,a.ischeck from test_testpaper_history a,test_testpaper_info b,pe_bzz_student c where a.testpaper_id=b.id and a.user_id = ('('||c.id||')'||c.true_name) ";

	private String SQLHOMEWORKPAPER = "select id,title,creatuser,creatdate,status,note,comments,startdate,enddate,type,group_id,batch_id,batch_name from "
			+ "(select thi.id as id,thi.title as title,thi.creatuser as creatuser,to_char(thi.creatdate,'yyyy-mm-dd') as creatdate,thi.status as status,"
			+ "thi.note as note,thi.comments as comments,thi.startdate as startdate,thi.enddate as enddate,thi.type as type,thi.group_id as group_id,"
			+ "to_date(thi.startdate,'yyyy-mm-dd hh24:mi:ss') as startdate1,to_date(thi.enddate,'yyyy-mm-dd hh24:mi:ss') as enddate1,pbb.id as batch_id,pbb.name as batch_name "
			+ " from test_homeworkpaper_info thi,pe_bzz_batch pbb where thi.batch_id=pbb.id(+))";

	// private String SQLHOMEWORKPAPERHISTORY = "select
	// id,user_id,testpaper_id,test_date,test_result,site_id,ischeck from
	// (select a.id, a.user_id,testpaper_id, to_char(test_date,'yyyy-mm-dd') as
	// test_date,test_result,b.group_id, c.site_id,a.ischeck from
	// test_homeworkpaper_history a,test_homeworkpaper_info b,
	// entity_student_info c where a.testpaper_id=b.id and
	// a.user_id=('('||c.id||')'||c.name) order by a.ischeck desc,test_date
	// desc) ";
	// bjsy2
	private String SQLHOMEWORKPAPERHISTORY = "select id,user_id,testpaper_id,test_date,test_result,site_id,ischeck from (select a.id, a.user_id,testpaper_id, to_char(test_date,'yyyy-mm-dd') as test_date,test_result,b.group_id, c.fk_enterprise_id as site_id,a.ischeck from test_homeworkpaper_history a,test_homeworkpaper_info b, pe_bzz_student c where a.testpaper_id=b.id and a.user_id=('('||c.id||')'||c.true_name) order by a.ischeck desc,test_date desc) ";

	private String SQLHOMEWORKPAPERHISTORYNEW = "select id,user_id,testpaper_id,test_date,site_id,ischeck from (select a.id, a.user_id,testpaper_id, to_char(test_date,'yyyy-mm-dd') as test_date,b.group_id, c.fk_enterprise_id as site_id,a.ischeck from test_homeworkpaper_history a,test_homeworkpaper_info b, pe_bzz_student c where a.testpaper_id=b.id and a.user_id=('('||c.id||')'||c.true_name) order by a.ischeck desc,test_date desc) ";

	// private String SQLHOMEWORKPAPERHISTORY2 = "select
	// id,user_id,testpaper_id,test_date,test_result,ischeck from (select a.id,
	// a.user_id,testpaper_id, to_char(test_date,'yyyy-mm-dd') as
	// test_date,test_result,b.group_id,a.ischeck,c.site_id from
	// test_homeworkpaper_history a,test_homeworkpaper_info b,
	// entity_student_info c where a.testpaper_id=b.id and a.user_id
	// =('('||c.id||')'||c.name) order by test_date desc) ";
	// private String SQLHOMEWORKPAPERHISTORY2 = "select
	// id,user_id,testpaper_id,test_date,test_result,ischeck from (select a.id,
	// a.user_id,testpaper_id, to_char(test_date,'yyyy-mm-dd') as
	// test_date,test_result,b.group_id,a.ischeck,c.site_id from
	// test_homeworkpaper_history a,test_homeworkpaper_info b,
	// entity_student_info c where a.testpaper_id=b.id and a.user_id
	// =('('||c.id||')'||c.name) order by a.ischeck desc,test_date desc) ";

	// hnsd
	private String SQLHOMEWORKPAPERHISTORY2 = "select id,user_id,testpaper_id,test_date,test_result,ischeck from (select a.id, a.user_id,testpaper_id, to_char(test_date,'yyyy-mm-dd') as test_date,test_result,b.group_id,a.ischeck,c.fk_enterprise_id as site_id from test_homeworkpaper_history a,test_homeworkpaper_info b, pe_bzz_student c where a.testpaper_id=b.id and a.user_id =('('||c.id||')'||c.true_name)  order by a.ischeck desc,test_date desc) ";

	private String SQLHOMEWORKPAPERHISTORY2NEW = "select id,user_id,testpaper_id,test_date,ischeck from (select a.id, a.user_id,testpaper_id, to_char(test_date,'yyyy-mm-dd') as test_date,b.group_id,a.ischeck,c.fk_enterprise_id as site_id from test_homeworkpaper_history a,test_homeworkpaper_info b, pe_bzz_student c where a.testpaper_id=b.id and a.user_id =('('||c.id||')'||c.true_name)  order by a.ischeck desc,test_date desc) ";

	private String SQLONLINEEXAMCOURSE = "select id,title,group_id,batch_id,note,start_date,end_date,isautocheck,ishiddenanswer,status,creatuser,creatdate"
			+ " from (select id,title,group_id,batch_id,note,start_date,end_date,isautocheck,ishiddenanswer,status,creatuser,to_char(creatdate,'yyyy-mm-dd') as creatdate"
			+ " from onlineexam_course_info)";

	private String SQLEXAMPAPER = "select id,title,creatuser,creatdate,status,note,time,type,group_id from " + "(select id,title,creatuser,to_char(creatdate,'yyyy-mm-dd') as creatdate,status,note,"
			+ "time,type,group_id from test_exampaper_info)";

	private String SQLEXAMPAPERHISTORY = "select id,user_id,testpaper_id,test_date,test_result,site_id from (select a.id, a.user_id,testpaper_id, to_char(test_date,'yyyy-mm-dd') as test_date,test_result,b.group_id,c.fk_enterprise_id as site_id from test_exampaper_history a,test_exampaper_info b,pe_bzz_student c where a.testpaper_id=b.id and a.user_id=('('||c.id||')'||c.true_name) order by test_date desc) ";

	private String SQLEXAMPAPERHISTORY2 = "select id,user_id,testpaper_id,test_date,test_result from (select a.id, a.user_id,testpaper_id, to_char(test_date,'yyyy-mm-dd') as test_date,test_result,b.group_id,c.fk_enterprise_id as site_id from test_exampaper_history a,test_exampaper_info b,pe_bzz_student c where a.testpaper_id=b.id and a.user_id = ('('||c.id||')'||c.true_name)  order by test_date desc) ";

	private String SQLEXPERIMENTPAPER = "select id,title,creatuser,creatdate,status,note,comments,startdate,enddate,type,group_id from (select id,title,creatuser,to_char(creatdate,'yyyy-mm-dd') as creatdate,status,note,comments,startdate,enddate,type,group_id,to_date(startdate,'yyyy-mm-dd hh24:mi:ss') as startdate1,to_date(enddate,'yyyy-mm-dd hh24:mi:ss') as enddate1"
			+ " from test_experimentpaper_info)";

	private String SQLEXPERIMENTPAPERHISTORY = "select id,user_id,testpaper_id,test_date,test_result,site_id,ischeck from (select a.id, a.user_id,testpaper_id, to_char(test_date,'yyyy-mm-dd') as test_date,test_result,b.group_id, c.fk_enterprise_id as site_id,a.ischeck from test_experimentpaper_history a,test_experimentpaper_info b, pe_bzz_student c where a.testpaper_id=b.id and a.user_id=('('||c.id||')'||c.true_name) order by test_date desc) ";

	private String SQLEXPERIMENTPAPERHISTORY2 = "select id,user_id,testpaper_id,test_date,test_result,ischeck from (select a.id, a.user_id,testpaper_id, to_char(test_date,'yyyy-mm-dd') as test_date,test_result,b.group_id,a.ischeck,c.fk_enterprise_id as site_id from test_experimentpaper_history a,test_experimentpaper_info b, pe_bzz_student c where a.testpaper_id=b.id and a.user_id =('('||c.id||')'||c.true_name)  order by test_date desc) ";

	public int getLoresNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLLORE + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getLores(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLLORE + Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList lores = null;
		try {
			db = new dbpool();
			lores = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleLore lore = new OracleLore();
				lore.setId(rs.getString("id"));
				lore.setName(rs.getString("name"));
				lore.setCreatDate(rs.getString("creatdate"));
				lore.setContent(rs.getString("content"));
				lore.setLoreDir(rs.getString("loredir"));
				lore.setCreaterId(rs.getString("createrid"));
				lore.setActive(rs.getString("active").equals("1") ? true : false);

				lores.add(lore);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return lores;
	}

	public int getLoreDirsNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLLOREDIR + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getLoreDirs(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLLOREDIR + Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList loreDirs = null;
		try {
			db = new dbpool();
			loreDirs = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleLoreDir loreDir = new OracleLoreDir();
				loreDir.setId(rs.getString("id"));
				loreDir.setName(rs.getString("name"));
				loreDir.setNote(rs.getString("note"));
				loreDir.setFatherDir(rs.getString("fatherdir"));
				loreDir.setCreatDate(rs.getString("creatdate"));
				loreDir.setGroupId(rs.getString("group_id"));
				loreDirs.add(loreDir);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return loreDirs;
	}

	public int getStoreQuestionsNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = SQLSTOREQUESTION + Conditions.convertToCondition(searchproperty, null);

		return db.countselect(sql);
	}

	/**
	 * by wuhao 获取当前目录下题库总数
	 * 
	 * 
	 */
	public int getStoreQuestionsNum(String loreDir) {
		dbpool db = new dbpool();

		String sql = " select tsi.id from test_storequestion_info tsi ,test_lore_info tli " + "		where tsi.lore=tli.id and tli.loredir in(select  tld.id from test_lore_dir tld where  tld.id='"
				+ loreDir + "' or tld.fatherdir='" + loreDir + "' )";

		return db.countselect(sql);
	}

	public List getStoreQuestions(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLSTOREQUESTION + Conditions.convertToCondition(searchproperty, orderproperty);

		MyResultSet rs = null;
		ArrayList storeQuestions = null;
		try {
			db = new dbpool();
			storeQuestions = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			// System.out.println(sql);
			while (rs != null && rs.next()) {
				OracleStoreQuestion storeQuestion = new OracleStoreQuestion();
				storeQuestion.setId(rs.getString("id"));
				storeQuestion.setCreatUser(rs.getString("creatuser"));
				storeQuestion.setCreatDate(rs.getString("creatdate"));
				storeQuestion.setDiff(rs.getString("diff"));
				storeQuestion.setQuestionCore(rs.getString("questioncore"));
				storeQuestion.setTitle(rs.getString("title"));
				storeQuestion.setKeyWord(rs.getString("keyword"));
				storeQuestion.setLore(rs.getString("lore"));
				storeQuestion.setCognizeType(rs.getString("cognizetype"));
				storeQuestion.setPurpose(rs.getString("purpose"));
				storeQuestion.setReferenceScore(rs.getString("referencescore"));
				storeQuestion.setReferenceTime(rs.getString("referencetime"));
				storeQuestion.setStudentNote(rs.getString("studentnote"));
				storeQuestion.setTeacherNote(rs.getString("teachernote"));
				storeQuestion.setType(rs.getString("type"));

				storeQuestions.add(storeQuestion);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return storeQuestions;
	}

	public List getTitleInfo(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = "select id,\n" + "       title,\n" + "       creatuser,\n" + "       creatdate,\n" + "       diff,\n" + "       keyword,\n" + "       questioncore,\n" + "       lore,\n"
				+ "       cognizetype,\n" + "       purpose,\n" + "       referencescore,\n" + "       referencetime,\n" + "       studentnote,\n" + "       teachernote,\n" + "       type,\n"
				+ "       nvl(num,0) as num,\n "+"remark\n" + "  from (select id,\n" + "               title,\n" + "               creatuser,\n" + "               to_char(creatdate, 'yyyy-mm-dd') as creatdate,\n"
				+ "               diff,\n" + "               keyword,\n" + "               questioncore,\n" + "               lore,\n" + "               cognizetype,\n" + "               purpose,\n"
				+ "               referencescore,\n" + "               referencetime,\n" + "               studentnote,\n" + "               teachernote,\n" + "               type,\n"
				+ "               tpi.num,\n" + "               rownum as rownums\n" + "   ,remark       from test_storequestion_info tsi\n" + "          left outer join (select tpi.titilelibrary_id,\n"
				+ "                                 count(tpi.titilelibrary_id) as num\n" + "                       from test_paperquestion_info tpi\n"
				+ "                           group by tpi.titilelibrary_id) tpi\n" + "          on tsi.id=tpi.titilelibrary_id   )";
		sql = sql + Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList storeQuestions = null;
		try {
			db = new dbpool();
			storeQuestions = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			// System.out.println(sql);
			while (rs != null && rs.next()) {
				OracleStoreQuestion storeQuestion = new OracleStoreQuestion();
				storeQuestion.setId(rs.getString("id"));
				storeQuestion.setCreatUser(rs.getString("creatuser"));
				storeQuestion.setCreatDate(rs.getString("creatdate"));
				storeQuestion.setDiff(rs.getString("diff"));
				storeQuestion.setQuestionCore(rs.getString("questioncore"));
				storeQuestion.setTitle(rs.getString("title"));
				storeQuestion.setKeyWord(rs.getString("keyword"));
				storeQuestion.setLore(rs.getString("lore"));
				storeQuestion.setCognizeType(rs.getString("cognizetype"));
				storeQuestion.setPurpose(rs.getString("purpose"));
				storeQuestion.setReferenceScore(rs.getString("referencescore"));
				storeQuestion.setReferenceTime(rs.getString("referencetime"));
				storeQuestion.setStudentNote(rs.getString("studentnote"));
				storeQuestion.setTeacherNote(rs.getString("teachernote"));
				storeQuestion.setType(rs.getString("type"));
				storeQuestion.setNum(rs.getString("num"));
				storeQuestion.setRemark(rs.getString("remark"));
				storeQuestions.add(storeQuestion);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return storeQuestions;
	}

	/**
	 * by wuhao
	 * 
	 */
	public List getTitleInfo(Page page, List searchproperty, List orderproperty, String loreDirId) {
		dbpool db = new dbpool();
		String sql = " select id, title,lorename,creatdate,purpose,type,cognizetype, referencescore,referencetime,questioncore,keyword, lore,studentnote,teachernote,rownums,creatuser ,diff,nvl(num,0) as num ,remark from  (select tsi.id as id,tsi.title as title ,tli.name as lorename, "
				+ " to_char(tsi.creatdate, 'yyyy-mm-dd') as creatdate,tsi.purpose,tsi.type,tsi.cognizetype,tsi.keyword,tsi.referencescore,tsi.referencetime,tsi.questioncore, tsi.lore,tsi.studentnote,tsi.teachernote,"
				+ "    rownum as rownums,tsi.creatuser,tsi.diff ,tsi.remark"
				+ "   from  test_storequestion_info tsi ,test_lore_info tli where tsi.lore=tli.id and tli.loredir  in (select id from test_lore_dir tld where tld.id='"
				+ loreDirId
				+ "' or tld.fatherdir='"
				+ loreDirId
				+ "') )a "
				+ "  left outer join(select tpi.titilelibrary_id,     count(tpi.titilelibrary_id) as num"
				+ "   from test_paperquestion_info tpi         group by tpi.titilelibrary_id) tpi on a.id=tpi.titilelibrary_id  ";

		sql = sql + Conditions.convertToCondition(searchproperty, orderproperty);

		MyResultSet rs = null;
		ArrayList storeQuestions = null;
		try {
			db = new dbpool();
			storeQuestions = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			// System.out.println(sql);
			while (rs != null && rs.next()) {
				OracleStoreQuestion storeQuestion = new OracleStoreQuestion();
				storeQuestion.setId(rs.getString("id"));
				storeQuestion.setCreatUser(rs.getString("lorename"));
				storeQuestion.setCreatDate(rs.getString("creatdate"));
				storeQuestion.setDiff(rs.getString("diff"));
				storeQuestion.setQuestionCore(rs.getString("questioncore"));
				storeQuestion.setTitle(rs.getString("title"));
				storeQuestion.setKeyWord(rs.getString("keyword"));
				storeQuestion.setLore(rs.getString("lore"));
				storeQuestion.setCognizeType(rs.getString("cognizetype"));
				storeQuestion.setPurpose(rs.getString("purpose"));
				storeQuestion.setReferenceScore(rs.getString("referencescore"));
				storeQuestion.setReferenceTime(rs.getString("referencetime"));
				storeQuestion.setStudentNote(rs.getString("studentnote"));
				storeQuestion.setTeacherNote(rs.getString("teachernote"));
				storeQuestion.setType(rs.getString("type"));
				storeQuestion.setNum(rs.getString("num"));
				storeQuestion.setRemark(rs.getString("remark"));
				storeQuestions.add(storeQuestion);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return storeQuestions;
	}

	public int getStoreQuestions1Num(List searchproperty, String courseId) {
		dbpool db = new dbpool();
		String sql = SQLSTOREQUESTION;
		if (courseId != null)
			sql += " where lore in (select a.id from test_lore_info a,test_lore_dir b where a.loredir=b.id and b.group_id='" + courseId + "')" + Conditions.convertToAndCondition(searchproperty, null);
		else
			sql += Conditions.convertToCondition(searchproperty, null);
		// System.out.println("sql==================>"+sql);
		return db.countselect(sql);
	}

	public List getQuestionsByPaperPolicy(String score, String courseId, Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		List searchproperty1 = searchproperty.subList(0, searchproperty.size() - 1);
		List searchproperty2 = searchproperty.subList(searchproperty.size() - 1, searchproperty.size());
		String sql = "select id,title,creatuser,creatdate,diff,keyword,questioncore,lore,"
				+ "cognizetype,purpose,referencescore,referencetime,studentnote,teachernote,type,remark "
				+ " ,(select count(titilelibrary_id) as num\n"
				+ "  from test_paperquestion_info\n"
				+ " where titilelibrary_id = tsi.id) as num"
				+ " from (select id,title,creatuser,creatdate,diff,keyword,questioncore,lore,"
				+ "cognizetype,purpose,referencescore,referencetime,studentnote,teachernote,type,remark ,rownum as rownums from (select id,title,creatuser,to_char(creatdate,'yyyy-mm-dd') as creatdate,diff,keyword,questioncore,lore,"
				+ "cognizetype,purpose,referencescore,referencetime,studentnote,teachernote,type,remark from test_storequestion_info) ";
		if (courseId != null && !courseId.equals("") && !courseId.equals("null"))
			sql += " where lore in (select a.id from test_lore_info a,test_lore_dir b where a.loredir=b.id and b.group_id='" + courseId + "')"
					+ Conditions.convertToAndCondition(searchproperty1, null);
		else
			sql += Conditions.convertToCondition(searchproperty1, null);
		sql += ") tsi" + Conditions.convertToCondition(searchproperty2, orderproperty);
		MyResultSet rs = null;
		ArrayList paperQuestions = null;

		// System.out.println("sql=======================>"+sql);
		try {
			db = new dbpool();
			paperQuestions = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			// System.out.println("sql:"+sql);
			while (rs != null && rs.next()) {
				OraclePaperQuestion paperQuestion = new OraclePaperQuestion();
				paperQuestion.setId(rs.getString("id"));
				paperQuestion.setCreatUser(rs.getString("creatuser"));
				paperQuestion.setCreatDate(rs.getString("creatdate"));
				paperQuestion.setDiff(rs.getString("diff"));
				paperQuestion.setQuestionCore(rs.getString("questioncore"));
				paperQuestion.setTitle(rs.getString("title"));
				if (!score.equals("-1"))
					paperQuestion.setScore(score);
				else
					paperQuestion.setScore(rs.getString("referencescore"));
				paperQuestion.setLore(rs.getString("lore"));
				paperQuestion.setCognizeType(rs.getString("cognizetype"));
				paperQuestion.setPurpose(rs.getString("purpose"));
				paperQuestion.setReferenceScore(rs.getString("referencescore"));
				paperQuestion.setReferenceTime(rs.getString("referencetime"));
				paperQuestion.setStudentNote(rs.getString("studentnote"));
				paperQuestion.setTeacherNote(rs.getString("teachernote"));
				paperQuestion.setType(rs.getString("type"));
				paperQuestion.setRemark(rs.getString("remark"));
				paperQuestion.setNum(rs.getString("num"));
				paperQuestions.add(paperQuestion);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return paperQuestions;
	}

	public List getPaperPolicys(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLPAPERPOLICY + Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList paperPolicys = null;
		try {
			db = new dbpool();
			paperPolicys = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OraclePaperPolicy paperPolicy = new OraclePaperPolicy();
				paperPolicy.setId(rs.getString("id"));
				paperPolicy.setTitle(rs.getString("title"));
				paperPolicy.setPolicyCore(rs.getString("policycore"));
				paperPolicy.setCreatDate(rs.getString("creatdate"));
				paperPolicy.setCreatUser(rs.getString("creatuser"));
				paperPolicy.setStatus(rs.getString("status"));
				paperPolicy.setNote(rs.getString("note"));
				paperPolicy.setType(rs.getString("type"));
				paperPolicy.setTestTime(rs.getString("testtime"));
				paperPolicy.setGroupId(rs.getString("group_id"));
				paperPolicys.add(paperPolicy);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return paperPolicys;
	}

	public int getPaperPolicysNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = SQLPAPERPOLICY + Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public String getRegNoByUserId(String userId) {
		dbpool db = new dbpool();
		String regNo = "";
		String sql = "select reg_no from pe_bzz_student where id = '" + userId + "'";
		MyResultSet rs = null;
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				regNo = rs.getString("reg_no");
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return regNo;
	}

	public LoreDir getLoreDirIdByGroupId(String groupId) {
		dbpool db = new dbpool();
		String sql = "select id,name,note,fatherdir,to_char(creatdate,'yyyy-mm-dd hh24:mi:ss') as creatdate," + "group_id from test_lore_dir where fatherdir='0' and group_id='" + groupId + "'";
		MyResultSet rs = null;
		OracleLoreDir loreDir = new OracleLoreDir();
		try {
			rs = db.executeQuery(sql);

			if (rs != null && rs.next()) {
				loreDir.setId(rs.getString("id"));
				loreDir.setName(rs.getString("name"));
				loreDir.setNote(rs.getString("note"));
				loreDir.setFatherDir(rs.getString("fatherdir"));
				loreDir.setCreatDate(rs.getString("creatdate"));
				loreDir.setGroupId(rs.getString("group_id"));
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return loreDir;
	}

	public int getTestPapersNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = SQLTESTPAPER + Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public List getTestPapers(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLTESTPAPER + Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList testPapers = null;
		try {
			db = new dbpool();
			testPapers = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleTestPaper testPaper = new OracleTestPaper();
				testPaper.setId(rs.getString("id"));
				testPaper.setTitle(rs.getString("title"));
				testPaper.setCreatUser(rs.getString("creatuser"));
				testPaper.setCreatDate(rs.getString("creatdate"));
				testPaper.setStatus(rs.getString("status"));
				testPaper.setNote(rs.getString("note"));
				testPaper.setTime(rs.getString("time"));
				testPaper.setType(rs.getString("type"));
				testPaper.setGroupId(rs.getString("group_id"));

				testPapers.add(testPaper);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return testPapers;
	}

	public List getTestPaperHistorys(Page page, List searchproperty, List orderproperty, String type) {
		dbpool db = new dbpool();
		String sql = "";
		if (type.equals("teacher"))
			sql = SQLTESTPAPERHISTORY2 + Conditions.convertToAndCondition(searchproperty, null) + " )order by test_date desc ";
		else
			sql = SQLTESTPAPERHISTORY + Conditions.convertToAndCondition(searchproperty, null) + " )order by test_date desc ";
		MyResultSet rs = null;
		ArrayList list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleTestPaperHistory history = new OracleTestPaperHistory();
				history.setId(rs.getString("id"));
				history.setUserId(rs.getString("user_id"));
				history.setTestDate(rs.getString("test_date"));
				history.setTestPaperId(rs.getString("testpaper_id"));
				history.setTestResult(rs.getString("test_result"));
				history.setScore(rs.getString("score"));
				history.setStatus(rs.getString("ischeck"));
				list.add(history);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public List getTestPaperHistorys(Page page, List searchproperty, List orderproperty, String type, String regNo) {
		dbpool db = new dbpool();
		String sql = "";
		if (regNo != null && !regNo.equals("") && !regNo.equals("null"))
			sql = "select id,user_id,testpaper_id,test_date,test_result,site_id,ischeck,mark_date from "
					+ "(select a.id, a.user_id,testpaper_id, to_char(test_date,'yyyy-mm-dd hh24:mi:ss') as test_date,to_char(mark_date,'yyyy-mm-dd hh24:mi:ss') as mark_date,"
					+ "test_result,b.group_id,c.fk_enterprise_id as site_id,a.ischeck from test_testpaper_history a,test_testpaper_info b,"
					+ "pe_bzz_student c where a.testpaper_id=b.id and c.reg_no like '%" + regNo + "%' and a.user_id=('('||c.id||')'||c.true_name) "
					+ Conditions.convertToAndCondition(searchproperty, orderproperty) + ")" + Conditions.convertToCondition(null, orderproperty);
		else
			sql = "select id,user_id,testpaper_id,test_date,test_result,site_id,ischeck,mark_date from "
					+ "(select a.id, a.user_id,testpaper_id, to_char(test_date,'yyyy-mm-dd hh24:mi:ss') as test_date,to_char(mark_date,'yyyy-mm-dd hh24:mi:ss') as mark_date,"
					+ "test_result,b.group_id,c.fk_enterprise_id as site_id,a.ischeck from test_testpaper_history a,test_testpaper_info b,"
					+ "pe_bzz_student c where a.testpaper_id=b.id and a.user_id=('('||c.id||')'||c.true_name) " + Conditions.convertToAndCondition(searchproperty, orderproperty) + ")"
					+ Conditions.convertToCondition(null, orderproperty);

		MyResultSet rs = null;
		ArrayList list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleTestPaperHistory history = new OracleTestPaperHistory();
				history.setId(rs.getString("id"));
				history.setUserId(rs.getString("user_id"));
				history.setTestDate(rs.getString("test_date"));
				history.setTestPaperId(rs.getString("testpaper_id"));
				history.setTestResult(rs.getString("test_result"));
				history.setStatus(rs.getString("ischeck"));
				history.setMark_date(rs.getString("mark_date"));
				list.add(history);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getTestPaperHistorysNum(List searchproperty, String type) {
		dbpool db = new dbpool();
		String sql = "";
		if (type.equals("teacher"))
			sql = SQLTESTPAPERHISTORY2 + Conditions.convertToAndCondition(searchproperty, null) + " )order by test_date desc ";
		else
			sql = SQLTESTPAPERHISTORY + Conditions.convertToAndCondition(searchproperty, null) + " )order by test_date desc ";
		return db.countselect(sql);
	}

	public int getTestPaperHistorysNum(List searchproperty, String type, String regNo) {
		dbpool db = new dbpool();
		String sql = "";
		if (regNo != null && !regNo.equals("") && !regNo.equals("null"))
			sql = "select id,user_id,testpaper_id,test_date,test_result,site_id from " + "(select a.id, a.user_id,testpaper_id, to_char(test_date,'yyyy-mm-dd') as test_date,"
					+ "test_result,b.group_id,c.fk_enterprise_id as site_id from test_testpaper_history a,test_testpaper_info b," + "pe_bzz_student c where a.testpaper_id=b.id and c.reg_no like '%"
					+ regNo + "%' and a.user_id=('('||c.id||')'||c.true_name) " + Conditions.convertToAndCondition(searchproperty, null) + ")";
		else
			sql = "select id,user_id,testpaper_id,test_date,test_result,site_id from " + "(select a.id, a.user_id,testpaper_id, to_char(test_date,'yyyy-mm-dd') as test_date,"
					+ "test_result,b.group_id,c.fk_enterprise_id as site_id from test_testpaper_history a,test_testpaper_info b,"
					+ "pe_bzz_student c where a.testpaper_id=b.id and a.user_id=('('||c.id||')'||c.true_name) " + Conditions.convertToAndCondition(searchproperty, null) + ")";
		return db.countselect(sql);
	}

	public List getHomeworkPapers(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLHOMEWORKPAPER + Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList homeworkPapers = null;
		try {
			db = new dbpool();
			homeworkPapers = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleHomeworkPaper homeworkPaper = new OracleHomeworkPaper();
				homeworkPaper.setId(rs.getString("id"));
				homeworkPaper.setNote(rs.getString("note"));
				homeworkPaper.setCreatDate(rs.getString("creatdate"));
				homeworkPaper.setCreatUser(rs.getString("creatuser"));
				homeworkPaper.setStatus(rs.getString("status"));
				homeworkPaper.setTitle(rs.getString("title"));
				homeworkPaper.setComments(rs.getString("comments"));
				homeworkPaper.setEndDate(rs.getString("enddate"));
				homeworkPaper.setStartDate(rs.getString("startdate"));
				homeworkPaper.setType(rs.getString("type"));
				homeworkPaper.setGroupId(rs.getString("group_id"));
				homeworkPaper.setBatch_id(rs.getString("batch_id"));
				homeworkPaper.setBatch_name(rs.getString("batch_name"));
				homeworkPapers.add(homeworkPaper);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return homeworkPapers;
	}

	public int getHomeworkPapersNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = SQLHOMEWORKPAPER + Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public boolean getHomeworkPaperExpired(String paperId) {
		dbpool db = new dbpool();
		String sql = "select id from (select id ,to_date(startdate,'yyyy-mm-dd hh24:mi:ss') as startdate1," + "to_date(enddate,'yyyy-mm-dd hh24:mi:ss') as enddate1 from test_homeworkpaper_info  "
				+ "where id = '" + paperId + "') where sysdate between startdate1 and enddate1  ";
		if (db.countselect(sql) > 0)
			return false;
		else
			return true;
	}

	public List getActiveHomeworkPapers(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLHOMEWORKPAPER + " where status = '1' " + Conditions.convertToAndCondition(searchproperty, null);
		// System.out.println("sql==="+sql);
		MyResultSet rs = null;
		ArrayList homeworkPapers = null;
		try {
			db = new dbpool();
			homeworkPapers = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleHomeworkPaper homeworkPaper = new OracleHomeworkPaper();
				homeworkPaper.setId(rs.getString("id"));
				homeworkPaper.setNote(rs.getString("note"));
				homeworkPaper.setCreatDate(rs.getString("creatdate"));
				homeworkPaper.setCreatUser(rs.getString("creatuser"));
				homeworkPaper.setStatus(rs.getString("status"));
				homeworkPaper.setTitle(rs.getString("title"));
				homeworkPaper.setComments(rs.getString("comments"));
				homeworkPaper.setEndDate(rs.getString("enddate"));
				homeworkPaper.setStartDate(rs.getString("startdate"));
				homeworkPaper.setType(rs.getString("type"));
				homeworkPaper.setGroupId(rs.getString("group_id"));
				homeworkPapers.add(homeworkPaper);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return homeworkPapers;
	}

	public List getActiveHomeworkPapers(Page page, List searchproperty, List orderproperty, String batch_id) {
		dbpool db = new dbpool();
		// String sql = SQLHOMEWORKPAPER + " where status = '1'"+batch_id
		// + Conditions.convertToAndCondition(searchproperty, null);
		String sql = SQLHOMEWORKPAPER + " where status = '1'" + Conditions.convertToAndCondition(searchproperty, null);
		// System.out.println("sql==="+sql);
		MyResultSet rs = null;
		ArrayList homeworkPapers = null;
		try {
			db = new dbpool();
			homeworkPapers = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleHomeworkPaper homeworkPaper = new OracleHomeworkPaper();
				homeworkPaper.setId(rs.getString("id"));
				homeworkPaper.setNote(rs.getString("note"));
				homeworkPaper.setCreatDate(rs.getString("creatdate"));
				homeworkPaper.setCreatUser(rs.getString("creatuser"));
				homeworkPaper.setStatus(rs.getString("status"));
				homeworkPaper.setTitle(rs.getString("title"));
				homeworkPaper.setComments(rs.getString("comments"));
				homeworkPaper.setEndDate(rs.getString("enddate"));
				homeworkPaper.setStartDate(rs.getString("startdate"));
				homeworkPaper.setType(rs.getString("type"));
				homeworkPaper.setGroupId(rs.getString("group_id"));
				homeworkPapers.add(homeworkPaper);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return homeworkPapers;
	}

	public int getActiveHomeworkPapersNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = SQLHOMEWORKPAPER + " where status = '1' " + Conditions.convertToAndCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public int getActiveHomeworkPapersNum(List searchproperty, String batch_id) {
		dbpool db = new dbpool();
		String sql = SQLHOMEWORKPAPER + " where status = '1'" + batch_id + Conditions.convertToAndCondition(searchproperty, null);
		// System.out.println(sql);
		return db.countselect(sql);
	}

	public List getHomeworkPaperHistorys(Page page, List searchproperty, List orderproperty, String type) {
		dbpool db = new dbpool();
		String sql = "";
		if (type.equals("teacher"))
			sql = SQLHOMEWORKPAPERHISTORY2 + Conditions.convertToCondition(searchproperty, orderproperty);
		else
			sql = SQLHOMEWORKPAPERHISTORY + Conditions.convertToCondition(searchproperty, orderproperty);
		// System.out.println(sql);
		MyResultSet rs = null;
		ArrayList list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleHomeworkPaperHistory history = new OracleHomeworkPaperHistory();
				history.setId(rs.getString("id"));
				history.setUserId(rs.getString("user_id"));
				history.setTestDate(rs.getString("test_date"));
				history.setTestPaperId(rs.getString("testpaper_id"));
				history.setTestResult(rs.getString("test_result"));
				history.setStatus(rs.getString("ischeck"));
				list.add(history);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public List getHomeworkPaperHistorysNew(Page page, List searchproperty, List orderproperty, String type) {
		dbpool db = new dbpool();
		String sql = "";
		if (type.equals("teacher"))
			sql = SQLHOMEWORKPAPERHISTORY2NEW + Conditions.convertToCondition(searchproperty, orderproperty);
		else
			sql = SQLHOMEWORKPAPERHISTORYNEW + Conditions.convertToCondition(searchproperty, orderproperty);
		// System.out.println(sql);
		MyResultSet rs = null;
		ArrayList list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleHomeworkPaperHistory history = new OracleHomeworkPaperHistory();
				history.setId(rs.getString("id"));
				history.setUserId(rs.getString("user_id"));
				history.setTestDate(rs.getString("test_date"));
				history.setTestPaperId(rs.getString("testpaper_id"));
				// history.setTestResult(rs.getString("test_result"));
				history.setStatus(rs.getString("ischeck"));
				list.add(history);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getHomeworkPaperHistorysNum(List searchproperty, String type) {
		dbpool db = new dbpool();
		String sql = "";
		if (type.equals("teacher"))
			sql = SQLHOMEWORKPAPERHISTORY2 + Conditions.convertToCondition(searchproperty, null);
		else
			sql = SQLHOMEWORKPAPERHISTORY + Conditions.convertToCondition(searchproperty, null);
		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>:"+sql);
		return db.countselect(sql);
	}

	public int getHomeworkPaperHistorysNumNew(List searchproperty, String type) {
		dbpool db = new dbpool();
		String sql = "";
		if (type.equals("teacher"))
			sql = SQLHOMEWORKPAPERHISTORY2NEW + Conditions.convertToCondition(searchproperty, null);
		else
			sql = SQLHOMEWORKPAPERHISTORYNEW + Conditions.convertToCondition(searchproperty, null);
		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>:"+sql);
		return db.countselect(sql);
	}

	public List getOnlineTestCourses(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLONLINETESTCOURSE + Conditions.convertToCondition(searchproperty, orderproperty);
		// System.out.println(sql);
		MyResultSet rs = null;
		ArrayList list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleOnlineTestCourse course = new OracleOnlineTestCourse();
				course.setId(rs.getString("id"));
				course.setNote(rs.getString("note"));
				course.setTitle(rs.getString("title"));
				course.setGroupId(rs.getString("group_id"));
				course.setBatch_id(rs.getString("fk_batch_id"));
				course.setBatch_name(rs.getString("batch_name"));
				OracleOnlineTestBatch batch = new OracleOnlineTestBatch();
				batch.setId(rs.getString("batch_id"));
				course.setBatch(batch);
				course.setNote(rs.getString("note"));
				course.setStartDate(rs.getString("start_date"));
				course.setEndDate(rs.getString("end_date"));
				course.setIsAutoCheck(rs.getString("isautocheck"));
				course.setIsHiddenAnswer(rs.getString("ishiddenanswer"));
				course.setStatus(rs.getString("status"));
				course.setCreatUser(rs.getString("creatuser"));
				course.setCreatDate(rs.getString("creatdate"));
				list.add(course);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public List getOnlineTestCourses(Page page, List searchproperty, List orderproperty, String batch_id) {
		dbpool db = new dbpool();
		// String sql = SQLONLINETESTCOURSE
		// + Conditions.convertToCondition(searchproperty,
		// orderproperty)+batch_id;
		String sql = SQLONLINETESTCOURSE + Conditions.convertToCondition(searchproperty, orderproperty);
		//System.out.println(sql);
		MyResultSet rs = null;
		ArrayList list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleOnlineTestCourse course = new OracleOnlineTestCourse();
				course.setId(rs.getString("id"));
				course.setNote(rs.getString("note"));
				course.setTitle(rs.getString("title"));
				course.setGroupId(rs.getString("group_id"));
				course.setBatch_id(rs.getString("fk_batch_id"));
				course.setBatch_name(rs.getString("batch_name"));
				OracleOnlineTestBatch batch = new OracleOnlineTestBatch();
				batch.setId(rs.getString("batch_id"));
				course.setBatch(batch);
				course.setNote(rs.getString("note"));
				course.setStartDate(rs.getString("start_date"));
				course.setEndDate(rs.getString("end_date"));
				course.setIsAutoCheck(rs.getString("isautocheck"));
				course.setIsHiddenAnswer(rs.getString("ishiddenanswer"));
				course.setStatus(rs.getString("status"));
				course.setCreatUser(rs.getString("creatuser"));
				course.setCreatDate(rs.getString("creatdate"));
				list.add(course);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getOnlineTestCoursesNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = SQLONLINETESTCOURSE + Conditions.convertToCondition(searchproperty, null);
//		System.out.println("OracleTestList getOnlineTestCoursesNum() sql="+sql);
		return db.countselect(sql);
	}

	public int getActiveExperimentPapersNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = SQLEXPERIMENTPAPER + " where status = '1' " + Conditions.convertToAndCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public List getActiveExperimentPapers(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLEXPERIMENTPAPER + " where status = '1' " + Conditions.convertToAndCondition(searchproperty, null);
		MyResultSet rs = null;
		ArrayList papers = null;
		try {
			db = new dbpool();
			papers = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleExperimentPaper paper = new OracleExperimentPaper();
				paper.setId(rs.getString("id"));
				paper.setNote(rs.getString("note"));
				paper.setCreatDate(rs.getString("creatdate"));
				paper.setCreatUser(rs.getString("creatuser"));
				paper.setStatus(rs.getString("status"));
				paper.setTitle(rs.getString("title"));
				paper.setComments(rs.getString("comments"));
				paper.setEndDate(rs.getString("enddate"));
				paper.setStartDate(rs.getString("startdate"));
				paper.setType(rs.getString("type"));
				paper.setGroupId(rs.getString("group_id"));
				papers.add(paper);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return papers;
	}

	public List getExamPaperHistorys(Page page, List searchproperty, List orderproperty, String type) {
		dbpool db = new dbpool();
		String sql = "";
		if (type.equals("teacher"))
			sql = SQLEXAMPAPERHISTORY2 + Conditions.convertToCondition(searchproperty, null);
		else
			sql = SQLEXAMPAPERHISTORY + Conditions.convertToCondition(searchproperty, null);
		MyResultSet rs = null;
		ArrayList list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleExamPaperHistory history = new OracleExamPaperHistory();
				history.setId(rs.getString("id"));
				history.setUserId(rs.getString("user_id"));
				history.setTestDate(rs.getString("test_date"));
				history.setTestPaperId(rs.getString("testpaper_id"));
				history.setTestResult(rs.getString("test_result"));
				list.add(history);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getExamPaperHistorysNum(List searchproperty, String type) {
		dbpool db = new dbpool();
		String sql = "";
		if (type.equals("teacher"))
			sql = SQLEXAMPAPERHISTORY2 + Conditions.convertToCondition(searchproperty, null);
		else
			sql = SQLEXAMPAPERHISTORY + Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public List getExamPapers(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLEXAMPAPER + Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList papers = null;
		try {
			db = new dbpool();
			papers = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleExamPaper paper = new OracleExamPaper();
				paper.setId(rs.getString("id"));
				paper.setTitle(rs.getString("title"));
				paper.setCreatUser(rs.getString("creatuser"));
				paper.setCreatDate(rs.getString("creatdate"));
				paper.setStatus(rs.getString("status"));
				paper.setNote(rs.getString("note"));
				paper.setTime(rs.getString("time"));
				paper.setType(rs.getString("type"));
				paper.setGroupId(rs.getString("group_id"));
				papers.add(paper);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return papers;
	}

	public int getExamPapersNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = SQLEXAMPAPER + Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public boolean getExperimentPaperExpired(String paperId) {
		dbpool db = new dbpool();
		String sql = "select id from (select id ,to_date(startdate,'yyyy-mm-dd hh24:mi:ss') as startdate1," + "to_date(enddate,'yyyy-mm-dd hh24:mi:ss') as enddate1 from test_experimentpaper_info  "
				+ "where id = '" + paperId + "') where sysdate between startdate1 and enddate1  ";
		if (db.countselect(sql) > 0)
			return false;
		else
			return true;
	}

	public List getExperimentPaperHistorys(Page page, List searchproperty, List orderproperty, String type) {
		dbpool db = new dbpool();
		String sql = "";
		if (type.equals("teacher"))
			sql = SQLEXPERIMENTPAPERHISTORY2 + Conditions.convertToCondition(searchproperty, orderproperty);
		else
			sql = SQLEXPERIMENTPAPERHISTORY + Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleExperimentPaperHistory history = new OracleExperimentPaperHistory();
				history.setId(rs.getString("id"));
				history.setUserId(rs.getString("user_id"));
				history.setTestDate(rs.getString("test_date"));
				history.setTestPaperId(rs.getString("testpaper_id"));
				history.setTestResult(rs.getString("test_result"));
				history.setStatus(rs.getString("ischeck"));
				list.add(history);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getExperimentPaperHistorysNum(List searchproperty, String type) {
		dbpool db = new dbpool();
		String sql = "";
		if (type.equals("teacher"))
			sql = SQLEXPERIMENTPAPERHISTORY2 + Conditions.convertToCondition(searchproperty, null);
		else
			sql = SQLEXPERIMENTPAPERHISTORY + Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public List getExperimentPapers(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLEXPERIMENTPAPER + Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList papers = null;
		try {
			db = new dbpool();
			papers = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleExperimentPaper paper = new OracleExperimentPaper();
				paper.setId(rs.getString("id"));
				paper.setNote(rs.getString("note"));
				paper.setCreatDate(rs.getString("creatdate"));
				paper.setCreatUser(rs.getString("creatuser"));
				paper.setStatus(rs.getString("status"));
				paper.setTitle(rs.getString("title"));
				paper.setComments(rs.getString("comments"));
				paper.setEndDate(rs.getString("enddate"));
				paper.setStartDate(rs.getString("startdate"));
				paper.setType(rs.getString("type"));
				paper.setGroupId(rs.getString("group_id"));
				papers.add(paper);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return papers;
	}

	public int getExperimentPapersNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = SQLEXPERIMENTPAPER + Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public List getOnlineExamCourses(Page page, List searchproperty, List orderproperty) {
		dbpool db = new dbpool();
		String sql = SQLONLINEEXAMCOURSE + Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleOnlineExamCourse course = new OracleOnlineExamCourse();
				course.setId(rs.getString("id"));
				course.setNote(rs.getString("note"));
				course.setTitle(rs.getString("title"));
				course.setGroupId(rs.getString("group_id"));
				OracleOnlineTestBatch batch = new OracleOnlineTestBatch();
				batch.setId(rs.getString("batch_id"));
				course.setBatch(batch);
				course.setNote(rs.getString("note"));
				course.setStartDate(rs.getString("start_date"));
				course.setEndDate(rs.getString("end_date"));
				course.setIsAutoCheck(rs.getString("isautocheck"));
				course.setIsHiddenAnswer(rs.getString("ishiddenanswer"));
				course.setStatus(rs.getString("status"));
				course.setCreatUser(rs.getString("creatuser"));
				course.setCreatDate(rs.getString("creatdate"));
				list.add(course);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getOnlineExamCoursesNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = SQLONLINEEXAMCOURSE + Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public int getOnlineTestCoursesNum2(List searchproperty) {
		dbpool db = new dbpool();
		String sql = SQLONLINETESTCOURSE + Conditions.convertToCondition(searchproperty, null);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = sdf1.format(new Date());
		String sdf = s.toString();
		sql = sql + " and to_date(end_date,'yyyy-mm-dd hh24:mi:ss')>= to_date('" + sdf + "','yyyy-mm-dd hh24:mi:ss')" + "and to_date(start_date,'yyyy-mm-dd hh24:mi:ss')<= to_date('" + sdf
				+ "','yyyy-mm-dd hh24:mi:ss')";
		return db.countselect(sql);
	}

	public int getOnlineTestCoursesNum2(List searchproperty, String batch_id) {
		dbpool db = new dbpool();
		String sql = SQLONLINETESTCOURSE + Conditions.convertToCondition(searchproperty, null);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = sdf1.format(new Date());
		String sdf = s.toString();
		sql = sql + " and to_date(end_date,'yyyy-mm-dd hh24:mi:ss')>= to_date('" + sdf + "','yyyy-mm-dd hh24:mi:ss')" + "and to_date(start_date,'yyyy-mm-dd hh24:mi:ss')<= to_date('" + sdf
				+ "','yyyy-mm-dd hh24:mi:ss')" + batch_id;
		return db.countselect(sql);
	}

	public Map getOnlineTestScore(String openCourseId, String studentId) {
		String scoreSql =

		"select ele.score_exam      as score,\n" + "       ele.exam_times      as times,\n" + "       ptc.examtimes_allow as maxTimes\n" + "  from pr_bzz_tch_stu_elective ele,\n"
				+ "       pr_bzz_tch_opencourse   pto,\n" + "       pe_bzz_tch_course       ptc\n" + " where ele.fk_tch_opencourse_id = pto.id\n" + "   and pto.fk_course_id = ptc.id"
				+ "   and ele.fk_stu_id = '" + studentId + "'\n" + "   and ele.fk_tch_opencourse_id = '" + openCourseId + "'";
		dbpool db = new dbpool();
		MyResultSet rs = null;
		Map re = new HashMap();
		try {
			rs = db.executeQuery(scoreSql);
			if (rs != null && rs.next()) {
				re.put("score", rs.getDouble(1));
				re.put("times", rs.getInt(2));
				re.put("maxTimes", rs.getInt(3));
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return re;
	}

	public List getQuestions(String loreId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select tsi.questioncore,tsi.title,tsi.type from test_storequestion_info tsi where tsi.lore ='" + loreId + "'";
		List list = new ArrayList();

		rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				OracleStoreQuestion que = new OracleStoreQuestion();
				// 1.获得字段内全部内容；
				String temp = rs.getString("questioncore");
				// 2.截取字符串，只留下内容；
				String subTemp = temp.substring(temp.indexOf("<body>"), temp.indexOf("</body>"));
				// 3.做长度限制，超过四十个字就截掉；
				que.setQuestionCore(subTemp.length() > 40 ? subTemp.substring(0, 39) : subTemp);
				que.setTitle(rs.getString("title"));
				String type = rs.getString("type");
				if (type != null && "DANXUAN".equals(type)) {
					type = "单选题";
				} else if (type != null && "DUOXUAN".equals(type)) {
					type = "多选题";
				} else if (type != null && "PANDUAN".equals(type)) {
					type = "判断题";
				} else if (type != null && "WENDA".equals(type)) {
					type = "问答题";
				} else if (type != null && "TIANKONG".equals(type)) {
					type = "填空题";
				} else {
					type = "不详";
				}
				que.setType(type);
				list.add(que);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close(rs);
		}
		return list;

	}

	public Map getOnlineExamScore(String stuid, String piciId) {
		String scoreSql =

		"select nvl(pbps.exam_times,0) as stuttime, pbp.exam_times as maxTimes, nvl(pbps.score,0),nvl(pbp.passscore,80)\n" + "  from pe_bzz_pici_student pbps, pe_bzz_pici pbp\n"
				+ " where pbps.pc_id = pbp.id\n" + "   and pbps.stu_id = '" + stuid + "'\n" + "   and pbp.id = '" + piciId + "'";
		dbpool db = new dbpool();
		MyResultSet rs = null;
		Map re = new HashMap();
		try {
			rs = db.executeQuery(scoreSql);
			if (rs != null && rs.next()) {
				re.put("score", rs.getDouble(3));
				re.put("times", rs.getString(1));
				re.put("maxTimes", rs.getString(2));
				re.put("passScore", rs.getDouble(4));
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return re;

	}

	public boolean getisExam(String stuid, String piciId, String type) {
		String scoreSql = "select count(info.id)\n" + "  from test_testpaper_history  history,\n" + "       test_paperquestion_info paper,\n" + "       test_testpaper_info     info\n"
				+ " where info.id = history.testpaper_id\n" + "   and info.id = paper.testpaper_id\n" + "   and info.group_id = '" + piciId + "'\n" + "   and nvl(history.ischeck,0)!='1'\n"
				+ "   and history.t_user_id = '" + stuid + "'";
		if (type != null && !"".equals(type)) {
			scoreSql += " and paper.type in(" + type + ")";
		}
		dbpool db = new dbpool();
		MyResultSet rs = null;
		int number = 0;
		try {
			rs = db.executeQuery(scoreSql);
			if (rs != null && rs.next()) {
				number = rs.getInt(1);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		if (number > 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 课后测验列表
	 * 
	 * @author Lee-正在使用
	 * @param page
	 * @param searchproperty
	 * @return
	 */
	public List getOnlineTestCourses(Page page, List searchproperty) {
		dbpool db = new dbpool();
		String sql = SQLONLINETESTCOURSELEE + Conditions.convertToCondition(searchproperty, null);
		MyResultSet rs = null;
		ArrayList list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleOnlineTestCourse course = new OracleOnlineTestCourse();
				course.setExamtimesAllow(rs.getString("examtimes_allow"));
				course.setPassrole(rs.getString("passrole"));
				course.setExamTimes(rs.getString("exam_times"));
				course.setIspass(rs.getString("ispass"));
				course.setId(rs.getString("id"));
				course.setNote(rs.getString("note"));
				course.setTitle(rs.getString("title"));
				course.setGroupId(rs.getString("group_id"));
				course.setBatch_id(rs.getString("fk_batch_id"));
				course.setBatch_name(rs.getString("batch_name"));
				OracleOnlineTestBatch batch = new OracleOnlineTestBatch();
				batch.setId(rs.getString("batch_id"));
				course.setBatch(batch);
				course.setNote(rs.getString("note"));
				course.setStartDate(rs.getString("start_date"));
				course.setEndDate(rs.getString("end_date"));
				course.setIsAutoCheck(rs.getString("isautocheck"));
				course.setIsHiddenAnswer(rs.getString("ishiddenanswer"));
				course.setStatus(rs.getString("status"));
				course.setCreatUser(rs.getString("creatuser"));
				course.setCreatDate(rs.getString("creatdate"));
				list.add(course);
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	/**
	 * 查询数量
	 * 
	 * @param searchproperty
	 * @param batch_id
	 * @return
	 */
	public int getOnlineTestCoursesNum3(List searchproperty) {
		dbpool db = new dbpool();
		String sql = SQLONLINETESTCOURSELEE + Conditions.convertToCondition(searchproperty, null);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = sdf1.format(new Date());
		String sdf = s.toString();
		//查询优化 zgl 查询条件不用处理函数，否则全表扫描 时间就是varchar2类型
		sql = sql + " and end_date >= '" + sdf + "'" + " and start_date <= '" + sdf + "'";
		return db.countselect(sql);
	}
}
