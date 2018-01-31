<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String d = String.valueOf(Math.random());
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
		<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
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
			function addToOrder(id){
				var ed = document.getElementById("endDate").value;
				var endDate = ed;
				var endDate_ = new Date(endDate.replace(/\-/g, "\/"));
				var now =  new Date().format("yyyy-MM-dd hh:mm:ss");
				var now_ = new Date(now.replace(/\-/g, "\/"));
				var pass = now_ - endDate_;
				if(pass < 0){
					var url='/entity/workspaceStudent/studentWorkspace_addCourseToPeriod.action?periodId=<s:property value="#session.periodId"/>' + "&endDate=" + ed + "&opId=" + id;
					// var url = '/entity/workspaceStudent/studentWorkspace_addCourseToOrder.action';
					window.location.href = url;
					/*
					var params = {opId:id,endDate:ed};
					jQuery.post(url, params, callbackFun, 'json');
					*/
				}else{
					alert("抱歉，选课期已结束，无法进行选课！");
				}
			}
			/*
			function callbackFun(data){
				alert(data.success);
				document.forms.form.submit();
			}*/
</script>
	</head>
	<body>
		<form id="frm2" action="/entity/workspaceStudent/studentWorkspace_searchCourse.action" name="form" method="post">
			<input id="endDate" name="endDate" type="hidden" value="<s:property value="#session.endDate" />" />
			<input type="hidden" name="startIndex" value="<s:property value="startIndex" />" />
			<input type="hidden" name="pageSize" value="<s:property value="pageSize" />" />   
			<input type="hidden" name="periodId" value="<s:property value="#session.periodId"/>" />
		<div class="main_title">选课期选课</div>
		<div class="main_txt">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="20%" align="left">课程编号：
						<input type="text" name="courseCode" class="sub" value="<s:property value="courseCode" />" size="11" />
					</td>
					<td width="25%" align="left">课程名称：
						<input type="text" name="courseName" class="sub" value="<s:property value="courseName" />" size="11" />
					</td>
					<td width="20%" align="left">
						课程性质：
						<select name="courseType" class="sub">
							<option value="" selected>全部课程</option>
							<s:iterator value="courseTypeList" id="type">
								<s:if test="#type.id == courseType">
									<option value="<s:property value="#type.id" />" selected>
										<s:property value="#type.name" />
									</option>
								</s:if>
								<s:else>
									<option value="<s:property value="#type.id" />">
										<s:property value="#type.name" />
									</option>
								</s:else>
							</s:iterator>
						</select>
					</td>
					<td align="left">
						业务分类：
						<select name="courseCategory" class="sub">
							<option value="" selected>
								全部
							</option>
							<s:iterator value="courseCategoryList" id="category">
								<s:if test="#category.id == courseCategory">
									<option value="<s:property value="#category.id" />" selected>
										<s:property value="#category.name" />
									</option>
								</s:if>
								<s:else>
									<option value="<s:property value="#category.id" />">
										<s:property value="#category.name" />
									</option>
								</s:else>
							</s:iterator>
						</select>
					</td>
				</tr>
				<tr>
					<td width="25%" nowarp="true" align="left">大纲分类：
						<select name="courseItemType" class="sub">
							<option value="" selected>全部 </option>
							<s:iterator value="courseItemTypeList" id="courseItemType">
								<s:if test="#courseItemType.id == courseItemType">
									<option value="<s:property value="#courseItemType.id" />"
										selected>
										<s:property value="#courseItemType.name" />
									</option>
								</s:if>
								<s:else>
									<option value="<s:property value="#courseItemType.id" />">
										<s:property value="#courseItemType.name" />
									</option>
								</s:else>
							</s:iterator>
						</select>
					</td>
					<td align="left">
						按内容属性：
						<select name="courseContent" class="sub">
							<option value="" selected>
								全部
							</option>
							<s:iterator value="courseContentList" id="courseContent">
								<s:if test="#courseContent.id == courseContent">
									<option value="<s:property value="#courseContent.id" />"
										selected>
										<s:property value="#courseContent.name" />
									</option>
								</s:if>
								<s:else>
									<option value="<s:property value="#courseContent.id" />">
										<s:property value="#courseContent.name" />
									</option>
								</s:else>
							</s:iterator>
						</select>
					</td>
					<td  align="left">主讲人：
						<input type="text" name="teacher" class="sub" value="<s:property value="teacher" />" size="11" />
					</td>
					<td align="right" colspan="3">
						<input type="submit" value="&nbsp;查&nbsp;找&nbsp;"/>
						<input type="button" value="&nbsp;清&nbsp;空&nbsp;" onclick="resetContent()" />
					</td>
				</tr>
			</table>
			<table class="datalist" width="100%">
				<tr bgcolor="#8dc6f6">
					<td width="5%" align="center">
						课程编号
					</td>
					<td width="20%" align="center">
						课程名称
					</td>
					<td width="5%" align="center">
						课程性质
					</td>
					<td width="15%" align="center">
						业务分类
					</td>
					<td width="12%" align="center">
						大纲分类
					</td>
					<td width="12%" align="center">
						内容属性
					</td>
					<td width="8%" align="center">
						主讲人
					</td>
					<td width="5%" align="center">
						学时
					</td>
					<td width="5%" align="center">
						期限
					</td>
					<td width="5%" align="center">
						价格
					</td>
					<td width="5%" align="center">
						操作
					</td>
				</tr>
				<s:iterator value="page.items" id="cp" status="st">
					<tr>
						<td width="5%" align="center">
							<s:property value="#cp[2]" />
						</td>
						<td width="8%" align="center" style="display: none;">
							<s:property value="#cp[2]" />
						</td>
						<td width="8%" align="left">
							<a style="color: #3366ff; cursor: pointer;"
								onclick="window.open('/entity/function/coursenotequery.jsp?course_id=<s:property value="#cp[1]"/>','newwindow_detail')">

								<s:property value="#cp[3]" /> </a>
						</td>
						<td width="5%" align="center">
							<s:property value="#cp[4]" />
						</td>
						<td align="center">
							<s:property value="#cp[5]" />
						</td>
						<td align="center">
							<s:property value="#cp[7]" />
						</td>
						<td align="center">
							<s:property value="#cp[9]" />
						</td>
						<td width="5%" align="center">
							<s:property value="#cp[6]" />
						</td>
						<td width="5%" align="center">
							<s:property value="#cp[8]" />
						</td>
						<!-- td width="5%" align="center">
							<a style="color: #3366ff; cursor: pointer;"
								onclick="window.open('/entity/workspaceStudent/studentWorkspace_InteractionStuManage.action?course_id=<s:property value="#cp[1]" />','newwindow_teacher')"><s:property
									value="#cp[8]" /> </a>

						</td -->
						<td width="5%" align="center">
							<s:property value="#cp[10]" />
						</td>  
						<td width="5%" align="center">
							<s:property value="#cp[11]" />
						</td>  
						<!-- td width="5%" align="center">
							<s:property value="coursePrice(#cp[11], #request.price)" />
						</td--->
						<td width="5%" align="center">
							<!-- <a href="/entity/workspaceStudent/studentWorkspace_addCourseToPeriod.action?periodId=<s:property value="#session.periodId"/>&opId=<s:property value="#cp[0]" />" onclick="return info()" >添加</a>  -->
							<a style="color:red;cursor: pointer;" onclick="addToOrder('<s:property value="#cp[0]" />')">添加</a>
						</td>
					</tr>
				</s:iterator>
				<tr>
					<td colspan="11" align="left" style="font_size:30px">
						<font color="red" >
							已选课程总金额：<s:property value="#request.moneySum" />(元)
							课程总金额上限:<s:property value="#request.amountUpLine"/>(元)
							学时数下限(仅供参考,不做系统校验):<s:property value="#request.compulsoryHour"/>
							学时数上限:<s:property value="#request.periodTime"/>
							已选课程学时总数：<s:property value="#request.classHourSum" />
							必修课程学时下限:<s:property value="#request.totalHour"/>
							已选必修课程学时总数：<s:property value="#request.bixiu" />
							已选选修课程学时总数：<s:property value="#request.xuanxiu" />
						</font>
					</td>
				</tr>
				<tr>
					<td colspan=11>
						<div id="fany">
							<script>
								var page1 = new showPages("page1",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
								page1.printHtml();
							</script>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan=11 align='center'>
						<input type="button" onclick=retu() value="返回已选列表" />
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
	<script>
	function resetContent(){
		var content=document.getElementsByTagName("input");
		document.forms.frm2.reset();
		for(var i=0;i<content.length;i++){
			if(content[i].type=="text"){
				content[i].value="";
			}
		}
	}
		function info(){
			if (confirm("您确定要选择这门课程吗")){
			
			return true ;
			}else{
			return false ;
			} 
		}
		
		var x = 0;
		function addCourse(opId) {
			if(x>0) return;
			x++;
			var endDate = document.getElementById("endDate").value;
			window.location.href="/entity/workspaceStudent/studentWorkspace_addCourseToPeriod.action?periodId=<s:property value="#session.periodId"/>&opId="+opId+"&endDate=" + endDate;
		}
		
		function retu() {
			var endDate = document.getElementById("endDate").value;
			window.location.href="/entity/workspaceStudent/studentWorkspace_selectcourselist.action?periodId=<s:property value="#session.periodId"/>" + "&endDate=" + endDate;
		}
	</script>

</html>