<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../pub/priv.jsp"%>
<%
	
	try {
		String id = request.getParameter("id");
		String[] user_answers = request.getParameterValues("index");
		String user_answer = "";
		for(int i=0;i<user_answers.length;i++)
		{
			user_answer+="|"+user_answers[i];
		}
		if(user_answer.length()>0)
			user_answer = user_answer.substring(1);
		HashMap userAnswer = (HashMap)session.getAttribute("UserAnswer");
		if(userAnswer!=null)
		{
			userAnswer.put(id,user_answer);
		}
		session.setAttribute("UserAnswer",userAnswer);
		
		String qNum = request.getParameter("qNum");
		String dire = request.getParameter("direction");
		List qList = (ArrayList)session.getAttribute("qList");
		String link = "";
		
			
			if(dire!=null && dire.equals("next"))
			{
				if(Integer.parseInt(qNum)<qList.size()-1)
				link = (String)qList.get(Integer.parseInt(qNum)+1);
				else
				link = (String)qList.get(Integer.parseInt(qNum));
			}
			if(dire!=null && dire.equals("last"))
			{
				if(Integer.parseInt(qNum)>0)
				link = (String)qList.get(Integer.parseInt(qNum)-1);
				else
				link = (String)qList.get(Integer.parseInt(qNum));
			}
			if(dire!=null && dire.equals("final"))
			{
				String paperId = (String)session.getAttribute("paperId");
				%>
<script type="text/javascript">
	//alert("答案保存完毕！");
	parent.location.href = "testpaper_result.jsp?id=<%=paperId%>";
</script>			
				<%
				return;
			}
%>

<script type="text/javascript">
	//alert("答案保存完毕！");
	location.href = "<%=link%>";
</script>
<%		
	} catch (Exception e) {
		out.print(e.toString());
	}
%>