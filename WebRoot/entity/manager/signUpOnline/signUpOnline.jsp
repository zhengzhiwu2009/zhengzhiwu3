<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.whaty.platform.entity.web.util.UUIDGenerator"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<html>
	<head>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			String validateStr = UUIDGenerator.getUUID();
			ActionContext.getContext().getSession().put(
					"signUpOrderValidateStr", validateStr);
			validateStr = UUIDGenerator.getUUID();
			ActionContext.getContext().getSession().put(
					"signUpOrderResubmitStr", validateStr);
		%>
		<script type="text/javascript">
	var basePath = '<%=basePath%>';
</script>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Grid to Grid Drag and Drop Example</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">
		<link rel="stylesheet" type="text/css"
			href="/entity/manager/ext3/resources/css/ext-all.css" />
		<script type="text/javascript" src="./js/ext-base.js"></script>
		<script type="text/javascript"
			src="/entity/manager/ext3/ext-all-debug.js"></script>
		<link rel="stylesheet" type="text/css" href="./css/examples.css" />
		<script type="text/javascript" src="./js/course_grid_to_grid.js"></script>
		<script type="text/javascript" src="./js/student_grid_to_grid.js"></script>
	</head>
	<body>
		<div style="width: 100%; margin: 0 auto;">
			<div>
				<div id="stuPanel_2"
					style="float: right; width: 50%; margin-left: 10px; margin-top: 30px;"></div>
				<div>
					<div id="stuPanel_1" style="width: 100%; margin-left: 10px;"></div>
				</div>
			</div>
			<div style="margin-top: 10px;">
				<div id="stuPanel_4"
					style="float: right; width: 50%; margin-left: 10px; margin-top: 30px;"></div>
				<div id="stuPanel_3" style="width: 100%; margin-left: 10px;"></div>
			</div>
			<div
				style="margin-top: 10px; border: 0px solid red; text-align: center;">
				<form action="/entity/basic/signUpOnline_submitSignUpResult.action"
					id="signForm" method="post">
					<input type="hidden" name="studentIds" id="studentIds">
					<input type="hidden" name="courseIds" id="courseIds">
					<input type="button" value="提交" onclick="submitSignUp();" />
				</form>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	function submitSignUp(){
		var studentIds = getStudentIds();
		if(studentIds!=null && studentIds.length > 0){
			document.getElementById('studentIds').value = studentIds;
		}else{
			alert('选择的学员列表不能为空！');
			return;
		}
		var courseIds = getCourseIds();
		if(courseIds!=null && courseIds.length > 0){
			document.getElementById('courseIds').value = courseIds;
		}else{
			alert('选择的课程列表不能为空！');
			return;
		}
		Ext.Ajax.request({
			url:'/entity/basic/signUpOnline_validateSignUp.action?type=lee',
			params : {'studentIds':studentIds,'courseIds':courseIds},
			method : 'post',
			success:function(response){
				var status = Ext.util.JSON.decode(response.responseText);
				if(status.isOK == 'true'){
					document.getElementById('signForm').submit();
				}else{
					alert(status.msg);
				}
			},
			failure:function(){
				alert('数据处理出错!');
			}
		});
	}
</script>
</html>
