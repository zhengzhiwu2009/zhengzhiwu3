<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*,java.text.SimpleDateFormat"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.onlinetest.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@page import="com.whaty.encrypt.OnlineExamParameter"%>
<%@ include file="../pub/priv.jsp"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>在线考试</title>
<style type="text/css">
<!--
#wrap{word-break:break-all; width:150px;} 
-->
</style>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script> 
function cfmdel(link)
{
	if(confirm("您确定要删除此在线考试吗？"))
		window.navigate(link);
}
function DetailInfo(testCourseId)
{
	window.open('onlinetestcourse_info.jsp?testCourseId='+testCourseId,'','width=630,height=550,scrollbars=yes');
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
<%!
String fixnull1(String str)
{
    if(str == null || str.equals("") || str.equals("null"))
		str = "暂无";
	return str;
}
%>	
<%

	dbpool db = new dbpool();
    String sql = "select to_char(b.start_time,'yyyy-mm-dd') as start_time,to_char(b.end_time,'yyyy-mm-dd') as end_time,b.id as batch_id from pe_bzz_student s inner join pe_bzz_batch b on s.fk_batch_id=b.id "
			+" where s.fk_sso_user_id='"+us.getSsoUser().getId()+"' ";
    MyResultSet rs = db.executeQuery(sql);
    String bStartTime = null;
    String bEndTime = null;
    String bBatchId="";
    if(rs.next()) {
	    bStartTime = rs.getString("start_time");
	    bEndTime = rs.getString("end_time");
	    bBatchId=rs.getString("batch_id");
    }
    db.close(rs);
    
    if(bBatchId.equals("ff8080812253f04f0122540a58000004")||bBatchId.equals("52cce2fd2471ddc30124764980580131")||bBatchId.equals("ff8080812824ae6f012824b0a89e0008"))
    {
    	bBatchId="and fk_batch_id is null";
    }
    else
    {
    	bBatchId="and fk_batch_id='"+bBatchId+"'";
    }
    
	String title = fixnull(request.getParameter("title"));
try
{
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
	TestManage testManage = interactionManage.creatTestManage();
	
	//UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	Map scoreM=null;
	boolean isExam=false;
	int times=0;
	int maxTimes=3;
	int totalItems = 0;
	if("teacher".equalsIgnoreCase(userType)){ 
		totalItems = testManage.getOnlineTestCoursesNum(null,title,null,null,null,null,null,null,null,teachclass_id,null);
		}
	else{
		totalItems = testManage.getOnlineTestCoursesNum(null,title,null,null,null,"1",null,null,null,teachclass_id,null,"student",null);
		scoreM = testManage.getOnlineExamScore(student.getId(),teachclass_id);
		 times = Integer.parseInt(fixnull(scoreM.get("times").toString()));
		if(scoreM.get("maxTimes") !=null){
				maxTimes =Integer.parseInt(fixnull(scoreM.get("maxTimes").toString()));
		}
			//根据配置文件来判断是否可以继续考试
			if(times>0){
				isExam=testManage.getisExam(student.getId(),teachclass_id,OnlineExamParameter.onlineExam_type);
			}
		}
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
	List testCourseList = null;
	if("teacher".equalsIgnoreCase(userType))
		testCourseList = testManage.getOnlineTestCourses(pageover,null,title,null,null,null,null,null,null,null,teachclass_id,null);
	else
		testCourseList = testManage.getOnlineTestCourses(pageover,null,title,null,null,null,"1",null,null,null,teachclass_id,null,null);
	
	int aaa = testCourseList.size();//找到的是对的。
	
	
%>

<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<br/>
<table width="90%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="../images/top_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="right" valign="top"> 
                        <table width="940" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                          
                          <td   class="text1"><img src="/entity/function/images/xb3.gif" width="17" height="15"> 
                                         <strong>在线考试</strong></td>   
<%
	if("teacher".equalsIgnoreCase(userType)&&us.getRoleId().equals("3")&&testCourseList.size()<1 )
	{
%>	
                            <td align="center"><a href="onlinetestcourse_add.jsp" class="tj">[添加新考试]</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
<%
	}
%>	    
                        	
                            </form>
                            <td width="35">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                 <td align="center">
				<table width="935" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <td height="26" background="../images/tabletop3.gif">
                	<table width="935" border="0" align="center" cellpadding="0" cellspacing="0">				
                    <tr>
                      <td width="18%" align="center" class="title">名称</td>
                      <td width="1%" align="center" valign="bottom" ><img src="images/topxb.gif"></td>
                      <td width="18%" align="center" class="title">考试限时</td>
					  <%
                      if("teacher".equalsIgnoreCase(userType)&& us.getRoleId().equals("3")){
                      %>
					  <%} %>
               	 	  <td width="22%" align="center" class="title" style="display:none">时间</td>
					  <td width="1%" align="center" valign="bottom" style="display:none"><img src="images/topxb.gif"></td>
                      <td width="8%" align="center" class="title" style="display:none">发布时间</td>
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
                      <td width="16%" align="center" class="title">状态</td>
                    </tr>                    
                  	</table>
                  </td>
                 </tr>
                    	<%                         
                          	int trNo=1;	//用来表示每行
                          	int trNoMod;	//记录是奇数行还是偶数行
                          	String trBgcolor;	//设置每行的背景色
                                                    	
                          	Iterator it=testCourseList.iterator();
                          
                          	while(it.hasNext()){ 
								//根据奇偶行，决定每行的背景颜色          	
                          		trNoMod=trNo%2;
                          		if(trNoMod==0){
                          			trBgcolor="#E8F9FF";
                          		}else{
                          			trBgcolor="#ffffff";
                          		}
                             	OnlineTestCourse testCourse = (OnlineTestCourse)it.next();
                             	String testCourseId = testCourse.getId();
                             	String examTimes = testManage.getExamTimes(testCourseId);
								String testCourseTitle = fixnull(testCourse.getTitle());
								String startDate = fixnull(testCourse.getStartDate());
								String endDate = fixnull(testCourse.getEndDate());
								String creatUser = fixnull(testCourse.getCreatUser());
								String creatDate = fixnull(testCourse.getCreatDate());
								String isAutoCheck = testCourse.getIsAutoCheck();
								String isHiddenAnswer = testCourse.getIsHiddenAnswer();
								String batch_id=testCourse.getBatch_id();
								String batch_name = fixnull(testCourse.getBatch_name());
								String status = testCourse.getStatus(); 
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
								String s = sdf.format(new Date()); 
								Date newDate = sdf.parse(s);
								Date startdate = new Date();
								if(!startDate.equals(""))
									startdate = sdf.parse(startDate);
								Date enddate = new Date();
								if(!endDate.equals(""))
									enddate = sdf.parse(endDate);
								long newDateTime = newDate.getTime();
								long startDateTime = startdate.getTime();
								long endDateTime = enddate.getTime();
								if((newDateTime<startDateTime || newDateTime>endDateTime) && "student".equalsIgnoreCase(userType)) continue;
								String status_link = "";
								if(status.equals("1"))
								{
									status_link = "<a href='onlinetestcourse_changestatus.jsp?testCourseId="+testCourseId+"&title="+title+"&pageInt="+pageInt+"&status=1&note=0'>有效</a>";
								}
								else
								{
									status_link = "<a href='onlinetestcourse_changestatus.jsp?testCourseId="+testCourseId+"&title="+title+"&pageInt="+pageInt+"&status=0&note=1'>无效</a>";
								}
								
								
								
								if(("student".equalsIgnoreCase(userType)&&status.equals("1"))||("teacher".equalsIgnoreCase(userType)))
								{ 
						%>
                    <tr>
                      <td align="center" background="images/tablebian.gif">
						<table width="935" border="0" cellspacing="0" cellpadding="0" class="mc1">
                          <tr bgcolor="<%=trBgcolor%>"> 
                            
                            <td width="18%"  class="td1" align="center"> 
                            		<%=testCourseTitle%>
							</td>
							
							 <td width="18%"  class="td1" align="center"> 
                            		<%=examTimes%> (分钟）
							</td>
							<%
                      if("teacher".equalsIgnoreCase(userType)&& us.getRoleId().equals("3")){
                      %>
                           <%=batch_name%></div>
							</td>
							<%} %>
                            <td width="23%" align="center"  class="td1" style="display:none">
                             <%
							if("teacher".equalsIgnoreCase(userType)&& us.getRoleId().equals("3"))
							{
							%>	
                            	<%=startDate%>至<%=endDate%>
                            <%} else if(bStartTime != null){ 
                            %>
                            	<%=bStartTime%>至<%=bEndTime%>
                            <%} else {%>
                            	<%=startDate%>至<%=endDate%>
                            <%} %></td></td>
                            <td width="9%" align="center"  class="td1" style="display:none"><%=creatDate%></td>
                            <%
							if("teacher".equalsIgnoreCase(userType)&&us.getRoleId().equals("3"))
							{
							%>	
                            <td width="6%" align="center"  class="td1">
                            <%=status_link%>
                            </td>
                            <%
                            } else if("teacher".equalsIgnoreCase(userType)&& !us.getRoleId().equals("3"))
                            { if(status.equals("1")){
                            %>
                            <td width="5%" align="left"  class="td1">
                             <%out.print("有效");%>
                               <%}
                                else {%>
                             <td width="5%" align="left"  class="td1">
                             <%out.print("无效");%>
                             <%}
                             } %>   
                           <td width="16%" align="center"  class="td1">
<%
	if("teacher".equalsIgnoreCase(userType)&&us.getRoleId().equals("3"))
	{
%>	
                            <a href="onlinetestcourse_session.jsp?testCourseId=<%=testCourseId%>&isAutoCheck=<%=isAutoCheck%>&isHiddenAnswer=<%=isHiddenAnswer%>&pageInt=<%=pageover.getPageInt() %>">进入</a>
                            &nbsp;&nbsp;&nbsp;
                            <%
                            	Boolean isTrue = testManage.invalideJudge(testCourseId);
                            	if(!isTrue) {
                            	%><%
                            	} else {
                            	
                            	}
                            %>
                            <a href="onlinetestcourse_edit.jsp?title=<%=title%>&pageInt=<%=pageInt%>&testCourseId=<%=testCourseId%>">编辑</a>
                            &nbsp;&nbsp;&nbsp;
                            <a href="javascript:cfmdel('onlinetestcourse_delexe.jsp?title=<%=title%>&pageInt=<%=pageInt%>&testCourseId=<%=testCourseId%>')" class="button">删除</a>
<%
	}
	else {
		
		double score = (Double)scoreM.get("score");
		long score1=Math.round(score);
		double passScore = 80.0;
		try{
			passScore = (Double)scoreM.get("passScore");
		}catch(Exception e){}
		%>
		    
			<span><%=times>0?"已考："+times+"次&nbsp;最多："+maxTimes+"次 <br/>":"" %></span>
			
		<%
		
		//if(score>=passScore){
			//课后测验通过
			//out.print("<font color=green>已经通过在线考试</font>" );
		//}else if(times>=maxTimes && score<passScore){
			//已经达到最多课后测验次数
			//out.print("<font color=red>未通过，在线考试次数达到上限！</font>");
		//}else{
			//正常，可进行课后测验
			if(times < maxTimes){
%>
                           
                        
                               <a href="javascript:void(0);" onclick="javascript:if(confirm('您确认开始进行在线考试？')) window.open('onlinetestcourse_session.jsp?testCourseId=<%=testCourseId%>&isAutoCheck=<%=isAutoCheck%>&isHiddenAnswer=<%=isHiddenAnswer%>&pageInt=<%=pageover.getPageInt() %>','onlineExam');else return false;">
                              
                            	进入在线考试
                           
                            </a>
                          
<%	
		}
	} %>
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
                    <%if((null==testCourseList||testCourseList.size()<=0)&&!"teacher".equalsIgnoreCase(userType)){ %>
                    
                    <tr>
                      <td align="center">没有可考试的试卷</td>
                    </tr>
                    <%} %>
                    <tr>
                      <td><img src="../images/tablebottom.gif" width="100%" height="4"></td>
                    </tr>
                  </table><br/>
                </td>
              </tr>
              <tr>
                <td align="center">
<table width="930" border="0" cellspacing="0" cellpadding="0">
                  
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
	e.printStackTrace();
	out.print(e.getMessage());
	return;
}
%>
