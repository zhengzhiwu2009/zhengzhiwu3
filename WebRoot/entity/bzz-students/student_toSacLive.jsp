<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<form action="/entity/workspaceStudent/studentWorkspace_toSacLive.action" name="form" method="post">
			<input type="hidden" name="startIndex" value="<s:property value="startIndex" />" />
			<input type="hidden" name="pageSize" value="<s:property value="pageSize" />" />
			<div class="" style="width: 100%; padding: 0; margin: 0;" align="center">
				<div class="main_title">
					在线直播报名
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
						<tr>
							<tr>
							 <b> 根据财务要求，小于1000元的订单（包含合并订单）无法开具增值税专用发票。学员进行个人报名时请考虑上述要求，如个人订单无法达到1000元，请集体管理员统一报名并申请发票。</b>
							 </td>
						</tr>
					</table>
				</div>
				<div style="overflow-x: auto; overflow-y: hidden;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="15%" align="right">
								课程编号：
							</td>
							<td width="18%" align="right">
								<input type="text" name="courseCode" class="sub"
									value="<s:property value="courseCode" />" />
							</td>
							<td width="15%" align="right">
								课程名称：
							</td>
							<td width="18%" align="left">
								<input type="text" name="courseName" class="sub"
									value="<s:property value="courseName" />" />
							</td>
							<td width="15%" align="right">
								主讲人：
							</td>
							<td align="left">
								<input type="text" name="teacher" class="sub"
									value="<s:property value="teacher" />" />
							</td>
						</tr>
						<tr>
							<td align="right">
								预计直播时间：
							</td>
							<td align="left" colspan="4">
								<input type="text" style="width: 100px" name="selSendDateStart"
									id="selSendDateStart" readonly="readonly"
									value="<s:property value="selSendDateStart"/>" />
								<img src="/js/calendar/img.gif" width="20" height="14"
									id="f_trigger_b"
									style="border: 0px solid #551896; cursor: pointer;"
									title="Date selector"
									onmouseover="this.style.background='white';"
									onmouseout="this.style.background=''" />
								至
								<input type="text" style="width: 100px" name="selSendDateEnd"
									id="selSendDateEnd" readonly="readonly"
									value="<s:property value="selSendDateEnd"/>" />
								<img src="/js/calendar/img.gif" width="20" height="14"
									id="f_trigger_c"
									style="border: 0px solid #551896; cursor: pointer;"
									title="Date selector"
									onmouseover="this.style.background='white';"
									onmouseout="this.style.background=''" />
							</td>
							<td align="right">
								<input type="button" value="&nbsp;查&nbsp;找&nbsp;"
									onclick="searSubmit();" />
								<input type="button" value="&nbsp;清&nbsp;空&nbsp;"
									onclick="resetContent();" />
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
							<th width="12%">
								直播报名<br />开始时间
							</th>
							<th width="12%">
								直播报名<br />结束时间
							</th>
							<th width="12%">
								预计直播<br />开始时间
							</th>
							<th width="12%">
								预计直播<br />结束时间
							</th>
							<th width="8%">
								学时数
							</th>
							<th width="9%">
								金额(元)
							</th>
							<th width="8%">
								主讲人
							</th>
							<th width="8%">
								去支付
							</th>
						</tr>
						<s:if test="page != null">
							<s:iterator value="page.items" id="webcast">
								<tr>
									<td>
										<s:property value="#webcast[5]" />
									</td>
									<td>
										<a style="color: #3366ff; cursor: pointer;"
											onclick="window.open('/cms/flkcalone.htm?myId=<s:property value="#webcast[4]"/>&flag=true','newwindow_detail')"'><s:property
												value="#webcast[6]" />
										</a>
									</td>
									<td>
										<s:property value="#webcast[7]" />
									</td>
									<td>
										<s:property value="#webcast[8]" />
									</td>
									<td>
										<s:property value="#webcast[9]" />
									</td>
									<td>
										<s:property value="#webcast[10]" />
									</td>
									<td>
										<s:property value="#webcast[12]" />
									</td>
									<td>
										<s:property value="#webcast[13]" />
									</td>
									<td>
										<s:property value="#webcast[16]" />
									</td>
									<td
										title="剩余报名人数：<s:property value="(#webcast[26]==null || #webcast[26]<= 0)?'0':#webcast[26]" />">
										<s:if test="#webcast[13] == 0">
											<!-- 免费直播 -->
											<s:if test="#webcast[25] == null">
												<a
													onclick="window.open('/entity/workspaceStudent/studentWorkspace_paymentFreeLive.action?batchId=<s:property value="#webcast[0]"/>', 'newwindow');"
													href="javascript:void(0);">报名参加</a>
											</s:if>
											<s:else>
												<span>已报名</span>
											</s:else>
										</s:if>
										<s:else>
											<!-- 收费直播 -->
											<s:if
												test="#webcast[20]==0 || #webcast[20] == 1 || #webcast[20] == 3">
												<span title="订单有退费操作">-</span>
												<!-- 待审核、已退费、退费中 -->
											</s:if>
											<s:if test="#webcast[18]==0 || #webcast[18] == null">
												<a
													onclick="window.open('/entity/workspaceStudent/studentWorkspace_paymentLive.action?batchId=<s:property value="#webcast[0]"/>', 'newwindow');"
													href="javascript:void(0);">去支付</a>
											</s:if>
											<s:elseif
												test="(#webcast[18]==1 || #webcast[18] == 2) && #webcast[19] == 0">
												<span title="订单未到账">未到账</span>
											</s:elseif>
											<s:elseif
												test="#webcast[18]==1 && #webcast[19] == 1 && (#webcast[20] == null || #webcast[20] == 2)">
												<!-- 已支付、已到账、未退费、退费拒绝 -->
												<span title="订单已到账">已支付</span>
											</s:elseif>
										</s:else>
									</td>
								</tr>
							</s:iterator>
						</s:if>
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