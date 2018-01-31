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
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中国证劵业协会培训平台</title>
<link href="/entity/bzz-students/css/style.css" rel="stylesheet"
	type="text/css" />
<link href="/entity/bzz-students/css/reset.css" rel="stylesheet"
	type="text/css" />
<link href="/entity/bzz-students/css1.css" rel="stylesheet"
	type="text/css" />
<link href="/entity/manager/statistics/css/admincss.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" media="all"
	href="/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />
<!-- 	<script type="text/javascript" src="/js/calendar/calendar.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
		<script language="javascript" src="/js/calender.js"></script> -->
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
<script>
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
		var Email = /^\w+([-+.]\w+)*@\w+([-+.]\w+)*$/;
		var JobNumber = /^[\u0391-\uFFE5-a-zA-Z0-9]{0,25}$/;
		var CardNoReg = /(^\d{15}$)|(^\d{17}([0-9]|X|x)$)/;		
		
		if(document.getElementById("peBzzStudent.trueName").value=="") {
			window.alert("姓名不能为空！");
  			document.getElementById("peBzzStudent.trueName").focus();
			return false;
		}
		
		if(document.getElementById("peBzzStudent.mobilePhone").value==""){
			window.alert("手机号码不能为空！");
  			document.getElementById("peBzzStudent.mobilePhone").focus();
			return false;
		}else{ 
		
			var myMobile_no=document.getElementById("peBzzStudent.mobilePhone").value;
			var myreg=/^1[3|4|5|8][0-9]\d{4,8}$/;
			if(!myreg.test(myMobile_no))
			{
				alert("手机号码不合法！\n请重新输入");
				document.getElementById("peBzzStudent.mobilePhone").value="";
				document.getElementById("peBzzStudent.mobilePhone").focus();
				return false;
			}
		}
		var em = document.getElementById('peBzzStudent.email').value;
		if(em=="") {
			window.alert('邮箱不能为空！');
			document.getElementById('peBzzStudent.email').focus();
			return false;
		} else {
			var  email_check=/^\w+([-+.]\w+)*@\w+([-+.]\w+)*$/;
			if(!email_check.test(em))
			{
				alert("邮箱格式不正确！\n请重新输入");
				document.getElementById("peBzzStudent.email").value="";
				document.getElementById("peBzzStudent.email").focus();
				return false;
			}
		
		}
		var year = document.getElementById('year').value;
	
		var month = document.getElementById('month').value;
		var day = document.getElementById('date').value;
		if(null != year && year != '')
			document.getElementById('peBzzStudent.birthdayDate').value = year+'-'+month+'-'+day;
	}
	
	function changePassEmail() {
		var choosePassEmail = document.getElementsByName("choosePassEmail");
		for(var i=0; i < choosePassEmail.length; i++) {
			if(choosePassEmail[i].checked) {
				if(choosePassEmail[i].value == 'passEmail') {
					var passSpan = document.getElementById("passEmailSpan");
					passSpan.style.display = "block";
				} else {
					var passSpan = document.getElementById("passEmailSpan");
					passSpan.style.display = "none";
				}
			}
		}
	}
</script>
</head>
<body>
	<div class="" style="width: 100%; padding: 0; margin: 0;"
		align="center">
		<div class="main_title">个人信息修改</div>
		<div class="main_txt">
			<form
				action="/entity/workspaceStudent/studentWorkspace_modifyPassEmail.action"
				method="post" name="infoform" enctype="multipart/form-data"
				onsubmit="return checknull();">
				<input type="hidden" name="peBzzStudent.id"
					value="<s:property value="peBzzStudent.id"/>" />
				<table width="100%" border="0" align="left" cellpadding="0"
					cellspacing="0">
					<tr>
						<td>
							<table class="datalist" width=" 100%" order="0" cellpadding="0"
								cellspacing="0" bgcolor="#D7D7D7">
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
										<font color="red" size="3">*</font><strong>姓 名：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
										&nbsp;<s:property value="peBzzStudent.trueName"/>
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<font color="red" size="3">*</font><strong>性 别：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
										&nbsp;<s:property value="peBzzStudent.gender"/>
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="kctx_zi2">
										<div align="center" class="STYLE1">
											<font color="red" size="3">*</font><strong>民 族：</strong>
										</div>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
										&nbsp;<s:property value="peBzzStudent.folk" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>出生日期：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
										&nbsp;<s:date name="peBzzStudent.birthdayDate" format="yyyy-MM-dd" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
										<font color="red" size="3">*</font><strong>国 籍：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
										&nbsp;<s:property value="peBzzStudent.cardType"/>
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<font color="red" size="3">*</font><strong>学 历：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
										&nbsp;<s:property value="peBzzStudent.education"/>
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>工作部门：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
										&nbsp;<s:property value="peBzzStudent.department" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>通讯地址：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										&nbsp;<s:property value="peBzzStudent.address"/>
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>工作区域：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
										&nbsp;<s:property value="peBzzStudent.workAddress"/>
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
										<strong>从事业务：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
										&nbsp;<s:property value="peBzzStudent.work" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
										<strong>职务：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
										&nbsp;<s:property value="peBzzStudent.position" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<font color="red" size="3">*</font><strong>手 机：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
										&nbsp;<s:property value="peBzzStudent.mobilePhone" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<font color="red" size="3">*</font><strong>电子邮件：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
										&nbsp;<s:property value="peBzzStudent.email" />
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<font color="red" size="3"></font><strong>密码找回邮箱：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
										&nbsp;<input type="radio" name="choosePassEmail" id="choosePassEmail" value="regEmail" onClick="changePassEmail()" <s:if test="peBzzStudent.passEmail == '' || peBzzStudent.passEmail == null">checked</s:if>/>注册邮箱找回<br/>
										&nbsp;<input type="radio" name="choosePassEmail" id="choosePassEmail" value="passEmail" onClick="changePassEmail()" <s:if test="peBzzStudent.passEmail != '' && peBzzStudent.passEmail != null">checked</s:if>/>新邮箱找回
										<div id="passEmailSpan" style="display:none">&nbsp;<input type="text" id="passEmail" name="passEmail" value="<s:property value="peBzzStudent.passEmail" />"/></div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table border="0" align="center" cellpadding="0" cellspacing="0" height="35" valign="bottom">
								<tr>
									<td>
										<input type="submit" value="修 &nbsp;改" stype="cursor:hand" />
									</td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td>
										<input type="button" value="返 &nbsp;回" 
											stype="cursor:hand" 
												onclick="javascript:window.navigate('/entity/workspaceStudent/studentWorkspace_StudentInfo.action')" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</form>
			<br />
		</div>
	</div>
</body>
</html>
<script language="JavaScript">
		var choosePassEmail = document.getElementsByName("choosePassEmail");
		for(var i=0; i < choosePassEmail.length; i++) {
			if(choosePassEmail[i].checked) {
				if(choosePassEmail[i].value == 'passEmail') {
					var passSpan = document.getElementById("passEmailSpan");
					passSpan.style.display = "block";
				} else {
					var passSpan = document.getElementById("passEmailSpan");
					passSpan.style.display = "none";
				}
			}
		}
</script>
