<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<Script type="text/javaScript">
function CloseWin() 
{   
window.opener=null;   
//window.opener=top;   
window.open("","_self");   
window.close();   
} 
</Script>
<html>
  <head>
    
    <title>调查问卷</title>
    
	<link href="/web/vote/css.css" rel="stylesheet" type="text/css">

  </head>
  
<body topmargin="0" leftmargin="0" bgcolor="#EEEEEE">

<table width="1000" border="1" align="center" cellpadding="0" cellspacing="0" bordercolordark="#8dc6f6" bordercolorlight="#FFFFFF">
 <tr>
    <td height="158" align="center" background="/entity/manager/images/votebg.gif" class="16title"><font size=6><s:property value="peVotePaper.pictitle"/></font></td>
  </tr>
  <tr>
    <td align="center" bgcolor="#8dc6f6" class="16title">调 查 结 果</td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF" style="padding-left:5px"><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
    <s:if test="togo!=1">
        <tr>
          <td class="12content" align=center><s:date name="peVotePaper.startDate" format="yyyy-MM-dd" />~~<s:date name="peVotePaper.endDate" format="yyyy-MM-dd" /></td>
        </tr>
        </s:if>
        
		<%
			List pbtcDetail = (List)request.getAttribute("pbtcDetail");
			Object code = "";
			Object name = "";
			Object teacher = "";
			System.out.println();
			System.out.println();
			System.out.println(pbtcDetail);
			System.out.println();
			System.out.println();
			if(null != pbtcDetail && pbtcDetail.size() > 0){
				code = ((Object[])pbtcDetail.get(0))[0];
				name = ((Object[])pbtcDetail.get(0))[1];
				teacher = ((Object[])pbtcDetail.get(0))[2];
				%>
				<tr>
					<td>
						<table width="100%" style="font-size:12px;">
							<tr>
								<td align="center">
									课程编号：<%=code %>
								</td>
								<td align="center">
									课程名称：<%=name %>
								</td>
								<td align="center">
									主讲人：<%=teacher %>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<%
			}
		 %>
         <%@ include file="/web/vote/vote_result_total.jsp" %>
        <tr>
          <td align="center" class="14title">总平均分（<font color="#FF0000"><s:property value="voteNumber" /></font>）</td>
        </tr>
        <tr>
          <td height="40" align="center"> 
            <input name="Submit1" type="submit" id="Submit1" value="关闭窗口" onClick="CloseWin();">
          </td>
        </tr>
      </table>
</td>
  </tr>
</table>
</body>
</html>

