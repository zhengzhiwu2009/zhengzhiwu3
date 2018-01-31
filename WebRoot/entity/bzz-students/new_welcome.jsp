<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String d = String.valueOf(Math.random());
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	Object notice = request.getAttribute("notice");
	Object title = request.getAttribute("title");
	Object userId =request.getAttribute("userId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<script language="JavaScript" type="text/javascript" src="cms/common_res/js/jquery-1.7.2.min.js"></script>
		<script src="/cms/res_base/saccms_com_www/newIndex/js/common.js" type="text/javascript"></script>
		<link href="/entity/bzz-students/css/homecss.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	/*.enroll_btn{ position:absolute; right:-2px; bottom:3px; height:24px;z-index: 999; width:46px;cursor: pointer; border:none soild #eee; border-radius:0px; corlor:#ccc }*/
	.enroll_btn{ position:absolute; right:0px; bottom:5px; height:30px;z-index: 999; width:30px;cursor: pointer;background: url(../entity/bzz-students/images/blue_plus.png) no-repeat; background-size:30px 30px;border:none;}
	.banner_index {
	    height: 225px;
	    position: relative;
	    overflow: hidden;
	    width: 715px;
	    margin: 30px auto;
	    background: #fff;
	    border: 1px solid #e8e8e8;
	}
	.banner_index .btnPre, .banner_pro .bPre {
	    background: url('/cms/res_base/saccms_com_www/newIndex/images/bg_btnPre_index.png') no-repeat 0 0;
	    left: 230px;
	}
	.banner_index .btnNext, .banner_pro .bNext {
	    background: url('/cms/res_base/saccms_com_www/newIndex/images/bg_btnNext_index.png') no-repeat 0 0;
	    right: 370px;
	}
	.banner_index .btn, .banner_pro .bBtn {
	    position: absolute;
	    top: 165px;
	    width: 56px;
	    height: 56px;
	    display: block;
	    z-index: 3;
	}
	.banner_index .banner_wrap {
	    position: absolute;
	    left: 0px;
	    top: 0px;
	    z-index: 2;
	}
	.left {
	    float: left;
	}
	.bannercontainer {
	    width: 355px;
	    height: 225px;
	    background: #f0f0f0;
	    padding-top: 0px;
	    padding-right: 0px;
	    font-weight: 330;
	    padding-left: 5px;
	}
	.h2 {
	    line-height: 24px;
	}
	.f6 {
	    font-size: 1.5em;
	}
	.f8 {
	    font-size: 1.1em;
	}
	.c2 {
	    color: #000000;
	    font-weight: 400;
	}
	.bannercontainerall {
	    overflow: hidden;
	    height: 165px;
	    width:340px;
	}
	.infomation_table a{color:red;}
	.infomation_table tr .title_td{ text-align:left;font-weight:800;  padding-left:30px;}
</style>
<script type="text/javascript">
$(function(){
	var defHeight = $('#wrap').height();
	var slideHeight = 105; // px
	if(defHeight <= 105)
		slideHeight = defHeight;
	if(defHeight > slideHeight){
		$('#wrap').css('height' , slideHeight + 'px');
		$('#wrap').css('overflow' , 'hidden');
		$('#read-more').append('<a href="javascript:void(0);">查看更多</a>');
		$('#read-more a').click(function(){
			var curHeight = $('#wrap').height();
			if(curHeight == slideHeight){
				$('#wrap').animate({height: defHeight}, "normal");
				$('#read-more a').html('点击隐藏');
				$('#gradient').fadeOut();
			}else{
				$('#wrap').animate({height: slideHeight}, "normal");
				$('#read-more a').html('查看更多');
				$('#gradient').fadeIn();
			}
			return false;
		});		
	}else{
		$('#read-more').append('');
	}
});
</script>
<script type="text/javascript">
    window.onload = function () {
	var oBtnLeft = document.getElementById("goleft");
	var oBtnRight = document.getElementById("goright");
	var oDiv = document.getElementById("indexmaindiv");
	var oDiv1 = document.getElementById("maindiv1");
	var oUl = document.getElementById("count1");
	var aLi = oUl.getElementsByTagName("li");
	
	var oBtnLeft_xf = document.getElementById("goleft_xf");
	var oBtnRight_xf = document.getElementById("goright_xf");
	var oDiv_xf = document.getElementById("indexmaindiv_xf");
	var oDiv1_xf = document.getElementById("maindiv1_xf");
	var oUl_xf = document.getElementById("count2");
	var aLi_xf = oUl_xf.getElementsByTagName("li");
	
	//var oBtnpre = document.getElementById("banner_index_pre");
    //var oBtnnext = document.getElementById("banner_index_next");
	//var oDiv_zt = document.getElementById("zhuanti");
	//var oDiv1_zt = document.getElementById("kecheng");
	//var oUl_zt = document.getElementById("banner_index_zt");
	//var aLi_zt = oUl_zt.getElementsByTagName("li");
	
	var now = -5 * (aLi[0].offsetWidth + 10); 
	//var now_zt = -1* (aLi_zt[0].offsetWidth ); 
	
	//oUl_zt.style.width = aLi_zt.length * (aLi[0].offsetWidth + 10) + 'px';
	
	

	oBtnRight_xf.onclick = function () {
		var n = Math.floor((aLi_xf.length * (aLi_xf[0].offsetWidth + 10) + oUl_xf.offsetLeft) / aLi_xf[0].offsetWidth);
		if (n <= 5) {
			move(oUl_xf, 'left', 0);
		}
		else {
			move(oUl_xf, 'left', oUl_xf.offsetLeft + now);
		}
	}
	oBtnLeft_xf.onclick = function () {
		var now1 = -Math.floor((aLi_xf.length / 5)) * 5 * (aLi_xf[0].offsetWidth + 10);
		if (oUl_xf.offsetLeft >= 0) {
			move(oUl_xf, 'left', now1);
		}
		else {
			move(oUl_xf, 'left', oUl_xf.offsetLeft - now);
		}
	}
	
	
	
	//oUl.style.width = aLi.length * (aLi[0].offsetWidth + 10) + 'px';
	oBtnRight.onclick = function () {
		var n = Math.floor((aLi.length * (aLi[0].offsetWidth + 10) + oUl.offsetLeft) / aLi[0].offsetWidth);
		if (n <= 5) {
			move(oUl, 'left', 0);
		}
		else {
			move(oUl, 'left', oUl.offsetLeft + now);
		}
	}
	oBtnLeft.onclick = function () {
		var now1 = -Math.floor((aLi.length / 5)) * 5 * (aLi[0].offsetWidth + 10);
		if (oUl.offsetLeft >= 0) {
			move(oUl, 'left', now1);
		}
		else {
			move(oUl, 'left', oUl.offsetLeft - now);
		}
	}
	//var timer = setInterval(oBtnRight.onclick, 10000);
	//oDiv.onmouseover = function () {
	//	clearInterval(timer);
	//}
	//oDiv.onmouseout = function () {
	//	timer = setInterval(oBtnRight.onclick, 10000);
	//}
	//var timer_xf = setInterval(oBtnRight_xf.onclick, 10000);
	//oDiv.onmouseover = function () {
	//	clearInterval(timer_xf);
	//}
	//oDiv.onmouseout = function () {
	//	timer_xf = setInterval(oBtnRight_xf.onclick, 10000);
	//}
	
	
};
function getStyle(obj, name) {
	if (obj.currentStyle) {
		return obj.currentStyle[name];
	}
	else {
		return getComputedStyle(obj, false)[name];
	}
}

function move(obj, attr, iTarget) {
	clearInterval(obj.timer)
	obj.timer = setInterval(function () {
		var cur = 0;
		if (attr == 'opacity') {
			cur = Math.round(parseFloat(getStyle(obj, attr)) * 100);
		}
		else {
			cur = parseInt(getStyle(obj, attr));
		}
		var speed = (iTarget - cur) / 6;
		speed = speed > 0 ? Math.ceil(speed) : Math.floor(speed);
		if (iTarget == cur) {
			clearInterval(obj.timer);
		}
		else if (attr == 'opacity') {
			obj.style.filter = 'alpha(opacity:' + (cur + speed) + ')';
			obj.style.opacity = (cur + speed) / 100;
		}
		else {
			obj.style[attr] = cur + speed + 'px';
		}
	}, 30);
}  
function addToOrder(id){
	  var url = '/entity/workspaceStudent/studentWorkspace_goShoppingCourse.action';
      var params = {courseId:id};
      jQuery.post(url, params, callbackFun, 'json');
}
function callbackFun(data){
      alert(data.success);
}
</script>
	</head>
	<body>
		<div class="title">
			个人主页
		</div>
		<div>
			<div class="content" style="color:#00274c;">
				<%
					if (notice != null && !"".equals(notice)) {
				%>
					<div style="width:100%;text-align:center;font-weight:bold;"><%=title %></div>
					<div id="wrap">
						<div id="noticeContent"><%=notice %></div>
					</div>
					<div id="read-more" style="width:100%;text-align:center;"></div>
				<%
					} else {
				%>
				<span size="2px"> 暂无内容 </span>
				<%
					}
				%>
			
			</div>
			<div class="m7" style="color:#00274c;">
				<div class="h4 inline-block">
						<span class="b3 inline-block"> 提示信息 </span>
						<a href="javascript:void(0)" 	class="b2 inline-block" style="color: #6b6b6b;">&nbsp;</a>
				</div>
				<div >
				<table width="100%" class="infomation_table">
				 	<tr>
				 		<td width="15%" class="title_td">站内邮箱：</td>
				 		<td width="30%">未读邮件&nbsp;<a href="/entity/workspaceStudent/studentWorkspace_tositeemail.action"><s:property value="#request.zhanneixinNum" /> </a> &nbsp;封</td>
				 		<td width="20%" class="title_td">正在学习课程：</td>
				 		<td width="35%"><a href="/entity/workspaceStudent/studentWorkspace_toLearningCourses.action"><s:property value="#request.zhengzaixuexikechengNum" /> </a>门，&nbsp;共计&nbsp;
													<s:property value="#request.zhengzaixuexikechengTime" /> </span>&nbsp;学时	</td>
				 	</tr>
				 	<tr>
				 		<td class="title_td">选课期：</td>
				 		<td>可用选课期&nbsp;<a href="/entity/workspaceStudent/studentWorkspace_toElectiveCoursePeriod.action"><s:property value="#request.xuankeqiNum" /> </a>&nbsp;个</td>
				 		<td class="title_td"> 待考试课程：</td>
				 		<td><a href="/entity/workspaceStudent/studentWorkspace_toCompletedCourses.action"><s:property value="#request.daikaoshikechengNum" /> </a>&nbsp;门，&nbsp;&nbsp;共计&nbsp;
 						<s:property value="#request.daikaoshikechengTime" /> &nbsp;学时	</td>
				 	</tr>
				 	<tr>
				 		<td class="title_td">专项培训：</td>
				 		<td><a href="/entity/workspaceStudent/studentWorkspace_tospecialenrolment.action"><s:property value="#request.zhuanxiangpeixunNum" /> </a> &nbsp;个专项培训可报名</td>
				 		<td class="title_td">在线直播课程：</td>
				 		<td><a href="/entity/workspaceStudent/studentWorkspace_toSacLive.action"><s:property value="#request.zaixianzhibokechengNum" /> </a>&nbsp;个视频直播可报名	</td>
				 	</tr>
				</table>
						
						
 				</div>	
 				
				
				
			</div>
			
			<div class="m2" id="indexmaindiv">
				<div class="indexmaindiv1 clearfix">
					<div class="h4 inline-block">
						<span class="b3 inline-block"> 热门课程推荐 </span>
						<a href="javascript:void(0)" onclick="window.open('/cms/flkcchannel.htm?orderSearch=1')"
							class="b2 inline-block" style="color: #6b6b6b;">查看更多>></a>
					</div>
					<div class="stylesgoleft inline-block" id="goleft"></div>
					<div class="box inline-block maindiv1" id="maindiv1"
						style="float: left">
						<ul id="count1">
							<s:iterator value="#request.recommendCourseList" id="recommend">
								<li>
									<div class="neirong" style="color:#00274c;" title="<s:property value="#recommend[1]" />">
										<div style=" position:relative;">
											<a style="color: #3366ff; cursor: pointer; position:relative;" onclick="window.open('/cms/flkcalone.htm?myId=<s:property value="#recommend[0]" />&flag=true','newWindow_detail')">
												<img onclick="" src="<s:property value="#recommend[7]" />" border="0" onerror="this.src='../entity/bzz-students/noneCourseImg.jpg'" width="120" height="90" ;" />
												
											</a>
											<button class="enroll_btn" onclick="addToOrder('<s:property value="#recommend[0]" />')"></button>
										</div>
										<s:if test="#recommend[1].length() > 18">
											<s:property value="#recommend[1].substring(0,18)" />...
										</s:if>
										<s:else>
											<s:property value="#recommend[1]" />
										</s:else>
										<br>
									</div>
								</li>
							</s:iterator>
						</ul>
					</div>
					<div class="stylesgoright inline-block" id="goright"></div>
				</div>
			</div>
				<div class="m2" id="indexmaindiv_xf">
				<div class="indexmaindiv1 clearfix">
					<div class="h4">
						<span class="b3 inline-block c h5"> 新发布课程 </span>
						<a href="javascript:void(0)"
							onclick="window.open('/cms/flkcchannel.htm?orderSearch=0')"
							class="inline-block h5 c2 b2 f3" style="color: #6b6b6b;">查看更多>></a>
					</div>
					<div class="stylesgoleft inline-block" id="goleft_xf"></div>
					<div class="box inline-block maindiv1" id="maindiv1_xf"
						style="float: left">
						<ul id="count2">
							<s:iterator value="#request.publishCourseList" id="publish">
								<li>
									<div class="neirong" title="<s:property value="#publish[1]" />">
										<div style=" position:relative;">
											<a style="color: #3366ff; cursor: pointer; position:relative;" onclick="window.open('/cms/flkcalone.htm?myId=<s:property value="#publish[0]" />&flag=true','newWindow_detail')">
												<img onclick="" src="<s:property value="#publish[7]" />" border="0" onerror="this.src='../entity/bzz-students/noneCourseImg.jpg'" width="120" height="90" ;" />
												
											</a>
											<button class="enroll_btn" onclick="addToOrder('<s:property value="#publish[0]" />')"></button>
										</div>
										<div class="newkc_title" style="color:#00274c;"> 
											<s:if test="#publish[1].length() > 18">
												 <s:property value="#publish[1].substring(0,18)" />...
											</s:if>
											<s:else>
												<s:property value="#publish[1]" />
											</s:else>
										</div>
										<br>
								</li>
							</s:iterator>
						</ul>
					</div>
					<div class="stylesgoright inline-block" id="goright_xf"></div>
				</div>
			</div>
			<div style ="display:none" class="m1" id="zhuanti">
				<div class="h4">
					<span class="b3 inline-block"> 专题课程推荐 </span>
					<a href="javascript:void(0)" onclick="window.open('/cms/ztalone.htm')" class="b2 inline-block" style="color: #6b6b6b;">查看更多>></a>
				</div>
			<section class="SpecialTopics" >
				<div class="banner_index">
					<a href="javascript:void(0);" class="btn btnPre" id="banner_index_pre"></a>
					<a href="javascript:void(0);" class="btn btnNext" id="banner_index_next"></a>
					<ul class="banner_wrap" id="banner_index">
						<s:iterator value="#request.seriesCourseList" id="series">
							<li style="float:left">
								<a href="javascript:void(0)" class="inline-block left" onclick="window.open('/cms/ztdetailalone.htm?xlCourse=<s:property value="#series[0]" />');">
									<img src="<s:property value="#series[3]" />" border="0" onerror="this.src='../entity/bzz-students/jpkc_24.jpg'" width="355px" height="225px" style="background: #fff" />
								</a>
								<div class="left bannercontainer">
									<h2 class="h2" style="margin-bottom:0px;margin-left:10px;">
										<a href="javascript:void(0)" class="f6" style="color:#2a7ab9" onclick="window.open('/cms/ztdetailalone.htm?xlCourse=<s:property value="#series[0]" />');">
										<s:property value="#series[1]" /></a>
									</h2>
								<div id="ztDesc" class="f8 c2 h2 bannercontainerall" style="padding-left:12px;padding-right:10px;text-indent:2em;">
									<a href="javascript:void(0)" class="f8" style="color:#00274c;  font-size:12px" onclick="window.open('/cms/ztdetailalone.htm?xlCourse=<s:property value="#series[0]" />');">
										<s:if test="#series[2].length() > 120">
											<s:property value="#series[2].substring(0,120)" />...
										</s:if>
										<s:else>
											<s:property value="#series[2]" />
										</s:else>
									</a>
								</div>
							</li>
						</s:iterator>
					</ul>
				</div>
			</section>
			</div>
		
			<div class="m1">
				<div class="h4">
					<span class="b3 inline-block c h5"> 学习资料推荐 </span>
					<a href="entity/workspaceStudent/studentWorkspace_toZiliao.action"
						class="inline-block h5 c2 b2 f3" style="color: #6b6b6b;">查看更多>></a>
				</div>
				<table width="100%" class="m1_table" style="color:#00274c;">
					<tr class="w1 b4 f8" >
						<th class="m1_th">
							资料名称
						</th>
						<th class="m1_th" width="25%">
							颁布/发布单位
						</th>
						<th class="m1_th" width="10%">
							资料分类
						</th>
						<th class="m1_th" width="15%">
							颁布/发布时间
						</th>
						<th class="m1_th" width="10%">
							查阅次数
						</th>
					</tr>
					<s:iterator value="#request.informationList" id="infor">
						<tr class="m1_tr">
							<td class="m1_td" title="<s:property value="#infor[1]" />">
								<a target="blank" style=" font-weight:normal" href="/cms/doumentDetail.htm?myId=<s:property value="#infor[0]" />&uid=<%=userId%>">
									<s:if test="#infor[1].length() > 20">
										<s:property value="#infor[1].substring(0,20)" />...
									</s:if>
									<s:else>
										<s:property value="#infor[1]" />
									</s:else>
								</a>
							</td>
							<td class="m1_td" align="center" title="<s:property value="#infor[2]" />">
									<s:if test="#infor[2].length() > 10">
										 <s:property value="#infor[2].substring(0,10)" />...
									</s:if>
									<s:else>
										 <s:property value="#infor[2]" />
									</s:else>
							</td>
							<td class="" align="center" title="<s:property value="#infor[3]" />">
									<s:if test="#infor[3].length() > 5">
										<s:property value="#infor[3].substring(0,5)" />...
									</s:if>
									<s:else>
										<s:property value="#infor[3]" />
									</s:else>
							</td>
							<td class="" align="center">
								<s:property value="#infor[4]" />
							</td>
							<td class="" align="center">
								<s:property value="#infor[5]" />
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
var ShowPre1 = new ShowPre({box:"banner_index",Pre:"banner_index_pre",Next:"banner_index_next",numIco:"index_numIco",loop:1,auto:1});
</script>
