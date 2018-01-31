<%@ page language="java" import="java.net.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.whaty.platform.entity.bean.TeacherInfo" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../pub/commonfuction.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>中国证券业协会</title>
		<link href="/entity/function/css/css.css" rel="stylesheet"
			type="text/css">
	</head>
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
								<table width="765" border="0" cellspacing="0" cellpadding="0">
									<s:if test="teacherInfo != null">
									<tr>
										<td height="65"
											background="/entity/function/images/table_01.gif">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td align="center" class="text1">
														<img src="/entity/function/images/xb3.gif" width="17"
															height="15">
														<strong>教师简介</strong>
													</td>
													<td width="300">
														&nbsp;
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td background="/entity/function/images/table_02.gif">
											<table width="95%" border="0" align="center" cellpadding="0"
												cellspacing="0" class="bg4">
												<tr>
													<td class="text2">
														<table border="0" align="center" cellpadding="0"
															cellspacing="0" width=77%>
															<tr>
																<td>
																	<p align="center">
																		<font size=5><strong><s:property value="teacherInfo.sg_tti_name"/></strong>
																		</font>
																	</p>
																</td>
															</tr>
															<tr style="line-height: 200%">
																<td>
																	<p>
																		<br/>
																		<%
																			Object desc = ((TeacherInfo)request.getAttribute("teacherInfo")).getSg_tti_description();
																		 %>
																		<%=desc == null ? "" : desc %></p>
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
											<img src="/entity/function/images/table_03.gif" width="765"
												height="21">
										</td>
									</tr>
									<tr>
										<s:if test="teacherInfo.sg_tti_description == null || teacherInfo.sg_tti_description == ''">
											<td align=center>
												<a href="/entity/basic/teacherResource_toAddNote.action?id=<s:property value="teacherInfo.id"/>&courseId=<s:property value="courseId"/>" class="tj">[添加教师简介]</a>&nbsp;
											</td>
										</s:if>
										<s:else>
											<td align=center>
												<a href="/entity/basic/teacherResource_toEditNote.action?id=<s:property value="teacherInfo.id"/>&courseId=<s:property value="courseId"/>" class="tj">[编辑教师简介]</a>&nbsp;
												<a href="/entity/basic/teacherResource_deleNote.action?id=<s:property value="teacherInfo.id"/>&courseId=<s:property value="courseId"/>" class="tj">[删除教师简介]</a>&nbsp;
											</td>
										</s:else>
									</tr>
									</s:if>
									<s:else>
										<tr>
										<td height="65">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td align="center" class="text1">
														<img src="/entity/function/images/xb3.gif" width="17"
															height="15">
														<strong>课程暂未设置讲师</strong>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									</s:else>
								</table>
							</td>
						</tr>

					</table>
				</td>
			</tr>
		</table>
	</body>
</html>