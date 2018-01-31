<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.lore.*"%>
<jsp:directive.page import="java.text.DateFormat"/>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="com.whaty.util.string.encode.HtmlEncoder"/>
<%@ include file="../pub/priv.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>阅读理解</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script language=javascript src="../js/check.js"></script>
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
<script language=javascript src="../js/check.js"></script>
<script>
function resetAll() {
	document.forms['question'].reset();
	var oEditor = FCKeditorAPI.GetInstance('body') ;
	oEditor.SetData('');	
}
	var bLoaded=false; 
	function pageGuarding()	{
		
		//document.question.body.value=Absolute2Relative(document.question.body.value);//替换绝对路径
		if (bLoaded==false) {
			alert("表单正在下载")
			return
		}
		var tt=document.question.tt;
		if(tt.value=='')
		{
			alert("请选择题目类型！");
			return;
		}
		
		
		if(document.question.branch_type.value==""){
			alert("对不起，择题目类型不能为空！");
			 document.question.branch_type.focus();
			return;
		}
		
		
		if(document.question.referencetime.value==""){
			alert("对不起，作答时间不能为空！");
			 document.question.referencetime.focus();
			return;
		}
		
		if(document.question.referencetime.value!=""){
			if(isNum(document.question.referencetime.value))
			{
			}
		else
			{ alert(111);
				alert("输入时间必须为数字!");
				document.question.referencetime.focus();
				//document.form_xml.zip_code.select();
				return ;
			}
		}
		
		if(document.question.referencescore.value==""){
			alert("对不起，题目分值不能为空！");
			document.question.referencescore.focus();
			return;
		}
		
		if(document.question.referencescore.value!=""){
		if(isNum(document.question.referencescore.value))
			{
			}
		else
			{alert(112);
				alert("输入分值必须为数字!");
				document.question.referencescore.focus();
				//document.form_xml.zip_code.select();
				return ;
			}
		}
		
		if(document.question.title.value==""){
			alert("对不起，题目名称不能为空！");
			document.question.title.focus();
			return;
		}
		//判断fckeditor 的内容是否为空
	   var oEditor = FCKeditorAPI.GetInstance('body') ;
       var acontent=oEditor.GetXHTML();
       if(acontent==""){
			alert("对不起，题目内容不能为空！");
			document.question.body.focus();
			return;
		}
		//options
		
		if(document.question.branch_type.value=="DANXUAN"){
			// document.question.branch_type.focus(); sing_ob
			if(document.getElementById("sing_ob").value==""||document.getElementById("sing_oa").value=="")		
			{
				alert("选项A和B不能为空。"); return;
			}
/*			var danxuanxiang = document.getElementsByName("single_answer").value;
			if(danxuanxiang.length==0){	
				alert("您还没有选择正确答案！");
				return;
			}
*/			
		}
		if(document.question.branch_type.value=="DUOXUAN"){
			// document.question.branch_type.focus(); sing_ob
/*			if(document.getElementById("multi_oa").value==""||document.getElementById("multi_ob").value=="")		
			{
				alert("选项A和B不能为空。"); return;
			}
*/			
			var count = 0;
			
			for(var j=0;j <document.question.multi_answer.length;j++)
			{  
				if(document.question.multi_answer[j].checked)
				{
					count ++;
					//alert( "对不起，您还没选择答案！");
					//document.question.multi_answer.focus();
					//return false;
				} 
			}
			if(count==0){
			alert( "对不起，您还没选择答案！");
			return;
			}
			
			if(document.getElementById("multi_otb").value==""||document.getElementById("multi_ota").value=="")		
			{
				alert("选项A和B不能为空。"); return;
			}
			
		}
		
		
		if(document.question.branch_type.value=="TIANKONG"){
			if(document.question.blank_answer.value==""){
			alert("对不起，答案不能为空！");
			document.question.blank_answer.focus();
			return;
		}
		}
		
		if(document.question.branch_type.value=="WENDA"){
			if(document.question.answer_answer.value==""){
			alert("对不起，答案不能为空！");
			document.question.answer_answer.focus();
			return;
		}
		}
		
		
		document.forms['question'].submit()
	}
	  
	var intRowIndexSingle = 3;
	var charCodeSingle = 67;
	function insertRowSingle(){
			//添加一行			
			var newTr = SingleTbl.insertRow(intRowIndexSingle);
			
			//添加两列			
			var newTd0 = newTr.insertCell();			
			var newTd1 = newTr.insertCell();
			
			//设置列内容和属性
			newTd0.align = 'center';
			newTd0.className = 'text6';			
			newTd0.innerHTML = '选项' + String.fromCharCode(charCodeSingle) 
			   + '<input type="radio" name="single_answer" value="' + String.fromCharCode(charCodeSingle) + '">';	
			newTd1.innerHTML = '<table width="100%" border="0" cellspacing="0" cellpadding="0"><tr>'
               + '<td width="82%"><input name="single_options" type="text" value="" size="80" maxlength="80"></td>'
               + '<td style="padding-left:5px"></td>'
			   + '<td valign="bottom" style="padding-left:5px"></td></tr></table>';
			
			//newTr.attachEvent("onclick",getIndex);
			//intRowIndex = newTr.rowIndex;
			intRowIndexSingle = intRowIndexSingle + 1;
			charCodeSingle = charCodeSingle + 1;
	}
	
	
	
		function deleteRow() {
		if(intRowIndexSingle < 4) {
			alert("选项不能少于两个！");
			return;
		} else {
			SingleTbl.deleteRow(intRowIndexSingle - 1);
			intRowIndexSingle -= 1;
			charCodeSingle -= 1;
		}
	}
	function deleteRow3() { 
		if(intRowIndexSingle2 < 4) {
			alert("选项不能少于两个！");
			return;
		} else {
			SingleTbl.deleteRow(intRowIndexSingle2 - 1);
			intRowIndexSingle2 -= 1;
			charCodeSingle2 -= 1;
		}
	}
	
	var intRowIndexMulti = 3;
	var charCodeMulti = 67;
	function insertRowMulti(){
			//添加一行			
			var newTr = MultiTbl.insertRow(intRowIndexMulti);
			
			//添加两列			
			var newTd0 = newTr.insertCell();			
			var newTd1 = newTr.insertCell();
			
			//设置列内容和属性
			newTd0.align = 'center';
			newTd0.className = 'text6';			
			newTd0.innerHTML = '选项' + String.fromCharCode(charCodeMulti) 
			   + '<input type="checkbox" name="multi_answer" value="' + String.fromCharCode(charCodeMulti) + '">';	
			newTd1.innerHTML = '<table width="100%" border="0" cellspacing="0" cellpadding="0"><tr>'
               + '<td width="82%"><input name="multi_options" type="text" value="" size="80" maxlength="80"></td>'
               + '<td style="padding-left:5px"></td>'
			   + '<td valign="bottom" style="padding-left:5px"></td></tr></table>';
			
			//newTr.attachEvent("onclick",getIndex);
			//intRowIndex = newTr.rowIndex;
			intRowIndexMulti = intRowIndexMulti + 1;
			charCodeMulti = charCodeMulti + 1;
	}
	function deleteRow2() { 
		if(intRowIndexMulti< 4) {
			alert("选项不能少于两个！");
			return;
		} else {
			MultiTbl.deleteRow(intRowIndexMulti - 1);
			intRowIndexMulti -= 1;
			charCodeMulti -= 1;
		}
	}
	
	function showDiv(val) {
		if(val == "DANXUAN") {
			single.style.display = '';
			multi.style.display = 'none';
			judge.style.display = 'none';
			blank.style.display = 'none';
			answer.style.display = 'none';
			document.forms["question"].action = "store_question_comprehension_single_addexe.jsp";
			document.forms["question"].tt.value = "DANXUAN";
		} else if(val == "DUOXUAN") {
			multi.style.display = '';
			single.style.display = 'none';
			judge.style.display = 'none';
			blank.style.display = 'none';
			answer.style.display = 'none';
			document.forms["question"].action = "store_question_comprehension_multi_addexe.jsp";
			document.forms["question"].tt.value = "DUOXUAN";
		} else if(val == "PANDUAN") {
			judge.style.display = '';
			single.style.display = 'none';
			multi.style.display = 'none';
			blank.style.display = 'none';
			answer.style.display = 'none';
			document.forms["question"].action = "store_question_comprehension_judge_addexe.jsp";
			document.forms["question"].tt.value = "PANDUAN";
		} else if(val == "TIANKONG") {
			blank.style.display = '';
			single.style.display = 'none';
			multi.style.display = 'none';
			judge.style.display = 'none';
			answer.style.display = 'none';
			document.forms["question"].action = "store_question_comprehension_blank_addexe.jsp";
			document.forms["question"].tt.value = "TIANKONG";
		} else if(val == "WENDA") {
			answer.style.display = '';
			single.style.display = 'none';
			multi.style.display = 'none';
			judge.style.display = 'none';
			blank.style.display = 'none';
			document.forms["question"].action = "store_question_comprehension_answer_addexe.jsp";
			document.forms["question"].tt.value = "WENDA";
		} else {
			single.style.display = 'none';
			multi.style.display = 'none';
			judge.style.display = 'none';
			blank.style.display = 'none';
			answer.style.display = 'none';
		}
	}
