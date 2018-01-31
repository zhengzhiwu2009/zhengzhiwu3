<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String name = request.getParameter("stu_name");
String days = request.getParameter("days");

Calendar c = Calendar.getInstance(); 

c.set(Calendar.MINUTE, 0); 
c.set(Calendar.SECOND, 0); 
c.set(Calendar.MILLISECOND, 0); 

c.set(Calendar.YEAR, 2010); 
c.set(Calendar.MONTH, 12); 
c.set(Calendar.DAY_OF_MONTH, 18); 
long l1 = c.getTimeInMillis(); 



days = ((l1 - new Date().getTime()) / (1000 * 60 * 60 * 24)) + ""; 

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>提示</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

  </head>
  
  <body>
  <p><span style="font-size: 12pt; background: white; color: black; line-height: 150%; font-family: 'Courier New'"><%=name %></span><span style="font-size: 12pt; background: white; color: black; line-height: 150%; font-family: 宋体">：</span></p>
<div style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%">
  	<%
  		
  		double mtime=(Double)session.getAttribute("mtime");
  		double ttime=(Double)session.getAttribute("ttime");
  		double pct = mtime/ttime ;
  		
  		DecimalFormat df = new DecimalFormat("###.##");
  		String mtStr = df.format(mtime);
  		String ttStr = df.format(ttime);
  		
  	if(pct < 0.5) {%>
   <span style="font-size: 12pt; background: white; color: black; line-height: 150%; font-family: 宋体">您好！距离考试还有</span><b><span style="font-size: 12pt; background: white; color: black; line-height: 150%; font-family: 'Courier New'"><%=days %></span></b><span style="font-size: 12pt; background: white; color: black; line-height: 150%; font-family: 宋体">天，您已完成</span><b><span style="font-size: 12pt; background: white; color: black; line-height: 150%; font-family: 'Courier New'"><%=mtStr %></span></b><span style="font-size: 12pt; background: white; color: black; line-height: 150%; font-family: 宋体">学时的课程学习，还有</span><b><span style="font-size: 12pt; background: white; color: black; line-height: 150%; font-family: 'Courier New'"><%=df.format(ttime-mtime) %></span></b><span style="font-size: 12pt; background: white; color: black; line-height: 150%; font-family: 宋体">学时的课程没有学习。请您加快学习进度，按计划完成必修课程学习、作业自测和课程评估，保证能够顺利参加考试。</span>
    <%} else if(pct >= 0.5 && pct < 1) {%>
    <span style="font-size: 12pt; background: white; color: black; line-height: 150%; font-family: 宋体">您好！距离考试还有</span><b><span style="font-size: 12pt; background: white; color: black; line-height: 150%; font-family: 'Courier New'"><%=days %></span></b><span style="font-size: 12pt; background: white; color: black; line-height: 150%; font-family: 宋体">天，您已完成</span><b><span style="font-size: 12pt; background: white; color: black; line-height: 150%; font-family: 'Courier New'"><%=mtStr %></span></b><span style="font-size: 12pt; background: white; color: black; line-height: 150%; font-family: 宋体">学时的课程学习，还有</span><b><span style="font-size: 12pt; background: white; color: black; line-height: 150%; font-family: 'Courier New'"><%=df.format(ttime-mtime) %></span></b><span style="font-size: 12pt; background: white; color: black; line-height: 150%; font-family: 宋体">学时的课程没有学习。请您继续按计划完成必修课程学习、作业自测和课程评估，保证能够顺利参加考试。为了更好地提高发展自己，在学有余力的情况下，您可以有选择地进行提升课程的学习。祝您学有所成！ </span>
    <%} else if(pct == 1) {%>
   <span style="font-size: 12pt; background: white; color: black; line-height: 150%; font-family: 宋体">您好！距离考试还有</span><b><span style="font-size: 12pt; background: white; color: black; line-height: 150%; font-family: 'Courier New'"><%=days %></span></b><span style="font-size: 12pt; background: white; color: black; line-height: 150%; font-family: 宋体">天，您已完成基础课程的学习，请您检查一下必修课程的作业自测和课程评估是否完成，保证能够顺利参加考试。为了更好地提高发展自己，您可以有选择地进行提升课程的学习。祝您学有所成！</span>
    <%}%>
    </div>
    <div style="text-justify: inter-ideograph; text-indent: 24pt; line-height: 150%"><span style="font-size: 12pt; background: white; color: black; line-height: 150%; font-family: 宋体">祝您 身体健康！工作顺利！</span></div>
  <br />
    <center><a href="#" onclick="window.close();">关闭</a></center>
  </body>
</html>
