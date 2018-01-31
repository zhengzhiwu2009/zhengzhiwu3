<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ include file="priv.jsp"%>
<%@page import="com.whaty.platform.entity.service.ass.PeBzzAssService" %>
<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragrma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<%
	String id = request.getParameter("id");
	PeBzzAssService peBzzAssService = new PeBzzAssService();
	
   try{
		
		InteractionTeachClass  teachclass = peBzzAssService.getTeachClass(id);
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

</script>
	</head>
	<body leftmargin="0" topmargin="0" background="../images/bg2.gif">
		<form method="post" action="homeworkpaper_list.jsp" name="paper_listSearchForm">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td valign="top" background="../images/top_01.gif">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="text1">
									<img src="/entity/function/images/xb3.gif" width="17" height="15">
									<strong>附件下载</strong>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td style="padding: 5px 20px 5px 20px;">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<th width="80%" class="th">
												名称
											</th>
											<th width="20%" class="th">
												操作
											</th>
										</tr>
										<%
							    			String fileLink = teachclass.getFileLink();
							    			String[] file = fileLink.split(",");
							    			for(int i = 0; i < file.length; i++) {
							    				String fileName = file[i].substring(16);
							    				String link = file[i];
							    		%>
										<tr bgcolor="">
											<td class="td">
												<%=fileName %>
											</td>
											<td class="td">
												<a href="<%=link %>" >下载</a>
											</td>
										</tr>
										<%  } %>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<%
	}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
%>