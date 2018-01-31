<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.info.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.answer.*"%>
<%@ page import="com.whaty.platform.*"%>
<%@ include file="./pub/priv.jsp"%>
<%
			String status = (String)session.getAttribute("mock_login");
			if(status != null && status.equals("1")){ 
		%>
			<script>alert('模拟学生登陆只能查看不能提问');window.history.back();</script>
		<%
			}
		%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
UserSession userSession = (UserSession)session.getAttribute("user_session");
if(userSession!=null)
{
}
else
{
%>
<script>
	window.alert("长时间未操作或操作异常，为了您的账号安全，请重新登录！");
	window.location = "/";
</script>
<%
return;
}
String user_id = userSession.getId();
String reg_no = userSession.getLoginId();
dbpool db = new dbpool();
MyResultSet rs = null;
String ask_sql = "select b.id,to_char(b.ask_start_date,'yyyy-mm-dd') as ask_start_date,to_char(b.ask_end_date,'yyyy-mm-dd') as ask_end_date from pe_bzz_batch b,pe_bzz_student s where s.fk_batch_id=b.id and s.reg_no='"+reg_no+"'";
String ask_end_date = "";
String ask_start_date = "";
rs = db.executeQuery(ask_sql);
while(rs!=null&&rs.next())
{
	ask_start_date = rs.getString("ask_start_date");
	ask_end_date = rs.getString("ask_end_date");
}
db.close(rs);
String check_sql1 = "select b.id from pe_bzz_batch b,pe_bzz_student s where s.fk_batch_id=b.id and s.reg_no='"+reg_no+"' and to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') between start_time and end_time";
if(db.countselect(check_sql1)<1)
{
%>
<script type="text/javascript">
	alert("现在不在学期时间范围内，不能提问。");
	window.history.back();
</script>
<%
	return;
}
String check_sql = "select b.id from pe_bzz_batch b,pe_bzz_student s where s.fk_batch_id=b.id and s.reg_no='"+reg_no+"' and to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') between ask_start_date and ask_end_date";
/*if(db.countselect(check_sql)<1)
{
%>
<script type="text/javascript">
	//alert("集中提问、答疑时间未到");
	//window.history.back();
</script>
<%
	//return;
}*/
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>答疑</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
</head>
<script language=javascript>
	var bLoaded=false; 
	function chkSubmit()
	{
		
		if (bLoaded==false)
		{
			alert("表单正在下载")
			return false
		}
		
		if(frmAnnounce.title.value.length <1)
		{
			alert("标题好像忘记写了!");
			return false;
		}
		
		if(frmAnnounce.key.value.length <1) {
			alert("关键字好像忘记写了！");
			return false;
		}
		
		if(frmAnnounce.type.value.length <1) {
			alert("问题类型好像忘记选择了！");
			return false;
		}
		
	   var oEditor = FCKeditorAPI.GetInstance('body') ;
       var acontent=oEditor.GetXHTML();
		
		if(acontent.length <2)
		{
			alert("问题内容为空，还是多写几句吧？");
			return false;
		}	
	}
</script>
<%
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
%>
<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<form action="question_addexe.jsp" method="POST" name="frmAnnounce" onSubmit="return chkSubmit()">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="../images/top_01.gif"><img src="../images/dy.gif" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="608" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
                    <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="100"  align="right" class="text1">提问主题*&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                  <td><input name="title" type="text" size="55" maxlength="50"></td>
                                  <td width="250" align="right"><img src="../images/gg2.gif" width="249" height="32"></td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr>
                            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="100"  align="right" class="text1">关&nbsp;键&nbsp;字*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                  <td><input name="key" type="text" size="55" maxlength="50"></td>
                                  <td width="250" align="right"><img src="../images/gg2.gif" width="249" height="32"></td>
                                </tr>
                              </table></td>
                          </tr>  
                          <tr>
                            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="100"  align="right" class="text1">提问分类*&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                  <td width="440">
                                  <select name="type" >
                                  <option >请选择...</option>
                                  <%
                                  	String sql="select id,name from interaction_question_type order by seq";
                                  	rs = db.executeQuery(sql);
                                  	while(rs.next()) {
                                  		String qtId = rs.getString("id");
                                  		String qtName = rs.getString("name");
                                  	%>
                                  		<option value="<%=qtId %>"><%=qtName %></option>
                                  	<%
                                  	}
                                  	db.close(rs);	
                                  %>
                                  </select></td>
                                  <td width="250" align="right"><img src="../images/gg2.gif" width="249" height="32"></td>
                                </tr>
                              </table></td>
                          </tr>                         
                          <tr>
                            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="65" align="right" class="text1">内容：</td> 
                                  <td align="right"><img src="../images/gg3.gif" width="147" height="42"></td>
                                </tr>
                              </table></td>
                          </tr>
						  <tr>
							<td colspan=2 class="neirong" valign=top>
							<textarea class="smallarea" cols="80" name="body" rows="12"></textarea>
							</td>
						  </tr>
						  <tr>
							<td colspan="2" align="center"><input type="image" name="Submit" src="../images/fs.gif" width="82" height="23">&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="javascript:history.back()"><img src="../images/fh.gif" width="82" height="23" border="0"></a></td>                                
						  </tr>
                        </table></td>
                    </tr>
                  </table> <br/>
                </td>
              </tr>

            </table></td>
        </tr>
</form>
</table>

      <br/>
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
