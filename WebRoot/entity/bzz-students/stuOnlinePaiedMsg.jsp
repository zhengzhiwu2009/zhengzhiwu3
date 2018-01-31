<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	response.setHeader("expires", "0");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><s:text name="test.info" />
		</title>
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" /> 
<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" /> 
	</head>
	<body>
		<form name="print">
			<div class="" style="width: 100%; padding: 0; margin: 0;"
				align="center">
				<div class="main_title">
					<s:text name="test.info" />
				</div>
				<div class="msg" width="98%">
					<img src="/entity/bzz-students/images/index_04.jpg" align="center" />
					<s:property value="getMsg()" escape="false" />
				</div>
				<div class="main_txt">
					<s:if test="getTogo()!=null">
						<s:if test='getTogo()==\"back\"'>
							<input type=button value="<s:text name="test.back"/>" onclick="history.go(-1);">
						</s:if>
						<s:if test='getTogo()==\"close\"'>
							<input type=button value="<s:text name="test.close"/>" onclick="reloadPage();">
						</s:if>
						<s:else>
							<input type=button value="<s:text name="test.confirm"/>" onclick="window.location='<s:property value="getTogo()" escape="false"/>';">
						</s:else>
					</s:if>
					<s:else>
						<input type=button value="关闭" onclick=reloadPage() />
					</s:else>
				</div>
			</div>
		</form>
	</body>
	<input type="hidden" id="flag" name="flag" value= "<s:property value="#request.flag"/>"/>
	<script>
		function reloadPage() {
			//var flag = document.getElementById('flag').value;
			//parent.location.reload()
			//window.opener.document.location.reload(); 
			//if(flag != 'onlinePayment') {
			try{
				if(opener !=null && opener.location != null){
					try{
						opener.location.host
					}catch(exp){
						opener.location='/entity/workspaceStudent/studentWorkspace_toLearningCourses.action';
					}
					//opener.location.href = '/entity/workspaceStudent/studentWorkspace_toLearningCourses.action';
				}
			}catch(e){}
			//}
			//alert('asdfasdf');
			//window.location.href='/entity/workspaceStudent/studentWorkspace_viewElectiveOrder.action';
			window.close();
		}
	</script>
</html>
