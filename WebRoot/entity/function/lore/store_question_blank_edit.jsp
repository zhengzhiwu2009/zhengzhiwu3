<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.lore.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.util.string.*"%>
<%@ include file="../pub/priv.jsp"%>
<%@ include file="../pub/commonfuction.jsp"%>

<%
			InteractionFactory interactionFactory = InteractionFactory.getInstance();
			InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
			TestManage testManage = interactionManage.creatTestManage();
			
			
			String id = request.getParameter("id");
			StoreQuestion question = testManage.getStoreQuestion(id);
			String diff = question.getDiff();
			String cognizetype = question.getCognizeType();
			String referencetime = question.getReferenceTime();
			String referencescore = question.getReferenceScore();
			String title = question.getTitle();
			String purpose = question.getPurpose();
			String type = question.getType();
			String studentnote = question.getStudentNote();
			String teachernote = question.getTeacherNote();
			String questionCoreXml = question.getQuestionCore();
				
			List coreList = XMLParserUtil.parserBlankAnswer(questionCoreXml);
			//WhatyStrManage str = new WhatyStrManage((String)coreList.get(0));
			//String bodyString = str.htmlDecode();
			//String answer = (String)coreList.get(coreList.size()-1);
			String bodyString = (String)coreList.get(0);
			String answer = (String)coreList.get(coreList.size()-1);
			EntityConfig entityConfig = (EntityConfig)application.getAttribute("entityConfig");
	
			String loreId = request.getParameter("loreId");
			String loreDirId = request.getParameter("loreDirId");
			String type_str = TestQuestionType.typeShow(type);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>填空题</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>

</head>

