<%@ page language="java" import="java.net.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page isELIgnored="false"%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.whaty.platform.entity.*,com.whaty.platform.entity.user.*,com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@page import="com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool" />
<jsp:directive.page
	import="com.whaty.platform.database.oracle.MyResultSet" />

<%!String fixnull(String str) {
		if (str == null || str.equals("null") || str.equals("")) {
			str = "";
			return str;
		}
		return str;
	}%>


<%!String fixnull1(String str) {
		if (str == null || str.equals("null") || str.equals("")) {
			str = "";
			return str;
		}
		return str;
	}%>

<%
	dbpool db = new dbpool();
	String sql = "";
	MyResultSet rs = null;
%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title></title>
		<link href="/entity/function/css/css.css" rel="stylesheet"
			type="text/css">
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.STYLE1 {
	font-size: 12px;
	color: #0041A1;
	font-weight: bold;
}

.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}

.kctx_zi1 {
	font-size: 12px;
	color: #0041A1;
}

.kctx_zi2 {
	font-size: 12px;
	color: #54575B;
	line-height: 24px;
	padding-top: 2px;
}
-->
</style>
		<script type="text/javascript">
function check(){
	var examSiteId = document.getElementById("examSiteId").value;
	var num  = document.getElementById(examSiteId+"num").value;
	if(num>0){
		document.search.submit();
	}else{
		alert("该考点容量已满，请选择其他考点");
	}
}
</script>
	</head>
	<body leftmargin="0" topmargin="0"
		background="/entity/function/images/bg2.gif">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="top" class="bg3">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="45" valign="top"></td>
						</tr>
						<tr>
							<td align="center" valign="top">
								<table width="765" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td height="65"
											background="/entity/function/images/table_01.gif">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td align="center" class="text1">
														<img src="/entity/function/images/xb3.gif" width="17"
															height="15">
														<strong>考试地点</strong>
													</td>
													<td width="300">
														&nbsp;
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td background="/entity/function/images/table_02.gif">
											<form name=search id="search"
												action="/entity/workspaceStudent/bzzstudent_toBaoMi.action"
												method="post">
												<table width="70%" border="0" align="center" cellpadding="0"
													cellspacing="1" class="bg4" bgcolor="#D7D7D7">
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
															<strong>考点：</strong>
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
															width=300>
															<select name="examSiteId" id="examSiteId">
																<s:iterator value="peBzzExamBatchSites" id="examSite"
																	status="u">
																	<option value="${examSite.id}"
																		<s:if test='%{peBzzExamScore.peBzzExamBatchSite.peBzzExamSite.name==#examSite.name}'>selected</s:if>>
																		${examSite.peBzzExamSite.name}&nbsp;&nbsp;&nbsp;&nbsp;
																		总容量：${examSite.peBzzExamSite.capacity}&nbsp;&nbsp;&nbsp;&nbsp;
																		剩余容量：${examSite.peBzzExamSite.capacity-yongs[u.index]}
																	</option>
																</s:iterator>
															</select>
															<s:iterator value="peBzzExamBatchSites" id="examSite"
																status="u">
																<input type="hidden" id="${examSite.id}num"
																	value='${examSite.peBzzExamSite.capacity-yongs[u.index]}' />
															</s:iterator>
														</td>
													</tr>
												</table>
												<table border="0" align="center" cellpadding="0"
													cellspacing="0">
													<tr>
														<td>
															&nbsp;
														</td>
													</tr>
													<tr>
														<td align="center" width="600">
															<input type="button" onclick="check()" value="提交" />
															&nbsp;&nbsp;&nbsp;&nbsp;
															<input type="button" value="返回"
																onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_examSiteExplain.action'" />
														</td>
													</tr>
												</table>
											</form>
										</td>
									</tr>
									<tr>
										<td>
											<img src="/entity/function/images/table_03.gif" width="765"
												height="21">
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>