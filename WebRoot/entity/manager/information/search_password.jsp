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
		<base href="<%=basePath%>"/>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="<%=basePath%>cms/res_base/saccms_com_www/default/article/css/style.css" rel="stylesheet" type="text/css"/>
		<script language="JavaScript" type="text/javascript" src="<%=basePath%>cms/res_base/saccms_com_www/default/article/js/menu.js"></script>
		<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
		<script language="javascript">
			function checknull(){
				if(document.getElementById("pename").value==""){
					alert('真实姓名不能为空!');
					document.getElementById("pename").focus();
					return false;
				}
				if(document.getElementById("cardno").value=="") {
					alert('证件号码不能为空!');
					document.getElementById("cardno").focus();
					return false;
				}
				return true;
			}
			function goSearch() {
				if(document.getElementById("userName").value==""){
					alert('请输入账号');
					document.getElementById("userName").focus();
					return;
				}
				var userName = $('#userName').val();
				$.post(
					'/entity/information/searchPassword_goSearch.action',
					{userName : userName},
					function(data){
						$('#showPassword').html(data);
						$('#showPassword').css("background-color","brown");
					}
				);
			}
		</script>
	</head>
	<body>
		<br/><br/>
		<div class="" style="width: 100%; padding: 0; margin: 0;"
			align="center">
			<div style="color:#00274c; font-size:15px;font-weight:bold;">
				查找密码
			</div>
			<br/>
			<div class="main_txt" align="center" style="margin-left:">
			<form name="infoform">
				<table width="100%" border="2" align="left" cellpadding="0"
					cellspacing="0">
					<tr>
						<td>
							<table class="datalist" width=" 100%" order="0" cellpadding="0"
								cellspacing="0" bgcolor="#D7D7D7">
								<tr>
									<td width="40%" align="center" bgcolor="#E9F4FF" class="STYLE1">
										<font color="red" size="3"></font>
										<span style="color:#00274c; font-size:14px;font-weight:bold;"><font color="red">*</font>请输入您要查找的学员账号：</span>
									</td>
									<td width="40%" align="left" bgcolor="#FAFAFA" class="kctx_zi2" >
										<input type="text" name="userName" id="userName" maxlength=30 value="<s:property value="userName"/>"/>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<span bgcolor="#E9F4FF" id="showPassword"></span>
									</td>
								</tr>
					 			<tr>
									<td>
										<table border="0" align="right" cellpadding="0" cellspacing="0" height="35" valign="bottom">
											<tr>
												<td>
													<input type="button" value="提&nbsp;交" onclick="goSearch();" />
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table border="0" align="left" cellpadding="0" cellspacing="0" height="35" valign="bottom">
											<tr>
												<td>
													<input type="button" value="清&nbsp;空" style="cursor:hand" onclick="window.location.href='/entity/information/searchPassword_goPage.action'; "/>
												</td>
											</tr>
										</table>
									</td>
								</tr>
						 </table>
						</td>
					</tr>								
				</table>
				</form>
			</div>
		</div>
	</body>
</html>