</script>
</head>
<%
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
	
	String loreId = request.getParameter("loreId");
	String loreDirId = request.getParameter("loreDirId");
	
	if(session.getAttribute("storeQuestion") == null) {		
		String diff = request.getParameter("diff");
		String cognizetype = request.getParameter("cognizetype");
		String referencetime = request.getParameter("referencetime");
		String referencescore = request.getParameter("referencescore");
		String title = request.getParameter("title");
		String[] purposeVal = request.getParameterValues("purpose");
		String type = request.getParameter("type");
		String questioncore = HtmlEncoder.encode( request.getParameter("body"));

		String studentnote = request.getParameter("studentnote");
		String teachernote = request.getParameter("teachernote");
		String purpose = "";
		for(int i=0; i<purposeVal.length; i++)
			purpose = purpose + purposeVal[i] + "|";
		if(purpose.length() > 0)
			purpose = purpose.substring(0, purpose.length()-1);
		String xml = "<question><body>" + questioncore  + "</body>";
	
//	String creatdate = DateFormat.getDateTimeInstance().format(new Date());
//		creatdate = "20080723";//creatdate.substring(0, creatdate.indexOf(" "));

	 java.util.Date sDate = new java.util.Date();
	SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	 String creatdate=sDateFormat.format(sDate);
	
		StoreQuestion storeQuestion = new StoreQuestion();
		storeQuestion.setTitle(title);
		storeQuestion.setCreatUser("creatuser");
		storeQuestion.setCreatDate(creatdate);
		storeQuestion.setDiff(diff);
		storeQuestion.setKeyWord("keyword");
		storeQuestion.setQuestionCore(xml);
		storeQuestion.setLore(loreId);
		storeQuestion.setCognizeType(cognizetype);
		storeQuestion.setPurpose(purpose);
		storeQuestion.setReferenceScore(referencescore);
		storeQuestion.setReferenceTime(referencetime);
		storeQuestion.setStudentNote(studentnote);
		storeQuestion.setTeacherNote(teachernote);
		storeQuestion.setType(type);
		
		session.setAttribute("storeQuestion", storeQuestion);
	}
