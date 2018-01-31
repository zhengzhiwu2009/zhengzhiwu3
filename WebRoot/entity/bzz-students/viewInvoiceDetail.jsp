<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.whaty.platform.entity.bean.FrequentlyAskedQuestions;"%>
<%
	String d = String.valueOf(Math.random());
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<form action="" name="form" method="post">
			<div class="" style="width: 100%; padding: 0; margin: 0;" align="center">
				<div class="main_title">
					发票详情
				</div>
				<div class="">
					<span color ="red" >
					已开发票。如需修改或重开发票，请致电远程培训咨询服务电话010-62415115。
					</span>
				</div>
				<div class="main_txt">
					<table class="datalist" width="100%">
						<tr>
							<th width="20%">
								发票类型：
							</th>
							<td align="left" colspan="3">
								<s:property value="peBzzInvoiceInfo.enumConstByInvoiceType.name" />
							</td>
						</tr>
						<tr>
							<th width="20%">
								付款单位：
							</th>
							<td align="left" colspan="3">
								<s:property value="peBzzInvoiceInfo.title" />
							</td>
						</tr>
						<s:if test='peBzzInvoiceInfo.enumConstByInvoiceType.code == "4"'>
						<tr>
							<th width="20%">
								邮寄地址：
							</th>
							<td align="left" colspan="3">
								<s:property value="peBzzInvoiceInfo.address" />
							</td>
						</tr>
						<tr>
							<th width="20%">
								邮政编码：
							</th>
							<td align="left" colspan="3">
								<s:property value="peBzzInvoiceInfo.zipCode" />
							</td>
						</tr>
						<tr>
							<th>
								收件人：
							</th>
							<td colspan="3" align="left">
								<s:property value="peBzzInvoiceInfo.addressee" />
							</td>
						</tr>
						<tr>
							<th>
								收件人电话：
							</th>
							<td colspan="3" align="left">
								<s:property value="peBzzInvoiceInfo.phone" />
							</td>
						</tr>
						</s:if>
						<s:if test='peBzzInvoiceInfo.enumConstByInvoiceType.code == "3"'>
						<tr>
							<th>
								购买方手机号码：
							</th>
							<td colspan="3" align="left">
								<s:property value="peBzzInvoiceInfo.gmfsjhm" />
							</td>
						</tr>
						</s:if>
						<s:if test='peBzzInvoiceInfo.enumConstByInvoiceType.code == "4"'>
						<tr>
							<th>
								购买方纳税人识别号：
							</th>
							<td colspan="3" align="left">
								<s:property value="peBzzInvoiceInfo.gmfnsrsbh" />
							</td>
						</tr>
						<tr>
							<th>
								购买方地址：
							</th>
							<td colspan="3" align="left">
								<s:property value="peBzzInvoiceInfo.gmfdz" />
							</td>
						</tr>
						<tr>
							<th>
								购买方电话：
							</th>
							<td colspan="3" align="left">
								<s:property value="peBzzInvoiceInfo.gmfdh" />
							</td>
						</tr>
						<tr>
							<th>
								购买方邮箱：
							</th>
							<td colspan="3" align="left">
								<s:property value="peBzzInvoiceInfo.email" />
							</td>
						</tr>
						<tr>
							<th>
								购买方开户行：
							</th>
							<td colspan="3" align="left">
								<s:property value="peBzzInvoiceInfo.gmfkhyh" />
							</td>
						</tr>
						<tr>
							<th>
								购买方银行账号：
							</th>
							<td colspan="3" align="left">
								<s:property value="peBzzInvoiceInfo.gmfyhzh" />
							</td>
						</tr>
						</s:if>
						<tr>
							<th>
								合并订单号：
							</th>
							<td colspan="3" align="left">
								<s:property value="peBzzInvoiceInfo.peBzzOrderInfo.mergeSeq" />
							</td>
						</tr>
						<tr>
							<th>
								发票状态：
							</th>
							<td colspan="3" align="left">
								<s:property value="peBzzInvoiceInfo.enumConstByFlagFpOpenState.name" />
							</td>
						</tr>
						<tr>
							<th>
								发票号码：
							</th>
							<td colspan="3" align="left">
								<s:property value="peBzzInvoiceInfo.num" />
							</td>
						</tr>
						<tr>
							<th>
								发票代码：
							</th>
							<td colspan="3" align="left">
								<s:property value="peBzzInvoiceInfo.fpdmNum" />
							</td>
						</tr>
						<tr>
							<th>
								发票校验码：
							</th>
							<td colspan="3" align="left">
								<s:property value="peBzzInvoiceInfo.fpjymNum" />
							</td>
						</tr>
						
						<tr>
							<td colspan="6" align="center">
								<input type="button" value="返回" onclick="history.go(-1)" />
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>
	</body>
</html>