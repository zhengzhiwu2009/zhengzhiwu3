<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.util.Cookie.*,com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ page import="java.net.URLEncoder" %>
<jsp:directive.page import="java.net.URLDecoder"/>
<%@ include file="../pub/priv.jsp"%>

<%
	String ids = request.getParameter("id");
	String tsId = request.getParameter("tsId");
	HashMap userAnswer = (HashMap)session.getAttribute("UserAnswer");
	HashMap standardAnswer = (HashMap)session.getAttribute("StandardAnswer");
	HashMap Title = (HashMap)session.getAttribute("Title");
	HashMap Type = (HashMap)session.getAttribute("Type");
	HashMap Score = (HashMap)session.getAttribute("Score");
	
	HashMap userScore = new HashMap();
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
	TestManage testManage = interactionManage.creatTestManage();
	
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>作业</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script>
function saveTime()
{
	//document.cookie="TestTime=0";
}

</script>
</head>
<body leftmargin="0" topmargin="0"  background="images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="images/top_01.gif"><img src="images/zxzy.gif" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="800" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="20%" background="images/st_01.gif" class="text3" style="padding-left:50px">作&nbsp;&nbsp;业</td>
                            <td width="75%" height="53" background="images/wt_03.gif" align="right"></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td valign="top">
						<table width="67%" border="0" align="center" cellpadding="0" cellspacing="0">
						<%
						int right=0;
						int errors =0;
						String total_type="";
						try {
							int count = 1;
							float total_score = 0f;
							boolean status =false;
							String id = "";
							String title = "";
							String type = "";
							String user_answer = "";
							String standard_answer = "";
							String standard_score = "";
							float user_score = 0f;
							Set set = Title.keySet();
							for(Iterator it = set.iterator();it.hasNext();)
							{
								id = (String)it.next();
								type = (String)Type.get(id);
								PaperQuestion pq = testManage.getPaperQuestion(id);  
								String coreXML = pq.getQuestionCore();
								/*
								List list = XMLParserUtil.parserBlankAnswer(coreXML);
								*/
								List list = new ArrayList();
								if(!type.equalsIgnoreCase(TestQuestionType.YUEDU)){
								list = XMLParserUtil.parserBlankAnswer(coreXML);
								}else{
								list = XMLParserUtil.parserComprehension(coreXML);
								}
								String body =  (String)list.get(0);
								
								total_type=type;
								//out.print((body));
								if(!type.equalsIgnoreCase(TestQuestionType.YUEDU))
								{
									title = (String)Title.get(id);
									standard_answer = (String)standardAnswer.get(id);
									if(standard_answer!=null)
										standard_answer = standard_answer.replace('|',',');
									else
										standard_answer = "";
									standard_score = (String)Score.get(id);
									user_answer = (String)userAnswer.get(id);
									
									if(type.equalsIgnoreCase(TestQuestionType.DUOXUAN)&&standard_answer.indexOf(",")>0){
										if(standard_answer!=null)
										standard_answer = standard_answer.replace(',','|');
									else
										standard_answer = "";
									}
									
									if(type.equalsIgnoreCase(TestQuestionType.PANDUAN)){
										if("1".equals(standard_answer)){
											standard_answer = "正确";
										}
										if("0".equals(standard_answer)){
											standard_answer = "错误";
										}
										if("1".equals(user_answer)){
											user_answer = "正确";
										}
										if("0".equals(user_answer)){
											user_answer = "错误";
										}
									}
									
									if(user_answer!=null && standard_answer.equals(user_answer)){
										user_score = Float.parseFloat(standard_score);
										right++;
										//System.out.println("right,type:"+type+",s_answer:"+standard_answer+",u_answer:"+user_answer+",count="+count);
									}
									else{
										user_score = 0f;
										errors++;
										//System.out.println("error,type:"+type+",s_answer:"+standard_answer+",u_answer:"+user_answer+",count="+count);
									}
									if(type.equalsIgnoreCase(TestQuestionType.WENDA)) {
										user_score = 0f;
									}
									total_score+=user_score;
									userScore.put(id,Float.toString(user_score));
									if(user_answer!=null)
									{
										user_answer = user_answer.replace('|',',');
										status = true;
									}
									else
									{
										user_answer = "未答此题！";
										status = false;
									}
									
						%>
                          <tr> 
                            <td valign="middle" align="left"><%=count++%>．<%=title%></td>
                          </tr>
                        <%
                     	if(status)
                        {
                        %>
                        	<tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">题目内容：<%=body%></td>
                          </tr>
                        
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">标准答案：<%=standard_answer%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">您的答案：<%=user_answer%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">此题得分：<%=user_score%></td>
                          </tr>
                       	<%
                       	}
                     	else
                       	{
                       	%>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;<%=user_answer%></td>
                          </tr>
                        <%
                        }
                       	%>
                          <tr> 
                            <td valign="middle" align="left">&nbsp;</td>
                          </tr>
                        <%
                		}
                		else
                		{
							List titleList = (List)Title.get(id);
							
							List standard_answerList = (List)standardAnswer.get(id);
							List standard_scoreList = (List)Score.get(id);
							List user_answerList = (List)userAnswer.get(id);
							List user_scoreList = new ArrayList();
							float scoretmp = 0f;
							user_scoreList.add("");
							title = (String)titleList.get(0);
							if(user_answer==null)
							{
								user_answer = "未答此题！";
								status = false;
							}
							else
								status = true;	
						
						%>
                          <tr> 
                            <td valign="middle" align="left"><%=count++%>．<%=title%></td>
                          </tr>
                        <%
                     	if(status)
                        {
                        	for(int k=1;k<titleList.size();k++)
                        	{
                        		title = (String)titleList.get(k);
								standard_answer = (String)standard_answerList.get(k);
								if(standard_answer!=null)
									standard_answer = standard_answer.replace('|',',');
								else
									standard_answer = "";
								standard_score = (String)standard_scoreList.get(k);
								if(user_answerList!=null)
								{
									user_answer = (String)user_answerList.get(k);
									user_answer = user_answer.replace('|',',');
								}
								else
									user_answer = "未答此题！";
								if(user_answer!=null && standard_answer.equals(user_answer))
								{
									user_score = Float.parseFloat(standard_score);
								    right++;
								}
								else
								{
									user_score = 0f;
									errors++;
								}
								
								total_score+=user_score;
								scoretmp+=user_score;
								user_scoreList.add(Float.toString(user_score));
									
                        %>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;<%=k%>. <%=title%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;标准答案：<%=standard_answer%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;您的答案：<%=user_answer%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;题目得分：<%=user_score%></td>
                          </tr>
                       	<%
                       		}
                       	}
                    	else
                       	{
                       	%>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;<%=user_answer%></td>
                          </tr>
                        <%
                        }
                       	%>
                          <tr> 
                            <td valign="middle" align="left">题目总得分：<%=scoretmp%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left">&nbsp;</td>
                          </tr>
                        <%
                        		user_scoreList.set(0,Float.toString(scoretmp));
								userScore.put(id,user_scoreList);
                        		}  
                        	}
							session.setAttribute("UserScore",userScore);
                        %>
                          <tr> 
                            <td valign="middle" align="left">试卷总得分：<%=total_score%></td>
                          </tr>
			              <tr> 
			                <td ><table width="40%" border="0" align="center" cellpadding="0" cellspacing="0">
			                    <tr align="center"> 
			                      <td class="text"><img src="images/bcfh.gif" width="100" height="30" onclick="window.location.href='homeworkpaper_resultexe.jsp?id=<%=ids%>&totalScore=<%=total_score%>'"></td><!--
			                      <td class="text"><img src="images/zjfh.gif" width="100" height="30" onclick="window.location.href='homeworkpaper_list.jsp'"></td>
			                    --></tr>
			                  </table></td>
			              </tr>
                        </table><br/>
                      </td>
                    </tr>
                  </table></td>
                    </tr>
                  </table> 
                </td>
              </tr>
            </table></td>
        </tr>
      </table-->
      <script>
      	//alert(<%=total_score%>);
      	window.location.href="homeworkpaper_resultexe.jsp?id=<%=ids%>&totalScore=<%=total_score%>&right=<%=right%>&errors=<%=errors%>&tsId=<%=tsId%>&totalType=<%=total_type%>";
      </script>
<%
	} catch (Exception e) {
	
		out.print(e.toString()+"22");
	}
%>
</body>
</html>
