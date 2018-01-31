<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%>
<%@page import="com.whaty.platform.entity.bean.PeBzzGoodStu"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.whaty.platform.entity.service.basic.PeBzzGoodStuService"%>
<%@page import="com.whaty.platform.entity.service.GeneralService"%>
<%@page import="org.hibernate.criterion.DetachedCriteria"%>
<%@page import="com.whaty.platform.entity.bean.PeBzzParty"%>
<%@page import="org.hibernate.criterion.Criterion"%>
<%@page import="org.hibernate.criterion.Restrictions"%>
<%@page import="com.whaty.platform.entity.bean.EnumConst"%>
<%@page import="com.whaty.platform.entity.bean.PeManager"%>
<%@page import="com.whaty.platform.entity.bean.PeBzzGoodMag"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改最佳/优秀管理员上报信息</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="entity/manager/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1">
<script language="javascript" src="/entity/manager/js/check.js"></script>		
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
</head>
<script type="text/javascript">
	function doCommit(){
	
	var content;
		if(FCKeditor_IsCompatibleBrowser()){
			var oEditor = FCKeditorAPI.GetInstance("advanced_story"); 
			content=oEditor.GetXHTML(); 
		}else{
			alert("表单正在下载");
			return false;
		}
		
		if(FCKeditor_IsCompatibleBrowser()){
			var oEditor = FCKeditorAPI.GetInstance("certificate"); 
			content=oEditor.GetXHTML(); 
		}else{
			alert("表单正在下载");
			return false;
		}
		
	
		var flag = true;
		
		var photo = document.getElementById("upload");
		var photo_one = "";
		var photo_two = "";
		var certificate = "";
		
		if(photo != null && photo.length == 3){
			photo_one = photo[0].value;
			photo_two = photo[1].value;
			certificate = photo[2].value;
		}
		
		photo_one = photo_one.toLowerCase();
		photo_two = photo_two.toLowerCase();
		certificate = certificate.toLowerCase();
		
		var ptem =photo_one.split(".");
		var str ="jpg";
		if(photo_one.length>1){
			if(!(str.indexOf(ptem[1])>=0)){
				window.alert("相片一图片格式不符!");
				flag = false;
				window.focus();
				return false;
			}
		}
		
		ptem =photo_two.split(".");
		str ="jpg";
		if(photo_two.length>1){
			if(!(str.indexOf(ptem[1])>=0)){
				window.alert("相片二图片格式不符!");
				flag = false;
				window.focus();
				return false;
			}
		}
		
		if(flag){
			var action="/entity/basic/peBzzGoodMag_addGoodMag.action";
			document.addNews.action=action;
			document.addNews.submit();
		}
	}
	
	function changePhoto(item){
	
		if( item == "photo_one_flag" ){
			document.addNews.photo_one_flag.value = "true";
		}else if( item == "photo_two_flag" ){
			document.addNews.photo_two_flag.value = "true";
		}
		
	}
	
	function checkvaluelen(item,len,messlabel){
		if(item.value.length > len){
			item.value = item.value.substring(0,len);
		}
		document.getElementById(messlabel).innerText = item.value.length;
	}
	
