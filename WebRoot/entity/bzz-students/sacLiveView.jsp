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
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
		<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
		<META HTTP-EQUIV="expires" CONTENT="0">
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css1.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
	</head>
	<body>
		<div style="width: 100%; padding: 0; margin: 0;" align="center">
			<div class="main_title">
				直播详情
			</div>
			<div class="main_txt">
				<table width="100%" align="left" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<table class="datalist" width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr bgcolor="#ECECEC">
									<td align="center" bgcolor="#E9F4FF" class="STYLE1" width="120px">
										<strong>课程编号：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" width="35%">
										<s:property value="#request.sacLiveView[0]" />
									</td>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1" width="120px">
										<strong>课程名称：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" width="35%">
										<s:property value="#request.sacLiveView[1]" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>报名开始时间：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:property value="#request.sacLiveView[2]" />
									</td>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>报名结束时间：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:property value="#request.sacLiveView[3]" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>报名人数上限：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:property value="#request.sacLiveView[4]" />
									</td>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>报名范围：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:property value="#request.sacLiveView[5]" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>预计学时：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:property value="#request.sacLiveView[6]" />
									</td>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>实际学时：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:property value="#request.sacLiveView[7]" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>金额(元)：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:property value="#request.sacLiveView[8]" />
									</td>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>学习建议：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:property value="#request.sacLiveView[17]" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>预计直播开始时间：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:property value="#request.sacLiveView[10]" />
									</td>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>预计直播结束时间：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:property value="#request.sacLiveView[11]" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>课程性质：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:property value="#request.sacLiveView[12]" />
									</td>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>业务分类：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:property value="#request.sacLiveView[13]" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>大纲分类：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:property value="#request.sacLiveView[14]" />
									</td>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>内容属性分类：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:property value="#request.sacLiveView[15]" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>建议学习人群：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:property value="#request.sacLiveView[18]" default="-" />
									</td>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>课程图片：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										<img width="200px" height="140px" src='<s:property value="#request.sacLiveView[19]" />' />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>通过规则描述：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="3">
										<s:property value="#request.sacLiveView[24]" />
									</td>
								</tr>
								<tr style="display:none;">
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>课程简介：</strong>
									</td>
									<td colspan="3" align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:if test='#request.sacLiveView[20] == "-"'>-</s:if>
										<s:else>
											<s:property value="#request.sacLiveView[20]" />
										</s:else>
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>主讲人：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:property value="#request.sacLiveView[21]" />
									</td>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>主讲人照片：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:if test='#request.sacLiveView[22] == "-"'>-</s:if>
										<s:else>
											<img  height="200" width="260" src='<s:property value="#request.sacLiveView[22]" />' />
										</s:else>
									</td>
								</tr>
								<tr style="display:none;">
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>主讲人简介：</strong>
									</td>
									<td colspan="3" align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										<s:property value="#request.sacLiveView[23]" />
									</td>
								</tr>
								<tr>
									<td colspan=6 align="center">
										<input type="button" value="返回" onclick="history.go(-1)" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
				</table>
			</div>
		</div>
	</body>
</html>