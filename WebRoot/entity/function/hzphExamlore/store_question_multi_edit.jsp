<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.lore.*"%>
<%@ page import="com.whaty.platform.test.TestManage,com.whaty.util.string.*" %>
<%@ include file="../pub/priv.jsp"%>
<%@ include file="../pub/commonfuction.jsp"%>

<%
	try {
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
				
			List coreList = XMLParserUtil.parserSingleMulti(questionCoreXml);
			String bodyString = (String)coreList.get(0);
			String answer = (String)coreList.get(coreList.size()-1);
			EntityConfig entityConfig = (EntityConfig)application.getAttribute("entityConfig");
	
			String loreId = request.getParameter("loreId");
			String loreDirId = request.getParameter("loreDirId");
			String titleVal = java.net.URLDecoder.decode(fixnull(request.getParameter("titleVal")),"UTF-8");
			String pageInt = request.getParameter("pageInt");
			
			String type_str = TestQuestionType.typeShow(type);
			
			int charCode = (coreList.size()-2)/2;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>多选题</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script language=javascript src="../js/check.js"></script>
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
<script>
function resetAll() {
	document.question.reset();
	document.question.diff.value="";
	document.question.cognizetype.value="";
	document.question.referencescore.value="";
	document.question.title.value="";
	var options=document.getElementsByName("options");
	for(i=0;i<options.length;i++)
	{
		options[i].value="";
	}
	
	var oEditor = FCKeditorAPI.GetInstance('body') ;
	oEditor.SetData('');	
}

	var bLoaded=false; 
	
	function pageGuarding()	{
/*		if(currentflag==1) {//处于普通编辑模式
			document.question.body.value=document.frames.editor.frames.edit1.textEdit.document.body.innerHTML;
		} else {
			if (currentflag==3) {//处于预浏览先通编辑模式
				document.question.body.value=oDiv.innerHTML;
			}
		}
		document.question.body.value=Absolute2Relative(document.question.body.value);//替换绝对路径
		if (bLoaded==false) {
			alert("表单正在下载")
			return false;
		}
*/				
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
		//alert("11");
		//alert(document.question.body.value);
		
		
		//判断fckeditor 的内容是否为空
	   /*
	   var oEditor = FCKeditorAPI.GetInstance('body') ;
       var acontent=oEditor.GetXHTML();
       if(acontent==""){
			alert("对不起，题目内容不能为空！");
			document.question.body.focus();
			return;
		}
		*/
       	if(document.question.body.value==""){
			alert("对不起，题目内容不能为空！");
			document.question.body.focus();
			return;
		}
		
		if(document.question.title.value.indexOf("'")!="-1")
		{
			alert("对不起，题目名称不能包含英文单引号！");
			document.question.title.focus();
			return;
		}
		
		
		var count=0;
		for(var j=0;j <document.question.answer.length;j++)
			{  
				if(document.question.answer[j].checked)
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
		
		
		if(document.getElementById("optionsA").value==""||document.getElementById("optionsB").value=="")		
	{
		alert("选项A和B答案内容不能为空。"); return;
	}
	
		document.question.submit();
		
	}
	
	//var intRowIndex = 3;
	//var charCode = 67;
	var intRowIndex = 1 + <%=charCode%>;
	var charCode = 65 + <%=charCode%>;
	function insertRow(){
			if(charCode<74){
				//添加一行			
				var newTr = Tbl.insertRow(intRowIndex);
				
				//添加两列			
				var newTd0 = newTr.insertCell();			
				var newTd1 = newTr.insertCell();
				
				//设置列内容和属性
				newTd0.align = 'center';
				newTd0.className = 'text6';			
				newTd0.innerHTML = '选项' + String.fromCharCode(charCode) 
				   + '<input type="checkbox" name="answer" value="' + String.fromCharCode(charCode) + '">';	
				newTd1.innerHTML = '<table width="100%" border="0" cellspacing="0" cellpadding="0"><tr>'
	               + '<td width="82%"><input name="options" type="text" value="" size="80" maxlength="80"></td>'
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
	
	function deleteRow() {
		if(intRowIndex < 4) {
			alert("选项不能少于两个！");
			return;
		} else {
			Tbl.deleteRow(intRowIndex - 1);
			intRowIndex -= 1;
			charCode -= 1;
		}
	}
</script>
</head>

<body leftmargin="0" topmargin="0">
<form name="question" method="post" action="store_question_multi_editexe.jsp" onsubmit="return pageGuarding();">
<INPUT type="hidden" name="id" value="<%=id%>">
<INPUT type="hidden" name="loreId" value="<%=loreId%>">
<INPUT type="hidden" name="loreDirId" value="<%=loreDirId%>">
<INPUT type="hidden" name="titleVal" value="<%=java.net.URLEncoder.encode(titleVal,"UTF-8")%>">
<INPUT type="hidden" name="pageInt" value="<%=pageInt%>">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
          <td width="3%"><br/></td>
          <td width="10%" class="text6"><br/></td>
          <td width="4%" class="text6"> <br/> 
          </td>
          <td class="text6"><br/></td>
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
          <td><br/></td>
          <td class="text6"></td>
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
          <td><input name="referencetime" type="hidden" value="10"></td>
          <td>&nbsp;</td>
          <td><br/></td>
          <td class="text6"></td>
        </tr>
        <tr> 
          <td height="3" colspan="7" background="images/ct_08.gif"> </td>
        </tr>
        <tr> 
          <td align="center"><img src="images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">题目名称：</td>
          <td class="text6"><input name="title" type="text" size="10" maxlength="15" value="<%=title%>">(不能包含英文单引号)</td>
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
    <td height="41" background="images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3">试题内容编辑区<!-- （"Shift+Enter"=换行,"Enter"=分段） --></td>
  </tr>
  <tr> 
    <td><table width="90%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolordark="#999999" bordercolorlight="#FFFFFF">
        <tr>
          <td align="center">
<!--          	  <img src="images/1.gif" width="730" height="417">-->
          	  <textarea name=body class=selfScale rows="22" cols="140"><%=bodyString%></textarea>
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
		<%
			for(int i=1; i<coreList.size()-2; i=i+2) {
				String index = (String)coreList.get(i);
				String content = (String)coreList.get(i+1);
				WhatyStrManage strManage = new WhatyStrManage();
				strManage.setString(content);
				//out.print(index+"、"+content);
		%>
        <tr> 
          <td align="center" class="text6">选项<%=index%><input type="checkbox" name="answer" value="<%=index%>" <%if(answer.indexOf(index)>=0){%>checked<%}%>></td>
          <td height="35"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="82%"><input name="options" id="options<%=index%>" type="text" size="80" maxlength="80" value="<%=strManage.htmlEncode()%>"></td>
                <td style="padding-left:5px"><br/></td>
				<td valign="bottom" style="padding-left:5px"></td>

              </tr>
            </table>
          </td>
        </tr>
        <%
        	}
        %>
        
      </table></td>
  </tr>
  <tr> 
    <td height="65" align="right" style="padding-right:50px"> 
      <table width="50%" border="0" cellpadding="0" cellspacing="0">
        <tr>
<%--      <td><input type="image" src="images/bcfh.gif" width="100" height="30" border="0"></td> --%> 
          <td><a href="#" onclick="javascript:pageGuarding()"><img src="images/bcfh.gif" width="100" height="30" border="0"></a></td>
          <td><a href="lore_store_question_lore.jsp?id=<%=loreId%>&loreDirId=<%=loreDirId%>&title=<%=java.net.URLEncoder.encode(titleVal,"UTF-8") %>&pageInt=<%=pageInt %>"><img src="images/zjfh.gif" width="100" height="30" border="0"></a></td>
        <!--  <td><a href="#" onclick="resetAll();"><img src="images/cxlr.gif" width="105" height="30" border="0"></a></td>-->
    <%--    <td><a href="store_question_multi_edit.jsp?id=<%=id%>&loreId=<%=loreId%>&loreDirId=<%=loreDirId%>&titleVal=<%=java.net.URLEncoder.encode(titleVal,"UTF-8") %>&pageInt=<%=pageInt %>" ><img src="images/cxlr.gif" width="105" height="30" border="0"></a></td>
     <td><a href="#"><img src="images/xyt.gif" width="105" height="30" border="0"></a></td> --%>
        </tr>
      </table>
    </td>
  </tr>
</table>
<script>bLoaded=true;</script>
</form>
<form action="" target=_blank name="edit" method=post>
 	<input type=hidden name="content">
 </form>
<script>bLoaded=true;</script>
<script>
	function editOptions(b)
	{
		var opt = b;
		document.edit.content.value=document.getElementById(opt).value;
		document.edit.action="editor.jsp?comp="+opt;
		document.edit.submit();
	}
</script>
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
</body>
<%
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
</html>
