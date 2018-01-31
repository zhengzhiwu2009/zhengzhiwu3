
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>中国证劵业协会培训平台－上传浮动图片</title>
<link href="<%=request.getContextPath()%>/work/manager/css/style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="../../../js/frame.js"></script>
<style type="text/css">
<!--
.w1 {
	font-family: "宋体";
	font-size: 12px;
	line-height: 24px;
	color: #333333;
	text-decoration: none;
}
.w2 {
	font-family: "宋体";
	font-size: 14px;
	line-height: 24px;
	font-weight: bold;
	color: #265A8E;
	text-decoration: none;
}
-->
</style>
<script>
function checkValues()
   {
   	  var sUrl = document.getElementById("imgFile").value;
   	  
   	  if(sUrl == null || sUrl == "") {
   	  	alert("请选择图片文件");
   	  	return false;
   	  }
      var Extlist = ".gif.jpg.bmp";
   	  Ext = GetExt(sUrl);
    
    if(Extlist.indexOf(Ext)==-1) {
    	alert("请选择图片文件,图片类型只能是gif,jpg或bmp");
   	  	return false;
    }

      return true;
   }
   function GetExt(sUrl)
{
        var arrList = sUrl.split(".");
        return arrList[arrList.length-1];
}
</script>
</head>
<%
	String id = request.getParameter("id");
	
	dbpool db = new dbpool();
	MyResultSet rs = null;
	String sql = "select pbo.flash_image from pe_bzz_onlinecourse pbo where pbo.id='"+id+"'";
	rs = db.executeQuery(sql);
	String imageBase = "/entity/manager/basic/FlashImage/";
	String imagePath = "default.jpg";
	if(rs.next() && rs.getString("flash_image")!= null) {
		imagePath = rs.getString("flash_image");
	}
	db.close(rs);
 %>
<body leftmargin="0" rightmargin="0" style="background-color:transparent;" class="scllbar">
	<!-- start:图标区域 -->
	<!-- end:图标区域 -->
	<form name='courseInfo' action='imageUpload.jsp' method='post' class='nomargin' onSubmit="return checkValues()"  enctype="multipart/form-data">
		<input type="hidden" name="id" value="<%=id %>" />
	<!-- start:内容区域1 -->
	<div unselectable="on" class="border">   
		<table width=60%  border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="3F6C61">
		<tr bgcolor="#FFFFFF">
			<td align='center' bgColor=#E6EFF9 class="w2" colspan="2">查看浮动图片</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='center' valign="middle" bgColor=#E6EFF9 class="w2" colspan="2"><img src="<%=imageBase %><%= imagePath%>"/></td>
		</tr>
		
		<tr bgcolor="#FFFFFF">
			<td bgColor=#ffffff class="w1">图片文件</td>
			<td bgColor=#ffffff class="w1"><input type="file" name="imgFile" id="imgFile" /></td>
		</tr>
		<tr bgcolor="#FFFFFF">
		<td colspan="3" align="center" bgColor=#ffffff class="w1">
		 <input name="ok" value="提 交" type="submit" class="dialogbutton" onmouseover="this.className='dialogbuttonOver'" onmouseout="this.className='dialogbutton'">
		
    <input name="cancel" type="button" value="关 闭" onclick="window.close()" class="dialogbutton" onmouseover="this.className='dialogbuttonOver'" onmouseout="this.className='dialogbutton'">
		</td>
		</tr>
		</table>
	</div>
	<!-- end:内容区域1 -->
	<!-- button row start -->
		<div class="dialogbuttons" unselectable="on">
		
   
		</div>
	<!-- button row end -->
	
</form>

	<!-- 内容区域结束 -->
</body>
</html>

