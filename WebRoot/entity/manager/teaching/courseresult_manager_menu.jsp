<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>课程成绩管理</title>
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
				<div id="0110" class="tab_menu2" onclick="top.openPage('/entity/manager/teaching/usualScoreView_search.jsp',this.id)" title="平时成绩管理">平时成绩管理</div>
				<div id="0111" class="tab_menu2" onclick="top.openPage('/entity/manager/teaching/homeworkScoreView_search.jsp',this.id)" title="作业成绩导入">作业成绩导入</div>
				<div id="0112" class="tab_menu2" onclick="top.openPage('/entity/manager/teaching/examScoreView_search.jsp',this.id)" title="考试成绩查看">考试成绩查看</div>
				<div id="0113" class="tab_menu2" onclick="top.openPage('/entity/teaching/scorePercentSet.action',this.id)" title="成绩合成设置">成绩合成设置</div>
				<div id="0114" class="tab_menu2" onclick="top.openPage('/entity/manager/teaching/result_make.jsp',this.id)" title="成绩合成">成绩合成</div>
				<div id="0115" class="tab_menu2" onclick="top.openPage('/entity/manager/teaching/score_search.jsp',this.id)" title="成绩查询">成绩查询</div>
				<script>top.openPage('/entity/manager/teaching/usualScoreView_search.jsp','0110');</script>
			</div>
		</div>
	</td>
  </tr>
</table>
</body>
</html>