<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.dbpool"%>
<%@page import="com.whaty.platform.database.oracle.MyResultSet"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>评优表彰专栏</title>
<link href="css/sub3.css" rel="stylesheet" type="text/css" />
</head>

<%! 
	String fixNull(String str){
		if(str==null || str.equalsIgnoreCase("null")) str = "";
		return str.trim();
	}
%>

<body>
<body>
<div class="wrap">
 <div class="header">
     <div class="top"><a href="#"><img src="images/logo.jpg"></a></div>
  </div>
 <!--头部结束-->
 <div class="container">
   <div class="ctntop">
     <div class="ctntoptitle"><div class="titletext">最佳管理员的个人风采展示</div></div>
   </div>
   <div class="ctncenter">
        <% 
        String sql = "";
		int current_num = 0;
		int count = 0;
		dbpool work = new dbpool();
		MyResultSet rs;	
		
		String goodMag_id = fixNull(request.getParameter("goodMag_id"));
		
		String true_name = "";
		String gender = "";
		String enterprise_name = "";
		String work_space = "";
		String photo_one = "";
		String feeling_words = "";
		String advanced_story = "";
		String certificate = "";
		
		int regard_num = 0;
		sql = " select regard_num from pe_bzz_goodmag where id = '"+goodMag_id+"' ";
		rs = work.executeQuery(sql);
		if(rs!=null && rs.next()){
			regard_num = rs.getInt("regard_num") + 1;
		}
		work.close(rs);
		
		sql = " update pe_bzz_goodmag set regard_num = '"+regard_num+"' where id = '"+goodMag_id+"' ";
		work.executeUpdate(sql);
        
        sql = " select b.name, d.name as gender, c.name as enterprise_name, a.work_space, a.photo_one , a.regard_num , a.feeling_words , a.advanced_story , a.certificate from pe_bzz_goodmag a, pe_enterprise_manager b, pe_enterprise c ,"+
        	" enum_const d where a.fk_managerid = b.id and b.fk_enterprise_id = c.id(+) and b.gender = d.id(+) and a.id = '"+goodMag_id+"' ";
        
        rs = work.executeQuery(sql);
        if(rs!=null && rs.next()){
        	true_name = fixNull(rs.getString("name"));
        	gender = fixNull(rs.getString("gender"));
        	enterprise_name = fixNull(rs.getString("enterprise_name"));
        	work_space = fixNull(rs.getString("work_space"));
        	photo_one = fixNull(rs.getString("photo_one"));
        	regard_num = rs.getInt("regard_num");
        	feeling_words = fixNull(rs.getString("feeling_words"));
        	advanced_story = fixNull(rs.getString("advanced_story"));
        	certificate = fixNull(rs.getString("certificate"));
        }
        work.close(rs);
        
        if("".equals(photo_one)){
        	photo_one = "/entity/manager/basic/goodStuMagImage/noImage.jpg";
        }else{
        	photo_one = "/entity/manager/basic/goodStuMagImage/goodMag/"+photo_one;
        }
        
        %>
   <div class="message">
        <div class="imgleft">
        
        <img src="<%=photo_one %>" width="115" height="169" border="0" /></div>
        <div class="tabble">
        <table width="820" border="0" cellspacing="1" cellpadding="0" bgcolor="#d8d8d8" >     
  <tr>
    <td width="98" height="44" bgcolor="#fffae3" align="center" ><span class="red">姓名：</span></td>
    <td width="92" bgcolor="#FFFFFF" align="center"><%=true_name %></td>
    <td width="98" bgcolor="#fffae3" class="red" align="center"> 性别：</td>
    <td width="69" bgcolor="#FFFFFF" align="center"> <%=gender %></td>
    <td width="126" bgcolor="#fffae3" class="red" align="center">所在企业：</td>
    <td width="330" bgcolor="#FFFFFF"><%=enterprise_name %></td>
  </tr>
  <tr>
    <td height="44" colspan="6" bgcolor="#FFFFFF"><span class="red">工作单位：</span> <%=work_space %></td>
  </tr>
   <tr>
    <td height="76" colspan="6" bgcolor="#FFFFFF"><span class="red">个人感言：</span> <%=feeling_words %></td>    
  </tr>
</table>
        </div>
     </div>
    <div style="clear:both; height:0px; overflow:hidden;"></div>
      <div class="grsj">
        <div class="titlebox">个人事迹</div>
        <div class="textbox">
           <%=advanced_story %>
        </div>
   
      </div>
   
    <div style="clear:both; height:0px; overflow:hidden;"></div>
   
   <div class="sujx">
     <div class="titlebox">所获奖项</div>
      <div class="textbox">
      <%=certificate %>
       </div>
      </div>
      
       <div  align="right">
			<span onClick="window.print()" class="print">【打 印】</span>　
			【点击次数：<%=regard_num %>】&nbsp;&nbsp;&nbsp;
	   </div>
     
  
  <div class="button"><a href="javascript:window.close();"><img src="images/sub3_21.jpg" width="62" height="28" /></a></div> 
   
   </div><!--ctncenter-->
  
   <div style="clear:both; height:0px; overflow:hidden;"></div>
   <div class="ctnbottom"><img src="images/sub3_24.jpg" width="988" height="21" border="0" /></div>
 </div>
 
 
  <div class="foot">版权所有：清华大学</div>
 
 </div><!--wrap-->
</body>
</html>


