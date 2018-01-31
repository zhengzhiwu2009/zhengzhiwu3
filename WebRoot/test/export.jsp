<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="com.opensymphony.xwork2.ActionContext" %>
<%
	String excelFileName = (String)ActionContext.getContext().getSession().get("excelFileName");
	if(excelFileName == null){//防备其他的地方重用export.xls
		excelFileName = "export.xls";
	}else{
		ActionContext.getContext().getSession().remove("excelFileName");		
	}
%>
<script>
	window.location='/test/<%=excelFileName%>';
</script>