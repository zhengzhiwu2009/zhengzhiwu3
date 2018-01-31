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
		<style type="text/css"><!--<%@ include file="css/style.css"%>-->
		.STYLE1{text-align:right}
		</style>
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

/*加载年份*/
   function years(obj, Cyear) {
    var len = 134; // select长度,即option数量
    var selObj = document.getElementById(obj);
    var selIndex = len - 1;
    var newOpt; // OPTION对象

    // 循环添加OPION元素到年份select对象中
    for (i = 1; i <= len; i++) {
        if (selObj.options.length <= len) { // 如果目标对象下拉框升度不等于设定的长度则执行以下代码
            newOpt = window.document.createElement("OPTION"); // 新建一个OPTION对象
            newOpt.text = Cyear - i+1; // 设置OPTION对象的内容
            newOpt.value = Cyear - i+1; // 设置OPTION对象的值
            selObj.options.add(newOpt, i); // 添加到目标对象中
        }
    }
}

function months(){
    var month = document.getElementById("month");
    month.length = 0;  
    for (i = 1; i < 13; i++) {  
        month.options.add(new Option(i, i));  
    }
}
function change_date(){  
   // var birthday = document.birthday;  
    var year  = document.getElementById("year");  
    var month = document.getElementById("month");  
    var date   = document.getElementById("date");  
    vYear  = parseInt(year.value);  
    vMonth = parseInt(month.value);  
    date.length=0;  
    //根据年月获取天数  
    var max = (new Date(vYear,vMonth, 0)).getDate();  
    for (var i=1; i <= max; i++) {  
        date.options.add(new Option(i, i));  
    }  
}
</script>
<script language="javascript">
	function checknull(){
		var Phone = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,8}(\-\d{1,5})?$/;
		var Mobile = /^((\(\d{2,3}\))|(\d{3}\-))?1[3|5|8|4]\d{9}$/;
		var Email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
		var JobNumber = /^[\u0391-\uFFE5-a-zA-Z0-9]{0,25}$/;
		var CardNoReg = /(^\d{15}$)|(^\d{17}([0-9]|X|x)$)/;
		var pe = document.getElementById("enterprises").value;
		var peId = pe.split("-");
		
		if(document.getElementById("peBzzStudent.trueName").value==""){
			alert('请输入真实姓名!');
			document.getElementById("peBzzStudent.trueName").focus();
			return false;
		}
		if(peId[1] != "V" && document.getElementById("peBzzStudent.cardType").value==""){
			alert('请选择国籍!');
			document.getElementById("peBzzStudent.cardType").focus();
			return false;
		}
		//if(!document.getElementById("gender1").checked&&!document.getElementById("gender2").checked){
			//alert('请选择性别!');
			//return false;
		//}
		//var year = document.getElementById("year").value;
		//var month = document.getElementById("month").value;
		//var date = document.getElementById("date").value;
		//if(!(year==""&&month==""&&date=="")&&!(year!=""&&month!=""&&date!="")){
			//alert('请填写完整的出生日期!');
			//return false;
		//}
		if(document.getElementById("password").value==""){
			alert('请输入密码!');
			document.getElementById("password").focus();
			return false;
		}
		if(document.getElementById("confirmpassword").value==""){
			alert('请输入确认密码!');
			document.getElementById("confirmpassword").focus();
			return false;
		}
		if(document.getElementById("password").value!=document.getElementById("confirmpassword").value){
			alert("两次输入的密码不一致，请从新输入！");
			document.getElementById("password").value="";
			document.getElementById("confirmpassword").value="";
			document.getElementById("password").focus();
			return false;
		}
		if(peId[1] != "V" && document.getElementById("peBzzStudent.cardNo").value==""){
			alert('请输入身份证号!');
			document.getElementById("peBzzStudent.cardNo").focus();
			return false;
		}else if(document.getElementById("peBzzStudent.cardNo").value!="") {
			//证件号格式校验
			if(!checkCardNo()){
				return false;
			}
			//证件号注册校验
		    if(cardNoisexist()){
		    	return false;
		    }
		}
		if(peId[1] != "V" && document.getElementById("peBzzStudent.education").value==""){
			alert('请选择学历!');
			document.getElementById("peBzzStudent.education").focus();
			return false;
		}
		if(document.getElementById("peBzzStudent.email").value==""){
  			window.alert("邮箱不能为空！");
  			document.getElementById("peBzzStudent.email").focus();
			return false;
		}else{
			var email=document.getElementById("peBzzStudent.email").value;
			//var pattern= /^([a-z.A-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
			//var pattern=/^\\w+([-+.]\\w+)*@\\w+([-]\\w+)*(\\.\\w+([-]\\w+)*){1,3}$/;
			var  email_check=/^\w+([-+.]\w+)*@\w+([-+.]\w+)*$/;
			if(!email_check.test(email)){
				alert("邮箱格式不正确！\n请重新输入");
				//document.getElementById("peBzzStudent.email").value="";
				document.getElementById("peBzzStudent.email").focus();
				return false;
			}
		}
		if(peId[1] != "V" && document.getElementById("peBzzStudent.mobilePhone").value==""){
			window.alert("手机号码不能为空！");
  			document.getElementById("peBzzStudent.mobilePhone").focus();
			return false;
		}else if(document.getElementById("peBzzStudent.mobilePhone").value!=""){ 
			var myMobile_no=document.getElementById("peBzzStudent.mobilePhone").value;
			var myreg=/^1[3|4|5|8][0-9]\d{4,8}$/;
			if(!myreg.test(myMobile_no)){
				alert("手机号码不合法！\n请重新输入");
				//document.getElementById("peBzzStudent.mobilePhone").value="";
				document.getElementById("peBzzStudent.mobilePhone").focus();
				return false;
			}
		}
		/*if(document.getElementById("peBzzStudent.enumConstByFlagStudentType.id").options[window.document.getElementById("peBzzStudent.enumConstByFlagStudentType.id").selectedIndex].text=="否"){
			if(document.getElementById("peBzzStudent.enterpriseId").value==""){
				alert('请选择机构名称!');
				document.getElementById("peBzzStudent.enterpriseId").focus();
				return false;
			}
		}*/
		if(document.getElementById("peBzzStudent.enterpriseId").value=="其它"&&document.getElementById("peBzzStudent.pename").value==""){
			//alert('请填写机构名称!');
			//document.getElementById("pename").focus();
			//return false;
		} else if(peId[1] == "V") {
			if(document.getElementById("peBzzStudent.department").value=="") {
				alert("请填写工作部门");
				return false;
			}
			if(document.getElementById("peBzzStudent.phone").value=="") {
				alert("请填写办公电话");
				return false;
			}
		}
		
		if(peId[1] == "V" && document.getElementById("peBzzStudent.cardNo").value == "") {
			document.getElementById("peBzzStudent.cardNo").value = "-";
		}
		return true;
	}
	function checkCardX(regCardNo){
		var year = document.getElementById("year").value;
		var month = document.getElementById("month").value;
		month = month.length==1?0+""+month:month;
		var day = document.getElementById("date").value;
		day = day.day==1?0+""+day:day;
		var birth=year+""+month+""+day;
		if(!isNaN(year) && !isNaN(month) && !isNaN(day)){
			if(regCardNo.length == 15){
				var cardYear = regCardNo.substring(6,8);
				var cardMonth = regCardNo.substring(8,10);
				var cardDate = regCardNo.substring(10,12);
				var cardBirth=cardYear+""+cardMonth+""+cardDate;
				if(birth.indexOf(cardBirth) == -1){
		            alert("身份证中出生日期与注册的出生日期不一致！");
		            return false;
	            }else{
	            	return true;
	            }
			}else if(regCardNo.length == 18){
				var cardYear = regCardNo.substring(6,10);
				var cardMonth = regCardNo.substring(10,12);
				var cardDate = regCardNo.substring(12,14);
				var cardBirth=cardYear+""+cardMonth+""+cardDate;
				if(cardBirth!=birth){
		            alert("身份证中出生日期与注册的出生日期不一致！");
		            return false;
	            }else{
	            	return true;
	            }
			}else{
		        alert('输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。');
	            return false;
			}
		}
		if(!isNaN(year) || !isNaN(month) || !isNaN(day)){
            alert("身份证中出生日期与注册的出生日期不一致！");
			return false;
		}
		return true;
	}
	//验证身份证号是否已存在
	function checkCardNo(){
		var CardNoReg = /^[a-zA-Z0-9]{6,25}$/;
		var num = document.getElementById("peBzzStudent.cardNo").value;
		num = num.toUpperCase();
		//设置身份证小写字符为大写
		document.getElementById("peBzzStudent.cardNo").value=num;
		if(document.getElementById("peBzzStudent.cardType").value=="中国"){//中国证件号验证
			if(num.indexOf("X") != -1){
				return checkCardX(num);
			}else{
				 if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {
			        alert('输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。');
			        return false;
			    }
			    // 校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
			    // 下面分别分析出生日期和校验位
			    /*
			    var len, re; len = num.length;
			    if (len == 15) {
			        re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
			        //var arrSplit = num.match(re);  // 检查生日日期是否正确
			        var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
			        var dtmBirth1 = new Date('20' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
			        var bGoodDay;
			        bGoodDay = (
				       		(dtmBirth.getYear() == Number(arrSplit[2])) 
					        	&& ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) 
					        	&& (dtmBirth.getDate() == Number(arrSplit[4]))
					        ||
					        (dtmBirth1.getYear() == Number(arrSplit[2])) 
					        	&& ((dtmBirth1.getMonth() + 1) == Number(arrSplit[3])) 
					        	&& (dtmBirth1.getDate() == Number(arrSplit[4]))
			        	);
			        	
			        if (!bGoodDay) {
			            alert('输入的身份证号里出生日期不对！');
			            return false;
			        } else { 
			       		// modify at 2013-12-16
			           var birth="19"+num.substring(6, 12);
			           var year = document.getElementById("year").value;
			           var month = document.getElementById("month").value;
			           var date = document.getElementById("date").value;
			           if(!year||!month||!date){
			           if(month.length<2){
			            month="0"+month;
			           }
			           if(date.length<2){
			            date="0"+date;
			           }
			          var editBirth=year+month+date;
			          alert(editBirth+":"+birth);
			           if(birth!=editBirth){
			             alert("身份证中出生日期与注册的出生日期不一致！");
			             document.getElementById("peBzzStudent.cardNo").value="";
			             document.getElementById("peBzzStudent.cardNo").focus();
			             return false;
			           }
			           }
			       	 // modify  end;
			       	 // 将15位身份证转成18位 //校验位按照ISO 7064:1983.MOD
			    	 // 11-2的规定生成，X可以认为是数字10。
			            var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
			            var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
			            var nTemp = 0, i;
			            num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
			            for (i = 0; i < 17; i++) {
			                nTemp += num.substr(i, 1) * arrInt[i];
			            }
			            num += arrCh[nTemp % 11];
			            //return true;
			        }
			    }
			    if (len == 18) {
			        re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
			        var arrSplit = num.match(re);  // 检查生日日期是否正确
			        var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
			        var bGoodDay; 
			        bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
			        if (!bGoodDay) {
			            alert('输入的身份证号里出生日期不对！');
			            return false;
			        }
			        else { 
			        // modify at 2013-12-16
			           var birth=num.substring(6, 14);
			           var year = document.getElementById("year").value;
			           var month = document.getElementById("month").value;
			           var date = document.getElementById("date").value;
			            if(!year||!month||!date){
			           if(month.length<2){
			            month="0"+month;
			           }
			           if(date.length<2){
			            date="0"+date;
			           }
			          var editBirth=year+month+date;
			           if(birth!=editBirth){
			             alert("身份证中出生日期与注册的出生日期不一致！");
			             document.getElementById("peBzzStudent.cardNo").value="";
			             document.getElementById("peBzzStudent.cardNo").focus();
			             return false;
			           }
			           }
			        // modify  end;
			        // 检验18位身份证的校验码是否正确。 //校验位按照ISO 7064:1983.MOD
			   			// 11-2的规定生成，X可以认为是数字10。
			            var valnum;
			            var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
			            var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
			            var nTemp = 0, i;
			            for (i = 0; i < 17; i++) {
			                nTemp += num.substr(i, 1) * arrInt[i];
			            }
			            valnum = arrCh[nTemp % 11];
			            if (valnum != num.substr(17, 1)) {
			                alert('18位身份证的校验码不正确！');
			                return false;
			            }
			        }
			    }
			    */
				return true;
			}
		}else{
			if(!CardNoReg.test(document.getElementById("peBzzStudent.cardNo").value)){
				alert('输入的身份证号长度不对，或者号码不符合规定！\n6~25位字母与数字的组合。 ');
		        return false;
			}
			return true;
		}
	}
	function cardNoisexist(){
		var flag=false;
		var num = document.getElementById("peBzzStudent.cardNo").value;
		var url="/sso/regist_isUsed.action?cardno=" + num+"&x="+Math.random();
		if (window.XMLHttpRequest) {
	        req = new XMLHttpRequest();
	    }
	    else if (window.ActiveXObject) {
	        req = new ActiveXObject("Microsoft.XMLHTTP");
	    }
	    req.open("Get",url,false);
	    req.onreadystatechange = function (){
		    if(req.readyState == 4){
				if(req.status == 200){
					var textResult = req.responseText;
					if (textResult == '1'){
						alert('所输入的身份证号已经注册，请使用首页“忘记账号？”功能找回账号，如身份证号码被恶意注册，请联系 010-62415115  training@sac.net.cn！');
						//document.getElementById("peBzzStudent.cardNo").value="";
						document.getElementById("peBzzStudent.cardNo").focus();
						flag=true;
					}else{
						flag=false;					
					}
				} 
			}
	    };
	    req.send(null);
		return flag;
	}
	function blank(){
		var enterprise = document.getElementById("enterprises");
		var peId = enterprise.value.split("-");
		document.getElementById("peBzzStudent.enterpriseId").value = peId[0];
		if(peId[0]=="其它"){
			//document.getElementById("enter").style.display = '';
			document.getElementById("dw").style.display="inline";
			document.getElementById("mo").style.display = 'none';
			document.getElementById("mo1").style.display = 'block';
			document.getElementById("gj").style.display = 'block';
			document.getElementById("gj1").style.display = 'none';
			document.getElementById("xl").style.display = 'block';
			document.getElementById("xl1").style.display = 'none';
			document.getElementById("zj").style.display = 'block';
			document.getElementById("zj1").style.display = 'none';
			document.getElementById("sj").style.display = 'block';
			document.getElementById("sj1").style.display = 'none';
			document.getElementById("zw").style.display = '';
			document.getElementById("dp").style.display = 'none';
		}else if(peId[1] == "V") {
			//document.getElementById("enter").style.display = 'none';
			document.getElementById("dw").style.display="none";
			document.getElementById("mo").style.display = 'block';
			document.getElementById("mo1").style.display = 'none';
			document.getElementById("gj").style.display = 'none';
			document.getElementById("gj1").style.display = 'block';
			document.getElementById("xl").style.display = 'none';
			document.getElementById("xl1").style.display = 'block';
			document.getElementById("zj").style.display = 'block';
			document.getElementById("zj1").style.display = 'none';
			document.getElementById("sj").style.display = 'none';
			document.getElementById("sj1").style.display = 'block';
			document.getElementById("zw").style.display = 'none';
			document.getElementById("dp").style.display = '';
		}else{
			//document.getElementById("enter").style.display = 'none';
			document.getElementById("dw").style.display="none";
			document.getElementById("mo").style.display = 'none';
			document.getElementById("mo1").style.display = 'block';
			document.getElementById("gj").style.display = 'block';
			document.getElementById("gj1").style.display = 'none';
			document.getElementById("xl").style.display = 'block';
			document.getElementById("xl1").style.display = 'none';
			document.getElementById("zj").style.display = 'block';
			document.getElementById("zj1").style.display = 'none';
			document.getElementById("sj").style.display = 'block';
			document.getElementById("sj1").style.display = 'none';
			document.getElementById("zw").style.display = '';
			document.getElementById("dp").style.display = 'none';
		}
	}
