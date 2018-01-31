<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.net.*" pageEncoding="UTF-8"%>
<%
	response.addHeader("Cache-Control", "no-store, must-revalidate");
	response.addHeader("Expires", "Thu, 01 Jan 1970 00:00:01 GMT");
%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.user.*,com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="com.whaty.platform.interaction.forum.*,com.whaty.platform.courseware.basic.*,com.whaty.platform.courseware.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.user.*,com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.util.Cookie.*"%>
<%@page import="com.whaty.platform.sso.web.action.InteractionAction"%>
<%@page import="com.whaty.platform.entity.service.regStudent.PeBzzRegService" %>
<%@ include file="priv.jsp"%>
<%@ include file="../../student/pub/priv_user.jsp"%>
<%!//判断字符串为空的话，赋值为""
	String fixnull(String str) {
		if (str == null || str.equals("null") || str.equals(""))
			str = "";
		return str;
	}%>
<%
	String courseName = openCourse.getBzzCourse().getName();
	String voteId = openCourse.getBzzCourse().getSatisfactionId();
	String path = request.getContextPath();
	//String seconds = studentOperationManage.getElectiveTimes(courseId, user.getId());

	String first = (String) session.getAttribute("First");
	String firstPage = fixnull((String) session.getAttribute("firstPage"));
	session.setAttribute("First", "0");
	String opencourseId = (String) session.getAttribute("opencourseId"); //学生下进入学习使用，请不要与openCourse混淆
	String userId = us.getSsoUser().getId();
	
	PeBzzRegService peBzzRegService = new PeBzzRegService();
%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>中国证券业协会-网络课程</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
		<link href="css/admincss.css" rel="stylesheet" type="text/css">
		<style type="text/css">
#kjxi {
	font-size: 13px;
	color: #922c09;
	text-decoration: none;
	font-weight: bold;
}
</style>
		<script>
var windows = {};//定义存储课件窗口的对象

var abcStatus=1;
function abc()
{
	if(document.getElementById("abc").width=="11")
	{
		document.getElementById("abc").width="12";
		abcStatus=1;
	}
	else
	{
		document.getElementById("abc").width="11";
		abcStatus=0;
	}
}
function aa(id)
{
	if(document.getElementById(id))
	document.getElementById(id).style.display = (document.getElementById(id).style.display=="none")?"block":"none";
}
</script>
		<script type="text/javascript">
var times = 0;
function ForceWindow ()
{
  this.r = document.documentElement;
  this.f = document.createElement("FORM");
  this.f.target = "_blank";
  this.f.method = "post";
  this.r.insertBefore(this.f, this.r.childNodes[0]);
}

ForceWindow.prototype.open = function (sUrl)
{
  this.f.action = sUrl;
  this.f.submit();
}

<%--var oPopup = window.createPopup(); 
function showMenu() 
{
	var oPopBody = oPopup.document.body; 
	oPopBody.style.backgroundColor = "lightyellow"; 
	oPopBody.style.border = "solid black 1px"; 
	str="<iframe src='computetime.jsp?times=10'>"; 
	str+="</iframe>"; 
	oPopBody.innerHTML=str; 
	oPopup.show(50, 50, 180, 50, document.body); 
}
//showMenu() ;
function setTime(time)
{
	times = parseInt(time);
}
function window.onunload(){
      var myWindow = new ForceWindow(); 
	  window.navigate("computetime.jsp?times="+times);
}
--%>
</script>
		<script language="JavaScript" src="js/cookie.js"></script>
		<script>
/*var first = parseInt(<%=first%>);
if(first==1)
	setCookie("TotalTime","0");
//var totalTime = parseInt(getCookie("TotalTime"));
var totalTime = 0;
var totalTimes = ;
setInterval("timer()",1000);
function n2(n)
{
 if(n < 10)return "0" + n.toString();
 return n.toString();
}

function timer()
{
	totalTime +=1;
	var n = totalTime;
	document.getElementById("second").innerHTML = n2(n % 60);
	n = (n - n % 60) / 60;
	document.getElementById("minute").innerHTML = n2(n % 60);
	n = (n - n % 60) / 60;
	document.getElementById("hour").innerHTML = n2(n);
}
function timers()
{
	document.getElementById("timers").style.display = "block";
	var n = totalTimes;
	document.getElementById("seconds").innerHTML = n2(n % 60);
	n = (n - n % 60) / 60;
	document.getElementById("minutes").innerHTML = n2(n % 60);
	n = (n - n % 60) / 60;
	document.getElementById("hours").innerHTML = n2(n);
}

function window.onbeforeunload(){
//	setCookie("TotalTime",totalTime);
//	top.window.location.reload();  //9.20
//	top.setTime(totalTime);   //9.20
    window.location.reload(); 
	setTime(totalTime);
}
*/
function openware(){
	var xx= window.document.getElementById("combo-box-link").value;
	if(xx.length<=0){
		alert("请选择课件！");
	}else{
		window.open(xx);
	}
}
window.force = new ForceWindow();