</script>
<body leftmargin="0" topmargin="0" class="scllbar" >
  <form name ="addNews" method="post" enctype="multipart/form-data">
  <%@ include file="/web/form-token.jsp" %>
  
  <%! 
  	String fixNull(String str){
  		if(str==null || str.equalsIgnoreCase("null")) str = "";
  		return str.trim();
  	}
  	
  	String selected(String str1,String str2){
  		if(str1.equals(str2)){
  			return "selected";
  		}else{
  			return "";
  		}
  	}
  %>
  
  <% 
  	String goodMag_id = fixNull(request.getParameter("goodMag_id"));
  	PeBzzGoodMag goodMag = new PeBzzGoodMag();
  	
  	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
  	GeneralService generalService = (GeneralService)wac.getBean("generalService");
  	goodMag = (PeBzzGoodMag)generalService.getById(goodMag_id);
  	
  	List partyList = new ArrayList();
  	DetachedCriteria dc = DetachedCriteria.forClass(PeBzzParty.class);
  	dc.add(Restrictions.eq("enumConstByFlagPartyState.id","ccb2880a91dadc115011dadfcf26b0003"));
  	partyList = generalService.getList(dc);
  	
  	String mag_name = "";
  	String party_id = "";
  	String work_age = "";
  	String duty = "";
  	String professional = "";
  	String work_space = "";
  	String photo_one = "";
  	String photo_two = "";
  	String certificate = "";
  	String submit_status= "";
  	int regard_num = 0;
  	String record_user = "";
  	Date record_date;
  	String advanced_story = "";
  	String together_declarer = "";
  	String feeling_words = "";
  	
  	mag_name = fixNull(goodMag.getPeEnterpriseManager().getName());
  	
  	PeBzzParty party = goodMag.getPeBzzParty();
  	if(party != null){
  		party_id = fixNull(party.getId());
  	}
  	
  	work_age = fixNull(goodMag.getWork_age());
  	duty = fixNull(goodMag.getDuty());
  	professional = fixNull(goodMag.getProfessional());
  	work_space = fixNull(goodMag.getWork_space());
  	photo_one = fixNull(goodMag.getPhoto_one());
  	photo_two = fixNull(goodMag.getPhoto_two());
  	certificate = fixNull(goodMag.getCertificate());
  	feeling_words = fixNull(goodMag.getFeeling_words());
  	
  	EnumConst enum1 = goodMag.getEnumConstByFlaggoodState();
  	if(enum1 != null){
  		submit_status = fixNull(enum1.getName());
  	}
  	
  	regard_num = goodMag.getRegard_num();
  	
  	PeManager manager = goodMag.getPeManager();
  	if(manager != null){
  		record_user = fixNull(manager.getName());
  	}
  	
  	record_date = goodMag.getRecord_date();
  	advanced_story = fixNull(goodMag.getAdvanced_story());
  	together_declarer = fixNull(goodMag.getTogether_declarer());
  	
  %>
  
