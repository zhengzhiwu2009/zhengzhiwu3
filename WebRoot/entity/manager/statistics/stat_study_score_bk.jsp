<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.*"%>
<%@page
	import="com.whaty.platform.training.basic.*,com.whaty.platform.sso.web.action.SsoConstant,com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.training.user.*"%>
<%@page
	import="com.whaty.platform.training.*,com.whaty.platform.database.oracle.standard.training.user.OracleTrainingStudentPriv"%>
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
<%@page
	import="com.whaty.platform.database.oracle.standard.interaction.OracleInteractionUserPriv"%>
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

<%
	String courseId = request.getParameter("coursewareId");
	TrainingFactory factory = TrainingFactory.getInstance();
	UserSession usersession = (UserSession) session
			.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
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
	InteractionFactory interactionFactory = InteractionFactory
			.getInstance();
	InteractionUserPriv interactionUserPriv = new OracleInteractionUserPriv();
	InteractionManage interactionManage = interactionFactory
			.creatInteractionManage(interactionUserPriv);
	TestManage testManage = interactionManage.creatTestManage();

	TrainingStudentOperationManage stuManage = factory
			.creatTrainingUserOperationManage(includePriv);
	/*ScormStudentManage scormStudentManage = stuManage
			.getScormStudentManage();
	UserCourseData userCourseData = scormStudentManage
			.getUserCourseData(courseId);
	if (userCourseData == null) {
		userCourseData = new UserCourseData();
	}
	List userScos = scormStudentManage.getUserScos(courseId);

	int scores = 0;
	for (int i = 0; i < userScos.size(); i++) {
		String val = ((UserScoData) userScos.get(i)).getCore()
		.getScore().getRaw().getValue();
		if (val != null)
			scores += Double.valueOf(val).intValue();
	}*/

	String search = fixnull(request.getParameter("search"));
	String pageInt1 = fixnull(request.getParameter("pageInt1"));
	String id = fixnull(request.getParameter("id"));
	String name = fixnull(request.getParameter("name"));
	String sql = "";
	dbpool db = new dbpool();
	MyResultSet rs = null;
	MyResultSet rs1 = null;
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>tableType_1</title>
		<link href="/entity/manager/statistics/css/admincss.css"
			rel="stylesheet" type="text/css">
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<script language="JavaScript">
	function listSelect(listId) {
	var form = document.forms[listId + 'form'];
	for (var i = 0 ; i < form.elements.length; i++) {
		if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'ids')) {
			if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
				form.elements[i].checked = form.listSelectAll.checked;
			}
		}
	}
	return true;
}
</script>
		<script language="javascript">
//	function jiesuo(id,page)
//	{
//		var batch_id = id;
//		var pageInt = page;
//		var link = "pici_jiesuo.jsp?batch_id="+batch_id+"&pageInt="+pageInt;
//		if(confirm("您确定要解锁当前批次吗？")){
//		    window.location.href=link;
//			}
//	}
//	function cfmdel(link)
//	{
//		if(confirm("您确定要删除本批次吗？"))
//			window.navigate(link);
//	}

