<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.whaty.platform.entity.bean.FrequentlyAskedQuestions;" %>
<%
	String d = String.valueOf(Math.random());
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
	</head>
	<body>
		<form
			action=""
			name="form" method="post">
			<div class="" style="width: 100%; padding: 0; margin: 0;"
				align="center">
				<div class="main_title">常见问题库-查看</div>
				<div class="main_txt">
					<table class="datalist" width="100%">
						<s:if test="question != null">
							<tr>
								<th width="30%">问题标题:</th>
								<td>
									<s:property value="question.title"/>
								</td>
							</tr>
							<tr>
								<th>问题分类:</th>
								<td>
									<s:property value="question.types"/>
								</td>
							</tr>
							<tr>
								<th>关键词:</th>
								<td>
									<s:property value="question.keywords"/>
								</td>
							</tr>
							<tr>
								<th>用户类型:</th>
								<td>
									<s:property value="question.roles"/>
								</td>
							</tr>
							<tr style="display:none">
								<th>问题描述:</th>
								<td>
									<s:property value="question.questionDescription"/>
								</td>
							</tr>
							<tr>
								<th>问题解决方案:</th>
									<%
										String desc = ((FrequentlyAskedQuestions)request.getAttribute("question")).getSolution();
										String str = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ";
										String solution = "";
										if(null != desc && !"".equals(desc)){
											if(desc.indexOf(str) != -1) {
												solution = desc.replaceAll(str, "&nbsp;&nbsp;&nbsp;&nbsp;");
											} else {
												solution = desc;
											}
										}
									 %>
								<td align="left"><%=solution%></td>
							</tr>
							<tr style="display:none">
								<th>备注:</th>
								<td>
									<s:property value="question.remarks"/>
								</td>
							</tr>
							<tr>
								<td colspan="2"><input type="button" value="关闭" onclick="javascript:window.close()"/></td>
							</tr>
						</s:if>
					</table>
					<br />
				</div>
			</div>
	</body>
</html>