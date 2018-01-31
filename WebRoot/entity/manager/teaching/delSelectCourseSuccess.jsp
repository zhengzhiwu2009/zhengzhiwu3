<%@ page language="java" import="com.whaty.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>

		<title></title>
		<meta http-equiv="expires" content="0">
		<link href="./css/css.css" rel="stylesheet" type="text/css">
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript">
		function changeSelect(e)
		{
			e.form.submit();
		}
		</script>
	</head>

	<body topmargin="0" leftmargin="0" bgcolor="#FAFAFA">

		<div id="main_content">
			<div class="content_title">
				删除选课成功
			</div>
			<div class="cntent_k">
				<div class="k_cc">
					<table width="554" border="0" align="center" cellpadding="0"
						cellspacing="0">

						<tr>
							<td height="26" align="center" valign="middle" colspan="2">
								删除成功
							</td>
						</tr>
						<tr>
							<td height="8">
							</td>
						</tr>

						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">选择课程数：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=(Integer)request.getAttribute("courses")%>
							</td>
						</tr>

						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">成功删除选课人数：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=(Integer)request.getAttribute("persons")%></td>
						</tr>
							<tr>
							<td height="10">&nbsp;
							</td>
						</tr>
						<tr valign="middle">
							<td align="center" colspan="2">
								<span class="name"><a href="/entity/teaching/prBzzTchOpenCourse.action?backParam=true">完成</a></span></button>
							</td>
						</tr>

						<tr>
							<td height="10">
							</td>
						</tr>
					</table>

				</div>
			</div>
		</div>
		<div class="clear"></div>
	</body>
</html>
