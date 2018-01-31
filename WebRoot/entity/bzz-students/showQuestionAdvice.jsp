<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.whaty.platform.entity.bean.FrequentlyAskedQuestions;"%>
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
	</head>
	<body>
		<form action="" name="form" method="post">
			<div class="" style="width: 100%; padding: 0; margin: 0;" align="center">
				<div class="main_title">
					问题及建议
				</div>
				<div class="main_txt">
					<table class="datalist" width="100%">
						<s:iterator value="issueDetail" id="cp" status="st">
							<tr>
								<th width="10%">
									问题标题：
								</th>
								<td align="left" colspan="3">
									<s:property value="#cp[0]" />
								</td>
							</tr>
							<tr>
								<th width="10%">
									问题分类：
								</th>
								<td align="left" width="60%">
									<s:property value="#cp[1]" />
								</td>
								<th width="10%">
									提问时间：
								</th>
								<td align="left">
									<s:property value="#cp[3]" />
								</td>
							</tr>
							<tr>
								<th>
									问题描述：
								</th>
								<td colspan="3" align="left">
									<s:property value="#cp[4]" />
								</td>
							</tr>
							<tr>
								<th>
									附件：
								</th>
								<td colspan="3" align="left">
									<s:iterator value="fileList" id="file" status="i">
										<a href="/entity/workspaceStudent/studentWorkspace_downloadFile.action?fileName=<s:property value="#file[0]"/>"><s:property value="#file[1]"/></a>
										<s:if test="fileList.size() > 1 && (#i.index + 1) != fileList.size()">
											<br/>
										</s:if>
									</s:iterator>
								</td>
							</tr>
						</s:iterator>
						<s:if test="replyList.size() > 0">
							<tr>
								<td colspan="6">
									<table width="100%">
										<s:iterator value="replyList" id="rp" status="st">
											<tr>
												<td align="left">
													<s:property value="#rp[0]" />
													<div style="float: right; color: red;">
														<s:property value="#rp[1]" />
														-
														<s:property value="#rp[2]" />
													</div>
												</td>
											</tr>
										</s:iterator>
									</table>
								</td>
							</tr>
						</s:if>
						<tr>
							<td colspan="6" align="center">
								<input type="button" value="返回" onclick="history.go(-1)" />
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>
	</body>
</html>