<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool" />
<%@page import="com.whaty.platform.database.oracle.MyResultSet"%>
<%
	/*---------------------------------------------
	 Function Description:	
	 Editing Time:	
	 Editor: chenjian
	 Target File:	
	 	 
	 Revise Log
	 Revise Time:  Revise Content:   Reviser:
	 -----------------------------------------------*/
%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,com.whaty.platform.training.*"%>
<%@page import="com.whaty.platform.entity.util.*"%>
<%@page import="com.whaty.platform.training.basic.*,com.whaty.platform.standard.scorm.operation.*,com.whaty.platform.database.oracle.standard.standard.scorm.operation.*"%>
<%@page import="com.whaty.platform.training.*,com.whaty.platform.database.oracle.standard.training.user.OracleTrainingStudentPriv"%>
<%@page import="com.whaty.platform.training.basic.*,com.whaty.platform.sso.web.action.SsoConstant,com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.training.user.*"%>
<%
	String path1 = request.getContextPath();
	String basePath1 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path1 + "/";
	String courseId = request.getParameter("course_id"); //课程Id;
	if(null == courseId || "".equals(courseId))
		courseId = request.getParameter("courseId"); //课程Id;
	//String coursewareId=request.getParameter("coursewareId"); //课件code;
	String opencourseId = request.getParameter("opencourseId"); //开课Id
	UserSession usersession = (UserSession) session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	if (usersession == null) {
%>
<script type="text/javascript">
	alert("请您登录后再学习课件！");
	//window.location="<%=basePath1%>";
	window.close();
</script>
<%
	return;
	}

	Date now = new Date();
	request.getSession().setAttribute("now", now);

	TrainingStudentPriv includePriv = new OracleTrainingStudentPriv();
	includePriv.setStudentId(usersession.getId());
	session.setAttribute("traininguser_priv", includePriv);
	TrainingFactory factory = TrainingFactory.getInstance();
	TrainingStudentOperationManage stuManage = factory.creatTrainingUserOperationManage(includePriv);
	TrainingCourseware courseware = stuManage.getCourseware(courseId);//stuManage.getScorm12Courseware(courseId);
	MyUtil.doLog("正在学习课程，进入学习，学习课程", usersession.getId() + "-" + usersession.getUserName(), "课程学习", "学员端", "开课Id=" + opencourseId + "", request.getRemoteAddr(), request.getRequestURI());
	//记录开始学习时间
	String datesql = "";
	dbpool dbdate = new dbpool();
	datesql = "update pr_bzz_tch_stu_elective set start_time=sysdate where fk_tch_opencourse_id='" + opencourseId
			+ "' and fk_stu_id=(select id from pe_bzz_student ps where ps.fk_sso_user_id='" + session.getAttribute("userId") + "') and start_time is null";
	dbdate.executeUpdate(datesql);

	dbdate = null;//Lee 2014年9月14日22:06:23
	String t_sql = "";
	dbpool db1 = new dbpool();
	t_sql = "select c.id from pe_bzz_tch_course t,pe_bzz_tch_courseware c,scorm_course_info sc where t.id = c.fk_course_id  and t.id='" + courseId + "' and sc.id=c.code ";
	if (courseware != null && db1.countselect(t_sql) > 0) {
		session.setAttribute("COURSEID", courseware.getId()); //课件ID
		session.setAttribute("openId", opencourseId); //开课id
		String coursewareType = courseware.getCoursewareType();
		if (coursewareType.equals(TrainingCoursewareType.SCORM12)) {
			String coursewareURL = "";
			String coursewareURL1 = "";
			coursewareURL = "./prepareJump.jsp?navigate=" + courseware.getNavigate() + "&course_id=" + courseware.getId() + "&user_id=" + usersession.getId() + "&opencourseId=" + opencourseId
					+ "&trueCourseId=" + courseId;
			coursewareURL1 = "./prepareJump.jsp?navigate=" + courseware.getNavigate() + "&course_id=" + courseware.getId() + "&user_id=" + usersession.getId() + "&opencourseId="
					+ opencourseId + "&trueCourseId=" + courseId + "&start=begin";
			ScormStudentPriv userPriv = new OracleScormStudentPriv();
			userPriv.setStudentId(usersession.getId());
			ScormStudentManage man = ScormFactory.getInstance().creatScormStudentManage(userPriv);

			man.addAttemptNum(courseware.getId());
			dbpool db = new dbpool();
			String sql = "select tcs.id from training_course_student tcs, pe_bzz_tch_courseware pbtc,pr_bzz_tch_opencourse pbto,scorm_course_info sci where sci.navigate<>'courseware_nav' and sci.id=pbtc.code and pbtc.code = '"
					+ courseware.getId() + "' and pbtc.fk_course_id = pbto.fk_course_id and pbto.id= tcs.course_id and tcs.student_id = '" + usersession.getId() + "' and tcs.percent <> '0'";
			String sql1 = "select tcs.id from training_course_student tcs, pe_bzz_tch_courseware pbtc,pr_bzz_tch_opencourse pbto,scorm_course_info sci where sci.navigate='courseware_nav' and sci.id=pbtc.code and pbtc.code = '"
					+ courseware.getId() + "' and pbtc.fk_course_id = pbto.fk_course_id and pbto.id= tcs.course_id and tcs.student_id = '" + usersession.getId() + "' and tcs.percent <> '0'";
			if (db.countselect(sql) > 0) {
%>
<script type="text/javascript">
	/*if(confirm("是否接着上次的学习进度继续学习，点击“确定”将继续上次进度，点击“取消”将重头开始学习？"))
	{
		window.location="<%=coursewareURL%>";
	}
	else
	{
		window.location="<%=coursewareURL1%>";
	}**/
	window.location="<%=coursewareURL1%>";
</script>
<%
	} else if (db.countselect(sql1) > 0) {
				response.sendRedirect(coursewareURL1);
			} else {
				response.sendRedirect(coursewareURL);
			}
			//response.sendRedirect(coursewareURL);
		}
	} else {
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>课件不存在</title>
	</head>

	<body>
		<table width="100%" height="100%" border="1">
			<tr height="100%">
				<td width="100%" valign="middle" align="center">
					<table width="200" border="0">
						<tr>
							<td width="100%" align="center">
								该课程还没有课件!
							</td>
						</tr>

						<tr>
							<td width="100%" align="center">
								<a href="javascript:window.close()">关闭</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
<%
	}
	db1 = null;//Lee 2014年9月14日22:07:16
%>

