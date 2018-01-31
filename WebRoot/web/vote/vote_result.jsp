<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>调查结果</title>
	<link href="/web/vote/css.css" rel="stylesheet" type="text/css">
		<script>
		function tosuggest(){
		var id = '<s:property value="peVotePaper.id"/>' ;
		window.location="/entity/first/firstPeVotePaper_toSuggest.action?bean.id=" + id;
		
		}
		</SCRIPT>
  </head>
  
<body topmargin="0" leftmargin="0" bgcolor="#EEEEEE">
<table width="780" border="1" align="center" cellpadding="0" cellspacing="0" bordercolordark="#8dc6f6" bordercolorlight="#FFFFFF">
        
  <tr>
    <td align="center" bgcolor="#8dc6f6" class="16title">调 查 结 果</br><span style="font-size:12px;">调查结果中主观题仅显示您本人填写的内容</span></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF" style="padding-left:5px"><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
    <s:if test="togo!=1">
        <tr>
          <td class="12content" align=center>起始时间：<s:date name="peVotePaper.startDate" format="yyyy-MM-dd" /> 截止时间：<s:date name="peVotePaper.endDate" format="yyyy-MM-dd" /> </td>
        </tr>
        </s:if>
         <%@ include file="/web/vote/vote_result_include.jsp" %>
        <tr>
          <td align="center" class="14title">共有（<font color="#FF0000"><s:property value="#request.voteCount" default="0"/></font>）人参加投票，感谢您的支持！</td>
        </tr>
        <tr>
          <td height="40" align="center"> 
            <s:if test="peVotePaper.enumConstByFlagViewSuggest.code == 1">
             <input name="Submi" type="submit" id="Submi" value="查看建议" onClick="tosuggest()">
            </s:if>
            <input name="Submit1" type="submit" id="Submit1" value="关闭窗口" onClick="javascript:window.close()">
          </td>
        </tr>
      </table>
</td>
  </tr>
  
</table>       
  </body>
</html>
