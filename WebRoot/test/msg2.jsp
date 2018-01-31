<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	response.setHeader("expires", "0");
	String msg = request.getParameter("msg");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><s:text name="支付结果" /></title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			.content_title{
				background-image:url(/entity/manager/pub/images/content_title_bg_55.jpg);
				height:27px;
				background-repeat:repeat-x;
				border:solid #8bbedc 1px;
				margin:10px 5px 0px 5px!important;
				margin:10px 5px 0px 5px;
				color:#034983;
				line-height:27px;
				padding-left:10px;
				}
			.cntent_k{
				background-color:#e3f1f6;
				padding:10px;
				border-left:solid #8bbedc 1px;
				border-bottom:solid #8bbedc 1px;
				border-right:solid #8bbedc 1px;
				margin:0px 5px 0px 5px!important;
				margin:0px 5px 0px 5px;
				height:100%;
				}
		</style>
	</head>
	<body>
		<form name="print">
			<div id="main_content">
				<div class="content_title">
					<s:text name="支付结果" />
				</div>
				<div class="cntent_k" align="center">
					<div class="k_cc">
						<table width="80%">
							<tr>
								<div class="" align="center">
									<%
										if ("success".equals(msg)) {
									%>
											支付成功
											<%
										} else if ("false".equals(msg)) {
									%>
											支付失败，请联系管理员
											<%
										} else {
									%>
											支付异常，请联系管理员
											<%
										}
									%>
								</div>
							</tr>
							<tr>
								<td colspan="2">
									<div align="center" class="postFormBox">
										<input type="button" value="确定" onclick="window.close()" />
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>
