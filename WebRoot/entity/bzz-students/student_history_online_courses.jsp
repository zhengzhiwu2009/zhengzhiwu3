<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page
	import="com.whaty.platform.sso.web.servlet.UserSession" />
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool" />
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String d = String.valueOf(Math.random());
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//request.getSession().setAttribute("firstPage", "/entity/function/testpaper/onlinetestcourse_list.jsp");
	String disabled = "";
	UserSession userSession = (UserSession) session
			.getAttribute("user_session");
	if (userSession != null) {
	} else {
%>
<script>
	window.alert("长时间未操作或操作异常，为了您的账号安全，请重新登录！");
	window.location = "/";
</script>
<%
	return;
	}
	String user_id = userSession.getId();
	String reg_no = userSession.getLoginId();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<script type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.STYLE1 {
	color: #FF0000;
	font-weight: bold;
}

.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}
-->
</style>
	</head>

	<body onLoad="MM_preloadImages('/entity/bzz-students/images/dqjz2.jpg','/entity/bzz-students/images/dqjz2_over.jpg','/entity/bzz-students/images/lsjz1.jpg','/entity/bzz-students/images/lsjz1_over.jpg')">
		<table width="1002" border="0" align="center" cellpadding="0"
			cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td>
					<table width="1002" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<IFRAME NAME="top" width=100% height=200 frameborder=0
							marginwidth=0 marginheight=0
							SRC="/entity/bzz-students/pub/top.jsp" scrolling=no
							allowTransparency="true"></IFRAME>
						<tr>
							<td height="13"></td>
						</tr>
					</table>
					<table width="1002" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="237" valign="top">
								<IFRAME NAME="leftA" width=237 height=580 frameborder=0
									marginwidth=0 marginheight=0
									SRC="/entity/bzz-students/pub/left.jsp?d=<%=d%>" scrolling=no
									allowTransparency="true"></IFRAME>
							</td>
							<td width="752" valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr align="center" valign="top">
										<td height="17" align="left" class="twocentop">
											<img src="/entity/bzz-students/images/two/1.jpg" width="11"
												height="12" />
											当前位置：在线课堂 
										</td>
									</tr>
									<tr>
										<td>
											<br/>
											<!-- <img name="two2_r5_c5" src="images/two/two2_r5_c5.jpg" width="750" height="72" border="0" id="two2_r5_c5" alt="" /> -->
										</td>
									</tr>
									
       <tr>
            <td align="left"><a href="/entity/workspaceStudent/bzzstudent_toOnlineCourses.action"><img border="0" src="/entity/bzz-students/images/dqjz2.jpg" width="124" height="25"  name="Image1" onMouseOver="MM_swapImage('Image1','','/entity/bzz-students/images/dqjz2_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="/entity/workspaceStudent/bzzstudent_toHistoryOnlineCourses.action"><img border="0" src="/entity/bzz-students/images/lsjz1.jpg" width="124" height="25"  name="Image2" onMouseOver="MM_swapImage('Image2','','/entity/bzz-students/images/lsjz1_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a>
            </td>
          </tr>
           
									<tr align="center">
										<td height="29"
											background="/entity/bzz-students/images/two/two2_r15_c5.jpg">
											<table width="96%" border="0" cellpadding="0" cellspacing="0"
												class="twocencetop">

												<tr>
													<td width="5%" align="center"></td>
													<td width="20%" align="left">
														讲座主题
													</td>
													<td width="20%" align="center">
														日期
													</td>
													<td width="15%" align="center">
														开始时间
													</td>
													<td width="15%" align="center">
														结束时间
													</td>
													<td width="10%" align="center">
														主持人
													</td>
													<td width="10%" align="center">
														点播课件
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr valign="top">
										<td>
											<table width="100" border="0" cellpadding="0" cellspacing="0"
												class="twocen">
												<s:if test="peBzzOnlineCourses.size > 0">
													<s:iterator value="peBzzOnlineCourses" status="stuts">
														<tr>
															<td width="22" height="30" align="center">
																<img src="/entity/bzz-students/images/two/4.jpg"
																	width="9" height="9" />															</td>
															<td width="183" align="left">
																<s:property value="getSubject()" />
														  </td>
															<td width="158" align="center">
																<s:property value="formateCourseDate()" />
														  </td>
															<td width="113" align="center">
																<s:property value="getStartTime()" />
														  </td>
															<td width="113" align="center">
																<s:property value="getEndTime()" />
														  </td>
															<td width="62"  align="center">
																<s:property value="getEmcee()" />
														  </td>
															<td width="95"  align="center">
																<s:if test="getIndexUrl() != null">
																
																<a href="/entity/bzz-students/jumpOnlineCourse.jsp?course_id=<s:property value="getId()" />"
																	class="button" title="点播" target="_blank"><img
																		src="/entity/bzz-students/images/two/6.jpg" width="21"
																		height="19" border=0 />
																</a>
																
																</s:if>	<s:else>暂无课件</s:else>														</td>
															
														</tr>
														<tr>
															<td colspan="7">
																<img src="/entity/bzz-students/images/two/7.jpg"
																	width="752" height="4" />
															</td>
														</tr>
													</s:iterator>
												<tr>
													<td></td>
												</tr>
												<tr align="center">
													<td height="29"
														background="/entity/bzz-students/images/two/two2_r15_c5.jpg"
														colspan="8">
														<table width="96%" border="0" cellpadding="0"
															cellspacing="0" class="twocencetop">
															<tr>
																<td width="20%">
																	<s:if test="pagedown!=-1">
																		<a
																			href="/entity/workspaceStudent/bzzstudent_toHistoryOnlineCourses.action?pagenow=1&pageSize =<s:property value ='pageSize'/>">首
																			页</a>
																	</s:if>
																	<s:else> &nbsp; </s:else>
																</td>
																<td width="20%">
																	<s:if test="pagedown!=-1">
																		<a
																			href="/entity/workspaceStudent/bzzstudent_toHistoryOnlineCourses.action?pagenow=<s:property value ='pagedown'/>&pageSize=<s:property value ='pageSize'/>">上一页</a>
																	</s:if>
																</td>
																<td width="20%">
																	<s:if test="pagenext!=-1">
																		<a
																			href="/entity/workspaceStudent/bzzstudent_toHistoryOnlineCourses.action?pagenow=<s:property value ='pagenext'/>&pageSize=<s:property value ='pageSize'/>">下一页</a>
																	</s:if>
																</td>
																<td width="20%">
																	<s:if test="pagenext!=-1">
																		<a
																			href="/entity/workspaceStudent/bzzstudent_toHistoryOnlineCourses.action?pagenow=<s:property value ='pagetotal'/>&pageSize=<s:property value='pageSize'/>">末
																			页</a>
																	</s:if>
																</td>
																<td width="20%">
																	共&nbsp;
																	<s:property value='pagetotal' />
																	&nbsp;页/&nbsp;
																	<s:property value='pagecount' />
																	&nbsp;条
																</td>
															</tr>
														</table>
													</td>
												</tr>
												</s:if>
												

											</table>
										</td>
									</tr>
									<tr>
										<td width="13">&nbsp;
											
										</td>
									</tr>

								</table>
							</td>
							<td width="13">&nbsp;
								
							</td>
						</tr>
					</table>
					<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0
						marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp"
						scrolling=no allowTransparency="true" align=center></IFRAME>
				</td>
			</tr>
		</table>
	</body>
</html>