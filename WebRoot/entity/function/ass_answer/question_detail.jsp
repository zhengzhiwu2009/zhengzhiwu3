<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.answer.*"%>

<%@ include file="../ass/priv.jsp"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant" />

<script language="javascript">
function cfmdel(link){
	if(confirm("您确定要删除吗？"))
		window.navigate(link);
}
function cfmOp(){
	return confirm("真的要修改吗");
}
</script>
<%
	String question_id = request.getParameter("id");
	String pageInt = request.getParameter("pageInt");
	String title = request.getParameter("title");
	String name = request.getParameter("name");
	dbpool db1 = new dbpool();
	db1.executeUpdate("update INTERACTION_QUESTION_INFO set click_num=click_num+1 where id='" + question_id + "'");
	try {
		//InteractionFactory interactionFactory = InteractionFactory.getInstance();
		//InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
		Question question = aAssService.getQuestion(question_id);
		//Answer answer = interactionManage.getAnswer(question_id);	
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("question_id", question_id, "="));
		//UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
%>

<html>
	<head>
		<title></title>
		<link href="../css/css.css" rel="stylesheet" type="text/css">
		<style type="text/css">
a:hover{background-color:#B2DFEE;}
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
	<body leftmargin="0" topmargin="0" background="../images/bg2.gif" style='overflow: scroll; overflow-x: hidden'>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="86" valign="top" background="../images/top_01.gif">
					<img src="../images/dy.gif" width="217" height="86">
				</td>
			</tr>
			<tr>
				<td>
					<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<th width="10%" class="th">
								问题
							</th>
							<td class="td">
								<%=question.getTitle()%>
								<input type="hidden" name="id" value="<%=question_id%>">
								<input type="hidden" name="pageInt" value="<%=pageInt%>">
								<input type="hidden" name="title" value="<%=title%>">
								<input type="hidden" name="name" value="<%=name%>">
							<th width="10%" class="th">
								作者
							</th>
							<td width="20%" class="td">
								<font color="#FF0000"><%=question.getSubmituserName()%></font>
							<th width="10%" class="th">
								发布时间
							</th>
							<td width="20%" class="td"><%=question.getDate()%></td>
						</tr>
						<tr>
							<th class="th">
								问题描述
							</th>
							<td class="td" colspan="5">
								<div style="word-wrap: break-word; word-break: normal; text-align: left;">
									&nbsp;&nbsp;<%=question.getBody()%></div>
							</td>
						</tr>
						<%
							List answerList = aAssService.getAnswers(null, searchProperty, null);
								if (null != answerList && answerList.size() > 0) {
						%>
						<tr>
							<th class="th" rowspan="<%=answerList.size() + 1%>">
								回答列表
							</th>
							<th class="th" colspan="3">
								回答内容
							</th>
							<th class="th">
								回答时间
							</th>
							<th class="th">
								回答人
							</th>
						</tr>
						<%
							} else {
						%>
						<tr>
							<td class="td" colspan="6">
								-暂无回答-
							</td>
						</tr>
						<%
							}
								for (int i = 0; i < answerList.size(); i++) {
									Answer answer = (Answer) answerList.get(i);
						%>
						<tr>
							<td class="td" colspan="3"><div style="word-wrap: break-word; word-break: normal; text-align: left;">&nbsp;&nbsp;<%=answer.getBody()%></div></td>
							<td class="td"><%=answer.getDate()%></td>
							<td class="td"><%=answer.getReuserName()%>
								<%
									String answertea_id = answer.getReuserId();
								}
								%>
							</td>
						</tr>
						<tr>
							<td colspan="6" class="td">
								<%
										if ((student != null && question.getSubmituserId().equals(student.getId()) && !question.isReStatus()) || "3".equals(us.getRoleId())) {
								%>
								&nbsp;&nbsp;
								<a href="question_edit.jsp?id=<%=question_id%>&pageInt=<%=pageInt%>&title1=<%=title%>&name=<%=name%>" class="tj">[修改问题]</a>&nbsp;&nbsp;
								<%
									}
										if ((student != null && question.getSubmituserId().equals(student.getId()) && !question.isReStatus())) {
								%>
								&nbsp;&nbsp;
								<a href="javascript:cfmdel('question_delexe.jsp?id=<%=question_id%>&pageInt=<%=pageInt%>&title=<%=title%>&name=<%=name%>')" class="tj">[删除问题(包括答案)]</a>&nbsp;&nbsp;
								<%
									}
								%>
								&nbsp;&nbsp;
								<a href="index.jsp?pageInt=<%=pageInt%>&title=<%=title%>&name=<%=name%>" class="tj">[返回答疑列表]</a>&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
<%
	} catch (Exception e) {
		out.print(e.getMessage());
		return;
	}
%>
