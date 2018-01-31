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
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet"
			type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet"
			type="text/css" />
		<link href="/entity/bzz-students/css1.css" rel="stylesheet"
			type="text/css" />
		<link href="/entity/manager/statistics/css/admincss.css"
			rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" media="all"
			href="/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />
		<script type="text/javascript" src="/js/calendar/calendar.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
		<script language="javascript" src="/js/calender.js"></script>				
	</head>
	<body>
		<div class="" style="width: 100%; padding: 0; margin: 0;"
			align="center">
			<!-- <div>
				<img src="cms/res_base/saccms_com_www/default/article/images/index_03.jpg" />
			</div> -->
			<div class="main_title">
				用户注册
			</div>
			<div class="main_txt">
			<form action="/sso/regist_registSucess.action"
					method="post" name="infoform" enctype="multipart/form-data"
					onsubmit="return checknull();">
				 
				<table width="100%" border="0" align="left" cellpadding="0"
					cellspacing="0">
					<tr>
						<td>
							<table class="datalist" width=" 100%" order="0" cellpadding="0"
								cellspacing="0" bgcolor="#D7D7D7">
								<tr>
									<td width="20%" align="center" bgcolor="#E9F4FF"
										class="STYLE1">
										<font color="red" size="3">*</font>
										<strong>用户名：</strong>
									</td>
									<td width="30%" align="left" bgcolor="#FAFAFA" class="kctx_zi2" >
										&nbsp;
										<input type="text" name="regStudent.regNo"  id="regStudent.regNo" maxlength="30">
									</td>
									<td width="20%" align="center" bgcolor="#E9F4FF" class="STYLE1" width="30%">
										<font color="red" size="3">*</font>
										<strong>真实姓名：</strong>
									</td>
									<td width="30%" align="left" bgcolor="#FAFAFA" class="kctx_zi2" >
										&nbsp;
										<input type="text" name="regStudent.trueName" id="regStudent.trueName" maxlength=30>
									</td>
								</tr>
								<tr>
								<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<font color="red" size="3">*</font> <strong>民 族：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
										>
										&nbsp;
										<input type="text" name="regStudent.folk" id="regStudent.folk" maxlength=30 />
									</td>
									
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<font color="red" size="3">*</font> <strong>生日：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
										>
										&nbsp;
										<input type="text"
											class="input" name="regStudent.birthdayDate" id="regStudent.birthdayDate"  size="8" readonly> <img
											src="entity/function/images/img.gif" width="20" height="14"
											id="f_trigger_b"
											style="border: 1px solid #551896; cursor: pointer;"
											title="Date selector"
											onmouseover="this.style.background='white';"
											onmouseout="this.style.background=''">
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<font color="red" size="3">*</font>
										<strong>证件类型：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" >
										&nbsp;
										<input type="text" name="regStudent.cardType" id="regStudent.cardType" maxlength=30/>
									</td>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<font color="red" size="3">*</font>
										<strong>身份证号：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" >
										&nbsp;
										<input type="text" name="regStudent.cardNo" id="regStudent.cardNo" maxlength=30 />
									</td>									
								</tr>							
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<font color="red" size="3">*</font>
										<strong>性 别：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" >
										&nbsp;
										<input type="radio" name="regStudent.gender" id="gender1" value="男"/>
										男 &nbsp;
										<input name="regStudent.gender" type="radio" id="gender2" value="女"/>
										女
									</td>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<font color="red" size="3">*</font> <strong>学 历：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" >
										&nbsp;
										<select name="regStudent.education" id="regStudent.education">
										
											<option value="初中"
													<s:if test='regStudent.education=="初中"'>selected</s:if>>
													初中
												</option>
												<option value="高中"
													<s:if test='regStudent.education=="高中"'>selected</s:if>>
													高中
												</option>
												<option value="职高"
													<s:if test='regStudent.education=="职高"'>selected</s:if>>
													职高
												</option>
												<option value="中专"
													<s:if test='regStudent.education=="中专"'>selected</s:if>>
													中专
												</option>
												<option value="技校"
													<s:if test='regStudent.education=="技校"'>selected</s:if>>
													技校
												</option>
												<option value="大专"
													<s:if test='regStudent.education=="大专"'>selected</s:if>>
													大专
												</option>
												<option value="本科"
													<s:if test='regStudent.education=="本科"'>selected</s:if>>
													本科
												</option>
												<option value="硕士"
													<s:if test='regStudent.education=="硕士"'>selected</s:if>>
													硕士
												</option>
												<option value="博士"
													<s:if test='regStudent.education=="博士"'>selected</s:if>>
													博士
												</option>
										</select>
									</td>
								</tr>	
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<font color="red" size="3">*</font><strong>邮 箱：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
										>
										&nbsp;
										<input type="text" name="regStudent.email" id="regStudent.email" maxlength=30 />
									</td>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<font color="red" size="3">*</font><strong>手机：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
										>
										&nbsp;
										<input type="text" name="regStudent.mobilePhone" id="regStudent.mobilePhone" maxlength=30 />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<font color="red" size="3">*</font> <strong>是否是个人用户：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
										>
										&nbsp;
										<select name="regStudent.enumConstByFlagStudentType.id" id="regStudent.enumConstByFlagStudentType.id" onchange="choose()">
											<s:iterator value="studentTypeList" id="stlist">
												<option value="<s:property value='#stlist.id' />"><s:property value="#stlist.name"/> </option>
											</s:iterator>
											<!-- <option value="1">个人</option>
											<option value="2">集体</option> -->
										</select>
									</td>
									
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<font color="red" size="3" >*</font> <strong>机构名称：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										&nbsp;
										<select name="regStudent.peEnterprise.id" id="regStudent.peEnterprise.id">
											<option value="">请选择机构名称</option>
											<s:iterator value="peEnterpriseList" id="pelist">
												 <option value="<s:property value='#pelist.id' />"><s:property value="#pelist.name"/> </option>
											</s:iterator>
										</select>
										
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>邮编：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
										>
										&nbsp;
										<input type="text" name="regStudent.zipcode" id="regStudent.zipcode" maxlength=30 />
									</td>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>年 龄：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
										>
										&nbsp;
										<input type="text" name="regStudent.age" id="regStudent.age" maxlength=30 />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>工作部门：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
										>
										&nbsp;
										<input type="text" name="regStudent.department" id="regStudent.department" maxlength=30 />
									</td>
									<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
										<strong>从事业务：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
										>
										&nbsp;
										<input type="text" name="regStudent.position" id="regStudent.position" maxlength=30 />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>职 称：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
										>
										&nbsp;
										<input type="text" name="regStudent.title" id="regStudent.title" maxlength=30 />
									</td>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>办公电话：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
										>
										&nbsp;
										<input type="text" name="regStudent.phone" id="regStudent.phone" maxlength=30 />
									</td>
								</tr>
								
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>通讯地址：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="3">
										&nbsp;
										<input type="text" name="regStudent.address" id="regStudent.address" maxlength=60 />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								height="35" valign="bottom">
								<tr>
									<td>
										<input type="submit" value="&nbsp;注&nbsp;册&nbsp;" stype="cursor:hand" />
									</td>
									<td width="20"></td>
									<td>
										<input type="reset" value="&nbsp;清&nbsp;空&nbsp;" stype="cursor:hand"  />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				</form>
				<br/>
			</div>
			<!-- <div>
				<img src="cms/res_base/saccms_com_www/default/article/images/index_1000.jpg" align="absmiddle" />
			</div> -->
		</div>
<script type="text/javascript">
    Calendar.setup({
        inputField     :    "regStudent.birthdayDate",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_b",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
    
</script>
	</body>
</html>