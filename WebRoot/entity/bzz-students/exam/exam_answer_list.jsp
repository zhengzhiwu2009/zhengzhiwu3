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


<%!String fixnull(String str) {
		if (str == null || str.equals("null") || str.equals("")) {
			str = "";
			return str;
		}
		return str;
	}%>

<%
	String exambatchId = fixnull(request.getParameter("exambatchId"));
	String type = java.net.URLDecoder.decode(fixnull(request
			.getParameter("type")), "UTF-8");
	String student_id = fixnull(student.getId());

	String zong_type = fixnull(request.getParameter("zong_type"));
	String zong_title = java.net.URLDecoder.decode(fixnull(request
			.getParameter("zong_title")), "UTF-8");
	String zong_pageInt = request.getParameter("zong_pageInt");
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
	</head>
	<body leftmargin="0" topmargin="0"
		background="/entity/function/images/bg2.gif">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="top" class="bg3">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="45" valign="top">
								<br/>
							</td>
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
														<strong>问题列表—<%=type%></strong>
													</td>
													<td width="300">
														&nbsp;
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td background="/entity/function/mages/table_02.gif">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0" class="list_zibg">
												<tr>
													<td style="padding: 5px;">
														<table width="100%" border="0" cellspacing="0"
															cellpadding="0">
															<tr>
																<td width="100%">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0" class="list_title">
																		<tr>
																			<td width="55%">
																				标题
																			</td>
																			<td width="1%">
																				<img src="/entity/bzz-students/exam/images/line.jpg" />
																			</td>
																			<td width="12%">
																				问题类型
																			</td>
																			<td width="1%">
																				<img src="/entity/bzz-students/exam/images/line.jpg" />
																			</td>
																			<td width="12%">
																				是否解决
																			</td>
																			<%
																				if (!type.equals("我的问题")) {
																			%>
																			<td width="1%">
																				<img src="/entity/bzz-students/exam/images/line.jpg" />
																			</td>
																			<td width="15%">
																				关注/取消关注
																			</td>
																			<%
																				}
																			%>
																		</tr>
																	</table>
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0" class="list_baibg">
																		<tr>
																			<td style="padding: 10px 10px 10px 10px;">
																				<table width="100%" border="0" cellspacing="0"
																					cellpadding="0">
																					<%
																						InteractionFactory interactionFactory = InteractionFactory
																								.getInstance();
																						InteractionManage interactionManage = interactionFactory
																								.creatInteractionManage(interactionUserPriv);
																						int totalItems = 0;
																						String link = "";
																						Page pageover = new Page();
																						int pageNext = 0;
																						int pageLast = 0;
																						int maxPage = 0;
																						int pageInt = 0;
																						int pagesize = 0;
																						String spagesize = "10";
																						String spageInt = request.getParameter("pageInt");
																						if (type.equals("我的问题")) {
																							totalItems = interactionManage.getExamQuestionsNumSelf(
																									exambatchId, student_id);
																							//----------分页开始---------------
																							//String spagesize = (String) session.getValue("pagesize");
																							pageover.setTotalnum(totalItems);
																							pageover.setPageSize(spagesize);
																							pageover.setPageInt(spageInt);
																							pageNext = pageover.getPageNext();
																							pageLast = pageover.getPagePre();
																							maxPage = pageover.getMaxPage();
																							pageInt = pageover.getPageInt();
																							pagesize = pageover.getPageSize();
																							link = "&exambatchId=" + exambatchId + "&type="
																									+ java.net.URLEncoder.encode(type, "UTF-8")
																									+ "&zong_title=" + zong_title + "&zong_type="
																									+ zong_type + "&zong_pageInt=" + zong_pageInt;
																							//----------分页结束---------------		
																							List questionList = interactionManage.getExamQuestionsSelf(
																									pageover, exambatchId, student_id);

																							Iterator it = questionList.iterator();
																							int i = 0;
																							while (it.hasNext()) {
																								OracleExamQuestion question = (OracleExamQuestion) it
																										.next();
																								i++;
																								String question_title = fixnull(question.getTitle());
																								String question_type = fixnull(question.getQuestionType());
																								String publish_type = fixnull(question.getPublish_type());
																								String publish_flag = "已解决";
																								if (publish_type.equals("")) {
																									publish_flag = "未解决";
																								}
																								//自己的问题没有关注和取消关注的操作
																								//String focus_id = fixnull(question.getFocus_id());
																								//String focus_flag = "关注";
																								//String focus_link = "";
																								String submituser_name = fixnull(question
																										.getSubmituserName());
																								String submit_date = fixnull(question.getPublishDate());
																								String click_num = fixnull(question.getClickNum());
																					%>
																					<tr>
																						<td width="62%" height="24" class="list_wenti">
																							·
																							<a
																								href="/entity/bzz-students/exam/exam_answer_info.jsp?question_id=<%=question.getId()%>&zong_title=<%=java.net.URLEncoder.encode(zong_title, "UTF-8")%>&zong_type=<%=zong_type%>&zong_pageInt=<%=zong_pageInt%>"
																								target="mainer"> <%=subTitle(question_title, 30)%></a>
																						</td>
																						<td width="15%" align="center" class="list_wenti"><%=question_type%></td>
																						<td width="15%" align="center" class="list_wenti"><%=publish_flag%></td>
																						<%--  <td width="10%" align="center" class="butten_grey"><a href="#">投入关注</a> </td>--%>
																					</tr>
																					<tr>
																						<td colspan="4" class="grey_font">
																							提问者：<%=submituser_name%>
																							| 提问时间：<%=submit_date%>
																							| 浏览：<%=click_num%>人
																						</td>
																					</tr>
																					<%
																						}
																						} else if (type.equals("我的关注")) {
																							totalItems = interactionManage.getExamQuestionsNumFocus(
																									exambatchId, student_id);
																							//----------分页开始---------------
																							//String spagesize = (String) session.getValue("pagesize");
																							pageover.setTotalnum(totalItems);
																							pageover.setPageSize(spagesize);
																							pageover.setPageInt(spageInt);
																							pageNext = pageover.getPageNext();
																							pageLast = pageover.getPagePre();
																							maxPage = pageover.getMaxPage();
																							pageInt = pageover.getPageInt();
																							pagesize = pageover.getPageSize();
																							link = "&exambatchId=" + exambatchId + "&type="
																									+ java.net.URLEncoder.encode(type, "UTF-8")
																									+ "&zong_title=" + zong_title + "&zong_type="
																									+ zong_type + "&zong_pageInt=" + zong_pageInt;
																							//----------分页结束---------------		
																							List questionList = interactionManage.getExamQuestionsFocus(
																									pageover, exambatchId, student_id);

																							Iterator it = questionList.iterator();
																							int i = 0;
																							while (it.hasNext()) {
																								OracleExamQuestion question = (OracleExamQuestion) it
																										.next();
																								i++;
																								String question_title = fixnull(question.getTitle());
																								String question_type = fixnull(question.getQuestionType());
																								String publish_type = fixnull(question.getPublish_type());
																								String publish_flag = "已解决";
																								if (publish_type.equals("")) {
																									publish_flag = "未解决";
																								}
																								String submituser_name = fixnull(question
																										.getSubmituserName());
																								String submit_date = fixnull(question.getPublishDate());
																								String click_num = fixnull(question.getClickNum());
																								String focus_link = "exam_answer_focus.jsp?question_id="
																										+ question.getId() + "&status=0" + "&zong_title="
																										+ java.net.URLEncoder.encode(zong_title, "UTF-8")
																										+ "&zong_type=" + zong_type + "&zong_pageInt="
																										+ zong_pageInt;
																					%>
																					<tr>
																						<td width="56%" height="24" class="list_wenti">
																							·
																							<a
																								href="/entity/bzz-students/exam/exam_answer_info.jsp?question_id=<%=question.getId()%>&zong_title=<%=java.net.URLEncoder.encode(zong_title, "UTF-8")%>&zong_type=<%=zong_type%>&zong_pageInt=<%=zong_pageInt%>"
																								target="mainer"> <%=subTitle(question_title, 30)%></a>
																						</td>
																						<td width="16%" align="center" class="list_wenti"><%=question_type%></td>
																						<td width="16%" align="center" class="list_wenti"><%=publish_flag%></td>
																						<td width="16%" align="center" class="butten_grey">
																							<a href="<%=focus_link%>">取消关注</a>
																						</td>
																					</tr>
																					<tr>
																						<td colspan="4" class="grey_font">
																							提问者：<%=submituser_name%>
																							| 提问时间：<%=submit_date%>
																							| 浏览：<%=click_num%>人
																						</td>
																					</tr>
																					<%
																						}
																						}

																						else if (type.equals("已解决问题")) {
																							totalItems = interactionManage.getExamQuestionsNumYes(
																									exambatchId, student_id);
																							//----------分页开始---------------
																							//String spagesize = (String) session.getValue("pagesize");
																							pageover.setTotalnum(totalItems);
																							pageover.setPageSize(spagesize);
																							pageover.setPageInt(spageInt);
																							pageNext = pageover.getPageNext();
																							pageLast = pageover.getPagePre();
																							maxPage = pageover.getMaxPage();
																							pageInt = pageover.getPageInt();
																							pagesize = pageover.getPageSize();
																							link = "&exambatchId=" + exambatchId + "&type="
																									+ java.net.URLEncoder.encode(type, "UTF-8")
																									+ "&zong_title=" + zong_title + "&zong_type="
																									+ zong_type + "&zong_pageInt=" + zong_pageInt;
																							//----------分页结束---------------		
																							List questionList = interactionManage.getExamQuestionsYes(
																									pageover, exambatchId, student_id);

																							Iterator it = questionList.iterator();
																							int i = 0;
																							while (it.hasNext()) {
																								OracleExamQuestion question = (OracleExamQuestion) it
																										.next();
																								i++;
																								String question_title = fixnull(question.getTitle());
																								String question_type = fixnull(question.getQuestionType());
																								String publish_type = fixnull(question.getPublish_type());
																								String publish_flag = "已解决";
																								String submituser_name = fixnull(question
																										.getSubmituserName());
																								String submit_date = fixnull(question.getPublishDate());
																								String click_num = fixnull(question.getClickNum());

																								String focus_id = fixnull(question.getFocus_id());
																								String focus_flag = "关注";
																								String focus_link = "exam_answer_focus.jsp?question_id="
																										+ question.getId() + "&status=1" + "&zong_title="
																										+ java.net.URLEncoder.encode(zong_title, "UTF-8")
																										+ "&zong_type=" + zong_type + "&zong_pageInt="
																										+ zong_pageInt;
																								if (!focus_id.equals("")) {
																									focus_flag = "取消关注";
																									focus_link = "exam_answer_focus.jsp?question_id="
																											+ question.getId()
																											+ "&status=0"
																											+ "&zong_title="
																											+ java.net.URLEncoder.encode(zong_title,
																													"UTF-8") + "&zong_type=" + zong_type
																											+ "&zong_pageInt=" + zong_pageInt;
																								}
																					%>
																					<tr>
																						<td width="56%" height="24" class="list_wenti">
																							·
																							<a
																								href="/entity/bzz-students/exam/exam_answer_info.jsp?question_id=<%=question.getId()%>&zong_title=<%=java.net.URLEncoder.encode(zong_title, "UTF-8")%>&zong_type=<%=zong_type%>&zong_pageInt=<%=zong_pageInt%>"
																								target="mainer"> <%=subTitle(question_title, 30)%></a>
																						</td>
																						<td width="16%" align="center" class="list_wenti"><%=question_type%></td>
																						<td width="16%" align="center" class="list_wenti"><%=publish_flag%></td>
																						<td width="16%" align="center" class="butten_grey">
																							<a href="<%=focus_link%>"><%=focus_flag%></a>
																						</td>
																					</tr>
																					<tr>
																						<td colspan="4" class="grey_font">
																							提问者：<%=submituser_name%>
																							| 提问时间：<%=submit_date%>
																							| 浏览：<%=click_num%>人
																						</td>
																					</tr>
																					<%
																						}
																						} else if (type.equals("待回答问题")) {
																							totalItems = interactionManage.getExamQuestionsNumNo(
																									exambatchId, student_id);
																							//----------分页开始---------------
																							//String spagesize = (String) session.getValue("pagesize");
																							pageover.setTotalnum(totalItems);
																							pageover.setPageSize(spagesize);
																							pageover.setPageInt(spageInt);
																							pageNext = pageover.getPageNext();
																							pageLast = pageover.getPagePre();
																							maxPage = pageover.getMaxPage();
																							pageInt = pageover.getPageInt();
																							pagesize = pageover.getPageSize();
																							link = "&exambatchId=" + exambatchId + "&type="
																									+ java.net.URLEncoder.encode(type, "UTF-8")
																									+ "&zong_title=" + zong_title + "&zong_type="
																									+ zong_type + "&zong_pageInt=" + zong_pageInt;
																							//----------分页结束---------------		
																							List questionList = interactionManage.getExamQuestionsNo(
																									pageover, exambatchId, student_id);

																							Iterator it = questionList.iterator();
																							int i = 0;
																							while (it.hasNext()) {
																								OracleExamQuestion question = (OracleExamQuestion) it
																										.next();
																								i++;
																								String question_title = fixnull(question.getTitle());
																								String question_type = fixnull(question.getQuestionType());
																								String publish_type = fixnull(question.getPublish_type());
																								String submituser_name = fixnull(question
																										.getSubmituserName());
																								String submit_date = fixnull(question.getPublishDate());
																								String click_num = fixnull(question.getClickNum());

																								String focus_id = fixnull(question.getFocus_id());
																								String focus_flag = "关注";
																								String focus_link = "exam_answer_focus.jsp?question_id="
																										+ question.getId() + "&status=1" + "&zong_title="
																										+ java.net.URLEncoder.encode(zong_title, "UTF-8")
																										+ "&zong_type=" + zong_type + "&zong_pageInt="
																										+ zong_pageInt;
																								if (!focus_id.equals("")) {
																									focus_flag = "取消关注";
																									focus_link = "exam_answer_focus.jsp?question_id="
																											+ question.getId()
																											+ "&status=0"
																											+ "&zong_title="
																											+ java.net.URLEncoder.encode(zong_title,
																													"UTF-8") + "&zong_type=" + zong_type
																											+ "&zong_pageInt=" + zong_pageInt;
																								}
																					%>
																					<tr>
																						<td width="56%" height="24" class="list_wenti">
																							·
																							<a
																								href="/entity/bzz-students/exam/exam_answer_info.jsp?question_id=<%=question.getId()%>&zong_title=<%=java.net.URLEncoder.encode(zong_title, "UTF-8")%>&zong_type=<%=zong_type%>&zong_pageInt=<%=zong_pageInt%>"
																								target="mainer"> <%=subTitle(question_title, 30)%></a>
																						</td>
																						<td width="16%" align="center" class="list_wenti"><%=question_type%></td>
																						<td width="16%" align="center" class="list_wenti">
																							未解决
																						</td>
																						<%
																							if (!question.getSubmituserId().equals(student_id)) {
																						%>
																						<td width="16%" align="center" class="butten_grey">
																							<a href="<%=focus_link%>"><%=focus_flag%></a>
																						</td>
																						<%
																							} else {
																						%>
																						<td width="16%" align="center" class="butten_grey"></td>
																						<%
																							}
																						%>
																					</tr>
																					<tr>
																						<td colspan="4" class="grey_font">
																							提问者：<%=submituser_name%>
																							| 提问时间：<%=submit_date%>
																							| 浏览：<%=click_num%>人
																						</td>
																					</tr>
																					<%
																						}
																						} else if (type.equals("新消息")) {
																							totalItems = interactionManage.getExamNews(exambatchId,
																									student_id);
																							//----------分页开始---------------
																							//String spagesize = (String) session.getValue("pagesize");
																							pageover.setTotalnum(totalItems);
																							pageover.setPageSize(spagesize);
																							pageover.setPageInt(spageInt);
																							pageNext = pageover.getPageNext();
																							pageLast = pageover.getPagePre();
																							maxPage = pageover.getMaxPage();
																							pageInt = pageover.getPageInt();
																							pagesize = pageover.getPageSize();
																							link = "&exambatchId=" + exambatchId + "&type="
																									+ java.net.URLEncoder.encode(type, "UTF-8")
																									+ "&zong_title=" + zong_title + "&zong_type="
																									+ zong_type + "&zong_pageInt=" + zong_pageInt;
																							//----------分页结束---------------		
																							List questionList = interactionManage.getExamNews(pageover,
																									exambatchId, student_id);

																							Iterator it = questionList.iterator();
																							int i = 0;
																							while (it.hasNext()) {
																								OracleExamQuestion question = (OracleExamQuestion) it
																										.next();
																								i++;
																								String question_title = fixnull(question.getTitle());
																								String question_type = fixnull(question.getQuestionType());
																								String submituser_name = fixnull(question
																										.getSubmituserName());
																								String submit_date = fixnull(question.getPublishDate());
																								String click_num = fixnull(question.getClickNum());

																								String focus_flag = "取消关注";
																								String focus_link = "exam_answer_focus.jsp?question_id="
																										+ question.getId() + "&status=0" + "&zong_title="
																										+ java.net.URLEncoder.encode(zong_title, "UTF-8")
																										+ "&zong_type=" + zong_type + "&zong_pageInt="
																										+ zong_pageInt;
																					%>
																					<tr>
																						<td width="56%" height="24" class="list_wenti">
																							·
																							<a
																								href="/entity/bzz-students/exam/exam_answer_info.jsp?question_id=<%=question.getId()%>&zong_title=<%=java.net.URLEncoder.encode(zong_title, "UTF-8")%>&zong_type=<%=zong_type%>&zong_pageInt=<%=zong_pageInt%>"
																								target="mainer"> <%=subTitle(question_title, 30)%></a>
																						</td>
																						<td width="16%" align="center" class="list_wenti"><%=question_type%></td>
																						<td width="13%" align="center" class="list_wenti">
																							已解决
																						</td>
																						<%
																							if (!question.getSubmituserId().equals(student_id)) {
																						%>
																						<td width="16%" align="center" class="butten_grey">
																							<a href="<%=focus_link%>"><%=focus_flag%></a>
																						</td>
																						<%
																							} else {
																						%>
																						<td width="16%" align="center" class="butten_grey"></td>
																						<%
																							}
																						%>
																					</tr>
																					<tr>
																						<td colspan="4" class="grey_font">
																							提问者：<%=submituser_name%>
																							| 提问时间：<%=submit_date%>
																							| 浏览：<%=click_num%>人
																						</td>
																					</tr>
																					<%
																						}
																						}
																					%>

																				</table>
																		<tr>
																			<td class="content">
																				<%@ include file="../pub/dividepage.jsp"%>
																			</td>
																		</tr>
																	</table>
															<tr>
																<td align="center">
																	<input type="button" value="返回"
																		onClick="window.location='/entity/bzz-students/exam/student_exam_tutor_y.jsp?zong_title=<%=java.net.URLEncoder.encode(zong_title, "UTF-8")%>&zong_type=<%=zong_type%>&pageInt=<%=zong_pageInt%>'">
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
				</td>
			</tr>
		</table>
	</body>
</html>