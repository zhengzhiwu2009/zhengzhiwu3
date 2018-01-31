<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>清华大学继续教育学院</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script>
function cfmdel(link)
{
	if(confirm("您确定要删除此试卷吗？"))
		window.navigate(link);
}
function DetailInfo(paperId)
{
	window.open('testpaper_info.jsp?paperId='+paperId,'','dialogWidth=630px;dialogHeight=550px');
}
</script>
</head>
<%!
String fixnull(String str)
{
    if(str == null || str.equals("") || str.equals("null"))
		str = "";
	return str;
}
%>	
<%
	String title = fixnull(request.getParameter("title"));
	try {
		String testCourseId = (String) session.getValue("testCourseId");  
		
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		
		int totalItems = testManage.getTestPapersNumByOnlineTestCourse(testCourseId);
		//----------分页开始---------------
		String spagesize = (String) session.getValue("pagesize");  
		String spageInt = request.getParameter("pageInt");
		Page pageover = new Page();
		pageover.setTotalnum(totalItems);
		pageover.setPageSize(spagesize);
		pageover.setPageInt(spageInt);
		int pageNext = pageover.getPageNext();
		int pageLast = pageover.getPagePre();
		int maxPage = pageover.getMaxPage();
		int pageInt = pageover.getPageInt();
		int pagesize = pageover.getPageSize();
		String link = "&title="+title;
		//----------分页结束---------------
		List testPaperList = testManage.getTestPapersByOnlineTestCourse(pageover,testCourseId);
%>

<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="../images/top_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="217" height="104" rowspan="2" valign="top"><img src="../images/zxzc.gif" width="217" height="86"></td>
                      <td height="46">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td align="right" valign="top"> 
                        <table width="50%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
<%
	if("teacher".equalsIgnoreCase(userType))
	{
%>	
                            <td align="center"><a href="testpaper_add.jsp" class="tj">[添加新试卷]</a>&nbsp;</td>
                            <td align="center"><a href="testpolicy_add.jsp" class="tj">[添加新组卷策略]</a>&nbsp;</td>
<%
	}
%>	                            	
                            <td align="center"><a href="testhistory_list.jsp" class="tj">[查看结果]</a></td>
                            <td>
                            	<form method="post" action="testpaper_list.jsp" name="paper_listSearchForm"><!--
                            	<table border="0" cellpadding="0" cellspacing="0">
                            		<tr>
                            			<td align="center" class="mc1">按标题搜索：</td>
                            			<td align="center"><input name="title" type="text" size="20" maxlength="50" value="<%//title%>"/></td>
                            		</tr>                            
                            	</table>
                            </td>
                            	<td align="center"><input type="image" src="../images/search.gif" width="99" height="19" /></td>
                            --></form>
                            
                            <td width="35">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td align="center">
				<table width="812" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <td height="26" background="../images/tabletop.gif">
                	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">				
                    <tr>
                      <td width="5%" height="17">&nbsp;</td>
                      <td width="24%" align="center" class="title">名&nbsp;&nbsp;&nbsp;&nbsp;称</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="16%" align="center" class="title">发布人</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="16%" align="center" class="title">发布时间</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="20%" align="center" class="title">试卷用途</td>
                      <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                    <%
					if("teacher".equalsIgnoreCase(userType))
					{
					%>	
                      <td width="5%" align="center" class="title">状态</td>
                      <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                    <%
					}
					%>	
                      <td width="10%" align="center" class="title">操作</td>
                    </tr>                    
                  	</table>
                  </td>
                 </tr>
                    	<%                         
                          	int trNo=1;	//用来表示每行
                          	int trNoMod;	//记录是奇数行还是偶数行
                          	String trBgcolor;	//设置每行的背景色
                                                    	
                          	Iterator it=testPaperList.iterator();
                          
                          	while(it.hasNext()){
								//根据奇偶行，决定每行的背景颜色          	
                          		trNoMod=trNo%2;
                          		if(trNoMod==0){
                          			trBgcolor="#E8F9FF";
                          		}else{
                          			trBgcolor="#ffffff";
                          		}
                             	TestPaper testPaper = (TestPaper)it.next();
                             	String paperId = testPaper.getId();
								String paperTitle = testPaper.getTitle();
								String creatUser = testPaper.getCreatUser();
								String creatDate = testPaper.getCreatDate();
								String paperType = testPaper.getType();
								String status = testPaper.getStatus();
								String status_link = "";
								if(status.equals("1"))
								{
									status_link = "<a href='testpaper_changestatus.jsp?paperId="+paperId+"&title="+title+"&pageInt="+pageInt+"&status=1&note=无效'>有效</a>";
								}
								else
								{
									status_link = "<a href='testpaper_changestatus.jsp?paperId="+paperId+"&title="+title+"&pageInt="+pageInt+"&status=0&note=有效'>无效</a>";
								}
								if(("student".equalsIgnoreCase(userType)&&status.equals("1"))||("teacher".equalsIgnoreCase(userType)))
								{
						%>
                    <tr>
                      <td align="center" background="images/tablebian.gif">
						<table width="99%" border="0" cellspacing="0" cellpadding="0" class="mc1">
                          <tr bgcolor="<%=trBgcolor%>"> 
                            <td width="5%" align="center" valign="middle" class="td1"><img src="images/xb2.gif" width="8" height="8" onClick="DetailInfo('<%=paperId%>')"></td>
                            <td width="24%"  class="td1"><span class="link" onClick="DetailInfo('<%=paperId%>')"><%=paperTitle%></span></td>
                            <td width="18%" align="center"  class="td1"><%=creatUser%></td>
                            <td width="17%" align="left"  class="td1"><%=creatDate%></td>
                            <td width="21%" align="center"  class="td1"><%=paperType%></td>
                            <%
							if("teacher".equalsIgnoreCase(userType))
							{
							%>	
                            <td width="5%" align="center"  class="td1">
                            <%=status_link%>
                            </td>
                            <%
                            }
                            %>
                            <td width="10%" align="center"  class="td1">
                            <a href="testpaper_pre.jsp?id=<%=paperId%>">进入</a>
<%
	if("teacher".equalsIgnoreCase(userType))
	{
%>	
                            <a href="javascript:cfmdel('testpaper_delete.jsp?title=<%=title%>&pageInt=<%=pageInt%>&paperId=<%=paperId%>')" class="button">删除</a>
<%
	}
%>	                            	
				            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    	<%  
                    			}
                       		trNo++;	
                          	}
                        %> 
                    
                    <tr>
                      <td><img src="../images/tablebottom.gif" width="812" height="4"></td>
                    </tr>
                  </table><br/>
                </td>
              </tr>
              <tr>
                <td align="center">
<table width="806" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="images/bottomtop.gif" width="806" height="7"></td>
                    </tr>
                    <tr>
                      <td background="images/bottom02.gif">
                       <%@ include file="../pub/dividepage.jsp" %>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="images/bottom03.gif" width="806" height="7"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table></td>
        
        </tr>
      </table>
</body>
</html>
<%
	}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
%>
