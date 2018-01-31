<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,java.net.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.answer.*"%>
<%@ include file="../pub/priv.jsp"%>
<%
	String mock_login = (String) session.getAttribute("mock_login");
	String title = fixnull(request.getParameter("title"));
	String name = fixnull(request.getParameter("name"));
	String type = fixnull(request.getParameter("type"));
%>
<html>
	<head>
		<title></title>
		<link href="../css/css.css" rel="stylesheet" type="text/css">
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
	<body leftmargin="0" topmargin="0" background="../images/bg2.gif">
		<%!String fixnull(String str) {
		if (str == null || str.equalsIgnoreCase("null"))
			return "";
		else
			return str.trim();
	}%>
		<form name="search" method="post" action="index.jsp">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="text1">
						<img src="/entity/function/images/xb3.gif" width="17" height="15">
						<strong>课程答疑</strong>
					</td>
				</tr>
				<tr>
					<td valign="top" class="bg3">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td align="center">
									<span valign="top" style="margin-top: 0px; padding-top: 0px;"><a href="faq.jsp" class="tj">[常见问题库]</a> <%
 	if ("student".equalsIgnoreCase(userType)) {
 		if (mock_login == null || !mock_login.equals("1")) {
 %> &nbsp;&nbsp;&nbsp;&nbsp;<a href="question_add.jsp" class="tj">[我要提问]</a> </span>
									<%
										}
										}
									%>
								</td>
								<td align="right" class="mc1">
									<img src="../images/xb.gif" width="48" height="32">
									按问题名称搜索：
									<input name="title" type="text" size="10" maxlength="50" value="<%=title%>">
									按发布人搜索：
									<input name="name" type="text" size="10" maxlength="50" value="<%=name%> ">
									<a href="javascript:document.search.submit()"><img src="../images/search.gif" width="99" height="19" border="0"> </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="padding: 5px 20px 5px 20px;">
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<th class="th">
									<!-- 问题 -->
									问题
								</th>
								<th class="th">
									<!-- 问题分类 -->
									是否回答
								</th>
								<th class="th">
									<!-- 是否回答 -->
									发布人
								</th>
								<th class="th">
									<!-- 发布人 -->
									提交时间
								</th>
								<th class="th">
									<!-- 发布时间 -->
									浏览次数
								</th>
							</tr>
							<%
								title = fixnull(request.getParameter("title"));
								name = fixnull(request.getParameter("name"));
								try {
									InteractionFactory interactionFactory = InteractionFactory.getInstance();
									InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
									int totalItems = interactionManage.getQuestionsNum(teachclass_id, title, name, type, "");
									//----------分页开始---------------
									String spagesize = (String) session.getValue("pagesize");
									String spageInt = request.getParameter("pageInt");
									Page pageover = new Page();
									pageover.setTotalnum(totalItems);
									pageover.setPageSize(spagesize);
									pageover.setPageInt(spageInt);
									int pageNext = pageover.getPageNext();
									int pageLast = pageover.getPagePre();
									int maxPage = pageover.getMaxPage();
									int pageInt = pageover.getPageInt();
									int pagesize = pageover.getPageSize();
									String link = "&title=" + URLEncoder.encode(title, "utf-8") + "&name=" + URLEncoder.encode(name, "utf-8") + "&type=" + type;
									//----------分页结束---------------		
									List questionList = interactionManage.getQuestions(pageover, teachclass_id, title, name, type, "");
									Iterator it = questionList.iterator();
									boolean bg = false;
									String bgcolor = "";
									if (null == it || !it.hasNext()) {
							%>
							<tr>
								<td class="td" colspan="5">
									-暂无数据-
								</td>
							</tr>
							<%
								}
									while (it.hasNext()) {
										Question question = (Question) it.next();
										if (bg == true) {
											bgcolor = " bgcolor=\"E8F9FF\"";
											bg = false;
										} else {
											bgcolor = "";
											bg = true;
										}
							%>
							<tr <%=bgcolor%>>
								<td class="td">
									<a href="question_detail.jsp?id=<%=question.getId()%>&pageInt=<%=pageInt%>&title=<%=title%>&name=<%=name%>"><font color="blue"><u> <%=question.getTitle()%></u> </font>
								</td>
								<td class="td">
									<%
										if (true == question.isReStatus())
														out.print("是");
													else
														out.print("否");
									%>
								</td>
								<td class="td"><%=question.getSubmituserName()%></td>
								<td class="td"><%=question.getDate()%></td>
								<td class="td"><%=question.getClickNum()%></td>
							</tr>
							<%
									}
							%>
						</table>
					</td>
				</tr>
				<tr>
					<td align="center">
						<%@ include file="../pub/dividepage.jsp"%>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<%
	} catch (Exception e) {
		out.print(e.getMessage());
		return;
	}
%>
