<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>满意度调查问卷预览</title>
    
	<link href="/web/vote/css.css" rel="stylesheet" type="text/css">

  </head>
  
<body topmargin="0" leftmargin="0" bgcolor="#EEEEEE">
<table width="780" border="1" align="center" cellpadding="0" cellspacing="0" bordercolordark="#FFD9AD" bordercolorlight="#FFFFFF">
  <tr>
    <td height="108" align="center" background="/entity/manager/images/votebg.gif" class="16title"></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
        
        <tr>
          <td align="center" valign="bottom" class="14title"><s:property value="satisfactionSurveyPaperInfo.title"/></td>
        </tr>
        <tr>
          <td align="center" valign="bottom" class="12content">创建日期：<s:date name="satisfactionSurveyPaperInfo.creatdate" format="yyyy-MM-dd" /></td>
        </tr>
        <tr>
          <td align="center"><img src="/entity/manager/images/votebian.gif" width="417" height="4"></td>
        </tr>
        <tr>
          <td class="12texthei"><s:property value="satisfactionSurveyPaperInfo.note" escape="false"/>
</td>
        </tr>
        <tr>
          <td><br/><form>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="12content">
            	<%@ include file="/entity/teacher/preview_include.jsp" %>
              <tr> 
                <td bgcolor="#F9F9F9" height=10></td>
              </tr>
           
              <tr> 
                <td bgcolor="#F9F9F9"></td>
              </tr>              
             
            </table></td>
        </tr>
              </table><br/></td>
  </tr>
</table>
<table width="780" border="1" align="center" cellpadding="0" cellspacing="0" bordercolordark="#FFD9AD" bordercolorlight="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#FF6600" class="16title">调 查 结 果</td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF" style="padding-left:5px"><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
        
        
        
        
      </table>
</td>
  </tr>
</table>
</body>
</html>

