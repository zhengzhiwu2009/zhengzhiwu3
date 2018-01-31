
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.Date"%>
<%@ include file="./pub/priv.jsp"%>

<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str){
	    if(str == null || str.equalsIgnoreCase("null")) str = "";
		return str.trim();
	}
%>
<script language="JavaScript" src="<%=request.getContextPath() %>/entity/manager/statistics/Data/FusionCharts.js"></script>

<html>
   <head>
      <title>每天学习总时长增长值</title>
   </head>
<link rel="stylesheet" type="text/css" media="all" href="../js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1">
<script type="text/javascript" src="../js/calendar/calendar.js"></script>
		<script type="text/javascript" src="../js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="../js/calendar/calendar-setup.js"></script>
		<script language="javascript" src="../js/calender.js"></script>	
</script>
<body>
<br/><br/>
<table width="98%" border="0" cellspacing="0" cellpadding="3" align="center">
 <tr>
 
 <td  class="text" align="center"><strong>每天学习总时长增长值</strong> &nbsp;  &nbsp; </td>
  </tr>
   </table>
   <br/>
<table width="98%" border="0" cellspacing="0" cellpadding="3" align="center">


  <tr> 
    <td  class="text" align="center"> <div id="chartdiv" align="center"> 
        </div>
     <script type="text/javascript">
		   var chart = new FusionCharts("<%=request.getContextPath() %>/entity/manager/statistics/fusioncharts/Line.swf", "ChartId", "600", "300", "0", "0");
		   var d = Math.random();
		   chart.setDataURL("<%=request.getContextPath() %>/entity/manager/statistics/chart/chart_online_two.jsp?d="+d);		   
		   chart.render("chartdiv");
		</script> </td>
  </tr>
  <tr>
    <td valign="top" class="text" align="center">&nbsp;</td>
  </tr>
 
  <tr>
    <td class="text" align="center"><a href="#" onclick="javascript:window.navigate('stat_onlineDateCount.jsp')">返回</a>
    <a href="/entity/manager/statistics/stat_onlinetwo_excel.jsp?d=<%=new Date().getTime()%>">导出</a></td></td>
  </tr>
</table>

</body>
</html>

