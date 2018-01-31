<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page import="java.util.*,java.text.*"%>
<%@ page import="com.whaty.util.*,com.whaty.platform.sso.web.action.*"%>
<%@page import="com.whaty.util.OnlineCourseCounter;"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String exambatchId = request.getParameter("exambatchId");
	String flag = "1";
%>


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<title>中国证券业协会远程培训系统</title>
		<link href="/web/bzz_index/style/index.css" rel="stylesheet"
			type="text/css">
		<link href="/web/bzz_index/style/style/xytd.css" rel="stylesheet"
			type="text/css">
		<link href="/web/zhibo/css/css.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="/web/bzz_index/js/wsMenu.js"></script>
		<%
			String loginUrl = "/";
			if (session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY) == null) {
		%>
		<script type="text/javascript">
				alert("连接超时，请重新登陆!");
				window.navigate("<%=loginUrl%>");
			</script>
		<%
			return;
			}
			//if(session.getAttribute("OnlineCourseSessionsIncreased") == null) {
			//OnlineCourseCounter.increaseOnlineCourseSessions();	
			//session.setAttribute("OnlineCourseSessionsIncreased",true);
			//System.out.println("++");
			//}
		%>
		<script type="text/javascript"> 
function copyText(obj) 
{ 
var rng = document.body.createTextRange(); 
rng.moveToElementText(obj); 
rng.scrollIntoView(); 
rng.select(); 
rng.execCommand("Copy"); 
rng.collapse(false);} 

function reinitIframe(){

var iframe = document.getElementById("xytd");

try{

var bHeight = iframe.contentWindow.document.body.scrollHeight;

var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;

var height = Math.max(bHeight, dHeight);

iframe.height = height;

}catch (ex){}

}

window.setInterval("reinitIframe()", 200);

</script>
		<SCRIPT LANGUAGE="JavaScript">
<!--
olddiv="9"
function menuclick(str){
document.getElementById("td"+olddiv).className="topmenu03";
document.getElementById("td"+str).className="topmenu031";
olddiv=str;
}
function menuover(str){
if(str!=olddiv)
{
document.getElementById("td"+str).className="topmenu031";
}
}
function menuout(str){

if(str!=olddiv)
{document.getElementById("td"+str).className="topmenu03";}

}

function clearAllChild()
{
	for(var i=1;i<5;i++)
	{
		document.getElementById("child"+i).style.display="none";
	}
	document.getElementById("child3img").src="/web/bzz_index/images/zx_07.gif";
}
//-->
</SCRIPT>
		<script language="javascript">
var preObj = new Object();
function overM(obj)
{
	obj.className = "top2";
}
function outM(obj)
{
	if(obj.on != "true")
		obj.className = "top1";
}
function gotoM(URL)
{
	if(preObj && preObj!=event.srcElement)
	{
		preObj.on = false;
		outM(preObj);
	}
	if(event.srcElement)
	{
		preObj = event.srcElement;
	event.srcElement.on = "true";
	}
	//
	var tempURL = parent.document.getElementById("proBox").src;
	tempURL = tempURL.substring(0,tempURL.indexOf("/")+1);
	parent.document.getElementById("pageBox").src = tempURL+URL;
}
//
function SH(id)
{
	if(document.getElementById(id))
	document.getElementById(id).style.display = (document.getElementById(id).style.display=="none")?"block":"none";
}
</script>
		<script language="JavaScript">
function show(DivId)
{
	if(document.all[DivId].style.display=='none')
	  { 
	  document.all[DivId].style.display='' 
	  	 if(DivId=="child3"){
		 document.getElementById("child3img").src="/web/bzz_index/images/zx_071.gif";
	     }
	  }
	else
	  { 
	  document.all[DivId].style.display='none'
	 document.getElementById("child3img").src="/web/bzz_index/images/zx_07.gif"; 	
		 
	     
	  }
	  

	return 0;
}

function closeWindow() {
	window.navigate("/web/bzz_index/zhibo/closeWindow.jsp");
	//window.close();
}

</script>

		<style type="text/css">
<!--
.sp_info {
	font-size: 12px;
	line-height: 20px;
	padding-left: 10px;
}

.sp_title {
	font-size: 14px;
	font-weight: bold;
	color: #FFFFFF;
	padding-top: 7px;
}

.sp_rcap {
	font-size: 12px;
	line-height: 24px;
	color: #FFFFFF;
}

