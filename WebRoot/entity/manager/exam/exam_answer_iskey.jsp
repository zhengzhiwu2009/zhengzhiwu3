<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>

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
	String answer_id=fixnull(request.getParameter("answer_id"));

String exambatch_id = fixnull(request.getParameter("exambatch_id"));
String question_type_search = fixnull(request.getParameter("question_type"));
String is_solve=fixnull(request.getParameter("is_solve"));
String title_search=fixnull(request.getParameter("title_search"));

	int ret ;
	dbpool db = new dbpool();
		    
	try
{
    String sql = "update exam_answer_info set is_key='1' where id='"+answer_id+"'";	
	ret = db.executeUpdate(sql);
			
}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
	if (ret == 1) {
%>
		<script language="javascript">
			alert("成功设置！");
			//window.navigate("/entity/exam/peBzzExamAnswer_questionInfo.action");
			window.navigate("/entity/manager/exam/question_list.jsp?exambatch_id=<%= exambatch_id %>&question_type=<%= question_type_search %>&is_solve=<%= is_solve %>&title_search=<%= title_search %>");
		</script>
<%
	} else {
%>
		<script language="javascript">
			alert("设置不成功！");
			window.history.back(-1);
		</script>
<%
	}
%>
