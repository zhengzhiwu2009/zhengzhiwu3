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
<%@ page contentType="text/html;charset=utf-8" %>
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

FileInputStream isfile=new FileInputStream(thisfile);
BufferedReader br = new BufferedReader(new InputStreamReader(isfile));
s_all="";
while((s_temp=br.readLine())!=null)
{
	s_all=s_all+s_temp+"\r\n";
}
//System.out.println("s_all="+s_all);
/*
DataInputStream isdata=new DataInputStream(isfile);

while((s_temp=isdata.readLine())!=null)
{
	s_all=s_all+s_temp+"\r\n";
}
isdata.close();*/
isfile.close();
br.close();
if(s_all.indexOf("<body")<0)
{
	s_bodypre="<html>\n";
	s_bodypre+="<head>\n";
	s_bodypre+="<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n";
	s_bodypre+="<link rel=\"stylesheet\" type=\"text/css\" href=\"../css/css.css\">\n";
	s_bodypre+="</head>\n";
	s_bodypre+="<body>\n";
}
else
{
	index=s_all.indexOf("<body");
	s_bodypre=s_all.substring(0,index);
	s_temp=s_all.substring(index,s_all.length());
	s_bodypre+=s_temp.substring(0,s_temp.indexOf(">")+1);
        if(s_bodypre.lastIndexOf("<")>s_bodypre.lastIndexOf(">")){
        s_bodypre+=">\n";
        } 
}

if(s_all.indexOf("</body")<0)
{
	s_bodyafter="</body></html>";
}
else
{
	index=s_all.indexOf("</body");
	s_bodyafter=s_all.substring(index);
}

s_all=s_bodypre+Memo+s_bodyafter;

FileOutputStream fileout=new FileOutputStream(thisfile);
PrintStream filewrite=new PrintStream(fileout);
filewrite.print(s_all);
filewrite.close();
fileout.close();

%>
成功保存。
<a href="./filemanager_main.jsp?dir=."><font color="blue">返回文件列表</font></a>

