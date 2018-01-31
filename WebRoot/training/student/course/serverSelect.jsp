<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<%@page import="org.apache.commons.lang.StringUtils,java.util.regex.Pattern"%>
<%@page import="com.whaty.platform.database.oracle.MyResultSet"%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.io.*,java.util.*"%>


<%!
	public static boolean isNumeric(String str){
		if(str==null || str.equals(""))
			return false;
	     Pattern pattern = Pattern.compile("[0-9]*");
	     return pattern.matcher(str).matches();   
	}
	
	%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中国证劵业协会培训平台</title>
<link href="/entity/bzz-students/css1.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>

<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
	String paramServer = request.getParameter("server");
	if(paramServer != null && !paramServer.equals("")) {
		String paramStr = request.getQueryString();
		String url = "/training/student/course/jumpCourseware.jsp";
		//String url = "/courseserver/search_server.jsp";
		if(!paramServer.equals("defaultServer")) {
			
			session.setAttribute("SelectedServerAddr", paramServer);
		}
		%>
<script language="javascript">
	alert("选择服务器成功，您选择的服务器为：<%=paramServer%>");
	window.close();
</script>
		<%
		//response.sendRedirect(url + "?" + paramStr);
	}
	
	String paramString = request.getQueryString();
		if(paramString != null && paramString.length() > 0) {
			paramString = "?" + paramString + "&";
		} else {
			paramString = "?";
		}
	//System.out.println(paramString);
 %>

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {font-size: 14px; color: #0041A1; font-weight: bold; }
.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}
.kctx_zi1 {
	font-size: 14px;
	color: #0041A1;
}
.kctx_zi2 {
	font-size: 14px;
	color: #54575B;
	line-height: 24px;
	padding-top:2px;
}
-->
</style></head>

<body>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="13"></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
   <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17" align="left" class="twocentop">	<div class="content_title"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" />请选择服务器</div>
	</td>
      </tr>
      <tr>
      	<td>&nbsp;</td>
      </tr>
       <table width="75%" border="0" align="center" cellpadding="0" cellspacing="0" style="border:solid 2px #9FC3FF; padding:5px; margin:15px 0px 15px 0px;">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D7D7D7" height='100%'>
      <tr bgcolor="#ECECEC">
        <td align="left" bgcolor="#E9F4FF" class="kctx_zi1"> 自动选择服务器：</td>
      </tr>
      <tr>
    	<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"><a href="<%=basePath %>training/student/course/serverSelect.jsp<%=paramString %>server=defaultServer" >自动选择</a></td>
  	  </tr>
      <tr bgcolor="#ECECEC">
        <td align="left" bgcolor="#E9F4FF" class="kctx_zi1"> 网通服务器列表：</td>
        </tr>

