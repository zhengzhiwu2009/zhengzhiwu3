<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String d=String.valueOf(Math.random());
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中国证劵业协会培训平台</title>
<link href="/entity/bzz-students/css1.css" rel="stylesheet" type="text/css" />
<link href="/entity/manager/statistics/css/admincss.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" media="all" href="/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1"/>
<script type="text/javascript" src="/js/calendar/calendar.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
		<script language="javascript" src="/js/calender.js"></script>	
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {font-size: 12px; color: #0041A1; font-weight: bold; }
.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}
.kctx_zi1 {
	font-size: 12px;
	color: #0041A1;
}
.kctx_zi2 {
	font-size: 12px;
	color: #54575B;
	line-height: 24px;
	padding-top:2px;
}
-->
</style>
<script language="javascript" >
	function checknull(){
		var Phone = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,8}(\-\d{1,5})?$/;
		var Mobile = /^((\(\d{2,3}\))|(\d{3}\-))?1[3|5|8]\d{9}$/;
		var Email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
		var  elem = document.getElementsByTagName("input");
		for(k =0 ;k<elem.length;k++){
			if(elem[k].type=='text'&&elem[k].value.length<1){
				alert("至少有一项信息未填写,请您完善您的资料！");
				return false;
			}else{
				if(elem[k].name=="mobilePhone"&&!Mobile.test(elem[k].value)){
						alert("移动电话格式不正确!");
						return false;
			    }
				if(elem[k].name=="phone"&&!Phone.test(elem[k].value)){
						alert("办公电话格式不正确!");
						return false;
				}
				if(elem[k].name=="email"&&!Email.test(elem[k].value)){
						alert("电子邮件格式不正确!");
						return false;
				}
			}
		}
	}
