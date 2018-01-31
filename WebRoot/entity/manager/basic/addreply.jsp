
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%><%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="java.util.List" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>问题及建议-回复</title>
		<link href="/entity/manager/images/css.css" rel="stylesheet" type="text/css">
		<script language="javascript" src="/entity/bzz-students/css/jquery.js"></script>
		<script type="text/javascript">
			function checkAndSubmit(){
				var reply = $("#reply").val();
				if("undefined" == reply || "" == reply){
					alert("请输入回复内容");
					return;
				}
				$("#form").submit();
			}
		</script>
	</head>
	<body>
		<div class="h2">
			<div class="h2div">
				问题及建议-回复
			</div>
		</div>
		<div class="content">
			<%
				UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			%>
			<form id="form" name="form" action="problemsManager_reply.action" method="post" enctype="multipart/form-data">
				<table class="datalist" width="100%">
					<s:iterator value="#request.issueDetail" id="cp" status="st">
						<tr>
							<th bgcolor="#8dc6f6" width="10%">
								问题标题：
							</th>
							<td align="left" colspan="5">
								<s:property value="#cp[0]" />
								<input type="hidden" id="qaid" name="qaid" value="<s:property value="#cp[7]" />" />
							</td>
						</tr>
						<tr>
							<th bgcolor="#8dc6f6" width="10%">
								问题分类：
							</th>
							<td align="left" colspan="3">
								<s:property value="#cp[1]" />
							</td>
							<th bgcolor="#8dc6f6" width="10%">
								提问时间：
							</th>
							<td align="left">
								<s:property value="#cp[2]" />
							</td>
						</tr>
						<%
							if (null != us.getUserLoginType() && "3".equals(us.getUserLoginType())) {
						%>
						<tr>
							<th bgcolor="#8dc6f6" width="10%">
								提问者姓名：
							</th>
							<td align="left" width="30%">
								<s:property value="#cp[3]" />
							</td>
							<th bgcolor="#8dc6f6" width="10%">
								提问者角色：
							</th>
							<td align="left" width="20%">
								<s:property value="#cp[4]" />
							</td>
							<th bgcolor="#8dc6f6" width="10%">
								联系方式：
							</th>
							<td align="left">
								<s:property value="#cp[5]" />
							</td>
						</tr>
						<%
							}
						%>
						<tr>
							<th bgcolor="#8dc6f6">
								问题描述：
							</th>
							<td colspan="5" align="left">&quot;
								<s:property value="#cp[6]" />
							</td>
						</tr>
						<tr>
							<th bgcolor="#8dc6f6">
								附件：
							</th>
							<td colspan="5" align="left">
								<table width="100%">
									<s:iterator value="fileList" id="file" status="i">
										<tr>
											<td><a href="/entity/workspaceStudent/studentWorkspace_downloadFile.action?fileName=<s:property value="#file[1]"/>"><s:property value="#file[1]"/></a></td>
											<td>
												<%
													List replyList = (List)request.getAttribute("replyList");
													if (null != us.getUserLoginType() && "3".equals(us.getUserLoginType()) && replyList.size() > 0) {
												%>
													<a href="problemsManager_deleFile.action?fileName=<s:property value="#file[0]"/>&qaid=<s:property value="#cp[7]"/>">删除</a>
												<%} %>
											</td>
										</tr>
										
									</s:iterator>
								</table>
							</td>
						</tr>
					</s:iterator>
					<s:if test="#request.replyList.size() > 0">
						<tr>
							<td colspan="6">
								<s:if test="#request.replyList.size() > 3">
									<div style="width: 100%; height: 200px; overflow-y: auto;">
								</s:if>
								<s:else>
									<div style="width: 100%; overflow-y: auto;">
								</s:else>
								<table width="100%">
									<s:iterator value="#request.replyList" id="rp" status="st">
										<tr>
											<td align="left" style="word-break: break-all;">
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
				</table>
				<%
					if (null != us.getUserLoginType() && "3".equals(us.getUserLoginType())) {
				%>
				<table cellspacing="0" cellpadding="0">
					<tr>
						<th>
							回复：
						</th>
						<td>
							<textarea cols="120" rows="10" id="reply" name="reply"></textarea>
						</td>
					</tr>
				</table>
				<%
					}
				%>
				<div>
					<div class="buttbox">
						<%
							if (null != us.getUserLoginType() && "3".equals(us.getUserLoginType())) {
						%>
						<input type="button" name="Submit" value="回复" onclick="checkAndSubmit()" />
						<%
							}
						%>
						<input type="button" value="返回" onclick="javascript:window.location.href='/entity/basic/problemsManager.action'">
					</div>
				</div>
			</form>
		</div>
	</body>
</html>