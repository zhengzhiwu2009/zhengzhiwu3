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

		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
		<link rel="stylesheet" type="text/css" media="all" href="/js/calendar/calendar-win2000.css"/>
		<script language="JavaScript">

	var newWindow;
	function searSubmit(){
		document.forms.frm.startIndex.value= 0;
		document.forms.frm.submit();
	}

 	function learnCourse(courseId, opencourseId, passStudy) {
		if (!newWindow || newWindow.closed) {
			newWindow = window.open('/sso/bzzinteraction_InteractionStuManage.action?course_id='+courseId+'&opencourseId='+opencourseId+'&passStudy='+passStudy,'newwindow');
		} else {
			alert("请先关闭当前学习的课程");
			return;
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
function toRePayment(opencourseId) {
	if(confirm('确定重新购买此课程吗？\n注：原课程记录不会被删除')){
		//window.open('/entity/workspaceStudent/studentWorkspace_incompleteCoursePayment.action?opencourseId='+opencourseId, 'newwindow_rebuy');
		window.open('/entity/bzz-students/check_rep.jsp?opencourseId='+opencourseId, 'newwindow_rebuy');
	}
}
</script>
	</head>
	<body>
		<form action="/entity/workspaceStudent/studentWorkspace_toPassedCourses.action" name="frm" method="post">
			<input type="hidden" name="startIndex" value="<s:property value="startIndex" />" />
			<input type="hidden" name="pageSize" value="<s:property value="pageSize" />" />
			<div class="" style="width: 100%; padding: 0; margin: 0;" align="center">
				<div class="main_title">
					已过期课程
				</div>
				<div>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="33%" align="left">
								课程编号：<input type="text" name="courseCode" value="<s:property value="courseCode"/>" />
							</td>
							<td width="33%" align="left">
								课程名称：<input type="text" name="courseName" value="<s:property value="courseName" />" />
							</td>
							<td width="33%" align="left">
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
						</tr>
						<tr>
							<td align="left">
								课程学时：<input type="text" name="time" class="sub" value="<s:property value="time" />" />
							</td>
							<td align="left">
								主讲人：<input type="text" name="teacher" class="sub" value="<s:property value="teacher" />" />
							</td>
							<td align="left">
								所属区域：
								<select name="coursearea" class="sub">
									<option value="" selected>
										全部
									</option>
									<s:iterator value="courseareaList" id="type">
										<s:if test="#type.id != 'Coursearea4' && #type.id != 'Coursearea5'">
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
						</tr>
						<tr>
							<td align="left" colspan="2">
								获得学时时间：
								<input type="text" style="width: 100px" name="selSendDateStart" id="selSendDateStart" readonly="readonly" value="<s:property value="selSendDateStart"/>" />
								<img src="/js/calendar/img.gif" width="20" height="14" id="f_trigger_b" style="border: 0px solid #551896; cursor: pointer;" title="Date selector" onmouseover="this.style.background='white';" onmouseout="this.style.background=''" />
								至
								<input type="text" style="width: 100px" name="selSendDateEnd" id="selSendDateEnd" readonly="readonly" value="<s:property value="selSendDateEnd"/>" />
								<img src="/js/calendar/img.gif" width="20" height="14" id="f_trigger_c" style="border: 0px solid #551896; cursor: pointer;" title="Date selector" onmouseover="this.style.background='white';" onmouseout="this.style.background=''" />
							</td>
							<td align="right">
								<input type="button" value="&nbsp;查&nbsp;找&nbsp;" onclick="searSubmit();" />
								<input type="button" value="&nbsp;清&nbsp;空&nbsp;" onclick="resetContent();" />
							</td>
						</tr>
					</table>
					<br />
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
							<th width="9%">
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
								获得学时时间
							</th>
							<th width="10%">
								考试结果
							</th>
							<th width="10%">
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
										<a style="color: #3366ff; cursor: pointer;" onclick="window.open('/cms/flkcalone.htm?myId=<s:property value="#list[0]"/>','newwindow_detail')"'><s:property value="#list[2]" /> </a>
										<!-- 
										<a style="color: #3366ff; cursor: pointer;" onclick="window.open('/entity/function/coursenotequery.jsp?course_id=<s:property value="#list[0]"/>','newwindow_detail')"><s:property value="#list[2]" /></a>
										 -->
									</td>
									<td>
										<s:if test="#list[7].length()>3">
											<s:property value="#list[7].substring(0,3)" />...</s:if>
										<s:else>
											<s:property value="#list[7]" />
										</s:else>
									</td>
									<td>
										<s:property value="#list[9]" />
									</td>
									<td>
										<s:property value="#list[4]" />
									</td>
									<td>
										<s:property value="#list[3] + '学时'" default="未设置" />
									</td>
									<td>
										<s:property value="#list[10]" />
									</td>
									<td>
										<s:if test="#list[11] == 'NOEXAM'">不考试</s:if>
										<s:elseif test="#list[11] == 'PASS'">通过</s:elseif>
										<s:else>未通过</s:else>
									</td>
									<td>
										<s:if test="#list[13]==0">
											<span title="直播课程请在在线直播报名中查看记录">&nbsp;&nbsp;-&nbsp;&nbsp;</span>
										</s:if>
										<s:else>
											<div class="ck" style="width: 70px; background-image: url('/entity/bzz-students/images/index_22.jpg')">
												<a style="cursor: pointer;" onclick="toRePayment('<s:property value="#list[6]"/>');">重新购买</a><br/>
											</div>
											<div class="ck" style="width: 70px; background-image: url('/entity/bzz-students/images/index_22.jpg')">
												<a style="cursor: pointer;" href="/entity/workspaceStudent/studentWorkspace_courseInfo.action?courseId=<s:property value="#list[0]"/>&opencourseId=<s:property value="#list[6]"/>&sid=<s:property value="#request.sid" />&courseCode=<s:property value="#list[1]"/>" target=_blank>学习记录</a>
											</div>
										</s:else>
									</td>
								</tr>
							</s:iterator>
						</s:if>
						<tr>
							<td colspan="9" align="left">
								通过课程学时总数：
								<s:property value="#request.a_list" />
								&nbsp;学时，其中必修：
								<s:property value="#request.b_list" />
								&nbsp;学时；选修：
								<s:property value="#request.c_list" />
								&nbsp;学时。
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
	<script type="text/javascript">
    Calendar.setup({
        inputField     :    "selSendDateStart",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_b",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
    Calendar.setup({
        inputField     :    "selSendDateEnd",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_c",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
</script>
</html>