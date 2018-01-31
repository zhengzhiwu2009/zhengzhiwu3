<%@ page language="java" import="java.util.*,com.whaty.platform.entity.bean.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String d=String.valueOf(Math.random());
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
<link href="/entity/manager/images/css_welcome.css" rel="stylesheet" type="text/css" />
<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
<script type="text/JavaScript">
<!--
var newWindow = "";

function MM_preloadImages() { //v3.0
var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}


function selectServer() {
window.open ('<%=basePath%>training/student/course/serverSelect.jsp', 'selectserver', 
'height=600, width=800, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
}
//-->
/*
$(document).ready(function() {
$(".sub").change(
function() {
document.forms.frm.startIndex.value= 0;
document.forms.frm.submit();
});
});**/
function searSubmit(){
document.forms.frm.startIndex.value= 0;
document.forms.frm.submit();
}

function openCourseWare(url,target){
if(!newWindow || newWindow.closed){
newWindow = window.open(url,target);
}else{
alert('请先关闭当前学习的课程');
}

}
function resetContent(){
var content=document.getElementsByTagName("input");
document.forms.frm.reset();
for(var i=0;i<content.length;i++){
if(content[i].type=="text"){
content[i].value="";
}
}
//	searSubmit();
}

</script>
</head>
	<body>
		<form action="/entity/workspaceRegStudent/regStudentWorkspace_toFreeCourses.action" name="frm" method="post">
		<input type="hidden" name="startIndex" value="<s:property value="startIndex" />" />
		<input type="hidden" name="pageSize" value="<s:property value="pageSize" />" />
			<div class="" style="width:100%;padding:0;margin:0; " align="center">
				<div class="main_title">课程报名</div>
				<div class="msg" style="height:auto;">
					<table border="0">
						<tr>
							<td>
								<b>1、点击“开始学习”进行课程学习，点击“预览”进行课程浏览”</b>
							</td>
						</tr>
						<tr>
							<td>
								<b>2、已点击“开始学习”的课程将自动转至【课程学习】列表中，【课程报名】页面将中不再显示</b>
							</td>
						</tr>
						<tr>
							<td>
								<b>3、学员首次退出某门课程学习后，请到【课程学习】页面中继续学习该门课程</b>
							</td>
						</tr>
					</table>
				</div>
				<div>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="33%" align=left>课程编号：
								<input type="text" name="courseCode" class="sub" value="<s:property value="courseCode" />" size="11" />
							</td>
							<td width="33%" align=left>课程名称：
								<input type="text" name="courseName" class="sub" value="<s:property value="courseName" />" size="13" />
							</td> 
							<td align="left">课程性质：
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
						</tr> 
						<tr>
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
							<td align="left">大纲分类：
								<select name="courseItemType" class="sub">
									<option value="" selected>全部 </option>
									<s:iterator value="courseItemTypeList" id="courseItemType">
										<s:if test="#courseItemType.id == courseItemType">
											<option value="<s:property value="#courseItemType.id" />" selected>
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
											<option value="<s:property value="#courseContent.id" />" selected>
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
						</tr>
						<tr>
							<td align="left">
								课程学时：<input type="text" name="time" class="sub" value="<s:property value="time" />" size="11" />
							</td>
							<td align="left">
								主讲人：<input type="text" name="teacher" class="sub" value="<s:property value="teacher" />" size="20" />
							</td>
							<td align="left">
								所属区域：
								<select name="coursearea" class="sub">
									<option value="" selected>
										全部
									</option>
									<s:iterator value="courseareaList" id="type">
										<s:if test="#type.id != 'Coursearea0' && #type.id != 'Coursearea3' && #type.id != 'Coursearea4'">
											<s:if test="#type.id == coursearea">
												<option selected value="<s:property value="#type.id" />">
													<s:property value="#type.name" />
												</option>
											</s:if>
											<s:else>
												<option value="<s:property value="#type.id" />">
													<s:property value="#type.name" />
												</option>
											</s:else>
										</s:if>
									</s:iterator>
								</select>
							</td>
						</tr>
						<tr>
							<td align="left" colspan="2">
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
							</td>
							<td align="right">
								<input type="button" value="&nbsp;查&nbsp;找&nbsp;"   onclick="searSubmit();"/>
								<input type="button" value="&nbsp;清&nbsp;空&nbsp;"   onclick="resetContent();"/>
							</td>
						</tr>
					</table>
					<table class="datalist" width="100%">
						<tr>
							<td colspan="10" align="left"> 
								<div id="fany">
									<script language="JavaScript"  >
										<s:if test="page != null">
											var page2 = new showPages("page2",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
										</s:if>
										<s:else>
											var page2 = new showPages("page2",<s:property value="startIndex" />,<s:property value="pageSize" />,0,"");
										</s:else>
										page2.printHtml();
									</script>
								</div>
							</td>
						</tr>
						<tr bgcolor="#8dc6f6">
							<th width="10%">课程编号</th>
							<th>课程名称</th>
							<th width="10%">课程性质</th>
							<th width="10%">业务分类</th>
							<th width="10%">大纲分类</th>
							<th width="10%">内容属性</th>
							<th width="10%">主讲教师</th>
							<th width="6%">学时</th>
							<th width="6%">预览</th>
							<th width="10%">进入学习</th>
							<!-- th width="10%">学习记录</th-->
						</tr>
						<s:if test="page != null">
							<s:iterator value="page.items" id="opencourse">
								<tr>
									<td><s:property value="#opencourse.peBzzTchCourse.code" /></td>
									<td align="center">
										<a style = "color:#3366ff; cursor: pointer;" onclick="window.open('/cms/flkcalone.htm?myId=<s:property value="#opencourse.peBzzTchCourse.id"/>','newWindow_detail')" >
											<s:property value="#opencourse.peBzzTchCourse.name" />
										</a>
									</td>
									<td><s:property value="#opencourse.peBzzTchCourse.enumConstByFlagCourseType.name" /></td>
									<td><s:property value="#opencourse.peBzzTchCourse.enumConstByFlagCourseCategory.name" /></td>
									<td><s:property value="#opencourse.peBzzTchCourse.enumConstByFlagCourseItemType.name" /></td>
									<td><s:property value="#opencourse.peBzzTchCourse.enumConstByFlagContentProperty.name" /></td>
									<td><a style = "color:#3366ff; cursor: pointer;" title="<s:property value="#opencourse.peBzzTchCourse.teacher"/>" onclick="window.open('/entity/workspaceStudent/studentWorkspace_InteractionStuManage.action?course_id=<s:property value="#opencourse.peBzzTchCourse.id" />','newwindow_teacher')"/>
								<s:if test="#opencourse.peBzzTchCourse.teacher.length()>3"><s:property value="#opencourse.peBzzTchCourse.teacher.substring(0,3)" />...</s:if>
								<s:else><s:property value="#opencourse.peBzzTchCourse.teacher" /></s:else></td>
									<td><s:if test='#opencourse.peBzzTchCourse.time=="0.0"' >0</s:if><s:else><s:property value="#opencourse.peBzzTchCourse.time" /></s:else></td>
									<td><a href="/entity/workspaceRegStudent/regStudentWorkspace_view.action?course_id=<s:property value="#opencourse.peBzzTchCourse.id"/>" target="_blank">预览</a></td>
									<td>
										<div class="ck" style="width:70px;background-image:url('/entity/bzz-students/images/index_21.jpg')" >
											<a style="cursor: pointer;" onclick="openCourseWare('/entity/workspaceRegStudent/regStudentWorkspace_InteractionStuManage.action?course_id=<s:property value="#opencourse.peBzzTchCourse.id"/>','newWindow');" >开始学习</a>
										</div>
									</td>
								<!--td><div class="ck" style="width:70px;background-image:url('/entity/bzz-students/images/index_23.jpg')" ><a href="/training/student/course/jumpCoursewareStatus.jsp?courseId=<s:property value="#opencourse.peBzzTchCourse.id"/>&opencourseId=<s:property value="#opencourse.id"/>" target="_blank" >查看</a></div></td--> 
								</tr>
							</s:iterator>
							<tr style="display:none;">
								<td colspan="10" align="left">课程学时总数：<s:property value="#request.classHour_all"/></td>
							</tr>
						</s:if>
						<s:else>
							<tr>
								<td colspan="10">
									<font size="3" color="red"><strong>免费课程需要先学习收费课程才能学习</strong></font>
								</td>
							</tr>		
						</s:else>
						<tr>
							<td colspan="10" align="left"> 
								<div id="fany">
									<script language="JavaScript"  >
										<s:if test="page != null">
											var page1 = new showPages("page1",<s:property value="startIndex" />,<s:property value="pageSize" />,<s:property value="page.totalCount" />,"");
										</s:if>
										<s:else>
											var page1 = new showPages("page1",<s:property value="startIndex" />,<s:property value="pageSize" />,0,"");
										</s:else>
										page1.printHtml();
									</script>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>
	</body>
</html>