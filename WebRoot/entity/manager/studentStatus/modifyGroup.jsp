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
				document.getElementById('groupName').value = val;
			}
			function checkGroup() {
				var m = document.batch.group.value;
				if(m==null || m=='') {
					alert('您未做分组信息的修改！');
					return false;
				}
				var s = document.batch.selectGroup;
				var value = s.options[s.selectedIndex].value;
				if(m==value) {
					alert('分组信息您未修改！');
					return false;
				}
				if(!confirm('您确定要修改此分组信息吗？')) {
					return false;
				}
				return true;
			}
</script>
	</head>
	<body leftmargin="0" topmargin="0" class="scllbar">
		<form name="batch"
			action="/entity/basic/peBzzStudent_go.action" method="POST"
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
									<td width="23%" valign="bottom" colspan="2">
										<span><font color="red">注：修改分组信息时，从下面的“选择已有分组”中选择已存在的分组，被选中的分组名称将显示在“分组名称”文本框中，如需修改，
										在“分组名称”文本框中，将分组名称修改，然后点击“修改”按钮，那么分组名称将被修改。
										</font></span>
									</td>
									
								</tr>
								<tr valign="bottom" class="postFormBox">
									<td width="23%" valign="bottom">
										<span class="name">分&nbsp;&nbsp;组&nbsp;&nbsp;名&nbsp;&nbsp;称&nbsp;&nbsp;：</span>
									</td>
									<td width="77%">
										<input name="group" id="groupName" type="text" size="15" maxlength="100">
									</td>
								</tr>
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">选择已有分组：</span>
									</td>
									<td valign="bottom">
										<select name ="selectGroup" onchange="getGroupName(this.options[this.selectedIndex].text);" id="group1">
											  <option  value="">--请选择分组--</option>
											  <s:iterator  value="#request.groupList" id="group" >
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
									 <td><input type=submit value = "修改" /> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <input type="button" value="返回" onclick="javascript:window.history.back()" ></td>
								</tr>
							</table>
						</div>

					</td>
				</tr>

			</table>
		</form>
	</body>
</html>