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
<%@page import="java.text.SimpleDateFormat" %>
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
	MyUtil.doLog("监管机构内训课程，进入学习，学习课程", usersession.getId() + "-" + usersession.getUserName(), "课程学习", "学员端", "开课Id=" + opencourseId + "", request.getRemoteAddr(), request.getRequestURI());
	//记录开始学习时间
	Object tcshStartTime = session.getAttribute(usersession.getSsoUser().getId() + "starttime");
	if (null != tcshStartTime && !"".equals(tcshStartTime))
		session.removeAttribute(usersession.getSsoUser().getId() + "starttime");
	dbpool db2 = new dbpool();
	String sysDateSql = "SELECT TO_CHAR(SYSDATE,'yyyy-MM-dd hh24:mi:ss') start_time FROM DUAL";
	MyResultSet date_rs = db2.execuQuery(sysDateSql);
	String tcsStartTime =  "";
	if(date_rs.next()) {
		tcsStartTime = date_rs.getString("start_time");
	}
	
	db2.close(date_rs);
	date_rs = null;
	db2 = null;
	
	session.setAttribute(session.getAttribute("userId") + "starttime", tcsStartTime);
	String datesql = "";
	dbpool dbdate = new dbpool();
	datesql = "update pr_bzz_tch_stu_elective set start_time = to_date('" + tcsStartTime + "','yyyy-mm-dd hh24:mi:ss' where fk_tch_opencourse_id='" + opencourseId + "' and fk_stu_id=(select id from pe_bzz_student pbs where pbs.fk_sso_user_id='"
			+ session.getAttribute("userId") + "') and start_time is null";
	dbdate.executeUpdate(datesql);
	
	String tcs_sql = "select tcs.learn_status from training_course_student tcs where tcs.student_id='" + usersession.getId() + "'"
			+ " and tcs.course_id='" + opencourseId + "'";
	MyResultSet mrs = dbdate.execuQuery(tcs_sql);
	String learn_status = "";
	if(mrs.next()) {
		learn_status = mrs.getString("learn_status");
	}
	dbdate.close(mrs);
	mrs = null;
	if("UNCOMPLETE".equals(learn_status)) {
		String update_learn_status = "";
		update_learn_status = "update training_course_student set learn_status='INCOMPLETE', get_date = to_date('" + tcsStartTime + "','yyyy-mm-dd hh24:mi:ss') where course_id='" + opencourseId + "' and student_id = '" + usersession.getId() + "'";
		dbdate.executeUpdate(update_learn_status);
	}
	
	
	
	//记录学习记录HIS
	//String save_tcs_his_sql = " INSERT INTO TRAINING_COURSE_STUDENT_HIS (ID, FK_STU_ID, COURSE_ID, START_TIME, FK_ORDER_ID) " + " SELECT SEQ_TCS_HIS.NEXTVAL,B.ID,'" + opencourseId
			//+ "',SYSDATE,A.FK_ORDER_ID FROM PR_BZZ_TCH_USER_ELECTIVE A INNER JOIN SSO_USER B " + "ON A.FK_USER_ID = B.ID AND A.FK_TCH_OPENCOURSE_ID = '" + opencourseId
			//+ "' AND B.ID = '" + session.getAttribute("userId") + "'";
	String tcs_his_id_sql = "SELECT MAX (TO_NUMBER (A.ID)) ID FROM TRAINING_COURSE_STUDENT_HIS A "
			+ " INNER JOIN PE_BZZ_STUDENT PBS ON A.FK_STU_ID = PBS.FK_SSO_USER_ID " 
			+ " INNER JOIN PR_BZZ_TCH_STU_ELECTIVE B ON PBS.ID = B.FK_STU_ID AND A.COURSE_ID = B.FK_TCH_OPENCOURSE_ID WHERE PBS.FK_SSO_USER_ID = '"
			+ session.getAttribute("userId") + "' AND B.FK_TCH_OPENCOURSE_ID = '" + opencourseId + "'";
	//if (dbdate.executeUpdate(save_tcs_his_sql) < 1) {
		//System.out.println("[ERROR]HIS_：error- " + save_tcs_his_sql);
	//} else {
		//String tcs_his_id = "";
		//System.out.println("_____________学习记录HIS成功： userId=" + session.getAttribute("userId") + "opencourseId=" + opencourseId);
		//MyResultSet mrs_his_id = dbdate.executeQuery(tcs_his_id_sql);
		//while (null != mrs_his_id && mrs_his_id.next()) {
			//tcs_his_id = mrs_his_id.getString(1);
		//}
		//Lee start 2014年9月14日22:05:58
		//dbdate.close(mrs_his_id);
		//mrs_his_id = null;
		//Lee end
		//添加null判断
		//if (tcs_his_id == null || "".equals(tcs_his_id) || tcs_his_id.length() <= 0) {
			//System.out.println("[ERROR]HIS_ID：error-" + tcs_his_id);
		//} else {
				//request.getSession().setAttribute(session.getAttribute("userId") + opencourseId + "tcshisid", tcs_his_id);
		//}
	//}
	String completeSql = "SELECT 1 FROM TRAINING_COURSE_STUDENT WHERE STUDENT_ID = '" + session.getAttribute("userId") + "' AND COURSE_ID = '" + opencourseId + "' AND LEARN_STATUS = 'COMPLETED' AND COMPLETE_DATE is not null";
	//String tcsStartTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	//session.setAttribute(usersession.getSsoUser().getId() + "starttime", tcsStartTime);
	/* Lee start 学习记录明细 2014年12月08日 */
	int count = dbdate.countselect(completeSql);
	if(count <= 0) {
		StringBuffer sbTcsHis = new StringBuffer();
		sbTcsHis.append("INSERT INTO TRAINING_COURSE_STUDENT_HIS ");
		sbTcsHis.append("  (ID, FK_STU_ID, COURSE_ID, START_TIME, FK_ORDER_ID) ");
		sbTcsHis.append("  SELECT SEQ_TCS_HIS.NEXTVAL, ");
		sbTcsHis.append("         '" + usersession.getSsoUser().getId() + "', ");
		sbTcsHis.append("         '" + opencourseId + "', ");
		sbTcsHis.append("         TO_DATE('" + tcsStartTime + "', 'yyyy-MM-dd hh24:mi:ss'), ");
		sbTcsHis.append("         NULL ");
		sbTcsHis.append("    FROM DUAL ");
		dbdate.executeUpdate(sbTcsHis.toString());
	}
	/* Lee end */
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
			db = null;
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

