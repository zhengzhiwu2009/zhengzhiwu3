<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
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
	<script type="text/javascript">
		function searSubmit(){
			document.forms.form.startIndex.value= 0;
			document.forms.form.submit();
		}
		function clearTime() {
			document.getElementById('title').value = '';
			document.getElementById('type').value = '';
			document.getElementById('keywords').value = '';
		}
		function listSelect(){
			var ids = document.getElementsByName("idaaa");
			var listSelectAll = document.getElementById("listSelectAll");
			if(listSelectAll.checked) {
				for (var i = 0 ; i < ids.length; i++) {
					ids[i].checked = true;
				}
			} else {
				for (var i = 0 ; i < ids.length; i++) {
					ids[i].checked = false;
				}
			}
		}
</script>
	<body>
		<form action="entity/workspaceStudent/studentWorkspace_questionAdvice.action" name="form" method="post">
			<input type="hidden" name="startIndex" value="<s:property value="startIndex" />" />
			<input type="hidden" name="pageSize" value="<s:property value="pageSize" />" />
			<input type="hidden" name="ids" id="ids" value="" />
			<div style="width: 100%; padding: 0; margin: 0;" align="center">
				<div class="main_title">
					问题及建议
				</div>
			<div class="msg" style="height:auto;">
				<table border="0" class="msg" width="100%" style="border:0px;">
					<tr>
						<td>
							<b>1、点击"我要提问/建议"按钮填写您的意见或建议。</b>
						</td>
					</tr>
					<tr>
						<td>
							<b>2、点击"问题/建议标题"查看您填写的问题/建议以及管理员的回复。</b>
							<div style="float:right;"><input style="margin-right:30px;width:120px;height:32px;font-weight:bold;font-size:14px" type="button" value="我要提问/建议" onclick="window.location.href='/entity/workspaceStudent/studentWorkspace_addQuestionAdvice.action'" /></div>
						</td>
					</tr>
				</table>
			</div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="10%">
							&nbsp;
						</td>
						<td align="left">
							问题标题：
							<input type=text style="width: 150px" id="title" name="title" value="<s:property value="title" />" />
						</td>
						<td align="left">
							问题分类：
							<select id="type" name="type">
								<option value="" selected>
									全部
								</option>
								<s:iterator value="issueType" id="type">
									<s:if test="#type.id == type">
										<option value="<s:property value="#type.id" />" selected>
											<s:property value="#type.name" />
										</option>
									</s:if>
									<s:else>
										<option value="<s:property value="#type.id" />">
											<s:property value="#type.name" />
										</option>
									</s:else>
								</s:iterator>
							</select>
						</td>
						<td align="right">
							<input type="button" value="&nbsp;查&nbsp;找&nbsp;" onclick="searSubmit()" />
							<input type="button" value="&nbsp;清&nbsp;空&nbsp;" onclick="clearTime();" />
						</td>
					</tr>
				</table>
				<div class="main_txt">
					<table class="datalist" width="100%">
						<tr>
							<td colspan="4" align="left">
								<div id="fany">
									<script language="JavaScript">
		var page2 = new showPages("page2",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
		page2.printHtml();
		</script>
								</div>
							</td>
						</tr>
						<tr bgcolor="#8dc6f6">
							<th align="center">
								问题标题
							</th>
							<th width="14%" align="center">
								问题分类
							</th>
							<th width="14%" align="center">
								提问时间
							</th>
							<th width="16%" align="center">
								最后一次回复时间
							</th>
						</tr>
						<s:if test="page != null">
							<s:iterator value="page.items" id="cp" status="st">
								<tr>
									<td height="30">
										<a href="/entity/workspaceStudent/studentWorkspace_showQuestionAdvice.action?qid=<s:property value="#cp[0]" />"><s:property value="#cp[1]" /> </a>
									</td>
									<td>
										<s:property value="#cp[2]" />
									</td>
									<td>
										<s:property value="#cp[3]" default="-" />
									</td>
									<td>
										<s:property value="#cp[4]" default="-" />
									</td>
								</tr>
							</s:iterator>
						</s:if>
						<s:else>
							<tr>
								<td colspan="4">
									--暂无数据--
								</td>
							</tr>
						</s:else>
						<tr>
							<td colspan="4" align="left">
								<div id="fany">
									<script language="JavaScript">
										var page1 = new showPages("page1",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
										page1.printHtml();
									</script>
								</div>
							</td>
						</tr>
					</table>
					<br />
				</div>
			</div>
		</form>
	</body>
</html>