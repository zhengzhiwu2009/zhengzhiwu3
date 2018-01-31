<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>查看课程信息</title>
		<link href="/entity/manager/images/css.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div class="h2">
			<div class="h2div">
				查看课程信息
			</div>
		</div>
		<div class="content">
			<table cellspacing="0" cellpadding="0">
				<s:iterator value='#request.courseDetail' id='items'>
					<tr>
						<th>
							课程编号：
						</th>
						<td width="37%">
							<span style="margin-left:20px;"><s:property value="#items[0]" /></span>
						</td>
						<th>
							课程名称：
						</th>
						<td width="37%">
							<span style="margin-left:20px;"><s:property value="#items[1]" /></span>
						</td>
					</tr>
					<tr>
						<th>
							主讲人：
						</th>
						<td>
							<span style="margin-left:20px;"><s:property value="#items[2]" /></span>
						</td>
						<th>
							学习期限(天)：
						</th>
						<td>
							<span style="margin-left:20px;"><s:property value="#items[3]" /></span>
						</td>
					</tr>
					<tr>
						<th>
							课程时长(分钟)：
						</th>
						<td>
							<span style="margin-left:20px;"><s:property value="#items[7]" /></span>
						</td>
						<th>
							拟停用日期：
						</th>
						<td>
							<span style="margin-left:20px;"><s:date name="#items[4]" format="yyyy-MM-dd" /></span>
						</td>
					</tr>
					<tr>
						<th>
							答疑开始时间：
						</th>
						<td>
							<span style="margin-left:20px;"><s:date name="#items[5]" format="yyyy-MM-dd" /></span>
						</td>
						<th>
							答疑结束时间：
						</th>
						<td>
							<span style="margin-left:20px;"><s:date name="#items[6]" format="yyyy-MM-dd" /></span>
						</td>
					</tr>
					<tr>
						<th>
							通过规则：
						</th>
						<td>
							<span style="margin-left:20px;"><s:property value="#items[8]" /></span>
						</td>
						<th>
							考试限制次数：
						</th>
						<td>
							<span style="margin-left:20px;"><s:property value="#items[9]" /></span>
						</td>
					</tr>
					<tr>
						<th>
							通过继续访问：
						</th>
						<td>
							<span style="margin-left:20px;"><s:property value="#items[10]" /></span>
						</td>
						<th>
							是否考试：
						</th>
						<td>
							<span style="margin-left:20px;"><s:property value="#items[11]" /></span>
						</td>
					</tr>
					<tr>
						<th>
							课程图片：
						</th>
						<td>
							<s:if test="#items[12]==null">
								<span style="margin-left:20px;">未上传</span>
							</s:if>
							<s:else>
								<span style="margin-left:20px;"><img src="<s:property value='#items[12]' />" height="200" width="300"  /></span>
							</s:else>
						</td>
						<th>
							主讲人照片：
						</th>
						<td>
							<s:if test="#items[13]==null">
								<span style="margin-left:20px;">未上传</span>
							</s:if>
							<s:else>
								<span style="margin-left:20px;"><img src="<s:property value='#items[13]' />"  height="200" width="260"  /></span>
							</s:else>
						</td>
					</tr>
					<tr>
						<th>
							课件首页：
						</th>
						<td colSpan="3">
							<s:if test="indexFile==null">
								<span style="margin-left:20px;">-</span>
							</s:if>
							<s:if test="indexFile==''">
								<span style="margin-left:20px;">-</span>
							</s:if>
							<s:else>
								<span style="margin-left:20px;"><s:property value="#items[16]" /></span>
							</s:else>
						</td>
					</tr>
					<tr>
						<th>
							通过规则描述：
						</th>
						<td colSpan="3">
							<span style="margin-left:20px;"><s:property value="#items[14]" escape="false" /></span>
						</td>
					</tr>
					<tr>
						<th>
							学习建议：
						</th>
						<td colSpan="3">
							<span style="margin-left:20px;"><s:property value="#items[15]" /></span>
						</td>
					</tr>
				</s:iterator>
			</table>
			<div>
				<div class="buttbox">
					<input type="button" value="关闭" onclick="javascript:window.self.close()">
				</div>
			</div>
		</div>
	</body>
</html>