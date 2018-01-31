<%
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
<%@ page import="java.util.*,java.net.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@page import="java.io.*"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.whaty.platform.entity.service.GeneralService"%>



<jsp:useBean id="file_work" scope="page" class="com.whaty.filemanager.filemanager"/>
<jsp:useBean id="encode" scope="session" class="com.whaty.encrypt.encode"/>


<%!
	/* public String readFile(String srcFile) throws IOException{
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
*/

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
	
	String base_dir="";
	ServletContext context = request.getSession().getServletContext();
 	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
    GeneralService service = (GeneralService)ctx.getBean("generalService");
	//String root_dir=parameter.g_root;
	//String domain=parameter.g_domain;
	//String port1 = parameter.port;
	//String rootDir=domain+":"+port1+root_dir;
	
	//System.out.println("rootDir+"+rootDir+"--------------------base_dir"+base_dir);
%>
<%
	String theWebPath="";
  	String courseware_id="";
	String courseware_name="";
	String courseware_code="";
	
		courseware_id=fixnull(request.getParameter("courseware_id"));
		courseware_code=fixnull(request.getParameter("courseware_id"));
		if (courseware_id!=null && !"".equals(courseware_id)) {
			session.setAttribute("courseware_id",courseware_id);
		}
		//System.out.println("&&&&&"+session.getAttribute("courseware_id"));
		try {
			theWebPath = getServletConfig().getServletContext().getRealPath( "/" );
			new File(theWebPath + "CourseImports" +File.separator+courseware_code).mkdirs();
		}catch (Exception e) {
		}
		
		base_dir= file_work.changeseperator(theWebPath);
 
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
 	Root_path=base_dir+"CourseImports/"+courseware_id;
  //for Windows
  //Root_path="H:\\InetPub\\wwwroot\\course\\courseware\\"+s_courseid;
  
  encode.setRootpath(Root_path);
  encode.setCourseid(courseware_id);
  encode.Initiate(Root_path);
  current_path=new File(Root_path);
  //System.out.println("=============="+current_path);
  if(!current_path.isDirectory())
  {
  %>
  
  <br/>
  没有课件文件目录！请与管理员联系。<br/>
  <br/>

 <%
  return;
}
	
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
  	//根目录
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
  	//System.out.println("1aaa:"+current_path);
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
  s_current_path = (String)session.getValue("s_current_path");
  s_current_path = file_work.changeseperator(s_current_path);
  String flag = request.getParameter("flag");

  //out.print(s_current_path+"<br/>");
  //out.print(Root_path+"<br/>");
  //out.print(s_current_path.indexOf(Root_path));
  
  //判断当前目录是否在根目录下
  //System.out.println(s_current_path);
  //System.out.println(Root_path);
  if(s_current_path.indexOf(Root_path)<0 && flag!=null)
  {
  %>
  	<script language=javascript>
  
  		alert("当前目录已是根目录");
  		window.location.href='/entity/manager/course/courseware/filemanager/filemanager_main02.jsp?courseware_id='+'<%=session.getAttribute("courseware_id")%>';
 // 		window.location.href='/entity/manager/course/courseware/filemanager/filemanager_main02.jsp?courseware_id='+<%=session.getAttribute("courseware_id")%>;
//  		history.back();
  	</script>
  <%
  	s_current_path = session.getValue("s_current_path")+File.separator+session.getAttribute("courseware_id");
  	session.putValue("s_current_path",s_current_path.substring(0,s_current_path.length()));
  	 //System.out.println("最后："+session.getValue("s_current_path")); 
  	return;
  }
  
 

  String file_list[]=current_path.list();
  //System.out.println("2aaa:"+file_list);
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
   
   session.putValue("dirtable",dirtable);
   session.putValue("filetable",filetable);
   
  %>