function doselect(objID)
{
	var tempObj;
		if(document.getElementById(objID))
		{
			tempObj = document.getElementById(objID);
			if(tempObj.checked)
			{
				tempObj.checked=false;
			}
			else
			{
				tempObj.checked=true;
			}
		}
}
</script>
	</head>

	<body leftmargin="0" topmargin="0" class="scllbar">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="28">
					<table width="100%" height="28" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="12">
								<img src="/entity/manager/statistics/images/page_titleLeft.gif"
									width="12" height="28">
							</td>
							<td align="right"
								background="/entity/manager/statistics/images/page_titleM.gif">
								<table height="28" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="112">
											<img
												src="/entity/manager/statistics/images/page_titleMidle.gif"
												width="112" height="28">
										</td>
										<td
											background="/entity/manager/statistics/images/page_titleRightM.gif"
											class="pageTitleText">
											查看<%=name%>作业自测状况
										</td>
									</tr>
								</table>
							</td>
							<td width="8">
								<img src="/entity/manager/statistics/images/page_titleRight.gif"
									width="8" height="28">
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td valign="top" class="pageBodyBorder">
					<!-- start:内容区域 -->

					<div class="border">
						<form name="searchForm"
							action="/entity/manager/statistics/stat_study_score.jsp"
							method="post">
							<input type="hidden" name="pageInt1" value="<%=pageInt1%>" />
							<input type="hidden" name="id" value="<%=id%>" />
							<input type="hidden" name="name" value="<%=name%>" />
							<table width='100%' border="0" cellpadding="0" cellspacing='0'
								style='margin-bottom: 5px'>
								<%
									int x = 0, y = 0, z = 0, z2 = 0;
									
									//针对2010春季学员，企业管理概览和企业管理概览（新），班组安全管理和班组安全管理（新）中选取进度高的一门，保证基础课共16门
									String sql_training="("+
									"select course_id,percent,learn_status,student_id from training_course_student s where s.course_id not in\n" +
									"('ff8080812910e7e601291150ddc70419','ff8080812bf5c39a012bf6a1bab80820','ff8080812910e7e601291150ddc70413','ff8080812bf5c39a012bf6a1baba0821')\n" + 
									"union\n" + 
									"select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n" + 
									"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,"+
									"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n" + 
									"select percent,student_id,course_id,learn_status from training_course_student s where s.course_id='ff8080812910e7e601291150ddc70419' )a,\n" + 
									"(select percent,student_id,course_id,learn_status from training_course_student s where s.course_id='ff8080812bf5c39a012bf6a1bab80820')b\n" + 
									"where a.student_id=b.student_id\n" + 
									"union\n" + 
									"select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n" + 
									"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,"+
									"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n" + 
									"select percent,student_id,course_id,learn_status from training_course_student s where s.course_id='ff8080812910e7e601291150ddc70413' )a,\n" + 
									"(select percent,student_id,course_id,learn_status from training_course_student s where s.course_id='ff8080812bf5c39a012bf6a1baba0821')b\n" + 
									"where a.student_id=b.student_id"+
									") cs,";
								//sql_training="training_course_student cs,";
								
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
											+ "sso_user su,"+sql_training
											+ "pr_bzz_tch_opencourse   bo "
											+ "where bs.fk_batch_id = bb.id "
											+ "and bs.id = '"
											+ id
											+ "'"
											+ "and bs.fk_enterprise_id = pe.id "
											+ "and ec.id= tc.flag_coursetype "
											+ "and se.fk_stu_id = bs.id "
											+ "and bo.fk_course_id = tc.id "
											+ "and bo.fk_batch_id = bb.id "
											+ "and bs.fk_sso_user_id=su.id and su.id=cs.student_id and cs.course_id=bo.id "
											+ "and se.fk_tch_opencourse_id = bo.id and tc.name like '%"
											+ search
											+ "%' order by bo.flag_course_type, to_number(tc.suqnum)";

									//out.println(sql);
								%>
								<tr>
									<td style='white-space: nowrap;'>
										<div style="padding-left: 6px">
											<input type='text' name='search' value='<%=search%>'
												size='20' maxlength='245' style='vertical-align: bottom;'>
											<span class="link"><img
													src='/entity/manager/statistics/images/buttons/search.png'
													alt='Search' width="20" height="20" title='Search'>&nbsp;<a
												href='javascript:document.searchForm.submit()'>查找（请输入课程名称查找）</a>
											</span>
										</div>
									</td>
									<td class='misc' style='white-space: nowrap;'>
									</td>
								</tr>
							</table>
						</form>
						<!-- end:内容区－头部：项目数量、搜索、按钮 -->
						<!-- start:内容区－列表区域 -->
						<input type="hidden" name="id" value="<%=id%>" />
						<table width='98%' align="center" cellpadding='1' cellspacing='0'
							class='list'>
							<tr>
								<th width='15%' style='white-space: nowrap;' align="left">
									<span class="link">课程名称</span>
								<th width='10%' style='white-space: nowrap;' align="left">
									<span class="link">课程类型</span>
								</th>
								<th width='15%' style='white-space: nowrap;' align="left">
									<span class="link">作业选择题正确率</span>
								</th>
								<th width='10%' style='white-space: nowrap;' align="left">
									<span class="link">作业判断题正确率</span>
								</th>
								<th width='10%' style='white-space: nowrap;'>
									<span class="link">作业问答题状态</span>
								</th>
								<th width='10%' style='white-space: nowrap;'>
									<span class="link">自测分数</span>
								</th>
							</tr>
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
								String link = "&search=" + search + "&id=" + id + "&name=" + name;
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

									String zcsql = " select ty.score as score,ty.id as tyid,ti.id as tiid ,count(ty.id) as num from test_testpaper_history ty "
									+ " inner join test_testpaper_info ti on ty.testpaper_id = ti.id "
									+ " and ti.group_id ='"
									+ course_id
									+ "' and ty.t_user_id ='"
									+ id
									+ "' group by ty.score,ty.id,ti.id";

									MyResultSet zcrs = db.executeQuery(zcsql);
									String zcscore = "";
									String zcid = "";
									String zice_link = "";
									int num = 0;
									while (zcrs != null & zcrs.next()) {
										y++;
										zcid = zcrs.getString("tyid");
										zcscore = fixnull(zcrs.getString("score"));
										//num = zcrs.getInt("num");
										if (!zcscore.equals(""))
									zice_link += "<a href=\"/entity/manager/statistics/stat_test_info.jsp?userid="
											+ id
											+ "&paperid="
											+ zcid
											+ "&course_id="
											+ course_id
											+ "\" target=\"_blank\"><font color=\"#0000ff\">"
											+ zcscore + "</font></a>&nbsp;&nbsp;&nbsp;";
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
									int pcount = 0;
									int dcount = 0;
									int pzong = 0;
									int dzong = 0;
									int mcount = 0;
									int mzong = 0;
									
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
							<tr class='<%if(a%2==0) {%>oddrowbg<%} else {%>evenrowbg<%} %>'>
								<td style='white-space: nowrap; text-align: center;'><%=course_name%></td>
								<td style='white-space: nowrap; text-align: center;'><%=course_type%></td>
								<td style='white-space: nowrap; text-align: center;'>
									<%
												if (mzong != 0) {
												if (!mmpercent.equals("多选未做") && !mmpercent.equals("无多选")) {
									%>
									<font color="red">(多选)&nbsp; <a
										href="/entity/manager/statistics/stat_course_info.jsp?userid=<%=id%>&paperid=<%=mpaperid%>&course_id=<%=course_id%>"
										target="_blank"> <%=mmpercent%></a> &nbsp; &nbsp; </font>
									<%
									} else {
									%>
									<font color="red"> <%=mmpercent%>&nbsp; &nbsp; </font>
									<%
											}
											}
											if (dzong != 0) {
												if (!ddpercent.equals("单选未做") && !ddpercent.equals("无单选") ) {
									%><font
										color="red">(单选)</font><font color="#0000ff"><a
										href="/entity/manager/statistics/stat_course_info.jsp?userid=<%=id%>&paperid=<%=dpaperid%>&course_id=<%=course_id%>"
										target="_blank"><%=ddpercent%></a>
									</font>
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
								<td style='white-space: nowrap; text-align: center;'>
									<%
									if (!pppercent.equals("未做")  && !pppercent.equals("无判断")) {
									%><a
										href="/entity/manager/statistics/stat_course_info.jsp?userid=<%=id%>&paperid=<%=ppaperid%>&course_id=<%=course_id%>"
										target="_blank"><font color="#0000ff"><%=pppercent%></font>
									</a>
									<%
									} else {
									%><font color="red"><%=pppercent%></font>
									<%
									}
									%>
								</td>
								<td style='white-space: nowrap; text-align: center;'>
									<%
									if (!stats.equals("未做") && !stats.equals("无问答")) {
									%><a
										href="/entity/manager/statistics/stat_course_info.jsp?userid=<%=id%>&paperid=<%=wpaperid%>&course_id=<%=course_id%>"
										target="_blank"><font color="#0000ff"><%=stats%></font>
									</a>
									<%
									} else {
									%><font color="red"><%=stats%></font>
									<%
									}
									%>
								</td>
								<td style='white-space: nowrap; text-align: center;'>
									<%
									if (num > 0) {
									%><%=zice_link%>
									<%
									} else {
									%><font color="red">未做</font>
									<%
									}
									%>
								</td>
							</tr>
							<%
								}
								db.close(rs);
							%>
						</table>
					</div>

					<!-- 内容区域结束 -->
				</td>
			</tr>
			<tr>
				<td height="48" align="center" class="pageBottomBorder">
					<br/><%@ include file="./pub/dividepage.jsp"%>
					<br/>
				</td>
			</tr>
			<tr>
				<td height="48" align="center" class="pageBottomBorder">
					<table width="98%" height="100%" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="4">
								<img src="../images/page_bottomSlip.gif" width="100%" height="2">
							</td>
						</tr>
						<tr>
							<td align="center" valign="middle" class="pageBottomBorder">
								<span class="norm"
									style="width: 46px; height: 15px; padding-top: 3px"
									onmouseover="className='over'" onmouseout="className='norm'"
									onmousedown="className='push'" onmouseup="className='over'">
									<span class="text"
									onclick="javascript:window.navigate('/entity/manager/statistics/study_student.jsp?pageInt=<%=pageInt1%>&search=<%=search%>')">返回</span>
								</span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>