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
		<link href="/entity/bzz-students/css.css" rel="stylesheet"
			type="text/css" />
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet"
			type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
		<link rel="stylesheet" type="text/css" media="all"
			href="/js/calendar/calendar-win2000.css">
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
-->
</style>
		<Script>
function selall(){
  	if (frm.all.delId ==null) {
		return;
	}
	var temp=frm.all.delId;
	if(temp.length==undefined){
	 temp.checked=frm.all.seldel.checked;
	 return;
	}
	else{
	for(var i=0;i<temp.length;i++){
	  temp[i].checked=frm.all.seldel.checked;
	}
	}
}
function del(){
	var flag=false;
	if (frm.all.delId ==null) {
		return;
	}
	var temp=frm.all.delId;
	if(temp.length==undefined){
	   flag=temp.checked;
	}else{
	   for(var i=0;i<temp.length;i++){
	      if(temp[i].checked){
	       flag=true;
	       break;
	      }
	   }
	}
	if (!flag) {
		alert("请选择要删除的邮件");
		return;
	}
	var answer = confirm("确定要删除吗？");
	if (!answer) {
		return;
	}
	frm.action="/entity/workspaceStudent/studentWorkspace_delEmail.action";
	frm.submit();
	
	
}

		var reg=/^(([1-9](\d){3})-((0?[1-9])|(1[0-2]))-((0?[1-9])|([1-2](\d))|(3[0-1])))?$/;
			function validateValue(strValue, strMatchPattern )
			{
			    var objRegExp = new RegExp(strMatchPattern);
			    return objRegExp.test(strValue);
			}
			function check(){
				
				var selSendDateStart = document.getElementById('selSendDateStart').value;
				var selSendDateEnd = document.getElementById('selSendDateEnd').value;
				var ds = selSendDateStart;
				var de = selSendDateEnd;
				if(selSendDateStart != null && selSendDateStart != '' ){
					if(!validateValue(selSendDateStart,reg) || !validateValue(selSendDateStart,reg)){
						alert('请输入正确的日期格式,如2010-10-01.');
						return false;
					}
					ds = ds.split('-');
					ds = new Date(ds[0], ds[1], ds[2]).getTime();
				}
				if(selSendDateEnd != null && selSendDateEnd != '' ){
					if(!validateValue(selSendDateEnd,reg) || !validateValue(selSendDateEnd,reg)){
						alert('请输入正确的日期格式,如2010-10-01.');
						return false;
					}
					de = de.split('-');
					de = new Date(de[0], de[1],de[2]).getTime();
				}
				if(selSendDateStart != null && selSendDateStart != '' && selSendDateEnd != null && selSendDateEnd != '' ) {
					
					if(ds>de) {
						alert('开始日期大于结束日期，请重新设定');
						return false;
					}
				}
				
				
				return true;
			}
			function doSearch(){
				if(check()){
					document.forms.frm.startIndex.value= 0;
					document.forms.frm.submit();
				}
			}
</Script>
	</head>
	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="window.location.href='#top'"><a id="top" name="top" />
		<form action="/entity/workspaceStudent/studentWorkspace_tositeemail.action" name="frm" method="post">
			<input type="hidden" name="startIndex"
				value="<s:property value="startIndex" />" />
			<input type="hidden" name="pageSize"
				value="<s:property value="pageSize" />" />
			<div class="" style="width: 100%; padding: 0; margin: 0;">
				<div class="main_title">
					站内信箱
					<font color='red' style="font-size: 12px;">
					*本信箱主要用于接收管理员发送的通知和公告
					</font>
				</div>
				<div class="main_txt">
					<table border="0" width="100%">
						<tr>
							<td width="20%" align="left">
								<font>标题：<input type=text style="width: 100px" name="selTitle" value="<s:property value="selTitle"/>"></input>
							</td>
							<td width="20%" align="left">
								发信人：
								<input type=text name="selSendName" style="width: 100px" value="<s:property value="selSendName"/>"></input>
							</td>

							<td width="50%" align="left" colspan="2">
								收信日期：
								<input type=text style="width: 100px" name="selSendDateStart"
									id="selSendDateStart" readonly="readonly"  value="<s:property value="selSendDateStart"/>"/>
								<img src="/js/calendar/img.gif" width="20" height="14"
									id="f_trigger_b"
									style="border: 0px solid #551896; cursor: pointer;"
									title="Date selector"
									onmouseover="this.style.background='white';"
									onmouseout="this.style.background=''" />
								到
								<input type=text style="width: 100px" name="selSendDateEnd"
									id="selSendDateEnd" readonly="readonly"  value="<s:property value="selSendDateEnd"/>"/>
								<img src="/js/calendar/img.gif" width="20" height="14"
									id="f_trigger_c"
									style="border: 0px solid #551896; cursor: pointer;"
									title="Date selector"
									onmouseover="this.style.background='white';"
									onmouseout="this.style.background=''" />

							</td>
							<td width="5%"><input type="button" value="&nbsp;查&nbsp;找&nbsp;" onclick="doSearch();"/></td>
							<!-- <td width=10% class="ck"
								style="width: 70px; background-image: url('/entity/bzz-students/images/index_23.jpg')">
								<a style="cursor: pointer;"
									onclick="document.forms.frm.submit();">查找</a></td> -->
								<!--      <input type=button onclick="del()" value="删除"></td>-->
						</tr>
					</table>
					<table class="datalist" width="100%">
						<tr>
							<td colspan="6" align="left">
								<div id="fany">
									<script language="JavaScript">
										var page2 = new showPages("page2",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
										page2.printHtml();
									</script>
								</div>
							</td>
						</tr>
						<tr bgcolor="#8dc6f6">
							<th width="5%" align="center">
								<input type=checkbox name="seldel" onclick="selall()" />
							</th>
							<th align=center>
								标题
							</th>
							<th width="16%" align="center">
								发信人
							</th>
							<th width="14%" align="center">
								收信日期
							</th>
							<th width="10%" align="center">
								签收状态
							</th>
							<th width="6%" align="center">
								操作
							</th>
						</tr>
						<s:iterator id="email" value="page.items">
							<tr>
								<td align="center">
									<input type=checkbox value="<s:property value='#email[0]'/>" name="delId">
								</td>
								<td align=center>
									<s:property value="#email[1]" />
								</td>
								<td align="center">
									<s:property value="#email[2]" />
								</td>
								<td align="center">
									<s:date name="#email[3]" format="yyyy-MM-dd" />
								</td>
								<td align="center">
									<s:if test="#email[4]==0">未阅读</s:if>
									<s:else>已阅读</s:else>
								</td>
								<td align="center">
									<a href="/siteEmail/recipientEmail_EmailInfo.action?id=<s:property value='#email[0]'/>&&temp=1" target="_self">查看</a>
								</td>
							</tr>
						</s:iterator>
						<tr>
							<td colspan="6" align="center">
								<input type="button" value="删&nbsp;&nbsp;除" onclick="del();"/>
							</td>
						</tr>
						<!-- <div style="height: 15px; width: 400px" align="center">
						<td colspan="6" class="ck"
							style="height: 25px; width: 70px; background-image: url('/entity/bzz-students/images/index_23.jpg')">
							<a style="cursor: pointer;" onclick="del()">删&nbsp;&nbsp;除</a>
						</td>
						</div> -->
						<tr>
							<td colspan="6" align="left">
								<div id="fany">
									<script language="JavaScript">
										var page1 = new showPages("page1",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
										page1.printHtml();
									</script>
								</div>
							</td>
						</tr>
					</table>
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