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
	
	String title = fixnull(request.getParameter("title"));
	String body = fixnull(request.getParameter("body"));
	//String key = request.getParameter("key");
	String type = fixnull(request.getParameter("type"));
	
	 //UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	 
    
    dbpool db = new dbpool();
    MyResultSet rs = null;
    String submituserId="";
    String submituserName="";
    String sql_user=
		"select t.id, t.name\n" +
		"  from pe_manager t\n" + 
		" where t.fk_sso_user_id = '"+us.getId()+"'";
    
    rs = db.executeQuery(sql_user);
    if(rs!=null && rs.next())
    {
    	submituserId = fixnull(rs.getString("id"));
    	submituserName = fixnull(rs.getString("name"));
    }
    db.close(rs);
	String submituserType = "manager";
	
	String exambatchId = fixnull(request.getParameter("exambatchId"));
	
	int ret ;
	try
   {
       String sql_t=
			"insert into exam_question_info \n" +
			"  (id,\n" + 
			"   title,\n" + 
			"   publish_date,\n" + 
			"   exambatch_id,\n" + 
			"   submituser_id,\n" +
			"   submituser_name,\n" + 
			"   submituser_type,\n" + 
			"   question_type,\n" + 
			"   body)\n" + 
			"values\n" + 
			"  (to_char(s_exam_question_info_id.nextval),\n" + 
			"   '"+title+"', sysdate,\n" + 
			"   '"+exambatchId+"',\n" + 
			"   '"+submituserId+"',\n" + 
			"   '"+submituserName+"',\n" + 
			"   '"+submituserType+"',\n" + 
			"   '"+type+"',\n" + 
			"    ?)";
			
			ret = db.executeUpdate(sql_t,body);
	
}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
	if (ret > 0) {
%>
		<script language="javascript">
			alert("成功添加！");
			window.navigate("/entity/exam/peBzzExamAnswer_questionInfo.action");
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
