<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.*"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'firstInfo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="../css/css.css" rel="stylesheet" type="text/css">
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">
	<STYLE type="text/css">
a:link {
	font-size: 18px;
	line-height:29px;
	font-weight: normal;
	color: blue;
	text-decoration: none;
	}
a:visited {
	font-size: 18px;
	line-height:29px;
	font-weight: normal;
	color: blue;
	text-decoration: none;
}
a:hover {
	font-size: 18px;
	line-height:29px;
	font-weight: normal;
	color: #FF8400;
	text-decoration: none;
}
a:active {
	font-size: 18px;
	line-height:29px;
	font-weight: normal;
	color: #000000;
	text-decoration: none;
}
	</STYLE>
	<%
		dbpool db = new dbpool();
		MyResultSet rs = null;
		
		UserSession us = (UserSession) session
			.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
	 %>
  </head>
  
  <body>
  <div id="main_content">
			<div class="content_title">
				提示信息
			</div>
			<div class="cntent_k">
				<div class="k_cc">
				<table align="center">
				<tr><td>
				<form action="/entity/information/peBulletinView_firstInfo.action" method="post">
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<div style="line-height: 150%"><span style="font-size: 12pt; line-height: 150%; font-family: 宋体"><s:property value="userName"/>：
</span><span style="font-size: 12pt; line-height: 150%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></div>
<p>&nbsp;</p>
<div style="text-indent: 24pt; line-height: 150%"><span style="font-size: 12pt; line-height: 150%; font-family: 宋体">您好！您所管理的学员在<select name="batch" onchange="this.form.submit();">
				<option value="">请选择</option>
				<%
				String sql = "select id,name,alert_selected from pe_bzz_batch where id<>'52cce2fd2471ddc30124764980580131'";
				rs=db.executeQuery(sql);
				
				String curBatch = (String)request.getAttribute("batch");
				//System.out.println("curBatch:"+curBatch);
				while (rs.next()) {
				
				String id=rs.getString("id");
				String name = rs.getString("name");
				String def = "";
				//String alert_selected=rs.getString("alert_selected");
				if( id.equals(curBatch)) {
					def=" selected=\"selected\"";
				}
				 %>
				<option value="<%=id %>" <%=def %>><%=name%></option>
				<%} 
				db.close(rs);
				%>
				</select>学期共有<b><s:property value="studentNum"/></b>名学员，学习情况如下：</span></div>
