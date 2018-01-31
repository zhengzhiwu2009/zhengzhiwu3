<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.MyResultSet"/>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
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
String path1 = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>中国证劵业协会培训平台</title>
<link href="/web/bzz_index/style/index.css" rel="stylesheet" type="text/css">
<link href="/web/bzz_index/style/msfc.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/web/bzz_index/js/wsMenu.js"></script>
<script language="javascript">
function resize()
{
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
}
</script>
<style type="text/css">
#contact{
background:url(/web/bzz_index/rhbm/images/rhbm_bg.jpg) no-repeat;
width:647px;
height:249px;
}
#contact div{
float:left;
width:200px;
color:#256485;
font-size:12px;
margin-left:90px;
}
#contact div p{
margin:0;
padding:0;
line-height:24px;}
</style>
</head>

<body>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td height="209"><iframe id="top" name="top" frameborder="0" width="1002" height="209" scrolling="no" src="/web/bzz_index/top.jsp"></iframe></td>
  </tr>
  <tr>
    <td align="center"><table width="932" align="center" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="199" valign="top" >
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="/web/bzz_index/lxwm_new/images/contact_03.jpg" width="199" height="5"></td>
  </tr>
  <tr>
    <td align="center" style="padding:15px 0; background:#F2F2F2"><img src="/web/bzz_index/lxwm_new/images/contact_06_1.jpg"></td>
  </tr>
  <tr>
    <td><img src="/web/bzz_index/lxwm_new/images/contact_15.jpg"></td>
  </tr>
</table> </td>
                <td align="center" valign="top" style="padding-left:30px;" >
                <table width="91%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="667"><img src="/web/bzz_index/rhbm/images/rhbm_001.jpg" width="667" height="26"></td>
  </tr>
  <tr>
    <td height="690" id="contact" >
    <div style="width:600px; text-align:left; margin-left:20px;font-size:14px;padding-top:25px;">
   <h3> 一、报名时间</h3>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2012年4月16日至6月15日
   <h3> 二、培训时间</h3>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2012年四期班于2012年7月开学，2013年7月结束
    <h3>三、报名流程</h3>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.各中央企业以集团公司为单位，统一组织下属企业报名工作。登录清华大学班组长岗位管理能力资格认证培训网站<a href="http://www.thbzzpx.org" target="_blank">www.thbzzpx.org</a>,下载《<a href="http://www.thbzzpx.org/web/bzz_index/files/xydjb.xls">企业和学员报名信息登记表</a>》，并将填写好的登记表发送到清华大学邮箱（bzzbm@mail.itsinghua.com）。发送邮件后,将收到自动回复,说明您的报名邮件已接收到，参训企业即可进行交费。如交表后未能如期收到回复，请重新发送邮件并拨打电话010-62797946确认。<br/><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.企业将培训费统一汇款至清华大学账户：<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户    名：清华大学<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开户银行：工行北京分行海淀西区支行<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;帐    号：0200004509089131550<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;汇款时请在汇款用途上注明“班组长远程培训”。汇款后请将汇款凭证（底联）传真到清华大学（010-62789770），传真应注明：报名单位名称、企业联系人姓名、电话、发票邮寄地址；以个人名称汇款的必须同时加注汇款人姓名、发票抬头。清华大学将根据传真开具发票，并邮寄至报名单位。<br/><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.清华大学将在收到企业培训费后10个工作日内完成学员注册，制作并寄出学员卡。《学员手册》及《学员学习指导》光盘将以电子版的形式放置于网站，供学员下载后参考。<br/><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.企业管理员负责接收发票、学员卡及相关学习资料。学员根据学员卡上的学号和密码登陆网站学习。
    <h3>四、培训费用</h3>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;培训费为每人780元
    
    </div>
    </td>
  </tr>
</table>

                
                </td>
              </tr>
            </table>			  </td>
		  </tr>
		</table>
	</td>
  </tr>
  <tr>
    <td><iframe id="foot" name="foot" frameborder="0" width="1002" height="147" scrolling="no" src="/web/bzz_index/bottom1.jsp"></iframe></td>
  </tr>
</table>


</body>
</html>
