<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page
	import="com.whaty.platform.sso.web.servlet.UserSession" />
<jsp:directive.page import="com.whaty.platform.database.oracle.*" />
<%@page import="com.whaty.platform.entity.bean.PeBzzStudent"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String d=String.valueOf(Math.random());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css.css" rel="stylesheet"
			type="text/css" />
		
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

a.ts:link { text-decoration: none;color: blue;}
a.ts:active { text-decoration:blink}
a.ts:hover { text-decoration:underline;color: red} 
a.ts:visited { text-decoration: none;color: #000000}

a:link { text-decoration: none;color: #000000}
a:active { text-decoration:blink}
a:hover { text-decoration:underline;color: red} 
a:visited { text-decoration: none;color: #000000}

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
<script type="text/javascript">
function hiddenTS(flag) {
if(flag=='0'){
document.getElementById("tishi").style.visibility="hidden";
}
if(flag=='1'){
document.getElementById("tishi1").style.visibility="hidden";
}
if(flag=='2'){
document.getElementById("tishi2").style.visibility="hidden";
}
	return false;
}
</script>
</head>
<body>
	<%@ include file="/entity/bzz-students/flash_image_precede.jsp" %>
		<%@ include file="/entity/bzz-students/flash_image.jsp" %> 
		<%
		String photo = ((PeBzzStudent)request.getAttribute("peBzzStudent")).getPhoto();
	  	boolean isPhotoed = true;
	  	String photoMessage = "";
	  	if(null == photo || "".equals(photo) || "null".equalsIgnoreCase(photo)){
	  		photoMessage = "您还没有上传照片，请在&ldquo;<a href=\"/entity/workspaceStudent/bzzstudent_StudentInfo.action\" class=\"ts\" style=\"color: blue;\">个人信息</a>&rdquo;功能中上传照片。";
	  		isPhotoed = false;
	  	}else{
	  		if(!photo.endsWith(".jpg")){
	  			photoMessage = "&ldquo;您还没有上传照片，请在<a href=\"/entity/workspaceStudent/bzzstudent_StudentInfo.action\" class=\"ts\" style=\"color: blue;\">个人信息</a>&rdquo;功能中上传照片。";
	  			isPhotoed = false;
	  		}//else{
	  		//	if(!"4028809c2d925bcf011d66fd0dda7006".equals(isPhotoConfirm)){	//照片未通确认
	  		//		photoMessage = "<font color='red'><br/>您已上传照片，但还未确认。<a href='/entity/workspaceStudent/bzzstudent_StudentInfo.action'>查看</a></font>";
	  		//	}
	  		//}
	  	}
	  	
	  	String emailMessage = (String)request.getAttribute("emailMessage");
	  	String emailMessageNote = "";
	  	if("1".equals(emailMessage)){
	  		emailMessageNote = "<br/>&nbsp;&nbsp;&nbsp;&nbsp;您的邮箱信息存在重复，为避免您找回密码受到影响请在&ldquo;<a href=\"/entity/workspaceStudent/bzzstudent_StudentInfo.action\" class=\"ts\" style=\"color: blue;\">个人信息</a>&rdquo;功能中修改您的邮箱地址。";
	  	}else if("2".equals(emailMessage)){
	  		emailMessageNote = "<br/>&nbsp;&nbsp;&nbsp;&nbsp;您的邮箱信息不完善，为方便您找回密码请在&ldquo;<a href=\"/entity/workspaceStudent/bzzstudent_StudentInfo.action\" class=\"ts\" style=\"color: blue;\">个人信息</a>&rdquo;功能中完善您的邮箱地址。";
	  	}
	  	
		if(session.getAttribute("student_alert_complete") == null) {
		%>
		
<%--  <div id="pic1" style="position:absolute; visibility:visible; left:0px; top:0px; z-index:5; width: 110; height: 97;">
<a href="/entity/workspaceStudent/bzzstudent_toExamIndex.action" target="_blank"><img src="/entity/bzz-students/images/exam.jpg" height="97" border="0"/></a>
 </div> 
<SCRIPT LANGUAGE="JavaScript">
<!-- Begin
var x = 50,y = 60 
var xin = true, yin = true 
var step = 1 
var delay = 10 
var obj=document.getElementById("pic1") 
function getwindowsize() { 
var L=T=0
//by www.qpsh.com
var R= document.body.clientWidth-obj.offsetWidth 
var B = document.body.clientHeight-obj.offsetHeight 
obj.style.left = x + document.body.scrollLeft 
obj.style.top = y + document.body.scrollTop 
x = x + step*(xin?1:-1) 
if (x < L) { xin = true; x = L} 
if (x > R){ xin = false; x = R} 
y = y + step*(yin?1:-1) 
if (y < T) { yin = true; y = T } 
if (y > B) { yin = false; y = B } 
} 
var itl= setInterval("getwindowsize()", delay) 
obj.onmouseover=function(){clearInterval(itl)} 
obj.onmouseout=function(){itl=setInterval("getwindowsize()", delay)} 
//  End -->
</script>--%>
		
		
		
		<div id="tishi" style="position: absolute;width: 450px; background-color:silver;left: 36%;top: 40%">
		<table width="100%">
		<tr >
			<td style="background-color:#8BC1ED;  color: white;"><table border="0" width="100%"><tr><td width="95%">提示信息</td><td><a href="#" style="background-color: transparent;color: white;" onclick="return hiddenTS(0);">X</a></td></tr></table></td>
		</tr>
		<tr>
		<td style="background-color: window; color: windowtext;" align="center"><table width="95%"><tr><td width="100%" align="left">
			<%
  	
  	String name = ((PeBzzStudent)request.getAttribute("peBzzStudent")).getTrueName();
  	
	String days = request.getParameter("days");
	
	Calendar c = Calendar.getInstance(); 
	
	c.set(Calendar.MINUTE, 0); 
	c.set(Calendar.SECOND, 0); 
	c.set(Calendar.MILLISECOND, 0); 
	
	c.set(Calendar.YEAR, 2010); 
	c.set(Calendar.MONTH, 11); //12月
	c.set(Calendar.DAY_OF_MONTH, 18); 
	long l1 = c.getTimeInMillis(); 
	
	days = ((l1 - new Date().getTime()) / (1000 * 60 * 60 * 24)) + ""; 
	  		
		double mtime=(Double)session.getAttribute("mtime");
		double ttime=(Double)session.getAttribute("ttime");
		double pct = mtime/ttime ;
		
		DecimalFormat df = new DecimalFormat("###.##");
		String mtStr = df.format(mtime);
		String ttStr = df.format(ttime);
  	%>
  	 <p><span style="font-size: 12pt; color: black; line-height: 150%; font-family: 'Courier New'"><%=name %></span><span style="font-size: 12pt; color: black; line-height: 150%; font-family: 宋体">：</span></p>
  	 <s:if test="1==1">
  	 <div style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%">
   <span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 宋体">
   您好!
       欢迎您进入中国证劵业协会远程培训系统
   </span>
       </div>
  	 </s:if>
<s:elseif test="peBzzExamScore!=null && exam_flag==0">
<div style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%">
   <span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 宋体">
   您好！考试定于2011年12月3日举行。
  <!--  您已报名参加考试，但<font color="red">尚未通过审核</font>。
   请您仔细检查一下<a href="/entity/workspaceStudent/bzzstudent_toJiChuCourses.action"><font color="blue">【基础课程】</font></a>的学习进度和基础课程的<a href="/entity/bzz-students/student_course_status2.jsp"><font color="blue">【作业自测】</font></a>完成情况，
   务必在报名考试截止前达到考试要求，以保证能够通过审核，顺利参加考试。<font color="red">自测成绩将计入总成绩</font>，
   请务必认真对待。祝您学有所成！ 
   --> 
   </span>
       </div>
</s:elseif>
<s:elseif test="peBzzExamScore!=null && exam_flag==1">
<div style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%">
   <span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 宋体">
   您好！考试定于2011年12月3日举行。
   您已通过考试审核，请认真复习，保证能够顺利完成考试。<font color="red">自测成绩将计入总成绩</font>， 
   请务必认真对待。祝您学有所成！&nbsp;
   </span>
       </div>
</s:elseif>
  	<s:else>
<div style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%">
   <% if(pct < 0.5) {%> 	
   <span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 宋体">您好！考试定于2011年12月3日举行。</span><b><span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 'Courier New'"></span></b><span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 宋体">您已完成</span><b><span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 'Courier New'"><%=mtStr %></span></b><span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 宋体">学时的课程学习，还有</span><b><span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 'Courier New'"><%=df.format(ttime-mtime) %></span></b><span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 宋体">学时的课程没有学习。请您加快学习进度，按计划完成<a href="/entity/workspaceStudent/bzzstudent_toJiChuCourses.action" class="ts" style="color: blue;">【必修课程学习】</a>、<a href="/entity/workspaceStudent/bzzstudent_toZiceCourses.action" class="ts" style="color: blue;">【作业自测】</a>和<a href="/entity/workspaceStudent/bzzstudent_toPingguCourses.action" class="ts" style="color: blue;">【课程评估】</a>，保证能够顺利参加考试。</span>
    <%} else if(pct >= 0.5 && pct < 1) {%>
    <span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 宋体">您好！考试定于2011年12月3日举行。</span><b><span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 'Courier New'"></span></b><span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 宋体">您已完成</span><b><span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 'Courier New'"><%=mtStr %></span></b><span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 宋体">学时的课程学习，还有</span><b><span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 'Courier New'"><%=df.format(ttime-mtime) %></span></b><span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 宋体">学时的课程没有学习。请您继续按计划完成<a href="/entity/workspaceStudent/bzzstudent_toJiChuCourses.action" class="ts" style="color: blue;">【必修课程学习】</a>、<a href="/entity/workspaceStudent/bzzstudent_toZiceCourses.action" class="ts" style="color: blue;">【作业自测】</a>和<a href="/entity/workspaceStudent/bzzstudent_toPingguCourses.action" class="ts" style="color: blue;">【课程评估】</a>，保证能够顺利参加考试。为了更好地提高发展自己，在学有余力的情况下，您可以有选择地进行<a href="/entity/workspaceStudent/bzzstudent_toTiShengCourses.action" class="ts" style="color: blue;">【提升课程】</a>的学习。祝您学有所成！&nbsp;</span>
    <%} else if(pct == 1) {%>
   <span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 宋体">您好！考试定于2011年12月3日举行。</span><b><span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 'Courier New'"></span></b><span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 宋体">您已完成基础课程的学习，请您检查一下<a href="/entity/workspaceStudent/bzzstudent_toJiChuCourses.action" class="ts" style="color: blue;">【必修课程】</a>的<a href="/entity/workspaceStudent/bzzstudent_toZiceCourses.action" class="ts" style="color: blue;">【作业自测】</a>和<a href="/entity/workspaceStudent/bzzstudent_toPingguCourses.action" class="ts" style="color: blue;">【课程评估】</a>是否完成，保证能够顺利参加考试。为了更好地提高发展自己，您可以有选择地进行<a href="/entity/workspaceStudent/bzzstudent_toTiShengCourses.action" class="ts" style="color: blue;">【提升课程】</a>的学习。祝您学有所成！</span>
    <%}%>
    </div>
    </s:else>
    <!--<s:if test='%{peBzzStudent.photo == null || peBzzStudent.photo.equals("")}'>
    	<div style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%"><span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 宋体">此外，您还没有上传照片，请在修改<a href="/entity/workspaceStudent/bzzstudent_StudentInfo.action" class="ts" style="color: blue;">&ldquo;个人信息&rdquo;</a>功能中上传照片。</span></div>
    </s:if>-->
    <div style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%"><span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 宋体">祝您 身体健康！工作顺利！<!--  <%= photoMessage %><%= emailMessageNote %>--></span></div>
    
  <br />
  <center><a href="#" onclick="return hiddenTS(0);">【关闭】</a></center>
		</td></tr></table></td></tr>
		</table>
	</div>
	<%}else{ 
		if(!isPhotoed){	
	%>
		
	<div id="tishi" style="position: absolute;width: 450px; background-color:silver;left: 36%;top: 40%">
		<table width="100%">
		<tr >
			<td style="background-color:#8BC1ED;  color: white;"><table border="0" width="100%"><tr><td width="95%">提示信息</td><td><a href="#" style="background-color: transparent;color: white;" onclick="return hiddenTS(0);">X</a></td></tr></table></td>
		</tr>
		<tr>
		<td style="background-color: window; color: windowtext;" align="center"><table width="95%"><tr><td width="100%" align="left">
    	<div style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%"><span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 宋体"><%= photoMessage %><%= emailMessageNote %></span></div>
    <div style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%"><span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 宋体">祝您 身体健康！工作顺利！</span></div>
    
		<center><a href="#" onclick="return hiddenTS(0);">【关闭】</a></center>
		</td></tr></table></td></tr>
		</table>
	</div>
	<%
		}
	}
	 %>
		<s:if test="showAgainInfo==1">
	<script>
	//window.alert("亲爱的学员您好！您的补考申请已经通过审核，可以开始学习了！");
	</script>
	</s:if>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<IFRAME NAME="top" width=100% height=150 frameborder=0 marginwidth=0
				marginheight=0 SRC="/entity/bzz-students/pub/top.jsp" scrolling=no
				allowTransparency="true"></IFRAME>
		</table>
		<table width="1002" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
			  <td width="237" valign="top" height="850"><iframe name="leftA" width=237 height=850 frameborder=0
						marginwidth=0 marginheight=0
						src="/entity/bzz-students/pub/left.jsp?d=<%=d %>" scrolling=no
						allowtransparency="true"></iframe></td>
			  <td width="543" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr align="center" valign="top">
							<td height="5" align="left" class="twocentop">
								<img src="/entity/bzz-students/images/two/1.jpg" width="11"
									height="10" />
								当前位置：浏览公告
								&nbsp;
									</tr>
									<tr>
										<td align="left">
											<img src="/entity/bzz-students/images/two/note.jpg"
												width="124" height="25" />										</td>
									</tr>
								</table>							
								<table width="96%" border="0" cellpadding="0" cellspacing="0">
									<tr>
									<td width="3%" height="30" align="center"background="/entity/bzz-students/images/two/two2_r15_c5.jpg">&nbsp;									  </td>
										<td width="57%" align="left"background="/entity/bzz-students/images/two/two2_r15_c5.jpg">
											&nbsp;&nbsp;标题										</td>
										<td width="17%" align="center" background="/entity/bzz-students/images/two/two2_r15_c5.jpg">
											时间										</td>
										<td width="23%" align="center" background="/entity/bzz-students/images/two/two2_r15_c5.jpg">
											发布人										</td>
									</tr>
								<!-- </table>
							</td>
						</tr>
						<tr valign="top">
							<td height="252" > -->
							<s:if test="bulletinList.size() > 0">	
								<s:iterator value="bulletinList" status="bulletin">
									<s:if test="title==1">
									<!-- <table width="96%" border="0" cellpadding="0" cellspacing="0"
										class="twocen"> -->
										<tr valign="bottom">
											<td width="3%" height="10" align="center">
												<img src="/entity/bzz-students/images/two/4.jpg" width="9"
													height="9" />											</td>
											<td width="57%" align="left">											</td>
											<td width="17%" align="left">											</td>
											<td width="23%" align="left">											</td>
										</tr>
										<tr valign="top">
											<td colspan="4">
												<img src="/entity/bzz-students/images/two/7.jpg" width="543"
													height="2" />											</td>
										</tr>
										<!-- </table> -->
									</s:if><s:else>
									<!-- <table width="96%" border="0" cellpadding="0" cellspacing="0"
										class="twocen"> -->
										<tr valign="bottom" height="25">
											<td width="3%" height="10" align="center">
												<img src="/entity/bzz-students/images/two/4.jpg" width="9"
													height="10" />											</td>
											<td width="57%" align="left">
												<a href='/entity/workspaceStudent/stuPeBulletinView_toInfo.action?bean.id=<s:property value="id"/>' target="_blank"><font size="2"><s:property value="title" /></font></a></td>
											<td width="17%" align="left">
										  <font size="2"><s:date name="publishDate" format="yyyy-MM-dd" /></font>											</td>
											<td width="23%" align="left">
												<s:if test="peManager!=null">
													<font size="2"><s:property value="peManager.trueName" /></font>
												</s:if>
												<s:else>
													<font size="2"><s:property value="enterpriseManager.name" /></font>
										  </s:else></td>
										</tr>
										<tr valign="top">
											<td colspan="4">
												<img src="/entity/bzz-students/images/two/7.jpg" width="543"
													height="2" /></td>
										</tr>
									<!-- </table> -->
									</s:else>
								</s:iterator>
								</s:if>
								
							<!-- </td>
						</tr> -->
					</table><br/><br/>
					<div class="font_bq" align="right" style="padding:0px 10px 0px 0px;"><a href="/entity/workspaceStudent/stuPeBulletinView_allPebulletins.action" target="_blank"><font color="#1E90FF">更多＞＞</font></a></div>				</td>
				<td width="13" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						
						<tr>
							<td height="280" align="center" valign="top"
								background="/entity/bzz-students/images/two/two2_r6_c8.jpg">
								<img name="two2_r4_c8"
									src="/entity/bzz-students/images/two/two2_r4_c8.jpg"
									width="209" height="52" border="0" id="two2_r4_c8" alt="" />
								<table width="90%" border="0" cellpadding="3" cellspacing="5"
									class="twocen">
									<tr valign="top">
										<td height="20" align="center">
											<font color="red"><s:property value="votePaper.title" />
											</font>										</td>
									</tr>
									<tr valign="top">
									  <td height="111" style="word-break: break-all" align="left">
									  <s:property value="votePaper.note" escape="false" />										</td>
									</tr>
									<tr valign="baseline">
										<td height="43" align="center">
											<a target="_blank"
												href="/entity/first/firstPeVotePaper_toVote.action?bean.id=<s:property value="votePaper.id"/>"><span class="font_bq" style="padding:0px 10px 0px 0px;"><a href="/entity/workspaceStudent/stuPeBulletinView_allPebulletins.action" target="_blank"><font color="#1E90FF"><img
													border="0"
													src="/entity/bzz-students/images/two/wenjuan.jpg"
													width="51" height="22" /></font></a></span>
											</a>
											<a target="_blank"
												href="/entity/first/firstPeVotePaper_voteResult.action?bean.id=<s:property value="votePaper.id"/>"><img
													border="0" src="/entity/bzz-students/images/two/9.jpg"
													width="72" height="22" />
											</a>										</td>
									</tr>
							</table>							</td>
						</tr>
					
						<tr>
							<td>
								<img name="two2_r22_c8"
									src="/entity/bzz-students/images/two/two2_r22_c8.jpg"
									width="209" height="40" border="0" id="two2_r22_c8" alt="" />							</td>
						</tr>
					</table>				</td>
			</tr>
		</table>			</td>
  </tr>
  <tr>
  	<td width="209" valign="top"><IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0
			marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no
			allowTransparency="true" align=center style=""></IFRAME></td>
  </tr>
</table>
	</body>
</html>