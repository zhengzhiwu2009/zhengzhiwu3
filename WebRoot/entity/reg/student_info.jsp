<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String d = String.valueOf(Math.random());
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

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
		<link href="/entity/bzz-students/css1.css" rel="stylesheet"
			type="text/css" />
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet"
			type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
	</head>

	<body>
		<div class="" style="width: 100%; padding: 0; margin: 0;"
			align="center">
			<div class="main_title">
				个人信息
			</div>
			<div class="main_txt">
				<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0" style="border: solid 2px #9FC3FF; ">
					<tr>
						<td>
							<table class="datalist" width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#D7D7D7">
								<tr bgcolor="#ECECEC">
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>系统编号：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										&nbsp;
										<s:property value="peBzzStudent.getRegNo()" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1" >
										<strong>姓 名：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										&nbsp;
										<s:property value="peBzzStudent.getTrueName()" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>性 别：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										&nbsp;
										<s:if test="peBzzStudent.gender == null ">--</s:if>
										<s:property value="peBzzStudent.gender" />
									</td>
								</tr>
								<tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>国 籍：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										&nbsp;
										<s:if test="peBzzStudent.cardType == null">--</s:if>
										<s:property value="peBzzStudent.cardType" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>学 历：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										&nbsp;
										<s:if test="peBzzStudent.education == null ">--</s:if>
										<s:property value="peBzzStudent.education" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>手 机：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
										&nbsp;
										<s:if test="peBzzStudent.mobilePhone == null ">--</s:if>
										<s:property value="peBzzStudent.mobilePhone" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="kctx_zi2">
										<DIV align="center" class="STYLE1">
											<strong>机构名称：</strong>
										</DIV>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2" colspan="2">
										&nbsp;
										<s:if test="peBzzStudent.peEnterprise.name == null ">--</s:if>
										<s:property value="peBzzStudent.peEnterprise.name" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="kctx_zi2">
										<DIV align="center" class="STYLE1">
											<strong>自填单位名称：</strong>
										</DIV>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2" colspan="2">
										&nbsp;
										<s:if test="peBzzStudent.enterpriseName == null ">--</s:if>
										<s:property value="peBzzStudent.enterpriseName" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>工作部门：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
										&nbsp;
										<s:if test="peBzzStudent.department == null ">--</s:if>
										<s:property value="peBzzStudent.department" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
										<strong>职务：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
										&nbsp;
										<s:if test="peBzzStudent.position==null">--</s:if>
										<s:property value="peBzzStudent.position" />
										 
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>办公电话：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
										&nbsp;
										<s:if test="peBzzStudent.phone == null ">--</s:if>
										<s:property value="peBzzStudent.phone" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>电子邮箱：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
										&nbsp;
										<s:if test="peBzzStudent.email == null ">--</s:if>
										<s:property value="peBzzStudent.email" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>通讯地址：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
										&nbsp;
										<s:if test="peBzzStudent.address == null ">--
										</s:if>
										<s:property value="peBzzStudent.address" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<s:if test="peBzzStudent==null"></s:if>
					<s:elseif test='peBzzStudent.enumConstByIsEmployee.name=="否"'>
						<tr>
							<td>
								<table border="0" align="center" cellpadding="0" cellspacing="0" height="35" valign="bottom">
									<tr>
										<td>
											<a href="/entity/workspaceRegStudent/regStudentWorkspace_toPassword.action">
												<img src="/entity/bzz-students/images/two/an_03.gif" width="71" height="23" border="0" />
											</a>
										</td>
										 
										<td width="20"></td>
										<td>
											<a	href="/entity/workspaceRegStudent/regStudentWorkspace_toModifyInfo.action">
											<img	src="/entity/bzz-students/images/two/an_03_1.gif" />
											</a>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</s:elseif>
				</table>
				&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
		</div>
	</body>
</html>