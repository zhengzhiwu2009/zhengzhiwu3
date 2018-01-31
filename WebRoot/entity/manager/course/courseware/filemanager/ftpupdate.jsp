<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.whaty.*" %>
<%@ page import="com.dbConnection.*" %>

<jsp:useBean id="work" scope="application" class="com.dbConnection.dbpool" />


<%
	String courseware_id="";
	String courseware_name="";
	com.whaty.dls.coursewareInfo coursewareinfo=(com.whaty.dls.coursewareInfo)session.getAttribute("coursewareinfo");
	if(coursewareinfo!=null)
	{
		courseware_id=coursewareinfo.getCoursewareId();
		courseware_name=coursewareinfo.getCoursewareName();
	}
	String CoursewarePath = com.whaty.parameter.g_basedir+com.whaty.parameter.g_root+"/courseware/"+courseware_id;
	//String user_id = (String)session.getValue("s_userid");
	String ftp_user = courseware_id+"_course";
	String sql = "select * from ftp_user where login_id='"+ftp_user+"'";
	String sqlftp = "";
	com.dbConnection.MyResultSet rs = work.executeQuery(sql);
	String ftp_location = "";
	String ftp_password = "";
	if(rs!=null && rs.next())
	{
		ftp_password = rs.getString("password");
	}
	else
	{
		sqlftp = "insert into ftp_user (login_id, password, home_dir, enabled, write_perm, idle_time, upload_rate, download_rate) "
			     +"values ('"+ftp_user+"','"+ftp_user+"','"+CoursewarePath+"','true','true','300','0','0')";
		if(work.executeUpdate(sqlftp)==0)
		{
%>
<script>
	alert("添加用户失败");
</script>
<%
		}
		ftp_password = ftp_user;
	}
	work.close(rs);
	ftp_location = com.whaty.parameter.g_domain;
	String location = "ftp://"+ftp_user+":"+ftp_password+"@"+ftp_location+":"+com.whaty.parameter.g_ftpport;
	out.print("把下面的地址复制到浏览器地址栏中，回车，然后把课件粘贴进去就可以了！<br/>"+location+"");
	//response.sendRedirect(location);
%>
