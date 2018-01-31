<%@ page language="java" import="com.whaty.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    
    <title> </title>
	<meta http-equiv="expires" content="0">    
	<link href="./css/css.css" rel="stylesheet" type="text/css">
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
  </head>
  
  <body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">
  
   <div id="main_content">
    <div class="content_title">学习相关统计</div>
    <div class="cntent_k">
   	  <div class="k_cc">
<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
                          
                          <tr>
                            <td height="26" align="center" valign="middle" colspan="2">请选择导出项目:</td>
                          </tr>
                          <tr>
                          </tr>
						 
                          <tr valign="middle"> 
                             <td width="25" height="30" align="center"><span>&nbsp;</span></td>
                             <td width="500" height="30" align="center"><span class="name"><a href="/entity/manager/statistics/stat_study_batch_select.jsp?tagEnt=1">一级企业学习状态</a></span></td>
                          </tr>
                          <tr valign="middle"> 
                             <td width="25" height="30" align="center"><span>&nbsp;</span></td>
                             <td width="500" height="30" align="center"><span class="name"><a href="/entity/manager/statistics/stat_study_batch_select.jsp?tagEnt=2">二级企业学习状态</a></span></td>
                          </tr>
                          <tr valign="middle"> 
                             <td width="25" height="30" align="center"><span>&nbsp;</span></td>
                             <td width="500" height="30" align="center"><span class="name"><a href="/entity/manager/statistics/stat_study_batch_select.jsp?tagEnt=3">课程评估结果</a></span></td>
                          </tr>
                            <tr valign="middle"> 
                             <td width="25" height="30" align="center"><span>&nbsp;</span></td>
                             <td width="500" height="30" align="center"><span class="name"><a href="/entity/manager/statistics/stat_study_batch_select.jsp?tagEnt=7">课程评估结果(新)</a></span></td>
                          </tr>
                          <tr valign="middle"> 
                             <td width="25" height="30" align="center"><span>&nbsp;</span></td>
                             <td width="500" height="30" align="center"><span class="name"><a href="/entity/manager/statistics/stat_study_batch_select.jsp?tagEnt=4">作业、自测总体状况</a></span></td>
                          </tr>
                          <tr valign="middle"> 
                             <td width="25" height="30" align="center"><span>&nbsp;</span></td>
                             <td width="500" height="30" align="center"><span class="name"><a href="/entity/manager/statistics/stat_study_batch_select.jsp?tagEnt=5">目前已学总学时在某时段内学生列表</a></span></td>
                          </tr>
                          <tr valign="middle"> 
                             <td width="25" height="30" align="center"><span>&nbsp;</span></td>
                             <td width="500" height="30" align="center"><span class="name"><a href="/entity/manager/statistics/stat_course_question_excel.jsp?tagEnt=6">课程答疑情况统计</a></span></td>
                          </tr>
						   <tr>
                            <td  height="10"> </td>
                          </tr>
        </table>

	  </div>
    </div>
</div>
<div class="clear"></div>
  </body>
</html>
