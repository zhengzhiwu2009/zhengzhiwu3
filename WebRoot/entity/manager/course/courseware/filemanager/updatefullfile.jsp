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
<%
byte fileall[]=new byte[50000]; 
int iresult;
String s_current_path=(String) session.getValue("s_current_path");
String filename=request.getParameter("file");
String filepath=s_current_path+"/"+filename;
FileInputStream editfile=new FileInputStream(filepath);
iresult=editfile.read(fileall);
String filecontent=new String(fileall);
//out.print(filepath);
%>

<html>
<head>
<meta HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="../css/FORUM.CSS">
<title>Web��ý��༭����</title>
</head>
<body>
<script language=javascript>
	var bSubmit=false
	var bLoaded=false; 
	function chkSubmit(){
		return true;
	}

</script>

<form action="save_fullfile.jsp" method="POST" name="frmAnnounce" onsubmit="return chkSubmit()">
<input type="hidden" name="filename"  value="<%=filename%>">    
<textarea class="smallarea" cols="100" rows="30" name="body"><%=filecontent%></textarea>
<br/>
  <table width="450" cellspacing="0" cellpadding="0" border=0>
  <tr>
    <td width="60%"><span class=smallFont>�� ��</span></td>
    <td width="40%" align="right"><input class="buttonface" type="submit" value=" �����޸� " id=submit1 name=submit1>
    <input type="button" value="����" name="back" onclick="javascript:history.back()"></td>
  </tr>
</table>  
</form>
<br/>

<script>bLoaded=true;</script>

</body>
</html>


