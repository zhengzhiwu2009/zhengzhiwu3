<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>问题及建议</title>
		<link href="/entity/manager/images/css.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="/entity/bzz-students/css/jquery.js"></script>
		<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
		<link rel="stylesheet" type="text/css" media="all" href="/js/calendar/calendar-win2000.css" />
	</head>
	<script type="text/javascript">
		function doSubmit() {
			if(document.getElementById('resourceName').value == null || document.getElementById('resourceName').value == '') {
				alert("资料名称不能为空");
				return;
			}
			if(document.getElementById('resetype').value == null || document.getElementById('resetype').value == '') {
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
	<body>

		<div class="h2">
			<div class="h2div">
				上传资料
			</div>
		</div>
		<div class="content">
			<form action="/entity/teaching/peResourcejiti_uploadResource.action" id="myForm" name="myForm" method="post" enctype="multipart/form-data">
				<input type="hidden" name="tagIds" id="tagIds"/>
				<input type="hidden" name="tagNames" id="tagNames"/>
				<table class="datalist" width="100%">
					<tr>
						<th bgcolor="#8dc6f6" width="120px">
							<font color="red">*</font>资料名称：
						</th>
						<td align="left">
							<input type="text" size="50" name="resourceName" id="resourceName"/>
						</td>
					</tr>
					<tr>
						<th bgcolor="#8dc6f6" width="120px">
							<font color="red">*</font>资料分类：
						</th>
						<td align="left">
							<select id="resetype" name="resetype">
								<s:iterator value="resourceTypeList" id="type">
									<option value="<s:property value="#type.id"/>"><s:property value="#type.name"/></option>
								</s:iterator>
							</select>
						</td>
					</tr>
					<!--  
					<tr>
						<th bgcolor="#8dc6f6">
							资料标签：
						</th>
						<td align="left">
							<s:if test="resourceTagList != null && resourceTagList.size() > 0">
								<s:iterator value="resourceTagList" id="tag">
									<input type="checkbox" name="resourceTag" value="<s:property value="#tag.id"/>"/><s:property value="#tag.name"/>
								</s:iterator>
							</s:if>
						</td>
					</tr>
					-->
					<tr>
						<th>
							<font color="red">&nbsp;</font>颁布/发布时间：
						</th>
						<td align="left">
							<input type="text" style="width: 80px" name="replyDate" id="replyDate" value="" />
							<img src="/js/calendar/img.gif" width="20" height="14" id="f_trigger_a" style="border: 1px solid #551896; cursor: pointer;" title="Date selector" onmouseover="this.style.background='white';" onmouseout="this.style.background=''" />
						</td>
					</tr>
					<tr>
						<th>
							<font color="red">&nbsp;</font>颁布/发布单位：
						</th>
						<td align="left">
							<input type="text" size="50" id="fabuunit" name="fabuunit"/>
						</td>
					</tr>
					<tr>
						<th width="120px">
							<font color="red">*</font>是否公开：
						</th>
						<td align="left">
							<select id="isOpen" name="isOpen">
								<s:iterator value="resourceIsOpen" id="isOpen">
									<option value="<s:property value="#isOpen.id"/>"><s:property value="#isOpen.name"/></option>
								</s:iterator>
							</select>
						</td>
					</tr>
					<tr>
						<th bgcolor="#8dc6f6">
							资料描述：
						</th>
						<td align="left">
							<textarea rows="6" cols="70" id="describe" name="describe"></textarea>
						</td>
					</tr>
					<tr>
						<th>
							资料附件：
						</th>
						<td align="left">
							<input type="file" name="file"/>&nbsp;&nbsp;&nbsp;&nbsp;<a id="add">添加</a>&nbsp;&nbsp;<font color="red">附件格式建议为word、pdf、jpg、ppt四种格式，单个文件大小不超过2M。</font><br/>
							<div id="addFile"></div>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" id="tijiao" value="保存" onclick="javascript: doSubmit()"/>&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="返回" onclick="javascript:window.history.back()"/>
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