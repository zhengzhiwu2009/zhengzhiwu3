<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>分配学时</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/shared.css">
<link href="/entity/manager/css/admincss.css" rel="stylesheet"
	type="text/css">
<script language="JavaScript" src="js/shared.js"></script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<table width="100%" border="0" align="left" cellpadding="0"
		cellspacing="0">
		<tr>
			<td height="28">
				<div class="content_title" id="zlb_title_start">分配学时</div id="zlb_title_end"></td>
		</tr>
		<tr>
			<td valign="top" class="pageBodyBorder" style="background: none;">
				<!-- start:内容区域 -->

				<div class="border">
					<table width="100%" border="0">
						<tr>
							<td style="text-align: left; padding-left: 20px;"><font
								color="red">集体账户学时数：<s:property
										value="#request.bean.ssoUser.amount" />学时</font></td>
							<td width="300px">分配方式： <select>
									<option>统一分配</option>
									<option>统一剥离</option>
									<option>定制分配</option>
							</select> <span><input type="text" class="text" name="1" size="5" />学时</span>
							</td>
							<td width="200px">
								<div style="text-align: right; padding-right: 10px">
									<span class="link" help=""><img
										src='/entity/manager/images/buttons/multi_activate.png'
										alt='Print' width="20" height="20" title='Print'>&nbsp;<a
										href='#' onclick="javascript:window.history.back();">返回</a> </span>
									&nbsp;&nbsp;&nbsp;&nbsp; <span class="link" help=""><img
										src='/entity/manager/images/buttons/multi_activate.png'
										alt='Print' width="20" height="20" title='Print'>&nbsp;<a
										href='#' onclick="javascript:alert('本次操作，分配XX学员，共分配XX学时');">分配学时</a>
									</span>
								</div></td>
						</tr>
					</table>
					<!-- end:内容区－列表区域 -->

					<form name="searchForm"
						action="/entity/manager/information/infoprint/printlist_student.jsp"
						method="post">
						<table width='100%' border="0" cellpadding="0" cellspacing='0'
							style='margin-bottom: 5px; text-align: left;'>
							<tr>
								<td style='white-space: nowrap;'>
									<div style="padding-left: 15px">

										系统编号： <input name="textfield36" type="text" size="10"
											maxlength="15"> &nbsp;&nbsp;&nbsp;&nbsp; 学员姓名： <input
											name="textfield36" type="text" size="10" maxlength="15">
										&nbsp;&nbsp;&nbsp;&nbsp; 证件 号码： <input name="textfield36"
											type="text" size="15" maxlength="18">
									</div></td>
							</tr>
							<tr>
								<td>
									<div style="padding-left: 15px">
										职务： <input name="textfield36" type="text" size="10"
											maxlength="15"> &nbsp;&nbsp;&nbsp;&nbsp; 所在地区： <input
											name="textfield36" type="text" size="10" maxlength="15">
										&nbsp;&nbsp;&nbsp;&nbsp; 未使用分配学时数： <select>
											<option>全部
											<option>大于10课时
											<option>小于10且大于5课时
											<option>小于5课时
											<option>0课时
										</select> <span class="link"><img
											src='/entity/manager/images/buttons/search.png' alt='Search'
											width="20" height="20" title='Search'>&nbsp;<a href='#'>查找</a>
										</span>
									</div></td>
							</tr>
						</table>
					</form>
					<!-- end:内容区－头部：项目数量、搜索、按钮 -->
					<!-- start:内容区－列表区域 -->
					<form name='userform' action='#' method='post' class='nomargin'
						onsubmit="">
						<table width='98%' align="center" cellpadding='1' cellspacing='0'
							class='list'>
							<tr>
								<th width='8px' class='select' align='center'><input
									type='checkbox' class='checkbox' name='listSelectAll'
									id="listSelectAll" onClick="listSelect()"></th>

								<th width='10%' style='white-space: nowrap;'><span
									class="link">系统编号</span></th>
								<th width='10%' style='white-space: nowrap;'><span
									class="link">学员姓名</span></th>
								<th width='' style='white-space: nowrap;'>身份证号</th>
								<th width='' style='white-space: nowrap;'>职务</th>
								<th width='' style='white-space: nowrap;'>所在地区</th>
								<th width='' style='white-space: nowrap;'>已使用分配学时数</th>
								<th width='' style='white-space: nowrap;'>已获得学时数</th>
								<th width='' style='white-space: nowrap;'>未使用分配学时数</th>
								<th width='10%' style='white-space: nowrap;'>分配学时数</th>
							</tr>
							<s:iterator value="stuList" id="stu">
								<tr class='oddrowbg'>
									<td style='white-space: nowrap;'><input type="checkbox"
										name="mag_id" id="mag_id"></td>
									<td style='white-space: nowrap; text-align: center;'><s:property
											value="#stu.regNo" /></td>
									<td style='white-space: nowrap; text-align: center;'><s:property
											value="#stu.trueName" /></td>
									<td style='white-space: nowrap; text-align: center;'><s:property
											value="#stu.cardNo" /></td>
									<td style='white-space: nowrap; text-align: center;'><s:property
											value="#stu.position" /></td>
									<td style='white-space: nowrap; text-align: center;'><s:property
											value="address" /></td>
									<td style='white-space: nowrap; text-align: center;'><s:property
											value="#stu.ssoUser.sumAmount-#stu.ssoUser.amount" /></td>
									<td style='white-space: nowrap; text-align: center;'><s:property
											value="#stu.ssoUser.sumAmount" /></td>

									<td style='white-space: nowrap; text-align: center;'><s:property
											value="#stu.ssoUser.amount" /></td>

									<td style='white-space: nowrap; text-align: center; '><input
										type=text disabled></td>
								</tr>
							</s:iterator>

						</table>
						<br />
					</form>
				</div>
				<table width="100%" height="36" border="0">
					<tr>
						<td width="10%" height="25">&nbsp;</td>
						<td width="90%" height="26">共 条记录 当前页：1/1 首页 上一页 下一页 末页 转到第 页
						</td>
					</tr>
				</table></td>
		</tr>
	</table>
</body>
<script type="text/javascript">
	function listSelect() {
		var b = document.getElementById("listSelectAll");
		var c = document.all("mag_id");
		if (b.checked == true) {

			for ( var i = 0; i < c.length; i++) {
				c[i].checked = true;
			}
		} else {
			for ( var i = 0; i < c.length; i++) {
				c[i].checked = false;
			}
		}
	}
</script>
</html>
