<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>中国证券协会远程培训系统</title>
		<style type="text/css"><!--<%@ include file="/entity/manager/css/admincss.css"%>--></style>
	</head>
	<body leftmargin="0" topmargin="0" class="scllbar">
		<s:form name="batch" id="batch"
			action="/entity/basic/signUpOnline_toConfirmSignUp.action" method="POST">
 			<table width="100%" border="0" align="left" cellpadding="0"
			cellspacing="0">
			<tr>
				<td height="28">
					<div class="content_title"  id="zlb_title_start">在线报名单</div id="zlb_title_end">
				</td>
			</tr>
			<tr>
				<td valign="top" class="pageBodyBorder" style="background:none;">
					<!-- start:内容区域 -->
					<div class="border">
						<table  width='98%' border="0" cellpadding="0" cellspacing='0' style='margin-bottom: 5px'>
							<tr>
								<td height="30" align="left">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总人数：
									<font color="red"><s:property value="#session.electiveMapList.size" /></font>
									&nbsp;人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学时数：
									<font color="red"><s:property value="#session.allCredit" /></font>&nbsp;
									学时&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总金额：
									<font color="red"><s:property value="#session.allAmount" /></font>&nbsp;元&nbsp;&nbsp;&nbsp;
									<font color="red">*报名单名称:</font>
									<input id="orderNote" type="text" name="orderNote"/>&nbsp;
								</td>
								<td class='misc' style='white-space: nowrap;'>
									<div>
                					</div>
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
										<span class="link">课程编号</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">学时数</span>
									</th>
								</tr>
								<s:iterator value="#session.electiveMapList" id="electiveMap">
									<tr class='oddrowbg'>
									<td style='white-space: nowrap; text-align: center;'>
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
								</tr>
								</s:iterator>
							</table>
							<br/>
      					<table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0">
   							 <tr>
          						<td align="center" valign="middle">
          	 						<span id="submitSpan"   style="width: 70px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_4.jpg');" ><a href="#" onclick="toPay();">去支付</a></span>	
          	 						<span id="submitSpan"   style="width: 70px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_4.jpg');" ><a href="#" onclick="invalideButton();">保存</a></span>	
    	 							<span   style="width: 40px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_2.jpg');" ><a href="###"  onclick="goBack();" >返&nbsp;回</a></span>
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
			if(confirm("点击【返回】，您所生成的记录将不会被保存，您确定要返回吗？\n（如果您已经保存或者已经支付成功，请忽略本提示信息。）"))
			history.back();
		}
		
		function invalideButton() {
			var orderNote = document.getElementById('orderNote').value;
			if(orderNote == ''){
				alert('请添加报名单名称');
				return;
			}
			if(sum==0){ 
				document.forms.batch.submit();
				document.getElementById('batch').submited=false;
				sum = 1;
			}
			document.getElementById('submitSpan').style.display = 'none';
		}
		
		function toPay() {
			var orderNote = document.getElementById('orderNote').value;
			document.forms.batch.action = '/entity/basic/signUpOnline_toPayOnlineSignUp.action';
			if(orderNote == ''){
				alert('请添加报名单名称');
				return;
			}
			if(sum==0){ 
				document.forms.batch.submit();
				document.getElementById('batch').submited=false;
				sum = 1;
			}
			document.getElementById('submitSpan').style.display = 'none';
		}
		
	</script>
</html>