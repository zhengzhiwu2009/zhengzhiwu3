
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page import="com.whaty.platform.entity.util.PaymentUtil" %>
<%@ page import="com.whaty.platform.entity.bean.OnlineOrderInfo" %>
<%@ page import="com.whaty.util.HzphCache" %>
<%@ page import="net.sf.ehcache.CacheManager" %>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.Element"%>
<%@ page import="java.util.*" %>
<% response.setHeader("expires", "0"); %>


<%
String num = request.getParameter("num");
CacheManager manager = CacheManager.getInstance();
Cache cache = manager.getCache("hzphCache");
cache.remove("Onlinesum");
Element element = new Element("Onlinesum", Integer.valueOf(num),false,5*1000,5*1000);
cache.put(element);

%>
<%=cache.get("Onlinesum").getValue() %>
