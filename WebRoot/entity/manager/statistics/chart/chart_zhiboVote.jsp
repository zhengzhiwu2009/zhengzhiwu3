<%@ page language="java" import="java.util.*"  contentType="text/xml;charset=gbk"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<jsp:directive.page import="java.io.PrintWriter"/>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>

<%!
	//�ж��ַ���Ϊ�յĻ�����ֵΪ""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "0";
			return str;
	}
%>
<%
	 String d=fixnull(request.getParameter("d"));
	   String batch_id="";
	   if(d.indexOf("|")>0)
	   		batch_id=fixnull(d.substring(0,d.indexOf("|")));
	   		
	UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	
	dbpool db = new dbpool();
	MyResultSet rs = null;
		

		String sql = "";
		sql = "select (select count(id) from zhibo_vote where result='1') as r1, \n"
	     + "  (select count(id) from zhibo_vote where result='2') as r2, \n"
	     + "  (select count(id) from zhibo_vote where result='3') as r3, \n"
	     + "  (select count(id) from zhibo_vote where result='4') as r4, \n"
	     + "  (select count(id) from zhibo_vote where result='5') as r5 from dual";
       // System.out.println(sql); 
        rs = db.executeQuery(sql);
		if(rs!=null&&rs.next())
		{
			String r1 = fixnull(rs.getString("r1"));
			String r2 = fixnull(rs.getString("r2"));
			String r3 = fixnull(rs.getString("r3"));
			String r4 = fixnull(rs.getString("r4"));
			String r5 = fixnull(rs.getString("r5"));
        
		StringBuffer sqltemp=new StringBuffer();
        String str ="<chart palette='8' baseFontSize='12'><set label='��Ƶʮ������' value='"+r1+"' /> <set label='����ʱ����΢��һЩ��\n����֮��ʮ��˳��' value='"+r2+"' /> <set label='ż����ͣ������' value='"+r3+"' /><set label='��������ͣ������' value='"+r4+"' /><set label='�����޷��ۿ���Ƶ' value='"+r5+"' /> </chart>";
		sqltemp.append(str);
		
		response.setContentType("text/xml");
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Pragma","no-cache");
		response.setDateHeader("Expires",0);
		PrintWriter w = response.getWriter();
		w.write(sqltemp.toString());
		w.close();
		}
		db.close(rs);
%>
