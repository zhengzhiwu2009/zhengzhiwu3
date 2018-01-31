<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,java.net.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.answer.*"%>
<%@ include file="../pub/priv.jsp"%>
<%
	String mock_login = (String)session.getAttribute("mock_login");
	String title = fixnull(request.getParameter("title"));
	String name = fixnull(request.getParameter("name"));
	String type = fixnull(request.getParameter("type"));
	
%>
<html>
<head>
<title>清华大学继续教育学院</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<br/>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                          <td width = "8">&nbsp;</td>
                          <td   class="text1"><img src="/entity/function/images/xb3.gif" width="17" height="15"> 
                                         <strong>课程答疑</strong></td> 
                          </tr>
                          </table>
                          <br/>
<table width="85%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="../images/top_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <!-- tr> 
                      <td width="217" height="104" rowspan="2" valign="top"><img src="../images/dy.gif" width="217" height="86"></td>
                      <td height="46">&nbsp;</td>
                    </tr-->
                    <tr> 
                      <td align="right" valign="top"> 
                      	<form name = "search" method="post" action="index.jsp">
                      	<input type="hidden" name="type" value="<%=type %>">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td align="center">
                            	<a href="faq.jsp" class="tj">[常见问题库]</a>
<%
	if("student".equalsIgnoreCase(userType))
	{
%>	
<%
		if(mock_login == null || !mock_login.equals("1")){
		%>
                            	<a href="question_add.jsp" class="tj">[我要提问]</a>
<%
		}
	}
%>	                            	
                            </td>
                            <td align="center"><img src="../images/xb.gif" width="48" height="32"></td>
                            <td align="center" class="mc1">按问题名称搜索：</td>
                            <td align="center">
<input name="title" type="text" size="10" maxlength="50" value="<%=title %>"> </td>
							
                            <td align="center" class="mc1">按发布人搜索：</td>
                            <td align="center">
<input name="name" type="text" size="10" maxlength="50" value="<%=name %> "></td>
                            <td align="center"><a href="javascript:document.search.submit()"><img src="../images/search.gif" width="99" height="19" border="0"></a></td>
                            <td width="35">&nbsp;</td>
                          </tr>
                        </table>
                        </form>
                      </td>
                    </tr>
                  </table></td>
              </tr>
              
              
              
              <tr>
                <td align="center">
<table width="87%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                      
                <td height="26" background="../images/tabletop1.gif">
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="35%" align="center" class="title">问&nbsp;&nbsp;&nbsp;&nbsp;题</td>
                      <td width="19%" align="center" class="title">问题分类</td>
                      <td width="8%" align="center" class="title">是否回答</td>                      
                      <td width="19%" align="center" class="title">发布人</td>
                      <td width="19%" align="center" class="title">发布时间</td>                      
                    </tr>
                  </table></td>
            </tr>
                    <tr>
                      <td align="center" background="../images/tablebian2.gif">
<table width="99%" border="0" cellspacing="0" cellpadding="0">
<%!
	String fixnull(String str)
	{
		if(str==null || str.equalsIgnoreCase("null"))
			return "";
		else
			return str.trim();
	}
%>  
<%  
title = fixnull(request.getParameter("title"));
name = fixnull(request.getParameter("name"));       
	try
{
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
		
		int totalItems = interactionManage.getQuestionsNum(teachclass_id, title, name,type,"");
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
		String link = "&title=" + URLEncoder.encode(title, "utf-8") + "&name=" + URLEncoder.encode(name, "utf-8") + "&type=" + type;;
			//----------分页结束---------------		
		List questionList = interactionManage.getQuestions(pageover, teachclass_id, title, name,type,"");
		
		Iterator it = questionList.iterator();
		boolean bg = false;
		String bgcolor = "";
		while(it.hasNext())
		{
			Question question = (Question)it.next();
			if(bg == true)
			{
				bgcolor = " bgcolor=\"E8F9FF\"";
				bg = false;
			}
			else
			{
				bgcolor = "";
				bg = true;
			}
%>                                                                                                
              <tr<%=bgcolor%>> 
		          <!-- td width="5%" align="center" valign="middle"  class="td1"><img src="../../images/xb2.gif" width="8" height="8"></td>
                  <td width="50%" align="center" class="title"><%=question.getTitle()%></td>
                  <td width="8%" align="center" class="title"><%=question.isReStatus()%></td>                      
                  <td width="18%" align="center" class="title"><%=question.getSubmituserName()%></td>
                  <td align="center" class="title"><%=question.getDate()%></td>
                  <td width="4%">&nbsp;</td-->                    
                <td width="1%" align="center" valign="middle"  class="td1"><img src="../images/xb2.gif" width="8" height="8"></td>
                <td width="34%" align="center" class="td1"><a href="question_detail.jsp?id=<%=question.getId()%>&pageInt=<%=pageInt%>&title=<%=title%>&name=<%=name%>"><font color="blue"><u> <%=question.getTitle()%></u></font> <a>&nbsp;<!-- a href="answer_list.jsp?id=<%=question.getId()%>">[答案列表]</a--></td>
               	 <td width="19%" align="center"  class="td1"><%=fixnull(question.getQuestionType())%></td>                
                <td width="7%" align="center"  class="td1"><%if(true == question.isReStatus()) out.print("是");else out.print("否");%></td>                
                <td width="20%" align="center"  class="td1"><%=question.getSubmituserName()%></td>
                <td width="19%" align="center"  class="td1"><%=question.getDate()%></td>
              </tr>
                          
<%
		}
%>           
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="../images/tablebottom.gif" width="100%" height="4"></td>
                    </tr>
                  </table><br/>
                </td>
              </tr>
			                           
              <tr>
                <td align="center">
					<table width="87%" border="0" cellspacing="0" cellpadding="0">
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

 