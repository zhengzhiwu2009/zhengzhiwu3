<%@ page language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page import="java.util.*,java.text.*" %>
<%@ page import="com.whaty.util.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%if(session.getAttribute("OnlineCourseSessionsIncreased") == null) {
		OnlineCourseCounter.increaseOnlineCourseSessions();	
		session.setAttribute("OnlineCourseSessionsIncreased",true);
		//System.out.println("++");
	}
 %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<base href="<%=basePath%>">
<title>中国证券业协会远程培训系统</title>
<link href="/web/zhibo/css/css.css" rel="stylesheet" type="text/css" />
<script src="/web/zhibo/js/inpng.js"></script>
<style type="text/css">
<!--
-->
</style>
</head>

<body>
<table width="912" border="0" align="center" cellpadding="0" cellspacing="0" class="main_bor">
  <tr>
    <td><img src="/web/zhibo/images/sp1_02.jpg" width="912" height="139" /></td>
  </tr>
  <tr>
    <td><img src="/web/zhibo/images/sp1_04.jpg" width="912" height="128" /></td>
  </tr>
  <tr>
    <td valign="top" class="main_bg"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="31%" align="center" valign="top"><table width="270" height="26" border="0" cellpadding="0" cellspacing="0" background="/web/zhibo/images/sp_03.png">
          <tr>
            <td class="title">日程安排</td>
          </tr>
        </table>
          <table width="269" border="0" cellspacing="0" cellpadding="0">
            <tr class="trbor">
              <td width="14" align="center" valign="middle"><img src="/web/zhibo/images/sp_13.png" width="5" height="5" /></td>
              <td width="60" align="left" valign="middle">8:45-9:30</td>
              <td width="195" align="left">来宾签到；</td>
            </tr>
            <tr class="trbor">
              <td width="14" align="center" valign="middle"><img src="/web/zhibo/images/sp_13.png" width="5" height="5" /></td>
              <td width="60" align="left" valign="middle">9:30-9:35</td>
              <td align="left">主持人宣布典礼开始并介绍出席领导及来宾；</td>
            </tr>
            <tr class="trbor">
              <td width="14" align="center" valign="middle"><img src="/web/zhibo/images/sp_13.png" width="5" height="5" /></td>
              <td width="60" align="left" valign="middle">9:35-9:50</td>
              <td align="left">清华大学领导致辞</td>
            </tr>
            <tr class="trbor">
              <td width="14" align="center" valign="middle"><img src="/web/zhibo/images/sp_13.png" width="5" height="5" /></td>
              <td width="60" align="left" valign="middle">9:50-10:00</td>
              <td align="left">企业管理员代表发言</td>
            </tr>
            <tr class="trbor">
              <td width="14" align="center" valign="middle"><img src="/web/zhibo/images/sp_13.png" width="5" height="5" /></td>
              <td width="60" align="left" valign="middle">10:00-10:05</td>
              <td align="left">2009年一期老学员代表发言</td>
            </tr>
            <tr class="trbor">
              <td width="14" align="center" valign="middle"><img src="/web/zhibo/images/sp_13.png" width="5" height="5" /></td>
              <td width="60" align="left" valign="middle">10:05-10:10</td>
              <td align="left">2010年二期新学员代表发言</td>
            </tr>
            <tr class="trbor">
              <td width="14" align="center" valign="middle"><img src="/web/zhibo/images/sp_13.png" width="5" height="5" /></td>
              <td width="60" align="left" valign="middle">10:10-10:30</td>
              <td align="left">国资委领导总结讲话</td>
            </tr>
            <tr class="trbor">
              <td width="14" align="center" valign="middle"><img src="/web/zhibo/images/sp_13.png" width="5" height="5" /></td>
              <td width="60" align="left" valign="middle">10:30</td>
              <td align="left">典礼结束，人员退场</td>
            </tr>
          </table></td>
        <td width="49%" align="center"><table width="416" border="0" cellspacing="0" cellpadding="0" style="margin-top:5px">
          <tr>
            <td width="27"><img src="/web/zhibo/images/sp_06.png" width="27" height="325" /></td>
            <td valign="top" class="spbg"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="spbox">
              <tr>
                <td>
				<object id=nstv classid='CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6' width=355 height=288 codebase=http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701standby=Loading Microsoft? Windows Media? Player components... type=application/x-oleobject>
