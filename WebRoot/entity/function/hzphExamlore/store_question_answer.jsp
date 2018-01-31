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
<title>填空题</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script language=javascript src="../js/check.js"></script>
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
<script>
function resetAll() {
	document.forms['question'].reset();
	var oEditor = FCKeditorAPI.GetInstance('body') ;
	oEditor.SetData('');	
}
	var bLoaded=false; 
	function pageGuarding()	{
		
		
		
		
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
		
	   var acontent = document.question.body.value;
        if(acontent==""){
			alert("对不起，题目内容不能为空！");
			document.question.body.focus();
			return;
		}

		
		
		
		if(document.question.answer.value==""){
			alert("对不起，答案内容不能为空！");
			document.question.answer.focus();
			return;
		}
		
		document.question.submit();
	}
	
	function pageGuarding1()	{
		
	
	
		
	
		
		if(document.question.title.value==""){
			alert("对不起，题目名称不能为空！");
			document.question.title.focus();
			return;
		}
		
		var acontent = document.question.body.value;
        if(acontent==""){
			alert("对不起，题目内容不能为空！");
			document.question.body.focus();
			return;
		}
	
		
		if(document.question.answer.value==""){
			alert("对不起，答案内容不能为空！");
			document.question.answer.focus();
			return;
		}
		
		var answer = document.getElementById("answer");
		document.question.types.value='1';
		document.question.submit();
	}
</script>
</head>
<%
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
	
	String loreId = request.getParameter("loreId");
	String loreDirId = request.getParameter("loreDirId");
	String type = request.getParameter("type").toUpperCase();
	String type_str = TestQuestionType.typeShow(type);
%>

<body leftmargin="0" topmargin="0">
<form  id="question" name="question" action="store_question_answer_addexe.jsp" method="post">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<input type="hidden" name="types" value="0">
<INPUT type="hidden" name="loreId" value="<%=loreId%>">
<INPUT type="hidden" name="loreDirId" value="<%=loreDirId%>">
<input name="referencetime" type="hidden" value="10">

  <tr> 
    <td height="41" background="images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3">试题属性选择区</td>
  </tr>
  <tr> 
    <td><table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
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
          <td><br/></td>
          <td class="text6"><br/></td>
        </tr>
         <tr> 
          <td height="3" colspan="7" align="center" background="images/ct_08.gif"> 
          </td>
        </tr>
         <tr style="display:"> 
          <td align="center"><img src="images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">建议题目分值：</td>
          <td class="text6"><input name="referencescore" type="text" size="10" maxlength="15" value="1">
            分</td>
          <td><input name="referencetime" type="hidden" value="10"></td>
          <td>&nbsp;</td>
          <td><br/></td>
          <td class="text6"><br/></td>
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
    <td height="41" background="images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3">试题内容编辑区（"Shift+Enter"=换行,"Enter"=分段）</td>
  </tr>
  <tr> 
    <td><table width="90%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolordark="#999999" bordercolorlight="#FFFFFF">
        <tr>
          <td align="center">
<!--          	  <img src="images/1.gif" width="730" height="417">-->
          	  <textarea name='body' class=selfScale rows="22" cols="160"></textarea>
             
          </td>
        </tr>
      </table><br/>
     </td>
  </tr>
  <tr> 
    <td>
    <table id="Tbl" width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
		<td height="25" colspan="2">&nbsp;</td>
		</tr>
        <tr> 
          <td align="center"  class="text6">答案</td>
          <td height="90" valign="middle"> 
            <textarea name="answer" cols="81" rows="5"></textarea>
          </td>
        </tr>
      </table></td>
  </tr>
  
  <tr> 
    <td height="65" align="right" style="padding-right:50px"> 
      <table width="50%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><a href="javascript:pageGuarding()"><img src="images/bcfh.gif" width="100" height="30" border="0"></a></td>
        <!--   <td><a href="lore_store_question.jsp?id=<%=loreId%>&loreDirId=<%=loreDirId%>" target="_parent"><img src="images/zjfh.gif" width="100" height="30" border="0"></a></td> -->
        <td><a href="#" onclick="javascript: top.window.close()"><img src="images/zjfh.gif" width="100" height="30" border="0"></a></td>
          <td><a href="#" onclick="resetAll()"><img src="images/cxlr.gif" width="105" height="30" border="0"></a></td>
          <td><a href="#" onclick="javascript:pageGuarding1()" ><img src="images/xyt.gif" width="100" height="30" border="0"></a></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
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