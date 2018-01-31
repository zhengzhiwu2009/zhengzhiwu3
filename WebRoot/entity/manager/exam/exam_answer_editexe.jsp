<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.user.*,com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@ page import = "com.whaty.platform.interaction.exam.*" %>
<%@ include file="./priv.jsp"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant" />
<%!
   String fixnull(String str){ 
   		if(str==null || str.equals("null") || str.equals("")){
   			str= "";
   			return str;
   		}
   		return str;
   }
%>
<%
	//String title = request.getParameter("title");
	String body = fixnull(request.getParameter("body"));
	String title = fixnull(request.getParameter("title"));
	String questionId = fixnull(request.getParameter("question_id"));
    //UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
    
    String exambatch_id = fixnull(request.getParameter("exambatch_id"));
String question_type_search = fixnull(request.getParameter("question_type"));
String is_solve=fixnull(request.getParameter("is_solve"));
String title_search=fixnull(request.getParameter("title_search"));
    
    dbpool db = new dbpool();

	int ret=0 ;
	
	String sql_t="";
		    
	try
    {
			sql_t="update exam_question_info set title='"+title+"',body=? where id='"+questionId+"'";
			ret = db.executeUpdate(sql_t,body);
				
	}
	catch(Exception e)
	{
		out.print(e.getMessage());
		return;
	}
		if (ret == 1) {
	%>
			<script language="javascript">
				alert("成功修改！");
				//window.navigate("/entity/exam/peBzzExamAnswer_questionInfo.action");
				window.navigate("/entity/manager/exam/question_list.jsp?exambatch_id=<%= exambatch_id %>&question_type=<%= question_type_search %>&is_solve=<%= is_solve %>&title_search=<%= title_search %>");
			</script>
	<%
		} else {
	%>
			<script language="javascript">
				alert("修改不成功！");
				window.history.back(-1);
			</script>
	<%
		}
	%>
