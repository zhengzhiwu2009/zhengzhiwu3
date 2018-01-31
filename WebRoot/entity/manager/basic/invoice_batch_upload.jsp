<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>中国证券业协会远程培训系统</title>
		<!-- <link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"> -->
		<style type="text/css"><!--<%@ include file="/entity/manager/css/admincss.css"%>--></style>
		<style type="" ></style>
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
  if(!(ExtentName == ".xls" )){
  	alert('文件格式不正确！');
  	this.focus()
  	return false;
  }
  
  	return true;
  }
</script>
	</head>
	<body leftmargin="0" topmargin="0" class="scllbar">
		<s:form name="batch"
			action="/entity/basic/invoiceManage_batchUpload.action" method="POST"
			enctype="multipart/form-data" onsubmit="return FileTypeCheck();">
			
<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title"  id="zlb_title_start">批量导入发票号</div ></td>
  </tr>
   <tr>
   	<td style="padding-left:20;"></td>
   </tr>         
  <tr> 
    <td valign="top" class="pageBodyBorder"  style="padding-left:5px;text-align:center;background:none;"> 
    <div class="border" style="padding-left:30px">
          <table width="96%" border="0" cellpadding="0" cellspacing="0" style="text-align:left;">
            <tr valign="bottom" class="postFormBox"> 
              <td width="150"  valign="bottom"><span class="name">下载标准格式:</span></td>
              <td width=""> <span class="link" help="下载Excel格式的模版表格">
              <img src='/entity/manager/images/buttons/excel.png' alt='Print' width="20" height="20" title='Print'>&nbsp;
              <a href='/entity/manager/invoice/invoice_batch.xls' target=_blank>Excel模板</a></span></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">上载发票信息:</span></td>
              <td valign="bottom"><input type=file name="_upload" id="src"/></td>
            </tr>
            <tr class="postFormBox">
              <td ><button onclick="history.go(-1);">返回</button></td>
              <td><input type=submit value = "导入发票号码" /></td>
            </tr>
         </table>
      </div>
    </td>
  </tr>
</table>
		</s:form>
	</body>
</html>