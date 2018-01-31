<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.whaty.platform.entity.util.MyUtil"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<jsp:directive.page import="com.whaty.platform.sso.web.servlet.UserSession" />
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool" />
<jsp:directive.page import="com.whaty.platform.database.oracle.MyResultSet" />
<%!String fixnull(String str) {
		if (str == null || str.equals("null") || str.equals(""))
			str = "";
		return str;
	}%>
<%
	UserSession userSession = (UserSession) session.getAttribute("user_session");
	String courseId =(String)session.getAttribute("courseId");
	String user_id = userSession.getId();
	String num = "0";
	if(request.getAttribute("num") != null && !"null".equals(request.getAttribute("num"))){
		num = request.getAttribute("num").toString();
	}else{
		dbpool db = new dbpool();
		//原SQL
		//String sql = " select count(*) num " + " from pr_bzz_tch_stu_elective pbtse" + " inner join training_course_student tcs on pbtse.fk_training_id=tcs.id"
		//		+ " inner join pe_bzz_student ps on ps.id=pbtse.fk_stu_id" + " and ps.fk_sso_user_id	='" + user_id + "' and tcs.learn_status<>'COMPLETED'";
		String sql = "SELECT COUNT(*) NUM FROM PR_BZZ_TCH_STU_ELECTIVE A INNER JOIN PR_BZZ_TCH_OPENCOURSE B ON A.FK_TCH_OPENCOURSE_ID = B.ID AND A.ISPASS <> '1' INNER JOIN PE_BZZ_TCH_COURSE C ON B.FK_COURSE_ID = C.ID AND C.FLAG_COURSEAREA != 'Coursearea0' LEFT JOIN TRAINING_COURSE_STUDENT D ON A.FK_TRAINING_ID = D.ID AND D.LEARN_STATUS <> 'COMPLETED' INNER JOIN ENUM_CONST EC4 ON C.FLAG_ISFREE = EC4.ID INNER JOIN PE_BZZ_STUDENT PBS ON A.FK_STU_ID = PBS.ID AND PBS.FK_SSO_USER_ID = '" + user_id + "'";
		System.out.println(sql);
		MyResultSet rs = null;
		rs = db.executeQuery(sql);
		while (rs != null && rs.next()) {
			num = fixnull(rs.getString("num"));
		}
		db.close(rs);
		db=null;//dgh 914
		rs=null;//dgh 914
	}
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>中国证券协会远程培训系统</title>
		<script language="JavaScript" src="js/frame.js"></script>
		<script type="text/javascript" src="/entity/bzz-students/js/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="/entity/bzz-students/js/jquery-ui.js"></script>
		<script type="text/javascript">
		window.onload=function(){
		if(navigator.userAgent.indexOf("MSIE")>0) {
			
	    }  
		else{
			$("#xxx").html("<br/>敬告：<br/>您的浏览器不能很好的支持课程的学习和支付，为获得更好的使用体验，推荐您使用微软IE8或IE9浏览器<br/>");
			$("#tishi").effect('shake', { times:10 ,direction:"left"}, 500);  
		}
		}
		
	   window.onunload= function()   { 
		if(window.screenLeft>10000|| window.screenTop>10000){ 
			var url="/sso/login_close.action?loginErrMessage=clear";
			if (window.XMLHttpRequest) {
		        req = new XMLHttpRequest();
		    }
		    else if (window.ActiveXObject) {
		        req = new ActiveXObject("Microsoft.XMLHTTP");
		    }
		    req.open("Get",url,true);
		    req.onreadystatechange = function(){
		    	if(req.readyState == 4);
		    };
	  		req.send(null);
  		}
	}
	function openPage(url){
		document.getElementById("content").src = url;
	}
	
 function iFrameHeight(iframeId) { 
var ifm= document.getElementById(iframeId); 
var subWeb = document.frames ? document.frames[iframeId].document : ifm.contentDocument; 
if(ifm != null && subWeb != null) { 
ifm.height = subWeb.body.scrollHeight; 
} 
} 
	</script>
		<script type="text/javascript">
