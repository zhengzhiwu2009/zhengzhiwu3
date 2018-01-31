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
						学习详情
					</td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="2"
				cellspacing="3" class="tdbg2">
				<tr>
					<!--   <td width="13%" nowrap class="tdbg2">系统编号：001</td> -->
					<td width="13%" align="left" nowrap class="tdbg1">
						学员姓名：
						<s:property value="peBzzStudent.trueName" />
					</td>
					<td width="74%" nowrap class="tdbg2">
						证件编号：
						<s:property value="peBzzStudent.cardNo" />
					</td>
				</tr>

			</table>
			<table width="100%" border="0" cellspacing="1" cellpadding="0">
				<tr>
					<td class="tdsectionbar">
						所学课程情况
					</td>
				</tr>
			</table>
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="tdframe">
				<tr align="center" class="tdbg2">
					<td style="display: none;">
						课程id
					</td>
					<td width="10%">
						课程名称
					</td>
					<td width="10%">
						课程编号
					</td>
					<td width="10%">
						是否必修
					</td>
					<td width="10%">
						课程学时
					</td>
					<td width="10%">
						支付时间
					</td>
					<td width="10%">
						首次学习时间
					</td>
					<td width="10%">
						学习完成时间
					</td>
					<td width="10%">
						学习期限
					</td>
					<td width="10%">
						课程状态
					</td>
					<td width="10%">
						学习结果
					</td>
				</tr>
				<s:iterator value="firstList" id="first">
					<tr align="center" class="tdbg0">
						<td style="display: none;">
							<s:property value="#first[0]" />
						</td>
						<td>
							<s:property value="#first[1]" />
						</td>
						<td>
							<s:property value="#first[2]" />
						</td>
						<td>
							<s:property value="#first[3]" />
						</td>
						<td>
							<s:property value="#first[4]" />
						</td>
						<td>
							<s:property value="#first[5]" />
						</td>
						<td>
							<s:property value="#first[6]" />
						</td>
						<td>
							<s:property value="#first[7]" />
						</td>
						<td>
							<s:property value="#first[8]" />
						</td>
						<td>
							<s:if test="isCompleted(#first[9])">完成</s:if>
							<s:else>未完成</s:else>
						</td>
						<td>
							<s:if test="#first[10]>=80">通过</s:if>
							<s:else>未通过</s:else>
						</td>
					</tr>
				</s:iterator>
			</table>
			<table width="100%" border="0" cellspacing="1" cellpadding="0">
				<tr>
					<td class="tdsectionbar">
						课程讲解
					</td>
				</tr>
			</table>
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="tdframe">
				<tr align="center" class="tdbg2">
					<!-- 	<td width="10%">
						要求学习时间
					</td> -->
					<td width="10%">
						累计学习时间
					</td>
					<td width="10%">
						累计学习次数
					</td>
					<td width="10%">
						上一次学习时间
					</td>
				</tr>
				<s:iterator value="secondList" id="second">
					<tr align="center" class="tdbg0">

						<td>
							<s:property value="#second[0]" />
						</td>
						<td>
							<s:property value="#second[1]" />
						</td>
						<td>
							<s:property value="#second[2]" />
						</td>
					</tr>
				</s:iterator>
			</table>
			<table width="100%" border="0" cellspacing="1" cellpadding="0">
				<tr>
					<td class="tdsectionbar">
						课程测验
					</td>
				</tr>
			</table>
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="tdframe">
				<tr align="center" class="tdbg2">
					<td width="10%">
						限定测验次数
					</td>
					<td width="10%">
						测验通过成绩
					</td>
					<td width="10%">
						上次测验时间
					</td>

				</tr>
				<s:iterator value="lastList" id="last">
					<tr align="center" class="tdbg0">
						<td>
							<s:property value="#last[0]" />
						</td>
						<td>
							<s:property value="#last[1]" />
						</td>
						<td>
							<s:property value="#last[2]" />
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
