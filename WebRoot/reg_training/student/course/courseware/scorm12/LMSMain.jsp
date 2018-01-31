<%@ page language="java" pageEncoding="gb2312"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@page import="com.whaty.platform.standard.scorm.util.ScormConstant,com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%--<%@page import="com.whaty.platform.sso.web.servlet.UserSession;"%>--%>
<%@ page import="com.whaty.platform.database.oracle.*" %>
<%@ page import="com.whaty.platform.entity.util.MyUtil" %>
<%@ page import="java.util.*" %>
<%
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
String path1 = request.getContextPath();
String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
UserSession userSession = (UserSession)session.getAttribute("user_session");
if(userSession==null)
{
%>
<script type="text/javascript">
	var myGlobal = 'myGlobal';
	alert("请您登录后再学习课件！");
	window.location="<%=basePath1%>";
</script>
<%
	return;
}

String user_id=request.getParameter("user_id");
String course_id=request.getParameter("course_id");
String opencourseId=request.getParameter("opencourseId");
String trueCourseId=request.getParameter("trueCourseId");
String start=request.getParameter("start");
String scorm_type=StringUtils.defaultIfEmpty(request.getParameter("scorm_type"),"");
session.setAttribute(ScormConstant.SCORMTYPE,scorm_type);
session.setAttribute("USERID",user_id);

dbpool db = new dbpool();

/*记录点击次数开始*/

String sql = "select id from pe_bzz_tch_course_click where fk_opencourse_id='"+opencourseId+"'";
MyResultSet rs = db.executeQuery(sql);
String id = "";
if(rs!=null) {
	while(rs.next()) {
		id = rs.getString("id");
	}
}
db.close(rs);
rs = null;//Lee 2014年9月14日22:16:14
List<String> listSql = new ArrayList<String>();
if(!"".equals(id)){	//存在记录
	//sql = "update pe_bzz_tch_course_click set fk_opencourse_id='"+opencourseId+"',fk_course_id='"+trueCourseId+"',last_click_time=sysdate,"+
	sql = "update pe_bzz_tch_course_click set last_click_time=sysdate,"+
			"total_click=nvl(total_click,0)+1,day_click=nvl(day_click,0)+1,week_click=nvl(week_click,0)+1,month_click=nvl(month_click,0)+1 where id='"+id+"'";
	listSql.add(sql);
	sql = "insert into pe_bzz_tch_course_click_detail (id,fk_student_id,fk_course_id,fk_opencourse_id,click_time) values "+
			"('"+MyUtil.getUpperUUID()+"','"+user_id+"','"+trueCourseId+"','"+opencourseId+"',sysdate)";
	listSql.add(sql);
}else{	//不存在记录，即首次点击
	sql = "insert into pe_bzz_tch_course_click (id,fk_opencourse_id,fk_course_id,last_click_time,total_click,day_click,week_click,month_click) values " +
			" ('"+MyUtil.getUUID()+"','"+opencourseId+"','"+trueCourseId+"',sysdate,1,1,1,1)";
	listSql.add(sql);
	sql = "insert into pe_bzz_tch_course_click_detail (id,fk_student_id,fk_course_id,fk_opencourse_id,click_time) values "+
	"('"+MyUtil.getUpperUUID()+"','"+user_id+"','"+trueCourseId+"','"+opencourseId+"',sysdate)";
	listSql.add(sql);
}
db.executeUpdateBatch(listSql);
db.close(null);
db = null;//Lee 2014年9月14日22:16:33
/*记录点击次数结束*/

//加入课件导航；
String navigate = request.getParameter("navigate");

boolean iscourseware_nav = false;
if(ScormConstant.SCORM_NAVIGATE_PLATFORM.equals(navigate)){
	iscourseware_nav = false;
}else if(ScormConstant.SCORM_NAVIGATE_COURSEWARE.equals(navigate)){
	iscourseware_nav = true;
}

%>
<html>
<head>
<input type="hidden" id="temp" name="temp" value="1"/>
<meta http-equiv="expires" content="Tue, 20 Aug 1999 01:00:00 GMT">
<meta http-equiv="Pragma" content="no-cache">
<title>中国证券业协会</title>
<script src="../time/libXmlRequest.js"></script>
<script src="/js/jquery-1.7.2.min.js"></script>

<script language ="JAVASCRIPT" >

