<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>操作记录</title>
		<link href="/entity/manager/images/css.css" rel="stylesheet" type="text/css">
	</head>
	<script type="text/javascript">
		function doSubmit() {
			document.myForm.action = "teacherResource_checkCode.action";
			document.myForm.submit();
		}
		
		function toCompare() {
			var logIds = document.getElementsByName("log_id");
			var count = 0;
			var log_ids = '';
			for(var i = logIds.length - 1; i >= 0; i--) {
				if(logIds[i].checked) {
					log_ids += logIds[i].value + ',';
					count ++;
				}
			}
			if(count > 2) {
				alert('最多只能选择两条日志进行对比');
				return;
			} else if(count == 0) {
				alert("请至少选择一条日志进行对比");
				return
			} else {
				var ids = document.getElementById("logIds");
				ids.value = log_ids;
				document.myForm.action = 'teacherResource_doCompare.action';
				document.myForm.submit();
			}
		}
	</script>
	<body>
		<div class="h2">
			<div class="h2div"> 
				操作记录
			</div>
		</div>
		<div class="content">
		<form action="" name="myForm">
			<input type="hidden" name="id" id="id" value="<s:property value="teacherInfo.id"/>"/>
			<input type="hidden" name="logIds" id="logIds" value=""/>
			<table cellspacing="0" cellpadding="0">
				<tr>
					<th></th>
					<th>讲师姓名</th>
					<th>所在机构：</th>
					<th>工作部门：</th>
					<th>证件号码：</th>
					<th>联系电话：</th>
					<th>电子邮箱：</th>
					<th>操作人：</th>
					<th>操作IP：</th>
					<th>操作时间：</th>
					<th>操作类型：</th>
				</tr>
				<s:if test="log_list != null && log_list.size() > 0">
					<s:iterator value="log_list" id="log">
						<tr align="center">
							<td>
								<input name="log_id" type="checkbox" value="<s:property value="#log[0]"/>">
							</td>
							<td>
								<s:property value="#log[1]"/>
							</td>
							<td>
								<s:property value="#log[2]"/>
							</td>
							<td>
								<s:property value="#log[3]"/>
							</td>
							<td>
								<s:property value="#log[4]"/>
							</td>
							<td>
								<s:property value="#log[5]"/>
							</td>
							<td>
								<s:property value="#log[6]"/>
							</td>
							<td>
								<s:property value="#log[7]"/>
							</td>
							<td>
								<s:property value="#log[8]"/>
							</td>
							<td>
								<s:property value="#log[9]"/>
							</td>
							<td>
								<s:property value="#log[10]"/>
							</td>
						</tr>
					</s:iterator>
					<tr>
						<td align="center" colspan="11">
							<input type="button" value="对比" onclick="toCompare()"/>
						</td>
					</tr>
				</s:if>
				<s:else>
					<tr>
						<td align="center" colspan="11"><font size="2">---暂无数据---</font></td>
					</tr>
				</s:else>
			</table>
		</form>
		</div>
	</body>
</html>