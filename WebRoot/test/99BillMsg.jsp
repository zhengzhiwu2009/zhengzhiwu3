<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	response.setHeader("expires", "0");
%>
<result><s:property value="rtnOK" /></result><redirecturl>http://trainingtest.sac.net.cn/test/msg2.jsp?msg=<s:property value="msg" /></redirecturl>