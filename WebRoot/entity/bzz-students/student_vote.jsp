<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.text.*" %>							
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String d=String.valueOf(Math.random());
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
// Date date = new Date();
//  date.getDate();
//   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//   String dateString = formatter.format(date);
 // System.out.print(dateString+"========================================");
  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中国证劵业协会培训平台</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" /> 
<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" /> 
<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
</head>

<body onload="window.location.href='#top'"><a id="top" name="top" />
	<form action="/entity/workspaceStudent/studentWorkspace_getVoteList.action" name="frm" method="post">
	<input type="hidden" name="startIndex" value="<s:property value="startIndex" />" />
	<input type="hidden" name="pageSize" value="<s:property value="pageSize" />" />
		<div class="" style="width:100%;padding:0;margin:0; " align="center">
	      <div class="main_title" style="cursor: default;">调查问卷</div>
	      <div class="main_txt">
			  <table class="datalist" width="100%" style="cursor: default;">
				<tr>
				    <td colspan="4" align="left"> 
				      	<div id="fany">
					      	<script language="JavaScript"  >
								var page2 = new showPages("page2",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
								page2.printHtml();
							</script>
				      	</div>
			      </td>
			    </tr>
				<tr bgcolor="#8dc6f6">
					<th>名称</th>
				    <th width="10%">结束时间</th>
				    <th width="30%" align="center">填写问卷&nbsp;&nbsp;查看结果</th>
				  </tr>
				  <s:if test="page != null">
				  	<s:iterator value="page.items" id="vote" status="item">
						<tr>
							<td>
								<s:if test="#vote[3].getTime() >= dateString.getTime()">
						   			<a target="_blank" href="/entity/first/firstPeVotePaper_toVote.action?bean.id=<s:property value="#vote[0]"/>">
				              			<s:property value="#vote[1]"/>
			              			</a>
				             	 </s:if>
					            <s:else>
					            	<s:property value="#vote[1]"/>
					        	</s:else>
				            </td>
						    <!-- <td ><s:date name="#vote.foundDate" format="yyyy-MM-dd" /></td> -->
					    	<td><s:date name="#vote[3]" format="yyyy-MM-dd" /></td>
						    <td >
							    <s:if test="#vote[3].getTime() >= dateString.getTime()">
							    	<a target="_blank" href="/entity/first/firstPeVotePaper_toVote.action?bean.id=<s:property value="#vote[0]"/>" title="开始填写"><img src="/entity/bzz-students/images/kctp.jpg" width="100" height="22" border="0" /></a>
							   	</s:if>
							   	<s:else>
							   		<img src="/entity/bzz-students/images/kctp.jpg" width="100" height="22" border="0" title="调查问卷填写时间已结束，无法填写。"  />
							   	</s:else>
						    	<a target="_blank" href="/entity/first/firstPeVotePaper_voteResult.action?bean.id=<s:property value="#vote[0]"/>" title="查看结果"><img border="0" src="/entity/bzz-students/images/ckjg.jpg" width="100" height="22" /></a>
						  	</td>
						  </tr>
				  	</s:iterator>
				  </s:if>
				  <tr>
				    <td colspan="4" align="left"> 
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