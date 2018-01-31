<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool" />
<jsp:directive.page
	import="com.whaty.platform.database.oracle.MyResultSet" />
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page import="java.util.*,java.text.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<title>中国证劵业协会培训平台</title>
		<link href="/web/bzz_index/style/index.css" rel="stylesheet"
			type="text/css">
		<link href="/web/bzz_index/style/xytd.css" rel="stylesheet"
			type="text/css">
		<script language="javascript" src="/web/bzz_index/js/wsMenu.js"></script>
		<link rel="stylesheet" type="text/css" media="all"
			href="/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />
		<script type="text/javascript" src="/js/calendar/calendar.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
		<script language="javascript" src="/js/calender.js"></script>
		<script language="javascript">
function resize()
{
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
}
</script>
		<script language="javascript">
	function check()
	{
		if(document.findpwd.stuName.value=="")
		{
			alert("请输入姓名！");
			document.findpwd.stuName.focus();
			return false;
		}
		if(document.findpwd.stuMobile.value=="")
		{
			alert("手机号码不能为空！");
			document.findpwd.stuMobile.focus();
			return false;
		}
		if(document.findpwd.stuEnterpriseId.value=="")
		{
			alert("请选择一级企业！");
			document.findpwd.stuEnterpriseId.focus();
			return false;
		}
	}
</script>
		<style>
.font_czmm {
	font-size: 12px;
	color: #0A0A64;
	padding: 0px 0px 0px 10px;
	line-height: 30px;
}

select {
	border: solid 1px #1E61C9;
	width: 180px;
	height: 20px;
	margin: 0px 0px 0px 10px;
	font-size: 12px;
	color: #000000;
}

.kctx_zi1 {
	font-size: 12px;
	color: #0041A1;
}

.kctx_zi2 {
	font-size: 12px;
	color: #54575B;
	line-height: 24px;
	padding-top: 2px;
}

