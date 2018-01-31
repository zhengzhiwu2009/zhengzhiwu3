package com.whaty.platform.entity.service.regStudent;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.whaty.platform.User;
import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.interaction.OracleInteractionList;
import com.whaty.platform.database.oracle.standard.interaction.OracleInteractionTeachClass;
import com.whaty.platform.database.oracle.standard.interaction.answer.OracleQuestion;
import com.whaty.platform.database.oracle.standard.test.OracleTestList;
import com.whaty.platform.database.oracle.standard.test.history.OracleTestPaperHistory;
import com.whaty.platform.database.oracle.standard.test.onlinetest.OracleOnlineTestBatch;
import com.whaty.platform.database.oracle.standard.test.onlinetest.OracleOnlineTestCourse;
import com.whaty.platform.database.oracle.standard.test.paper.OracleTestPaper;
import com.whaty.platform.entity.user.EntityUser;
import com.whaty.platform.interaction.InteractionTeachClass;
import com.whaty.platform.interaction.answer.Question;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.test.paper.TestPaper;
import com.whaty.platform.test.question.TestQuestionType;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.string.encode.HtmlEncoder;

public class PeBzzRegService {
	
	private String testResult;
	
	
	
	private String SQLONLINETESTCOURSELEE = " SELECT  SSO_USER_ID,EXAMTIMES_ALLOW, PASSROLE, EXAM_TIMES, ISPASS, ID, TITLE, GROUP_ID, BATCH_ID, NOTE, START_DATE, END_DATE, ISAUTOCHECK, ISHIDDENANSWER, STATUS, CREATUSER, CREATDATE, FK_BATCH_ID, BATCH_NAME "
		+ " FROM (SELECT F.ID SSO_USER_ID, C.EXAMTIMES_ALLOW, C.PASSROLE, E.EXAM_TIMES, E.ISPASS, OCI.ID AS ID, OCI.TITLE AS TITLE, OCI.GROUP_ID AS GROUP_ID, OCI.BATCH_ID AS BATCH_ID, OCI.NOTE AS NOTE, OCI.START_DATE AS START_DATE, OCI.END_DATE AS END_DATE, OCI.ISAUTOCHECK AS ISAUTOCHECK, OCI.ISHIDDENANSWER AS ISHIDDENANSWER, OCI.STATUS AS STATUS, OCI.CREATUSER AS CREATUSER, TO_CHAR(OCI.CREATDATE, 'yyyy-mm-dd') AS CREATDATE, PBB.ID AS FK_BATCH_ID, PBB.NAME AS BATCH_NAME "
		+ " FROM ONLINETEST_COURSE_INFO OCI, PE_BZZ_BATCH PBB, PE_BZZ_TCH_COURSE C, PR_BZZ_TCH_OPENCOURSE D, PR_BZZ_TCH_STU_ELECTIVE E, PE_BZZ_STUDENT PBS, SSO_USER F WHERE OCI.FK_BATCH_ID = PBB.ID(+) AND OCI.GROUP_ID = C.ID AND C.ID = D.FK_COURSE_ID AND D.ID = E.FK_TCH_OPENCOURSE_ID AND E.FK_STU_ID = PBS.ID AND PBS.FK_SSO_USER_ID = F.ID " +
				" GROUP BY F.ID, C.EXAMTIMES_ALLOW, C.PASSROLE,E.EXAM_TIMES, E.ISPASS, OCI.ID,OCI.TITLE,OCI.GROUP_ID,OCI.BATCH_ID, OCI.NOTE," +
				"OCI.START_DATE, OCI.END_DATE,OCI.ISAUTOCHECK, OCI.ISHIDDENANSWER, OCI.STATUS, OCI.CREATUSER,OCI.CREATDATE, PBB.ID,PBB.NAME) ";
	
