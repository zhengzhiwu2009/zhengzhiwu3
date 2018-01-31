<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html>
	<head>
		<title>公共课程查询</title>
		<link rel="stylesheet" type="text/css" href="./js/extjs/resources/css/ext-all.css">
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
		<script language="JavaScript" src="js/shared.js"></script>
	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<table width="101.5%" border="0" cellspacing="0" cellpadding="4" align="center">
			<tr>

				<td>
					<div class="content_title">
						公共课程查询
					</div>
				</td>
			</tr>
		</table>
		<div id="tab">
			<div id="tab_user2course" style="width: 100.73%">
				<form name="form1" method="post" action="/entity/teaching/peBzzCoursePubSearch_singleCourseSearch.action" id="form1" target="_blank">
					<div class="cntent_k">
						<table width="98%" border="0" align="center" cellpadding="2" cellspacing="3" class="tdbg2">
							<tr>
								<td width="25%" nowrap class="tdbg2" align="center">
									课程编号：
									<input name="courseCode" type="text" size="16" maxlength="30" id="courseCode">
								</td>
								<td width="25%" nowrap class="tdbg2" align="center">
									课程名称：
									<input name="courseName" type="text" size="16" maxlength="30" id="courseName">
								</td>
								<td width="25%" nowrap class="tdbg2" align="center">
									主讲人：
									<input name="courseTeacher" type="text" size="16" maxlength="30" id="courseTeacher">
								</td>
								<td width="25%" nowrap class="tdbg2" align="left">
									<INPUT class=button onclick="doSubmit();" type="button" value="&nbsp;&nbsp;搜&nbsp;&nbsp;索&nbsp;&nbsp;">
								</td>
							</tr>
						</table>
					</div>
				</form>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	function doSubmit() {
		var code = document.getElementById('courseCode').value;
		var name = document.getElementById('courseName').value;
		var teacher = document.getElementById('courseTeacher').value;
		if((code == null || code == '') && (name == null || name == '') && (teacher == null || teacher == '')) {
			alert('请至少填写一个查询条件');
			return;
		}
		document.getElementById('form1').submit();
	}
	
</script>
</html>
