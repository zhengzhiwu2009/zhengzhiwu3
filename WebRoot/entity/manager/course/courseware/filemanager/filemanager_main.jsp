




<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ path + "/";

	/*---------------------------------------------
	功能描述:文件管理主页面
	编写时间:2004-8-28
	编写人:陈健
	email:陈健@whaty.com
	
	修改情况记录
	修改时间：  修改内容：   修改人：
	-----------------------------------------------*/
%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.whaty.platform.database.oracle.*" %>
<%@page  import="com.whaty.platform.entity.bean.PeBzzTchCourseware" %>
<jsp:useBean id="file_work" scope="page" class="com.whaty.filemanager.filemanager"/>
<jsp:useBean id="encode" scope="session" class="com.whaty.encrypt.encode"/>
<jsp:useBean id="parameter" scope="application" class="com.whaty.encrypt.parameter" />

<%!
	public String readFile(String srcFile) throws IOException{
		RandomAccessFile myFile=new RandomAccessFile(srcFile,"r");
		byte[] buffer=new byte[10000];
    	String Codes="";
    	int myBytes;
    	while ((myBytes=myFile.read(buffer,0,10000))>=0){
    		Codes+=new String(buffer,0,0,myBytes);
    	}
    	myFile.close();
    	return com.whaty.encrypt.appbox.convertEncoding("ISO8859-1","utf-8",Codes);
    }
    
    	String fixnull(Object str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
	    	 return "";
	    else return str.toString();
	   
		
	}
%>

<%
	String Root_path,aaa,s_courseid;
	File current_path;
	
	String base_dir=request.getContextPath();
	String root_dir=parameter.g_root;
	String domain=parameter.g_domain;
	String port1 = parameter.port;
	String rootDir=domain+":"+port1+root_dir;
	//System.out.println("rootDir+"+rootDir);
	
	
	
	//////////wuhao
		String courseware_code=fixnull(request.getParameter("courseware_id"));
	String courseware_id=fixnull(request.getParameter("courseware_id"));
	String courseware_name=fixnull(request.getAttribute("courseware_name"));
	String theWebPath="";
	/***whatyutil的fileManage使用解压方法时多层次结构目录会失败，所以先创建目录***/
	/*创建课件目录start huze*/
	try {
		theWebPath = getServletConfig().getServletContext().getRealPath( "/" );
		new File(theWebPath + "CourseImports" +File.separator+courseware_code).mkdirs();
	}catch (Exception e) {
	}
