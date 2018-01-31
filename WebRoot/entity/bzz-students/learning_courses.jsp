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
function checkGoStudy(id, courseId, opId) {
	var url1 = '/entity/workspaceStudent/studentWorkspace_checkGoStudy.action';
     $.ajax({
	type: "post",
	url: url1,
	dataType: "json",
	data:{electiveId:id},
  	success: function(data, textStatus){
		var info = data['info'];
		if(info=='false') {
			if (!newWindow || newWindow.closed) {
				newWindow = window.open('/sso/bzzinteraction_InteractionStuManage.action?course_id='+courseId+'&opencourseId='+opId+'&passStudy=1','newwindow')
			} else {
				alert("请先关闭当前学习的课程");
				return;
			}
		} else {
			alert('课程已经申请退费，不能学习');
			return;
		}
 	},
	error: function(){
		alert('好像出问题了，请重新点击菜单');
		return;
	}
  });
}

function toRePayment(opencourseId) {
	if(confirm('确定重新购买此课程吗？\n注：原课程记录不会被删除')){
		//window.open('/entity/workspaceStudent/studentWorkspace_incompleteCoursePayment.action?opencourseId='+opencourseId, 'newwindow_rebuy');
		window.open('/entity/bzz-students/check_rep.jsp?opencourseId='+opencourseId, 'newwindow_rebuy');
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
		<form action="/entity/workspaceStudent/studentWorkspace_toLearningCourses.action" name="frm" method="post">
			<input type="hidden" name="startIndex" value="<s:property value="startIndex" />" />
			<input type="hidden" name="pageSize" value="<s:property value="pageSize" />" />
			<div class="" style="width: 100%; padding: 0; margin: 0;" align="center">
				<div class="main_title">
					正在学习课程
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
										<s:if test="#type.id != 'Coursearea0' && #type.id != 'Coursearea4' && #type.id != 'Coursearea5'">
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
								排序方式：
							</td>
							<td width="13%" align="left">
								<select name="orderInfo" class="sub">
									<option value="" selected>
										全部
									</option>
									<option value="pbtc_code" <s:if test="orderInfo == 'pbtc_code'">selected</s:if>>
										课程编号
									</option>
									<option value="tcs_get_date"<s:if test="orderInfo == 'tcs_get_date'">selected</s:if>>
										开始学习时间
									</option>
									<option value="lastTime"<s:if test="orderInfo == 'lastTime'">selected</s:if>>
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
							</td>
							<td colspan="2" align="right">
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
							<th width="12%">
								课程过期时间
							</th>
							<th width="10%">
								学习状态
							</th>
							<th width="10%">
								课程学习
							</th>
							<th nowrap="true" style="display:none;">
								学习记录
							</th>
						</tr>
						<s:if test="page != null">
							<s:iterator value="page.items" id="electiveCourse" status="item">
								<s:set name="sDate" value="new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss').parse(#electiveCourse[9])" />
								<tr>
									<td>
										<s:property value="#electiveCourse[3]" />
									</td>
									<td>
										<a style="color: #3366ff; cursor: pointer;" onclick="window.open('/cms/flkcalone.htm?myId=<s:property value="#electiveCourse[2]"/>&flag=true','newwindow_detail')"'><s:property value="#electiveCourse[4]" /> </a>
										<!-- 查看页面没做完
										<a style="color: #3366ff; cursor: pointer;" onclick="window.open('/entity/bzz-students/courseInfo.jsp?myId=<s:property value="#electiveCourse[2]"/>','newwindow_detail')"'><s:property value="#electiveCourse[4]" /> </a>
										 -->
										<!-- 截取课程名称方式 使表格没那么狰狞
										<a style="color: #3366ff; cursor: pointer;" onclick="window.open('/entity/function/coursenotequery.jsp?course_id=<s:property value="#electiveCourse[2]"/>','newwindow_detail')"  title='<s:property value="#electiveCourse[4]" />' ><s:property value="#electiveCourse[4].substring(0,6)" />...</a>
										 -->
									</td>
									<td>
										<s:if test="#electiveCourse[5].length()>3">
											<s:property value="#electiveCourse[5].substring(0,3)" />...</s:if>
										<s:else>
											<s:property value="#electiveCourse[5]" />
										</s:else>
									</td>
									<td>
										<s:property value="#electiveCourse[6]" />
									</td>
									<td>
										<s:property value="#electiveCourse[7]" />
									</td>
									<td>
										<s:property value="#electiveCourse[8] + '学时'" default="未设置" />
									</td>
									<td>
										<s:if test="#electiveCourse[13]==1">
											<span title="免费课程">-</span>
										</s:if>
										<s:else>
											<s:bean name="com.whaty.platform.entity.web.action.workspaceStudent.StudentWorkspaceAction" id="swa"></s:bean>
											<s:set name="sd" value="#electiveCourse[9]"/>
											<s:set name="ss" value="#electiveCourse[10]"/>
											<s:property value="#swa.CalcDate(#sd,#ss)" />
										</s:else>
									</td>
									<td>
										<s:bean name="com.whaty.platform.entity.web.action.workspaceStudent.StudentWorkspaceAction" id="swa"></s:bean>
										<s:set name="sd" value="#electiveCourse[9]"/>
										<s:set name="ss" value="#electiveCourse[10]"/>
										<s:if test="#swa.CompareTime(#sd,#ss)">
											<s:if test="#electiveCourse[12]=='INCOMPLETE'">未完成</s:if>
											<s:elseif test="#electiveCourse[12]=='COMPLETED'">已完成</s:elseif>
											<s:else>未学习</s:else>
										</s:if>
										<s:else>
											<s:if test="#electiveCourse[13]==1">
												<s:if test="#electiveCourse[12]=='INCOMPLETE'">未完成</s:if>
												<s:elseif test="#electiveCourse[12]=='COMPLETED'">已完成</s:elseif>
												<s:else>未学习</s:else>
											</s:if>
											<s:else>
									    		已过期
								    		</s:else>
								   		</s:else>
									</td>
									<td>
										<s:if test="#electiveCourse[15] == 0">
											<span title="在线直播课程请在在线直播报名中进行学习">&nbsp;-&nbsp;</span>
										</s:if>
										<s:else>
											<div class="ck" style="width: 70px; background-image: url('/entity/bzz-students/images/index_22.jpg')">
												<s:bean name="com.whaty.platform.entity.web.action.workspaceStudent.StudentWorkspaceAction" id="swa"></s:bean>
												<s:set name="sd" value="#electiveCourse[9]"/>
												<s:set name="ss" value="#electiveCourse[10]"/>
												<s:if test="#electiveCourse[11] != null && !#electiveCourse[11].equals(\"2\")"> 
											 		退费审核中
											 	</s:if>
												<s:elseif test="#swa.CompareTime(#sd,#ss)">
													<a style="cursor: pointer;" onclick="checkGoStudy('<s:property value="#electiveCourse[0]"/>', '<s:property value="#electiveCourse[2]"/>', '<s:property value="#electiveCourse[1]"/>')">进入学习</a>
												</s:elseif>
												<s:elseif test="#electiveCourse[13] == 1">
													<a style="cursor: pointer;" onclick="checkGoStudy('<s:property value="#electiveCourse[0]"/>', '<s:property value="#electiveCourse[2]"/>', '<s:property value="#electiveCourse[1]"/>')">进入学习</a>
												</s:elseif>
												<s:elseif test="#electiveCourse[13] == 0">
													<a style="cursor: pointer;" onclick="toRePayment('<s:property value="#electiveCourse[1]"/>');">重新购买</a>
												</s:elseif>
											</div>
										</s:else>
									</td>
									<td  style="display:none;">
										<s:if test="#electiveCourse[15] == 0">
											<span title="">&nbsp;-&nbsp;</span>
										</s:if>
										<s:else>
											<div class="ck" style="width: 70px; background-image: url('/entity/bzz-students/images/index_23.jpg')">
												<a href="/training/student/course/jumpCoursewareStatus.jsp?courseId=<s:property value="#electiveCourse[2]"/>&electiveId=<s:property value="#electiveCourse[0]"/>" target="_blank">查看</a>
											</div>
										</s:else>
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
