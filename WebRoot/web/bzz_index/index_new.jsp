<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.ParseException"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="com.whaty.platform.database.oracle.*" %>
<%@ taglib uri="oscache" prefix="cache" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%!
boolean isDateBefore(String date2)   
{   
      try{   
    Date date1 = new Date();
    //DateFormat df = DateFormat.getDateTimeInstance();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //System.out.println(date1.before(df.parse(date2)));   
    return date1.before(df.parse(date2));    
    }catch(ParseException e){   
    System.out.print(e.getMessage());   
    return false;   
    }   
}
  
boolean isDateAfter(String date2){   
    try{   
        Date date1 = new Date();   
        //DateFormat df = DateFormat.getDateTimeInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //System.out.println(date1.after(df.parse(date2)));   
        return date1.after(df.parse(date2));    
    }catch(ParseException e){   
        System.out.print(e.getMessage());   
        return false;   
    }   
}   

%>
<%
dbpool db = new dbpool();
MyResultSet rs = null;
%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>中国证券业协会远程培训系统</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="班组长培训,企业班组长,岗位管理能力资格认证课堂">
	<meta http-equiv="description" content="班组长培训,企业班组长,岗位管理能力资格认证课堂">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="/web/bzz_index/index_new/css/css.css" />
	<link rel="stylesheet" type="text/css" href="/web/bzz_index/index_new/css/reset.css" />
	<script src="/web/bzz_index/index_new/js/jquery-1.6.1.min.js" type="text/javascript"></script>
	<script src="/web/bzz_index/index_new/js/slider.js" type="text/javascript"></script>
	<script src="/web/bzz_index/index_new/js/msclass.js" language="javascript"></script>
	<script type="text/javascript" src="/js/jquery.cookies.2.2.0.js"></script>
<script>
    $(function () {
        $("#nav ul li").click(function () {
            $("#nav ul li").removeClass("first");
            $(this).addClass("first");
        });
    });
</script>
 <script type="text/javascript">
					$(function(){
						var tabTitle = ".tab dl dt a";
						var tabContent = ".tab dl ul";
						$(tabTitle + ":first").addClass("on");
						$(tabContent).not(":first").hide();
						$(tabTitle).unbind("click").bind("click", function(){
							$(this).siblings("a").removeClass("on").end().addClass("on");
							var index = $(tabTitle).index( $(this) );
							$(tabContent).eq(index).siblings(tabContent).hide().end().show();
					   });
					});
</script>
 <script type="text/javascript">
					$(function(){
						var tabTitle = ".tab2 dl dt a";
						var tabContent = ".tab2 dl ul";
						$(tabTitle + ":first").addClass("on");
						$(tabContent).not(":first").hide();
						$(tabTitle).unbind("click").bind("click", function(){
							$(this).siblings("a").removeClass("on").end().addClass("on");
							var index = $(tabTitle).index( $(this) );
							$(tabContent).eq(index).siblings(tabContent).hide().end().show();
					   });
					});
</script>
 <script type="text/javascript">
					$(function(){
						var tabTitle = ".tab3 dl dt a";
						var tabContent = ".tab3 dl ul";
						$(tabTitle + ":first").addClass("on");
						$(tabContent).not(":first").hide();
						$(tabTitle).unbind("click").bind("click", function(){
							$(this).siblings("a").removeClass("on").end().addClass("on");
							var index = $(tabTitle).index( $(this) );
							$(tabContent).eq(index).siblings(tabContent).hide().end().show();
					   });
					});
</script>
<script type="text/javascript">
//    auto:[false,3000]   这里是个数组，第一个是否自动滚动,第二个是自动滚动间隔时间
//    visible:4    可显示图片的数量
//    speed:1000   动画时间，可选slow，fast，数值类
//    scroll:1     每次切换的个数,此数小于等于visible值
//        //手动滚动
//        $("#product0").myScroll({
//            visible: 3,
//            scroll: 2,
//            speed: 1000
//        });

        //自动滚动
        $(function () {
            $("#scDiv").myScroll({
                visible: 4,
                scroll: 2,
                auto: [true, 1000],
                speed: 800
            });
        });
</script>
<script type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
<script type="text/javascript">
$(function(){
	showKeFu('xixi',100,10);
	myonload();
});

function showKeFu(id,_top,_left){
	var me=id.charAt?document.getElementById(id):id, d1=document.body, d2=document.documentElement;
	d1.style.height=d2.style.height='100%';me.style.top=_top?_top+'px':0;me.style.left=_left+"px";
	me.style.position='absolute';
	setInterval(function (){me.style.top=parseInt(me.style.top)+(Math.max(d1.scrollTop,d2.scrollTop)+_top-parseInt(me.style.top))*0.1+'px';},10+parseInt(Math.random()*20));
	return arguments.callee;
}

function myonload() {
MM_preloadImages('/web/bzz_index/images/anniu_over_03.jpg','/web/bzz_index/images/anniu_over_05.jpg');
<s:if test="loginErrMessage!=null&& loginErrMessage!='clear'">
	// window.document.loginform.loginType.value="<s:property value='loginType'/>";
	window.document.loginform.loginId.value="<s:property value='loginId'/>";
	window.alert("<s:property value='loginErrMessage'/>");
</s:if>
}

