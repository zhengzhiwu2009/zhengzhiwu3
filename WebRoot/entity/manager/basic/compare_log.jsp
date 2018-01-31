<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.whaty.platform.entity.bean.TeacherInfo;" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>修改讲师信息</title>
		<link href="/entity/manager/images/css.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div class="h2">
			<div class="h2div">  
				<br/>修改讲师信息  
			</div>
		</div>
		<div class="content">
		<form action="" name="myForm">
			<input type="hidden" name="fields" id="fields"/>
			<input type="hidden" name="fieldIds" id="fieldIds"/>
			<input type="hidden" name="id" id="id" value="<s:property value="teacherInfo.id"/>"/>
			<input type="hidden" name="existFlag" value="<s:property value="existFlag"/>"/>
			<input type="hidden" name="teacherId" value="<s:property value="teacherId"/>"/>
			<table cellspacing="0" cellpadding="0">
					<tr>
						<th></th>
						<th>修改前</th>
						<th>修改后</th>
					</tr>
				<s:if test="count == 2">
					<tr>
						<th width="30%">
							<font color="red">*</font>讲师姓名：
						</th>
						<td>
							<s:property value="oldTeacherInfo[0]"/>
						</td>
						<td>
							<s:property value="newTeacherInfo[0]"/>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">*</font>所在机构：
						</th>
						<td>
							<s:property value="oldTeacherInfo[1]"/>
						</td>
						<td>
							<s:property value="newTeacherInfo[1]"/>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>工作部门：
						</th>
						<td>
							<s:property value="oldTeacherInfo[2]"/>
						</td>
						<td>
							<s:property value="newTeacherInfo[2]"/>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>证件号码：
						</th>
						<td>
							<s:property value="oldTeacherInfo[3]"/>
						</td>
						<td>
							<s:property value="newTeacherInfo[3]"/>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">*</font>联系电话：
						</th>
						<td>
							<s:property value="oldTeacherInfo[4]"/>
						</td>
						<td>
							<s:property value="newTeacherInfo[4]"/>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>电子邮箱：
						</th>
						<td>
							<s:property value="oldTeacherInfo[5]"/>
						</td>
						<td>
							<s:property value="newTeacherInfo[5]"/>
						</td>
					</tr>
					<tr>
						<%
							Object oldDesc = ((Object[])request.getAttribute("oldTeacherInfo"))[6];
							Object newDesc = ((Object[])request.getAttribute("newTeacherInfo"))[6];
						 %>
						<th>
							<font color="red">*</font>讲师简介：
						</th>
						<td>
							<%=oldDesc %>
						</td>
						<td>
							<%=newDesc %>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">*</font>授课领域：
						</th>
						<td>
							<s:property value="oldTeacherInfo[7]"/>
						</td>
						<td>
							<s:property value="newTeacherInfo[7]"/>
						</td>
					</tr>
				</s:if>
				<s:else>
					<tr>
						<th width="30%">
							<font color="red">*</font>讲师姓名：
						</th>
						<td>
							<s:property value="oldTeacherInfo[0]"/>
						</td>
						<td>
							<s:property value="teacherInfo.sg_tti_name"/>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">*</font>所在机构：
						</th>
						<td>
							<s:property value="oldTeacherInfo[1]"/>
						</td>
						<td>
							<s:property value="teacherInfo.sg_tti_organization_name"/>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>工作部门：
						</th>
						<td>
							<s:property value="oldTeacherInfo[2]"/>
						</td>
						<td>
							<s:property value="teacherInfo.sg_tti_department_name"/>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>证件号码：
						</th>
						<td>
							<s:property value="oldTeacherInfo[3]"/>
						</td>
						<td>
							<s:property value="teacherInfo.sg_tti_code"/>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">*</font>联系电话：
						</th>
						<td>
							<s:property value="oldTeacherInfo[4]"/>
						</td>
						<td>
							<s:property value="teacherInfo.sg_tti_mobile"/>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>电子邮箱：
						</th>
						<td>
							<s:property value="oldTeacherInfo[5]"/>
						</td>
						<td>
							<s:property value="teacherInfo.sg_tti_email"/>
						</td>
					</tr>
					<tr>
						<%
							Object oldDesc = ((Object[])request.getAttribute("oldTeacherInfo"))[6];
							Object desc = ((TeacherInfo)request.getAttribute("teacherInfo")).getSg_tti_description();
						 %>
						<th>
							<font color="red">*</font>讲师简介：
						</th>
						<td>
							<%=oldDesc %>
						</td>
						<td>
							<%=desc %>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">*</font>授课领域：
						</th>
						<td>
							<s:property value="oldTeacherInfo[7]"/>
						</td>
						<td>
							<s:property value="teacherInfo.tfc_name"/>
						</td>
					</tr>
				</s:else>
					<tr>
						<td colSpan="3" align="center">
							<input type="button" id="tijiao" value="返回" onclick="window.history.back()"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="关闭" onclick="javascript:window.close()">
						</td>
					</tr>
			</table>
		</form>
		</div>
	</body>
</html>