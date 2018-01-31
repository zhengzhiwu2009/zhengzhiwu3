<%@ page language="java" pageEncoding="gb2312"%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@page
	import="java.util.*,com.whaty.util.string.*,com.whaty.util.file.*,com.whaty.platform.standard.scorm.util.*,com.whaty.platform.standard.scorm.*,com.whaty.platform.standard.scorm.operation.*,java.io.*,org.adl.parsers.dom.*,javazoom.upload.*,java.util.zip.*,org.xml.sax.InputSource"%>
<%@ page import="java.io.File"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ include file="importUtil.jsp"%>
<%@ page import="com.whaty.platform.util.*"%>
<jsp:directive.page
	import="org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper" />
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'imageUpload.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%
		String id = request.getParameter("id");
		
		String tempDir = System.getProperty("java.io.tmpdir"); 
		UploadBean upBean=new UploadBean();
		upBean.setParser(MultipartFormDataRequest.COSPARSER);
		upBean.setParsertmpdir(tempDir);
		upBean.setWhitelist("*.zip");
		upBean.setOverwrite(true);
		if (!MultipartFormDataRequest.isMultipartFormData(request))
		{
			return;
		}
		
		MultiPartRequestWrapper mrequest = (MultiPartRequestWrapper)request;

   		File[] files = mrequest.getFiles("imgFile"); 
   		String[] fileNames = mrequest.getFileNames("imgFile"); 
   		if(fileNames == null || fileNames.length == 0) {
	     	%>
	     	<script type="text/javascript">
	     		alert("未选择图片文件");
	     		hisory.back();
	     	</script>
	     	<%
    		return;
    	} else {
    		String ext = fileNames[0].substring(fileNames[0].lastIndexOf("."),fileNames[0].length());
    		String myExt = ".gif.jpg.bmp";
    		if(myExt.indexOf(ext) == -1) {
    		%>
	     	<script type="text/javascript">
	     		alert("请选择图片文件");
	     		hisory.back();
	     	</script>
	     	<%
	     	return ;
    		}
    	}
    	
    	if ( (files != null) && (files.length!=0) ) {
    	try {
    		String theWebPath = getServletConfig().getServletContext().getRealPath( "/" );
    		String fileName = id+fileNames[0].substring(fileNames[0].lastIndexOf("."),fileNames[0].length());
	    	String imagePath = "/entity/manager/basic/FlashImage/"+fileName;
	    	//System.err.println(imagePath);
			FileInputStream fis = new FileInputStream(files[0]); 
			FileOutputStream fos = new FileOutputStream(theWebPath + imagePath); 
			byte[] buf = new byte[1024]; 
			int j = 0; 
			while ((j = fis.read(buf)) != -1) { 
				fos.write(buf, 0, j); 
			}
			fis.close();
			fos.close();
			String sql = "update pe_bzz_onlinecourse pbo set pbo.flash_image='"+fileName+"' "
				+ "where pbo.id='"+id+"'";
			dbpool db = new dbpool();
			db.executeUpdate(sql);
		} catch(Exception e) {
			e.printStackTrace();
			%>
	     	<script type="text/javascript">
	     		alert("文件上传失败");
	     		hisory.back();
	     	</script>
	     	<%
		}
	  	}
	  	
	  	

	 %>
	 <script type="text/javascript">
	 	alert("上传成功！");
	 	window.navigate("image.jsp?id=<%=id%>");
	 </script>
  </head>
  
  <body>
    
  </body>
</html>
