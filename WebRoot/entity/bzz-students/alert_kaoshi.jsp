<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String d = String.valueOf(Math.random());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.STYLE1 {
	color: #FF0000;
	font-weight: bold;
}

.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}
-->
</style>
	</head>

	<body>
		<table width="1002" border="0" align="center" cellpadding="0"
			cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td>
					<table width="1002" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<IFRAME NAME="top" width=100% height=200 frameborder=0
							marginwidth=0 marginheight=0
							SRC="/entity/bzz-students/pub/top.jsp" scrolling=no
							allowTransparency="true"></IFRAME>
						<tr>
							<td height="13"></td>
						</tr>
					</table>
					<table width="1002" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="237" valign="top">
								<IFRAME NAME="leftA" width=237 height=580 frameborder=0
									marginwidth=0 marginheight=0
									SRC="/entity/bzz-students/pub/left.jsp?d=<%=d%>" scrolling=no
									allowTransparency="true"></IFRAME>
							</td>
							<td width="752" valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr align="center" valign="top">
										<td height="17" align="left" class="twocentop">
											<img src="/entity/bzz-students/images/two/1.jpg" width="11"
												height="12" />
											当前位置：考试信息
										</td>
									</tr>
									<tr>
										<td>
											<br/>
											<img name="two2_r5_c5"
												src="/entity/bzz-students/images/two/two2_r5_c5.jpg"
												width="750" height="72" border="0" id="two2_r5_c5" alt="" />
										</td>
									</tr>

									<tr>
										<td align="left">
											<font size=2><br/>
												<font color=red>考试信息说明</font>：<br/>
												<br/>目前还未进入考试期间，请于学期最后一个月关注网站公告，根据公告访问本栏目，主要服务内容如下：<br/>
												<br/> 1． 查询考试时间、考试地点、考试要求及注意事项；<br/>
												<br/> 2． 确认准考证个人信息，以保证准考证有效，顺利参加考核认证；<br/>
												<br/> 3． 下载打印准考证；<br/>
												<br/> 4． 考试成绩发布后，查询考试成绩； </br>
											</font>
										</td>
									</tr>

								</table>
							</td>
							<td width="13">
								&nbsp;
							</td>
						</tr>
					</table>
					<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0
						marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp"
						scrolling=no allowTransparency="true" align=center></IFRAME>
				</td>
			</tr>
		</table>
	</body>
</html>