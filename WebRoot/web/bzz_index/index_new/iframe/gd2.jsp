<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
a { color:#3e3e3e; font-size:9pt; text-decoration:none;}
a:hover {color:#dc0000; }
.blk_18 {
 overflow:hidden;
 zoom:1;
 font-size:9pt;
 background:#fff;
 width:958px;
 margin-top:15px;
 padding:2px;
}
.blk_18 .pcont {
 width:902px;
 float:left;
 overflow:hidden;
 padding-left:5px;
}
.blk_18 .ScrCont {
 width:32766px;
 zoom:1;
 margin-left:-10px;
}
.blk_18 #List1_1, .blk_18 #List2_1 {
 float:left;
}
.blk_18 .LeftBotton, .blk_18 .RightBotton {
 width:15px;
 height:53px;
 float:left;
 background:url(../images/ax.gif) no-repeat;
}
.blk_18 .LeftBotton {
 background-position: 0 0;
 margin:8px 5px 0px 4px;
}
.blk_18 .RightBotton {
 background-position: 0 -71px;
 margin:8px 2px 10px 5px;
}
.blk_18 .LeftBotton:hover {
 background-position: -20px 0;
}
.blk_18 .RightBotton:hover {
 background-position: -20px -71px;
}
.blk_18 .pl img {
 display:block;
 cursor:pointer;
 border:none;
 padding:2px;
 background-color:#FFF;
 border:1px solid #f3f3f3;
}
.blk_18 .pl {
 float:left;
 text-align:center;
 margin-left:25px;
}
.blk_18 .pl h1{
	font-size:12px; font-weight:normal;
}

.blk_18 a.pl:hover {
 color:#dc0000;
 background:#fff;
 float:left;}
body {
	margin:0px auto;
	padding:0px auto;
}
</style>
<script type="text/javascript">

var Speed_1 = 10; //速度(毫秒)
var Space_1 = 20; //每次移动(px)
var PageWidth_1 = 96 * 2; //翻页宽度
var interval_1 = 2000; //翻页间隔时间
var fill_1 = 0; //整体移位
var MoveLock_1 = false;
var MoveTimeObj_1;
var MoveWay_1="right";
var Comp_1 = 0;
var AutoPlayObj_1=null;
function GetObj(objName){if(document.getElementById){return eval('document.getElementById("'+objName+'")')}else{return eval('document.all.'+objName)}}
function AutoPlay_1(){clearInterval(AutoPlayObj_1);AutoPlayObj_1=setInterval('ISL_GoDown_1();ISL_StopDown_1();',interval_1)}
function ISL_GoUp_1(){if(MoveLock_1)return;clearInterval(AutoPlayObj_1);MoveLock_1=true;MoveWay_1="left";MoveTimeObj_1=setInterval('ISL_ScrUp_1();',Speed_1);}
function ISL_StopUp_1(){if(MoveWay_1 == "right"){return};clearInterval(MoveTimeObj_1);if((GetObj('ISL_Cont_1').scrollLeft-fill_1)%PageWidth_1!=0){Comp_1=fill_1-(GetObj('ISL_Cont_1').scrollLeft%PageWidth_1);CompScr_1()}else{MoveLock_1=false}
AutoPlay_1()}
function ISL_ScrUp_1(){if(GetObj('ISL_Cont_1').scrollLeft<=0){GetObj('ISL_Cont_1').scrollLeft=GetObj('ISL_Cont_1').scrollLeft+GetObj('List1_1').offsetWidth}
GetObj('ISL_Cont_1').scrollLeft-=Space_1}
function ISL_GoDown_1(){clearInterval(MoveTimeObj_1);if(MoveLock_1)return;clearInterval(AutoPlayObj_1);MoveLock_1=true;MoveWay_1="right";ISL_ScrDown_1();MoveTimeObj_1=setInterval('ISL_ScrDown_1()',Speed_1)}
function ISL_StopDown_1(){if(MoveWay_1 == "left"){return};clearInterval(MoveTimeObj_1);if(GetObj('ISL_Cont_1').scrollLeft%PageWidth_1-(fill_1>=0?fill_1:fill_1+1)!=0){Comp_1=PageWidth_1-GetObj('ISL_Cont_1').scrollLeft%PageWidth_1+fill_1;CompScr_1()}else{MoveLock_1=false}
AutoPlay_1()}
function ISL_ScrDown_1(){if(GetObj('ISL_Cont_1').scrollLeft>=GetObj('List1_1').scrollWidth){GetObj('ISL_Cont_1').scrollLeft=GetObj('ISL_Cont_1').scrollLeft-GetObj('List1_1').scrollWidth}
GetObj('ISL_Cont_1').scrollLeft+=Space_1}
function CompScr_1(){if(Comp_1==0){MoveLock_1=false;return}
var num,TempSpeed=Speed_1,TempSpace=Space_1;if(Math.abs(Comp_1)<PageWidth_1/2){TempSpace=Math.round(Math.abs(Comp_1/Space_1));if(TempSpace<1){TempSpace=1}}
if(Comp_1<0){if(Comp_1<-TempSpace){Comp_1+=TempSpace;num=TempSpace}else{num=-Comp_1;Comp_1=0}
GetObj('ISL_Cont_1').scrollLeft-=num;setTimeout('CompScr_1()',TempSpeed)}else{if(Comp_1>TempSpace){Comp_1-=TempSpace;num=TempSpace}else{num=Comp_1;Comp_1=0}
GetObj('ISL_Cont_1').scrollLeft+=num;setTimeout('CompScr_1()',TempSpeed)}}
function picrun_ini(){
GetObj("List2_1").innerHTML=GetObj("List1_1").innerHTML;
GetObj('ISL_Cont_1').scrollLeft=fill_1>=0?fill_1:GetObj('List1_1').scrollWidth-Math.abs(fill_1);
GetObj("ISL_Cont_1").onmouseover=function(){clearInterval(AutoPlayObj_1)}
GetObj("ISL_Cont_1").onmouseout=function(){AutoPlay_1()}
AutoPlay_1();
}
</script>
  </head>
  
  <body>
    <!-- picrotate_left start -->
<div class="blk_18">
  <div class="pcont" id="ISL_Cont_1">
    <div class="ScrCont">
      <div id="List1_1">
        <!-- piclist begin -->
       <a class="pl" href="/web/bzz_index/msfc/sunjing.jsp" target="_blank"><img src="/web/bzz_index/msfc/images/sunjing.jpg" width="65" height="73" /><h1>孙静</h1></a>
       <a class="pl" href="/web/bzz_index/msfc/yangzonghua.jsp" target="_blank"><img src="/web/bzz_index/msfc/images/yangzonghua.jpg" width="65" height="73" /><h1>杨宗华</h1></a>
       <a class="pl" href="/web/bzz_index/msfc/liudacheng.jsp" target="_blank"><img src="/web/bzz_index/msfc/images/liudacheng.jpg" width="65" height="73" /><h1>刘大成</h1></a>
        <!-- piclist end -->
      </div>
      <div id="List2_1"></div>
    </div>
  </div>
 </div>
<div class="c"></div>
<script type="text/javascript">
  <!--
  picrun_ini()
  //-->
  </script>
<!-- picrotate_left end -->
  </body>
</html>
