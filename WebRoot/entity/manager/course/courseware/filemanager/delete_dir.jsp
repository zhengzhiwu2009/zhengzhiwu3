
<%@page import="com.whaty.encrypt.appbox"%><%
	/*---------------------------------------------
	��������:ɾ��Ŀ¼
	��дʱ��:2004-8-28
	��д��:�½�
	email:�½�@whaty.com
	
	�޸�����¼
	�޸�ʱ�䣺  �޸����ݣ�   �޸��ˣ�
	-----------------------------------------------*/
%>
<%@page import="java.io.*,com.whaty.*,java.util.*" %>
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
<%s_dir=request.getParameter("dir");
  String s_current_path=(String)session.getValue("s_current_path");
  file_work.deleteFile(appbox.convertEncoding("ISO8859-1","GB2312",s_dir),s_current_path);
  
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
<META HTTP-EQUIV="Refresh" CONTENT="0.1;URL=filemanager_main02.jsp?dir=.">
<body>
<a href="filemanager_main.jsp02?dir=.">back</a>
</body>
</html>
