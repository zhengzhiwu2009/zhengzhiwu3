<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>随堂练习</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script>
function cfmdel(link)
{
	if(confirm("您确定要删除此作业吗？"))
		window.navigate(link);
}
function DetailInfo(paperId)
{
	window.open('homeworkpaper_info.jsp?paperId='+paperId,'','dialogWidth=630px;dialogHeight=550px,scrollbars=yes');
}
</script>
</head>
<%!
String fixnull(String str)
{
    if(str == null || str.equals("") || str.equals("null"))
		str = "";
	return str;
}
%>
<%!
String fixnull1(String str)
{
    if(str == null || str.equals("") || str.equals("null"))
		str = "未知";
	return str;
}
%>	
<%

	String title = fixnull(request.getParameter("title"));
	session.removeAttribute("historyPaperId");
	
	dbpool db = new dbpool();
    String sql = "select to_char(b.start_time,'yyyy-mm-dd') as start_time,to_char(b.end_time,'yyyy-mm-dd') as end_time,b.id as batch_id from pe_bzz_student s inner join pe_bzz_batch b on s.fk_batch_id=b.id "
			+" where s.fk_sso_user_id='"+us.getSsoUser().getId()+"' ";
    MyResultSet rs = db.executeQuery(sql);
    String bStartTime = null;
    String bEndTime = null;
    String bBatchId="";
    if(rs.next()) {
	    bStartTime = fixnull(rs.getString("start_time"));
	    bEndTime = fixnull(rs.getString("end_time"));
	    bBatchId=fixnull(rs.getString("batch_id"));
    }
    db.close(rs);
    if(bBatchId.equals("ff8080812253f04f0122540a58000004")||bBatchId.equals("52cce2fd2471ddc30124764980580131")||bBatchId.equals("ff8080812824ae6f012824b0a89e0008"))
    {
    	bBatchId="and batch_id is null";
    }
    else
    {
    	bBatchId="and batch_id='"+bBatchId+"'";
    }
   
	//UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
