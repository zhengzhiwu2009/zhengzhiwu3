<%@ page language="java" import="java.net.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.whaty.platform.entity.*,com.whaty.platform.entity.user.*,com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@page import="com.whaty.platform.database.oracle.*"%>




<%!String fixnull1(String str) {
		if (str == null || str.equals("null") || str.equals("")) {
			str = "";
			return str;
		}
		return str;
	}%>


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title></title>
		<link href="/entity/function/css/css.css" rel="stylesheet"
			type="text/css">
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
														<strong>缓考说明</strong>
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
											<table width="95%" border="0" align="center" cellpadding="0"
												cellspacing="0" class="bg4">
												<tr>
													<td class="text2">
														<table border="0" align="center" cellpadding="0"
															cellspacing="0" width=77%>
															<tr>
																<td>
																	<s:if test="peBzzExamBatch.late_explain == null ">暂无</s:if>
																	<s:else>
																		<s:property value="peBzzExamBatch.late_explain"
																			escape="false" />
																	</s:else>

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
																	<input type="button" value="下一步"
																		onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_toLateInfoN.action'" />
																	<!-- <input type="button" value="下一步"  onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_latestudentInfo.action'"/> -->
																	&nbsp;&nbsp;&nbsp;&nbsp;
																	<input type="button" value="返回"
																		onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_examBat.action'" />
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td>
														<img src="/entity/function/images/table_03.gif"
															width="765" height="21">
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
	</body>
</html>