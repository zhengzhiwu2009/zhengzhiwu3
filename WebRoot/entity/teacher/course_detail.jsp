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
		
			<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
		<script language="JavaScript" src="js/shared.js"></script>
	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<form name="form1" method="post" action="">
			<!--开始-->
			<table width="100%" border="0" cellspacing="0" cellpadding="4"
				align="center">
				<tr>

					<td>
					<div class="content_title">
						<b>查看课程信息</b>
						</div>
					</td>
					
				</tr>
			</table>
			<div class="cntent_k">
			<div class="k_cc">
			<table width="90%" border="0" cellspacing="3" cellpadding="0"
				align="center">
				
				<tr valign="middle">
					<td height="77" class="tdframe">
						<table width="100%" border="0" cellspacing="1" cellpadding="3"
							align="center">
							<tr>
								<td width="19%" height="30" nowrap class="postFormBox">
									<div align="right">
										<b>课程编号：</b>
									</div>
								</td>
								<td width="19%"  height="30" nowrap class="postFormBox">
									<s:property value="peBzzTchCourse.code"/>
								</td>
								<td width="19%" class="postFormBox" height="30">
									<div align="right">
										<b>课程名称：</b>
									</div>
								</td>
								<td width="43%" class="postFormBox" height="30">
									<s:property value="peBzzTchCourse.name"/>
								</td>
							</tr>
							<tr>
								<td width="19%" nowrap class="postFormBox" height="30">
									<div align="right">
										<b>讲师：</b>
									</div>
								</td>
								<td nowrap class="postFormBox" height="30">
									<s:property value="peBzzTchCourse.teacher"/>
								</td>
								<td class="postFormBox" height="30">
									<div align="right">
										<b>价格：</b>
									</div>
								</td>
								<td class="postFormBox" height="30">
									<s:property value="peBzzTchCourse.time * #session.ClassHourRate"/>
								</td>
							</tr>
							<tr>
								<td nowrap class="postFormBox" height="30">
									<div align="right">
										<b>学习期限(天)：</b>
									</div>
								</td>
								<td nowrap class="postFormBox" height="30">
									<s:property value="peBzzTchCourse.studyDates"/>
								</td>
								<td class="postFormBox" height="30">
									<div align="right">
										<b>停用日期：</b>
									</div>
								</td>
								<td class="postFormBox" height="30">
									<s:date name="peBzzTchCourse.endDate" format="yyyy-MM-dd"/>
								</td>
							</tr>
							<tr>
								<td nowrap class="postFormBox" height="30">
									<div align="right">
										<b>答疑开始时间：</b>
									</div>
								</td>
								<td nowrap class="postFormBox" height="30">
									<s:date name="peBzzTchCourse.answerBeginDate" format="yyyy-MM-dd"/>
								</td>
								<td class="postFormBox" height="30">
									<div align="right" >
										<b>答疑结束时间：</b>
									</div>
								</td>
								<td class="postFormBox" height="30">
									<s:date name="peBzzTchCourse.answerEndDate" format="yyyy-MM-dd"/>
								</td>
							</tr>
							<tr>
								<td nowrap class="postFormBox" height="30">
									<div align="right">
										<b>是否必修：</b>
									</div>
								</td>
								<td nowrap class="postFormBox" height="30">
									<s:property value="peBzzTchCourse.enumConstByFlagCourseType.name"/>
								</td>
								<td class="postFormBox" height="30">
									<div align="right">
										<b>课程学时：</b>
									</div>
								</td>
								<td class="postFormBox" height="30">
									<s:property value="peBzzTchCourse.time"/>
								</td>
							</tr>
							<tr>
								<td nowrap class="postFormBox" height="30">
									<div align="right">
										<b>通过规则(百分比分数线)：</b>
									</div>
								</td>
								<td nowrap class="postFormBox" height="30">
									<s:property value="peBzzTchCourse.passRole"/>
								</td>
								<td class="postFormBox" height="30">
									<div align="right">
										<b>考试限制次数：</b>
									</div>
								</td>
								<td class="postFormBox" height="30">
									<s:property value="peBzzTchCourse.examTimesAllow"/>
								</td>
							</tr>
							<tr>
								<td nowrap class="postFormBox" height="30">
									<div align="right">
										<b>通过后能否继续访问：</b>
									</div>
								</td>
								<td nowrap class="postFormBox" height="30">
									<s:property value="peBzzTchCourse.examTimesAllow"/>
								</td>
								<td class="postFormBox" height="30">
									<div align="right">
										<b>课程类别：</b>
									</div>
								</td>
								<td class="postFormBox" height="30">
									<s:property value="peBzzTchCourse.enumConstByFlagCourseCategory.name"/>
								</td>
							</tr>

							<tr>
								<td height="24" class="postFormBox" height="30">
									<div align="right">
										<b>通过规则描述：</b>
									</div>
								</td>
								<td colspan="3" class="postFormBox" height="30">
									<div align="left">
										<s:property value="peBzzTchCourse.passRoleNote" escape="false"/>
									</div>
								</td>
							</tr>
							<tr>
								<td height="24" nowrap class="postFormBox" height="30">
									<div align="right">
										<b>学习建议：</b>
									</div>
								</td>
								<td colspan="3" class="postFormBox" height="30">
									<div align="left">
										<s:property value="peBzzTchCourse.suggestion" escape="false"/>
									</div>
								</td>
							</tr>
						<!-- <tr>
								<td height="24" nowrap class="postFormBox" height="30">
									<div align="right">
										<b>已导入课件：</b>
									</div>
								</td>
								<td colspan="3" nowrap class="postFormBox" height="30">
									<div align="left">
										<a href="#">上市公司治理.zip</a>
									</div>
								</td>
							</tr> -->	

						</table>
					</td>
				</tr>
			</table>

			<table width="1247" align="center" cellpadding="0"
				cellspacing="0" height="80">
				<tr>
					<!-- <td align="center" >
					<a href="javascript:void(0)" onclick="javascript:document.forms.form1.submit()" class="buttonn" ><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'"><span class="text">确定</span></span></a>
					</td> --><td align="center" >
					<a href="###" onclick="javascript:goBack('<s:property value="peBzzTchCourse.id"/>');"  class="buttonn" ><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'"><span class="text">返回</span></span></a>					
					</td>
					<!-- 
					<td align="center" class="tdbgbutton">
						<input type="submit" name="Submit2" value=" 确 定 " class="button">
						<input name="buttons" type="button" id="buttons" value=" 返  回 "
							class="button" onclick="window.history.go(-1);">
					</td>
					-->
					
				</tr>
			</table>
			<!--结束-->
		</form>
</div>
</div>
	</body>
	<script type="text/javascript">
		function goBack (courseId) {
			window.location.href('/entity/teaching/courseStudent.action?courseId='+courseId);
		}
	</script>
</html>

