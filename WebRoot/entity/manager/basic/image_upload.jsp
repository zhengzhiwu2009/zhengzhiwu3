<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>中国证券业协会远程培训系统</title>
<link href="<%=request.getContextPath()%>/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
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
  if(ExtentName.toLowerCase() != ".zip"){
  	alert('文件格式不正确！');
  	this.focus()
  	return false;
  }
  	return true;
  }
</script>
</head>
<body leftmargin="0" topmargin="0" class="scllbar">
<form name = "batch" action="<s:property value='servletPath'/>_uploadImage.action" method="POST"  enctype="multipart/form-data" onsubmit ="return FileTypeCheck();">
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">批量导入学生照片</div id="zlb_title_end"></td>
  </tr>
    
            
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb" align="center"> 
        <div class="cntent_k" id="zlb_content_start">
          <p>&nbsp;</p>
          <table width="68%" border="0" cellpadding="0" cellspacing="0">
            
            <tr valign="bottom" class="postFormBox"> 
              <td width="23%"  valign="middle" align="left" style="color: red;"><span class="name">说明：</span></td>
              <td width="77%" style="color: red;"> <span help="说明"><font size="4">
              	◎请将所要上传的图片文件命名为该学生的系统编号（如：200988899999），文件的类型可是jpg,png,gif,jpeg,bmp,tiff；<br/>
              	◎请将所有要上传的图片以zip格式压缩，压缩文件中不应包含文件夹(即所有图片文件直接压缩到zip文件中，不要带目录压缩)；<br/>
              	◎ 单个上传时，请在学员信息列表双击某学员的记录，打开修改界面后选择图片文件并更新学员信息即可(此处文件名可任意，文件类型同上)。
             </font></span></td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">上载学生照片:</span></td>
              <td valign="bottom"><input type=file name="upload" id="src"/></td>
            </tr>
                      
            
            <tr class="postFormBox">
              <td colspan="2" align="center"><input type=submit value = "上传" /></td>
            </tr>
         </table>
      </div>

    </td>
  </tr>
</table>
</form>
</body>
</html>