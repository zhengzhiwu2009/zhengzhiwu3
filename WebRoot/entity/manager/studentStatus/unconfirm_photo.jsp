<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<% 
	String id = request.getParameter("id");
 %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>tableType_2</title>
		<link rel="stylesheet" href="/entity/manager/css/admincss.css"
			type="text/css"></link>
	</head>
	<body leftmargin="0" topmargin="0" class="scllbar">
		<s:form name="batch"
			action="/entity/basic/peBzzStudent_unConfirmPhoto.action" method="POST">
			<input type="hidden" name="studentId" value="<%=id %>" />
			<table width="80%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td valign="top" class="pageBodyBorder_zlb" align="center">
						<div class="cntent_k" id="zlb_content_start">
							<p>
								&nbsp;
							</p>
							<table width="68%" border="0" cellpadding="0" cellspacing="0">

								
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">取消原因:</span>
									</td>
									<td valign="bottom">
										<textarea class="smallarea"  name="reason" cols="60" rows="5" id="reason"></textarea>
									</td>
								</tr>
									<tr class="postFormBox">
									<td></td>
									 <td align=left></td>
								</tr>	
										
								<tr class="postFormBox">
									<td></td>
									 <td align=center><input type=submit value = "取消确认" /> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
									 <input type="button" value="返回" onclick="javascript:window.history.back()"</td>
								</tr>
							</table>
						</div>

					</td>
				</tr>

			</table>
		</s:form>
	</body>
</html>