<param name='URL' value='http://www.thbzzpx.org/courseserver/search_server2.jsp'>
<PARAM NAME='UIMode' value='full'><PARAM NAME='AutoStart' value='true'>
<PARAM NAME='Enabled' value='true'>
<PARAM NAME='enableContextMenu' value='true'>
<param name='WindowlessVideo' value='true'></object></td>
              </tr>
            </table></td>
            <td width="34"><img src="/web/zhibo/images/sp_09.png" width="34" height="325" /></td>
          </tr>
        </table></td>
        <td width="20%" valign="top"><table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" style="margin-top:50px">
          <tr>
            <td><table width="133" border="0" cellpadding="0" cellspacing="0">
            <tr>
                  <td height="28" align="center" background="/web/zhibo/images/sp_21.png" class="title2">当前观看人数</td>
                </tr>
                <tr>
                  <td height="50" background="/web/zhibo/images/sp_23.png"><table width="100%" height="40" border="0" cellpadding="0" cellspacing="5" class="jishu">
                    <tr>
                    <td align="center" bgcolor="#D46900">
                    <jsp:include page= "/web/bzz_index/zhibo/onlinePersons.txt" flush= "true "> 
					     <jsp:param   name= "temp"   value= "<%=new Date().getTime()%>"/> 
					</jsp:include> 
					</td>
                      <!-- <td align="center" bgcolor="#D46900">0</td>
                      <td align="center" bgcolor="#D46900">0</td>
                      <td align="center" bgcolor="#D46900">0</td>
                      <td align="center" bgcolor="#D46900">0</td>
                      <td align="center" bgcolor="#D46900">1</td>
                      <td align="center" bgcolor="#D46900">5</td> -->
                    </tr>
                  </table></td>
                </tr>
            </table>
              <br/>
              <br/>
              <br/>
              <br/>
              <br />
              <br /></td>
          </tr>
       <!--  <tr>
            <td><table width="131" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td><a href="#"><img src="/web/zhibo/images/sp_26.png" width="132" height="28" border="0" /></a></td>
                </tr>
                <tr>
                  <td><img src="/web/zhibo/images/sp_28.png" width="132" height="28" /></td>
                </tr>
            </table></td>
          </tr>  -->  
        </table></td>
      </tr>
    </table>
    <br /></td>
  </tr>
  <tr>
    <td valign="top"><table width="912" height="100" border="0" align="center" cellpadding="0" cellspacing="0" background="/web/zhibo/images/lingbg.jpg">
      <tr>
        <td align="center"><a href="javascript:window.close()"><img src="/web/zhibo/images/sp_33.png" width="124" height="34" vspace="10" border="0"></a></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="912" height="55" border="0" align="center" cellpadding="0" cellspacing="0" background="/web/zhibo/images/sp1_09.jpg">
      <tr>
        <td class="footer">版权所有：中国证劵业协会<br/>
          投诉：010-11110000 传真：010-11116666技术客服热线电话：010-62796459</td>
      </tr>
    </table></td>
  </tr>
</table>
<div id="pic1" style="position:absolute; visibility:visible; left:0px; top:0px; z-index:5; width: 110; height: 97;">
 <form id="loginform" name="loginform" method="post" action="/web/zhibo/zhibo_voteexe.jsp" target="_blank" >
  <table width="290" height="230" border="0" cellpadding="0" cellspacing="1" bgcolor="#e28016">
    <tr>
      <td valign="top" bgcolor="#FFFFFF"><table width="100%" height="28" border="0" cellpadding="0" cellspacing="0" background="/web/zhibo/images/k_03.jpg">
        <tr>
          <td width="81%" class="diaocha">调 查</td>
          <td width="19%" valign="top"><img src="/web/zhibo/images/k_04.jpg" width="34" height="19" onClick="pic1.style.display='none'" style="cursor:pointer"></td>
        </tr>
      </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="170" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="40" class="title3">　您在观看直播视频时的流畅度如何？</td>
              </tr>
              <tr>
                <td class="diaocha_con"><input type="checkbox" name="item" value="1">
                  视频十分流畅； <br/>
                  <input type="checkbox" name="item" value="2">
                  缓冲时间稍微长一些，但是之后十分顺畅； <br/>
                  <input type="checkbox" name="item" value="3">
                  偶尔有停滞现象； <br/>
                  <input type="checkbox" name="item" value="4">
                  经常出现停滞现象； <br/>
                  <input type="checkbox" name="item" value="5">
                  基本无法观看视频；<br/></td>
              </tr>
            </table></td>
          </tr>
        </table>
        <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#eeeeee">
          <tr>
            <td height="34" align="center"><input type="image" name="imageField" src="/web/zhibo/images/k_09.jpg" onclik="javascript:document.sumit();"></td>
          </tr>
        </table></td>
    </tr>
  </table>
  </form>
</div>
<SCRIPT LANGUAGE="JavaScript">
<!-- Begin
var x = 50,y = 60 
var xin = true, yin = true 
var step = 1 
var delay = 10 
var obj=document.getElementById("pic1") 
function getwindowsize() { 
var L=T=0
//by www.qpsh.com
var R= document.body.clientWidth-obj.offsetWidth 
var B = document.body.clientHeight-obj.offsetHeight 
obj.style.left = x + document.body.scrollLeft 
obj.style.top = y + document.body.scrollTop 
x = x + step*(xin?1:-1) 
if (x < L) { xin = true; x = L} 
if (x > R){ xin = false; x = R} 
y = y + step*(yin?1:-1) 
if (y < T) { yin = true; y = T } 
if (y > B) { yin = false; y = B } 
} 
var itl= setInterval("getwindowsize()", delay) 
obj.onmouseover=function(){clearInterval(itl)} 
obj.onmouseout=function(){itl=setInterval("getwindowsize()", delay)} 
//  End -->
</script>
</body>
</html>
