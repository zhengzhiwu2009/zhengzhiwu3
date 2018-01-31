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
<%@page import="com.whaty.platform.entity.service.ass.PeBzzAssService" %>

<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragrma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>中国证券业协会</title>
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
		<script>
function cfmdel(link)
{
	if(confirm("您确定要删除此作业吗？"))
		window.navigate(link);
}
function DetailInfo(paperId)
{
	window.open('homeworkpaper_info.jsp?paperId='+paperId,'','dialogWidth=630px;dialogHeight=550px,scrollbars=yes');
}
</script>
	</head>
	<%!String fixnull(String str) {
		if (str == null || str.equals("") || str.equals("null"))
			str = "";
		return str;
	}%>
	<%
		String title = fixnull(request.getParameter("title"));
		String title_1 = title;

		title = java.net.URLEncoder.encode(title, "UTF-8");
		String type = "KCZL";

		try {
			//InteractionFactory interactionFactory = InteractionFactory.getInstance();
			//InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
			PeBzzAssService peBzzAssService = new PeBzzAssService();

			int totalItems = 0;
			totalItems = peBzzAssService.getTeachClassListNum(courseId, type, title_1);
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
			String link = "&type=" + type + "&title=" + title;
			//----------分页结束---------------
			List testPaperList = null;
			testPaperList = peBzzAssService.getTeachClassList(pageover, courseId, type, title_1);
	%>
	<body leftmargin="0" topmargin="0" background="../images/bg2.gif">
		<form method="post" action="homeworkpaper_list.jsp" name="paper_listSearchForm">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td valign="top" background="../images/top_01.gif">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="text1">
									<img src="/entity/function/images/xb3.gif" width="17" height="15">
									<strong>资料下载</strong>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td align="right">
									<table width="80%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<%
												if ("teacher".equalsIgnoreCase(userType) && (us.getRoleId().equals("3") || us.getRoleId().equals("R004"))) {
											%>
											<td align="center">
												<a href="homeworkpaper_add.jsp?type=KCZL" class="tj">[添加参考资料]</a>&nbsp;
											</td>
											<%
												}
											%>
											<td align="right" class="mc1">
												<img src="../images/xb.gif" width="48" height="32">
												按标题搜索：
												<input name="title" type="text" size="20" maxlength="50" value="<%=title_1%>" />
												<input type="image" src="../images/search.gif" width="99" height="19" />
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td style="padding: 5px 20px 5px 20px;">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<th width="80%" class="th">
												名称
											</th>
											<th width="10%" class="th">
												发布时间
											</th>
											<th width="10%" class="th">
												操作
											</th>
										</tr>
										<%
											if (null != testPaperList || testPaperList.size() > 0) {
													int trNo = 1; //用来表示每行
													int trNoMod; //记录是奇数行还是偶数行
													String trBgcolor; //设置每行的背景色
													Iterator it = testPaperList.iterator();
													while (it.hasNext()) {
														//根据奇偶行，决定每行的背景颜色          	
														trNoMod = trNo % 2;
														if (trNoMod == 0) {
															trBgcolor = "#E8F9FF";
														} else {
															trBgcolor = "#ffffff";
														}
														InteractionTeachClass homeworkPaper = (InteractionTeachClass) it.next();

														String paperId = homeworkPaper.getId();
														String paperTitle = homeworkPaper.getTitle();
														String paperType = homeworkPaper.getType();
														String status = homeworkPaper.getStatus();
														String creatDate = homeworkPaper.getPublish_date();
														String fileLink = homeworkPaper.getFileLink();
														String status_link = "";
														if (status.equals("1")) {
															status_link = "<a href='homeworkpaper_changestatus.jsp?paperId=" + paperId + "&pageInt=" + pageInt + "&status=1'>无效</a>";
														} else {
															status_link = "<a href='homeworkpaper_changestatus.jsp?paperId=" + paperId + "&pageInt=" + pageInt + "&status=0'>有效</a>";
														}
										%>
										<tr bgcolor="<%=trBgcolor%>">
											<td class="td">
												<a href="homeworkpaper_info.jsp?id=<%=paperId%>&paperId=<%=paperId%>&type=KCZL&title=<%=title%>&pageInt=<%=pageInt%>"><%=paperTitle%></a>
											</td>
											<td class="td"><%=creatDate%></td>
											<td class="td">
												<%
													if ("teacher".equalsIgnoreCase(userType) && (us.getRoleId().equals("3") || us.getRoleId().equals("R004"))) {
												%>
												<a href="homeworkpaper_edit.jsp?id=<%=paperId%>&type=KCZL&title=<%=title%>&pageInt=<%=pageInt%>">编辑</a>
												<a href="javascript:cfmdel('homeworkpaper_delete.jsp?pageInt=<%=pageInt%>&paperId=<%=paperId%>&title=<%=title%>')" class="button">删除</a>

												<%
													} else {
												%>
												<a href="homeworkpaper_info.jsp?id=<%=paperId%>&type=KCZL&title=<%=title%>&pageInt=<%=pageInt%>">查看</a>
												<%
													}
													//判断附件个数,如果附件数超过1个,则到下载页面下载
													if(fileLink != null && !"".equals(fileLink)) {
														if (fileLink.split(",").length == 1) {
												%>
												<a href="<%=fileLink%>" class="button">下载附件</a>
												<%  	} else if(fileLink.split(",").length > 1) { %>
												<a href="downloadFileLink.jsp?id=<%=paperId %>" class="button" target="blank">下载附件</a>
												<%  	}
													} %>
											</td>
										</tr>
							<%
								}
									} else {
							%>
							<tr>
								<td class="td" colspan="3">
									-暂无数据-
								</td>
							</tr>
							<%
								}
							%>
									</table>
								</td>
							</tr>
							<tr>
								<td><%@ include file="../pub/dividepage.jsp"%></td>
							</tr>
						</table>
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
