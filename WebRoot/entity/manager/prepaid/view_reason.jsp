<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>查看退费情况</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="css/shared.css">
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">
		<script language="JavaScript" src="js/shared.js"></script>
		<script language="JavaScript"
			src="/web/bzz_index/password/js/jquery-1.7.1.min.js"></script>
	</head>
	<body leftmargin="0" topmargin="0" class="scllbar">
		<form action="/entity/basic/buyClassHourRecord_applyRefund.action" method="post"
			id="form">
			<input type="hidden" name="id" value="<s:property value="id" />" />
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="28">
						<div class="content_title" id="zlb_title_start">
							退费详细
						</div id="zlb_title_end">
					</td>
				</tr>
				<tr>
					<td valign="top" class="pageBodyBorder" style="background: none;">
						<div class="border" style="padding-left: 30px">

							<table style="text-align: left;" width="96%" border="0"
								cellpadding="0" cellspacing="0">

								<tr class="postFormBox" id="a2">
									<td>
										<span class="name">退费原因：</span>
									</td>
									<td>
										<textarea  disabled="disabled" cols="50" rows="10" ><s:property value="#request.peBzzRefundInfo.reason"/></textarea>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<!-- <tr>
					<td height="28">
						<div class="content_title" id="zlb_title_start">
							拒绝原因：
						</div id="zlb_title_end">
					</td>
				</tr> -->
				<tr>
					<td valign="top" class="pageBodyBorder" style="background: none;">
						<div class="border" style="padding-left: 30px">

							<table style="text-align: left;" width="96%" border="0"
								cellpadding="0" cellspacing="0">

								<tr class="postFormBox" id="a2">
									<td>
										<span class="name">拒绝原因：</span>
									</td>
									<td>
										<textarea  disabled="disabled" cols="50" rows="10" ><s:property value="#request.peBzzRefundInfo.refauseReason"/></textarea>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<table width="98%" height="100%" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td align="center" valign="middle">
									<span class="name" style="width: 46px; height: 15px; padding-top: 3px"><a
										href="#" onclick="javascript:window.history.back()">返回</a> </span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			
			</table>
		</form>
		<input  type="hidden" id="forward" value="<s:property value="forward"/>">
	</body>
	<script type="text/javascript">
		function isSubmit() {
			var forward = $('#forward').val();
			if(forward!=null || forward!='') {
				$('form').attr('action',forward);
			}
			$('#form').submit();
		}
	
	</script>
</html>
