<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>添加讲师</title>
		<link href="/entity/manager/images/css.css" rel="stylesheet" type="text/css">
	</head>
	<script type="text/javascript">
		function doSubmit() {
			if(document.getElementById('name').value == null || document.getElementById('name').value == '') {
				alert("讲师姓名不能为空");
				return;
			}
			if(document.getElementById('organization_name').value == null || document.getElementById('organization_name').value == '') {
				alert("所在机构不能为空");
				return;
			}
			if(document.getElementById('mobile').value == null || document.getElementById('mobile').value == '') {
				alert("联系电话不能为空");
				return;
			}
			if(document.getElementById('description').value == null || document.getElementById('description').value == '') {
				alert("讲师简介不能为空");
				return;
			}
			var fields = document.getElementsByName('teachingField');
			var flag = false;
			var fieldsStr = '';
			for(var i = 0; i < fields.length; i++) {
				if(fields[i].checked) {
					flag = true;
					fieldsStr += fields[i].value + ','
				}
			}
			document.getElementById("fields").value = fieldsStr.substring(0, fieldsStr.length - 1);
			if(!flag) {
				alert("授课领域不能为空");
				return;
			}
			document.myForm.action = "industryTeacherResource_updateTeacher.action";
			document.myForm.submit();
		}
	</script>
	<body>
		<div class="h2">
			<div class="h2div">  
				添加讲师
			</div>
		</div>
		<div class="content">
		<form action="" name="myForm" method="post">
			<input type="hidden" name="fields" id="fields"/>
			<input type="hidden" name ="id" value ="<s:property value="teacherInfo.id" />">
			<input type ="hidden" name ="createOrganizationName" value="<s:property value="teacherInfo.createOrganizationName" />">
			<input type ="hidden" name ="createDate" value="<s:property value="teacherInfo.createDate" />">
			<input type ="hidden" name ="" value="">
			
			<table cellspacing="0" cellpadding="0">
					<tr>
						<th width="30%">
							<font color="red">*</font>讲师姓名：
						</th>
						<td>
							<input type="text" size="50" name="name" id="name"  value="<s:property value="teacherInfo.name" />">
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">*</font>所在机构：
						</th>
						<td>
							<input type="text" size="50" name="organization_name" id="organization_name" value ="<s:property value="teacherInfo.organization_name" />">
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>工作部门：
						</th>
						<td>
							<input type="text" size="50" name="department" id="department" value ="<s:property value="teacherInfo.departmentName" />">
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>证件号码：
						</th>
						<td>
							<input type="text" size="50" name="code" id="code" value="<s:property value="teacherInfo.code" />" >
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">*</font>联系电话：
						</th>
						<td>
							<input type="text" size="50" name="mobile" id="mobile" value="<s:property value="teacherInfo.mobile" />" >
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>电子邮箱：
						</th>
						<td>
							<input type="text" size="50" name="email" id="email" value="<s:property value="teacherInfo.email" />">
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">*</font>讲师简介：
						</th>
						<td>
							<textarea rows="6" cols="70" name="description" id="description" > <s:property value="teacherInfo.description" /></textarea>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">*</font>授课领域：
						</th>
						<td> 
							<s:if test="fieldList != null && fieldList.size() > 0">
								<s:iterator value="fieldList" id="field">
									<s:if test="teacherInfo.tfc_name.indexOf(#field.name) >=0">
										<input checked="checked" type="checkbox" name="teachingField" value="<s:property value="#field.name"/>"/><s:property value="#field.name"/>
									</s:if>
									<s:else>
										<input  type="checkbox" name="teachingField" value="<s:property value="#field.name"/>"/><s:property value="#field.name"/>
									</s:else>
								</s:iterator>
							</s:if>
						</td>
					</tr>
					<tr>
						<td colSpan="2" align="center">
							<input type="button" id="tijiao" value="保存" onclick="doSubmit()"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="返回" onclick="javascript:window.history.back()">
						</td>
					</tr>
			</table>
		</form>
		</div>
	</body>
</html>