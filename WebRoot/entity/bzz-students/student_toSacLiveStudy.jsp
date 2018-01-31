<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
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
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" />
		<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
		<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
		<link rel="stylesheet" type="text/css" media="all" href="/js/calendar/calendar-win2000.css"></link>
		<script type="text/javascript">
		function searSubmit(){
			document.forms.form.startIndex.value= 0;
			document.forms.form.submit();
		}
		function resetContent(){
			var content=document.getElementsByTagName("input");
			document.forms.form.reset();
				for(var i=0;i<content.length;i++){
					if(content[i].type=="text"){
						content[i].value="";
					}
				}
		}
		</script>
	</head>
	<body>
		<form action="/entity/workspaceStudent/studentWorkspace_toSacLiveStudy.action" name="form" method="post">
			<input type="hidden" name="startIndex" value="<s:property value="startIndex" />" />
			<input type="hidden" name="pageSize" value="<s:property value="pageSize" />" />
			<div class="" style="width: 100%; padding: 0; margin: 0;" align="center">
				<div class="main_title">
					直播课程学习
				</div>
				<div class="msg" style="height:auto;">
					<table border="0">
						<tr>
							<td>
								<b>1、通过规则是学员获得直播课程学时的标准。学员累计在线学习直播课程时间除以累计直播时间的比值大于等于通过规则时，方可获得学时。</b>
							</td>
						</tr>
						<%
							try{
								Object ptp = ServletActionContext.getRequest().getAttribute("ptp");
								if (null == ptp || !"V".equalsIgnoreCase(ptp.toString())) {
						%>
						<tr>
							<td>
								<b>2、关于直播课程退费：自支付成功至课程直播的前一日可申请退费，直播当天及以后时间均不能再申请退费。</b>
							</td>
						</tr>
						<%
							}
							}catch(Exception e){
								e.printStackTrace();
							}
						%>
					</table>
				</div>
				<div style="overflow-x: auto; overflow-y: hidden;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="33%" align="left">
								课程编号：
								<input type="text" name="courseCode" class="sub" value="<s:property value="courseCode" />" />
							</td>
							<td width="33%" align="left">
								课程名称：
								<input type="text" name="courseName" class="sub" value="<s:property value="courseName" />" />
							</td>
							<td align="left" width="33%">
								主讲人：
								<input type="text" name="teacher" class="sub" value="<s:property value="teacher" />" />
							</td>
						</tr>
						<tr>
							<td align="left" colspan="2">
								&nbsp;&nbsp;预计直播时间：
								<input type="text" style="width: 100px" name="selSendDateStart" id="selSendDateStart" readonly="readonly" value="<s:property value="selSendDateStart"/>" />
								<img src="/js/calendar/img.gif" width="20" height="14" id="f_trigger_b" style="border: 0px solid #551896; cursor: pointer;" title="Date selector" onmouseover="this.style.background='white';" onmouseout="this.style.background=''" />
								至
								<input type="text" style="width: 100px" name="selSendDateEnd" id="selSendDateEnd" readonly="readonly" value="<s:property value="selSendDateEnd"/>" />
								<img src="/js/calendar/img.gif" width="20" height="14" id="f_trigger_c" style="border: 0px solid #551896; cursor: pointer;" title="Date selector" onmouseover="this.style.background='white';" onmouseout="this.style.background=''" />
							</td>
							<td width="13%" align="left">
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
							</td>
							<td align="right">
								<input type="button" value="&nbsp;查&nbsp;找&nbsp;" onclick="searSubmit();" />
								<input type="button" value="&nbsp;清&nbsp;空&nbsp;" onclick="resetContent();" />
							</td>
						</tr>
					</table>
					<table class="datalist" width="100%">
						<tr>
							<td colspan="18" align="left">
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
								预计直播<br />开始时间
							</th>
							<th width="9%">
								预计直播<br />结束时间
							</th>
							<th width="6%" title="通过规则">
								规则
							</th>
							<th width="8%">
								学时数
							</th>
							<th width="8%">
								主讲人
							</th>
							<th width="9%">
								学习状态
							</th>
							<th width="6%" title="累计学习时长(分钟)">
								累计
							</th>
							<th width="9%">
								获得学时
							</th>
							<th width="9%">
								学习详情
							</th>
							<th width="8%">
								去学习
							</th>
						</tr>
						<s:if test="page != null">
							<s:iterator value="page.items" id="webcast">
								<tr>
									<td>
										<s:property value="#webcast[1]" />
									</td>
									<td>
										<a style="color: #3366ff; cursor: pointer;" onclick="window.open('/cms/flkcalone.htm?myId=<s:property value="#webcast[11]"/>&flag=true','newwindow_detail')"'><s:property value="#webcast[2]" /></a>
									</td>
									<td>
										<s:property value="#webcast[3]" />
									</td>
									<td>
										<s:property value="#webcast[4]" />
									</td>
									<td>
										<s:property value="#webcast[5]" />
									</td>
									<td>
										<s:property value="#webcast[6]" />
									</td>
									<td>
										<s:property value="#webcast[7]" />
									</td>
									<td>
										<s:if test="#webcast[8]=='UNCOMPLETED'">
											未完成
										</s:if>
										<s:elseif test="#webcast[8]=='COMPLETED'">
											已完成
										</s:elseif>
										<s:else>
											未开始
										</s:else>
									</td>
									<td>
										<s:property value="#webcast[12]" default="-" />
									</td>
									<td>
										<s:property value="#webcast[13]" default="-" />
									</td>
									<td>
										<a href='/entity/workspaceStudent/studentWorkspace_showDetail.action?id=<s:property value="#webcast[11]" />' target="_blank">查看记录</a>
									</td>
									<td>
										<s:if test="#webcast[9] == 1 && (#webcast[10] == null || #webcast[10] == 2)">
											<s:if test="null != #webcast[14] && #webcast[14] != ''">
												<a href="/entity/workspaceStudent/studentWorkspace_viewlive.action?course_id=<s:property value="#webcast[1]" />" target="_blank">回看</a><br/>
											</s:if>
											<s:else>
												<s:if test="!CompareLiveTime(#webcast[3],#webcast[4])">
													<!-- 已到账、未退费、退费拒绝 -->
													<a href='/entity/workspaceStudent/studentWorkspace_liveStudy.action?id=<s:property value="#webcast[0]" />' target="_blank">去学习</a><br/>
												</s:if>
												<s:elseif test = "!isHaveResource(#webcast[11])">
													<span title="直播未开始或已结束">&nbsp;&nbsp;-&nbsp;&nbsp;</span><br/>
												</s:elseif>
											</s:else>
											<s:if test="isHaveResource(#webcast[11])">
												<a href='/sso/peResourceInteraction_listpeResource.action?teachclassId=<s:property value="#webcast[11]" />&coursename=<s:property value="#webcast[2]" />' target="_blank">查看资料</a>
											</s:if>
										</s:if>
										<s:elseif test="#webcast[10]==0 || #webcast[10] == 1 || #webcast[10] == 3">
											<span title="订单有退费操作">&nbsp;-&nbsp;</span>
										</s:elseif>
										<s:elseif test="#webcast[9] == 0">
											<span title="订单未到账">&nbsp;-&nbsp;</span>
										</s:elseif>
										<s:else>
											<span title="未支付">&nbsp;-&nbsp;</span>
										</s:else>
									</td>
								</tr>
							</s:iterator>
						</s:if>
						<tr>
							<td colspan="18" align="left">
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