<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.whaty.platform.sso.web.servlet.UserSession"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String d=String.valueOf(Math.random());
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String firstPage="/entity/function/testpaper/onlinetestcourse_list.jsp";
//request.getSession().setAttribute("firstPage", "/entity/function/testpaper/onlinetestcourse_list.jsp");
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
//在学员进行报名时存在一点问题，有部分学员补考申请通过，但申请报名时会把补考信息给删掉，在些附加如果在当前批次有该学员的报名信息时也可以进行在线自测
String examscore_sql = "select * from pe_bzz_examscore a,pe_bzz_student b,pe_bzz_exambatch c "+
	"where a.student_id=b.id and b.fk_sso_user_id='ff8080812515036801251c2e37d60851'  "+
	"and a.exambatch_id=c.id and c.selected='402880a91dadc115011dadfcf26b00aa'"; 
dbpool db = new dbpool();
if(db.countselect(sql)>0||db.countselect(examlate_sql)>0 || db.countselect(examscore_sql)>0)
{
	disabled = "";
}
else
{
	disabled = "disabled";
}
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

<body onLoad="MM_preloadImages('/entity/bzz-students/images/jckc1.jpg','/entity/bzz-students/images/jckc1_over.jpg','/entity/bzz-students/images/tskc2.jpg','/entity/bzz-students/images/tskc2_over.jpg')">
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
 <tr>
   <td>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <IFRAME NAME="top" width=100% height=200 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/top.jsp" scrolling=no allowTransparency="true"></IFRAME>
  <tr>
    <td height="13"></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="237" valign="top"><IFRAME NAME="leftA" width=237 height=580 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/left.jsp?d=<%=d %>" scrolling=no allowTransparency="true"></IFRAME></td>
   <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17" align="left" class="twocentop"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" /> 当前位置：课程自测</td>
      </tr>
      <tr>
          <td><br/><!-- <img name="two2_r5_c5" src="images/two/two2_r5_c5.jpg" width="750" height="72" border="0" id="two2_r5_c5" alt="" /> --></td>
      </tr>
       <tr>
            <td align="left"><a href="/entity/workspaceStudent/bzzstudent_toZiceCourses.action"><img border="0" src="/entity/bzz-students/images/jckc1.jpg" width="124" height="25"  name="Image1" onMouseOver="MM_swapImage('Image1','','/entity/bzz-students/images/jckc1_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="/entity/workspaceStudent/bzzstudent_toZiceTishengCourses.action"><img border="0" src="/entity/bzz-students/images/tskc2.jpg" width="124" height="25"  name="Image2" onMouseOver="MM_swapImage('Image2','','/entity/bzz-students/images/tskc2_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a>
            </td>
          </tr>
      <tr align="center">
        <td height="29"><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr>
          <td width="5%" height="30" align="center" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"></td>
            <td width="40%" align="left" background="/entity/bzz-students/images/two/two2_r15_c5.jpg">课程名称</td>
            <td width="10%" align="center" background="/entity/bzz-students/images/two/two2_r15_c5.jpg">学时</td>
            <td width="45%" align="center" background="/entity/bzz-students/images/two/two2_r15_c5.jpg">进入自测</td>
          </tr>
       <!--  </table></td>
      </tr>
      <tr valign="top">
        <td ><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocen"> -->
           <s:if test="ziceCourses.size > 0">
     	 <s:iterator value="ziceCourses" status="stuts" >
          <tr>
            <td width="5%"  align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="40%" align="left"><s:property value="getPrBzzTchOpencourse().getPeBzzTchCourse().getName()"/></td>
            <td width="10%" align="center"><s:property value="getPrBzzTchOpencourse().getPeBzzTchCourse().getTime()"/></td>
            <td width="45%" align="center">
           <input type="button" value="进入自测" <%if(disabled!=null&&disabled.equals("disabled")){%> disabled="<%=disabled %>" <%} %> onclick='window.open("<%=basePath %>/sso/bzzinteraction_InteractionStuManage.action?course_id=<s:property value="getPrBzzTchOpencourse().getPeBzzTchCourse().getId()"/>&opencourseId=<s:property value="getPrBzzTchOpencourse().getId()"/>&firstPage=<%=firstPage %>","newwindow");'></td>
			</td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
           </s:iterator>
         </s:if>
         	<tr align="center">
         		<td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg" colspan="8">
         			<table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
         					<tr>
         						<td width="20%"><s:if test="pagedown!=-1"><a href="/entity/workspaceStudent/bzzstudent_toZiceCourses.action?pagenow=1&pageSize =<s:property value ='pageSize'/>">首  页</a></s:if><s:else> &nbsp; </s:else></td>
         						<td width="20%"><s:if test="pagedown!=-1"><a href="/entity/workspaceStudent/bzzstudent_toZiceCourses.action?pagenow=<s:property value ='pagedown'/>&pageSize=<s:property value ='pageSize'/>">上一页</a></s:if></td>
         						<td width="20%"><s:if test="pagenext!=-1"><a href="/entity/workspaceStudent/bzzstudent_toZiceCourses.action?pagenow=<s:property value ='pagenext'/>&pageSize=<s:property value ='pageSize'/>">下一页</a></s:if></td>
         						<td width="20%"><s:if test="pagetotal>1"><a href="/entity/workspaceStudent/bzzstudent_toZiceCourses.action?pagenow=<s:property value ='pagetotal'/>&pageSize=<s:property value='pageSize'/>">末 页</a></s:if></td>
         						<td width="20%">共&nbsp;<s:property value ='pagetotal'/>&nbsp;页/&nbsp;<s:property value ='pagecount'/>&nbsp;条</td>
         					</tr>
         			</table>
         		</td>
         	</tr>
         	
          </table></td>
      </tr>
      <tr>
       <td width="13">&nbsp;</td>
      <!-- </tr>
       
    </table> --></td>
  </tr>
</table>

</td>
  </tr>
</table>
<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no allowTransparency="true" align=center></IFRAME>
</body>
</html>