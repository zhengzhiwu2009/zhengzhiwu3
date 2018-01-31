<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.lore.*"%>
<%@ page import="com.whaty.platform.test.TestManage,com.whaty.util.string.*" %>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals("null"))
			str = "";
			return str;
	}
%>
<%
			Map questionMap = new HashMap();
			if(request.getAttribute("questionMap") !=null){
				questionMap = (HashMap)request.getAttribute("questionMap");
			}
			
			String id = (String)questionMap.get("id");
			String title = (String)questionMap.get("title");
				
			List coreList = (ArrayList)questionMap.get("coreList");
			String bodyString = (String)coreList.get(0);
			String answer = (String)coreList.get(coreList.size()-1);
			int charCode = (coreList.size()-1)/2;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>单选题</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
<script language=javascript src="/entity/manager/js/check.js"></script>
</head>
<body leftmargin="0" topmargin="0">

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


		if(document.getElementById("optionsA").value==""||document.getElementById("optionsB").value=="")		
	{
		alert("选项A和B不能为空。"); return;
	}
		
		document.question.submit();
	}
	
	//var intRowIndex = 3;
	//var charCode = 67;
	var intRowIndex = 1 + <%=charCode%>;
	var charCode = 65 + <%=charCode%>;
	function insertRow(){
			//添加一行			
			var newTr = Tbl.insertRow(intRowIndex);
			
			//添加两列			
			var newTd0 = newTr.insertCell();			
			var newTd1 = newTr.insertCell();
			
			//设置列内容和属性
			newTd0.align = 'center';
			newTd0.className = 'text6';			
			newTd0.innerHTML = '选项' + String.fromCharCode(charCode) ;
			newTd1.innerHTML = '<table width="100%" border="0" cellspacing="0" cellpadding="0"><tr>'
               + '<td width="82%"><input name="options" type="text" value="" size="80" maxlength="80"></td>'
               + '<td style="padding-left:5px"></td>'
			   + '<td valign="bottom" style="padding-left:5px"></td></tr></table>';
			
			//newTr.attachEvent("onclick",getIndex);
			//intRowIndex = newTr.rowIndex;
			intRowIndex = intRowIndex + 1;
			charCode = charCode + 1;
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
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<form name="question" id="form1" method="post" action="/entity/teaching/satisfactionQuestionManager_updateQuestion.action">
<INPUT type="hidden" name="bean.id" value="<%=id%>">
  <tr> 
    <td height="41" background="/entity/manager/teaching/satisfaction/images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3">试题属性选择区</td>
  </tr>
  <tr> 
    <td><table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
        
        <tr> 
          <td height="3" colspan="3" align="center" background="images/ct_08.gif"> 
          </td>
        </tr>
        <tr> 
          <td height="3" colspan="3" background="/entity/manager/teaching/satisfaction/images/ct_08.gif"> </td>
        </tr>
        <tr> 
          <td align="center"><img src="/entity/manager/teaching/satisfaction/images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">题目名称：</td>
          <td class="text6"><input name="title" type="text" size="10" maxlength="15" value="<%=title%>">(不能包含英文单引号)</td>
        </tr>
        <tr> 
          <td height="3" colspan="3" align="center" background="/entity/manager/teaching/satisfaction/images/ct_08.gif"> 
          </td>
        </tr>
      </table>
      <br/></td>
  </tr>
  <tr> 
    <td height="41" background="/entity/manager/teaching/satisfaction/images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3">试题内容编辑区（"Shift+Enter"=换行,"Enter"=分段）</td>
  </tr>
  <tr> 
    <td><table width="90%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolordark="#999999" bordercolorlight="#FFFFFF">
        <tr>
          <td align="center">
<!--          	  <img src="images/1.gif" width="730" height="417">-->
          	  <textarea name=body class=selfScale rows="22" cols="72"><%=bodyString%></textarea>
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
			if(coreList.size()>=1)
			{
				for(int i=1; i<coreList.size(); i=i+2) {
					String index = (String)coreList.get(i);
					String content = (String)coreList.get(i+1);
					//out.print(index+"、"+content);
					WhatyStrManage strManage = new WhatyStrManage();
					strManage.setString(content);
		%>
        <tr> 
          <td align="center" class="text6">选项<%=index%></td>
          <td height="35"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="82%"><input name="options" id="options<%=index %>" type="text" size="80" value="<%=strManage.htmlEncode()%>"></td>
                <td style="padding-left:5px"></td>
				<td valign="bottom" style="padding-left:5px">&nbsp;</td>

              </tr>
            </table>
          </td>
        </tr>
        <%
        		}
        	}
        %>
        
      </table></td>
  </tr>
   <tr> 
    <td height="65" align="center" style=""> 
    	<a href="#" onclick="javascript:pageGuarding()"><img src="/entity/manager/images/fs1.gif" width="80" height="25" border="0"></a>
    	&nbsp;&nbsp;&nbsp;&nbsp;
    	<a href="/entity/teaching/satisfactionQuestionManager.action"><img src="/entity/manager/images/fh.gif" width="80" height="25" border="0"></a>
    </td>
  </tr> 
</table>
 </form> 

<script>bLoaded=true;</script>
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


oFCKeditor.Value = '' ; 
oFCKeditor.ReplaceTextarea() ; 
//--> 
</script> 
</html>