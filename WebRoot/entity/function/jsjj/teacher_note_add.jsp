<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="java.sql.Clob, java.io.*" %>
<%@ include file="../pub/priv.jsp"%>

<%!
   String fixnull(String str){
   		if(str==null || str.equals("null") || str.equals("")){
   			return "";
   		}
   		return str;
   }
%>
<%
	String sql = "SELECT STTI.SG_TTI_DISCRIPTION FROM SG_TRAIN_TERCHAER_INFO STTI JOIN PE_BZZ_TCH_COURSE PBTC ON PBTC.TEACHER_ID = STTI.SG_TTI_ID WHERE PBTC.ID = '" + courseId + "'";
	String description = "";
	dbpool db = new dbpool();
	MyResultSet mrs = db.execuQuery(sql);
	if (mrs.next()) {
		Clob clob = (Clob) mrs.getClob(1);
		if (clob != null && !"".equals(clob)) {
			Reader inStream = clob.getCharacterStream();
			char[] c = new char[(int) clob.length()];
			inStream.read(c);
			description = new String(c);
		}
	}
	db.close(mrs);
	String type = request.getParameter("type");
   try{
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>中国证券业协会</title>
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
</head>
<script language=javascript>
	var bLoaded=false; 
	function chkSubmit()
	{
	
		if(frmAnnounce.title.value.length ==0)
		{
			alert("请填写标题");
			return false;
		}
		
		 var oEditor = FCKeditorAPI.GetInstance('body') ;
       var acontent=oEditor.GetXHTML();
		
		if(acontent.length <2 || acontent == "")
		{
			alert("内容为空，还是多写几句吧？");
			return false;
		}	
		
		document.getElementById("sub").disabled = true;
	}
</script>
<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<form action="teacher_note_addexe.jsp" method="POST" name="frmAnnounce" onsubmit="return chkSubmit()">
<input type=hidden name=teachclass_id value=<%=teachclass_id %>>
<input type=hidden name=courseId value=<%=courseId %>>
<input type=hidden name=type value=<%=type %>>
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="45" valign="top" background="../images/top_01.gif"></td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="765" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="65" background="../images/table_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="../images/xb3.gif" width="17" height="15"> 
                              <strong>教师简介</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td background="../images/table_02.gif" ><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
                          <tr>
                            <td class="text2">
                            	<table border="0" align="center" cellpadding="0" cellspacing="0" width=90%>
                            <tr>
                            <td class="neirong" width=20% class="text1" nowrap="nowrap">
							题目：
							</td>
							<td class="neirong" valign=top>
							<input type=text name=title class=input1 size=60 maxlength="60">
							</td>
						  </tr>
                          	<tr>
                          	<td class="neirong" valign=top width=20% class="text1">
							内容：
							</td>
							<td valign=top>
							<textarea  cols="80" name="body" rows="12"><%=description %></textarea>
							</td>
						  </tr>
                            	</table>
							</td>
							</tr>
							
     </table></td>
    </tr>
    
        <tr>
		 <td><img src="../images/table_03.gif" width="765" height="21"></td>
                    </tr>
		<tr>
			<td align=center><input type=submit value="提交" id=sub>&nbsp;&nbsp;<input type=button value="返回" onclick="window.location='teachernote_list.jsp'"></td>
		</tr>
                  </table> </td>
              </tr>
            </table></td>
        </tr>
      </table> 
      </form>
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
<%
	}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
%>
