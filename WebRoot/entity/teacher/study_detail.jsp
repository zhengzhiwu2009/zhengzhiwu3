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
			<link href="./css/css.css" rel="stylesheet" type="text/css">
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
		 
			
		<link rel="stylesheet" type="text/css"
			href="./js/extjs/resources/css/ext-all.css">
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
					<td>
					<div class="content_title">
						学习详情
						</div>
					</td>
				</tr>
			</table>
			<div class="cntent_k">
			<table width="100%" border="0" align="center" cellpadding="2"
				cellspacing="3"  >
				<tr >
					<!--   <td width="13%" nowrap  >系统编号：001</td> -->
					<td width="13%" align="left" nowrap >
						<b>学员姓名：</b>
						<s:property value="peBzzStudent.trueName" />
					</td>
					<td width="74%" nowrap  >
						<b>身份证号：</b>
						<s:property value="peBzzStudent.cardNo" />
					</td>
				</tr>

			</table>
			<table width="100%" border="0" cellspacing="1" cellpadding="0">
				<tr>
					<td >
						<b>所学课程情况</b>
					</td>
				</tr>
			</table>
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="content_title">
				<tr align="center"  >
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
					<tr align="center" class="tdbg0"  >
						<td style="display: none;" bgcolor="rgb(227, 241, 246)">
							<s:property value="#first[0]" />
						</td>
						<td bgcolor="rgb(227, 241, 246)">
							<s:property value="#first[1]" />
						</td>
						<td bgcolor="rgb(227, 241, 246)">
							<s:property value="#first[2]" />
						</td>
						<td bgcolor="rgb(227, 241, 246)">
							<s:property value="#first[3]" />
						</td>
						<td bgcolor="rgb(227, 241, 246)">
							<s:property value="#first[4]" />
						</td>
						<td bgcolor="rgb(227, 241, 246)">
							<s:property value="#first[5]" />
						</td>
						<td bgcolor="rgb(227, 241, 246)">
							<s:property value="#first[6]" />
						</td>
						<td bgcolor="rgb(227, 241, 246)">
							<s:property value="#first[7]" />
						</td>
						<td bgcolor="rgb(227, 241, 246)">
							<s:property value="#first[8]" />
						</td>
						<td bgcolor="rgb(227, 241, 246)">
							<s:if test="#first[9]=='COMPLETED'">完成</s:if>
							<s:elseif test="#first[9]=='UNCOMPLETE'">未开始</s:elseif>
							<s:else>未完成</s:else>
						</td>
						<td bgcolor="rgb(227, 241, 246)">
							<s:if test="#first[10]=='PASSED'">通过</s:if>
							<s:else>未通过</s:else>
						</td>
					</tr>
				</s:iterator>
			</table>
			<table width="100%" border="0" cellspacing="1" cellpadding="0">
				<tr>
					<td >
						<b>课程讲解</b>
					</td>
				</tr>
			</table>
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="content_title">
				<tr align="center"  >
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

						<td bgcolor="rgb(227, 241, 246)">
							<s:property value="#second[0]" />
						</td>
						<td bgcolor="rgb(227, 241, 246)">
							<s:property value="#second[1]" />
						</td >
						<td bgcolor="rgb(227, 241, 246)">
							<s:property value="#second[2]" />
						</td>
					</tr>
				</s:iterator>
			</table>
			<table width="100%" border="0" cellspacing="1" cellpadding="0">
				<tr>
					<td >
						<b>课程测验</b>
					</td>
				</tr>
			</table>
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="content_title">
				<tr align="center" >
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
						<td bgcolor="rgb(227, 241, 246)">
							<s:property value="#last[0]" />
						</td>
						<td bgcolor="rgb(227, 241, 246)">
							<s:property value="#last[1]" />
						</td>
						<td bgcolor="rgb(227, 241, 246)">
							<s:property value="#last[2]" />
						</td>

					</tr>
				</s:iterator>
			</table>
		</form>
		<table width="98%" border="0" cellspacing="3" cellpadding="0"
			align="center">
			<tr>
				<td align="center"><a href="###" onclick="window.history.go(-1)" title="返回" class="buttonn"><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'"><span class="text">返回</span></span></a></td>
			</tr>
		</table>
		</div>
	</body>
</html>
