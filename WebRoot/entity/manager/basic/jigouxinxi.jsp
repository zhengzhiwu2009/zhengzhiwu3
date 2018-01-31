<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>查看单位信息</title>
		<link href="/entity/manager/images/css.css" rel="stylesheet"
			type="text/css">
	</head>
	<body>

		<div class="h2">
			<div class="h2div">
				查看单位信息
			</div>
		</div>
		<div class="content">
			<form action="" method="post" enctype="multipart/form-data">
				<table border="0" align="center" cellpadding="0" cellspacing="0">
					<s:iterator value='#request.listti' id='items'>
						<tr>
							<th  width="120px">
								用&nbsp;户&nbsp;名：
							</th>
							<td>
								<s:property value="#items[1]" />
							</td>
						</tr>
						<tr>
							<th>
								姓名：
							</th>
							<td>
								<s:property value="#items[2]" />
							</td>
						</tr>
						<tr>
							<th>
								组织机构：
							</th>
							<td>
								<s:property value="#items[3]" />
							</td>
						</tr>
						<tr>
							<th>
								账号类别：
							</th>
							<td>
								<s:property value="#items[4]" />
							</td>
						</tr>
						<tr>
							<th>
								固定电话：
							</th>
							<td>
								<s:property value="#items[5]" />
							</td>
						</tr>
						<tr>
							<th>
								移动电话：
							</th>
							<td>
								<s:property value="#items[6]" />
							</td>
						</tr>
						<tr>
							<th>
								电子邮箱：
							</th>
							<td>
								<s:property value="#items[7]" />
							</td>
						</tr>
						<tr>
							<th>
								办公地址：
							</th>
							<td>
								<s:property value="#items[8]" />
							</td>
						</tr>
						<tr>
							<th>
								从业规模：
							</th>
							<td>
								<s:property value="#items[9]" />
							</td>
						</tr>
					</s:iterator>
					<tr>
						<td colspan="2" align="center"><input type="button" value="返回" onclick="history.back()" /></td>
					</tr>
				</table>
				
		</div>

	</body>
</html>