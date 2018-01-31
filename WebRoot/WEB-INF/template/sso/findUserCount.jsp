<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String d = String.valueOf(Math.random());
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>"/>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="<%=basePath%>cms/res_base/saccms_com_www/newIndex/css.css" rel="stylesheet" type="text/css"/>
		<link href="<%=basePath%>cms/res_base/saccms_com_www/newIndex/index.css" rel="stylesheet" type="text/css"/>
		<script language="JavaScript" type="text/javascript" src="<%=basePath%>cms/common_res/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>cms/common_res/js/lhgdialog/lhgdialog.js?self=true&skin=blue"></script>
		<script language="JavaScript" type="text/javascript" src="<%=basePath%>cms/res_base/saccms_com_www/default/article/js/menu.js"></script>
		<script type="text/javascript" src="<%=basePath%>cms/My97DatePicker/WdatePicker.js"></script>
		<script src="<%=basePath%>cms/res_base/saccms_com_www/newIndex/js/ad.js" type="text/javascript"></script>
		<script language="javascript">
			function checknull(){
				if(document.getElementById("pename").value==""){
					alert('真实姓名不能为空!');
					document.getElementById("pename").focus();
					return false;
				}
				if(document.getElementById("cardno").value=="") {
					alert('证件号码不能为空!');
					document.getElementById("cardno").focus();
					return false;
				}
				return true;
			}
		</script>
		<script src="js/jquery.js" type="text/javascript"></script>
		<script> 
		   (function() {
		     if (! 
		     /*@cc_on!@*/
		     0) return;
		     var e = "abbr, article, aside, audio, canvas, datalist, details, dialog, eventsource, figure, footer, header, hgroup, mark, menu, meter, nav, output, progress, section, time, video".split(', ');
		     var i= e.length;
		     while (i--){
		         document.createElement(e[i])
		     } 
		})()
		$(function(){
	    var $div_li =$(".HotMenu h1 span");
	    $div_li.click(function(){
			$(this).addClass("active")            //当前<li>元素高亮
				   .siblings().removeClass("active");  //去掉其它同辈<li>元素的高亮
            var index =  $div_li.index(this);  // 获取当前点击的<li>元素 在 全部li元素中的索引。
			$("div.MenuTab > div")   	//选取子节点。不选取子节点的话，会引起错误。如果里面还有div 
					.eq(index).show()   //显示 <li>元素对应的<div>元素
					.siblings().hide(); //隐藏其它几个同辈的<div>元素
		}).hover(function(){
			$(this).addClass("hover");
		},function(){
			$(this).removeClass("hover");
		})
})
$(document).ready(function(){
	
	$("#fgtStudentAccount").click(function(){
	   form1.action="/sso/regist_findUserAccount.action";
	   form1.submit();
	 })
	 
    $("#fgtGroupAccount").click(function(){
	   form2.action="/sso/regist_findGroupUserAccount.action";
	   form2.submit();
    })
    
    $("#fgtStudentPwd").click(function(){
		jQuery.dialog({
			id : 'fgtStudentPwd',
			title : '<div style="text-align:left;">忘记密码</div>',
			max : false,
			min : false,
			fixed : false,
			lock: true,
			cancelVal: '关闭', 
			cancel: true ,
			width : '200px',
			height: '100px',
			content : '<div style="text-align:center;"><a style="text-decoration: none;color: #0458ae" href="/password/getPsw_identify.action" target="_blank">非从业人员重置密码入口</a><br/><br/>'
				+'<a style="text-decoration: none;color: #0458ae" href="http://person.sac.net.cn/pages/retrievePassword/retrievePassword.html" target="_blank">从业资格人员重置密码入口</a><br/>'+'<font color=red>(从业账户重置密码20分钟内生效)</font></div>'
		}); 
  	})
  	$("#reg1").click(function(){
	   form1.action="/sso/regist_registUser.action";
	   form1.submit();
	  })
  
 	$("#fgtGroupPwd").click(function(){
	   form2.action="/password/getPsw_identify.action";
	   form2.submit();
    })
	
	
});	
$(function(){
	$(".SAC-nav h1 dt").toggle(function(){
		$(this).addClass("hover");
		$(".SAC-login").show();
		$(".SAC-nav h1 dd").show();
		},function(){
			$(this).removeClass("hover");
			$(".SAC-login").hide();
			});
	$(".SAC-nav h1 div.gogo").click(function(){
		
		$(".SAC-login").hide();
		});
	
})	
					
