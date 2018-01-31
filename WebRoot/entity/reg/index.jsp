<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
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
	
	String zhanneixinNum = (String) request.getAttribute("zhanneixinNum");
	String diaochawenjuanNum = (String) request.getAttribute("diaochawenjuanNum");
	String daikaoshikechengNum = (String) request.getAttribute("daikaoshikechengNum");
	String tongzhigonggaoNum = (String)request.getAttribute("tongzhigonggaoNum");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>中国证券协会远程培训系统</title>
		<script language="JavaScript" src="js/frame.js"></script>
		<script type="text/javascript" src="/entity/reg/js/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="/entity/reg/js/jquery-ui.js"></script>
		<script type="text/javascript">
		window.onload=function(){
			if(navigator.userAgent.indexOf("MSIE")>0) {
				
		    } else{
				$("#xxx").html("<br/>敬告：<br/>您的浏览器不能很好的支持课程的学习和支付，为获得更好的使用体验，推荐您使用微软IE8或IE9浏览器<br/>");
				$("#tishi").effect('shake', { times:10 ,direction:"left"}, 500);  
			}
			var check_info = '<%=request.getAttribute("check_info_comp")%>';
			if(check_info == 'false') {
				if(window.confirm("您的个人信息未填写完全，是否现在补全?")) {
					window.top.document.getElementById("content").contentWindow.location = "/entity/workspaceRegStudent/regStudentWorkspace_toModifyInfo.action";
					hiddenTS(0);
				}
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
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td style="background-image: url(/web/bzz_index/images/top_pic1.jpg); background-position: bottom; background-repeat: repeat-x;">
					<table width="1002px" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<IFRAME NAME="top" width="100%" height=150 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/reg/pub/top.jsp" scrolling=no allowTransparency="true" style="margin: 0; overflow: auto;"></IFRAME>
							</td>
						</tr>
						
					</table>
				</td>
				</tr>
			<tr>
				<td height="100%">
					<table width="1002px" border="0" align="center" cellpadding="0" height="100%" cellspacing="0" style="padding-top: 20px;">
						<tr>
							<td width="200" id="" valign="top" style="padding-left: 20px;">
								<iframe name="left" id="lmn" width=200 height="100%" frameborder=0 marginwidth=0 marginheight=0 src="/entity/reg/pub/left.jsp?x=<%=Math.random()%>" scrolling=no allowtransparency="true" onload="javascript:iFrameHeight('lmn')"></iframe>
							</td>
							<td width="" valign="top">
								<iframe name="content" id="content" width="100%" height="100%" frameborder=0 marginwidth=0 marginheight=0 src="/entity/workspaceRegStudent/regStudentWorkspace_toLearningCourses.action?x=<%=Math.random()%>" scrolling=no allowtransparency="true" onload="javascript:iFrameHeight('content')"></iframe>
							</td>
						</tr>
					</table>
					<div id="tishi" style="position: absolute; width: 450px; background-color: #8BC1ED; left: 36%; top: 40%">

						<table width="100%" border="0" cellspacing="5">
							<style type="text/css">
a img {
	border: none;
}
</style>
							<tr>
								<td width="100%" style="color: #FFF; padding-left: 10px;">
									提示信息
								</td>
								<td align="right">
									<a href="#" onclick="return hiddenTS(0);"><img src="/entity/reg/images/index/index_close.jpg" /> </a>
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
												<div style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%">
													<font size="2" color="red">学员，您好：
														<br/>
													</font>
												</div>
												<div style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%">
													<font size="2" color="red">
														您有
														<span style="color:#ff0000"><a href="###" onclick="javascript:openPage('/entity/workspaceRegStudent/regStudentWorkspace_getFirstinfo.action')"><%=tongzhigonggaoNum %></a></span>条未读公告,
														<span style="color:#ff0000"><a href="###" onclick="javascript:openPage('/entity/workspaceStudent/studentWorkspace_tositeemail.action')"><%=zhanneixinNum %></a></span>封未读邮件,
														<span style="color:#ff0000"><a href="###" onclick="javascript:openPage('/entity/workspaceStudent/studentWorkspace_getVoteList.action')"><%=diaochawenjuanNum %></a></span>套待填写的调查问卷。
													</font>
													<br/>
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
				</td>
			</tr>
			<tr>
				<td>
					<table width="1002px" border="0" align="center" cellpadding="0" cellspacing="0" style="padding-top: 20px;">
						<tr>
							<td valign="top">
								<IFRAME NAME="bottom" width=100% height=73 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/reg/pub/bottom.jsp" scrolling=no allowTransparency="true" align=center style=""></IFRAME>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
