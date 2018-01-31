<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>welcome</title>


		<link href="/entity/manager/pub/images/css/admincss.css"
			rel="stylesheet" type="text/css">
		<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
		<style type="text/css">
.pages {
	color: #000000;
	cursor: default;
	font-size: 14px;
	font-family: Tahoma, Verdana;
	padding: 3px 0px 3px 0px;
}

.pages .count,.pages .number,data_count,.pages .arrow {
	color: #000000;
	font-size: 14px;
}

/* Page and PageCount Style */
.pages .count {
	font-weight: bold;
	border-right: none;
}

/* Mode 0,1,2 Style (Number) */
.pages .number {
	font-weight: normal;
	margin-left: 10px;
	/*	padding: 2px 10px 1px 10px; */
}

.pages .number a,.pages .number span {
	font-size: 14px;
	background-color: #F7F7F7;
}

.pages .number span {
	color: #999999;
	margin: 0px 3px 0px 3px;
	padding: 3px 5px;
	border: 1px solid #CCCCCC;
}

.pages .number a {
	color: #000000;
	text-decoration: none;
}

.pages .number a:hover {
	color: #0000ff;
}

.pages .data_count {
	float: right;
	font-weight: bold;
}

/* Mode 3 Style (Arrow) */
.pages .arrow {
	font-weight: normal;
	padding: 0px 5px 0px 5px;
}

.pages .arrow a,.pages .arrow span {
	font-size: 14px;
	font-family: Webdings;
}

.pages .arrow span {
	color: #999999;
	margin: 0px 5px 0px 5px;
}

.pages .arrow a {
	color: #000000;
	text-decoration: none;
}

.pages .arrow a:hover {
	color: #0000ff;
}

/* Mode 4 Style (Select) */
.pages select,.pages input {
	color: #000000;
	font-size: 14px;
	font-family: Tahoma, Verdana;
}

/* Mode 5 Style (Input) */
.pages .input input.ititle,.pages .input input.itext,.pages .input input.icount
	{
	color: #666666;
	font-weight: bold;
	background-color: #F7F7F7;
	border: 1px solid #CCCCCC;
}

.pages .input input.ititle {
	width: 70px;
	text-align: right;
	border-right: none;
}

.pages .input input.itext {
	width: 25px;
	color: #000000;
	text-align: right;
	border-left: none;
	border-right: none;
}

.pages .input input.icount {
	width: 35px;
	text-align: left;
	border-left: none;
}

.pages .input input.ibutton {
	height: 17px;
	color: #FFFFFF;
	font-weight: bold;
	font-family: Verdana;
	background-color: #999999;
	border: 1px solid #666666;
	padding: 0px 0px 2px 1px;
	margin-left: 2px;
	cursor: hand;
}

#fany {
	font-size: 12px;
	color: #303030;
	font-weight: bold;
}

#fany a {
	color: #303030;
	font-weight: bold;
}

#fany a:link {
	color: #303030;
	font-weight: bold;
}

#fany a:hover {
	color: #FF0000;
	font-weight: bold;
}

body {
	text-align: center;
}

#content {
	width: 100%;
	margin: 0 auto;
	text-align: center;
}

.status_msg {
	background: url(/entity/manager/pub/images/gly_03.jpg) repeat-x center;
	height: 26px;
	line-height: 26px;
	color: #000000;
	font-weight: bold;
	font-size: 12px;
	text-align: left;
}

.status_msg span {
	color: #0d6ca2;
	padding-left: 12px;
}

.main {
	margin: 13px 12px;
	width: 100%;
}

.list {
	margin: 0 auto 11px;
	border: 1px solid #dee4e8;
	background: #F2F7FC;
	font-family: "宋体";
	height: 71px; +
	height: 73px;
	text-align: left;
}

.list h3 {
	font-size: 12px;
	color: #000000;
	margin-top: 16px;
	margin-bottom: 10px;
}

.list h3 a {
	color: #0033cc;
	text-decoration: underline;
}

.list h3 a:link,.list h3 visited {
	color: #0033cc;
	text-decoration: underline;
}

.list h3 a:hover {
	color: #FF0000;
	text-decoration: underline;
}

.list p {
	color: #666666;
	margin: 0;
	padding: 0;
}

.list p span {
	color: #ff6600;
}

.pic {
	float: left;
	margin: 6px 16px 6px 11px;
	padding: 1px;
	border: 1px solid #dbe2e6; +
	width: 61px; +
	height: 56px;
	display: inline
}

