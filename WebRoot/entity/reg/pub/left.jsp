<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<jsp:directive.page import="com.whaty.platform.sso.web.servlet.UserSession" />
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool" />
<jsp:directive.page import="com.whaty.platform.database.oracle.MyResultSet" />
<%!//判断字符串为空的话，赋值为""
	String fixnull(String str) {
		if (str == null || str.equals("null") || str.equals(""))
			str = "";
		return str;
	}%>
<%
	String d = request.getParameter("d");
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	String name = "";
	String fk_batch_id = "";

	UserSession userSession = (UserSession) session.getAttribute("user_session");
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
	name = userSession.getUserName();
	if(null == name || "".equals(name)){
		dbpool db = new dbpool();
		String sql = "select true_name,fk_batch_id from pe_bzz_student where reg_no='" + reg_no + "'";
		MyResultSet rs = null;
		rs = db.executeQuery(sql);
		while (rs != null && rs.next()) {
			name = fixnull(rs.getString("true_name"));
		}
		db.close(rs);
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="../css.css" rel="stylesheet" type="text/css" />
		<link href="../css/style.css" rel="stylesheet" type="text/css" />

		<script src="../css/jquery.js" type="text/javascript"></script>
		<link href="../css/reset.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
.menuBtn {
	position: absolute;
	left: 150px;
	top: 0px;
}
</style>
		<script type="text/javascript">
//等待dom元素加载完毕.
$(document).ready(function () {
		var aMenuOneLi = $(".menu-one > li");
		var aMenuTwo = $(".menu-two");
		
			$(".menu-one > li > .header").click(function () {
				if ($(this).next().css("display") == "block") {
					$(this).next().slideUp(300);
					$(this).parent().addClass("menu-show")
				} else {
					$(this).next().slideDown(300);
					$(this).parent().removeClass("menu-show")
				}
			});
		
	});
</script>
		<script type="text/javascript">
		function changeIcon(op){
			var imgSrc = document.getElementById(op).src;
			if(imgSrc.indexOf("up.png") == -1){
				imgSrc = imgSrc.replace("down.png","up.png");
			}else{
				imgSrc = imgSrc.replace("up.png","down.png");
			}
			document.getElementById(op).src = imgSrc;
		}
		function logout(){
	
		/*	var url="/sso/login_close.action?loginErrMessage=clear";
			if (window.XMLHttpRequest) {
		        req = new XMLHttpRequest();
		    }
		    else if (window.ActiveXObject) {
		        req = new ActiveXObject("Microsoft.XMLHTTP");
		    }
		    req.open("Get",url,true);
		    req.onreadystatechange = function(){
		    	if(req.readyState == 4){
			    	window.top.navigate("/");
		    	}
		    };
	  		req.send(null); */
	  		// window.top.navigate("/sso/login_close.action?loginErrMessage=clear");	
	  		window.top.location.href="/sso/login_close.action?loginErrMessage=clear";
		}
		function returnIndex(){	
	  		window.open("/");
		}	
</script>
	</head>
	<body>
		<div class="left" width="237">
			<div class="status" align="left" style="border-radius: 5px; margin-bottom: 1px; text-align: center; margin-left: 0px;margin-top:-10px;">
				<font size="3">欢迎<span><%=name%></span></font>
				<span style="cursor: pointer" onclick="if(confirm('确定退出平台？')) logout()"><font style="color: #666; font-size: 12px; font-weight: normal">【退出】</font></span>
			</div>
			<div class="sideBar content">
				<ul class="menu-one">
					<li>
						<div class="header1">
							<div class="sideBar_title" style="background: url(../images/index/index_14.gif) no-repeat center; cursor: pointer" onclick="window.parent.location.reload();">
								<span style="padding:0 0 0 14px;">个人主页</span>
							</div>
						</div>
					</li>
					<li>
						<div class="header1">
							<div class="sideBar_title" style="background: url(../images/index/index_03.gif) no-repeat center; cursor: pointer" onClick="top.openPage('/entity/workspaceRegStudent/regStudentWorkspace_getFirstinfo.action')">
								<span style="padding:0 0 0 14px;">公告列表</span>
							</div>
						</div>
					</li>
					<li>
						<div class="header">
							<div class="sideBar_title" style="position: relative; background: url(../images/index/index_01.gif) no-repeat center;cursor:pointer" onclick="changeIcon('zhgl')">
								<span style="padding:0 0 0 14px;">账户管理</span>
								<div class="menuBtn">
									<img id="zhgl" src="../images/index/up.png" style="padding: 8px 2px 0 0;" />
								</div>
							</div>
						</div>
						<ul class="menu-two list">
							<li style="background: url(../images/index_35.png) no-repeat center; background-color:#cae4ff;">
								<a style="padding:0 0 0 14px;" href="###" onClick="top.openPage('/entity/workspaceRegStudent/regStudentWorkspace_StudentInfo.action')">账户基本信息</a>
							</li>
						</ul>
					</li>
					<li>
						<div class="header">
							<div class="sideBar_title" style="position: relative; background: url(../images/index/index_02.gif) no-repeat center;cursor:pointer" onclick="changeIcon('hdbz')">
								<span style="padding:0 0 0 14px;">获得帮助</span>
								<div class="menuBtn">
									<img id="hdbz" src="../images/index/up.png" style="padding: 8px 2px 0 0;" />
								</div>
							</div>
						</div>
						<ul class="list menu-two">
							<li style="background: url(../images/index_35.png) no-repeat center; background-color:#cae4ff;">
								<a style="padding:0 0 0 22px;" href="###" onClick="top.openPage('/entity/workspaceStudent/studentWorkspace_questionAdvice.action')">问题及建议</a>
							</li>
							<li style="background: url(../images/index_35.png) no-repeat center; background-color:#cae4ff;">
								<a style="padding:0 0 0 18px;" href="###" onClick="top.openPage('/entity/workspaceStudent/studentWorkspace_frequentlyQuestion.action')">常见问题库</a>
							</li>
							<!--  
							<li style="background: url(../images/index_35.png) no-repeat center; background-color: #cae4ff;">
								<a style="padding:0 0 0 22px;" href="###" onClick="top.openPage('/entity/workspaceRegStudent/regStudentWorkspace_toHelp.action')">获得帮助</a>
							</li>
							-->
							<li style="background: url(../images/index_35.png) no-repeat center; background-color: #cae4ff;">
								<a style="padding:0 0 0 22px;" href="/help/supdoc.pdf" target="_blank">使用手册</a>
							</li>
						</ul>
					</li>
					<li>
						<div class="header">
							<div class="sideBar_title" style="position: relative; background: url(../images/index/index_04.gif) no-repeat center;cursor:pointer" onclick="changeIcon('zlk')">
								<span style="padding:0 0 0 14px;">资料库</span><div class="menuBtn" style="float:right;align:right;"><img id="zlk" src="../images/index/up.png" style="padding:8px 2px 0 0;"/></div>
							</div>
						</div>
						<ul class="list menu-two">
							<li style="background: url(../images/index_35.png) no-repeat center; background-color:#cae4ff;">
								<a style="padding:0 0 0 22px;" href="###" onClick="top.openPage('/entity/workspaceStudent/studentWorkspace_toZiliao.action')">资料库</a>
							</li>
							<li style="background: url(../images/index_35.png) no-repeat center; background-color:#cae4ff;">
								<a style="padding:0 0 0 22px;" href="###" onClick="top.openPage('/entity/workspaceStudent/studentWorkspace_toUploadResource.action')">上传资料</a>
							</li>
							<li style="background: url(../images/index_35.png) no-repeat center; background-color:#cae4ff;">
								<a style="padding:0 0 0 22px;" href="###" onClick="top.openPage('/entity/workspaceStudent/studentWorkspace_myResource.action')">我的资料</a>
							</li>
						</ul>
					</li>
					<li>
						<div class="header">
							<div class="sideBar_title" style="position: relative; background: url(../images/index_19.jpg) no-repeat center; cursor: pointer" onclick="changeIcon('bmff')">
								<span style="padding:0 0 0 14px;">课程报名</span>
								<div class="menuBtn">
									<img id="bmff" src="../images/index/up.png" style="padding: 8px 2px 0 0;" />
								</div>
							</div>
						</div>
						<ul class="list meun-two">
							<li style="background: url(../images/index_35.png) no-repeat center; background-color: #cae4ff;">
								<a style="padding:0 0 0 14px;" href="###" onClick="top.openPage('/entity/workspaceRegStudent/regStudentWorkspace_toFreeCourses.action')">课程报名</a>
							</li>
							<li style="background: url(../images/index_35.png) no-repeat center; background-color: #cae4ff;">
								<a style="padding:0 0 0 14px;" href="###" onClick="top.openPage('/entity/workspaceStudent/studentWorkspace_toSacLive.action')">在线直播报名</a>
							</li>
						</ul>
					</li>
					<li>
						<div class="header">
							<div class="sideBar_title" style="position: relative; background: url(../images/index/index_06.gif) no-repeat center; cursor: pointer" onclick="changeIcon('kcxx')">
								<span style="padding:0 0 0 14px;">课程学习</span>
								<div class="menuBtn">
									<img id="kcxx" src="../images/index/up.png" style="padding: 8px 2px 0 0;" />
								</div>
							</div>
						</div>
						<ul class="list meun-two">
							<li style="background: url(../images/index_35.png) no-repeat center; background-color: #cae4ff;">
								<a style="padding:0 0 0 22px;" href="###" onClick="top.openPage('/entity/workspaceRegStudent/regStudentWorkspace_toLearningCourses.action')">课程学习</a>
							</li>
							<li style="background: url(../images/index_35.png) no-repeat center; background-color: #cae4ff;">
								<a style="padding:0 0 0 14px;" href="###" onClick="top.openPage('/entity/workspaceStudent/studentWorkspace_toSacLiveStudy.action')">直播课程学习</a>
							</li>
							<!-- Lee 2013年12月26日 已完成改为待考试 -->
							<!--  
							<li style="background: url(../images/index_39.png) no-repeat center; background-color:#cae4ff;">
							<a href="###" onClick="top.openPage('/entity/workspaceRegStudent/regStudentWorkspace_toCompletedCourses.action')">待考试课程</a>
							</li>
							<li style="background: url(../images/index_39.png) no-repeat center; background-color:#cae4ff;">
							<a href="###" onClick="top.openPage('/entity/workspaceRegStudent/regStudentWorkspace_toPassedCourses.action')">已通过学习课程</a>
							</li>
						-->
						</ul>
					</li>
					<li>
						<div class="header1">
							<div class="sideBar_title" style="background: url(../images/index_12.jpg) no-repeat center; cursor: pointer" onclick="top.openPage('/entity/workspaceStudent/studentWorkspace_firstOnlineExam.action');">
								<span style="padding:0 0 0 14px;">在线考试</span>
							</div>
						</div>
					</li>
					<li>
						<div class="header1">
							<div class="sideBar_title" style="background: url(../images/index/index_08.gif) no-repeat center; cursor: pointer" onClick="top.openPage('/entity/workspaceRegStudent/regStudentWorkspace_tositeemail.action')">
								<span style="padding:0 0 0 14px;">站内信箱</span>
							</div>
						</div>
					</li>
					<li>
						<div class="header1">
							<div class="sideBar_title" style="background: url(../images/index/index_10.gif) no-repeat center; cursor: pointer" onClick="top.openPage('/entity/workspaceStudent/studentWorkspace_getVoteList.action')">
								<span style="padding:0 0 0 14px;">调查问卷</span>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</body>
</html>
