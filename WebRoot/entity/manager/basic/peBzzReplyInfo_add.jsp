<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>解答反馈</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="entity/manager/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1">
<script language="javascript" src="/entity/manager/js/check.js"></script>		
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
<script type="text/javascript">
	function doCommit(){
		if(document.pbri_add.content == null){
			alert("回复内容不能为空！");
		}
		document.pbri_add.submit();
	}
</script>
</head>
<body leftmargin="0" topmargin="0" class="scllbar"  >
  <form name='pbri_add' method='post' class='nomargin' action="<%=basePath %>entity/basic/peBzzReplyInfo_addReplyexe.action">
<table width="86%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">反馈回复</div></td>
  </tr>

  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 
<input type="hidden" name="feedback_id" value="<s:property value='peBzzFeedbackInfo.id'/>"/>
        <div class="cntent_k" id="zlb_content_start">
          <table width="85%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap>反馈标题：</td>
              <td width="626">  <s:property value="peBzzFeedbackInfo.title"/></td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom">发布人：</td>
              <td> <s:property value="peBzzFeedbackInfo.author"/></td>
            </tr>         
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom">反馈时间：</td>
              <td> <s:date name="peBzzFeedbackInfo.addDate" format="yyyy-MM-dd hh:mm:ss"/></td>
            </tr>
            
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom">联系方式：</td>
              <td> <s:property value="peBzzFeedbackInfo.phone"/></td>
            </tr>
            
             <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom">反馈内容:</td>
              <td valign="bottom"><s:property escape='0' value='peBzzFeedbackInfo.content'/></td>
            </tr>
            
             <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">回复:</span></td>
              <td> 
              <textarea class="smallarea"  name="content" cols="70" rows="12"></textarea>
<script language="javascript" type="text/javascript">
function FCKeditor_OnComplete( editorInstance ) 
{ 
editorInstance.SwitchEditMode(); 
}
</script>
              
			  </td>
            </tr>
          </table> 
      </div>
   </td>
  </tr>
  <tr> 
    <td align="center" id="pageBottomBorder_zlb"><div class="content_bottom"> 
    <table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
       <td  align="center" valign="middle">
       	<a href="#" title="提交" class="button">
       		<span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onClick="doCommit()" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'">
       			<span class="text">提交</span>       			
       		</span>
       	</a>
       	</td>
       <td  align="center" id="back" valign="middle">
       <a href="<%=basePath %>entity/basic/peBzzFeedbackInfo.action?backParam=true" title="返回" class="button">
       		<span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'">
       			<span class="text">返回</span>       			
       		</span>
       	</a>
       	</td>            
       	</tr>
      </table></td>
  </tr>
</table>
</form>
<script type="text/javascript"> 


var oFCKeditor = new FCKeditor( 'content' ) ; 
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



