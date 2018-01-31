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
		
		if(document.getElementById("peBzzStudent.mobilePhone").value!=""){
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
		if(document.getElementById("peBzzStudent.department").value=="") {
			alert("工作部门不能为空");
			return false;
		}
		if(document.getElementById("peBzzStudent.phone").value=="") {
			alert("办公电话不能为空");
			return false;
		}
		var year = document.getElementById('year').value;
	
		var month = document.getElementById('month').value;
		var day = document.getElementById('date').value;
		
		document.getElementById('peBzzStudent.birthdayDate').value = year+'-'+month+'-'+day;
		//alert(document.getElementById('peBzzStudent.birthdayDate').value)
	}
</script>
	</head>
	<body>
		<div class="" style="width: 100%; padding: 0; margin: 0;"
			align="center">
			<div class="main_title">
				个人信息修改
			</div>
			<div class="main_txt">
			<form
								action="/entity/workspaceRegStudent/regStudentWorkspace_modifyInfo.action"
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
											<font color="red" size="3">*</font>
											<strong>姓 名：</strong>
										</td>
										<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
											&nbsp;
											<input type="text" name="peBzzStudent.trueName"
												value="<s:property value="peBzzStudent.trueName"/>"
												id="peBzzStudent.trueName" maxlength=20 size="24"/>	
											<!--<s:property value='peBzzStudent.trueName' />-->
										</td>

									</tr>
									<tr>
										<td align="center" bgcolor="#E9F4FF" class="STYLE1">
											<font color="red" size="3">*</font> <strong>性 别：</strong>
										</td>
										<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
											colspan="2">
											&nbsp;
											<s:if test="peBzzStudent.gender == null "></s:if>
											<input type="radio" name="peBzzStudent.gender"  id="gender1" value="男" <s:if test='peBzzStudent.gender =="男"'>checked</s:if>>男 &nbsp;&nbsp;&nbsp;&nbsp;
                                  			<input type="radio" name="peBzzStudent.gender"  id="gender2" value="女" <s:if test='peBzzStudent.gender =="女"'>checked</s:if>>女
										</td>
									</tr>
									<!--  
									<tr>
										<td align="center" bgcolor="#E9F4FF" class="kctx_zi2">
											<DIV align="center" class="STYLE1">
												<font color="red" size="3">*</font> <strong>民 族：</strong>
											</DIV>
										</td>
										<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
											colspan="2">
											&nbsp;
											<s:if test="peBzzStudent.folk == null "></s:if>
											<select name="peBzzStudent.folk" id="peBzzStudent.folk" style="width:154px">
							                <OPTION VALUE="<s:property value="peBzzStudent.folk"/>"><s:property value="peBzzStudent.folk"/></OPTION>
							                <OPTION VALUE="汉族">汉族</OPTION>
							                
							                <OPTION VALUE="蒙古族">蒙古族</OPTION>
							                
							                <OPTION VALUE="回族">回族</OPTION>
							                
							                <OPTION VALUE="藏族">藏族</OPTION>
							                
							                <OPTION VALUE="维吾尔族">维吾尔族</OPTION>
							                
							                <OPTION VALUE="苗族">苗族</OPTION>
							                
							                <OPTION VALUE="彝族">彝族</OPTION>
							                
							                <OPTION VALUE="壮族">壮族</OPTION>
							                
							                <OPTION VALUE="布依族">布依族</OPTION>
							                
							                <OPTION VALUE="朝鲜族">朝鲜族</OPTION>
							                
							                <OPTION VALUE="满族">满族</OPTION>
							                
							                <OPTION VALUE="侗族">侗族</OPTION>
							                
							                <OPTION VALUE="瑶族">瑶族</OPTION>
							                
							                <OPTION VALUE="白族">白族</OPTION>
							                
							                <OPTION VALUE="土家族">土家族</OPTION>
							                
							                <OPTION VALUE="哈尼族">哈尼族</OPTION>
							                
							                <OPTION VALUE="哈萨克族">哈萨克族</OPTION>
							                
							                <OPTION VALUE="傣族">傣族</OPTION>
							                
							                <OPTION VALUE="黎族">黎族</OPTION>
							                
							                <OPTION VALUE="傈僳族">傈僳族</OPTION>
							                
							                <OPTION VALUE="佤族">佤族</OPTION>
							                
							                <OPTION VALUE="畲族">畲族</OPTION>
							                
							                <OPTION VALUE="高山族">高山族</OPTION>
							                
							                <OPTION VALUE="拉祜族">拉祜族</OPTION>
							                
							                <OPTION VALUE="水族">水族</OPTION>
							                
							                <OPTION VALUE="东乡族">东乡族</OPTION>
							                
							                <OPTION VALUE="纳西族">纳西族</OPTION>
							                
							                <OPTION VALUE="景颇族">景颇族</OPTION>
							                
							                <OPTION VALUE="柯尔克孜族">柯尔克孜族</OPTION>
							                
							                <OPTION VALUE="土族">土族</OPTION>
							                
							                <OPTION VALUE="达斡尔族">达斡尔族</OPTION>
							                
							                <OPTION VALUE="仫佬族">仫佬族</OPTION>
							                
							                <OPTION VALUE="羌族">羌族</OPTION>
							                
							                <OPTION VALUE="布朗族">布朗族</OPTION>
							                
							                <OPTION VALUE="撒拉族">撒拉族</OPTION>
							                
							                <OPTION VALUE="毛难族">毛难族</OPTION>
							                
							                <OPTION VALUE="仡佬族">仡佬族</OPTION>
							                
							                <OPTION VALUE="锡伯族">锡伯族</OPTION>
							                
							                <OPTION VALUE="阿昌族">阿昌族</OPTION>
							                
							                <OPTION VALUE="普米族">普米族</OPTION>
							                
							                <OPTION VALUE="塔吉克族">塔吉克族</OPTION>
							                
							                <OPTION VALUE="怒族">怒族</OPTION>
							                
							                <OPTION VALUE="乌孜别克族">乌孜别克族</OPTION>
							                
							                <OPTION VALUE="俄罗斯族">俄罗斯族</OPTION>
							                
							                <OPTION VALUE="鄂温克族">鄂温克族</OPTION>
							                
							                <OPTION VALUE="崩龙族">崩龙族</OPTION>
							                
							                <OPTION VALUE="保安族">保安族</OPTION>
							                
							                <OPTION VALUE="裕固族">裕固族</OPTION>
							                
							                <OPTION VALUE="京族">京族</OPTION>
							                
							                <OPTION VALUE="塔塔尔族">塔塔尔族</OPTION>
							                
							                <OPTION VALUE="独龙族">独龙族</OPTION>
							                
							                <OPTION VALUE="鄂伦春族">鄂伦春族</OPTION>
							                
							                <OPTION VALUE="赫哲族">赫哲族</OPTION>
							                
							                <OPTION VALUE="门巴族">门巴族</OPTION>
							                
							                <OPTION VALUE="珞巴族">珞巴族</OPTION>
							                
							                <OPTION VALUE="基诺族">基诺族</OPTION>
							                
							                <OPTION VALUE="其他">其他</OPTION>
							                
							                <OPTION VALUE="外国血统">外国血统</OPTION>                        
				                        </select>
										</td>
									</tr>
									
									<tr>
										<td align="center" bgcolor="#E9F4FF" class="STYLE1">
											<strong>出生日期：</strong>
										</td>
										<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
											colspan="2">
											&nbsp;
											 <select  size="1" style="width:60px"  id="year" name="year" onfocus="years('year',new Date().getFullYear()),change_date()" onchange="change_date()">
								          <option id="year" value="<s:date name="peBzzStudent.birthdayDate" format="yyyy"/>" ><s:date name="peBzzStudent.birthdayDate" format="yyyy"/></option>
								         </select>&nbsp;年&nbsp; 
								          <select size="1" style="width:50px"  name="month" id="month" onfocus="months(),change_date()" onchange="change_date()">
								          <option id="month" value="<s:date name="peBzzStudent.birthdayDate" format="MM"/>"><s:date name="peBzzStudent.birthdayDate" format="MM"/></option>
								          </select>&nbsp;月&nbsp;
								          <select size="1" style="width:50px"  name="date" id="date">
								          <option id="day" value="<s:date name="peBzzStudent.birthdayDate" format="dd"/>"><s:date name="peBzzStudent.birthdayDate" format="dd"/></option>
								         </select>&nbsp;日<br/>	
								         <input type="hidden" id="peBzzStudent.birthdayDate" name="peBzzStudent.birthdayDate" value=""/>
										<!-- 	<input type="text" name="peBzzStudent.birthdayDate" id="peBzzStudent.birthdayDate"  value="<s:date name="peBzzStudent.birthdayDate" format="yyyy-MM-dd"/>" readonly="readonly" size="24"/>
											&nbsp;<img src="/js/calendar/img.gif" width="20" height="14"
											id="f_trigger_b"
											style="text-align:center; border: 1px solid #551896; cursor: pointer;"
											title="Date selector"
											onmouseover="this.style.background='white';"
											onmouseout="this.style.background=''">  -->
										</td>
									</tr>
									<tr>
										<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
											<font color="red" size="3">*</font>
											<strong>国 籍：</strong>
										</td>
										<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
											&nbsp;
											<select name="peBzzStudent.cardType" id="peBzzStudent.cardType" style="width:155px">
											<option value="中国"
													<s:if test='ppeBzzStudent.cardType=="中国"'>selected</s:if>>
													中国
												</option>
												<option value="中国香港"
													<s:if test='peBzzStudent.cardType=="中国香港"'>selected</s:if>>
													中国香港
												</option>
												<option value="中国澳门"
													<s:if test='peBzzStudent.cardType=="中国澳门"'>selected</s:if>>
													中国澳门
												</option>
												<option value="中国台湾"
													<s:if test='peBzzStudent.cardType=="中国台湾"'>selected</s:if>>
													中国台湾
												</option>
												<option value="美国"
													<s:if test='peBzzStudent.cardType=="美国"'>selected</s:if>>
													美国
												</option>
												<option value="英国"
													<s:if test='peBzzStudent.cardType=="英国"'>selected</s:if>>
													英国
												</option>
												<option value="法国"
													<s:if test='peBzzStudent.cardType=="法国"'>selected</s:if>>
													法国
												</option>
												<option value="其他"
													<s:if test='peBzzStudent.cardType=="其他"'>selected</s:if>>
													其他
												</option>
										</select>
										</td>
									</tr>
									<!--  <tr>
										<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
											<font color="red" size="3">*</font>
											<strong>身份证号：</strong>
										</td>
										<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
											&nbsp;
											<input type="text" name="peBzzStudent.cardNo"
												value="<s:property value="peBzzStudent.cardNo"/>"
												id="peBzzStudent.cardNo" maxlength=20 />
											</font>
										</td>
									</tr> -->
									
									<tr>
										<td align="center" bgcolor="#E9F4FF" class="STYLE1">
											<font color="red" size="3">*</font> <strong>学 历：</strong>
										</td>
										<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2">
											&nbsp;
											<select name="peBzzStudent.education" style="width:155px">
												<option value="初中"
													<s:if test='peBzzStudent.education=="初中"'>selected</s:if>>
													初中
												</option>
												<option value="高中"
													<s:if test='peBzzStudent.education=="高中"'>selected</s:if>>
													高中
												</option>
												<option value="职高"
													<s:if test='peBzzStudent.education=="职高"'>selected</s:if>>
													职高
												</option>
												<option value="中专"
													<s:if test='peBzzStudent.education=="中专"'>selected</s:if>>
													中专
												</option>
												<option value="技校"
													<s:if test='peBzzStudent.education=="技校"'>selected</s:if>>
													技校
												</option>
												<option value="大专"
													<s:if test='peBzzStudent.education=="大专"'>selected</s:if>>
													大专
												</option>
												<option value="本科"
													<s:if test='peBzzStudent.education=="本科"'>selected</s:if>>
													本科
												</option>
												<option value="硕士"
													<s:if test='peBzzStudent.education=="硕士"'>selected</s:if>>
													硕士
												</option>
												<option value="博士"
													<s:if test='peBzzStudent.education=="博士"'>selected</s:if>>
													博士
												</option>
											</select>
										</td>
									</tr>
									
									
									<tr>
										<td align="center" bgcolor="#E9F4FF" class="STYLE1">
											<strong>手 机：</strong>
										</td>
										<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
											colspan="2">
											&nbsp;
											<input type="text" name="peBzzStudent.mobilePhone" size="24" id="peBzzStudent.mobilePhone" value="<s:property value="peBzzStudent.mobilePhone" />"/>
										</td>
									</tr>
									
									<tr>
										<td align="center" bgcolor="#E9F4FF" class="STYLE1">
											<font color="red" size="3">*</font><strong>工作部门：</strong>
										</td>
										<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
											colspan="2">
											&nbsp;
											<input type="text" name="peBzzStudent.department" size="24" id="peBzzStudent.department" value="<s:property value="peBzzStudent.department" />"/>
										</td>
									</tr>
									<!-- <tr>	
									  <td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<font color="red" size="3" ></font> <strong>单位名称：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">&nbsp;
										<input type="text" name="peBzzStudent.pename" id="peBzzStudent.pename" maxlength=30/>
										
									</td>
									</tr>  -->
									<!--<tr>
								 	<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>职 称：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
										colspan="2">
										&nbsp;
										<input type="text" name="peBzzStudent.title" id="peBzzStudent.title" maxlength=30 value="<s:property value="peBzzStudent.title"/>"/>
										
									</td>
									</tr>-->
									<tr>

										<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
										
											<strong>职务：</strong>
										</td>
										<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
											colspan="2">
											&nbsp;
											<input type="text" name="peBzzStudent.position" size="24" id="peBzzStudent.position" value="<s:property value="peBzzStudent.position" />"/>
										</td>
									</tr>
								<!-- 	 <tr>
										<td align="center" bgcolor="#E9F4FF" class="STYLE1">
											&nbsp;工作所在地区：
										</td>
										<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
											colspan="2">
											&nbsp;
											<s:if test="peBzzStudent.peEnterprise.address == null ">不详</s:if>
											<s:property value="peBzzStudent.peEnterprise.address" />
										</td>
									</tr>
									  -->
									<tr>
										<td align="center" bgcolor="#E9F4FF" class="STYLE1">
											 <font color="red" size="3">*</font><strong>电子邮件：</strong>
										</td>
										<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
											colspan="2">
											&nbsp;
											<input type="text" size="24" name="peBzzStudent.email" id="peBzzStudent.email" value="<s:property value="peBzzStudent.email" />"/>
										</td>
									</tr>
								<!-- 	<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>邮编：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
										colspan="2">
										&nbsp;
										<input type="text" name="peBzzStudent.zipcode" id="peBzzStudent.zipcode" maxlength=30 value="<s:property value="peBzzStudent.title"/>"/>
										
									</td>
									</tr> -->
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<font color="red" size="3">*</font><strong>办公电话：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
										colspan="2">
										&nbsp;
										<input type="text" name="peBzzStudent.phone" id="peBzzStudent.phone" maxlength=30 value="<s:property value="peBzzStudent.phone"/>"/>
									</td>
								</tr>
								<tr>
									<td align="center" bgcolor="#E9F4FF" class="STYLE1">
										<strong>通讯地址：</strong>
									</td>
									<td align="left" bgcolor="#FAFAFA" class="kctx_zi2" >
										&nbsp;
										<input type="text" name="peBzzStudent.address" id="peBzzStudent.address" maxlength=60 size="50" value="<s:property value="peBzzStudent.address"/>" />
										
									</td>
								</tr>
								</table>
						</td>
					</tr>
					<tr>
						<td >
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								height="35" valign="bottom">
								<tr>
									<td >
										<input type="submit" value="修 &nbsp;改" stype="cursor:hand" />
									</td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td >
										<input type="button" value="返 &nbsp;回" stype="cursor:hand"
											onclick="javascript:window.navigate('/entity/workspaceRegStudent/regStudentWorkspace_StudentInfo.action')" />
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
		<script type="text/javascript">
		/*    Calendar.setup({
		        inputField     :    "peBzzStudent.birthdayDate",     // id of the input field
		        ifFormat       :    "%Y-%m-%d",       // format of the input field
		        button         :    "f_trigger_b",  // trigger for the calendar (button ID)
		        align          :    "Tl",           // alignment (defaults to "Bl")
		        singleClick    :    true
		    });*/
		</script>
</html>