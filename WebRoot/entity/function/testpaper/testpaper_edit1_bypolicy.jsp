<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.*"%>
<%@ page
	import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page
	import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page
	import="com.whaty.platform.test.*,com.whaty.platform.test.lore.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage"%>
<%@ page
	import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>

<%@ include file="../pub/priv.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title></title>
		<link href="../css/css.css" rel="stylesheet" type="text/css">
		<SCRIPT>
<%
		String paperId = request.getParameter("paperId");
%>
function cfmdel(link)
{
	if(confirm("您确定要删除这道题目吗？"))
		window.navigate(link);
}

function DetailInfo(type,id,id1)
{
	if(type=='YUEDU')
		window.open('paper_question_comprehension_info1.jsp?id='+id +'&id1='+id1,'','dialogWidth=350px;dialogHeight=330px');
	else 
		window.open('paper_question_info.jsp?id='+id,'','dialogWidth=350px;dialogHeight=330px');
	
}
function addSubmit()
{
	document.add.action="testpaper_editexe_bypolicy.jsp";
	document.add.submit();
}
function deleteSubmit()
{
	var flag = false;
	var form = document.forms['add'];
	
	for (var i = 0 ; i < form.elements.length; i++) {
		if ((form.elements[i].type == 'checkbox')) {
			if ( form.elements[i].checked == true) {
				flag = true;
				break;
			}
		}
	}
	if(flag == false){
		alert('请选择您要删除的题目。');
		return;
	}
	
	document.add.action="testpaper_del1_bypolicy_batch.jsp?paperid=<%=paperId%>";
	document.add.submit();
}
function doCommit()
{
  	addSubmit();
}
function listSelect(listName) {
	var form = document.forms['add'];
	var check = document.all(listName);
	for (var i = 0 ; i < form.elements.length; i++) {
		if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == listName+"Child")) {
			if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
				form.elements[i].checked = check.checked;
			}
		}
	}
	return true;
}
</SCRIPT>
	</head>
	<%!//判断字符串为空的话，赋值为""
	String fixnull(String str) {
		if (str == null || str.equals("null"))
			str = "";
		return str;

	}

	%>
	<body leftmargin="0" topmargin="0" class="scllbar">
		<%
			String pageInt = request.getParameter("pageInt");
			HashMap paperQuestions = (HashMap) session
					.getAttribute("paperQuestions");//到这里的paperQuestions的里面的内容还是正确的。
			if (paperQuestions == null) {
				paperQuestions = new HashMap();
				paperQuestions.put(TestQuestionType.DANXUAN, new ArrayList());
				paperQuestions.put(TestQuestionType.DUOXUAN, new ArrayList());
				paperQuestions.put(TestQuestionType.PANDUAN, new ArrayList());
				//paperQuestions.put(TestQuestionType.TIANKONG, new ArrayList());
				//paperQuestions.put(TestQuestionType.WENDA, new ArrayList());
				//paperQuestions.put(TestQuestionType.YUEDU, new ArrayList());
			}
			if(paperQuestions.get(TestQuestionType.TIANKONG)!=null )
				paperQuestions.remove(TestQuestionType.TIANKONG);
				
			Set set = paperQuestions.keySet();

			Object[] key=paperQuestions.keySet().toArray();
			Arrays.sort(key);
			
			
			
			
			//session.removeAttribute("paperQuestions");
		%>
		<table width="489" border="0" cellspacing="0" cellpadding="0"
			align="center">
			<tr>
				<td height="42" background="images/st_05.gif"
					style="padding-left:53px;padding-top:8px" class="text3">
					题型选择添加
				</td>
			</tr>
			<form name='add' method='post' class='nomargin' onsubmit="">
				<input type="hidden" name="paperId" value=<%=paperId%>>
			<tr>
				<td valign="top" background="images/st_06.gif">
					<table height="390" width="100%" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td valign="top" class="bg2">
								<table width="100%" border="0" cellspacing="0" cellpadding="6">
									<tr>
										<td valign="top" style="padding-left:30px">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<%
													String field = "";
													String typename = "";
													List questionList = null;
													int count = 1;
													int allScore =0;
													for(int jj=0;jj<key.length;jj++){
													field=(String)key[jj];
													typename=TestQuestionType.typeShow(field);
													
													//for (Iterator it = set.iterator(); it.hasNext();) {
													//	field = (String) it.next();
													//	typename = TestQuestionType.typeShow(field);
														questionList = (List) paperQuestions.get(field);//这样得到questionList的list。
												%>
												<tr>
													<td align="center">
														<table width="100%" border="0" cellpadding="0"
															cellspacing="0">
															<tr valign="bottom" class="mc1">
																<td width="10%" valign="middle">
																	<input type='checkbox' class='checkbox' 
																		name='<%=field%>' id="<%=field%>>" value='true'
																		 onClick="listSelect(name)">
																</td>
																<td colspan="2" width="45%" style="text-align: justify;">
																	<%=typename.trim()%>：
																</td>
																<td colspan="2" width="45%">
																	<a
																		href="testpaper_edit2_bypolicy.jsp?field=<%=field%>&paperId=<%=paperId%>&pageInt=<%=pageInt%>"
																		title="提交"><span unselectable="on">添加试题</span>
																	</a>
																</td>
															</tr>
															<%
																		for (int i = 0; i < questionList.size(); i++) {
																		PaperQuestion question = (PaperQuestion) questionList
																		.get(i);//这里得到是question是单个的题目。
																		allScore += Integer.parseInt(question.getReferenceScore());
																		String clickLink = "";
																		String pName = "";
																		String status = question.getStatus();
																		if (status != null && status.equals("old"))
																	pName = "paper";
																		else
																	pName = "store";
																		if (question.getType().equals("YUEDU")) {
																	clickLink = pName
																			+ "_question_comprehension_info.jsp?id="
																			+ question.getId() + "&id1=" + i;
																		} else {
																	clickLink = pName + "_question_info.jsp?id="
																			+ question.getId();
																		}
															%>
															<tr valign="bottom" class="mc1">
																<td width="15%" align="right" valign="middle">
																	<input type='checkbox' class='checkbox'
																		name='<%=field + "Child"%>' value='<%=i%>'	id='<%=fixnull(question.getId())%>' /></td>
																<td width="5%" align="right" valign="baseline">
																	<%=count++%>.
																</td>
																<td width="45%" valign="baseline">
																	<a href="<%=clickLink%>" title="查看" class="button"
																		target=_blank><span unselectable="on"><%=question.getTitle()%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																		<font color=red>(引用次数：<%=question.getNum() == null ? 0 : question.getNum()%>)</font>
																	</a>
																</td>
																<td width="20%" valign="bottom">
																	分值：
																	<input type="hidden" name="<%=question.getId()%>score" size="2" style="text-align:center" value='<%=question.getReferenceScore()%>' >
																	(<%=question.getReferenceScore()%>)
																	<%-- <%if(question.getScore()==null||question.getScore().equalsIgnoreCase("null")||question.getScore().equals("")) out.print(question.getReferenceScore()); else out.print(question.getScore());%> --%>
																	 </td>
																</td>
																<td valign="bottom" width="15%">
																	<!--
				                        <a href="javascript:cfmdel('testpaper_del1_bypolicy.jsp?field=<%--<%=field%>&i=<%=i%>&paperId=<%=paperId%>--%>')" title="提交" class="button"><span unselectable="on">删	除</span></a></span></td>
				                        -->
															</tr>
															<%
															}
															%>

														</table>
													</td>
												</tr>
												<%
												}
												%>
												<tr><td colspan="5"><font color=red>总分值：<%=allScore %></font> </td></tr>
											</table>
									</tr>
									<tr>
										<td>
											<table width="98%" height="100%" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td height="4">
														<img src="../images/page_bottomSlip.gif" width="100%"
															height="2">
													</td>
												</tr>
												<tr>
													<td align="center" valign="middle">
													<% if(allScore ==100){%>
														<img src="images/Confirm.gif" width="80" height="24"
															onclick="doCommit()">
													<%} else { %>
														<font size="2">题目总分值为100分才可提交</font>
													<%} %>
														&nbsp;&nbsp;&nbsp;
														<img src="images/Delete.gif" width="80" height="24"
															onclick="deleteSubmit()">
														&nbsp;&nbsp;&nbsp;
														<img src="images/Return.gif" width="80" height="24"
															onclick="window.location.href='testpaper_list.jsp?pageInt=<%=pageInt%>'">
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
				<td>
					<img src="images/st_07.gif" width="489" height="15">
				</td>
			</tr>
			</form>
		</table>
	</body>
</html>
