<%
	/*---------------------------------------------
	��������:�޸ĸ��ļ���
	��дʱ��:2004-8-28
	��д��:�½�
	email:�½�@whaty.com
	
	�޸������¼
	�޸�ʱ�䣺  �޸����ݣ�   �޸��ˣ�
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

//�жϸ��ļ��Ƿ����
if(!thisfile.exists())
{
	out.print("�ļ�"+filename+"������!!!");
	return;
}

s_all=Memo;

FileOutputStream fileout=new FileOutputStream(thisfile);
PrintStream filewrite=new PrintStream(fileout);
filewrite.print(s_all);
filewrite.close();
fileout.close();
%>
�ɹ����档
<a href="./filemanager_main.jsp?dir=."><font color="blue">�����ļ��б�</font></a>


