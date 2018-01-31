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
function delFromOrder(openCourseId,myBatchId){
	if(null != openCourseId && "" != openCourseId && null != myBatchId && "" != myBatchId){
		var url="/entity/workspaceStudent/studentWorkspace_delCourseToBatch.action?openCourseId=" + openCourseId + "&myBatchId=" + myBatchId;
		window.location.href = url;
	}else{
		alert("删除失败！");
		history.go(0);
	}
}
</script>
	</head>

	<body>
		<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td>
					<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
					</table>
					<table width="706" border="0" align="left" cellpadding="0" cellspacing="0">
						<form id="frm2" action="/entity/workspaceStudent/studentWorkspace_searchViewBatchCourse.action" name="form" method="post">
							<input id="endDate" name="endDate" type="hidden" value="<s:property value="#session.endDate" />" />
							<input type="hidden" name="startIndex" value="<s:property value="startIndex" />" />
							<input type="hidden" name="pageSize" value="<s:property value="pageSize" />" />
							<input type="hidden" name="mybatchid" value="<s:property value="#session.mybatchid"/>" />
						<tr>
							<td width="706" valign="top">
								<div class="main_title">
									查看已选课程
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
											<input type="text" name="courseName" class="sub" value="<s:property value="courseName" />" size="13" />
										</td>
										<td width="33%" align="left">
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
										<td nowarp="true" width="40%" align="left">
											大纲分类：
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
							<td height="30">
								<table width="100%" border="0" cellpadding="0" cellspacing="0" class="myTable">
									<tr bgcolor="#8dc6f6">
										<td align="center">
											课程编号
										</td>
										<td align="center">
											课程名称
										</td>
										<td align="center">
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
										<td align="center">
											期限
										</td>
										<td align="center">
											价格(元)
										</td>
										<td align="center">
											专项性质
										</td>
										<td align="center">
											操作
										</td>
									</tr>
									<s:if test="page.getItems().size() > 0">
										<s:bean name="com.whaty.platform.entity.web.action.teaching.elective.SearchAnyStudentAction" id="sas" />
										<s:set name="totalCourse" value="0"></s:set>
										<s:set name="totalTime" value="0"></s:set>
										<s:set name="hasCourse" value="0"></s:set>
										<s:set name="hasTime" value="0"></s:set>
										<s:set name="chooseCourse" value="0"></s:set>
										<s:set name="chooseTime" value="0"></s:set>
										<s:iterator value="page.items" id="cp" status="st">
										<s:if test="#cp[4]=='必修'">
											<s:set name="hasCourse" value="#hasCourse + 1" />
											<s:set name="hasTime" value="#sas.CalcTime(#hasTime,#cp[8])" />
										</s:if>
										<s:else>
											<s:set name="chooseCourse" value="#chooseCourse + 1" />
											<s:set name="chooseTime" value="#sas.CalcTime(#chooseTime,#cp[8])" />
										</s:else>
											<s:if test="'choose1'==#cp[12]">
												<tr style="background-color:#F0FFFF;">
											</s:if>
											<s:else>
												<tr>
											</s:else>
											<tr>
												<td align="center" width="8%">
													<s:property value="#cp[2]" />
												</td>
												<td align="center" style="display: none;">
													<s:property value="#cp[2]" />
												</td>
												<td align="center">
													<a style="color: #3366ff; cursor: pointer;" onclick="window.open('/entity/function/coursenotequery.jsp?course_id=<s:property value="#cp[1]"/>','newwindow_detail')"> <s:property value="#cp[3]" /> </a>
												</td>
												<td align="center" width="8%">
													<s:property value="#cp[4]" />
												</td>
												<td align="center" width="8%">
													<s:property value="#cp[5]" />
												</td>
												<td align="center" width="10%">
													<s:property value="#cp[7]" />
												</td>
												<td align="center" width="8%">
													<s:property value="#cp[9]" />
												</td>
												<td align="center" width="6%">
													<s:property value="#cp[6]==null?'-':#cp[6]" />
												</td>
												<td align="center" width="6%">
													<s:property value="#cp[8]" />
												</td>
												<td align="center" width="6%">
													<s:property value="#cp[10]" />
												</td>
												<td align="center" width="8%">
													<s:property value="#cp[11]" />
												</td>
												<td align="center" width="8%">
													<s:property value="#cp[15]" />
												</td>
												<td align="center" width="6%">
													<s:if test="0==#cp[14]">
														<!-- 必选 -->
														<s:if test="'choose1'==#cp[12]">
															<span title="必选课程不能删除">&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;&nbsp;</span>
														</s:if>
														<s:else>
															<a style="color: red; cursor: pointer;" onclick="delFromOrder('<s:property value="#cp[0]" />','<s:property value="#cp[13]" />')">删除</a>
														</s:else>
													</s:if>
													<s:else>
														<span title="已生成订单，不允许操作">&nbsp;&nbsp;-&nbsp;&nbsp;</span>
													</s:else>
												</td>
											</tr>
										</s:iterator>
										<tr>
											<s:set name="totalTime" value="#hasTime + #chooseTime" />
											<s:set name="totalCourse" value="#hasCourse + #chooseCourse" />
											<td colspan="12" align="left">&nbsp;&nbsp;共计<s:property value="#totalCourse" />门课程、<s:property value="#totalTime" />学时。其中必修<s:property value="#hasCourse" />门、<s:property value="#hasTime" />学时，选修<s:property value="#chooseCourse" />门、<s:property value="#chooseTime" />学时。</td>
										</tr>
										<tr style="height: 30px;">
											<td colspan="12">
												<div id="fany">
													<script>
														var page1 = new showPages("page1",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
														page1.printHtml();
													</script>
												</div>
											</td>
										</tr>
										<tr height="30">
											<td colspan="12" align="center">
												<input type="button" onclick="javascript:window.open('/entity/workspaceStudent/studentWorkspace_paymentSpecialTraining.action?batchId=<s:property value="#session.mybatchid"/>', 'newwindow');" value="去支付" />
												&nbsp;&nbsp;&nbsp;&nbsp;
												<input type="button" onclick="javascript:window.location.href='/entity/workspaceStudent/studentWorkspace_tospecialenrolment.action'" value="返回" />
											</td>
										</tr>
									</s:if>
									<s:else>
										<tr>
											<td colspan="12" align="center"><font color="red">*未选择任何课程，或专项培训课程均已学习。</font></td>
										</tr>
									<tr height="30">
										<td colspan="12" align="center">
											<input type="button" onclick="javascript:window.location.href='/entity/workspaceStudent/studentWorkspace_tospecialenrolment.action'" value="返回" />
										</td>
									</tr>
									</s:else>
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