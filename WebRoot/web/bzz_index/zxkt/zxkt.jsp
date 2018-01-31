<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String src=fixnull(request.getParameter("src"));
String menu=fixnull(request.getParameter("menu"));
if(src.equals(""))
{
	src="/web/bzz_index/zxkt/bjjs.jsp";
	menu="menu0";
}
else
{
	src="/web/bzz_index/zxkt/"+src+".jsp";
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<base href="<%=basePath%>">
<title>中国证劵业协会培训平台</title>
<link href="/web/bzz_index/style/index.css" rel="stylesheet" type="text/css">
<link href="/web/bzz_index/style/zxkt.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/web/bzz_index/js/wsMenu.js"></script>
<script language="javascript">
function resize()
{
	document.getElementById("zxkt").height=document.getElementById("zxkt").contentWindow.document.body.scrollHeight;
	document.getElementById("zxkt").height=document.getElementById("zxkt").contentWindow.document.body.scrollHeight;
}
function loadMenu()
{
	overMenu(<%=menu %>);
	//registerMenu(<%=menu %>);
}
</script>
</head>

<body onload="loadMenu()">
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td height="209"><iframe id="top" name="top" frameborder="0" width="1002" height="209" scrolling="no" src="/web/bzz_index/top.jsp"></iframe></td>
  </tr>
  <tr>
    <td align="center" class="body_box"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="218" valign="top" bgcolor="#9FC3FF"><img src="/web/bzz_index/zxkt/images/l_t.jpg" width="218" height="8"></td>
        <td width="16" rowspan="3"></td>
        <td width="696" rowspan="3" valign=top><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
                <td height="27" background="/web/bzz_index/images/index/index_r5_c007.jpg"> 
                  <div align="left">
                    <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
                      <tr> 
                        <td width="6%">&nbsp;</td>
                        <td width="94%"><font color="#000066" size="3"><strong>在线课堂</strong></font></td>
                      </tr>
                    </table>
                  </div></td>
              </tr>
        </table>
        <iframe id="zxkt" name="zxkt" allowtransparency="yes" frameborder="0" width="100%" height="100%" scrolling="no" src="<%=src %>" onLoad="resize()"></iframe></td>
      </tr>
      <tr bgcolor="#9FC3FF">
        <td valign="top"><br/>
            <table width="100" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="/web/bzz_index/zxkt/images/list_t.jpg" width="206" height="70"></td>
          </tr>
          <tr>
            <td background="/web/bzz_index/zxkt/images/list_bg.jpg"><table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td height="30" id="menu0" status="off" class="cd_1-off" onMouseOver="overMenu(this)" onMouseOut="outMenu(this)" onClick="registMenu(this);window.open('bjjs.jsp','zxkt');parent.resize();">　 背景介绍</td>
              </tr>
              <tr>
                <td height="6"></td>
              </tr>
              <tr>
                <td height="30" id="menu1" status="off" class="cd_1-off" onMouseOver="overMenu(this)" onMouseOut="outMenu(this)" onClick="registMenu(this);window.open('zynr.jsp','zxkt');parent.resize();">　 主要内容</td>
              </tr>
              <tr>
                <td height="6"></td>
              </tr>
              <tr>
                <td height="30" id="menu2" status="off" class="cd_1-off" onMouseOver="overMenu(this)" onMouseOut="outMenu(this)" onClick="registMenu(this);window.open('xxfs.jsp','zxkt');parent.resize();">　 学习方式</td>
              </tr>
              <tr>
                <td height="6"></td>
              </tr>
               <tr>
                <td height="30" id="menu3" status="off" class="cd_1-off" onMouseOver="overMenu(this)" onMouseOut="outMenu(this)" onClick="registMenu(this);window.open('zbkt.jsp','zxkt');parent.resize();">　 直播课堂</td>
              </tr>
              
            </table></td>
          </tr>
          <tr>
            <td><img src="/web/bzz_index/zxkt/images/list_b.jpg" width="206" height="31"></td>
          </tr>
        </table></td>
      </tr>
      <tr bgcolor="#9FC3FF">
        <td valign="bottom"><img src="/web/bzz_index/zxkt/images/l_b.jpg" width="218" height="7"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><iframe id="foot" name="foot" frameborder="0" width="1002" height="147" scrolling="no" src="/web/bzz_index/bottom1.jsp"></iframe></td>
  </tr>
</table>


<map name="Map"><area shape="rect" coords="640,13,693,28" href="#"></map></body>
</html>
