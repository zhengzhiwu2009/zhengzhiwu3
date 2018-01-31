<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s"  uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <Script>
 <s:if test="getFlag()=='false'">
  <s:if test="getErrorid()!=null&&getErrorid()!=''">
  </s:if>
  <s:else>
   alert("发送失败1");
   </s:else>
   window.history.go(-1);
 </s:if>
 <s:else>
   alert("发送成功");
   window.close();
 </s:else>
</Script>
  </head>
  
  <body>
    
  </body>
</html>
