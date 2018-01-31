<%@ page language="java" import="com.whaty.util.*" pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.*"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>

<html>
	<head>

		<title></title>
		<meta http-equiv="expires" content="0">
		<link href="./css/css.css" rel="stylesheet" type="text/css">
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript">
		function getSelect()
		{
			var sel = document.getElementById("batch_id").value;
			return sel;
		}
		
		function exportManager()
		{
			var url = "/entity/manager/statistics/stat_account_manager_excel.jsp";
			window.navigate(url);
			//export(url);
		}
		
		function exportOpManager()
		{
			var url = "/entity/manager/statistics/stat_account_op_manager_excel.jsp";
			window.navigate(url);
		}
		</script>
	</head>

	<body topmargin="0" leftmargin="0" bgcolor="#FAFAFA">

		<div id="main_content">
			<div class="content_title">
				状态激活状态
			</div>
			<div class="cntent_k">
				<div class="k_cc">
					<table width="554" border="0" align="center" cellpadding="0"
						cellspacing="0">
						
                            <td height="26" align="center" valign="middle" colspan="2">请选择导出项目:</td>
                          </tr>
                          <tr>
                          </tr>
						 
                          <tr valign="middle"> 
                             <td width="25" height="30" align="center"><span>&nbsp;</span></td>
                             <td width="500" height="30" align="center"><span class="name"><a href="#" onclick="exportManager()">管理员账号激活情况</a></span></td>
                          </tr>
                          <tr valign="middle"> 
                             <td width="25" height="30" align="center"><span>&nbsp;</span></td>
                             <td width="500" height="30" align="center"><span class="name"><a href="#" onclick="exportOpManager()">有操作管理员列表</a></span></td>
                          </tr>
                          <tr valign="middle"> 
                             <td width="25" height="30" align="center"><span>&nbsp;</span></td>
                             <td width="500" height="30" align="center"><span class="name"><a href="/entity/manager/statistics/stat_study_batch_select.jsp?tagEnt=6">未登录学员列表</a></span></td>
                          </tr>
                          
						   <tr>
                            <td  height="10"> </td>
                          </tr>
					</table>

				</div>
			</div>
		</div>
		<div class="clear"></div>
	</body>
</html>
