<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*,com.whaty.util.string.encode.*"%>
<jsp:directive.page import="java.net.URLDecoder" />
<%@ include file="../pub/priv.jsp"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="com.whaty.platform.entity.bean.SsoUser"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>问答题</title>
		<link href="../css/css.css" rel="stylesheet" type="text/css">
		<script>

		
	function onCommit(){
	<%
		//Lee start 记录开始答题时间
		ServletActionContext.getRequest().getSession().setAttribute("flag","true");
		SsoUser ssoUser = us.getSsoUser();
		Object refreshString = ServletActionContext.getRequest().getSession().getAttribute(ssoUser.getId()+"startTimes");
		refreshString = (refreshString == null|| "".equals(refreshString))?0:(Integer.parseInt(refreshString.toString()) + 1);
		if("1".equals(refreshString.toString())){
			String sysDateSql = "SELECT TO_CHAR(SYSDATE,'yyyy-MM-dd hh24:mi:ss') TOD FROM DUAL";
			dbpool db4 = new dbpool();
			MyResultSet mrs4 = db4.execuQuery(sysDateSql);
			String testOpenDate = "SYSDATE";
			while(mrs4.next()){
				testOpenDate = mrs4.getString("TOD");
			}
			db4.close(mrs4);
			db4 = null;
			ServletActionContext.getRequest().getSession().setAttribute(ssoUser.getId() + "startTime", testOpenDate);
		}
		ServletActionContext.getRequest().getSession().setAttribute(ssoUser.getId()+"startTimes",refreshString);
		//记录提交测验时间
		String sysDateSql = "SELECT TO_CHAR(SYSDATE,'yyyy-MM-dd hh24:mi:ss') TOD FROM DUAL";
		dbpool db4 = new dbpool();
		MyResultSet mrs4 = db4.execuQuery(sysDateSql);
		String testSubmitTime = "SYSDATE";
		while(mrs4.next()){
			testSubmitTime = mrs4.getString("TOD");
		}
		db4.close(mrs4);
		db4 = null;
		ServletActionContext.getRequest().getSession().setAttribute(ssoUser.getId() + "submitTime", testSubmitTime);
		//Lee end
	%>
		var inputs = document.getElementsByTagName("input");//获取所有的input标签对象
		var checkboxArray = [];//初始化空数组，用来存放checkbox对象。
			for(var i=0;i<inputs.length;i++){ 
			 var obj = inputs[i];  
				 if(obj.type=='checkbox'){
				     checkboxArray.push(obj); 
			     }
			 }//所有的checkbox对象都存在checkboxArray这个数组里了。
		
		var radioArray=[];
			for(var i=0;i<inputs.length;i++){ 
				 var obj = inputs[i];  
					 if(obj.type=='radio'){
					     radioArray.push(obj); 
				     }
			}//所有的radio对象都存在 radioArray数组中
		var temp =0;
		
		for(var j=0;j<radioArray.length;j++){
			if(radioArray[j].checked){
				temp++;
				 if(confirm("您确定要提交吗")){
				document.answers.submit();
				document.getElementById('hidden').disabled = true;
				return ;
				 }else{
				 return false ;
				 }
			}
			
		}
		for(var j=0;j<checkboxArray.length;j++){
				if(checkboxArray[j].checked){
					temp++;
				 if(confirm("您确定要提交吗")){
				document.answers.submit();
				document.getElementById('hidden').disabled = true;
				return ;
				 }else{
				 return false ;
				 }
				}
		}
		if(temp==0){
			if(confirm("该试卷尚未作答，您确定要提交吗")){
				document.answers.submit();
				document.getElementById('hidden').disabled = true;
			}
			return false ;
		}
		if(confirm("您确定要提交吗")){
			document.answers.submit();
			document.getElementById('hidden').disabled = true;
		}else{
			return false ;
		}
	}
	function numberTo(num)
	{
	    switch (num)
	    {
	    	case 1:return "一";break;
	   		case 2:return "二";break;
	    	case 3:return "三";break;
	    	case 4:return "四";break;
	    	default:break;
    	}
	    
	}
	
