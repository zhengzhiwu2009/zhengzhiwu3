<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>预付费方式</title>
	<meta http-equiv="expires" content="0">    
	<!-- <link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css"> -->
	<style type="text/css"><!--<%@ include file="/entity/manager/css/admincss.css"%>--></style>		
	<script language="JavaScript" src="js/shared.js"></script>
  </head>
  
  <body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="28">
					<div class="content_title" id="zlb_title_start">
						预付费账户管理
					</div id="zlb_title_end">
				</td>
			</tr>
			<tr>
				<td valign="top" class="pageBodyBorder" style="background: none;">
					<div class="border" style="padding-left: 30px">
						<table style="text-align: left;" width="96%" border="0"
							cellpadding="0" cellspacing="0">
							<tr vlign="bottom" class="postFormBox">
								<td >
									<span class="name">账户余额：</span>
									&nbsp;&nbsp;&nbsp;
									<font color="red"><s:property value="calClassHour(#request.bean.ssoUser)" /></font>&nbsp;&nbsp;学时
								</td>
							</tr>
							<tr class="postFormBox">
								<td colspan="2"></td>
							</tr>
							<tr class="postFormBox">
								<td colspan="2">
									<span class="STYLE4">
										<input type="button" value="购买学时" class="name1"
											style="width: 70px;"
											onclick='window.open("/entity/basic/prepaidAccountManage_toPurchaseStudyHour.action?bean.id=<s:property value="#request.bean.id" />","_blank");' />
										<br/><br/>
										<input 
											type="button" value="学生分配学时" class="name1"
											style="width: 90px;"
											onclick='window.location.href="/entity/basic/assignStudyHour.action?bean.id=<s:property value="#request.bean.id"/>"' />
										
										 <input
											type="button" value="查看学时购买记录" class="name1"
											style="width: 140px;"
											onclick='window.location.href="/entity/basic/buyClassHourRecord.action"' />
										 <input
											type="button" value="查看学时分配记录" class="name1"
											style="width: 140px;"
											onclick='window.location.href="/entity/basic/checkClassHourRecord.action?flag=old&forg=y"' />
											
										 <input
											type="button" value="查看学时剥离记录" class="name1"
											style="width: 140px;"
											onclick='window.location.href="/entity/basic/checkClassHourRecord.action?flag=strip&forg=y"' />
										 <s:if test="#session.Logintype==2">
										 	<br/><br/>
											 <input
												type="button" value="二级集体分配学时" class="name1"
												style="width: 140px;"
												onclick='window.location.href="/entity/basic/peSubEnterprise.action?bean.id=<s:property value="#request.bean.id"/>&flag=inner&forg=y"' />
											 <input
												type="button" value="集体学时分配记录" class="name1"
												style="width: 140px;"
												onclick='window.location.href="/entity/basic/checkClassHourRecordEnt.action?flag=old&forg=y"' />
											 <input
												type="button" value="集体学时剥离记录" class="name1"
												style="width: 140px;"
												onclick='window.location.href="/entity/basic/checkClassHourRecordEnt.action?flag=strip&forg=y"' />
										 </s:if>
									</span>
								</td>

							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>

		</table>
	</body>
</html>
