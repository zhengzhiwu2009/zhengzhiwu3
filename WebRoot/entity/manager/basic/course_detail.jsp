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
				
				课程信息
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
						<s:property value="courseInfo.courseName" />
						</td>
					</tr>
					<tr>
						<th>

							<font color="red">&nbsp;</font>业务分类：
						</th>
						<td>
						<s:property value="courseInfo.getEnumConstByFlagCourseCategory().getName()" />
						</td>
					</tr>
					<tr>
						<th>

							<font color="red">&nbsp;</font>大纲分类：
						</th>
						<td>
						<s:property value="courseInfo.getEnumConstByFlagCourseItemType().getName()" />
						</td>
					</tr>
					<tr>
						<th>

							<font color="red">&nbsp;</font>内容属性分类：
						</th>
						<td>
						<s:property value="courseInfo.getEnumConstByFlagContentProperty().getName()" />
						</td>
					</tr>
					<tr>
						<th>

							<font color="red">&nbsp;</font>提交机构：
						</th>
						<td>
						<s:property value="courseInfo.createOrganizationName" />
						</td>
					</tr>
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