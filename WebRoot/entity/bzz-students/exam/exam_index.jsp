<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:directive.page
	import="com.whaty.platform.sso.web.servlet.UserSession" />
<%@ page import="com.whaty.platform.entity.*,java.text.*,com.whaty.platform.entity.user.*,com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

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
	String firstPage = fixnull((String)session.getAttribute("firstPage"));
	Date   date   =   new   Date();   
	Format   formatter   =   new   SimpleDateFormat("yyyy-MM-dd");   
	String   nowDateStr   =   formatter.format(date);
	
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>中国证券业协会远程培训系统</title>
<link rel="stylesheet" type="text/css" href="/entity/bzz-students/exam/css/style.css">
<link href="/entity/bzz-students/exam/css/admincss.css" rel="stylesheet" type="text/css">
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

var oPopup = window.createPopup(); 
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

</script>
<script language="JavaScript" src="js/cookie.js"></script>
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
</head>
<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="93" valign="top"><table width="100%" height="93" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#7B47B5">
      <tr>
        <td width="342" align="center">&nbsp;<img src="/entity/bzz-students/exam/images/logo.jpg" width="315" height="60"></td>
        <td style="padding-bottom:12px; background-repeat:no-repeat; padding-right:12px" align="right" valign="bottom" background="/entity/bzz-students/exam/images/topbg.jpg"><a href="#"><img src="/entity/bzz-students/exam/images/tuichu.jpg" width="62" height="21" border="0" onclick="if(confirm('您确定要退出工作室吗？')){parent.window.close();}"/></a></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="211" height="100%" valign="top" background="/entity/bzz-students/exam/images/leftbg2.jpg" bgcolor="#004DA9" style="padding-left:4px; background-repeat:repeat-y" id="bj">
        <table width="208" height="42" border="0" cellpadding="0" cellspacing="0" bgcolor="#DBC4F2" >
          <tr>
            <td ><table style="margin-top:5px;" width="208" height="42" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <!-- <td width="80" valign="top" bgcolor="#003fa9" class="bai"><p>·当前课程：<br/>
                </p></td>-->
                <td bgcolor="#B98EE3" align="center" height="42" class="bai1">考试专栏</td>
              </tr>
            </table>            
           </td>
          </tr>
          <tr>
            <td ><table style="margin-top:15px; margin-left:2px" width="205" border="0" cellpadding="0" cellspacing="0" bgcolor="#DBC4F2">
            <s:if test="flagJumpPage != 1&&peBzzExamBatch != null">
              <tr>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu1bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamEx.action" target="mainer" class="menuzi">考试说明</a></td>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu2bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamNoti.action" target="mainer" class="menuzi">考试通知</a></td>
              </tr>
              <tr>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu3bg1.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_examBat.action" target="mainer" class="menuzi">补缓考申请</a></td>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu4bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamCa.action" target="mainer" class="menuzi">准考证打印</a></td>
              </tr>
              <tr>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu5bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamTu.action" target="mainer" class="menuzi">辅导答疑</a></td>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu6bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toSimulatedEx.action" target="mainer" class="menuzi">模拟考试</a></td>
              </tr>
              <tr>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu7bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><!-- <a href="#" target="_self" class="menuzi" onclick="alert('成绩未发布')">成绩查询</a> --><a href="/entity/workspaceStudent/bzzstudent_toStudentSco.action" target="mainer" class="menuzi">成绩查询</a></td>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu9bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toCertifi.action" target="mainer" class="menuzi">认证证书</a></td>
              </tr>
              </s:if>
              <s:elseif test="flagJumpPage != 1&&peBzzExamBatch == null">
              <tr>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu1bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamIdentity.action" target="mainer" class="menuzi">考试说明</a></td>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu2bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamIdentity.action" target="mainer" class="menuzi">考试通知</a></td>
              </tr>
              <tr>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu3bg1.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamIdentity.action" target="mainer" class="menuzi">补缓考申请</a></td>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu4bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamIdentity.action" target="mainer" class="menuzi">准考证打印</a></td>
              </tr>
              <tr>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu5bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamIdentity.action" target="mainer" class="menuzi">辅导答疑</a></td>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu6bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamIdentity.action" target="mainer" class="menuzi">模拟考试</a></td>
              </tr>
              <tr>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu7bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamIdentity.action" target="mainer" class="menuzi">成绩查询</a></td>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu9bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamIdentity.action" target="mainer" class="menuzi">认证证书</a></td>
              </tr></s:elseif>
               <s:if test="flagJumpPage == 1">
              <tr>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu1bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamEx.action" target="mainer" class="menuzi">考试说明</a></td>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu2bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamNoti.action" target="mainer" class="menuzi">考试通知</a></td>
              </tr>
              <tr>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu3bg1.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_examBat.action" target="mainer" class="menuzi">补缓考申请</a></td>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu4bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamCa.action" target="mainer" class="menuzi">准考证打印</a></td>
              </tr>
              <tr>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu5bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamTu.action" target="mainer" class="menuzi">辅导答疑</a></td>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu6bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toSimulatedEx.action" target="mainer" class="menuzi">模拟考试</a></td>
              </tr>
              <tr>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu7bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><!--  <a href="#" target="_self" class="menuzi" onclick="alert('成绩未发布')">成绩查询</a>--><a href="/entity/workspaceStudent/bzzstudent_toStudentSco.action" target="mainer" class="menuzi">成绩查询</a></td>
                <td width="107" height="45" align="center" valign="top" background="/entity/bzz-students/exam/images/menu9bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toCertifi.action" target="mainer" class="menuzi">认证证书</a></td>
              </tr>
              </s:if>
            </table></td>
          </tr>
        </table>
          <table width="209" height="8" border="0" cellpadding="0" cellspacing="0" background="/entity/bzz-students/exam/images/leftcenterbg.jpg">
              <tr>
                <td></td>
              </tr>
            </table>
            <table width="100%" height="320" border="0" cellpadding="0" cellspacing="0" bgcolor="#B98EE3">
               <tr>
                <td valign="top" bgcolor="#B98EE3">
              <iframe src="/entity/workspaceStudent/bzzstudent_toExamIndexMiddle.action" width="100%" height="100%" frameborder="0" id="middle" name="middle" align="top" scrolling="no" style="margin:0px 0px 0px 0px;"></iframe>
           </td> 
            </tr>
            </table>
            </td>
        <td width="12" align="left" background="/entity/bzz-students/exam/images/leftbg2.jpg" style="background-repeat:repeat-y; background-position:right" id="abc"><img id="button" alt="单击收缩/展开右侧功能区"  src="/entity/bzz-students/exam/images/jiaotou.jpg" width="9" height="79" style="margin:0px 3px 0px 0px; cursor:pointer" onClick="abc();if(abcStatus==0) this.src='/entity/bzz-students/exam/images/jiaotou1.jpg';else this.src='/entity/bzz-students/exam/images/jiaotou.jpg';aa('bj');"></td>
        <td height="100%" align="center" valign="top">
        <s:if test="peBzzExamBatch == null">
         <iframe src="/entity/workspaceStudent/bzzstudent_toExamIdentity.action" width="100%" height="100%" frameborder="0" id="mainer" name="mainer" align="top" scrolling="yes" style="margin:0px 8px 0px 0px;"></iframe>
        </s:if>
        <s:else>
        <iframe src="/entity/workspaceStudent/bzzstudent_toExamEx.action" width="100%" height="100%" frameborder="0" id="mainer" name="mainer" align="top" scrolling="yes" style="margin:0px 8px 0px 0px;"></iframe>
        </s:else>
        </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="47"><table width="100%" height="47" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="5" bgcolor="#6F257E"></td>
      </tr>
      <tr>
        <td height="42" align="center" background="/entity/bzz-students/exam/images/btbg.jpg" class="baizi">版权所有：中国证劵业协会</td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>

