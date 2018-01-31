<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中国证劵业协会培训平台</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" /> 
<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" /> 
<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
<script language="JavaScript">
function searSubmit(){
	document.forms.frm.startIndex.value= 0;
	document.forms.frm.action="/entity/workspaceStudent/studentWorkspace_viewElectiveOrder.action";
	document.forms.frm.submit();
}
function tonewcourse(){
	document.forms.frm.startIndex.value= 0;
	document.forms.frm.action="/entity/workspaceStudent/studentWorkspace_tonewcourse.action";
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
function tishi(){
	var msg ='<s:property value="msg" />'
	if (msg !=null && msg !=""){
		alert(msg);
		}
	}

</script>
</head>
<body onload ="tishi()">
<form action="/entity/workspaceStudent/studentWorkspace_tonewcourse.action" name="frm" method="post"">
	<input type="hidden" name="startIndex" value="<s:property value="startIndex" />" />
	<input type="hidden" name="pageSize" value="<s:property value="pageSize" />" />
	<input type="hidden" name="opids" id="opids" value="" />
	<div class="" style="width:100%;padding:0;margin:0; " align="center">
    <div class="main_title">选课单</div>
    <div class="msg" style="height: 110px;" width="98%"><img src="/entity/bzz-students/images/index_04.jpg" align="absmiddle" />您报名的课程的必修总学时:<span><s:property value="#request.biNum"/></span>学时&nbsp;&nbsp;&nbsp;选修总学时： <span><s:property value="#request.xuanNum"/></span>学时&nbsp;&nbsp;&nbsp;价格小计：<span><s:property value="#request.totalMoney"/></span>元
    <br>
    根据财务要求，小于1000元的订单（包含合并订单）无法开具增值税专用发票。学员进行个人报名时请考虑上述要求，如个人订单无法达到1000元，请集体管理员统一报名并申请发票。
    
    </div>
    
    <div class="main_txt">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="40%" align="left">
				课程编号：<input type="text" name="courseCode" class="sub" value="<s:property value="courseCode" />" size="11" />
			</td>
			<td width="30%" align="left">
				课程名称：<input type="text" name="courseName" class="sub" value="<s:property value="courseName" />" size="13" />
			</td>
			<td width="30%" align="left">
				课程学时：<input type="text" name="time" class="sub" value="<s:property value="time" />" size="11" />
			</td>
		</tr>
		<tr>
			<td width="40%" align="left">
				课程价格：<input type="text" name="tchprice" class="sub" value="<s:property value="tchprice" />" size="3" />
			</td>
			<td align="left" width="30%" >大纲分类：
					<select name="courseItemType" class="sub">
						<option value="" selected>全部</option>
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
			<td align="left" width="30%" >课程性质：
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
		<td align="left" width="40%" >业务分类：
				<select name="courseCategory" class="sub">
					<option value="" selected>全部</option>
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
			<td align="left" width="30%" >按内容属性：
				<select name="courseContent" class="sub">
					<option value="" selected>全部</option>
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
			<td width="30%" align="left">
				主&nbsp;&nbsp;讲&nbsp;&nbsp;人：<input type="text" name="teacher" class="sub" value="<s:property value="teacher" />" size="20" />
			</td>
		</tr>
		<tr>
			<td align="right" colspan="3">
				<input type="button" value="&nbsp;查&nbsp;找&nbsp;" onclick="searSubmit();" />
				<input type="button" value="&nbsp;清&nbsp;空&nbsp;" onclick="resetContent()" />
			</td>
		</tr>
	</table>
	<table class="datalist" width="100%">
	  <tr bgcolor="#8dc6f6">
	  <!-- 	<th><input type='checkbox' name="checkAll" id="checkAll" onclick="selectAll();"></th> -->
	    <th width="">课程编号</th>
	    <th>课程名称</th>
	    <th>课程性质</th>
	    <th>业务分类</th>
	    <th>大纲分类</th>
	    <th>内容属性</th>
	    <th>主讲人</th>
	    <th>学时</th>
	    <th>期限</th>
	    <th>价格(元)</th>
	    <th>操作</th>
	  </tr>
	  <s:if test="opencourseList != null">
	  	<s:iterator value="opencourseList" id="opencourse">
		  <tr>
			  	<td ><s:property value="#opencourse.peBzzTchCourse.code" /></td>
			    <td align="left"><s:property value="#opencourse.peBzzTchCourse.name" /></td>
			    <td ><s:property value="#opencourse.peBzzTchCourse.enumConstByFlagCourseType.name" /></td>
			    <td ><s:property value="#opencourse.peBzzTchCourse.enumConstByFlagCourseCategory.name" /></td>
			    <td ><s:property value="#opencourse.peBzzTchCourse.enumConstByFlagCourseItemType.name" /></td>
			    <td ><s:property value="#opencourse.peBzzTchCourse.enumConstByFlagContentProperty.name" /></td>
			    <td ><s:property value="#opencourse.peBzzTchCourse.teacher" /></td>
			    <td><s:property value="#opencourse.peBzzTchCourse.time" /></td>
			    <td ><s:property value="#opencourse.peBzzTchCourse.studyDates" /></td>
			    <td ><s:property value="#opencourse.peBzzTchCourse.price"/></td>
			    <td><div class="ck" style="width:70px;background-image:url('/entity/bzz-students/images/index_21.jpg')" ><a href="###" onclick="is_Delete('<s:property value="#opencourse.id" />');">删除课程</a></div></td>
		  </tr>
	  	</s:iterator>
	  	<tr>
	    	<td colspan="11" align="left">课程学时总计：<s:property value="#request.t"/></td>
	    </tr>
	  <tr align="center">
	    <td colspan="11" > 
		    <table border="0" cellpadding="0" cellspacing="0" width="100%">
		    	<tr align="center">
			    	<td style="border:none;" width="33%">
			    		<div class="ck" style="width:70px;background-image:url('/entity/bzz-students/images/index_21.jpg')" ><a href="/entity/workspaceStudent/studentWorkspace_tonewcourse.action" >继续选课</a></div>
			    	</td>
			    	<td style="border:none;" width="33%">
			    		<div class="ck" style="width:70px;background-image:url('/entity/bzz-students/images/index_21.jpg')" ><a onclick="window.open('/entity/workspaceStudent/studentWorkspace_paymentCourse.action', 'to_pay')" href="###" >立即支付</a></div>
			    	</td>
			    	<td style="border:none;" width="33%">
			    		<div class="ck" style="width:70px;background-image:url('/entity/bzz-students/images/index_21.jpg')" ><a href="/entity/workspaceStudent/studentWorkspace_tonewcourse.action">返回列表</a></div>
			    	</td>
		    	</tr>
		    </table>
	   	</td>
	   </tr>
	     </s:if>
	    <s:else>
	    	 <tr align="center">
	    	 	<td colspan="11"> 
	    	 		您的选课单中没有课程
	    	 	</td>
	    	 </tr>
	    	 <tr align="center">
	    	 	<td colspan="11">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
					    <tr align="center">
					    	<td  style="border:none;" width="50%">
				    	 		<div class="ck" style="width:70px;background-image:url('/entity/bzz-students/images/index_21.jpg')" ><a href="/entity/workspaceStudent/studentWorkspace_tonewcourse.action">立即选课</a></div>
	    	 				</td>
	    	 				<td  style="border:none;" width="50%">
				    	 		<div class="ck" style="width:70px;background-image:url('/entity/bzz-students/images/index_21.jpg')" ><a href="javascript:history.go(-1)">返回列表</a></div>
			    	 		</td>
	    	 			</tr>
	    	 		</table>
	    	 	</td>
	    	 </tr>
	    </s:else>
	</table>
<br />
</div>
</div>
</form>
</body>
<script language="JavaScript">
	function selectAll() {
		var c = document.getElementById('checkAll');
		var tform=document.forms['frm']; 
		for(var i=0; i<tform.length; i++) {
			var e=tform.elements[i]; 
			if(e.type=='checkbox') {
				if(c.checked==true) {
					e.checked = true;
				} else {
					e.checked = false;
				}
			}
		}
	}
	
	function quickSubmit() {
		var tform=document.forms['frm'];
		var opids = '';
		for(var i=0; i<tform.length; i++) {
			var e=tform.elements[i]; 
			if(e.type=='checkbox') {
				if(e.checked==true) {
					if(e.value != 'on') {
						opids += (e.value+",");
					}
				} 
			}
		} 
		if(opids == '') {
			alert('请选择要支付的课程');
			return ;
		}
		opids = opids.substring(0, opids.length-1);
		document.getElementById('opids').value = opids;
		/*alert('opids='+opids);
		var x = document.getElementById('opids').value;
		alert(x);*/
		//alert(opids);
		var to_window = window.open("/entity/workspaceStudent/studentWorkspace_paymentCourse.action?opids="+opids,"to_pay");
		if(to_window==null){
			alert("您的浏览器拦截了窗口，请关闭弹窗拦截。");
		}
		//"/entity/workspaceStudent/studentWorkspace_tonewcourse.action"
	}
	
	function is_Delete(opencourseId) {
		var answer = confirm("确定要删除吗？");
		if (answer) {
			window.location.href = "/entity/workspaceStudent/studentWorkspace_delCourseFromOrder.action?electiveId="+opencourseId;
		}
	}
</script>
</html>