%>
<%
  	//String courseware_id=request.getParameter("courseware_id");
	//String courseware_name="";
	HttpSession se=request.getSession();
	PeBzzTchCourseware coursewareinfo =(PeBzzTchCourseware)se.getAttribute("coursewareinfo");
	if(coursewareinfo!=null)
	{
		courseware_id=coursewareinfo.getId();
		courseware_name=coursewareinfo.getName();
	}

  Vector vector_file = new Vector();
  Vector vector_dir= new Vector();
  String dir_path=request.getParameter("dir");

  //prepare for hashtable;
  Hashtable dirtable=new Hashtable();
  Hashtable filetable=new Hashtable();
  if(session.getValue("dirtable")!=null)
  {
   session.putValue("dirtable",null);
  }
  if(session.getValue("filetable")!=null)
  {
   session.putValue("filetable",null);
  } 
  

  //Root_path:课程的根目录
  //for Linux 
 	Root_path=	file_work.changeseperator(theWebPath + "CourseImports" +File.separator+courseware_code);
  //for Windows
  //Root_path="H:\\InetPub\\wwwroot\\course\\courseware\\"+s_courseid;
  
  encode.setRootpath(Root_path);
  encode.setCourseid(courseware_id);
  encode.Initiate(Root_path);
  current_path=new File(theWebPath + "CourseImports" +File.separator+courseware_code);
  //System.out.println("=============="+current_path);
 
	
  //for Linux 
  String Perl_path=base_dir+"/perl/";
  //for Windows
  // String Perl_path="H:/InetPub/wwwroot/perl/";
  
  //s_current_path:当前目录的session
  //current _path:当前目录 
  //this section for encode
  //encode.setFilepath(Perl_path);
  //encode.setUrlname("urlmap.txt");
  encode.setRootpath(Root_path);
  if(dir_path==null) 
  {
  	current_path=new File(Root_path);
  	session.putValue("s_current_path",Root_path);	
  }
  
  if(session.getValue("s_current_path")==null)
  {
       out.print("非法操作");
       return;
   }
   
  String s_current_path=(String)session.getValue("s_current_path");
   
  if(dir_path==null)
  {
  	//根目录//theWebPath + "CourseImports" +File.separator+courseware_code
  	current_path=new File(s_current_path);
  }
  else if(dir_path.compareTo(".")==0)
  {
  	//当前目录
  	current_path=new File(s_current_path);
  }
  else if(dir_path.compareTo("..")==0)
  {
  	//上一级目录
  	aaa=file_work.parentdir(s_current_path);
  	current_path=new File(aaa);
  }
  else
  { 
  	//子目录
  	current_path=new File(s_current_path+"/"+dir_path);
  }
   if (!current_path.isDirectory()){
  	current_path=new File(s_current_path);
  }

  session.putValue("s_current_path",current_path.toString());
  s_current_path =(String)session.getValue("s_current_path");
  s_current_path = file_work.changeseperator(s_current_path);

  //out.print(s_current_path+"<br/>");
  //out.print(Root_path+"<br/>");
  //out.print(s_current_path.indexOf(Root_path));
  
 //判断当前目录是否在根目录下
  if(s_current_path.indexOf(Root_path)<0)
  {
	  out.print("目录错误");
	     
  	return;
  }
  
 

  String file_list[]=current_path.list();
  if(file_list!=null&&file_list.length>0){
 
		  for(int i=0;i<file_list.length;i++)
		  {
		  	File filetemp=new File(current_path,file_list[i]);
		  	String s_filetemp=filetemp.getName();
		        if(filetemp.isDirectory())
		  	   {
		  	   	file_work.addToVector(vector_dir,filetemp,s_filetemp);
		  	   	if(encode.IsDirEncoded(filetemp.toString()))
		                  {
		                    dirtable.put(filetemp.toString().intern(),filetemp.toString());
		                  }
		  	   }
		  	   else
		  	   {
		  	       
		  	   	file_work.addToVector(vector_file,filetemp,s_filetemp);
		  	   	if(encode.IsFileEncoded(filetemp.toString()))
		  	   	{
		  	   	  filetable.put(filetemp.toString().intern(),filetemp.toString());
		  	   	 }
		  	   }
		   }
    }
   session.putValue("dirtable",dirtable);
   session.putValue("filetable",filetable);
   
  %>

<html>
<head>
<title>课件文件管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<LINK href="../css/style.css" type=text/css rel=stylesheet>
<script language="../	JavaScript1.2" src="js/fader.js"></script>

<script language=javascript>

function nogoingback()
{ 
history.go(1) == false;
}
  function ftpupdate()
  {
	window.open("ftpupdate.htm", "ftp", "toolbar=yes location=no titlebar=no scrollbar=yes resizable=yes");
  }
</script>
</head>
<body link="#333399" vlink="#333399" alink="#333399" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
=<br/>
<script language=javascript>
//nogoingback();
</script>
<center>
 <table width=85% cellpadding=2 cellspacing=1 border=0 align=center bgcolor=#999999>
    <tr class=maindownmenu>
      <td width=100% height=20 style="color: white">
        &nbsp; * 课程管理 >> 课件文件管理 
      </td>
    </tr>
    <tr bgcolor=#F8F8F8>
      <td width=100% height=20>
      <!--main--><font class=Verdana8>
  <%
   String s_current_path1 = (String)session.getValue("s_current_path");    
   	//System.out.println("s_current_path1->"+s_current_path1);
	int index1 = s_current_path1.indexOf("CourseImports");
	String s_current_path2 = s_current_path1.substring(index1); 
	//System.out.println("s_current_path2->"+s_current_path2);
   String s_current_path3 = "/"+s_current_path2;
//  out.print("&nbsp;&nbsp; 当前目录:"+"http://"+domain+file_work.changeseperator(((String)session.getValue("s_current_path")).substring(38))); 
  	
 // 	out.print("&nbsp;&nbsp; 当前目录:"+theWebPath + "CourseImports" +File.separator+courseware_code); 
 	out.print("&nbsp;&nbsp; 当前目录:"+Root_path);
  