var API = null;
function initAPI()
{
   //API = window.LMSFrame.document.APIAdapter;
 
}
     var req;
		var url="request11.jsp?url=1";
		
		function donothing()
		{
			
		}
		
		function sendreq()
		{
		 	
			 var date = new Date();
		     var str ="&date="+date.toString();
			if(window.XMLHttpRequest) 
			{
				req=new XMLHttpRequest();
			}
			else if(window.ActiveXObject)
			{
				req=new ActiveXObject("Microsoft.XMLHttp");
			} 
			if(req) 
			{
				req.open("GET",url+str,false); 
				req.onreadystatechange =donothing; 
				req.send(null); 
			}		
			
			
		}
		setInterval(sendreq,5000*12*18);
		if(!window.opener)
			window.location="<%=request.getContextPath()%>";
</script>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String updataScomDataUrl = basePath + "servlet/OffLineLMSCMIServlet?command=updatescorm&message=";
 %>
<script type="text/javascript"><!--
//用于课件导航时课件向平台设置当前的session
//扩展scorm 功能的对象； 在精品课程中使用；由课件主动查找对象，并调用方法； modify by lwx 20090520
var request;
function createRequest() {
  try {
    request = new XMLHttpRequest();
  } catch (trymicrosoft) {
    try {
      request = new ActiveXObject("Msxml2.XMLHTTP");
    } catch (othermicrosoft) {
      try {
        request = new ActiveXObject("Microsoft.XMLHTTP");
      } catch (failed) {
        request = false;
      }
    }
  }
  if (!request)
    alert("Error initializing XMLHttpRequest!");
}

function sendData(dataUrl) {
     var url = dataUrl;
     request.open("GET", url, false);
     request.onreadystatechange = getScormData;
     request.send(null);
   }
   function getScormData() {
      if (request.readyState == 4)
       if (request.status == 200){
       }
       else if (request.status == 404)
         alert("Request URL does not exist");
       else
         alert("Error: status code is " + request.status);
   }

var whatyExtAPI = new Object;
	//当前的scoID,
	whatyExtAPI.scoID="";
	whatyExtAPI.setSCOID =function(scoID)
	{
		this.scoID = scoID;
		createRequest();
		var d = Math.random();
		sendData("set_session.jsp?d="+d+"&scoID="+scoID);
		return true;
	}
	// 获得课件同步数据的地址
	whatyExtAPI.getDataUrl = function(){
		return '<%=updataScomDataUrl%>';
	}




var startDate;
function startTimer()
{
   startDate = new Date().getTime();
}
/*******************************************************************************
** this function will convert seconds into hours, minutes, and seconds in
** CMITimespan type format - HHHH:MM:SS.SS (Hours has a max of 4 digits &
** Min of 2 digits
*******************************************************************************/
function convertTotalSeconds(ts)
{
   var sec = (ts % 60);

   ts -= sec;
   var tmp = (ts % 3600);  //# of seconds in the total # of minutes
   ts -= tmp;              //# of seconds in the total # of hours

   // convert seconds to conform to CMITimespan type (e.g. SS.00)
   sec = Math.round(sec*100)/100;
   
   var strSec = new String(sec);
   var strWholeSec = strSec;
   var strFractionSec = "";

   if (strSec.indexOf(".") != -1)
   {
      strWholeSec =  strSec.substring(0, strSec.indexOf("."));
      strFractionSec = strSec.substring(strSec.indexOf(".")+1, strSec.length);
   }
   
   if (strWholeSec.length < 2)
   {
      strWholeSec = "0" + strWholeSec;
   }
   strSec = strWholeSec;
   
   if (strFractionSec.length)
   {
      strSec = strSec+ "." + strFractionSec;
   }


   if ((ts % 3600) != 0 )
      var hour = 0;
   else var hour = (ts / 3600);
   if ( (tmp % 60) != 0 )
      var min = 0;
   else var min = (tmp / 60);

   if ((new String(hour)).length < 2)
      hour = "0"+hour;
   if ((new String(min)).length < 2)
      min = "0"+min;

   var rtnVal = hour+":"+min+":"+strSec;

   return rtnVal;
}

function computeTime()
{
   if ( startDate != 0 )
   {
      var currentDate = new Date().getTime();
      //alert('currentDate='+currentDate);
     // alert('startDate='+startDate);
      var elapsedSeconds = ( (currentDate - startDate) / 1000 );
     // alert('elapsedSeconds='+elapsedSeconds);
      var formattedTime = convertTotalSeconds( elapsedSeconds );
   }
   else
   {
      formattedTime = "00:00:00.0";
   }
 // alert('formattedTime='+formattedTime);
  return formattedTime;
}

