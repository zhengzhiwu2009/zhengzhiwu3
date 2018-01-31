<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.whaty.platform.entity.bean.TeacherInfo;" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>添加常见问题</title>
		<link href="/entity/manager/images/css.css" rel="stylesheet" type="text/css">
	</head>
	<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
	<script type="text/javascript">
		function doSubmit() {
			var content;
			if(FCKeditor_IsCompatibleBrowser()){
				var oEditor = FCKeditorAPI.GetInstance("solution"); 
				content=oEditor.GetXHTML(); 
			}else{
				alert("表单正在下载");
				return false;
			}
			if(document.getElementById('title').value == null || document.getElementById('title').value == '') {
				alert("问题标题不能为空");
				return;
			}
			if(document.getElementById('keyword').value == null || document.getElementById('keyword').value == '') {
				alert("关键词不能为空");
				return;
			}
			if(content.length <1) {
				alert("解决方案不能为空");
				return;
			}
			var roles = document.getElementsByName('role');
			var rolesStr = '';
			var rolesIdsStr = '';
			for(var i = 0; i < roles.length; i++) {
				if(roles[i].checked) {
					rolesStr += roles[i].nextSibling.nodeValue + ',';
					rolesIdsStr += roles[i].value + ',';
				}
			}
			document.getElementById("roles").value = rolesStr.substring(0, rolesStr.length - 1);
			document.getElementById("rolesIds").value = rolesIdsStr.substring(0, rolesIdsStr.length - 1);
			
			var types = document.getElementsByName('type');
			var typesStr = '';
			var typesIdsStr = '';
			var flag = 'false';
			for(var i = 0; i < types.length; i++) {
				if(types[i].checked) {
					flag = 'true';
					typesStr += types[i].nextSibling.nodeValue + ',';
					typesIdsStr += types[i].value + ',';
				}
			}
			if(flag == 'false') {
				alert("问题类型不能为空");
				return;
			}
			document.getElementById("types").value = typesStr.substring(0, typesStr.length - 1);
			document.getElementById("typesIds").value = typesIdsStr.substring(0, typesIdsStr.length - 1);
			document.myForm.action = "frequentlyAskedQuestions_saveQuestion.action";
			document.myForm.submit();
		}
	</script>
	<body>
		<div class="h2">
			<div class="h2div">   
				添加常见问题 
			</div>
		</div>
		<div class="content">
		<form action="" name="myForm">
			<input type="hidden" name="roles" id="roles"/>
			<input type="hidden" name="rolesIds" id="rolesIds"/>
			<input type="hidden" name="types" id="types"/>
			<input type="hidden" name="typesIds" id="typesIds"/>
			<table cellspacing="0" cellpadding="0">
				
					<tr>
						<th width="30%">
							<font color="red">*</font>问题标题：
						</th>
						<td>
							<input type="text" size="50" name="title" id="title" value="">
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">*</font>问题分类：
						</th>
						<td>
							<s:if test="type_list != null && type_list.size() > 0">
								<s:iterator value="type_list" id="type">
									<input type="checkbox" name="type" id="type" value="<s:property value="#type[0]"/>"/><s:property value="#type[1]"/>
								</s:iterator>
							</s:if>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">*</font>关键词：
						</th>
						<td>
							<input type="text" size="50" name="keyword" id="keyword" value="">
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>用户类型：
						</th>
						<td>
							<s:if test="role_list != null && role_list.size() > 0">
								<s:iterator value="role_list" id="role">
									<input type="checkbox" name="role" id="role" value="<s:property value="#role[0]"/>"/><s:property value="#role[1]"/>
								</s:iterator>
							</s:if>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>问题描述：
						</th>
						<td>
							<textarea rows="6" cols="70" name="description" id="description"></textarea>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>备注：
						</th>
						<td>
							<textarea rows="6" cols="70" name="remark" id="remark"></textarea>
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>解决方案：
						</th>
						<td>
							<textarea rows="6" cols="70" name="solution" id="solution"></textarea>
						</td>
					</tr>
					<tr>
						<td colSpan="2" align="center">
							<input type="button" value="提交" onclick="doSubmit()">
						</td>
					</tr>
			</table>
		</form>
		</div>
	</body>
	<script type="text/javascript"> 
<!-- 
// Automatically calculates the editor base path based on the _samples directory. 
// This is usefull only for these samples. A real application should use something like this: 
// oFCKeditor.BasePath = '/fckeditor/' ; // '/fckeditor/' is the default value. 

var oFCKeditor = new FCKeditor( 'solution' ) ; 
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