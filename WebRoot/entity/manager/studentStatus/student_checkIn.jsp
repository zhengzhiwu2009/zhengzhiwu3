<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>tableType_2</title>
		<link rel="stylesheet" href="/entity/manager/css/admincss.css"
			type="text/css"></link>
	</head>
	<%!
		String fixnull(String str) {
			if(str == null) 
				str = "";
			return str;
		}
	 %>
	<%
		String enterpriseIds = fixnull((String)request.getAttribute("enterpriseIds"));
	 %>
	<script type="text/javascript">
		function FileTypeCheck() {
			var peEnterpriseId = $("#peEnterpriseId").val();
			var id1 = '<%=enterpriseIds%>';
			var ids = id1.split(",");
			var flag = "";
			for (var i=0;i<ids.length;i++) {
				if (ids[i]==peEnterpriseId) {
					flag = "1"
				}
			}
			if (flag=="1") {
				alert("已在当前机构的学员不能重复分配！");
				return false;
			}
		}
	</script>
	<body leftmargin="0" topmargin="0" class="scllbar">
		<form name="batch"
			action="<s:property value='servletPath'/>_checkIn.action" method="POST"
			enctype="multipart/form-data" onsubmit="return FileTypeCheck();">
			<input type="hidden" name="flag" value="<s:property value="flag"/>"/>
			<table width="90%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td>
						<div class="content_title" id="zlb_title_start">
							学员签入
						</div id="zlb_title_end">
					</td>
				</tr>
				<tr align="center">
					<td valign="top" class="pageBodyBorder_zlb" align="center">
						<div class="cntent_k" id="zlb_content_start">
							<p>
								&nbsp;
							</p>
							<table width="100%" border="0" cellpadding="0" cellspacing="0">

								<tr valign="bottom" class="postFormBox">
									<td width="23%" valign="bottom" align="center">
										<span class="name">选择签入机构:</span>
									</td>
									<td width="77%" align="left">
										<select name ="peEnterpriseId" id="peEnterpriseId">
											  <s:iterator  value="peEnterpriseList" id="a" >
											  <option value="<s:property value="#a.id"/>" default="--请选择--"><s:property value="#a.name"/></option>
  											</s:iterator >
										</select>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr class="postFormBox">
									<td></td>
									 <td><input type=submit value = "确定" /> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <input type="button" value="返回" onclick="javascript:window.history.back()" ></td>
								</tr>
			</table>
		</form>
	</body>
</html>