function hiddenTS(flag) {
	if(flag=='0'){
		document.getElementById("tishi").style.visibility="hidden";
		flag="1";
	}
	return false;
}
</script>
	</head>
	<body style="margin: 0; overflow: auto;">
		<input type="hidden" value="<%=userSession.getSsoUser().getPasswordBk() %>"/>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="">
			<tr>
				<td style="background-image: url(/web/bzz_index/images/top_pic1.jpg); background-position: bottom; background-repeat: repeat-x;">
					<table width="1002px" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<IFRAME NAME="top" width="100%" height=150 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/top.jsp" scrolling=no allowTransparency="true" style="margin: 0; overflow: auto;"></IFRAME>
							</td>
						</tr>
					</table>
				</td>
			<tr>
				<td height="100%">
					<table width="1002px" border="0" align="center" cellpadding="0" height="100%" cellspacing="0" style="padding-top: 20px;">
						<tr>
							<td width="200" id="" valign="top" style="padding-left: 20px;">
								<iframe name="left" id="lmn" width=200 height="100%" frameborder=0 marginwidth=0 marginheight=0 src="/entity/bzz-students/pub/left.jsp?x=<%=Math.random()%>" scrolling=no allowtransparency="true" onload="javascript:iFrameHeight('lmn')"></iframe>
							</td>
							<td width="" valign="top">
								<iframe name="content"  id="content" width="100%" height ="1200" frameborder=0 marginwidth=0 marginheight=0 src="/entity/workspaceStudent/studentWorkspace_welcome.action?x=<%=Math.random()%>" scrolling=no allowtransparency="true" ></iframe>
							</td>
						</tr>
					</table>
					<%if (courseId ==null || "".equals(courseId)){ %>
					<div id="tishi" style="position: absolute; width: 500px; background-color: #8BC1ED; left: 36%; top: 40%">
						<table style="display:none" width="100%" border="0" cellspacing="5">
							<style type="text/css">
								a img {border: none;}
							</style>
							<tr>
								<td width="100%" style="color: #FFF; padding-left: 10px;">
									提示信息
								</td>
								<td align="right">
									<a href="#" onclick="return hiddenTS(0);"><img src="/entity/bzz-students/images/index/index_close.jpg" /> </a>
								</td>
							</tr>
							<tr>
								<td style="background-color: window; color: windowtext;" align="center" colspan="2">
									<table width="95%">
										<tr>
											<td align="center" style="background-color: window; color: windowtext;">
												<div style="border-bottom: 1px solid #81AFFC; width: 180px; margin-left: 10px;">
													<font size="2">您现在共有</font><font size="3" color="red">&nbsp;<%=num%>&nbsp;</font><font size="2">门在学课程</font>
												</div>
											</td>
										</tr>
										<tr>
											<td width="100%" align="left">
												<font style="font-size: 14px;"><br/> <strong>各位学员：</strong> <br/> </font>
												<div style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%">
													<span style="font-size: 14px; color: black; line-height: 150%; font-family: 宋体">
													系统已于2015年12月18日进行了升级更新。本次主要更新内容：<br />&nbsp;&nbsp;&nbsp;&nbsp;1、系统第三方支付平台服务商将更换为快钱支付。<br />&nbsp;&nbsp;&nbsp;&nbsp;2、【公共课程报名】页面中增加报名前测试功能，学员可通过此功<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;能在报名前了解或评估对课程知识的掌握程度。<br />&nbsp;&nbsp;&nbsp;&nbsp;3、 增加课件播放时与系统的交互验证频次，进一步完善课程记录机<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;制。<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如在使用上述功能中有任何问题，可咨询远程培训服务热线<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;010-62415115。<br />
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中国证券业协会远程培训系统<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2015年12月21日
													</span>
												</div>
												<center>
													<a href="#" onclick="return hiddenTS(0);">【我已知晓】</a>
												</center>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
					<% } %>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="padding-top: 20px;">
						<tr>
							<td valign="top">
								<IFRAME NAME="bottom" width=100% height=73 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no allowTransparency="true" align=center style=""></IFRAME>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>


<script type="text/javascript" language="javascript">   
 
</script>
<%if(MyUtil.md5("sac123").equals(userSession.getSsoUser().getPasswordBk())) { %>
<script language="JavaScript">
	alert("您的登陆密码为初始密码，请您尽快修改!");
</script>
<%} %>
