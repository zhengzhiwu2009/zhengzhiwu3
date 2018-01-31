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
<%@page import="java.io.*,com.whaty.*"%>
<jsp:useBean id="parameter" scope="application" class="com.whaty.encrypt.parameter" />
<%
String GLOBAL_BASE_DIR=parameter.g_basedir;
String GLOBAL_DOMAIN=parameter.g_domain;
String GLOBAL_ROOT=parameter.g_root;
String GLOBAL_IP=parameter.g_ip;
%>

<%
byte fileall[]=new byte[50000]; 
int iresult;
String s_current_path=(String) session.getValue("s_current_path");
String filename=request.getParameter("file");
//filename=appbox.convertEncoding("ISO8859-1","GB2312",filename);
String filepath=s_current_path+"/"+filename;
/*
FileInputStream editfile=new FileInputStream(filepath);
iresult=editfile.read(fileall);
String filecontent=new String(fileall);
//out.print(filepath);
*/
FileInputStream isfile=new FileInputStream(filepath);
BufferedReader br = new BufferedReader(new InputStreamReader(isfile));
String filecontent="";
String s_temp="";
while((s_temp=br.readLine())!=null)
{
	filecontent=filecontent+s_temp+"\r\n";
}

%>

<html>
<head>
<meta HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8">
<title>Web多媒体编辑工具</title>
</head>
<body>
<script language=javascript>
	var bSubmit=false
	var bLoaded=false; 
	function chkSubmit(){
		if(currentflag==1)//处于普通编辑模式
		{
		frmAnnounce.body.value=document.frames.editor.frames.edit1.textEdit.document.body.innerHTML;
		}        
		else if (currentflag==3)//处于预浏览先通编辑模式
		{
		frmAnnounce.body.value=oDiv.innerHTML;
		}
		document.frmAnnounce.body.value=Absolute2Relative(document.frmAnnounce.body.value);//替换绝对路径
		if (bLoaded==false)
		{
			alert("表单正在下载")
			return false
		}
		if (bSubmit==false)
		{
			bSubmit=true;
			return true;
		}
		if(frmAnnounce.body.value.length <2)
		{
			alert("问题内容为空，还是多写几句吧？");
			return false;
		}else
		{
			return false;
		}
	}
</script>

<form action="save_file.jsp" method="POST" name="frmAnnounce" onsubmit="return chkSubmit()">
<input type="hidden" name="filename"  value="<%=filename%>">    
<textarea class="smallarea" cols="100" rows="30" name="body"><%=com.whaty.filemanager.encode.HtmlEncoder.encode(filecontent)%></textarea>
<script language=JavaScript src="<%=GLOBAL_ROOT%>/WhatyEditor/config.js"></script><br/>
<script language=JavaScript src="<%=GLOBAL_ROOT%>/WhatyEditor/edit.js"></script>
<br/>
  <table width="450" cellspacing="0" cellpadding="0" border=0>
  <tr>
    <td width="60%"><span class=smallFont>内 容</span></td>
    <td width="40%" align="right"><input class="buttonface" type="submit" value=" 保存修改 " id=submit1 name=submit1>
    <input type="button" value="返回" name="back" onclick="javascript:history.back()"></td>
  </tr>
</table>  
</form>
<br/>
<%//=filecontent%>
<script>
bLoaded=true;
</script>

</body>
</html>


