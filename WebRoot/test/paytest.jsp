<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page import="com.whaty.platform.entity.util.PaymentUtil" %>
<%@ page import="com.whaty.platform.entity.bean.OnlineOrderInfo" %>
<%@ page import="java.util.*" %>
<% response.setHeader("expires", "0"); %>

hello test.jsp!

<%
	PaymentUtil pu = new PaymentUtil();
	String s = (String)request.getParameter("s");
	String e = (String)request.getParameter("e");
	System.out.println(s+"--"+e);
	List<OnlineOrderInfo> orderList = pu.checkOrderList(s,e,"");

%>

