<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>教学计划</title>
<link href="/entity/manager/pub/images/layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<!--内容区-->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="6" bgcolor="278abe" style="background-color:#278abe;"></td>
    <td>
		<div id="main_content">
			<div id="tab_bg">
				<div id="0110" class="tab_menu2" onclick="top.openPage('/entity/teaching/peTeaPlan.action',this.id)" title="教学计划列表">教学计划列表</div>
				<div id="0111" class="tab_menu2" onclick="top.openPage('/entity/teaching/peTeaPlan_batch.action',this.id)" title="导入教学计划（Excel表格导入）">导入教学计划</div>
				
				<script>top.openPage('/entity/teaching/peTeaPlan.action','0110');</script>
			</div>
		</div>
	</td>
  </tr>
</table>
</body>
</html>
