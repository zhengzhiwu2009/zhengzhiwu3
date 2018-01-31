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
		<title>准考证打印</title>
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
	</head>
	<body leftmargin="0" topmargin="0"
		background="/entity/function/images/bg2.gif">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bgcolor="#FFFFFF">
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
														<strong>准考证打印</strong>
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
											<table width="90%" border="0" align="center" cellpadding="0"
												cellspacing="1" class="bg4" bgcolor="#D7D7D7">
												<tr bgcolor="#ECECEC">
													<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
														<strong>准考证打印：</strong>
													</td>
													<s:if test="peBzzExamScore == null ">
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
															width=300>
															&nbsp; 还未报名
														</td>
													</s:if>
													<s:elseif
														test="peBzzExamScore.testcard_id==null || peBzzExamScore.room_id==null || peBzzExamScore.desk_no==null">
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
															width=300>
															&nbsp;考试信息还未录入，请等待！
														</td>
													</s:elseif>
													<s:else>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
															width=300>
															&nbsp;
															<a
																href="/entity/workspaceStudent/bzzstudent_printTestCard.action"
																target="_blank"><font color="red"><b>打印</b> </font>
															</a>
														</td>
													</s:else>
												</tr>
											</table>
											<table width="90%" border="0" align="center" cellpadding="0"
												cellspacing="1" class="bg4" bgcolor="#D7D7D7">
												<tr bgcolor="#ECECEC">
													<td bgcolor="#FAFAFA">
														<br/>
														<font color="red" size="3">打印注意事项：</font>
														<br/>
														1、点击“打印”后进入准考证打印页面
														<br/>
														2、在打印页面，点击“页面设置”
														<br/>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<img src="/entity/function/images/testcard1.jpg" alt="" />
														<br/>
														3、将其中的页眉和页脚的设置都置为“空”，并且将左右边距设为19.05，上下边距设为5
														<br/>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<img src="/entity/function/images/testcard2.jpg" alt="" />
														<br/>
														4、点击“确定”后再点击浏览器的“打印”按钮即可打印
														<br/>
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
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>