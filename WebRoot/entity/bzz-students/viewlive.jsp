<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.apache.struts2.ServletActionContext"%>
<%@ page import="com.whaty.platform.database.oracle.*" %>
<%@ page import="com.whaty.platform.sso.web.servlet.UserSession" %>
<%@ page import="com.whaty.platform.entity.bean.SsoUser" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'view.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<% 
		dbpool db = new dbpool();
		Object cid = ServletActionContext.getRequest().getAttribute("cid");
		UserSession userSession = (UserSession) session.getAttribute("user_session");
		SsoUser ssoUser = userSession.getSsoUser();
		String sql = "SELECT 1 FROM PE_BZZ_TCH_COURSE PBTC INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON PBTO.FK_COURSE_ID = PBTC.ID INNER JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ON PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID INNER JOIN PE_BZZ_STUDENT PBS ON PBTSE.FK_STU_ID = PBS.ID AND PBS.FK_SSO_USER_ID = '" + ssoUser.getId() + "' AND PBTC.CODE = '" + cid + "'";
		System.out.println(sql);
		int res = 0;
		try{
			res = db.executeUpdate(sql);
		}catch(Exception e){
			System.out.println("学员信息验证失败：" + sql);
		}
	    String url = "";
		if(res > 0){
			Object p1 = ServletActionContext.getRequest().getAttribute("courseCode");
			Object p2 = ServletActionContext.getRequest().getAttribute("scormType");
			Object p3 = ServletActionContext.getRequest().getAttribute("indexFile");
			Object p4 = ServletActionContext.getRequest().getAttribute("myDate");
			url = "/CourseImports/" + p1 + "/" + p2 + "/" + p3 + "?myDate=" + p4;
		}else{
		%>
		<script type="text/JavaScript">
			window.onload = function() {
				alert("学员信息验证失败！");
				window.close();
			}
		</script>
		<%
		}
	 %>
  </head>
  
  <body>
  	<font color="red" size="3"><b>该课程仅供已报名直播课程的学员回看学习使用。未经允许，不得下载或传播。</b></font><br />
    <iframe border="0" height="100%" width="100%" marginwidth='0' marginheight='0' hspace='0' vspace='0' frameborder='0' scrolling='no' src="<%=url%>"></iframe>
  </body>
</html>
