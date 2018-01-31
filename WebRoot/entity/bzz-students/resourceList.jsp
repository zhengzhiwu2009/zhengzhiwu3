<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String d = String.valueOf(Math.random());
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

	response.addHeader("Cache-Control", "no-cache");
	response.addHeader("Expires", "Thu, 01 Jan 1970 00:00:01 GMT");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css1.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
	</head>
	<body onload="window.location.href='#top'"><a id="top" name="top" />
		<form action="/entity/workspaceStudent/studentWorkspace_toZiliao.action" name="frm" method="post">
			<div style="width: 100%; padding: 0; margin: 0;" align="center">
				<div class="main_title">
					资料库
				</div>
				<div class="main_txt">
					<table class="datalist" width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#D7D7D7">
						<s:iterator value='#request.resourceList' id='items'>
							<tr bgcolor="#ECECEC">
								<td align="center" bgcolor="#E9F4FF" class="STYLE1" width="120px">
									资料名称：
								</td>
								<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
									<s:property value="#items[0]" />
								</td>
							</tr>
							<tr>
								<td align="center" bgcolor="#E9F4FF" class="STYLE1" width="120px">
									资料分类：
								</td>
								<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
									<s:property value="#items[1]" />
								</td>
							</tr>
							<tr>
								<td align="center" bgcolor="#E9F4FF" class="STYLE1" width="120px">
									资料描述：
								</td>
								<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
									<s:property value="#items[2]" />
								</td>
							</tr>
							<tr>
								<td align="center" bgcolor="#E9F4FF" class="STYLE1" width="120px">
									颁布/发布单位：
								</td>
								<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
									<s:property value="#items[3]" />
								</td>
							</tr>
							<tr>
								<td align="center" bgcolor="#E9F4FF" class="STYLE1" width="120px">
									颁布/发布时间：
								</td>
								<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
									<s:property value="#items[4]" />
								</td>
							</tr>
						</s:iterator>
					</table>
					<table class="datalist" width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#D7D7D7">
						<tr bgcolor="#ECECEC">
							<td align="center" bgcolor="#E9F4FF" class="STYLE1">
								<b>资料附件</b>
							</td>
						</tr>
						<s:if test="#request.filelink==null || #request.filelink.size() <= 0">
							<tr>
								<td align="center" bgcolor="#FAFAFA" class="kctx_zi2">
									暂无数据！
								</td>
							</tr>
						</s:if>
						<s:else>
							<s:iterator value='#request.filelink' id='filelinks'>
								<tr>
									<td align="center" bgcolor="#FAFAFA" class="kctx_zi2">
										<a href="<s:property value='#filelinks[0]' />"><s:property value="#filelinks[1]" /></a>
									</td>
								</tr>
							</s:iterator>
						</s:else>
					</table>
					<table class="datalist" width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#D7D7D7">
						<tr bgcolor="#ECECEC">
							<td align="center" bgcolor="#E9F4FF" class="STYLE1" colspan="2">
								<b>关联课程</b>
							</td>
						</tr>
						<tr bgcolor="#E9F4FF">
							<td>课程编号</td>
							<td>课程名称</td>
						</tr>
						<s:if test="page != null">
							<s:iterator value="page.items" id="opencourse">
								<tr>
									<td align="center" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:property value="#opencourse[2]" />
									</td>
									<td align="center" bgcolor="#FAFAFA" class="kctx_zi2">
										<a style="color: #3366ff; cursor: pointer;" href="/cms/flkcalone.htm?myId=<s:property value="#opencourse[0]"/>" target="blank"><s:property value="#opencourse[1]" /></a>
									</td>
								</tr>
							</s:iterator>
						</s:if>
						<s:else>
							<tr>
								<td align="center" bgcolor="#FAFAFA" class="kctx_zi2">
									暂无数据！
								</td>
							</tr>
						</s:else>
						<tr>
							<td align="center" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
								<div id="fany">
									<script language="JavaScript">
								      	<s:if test="page != null">
											var page1 = new showPages("page1",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
										</s:if>
										<s:else>
											var page1 = new showPages("page1",<s:property value="startIndex" />,<s:property value="pageSize" />,0,"");
										</s:else>
										page1.printHtml();
									</script>
								</div>
							</td>
						</tr>
					</table>
					<table class="datalist" width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#D7D7D7">
						<tr bgcolor="#ECECEC">
							<td align="center" bgcolor="#E9F4FF" class="STYLE1" colspan="4">
								<b> 相关课程资料 </b>
							</td>
						</tr>
						<tr bgcolor="#ECECEC">
							<td align="center" bgcolor="#E9F4FF" class="STYLE1">
								资料名称
							</td>
							<td align="center" bgcolor="#E9F4FF" class="STYLE1" width="100px">
								资料分类
							</td>
							<td align="center" bgcolor="#E9F4FF" class="STYLE1" width="100px">
								颁布/发布单位
							</td>
							<td align="center" bgcolor="#E9F4FF" class="STYLE1" width="100px">
								颁布/发布时间
							</td>
						</tr>
						<s:if test="#request.resourceListc==null || #request.resourceListc.size() <= 0">
							<tr>
								<td align="center" bgcolor="#FAFAFA" class="kctx_zi2" colspan="4">
									暂无数据！
								</td>
							</tr>
						</s:if>
						<s:else>
							<s:iterator value='#request.resourceListc' id='czl'>
								<tr>
									<td align="center" bgcolor="#FAFAFA" class="kctx_zi2">
										<a href="/entity/workspaceStudent/studentWorkspace_toresourceList.action?perid=<s:property value="#czl[0]" />"> <s:property value="#czl[1]" /> </a>

									</td>
									<td align="center" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:property value="#czl[2]" />
									</td>
									<td align="center" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:property value="#czl[3]" />
									</td>
									<td align="center" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:property value="#czl[4]" />
									</td>
								</tr>
							</s:iterator>
						</s:else>
					</table>
				</div>
				<div style="height:20px;">
					<input type="button" value="返  回" onclick="window.location='/entity/workspaceStudent/studentWorkspace_toZiliao.action'" /><br />
				</div>
				<div>&nbsp;</div>
			</div>
		</form>
	</body>
</html>