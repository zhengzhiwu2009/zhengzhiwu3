<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<jsp:directive.page import="com.whaty.platform.sso.web.servlet.UserSession"/>
<jsp:directive.page import="com.whaty.platform.entity.bean.SsoUser"/>
<%
String d=String.valueOf(Math.random());
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" media="all"	href="/js/calendar/calendar-win2000.css"/>
		<script type="text/javascript" src="/js/calendar/calendar.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
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

function searSubmit(){
	var startDate = document.getElementById("ktimestart").value;
	var endDate = document.getElementById("ktimeend").value;
	if(Date.parse(startDate.replace("-","/")) > Date.parse(endDate.replace("-","/"))) {
		alert("开始时间大于结束时间,请检查!");
		return;
	}
	document.forms.form.submit();
}

function clear_text() {
	document.getElementById("ktimestart").value = "";
	document.getElementById("ktimeend").value = "";
	document.getElementById("optamount").value = "";
	document.getElementById("opttype").value = "";
}
</script>

</head>
<body >
<div class="" style="width:100%;padding:0;margin:0; " align="center">
      <div class="main_title">预付费账户查询(学时)</div>
      <div class="main_txt">
	  <table class="datalist" width="100%">
		  <tr bgcolor="#8dc6f6">
		    <th>已分配学时数</th>
		    <th>已使用学时数</th>
		    <th>未使用学时数</th>
		  </tr>
	  	  <tr>
            <td width="25%" align="center"><s:property value="#request.sumAmount"/></td>
            <td width="25%" align="center"><s:property value="#request.amount"/></td>
            <td width="25%" align="center"><s:property value="#request.subAmount"/></td>
	     </tr>
	  </table>
	  <form action="entity/workspaceStudent/studentWorkspace_toaccountbalance.action" name="form" method="post">
		  <table width="100%">
		  	<tr>
		  	  	<th>操作时间</th>
		  	  	<td align='left'>
		  	  		<input type="text" style="width:80px" name="ktimestart" id="ktimestart" value="<s:property value="ktimestart"/>"/>
					<img src="/js/calendar/img.gif" width="20" height="14" id="f_trigger_a" style="border: 1px solid #551896; cursor: pointer;" title="Date selector" onmouseover="this.style.background='white';" onmouseout="this.style.background=''"/> 
					到
				    <input type="text" style="width:80px" name="ktimeend" id="ktimeend" value="<s:property value="ktimeend"/>"/>
					<img src="/js/calendar/img.gif" width="20" height="14" id="f_trigger_b" style="border: 1px solid #551896; cursor: pointer;" title="Date selector" onmouseover="this.style.background='white';" onmouseout="this.style.background=''"/> 
		  	  	</td>
		  	  	<th>操作学时</th>
		  	  	<td  align='left'><input type="text" name='optamount' value='<s:property value="#request.optamount"/>' id='optamount'/></td>
		  	  </tr>
		  	  <tr>
	  	  		<th>操作类型</th>
	  	  		<td align='left'>
	  	  			<select name='opttype' id="opttype">
 	  					<option value="">全部</option>
 	  					<option value="分配-学员">分配</option>
 	  					<option value="剥离-学员">剥离</option>
 	  					<option value="消费">消费</option>
	  	  			</select> 
	  	  		</td>
	  	  		<td colspan="2" align="right">
	  	  			<input type="button" value="&nbsp;查&nbsp;找&nbsp;" onclick="searSubmit()" />
					<input type="button" value="&nbsp;清&nbsp;空&nbsp;" onclick="javascript:clear_text();"/>
				</td>
		  	  </tr>
		  </table>
	</form>
		  <table class="datalist" width="100%">
			  <tr bgcolor="#8dc6f6">
				    <th>操作时间</th>
				    <th>操作学时</th>
				    <th>操作类型</th> 
				    <th>账户剩余学时</th> 
			  </tr>
			  <s:if test="yufeihislist.size() > 0">
			  <s:iterator value="yufeihislist" id="cp" status="st">
			  	  <tr>
			            <td height="30"><s:property value="#cp[0]" /></td>
			            <td height="30"><s:property value="#cp[1]" /></td>
			            <td height="30"><s:property value="#cp[2]" /></td>
			            <td height="30"><s:property value="#cp[3]" /></td>
			     </tr>
			  </s:iterator>
			  </s:if>
			  <s:else>
			  	<tr>
			  		<td align="center" colspan="4">无数据</td>
			  	</tr>
			  </s:else>
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
</script>
</html>