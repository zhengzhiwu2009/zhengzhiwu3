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

//行业课程不限制
/*if(db.countselect(sql)>0||db.countselect(examlate_sql)>0||db.countselect(examAgain_sql)>0)
{
	disabled = "";
}
else
{
	disabled = "disabled";
}*/
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

<body onLoad="MM_preloadImages('/entity/bzz-students/images/jckc2.jpg','/entity/bzz-students/images/jckc2_over.jpg','/entity/bzz-students/images/tskc2.jpg','/entity/bzz-students/images/tskc2_over.jpg','/entity/bzz-students/images/hykc1.jpg','/entity/bzz-students/images/hykc1_over.jpg','/entity/bzz-students/images/zyzc2.jpg','/entity/bzz-students/images/zyzc2_over.jpg')">
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
        <td height="17" align="left" class="twocentop"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" /> 当前位置：课程列表    &nbsp;&nbsp;&nbsp;&nbsp;<font color=red>当前视频服务器：<%=courseSever %> &nbsp;&nbsp;&nbsp;&nbsp;(学习课件时，如果视频不流畅，请点击右下方“选择服务器”)</font></td>
      </tr>
      <tr>
          <td><br/><!-- <img name="two2_r5_c5" src="images/two/two2_r5_c5.jpg" width="750" height="72" border="0" id="two2_r5_c5" alt="" /> --></td>
      </tr>
       <tr>
            <td align="left">
            <table width="100%">
            <tr>
            <td width="80%">
            <a href="/entity/workspaceStudent/bzzstudent_toJiChuCourses.action"><img border="0" src="/entity/bzz-students/images/jckc2.jpg" width="124" height="25" name="Image1" onMouseOver="MM_swapImage('Image1','','/entity/bzz-students/images/jckc2_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="/entity/workspaceStudent/bzzstudent_toTiShengCourses.action"><img border="0" src="/entity/bzz-students/images/tskc2.jpg" width="124" height="25" name="Image2" onMouseOver="MM_swapImage('Image2','','/entity/bzz-students/images/tskc2_over.jpg',1)" onMouseOut="MM_swapImgRestore()" /></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="/entity/workspaceStudent/bzzstudent_toHangYeCourses.action"><img border="0" src="/entity/bzz-students/images/hykc1.jpg" width="124" height="25" name="Image4" onMouseOver="MM_swapImage('Image4','','/entity/bzz-students/images/hykc1_over.jpg',1)" onMouseOut="MM_swapImgRestore()" /></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           	<a href="/entity/bzz-students/student_course_status2.jsp"><img border="0" src="/entity/bzz-students/images/zyzc2.jpg" width="124" height="25" name="Image3" onMouseOver="MM_swapImage('Image3','','/entity/bzz-students/images/zyzc2_over.jpg',1)" onMouseOut="MM_swapImgRestore()" /></a>
           <td>
           <td width="20%">
           <!-- 
            当前服务器：<%=(String)session.getAttribute("SelectedServerAddr") %>
            -->
             <input type="button" name="" value="选择服务器..." onClick="selectServer();"/>  
           </td>
            </tr>
            </table>
            
            </td>
            
          </tr>
      <tr align="center">
        <td height="29">
        <table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr>
          <td width="47" height="30" align="center" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"></td>
            <td width="404" align="left" background="/entity/bzz-students/images/two/two2_r15_c5.jpg">课程名称</td>
             <td width="108" align="center" background="/entity/bzz-students/images/two/two2_r15_c5.jpg">课程类型</td>
            <td width="134" align="center" background="/entity/bzz-students/images/two/two2_r15_c5.jpg">学习状态</td>
            <td width="104" align="center" background="/entity/bzz-students/images/two/two2_r15_c5.jpg">课程学习</td>
            <td width="152" align="center" background="/entity/bzz-students/images/two/two2_r15_c5.jpg">详细学习记录</td>
          </tr>
         <s:if test="HangYeCourses.size > 0">
      	<% 
      		List<PrBzzTchStuElective> list = (List<PrBzzTchStuElective>)request.getAttribute("HangYeCourses");
      		int i = 0;
      		for (PrBzzTchStuElective ele : list)
      		{
      	%>
          <tr>
            <td width="47" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="404" align="left">
            <%if(disabled!=null&&disabled.equals("disabled")){%>
            <%=ele.getPrBzzTchOpencourse().getPeBzzTchCourse().getName() %>
            <%}else{ %>
            <a class="twocen1" href='<%=basePath %>sso/bzzinteraction_InteractionStuManage.action?course_id=<%=ele.getPrBzzTchOpencourse().getPeBzzTchCourse().getId() %>&opencourseId=<%=ele.getPrBzzTchOpencourse().getId() %>' target="_blank"><%=ele.getPrBzzTchOpencourse().getPeBzzTchCourse().getName()%></a>
           <%
           if(batch_id.equals("ff8080812824ae6f012824b0a89e0008")&&(ele.getPrBzzTchOpencourse().getId().equals("ff8080812910e7e601291150ddc70413")||ele.getPrBzzTchOpencourse().getId().equals("ff8080812910e7e601291150ddc70419")||ele.getPrBzzTchOpencourse().getId().equals("ff8080812bf5c39a012bf6a1bab80820")||ele.getPrBzzTchOpencourse().getId().equals("ff8080812bf5c39a012bf6a1baba0821")))
           {
           %>
           <font color=red>(二选一)</font>
           <%
           }
            %>
            &nbsp;
            <%
            	if(ele.getTrainingCourseStudent().getPercent() >= 50.0) {
            
            	sql = "select * from pe_bzz_assess a \n"
					+ "where  a.fk_student_id='" + user_id + "' \n"
					+ "      and a.fk_course_id='" + ele.getPrBzzTchOpencourse().getId() + "'";
				//	System.out.println(sql);
				if(db.countselect(sql) > 0) {
			%>
				<a style="color: green;" href="#" onClick="window.open('<%=basePath%>entity/workspaceStudent/bzzAssess_toAssess.action?courseid=<%=ele.getPrBzzTchOpencourse().getId() %>');return false">[已评估]</a>
			<%
				} else {
			%>
				<a style="color: red;" href="#" onClick="window.open('<%=basePath%>entity/workspaceStudent/bzzAssess_toAssess.action?courseid=<%=ele.getPrBzzTchOpencourse().getId() %>');return false">[进入评估]</a>
			<%
				}				
            %>
            
            <%} %>
            <%} %>
            </td>
             <td width="108" align="center"><font color=red><%=ele.getPrBzzTchOpencourse().getPeBzzTchCourse().getEnumConstByFlagCourseCategory().getName() %></font></td>
            <td width="134"><script language="javascript">
                            var selectC<%=i%> =new CreatPro("selectC<%=i%>",<%=ele.getTrainingCourseStudent().getPercent()%>,"",5000,1,10,"/entity/bzz-students/");selectC<%=i%>.stepPro()
                            </script></td>
            <td width="104">
            <input type="button" value="进入学习" <%if(disabled!=null&&disabled.equals("disabled")){%> disabled="<%=disabled %>" <%} %> onclick='window.open("<%=basePath %>sso/bzzinteraction_InteractionStuManage.action?course_id=<%=ele.getPrBzzTchOpencourse().getPeBzzTchCourse().getId() %>&opencourseId=<%=ele.getPrBzzTchOpencourse().getId() %>","newwindow");'></td>
            <td width="152" align=center>
           <a href="/training/student/course/jumpCoursewareStatus.jsp?courseId=<%=ele.getPrBzzTchOpencourse().getPeBzzTchCourse().getId() %>"  onfocus="this.blur()"  class="button" target=_blank ><img src="/entity/bzz-students/images/two/5.jpg" width="59" height="22" border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             <%if((disabled!=null&&disabled.equals("disabled"))&&(!yanchang)){}else{ %>
             <!-- <a href="#" class="button" title="播放课件" onclick="javascript:window.open('<%=basePath %>training/student/course/serverSelect.jsp?http=true&courseId=<%=ele.getPrBzzTchOpencourse().getPeBzzTchCourse().getId() %>&opencourseId=<%=ele.getPrBzzTchOpencourse().getId() %>','courseWin');return false"> -->
             <a href="#" class="button" title="播放课件" onClick="javascript:window.open('<%=basePath %>training/student/course/jumpCourseware.jsp?http=true&courseId=<%=ele.getPrBzzTchOpencourse().getPeBzzTchCourse().getId() %>&opencourseId=<%=ele.getPrBzzTchOpencourse().getId() %>','courseWin');return false"> 
             <img src="/entity/bzz-students/images/two/6.jpg" width="21" height="19"  border=0/></a><%} %></td>
          </tr>
           <tr>
            <td colspan="6"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          
        
          <%
          		i++;
          	} %>
            </s:if> 
            
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