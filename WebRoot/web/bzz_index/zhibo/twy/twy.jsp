<%@ page language="java"
	import="java.util.*,com.whaty.platform.sso.web.action.*"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String textAreaContent = "请输入问题内容(200字以内):";
	String disabled = "";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>中国证券业协会远程培训系统</title>
		<script>
<!--
var totalQ = 16;
var totalW = 5;
var rightNum=0;
var prePauseTime = 5000;
parentObj='kcxx_frame';
//-->
function textarea_confirm(str)
  {
if( document.getElementById("p"+str).value == "请输入答案:" )
document.getElementById("p"+str).value= "";
  }
  
  function checkForm() {
  	var question = document.getElementById("question");
  	if(question.value == "<%=textAreaContent%>" || question.value == "")
  	{
  		alert("请输入问题");
  		question.focus();
  		return false;
  	} else if(question.value.length > 200)
  	{
  		alert("输入的问题不能超过200字");
  		question.focus();
  		return false;
  	}
  	return true;
  }
  
</script>
		<s:if test="onlineCourseTwyList.size() >= 5">
			<%
				textAreaContent = "每人最多只能提出5个问题!";
					disabled = "disabled=\"disabled\"";
			%>
		</s:if>
		<link href="/web/bzz_index/zhibo/twy/css/css.css" rel="stylesheet"
			type="text/css" />
		<style type="text/css">
<!--
body {
	background-color: #E0E0E0;
}
-->
</style>
		<%
			String loginUrl = "/web/bzz_index/zhibo/login/login.jsp";
			if (session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY) == null) {
		%>
		<script type="text/javascript">
				alert("连接超时，请重新登陆!");
				window.navigate("<%=loginUrl%>");
			</script>
		<%
			return;
			}
			String errMessage = (String) request.getAttribute("errMessage");
			if (errMessage != null) {
		%>
		<script type="text/javascript">
	alert("<%=errMessage%>");
</script>
		<%
			}
		%>
	</head>

	<body>
		<table width="912" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
					<img src="/web/bzz_index/zhibo/twy/images/twy_01.jpg" width="912"
						height="166" />
				</td>
			</tr>
			<tr>
				<td valign="top">
					<table width="912" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="right" valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td align="right" valign="middle" class="cc_bg"
											style="padding-right: 150px; padding-top: 0px; padding-bottom: 0px;">
											<a href="javascript:self.close()"><span class="cc">关闭</span>
											</a>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr height="60">
							<td class="bg1">
								<table width="75%" border="0" align="center" cellpadding="0"
									cellspacing="0" style="margin-top: 10px">
									<tr>
										<td width="3%" align="right">
											<img src="/web/bzz_index/zhibo/twy/images/c2.jpg" width="15"
												height="13" />
										</td>
										<td width="97%" class="font1">
											<strong>您已提问题列表</strong>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<!--
      <tr>
        <td><img src="/web/bzz_index/zhibo/twy/images/twy_03.jpg" width="912" height="74" /></td>
      </tr> -->
						<tr>
							<td class="body_content">
								<table width="70%" border="0" align="center" cellpadding="0"
									cellspacing="0">
									<s:if test="onlineCourseTwyList.size() > 0">

										<s:iterator value="onlineCourseTwyList" status="stuts">
											<tr>
												<td width="5%" height="25" align="center" class="di_bi">
													<img src="/web/bzz_index/zhibo/twy/images/twy_013.jpg"
														width="19" height="17" />
												</td>
												<td width="95%" height="25" class="di_bi">
													<s:property value="#stuts.index+1" />
													.
													<s:property value="getQuestion()" />
												</td>
											</tr>
										</s:iterator>
									</s:if>
									<s:else>
										<tr>
											<td width="100%" height="25" class="di_bi">
												<center>
													您还没有提问。
												</center>
											</td>
										</tr>
									</s:else>
								</table>
							</td>
						</tr>
						<tr>
							<td class="body_content" style="padding-left: 117px">
								<img src="/web/bzz_index/zhibo/twy/images/tw1.jpg" width="673"
									height="19" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="bg1">
					<form
						action="/entity/workspaceStudent/bzzOlineCourseTwy_addQuestion.action"
						method="post" id="questionFrom" onsubmit="return checkForm()">
						<table width="75%" border="0" align="center" cellpadding="0"
							cellspacing="0" style="margin-top: 10px">
							<tr>
								<td width="3%" align="right">
									<img src="/web/bzz_index/zhibo/twy/images/c2.jpg" width="15"
										height="13" />
								</td>
								<td width="97%" class="font1">
									<strong>我要提问</strong> 请在下面输入您的问题，我们将尽快答复您，每人限提5个问题！
								</td>
							</tr>
							<tr>
								<td colspan="2" align="center">
									<table width="673" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td align="center">
												<img src="/web/bzz_index/zhibo/twy/images/tt_13.jpg"
													width="673" height="12" />
											</td>
										</tr>
										<tr class="red">
											<td align="center">
												<table width="95%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td width="26%" style="padding: 10px 10px">
															<img src="/web/bzz_index/zhibo/twy/images/tt3_14.jpg"
																width="135" height="129" />
														</td>
														<td width="74%">
															<textarea name="peBzzOnlineCourseTwy.question"
																id="question" class="areashort" style="overflow: auto"
																onFocus="this.className='areashort';if(this.value=='<%=textAreaContent%>')this.value='';"
																onBlur="this.className='areashort';if(this.value=='')this.value='<%=textAreaContent%>';"
																<%=disabled%>><%=textAreaContent%></textarea>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td align="center">
												<img src="/web/bzz_index/zhibo/twy/images/tt_17.jpg"
													width="673" height="14" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="2" align="center" style="padding: 30px 10px"
									class="hand">
									<input type="image"
										src="/web/bzz_index/zhibo/twy/images/tt4_18.jpg" width="155"
										height="35" <%=disabled%> />
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td bgcolor="#F3F3F3" class="bot font2"
								style="padding: 10px 20px">
								<p>
									版权所有：中国证劵业协会 京ICP备05046845
								</p>
								<p>
									投诉：010-11110000 传真：010-11116666 咨询服务电话：010-62415115
								</p>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>

