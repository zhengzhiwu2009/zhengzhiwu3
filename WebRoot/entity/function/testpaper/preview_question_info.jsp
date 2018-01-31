<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*,com.whaty.util.string.encode.*"%>
<jsp:directive.page import="java.net.URLDecoder"/>
<%@ include file="../pub/priv.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>问答题</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
 
</head>

<body>
<table width="98%" border="0" cellspacing="0" cellpadding="0">
<%
	session.setAttribute("openCourseId",openCourse.getId());
	
    HashMap standardAnswer = (HashMap)session.getAttribute("StandardAnswer");

	List singleList = (ArrayList)session.getAttribute("singleList");
	List multiList = (ArrayList)session.getAttribute("multiList");
	List judgeList = (ArrayList)session.getAttribute("judgeList");
	List blankList = (ArrayList)session.getAttribute("blankList");
	List answerList = (ArrayList)session.getAttribute("answerList");
	List comprehensionList = (ArrayList)session.getAttribute("comprehensionList");
	int qNum=1;
	int a=0;
	String da="";
	try {
		if(singleList!=null && singleList.size()>0)
		{
			a++;
			switch (a)
		    {
		    	case 1: da="一";break;
		   		case 2: da="二";break;
		    	case 3: da="三";break;
		    	case 4: da="四";break;
		    	case 5: da="五";break;
		    	default:break;
	    	}
%>
  <tr> 
    <td height="42" style="padding-left:23px;padding-top:8px" class="text3"><font size='5'><%=da%>、单项选择题</font></td>
  </tr>
<%
			for(int t=0;t<singleList.size();t++)
			{
				PaperQuestion pq = (PaperQuestion)singleList.get(t);
				String coreXML = pq.getQuestionCore();
				String id = pq.getId();
				List list = XMLParserUtil.parserSingleMulti(coreXML);
				String body = HtmlEncoder.decode((String)list.get(0));
				String standard_answer = HtmlEncoder.decode((String)list.get(list.size()-1));
				standardAnswer.put(id,standard_answer);
%>
  <tr> 
    <td valign="top" style="padding-left:23px;padding-top:8px"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td width="100%" align="left" class="content1"><font size='4'><%=(qNum++)+". "+body%></font></td>
              </tr>
              <tr> 
                <td width="100%" valign="top" align="left" style="padding-left:10px">
                <table width="600px" border="0" cellspacing="0" cellpadding="0">
                	<%
                		for(int i=1; i<list.size()-2; i=i+2) {
                			String index = (String)list.get(i);
                			String content =  HtmlEncoder.decode((String)list.get(i+1));
                	%>
                    <tr>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td> 
                      <td width="40" class="mc1" align="left" style="padding-top:5px;margin-top:0px" valign="top"><input type="radio"></td>
                      <td width="550" class="mc1" align="left"><div><%=index+". "+content %></td></div>
                    </tr>
                    <%
                    	}
                    %>
                    
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
<%
			}
		}

		if(multiList!=null && multiList.size()>0)
		{
			a++;
			switch (a)
		    {
		    	case 1: da="一";break;
		   		case 2: da="二";break;
		    	case 3: da="三";break;
		    	case 4: da="四";break;
		    	case 5: da="五";break;
		    	default:break;
	    	}
%>
  <tr> 
    <td height="42" style="padding-left:23px;padding-top:8px" class="text3"><font size='5'><%=da%>、多项选择题</font></td>
  </tr>
<%
			for(int t=0;t<multiList.size();t++)
			{
				
				PaperQuestion pq = (PaperQuestion)multiList.get(t);
				String coreXML = pq.getQuestionCore();
				String id = pq.getId();
				List list = XMLParserUtil.parserSingleMulti(coreXML);
				String body = HtmlEncoder.decode((String)list.get(0));
				String standard_answer = HtmlEncoder.decode((String)list.get(list.size()-1));
				standardAnswer.put(id,standard_answer);
%>
  <tr> 
    <td valign="top" style="padding-left:23px;padding-top:8px"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td width="100%" align="left" class="content1"><font size='4'><%=(qNum++)+". "+body%></font></td>
              </tr>
              <tr> 
                <td width="100%" valign="top" align="left" style="padding-left:10px">
                <table width="600" border="0" cellspacing="0" cellpadding="0">
                	<%
                		for(int i=1; i<list.size()-2; i=i+2) {
                			String index = (String)list.get(i);
                			String content = HtmlEncoder.decode((String)list.get(i+1));
                	%>
                    <tr> 
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                      <td width="40" class="mc1" style="padding-top:5px;margin-top:0px" valign="top"><input type="checkbox" name="multi_<%=id %>" value="<%=index%>"></td>
                      <td width="550" class="mc1"><%=index+". "+content %></td>
                    </tr>
                    <%
                    	}
                    %>
                    
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
<%
			}
		}

		if(judgeList!=null && judgeList.size()>0)
		{
			a++;
			switch (a)
		    {
		    	case 1: da="一";break;
		   		case 2: da="二";break;
		    	case 3: da="三";break;
		    	case 4: da="四";break;
		    	case 5: da="五";break;
		    	default:break;
	    	}
%>
  <tr> 
    <td height="42" style="padding-left:23px;padding-top:8px" class="text3"><font size='5'><%=da%>、判断题</font></td>
  </tr>
<%
			for(int t=0;t<judgeList.size();t++)
			{
				PaperQuestion pq = (PaperQuestion)judgeList.get(t);
				String coreXML = pq.getQuestionCore();
				String id = pq.getId();
				List list = XMLParserUtil.parserSingleMulti(coreXML);
				String body = HtmlEncoder.decode((String)list.get(0));
				String standard_answer = HtmlEncoder.decode((String)list.get(list.size()-1));
				standardAnswer.put(id,standard_answer);
%>
  <tr> 
    <td valign="top" style="padding-left:23px;padding-top:8px"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td width="100%" align="left" class="content1"><font size='4' ><%=(qNum++)+". "+body%></font></td>
              </tr>
              <tr> 
                <td width="100%" valign="top" align="left" style="padding-left:10px">
                <table width="600" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                      <td width="40" class="mc1" style="padding-top:5px;margin-top:0px" valign="top"><input type="radio" name="judge_<%=id %>" value="1"></td>
                      <td width="550" class="mc1">正确</td>
                    </tr>
                    <tr> 
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                      <td width="40" class="mc1" style="padding-top:5px;margin-top:0px" valign="top"><input type="radio" name="judge_<%=id %>" value="0"></td>
                      <td width="550" class="mc1">错误</td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
<%
			}
		}

		if(blankList!=null && blankList.size()>0)
		{
			a++;
			switch (a)
		    {
		    	case 1: da="一";break;
		   		case 2: da="二";break;
		    	case 3: da="三";break;
		    	case 4: da="四";break;
		    	case 5: da="五";break;
		    	default:break;
	    	}
%>
  <tr> 
    <td height="42" style="padding-left:23px;padding-top:8px" class="text3">填空题</td>
  </tr>
<%
			for(int t=0;t<blankList.size();t++)
			{
				PaperQuestion pq = (PaperQuestion)blankList.get(t);
				String coreXML = pq.getQuestionCore();
				String id = pq.getId();
				List list = XMLParserUtil.parserSingleMulti(coreXML);
				String body = HtmlEncoder.decode((String)list.get(0));
				String standard_answer = HtmlEncoder.decode((String)list.get(list.size()-1));
				standardAnswer.put(id,standard_answer);
%>
  <tr> 
    <td valign="top" style="padding-left:23px;padding-top:8px"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td width="100%" align="left" class="content1"><%=(qNum++)+". "+body%></td>
              </tr>
              <tr> 
                <td width="100%" valign="top" align="left" style="padding-left:10px">
                <table width="600" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                      <td width="0%" class="mc1"></td>
                      <td width="100%" class="mc1"><textarea name="blank_<%=id %>"></textarea></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
<%
			}
		}

		if(answerList!=null && answerList.size()>0)
		{
			a++;
			switch (a)
		    {
		    	case 1: da="一";break;
		   		case 2: da="二";break;
		    	case 3: da="三";break;
		    	case 4: da="四";break;
		    	case 5: da="五";break;
		    	default:break;
	    	}
%>
  <tr> 
    <td height="42" style="padding-left:23px;padding-top:8px" class="text3"><%=da%>、问答题</td>
  </tr>
<%
			for(int t=0;t<answerList.size();t++)
			{
				PaperQuestion pq = (PaperQuestion)answerList.get(t);
				String coreXML = pq.getQuestionCore();
				String id = pq.getId();
				List list = XMLParserUtil.parserSingleMulti(coreXML);
				String body = HtmlEncoder.decode((String)list.get(0));
				String standard_answer = HtmlEncoder.decode((String)list.get(list.size()-1));
				standardAnswer.put(id,standard_answer);
%>
  <tr> 
    <td valign="top" style="padding-left:23px;padding-top:8px"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td width="100%" align="left" class="content1"><%=(qNum++)+". "+body%></td>
              </tr>
              <tr> 
                <td width="100%" valign="top" align="left" style="padding-left:10px">
                <table width="600" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                     <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                      <td width="0%" class="mc1"></td>
                      <td width="30%" class="mc1"><textarea name="answer_<%=id %>"></textarea></td>
                      <td style="padding-left:5px"></td>
					  <td width="70%" valign="bottom" style="padding-left:5px"></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
<%
			}
		}

		if(comprehensionList!=null && comprehensionList.size()>0)
		{
			a++;
			switch (a)
		    {
		    	case 1: da="一";break;
		   		case 2: da="二";break;
		    	case 3: da="三";break;
		    	case 4: da="四";break;
		    	case 5: da="五";break;
		    	default:break;
	    	}
%>
  <tr> 
    <td height="42" style="padding-left:23px;padding-top:8px" class="text3"><%=da%>、案例分析题</td>
  </tr>
<%
			HashMap Score = (HashMap)session.getAttribute("Score");
			HashMap Title = (HashMap)session.getAttribute("Title");
			for(int t=0;t<comprehensionList.size();t++)
			{
				PaperQuestion pq = (PaperQuestion)comprehensionList.get(t);
				String id = pq.getId();
				String questionCoreXml = pq.getQuestionCore();
				List coreList = XMLParserUtil.parserComprehension(questionCoreXml);
				String bodyString = HtmlEncoder.decode((String)coreList.get(0));
				List scoreList = new ArrayList();
				List titleList = new ArrayList();
				List standard_answer = new ArrayList();
				standard_answer.add("");
				titleList.add(pq.getTitle());
				scoreList.add(pq.getScore());
%>
	<INPUT type="hidden" name="comp_<%=id %>_totalNum" value="<%=coreList.size()-1%>">	
  <tr> 
    <td valign="top" style="padding-left:23px;padding-top:8px"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td class="content1" width="100%"><%=(qNum++)+". "+bodyString%></td>
              </tr>
              <%
				int i = 1;
				for(; i<coreList.size(); i++) {
			  		List subList = (List)coreList.get(i);
			  		String subType = HtmlEncoder.decode((String)subList.get(0));
			  		String subTitle = HtmlEncoder.decode((String)subList.get(1));
			  		String subTime = HtmlEncoder.decode((String)subList.get(2));
			  		String subScore = HtmlEncoder.decode((String)subList.get(3));
			  		String subBody = HtmlEncoder.decode((String)subList.get(4));
			  		String subAnswer = HtmlEncoder.decode((String)subList.get(subList.size()-1));
			  		if(subType.equals(TestQuestionType.PANDUAN))
			  		{
			  			if(subAnswer.equals("1"))
			  				subAnswer = "正确";
			  			else
			  				subAnswer = "错误";
			  		}	
			  		standard_answer.add(subAnswer);
			  		titleList.add(subTitle);
			  		scoreList.add(subScore);
			  		
			  		if(subType.equals(TestQuestionType.DANXUAN)) {
			%>
              <tr> 
                <td width="100%" valign="top" style="padding-left:10px">
                <table width="600" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td width="100%" class="mc1"><%=i%>．<%=subTitle%></td>
                	</tr>
                	<tr> 
					  <td width="100%" class="mc1">&nbsp;&nbsp;<%=subBody%></td>
                	</tr>
					<tr> 
						<td width="100%" valign="top" style="padding-left:10px">
						<table width="600" border="0" cellspacing="0" cellpadding="0">
			              	<%
					        	for(int j=5; j<subList.size()-2; j=j+2) {
									String index = (String)subList.get(j);
									String content = HtmlEncoder.decode((String)subList.get(j+1));
					        %>
							<tr> 
						      <td width="40" class="mc1" style="padding-top:5px;margin-top:0px" valign="top">
						      	<input type="radio" name="comp_<%=id %>_<%=i%>" value="<%=index%>">
						      </td>
						      <td width="550" class="mc1"><%=index+". "+content %></td>
						    </tr>
					        <%
					        	}
					        %>
						</table>
						</td>
					</tr>
                </table>
                </td>
              </tr>
				<%
					} else if(subType.equals(TestQuestionType.DUOXUAN)) {
				%>
              <tr> 
                <td width="100%" valign="top" style="padding-left:10px">
                <table width="600" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td width="100%" class="mc1"><%=i%>．<%=subTitle%></td>
                	</tr>
                	<tr> 
					  <td width="100%" class="mc1">&nbsp;&nbsp;<%=subBody%></td>
                	</tr>
					<tr> 
						<td width="100%" valign="top" style="padding-left:10px">
						<table width="600" border="0" cellspacing="0" cellpadding="0">
			              	<%
					        	for(int j=5; j<subList.size()-2; j=j+2) {
									String index = (String)subList.get(j);
									String content = HtmlEncoder.decode((String)subList.get(j+1));
					        %>
							<tr> 
							<td>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
						      <td width="40" class="mc1" style="padding-top:5px;margin-top:0px" valign="top">
						      	<input type="checkbox" name="comp_<%=id %>_<%=i%>" value="<%=index%>">
						      </td>
						      <td width="550" class="mc1"><%=index+". "+content %></td>
						    </tr>
					        <%
					        	}
					        %>
						</table>
						</td>
					</tr>
                </table>
                </td>
              </tr>
				<%
					} else if(subType.equals(TestQuestionType.PANDUAN)) {
				%>
              <tr> 
                <td width="100%" valign="top" style="padding-left:10px">
                <table width="600" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td width="100%" class="mc1"><%=i%>．<%=subTitle%></td>
                	</tr>
                	<tr> 
					  <td width="100%" class="mc1">&nbsp;&nbsp;<%=subBody%></td>
                	</tr>
					<tr> 
						<td width="100%" valign="top" style="padding-left:10px">
						<table width="600" border="0" cellspacing="0" cellpadding="0">
							<tr> 
							<td>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
						      <td width="100%" class="mc1" style="padding-top:5px;margin-top:0px" valign="top">
						      	<input type="radio" name="comp_<%=id %>_<%=i%>" value="正确">正确&nbsp;<input type="radio" name="comp_<%=id %>_<%=i%>" value="错误">错误
						      </td>
						    </tr>
						</table>
						</td>
					</tr>
                </table>
                </td>
              </tr>
				<%
					} else if(subType.equals(TestQuestionType.TIANKONG)) {
				%>
              <tr> 
                <td width="100%" valign="top" style="padding-left:10px">
                <table width="600" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td width="100%" class="mc1"><%=i%>．<%=subTitle%></td>
                	</tr>
                	<tr> 
					  <td width="100%" class="mc1">&nbsp;&nbsp;<%=subBody%></td>
                	</tr>
					<tr> 
						<td width="100%" valign="top" style="padding-left:10px">
						<table width="600" border="0" cellspacing="0" cellpadding="0">
							<tr> 
							<td>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
						      <td width="100%" class="mc1">
						      	<textarea name="comp_<%=id %>_<%=i%>" cols="40" rows="5"></textarea>
						      </td>
						    </tr>
						</table>
						</td>
					</tr>
                </table>
                </td>
              </tr>
				<%
					} else if(subType.equals(TestQuestionType.WENDA)) {
				%>
              <tr> 
                <td width="100%" valign="top" style="padding-left:10px">
                <table width="600" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td width="100%" class="mc1"><%=i%>．<%=subTitle%></td>
                	</tr>
                	<tr> 
					  <td width="100%" class="mc1">&nbsp;&nbsp;<%=subBody%></td>
                	</tr>
					<tr> 
						<td width="100%" valign="top" style="padding-left:10px">
						<table width="600" border="0" cellspacing="0" cellpadding="0">
							<tr>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td> 
						      <td width="100%" class="mc1">
						      	<textarea name="comp_<%=id %>_<%=i%>" cols="40" rows="5"></textarea>
						      </td>
						    </tr>
						</table>
						</td>
					</tr>
                </table>
                </td>
              </tr>
              	<%
              	}
              }
              	standardAnswer.put(id,standard_answer);
              	Score.put(id,scoreList);
              	Title.put(id,titleList);
				session.setAttribute("Score",Score);
				session.setAttribute("Title",Title);
				session.setAttribute("StandardAnswer",standardAnswer);
              	%>
              </table></td></tr>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
<%
			}
		}
	} catch (Exception e) {
		//out.print(e.toString());
		
	}
%>
   
</table>
</body>
</html>
