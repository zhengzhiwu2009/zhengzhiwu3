
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%><%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
	String d = String.valueOf(Math.random());
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>查看课程信息</title>
		<link href="./css/css.css" rel="stylesheet" type="text/css">
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
		<style type="text/css">
body {
	padding: 0 0 0 8px;
	margin: 2px 0 0 0;
	background-color: rgb(236, 247, 255);
}

.STYLE1 {
	color: #000000
}

.border {
	border: solid 1px #ffffff;
}
</style>
	</head>
	<body topmargin="0" leftmargin="0" bgcolor="#FAFAFA">
		<div id="main_content">
			<div class="content_title">
				查看课程信息
			</div>
			<div class="cntent_k">
				<div class="k_cc">
					<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td height="8" colspan="4">
								&nbsp;
							</td>
						</tr>
						<tr valign="middle">
							<td width="15%" height="30" align="left" class="postFormBox">
								<span class="name">课程编号：</span>
							</td>
							<td width="35%" class="postFormBox" style="padding-left: 18px">
								<s:property value="getBean().getCode()" />
							</td>
							<td width="15%" height="30" align="left" class="postFormBox">
								<span class="name">课程名称：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="getBean().getName()" />
							</td>
						</tr>
						<tr valign="middle">
							<td height="30" align="left" class="postFormBox">
								<span class="name">报名开始时间：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:date name="getBean().getSignStartDatetime()" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td height="30" align="left" class="postFormBox">
								<span class="name">报名结束时间：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:date name="getBean().getSignEndDatetime()" format="yyyy-MM-dd HH:mm:ss" />
							</td>
						</tr>
						<tr valign="middle">
							<td height="30" align="left" class="postFormBox">
								<span class="name">报名人数上限：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="getBean().getMaxStus()" />
							</td>
							<td height="30" align="left" class="postFormBox">
								<span class="name">报名范围：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="getBean().getEnumConstByFlagSignType().getName()" />
							</td>
						</tr>
						<tr valign="middle">
							<td height="30" align="left" class="postFormBox">
								<span class="name">预计学时：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="getBean().getEstimateTime()" />
							</td>
							<td height="30" align="left" class="postFormBox">
								<span class="name">实际学时：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="getBean().getTime()" />
							</td>
						</tr>
						<tr valign="middle">
							<td height="30" align="left" class="postFormBox">
								<span class="name">金额(元)：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="getBean().getPrice()" />
							</td>
							<td height="30" align="left" class="postFormBox">
								<span class="name">主讲人：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="getBean().getTeacher()" />
							</td>
						</tr>
						<%
						UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
						if(!"2".equals(us.getUserLoginType()) && !"4".equals(us.getUserLoginType())){
						%>
						<tr valign="middle">
							<td height="30" align="left" class="postFormBox">
								<span class="name">直播链接(WEB)：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px" colspan="3">
								<s:property value="getBean().getLiveUrl()" />
							</td>
						</tr>
						<tr valign="middle">
							<td height="30" align="left" class="postFormBox">
								<span class="name">直播链接(CLIENT)：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px" colspan="3">
								<span><s:property value="getBean().getLiveUrlClient()" />?k=<s:property value="getBean().getId()" /></span>
							</td>
						</tr>
						<tr valign="middle">
							<td height="30" align="left" class="postFormBox">
								<span class="name">预计直播开始时间：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:date name="getBean().getEstimateStartDatetime()" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td height="30" align="left" class="postFormBox">
								<span class="name">预计直播结束时间：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:date name="getBean().getEstimateEndDatetime()" format="yyyy-MM-dd HH:mm:ss" />
							</td>
						</tr>
						<%} %>
						<tr valign="middle">
							<td height="30" align="left" class="postFormBox">
								<span class="name">课程性质：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="getBean().getEnumConstByFlagCourseType().getName()" />

							</td>
							<td height="30" align="left" class="postFormBox">
								<span class="name">业务分类：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="getBean().getEnumConstByFlagCourseCategory().getName()" />
							</td>
						</tr>
						<tr valign="middle">
							<td height="30" align="left" class="postFormBox">
								<span class="name">大纲分类：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="getBean().getEnumConstByFlagCourseItemType().getName()" />
							</td>
							<td height="30" align="left" class="postFormBox">
								<span class="name">内容属性分类：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="getBean().getEnumConstByFlagContentProperty().getName()" />
							</td>
						</tr>
						<tr valign="middle">
							<td height="30" align="left" class="postFormBox">
								<span class="name">发布日期：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:if test="getBean().getAnnounceDate()==null">
									-
								</s:if>
								<s:else>
									<s:date name="getBean().getAnnounceDate()" format="yyyy-MM-dd HH:mm:ss" />
								</s:else>
							</td>
							<td height="30" align="left" class="postFormBox">
								<span class="name">学习建议：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="getBean().getSuggestion()" />
							</td>
						</tr>
						<tr>
							<td height="30" align="left" class="postFormBox">
								<span class="name">通过规则描述：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px" colspan="3">
								<s:property value="getBean().getPassRoleNote()" />
							</td>
						</tr>
						<tr valign="middle">
							<td height="30" align="left" class="postFormBox">
								<span class="name">建议学习人群：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px" colspan="3">
								<s:property value="#request.suggestName" default="-" />
							</td>
						</tr>
						<tr valign="middle">
							<td height="30" align="left" class="postFormBox">
								课程图片：
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<img width="200px" height="140px" src='<s:property value="getBean().getPhotoLink()" />' />
							</td>
							<td height="30" align="left" class="postFormBox">
								主讲人照片：
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<img  height="200" width="260" src='<s:property value="getBean().getTeaImg()" />' />
							</td>
						</tr>
						<tr valign="middle" style="display:none;">
							<td height="30" align="left" class="postFormBox">
								<span class="name">课程简介：</span>
							</td>
							<td colspan="3" class="postFormBox" style="padding-left: 18px">
								<s:property value="getBean().getLiveDesc()" />
							</td>
						</tr>
						<tr valign="middle" style="display:none;">
							<td height="30" align="left" class="postFormBox">
								<span class="name">主讲人简介：</span>
							</td>
							<td colspan="3" class="postFormBox" style="padding-left: 18px">
								<s:property value="getBean().getTeaNote()" />
							</td>
						</tr>
						<tr>
							<td height="10" colspan="6" align="center">
								<input type="button" value="关闭" onclick="javascript:window.self.close()">
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</body>
</html>