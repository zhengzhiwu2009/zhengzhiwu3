<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>	
<%@page import="com.whaty.encrypt.appbox"%>
<%@page import="java.io.*,com.whaty.*"%>
<%@page import="java.lang.*"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.whaty.platform.entity.service.GeneralService"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%>
<%!String s_dir;
%>
<jsp:useBean id="file_work" scope="page" class="com.whaty.filemanager.filemanager"/>
<%
//String type_flag=java.net.URLDecoder.decode(request.getParameter("file"),"UTF-8");
	s_dir=request.getParameter("file");
//System.out.println("========="+s_dir);
  	String s_current_path=(String)session.getValue("s_current_path");
  	//file_work.deleteFile(appbox.convertEncoding("ISO8859-1","GBK",s_dir),s_current_path);
	file_work.deleteFile(s_dir,s_current_path);
	
	ServletContext context = request.getSession().getServletContext();
 	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
    GeneralService service = (GeneralService)ctx.getBean("generalService");
    UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
    String id = UUID.randomUUID().toString().replace("-","");
    String login = us.getLoginId();
    String name = us.getUserName();
    String code = session.getAttribute("courseware_id").toString();
    s_dir = appbox.convertEncoding("ISO8859-1","UTF-8",s_dir);
    String sql = "insert into  SCIRM_COURSE_CZ(ID,COURSEWARE_CODE,OPERATE_TYPE,OPERATE_DATE,OPERATE_FILENAME,OPERATE_LOGINNAME,OPERATE_USERNAME) values('"+ id +"','" + code +"',3,sysdate,'" + s_dir + "','" + login + "','" + name +"')";
    service.executeBySQL(sql);
  %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<META HTTP-EQUIV="Refresh" CONTENT="0.1;URL=filemanager_main02.jsp?dir=.">
<body>
<a href="filemanager_main02.jsp?dir=.">返回</a>
</body>
</html>
