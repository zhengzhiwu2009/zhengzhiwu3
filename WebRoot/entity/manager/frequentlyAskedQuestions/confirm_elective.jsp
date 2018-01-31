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
			action="/entity/basic/frequentlyAskedQuestions.action" method="POST">
 			<table width="100%" border="0" align="left" cellpadding="0"
			cellspacing="0">
			<tr>
				<td height="28">
					<div class="content_title"  id="zlb_title_start">常见问题导入</div id="zlb_title_end">
				</td>
			</tr>
			<tr>
				<td valign="top" class="pageBodyBorder" style="background:none;">
					<!-- start:内容区域 -->
					<div class="border">
						<table width='100%' border="0" cellpadding="0" cellspacing='0'>
							<tr>
								<td style="padding-left: 10px;" align="center">
									导入成功
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
      					<table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0">
   							 <tr>
          						<td align="center" valign="middle">

          							<!--<span   style="width: 70px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_4.jpg');" ><a href="#" onclick="javascript:document.forms.batch.submit();">确认报名</a></span>-->
          	 						<span id="submitSpan"   style="width: 70px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_4.jpg');" ><a href="#" onclick="invalideButton();">确认</a></span>	
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
				document.forms.batch.submit();
			
		}
	</script>
</html>