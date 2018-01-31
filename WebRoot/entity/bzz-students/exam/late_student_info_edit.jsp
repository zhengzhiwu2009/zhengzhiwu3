<%@ page language="java" import="java.net.*,java.util.*"
	pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.whaty.platform.entity.*,com.whaty.platform.entity.user.*,com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@page import="com.whaty.platform.database.oracle.*"%>




<%!String fixnull1(String str) {
		if (str == null || str.equals("null") || str.equals("")) {
			str = "";
			return str;
		}
		return str;
	}%>


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title></title>
		<link href="/entity/function/css/css.css" rel="stylesheet"
			type="text/css">
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.STYLE1 {
	font-size: 12px;
	color: #0041A1;
	font-weight: bold;
}

.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
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
-->
</style>
		<script language="javascript">
	function checknull(){
		var Phone = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,8}(\-\d{1,5})?$/;
		var Mobile = /^((\(\d{2,3}\))|(\d{3}\-))?1[3|5|8]\d{9}$/;
		var Email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
		var  elem = document.getElementsByTagName("input");
		for(k =0 ;k<elem.length;k++){
			if(elem[k].type=='text'&&elem[k].value.length<1){
				alert("至少有一项信息未填写,请您完善您的资料！");
				return false;
			}else{
				if(elem[k].name=="mobilePhone"&&!Mobile.test(elem[k].value)){
						alert("移动电话格式不正确!");
						return false;
			    }
				if(elem[k].name=="phone"&&!Phone.test(elem[k].value)){
						alert("办公电话格式不正确!");
						return false;
				}
				if(elem[k].name=="email"&&!Email.test(elem[k].value)){
						alert("电子邮件格式不正确!");
						return false;
				}
			}
		}
	}
