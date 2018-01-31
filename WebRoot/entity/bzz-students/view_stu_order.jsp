<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String d = String.valueOf(Math.random());
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" /> 
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" /> 
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.STYLE1 {
	color: #FF0000;
	font-weight: bold;
}

.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}
.myTable,.myTable td {
	border: 1px solid #cccccc;
	border-collapse: collapse;
}
-->
</style>
	</head>
	<body>
		<table width="1002" border="0" align="center" cellpadding="0"
			cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td>
					<form  name="form" method="post">
						<table width="752" border="0" align="left" cellpadding="0"
							cellspacing="0">
								<input type="hidden" name="startIndex" value="<s:property value="startIndex" />" />
								<input type="hidden" name="pageSize" value="<s:property value="pageSize" />" />
							<tr>
								<td width="752" valign="top">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										
										 <tr align="center" valign="top">
											<td height="17" align="left" class="twocentop" colspan="2">
												<div class="main_title">当前位置：订单详情</div>
											</td>
										</tr>
										<tr align="center">
											<td height="29" background="" colspan="2">
												<table width="100%" border="0" cellpadding="0"
													cellspacing="0" class="datalist">
													
													<!-- 订单信息 -->
													<tr align="bottom" class="postFormBox">
														<td width="">
															<span class="name">订&nbsp;&nbsp;单&nbsp;&nbsp;号：</span>
														</td>
														<td width="">
															<font color="red"><s:property value="#request.peBzzOrderInfo.seq" />
															</font>
														</td>
														<td>
															<span class="name">备注：</span>
														</td>
														<td>
															<font color="red"><s:property value="#request.peBzzOrderInfo.cname" />
															</font>
														</td>
													</tr>
													

													<tr align="bottom" class="postFormBox">
														<td width="">
															<span class="name">到账状态</span>
														</td>
														<td width="">
															<font color="red"><s:property
																	value="#request.peBzzOrderInfo.enumConstByFlagPaymentState.name" />
															</font>
														</td>
														<td width="">
															<span class="name">总&nbsp;&nbsp;金&nbsp;&nbsp;额：</span>
														</td>
														<td width="">
															<font color="red"><s:property value="#request.peBzzOrderInfo.amount" />&nbsp;元</font>
														</td>
													</tr>
													<tr class="postFormBox">
														<td width="">
															<span class="name">支付类型：</span>
														</td>
														<td width="">
															<font color="red"><s:property
																	value="#request.peBzzOrderInfo.enumConstByFlagPaymentType.name" />
															</font>
														</td>
														<td width="">
															<span class="name">支付方式：</span>
														</td>
														<td width="">
															<font color="red"><s:property
																	value="#request.peBzzOrderInfo.enumConstByFlagPaymentMethod.name" />
															</font>
														</td>
													</tr>
													<tr class="postFormBox">
														<td width="">
															<span class="name">下单日期：</span>
														</td>
														<td width="">
															<font color="red"><s:date name="#request.peBzzOrderInfo.createDate" format="yyyy-MM-dd HH:mm:ss"/></font>
														</td>
														<td width="">
															<span class="name">到账确认时间：</span>
														</td>
														<td width="">
															<font color="red">
															<s:if test="#request.peBzzOrderInfo.paymentDate!=null">
																<s:date name="#request.peBzzOrderInfo.paymentDate" format="yyyy-MM-dd HH:mm:ss"/>
															</s:if>
															<s:else>
															    --
															</s:else>
															</font>
														</td>
													</tr>
												
								<!-- 学员信息 -->
											<!--  <tr class="postFormBox">
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
											-->
												</table>
												<table width="100%" border="0" cellpadding="0"
													cellspacing="0" class="datalist">
													<tr height=30 bgcolor="#8dc6f6">
														<th width="8%" align="center">
															课程编号
														</th>
														<th width="14%" align="center">
															课程名称
														</th>
														<th width="8%" align="center">
															课程性质
														</th>
														<th width="10%" align="center">
															业务分类
														</th>
														<th width="8%" align="center">
															大纲分类
														</th>
														<th width="8%" align="center">
															内容属性
														</th>
														<th width="5%" align="center">
															学时
														</th>
														<th width="5%" align="center">
															期限
														</th>
														<th width="5%" align="center">
															价格(元)
														</th>
														<th width="8%" align="center">
															学习状态
														</th>
													</tr>
													<s:iterator value="page.items" id="order">
														<tr height=30>
															<td align="center">
																<s:property value="#order[2]" />
															</td>
															<td align="left">
																<a style = "color:#3366ff; cursor: pointer;" onclick="window.open('/entity/function/coursenotequery.jsp?course_id=<s:property value="#order[6]"/>','newwindow_detail')"><s:property value="#order[3]" /></a>
															</td>
															<td align="center">
																<s:property value="#order[7]" />
															</td>
															<td align="center">
																<s:property value="#order[9]" />
															</td>
															<td align="center">
																<s:property value="#order[10]" />
															</td>
															<td align="center">
																<s:property value="#order[11]" />
															</td>
															<td align="center">
																<s:property value="#order[5]" />
															</td>
															<td align="center">
																<s:property value="#order[12]" />
															</td>
															<td align="center">
																<s:property value="#order[13]" />
															</td>
															<td align="center">
																<s:if test="#order[8]!=null && #order[8]==0"><font title="关闭订单、重新购买会造成课程删除" color="red" style="cursor: pointer;">已点击重新购买</font></s:if>
																<s:else>
																	<s:if test="#order[4]=='INCOMPLETE'">未完成</s:if>
																	<s:elseif test="#order[4]=='COMPLETED'">已完成</s:elseif>
		    														<s:elseif test="#order[4]=='UNCOMPLETE'">未学习</s:elseif>
		    														<s:else>未学习</s:else>
																</s:else>
															</td>
														</tr>
													</s:iterator>
													<tr>
														<td colspan="10" align="left">
															订单学时合计：<s:property value="#request.classHour"/>&nbsp;
															必修学时合计：<s:property value="#request.totalhourbx"/>&nbsp;
															选修学时合计：<s:property value="#request.totalhourxx"/>&nbsp;
															学时合计：<s:property value="#request.tchprice"/>&nbsp;(元)
														
														</td>
													</tr>
													<tr height=30>
														<td colspan=10>
														<div id="fany">
															<script language="JavaScript"  >
																var page1 = new showPages("page1",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"form");
																page1.printHtml();
															</script>
														</div>
														</td>
													</tr>
													<tr>
														<td colspan="10" align="center">
															<div class="ck" style="width:70px;background-image:url('/entity/bzz-students/images/index_21.jpg')" ><a href="/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action" >返回</a></div>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
								<td width="13">
									&nbsp;
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>