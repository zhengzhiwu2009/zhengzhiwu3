<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String d = String.valueOf(Math.random());
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
		<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.STYLE1 {
	color: #FF0000;
	font-weight: bold;
}

.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}

.myTable,.myTable td {
	border: 1px solid #cccccc;
	border-collapse: collapse;
}
-->
</style>
		<script type="text/javascript">
			Date.prototype.format = function(fmt)   
			{ //author: meizz   
			  var o = {   
			    "M+" : this.getMonth()+1,                 //月份   
			    "d+" : this.getDate(),                    //日   
			    "h+" : this.getHours(),                   //小时   
			    "m+" : this.getMinutes(),                 //分   
			    "s+" : this.getSeconds(),                 //秒   
			    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
			    "S"  : this.getMilliseconds()             //毫秒   
			  };   
			  if(/(y+)/.test(fmt))   
			    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
			  for(var k in o)   
			    if(new RegExp("("+ k +")").test(fmt))   
			  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
			  return fmt; 
			}
			function addToOrder(id){
				var ed = document.getElementById("endDate").value;
				var endDate = ed + " 23:59:59";
				endDate = ed;
				var endDate_ = new Date(endDate.replace(/\-/g, "\/"));
				var now =  new Date().format("yyyy-MM-dd hh:mm:ss");
				var now_ = new Date(now.replace(/\-/g, "\/"));
				var pass = now_ - endDate_;
				if(pass < 0){
					var url='/entity/workspaceStudent/studentWorkspace_addCourseToBatch.action?mybatchid=<s:property value="#session.mybatchid"/>' +  "&opId=" + id + "&endDate=" + endDate;
					window.location.href = url;
				}else{
					alert("抱歉，此次专项报名已结束，无法进行选课！");
				}
			}
