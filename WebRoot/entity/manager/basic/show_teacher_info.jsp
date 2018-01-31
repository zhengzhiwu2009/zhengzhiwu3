<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.whaty.platform.entity.bean.TeacherInfo;" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>查看详情</title>
		<link href="/entity/manager/images/css.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div class="h2">
			<div class="h2div"> 
				查看详情
			</div>
		</div>
		<div class="content">
		<form action="" name="myForm">
			<input type="hidden" name="fields" id="fields"/>
			<input type="hidden" name="id" id="id" value="<s:property value="teacherInfo.id"/>"/>
			<table cellspacing="0" cellpadding="0">
					<tr>
						<th width="30%">
							讲师姓名：
						</th>
						<td>
							<s:property value="teacherInfo.sg_tti_name"/>
						</td>
					</tr>
					<tr>
						<th>
							所在机构：
						</th>
						<td>
							<s:property value="teacherInfo.sg_tti_organization_name"/>
						</td>
					</tr>
					<tr>
						<th>
							工作部门：
						</th>
						<td>
							<s:property value="teacherInfo.sg_tti_department_name"/>
						</td>
					</tr>
					<tr>
						<th>
							证件号码：
						</th>
						<td>
							<s:property value="teacherInfo.sg_tti_code"/>
						</td>
					</tr>
					<tr>
						<th>
							联系电话：
						</th>
						<td>
							<s:property value="teacherInfo.sg_tti_mobile"/>
						</td>
					</tr>
					<tr>
						<th>
							电子邮箱：
						</th>
						<td>
							<s:property value="teacherInfo.sg_tti_email"/>
						</td>
					</tr>
					<tr>
						<th>
							讲师简介：
						<%
							Object desc = ((TeacherInfo)request.getAttribute("teacherInfo")).getSg_tti_description();
						 %>
						</th>
						<td id="desc">
							<%=desc %>
							<!-- s:property value="teacherInfo.sg_tti_description"/ -->
						</td>
					</tr>
					<tr>
						<th>
							授课领域：
						</th>
						<td>
							<s:property value="teacherInfo.tfc_name"/>
						</td>
					</tr>
					<tr>
						<td colSpan="2" align="center">
							<input type="button" value="关闭" onclick="javascript:window.self.close()">
						</td>
					</tr>
			</table>
		</form>
		</div>
	</body>
</html>