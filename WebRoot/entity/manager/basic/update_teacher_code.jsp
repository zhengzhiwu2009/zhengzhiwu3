<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>修改讲师信息</title>
		<link href="/entity/manager/images/css.css" rel="stylesheet" type="text/css">
	</head>
	<script type="text/javascript">
		function doSubmit() {
			var code = document.getElementById("code").value;
			if(code.length > 18) {
				alert("身份证号不能超过18位");
				return;
			}
			document.myForm.action = "teacherResource_checkCode.action";
			document.myForm.submit();
		}
	</script>
	<body>
		<div class="h2">
			<div class="h2div"> 
				修改讲师信息 
			</div>
		</div>
		<div class="content">
		<form action="" name="myForm">
			<input type="hidden" name="id" id="id" value="<s:property value="teacherInfo.id"/>"/>
			<table cellspacing="0" cellpadding="0">
					<tr>
						<th>
							证件号码：
						</th>
						<td>
							<input type="text" size="50" id="code" name="code" id="code" value="<s:property value="teacherInfo.sg_tti_code"/>">
							<font color="red">*讲师证件号码一旦录入将无法修改，若无法确认证件号码可直接点击“保存下一步”，修改讲师其他信息。</font>
						</td>
					</tr>
					<tr>
						<td colSpan="2" align="center">
							<input type="button" value="保存下一步" onclick="doSubmit()"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="返回" onclick="javascript:window.history.back()">
						</td>
					</tr>
			</table>
		</form>
		</div>
	</body>
</html>