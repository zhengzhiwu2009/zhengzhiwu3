<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<Script>
  function sub(){
  var data=document.all.checkBox;
   for(var i=0;i<data.length;i++){
     if(data[i].checked){
       form1.submit();
       return;
     }
   }
   alert("请选择要统计的项目");
   return;
  }
</Script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>注册统计</title>
<link href="./css/css.css" rel="stylesheet" type="text/css">
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
</head>
<body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">
<div id="main_content">
    <div class="content_title">注册统计</div>
    <div class="cntent_k">
   	  <div class="k_cc">
   	  <form action="/entity/statistics/reStatistics.action" name="form1">
			<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td height="30"></td>
					</tr>
                    <tr>
                        <td colspan="2" align="center" class="postFormBox" valign="top" >注册统计</td>
                    </tr>
                      
                    <tr valign="middle"> 
                       <td width="200" height="90" align="left" class="postFormBox"><span class="name">请选择您要统计的类别：</span></td>
                       <td class="postFormBox" style="padding-left:18px">
                       	<div class="postFormBox"><input type="radio" name="checkBox" value="site_name" >&nbsp;&nbsp;按注册类别</div>
                       	<!--  
                       	<div class="postFormBox"><input type="radio" name="checkBox" value="sitemanager" >&nbsp;&nbsp;信息采集人</div>
                       	<div class="postFormBox"><input type="radio" name="checkBox" value="gsd" >&nbsp;&nbsp;归属地</div>
                       	-->
                       </td>
               	    </tr>
                    <tr valign="middle"> 
                          <td width="200" height="60" align="left" class="postFormBox"></td>
                          <td class="postFormBox" style="padding-left:18px">
                          	<input type="button" value="确定" onclick=sub();><br/>
                          </td>
                   	</tr>
                           
			   		<tr>
                         <td  height="10"> </td>
                    </tr>
        	</table>
	</form>
	  </div>
    </div>
</div>
<div class="clear"></div>
</body>
</html>

