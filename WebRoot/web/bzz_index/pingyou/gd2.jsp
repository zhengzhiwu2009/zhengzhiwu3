<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.dbpool"%>
<%@page import="com.whaty.platform.database.oracle.MyResultSet"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>图片滚动</title>
		<style type="text/css">
a {
	color: #333333;
	font-size: 12px;
	text-decoration: none;
	cursor: pointer;
}

a:hover {
	color: #333333;
}

.blk_18 {
	overflow: hidden;
	zoom: 1;
	font-size: 9pt;
	background: #fff;
	width: 482px;
	margin-top: 8px;
	padding: 0px;
}

.blk_18 .pcont {
	width: 399px;
	float: left;
	overflow: hidden;
	padding-left: 5px;
}

.blk_18 .ScrCont {
	width: 32766px;
	zoom: 1;
	margin-left: -10px;
}

.blk_18 #List1_1,.blk_18 #List2_1 {
	float: left;
}

.blk_18 .LeftBotton {
	width: 24px;
	height: 39px;
	float: left;
	background: url(images/p_16.jpg) no-repeat;
}

.blk_18 .RightBotton {
	width: 24px;
	height: 39px;
	float: left;
	background: url(images/p_14.jpg) no-repeat;
}

.blk_18 .LeftBotton {
	background-position: 0 0;
	margin: 120px 8px 0px 8px;
}

.blk_18 .RightBotton {
	background-position: 0 0px;
	margin: 120px 2px 10px 8px;
}

.blk_18 .LeftBotton:hover {
	background-position: 0px 0px;
}

.blk_18 .RightBotton:hover {
	background-position: 0px 0px;
}

.blk_18 .pl {
	width: 394px;
	_width: 394px;
	float: left;
	line-height: 24px;
	margin-left: 8px;
}

.blk_18 a.pl:hover {
	color: #dc0000;
	background: #fff;
	float: left;
	margin-left: 8px;
}

.pp1 {
	padding-top: 10px;
	padding-bottom: 6px;
	width: 394px;
	height: 280px;
}

.namep1 {
	font-size: 14px;
	color: #9d0a00;
	font-weight: 600;
	text-align: left;
	padding-top: 10px;
}

.p1text {
	font-size: 12px;
	color: #333333;
	line-height: 18px;
	margin-top: 6px;
	text-align: left;
	padding-bottom: 4px;
	border-bottom: 1px #333333 dashed;
}

.bfont {
	font-weight: bold;
}

body {
	margin: 0px auto;
	padding: 0px auto;
}
</style>
		<script type="text/javascript">

var Speed_1 = 10; //速度(毫秒)
var Space_1 = 402; //每次移动(px)
var PageWidth_1 = 402 *1; //翻页宽度
var interval_1 = 5000; //翻页间隔时间
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

	<%!String fixNull(String str) {
		if (str == null || str.equalsIgnoreCase("null"))
			str = "";
		return str.trim();
	}%>

	<body>
		<!-- picrotate_left start -->
		<div class="blk_18">
			<a class="LeftBotton" onmousedown="ISL_GoUp_1()"
				onmouseup="ISL_StopUp_1()" onmouseout="ISL_StopUp_1()"
				href="javascript:void(0);" target="_self"></a>
			<div class="pcont" id="ISL_Cont_1">
				<div class="ScrCont">
					<div id="List1_1">
						<!-- piclist begin -->

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
							String name_linksTemp = "";

							sql = " select distinct c.id as enterprise_id , c.name as enterprise_name,c.code from pe_bzz_goodmag a, pe_enterprise_manager b, pe_enterprise c "
									+ " where a.fk_managerid = b.id and b.fk_enterprise_id = c.id and b.is_goodmag = 'ccb2880a91dadc115011dadfcf26b0010' and "
									+ " a.submit_status = 'ccb2880a91dadc115011dadfcf26b0005' order by c.code  ";
							rs = work.executeQuery(sql);

							current_num = 0;
							count = work.countselect(sql);
							while (rs != null && rs.next()) {
								mag_name = "";
								enterprise_id = fixNull(rs.getString("enterprise_id"));
								enterprise_name = fixNull(rs.getString("enterprise_name"));

								sql = " select a.id as goodMag_id ,  b.name as name from pe_bzz_goodmag a, pe_enterprise_manager b where a.fk_managerid = b.id and "
										+ " a.submit_status = 'ccb2880a91dadc115011dadfcf26b0005' and b.is_goodmag = 'ccb2880a91dadc115011dadfcf26b0010' and "
										+ " b.fk_enterprise_id = '"
										+ enterprise_id
										+ "' order by name ";
								rs2 = work.executeQuery(sql);
								while (rs2 != null && rs2.next()) {
									goodMag_id = fixNull(rs2.getString("goodMag_id"));
									name_linksTemp = "<a href='zj_sub3.jsp?goodMag_id="
											+ goodMag_id + "' target='_blank' >"
											+ fixNull(rs2.getString("name"))
											+ "</a>&nbsp;&nbsp;";
									mag_name = mag_name + name_linksTemp;

								}
								work.close(rs2);

								if ((current_num % 4) == 0) {
						%>
						<div class="pl">
							<div class="pp1">
								<%
									}
								%>
								<div class="namep1">
									企业名称：<%=enterprise_name%></div>
								<div class="p1text">
									<span class="bfont">管理员：</span><%=mag_name%></div>
								<%
									if (((current_num + 1) % 4) == 0) {
								%>
							</div>
						</div>
						<%
							} else if ((current_num + 1) == count) {
						%>
					</div>
				</div>
				<%
					}
						current_num++;
					}
					work.close(rs);
				%>
				<!-- piclist end -->
			</div>
			<div id="List2_1">

			</div>
		</div>
		<div>
			<a class="RightBotton" onmousedown="ISL_GoDown_1()"
				onmouseup="ISL_StopDown_1()" onmouseout="ISL_StopDown_1()"
				href="javascript:void(0);" target="_self"></a>
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
