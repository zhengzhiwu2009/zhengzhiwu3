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
	String questionId = fixnull(request.getParameter("question_id"));
	String flag=fixnull(request.getParameter("flag"));
	String answer_id = fixnull(request.getParameter("answer_id"));
    
	String exambatch_id = fixnull(request.getParameter("exambatch_id"));
	String question_type_search = fixnull(request.getParameter("question_type_search"));
	String is_solve=fixnull(request.getParameter("is_solve"));
	String title_search=fixnull(request.getParameter("title_search"));
	
    //UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
    
    dbpool db = new dbpool();
    MyResultSet rs = null;
    String publish_id="";
    String publish_name="";
    String sql_user=
		"select t.id, t.name\n" +
		"  from pe_manager t\n" + 
		" where t.fk_sso_user_id = '"+us.getId()+"'";
    
    rs = db.executeQuery(sql_user);
    if(rs!=null && rs.next())
    {
    	publish_id = fixnull(rs.getString("id"));
    	publish_name = fixnull(rs.getString("name"));
    }
    db.close(rs);
	String publish_type = "manager";

	int ret=0 ;
	
	String sql_t="";
		    
	try
    {
		if(flag.equals("1"))//官方已经回答，修改此答案
		{
			sql_t="update exam_answer_info set body=? where id='"+answer_id+"'";
			ret = db.executeUpdate(sql_t,body);
		}
		else  //官方未回答，添加答案
		{
			sql_t=
			"insert into exam_answer_info\n" +
			"  (id,\n" + 
			"   question_id,\n" + 
			"   publish_date,\n" + 
			"   publish_id,\n" + 
			"   publish_name,\n" + 
			"   publish_type,\n" + 
			"   is_key,\n" + 
			"   body)\n" + 
			"values\n" + 
			"  (to_char(s_exam_answer_info_id.nextval),\n" + 
			"   '"+questionId+"', sysdate,\n" + 
			"   '"+publish_id+"',\n" + 
			"   '"+publish_name+"',\n" + 
			"   '"+publish_type+"',\n" + 
			"   '0',\n" + 
			"    ?)";
			
			ret = db.executeUpdate(sql_t,body);

		}
				
	}
	catch(Exception e)
	{
		out.print(e.getMessage());
		return;
	}
		if (ret == 1) {
	%>
			<script language="javascript">
				alert("成功添加！");
				//window.navigate("/entity/exam/peBzzExamAnswer_questionInfo.action");
				window.navigate("/entity/manager/exam/question_list.jsp?exambatch_id=<%= exambatch_id %>&question_type=<%= question_type_search %>&is_solve=<%= is_solve %>&title_search=<%= title_search %>");
			</script>
	<%
		} else {
	%>
			<script language="javascript">
				alert("添加不成功！");
				window.history.back(-1);
			</script>
	<%
		}
	%>
