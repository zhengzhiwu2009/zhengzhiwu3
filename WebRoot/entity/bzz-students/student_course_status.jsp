<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.*"%>
<%@page import="com.whaty.platform.training.basic.*,com.whaty.platform.sso.web.action.SsoConstant,com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.training.user.*"%>
<%@page import="com.whaty.platform.training.*,com.whaty.platform.database.oracle.standard.training.user.OracleTrainingStudentPriv"%>
<%@page import="java.util.*,com.whaty.platform.training.*"%>
<%@page import="com.whaty.platform.standard.scorm.operation.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page" />
<%@page import="com.whaty.platform.test.history.HomeworkPaperHistory"%>
<%@page import="com.whaty.platform.interaction.*"%>
<%@page import="com.whaty.platform.test.TestManage"%>
<%@page import="com.whaty.platform.test.question.PaperQuestion"%>
<%@page import="com.whaty.platform.test.question.TestQuestionType"%>
<%@page import="com.whaty.platform.util.XMLParserUtil"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="com.whaty.platform.database.oracle.standard.interaction.OracleInteractionUserPriv"%>
<%@page import="org.dom4j.Document"%>
<%@page import="org.dom4j.DocumentHelper"%>
<%@page import="org.dom4j.Element"%>
<%!//判断字符串为空的话，赋值为""
	String fixnull(String str) {
		if (str == null || str.equals("null") || str.equals(""))
			str = "";
		return str;
	}

	private String percent(String one, String two) {
		/*Double on = Double.valueOf(one);
		Double tw = Double.valueOf(two);
		
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMaximumIntegerDigits(2);
		nf.setMaximumFractionDigits(2);
		Double double1 = on/tw;
		String percent =nf.format(double1);*/
		int a = Integer.parseInt(one);
		int b = Integer.parseInt(two);
		double p = (double) ((a * 10000) / b) / 100;
		return new Double(p).toString() + "%";
	}%>
<%--
	if(true)//由于本页面程序执行效率很差，造成数据库服务器负担很重，暂时不执行本页面程序——朱高建，20100715
	{
%>
<script language="javascript">
	alert("功能调整中，请稍后使用，给您造成不便，敬请见谅！");
	window.history.back();
</script>
<%
		return;
	}
--%>
<%
	
	String path1 = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";

	String courseId = request.getParameter("coursewareId");
	TrainingFactory factory = TrainingFactory.getInstance();
	UserSession usersession = (UserSession) session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	String userid = "";
	if (usersession != null) {
		userid = usersession.getId();
	} else {
	%>
	<script>
		window.alert("长时间未操作或操作异常，为了您的账号安全，请重新登录！");
		window.top.location="/";
		</script>
	<%
	return;
	}

	TrainingStudentPriv includePriv = new OracleTrainingStudentPriv();
	includePriv.setStudentId(usersession.getId());
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionUserPriv interactionUserPriv = new OracleInteractionUserPriv();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
	TestManage testManage = interactionManage.creatTestManage();

	TrainingStudentOperationManage stuManage = factory.creatTrainingUserOperationManage(includePriv);
	/*ScormStudentManage scormStudentManage = stuManage.getScormStudentManage();
	UserCourseData userCourseData = scormStudentManage.getUserCourseData(courseId);
	if (userCourseData == null) {
		userCourseData = new UserCourseData();
	}
	List userScos = scormStudentManage.getUserScos(courseId);

	int scores = 0;
	for (int i = 0; i < userScos.size(); i++) {
		String val = ((UserScoData) userScos.get(i)).getCore().getScore().getRaw().getValue();
		if (val != null)
			scores += Double.valueOf(val).intValue();
	}*/
	
	String pageInt1 = fixnull(request.getParameter("pageInt1"));
	String sql = "";
	dbpool db = new dbpool();
	MyResultSet rs = null;
	MyResultSet rs1 = null;
	
	String suid = usersession.getSsoUser().getId();
	sql = "select pbs.id from pe_bzz_student pbs inner join sso_user su on pbs.fk_sso_user_id=su.id \n"
		+ "where su.id='"+suid+"'";
	rs = db.executeQuery(sql);
	String id = null;
	if(rs.next()) {
		id = rs.getString("id");
	}
	db.close(rs);
	
	int x = 0, y = 0, z = 0, z2 = 0;
	sql = "select bs.id         as id,"
			+ "tc.name       as course_name,"
			+ "tc.id       as course_id,"
			+ "se.score_exam       as score ,"
			+ "ec.name       as course_type ,"
			+ "cs.PERCENT       as progress "
			+ "from pe_bzz_student          bs,"
			+ "enum_const              ec,"
			+ "pe_bzz_batch            bb,"
			+ "pe_enterprise           pe,"
			+ "pr_bzz_tch_stu_elective se,"
			+ "pe_bzz_tch_course       tc,"
			+ "sso_user su,training_course_student cs, "
			+ "pr_bzz_tch_opencourse   bo "
			+ "where bs.fk_batch_id = bb.id "
			+ "and bs.id = '"+ id+ "'"
			+ "and bs.fk_enterprise_id = pe.id "
			+ "and ec.id= tc.flag_coursetype "
			+ "and se.fk_stu_id = bs.id "
			+ "and bo.fk_course_id = tc.id "
			+ "and bo.fk_batch_id = bb.id "
			+ "and bs.fk_sso_user_id=su.id and su.id=cs.student_id and cs.course_id=bo.id "
			+ "and se.fk_tch_opencourse_id = bo.id "
			+ "order by bo.flag_course_type, to_number(tc.suqnum)";

	//System.out.println(sql);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中国证劵业协会培训平台</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
