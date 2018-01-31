<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>问题及建议</title>
		<link href="/entity/manager/images/css.css" rel="stylesheet" type="text/css">
		<script language="javascript" src="/entity/bzz-students/css/jquery.js"></script>
		<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
		<script type="text/javascript">
			function checkAndSubmit(){
				var topic = $("#topic").val();
				var type = $("#type").val();
				var desc = $("#desc").val();
				if("undefined" == topic || "" == topic){
					alert("请输入问题/建议标题");
					return;
				}
				if("undefined" == type || "" == type){
					alert("请选择问题/建议分类");
					return;
				}
				if("undefined" == desc || "" == desc){
					alert("请输入问题/建议描述");
					return;
				}
				$("#form").submit();
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
	</head>

	<body>

		<div class="h2">
			<div class="h2div">
				我要提问/建议
			</div>
		</div>
		<div class="content">
			<form action="/entity/basic/problemsManager_saveQuestionAdvice.action" id="form" name="form" method="post" enctype="multipart/form-data">
				<table class="datalist" width="100%">
					<tr>
						<th bgcolor="#8dc6f6" width="120px">
							问题/建议标题：
						</th>
						<td align="left">
							<input type="text" id="topic" name="topic" style="width:400px" maxlength="99" />
						</td>
					</tr>
					<tr>
						<th bgcolor="#8dc6f6" width="120px">
							问题/建议分类：
						</th>
						<td align="left">
							<select id="type" name="type">
								<option value="" selected>
									请选择
								</option>
								<s:iterator value="issueType" id="type">
									<s:if test="#type.id == type">
										<option value="<s:property value="#type.id" />" selected>
											<s:property value="#type.name" />
										</option>
									</s:if>
									<s:else>
										<option value="<s:property value="#type.id" />">
											<s:property value="#type.name" />
										</option>
									</s:else>
								</s:iterator>
							</select>
						</td>
					</tr>
					<tr>
						<th bgcolor="#8dc6f6">
							问题/建议描述：
						</th>
						<td align="left">
							<textarea rows="10" cols="80" id="desc" name="desc"></textarea>
						</td>
					</tr>
					<tr>
						<th>
							附件：
						</th>
						<td align="left">
							<input type="file" name="file"/>&nbsp;&nbsp;&nbsp;&nbsp;<a id="add">添加</a>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">附件格式建议为word、pdf、jpg、ppt四种格式，单个文件大小不超过2M。</font><br/>
							<div id="addFile"></div>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" value="提问/建议" onclick="checkAndSubmit()" />
							<input type="button" value="返回" onclick="history.go(-1)" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>