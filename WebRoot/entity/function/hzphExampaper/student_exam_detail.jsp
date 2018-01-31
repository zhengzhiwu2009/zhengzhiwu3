<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*,com.whaty.platform.test.history.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*,com.whaty.util.string.encode.*"%>
<%@ page import="java.net.URLDecoder" %>
<%@ include file="../pub/priv.jsp"%>
<%
try{
	String tsId = (String)session.getAttribute("tsId");//得到的是试题的ID。
	String pageInt = "1";
	String piciId = (String)session.getAttribute("piciId");

	String sql = " SELECT PASSSCORE FROM PE_BZZ_PICI WHERE ID = '" + piciId + "'";
	dbpool db  = new dbpool();
	MyResultSet mrs = db.execuQuery(sql);
	String passscore = "0";
	while(mrs.getMyrset().next()){
		passscore = mrs.getString("passscore");
	}
	db.close(mrs);
	mrs = null;
	db = null;
	
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
	TestManage testManage = interactionManage.creatTestManage();
	TestPaperHistory history = testManage.getTestPaperHistory(tsId);//根据试题ID来得到试题详细信息test_testpaper_history表中。
	String paperId = history.getTestPaperId();//得到paperid
	String paperUserId =history.getUserId();//得到user。
	HashMap map = history.getTestResultMap();//得到试题内容。其实存储在test_testpaper_history的test_result的中存储的时候的分数已经是这样了的。总分是100分。
	List idList = (List)map.get("idList");
	session.putValue("idList",idList);
	HashMap userAnswer = (HashMap)map.get("userAnswer");
	session.putValue("UserAnswer",userAnswer);
	HashMap standardAnswer = (HashMap)map.get("standardAnswer");
	session.putValue("StandardAnswer",standardAnswer);
	HashMap Title = (HashMap)map.get("title");
	session.putValue("Title",Title);
	HashMap Type = (HashMap)map.get("type");
	session.putValue("Type",Type);
	HashMap standardScore = (HashMap)map.get("standardScore");
	session.putValue("StandardScore",standardScore);
	HashMap userScore = (HashMap)map.get("userScore");
	HashMap Note = (HashMap)map.get("note");
	String total_score = (String)map.get("totalScore");
	String total_note = (String)map.get("totalNote");
		TestPaper paper = testManage.getTestPaper(paperId);
		List questionList = paper.getPaperQuestion();
		HashMap Titles = new HashMap();
		List singleList = new ArrayList();
		List multiList = new ArrayList();
		List judgeList = new ArrayList();
		List blankList = new ArrayList();
		List answerList = new ArrayList();
		List comprehensionList = new ArrayList();
		for(int i=0; i<questionList.size(); i++) {
			PaperQuestion pq = (PaperQuestion)questionList.get(i);
			String pq_type = pq.getType();
			if(pq_type.equalsIgnoreCase(TestQuestionType.DANXUAN))
				singleList.add(pq);
			if(pq_type.equalsIgnoreCase(TestQuestionType.DUOXUAN))
				multiList.add(pq);
			if(pq_type.equalsIgnoreCase(TestQuestionType.PANDUAN))
				judgeList.add(pq);
			if(pq_type.equalsIgnoreCase(TestQuestionType.TIANKONG))
				blankList.add(pq);
			if(pq_type.equalsIgnoreCase(TestQuestionType.WENDA))
				answerList.add(pq);
			if(pq_type.equalsIgnoreCase(TestQuestionType.YUEDU))
			{
				comprehensionList.add(pq);
			}
		}
		List normalList = new ArrayList();
		if(singleList.size()>0)
			normalList.addAll(singleList);
		if(multiList.size()>0)
			normalList.addAll(multiList);
		if(judgeList.size()>0)
			normalList.addAll(judgeList);
		if(blankList.size()>0)
			normalList.addAll(blankList);
		if(answerList.size()>0)
			normalList.addAll(answerList);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>试题</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script>
	function isNum(x)
	{
		var isRight=true;
		var count = 0;
		for (var i=0;i<x.length;i++)
		{
			var k = x.charAt(i);
			if ((k<'0' && k!='.') || (k>'9' && k!='.' ))
			{
				isRight=false;
				break;
			}
			if(k=='.')
				count++;
		}
		if(count>1)
		{
			isRight=false;
		}	
		return isRight;
	}
	
	function checkscore(name,score,sscore)
	{
		if(!isNum(score))
		{
			alert("分数请输入数字！");
			document.getElementById(name).focus();
			document.getElementById(name).select();
			return;
		}
		if(score*1>sscore*1)
		{
			alert("评分不能大于题目分数！");
			document.getElementById(name).focus();
			document.getElementById(name).select();
			return;
		}
	}

</script>
</head>
<body leftmargin="0" topmargin="0"  background="images/bg2.gif" style='overflow:scroll;overflow-x:hidden'>
<form action="testhistory_checkexe.jsp?piciId=<%=piciId %>" method="post" name="check">
<input type="hidden" name="tsId" value=<%=tsId%>>
<input type="hidden" name="paperId" value=<%=paperId%>>
<input type="hidden" name="pageInt" value=<%=pageInt%>>
<input type="hidden" name="paperUserId" value=<%=paperUserId%>>
<table width="70%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="images/top_01.gif"><img src="images/zxzc.gif" width="217" height="86"></td>
              </tr>
               <tr>
              	 <td align="">
            		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <font size="3"><b>提示：</b> 考试记录最后一次提交有效，当前查看最后一次考试记录！</font>
            	   </td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="800" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="20%" background="images/st_01.gif" class="text3" style="padding-left:50px">试&nbsp;&nbsp;题</td>
                            <td width="75%" height="53" background="images/wt_03.gif" align="right"></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td valign="top">
						<table width="67%" border="0" align="center" cellpadding="0" cellspacing="0">
						<%
							int count = 1;
							boolean status =false;
							String id = "";
							String title = "";
							String type = "";
							String user_answer = "";
							String standard_answer = "";
							String standard_score = "";
							String user_score = "";
							String passScore="";
							String note = "";
							//Set set = Title.keySet();
							int num = 0;
							int num1 = 0;
							for(Iterator it = idList.iterator();it.hasNext();)
							{
								id = (String)it.next();
								type = (String)Type.get(id);
								if(!type.equalsIgnoreCase(TestQuestionType.YUEDU))
								{
		                			PaperQuestion pq = (PaperQuestion)normalList.get(num);
		                			String coreXML = pq.getQuestionCore();
		                			List list = XMLParserUtil.parserSingleMulti(coreXML);
		                			String body = HtmlEncoder.decode((String)list.get(0));
		                			passScore =pq.getScore();
		                			
		                			List answers =new ArrayList();
		                			for(int i =1;i<list.size() -1;i++){
		                			answers.add(list.get(i));
		                			}
									title = (String)Title.get(id);
									standard_answer = (String)standardAnswer.get(id);
									user_answer = (String)userAnswer.get(id);
									if(standard_answer!=null&&!standard_answer.equals("")&&!standard_answer.equals("null") || user_answer!=null)
									{
										standard_answer = standard_answer.replace('|',',');
										status = true;
									}
									else
									{
										standard_answer = "未答此题！";
										status = false;
									}
									if(type.equalsIgnoreCase(TestQuestionType.PANDUAN))
									{
										if(standard_answer.equals("0"))
											standard_answer = "错误";
										if(standard_answer.equals("1"))
											standard_answer = "正确";
									}
									standard_score = (String)standardScore.get(id);//这个是得到分数的语句。
									user_answer =(String)userAnswer.get(id);
									user_score = (String)userScore.get(id);
									note = (String)Note.get(id);
									if(user_answer!=null){
										//对答案进行排序
										if(user_answer.indexOf("|")!=-1){
											
											char[] str1=user_answer.replace("|","").toCharArray();
											Arrays.sort(str1);
											String tempUser_answer="";
											for(int i=0;i<str1.length;i++){
												if(i+1==str1.length){
													tempUser_answer+=str1[i];
												}else{
													tempUser_answer+=str1[i]+",";
												}
											}
											user_answer=tempUser_answer;
										} 
										//user_answer = user_answer.replace('|',',');
									}
									else{
										user_answer = "未答此题！";
									}
									if(type.equalsIgnoreCase(TestQuestionType.PANDUAN))
									{
										if(user_answer.equals("0"))
											user_answer = "错误";
										if(user_answer.equals("1"))
											user_answer = "正确";
									}
									if(note==null || note.equals("") || note.equals("null"))
										note = "";
						%>
                          <tr> 
                            <td valign="middle" align="left"><%=count++%>．<%=TestQuestionType.typeShow(type) %>&nbsp;&nbsp; <%=body%></td>
                          </tr>
                        <%
                        if(status)
                        {
                        %>
                           <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">标准答案：<%=standard_answer%></td>
                          </tr> 
                          <%
                          if(pq.getType().equals("DANXUAN") ||pq.getType().equals("DUOXUAN")){
                          for(int i =0; i<answers.size() ;i=i+2){   %> 
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px"><%= answers.get(i)%> :&nbsp;&nbsp; <%= answers.get(i+1)%></td>
                          </tr>
                          
                        <% }
                        }%>  
                          
                         
                           
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">考生答案：<font color="red"><%=user_answer%></font></td>
                          </tr>
                          <tr>
                           <td width="75%" height="53" background="images/wt_03.gif" align="right"></td>
                           </tr>
                       	<%
                       	}
                       	else
                       	{
                       	%>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;<%=standard_answer%></td>
                          </tr>
                        <%
                        }
                       	%>

                        <%
                        	num++;
                        }
                        else
                		{
							PaperQuestion pq = (PaperQuestion)comprehensionList.get(num1);
							String questionCoreXml = pq.getQuestionCore();
							List coreList = XMLParserUtil.parserComprehension(questionCoreXml);
							String bodyString = HtmlEncoder.decode((String)coreList.get(0));
										
							List titleList = (List)Title.get(id);
							List standard_answerList = (List)standardAnswer.get(id);
							List standard_scoreList = (List)standardScore.get(id);
							List user_answerList = (List)userAnswer.get(id);
							List user_scoreList = (List)userScore.get(id);
							List noteList = (List)Note.get(id);
							title = (String)titleList.get(0);
							String notetmp = (String)noteList.get(0);
							String scoretmp = (String)user_scoreList.get(0);
							String scoretmp1 = (String)standard_scoreList.get(0);
							if(scoretmp==null || scoretmp.equals("") ||scoretmp.equals("null"))
							{
								standard_answer = "未答此题！";
								status = false;
							}
							else
								status = true;
						%>
                          <tr> 
                            <td valign="middle" align="left"><%=count++%>．<%=title%>&nbsp;&nbsp;&nbsp;&nbsp; <%=TestQuestionType.typeShow(type) %> <%=bodyString%></td>
                          </tr>
                        <%
                     	if(status)
                        {
                        	for(int k=1;k<titleList.size();k++)
                        	{
                        		List subList = (List)coreList.get(k);	
                        		String subBody = HtmlEncoder.decode((String)subList.get(4));	
                        			
                        		title = (String)titleList.get(k);
								standard_answer = (String)standard_answerList.get(k);
								if(standard_answer!=null)
									standard_answer = standard_answer.replace('|',',');
								else
									standard_answer = "";
								standard_score = (String)standard_scoreList.get(k);
								user_answer = (String)user_answerList.get(k);
								if(user_answer!=null)
									user_answer = user_answer.replace('|',',');
								user_score = (String)user_scoreList.get(k);
								note = (String)noteList.get(k);
                        %>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;<%=k%>. <%=title%>&nbsp;&nbsp; <%=subBody%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;标准答案：<%=standard_answer%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;考生答案：<%=user_answer%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;题目分数：<%=standard_score%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;此题得分：<input type="text" size=2 style="text-align:center" id="<%=id+"_"+k+"_score"%>" name="<%=id+"_"+k+"_score"%>" value="<%=user_score%>" onblur="checkscore('<%=id+"_score"%>',this.value,'<%=standard_score%>');totalScore();"></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;批注：<textarea name="<%=id+"_"+k+"_note"%>"><%=note%></textarea></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left">&nbsp;</td>
                          </tr>
                       	<%
                       		}
                       	}
                    	else
                       	{
                       	%>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;<%=standard_answer%></td>
                          </tr>
                        <%
                        }
                       	%>
                        <input type="hidden" name="<%=id+"_totalnum"%>" value="<%=titleList.size()%>">
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;题目总分数：<%=scoretmp1%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;题目总得分：<input type="text" size=2 style="text-align:center" name="<%=id+"_score"%>" value=<%=scoretmp%> ></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;题目总批注：<textarea name="<%=id+"_note"%>"><%=notetmp%></textarea></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left">&nbsp;</td>
                          </tr>
                        <%
                        			num1++;
                        		}
                        	}
                        %>
                         
			              <tr> 
			                <td ><table width="40%" border="0" align="center" cellpadding="0" cellspacing="0">
			                    <tr align="center"> 
			                    <!--  <td class="text"><img src="images/OK.gif" width="80" height="24" onclick="document.check.submit()"></td>
			                      <td class="text"><img src="images/tlfh.gif" width="80" height="24" onclick="window.location.href='/entity/onlineexam/pePcDetail.action?id=<%=piciId %>&method=mystudent'"></td>-->  
			                    </tr>
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
<%
	} catch (Exception e) {
		//out.print(e.toString());
		
	}
%>
</form>
</body>
</html>
<script>
	function totalScore()
	{
		var tags = document.getElementsByTagName("INPUT");
		var total = 0;
		for(var i=0;i<tags.length;i++)
		{
			var t = tags[i].type;
			var name = tags[i].name;
			if(t == 'text' && name.indexOf("score") != -1 && name.indexOf("total") < 0)
			{
				total += parseInt(tags[i].value);
			}
		}
		document.check.total_score.value = total;
	}
	//totalScore();
</script>