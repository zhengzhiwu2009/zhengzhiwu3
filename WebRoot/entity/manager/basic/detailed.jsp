<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>推荐系列详情</title>
		<link href="/entity/manager/images/css.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div class="h2">
			<div class="h2div">
				推荐系列详情
			</div>
		</div>
		<div class="content">
			<table cellspacing="0" cellpadding="0">
				<s:iterator value='#request.list' id='items'>
					<tr>
						<th width="100px">
							推荐系列名称：
						</th>
						<td>
							<s:property value="#items[0]" />
						</td>
					</tr>
					<tr>
						<th>
							推荐系列描述：
						</th>
						<td>
							<s:property value="#items[1]" escape="false"  />
						</td>
					</tr>
					<tr>
						<th>
							是否首页显示：
						</th>
						<td>
							<s:property value="#items[2]" />
						</td>
					</tr>
					<tr>
						<th>
							建议学习人群：
						</th>
						<td>
							<s:property value="#items[4]" />
						</td>
					</tr>
					<tr>
						<th>
							推荐系列图片：
						</th>
						<td>
							<s:if test="#items[3]==null">
										未上传
							</s:if>
							<s:else>
								<img src="<s:property value='#items[3]' />" height="200" width="300" />
							</s:else>
						</td>
					</tr>
				</s:iterator>
				</table>
			<div>
				<div class="buttbox">
						<input type="button" value="返回" onclick="window.location='/entity/basic/recommendSeries.action'">
				</div>
			</div>
		</div>
	</body>
</html>