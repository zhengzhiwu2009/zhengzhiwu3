<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.onlinetest.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加课后测验</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all"
	href="../../../js/calendar/calendar-win2000.css">
<script type="text/javascript" src="../../../js/calendar/calendar.js"></script>
<script type="text/javascript" src="../../../js/calendar/calendar-zh_utf8.js"></script>
<script type="text/javascript" src="../../../js/calendar/calendar-setup.js"></script>
<script language=javascript src="../js/check.js">
</script>
<script>

	function userAddGuarding()
	{	
		
		var startArr = document.courseForm.start_date.value.split("-");
		var endArr = document.courseForm.end_date.value.split("-");
		var startHour = document.courseForm.start_hour[document.courseForm.start_hour.selectedIndex].value;
		var startMinute = document.courseForm.start_minute[document.courseForm.start_minute.selectedIndex].value;
		var startSecond = document.courseForm.start_second[document.courseForm.start_second.selectedIndex].value;
		var endHour = document.courseForm.end_hour[document.courseForm.end_hour.selectedIndex].value;
		var endMinute = document.courseForm.end_minute[document.courseForm.end_minute.selectedIndex].value;
		var endSecond = document.courseForm.end_second[document.courseForm.end_second.selectedIndex].value;
		var startDate = new Date(startArr[0],startArr[1],startArr[2],startHour,startMinute,startSecond);
		var endDate = new Date(endArr[0],endArr[1],endArr[2],endHour,endMinute,endSecond);
		//alert("开始时间：" + startDate + "<br/>结束时间：" + endDate);
		if(startDate > endDate) {
			alert("开始时间不能比结束时间晚！");
			return false;
		}
		
		return true;
	}
	
</script>
</head>

