<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s"  uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <Script>
 <s:if test="getFlag()=='delfalse'">
  alert("删除失败");
   window.location.href="<%=request.getContextPath()%>/entity/workspaceStudent/studentWorkspace_tositeemail.action";
  </s:if>
 <s:if test="getFlag()=='deltrue'">
  alert("删除成功");
   window.location.href="<%=request.getContextPath()%>/entity/workspaceStudent/studentWorkspace_tositeemail.action";
  </s:if>
 <s:if test="getFlag()=='false'">
  <s:if test="getErrorid()!=null&&getErrorid()!=''">
   alert("收件人编号为<s:property value='getErrorid()'/>不存在，如果要添加多个联系人，请用英文“;”分号将收件人隔开");
  </s:if>
  <s:else>
   alert("操作失败");
   </s:else>
   window.history.go(-1);
 </s:if>
<s:if test="getFlag()=='true'">
   alert("发送成功");
   window.location.href="<%=request.getContextPath()%>/siteEmail/editEmail_index.action";
</s:if>
</Script>
  </head>
  
  <body>
    
  </body>
</html>
