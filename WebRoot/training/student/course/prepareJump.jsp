
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool" />
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.whaty.platform.database.oracle.MyResultSet"%>
<%
	/*---------------------------------------------
	 Function Description:选择课件的表现形式，如果只有一种形式直接进入课件	
	 Editing Time:	
	 Editor: Huze
	 Target File:	
	 	 
	 Revise Log
	 Revise Time:  Revise Content:   Reviser:
	 -----------------------------------------------*/
%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,com.whaty.platform.training.*"%>
<%@page
	import="com.whaty.platform.training.basic.*,com.whaty.platform.standard.scorm.operation.*,com.whaty.platform.database.oracle.standard.standard.scorm.operation.*"%>
<%@page
	import="com.whaty.platform.training.*,com.whaty.platform.database.oracle.standard.training.user.OracleTrainingStudentPriv"%>
<%@page
	import="com.whaty.platform.training.basic.*,com.whaty.platform.sso.web.action.SsoConstant,com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.training.user.*"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);

	String navigate = StringUtils.defaultIfEmpty(request
			.getParameter("navigate"), "");
	String user_id = StringUtils.defaultIfEmpty(request
			.getParameter("user_id"), "");
	String course_id = StringUtils.defaultIfEmpty(request
			.getParameter("course_id"), "");
	String opencourseId = StringUtils.defaultIfEmpty(request
			.getParameter("opencourseId"), "");
	String trueCourseId = StringUtils.defaultIfEmpty(request
			.getParameter("trueCourseId"), "");
	String start = StringUtils.defaultIfEmpty(request
			.getParameter("start"), "");

	//查询是否有多种形式课件存在
	StringBuilder sb = new StringBuilder();
	sb.append("select id, name                                  ");
	sb.append("  from (select distinct b.code as id, b.name     ");
	sb.append("          from scorm_sco_launch a, scorm_type b  ");
	sb.append("         where a.scorm_type = b.code             ");
	sb.append("           and a.course_id = '" + course_id + "')	");

	dbpool db = new dbpool();
	MyResultSet rs = db.executeQuery(sb.toString());
	List<String[]> lstType = new ArrayList<String[]>();
	if (rs != null) {
		while (rs.next()) {
			String[] arrType = {
					StringUtils.defaultIfEmpty(rs.getString("id"), ""),
					StringUtils
							.defaultIfEmpty(rs.getString("name"), "") };
			lstType.add(arrType);
		}
	}
	db.close(rs);
	//Lee start 2014年9月14日22:11:08
	rs = null;
	db = null;
	//Lee end
	if (lstType.size() == 0) {
%>
<script language="javascript">
				alert("没有课件");
				window.close();
			</script>
<%
	return;
	} else if (lstType.size() == 1) {
%>
<script language="javascript">
				window.location="./courseware/scorm12/LMSMain.jsp?navigate=<%=navigate%>&course_id=<%=course_id%>&opencourseId=<%=opencourseId%>&trueCourseId=<%=trueCourseId%>&user_id=<%=user_id%>&start=<%=start%>&scorm_type=<%=(lstType.get(0))[0]%>";
			</script>
<%
	return;
	} else {
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css1.css" rel="stylesheet"
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
	font-size: 12px;
	color: #0041A1;
	font-weight: bold;
}

.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}

.kctx_zi1 {
	font-size: 12px;
	color: #0041A1;
}

.kctx_zi2 {
	font-size: 12px;
	color: #54575B;
	line-height: 24px;
	padding-top: 2px;
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
						<tr>
							<td height="13"></td>
						</tr>
					</table>
					<table width="1002" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="752" valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr align="center" valign="top">
										<td height="17" align="left" class="twocentop">
											<div class="content_title">
												<img src="/entity/bzz-students/images/two/1.jpg" width="11"
													height="12" />
												请选择课件类型
											</div>
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
									</tr>
									<table width="75%" border="0" align="center" cellpadding="0"
										cellspacing="0"
										style="border: solid 2px #9FC3FF; padding: 5px; margin: 15px 0px 15px 0px;">
										<tr>
											<td>
												<table width="100%" border="0" cellpadding="0"
													cellspacing="1" bgcolor="#D7D7D7" height='100%'>
													<tr bgcolor="#ECECEC">
														<td align="center" bgcolor="#E9F4FF" class="kctx_zi1"></td>
													</tr>
													<%
														for (String[] arrStr : lstType) {
													%>
													<tr>
														<td align="center" bgcolor="#FAFAFA" class="kctx_zi2">
															<a
																href="./courseware/scorm12/LMSMain.jsp?navigate=<%=navigate%>&course_id=<%=course_id%>&opencourseId=<%=opencourseId%>&trueCourseId=<%=trueCourseId%>&user_id=<%=user_id%>&start=<%=start%>&scorm_type=<%=arrStr[0]%>"><%=arrStr[1]%></a>
														</td>
													</tr>
													<%
														}
													%>

													<tr>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
															课件说明：
														</td>
													</tr>
													<tr>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
															1、普通课件需要安装Windows Media Player插件;
															<br/>
															2、Flash课件需要安装Flash插件;
															<br/>
															3、安装软件在首页上“常用资料下载”下“常用软件下载”中提供插件安装文件下载;
															<br/>
															4、学习过程中如果出现其中一种课件安装插件后也无法播放请尝试另外一种形式;
															<br/>
															5、Flash课件在播放过程中开始需要先加载视频，需要缓冲时间，请您耐心等待;
															<br/>
																6、学习过程中有任何疑问，请拨打咨询服务电话：010-62415115，或登录首页http://www.thbzzpx.org上与在线客服人员联系。感谢您的参与！
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</table>
							</td>
							<td width="13">
								&nbsp;
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
</td>
</tr>
</table>
</body>
</html>
<%
	}
%>

