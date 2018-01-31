<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.text.*"%>
<%@page import="java.net.URLEncoder"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String d = String.valueOf(Math.random());
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css.css" rel="stylesheet"
			type="text/css" />
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet"
			type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet"
			type="text/css" />
		<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
		<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
		<script type="text/javascript">
		function show_div(){ 
			var obj_div=document.getElementById("starlist"); 
			obj_div.style.display=(obj_div.style.display=='none')?'block':'none'; 
		} 
		function hide_div(){
			var obj_div=document.getElementById("starlist"); 
			obj_div.style.display=(obj_div.style.display=='none')?'block':'none';
		 }
		</script>
	</head>
	<body onload="window.location.href='#top'">
		<a id="top" name="top" />
		<form
			action="/entity/workspaceStudent/studentWorkspace_getVoteList.action"
			name="frm" method="post">
			<input type="hidden" name="startIndex"
				value="<s:property value="startIndex" />" />
			<input type="hidden" name="pageSize"
				value="<s:property value="pageSize" />" />
			<div class="" style="width: 100%; padding: 0; margin: 0;"
				align="center">
				<div class="main_title">
					<span>在线考试 </span>
					<span style="text-align:right";><input  type="button" value="开放式考试" onclick="show_div()"></span>
				</div>
				<div id="starlist" style="display: none">
						<div class="main_txt">
							<table class="datalist" width="100%">
								<tr bgcolor="#8dc6f6">
									<th>
										考试名称
									</th>
									<th width="14%">
										考试开始时间
									</th>
									<th width="14%">
										考试结束时间
									</th>
									<th width="14%">
										考试通过分数
									</th>
									<th width="14%">
										考试次数
									</th>
									<th width="26%">
										报名
									</th>
								</tr>
								<s:if test="page != null">
									<s:iterator value="piciList" id="pici" status="item">
										<tr>
											<td title="<s:property value="#pipc.remark" />">
												<a style="color: #3366ff; cursor: pointer;"
													onclick="window.open('/entity/function/liveDescription.jsp?exam_id=<s:property value="#pici[0]"/>','newwindow_detail')">
													<s:property value="#pici[1]" /> </a>
											</td>
											<td>
												<s:date name="#pici[2]" format="yyyy-MM-dd HH:mm:ss" />
											</td>
											<td>
												<s:date name="#pici[3]" format="yyyy-MM-dd HH:mm:ss" />
											</td>
											<td>
												<s:property value="#pici[4]" />
											</td>
											<td>
												<s:property value="#pici[5]" />
											</td>
											<td>
												<s:if test="compareTime(#pici[3].getTime())">
													报名结束
											</s:if>
												<s:else>
													<a target=""
														href="/entity/workspaceStudent/studentWorkspace_piciEnter.action?batch_id=<s:property value='#pici[0]'/>&piciName=<s:property value='transcoding(#pici[1])' />"
														title="进入考试">报名</a>
												</s:else>
											</td>
										</tr>
									</s:iterator>
								</s:if>
							</table>
							<br />
						</div>
				</div>
				<div class="main_txt">
					<table class="datalist" width="100%">
						<tr>
							<td colspan="8" align="left">
								<div id="fany">
									<script language="JavaScript">
										var page2 = new showPages("page2",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
										page2.printHtml();
									</script>
								</div>
							</td>
						</tr>
						<tr bgcolor="#8dc6f6">
							<th>
								考试名称
							</th>
							<th width="14%">
								考试开始时间
							</th>
							<th width="14%">
								考试结束时间
							</th>
							<th width="14%">
								考试通过分数
							</th>
							<th width="14%">
								成绩公布时间
							</th>
							<th width="10%">
								考试分数
							</th>
							<th width="10%">
								考试结果
							</th>
							<th width="6%">
								操作
							</th>
						</tr>
						<s:if test="paramter !='open'">
							<s:if test="page != null">
								<s:iterator value="page.items" id="pipc" status="item">
									<tr>
										<td title="<s:property value="#pipc.peBzzPici.remark" />">
											<a style="color: #3366ff; cursor: pointer;"
												onclick="window.open('/entity/function/liveDescription.jsp?exam_id=<s:property value="#pipc.peBzzPici.id"/>','newwindow_detail')">
												<s:property value="#pipc.peBzzPici.name" /> </a>
										</td>
										<td>
											<s:date name="#pipc.peBzzPici.startDatetime"
												format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td>
											<s:date name="#pipc.peBzzPici.endDatetime"
												format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td>
											<s:property value="#pipc.peBzzPici.passScore" />
										</td>
										<td>
											<s:date name="#pipc.peBzzPici.publishDatetime"
												format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td>
											<s:if
												test="compareTime(#pipc.peBzzPici.publishDatetime.getTime())">
												<s:if test="#pipc.score !=null">
													<s:property value="#pipc.score" />
												</s:if>
												<s:else>
												-- 
												</s:else>
											</s:if>
											<s:else>
												<s:if test="#pipc.peBzzPici.publishDatetime == null">
													<s:if test="#pipc.score !=null">
														<s:property value="#pipc.score" />
													</s:if>
													<s:else>
														--
													</s:else>
												</s:if>
											</s:else>
										</td>
										<td>
											<s:if
												test="compareTime(#pipc.peBzzPici.publishDatetime.getTime())">
												<s:if test="#pipc.score <#pipc.peBzzPici.passScore">
											   		 未通过考试
											   	 </s:if>
												<s:else> 通过考试</s:else>
											</s:if>
											<s:else>
												<s:if test="#pipc.peBzzPici.publishDatetime == null">
													<s:if test="#pipc.score <#pipc.peBzzPici.passScore">
											   		 未通过考试
											   		 </s:if>
													<s:else> 通过考试</s:else>
												</s:if>
											</s:else>
										</td>
										<td>
											<s:if test="#pipc.score !=null">
												<s:if
													test="compareTime(#pipc.peBzzPici.startDatetime.getTime(),#pipc.peBzzPici.endDatetime.getTime())">
													<a target="newwindow"
														href="/sso/bzzinteraction_hzphExamstuManage.action?batch_id=<s:property value='#pipc.peBzzPici.id'/>&piciName=<s:property value='transcoding(#pipc.peBzzPici.name)' />"
														title="进入考试"> <img
															src="/entity/bzz-students/images/hzphexam.jpg"
															width="100" height="15" border="0"
															onclick="javascript:if(!confirm('点击“进入在线考试”即开始倒计时，请您在规定时间内提交考卷。')) return false;" />
													</a>
												</s:if>
											</s:if>
											<s:else>
												<s:if
													test="compareTime(#pipc.peBzzPici.startDatetime.getTime(),#pipc.peBzzPici.endDatetime.getTime())">
													<a target="newwindow"
														href="/sso/bzzinteraction_hzphExamstuManage.action?batch_id=<s:property value='#pipc.peBzzPici.id'/>&piciName=<s:property value='transcoding(#pipc.peBzzPici.name)' />"
														title="进入考试"> <img
															src="/entity/bzz-students/images/hzphexam.jpg"
															width="100" height="22" border="0"
															onclick="javascript:if(!confirm('点击“进入在线考试”即开始倒计时，请您在规定时间内提交考卷。')) return false;" />
													</a>
												</s:if>
											</s:else>
											<s:else>
												<img src="/entity/bzz-students/images/hzphexam.jpg"
													width="100" height="15" border="0" title="不在考试时间范围内" />
											</s:else>
											<s:if test="#pipc.score !=null">
												<s:if
													test="compareTime(#pipc.peBzzPici.publishDatetime.getTime())">
													<a target="_blank"
														href="/sso/bzzinteraction_hzphExamManageDetail.action?batch_id=<s:property value="#pipc.peBzzPici.id"/>&teacher_id=teacher1&piciName= '+encodeURI(encodeURI(<s:property value="#pipc.peBzzPici.name"/>)+'&pageInt=1&piciId=<s:property value="#pipc.peBzzPici.name"/>&parameter=1">
														<img border="0" src="/entity/bzz-students/images/ckjg.jpg"
															width="100" height="15" /> </a>
												</s:if>
												<s:else>
													<s:if test="#pipc.peBzzPici.publishDatetime == null">
														<a target="_blank"
															href="/sso/bzzinteraction_hzphExamManageDetail.action?batch_id=<s:property value="#pipc.peBzzPici.id"/>&teacher_id=teacher1&piciName= '+encodeURI(encodeURI(<s:property value="#pipc.peBzzPici.name"/>)+'&pageInt=1&piciId=<s:property value="#pipc.peBzzPici.name"/>&parameter=1">
															<img border="0"
																src="/entity/bzz-students/images/ckjg.jpg" width="100"
																height="15" /> </a>
													</s:if>
												</s:else>
											</s:if>
										</td>
									</tr>
								</s:iterator>
							</s:if>
						</s:if>
						<s:else>
							<s:if test="page != null">
								<s:iterator value="page.items" id="pipc" status="item">
									<tr>
										<td title="<s:property value="#pipc.remark" />">
											<a style="color: #3366ff; cursor: pointer;"
												onclick="window.open('/entity/function/liveDescription.jsp?exam_id=<s:property value="#pipc.id"/>','newwindow_detail')">
												<s:property value="#pipc.name" /> </a>
										</td>
										<td>
											<s:date name="#pipc.startDatetime"
												format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td>
											<s:date name="#pipc.endDatetime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td>
											<s:property value="#pipc.passScore" />
										</td>
										<td>
											<s:date name="#pipc.publishDatetime"
												format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td>
											<s:if test="compareTime(#pipc.publishDatetime.getTime())">
												<s:if test="#pipc.score !=null">
													<s:property value="#pipc.score" />
												</s:if>
												<s:else>
												--
												</s:else>
											</s:if>
											<s:else>
									   			--
									   		</s:else>
										</td>
										<td>
											<s:if test="compareTime(#pipc.publishDatetime.getTime())">
												<s:if test="#pipc.score <#pipc.passScore">
											   		 未通过考试
											   	 </s:if>
												<s:else> 通过考试</s:else>
											</s:if>
											<s:else> --</s:else>
											<s:property value='transcoding(#pipc.name)' />
										</td>
										<td>
											<s:if
												test="compareTime(#pipc.startDatetime.getTime(),#pipc.endDatetime.getTime())">
												<a target="newwindow"
													href="/sso/bzzinteraction_hzphExamstuManage.action?batch_id=<s:property value='#pipc.id'/>&piciName=<s:property value='transcoding(#pipc.name)' />&flag=open"
													title="进入考试"> <img
														src="/entity/bzz-students/images/hzphexam.jpg" width="100"
														height="22" border="0"
														onclick="javascript:if(!confirm('点击“进入在线考试”即开始倒计时，请您在规定时间内提交考卷。')) return false;" />
												</a>
											</s:if>
											<s:else>
												<img src="/entity/bzz-students/images/hzphexam.jpg"
													width="100" height="22" border="0" title="不在考试时间范围内" />
											</s:else>
											<s:if test="#pipc.score !=null">
												<s:if test="compareTime(#pipc.publishDatetime.getTime())">
													<a target="_blank"
														href="/sso/bzzinteraction_hzphExamManageDetail.action?batch_id=<s:property value="#pipc.id"/>&teacher_id=teacher1&piciName= '+encodeURI(encodeURI(<s:property value="#pipc.name"/>)+'&pageInt=1&piciId=<s:property value="#pipc.name"/>&parameter=1">
														<img border="0" src="/entity/bzz-students/images/ckjg.jpg"
															width="100" height="22" /> </a>
												</s:if>
											</s:if>
										</td>
									</tr>
								</s:iterator>
							</s:if>
						</s:else>
						<tr>
							<td colspan="8" align="left">
								<div id="fany">
									<script language="JavaScript">
										var page1 = new showPages("page1",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
										page1.printHtml();
									</script>
								</div>
							</td>
						</tr>
					</table>
					<br />
				</div>
			</div>
		</form>
	</body>
</html>