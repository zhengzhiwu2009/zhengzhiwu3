<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,java.net.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.answer.*"%>
<%@ include file="../pub/priv.jsp"%>
<html>
<head>
<title>清华大学继续教育学院</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<br/>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                          <td width = "8">&nbsp;</td>
                          <td   class="text1"><img src="/entity/function/images/xb3.gif" width="17" height="15"> 
                                         <strong>课程答疑</strong></td> 
                          </tr>
                          </table>
                          <br/>
<table width="85%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="../images/top_01.gif"></td>
              </tr>
                  
              <tr>
                <td align="center">
<table width="87%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                      
                <td height="26" background="../images/tabletop1.gif">
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
					<td width="8%" align="center" class="title"></td>
                      <td width="39%" align="center" class="title">问题类型</td>
                      <td width="27%" align="center" class="title">问题数</td>
                      <td width="26%" align="center" class="title">已回答</td>                                 
                    </tr>
                  </table></td>
            </tr>
                    <tr>
                      <td align="center" background="../images/tablebian2.gif">
<table width="99%" border="0" cellspacing="0" cellpadding="0">
<%!
	String fixnull(String str)
	{
		if(str==null || str.equalsIgnoreCase("null"))
			return "";
		else
			return str.trim();
	}
%>  
<%        
	try
{

		dbpool db = new dbpool();	
		String sql = "select id,name,qis, ais from ( \n"
			+ " select qt.id as id,nvl(qt.name,'未分类') as name,count(qi.id) as qis,count(ai.id) as ais \n" 
			+ "  from interaction_question_type qt left outer join interaction_question_info qi on (qi.question_type=qt.id and qi.course_id='"+teachclass_id+"') left outer join interaction_answer_info ai on ai.question_id=qi.id \n" 
			+ "  group by qt.id,qt.name \n"
			+ "  union \n"
			+ "  select '' as id,'未分类' as name,count(qi.id) as qis,count(ai.id) as ais from interaction_question_info qi left outer join interaction_answer_info ai on ai.question_id=qi.id \n" 
			+ "  where qi.question_type is null and qi.course_id='"+teachclass_id+"') \n"
			+ "  order by id";
		//System.out.println(sql);	
		MyResultSet rs = db.executeQuery(sql);
		while(rs.next())
		{
			String id = rs.getString("id");
			String name=rs.getString("name");
			String qis = rs.getString("qis");
			String ais = rs.getString("ais");
%>                                                                                                
              <tr> 
                
                <td width="8%" align="center" valign="middle"  class="td1"><img src="../images/xb2.gif" width="8" height="8"></td>
                <td width="39%" align="center" class="td1"><a href="/entity/function/answer/index_tch.jsp?typeid=<%=id%>&type=<%=name %>"><%=name %></a></td>
               	 <td width="27%" align="center"  class="td1"><%=fixnull(qis)%></td>
               	  <td width="26%" align="center"  class="td1"><%=fixnull(ais)%></td>                
              </tr>
<%
		}
		db.close(rs);
%>           
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="../images/tablebottom.gif" width="100%" height="4"></td>
                    </tr>
                  </table><br/>
                </td>
              </tr>
      </table>
</body>
</html>
<%
}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
%>

 