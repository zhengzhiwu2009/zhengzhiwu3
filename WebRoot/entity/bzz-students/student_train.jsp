<%@ page language="java" import="java.util.*,com.whaty.platform.entity.bean.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.net.URLEncoder"/>
<jsp:directive.page import="com.whaty.platform.sso.web.servlet.UserSession"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.MyResultSet"/>
<%!
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
 %>
<%
String d=String.valueOf(Math.random());
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.getSession().setAttribute("firstPage", "");
String disabled = "";
UserSession userSession = (UserSession)session.getAttribute("user_session");
if(userSession!=null)
{
}
else
{
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
String sql = "select b.id from pe_bzz_batch b,pe_bzz_student s where b.id=s.fk_batch_id and s.fk_sso_user_id='"+user_id+"' and to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') between start_time and end_time";
String examlate_sql = "select s.id from pe_bzz_examlate e,pe_bzz_student s where e.student_id=s.id and s.fk_sso_user_id='"+user_id+"'";
//String examlate_sql = "select s.id from pe_bzz_examlate e,pe_bzz_student s ,enum_const const where const.id=e.status and e.student_id=s.id and s.fk_sso_user_id='"+user_id+"' and const.code<>2";
String examAgain_sql = "select s.id from pe_bzz_examagain e,pe_bzz_student s,enum_const const where const.id=e.status and e.student_id=s.id and s.fk_sso_user_id='"+user_id+"' and const.code='2'";
//System.out.println(examAgain_sql);
dbpool db = new dbpool();
if(db.countselect(sql)>0||db.countselect(examlate_sql)>0||db.countselect(examAgain_sql)>0)
{
	disabled = "";
}
else
{
	disabled = "disabled";
}
boolean yanchang = false;
String yanchang_sql = "select id from pe_bzz_student where study_end_date is not null and fk_sso_user_id='"+user_id+"' and to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')<=study_end_date";
if(db.countselect(yanchang_sql)>0){
	yanchang=true;
}
String sql1 = "select b.id from pe_bzz_batch b,pe_bzz_student s where b.id=s.fk_batch_id and s.fk_sso_user_id='"+user_id+"'";
MyResultSet rs = null;
String batch_id="";
rs=db.executeQuery(sql1);
if(rs!=null && rs.next())
{
	batch_id=fixnull(rs.getString("id"));
}
db.close(rs);


String courseSever = fixnull((String)session.getAttribute("SelectedServerAddr"));
if(courseSever.equals(""))
	courseSever="自动选择";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中国证劵业协会培训平台</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
<script type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}


function selectServer() {
	window.open ('<%=basePath%>training/student/course/serverSelect.jsp', 'selectserver', 
	'height=600, width=800, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
}
//-->
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
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
</style></head>

<body onLoad="MM_preloadImages('/entity/bzz-students/images/jckc1.jpg','/entity/bzz-students/images/jckc1_over.jpg','/entity/bzz-students/images/tskc2.jpg','/entity/bzz-students/images/tskc2_over.jpg','/entity/bzz-students/images/zyzc2.jpg','/entity/bzz-students/images/zyzc2_over.jpg')">
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <IFRAME NAME="top" width=100% height=150 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/top.jsp" scrolling=no allowTransparency="true"></IFRAME>
  <tr>
    <td height="13"></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="237" valign="top"><IFRAME NAME="leftA" width=237 height=850 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/left.jsp?d=<%=d %>" scrolling=no allowTransparency="true"></IFRAME></td>
   <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17" align="left" class="twocentop"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" /> 当前位置：专项培训    &nbsp;&nbsp;&nbsp;&nbsp;</td>
      </tr>
      <tr>
          <td><br/><!-- <img name="two2_r5_c5" src="images/two/two2_r5_c5.jpg" width="750" height="72" border="0" id="two2_r5_c5" alt="" /> --></td>
      </tr>
      <tr align="center">
        <td height="29">
        <table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr> 
          <td width="47" height="30" align="center" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"><input type=checkbox></td>
            <td width="404" align="center" background="/entity/bzz-students/images/two/two2_r15_c5.jpg">名称</td>
             <td width="108" align="center" background="/entity/bzz-students/images/two/two2_r15_c5.jpg">开始时间</td>
            <td width="134" align="center" background="/entity/bzz-students/images/two/two2_r15_c5.jpg">截止时间</td>
            <td width="104" align="center" background="/entity/bzz-students/images/two/two2_r15_c5.jpg">学时数</td>
            <td width="152" align="center" background="/entity/bzz-students/images/two/two2_r15_c5.jpg">总学时数</td>
            <td width="152" align="center" background="/entity/bzz-students/images/two/two2_r15_c5.jpg">缴费金额（元）</td>
            <td width="152" align="center" background="/entity/bzz-students/images/two/two2_r15_c5.jpg">操作</td>
          </tr>
          <tr>
          <td width="47" height="30" align="center"><input type=checkbox></td>
            <td width="404" align="center" >2011保代</td>
             <td width="108" align="center">2013-01-01</td>
            <td width="134" align="center">2013-05-01</td>
            <td width="104" align="center">2</td>
            <td width="152" align="center">70</td>
            <td width="152" align="center">150</td>
            <td width="152" align="center"><a href=#>查看详情</a>/<a href=#>支付</a></td>
          </tr> 
            
         	<tr align="center">
         		<td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg" colspan="8">
         			<table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
         					<tr>
         						<td width="20%"><s:if test="pagedown!=-1"><a href="/entity/workspaceStudent/bzzstudent_toJiChuCourses.action?pagenow=1&pageSize =<s:property value ='pageSize'/>">首  页</a></s:if><s:else> &nbsp; </s:else></td>
         						<td width="20%"><s:if test="pagedown!=-1"><a href="/entity/workspaceStudent/bzzstudent_toJiChuCourses.action?pagenow=<s:property value ='pagedown'/>&pageSize=<s:property value ='pageSize'/>">上一页</a></s:if></td>
         						<td width="20%"><s:if test="pagenext!=-1"><a href="/entity/workspaceStudent/bzzstudent_toJiChuCourses.action?pagenow=<s:property value ='pagenext'/>&pageSize=<s:property value ='pageSize'/>">下一页</a></s:if></td>
         						<td width="20%"><s:if test="pagenext!=-1"><a href="/entity/workspaceStudent/bzzstudent_toJiChuCourses.action?pagenow=<s:property value ='pagetotal'/>&pageSize=<s:property value='pageSize'/>">末 页</a></s:if></td>
         						<td width="20%">共&nbsp;<s:property value ='pagetotal'/>&nbsp;页/&nbsp;<s:property value ='pagecount'/>&nbsp;条</td>
         					</tr>
         			</table>
         		</td>
         	</tr>
         
         
      
      <tr>
       <td width="47">&nbsp;</td>
      </tr>
      
     </table></td>
    <td width="13">&nbsp;</td>
  </tr>
</table>

</td>
</tr>
</table>
<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no allowTransparency="true" align=center></IFRAME>
</body>
</html>