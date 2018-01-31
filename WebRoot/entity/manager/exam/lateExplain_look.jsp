<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改缓考说明</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="entity/manager/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1">
<script language="javascript" src="/entity/manager/js/check.js"></script>		
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
</head>
<script type="text/javascript">
	function doCommit(){
	
	var content;
		if(FCKeditor_IsCompatibleBrowser()){
			var oEditor = FCKeditorAPI.GetInstance("late_explain"); 
			content=oEditor.GetXHTML(); 
		}else{
			alert("表单正在下载");
			return false;
		}
		var flag = true;
		if(content.length<1){
			window.alert("缓考说明内容不能为空!");
			flag = false;
			window.focus();
			return false;
		}
		
		if(flag){
			var action="/entity/exam/peBzzExamBatch_modifyLateExplain.action?backParam=true";
			document.addNews.action=action;
			document.addNews.submit();
		}
	}
</script>
<body leftmargin="0" topmargin="0" class="scllbar">
  <form name ="addNews" method="post" enctype="multipart/form-data">
  <%@ include file="/web/form-token.jsp" %>
<table width="86%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">查看缓考说明</div></td>
  </tr>
  <input type="hidden" name="id" value="<s:property value="peBzzExamBatch.id" />">
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 
        <div class="cntent_k" id="zlb_content_start">
          <table width="85%" border="0" cellpadding="0" cellspacing="0">
             <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">缓考说明内容</span></td>
              <td> 
              <textarea class="smallarea"  name="late_explain" cols="70" rows="12" id="late_explain"><s:property value='peBzzExamBatch.late_explain'/></textarea>
			  </td>
            </tr>
          </table> 
      </div>
   </td>
  </tr>
 <tr> 
    <td align="center" id="pageBottomBorder_zlb"><div class="content_bottom"> <table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0"><tr>
      	<td width="60">&nbsp;</td>
       <td  align="center"  valign="middle" width="150">
       <a href='#' title='返回' class='button' ><span unselectable='on' class='norm'  style='width:46px;height:15px;padding-top:3px' onclick="window.navigate('/entity/exam/peBzzExamBatch.action?backParam=true')" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'"><span class='text'>返回</span></span></a>
       	</td>             
       	</tr>
      </table></td>
  </tr>
</table>
</form>
<script type="text/javascript"> 


var oFCKeditor = new FCKeditor( 'late_explain' ) ; 
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
<script>bLoaded=true;</script>

</body>

</html>