function toManyi(){
	//if(confirm('您已经学习完了课程，可以对课程满意度进行投票，是否投票？')){
		if(confirm('您已经学习完了课程，请对课程满意度进行投票!')){
			var toUrl ='/entity/first/firstPeVotePaper_toVote.action?bean.id=<%=voteId%>&togo=1&opencourseId=<%=opencourseId%>';
			window.open(toUrl);
		}
		//window.location.reload();
	//}else{
	//	alert('您选择本次不进行投票，可以正常浏览，下次进入本课程会再次提醒您\n\n注：需要考试的课程在未完成满意度调查的情况下无法进行考试！');
	//}
}

function toTest(){
	if(confirm('您已经完成本课程的课件学习，现在可以进行本课程的课后测验了，\n是否现在进入？')){
		//alert('您现在可以进行本课程的考试了。');
		var toUrl = '/entity/function/reg/onlinetestcourse_list.jsp?opencourseId=<%=opencourseId%>';
		//window.force.open(toUrl);
		document.getElementById('mainer').src=toUrl;
		//window.location=toUrl;
	}else{
		alert('您选择本次不进行课后测验，下次进入本课程时请完成课后测验！');
	}
}

function openCourseWare(){
	/*if(windows['courseWin']){
		alert('课件播放窗口已经存在或未正常关闭，请先关闭或刷新课程工作室页面.');
		return false;
	}*/
	windows['courseWin'] = window.open('<%=path%>/reg_training/student/course/jumpCourseware.jsp?courseId=<%=courseId%>&opencourseId=<%=opencourseId%>', 'courseWin', 'height=680,width=1050,top=0,left=50,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
}

</script>
	</head>
	<body scroll="no" onload="<%-- timers() --%>">
		<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="93" valign="top">
					<table width="100%" height="93" border="0" style="background-repeat: repeat-x;" background="images/top_bg.png" align="center" cellpadding="0" cellspacing="0" bgcolor="#4B8CBB">
						<tr>
							<td width="342" align="left"> 
								<img src="images/logo2.png" />
							</td>
							<td style="padding-bottom: 12px; background-position: right; background-repeat: no-repeat; padding-right: 12px" align="right" valign="bottom" background="images/topbg_2.jpg">
								<a href="#"><img src="images/logout.jpg" width="62" height="21" border="0" onclick="if(confirm('您确定要退出工作室吗？')){parent.window.close();}"> </a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td valign="top">
					<table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td width="211" height="100%" valign="top" background="images/leftbg2.jpg-" bgcolor="#4B8CBB" style="padding-left: 4px; background-repeat: repeat-y" id="bj">
								<table width="208" height="42" border="0" cellpadding="0" cellspacing="0" bgcolor="#E2EEF3">
									<tr>
										<td>
											<table style="margin-top: 5px;" width="208" height="26" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td width="80" valign="top" bgcolor="#4B8CBB" class="bai">
														<p>
															当前课程：
															<br/>
														</p>
													</td>
													<td width="128" bgcolor="#4B8CBB" class="bai"><%=courseName%></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table style="margin-top: 15px; margin-left: 2px" width="205" border="0" cellpadding="0" cellspacing="0" bgcolor="#e2eef3">
											<%
												//课程不需要考试
												String exam_y_n = "false";
												if("1".equalsIgnoreCase(openCourse.getBzzCourse().getEnumConstByFlagIsExam().getCode()))//课程需要考试
													exam_y_n = "true";
												//未填写满意度不能进行考试
												String exam_flag = "false";
											 %>
												<tr>
													<td width="138" height="45" align="center" valign="top" background="images/menu1_1bg.jpg" style="background-repeat: no-repeat; padding-top: 13px; padding-left: 33px; background-position: center">
														<a href="/entity/function/reg/course_state.jsp?courseId=<%=courseId%>&exam_y_n=<%=exam_y_n %>&courseCode=<%=openCourse.getBzzCourse().getCode() %>&opencourseId=<%=opencourseId %>" target="mainer" class="menuzi">课程状态</a>
													</td>
												</tr>
												<tr>
													<td width="138" height="45" align="center" valign="top" background="images/menu1_1bg.jpg" style="background-repeat: no-repeat; padding-top: 13px; padding-left: 33px; background-position: center">
														<a href="/entity/function/coursenote_list.jsp?open_course_id=<%=teachclass_id%>" target="mainer" class="menuzi">课程简介</a>
													</td>
												</tr>
												<tr>
													<td width="138" height="45" align="center" valign="top" background="images/menu8_1bg.jpg" style="background-repeat: no-repeat; padding-top: 13px; padding-left: 33px; background-position: center">
														<a style="cursor: pointer;" onclick="openCourseWare();" class="menuzi"><span id="kjxi">课件学习</span> </a>
													</td>
												</tr>
												<tr>
													<td width="138" height="45" align="center" valign="top" background="images/menu3_1bg.jpg" style="background-repeat: no-repeat; padding-top: 13px; padding-left: 33px; background-position: center">
														<a href="/sso/peResourceInteraction_listpeResource.action?teachclassId=<%=courseId%>&coursename=<%=courseName%>" target="mainer" class="menuzi">参考资料</a>
													</td>
												</tr>
												<%
													//已经学完并且课程有满意度ID
													if (peBzzRegService.isLearningCompleted(opencourseId, userId) && voteId != null && !"".equals(voteId)) {
														//是否存在成绩，如果成绩为空则显示满意度，如果不为空则可直接进入考试
														if (peBzzRegService.isShowManyi(userId, openCourse.getId())) {
															//课程需要考试
															if (openCourse.getBzzCourse().getEnumConstByFlagIsExam() != null && "1".equalsIgnoreCase(openCourse.getBzzCourse().getEnumConstByFlagIsExam().getCode())) {
																//InteractionFactory interactionFactory = InteractionFactory.getInstance();
																//InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
																//TestManage testManage = interactionManage.creatTestManage();
																Map scoreM = peBzzRegService.getOnlineTestScore(opencourseId, userId);
																
																double score = (Double) scoreM.get("score");
																double passScore = 80.0;
																try {
																	passScore = Double.parseDouble(openCourse.getBzzCourse().getPassRole());
																} catch (Exception e) {
																}
												%>
												<tr>
													<td width="138" height="45" align="center" valign="top" background="images/menu1_1bg.jpg" style="background-repeat: no-repeat; padding-top: 13px; padding-left: 33px; background-position: center">
														<a href="/entity/function/reg/manyidu_list.jsp?opencourseId=<%=opencourseId%>" target="mainer" class="menuzi">满意度调查</a>
													</td>
												</tr>
												<%
													//不及格或未考试直接弹出继续考试提示
													if (score < passScore) {
												%>
												<script>window.onload=function (){toTest();}</script>
												<%
													}
															}
														} else {
															//已学完但是没有填写满意度
															//InteractionFactory interactionFactory = InteractionFactory.getInstance();
															//InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
															//TestManage testManage = interactionManage.creatTestManage();
															Map scoreM = peBzzRegService.getOnlineTestScore(opencourseId, userId);
															double score = (Double) scoreM.get("score");
															double passScore = 80.0;
															try {
																passScore = Double.parseDouble(openCourse.getBzzCourse().getPassRole());
															} catch (Exception e) {
															}
															//没有填写过满意度
															if (!peBzzRegService.isManyiCompleted(openCourse.getBzzCourse().getId(), userId)) {
												%>
												<tr>
													<td width="138" height="45" align="center" valign="top" background="images/menu1_1bg.jpg" style="background-repeat: no-repeat; padding-top: 13px; padding-left: 33px; background-position: center">
														<a href="/entity/function/reg/manyidu_list.jsp?opencourseId=<%=opencourseId%>" target="mainer" class="menuzi">满意度调查</a>
													</td>
												</tr>
												<script>window.onload=function (){toManyi();}</script>
												<%
													//填写过满意度并且课程需要考试
													} else if (peBzzRegService.isManyiCompleted(openCourse.getBzzCourse().getId(), userId) && openCourse.getBzzCourse().getEnumConstByFlagIsExam() != null
																	&& "1".equalsIgnoreCase(openCourse.getBzzCourse().getEnumConstByFlagIsExam().getCode())) {
												%>
												<tr>
													<td width="138" height="45" align="center" valign="top" background="images/menu1_1bg.jpg" style="background-repeat: no-repeat; padding-top: 13px; padding-left: 33px; background-position: center">
														<a href="/entity/function/reg/manyidu_list.jsp?opencourseId=<%=opencourseId%>" target="mainer" class="menuzi">满意度调查</a>
													</td>
												</tr>
												<%
													//不及格或未考试直接弹出继续考试提示
													if (score < passScore) {
													exam_flag = "true";
												%>
												<script>window.onload=function (){toTest();}</script>
												<%
													}
															}else{
															//学完但是课程不需要考试
															%>
													<tr>
														<td width="138" height="45" align="center" valign="top" background="images/menu1_1bg.jpg" style="background-repeat: no-repeat; padding-top: 13px; padding-left: 33px; background-position: center">
															<a href="/entity/function/reg/manyidu_list.jsp?opencourseId=<%=opencourseId%>&flag=false" target="mainer" class="menuzi">满意度调查</a>
														</td>
													</tr><%
															}
														}
													} else {
													//没学完或者满意度ID丢失
												%>
												<tr>
													<td width="138" height="45" align="center" valign="top" background="images/menu1_1bg.jpg" style="background-repeat: no-repeat; padding-top: 13px; padding-left: 33px; background-position: center">
														<a href="/entity/function/reg/manyidu_list.jsp?opencourseId=<%=opencourseId%>&flag=false" target="mainer" class="menuzi">满意度调查</a>
													</td>
												</tr>
												<%
													}
													//需要考试
													if("true".equalsIgnoreCase(exam_y_n)){
													%>
													<tr>
														<td width="138" height="45" align="center" valign="top" background="images/menu1_1bg.jpg" style="background-repeat: no-repeat; padding-top: 13px; padding-left: 33px; background-position: center">
															<a href="/entity/function/reg/onlinetestcourse_list.jsp?opencourseId=<%=opencourseId%>&flag=<%=exam_flag %>" target="mainer" class="menuzi">课后测验</a>
														</td>
													</tr>
													<%
													}
												 %>
												 <%
									              	String flag = (String)request.getSession().getAttribute("flag");
									              	if(flag.equals("true")) {
									             %>
												<tr>
													<td width="138" height="45" align="center" valign="top" background="images/menu1_1bg.jpg" style="background-repeat: no-repeat; padding-top: 13px; padding-left: 33px; background-position: center">
														<a href="/entity/function/ass_answer/index.jsp" target="mainer" class="menuzi">课程答疑</a>
													</td>
												</tr>
												<%
									              	} 
									           %> 
											</table>
										</td>
									</tr>
								</table>
								<table width="209" height="8" border="0" cellpadding="0" cellspacing="0" background="images/leftcenterbg.jpg">
									<tr>
										<td></td>
									</tr>
								</table>
							</td>
							<td width="12" align="left" background="images/leftbg4.jpg" style="background-repeat: repeat-y; background-position: right" id="abc">
								<img id="button" alt="单击收缩/展开右侧功能区" src="images/jiaotou.jpg" width="9" height="79" style="margin: 0px 3px 0px 0px; cursor: pointer" onClick="abc();if(abcStatus==0) this.src='images/jiaotou1.jpg';else this.src='images/jiaotou.jpg';aa('bj');">
							</td>
							<td height="100%" align="center" valign="top"><iframe src="<%if(firstPage!=null&&(!firstPage.equals(""))&&(!firstPage.equals("null"))){out.print(firstPage);}else{%>/entity/function/reg/course_state.jsp?courseId=<%=courseId%>&exam_y_n=<%=exam_y_n %>&courseCode=<%=openCourse.getBzzCourse().getCode() %>&opencourseId=<%=opencourseId %><%}%>" width="100%" height="100%" frameborder="0" id="mainer" name="mainer" align="top" scrolling="yes" style="margin: 0px 8px 0px 0px;"></iframe>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="47">
					<table width="100%" height="47" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td height="5" bgcolor="#4B8CBB"></td>
						</tr>
						<tr>
							<td height="42" align="center" background="images/btbg_2.jpg" class="baizi">
								版权所有：中国证劵业协会
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>