</script>
	</head>

	<body>
		<form id="answers" name="answers" action="question_infoexe.jsp" method="post">
			<table width="98%" border="0" cellspacing="0" cellpadding="0">
				<%!//List乱序
	// swaps array elements i and j
	public static void exch(List list, int i, int j) {
		Object swap = list.get(i);
		list.set(i, list.get(j));
		list.set(j, swap);
	}

	// take as input an array of strings and rearrange them in random order
	public static void shuffle(List list) {
		int N = list.size();
		for (int i = 0; i < N; i++) {
			int r = (new Random().nextInt(N)); // between i and N-1
			exch(list, i, r);
		}
	}

	public static void exch2(List list, int i, int j) {
		Object swap = list.get(i);
		Object swap1 = list.get(i + 1);
		list.set(i, list.get(j));
		list.set(i + 1, list.get(j + 1));
		list.set(j, swap);
		list.set(j + 1, swap1);
	}

	// take as input an array of strings and rearrange them in random order
	public static void shuffle2(List list) {
		int N = list.size();
		//引文list中存的比较乱，所以乱序的循环判断也就一样的乱
		//（题目信息list中第0个为题目的标题，第1个是选项A的索引，第2个是选项A的内容，第3个是B索引，依此类推。。。。。）
		for (int i = 1; i < N - 2; i = i + 2) {
			int temp = new Random().nextInt(N - 3) + 1;
			//System.out.println(N-2);
			//System.out.println("temp--"+temp);
			if (temp % 2 == 0) {
				temp += 1;
			}
			int r = temp;
			exch2(list, i, r);
		}
	}%>
				<%
					session.setAttribute("openCourseId", openCourse.getId()); //防止学生在交卷过程中进入另一门课程交流园地重置了session里的opencourse

					HashMap standardAnswer = (HashMap) session.getAttribute("StandardAnswer");

					List singleList = (ArrayList) session.getAttribute("singleList"); //
					shuffle(singleList);//乱序
					List multiList = (ArrayList) session.getAttribute("multiList");
					shuffle(multiList);//乱序
					List judgeList = (ArrayList) session.getAttribute("judgeList");
					shuffle(judgeList);//乱序
					List blankList = (ArrayList) session.getAttribute("blankList");
					List answerList = (ArrayList) session.getAttribute("answerList");
					List comprehensionList = (ArrayList) session.getAttribute("comprehensionList");
					int a = 0;
					String da = "";
					int qNum = 1;
					try {
						if (singleList != null && singleList.size() > 0) {
							a++;
							switch (a) {
							case 1:
								da = "一";
								break;
							case 2:
								da = "二";
								break;
							case 3:
								da = "三";
								break;
							case 4:
								da = "四";
								break;
							case 5:
								da = "五";
								break;
							default:
								break;
							}
				%>
				<tr>
					<td height="42" style="padding-left: 23px; padding-top: 8px" class="text3">
						<font size="5"><%=da%>、单项选择题</font>
					</td>
				</tr>
				<%
					for (int t = 0; t < singleList.size(); t++) {
								PaperQuestion pq = (PaperQuestion) singleList.get(t);
								String score1 = pq.getReferenceScore();//这个才应该是得到分数的。果然是对的。
								String coreXML = pq.getQuestionCore();
								String remark = pq.getRemark();//2015-8-10 加入题目描述
								String id = pq.getId(); //String score1 = pq.getScore();  //在这里得到分数的时候已经错了。
								List list = XMLParserUtil.parserSingleMulti(coreXML);
								shuffle2(list);
								String body ="";
								String standard_answer ="";
								if(list !=null && list.size()>0){
									body = HtmlEncoder.decode((String) list.get(0));
								 	standard_answer = HtmlEncoder.decode((String) list.get(list.size() - 1));
									standardAnswer.put(id, standard_answer);
									}
				%>
				<tr>
					<td valign="top" style="padding-left: 23px; padding-top: 8px">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td valign="top" class="bg2">
									<table width="100%" border="0" cellspacing="0" cellpadding="6">
										<tr>
											<td width="100%" align="left" class="content1">
												<font size='4'><%=(qNum++) + ". " + body%></font>
											</td>
										</tr>
										<tr>
											<td width="100%" valign="top" align="left" style="padding-left: 10px">
												<table width="600" border="0" cellspacing="0" cellpadding="0">
													<%
														int idx = 65;
																	for (int i = 1; i < list.size() - 2; i = i + 2) {
																		String index = (String) list.get(i);
																		String content = HtmlEncoder.decode((String) list.get(i + 1));
																		System.out.println();
													%>
													<tr>
														<td>
															&nbsp;&nbsp;&nbsp;
														</td>
														<td width="40" class="mc1" style="padding-top: 5px; margin-top: 0px" valign="top">
															<input type="radio" name="single_<%=id%>" value="<%=index%>">
														</td>
														<td width="550" class="mc1"><%=(char) idx + ". " + content%></td>
													</tr>
													<%
														idx++;
																	}
													%>

												</table>
											</td>
										</tr>
									
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<%
					}
						}

						if (multiList != null && multiList.size() > 0) {
							a++;
							switch (a) {
							case 1:
								da = "一";
								break;
							case 2:
								da = "二";
								break;
							case 3:
								da = "三";
								break;
							case 4:
								da = "四";
								break;
							case 5:
								da = "五";
								break;
							default:
								break;
							}
				%>
				<tr>
					<td height="42" style="padding-left: 23px; padding-top: 8px" class="text3">
						<font size="5"><%=da%>、多项选择题</font>
					</td>
				</tr>
				<%
					for (int t = 0; t < multiList.size(); t++) {
								PaperQuestion pq = (PaperQuestion) multiList.get(t);
								String coreXML = pq.getQuestionCore();
								String id = pq.getId();
								String remark = pq.getRemark();
								List list = XMLParserUtil.parserSingleMulti(coreXML);
								shuffle2(list);
								String body ="";
								String standard_answer ="";
								if(list !=null && list.size()>0){
									body = HtmlEncoder.decode((String) list.get(0));
								 	standard_answer = HtmlEncoder.decode((String) list.get(list.size() - 1));
									standardAnswer.put(id, standard_answer);
									}
				%>
				<tr>
					<td valign="top" style="padding-left: 23px; padding-top: 8px">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td valign="top" class="bg2">
									<table width="100%" border="0" cellspacing="0" cellpadding="6">
										<tr>
											<td width="100%" align="left" class="content1">
												<font size='4'><%=(qNum++) + ". " + body%></font>
											</td>
										</tr>
										<tr>
											<td width="100%" valign="top" align="left" style="padding-left: 10px">
												<table width="600" border="0" cellspacing="0" cellpadding="0">
													<%
														int idx = 65;
																	for (int i = 1; i < list.size() - 2; i = i + 2) {
																		String index = (String) list.get(i);
																		String content = HtmlEncoder.decode((String) list.get(i + 1));
													%>
													<tr>
														<td>
															&nbsp;&nbsp;&nbsp;
														</td>
														<td width="40" class="mc1" style="padding-top: 5px; margin-top: 0px" valign="top">
															<input type="checkbox" name="multi_<%=id%>" value="<%=index%>">
														</td>
														<td width="550" class="mc1"><%=(char) idx + ". " + content%></td>
													</tr>
													<%
														idx++;
																	}
													%>

												</table>
											</td>
										</tr>
										
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<%
					}
						}

						if (judgeList != null && judgeList.size() > 0) {
							a++;
							switch (a) {
							case 1:
								da = "一";
								break;
							case 2:
								da = "二";
								break;
							case 3:
								da = "三";
								break;
							case 4:
								da = "四";
								break;
							case 5:
								da = "五";
								break;
							default:
								break;
							}
				%>
				<tr>
					<td height="42" style="padding-left: 23px; padding-top: 8px" class="text3">
						<font size="5"><%=da%>、判断题</font>
					</td>
				</tr>
				<%
					for (int t = 0; t < judgeList.size(); t++) {
								PaperQuestion pq = (PaperQuestion) judgeList.get(t);
								String coreXML = pq.getQuestionCore();
								String id = pq.getId();
								String remark =pq.getRemark();
								List list = XMLParserUtil.parserSingleMulti(coreXML);
								String body ="";
								String standard_answer ="";
								if(list !=null && list.size()>0){
									body = HtmlEncoder.decode((String) list.get(0));
								 	standard_answer = HtmlEncoder.decode((String) list.get(list.size() - 1));
									standardAnswer.put(id, standard_answer);
									}
				%>
				<tr>
					<td valign="top" style="padding-left: 23px; padding-top: 8px">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td valign="top" class="bg2">
									<table width="100%" border="0" cellspacing="0" cellpadding="6">
										<tr>
											<td width="100%" align="left" class="content1">
												<font size='4'><%=(qNum++) + ". " + body%></font>
											</td>
										</tr>
										<tr>
											<td width="100%" valign="top" align="left" style="padding-left: 10px">
												<table width="600" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td>
															&nbsp;&nbsp;&nbsp;
														</td>
														<td width="40" class="mc1" style="padding-top: 5px; margin-top: 0px" valign="top">
															<input type="radio" name="judge_<%=id%>" value="1">
														</td>
														<td width="550" class="mc1">
															正确
														</td>
													</tr>
													<tr>
														<td>
															&nbsp;&nbsp;&nbsp;
														</td>
														<td width="40" class="mc1" style="padding-top: 5px; margin-top: 0px" valign="top">
															<input type="radio" name="judge_<%=id%>" value="0">
														</td>
														<td width="550" class="mc1">
															错误
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
				<%
					}
						}

						if (blankList != null && blankList.size() > 0) {
							a++;
							switch (a) {
							case 1:
								da = "一";
								break;
							case 2:
								da = "二";
								break;
							case 3:
								da = "三";
								break;
							case 4:
								da = "四";
								break;
							case 5:
								da = "五";
								break;
							default:
								break;
							}
				%>
				<tr>
					<td height="42" style="padding-left: 23px; padding-top: 8px" class="text3">
						<font size="5"><%=da%>、填空题</font>
					</td>
				</tr>
				<%
					for (int t = 0; t < blankList.size(); t++) {
								PaperQuestion pq = (PaperQuestion) blankList.get(t);
								String coreXML = pq.getQuestionCore();
								String id = pq.getId();
								List list = XMLParserUtil.parserSingleMulti(coreXML);
								String body = HtmlEncoder.decode((String) list.get(0));
								String standard_answer = HtmlEncoder.decode((String) list.get(list.size() - 1));
								standardAnswer.put(id, standard_answer);
				%>
				<tr>
					<td valign="top" style="padding-left: 23px; padding-top: 8px">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td valign="top" class="bg2">
									<table width="100%" border="0" cellspacing="0" cellpadding="6">
										<tr>
											<td width="100%" align="left" class="content1">
												<font size='4'><%=(qNum++) + ". " + body%></font>
											</td>
										</tr>
										<tr>
											<td width="100%" valign="top" align="left" style="padding-left: 10px">
												<table width="600" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td>
															&nbsp;&nbsp;&nbsp;
														</td>
														<td width="0%" class="mc1" style="padding-top: 5px; margin-top: 0px" valign="top"></td>
														<td width="100%" class="mc1">
															<textarea name="blank_<%=id%>"></textarea>
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
				<%
					}
						}

						if (answerList != null && answerList.size() > 0) {
							a++;
							switch (a) {
							case 1:
								da = "一";
								break;
							case 2:
								da = "二";
								break;
							case 3:
								da = "三";
								break;
							case 4:
								da = "四";
								break;
							case 5:
								da = "五";
								break;
							default:
								break;
							}
				%>
				<tr>
					<td height="42" style="padding-left: 23px; padding-top: 8px" class="text3"><%=da%>、问答题
					</td>
				</tr>
				<%
					for (int t = 0; t < answerList.size(); t++) {
								PaperQuestion pq = (PaperQuestion) answerList.get(t);
								String coreXML = pq.getQuestionCore();
								String id = pq.getId();
								List list = XMLParserUtil.parserSingleMulti(coreXML);
								String body ="";
								if(list !=null && list.size()>0){
								body = HtmlEncoder.decode((String) list.get(0));
								}
								String standard_answer = HtmlEncoder.decode((String) list.get(list.size() - 1));
								standardAnswer.put(id, standard_answer);
				%>
				<tr>
					<td valign="top" style="padding-left: 23px; padding-top: 8px">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td valign="top" class="bg2">
									<table width="100%" border="0" cellspacing="0" cellpadding="6">
										<tr>
											<td width="100%" align="left" class="content1"><%=(qNum++) + ". " + body%></td>
										</tr>
										<tr>
											<td width="100%" valign="top" align="left" style="padding-left: 10px">
												<table width="600" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td>
															&nbsp;&nbsp;&nbsp;
														</td>
														<td width="0%" class="mc1" style="padding-top: 5px; margin-top: 0px" valign="top"></td>
														<td width="30%" class="mc1">
															<textarea name="answer_<%=id%>" rows="10" cols="50"></textarea>
														</td>
														<td style="padding-left: 5px"></td>
														<td width="70%" valign="bottom" style="padding-left: 5px"></td>
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
				<%
					}
						}

						if (comprehensionList != null && comprehensionList.size() > 0) {
							a++;
							switch (a) {
							case 1:
								da = "一";
								break;
							case 2:
								da = "二";
								break;
							case 3:
								da = "三";
								break;
							case 4:
								da = "四";
								break;
							case 5:
								da = "五";
								break;
							default:
								break;
							}
				%>
				<tr>
					<td height="42" style="padding-left: 23px; padding-top: 8px" class="text3"><%=da%>、案例分析题
					</td>
				</tr>
				<%
					HashMap Score = (HashMap) session.getAttribute("Score");
							HashMap Title = (HashMap) session.getAttribute("Title");
							for (int t = 0; t < comprehensionList.size(); t++) {
								PaperQuestion pq = (PaperQuestion) comprehensionList.get(t);
								String id = pq.getId();
								String questionCoreXml = pq.getQuestionCore();
								List coreList = XMLParserUtil.parserComprehension(questionCoreXml);
								String bodyString = HtmlEncoder.decode((String) coreList.get(0));
								List scoreList = new ArrayList();
								List titleList = new ArrayList();
								List standard_answer = new ArrayList();
								standard_answer.add("");
								titleList.add(pq.getTitle());
								scoreList.add(pq.getScore());
				%>
				<INPUT type="hidden" name="comp_<%=id%>_totalNum" value="<%=coreList.size() - 1%>">
				<tr>
					<td valign="top" style="padding-left: 23px; padding-top: 8px">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td valign="top" class="bg2">
									<table width="400" border="0" cellspacing="0" cellpadding="6">
										<tr>
											<td class="content1" width="100%"><%=(qNum++) + ". " + bodyString%></td>
										</tr>
										<%
											int i = 1;
														for (; i < coreList.size(); i++) {
															List subList = (List) coreList.get(i);
															String subType = HtmlEncoder.decode((String) subList.get(0));
															String subTitle = HtmlEncoder.decode((String) subList.get(1));
															String subTime = HtmlEncoder.decode((String) subList.get(2));
															String subScore = HtmlEncoder.decode((String) subList.get(3));
															String subBody = HtmlEncoder.decode((String) subList.get(4));
															String subAnswer = HtmlEncoder.decode((String) subList.get(subList.size() - 1));
															if (subType.equals(TestQuestionType.PANDUAN)) {
																if (subAnswer.equals("1"))
																	subAnswer = "正确";
																else
																	subAnswer = "错误";
															}
															standard_answer.add(subAnswer);
															titleList.add(subTitle);
															scoreList.add(subScore);

															if (subType.equals(TestQuestionType.DANXUAN)) {
										%>
										<tr>
											<td width="100%" valign="top" style="padding-left: 10px">
												<table width="600" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td width="100%" class="mc1"><%=i%>．<%=subTitle%></td>
													</tr>
													<tr>
														<td width="100%" class="mc1">
															&nbsp;&nbsp;<%=subBody%></td>
													</tr>
													<tr>
														<td width="100%" valign="top" style="padding-left: 10px">
															<table width="600" border="0" cellspacing="0" cellpadding="0">
																<%
																	for (int j = 5; j < subList.size() - 2; j = j + 2) {
																							String index = (String) subList.get(j);
																							String content = HtmlEncoder.decode((String) subList.get(j + 1));
																%>
																<tr>
																	<td>
																		&nbsp;&nbsp;&nbsp;
																	</td>
																	<td width="40" class="mc1" style="padding-top: 5px; margin-top: 0px" valign="top">
																		<input type="radio" name="comp_<%=id%>_<%=i%>" value="<%=index%>">
																	</td>
																	<td width="550" class="mc1"><%=index + ". " + content%></td>
																</tr>
																<%
																	}
																%>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<%
											} else if (subType.equals(TestQuestionType.DUOXUAN)) {
										%>
										<tr>
											<td width="100%" valign="top" style="padding-left: 10px">
												<table width="600" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td width="100%" class="mc1"><%=i%>．<%=subTitle%></td>
													</tr>
													<tr>
														<td width="100%" class="mc1">
															&nbsp;&nbsp;<%=subBody%></td>
													</tr>
													<tr>
														<td width="100%" valign="top" style="padding-left: 10px">
															<table width="600" border="0" cellspacing="0" cellpadding="0">
																<%
																	for (int j = 5; j < subList.size() - 2; j = j + 2) {
																							String index = (String) subList.get(j);
																							String content = HtmlEncoder.decode((String) subList.get(j + 1));
																%>
																<tr>
																	<td>
																		&nbsp;&nbsp;&nbsp;
																	</td>
																	<td width="40" class="mc1" style="padding-top: 5px; margin-top: 0px" valign="top">
																		<input type="checkbox" name="comp_<%=id%>_<%=i%>" value="<%=index%>">
																	</td>
																	<td width="550" class="mc1"><%=index + ". " + content%></td>
																</tr>
																<%
																	}
																%>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<%
											} else if (subType.equals(TestQuestionType.PANDUAN)) {
										%>
										<tr>
											<td width="100%" valign="top" style="padding-left: 10px">
												<table width="100%" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td width="100%" class="mc1"><%=i%>．<%=subTitle%></td>
													</tr>
													<tr>
														<td width="100%" class="mc1">
															&nbsp;&nbsp;<%=subBody%></td>
													</tr>
													<tr>
														<td width="100%" valign="top" style="padding-left: 10px">
															<table width="100%" border="0" cellspacing="0" cellpadding="0">
																<tr>
																	<td>
																		&nbsp;&nbsp;&nbsp;
																	</td>
																	<td width="100%" class="mc1" style="padding-top: 5px; margin-top: 0px" valign="top">
																		<input type="radio" name="comp_<%=id%>_<%=i%>" value="正确">
																		正确&nbsp;
																		<input type="radio" name="comp_<%=id%>_<%=i%>" value="错误">
																		错误
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<%
											} else if (subType.equals(TestQuestionType.TIANKONG)) {
										%>
										<tr>
											<td width="100%" valign="top" style="padding-left: 10px">
												<table width="100%" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td width="100%" class="mc1"><%=i%>．<%=subTitle%></td>
													</tr>
													<tr>
														<td width="100%" class="mc1">
															&nbsp;&nbsp;<%=subBody%></td>
													</tr>
													<tr>
														<td width="100%" valign="top" style="padding-left: 10px">
															<table width="100%" border="0" cellspacing="0" cellpadding="0">
																<tr>
																	<td>
																		&nbsp;&nbsp;&nbsp;
																	</td>
																	<td width="100%" class="mc1">
																		<textarea name="comp_<%=id%>_<%=i%>" cols="40" rows="5"></textarea>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<%
											} else if (subType.equals(TestQuestionType.WENDA)) {
										%>
										<tr>
											<td width="100%" valign="top" style="padding-left: 10px">
												<table width="100%" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td width="100%" class="mc1"><%=i%>．<%=subTitle%></td>
													</tr>
													<tr>
														<td width="100%" class="mc1">
															&nbsp;&nbsp;<%=subBody%></td>
													</tr>
													<tr>
														<td width="100%" valign="top" style="padding-left: 10px">
															<table width="100%" border="0" cellspacing="0" cellpadding="0">
																<tr>
																	<td width="100%" class="mc1">
																		<textarea name="comp_<%=id%>_<%=i%>" cols="40" rows="5"></textarea>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<%
											}
														}
														standardAnswer.put(id, standard_answer);
														Score.put(id, scoreList);
														Title.put(id, titleList);
														session.setAttribute("Score", Score);
														session.setAttribute("Title", Title);
														session.setAttribute("StandardAnswer", standardAnswer);
										%>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<%
					}
						}
					} catch (Exception e) {
						out.print(e.toString());
						e.printStackTrace();
					}
				%>
				<tr>
					<td width="50%" align="center">
						<input type=button value="完成自测" id="hidden" border=0 onclick="javascript:onCommit()">
					</td>
				</tr>
			</table>
	</body>
</html>
