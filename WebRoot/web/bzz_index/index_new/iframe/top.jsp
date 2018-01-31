<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>top</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="/web/bzz_index/index_new/css/css.css" />
	<link rel="stylesheet" type="text/css" href="/web/bzz_index/index_new/css/reset.css" />
<script src="/web/bzz_index/index_new/js/jquery-1.6.1.min.js" type="text/javascript"></script>
<script src="/web/bzz_index/index_new/js/slider.js" type="text/javascript"></script>
<script src="/web/bzz_index/index_new/js/msclass.js" language="javascript"></script>
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
</head>

<body>
<!--top start-->
<div id="top"><img src="/web/bzz_index/index_new/images/top.jpg" width="1002" height="143" /></div>
<!--top end-->
<!--nav start-->
<div id="nav" >
<ul>
<li class="first"><a href="/" target="_parent">首页</a></li> 
<li style="width:2px; height:37px"><img src="/web/bzz_index/index_new/images/line.jpg" /></li>
<li><a href="/web/bzz_index/xmjs/xmjs.jsp"  target="_parent">项目介绍</a></li>
<li style="width:2px; height:37px"><img src="/web/bzz_index/index_new/images/line.jpg" /></li>
<li><a href="/web/bzz_index/kctx/kctx.jsp"  target="_parent">课程体系</a></li>
<li style="width:2px; height:37px"><img src="/web/bzz_index/index_new/images/line.jpg" /></li>
<li><a href="/web/bzz_index/khrz/khrz.jsp" target="_parent" >考核认证</a></li>
<li style="width:2px; height:37px"><img src="/web/bzz_index/index_new/images/line.jpg" /></li>
<li><a href="/web/bzz_index/msfc/msfc_new.jsp" target="_parent" >名师风采</a></li>
<li style="width:2px; height:37px"><img src="/web/bzz_index/index_new/images/line.jpg" /></li>
<li><a href="/web/bzz_index/yxbzz/yxbzz.jsp" target="_parent" >优秀班组长</a></li>
<li style="width:2px; height:37px"><img src="/web/bzz_index/index_new/images/line.jpg" /></li>
<li><a href="/web/bzz_index/xyfw/xyfw.jsp" target="_parent" >学员服务</a></li>
<li style="width:2px; height:37px"><img src="/web/bzz_index/index_new/images/line.jpg" /></li>
<li><a href="/web/bzz_index/cjwt_new/cjwt.jsp" target="_parent" >常见问题</a></li>
<li style="width:2px; height:37px"><img src="/web/bzz_index/index_new/images/line.jpg" /></li>
<li><a href="/web/bzz_index/lxwm_new/lxwm_new.jsp" target="_parent" >联系我们</a></li>
</ul>
</div>
<!--nav end-->