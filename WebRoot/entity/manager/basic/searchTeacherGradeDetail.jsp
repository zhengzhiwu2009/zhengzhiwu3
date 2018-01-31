<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>讲师评价详情查询</title>
		<link href="/entity/manager/images/css.css" rel="stylesheet" type="text/css">
	</head>
	<script type="text/javascript">
		function doSubmit() {
			var year = document.getElementById("year").value;
			if(year == '') {
				alert("年份不能为空");
				return;
			}
			var name = document.getElementById("name").value;
			document.getElementById("teacherName").value = encodeURI(name,"utf-8");
			document.myForm.action = "teacherGradeDetail_searchTeacherGradeDetail.action";
			document.myForm.submit();
		}
	</script>
	<body>
		<div class="h2">
			<div class="h2div"> 
				讲师评价详情查询
			</div>
		</div>
		<div class="content">
		<form action="" name="myForm" method="post">
			<input type="hidden" name="id" id="id" value="<s:property value="teacherInfo.id"/>"/>
			<input type="hidden" name="teacherName" id="teacherName" value=""/>
			<table cellspacing="0" cellpadding="0">
					<tr>
						<th>
							<font color="red">*</font>年份：
						</th>
						<td>
							<input type="text" size="45" align="left" id="year" name="year" value="<s:property value="year"/>" />
							<font color="red">如：2015</font>
						</td>
						<th>
							讲师姓名：
						</th>
						<td>
							<input type="text" size="50" id="name" name="name"/>
						</td>
					</tr>
					<tr>
						<td colSpan="4" align="center">
							<input type="button" value="查询" onclick="doSubmit()"/>
						</td>
					</tr>
			</table>
		</form>
		</div>
	</body>
</html>