.sp_text {
	font-size: 12px;
	line-height: 24px;
	padding: 10px;
}
-->
</style>
	</head>

	<body onunload="closeWindow()">
		<table width="912" border="0" align="center" cellpadding="0"
			cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td height="139">
					<img src="/web/bzz_index/zhibo/images/top.jpg" width="912"
						height="139">
				</td>
			</tr>
			<tr>
				<td align="center" background="/web/bzz_index/zhibo/images/bg.jpg"
					class="body_box">
					<br/>
					<table width="26" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<table width="780" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="257" valign="top">
											<table width="257" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td>
														<img src="/web/bzz_index/zhibo/images/spk_03.jpg"
															width="257" height="36">
													</td>
												</tr>
												<tr>
													<td>
														<img src="/web/bzz_index/zhibo/images/spk_09.jpg"
															width="257" height="72">
													</td>
												</tr>
												<tr>
													<td align="center"
														background="/web/bzz_index/zhibo/images/spk_15.jpg"
														class="sp_rcap">
														&lt;&lt; 知识点 &gt;&gt;
													</td>
												</tr>
												<tr>
													<td align="center"
														background="/web/bzz_index/zhibo/images/spk_15.jpg">
														<table width="85%" border="0" cellpadding="0"
															cellspacing="0" bgcolor="#ffeeee">
															<tr>
																<td height="356" class="sp_text">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																		<tr class="trbor">
																			<td width="14" align="center" valign="middle">
																				<img src="/web/zhibo/images/sp_13.png" width="5"
																					height="5" />
																			</td>
																			<td width="56" align="left" valign="middle">
																				&nbsp;
																				<!--9:00-9:10-->
																			</td>
																			<td width="200" align="left">
																				&nbsp;
																				<!--  主讲嘉宾介绍 -->
																			</td>
																		</tr>
																		<tr class="trbor">
																			<td width="14" align="center" valign="middle">
																				<img src="/web/zhibo/images/sp_13.png" width="5"
																					height="5" />
																			</td>
																			<td width="56" align="left" valign="middle">
																				&nbsp;
																				<!--9:10—11:10-->
																			</td>
																			<td align="left">
																				&nbsp;
																				<!-- 王洪军讲授《平凡岗位成就不平凡事业—谈中央企业班组长岗位创新》-->
																			</td>
																		</tr>
																		<tr class="trbor">
																			<td width="14" align="center" valign="middle">
																				<img src="/web/zhibo/images/sp_13.png" width="5"
																					height="5" />
																			</td>
																			<td width="58" align="left" valign="middle">
																				&nbsp;
																				<!--11:10—11:40-->
																			</td>
																			<td align="left">
																				&nbsp;
																				<!-- 互动环节，观众现场提问、主持人即兴提问，学员在线提问，论坛问题选答-->
																			</td>
																		</tr>

																	</table>
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td align="center"
														background="/web/bzz_index/zhibo/images/spk_15.jpg">
														<a
															href="/entity/bzz-students/exam/exam_answer_tw.jsp?exambatchId=<%=exambatchId%>&flag=<%=flag%>"
															target="_blank"> <img
																src="/web/bzz_index/zhibo/images/zxtw.gif" width="231"
																height="60" vspace="10" border="0"> </a>
													</td>
												</tr>
											</table>
										</td>
										<td valign="top">
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0"
												background="/web/bzz_index/zhibo/images/spk_12.jpg">
												<tr>
													<td>
														<table width="100%" border="0" cellspacing="0"
															cellpadding="0">
															<tr>
																<td width="131">
																	<img src="/web/bzz_index/zhibo/images/spk_04.jpg"
																		width="131" height="36">
																</td>
																<td align="center"
																	background="/web/bzz_index/zhibo/images/spk_05.jpg"
																	class="sp_title">
																	串讲课堂
																</td>
																<td width="119">
																	<img src="/web/bzz_index/zhibo/images/spk_07.jpg"
																		width="119" height="36">
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td height="437" align="center"
														background="/web/bzz_index/zhibo/images/spk_13.jpg">
														<br/>
														<table width="100%" border="0" cellspacing="0"
															cellpadding="0">
															<tr>
																<td align="center">
																	<table width="464" height="350" border="0"
																		cellpadding="0" cellspacing="2" bgcolor="#d95050">
																		<tr>
																			<td align="center" bgcolor="#FFFFFF">
																				<object id=nstv
																					classid='CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6'
																					width=460 height=346
																					codebase=http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701standby=Loading
																					Microsoft? Windows Media? Player components...
																					type=application/x-oleobject>
																					<param name='URL'
																						value='http://www.thbzzpx.org/courseserver/search_server2.jsp'>
																					<PARAM NAME='UIMode' value='none'>
																					<PARAM NAME='AutoStart' value='true'>
																					<PARAM NAME='Enabled' value='true'>
																					<PARAM NAME='stretchToFit' value='true'>
																					<PARAM NAME='enableContextMenu' value='true'>
																					<param name='WindowlessVideo' value='true'>
																				</object>
																			</td>
																		</tr>
																	</table>
																</td>
															</tr>
															<tr>
																<td align="center">
																	<img src="/web/bzz_index/zhibo/images/spk_22.jpg"
																		width="468" height="40">
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td height="27">
														<table width="520" height="27" border="0" align="center"
															cellpadding="0" cellspacing="0">
															<tr>
																<td height="27"
																	background="/web/bzz_index/zhibo/images/spk_18.jpg"
																	class="sp_info">
																	&nbsp;资料下载：
																	<%--<jsp:include page= "/web/bzz_index/zhibo/onlinePersons.txt" flush= "true "> 
								      <jsp:param   name= "temp"   value= "<%=new Date().getTime()%>"/> 
								</jsp:include> --%>
																</td>
															</tr>
															<tr>
																<td height="68"
																	background="/web/bzz_index/zhibo/images/spk_20.jpg"
																	class="sp_info">
																	<!--讲座主题：《平凡岗位成就不平凡事业——谈中央企业班组长岗位上的创新》<br/>
                            主讲嘉宾：王洪军<br/>
                            讲座时间：2010年6月29日（星期二）  9:00-11:30-->
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<img src="/web/bzz_index/zhibo/images/spk_21.jpg" width="780"
									height="7">
							</td>
						</tr>
					</table>
					<br/>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0" bgcolor="#FFFFFF">

						<tr>
							<td height="54" align="center" valign="middle"
								background="/web/bzz_index/images/bottom_08.jpg">
								<span class="down">版权所有：中国证劵业协会企业远程培训中心 京ICP备05046845号 <BR>
									传真：010-11116666 咨询服务电话：010-62415115</span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>


	</body>
</html>
