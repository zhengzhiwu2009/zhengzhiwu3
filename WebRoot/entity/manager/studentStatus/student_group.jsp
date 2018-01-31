<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>tableType_2</title>
		<link rel="stylesheet" href="/entity/manager/css/admincss.css"
			type="text/css"></link>
		<script>
			
			function getGroupName(val) {
				document.getElementById('group1').value = val;
			}
			function checkGroup() {
				var val1 = document.getElementById('groupName').value;
				var val2 = document.getElementById('group1').value;
				if(val1=="" && val2=="") {
					alert('请填写分组信息');
					return false;
				}
				if (val1!="" && val2!="") {
					alert("只能选择一项填写") ;
					return false;
				}
				return true;
			}
</script>
	</head>
	<body leftmargin="0" topmargin="0" class="scllbar">
		<form name="batch"
			action="<s:property value='servletPath'/>_doGroup.action" method="POST"
			onsubmit="return checkGroup();">
			<input type="hidden" name="flag" value="<s:property value="flag"/>"/>
			<table width="80%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td>
						<div class="content_title" id="zlb_title_start">
							学员分组
						</div id="zlb_title_end">
					</td>
				</tr>


				<tr>
					<td valign="top" class="pageBodyBorder_zlb" align="center">
						<div class="cntent_k" id="zlb_content_start">
							<p>
								&nbsp;
							</p>
							<table width="68%" border="0" cellpadding="0" cellspacing="0">

								<tr valign="bottom" class="postFormBox">
									<td width="23%" valign="bottom">
										<span class="name">新建分组名称：</span>
									</td>
									<td width="77%">
										<input type="radio" name="1" checked="true" style="display:none"/><input name="group" id="groupName" type="text" size="15" maxlength="15">
									</td>
								</tr>
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">选择已有分组：</span>
									</td>
									<td valign="bottom">
										<select name ="selectGroup" onchange="getGroupName(this.options[this.selectedIndex].text);" id="group1">
											  <option  value="">--请选择分组--</option>
											  <s:iterator  value="groupList" id="group" >
											  <option value="<s:property value="group"/>"><s:property value="group"/></option>
  											</s:iterator >
										</select>
									</td>
								</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
								<tr class="postFormBox">
									<td></td>
									 <td><input type=submit value = "确定" /> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <input type="button" value="返回" onclick="javascript:window.history.back()" ></td>
								</tr>
							</table>
						</div>

					</td>
				</tr>

			</table>
		</form>
	</body>
</html>