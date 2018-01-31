<%@ page language="java" import="java.net.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.whaty.platform.entity.*,com.whaty.platform.entity.user.*,com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@page import="com.whaty.platform.database.oracle.*"%>
<%@ page import="com.whaty.platform.interaction.exam.*"%>

<%@ page
	import="com.whaty.platform.database.oracle.standard.interaction.exam.*"%>
<%@ include file="../pub/priv.jsp"%>

<%!String fixnull1(String str) {
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
<%!String fixnull(String str) {
		if (str == null || str.equals("null") || str.equals("")) {
			str = "";
			return str;
		}
		return str;
	}%>

<%
	//String path = request.getContextPath();
	//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	dbpool db = new dbpool();
	MyResultSet rs = null;

	String zong_type = fixnull(request.getParameter("zong_type"));
	String zong_title = java.net.URLDecoder.decode(fixnull(request
			.getParameter("zong_title")), "UTF-8");
	String exambatchId = "";
	String answer_explain = "";
	String sql_batch = "select t.answer_explain,t.id\n"
			+ "  from pe_bzz_exambatch t\n"
			+ " where t.selected = '402880a91dadc115011dadfcf26b00aa'";//当前批次

	rs = db.executeQuery(sql_batch);
	if (rs != null && rs.next()) {
		exambatchId = rs.getString("id");
		answer_explain = fixnull(rs.getString("answer_explain"));
	}
	if (answer_explain.equals("")) {
		answer_explain = "暂无";
	}
	db.close(rs);

	InteractionFactory interactionFactory = InteractionFactory
			.getInstance();
	InteractionManage interactionManage = interactionFactory
			.creatInteractionManage(interactionUserPriv);
	String news_count = interactionManage.getExamNewsCount(exambatchId,
			student.getId());
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title></title>
		<link href="/entity/function/css/css.css" rel="stylesheet"
			type="text/css">
		<link href="/entity/bzz-students/exam/style.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript"
			src="/entity/bzz-students/exam/wsMenu.js"></script>
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.STYLE1 {
	font-size: 12px;
	color: #0041A1;
	font-weight: bold;
}

.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}

.kctx_zi1 {
	font-size: 12px;
	color: #0041A1;
}

