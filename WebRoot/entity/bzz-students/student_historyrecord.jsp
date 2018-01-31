<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String d = String.valueOf(Math.random());
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />

		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
		<link rel="stylesheet" type="text/css" media="all" href="/js/calendar/calendar-win2000.css" />
		<script type="text/javascript">
			var reg=/^(([1-9](\d){3})-((0?[1-9])|(1[0-2]))-((0?[1-9])|([1-2](\d))|(3[0-1])))?$/;
			function validateValue(strValue, strMatchPattern ){
			    var objRegExp = new RegExp(strMatchPattern);
			    return objRegExp.test(strValue);
			}
			function check(){
				var payDateStart = document.getElementById('paymentDateStart').value;
				var payDateEnd = document.getElementById('paymentDateEnd').value;
				var ds = payDateStart;
				var de = payDateEnd;
				if(payDateStart != null && payDateStart != '' ){
					if(!validateValue(payDateStart,reg) || !validateValue(payDateEnd,reg)){
						alert('请输入正确的日期格式,如2010-10-01.');
						return false;
					}
					ds = ds.split('-');
					ds = new Date(ds[0], ds[1], ds[2]).getTime();
				}
				if(payDateEnd != null && payDateEnd != '' ){
					if(!validateValue(payDateStart,reg) || !validateValue(payDateEnd,reg)){
						alert('请输入正确的日期格式,如2010-10-01.');
						return false;
					}
					de = de.split('-');
					de = new Date(de[0],de[1],de[2]).getTime();
				}
				if(payDateStart != null && payDateStart != '' && payDateEnd != null && payDateEnd != '' ) {
					if(ds>de) {
						alert('开始日期大于结束日期，请重新设定');
						return false;
					}
				}
				return true;
			}
			function doSearch(){
				if(check()){
					document.forms.form.startIndex.value= 0;
					document.forms.form.submit();
				}
			}
			function clearTime() {
				document.getElementById('paymentDateStart').value = '';
				document.getElementById('paymentDateEnd').value = '';
				document.getElementById('orderno').value = '';
				document.getElementById('tchprice').value = '';
				document.getElementById('cname').value = '';
				
			}
			
			function mergeOrder() {
				var orderChecks = document.getElementsByName("orderCheck");
				var ordersStr = "";
				var cou = 0;
				if(confirm("确认合并申请发票吗?")) {
					for(var i = 0; i < orderChecks.length; i++) {
						if(orderChecks[i].checked) {
							ordersStr += orderChecks[i].value + ",";
							cou++;
						}
					}
					if(cou > 1) {
						window.location.href="/entity/workspaceStudent/studentWorkspace_toApplyInvoice.action?id=" + ordersStr;
					} else {
						alert("两条或两条以上订单才允许合并申请发票!");
						return;
					}
				} else {
					return;
				}
				//window.location.href="/entity/workspaceStudent/studentWorkspace_toMergeOrder.action?id=" + id + "&amount=" + amount;
			}
			
			function cancleMergeOrder() {
				var orderChecks = document.getElementsByName("orderCheck");
				var ordersStr = "";
				var n = 0;
				for(var k = 0; k < orderChecks.length; k++) {
					if(orderChecks[k].checked) {
						n++;
					}
				}
				if(n > 1) {
					alert("只能选择一条订单进行删除发票操作！");
					return;
				}
				if(confirm("确认删除发票吗?")) {
					for(var i = 0; i < orderChecks.length; i++) {
						if(orderChecks[i].checked) {
							ordersStr += orderChecks[i].value + ",";
						}
					}
					window.location.href="/entity/workspaceStudent/studentWorkspace_toCancleMergeOrder.action?orders=" + ordersStr;
				} else {
					return;
				}
			}
			
			function creditNote() {
				var orderChecks = document.getElementsByName("orderCheck");
				var ordersStr = "";
				if(confirm("确认冲红发票吗?")) {
					var cou = 0;
					for(var i = 0; i < orderChecks.length; i++) {
						if(orderChecks[i].checked) {
							ordersStr += orderChecks[i].value
							cou++;
						}
					}
					if(cou > 1) {
						alert("最多只能选择一个订单进行发票冲红操作");
						return;
					} else if(orderChecks.length < 1) {
						alert("请最少选择一个订单进行操作");
						return;
					}
					window.location.href="/entity/workspaceStudent/studentWorkspace_toCreditNote.action?id=" + ordersStr;
				} else {
					return;
				}
			}
		</script>
	</head>
	<body>
		<form action="/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action" name="form" method="post">
			<input type="hidden" name="startIndex" value="<s:property value="startIndex" />" />
			<input type="hidden" name="pageSize" value="<s:property value="pageSize" />" />
			<div class="" style="width: 100%; padding: 0; margin: 0;" align="center">
				<div class="main_title">
					我的订单
				</div>
				<div>
					<table width="95%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="left" width="33%">
								订单号：
								<input type="text" name="orderno" id="orderno" value="<s:property value="orderno" />" />
							</td>
							<td align="left" width="33%">
								价格(元)：
								<input type="text" name="tchprice" id="tchprice" value="<s:property value="tchprice" />" />
							</td>
							<td align="left" width="33%">
								订单备忘录：
								<input type="text" name="cname" id="cname" value="<s:property value="cname" />" />
							</td>
							<td></td>
						</tr>
						<tr>
							<td align="left" colspan="2">
								支付时间：
								<input type="text" name="paymentDateStart" id="paymentDateStart" value="<s:property value="paymentDateStart"/>" />
								<img src="/js/calendar/img.gif" width="20" height="14" id="f_trigger_b" style="border: 1px solid #551896; cursor: pointer;" title="Date selector" onmouseover="this.style.background='white';" onmouseout="this.style.background=''" />
								到 
								<input type="text" name="paymentDateEnd" id="paymentDateEnd" value="<s:property value="paymentDateEnd"/>" />
								<img src="/js/calendar/img.gif" width="20" height="14" id="f_trigger_c" style="border: 1px solid #551896; cursor: pointer;" title="Date selector" onmouseover="this.style.background='white';" onmouseout="this.style.background=''" />
							</td>
							<td align="right">
								<input type="button" value="&nbsp;查&nbsp;找&nbsp;" onclick="doSearch();" />
								<input type="button" value="&nbsp;清&nbsp;空&nbsp;" onclick="clearTime();" />
							</td>
						</tr>
					</table>
					<table class="datalist" width="100%">
						<tr>
							<td colspan="12" align="left">
								<div id="fany">
									<script language="JavaScript">
										var page2 = new showPages("page2",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
										page2.printHtml();
									</script>
								</div>
							</td>
						</tr>
						<tr bgcolor="#8dc6f6">
							<th width="4%">
							</th>
							<th width="10%">
								订单号
							</th>
							<th width="9%">
								订单备忘录
							</th>
							<th width="10%">
								价格(元)
							</th>
							<th width="7%">
								学时
							</th>
							<th width="10%">
								合并订单号
							</th>
							<th width="10%">
								支付状态
							</th>
							<th width="10%">
								支付方式
							</th>
							<th width="8%">
								发票状态
							</th>
							<th width="6%">
								发票详情
							</th>
							<th width="8%">
								退费操作
							</th>
							<th width="8%">
								关闭订单
							</th>
						</tr>
						<s:if test="page != null">
							<s:iterator value="page.items" id="order">
								<tr>
									<td>
										<input type="checkbox" id="orderCheck" name="orderCheck" value="<s:property value="#order.id" />"/>
									</td>
									<td>
										<a style="color: #3366ff; cursor: pointer;" onclick="viewDetail('<s:property value="#order.id"/>', '<s:property value="#order.enumConstByFlagRefundState.code" default="10"/>')"><s:property value="#order.seq" /></a>
									</td>
									<td>
										<s:property value="#order.cname" />
									</td>
									<td>
										<s:property value="#order.amount" />
									</td>
									<td>
										<s:property value="#order.classHour" />
									</td>
									<td>
										<s:property value="#order.mergeSeq" />
									</td>
									<td>
										<s:if test="#order.enumConstByFlagPaymentState.code == 1 || #order.enumConstByFlagPaymentState.code == 2">
											已到账
										</s:if>
										<s:elseif test="#order.enumConstByFlagPaymentMethod.name=='预付费账户支付'">
											<font color="red" title="预付费订单异常，请关闭订单，如有疑问请记录订单信息及预付费余额情况并进行反馈。" style="cursor: pointer;"> 订单支付异常 </font>
										</s:elseif>
										<s:elseif test="#order.enumConstByFlagOrderIsValid.code !=1">
											未到账
										</s:elseif>
										<%-- 支付接口切换&paymentType=99bill --%>
										<s:else>未到账|<a style="color: #3366ff; cursor: pointer;" onclick="window.open('/entity/workspaceStudent/studentWorkspace_onlinePayment.action?id=<s:property value="#order.id" />&paymentType=99bill', 'to_pay')"><font color="red">去支付</font> </a>
										</s:else>
									</td>
									<td style="cursor: pointer;" title="<s:property value="#order.enumConstByFlagOrderType.name" />">
										<s:property value="#order.enumConstByFlagPaymentMethod.name" />
									</td>
									<td width="70">
										<s:if test="#order.peBzzInvoiceInfos.size > 0">
											<s:if test='getOrderInvoiceState(#order.peBzzInvoiceInfos)=="待开" && getOrderInvoiceType(#order.peBzzInvoiceInfos) == "4"'>
												<a onclick="seekInvoice('<s:property value="#order.id"/>', '<s:property value="#order.amount"/>');" style="color: #3366ff; cursor: pointer;"><s:property value="getOrderInvoiceState(#order.peBzzInvoiceInfos)" /> </a>
											</s:if>
											<s:else>
												<s:property value="getOrderInvoiceState(#order.peBzzInvoiceInfos)" />
											</s:else>
										</s:if>
										<s:elseif test="#order.peBzzInvoiceInfos.size <= 0">
											<a onclick="applyInvoice('<s:property value="#order.id"/>', '<s:property value="#order.amount"/>', '<s:property value="#order.enumConstByFlagPaymentMethod.code" />','<s:property value="#order.enumConstByFlagRefundState.name"/>','<s:property value="#order.enumConstByFlagPaymentState.code"/>','<s:date name="#order.paymentDate" format="yyyy-MM-dd"/>');" style="color: #3366ff; cursor: pointer;">申请发票</a>
										</s:elseif>
									</td>
									<td width="70">
										<!-- <a href='/entity/workspaceStudent/studentWorkspace_viewOrder.action?id=<s:property value="#order.id"/>'>详情</a> -->
										<s:if test="#order.peBzzInvoiceInfos.size > 0">
											<a style="color: #3366ff; cursor: pointer;" onclick="viewInvoiceDetail('<s:property value="#order.id"/>')">详情</a>
										</s:if>
										<s:elseif test="#order.peBzzInvoiceInfos.size <= 0">
											详情
										</s:elseif>
									<td width="70">
										<s:if test="setIsNull(#order.id)">
											<s:if test="#order.enumConstByFlagPaymentState.code!=1">
												<s:property value="order.enumConstByFlagPaymentState.name" />
											</s:if>
											<s:else>
												<a href='/entity/workspaceStudent/studentWorkspace_applyRefund.action?id=<s:property value="#order.id"/>'>申请退费</a>
											</s:else>
										</s:if>
										<s:else>
											<a href='/entity/workspaceStudent/studentWorkspace_viewReason.action?id=<s:property value="#order.id"/>'><s:property value="#order.enumConstByFlagRefundState.name" /> </a>
										</s:else>
									</td>
									<td>
										<s:if test="#order.enumConstByFlagOrderIsValid.code !=0">										
										<a onclick="invalidOrder('<s:property value="#order.id"/>', '<s:property value="#order.enumConstByFlagPaymentState.code"/>')"><font color="red">关闭</font> </a>
										</s:if>
										<s:else> 已关闭</s:else>
									</td>
								</tr>
								<!-- 													
													 <tr>
														<td>
															<s:property value="#order[1]" />
														</td>
														<td>
															<s:property value="#order[2]" />
														</td>
														<td width="15%">
															<s:if test="#order[5] == 1">
																<s:date name="#order[3]" format="yyyy-MM-dd" />
															</s:if>
															<s:else>未到账|<a href="javascript:void(0);" onclick="window.open('/entity/workspaceStudent/studentWorkspace_continuePaymentOnline.action?id=<s:property value="#order[0]" />', 'newwindow')"><font color="red">去支付</font></a></s:else>  
														</td>
														<td>
															<s:property value="#order[]" />
														</td>
														<td>
															<s:property value="#order[6]" />
														</td>
														<td>
															<s:if test="#order.peBzzInvoiceInfos.size > 0">
																已开
															</s:if>
															<s:else>
																<a onclick="applyInvoice('<s:property value="#order[0]"/>', '<s:property value="#order[7]" />');" href="javascript:void(0);">申请发票</a>
															</s:else>
														</td>
														<td>
															<a href='/entity/workspaceStudent/studentWorkspace_viewOrder.action?id=<s:property value="#order[0]"/>'>查看详情</a>
														<td>
															<s:if test="setIsNull(#order.id)">
																<a href='/entity/workspaceStudent/studentWorkspace_applyRefund.action?id=<s:property value="#order[0]"/>'>申请退费</a>
															</s:if>
															<s:else>
																<a href='/entity/workspaceStudent/studentWorkspace_viewReason.action?id=<s:property value="#order[0]"/>'><s:property value="#order[8]"/></a>
															</s:else>
														</td>
														<td><a onclick="invalidOrder('<s:property value="#order[0]"/>', '<s:property value="#order[5]"/>')" href="javascript:void(0);">关闭</a></td>
													</tr>
													 -->
							</s:iterator>
						</s:if>
						<tr>
							<td colspan="2">
								总计
							</td>
							<td colspan="3">
								预付费账户支付订单数
							</td>
							<td colspan="1">
								<s:property value="#request.tongjiList[0]" />
							</td>
							<td colspan="2">
								在线支付订单数
							</td>
							<td colspan="4">
								<s:property value="#request.tongjiList[1]" />
							</td>
						</tr>
						<tr>
							<td colspan="12">
								<input type="button" value="合并申请发票" onclick="mergeOrder()"/>&nbsp;&nbsp;
								<input type="button" value="删除发票申请" onclick="cancleMergeOrder()"/>&nbsp;&nbsp;
								<!--<input type="button" value="发票冲红申请" onclick="creditNote()"/>-->
							</td>
						</tr>
						<tr>
							<td colspan="12" align="left">
								<div id="fany">
									<script language="JavaScript">
		var page1 = new showPages("page1",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
		page1.printHtml();
		</script>
								</div>
							</td>
						</tr>
					</table>
					<br />
				</div>
			</div>
		</form>
	</body>
	<script type="text/javascript">
    Calendar.setup({
        inputField     :    "paymentDateStart",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_b",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
    Calendar.setup({
        inputField     :    "paymentDateEnd",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_c",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
</script>
	<script>
		function checkDate() {
			var date = document.getElementById('paymentDate').value;
			var reg = /^(\d{4})-(0\d{1}|1[0-2])-(0\d{1}|[12]\d{1}|3[01])$/;
			if(date!='' && !reg.test(date)) {
				alert('请输入合法的日期格式yyyy-MM-dd');
				return false;
			}
			document.forms[0].submit();
		}
		
		function invalidOrder(oid, state) {
			if(state==1) {
				alert('此订单已支付，不能关闭');
				return;
			}
			if(confirm('关闭后，订单中选课内容也将被撤销，您确定要关闭订单吗？')) {
				window.location.href = '/entity/workspaceStudent/studentWorkspace_closeOrder.action?id='+oid;
			}
		}
		
		function applyInvoice(id, amount, state,name,payState,paymentDate) {
			if (name=='已退费' || name=='退费中') {
				alert('申请退费的订单不能开发票');
				return;
			} 
			if(name=='待审核'){
				alert('待审核订单不能开发票');
				return;
			}
			if(state==1) {
				alert('预付费支付不能开具发票');
				return;
			}
			if(payState=='0'){
				alert('订单未支付不能申请发票');
				return;
			}
			var today = new Date();
			var payDate = new Date(Date.parse(paymentDate.replace(/-/g, "/")));
			if(today < new Date(payDate.getTime() + (7 * 24 * 3600 * 1000))){
				alert('订单支付7天后可以申请发票');
				return;
			}
			var sdatestr = today.getFullYear() + "-12-20";
			var edatestr = today.getFullYear() + 1 + "-01-01";
			var sdate = new Date(Date.parse(sdatestr.replace(/-/g, "/")));
			var edate = new Date(Date.parse(edatestr.replace(/-/g, "/")));
			if(today >= sdate && today <= edate) {
				alert("12月20日至1月1日期间无法申请当年度发票");
			}
			alert("当此订单金额小于1000元时，学员将无法点击选择增值税专用发票，页面提供提示信息，因财务制度要求如需申请增值税专用发票，则可通过多笔订单合并(金额>=1000)方式申请");
			window.location.href="/entity/workspaceStudent/studentWorkspace_toApplyInvoice.action?id=" + id + "&amount=" + amount;
		}
		
		function viewDetail(oid, code) {
				window.location.href='/entity/workspaceStudent/studentWorkspace_viewOrder.action?id='+oid;
		}
		
		function viewInvoiceDetail(oid) {
				window.location.href='/entity/workspaceStudent/studentWorkspace_viewInvoiceDetail.action?id='+oid;
		}
		
		function seekInvoice(id, amount) {
			window.location.href = '/entity/workspaceStudent/studentWorkspace_seekInvoice.action?id=' + id;
		}
	</script>
</html>