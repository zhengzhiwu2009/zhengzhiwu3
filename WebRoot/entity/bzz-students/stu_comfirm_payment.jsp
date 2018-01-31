\<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<link href="/entity/bzz-students/css.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<script language="JavaScript"
			src="/web/bzz_index/password/js/jquery-1.7.1.min.js"></script>
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.STYLE1 {
	color: #FF0000;
	font-weight: bold;
}

.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}
-->
</style>
	</head>
	<body>
		<table width="1002" border="0" align="center" cellpadding="0"
			cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td>
					<table width="1002" border="0" align="center" cellpadding="0"
						cellspacing="0">
					</table>
					<table width="1002" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<form
							action="/entity/workspaceStudent/bzzstudent_confirmPayment.action"
							name="form" id="form" method="post">
						<tr>
							<td width="752" valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr align="center" valign="top">
										<td height="17" align="left" class="twocentop">
											<img src="/entity/bzz-students/images/two/1.jpg" width="11"
												height="12" />
											当前位置：专项培训支付
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<table width="100%" border="0" cellpadding="0" cellspacing="0">

											<tr>
												<td>
													<div class="content_title" id="zlb_title_start">
														选择支付方式
													</div id="zlb_title_end">
												</td>
											</tr>
											<tr>
												<td style="padding-left: 20;"></td>
											</tr>
											<tr>
												<td valign="top" class="pageBodyBorder"
													style="padding-left: 5px; text-align: center; background: none;">
													<div class="border" style="padding-left: 30px;">
														<p>
															&nbsp;
														</p>
														<table style="text-align: left">
															<tr>
																<td height="28" class="" colspan="2">
																	<input type="radio" name="radio_payment" value="0" />
																	网上支付
																</td>
															</tr>
															<tr>
																<td height="26" class="" colspan="2">
																	<input type="radio" name="radio_payment" value="2" />
																	电汇支付
																</td>

															</tr>
															<tr>
																<td height="27" class="" colspan="2">
																	<input type="radio" name="radio_payment" value="3" />
																	支票付款
																</td>
															</tr>
															<tr>
																<td width="81%" height="29" colspan="2">
																	&nbsp;
																</td>
															</tr>
															<tr>
																<td height="29" colspan="2" style="color: red;">
																	汇款支付说明
																</td>
															</tr>

															<tr>
																<td colspan="2" height="29" style="color: red;">
																	请会员单位汇款至以下账户：
																</td>
															</tr>
															<tr>
																<td colspan="2" height="29" style="color: red;">
																	户名：中国证券业协会培训中心
																</td>
															</tr>
															<tr>
																<td colspan="2" height="29" style="color: red;">
																	账号：1100 1020 5000 5908 0060
																</td>
															</tr>
															<tr>
																<td colspan="2" height="29" style="color: red;">
																	开户行：建行北京月坛支行
																</td>
															</tr>
														</table>
														<br />
														<table width="98%" height="100%" border="0"
															cellpadding="0" cellspacing="0">
															<tr>
																<td align="center" valign="middle">
																	<span
																		style="width: 46px; height: 15px; padding-top: 3px"><a
																		onclick="check();" href="#">下一步</a> </span>
																	&nbsp;&nbsp;&nbsp;
																	<span
																		style="width: 46px; height: 15px; padding-top: 3px"><a
																		href="#" onclick="javascript:window.history.back()">上一步</a>
																	</span>
																</td>
															</tr>
														</table>
													</div>
												</td>
											</tr>



										</table>
									</tr>



									<tr>
										<td width="13">
											&nbsp;
										</td>
									</tr>
								</table>
							</td>
							<td width="13">
								&nbsp;
							</td>
						</tr>
						<input type="hidden" id="paymentMethod" name="paymentMethod"/>
						</form>
					</table>
					<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0
						marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp"
						scrolling=no allowTransparency="true" align=center></IFRAME>
				</td>
			</tr>
		</table>
	</body>
	<script type="text/javascript">
		function check() {
			var val = $("input[name='radio_payment'][checked]").val();
			if(val == null){
				alert("请选择支付方式");
				return;
			}
			if(val == '0') {
				alert("暂时不能进行网上支付");
				return;
			}
			$('#paymentMethod').val(val);
			$("#form").submit();		
		}
	</script>
</html>