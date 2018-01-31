<%@ page language="java" import="com.whaty.util.*,java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.entity.bean.*"%>
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
				var destEnt = document.getElementById('destEnt');
				
				if(destEnt.value=='' || destEnt.value==null)
				{
					alert('请选择目的企业');
					destEnt.focus();
					return false;
				}
				return true;
			}
			
		</script>
	</head>

	<body topmargin="0" leftmargin="0" bgcolor="#FAFAFA">

		<div id="main_content">
			<div class="content_title">
				企业合并
			</div>
			<div class="cntent_k">
				<div class="k_cc">
					<form action="peSubEnterprise_combineEnterprise.action" method="post" onsubmit="return checkForm();">
						<input type="hidden" name="ids" value="<%=(String)(request.getAttribute("ids"))%>">
					<table width="554" border="0" align="center" cellpadding="0"
						cellspacing="0">

						<tr>
							<td height="26" align="center" valign="middle" colspan="2">
								要合并的企业：
							</td>
						</tr>
						<tr>
							<td align="center" colspan="2">
							<table>
								<tr>
									<td width="140" height="30" align="center"class="postFormBox">
										<span class="name">企业名称</span>
									</td>
									<td width="140" height="30" align="center" class="postFormBox">
										<span class="name">企业编号</span>
									</td>
								</tr>
									<%
									List<PeEnterprise> combineEnterpriseList = (List<PeEnterprise>)request.getAttribute("combineEnterpriseList");
			
									for(PeEnterprise ent : combineEnterpriseList) {
									%>
									<tr>
										<td class="postFormBox" style="padding-left: 18px" align="center"><%=ent.getName() %></td>
										<td class="postFormBox" style="padding-left: 18px"  align="center"><%=ent.getCode() %></td>
									</tr>
									
									<%
									}
								%>

							</table>
							</td>
						</tr>
						
						
						<tr>
							<td height="10">&nbsp;</td>
						</tr>
						<tr>
						<td height="26" align="center" valign="middle" colspan="2">
								请选择目的企业：
							</td>
						</tr>
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">目的企业：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><select name="destEnt" id="destEnt">
								<option value="">请选择...</option>
								<%
									List<PeEnterprise> combineDesEntList = (List<PeEnterprise>)request.getAttribute("combineDesEntList"); 
									for(PeEnterprise ent : combineDesEntList) {
									
									%>
										<option value="<%=ent.getId()%>"><%=ent.getName()%></option>
									<%}
								%>
							</select>
							</td>
						</tr>
						
						<tr>
							<td height="10" colspan="2" align="center">
							<input type="submit" value="提交"><span >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><button onclick="history.go(-2);">返回</button>
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
