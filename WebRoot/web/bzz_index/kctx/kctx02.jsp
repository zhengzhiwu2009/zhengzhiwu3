<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.MyResultSet"/>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
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
String path1 = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>中国证劵业协会培训平台</title>
<link href="/web/bzz_index/style/xytd.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/web/bzz_index/js/wsMenu.js"></script>
<script language="javascript">
function resize()
{
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
}
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>

<body bgcolor="#FFFFFF">
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" style="border:solid 2 #9FC3FF; padding:5px; margin-top:10px;">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D7D7D7">
    <tr bgcolor="#E9F4FF">
        <td colspan=3 height="35" align="center" class="kctx_zi1"><font size=3><strong>提升类课程（86学时，供学员自由选择学习，74学时选修+12学时名家）</strong></font></td>
      </tr>
      <tr bgcolor="#ECECEC">
        <td height="35" align="center" class="kctx_zi1"><strong>课程类别</strong></td>
        <td align="center" class="kctx_zi1"><strong>课程名称</strong></td>
        <td align="center" class="kctx_zi1"><strong>学时</strong></td>
      </tr>
      <tr>
        <td rowspan="2" align="center" bgcolor="#F5F5F5" class="kctx_zi1">管理基础系列课程</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">组织行为与学习型班组</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">8</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">时间管理</td>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">4</td>
      </tr>
      <tr>
        <td rowspan="6" align="center" bgcolor="#F5F5F5" class="kctx_zi1">管理技能与技巧系列
</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">技术革新与班组创新</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">8</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">班组节能减排</td>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">4</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">班组工作效率测度与改善</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">4</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">班组民主建设</td>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">8</td>
      </tr>
        <tr>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">职业安全与健康管理</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">8</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">优秀班组建设与管理案例解析</td>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">8</td>
      </tr>
      <tr>
        <td rowspan="6" align="center" bgcolor="#F5F5F5" class="kctx_zi1">综合素养系列</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">职业道德塑造</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">4</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">企业实用应用文写作</td>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">4</td>
      </tr>
        <tr>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">和谐劳动关系构建及劳动合同法解读</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">2</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">从技术人才走向管理</td>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">4</td>
      </tr>
        <tr>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">阳光心态</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">4</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">传统文化与人文精神</td>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">4</td>
      </tr>
       <tr>
        <td rowspan="4" align="center" bgcolor="#F5F5F5" class="kctx_zi1">名家讲座
</td>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">中华人文精神与领导素养</td>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">4</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">循环经济与可持续发展</td>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">3</td>
      </tr>
        <tr>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">企业创新与信息技术</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">1</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">孙子兵法战略精要</td>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">4</td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>

