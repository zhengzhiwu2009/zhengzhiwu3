<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看课程信息</title>
<link href="./css/css.css" rel="stylesheet" type="text/css">
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<style type="text/css">
body{
 padding:0 0 0 8px;
 margin:2px 0 0 0;
 background-color:rgb(236, 247, 255);
 }
.STYLE1 {color: #000000}
.border{
border:solid 1px #ffffff;
}
</style>
</head>
<body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">
  
  
   <div id="main_content">
    <div class="content_title">查看课程信息</div>
    <div class="cntent_k">
   	  <div class="k_cc">
					<table width="90%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="8" colspan="4">
								&nbsp;
							</td>
						</tr>
						<tr valign="middle">
							<td width="100" height="30" align="left" class="postFormBox">
								<span class="name">课&nbsp;&nbsp;程&nbsp;&nbsp;编&nbsp;&nbsp;号&nbsp;&nbsp;：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="getBean().getCode()" />
								
							</td>
							<td width="100" height="30" align="left" class="postFormBox">
								<span class="name">课&nbsp;&nbsp;程&nbsp;&nbsp;名&nbsp;&nbsp;称&nbsp;&nbsp;：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="getBean().getName()" />
								
							</td>
							</tr>
							<tr valign="middle">
								<td width="100" height="30" align="left" class="postFormBox">
									<span class="name">讲&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;师：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">
									<s:property value="getBean().getTeacher()" />
								</td>
								<td width="100" height="30" align="left" class="postFormBox">
									<span class="name">价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">
									<s:property value="getBean().getTime()*price" />
								</td>
							</tr>
						<tr valign="middle">
							<td width="100" height="30" align="left" class="postFormBox">
								<span class="name">学习期限(天)&nbsp;：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="getBean().getStudyDates()" />
							</td>
							<td width="100" height="30" align="left" class="postFormBox">
								<span class="name">停&nbsp;&nbsp;用&nbsp;&nbsp;日&nbsp;&nbsp;期&nbsp;&nbsp;：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:date name="getBean().getEndDate()" format="yyyy-MM-dd"/>
							</td>
						</tr>
						
						<tr valign="middle">
							<td width="100" height="30" align="left" class="postFormBox">
								<span class="name">答疑开始时间：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:date name="getBean().getAnswerBeginDate()" format="yyyy-MM-dd"/>
								
							</td>
							<td width="100" height="30" align="left" class="postFormBox">
								<span class="name">答疑结束时间：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:date name="getBean().getAnswerEndDate()" format="yyyy-MM-dd"/>
						
							</td>
						</tr>
						<tr valign="middle">
							<td width="100" height="30" align="left" class="postFormBox">
								<span class="name">课&nbsp;&nbsp;程&nbsp;&nbsp;性&nbsp;&nbsp;质&nbsp;&nbsp;：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="getBean().getEnumConstByFlagCourseType().getName()" />
								
							</td>
							<td width="100" height="30" align="left" class="postFormBox">
								<span class="name">课&nbsp;&nbsp;程&nbsp;&nbsp;学&nbsp;&nbsp;时&nbsp;&nbsp;：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="getBean().getTime()" />
								
							</td>
						</tr>
							<tr valign="middle">
								<td width="100" height="30" align="left" class="postFormBox">
									<span class="name">通&nbsp;&nbsp;过&nbsp;&nbsp;规&nbsp;&nbsp;则&nbsp;&nbsp;：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">
									<s:property value="getBean().getPassRole()" />
									
								</td>
								<td width="100" height="30" align="left" class="postFormBox">
								<span class="name">考试限制次数：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">
									<s:property value="getBean().getExamTimesAllow()" />
									
								</td>
							</tr>
							<tr valign="middle">
								<td width="100" height="30" align="left" class="postFormBox">
									<span class="name">通过继续访问：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">
									<s:property value="getBean().getEnumConstByFlagIsvisitAfterPass().getName()" />
									
								</td>
								<td width="100" height="30" align="left" class="postFormBox">
								<span class="name">按业务分类&nbsp;&nbsp;&nbsp;&nbsp;：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">
									<s:property value="getBean().getEnumConstByFlagCourseCategory().getName()" />
									
								</td>
							</tr>
							<tr valign="middle">
								<td width="100" height="30" align="left" class="postFormBox">
									<span class="name">是&nbsp;&nbsp;否&nbsp;&nbsp;考&nbsp;&nbsp;试&nbsp;&nbsp;：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">
									<s:property value="getBean().getEnumConstByFlagIsExam().getName()" />
									
								</td>
								<td width="100" height="30" align="left" class="postFormBox">
								<span class="name">按大纲分类&nbsp;&nbsp;&nbsp;&nbsp;：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">
									<s:property value="getBean().getEnumConstByFlagCourseItemType().getName()" />
									
								</td>
							</tr>
							<tr valign="middle">
								<!--
								<td width="100" height="30" align="left" class="postFormBox">
									<span class="name">推&nbsp;&nbsp;荐&nbsp;&nbsp;状&nbsp;&nbsp;态&nbsp;&nbsp;：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">
									<s:property value="getBean().getEnumConstByFlagIsRecommend().getName()" />
									
								</td>
								-->
								<td width="100" height="30" align="left" class="postFormBox">
									<span class="name">课&nbsp;&nbsp;件&nbsp;&nbsp;首&nbsp;&nbsp;页&nbsp;&nbsp;：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">
									<s:property value="indexFile" />
									
								</td>
								<td width="100" height="30" align="left" class="postFormBox">
								<span class="name">收&nbsp;&nbsp;费&nbsp;&nbsp;状&nbsp;&nbsp;态&nbsp;&nbsp;：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">
									<s:property value="getBean().getEnumConstByFlagIsFree().getName()" />
									
								</td>
							</tr>
							<!--
							<tr valign="middle">
								<td width="100" height="30" align="left" class="postFormBox">
									<span class="name">课&nbsp;&nbsp;件&nbsp;&nbsp;首&nbsp;&nbsp;页&nbsp;&nbsp;：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px" colspan=3>
									<s:property value="indexFile" />
									
								</td>
								
							</tr>
							-->				
							<tr valign="middle">
								<td width="100" height="30" align="left" class="postFormBox">
									<span class="name">通过规则描述：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px" colspan=3>
									<s:property value="getBean().getPassRoleNote()" escape="false"/>
								</td>
								
							</tr>
							<tr valign="middle">
								<td width="100" height="30" align="left" class="postFormBox" >
									<span class="name">学&nbsp;&nbsp;习&nbsp;&nbsp;建&nbsp;&nbsp;议&nbsp;&nbsp;：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px" colspan=3>
									<s:property value="getBean().getSuggestion()" />
									
								</td>
								
							</tr>
							<!-- 
							<tr valign="middle">
								<td width="100" height="30" align="left" class="postFormBox">
									<span class="name">&nbsp;&nbsp;&nbsp;已导入课件&nbsp;：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px" colspan=3>
									<a href="#"><s:property value="" /></a>
								</td>
								
							</tr>
						   -->
						<tr>
						
							<td height="10" colspan=6 align="center">&nbsp;
							<input type="button" value="关闭" onclick="javascript:window.self.close()">
							</td>
					
						</tr>
					</table>

				</div>
    </div>
</div>
<div class="clear"></div>
  </body>
</html>