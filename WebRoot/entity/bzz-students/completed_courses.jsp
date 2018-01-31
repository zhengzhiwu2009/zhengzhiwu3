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
		<base href="<%=basePath%>" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" />
		<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
		<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
		<script language="JavaScript">
	var newWindow;
	function searSubmit(){
		document.forms.frm.startIndex.value= 0;
		document.forms.frm.submit();
	}
	
 	function learnCourse(courseId, opencourseId) {
		if (!newWindow || newWindow.closed) {
			newWindow = window.open('/sso/bzzinteraction_InteractionStuManage.action?course_id='+courseId+'&opencourseId='+opencourseId+'&passStudy=1','newwindow');
		} else {
			alert("请先关闭当前学习的课程");
			return;
		}
	}
	function toRePayment(opencourseId) {
		if(confirm('确定重新购买此课程吗？\n注：原课程记录不会被删除')){
			//window.open('/entity/workspaceStudent/studentWorkspace_incompleteCoursePayment.action?opencourseId='+opencourseId, 'newwindow_rebuy');
			window.open('/entity/bzz-students/check_rep.jsp?opencourseId='+opencourseId, 'newwindow_rebuy');
			//window.location.href = "/entity/workspaceStudent/studentWorkspace_incompleteCoursePayment.action?opencourseId="+opencourseId;
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
//	searSubmit();
}
</script>
	</head>
	<body>
		<form action="/entity/workspaceStudent/studentWorkspace_toCompletedCourses.action" name="frm" method="post">
			<input type="hidden" name="startIndex" value="<s:property value="startIndex" />" />
			<input type="hidden" name="pageSize" value="<s:property value="pageSize" />" />
			<div class="" style="width: 100%; padding: 0; margin: 0;" align="center">
				<div class="main_title">
					待考试课程
				</div>
				<div>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="25%" align="left">
								课程编号：<input type="text" name="courseCode" class="sub" value="<s:property value="courseCode" />" />
							</td>
							<td width="30%" align="left">
								课程名称：<input type="text" name="courseName" class="sub" value="<s:property value="courseName" />" />
							</td>
							<td width="25%" align="left">
								课程学时： <input type="text" name="time" class="sub" value="<s:property value="time" />" />
							</td>
						</tr>
						<tr>
							<td align="left">
								所属区域：
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
							<td align="left">
								课程性质：
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
							<td align="left">
								&nbsp;&nbsp;&nbsp;主讲人：
								<input type="text" name="teacher" class="sub" value="<s:property value="teacher" />" />
							</td>
						</tr>
						<tr>
							<td width="13%" align="left">
								排序方式：
								<select name="orderInfo" class="sub">
									<option value="" selected>
										全部
									</option>
									<option value="pbtc_code" <s:if test="orderInfo == 'pbtc_code'">selected</s:if>>
										课程编号
									</option>
									<option value="tcs_get_date" <s:if test="orderInfo == 'tcs_get_date'">selected</s:if>>
										开始学习时间
									</option>
									<option value="lastTime" <s:if test="orderInfo == 'lastTime'">selected</s:if>>
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
							<td align="right" rowspan="2">
								<input type="button" value="&nbsp;查&nbsp;找&nbsp;" onclick="searSubmit();" />
								<input type="button" value="&nbsp;清&nbsp;空&nbsp;" onclick="resetContent();" />
							</td>
						</tr>
					</table>
					<table class="datalist" width="100%">
						<tr>
							<td colspan="10" align="left">
								<div id="fany">
									<script language="JavaScript">
		var page2 = new showPages("page2",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
		page2.printHtml();
		</script>
								</div>
							</td>
						</tr>
						<tr bgcolor="#8dc6f6">
							<th width="9%">
								课程编号
							</th>
							<th>
								课程名称
							</th>
							<th width="9%">
								主讲人
							</th>
							<th width="9%">
								所属区域
							</th>
							<th width="9%">
								课程性质
							</th>
							<th width="9%">
								课程学时
							</th>
							<th width="9%">
								开始时间
							</th>
							<th width="12%">
								课程过期时间
							</th>
							<th width="9%">
								考试结果
							</th>
							<th width="9%">
								课程学习
							</th>
						</tr>
						<s:if test="page != null">
							<s:iterator value="page.items" id="list">
								<tr>
									<td>
										<s:property value="#list[1]" />
									</td>
									<td>
										<a style="color: #3366ff; cursor: pointer;" onclick="window.open('/cms/flkcalone.htm?myId=<s:property value="#list[0]"/>&falg=true','newwindow_detail')"'><s:property value="#list[2]" /> </a>
										<!-- 													
										<a style="color: #3366ff; cursor: pointer;" onclick="window.open('/entity/function/coursenotequery.jsp?course_id=<s:property value="#list[0]"/>','newwindow_detail')"> <s:property value="#list[2]" />
										 -->
									</td>
									<td>
										<s:if test="#list[9].length()>3">
											<s:property value="#list[9].substring(0,3)" />...</s:if>
										<s:else>
											<s:property value="#list[9]" />
										</s:else>
									</td>
									<td>
										<s:property value="#list[15]" />
									</td>
									<td>
										<s:property value="#list[4]" />
									</td>
									<td>
										<s:property value="#list[3] + '学时'" default="未设置" />
									</td>
									<td>
										<s:date name="#list[5]" format="yyyy-MM-dd" />
									</td>
									<td>
										<s:if test="#list[18]=='Coursearea3'">
											-
										</s:if>
										<s:else>
											<s:property value="#list[14]" />
										</s:else>
									</td>
									<td>
										<s:if test="#list[18]!='Coursearea3' && #list[13] != 'GOBUY'&&!CompareTime(#list[5],#list[11])">已过期</s:if>
										<s:elseif test="#list[10] == 'NOEXAM'">不考试</s:elseif>
										<s:elseif test="#list[10] == 'PASS'">通过</s:elseif>
										<s:else>未通过</s:else>
									</td>
									<td>
										<div class="ck" style="width: 70px; background-image: url('/entity/bzz-students/images/index_21.jpg')">
											<s:if test="#list[17] == '40288a7b39968644013996bf01e7004b' || #list[18]=='Coursearea3'">
												<!--40288a7b39968644013996bf01e7004b表示免费课程  Coursearea3 表示公司内训课程 -->
												<a style="cursor: pointer;" onclick="learnCourse('<s:property value="#list[0]"/>','<s:property value="#list[8]"/>');">进入学习</a>
											</s:if>
											<s:else>
												<s:if test="#list[13] == 'GOBUY'">
													<a style="cursor: pointer;" onclick="toRePayment('<s:property value="#list[8]"/>');">去购买</a>
												</s:if>
												<s:elseif test="CompareTime(#list[5],#list[11])">
													<a style="cursor: pointer;" onclick="learnCourse('<s:property value="#list[0]"/>','<s:property value="#list[8]"/>');">进入学习</a>
												</s:elseif>
												<s:else>
													<a style="cursor: pointer;" onclick="toRePayment('<s:property value="#list[8]"/>');">重新购买</a>
												</s:else>
											</s:else>

										</div>
									</td>
								</tr>
							</s:iterator>
						</s:if>
						<tr>
							<td colspan="10" align="left">
								课程学时总数：
								<s:property value="#request.list" />
								&nbsp;学时
							</td>
						</tr>
						<tr>
							<td colspan="10" align="left">
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