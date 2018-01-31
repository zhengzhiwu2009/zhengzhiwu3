<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>批量修改学员学习进度</title>
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
	var obj =document.getElementById('src');
	if(obj.value==null || obj.value ==''){
		alert('文件不能为空，请先选择正确文件');
		return false;
	}else{
		alert("过程较慢，请耐心等候");
		document.getElementById('batch').submit();
	}
}
</script>
	</head>
	<body leftmargin="0" topmargin="0" background="/entity/manager/teaching/satisfaction/images/bg2.gif">
		<form name="batch" id='batch' action="<s:property value='servletPath'/>_impStuLearnStatus.action" method="POST" enctype="multipart/form-data" onsubmit="return FileTypeCheck();">
			<input type="hidden" id="param_id" name="param_id" value="<s:property value="id"/>" />
			<input type="hidden" id="id" name="id" value="<s:property value="id"/>" />
			<input type="hidden" value="<s:property value='servletPath'/>_impStudents.action" />
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td valign="top" class="bg3">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="86" valign="top" background="/entity/manager/teaching/satisfaction/images/top_01.gif"></td>
							</tr>
							<tr>
								<td align="center" valign="top">
									<table width="608" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
										<tr>
											<td bgcolor="#FFFFFF">
												<table width="100%" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td>
															<table width="100%" border="0" cellspacing="0" cellpadding="0">
																<tr>
																	<td width="78">
																		<img src="/entity/manager/teaching/satisfaction/images/wt_02.gif" width="78" height="52">
																	</td>
																	<td width="120" class="text3" style="padding-top: 25px; font-size: 12px;">
																		批量修改学员学习进度
																	</td>
																	<td background="/entity/manager/teaching/satisfaction/images/wt_03.gif">
																		&nbsp;
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>
															<img src="/entity/manager/teaching/satisfaction/images/wt_01.gif" width="605" height="11">
														</td>
													</tr>
													<tr>
														<td>
															<img src="/entity/manager/teaching/satisfaction/images/wt_04.gif" width="604" height="13">
														</td>
													</tr>
													<tr>
														<td background="/entity/manager/teaching/satisfaction/images/wt_05.gif">
															<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
																<tr>
																	<td>
																		<img src="/entity/manager/teaching/satisfaction/images/wt_08.gif" width="572" height="11">
																	</td>
																</tr>
																<tr>
																	<td background="/entity/manager/teaching/satisfaction/images/wt_10.gif">
																		<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
																			<tr>
																				<td>
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr>
																							<td width="20" valign="top">
																								<img src="/entity/manager/teaching/satisfaction/images/ggzt.gif" width="20" height="32">
																							</td>
																							<td>
																								<a href="/entity/manager/studentStatus/stuLearnStatus.xls" id="link" name="link" style="font-size: 12px;">下载标准模板</a>
																							</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																			<tr>
																				<td>
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr>
																							<td width="20">
																								<img src="/entity/manager/teaching/satisfaction/images/ggzt.gif" width="20" height="32">
																							</td>
																							<td width="130" class="text3" style="font-size: 12px;">
																								选择文件:
																							</td>
																							<td>
																								<input type="file" name="_upload" id='src'>
																							</td>
																						</tr>
																						<tr>
																							<td colspan="2">&nbsp;</td>
																							<td>
																								<font color="red" size="-1">*每次最多修改20人(超过20人部分不进行操作)！由于文件<br />&nbsp;&nbsp;大小、网络环境等原因修改过程可能较慢，点击修改后请<br />&nbsp;&nbsp;耐心等待提示，不要进行其他操作，以免修改失败！ </font>
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
																		<img src="/entity/manager/teaching/satisfaction/images/wt_09.gif" width="572" height="11">
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>
															<img src="/entity/manager/teaching/satisfaction/images/wt_06.gif" width="604" height="11">
														</td>
													</tr>
													<tr>
														<td align="center">
															<input type="button" value="执行修改" onclick="submits();" />&nbsp;&nbsp;&nbsp;&nbsp;
															<input type="button" value="返    回" onclick="javascript:window.location.href='/entity/basic/peBzzStudent.action'" />
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
									<br/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>