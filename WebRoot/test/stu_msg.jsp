<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<% response.setHeader("expires", "0"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><s:text name="test.info"/></title>
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
				<div class="msg" style="height:100%" width="98%">
					<img src="/entity/bzz-students/images/index_04.jpg" align="center" />
					<s:property value="getMsg()" escape="false" />
				</div>
				<div class="main_txt">
					<s:if test="getTogo()!=null && getTogo()!=''">
							<s:if test="getTogo()==\"back\"">
								<input type=button value="<s:text name="test.back"/>" onclick="history.back();">
							</s:if>
							<s:elseif test="getTogo()==\"close\"">
								<input type=button value="<s:text name="test.close"/>" onclick="window.close();">
							</s:elseif>
							<s:else>
								<input type=button value="<s:text name="test.confirm"/>" onclick="turnTo('<s:property value="getTogo()" escape="false"/>');">
							</s:else>
					</s:if>
					<s:else>
						<input type=button value="确定" onclick="reloadPage();" />
					</s:else>
				</div>
				<div style="height:10px"></div>
			</div>
		</form>
	</body>
	<script>
		function reloadPage() {
			try{
				if(opener !=null && opener.location != null){
					opener.location.reload();
				}
			}catch(e){}
			window.close();
		}
		
		function turnTo(url){
			try{
				if(opener !=null && opener.location != null){
					
					opener.location.href=url;
					window.close();
				}else{
					
				}
			}catch(e){}
			window.location.href=url;
		}
	</script>
	<!-- <body>
		<form name="print" >
			<div id="main_content">
			   <div class="content_title"><s:text name="test.info"/></div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="80%">
			   		<tr>
			   			<div class="" align="center"><s:property value="getMsg()" escape="false"/></div>
			   		</tr>
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox">

							<s:if test="getTogo()!=null">
								<input type=button 								
								<s:if test="getTogo()==\"back\"">
									value="<s:text name="test.back"/>" onclick="history.back();"
								</s:if>
								<s:else>
									value="<s:text name="test.confirm"/>" onclick="window.location='<s:property value="getTogo()" escape="false"/>';"
								</s:else>
								>
							</s:if>

							</div>
			   			</td>
			   		</tr>
			   </table>
			   </div>
		   </div>
		   </div>
		</form>
	</body> -->
</html>