function createCode(){
 	 var img = document.getElementById("lyt_img_id");
 	 img.src = img.src+'&'+(new Date()).getTime();
}
function createCodeJt(){
 	 var img = document.getElementById("lyt_img_id_jt");
 	 img.src = img.src+'?'+(new Date()).getTime();
}
function check1(e){
	var eve=window.event||e;
  	if (eve.keyCode == 13 ){
 // alert("qwert");
	  if(document.getElementById("id").value==""){
		alert("用户账户不能为空");
		return false;
		}
		 if(document.getElementById("pass").value==""){
		alert("用户密码不能为空");
		return false;
		}
	if(document.getElementById("authCode0").value==""){
		alert("验证码不能为空!");
		return false;
	}
	 document.form1.action="/sso/login_login.action";
	 document.form1.submit();
  }   
}
function check1_1(){
	  if(document.getElementById("id").value==""){
			alert("用户账户不能为空");
			return false;
	   }
	  if(document.getElementById("pass").value==""){
			alert("用户密码不能为空");
			return false;
	  }
	  if(document.getElementById("authCode0").value==""){
			alert("验证码不能为空!");
			return false;
	   }
	   document.form1.action="/sso/login_login.action";
	   document.form1.submit();
}
function check2(e){
 	var eve=window.event||e;
    if (eve.keyCode == 13 ){
		  if(document.getElementById("id1").value==""){
			alert("用户账户不能为空");
			return false;
		} if(document.getElementById("pass1").value==""){
			alert("用户密码不能为空");
			return false;
			}
	if(document.getElementById("authCode").length<1){
		alert("验证码不能为空!");
		return false;
	}
	 document.form2.action="/sso/login_login.action";
	document.form2.submit();
  }   
}
function check2_2(){
		if(document.getElementById("id1").value==""){
			alert("用户账户不能为空");
			return false;
		} 
		if(document.getElementById("pass1").value==""){
			alert("用户密码不能为空");
			return false;
		}
		if(document.getElementById("authCode").value==""){
			alert("验证码不能为空!");
			return false;
		}
	 document.form2.action="/sso/login_login.action";
	 document.form2.submit();
}
$("#log1").click(function(){
	   if($("#id").val()==''){
		    alert("账户不能为空");
		    return;
	   }
	   if($("#pass").val()==''){
		    alert("密码不能为空");
		    return;
	   }
	   if($("#authCode0").val()==''){
		    alert("验证码不能为空");
		    return;
	   }
	   form1.action="/sso/login_login.action";
	   form1.submit();
  });
$("#log2").click(function(){
   if($("#id1").val()==''){
    alert("账户不能为空");
    return;
   }
   if($("#pass1").val()==''){
    alert("密码不能为空");
    return;
   }
   if($("#authCode").val()==''){
    alert("验证码不能为空");
    return;
   }
   form2.action="/sso/login_login.action";
   form2.submit();
});
		</script>
	</head>
	<body>
	      <section class="SAC-logo">
		    <section class="Slogo"> 
		    </section>
          </section>
          <!----NAV---->
<div class="w1">
  <nav class="w2 SAC-nav">
  <ul class="inline-block left">
    	<li><a onClick="registMenu(this);window.open('/cms/','_self')" class="fi">首页</a></li>
	    <li><a onClick="registMenu(this);window.open('<%=basePath%>cms/flkcchannel.htm','_self')" class="fi">公告</a></li>
	    <li><a onClick="registMenu(this);window.open('<%=basePath%>cms/notice/index.htm','_self')" class="fi">课程区</a></li>
	    <li><a onClick="registMenu(this);window.open('<%=basePath%>cms/referdownload.htm','_self')" class="fi">资料库</a></li>
	    <li><a onClick="registMenu(this);window.open('<%=basePath%>cms/newfaq.htm','_self')" class="fi">常见问题</a></li>
	    <li><a onClick="registMenu(this);window.open('<%=basePath%>cms/about.htm','_self')" class="fi">关于我们</a></li>
  </ul>
   <h1 class="inline-block right">
    <dt>
     <span class="inline-block"></span> 
    </dt>
   <dd></dd>
    <!----------login-start---------->
    <section class="none SAC-login" >
     <p class="f2 h4 c" style="position:relative"><span class="inline-block">个人用户登录</span><span class="linee"></span></p>
     <form name="form1" method=post id="d1" action="" onkeydown="check1(event)" class="h4 loginForm">
        <label for="Fusername" class="f3 c2">用户名</label>
        <input name="loginId" type="text" id="id" class="input"><br/>
        <label for="Fpassword" class="f3 c2">密&nbsp;&nbsp;&nbsp;码</label>
          <input name="passwd" type="password" id="pass" class="input m6"/>
		<input type="hidden" name="loginType" value="2"/>
        <div class="Fcode">
          <label for="Fcode" class="f3 c2">验证码</label>
         <input type="text" onfocus="createCode()" id="authCode0" name="authCode0" type="text" class="inputcode1" />
		 <img src="/sso/authimg?flag=0" id="lyt_img_id" style="padding-top:3px;width:70px; height:26px;" align="absmiddle" onclick="this.src=this.src+'&'+(new Date()).getTime()" />
        </div>
        <input type="button" value="登录" class="f3 right" style="margin-top:10px;" onclick="check1_1()" id="log1"/>
        <div class="left h3" style="text-align:right;padding-top:12px;">
        	<a class="c2" id="fgtStudentPwd">忘记密码？</a>
        	<a class="c2" id="fgtStudentAccount">忘记用户名？</a>
         </div>
         <div class="clear"></div>
			<div>
				<a class="c2 inline-block left" href="/help/studoc.pdf" title='个人用户使用手册 ' target="_blank">使用手册</a>
				<a href="/sso/regist_registUser.action" id="reg1" class="c11 inline-block right">立即注册>></a>
				<div class="clear"></div>
			</div>
       <div class="h1 c12">
       *提示：从业人员请使用从业人员管理系统账户和密码登录，非从业人员请注册登录
       </div>
      </form> 
     <p class="f2 h4 c  m5">集体用户登录</p>
      <form name="form2" method=post action="" id="d2" onkeydown="check2(event)" class="h4">
        <label for="Fusername" class="f3 c2">用户名</label>
         <input	name="loginId" type="text" id="id1" class="input"><br/>
        <label for="Fpassword" class="f3 c2">密&nbsp;&nbsp;&nbsp;码</label>
        <input name="passwd" type="password" id="pass1" class="input m6"/>
		<input type="hidden" name="loginType" value="0"/>
        <div class="Fcode">
          <label for="Fcode" class="f3 c2">验证码</label>
       <input type="text" onfocus="createCodeJt()" id="authCode" name="authCode" type="text" class="inputcode1" />
	   <img src="/sso/authimg" id='lyt_img_id_jt' style="padding-top:3px;width:70px; height:26px;" align="absmiddle"  onclick="this.src=this.src+'?'+(new Date()).getTime()" />
        </div>
        <input type="button" value="登录" class="f3 right" style="margin-top:10px;" onclick="check2_2()" id="log2" />
        <div class="left h3" style="padding-top:12px;">
         	<a class="c2" id="fgtGroupPwd"> 忘记密码？</a>
			<a class="c2"  id="fgtGroupAccount">忘记用户名？</a>
          </div>
 
       	
			<div class="h3">
				<a href="/help/coldoc.pdf" target="_blank" title='集体管理员使用手册'  class="c2">使用手册</a>
			</div>
      </form> 
      <section class="Loginsystem" >
      <input type="button" name="Loginsystem" class="bordernone" onclick="window.location.href='<%=basePath %>cms/tologin.htm'"/>
    </section>
    <div class="gogo"><img src="cms/res_base/saccms_com_www/newIndex/images/bak.png"></div>
    </section>
  </h1>
