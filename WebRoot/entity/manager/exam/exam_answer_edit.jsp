<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.user.*,com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@ page import = "com.whaty.platform.interaction.exam.*" %>
<%@ include file="./priv.jsp"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant" />

<%!
   String fixnull(String str){ 
   		if(str==null || str.equals("null") || str.equals("")){
   			str= "";
   			return str;
   		}
   		return str;
   }
%>
<%!
	public   static   String   subTitle(String   str,   int  len)   {   
          if   (str   ==   null)   {   
              return   "";   
          }   
          str   =   str.trim();   
          StringBuffer   r   =   new   StringBuffer();   
          int   l   =   str.length();   
          float   count   =   0;   
          for   (int   i   =   0;   i   <   l;   ++i)   {   
              char   c   =   str.charAt(i);   
              if   (c   >   255   ||   c   <   0)   {   
                  ++count;   
                  r.append(c);   
              }   
              else   {   
                  count   +=   0.5;   
                  r.append(c);   
              }   
              if   (count   >=   len)   {   
              	  if(i<l-1)
              	  {
              	  	r.append("...");
              	  }
                  break;   
              }   
          }   
          return   r.toString();   
      }

%> 	
<%
//String path = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//UserSession userSession = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

//String user_id = userSession.getId();
//String reg_no = userSession.getLoginId();

String exambatch_id = fixnull(request.getParameter("exambatch_id"));
String question_type_search = fixnull(request.getParameter("question_type"));
String is_solve=fixnull(request.getParameter("is_solve"));
String title_search=fixnull(request.getParameter("title_search"));



dbpool db = new dbpool();
MyResultSet rs = null;
MyResultSet rs_batch = null;

String question_id = fixnull(request.getParameter("question_id"));
String sql_question=
		"select id,\n" +
		"       title,\n" + 
		"       body,\n" + 
		"       publish_date,\n" + 
		"       exambatch_id,\n" + 
		"       submituser_id,\n" + 
		"       submituser_name,\n" + 
		"       submituser_type,\n" + 
		"       question_type\n" + 
		"  from (select eqi.id,\n" + 
		"               eqi.title,\n" + 
		"               body,\n" + 
		"               to_char(publish_date, 'yyyy-mm-dd') as publish_date,\n" + 
		"               exambatch_id,\n" + 
		"               submituser_id,\n" + 
		"               submituser_name,\n" + 
		"               submituser_type,\n" + 
		"               eqt.name as question_type\n" + 
		"          from exam_question_info eqi, exam_question_type eqt\n" + 
		"         where eqi.question_type = eqt.id(+)\n" + 
		"           and eqi.id = '"+question_id+"')";
		
	MyResultSet rs_question = null;
	rs_question = db.executeQuery(sql_question);
	String question_title="";
	String question_body="";
	String question_publishdate="";
	String question_submituser_name="";
	String question_submituser_id="";
	String question_exambatch = "";
	String question_type="";
	while(rs_question!=null&&rs_question.next())
	{
	 	question_title=fixnull(rs_question.getString("title"));
	 	question_body=fixnull(rs_question.getString("body"));
	 	question_publishdate=fixnull(rs_question.getString("publish_date"));
	 	question_submituser_name=fixnull(rs_question.getString("submituser_name"));
	 	question_exambatch=fixnull(rs_question.getString("exambatch_id"));
	 	question_type=fixnull(rs_question.getString("question_type"));
	 	question_submituser_id=fixnull(rs_question.getString("submituser_id"));
	} 
	db.close(rs_question);
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<link href="/entity/bzz-students/exam/style.css" rel="stylesheet" type="text/css" />
<link href="/entity/bzz-students/exam/wyjd.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/entity/bzz-students/exam/wsMenu.js"></script>
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
</head>
<script>
    function reload()
    {
    	document.tw.submit();
    }
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
		
	   var oEditor = FCKeditorAPI.GetInstance('body') ;
       var acontent=oEditor.GetXHTML();
		
		if(acontent.length <2)
		{
			alert("问题内容为空，还是多写几句吧？");
			return false;
		}	
	}
</script>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="45" valign="top" ></td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="1000" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="65"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="/entity/function/images/xb3.gif" width="17" height="15"> 
                              <strong>修改问题</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td background="/entity/function/mages/table_02.gif" >
							<form action="exam_answer_editexe.jsp?exambatch_id=<%= exambatch_id %>&question_type=<%= question_type_search %>&is_solve=<%= is_solve %>&title_search=<%= title_search %>" method="POST" name="frmAnnounce" onSubmit="return chkSubmit()">
							<input type="hidden" id="question_id" name="question_id" value="<%=question_id%>">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="fddy_sousuobg" style="margin-top:10px;">
							  <tr>
							    <td width="11%" class="fddy_sousuo_font"><img src="/entity/bzz-students/exam/images/xytw_07.jpg" align="absmiddle" />问题标题:</td>
							    <td width="89%" align="left"><input name="title" type="text" value=<%=question_title %> /></td>
							  </tr>
							</table>
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="list_zibg">
							  <tr>
							    <td style="padding:5px;"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="" style="margin-left:10px;">
							      <tr>
							        <td width="10%" valign="top" class="fddy_sousuo_font" style="padding-top:10px;"><img src="/entity/bzz-students/exam/images/xytw_10.jpg" align="absmiddle" /><span style="margin-left:12px;">问题内容:</span></td>
							        <td width="90%" align="left">
                                    <textarea class="smallarea" cols="80" name="body" rows="12"><%=question_body %></textarea>
                                    </td>
							      </tr>
							      <tr>
							        <td valign="top" class="fddy_sousuo_font">&nbsp;</td>
							        <!--  <td align="left">
							           <div class="jd_biaot1" >
							           	<a href="javascript:chkSubmit()"><font color="#a04400" style="font-weight:bold;font-size:14px;">提交回答</font></a>
							           </div>
									</td>-->
									
									<td align=center width="700">
									<input type=submit value="修改" id=sub>&nbsp;&nbsp;
									<input type="button" value="返回"  onClick="javascript:window.history.back(-1)">
							      </tr>
							    </table></td>
							  </tr>
							</table>
							</form>
						</td>
					</tr>
				     </table>
				     </td>
				    </tr>
        <tr>
		 <td></td>
                    </tr>
                  <tr>
		 <td><br/><br/></td>
                    </tr>
                 
                  </table> </td>
              </tr>
              	
            </table><td></td>
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