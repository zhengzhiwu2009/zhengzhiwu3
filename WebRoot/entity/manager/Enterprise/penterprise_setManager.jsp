<%@ page language="java" import="com.whaty.util.*,java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.entity.bean.*"%>
<%@page import="com.whaty.platform.database.oracle.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>

		<title></title>
		<meta http-equiv="expires" content="0">
		<link href="./css/css.css" rel="stylesheet" type="text/css">
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript">
			function checkForm() {
				var roleId = document.getElementById('roleId');
				var loginid = document.getElementById('loginid');
				
				if(roleId.value=='' || roleId.value==null)
				{
					alert('请选择管理员类型');
					roleId.focus();
					return false;
				}
				
				if(loginid.value == '' || loginid.value == null)
				{
					alert('请输入登陆账号');
					loginid.focus();
					return false;
				}
				
				if(loginid.value.length < 3 || loginid.value.length > 25)
				{
					alert('登陆账号应在3位到25位之间');
					loginid.focus();
					return false;
				}
				return true;
			}
			
		</script>
	</head>

	<body topmargin="0" leftmargin="0" bgcolor="#FAFAFA">

		<div id="main_content">
			<div class="content_title">
				设置企业管理员
			</div>
			<div class="cntent_k">
				<div class="k_cc">
					<form action="peSubEnterprise_addManager.action" method="post" onsubmit="return checkForm();">
						<input type="hidden" name="manager.peEnterprise.id" value="<%=((PeEnterprise)request.getAttribute("bean")).getId()%>">
						<input type="hidden" name="manager.peEnterprise.lxrEmail" value="<%=((PeEnterprise)request.getAttribute("bean")).getLxrEmail()%>">
						<input type="hidden" name="manager.peEnterprise.lxrXb" value="<%=((PeEnterprise)request.getAttribute("bean")).getLxrXb()%>">
						<input type="hidden" name="manager.peEnterprise.lxrMobile" value="<%=((PeEnterprise)request.getAttribute("bean")).getLxrMobile()%>">
						<input type="hidden" name="manager.peEnterprise.lxrName" value="<%=((PeEnterprise)request.getAttribute("bean")).getLxrName()%>">
						<input type="hidden" name="manager.peEnterprise.lxrPhone" value="<%=((PeEnterprise)request.getAttribute("bean")).getLxrPhone()%>">
						<input type="hidden" name="manager.peEnterprise.lxrPosition" value="<%=((PeEnterprise)request.getAttribute("bean")).getLxrPosition()%>">
					<table width="554" border="0" align="center" cellpadding="0"
						cellspacing="0">

						<tr>
							<td height="26" align="center" valign="middle" colspan="2">
								管理员信息：
							</td>
						</tr>

						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">管理员姓名：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><input type="text" value="<%=((PeEnterprise)request.getAttribute("bean")).getLxrName()%>" readonly="readonly" >
							</td>
						</tr>
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">管理员手机：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><input type="text" value="<%=((PeEnterprise)request.getAttribute("bean")).getLxrMobile()%>" readonly="readonly">
							</td>
						</tr>
						
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">管理员类型：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><select name="manager.ssoUser.pePriRole.id" id="roleId">
								<option value="">请选择...</option>
								<%
									String sql = "select t.id,t.name  from pe_pri_role t, enum_const e where t.flag_role_type=e.id and e.code in ('2') and e.namespace='FlagRoleType' order by t.name";
									dbpool db = new dbpool();
									MyResultSet rs = db.executeQuery(sql);
									while (rs.next()) {
									
									%>
										<option value="<%=rs.getString("id")%>"><%=rs.getString("name")%></option>
									<%}
									db.close(rs);
								%>
							</select>
							</td>
						</tr>
						
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">登陆账号：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><input type="text" name="manager.loginId" id="loginid">
							</td>
						</tr>
						
						<tr>
							<td height="10" colspan="2" align="center">
							<input type="submit" value="提交"><span >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><button onclick="history.back();">返回</button>
							</td>
						</tr>

						<tr>
							<td height="10">
							</td>
						</tr>
					</table>
					</form>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</body>
</html>
