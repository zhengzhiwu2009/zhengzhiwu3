<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<% response.setHeader("expires", "0"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><s:text name="test.info"/></title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<script>
			function handle() {
				try{
					if(opener !=null && opener.location != null){
						opener.location.reload();
					}
				}catch(e){}
				window.close();
			}
			
			function cancle() {
				window.close();
			}
		</script>
	</head>
	<body>
		<form name="print" >
			<div id="main_content">
			   <div class="content_title"><s:text name="test.info"/></div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="60%">
			   		<tr>
			   			<div class="" align="center"><s:property value="getMsg()" escape="false"/></div>
			   		</tr>
			   		<!-- 
			   		<tr>
			   			<div align="center" class="postFormBox">
			   				<input type="button" value="关闭" onclick="javascript:cancle();"/>
			   			</div>
			   		</tr>
			   		 -->
			   		<s:if test='getTogo()!=null&&getTogo()!=\"\"'>
			   		<tr>
				   		<div align="center" class="postFormBox">
					   		<s:if test='getTogo()==\"back\"'>
								<input type=button value="<s:text name="test.back"/>" onclick="history.go(-1);" /> 								
					   		</s:if>
					   		<s:elseif test='getTogo()==\"close\"'>
				   				<input type="button" value="关闭" onclick="javascript:cancle();"/>
					   			<!-- 
								<input type=button value="<s:text name="test.close"/>" onclick="handle();" />
								 --> 								
					   		</s:elseif>
					   		<s:else>
								<input type="button" value="<s:text name="test.confirm"/>" onclick="window.location.href='<s:property value="getTogo()" escape="false"/>'" /> 								
					   		</s:else>
				   		</div>
				   	</tr>
			   		</s:if>
			   </table>
			   </div>
		   </div>
		   </div>
		</form>
	</body>
	<input type="hidden" value="<s:property value="#session.collectiveElective"/>" id="typevalue"/>
	
</html>
