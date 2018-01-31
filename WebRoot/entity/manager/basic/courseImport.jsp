<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
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

String sessionID = new String();
String uploadDir = new String();
String userDir = new String();
String error = new String();
LMSManifestHandler myManifestHandler;
LMSPackageHandler myPackageHandler;

String osname=SystemInfoUtil.getOsName();
//out.println("isname:"+osname);
String courseID=null;
String courseCode=null;
try
{
	sessionID = session.getId();
   	String theWebPath = getServletConfig().getServletContext().getRealPath( "/" );
   	String tempDir = System.getProperty("java.io.tmpdir"); 
	UploadBean upBean=new UploadBean();
	upBean.setParser(MultipartFormDataRequest.COSPARSER);
	upBean.setParsertmpdir(tempDir);
	upBean.setWhitelist("*.zip");
	upBean.setOverwrite(true);
	ScormLog.setDebug("encoding="+MultipartFormDataRequest.DEFAULTENCODING);
	
	if (!MultipartFormDataRequest.isMultipartFormData(request))
	{
		return;
	}
  //	MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);
  	
	//使用struts2 后的方式
    MultiPartRequestWrapper mrequest = (MultiPartRequestWrapper)request;
    courseID=mrequest.getParameter("id");
	courseCode=mrequest.getParameter("code");
    File[] files = mrequest.getFiles("coursezipfile"); 
    
    	//文件现在还在临时目录中 
    String[] fileNames = mrequest.getFileNames("coursezipfile"); 
     if(fileNames == null || fileNames.length == 0) {
    	response.sendRedirect( "import.jsp?id="+courseID+"&error=无上传文件");
    	return;
    }
  	
	//uploadDir = "d:\\SampleRTEFiles\\tempUploads\\" + sessionID;
	uploadDir =tempDir + File.separator + "tempUploads" + File.separator + sessionID;
	java.io.File theRTEUploadDir = new java.io.File( uploadDir );
   	if ( !theRTEUploadDir.isDirectory() )
   	{
      	theRTEUploadDir.mkdirs();
   	}
   	upBean.setFolderstore(uploadDir);

	String filename="";
  	if ( (files != null) && (files.length!=0) ) {
    	
		FileInputStream fis = new FileInputStream(files[0]); 
		FileOutputStream fos = new FileOutputStream(uploadDir+File.separator+fileNames[0]); 
		byte[] buf = new byte[1024]; 
		int j = 0; 
		while ((j = fis.read(buf)) != -1) { 
		fos.write(buf, 0, j); 
		}
		fis.close();
		fos.close();
  	  	filename=fileNames[0];	
  	  	
  	  	if(!filename.toLowerCase().endsWith("zip")) {
  	  		response.sendRedirect( "import.jsp?id="+courseID+"&error=请上传zip格式文件");
   			return;
  	  	}
  	}
  	
   	String zipFile =uploadDir+File.separator+filename;

   	String scormType = mrequest.getParameter( "scorm_type" );
   	String indexFile = StringUtils.defaultIfEmpty(mrequest.getParameter("scorm_index"),"");
   	if(scormType == null || "".equals(scormType)) {
   		response.sendRedirect( "import.jsp?id="+courseID+"&error=课件类型不能为空");
   		return;
    }
   	if(indexFile == null || "".equals(indexFile)) {
   		response.sendRedirect( "import.jsp?id="+courseID+"&error=课件首页不能为空");
   		return;
    }
   	String tScormType = "";
   	//System.err.println(scormType);
   	if(scormType.trim().toUpperCase().equals("NORMAL")) {
   		tScormType = "";
   	} else {
   		tScormType = File.separator+scormType;
   	}
   	
   	// Extract zip file
    
    FileManage fm =  FileManageFactory.creat();
    String destPath = theWebPath + "CourseImports" +File.separator+ "OnlineCourse"+File.separator + courseCode;
    new File(destPath).mkdirs();
    destPath += tScormType;
    fm.unZip(zipFile,destPath);
 	String dbPath = File.separator + "CourseImports" +File.separator+ "OnlineCourse"+File.separator + courseCode + tScormType + File.separator + indexFile;
    //System.err.println(dbPath);
    dbPath = dbPath.replace("\\","/");
    dbpool db = new dbpool();
    String sql = "update pe_bzz_onlinecourse pbo set pbo.index_url='"+dbPath+"' \n"
			+ "where pbo.id='"+courseID+"'";
	db.executeUpdate(sql);
    
    sql = "insert into scorm_sco_launch(id,scorm_type,launch,indexfile,course_id) values(s_scorm_launch_id.nextval, '"+scormType+"','"+dbPath+"','"+indexFile+"','"+courseID+"')";
   	//System.out.println(sql);
   	db.executeUpdate(sql);
   	
   	// Delete uploaded files
   	boolean wasdeleted = false;
   	java.io.File uploadFiles[] = theRTEUploadDir.listFiles();
   	for( int i = 0; i < uploadFiles.length; i++ )
   	{
      	uploadFiles[i].deleteOnExit();
   	}   
   	try {
   		theRTEUploadDir.deleteOnExit();
   	}catch(Exception e) {
   	}  

   	// set content-type header before accessing Writer
   	response.setContentType("text/html");
   	response.sendRedirect( "confirmImport.jsp?course_id="+courseID);

}
catch (Exception e)
{
   error = e.getLocalizedMessage();
   response.sendRedirect( "import.jsp?id="+courseID+"&error="+error);
}
%>

Error!:
<%=error%>

