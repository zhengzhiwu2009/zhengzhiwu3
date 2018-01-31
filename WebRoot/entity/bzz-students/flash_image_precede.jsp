<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<jsp:directive.page
	import="com.whaty.platform.sso.web.servlet.UserSession" />
<jsp:directive.page import="com.whaty.platform.database.oracle.*" />
<%@page import="com.sun.org.apache.xerces.internal.impl.xpath.regex.Match"%>

<%
	dbpool workFIP = new  dbpool();
	
	//提前提示直播
	String sqlFIP = "select pbo.flash_image,pbo.subject,pbo.start_date from pe_bzz_onlinecourse pbo where pbo.start_date > sysdate and pbo.start_date < sysdate+pbo.precede_days";
	MyResultSet rsFIP = workFIP.executeQuery(sqlFIP);
	//String onlineCourseUrl = "/entity/bzz-students/jumpNowOnlineCourse.jsp";
	int n = 0;
	while(rsFIP.next()) {
		String flashImage = rsFIP.getString("flash_image");
		String subject = rsFIP.getString("subject");
		String startDate = rsFIP.getString("start_date");
		startDate=startDate.substring(0,startDate.length()-2);
		String info = subject + " 将于 " + startDate + " 开始直播！";
		//String subject = "";
		//if(flashImage == null) {
		//	flashImage = "default.gif";
		//	subject = "<div style=\"position: absolute;visibility: visible;height: 88px;width: 102px;top: 21px;left:0px;text-align: center;vertical-align: middle;cursor: inherit\"><a href=\""+onlineCourseUrl+"\"><font color=\"red\">《" + rs.getString("subject") + "》正在直播</font></a></div>";
		//}
		%>
	<div id="flash_pic_precede_<%=n %>" style="position: absolute; visibility: visible; left: 0px; top: 0px; z-index: 5; width: 110; height: 97;cursor: pointer" onclick="alert('<%=info %>');">
	<img
			src="/entity/manager/basic/FlashImage/<%= flashImage%>"  border="0">
	
	<map name="Map">
		<area shape="rect" coords="6,40,105,60"
			href="#" onclick="alert('<%=info %>');return false;">
	</map>
	<!--%=subject %-->
</div>
<SCRIPT LANGUAGE="JavaScript">
	<!-- Begin
	var x<%=n %> = <%=(int)(Math.random() * 800)%>,y<%=n %> = <%=(int)(Math.random() * 600)%>
	var xin<%=n %> = true, yin<%=n %> = true 
	var step<%=n %> = <%=(int)(Math.random() * 2)+1%> 
	var delay<%=n %> = 10 
	var obj<%=n %>=document.getElementById("flash_pic_precede_<%=n %>") 
	function getwindowsize<%=n %>() { 
	var L=T=0
	//by www.qpsh.com
	var R= document.body.clientWidth-obj<%=n %>.offsetWidth 
	var B = document.body.clientHeight-obj<%=n %>.offsetHeight 
	obj<%=n %>.style.left = x<%=n %> + document.body.scrollLeft 
	obj<%=n %>.style.top = y<%=n %> + document.body.scrollTop 
	x<%=n %> = x<%=n %> + step<%=n %>*(xin<%=n %>?1:-1) 
	if (x<%=n %> < L) { xin<%=n %> = true; x<%=n %> = L} 
	if (x<%=n %> > R) { xin<%=n %> = false; x<%=n %> = R} 
	y<%=n %> = y<%=n %> + step<%=n %>*(yin<%=n %>?1:-1) 
	if (y<%=n %> < T) { yin<%=n %> = true; y<%=n %> = T } 
	if (y<%=n %> > B) { yin<%=n %> = false; y<%=n %> = B } 
	} 
	var it<%=n %>= setInterval("getwindowsize<%=n %>()", delay<%=n %>) 
	obj<%=n %>.onmouseover=function(){clearInterval(it<%=n %>)} 
	obj<%=n %>.onmouseout=function(){it<%=n %>=setInterval("getwindowsize<%=n %>()", delay<%=n %>)} 
	//  End -->
</script>
		<%
		n++;
	}
	workFIP.close(rsFIP);
 %>

