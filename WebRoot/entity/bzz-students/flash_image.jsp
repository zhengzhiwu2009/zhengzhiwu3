<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:directive.page
	import="com.whaty.platform.sso.web.servlet.UserSession" />
<jsp:directive.page import="com.whaty.platform.database.oracle.*" />

<%
	dbpool workFI = new  dbpool();
	
	//当前直播
	String sqlFI = "select pbo.flash_image,pbo.subject from pe_bzz_onlinecourse pbo where pbo.start_date < sysdate and pbo.end_date > sysdate";
	MyResultSet rsFI = workFI.executeQuery(sqlFI);
	String onlineCourseUrl = "/entity/bzz-students/jumpNowOnlineCourse.jsp";
	if(rsFI.next()) {
		String flashImage = rsFI.getString("flash_image");
		String subject = "";
		if(flashImage == null) {
			flashImage = "default.jpg";
			subject = "<div style=\"position: absolute;visibility: visible;height: 88px;width: 102px;top: 21px;left:0px;text-align: center;vertical-align: middle;cursor: inherit\"><a href=\""+onlineCourseUrl+"\"><font color=\"red\">《" + rsFI.getString("subject") + "》正在直播</font></a></div>";
		}
		%>
	<div id="flash_pic1" style="position: absolute; visibility: visible; left: 0px; top: 0px; z-index: 5; width: 110; height: 97;cursor: pointer">
	<a
		href="<%=onlineCourseUrl %>"
		target="_blank"><img
			src="/entity/manager/basic/FlashImage/<%= flashImage%>"  border="0">
	</a>
	<%=subject %>
	<map name="Map">
		<area shape="rect" coords="6,40,105,60"
			href="<%= onlineCourseUrl%>"
			target="_blank">
	</map>
</div>
<SCRIPT LANGUAGE="JavaScript">
	<!-- Begin
	var x = 50,y = 60 
	var xin = true, yin = true 
	var step = 1 
	var delay = 10 
	var obj=document.getElementById("flash_pic1") 
	function getwindowsize() { 
	var L=T=0
	//by www.qpsh.com
	var R= document.body.clientWidth-obj.offsetWidth 
	var B = document.body.clientHeight-obj.offsetHeight 
	obj.style.left = x + document.body.scrollLeft 
	obj.style.top = y + document.body.scrollTop 
	x = x + step*(xin?1:-1) 
	if (x < L) { xin = true; x = L} 
	if (x > R){ xin = false; x = R} 
	y = y + step*(yin?1:-1) 
	if (y < T) { yin = true; y = T } 
	if (y > B) { yin = false; y = B } 
	} 
	var itl= setInterval("getwindowsize()", delay) 
	obj.onmouseover=function(){clearInterval(itl)} 
	obj.onmouseout=function(){itl=setInterval("getwindowsize()", delay)} 
	//  End -->
</script>
		<%
	}
	workFI.close(rsFI);
 %>

