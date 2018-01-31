<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.lore.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ include file="../pub/priv.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>单选题</title>
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
<link href="../css/css.css" rel="stylesheet" type="text/css">

</head>
<%
	//EntityConfig entityConfig = (EntityConfig)application.getAttribute("entityConfig");
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
	
	
	String loreId = request.getParameter("loreId");
	String loreDirId = request.getParameter("loreDirId");
	String type = request.getParameter("type").toUpperCase();
	String type_str = TestQuestionType.typeShow(type);
%>
<body leftmargin="0" topmargin="0">
<script language=javascript src="../js/check.js"></script>
<script>

function resetAll() {
	document.forms['question'].reset();
	var oEditor = FCKeditorAPI.GetInstance('body') ;
	oEditor.SetData('');	
}

	var bLoaded=false; 
function pageGuarding()	{  
		
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
			{
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
			{
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
		
		if(document.question.title.value.indexOf("'")!="-1")
		{
			alert("对不起，题目名称不能包含英文单引号！");
			document.question.title.focus();
			return;
		}
		
		//判断fckeditor 的内容是否为空
	   /*var oEditor = FCKeditorAPI.GetInstance('body') ;
       var acontent=oEditor.GetXHTML();
       if(acontent==""){
			alert("对不起，题目内容不能为空！");
			document.question.body.focus();
			return;
		}
		*/
		var acontent = document.question.body.value;
        if(acontent==""){
			alert("对不起，题目内容不能为空！");
			document.question.body.focus();
			return;
		}
	/*	if(document.question.body.value==""){
			alert("对不起，题目内容不能为空！");
			document.question.body.focus();
			return;
		}
		*/

	if(!optcheck())		
	{
		alert("选项不能为空。"); return;
	}
	
		var resualt=false;
	for(var i=0;i<document.question.answer.length;i++)
	{
	    if(document.question.answer[i].checked)
	    {
	      resualt=true;
	    }
	}
	if(!resualt)
	{
	    alert("请选择一个答案！");
	    return false;
	}
		document.question.submit();
	}
	
function  pageGuarding1(){
		
		
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
			{
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
			{
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
	  // var oEditor = FCKeditorAPI.GetInstance('body') ;
     //  var acontent=oEditor.GetXHTML();
	   var acontent=document.getElementById("body").value;
       if(acontent==""){
			alert("对不起，题目内容不能为空！");
			document.question.body.focus();
			return;
		}
	/*	if(document.question.body.value==""){
			alert("对不起，题目内容不能为空！");
			document.question.body.focus();
			return;
		}
		*/

	if(!optcheck())		
	{
		alert("选项不能为空。"); return;
	}
	document.question.types.value="1";
		document.question.submit();
}
	
	String.prototype.trim=function()
	{
        return this.replace(/(^\s*)|(\s*$)/g, "");
	}
	
	function optcheck()
	{
		var input;
		for(i=0;i<opts.length;i++)
		{
			input=document.getElementById(opts[i]);
			if(input.value.trim()=="")
			{
				input.focus();
				return false;
			}
		}
		return true;
	
	}
	
	var opts=new Array('optionsA','optionsB');
	
	var intRowIndex = 3;
	var charCode = 67;
	function insertRow(){
			if(charCode<74){
				//添加一行			
				var newTr = Tbl.insertRow(intRowIndex);
				
				//添加两列			
				var newTd0 = newTr.insertCell();			
				var newTd1 = newTr.insertCell();
				
				opts[opts.length]='options' + String.fromCharCode(charCode);
				
				//设置列内容和属性
				newTd0.align = 'center';
				newTd0.className = 'text6';			
				newTd0.innerHTML = '选项' + String.fromCharCode(charCode) 
				   + '<input type="radio" name="answer" value="' + String.fromCharCode(charCode) + '">';	
				newTd1.innerHTML = '<table width="100%" border="0" cellspacing="0" cellpadding="0"><tr>'
	               + '<td width="82%"><input id="options' + String.fromCharCode(charCode) + '" name="options" type="text" value="" size="80" maxlength="80"></td>'
	               + '<td style="padding-left:5px"></td>'
				   + '<td valign="bottom" style="padding-left:5px"></td></tr></table>';
				
				//newTr.attachEvent("onclick",getIndex);
				//intRowIndex = newTr.rowIndex;
				intRowIndex = intRowIndex + 1;
				charCode = charCode + 1;
			}else{
				alert("选项最多有9个！");
			}
	}
	Array.prototype.remove=function(dx){ 
	    if(isNaN(dx)||dx>this.length){return false;} 
	    for(var i=0,n=0;i<this.length;i++) 
	    { 
	        if(this[i]!=this[dx]) 
	        { 
	            this[n++]=this[i] 
	        } 
	    } 
	    this.length-=1 
	}
	function deleteRow() {
		if(intRowIndex < 4) {
			alert("选项不能少于两个！");
			return;
		} else {
			Tbl.deleteRow(intRowIndex - 1);
			intRowIndex -= 1;
			charCode -= 1;
			opts.remove(opts.length-1);
		}
	}
</script>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<form name="question" method="post" action="store_question_single_addexe.jsp">
<input type="hidden" name="types" value="0">
<INPUT type="hidden" name="loreId" value="<%=loreId%>">
<INPUT type="hidden" name="loreDirId" value="<%=loreDirId%>">
  <tr> 
    <td height="41" background="images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3">试题属性选择区</td>
  </tr>
  <tr> 
    <td><table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr style="display:none"> 
          <td width="3%" align="center"><img src="images/ct_09.gif" width="14" height="14"></td>
          <td width="15%" class="text6" dis >难&nbsp;&nbsp;&nbsp;&nbsp;度：</td>
          <td width="45%"> <select name="diff">
              <option value="0.0"> 0.0</option>
              <option value="0.1"> 0.1</option>
              <option value="0.2"> 0.2</option>
              <option value="0.3"> 0.3</option>
              <option value="0.4"> 0.4</option>
              <option value="0.5"> 0.5</option>
              <option value="0.6"> 0.6</option>
              <option value="0.7"> 0.7</option>
              <option value="0.8"> 0.8</option>
              <option value="0.9"> 0.9</option>
              <option value="1.0"> 1.0</option>
            </select> </td>
          <td width="3%"></td>
          <td width="10%" class="text6"></td>
          <td width="4%" class="text6">
          </td>
          <td class="text6"></td>
        </tr>
        <tr> 
          <td height="3" colspan="7" align="center" background="images/ct_08.gif"> 
          </td>
        </tr>
        <tr> 
          <td align="center"><img src="images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">认知分类：</td>
          <td><select name="cognizetype">
              <option value="LIAOJIE"> 了解</option>
              <option value="LIJIE"> 理解</option>
              <option value="YINGYONG"> 应用</option>
              <option value="FENXI"> 分析</option>
              <option value="ZONGHE"> 综合</option>
              <option value="PINGJIAN"> 评鉴</option>
            </select></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td></td>
          <td class="text6"></td>
        </tr>
        <tr> 
          <td height="3" colspan="7" align="center" background="images/ct_08.gif"> 
          </td>
        </tr>
        <tr> 
          <td align="center"><img src="images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">建议题目分值：</td>
          <td class="text6"><input name="referencescore" type="text" size="10" maxlength="15" value="1">
            分</td>
          <td><input name="referencetime" type="hidden" value="10"></td>
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
          <td class="text6"><input name="title" type="text" size="10" maxlength="15">(不能包含英文单引号)</td>
          <td><img src="images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">题目类型：</td>
          <td class="text6"> <input type="hidden" name="type" value="<%=type%>"></td>
          <td class="text6"><%=type_str%></td>
        </tr>
        <tr> 
          <td height="3" colspan="7" align="center" background="images/ct_08.gif"> 
          </td>
        </tr>
      </table>
      <br/></td>
  </tr>
  <tr>
  <td height="41" background="images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3">题目描述<!-- （"Shift+Enter"=换行,"Enter"=分段） --></td>
  </tr>
  <tr>
   <td align="center">
          	  <textarea name="remark" class=selfScale rows="5" cols="160"></textarea>
          </td>
  </tr>
  <tr> 
    <td height="41" background="images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3">试题内容编辑区<!-- （"Shift+Enter"=换行,"Enter"=分段） --></td>
  </tr>
  
  <tr> 
    <td><table width="90%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolordark="#999999" bordercolorlight="#FFFFFF">
        <tr>
          <td align="center">
          	  <textarea name=body class=selfScale rows="15" cols="160"></textarea>
          </td>
        </tr>
      </table><br/>
     </td>
  </tr>
  <tr> 
    <td>
    <table id="Tbl" width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
		<td height="25" colspan="2">
			<input type="button" name="add" value="添加选项" onclick="insertRow()">&nbsp;&nbsp;&nbsp;
			<input type="button" name="del" value="删除选项" onclick="deleteRow()">
		</td>
		</tr>
        <tr> 
          <td align="center" class="text6">选项A<input type="radio" name="answer" value="A"></td>
          <td height="35"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="82%"><input id="optionsA" name="options" type="text" size="80" maxlength="80"></td>
                <td style="padding-left:5px"></td>
				<td valign="bottom" style="padding-left:5px"></td>

              </tr>
            </table>
          </td>
        </tr>
        <tr> 
          <td align="center" class="text6">选项B<input type="radio" name="answer" value="B"></td>
          <td height="35"> 
          	<table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="82%"> <input id="optionsB" name="options" type="text" size="80" maxlength="80"> 
                </td>
                <td style="padding-left:5px"><br/></td>
				<td valign="bottom" style="padding-left:5px"><br/></td>
              </tr>
            </table>
          </td>
        </tr>
      </table></td>
  </tr>
  
  </form>
  <tr> 
    <td height="65" align="right" style="padding-right:50px"> 
      <table width="50%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><a href="#" onclick="javascript:pageGuarding()"><img src="images/bcfh.gif" width="100" height="30" border="0"></a></td>
<!--      <td><a href="lore_store_question.jsp?id=<%=loreId%>&loreDirId=<%=loreDirId%>" target="_parent"><img src="images/zjfh.gif" width="100" height="30" border="0"></a></td>  -->
          <td><a href="#" onclick="javascript: top.window.close()"><img src="images/zjfh.gif" width="100" height="30" border="0"></a></td>
          <td><a href="#" onclick="resetAll();"><img src="images/cxlr.gif" width="105" height="30" border="0"></a></td>
          <td><a href="#" onclick="javascript:pageGuarding1()" ><img src="images/xyt.gif" width="100" height="30" border="0"></a></td>
          <td>&nbsp;</td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<script type="text/javascript"> 
<!-- 
// Automatically calculates the editor base path based on the _samples directory. 
// This is usefull only for these samples. A real application should use something like this: 
// oFCKeditor.BasePath = '/fckeditor/' ; // '/fckeditor/' is the default value. 
/*
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
*/
//--> 
</script> 
<script>bLoaded=true;</script>

</body>
</html>
