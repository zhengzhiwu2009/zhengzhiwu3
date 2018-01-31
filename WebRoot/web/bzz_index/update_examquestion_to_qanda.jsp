<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*,java.util.*,java.sql.*"%>

<%
String sql = 
	"select q.id as q_id,q.title as q_title,to_char(q.publish_date,'yyyy-mm-dd') as q_publish_date,q.submituser_name as q_submituser_name,q.body as q_body,\n" +
	"q.question_type as q_question_type,a.publish_name as a_publish_name,to_char(a.publish_date,'yyyy-mm-dd') as a_publish_date,a.body as a_body\n" + 
	"from exam_question_info q,exam_answer_info a,exam_question_type t where q.id=a.question_id and a.publish_type='manager' and q.question_type=t.id";
dbpool db = new dbpool();
int count = 0;
int i = 0;
int j = 0;
count = db.countselect(sql);
MyResultSet rs = db.executeQuery(sql);
while(rs!=null&&rs.next()){
	String q_id = rs.getString("q_id");
	String q_title = rs.getString("q_title");
	String q_publish_date = rs.getString("q_publish_date");
	String q_submituser_name = rs.getString("q_submituser_name");
	String q_body = rs.getString("q_body");
	String q_question_type = rs.getString("q_question_type");
	String dir_id = "";
	if("1".equals(q_question_type)){
		dir_id="37";
	}else if("2".equals(q_question_type)){
		dir_id="38";
	}else if("3".equals(q_question_type)){
		dir_id="39";
	}
		
	String a_publish_name = rs.getString("a_publish_name");
	String a_publish_date = rs.getString("a_publish_date");
	String a_body = rs.getString("a_body");
	
	String content = "<faq><author>"+q_submituser_name+"</author><time>"+q_publish_date+"</time><body>exam_"+q_id+"</body><answer><reauthor>"
	+a_publish_name.substring(a_publish_name.indexOf("/")+1)+"</reauthor><retime>"+a_publish_date+"</retime><rebody>"+a_body+"</rebody></answer></faq>";
	String str = "<resource><item><name>内容</name><content>"+com.whaty.util.string.encode.HtmlEncoder.encode(content)+"</content></item></resource>";
	String ins_sql = "insert into resource_info(id,title,language,description,keywords,creatuser,dir_id,content,status)\n" +
		"values(s_resource_info_id.nextval,'"+q_title+"','','','','教师1','"+dir_id+"',?,'1')";
	i++;
	if(db.executeUpdate(ins_sql,str)>0){
		
	}else{
		j++;
		System.out.println(ins_sql);
	}
	System.out.println("总共："+count+"条，已完成"+i+"条，失败"+j+"条");
	out.println("总共："+count+"条，已完成"+i+"条，失败"+j+"条<br/>");
	//System.out.println(ins_sql);
}
db.close(rs);
%>