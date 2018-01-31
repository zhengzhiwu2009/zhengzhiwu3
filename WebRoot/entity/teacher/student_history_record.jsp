<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<%@page import="com.whaty.platform.standard.scorm.operation.*"%>
<%@page import="com.whaty.platform.database.oracle.standard.standard.scorm.operation.*"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="./js/extjs/resources/css/ext-all.css"/>
		<script language="JavaScript" src="js/shared.js"></script>
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<style type="text/css">
			.tdbg0		{color:#444444}
		</style>
	</head>

	<body>
		<form name="form1" method="post" action="">
			<table width="100%" border="0" cellspacing="0" cellpadding="4"
				align="center">
				<tr>
					<td >
						<div class="content_title">查看学习详情</div>
					</td>
				</tr>
			</table>
			<div class="cntent_k">
			<table width="100%" border="0" align="center" cellpadding="2"
				cellspacing="3" class="tdbg2">
				<tr>
					<td width="100%" align="center" nowrap class="tdbg1">
						<b>学员姓名：</b>
						<s:property value="peBzzStudent.trueName" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<b>证件号码：</b>
						<s:property value="peBzzStudent.cardNo" />
					</td>
					 
				</tr>

			</table>
			<table width="100%" border="0" cellspacing="1" cellpadding="0">
				<tr>
					<td class="tdsectionbar">
						<b>所学课程情况</b>
					</td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="content_title">
				<tr align="center" class="tdbg2">
					<td width="2%">序号</td>
					<s:if test='peBzzStudent.peEnterprise.enumConstByFlagEnterpriseType.code != "V" || peBzzStudent.peEnterprise.peEnterprise.enumConstByFlagEnterpriseType.code != "V"'>
						<td width="5%">订单号</td>
					</s:if>
					<td width="5%">课程编号</td>
					<td width="7%">课程名称</td>
					<td width="5%">课程性质</td>
					<td width="5%">课程学时</td>
					<td width="5%">支付时间</td>
					<td width="5%">首次学习时间</td>
					<td width="5%">末次学习时间</td>
					<td width="5%">学习完成时间</td>
					<td width="5%">学习期限(天)</td>
					<td width="5%">课程状态</td>
					<td width="5%">考试结果</td>
					<td width="5%">获得学时时间</td>
				</tr>
				<!-- 所学课程情况 -->
				<s:iterator value="coursesSituation" id="first" status="item">
					<tr align="center" class="tdbg0">
						<td >
					    	<s:property value="#item.index+1" />
						</td>
						<s:if test='peBzzStudent.peEnterprise.enumConstByFlagEnterpriseType.code != "V" || peBzzStudent.peEnterprise.peEnterprise.enumConstByFlagEnterpriseType.code != "V"'>
						<td>
						   <s:property value="#first[0]"  default="--"/>
						</td>
						</s:if>
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
							<s:property value="#first[4]" default="--"/>
						</td>
						<td>
							<s:property value="#first[5]" />
						</td>
						<td>
							<s:property value="#first[6]" default="--"/>
						</td>
						<td>
							<s:property value="#first[7]" />
						</td>
						<td>
							<s:property value="#first[8]" default="--" />
						</td>
						<td>
								<s:if test="#first[13]=='40288a7b39968644013996bf01e7004b'">--</s:if><!-- 40288a7b39968644013996bf01e7004b  表示免费公共课程的ID -->
								<s:else><s:property value="#first[9]" /></s:else>
						</td>
						<td>
							<s:if test="#first[10]=='COMPLETED'">完成</s:if>
							<s:elseif test="#first[10]=='INCOMPLETE'">学习中</s:elseif>
							<s:elseif test="#first[10]=='GUOQI'">已过期</s:elseif>
							<s:else>未学习</s:else>
						</td>
						<td>
							<s:if test="#first[11]=='PASSED'">通过</s:if>
							<s:elseif test="#first[11]=='NOEXAMLEE'">不考试</s:elseif>
							<s:else>未通过</s:else>
						</td>
						<td width="5%"><s:property value="#first[13]" default="--" /></td>
					</tr>
				</s:iterator>
			</table>
			
			
			<table width="100%" border="0" cellspacing="1" cellpadding="0">
				<tr>
					<td class="tdsectionbar">
						<b>满意度调查</b>
					</td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="content_title">
				<tr align="center" class="tdbg2">
					 <td width="10%">序号</td> 
					 <td width="10%">最后提交时间</td> 
				</tr>
				<!-- 满意度调查 -->
				<s:iterator value="satisfactionSurvey" id="ss" status="item">
					<tr align="center" class="tdbg0">
						<td><s:property value="#item.index+1"  /></td>
						<td><s:property value="#ss" default="--"/></td>
					</tr>
				</s:iterator>
			</table>
			<table width="100%" border="0" cellspacing="1" cellpadding="0">
				<tr>
					<td class="tdsectionbar">
						<b>课后测验</b>
					</td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="content_title">
				<tr align="center" class="tdbg2">
					 <td width="10%">序号</td> 
					 <td width="10%">打开试卷时间</td> 
					 <td width="10%">提交试卷时间</td> 
					 <td width="10%">限定测验次数</td> 
					 <td width="10%">测验成绩</td> 
					 <td width="10%">测验通过成绩</td> 
					 <td width="10%">考试结果</td> 
				</tr>
				<!-- 课后测验 -->
				<s:iterator value="classTest" id="last" status="item">
					<tr align="center" class="tdbg0">
						<td><s:property value="#item.index+1"  default="--"/></td>
						<td><s:property value="#last[0]" default="--"/></td>
						<td>
							<s:property value="#last[1]" default="--"/>
						</td> 
						<td>
						<s:if test="#last[7]=='40288a7b39968644013996bf01e7004b'">--</s:if><!-- 40288a7b39968644013996bf01e7004b  表示免费公共课程的ID -->
								<s:else><s:property value="#last[2]"  default="--"/></s:else>
						</td> 
						<td><s:property value="#last[3]"  default="--"/></td> 
						<td><s:property value="#last[4]"  default="--"/></td>  
						<td>
							<s:if test="#last[5]==1||#last[5]=='1'">
								<s:if test="#last[6]>=0">
									通过
								</s:if>
								<s:else>未通过</s:else>
							</s:if>
							<s:else>未通过</s:else>
						</td>
					</tr>
				</s:iterator>
			</table>
		</form>
		<table width="98%" border="0" cellspacing="3" cellpadding="0"
			align="center">
			<tr class="tdbg2">
				<td align="center">
				<input   type="button" value="关闭" onclick="window.close()"/>
				</td>
			</tr>
		</table>
	</body>
</html>