function checkEmail() {
	var email=document.getElementById("peBzzStudent.email").value;
	//	var pattern= /^([a-z.A-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
	var  email_check=/^\w+([-+.]\w+)*@\w+([-+.]\w+)*$/;
	if(!email_check.test(email)){
		alert("邮箱格式不正确！\n请重新输入");
		//document.getElementById("peBzzStudent.email").value="";
		document.getElementById("peBzzStudent.email").focus();
		return false;
	}
}
function checkMobilePhone() {
	var myMobile_no=document.getElementById("peBzzStudent.mobilePhone").value;
	var myreg=/^1[3|4|5|8][0-9]\d{4,8}$/;
	if(!myreg.test(myMobile_no)){
		alert("手机号码不合法！\n请重新输入");
		//document.getElementById("peBzzStudent.mobilePhone").value="";
		document.getElementById("peBzzStudent.mobilePhone").focus();
		return false;
	}
}
/**
 *用于校验密码是否符合规则
 */
function checkPassword() {
	var password=document.getElementById("password").value;
	var r = /^\d+$/;    //正则纯数字
	var m = /^[a-zA-Z]+$/;  //纯字母
	if(password.length < 8 || password.length > 16) {
		alert('密码必须由8-16位组成，且不能为纯数字或纯字母');
		document.getElementById("password").value = '';
		return false;
	}
	if(r.test(password)) {
		alert('您的密码安全等级太低，不能由纯数字组成');
		document.getElementById("password").value = '';
		return false;
	}
	if(m.test(password)) {
		alert('您的密码安全等级太低，不能由纯字母组成');
		document.getElementById("password").value = '';
		return false;
	}
}
/**
 *用于校验两次密码是否相等
 **/
