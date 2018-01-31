<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.onlinetest.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="0" topmargin="0"  background="images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<%
	try {
		String testCourseId = request.getParameter("testCourseId");
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		OnlineTestCourse testCourse = testManage.getOnlineTestCourse(testCourseId);
		String title = testCourse.getTitle();
		String note = testCourse.getNote();
		if(note==null||note.equals("")){
			note="暂无说明";
		}
		String startDate = testCourse.getStartDate();
		String endDate = testCourse.getEndDate();
		String status = testCourse.getStatus();
		String isAutoCheck = testCourse.getIsAutoCheck();
		String isHiddenAnswer = testCourse.getIsHiddenAnswer();
		String selected = "";
		if(us.getRoleId().equals("0")) {
			String sql = "select to_char(pb.start_time,'yyyy-mm-dd') as start_time,to_char(pb.end_time,'yyyy-mm-dd') as end_time from " + 
			"pe_bzz_student ps inner join pe_bzz_batch pb on ps.fk_batch_id=pb.id and ps.reg_no='"+us.getLoginId()+"'";
			dbpool db = new dbpool();
			MyResultSet rs = db.executeQuery(sql);
			if(rs!=null&&rs.next()) {
				startDate = rs.getString("start_time");
				endDate = rs.getString("end_time");
			}
			db.close(rs);
		}
		
%>
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="images/top_01.gif"><img src="../images/khcy.jpg" width="217" height="86"></td>
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
                            <td width="160" valign="top" class="text3" style="padding-top:25px">详细信息</td>
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
				                          <td width="160" class="text3" valign="top">标题:</td>
		                                  <td width="400" valign="top" class="text1"  align="left" ><%=title%></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr style="display:none">
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" class="text3">开始时间:</td>
		                                  <td width="400" class="text1"  align="left" ><%=startDate%></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr style="display:none">
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" class="text3">结束时间:</td>
		                                  <td width="400" class="text1"  align="left" ><%=endDate%></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" align="left" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" align="left" class="text3" valign="top">说明:</td>
		                                  <td width="400" align="left" valign="top" class="text1" style="word-wrap: break-word; word-break: normal;"> <%=note%></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<!-- tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" class="text3" valign="top">客观题是否自动判卷:</td>
		                                  <td valign="top">
		                                  	<%
		                                  	if(isAutoCheck.equals("1")) 
		                                  			out.print("是");
		                                  	else 
		                                  			out.print("否");
		                                  	
		                                  	%>
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
				                          <td width="160" class="text3" valign="top">答题完毕后是否显示答案:</td>
		                                  <td valign="top">
		                                  	<%
		                                  	if(isHiddenAnswer.equals("1")) 
		                                  			out.print("是");
		                                  	else 
		                                  			out.print("否");
		                                  	
		                                  	%>
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
		                                  <td valign="top">
		                                  	<%
		                                  	if(status.equals("1")) 
		                                  			out.print("是");
		                                  	else 
		                                  			out.print("否");
		                                  	
		                                  	%>
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr-->
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
                      <td align="center"><a href="#" class="tj" onclick="javascript:window.close()">[关闭]</a> </td>
                    </tr>
                  </table></td>
                    </tr>
                  </table> <br/>
                </td>
              </tr>

            </table></td>
        </tr>
      </table>
<%
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
</body>
</html>
