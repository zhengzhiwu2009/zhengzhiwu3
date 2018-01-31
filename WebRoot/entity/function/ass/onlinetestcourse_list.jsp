<%@page language="java" pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.whaty.platform.*"%>
<%@page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*,java.text.SimpleDateFormat"%>
<%@page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.onlinetest.*"%>
<%@page import="com.whaty.platform.test.TestManage"%>
<%@page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<%@include file="priv.jsp"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="com.whaty.platform.entity.bean.SsoUser"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%>
<%@page import="com.whaty.platform.entity.service.ass.PeBzzAssService"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>课后测验</title>
		<style type="text/css">
<!--
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

#wrap {
	word-break: break-all;
	width: 150px;
}
-->
</style>
		<link href="../css/css.css" rel="stylesheet" type="text/css">
		<script>
		
function showTS(o,testCourseId,title,pageInt){
	if("0" == title || 0 == title)
		title = "";
	if("1" == o || 1 == o){
		if(confirm("确认设为无效吗？")){
		window.location.href="onlinetestcourse_changestatus.jsp?testCourseId=" + testCourseId + "&title=" + title + "&pageInt=" + pageInt + "&status=1&note=0";
		}
	}else{
		if(confirm("确认设为有效吗？")){
		window.location.href="onlinetestcourse_changestatus.jsp?testCourseId=" + testCourseId + "&title=" + title + "&pageInt=" + pageInt + "&status=0&note=1";
		}
	}
}
function cfmdel(link)
{
	if(confirm("您确定要删除此课后测验吗？"))
		window.navigate(link);
}
function DetailInfo(testCourseId)
{
	window.open('onlinetestcourse_info.jsp?testCourseId='+testCourseId,'','width=630,height=550,scrollbars=yes');
}
</script>
	</head>
	<%!String fixnull(String str) {
		if (str == null || str.equals("") || str.equals("null"))
			str = "";
		return str;
	}%>
	<%!String fixnull1(String str) {
		if (str == null || str.equals("") || str.equals("null"))
			str = "暂无";
		return str;
	}%>
	<%
		PeBzzAssService peBzzAssService = new PeBzzAssService();
		String flag = request.getParameter("flag");
		dbpool db = new dbpool();
		String sql = "select to_char(b.start_time,'yyyy-mm-dd') as start_time,to_char(b.end_time,'yyyy-mm-dd') as end_time,b.id as batch_id from pe_bzz_student s inner join pe_bzz_batch b on s.fk_batch_id=b.id "
				+ " where s.fk_sso_user_id='" + us.getSsoUser().getId() + "' ";
		MyResultSet rs = db.executeQuery(sql);
		String bStartTime = null;
		String bEndTime = null;
		String bBatchId = "";
		if (rs.next()) {
			bStartTime = fixnull(rs.getString("start_time"));
			bEndTime = fixnull(rs.getString("end_time"));
			bBatchId = fixnull(rs.getString("batch_id"));
		}
		db.close(rs);
		//Lee start 2014年9月14日22:23:46
		rs = null;
		db = null;
		//Lee end
		if (bBatchId.equals("ff8080812253f04f0122540a58000004") || bBatchId.equals("52cce2fd2471ddc30124764980580131") || bBatchId.equals("ff8080812824ae6f012824b0a89e0008")) {
			bBatchId = "and fk_batch_id is null";
		} else {
			bBatchId = "and fk_batch_id='" + bBatchId + "'";
		}
		String title = fixnull(request.getParameter("title"));
		try {
			//InteractionFactory interactionFactory = InteractionFactory.getInstance();
			//InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
			//TestManage testManage = interactionManage.creatTestManage();
			//UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			int totalItems = 0;
			if ("teacher".equalsIgnoreCase(userType)) {
				totalItems = peBzzAssService.getOnlineTestCoursesNum(null, title, null, null, null, null, null, null, null, teachclass_id, null);
			} else {
				//		totalItems = testManage.getOnlineTestCoursesNum(null,title,null,null,null,"1",null,null,null,teachclass_id,null);
				//				totalItems = testManage.getOnlineTestCoursesNum(null, title, null, null, null, "1", null, null, null, teachclass_id, null, "student", null);
				totalItems = peBzzAssService.getOnlineTestCoursesNum(title, "1", teachclass_id, "student");
			}
			//----------分页开始---------------
			String spagesize = (String) session.getValue("pagesize");
			String spageInt = request.getParameter("pageInt");
			Page pageover = new Page();
			pageover.setTotalnum(totalItems);
			pageover.setPageSize(spagesize);
			pageover.setPageInt(spageInt);
			int pageNext = pageover.getPageNext();
			int pageLast = pageover.getPagePre();
			int maxPage = pageover.getMaxPage();
			int pageInt = pageover.getPageInt();
			int pagesize = pageover.getPageSize();
			String link = "&title=" + title;
			//----------分页结束---------------
			List testCourseList = null;
			if ("teacher".equalsIgnoreCase(userType))
				testCourseList = peBzzAssService.getOnlineTestCourses(pageover, null, title, null, null, null, null, null, null, null, teachclass_id, null);
			else
				testCourseList = peBzzAssService.getOnlineTestCourses(pageover, title, "1", teachclass_id);
			//testCourseList = testManage.getOnlineTestCourses(pageover, null, title, null, null, null, "1", null, null, null, teachclass_id, null, null);
			int aaa = testCourseList.size();//找到的是对的。
	%>
	<body leftmargin="0" topmargin="0" background="../images/bg2.gif">
		<form>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26">
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td class="text1" colspan="2">
									<img src="/entity/function/images/xb3.gif" width="17" height="15">
									<strong>课后测验</strong>
								</td>
								<%
									if ("teacher".equalsIgnoreCase(userType) && (us.getRoleId().equals("3") || us.getRoleId().equals("R004")) && testCourseList.size() < 1) {
								%>
								<td align="right" colspan="4">
									<a href="onlinetestcourse_add.jsp" class="tj">[添加新测验]</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<%
									}
								%>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="padding: 5px 20px 5px 20px;">
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
							<%
								if (!"teacher".equalsIgnoreCase(userType)) {
							%>
							<tr>
								<th class="th">
									测验名称
								</th>
								<th class="th">
									测验通过成绩
								</th>
								<th class="th">
									考试结果
								</th>
								<!-- 添加考试成绩列 -->
								<th class="th">
									考试成绩
								</th>								
								<th class="th">
									操作
								</th>
							</tr>
							<%
								if (null != testCourseList && testCourseList.size() > 0) {
											int trNo = 1; //用来表示每行
											Iterator it = testCourseList.iterator();
											while (it.hasNext()) {
												OnlineTestCourse testCourse = (OnlineTestCourse) it.next();
												String examtimesAllow = testCourse.getExamtimesAllow();// 允许测验次数
												String passrole = testCourse.getPassrole();// 测验通过成绩
												String examTimes = testCourse.getExamTimes();// 已测验次数
												String ispass = ("1".equals(testCourse.getIspass()) ? "已通过" : "未通过");// 考试结果
												String testCourseId = testCourse.getId();
												String testCourseTitle = fixnull(testCourse.getTitle());
												String startDate = fixnull(testCourse.getStartDate());
												String endDate = fixnull(testCourse.getEndDate());
												String creatUser = fixnull(testCourse.getCreatUser());
												String creatDate = fixnull(testCourse.getCreatDate());
												String isAutoCheck = testCourse.getIsAutoCheck();
												String isHiddenAnswer = testCourse.getIsHiddenAnswer();
												String batch_id = testCourse.getBatch_id();
												String batch_name = fixnull(testCourse.getBatch_name());
												String status = testCourse.getStatus();
												SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
												String s = sdf.format(new Date());
												Date newDate = sdf.parse(s);
												Date startdate = new Date();
												if (!startDate.equals(""))
													startdate = sdf.parse(startDate);
												Date enddate = new Date();
												if (!endDate.equals(""))
													enddate = sdf.parse(endDate);
												long newDateTime = newDate.getTime();
												long startDateTime = startdate.getTime();
												long endDateTime = enddate.getTime();
												if ((newDateTime < startDateTime || newDateTime > endDateTime) && "student".equalsIgnoreCase(userType))
													continue;
												String status_link = "";
												if (status.equals("1"))
												//URLEncoder.encode('无',"utf-8")
												{
													status_link = "<a href='onlinetestcourse_changestatus.jsp?testCourseId=" + testCourseId + "&title=" + title + "&pageInt=" + pageInt + "&status=1&note=0'>有效</a>";
												} else {
													status_link = "<a href='onlinetestcourse_changestatus.jsp?testCourseId=" + testCourseId + "&title=" + title + "&pageInt=" + pageInt + "&status=0&note=1'>无效</a>";
												}
												if (("student".equalsIgnoreCase(userType) && status.equals("1")) || ("teacher".equalsIgnoreCase(userType))) {
							%>
							<tr>
								<td class="td">
									<%=testCourseTitle%>
								</td>
								<td class="td">
									<%=passrole%>
								</td>
								<td class="td">
									<%=ispass%>
								</td>
								<!-- 添加考试成绩列 -->
								<%String openCourseId = fixnull(request.getParameter("opencourseId"));
								Map scoreM = peBzzAssService.getOnlineTestScore(openCourseId, us.getSsoUser().getId());
								double score = scoreM.containsKey("score") ? (Double) scoreM.get("score") : 0; %>
								<td class="td">
									<%=score%>
								</td>	
								<!-- end -->							
								<td class="td">
									<%
										if ("teacher".equalsIgnoreCase(userType) && (us.getRoleId().equals("3") || us.getRoleId().equals("R004"))) {
									%>
									<a href="onlinetestcourse_session.jsp?testCourseId=<%=testCourseId%>&isAutoCheck=<%=isAutoCheck%>&isHiddenAnswer=<%=isHiddenAnswer%>&pageInt=<%=pageover.getPageInt()%>">进入</a> &nbsp;&nbsp;&nbsp;
									<a href="onlinetestcourse_edit.jsp?title=<%=title%>&pageInt=<%=pageInt%>&testCourseId=<%=testCourseId%>">编辑</a> &nbsp;&nbsp;&nbsp;
									<a href="javascript:cfmdel('onlinetestcourse_delexe.jsp?title=<%=title%>&pageInt=<%=pageInt%>&testCourseId=<%=testCourseId%>')" class="button">删除</a>
									<%
										} else {

																long score1 = Math.round(score);

																int times = Integer.parseInt(fixnull(scoreM.containsKey("times") ? scoreM.get("times").toString() : "0"));
																int maxTimes = Integer.parseInt(fixnull(scoreM.containsKey("maxTimes") ? scoreM.get("maxTimes").toString() : "0"));
																double passScore = 80.0;
																try {
																	passScore = Double.parseDouble(openCourse.getBzzCourse().getPassRole());
																} catch (Exception e) {
																}
									%>
									<!-- <span><%=times > 0 ? "得分：" + score1 + "&nbsp;已考：" + times + "次&nbsp;最多：" + maxTimes + "次<br>" : ""%></span> -->
									<%
										if (score >= passScore) {
																	//课后测验通过
																	out.print("-");//<font color=green>已经通过课后测验</font>
																} else {
																	if (!"false".equalsIgnoreCase(flag)) {
																		//正常，可进行课后测验
									%>
									<a href="javascript:void(0);" onclick="jinruceyan()" style="color: red;"><b>考试</b> </a>
									<script type="text/javascript">
															function jinruceyan(){
																if(confirm('确定要进入课后测验吗')){
																<%
																	//Lee start 记录开始答题时间
																	SsoUser ssoUser = us.getSsoUser();
																	String sysDateSql = "SELECT TO_CHAR(SYSDATE,'yyyy-MM-dd hh24:mi:ss') TOD FROM DUAL";
																	dbpool db4 = new dbpool();
																	MyResultSet mrs4 = db4.execuQuery(sysDateSql);
																	String testOpenDate = "SYSDATE";
																	while(mrs4.next()){
																		testOpenDate = mrs4.getString("TOD");
																	}
																	db4.close(mrs4);
																	db4 = null;
																	
																	ServletActionContext.getRequest().getSession().setAttribute(ssoUser.getId() + "startTime", testOpenDate);
																	ServletActionContext.getRequest().getSession().setAttribute(ssoUser.getId() + "startTimes", 0);
																	//Lee end
																%>
																	window.open('onlinetestcourse_session.jsp?testCourseId=<%=testCourseId%>&isAutoCheck=<%=isAutoCheck%>&isHiddenAnswer=<%=isHiddenAnswer%>&pageInt=<%=pageover.getPageInt()%>');
																}else{
																	return false;
																}
															}
															</script>
									<%
										} else {
									%><font size="2" color="red">完成学习并填写完毕满意度调查方可考试</font>
									<%
										}
																}
															}
									%>
								</td>
							</tr>
							<%
								}
												trNo++;
											}
										} else {
							%>
							<tr>
								<td class="td" colspan="7">
									-暂无数据-
								</td>
							</tr>
							<%
								}
									} else if ("teacher".equalsIgnoreCase(userType) || us.getRoleId().equals("3") || us.getRoleId().equals("R004")) {
							%>
							<tr>
								<th class="th">
									名称
								</th>
								<th class="th">
									状态
								</th>
								<th class="th">
									操作
								</th>
							</tr>
							<%
								int trNo = 1; //用来表示每行
										Iterator it = testCourseList.iterator();
										while (it.hasNext()) {
											OnlineTestCourse testCourse = (OnlineTestCourse) it.next();
											String testCourseId = testCourse.getId();
											String testCourseTitle = fixnull(testCourse.getTitle());
											String startDate = fixnull(testCourse.getStartDate());
											String endDate = fixnull(testCourse.getEndDate());
											String creatUser = fixnull(testCourse.getCreatUser());
											String creatDate = fixnull(testCourse.getCreatDate());
											String isAutoCheck = testCourse.getIsAutoCheck();
											String isHiddenAnswer = testCourse.getIsHiddenAnswer();
											String batch_id = testCourse.getBatch_id();
											String batch_name = fixnull(testCourse.getBatch_name());
											String status = testCourse.getStatus();
											SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
											String s = sdf.format(new Date());
											Date newDate = sdf.parse(s);
											Date startdate = new Date();
											if (!startDate.equals(""))
												startdate = sdf.parse(startDate);
											Date enddate = new Date();
											if (!endDate.equals(""))
												enddate = sdf.parse(endDate);
											long newDateTime = newDate.getTime();
											long startDateTime = startdate.getTime();
											long endDateTime = enddate.getTime();
											if ((newDateTime < startDateTime || newDateTime > endDateTime) && "student".equalsIgnoreCase(userType))
												continue;
											String status_link = "";
											title = (null == title || "".equals(title)) ? "0" : title;
											if (status.equals("1")) {
												//status_link = "<a href='onlinetestcourse_changestatus.jsp?testCourseId=" + testCourseId + "&title=" + title + "&pageInt=" + pageInt + "&status=1&note=0'>有效</a>";
												status_link = "<a href='javascript:showTS(1," + testCourseId + "," + title + "," + pageInt + ")'>有效</a>";
											} else {
												//status_link = "<a href='onlinetestcourse_changestatus.jsp?testCourseId=" + testCourseId + "&title=" + title + "&pageInt=" + pageInt + "&status=0&note=1'>无效</a>";
												status_link = "<a href='javascript:showTS(0," + testCourseId + "," + title + "," + pageInt + ")'>无效</a>";
											}
											if (("student".equalsIgnoreCase(userType) && status.equals("1")) || ("teacher".equalsIgnoreCase(userType))) {
							%>
							<tr>
								<td class="td">
									<%=testCourseTitle%>
								</td>
								<td class="td">
									<%=status_link%>
								</td>
								<td class="td">
									<%
										if ("teacher".equalsIgnoreCase(userType) || us.getRoleId().equals("3") || us.getRoleId().equals("R004")) {
									%>
									<a href="onlinetestcourse_session.jsp?testCourseId=<%=testCourseId%>&isAutoCheck=<%=isAutoCheck%>&isHiddenAnswer=<%=isHiddenAnswer%>&pageInt=<%=pageover.getPageInt()%>">进入</a> &nbsp;&nbsp;&nbsp;
									<%
										Boolean isTrue = peBzzAssService.invalideJudge(testCourseId);
									%>
									<a href="onlinetestcourse_edit.jsp?title=<%=title%>&pageInt=<%=pageInt%>&testCourseId=<%=testCourseId%>">编辑</a> &nbsp;&nbsp;&nbsp;
									<a href="javascript:cfmdel('onlinetestcourse_delexe.jsp?title=<%=title%>&pageInt=<%=pageInt%>&testCourseId=<%=testCourseId%>')" class="button">删除</a>
									<%
										} else {
															String openCourseId = fixnull(request.getParameter("opencourseId"));
															Map scoreM = peBzzAssService.getOnlineTestScore(openCourseId, us.getSsoUser().getId());
															double score = (Double) scoreM.get("score");
															long score1 = Math.round(score);

															int times = Integer.parseInt(fixnull(scoreM.get("times").toString()));
															int maxTimes = Integer.parseInt(fixnull(scoreM.get("maxTimes").toString()));
															double passScore = 80.0;

															try {
																passScore = Double.parseDouble(openCourse.getBzzCourse().getPassRole());
															} catch (Exception e) {
															}
									%>
									<span><%=times > 0 ? "得分：" + score1 + "&nbsp;已考：" + times + "次&nbsp;最多：" + maxTimes + "次<br>" : ""%></span>
									<%
										if (score >= passScore) {
																//课后测验通过
																out.print("<font color=green>已经通过课后测验</font>");
															} else {
																//正常，可进行课后测验
									%>
									<a href="javascript:void(0);" onclick="jinruceyan()"> <%=score < passScore ? times > 0 ? "未通过，" : "" : ""%>进入课后测验</a>
									<script type="text/javascript">
															function jinruceyan(){
																if(confirm('确定要进入课后测验吗')){
																<%
																	//Lee start 记录开始答题时间
																	SsoUser ssoUser = us.getSsoUser();
																	ServletActionContext.getRequest().getSession().setAttribute(ssoUser.getId() + "startTime", new Date());
																	ServletActionContext.getRequest().getSession().setAttribute(ssoUser.getId()+"startTimes",0);
																	//Lee end
																%>
																	window.open('onlinetestcourse_session.jsp?testCourseId=<%=testCourseId%>&isAutoCheck=<%=isAutoCheck%>&isHiddenAnswer=<%=isHiddenAnswer%>&pageInt=<%=pageover.getPageInt()%>');
																}else{
																	return false;
																}
															}
															</script>
									<%
										}
														}
									%>
								</td>
							</tr>
							<%
								}
										}
									} else {
							%>
							<tr>
								<th class="th">
									名&nbsp;&nbsp;&nbsp;&nbsp;称
								</th>
								<th class="th">
									时间
								</th>
								<th class="th">
									发布时间
								</th>
								<%
									if ("teacher".equalsIgnoreCase(userType) || us.getRoleId().equals("3") || us.getRoleId().equals("R004")) {
								%>
								<th class="th">
									状态
								</th>
								<%
									}
								%>
								<th class="th">
									操作
								</th>
							</tr>

							<%
								int trNo = 1; //用来表示每行
										Iterator it = testCourseList.iterator();
										while (it.hasNext()) {
											OnlineTestCourse testCourse = (OnlineTestCourse) it.next();
											String testCourseId = testCourse.getId();
											String testCourseTitle = fixnull(testCourse.getTitle());
											String startDate = fixnull(testCourse.getStartDate());
											String endDate = fixnull(testCourse.getEndDate());
											String creatUser = fixnull(testCourse.getCreatUser());
											String creatDate = fixnull(testCourse.getCreatDate());
											String isAutoCheck = testCourse.getIsAutoCheck();
											String isHiddenAnswer = testCourse.getIsHiddenAnswer();
											String batch_id = testCourse.getBatch_id();
											String batch_name = fixnull(testCourse.getBatch_name());
											String status = testCourse.getStatus();
											SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
											String s = sdf.format(new Date());
											Date newDate = sdf.parse(s);
											Date startdate = new Date();
											if (!startDate.equals(""))
												startdate = sdf.parse(startDate);
											Date enddate = new Date();
											if (!endDate.equals(""))
												enddate = sdf.parse(endDate);
											long newDateTime = newDate.getTime();
											long startDateTime = startdate.getTime();
											long endDateTime = enddate.getTime();
											if ((newDateTime < startDateTime || newDateTime > endDateTime) && "student".equalsIgnoreCase(userType))
												continue;
											String status_link = "";
											title = (null == title || "".equals(title)) ? "0" : title;
											if (status.equals("1")) {
												//status_link = "<a href='onlinetestcourse_changestatus.jsp?testCourseId=" + testCourseId + "&title=" + title + "&pageInt=" + pageInt + "&status=1&note=0'>有效</a>";
												status_link = "<a href='javascript:showTS(1," + testCourseId + "," + title + "," + pageInt + ")'>有效</a>";
											} else {
												//status_link = "<a href='onlinetestcourse_changestatus.jsp?testCourseId=" + testCourseId + "&title=" + title + "&pageInt=" + pageInt + "&status=0&note=1'>无效</a>";
												status_link = "<a href='javascript:showTS(0," + testCourseId + "," + title + "," + pageInt + ")'>无效</a>";
											}
											if (("student".equalsIgnoreCase(userType) && status.equals("1")) || ("teacher".equalsIgnoreCase(userType))) {
							%>
							<tr>
								<td class="td">
									<%=testCourseTitle%>
								</td>
								<td class="td">
									<%
										if ("teacher".equalsIgnoreCase(userType) && (us.getRoleId().equals("3") || us.getRoleId().equals("R004"))) {
									%>
									<%=startDate%>至<%=endDate%>
									<%
										} else if (bStartTime != null) {
									%>
									<%=bStartTime%>至<%=bEndTime%>
									<%
										} else {
									%>
									<%=startDate%>至<%=endDate%>
									<%
										}
									%>
								</td>
								<td class="td">
									<%=creatDate%>
								</td>
								<%
									if ("teacher".equalsIgnoreCase(userType) && (us.getRoleId().equals("3") || us.getRoleId().equals("R004"))) {
								%>
								<td class="td">
									<%=status_link%>
								</td>
								<%
									} else if ("teacher".equalsIgnoreCase(userType) && !us.getRoleId().equals("3") && !us.getRoleId().equals("R004")) {
														if (status.equals("1")) {
								%>
								<td class="td">
									<%
										out.print("有效");
															} 
										}else {
									%>
								
								<td class="td">
									<%
										out.print("无效");
															}
														}
									%>
								
								<td class="td">
									<%
										if ("teacher".equalsIgnoreCase(userType) && (us.getRoleId().equals("3") || us.getRoleId().equals("R004"))) {
									%>
									<a href="onlinetestcourse_session.jsp?testCourseId=<%=testCourseId%>&isAutoCheck=<%=isAutoCheck%>&isHiddenAnswer=<%=isHiddenAnswer%>&pageInt=<%=pageover.getPageInt()%>">进入</a> &nbsp;&nbsp;&nbsp;
									<%
										Boolean isTrue = peBzzAssService.invalideJudge(testCourseId);
									%>
									<a href="onlinetestcourse_edit.jsp?title=<%=title%>&pageInt=<%=pageInt%>&testCourseId=<%=testCourseId%>">编辑</a> &nbsp;&nbsp;&nbsp;
									<a href="javascript:cfmdel('onlinetestcourse_delexe.jsp?title=<%=title%>&pageInt=<%=pageInt%>&testCourseId=<%=testCourseId%>')" class="button">删除</a>
									<%
										} else {
															String openCourseId = fixnull(request.getParameter("opencourseId"));
															Map scoreM = peBzzAssService.getOnlineTestScore(openCourseId, us.getSsoUser().getId());
															double score = (Double) scoreM.get("score");
															long score1 = Math.round(score);

															int times = Integer.parseInt(fixnull(scoreM.get("times").toString()));
															int maxTimes = Integer.parseInt(fixnull(scoreM.get("maxTimes").toString()));
															double passScore = 80.0;

															try {
																passScore = Double.parseDouble(openCourse.getBzzCourse().getPassRole());
															} catch (Exception e) {
															}
									%>
									<span><%=times > 0 ? "得分：" + score1 + "&nbsp;已考：" + times + "次&nbsp;最多：" + maxTimes + "次<br>" : ""%></span>
									<%
										if (score >= passScore) {
																//课后测验通过
																out.print("<font color=green>已经通过课后测验</font>");
															} else {
																//正常，可进行课后测验
									%>
									<a href="javascript:void(0);" onclick="jinruceyan()"> <%=score < passScore ? times > 0 ? "未通过，" : "" : ""%>进入课后测验</a>
									<script type="text/javascript">
															function jinruceyan(){
																if(confirm('确定要进入课后测验吗')){
																<%
																	//Lee start 记录开始答题时间
																	SsoUser ssoUser = us.getSsoUser();
																	ServletActionContext.getRequest().getSession().setAttribute(ssoUser.getId() + "startTime", new Date());
																	ServletActionContext.getRequest().getSession().setAttribute(ssoUser.getId()+"startTimes",0);
																	//Lee end
																%>
																	window.open('onlinetestcourse_session.jsp?testCourseId=<%=testCourseId%>&isAutoCheck=<%=isAutoCheck%>&isHiddenAnswer=<%=isHiddenAnswer%>&pageInt=<%=pageover.getPageInt()%>');
																}else{
																	return false;
																}
															}
															</script>
									<%
										}
														}
									%>
								</td>
							</tr>
							<%
								}
									}
							%>
						</table>
					</td>
				</tr>
				<tr>
					<td align="center">
						<table width="930" border="0" cellspacing="0" cellpadding="0">
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<%
	} catch (Exception e) {
		e.printStackTrace();
		out.print(e.getMessage());
		return;
	}
%>
