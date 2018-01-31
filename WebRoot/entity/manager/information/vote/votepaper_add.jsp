<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page import="java.util.*,com.whaty.platform.config.*"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" media="all"	href="/js/calendar/calendar-win2000.css">

		<script type="text/javascript" src="/js/calendar/calendar.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
		<script language="javascript" src="../js/check.js"></script>	
		
		<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
		<script>
		var bSubmit=false;
		var bLoaded=false;
		function pageGuarding(){
		/*if(currentflag==1) {//处于普通编辑模式
			document.paperinfo.body.value=document.frames.editor.frames.edit1.textEdit.document.body.innerHTML;
		} else {
			if (currentflag==3) {//处于预浏览先通编辑模式
				document.info_news_add.body.value=oDiv.innerHTML;
			}
		}
		document.paperinfo.body.value=Absolute2Relative(document.paperinfo.body.value);//替换绝对路径*/
		
			
			if(document.paperinfo.title.value.replace(/(^\s*)|(\s*$)/g, "") == "") {
				alert("调查问卷名不能为空!");
				document.paperinfo.title.focus();
				return false;
			}
			
			if(document.paperinfo.startDate.value == "") {
				alert("开始时间不能为空!");
				return false;
			}
			
			if(document.paperinfo.endDate.value == "") {
				alert("结束时间不能为空!");
				return false;
			}
			
			if(!checkRight(document.paperinfo.startDate.value,document.paperinfo.endDate.value)) {
				alert("开始结束时间不合理！");
				return false;
			}
			if(document.paperinfo.title.value.length > 50)
			{
				alert("调查问卷名不得多于50个字，请重新填写!");
				document.paperinfo.title.focus();
				document.paperinfo.title.select();
				return false;
			}	
		/*
			if(document.paperinfo.paper_pictitle.value.length > 50)
			{
				alert("图片背景上的名称不得多于50个字，请重新填写!");
				document.paperinfo.paper_pictitle.focus();
				document.paperinfo.paper_pictitle.select();
				return false;
			}	
			
			var type = document.getElementById("type");
			if(type.value=='1'){
				var course_keywords = document.getElementsByName("course_keywords");
				var isZero = true;
				for(i=0;i<course_keywords.length;i++)
				{
					if(course_keywords[i].checked)
					{
						isZero = false;
						break;	
					}
				}
				if(isZero)
				{
					alert("请选择所属课程！");
					return;
				}
			}*/
			
			
			var oEditor = FCKeditorAPI.GetInstance('body') ;
       var acontent=oEditor.GetXHTML();
       	if(acontent == null || acontent==""){
       		alert("内容为空，您还是多写几句吧！");
					return;
       	}
       	
       	if(acontent.length > 2000)
			{
				alert("说明不得多于2000个字，请重新填写!");
				return false;
			}
			
			document.paperinfo.submit();
		}
		// 取消操作
		function quxiao() {
		document.paperinfo.reset();
		var oEditor = FCKeditorAPI.GetInstance('body') ;
		oEditor.EditorDocument.body.innerHTML="";    //清空Editor中内容
		}
		
		function checkRight(startDateStr, endDateStr) {
			var startArr = startDateStr.split("-");
			var endArr = endDateStr.split("-");
			var startDate = new Date(startArr[0],startArr[1],startArr[2]);
			var endDate = new Date(endArr[0],endArr[1],endArr[2]);
			if(startDate > endDate)
				return false;
			return true;
		}
		
		function changeDiv(){
			var type = document.getElementById("type");
			var div_normal = document.getElementById("div_normal");
			var div_course = document.getElementById("div_course");
			var div_normal1 = document.getElementById("div_normal1");
			var div_course1 = document.getElementById("div_course1");
			if(type.value==0){
				div_normal.style.display = "none";
				div_course.style.display = "none";
				div_normal1.style.display = "none";
				div_course1.style.display = "none";
			}
			else{
				div_normal.style.display = "none";
				div_course.style.display = "block";
				div_normal1.style.display = "none";
				div_course1.style.display = "block";
			}
		}
		function changeSemester(){

			var semester_id = document.getElementById("semester_id").value;
			var div_course2 = document.getElementById("div_course2");
			var div_course1 = document.getElementById("div_course1");
			var button_course = document.getElementById("button_course");
			<s:iterator value="map.keySet()" id="class">
                semester_ids = '<s:property value="#class.getId()"/>';
				if(semester_id==semester_ids)
				{
					var innerhtml="<input type='checkbox' name='course_keywords_all' value='' onclick='checkCourseAll()'>&nbsp;&nbsp;&nbsp;&nbsp;全选<br/>";
                    <s:iterator value="map.get(#class)" id="name">
						opencourse_id = '<s:property value="peTchCourse.id"/>';
						opencourse_name = '<s:property value="peTchCourse.name"/>';
						innerhtml+="<input type='checkbox' name='course_keywords' value='"+opencourse_id+"'>&nbsp;&nbsp;&nbsp;&nbsp;"+opencourse_name+"<br/>";
                    </s:iterator>
                    innerhtml+="<input type='button' onclick='showDivCourse2()' value='确定'>";
					div_course2.innerHTML = innerhtml;
					div_course2.style.display='none';
					button_course.style.display='block';
				}		
			</s:iterator>			
			if(semester_id=='')
			{
				div_course2.innerHTML = "";
				div_course2.style.display='none';
				button_course.style.display='none';
			}
		}
		
		function showDivCourse2()
		{
			var div_course2 = document.getElementById("div_course2");
			if(div_course2.style.display=='none')
				div_course2.style.display='block';
			else
				div_course2.style.display='none';
		}
		
		function checkCourseAll()
		{
			var course_keywords = document.getElementsByName("course_keywords");
			for(i=0;i<course_keywords.length;i++)
			{
				course_keywords[i].checked = !course_keywords[i].checked;
			}
		}
		function pageGuardings()
		{
			var type = document.getElementById("type");
			type.value=1;
			if(type.value==1){
				var course_keywords = document.getElementsByName("course_keywords");
				var isZero = true;
				for(i=0;i<course_keywords.length;i++)
				{
					if(course_keywords[i].checked)
					{
						isZero = false;
						break;	
					}
				}
				if(isZero)
				{
					alert("请选择所属课程！");
					return false;
				}
			}
			return true;
		}
