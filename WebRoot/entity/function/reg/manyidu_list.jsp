<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="priv.jsp"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant" />

<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragrma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>中国证券业协会</title>
		<link href="./css/css.css" rel="stylesheet" type="text/css">
		<style type="text/css">
.tab {
	border-collapse: collapse;
}

.th {
	font-size: 14px;
	background: url(thbg.jpg) repeat-x top #59a4da;
	border: #dedede 1px solid;
	color: #FFF;
}

.td {
	font-size: 12px;
	border: #dedede 1px solid;
	text-align: center;
}
</style>
	</head>
	<%!String fixnull(String str) {
		if (str == null || str.equals("") || str.equals("null"))
			str = "-";
		return str;
	}%>
	<%
		String flag = request.getParameter("flag");
		String opencourseId = request.getParameter("opencourseId");
		String sql = " SELECT B.ID, A.TITLE, NVL(TO_CHAR(C.VOTE_DATE,'yyyy-MM-dd'),'-') VOTE_DATE FROM PE_VOTE_PAPER A INNER JOIN (SELECT ID, FK_PARENT_ID FROM PE_VOTE_PAPER "
				+ " WHERE COURSEID = (SELECT FK_COURSE_ID FROM PR_BZZ_TCH_OPENCOURSE WHERE ID = '" + opencourseId + "')) B "
				+ " ON A.ID = B.FK_PARENT_ID LEFT JOIN (SELECT * FROM PR_VOTE_RECORD WHERE SSOID = '" + us.getSsoUser().getId() + "') C ON B.ID = C.FK_VOTE_PAPER_ID ";//Lee 2014年9月18日14:37:02 去掉满意度为有效的条件 WHERE A.FLAG_ISVALID = '2'
		dbpool db = new dbpool();
		MyResultSet mrs = null;
		mrs = db.executeQuery(sql);
		List<String> mydList = new ArrayList<String>();
		while (null != mrs && mrs.next()) {
			mydList.add(fixnull(mrs.getString("id")));
			mydList.add(fixnull(mrs.getString("title")));
			mydList.add(fixnull(mrs.getString("vote_date")));
		}
		//Lee start 2014年9月14日22:33:42
		db.close(mrs);
		mrs = null;
		db = null;
		//Lee end
	%>
	<body leftmargin="0" topmargin="0" background="./images/bg2.gif">
		<form>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="/entity/function/images/xb3.gif" width="17" height="15">
									<strong>满意度调查</strong>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="padding: 5px 20px 5px 20px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<th class="th">
									问卷名称
								</th>
								<th class="th">
									填写时间
								</th>
								<th class="th">
									操作
								</th>
							</tr>
							<%
								if (null != mydList && mydList.size() > 0) {
							%>
							<tr>
								<td class="td">
									<%=mydList.get(1)%>
								</td>
								<td class="td">
									<%=mydList.get(2)%>
								</td>
								<td class="td">
									<%
										if (null == mydList.get(2) || "-".equals(mydList.get(2)) || "".equals(mydList.get(2))) {
												if (!"false".equalsIgnoreCase(flag)) {
									%>
									<a href="###" onClick="window.parent.open('/entity/first/firstPeVotePaper_toVote.action?bean.id=<%=mydList.get(0)%>&togo=1', 'newwin')" class="menuzi">填写</a>
									<%
										} else {
									%>
									<font color="red">未完成学习，不能填写满意度调查。</font>
									<%
										}
											} else {
									%>
									-
									<%
										}
									%>
								</td>
							</tr>
							<%
								} else {
							%>
							<tr>
								<td colspan="3" align="center" class="td">
									-暂无数据-
								</td>
							</tr>
							<%
								}
							%>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
