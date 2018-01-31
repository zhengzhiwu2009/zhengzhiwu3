<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<jsp:directive.page import="com.whaty.platform.sso.web.servlet.UserSession"/>
<% response.setHeader("expires", "0"); %>	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/entity/manager/pub/images/layout.css" rel="stylesheet" type="text/css" />
<script src="/entity/manager/pub/tree/js/tree/menu.js" type="text/javascript"></script>

<title>

</title> 


<script language="JavaScript">
function window_onload()
{
//	initialize();
	setTimeout(expandall,500);
}
//
var judge=1;
function expandall()
{
	var o = document.getElementById("topB");
	if (judge==1)
	{
	
		closeAll();
		judge=0;
		o.src="/entity/manager/pub/tree/js/tree/icon-closeall.gif";
		o.alt="全部展开";
		document.getElementById("topText").innerText = "( 展开全部 )";
	}
	else
	{
		openAll();
		judge=1;
		o.src="/entity/manager/pub/tree/js/tree/icon-expandall.gif";
		o.alt="全部折叠";
		document.getElementById("topText").innerText = "( 折叠全部 )";
	}
}
</SCRIPT>
<style>
body{margin-left:0px; background-color:#E0F2FF;}
.aaul{
    font-size:12px;
	color:#0D2F4F;
	background-color:#E0F2FF;
	margin-left:0px;}
</style>
</head>
<body onLoad="window_onload()" onselectstart="return false;" >
<!--left_menu-->
<div id="left_menu">
  <div class="menu_bg">
 
        <div class="menu_content">
<table id=control width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="16" align="left" valign="top" style="font-size:9pt;color:#666666;cursor:hand"  onclick="expandall(document.getElementById("topB"))"><img src="/entity/manager/pub/tree/js/tree/icon-expandall.gif" id="topB" width="16" height="15" class="button" vspace="2" alt="全部展开" /></td>
    <td id="topText" align="left" valign="top" style="font-size:9pt;color:#666666;cursor:hand;padding-top:4px"  onclick="expandall(document.getElementById("topB"))">( 折叠全部 )</td>
  </tr>
</table>
<table border=0>
  <tr>
    <td >

    
     
    <script language="javascript" type="text/javascript">

			add_item(01,0,"公告列表","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/workspaceStudent/stuPeBulletinView_getFirstinfo.action");
			add_item(02,0,"账户管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
				add_item(02001,02,"账户基本信息","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/workspaceStudent/bzzstudent_StudentInfo.action");
    		add_item(03,0,"课程报名与付费","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
    			add_item(03001,03,"课程报名","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/workspaceStudent/bzzstudent_tonewcourse.action");
    			add_item(03002,03,"选课期查询","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/workspaceStudent/bzzstudent_toqueryselectcourse.action");
    			add_item(03003,03,"预付费账户查询","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/workspaceStudent/bzzstudent_toaccountbalance.action");
    			add_item(03004,03,"专项培训报名","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/workspaceStudent/bzzstudent_tospecialenrolment.action");
    			add_item(03005,03,"我的订单","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/workspaceStudent/bzzstudent_tohistoryrecord.action");
    		
    		add_item(04,0,"课程学习","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
    			add_item(04001,04,"正在学习课程","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/workspaceStudent/bzzstudent_toJiChuCourses.action");
    			add_item(04002,04,"以完成学习课程","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/workspaceStudent/bzzstudent_tolearnreview.action");
    			add_item(04003,04,"免费课程学习","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/workspaceStudent/bzzstudent_tofreecourselearn.action");
    		add_item(05,0,"在线考试","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","#","");
    		add_item(06,0,"站内信箱","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/workspaceStudent/bzzstudent_tositeemail.action");
    		add_item(07,0,"调查问卷","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/workspaceStudent/bzzstudent_getVoteList.action");
    		add_item(08,0,"论坛","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","#","");
    		add_item(09,0,"退出","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","#","");
    		
    		document.write(menu(0));
    </script>

    </td>
  </tr>
</table>
        </div>
    </div>
</div>
</body>
</html>
