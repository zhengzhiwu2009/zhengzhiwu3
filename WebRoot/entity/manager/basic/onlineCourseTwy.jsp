<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page" />
<jsp:directive.page
	import="com.whaty.platform.sso.web.action.SsoConstant" />
<%!//判断字符串为空的话，赋值为""
	String fixnull(String str) {
		if (str == null || str.equals("null") || str.equals(""))
			str = "";
		return str;
	}
	%>
<%
	if(session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY) == null) {
		response.sendRedirect("/error/priv_error.htm");
		return ;
	}
	String courseId = fixnull(request.getParameter("id"));
	
	dbpool db = new dbpool();
	MyResultSet rs = null;
	String sql = "select twy.id,twy.querstion,su.login_id,twy.twy_date \n"
		+ "from pe_bzz_onlinecourse_twy twy inner join sso_user su on twy.fk_sso_user_id=su.id \n" 
		+ "where twy.fk_online_course_id='"+courseId+"' \n"
		+ "order by twy.twy_date desc";
		
	//System.out.println(sql);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="Refresh" content="60"> 
		<title>在线课堂问题列表</title>
		<link href="/entity/manager/statistics/css/admincss.css"
			rel="stylesheet" type="text/css">

		<script language="javascript">
//	function jiesuo(id,page)
//	{
//		var batch_id = id;
//		var pageInt = page;
//		var link = "pici_jiesuo.jsp?batch_id="+batch_id+"&pageInt="+pageInt;
//		if(confirm("您确定要解锁当前批次吗？")){
//		    window.location.href=link;
//			}
//	}
//	function cfmdel(link)
//	{
//		if(confirm("您确定要删除本批次吗？"))
//			window.navigate(link);
//	}