function goUrl()
{	
	window.open(document.form1.select.value,"newwindow");
}

function window.onunload() {
	if(window.screenLeft>10000|| window.screenTop>10000){ 
			var url="/sso/login_close.action?loginErrMessage=clear";
			if (window.XMLHttpRequest) {
		        req = new XMLHttpRequest();
		    }
		    else if (window.ActiveXObject) {
		        req = new ActiveXObject("Microsoft.XMLHTTP");
		    }
		    req.open("Get",url,true);
		    req.onreadystatechange = function(){
		    	if(req.readyState == 4);
		    };
	  		req.send(null);
  		}
}

$(function() {
	var re = $.cookies.get( "REMEMBER_ME_PASSWORD" );
	if( re!=null && re!="" && re!="null") {
	      $("[name = rememberMe]:checkbox").attr("checked", true);
		  $("#password").attr("value","00000000") ;
	  }
	  var lo=$.cookies.get("REMEMBER_ME_LOGINID");
	  if(lo!=null&&""!=lo) {
		  $("#loginId").attr("value",lo);
	  }
});
function updateInputState() {
	$("#passwdInput").attr("value","1");
}

</script>
</head>

<body>
<%
if(isDateAfter("2011-10-16 00:00:00") ){  // 添加 评优 浮动链接
	%>
	<div id="pic1" style="position:absolute; visibility:visible; left:0px; top:0px; z-index:5; width:200; height: 68;background-color:#FF1111;">
<!-- <a href="web/bzz_index/pingyou/index_gongshi.jsp" target="_blank">
<img src="web/bzz_index/images/pingyou_gs.jpg" height="75" border="0" />
</a> -->

<div style="heigth:68px;font-size:x-large; font-weight: bold;">
<a href="http://www.thbzzpx.org/entity/first/firstBulletin_viewDetail.action?bean.id=ff80808136ab4b180136c8a2659c47a7" target="_blank" style="color:#000000;text-decoration:none;">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2012年第四期<br/>报名通知红头文件
</a>
</div>
<!-- <a href="http://www.thbzzpx.org/entity/first/firstBulletin_viewDetail.action?bean.id=ff808081345a06520134828b3c2d5498" target="_blank">
<img src="web/bzz_index/images/confirm_info.jpg" height="75" border="0" />
</a> -->
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
<%
}
%>
<div style=" margin:0 auto; width:1002px; height:181px">
<iframe src="/web/bzz_index/index_new/iframe/top.jsp" frameborder="0" width="1002" height="181" scrolling="no"></iframe> </div>

<!--container_1 start-->
<div id="container_1">
<!--首页公告Announcement start-->
<div id="Announcement">
<div class="title_1"><div class="more"><a href="#">更多>></a></div><h1>公告</h1></div>
<ul>
<s:if test="Bulletin.size() > 0">
 	<s:append id="list3">
		<s:param value="Bulletin"></s:param>	
		<s:param value="{0,0,0,0,0}"></s:param>
	</s:append>
</s:if>
<s:else>
	<s:append id="list3">
		<s:param value="{0,0,0,0,0,0,0}"></s:param>
	</s:append>
</s:else>
<s:iterator id="BulletinList" value="#list3" status="bulletin">
	<s:if test="#bulletin.getIndex()<6">
		<s:if test="#this==0">
			
		</s:if>
		<s:else >
			<li style="text-align:left;"><a href="/entity/first/firstBulletin_viewDetail.action?bean.id=<s:property value="id"/>" target="_blank""><s:if test='%{enumConstByFlagIsNew.code.equals("0")}'><span style="color:#FF0000">【new】</span><s:if test="title.length()>14"><s:property value="title.substring(0,14)" />...</s:if><s:else><s:property value="title" /></s:else></s:if><s:else><s:if test="title.length()>18"><s:property value="title.substring(0,18)" />...</s:if><s:else><s:property value="title" /></s:else></s:else></a></li>
		</s:else>
	</s:if>
