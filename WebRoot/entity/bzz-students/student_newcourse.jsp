<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.whaty.platform.test.TestManage"%>
<%@page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
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
		<base href="<%=basePath%>"/>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css.css" rel="stylesheet"
			type="text/css" />
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet"
			type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet"
			type="text/css" />
		<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
		<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
		<script language="JavaScript">
function addToOrder(id){
	  var url = '/entity/workspaceStudent/studentWorkspace_addCourseToOrder.action';
      var params = {electiveId:id};
      jQuery.post(url, params, callbackFun, 'json');
}
function test(id,passrole){
	 var message = "（1）您可以通过‘课前测验’了解或评估您对所选课程知识的掌握程度。\n（2）‘课前测验’结果仅供您选课时参考使用。\n（3）无论测验结果是否通过，均不影响您正常选课报名及学时获得。";
	 if(confirm(message)){
		  var url = '/sso/bzzinteraction_getTestScore.action';
	      var params = {course_id:id,passrole:passrole};
	      jQuery.post(url, params, getScore, 'json');
      }
	
}
function getScore(data){
		  if(data.score !=""){
		  	var message = '您已进行了该门课程的"课前测验"，上次您的测验得分为' + data.score + '分。';
		  	if(confirm(message)){
		      var url = '/sso/bzzinteraction_InteractionPrivTest.action';
		      var params = {course_id:data.course_id,passrole:data.passrole};
		      jQuery.post(url, params, opentest, 'json');
	     	 }
		  }else{
		      var url = '/sso/bzzinteraction_InteractionPrivTest.action';
		       var params = {course_id:data.course_id,passrole:data.passrole};
		      jQuery.post(url, params, opentest, 'json');
	      }
}
function opentest(data){
	document.getElementById("openA").href = 'entity/function/testpaper/privtestcourse_session.jsp?pbtc=&testCourseId='+data.testCourseId + '&isAutoCheck=true&isHiddenAnswer=false&pageInt=1';
	document.getElementById("openA").click();
    //window.open('entity/function/testpaper/privtestcourse_session.jsp?pbtc=&testCourseId='+data.testCourseId + '&isAutoCheck=true&isHiddenAnswer=false&pageInt=1');
}
function callbackFun(data){
      alert(data.success);
      document.forms.frm.submit();
}
function searSubmit(){
	document.forms.frm.startIndex.value= 0;
	document.forms.frm.submit();
}
function resetContent(){
	var content=document.getElementsByTagName("input");
	document.forms.frm.reset();
	for(var i=0;i<content.length;i++){
		if(content[i].type=="text"){
				content[i].value="";
		}
	}
}
</script>
	</head>
	<body>
		<form
			action="/entity/workspaceStudent/studentWorkspace_tonewcourse.action"
			name="frm" method="post">
			<input type="hidden" name="startIndex"
				value="<s:property value="startIndex" />" />
			<input type="hidden" name="pageSize"
				value="<s:property value="pageSize" />" />
			<div class="" style="width: 100%; padding: 0; margin: 0;"
				align="center">
				<div class="main_title">
					公共课程报名
				</div>
				<div class="msg" width="98%" style="height: 110px;">
					<!--  <img src="/entity/bzz-students/images/index_04.jpg"
						align="absmiddle" />
					您报名的课程的必修总学时：
					<span style="font-size: 20px"><s:property
							value="#request.biNum" /> </span>学时&nbsp;&nbsp;&nbsp;选修总学时：
					<span style="font-size: 20px"><s:property
							value="#request.xuanNum" /> </span>学时&nbsp;&nbsp;&nbsp;价格小计：
					<span style="font-size: 20px"><s:property
							value="#request.totalMoney" /> </span>元
							-->
					
					<span
						style="float: left; color: red; font-weight: bold; font-size: 12px;">提示：免费课程无需报名，学员可在“免费公共课程报名”菜单中直接进行学习。
					<br>
					根据财务要求，小于1000元的订单（包含合并订单）无法开具增值税专用发票。学员进行个人报名时请考虑上述要求，如个人订单无法达到1000元，请集体管理员统一报名并申请发票。
						
					<input
							type="button" style="font-size: 18px;"
							onclick="window.location.href='/entity/workspaceStudent/studentWorkspace_viewElectiveOrder.action'"
							value="查看选课单" />
					</span>
				</div>
				<div class="main_txt">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="30%" align="left">
								课程编号：
								<input type="text" name="courseCode" class="sub"
									value="<s:property value="courseCode" />" size="11" />
							</td>
							<td width="30%" align="left">
								课程名称：
								<input type="text" name="courseName" class="sub"
									value="<s:property value="courseName" />" size="13" />
							</td>
							<td align="left" width="40%">
								大纲分类：
								<select name="courseItemType" class="sub">
									<option value="" selected>
										全部
									</option>
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
						</tr>
						<tr>
							<td width="30%" align="left">
								课程学时：
								<input type="text" name="time" class="sub"
									value="<s:property value="time" />" size="11" />
							</td>
							<td width="30%" align="left">
								课程价格：
								<input type="text" name="tchprice" class="sub"
									value="<s:property value="tchprice" />" />
							</td>
							<td align="left" width="40%">
								课程性质：
								<select name="courseType" class="sub">
									<option value="" selected>
										全部课程
									</option>
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
						</tr>
						<tr>
							<!-- Lee start 2014年03月12日 添加内容属性查询条件-->
							<td align="left" width="30%">
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
							<!-- Lee end -->
							<td width="30%" align="left">
								主&nbsp;&nbsp;讲&nbsp;&nbsp;人：
								<input type="text" name="teacher" class="sub"
									value="<s:property value="teacher" />" size="20" />
							</td>
							<td align="left" width="40%">
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
							<td align="left" colspan="3" >
								按建议学习人群：
								<select name="suggestren" class="sub">
									<option value="" selected>
										全部
									</option>
									<s:iterator value="suggestrenList" id="type">
										<s:if test="#type.name == suggestren">
											<option value="<s:property value="#type.name" />" selected>
												<s:property value="#type.name" />
											</option>
										</s:if>
										<s:else>
											<option value="<s:property value="#type.name" />">
												<s:property value="#type.name" />
											</option>
										</s:else>
									</s:iterator>
								</select>
								&nbsp;&nbsp;&nbsp;按系列课程：


								<select name="seriseCourse" class="sub">
									<option value="" selected>
										全部
									</option>
									<s:iterator value="seriesList" id="type">
										<s:if test="#type[0] == seriseCourse">
											<option value="<s:property value="#type[0]" />" selected>
												<s:property value="#type[1]" />
											</option>
										</s:if>
										<s:else>
											<option value="<s:property value="#type[0]" />">
												<s:property value="#type[1]" />
											</option>
										</s:else>
									</s:iterator>
								</select>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" value="&nbsp;查&nbsp;找&nbsp;"
									onclick="searSubmit();" />
								<input type="button" value="&nbsp;清&nbsp;空&nbsp;"
									onclick="resetContent()" />
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
							<!--<th><input type="checkbox"  /></th>-->
							<th>
								课程编号
							</th>
							<th>
								课程名称
							</th>
							<th>
								课程性质
							</th>
							<th>
								业务分类
							</th>
							<th>
								大纲分类
							</th>
							<th>
								内容属性
							</th>
							<th>
								主讲人
							</th>
							<th>
								学时
							</th>
							<th>
								期限
							</th>
							<th>
								价格(元)
							</th>
							<th width="11%">

								课前测验
								（可选操作）
							</th>							
							<th>
								操作
							</th>
						</tr>
						<s:if test="page != null">
							<s:iterator value="page.items" id="opencourse">
								<tr>
									<td align="center">
										<s:property value="#opencourse[2]" />
									</td>
									<td align="center">
										<a style="color: #3366ff; cursor: pointer;"
											onclick="window.open('/cms/flkcalone.htm?myId=<s:property value="#opencourse[1]"/>&flag=true','newwindow_detail')">
											<s:property value="#opencourse[3]" /></a>
									</td>
									<td align="center">
										<s:property value="#opencourse[4]" />
									</td>
									<td align="center">
										<s:property value="#opencourse[5]" />
									</td>
									<td align="center">
										<s:property value="#opencourse[7]" />
									</td>
									<td align="center">
										<s:property value="#opencourse[9]" />
									</td>
									<td align="center">
										<s:property
											value="(#opencourse[6]==null || '' == #opencourse[6])?'-':#opencourse[6]" />
									</td>
									<td align="center">
										<s:property value="#opencourse[8]" />
									</td>
									<td align="center">
										<s:property value="#opencourse[10] + '天'" default="未设置" />
									</td>
									<td align="center">
										<s:property value="#opencourse[11]" />
									</td>
									<td align="center">
										 <s:if test="#opencourse[12] == '1'.toString()">
										<div class="ck"
											style="width: 70px; background-image: url('/entity/bzz-students/images/index_22.jpg')">
											<a style="cursor: pointer;" onclick="test('<s:property value="#opencourse[1]" />','<s:property value="#opencourse[13]" />');">课前测验</a>
											<a target="_blank" id="openA" onlick=""></a>
										</div>
										</s:if>
										<s:else>
										-
										</s:else>
									</td>									
									<td align="center">
										<div class="ck"
											style="width: 70px; background-image: url('/entity/bzz-students/images/index_22.jpg')">
											<a style="cursor: pointer;"
												onclick="addToOrder('<s:property value="#opencourse[0]" />');">加入选课单</a>
										</div>
									</td>

								</tr>
							</s:iterator>
						</s:if>
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
</html>