</script>
	</head>
	<body leftmargin="0" topmargin="0"
		background="/entity/function/images/bg2.gif">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="top" class="bg3">
					<form
						action="/entity/workspaceStudent/bzzstudent_lateModify.action"
						method="post" name="infoform" onsubmit="return checknull();">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="45" valign="top"></td>
							</tr>
							<tr>
								<td align="center" valign="top">
									<table width="765" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="65"
												background="/entity/function/images/table_01.gif">
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td align="center" class="text1">
															<img src="/entity/function/images/xb3.gif" width="17"
																height="15">
															<strong>修改个人信息</strong>
														</td>
														<td width="300">
															&nbsp;
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td background="/entity/function/images/table_02.gif">
												<input type="hidden" name="peBzzStudent.photo"
													value="<s:property value="peBzzStudent.photo"/>" />

												<table width="95%" border="0" align="center" cellpadding="0"
													cellspacing="1" class="bg4" bgcolor="#D7D7D7">
													<tr>
														<td>
													<tr bgcolor="#ECECEC">
														<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
															<font color="red" size="3">*</font>
															<strong>学 号：</strong>
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
															<input type="hidden" name="peBzzStudent.regNo"
																value="<s:property value='peBzzStudent.regNo'/>" />
															&nbsp;
															<s:property value="peBzzStudent.regNo" />
															<input type="hidden" name="peBzzStudent.id"
																value="<s:property value='peBzzStudent.id'/>" />
														</td>
														<td align="center" valign="middle" bgcolor="#FAFAFA"
															class="kctx_zi2" rowspan="6" width="120">
															<img
																src="/incoming/student-photo/<s:if test="peBzzStudent.photo != null"><s:property value="peBzzStudent.peBzzBatch.batchCode"/>/<s:if test="peBzzStudent.peEnterprise.peEnterprise != null"><s:property value="peBzzStudent.peEnterprise.peEnterprise.code"/></s:if><s:else><s:property value="peBzzStudent.peEnterprise.code"/></s:else>/<s:property value="peBzzStudent.photo"/>?timestamp=<%=new Date().getTime()%></s:if><s:else>noImage.jpg</s:else>"
																width='120' hight='174' />
														</td>
													</tr>
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
															<font color="red" size="3">*</font>
															<strong>姓 名：</strong>
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
															&nbsp;
															<s:if test='temp.equals("0") && peBzzEditStudent == null'>
																<input type="text" name="trueName"
																	value="<s:if test='peBzzStudent.trueName.length()>0'><s:property value="peBzzStudent.trueName"/></s:if><s:else></s:else>" />
															</s:if>
															<s:else>
																<s:property value="peBzzStudent.trueName" />
															</s:else>
														</td>

													</tr>
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
															<font color="red" size="3">*</font>
															<strong>性 别：</strong>
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
															<input type="radio" name="gender" value="男"
																<s:if test='peBzzStudent.gender=="男"'>checked</s:if> />
															男 &nbsp;
															<input name="gender" type="radio" value="女"
																<s:if test='peBzzStudent.gender=="女"'>checked</s:if> />
															女
														</td>
													</tr>
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
															<font color="red" size="3">*</font>
															<strong>出生日期：</strong>
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
															&nbsp;
															<input type="text" name="birthdayDate"
																value="<s:date name='peBzzStudent.birthdayDate' format='yyyy-MM-dd'/>"
																id="peBzzStudent.birthdayDate" readonly />
														</td>
													</tr>
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
															<font color="red" size="3">*</font>
															<strong>民    族：</strong>
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
															&nbsp;
															<input type="text" name="folk"
																value="<s:if test='peBzzStudent.folk.length()>0'><s:property value="peBzzStudent.folk"/></s:if><s:else></s:else>"
																maxlength=20 />
															<font color="red" size="2px">* 20个字以内</font>
														</td>
													</tr>
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="STYLE1">
															<font color="red" size="3">*</font> 学 历：
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
															&nbsp;
															<select name="education">
																<option value="初中"
																	<s:if test='peBzzStudent.education=="初中"'>selected</s:if>>
																	初中
																</option>
																<option value="高中"
																	<s:if test='peBzzStudent.education=="高中"'>selected</s:if>>
																	高中
																</option>
																<option value="职高"
																	<s:if test='peBzzStudent.education=="职高"'>selected</s:if>>
																	职高
																</option>
																<option value="中专"
																	<s:if test='peBzzStudent.education=="中专"'>selected</s:if>>
																	中专
																</option>
																<option value="技校"
																	<s:if test='peBzzStudent.education=="技校"'>selected</s:if>>
																	技校
																</option>
																<option value="大专"
																	<s:if test='peBzzStudent.education=="大专"'>selected</s:if>>
																	大专
																</option>
																<option value="本科"
																	<s:if test='peBzzStudent.education=="本科"'>selected</s:if>>
																	本科
																</option>
																<option value="硕士"
																	<s:if test='peBzzStudent.education=="硕士"'>selected</s:if>>
																	硕士
																</option>
																<option value="博士"
																	<s:if test='peBzzStudent.education=="博士"'>selected</s:if>>
																	博士
																</option>
															</select>
														</td>
													</tr>
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="STYLE1">
															<font color="red" size="3">*</font> 所在学期：
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
															colspan="2">
															<input type="hidden" name="peBzzStudent.peBzzBatch.name"
																value="<s:property value="peBzzStudent.peBzzBatch.name"/>" />
															&nbsp;
															<s:if test="peBzzStudent.peBzzBatch.name == null "></s:if>
															<s:property value="peBzzStudent.peBzzBatch.name" />
														</td>
													</tr>
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="STYLE1">
															<font color="red" size="3">*</font> 通信地址：
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
															colspan="2">
															<input type="hidden" name="peBzzStudent.address"
																value="<s:property value="peBzzStudent.address"/>" />
															&nbsp;
															<s:if test='peBzzStudent.address.length()>0'>
																<s:property value="peBzzStudent.address" />
															</s:if>
															<s:else></s:else>
														</td>
													</tr>
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="kctx_zi2">
															<DIV align="center" class="STYLE1">
																<font color="red" size="3">*</font> 办公电话：
															</DIV>
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
															colspan="2">
															&nbsp;
															<input type="text" name="phone"
																value="<s:if test='peBzzStudent.phone.length()>0'><s:property value="peBzzStudent.phone"/></s:if><s:else></s:else>"
																maxlength=50 />
															<font color="red" size="2px">*
																格式:010-11111111,50个字以内</font>
														</td>
													</tr>
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="STYLE1">
															<font color="red" size="3">*</font> 移动电话：
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
															colspan="2">
															&nbsp;
															<input type="text" name="mobilePhone"
																value="<s:if test='peBzzStudent.mobilePhone.length()>0'><s:property value="peBzzStudent.mobilePhone"/></s:if><s:else></s:else>"
																maxlength=50 />
															<font color="red" size="2px">*
																格式:13911111111,50个字以内</font>
														</td>
													</tr>
													<tr>

														<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
															<font color="red" size="3">*</font>
															<strong>所在企业：</strong>
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
															colspan="2">
															<input type="hidden"
																name="peBzzStudent.peEnterprise.name"
																value="<s:property value="peBzzStudent.peEnterprise.name"/>" />
															&nbsp;
															<s:if test="peBzzStudent.peEnterprise.name.length() < 1"></s:if>
															<s:property value="peBzzStudent.peEnterprise.name" />
															 
														</td>
													</tr>
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="STYLE1">
															<font color="red" size="3">*</font> 电子邮件：
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
															colspan="2">
															&nbsp;
															<input type="text" name="email"
																value="<s:if test='peBzzStudent.email.length()>0'><s:property value="peBzzStudent.email"/></s:if><s:else></s:else>"
																maxlength=50 />
															<font color="red" size="2px">* 50个字以内</font>
														</td>
													</tr>
												</table>

												<table border="0" align="center" cellpadding="0"
													cellspacing="0">
													<tr>
														<td>
															&nbsp;
														</td>
													</tr>
													<tr>
														<td width="20">
															<input type="submit" value="修 &nbsp;改"
																stype="cursor:hand" />
														</td>
														<td wi0dth="20">
															&nbsp;
														</td>
														<!--  <td><a href="/entity/workspaceStudent/bzzstudent_StudentInfo.action"><img src="/entity/bzz-students/images/two/an_05.gif" width="71" height="23" border="0"></a></td>  -->
														<td width="20">
															<input type="button" value="返 &nbsp;回"
																stype="cursor:hand"
																onclick="javascript:window.navigate('/entity/workspaceStudent/bzzstudent_toLateCondi.action')" />
														</td>
													</tr>
												</table>
											</td>
										</tr>

										<tr>
											<td>
												<img src="/entity/function/images/table_03.gif" width="765"
													height="21">
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>