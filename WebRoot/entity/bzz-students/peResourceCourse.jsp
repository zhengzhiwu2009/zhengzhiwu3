<%@ page language="java"
	import="java.util.*,com.whaty.platform.entity.bean.*"
	pageEncoding="UTF-8"%>
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
		<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
		<link href="/entity/bzz-students/css.css" rel="stylesheet"
			type="text/css" />
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet"
			type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet"
			type="text/css" />
		<link href="/entity/bzz-students/css.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css" media="all"
			href="/js/calendar/calendar-win2000.css" />
		<script type="text/javascript" src="/js/calendar/calendar.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
<script type="text/JavaScript">
	function searSubmit(){
	document.forms.frm.startIndex.value= 0;
	document.forms.frm.submit();
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
		function historyBack(){
			var url ="/entity/workspaceStudent/studentWorkspace_toZiliao.action";
			window.location.href(url);
		}
</script>
	</head>
	<body>
		<form action="/entity/workspaceStudent/studentWorkspace_toPeResourcecourse.action" name="frm" method="post">
			<input type="hidden" name="startIndex" value="<s:property value="startIndex" />" />
			<input type="hidden" name="pageSize" value="<s:property value="pageSize" />" />
			<input type="hidden" name="perid" value="<s:property value="perid" />" />
			<div class="" style="width: 100%; padding: 0; margin: 0;" align="center">
				<div class="main_title">
					资料相关课程
				</div>
				<div>
					<table width="95%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="40%" align=left>
								课程名称：
								<input type="text" name="kname" class="sub" value="<s:property value="kname" />" size="11" />
							</td>
							<td width="30%" align=left>
								课程编号：
								<input type="text" name="kbianhao" class="sub" value="<s:property value="kbianhao" />" size="11" />
							</td>
							<td align="right">
								<input type="button" value="&nbsp;查&nbsp;找&nbsp;" onclick="searSubmit();" />
								<input type="button" value="&nbsp;清&nbsp;空&nbsp;" onclick="resetContent();" />
								<input type="button" value="&nbsp;返&nbsp;回&nbsp;" onclick="historyBack();" />
							</td>
						</tr>
					</table>
					<table class="datalist" width="100%">
						<tr>
							<td colspan="2" align="left">
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
							<th width="7%">
								课程编号
							</th>
							<th width="17%">
								课程名称
							</th>
						</tr>
						<s:if test="page != null">
							<s:iterator value="page.items" id="opencourse">
								<tr>

									<td>
										<s:property value="#opencourse[2]" />
									</td>
									<td>
										<a style="color: #3366ff; cursor: pointer;" onclick="window.open('/cms/flkcalone.htm?myId=<s:property value="#opencourse[0]"/>','newwindow_detail')"'><s:property value="#opencourse[1]" /></a>
									</td>
								</tr>
							</s:iterator>
						</s:if>
						<tr>
							<td colspan="2" align="left">
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
				</div>
			</div>
		</form>
	</body>
</html>