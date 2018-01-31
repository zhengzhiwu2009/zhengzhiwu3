<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>tableType_2</title>
		<link rel="stylesheet" href="/entity/manager/css/admincss.css"
			type="text/css"></link>
		<script>
	//文件类型(*.xls)
function FileTypeCheck()
  {
  var obj =document.getElementById('src');
  if(obj.value==null || obj.value ==''){
  	alert('文件格式不正确！');
  	this.focus()
  	return false;
  }
  var length = obj.value.length;
  var charindex = obj.value.lastIndexOf(".");
  var ExtentName = obj.value.substring(charindex,charindex+4);
  if(!(ExtentName == ".xls" || ExtentName == ".XLS")){
  	alert('文件格式不正确！');
  	this.focus()
  	return false;
  }
  
  var bathid = document.getElementById("batchid").value;
  if(bathid==0){
  	alert('考试批次信息不正确!');
  	this.focus()
  	return false;
  }
  
  	return true;
  }
</script>
	</head>
	<body leftmargin="0" topmargin="0" class="scllbar">
		<s:form name="batch"
			action="/entity/exam/peBzzExamScore_uploadExamBatchExcel.action" method="POST"
			enctype="multipart/form-data" onsubmit="return FileTypeCheck();">
			<table width="80%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td>
						<div class="content_title" id="zlb_title_start">
							批量导入学生考试信息
						</div id="zlb_title_end">
					</td>
				</tr>


				<tr>
					<td valign="top" class="pageBodyBorder_zlb" align="center">
						<div class="cntent_k" id="zlb_content_start">
							<p>
								&nbsp;
							</p>
							<table width="68%" border="0" cellpadding="0" cellspacing="0">

								<tr valign="bottom" class="postFormBox">
									<td width="23%" valign="bottom">
										<span class="name">下载标准格式:</span>
									</td>
									<td width="77%">
										<span class="link" help="下载Excel格式的模版表格"><img
												src='/entity/manager/images/buttons/excel.png' alt='Print'
												width="20" height="20" title='Print'>&nbsp;<a
											href='/entity/manager/exam/examCardInfo.xls'
											target=_blank>Excel报表</a>
										</span>
									</td>
								</tr>
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">学生考试信息:</span>
									</td>
									<td valign="bottom">
										<input type=file name="upload" id="src" />
									</td>
								</tr>
										<s:select name="batchid" id="batchid" list="peBzzExamBatch" listKey="id" listValue="name" label="选择考试批次"
										 headerKey="0" headerValue="请选择上传批次">
										</s:select>
										<tr>
											<td>&nbsp;</td>
										</tr>
								<tr class="postFormBox">
									<td></td>
									 <td align=left>
									 <input type=submit value = "上传" />
									 <input type="button" value="返回" onclick="javaScript:window.location.href='/entity/exam/peBzzExamScore.action?backParam=true'">
									  </td>
								</tr>
								<tr>
								<td></td>
								<td>
								<font color="red">（注意：请将待上传的excel文件的单元格格式设为“文本”）</font>
								</td>
								
								</tr>
							</table>
						</div>

					</td>
				</tr>

			</table>
		</s:form>
	</body>
</html>