</nav>      
</div>
<!----NAV---->	
<div class="w2">
				<p class="m2">
	      <span class="f7 c h4 ">
	   		找回账户
	      </span>
		  <Span class="f3 c10">
		  	请填写以下信息（两项都为必填项）
		  </Span>
	   </p>
	   <section class="b4 Identify">
			<form action="/sso/regist_findBackUserAccount.action"
					method="post" name="infoform" enctype="multipart/form-data"
					onsubmit="return checknull();">
				<h1>
			    	<span class="c10">*</span>
					<label class="f3">真实姓名：</label>
					<input type="text" name="pename" id="pename" maxlength=30 value="<s:property value="pename"/>"/>
				</h1>
				<h1>
					<span class="c10">*</span>
					<label class="f3">身份证号：</label>
					<input type="text" name="cardno" id="cardno" maxlength=30 value="<s:property value="cardno"/>"/>
				</h1>
								<s:if test="operateresult.equals(\"1\")||operateresult.equals(\"2\")">
										<font color="red" size="3">*</font>
										<strong>查询结果：</strong>
										<span><s:property value="resultMessage" escape="false"/></span>
								</s:if>	
				<h2>
								<input type="submit" value="提&nbsp;交" name="button"  class="f3" />
								<input type="button" value="清&nbsp;空" name="button"  class="f3" onclick="window.location.href='/sso/regist_findUserAccount.action'; "/>
				</h2>
				</form>
				
			</section>
			</div>
<!----链接---->
<footer class="clear SAC-footer" >
 
  <p>版权所有：中国证券业协会 咨询服务电话：010-62415115 (每天8:00-20:00)</p>
  <p>京ICP备05036061号 京公网安备110102001337 45号   版本：2.4.1</p>
</footer>
<div class="w1" style="background:#e1e1e1;padding-top:40px;">
  <section class="SAC-Link">
     <a href="http://www.csrc.gov.cn/pub/newsite/" target="_blank">
     <img src="<%=basePath%>/cms/res_base/saccms_com_www/newIndex/images/frirend 1.gif" />
     </a>
     <a href="http://www.sac.net.cn"  target="_blank">
     <img src="<%=basePath%>/cms/res_base/saccms_com_www/newIndex/images/frirend2.gif" />
     </a>
     <a href="http://person.sac.net.cn" target="_blank">
     <img src="<%=basePath%>/cms/res_base/saccms_com_www/newIndex/images/frirend3.gif" />
     </a>
     <a href="http://training.sac.net.cn" target="_blank">
     <img src="<%=basePath%>/cms/res_base/saccms_com_www/newIndex/images/frirend 4.gif" />
     </a>
     <a href="http://person.sac.net.cn/pages/yearcheck/tran-hours-out.html" target="_blank">
     <img src="<%=basePath%>/cms/res_base/saccms_com_www/newIndex/images/frirend 5.gif" /> 
     </a>
 </section>
</div>
</body>
</html>