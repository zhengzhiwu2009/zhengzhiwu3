<%@ page language="java" import="java.util.*"
	contentType="text/xml;charset=gbk"%>
<%@page import="com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page" />
<jsp:directive.page import="java.io.PrintWriter" />
<jsp:directive.page
	import="com.whaty.platform.sso.web.action.SsoConstant" />
<%@page
	import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>

<%!
	//判断字符串为空的话，赋值为"0"
	String fixnullNum(String str){
    	if(str == null || str.equalsIgnoreCase("null"))str = "0";
		return str.trim();
	}

%>

<%
	String sql = "";
	String sql_temp = "";
	dbpool work = new dbpool();
	MyResultSet rs ;
	
	
	String[][] x_date = new String[10][2];
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
	
	Date current_date = cal.getTime();
	long current_date_times = current_date.getTime();
	
	for(int i=0;i<x_date.length;i++){
		Date date_temp = new Date(current_date_times - ( 100*60*60*24*10*(9-i) ));
		x_date[i][0] = format.format(date_temp);
	}
	
	for(int i=0;i<x_date.length;i++){
		sql = " select max_num  from stat_max_onlinenum where stat_date = to_date('"+x_date[i][0]+"','yyyy-MM-dd') ";
		rs = work.executeQuery(sql);
		if(rs!=null && rs.next()){
			x_date[i][1] = fixnullNum(rs.getString("max_num"));
		}
		work.close(rs);
	}
	//System.out.println("1111111111111111>>>>>>>>>>>>>>>:"+sql);
	
	sql = " <chart  xAxisName='时间（时）' yAxisName='Number'   showValues='0' alternateHGridColor='FCB541' alternateHGridAlpha='20' divLineColor='FCB541' "+
		" divLineAlpha='50' canvasBorderColor='666666' baseFontColor='666666' lineColor='FCB541'> ";
	for(int i=0;i<x_date.length;i++){
		sql_temp = sql_temp + " <set label='"+x_date[i][0]+"' value='"+x_date[i][1]+"' /> ";
	}
	sql = sql + sql_temp + " </chart> ";
	
	response.setContentType("text/xml");
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
	PrintWriter w = response.getWriter();
	w.write(sql);
	w.close();
              
%>