	public Map getOnlineTestScore(String openCourseId, String studentId) {
		String scoreSql =

		"select ele.score_exam      as score,\n" + 
		"       ele.exam_times      as times,\n" + 
		"       ptc.examtimes_allow as maxTimes\n" + 
		"  from pr_bzz_tch_stu_elective ele,\n" + 
		"       pr_bzz_tch_opencourse   pto,\n" + 
		"       pe_bzz_tch_course       ptc,\n" + 
		"		pe_bzz_student 			pbs\n"  +
		" where ele.fk_tch_opencourse_id = pto.id\n" +
		"   and pto.fk_course_id = ptc.id" +
		"	and ele.fk_stu_id = pbs.id" +
		"   and pbs.fk_sso_user_id = '" + studentId + "'\n" + 
		"   and ele.fk_tch_opencourse_id = '" + openCourseId + "'";
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
	
	/**
	 * 判断课程是否完成学习
	 */
	public boolean isLearningCompleted(String courseId, String student_id) {
		dbpool db = new dbpool();
		int cou=0;
		String sql =
				"select 1\n" +
				"  from training_course_student tcs\n" + 
				" where tcs.student_id = '" + student_id + "'\n" + 
				"   and tcs.course_id = '" + courseId + "'\n" + 
				"   and tcs.learn_status = 'COMPLETED'";
			cou = db.countselect(sql);
		return cou>0;
	}
	
	/**
	 * 用于判断选课表中是否存在考试成绩，如果存在考试成绩，则不进行满意度调查问卷提示，直接进入考试
	 * @param student_id
	 * @param course_id
	 * @return
	 */
	public boolean isShowManyi(String student_id, String course_id) {
		dbpool db = new dbpool();
		int cou=0;
		String sql =

			"select case\n" +
			"         when nvl(ele.exam_times,0)<1 then\n" + 
			"          0\n" + 
			"         else\n" + 
			"          1\n" + 
			"       END as score\n" + 
			"  from pr_bzz_tch_stu_elective ele\n" + 
			" inner join pr_bzz_tch_opencourse pbto on ele.fk_tch_opencourse_id =\n" + 
			"                                          pbto.id\n" + 
			" inner join pe_bzz_student pbs on ele.fk_stu_id = pbs.id\n" + 
			" inner join pe_bzz_tch_course pbtc on pbtc.id = pbto.fk_course_id\n" + 
			"\n" + 
			" where pbtc.id = '"+course_id+"'\n" + 
			"   and pbs.fk_sso_user_id = '"+student_id+"'";




			MyResultSet rs = db.executeQuery(sql);
			try {
				if(rs.next()) {
					cou = rs.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				db.close(rs);
				db=null;
			}
		return cou>0;
		
	}
	
	/**
	 * 判断课程是否完成满意度调查
	 */
	public boolean isManyiCompleted(String courseId, String student_id) {
		dbpool db = new dbpool();
		int cou=0;
		String sql =
			"select pvp.id\n" +
			"  from pe_vote_paper pvp, pr_vote_record pvr\n" + 
			" where pvp.id = pvr.fk_vote_paper_id\n" + 
			"   and pvp.courseid = '"+courseId+"'\n" + 
			"   and pvr.ssoid = '"+student_id+"'";
			cou = db.countselect(sql);
		return cou>0;
	}
	
	public List getOnlineTestCourses(Page page, String id, String title, String note, String startDate, String endDate, String status, String isAutoCheck, String isHiddenAnswer, String batchId,
			String groupId, String creatUser) throws NoRightException, PlatformException {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (note != null) {
				searchList.add(new SearchProperty("note", note, "like"));
			}
			if (startDate != null) {
				searchList.add(new SearchProperty("start_date", startDate, "D>="));
			}
			if (endDate != null)
				searchList.add(new SearchProperty("end_date", endDate, "D<="));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));
			if (isAutoCheck != null)
				searchList.add(new SearchProperty("isautocheck", isAutoCheck, "="));
			if (isHiddenAnswer != null)
				searchList.add(new SearchProperty("ishiddenanswer", isHiddenAnswer, "="));
			if (batchId != null)
				searchList.add(new SearchProperty("batch_id", batchId, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			if (creatUser != null)
				searchList.add(new SearchProperty("creatuser", creatUser, "="));
			return testList.getOnlineTestCourses(page, searchList, null);
	}
	
	/**
	 * 课后测验列表
	 * 
	 * @author Lee
	 * @param page
	 * @param title
	 * @param status
	 * @param groupId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public List getOnlineTestCourses(Page page, String title, String status, String groupId) throws NoRightException, PlatformException {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			String uid = us.getSsoUser().getId();
			List searchList = new ArrayList();
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			searchList.add(new SearchProperty("sso_user_id", uid, "="));
			return this.getOnlineTestCourses(page, searchList);
	}
	
	public int getOnlineTestCoursesNum(String id, String title, String note, String startDate, String endDate, String status, String isAutoCheck, String isHiddenAnswer, String batchId,
			String groupId, String creatUser) throws NoRightException, PlatformException {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (note != null) {
				searchList.add(new SearchProperty("note", note, "like"));
			}
			if (startDate != null) {
				searchList.add(new SearchProperty("start_date", startDate, "D>="));
			}
			if (endDate != null)
				searchList.add(new SearchProperty("end_date", endDate, "D<="));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));
			if (isAutoCheck != null)
				searchList.add(new SearchProperty("isautocheck", isAutoCheck, "="));
			if (isHiddenAnswer != null)
				searchList.add(new SearchProperty("ishiddenanswer", isHiddenAnswer, "="));
			if (batchId != null)
				searchList.add(new SearchProperty("batch_id", batchId, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			if (creatUser != null)
				searchList.add(new SearchProperty("creatuser", creatUser, "="));
			return testList.getOnlineTestCoursesNum(searchList);
	}
	
	/**
	 * 课后测验数量
	 */
	public int getOnlineTestCoursesNum(String title, String status, String groupId, String user) throws NoRightException, PlatformException {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			return testList.getOnlineTestCoursesNum3(searchList);
	}
	
	/**
	 * by 蔡磊 设为无效前判断 说明：如果学员已经考试，不能设为无效
	 * 
	 * @param testCourseId
	 */
	public Boolean invalideJudge(String testCourseId) {

		Boolean flag = true;
		dbpool db = new dbpool();
		MyResultSet rs = null;

		String sql = "select 1 from onlinetest_course_info oci\n" + "inner join test_testpaper_info tti on oci.group_id = tti.group_id\n"
				+ "inner join test_testpaper_history tth on tth.testpaper_id = tti.id\n" + "where oci.id = '" + testCourseId + "'";

		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return flag;
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
	
	public List getTestPapersByOnlineTestCourse(Page page, String testCourseId) throws NoRightException, PlatformException {
			OracleOnlineTestCourse testCourse = new OracleOnlineTestCourse();
			testCourse.setId(testCourseId);
			return testCourse.getTestPapers(page);
	}
	
	public int getTestPaperHistorysNum(String userId, String testPaperId, String beginDate, String endDate, String groupId) throws NoRightException, PlatformException {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			String type = "teacher";
			if (userId != null) {
				searchList.add(new SearchProperty("user_id", userId, "="));
				type = "student";
			}
			if (testPaperId != null)
				searchList.add(new SearchProperty("testpaper_id", testPaperId, "="));
			if (beginDate != null) {
				searchList.add(new SearchProperty("test_date", beginDate, "D<="));
			}
			if (endDate != null) {
				searchList.add(new SearchProperty("test_date", endDate, "D<="));
			}
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			return testList.getTestPaperHistorysNum(searchList, type);
	}
	
	public TestPaper getTestPaper(String id) throws NoRightException, PlatformException {
			OracleTestPaper testPaper = new OracleTestPaper(id);
			return testPaper;
	}
	
	public int getTestPapersNumByOnlineTestCourse(String testCourseId) throws NoRightException, PlatformException {
			OracleOnlineTestCourse testCourse = new OracleOnlineTestCourse();
			testCourse.setId(testCourseId);
			return testCourse.getTestPapersNum();
	}
	
	public List getTestPaperHistorys(Page page, String userId, String testPaperId, String beginDate, String endDate, String groupId) throws NoRightException, PlatformException {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			String type = "teacher";
			if (userId != null) {
				searchList.add(new SearchProperty("user_id", userId, "="));
				type = "student";
			}
			if (testPaperId != null)
				searchList.add(new SearchProperty("testpaper_id", testPaperId, "="));
			if (beginDate != null) {
				searchList.add(new SearchProperty("test_date", beginDate, "D<="));
			}
			if (endDate != null) {
				searchList.add(new SearchProperty("test_date", endDate, "D<="));
			}
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			return testList.getTestPaperHistorys(page, searchList, null, type);
	}
	
	/**
	 * by 魏慧宁 删除试卷前判断 说明：如果学员已经考试，不能删除
	 * 
	 * @param loreId
	 */
	public Boolean deleteJudge(String loreId) {
		Boolean flag = true;
		dbpool db = new dbpool();
		MyResultSet rs = null;

		String sql = "select 1 from test_testpaper_history where testpaper_id = '" + loreId + "'";

		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return flag;
	}
	
	/**
	 * 新添加，分数保存在elective
	 */
	public int addTestPaperHistory(String userId, String testPaperId, String score, HashMap answer) throws NoRightException, PlatformException {
			this.setTestResult(answer);
			return this.add(userId, testPaperId, score);
	}
	
	public int add(String userId, String testPaperId, String score) throws PlatformException {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		dbpool db = new dbpool();
		String t_user_id = "";
		t_user_id = userId;
//		t_user_id = t_user_id.substring(t_user_id.indexOf("(")+1, t_user_id.indexOf(")"));
		String openTime = "";
		try {
			openTime = ServletActionContext.getRequest().getSession().getAttribute(us.getSsoUser().getId() + "startTime").toString();
		} catch (Exception e) {
			openTime = "SYSDATE";
		}
		String sql = "insert into test_testpaper_history (id, user_id,testpaper_id, test_date, test_result,score,t_user_id,test_open_date) values ("
				+ "to_char(s_test_testpaper_history_id.nextval), '"
				+ userId
				+ "', '"
				+ testPaperId
				+ "',sysdate, ?" + ", '" + score + "','"+t_user_id+"','" + openTime + "')";
		if("SYSDATE".equalsIgnoreCase(openTime)){sql = "insert into test_testpaper_history (id, user_id,testpaper_id, test_date, test_result,score,t_user_id,test_open_date) values ("
			+ "to_char(s_test_testpaper_history_id.nextval), '"
			+ userId
			+ "', '"
			+ testPaperId
			+ "',sysdate, ?" + ", '" + score + "','"+t_user_id+"', TO_CHAR(SYSDATE,'yyyy-MM-dd hh24:mi:ss'))";
		}
		int suc = db.executeUpdate(sql, this.getTestResult());
		UserAddLog.setDebug("OracleTestPaperHistory.add() SQL=" + sql + " COUNT=" + suc + " DATE=" + new Date());
		String sql1 = "";
		if(score!=null&&!"".equals(score)&&Double.parseDouble(score)>=80){//如果分数大于80状态改为1，完成时间改为
			sql1 = " ,e.ispass ='1',e.completed_time=sysdate ";
		}
			
		String scoreSql = 
			"update pr_bzz_tch_stu_elective e\n" +
			"   set e.exam_times = nvl(e.exam_times,0) + 1, e.score_exam = decode(sign(nvl(e.score_exam,0)-" + score + "),-1," + score + ",e.score_exam) "+sql1+"\n" + 
			" where e.id in (select ele.id\n" + 
			"                  from pr_bzz_tch_stu_elective ele,\n" + 
			"                       pr_bzz_tch_opencourse   opn,\n" + 
			"                       test_testpaper_info     tti,\n" + 
			"						pe_bzz_student 			pbs,\n" +
			"						sso_user 				su\n" +
			"                 where ele.fk_tch_opencourse_id = opn.id\n" + 
			"                   and opn.fk_course_id = tti.group_id\n" + 
			"                   and ele.fk_stu_id = pbs.id\n" +
			"					and pbs.fk_sso_user_id = su.id\n" +
			"					and su.id = '" + userId + "'\n" +
			"                   and tti.id = '" + testPaperId + "')";
		db.executeUpdate(scoreSql);
		UserUpdateLog.setDebug("OracleTestPaperHistory.add() SQL=" + scoreSql + " DATE=" + new Date());
		return suc;
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
				xml += "<item><id>" + id + "</id><type>" + type
						+ "</type><title>" + HtmlEncoder.encode(title)
						+ "</title><sanswer>" + HtmlEncoder.encode(sAnswer)
						+ "</sanswer><uanswer>" + HtmlEncoder.encode(uAnswer)
						+ "</uanswer><sscore>" + HtmlEncoder.encode(sScore)
						+ "</sscore><uscore>" + HtmlEncoder.encode(uScore)
						+ "</uscore><note>" + HtmlEncoder.encode(note)
						+ "</note></item>";
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
				if (sScore == null || sScore.equals("")
						|| sScore.equals("null"))
					sScore = "0";
				xml += "<sscore>" + HtmlEncoder.encode(sScore) + "</sscore>";
				try{
				uScore = (String) uScoreList.get(0);
				} catch(Exception e1) {
					
				}
				if (uScore == null || uScore.equals("")
						|| uScore.equals("null"))
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
							+ HtmlEncoder.encode(title) + "</title><sanswer>"
							+ HtmlEncoder.encode(sAnswer)
							+ "</sanswer><uanswer>"
							+ HtmlEncoder.encode(uAnswer)
							+ "</uanswer><sscore>" + HtmlEncoder.encode(sScore)
							+ "</sscore><uscore>" + HtmlEncoder.encode(uScore)
							+ "</uscore><note>" + HtmlEncoder.encode(note)
							+ "</note></item>";
				}
				xml += "</subitem></item>";
			}
		}
		xml += "</answers>";
		this.setTestResult(xml);
	}
	
