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
function displayFun(){
document.getElementById("trId").style.display='none';
}
</script>
<script language="JavaScript" src="js/cookie.js"></script>
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
</head>
<BODY STYLE='OVERFLOW:SCROLL;OVERFLOW-X:HIDDEN'> 
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#B98EE3">
               <tr>
                <td valign="top" background="/entity/bzz-students/exam/images-exam/leftbtbg.jpg" style="background-position:bottom; background-repeat:no-repeat;">
                <table style="margin-top:15px" width="196" border="0" cellspacing="0" cellpadding="0">
                <tr>
                <td height="30" class="bai" style="padding-left:10px"><font size='2'>当前时间：<%=nowDateStr %></font>
                </td>
                </tr>
                <s:if test="peBzzExamBatch != null">
                  <tr>
                    <td height="30" class="bai" style="padding-left:10px">您当前的考试状态是:<s:if test='examNote==null'>未报名</s:if> <s:property value='examNote'/></td>
                  </tr>
                  <s:if test='timeTemp==1'>
                  	<tr>
                     <td  height="37" width="196" align="center" valign="middle" background="/entity/bzz-students/exam/images/menu3bg1new1.jpg" style="background-repeat:no-repeat; align:center; valign:middle; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamModifyN.action" target="mainer" class="menuzi">个人信息</a></td>
                  </tr>
                  <tr>
                    <td height="8" class="bai" style="padding-left:10px"></td>
                  </tr>
                  </s:if>
                  <s:elseif test='timeTemp==0'>
                  	<tr>
                     <td  height="37" width="196" align="center" valign="middle" background="/entity/bzz-students/exam/images/menu3bg1new1.jpg" style="background-repeat:no-repeat; align:center; valign:middle; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamInfo.action" target="mainer" class="menuzi">个人信息</a></td>
                  </tr>
                  <tr>
                    <td height="8" class="bai" style="padding-left:10px"></td>
                  </tr>
                  </s:elseif>
                  <s:if test='peBzzExamScore==null && peBzzExamLate==null'>
                      <s:if test='timeTemp==1'>
	                  <tr>
	                    <td  height="37" width="196" align="center" valign="middle" background="/entity/bzz-students/exam/images/menu3bg1new1.jpg" style="background-repeat:no-repeat; align:center; valign:middle; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamIntroduce.action" target="mainer" class="menuzi">考试报名</a></td>
	                  </tr>
	                  <tr>
                    <td height="8" class="bai" style="padding-left:10px"></td>
                  </tr>
	                  </s:if>
	                  <s:if test='lateTimeTemp==1'>
	                  	<tr>
	                    <td  height="37" width="196" align="center" valign="middle" background="/entity/bzz-students/exam/images/menu3bg1new1.jpg" style="background-repeat:no-repeat; align:center; valign:middle; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toLateConditionViewN.action" target="mainer" class="menuzi">缓考申请</a></td>
	                  </tr>
	                  <tr>
                    <td height="8" class="bai" style="padding-left:10px"></td>
                  </tr>
	                  </s:if>
                  </s:if>
                  <s:elseif test='peBzzExamScore!=null && peBzzExamLate==null'>
                  	<tr>
	                    <td  height="37" width="196" align="center" valign="middle" background="/entity/bzz-students/exam/images/menu3bg1new1.jpg" style="background-repeat:no-repeat; align:center; valign:middle; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_examstudentn.action" target="mainer" class="menuzi">查看审核状态</a></td>
	                  </tr>
	                  <tr>
                    <td height="8" class="bai" style="padding-left:10px"></td>
                  </tr>
	                  <s:if test='examTemp==0 && timeTemp==1'>
		                  <tr>
		                    <td  height="37" width="196" align="center" valign="middle" background="/entity/bzz-students/exam/images/menu3bg1new1.jpg" style="background-repeat:no-repeat; align:center; valign:middle; padding-top:8px; padding-left:14px">
		                    <a href="javascript:if(confirm('您确定取消考试报名吗？')) window.location.href=window.location.href='/entity/workspaceStudent/bzzstudent_cancelBao.action'" target="mainer" class="menuzi">取消报名</a></td>
		                  </tr>
		                  <tr>
                    <td height="8" class="bai" style="padding-left:10px"></td>
                  </tr>
		              </s:if>
                  </s:elseif>
                  <s:elseif test='peBzzExamScore==null && peBzzExamLate!=null'>
                  	<tr>
	                    <td  height="37" width="196" align="center" valign="middle" background="/entity/bzz-students/exam/images/menu3bg1new1.jpg" style="background-repeat:no-repeat; align:center; valign:middle; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_latestudentn.action" target="mainer" class="menuzi">查看审核状态</a></td>
	                  </tr>
	                  <tr>
                    <td height="8" class="bai" style="padding-left:10px"></td>
                  </tr>
	                  <s:if test='lateTemp==0 && lateTimeTemp==1'>
	                  <tr>
	                    <td  height="37" width="196" align="center" valign="middle" background="/entity/bzz-students/exam/images/menu3bg1new1.jpg" style="background-repeat:no-repeat; align:center; valign:middle; padding-top:8px; padding-left:14px">
	                    <a href="javascript:if(confirm('您确定取消缓考申请吗？')) window.location.href=window.location.href='/entity/workspaceStudent/bzzstudent_cancelLa.action'" target="mainer" class="menuzi">取消缓考申请</a>
	                   </td>
	                  </tr>
	                  <tr>
                    <td height="8" class="bai" style="padding-left:10px"></td>
                  </tr>
	                  </s:if>
                  </s:elseif>
                  </s:if>
                   <s:if test='flagLate==1'>
                    <tr>
                    <td height="30" class="bai" style="padding-left:10px">您当前的考试状态是:缓考申请审核成功<s:property value='examNote'/></td>
                  	</tr>
                  	<tr>
                     <td  height="37" width="196" align="center" valign="middle" background="/entity/bzz-students/exam/images/menu3bg1new1.jpg" style="background-repeat:no-repeat; align:center; valign:middle; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamInfo.action" target="mainer" class="menuzi">个人信息</a></td>
                  </tr>
                  <tr>
                    <td height="8" class="bai" style="padding-left:10px"></td>
                  </tr>
	                   <tr>
	                    <td  height="37" width="196" align="center" valign="middle" background="/entity/bzz-students/exam/images/menu3bg1new1.jpg" style="background-repeat:no-repeat; align:center; valign:middle; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamIntroduce.action"  target="mainer" class="menuzi">考试报名</a></td>
	                  </tr>
	                  <tr>
                    <td height="8" class="bai" style="padding-left:10px"></td>
                  </tr>
	                  </s:if>
                   <s:if test='falgAgainExam==1'>
                   <tr>
                    <td height="30" class="bai" style="padding-left:10px">您当前的考试状态是:未报名</td>
                  	</tr>
                  	<tr>
                     <td  height="37" width="196" align="center" valign="middle" background="/entity/bzz-students/exam/images/menu3bg1new1.jpg" style="background-repeat:no-repeat; align:center; valign:middle; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamInfo.action" target="mainer" class="menuzi">个人信息</a></td>
                  </tr>
                  <tr>
                    <td height="8" class="bai" style="padding-left:10px"></td>
                  </tr>
	                   <tr id="trId">
	                    <td  height="37" width="196" align="center" valign="middle" background="/entity/bzz-students/exam/images/menu3bg1new1.jpg" style="background-repeat:no-repeat; align:center; valign:middle; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamAgainIntroduce.action"  target="mainer" class="menuzi">补考申请</a></td>
	                  </tr>
	                  <tr>
                    <td height="8" class="bai" style="padding-left:10px"></td>
                  </tr>
	                  </s:if>
	                  <s:if test='flagExamAgain==0'>
	                  <tr>
                    <td height="30" class="bai" style="padding-left:10px">您当前的考试状态是:补考申请成功，请等待审核</td>
                  	</tr>
                    </s:if>
	                   <s:if test='flagExamAgain==5'>
	                   <tr>
                    <td height="30" class="bai" style="padding-left:10px">您当前的考试状态是:补考申请审核成功，考试<s:property value='examNote'/></td>
                  	</tr>
                  	<s:if test='bBaoming==null'>
                  		<tr>
                     <td  height="37" width="196" align="center" valign="middle" background="/entity/bzz-students/exam/images/menu3bg1new1.jpg" style="background-repeat:no-repeat; align:center; valign:middle; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamInfo.action" target="mainer" class="menuzi">个人信息</a></td>
                  </tr>
                    <tr>
                    <td height="8" class="bai" style="padding-left:10px"></td>
                  </tr>
	                  <tr>
	                    <td  height="37" width="196" align="center" valign="middle" background="/entity/bzz-students/exam/images/menu3bg1new1.jpg" style="background-repeat:no-repeat; align:center; valign:middle; padding-top:8px; padding-left:14px"><a href="/entity/workspaceStudent/bzzstudent_toExamIntroduce.action" target="mainer" class="menuzi">考试报名</a></td>
	                  </tr>
	                  <tr>
                    <td height="8" class="bai" style="padding-left:10px"></td>
                  </tr>
                   </s:if>
	                  </s:if>
                </table>
              <!--   &ge;&gt;&le;&lt;--> 
                </td>
              </tr>
          </table>
</body>
</html>