function unloadCourse()
{

}
function init()
{
	startTimer();
}
function sendMsg()
{ 
	//if(window.LMSFrame.sendTime)
	//{
	 
		var oXml = org.cote.js.xml.getXml("submitTime.jsp?course_id=<%=course_id%>&student_id=<%=user_id%>&learn_time="+computeTime());
	 
	//}
	 //window.LMSFun.sendTime('<%=course_id%>',"<%=user_id%>",computeTime());
}

document.onkeydown=function(e)	//屏蔽   Ctrl+n
{
	var currKey = (window.event)?event.keyCode:e.which;
	var ctrlKey = (window.event)?event.ctrlKey:e.ctrlKey;
	if(ctrlKey && currKey == 78){
		return false;
	}
}


/*	
  	  window.onbeforeunload = function()
	  {		
	  		var temp = document.getElementById("temp");
	  		if(temp.value=='1'){
	  			return "提示：刷新或关闭本页面将导致开始学习后学习状态无法正常记录！如果您已经结束学习，关闭本页面将没有影响";
	  		}
	  }	*/
  
 // alert('<%=iscourseware_nav%>==='+<%=iscourseware_nav%>);
  //限制Flash型课件和精品课件不能关闭开始学习页面
 if(<%=iscourseware_nav%>)
 {
 	if ($.browser.msie){
  		window.onbeforeunload = function(){		
	  		var temp = document.getElementById("temp");
	  		if(temp.value=='1'){
	  			window.onunload = function(e){
    				window.opener.location.reload();
				}
	  			return "您确定要退出课件观看?";
	  		}
	  	}
	}else{
		$(window).on('beforeunload',function(){
			window.frames["closeFrame"].document.getElementById('closeBtn2').onclick();
		});
	}
 }

--></script>

<script type="text/javascript">
		function loadseqPage(){
			if(window.frames["LMSFrame"].document.readyState=="complete"){
				document.getElementById('Content').src = 'sequencingEngine.jsp?courseID=<%=course_id%>&start=<%=start%>';
			}
		}
	</script>
</head>
<frame id="closeFrame" name="closeFrame" src="closeCourseware.jsp" frameborder="0" noresize/>
		<%if(true){//课件导航
			%>
<frameset rows="0,*" ONLOAD="initAPI()"  frameborder="0" noresize>
		
        <frame id="LMSFrame" name="LMSFrame" src="top.jsp" frameborder="0" noresize onload="loadseqPage();">
        <input type="button" value="submit"/>
        
        <frameset id="LMSMain" cols="0,*"  frameborder="0" style="background-color: #CCCCCC;">
            <frameset rows="10,*" frameborder="0" >
               <frame id="code" src="code.jsp?course_id=<%=course_id %>" name="code" frameborder="0">
               <frame src="menu_empty.htm" name="menu" frameborder="0">
            </frameset>
            <frameset rows="32,*" frameborder="0" style="background-color: #CCCCCC;">
            	<frame id="closeFrame" onload="init();" noresize style="width:1012px;border:0px solid red;background-color: #CCCCCC;" name="closeFrame" src="closeCourseware.jsp" frameborder="0">
            	<frame id="Content" name="Content" src="" frameborder="0">
            </frameset>
        </frameset>
        <frame id="setSession" name="setSession" src="set_session.jsp" frameborder="0" noresize width="0"> <!-- 用于课件导航时课件向平台设置当前scoId -->
</frameset>
			<%
		}else{//平台导航
			%>
 <frameset rows="62,*" ONLOAD="initAPI();"   frameborder="0" noresize scroll=auto>
        <frame id="LMSFrame" name="LMSFrame" src="top.jsp" frameborder="0" noresize onload="loadseqPage();">
        <frameset id="LMSMain" cols="160,10,*" frameborder="0" noresize  scroll=auto>
            <frameset rows="0,*" frameborder="0" >
               <frame id="code" src="code.jsp?course_id=<%=course_id %>"  name="code" frameborder="0"  >
               <frame src="menu_empty.htm" name="menu" frameborder="0" noresize scroll=auto>
               <frame id="closeFrame" style="width:200px;height:1000px;border:1px solid red;" name="closeFrame" src="closeCourseware.jsp" frameborder="1">
            </frameset>
            <frame id="bar" name="bar" src="bar.html" frameborder="0" scrolling="no" noresize="noresize">
            <frame id="Content" name="Content" src="" frameborder="0">
        </frameset>
        <frame id="setSession" name="setSession" src="" frameborder="0" noresize width="0">
</frameset>
			<%
		}
		%>
<body >
</body>
</html>
