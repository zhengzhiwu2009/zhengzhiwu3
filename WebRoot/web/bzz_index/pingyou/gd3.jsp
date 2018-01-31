<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.dbpool"%>
<%@page import="com.whaty.platform.database.oracle.MyResultSet"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>中国证劵业协会培训平台-评优表彰专栏</title>
<style type="text/css">
#menu{
width:540px;
margin:0px;
padding:0px;
}
#menu ul{
margin:0px;
padding:0px;
}
#menu ul li{
margin:3px 0px 4px;
padding:0px 0px 0px 0px;
list-style-type:none;
width:540px;
height:240px;
+height:240px;
}
 .pp2{
padding-top:0px;
padding-bottom:6px;
width:540px;
height:240px;

}
.namep2{
font-size:14px;
color:#9d0a00;
font-weight:600;
text-align:left;
padding-top:10px;}
.p2text{
font-size:12px;
color:#333333;
line-height:18px;
margin-top:6px;
text-align:left;
padding-bottom:4px;
border-bottom:1px #333333 dashed;}

a:link{
text-decoration:none;
COLOR:#333333;
}
a:visited{
text-decoration:none;
COLOR:#333333;
}
a:active{
text-decoration:none;
COLOR:#333333;
}

.bfont{
font-weight:bold;}
</style>
</head>

<%! 
	String fixNull(String str){
		if(str==null || str.equalsIgnoreCase("null")) str = "";
		return str.trim();
	}
%>

<body style="background-color:transparent;">
	<div id="menu">
		<% 
		String sql = "";
		int current_num = 0;
		int count = 0;
		dbpool work = new dbpool();
		MyResultSet rs;	
		MyResultSet rs2;	
		
		String enterprise_id = "";
		String enterprise_name = "";
		String stu_name = ""; 
		String goodStu_id = "";
		
		sql = " select distinct c.id as enterprise_id, c.name as enterprise_name,c.code from pe_bzz_goodstu a, pe_bzz_student b, pe_enterprise c where "+
			" a.fk_studentid = b.id and a.submit_status = 'ccb2880a91dadc115011dadfcf26b0005' and b.fk_enterprise_id = c.id order by code  ";
		rs = work.executeQuery(sql);
		
		current_num = 0;
		count = work.countselect(sql);
		while(rs!=null && rs.next()){
			enterprise_id = fixNull(rs.getString("enterprise_id"));
			enterprise_name = fixNull(rs.getString("enterprise_name"));
			
			if( (current_num%4) == 0 ){
			%>
				<ul>
					<li> 
						<div class="pp1">
			<%
			}
			
			%>
			
							<div class="namep2">企业名称：<%=enterprise_name %></div>
							<div class="p2text"><span class="bfont">学员：</span>
							<% 
							sql = " select a.id as goodStu_id ,  b.true_name as stu_name from pe_bzz_goodstu a, pe_bzz_student b where "+
								" a.fk_studentid = b.id and a.submit_status = 'ccb2880a91dadc115011dadfcf26b0005' and b.fk_enterprise_id = '"+enterprise_id+
								"' order by  stu_name ";
							rs2 = work.executeQuery(sql);
							while(rs2!=null && rs2.next()){
								goodStu_id = fixNull(rs2.getString("goodStu_id"));
								stu_name = "<a  href='sub3.jsp?goodStu_id="+goodStu_id+"' target='_blank' >"+fixNull(rs2.getString("stu_name"))+"</a>";
								
								%>
								<%=stu_name %>&nbsp;&nbsp;
								<%
							}
							work.close(rs2);	
							
							%>
							</div>
							
			<%
			if( ((current_num+1)%4) == 0 ){
			%>
						</div>
            		</li>
				</ul>
			<%
			}else if((current_num+1) == count){
			%>
						</div>
            		</li>
				</ul>
			<%
			}
			
			current_num ++;
		}
		work.close(rs);
		%>
		
	</div>
</body>
</html>