.kctx_zi2 {
	font-size: 12px;
	color: #54575B;
	line-height: 24px;
	padding-top: 2px;
}
-->
</style>
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
														<strong>辅导答疑</strong>
													</td>
													<td width="300">
														&nbsp;
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<form name="search" method="post"
												action="/entity/bzz-students/exam/student_exam_tutor_y.jsp">
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0" bgcolor="#f0e0fd"
													style="margin-bottom: 5px;">
													<tr>
														<td valign="t">
															<table width="100%" border="0" cellspacing="0"
																cellpadding="0" style="margin: 5px;">
																<tr>
																	<td width="66%" class="fddy_ggl_tit">
																		<img
																			src="/entity/bzz-students/exam/images/fdda_03.jpg"
																			align="absmiddle" />
																		公告栏
																	</td>
																	<td width="34%" align="right" class="edit">
																		<font></font>
																		<%=student.getName()%>，您有
																		<font> <%
 	if (!news_count.equals("0")) {
 %> <a
																			href="/entity/bzz-students/exam/exam_answer_list.jsp?&type=<%=java.net.URLEncoder.encode("新消息", "UTF-8")%>&exambatchId=<%=exambatchId%>"
																			target="mainer"
																			style="text-decoration: none; font-weight: bold; color: #FF0033;">
																				<%=news_count%></a> <%
 	} else {
 %> <%=news_count%> <%
 	}
 %> </font>&nbsp;&nbsp;条新消息
																	</td>
																</tr>
															</table>
															<table width="99%" bgcolor="#000000" border="0"
																cellspacing="0" cellpadding="0"
																style="margin-left: 5px; margin-bottom: 5px;">
																<tr>
																	<td bgcolor="#FFFFFF"
																		style="border: 1px solid #d6b5f0; padding: 8px 0px;">

																		<%=answer_explain%>

																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0" class="ad_bg">
													<tr>
														<td width="60%" align="left">
															<img src="/entity/bzz-students/exam/images/fdda_07.jpg" />
														</td>
														<td width="40%" valign="bottom" align="left"
															style="background: url(/entity/bzz-students/exam/images/fdda_08.jpg) no-repeat right center;">
															<a href="/CourseImports/KaoQianChuanJiang/002/index.htm"
																target="_blank"> <img
																	src="/entity/bzz-students/exam/images/goin.jpg"
																	border="0" /> </a>
														</td>
													</tr>
												</table>

												<table width="100%" border="0" cellspacing="0"
													cellpadding="0" class="fddy_sousuobg">
													<tr>
														<td width="15%" class="fddy_sousuo_font">
															<img src="/entity/bzz-students/exam/images/fddy03.jpg"
																align="absmiddle" />
															标题：
														</td>
														<td width="32%" align="left">
															<input name="zong_title" type="text"
																value="<%=zong_title%>" />
														</td>
														<td width="27%" align="left">
															<select name="zong_type" id="zong_type">
																<option value="">
																	请选择问题类型
																</option>
																<%
																	String sql = "select id,name from exam_question_type order by id";
																	rs = db.executeQuery(sql);
																	while (rs.next()) {
																		String qtId = rs.getString("id");
																		String qtName = rs.getString("name");
																%>
																<option value="<%=qtId%>"
																	<%if(qtId.equals(zong_type)){out.print("selected");} %>><%=qtName%></option>
																<%
																	}
																	db.close(rs);
																%>
															</select>
														</td>
														<td width="32%" align="left">
															<a href="javascript:document.search.submit()"><img
																	src="/entity/bzz-students/exam/images/fddy02.jpg" /> </a>
														</td>
													</tr>
												</table>
												<input type="hidden" name="zong_type" value=<%=zong_type%>>
												<input type="hidden" name="zong_title"
													value=<%=java.net.URLEncoder.encode(zong_title, "UTF-8")%>>
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0" class="list_zibg">
													<tr>
														<td style="padding: 5px;">
															<table width="100%" border="0" cellspacing="0"
																cellpadding="0">
																<tr>
																	<td width="80%">
																		<table width="100%" border="0" cellspacing="0"
																			cellpadding="0" class="list_title">
																			<tr>
																				<td width="40%">
																					相关问题
																				</td>
																				<td width="1%">
																					<img
																						src="/entity/bzz-students/exam/images/line.jpg" />
																				</td>
																				<td width="16%">
																					问题类型
																				</td>
																				<td width="1%">
																					<img
																						src="/entity/bzz-students/exam/images/line.jpg" />
																				</td>
																				<td width="16%">
																					发布时间
																				</td>
																				<td width="1%">
																					<img
																						src="/entity/bzz-students/exam/images/line.jpg" />
																				</td>
																				<td width="25%">
																					发布人
																				</td>
																			</tr>
																		</table>
																		<table width="100%" border="0" cellspacing="0"
																			cellpadding="0" class="list_baibg">
																			<tr>
																				<td style="padding: 0px 10px 10px 10px;">
																					<table width="100%" border="0" cellspacing="0"
																						cellpadding="0">
																						<%
																							int totalItems = interactionManage.getExamQuestionsNum(exambatchId,
																									zong_title, zong_type);
																							//----------分页开始---------------
																							//String spagesize = (String) session.getValue("pagesize");
																							String spagesize = "10";
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
																							String link = "&title=" + URLEncoder.encode(zong_title, "utf-8")
																									+ "&zong_type=" + zong_type;
																							//----------分页结束---------------		
																							List questionList = interactionManage.getExamQuestions(pageover,
																									exambatchId, zong_title, zong_type);
																							Iterator it = questionList.iterator();
																							int t = 0;
																							while (it.hasNext()) {
																								OracleExamQuestion question = (OracleExamQuestion) it.next();
																								t++;
																						%>
																						<tr>
																							<td width="41%" class="list_baitd">
																								·
																								<a
																									href="/entity/bzz-students/exam/exam_answer_info.jsp?&question_id=<%=question.getId()%>&zong_title=<%=java.net.URLEncoder.encode(zong_title, "UTF-8")%>&zong_type=<%=zong_type%>&zong_pageInt=<%=pageInt%>"
																									target="mainer"><%=fixnull(subTitle(question.getTitle(), 16))%></a>
																							</td>
																							<td width="17%" align="center" class="list_baitd"><%=fixnull(question.getQuestionType())%></td>
																							<td width="17%" align="center" class="list_baitd"><%=fixnull(question.getPublishDate())%></td>
																							<td width="25%" align="center" class="list_baitd"><%=fixnull(question.getSubmituserName())%>
																							</td>
																						</tr>

																						<%
																							}
																							if (t < 10) {
																								for (int j = 0; j < 10 - t; j++) {
																						%>
																						<tr>
																							<td width="41%" class="list_baitd">
																								·
																							</td>
																							<td width="17%" align="center" class="list_baitd">
																								&nbsp;
																							</td>
																							<td width="17%" align="center" class="list_baitd">
																								&nbsp;
																							</td>
																							<td width="25%" align="center" class="list_baitd">
																								&nbsp;
																							</td>
																						</tr>
																						<%
																							}
																							}
																						%>
																					</table>
																				</td>
																			</tr>
																			<tr>
																				<td class="content">
																					<%@ include file="../pub/dividepage_fddy.jsp"%>
																				</td>
																			</tr>
																		</table>

																	</td>
																	<td width="14%" valign="top" style="padding-left: 5px;">
																		<table width="100%" border="0" cellspacing="0"
																			cellpadding="0">
																			<tr>
																				<td id="menu0" status="off" class="cd1-on"
																					onMouseOver="overMenu(this)"
																					onMouseOut="outMenu(this)"
																					onClick="registMenu(this);">
																					<a
																						href="/entity/bzz-students/exam/exam_answer_tw.jsp?exambatchId=<%=exambatchId%>&zong_title=<%=java.net.URLEncoder.encode(zong_title, "UTF-8")%>&zong_type=<%=zong_type%>&zong_pageInt=<%=pageInt%>"
																						target="mainer" style="text-decoration: none;"}>
																						<font color="#a04400"
																						style="font-weight: bold; font-size: 12px">我要提问</font>
																					</a>
																				</td>
																			</tr>
																			<tr>

																				<td id="menu1" status="off" class="cd2-off"
																					onMouseOver="overMenu(this)"
																					onMouseOut="outMenu(this)"
																					onClick="registMenu(this);">
																					<a
																						href="/entity/bzz-students/exam/exam_answer_list.jsp?&type=<%=java.net.URLEncoder.encode("我的问题", "UTF-8")%>&exambatchId=<%=exambatchId%>&zong_title=<%=java.net.URLEncoder.encode(zong_title, "UTF-8")%>&zong_type=<%=zong_type%>&zong_pageInt=<%=pageInt%>"
																						target="mainer" style="text-decoration: none;"><font
																						color="#a04400"
																						style="font-weight: bold; font-size: 12px">我的问题</font>
																					</a>
																				</td>
																			</tr>
																			<tr>
																				<td id="menu2" status="off" class="cd3-off"
																					onMouseOver="overMenu(this)"
																					onMouseOut="outMenu(this)"
																					onClick="registMenu(this);">
																					<a
																						href="/entity/bzz-students/exam/exam_answer_list.jsp?&type=<%=java.net.URLEncoder.encode("我的关注", "UTF-8")%>&exambatchId=<%=exambatchId%>&zong_title=<%=java.net.URLEncoder.encode(zong_title, "UTF-8")%>&zong_type=<%=zong_type%>&zong_pageInt=<%=pageInt%>"
																						target="mainer" style="text-decoration: none;">
																						<font color="#a04400"
																						style="font-weight: bold; font-size: 12px">我的关注</font>
																					</a>
																				</td>
																			</tr>
																			<tr>
																				<td id="menu3" status="off" class="cd4-off"
																					onMouseOver="overMenu(this)"
																					onMouseOut="outMenu(this)"
																					onClick="registMenu(this);">
																					<a
																						href="/entity/bzz-students/exam/exam_answer_list.jsp?&type=<%=java.net.URLEncoder.encode("已解决问题", "UTF-8")%>&exambatchId=<%=exambatchId%>&zong_title=<%=java.net.URLEncoder.encode(zong_title, "UTF-8")%>&zong_type=<%=zong_type%>&zong_pageInt=<%=pageInt%>"
																						target="mainer" style="text-decoration: none;">
																						<font color="#a04400"
																						style="font-weight: bold; font-size: 12px">已解决问题</font>
																					</a>
																				</td>
																			</tr>
																			<tr>
																				<td id="menu4" status="off" class="cd5-off"
																					onMouseOver="overMenu(this)"
																					onMouseOut="outMenu(this)"
																					onClick="registMenu(this);">
																					<a
																						href="/entity/bzz-students/exam/exam_answer_list.jsp?&type=<%=java.net.URLEncoder.encode("待回答问题", "UTF-8")%>&exambatchId=<%=exambatchId%>&zong_title=<%=java.net.URLEncoder.encode(zong_title, "UTF-8")%>&zong_type=<%=zong_type%>&zong_pageInt=<%=pageInt%>"
																						target="mainer" style="text-decoration: none;">
																						<font color="#a04400"
																						style="font-weight: bold; font-size: 12px">待回答问题</font>
																					</a>
																				</td>
																			</tr>
																		</table>

																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</form>
										</td>
									</tr>
									<tr>
										<td>
											<br/>
											<br/>
										</td>
									</tr>
									<tr>

									</tr>
								</table>
							</td>
						</tr>

					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