%>
<body leftmargin="0" topmargin="0">
<form name="question" action="" method="post">
<INPUT type="hidden" name="tt" value="">
<INPUT type="hidden" name="loreId" value="<%=loreId%>">
<INPUT type="hidden" name="loreDirId" value="<%=loreDirId%>">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td height="35" background="images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3">
    	试题属性选择区
    	<select name="branch_type" onchange="showDiv(this.options[this.options.selectedIndex].value)">
    		  <option value="">请选择题型</option>
              <option value="DANXUAN">单选题</option>
              <option value="DUOXUAN">多选题</option>
              <option value="PANDUAN">判断题</option>
              <option value="TIANKONG">填空题</option>
              <option value="WENDA">问答题</option>
        </select>
    </td>
  </tr>
  <tr> 
    <td>
    <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="3%" align="center"><img src="images/ct_09.gif" width="14" height="14"></td>
          <td width="15%" class="text6">建议作答时间：</td>
          <td width="45%" class="text6"><input name="referencetime" type="text" size="10" maxlength="15">
            秒</td>
          <td width="3%">&nbsp;</td>
          <td width="10%" class="text6">&nbsp;</td>
          <td width="4%" class="text6"></td>
          <td class="text6"></td>
        </tr>
        <tr> 
          <td height="3" colspan="7" align="center" background="images/ct_08.gif"> 
          </td>
        </tr>
        <tr> 
          <td align="center"><img src="images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">建议题目分值：</td>
          <td class="text6"><input name="referencescore" type="text" size="10" maxlength="15">
            分</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td></td>
          <td class="text6"></td>
        </tr>
        <tr> 
          <td height="3" colspan="7" background="images/ct_08.gif"> </td>
        </tr>
        <tr> 
          <td align="center"><img src="images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">题目名称：</td>
          <td class="text6"><input name="title" type="text" size="10" maxlength="15"></td>
          <td></td>
          <td class="text6"></td>
          <td class="text6"></td>
          <td class="text6"></td>
        </tr>
        <tr> 
          <td height="3" colspan="7" align="center" background="images/ct_08.gif"> 
          </td>
        </tr>
      </table>
      <br/></td>
  </tr>
  <tr> 
    <td height="41" background="images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3">试题内容编辑区（"Shift+Enter"=换行,"Enter"=分段）</td>
  </tr>
  <tr> 
    <td>
    <table width="90%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolordark="#999999" bordercolorlight="#FFFFFF">
        <tr>
          <td align="center">
          	  <textarea name=body class=selfScale rows="22" cols="72"></textarea>
             
          </td>
        </tr>
      </table><br/>
     </td>
  </tr>
  <tr> 
    <td>
    <DIV id="single" style="display:none">
    <table id="SingleTbl" width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
		<td height="25" colspan="2"><input type="button" name="add" value="添加选项" onclick="insertRowSingle()">&nbsp;&nbsp;&nbsp;
			<input type="button" name="del" value="删除选项" onclick="deleteRow()">
		
		</td>
		</tr>
        <tr> 
          <td align="center" class="text6">选项A<input type="radio" name="single_answer" value="A" checked="checked"></td>
          <td height="35"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="82%"><input name="single_options" type="text" value="" id="sing_oa" size="80" maxlength="80"></td>
                <td style="padding-left:5px"></td>
				<td valign="bottom" style="padding-left:5px"></td>

              </tr>
            </table>
          </td>
        </tr>
        <tr> 
          <td align="center" class="text6">选项B<input type="radio" name="single_answer" value="B" checked="checked"></td>
          <td height="35"> 
          	<table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="82%"> <input name="single_options" type="text" value="" id="sing_ob" size="80" maxlength="80"> 
                </td>
                <td style="padding-left:5px"></td>
				<td valign="bottom" style="padding-left:5px"></td>
              </tr>
            </table>
          </td>
        </tr>
        
      </table>
      </DIV>
      
      <DIV id="multi" style="display:none">
    	<table id="MultiTbl" width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
		<td height="25" colspan="2"><input type="button" name="add" value="添加选项" onclick="insertRowMulti()"><%-- onclick="insertRowMulti()">--%>
		<input type="button" name="del" value="删除选项" onclick="deleteRow2()">
		</td>
		</tr>
        <tr> 
          <td align="center" class="text6">选项A<input type="checkbox" name="multi_answer" id="multi_oa" value="A" checked="checked"></td>
          <td height="35"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="82%"><input name="multi_options" type="text" id="multi_ota"  value="" size="80" maxlength="80"></td>
                <td style="padding-left:5px"></td>
				<td valign="bottom" style="padding-left:5px"></td>

              </tr>
            </table>
          </td>
        </tr>
        <tr> 
          <td align="center" class="text6">选项B<input type="checkbox" name="multi_answer" id="multi_ob"  value="B" checked="checked"></td>
          <td height="35"> 
          	<table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="82%"> <input name="multi_options" type="text" id="multi_otb" value="" size="80" maxlength="80"> 
                </td>
                <td style="padding-left:5px"></td>
				<td valign="bottom" style="padding-left:5px"></td>
              </tr>
            </table>
          </td>
        </tr>
        
      </table>
      </DIV>
      
      <DIV id="judge" style="display:none">
    	<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
		
        <tr> 
          <td align="center" class="text6">答案</td>
          <td height="35"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="82%" class="text6"><input type="radio" name="judge_answer" value="1" checked="checked">正确&nbsp;<input type="radio" name="judge_answer" value="0">错误</td>
                <td style="padding-left:5px">&nbsp;</td>
				<td valign="bottom" style="padding-left:5px">&nbsp;</td>

              </tr>
            </table>
          </td>
        </tr>
      </table>
      </DIV>
      
      <DIV id="blank" style="display:none">
    	<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
		
        <tr> 
          <td align="center"  class="text6">答案</td>
          <td height="90" valign="middle"> 
            <textarea name="blank_answer" cols="81" rows="5"></textarea>
          </td>
        </tr>
        
      </table>
      </DIV>
      
      <DIV id="answer" style="display:none">
    	<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
		
        <tr> 
          <td align="center"  class="text6">答案</td>
          <td height="90" valign="middle"> 
            <textarea name="answer_answer" cols="81" rows="5"></textarea>
          </td>
        </tr>
        
      </table>
      </DIV>
      </td>
  </tr>
  <tr> 
    <td height="65" align="right" style="padding-right:50px"> 
      <table width="50%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><a href="#" onclick="pageGuarding();"><img src="images/bcfh.gif" width="100" height="30" border="0"></a></td>
          <td><a href="lore_store_question.jsp?id=<%=loreId%>&loreDirId=<%=loreDirId%>" target="_parent"><img src="images/zjfh.gif" width="100" height="30" border="0"></a></td>
          <td><a href="#" onclick="resetAll()"><img src="images/cxlr.gif" width="105" height="30" border="0"></a></td>
          <!-- td><a href="#"><img src="images/xyt.gif" width="105" height="30" border="0"></a></td-->
        </tr>
      </table>
    </td>
  </tr>
</table>
<script>bLoaded=true;</script>
</form>
</body>
<script>
   function returnList(){
    parent.opener.location.href = "lore_store_question.jsp?id=<%=loreId%>&loreDirId=<%=loreDirId%>";
	top.window.close();
   }
</script>
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


oFCKeditor.Value = '' ; 
oFCKeditor.ReplaceTextarea() ; 
//--> 
</script> 
</html>