function checkEqu() {
	var password=document.getElementById("password").value;
	var confirmpassword =document.getElementById("confirmpassword").value;
	if(password != confirmpassword) {
		alert('两次输入密码不同');
		//document.getElementById("confirmpassword").value = '';
		return false;
	}
}
//验证真实姓名
function checkTrueName(){
	var textval=document.getElementById("peBzzStudent.trueName").value;
	var check=/^[\u4E00-\u9FFF]+$|^[a-zA-Z]+$|^[\u4E00-\u9FFF]+(·[\u4E00-\u9FFF]+)+$|^[a-zA-Z]+([ |·][a-zA-Z]+)+$/;
	if(!check.test(textval)){ 
		alert("请输入正确的名字格式！中文姓名由纯汉字或汉字+分隔符'·'构成；英文姓名由纯字符或字符+分隔符'·'（或空格）构成");
		document.getElementById("peBzzStudent.trueName").value="";
		document.getElementById("peBzzStudent.trueName").focus();
		return false;
	}
}
//机构名称输入框
function setDropDown(str){
	 var ops=document.getElementById("enterprises").options;//原有列表
	 var ops2=document.getElementById("enterprises2").options;//暂存列表
	 if(str==""){
		 $("#enterprises").empty();
		 for(var i=0;i<ops2.length;i++){
		   $("#enterprises").append("<option value=\""+ops2[i].value+"\">"+ops2[i].text+"</option>");
		 }
	 }else{
		 $("#enterprises").empty();
		 for(var i=0;i<ops2.length;i++){
			 var a = ops2[i].text;
			 while(a.indexOf(str) != -1){		//如果该项的value等于文本框填写的值
				   $("#enterprises").append("<option value=\""+ops2[i].value+"\">"+ops2[i].text+"</option>");
				 break;
		  	}
		}
		var ops_=document.getElementById("enterprises").options;//原有列表
		if(ops_.length<=0){
		 $("#enterprises").empty();
		 for(var i=0;i<ops2.length;i++){
		   $("#enterprises").append("<option value=\""+ops2[i].value+"\">"+ops2[i].text+"</option>");
		 }
		}
	 } 
}
</script>
	</head>
