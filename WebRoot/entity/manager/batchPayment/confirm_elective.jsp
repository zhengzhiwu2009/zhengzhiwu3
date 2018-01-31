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
			action="/entity/basic/paymentBatch_toConfirmOrderStu.action" method="POST" target="_blank">
			<s:hidden name="id" value="id"></s:hidden>
			<s:hidden name="loginId" value="loginId"></s:hidden>
			<s:hidden name="ids" value="ids"></s:hidden>
 			<table width="100%" border="0" align="left" cellpadding="0"
			cellspacing="0">
			<tr>
				<td height="28">
					<div class="content_title"  id="zlb_title_start">专项培训支付确认</div id="zlb_title_end">
				</td>
			</tr>
			<tr>
				<td valign="top" class="pageBodyBorder" style="background:none;">
					<!-- start:内容区域 -->
					<div class="border">
						<table  width='98%' border="0" cellpadding="0" cellspacing='0' style='margin-bottom: 5px'>
							<tr>
								<td height="30" align="left">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;专项名称：
									<font color="red"><s:property value="peBzzBatch.name" /></font>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所选人数：
									<font color="red"><s:property value="#request.list.size()" /></font>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选中金额：
									<font color="red"><s:property value="peBzzOrderInfo.amount" /></font>&nbsp;元
								</td>
							</tr>
							
						</table>
						<!-- end:内容区－头部：项目数量、搜索、按钮 -->
						<!-- start:内容区－列表区域 -->
						<form name='userform' action='#' method='post' class='nomargin'
							onsubmit="">
							<table width='98%' align="center" cellpadding='1' cellspacing='0'
								class='list'>
								<tr>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">系统编号</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">学员姓名</span>
									</th>
									<th width='' style='white-space: nowrap;'>
										<span class="link">证件号码</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">集体编号</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">集体名称</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">已报名课程数</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">已报名学时数</span>
									</th>
								</tr>
								<s:iterator value="list" id="peBzzStudent">
									<tr class='oddrowbg'>
									<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#peBzzStudent[1]"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#peBzzStudent[2]"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#peBzzStudent[3]"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#peBzzStudent[4]"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#peBzzStudent[5]"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#peBzzStudent[6]"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#peBzzStudent[7]"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#peBzzStudent[8]"/>
										</td>
									</tr>
								</s:iterator>
							</table>
							<br/>
      					<table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0">
   							 <tr>
          						<td align="center" valign="middle">
          	 						<span id="submitSpan"   style="width: 70px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_4.jpg');" ><a href="#" onclick="invalideButton();">确认报名</a></span>	
          	 							&nbsp;&nbsp;&nbsp;
          	 						<s:if test="togo==back">
			          	 				<span style="width: 40px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_2.jpg');" ><a href="###"  onclick="history.back();" >返回</a></span>
          	 						</s:if>
          	 						<s:else>
			          	 				<span style="width: 40px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_2.jpg');" ><a href="###"  onclick="window.close();" >关闭</a></span>
			          	 			</s:else>			
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
			//history.back();
			window.close();
		}
		
		function invalideButton() {
			if(sum==0) { 
				document.forms.batch.submit();
				document.getElementById('batch').submited=false;
				sum = 1;
			}
			document.getElementById('submitSpan').style.display = 'none';
		}
	</script>
</html>