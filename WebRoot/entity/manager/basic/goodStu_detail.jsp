<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%>
<%@page import="com.whaty.platform.entity.bean.PeBzzGoodStu"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>优秀学员上报信息</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="entity/manager/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1">
<script language="javascript" src="/entity/manager/js/check.js"></script>		
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
</head>
<body leftmargin="0" topmargin="0" class="scllbar" >
  <form name ="addNews" method="post" enctype="multipart/form-data">
  <%@ include file="/web/form-token.jsp" %>
<table width="86%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">优秀学员上报信息</div></td>
  </tr>
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 
        <div class="cntent_k" id="zlb_content_start">
          <table width="85%" border="0" cellpadding="0" cellspacing="0">
          	<tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">学员姓名</span></td>
              <td width="626"> <s:property value="goodStu.peBzzStudent.trueName" escape="false"/> </td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">政治面貌</span></td>
              <td width="626"> <s:property value="goodStu.peBzzParty.name" escape="false"/></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">工&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄</span></td>
              <td width="626"> <s:property value="goodStu.work_age" escape="false"/></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">职务与工种</span></td>
              <td width="626"> <s:property value="goodStu.duty" escape="false"/></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称</span></td>
              <td width="626"> <s:property value="goodStu.professional" escape="false"/></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">工作单位</span></td>
              <td width="626"> <s:property value="goodStu.work_space" escape="false"/></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="top" width="90" nowrap><span class="name">相片一&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
              <td width="626">
              	<s:if test="goodStu.photo_one != null">
              	<span style="padding-top:3px; padding-left:4px; padding-right:4px; margin-bottom:1px; height:19px; color:#000000; font-size:9pt; border:1px solid #38819c;background-color:#f0f4f7;">
                <img src="/entity/manager/basic/goodStuMagImage/goodStu/<s:property value="goodStu.photo_one"/>" width='120' hight='174'/>
                </span>
              	</s:if>
              </td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="top" width="90" nowrap><span class="name">相片二&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
              <td width="626">
              	<s:if test="goodStu.photo_two != null">
              	<span style="padding-top:3px; padding-left:4px; padding-right:4px; margin-bottom:1px; height:19px; color:#000000; font-size:9pt; border:1px solid #38819c;background-color:#f0f4f7;">
                <img src="/entity/manager/basic/goodStuMagImage/goodStu/<s:property value="goodStu.photo_two"/>" width='120' hight='174'/>
                </span>
              	</s:if>
              </td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">审核状态</span></td>
              <td width="626"> <s:property value="goodStu.enumConstByFlaggoodState.name" escape="false"/></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">关注度&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
              <td width="626"> <s:property value="goodStu.regard_num" escape="false"/></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">录入人姓名</span></td>
              <td width="626"> <s:property value="goodStu.peManager.name" escape="false"/></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">录入时间</span></td>
              <td width="626"> <s:property value="goodStu.record_date" escape="false"/></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td  width="90" valign="top" nowrap><span class="name">个人感言</span></td>
              <td width="626">
              <textarea cols='70' rows='5'  class="smallarea" name="feeling_words" cols="70" rows="12" id="feeling_words" readonly ><s:property value="goodStu.feeling_words" escape="false"/></textarea><br/><br/>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td  width="90" valign="top" nowrap><span class="name">个人事迹</span></td>
              <td width="626"> 
              <textarea cols='70' rows='5'  class="smallarea" name="advanced_story" cols="70" rows="12" id="advanced_story" readonly ><s:property value="goodStu.advanced_story" escape="false"/></textarea><br/><br/>
              </td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td  width="90" valign="top" nowrap><span class="name">所获奖项</span></td>
              <td width="626"> 
              <textarea cols='70' rows='5'  class="smallarea" name="certificate" cols="70" rows="12" id="certificate" readonly ><s:property value="goodStu.certificate" escape="false"/></textarea><br/><br/>
              </td> 
            </tr>
            <tr>
       			<td colspan="2" align="center" valign="middle">
       			<s:if test=" !goodStu.enumConstByFlaggoodState.code.equals('002') " >
       				<a href="../manager/basic/goodStu_edit.jsp?goodStu_id=<s:property value="goodStu.id" escape="false"/>" title="修改" class="button">
	       			<span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px"  onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'">
	       				<span class="text">修改</span>       			
	       			</span>
	       			</a>
	       			<br/>
       			</s:if>
       			<a href="peBzzGoodStu.action?backParam=true" title="返回" class="button">
       			<span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px"  onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'">
       				<span class="text">返回</span>       			
       			</span>
       			</a>
       			</td>
       		</tr>
  
</table>
</form>
<script type="text/javascript"> 
var oFCKeditor = new FCKeditor( 'advanced_story' ) ; 
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

<script type="text/javascript"> 
var oFCKeditor = new FCKeditor( 'certificate' ) ; 
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



