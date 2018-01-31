<%@page import="java.util.List"%>
<%@page import="org.apache.struts2.ServletActionContext"%><%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%>
<%@page import="java.util.*"%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>
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
		<title>直播学习记录</title>
		<link href="/entity/manager/images/css_welcome.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="list" style="width: 100%">
			<table cellpadding="0" cellspacing="1">
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0">
							<%
								List listPbtc = (List) request.getAttribute("listPbtc");
								if (null != listPbtc && listPbtc.size() > 0) {
									for (int i = 0; i < listPbtc.size(); i++) {
										Object[] detail = (Object[]) listPbtc.get(i);
							 %>
							<tr align="center">
								<td colspan="5" align="left">
									课程编号：<b><%=detail[0]%></b>&nbsp;&nbsp;&nbsp;&nbsp;
									课程名称：<b><%=detail[1]%></b>
								</td>
							</tr>
							 <%
							 	}}
							  %>
							<tr>
								<th colspan="5" align="left">
									<font size="+1">登陆记录</font>
								</th>
							</tr>
							<tr>
								<th width="100px;">
									序号
								</th>
								<th width="48%">
									登陆时间
								</th>
								<th width="48%">
									登出时间
								</th>
							</tr>
							<%
								List listWeHistory = (List) request.getAttribute("listWeHistory");
								if (null != listWeHistory && listWeHistory.size() > 0) {
									for (int i = 0; i < listWeHistory.size(); i++) {
										Object[] detail = (Object[]) listWeHistory.get(i);
							%>
							<tr align="center">
								<td>
									<%=(i + 1)%>
								</td>
								<td>
									<%=detail[0]%>
								</td>
								<td>
									<%=detail[1]%>
								</td>
							</tr>
							<%
								}
								} else {
							%>
							<tr align="center">
								<td colspan="3">
									-无记录-
								</td>
							</tr>
							<%
								}
							%>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0">
							<tr>
								<th colspan="4" align="left">
									<font size="+1">点名记录</font>
								</th>
							</tr>
							<tr>
								<th width="100px;">
									序号
								</th>
								<th width="46%">
									点名开始时间
								</th>
								<th width="46%">
									点名结束时间
								</th>
								<th width="4%">
									应答次数
								</th>
							</tr>
							<%
								List listWeRollCall = (List) request.getAttribute("listWeRollCall");
								if (null != listWeRollCall && listWeRollCall.size() > 0) {
									for (int i = 0; i < listWeRollCall.size(); i++) {
										Object[] detail = (Object[]) listWeRollCall.get(i);
							%>
							<tr align="center">
								<td>
									<%=(i + 1)%>
								</td>
								<td>
									<%=detail[0]%>
								</td>
								<td>
									<%=detail[1]%>
								</td>
								<td>
									<%=detail[2]%>
								</td>
							</tr>
							<%
								}
								} else {
							%>
							<tr align="center">
								<td colspan="4">
									-无记录-
								</td>
							</tr>
							<%
								}
							%>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0">
							<tr>
								<th colspan="3" align="left">
									<font size="+1">互动调查记录</font>
								</th>
							</tr>
							<%
								List listVote = (List) request.getAttribute("listVote");
								Map<Object, List> mapVoteQuestion = (Map<Object, List>) request.getAttribute("mapVoteQuestion");
								Map<Object, List> mapVoteOption = (Map<Object, List>) request.getAttribute("mapVoteOption");
								Map<Object, List> mapVoteResult = (Map<Object, List>) request.getAttribute("mapVoteResult");
								if (null != listVote && listVote.size() > 0) {
									for (int i = 0; i < listVote.size(); i++) {
										Object[] detail = (Object[]) listVote.get(i);
										Object subject = detail[1];
										Object subId = detail[0];
							%>
							<tr>
								<th colspan="2">
									互动调查主题
								</th>
							</tr>
							<tr align="center">
								<td colspan="2">
									<%=subject%>
								</td>
							</tr>
							<tr>
								<th width="20%">
									问题内容
								</th>
								<th>
									<span title="红色为学员选择的答案">问题选项/回答</span>
								</th>
							</tr>
							<%
								List listVoteQuestion = mapVoteQuestion.get(subId);
										for (int ii = 0; ii < listVoteQuestion.size(); ii++) {
											Object[] questionDetail = (Object[]) listVoteQuestion.get(ii);
											Object questionId = questionDetail[0];
											Object content = questionDetail[1];
							%>
							<tr>
								<td>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<b><%=content%></b>
								</td>
								<td align="left">
									<%
										List listVoteOption = mapVoteOption.get(questionId);
													Boolean indexDisplay = false;
													for (int iii = 0; iii < listVoteOption.size(); iii++) {
														Object[] optionDetail = (Object[]) listVoteOption.get(iii);
														Object optionId = optionDetail[0];
														Object optionValue = optionDetail[1] + "";
														List listResult = mapVoteResult.get(optionId);
														Object[] result = null;
														if (null != listResult && listResult.size() > 0)
															result = (Object[]) listResult.get(0);
														Object resId = null;
														Object resValue = null;
														Object optId = null;
														String style = "";
														if (null != result) {
															resId = result[0];
															resValue = result[1];
															optId = result[2];
														}
														if (optionId.equals(optId) && (null == resValue || "".equals(resValue))) {
															style = "color:red";
														}
														if (null == resValue || "".equals(resValue)) {
															indexDisplay = true;
														} else {
															indexDisplay = false;
															optionValue = resValue;
														}
									%>
									<%=indexDisplay ? (iii + 1) + "、" : ""%><span style="<%=style %>" id="option<%=optionId%>"><%=optionValue%></span>
									<br />
									<%
										}
									%>
								</td>
							</tr>
							<%
								}
									}
								} else {
							%>
							<tr align="center">
								<td colspan="3">
									-无记录-
								</td>
							</tr>
							<%
								}
							%>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0">
							<tr>
								<th colspan="5" align="left">
									<font size="+1">答疑记录</font>
								</th>
							</tr>
							<tr>
								<th width="100px;">
									序号
								</th>
								<th width="10%">
									提问时间
								</th>
								<th width="40%">
									问题
								</th>
								<th width="40%">
									回答
								</th>
								<th width="6%">
									回答人
								</th>
							</tr>
							<%
								List listWeQa = (List) request.getAttribute("listWeQa");
								if (null != listWeQa && listWeQa.size() > 0) {
									for (int i = 0; i < listWeQa.size(); i++) {
										Object[] detail = (Object[]) listWeQa.get(i);
							%>
							<tr align="center">
								<td>
									<%=(i + 1)%>
								</td>
								<td>
									<%=detail[0]%>
								</td>
								<td>
									<%=detail[1]%>
								</td>
								<td>
									<%=detail[2]%>
								</td>
								<td>
									<%=detail[3].toString().substring(0, detail[3].toString().indexOf("-"))%>
								</td>
							</tr>
							<%
								}
								} else {
							%>
							<tr align="center">
								<td colspan="5">
									-无记录-
								</td>
							</tr>
							<%
								}
							%>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<%
							Object minSum = ServletActionContext.getRequest().getAttribute("minSum");
							Object stuName = ServletActionContext.getRequest().getAttribute("stuName");
							if (null != stuName && !"".equals(stuName)) {
						%>
						学员：
						<b><%=stuName%></b>
						<%
							}
							if (null != minSum && !"".equals(minSum)) {
						%>
						本次直播在线时间合计：
						<b><%=minSum%></b> 分钟
						<%
							}
						%>
					</td>
				</tr>
				<tr>
					<td height="10" colspan="5" align="center">
						<input type="button" value="关闭" onclick="javascript:window.self.close()">
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>