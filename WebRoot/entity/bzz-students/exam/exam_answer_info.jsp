<%@ page language="java" import="java.net.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.whaty.platform.entity.*,com.whaty.platform.entity.user.*,com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@page import="com.whaty.platform.database.oracle.*"%>
<%@ page import="com.whaty.platform.interaction.exam.*"%>
<%@ include file="../pub/priv.jsp"%>
<%@page
	import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page
	import="com.whaty.platform.sso.web.action.SsoConstant" />
<%@ page
	import="com.whaty.platform.database.oracle.standard.interaction.exam.*"%>

<%!String fixnull(String str) {
		if (str == null || str.equals("null") || str.equals("")) {
			str = "";
			return str;
		}
		return str;
	}%>
<%!public static String subTitle(String str, int len) {
		if (str == null) {
			return "";
		}
		str = str.trim();
		StringBuffer r = new StringBuffer();
		int l = str.length();
		float count = 0;
		for (int i = 0; i < l; ++i) {
			char c = str.charAt(i);
			if (c > 255 || c < 0) {
				++count;
				r.append(c);
			} else {
				count += 0.5;
				r.append(c);
			}
			if (count >= len) {
				if (i < l - 1) {
					r.append("...");
				}
				break;
			}
		}
		return r.toString();
	}%>

<%!String fixnull1(String str) {
		if (str == null || str.equals("null") || str.equals("")) {
			str = "";
			return str;
		}
		return str;
	}%>
<script language="javascript">
function cfmdel(link)
{
	if(confirm("您确定要删除吗？"))
		window.navigate(link);
}
function cfmOp() {
	return confirm("真的要修改吗");
}
</script>

<%
	String question_id = request.getParameter("question_id");
	//String pageInt = request.getParameter("pageInt");
	String title = request.getParameter("title");
	String name = request.getParameter("name");

	String zong_type = fixnull(request.getParameter("zong_type"));
	String zong_title = java.net.URLDecoder.decode(fixnull(request
			.getParameter("zong_title")), "UTF-8");
	String zong_pageInt = request.getParameter("zong_pageInt");

	String flag = fixnull(request.getParameter("flag"));

	String student_id = fixnull(student.getId());

	dbpool db1 = new dbpool();
	db1
			.executeUpdate("update exam_QUESTION_INFO set click_num=click_num+1 where id='"
					+ question_id + "'");

	InteractionFactory interactionFactory = InteractionFactory
			.getInstance();
	InteractionManage interactionManage = interactionFactory
			.creatInteractionManage(interactionUserPriv);

	ExamQuestion question = interactionManage
			.getExamQuestion(question_id);

	//Answer answer = interactionManage.getAnswer(question_id);	

	List searchProperty = new ArrayList();
	searchProperty.add(new SearchProperty("question_id", question_id,
			"="));

	//官方回答
	ExamAnswer teacher_answer = interactionManage
			.getExamAnswerTeacher(question_id);

	//最佳回答
	ExamAnswer key_answer = interactionManage
			.getExamAnswerKey(question_id);

	//查看该问题自己是否已关注

	ExamUserQuestion focus_question = interactionManage
			.getExamUserQuestion(question_id, student_id);
	//UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title></title>
		<link href="/entity/function/css/css.css" rel="stylesheet"
			type="text/css">
		<link href="/entity/bzz-students/exam/style.css" rel="stylesheet"
			type="text/css" />
		<link href="/entity/bzz-students/exam/wyjd.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript"
			src="/entity/bzz-students/exam/wsMenu.js"></script>
		<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
	</head>
	<script>
	var bLoaded=false; 
	function chkSubmit()
	{
		
		if (bLoaded==false)
		{
			alert("表单正在下载")
			return false
		}		
		
	   var oEditor = FCKeditorAPI.GetInstance('body') ;
       var acontent=oEditor.GetXHTML();
		if(acontent.length <2)
		{
			alert("内容为空，还是多写几句吧？");
			return false;
		}	
	}
