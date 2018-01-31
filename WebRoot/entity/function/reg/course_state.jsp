<%@page language="java" import="java.net.*" pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.whaty.platform.entity.bean.SsoUser"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.*"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%>
<%@page import="com.whaty.platform.standard.scorm.operation.*"%>
<%@page import="com.whaty.platform.database.oracle.standard.standard.scorm.operation.*"%>
<style type="text/css">
.tab {
	border-collapse: collapse;
}

.th {
	font-size: 14px;
	background: url(thbg.jpg) repeat-x top #59a4da;
	border: #dedede 1px solid;
	color: #FFF;
}

.td {
	font-size: 12px;
	border: #dedede 1px solid;
	text-align: center;
}
</style>
<%!String fixnull(String str) {
		if (str == null || str.equals("") || str.equals("null"))
			str = "-";
		return str;
	}%>
<%
	UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	SsoUser user = us.getSsoUser();
	String uid = user.getId();
	//课程是否需要考试
	String exam_y_n = request.getParameter("exam_y_n");
	if (exam_y_n == null) {
		exam_y_n = (String) request.getAttribute("exam_y_n");
	}
	String opencourseid = request.getParameter("opencourseId");//课程ID
	String courseId = request.getParameter("courseId");//课程ID
	String userId = request.getParameter("id");
	String type = request.getParameter("type");
	if ("0".equals(type)) {
		uid = userId;
	}
	String opId = request.getParameter("opencourseId");//开课ID
	String sql = " SELECT DISTINCT  PBTC.CODE CODE, PBTC.NAME NAME, EC.NAME EC1NAME, PBTC.TIME, " + " TO_CHAR(TCS.GET_DATE, 'yyyy-MM-dd hh24:mi:ss') START_TIME, "
			+ " TO_CHAR(SSS.LAST_ACCESSDATE, 'yyyy-MM-dd hh24:mi:ss') END_TIME, TO_CHAR(TCS.COMPLETE_DATE, 'yyyy-MM-dd hh24:mi:ss') COMPLETE_DATE, " + " PBTC.STUDYDATES, TCS.LEARN_STATUS, "
			+ " CASE WHEN TCS.LEARN_STATUS = 'COMPLETED' AND PBTC.FLAG_IS_EXAM = '40288acf3ae01103013ae012940b0001' AND PBTUE.ISPASS = '1' THEN 'PASSED' "
			+ " WHEN PBTC.FLAG_IS_EXAM = '40288acf3ae01103013ae0130d030002' THEN 'NOEXAMLEE' "
			+ " ELSE 'UNFINISHED' END AS EXAMSTATUS, PBTUE.ISPASS, TO_CHAR(PBTUE.COMPLETED_TIME, 'yyyy-MM-dd hh24:mi:ss') COMPLETED_TIME "
			+ " FROM PE_BZZ_TCH_COURSE PBTC INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON PBTC.ID = PBTO.FK_COURSE_ID "
			+ " INNER JOIN pr_bzz_tch_stu_elective PBTUE ON PBTO.ID = PBTUE.FK_TCH_OPENCOURSE_ID " + " INNER JOIN PE_BZZ_STUDENT PBS ON PBTUE.FK_STU_ID = PBS.ID "
			+ " INNER JOIN SSO_USER SU ON PBS.FK_SSO_USER_ID = SU.ID " + " AND SU.ID = '" + uid + "' "
			+ " LEFT JOIN SCORM_STU_SCO SSS ON SU.ID = SSS.STUDENT_ID AND PBTC.CODE = SSS.COURSE_ID " + " LEFT JOIN ENUM_CONST EC ON PBTC.FLAG_COURSETYPE = EC.ID "
			+ " LEFT JOIN TRAINING_COURSE_STUDENT TCS ON PBTUE.FK_TRAINING_ID = TCS.ID " + " WHERE PBTC.ID = '" + courseId + "' ";
	dbpool db = new dbpool();
	List<Map<String, String>> courseList = new ArrayList<Map<String, String>>();
	MyResultSet mrsCourseList = db.executeQuery(sql);
	while (null != mrsCourseList && mrsCourseList.next()) {
		Map<String, String> m = new HashMap<String, String>();
		String code = fixnull(mrsCourseList.getString("code"));
		m.put("code", code);
		String name = fixnull(mrsCourseList.getString("name"));
		m.put("name", name);
		String ec1name = fixnull(mrsCourseList.getString("ec1name"));
		m.put("ec1name", ec1name);
		String time = fixnull(mrsCourseList.getString("time"));
		m.put("time", time);
		String start_time = fixnull(mrsCourseList.getString("start_time"));
		m.put("start_time", start_time);
		String end_time = fixnull(mrsCourseList.getString("end_time"));
		m.put("end_time", end_time);
		String complete_date = fixnull(mrsCourseList.getString("complete_date"));
		m.put("complete_date", complete_date);
		String studydates = fixnull(mrsCourseList.getString("studydates"));
		m.put("studydates", studydates);
		String learn_status = fixnull(mrsCourseList.getString("learn_status"));
		m.put("learn_status", learn_status);
		String examstatus = fixnull(mrsCourseList.getString("examstatus"));
		m.put("examstatus", examstatus);
		String ispass = fixnull(mrsCourseList.getString("ispass"));
		m.put("ispass", ispass);
		String completed_time = fixnull(mrsCourseList.getString("completed_time"));
		m.put("completed_time", completed_time);
		courseList.add(m);
	}
	db.close(mrsCourseList);
	mrsCourseList = null;//Lee 2014年9月14日22:28:18
	sql = "SELECT TO_CHAR(A.START_TIME, 'YYYY-MM-DD HH24:MI:SS') START_TIME, TO_CHAR(A.END_TIME, 'YYYY-MM-DD HH24:MI:SS') END_TIME, A.TOTAL_TIME FROM TRAINING_COURSE_STUDENT_HIS A INNER JOIN (SELECT FK_STU_ID, FK_TCH_OPENCOURSE_ID FROM PR_BZZ_TCH_STU_ELECTIVE WHERE FK_STU_ID = (SELECT ID FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '"
			+ user.getId()
			+ "') UNION ALL SELECT FK_STU_ID, FK_TCH_OPENCOURSE_ID FROM ELECTIVE_HISTORY WHERE FK_STU_ID = (SELECT ID FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '"
			+ user.getId()
			+ "')) B ON A.COURSE_ID = B.FK_TCH_OPENCOURSE_ID INNER JOIN PE_BZZ_STUDENT C ON A.FK_STU_ID = C.FK_SSO_USER_ID AND C.FK_SSO_USER_ID = '"
			+ user.getId()
			+ "' AND B.FK_STU_ID = C.ID INNER JOIN SSO_USER D ON C.FK_SSO_USER_ID = D.ID INNER JOIN PR_BZZ_TCH_OPENCOURSE E ON B.FK_TCH_OPENCOURSE_ID = E.ID AND E.FK_COURSE_ID = '"
			+ courseId + "' INNER JOIN PE_BZZ_TCH_COURSE F ON E.FK_COURSE_ID = F.ID GROUP BY A.START_TIME, A.END_TIME, A.TOTAL_TIME ORDER BY A.START_TIME DESC";
	MyResultSet mrsTrainingList = null;
	mrsTrainingList = db.executeQuery(sql);
	List<Map<String, String>> trainingList = new ArrayList<Map<String, String>>();
	while (null != mrsTrainingList && mrsTrainingList.next()) {
		Map<String, String> t = new HashMap<String, String>();
		String start_time = fixnull(mrsTrainingList.getString("start_time"));
		t.put("start_time", start_time);
		String end_time = fixnull(mrsTrainingList.getString("end_time"));
		t.put("end_time", end_time);
		String total_time = fixnull(mrsTrainingList.getString("total_time"));
		t.put("total_time", total_time);
		trainingList.add(t);
	}
	mrsTrainingList.close();
	db.close(mrsTrainingList);
	sql = " SELECT TO_CHAR(PVR.VOTE_DATE,'yyyy-MM-dd') VOTE_DATE " + "   FROM PE_VOTE_PAPER PVP, PR_VOTE_RECORD PVR " + "  WHERE PVP.ID = PVR.FK_VOTE_PAPER_ID " + " AND PVP.COURSEID = '"
			+ courseId + "' AND PVR.SSOID = '" + uid + "'";
	MyResultSet mrsManYiList = null;
	mrsManYiList = db.executeQuery(sql);
	List<Map<String, String>> manYiList = new ArrayList<Map<String, String>>();
	while (null != mrsManYiList && mrsManYiList.next()) {
		Map<String, String> my = new HashMap<String, String>();
		String vote_date = fixnull(mrsManYiList.getString("vote_date"));
		my.put("vote_date", vote_date);
		manYiList.add(my);
	}
	db.close(mrsManYiList);
	mrsManYiList = null;//Lee 2014年9月14日22:28:40
	List<Map<String, String>> testList = new ArrayList<Map<String, String>>();
	List<Map<String, String>> testList2 = new ArrayList<Map<String, String>>();
	MyResultSet mrsTestList = null;
	if ("true".equalsIgnoreCase(exam_y_n)) {
		sql = " SELECT DISTINCT TTH.TEST_OPEN_DATE OPEN_TIME, TO_CHAR(TTH.TEST_DATE, 'yyyy-MM-dd hh24:mi:ss') SUBMIT_TIME, PBTC.EXAMTIMES_ALLOW EXAMTIMES_ALLOW, PBTC.PASSROLE PASSROLE, TTH.SCORE SCORE_EXAM, CASE WHEN TO_NUMBER(TTH.SCORE) >= TO_NUMBER(PBTC.PASSROLE) THEN '1' ELSE '0' END AS ISPASS, TTH.SCORE - PBTC.PASSROLE FENSHUCHA, FLAG_ISFREE FROM TEST_TESTPAPER_HISTORY TTH JOIN TEST_TESTPAPER_INFO TTI ON TTI.ID = TTH.TESTPAPER_ID JOIN PE_BZZ_STUDENT PBS ON PBS.FK_SSO_USER_ID = TTH.T_USER_ID AND PBS.FK_SSO_USER_ID = '"
				+ user.getId()
				+ "' JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON PBTO.FK_COURSE_ID = TTI.GROUP_ID JOIN PE_BZZ_TCH_COURSE PBTC ON PBTO.FK_COURSE_ID = PBTC.ID AND PBTC.ID = '"
				+ courseId
				+ "' AND PBTC.FLAG_IS_EXAM = '40288acf3ae01103013ae012940b0001' JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ON PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID AND PBS.ID = PBTSE.FK_STU_ID ORDER BY TTH.TEST_OPEN_DATE ASC ";
		mrsTestList = db.executeQuery(sql);
		while (null != mrsTestList && mrsTestList.next()) {
			Map<String, String> tMap = new HashMap<String, String>();
			String open_time = fixnull(mrsTestList.getString("open_time"));
			tMap.put("open_time", open_time);
			String submit_time = fixnull(mrsTestList.getString("submit_time"));
			tMap.put("submit_time", submit_time);
			String examtimes_allow = fixnull(mrsTestList.getString("examtimes_allow"));
			tMap.put("examtimes_allow", examtimes_allow);
			String passrole = fixnull(mrsTestList.getString("passrole"));
			tMap.put("passrole", passrole);
			String score_exam = fixnull(mrsTestList.getString("score_exam"));
			tMap.put("score_exam", score_exam);
			String ispass = fixnull(mrsTestList.getString("ispass"));
			tMap.put("ispass", ispass);
			String fenshucha = fixnull(mrsTestList.getString("fenshucha"));
			tMap.put("fenshucha", fenshucha);
			//tMap.put("last_time", "-");
			if (null != tMap && tMap.containsKey("ispass")) {
				String fsc = tMap.get("fenshucha");
				Double fscD = -1.0;
				try {
					fscD = Double.parseDouble(fsc);
				} catch (Exception e) {
					fscD = -1.0;
				}
				if ("1".equals(tMap.get("ispass"))) {
					if (fscD >= 0)
						tMap.put("ispass", "通过");
					else
						tMap.put("ispass", "未通过");
				} else {
					tMap.put("ispass", "未通过");
				}
			}
			testList.add(tMap);
		}
		for (int i = 0; i < testList.size(); i++) {
			Map<String, String> tMap2 = testList.get(i);
			Map<String, String> tMapLast = null;
			if (i != 0) {
				tMapLast = testList.get(i - 1);
				tMap2.put("last_time", tMapLast.get("submit_time"));
			} else {
				tMap2.put("last_time", "-");
			}
			testList2.add(tMap2);
		}
		db.close(mrsTestList);
		mrsTestList = null;//Lee 2014年9月14日22:29:04
	}
	db = null;
	UserCourseData userCourseData = null;//注释上边代码 用次代码做替换 防止代码报错
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title></title>
		<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
	</head>
	<body leftmargin="0" topmargin="0" background="/entity/function/images/bg2.gif">
		<form>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" class="text1">
						<br/>
						<img src="/entity/function/images/xb3.gif" width="17" height="15">
						<b>课程状态</b>
					</td>
				</tr>
				<tr>
					<td style="padding: 5px 20px 5px 20px;">
						<b>课件信息</b>
					</td>
				</tr>
				<tr>
					<td style="padding: 5px 20px 5px 20px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<th class="th">
									序号
								</th>
								<th class="th">
									课程编号
								</th>
								<th class="th">
									课程名称
								</th>
								<th class="th">
									课程性质
								</th>
								<th class="th">
									课程学时
								</th>
								<th class="th">
									首次学习时间
								</th>
								<th class="th">
									末次学习时间
								</th>
								<th class="th">
									学习完成时间
								</th>
								<th class="th">
									学习期限（天）
								</th>
								<th class="th">
									课程状态
								</th>
								<th class="th">
									考试结果
								</th>
								<th class="th">
									获得学时时间
								</th>
							</tr>
							<%
								if (null != courseList && courseList.size() > 0) {
									for (int i = 0; i < courseList.size(); i++) {
										Map<String, String> mEntity = courseList.get(i);
							%>
							<tr>
								<td class="td">
									<%=i + 1%>
								</td>
								<td class="td">
									<%=mEntity.get("code")%>
								</td>
								<td class="td">
									<%=mEntity.get("name")%>
								</td>
								<td class="td">
									<%=mEntity.get("ec1name")%>
								</td>
								<td class="td">
									<%=mEntity.get("time")%>
								</td>
								<td class="td">
									<%=mEntity.get("start_time")%>
								</td>
								<td class="td">
									<%=mEntity.get("end_time")%>
								</td>
								<td class="td">
									<%=mEntity.get("complete_date")%>
								</td>
								<td class="td">
									<%=mEntity.get("studydates")%>
								</td>
								<td class="td">
									<%
										if ("COMPLETED".equalsIgnoreCase(mEntity.get("learn_status"))) {
													out.println("完成");
												} else if ("UNCOMPLETE".equalsIgnoreCase(mEntity.get("learn_status"))) {
													out.println("未学习");
												} else {
													out.println("学习中");
												}
									%>
								</td>
								<td class="td">
									<%
										if ("PASSED".equalsIgnoreCase(mEntity.get("examstatus"))) {
													out.println("通过");
												} else if ("NOEXAMLEE".equalsIgnoreCase(mEntity.get("examstatus"))) {
													out.println("不考试");
												} else {
													out.println("未通过");
												}
									%>
								</td>
								<td class="td">
									<%=mEntity.get("completed_time")%>
								</td>
							</tr>
							<%
								}
								} else {
							%>
							<tr>
								<td class="td" colspan="13">
									---暂无记录---
								</td>
							</tr>
							<%
								}
							%>
						</table>
					</td>
				</tr>
				<tr>
					<td style="padding: 5px 20px 5px 20px;">
						<b>课程学习过程</b>
					</td>
				</tr>
				<tr>
					<td style="padding: 5px 20px 5px 20px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<th class="th">
									序号
								</th>
								<th class="th">
									开始学习时间
								</th>
								<th class="th">
									结束学习时间
								</th>
								<th class="th">
									本次累计学习时间（分钟）
								</th>
							</tr>
							<%
								if (null != trainingList && trainingList.size() > 0) {
									for (int i = 0; i < trainingList.size(); i++) {
										Map<String, String> tEntity = trainingList.get(i);
							%>

							<tr>
								<td class="td">
									<%=i + 1%>
								</td>
								<td class="td">
									<%=tEntity.get("start_time")%>
								</td>
								<td class="td">
									<%=tEntity.get("end_time")%>
								</td>
								<td class="td" title="0表示学习不足一分钟">
									<%=tEntity.get("total_time")%>
								</td>
							</tr>
							<%
								}
								} else {
							%>
							<tr>
								<td colspan="6" class="td">
									---暂无记录---
								</td>
							</tr>
							<%
								}
							%>
						</table>
					</td>
				</tr>
				<tr>
					<td style="padding: 5px 20px 5px 20px;">
						<b>满意度调查</b>
					</td>
				</tr>
				<tr>
					<td style="padding: 5px 20px 5px 20px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<th width="50%" class="th">
									序号
								</th>
								<th width="50%" class="th">
									最后提交日期
								</th>
							</tr>
							<%
								if (null != manYiList && manYiList.size() > 0) {
									for (int i = 0; i < manYiList.size(); i++) {
										Map<String, String> myEntity = manYiList.get(i);
							%>
							<tr>
								<td class="td">
									<%=i + 1%>
								</td>
								<td class="td">
									<%=myEntity.get("vote_date")%>
								</td>
							</tr>
							<%
								}
								} else {
							%>
							<tr>
								<td colspan="2" class="td">
									---暂无记录---
								</td>
							</tr>
							<%
								}
							%>
						</table>
					</td>
				</tr>
				<tr style="">
					<td style="padding: 5px 20px 5px 20px;">
						<b>课后测验</b>
					</td>
				</tr>
				<tr style="">
					<td style="padding: 5px 20px 5px 20px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<th class="th">
									序号
								</th>
								<th class="th">
									打开试卷时间
								</th>
								<th class="th">
									提交试卷时间
								</th>
								<th class="th">
									测验通过成绩
								</th>
								<th class="th">
									上次测验时间
								</th>
								<th class="th">
									考试成绩
								</th>
								<th class="th">
									考试结果
								</th>
							</tr>
							<%
								for (int i = 0; i < testList2.size(); i++) {
									Map<String, String> ttMap = testList2.get(i);
							%>
							<tr>
								<td class="td">
									<%=i + 1%>
								</td>
								<td class="td">
									<%=ttMap.get("open_time")%>
								</td>
								<td class="td">
									<%=ttMap.get("submit_time")%>
								</td>
								<td class="td">
									<%=ttMap.get("passrole")%>
								</td>
								<td class="td">
									<%=ttMap.get("last_time")%>
								</td>
								<td class="td">
									<%=ttMap.get("score_exam")%>
								</td>
								<td class="td">
									<%=ttMap.get("ispass")%>
								</td>
							</tr>
							<%
								}
								if (null == testList2 || testList2.size() == 0) {
							%>
							<tr>
								<td colspan="8" class="td">
									-暂无数据-
								</td>
							</tr>
							<%
								}
							%>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
