<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page import="java.util.*,java.text.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<?xml version="1.0" encoding="gb2312"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" /> 
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" /> 
		<link href="/entity/bzz-students/css.css" rel="stylesheet"
			type="text/css" />
	</head>
	<body>
	<form action="/entity/workspaceStudent/studentWorkspace_trainview.action" name="form" method="post">
	<input type="hidden" name="startIndex" value="<s:property value="startIndex" />" />
	<input type="hidden" name="pageSize" value="<s:property value="pageSize" />" />
	<input type="hidden" name="id" value="<s:property value="#request.id"/>"/>
	<div class="" style="width:100%;padding:0;margin:0; " align="center">
      <div class="main_title">专项培训课程</div>
      <div class="main_txt">
	  <table class="datalist" width="100%">
  <tr bgcolor="#8dc6f6">
    <th width="20%">课程编号</th>
    <th width="50%">课程名称</th>
    <th width="20%">课程性质</th>
    <th width="10%">学时</th>
    <!-- <th width="60">价格</th> -->
  </tr>
  <s:if test="page != null">
  	<s:iterator value="page.items" id="course">
							<tr height=30>
										<td>
											<s:property value="#course.peBzzTchCourse.code" />
										</td>
										<td>
											 <a style = "color:#3366ff; cursor: pointer;" onclick="window.open('/entity/function/coursenotequery.jsp?course_id=<s:property value="#course.peBzzTchCourse.id" />','newwindow_detail')" >
    											<s:property value="#course.peBzzTchCourse.name" />
											</a>
											
										</td>
										<td>
											<s:property value="#course.peBzzTchCourse.enumConstByFlagCourseType.name" />
										</td>
										<td>
											<s:property value="#course.peBzzTchCourse.time" />
										</td>
										<!-- <td>
											<s:property value="coursePrice(#course.peBzzTchCourse.time, #request.price)" />
										</td> -->
									</tr>
								</s:iterator>
  </s:if>
 <tr>
 	<td colspan="5" align="left">课程学时总数：<s:property value="#request.classHour"/></td>
 </tr>
  <tr>
    <td colspan="5" align="left"> 
      	<div id="fany">
      	<script language="JavaScript"  >
		var page1 = new showPages("page1",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
		page1.printHtml();
		</script>
      	</div>
      </td>
    </tr>
   <tr>
   	<td colspan="5" align="center"><div class="ck" style="width:70px;background-image:url('/entity/bzz-students/images/index_21.jpg')" ><a href="/entity/workspaceStudent/studentWorkspace_tospecialenrolment.action" >返回</a></div></td>
   </tr>
</table>
<br />
</div>
</div>
</form>
</body>
</html>
