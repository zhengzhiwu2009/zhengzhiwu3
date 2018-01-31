<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>添加资料</title>
		<link href="/entity/manager/images/css.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" media="all" href="/js/calendar/calendar-win2000.css" />
	</head>
	<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
	<script type="text/javascript" src="/js/calendar/calendar.js"></script>
	<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
	<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
	<script language="javascript" src="/entity/bzz-students/css/jquery.js"></script>
	<script type="text/javascript">
		function doSubmit() {
			if(document.getElementById('seq').value == null || document.getElementById('seq').value == '') {
				alert("输入订单号不能为空");
				return;
			}
			
			document.myForm.action = "/entity/basic/applyInvoiceMerge_applyMergeInvoice.action";
			document.myForm.submit();
		}
		
	
	</script>
	<script type="text/javascript">
		
	</script>
	<body>
		<div class="h2">
			<div class="h2div">   
				代申请专用发票
			</div>
		</div>
		<div class="content">
		<form action="" name="myForm" method="post" enctype="multipart/form-data">
			<input type="hidden" name="tagIds" id="tagIds"/>
			<input type="hidden" name="tagNames" id="tagNames"/>
			<input type="hidden" name="showusers" id="showusers"/>
			<table cellspacing="0" cellpadding="0">
					<tr>
						<th width="30%" align="right">
							<font color="red"></font>提示：
						</th>
						<td>
							<font color="red">申请合并开发票的订单，必须符合开具发票条件并且同属一个机构下的订单。输入多个订单号已“|”隔开</font>
						</td>
					</tr>
					
					<tr>
						<th align="right">
							<font color="red">&nbsp;</font>输入订单号：
						</th>
						<td>
							<textarea rows="6" cols="80" id="seq" name="seqs"></textarea>
						</td>
					</tr>
					
					<tr>
						<td colSpan="2" align="center">
							<input type="button" id="tijiao" value="保存" onclick="javascript: doSubmit()"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="返回" onclick="javascript:window.history.back()">
						</td>
					</tr>
			</table>
		</form>
		</div>
	</body>
	
</html>