<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.database.oracle.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>tableType_2</title>
<link href="<%=request.getContextPath()%>/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<script>
	//文件类型(*.xls)
	 function FileTypeCheck()
     {
        if(document.batch.src_batch_id.value == "")
		{
			alert("请选择源学期！");
			return false;
		}
		if(document.batch.tar_batch_id.value == "")
		{
			alert("请选择复制到学期！");
			return false;
		}
  	    return true;
    }
</script>
</head>
<body leftmargin="0" topmargin="0" class="scllbar">
<form name = "batch" action="/entity/manager/teaching/copy_homeworkexe.jsp" method="POST"  enctype="multipart/form-data" onsubmit ="return FileTypeCheck();">
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">复制课程作业信息</div></td>
  </tr>
    
            
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb" align="center"> 
        <div class="cntent_k" id="zlb_content_start">
          <p><FONT color="red"><s:property value="msg" escape="false" /></FONT></p>
          <table width="68%" border="0" cellpadding="0" cellspacing="0">
            
              <tr valign="bottom" class="postFormBox">
                <td width="80" class="text3" valign="bottom">源学期：</td>
				<td valign="bottom">
					 <select name="src_batch_id">
						<option value="">请选择</option>
						 <%
							String sql="select id,name from pe_bzz_batch order by recruit_selected desc";
							 dbpool db = new dbpool();
							 MyResultSet rs = db.executeQuery(sql);
							 while(rs!=null && rs.next())
							 {
								String id=rs.getString("id");
								String name=rs.getString("name");
							 %>
							<option value="<%=id%>"><%=name %></option>
							 <%
						}
						db.close(rs);
						 %>
						</select>
				</td>
			  </tr>	
             <tr valign="bottom" class="postFormBox">
             <td width="80" class="text3" valign="bottom">复制到学期：</td>
				<td valign="bottom">
					 <select name="tar_batch_id">
						<option value="">请选择</option>
						 <%
							sql="select id,name from pe_bzz_batch order by recruit_selected desc";
						    rs = db.executeQuery(sql);
							 while(rs!=null && rs.next())
							 {
								String id=rs.getString("id");
								String name=rs.getString("name");
							 %>
							<option value="<%=id%>"><%=name %></option>
							 <%
						}
						db.close(rs);
						 %>
						</select>
				</td>
			  </tr>	
            <tr>
									<td>&nbsp;</td>
								</tr>
            <tr class="postFormBox">
              <td ></td>
             <td><input type=submit name="submit1" value = "复制" /> &nbsp; &nbsp; &nbsp; &nbsp;
             <input type="button" value="返回" onclick="javascript:window.navigate('/entity/teaching/peBzzCourseManager.action?backParam=true')"></td>
            </tr>
         </table>
      </div>

    </td>
  </tr>
 
</table>
</form>
</body>
</html>