<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>添加资料</title>
		<link href="/entity/manager/images/css.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" media="all" href="/js/calendar/calendar-win2000.css" />
	</head>
	<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
	<script type="text/javascript" src="/js/calendar/calendar.js"></script>
	<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
	<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
	<script language="javascript" src="/entity/bzz-students/css/jquery.js"></script>
	<script type="text/javascript">
		function doSubmit() {
			if(document.getElementById('name').value == null || document.getElementById('name').value == '') {
				alert("资料名称不能为空");
				return;
			}
			if(document.getElementById('reseType').value == null || document.getElementById('reseType').value == '') {
				alert("资料分类不能为空");
				return;
			}
			
			var resourceTag = document.getElementsByName('resourceTag');
			var tagIdStr = '';
			var tagNameStr = '';
			for(var i = 0; i < resourceTag.length; i++) {
				if(resourceTag[i].checked) {
					tagIdStr += resourceTag[i].value + ','
					tagNameStr += resourceTag[i].nextSibling.nodeValue + ',';
				}
			}
			document.getElementById("tagIds").value = tagIdStr.substring(0, tagIdStr.length - 1);
			document.getElementById("tagNames").value = tagNameStr.substring(0, tagNameStr.length - 1);
			
			var showuser = document.getElementsByName('showuser');
			var showuserStr = '';
			var c = 0;
			for(var i = 0; i < showuser.length; i++) {
				if(showuser[i].checked) {
					c = c + 1;
					showuserStr += showuser[i].value + ','
				}
			}
			if(c == 4) {
				showuserStr += showuserStr + 'all,';
			}
			document.getElementById("showusers").value = showuserStr.substring(0, showuserStr.length - 1);
			
			document.myForm.action = "peResource_savePeResource.action";
			document.myForm.submit();
		}
		
		$(function() {
	            $("#add").click(function() {
	                var html = $("<input type='file' name='file'><label>&nbsp;&nbsp;&nbsp;&nbsp;</label>");
	                var button = $("<a id='add'>删除</a><br/>");
	                
	                $("#addFile").append(html).append(button);
	                
	                button.click(function()
	                {
	                    html.remove();
	                    button.remove();
	                })
	            })
       		})
	</script>
	<script type="text/javascript">
		
	</script>
	<body>
		<div class="h2">
			<div class="h2div">   
				添加资料 
			</div>
		</div>
		<div class="content">
		<form action="" name="myForm" method="post" enctype="multipart/form-data">
			<input type="hidden" name="tagIds" id="tagIds"/>
			<input type="hidden" name="tagNames" id="tagNames"/>
			<input type="hidden" name="showusers" id="showusers"/>
			<table cellspacing="0" cellpadding="0">
					<tr>
						<th width="30%" align="right">
							<font color="red">*</font>资料名称：
						</th>
						<td>
							<input type="text" size="50" name="name" id="name">
						</td>
					</tr>
					<tr>
						<th align="right">
							<font color="red">*</font>资料分类：
						</th>
						<td>
							<!--  
							<input type="text" size="50" name="reseType" id="resetype">
							-->
							<select id="reseType" name="reseType">
								<s:iterator value="resourceTypeList" id="type">
									<option value="<s:property value="#type.id"/>"><s:property value="#type.name"/></option>
								</s:iterator>
							</select>
							
						</td>
					</tr>
					<tr>
						<th align="right">
							<font color="red">&nbsp;</font>资料标签：
						</th>
						<td>
							<s:if test="resourceTagList != null && resourceTagList.size() > 0">
								<s:iterator value="resourceTagList" id="tag">
									<input type="checkbox" name="resourceTag" value="<s:property value="#tag.id"/>"/><s:property value="#tag.name"/>
								</s:iterator>
							</s:if>
						</td>
					</tr>
					<tr>
						<th align="right">
							<font color="red">*</font>是否首页显示：
						</th>
						<td>
							<select name="show">
								<s:iterator value="isValidList" id="show">
									<option value="<s:property value="#show.id"/>">
										<s:property value="#show.name"/>
									</option>
								</s:iterator>
							</select>
						</td>
					</tr>
					<tr>
						<th align="right">
							<font color="red">*</font>是否首页可下载：
						</th>
						<td>
							<select name="down">
								<s:iterator value="isValidList" id="down">
									<option value="<s:property value="#down.id"/>">
										<s:property value="#down.name"/>
									</option>
								</s:iterator>
							</select>
						</td>
					</tr>
					<tr>
						<th align="right">
							<font color="red">&nbsp;</font>颁布/发布时间：
						</th>
						<td>
							<input type="text" style="width: 80px" name="replyDate" id="replyDate" value="" />
							<img src="/js/calendar/img.gif" width="20" height="14" id="f_trigger_a" style="border: 1px solid #551896; cursor: pointer;" title="Date selector" onmouseover="this.style.background='white';" onmouseout="this.style.background=''" />
						</td>
					</tr>
					<tr>
						<th align="right">
							<font color="red">&nbsp;</font>颁布/发布单位：
						</th>
						<td>
							<input type="text" size="50" id="fabuunit" name="fabuunit"/>
						</td>
					</tr>
					<tr>
						<th align="right">
							<font color="red">&nbsp;</font>查阅范围：
						</th>
						<td>
							<input type="checkbox" name="showuser" id="showuser" value="jgmanager"/>监管集体
							<input type="checkbox" name="showuser" id="showuser" value="jgstudent"/>监管学员
							<input type="checkbox" name="showuser" id="showuser" value="jtmanager"/>集体
							<input type="checkbox" name="showuser" id="showuser" value="jtstudent"/>学员
							&nbsp;&nbsp;<font color="red">*不选则默认为系统所有用户可查阅</font>
						</td>
					</tr>
					<tr>
						<th align="right">
							<font color="red">&nbsp;</font>资料描述：
						</th>
						<td>
							<textarea rows="6" cols="70" id="describe" name="describe"></textarea>
						</td>
					</tr>
					<tr>
						<th align="right">
							<font color="red">&nbsp;</font>资料附件：
						</th>
						<td>
							<input type="file" name="file"/>&nbsp;&nbsp;&nbsp;&nbsp;<a id="add">添加</a><br/>
							<div id="addFile"></div>
						</td>
					</tr>
					<tr>
						<td colSpan="2" align="center">
							<input type="button" id="tijiao" value="保存" onclick="javascript: doSubmit()"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="返回" onclick="javascript:window.history.back()">
						</td>
					</tr>
			</table>
		</form>
		</div>
	</body>
	<script>
	    Calendar.setup({
	        inputField     :    "replyDate",     // id of the input field
	        ifFormat       :    "%Y-%m-%d",       // format of the input field
	        button         :    "f_trigger_a",  // trigger for the calendar (button ID)
	        align          :    "Tl",           // alignment (defaults to "Bl")
	        singleClick    :    true
	    });
</script>
</html>