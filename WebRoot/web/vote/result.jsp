<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>调查结果</title>
	<link href="/web/vote/css.css" rel="stylesheet" type="text/css">
  </head>
  
<body topmargin="0" leftmargin="0" bgcolor="#EEEEEE">
<table width="780" border="1" align="center" cellpadding="0" cellspacing="0" bordercolordark="#8dc6f6" bordercolorlight="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#8dc6f6" class="16title">提示信息</td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF" style="padding-left:5px"><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td class="12content" align=center><s:property value="msg"/></td>
        </tr>
        <s:if test="success">
        <form action="/entity/first/firstPeVotePaper_voteResult.action">
        <tr>
          <td height="40" align="center"> 
           <input type="hidden" name="bean.id" value="<s:property value="peVotePaper.id"/>"/>
                        <input name="close" type="button" id="close" value="关闭窗口" onClick="javascript:closeWindow();"><s:if test="togo!=1"><input name="Submit1" type="submit" id="Submit1" value="查看调查结果" /></s:if>
          </td>
        </tr>
        </form>
        </s:if>

      </table>
</td>
  </tr>
</table>  
<script type="text/javascript">
	/* function closeWindow(){
		if(opener !=null && opener.location != null){
		  alert("1");
			opener.location.reload();
		}
		alert("2";)
		 window.close();
	} */
function closeWindow(){	
		if(window.opener != null && window.opener.location != null){
			window.opener.location.reload();
		}
		window.close();
}  
</script>
     
  </body>
</html>
