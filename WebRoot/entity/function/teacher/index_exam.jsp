<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../pub/priv.jsp"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<% 
   // UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	
		
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>中国证券协会-在线考试</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link href="css/admincss.css" rel="stylesheet" type="text/css">

<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>
<%

	String courseName = "";
	if(openCourse!=null)
	{
		courseName = openCourse.getBzzCourse().getName();
		courseId=openCourse.getBzzCourse().getId();
	}
%>
<script>
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
</head>

<body scroll=no>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
   <tr>
    <td height="93" valign="top"><table  width="100%" height="93" border="0" style=" background-repeat:repeat-x;"background="images/top_bg.png" align="center" cellpadding="0" cellspacing="0" bgcolor="#4B8CBB">
      <tr>
        <td width="342" align="left"><img src="images/logo2.png" /></td>
        <td style="padding-bottom:12px; background-position:right; background-repeat:no-repeat; padding-right:12px" align="right" valign="bottom" background="images/topbg_2.jpg"><a href="#"><img src="images/logout.jpg" width="62" height="21" border="0" onclick="if(confirm('您确定要退出工作室吗？')){parent.window.close();}"></a></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="180" height="100%" valign="top" background="" bgcolor="#4B8CBB" style="padding-left:4px; background-repeat:repeat-y" id="bj"><table width="208" height="42" border="0" cellpadding="0" cellspacing="0" bgcolor="#E2EEF3" >
          <tr>
            <td ><table style="margin-top:5px;" width="208" height="26" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="80" valign="top" bgcolor="#4B8CBB" class="bai"><p>·当前位置：<br/>
                </p></td>
                <td width="128" bgcolor="#4B8CBB" class="bai"><%=courseName %></td>
              </tr>
            </table>            
           </td>
          </tr>
          <tr>
            <td align="center" >
	            <table style="margin-top:15px; margin-left:2px" width="138" border="0" cellpadding="0" cellspacing="0" bgcolor="#e2eef3">
	              <tr>
	              	
	              	<td width="138" height="45" align="center" valign="top" background="/entity/function/images/menu3_1bg.jpg" style="background-repeat:no-repeat; padding-top:13px; padding-left:15px; background-position:center"><a href="/entity/function/hzphExamlore/lore_dir_list.jsp" target="mainer" class="menuzi">题库管理</a></td>
	              	
	              </tr>
	              <tr>
		              <td width="138" height="45" align="center" valign="top" background="/entity/function/images/menu6_1bg.jpg" style="background-repeat:no-repeat; padding-top:13px; padding-left:15px; background-position:center"><a href="/entity/function/hzphExampaper/onlinetestcourse_list.jsp" target="mainer" class="menuzi">在线考试</a></td>	              </tr>
	              </tr>
	              <!--
	               <td width="107" height="45" align="center" valign="top" background="images/menu7bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/function/exampaper/alert.jsp" target="mainer" class="menuzi">工具与案例</a></td>
	                 <td width="107" height="45" align="center" valign="top" background="images/menu6bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="#" target="mainer" class="menuzi">满意度调查</a></td>
	             <tr>
	               <a href="/entity/function/onlinehomeworkpaper/homeworkpaper_list.jsp" target="mainer" class="menuzi"><td width="107" height="45" align="center" valign="top" background="images/suitanglianxi.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"></td></a> 
	             </tr> -->
	            </table>
	            <br/>
            </td>
          </tr>
        </table>
        <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td valign="top" background="" style="background-position:bottom; background-repeat:no-repeat;"><table style="margin-top:15px" width="206" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                     <td height="16" colspan="3"></td>
                  </tr>
                  
                  <tr>
                </table></td>
              </tr>
          </table></td>
        <td width="12" align="left" background="images/leftbg4.jpg" style="background-repeat:repeat-y; background-position:right" id="abc"><img id="button" alt="单击收缩/展开右侧功能区"  src="images/jiaotou.jpg" width="9" height="79" style="margin:0px 3px 0px 0px; cursor:pointer" onClick="abc();if(abcStatus==0) this.src='images/jiaotou1.jpg';else this.src='images/jiaotou.jpg';aa('bj');"></td>
        <td height="100%" align="center" valign="top"><iframe src="/entity/function/hzphExamlore/lore_dir_list.jsp" width="100%" height="100%" frameborder="0" id="mainer" name="mainer" align="top" scrolling="yes" style="margin:0px 8px 0px 0px;"></iframe></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="47"><table width="100%" height="47" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="5" bgcolor="#4B8CBB"></td>
      </tr>
      <tr>
        <td height="42" bgcolor="#4B8CBB" align="center" background="images/btbg_2.jpg" class="baizi">版权所有：中国证券业协会</td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
