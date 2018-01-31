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
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">批量导入学生照片</div id="zlb_title_end"></td>
  </tr>
    
            
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb" align="center"> 
        <div class="cntent_k" id="zlb_content_start">
          <p>&nbsp;</p>
          <table width="68%" border="0" cellpadding="0" cellspacing="0">
            <tr>
            <td align="center"><b>导入结果</b><br/><br/></td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
            	
              <td width="77%" style="color: red;"> <span help="说明"><font size="4">
              <%  	
              	StringBuffer message = (StringBuffer)request.getAttribute("uploadErrors");
              	if(message.toString().length() == 0) {%>
              	所有照片都成功导入到系统中，可单击【学员管理】中的【学员信息列表】的【查看详细信息】查看已上传的照片
              <%} else {%> 
              	照片导入时发生如下错误，未出错的图片信息均成功导入：<br/>&nbsp;<%=message.toString() %>
              <% }%>
             </font></span></td>
            </tr>
            
                      
            
            <tr class="postFormBox">
              <td colspan="2" align="center"><button value="返回" onclick="window.navigate('/entity/manager/basic/image_upload.jsp')">返回</button> </td>
            </tr>
         </table>
      </div>

    </td>
  </tr>
</table>
</body>
</html>