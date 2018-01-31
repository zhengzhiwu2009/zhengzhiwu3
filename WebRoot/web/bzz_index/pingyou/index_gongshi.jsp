<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.dbpool"%>
<%@page import="com.whaty.platform.database.oracle.MyResultSet"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>中国证劵业协会培训平台-评优表彰专栏</title>
<link href="css/style.css" rel="stylesheet" type="text/css">

<script language="JavaScript">
<!--
var sf = new Number();
function aa()
{clearInterval(sf);sf=setInterval('a1.scrollBy(1,2)',1);}
function bb()
{clearInterval(sf);sf=setInterval('a1.scrollBy(1,-2)',1);}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
</head>

<%! 
	String fixNull(String str){
		if(str==null || str.equalsIgnoreCase("null")) str = "";
		return str.trim();
	}
%>

<body>
<div id="body">
<div id="big">
 
  <div id="header">
     <div class="top"><a href="#"><img src="images/logo.jpg"></a></div>
     <div class="top1">
        <div class="top_l"></div>
        <div class="top_r"> 
           <p>&nbsp;
           </p>
        </div>
     </div>
  </div>
 <!--头部结束-->
 <!--  <div id="contenter">
   <div class="con1">
   <div class="ctitle">
   <a href="news_sub2.jsp" class="more">更多>></a>
   <div class="title">新闻动态</div>
   
   </div>
     <div class="con1text">
      <div class="imgleft"><img src="images/1.jpg"  width="224" height="129" border="0" /></div>
      <div class="newlist">
        <div class="listtitle">中央企业班组长岗位管理能力与资格认证培训首期评优表彰</div>
        <div class="listtext">
        <ul>
        
        <% 
        String sql = "";
		int count = 0;
		dbpool work = new dbpool();
		MyResultSet rs;	
		
		String news_id = ""; 
		String news_title = "";
		
		sql = " select id, title from pe_info_news where fk_news_type_id = '_pingyou' and flag_isactive = '402880a91e4f1248011e4f1a2b360002' "+
			" and flag_news_status = '402880a91e4f1248011e4f1c0ab40004' order by submit_date desc ";
		rs = work.executeQuery(sql);
		//System.out.println(sql);
		count = 0;
		while(rs!=null && rs.next()){
			count ++;
			if(count < 5){
				news_id = fixNull(rs.getString("id"));
				news_title = fixNull(rs.getString("title"));
				%>
				<li><a href="news_sub3.jsp?news_id=<%=news_id %>" target="_blank" ><%=news_title %></a></li>
				<%
			}	
		}
		work.close(rs);
        
        %>
        </ul>
        </div>
      </div>
       <div class="clear"></div>
     </div>
    
   </div>    -->
   
     <div class="con3">
  <div class="ctitle">
   <a href="sub2_gongshi.jsp" target="_blank" class="more">更多>></a>
   <div class="title"><strong> 优秀学员</strong><span class="font_s">（排名不分先后）</span></div>
   </div>
   <div class="con3text">
    <div class="con3left"><img src="images/p_23_1.jpg" width="342" height="278" border="0" /></div>
    <div class="con3right">
        <div><a href="javascript:;"onMouseDown="bb(this)" onMouseOut="clearInterval(sf)"><img src="images/p_21.jpg" width="45" height="30"  border="0" id="divUpControl" style="margin-left:240px;" /></a></div>
<div>
							  <iframe width="600" height="240"  id="a1" name="a1" allowtransparency="yes" frameborder="0" scrolling="no" src="gd3.jsp"></iframe>
							</div>
					  <div><a href="javascript:;"onMouseDown="aa(this)" onMouseOut="clearInterval(sf)"><img src="images/p_25.jpg"  width="45" height="30"  border="0" id="divDownControl" style="margin-left:240px;"></a></div>
    </div>
    <div class="clear"></div>
   </div>
  </div>      
    
        
  <div class="con2">
   <!--  <div class="flash"><img src="images/p_03.jpg" border="0" width="975" height="237" /></div>   --> 
     <div class="content2">
       <div class="c_2left">
         <div class="tit"><a href="zj_sub2_gs.jsp" target="_blank" class="more">更多>></a><strong> 最佳管理员</strong><span class="font_s">（排名不分先后）</span></div>
         <div class="ctncroll">
           <iframe id="gd2" name="gd2" src="gd2.jsp" frameborder="0" width="100%" height="330" scrolling="no"></iframe>
         </div>
       </div>
       
       <div class="c_2right">
    
         <div class="tit"><a href="yx_sub2_gs.jsp" target="_blank" class="more">更多>></a><strong> 优秀管理员</strong><span class="font_s">（排名不分先后）</span></div>
         <div class="ctncroll"><iframe id="gd1" name="gd1" src="gd1.jsp" frameborder="0" width="100%" height="330" scrolling="no"></iframe></div>
       </div>
     </div>
  <div class="clear"></div>
  </div>  <!--con2   -->
  

 
 
 </div><!--content-->           
        
        <!---->
      <!--班组长学员结束-->


 <div class="foot">版权所有：清华大学</div>
</div>
</div>
</body>
</html>

