<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>导入学员</title>
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
		alert("导入过程较慢，请耐心等候");
		document.getElementById('batch').submit();
	}
  }
</script>
	</head>
	<body leftmargin="0" topmargin="0"
		background="/entity/manager/teaching/satisfaction/images/bg2.gif">
		<form name="batch" id='batch' action="<s:property value='servletPath'/>_impLiveStudents.action" method="POST" enctype="multipart/form-data" onsubmit="return FileTypeCheck();">
			<input type="hidden" id="param_id" name="param_id" value="<s:property value="#session.param_id"/>" />
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
																	<td width="78">
																		<img
																			src="/entity/manager/teaching/satisfaction/images/wt_02.gif"
																			width="78" height="52">
																	</td>
																	<td width="100" class="text3"
																		style="padding-top: 25px; font-size: 12px;">
																		导入学员
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
																							<td>
																								<a
																									href="/entity/manager/studentStatus/impStudent.xls"
																									id="link" name="link" style="font-size: 12px;">下载标准模板</a>
																							</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																			<tr>
																				<td>
																					<table width="100%" border="0" cellspacing="0"
																						cellpadding="0">
																						<tr>
																							<td width="20">
																								<img
																									src="/entity/manager/teaching/satisfaction/images/ggzt.gif"
																									width="20" height="32">
																							</td>
																							<td width="80" class="text3"
																								style="font-size: 12px;">
																								选择文件:
																							</td>
																							<td>
																								<input type="file" name="_upload" id='src'>
																								<br />
																								<font color="red" size="-1">*
																									每次最多可导入1000人<br/>*
																									由于导入文件大小、网络环境等原因，导入过程可能较慢<br />点击导入后请耐心等待提示，不要进行其他操作，以免导入失败！
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
															<a href="###" onclick="submits();" class="tj"
																style="font-size: 12px;">[导入]</a>&nbsp;
															<a href="###"
																onclick="window.location.href='/entity/basic/sacLive.action'"
																class="tj" style="font-size: 12px;">[返回]</a>
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