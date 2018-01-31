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

<%!
   String fixnull1(String str){ 
   		if(str==null || str.equals("null") || str.equals("")){
   			str= "";
   			return str;
   		}
   		return str;
   }
%>
<script  language="javascript">
function cfmdel(link)
{
	if(confirm("您确定要删除吗？"))
		window.navigate(link);
}
function cfmOp() {
	return confirm("真的要修改吗");
}
</script>

<%
	String question_id = request.getParameter("question_id");
	
	String exambatch_id = fixnull(request.getParameter("exambatch_id"));
	String question_type_search = fixnull(request.getParameter("question_type"));
	String is_solve=fixnull(request.getParameter("is_solve"));
	String title_search=fixnull(request.getParameter("title_search"));
	
	
	//UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	
	//String student_id = fixnull(student.getId());
	
	dbpool db1 = new dbpool();
	db1.executeUpdate("update exam_QUESTION_INFO set click_num=click_num+1 where id='"+question_id+"'");
	
	dbpool db = new dbpool();
	
	 
    
    MyResultSet rs_user = null;
    String userId="";
    String sql_user=
		"select t.id, t.name\n" +
		"  from pe_manager t\n" + 
		" where t.fk_sso_user_id = '"+us.getId()+"'";
    rs_user = db.executeQuery(sql_user);
    if(rs_user!=null && rs_user.next())
    {
    	userId = fixnull(rs_user.getString("id"));
    }
    db.close(rs_user);
    
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
	var bLoaded=false; 
	function chkSubmit()
	{
		
		if (bLoaded==false)
		{
			alert("表单正在下载")
			return false
		}		
		
	   var oEditor = FCKeditorAPI.GetInstance('body') ;
       var acontent=oEditor.GetXHTML();
		if(acontent.length <2)
		{
			alert("内容为空，还是多写几句吧？");
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
                              <strong>问题详细</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td background="/entity/function/mages/table_02.gif" valign="top">
                      <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
        <tr>
          <td background="/entity/bzz-students/exam/images/wyjd_03.jpg" height="2"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tw_bian">
        <tr>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="tw_biaot">
              <tr>
                <td><img src="/entity/bzz-students/exam/images/wyjd_13.jpg" width="16" height="16" align="absmiddle" class="img" />
                <span class="fddy_sousuo_font" style="padding-left:12px;"><%=question_title %></span></td>
              </tr>
              <tr>
              	<td>&nbsp;
              	</td>
              </tr>
              <tr>
                <td><font size="2">
                <%=question_body%></font></td>
              </tr>
            </table>
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="tw_wenz">
              <tr>
              <%if(question_submituser_id.equals(userId)){ %>
               <td><div class="cwzj"><a href="/entity/manager/exam/exam_answer_edit.jsp?question_id=<%=question_id %>&exambatch_id=<%= exambatch_id %>&question_type=<%= question_type_search %>&is_solve=<%= is_solve %>&title_search=<%= title_search %>">修改问题</a></div></td>
               <%} %>
                <td>提问时间：<%=question_publishdate %> | 提问者：<span class="lanz"><%=question_submituser_name %></span> </td>
              </tr>
            </table></td>
        </tr>
      </table>
      <%
      	//官方回答
      	String sql_teacher = 
		"select id,\n" +
		"       question_id,\n" + 
		"       body,\n" + 
		"       publish_date,\n" + 
		"       publish_id,\n" + 
		"       publish_name,\n" + 
		"       publish_type,\n" + 
		"       is_key\n" + 
		"  from (select id,\n" + 
		"               question_id,\n" + 
		"               body,\n" + 
		"               to_char(publish_date, 'yyyy-mm-dd') as publish_date,\n" + 
		"               publish_id,\n" + 
		"               publish_name,\n" + 
		"               publish_type,\n" + 
		"               is_key\n" + 
		"          from exam_answer_info\n" + 
		"         where question_id = '"+question_id+"'\n" + 
		"           and publish_type = 'manager')";
       MyResultSet rs_answer = null;
	   String answer_body="";
	   String answer_date="";
	   String answer_id="";
	   String flag = "";
      	if(db.countselect(sql_teacher)>0)
      	{
      		flag="1";
      		rs_answer = db.executeQuery(sql_teacher);
      		while(rs_answer!=null&&rs_answer.next())
			{
				answer_id = fixnull(rs_answer.getString("id"));
			 	answer_body=fixnull(rs_answer.getString("body"));
			 	answer_date=fixnull(rs_answer.getString("publish_date"));
			} 
			db.close(rs_answer);
      %>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="lshd">
        <tr>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="lshd_biaot">
              <tr>
                <td><img src="/entity/bzz-students/exam/images/wyjd_23.jpg" width="23" height="32" align="absmiddle" class="img" />
                <span class="fddy_sousuo_font" style="padding-left:12px;">老师回答</span></td>
              </tr>
            </table>
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="lshd_biaot">
              <tr>
                <td class="lshd_heiz"><%=answer_body%></td>
              </tr>
              <tr>
                <td align="right" class="tw_wenz list_baitd">回答时间：<%=answer_date%></td>
              </tr>
            </table></td>
        </tr>
      </table>
      <%} %>
      <%
      	//最佳回答
      	String sql_key= 
		"select id,\n" +
		"       question_id,\n" + 
		"       body,\n" + 
		"       publish_date,\n" + 
		"       publish_id,\n" + 
		"       publish_name,\n" + 
		"       publish_type,\n" + 
		"       is_key\n" + 
		"  from (select id,\n" + 
		"               question_id,\n" + 
		"               body,\n" + 
		"               to_char(publish_date, 'yyyy-mm-dd') as publish_date,\n" + 
		"               publish_id,\n" + 
		"               publish_name,\n" + 
		"               publish_type,\n" + 
		"               is_key\n" + 
		"          from exam_answer_info\n" + 
		"         where question_id = '"+question_id+"'\n" + 
		"           and is_key = '1')";
       MyResultSet rs_key = null;
	   String key_body="";
	   String key_date="";
	   String key_submitname="";
      	if(db.countselect(sql_key)>0)
      	{
      		rs_key = db.executeQuery(sql_key);
      		while(rs_key!=null&&rs_key.next())
			{
			 	key_body=fixnull(rs_key.getString("body"));
			 	key_date=fixnull(rs_key.getString("publish_date"));
			 	key_submitname=fixnull(rs_key.getString("publish_name"));
			} 
			db.close(rs_key);
      %>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="zjda">
        <tr>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="lshd_biaot">
              <tr>
                <td><img src="/entity/bzz-students/exam/images/wyjd_28.jpg" width="23" height="33" align="absmiddle" class="img" />
                <span class="hangz" style="padding-left:12px;">最佳回答 </span></td>
              </tr>
            </table>
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="lshd_biaot">
              <tr>
                <td class="lshd_heiz"><%=key_body%></td>
              </tr>
              <tr>
                <td align="right" class="tw_wenz list_baitd">回答者：<%=key_submitname%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;回答时间：<%=key_date%></td>
              </tr>
            </table></td>
        </tr>
      </table>
      <%
      } 
        String sql_qt=
		"select id,\n" +
		"       question_id,\n" + 
		"       body,\n" + 
		"       publish_date,\n" + 
		"       publish_id,\n" + 
		"       publish_name,\n" + 
		"       publish_type,\n" + 
		"       is_key\n" + 
		"  from (select id,\n" + 
		"               question_id,\n" + 
		"               body,\n" + 
		"               to_char(publish_date, 'yyyy-mm-dd') as publish_date,\n" + 
		"               publish_id,\n" + 
		"               publish_name,\n" + 
		"               publish_type,\n" + 
		"               is_key\n" + 
		"          from exam_answer_info\n" + 
		"         where question_id = '"+question_id+"'\n" + 
		"           and publish_type = 'student'\n" + 
		"           and is_key = '0')";
		
		MyResultSet rs_qt = null;
        if(db.countselect(sql_qt)>0)//存在其他回答在此显示
        {
      %>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="qtda">
        <tr>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="tw_biaot">
              <tr>
                <td style="padding-bottom:17px"><img src="/entity/bzz-students/exam/images/wyjd_32.jpg" width="16" height="16" align="absmiddle" class="img" /><span class="heiz">其他回答</span><span class="tw_wenz">共<%=db.countselect(sql_qt) %>条</span></td>
              </tr>
            </table>
            <%
            	rs_qt = db.executeQuery(sql_qt);
            	
	      		while(rs_qt!=null&&rs_qt.next())
				{
					String qt_id = fixnull(rs_qt.getString("id"));
				 	String qt_body=fixnull(rs_qt.getString("body"));
				 	String qt_date=fixnull(rs_qt.getString("publish_date"));
				 	String qt_submitname=fixnull(rs_qt.getString("publish_name"));
				
            %>
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="lshd_biaot">
              <tr>
                <td colspan="2" class="lshd_heiz"><%=qt_body %></td>
              </tr>
              <tr style="padding:10px 0px;">
                <%if(db.countselect(sql_key)<1) {//没有最佳答案的时候设置最佳答案，最佳答案只有一个%>
                <td class="list_baitd"><div class="cwzj"><a href="/entity/manager/exam/exam_answer_iskey.jsp?answer_id=<%=qt_id%>&exambatch_id=<%= exambatch_id %>&question_type=<%= question_type_search %>&is_solve=<%= is_solve %>&title_search=<%= title_search %>">成为最佳答案</a></div></td>
                <%}
                  else {%>
                  <td class="list_baitd">&nbsp;</td>
                  <%} %>
                <td class="list_baitd"><img src="/entity/bzz-students/exam/images/wyjd_33.jpg" width="14" height="12" align="absmiddle" class="img" />
                <span class="qt_wenz" style="padding-left:12px;">回答者：</span><span class="lanz"><%=qt_submitname %></span><span class="qt_wenz"> | <%=qt_date%></span></td>
              </tr>
            </table>
           <%}
              db.close(rs_qt); %>
            </td>
        </tr>
      </table>
      <%} %>
      <form action="exam_answer_addexe.jsp?question_id=<%=question_id%>&flag=<%=flag %>&answer_id=<%=answer_id %>&exambatch_id=<%= exambatch_id %>&question_type_search=<%= question_type_search %>&is_solve=<%= is_solve %>&title_search=<%= title_search %>" method="POST" name="frmAnnounce" onsubmit="return chkSubmit()">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="jd">
        <tr>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="jd_biaot">
            <tr>
              <td>我要解答：</td>
            </tr>
          </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="jd_biaot">
              <tr>
                <td>
                <textarea class="smallarea" cols="80" name="body" rows="12" ><%=answer_body %></textarea>
                </td>
              </tr>
            </table>
           <!--  <div class="jd_biaot1"><a href="javascript:document.frmAnnounce.submit();">提交回答</a></div> -->
           <tr>
           <td align="center">
           <input type=submit value="提交回答" id=sub>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           <input type="button" value="返回"  onClick="javascript:window.location.href='/entity/manager/exam/question_list.jsp?exambatch_id=<%= exambatch_id %>&question_type=<%= question_type_search %>&is_solve=<%= is_solve %>&title_search=<%= title_search %>'">
           </td></tr>
            </td>
        </tr>
      </table>
      </form>
      </td>
					<td style="padding-left:10px;" valign="top" width="500"><table width="95%" border="0" cellspacing="0" cellpadding="0" class="xgwt">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="xgwt_bt">
          <tr>
            <td style="padding-left:12px;"><img src="/entity/bzz-students/exam/images/wyjd_10.jpg" width="16" height="16" align="absmiddle"><span class="heiz" style="padding-left:12px;">相关问题</span></td>
          </tr>
        </table>
          <table width="96%" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;">
          <% 
		    String sql_xg =
				"select id, title, click_num\n" +
				"  from (select eqi.id, eqi.title, eqt.id as questiontype_id, click_num\n" + 
				"          from exam_question_info eqi, exam_question_type eqt\n" + 
				"         where eqi.question_type = eqt.id(+)\n" + 
				"           and eqi.exambatch_id = '"+question_exambatch+"'\n" + 
				"           and eqt.name = '"+question_type+"'\n" + 
				"           and eqi.id <> '"+question_id+"')\n" + 
				" order by click_num desc";
			int totalItems = db.countselect(sql_xg);
			//----------分页开始---------------
			//String spagesize = (String) session.getValue("pagesize");
			String spagesize = "15";
			String spageInt = request.getParameter("pageInt");
			Page pageover = new Page();
			pageover.setTotalnum(totalItems);
			pageover.setPageSize(spagesize);
			pageover.setPageInt(spageInt);
			int pageNext = pageover.getPageNext();
			int pageLast = pageover.getPagePre();
			int maxPage = pageover.getMaxPage();
			int pageInt = pageover.getPageInt();
			int pagesize = pageover.getPageSize();
			String link ="&question_exambatch="+question_exambatch+"&exambatch_id="+exambatch_id+"&question_type="+question_type_search+"&is_solve="+is_solve+"&title_search="+title_search+"&question_id="+question_id;
			//----------分页结束---------------		
			MyResultSet rs_xg = null;
			rs_xg=db.execute_oracle_page(sql_xg,pageInt,pagesize);
			int t=0;
			while(rs_xg!=null && rs_xg.next())
				{
					String questionId=fixnull(rs_xg.getString("id"));
					String questionTitle=fixnull(rs_xg.getString("title"));
					t++;
					
				%>
            <tr>
              <td class="xgwt_wz"><a href="/entity/manager/exam/exam_answer_info.jsp?question_id=<%=questionId%>&exambatch_id=<%= exambatch_id %>&question_type=<%= question_type_search %>&is_solve=<%= is_solve %>&title_search=<%= title_search %>">
              <%=subTitle(questionTitle,17)%></a></td>
            </tr>
            <%}
            db.close(rs_xg); 
            if(t<15)
			{
				for(int j=0;j<15-t;j++)
				{
			%>          
			         <tr>
					  <td class="xgwt_wz">&nbsp;</td>   
					  </tr>                       		
			<%
				}
			}
            %>
           <tr>
					  <td class="content">
					  <%@ include file="../pub/dividepage_fddy_detail.jsp"%>
					  </td>
					</tr>
          </table>
          </td>
      </tr>
    </table></td>
  </tr>
</table>
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