</script>
	</head>

	<body leftmargin="0" topmargin="0" class="scllbar">
		<form name='paperinfo' action="/entity/information/peVotePaper_addVotePaper.action" method='post'
			class='nomargin' onsubmit="">
			<%@ include file="/web/form-token.jsp" %>
			<table width="99%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td><div class="content_title" id="zlb_title_start">
												添加调查问卷
											</div id="zlb_title_end"></td>
				</tr>
				<tr>
					<td valign="top" class="pageBodyBorder_zlb">
						<div class="cntent_k" id="zlb_content_start">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom" width="20%" nowrap>
										<span class="name">调查问卷名称：<font color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</td>
									<td>
										<input type=text name="peVotePaper.title" id="title" class=selfScale size="50" maxlength="50"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom" nowrap>
										<span class="name">调查问卷类型：<font color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</td>
									<td>
										<!--<select name="type" onchange="changeDiv()"> -->
										<select name="type">
										<s:iterator value="enumConstList" >
										<option value="<s:property value="code"/>"><s:property value="name"/></option>  
										</s:iterator>
										<option value="jgstu">面向监管类学员</option>
										<option value="jgman">面向监管类集体管理员</option>
										</select> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>

							<!-- 	<tr valign="bottom" class="postFormBox">
									<td valign="top">
										<div id="div_normal" name="div_normal" style="display:none"><span class="name">关键字：</span></div>
										<div id="div_course" name="div_course" style="display:none"><span class="name">所属课程：</span></div>
									</td>
									<td>
									<div id="div_normal1" name="div_normal1"  style="display:none">
										<input type=text name="peVotePaper.keywords" id="normal_keywords" class=selfScale size="50" maxlength="50"> 
										&nbsp;&nbsp;&nbsp;
									</div>
									<div id="div_course1" name="div_course1" style="display:none">
										选择学期<font color="red">*</font>&nbsp;
										<select name="semester_id" id="semester_id" onchange="changeSemester();">
												<option value="">请选择学期</option>
												<s:iterator value="map.keySet()" id="class">
												<option value="<s:property value="#class.id"/>"><s:property value="#class.name"/></option>
												</s:iterator>									
										</select>
										<img src="/entity/manager/images/buttons/help.png" width="16" height="16"
											class="helpImg" onMouseOver="window.status='调查问卷的所属课程，在调用时需要使用'"
											onmouseout="window.status='信息提示...'">										
										<br/>
										<input type="button" name="button_course" value="选择所属课程" onclick="showDivCourse2()" style="display:none">
										<div id="div_course2" name="div_course2" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
										</div>
									</div>
									</td>
								</tr>   

								<tr valign="bottom" class="postFormBox">
									<td valign="bottom" nowrap>
										<span class="name">图片背景上的名称：</span>
									</td>
									<td>
										<input type=text name="peVotePaper.pictitle" id="paper_pictitle" class=selfScale size="100" maxlength="100"> 
										 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>-->

								<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">开始时间：<font color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</td>
									<td>
										<input type=text name="peVotePaper.startDate" id="startDate" class=selfScale readonly>
										<img src="/js/calendar/img.gif" width="20" height="14"
											id="f_trigger_b"
											style="border: 1px solid #551896; cursor: pointer;"
											title="Date selector"
											onmouseover="this.style.background='white';"
											onmouseout="this.style.background=''"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">结束时间：<font color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</td>
									<td>
										<input type="text" name="peVotePaper.endDate" id=endDate class=selfScale readonly>
										<img src="/js/calendar/img.gif" width="20" height="14"
											id="f_trigger_c"
											style="border: 1px solid #551896; cursor: pointer;"
											title="Date selector"
											onmouseover="this.style.background='white';"
											onmouseout="this.style.background=''"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr>&nbsp;</tr><tr>&nbsp;</tr><tr>&nbsp;</tr>
								 <tr valign="bottom" class="postFormBox"> 
						              <td valign="top"><span class="name">内容：<font color="red">*</font></span></td>
						              <td> 
						              <textarea class="smallarea"  name="peVotePaper.note" cols="70" rows="12" id="body"></textarea>
						
									  <img src="/entity/manager/images/buttons/help.png" width="16" height="16" class="helpImg" onMouseOver="window.status='该调查问卷的介绍'" onmouseout="window.status='信息提示...'"/>
						              </td>
            					</tr>
							
								<tr valign="bottom" class="postFormBox" style="display:none">
									<td valign="bottom">
										<span class="name">是否发布：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</td>
									<td>
										<select name="active" id="active" class="input6303">
											<option value="0" selected>
												否
											</option>
										<!-- 
										 <option value="1">
												是
											</option>
										 -->	
										</select> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr> 
								<!--  
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">是否允许添加建议：</span>
									</td>
									<td>
										<select name="canSuggest" id="canSuggest" class="input6303">
											<option value="0" selected>
												否
											</option>
											<option value="1">
												是
											</option>
										</select> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">是否允许查看建议：</span>
									</td>
									<td>
										<select name="viewSuggest" id="viewSuggest" class="input6303">
											<option value="0" selected>
												否
											</option>
											<option value="1">
												是
											</option>
										</select> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								-->
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">限制IP：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</td>
									<td>
										<select name="limitIp" id="limitIp" class="input6303">
											<option value="0" selected>
												否
											</option>
											<option value="1">
												是
											</option>
										</select> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">限制会话：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</td>
									<td>
										<select name="limitSession" id="limitSession"
											class="input6303">
											<option value="0" selected>
												否
											</option>
											<option value="1">
												是
											</option>
										</select> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
							</table>
						</div>

					</td>
				</tr>
				<tr>
					<td align="center" id="pageBottomBorder_zlb"><div class="content_bottom"> <table width="98%" height="100%" border="0" cellpadding="0"
							cellspacing="0"><tr>
								<td align="center" valign="middle">
									<span class="norm"
										style="width:46px;height:15px;padding-top:3px"
										onmouseover="className='over'" onmouseout="className='norm'"
										onmousedown="className='push'" onmouseup="className='over'"
										onclick="javascript:pageGuarding();"><span class="text">提交</span>
									</span>
								</td>
								<td align="center" valign="middle">
									<span class="norm"
										style="width:46px;height:15px;padding-top:3px"
										onmouseover="className='over'" onmouseout="className='norm'"
										onmousedown="className='push'" onmouseup="className='over'"
										onclick="javascript:quxiao();"><span class="text">取消</span>
									</span>
								</td>
							</tr>
						</table>
					</td>
				</tr>

			</table>
		</form>
		<script>bLoaded=true;</script>
		<script type="text/javascript">
    Calendar.setup({
        inputField     :    "startDate",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       //: %H:%M:%S",       // format of the input field
        button         :    "f_trigger_b",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")  showsTime      :         true,  
        singleClick    :    true
    });
    
    Calendar.setup({
        inputField     :    "endDate",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_c",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
</script>
	</body>
	
<script type="text/javascript"> 
<!-- 
// Automatically calculates the editor base path based on the _samples directory. 
// This is usefull only for these samples. A real application should use something like this: 
// oFCKeditor.BasePath = '/fckeditor/' ; // '/fckeditor/' is the default value. 

var oFCKeditor = new FCKeditor( 'body' ) ; 
oFCKeditor.Height = 300 ; 
oFCKeditor.Width  = 700 ; 

oFCKeditor.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
oFCKeditor.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';

oFCKeditor.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
oFCKeditor.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';

oFCKeditor.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
oFCKeditor.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';


oFCKeditor.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; 
oFCKeditor.ReplaceTextarea() ; 
//--> 
</script> 
</html>