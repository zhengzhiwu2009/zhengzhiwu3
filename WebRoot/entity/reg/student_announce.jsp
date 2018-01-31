<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中国证劵业协会培训平台</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" /> 
<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" /> 
<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
</head>
<body>
<form action="/entity/workspaceStudent/stuPeBulletinView_getFirstinfo.action" name="frm" method="post">
<input type="hidden" name="startIndex" value="<s:property value="startIndex" />" />
<input type="hidden" name="pageSize" value="<s:property value="pageSize" />" />
<div class="" style="width:100%;padding:0;margin:0; " align="center">
      <div class="main_title">浏览公告</div>
     <div class="main_txt">
	  <table class="datalist" width="100%">
	  
	  <tr>
    <td colspan="9" align="left"> 
      	<div id="fany">
      	<script language="JavaScript"  >
		var page2 = new showPages("page2",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
		page2.printHtml();
		</script>
      	</div>
      </td>
    </tr>
    
  <tr bgcolor="#8dc6f6">
    <th>标题</th>
    <th width="12%">时间</th>
    <th width="16%">发布人</th>
    <th width="12%">查看内容</th>
  </tr>
  	<s:iterator value="page.items" id="bulletin">
  <tr>
  	<td>
		<font size=2><s:property value="#bulletin[1]" /></font>
	</td>
    <td><s:date name="#bulletin[2]" format="yyyy-MM-dd" /></td>
    <td><s:property value="#bulletin[3]"/></td>
    <td align="center"><div class="ck" style="width:70px;background-image:url('/entity/bzz-students/images/index_22.jpg')" ><a href='/entity/workspaceStudent/stuPeBulletinView_toInfo.action?bean.id=<s:property value="#bulletin[0]"/>' target="_blank" >查看</a></div></td>
  </tr>
  	</s:iterator>
 	<tr>
    	<td colspan="9" align="left"> 
	      	<div id="fany">
		      	<script language="JavaScript"  >
				var page1 = new showPages("page1",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
				page1.printHtml();
				</script>
	      	</div>
      </td>
    </tr>
</table>
</div>
</div>
</form>
</body>
</html>