<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>
<%
	String reg_no = fixnull(request.getParameter("RegNo"));
	String name = fixnull(request.getParameter("name"));
	name = name.replaceAll(" ","").replaceAll("　","");
 %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查询</title>
<link href="/entity/manager/statistics/css/admincss.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0" class="scllbar">
<form name='search' action='/entity/manager/search.jsp' method='post' class='nomargin'>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td height="28"><table width="100%" height="28" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="12"><img src="/entity/manager/statistics/images/page_titleLeft.gif" width="12" height="28"></td>
          <td align="right" background="/entity/manager/statistics/images/page_titleM.gif"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td width="112"><img src="/entity/manager/statistics/images/page_titleMidle.gif" width="112" height="28"></td>
                <td background="/entity/manager/statistics/images/page_titleRightM.gif" class="pageTitleText">查询</td>
              </tr>
            </table></td>
          <td width="8"><img src="/entity/manager/statistics/images/page_titleRight.gif" width="8" height="28"></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td valign="top" class="pageBodyBorder"> 
        <div class="border">
          <table width="60%" border="0" cellpadding="0" cellspacing="0">
          	<tr valign="bottom" class="postFormBox"> 
              <td>
              <span class="name">系统编号（登陆账号）&nbsp;</span>
              </td>
              <td>
				<input type="text" name="RegNo" value="<%=reg_no %>">
				</td>
				<td>
              <span class="name">姓名&nbsp;</span>
              </td>
              <td>
				<input type="text" name="name" value="<%=name %>">
				</td>
				<td><span class="norm"  style="width:46px;height:15px;padding-top:3px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'" onclick="javascript:document.search.submit();"><span class="text" >提交</span></span></td>
			</tr>
          </table>
      </div>
    </td>
  </tr>
  <tr> 
    <td height="48" align="center" class="pageBottomBorder"> 
      <table width="40%" height="100%" border="1" cellpadding="0" cellspacing="0">
  <%
  	if(reg_no.equals("")&&name.equals(""))
  	{}
  	else
  	{
  		String reg_no_con = "";
  		if(!reg_no.equals("")) reg_no_con=" u.login_id like '"+reg_no+"' and ";
  	String sql = 
"select u.login_id,\n" +
"       u.password,\n" + 
"       u.login_num,\n" + 
"       u.last_login_date,\n" + 
"       u.last_login_ip,\n" + 
"       s.gender,\n" + 
"       '' as folk,\n" + 
"       '' as education,\n" + 
"       '' as age,\n" + 
"       s.position,\n" + 
"       '' as title,\n" + 
"       '' as department,\n" + 
"       '' as zipcode,\n" + 
"       s.phone,\n" + 
"       s.mobile_phone,\n" + 
"       s.email,\n" + 
"       s.name as true_name,s.name,\n" + 
"       '' as birthday,\n" + 
"       e.name as sub_enterprise_name\n" + 
"  from sso_user u, pe_enterprise_manager s,pe_enterprise e\n" + 
" where "+reg_no_con+" replace(replace(s.name,' ',''),'　','') like '%"+name+"%'\n" + 
"   and s.fk_sso_user_id = u.id and s.fk_enterprise_id=e.id\n" + 
"\n" + 
"   union\n" + 
"\n" + 
"   select u.login_id,\n" + 
"       u.password,\n" + 
"       u.login_num,\n" + 
"       u.last_login_date,\n" + 
"       u.last_login_ip,\n" + 
"       s.gender,\n" + 
"       s.folk,\n" + 
"       s.education,\n" + 
"       s.age,\n" + 
"       s.position,\n" + 
"       s.title,\n" + 
"       s.department,\n" + 
"       s.zipcode,\n" + 
"       s.phone,\n" + 
"       s.mobile_phone,\n" + 
"       s.email,\n" + 
"       s.true_name,s.name,\n" + 
"       to_char(s.birthday,'yyyy-mm-dd') as birthday,\n" + 
"       s.sub_enterprise_name\n" + 
"  from sso_user u, pe_bzz_student s\n" + 
" where "+reg_no_con+" (replace(replace(s.true_name,' ',''),'　','') like '%"+name+"%' or replace(replace(s.name,' ',''),'　','') like '%"+name+"%')\n" + 
"   and s.fk_sso_user_id = u.id";

  	//out.print(sql);
  	
  	//if(true) return;
  	dbpool db = new dbpool();
	MyResultSet rs = null;
	rs=db.executeQuery(sql);
	while(rs!=null&&rs.next()){
   %>
        <tr><td height="4">系统编号（登陆账号）：</td><td height="4"><%=fixnull(rs.getString("login_id")) %></td></tr>
        <tr><td height="4">姓名(true_name)：</td><td height="4"><%=fixnull(rs.getString("true_name")) %></td></tr>
        <tr><td height="4">姓名(name)：</td><td height="4"><%=fixnull(rs.getString("name")) %></td></tr>
        <tr><td height="4">密码：</td><td height="4"><%=fixnull(rs.getString("password")) %></td></tr>
        <tr><td height="4">生日：</td><td height="4"><%=fixnull(rs.getString("birthday")) %></td></tr>
        <tr><td height="4">手机号：</td><td height="4"><%=fixnull(rs.getString("mobile_phone")) %></td></tr>
        <tr><td height="4">固定电话：</td><td height="4"><%=fixnull(rs.getString("phone")) %></td></tr>
        <tr><td height="4">所在企业：</td><td height="4"><%=fixnull(rs.getString("sub_enterprise_name")) %></td></tr>
        <tr><td height="4">民族：</td><td height="4"><%=fixnull(rs.getString("folk")) %></td></tr>
        <tr><td height="4">性别：</td><td height="4"><%=fixnull(rs.getString("gender")) %></td></tr>
        <tr><td height="4">登陆次数：</td><td height="4"><%=fixnull(rs.getString("login_num")) %></td></tr>
        <tr><td height="4">上次登陆时间：</td><td height="4"><%=fixnull(rs.getString("last_login_date")) %></td></tr>
        <tr><td height="4">上次登陆IP：</td><td height="4"><%=fixnull(rs.getString("last_login_ip")) %></td></tr>
        <tr><td height="4">学历：</td><td height="4"><%=fixnull(rs.getString("education")) %></td></tr>
        <tr><td height="4">年龄：</td><td height="4"><%=fixnull(rs.getString("age")) %></td></tr>
        <tr><td height="4">职位：</td><td height="4"><%=fixnull(rs.getString("position")) %></td></tr>
        <tr><td height="4">职称：</td><td height="4"><%=fixnull(rs.getString("title")) %></td></tr>
        <tr><td height="4">所属部门：</td><td height="4"><%=fixnull(rs.getString("department")) %></td></tr>
        <tr><td height="4">邮编：</td><td height="4"><%=fixnull(rs.getString("zipcode")) %></td></tr>
        <tr><td height="4">电子邮件：</td><td height="4"><%=fixnull(rs.getString("email")) %></td></tr>
        <tr><td height="4">&nbsp;</td><td height="4">&nbsp;</td></tr>
        <tr><td height="4">&nbsp;</td><td height="4">&nbsp;</td></tr>
<%
	}
	db.close(rs);
	}
 %>
      </table></td>
  </tr>
  
</table>
 </form>
</body>
</html>