.pic img {
	border: 0;
}
</style>
	</head>
	<body bgcolor="#ECF7FF">
		<form name="frm" action="" method=post>
			<input type="hidden" name="startIndex"
				value="<s:property value="startIndex" />" />
			<input type="hidden" name="pageSize"
				value="<s:property value="pageSize" />" />
			<div id="content">
				<div class="status_msg">
					<span> <s:if test="#attr.user_session.roleId==2">集体管理员</s:if>
						<s:elseif test="#attr.user_session.roleId==3">协会管理员</s:elseif>
						&nbsp;&nbsp;&nbsp; <font color="red"><s:property
								value="#attr.user_session.userName" /> </font>，您好! 欢迎进入管理员工作室! </span>

				</div>
				<div class="main">
					<s:if test="#attr.user_session.roleId==3">
						<div class="list">
							<div class="pic">
								<img src="/entity/manager/pub/images/gly_07.jpg" width="59"
									height="54">
							</div>
							<h3>
								订单
								<a href="/entity/basic/paymentManage.action">[处理]</a>
							</h3>
							<p>
								共
								<span><s:property value="getOrdersList().get(0)"
										default="0" /> </span>单 处理订单
								<span> <s:property value="getOrdersList().get(1)"
										default="0" /> </span>条 未处理订单
								<span> <s:property
										value="getOrdersList().get(0)-getOrdersList().get(1)"
										default="0" /> </span>条
							</p>
						</div>
					</s:if>
					<div class="list">
						<div style="float: left">
							<div class="pic">
								<img src="/entity/manager/pub/images/gly_10.jpg" width="59"
									height="54">
							</div>
							<h3>
								站内信箱
								<a href="/siteEmail/recipientEmail.action">[处理]</a>
							</h3>
							<p>
								收到信息
								<span><s:property value="getEmailList().get(0)"
										default="0" /> </span>条 已发送信息
								<span> <s:property value="getEmailList().get(1)"
										default="0" /> </span>条 未读信息
								<span> <s:property value="getEmailList().get(2)"
										default="0" /> </span>条
							</p>
						</div>
						<!-- Lee 2013年12月26日添加 -->
						<div style="float: left; margin-left: 10px;">
							<div class="pic">
								<img src="/entity/manager/pub/images/sys_notice.png" width="59"
									height="54">
							</div>
							<h3>
								系统公告
								<a href="/entity/information/peBulletinView.action">[查看]</a>
							</h3>
							<p>
								共有
								<span><s:property value="getSysNoticeCount()" default="0" />
								</span>条 系统公告
							</p>
						</div>
						<!-- Lee end -->
					</div>
					<!--#attr.user_session.roleId==2--> 
					<s:if test="false">
						<div class="status_msg">
							<span>集体报名概况</span>
						</div>
						<div class="list" style="padding: 10 10 10">
							<table width="100%">
								<tr style="color: rgb(0, 85, 119);" class="head" align="right">
									<th scope="col">
										年度
									</th>
									<th scope="col">
										报名单总数
									</th>
									<th scope="col">
										已支付报名单
									</th>
									<th scope="col">
										已支付报名课程
									</th>
									<th scope="col">
										已支付学员人数
									</th>
									<th scope="col">
										已支付金额
									</th>
								</tr>
								<s:iterator value="page.items" id="temp">
									<tr class="row" align="right">
										<td style="text-align: center">

											<s:property value="#temp[0]" />
										</td>
										<td style="text-align: center">
											<s:property value="#temp[1]" />
										</td>
										<td style="text-align: center">
											<s:property value="#temp[2]" />
										</td>
										<td style="text-align: center">
											<s:property value="#temp[5]" />
										</td>
										<td style="text-align: center">
											<s:property value="#temp[4]" />
										</td>
										<td style="text-align: center">
											<s:property value="#temp[3]" />
										</td>
									</tr>
								</s:iterator>
								<tr
									style="color: rgb(0, 85, 119); font-weight: bold; background-color: rgb(250, 250, 250);"
									align="right">
									<td style="text-align: center">
										合计
									</td>
									<td style="text-align: center">
										<s:property value="applysum[0]" />
									</td>
									<td style="text-align: center">
										<s:property value="applysum[1]" />
									</td>
									<td style="text-align: center">
										<s:property value="applysum[2]" />
									</td>
									<td style="text-align: center">
										<s:property value="applysum[3]" />
									</td>
									<td style="text-align: center">
										<s:property value="amount" />
									</td>
								</tr>
								<tr>
									<td colspan="6" align="left">
										<div id="fany">
											<script language="JavaScript">
		var page1 = new showPages("page1",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
		page1.printHtml();
		</script>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</s:if>
				</div>
			</div>
		</form>
	</body>
</html>
