<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page import="java.util.*,java.text.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<?xml version="1.0" encoding="gb2312"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>中国证劵业协会培训平台</title>
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
		<link href="/web/bzz_index/style/index.css" rel="stylesheet"
			type="text/css">
		<link href="/web/bzz_index/style/xytd.css" rel="stylesheet"
			type="text/css">
		<link href="/web/bzz_index/css.css" rel="stylesheet" type="text/css" />
		<link href="/web/css/css.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
<!--
.STYLE1 {
	color: #FF9000;
	font-weight: bold;
}

.link a {
	color: #3399cc;
}
-->
</style>
	</head>
	<Script>
function toadd(){
 window.location.href="<%=request.getContextPath()%>/entity/bzz-students/student_newcourse.jsp";
}
function toljzf(){
 window.open("<%=request.getContextPath()%>/entity/bzz-students/ljzf.jsp");
}
</Script>
	<body>
		<table width="918" align="center" border="0" cellspacing="0"
			cellpadding="0" height=150>
			<tr valign=top height=150>
				<td>
					<img src="/web/bzz_index/images/top_02.jpg" border="0">
				</td>
			</tr>
			<table>
				<table width="1000" align="center" border="0" cellspacing="0"
					cellpadding="0" bgcolor="#FFFFFF">
					<tr valign=top height=30>
						<td align=left valign=bottom>
							<font size=2><b>我选择的课程:</b> </font>
						</td>
					</tr>
					<tr align="center">
						<td height="29"
							background="/entity/bzz-students/images/two/two2_r15_c5.jpg">
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								class="twocencetop">
								<tr>
									<td width="5%" align="center">
										<input type=checkbox>
									</td>
									<td width="10%" align="center">
										课程编号
									</td>
									<td width="20%" align="center">
										课称名称
									</td>
									<td width="10%" align="center">
										课程性质
									</td>
									<td width="10%" align="center">
										课程学时
									</td>
									<td width="10%" align="center">
										价格
									</td>
									<td width="10%" align="center">
										删除课程
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr height=30>
						<td>
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								class="twocencetop">
								<tr>
									<td width="5%" align="center">
										<input type=checkbox>
									</td>
									<td width="10%" align="center">
										c12008
									</td>
									<td width="20%" align="center">
										《证券公司合规管理有效性评估指引》解读
									</td>
									<td width="10%" align="center">
										必修
									</td>
									<td width="10%" align="center">
										3学时
									</td>
									<td width="10%" align="center">
										40
									</td>
									<td width="10%" align="center">
										删除课程
									</td>
								</tr>
								<tr height=30>
									<td>
								<tr>
									<td width="5%" align="center">
										<input type=checkbox>
									</td>
									<td width="10%" align="center">
										c12008
									</td>
									<td width="20%" align="center">
										《证券公司合规管理有效性评估指引》解读
									</td>
									<td width="10%" align="center">
										必修
									</td>
									<td width="10%" align="center">
										3学时
									</td>
									<td width="10%" align="center">
										40
									</td>
									<td width="10%" align="center">
										删除课程
									</td>
								</tr>
								<tr height=30>
									<td>
								<tr>
									<td width="5%" align="center">
										<input type=checkbox>
									</td>
									<td width="10%" align="center">
										c12008
									</td>
									<td width="20%" align="center">
										《证券公司合规管理有效性评估指引》解读
									</td>
									<td width="10%" align="center">
										必修
									</td>
									<td width="10%" align="center">
										3学时
									</td>
									<td width="10%" align="center">
										40
									</td>
									<td width="10%" align="center">
										删除课程
									</td>
								</tr>
								<tr height=40>
									<td colspan=3 width=20% align=center>
										您报名的课程的必修总:5学时
									</td>
									<td colspan=2 width=20% align=center>
										选修总学时:2学时
									</td>
									<td colspan=2 width=20% align=center>
										价格小计：140元
									</td>
								</tr>
								<tr height=40>
									<td width=5%></td>
									<td colspan=3 width=20% align=center>
										<input type=button value="继续选课" onclick=toadd()>
									</td>
									<td colspan=3 width=20% align=center>
										<input type=button value="立即支付" onclick=toljzf()>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					</tr>

				</table>
				<table width="1000" border="0" cellspacing="0" cellpadding="0"
					align="center">
					<tr>
						<td width="10" height="78">
							<img src="/web/news/images/news_15.jpg" border="0">
						</td>
						<td background="/web/news/images/news_17.jpg" valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td align="center" class="close">
										<div style="cursor: pointer;"
											onClick="window.opener=null;window.close()">
											【关 闭】
										</div>
									</td>
								</tr>
								<tr>
									<td align="center" style="padding-top: 4px;">
										<span class="down">版权所有：中国证劵业协会 <BR>
											投诉：010-11110000 传真：010-11116666 咨询服务电话：010-62415115</span>
									</td>
								</tr>
							</table>
						</td>
						<td width="10" height="78">
							<img src="/web/news/images/news_20.jpg" border="0">
						</td>
					</tr>
				</table>
	</body>
</html>
