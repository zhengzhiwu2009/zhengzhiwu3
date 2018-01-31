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
	</head>
	<script type="text/javascript">

function searSubmit(){
	document.forms.form.startIndex.value= 0;
	document.forms.form.submit();
}
function clearTime() {
				document.getElementById('title').value = '';
				document.getElementById('types').value = '';
				document.getElementById('keywords').value = '';
			}
	function listSelect(){
		var ids = document.getElementsByName("idaaa");
		var listSelectAll = document.getElementById("listSelectAll");
		if(listSelectAll.checked) {
			for (var i = 0 ; i < ids.length; i++) {
				ids[i].checked = true;
			}
		} else {
			for (var i = 0 ; i < ids.length; i++) {
				ids[i].checked = false;
			}
		}
		
	}
function exportQuestion() {
	var x=document.getElementsByName("idaaa");
	var issel=false;
	var ids = "";
	for(var i=0;i<x.length;i++) {
		if(x[i].checked) {
			issel=true;
			ids += x[i].value + ",";
		}
	}
	document.getElementById("ids").value=ids;
 	if(issel==false){
 		alert("请选择要导出的问题！");
 		return;
 	}
 	document.form.action = "entity/workspaceStudent/studentWorkspace_resultSetToExcel.action";
	document.form.submit();
}
</script>
	<body>
		<form
			action="entity/workspaceStudent/studentWorkspace_frequentlyQuestion.action"
			name="form" method="post">
			<input type="hidden" name="startIndex"
				value="<s:property value="startIndex" />" />
			<input type="hidden" name="pageSize"
				value="<s:property value="pageSize" />" />
			<input type="hidden" name="ids" id="ids" value="" />
			<div class="" style="width: 100%; padding: 0; margin: 0;" align="center">
				<div class="main_title">常见问题库</div>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td align="center" width="33%">问题标题：
							<input type=text style="width:150px" id="title" name="title" value="<s:property value="title" />" />
						</td>
						 <td align="center" width="33%">问题分类：
							<input type=text style="width:100px" id="types" name="types" value="<s:property value="types" />" />
						</td>
						<td align="center" width="33%">关键词：
							<input type=text style="width:100px" id="keywords" name="keywords" value="<s:property value="keywords" />" />
						</td>
					</tr>
					<tr>
						<td align="right" colspan="3">
							<input type="button" value="&nbsp;查&nbsp;找&nbsp;" onclick="searSubmit()" />
							<input type="button" value="&nbsp;清&nbsp;空&nbsp;" onclick="clearTime();"/>
							<input type="button" value="&nbsp;导出明细&nbsp;" onclick="exportQuestion();"/>
						</td>
						<td width="60"></td>
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
							<th width="6%">
								<input type='checkbox' class='checkbox' id="listSelectAll" name='listSelectAll' value='true'  onclick="listSelect()"/>
							</th>
							<th align="center">
								问题标题
							</th>
							<th width="20%" align="center">
								问题分类
							</th>
							<th width="10%" align="center">
								关键字
							</th>
							<th width="10%" align="center">
								浏览次数
							</th>
						</tr>
						<s:if test="page != null">
							<s:iterator value="page.items" id="cp" status="st">
								<tr>
									<td class='select' width="5%" style='text-align: center; '> 
                          				<input type='checkbox' class='checkbox' name='idaaa' value='<s:property value="#cp[0]" />' id='' />
                          	 		</td>
									<td height="30">
										<a href="/entity/workspaceStudent/studentWorkspace_showQuestion.action?qid=<s:property value="#cp[0]" />"><s:property value="#cp[1]" /></a>
									</td>
									<td>
										<s:property value="#cp[2]" />
									</td>
									<td>
										<s:property value="#cp[3]" />
									</td>
									<td>
										<s:property value="#cp[4]" />
									</td>
								</tr>
							</s:iterator>
						</s:if>
						<s:else>
							<tr>
								<td colspan="4">--暂无数据--</td>
							</tr>
						</s:else>
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
</html>