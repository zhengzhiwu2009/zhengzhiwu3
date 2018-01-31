<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*"%>
<%@ page import="com.whaty.util.Cookie.*,com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%
	String id = request.getParameter("id");
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		TestPaper paper = testManage.getTestPaper(id);
		String teachcourseId=paper.getGroupId();
		
		String  sql="   select ptcc.name from Pe_Bzz_Pici  ptcc where ptcc.id='"+teachcourseId+"'";
		dbpool db = new dbpool();
	    String courseName="";
	    MyResultSet rs = db.executeQuery(sql);
	    try{
	    	
		    while(rs!=null&&rs.next()){
		    	courseName=rs.getString("name");
		    	
		    }
	    }catch(Exception e){
	    	
	    }finally{
	    	db.close(rs);
	    }
		
		
		WhatyCookieManage cookieManage = new WhatyCookieManage();
		String time = paper.getTime();
		String secondstr = cookieManage.getCookieValue(request,"TestTime",null); //(String)session.getAttribute("TestTime");
		long second = 0;
		if(secondstr == null || secondstr.equals("null")|| secondstr.equals(""))
			second = Integer.parseInt(time)*60;
		else
			second = Integer.parseInt(secondstr);
		
		
		
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<title>试题</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script>
var totalTime = 600;
setInterval("timer()",1000);
function n2(n)
{
 if(n < 10)return "0" + n.toString();
 return n.toString();
}

function initTime(second)
{
	totalTime = second;
	// document.getElementById("timer").style.display = "none";
}
function timer()
{
	if(totalTime>0)	
	{
		totalTime -=1;
		var n = totalTime;
		document.getElementById("second").innerHTML = n2(n % 60);
 		n = (n - n % 60) / 60;
 		document.getElementById("minute").innerHTML = n2(n % 60);
 		n = (n - n % 60) / 60;
 		document.getElementById("hour").innerHTML = n2(n);
	}
	else 
	{
		commitPaper();
	}
}
function saveTime()
{
	document.cookie="TestTime="+totalTime;
}
function commitPaper()
{
	document.cookie="TestTime=0";
	parent.submain.onCommit();
}
function refresh(){
	setTimeout('myrefresh()',10*10*1000);
}
function myrefresh() {
	$.ajax({
    url:"/entity/workspaceStudent/studentWorkspace_checkGoStudy.action",   
    dataType:"json",  
    async:true,//
    data:{"id":"value"},   
    type:"GET",   
    beforeSend:function(){
       
    },
    success:function(req){
      refresh();
    },
    complete:function(){
        
    },
    error:function(){
      
    }
});
}
</script></head>
<body leftmargin="0" topmargin="0"  background="images/bg2.gif" style='overflow:scroll;overflow-x:hidden' onload="initTime('<%=second%>'),refresh();" onunload = "saveTime()">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <!-- tr>
                <td height="86" valign="top" background="images/top_01.gif"><img src="images/zxzy.gif" width="217" height="86"></td>
              </tr-->
              <tr>
                <td align="center" valign="top"><table width="98%" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="20%"  class="text3" style="padding-left:50px">试卷：<font size="3" ><%=courseName!=null?courseName:"" %></font>  &nbsp; &nbsp; &nbsp;</td>
                            <td width="75%" height="53" background="images/wt_03.gif" align="right" valign="middle">
                            
                            <div id="timer" style=" border="1px #000000 solid">
                            <span id = hour style="font-size:24">00</span>:<span id = minute style="font-size:24">00</span>:<span id = second style="font-size:24">00</span>
                            <%-- &nbsp;&nbsp;&nbsp;&nbsp;<SPAN valign="bottom"><a href="testpaper_result.jsp?id=<%=id%>" onclick="javascript:location.replace(this.href);event.returnValue=false;" border=0><img src="images/tjsj.gif" width="97" height="31" border=0></a></SPAN> --%>
                            </div>
                            </td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
                    </tr>
                  </table> 
                </td>
              </tr>
            </table></td>
        </tr>
      </table>
      <%
      	}
      	catch(Exception e)
      	{
      		
      	}
       %>
</body>
</html>
