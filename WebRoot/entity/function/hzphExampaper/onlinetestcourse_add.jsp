<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加在线考试</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all"
	href="../../../js/calendar/calendar-win2000.css">
<script type="text/javascript" src="../../../js/calendar/calendar.js"></script>
<script type="text/javascript" src="../../../js/calendar/calendar-zh_utf8.js"></script>
<script type="text/javascript" src="../../../js/calendar/calendar-setup.js"></script>
<script language=javascript src="../js/check.js">
</script>
<script>

	function pageGuarding()	{
		//alert(document.courseForm.start_hour[document.courseForm.start_hour.selectedIndex].value);
		if(document.courseForm.title.value.replace(/[ ]/g,"")=="") {
			alert("请填写考试标题!");
			document.courseForm.title.focus();
			return false;
		}

		if(document.courseForm.note.value.replace(/[ ]/g,"")=="") {
			alert("请填写内容!");
			document.courseForm.note.focus();
			return false;
		}

		if(document.courseForm.start_date.value == "") {
			alert("请填写测验开始时间!");
			return false;
		}
		if(document.courseForm.end_date.value == "") {
			alert("请填写测验结束时间!");
			return false;
		}		
		//if(document.courseForm.note.value == "") {
		//	alert("请填写考试说明!");
		//	return false;
		//}	
		if(document.courseForm.batch_id.value == "") {
			alert("请选择所属学期!");
			return false;
		}	
		
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
		
		return true
	}
</script>
<%
String startDate="2012-02-09 ";
	String endDate="2112-02-09 ";
 %>
</head>

<body leftmargin="0" topmargin="0"  background="images/bg2.gif">
<form name="courseForm" action="onlinetestcourse_addexe.jsp" method="post" onsubmit="return pageGuarding()">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
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
                            <td width="100" valign="top" class="text3" style="padding-top:25px">添加考试</td>
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
				                          <td width="100" class="text3" valign="top"><font color=red>*</font>标题:</td>
				                          <td ><input name="title" type="text" size="30" maxlength="50"></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>                  		
                          		<tr style="visibility:hidden">
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="100" class="text3">开始时间:<font color=red>*</font></td>
		                                  <td class="text1">
		                                  <input type="text"
											class="input" name="start_date" id="start_date" value="<%=startDate %>" size="8" readonly> <img
											src="images/img.gif" width="20" height="14"
											id="f_trigger_b"
											style="border: 1px solid #551896; cursor: pointer;"
											title="Date selector"
											onmouseover="this.style.background='white';"
											onmouseout="this.style.background=''">&nbsp;
											  <select name="start_hour">
											  	<%
											  		for(int i=0; i<24; i++) {
											  			String str = String.valueOf(i);
											  			if(i < 10)
											  				str = "0" + str;
											  	%>
												<option value="<%=str%>"><%=str%></option>
												<%
													}
												%>
											  </select> 时
											  <select name="start_minute">
											  	<%
											  		for(int i=0; i<60; i++) {
											  			String str = String.valueOf(i);
											  			if(i < 10)
											  				str = "0" + str;
											  	%>
												<option value="<%=str%>"><%=str%></option>
												<%
													}
												%>
											  </select> 分
											  <select name="start_second">
											  	<%
											  		for(int i=0; i<60; i++) {
											  			String str = String.valueOf(i);
											  			if(i < 10)
											  				str = "0" + str;
											  	%>
												<option value="<%=str%>"><%=str%></option>
												<%
													}
												%>
											  </select> 秒
										  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr style="visibility:hidden">
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="100" class="text3">结束时间:<font color=red>*</font></td>
		                                  <td class="text1">
		                                  <input type="text"
											class="input" name="end_date" id="end_date" value="<%=endDate %>" size="8" readonly> <img
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
											  	%>
												<option value="<%=str%>"><%=str%></option>
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
											  	%>
												<option value="<%=str%>"><%=str%></option>
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
											  	%>
												<option value="<%=str%>"><%=str%></option>
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
				                          <td width="160" class="text3" valign="top"><font color="red">*</font>说明:</td>
		                                  <td ><textarea name="note" rows="10" cols="50" onfocus="javascript:if(this.value == '略...') this.value='';" type="_moz">略...</textarea></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0"  style="visibility: hidden">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" class="text3" valign="top">客观题是否自动判卷:</td>
		                                  <td valign="top" class="text1">
		                                  	<INPUT type="radio" name="isAutoCheck" value="1" checked>是&nbsp;&nbsp;&nbsp;
		                                  	<%-- <INPUT type="radio" name="isAutoCheck" value="0" >否 --%>
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0" style="visibility: hidden">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" class="text3" valign="top">答题完毕后是否显示答案:</td>
		                                  <td valign="top" class="text1">
		                                  	<%--<INPUT type="radio" name="isHiddenAnswer" value="1">是&nbsp;&nbsp;&nbsp;--%>
		                                  	<INPUT type="radio" name="isHiddenAnswer" value="0" checked>否
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
				                          <td width="160" class="text3" valign="top">是否激活:</td>
		                                  <td  class="text1">
		                                  	<INPUT type="radio" name="status" value="1">是&nbsp;&nbsp;&nbsp;
		                                  	<INPUT type="radio" name="status" value="0"  checked>否
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
				                          <td width="100" class="text3" valign="top">* 所属学期:</td>
		                                  <td valign="top">
		                                  	<select name="batch_id">
									    		  <option value="">请选择学期</option>
									    		  <%
									    		  String sql="select id,name from pe_bzz_batch order by recruit_selected desc";
									    		  dbpool db = new dbpool();
									    		  MyResultSet rs = db.executeQuery(sql);
									    		  while(rs!=null && rs.next())
									    		  {
									    			  String id=rs.getString("id");
									    			  String name=rs.getString("name");
									    			  %>
									    			  <option value="<%=id%>"><%=name %></option>
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
		                                  <td><input type="submit" value="添加"></td>
		                                   <td align="center"></td>		                                  
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
</body>
</html>
