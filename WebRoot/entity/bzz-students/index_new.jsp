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
	String d = String.valueOf(Math.random());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css.css" rel="stylesheet"
			type="text/css" />
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet"
			type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet"
			type="text/css" />

		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

a.ts:link {
	text-decoration: none;
	color: blue;
}

a.ts:active {
	text-decoration: blink
}

a.ts:hover {
	text-decoration: underline;
	color: red
}

a.ts:visited {
	text-decoration: none;
	color: #000000
}

a:link {
	text-decoration: none;
	color: #000000
}

a:active {
	text-decoration: blink
}

a:hover {
	text-decoration: underline;
	color: red
}

a:visited {
	text-decoration: none;
	color: #000000
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
		<%@ include file="/entity/bzz-students/flash_image_precede.jsp"%>
		<%@ include file="/entity/bzz-students/flash_image.jsp"%>
		<%
			String photo = ((PeBzzStudent) request.getAttribute("peBzzStudent"))
					.getPhoto();
			boolean isPhotoed = true;
			String photoMessage = "";
			if (null == photo || "".equals(photo)
					|| "null".equalsIgnoreCase(photo)) {
				photoMessage = "您还没有上传照片，请在&ldquo;<a href=\"/entity/workspaceStudent/bzzstudent_StudentInfo.action\" class=\"ts\" style=\"color: blue;\">个人信息</a>&rdquo;功能中上传照片。";
				isPhotoed = false;
			} else {
				if (!photo.endsWith(".jpg")) {
					photoMessage = "&ldquo;您还没有上传照片，请在<a href=\"/entity/workspaceStudent/bzzstudent_StudentInfo.action\" class=\"ts\" style=\"color: blue;\">个人信息</a>&rdquo;功能中上传照片。";
					isPhotoed = false;
				}//else{
				//	if(!"4028809c2d925bcf011d66fd0dda7006".equals(isPhotoConfirm)){	//照片未通确认
				//		photoMessage = "<font color='red'><br/>您已上传照片，但还未确认。<a href='/entity/workspaceStudent/bzzstudent_StudentInfo.action'>查看</a></font>";
				//	}
				//}
			}

			String emailMessage = (String) request.getAttribute("emailMessage");
			String emailMessageNote = "";
			if ("1".equals(emailMessage)) {
				emailMessageNote = "<br/>&nbsp;&nbsp;&nbsp;&nbsp;您的邮箱信息存在重复，为避免您找回密码受到影响请在&ldquo;<a href=\"/entity/workspaceStudent/bzzstudent_StudentInfo.action\" class=\"ts\" style=\"color: blue;\">个人信息</a>&rdquo;功能中修改您的邮箱地址。";
			} else if ("2".equals(emailMessage)) {
				emailMessageNote = "<br/>&nbsp;&nbsp;&nbsp;&nbsp;您的邮箱信息不完善，为方便您找回密码请在&ldquo;<a href=\"/entity/workspaceStudent/bzzstudent_StudentInfo.action\" class=\"ts\" style=\"color: blue;\">个人信息</a>&rdquo;功能中完善您的邮箱地址。";
			}

			if (session.getAttribute("student_alert_complete") == null) {
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



		<div id="tishi"
			style="position: absolute; width: 450px; background-color: silver; left: 36%; top: 40%">
			<table width="100%">
				<tr>
					<td style="background-color: #8BC1ED; color: white;">
						<table border="0" width="100%">
							<tr>
								<td width="95%">
									提示信息
								</td>
								<td>
									<a href="#"
										style="background-color: transparent; color: white;"
										onclick="return hiddenTS(0);">X</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="background-color: window; color: windowtext;"
						align="center">
						<table width="95%">
							<tr>
								<td width="100%" align="left">
									<%
										String name = ((PeBzzStudent) request
													.getAttribute("peBzzStudent")).getTrueName();

											String days = request.getParameter("days");

											Calendar c = Calendar.getInstance();

											c.set(Calendar.MINUTE, 0);
											c.set(Calendar.SECOND, 0);
											c.set(Calendar.MILLISECOND, 0);

											c.set(Calendar.YEAR, 2010);
											c.set(Calendar.MONTH, 11); //12月
											c.set(Calendar.DAY_OF_MONTH, 18);
											long l1 = c.getTimeInMillis();

											days = ((l1 - new Date().getTime()) / (1000 * 60 * 60 * 24))
													+ "";

											double mtime = (Double) session.getAttribute("mtime");
											double ttime = (Double) session.getAttribute("ttime");
											double pct = mtime / ttime;

											DecimalFormat df = new DecimalFormat("###.##");
											String mtStr = df.format(mtime);
											String ttStr = df.format(ttime);
									%>
									<p>
										<span
											style="font-size: 12pt; color: black; line-height: 150%; font-family: 'Courier New'"><%=name%></span><span
											style="font-size: 12pt; color: black; line-height: 150%; font-family: 宋体">：</span>
									</p>
									<s:if test="1==1">
										<div
											style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%">
											<span
												style="font-size: 12pt; color: black; line-height: 150%; font-family: 宋体">
												您好! 欢迎您进入中国证劵业协会远程培训系统 </span>
										</div>
									</s:if>
									<s:elseif test="peBzzExamScore!=null && exam_flag==0">
										<div
											style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%">
											<span
												style="font-size: 12pt; color: black; line-height: 150%; font-family: 宋体">
												您好！考试定于2011年12月3日举行。 <!--  您已报名参加考试，但<font color="red">尚未通过审核</font>。
   请您仔细检查一下<a href="/entity/workspaceStudent/bzzstudent_toJiChuCourses.action"><font color="blue">【基础课程】</font></a>的学习进度和基础课程的<a href="/entity/bzz-students/student_course_status2.jsp"><font color="blue">【作业自测】</font></a>完成情况，
   务必在报名考试截止前达到考试要求，以保证能够通过审核，顺利参加考试。<font color="red">自测成绩将计入总成绩</font>，
   请务必认真对待。祝您学有所成！ 
   --> </span>
										</div>
									</s:elseif>
									<s:elseif test="peBzzExamScore!=null && exam_flag==1">
										<div
											style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%">
											<span
												style="font-size: 12pt; color: black; line-height: 150%; font-family: 宋体">
												您好！考试定于2011年12月3日举行。 您已通过考试审核，请认真复习，保证能够顺利完成考试。<font
												color="red">自测成绩将计入总成绩</font>， 请务必认真对待。祝您学有所成！&nbsp; </span>
										</div>
									</s:elseif>
									<s:else>
										<div
											style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%">
											<%
												if (pct < 0.5) {
											%>
											<span
												style="font-size: 12pt; color: black; line-height: 150%; font-family: 宋体">您好！考试定于2011年12月3日举行。</span><b><span
												style="font-size: 12pt; color: black; line-height: 150%; font-family: 'Courier New'"></span>
											</b><span
												style="font-size: 12pt; color: black; line-height: 150%; font-family: 宋体">您已完成</span><b><span
												style="font-size: 12pt; color: black; line-height: 150%; font-family: 'Courier New'"><%=mtStr%></span>
											</b><span
												style="font-size: 12pt; color: black; line-height: 150%; font-family: 宋体">学时的课程学习，还有</span><b><span
												style="font-size: 12pt; color: black; line-height: 150%; font-family: 'Courier New'"><%=df.format(ttime - mtime)%></span>
											</b><span
												style="font-size: 12pt; color: black; line-height: 150%; font-family: 宋体">学时的课程没有学习。请您加快学习进度，按计划完成<a
												href="/entity/workspaceStudent/bzzstudent_toJiChuCourses.action"
												class="ts" style="color: blue;">【必修课程学习】</a>、<a
												href="/entity/workspaceStudent/bzzstudent_toZiceCourses.action"
												class="ts" style="color: blue;">【作业自测】</a>和<a
												href="/entity/workspaceStudent/bzzstudent_toPingguCourses.action"
												class="ts" style="color: blue;">【课程评估】</a>，保证能够顺利参加考试。</span>
											<%
												} else if (pct >= 0.5 && pct < 1) {
											%>
											<span
												style="font-size: 12pt; color: black; line-height: 150%; font-family: 宋体">您好！考试定于2011年12月3日举行。</span><b><span
												style="font-size: 12pt; color: black; line-height: 150%; font-family: 'Courier New'"></span>
											</b><span
												style="font-size: 12pt; color: black; line-height: 150%; font-family: 宋体">您已完成</span><b><span
												style="font-size: 12pt; color: black; line-height: 150%; font-family: 'Courier New'"><%=mtStr%></span>
											</b><span
												style="font-size: 12pt; color: black; line-height: 150%; font-family: 宋体">学时的课程学习，还有</span><b><span
												style="font-size: 12pt; color: black; line-height: 150%; font-family: 'Courier New'"><%=df.format(ttime - mtime)%></span>
											</b><span
												style="font-size: 12pt; color: black; line-height: 150%; font-family: 宋体">学时的课程没有学习。请您继续按计划完成<a
												href="/entity/workspaceStudent/bzzstudent_toJiChuCourses.action"
												class="ts" style="color: blue;">【必修课程学习】</a>、<a
												href="/entity/workspaceStudent/bzzstudent_toZiceCourses.action"
												class="ts" style="color: blue;">【作业自测】</a>和<a
												href="/entity/workspaceStudent/bzzstudent_toPingguCourses.action"
												class="ts" style="color: blue;">【课程评估】</a>，保证能够顺利参加考试。为了更好地提高发展自己，在学有余力的情况下，您可以有选择地进行<a
												href="/entity/workspaceStudent/bzzstudent_toTiShengCourses.action"
												class="ts" style="color: blue;">【提升课程】</a>的学习。祝您学有所成！&nbsp;</span>
											<%
												} else if (pct == 1) {
											%>
											<span
												style="font-size: 12pt; color: black; line-height: 150%; font-family: 宋体">您好！考试定于2011年12月3日举行。</span><b><span
												style="font-size: 12pt; color: black; line-height: 150%; font-family: 'Courier New'"></span>
											</b><span
												style="font-size: 12pt; color: black; line-height: 150%; font-family: 宋体">您已完成基础课程的学习，请您检查一下<a
												href="/entity/workspaceStudent/bzzstudent_toJiChuCourses.action"
												class="ts" style="color: blue;">【必修课程】</a>的<a
												href="/entity/workspaceStudent/bzzstudent_toZiceCourses.action"
												class="ts" style="color: blue;">【作业自测】</a>和<a
												href="/entity/workspaceStudent/bzzstudent_toPingguCourses.action"
												class="ts" style="color: blue;">【课程评估】</a>是否完成，保证能够顺利参加考试。为了更好地提高发展自己，您可以有选择地进行<a
												href="/entity/workspaceStudent/bzzstudent_toTiShengCourses.action"
												class="ts" style="color: blue;">【提升课程】</a>的学习。祝您学有所成！</span>
											<%
												}
											%>
										</div>
									</s:else>
									<!--<s:if test='%{peBzzStudent.photo == null || peBzzStudent.photo.equals("")}'>
    	<div style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%"><span style="font-size: 12pt;  color: black; line-height: 150%; font-family: 宋体">此外，您还没有上传照片，请在修改<a href="/entity/workspaceStudent/bzzstudent_StudentInfo.action" class="ts" style="color: blue;">&ldquo;个人信息&rdquo;</a>功能中上传照片。</span></div>
    </s:if>-->
									<div
										style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%">
										<span
											style="font-size: 12pt; color: black; line-height: 150%; font-family: 宋体">祝您
											身体健康！工作顺利！<!--  <%=photoMessage%><%=emailMessageNote%>-->
										</span>
									</div>

									<br />
									<center>
										<a href="#" onclick="return hiddenTS(0);">【关闭】</a>
									</center>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<%
			} else {
				if (!isPhotoed) {
		%>

		<div id="tishi"
			style="position: absolute; width: 450px; background-color: silver; left: 36%; top: 40%">
			<table width="100%">
				<tr>
					<td style="background-color: #8BC1ED; color: white;">
						<table border="0" width="100%">
							<tr>
								<td width="95%">
									提示信息
								</td>
								<td>
									<a href="#"
										style="background-color: transparent; color: white;"
										onclick="return hiddenTS(0);">X</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="background-color: window; color: windowtext;"
						align="center">
						<table width="95%">
							<tr>
								<td width="100%" align="left">
									<div
										style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%">
										<span
											style="font-size: 12pt; color: black; line-height: 150%; font-family: 宋体"><%=photoMessage%><%=emailMessageNote%></span>
									</div>
									<div
										style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%">
										<span
											style="font-size: 12pt; color: black; line-height: 150%; font-family: 宋体">祝您
											身体健康！工作顺利！</span>
									</div>

									<center>
										<a href="#" onclick="return hiddenTS(0);">【关闭】</a>
									</center>
								</td>
							</tr>
						</table>
					</td>
				</tr>
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
		<table width="1002" border="0" align="center" cellpadding="0"
			cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td>
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr><td>
						<IFRAME NAME="top" width=100% height=150 frameborder=0
							marginwidth=0 marginheight=0
							SRC="/entity/bzz-students/pub/top.jsp" scrolling=no
							allowTransparency="true"></IFRAME>
							</td></tr>
					</table>
					<table width="1002" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="227" valign="top" height="850">
								<iframe name="leftA" width=237 height=850 frameborder=0
									marginwidth=0 marginheight=0
									src="/entity/bzz-students/pub/left.jsp?d=<%=d%>" scrolling=no
									allowtransparency="true"></iframe>
							</td>
							<td width="" valign="top">
								<iframe name="leftA" width="100%" height=850 frameborder=0
									marginwidth=0 marginheight=0
									src="/entity/bzz-students/welcome.jsp" scrolling=no
									allowtransparency="true"></iframe>
							</td>
						</tr>
					</table>
					<table>
						<tr>
							<td width="209" valign="top">
								<IFRAME NAME="top" width=1002 height=73 frameborder=0
									marginwidth=0 marginheight=0
									SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no
									allowTransparency="true" align=center style=""></IFRAME>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>