<body leftmargin="0" topmargin="0"  background="images/bg2.gif">
<form name="courseForm" action="onlinetestcourse_editexe.jsp" method="post" onsubmit="return userAddGuarding();">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<%!
String fixnull(String str)
{
    if(str == null || str.equals("") || str.equals("null"))
		str = "";
	return str;
}
%>	
<%
	try {
		String testCourseId = request.getParameter("testCourseId");
		String pageInt = request.getParameter("pageInt");
		String title1 = request.getParameter("title");
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		OnlineTestCourse testCourse = testManage.getOnlineTestCourse(testCourseId);
		String title = testCourse.getTitle();
		String note = testCourse.getNote();
	//	String startDate = testCourse.getStartDate();
		String startDate="2012-02-09 00:00:00";
		String start_date = startDate.substring(0,10);
		String start_hour = startDate.substring(11,13);
		String start_minute = startDate.substring(14,16);
		String start_second = startDate.substring(17,19);
		String endDate ="2198-02-09 00:00:00";
		String end_date = endDate.substring(0,10);
		String end_hour = endDate.substring(11,13);
		String end_minute = endDate.substring(14,16);
		String end_second = endDate.substring(17,19);
		String status = testCourse.getStatus();
		String isAutoCheck = testCourse.getIsAutoCheck();
		String isHiddenAnswer = testCourse.getIsHiddenAnswer();
		String batch_id=testCourse.getBatch_id();
		String selected = "";
		
		//UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
%>
<input type="hidden" name="testCourseId" value="<%=testCourseId%>">
<input type="hidden" name="pageInt" value="<%=pageInt%>">
<input type="hidden" name="title1" value="<%=title1%>">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="images/top_01.gif"><img src="images/khcy.jpg" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="608" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
                    <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td>
                      	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="78"><img src="images/wt_02.gif" width="78" height="52"></td>
                             <%
							if("teacher".equalsIgnoreCase(userType)&&us.getRoleId().equals("3"))
							{
							%>	
                            <td width="100" valign="top" class="text3" style="padding-top:25px">编辑测验</td>
                            <%}
                               else { %>
                                <td width="100" valign="top" class="text3" style="padding-top:25px">查看测验</td>
                                <%} %>
                            <td background="images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td><img src="images/wt_01.gif" width="605" height="11"></td>
                    </tr>
                  	<tr>
                  		<td><img src="images/wt_04.gif" width="604" height="13"></td>
                    </tr>
                    
                    <tr>
                    	<td background="images/wt_05.gif">
                    	<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                        	<tr>
                            	<td><img src="images/wt_08.gif" width="572" height="11"></td>
                          	</tr>
                          	<tr>
                            	<td background="images/wt_10.gif">
                            	
                            	<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="85" class="text3" valign="top">标题:</td>
		                                  <td valign="top"><input name="title" type="text" size="30" maxlength="50" value=<%=title%>></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          	
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0" style="visibility:hidden">
		                                <tr>
		                                  <td width="20"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="100" class="text3">开始时间:</td>
		                                  <td class="text1">
		                                  <input type="text"
											class="input" name="start_date" id="start_date" value="<%=start_date%>" size="8" readonly> <img
											src="images/img.gif" width="20" height="14"
											id="f_trigger_b"
											style="border: 1px solid #551896; cursor: pointer;"
											title="Date selector"
											onmouseover="this.style.background='white';"
											onmouseout="this.style.background=''">&nbsp;
											  <select name="start_hour">
									
										
												<option value="<%=start_hour%>" <%=selected%>><%=start_hour%></option>
											
											  </select> 时
											  <select name="start_minute">
										
												<option value="<%=start_minute%>" <%=selected%>><%=start_minute%></option>
											
											  </select> 分
											  <select name="start_second">
											  
												<option value="<%=start_second%>" <%=selected%>><%=start_second%></option>
												
											  </select> 秒
										  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0" style="visibility:hidden">
		                                <tr>
		                                  <td width="20"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="100" class="text3">结束时间:</td>
		                                  <td class="text1">
		                                  <input type="text"
											class="input" name="end_date" id="end_date" value="<%=end_date%>" size="8" readonly> <img
											src="images/img.gif" width="20" height="14"
											id="f_trigger_c"
											style="border: 1px solid #551896; cursor: pointer;"
											title="Date selector"
											onmouseover="this.style.background='white';"
											onmouseout="this.style.background=''">&nbsp;
											  <select name="end_hour">
											  	<%
											  		for(int i=0; i<24; i++) {
											  			String str = String.valueOf(i);
											  			if(i < 10)
											  				str = "0" + str;
											  			if(str.equals(end_hour))
											  				selected = "selected";
											  			else
											  				selected = "";
											  	%>
												<option value="<%=str%>" <%=selected%>><%=str%></option>
												<%
													}
												%>
											  </select> 时
											  <select name="end_minute">
											  	<%
											  		for(int i=0; i<60; i++) {
											  			String str = String.valueOf(i);
											  			if(i < 10)
											  				str = "0" + str;
											  			if(str.equals(end_minute))
											  				selected = "selected";
											  			else
											  				selected = "";
											  	%>
												<option value="<%=str%>" <%=selected%>><%=str%></option>
												<%
													}
												%>
											  </select> 分
											  <select name="end_second">
											  	<%
											  		for(int i=0; i<60; i++) {
											  			String str = String.valueOf(i);
											  			if(i < 10)
											  				str = "0" + str;
											  			if(str.equals(end_second))
											  				selected = "selected";
											  			else
											  				selected = "";
											  	%>
												<option value="<%=str%>" <%=selected%>><%=str%></option>
												<%
													}
												%>
											  </select> 秒
										  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          	
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="100" class="text3" valign="top">说明:</td>
		                                  <td valign="top"><textarea name="note" rows="10" cols="49"><%=fixnull(note)%></textarea></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0"  style="visibility: hidden;">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="180" class="text3" valign="top">客观题是否自动判卷:</td>
		                                  <td valign="top" class="text1">
		                                  	<INPUT type="radio" name="isAutoCheck" value="1" <%if(isAutoCheck.equals("1")) out.print("checked");%>>是&nbsp;&nbsp;&nbsp;
		                                  	 <INPUT type="radio" name="isAutoCheck" value="0" <%if(isAutoCheck.equals("0")) out.print("checked");%>>否 
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0" style="visibility: hidden;">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="180" class="text3" valign="top">答题完毕后是否显示答案:</td>
		                                  <td valign="top" class="text1">
		                                  	<INPUT type="radio" name="isHiddenAnswer" value="1" <%if(isHiddenAnswer.equals("1")) out.print("checked");%>>是&nbsp;&nbsp;&nbsp;
		                                  	<%-- <INPUT type="radio" name="isHiddenAnswer" value="0" <%if(isHiddenAnswer.equals("0")) out.print("checked");%>>否 --%>
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="100" class="text3" valign="top">是否激活:</td>
		                                  <td valign="top" class="text1">
		                                  	<INPUT type="radio" name="status" value="1" <%if(status.equals("1")) out.print("checked");%>>是&nbsp;&nbsp;&nbsp;
		                                  	<INPUT type="radio" name="status" value="0" <%if(status.equals("0")) out.print("checked");%>>否
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<!--<tr>
                          		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                 <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">*所属学期:</td>
		                                  <td class="text1" valign="top">
		                              	    <select name="batch_id">
									    		  <option value="">请选择学期</option>
									    		  <%
									    		  String sql="select id,name from pe_bzz_batch order by recruit_selected desc";
									    		  dbpool db = new dbpool();
									    		  MyResultSet rs = db.executeQuery(sql);
									    		  while(rs!=null && rs.next())
									    		  {
									    			  String batchId=rs.getString("id");
									    			  String batch_name=rs.getString("name");
									    			  %>
									    			  <option value="<%=batchId%>" <%if(batch_id.equals(batchId))out.print("selected");%> ><%=batch_name %></option>
									    			  <%
									    		  }
									    		  db.close(rs);
									    		  %>
									        </select>
           									</td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		--><tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"></td>
				                          <td width="180" class="text3"></td>
				                           <%
							if("teacher".equalsIgnoreCase(userType)&& us.getRoleId().equals("3"))
							{
							%>	
		                                  <td><input type="submit" value="提交"></td>	
		                                  <%} %>	                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                              	</table>
                              	</td>
                          	</tr>
                          <tr>
                            <td><img src="images/wt_09.gif" width="572" height="11"></td>
                          </tr>
                        </table>
                        </td>
                    </tr>
                    <tr>                            
                      <td><img src="images/wt_06.gif" width="604" height="11"></td>
                    </tr>
                    <tr>
                      <td align="center"><a href="onlinetestcourse_list.jsp" class="tj">[返回]</a> </td>
                    </tr>
                  </table></td>
                    </tr>
                  </table> <br/>
                </td>
              </tr>

            </table></td>
        </tr>
      </table>
</form>
<script type="text/javascript">

    Calendar.setup({
        inputField     :    "start_date",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_b",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
    
    Calendar.setup({
        inputField     :    "end_date",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_c",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
   
</script>
<%
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
</body>
</html>
