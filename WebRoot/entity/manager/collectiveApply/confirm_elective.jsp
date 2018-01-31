<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>中国证券协会远程培训系统</title>
		<!--  <link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">-->
		<style type="text/css"><!--<%@ include file="/entity/manager/css/admincss.css"%>--></style>
	</head>
	<body leftmargin="0" topmargin="0" class="scllbar">
		<s:form name="batch" id="batch"
			action="/entity/basic/collectiveElective_toConfirmOrderInfo.action" method="POST" target="_blank">
 			<table width="100%" border="0" align="left" cellpadding="0"
			cellspacing="0">
			<tr>
				<td height="28">
					<div class="content_title"  id="zlb_title_start">集体报名单</div id="zlb_title_end">
				</td>
			</tr>
			<tr>
				<td valign="top" class="pageBodyBorder" style="background:none;">
					<!-- start:内容区域 -->
					<div class="border">
						<table width='100%' border="0" cellpadding="0" cellspacing='0'>
							<tr>
								<td style="padding-left: 10px;">
									<img src="/entity/manager/images/jd_02.jpg" />
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
						<table  width='98%' border="0" cellpadding="0" cellspacing='0' style='margin-bottom: 5px'>
							<tr>
								<td height="30" align="left">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总人数：
									<font color="red"><s:property value="#session.electiveMapList.size" /></font>
									&nbsp;人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学时数：
									<font color="red"><s:property value="#session.allCredit" /></font>&nbsp;
									学时
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;必修学时数：
									<font color="red"><s:property value="#session.bTimes" /></font>&nbsp;
									学时&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选修学时数：
									<font color="red"><s:property value="#session.xTimes" /></font>&nbsp;
									学时&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总金额：
									<font color="red"><s:property value="#session.allAmount" /></font>&nbsp;元
								</td>
								<td class='misc' style='white-space: nowrap;'>
									<div><!-- <span class="link" help="将当前表格数据导出到Excel格式的表格。" onMouseOver="top.showHelpInfo(this.help)" onMouseOut="top.showHelpInfo()"><img src='../images/buttons/excel.png' alt='Print' width="20" height="20" title='Print'>&nbsp;<a href='#'>Excel报表</a></span>&nbsp;&nbsp;<span class="link" help="将选中的课程从数据库中删掉" onMouseOver="top.showHelpInfo(this.help)" onMouseOut="top.showHelpInfo()"><img src='/entity/manager/images/buttons/multi_delete.png' alt='Print' width="20" height="20" title='Print'>&nbsp;<a href='#' onclick='if(confirm("要批量删除学期吗?")) document.userform.submit();'>删除</a></span> -->
                					</div>
								</td>
							</tr>
							
						</table>
						<!-- end:内容区－头部：项目数量、搜索、按钮 -->
						<!-- start:内容区－列表区域 -->
						<form name='userform' action='#' method='post' class='nomargin'
							onsubmit="">
							<%--  Lee start 2014年1月23日 --%>
							<input type="hidden" id="peSignupOrderId" name="peSignupOrderId"
									value="<%=session.getAttribute("peSignupOrderId") %>" />
							<%--  Lee end --%>
							<table width='98%' align="center" cellpadding='1' cellspacing='0'
								class='list'>
								<tr>
									<!--<th width='8px' class='select' align='center'><input type='checkbox' class='checkbox' name='listSelectAll' value='true'  onClick="listSelect('user')"> 
             						</th>
									<th width='8px' style='white-space: nowrap;'> <div style='display:block; width: 20px;'> 
                  <span class="link linkdisabled"><a href='#' title="删除">D</a></span></div></th>
									--><th width='10%' style='white-space: nowrap;'>
										<span class="link">系统编号</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">学员姓名</span>
									</th>
									<th width='' style='white-space: nowrap;'>
										<span class="link">课程编号</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">学时数</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">价格</span>
									</th>
								</tr>
								<s:iterator value="#session.electiveMapList" id="electiveMap">
									<tr class='oddrowbg'>
									<!--<td style='white-space: nowrap;'>
										<input type="checkbox" name="mag_id" value="">
									</td>
									 <td style='text-align: center; '><span class="link" title='删除' help="从数据库中删除该学期" onMouseOver="top.showHelpInfo(this.help)" onMouseOut="top.showHelpInfo()"><a href="javascript:alert('删除')"><img src='/entity/manager/images/buttons/delete.png' alt='删除' width="20" height="20" title='删除' border="0"></a></span> 
              						</td>
									--><td style='white-space: nowrap; text-align: center;'>
										<s:property value="#electiveMap.peBzzStudent.regNo"/>
									</td>
									<td style='white-space: nowrap; text-align: center;'>
										<s:property value="#electiveMap.peBzzStudent.trueName"/>
									</td>
									<td style='white-space: nowrap; text-align: center;'>
										<s:property value="#electiveMap.courseStr"/>
									</td>
									<td style='white-space: nowrap; text-align: center;'>
										<s:property value="#electiveMap.credit"/>
									</td>
									<td style='white-space: nowrap; text-align: center;'>
										<s:property value="#electiveMap.amount"/>
									</td>
								</tr>
								</s:iterator>
							</table>
							<br/>
      					<table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0">
   							 <tr>
          						<td align="center" valign="middle">

          							<!--<span   style="width: 70px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_4.jpg');" ><a href="#" onclick="javascript:document.forms.batch.submit();">确认报名</a></span>-->
          	 						<span id="submitSpan"   style="width: 70px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_4.jpg');" ><a href="#" onclick="invalideButton();">确认报名</a></span>	

          						<!-- <span   style="width: 70px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_4.jpg');" ><a href="###" onclick="javascript:document.forms.batch.submit();">确认报名</a></span>
 -->	
          	 							&nbsp;&nbsp;&nbsp;
          	 							<span id="fanhui"   style="width: 40px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_2.jpg');" ><a href="###"  onclick="goBack();" >&nbsp;返回&nbsp;</a></span>
          						</td>
        					</tr>
      					</table>
						</form>
						<!-- end:内容区－列表区域 -->
					</div>
					<!-- 内容区域结束 -->
				</td>
			</tr>
		</table>
		</s:form>
	</body>
	<script type="text/javascript">
		var sum = 0;
		function goBack() {
			history.go(-1);
		}
		
		function invalideButton() {
			//document.getElementById('batch').submit();
			if(sum==0) { 
				document.forms.batch.submit();
				document.getElementById('batch').submited=false;
				document.getElementById("fanhui").style.display = 'none';
				sum = 1;
			}
			document.getElementById('submitSpan').style.display = 'none';			
			document.getElementById("fanhui").style.display = 'block';
			
		}
	</script>
</html>