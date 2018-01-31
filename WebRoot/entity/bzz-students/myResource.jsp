<%@ page language="java" import="java.util.*,com.whaty.platform.entity.bean.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<jsp:directive.page import="com.whaty.platform.sso.web.servlet.UserSession" />
<%
	String d = String.valueOf(Math.random());
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	
	UserSession userSession = (UserSession) session.getAttribute("user_session");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>"/>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" media="all" href="/js/calendar/calendar-win2000.css" />
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
	
	document.frm.form.submit();
}

function clear_text() {
	document.getElementById("ktimestart").value = "";
	document.getElementById("ktimeend").value = "";
	document.getElementById("optamount").value = "";
	document.getElementById("opttype").value = "";
}
</script>
		<script type="text/JavaScript">
<!--
var newWindow = "";

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}


function selectServer() {
	window.open ('<%=basePath%>training/student/course/serverSelect.jsp', 'selectserver', 
	'height=600, width=800, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
}
//-->
/*
$(document).ready(function() {
	$(".sub").change(
			function() {
				document.forms.frm.startIndex.value= 0;
				document.forms.frm.submit();
});
});**/
function searSubmit(){
	var txtValue=document.getElementById("txt").value;
	if(txtValue.length>0){
		document.getElementById("ziliaotypes").value = txtValue;
	}
	var resourceTag = document.getElementsByName('resourceTag');
	var tagIdStr = '';
	for(var i = 0; i < resourceTag.length; i++) {
		if(resourceTag[i].checked) {
			tagIdStr += resourceTag[i].value + ','
		}
	}
	document.getElementById("tagIds").value = tagIdStr.substring(0, tagIdStr.length - 1);
	
	document.forms.frm.startIndex.value= 0;
	document.forms.frm.submit();
}

function openCourseWare(url,target){
	if(!newWindow || newWindow.closed){
		newWindow = window.open(url,target);
	}else{
		alert('请先关闭当前学习的课程');
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
function changeF() {
  document.getElementById('ziliaotypes').value = document.getElementById('txt').options[document.getElementById('txt').selectedIndex].value;
} 
	
</script>
	</head>
	<body onLoad="MM_preloadImages('/entity/bzz-students/images/jckc1.jpg','/entity/bzz-students/images/jckc1_over.jpg','/entity/bzz-students/images/tskc2.jpg','/entity/bzz-students/images/tskc2_over.jpg','/entity/bzz-students/images/zyzc2.jpg','/entity/bzz-students/images/zyzc2_over.jpg')">
		<form action="/entity/workspaceStudent/studentWorkspace_myResource.action" name="frm" method="post">
			<input type="hidden" name="tagIds" id="tagIds"/>
			<input type="hidden" name="startIndex" value="<s:property value="startIndex" />" />
			<input type="hidden" name="pageSize" value="<s:property value="pageSize" />" />
			<div class="" style="width: 100%; padding: 0; margin: 0;" align="center">
				<div class="main_title">
					我的资料
				</div>
				<div>
					<table width="95%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="20%" align=left>
								资料名称：
								<input type="text" name="ziliaoname" class="sub" value="<s:property value="ziliaoname" />" size="11" />
							</td>
							<td width="25%" align=left>
								颁布/发布单位：
								<input type="text" name="fabuunit" class="sub" value="<s:property value="fabuunit" />" size="11" />
							</td>
							<td align=left>
								<div style="position:relative; width:260px;margin-bottom:10px;margin-left:26px;">
									资料分类：
									<select id="txt" name="txt" class="sub"  style="margin-left:-36px;float: right; width: 140px; z-index:88; position:absolute; left:100px; top:0px;" onchange="changeF();">
										<option value="">
											全部
										</option>
										<s:iterator value="resourceTagList" id="type">
											<s:if test="#type.name == ziliaotypes">
												<option selected value="<s:property value="#type.name" />">
													<s:property value="#type.name" />
												</option>
											</s:if>
											<s:else>
												<option value="<s:property value="#type.name" />">
													<s:property value="#type.name" />
												</option>
											</s:else>
										</s:iterator>
									</select>
									<input type="text" id="ziliaotypes" name="ziliaotypes" value="<s:property value="#request.ziliaotypes" />" style="margin-left:-36px;position:absolute; width:118px; left:101px;top:1px;z-index:99; border:1px #FFF solid" />
								</div>  
							</td>
							<td align="center" valign="middle">
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
      	<s:if test="page != null">
			var page2 = new showPages("page2",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
		</s:if>
		<s:else>
			var page2 = new showPages("page2",<s:property value="startIndex" />,<s:property value="pageSize" />,0,"");
		</s:else>
		page2.printHtml();
		</script>
								</div>
							</td>
						</tr>
						<tr bgcolor="#8dc6f6">
							<th>
								资料名称
							</th>
							<th width="10%">
								资料分类
							</th>
							<!--  
							<th width="14%">
								资料标签
							</th>
							-->
							<th width="14%">
								颁布/发布时间
							</th>
							<th width="14%">
								颁布/发布单位
							</th>
							<th width="10%">
								是否公开
							</th>
							<th width="10%">
								是否审核通过
							</th>
							<th width="10%">
								浏览次数
							</th>
							<th width="15%">
								上传时间
							</th>
							<th width="5%">
								操作
							</th>
							<!--  
							<th width="9%">
								相关课程
							</th>
							-->
						</tr>
						<s:if test="page != null">
							<s:iterator value="page.items" id="opencourse">
								<tr>
									<td>
										<a target="blank" href="/cms/doumentDetail.htm?myId=<s:property value="#opencourse[0]" />&uid=<s:property value="uid"/>"> <s:property value="#opencourse[1]" /> </a>
									</td>
									<td>
										<s:property value="#opencourse[2]" />
									</td>
									<!--  
									<td>
										<property value="#opencourse[5]"/>
									</td>
									-->
									<td>
										<s:property value="#opencourse[3]" />
									</td>
									<td>
										<s:property value="#opencourse[4]" />
									</td>
									<td>
										<s:property value="#opencourse[11]" />
									</td>
									<td>
										<s:property value="#opencourse[10]" />
									</td>
									<td>
										<s:property value="#opencourse[6]" />
									</td>
									<td>
										<s:property value="#opencourse[7]" />
									</td>
									<td>
										<s:if test="#opencourse[8] != uid">
											<a href="/entity/workspaceStudent/studentWorkspace_cancleAudit.action?perid=<s:property value="#opencourse[0]" />">删除</a>
										</s:if>
										<s:elseif test="#opencourse[10] == \"否\" && #opencourse[8] == uid">
											<a href="/entity/workspaceStudent/studentWorkspace_deleteResource.action?perid=<s:property value="#opencourse[0]" />">删除</a>
										</s:elseif>
										<s:else>
											-
										</s:else>
									</td>
									<!--  
									<td>
										<a href="/entity/workspaceStudent/studentWorkspace_toPeResourcecourse.action?perid=<s:property value="#opencourse[0]" />"> 查看课程 </a>
									</td>
									-->
								</tr>
							</s:iterator>
						</s:if>
						<tr>
							<td colspan="9" align="left">
								<div id="fany">
									<script language="JavaScript">
      	<s:if test="page != null">
			var page1 = new showPages("page1",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
		</s:if>
		<s:else>
			var page1 = new showPages("page1",<s:property value="startIndex" />,<s:property value="pageSize" />,0,"");
		</s:else>
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