</script>
	</head>

	<body leftmargin="0" topmargin="0" class="scllbar">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="28">
					<table width="100%" height="28" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="12">
								<img src="/entity/manager/statistics/images/page_titleLeft.gif"
									width="12" height="28">
							</td>
							<td align="right"
								background="/entity/manager/statistics/images/page_titleM.gif">
								<table height="28" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="112">
											<img
												src="/entity/manager/statistics/images/page_titleMidle.gif"
												width="112" height="28">
										</td>
										<td
											background="/entity/manager/statistics/images/page_titleRightM.gif"
											class="pageTitleText">
											在线课堂问题列表
										</td>
									</tr>
								</table>
							</td>
							<td width="8">
								<img src="/entity/manager/statistics/images/page_titleRight.gif"
									width="8" height="28">
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td valign="top" class="pageBodyBorder">
					<!-- start:内容区域 -->

					<div class="border">
					
							<table width='100%' border="0" cellpadding="0" cellspacing='0'
								style='margin-bottom: 5px'>
								<tr>
									<td style='white-space: nowrap;'>
										<div style="padding-left: 6px">
											<table>
		
													
												<tr>
												<td>
												
												&nbsp;&nbsp;&nbsp;
												<span class="link"><a id ="getpdf" href = "/entity/manager/basic/onlineCourseTwyExcel.jsp?id=<%=courseId %>">导出Excel</a></span>
												</td>
												</tr>
											</table>
										</div>
									</td>
									<td class='misc' style='white-space: nowrap;'><%--				<div>--%>
										<%--				<span class="link"><img src='/entity/manager/statistics/images/buttons/multi_delete.png' alt='Delete' width="20" height="20" title='Delete'>&nbsp;<a href='#' onclick='javascript:if(confirm("要批量删除选定的批次吗?")) document.userform.submit();'>删除</a></span> --%>
										<%--                </div>--%>
									</td>
								</tr>
							</table>
						
						<!-- end:内容区－头部：项目数量、搜索、按钮 -->
						<!-- start:内容区－列表区域 -->
						<%--	<form name='userform' action='pici_delexe.jsp' method='post' class='nomargin' onsubmit="">	--%>
						<table width='98%' align="center" cellpadding='1' cellspacing='0'
							class='list'>
							<tr>
								<!--<th width='0' class='select'><input type='checkbox' class='checkbox' name='listSelectAll' value='true'  onClick="listSelect('user')"> 
              </th>
              <th width='20' style='white-space: nowrap;'><div style='display:block; width: 20px;'> 
                  <span class="link linkdisabled"><a href='#' title="详细信息">F</a></span></div></th>
              <th width='20' style='white-space: nowrap;'><div style='display:block; width: 20px;'> 
                  <span class="link"><a href='#' title="状态设置">A</a></span></div></th>
              <th width='20' style='white-space: nowrap;'> <div style='display:block; width: 20px;'> 
                  <span class="link linkdisabled"><a href='#' title="编辑">E</a></span></div></th>
              <th width='20' style='white-space: nowrap;'> <div style='display:block; width: 20px;'> 
                  <span class="link linkdisabled"><a href='#' title="删除">D</a></span></div></th> -->
								<th width='3%' style='white-space: nowrap;'>
									<span class="link">编号</span>
								</th>
								<th width='77%' style='white-space: nowrap;'>
									<span class="link">内容</span>
								</th>
								<th width='10%' style='white-space: nowrap;'>
									<span class="link" >时间</span>
								</th>
								<th width='10%' style='white-space: nowrap;'>
									<span class="link">提出人</span>
								</th>
								<!-- <th width='20%' style='white-space: nowrap;'> <span class="link">所属年级</span></th>
               <th width='10%' style='white-space: nowrap;'> <span class="link">设置层次专业</span></th> -->
							</tr>
							<%
								
									int totalItems = db.countselect(sql);
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
								String link = "&id=" + courseId;
								//----------分页结束---------------
								rs = db.execute_oracle_page(sql, pageInt, pagesize);
								int a = 0;
								while (rs != null && rs.next()) {
									a++;
									String id = fixnull(rs.getString("id"));
									String querstion = fixnull(rs.getString("querstion"));
									String login_id = fixnull(rs.getString("login_id"));
									String twy_date = rs.getString("twy_date");
							%>
							<tr class='<%if(a%2==0) {%>oddrowbg<%} else {%>evenrowbg<%} %>'>
								<%-- <td class='select' align='center'> <input type='checkbox' class='checkbox' name='ids' value='<%%>' id='<%%>'> 
              </td>
              <td style='text-align: center; '><span class="link" title='详细信息' onClick="window.open('pici_info.jsp?id=<%%>')"><img src='/entity/manager/statistics/images/buttons/csv.png' alt='详细信息' width="20" height="20" title='详细信息'></span> 
              </td>
              <td style='text-align: center; '><% if(){%> <span class="link" title='锁定' ><img src='/entity/manager/statistics/images/buttons/active.png' alt='锁定' width="20" height="20" title='锁定' onClick="javascript:jiesuo('<%%>','<%%>');"></span> <% }else{ %> <span><img src='/entity/manager/statistics/images/buttons/multi_activate.png' alt='解锁' width="20" height="20" title='解锁'></span><%} %>
              </td>
              <td style='text-align: center; '><span class="link" title='编辑'><a href="pici_edit.jsp?pageInt=<%%>&id=<%%>"><img src='/entity/manager/statistics/images/buttons/edit.png' alt='编辑' width="20" height="20" title='编辑' border=0></a></span> 
              </td>
              <td style='text-align: center; '><span class="link" title='删除'><a href="javascript:cfmdel('pici_delexe.jsp?pageInt=<%%>&id=<%%>')"><img src='/entity/manager/statistics/images/buttons/delete.png' alt='删除' width="20" height="20" title='删除' border=0></a></span> 
              </td> --%>

								<td style='white-space: nowrap; text-align: center;'><strong><%=a%></strong></td>
								<td style='white-space: nowrap; text-align: center;'><span><a href="/entity/manager/basic/onlineCourseTwyDetail.jsp?id=<%=id %>" target="_blank" ><%if(querstion.length() < 50){%><%=querstion%><%} else { %><%=querstion.substring(0,50)%>...<%} %></a></span></td>
								<td style='white-space: nowrap; text-align: center;'><%=twy_date%></td>
								<td style='white-space: nowrap; text-align: center;'><%=login_id%></td>
							</tr>
							<%
								}
								db.close(rs);
							%>
						</table>
						<%--          </form>--%>
						<!-- end:内容区－列表区域 -->
					</div>

					<!-- 内容区域结束 -->
				</td>
			</tr>
			<tr>
				<td height="48" align="center" class="pageBottomBorder">	<%@ include file="../pub/dividepage.jsp"%>
				</td>
			</tr>
				<tr>
				<td height="48" align="center" class="pageBottomBorder"><button value="关闭" onclick="window.close();" class="name">关闭</button>
				</td>
			</tr>
		</table>
</body>
</html>