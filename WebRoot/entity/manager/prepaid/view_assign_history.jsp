<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<title>学时分配记录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/shared.css">
<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">
<script language="JavaScript" src="js/shared.js"></script>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="form1" method="post" action="">
<table width="100%" border="0" align="left" cellpadding="0"
			cellspacing="0">
			<tr>
				<td height="28">
					<div class="content_title" id="zlb_title_start">学时分配记录 
					</div id="zlb_title_end">
				</td>
			</tr>
			<tr>
				<td valign="top" class="pageBodyBorder" style="background: none;">
					<!-- start:内容区域 -->

					<div class="border">
						<!-- end:内容区－列表区域 -->
					
						<form name="searchForm"
							action="/entity/manager/information/infoprint/printlist_student.jsp"
							method="post">
							<table width='100%' border="0" cellpadding="0" cellspacing='0'
								style='margin-bottom: 5px;text-align:left;'>
									<tr>
										<td>
											<div style="padding-left: 15px">分配时间：   
										<input type="text" name="textfield36" size="10" maxlength="15">   
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
										<span class="link"><img src='/entity/manager/images/buttons/search.png' alt='Search' width="20" height="20" title='Search'>&nbsp;<a href='#'>查找</a></span>
										</div>
									</td>
									<td>
										<div style="text-align:right;padding-right:15px"><span class="link" help=""><img src='/entity/manager/images/buttons/back.png' alt='Print' width="20" height="20" title='Print'>&nbsp;<a href='#' onclick="javascript:window.history.back();">返回</a></span> 
                				</div>
									</td>
								</tr>
							</table>
						</form>
						<!-- end:内容区－头部：项目数量、搜索、按钮 -->
						<!-- start:内容区－列表区域 -->
						<form name='userform' action='#' method='post' class='nomargin'
							onsubmit="">
							<table width='98%' align="center" cellpadding='1' cellspacing='0'
								class='list'>
								<tr>
									<th width='8px' class='select' align='center'>
										<input type='checkbox' class='checkbox' name='listSelectAll'
											value='true' onClick="listSelect('user')">
									</th>

									<th width='20%' style='white-space: nowrap;'>分配时间 
									</th>
									<th width='20%' style='white-space: nowrap;'>分配学时数 
									</th>
									<th width='' style='white-space: nowrap;'>分配学生数  
									</th>
									<th width='' style='white-space: nowrap;'>操作  
									</th>
								</tr>
								<tr class='oddrowbg'>
									<td style='white-space: nowrap;'>
										<input type="checkbox" name="mag_id" value="">
									</td>
									<td style='white-space: nowrap; text-align: center;'>2012-02-11 19:15 
									</td>
									<td style='white-space: nowrap; text-align: center;'>150  
									</td>
									<td style='white-space: nowrap; text-align: center;'>10  
									</td>
									<td style='white-space: nowrap; text-align: center;'><a href="#">查看详情</a> 
									</td>
								</tr>
								<tr class='oddrowbg'>
									<td style='white-space: nowrap;'>
										<input type="checkbox" name="mag_id" value="">
									</td>
									<td style='white-space: nowrap; text-align: center;'>2012-02-18 12:15  
									</td>
									<td style='white-space: nowrap; text-align: center;'>320   
									</td>
									<td style='white-space: nowrap; text-align: center;'>50	<br/></td>
									<td style='white-space: nowrap; text-align: center;'><a href="#">查看详情</a> 
									</td>
								</tr>
								<tr class='oddrowbg'>
									<td style='white-space: nowrap;'>
										<input type="checkbox" name="mag_id" value="">
									</td>
									<td style='white-space: nowrap; text-align: center;'>2012-02-21 14:15  
									</td>
									<td style='white-space: nowrap; text-align: center;'>800   
									</td>
									<td style='white-space: nowrap; text-align: center;'>40	<br/></td>
									<td style='white-space: nowrap; text-align: center;'><a href="#">查看详情</a> 
									</td>
								</tr>
							</table>
							<br />
						</form>
					</div>
					<table width="100%" height="36" border="0">
								<tr>
									<td width="10%" height="25">
										&nbsp;
									</td>
									<td width="90%" height="26">
										共 条记录 当前页：1/1 首页 上一页 下一页 末页 转到第 页
									</td>
								</tr>
							</table>
				</td>
			</tr>
		</table>
	
  <!--结束-->
</form>
 
</body>
</html>
