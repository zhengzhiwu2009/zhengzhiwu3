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
		<link rel="stylesheet" type="text/css"
			href="/entity/teacher/css/shared.css">
			-->
			<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css"
			href="./js/extjs/resources/css/ext-all.css">
		<script language="JavaScript" src="js/shared.js"></script>
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<style type="text/css">
		.tdbg0		{color:#444444}
		</style>
	</head>

	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<form name="form1" method="post" action="">
			<table width="100%" border="0" cellspacing="0" cellpadding="4"
				align="center">
				<tr>
					<td class="tdsectionbar">
						<div class="content_title">课程信息</div>
					</td>
				</tr>
			</table>
			<div class="cntent_k">
			<table width="100%" border="0" align="center" cellpadding="2"
				cellspacing="3" class="tdbg2">
				<tr>
					<td width="15%" nowrap class="tdbg2">
						<b>学员姓名：</b> <s:property value="peBzzStudent.trueName"/>
					</td>
					<td width="85%" align="left" nowrap class="tdbg2">
						<b>证件编号：</b><s:property value="peBzzStudent.cardNo"/>
					</td>
				</tr>

			</table>
			<table width="100%" border="0" cellspacing="1" cellpadding="0">
				<tr>
					<td class="tdsectionbar">
						<b>学员选课信息</b>
					</td>
				</tr>
			</table>
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="content_title">
				<tr align="center" class="tdbg2" height=25>
					<td style="display: none;" >
						课程Id
					</td>
					<td width="10%">
						课程编号
					</td>
					<td width="10%">
						课程名称
					</td>
					<td width="10%">
						课程学时
					</td>
					<td width="5%">
						是否必修
					</td>
					<td width="10%">
						支付时间
					</td>
					<td width="10%">
						开始学习时间
					</td>
					<td width="10%">
						是否完成
					</td>
					<td width="10%">
						考试结果
					</td>
					<td width="10%">
						课程状态
					</td>
					<td width="5%">
						操作
					</td>
				</tr>
				<s:iterator value="list" id="obj" status="item">
					<tr align="center" class="tdbg0" height=25>

						<td style="display: none;">
							<s:property value="#obj[0]" />
						</td>
						<td>
							<s:property value="#obj[1]" />
						</td>
						<td>
							<s:property value="#obj[2]" />
						</td>
						<td>
							<s:property value="#obj[3]" />
						</td>
						<td>
							<s:property value="#obj[4]" />
						</td>
						<td>
							<s:property value="#obj[5]" />
						</td>
						<td>
							<s:property value="#obj[6]" />
						</td>

						<td>
							<s:property value="#obj[7]"/>
						</td>
						<td>
							<s:if test="#obj[8]>=80">通过</s:if><s:else>未通过</s:else>
						</td>
						<td>
							<s:property value="#obj[9]" />
						</td>
						<td>
							<a href="/entity/teaching/studentCourse_studyDetail.action?courseId=<s:property value="#obj[0]" />&id=<s:property value="peBzzStudent.id"/>">查看</a>
						</td>
					</tr>
				</s:iterator>

			</table>
		</form>
		<table width="98%" border="0" cellspacing="3" cellpadding="0"
			align="center">
			<tr class="tdbg2">
				<td align="center">
					<td align="center"><a  onclick="window.history.go(-1)" title="返回" class="buttonn"><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'"><span class="text">返回</span></span></a></td>
				</td>
			</tr>
		</table>
		</div>
	</body>
</html>