<script type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}


function selectServer() {
	window.open ('<%=basePath%>training/student/course/serverSelect.jsp', 'newwindow', 
	'height=600, width=800, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
}
//-->
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {
	color: #FF0000;
	font-weight: bold;
}
.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}
-->
</style></head>

<body onLoad="MM_preloadImages('/entity/bzz-students/images/jckc2.jpg','/entity/bzz-students/images/jckc2_over.jpg','/entity/bzz-students/images/tskc2.jpg','/entity/bzz-students/images/tskc2_over.jpg','/entity/bzz-students/images/zyzc1.jpg','/entity/bzz-students/images/zyzc1_over.jpg')">
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <IFRAME NAME="top" width=100% height=200 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/top.jsp" scrolling=no allowTransparency="true"></IFRAME>
  <tr>
    <td height="13"></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="237" valign="top"><IFRAME NAME="leftA" width=237 height=580 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/left.jsp?d=" scrolling=no allowTransparency="true"></IFRAME></td>
   <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17" align="left" class="twocentop"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" /> 当前位置：课程列表    &nbsp;&nbsp;&nbsp;&nbsp;<font color=red>(学习课件时，如果视频不流畅，请点击右下方“选择服务器”)</font></td>
      </tr>
      <tr>
          <td><br/><!-- <img name="two2_r5_c5" src="images/two/two2_r5_c5.jpg" width="750" height="72" border="0" id="two2_r5_c5" alt="" /> --></td>
      </tr>
       <tr>
            <td align="left">
            <table width="100%">
            <tr>
            <td width="80%">
	            <a href="/entity/workspaceStudent/bzzstudent_toJiChuCourses.action"><img border="0" src="/entity/bzz-students/images/jckc2.jpg" width="124" height="25" name="Image1" onMouseOver="MM_swapImage('Image1','','/entity/bzz-students/images/jckc2_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	            <a href="/entity/workspaceStudent/bzzstudent_toTiShengCourses.action"><img border="0" src="/entity/bzz-students/images/tskc2.jpg" width="124" height="25" name="Image2" onMouseOver="MM_swapImage('Image2','','/entity/bzz-students/images/tskc2_over.jpg',1)" onMouseOut="MM_swapImgRestore()" /></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           		<a href="/entity/bzz-students/student_course_status.jsp"><img border="0" src="/entity/bzz-students/images/zyzc1.jpg" width="124" height="25" name="Image3" onMouseOver="MM_swapImage('Image3','','/entity/bzz-students/images/zyzc1_over.jpg',1)" onMouseOut="MM_swapImgRestore()" /></a>
           <td>
           <td width="20%">
           <!-- 
            当前服务器：<%=(String)session.getAttribute("SelectedServerAddr") %>
            -->
             <input type="button" name="" value="选择服务器..." onClick="selectServer();"/>  
           </td>
            </tr>
            </table>
            
            </td>
            
          </tr>
      <tr align="center">
        <td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr>
          <td width="4%" align="center"></td>
            <td width="28%" align="center">课程名称</td>
             <td width="9%" align="center">课程类型</td>
            <td width="15%" align="center">选择题</td>
            <td width="12%" align="center">判断题</td>
            <td width="16%" align="center">问答题</td>
            <td width="16%" align="center">自测分数</td>
          </tr>
        </table></td>
      </tr>
      <tr valign="top">
        <td ><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocen">
 <%
						int totalItems = db.countselect(sql);
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
						String link = "&id=" + id;
						//----------分页结束---------------
						rs = db.execute_oracle_page(sql, pageInt, pagesize);
						int a = 0;
						while (rs != null && rs.next()) {
							x++;
							a++;
							String t_id = fixnull(rs.getString("id"));
							String course_id = fixnull(rs.getString("course_id"));
							String course_name = fixnull(rs.getString("course_name"));
							String course_type = fixnull(rs.getString("course_type"));

							String zcsql = " select ty.score as score,ty.id as tyid,ti.id as tiid from test_testpaper_history ty "
							+ " inner join test_testpaper_info ti on ty.testpaper_id = ti.id "
							+ " and ti.group_id ='"
							+ course_id
							+ "' and ty.t_user_id ='"
							+ id
							+ "'";

							MyResultSet zcrs = db.executeQuery(zcsql);
							String zcscore = "";
							String zcid = "";
							String zice_link = "";
							int num = 0;
							while (zcrs != null & zcrs.next()) {
								y++;
								zcid = zcrs.getString("tyid");
								zcscore = fixnull(zcrs.getString("score"));
								if (!zcscore.equals(""))
							zice_link += zcscore+"&nbsp;&nbsp;&nbsp;";
								num++;
							}
							db.close(zcrs);
							String pjsql = " select hy.id as id ,hi.id as hid, hy.test_result as test_result from test_homeworkpaper_history hy "
							+ " inner join test_homeworkpaper_info hi on hy.testpaper_id = hi.id "
							+ " and hi.group_id ='"
							+ course_id
							+ "'   and hy.t_user_id = '" + id + "'";
//		System.out.println(pjsql);
							rs1 = db.executeQuery(pjsql);
							String etype = "";
							String stanAnswer = "";
							String userAnswer = "";
							int pcount = 0;//判断数量
							int dcount = 0;//单选数量
							int pzong = 0;//判断总数
							int dzong = 0;//单选总数
							int mcount = 0;//多选数量
							int mzong = 0;//多选总数
							
							String pppercent = "无判断";
							String ddpercent = "无单选";
							String mmpercent = "无多选";
							String stats = "无问答";
//System.out.println(course_id);
							String tiSql = "select distinct pi.type as type from test_homeworkpaper_info hi,test_paperquestion_info pi \n"
									+ "where pi.testpaper_id=hi.id and hi.group_id='" + course_id + "'";
							MyResultSet tiRs = db.executeQuery(tiSql);
	//	System.out.println(tiSql);
							while(tiRs.next()) {
								String typeTi = tiRs.getString("type");
			//System.out.println("type :" + typeTi);
								if(typeTi.equals(TestQuestionType.PANDUAN)) {
									pppercent = "未做";
								} else if(typeTi.equals(TestQuestionType.DANXUAN)) {
									ddpercent = "单选未做";
								} else if(typeTi.equals(TestQuestionType.DUOXUAN)) {
									mmpercent = "多选未做";
								} else if(typeTi.equals(TestQuestionType.WENDA)) {
									stats = "未做";
								}
							}
							db.close(tiRs);
							
							String ppaperid = "";
							String dpaperid = "";
							String wpaperid = "";
							String mpaperid = "";
							Map map = new HashMap();
							int k = 0;
							while (rs1 != null && rs1.next()) {
								z++;
								String temp = rs1.getString("test_result");
								String hid = rs1.getString("hid");
								
								//System.err.print("hid: "+hid);
								//System.err.println(" ; id: " + rs1.getString("id"));
								
								Document doc = DocumentHelper.parseText(temp);
								String totalScore = doc.selectSingleNode(
								"/answers/totalscore").getText();
								String totalnote = doc.selectSingleNode(
								"/answers/totalnote").getText();
								List itemlist = doc.selectNodes("/answers/item");
							for (Iterator it = itemlist.iterator(); it.hasNext();) {
							z2++;
							Element answer = (Element) it.next();
							Element typeEle = answer.element("type");
							
							etype = typeEle.getTextTrim();
		//System.out.println("etype:" + etype);
							//判断题
							if (etype.equalsIgnoreCase(TestQuestionType.PANDUAN)) {
								Element uAnswerEle = answer.element("uanswer");
								if (uAnswerEle.getTextTrim().equals("正确")) {
									userAnswer = "1";
								} else if (uAnswerEle.getTextTrim().equals("错误")) {
									userAnswer = "0";
								} else {
									userAnswer = "未做";
								}
								Element sAnswerEle = answer.element("sanswer");
								stanAnswer = sAnswerEle.getTextTrim();
								if (userAnswer.equals(stanAnswer)) {
									pcount++;
								}
								pzong++;
								ppaperid = hid;
							}

							//多选题
							if (etype.equalsIgnoreCase(TestQuestionType.DUOXUAN)) {
								Element uAnswerEle = answer.element("uanswer");
								userAnswer = uAnswerEle.getTextTrim().toUpperCase();
								Element sAnswerEle = answer.element("sanswer");
								stanAnswer = sAnswerEle.getTextTrim();
								if (userAnswer.equals(stanAnswer)) {
									mcount++;
								}
								mzong++;
								mpaperid = hid;
							}

							//单选题
							if (etype.equalsIgnoreCase(TestQuestionType.DANXUAN)) {
								Element uAnswerEle = answer.element("uanswer");
								userAnswer = uAnswerEle.getTextTrim().toUpperCase();
								Element sAnswerEle = answer.element("sanswer");
								stanAnswer = sAnswerEle.getTextTrim();
								if (userAnswer.equals(stanAnswer)) {
									dcount++;
								}
								dzong++;
								dpaperid = hid;
							}

							//问答题
							if (etype.equalsIgnoreCase(TestQuestionType.WENDA)) {
								Element uAnswerEle = answer.element("uanswer");
								userAnswer = uAnswerEle.getTextTrim();
								if (userAnswer != "" || userAnswer.length() > 0) {
									stats = "已做";
								}
								Element sAnswerEle = answer.element("sanswer");
								stanAnswer = sAnswerEle.getTextTrim();
								wpaperid = hid;
							}

								}
								k++;
							}
							db.close(rs1);
							String ten = pcount + "";
							String den = dcount + "";
							String men = mcount + "";
							//	System.out.println("panduantotal:"+pzong+",panduancount:"+ten+",danxuantotal:"+dzong+",danxuancount:"+den+",multitotla:"+mzong+",multicount:"+men);
							if (pzong != 0) {
								String ppzong = pzong + "";
								pppercent = this.percent(ten, ppzong);
							}
							if (dzong != 0) {
								String ddzong = dzong + "";
								ddpercent = this.percent(den, ddzong);
							}
							if (mzong != 0) {
								String mmzong = mzong + "";
								mmpercent = this.percent(men, mmzong);
							}
							//System.out.println("x:" + x + "\ny:" + "\ny:" + y + "\nz:" + z + "\nz2:" +z2  );;
					%>
          <tr>
            <td width="4%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="28%">
           		<%=course_name%>
            </td>
             <td width="9%" align="center"><%=course_type%></td>
            <td width="15%" align="center">
            <%
						if (mzong != 0) {
						if (!mmpercent.equals("多选未做") && !mmpercent.equals("无多选")) {
			%>
			<font color="red">(多选)</font><font color="#0000ff">已做</font>
			<%
			} else {
			%>
			<font color="red"> <%=mmpercent%></font>
			<%
					}
					}
					if (dzong != 0) {
						if (!ddpercent.equals("单选未做") && !ddpercent.equals("无单选") ) {
			%><font color="red">(单选)</font><font color="#0000ff">已做</font>
			<%
			} else {
			%><font color="red"><%=ddpercent%></font>
			<%
					}
					}
			%>
			<%
			if (mzong == 0 && dzong == 0) {
			%><font color="red">未做</font>
			<%
			}
			%>
            </td>
            <td width="11%" align="center">
            <%
					if (!pppercent.equals("未做")  && !pppercent.equals("无判断")) {
					%>
						<font color="#0000ff">已做</font>
					<%
					} else {
					%>
						<font color="red"><%=pppercent%></font>
					<%
					}
			%>
            </td>
            <td width="16%" align=center>
          		<font color="red"><%=stats%></font>
          	</td>
          	<td width="17%" align=center>
        	<%
				if (num > 0) {
				%><font color="#0000ff"><%=zice_link%></font>
				<%
				} else {
				%><font color="red">未做</font>
				<%
				}
			%>
          	</td>
          </tr>
           <tr>
            <td colspan="7"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          
        
          <%
        	}
			db.close(rs); %>
            </s:if> 
            <tr>
            <td><br/></td>
            </tr>
         	<tr align="center">
         		<td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg" colspan="8">
         			<%@ include file="./pub/dividepage.jsp"%>
         		</td>
         	</tr>
          </table>
          </td>
      </tr>
      <tr>
       <td width="13">&nbsp;</td>
      </tr>
      
     </table></td>
    <td width="13">&nbsp;</td>
  </tr>
</table>
<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no allowTransparency="true" align=center></IFRAME>
</td>
</tr>
</table>
</body>
</html>