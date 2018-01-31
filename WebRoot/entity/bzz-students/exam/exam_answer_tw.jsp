<%@ page language="java" import="java.net.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.whaty.platform.entity.*,com.whaty.platform.entity.user.*,com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@page import="com.whaty.platform.database.oracle.*"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page
	import="com.whaty.platform.info.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page
	import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page
	import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page
	import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page
	import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page
	import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page
	import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page
	import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.answer.*"%>
<%@ page
	import="com.whaty.platform.database.oracle.standard.interaction.exam.*"%>
<%@ include file="../pub/priv.jsp"%>

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
<%
	String status = (String) session.getAttribute("mock_login");
	if (status != null && status.equals("1")) {
%>
<script>alert('模拟学生登陆只能查看不能提问');window.history.back();</script>
<%
	}
%>
<%
	//String path = request.getContextPath();
	//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	UserSession userSession = (UserSession) session
			.getAttribute("user_session");
	if (userSession != null) {
	} else {
%>
<script>
	window.alert("长时间未操作或操作异常，为了您的账号安全，请重新登录！");
	window.location = "/";
</script>
<%
	return;
	}
	dbpool db = new dbpool();
	MyResultSet rs = null;

	String type = fixnull(request.getParameter("type"));
	String exambatchId = fixnull(request.getParameter("exambatchId"));
	String flag = fixnull(request.getParameter("flag"));

	String zong_type = fixnull(request.getParameter("zong_type"));
	String zong_title = java.net.URLDecoder.decode(fixnull(request
			.getParameter("zong_title")), "UTF-8");
	String zong_pageInt = request.getParameter("pageInt");
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
    function reload()
    {
    	document.tw.submit();
    }
	var bLoaded=false; 
	function chkSubmit()
	{
		
		if (bLoaded==false)
		{
			alert("表单正在下载")
			return false
		}
		
		if(frmAnnounce.title.value.length <1)
		{
			alert("标题好像忘记写了!");
			return false;
		}
		
		if(tw.type.value.length <1) {
			alert("问题类型好像忘记选择了！");
			return false;
		}
		
	   var oEditor = FCKeditorAPI.GetInstance('body') ;
       var acontent=oEditor.GetXHTML();
		
		if(acontent.length <2)
		{
			alert("问题内容为空，还是多写几句吧？");
			return false;
		}	
	}