try
{
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
	TestManage testManage = interactionManage.creatTestManage();
	
	int totalItems = 0;
	if("teacher".equalsIgnoreCase(userType)){
		totalItems = testManage.getHomeworkPapersNum(null,null,null,null,null,null,null,null,null,null,teachclass_id);
	}
	else{
		totalItems = testManage.getActiveHomeworkPapersNum(null,null,null,null,null,null,null,null,null,null,teachclass_id,null);
	}
	//----------分页开始---------------
		String spagesize = (String) session.getValue("pagesize");
		String spageInt = request.getParameter("pageInt");
		Page pageover = new Page();
		pageover.setTotalnum(totalItems);
		pageover.setPageSize(spagesize);
		pageover.setPageInt(spageInt);
		int pageNext = pageover.getPageNext();
		int pageLast = pageover.getPagePre();
		int maxPage = pageover.getMaxPage();
		int pageInt = pageover.getPageInt();
		int pagesize = pageover.getPageSize();
		String link = "&title="+title;
		//----------分页结束---------------
	List testPaperList = null;
	if("teacher".equalsIgnoreCase(userType)){
		testPaperList = testManage.getHomeworkPapers(pageover,null,null,null,null,null,null,null,null,null,null,teachclass_id);
	}
	else{
		testPaperList = testManage.getActiveHomeworkPapers(pageover,null,null,null,null,null,null,null,null,null,null,teachclass_id,null);
	}
%>

<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="../images/top_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    
                    <tr> 
                      <td align="right" valign="top"> 
                        <table width="95%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                          <td  align = "left" class="text1"><img src="/entity/function/images/xb3.gif" width="17" height="15"> 
                                         <strong>随堂练习</strong></td>   
                          
<%
	if("teacher".equalsIgnoreCase(userType)&&us.getRoleId().equals("3"))
	{
%>	
                            <td align="center"><a href="homeworkpaper_add.jsp" class="tj">[添加新作业]</a>&nbsp;</td>
<%
	}
%>	                            	
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td align="center">
				<table width="95%" border="0" cellspacing="0" cellpadding="0">
				  
                    <br/>		
                 <tr>
                   <td height="26" background="../images/tabletop.gif" style="background-repeat: no-repeat;filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src='../images/tabletop.gif');">
                	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">	
                		
                    <tr>
                      <td width="22%" align="center" class="title">名&nbsp;&nbsp;&nbsp;&nbsp;称</td>
                      <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <%
                      if("teacher".equalsIgnoreCase(userType)&& us.getRoleId().equals("3")){
                      %>
                      <!--<td width="15%" align="center" class="title">所属学期</td>-->
                      <!--<td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>-->
                      <%} %>
                      <td width="6%" align="center" class="title">发布人</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="15%" align="center" class="title">截至时间</td>
                      <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                    <%
					if("teacher".equalsIgnoreCase(userType))
					{
					%>	
                      <td width="5%" align="center" class="title">状态</td>
                      <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                    <%
					}
					else
					{
					%>
					 <td width="10%" align="center" class="title">作业状态</td>
                      <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
					
					<%
					}
					%>	
                      <td width="25%" align="center" class="title">操作</td>
                    </tr>                    
                  	</table>
                  </td>
                 </tr>
                    	<%                         
                          	int trNo=1;	//用来表示每行
                          	int trNoMod;	//记录是奇数行还是偶数行
                          	String trBgcolor;	//设置每行的背景色
                                                    ;
                          	Iterator it=testPaperList.iterator();
                          	
                          	
                          
                          	while(it.hasNext()){
								//根据奇偶行，决定每行的背景颜色          	
                          		trNoMod=trNo%2;
                          		if(trNoMod==0){
                          			trBgcolor="#E8F9FF";
                          		}else{
                          			trBgcolor="#ffffff";
                          		}
                             	HomeworkPaper homeworkPaper = (HomeworkPaper)it.next();
                             	String paperId = homeworkPaper.getId();
								String paperTitle = homeworkPaper.getTitle();
								String creatUser = homeworkPaper.getCreatUser();
								String creatDate = homeworkPaper.getCreatDate();
								String startDate = homeworkPaper.getStartDate().substring(0,10);
								String endDate = homeworkPaper.getEndDate().substring(0,10);
								String paperType = homeworkPaper.getType();
								String status = homeworkPaper.getStatus();
								String batch_name=fixnull1(homeworkPaper.getBatch_name());
								String status_link = "";
								String user_id = "("+user.getId()+")"+user.getName();
								session.setAttribute("user_id",user_id);
                            	int homeWorkNum = testManage.getHomeworkPaperHistorysNum(user_id,paperId,null,null,teachclass_id);
								if(status.equals("1"))
								{
									status_link = "<a href='homeworkpaper_changestatus.jsp?paperId="+paperId+"&title="+title+"&pageInt="+pageInt+"&status=1&note=无效'>有效</a>";
								}
								else
								{
									status_link = "<a href='homeworkpaper_changestatus.jsp?paperId="+paperId+"&title="+title+"&pageInt="+pageInt+"&status=0&note=有效'>无效</a>";
								}
								if(("student".equalsIgnoreCase(userType)&&status.equals("1"))||("teacher".equalsIgnoreCase(userType)))
								{
						%>
                    <tr>
                      <td align="center" background="images/tablebian.gif">
						<table width="99%" border="0" cellspacing="0" cellpadding="0" class="mc1">
                          <tr bgcolor="<%=trBgcolor%>"> 
                           	<td width="23%"  class="td1"><%=paperTitle%></td>
                           	<%
                      if("teacher".equalsIgnoreCase(userType)&& us.getRoleId().equals("3")){
                      %>
                           	<!--<td width="16%"  class="td1"><%=batch_name%></td>-->
                           	<%} %>
                            <td width="7%" align="center"  class="td1"><%=creatUser%></td>
                            <td width="16%" align="center" class="td1">
                             <%
							if("teacher".equalsIgnoreCase(userType)&& us.getRoleId().equals("3"))
							{
							%>	
                            	<%=startDate%>至<%=endDate%>
                            <%} else if(bStartTime != null){ 
                            %>
                            	<%=bStartTime%>至<%=bEndTime%>
                            <%} else {%>
                            	<%=startDate%>至<%=endDate%>
                            <%} %></td>
                            <%
							if("teacher".equalsIgnoreCase(userType)&& us.getRoleId().equals("3"))
							{
							%>	
                            <td width="6%" align="center"  class="td1">
                            <%=status_link%>
                            </td>
                            <td width="25%" align="center"  class="td1">
                            <%
                            }
                            else if("teacher".equalsIgnoreCase(userType)&& !us.getRoleId().equals("3"))
                            {
                            	if(status.equals("1")){
							%>	
                            <td width="5%" align="center"  class="td1">
                            <%out.print("有效");%>
                            </td>
                            <td width="25%" align="center"  class="td1">
                            <%}
                                else {%>
                                <td width="5%" align="center"  class="td1">
                             <%out.print("无效");%>
                                </td>
                                    <td width="30%" align="center"  class="td1">
                            <%  }
                            }
                           	 else
                            {
                            	
                             %>
                             <td width="11%" align="center" class="td1"><%if(homeWorkNum>0) {%>状态：已做<%}else if(homeworkPaper.getType().equals("0")){%><font color=red>状态：未做</font><%}else{%><font color=red>状态：未做</font><%} %></td>
                             <td width="25%" align="center"  class="td1">
                             <%
                             	if(testManage.getHomeworkPaperExpired(paperId))
                             	{
                              %>
                             <%if(homeWorkNum>0)  out.print("状态：已做"); else out.print("<font color=red>状态：未做</font>");%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 现在不是交作业时间
                              <%
                              	}
                              	else
                              	{
                               %>
                               <%if(homeWorkNum>0) {%><a href="homeworkhistory_list.jsp?paperId=<%=paperId%>&prepageInt=<%=pageInt %>" >查看已做作业</a>
                               <% }else if(homeworkPaper.getType().equals("0")){%>
                           		<a href="#" onclick="javascript:window.open('homeworkpaper_pre.jsp?id=<%=paperId%>&pageInt=<%=pageover.getPageInt() %>','zy','resizable,scrollbars,width=800,height=600')">做作业</a>
                            	<%
                            		} else {
                            	%>
								<a href="#" onclick="javascript:window.open('homeworkpaper_add_bypolicy_random.jsp?paperId=<%=paperId%>&pageInt=<%=pageover.getPageInt() %>','zy','resizable,width=800,height=100%')">组卷做作业</a>
                            	
                            	<%
                            		}
                            	}
                             %>
<%
					}
	if("teacher".equalsIgnoreCase(userType)&& us.getRoleId().equals("3"))
	{
		if(homeworkPaper.getType().equals("0")) {
%>							
		<a href="homeworkhistory_list.jsp?paperId=<%=paperId%>&prepageInt=<%=pageInt %>">已交作业列表</a>
        <a href="homeworkpaper_edit.jsp?id=<%=paperId%>">编辑基本信息</a>
        <a href="homeworkpaper_edit_bypolicy.jsp?id=<%=paperId%>&pageInt=<%=pageover.getPageInt() %>">编辑题目</a>
        <a href="javascript:cfmdel('homeworkpaper_delete.jsp?title=<%=title%>&pageInt=<%=pageInt%>&paperId=<%=paperId%>')" class="button">删除</a>
<%
		} else {
		%>
		<a href="homeworkhistory_list.jsp?paperId=<%=paperId%>&prepageInt=<%=pageInt %>">已交作业列表</a>
		<a href="homeworkpaper_edit.jsp?id=<%=paperId%>">编辑基本信息</a>
		<a href="javascript:cfmdel('homeworkpaper_delete.jsp?title=<%=title%>&pageInt=<%=pageInt%>&paperId=<%=paperId%>')" class="button">删除</a>
		<% }
	}
	else if("teacher".equalsIgnoreCase(userType)&& !us.getRoleId().equals("3"))
	{
		if(homeworkPaper.getType().equals("0")) {
%>							
		<a href="homeworkhistory_list.jsp?paperId=<%=paperId%>&prepageInt=<%=pageInt %>">已交作业列表</a>
        <a href="homeworkpaper_edit.jsp?id=<%=paperId%>">查看基本信息</a>
        <a href="#"  onclick="javascript:window.open('homeworkpaper_pre.jsp?id=<%=paperId%>&pageInt=<%=pageover.getPageInt() %>','zy','resizable,width=800,height=600')">查看题目</a>
        <%
		} else {
		%>
		<a href="homeworkhistory_list.jsp?paperId=<%=paperId%>&prepageInt=<%=pageInt %>">已交作业列表</a>
		<a href="homeworkpaper_edit.jsp?id=<%=paperId%>">查看基本信息</a>
				<% }
	}
%>	                            	
				            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    	<%  
                    			}
                       		trNo++;	
                          	}
                        %> 
                    
                    <tr>
                      <td><img src="../images/tablebottom.gif" width="99%" height="4"></td>
                    </tr>
                  </table><br/>
                </td>
              </tr>
              <tr>
                <td align="center">
<table width="95%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="images/bottomtop.gif" width="100%" height="7"></td>
                    </tr>
                    <tr>
                      <td background="images/bottom02.gif">
                       <%@ include file="../pub/dividepage.jsp" %>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="images/bottom03.gif" width="100%" height="7"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table></td>
        
        </tr>
      </table>
</body>
</html>
<%
	}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
%>