.kctx_zi3 {
	font-size: 14px;
	color: red;
	line-height: 24px;
	padding-top: 2px;
}
</style>
	</head>

	<body>
		<table width="993" border="0" align="center" cellpadding="0"
			cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td height="153">
					<img src="/web/bzz_index/images/top_02.jpg" width="993"
						height="153">
				</td>
			</tr>
			<tr>
				<td align="center"
					style="background-image: url(/web/bzz_index/czmm/images/czmm_05.jpg); background-position: bottom; background-repeat: repeat-x; padding: 30px 0px 30px 0px;">
					<table width="367" height="150" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="40" align="left" class="kctx_zi3">
								报名信息验证通过，请核实您的其他报名信息，如果确认没有问题，请点击“确定”，如果有错误，请修改后点击“确定”。
							</td>
						</tr>
						<form name="findpwd"
							action="/entity/workspaceStudent/bzzstudent_modifyRecruitInfo.action"
							method="post" onsubmit="return check();">
						<tr>
							<td>
								<table width="100%" border="3" cellpadding="0" cellspacing="1"
									bordercolor="#CCE2FF" bgcolor="#FFFFFF">

									<tr>
										<td bgcolor="#A1CBFF" class="font_czmm">
											姓 名：
										</td>
										<td class="kctx_zi2">
											&nbsp;
											<s:property value="peBzzRecruit.getName()" />
											<input type="hidden" name="peBzzRecruit.id"
												value="<s:property value='peBzzRecruit.id'/>" />
										</td>
									</tr>
									<tr>
										<td bgcolor="#A1CBFF" class="font_czmm">
											手机号码：
										</td>
										<td class="kctx_zi2">
											&nbsp;
											<s:property value="peBzzRecruit.getMobilePhone()" />
										</td>
									</tr>
									<tr>
										<td bgcolor="#A1CBFF" class="font_czmm">
											报名学期：
										</td>
										<td class="kctx_zi2">
											&nbsp;
											<s:property value="peBzzRecruit.getPeBzzBatch().getName()" />
										</td>
									</tr>
									<tr>
										<td bgcolor="#A1CBFF" class="font_czmm">
											所在一级企业：
										</td>
										<td class="kctx_zi2">
											&nbsp;
											<s:property
												value="peBzzRecruit.getPeEnterprise().getPeEnterprise().getName()" />
										</td>
									</tr>
									<tr>
										<td bgcolor="#A1CBFF" class="font_czmm">
											性 别：
										</td>
										<td class="kctx_zi2">
											<input type="radio" name="peBzzRecruit.gender" value="男"
												<s:if test='peBzzRecruit.gender=="男"'>checked</s:if> />
											男
											<input name="peBzzRecruit.gender" type="radio" value="女"
												<s:if test='peBzzRecruit.gender=="女"'>checked</s:if> />
											女
										</td>
									</tr>
									<tr>
										<td bgcolor="#A1CBFF" class="font_czmm">
											出生日期:
										</td>
										<td class="kctx_zi2">
											&nbsp;&nbsp;
											<input type="text" name="peBzzRecruit.birthdayDate"
												value="<s:date name='peBzzRecruit.birthdayDate' format='yyyy-MM-dd'/>"
												id="peBzzRecruit.birthdayDate" readonly />
										</td>
									</tr>
									<tr>
										<td bgcolor="#A1CBFF" class="font_czmm">
											学 历：
										</td>
										<td>
											<select name="peBzzRecruit.education">
												<option value="初中"
													<s:if test='peBzzRecruit.education=="初中"'>selected</s:if>>
													初中
												</option>
												<option value="高中"
													<s:if test='peBzzRecruit.education=="高中"'>selected</s:if>>
													高中
												</option>
												<option value="职高"
													<s:if test='peBzzRecruit.education=="职高"'>selected</s:if>>
													职高
												</option>
												<option value="中专"
													<s:if test='peBzzRecruit.education=="中专"'>selected</s:if>>
													中专
												</option>
												<option value="技校"
													<s:if test='peBzzRecruit.education=="技校"'>selected</s:if>>
													技校
												</option>
												<option value="大专"
													<s:if test='peBzzRecruit.education=="大专"'>selected</s:if>>
													大专
												</option>
												<option value="本科"
													<s:if test='peBzzRecruit.education=="本科"'>selected</s:if>>
													本科
												</option>
												<option value="硕士"
													<s:if test='peBzzRecruit.education=="硕士"'>selected</s:if>>
													硕士
												</option>
												<option value="博士"
													<s:if test='peBzzRecruit.education=="博士"'>selected</s:if>>
													博士
												</option>
											</select>
										</td>
									</tr>
									<tr>
										<td bgcolor="#A1CBFF" class="font_czmm">
											民    族：
										</td>
										<td class="kctx_zi2">
											&nbsp;&nbsp;
											<input type="text" name="peBzzRecruit.folk"
												value="<s:if test='peBzzRecruit.folk.length()>0'><s:property value="peBzzRecruit.folk"/></s:if><s:else></s:else>"
												maxlength=20 />
											<font color="red" size="2px">* 20个字以内</font>
										</td>
									</tr>
									<tr>
										<td bgcolor="#A1CBFF" class="font_czmm">
											电子邮件：
										</td>
										<td class="kctx_zi2">
											&nbsp;
											<input type="text" name="peBzzRecruit.email"
												value="<s:if test='peBzzRecruit.email.length()>0'><s:property value="peBzzRecruit.email"/></s:if><s:else></s:else>"
												maxlength=50 />
											<font color="red" size="2px">* 50个字以内</font>
										</td>
									</tr>
									<tr>
										<td bgcolor="#A1CBFF" class="font_czmm">
											固定电话：
										</td>
										<td class="kctx_zi2">
											&nbsp;
											<input type="text" name="peBzzRecruit.phone"
												value="<s:if test='peBzzRecruit.phone.length()>0'><s:property value="peBzzRecruit.phone"/></s:if><s:else></s:else>"
												maxlength=50 />
											<font color="red" size="2px">* 格式:010-11111111,50个字以内</font>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						</form>

						<tr>
							<td height="50" align="center" colspan=2>
								<a href="javascript:window.document.findpwd.submit();"><img
										src="/web/bzz_index/czmm/images/czmm_12.jpg" width="71"
										height="23" border="0">
								</a><a href="javascript:window.document.findpwd.reset();"><img
										src="/web/bzz_index/czmm/images/czmm_14.jpg" width="71"
										height="23" hspace="10" border="0">
								</a><a href="javascript:window.close();"><img
										src="/web/bzz_index/czmm/images/czmm_16.jpg" width="71"
										height="23" border="0">
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="54" align="center" valign="middle"
					background="web/bzz_index/images/bottom_08.jpg">
					<span class="down">版权所有：中国证劵业协会 <BR> 投诉：010-11110000
						传真：010-11116666 咨询服务电话：010-62415115</span>
				</td>
			</tr>
		</table>
		<script type="text/javascript">
    Calendar.setup({
    	inputField     :    "peBzzRecruit.birthdayDate",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_a",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
	</script>
	</body>
</html>

