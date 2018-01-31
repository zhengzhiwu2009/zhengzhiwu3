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
		<base href="<%=basePath%>"/>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" />
		<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
		<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<script language="JavaScript">
//$(document).ready(function() {
//	$(".sub").change(
//			function() {
//				document.forms.frm.startIndex.value= 0;
//				document.forms.frm.submit();
//});
//});
var newWindow = "";
function searSubmit(){
	document.forms.frm.startIndex.value= 0;
	document.forms.frm.submit();
}
function checkGoStudy(id) {
	if (!newWindow || newWindow.closed) {
		newWindow = window.open('/entity/workspaceRegStudent/regStudentWorkspace_InteractionStuManage.action?course_id='+id,'newwindow');
	} else {
		alert("请先关闭当前学习的课程");
		return;
	}
}

function toRePayment(opencourseId) {
	if(confirm('确定重新购买此课程吗？\n注：原课程记录不会被删除')){
		window.open('/entity/workspaceStudent/studentWorkspace_incompleteCoursePayment.action?opencourseId='+opencourseId, 'newwindow_rebuy');
	}
}

function resetContent(){
	var content=document.getElementsByTagName("input");
	document.forms.frm.reset();
	for(var i=0;i<content.length;i++){
		if(content[i].type=="text"){
			content[i].value="";
		}
	}
}
</script>
	</head>
	<body>
		<form action="/entity/workspaceRegStudent/regStudentWorkspace_toLearningCourses.action" name="frm" method="post">
			<input type="hidden" name="startIndex" value="<s:property value="startIndex" />" />
			<input type="hidden" name="pageSize" value="<s:property value="pageSize" />" />
			<div class="" style="width: 100%; padding: 0; margin: 0;" align="center">
				<div class="main_title">
					课程学习
				</div>
				<div>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="20%" align="right">
								课程编号：
							</td>
							<td width="13%" align="left">
								<input type="text" name="courseCode" class="sub" value="<s:property value="courseCode" />" />
							</td>
							<td width="20%" align="right">
								课程名称：
							</td>
							<td width="13%" align="left">
								<input type="text" name="courseName" class="sub" value="<s:property value="courseName" />" />
							</td>
							<td width="20%" align="right">
								课程学时：
							</td>
							<td width="13%" align="left">
								<input type="text" name="time" class="sub" value="<s:property value="time" />" />
							</td>
						</tr>
						<tr>
							<td width="20%" align="right">
								课程性质：
							</td>
							<td width="13%" align="left">
								<select name="courseType" class="sub">
									<option value="" selected>
										全部
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
							<td width="20%" align="right">
								大纲分类：
							</td>
							<td width="13%" align="left">
								<select name="courseItemType" class="sub">
									<option value="" selected>
										全部
									</option>
									<s:iterator value="courseItemTypeList" id="type">
										<s:if test="#type.id == courseItemType">
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
							<td width="20%" align="right">
								业务分类：
							</td>
							<td width="13%" align="left">
								<select name="courseCategory" class="sub">
									<option value="" selected>
										全部
									</option>
									<s:iterator value="courseCategoryList" id="type">
										<s:if test="#type.id == courseCategory">
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
							<td width="20%" align="right">
								内容属性：
							</td>
							<td width="13%" align="left">
								<select name="courseContent" class="sub">
									<option value="" selected>
										全部
									</option>
									<s:iterator value="courseContentList" id="type">
										<s:if test="#type.id == courseContent">
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
							<td width="20%" align="right">
								建议学习人群：
							</td>
							<td width="13%" align="left">
								<select name="suggestren" class="sub">
									<option value="" selected>
										全部
									</option>
									<s:iterator value="suggestrenList" id="type">
										<s:if test="#type.id == suggestren">
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
							<td width="20%" align="right">
								主讲人：
							</td>
							<td width="13%" align="left">
								<input type="text" name="teacher" class="sub" value="<s:property value="teacher" />" />
							</td>
						</tr>
						<tr>
							<td width="20%" align="right">
								所属区域：
							</td>
							<td width="13%" align="left">
								<select name="coursearea" class="sub">
									<option value="" selected>
										全部
									</option>
									<s:iterator value="courseareaList" id="type">
										<s:if test="#type.id != 'Coursearea0' && #type.id != 'Coursearea3' && #type.id != 'Coursearea4'">
											<s:if test="#type.id == coursearea">
												<option selected value="<s:property value="#type.id" />">
													<s:property value="#type.name" />
												</option>
											</s:if>
											<s:else>
												<option value="<s:property value="#type.id" />">
													<s:property value="#type.name" />
												</option>
											</s:else>
										</s:if>
									</s:iterator>
								</select>
							</td>
							<td width="20%" align="right">
								学习状态：
							</td>
							<td width="13%" align="left">
								<select name="learnStatus" class="sub">
									<option value="" selected>
										全部
									</option>
									<s:iterator value="learnStutusList" id="type">
										<s:if test="#type.code == learnStatus">
											<option selected value="<s:property value="#type.code" />">
												<s:property value="#type.name" />
											</option>
										</s:if>
										<s:else>
											<option value="<s:property value="#type.code" />">
												<s:property value="#type.name" />
											</option>
										</s:else>
									</s:iterator>
								</select>
							</td>
							<td colspan="2" align="right">
								排序方式：
								<select name="orderInfo" class="sub">
									<option value="" selected>
										全部
									</option>
									<option value="code" <s:if test="orderInfo == 'code'">selected</s:if>>
										课程编号
									</option>
									<option value="start_time" <s:if test="orderInfo == 'start_time'">selected</s:if>>
										开始学习时间
									</option>
									<option value="last_time" <s:if test="orderInfo == 'last_time'">selected</s:if>>
										最后学习时间
									</option>
								</select>
								<select name="orderType" class="sub">
									<option value="asc" <s:if test="orderType == 'asc'">selected</s:if>>
										正序
									</option>
									<option value="desc" <s:if test="orderType == 'desc'">selected</s:if>>
										倒序
									</option>
								</select>
								<input type="button" value="&nbsp;查&nbsp;找&nbsp;" onclick="searSubmit();" />
								<input type="button" value="&nbsp;清&nbsp;空&nbsp;" onclick="resetContent();" />
							</td>
						</tr>
					</table>
					<table class="datalist" width="100%">
						<tr>
							<td colspan="9" align="left">
								<div id="fany">
									<script language="JavaScript">
										var page2 = new showPages("page2",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
										page2.printHtml();
									</script>
								</div>
							</td>
						</tr>
						<tr bgcolor="#8dc6f6">
							<th width="10%">
								课程编号
							</th>
							<th>
								课程名称
							</th>
							<th width="10%">
								主讲人
							</th>
							<th width="10%">
								所属区域
							</th>
							<th width="10%">
								课程性质
							</th>
							<th width="10%">
								课程学时
							</th>
							<th width="10%">
								学习状态
							</th>
							<th width="10%">
								课程学习
							</th>
							<th nowrap="true" style="display: none;">
								学习记录
							</th>
						</tr>
						<s:if test="page != null">
							<s:iterator value="page.items" id="electiveCourse" status="item">
								<tr>
									<td>
										<s:property value="#electiveCourse[1]" />
									</td>
									<td>
										<a style="color: #3366ff; cursor: pointer;" onclick="window.open('/cms/flkcalone.htm?myId=<s:property value="#electiveCourse[0]"/>','newwindow_detail')"'><s:property value="#electiveCourse[2]" /> </a>
									</td>
									<td>
										<s:property value="#electiveCourse[3]" />
									</td>
									<td>
										<s:property value="#electiveCourse[4]" />
									</td>
									<td>
										<s:property value="#electiveCourse[5]" />
									</td>
									<td>
										<s:property value="#electiveCourse[6] + '学时'" default="未设置" />
									</td>
									<td>
										<s:if test="#electiveCourse[8]=='INCOMPLETE'">正在学习</s:if>
										<s:elseif test="#electiveCourse[8]=='COMPLETED'">已完成学习</s:elseif>
										<s:else>未开始学习</s:else>
									</td>
									<td>
										<div class="ck" style="width: 70px; background-image: url('/entity/bzz-students/images/index_22.jpg')">
											<a style="cursor: pointer;" onclick="checkGoStudy('<s:property value="#electiveCourse[0]"/>')">进入学习</a>
										</div>
									</td>
								</tr>
							</s:iterator>
						</s:if>
						<tr>
							<td colspan="9">
								<table width="100%">
									<tr>
										<td style="border: 0px;">
											课程学时总计：
											<s:property value="#request.totalNum" />
											&nbsp;学时
										</td>
										<td style="border: 0px;">
											正在学习课程学时总计：
											<s:property value="#request.incomNum" />
											&nbsp;学时
										</td>
										<td style="border: 0px;">
											未开始学习课程学时总计：
											<s:property value="#request.uncomNum" />
											&nbsp;学时
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="9" align="left">
								<div id="fany">
									<script language="JavaScript">
										var page1 = new showPages("page1",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
										page1.printHtml();
									</script>
								</div>
							</td>
						</tr>
					</table>
					<br />
				</div>
			</div>
		</form>
	</body>
</html>
