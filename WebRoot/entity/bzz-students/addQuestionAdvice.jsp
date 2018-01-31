<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.whaty.platform.entity.bean.FrequentlyAskedQuestions;"%>
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
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<script language="javascript" src="/entity/bzz-students/css/jquery.js"></script>
		<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
		<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
		<script type="text/javascript">
			function checkAndSubmit(){
				var topic = $("#topic").val();
				var type = $("#type").val();
				if("undefined" == topic || "" == topic){
					alert("请输入问题/建议标题");
					return;
				}
				if("undefined" == type || "" == type){
					alert("请选择问题/建议分类");
					return;
				}
				var acontent = $("#desc").val();
				if(acontent.length == 0 || acontent == "") {
					alert("请输入问题/建议描述");
					return false;
				}
				$("#form").submit();
			}
			function calcMax(op){
				document.getElementById("calc").innerText = 500-op.length;
				kdConetent(op);
			}
			function kdConetent(op){
				if(op.length > 500){
					document.getElementById("desc").innerText = op.substr(0,500);
				}
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
		<form action="/entity/workspaceStudent/studentWorkspace_saveQuestionAdvice.action" id="form" name="form" method="post" enctype="multipart/form-data">
			<div class="" style="width: 100%; height:100%; padding:0; margin: 0;" align="center" >
				<div class="main_title">&nbsp; 
					我要提问/建议 
				</div>
				<div class="main_txt" style="magrin:0 0 60px 0">
					<table class="datalist" width="100%" height="100%" >
						<tr>
							<th width="120px">
								问题/建议标题：
							</th>
							<td align="left">
								<input type="text" id="topic" name="topic" size="50" maxlength="100" />
							</td>
						</tr>
						<tr>
							<th width="120px">
								问题/建议分类：
							</th>
							<td align="left">
								<select id="type" name="type">
									<s:iterator value="issueType" id="type">
										<option value="<s:property value="#type.id" />">
											<s:property value="#type.name" />
										</option>
									</s:iterator>
								</select>
							</td>
						</tr>
						<tr>
							<th>
								问题/建议描述：
							</th>
							<td align="left">
								<textarea rows="6" cols="70" id="desc" name="desc" onkeydown="kdConetent(this.value)" onpropertychange="calcMax(this.value)"></textarea><br/><font color="red">*还可输入<span id="calc">500</span>个字符。</font>
							</td>
						</tr>
						<tr>
							<th>
								附件：
							</th>
							<td align="left" >
								<input type="file" name="file"/>&nbsp;&nbsp;&nbsp;&nbsp;<a id="add">添加</a>&nbsp;&nbsp;<font color="red">附件格式建议为word、pdf、jpg、ppt四种格式，单个文件大小不超过2M。</font><br/>
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
				</div>
			</div>
		</form>
	</body>
</html>