<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>调查问卷</title>
    
	<link href="/web/vote/css.css" rel="stylesheet" type="text/css">

  </head>
  
<body topmargin="0" leftmargin="0" bgcolor="#EEEEEE">
<table width="780" border="1" align="center" cellpadding="0" cellspacing="0" bordercolordark="#FFD9AD" bordercolorlight="#FFFFFF">
  <tr>
    <td height="108" align="center" background="/entity/manager/images/votebg.gif" class="16title"><font size=6><s:property value="peVotePaper.pictitle"/></font></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
        
        <tr>
          <td align="center" valign="bottom" class="14title"><s:property value="peVotePaper.title"/></td>
        </tr>
        <tr>
          <td align="center"><img src="/entity/manager/images/votebian.gif" width="417" height="4"></td>
        </tr>
        <tr>
          <td class="12texthei"><s:property value="peVotePaper.note" escape="false"/>
</td>
        </tr>
        <tr>
          <td><br/><form>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="12content">
            	<%@ include file="/web/vote/vote_include.jsp" %>
              <tr> 
                <td bgcolor="#F9F9F9" height=10></td>
              </tr>
              
              <s:if test="peVotePaper.enumConstByFlagCanSuggest.code == 1">
             <tr> 
                <td bgcolor="#F9F9F9">请输入您的意见或建议！</td>
              </tr>
              <tr> 
                <td bgcolor="#F9F9F9"><textarea name="suggest" cols="65" rows="6"></textarea></td>
              </tr>              
              </s:if>
              <s:else>
              <tr> 
                <td bgcolor="#F9F9F9"></td>
              </tr>              
              </s:else>
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
        <tr>
          <td class="12content" align=center><s:date name="peVotePaper.startDate" format="yyyy-MM-dd" />~~<s:date name="peVotePaper.endDate" format="yyyy-MM-dd" /></td>
        </tr>
         <%@ include file="/web/vote/vote_result_include.jsp" %>
        <tr>
          <td align="center" class="14title">共有（<font color="#FF0000"><s:property value="voteNumber" /></font>）人参加投票，感谢您的支持！</td>
        </tr>
        <tr>
          <td height="40" align="center"> 
            <input name="Submit1" type="submit" id="Submit1" value="关闭窗口" onClick="javascript:window.close()">
          </td>
        </tr>
      </table>
</td>
  </tr>
</table>
</body>
</html>

