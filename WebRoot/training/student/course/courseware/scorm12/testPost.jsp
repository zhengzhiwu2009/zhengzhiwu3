<%@ page language="java" pageEncoding="gb2312"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@page import="com.whaty.platform.standard.scorm.util.ScormConstant,com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%--<%@page import="com.whaty.platform.sso.web.servlet.UserSession;"%>--%>
<%@ page import="com.whaty.platform.database.oracle.*" %>
<%@ page import="com.whaty.platform.entity.util.MyUtil" %>
<%@ page import="java.util.*" %>

<head>
	<script src="/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript">
		function submitData(){
				var url = 'http://new.hzph.com:5525/servlet/lmscmijs?d=0.22566389312940432&command=putdata&JSESSIONID=A8E0E3407491930E628F6DA0660189FC&dmeID=cmi.core.student_id&dmeValue=SAC0005850&dmeID=cmi.core.student_name&dmeValue=&dmeID=cmi.core.lesson_location&dmeValue=null&dmeID=cmi.core.credit&dmeValue=null&dmeID=cmi.core.lesson_status&dmeValue=incomplete&dmeID=cmi.core.entry&dmeValue=null&dmeID=cmi.core.total_time&dmeValue=00%3A00%3A34.68&dmeID=cmi.core.lesson_mode&dmeValue=&dmeID=cmi.core.exit&dmeValue=null&dmeID=cmi.core.session_time&dmeValue=00%3A00%3A14.63&dmeID=cmi.core.score.raw&dmeValue=null&dmeID=cmi.core.score.min&dmeValue=null&dmeID=cmi.core.score.max&dmeValue=null&dmeID=cmi.core.uncommit_time&dmeValue=&dmeID=cmi.comments&dmeValue=null&dmeID=cmi.launch_data&dmeValue=null&dmeID=cmi.suspend_data&dmeValue=Slide02*1%2C%23%2Cundefined';
				saveData(url);	
		}
		
		function saveData(dataUrl){
		  	var url = dataUrl;
			$.ajaxSettings.async = false;
			$.ajax({
			   type: "POST",
			   url: url,
			   success: function(data){
					alert(data);
			   }
			});
		}
		
	</script>
</head>
<body>

<input type="button" value="Submit" onclick="submitData();">

</body>
</html>