//  String strCurPath="http://"+domain+file_work.changeseperator(((String)session.getValue("s_current_path")).substring(38));
	String strCurPath="http://"+rootDir+file_work.changeseperator((s_current_path3));
  %>
       </font></td>
     </tr>
     <tr bgcolor=#F8F8F8>
       <td width=100%>  
  <%//创建新目录:%>
  
  <table cellspacing=0 cellpadding=0 border=0>
  <form action="new_dir.jsp" method=post>
  <tr height=25>	
  	<td>&nbsp; 创建新目录:</td>
  	<td><input name=newdir size=20 class=smart> <input type=submit value=创建 class=smart></td>
  </tr>
  </form>
  <%//上载文件%>
  <!--  	
  <form action="/entity/teaching/peBzzCourseManager_saveUpload.action"  method="post" ENCTYPE="multipart/form-data">
  <form action="upload_info.jsp" method="post" ENCTYPE="multipart/form-data">-->
 <form action="upload_info1.jsp" method="post" ENCTYPE="multipart/form-data">
  <tr height=25>	
  	<td>&nbsp; 上载文件:</td>
  	<input type="hidden" name="courseware_code" value="<%=courseware_code %>"/>
  	<td><input class=smart NAME="coursezipfile" size=20 type=file> <input type=submit value="上载" class=smart></td>
  	<!-- <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="点此以Ftp方式上传课件" onclick="ftpupdate()"></td>
  	 -->
  </tr>
  </form>
  </table>
  <table BORDER="0" cellspacing="1" cellpadding="1" width="100%" bgcolor="#eeeeee">
  <form name=encoder action="encode.jsp">
   <tr class=maindownmenu height=20>
   <td align=center><font color="white">类型</font></td>
   <td align=center><font color="white">名称</font></td>
   <td align=center><font color="white">大小</font></td> 
   <td align=center><font color="white">修改时间</font></td>
   <td align=center><font color="white">修改</font></td>
   <td align=center><font color="white">删除</font></td>
   <!--
   <td align=center><a href="javascript:formsubmit()"><font color="white" face="arial,helvetica">加密解密</font></a></td>
   -->
   </tr>
     <%if(((String)session.getValue("s_current_path")).compareTo(Root_path)!=0)
   {%>
   <tr>
                <td align="center" nobreak><A a href=filemanager_main.jsp?dir=..><img border=0 src="parent.gif" alt="Parent directory" border="0"> </a></td>
                <td nobreak><a href=filemanager_main.jsp?dir=..><font size="-1" face"arial,helvetica">上一级目录</font></a></td>
                <td align="right" nobreak><font class=Verdana8> </font><br/></td>
                <td align="right" nobreak><font class=Verdana8> </font><br/></td>  
                <td>&ldquo;</td>
                <td><br/></td>
                <!--
                <td></td>
                <td></td>
                -->
   </tr>
    <%}%>
    <%for(int k = 0; k < vector_dir.size(); k++)
     {
     	String dirlist = (String)vector_dir.elementAt(k);
     	String dir_size=file_work.getSize((String)session.getValue("s_current_path"),dirlist);
     	String dir_modified=file_work.getModified((String)session.getValue("s_current_path"),dirlist);
   %>
            <tr>
                <td align="center" nobreak><a href=filemanager_main.jsp?dir=<%=dirlist%>><img border=0 src="dir_dir.gif" alt="Change directory to <%=dirlist%>" border="0"></a></td>
                <td nobreak><a href=filemanager_main.jsp?dir=<%=dirlist%>><font class=Verdana8><%=dirlist%></font></a></td>
                <td align="right" nobreak><font class=Verdana8><%=dir_size%></font></td>
                <td align="center" nobreak><font class=Verdana8><%=dir_modified%></font></td>
                <td>&nbsp; </td>
                <td align="center" nobreak><a href="delete_dir.jsp?dir=<%=dirlist%>"><img border=1 src="delete.gif" alt="删除文件夹<%=dirlist%>"> </a></td>
                <!--
                <td align="center" nobreak>
                <%
                  //File temp=new File((String)session.getValue("s_current_path"),dirlist);
                  //if(encode.IsDirEncoded(temp.toString()))
                  //{
                 %>
                 <input type="checkbox" name=check_dir[]  checked value=<%//=temp.toString()%>>
                 <% //}
                 //else{ %>
                 <input type="checkbox" name=check_dir[]  value=<%//=temp.toString()%>>
                 <%//}%>
                 </td>
                 <td></td>
                 -->
           </tr>
                
     <%}%>
     <%for(int k = 0; k < vector_file.size(); k++)
     {
     	String file_type,file_ctype;
     	String file_size,file_modified;
     	String filelist = (String)vector_file.elementAt(k);
     	String s_dix=filelist.substring(filelist.lastIndexOf(".")+1);
     	file_size=file_work.getSize((String)session.getValue("s_current_path"),filelist);
     	file_modified=file_work.getModified((String)session.getValue("s_current_path"),filelist);
     	if(s_dix.equalsIgnoreCase("htm") || s_dix.equalsIgnoreCase("html"))
     	{
     		file_type="htm";
     		file_ctype="网页";
     		
     	}
     	else if(s_dix.equalsIgnoreCase("gif") || s_dix.equalsIgnoreCase("JPG") || s_dix.equalsIgnoreCase("bmp") || s_dix.equalsIgnoreCase("png"))
     	{
     		file_type="img";
     		file_ctype="图片";
     	}
     	else if(s_dix.equalsIgnoreCase("jsp") || s_dix.equalsIgnoreCase("asp") || s_dix.equalsIgnoreCase("php"))
     	{
     		file_type="scr";
     		file_ctype="脚本";
     	}
     	else if(s_dix.equalsIgnoreCase("zip"))
     	{
     		file_type="zip";
     		file_ctype="压缩文件";
     	}
     	else if(s_dix.equalsIgnoreCase("txt"))
     	{
     		file_type="txt";
     		file_ctype="纯文本文件";
     	}
     	else 
     	{
     		file_type="misc";
     		file_ctype="其它";
     	}
      %>
            <tr>
                <td align="center" nobreak><img border=0 src="<%="dir_"+file_type+".gif"%>" alt=<%=file_ctype%>></td>
                <td nobreak>
                	<% if (file_type.equals("txt")||file_type.equals("htm")){ %>
                	<a href="<%=strCurPath%>/<%=filelist%>" target="_new"><font class=Verdana8><%=filelist%></font></a>
                	<% }else{ %>
                	<font class=Verdana8><%=filelist%></font>
                	<% } %>
                </td>
                <td align="right" nobreak><font class=Verdana8><%=file_size%></font></td>
                <td align="center" nobreak><font class=Verdana8><%=file_modified%></font></td>
                <td align="right" nobreak>            
                 <%
                  if(file_type.compareTo("htm")==0||file_type.compareTo("html")==0)
                  {                  	
                %><a href="updatefile.jsp?file=<%=filelist%>"><img border=0 src="edit.gif" alt="在线修改"></a>
                  
                <%}
                  else if(file_type.compareTo("zip")==0)
                  {
                  %><a href="unzip.jsp?file=<%=filelist%>"><img border=0 src="exit.gif" width="16" height="16" alt="解开文件夹"></a>
                  <%}%>
                  
                 
                 <% if (file_type.equals("zip")){ %>
                  <a href="unzip.jsp?file=<%=filelist%>">解开文件</a>
                 <% } %> 
                </td>
                <td align="center" nobreak>
                	<% if (!file_type.equals("scr")){ %>
                	<a href="delete_file.jsp?file=<%=filelist%>"><img border=1 src="delete.gif" alt="删除文件<%=filelist%>"> </a>
                	<% } %>
                </td>
                <!--
                <td align="center" nobreak>
                <%
                  //File ftemp=new File((String)session.getValue("s_current_path"),filelist);
                  //if(encode.IsFileEncoded(ftemp.toString()))
                  //{
                 %>
                 <input type="checkbox" name=check_file[]  checked value=<%//=ftemp.toString()%>>
                 <% //}else{ %>
                 <input type="checkbox" name=check_file[]  value=<%//=ftemp.toString()%>>
                 <%//}%>
                 </td>
                 -->
           </tr>
                
     <%}%>
<!--
     <tr>
       <td></td>
       <td></td>
       <td></td>
       <td></td>
       <td></td>
       <td align="right" nobreak><font class=Verdana8>全部选中</font></td>
       <td align="center" nobreak><input name="allbox" type="checkbox" value="Check All" onClick="javascript:CheckAll()"></td>
       <td></td>
      </tr>
-->
      </form>
    </table>

  <!-- end of main -->   
  </td>
  </tr>
 </table>      
</center>      
<br/>
<br/>

<script>
function CheckAll()
{
	for (var i=0;i<encoder.elements.length;i++)
	{
		var e = encoder.elements[i];
		if(e.type=="checkbox"){
		        if (!(e.name == "allbox")){
			  e.checked = encoder.allbox.checked;
			}
	        }
			
	}
}
function formsubmit(){
	encoder.submit(true);
}
</script> 

</body>
</html>