<body leftmargin="0" topmargin="0">
<script language=javascript src="../js/check.js"></script>
<script>
	var bLoaded=false; 
	
	function pageGuarding()	{
		
		if (bLoaded==false) {
			alert("表单正在下载")
			return false
		}
		var purcheck = false;
		var obj = document.getElementsByName("purpose");
		for(i=0; i<obj.length; i++) {
			if(obj[i].checked)
				purcheck = true;
		}
		if(purcheck == false)
		{
			alert("题目用途请至少选择一项");
			return false;
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
	   var oEditor = FCKeditorAPI.GetInstance('body') ;
       var acontent=oEditor.GetXHTML();
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

		if(document.question.answer.value==""){
			alert("对不起，答案内容不能为空！");
			document.question.answer.focus();
			return;
		}
		
		
		
		//alert(document.question.body.value);
		document.question.submit();
	}
</script>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<form name="question" action="store_question_blank_editexe.jsp" method="post">
<INPUT type="hidden" name="id" value="<%=id%>">
<INPUT type="hidden" name="loreId" value="<%=loreId%>">
<INPUT type="hidden" name="loreDirId" value="<%=loreDirId%>">
  <tr> 
    <td height="41" background="images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3">试题属性选择区</td>
  </tr>
  <tr> 
    <td><table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="3%" align="center"><img src="images/ct_09.gif" width="14" height="14"></td>
          <td width="15%" class="text6">难&nbsp;&nbsp;&nbsp;&nbsp;度：</td>
          <td width="45%"> <select name="diff">
              <option value="0.0" <%if(diff.equals("0.0")){%>selected<%}%>> 0.0</option>
              <option value="0.1" <%if(diff.equals("0.1")){%>selected<%}%>> 0.1</option>
              <option value="0.2" <%if(diff.equals("0.2")){%>selected<%}%>> 0.2</option>
              <option value="0.3" <%if(diff.equals("0.3")){%>selected<%}%>> 0.3</option>
              <option value="0.4" <%if(diff.equals("0.4")){%>selected<%}%>> 0.4</option>
              <option value="0.5" <%if(diff.equals("0.5")){%>selected<%}%>> 0.5</option>
              <option value="0.6" <%if(diff.equals("0.6")){%>selected<%}%>> 0.6</option>
              <option value="0.7" <%if(diff.equals("0.7")){%>selected<%}%>> 0.7</option>
              <option value="0.8" <%if(diff.equals("0.8")){%>selected<%}%>> 0.8</option>
              <option value="0.9" <%if(diff.equals("0.9")){%>selected<%}%>> 0.9</option>
              <option value="1.0" <%if(diff.equals("1.0")){%>selected<%}%>> 1.0</option>
            </select> </td>
          <td width="3%"><img src="images/ct_09.gif" width="14" height="14"></td>
          <td width="10%" class="text6">题目用途：</td>
          <td width="4%" class="text6"> <input type="checkbox" name="purpose" value="KAOSHI" <%if(purpose.indexOf("KAOSHI") >= 0){%>checked<%}%>> 
          </td>
          <td class="text6">在线自测</td>
        </tr>
        <tr> 
          <td height="3" colspan="7" align="center" background="images/ct_08.gif"> 
          </td>
        </tr>
        <tr> 
          <td align="center"><img src="images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">认知分类：</td>
          <td><select name="cognizetype">
              <option value="LIAOJIE" <%if(cognizetype.equals("LIAOJIE")){%>selected<%}%>> 了解</option>
              <option value="LIJIE" <%if(cognizetype.equals("LIJIE")){%>selected<%}%>> 理解</option>
              <option value="YINGYONG" <%if(cognizetype.equals("YINGYONG")){%>selected<%}%>> 应用</option>
              <option value="FENXI" <%if(cognizetype.equals("FENXI")){%>selected<%}%>> 分析</option>
              <option value="ZONGHE" <%if(cognizetype.equals("ZONGHE")){%>selected<%}%>> 综合</option>
              <option value="PINGJIAN" <%if(cognizetype.equals("PINGJIAN")){%>selected<%}%>> 评鉴</option>
            </select></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td><input type="checkbox" name="purpose" value="ZUOYE" <%if(purpose.indexOf("ZUOYE") >= 0){%>checked<%}%>></td>
          <td class="text6">在线作业</td>
        </tr>
        <tr> 
          <td height="3" colspan="7" align="center" background="images/ct_08.gif"> 
          </td>
        </tr>
        <tr> 
          <td align="center"><img src="images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">建议作答时间：</td>
          <td class="text6"><input name="referencetime" type="text" size="10" maxlength="15" value="<%=referencetime%>">
            秒</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td><input type="checkbox" name="purpose" value="EXPERIMENT" <%if(purpose.indexOf("EXPERIMENT") >= 0){%>checked<%}%>></td>
          <td class="text6">实验</td>
        </tr>
        <tr> 
          <td height="3" colspan="7" align="center" background="images/ct_08.gif"> 
          </td>
        </tr>
        <tr> 
          <td align="center"><img src="images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">建议题目分值：</td>
          <td class="text6"><input name="referencescore" type="text" size="10" maxlength="15" value="<%=referencescore%>">
            分</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td><input type="checkbox" name="purpose" value="EXAM" <%if(purpose.indexOf("EXAM") >= 0){%>checked<%}%>></td>
          <td class="text6">在线考试</td>
        </tr>
        <tr> 
          <td height="3" colspan="7" background="images/ct_08.gif"> </td>
        </tr>
        <tr> 
          <td align="center"><img src="images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">题目名称：</td>
          <td class="text6"><input name="title" type="text" size="10" maxlength="15" value="<%=title%>"></td>
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
          	  <textarea name="body" class=selfScale rows="22" cols="72"><%=bodyString%></textarea>
          </td>
        </tr>
      </table><br/>
     </td>
  </tr>
  <tr> 
    <td>
    <table id="Tbl" width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr> 
          <td align="center"  class="text6">答案</td>
          <td height="90" valign="middle"> 
            <textarea name="answer" cols="81" rows="5"><%=answer%></textarea>
          </td>
        </tr>
        <tr> 
          <td align="center"  class="text6">题目提示<br/>
            （对学生）</td>
          <td height="90" valign="middle"> 
            <textarea name="studentnote" cols="81" rows="5"><%=fixnull(studentnote)%></textarea>
          </td>
        </tr>
        <tr> 
          <td align="center" class="text6">题目要求<br/>
            （对教师）</td>
          <td height="90"> 
            <textarea name="teachernote" cols="81" rows="5"><%=fixnull(teachernote)%></textarea>
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
          <td><a href="lore_store_question.jsp?id=<%=loreId%>&loreDirId=<%=loreDirId%>" ><img src="images/zjfh.gif" width="100" height="30" border="0"></a></td>
          <td><a href="#" onclick="javascript:document.forms['question'].reset()"><img src="images/cxlr.gif" width="105" height="30" border="0"></a></td>
          <!-- td><a href="#"><img src="images/xyt.gif" width="105" height="30" border="0"></a></td-->
        </tr>
      </table>
    </td>
  </tr>
</table>

<script>bLoaded=true;</script>
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
</body>

</html>
