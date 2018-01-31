
<%@page import="java.text.SimpleDateFormat"%><%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>tableType_2</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">
	</head>
	<body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">
	<div id="main_content"><div align="center"> 
    </div><div class="cntent_k"><div align="center"> 
   	  </div><div class="k_cc"><div align="center"> 
</div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="28">
						<div class="content_title" id="zlb_title_start">
							订单详情
						</div id="zlb_title_end">
					</td>
				</tr>
				<tr>
					<td valign="top" class="pageBodyBorder" style="background: none;">
						<div class="border" style="padding-left: 30px">
							<table style="text-align: left;" width="96%" border="0"
								cellpadding="0" cellspacing="0">
								<tr align="bottom" class="postFormBox">
									<td width="">
										<span class="name">订&nbsp;&nbsp;单&nbsp;&nbsp;号：</span>
									</td>
									<td width="">
										<font color="red"><s:property value="bean.seq"/></font>
									</td>
									<td>
										<span class="name">备注：</span>
									</td>
									<td>
										<font color="red"><s:property value="bean.cname"/></font>
									</td>
								</tr>
														
								<tr align="bottom" class="postFormBox">
										<td width="">
										<span class="name">到账状态</span>
									</td>
									<td width="">
										<font color="red"><s:property value="bean.enumConstByFlagPaymentState.name" /></font>
									</td>
									<td width="">
										<span class="name">总&nbsp;&nbsp;金&nbsp;&nbsp;额：</span>
									</td>
									<td width="">
										<font color="red"><s:property value="bean.amount"/>&nbsp;元</font>
									</td>
								</tr>
								<tr class="postFormBox">
									<td width="">
										<span class="name">支付类型：</span>
									</td>
									<td width="">
										<font color="red"><s:property value="bean.enumConstByFlagPaymentType.name"/></font>
									</td>
									<td width="">
										<span class="name">支付方式：</span>
									</td>
									<td width="">
										<font color="red"><s:property value="bean.enumConstByFlagPaymentMethod.name"/></font>
									</td>
								</tr>
								<tr class="postFormBox">
									<td width="">
										<span class="name">下单日期：</span>
									</td>
									<td width="">
										<font color="red"><s:date name="bean.createDate" format="yyyy-MM-dd HH:mm:ss"/></font>
									</td>
									<td width="">
										<span class="name">到账确认时间：</span>
									</td>
									<td width="">
										<font color="red">
										<s:if test="bean.paymentDate!=null">
											<s:date name="bean.paymentDate" format="yyyy-MM-dd HH:mm:ss"/>
										</s:if>
										<s:else>
										    --
										</s:else>
										</font>
									</td>
									
								</tr>
								<!-- 学员信息 -->
											<tr class="postFormBox">
												<td>
													<span class="name">
														<s:if test="#request.peBzzStudent!=null">系统编号：</s:if>
														<s:else>机构代码：</s:else>
													</span>
												</td>
												<td>
													<s:property value="#request.peBzzStudent.regNo"/>
													<s:property value="#request.peEnterpriseManager.peEnterprise.code"/>
												</td>
												<td>
													<span class="name">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span>
												</td>
												<td>
													<s:property value="#request.peBzzStudent.trueName"/>
													<s:property value="#request.peEnterpriseManager.name"/>
												</td>
											</tr>
											<s:if test="#request.peBzzStudent!=null">
											<tr class="postFormBox">
												<td>
													<span class="name">国&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;籍：</span>
												</td>
												<td>
													<s:property value="#request.peBzzStudent.cardType" default="--"/>
												</td>
												<td>
													<span class="name">证件编号：</span>
												</td>
												<td>
													<s:property value="#request.peBzzStudent.cardNo"  default="--"/>
												</td>
											</tr>
											
											<tr class="postFormBox">
												<td>
													<span class="name">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历：</span>
												</td>
												<td>
													<s:property value="#request.peBzzStudent.education"  default="--"/>
												</td>
												<td>
													<span class="name">工作部门：</span>
												</td>
												<td>
													<s:property value="#request.peBzzStudent.department"  default="--"/>
												</td>
											</tr>
											
											<tr class="postFormBox">
												<td>
													<span class="name">工作所在地：</span>
												</td>
												<td>
													<s:property value="#request.peBzzStudent.address"  default="--"/>
												</td>
												<td>
													<span class="name">职务：</span>
												</td>
												<td>
													<s:property value="#request.peBzzStudent.position"  default="--"/>
												</td>
											</tr>
											</s:if>
											<tr class="postFormBox">
												<td>
													<span class="name">电子邮件：</span>
												</td>
												<td>
												<s:if test="#request.peBzzStudent!=null">													
													<s:property value="#request.peBzzStudent.email" default="--"/>
												</s:if>
												<s:else>
													<s:property value="#request.peEnterpriseManager.email" default="--"/>												
												</s:else>
												</td>
												<td>
													<span class="name">手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</span>
												</td>
												<td>
												<s:if test="#request.peBzzStudent!=null">	
													<s:property value="#request.peBzzStudent.mobilePhone" default="--"/>
												</s:if>
												<s:else>												
													<s:property value="#request.peEnterpriseManager.mobilePhone"  default="--"/>
												</s:else>
												</td>
											</tr>
											 <tr class="postFormBox">
												<td>
													<span class="name">所在机构：</span>
												</td>
												<td>
												<s:if test="#request.peBzzStudent!=null">	
													<s:property value="#request.peBzzStudent.peEnterprise.name" default="--"/>
												</s:if>
												<s:else>												
													<s:property value="#request.peEnterpriseManager.peEnterprise.name" default="--"/>
												</s:else>
												</td>
												<s:if test="#request.peEnterpriseManager==null">
												<td>
													<span class="name">所在机构代码：</span>
												</td>
												<td>
												<s:if test="#request.peBzzStudent!=null">	
													<s:property value="#request.peBzzStudent.peEnterprise.code" default="--"/>
												</s:if>
												<s:else>
													<s:property value="#request.peEnterpriseManager.peEnterprise.code" default="--"/>												
												</s:else>
												</td>
												</s:if>
												<s:else>
													<td>
														&nbsp;
													</td>
												<td>&nbsp;
												</td>
												</s:else>
											</tr> 
								<s:if test="peBzzInvoiceInfo != null">
								
								<tr class="postFormBox">
									<td colspan="2">
										<table width="100%" id="invoice" border="0" cellpadding="0" cellspacing="0" style="display:none;">
											<tr class="postFormBox">
												<td>
													<span class="name">付款单位（个人）：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.title" type="text" size="15"
														maxlength="100">
												</td>
												<td>
													<span class="name">邮寄地址：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.address" type="text" size="15"
														maxlength="100">
												</td>
											</tr>
											<tr class="postFormBox">
												<td>
													<span class="name">邮政编码：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.zipCode" type="text" size="15"
														maxlength="15">
												</td>
												<td>
													<span class="name">收&nbsp;&nbsp;件&nbsp;&nbsp;人：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.addressee" type="text" size="15"
														maxlength="15">
												</td>
											</tr>
											<tr class="postFormBox">
												<td>
													<span class="name">联系电话：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.phone" type="text" size="15"
														maxlength="15">
												</td>
												<td>
													<span class="name">&nbsp;</span>
												</td>
												<td>
													&nbsp;
												</td>
											</tr>
											
										</table>
									</td>
								</tr>
							</s:if>

							</table>
							<s:if test="electiveList != null && electiveList.size() >0">
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
									<th width='40%' style='white-space: nowrap;'>
										<span class="link">课程名称</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">课程金额(元)</span>
									</th>
										<th width='10%' style='white-space: nowrap;'>
										<span class="link">课程学时</span>
									</th>
									
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">课程状态</span>
									</th>
								</tr>
								<s:iterator value="electiveList" id="elective">
									<tr class='oddrowbg'>
									<td style='white-space: nowrap; text-align: center;'>
										<s:property value="#elective[0]"/>
									</td>
									<td style='white-space: nowrap; text-align: center;'>
										<s:property value="#elective[1]"/>
									</td>
									<td style='white-space: nowrap; text-align: center;'>.
										<s:property value="#elective[2]"/>
									</td>
									<td style='white-space: nowrap; text-align: center;'>
										<s:property value="#elective[3]"/>
									</td>
									<td style='white-space: nowrap; text-align: center;'>
										<s:property value="#elective[4]"/>
									</td>
									<td style='white-space: nowrap; text-align: center;'>
										<s:property value="#elective[5]"/>
									</td>
									
									
									<td style='white-space: nowrap; text-align: center;'>
										<s:if test="#elective[10]!=null && #elective[10]==0"><font title="关闭订单、重新购买会造成课程删除" color="red" style="cursor: pointer;">已点击重新购买</font></s:if>
										<s:elseif test="#elective[6]=='INCOMPLETE'">未完成</s:elseif>
										<s:elseif test="#elective[6]=='COMPLETED'">已完成</s:elseif>
										<s:elseif test="#elective[6]=='UNCOMPLETE'">未学习</s:elseif>
										<s:else>未学习</s:else>
									</td>
								</tr>
								</s:iterator>
							</table>
							</s:if>
						</div>
					</td>
				</tr>
                          <tr>
                          	<td colspan="10" align="center">
                          		<span style="width: 40px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_2.jpg');">
									<a href="#" onclick="javascript:history.go(-1)">返回</a>
								</span>
                          </tr>
			</table>
				  </div>
    </div>
</div>
<div class="clear"></div>
	</body>
</html>