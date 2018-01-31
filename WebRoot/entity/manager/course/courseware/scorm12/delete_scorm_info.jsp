
<%@page import="com.whaty.platform.standard.scorm.util.ScormConstant"%><%		/*---------------------------------------------
		 Function Description:	
		 Editing Time:	
		 Editor: chenjian
		 Target File:	
		 	 
		 Revise Log
		 Revise Time:  Revise Content:   Reviser:
		 -----------------------------------------------*/
%>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*,com.whaty.platform.training.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@ include file="./readerror.jsp"%>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>培训平台－后台管理</title>
<link href="<%=request.getContextPath()%>/work/manager/css/style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="../../../js/frame.js"></script>
<script>
function checkValues()
   {
      if ( courseInfo.title.value == "" || 
           courseInfo.coursezipfile.value == "" ) 
      {
         alert( "You must enter a value for all items" );
         return false;
      }
      
      courseInfo.theZipFile.value = courseInfo.coursezipfile.value;
      return true;
   }
</script>
</head>
<%
	String courseware_id=fixnull(request.getParameter("courseware_id"));
	String courseware_name=fixnull(request.getParameter("courseware_name"));
	String scormType = fixnull(request.getParameter("scormType"));
	
	dbpool db = new dbpool();
	MyResultSet rs = null;
	String sql= "" ;
	int jls = 0;
	sql = "select 1 jls from pe_bzz_tch_course t,enum_const ec where t.flag_isvalid=ec.id and ec.code='1' and t.code='"+courseware_id+"'";
	jls = db.countselect(sql);
	if(jls>0){
		%>
			<script type="text/javascript">
				alert('请先取消发布状态再删除课件！');
				window.close();
			</script>
		<%
	}else{
		String sqlCount = "select distinct scorm_type from scorm_sco_launch where course_id='"+courseware_id+"'" ;
		if(db.countselect(sqlCount)>1) {
			String sqlDelete = "delete from scorm_sco_launch where course_id='"+courseware_id+"'";// and scorm_type='"+scormType+"'" ;
			int suc = db.executeUpdate(sqlDelete);
			if(suc>0) {
				%>
				<script type="text/javascript">
					alert('删除成功');
					window.opener.location.reload();
					window.close();
				</script>
				<%
				
			} else {
				%>
				<script type="text/javascript">
					alert('删除失败');
					window.close();
				</script>
				<%
			}
			return;
		}
		sql="select e.percent\n" + 
			"  from pe_bzz_tch_courseware   a,\n" + 
			"       pe_bzz_tch_course       b,\n" + 
			"       pr_bzz_tch_opencourse   d,\n" + 
			"       training_course_student e\n" + 
			" where a.code = '"+courseware_id+"'\n" + 
			"   and a.fk_course_id = b.id\n" + 
			"   and d.fk_course_id = b.id\n" + 
			"   and e.course_id = d.id and to_number(e.percent)>0";
		if(db.countselect(sql)>0)
		{
		%>
		<script>
		if(confirm("该课件下已有学员学习记录，是否需要删除？"))
		{
			window.location.href="delete_scorm_info1.jsp?courseware_id=<%=courseware_id%>";
		}
		else{
			window.close();
		}
		</script>
		<%
		return;
		} 
		else
		{
		%>
		<script>
		window.location.href="delete_scorm_info1.jsp?courseware_id=<%=courseware_id%>";
		</script>
		<%
		}	
		
	}
	db=null;
 %>
</html>