</script>
	<%
		InteractionFactory interactionFactory = InteractionFactory
				.getInstance();
		InteractionManage interactionManage = interactionFactory
				.creatInteractionManage(interactionUserPriv);
	%>
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
														<strong>我要提问</strong>
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
											<form action="exam_answer_tw.jsp" method="POST" name="tw">
												<input type="hidden" id="exambatchId" name="exambatchId"
													value="<%=exambatchId%>">
												<input type="hidden" id="flag" name="flag" value="<%=flag%>">
												<input type="hidden" id="zong_type" name="zong_type"
													value="<%=zong_type%>">
												<input type="hidden" id="zong_title" name="zong_title"
													value="<%=java.net.URLEncoder.encode(zong_title, "UTF-8")%>">
												<input type="hidden" id="zong_pageInt" name="zong_pageInt"
													value="<%=zong_pageInt%>">
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0" class="fddy_sousuobg">
													<tr>
														<td width="20%" class="fddy_sousuo_font">
															<img src="/entity/bzz-students/exam/images/xytw_03.jpg"
																align="absmiddle" />
															选择类型:
														</td>
														<td width="28%" align="left">
															<select name="type" onchange="reload()">
																<option value="">
																	请选择...
																</option>
																<%
																	String sql = "select id,name from exam_question_type order by id";
																	rs = db.executeQuery(sql);
																	while (rs.next()) {
																		String qtId = rs.getString("id");
																		String qtName = rs.getString("name");
																%>
																<option value="<%=qtId%>"
																	<%if(qtId.equals(type)){out.print("selected");} %>><%=qtName%></option>
																<%
																	}
																	db.close(rs);
																%>
															</select>
														</td>
														<td width="60%" align="left">
															<font color="red" size="2">请先查看是否与已有问题重复，如无则请继续提问。</font>
														</td>
													</tr>
												</table>
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0" class="list_zibg">
													<tr>
														<td style="padding: 5px;">
															<table width="100%" border="0" cellspacing="0"
																cellpadding="0">
																<tr>
																	<td width="86%">
																		<table width="100%" border="0" cellspacing="0"
																			cellpadding="0" class="list_title">
																			<tr>
																				<td width="43%">
																					相关问题
																				</td>
																				<td width="1%">
																					<img
																						src="/entity/bzz-students/exam/images/line.jpg" />
																				</td>
																				<td width="12%">
																					问题类型
																				</td>
																				<td width="1%">
																					<img
																						src="/entity/bzz-students/exam/images/line.jpg" />
																				</td>
																				<td width="20%">
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
																									null, type);
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
																							String link = "&exambatchId="
																									+ URLEncoder.encode(exambatchId, "utf-8") + "&type=" + type
																									+ "&zong_title=" + zong_title + "&zong_type=" + zong_type
																									+ "zong_pageInt=" + zong_pageInt;
																							//----------分页结束---------------		
																							List questionList = interactionManage.getExamQuestions(pageover,
																									exambatchId, null, type);
																							Iterator it = questionList.iterator();
																							int t = 0;
																							while (it.hasNext()) {
																								OracleExamQuestion question = (OracleExamQuestion) it.next();
																								t++;
																						%>
																						<tr>
																							<%
																								if (flag.equals("1")) {
																							%>
																							<td width="42%" class="list_baitd">
																								·
																								<a
																									href="/entity/bzz-students/exam/exam_answer_info.jsp?question_id=<%=question.getId()%>&flag=<%=flag%>"
																									target="_blank"><%=fixnull(subTitle(question.getTitle(), 22))%></a>
																							</td>
																							<%
																								} else {
																							%>
																							<td width="42%" class="list_baitd">
																								·
																								<a
																									href="/entity/bzz-students/exam/exam_answer_info.jsp?question_id=<%=question.getId()%>"
																									target="mainer"><%=fixnull(subTitle(question.getTitle(), 22))%></a>
																							</td>
																							<%
																								}
																							%>
																							<td width="14%" align="center" class="list_baitd"><%=fixnull(question.getQuestionType())%></td>
																							<td width="22%" align="center" class="list_baitd"><%=fixnull(question.getPublishDate())%></td>
																							<td width="26%" align="center" class="list_baitd"><%=fixnull(question.getSubmituserName())%></td>
																						</tr>
																						<%
																							}
																							if (t < 10) {
																								for (int j = 0; j < 10 - t; j++) {
																						%>
																						<tr>
																							<td width="42%" class="list_baitd">
																								·
																							</td>
																							<td width="14%" align="center" class="list_baitd">
																								&nbsp;
																							</td>
																							<td width="22%" align="center" class="list_baitd">
																								&nbsp;
																							</td>
																							<td width="26%" align="center" class="list_baitd">
																								&nbsp;
																							</td>
																						</tr>
																						<%
																							}
																							}
																						%>

																					</table>
																			<tr>
																				<td class="content">
																					<%@ include file="../pub/dividepage_fddy.jsp"%>
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
											<form action="exam_answer_twexe.jsp" method="POST"
												name="frmAnnounce" onSubmit="return chkSubmit()">
												<input type="hidden" id="type" name="type" value="<%=type%>">
												<input type="hidden" id="exambatchId" name="exambatchId"
													value="<%=exambatchId%>">
												<input type="hidden" id="flag" name="flag" value="<%=flag%>">
												<input type="hidden" id="zong_type" name="zong_type"
													value="<%=zong_type%>">
												<input type="hidden" id="zong_title" name="zong_title"
													value="<%=java.net.URLEncoder.encode(zong_title, "UTF-8")%>">
												<input type="hidden" id="zong_pageInt" name="zong_pageInt"
													value="<%=zong_pageInt%>">

												<table width="100%" border="0" cellspacing="0"
													cellpadding="0" class="fddy_sousuobg"
													style="margin-top: 10px;">
													<tr>
														<td width="20%" class="fddy_sousuo_font">
															<img src="/entity/bzz-students/exam/images/xytw_07.jpg"
																align="absmiddle" />
															问题标题:
														</td>
														<td width="89%" align="left">
															<input name="title" type="text" />
															<font color="red">*请先选择问题类型</font>
														</td>
													</tr>
												</table>
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0" class="list_zibg">
													<tr>
														<td style="padding: 5px;">
															<table width="100%" border="0" cellspacing="0"
																cellpadding="0" class="" style="margin-left: 10px;">
																<tr>
																	<td width="13%" valign="top" class="fddy_sousuo_font"
																		style="padding-top: 10px;">
																		<img
																			src="/entity/bzz-students/exam/images/xytw_10.jpg"
																			align="absmiddle" />
																		<span style="margin-left: 12px;">问题内容:</span>
																	</td>
																	<td width="90%" align="left">
																		<textarea class="smallarea" cols="80" name="body"
																			rows="12"></textarea>
																	</td>
																</tr>
																<tr>
																	<td valign="top" class="fddy_sousuo_font">
																		&nbsp;
																	</td>
																	<!--  <td align="left">
							           <div class="jd_biaot1" >
							           	<a href="javascript:chkSubmit()"><font color="#a04400" style="font-weight:bold;font-size:14px;">提交回答</font></a>
							           </div>
									</td>-->

																	<td align=center width="700">
																		<input type=submit value="提交问题" id=sub>
																		&nbsp;&nbsp;
																		<%
																			if (flag.equals("1")) {
																		%>
																		<input type="button" value="关闭"
																			onClick="javascript:window.close()">
																		<%
																			} else {
																		%>
																		<input type="button" value="返回"
																			onClick="javascript:window.location='/entity/bzz-students/exam/student_exam_tutor_y.jsp?zong_title=<%=java.net.URLEncoder.encode(zong_title, "UTF-8")%>&zong_type=<%=zong_type%>&pageInt=<%=zong_pageInt%>'">
																		<%
																			}
																		%>
																	
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</form>
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
		<script>bLoaded=true;</script>
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
	</body>
</html>