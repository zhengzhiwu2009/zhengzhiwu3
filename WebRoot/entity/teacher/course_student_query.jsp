<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>学员-课程</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="js/shared.js"></script>
<script type="text/javascript" src="/web/bzz_index/index_new/js/jquery-1.6.1.min.js"></script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="101.5%" border="0" cellspacing="0" cellpadding="4" align="center">
    <tr> 
      <td><div class="content_title">课程-学员</div></td>
    </tr>
</table>
<div id="tab">
<div id="tab_user2course" style="width:100.73%">
<form name="form1" method="post" action="/entity/teaching/courseStudent.action" id="form1">
<div class="cntent_k">
   <table width="98%" border="0" align="center" cellpadding="2" cellspacing="3" class="tdbg2">
    <tr >
	    <td width="50%" nowrap class="tdbg2" align="center"><font color="red">*</font>
	    	课程列表
			<select name="courseId" style="width:400px;" id="courseId">
			   <s:iterator value="couStuList" id="stat">
			      <option value="<s:property value="#stat[0]"/>">(<s:property value="#stat[2]"/>)<s:property value="#stat[1]"/></option>
			   </s:iterator>
			</select>
		</td>
	</tr>
	<tr>	
		<td width="50%" nowrap class="tdbg2" align="center"><font color="red">&nbsp;&nbsp;</font>	
			<INPUT class=button style="margin-left:50px;" onclick="doSubmit();" type="button" value="&nbsp;查&nbsp;找&nbsp;">
	    </td>
    </tr>
    <tr>
    	<td colspan="4" align="center"><font color="red"></font></td>
    </tr>
  </table>
  </div>
  </form>
</div>
</div>
</body>
<script type="text/javascript">
	function doSubmit() {
		var courseId = document.getElementById('courseId').value;
		var url = '/entity/teaching/courseStudent.action?courseId='+courseId;
		document.getElementById('form1').action = url;
		document.getElementById('form1').submit();
	}
</script>
</html>
