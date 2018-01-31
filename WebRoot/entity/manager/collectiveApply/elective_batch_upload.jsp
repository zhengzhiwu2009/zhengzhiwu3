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
	<body leftmargin="0" topmargin="0" class="scllbar">
		<s:form name="batch"
			action="/entity/basic/collectiveElective_BatchUpload.action" method="POST"
			enctype="multipart/form-data" onsubmit="return FileTypeCheck();">
			
<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title"  id="zlb_title_start">集体报名</div ></td>
  </tr>
   <tr>
   	<td style="padding-left:20;"></td>
   </tr>         
  <tr> 
    <td valign="top" class="pageBodyBorder"  style="padding-left:5px;text-align:center;background:none;"> 
    <div class="border" style="padding-left:30px">
         <div align="left"  ><img src="/entity/manager/images/jd_01.jpg" /></div>
          <p>&nbsp;</p>
          <p align="left"><font color=red>提示：1、免费课程无需报名。2、“专项培训课程”请在“专项支付”菜单中报名。3、导入数据最多3000条</font></p>
          <p>&nbsp;</p>
          <table width="96%" border="0" cellpadding="0" cellspacing="0" style="text-align:left;">
            <tr valign="bottom" class="postFormBox"> 
              <td width="150"  valign="bottom"><span class="name">下载标准格式:</span></td>
              <td width=""> <span class="link" help="下载Excel格式的模版表格">
              <img src='/entity/manager/images/buttons/excel.png' alt='Print' width="20" height="20" title='Print'>&nbsp;
              <a href='/entity/manager/collectiveApply/stu_elective_batch.xls' target=_blank>Excel报表</a></span></td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">上载报名信息:</span></td>
              <td valign="bottom"><input type=file name="_upload" id="src"/></td>
            </tr>
            <tr class="postFormBox">
              <td >&nbsp;</td>
              <td><input type=button  onclick=" return submits();" value = "导入集体报名单" /></td>
            </tr> 
           <!--   <tr class="postFormBox">
              <td >&nbsp;</td>
              <td><input type=button value = "在线填写集体报名单" /></td>
            </tr> -->
         </table>
      </div>
    </td>
  </tr>
</table>
		</s:form>
	</body>
</html>