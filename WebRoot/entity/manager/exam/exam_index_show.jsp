<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>

<%@ page contentType="text/html;charset=UTF-8"%>
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
String question_id = fixnull(request.getParameter("question_id"));	//问题id
String exambatch_id = fixnull(request.getParameter("exambatch_id"));	//问题id
String question_type = fixnull(request.getParameter("question_type"));	//问题id
String is_solve = fixnull(request.getParameter("is_solve"));	//问题id
String title_search = fixnull(request.getParameter("title_search"));	//问题id

dbpool db = new dbpool();

String sql_update = "update exam_question_info set is_index_show=1-nvl(is_index_show, 0) where id='"+question_id+"'";
int result = db.executeUpdate(sql_update);
db.close(null);
if(result > 0){
%>
<script type="text/javascript">
<!--
alert("操作成功");
window.location.href='/entity/manager/exam/question_list.jsp?question_id=<%= question_id%>&exambatch_id=<%= exambatch_id %>&question_type=<%= question_type %>&is_solve=<%=is_solve%>&title_search=<%= title_search %>';
//-->
</script>
<%
}else{
%>
<script type="text/javascript">
<!--
alert("操作失败");
window.location.href='/entity/manager/exam/question_list.jsp?question_id=<%= question_id%>&exambatch_id=<%= exambatch_id %>&question_type=<%= question_type %>&is_solve=<%=is_solve%>&title_search=<%= title_search %>';
//-->
</script>
<%
}
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
</head>
<body>
</body>
</html>