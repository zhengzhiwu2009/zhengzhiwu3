<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>查看退费情况</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" /> 
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" /> 
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
		<script language="JavaScript" src="js/shared.js"></script>
		<script language="JavaScript" src="/web/bzz_index/password/js/jquery-1.7.1.min.js"></script>
		<style type="text/css">
		.content table{ margin-bottom:5px; width:100%;   background:#FFF}
		.content td,.content th{font-weight:lighter; padding:5px; border:#99bbe8 1px solid}
		.content th  { background:#f1f7fb; font-weight:bold; width:120px}
		</style>
	</head>
	<body leftmargin="0" topmargin="0" class="scllbar">
		<form action="/entity/basic/buyClassHourRecord_applyRefund.action" method="post"
			id="form">
			<input type="hidden" name="id" value="<s:property value="id" />" />
			<div style="width: 740px; padding: 0; margin: 0;" align="center">
				<div class="main_title" style="width:100%">退费详细</div>
				<div class="content">
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<th valign="top">
								<span class="name">退费原因：</span>
							</th>
							<td style="word-break: normal;">
								<span><s:property value="#request.peBzzRefundInfo.reason"/></span>
							</td>
						</tr>
						<tr>
							<th valign="top">
								<span class="name">拒绝原因：</span>
							</th>
							<td style="word-break: normal;">
								<span>&nbsp;<s:property value="#request.peBzzRefundInfo.refauseReason"/></span>
							</td>
						</tr>
						<tr>
							<td align="center" valign="middle" colspan="2">
								<span style="width: 46px; height: 15px; padding-top: 3px"><a
									href="#" onclick="javascript:window.history.back()">返回</a> </span>
							</td>
						</tr>
					</table>
				</div>
			</div>
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
