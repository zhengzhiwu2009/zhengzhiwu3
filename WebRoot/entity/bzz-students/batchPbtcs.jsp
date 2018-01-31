<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page import="org.apache.struts2.ServletActionContext" %>
<%
	String d = String.valueOf(Math.random());
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
		<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
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

.myTable,.myTable td {
	border: 1px solid #cccccc;
	border-collapse: collapse;
}
-->
</style>
</head>

	<body>
		<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td>
					<table width="706" border="0" align="left" cellpadding="0" cellspacing="0">
						<form id="frm2" action="/entity/workspaceStudent/studentWorkspace_searchViewBatchCourse.action" name="form" method="post">
							<input id="endDate" name="endDate" type="hidden" value="<s:property value="#session.endDate" />" />
							<input type="hidden" id="mybatchid" name="mybatchid" value="<s:property value="#session.mybatchid"/>" />
						<tr>
							<td width="706" valign="top">
								<div class="main_title">
									<a href="/entity/workspaceStudent/studentWorkspace_searchViewBatchCourse.action?mybatchid=<s:property value="#session.mybatchid"/>">查看已选课程</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="text-decoration:underline;">查看专项课程</span>
								</div>
							</td>
						</tr>
						<tr align="center">
							<td height="30">
								<table width="100%" border="0" cellpadding="0" cellspacing="0" class="myTable">
									<tr bgcolor="#8dc6f6">
										<td align="center" width="9%">
											课程编号
										</td>
										<td align="center">
											课程名称
										</td>
										<td align="center" width="9%">
											课程性质
										</td>
										<td align="center" width="9%">
											业务分类
										</td>
										<td align="center" width="9%">
											大纲分类
										</td>
										<td align="center" width="9%">
											内容属性
										</td>
										<td align="center" width="6%">
											主讲人
										</td>
										<td align="center" width="6%">
											学时
										</td>
										<td align="center" width="6%">
											期限
										</td>
										<td align="center" width="9%">
											价格(元)
										</td>
										<td align="center" width="9%">
											专项性质
										</td>
									</tr>
									<%
										List list = (List)ServletActionContext.getRequest().getAttribute("batchPbtcList");
										if(null != list && list.size() > 0){
										for(int i = 0; i < list.size(); i++){
											Object[] objArr = (Object[])list.get(i);
									 %>
										<tr>
											<td align="center"><%=objArr[0]==null||"".equals(objArr[0])?"-":objArr[0] %>
											</td>
											<td align="center">
												<a style="color: #3366ff; cursor: pointer;" onclick="window.open('/cms/flkcalone.htm?myId=<%=objArr[11]==null||"".equals(objArr[11])?"-":objArr[11] %>','newwindow_detail')"'><%=objArr[1]==null||"".equals(objArr[1])?"-":objArr[1] %></a>
											</td>
											<td align="center"><%=objArr[2]==null||"".equals(objArr[2])?"-":objArr[2] %>
											</td>
											<td align="center"><%=objArr[3]==null||"".equals(objArr[3])?"-":objArr[3] %>
											</td>
											<td align="center"><%=objArr[4]==null||"".equals(objArr[4])?"-":objArr[4] %>
											</td>
											<td align="center"><%=objArr[5]==null||"".equals(objArr[5])?"-":objArr[5] %>
											</td>
											<td align="center"><%=objArr[6]==null||"".equals(objArr[6])?"-":objArr[6] %>
											</td>
											<td align="center"><%=objArr[7]==null||"".equals(objArr[7])?"-":objArr[7] %>
											</td>
											<td align="center"><%=objArr[8]==null||"".equals(objArr[8])?"-":objArr[8] %>
											</td>
											<td align="center"><%=objArr[9]==null||"".equals(objArr[9])?"-":objArr[9] %>
											</td>
											<td align="center"><%=objArr[10]==null||"".equals(objArr[10])?"-":objArr[10] %>
											</td>
										</tr>
									<%
									}
										}else{
									 %>
									 <tr>
									 	<td colspan="11">加载失败，请重试</td>
									 </tr>
									 <%} %>
								</table>
							</td>
						</tr>
					</table>
				</td>
				<td width="13">
					&nbsp;
				</td>
			</tr>
		</table>
	</body>
</html>