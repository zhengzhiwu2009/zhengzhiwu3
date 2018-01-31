<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page import="java.util.*,java.text.*"%>
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
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" />
		<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
		<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
	</head>
	<script type="text/javascript">
Date.prototype.format = function(fmt)   
{ //author: meizz   
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
function toadd(){
	var ed = document.getElementById("endDate").value;
	var endDate = ed;
	var endDate_ = new Date(endDate.replace(/\-/g, "\/"));
	var now =  new Date().format("yyyy-MM-dd hh:mm:ss");
	var now_ = new Date(now.replace(/\-/g, "\/"));
	var pass = now_ - endDate_;
	if(pass < 0){
		window.location.href="<%=request.getContextPath()%>/entity/workspaceStudent/studentWorkspace_searchCourse.action?periodId=<s:property value="#request.periodId"/>" + "&endDate=" + ed;
	}else{
		alert("抱歉，选课期已结束，无法进行选课！");
	}
}
function retu(){
window.location.href="/entity/workspaceStudent/studentWorkspace_toElectiveCoursePeriod.action";
}
</script>
	<body>
		<form action="/entity/workspaceStudent/studentWorkspace_selectcourselist.action" method="post" name="frm" id="frm">
			<input type="hidden" id="endDate" name="endDate" value="<s:property value="#request.endDate" />" />
			<input type="hidden" name="startIndex" value="<s:property value="startIndex" />" />
			<input type="hidden" name="pageSize" value="100" />
			<input type="hidden" name="periodId" value="<s:property value="#request.periodId"/>" />
			<table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
				<tr valign=top height=30>
					<td align=left valign=bottom>
						<div class="main_title">
							已选课程列表
						</div>
					</td>
				</tr>
				<tr align="left">
					<td height="30">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="datalist" align="left">
							<tr bgcolor="#8dc6f6">
								<th width="8%" align="center">
									课程编号
								</th>
								<th width="20%" align="center">
									课程名称
								</th>
								<th width="8%" align="center">
									性质
								</th>
								<th width="12%" align="center">
									业务分类
								</th>
								<th width="12%" align="center">
									大纲分类
								</th>
								<th width="12%" align="center">
									内容属性
								</th>
								<th width="8%" align="center">
									课程学时
								</th>
								<th width="8%" align="center">
									价格(元)
								</th>
								<th width="8%" align="center">
									操作
								</th>
							</tr>
							<s:if test="page.items.size()!=0">
								<s:iterator value="page.items" id="ele">
									<tr height=30>
										<td align="center">
											<s:property value="#ele.prBzzTchOpencourse.peBzzTchCourse.code" />
										</td>
										<td align="left">
											<s:property value="#ele.prBzzTchOpencourse.peBzzTchCourse.name" />
										</td>
										<td align="center">
											<s:property value="#ele.prBzzTchOpencourse.peBzzTchCourse.enumConstByFlagCourseType.name" />
										</td>
										<td align="center">
											<s:property value="#ele.prBzzTchOpencourse.peBzzTchCourse.enumConstByFlagCourseCategory.name" />
										</td>
										<td align="center">
											<s:property value="#ele.prBzzTchOpencourse.peBzzTchCourse.enumConstByFlagCourseItemType.name" />
										</td>
										<td align="center">
											<s:property value="#ele.prBzzTchOpencourse.peBzzTchCourse.enumConstByFlagContentProperty.name" />
										</td>
										<td align="center">
											<s:property value="#ele.prBzzTchOpencourse.peBzzTchCourse.time" />
										</td>
										<td align="center">
											<s:property value="#ele.prBzzTchOpencourse.peBzzTchCourse.price" />
										</td>
										<td align="center">
											<s:if test="#request.isRightDate == 1">
												<a onclick="delCourse('<s:property value="#ele.id"/>','<s:property value="#request.periodId"/>');"><font
													color="red">删除</font> </a>
											</s:if>
											<s:else>-</s:else>
										</td>
									</tr>
								</s:iterator>
							</s:if>
							<s:else>
								<tr height=30>
									<td width="5%" align="center" colspan="9">
										<div>
											<s:if test="#request.isRightDate == 1">
										您还未选任何课程，现在要去
											<a href="/entity/workspaceStudent/studentWorkspace_searchCourse.action?periodId=<s:property value="#request.periodId"/>&endDate=<s:property value="#request.endDate"/>"><font style="font-size:larger">选课</font></a>
											吗？
										</s:if>
											<s:else>当前不在选课期选课时间内，不能选课</s:else>

										</div>
									</td>
								</tr>
							</s:else>
							<tr>
								<td colspan="9" align="left" style="font_size:30px">
									<font color="red" >
										本页课程总金额：<s:property value="#request.pageMoneyHourSum" />(元)
										已选课程总金额：<s:property value="#request.moneySum" />(元)
										本页课程学时总数：<s:property value="#request.pageClassHourSum" />
										所选课程学时总数：<s:property value="#request.classHourSum" />
										必修课程学时总数：<s:property value="#request.bixiu" />
										选修课程学时总数：<s:property value="#request.xuanxiu" />
									</font>
								</td>
							</tr>
							<tr height=30>
								<td width="" align="center" colspan="9">
									<div id="fany">
										<script>
											var page1 = new showPages("page1",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
											page1.printHtml();
										</script>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="9" width=20% align=center>
									<input type=button value="继续选课" onclick=toadd()
										<s:if test="#request.isRightDate == 1"></s:if>
										<s:else>style="display:none;"</s:else> />
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type=button value="返  回" onclick=retu() />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
	<script>
		function delCourse(eleId, periodId) {
			var ed = document.getElementById("endDate").value;			 
			var endDate = ed;
			var endDate_ = new Date(endDate.replace(/\-/g, "\/"));
			var now =  new Date().format("yyyy-MM-dd 00:00:00");
			var now_ = new Date(now.replace(/\-/g, "\/"));
			var pass = now_ - endDate_;
			if(pass < 0){
				if(confirm('您确定要删除此门课程吗？')) {
					window.location.href='/entity/workspaceStudent/studentWorkspace_deletePeriodCourse.action?eleId='+eleId+'&periodId='+periodId+'&endDate='+ed;
				}
			}else{
				alert("抱歉，选课期已结束，无法进行选课！");
			}
		}
	</script>
</html>
