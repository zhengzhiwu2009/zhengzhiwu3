<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>无标题文档</title>
		<link href="images/css/admincss.css" rel="stylesheet" type="text/css">
	</head>

<body style="padding:0px; margin:0px;">
	<table border="0" cellspacing="0" cellpadding="0" width="100%" height="34">
		<tr>
			<td height="34" width="100%" style="background-image: url(images/images/g_04.jpg); background-repeat: repeat-x;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-top: 3px;">
					<tr>
						<td width="70%" align="left" class="topTimes" style="color: #fff;white-space: nowrap;font-size:12px;">
							&nbsp;&nbsp;当前用户：
							<span style="line-height:15px;">
							<s:if test="#attr.user_session.userLoginType==2||#attr.user_session.roleId==1" ><font color="red" ><s:property value="#attr.session.peEnterpriseName" default=""/></font>
							&nbsp;&nbsp;集体管理员</s:if>
							<s:elseif test="#attr.user_session.userLoginType==3"><font color="red" ><s:property value="#attr.user_session.loginId" default=""/></font>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!-- 协会管理员 --></s:elseif>
							<s:elseif test="#attr.user_session.userLoginType==5||#attr.user_session.userLoginType==6">
								<font color="red" ><s:property value="#attr.session.peEnterpriseName" default=""/></font>
							&nbsp;&nbsp;监管集体管理员
							</s:elseif>
							</span>
						</td>
						<td align="left">
							<table width="82%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="21%" class="menuTitle">
										<!-- 信息检索: -->
									</td>
									<td width="57%">
										<label>
											<!-- input type="text" name="textfield" class="info"> -->
										</label>
									</td>
									<td width="22%">
										<!-- img src="images/images/g_07.jpg" width="48" height="19"> -->
									</td>
								</tr>
							</table>
						</td>
						<!--<td align="right" >
						       <table width="200" border="0" cellspacing="0" cellpadding="0">
						         <tr>
						           <td width="44%" class="menuTitle" align="right">显示方式:</td>
						           <td width="56%" align="left">
						           <table width="60" height="22" border="0" cellpadding="0" cellspacing="0">
						             <tr align="center"> 
						               <td><img src="images/images/g_12.jpg" alt="两分工作区" width="19" height="16" class="modelImgNormal" onClick="top.modelB(this)"></td>
						               <td><img src="images/images/g_14.jpg" alt="三分工作区" width="19" height="16" class="modelImgNormal" onClick="top.modelA(this)" onLoad="top.registerObj(this)"></td>
						             </tr>
						           </table></td>
						         </tr>
						       </table>
     							  现在是：
           				   -->
						<s:if test="#attr.user_session.roleId==\"3\" || #attr.user_session.roleId==\"ff8080813fad54ce013fbc4eeb600d9b\"" >
							<td width="350" align="right" class="topTimes">
								当前在线人数:<b><font color="red" id="num">0</font></b>人&nbsp;&nbsp;
							</td>
						</s:if>
						<td width="270" align="center" class="topTimes" id="topTimeBox">&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
	<script type="text/javascript">
  <!--

var d=new initArray("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
var today=new Date();
var date;
function initArray()
{
	this.length=initArray.arguments.length;
	for(var i=0;i<this.length;i++)
	{
		this[i+1]=initArray.arguments[i];
	}
}
date = today.getYear()+"年"+(today.getMonth()+1)+"月"+today.getDate()+"日 "+d[today.getDay()+1];
function showtime()
{
	if (!document.layers&&!document.all)
	return;
	var mydate=new Date();
	var hours=mydate.getHours();
	var minutes=mydate.getMinutes();
	/*
	var dn=" AM";
	if (hours>12){
	dn=" PM";
	hours=hours-12;
	}
	if (hours==0)
	hours=12;
	*/
	if (minutes<=9)
	minutes="0"+minutes;
	//change font size here to your desire           
	myclock="  "+hours+":"+minutes;//+dn;
	if (document.layers)
	{
		document.layers.liveclock.document.write(myclock);
		document.layers.liveclock.document.close();
	}
	else if (document.all)
	{
		document.getElementById("topTimeBox").innerText = date+myclock;
	}
}
showtime();
setInterval("showtime()",3000);     
<s:if test="#attr.user_session.roleId==\"3\" || #attr.user_session.roleId==\"ff8080813fad54ce013fbc4eeb600d9b\""> 
	var xmlHttp;
	var int;
	function create(){
		//开始初始化XMLHttpRequest对象  
		if(window.XMLHttpRequest) { //Mozilla 浏览器  
			xmlHttp = new XMLHttpRequest();  
			if (xmlHttp.overrideMimeType) {//设置MiME类别  
				xmlHttp.overrideMimeType("text/xml");  
			}  
		}else if (window.ActiveXObject) { // IE浏览器  
			try {  
				xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");  
			} catch (e) {  
			 try {  
				 xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");  
			 } catch (e) {}  
			}  
		}  
		if (!xmlHttp) { // 异常，创建对象实例失败  
		 window.alert("不能创建XMLHttpRequest对象实例.");  
		 return false;  
		}  
	}
	//ajax核心执行方法（此处为提交到servlet处理后,返回纯文本）
	function run(){//获得在线人数
		create();
		var URL = "/entity/information/peBulletinView_getOnlineNum.action?ids=9&x="+Math.random();
		xmlHttp.open("GET",URL,true);
		xmlHttp.onreadystatechange=callback;
		xmlHttp.send(null);
	}
	//回调函数
	function callback(){
		if(xmlHttp.readyState == 4){
			if(xmlHttp.status == 200){
				var v = xmlHttp.responseText;
				if(v!='' && v.indexOf('DOCTYPE')>0){
					v='--已掉线--';
					if(int){
						clearInterval(int);//停止自动请求在线人数
					}
					alert('您的登录已经注销，请重新登录！');
				}
				document.getElementById("num").innerText = v;
			}
		}
	} 
	run();
	int = setInterval("run()",60000); 
</s:if>
	//-->
</script>
</html>
