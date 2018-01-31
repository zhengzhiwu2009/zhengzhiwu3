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
	<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
	<script type="text/javascript">
		function doSubmit() {
			var description;
			if(FCKeditor_IsCompatibleBrowser()){
				var oEditor = FCKeditorAPI.GetInstance(""content""); 
				description=oEditor.GetXHTML(); 
			}else{
				alert("表单正在下载");
				return false;
			}
			document.getElementById('description').value= content ;
			if(document.getElementById('name').value == null || document.getElementById('name').value == '') {
				alert("讲师姓名不能为空");
				return;
			}
			if(document.getElementById('organization').value == null || document.getElementById('organization').value == '') {
				alert("所在机构不能为空");
				return;
			}
			//if(document.getElementById('mobile').value == null || document.getElementById('mobile').value == '') {
				//alert("联系电话不能为空");
				//return;
			//}
			//if(content.length <1) {
				//alert("讲师简介不能为空");
				//return;
			//}
			var fields = document.getElementsByName('teachingField');
			var flag = 'false';
			var fieldsStr = '';
			var fieldIdsStr = '';
			for(var i = 0; i < fields.length; i++) {
				if(fields[i].checked) {
					flag = 'true';
					fieldIdsStr += fields[i].value + ',';
					fieldsStr += fields[i].nextSibling.nodeValue + ',';
				}
			}
			document.getElementById("fieldIds").value = fieldIdsStr.substring(0, fieldIdsStr.length - 1);
			document.getElementById("fields").value = fieldsStr.substring(0, fieldsStr.length - 1);
			//if(flag == 'false') {
				//alert("授课领域不能为空");
				//return;
			//}
			document.myForm.action = "teacherResource_modify.action";
			//document.myForm.submit();
		}
		
		function checkInfo() {
			var existFlag = document.getElementById("existFlag").value;
			if(existFlag == 'true') {
				document.getElementById("tijiao").style.display = "none";
				var flag = window.confirm("讲师信息已存在,是否合并数据?");
				if(flag) {
					var id = document.getElementById("id").value;
					var teacherId = document.getElementById("teacherId").value;
					window.myForm.action = "teacherResource_merge.action?id=" + id + "&teacherId=" + teacherId;
					window.myForm.submit();
				} else {
					return;
				}
			} else {
				document.getElementById("mergeBut").style.display = "none";
			}
		}
		
		function doMerge() {
			var content;
			if(FCKeditor_IsCompatibleBrowser()){
				var oEditor = FCKeditorAPI.GetInstance("description"); 
				content=oEditor.GetXHTML(); 
			}else{
				alert("表单正在下载");
				return false;
			}
			
			if(document.getElementById('name').value == null || document.getElementById('name').value == '') {
				alert("讲师姓名不能为空");
				return;
			}
			if(document.getElementById('organization').value == null || document.getElementById('organization').value == '') {
				alert("所在机构不能为空");
				return;
			}
			//if(document.getElementById('mobile').value == null || document.getElementById('mobile').value == '') {
				//alert("联系电话不能为空");
				//return;
			//}
			//if(content.length <1) {
				//alert("讲师简介不能为空");
				//return;
			//}
			var fields = document.getElementsByName('teachingField');
			var flag = 'false';
			var fieldsStr = '';
			var fieldIdsStr = '';
			for(var i = 0; i < fields.length; i++) {
				if(fields[i].checked) {
					flag = 'true';
					fieldIdsStr += fields[i].value + ',';
					fieldsStr += fields[i].nextSibling.nodeValue + ',';
				}
			}
			document.getElementById("fieldIds").value = fieldIdsStr.substring(0, fieldIdsStr.length - 1);
			document.getElementById("fields").value = fieldsStr.substring(0, fieldsStr.length - 1);
			//if(flag == 'false') {
				//alert("授课领域不能为空");
				//return;
			//}
			var id = document.getElementById("id").value;
			var teacherId = document.getElementById("teacherId").value;
			window.myForm.action = "teacherResource_merge.action?id=" + id + "&teacherId=" + teacherId;
			window.myForm.submit();
		}
		function goBack() {
			window.myForm.action = "teacherResource.action";
			window.myForm.submit();
		}
	</script>
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
			<input type="hidden" name="description" id="description"/>
			<table cellspacing="0" cellpadding="0">
				
					<tr>
						<th width="30%">
							<font color="red">*</font>讲师姓名：
						</th>
						<td>
							<input type="text" size="50" name="name" id="name" value="<s:property value="teacherInfo.sg_tti_name"/>">
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">*</font>所在机构：
						</th>
						<td>
							<input type="text" size="50" name="organization" id="organization" value="<s:property value="teacherInfo.sg_tti_organization_name"/>">
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>工作部门：
						</th>
						<td>
							<input type="text" size="50" name="department" id="department" value="<s:property value="teacherInfo.sg_tti_department_name"/>">
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>证件号码：
						</th>
						<td>
							<s:property value="teacherInfo.sg_tti_code"/>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>联系电话：
						</th>
						<td>
							<input type="text" size="50" name="mobile" id="mobile" value="<s:property value="teacherInfo.sg_tti_mobile"/>">
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>电子邮箱：
						</th>
						<td>
							<input type="text" size="50" name="email" id="email" value="<s:property value="teacherInfo.sg_tti_email"/>">
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>讲师简介：
						</th>
						<td>
							<textarea rows="6" cols="70" name="content" id="content"><s:property value="teacherInfo.sg_tti_description"/></textarea>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>授课领域：
						</th>
						<td>
							<s:if test="fieldList != null && fieldList.size() > 0">
								<s:iterator value="fieldList" id="field">
									<input type="checkbox" name="teachingField" value="<s:property value="#field.id"/>" <s:if test="teacherInfo.tfc_name.indexOf(#field.name) != -1">checked</s:if>/><s:property value="#field.name"/>
								</s:iterator>
							</s:if>
						</td>
					</tr>
					<s:if test="existFlag == 'true'">
						<tr>
							<td colspan="2" align="center">
								<font color="red">数据已经存在，是否合并?</font>
							</td>
						</tr>
					</s:if>
					<tr>
						<td colSpan="2" align="center">
							<s:if test="existFlag == 'true'">
								<input type="button" id="mergeBut" value="合并数据" onclick="doMerge()"/>&nbsp;&nbsp;&nbsp;&nbsp;
							</s:if>
							<s:else>
								<input type="button" id="tijiao" value="保存" onclick="doSubmit()"/>&nbsp;&nbsp;&nbsp;&nbsp;
							</s:else>
								<input type="button" value="返回" onclick="goBack()">
						</td>
					</tr>
				<!--  
					<tr>
						<th width="30%">
							<font color="red">*</font>讲师姓名：
						</th>
						<td>
							<s:property value="teacherInfo.sg_tti_name"/>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">*</font>所在机构：
						</th>
						<td>
							<s:property value="teacherInfo.sg_tti_organization_name"/>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>工作部门：
						</th>
						<td>
							<s:property value="teacherInfo.sg_tti_department_name"/>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>证件号码：
						</th>
						<td>
							<s:property value="teacherInfo.sg_tti_code"/>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">*</font>联系电话：
						</th>
						<td>
							<s:property value="teacherInfo.sg_tti_mobile"/>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>电子邮箱：
						</th>
						<td>
							<s:property value="teacherInfo.sg_tti_email"/>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">*</font>讲师简介：
						</th>
						<td>
							<%
								//Object desc = ((TeacherInfo)request.getAttribute("teacherInfo")).getSg_tti_description();
						 	%>
							<%//=desc %>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">*</font>授课领域：
						</th>
						<td>pe
							<s:property value="teacherInfo.tfc_name"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<font color="red">数据已经存在，是否合并?</font>
						</td>
					</tr>
					<tr>
						<td colSpan="2" align="center">
							<input type="button" id="mergeBut" value="合并数据" onclick="doMerge()"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="返回" onclick="javascript:window.history.back()">
						</td>
					</tr>
				-->
			</table>
		</form>
		</div>
	</body>
	<script type="text/javascript"> 
<!-- 
// Automatically calculates the editor base path based on the _samples directory. 
// This is usefull only for these samples. A real application should use something like this: 
// oFCKeditor.BasePath = '/fckeditor/' ; // '/fckeditor/' is the default value. 

var oFCKeditor = new FCKeditor( 'content' ) ; 
oFCKeditor.Height = 300 ; 
oFCKeditor.Width  = 700 ; 

oFCKeditor.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
oFCKeditor.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';

oFCKeditor.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
oFCKeditor.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';

oFCKeditor.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
oFCKeditor.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';


oFCKeditor.Value = '' ; 
oFCKeditor.ReplaceTextarea() ; 
//--> 
</script> 
</html>