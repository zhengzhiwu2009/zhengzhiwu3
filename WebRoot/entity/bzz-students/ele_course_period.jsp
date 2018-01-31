<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String d = String.valueOf(Math.random());
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
		<script type="text/javascript" src="/js/calendar/calendar.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
		<link rel="stylesheet" type="text/css" media="all"	href="/js/calendar/calendar-win2000.css"/>
	</head>
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

function toadd(id){
	var ed = document.getElementById(id).value.substring(0,21);
	var startDate = document.getElementById('start_'+id).value.substring(0,21);
	var endDate = ed ;
	var startDate_ = new Date(startDate.replace(/\-/g, "\/"));
	var endDate_ = new Date(endDate.replace(/\-/g, "\/"));
	var now =  new Date().format("yyyy-MM-dd hh:mm:ss");
	var now_ = new Date(now.replace(/\-/g, "\/"));
	var startPass = now_ - startDate_;
	if(startPass <0){
		alert("抱歉，选课期未开始，无法进行选课！");
		return;
	}
	var pass = now_ - endDate_;
	if(pass < 0){
		window.location.href="<%=request.getContextPath()%>/entity/workspaceStudent/studentWorkspace_searchCourse.action?periodId=" + id + "&endDate=" + ed;
	}else{
		alert("抱歉，选课期已结束，无法进行选课！");
	}
}
function tocourse(id){
	var endDate = document.getElementById(id).value.substring(0,21);
	window.location.href="<%=request.getContextPath()%>/entity/workspaceStudent/studentWorkspace_selectcourselist.action?periodId=" + id + "&endDate="+endDate;
}

function searSubmit(){
	document.forms.form.startIndex.value= 0;
	document.forms.form.submit();
}
function clearTime() {
				document.getElementById('electiveCoursePeriodName').value = '';
				document.getElementById('ktimestart').value = '';
				document.getElementById('ktimeend').value = '';
				document.getElementById('etimestart').value = '';
				document.getElementById('etimeend').value = '';
			}
