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
 
 <% 
        String sql = "";
		int current_num = 0;
		int count = 0;
		dbpool work = new dbpool();
		MyResultSet rs;	
		
		String news_id = fixNull(request.getParameter("news_id"));
		
		String news_title = "";
		String news_submit_date = "";
		String news_summary = ""; 
		String news_note = "";
		int news_read_count = 0;
		
		sql = " select read_count from pe_info_news where id = '"+news_id+"' ";
		rs = work.executeQuery(sql);
		if(rs!=null && rs.next()){
			news_read_count = rs.getInt("read_count") + 1;
		}
		work.close(rs);
		
		sql = " update pe_info_news set read_count = '"+news_read_count+"' where id = '"+news_id+"'  ";
		work.executeUpdate(sql);
		
		sql = " select title, to_char(submit_date,'yyyy-mm-dd') as submit_date ,summary , note ,read_count from pe_info_news where id = '"+news_id+"' ";
        
        rs = work.executeQuery(sql);
        if(rs!=null && rs.next()){
        	news_title = fixNull(rs.getString("title"));
        	news_submit_date = fixNull(rs.getString("submit_date"));
        	news_summary = fixNull(rs.getString("summary"));
        	news_note = fixNull(rs.getString("note"));
        	news_read_count = rs.getInt("read_count");
        }
        work.close(rs);
        
        String photo_one = "";
        if("".equals(photo_one)){
        	photo_one = "/entity/manager/basic/goodStuMagImage/noImage.jpg";
        }else{
        	photo_one = "/entity/manager/basic/goodStuMagImage/goodMag/"+photo_one;
        }
        
        %>
        
 <div class="container">
   <div class="ctntop">
     <div class="ctntoptitle"><div class="titletext">新闻：<%=news_title %></div></div>
   </div>
   <div class="ctncenter">
    <div style="clear:both; height:0px; overflow:hidden;"></div>
    <div style="clear:both; height:0px; overflow:hidden;"></div>
   
   <div class="sujx">
     <div class="titlebox">内容简介：<%=news_summary %></div>
      <div class="textbox">
      <div class=" texttitle">详细内容：</div>
       <div class="textp">
       <p>
       	<%=news_note %><br />
       </p>
       </div>
      
       </div>
    	
       <div  align="right">
       		发布时间：<%=news_submit_date %>
			<span onClick="window.print()" class="print">【打 印】</span>　
			【点击次数：<%=news_read_count %>】
	   </div>
       
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


