<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="/entity/manager/images/css.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" media="all" href="/js/calendar/calendar-win2000.css" />
	</head>
	<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
	<script type="text/javascript" src="/js/calendar/calendar.js"></script>
	<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
	<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
	<script language="javascript" src="/entity/bzz-students/css/jquery.js"></script>
	<script type="text/javascript">
		function doSubmit(){
			if(document.getElementById('organCode').value ==null || document.getElementById('organCode').value ==''){
			alert('机构名称不能为空');
			return ;
			}
			if(document.getElementById('name').value ==null || document.getElementById('name').value ==''){
			alert('机构名称不能为空');
			return;
			}
			if(document.getElementById('organType').value ==null || document.getElementById('organType').value ==''){
			alert('机构类型不能为空');
			return;
			}
			document.myForm.submit();
		}
	</script>
	<script type="text/javascript">
		
	</script>
	<body>
		<div class="h2">
			<div class="h2div">   
				添加一级集体
			</div>
		</div>
		<div class="content">
		<form action="enterpriseManager_saveEnterprise.action" name="myForm" method="post" enctype="multipart/form-data">
			<table cellspacing="0" cellpadding="0">
					<tr>
						<th width="30%" align="right">
							<font color="red">*</font>账号：
						</th>
						<td>
							<input type="text" size="50" name="organCode" id="organCode">
						</td>
					</tr>
					<tr>
						<th align="right">
							<font color="red">*</font>机构名称：
						</th>
						<td>
							<input type="text" size ="50" name ="organName" id ="name" >
							
						</td>
					</tr>
					<tr>
						<th align="right">
							<font color="red">*</font>机构类型：
						</th>
						<td>
							<select name ="enTypeId" id ="organType" >
									<option value ="" id ="">--请选择--</option>
								<s:iterator value ="#request.organTypeList" id = "type" >
									<option value ="<s:property value="#type.id" />" ><s:property value="#type.name" />  </option>
								</s:iterator>
								
							</select>
							
						</td>
					</tr>
					
					<tr>
						<td colSpan="2" align="center">
							<input type="button" id="tijiao" value="保存" onclick="javascript: doSubmit()"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="返回" onclick="javascript:window.history.back()">
						</td>
					</tr>
			</table>
		</form>
		</div>
	</body>
	
</html>