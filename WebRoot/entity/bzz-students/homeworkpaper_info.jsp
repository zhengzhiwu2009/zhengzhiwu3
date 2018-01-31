<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.MyResultSet"/>

<%!
   String fixnull(String str){
   		if(str==null || str.equals("null") || str.equals("")){
   			return "";
   		}
   		return str;
   }
   %>
   <%
	String id = request.getParameter("id");
	
   try{
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String title = "";
		String content = "";
		String publish_date = "";
		String sql = "select id,teachclass_id,title,content,type,status,to_char(publish_date,'yyyy-mm-dd hh24:Mi:ss') as publish_date from interaction_teachclass_info where id = '"+id+"'";
		rs = db.executeQuery(sql);
		while(rs!=null&&rs.next())
		{
			
			content = rs.getString("content");
			title = rs.getString("title");
			publish_date = rs.getString("publish_date");
		}
		db.close(rs);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>清华大学继续教育学院</title>
<link href="/entity/function/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="45" valign="top" background="/entity/function/images/top_01.gif"></td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="765" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="65" background="/entity/function/images/table_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="/entity/function/images/xb3.gif" width="17" height="15"> 
                              <strong>参考资料</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td background="/entity/function/images/table_02.gif" ><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
                          <tr>
                            <td>
                            	<table border="0" align="center" cellpadding="0" cellspacing="0" width=90%>
                            	<tr>
                            <td class="neirong" width=20%>
							<font size=2>题目：</font>
							</td>
							<td class="neirong" valign=top>
							<font size=2><%=title %>
							</td>
						  </tr>
                          		<tr>
                          		<td class="neirong" valign=top width=20%>
							<font size=2>内容：</font>
							</td>
							<td class="neirong" valign=top>
							<%=content %>
							</td>
						  </tr>
                            	</table>
							</td>
							</tr>
							
     </table></td>
    </tr>
    
        <tr>
		 <td><img src="/entity/function/images/table_03.gif" width="765" height="21"></td>
                    </tr>
		<tr>
			<td align=center><input type=button value="关闭" onclick="window.close();"></td>
		</tr>
                  </table> </td>
              </tr>
            </table></td>
        </tr>
      </table>
      </form>
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
