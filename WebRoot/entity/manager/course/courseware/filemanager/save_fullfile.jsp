<%
	/*---------------------------------------------
	功能描述:修改稿文件安
	编写时间:2004-8-28
	编写人:陈健
	email:陈健@whaty.com
	
	修改情况记录
	修改时间：  修改内容：   修改人：
	-----------------------------------------------*/
%>
<%@ page contentType="text/html;charset=gb2312" %>
<%@page import="java.io.*"%>
<%@page import="java.lang.*"%>
<%
int index;
String s_all,s_bodypre,s_bodyafter,s_temp;
String filename=request.getParameter("filename");
String Memo = request.getParameter("body");
String s_current_path=(String)session.getValue("s_current_path");
File thisfile=new File(s_current_path,filename);

//判断该文件是否存在
if(!thisfile.exists())
{
	out.print("文件"+filename+"不存在!!!");
	return;
}

s_all=Memo;

FileOutputStream fileout=new FileOutputStream(thisfile);
PrintStream filewrite=new PrintStream(fileout);
filewrite.print(s_all);
filewrite.close();
fileout.close();
%>
成功保存。
<a href="./filemanager_main.jsp?dir=."><font color="blue">返回文件列表</font></a>