<html>
<head>
<title>课件文件管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<LINK href="../css/style.css" type=text/css rel=stylesheet>
<link href="../../../images/css.css" rel="stylesheet" type="text/css">


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
<br/>
<script language=javascript>
//nogoingback();
</script>
<center>
  <TABLE border=0 cellSpacing=1 cellPadding=2 width="85%" bgColor=#999999 align=center>
	<tbody>
	<TR class=maindownmenu>
      <TD style="COLOR: white" height=20 width="100%">课件文件管理</TD>
    </TR>   
    <tr bgcolor=#F8F8F8>
      <td width=100% height=20>
      <!--main--><font class=Verdana8>
  <%
   String s_current_path1 = (String)session.getValue("s_current_path");    
   //System.out.println("s_current_path1->"+s_current_path1);
	int index1 = s_current_path1.indexOf("CourseImports");
//	System.out.println("index1"+index1);
//	String s_current_path2 = s_current_path1.substring(index1-1); System.out.println("s_current_path2->"+s_current_path2);
//  String s_current_path3 = "/"+s_current_path2;
//  out.print("&nbsp;&nbsp; 当前目录:"+"http://"+domain+file_work.changeseperator(((String)session.getValue("s_current_path")).substring(38))); 
  	
  //	out.print("&nbsp;&nbsp; 当前目录:"+"http://"+rootDir+file_work.changeseperator((s_current_path3))); 
  	out.print("&nbsp;&nbsp; 当前目录:"+s_current_path); 
//  String strCurPath="http://"+domain+file_work.changeseperator(((String)session.getValue("s_current_path")).substring(38));
//	String strCurPath="http://"+rootDir+file_work.changeseperator((s_current_path3));
  %>
       </font></td>
     </tr>
  <TR bgColor=#f8f8f8>

    <TD width="100%">
  <%//创建新目录:%>
  
  <table cellspacing=0 cellpadding=0 border=0>
         <FORM method=post action=new_dir.jsp>
            <TBODY>
              <TR height=25>
                <TD>&nbsp; 创建新目录:</TD>
                <TD><INPUT class=smart name=newdir>
                  <INPUT class=smart value=创建 type=submit></TD>
              </TR>
          </FORM>

  <%//上载文件%>
  <FORM encType=multipart/form-data method=post action=upload_info1.jsp>
            <TR height=25>
              <TD>&nbsp; 上载文件:</TD>
             	<input type="hidden" name="courseware_code" value="<%=courseware_code %>"/>
              <TD><INPUT class=smart name=coursezipfile type=file>
                <INPUT class=smart value=上载 type=submit></TD>
              <!-- <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="点此以Ftp方式上传课件" onclick="ftpupdate()"></td>
  	 --></TR>
          </FORM>
        </TABLE>
 <TABLE border=0 cellSpacing=1 cellPadding=1 width="100%"   >

