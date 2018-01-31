<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>tableType_2</title>
		<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="./css/css.css" rel="stylesheet" type="text/css">
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
	</head>
	<body leftmargin="0" topmargin="0" class="scllbar">
	<div id="main_content"><div align="center"> 
    </div><div class="cntent_k"><div align="center"> 
   	  </div><div class="k_cc"><div align="center"> 
</div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="28">
						<div class="content_title" id="zlb_title_start">
							退费订单详情
						</div id="zlb_title_end">
					</td>
				</tr>
				<tr>
					<td valign="top" class="pageBodyBorder" style="background: none;">
						<div style="padding-left: 30px">
							<table style="text-align: left;" width="96%" border="0"
								cellpadding="0" cellspacing="0">
								<tr align="bottom" class="postFormBox">
									<td width="45%">
										<span class="name">订&nbsp;&nbsp;单&nbsp;&nbsp;号：</span>
									</td>
									<td width="">
										<font color="red"><s:property value="bean.peBzzOrderInfo.seq"/></font>
									</td>
								</tr>
								<tr class="postFormBox">
									<td width="45%">
										<span class="name">订单备忘录：</span>
									</td>
									<td>
										<font color="red"><s:property value="bean.peBzzOrderInfo.cname"/></font>
									</td>
								</tr>
								<s:if test="bean.peBzzOrderInfo.EnumConstByFlagOrderType.code == '1'" >
								<tr align="bottom" class="postFormBox">
									<td width="45%">
										<span class="name">购买学时数：</span>
									</td>
									<td width="">
										<font color="red"><s:property value="bean.peBzzOrderInfo.classhour"/></font>
									</td>
								</tr>
								</s:if>
								<tr class="postFormBox">
									<td width="45%">
										<span class="name">总&nbsp;&nbsp;金&nbsp;&nbsp;额：</span>
									</td>
									<td width="">
										<font color="red"><s:property value="bean.peBzzOrderInfo.amount"/>&nbsp;元</font>
									</td>
								</tr>
								<tr class="postFormBox">
									<td width="45%">
										<span class="name">支付类型：</span>
									</td>
									<td width="">
										<font color="red"><s:property value="bean.peBzzOrderInfo.enumConstByFlagPaymentType.name"/></font>
									</td>
								</tr>
								<tr class="postFormBox">
									<td width="45%">
										<span class="name">支付方式：</span>
									</td>
									<td width="">
										<font color="red"><s:property value="bean.peBzzOrderInfo.enumConstByFlagPaymentMethod.name"/></font>
									</td>
								</tr>
								<tr class="postFormBox">
									<td width="45%">
										<span class="name">下单日期：</span>
									</td>
									<td width="">
										<font color="red"><s:date name="bean.peBzzOrderInfo.createDate"/></font>
									</td>
								</tr>
								<tr class="postFormBox">
									<td width="45%">
										<span class="name">到账确认时间：</span>
									</td>
									<td width="">
										<font color="red"><s:date name="bean.peBzzOrderInfo.paymentDate"/></font>
									</td>
								</tr>
							</table>
							<s:if test="electiveList != null && electiveList.size >0">
							<table width='98%' align="center" cellpadding='1' cellspacing='0'
								class='list'>
								<tr>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">系统编号</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">学员姓名</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">课程编号</span>
									</th>
									<th width='' style='white-space: nowrap;'>
										<span class="link">课程名称</span>
									</th>
								</tr>
								<s:iterator value="electiveList" id="elective">
									<tr class='oddrowbg'>
									<td style='white-space: nowrap; text-align: center;'>
										<s:property value="#elective.peBzzStudent.regNo"/>
									</td>
									<td style='white-space: nowrap; text-align: center;'>
										<s:property value="#elective.peBzzStudent.trueName"/>
									</td>
									<td style='white-space: nowrap; text-align: center;'>
										<s:property value="#elective.prBzzTchOpencourse.peBzzTchCourse.code"/>
									</td>
									<td style='white-space: nowrap; text-align: center;'>
										<s:property value="#elective.prBzzTchOpencourse.peBzzTchCourse.name"/>
									</td>
								</tr>
								</s:iterator>
							</table>
							</s:if>
							<br/>
							<table style="text-align: left;" width="96%" border="0"
								cellpadding="0" cellspacing="0">
								<tr align="bottom" class="postFormBox">
									<td width="45%">
										<span class="name">退费原因：</span>
									</td>
									<td width="">
										<p><s:property value="bean.reason" /></p>
									</td>
								</tr>
								<tr align="bottom" class="postFormBox">
									<td width="45%">
										<span class="name">拒绝原因：</span>
									</td>
									<td width="">
										<p><s:property value="bean.refauseReason" /></p>
									</td>
								</tr>
							</table>
							<!-- <table width="96%" 	border="0" cellpadding="0" cellspacing="0">
   							 	<tr class="postFormBox">
   							 		<td align="left" valign="middle">
          	 							<span class="name">退费原因：</span>
          							</td>
          							<td align="left" valign="middle">
          	 							<p><s:property value="bean.reason" /></p>
          							</td>
        						</tr>
        						<tr class="postFormBox">
   							 		<td align="left" valign="middle">
          	 							<span class="name">拒绝原因：</span>
          							</td>
          							<td align="left" valign="middle">
          	 							<p><s:property value="bean.refauseReason" /></p>
          							</td>
        						</tr>
      						</table> -->
      						

   							 	<tr>
          							<td colspan="20" align="center">
			                          	<span style="width: 40px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_2.jpg');">
											<a href="<s:property value="servletPath"/>.action" onclick="history.back();">返回</a>
										</span>
          							</td>
        						</tr>

						</div>
					</td>
				</tr>
			</table>
				  </div>
    </div>
</div>
<div class="clear"></div>
	</body>
</html>