<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page
	import="com.whaty.platform.sso.web.action.SsoConstant" />
<% 
	if(session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY) == null) {
		response.sendRedirect("/error/priv_error.htm");
		return ;
	}
	dbpool db = new dbpool();
	MyResultSet rs = null;
	
	String id = request.getParameter("id");
	String sql = "select launch from scorm_sco_launch where id='"+id+"'";
	rs = db.executeQuery(sql);
	if(rs.next() && rs.getString("launch") != null) {
		String url = rs.getString("launch");
		%>
		<script type="text/javascript">
			window.navigate('<%=url%>');
		</script>
		<%
	} else {
		%>
		<script type="text/javascript">
			alert("课件不存在");
			window.close();
		</script>
		<%
	}
	db.close(rs);
%>