<table width="86%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">修改最佳/优秀管理员上报信息</div></td>
  </tr>

  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 
        <div class="cntent_k" id="zlb_content_start">
          <table width="85%" border="0" cellpadding="0" cellspacing="0">
          <input type="hidden" name="goodMag_id" value="<%=goodMag_id %>" >
          	<tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">管理员姓名</span></td>
              <td width="626"> <%=mag_name %> </td> 
            </tr>
            
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">政治面貌</span></td>
              <td width="626">
				<select name="party_id">
					<option value="">请选择</option>
				<% 
					for(int i=0;i<partyList.size();i++){
						%>
						<option value="<%=fixNull(((PeBzzParty)partyList.get(i)).getId()) %>" <%=selected(party_id,fixNull(((PeBzzParty)partyList.get(i)).getId())) %>  >
							<%=fixNull(((PeBzzParty)partyList.get(i)).getName()) %>
						</option>
						<%
					}
				%>
				</select>              
            </tr>
            
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">工&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄</span></td>
              <td width="626"> <input name="work_age" type="text" class="selfScale" id="work_age" size="50" 
              	onchange="javascript:if(!isNum2(this.value)){alert('格式不正确，应为数字型');this.value='<%=work_age %>';}" value="<%=work_age %>" maxlength="2" ></td> 
            </tr>
            
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">职务与工种</span></td>
              <td width="626"> <input name="duty" type="text" class="selfScale" id="duty" size="50" value="<%=duty %>" maxlength="25" ></td> 
            </tr>
            
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称</span></td>
              <td width="626"> <input name="professional" type="text" class="selfScale" id="professional" size="50" value="<%=professional %>" maxlength="25" ></td> 
            </tr>
          
          
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">工作单位</span></td>
              <td width="626"> <input name="work_space" type="text" class="selfScale" id="work_space" size="50" value="<%=work_space %>" maxlength="50" ></td> 
            </tr>
            
            
            <tr valign="bottom" class="postFormBox"> 
              <td valign="top" width="90" nowrap><span class="name">相片一&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
              <td width="626">
              	<%if(!photo_one.equals("")){
              	%>
              		<span style="padding-top:3px; padding-left:4px; padding-right:4px; margin-bottom:1px; height:19px; color:#000000; font-size:9pt; border:1px solid #38819c;background-color:#f0f4f7;">
                		<img src="/entity/manager/basic/goodStuMagImage/goodMag/<%=photo_one %>" width='120' hight='174'/>
                	</span>
              	<%
              	} %>
              	<input type="file" name="upload" contentEditable="false" onchange="changePhoto('photo_one_flag')" ><span><font size="2" color="red"> &nbsp;  &nbsp;格式: jpg</font></span>
              	<input type="hidden" id="photo_one_flag" name="photo_one_flag" value="false" >
              </td> 
            </tr>
            
            <tr valign="bottom" class="postFormBox"> 
              <td valign="top" width="90" nowrap><span class="name">相片二&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
              <td width="626">
              	<%if(!photo_two.equals("")){
              	%>
              		<span style="padding-top:3px; padding-left:4px; padding-right:4px; margin-bottom:1px; height:19px; color:#000000; font-size:9pt; border:1px solid #38819c;background-color:#f0f4f7;">
                		<img src="/entity/manager/basic/goodStuMagImage/goodMag/<%=photo_two %>" width='120' hight='174'/>
                	</span>
              	<%
              	} %>
              	<input type="file" name="upload" contentEditable="false" onchange="changePhoto('photo_two_flag')" ><span><font size="2" color="red"> &nbsp;  &nbsp;格式: jpg</font></span>
              	<input type="hidden" id="photo_two_flag" name="photo_two_flag" value="false" >
              </td> 
            </tr>
            
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">审核状态</span></td>
              <td width="626"><%=submit_status %> 
              </td> 
            </tr>
            
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">关注度&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
              <td width="626"> <%=regard_num %>
              </td> 
            </tr>
            
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">录入人姓名</span></td>
              <td width="626"><%=record_user %>
               </td> 
            </tr>
            
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">录入时间</span></td>
              <td width="626"><%=record_date %>
               </td> 
            </tr>
            
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">共同申报人</span></td>
              <td width="626"> <input name="together_declarer" type="text" class="selfScale" id="together_declarer" size="50" value="<%=together_declarer %>" maxlength="100" ></td> 
            </tr>

             <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">个人感言</span></td>
              <td> 
              	<textarea class="smallarea" name="feeling_words" cols="70" rows="12" id="feeling_words" onpropertychange="checkvaluelen(this,1000,'feeling_words_label')" ><%=feeling_words %></textarea><br/>
              	字数最长为1000，您已输入<label name="feeling_words_label" id="feeling_words_label" ><%=feeling_words.length() %></label>个字<br/><br/>
			  </td>
            </tr>
            
            <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">个人事迹</span></td>
              <td> 
              	<textarea class="smallarea" name="advanced_story" cols="70" rows="12" id="advanced_story"><%=advanced_story %></textarea><br/><br/>
			  </td>
            </tr>
            
            <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">所获奖项</span></td>
              <td> 
              	<textarea class="smallarea" name="certificate" cols="70" rows="12" id="certificate"><%=certificate %></textarea><br/><br/>
			  </td>
            </tr>
            
          </table> 
      </div>
   </td>
  </tr>
  <tr> 
    <td align="center" id="pageBottomBorder_zlb"><div class="content_bottom"> <table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0"><tr>
       <td  align="center" valign="middle">
       
       	<a href="#" title="提交" class="button">
       		<span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onClick="doCommit()" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'">
       			<span class="text">提交</span>       			
       		</span>
       	</a><br/>
       	
       	<a href="../../basic/peBzzGoodMag_viewDetail.action?bean.id=<%=goodMag_id %>" title="返回" class="button">
       		<span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px"  onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'">
       			<span class="text">返回</span>       			
       		</span>
       	</a>
       	
       	</td>
       <td  align="center" id="back" valign="middle">
       	</td>            
       	</tr>
      </table></td>
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



