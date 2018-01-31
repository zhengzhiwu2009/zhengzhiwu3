<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<script language="javascript">
			function pageGuarding()
			{
				var xPage = document.jump.pageNo.value;
				var maxPage = document.jump.maxPage.value;

				var isError = false;
				
				for (var i=0;i<xPage.length;i++)
				{
					var k = xPage.charAt(i);
					if ((k<'0') || (k>'9'))
					{
						isError=true;
						break;
					}
				}
				if ((xPage=="0")||(xPage=="")||parseInt(xPage,10)>parseInt(maxPage,10)|| parseInt(xPage,10)<1)
				{
					
					isError=true;
				}

				if (isError)
				{
					alert("输入页数错误，该页无法跳转！");
					document.jump.pageNo.vaule="";
					document.jump.pageNo.focus();
					document.jump.pageNo.select();
					return false;
				}
				else
					return true;
			}
</script>

<% String sName=request.getParameter("sendName");
   if(null==sName||"".equals(sName)){
	   sName="";
   }else{
   sName=java.net.URLDecoder.decode(sName,"UTF-8");
   };
%>
<table border="0" cellpadding="2" cellspacing="1" align="center" width=100%>
	<tr>
	<form name="jump" method="post" action="/siteEmail/editEmail_addStudent.action" onsubmit="return pageGuarding()">
		<td align=center width=30% style="font-family: Tahoma,Georgia;font-size: 9pt;"> 
		<s:if test="curPage == 1">
			<s:if test="totalPage > 1">
				<a href="/siteEmail/editEmail_addStudent.action?curPage=<s:property value="totalPage"/>&sendIds=<s:property value="getSendIds()"/>&sendId=<s:property value='sendId'/>&sendName=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode(sName,"UTF-8"),"UTF-8") %>">末页</a>
				<a href="/siteEmail/editEmail_addStudent.action?curPage=<s:property value="nextPage"/>&sendIds=<s:property value="getSendIds()"/>&sendId=<s:property value='sendId'/>&sendName=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode(sName,"UTF-8"),"UTF-8") %>">下一页</a>
			</s:if>
		</s:if>
		<s:elseif test="curPage > 1">
			<s:if test="totalPage > curPage">
				<a href="/siteEmail/editEmail_addStudent.action?curPage=1&sendIds=<s:property value="getSendIds()"/>&sendId=<s:property value='sendId'/>&sendName=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode(sName,"UTF-8"),"UTF-8") %>">首页</a> <a href="/siteEmail/editEmail_addStudent.action?curPage=<s:property value="prePage"/>&sendIds=<s:property value="getSendIds()"/>&sendId=<s:property value='sendId'/>&sendName=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode(sName,"UTF-8"),"UTF-8") %>">上一页</a> 
				<a href="/siteEmail/editEmail_addStudent.action?curPage=<s:property value="nextPage"/>&sendIds=<s:property value="getSendIds()"/>&sendId=<s:property value='sendId'/>&sendName=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode(sName,"UTF-8"),"UTF-8")%>">下一页</a> <a href="/siteEmail/editEmail_addStudent.action?curPage=<s:property value="totalPage"/>&sendIds=<s:property value="getSendIds()"/>&sendId=<s:property value='sendId'/>&sendName=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode(sName,"UTF-8"),"UTF-8") %>">末页</a>
			</s:if>
			<s:elseif test="curPage == totalPage">
				<a href="/siteEmail/editEmail_addStudent.action?curPage=1&sendIds=<s:property value="getSendIds()"/>&sendId=<s:property value='sendId'/>&sendName=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode(sName,"UTF-8"),"UTF-8")%>">首页</a> <a href="/siteEmail/editEmail_addStudent.action?curPage=<s:property value="prePage"/>&sendIds=<s:property value="getSendIds()"/>&sendId=<s:property value='sendId'/>&sendName=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode(sName,"UTF-8"),"UTF-8") %>">上一页</a>
			</s:elseif>
		</s:elseif>
		</td>
		<td align="center" width=20% style="font-family: Tahoma,Georgia;font-size: 9pt;"> 
         共<s:property value="totalPage"/>页 &nbsp;&nbsp;第<s:property value="curPage"/>页&nbsp;&nbsp;&nbsp; 
		</td>
		<td align="center" width=30% style="font-family: Tahoma,Georgia;font-size: 9pt;">
			跳转到第<input  type="text" name="pageNo" type="text" size="3" maxlength="5">页
			<input type=hidden name=maxPage value="<s:property value="totalPage"/>" >
			<input type="hidden" name="sendIds" value="<s:property value='getSendIds()'/>">
			<input type="hidden" name="sendName"  value="<s:property value='sendName' />">
			<input type="hidden" name="sendId" value="<s:property value='sendId' />">
	    </td>
	    <td align="center">
			<input name=Submit type=image src="../entity/function/pub/images/go.gif" width="47" height="32" border=0>
		</td>
	</form>
		
	<form name="setsize" method="post" action="/siteEmail/editEmail_addStudent.action?isLimit=1">
		<td align=center width=25% style="font-family: Tahoma,Georgia;font-size: 9pt;">
    	每页 <input type="hidden" name="sendIds" value="<s:property value='getSendIds()'/>">
			<select name="limit" onChange="document.setsize.submit()">
				<option value="10" <s:if test="limit == 10">selected</s:if> >10</option>
				<option value="20" <s:if test="limit == 20">selected</s:if> >20</option>
				<option value="30" <s:if test="limit == 30">selected</s:if> >30</option>
				<option value="50" <s:if test="limit == 50">selected</s:if> >50</option>
				<option value="100" <s:if test="limit == 100">selected</s:if> >100</option>
			</select>
    	条
		</td>
		<input type="hidden" name="path" value="<s:property value="totalPage"/>">
		<input type="hidden" name="sendName"  value="<s:property value='sendName' />">
			<input type="hidden" name="sendId" value="<s:property value='sendId' />">
	</form>
	</tr>
</table>
