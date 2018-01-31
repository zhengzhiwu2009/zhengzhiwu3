<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<!--  
		<link rel="stylesheet" type="text/css" href="/zhengquan/css/shared.css">
		-->
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="./js/extjs/resources/css/ext-all.css">
		<script language="JavaScript" src="js/shared.js"></script>
		
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<style type="text/css">
		.tdbg0		{background-color: #ffffff; color:#444444}
		</style>
	</head>

	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<form name="form1" method="post" action="">
			<table width="100%" border="0" cellspacing="0" cellpadding="4"
				align="center">
				<tr>
					<td class="content_title">
						学员信息
					</td>
				</tr>
			</table>
			<div class="cntent_k">
			<table width="100%" border="0" align="center" cellpadding="2"
				cellspacing="3" >
				<tr>
					<td width="15%" nowrap >
						<b>课程代码：</b> <s:property value="peBzzTchCourse.code"/>
					</td>
					<td width="85%" align="left" nowrap >
						<b>课程名称：</b> <s:property value="peBzzTchCourse.name"/>
					</td>
				</tr>

			</table>

			<table width="98%" border="0" cellspacing="1" cellpadding="0"
				align=center>
				<tr>
					<td >
						<b>学员选课信息</b>
					</td>
				</tr>
			</table>
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="content_title" >
				<tr align="center" >
					<td style="display:none;">ID</td>
					<td width="10%">
						学员姓名
					</td>
					<td width="15%">
						身份证号
					</td>
					<td width="10%">
						单位名称
					</td>
					<td width="10%">
						从事业务
					</td>
					<td width="10%">
						支付时间
					</td>
					<td width="10%">
						开始学习时间
					</td>
					<td width="10%">
						结束学习时间
					</td>
					<td width="8%">
						学习状态
					</td>
					<td width="8%">
						学习结果
					</td>
					<td width="8%">
						考试结果
					</td>
				</tr>
				<s:iterator value="list" id="stu" status="item">
				<tr align="center" class="tdbg0">
					<td bgcolor="rgb(227, 241, 246)" >
						<s:property value="#stu[0]"/>
					</td>
					<td bgcolor="rgb(227, 241, 246)">
						<s:property value="#stu[1]"/>
					</td>
					<td bgcolor="rgb(227, 241, 246)">
						<s:property value="#stu[2]"/>
					</td>
					<td bgcolor="rgb(227, 241, 246)">
						<s:property value="#stu[3]"/>
					</td>
					<td bgcolor="rgb(227, 241, 246)">
						<s:property value="#stu[4]"/>
					</td>
					<td bgcolor="rgb(227, 241, 246)">
						<s:property value="#stu[5]"/>
					</td>
					<td bgcolor="rgb(227, 241, 246)">
						<s:property value="#stu[6]"/>
					</td>
					<td bgcolor="rgb(227, 241, 246)">
						<script language="javascript">
               					var selectC<s:property value="#item.index"/> =new CreatPro("selectC<s:property value="#item.index"/>",<s:property value="#stu[7]"/>,"",5000,1,10,"/entity/bzz-students/");selectC<s:property value="#item.index"/>.stepPro()
         				</script>
					</td>
					<td class="enabled" bgcolor="rgb(227, 241, 246)">
						<s:if test="isCompleted(#stu[8])">完成</s:if><s:else>未完成</s:else>
					</td>
					<td class="enabled" bgcolor="rgb(227, 241, 246)">
						<s:if test="isPassed(#stu[10], #stu[9])">通过</s:if><s:else>未通过</s:else>
					</td>
				</tr>
				</s:iterator>
			</table>

		</form>
		<table width="98%" border="0" cellspacing="3" cellpadding="0"
			align="center">
			<tr >
				<td align="center">
					<td align="center"><a href="###" onclick="window.history.go(-1)" title="返回" class="buttonn"><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'"><span class="text">返回</span></span></a></td>
				</td>
			</tr>
		</table>
		</div>
	</body>
</html>
