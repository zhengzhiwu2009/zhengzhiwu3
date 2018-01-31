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
		<link href="/entity/manager/statistics/css/admincss.css"
			rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" media="all"
			href="/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />
		<script type="text/javascript" src="/js/calendar/calendar.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
		<script language="javascript" src="/js/calender.js"></script>
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
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
			if(elem[k].type=='text'&&elem[k].value.length<1 && elem[k].name!="phone" && elem[k].name!="email"){
				alert("除办公电话和电子邮件外，其他各项内容均需填写,请完善您的资料！");
				return false;
			}else{
				if(elem[k].name=="mobilePhone"&&!Mobile.test(elem[k].value)){
						alert("移动电话格式不正确!");
						return false;
			    }
				if(elem[k].name=="phone"&&!Phone.test(elem[k].value)&& elem[k].value.length >=1){
						alert("办公电话格式不正确!");
						return false;
				}
				if(elem[k].name=="email"&&!Email.test(elem[k].value)&& elem[k].value.length >=1 ){
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
		<form
			action="/entity/workspaceStudent/bzzstudent_examInfoModify.action"
			method="post" name="infoform" onsubmit="return checknull();">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td valign="top" class="bg3">
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
															<strong>修改信息</strong>
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
															<input type="hidden" name="peBzzStudent.trueName"
																value="<s:property value="peBzzStudent.trueName"/>" />
															&nbsp;
															<s:if test="peBzzStudent.trueName == null "></s:if>
															<s:property value="peBzzStudent.trueName" />
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
															<font color="red" size="2px">*
																此日期将用于认证证书，请认真核对，以身份证为准。</font>
														</td>
													</tr>
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
															<font color="red" size="3">*</font>
															<strong>民    族：</strong>
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
															&nbsp;
															<SELECT id=folk name=folk>
																<OPTION value=汉族
																	<s:if test='peBzzStudent.folk=="汉族"'>selected</s:if>>
																	汉族
																</OPTION>
																<OPTION value=蒙古族
																	<s:if test='peBzzStudent.folk=="蒙古族"'>selected</s:if>>
																	蒙古族
																</OPTION>
																<OPTION value=回族
																	<s:if test='peBzzStudent.folk=="回族"'>selected</s:if>>
																	回族
																</OPTION>
																<OPTION value=藏族
																	<s:if test='peBzzStudent.folk=="藏族"'>selected</s:if>>
																	藏族
																</OPTION>
																<OPTION value=维吾尔族
																	<s:if test='peBzzStudent.folk=="维吾尔族"'>selected</s:if>>
																	维吾尔族
																</OPTION>
																<OPTION value=苗族
																	<s:if test='peBzzStudent.folk=="苗族"'>selected</s:if>>
																	苗族
																</OPTION>
																<OPTION value=彝族
																	<s:if test='peBzzStudent.folk=="彝族"'>selected</s:if>>
																	彝族
																</OPTION>
																<OPTION value=壮族
																	<s:if test='peBzzStudent.folk=="壮族"'>selected</s:if>>
																	壮族
																</OPTION>
																<OPTION value=布依族
																	<s:if test='peBzzStudent.folk=="布依族"'>selected</s:if>>
																	布依族
																</OPTION>
																<OPTION value=朝鲜族
																	<s:if test='peBzzStudent.folk=="朝鲜族"'>selected</s:if>>
																	朝鲜族
																</OPTION>
																<OPTION value=满族
																	<s:if test='peBzzStudent.folk=="满族"'>selected</s:if>>
																	满族
																</OPTION>
																<OPTION value=侗族
																	<s:if test='peBzzStudent.folk=="侗族"'>selected</s:if>>
																	侗族
																</OPTION>
																<OPTION value=瑶族
																	<s:if test='peBzzStudent.folk=="瑶族"'>selected</s:if>>
																	瑶族
																</OPTION>
																<OPTION value=白族
																	<s:if test='peBzzStudent.folk=="白族"'>selected</s:if>>
																	白族
																</OPTION>
																<OPTION value=土家族
																	<s:if test='peBzzStudent.folk=="土家族"'>selected</s:if>>
																	土家族
																</OPTION>
																<OPTION value=哈尼族
																	<s:if test='peBzzStudent.folk=="哈尼族"'>selected</s:if>>
																	哈尼族
																</OPTION>
																<OPTION value=哈萨克族
																	<s:if test='peBzzStudent.folk=="哈萨克族"'>selected</s:if>>
																	哈萨克族
																</OPTION>
																<OPTION value=傣族
																	<s:if test='peBzzStudent.folk=="傣族"'>selected</s:if>>
																	傣族
																</OPTION>
																<OPTION value=黎族
																	<s:if test='peBzzStudent.folk=="黎族"'>selected</s:if>>
																	黎族
																</OPTION>
																<OPTION value=傈僳族
																	<s:if test='peBzzStudent.folk=="傈僳族"'>selected</s:if>>
																	傈僳族
																</OPTION>
																<OPTION value=佤族
																	<s:if test='peBzzStudent.folk=="佤族"'>selected</s:if>>
																	佤族
																</OPTION>
																<OPTION value=畲族
																	<s:if test='peBzzStudent.folk=="畲族"'>selected</s:if>>
																	畲族
																</OPTION>
																<OPTION value=高山族
																	<s:if test='peBzzStudent.folk=="高山族"'>selected</s:if>>
																	高山族
																</OPTION>
																<OPTION value=拉祜族
																	<s:if test='peBzzStudent.folk=="拉祜族"'>selected</s:if>>
																	拉祜族
																</OPTION>
																<OPTION value=水族
																	<s:if test='peBzzStudent.folk=="水族"'>selected</s:if>>
																	水族
																</OPTION>
																<OPTION value=东乡族
																	<s:if test='peBzzStudent.folk=="东乡族"'>selected</s:if>>
																	东乡族
																</OPTION>
																<OPTION value=纳西族
																	<s:if test='peBzzStudent.folk=="纳西族"'>selected</s:if>>
																	纳西族
																</OPTION>
																<OPTION value=景颇族
																	<s:if test='peBzzStudent.folk=="景颇族"'>selected</s:if>>
																	景颇族
																</OPTION>
																<OPTION value=柯尔克孜族
																	<s:if test='peBzzStudent.folk=="柯尔克孜族"'>selected</s:if>>
																	柯尔克孜族
																</OPTION>
																<OPTION value=土族
																	<s:if test='peBzzStudent.folk=="土族"'>selected</s:if>>
																	土族
																</OPTION>
																<OPTION value=达斡尔族
																	<s:if test='peBzzStudent.folk=="达斡尔族"'>selected</s:if>>
																	达斡尔族
																</OPTION>
																<OPTION value=仫佬族
																	<s:if test='peBzzStudent.folk=="仫佬族"'>selected</s:if>>
																	仫佬族
																</OPTION>
																<OPTION value=羌族
																	<s:if test='peBzzStudent.folk=="羌族"'>selected</s:if>>
																	羌族
																</OPTION>
																<OPTION value=布朗族
																	<s:if test='peBzzStudent.folk=="布朗族"'>selected</s:if>>
																	布朗族
																</OPTION>
																<OPTION value=撒拉族
																	<s:if test='peBzzStudent.folk=="撒拉族"'>selected</s:if>>
																	撒拉族
																</OPTION>
																<OPTION value=毛难族
																	<s:if test='peBzzStudent.folk=="毛难族"'>selected</s:if>>
																	毛难族
																</OPTION>
																<OPTION value=仡佬族
																	<s:if test='peBzzStudent.folk=="仡佬族"'>selected</s:if>>
																	仡佬族
																</OPTION>
																<OPTION value=锡伯族
																	<s:if test='peBzzStudent.folk=="锡伯族"'>selected</s:if>>
																	锡伯族
																</OPTION>
																<OPTION value=阿昌族
																	<s:if test='peBzzStudent.folk=="阿昌族"'>selected</s:if>>
																	阿昌族
																</OPTION>
																<OPTION value=普米族
																	<s:if test='peBzzStudent.folk=="普米族"'>selected</s:if>>
																	普米族
																</OPTION>
																<OPTION value=塔吉克族
																	<s:if test='peBzzStudent.folk=="塔吉克族"'>selected</s:if>>
																	塔吉克族
																</OPTION>
																<OPTION value=怒族
																	<s:if test='peBzzStudent.folk=="怒族"'>selected</s:if>>
																	怒族
																</OPTION>
																<OPTION value=乌兹别克族
																	<s:if test='peBzzStudent.folk=="乌兹别克族"'>selected</s:if>>
																	乌兹别克族
																</OPTION>
																<OPTION value=俄罗斯族
																	<s:if test='peBzzStudent.folk=="俄罗斯族"'>selected</s:if>>
																	俄罗斯族
																</OPTION>
																<OPTION value=鄂温克族
																	<s:if test='peBzzStudent.folk=="鄂温克族"'>selected</s:if>>
																	鄂温克族
																</OPTION>
																<OPTION value=崩龙族
																	<s:if test='peBzzStudent.folk=="崩龙族"'>selected</s:if>>
																	崩龙族
																</OPTION>
																<OPTION value=保安族
																	<s:if test='peBzzStudent.folk=="保安族"'>selected</s:if>>
																	保安族
																</OPTION>
																<OPTION value=裕固族
																	<s:if test='peBzzStudent.folk=="裕固族"'>selected</s:if>>
																	裕固族
																</OPTION>
																<OPTION value=京族
																	<s:if test='peBzzStudent.folk=="京族"'>selected</s:if>>
																	京族
																</OPTION>
																<OPTION value=塔塔尔族
																	<s:if test='peBzzStudent.folk=="塔塔尔族"'>selected</s:if>>
																	塔塔尔族
																</OPTION>
																<OPTION value=独龙族
																	<s:if test='peBzzStudent.folk=="独龙族"'>selected</s:if>>
																	独龙族
																</OPTION>
																<OPTION value=鄂伦春族
																	<s:if test='peBzzStudent.folk=="鄂伦春族"'>selected</s:if>>
																	鄂伦春族
																</OPTION>
																<OPTION value=赫哲族
																	<s:if test='peBzzStudent.folk=="赫哲族"'>selected</s:if>>
																	赫哲族
																</OPTION>
																<OPTION value=门巴族
																	<s:if test='peBzzStudent.folk=="门巴族"'>selected</s:if>>
																	门巴族
																</OPTION>
																<OPTION value=珞巴族
																	<s:if test='peBzzStudent.folk=="珞巴族"'>selected</s:if>>
																	珞巴族
																</OPTION>
																<OPTION value=基诺族
																	<s:if test='peBzzStudent.folk=="基诺族"'>selected</s:if>>
																	基诺族
																</OPTION>
																<OPTION value=其他
																	<s:if test='peBzzStudent.folk=="其他"'>selected</s:if>>
																	其他
																</OPTION>
																<OPTION value=外国血统
																	<s:if test='peBzzStudent.folk=="外国血统"'>selected</s:if>>
																	外国血统
																</OPTION>
															</SELECT>
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
													<%-- <tr>
						        <td align="center" bgcolor="#E9F4FF" class="STYLE1"><font color="red" size="3">*</font> 通信地址：</td>
						        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2"><input type="hidden" name="peBzzStudent.address" value="<s:property value="peBzzStudent.address"/>"/> &nbsp; <s:if test='peBzzStudent.address.length()>0'><s:property value="peBzzStudent.address"/></s:if><s:else></s:else></td>
						      </tr> --%>
													<tr>
														<td align="center" bgcolor="#E9F4FF" class="kctx_zi2">
															<DIV align="center" class="STYLE1">
																办公电话：
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
															电子邮件：
														</td>
														<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
															colspan="2">
															&nbsp;
															<input type="text" name="email"
																value="<s:if test='peBzzStudent.email.length()>0'><s:property value="peBzzStudent.email"/></s:if><s:else></s:else>"
																maxlength=50 />
															<font color="red" size="2px">* 50个字以内,如无则空白</font>
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
																onclick="javascript:window.navigate('/entity/workspaceStudent/bzzstudent_toModifyInfoN.action')" />
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
					</td>
				</tr>
			</table>
		</form>
	</body>
	<script type="text/javascript">
    Calendar.setup({
    	inputField     :    "peBzzStudent.birthdayDate",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_a",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
	</script>
</html>