</script>
	</head>
	<body bgcolor="#E0E0E0">
		<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td>
					<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
					</table>
					<form id="frm2" action="/entity/workspaceStudent/studentWorkspace_searchBatchCourse.action" name="form" method="post">
							<input id="endDate" name="endDate" type="hidden" value="<s:property value="#session.endDate" />" />
							<input type="hidden" name="startIndex" value="<s:property value="startIndex" />" />
							<input type="hidden" name="pageSize" value="<s:property value="pageSize" />" />
							<input type="hidden" name="mybatchid" value="<s:property value="#session.mybatchid"/>" />
						<table width="706" border="0" align="left" cellpadding="0" cellspacing="0">
							<tr>
								<td width="706" valign="top" align="left">
									<div class="main_title">
										专项选课
									</div>
									<div>
										<table border="0" class="msg">
											<tr>
												<td>
													<img src="/entity/bzz-students/images/index_04.jpg" />
													<b>①  课程列表显示全部可供学员报名的自选课程。
												</td>
											</tr>
											<tr>
												<td>
													<img src="/entity/bzz-students/images/index_04.jpg" />
													<b>②  若某门自选课程,学员曾在公共课程区成功报名，则此表中将不再显示该课程。</b>
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="33%" align="left">
												课程编号：
												<input type="text" name="courseCode" class="sub" value="<s:property value="courseCode" />" size="11" />
											</td>
											<td width="33%" align="left">
												课程名称：
												<input type="text" name="courseName" class="sub" value="<s:property value="courseName" />" size="11" />
											</td>
											<td align="left" width="33%">
												课程性质：
												<select name="courseType" class="sub">
													<option value="" selected>
														全部课程
													</option>
													<s:iterator value="courseTypeList" id="type">
														<s:if test="#type.id == courseType">
															<option value="<s:property value="#type.id" />" selected>
																<s:property value="#type.name" />
															</option>
														</s:if>
														<s:else>
															<option value="<s:property value="#type.id" />">
																<s:property value="#type.name" />
															</option>
														</s:else>
													</s:iterator>
												</select>
											</td>
										</tr>
										<tr>
											<td align="left">
												业务分类：
												<select name="courseCategory" class="sub">
													<option value="" selected>
														全部
													</option>
													<s:iterator value="courseCategoryList" id="category">
														<s:if test="#category.id == courseCategory">
															<option value="<s:property value="#category.id" />" selected>
																<s:property value="#category.name" />
															</option>
														</s:if>
														<s:else>
															<option value="<s:property value="#category.id" />">
																<s:property value="#category.name" />
															</option>
														</s:else>
													</s:iterator>
												</select>
											</td>
											<td nowarp="true" align="left">
												&nbsp;&nbsp;&nbsp;&nbsp;大纲分类：
												<select name="courseItemType" class="sub">
													<option value="" selected>
														全部
													</option>
													<s:iterator value="courseItemTypeList" id="courseItemType">
														<s:if test="#courseItemType.id == courseItemType">
															<option value="<s:property value="#courseItemType.id" />" selected>
																<s:property value="#courseItemType.name" />
															</option>
														</s:if>
														<s:else>
															<option value="<s:property value="#courseItemType.id" />">
																<s:property value="#courseItemType.name" />
															</option>
														</s:else>
													</s:iterator>
												</select>
											</td>
											<td align="left">
												按内容属性：
												<select name="courseContent" class="sub">
													<option value="" selected>
														全部
													</option>
													<s:iterator value="courseContentList" id="courseContent">
														<s:if test="#courseContent.id == courseContent">
															<option value="<s:property value="#courseContent.id" />" selected>
																<s:property value="#courseContent.name" />
															</option>
														</s:if>
														<s:else>
															<option value="<s:property value="#courseContent.id" />">
																<s:property value="#courseContent.name" />
															</option>
														</s:else>
													</s:iterator>
												</select>
											</td>
										</tr>
										<tr>
											<td align="left">
												主讲人：
												<input type="text" name="teacher" class="sub" value="<s:property value="teacher" />" size="11" />
											</td>
											<td align="right" colspan="3">
												<input type="submit" value="&nbsp;查&nbsp;找&nbsp;" />
												<input type="button" value="&nbsp;清&nbsp;空&nbsp;" onclick="resetContent()" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr align="center">
								<td>
									<table width="100%" border="0" cellpadding="0" cellspacing="0" class="myTable">
										<tr bgcolor="#8dc6f6">
											<td width="5%" align="center">
												课程编号
											</td>
											<td width="20%" align="center">
												课程名称
											</td>
											<td width="5%" align="center">
												课程性质
											</td>
											<td align="center">
												业务分类
											</td>
											<td align="center">
												大纲分类
											</td>
											<td align="center">
												内容属性
											</td>
											<td align="center">
												主讲人
											</td>
											<td align="center">
												学时
											</td>
											<td width="5%" align="center">
												期限(天)
											</td>
											<td width="5%" align="center">
												价格(元)
											</td>
											<td width="5%" align="center">
												操作
											</td>
										</tr>
										<s:if test="page.getItems().size() > 0">
											<s:iterator value="page.items" id="cp" status="st">
												<tr height=30>
													<td width="5%" align="center">
														<s:property value="#cp[2]" />
													</td>
													<td width="8%" align="center" style="display: none;">
														<s:property value="#cp[2]" />
													</td>
													<td width="8%" align="center">
														<!-- <a style="color: #3366ff; cursor: pointer;"
														onclick="window.open('/entity/function/coursenotequery.jsp?course_id=<s:property value="#cp[1]"/>','newwindow_detail')">
													-->
														<!-- Lee 课程预览 -->
														<a style="color: #3366ff; cursor: pointer;" onclick="window.open('/cms/tjkcalone.htm?myId=<s:property value="#cp[1]"/>','newwindow_detail')"><s:property value="#cp[3]" /> </a>
													</td>
													<td width="5%" align="center">
														<s:property value="#cp[4]" />
													</td>
													<td align="center">
														<s:property value="#cp[5]" />
													</td>
													<td align="center">
														<s:property value="#cp[7]" />
													</td>
													<td align="center">
														<s:property value="#cp[9]" />
													</td>
													<td width="5%" align="center">
														<s:property value="#cp[6]" />
													</td>
													<td width="5%" align="center">
														<s:property value="#cp[8]" />
													</td>
													<!-- td width="5%" align="center">
													<a style="color: #3366ff; cursor: pointer;"
														onclick="window.open('/entity/workspaceStudent/studentWorkspace_InteractionStuManage.action?course_id=<s:property value="#cp[1]" />','newwindow_teacher')"><s:property
															value="#cp[8]" /> </a>
	
												</td -->
													<td width="5%" align="center">
														<s:property value="#cp[10]" />
													</td>
													<td width="5%" align="center">
														<s:property value="#cp[11]" />
													</td>
													<!-- td width="5%" align="center">
													<s:property value="coursePrice(#cp[11], #request.price)" />
												</td--->
													<td width="5%" align="center">
														<!-- <a href="/entity/workspaceStudent/studentWorkspace_addCourseToPeriod.action?periodId=<s:property value="#session.periodId"/>&opId=<s:property value="#cp[0]" />" onclick="return info()" >添加</a>  -->
														<a style="color: red; cursor: pointer;" onclick="addToOrder('<s:property value="#cp[0]" />')">添加</a>
													</td>
												</tr>
											</s:iterator>
											<tr height=30>
												<td colspan=11>
													<div id="fany">
														<script>
														var page1 = new showPages("page1",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
														page1.printHtml();
													</script>
													</div>
												</td>
											</tr>
										</s:if>
										<s:else>
											<tr>
												<td colspan="11" align="center"><font color="red">*专项培训课程均已学习，或已选择。</font></td>
											</tr>
										</s:else>
										<tr height=30>
											<td colspan=11>
												<input type="button" onClick="javascript:window.location.href='/entity/workspaceStudent/studentWorkspace_tospecialenrolment.action'" value="返回" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr align=center>
								<td>
									<table border="0" cellpadding="0" cellspacing="0">

									</table>
								</td>
							</tr>
							<tr>
								<td width="13">
									&nbsp;
								</td>
							</tr>
						</table>
					</form>
				</td>
				<td width="13">
					&nbsp;
				</td>
			</tr>
		</table>
	</body>
	<script>
	function resetContent(){
		var content=document.getElementsByTagName("input");
		document.forms.frm2.reset();
		for(var i=0;i<content.length;i++){
			if(content[i].type=="text"){
				content[i].value="";
			}
		}
	}
		function info(){
			if (confirm("您确定要选择这门课程吗")){
			
			return true ;
			}else{
			return false ;
			} 
		}
		
		var x = 0;
		function addCourse(opId) {
			if(x>0) return;
			x++;
			var endDate = document.getElementById("endDate").value;
			window.location.href="/entity/workspaceStudent/studentWorkspace_addCourseToPeriod.action?periodId=<s:property value="#session.periodId"/>&opId="+opId+"&endDate=" + endDate;
		}
		
		function retu() {
			var endDate = document.getElementById("endDate").value;
			window.location.href="/entity/workspaceStudent/studentWorkspace_selectcourselist.action?periodId=<s:property value="#session.periodId"/>" + "&endDate=" + endDate;
		}
	</script>
</html>