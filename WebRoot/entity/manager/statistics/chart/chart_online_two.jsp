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
<%@page import="java.text.DecimalFormat"%>

<%!
	//判断字符串为空的话，赋值为"0"
	String fixnullNum(String str){
    	if(str == null || str.equalsIgnoreCase("null"))str = "0";
		return str.trim();
	}
	
	String getIncrement(String str1,String str2){
		DecimalFormat df = new DecimalFormat("##0.0");
		
		str1 = fixnullNum(str1);
		str2 = fixnullNum(str2);
		Double double1 = Double.parseDouble(str1);
		Double double2 = Double.parseDouble(str2);
		
		return df.format(double2 - double1);
	}
	
%>
 
<%
	String sql = "";
	String sql_temp = "";
	dbpool work = new dbpool();
	MyResultSet rs ;
	
	
	String[][] x_date = new String[11][3];
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
	
	Date current_date = cal.getTime();
	long current_date_times = current_date.getTime();
	
	for(int i=0;i<x_date.length;i++){
		Date date_temp = new Date(current_date_times - ( 100*60*60*24*10*(10-i) ));
		x_date[i][0] = format.format(date_temp);
	}
	
	for(int i=0;i<x_date.length;i++){
		sql = " select total_time  from stat_study_totaltime where stat_date = to_date('"+x_date[i][0]+"','yyyy-MM-dd') ";
		rs = work.executeQuery(sql);
		if(rs!=null && rs.next()){
			x_date[i][1] = fixnullNum(rs.getString("total_time"));
		}
		work.close(rs);
	}
	
	System.out.println("222222222222>>>>>>>>>>>>>>>:"+sql);
	
	for(int i=1;i<x_date.length;i++){
		x_date[i][2] = getIncrement( x_date[i-1][1] , x_date[i][1]);
	}
	
	sql = " <chart  xAxisName='时间（时）' yAxisName='Number'   showValues='0' alternateHGridColor='FCB541' alternateHGridAlpha='20' divLineColor='FCB541' "+
		" divLineAlpha='50' canvasBorderColor='666666' baseFontColor='666666' lineColor='FCB541'> ";
	for(int i=1;i<x_date.length;i++){
		sql_temp = sql_temp + " <set label='"+x_date[i][0]+"' value='"+x_date[i][2]+"' /> ";
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