	public int getQuestionsNum(String teachclass_id, String title, String name,
			String questionType, String notuse) throws NoRightException {
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
	}
	
	public List getQuestions(Page page, String teachclass_id, String title,
			String name, String questionType, String noUse)
			throws NoRightException {
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
	}
	
	public int addQuestion(User student, String title, String body, String key, String type,
			String submituserId, String submituserName, String submituserType,
			String courseId, String reStatusStr) throws NoRightException,
			PlatformException {
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
									+ student.getId()
									+ "</whaty><whaty>BEHAVIOR$|$addQuestion</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
	}
	
	public EntityUser getStudent(String ssoId) {
		String sql = "select u.id,u.login_id,u.password,u.fk_role_id,m.name from sso_user u ,pe_manager m where m.fk_sso_user_id = u.id and u.id='"+ssoId+"'";
		EntityUser user = new EntityUser();
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		try {
			if(rs != null && rs.next()) {
				user.setId(rs.getString("id"));
				user.setLoginId(rs.getString("login_id"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
//				this.setEmail(rs.getString("email"));
				//user.setLoginType(rs.getString("typeId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
		}
		return user;
	}
	
	public Question getQuestion(String tid) throws NoRightException {
			return new OracleQuestion(tid);
	}
	
	public List getAnswers(Page page, List searchProperty, List orderProperty) throws NoRightException {
			OracleInteractionList interactionList = new OracleInteractionList();
			return interactionList.getAnswers(page, searchProperty, orderProperty);
	}
	
	public InteractionTeachClass getTeachClass(String id)
			throws PlatformException {
		return new OracleInteractionTeachClass(id);
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public String getTestResult() {
		return testResult;
	}
	
}