</script>
	<body>
		<form
			action="entity/workspaceStudent/studentWorkspace_toElectiveCoursePeriod.action"
			name="form" method="post">
			<input type="hidden" name="startIndex"
				value="<s:property value="startIndex" />" />
			<input type="hidden" name="pageSize"
				value="<s:property value="pageSize" />" />
			<div style="width: 100%; padding: 0; margin: 0;" align="center">
				<div class="main_title">选课期报名</div>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td align="left">选课期名称：
							<input type=text style="width:150px" id="electiveCoursePeriodName" name="coursePeriodName" value="<s:property value="#session.coursePeriodName" />" />
						</td>
						 <td align="left">开始时间:
							<input type="text" style="width:80px" name="ktimestart" id="ktimestart" value="<s:property value="ktimestart"/>"/>
							<img src="/js/calendar/img.gif" width="20" height="14"
								id="f_trigger_a"
								style="border: 1px solid #551896; cursor: pointer;"
								title="Date selector"
								onmouseover="this.style.background='white';"
								onmouseout="this.style.background=''"/> 
							到
							<input type="text" style="width:80px" name="ktimeend" id="ktimeend" value="<s:property value="ktimeend"/>"/>
							<img src="/js/calendar/img.gif" width="20" height="14"
								id="f_trigger_b"
								style="border: 1px solid #551896; cursor: pointer;"
								title="Date selector"
								onmouseover="this.style.background='white';"
								onmouseout="this.style.background=''"/> 
						</td>
					</tr>
					<tr>
						<td align="left">结束时间:
							<input type="text" style="width:80px" name="etimestart" id="etimestart" value="<s:property value="etimestart"/>"/>
							<img src="/js/calendar/img.gif" width="20" height="14"
								id="f_trigger_c"
								style="border: 1px solid #551896; cursor: pointer;"
								title="Date selector"
								onmouseover="this.style.background='white';"
								onmouseout="this.style.background=''"/> 
							到
							<input type="text" style="width:80px" name="etimeend" id="etimeend" value="<s:property value="etimeend"/>"/>
							<img src="/js/calendar/img.gif" width="20" height="14"
								id="f_trigger_d"
								style="border: 1px solid #551896; cursor: pointer;"
								title="Date selector"
								onmouseover="this.style.background='white';"
								onmouseout="this.style.background=''"/> 
						</td>
						<td align="right">
							<input type="button" value="&nbsp;查&nbsp;找&nbsp;" onclick="searSubmit()" />
							<input type="button" value="&nbsp;清&nbsp;空&nbsp;" onclick="clearTime();"/>
						</td>
					</tr>
				</table>
				<div class="main_txt">
					<table class="datalist" width="100%">
						<tr>
							<td colspan="5" align="left">
								<div id="fany">
									<script language="JavaScript">
		var page2 = new showPages("page2",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
		page2.printHtml();
		</script>
								</div>
							</td>
						</tr>
						<tr bgcolor="#8dc6f6">
							<th align="center">
								选课期名称
							</th>
							<th width="16%" align="center">
								开始时间
							</th>
							<th width="16%" align="center">
								结束时间
							</th>
							
							<!-- <th width="10%" align="center">
								学时上限
							</th>
							
							<th width="10%" align="center">
								金额上限
							</th>
							 -->
							<th width="14%" align="center">
								已选课程学时数
							</th>
							<th width="20%" align="center">
								操作
							</th>
						</tr>
						<s:if test="page != null">
							<s:iterator value="page.items" id="cp" status="st">
								<tr>
									<td height="30">
										<s:property value="#cp[1]" />
									</td>
									<td>
										<s:property value="#cp[2]" />
									</td>
									<td>
									<s:property value="#cp[3]" />
									</td>
									<!-- 
									<td>
										<s:property value="#cp[4]" />
									</td>
									<td>
										<s:property value="#cp[6]" />
									</td>
									 -->
									<td>									
										<s:property value="#cp[5]" />
									</td>
									<td>
										<input type=button onclick=tocourse("<s:property value="#cp[0]"/>") value="查看已选课程"></input>&nbsp;&nbsp;
										<input type=button onclick=toadd("<s:property value="#cp[0]"/>") value="去选课"></input>
									    <input id="<s:property value="#cp[0]"/>" type="hidden" value="<s:property value="#cp[3]" />"></input>
									    <input id="start_<s:property value="#cp[0]"/>" type="hidden" value="<s:property value="#cp[2]" />"></input>
									</td>
								</tr>
							</s:iterator>
						</s:if>
						<!-- Lee start 2014年04月03日 添加已选课程学时总数-->
						<tr>
							<td colspan="5" align="left">
								&nbsp;&nbsp;
								<font color="red">所有选课期已选课程学时总数：<s:property
										value="yxkcxss" />
								</font>
							</td>
						</tr>
						<!-- Lee end -->
						<tr>
							<td colspan="5" align="left">
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
	</body>
	<script type="text/javascript">
	    Calendar.setup({
	        inputField     :    "ktimestart",     // id of the input field
	        ifFormat       :    "%Y-%m-%d",       // format of the input field
	        button         :    "f_trigger_a",  // trigger for the calendar (button ID)
	        align          :    "Tl",           // alignment (defaults to "Bl")
	        singleClick    :    true
	    });
	    Calendar.setup({
	        inputField     :    "ktimeend",     // id of the input field
	        ifFormat       :    "%Y-%m-%d",       // format of the input field
	        button         :    "f_trigger_b",  // trigger for the calendar (button ID)
	        align          :    "Tl",           // alignment (defaults to "Bl")
	        singleClick    :    true
	    });
	     Calendar.setup({
	        inputField     :    "etimestart",     // id of the input field
	        ifFormat       :    "%Y-%m-%d",       // format of the input field
	        button         :    "f_trigger_c",  // trigger for the calendar (button ID)
	        align          :    "Tl",           // alignment (defaults to "Bl")
	        singleClick    :    true
	    });
	     Calendar.setup({
	        inputField     :    "etimeend",     // id of the input field
	        ifFormat       :    "%Y-%m-%d",       // format of the input field
	        button         :    "f_trigger_d",  // trigger for the calendar (button ID)
	        align          :    "Tl",           // alignment (defaults to "Bl")
	        singleClick    :    true
	    });
	</script>
</html>