</script>
</head>
<body>
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
    <td width="237" valign="top"><IFRAME NAME="leftA" width=237 height=850 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/left.jsp?d=<%=d %>" scrolling=no allowTransparency="true"></IFRAME></td>
   <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17" align="left" class="twocentop">	<div class="content_title"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" />当前位置：个人信息</div>
	</td>
      </tr>
      <tr>
      	<td>&nbsp;</td>
      </tr>
   <tr align="center">
        <td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr>
          <td width="5%" align="center"></td>
            <td width="45%" align="left">&lt;&lt; <font color="red"><s:property value="peBzzStudent.trueName"/></font>&gt;&gt; 的个人信息: </td>
          </tr>
        </table></td>
      </tr>
       <table width="75%" border="0" align="center" cellpadding="0" cellspacing="0" style="border:solid 2px #9FC3FF; padding:5px; margin:15px 0px 15px 0px;">
  <tr>
    <td>
    <form action="/entity/workspaceStudent/bzzstudent_examModifyInfo.action" method="post" name="infoform"  onsubmit="return checknull();" >
    <input type="hidden" name="peBzzStudent.photo" value="<s:property value="peBzzStudent.photo"/>"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D7D7D7">
      <tr bgcolor="#ECECEC">
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><font color="red" size="3">*</font> <strong>学    号：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"><input type="hidden" name="peBzzStudent.regNo" value="<s:property value='peBzzStudent.regNo'/>"/> &nbsp; <s:property value="peBzzStudent.regNo"/><input type="hidden" name="peBzzStudent.id" value="<s:property value='peBzzStudent.id'/>"/></td>
        <td align="center" valign="middle" bgcolor="#FAFAFA" class="kctx_zi2" rowspan="6" width="120"><img src="/incoming/student-photo/<s:if test="peBzzStudent.photo != null"><s:property value="peBzzStudent.peBzzBatch.batchCode"/>/<s:if test="peBzzStudent.peEnterprise.peEnterprise != null"><s:property value="peBzzStudent.peEnterprise.peEnterprise.code"/></s:if><s:else><s:property value="peBzzStudent.peEnterprise.code"/></s:else>/<s:property value="peBzzStudent.photo"/>?timestamp=<%=new Date().getTime() %></s:if><s:else>noImage.jpg</s:else>" width='120' hight='174'/> </td>
        </tr>
        <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><font color="red" size="3">*</font> <strong>姓    名：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp;<s:if test='temp.equals("0") && peBzzEditStudent == null'><input type="text" name="trueName" value="<s:if test='peBzzStudent.trueName.length()>0'><s:property value="peBzzStudent.trueName"/></s:if><s:else></s:else>" /></s:if><s:else><s:property value="peBzzStudent.trueName"/></s:else> </td>
        
        </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><font color="red" size="3">*</font> <strong>性   别：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"><input type="radio" name="gender" value="男" <s:if test='peBzzStudent.gender=="男"'>checked</s:if> />男  &nbsp;  <input name="gender" type="radio" value="女" <s:if test='peBzzStudent.gender=="女"'>checked</s:if> />女</td>
      </tr> 
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><font color="red" size="3">*</font> <strong>出生日期：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp;<input type="text" name="birthdayDate" value="<s:date name='peBzzStudent.birthdayDate' format='yyyy-MM-dd'/>" id="peBzzStudent.birthdayDate" readonly/></td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><font color="red" size="3">*</font> <strong>民    族：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <input type="text" name="folk" value="<s:if test='peBzzStudent.folk.length()>0'><s:property value="peBzzStudent.folk"/></s:if><s:else></s:else>" maxlength=20/>  <font color="red" size="2px">* 20个字以内</font>
                                  		</td>
     </tr>
     <tr>                             		
        <td align="center" bgcolor="#E9F4FF" class="STYLE1"><font color="red" size="3">*</font> 学   历：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <select name="education">
        											<option value="初中" <s:if test='peBzzStudent.education=="初中"'>selected</s:if> >初中</option>
       												<option value="高中" <s:if test='peBzzStudent.education=="高中"'>selected</s:if> >高中</option>
        											<option value="职高" <s:if test='peBzzStudent.education=="职高"'>selected</s:if> >职高</option>
        											<option value="中专" <s:if test='peBzzStudent.education=="中专"'>selected</s:if> >中专</option>
        											<option value="技校" <s:if test='peBzzStudent.education=="技校"'>selected</s:if> >技校</option>
        											<option value="大专" <s:if test='peBzzStudent.education=="大专"'>selected</s:if> >大专</option>
        											<option value="本科" <s:if test='peBzzStudent.education=="本科"'>selected</s:if> >本科</option>
        											<option value="硕士" <s:if test='peBzzStudent.education=="硕士"'>selected</s:if> >硕士</option>
        											<option value="博士" <s:if test='peBzzStudent.education=="博士"'>selected</s:if> >博士</option>
        								</select></td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="STYLE1"><font color="red" size="3">*</font> 所在学期：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2"> <input type="hidden" name="peBzzStudent.peBzzBatch.name" value="<s:property value="peBzzStudent.peBzzBatch.name"/>"/> &nbsp; <s:if test="peBzzStudent.peBzzBatch.name == null "></s:if>
                                  		<s:property value="peBzzStudent.peBzzBatch.name"/> </td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="STYLE1"><font color="red" size="3">*</font> 通信地址：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2"><input type="hidden" name="peBzzStudent.address" value="<s:property value="peBzzStudent.address"/>"/> &nbsp; <s:if test='peBzzStudent.address.length()>0'><s:property value="peBzzStudent.address"/></s:if><s:else></s:else></td>
      </tr>
      <tr>                            		
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2"><DIV align="center" class="STYLE1"><font color="red" size="3">*</font> 办公电话：</DIV></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2"> &nbsp; <input type="text" name="phone" value="<s:if test='peBzzStudent.phone.length()>0'><s:property value="peBzzStudent.phone"/></s:if><s:else></s:else>" maxlength=50/><font color="red" size="2px">* 格式:010-11111111,50个字以内</font></td>
     </tr>
     <tr>                             		
        <td align="center" bgcolor="#E9F4FF" class="STYLE1"><font color="red" size="3">*</font> 移动电话：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2"> &nbsp; <input type="text" name="mobilePhone" value="<s:if test='peBzzStudent.mobilePhone.length()>0'><s:property value="peBzzStudent.mobilePhone"/></s:if><s:else></s:else>" maxlength=50/><font color="red" size="2px">* 格式:13911111111,50个字以内</font></td>  
      </tr>
      <tr>
       
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><font color="red" size="3">*</font> <strong>所在企业：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2"><input type="hidden" name="peBzzStudent.peEnterprise.name" value="<s:property value="peBzzStudent.peEnterprise.name"/>"/> &nbsp; <s:if test="peBzzStudent.peEnterprise.name.length() < 1"></s:if>
                                  		<s:property value="peBzzStudent.peEnterprise.name"/> </td>                        		
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="STYLE1" ><font color="red" size="3">*</font> 电子邮件：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2"> &nbsp; <input type="text" name="email" value="<s:if test='peBzzStudent.email.length()>0'><s:property value="peBzzStudent.email"/></s:if><s:else></s:else>" maxlength=50/><font color="red" size="2px">* 50个字以内</font></td>
       </tr>
 <!--       <tr>
        <td align="center" bgcolor="#E9F4FF" class="STYLE1" > &nbsp;个人照片：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2"> &nbsp; <input type="file" name="photo"/> </td>
        </tr> 
 -->    </table></td>
  </tr>
</table>
<table border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
	 <td width="20"><input type="submit" value="修 &nbsp;改" stype="cursor:hand"/></td>
	<!--  <td><a href="/entity/workspaceStudent/bzzstudent_StudentInfo.action"><img src="/entity/bzz-students/images/two/an_05.gif" width="71" height="23" border="0"></a></td>  -->
  	 <td width="20"><input type="button" value="返 &nbsp;回" stype="cursor:hand" onclick="javascript:window.navigate('/entity/workspaceStudent/bzzstudent_studentInfo.action')"/></td> 
  </tr>
</table>
    </table>
    </form>
    </td>
    <td width="13">&nbsp;</td>
  </tr>
</table>
<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no allowTransparency="true" align=center></IFRAME>
</td>
</tr>
</table>
</body>
<script type="text/javascript">
    Calendar.setup({
    	inputField     :    "peBzzStudent.birthdayDate",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_a",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
	</script>
</html>