<body>
	<section class="SAC-logo">
		<section class="Slogo"></section>
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
				<dd>
				</dd>
			    <!----------login-start---------->
			    <section class="none SAC-login" >
					<p class="f2 h4 c" style="position:relative"><span class="inline-block">个人用户登录</span><span class="linee"></span></p>
						<form name="form1" method=post id="d1" action="" onkeydown="check1(event)" class="h4 loginForm">
							<label for="Fusername" class="f3 c2">用户名</label>
        					<input name="loginId" type="text" id="id" class="input" /><br/>
							<label for="Fpassword" class="f3 c2">密&nbsp;&nbsp;&nbsp;码</label>
							<input name="passwd" type="password" id="pass" class="input m6"/>
							<input type="hidden" name="loginType" value="2"/>
							<div class="Fcode">
								<label for="Fcode" class="f3 c2">验证码</label>
         						<input type="text" onfocus="createCode()" id="authCode0" name="authCode0" type="text" class="inputcode1" />
								<img src="/sso/authimg?flag=0" id="lyt_img_id" style="padding-top:3px;width:70px; height:26px;" align="absmiddle" onclick="this.src=this.src+'&'+(new Date()).getTime()" />
        					</div>
         					<input type="button" value="登录" onclick="check1_1()" id="log1" style="margin-top:10px;" class="f3 right"/>
							<div class="left h3" style="text-align:right;padding-top:12px;">
								<a class="c2" id="fgtStudentAccount">忘记用户名？</a>
								<a class="c2" id="fgtStudentPwd">忘记密码？</a>   
							</div>
        					<div class="clear"></div>
							<div >
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
         					<input	name="loginId" type="text" id="id1" class="input" /><br/>
					        <label for="Fpassword" class="f3 c2">密&nbsp;&nbsp;&nbsp;码</label>
					        <input name="passwd" type="password" id="pass1" class="input m6"/>
							<input type="hidden" name="loginType" value="0"/>
					        <div class="Fcode">
								<label for="Fcode" class="f3 c2">验证码</label>
					        	<input type="text" onfocus="createCodeJt()" id="authCode" name="authCode" type="text" class="inputcode1" />
						    	<img src="/sso/authimg" id='lyt_img_id_jt' style="padding-top:3px;width:70px; height:26px;" align="absmiddle"  onclick="this.src=this.src+'?'+(new Date()).getTime()" />
					        </div>
					        <input type="button" value="登录" class="f3 right" onclick="check2_2()" id="log2" style="margin-top:10px;"/>
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
					    <div class="gogo">
					    	<img src="cms/res_base/saccms_com_www/newIndex/images/bak.png" />
					   	</div>
					</section>
				</h1>
			</nav>      
		</div>
		<!----NAV---->	
		<div class="" style="width: 100%; padding: 0; margin: 0;" align="center">
			<div style="width:960px;border:1px solid #ccc;background:#fff">
				<p class="m2">
					<span class="f7 c h4">
						新学员注册
					</span><br/>
					<span class="f3 c10 h2">
						请认真填写以下信息（带“*”号项必须填写）
			  		</span><br/>
					<span class="f3 c10 h2">
			 			提示：选择'国籍'为'中国'时，证件号码需要学员填写18位的身份证号
			  		</span>
				</p>
				<div class="main_txt">
					<form action="/sso/regist_registSucess.action" method="post" name="infoform" enctype="multipart/form-data" onsubmit="return checknull();">
						<input type="hidden" id="peBzzStudent.enterpriseId" name="peBzzStudent.enterpriseId" value="其他"/>
						<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<table class="datalist" width=" 100%" order="0" cellpadding="0" cellspacing="0" bgcolor="#D7D7D7">
										<tr>
											<td width="200px" align="center" bgcolor="#E9F4FF" class="STYLE1" style="text-align:right">
												<font color="red" size="3">*</font>
												<strong>真实姓名：</strong>
											</td>
											<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" >
												&nbsp;
												<input type="text" name="peBzzStudent.trueName" id="peBzzStudent.trueName" onchange="checkTrueName();" maxlength=30 />
												<font color="red">&nbsp;&nbsp;请填写真实姓名，可用于找回账号</font><br/>
											</td>
										</tr>
										<tr>	
											<td align="center" bgcolor="#E9F4FF" class="STYLE1" style="text-align:right">
												<span id="gj" style="display:block;"><font color="red" size="3">*</font><strong>国&nbsp;&nbsp;籍：</strong></span>
												<span id="gj1" style="display:none;"><font color="red" size="3">&nbsp;</font><strong>国&nbsp;&nbsp;籍：</strong></span>
											</td>
											<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" >
												&nbsp;
												<!-- <input type="text" name="peBzzStudent.cardType" id="peBzzStudent.cardType" maxlength=30> -->
												<select name="peBzzStudent.cardType" id="peBzzStudent.cardType" style="width:154px">
													<option value="中国">
															中国
														</option>
														<option value="中国香港">
															中国香港
														</option>
														<option value="中国澳门">
															中国澳门
														</option>
														<option value="中国台湾">
															中国台湾
														</option>
														<option value="美国">
															美国
														</option>
														<option value="英国">
															英国
														</option>
														<option value="法国">
															法国
														</option>
														<option value="其他">
															其他
														</option>
												</select>
											</td>
										</tr>
										<tr>	
											<td align="center" bgcolor="#E9F4FF" class="STYLE1" style="text-align:right">
												<font color="red" size="3">*</font>
												<strong>性&nbsp;&nbsp;别：</strong>
											</td>
											<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" >
												<input type="radio" name="peBzzStudent.gender" id="gender1" value="男" />男&nbsp;&nbsp;&nbsp;&nbsp;
		                                  		<input type="radio" name="peBzzStudent.gender" id="gender2" value="女" />女
											</td>
										</tr>
										<tr>
											<td align="center" bgcolor="#E9F4FF" class="STYLE1">
												<font color="red" size="3">*</font>
												<strong>密&nbsp;&nbsp;码：</strong>
											</td>
											<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
												&nbsp;
												<input type="password" name="password" id="password" maxlength=16  style="width:150px;background:#fff;height:14px;" onchange="checkPassword();" />
												<font color="red">密码必须由8-16位组成，不能为纯数字或纯字母</font>
											</td>
										</tr>
										<tr>	
											<td align="center" bgcolor="#E9F4FF" class="STYLE1">
												<font color="red" size="3">*</font>
												<strong>确认密码：</strong>
											</td>
											<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" >
												&nbsp;
												<input type="password" name="confirmpassword" id="confirmpassword" style="width:150px;background:#fff;height:22px;BORDER:1px solid #cccccc" maxlength=16 style="width:150px" onchange="checkEqu();" />
											</td>
										</tr>
										<tr>
											<td align="center" bgcolor="#E9F4FF" class="STYLE1">
												<span id="xl" style="display:block;"><font color="red" size="3">*</font> <strong>学&nbsp;&nbsp;历：</strong></span>
												<span id="xl1" style="display:none;"><font color="red" size="3">&nbsp;</font> <strong>学&nbsp;&nbsp;历：</strong></span>
											</td>
											<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" >
												&nbsp;
												<select name="peBzzStudent.education" id="peBzzStudent.education" style="width:154px">
													<option value="初中" >
														初中
													</option>
													<option value="高中" >
														高中
													</option>
													<option value="职高" >
														职高
													</option>
													<option value="中专" >
														中专
													</option>
													<option value="技校" >
														技校
													</option>
													<option value="大专" >
														大专
													</option>
													<option value="本科" >
														本科
													</option>
													<option value="硕士" >
														硕士
													</option>
													<option value="博士" >
														博士
													</option>
												</select>
											</td>
										</tr>
										<tr>	
											<td align="center" bgcolor="#E9F4FF" class="STYLE1">
												<span id="zj" style="display:block;"><font color="red" size="3">*</font><strong>证件号码：</strong></span>
												<span id="zj1" style="display:none;"><font color="red" size="3">&nbsp;</font><strong>证件号码：</strong></span>
											</td>
											<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" >
												&nbsp;
												<input type="text" name="peBzzStudent.cardNo" id="peBzzStudent.cardNo" maxlength="30" onchange="checkCardNo();cardNoisexist();"/>
												<font color="red">&nbsp;&nbsp;可进行找回账号</font>
											</td>									
										</tr>	
										<tr>
											<td align="center" bgcolor="#E9F4FF" class="STYLE1">
												<font color="red" size="3">*</font><strong>邮&nbsp;&nbsp;箱：</strong>
											</td>
											<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
												&nbsp;
												<input type="text" name="peBzzStudent.email" id="peBzzStudent.email" maxlength=30 style="sime-mode:disabled" onchange="checkEmail();"/>
												<font color="red">&nbsp;&nbsp;可通过邮箱找回密码</font>
											</td>
										</tr>
										<tr>	
											<td align="center" bgcolor="#E9F4FF" class="STYLE1">
												<span id="sj" style="display:block;"><font color="red" size="3">*</font><strong>手&nbsp;&nbsp;机：</strong></span>
												<span id="sj1" style="display:none;"><font color="red" size="3">&nbsp;</font><strong>手&nbsp;&nbsp;机：</strong></span>
											</td>
											<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
												&nbsp;
												<input type="text" name="peBzzStudent.mobilePhone" id="peBzzStudent.mobilePhone" maxlength=30 onchange="checkMobilePhone();"/>
											</td>
										</tr>
										<tr>
											<td align="center" bgcolor="#E9F4FF" class="STYLE1">
												<font color="red" size="3" >&nbsp;</font> <strong>机构名称：</strong>
											</td>
											<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" >
												<input type="text" id="enterprisesInput" onpropertychange="setDropDown(this.value);" value="机构关键字..." onfocus="this.select()" /><br />
												<select name="enterprises" id="enterprises" onchange="blank();"> 
													<option value="其它">其它</option>
														<s:if test="peEnterpriseList != null && peEnterpriseList.size() > 0">
															<s:iterator value="peEnterpriseList" id="enterprise">
																<option value="<s:property value="#enterprise[0]"/>"><s:property value="#enterprise[1]"/></option>
															</s:iterator>
														</s:if>
														</select>
												<select name="enterprises2" id="enterprises2" onchange="blank();" style="display:none">  
													<option value="其它">其它</option>
													<s:if test="peEnterpriseList != null && peEnterpriseList.size() > 0">
														<s:iterator value="peEnterpriseList" id="enterprise">
															<option value="<s:property value="#enterprise[0]"/>"><s:property value="#enterprise[1]"/></option>
														</s:iterator>
													</s:if>
													</select>
												<font color="red">提交后不可修改</font>	
											</td>
										</tr>
										<tr id="enter" style="display:">
											<td align="center" bgcolor="#E9F4FF" class="STYLE1">
												<span id="dw" style="display:inline"><font color="red" size="3">*</font></span><strong>单位名称：</strong>
											</td>
											<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">&nbsp;
												<input type="text" name="pename" id="pename" size="50" onclick ="blank();"/><br />
												<font color="red">&nbsp;&nbsp;机构名称选择“其它”时，此为必填项，且提交后不可修改。</font>
											</td>
										</tr>
										<tr>
											<td align="center" bgcolor="#E9F4FF" class="STYLE1">
												<strong>邮&nbsp;&nbsp;编：</strong>
											</td>
											<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
												&nbsp;
												<input type="text" name="peBzzStudent.zipcode" id="peBzzStudent.zipcode" maxlength=6 />
											</td>
										</tr>
										<tr>
											<td align="center" bgcolor="#E9F4FF" class="STYLE1">
												<span id="dp" style="display:none;"><font color="red" size="3">*</font></span><strong>工作部门：</strong>
											</td>
											<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
												&nbsp;
												<input type="text" name="peBzzStudent.department" id="peBzzStudent.department" maxlength=30 />
											</td>
										</tr>
										<tr id="zw">	
											<td align="center" bgcolor="#E9F4FF" class="STYLE1" style="text-align:right">
												<span><strong>从事业务：</strong></span>
											</td>
											<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
												&nbsp;
												<input type="text" name="peBzzStudent.work" id="peBzzStudent.position" maxlength=30 />
											</td>
										</tr>
										<tr>	
											<td align="center" bgcolor="#E9F4FF" class="STYLE1">
												<span id="zw1"><strong>职&nbsp;&nbsp;务：</strong></span>
											</td>
											<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
												&nbsp;
												<input type="text" name="peBzzStudent.position" id="peBzzStudent.position" maxlength=30 />
											</td>
										</tr>
										<tr>
											<td align="center" bgcolor="#E9F4FF" class="STYLE1">
												<strong>职&nbsp;&nbsp;称：</strong>
											</td>
											<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
												&nbsp;
												<input type="text" name="peBzzStudent.title" id="peBzzStudent.title" maxlength=30 />
											</td>
											</tr>
										<tr>	
											<td align="center" bgcolor="#E9F4FF" class="STYLE1">
												<span id="mo" style="display:none;"><font color="red" size="3">*</font><strong>办公电话：</strong></span>
												<span id="mo1" style="block;"><strong>办公电话：</strong></span>
											</td>
											<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
												&nbsp;
												<input type="text" name="peBzzStudent.phone" id="peBzzStudent.phone" maxlength=30 />
											</td>
										</tr>
										<tr>
											<td align="center" bgcolor="#E9F4FF" class="STYLE1">
												<strong>通讯地址：</strong>
											</td>
											<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" >
												&nbsp;
												<input type="text" name="peBzzStudent.address" id="peBzzStudent.address"  size="50"/>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<table border="0" align="center" cellpadding="0" cellspacing="0" height="64" valign="bottom">
										<tr>
											<td>
												<input type="submit" value="注 &nbsp;册" stype="cursor:hand" />
											</td>
											<td width="40" style="color:#fff">&nbsp;&nbsp;&nbsp;&nbsp;</td>
											<td>
												<input type="button" value="取 &nbsp;消" stype="cursor:hand"  onclick="window.location.href='/cms';"/>
											</td>
										</tr>
									</table>
								</td>
							</tr> 
						</table>
					</form>
				</div>
				<div>
					<img src="cms/res_base/saccms_com_www/default/article/images/index_1000.jpg" width="960" align="absmiddle" />
				</div>
			</div>
		</div>
		<!----链接---->
		<footer class="clear SAC-footer " >
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
