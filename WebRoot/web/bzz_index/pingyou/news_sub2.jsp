<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.dbpool"%>
<%@page import="com.whaty.platform.database.oracle.MyResultSet"%>
<%@page import="com.whaty.platform.util.Page"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>评优表彰专栏</title>
<link rel="stylesheet" href="css/sub2.css" type="text/css" />
</head>

<body>

<%! 
	String fixNull(String str){
		if(str==null || str.equalsIgnoreCase("null")) str = "";
		return str.trim();
	}
%>

<div class="wrap">
 <div class="header">
     <div class="top"><a href="#"><img src="images/logo.jpg"></a></div>
  </div>
 <!--头部结束-->
 <div class="container">  
  <div class="contenttitle"><strong> 新闻动态</div>
   <div style="clear:both; height:0px; overflow:hidden;"></div>
  <div class="content">
  <% 
  	String sql = "";
	int current_num = 0;
	int count = 0;
	dbpool work = new dbpool();
	MyResultSet rs;	
	MyResultSet rs2;	
	
	String news_id = "";
	String news_title = "";
	String news_submit_date = "";
	String news_summary = ""; 
	
	sql = " select id, title, to_char(submit_date,'yyyy-mm-dd') as submit_date ,summary from pe_info_news where fk_news_type_id = '_pingyou' and "+
		" flag_isactive = '402880a91e4f1248011e4f1a2b360002' and flag_news_status = '402880a91e4f1248011e4f1c0ab40004' order by submit_date desc ";
	
	int totalItems = 0;
	totalItems = work.countselect(sql);
	//----------分页开始---------------
	String spagesize = (String) session.getValue("pagesize");
		String spageInt = request.getParameter("pageInt");
		Page pageover = new Page();
		pageover.setTotalnum(totalItems);
		pageover.setPageSize(spagesize);
		pageover.setPageInt(spageInt);
		int pageNext = pageover.getPageNext();
		int pageLast = pageover.getPagePre();
		int maxPage = pageover.getMaxPage();
		int pageInt = pageover.getPageInt();
		int pagesize = pageover.getPageSize();
		String link = "";
		rs = work.execute_oracle_page(sql,pageInt,pagesize);
		while(rs!=null&&rs.next()){
    		news_id = fixNull(rs.getString("id"));
			news_title = fixNull(rs.getString("title"));
			news_submit_date = fixNull(rs.getString("submit_date"));
			news_summary = fixNull(rs.getString("summary"));
			
			news_summary = "<a href='news_sub3.jsp?news_id="+news_id+"' >"+news_summary+"</a>";
			
    	%>
    		<div class="clist">
    			<div class="ctitle">新闻标题：<%=news_title %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发布时间：<%=news_submit_date %></div>
    			<div class="ctext"><span class="bfont">内容简介：</span> <%=news_summary %> </div>
  			</div>
    	<%
		}
		work.close(rs);
		
  %>
  
<div id="page_div">
  <ul class="page_num">
    <li><%@ include file="/web/bzz_index/pub/dividepage.jsp" %></li>
  </ul>
  
</div>

  </div><!--content--> 
 </div><!--container-->
 <div style="clear:both;"></div>
 <div class="foot">版权所有：清华大学</div>
</div>
</body>
</html>
