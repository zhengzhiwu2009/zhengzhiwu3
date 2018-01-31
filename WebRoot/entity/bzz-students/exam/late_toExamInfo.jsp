<%@ page language="java" import="java.net.*,java.util.*"
	pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page isELIgnored="false"%>

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
						action="/entity/workspaceStudent/bzzstudent_examModifyIn.action"
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
															<strong>个人信息及缓考状态</strong>
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
															<strong>学 号：</strong>
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
															width=300>
															&nbsp;
															<s:property value="peBzzStudent.getRegNo()" />
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
															<strong>姓 名：</strong>
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
															&nbsp;
															<s:property value="peBzzStudent.getTrueName()" />
														</td>
													</tr>
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
															<strong>性 别：</strong>
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
															&nbsp;
															<s:if test="peBzzStudent.gender == null "></s:if>
															<s:property value="peBzzStudent.gender" />
														</td>
													</tr>
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
															<strong>出生日期：</strong>
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
															&nbsp;
															<s:if test="peBzzStudent.birthdayDate == null "></s:if>
															<s:date name="peBzzStudent.birthdayDate"
																format="yyyy-MM-dd" />
															 
														</td>
													</tr>
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
															<strong>民    族：</strong>
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
															&nbsp;
															<s:if test="peBzzStudent.folk == null "></s:if>
															<s:property value="peBzzStudent.folk" />
														</td>
													</tr>
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="STYLE1">
															学 历：
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
															&nbsp;
															<s:if test="peBzzStudent.education == null "></s:if>
															<s:property value="peBzzStudent.education" />
														</td>
													</tr>
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="STYLE1">
															所在学期：
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
															colspan="2">
															&nbsp;
															<s:if test="peBzzStudent.peBzzBatch.name == null "></s:if>
															<s:property value="peBzzStudent.peBzzBatch.name" />
														</td>
													</tr>
													<%-- <tr>
					        <td align="center" bgcolor="#E9F4FF" class="STYLE1">通信地址：</td>
					        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2"> &nbsp; <s:if test="peBzzStudent.peEnterprise.address == null "></s:if>
					                                  		<s:property value="peBzzStudent.peEnterprise.address"/>  </td>
					      </tr>--%>
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="kctx_zi2">
															<DIV align="center" class="STYLE1">
																办公电话：
															</DIV>
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
															colspan="2">
															&nbsp;
															<s:if test="peBzzStudent.phone == null "></s:if>
															<s:property value="peBzzStudent.phone" />
														</td>
													</tr>
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="STYLE1">
															移动电话：
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
															colspan="2">
															&nbsp;
															<s:if test="peBzzStudent.mobilePhone == null "></s:if>
															<s:property value="peBzzStudent.mobilePhone" />
														</td>
													</tr>
													<tr>

														<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
															<strong>所在企业：</strong>
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
															colspan="2">
															&nbsp;
															<s:if test="peBzzStudent.peEnterprise.name == null "></s:if>
															<s:property value="peBzzStudent.peEnterprise.name" />
															 
														</td>
													</tr>
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="STYLE1">
															电子邮件：
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
															colspan="2">
															&nbsp;
															<s:if test="peBzzStudent.email == null "></s:if>
															<s:property value="peBzzStudent.email" />
														</td>
													</tr>
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="STYLE1">
															缓考状态：
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
															colspan="2">
															&nbsp;
															<s:if
																test="peBzzExamLate.enumConstByFlagExamLateStatus.name == null "></s:if>
															<s:property
																value="peBzzExamLate.enumConstByFlagExamLateStatus.name" />
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
														<!--  <td><a href="/entity/workspaceStudent/bzzstudent_StudentInfo.action"><img src="/entity/bzz-students/images/two/an_05.gif" width="71" height="23" border="0"></a></td>  -->
														<td align="center">

															<input type="button" value="打印缓考申请表"
																onClick="javascript:window.location = '/entity/bzz-students/exam/exam_late_print.jsp?student_id=<s:property value="peBzzStudent.id" />'" />
															&nbsp;&nbsp;&nbsp;&nbsp;
															<input type="button" value="返 &nbsp;回"
																stype="cursor:hand"
																onclick="javascript:window.navigate('/entity/workspaceStudent/bzzstudent_examBat.action')" />
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