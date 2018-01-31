<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
	Map map = (Map)request.getSession().getAttribute("map");
	for(int i=0; i<map.size(); i++) {
		List list = (List)map.get(i);
			
		for(int j=0; j<list.size(); j+=2) {
				
			if(j==0) {
			j-=1;
					%>
				
            	 <tr>
            		<td class="14title" align="left"><%=i+1 %>、<%=list.get(j+1) %></td>
            	 </tr>
				<%
			} else if(j==1){
				%>
				<tr>
            	  <td bgcolor="#F9F9F9">
					<%=list.get(j) %><input type="radio" name=<%=i %>/><%=list.get(j+1) %><br/><br/>
				<%
			} else if(j==list.size()-1) {
			%>
				<%=list.get(j) %><input type="radio" name=<%=i %>/><%=list.get(j+1) %><br/><br/>	
				</td></tr>			
			<%
			} else {
			%>
				<%=list.get(j) %><input type="radio" name=<%=i %>/><%=list.get(j+1) %><br/><br/>			
			<%
			}
		}
	}
	
 %>
       