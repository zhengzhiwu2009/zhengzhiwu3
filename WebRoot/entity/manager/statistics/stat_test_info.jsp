<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page  import="java.util.*"%>
<%@ page import="com.whaty.platform.test.question.*"%>
<%@page import="org.dom4j.DocumentHelper"%>
<%@page import="org.dom4j.Document"%>
<%@page import="org.dom4j.Element"%>
<%@page import="com.whaty.platform.database.oracle.dbpool"%>
<%@page import="com.whaty.platform.database.oracle.MyResultSet"%>
<%@page import="com.whaty.util.string.encode.HtmlEncoder" %>
<%@ include file="./pub/priv.jsp"%>
<html>
<head>
</head>
<body background="images/bg2.gif">
<table width="70%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="images/top_01.gif"><img src="images/zxzc.gif" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="800" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="20%" background="images/st_01.gif" class="text3" style="padding-left:50px">自&nbsp;&nbsp;测</td>
                            <td width="75%" height="53" background="images/wt_03.gif" align="right"></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td valign="top">
						<table width="67%" border="0" align="center" cellpadding="0" cellspacing="0">
						<%
						String paperId = request.getParameter("paperid");
						String user_id = request.getParameter("userid");
						dbpool db = new dbpool();
						MyResultSet rs = null;
						MyResultSet rs1 = null;
						String sql = "";
						sql = "select ty.id as id,ty.TEST_RESULT as testresult from test_testpaper_history ty where ty.t_user_id = '"+user_id+"' and ty.id ='"+paperId+"'";
						
						rs = db.executeQuery(sql);
						String totalscore = ""; //总分
						String note=""; 
						int num=0;
						while(rs!=null&&rs.next()){
							
							String btltle =""; //第几题
							String sanswer=""; //标准答案
							String uanswer=""; //用户答案
							String sscore =""; //题目分数
							String uscore =""; //用户得分
							
							
							String testresult = rs.getString("testresult");
							Document doc = DocumentHelper.parseText(testresult);
							List itemlist = doc.selectNodes("/answers/item");
							HashMap map = new HashMap();
							String totalScore = doc.selectSingleNode("/answers/totalscore").getText();
							map.put("totalScore", totalScore);
							String totalNote = doc.selectSingleNode("/answers/totalnote").getText();
							map.put("totalNote", totalNote);
							
							HashMap userAnswer = new HashMap();
							HashMap standardAnswer = new HashMap();
							HashMap Title = new HashMap();
							HashMap Type = new HashMap();
							HashMap standardScore = new HashMap();
							HashMap userScore = new HashMap();
							List idList = new ArrayList();
							String uAnswer = "";
							String sAnswer = "";
							String title = "";
							String type = "";
							String sScore = "";
							String uScore = "";
							String id = "";
							for(Iterator it =itemlist.iterator();it.hasNext();){
								num++;
								Element element = (Element)it.next();
								totalscore = element.selectSingleNode("/answers/totalscore").getText();
								note= element.selectSingleNode("/answers/totalnote").getText();
								btltle = element.element("title").getTextTrim();
								String typeEle = element.element("type").getTextTrim();
								if(!typeEle.equalsIgnoreCase(TestQuestionType.YUEDU))
								{
									sanswer = element.element("sanswer").getTextTrim();
									uanswer = element.element("uanswer").getTextTrim();
									if(typeEle.equalsIgnoreCase(TestQuestionType.PANDUAN)){
										if(uanswer.equals("0"))
											uanswer = "错误";
										if(uanswer.equals("1"))
											uanswer = "正确";
										if(sanswer.equals("0"))
											sanswer = "错误";
										if(sanswer.equals("1"))
											sanswer = "正确";
									}
									if(typeEle.equalsIgnoreCase(TestQuestionType.DUOXUAN)){
									if(sanswer!=null&&!sanswer.equals("")&&!sanswer.equals("null")&&uanswer!=null)
									{
										sanswer = sanswer.replace('|',',');
										uanswer = uanswer.replace('|',',');
									}
									else
									{
										sanswer = "未答此题！";
									}
									}
									sscore = element.element("sscore").getTextTrim();
									uscore = element.element("uscore").getTextTrim();
									
									id = element.element("id").getTextTrim();
									sql="select po.id as id ,po.QUESTIONCORE as QUESTION from test_paperquestion_info po where po.id='"+id+"'";
									rs1 = db.executeQuery(sql);
									while(rs1!=null&&rs1.next()){
										String question = rs1.getString("QUESTION");
										Document dm = DocumentHelper.parseText(question);
										String body = dm.selectSingleNode("/question/body").getText();
										List list = dm.selectNodes("/question/select/item");
										%>
										<tr> 
				                            <td valign="middle" align="left" style="font-size:14px;color:#7f7f7f;line-height:24px"><%=btltle %> &nbsp; <%=body %></td>
				                        </tr>
										<%
										for(Iterator itt = list.iterator();itt.hasNext();){
											Element ele = (Element)itt.next();
											String index = ele.element("index").getTextTrim();
											String content = ele.element("content").getTextTrim();
											String san  = index+". "+content;
										%>
				                          <tr> 
				                            <td valign="middle" align="left"> &nbsp; &nbsp; &nbsp; &nbsp;<span style="font-size:13px;line-height:24px">
												<%=san %></span>
				                            </td>
				                          </tr>
				                          <%
										}
										%>
										<tr> 
	                           			 <td valign="middle" align="left" style="font-size:13px;color:red;line-height:24px"> &nbsp; &nbsp; &nbsp;标准答案: &nbsp; &nbsp; <%=sanswer%></td>
	                          			</tr>
										<tr> 
	                           			 <td valign="middle" align="left" style="font-size:13px;color:blue;line-height:24px"> &nbsp; &nbsp; &nbsp;您的答案: &nbsp; &nbsp; <%=uanswer%></td>
	                          			</tr>
	                          			<tr> 
	                           			 <td valign="middle" align="left" style="font-size:13px;line-height:24px"> &nbsp; &nbsp; &nbsp;题目分数: &nbsp; &nbsp; <%=sscore%></td>
	                          			</tr>
	                          			<tr>
	                          				<td>&nbsp;</td>
	                          			</tr>
										<%
										
								}
									db.close(rs1);
							}
							else
							{
								List uAnswerList = new ArrayList();
								List sAnswerList = new ArrayList();
								List titleList = new ArrayList();
								List sScoreList = new ArrayList();
								List uScoreList = new ArrayList();

								uAnswer = "";
								uAnswerList.add(HtmlEncoder.decode(uAnswer));
								sAnswer = "";
								sAnswerList.add(HtmlEncoder.decode(sAnswer));
								Element titleEle = element.element("title");
								title = titleEle.getTextTrim();
								titleList.add(HtmlEncoder.decode(title));
								Element sScoreEle = element.element("sscore");
								sScore = sScoreEle.getTextTrim();
								sScoreList.add(HtmlEncoder.decode(sScore));
								Element uScoreEle = element.element("uscore");
								uScore = uScoreEle.getTextTrim();
								uScoreList.add(HtmlEncoder.decode(uScore));
								Element subEle = element.element("subitem");
								Iterator itSub = subEle.elementIterator("item");
								while (itSub.hasNext()) {
									Element subItem = (Element) itSub.next();
									Element idEleSub = element.element("id");
									id = idEleSub.getTextTrim();

									Element uAnswerEleSub = subItem.element("uanswer");
									uAnswer = uAnswerEleSub.getTextTrim();
									uAnswerList.add(HtmlEncoder.decode(uAnswer));
									Element sAnswerEleSub = subItem.element("sanswer");
									sAnswer = sAnswerEleSub.getTextTrim();
									sAnswerList.add(HtmlEncoder.decode(sAnswer));
									Element titleEleSub = subItem.element("title");
									title = titleEleSub.getTextTrim();
									titleList.add(HtmlEncoder.decode(title));
									Element sScoreEleSub = subItem.element("sscore");
									sScore = sScoreEleSub.getTextTrim();
									sScoreList.add(HtmlEncoder.decode(sScore));
									Element uScoreEleSub = subItem.element("uscore");
									uScore = uScoreEleSub.getTextTrim();
									uScoreList.add(HtmlEncoder.decode(uScore));
								}
								
								title = (String)titleList.get(0);
								%>
								<tr> 
				                            <td valign="middle" align="left" style="font-size:14px;color:#7f7f7f;line-height:24px">第<%=num %>题  <%=title %></td>
				                        </tr>
								<% 
								for(int k=1;k<titleList.size();k++)
	                        	{
	                        		title = (String)titleList.get(k);
	                        		sanswer = (String)sAnswerList.get(k);
									if(sanswer!=null)
										sanswer = sanswer.replace('|',',');
									else
										sanswer = "";
									sscore = (String)sScoreList.get(k);
									uanswer = (String)uAnswerList.get(k);
									if(uanswer!=null)
										uanswer = uanswer.replace('|',',');
									uscore = (String)uScoreList.get(k);
									%>
									<tr> 
					                            <td valign="middle" align="left" style="font-size:14px;color:#7f7f7f;line-height:24px"><%=k%>. <%=title%></td>
					                        </tr>
					                        <tr> 
					                          </tr>
									<tr> 
	                           			 <td valign="middle" align="left" style="font-size:13px;color:red;line-height:24px"> &nbsp; &nbsp; &nbsp;标准答案: &nbsp; &nbsp; <%=sanswer%></td>
	                          			</tr>
										<tr> 
	                           			 <td valign="middle" align="left" style="font-size:13px;color:blue;line-height:24px"> &nbsp; &nbsp; &nbsp;您的答案: &nbsp; &nbsp; <%=uanswer%></td>
	                          			</tr>
	                          			<tr> 
	                           			 <td valign="middle" align="left" style="font-size:13px;line-height:24px"> &nbsp; &nbsp; &nbsp;题目分数: &nbsp; &nbsp; <%=sscore%></td>
	                          			</tr>
	                          			<tr>
	                          				<td>&nbsp;</td>
	                          			</tr>
									<% 
	                        	}
							  }
							}
						}		
						db.close(rs);
						%>
                         
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;" line-height:24px">自测总得分：<span style="font-size:13px;color:red;"><%=totalscore%></span></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">自测总批注：<%=note %></td>
                          </tr>
			              <tr> 
			                <td ><table width="40%" border="0" align="center" cellpadding="0" cellspacing="0">
			                    <tr align="center"> 
			                      <td class="text"><img src="images/gb.gif" width="80" height="24" onclick="window.close()"></td><!--
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
      </table>
</body>
</html>

