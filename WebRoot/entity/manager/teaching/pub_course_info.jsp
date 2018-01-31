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
							价格：
						</th>
						<td>
							<span style="margin-left:20px;"><s:property value="#items[3]" /></span>
						</td>
					</tr>
					<tr>
						<th>
							学习期限(天)：
						</th>
						<td>
							<span style="margin-left:20px;"><s:property value="#items[4]" /></span>
						</td>
						<th>
							拟停用日期：
						</th>
						<td>
							<span style="margin-left:20px;"><s:date name="#items[5]" format="yyyy-MM-dd" /></span>
						</td>
					</tr>
					<tr>
						<th>
							答疑开始时间：
						</th>
						<td>
							<span style="margin-left:20px;"><s:date name="#items[6]" format="yyyy-MM-dd" /></span>
						</td>
						<th>
							答疑结束时间：
						</th>
						<td>
							<span style="margin-left:20px;"><s:date name="#items[7]" format="yyyy-MM-dd" /></span>
						</td>
					</tr>
					<tr>
						<th>
							课程性质 ：
						</th>
						<td>
							<span style="margin-left:20px;"><s:property value="#items[8]" /></span>
						</td>
						<th>
							课程学时：
						</th>
						<td>
							<span style="margin-left:20px;"><s:property value="#items[9]" /></span>
						</td>
					</tr>
					<tr>
						<th>
							通过规则：
						</th>
						<td>
							<span style="margin-left:20px;"><s:property value="#items[10]" /></span>
						</td>
						<th>
							考试限制次数：
						</th>
						<td>
							<span style="margin-left:20px;"><s:property value="#items[11]" /></span>
						</td>
					</tr>
					<tr>
						<th>
							通过继续访问：
						</th>
						<td>
							<span style="margin-left:20px;"><s:property value="#items[12]" /></span>
						</td>
						<th>
							是否考试：
						</th>
						<td>
							<span style="margin-left:20px;"><s:property value="#items[13]" /></span>
						</td>
					</tr>
					<tr>
						<th>
							业务分类：
						</th>
						<td>
							<span style="margin-left:20px;"><s:property value="#items[14]" /></span>
						</td>
						<th>
							大纲分类：
						</th>
						<td>
							<span style="margin-left:20px;"><s:property value="#items[15]" /></span>
						</td>
					</tr>
					<tr>
						<th>
							内容属性分类：
						</th>
						<td>
							<span style="margin-left:20px;"><s:property value="#items[16]" /></span>
						</td>
						<th>
							建议学习人群：
						</th>
						<td>
							<s:if test="#items[17]==null">
										<span style="margin-left:20px;">未设置</span>
							</s:if>
							<s:else>
								<span style="margin-left:20px;"><s:property value="#items[17]" /></span>
							</s:else>
						</td>
					</tr>
					<tr>
						<!--  
						<th>
							推荐状态：
						</th>
						<td>
							<s:property value="#items[18]" />
						</td>
						-->
						<th>
							课件首页：
						</th>
						<td>
							<s:if test="#items[24]!=null">
								<span style="margin-left:20px;"><s:property value='#items[24]' /></span>
							</s:if>
							<s:else>
								<span style="margin-left:20px;">-</span>
							</s:else>
						</td>
						<th>
							收费状态：
						</th>
						<td>
							<span style="margin-left:20px;"><s:property value="#items[19]" /></span>
						</td>
					</tr>
					<tr>
						<th>
							课程图片：
						</th>
						<td>
							<s:if test="#items[20]==null">
										<span style="margin-left:20px;">未上传</span>
							</s:if>
							<s:else>
								<span style="margin-left:20px;"><img src="<s:property value='#items[20]' />" height="200" width="300" /></span>
							</s:else>
						</td>
						<th>
							主讲人照片：
						</th>
						<td>
							<s:if test="#items[21]==null">
										<span style="margin-left:20px;">未上传</span>
									</s:if>
							<s:else>
								<span style="margin-left:20px;"><img src="<s:property value='#items[21]' />" height="200" width="260" /></span>
							</s:else>
						</td>
					</tr>
					<tr>
						<th>
							通过规则描述：
						</th>
						<td colSpan="3">
							<span style="margin-left:20px;"><s:property value="#items[22]" escape="false" /></span>
						</td>
					</tr>
					<tr>
						<th>
							学习建议：
						</th>
						<td colSpan="3">
							<span style="margin-left:20px;"><s:property value="#items[23]" /></span>
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