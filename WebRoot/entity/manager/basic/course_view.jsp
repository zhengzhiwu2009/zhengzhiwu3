<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>添加讲师</title>
		<link href="/entity/manager/images/css.css" rel="stylesheet"
			type="text/css">
	</head>
	<script type="text/javascript">
		
	</script>
	<body>
		<div class="h2">
			<div class="h2div">
				
				课程简介
			</div>
		</div>
		<div class="content">
			<form action="" name="myForm" method="post">
				<input type="hidden" name="fields" id="fields" />
				<table cellspacing="0" cellpadding="0">
					<tr>
						<th>

							<font color="red">&nbsp;</font>课程简介：
						</th>
						<td>
						<s:property value="courseInfo.description" />
						</td>
					</tr>
					<tr>
				
						<td colSpan="2" align="center">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="返回"
								onclick="javascript:window.history.back()">
						</td>
						 
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>