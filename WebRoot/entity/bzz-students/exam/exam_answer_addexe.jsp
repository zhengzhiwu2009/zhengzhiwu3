<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>

<%@ include file="../pub/priv.jsp"%>
<%!
   String fixnull(String str){ 
   		if(str==null || str.equals("null") || str.equals("")){
   			str= "";
   			return str;
   		}
   		return str;
   }
%>
<%
	if(student == null  || !"student".equals(userType))
	{
%>
 <script>
	alert("请以学生身份登陆");
	window.history.back();
</script>	
<%
	}
	//String title = request.getParameter("title");
	String body = request.getParameter("body");
	String questionId = request.getParameter("question_id");
	String title = request.getParameter("title");
	String name = request.getParameter("name");
	
	String zong_type=fixnull(request.getParameter("zong_type"));
	String zong_title=java.net.URLDecoder.decode(fixnull(request.getParameter("zong_title")),"UTF-8");
	String zong_pageInt = request.getParameter("zong_pageInt");

	String publish_id = fixnull(student.getId());
	String publish_name = fixnull(student.getName());
	String publish_type = fixnull(student.getLoginType());	
	if(publish_type == null || publish_type.trim().equals(""))
		publish_type = "student";

	int ret ;
		    
	try
{
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
	
	ret = interactionManage.addExamAnswer(questionId, body, publish_id, publish_name, publish_type);
			
}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
	if (ret == 1) {
%>
		<script language="javascript">
			alert("成功添加！");
			window.navigate("/entity/bzz-students/exam/student_exam_tutor_y.jsp?&zong_title=<%=java.net.URLEncoder.encode(zong_title,"UTF-8") %>&zong_type=<%=zong_type %>&pageInt=<%=zong_pageInt%>");
		</script>
<%
	} else {
%>
		<script language="javascript">
			alert("添加不成功！");
			window.history.back(-1);
		</script>
<%
	}
%>