</s:iterator>
</ul>
</div>
<!--Announcement end-->
<!--新闻动态 Dynamic news start-->
<div id="Dynamic_news">
<div class="title_2"><div class="more2"><a href="#">更多>></a></div><h1>新闻动态</h1></div>
<div style="clear:both"></div>
<!-- 图片新闻动态开始 -->
<div class="pic">
<%--<img src="/web/bzz_index/index_new/images/pic.jpg" width="265" height="163" />
--%>
<table style="margin-top: -10px;width:265" border="0" cellspacing="0" cellpadding="0">
                <s:if test="tpnewsList.size()>0">
                  <s:iterator id="tpnewsList" value="tpnewsList" status="tpnews">
                	<input id="pic" type="hidden" name="pic" value="<s:property value="pictrue"/>"/>
                	<input id="title" type="hidden" name="title" value="<s:if test="title.length()>16"><s:property value="title.substring(0,16)" />...</s:if><s:else><s:property value="title" /></s:else>"/>
                	<input id="pid" type="hidden" name="pid" value="<s:property value="id"/>"/>
                </s:iterator>
                </s:if><s:else>
                	<input id="pic" type="hidden" name="pic" value=""/>
                	<input id="title" type="hidden" name="title" value=""/>
                	<input id="pid" type="hidden" name="pid" value=""/>
                </s:else>
            <script type="text/javascript"> 
            		var basePath="<%=basePath%>"
                    var pic =document.getElementsByName("pic").length;
                    var pics = basePath+document.getElementsByName("pic")[0].value+"|";
                    var purl ="/entity/first/firstInfoNews_toInfoNews.action?bean.id=";
                    var links = purl+document.getElementsByName("pid")[0].value+"|";
                    var texts= document.getElementsByName("title")[0].value.substring(0,15)+"|";
            		for(var k=1;k<pic;k++){
            			pics = basePath+document.getElementsByName("pic")[k].value+"|"+pics;
            			links =purl+document.getElementsByName("pid")[k].value+"|"+links;
            			texts =document.getElementsByName("title")[k].value+"|"+texts
            		}
            				
            		pics = pics.substring(0,pics.length-1);
                    links= links.substring(0,links.length-1);     
                	texts= texts.substring(0,texts.length-1);
					var focus_width=250;
					var focus_height=165;
					var text_height=20;
					//alert("pics:"+pics+"texts:"+texts );
					var swf_height = focus_height+text_height;
					document.write('<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0" width="'+ focus_width +'" height="'+ swf_height +'">');
					document.write('<param name="allowScriptAccess" value="sameDomain"><param name="movie" value="/web/bzz_index/images/pixviewer.swf"><param name="quality" value="high"><param name="bgcolor" value="#F0F0F0">');
					document.write('<param name="menu" value="false"><param name=wmode value="opaque">');
					document.write('<param name="FlashVars" value="pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height+'">');
					document.write('<embed src="/web/bzz_index/images/pixviewer.swf" wmode="opaque" FlashVars="pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height+'" menu="false" bgcolor="#F0F0F0" quality="high" width="'+ focus_width +'" height="'+ focus_height +'" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />');  
					document.write('</object>');
				</script>
                  
                </table>
</div>
<!-- 图片新闻动态结束 -->
<!-- 文字新闻动态开始 -->
<ul>
<s:iterator id="newsList" value="newsList" status="news">
	<li style="text-align:left;"><a href="/entity/first/firstInfoNews_toInfoNews.action?bean.id=<s:property value="id"/>" target="_blank"> <s:if test="title.length()>20"><s:property value="title.substring(0,20)" />...</s:if><s:else><s:property value="title" /></s:else></a></li>
</s:iterator>
</ul>
<!-- 文字新闻动态结束 -->
</div>
<!--新闻动态结束Dynamic news end-->
<!--Login area start-->
<div id="Login_area">
<form id="loginform" name="loginform" method="post" action="/sso/login_login.action" target="_self" >
<p><span class="sp1">账 号：</span><span><input name="loginId" id="loginId" type="text" class="input_1"/></span></p>
<p><span class="sp1">密 码：</span><span><input name="passwd" id="password" type="password" class="input_1" onKeyUp="updateInputState()"/></span></p>
<p><span class="sp1">验证码：</span><span><input name="authCode" type="text" class="input_2" /></span><span class="sp2"><img src="/sso/authimg" width="51" height="20" onclick="this.src=this.src+'?'+(new Date()).getTime()" /></span></p>
<div class="button"><input type="image" src="/web/bzz_index/index_new/images/login.jpg" /><a href="/web/bzz_index/czmm/find.jsp" target="_blank"><img src="/web/bzz_index/index_new/images/reset.jpg" width="82" height="26" /></a></div>
<div class="key"><span class="sp10"><input type="checkbox" name="rememberMe" value="rememberMe" id="remeberMe" class="input_3" style="border:0; border:none"/></span><span class="sp11">记住我</span><span class="sp12"><a href="/password/getPsw_identify.action" target="_blank">忘记密码？</a></span></div>
</form>
</div>
<!--Login area end-->
<!--Service area start-->
<div id="Service_area">
<div class="title_1"><h1>服务专区</h1></div>
<ul>
<li><a href="#"><img src="/web/bzz_index/index_new/images/1.jpg" width="42" height="43" /><h1>如何报名</h1></a></li>
<li><a href="#"><img src="/web/bzz_index/index_new/images/2.jpg" width="42" height="43" /><h1>如何学习</h1></a></li>
<li><a href="#"><img src="/web/bzz_index/index_new/images/3.jpg" width="42" height="43" /><h1>学员通道</h1></a></li>
<li><a href="#"><img src="/web/bzz_index/index_new/images/4.jpg" width="42" height="43" /><h1>在线咨询</h1></a></li>
<li><a href="#"><img src="/web/bzz_index/index_new/images/5.jpg" width="42" height="43" /><h1>成绩查询</h1></a></li>
<li><a href="#"><img src="/web/bzz_index/index_new/images/6.jpg" width="42" height="43" /><h1>证书查询</h1></a></li>
<li><a href="#"><img src="/web/bzz_index/index_new/images/7.jpg" width="42" height="43" /><h1>表格下载</h1></a></li>
<li><a href="#"><img src="/web/bzz_index/index_new/images/8.jpg" width="42" height="43" /><h1>软件下载</h1></a></li>
<li><a href="#"><img src="/web/bzz_index/index_new/images/9.jpg" width="42" height="43" /><h1>联系我们</h1></a></li>
</ul>
</div>
<!--Service area end-->
<!--Learning to rank start-->
<div id="Learning_to_rank">
  <div class="tab">
    <dl>
      <dt>
        <div class="title_3"><a>学习人数</a><a>平均学时</a><a>二期考试排名</a><a>交流排名</a><a>管理排名</a></div>
        <h1>学习排名</h1>
      </dt>
      <dd>
        <ul>
          <div class="title_4"><span class="sp4">名次</span> <span class="sp5">企业名称</span><span class="sp6">学习人数</span></div>
          <%
          //学习人数开始
          String sql_study_stu_num = " select id,name,stu_num,rownum from ( \n"+
        	  " select pe.id,pe.name,nvl(c.stu_num,0) as stu_num,rownum from pe_enterprise pe, \n"+
        	  " ( \n"+
        	  " --按一级企业分组学员 \n"+
        	  " select pe.fk_parent_id,sum(b.stu_num) as stu_num from pe_enterprise pe, \n"+
        	  " ( \n"+
        	  " --按二级企业分组学员 \n"+
        	  " select pbs.fk_enterprise_id,count(pbs.id) as stu_num from pe_bzz_student pbs,sso_user su \n"+
        	  " where pbs.fk_sso_user_id=su.id and pbs.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and su.flag_isvalid='2' group by pbs.fk_enterprise_id \n"+
        	  " ) b \n"+
        	  " where pe.id=b.fk_enterprise_id group by pe.fk_parent_id \n"+
        	  " ) c  \n"+
        	  " where pe.id=c.fk_parent_id(+) and pe.fk_parent_id is null  order by stu_num desc \n"+
        	  " ) where rownum < 7";
          rs = db.executeQuery(sql_study_stu_num);
          int study_stu_num_i = 1;
          while(null != rs && rs.next()){
        	  %>
        	  <li ><a ><span class="sp7"><%= study_stu_num_i++ %></span> <span class="sp8">  <%= rs.getString("name") %> </span><span class="sp9"><%= rs.getString("stu_num") %></span></a></li>
        	  <%
          }
          db.close(rs);
          %>
        </ul>
        <ul style="display:none">
          <div class="title_4"><span class="sp4">名次</span> <span class="sp5">企业名称</span><span class="sp6">平均总学时</span></div>
          <%
          //平均学时开始
          String sql_study_time = " select id,name,avg_finished_ctime from ( \n"+
        	  " select pe.id,pe.name,nvl(a.avg_finished_ctime,0) as avg_finished_ctime from  \n"+
        	  " pe_enterprise pe, \n"+
        	  " --分组获取平均学时 \n"+
        	  " (select sss.yiji_id,round(avg(nvl(sss.finished_ctime,0)),2) as avg_finished_ctime from  \n"+
        	  " stat_study_summary sss group by sss.yiji_id) a \n"+
        	  "  where pe.id=a.yiji_id(+) and pe.fk_parent_id is null order by avg_finished_ctime desc \n"+
        	  "  ) where rownum < 7";
          rs = db.executeQuery(sql_study_time);
          int study_time_i = 1;
          while(null != rs && rs.next()){
        	  %>
        	  <li ><a ><span class="sp7"><%= study_time_i++ %></span> <span class="sp8">  <%= rs.getString("name") %> </span><span class="sp9"><%= rs.getString("avg_finished_ctime") %></span></a></li>
        	  <%
          }
          db.close(rs);
          %>
        </ul>
        <ul style="display:none">
          <div class="title_4"><span class="sp4">名次</span> <span class="sp5">企业名称</span><span class="sp6">优秀率</span></div>
          <%
          //二期考试排名开始
          String sql_exam = " select youxiu_percent,yiji_id,yiji_name from ( \n"+
        	  " select round(a.youxiu_num / b.total_num * 100, 2) as youxiu_percent,a.yiji_id,a.yiji_name from  \n"+
        	  " (--优秀学员数 \n"+
        	  " select count(*) as youxiu_num,a.yiji_id,a.yiji_name from pe_bzz_examscore pbe,pe_bzz_student pbs, \n"+
        	  " (select pe.id as erji_id,pe2.id as yiji_id,pe2.name as yiji_name from  \n"+
        	  " pe_enterprise pe,pe_enterprise pe2 where pe.fk_parent_id=pe2.id) a  \n"+
        	  " where pbe.student_id=pbs.id and pbs.fk_enterprise_id=a.erji_id and pbe.exambatch_id='ff80808132945bc80132a53212bd39d6' \n"+
        	  " and total_score >=90 group by a.yiji_id,a.yiji_name order by youxiu_num desc \n"+
        	  " ) a, \n"+
        	  " (--总人数 \n"+
        	  " select count(*) as total_num,a.yiji_id,a.yiji_name from pe_bzz_examscore pbe,pe_bzz_student pbs, \n"+
        	  " (select pe.id as erji_id,pe2.id as yiji_id,pe2.name as yiji_name from  \n"+
        	  " pe_enterprise pe,pe_enterprise pe2 where pe.fk_parent_id=pe2.id) a  \n"+
        	  " where pbe.student_id=pbs.id and pbs.fk_enterprise_id=a.erji_id and pbe.exambatch_id='ff80808132945bc80132a53212bd39d6' \n"+
        	  " group by a.yiji_id,a.yiji_name order by total_num desc \n"+
        	  " ) b where a.yiji_id=b.yiji_id order by youxiu_percent desc)  where rownum < 7";
          rs = db.executeQuery(sql_exam);
          int exam_i = 1;
          while(null != rs && rs.next()){
        	  %>
        	  <li ><a ><span class="sp7"><%= exam_i++ %></span> <span class="sp8">  <%= rs.getString("yiji_name") %> </span><span class="sp9"><%= rs.getString("youxiu_percent") %>%</span></a></li>
        	  <%
          }
          db.close(rs);
          %>
        </ul>
        <ul style="display:none">
          <div class="title_4"><span class="sp4">名次</span> <span class="sp5">企业名称</span><span class="sp6">交流次数</span></div>
          <%
          //交流排名
          String sql_question_answer = " select nvl(a.question_num,0)+nvl(b.answer_num,0) as total_num,a.enterprise_id,a.enterprise_name,rownum from \n"+
        	  " (--学员答疑 \n"+
        			  " select count(*) as question_num,p.id as enterprise_id,p.name as enterprise_name from  \n"+
        			  " pe_bzz_student pbs,exam_question_info eqi,pe_enterprise pe,pe_enterprise p   \n"+
        			  " where pbs.id=eqi.submituser_id and pbs.fk_enterprise_id=pe.id and pe.fk_parent_id=p.id \n"+
        			  " group by p.id,p.name order by question_num desc) a, \n"+
        			  " (--学员回复答疑 \n"+
        			  " select count(*) as answer_num,p.id as enterprise_id,p.name as enterprise_name from  \n"+
        			  " pe_bzz_student pbs,exam_answer_info eai,pe_enterprise pe,pe_enterprise p   \n"+
        			  " where pbs.id=eai.publish_id and pbs.fk_enterprise_id=pe.id and pe.fk_parent_id=p.id \n"+
        			  " group by p.id,p.name order by answer_num desc) b \n"+
        			  " where a.enterprise_id=b.enterprise_id(+) and rownum < 7 order by total_num desc";
          rs = db.executeQuery(sql_question_answer);
          int question_answer_i = 1;
          while(null != rs && rs.next()){
        	  %>
        	  <li ><a ><span class="sp7"><%= question_answer_i++ %></span> <span class="sp8">  <%= rs.getString("enterprise_name") %> </span><span class="sp9"><%= rs.getString("total_num") %></span></a></li>
        	  <%
          }
          db.close(rs);
          %>
        </ul>
        <ul style="display:none">
          <div class="title_4"><span class="sp4">名次</span> <span class="sp5">企业名称</span><span class="sp6">管理次数</span></div>
          <%
          //管理排名开始
          String sql_manage = " select a.id,a.total_num,b.name from ( \n"+
        	  " select a.id,sum(b.total_num) as total_num from  \n"+
        	  " (select nvl(fk_parent_id,id) as id,id as sub_id from pe_enterprise ) a, \n"+
        	  " (--企业下管理员登录和发布公告数之和 \n"+
        	  " select a.fk_enterprise_id as enterprise_id,round(nvl(a.login_num,0) / 10, 0) + nvl(b.bulletin_num,0) as total_num from  \n"+

        	  " (--企业下管理员登录次数 \n"+
        	  " select pem.fk_enterprise_id,sum(nvl(su.login_num,0)) as login_num from pe_enterprise_manager pem,sso_user su  \n"+
        	  " where pem.fk_sso_user_id=su.id group by fk_enterprise_id) a, \n"+
        	  " (--企业下管理员发布的公告数 \n"+
        	  " select count(*) as bulletin_num,pem.fk_enterprise_id from pe_bulletin pb,pe_enterprise_manager pem  \n"+
        	  " where pem.id=pb.fk_enterprisemanager_id(+) and pb.fk_enterprisemanager_id is not null group by fk_enterprise_id) b  \n"+

        	  " where a.fk_enterprise_id=b.fk_enterprise_id(+) order by total_num desc ) b  \n"+
        	  " where a.sub_id=b.enterprise_id group by id order by total_num desc ) a, pe_enterprise b  \n"+
        	  " where a.id=b.id and b.fk_parent_id is null and rownum < 7";
          rs = db.executeQuery(sql_manage);
          int manage_i = 1;
          while(null != rs && rs.next()){
        	  %>
        	  <li ><a ><span class="sp7"><%= manage_i++ %></span> <span class="sp8">  <%= rs.getString("name") %> </span><span class="sp9"><%= rs.getString("total_num") %></span></a></li>
        	  <%
          }
          db.close(rs);
          %>
        </ul>
      </dd>
    </dl>
  </div>
</div>
<!--Learning to rank end-->

<!--Popular courses start-->
<div id="Popular_courses">
<div class="title_1"><div class="more3"><a href="#">更多>></a></div><h1>热门课程<span class="sp3">(按点击排行)</span></h1></div>
<div class="img2"><img src="/web/bzz_index/index_new/images/pic3.jpg" /></div>
<ul>
<%
//热门课程
String sql_course = " select id,name,clicks,rownum from ( \n"+
	" select pbtc.id,pbtc.name,nvl(b.clicks,0) as clicks,rownum from pe_bzz_tch_course pbtc, \n"+
	" ( \n"+
	" --按课程点击分组 \n"+
	" select fk_course_id,sum(week_click) as clicks from pe_bzz_tch_course_click group by fk_course_id \n"+
	" ) b \n"+
	" where pbtc.id=b.fk_course_id(+) order by clicks desc \n"+
	" )where rownum < 6";
rs = db.executeQuery(sql_course);
int course_i = 1;
while(null != rs && rs.next()){
%>
<li class="li_<%= course_i++ %>" style="text-align:left;"><a href="#"> <%= rs.getString("name") %></a><span><%= rs.getString("clicks") %></span></li>
<%	
}
db.close(rs);
%>
</ul>
</div>
<!--Popular courses end-->
<div style="clear:both"></div>
</div>
<!--container_1 end-->

<!--banner start-->
<div id="banner">
  <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="994" height="90">
    <param name="movie" value="/web/bzz_index/index_new/images/flash.swf" />
    <param name="quality" value="high" />
    <embed src="/web/bzz_index/index_new/images/flash.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="994" height="90"></embed>
  </object>
</div>
<!--banner end-->



<!--container_2 start-->
<div id="container_2">
<!--Teacher style start-->
<div id="Teacher_style">
<div class="title_1"><div class="more"><a href="#">更多>></a></div><h1>名师风采</h1></div>


  <iframe id="gd2" name="gd2" src="/web/bzz_index/index_new/iframe/gd2.jsp" frameborder="0" width="100%" height="130" scrolling="no"></iframe>                      



</div>
<!--Teacher style end-->
<!--Excellent courses start-->
<div id="Excellent_courses">

<div class="tab2">
            <dl>
              <dt style="text-align:left;"><a>精品课程</a><!-- <a>课程体系</a> --></dt>
              <dd>
              <table style="margin-top:20px;" width="502" align="center" border="0" cellspacing="0" cellpadding="0">
					  <tr>
						<td width="144" class="bzz1105img"><img src="/web/bzz_index/images/bzz1105_07.jpg" /></td>
						<td>&nbsp;</td>
						<td width="144" class="bzz1105img"><img src="/web/bzz_index/images/bzz1105_09.jpg" /></td>
						<td>&nbsp;</td>
						<td width="144" class="bzz1105img"><img src="/web/bzz_index/images/bzz1105_11.jpg" /></td>
					  </tr>
					  <tr>
						<td width="144" class="bzz1105text">&nbsp;&nbsp;企业文化与班组团队管理<br/>
					    &nbsp;&nbsp;&nbsp;授课教师：<strong>蒋勇</strong> <a href="http://www.thbzzpx.org/CourseDemo/qywh_new/index.htm" target="_blank">[查看]</a></td>
						<td>&nbsp;</td>
						<td width="144" class="bzz1105text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;认识班组长<br/>
					    &nbsp;&nbsp;授课教师：<strong>李飞龙</strong> <a href="http://www.thbzzpx.org/CourseDemo/rsbzz/index.htm" target="_blank">[查看]</a></td>
						<td>&nbsp;</td>
						<td width="144" class="bzz1105text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;班组质量管理<br/>
					   &nbsp;&nbsp;&nbsp; 授课教师：<strong>孙静</strong> <a href="http://www.thbzzpx.org/CourseDemo/bzzlgl/3/index.htm" target="_blank">[查看]</a></td>
					  </tr>
					</table>
                <!-- <ul>
					<h3><a href="#">更多>></a></h3>
<li><a href="#"><img src="/web/bzz_index/index_new/images/pic6.jpg" width="88" height="88" /><h1>如何报名</h1></a></li>
<li><a href="#"><img src="/web/bzz_index/index_new/images/pic6.jpg" width="88" height="88" /><h1>如何报名</h1></a></li>
<li><a href="#"><img src="/web/bzz_index/index_new/images/pic6.jpg" width="88" height="88" /><h1>如何报名</h1></a></li>
<li><a href="#"><img src="/web/bzz_index/index_new/images/pic6.jpg" width="88" height="88" /><h1>如何报名</h1></a></li>
<li><a href="#"><img src="/web/bzz_index/index_new/images/pic6.jpg" width="88" height="88" /><h1>如何报名</h1></a></li>
                 
                </ul>
                <ul style="display:none">
				<h3><a href="#">更多2>></a></h3>
<li><a href="#"><img src="/web/bzz_index/index_new/images/pic6.jpg" width="88" height="88" /><h1>如何报名</h1></a></li>
<li><a href="#"><img src="/web/bzz_index/index_new/images/pic6.jpg" width="88" height="88" /><h1>如何报名</h1></a></li>
<li><a href="#"><img src="/web/bzz_index/index_new/images/pic6.jpg" width="88" height="88" /><h1>如何报名</h1></a></li>
<li><a href="#"><img src="/web/bzz_index/index_new/images/pic6.jpg" width="88" height="88" /><h1>如何报名</h1></a></li>
                </ul> -->
            
			
              </dd>
            </dl>
    </div>
</div>
<!--Excellent courses end-->


<!--Teaching administrationstart-->
<div id="Teaching_administration">
<div class="title_1"><div class="more3"><a href="#">更多>></a></div>
<h1>教学教务</h1>
</div>
<ul>
<li><a href="#"> 关于二期关于二期班学员成绩班学员成绩</a></li>
<li><a href="#"> 关于二期关于二期班学员成绩班学员成绩</a></li>
<li><a href="#"> 关于二期关于二期班学员成绩班学员成绩</a></li>
<li><a href="#"> 关于二期关于二期班学员成绩班学员成绩</a></li>
<li><a href="#"> 关于二期关于二期班学员成绩班学员成绩</a></li>


</ul>

</div>
<!--Teaching administration end-->


<!--Student Yuen start-->
<div id="Student_Yuen">

<div class="tab3">
            <dl>
              <dt style="text-align:left;"><a>学员文苑</a><a>答疑</a><a>常见问题</a><a>论坛精粹</a></dt>
              <dd>
                <ul>
<div class="wrap">
					<h3><a href="#">更多1>></a></h3>
					<div style="clear:both"></div>
					<!-- 学员文苑开始 -->
					<s:iterator id="xywy" value="xywy" status="xywynews">
						<li><a href="#" class="a_1"> <s:property value="title" /></a></li>
						<s:if test="#xywynews.index == 4">
							</div>
							<div class="wrap">
						</s:if>
					</s:iterator>
</div>


                 <div id="Essay">
<P>欢迎各企业管理员组织参加培训班组长投稿，题材不限，可以是班组建设个人发展、岗位技能等经验分享，也可以是学习心得和感悟。投稿作品须由管理员审核后统一推荐到清华大学。 </P>
<P>联系电话：<span style="color:#FF0000; font-family:Arial, Helvetica, sans-serif">010-62797946</span> 推荐作品发送至Email:<span style="color:#FF0000; font-family:Arial, Helvetica, sans-serif"> zzzg@mail.itsinghua.com</span></P>
</div>

                </ul>
                <ul style="display:none">
				<div class="wrap">
					<h3><a href="#">更多2>></a></h3>
					<div style="clear:both"></div>
					<%
					//辅导答疑开始
					String sql_answer = " select id,title from ( \n"+
						" select eqi.id,eqi.title from exam_question_info eqi  \n"+
						" where eqi.is_index_show='1' order by publish_date desc \n"+
						" ) where rownum < 16";
					rs = db.executeQuery(sql_answer);
					int answer_i = 0;
					while(null != rs && rs.next()){
						answer_i++;
					%>
					<li><a href="#" class="a_1"> <%= rs.getString("title") %></a></li>
					<%
					if(answer_i == 5 || answer_i == 10){
						%>
						</div>
						<div class="wrap">
						<%
					}
					}
					db.close(rs);
					if(answer_i < 15){
						for(int i = answer_i; i < 15; i++){
							%>
							<li></li>
							<%
							if(i == 5 || i == 10){
								%>
								</div>
								<div class="wrap">
								<%
							}
						}
					}
					%>
</div>

                </ul>
             <ul style="display:none">
				<div class="wrap">
					<h3><a href="#">更多3>></a></h3>
					<div style="clear:both"></div>
					<%
					//常见问题开始
					String sql_comm_question = " select id,title from resource_info \n"+
						" where status='1' and is_index_show='1' and rownum < 16 order by id desc";
					rs = db.executeQuery(sql_comm_question);
					int comm_question_i = 0;
					while(null != rs && rs.next()){
						comm_question_i++;
					%>
					<li><a href="#" class="a_1"> <%= rs.getString("title") %></a></li>
					<%
					if(comm_question_i == 5 || comm_question_i == 10){
						%>
						</div>
						<div class="wrap">
						<%
					}
					}
					db.close(rs);
					if(comm_question_i < 15){
						for(int i = comm_question_i; i < 15; i++){
							%>
							<li></li>
							<%
							if(i == 5 || i == 10){
								%>
								</div>
								<div class="wrap">
								<%
							}
						}
					}
					%>
</div>
                </ul>
            
			 <ul style="display:none">
								<div class="wrap">
					<h3><a href="#">更多4>></a></h3>
					<div style="clear:both"></div>
					<%
					//论坛精粹开始
					String sql_forum = " select wf.id,wf.title,rownum from whatyforum.whatyforum_forum wf \n"+
						" where wf.elite='189' and rownum < 16 order by click desc";
					rs = db.executeQuery(sql_forum);
					int forum_i = 0;
					while(null != rs && rs.next()){
						forum_i++;
					%>
					<li><a href="#" class="a_1"> <%= rs.getString("title") %></a></li>
					<%
					if(forum_i == 5 || forum_i == 10){
						%>
						</div>
						<div class="wrap">
						<%
					}
					}
					db.close(rs);
					if(forum_i < 15){
						for(int i = forum_i; i < 15; i++){
							%>
							<li></li>
							<%
							if(i == 5 || i == 10){
								%>
								</div>
								<div class="wrap">
								<%
							}
						}
					}
					%>
			</div>
                </ul>
            
			
              </dd>
            </dl>
    </div>


</div>
<!--Student Yuen end-->

<!--Sponsored by start-->
<div id="Sponsored">
<div class="title_5"><h1>主办单位</h1></div>
<p><a href="http://www.tsinghua.edu.cn/qhdwzy/index.jsp" target="_blank"><img src="/web/bzz_index/index_new/images/001.jpg" /></a></p>
<p><a href="http://www.sasac.gov.cn/" target="_blank"><img src="/web/bzz_index/index_new/images/002.jpg" /></a></p>

</div>
<!--Sponsored by end-->


<div style="clear:both"></div>
</div>
<!--container_2 end-->

<!--Link start-->
<div id="Link">

<table width="970" border="0" cellspacing="0" cellpadding="0">
<tr>
<td>
<div id="marqueedivcontrol1" style="height:60px; overflow:hidden;line-height:33px;font-size:12px; border:1px solid #CCC; margin-left:5px; padding:5px 10px 0px 5px">
<a href="#"><img src="/web/bzz_index/index_new/images/004.jpg" width="120" height="58" /></a>
 <a href="#"><img src="/web/bzz_index/index_new/images/003.jpg" width="120" height="58" /></a> 
 <a href="#"><img src="/web/bzz_index/index_new/images/003.jpg" width="120" height="58" /></a> 
  <a href="#"><img src="/web/bzz_index/index_new/images/004.jpg" width="120" height="58" /></a>
   <a href="#"><img src="/web/bzz_index/index_new/images/005.jpg" width="120" height="58" /></a>
     <a href="#"><img src="/web/bzz_index/index_new/images/005.jpg" width="120" height="58" /></a>
      <a href="#"><img src="/web/bzz_index/index_new/images/004.jpg" width="120" height="58" /></a>
        <a href="#"><img src="/web/bzz_index/index_new/images/003.jpg" width="120" height="58" /></a>
         <a href="#"><img src="/web/bzz_index/index_new/images/004.jpg" width="120" height="58" /></a>
          <a href="#"><img src="/web/bzz_index/index_new/images/004.jpg" width="120" height="58" /></a> 
          <a href="#"><img src="/web/bzz_index/index_new/images/003.jpg" width="120" height="58" /></a> 
           <a href="#"><img src="/web/bzz_index/index_new/images/004.jpg" width="120" height="58" /></a> 
           <a href="#"><img src="/web/bzz_index/index_new/images/005.jpg" width="120" height="58" /></a> 
            <a href="#"><img src="/web/bzz_index/index_new/images/005.jpg" width="120" height="58" /></a>
             <a href="#"><img src="/web/bzz_index/index_new/images/004.jpg" width="120" height="58" /></a> 
              <a href="#"><img src="/web/bzz_index/index_new/images/004.jpg" width="120" height="58" /></a></div>
</td>
</tr>
</table>

</div>
<!--Link end-->


<!--Foot start-->
<div style=" margin:0 auto; width:1002px; height:60px">
<iframe src="/web/bzz_index/index_new/iframe/foot.html" frameborder="0" width="1002" height="60px" scrolling="no"></iframe> </div>
<!--Foot end-->


<script defer>
var marquee3=new Marquee("marqueedivcontrol1");
marquee3.Direction="left";
marquee3.Step=1;
marquee3.Width=976;
marquee3.Height=63;
marquee3.Timer=20;
marquee3.ScrollStep=-1;//此句禁止鼠标控制
marquee3.Start();
</script>
<!--在线客服开始-->   
<DIV id=xixi >
<TABLE style="FLOAT: left" border=0 cellSpacing=0 cellPadding=0 width=157>
  <TBODY>
  <TR>
    <TD class=main_head height=39 vAlign=top>&nbsp;</TD></TR>
  <TR>
    <TD class=info vAlign=top>
      <TABLE class=qqtable border=0 cellSpacing=0 cellPadding=0 width=135 
      align=center>
        <TBODY>
        <TR>
          <TD height=5></TD></TR>
       
     
        <TR>
          <TD height=5></TD></TR>
        
        <TR>
          <TD height=38 vAlign=top align=middle><A  href="javascript:void(window.open('http://livechat.webtrn.cn/thbzzpx/chat.php?intgroup=dGhienpweA==&amp;intid=dGhienpweA__&amp;pref=user','','width=590,height=610,left=0,top=0,resizable=yes,menubar=no,location=no,status=yes,scrollbars=yes'))"><IMG id="chat_button_image" src="http://livechat.webtrn.cn/thbzzpx/image.php?id=04&amp;type=overlay" width="149" height="81" border="0"></A></TD>
        </TR>
        <TR>
          <TD align=middle>&nbsp;</TD></TR>
        <TR>
          <TD align=middle>&nbsp;</TD></TR></TBODY></TABLE></TD></TR>
  <TR>
    <TD class=down_kefu vAlign=top></TD></TR></TBODY></TABLE>
<DIV class=Obtn></DIV></DIV>


<!--在线客服结束-->
</body>
</html>