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

		<script type="text/javascript">
  	window.parent.middle.location.reload();
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
														<strong>缓考申请成功</strong>
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
																	您好，您的缓考申请已提交，请尽快填写书面缓考申请表，由本企业审核签字盖章后，提交至集团一级管理员审核签字盖章上报。请随时关注您的审核状态，缓考和补考时间另行通知。
																	<br/>
																	<font color='red' size='3'>&nbsp;&nbsp;&nbsp;&nbsp;请务必打印提交书面缓考申请表</font>
																</td>
															</tr>
															<br/>
															<br/>
															<tr>
																<td align="center">
																	<input type="button" value="打印缓考申请表"
																		onClick="javascript:window.location = '/entity/bzz-students/exam/exam_late_print.jsp?student_id=<s:property value="peBzzStudent.id" />'" />
																	&nbsp;&nbsp;&nbsp;&nbsp;
																	<input type="button" value="完成"
																		onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_toLateInfo.action'" />
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<img src="/entity/function/images/table_03.gif" width="765"
												height="21">
										</td>
									</tr>
									<tr>

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