<% 
		
		
		String path=request.getRealPath("/");
		path+="courseserver/";
		
		String minServer = "";
		List<String> serverList = new ArrayList<String>();
		long minValue = Long.MAX_VALUE;
		try {
		   FileReader read = new FileReader(path + "cnc.txt");
		   BufferedReader br = new BufferedReader(read);
		   String row;
		   while((row = br.readLine())!=null){
			   if(row!=null && !row.equals(""))
			   {
				   String[] ipArray=row.split("\\ ");
				   if(ipArray.length>1)
				   {
				   		String tmp_str=ipArray[1];
				   		if(!isNumeric(tmp_str))
				   		{
				   			continue;
				   		}
				   		else
				   		{
					   		long temp_num=Long.parseLong(tmp_str);
					   		//获取最小流量对应流媒体服务器
					   		
					   		if(temp_num<minValue)
					   		{
					   			minValue=temp_num;
					   			minServer=ipArray[0];
					   		}
					   		serverList.add(ipArray[0]);
				   		}
				   }
			   }
		   	}
		   	int index = 1;
		   	%>
		   	<tr>
    			<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
    			<%
    			for(String server : serverList) {
		   	%>
    			<a href="<%=basePath %>training/student/course/serverSelect.jsp<%=paramString %>server=<%= server%>" >网通<%=index %><% if(server.equals(minServer)){%> [<font color=red>推荐</font>]<%} %></a>&nbsp;&nbsp;&nbsp;&nbsp;
    				<%
		   	index ++;
		   	}
		   	%>
    			</td>
  			</tr>
		   	<%
		   	serverList = new ArrayList<String>();
		   	minValue = Long.MAX_VALUE;
		   	minServer = "";
		   	%>
		   	<tr bgcolor="#ECECEC">
        		<td align="left" bgcolor="#E9F4FF" class="kctx_zi1"> 电信服务器列表：</td>
        	</tr>
		   	<%
		   read = new FileReader(path + "tel.txt");
		   br = new BufferedReader(read);
		   while((row = br.readLine())!=null){
			   if(row!=null && !row.equals(""))
			   {
				   String[] ipArray=row.split("\\ ");
				   if(ipArray.length>1)
				   {
				   		String tmp_str=ipArray[1];
				   		if(!isNumeric(tmp_str))
				   		{
				   			continue;
				   		}
				   		else
				   		{
					   		long temp_num=Long.parseLong(tmp_str);
					   		//获取最小流量对应流媒体服务器
					   		
					   		if(temp_num<minValue)
					   		{
					   			minValue=temp_num;
					   			minServer=ipArray[0];
					   		}
					   		serverList.add(ipArray[0]);
				   		}
				   }
			   }
		   	}
		   	index = 1;
		   %>
		   	<tr>
    			<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
    			<%
    				for(String server : serverList) {
		   	%>
    			<a href="<%=basePath %>training/student/course/serverSelect.jsp<%=paramString %>server=<%= server%>" >电信<%=index %><% if(server.equals(minServer)){%> [<font color=red>推荐</font>]<%} %></a>&nbsp;&nbsp;&nbsp;&nbsp;
    			<%
    				index ++;
		   	}
    			 %>
    			</td>
  			</tr>
		   	<%
		   	serverList = new ArrayList<String>();
		   	minValue = Long.MAX_VALUE;
		   	minServer = "";
		   	%>
		   	<tr bgcolor="#ECECEC">
        		<td align="left" bgcolor="#E9F4FF" class="kctx_zi1"> 教育网服务器列表：</td>
        	</tr>
        	<%
		   read = new FileReader(path + "edu.txt");
		   br = new BufferedReader(read);
		   while((row = br.readLine())!=null){
			   if(row!=null && !row.equals(""))
			   {
				   String[] ipArray=row.split("\\ ");
				   if(ipArray.length>1)
				   {
				   		String tmp_str=ipArray[1];
				   		if(!isNumeric(tmp_str))
				   		{
				   			continue;
				   		}
				   		else
				   		{
					   		long temp_num=Long.parseLong(tmp_str);
					   		//获取最小流量对应流媒体服务器
					   		
					   		if(temp_num<minValue)
					   		{
					   			minValue=temp_num;
					   			minServer=ipArray[0];
					   		}
					   		serverList.add(ipArray[0]);
				   		}
				   }
			   }
		   	}
		   	index = 1;
		   	%>
		   	<tr>
    			<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
    			<%
    			for(String server : serverList) {
    			 %>
    			<a href="<%=basePath %>training/student/course/serverSelect.jsp<%=paramString %>server=<%= server%>" >教育网<%=index %><% if(server.equals(minServer) && serverList.size() > 1){%> [<font color=red>推荐</font>]<%} %></a>
    			<%
    			index ++;
		   		}
    			 %>
    			</td>
  			</tr>
  			
  			 	<%
		   	serverList = new ArrayList<String>();
		   	minValue = Long.MAX_VALUE;
		   	minServer = "";
		   	%>
		   	<tr bgcolor="#ECECEC">
        		<td align="left" bgcolor="#E9F4FF" class="kctx_zi1"> 移动服务器列表：</td>
        	</tr>
        	<%
		   read = new FileReader(path + "cmcc.txt");
		   br = new BufferedReader(read);
		   while((row = br.readLine())!=null){
			   if(row!=null && !row.equals(""))
			   {
				   String[] ipArray=row.split("\\ ");
				   if(ipArray.length>1)
				   {
				   		String tmp_str=ipArray[1];
				   		if(!isNumeric(tmp_str))
				   		{
				   			continue;
				   		}
				   		else
				   		{
					   		long temp_num=Long.parseLong(tmp_str);
					   		//获取最小流量对应流媒体服务器
					   		
					   		if(temp_num<minValue)
					   		{
					   			minValue=temp_num;
					   			minServer=ipArray[0];
					   		}
					   		serverList.add(ipArray[0]);
				   		}
				   }
			   }
		   	}
		   	index = 1;
		   	%>
		   	<tr>
    			<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
    			<%
    			for(String server : serverList) {
    			 %>
    			<a href="<%=basePath %>training/student/course/serverSelect.jsp<%=paramString %>server=<%= server%>" >移动网<%=index %><% if(server.equals(minServer) && serverList.size() > 1){%> [<font color=red>推荐</font>]<%} %></a>
    			<%
    			index ++;
		   		}
    			 %>
    			</td>
  			</tr>
		   	<%
	   }
	  catch (FileNotFoundException e) {
		   e.printStackTrace();
		   System.out.println("Error:"+e.toString());
	  } catch (IOException e){
	   e.printStackTrace();
	    System.out.println("Error1:"+e.toString());
	  }
%>
  	<tr bgcolor="#ECECEC">
        		<td align="left" bgcolor="#E9F4FF" class="kctx_zi1"> <font color=red>服务器选择说明：</font></td>
       </tr>
   	<tr>
    			<td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
1、不选择时，系统会默认根据您的网络类型选择合适的服务器。<br/>	    			
2、如果您不确定自己目前使用网络的类型，可以不选择，直接关闭当前页面，系统会默认给您分配相应的服务器。<br/>
3、如果您学习课件时，视频不流畅，请您尝试选择一下其它类型服务器进行尝试。<br/>
4、请先关闭正在学习的课件，再选择服务器，选择服务器后重新打开课件学习。<br/>
5、如果当前页面，除了自动选择，其它类型服务器列表均为空，请刷新当前页面。<br/>
6、该页面选择后会自动关闭
 			</td>
 			</tr>
    </table></td>
  </tr>
</table>
    </table></td>
    <td width="13">&nbsp;</td>
  </tr>
</table>
</td>
</tr>
</table>
</body>
</html>
</td></tr>
</table>
</body>
</html>	


