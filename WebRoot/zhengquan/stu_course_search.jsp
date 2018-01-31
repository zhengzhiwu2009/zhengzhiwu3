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
		<link rel="stylesheet" type="text/css"
			href="/zhengquan/css/shared.css">
		<link rel="stylesheet" type="text/css"
			href="./js/extjs/resources/css/ext-all.css">
		<script language="JavaScript" src="js/shared.js"></script>
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
	</head>

	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<form name="form1" method="post" action="">
			<table width="100%" border="0" cellspacing="0" cellpadding="4"
				align="center">
				<tr>
					<td class="tdsectionbar">
						课程信息
					</td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="2"
				cellspacing="3" class="tdbg2">
				<tr>
					<td width="15%" nowrap class="tdbg2">
						学员姓名： <s:property value="peBzzStudent.trueName"/>
					</td>
					<td width="85%" align="left" nowrap class="tdbg2">
						证件编号：<s:property value="peBzzStudent.cardNo"/>
					</td>
				</tr>

			</table>
			<table width="100%" border="0" cellspacing="1" cellpadding="0">
				<tr>
					<td class="tdsectionbar">
						学员选课信息
					</td>
				</tr>
			</table>
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="tdframe">
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
						学习状态
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
							<script language="javascript">
               						var selectC<s:property value="#item.index"/> =new CreatPro("selectC<s:property value="#item.index"/>",<s:property value="#obj[7]"/>,"",5000,1,10,"/entity/bzz-students/");selectC<s:property value="#item.index"/>.stepPro()
         					</script>
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
					<input type="button" class="button" value="返回"
						onclick="window.history.go(-1);" />
				</td>
			</tr>
		</table>
	</body>
</html>
