<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.dbpool"%>
<%@page import="com.whaty.platform.database.oracle.MyResultSet"%>
<%@page import="com.whaty.platform.util.Page"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>中国证劵业协会培训平台-评优表彰结果公示</title>
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
  <div class="contenttitle"><strong> 优秀管理员</strong><span class="font_s">（排名不分先后）</span></div>
   <div style="clear:both; height:0px; overflow:hidden;"></div>
  <div class="content">
  <% 
  	String sql = "";
	int current_num = 0;
	int count = 0;
	dbpool work = new dbpool();
	MyResultSet rs;	
	MyResultSet rs2;	
	
	String enterprise_id = "";
	String enterprise_name = "";
	String mag_name = ""; 
	String goodMag_id = "";
	
	sql = " select distinct c.id as enterprise_id , c.name as enterprise_name,c.code from pe_bzz_goodmag a, pe_enterprise_manager b, pe_enterprise c "+
        	" where a.fk_managerid = b.id and b.fk_enterprise_id = c.id and b.is_goodmag = 'ccb2880a91dadc115011dadfcf26b0011' and "+
        	" a.submit_status = 'ccb2880a91dadc115011dadfcf26b0005' order by c.code  ";
	
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
			mag_name = "";
    		enterprise_id = fixNull(rs.getString("enterprise_id"));
			enterprise_name = fixNull(rs.getString("enterprise_name"));
    	
    		sql = " select a.id as goodMag_id , b.name as mag_name from pe_bzz_goodmag a, pe_enterprise_manager b where a.fk_managerid = b.id and "+
        		" a.submit_status = 'ccb2880a91dadc115011dadfcf26b0005' and b.is_goodmag = 'ccb2880a91dadc115011dadfcf26b0011' and "+
        		" b.fk_enterprise_id = '"+enterprise_id+"' order by name ";
        		
    		rs2 = work.executeQuery(sql);
    		while(rs2!=null && rs2.next()){
    			goodMag_id = fixNull(rs2.getString("goodMag_id"));
    			mag_name = mag_name + "<a href='yx_sub3.jsp?goodMag_id="+goodMag_id+"'  >"+fixNull(rs2.getString("mag_name"))+"</a>&nbsp;&nbsp;";
    		}
    		work.close(rs2);	
    	%>
    		<div class="clist">
    			<div class="ctitle">企业名称：<%=enterprise_name %></div>
    			<div class="ctext"><span class="bfont">管理员：</span> <%=mag_name %> </div>
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
