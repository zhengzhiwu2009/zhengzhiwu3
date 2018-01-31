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
<%
 ScormFactory scormFactory=ScormFactory.getInstance();
%>
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
  	
	//ʹ��struts2 ��ķ�ʽ
    MultiPartRequestWrapper mrequest = (MultiPartRequestWrapper)request;
    courseID=mrequest.getParameter("course_id");
    File[] files = mrequest.getFiles("coursezipfile"); 
    	//�ļ����ڻ�����ʱĿ¼�� 
    String[] fileNames = mrequest.getFileNames("coursezipfile"); 
  	
  	
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
  	}
  	
    
    //StrManage strManage=StrManageFactory.creat(mrequest.getParameter("course_title"));
   //	String courseTitle=strManage.charsetEncode("GBK","UTF-8") ;
   String courseTitle = mrequest.getParameter("course_title");
   	//String courseTitle=mrequest.getParameter("course_title");
   	//String zipFile = mrequest.getParameter( "theZipFile" );
   	String zipFile =uploadDir+File.separator+filename;
   	String controlType = mrequest.getParameter( "controltype" );
   	String navigate = mrequest.getParameter( "navigate" );
   	String scormType = mrequest.getParameter( "scorm_type" );
   	String indexFile = StringUtils.defaultIfEmpty(mrequest.getParameter("scorm_index"),"");
   	
    //out.print(courseTitle);
   	// Extract the manfest from the package
   	myPackageHandler = new LMSPackageHandler();
   	myPackageHandler.extract(zipFile, "imsmanifest.xml", uploadDir); 

   	String manifestFile = uploadDir + File.separator + "imsmanifest.xml";
   	String newZip="";
   	if(osname.equals("Linux"))
   	{
   		manifestFile = manifestFile.replace("\\","/");
   		newZip = zipFile.substring( zipFile.lastIndexOf("/")+1);
   	}
   	else
   	{
   		newZip = zipFile.substring( zipFile.lastIndexOf("\\")+1);
   	}
   	
   	zipFile = uploadDir + File.separator + newZip;
   
   	// Create a manifest handler instance
   	myManifestHandler = scormFactory.creatLMSManifestHandler();
   
   	InputSource fileToParse = setUpInputSource( manifestFile );
   	//fileToParse.setEncoding("gb2312");
   	myManifestHandler.setCourseID(courseID);
   	myManifestHandler.setCourseTitle(courseTitle);
   	myManifestHandler.setFileToParse( fileToParse );
   	myManifestHandler.setControl( controlType );
   	myManifestHandler.setNavigate(navigate);
    myManifestHandler.setScormType(scormType);
    myManifestHandler.setIndexfile(indexFile);
   	// Parse the manifest and fill up the object structure
   	boolean result = myManifestHandler.processManifest();
    
   	//ɾ��ԭ�μ��ļ�
   	if(new File(theWebPath + "CourseImports" +File.separator+ courseID+File.separator+scormType).exists()){
   		WhatyFileManage wfm = new WhatyFileManage();
   		wfm.delete(theWebPath + "CourseImports" +File.separator+ courseID+File.separator+scormType);
   	}
   	//new File(theWebPath + "CourseImports" +File.separator+ courseID).mkdirs();
    FileManage fm =  FileManageFactory.creat();
    fm.unZip(zipFile,theWebPath + "CourseImports" +File.separator+ courseID+File.separator+scormType);
    
   	//  Write the Sequencing Object to a file
   	String sequencingFileName = theWebPath + "CourseImports\\" + courseID + "\\" + scormType +  "\\sequence.obj";
   	if(osname.equals("Linux"))
   	 	sequencingFileName = sequencingFileName.replace("\\","/");
   	java.io.File sequencingFile = new java.io.File(sequencingFileName);
   	String t_filename=theWebPath + "CourseImports\\" + courseID + "\\" + scormType ;
   	if(osname.equals("Linux"))
   		t_filename=theWebPath + "CourseImports/" + courseID + "/" + scormType;
   	File f = new File(t_filename);
   	if(!f.exists()){
   		f.mkdirs();
   	}
   	FileOutputStream ostream = new FileOutputStream(sequencingFile);
   	ObjectOutputStream oos = new ObjectOutputStream(ostream); 
   	oos.writeObject(myManifestHandler.getOrgsCopy());
   	oos.flush();
   	oos.close();

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
   session.setAttribute("error",error);
   //out.println("e:"+e.toString());
   response.sendRedirect( "import.jsp?courseware_id="+courseID+"&error="+error);
}
%>

Error!:
<%=error%>