<div style="margin: 0cm 0cm 0pt 45pt; text-indent: -21pt; line-height: 150%"><span style="font-size: 12pt; line-height: 150%; font-family: Wingdings">l<span style="font: 7pt 'Times New Roman'">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span style="font-size: 12pt; line-height: 150%; font-family: 宋体">尚未登录学习的有</span><b><span style="font-size: 12pt; line-height: 150%; "><a href="/entity/information/studentStudyStatus.action?opt=noLogin&batch=<%=curBatch %>">&nbsp;<s:property value="noLoginStuNum"/>&nbsp;</a></span></b><span style="font-size: 12pt; line-height: 150%; font-family: 宋体">名<a href="/entity/information/studentStudyStatus.action?opt=noLogin&batch=<%=curBatch %>">【查看详细】</a>
<a href="/entity/manager/information/export_nologin_excel.jsp?batch_id=<%=curBatch %>">【导出学员列表】</a></span></div>
<div style="margin: 0cm 0cm 0pt 45pt; text-indent: -21pt; line-height: 150%"><span style="font-size: 12pt; line-height: 150%; font-family: Wingdings">l<span style="font: 7pt 'Times New Roman'">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span style="font-size: 12pt; line-height: 150%; font-family: 宋体">学习进度低于</span><span style="font-size: 12pt; line-height: 150%">25%</span><span style="font-size: 12pt; line-height: 150%; font-family: 宋体">的有</span><b><span style="font-size: 12pt; line-height: 150%"><a href="/entity/information/studentStudyStatus.action?opt=lssFFPc&batch=<%=curBatch %>">&nbsp;<s:property value="lessfPercentNum"/>&nbsp;</a></span></b><span style="font-size: 12pt; line-height: 150%; font-family: 宋体">名<a href="/entity/information/studentStudyStatus.action?opt=lssFFPc&batch=<%=curBatch %>">【查看详细】</a>
<a href="/entity/manager/information/export_progress_excel.jsp?type=1&batch_id=<%=curBatch %>">【导出学员列表】</a></span></div>
<div style="margin: 0cm 0cm 0pt 45pt; text-indent: -21pt; line-height: 150%"><span style="font-size: 12pt; line-height: 150%; font-family: Wingdings">l<span style="font: 7pt 'Times New Roman'">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span style="font-size: 12pt; line-height: 150%; font-family: 宋体">学习进度在</span><span style="font-size: 12pt; line-height: 150%">25%-50%</span><span style="font-size: 12pt; line-height: 150%; font-family: 宋体">的有</span><b><span style="font-size: 12pt; line-height: 150%"><a href="/entity/information/studentStudyStatus.action?opt=lssFGPcc&batch=<%=curBatch %>">&nbsp;<s:property value="lessfgPercentNum"/>&nbsp;</a></span></b><span style="font-size: 12pt; line-height: 150%; font-family: 宋体">名<a href="/entity/information/studentStudyStatus.action?opt=lssFGPcc&batch=<%=curBatch %>">【查看详细】</a>
<a href="/entity/manager/information/export_progress_excel.jsp?type=2&batch_id=<%=curBatch %>">【导出学员列表】</a></span></div>
<div style="margin: 0cm 0cm 0pt 45pt; text-indent: -21pt; line-height: 150%"><span style="font-size: 12pt; line-height: 150%; font-family: Wingdings">l<span style="font: 7pt 'Times New Roman'">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span style="font-size: 12pt; line-height: 150%; font-family: 宋体">学习进度在</span><span style="font-size: 12pt; line-height: 150%">50%-80%</span><span style="font-size: 12pt; line-height: 150%; font-family: 宋体">的有</span><b><span style="font-size: 12pt; line-height: 150%"><a href="/entity/information/studentStudyStatus.action?opt=lssFGPccc&batch=<%=curBatch %>">&nbsp;<s:property value="lessfgtPercentNum"/>&nbsp;</a></span></b><span style="font-size: 12pt; line-height: 150%; font-family: 宋体">名<a href="/entity/information/studentStudyStatus.action?opt=lssFGPccc&batch=<%=curBatch %>">【查看详细】</a>
<a href="/entity/manager/information/export_progress_excel.jsp?type=3&batch_id=<%=curBatch %>">【导出学员列表】</a></span></div>
<div style="margin: 0cm 0cm 0pt 45pt; text-indent: -21pt; line-height: 150%"><span style="font-size: 12pt; line-height: 150%; font-family: Wingdings">l<span style="font: 7pt 'Times New Roman'">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span style="font-size: 12pt; line-height: 150%; font-family: 宋体">学习进度在</span><span style="font-size: 12pt; line-height: 150%">80%-100%</span><span style="font-size: 12pt; line-height: 150%; font-family: 宋体">的有</span><b><span style="font-size: 12pt; line-height: 150%"><a href="/entity/information/studentStudyStatus.action?opt=grtFPc&batch=<%=curBatch %>">&nbsp;<s:property value="glfPercentNum"/>&nbsp;</a></span></b><span style="font-size: 12pt; line-height: 150%; font-family: 宋体">名<a href="/entity/information/studentStudyStatus.action?opt=grtFPc&batch=<%=curBatch %>">【查看详细】</a>
<a href="/entity/manager/information/export_progress_excel.jsp?type=4&batch_id=<%=curBatch %>">【导出学员列表】</a></span></div>
<div style="margin: 0cm 0cm 0pt 45pt; text-indent: -21pt; line-height: 150%"><span style="font-size: 12pt; line-height: 150%; font-family: Wingdings">l<span style="font: 7pt 'Times New Roman'">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span style="font-size: 12pt; line-height: 150%; font-family: 宋体">已完成基础课程学习的</span><b><span style="font-size: 12pt; line-height: 150%"><a href="/entity/information/studentStudyStatus.action?opt=completeJc&batch=<%=curBatch %>">&nbsp;<s:property value="completePercentNum"/>&nbsp;</a></span></b><span style="font-size: 12pt; line-height: 150%; font-family: 宋体">名<a href="/entity/information/studentStudyStatus.action?opt=completeJc&batch=<%=curBatch %>">【查看详细】</a>
<a href="/entity/manager/information/export_progress_excel.jsp?type=5&batch_id=<%=curBatch %>">【导出学员列表】</a></span></div>
<div style="margin: 0cm 0cm 0pt 45pt; text-indent: -21pt; line-height: 150%"><span style="font-size: 12pt; line-height: 150%; font-family: Wingdings">l<span style="font: 7pt 'Times New Roman'">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span style="font-size: 12pt; line-height: 150%; font-family: 宋体"> 还有</span><b><span style="font-size: 12pt; line-height: 150%"><a href="/entity/information/studentStudyStatus.action?opt=noPhoto&batch=<%=curBatch %>">&nbsp;<s:property value="noPhotoNum"/>&nbsp;</a></span></b><span style="font-size: 12pt; line-height: 150%; font-family: 宋体">名学员未上传照片<a href="/entity/information/studentStudyStatus.action?opt=noPhoto&batch=<%=curBatch %>">【查看详细】</a>
<a href="/entity/manager/information/export_progress_excel.jsp?type=6&batch_id=<%=curBatch %>">【导出学员列表】</a> 。</span></div>
<div style="text-indent: 24pt; line-height: 150%"><span style="font-size: 12pt; line-height: 150%; font-family: 宋体">请您根据学员学习的具体情况，有针对性的进行督促管理，保证所有学员能够顺利参加考试。</span></div> 
<div style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%"><span style="font-size: 12pt; color: black; line-height: 150%; font-family: 宋体">祝您  身体健康！工作顺利！</span></div>
<span style="font-size: 12pt; color: black; line-height: 150%; font-family: 宋体"><%
if (us!=null && us.getRoleId().equals("3"))
{
 %>
<a href="/entity/information/peBulletinView_clearCache.action">清空缓存</a>&nbsp;&nbsp;&nbsp;&nbsp;注： (如果需要更新统计人数，请点击该功能，但是不要频繁进行操作，否则不能缓解服务器压力)
<%
}
%></span></div>

</form>
</td></tr>
</table>
				</div>
			</div>
		</div>
		<div class="clear"></div>
  </body>
</html>
