<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>中国证券业协会</title>
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
		window.location.href=link;
}
function DetailInfo(paperId)
{
	window.open('homeworkpaper_info.jsp?paperId='+paperId,'','dialogWidth=630px;dialogHeight=550px,scrollbars=yes');
}
</script>
	</head>
	
	<body leftmargin="0" topmargin="0" background="/entity/function/images/bg2.gif">
		<form action="/sso/peResourceInteraction_peResourcejs.action" method="POST" name="frmAnnounce">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <input type=hidden name=teachclassId value=<s:property value="#request.teachclassId" />>
			  <input type=hidden name=coursename value=<s:property value="#request.coursename" />>
			 <input type=hidden name=privilege value=<s:property value="#request.privilege" />>
			
				<tr>
					<td valign="top" background="/entity/function/images/top_01.gif">
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
											<td align="center">
											<s:if test="#request.privilege==3">
												<a href="/sso/peResourceInteraction_addGoTo.action?teachclassId=<s:property value="#request.teachclassId" />&coursename=<s:property value="#request.coursename" />" class="tj">[添加参考资料]</a>&nbsp;
											</s:if>
											</td>
											<td align="right" class="mc1">
											<!--  
												<img src="/entity/function/images/xb.gif" width="48" height="32">
												按标题搜索：
												<input name="ziLiaoname" type="text" size="20" maxlength="50"/>
										     	<input type="image" src="/entity/function/images/search.gif" width="99" height="19" />
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											-->
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td style="padding: 5px 20px 5px 20px;">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<th width="81.5%" class="th">
												名称
											</th>
											
											<th width="18.5%" class="th">
												操作
											</th>
									</tr>
							<s:if test="knowledeList==null">
									<tr>
										<td class="td" colspan="3">
											-暂无数据-
										</td>
									</tr>
								</s:if>
							<s:else>
									<s:iterator value='knowledeList' id='knowledge'>
									
										<tr>
											<td class="td" height="35px">
												<a href="/sso/peResourceInteraction_listpeResourcexq.action?ziliaoId=<s:property value="#knowledge[0]" />"><font size="3px"><s:property value="#knowledge[1]" /></font></a>
											</td>
											<td class="td">
											<s:if test="#request.privilege==3">
												<a href="/sso/peResourceInteraction_edit.action?ziliaoId=<s:property value="#knowledge[0]" />&teachclassId=<s:property value="#knowledge[4]" />&coursename=<s:property value="#request.coursename" />"><font size="3px">编辑</font></a>
											<a href="javascript:cfmdel('/sso/peResourceInteraction_delpeResource.action?fkziliao=<s:property value="#knowledge[5]" />&teachclassId=<s:property value="#knowledge[4]" />&coursename=<s:property value="#request.coursename" />')" class="button"><font size="3px">删除</font></a>
											</s:if>	
											<a href="/sso/peResourceInteraction_loadFileLink.action?ziliaoId=<s:property value="#knowledge[0]" />" target="_blank"><font size="3px">下载</font></a>
											</td>
											
									   </tr>
									   
									</s:iterator>
							</s:else>
							
									</table>
									
								</td>
							</tr>
							<tr>
								<td></td>
							</tr>
						
						</table>
					</td>
				</tr>
			</table>
				</form>
	</body>
</html>

