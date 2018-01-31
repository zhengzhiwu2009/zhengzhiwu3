<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.entity.bean.*" %>
<%@ page import = "com.whaty.platform.database.oracle.*" %>
<%@page import="com.whaty.platform.database.oracle.*,java.io.*,java.util.*"%>
<%@page import = "com.whaty.platform.database.oracle.*,java.util.*,java.text.*"%>

	<%!String fixnull(String str) { 
		if (str == null || str.equals("null"))
			str = "";
		return str;
	}%>
<%
    //获取源学期和目标学期参数
    String src_batch_id=fixnull(request.getParameter("src_batch_id"));
	String tar_batch_id=fixnull(request.getParameter("tar_batch_id"));

    List peBzzTchCourses = (List)session.getAttribute("peBzzTchCourses");
    
    dbpool db = new dbpool();
    MyResultSet rs =null;
    MyResultSet rs_1 =null;
	int homecount=0;
	int questioncount=0;
	 
  	for(int i=0;i<peBzzTchCourses.size();i++)
  	{
  		//所选课程ID
  		String course_id=fixnull(((PeBzzTchCourse)peBzzTchCourses.get(i)).getId());
  		
  		//根据课程ID查找作业列表
  		String bBatchId="";
  		if(src_batch_id.equals("ff8080812253f04f0122540a58000004")||src_batch_id.equals("52cce2fd2471ddc30124764980580131")||src_batch_id.equals("ff8080812824ae6f012824b0a89e0008"))
	    {
	    	bBatchId=" and t.batch_id is null";
	    }
	    else
	    {
	    	bBatchId=" and t.batch_id='"+src_batch_id+"'";
	    }
		String sql="select id from test_homeworkpaper_info t where t.group_id='"+course_id+"'"+bBatchId;
		
		rs=db.executeQuery(sql);
		while(rs!=null && rs.next())
		{
			String testpaper_id=fixnull(rs.getString("id"));
			String sql_pre = "select to_char(s_test_paper_info.nextval) as id from dual";
			MyResultSet rs_pre = db.executeQuery(sql_pre);
			String testid="";
			if (rs_pre != null && rs_pre.next())
			{
				testid = rs_pre.getString("id");
			}
			db.close(rs_pre);
			//复制作业基本信息表
			String update_sql=
				"insert into test_homeworkpaper_info\n" +
				"  select '"+testid+"',\n" + 
				"         thi.title,\n" + 
				"         thi.creatuser,\n" + 
				"         sysdate,\n" + 
				"         thi.status,\n" + 
				"         thi.note,\n" + 
				"         thi.comments,\n" + 
				"         thi.startdate,\n" + 
				"         thi.enddate,\n" + 
				"         thi.group_id,\n" + 
				"         thi.type,\n" + 
				"         '"+tar_batch_id+"'\n" + 
				"    from test_homeworkpaper_info thi\n" + 
				"   where thi.id = '"+testpaper_id+"'";
			homecount+=db.executeUpdate(update_sql);
			
			
			//复制该作业下的题目信息
			//由于存在long型数据插入，不能直接写到sql中，需要先查出来，再插入操作
			String  q_sql=
				"select tpi.creatuser,\n" +
				"       to_char(tpi.creatdate,'yyyy-mm-dd') as creatdate,\n" + 
				"       tpi.diff,\n" + 
				"       tpi.questioncore,\n" + 
				"       tpi.title,\n" + 
				"       tpi.serial,\n" + 
				"       tpi.score,\n" + 
				"       tpi.lore,\n" + 
				"       tpi.cognizetype,\n" + 
				"       tpi.purpose,\n" + 
				"       tpi.referencescore,\n" + 
				"       tpi.referencetime,\n" + 
				"       tpi.studentnote,\n" + 
				"       tpi.teachernote,\n" + 
				"       tpi.type,\n" + 
				"       tpi.user_id\n" + 
				"  from test_paperquestion_info tpi\n" + 
				" where tpi.testpaper_id = '"+testpaper_id+"'";
			rs_1 = db.executeQuery(q_sql);
			while(rs_1!=null && rs_1.next())
			{
				String creatuser = fixnull(rs_1.getString("creatuser"));
				String diff = fixnull(rs_1.getString("diff"));
				String questioncore = fixnull(rs_1.getString("questioncore"));
				String title = fixnull(rs_1.getString("title"));
				String serial = fixnull(rs_1.getString("serial"));
				String score = fixnull(rs_1.getString("score"));
				String lore = fixnull(rs_1.getString("lore"));
				String cognizetype = fixnull(rs_1.getString("cognizetype"));
				String purpose = fixnull(rs_1.getString("purpose"));
				String referencescore = fixnull(rs_1.getString("referencescore"));
				String referencetime = fixnull(rs_1.getString("referencetime"));
				String studentnote = fixnull(rs_1.getString("studentnote"));
				String teachernote = fixnull(rs_1.getString("teachernote"));
				String type = fixnull(rs_1.getString("type"));
				String user_id = fixnull(rs_1.getString("user_id"));
				String creatdate = fixnull(rs_1.getString("creatdate"));
				
				String question_sql=
					"insert into test_paperquestion_info\n" +
					"  (id,\n" + 
					"   title,\n" + 
					"   creatuser,\n" + 
					"   creatdate,\n" + 
					"   diff,\n" + 
					"   serial,\n" + 
					"   score,\n" + 
					"   lore,\n" + 
					"   cognizetype,\n" + 
					"   purpose,\n" + 
					"   referencescore,\n" + 
					"   referencetime,\n" + 
					"   questioncore,\n" + 
					"   teachernote,\n" + 
					"   studentnote,\n" + 
					"   testpaper_id,\n" + 
					"   type,\n" + 
					"   user_id)\n" + 
					"values\n" + 
					"  (s_test_paperquestion_info.nextval,'"+title+"','"+creatuser+"',to_date('"+creatdate+"','yyyy-mm-dd'),'"+diff+"','"+serial+"', \n"+
					"		'"+score+"','"+lore+"','"+cognizetype+"','"+purpose+"','"+referencescore+"','"+referencetime+"', \n"+
					"  ?,'"+teachernote+"','"+studentnote+"','"+testid+"','"+type+"','"+user_id+"')";
				
				if(db.executeUpdate(question_sql,questioncore)<1)
				{
				
				}
				else
				{
					questioncount++;
				}
			}
			db.close(rs_1);
		}
		db.close(rs);
	}
  	session.removeAttribute("peBzzTchCourses");//移除session中的记录
%>
<script>
        alert("共成功复制<%=peBzzTchCourses.size()%>门课程的<%=homecount%>项作业信息，且成功复制<%=questioncount%>条题目信息");
        window.navigate('/entity/teaching/peBzzCourseManager.action?backParam=true')
</script>