<FORM name=encoder action=encode.jsp>
	 <TBODY>	
    <TR  >
   <td align=center>类型</td>
   <td align=center>名称</td>
   <td align=center>大小</td> 
   <td align=center>修改时间</td>
   <td align=center>修改</td>
   <td align=center>删除</td>
 <!--
   <td align=center><a href="javascript:formsubmit()"><font color="white" face="arial,helvetica">加密解密</font></a></td>
   -->
   </tr>
     <%System.out.println(((String)session.getValue("s_current_path")).compareTo(Root_path)!=0);
     if(((String)session.getValue("s_current_path")).compareTo(Root_path)!=0)
   {%>
    <TR  >
            <td nobreak>
                <a href=filemanager_main02.jsp?dir=..&flag=1><font size="-1" face"arial,helvetica">上一级目录</font></a>
            </td>
            <td align="right" nobreak><font class=Verdana8> </font></td>
                <td align="right" nobreak><font class=Verdana8> </font></td>  
          </TR>
    <%}%>
    <%for(int k = 0; k < vector_dir.size(); k++)
     {
     	String dirlist = (String)vector_dir.elementAt(k);
     	String dir_size=file_work.getSize((String)session.getValue("s_current_path"),dirlist);
     	String dir_modified=file_work.getModified((String)session.getValue("s_current_path"),dirlist);
   %>
          
            <tr>
                <td align="center" nobreak><a href=filemanager_main02.jsp?dir=<%=dirlist%>><img border=0 src="dir_dir.gif" alt="Change directory to <%=dirlist%>" border="0"></a></td>
                <td nobreak><a href=filemanager_main02.jsp?dir=<%=dirlist%>><font class=Verdana8><%=dirlist%></font></a></td>
                <td align="center" nobreak><font class=Verdana8><%=dir_size%></font></td>
                <td align="center" nobreak><font class=Verdana8><%=dir_modified%></font></td>
                <td> </td>
                <td align="center" nobreak>
                	<input type="hidden" value="<%=dirlist%>" id="hd_dir">
                	<a href="#" onclick="isDelDir('<%=dirlist%>');"><img border=1 src="delete.gif" alt="删除文件夹<%=dirlist%>"> </a>
                	<!-- <a href="delete_dir.jsp?dir=<%=dirlist%>"><img border=1 src="delete.gif" alt="删除文件夹<%=dirlist%>"> </a> -->
                </td>
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
                	<% if (false){ %>
                <!--  	<a href="*" target="_new"><font class=Verdana8><%=filelist%></font></a>-->
                	<% }else{ %>
                	<font class=Verdana8><%=filelist%></font>
                	<% } %>
                </td>
                <td align="center" nobreak><font class=Verdana8><%=file_size%></font></td>
                <td align="center" nobreak><font class=Verdana8><%=file_modified%></font></td>
                <td align="right" nobreak>            
                <% if(file_type.compareTo("zip")==0)
                  {
                  %><a href="unzip.jsp?file=<%=filelist%>"><img border=0 src="exit.gif" width="16" height="16" alt="解开文件夹"></a>
                  <%}%>
                  
                 
                 <% if (file_type.equals("zip")){ %>
                  <a href="unzip.jsp?file=<%=filelist%>">解开文件</a>
                 <% } %> 
                </td>
                <td align="center" nobreak>
                	<% if (true){ %>
                	<input type="hidden" value="<%=filelist%>" id="hd_name">
                	<a href="#" onclick="isDel('<%=filelist%>');"><img border=1 src="delete.gif" alt="删除文件<%=filelist%>"> </a>
                	<!-- <a href="delete_file.jsp?file=<%=filelist%>"><img border=1 src="delete.gif" alt="删除文件<%=filelist%>"> </a> -->
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
      </form>
    </table>
    <% 
    	if(courseware_code == null || courseware_code.equals("")){
    		courseware_code = session.getAttribute("courseware_id").toString();
    	}
    	String sql = "select OPERATE_USERNAME,to_char(OPERATE_DATE,'yyyy-mm-dd hh24:mi:ss'),OPERATE_TYPE,OPERATE_FILENAME from SCIRM_COURSE_CZ where COURSEWARE_CODE = '" + courseware_code + "' ORDER BY OPERATE_DATE DESC"; 
    	List list = service.getBySQL(sql);
    %>
	<TABLE border=0 cellSpacing=1 cellPadding=1 width="100%"   >

              <TR  >
                <td align=center> 操作人 </td>
                <td align=center> 操作时间 </td>
                <td align=center> 操作类型</td>
                <td align=center>操作对象</td>
              </TR>
              <%for(int i = 0; i < list.size(); i++) {%>
              <TR >
              <% String type = "";
              	 String  value = list.get(i) == null ?"":((Object[])list.get(i))[2].toString();
              	if(value.equals("1")){
              		type = "新增";
              	}else if(value.equals("2")){
              		type = "修改";
              	}else if(value.equals("3")){
              		type = "删除";
              	}              	
               %>
               <td align=center> <%=((Object[])list.get(i))[0] %></td>
               <td align=center> <%=((Object[])list.get(i))[1] %></td>
               <td align=center> <%=type %></td>
               <td align=center> <%=((Object[])list.get(i))[3] %></td>
              </TR>
              <%} %>
        </TABLE>
  </td>
  </tr>
  </tbody>
  
 </table>         
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

function isDel(val) {
	//var val = document.getElementById('hd_name').value;
	if(confirm('确认删除文件'+val+'吗？')) {
		window.location.href = 'delete_file.jsp?file='+val;
	}
}
function isDelDir(val) {
	//var val = document.getElementById('hd_dir').value;
	if(confirm('确认删除文件夹'+val+'吗？')) {
		window.location.href = 'delete_dir.jsp?dir='+val;
	}
}
</script> 

</body>
</html>
