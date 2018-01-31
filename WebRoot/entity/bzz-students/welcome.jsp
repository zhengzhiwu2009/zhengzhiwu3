<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<link href="/entity/manager/images/css_welcome.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="title">
			个人主页
		</div>
		<div class="content">
			<table width="100%">
				<tr>
					<td class="red">
						<p>
							尊敬的学员：
						</p>
						<p>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;证券从业人员后续职业培训学时的获得条件为：完成课程学习、填写满意度调查问卷并通过课后测验，请知悉。
						</p>
						<p>
							敬告：
						</p>
						<p>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您的浏览器不能很好的支持课程的学习和支付，为获得更好的使用体验，推荐您使用微软IE8或IE9浏览器
						</p>
						<p>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.请在课程开始学习后90天内完成学习并获得学时，否则课程会过期，需要重新购买。
						</p>
						<p>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.请使用IE8浏览器进行学习，IE高版本浏览器请设置为兼容模式。
						</p>
					</td>
				</tr>
			</table>
		</div>
		<div class="list">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<th width="20%">
						站内信箱：
					</th>
					<td width="80%">
						未读信息&nbsp;
						<span class="red"><a href="/entity/workspaceStudent/studentWorkspace_tositeemail.action"><s:property value="#request.zhanneixinNum" /> </a> </span>&nbsp;条
					</td>
				</tr>
				<tr>
					<th>
						通知公告：
					</th>
					<td>
						通知公告&nbsp;
						<span class="red"><a href="/entity/workspaceStudent/stuPeBulletinView_getFirstinfo.action"><s:property value="#request.tongzhigonggaoNum" /> </a> </span>&nbsp;条
					</td>
				</tr>
				<tr>
					<th>
						调查问卷：
					</th>
					<td>
						可填写问卷&nbsp;
						<span class="red"><a href="/entity/workspaceStudent/studentWorkspace_getVoteList.action"><s:property value="#request.diaochawenjuanNum" /> </a> </span>&nbsp;条
					</td>
				</tr>
				<tr>
					<th>
						未支付订单：
					</th>
					<td>
						未支付订单&nbsp;
						<span class="red">&nbsp;<a href="/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action"><s:property value="#request.weizhifudingdanNum" /> </a> </span>&nbsp;条
					</td>
				</tr>
				<tr>
					<th>
						选课期：
					</th>
					<td>
						可选选课期&nbsp;
						<span class="red">&nbsp;<a href="/entity/workspaceStudent/studentWorkspace_toElectiveCoursePeriod.action"><s:property value="#request.xuankeqiNum" /> </a> </span>&nbsp;个
					</td>
				</tr>
				<tr>
					<th>
						预付费账户剩余学时：
					</th>
					<td>
						<span class="red">&nbsp;<s:property value="#request.yufufeizhanghuyueNum" /> </span>&nbsp;学时
					</td>
				</tr>
				<tr>
					<th>
						正在学习课程：
					</th>
					<td>
						<span class="red">&nbsp;<a href="/entity/workspaceStudent/studentWorkspace_toLearningCourses.action"><s:property value="#request.zhengzaixuexikechengNum" /> </a> </span>&nbsp;门&nbsp;&nbsp;共计&nbsp;
						<span class="red"><s:property value="#request.zhengzaixuexikechengTime" /> </span>&nbsp;学时
					</td>
				</tr>
				<tr>
					<th>
						待考试课程：
					</th>
					<td>
						<span class="red">&nbsp;<a href="/entity/workspaceStudent/studentWorkspace_toCompletedCourses.action"><s:property value="#request.daikaoshikechengNum" /> </a> </span>&nbsp;门&nbsp;&nbsp;共计&nbsp;
						<span class="red"><s:property value="#request.daikaoshikechengTime" /> </span>&nbsp;学时
					</td>
				</tr>
				<tr>
					<th>
						专项培训：
					</th>
					<td>
						<span class="red">&nbsp;<a href="/entity/workspaceStudent/studentWorkspace_tospecialenrolment.action"><s:property value="#request.zhuanxiangpeixunNum" /> </a> </span>&nbsp;个培训可报名
					</td>
				</tr>
				<tr>
					<th>
						在线直播课程：
					</th>
					<td>
						<span class="red">&nbsp;<a href="/entity/workspaceStudent/studentWorkspace_toSacLive.action"><s:property value="#request.zaixianzhibokechengNum" /> </a> </span>&nbsp;个视频直播可报名
					</td>
				</tr>
				<tr>
					<th>
						在线考试：
					</th>
					<td>
						<span class="red">&nbsp;<a href="/entity/workspaceStudent/studentWorkspace_firstOnlineExam.action"><s:property value="#request.zaixiankaoshiNum" /> </a> </span>&nbsp;个在线考试可报名
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>