<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="/entity/teacher/css/shared.css">
		<script language="JavaScript" src="js/shared.js"></script>
	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<form name="form1" method="post" action="">
			<!--开始-->

			<table width="98%" border="0" cellspacing="0" cellpadding="4"
				align="center">
				<tr>

					<td class="tdsectionbar">
						查看课程信息
					</td>
				</tr>
			</table>
			<table width="98%" border="0" cellspacing="3" cellpadding="0"
				align="center">
				<tr>

					<td height="77" class="tdframe">
						<table width="100%" border="0" cellspacing="1" cellpadding="3"
							align="center">
							<tr>
								<td width="19%" nowrap class="tdbg0">
									<div align="right">
										课程编号：
									</div>
								</td>
								<td width="29%" nowrap class="tdbg0">
									<s:property value="peBzzTchCourse.code"/>
								</td>
								<td width="26%" class="tdbg0">
									<div align="right">
										课程名称：
									</div>
								</td>
								<td width="26%" class="tdbg0">
									<s:property value="peBzzTchCourse.name"/>
								</td>
							</tr>
							<tr>
								<td width="19%" nowrap class="tdbg0">
									<div align="right">
										讲师：
									</div>
								</td>
								<td width="29%" nowrap class="tdbg0">
									<s:property value="peBzzTchCourse.teacher"/>
								</td>
								<td class="tdbg0">
									<div align="right">
										价格：
									</div>
								</td>
								<td width="26%" class="tdbg0">
									<s:property value="peBzzTchCourse.price"/>
								</td>
							</tr>
							<tr>
								<td nowrap class="tdbg0">
									<div align="right">
										学习期限(天)：
									</div>
								</td>
								<td nowrap class="tdbg0">
									<s:property value="peBzzTchCourse.studyDates"/>
								</td>
								<td class="tdbg0">
									<div align="right">
										停用日期：
									</div>
								</td>
								<td class="tdbg0">
									<s:property value="peBzzTchCourse.endDate"/>
								</td>
							</tr>
							<tr>
								<td nowrap class="tdbg0">
									<div align="right">
										答疑开始时间：
									</div>
								</td>
								<td nowrap class="tdbg0">
									<s:property value="peBzzTchCourse.answerBeginDate"/>
								</td>
								<td class="tdbg0">
									<div align="right">
										答疑结束时间：
									</div>
								</td>
								<td class="tdbg0">
									<s:property value="peBzzTchCourse.answerEndDate"/>
								</td>
							</tr>
							<tr>
								<td nowrap class="tdbg0">
									<div align="right">
										是否必修：
									</div>
								</td>
								<td nowrap class="tdbg0">
									<s:property value="peBzzTchCourse.enumConstByFlagCourseType.name"/>
								</td>
								<td class="tdbg0">
									<div align="right">
										课程学时：
									</div>
								</td>
								<td class="tdbg0">
									<s:property value="peBzzTchCourse.time"/>
								</td>
							</tr>
							<tr>
								<td nowrap class="tdbg0">
									<div align="right">
										通过规则(百分比分数线)：
									</div>
								</td>
								<td nowrap class="tdbg0">
									<s:property value="peBzzTchCourse.passRole"/>
								</td>
								<td class="tdbg0">
									<div align="right">
										考试限制次数：
									</div>
								</td>
								<td class="tdbg0">
									<s:property value="peBzzTchCourse.examTimesAllow"/>
								</td>
							</tr>
							<tr>
								<td nowrap class="tdbg0">
									<div align="right">
										通过后能否继续访问：
									</div>
								</td>
								<td nowrap class="tdbg0">
									<s:property value="peBzzTchCourse.examTimesAllow"/>
								</td>
								<td class="tdbg0">
									<div align="right">
										课程类别：
									</div>
								</td>
								<td class="tdbg0">
									<s:property value="peBzzTchCourse.enumConstByFlagCourseCategory.name"/>
								</td>
							</tr>

							<tr>
								<td height="24" class="tdbg0">
									<div align="right">
										通过规则描述：
									</div>
								</td>
								<td colspan="3" class="tdbg0">
									<div align="left">
										<s:property value="peBzzTchCourse.passRoleNote"/>
									</div>
								</td>
							</tr>
							<tr>
								<td height="24" nowrap class="tdbg0">
									<div align="right">
										学习建议：
									</div>
								</td>
								<td colspan="3" class="tdbg0">
									<div align="left">
										<s:property value="peBzzTchCourse.suggestion"/>
									</div>
								</td>
							</tr>
							<tr>
								<td height="24" nowrap class="tdbg0">
									<div align="right">
										已导入课件：
									</div>
								</td>
								<td colspan="3" nowrap class="tdbg0">
									<div align="left">
										<a href="#">上市公司治理.zip</a>
									</div>
								</td>
							</tr>

						</table>
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td align="center" class="tdbgbutton">
						<input type="submit" name="Submit2" value=" 确 定 " class="button">
						<input name="buttons" type="button" id="buttons" value=" 返  回 "
							class="button" onclick="window.history.go(-1);">
					</td>
				</tr>
			</table>
			<!--结束-->
		</form>

	</body>
</html>

