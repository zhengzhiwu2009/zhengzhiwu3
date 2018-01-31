<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.lore.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>中国证券业协会远程培训系统</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<%
	String paperId = request.getParameter("paperId");
 %>
 <%!
 	String fixnull(String str) {
		if (str == null || str.equals("null"))
			str = "";
		return str;

	}
  %>
<SCRIPT>
function cfmdel(link)
{
	if(confirm("您确定要删除这道题目吗？"))
		window.navigate(link);
}

function DetailInfo(type,id,id1)
{
	if(type=='YUEDU')
		window.open('store_question_comprehension_info1.jsp?id='+id +'&id1='+id1,'');
	else 
		window.open('store_question_info.jsp?id='+id,'');
	
}
function deleteSubmit()
{
	var flag = false;
	var form = document.forms['add'];
	
	for (var i = 0 ; i < form.elements.length; i++) {
		if ((form.elements[i].type == 'checkbox')) {
			if ( form.elements[i].checked == true) {
				flag = true;
				break;
			}
		}
	}
	if(flag == false){
		alert('请选择您要删除的题目。');
		return;
	}
	
	document.add.action="testpaper_del2_bypolicy_batch.jsp?paperid=<%=paperId%>";
	document.add.submit();
}
  function doCommit()
  {
  	document.add.submit();
  }
</SCRIPT>
</head>

<body leftmargin="0" topmargin="0" class="scllbar">
<%
	String pageInt = request.getParameter("pageInt");
	HashMap paperQuestions = (HashMap) session.getAttribute("paperQuestions");
	if(paperQuestions==null)
	{
		paperQuestions = new HashMap();
		paperQuestions.put(TestQuestionType.DANXUAN,new ArrayList());
		paperQuestions.put(TestQuestionType.DUOXUAN,new ArrayList());
		paperQuestions.put(TestQuestionType.PANDUAN,new ArrayList());
		//paperQuestions.put(TestQuestionType.TIANKONG,new ArrayList());
		//	paperQuestions.put(TestQuestionType.WENDA,new ArrayList());
		//paperQuestions.put(TestQuestionType.YUEDU,new ArrayList());
	}
	//判断去掉填空和阅读
	if(paperQuestions.get(TestQuestionType.TIANKONG)!=null)
		paperQuestions.remove(TestQuestionType.TIANKONG);
		
	Set set = paperQuestions.keySet();

	//wuhao
	Object[] key=paperQuestions.keySet().toArray();
	Arrays.sort(key);
	
	
	
	//session.removeAttribute("paperQuestions");
%>
<table width="489" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td height="42" background="images/st_05.gif" style="padding-left:53px;padding-top:8px" class="text3">题型选择添加</td>
  </tr>
<form name='add' action='testpaper_addexe_bypolicy.jsp' method='post' class='nomargin' onsubmit="">
<input type="hidden" name="paperId" value=<%=paperId%>>
  <tr> 
    <td valign="top" background="images/st_06.gif" > <table height="390" width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td valign="top" style="padding-left:30px">
				          <table width="100%" border="0" cellspacing="0" cellpadding="0">
				          <%
				          	String field = "";
				          	String typename = "";
				          	List questionList = null;
				          	int count = 1;
				          	int allScore =0;
				          	for(int jj=0;jj<key.length;jj++){
				          	field=(String)key[jj];	
				          	typename=TestQuestionType.typeShow(field);
				          	
				          //	for(Iterator it = set.iterator();it.hasNext();)
				         // 	{
				          	//	field = (String)it.next();
				          //		System.out.println(field+"================");
				          //		typename = TestQuestionType.typeShow(field);
				          		questionList = (List)paperQuestions.get(field);
				          %>
				            <tr>
				              <td align="center">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
				                  <tr valign="bottom" class="mc1"> 
				                    <td colspan="2" valign="bottom" width=70%><%=typename%>：</td>
				                    <td colspan="2" width=30% ><a href="testpaper_add2_bypolicy.jsp?field=<%=field%>&paperId=<%=paperId%>" title="提交" class="button"><span unselectable="on"><font color="red">添加试题</font></span></a></span></td>
				                  </tr>
				                  <%
				                  	for(int i=0;i<questionList.size();i++)
				                  	{
				                  		PaperQuestion question = (PaperQuestion)questionList.get(i);
				                  		allScore += Integer.parseInt(question.getReferenceScore());
				                  %>
				                  <tr valign="bottom" class="mc1"> 
				                  	<td width="15%" align="right" valign="middle">
										<input type='checkbox' class='checkbox'
											name='<%=field + "Child"%>' value='<%=i%>'	id='<%=fixnull(question.getId())%>' /></td>
				                    <td width="5%" align="right" valign="bottom" style="padding-right:8px"><%=count++%>.</td>
				                    <td width="50%" valign="bottom">
				                    <a href="" title="提交" class="button" onClick="DetailInfo('<%=question.getType()%>','<%=question.getId()%>','<%=i%>'); return false;">
				                    <span unselectable="on"><%=question.getTitle()%></span><font color=red>(引用：<%=question.getNum()%>次)</font></a></span> 
				                    </td>
				                    <td valign="bottom" width="15%">
				                      &nbsp;</span></td>
				                    <td width="15%" valign="bottom">分值：<input type="hidden" name="<%=question.getId()%>score" size="2" style="text-align:center" value=<%=question.getReferenceScore()%>>(<%=question.getReferenceScore()%>) </td>
				                  </tr>
				                  <%
				                  	}
				                  %>				                  
				                </table></td>
							  </tr>
									<%
									}
									%>
							<tr><td colspan="5"><font color=red>总分值：<%=allScore %></font> </td></tr>
							</table>
              </tr>
              <tr> 
			      <td><table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0">
			        <tr>
			          <td height="4"><img src="../images/page_bottomSlip.gif" width="100%" height="2"></td>
			        </tr>
			        <tr>
			          <td align="center" valign="middle">
			          <% if(allScore ==100){%>
			          <a href="" onclick="return false;"><img src="images/OK.gif" width="80" height="24" border="0" onclick="doCommit()"></a>
			          <%} else { %>
			          	<font size="2">题目总分值为100分才可提交</font>
			          <%} %>
			          	&nbsp;&nbsp;&nbsp;
						<img src="images/Delete.gif" width="80" height="24"
							onclick="deleteSubmit()">
						&nbsp;&nbsp;&nbsp;
						<img src="images/Return.gif" width="80" height="24"
							onclick="window.location.href='testpaper_list.jsp?pageInt=<%=pageInt%>'">
			          </td>
			        </tr>
			      </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td><img src="images/st_07.gif" width="489" height="15"></td>
  </tr>
</form>
</table>
</body>
</html>
