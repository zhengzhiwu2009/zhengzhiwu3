<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.lore.*"%>
<%@ page import="com.whaty.platform.test.TestManage,com.whaty.util.string.encode.*" %>
<%@ include file="../pub/priv.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>阅读理解--单选题添加</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">

</head>
<%
	StoreQuestion sq = (StoreQuestion)session.getAttribute("storeQuestion");
	String loreId = request.getParameter("loreId");
	String loreDirId = request.getParameter("loreDirId");
	String type = request.getParameter("branch_type");
	String referencetime = request.getParameter("referencetime");
	String referencescore = request.getParameter("referencescore");
	String title = request.getParameter("title");
	String[] options = request.getParameterValues("multi_options");
	String[] answers = request.getParameterValues("multi_answer");
	String questionbody = request.getParameter("body");
	if(answers == null)
	{
		out.println("<script>alert('没有选择正确答案!');window.history.back();</script>");
		return;
	}
	String xml = sq.getQuestionCore();
	xml = xml + "<subquestion><type>" + HtmlEncoder.encode(type) + "</type><title>" + HtmlEncoder.encode(title) + "</title><referencetime>" + HtmlEncoder.encode(referencetime) 
		+ "</referencetime><referencescore>" + HtmlEncoder.encode(referencescore) + "</referencescore><body>" + HtmlEncoder.encode(questionbody) 
		+ "</body><select>";
	for(int i=0; options!=null && i<options.length; i++) {
		int charCode = i + 65;		//选项字母的ASCII码
		String index = String.valueOf((char)charCode);	//将ASCII码转换成字母
		xml = xml + "<item><index>" + HtmlEncoder.encode(index) + "</index><content>" + HtmlEncoder.encode(options[i]) + "</content></item>";
	}
	
	String answer = "";
	for(int i=0; i<answers.length; i++) {
		answer = answer + answers[i] + "|";
	}
	if(answer.length() > 0)
		answer = answer.substring(0, answer.length()-1);
	
	xml = xml + "</select><answer>" + HtmlEncoder.encode(answer) + "</answer></subquestion>";
	sq.setQuestionCore(xml);
	session.setAttribute("storeQuestion", sq);
%>
<body leftmargin="0" topmargin="0">

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td height="35" background="images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3"></td>
  </tr>
  <tr> 
    <td>
    <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="3%" align="center">&nbsp;</td>
          <td width="15%" class="text6"></td>
          <td width="45%"></td>
          <td width="3%"></td>
          <td width="10%" class="text6"></td>
          <td width="4%" class="text6"></td>
          <td class="text6"></td>
        </tr>
        <tr> 
          <td height="3" colspan="7" align="center" background="images/ct_08.gif"> 
          </td>
        </tr>
        <tr> 
          <td align="center">&nbsp;</td>
          <td class="text6"></td>
          <td></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td></td>
          <td class="text6"></td>
        </tr>
        <tr> 
          <td height="3" colspan="7" align="center" background="images/ct_08.gif"> 
          </td>
        </tr>
        <tr> 
          <td align="center">&nbsp;</td>
          <td class="text6"></td>
          <td class="text6" align="center">[<a href="store_question_comprehension_branch.jsp?loreId=<%=loreId%>&loreDirId=<%=loreDirId%>">继续添加</a>]&nbsp;[<a href="store_question_comprehension_addexe.jsp?loreId=<%=loreId%>&loreDirId=<%=loreDirId%>">结束返回</a>]</td>
          <td class="text6"></td>
          <td>&nbsp;</td>
          <td></td>
          <td class="text6"></td>
        </tr>
        <tr> 
          <td height="3" colspan="7" align="center" background="images/ct_08.gif"> 
          </td>
        </tr>
        <tr> 
          <td align="center">&nbsp;</td>
          <td class="text6"></td>
          <td class="text6"></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td></td>
          <td class="text6"></td>
        </tr>
        <tr> 
          <td height="3" colspan="7" background="images/ct_08.gif"> </td>
        </tr>
        <tr> 
          <td align="center">&nbsp;</td>
          <td class="text6"></td>
          <td class="text6"></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td></td>
          <td class="text6"></td>
        </tr>
        <tr> 
          <td height="3" colspan="7" align="center" background="images/ct_08.gif"> 
          </td>
        </tr>
      </table>
      </td>
  </tr>
  <tr> 
    <td height="35" background="images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3"></td>
  </tr>
</table>
</body>

</html>
