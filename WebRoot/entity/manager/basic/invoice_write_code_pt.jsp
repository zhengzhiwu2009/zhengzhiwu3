<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>批量调入学员</title>
		<link href="../css/css.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
	function FileTypeCheck() {
		if(document.batch._upload.value == "") {
			alert("文件不能为空，请选择文件");
			document.loreForm._upload.focus();
			return false;
		}
		return true;
	}
function submits(){	
	var code =document.getElementById('code');
	var fpdm =document.getElementById('fpdm');
	var fpjym = document.getElementById('fpjym');
	if(code.value==null || code.value ==''){
		alert('输入发票号码不能为空');
		return false;
	}
	if(fpdm.value==null || fpdm.value ==''){
		alert('输入发票代码不能为空');
		return false;
	}
	if(fpjym.value==null || fpjym.value ==''){
		alert('输入发票校验码不能为空');
		return false;
	}
	document.batch.action ="/entity/basic/ptInvoiceManage_writeCode.action";
	document.batch.submit();
}
</script>
	</head>
	<body leftmargin="0" topmargin="0"
		background="/entity/manager/teaching/satisfaction/images/bg2.gif">
		<form name="batch" id='batch'
			action=""
			method="POST" enctype="multipart/form-data"
			onsubmit="return FileTypeCheck();">
			<input type="hidden" id="id" name="id"
				value="<s:property value="id"/>" />
			<input type="hidden"
				value="<s:property value='servletPath'/>_impStudents.action" />
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td valign="top" class="bg3">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="86" valign="top"
									background="/entity/manager/teaching/satisfaction/images/top_01.gif"></td>
							</tr>
							<tr>
								<td align="center" valign="top">
									<table width="608" border="1" cellspacing="0" cellpadding="0"
										bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
										<tr>
											<td bgcolor="#FFFFFF">
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td>
															<table width="100%" border="0" cellspacing="0"
																cellpadding="0">
																<tr>
																	<td width="250" class="text3"
																		style="padding-top: 25px; font-size: 12px;">
																		&nbsp;&nbsp;&nbsp;输入发票号码、发票代码、发票校验码
																	</td>
																	<td
																		background="/entity/manager/teaching/satisfaction/images/wt_03.gif">
																		&nbsp;
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>
															<img
																src="/entity/manager/teaching/satisfaction/images/wt_01.gif"
																width="605" height="11">
														</td>
													</tr>
													<tr>
														<td>
															<img
																src="/entity/manager/teaching/satisfaction/images/wt_04.gif"
																width="604" height="13">
														</td>
													</tr>
													<tr>
														<td
															background="/entity/manager/teaching/satisfaction/images/wt_05.gif">
															<table width="95%" border="0" align="center"
																cellpadding="0" cellspacing="0">
																<tr>
																	<td>
																		<img
																			src="/entity/manager/teaching/satisfaction/images/wt_08.gif"
																			width="572" height="11">
																	</td>
																</tr>
																<tr>
																	<td
																		background="/entity/manager/teaching/satisfaction/images/wt_10.gif">
																		<table width="90%" border="0" align="center"
																			cellpadding="0" cellspacing="0">
																			<tr>
																				<td>
																					<table width="100%" border="0" cellspacing="0"
																						cellpadding="0">
																						
																						<tr>
																							<td width="20" valign="top">
																								<img
																									src="/entity/manager/teaching/satisfaction/images/ggzt.gif"
																									width="20" height="32">
																							</td>
																							<td width="100" class="text3"
																								style="font-size: 12px;" >
																								输入发票号码：
																							</td>
																							<td>
																								<input type="text" name="invoiceCode" id="code"
																									value="">
																							</td>
																						</tr>
																						<tr>
																							<td width="20">
																								<img
																									src="/entity/manager/teaching/satisfaction/images/ggzt.gif"
																									width="20" height="32">
																							</td>
																							<td width="100" class="text3"
																								style="font-size: 12px;">
																								输入发票代码:
																							</td>
																							<td>
																								<input type="text" value=""
																									name="invoiceFpdm" id="fpdm">
																							</td>
																						</tr>
																						<tr>
																							<td width="20">
																								<img
																									src="/entity/manager/teaching/satisfaction/images/ggzt.gif"
																									width="20" height="32">
																							</td>
																							<td width="100" class="text3"
																								style="font-size: 12px;">
																								输入发票校验码:
																							</td>
																							<td>
																								<input type="text" value=""
																									name="invoiceFpjym" id="fpjym">
																							</td>
																						</tr>
																						<tr>
																							
																							<td colspan ="3">
																								<font color="red" size="-1">输入多个发票号码多个发票代码多个发票校验码用英文","分隔
																								</font>
																							</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																			
																		</table>
																	</td>
																</tr>
																<tr>
																	<td>
																		<img
																			src="/entity/manager/teaching/satisfaction/images/wt_09.gif"
																			width="572" height="11">
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>
															<img
																src="/entity/manager/teaching/satisfaction/images/wt_06.gif"
																width="604" height="11">
														</td>
													</tr>
													<tr>
														<td align="center">
															<input type="button" value="确定" onclick="submits();" />
															&nbsp;&nbsp;&nbsp;&nbsp;
															<input type="button" value="返回"
																onclick="javascript:window.history.back(-1)" />
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
									<br />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>