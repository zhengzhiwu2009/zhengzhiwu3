<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>资料详情</title>
		<link href="/entity/manager/images/css.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div class="h2">
			<div class="h2div">
				资料详情
			</div>
		</div>
		<div class="content">
			<table cellspacing="0" cellpadding="0">
				<s:iterator value='#request.list' id='items'>
					<tr>
						<th width="100px">
							资料名称：
						</th>
						<td>
							<s:property value="#items[0]" />
						</td>
					</tr>
					<tr>
						<th>
							资料分类：
						</th>
						<td>
							<s:property value="#items[1]" />
						</td>
					</tr>
					<tr>
						<th>
							资料描述：
						</th>
						<td>
							<s:property value="#items[2]" />
						</td>
					</tr>
					<tr>
						<th>
							颁布/发布单位：
						</th>
						<td> 
							<s:property value="#items[3]" />
						</td>
					</tr>
					<tr>
						<th>
							颁布/发布时间：
						</th>
						<td>
						 <s:property value="#items[4]" />
							
						</td>
					</tr>
						
				</s:iterator>
				</table>
					<table cellspacing="0" cellpadding="0">
						<tr align ="center">
							<th>
							资料附件：
							</th>
						</tr>
						
				<s:if test="#request.filelinkList==null || #request.filelinkList.size() <= 0">
										<td colspan="3" align="center">
											-暂无数据-
										</td>
				</s:if>
							<s:else>
						<s:iterator value='#request.filelinkList' id='filelink'>
						<tr>
						
							<td align="center" >
								<a href="<s:property value='#filelink[0]' />"><s:property value="#filelink[1]" /></a>
							</td>
							</tr>
						</s:iterator>
					</s:else>
			</table>
			<div class="h2">
				<div class="h2div">
					相关课程资料
				</div>
	   		</div>
			<table cellspacing="0" cellpadding="0">
				<tr>
					<th>
						资料名称
					</th>
					<th>
						资料分类
					</th>
					<th>
						颁布/发布单位
					</th>
					<th>
						颁布/发布时间
					</th>
				</tr>
				<s:if test="#request.courseZl == null">
					<tr><td colspan="4" align="center">无</td></tr>
				</s:if>
				<s:else>
					<s:iterator value='#request.courseZl' id='czl'>
					<tr>
						<td>
						<s:if test="#request.usType==3">
							<a href=/entity/teaching/peResource_courseInfo.action?id=<s:property value="#czl[0]" />>
								<s:property value="#czl[1]" />
							</a>
						</s:if>
						<s:else>
							<a href=/entity/teaching/peResourcejiti_courseInfo.action?id=<s:property value="#czl[0]" />>
								<s:property value="#czl[1]" />
							</a>
						</s:else>
						</td>
						<td>
							<s:property value="#czl[2]" />
						</td>
						<td>
							<s:property value="#czl[3]" />
						</td>
						<td>
							<s:property value="#czl[4]" />
						</td>
					</tr>
				</s:iterator>
			</s:else>
			</table>
			<div>
				<div class="buttbox">
					<s:if test="#request.usType==3">
						<input type="button" value="返回" onclick="window.location='/entity/teaching/peResource.action'">
					</s:if>
					<s:elseif test="#request.usType==2 || #request.usType==4 || #request.usType==5 || #request.usType==6">
						<input type="button" value="返回" onclick="window.location='/entity/teaching/peResourcejiti.action'">
					</s:elseif>
					<s:else>
						<input type="button" value="返回" onclick="window.location='/entity/workspaceStudent/studentWorkspace_toZiliao.action'">
					</s:else>
				</div>
			</div>
		</div>
	</body>
</html>