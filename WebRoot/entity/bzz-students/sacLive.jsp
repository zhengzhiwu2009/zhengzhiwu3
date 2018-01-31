<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool" />
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,com.whaty.platform.training.*"%>
<%@page import="com.whaty.platform.training.basic.*,com.whaty.platform.standard.scorm.operation.*,com.whaty.platform.database.oracle.standard.standard.scorm.operation.*"%>
<%@page import="com.whaty.platform.database.oracle.standard.training.user.OracleTrainingStudentPriv"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant,com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.training.user.*"%>
<%@page import="com.whaty.platform.database.oracle.MyResultSet"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
	</head>
	<body>
		<%
			UserSession usersession = (UserSession) session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			if (usersession.getLoginId() == null || "".equals(usersession.getLoginId())) {
		%><script type="text/javascript">
						alert("获取学员信息失败，请重新登录再试！");
						window.close();
						</script>
		<%
			}
			Object url = ServletActionContext.getRequest().getAttribute("liveStudy");
			if (url == null || "".equals(url)) {
		%><script type="text/javascript">
						alert("获取在线直播失败，请重试！");
						window.close();
						</script>
		<%
			}
		%>
		<iframe border="0" scrolling="auto" height="768px" width="1024px" src="<%=url%>"></iframe>
	</body>
</html>