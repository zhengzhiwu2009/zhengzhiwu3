<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<title>调查问卷</title>
	<head>
		<title></title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">
		<link href="../css/style.css" rel="stylesheet" type="text/css">
		<link href="/entity/manager/css/css.css" rel="stylesheet"
			type="text/css">
		<script>
	
	</script>
	</head>

	<body eftmargin="0" topmargin="0" class="scllbar">
		<table width="1000" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolordark="#8dc6f6" bordercolorlight="#FFFFFF">
			<tr>
				<td align ="center" ><font size ="2" ><b>自定义回答列表</b></font></td>
			</tr>
			<tr>
				<td class="14title">
					&nbsp;&nbsp;<b>问题：</b>
					<b><s:property value="bean.questionNote" />
					</b>
				</td>
			</tr>
			<s:iterator value="#request.voteSubList" id="vote">
				<tr>
					<td bgcolor="#F9F9F9">
						&nbsp;&nbsp;自定义答案：
						<s:property value="#vote[1]" />
					</td>
				</tr>
			</s:iterator>
			<td align="center" valign="middle">
				<span class="norm"
					style="width: 46px; height: 15px; padding-top: 3px"
					onmouseover="className='over'" onmouseout="className='norm'"
					onmousedown="className='push'" onmouseup="className='over'"
					onclick="javascript:history.back();"><span class="text">返回</span>
				</span>
			</td>
		</table>
	</body>
</html>
