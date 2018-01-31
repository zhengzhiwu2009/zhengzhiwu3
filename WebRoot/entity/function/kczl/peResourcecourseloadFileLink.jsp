<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>中国证券业协会</title>
		<link href="../css/css.css" rel="stylesheet" type="text/css">
		<style type="text/css">
.tab {
	border-collapse: collapse;
}

.th {
	font-size: 14px;
	background: url(thbg.jpg) repeat-x top #59a4da;
	border: #dedede 1px solid;
	color: #FFF;
}

.td {
	font-size: 12px;
	border: #dedede 1px solid;
	text-align: center;
}
</style>
		<script>

</script>
	</head>
	<body leftmargin="0" topmargin="0" background="/entity/function/images/bg2.gif">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				
				<tr>
					<td valign="top" background="/entity/function/images/top_01.gif">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="text1">
									<img src="/entity/function/images/xb3.gif" width="20" height="20">
									<strong>附件下载</strong>
								</td>
							</tr>
						</table>
					</td>
				</tr>
					<input type=hidden name=coursename value=<s:property value="#request.coursename" />>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td style="padding: 5px 20px 5px 20px;">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<th width="65%" class="th">
												名称
											</th>
											<th width="25%" class="th">
												操作
											</th>
											
										</tr>
										<s:iterator value ="#request.knowledeListxq" id ="know" >
											<tr>
											<th width="65%" >
												<s:property value ="#know[1]" />
											</th>
											<th width="25%" >
											<a href="<s:property value ="#know[0]" />" >下载</a>
												
											</th>
											
										</tr>
										 </s:iterator>
											
										
										
									</table>
									
								</td>
						
							</tr>
					
						</table>
					
					</td>
					
				</tr>
			<tr  align="center">
				<td>
					<input type=button value="关闭" onclick="window.close()"></td>
				</td>
			</tr>
			</table>
			
		
	</body>
</html>