</script>

	<body leftmargin="0" topmargin="0"
		background="/entity/function/images/bg2.gif">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="top" class="bg3">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="45" valign="top"></td>
						</tr>
						<tr>
							<td align="center" valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td height="65">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td align="center" class="text1">
														<img src="/entity/function/images/xb3.gif" width="17"
															height="15">
														<strong>问题详细</strong>
													</td>
													<td width="300">
														&nbsp;
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td background="/entity/function/mages/table_02.gif"
											valign="top">
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
												<tr>
													<td
														background="/entity/bzz-students/exam/images/wyjd_03.jpg"
														height="2"></td>
												</tr>
											</table>
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0" class="tw_bian">
												<tr>
													<td>
														<table width="100%" border="0" cellspacing="0"
															cellpadding="0" class="tw_biaot">
															<tr>
																<td>
																	<img src="/entity/bzz-students/exam/images/wyjd_13.jpg"
																		width="16" height="16" align="absmiddle" class="img" />
																	<span class="fddy_sousuo_font"
																		style="padding-left: 12px;"><%=question.getTitle()%></span>
																</td>
															</tr>
															<tr>
																<td>
																	&nbsp;
																</td>
															</tr>
															<tr>
																<td>
																	<font size="2"> <%=question.getBody()%></font>
																</td>
															</tr>
														</table>
														<table width="97%" border="0" cellspacing="0"
															cellpadding="0" class="tw_wenz">
															<tr>
																<%
																	if (!student_id.equals(question.getSubmituserId())) {
																%>
																<%
																	if (fixnull(focus_question.getId()).equals("")) {
																%>
																<td>
																	<div class="cwzj">
																		<a
																			href="exam_answer_focus.jsp?question_id=<%=question.getId()%>&status=1&zong_title=<%=java.net.URLEncoder.encode(zong_title, "UTF-8")%>&zong_type=<%=zong_type%>&zong_pageInt=<%=zong_pageInt%>">关注</a>
																	</div>
																</td>
																<%
																	} else {
																%>
																<td>
																	<div class="cwzj">
																		<a
																			href="exam_answer_focus.jsp?question_id=<%=question.getId()%>&status=0&zong_title=<%=java.net.URLEncoder.encode(zong_title, "UTF-8")%>&zong_type=<%=zong_type%>&zong_pageInt=<%=zong_pageInt%>">取消关注</a>
																	</div>
																</td>
																<%
																	}
																%>
																<%
																	} else {
																%>
																<td>
																	<div class="cwzj">
																		<a
																			href="exam_answer_edit.jsp?question_id=<%=question.getId()%>&zong_title=<%=java.net.URLEncoder.encode(zong_title, "UTF-8")%>&zong_type=<%=zong_type%>&zong_pageInt=<%=zong_pageInt%>">修改问题</a>
																	</div>
																</td>
																<%
																	}
																%>
																<td>
																	提问时间：<%=question.getPublishDate()%>
																	| 提问者：
																	<span class="lanz"><%=question.getSubmituserName()%></span>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
											<%
												if (!fixnull(teacher_answer.getId()).equals("")) {
											%>
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0" class="lshd">
												<tr>
													<td>
														<table width="100%" border="0" cellspacing="0"
															cellpadding="0" class="lshd_biaot">
															<tr>
																<td>
																	<img src="/entity/bzz-students/exam/images/wyjd_23.jpg"
																		width="23" height="32" align="absmiddle" class="img" />
																	<span class="fddy_sousuo_font"
																		style="padding-left: 12px;">老师回答</span>
																</td>
															</tr>
														</table>
														<table width="97%" border="0" cellspacing="0"
															cellpadding="0" class="lshd_biaot">
															<tr>
																<td class="lshd_heiz"><%=teacher_answer.getBody()%></td>
															</tr>
															<tr>
																<td align="right" class="tw_wenz list_baitd">
																	回答时间：<%=teacher_answer.getPublishDate()%></td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
											<%
												}
											%>
											<%
												if (!fixnull(key_answer.getId()).equals("")) {
											%>
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0" class="zjda">
												<tr>
													<td>
														<table width="100%" border="0" cellspacing="0"
															cellpadding="0" class="lshd_biaot">
															<tr>
																<td>
																	<img src="/entity/bzz-students/exam/images/wyjd_28.jpg"
																		width="23" height="33" align="absmiddle" class="img" />
																	<span class="hangz" style="padding-left: 12px;">最佳回答
																	</span>
																</td>
															</tr>
														</table>
														<table width="97%" border="0" cellspacing="0"
															cellpadding="0" class="lshd_biaot">
															<tr>
																<td class="lshd_heiz"><%=key_answer.getBody()%></td>
															</tr>
															<tr>
																<td align="right" class="tw_wenz list_baitd">
																	回答者：<%=key_answer.getPublishName()%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;回答时间：<%=key_answer.getPublishDate()%></td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
											<%
												}
												List answerList = interactionManage.getExamAnswers(null,
														question_id);
												if (answerList.size() > 0) {
											%>
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0" class="qtda">
												<tr>
													<td>
														<table width="100%" border="0" cellspacing="0"
															cellpadding="0" class="tw_biaot">
															<tr>
																<td style="padding-bottom: 17px">
																	<img src="/entity/bzz-students/exam/images/wyjd_32.jpg"
																		width="16" height="16" align="absmiddle" class="img" />
																	<span class="heiz">其他回答</span><span class="tw_wenz">共<%=answerList.size()%>条</span>
																</td>
															</tr>
														</table>
														<%
															for (int i = 0; i < answerList.size(); i++) {
																	ExamAnswer answer = (ExamAnswer) answerList.get(i);
														%>
														<table width="97%" border="0" cellspacing="0"
															cellpadding="0" class="lshd_biaot">
															<tr>
																<td colspan="2" class="lshd_heiz"><%=fixnull(answer.getBody())%></td>
															</tr>
															<tr style="padding: 10px 0px;">
																<%
																	if (fixnull(key_answer.getId()).equals("")) {
																%>
																<td class="list_baitd">
																	<div class="cwzj">
																		<a
																			href="exam_answer_iskey.jsp?answer_id=<%=answer.getId()%>&zong_title=<%=java.net.URLEncoder.encode(zong_title,
										"UTF-8")%>&zong_type=<%=zong_type%>&zong_pageInt=<%=zong_pageInt%>">成为最佳答案</a>
																	</div>
																</td>
																<%
																	} else {
																%>
																<td class="list_baitd">
																	&nbsp;
																</td>
																<%
																	}
																%>
																<td class="list_baitd">
																	<img src="/entity/bzz-students/exam/images/wyjd_33.jpg"
																		width="14" height="12" align="absmiddle" class="img" />
																	<span class="qt_wenz" style="padding-left: 12px;">回答者：</span><span
																		class="lanz"><%=fixnull(answer.getPublishName())%></span><span
																		class="qt_wenz"> | <%=fixnull(answer.getPublishDate())%></span>
																</td>
															</tr>
														</table>
														<%
															}
														%>
													</td>
												</tr>
											</table>
											<%
												}
											%>
											<form
												action="exam_answer_addexe.jsp?question_id=<%=question_id%>&zong_title=<%=java.net.URLEncoder.encode(zong_title, "UTF-8")%>&zong_type=<%=zong_type%>&zong_pageInt=<%=zong_pageInt%>"
												method="POST" name="frmAnnounce"
												onsubmit="return chkSubmit()">
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0" class="jd">
													<tr>
														<td>
															<table width="100%" border="0" cellspacing="0"
																cellpadding="0" class="jd_biaot">
																<tr>
																	<td>
																		我要解答：
																	</td>
																</tr>
															</table>
															<table width="100%" border="0" cellspacing="0"
																cellpadding="0" class="jd_biaot">
																<tr>
																	<td>
																		<textarea class="smallarea" cols="60" name="body"
																			rows="12"></textarea>
																	</td>
																</tr>
															</table>
															<!--  <div class="jd_biaot1"><a href="javascript:document.frmAnnounce.submit();">提交回答</a></div> -->
													<tr>
														<td align="center">
															<input type=submit value="提交回答" id=sub>
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															<%
																if (flag.equals("1")) {
															%>
															<input type="button" value="关闭"
																onClick="javascript:window.close()">
															<%
																} else {
															%>
															<input type="button" value="返回"
																onClick="window.location='/entity/bzz-students/exam/student_exam_tutor_y.jsp?&zong_title=<%=java.net.URLEncoder.encode(zong_title, "UTF-8")%>&zong_type=<%=zong_type%>&pageInt=<%=zong_pageInt%>'">
															<%
																}
															%>
														</td>
													</tr>
												</table>
											</form>
										</td>
										<td style="padding-left: 10px;" valign="top" width="500">
											<table width="95%" border="0" cellspacing="0" cellpadding="0"
												class="xgwt">
												<tr>
													<td>
														<table width="100%" border="0" cellpadding="0"
															cellspacing="0" class="xgwt_bt">
															<tr>
																<td style="padding-left: 12px;">
																	<img src="/entity/bzz-students/exam/images/wyjd_10.jpg"
																		width="16" height="16" align="absmiddle">
																	<span class="heiz" style="padding-left: 12px;">相关问题</span>
																</td>
															</tr>
														</table>
														<table width="96%" border="0" align="center"
															cellpadding="0" cellspacing="0" style="margin-top: 5px;">
															<%
																String exambatchId = question.getExambatchId();
																String type = question.getQuestionType();
																int totalItems = interactionManage.getExamQuestionsNum(exambatchId,
																		null, type);
																totalItems = totalItems - 1;
																//----------分页开始---------------
																//String spagesize = (String) session.getValue("pagesize");
																String spagesize = "15";
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
																String link = "";
																//----------分页结束---------------		

																List questionList = interactionManage.getExamQuestions(pageover,
																		exambatchId, null, type);
																Iterator it = questionList.iterator();
																int t = 0;
																while (it.hasNext()) {
																	OracleExamQuestion questiont = (OracleExamQuestion) it.next();
																	t++;
																	if (!questiont.getId().equals(question.getId())) {
															%>
															<tr>
																<%
																	if (!flag.equals("1")) {
																%>
																<td class="xgwt_wz">
																	<a
																		href="/entity/bzz-students/exam/exam_answer_info.jsp?flag=<%=flag%>&question_id=<%=questiont.getId()%>&zong_title=<%=java.net.URLEncoder.encode(zong_title,
										"UTF-8")%>&zong_type=<%=zong_type%>&zong_pageInt=<%=zong_pageInt%>"
																		target="mainer"> <%=subTitle(questiont.getTitle(), 6)%></a>
																</td>
																<%
																	} else {
																%>
																<td class="xgwt_wz">
																	<a
																		href="/entity/bzz-students/exam/exam_answer_info.jsp?flag=<%=flag%>&question_id=<%=questiont.getId()%>&zong_title=<%=java.net.URLEncoder.encode(zong_title,
										"UTF-8")%>&zong_type=<%=zong_type%>&zong_pageInt=<%=zong_pageInt%>"
																		target="_blank"> <%=subTitle(questiont.getTitle(), 6)%></a>
																</td>
																<%
																	}
																%>
															</tr>
															<%
																}
																}
																if (t < 15) {
																	for (int j = 0; j < 15 - t; j++) {
															%>
															<tr>
																<td class="xgwt_wz">
																	&nbsp;
																</td>
															</tr>
															<%
																}
																}
															%>
															<tr>
																<td class="content">
																	<%@ include file="../pub/dividepage_fddy_detail.jsp"%>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td></td>
			</tr>
			<tr>
				<td>
					<br/>
					<br/>
				</td>
			</tr>

		</table>
		<script>bLoaded=true;</script>
	</body>
	<script type="text/javascript"> 
<!-- 
// Automatically calculates the editor base path based on the _samples directory. 
// This is usefull only for these samples. A real application should use something like this: 
// oFCKeditor.BasePath = '/fckeditor/' ; // '/fckeditor/' is the default value. 

var oFCKeditor = new FCKeditor( 'body' ) ; 
oFCKeditor.Height = 300 ; 
oFCKeditor.Width  = 600 ; 

oFCKeditor.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
oFCKeditor.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';

oFCKeditor.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
oFCKeditor.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';

oFCKeditor.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
oFCKeditor.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';


oFCKeditor.Value = '' ; 
oFCKeditor.ReplaceTextarea() ; 
//--> 
</script>
</html>
