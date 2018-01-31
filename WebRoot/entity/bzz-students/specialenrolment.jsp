<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String d = String.valueOf(Math.random());
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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

		<script type="text/javascript">
		Date.prototype.format = function(fmt){ //author: meizz   
		  var o = {   
		    "M+" : this.getMonth()+1,                 //月份   
		    "d+" : this.getDate(),                    //日   
		    "h+" : this.getHours(),                   //小时   
		    "m+" : this.getMinutes(),                 //分   
		    "s+" : this.getSeconds(),                 //秒   
		    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
		    "S"  : this.getMilliseconds()             //毫秒   
		  };   
		  if(/(y+)/.test(fmt))   
		    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
		  for(var k in o)   
		    if(new RegExp("("+ k +")").test(fmt))   
		  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
		  return fmt;   
		}
		function toadd(id,id1){
			var ed = document.getElementById(id).value;
			//var sd = document.getElementById(id1).value;
			var endDate = ed + " 23:59:59";
			endDate = ed;
			var endDate_ = new Date(endDate.replace(/\-/g, "\/"));
			var now =  new Date().format("yyyy-MM-dd hh:mm:ss");
			var now_ = new Date(now.replace(/\-/g, "\/"));
			var pass = now_ - endDate_;
			//var startDate = new Date(sd.replace(/\-/g, "\/"));
			//var pass_ = now_ - startDate;
			if(pass > 0){
				if(pass >0) {
					alert("抱歉，此专项已结束报名，无法进行选课！");
				}
				//if(pass_ < 0) {
					//alert("抱歉，此专项未开始报名，无法进行选课！");
				//}
			}else{
				window.location.href="<%=request.getContextPath()%>/entity/workspaceStudent/studentWorkspace_searchBatchCourse.action?mybatchid=" + id + "&endDate=" + ed;
			}
			
			
		}
		</script>
	</head>
	<body>
		<form action="/entity/workspaceStudent/studentWorkspace_tospecialenrolment.action" name="form" id="form" method="post">
			<input type="hidden" name="startIndex" value="<s:property value="startIndex" />" />
			<input type="hidden" name="pageSize" value="<s:property value="pageSize" />" />
			<div class="" style="width: 100%; padding: 0; margin: 0;" align="center">
				<div class="main_title">
					专项培训报名
				</div>
				<div class="msg" style="height:auto;">
					<table border="0">
						<tr>
							<td>
								<b>1、专项培训课程中可包含必选课程和自选课程，其中必选课程由系统自动添加到已选课程列表中，自选课程则需要学员点击“去选课”按钮自行选择。
							</td>
						</tr>
						<tr>
							<td>
								<b>2、点击“查看”可查看已选的专项培训课程列表，包括必选课程和自选课程。其中必选课程无法删除，自选课程可删除。点击页面下方的“去支付”可进行专项支付。</b>
							</td>
						</tr>
					</table>
				</div>
				<div>
					<table width="95%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="right">
								专项培训名称：&nbsp;&nbsp;&nbsp;
								<input type=text id="batchName" name="batchName" value="<s:property value="batchName"/>" />
							</td>
							<td width="60" align="right">
								<input type="button" value="&nbsp;查&nbsp;找&nbsp;" onclick="document.forms.form.submit();" />
							</td>
						</tr>
					</table>
					<table class="datalist" width="100%">
						<tr>
							<td colspan="7" align="left">
								<div id="fany">
									<script language="JavaScript">
										var page2 = new showPages("page2",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
										page2.printHtml();
									</script>
								</div>
							</td>
						</tr>
						<tr bgcolor="#8dc6f6">
							<th>
								专项培训名称
							</th>
							<th width="12%">
								报名开始时间
							</th>
							<th width="12%">
								报名结束时间
							</th>
							<th width="10%">
								建议学时数
							</th>
							<th width="6%">
								选课
							</th>
							<th width="16%">
								操作
							</th>
						</tr>
						<s:if test="page != null">
							<s:iterator value="page.items" id="batch" status="st">
								<tr>
									<td height="30" title="专项描述：<s:property value="#batch[10]" />">
										<s:property value="#batch[1]" />
									</td>
									<td>
										<s:date name="#batch[2]" format="yyyy-MM-dd" />
									</td>
									<td>
										<s:date name="#batch[3]" format="yyyy-MM-dd" />
									</td>
									<td>
										<s:property value="#batch[8]" />
									</td>
									<td>
										<s:if test="null==#batch[10]">
											-
										</s:if>
										<s:else>
											<s:if test="0==#batch[9]">
												<input type="button" onclick='toadd( "<s:property value="#batch[0]"/>","<s:property value="#st.index"/>")' value="去选课"></input>
												<input id="<s:property value="#batch[0]"/>" type=hidden value="<s:date name="#batch[3]" format="yyyy-MM-dd   23:59:59" />"></input>
												<input id="<s:property value="#st.index"/>" type="hidden" value="<s:date name="#batch[2]" format="yyyy-MM-dd HH:mm:ss" />"/>
											</s:if>
											<s:else>
												<span title="已生成订单，不允许选课操作">&nbsp;&nbsp;-&nbsp;&nbsp;</span>
											</s:else>
										</s:else>
									</td>
									<!--  td>
										<!--a href='/entity/workspaceStudent/studentWorkspace_trainview.action?id=<s:property value="#batch[0]"/>'>查看</a-->

									</td-->
									<td>
										<s:if test="null==#batch[9]">
											专项存在退费订单，请联系管理员重新添加课程
										</s:if>
										<s:else>
											<a href='/entity/workspaceStudent/studentWorkspace_searchViewBatchCourse.action?mybatchid=<s:property value="#batch[0]"/>'>查看</a> &nbsp;&nbsp;
											<s:if test="#batch[6]==1">
												已支付
											</s:if>
											<s:elseif test="#batch[6]==2&&#batch[7]!=null">
												<a style="cursor: pointer;" onclick="alert('请到【课程报名与付费】——&quot;我的订单&quot;中继续支付');">已提交，请支付</a>
											</s:elseif>
											<s:elseif test="#batch[6]==2">
												管理员支付，未到账
											</s:elseif>
											<s:else>
												<a onclick="window.open('/entity/workspaceStudent/studentWorkspace_paymentSpecialTraining.action?batchId=<s:property value="#batch[0]"/>&classHour=<s:property value="#batch[5]"/>', 'newwindow');" href="javascript:void(0);">去支付</a>
												<!-- 
												<s:else>
													<span>该专项存在退费订单，请联系管理员重新为您添加课程</span>								
												</s:else>
												 -->
											</s:else>
										</s:else>
									</td>
								</tr>
							</s:iterator>
						</s:if>
						<tr>
							<td colspan="7" align="left">
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
	</body>
	<script>
		function payment(id) {
			window.open('/entity/workspaceStudent/studentWorkspace_paymentSpecialTraining.action?batchId='+id,'newwindow');
		}
	</script>
</html>