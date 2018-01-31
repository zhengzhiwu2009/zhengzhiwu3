<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.lore.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ include file="../pub/priv.jsp"%>
 
 <%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("") || str.equals("null"))
			str = "";
			return str;
	}
%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>知识点题库</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<%
	String loreDirId = fixnull(request.getParameter("loreDirId"));
	String id = fixnull(request.getParameter("id")); 
%>
<script type="text/javascript">
/*   function onSub(){
      var type = document.getElementById("type").value;
       var id = document.getElementById("id").value;
          var loreDirId = document.getElementById("loreDirId").value;
          var link = "lore_store_question_batch_add.jsp?id="+id+"&loreDirId="+loreDirId;
          if(type!="YUEDU")
       window.open (link, 'newwindow', ' top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=yes');
       else
       	alert("不能导入");
   }
  */ 
  

  
   function onSub(){
     var type = document.getElementById("type");   
//       var id = document.getElementById("id"); 
//          var loreDirId = document.getElementById("loreDirId");
//          var link = "lore_store_question_batch_add.jsp?id="+id+"&loreDirId="+loreDirId;
	 var link = "lore_store_question_batch_add.jsp?loreDirId="+<%=loreDirId%>;
          if(type!="YUEDU")
       window.location= link; //, '', ' top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=yes'";
       else
       	alert("不能导入");
   }

   function onDown1(){
//      var type = document.getElementById("type").value;
//      var id = document.getElementById("id").value;
	  var link="";
      if(type=="DANXUAN")
//     	link = "lore_list_excel1.jsp?id=" + id;
    	link = "lore_list_excel1.jsp";
      if(type=="DUOXUAN")
      	link = "lore_list_excel2.jsp";
      if(type=="WENDA")
      	link = "lore_list_excel4.jsp";
       if(type=="PANDUAN")
      	link = "lore_list_excel3.jsp";
       if(type=="TIANKONG")
      	link = "lore_list_excel5.jsp";
       if(type!="YUEDU")		
	       window.open (link);
	   else
	   	   alert("不能导出");
   }

 function onDown(){
     var type = document.getElementById("type");
//        var id = document.getElementById("id");
  //        var loreDirId = document.getElementById("loreDirId");
          
          var link = "download.jsp?loreDirId="+<%=loreDirId%> ;
          if(type!="YUEDU")
       window.open (link, 'newwindow', ' top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=yes');
       else
       　	alert("不能导出");
   }
 
 
</script>
<script type="text/javascript">
    function listSelect(listId){

		var form = document.forms[listId];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'idaaa')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.listSelectAll.checked;
				}
			}
		}
		return true;
	}
  

function deleteaa() {

var x=document.getElementsByName("idaaa");
var issel=false;
for(var i=0;i<x.length;i++)
{
if(x[i].checked) 
{
issel=true;

} 
}
 if(issel==false){
 alert("请选择要删除的题目！");
 return;
 }

 if(confirm("您确定要删除吗")){
 
document.user.submit();
 }
   
	
}
function download() {
	var x=document.getElementsByName("idaaa");
	var issel=false;
	for(var i=0;i<x.length;i++) {
		if(x[i].checked) {
			issel=true;
		} 
	}
 	if(issel==false){
 		alert("请选择要下载的题目！");
 		return;
 	}
 	document.user.action = "store_question_batch_download.jsp";
 	document.user.submit();
}
function checkTitle()
{
	if(document.search.title.value!="" && document.search.title.value.indexOf("'")!="-1")
	{
		alert("对不起，搜索内容不能包含英文单引号！");
		document.search.title.focus();
		return false;
	}
}
</script>
</head>
<%
//	String loreDirId = request.getParameter("loreDirId");
//	String id = request.getParameter("id");
	String titleVal = java.net.URLDecoder.decode(fixnull(request.getParameter("title")),"UTF-8");
	String types = fixnull(request.getParameter("type"));  
	String type = "";
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		String questionDetail="";
%>

<body leftmargin="0" topmargin="0"  background="images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="images/top_01.gif">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="217" height="104" rowspan="2" valign="top"><img src="images/tk_bak.gif" width="217" height="86"></td>
                      <td height="46">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td align="left" valign="top">
                      	<form name="search" action="lore_store_question_lore.jsp" method="post" onsubmit="return checkTitle();">
                      	<input type="hidden" name="id" value="<%=id %>">
                      	<input type="hidden" name="loreDirId" value="<%=loreDirId %>">
                        <table width="90%" border="0" cellpadding="0" cellspacing="0">
                          <tr>                            
                            <td align="center"><img src="images/xb.gif" width="48" height="32"></td>
                            <td align="center" width="30%" class="mc1">按名称搜索：</td>
                            <td align="center"><input name="title" type="text" size="20" maxlength="50" value=<%=titleVal %>></td>
                            <td align="center"><input type="image" src="images/search.gif" width="99" height="19"></td>
                          
                            <td align="center" width="50%" class="mc1" >
								<div id='link' style="display:none">
								<!-- 
								<a href="#"  onclick="onSub();" >[批量导入]</a>
								<a href="store_question_frame.jsp?loreId=<%=id%>&loreDirId=<%=loreDirId%>" target=_blank>[添加题目]</a>
								<a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>">[返回知识点]</a>
								<a href="#" onclick="javascript:onDown()">[批量导出]</a>
							-->
								 </div>
								 <div></div>
								 </td>
							  	
                          </tr>
                        </table>
                        </form>
                      </td>
                      <td height="46">&nbsp;</td>
                    </tr>
                </table>
                </td>
              </tr>
              <form name="user"  action="store_question_delexe.jsp" method="post">
              <input type="hidden" name="loreId" value="<%=id%>">
               	<input type="hidden" name="loreDirId" value="<%=loreDirId%>">
              <tr>
                <td align="center">
                
			<table width="812" border="0" cellspacing="0" cellpadding="0">
              <tr>
                      
                <td height="26" background="images/tabletop.gif">
	              <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">				
                    <tr>
                     
                      <td  class='select' width="5%" align='center'>
                      <input type='checkbox' class='checkbox' name='listSelectAll' value='true'  onclick="listSelect('user')"/>
                       </td>
                      <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="5%" align="center" class="title">名称</td>
                       <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="24%" align="center" class="title">内容</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
					  <!--  
                      <td width="10%" align="center" class="title">知识点</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
					  --> 
                      <td width="12%" align="center" class="title">创建时间</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="12%" align="center" class="title">题目类型</td>
                      <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="6%" align="center" class="title">引用数</td>
                      <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="10%" align="center" class="title">分值</td>
                       <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="12%" align="center" class="title">题目描述</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="10%" align="center" class="title">操作</td>
                    </tr>                    
                  </table>
                </td>
                </tr>  
                    <%
						int totalItems = testManage.getStoreQuestionsNum(loreDirId );
						//----------分页开始---------------
						String spagesize = (String) session.getValue("pagesize");
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
						String link = "&id=" + id + "&loreDirId=" + loreDirId + "&title="+java.net.URLEncoder.encode(titleVal,"UTF-8");
						//----------分页结束---------------
						List storeQuestionList = testManage.getTitleInfo(pageover, null, titleVal, null, null, null, null, null, null, 
									null, null, null, null, null, null, null,loreDirId);
						
						for(int i=0;i<storeQuestionList.size();i++) {
							String edit_url = "";		
							String url = "store_question_info.jsp";		 
							StoreQuestion question = (StoreQuestion)storeQuestionList.get(i);
							String questionId = fixnull(question.getId());
							String purpose=fixnull(question.getPurpose());
							String lore=fixnull(question.getLore());
							String title = fixnull(question.getTitle());
							String creatuser =fixnull( question.getCreatUser());//此处获取的为所属知识点名称
							String creatdate = fixnull(question.getCreatDate());
							String referencescore = fixnull(question.getReferenceScore());
							String remark =fixnull(question.getRemark());
							String stRemark =remark;
							if(remark.equals("")|| remark ==null){
								remark ="--";
								stRemark="--";
							}else{
								if(remark.length()>10){
								stRemark = remark.substring(0,10)+"...";
								}
							}
							if(question.getQuestionCore()!=null&&question.getQuestionCore().contains("</body>")){
								int length=question.getQuestionCore().indexOf("</body>");
							 questionDetail=fixnull(question.getQuestionCore().substring(0,length).replaceAll("<.*?>",""));
							}
								
							String questionCore=fixnull(question.getQuestionCore().substring(0,30).replaceAll("<.*?>",""));
						
							String num=fixnull(question.getNum());
							type = fixnull(question.getType());
							String type_str = TestQuestionType.typeShow(question.getType());
							//out.print(type);
							if(type.equals(TestQuestionType.DANXUAN))
								edit_url = "store_question_single_edit.jsp";
							if(type.equals(TestQuestionType.DUOXUAN))
								edit_url = "store_question_multi_edit.jsp";
							if(type.equals(TestQuestionType.PANDUAN))
								edit_url = "store_question_judge_edit.jsp";
							if(type.equals(TestQuestionType.TIANKONG))
								edit_url = "store_question_blank_edit.jsp";
							if(type.equals(TestQuestionType.WENDA))
								edit_url = "store_question_answer_edit.jsp";
							if(type.equals(TestQuestionType.YUEDU)) {
								edit_url = "store_question_comprehension_edit.jsp";
								url = "store_question_comprehension_info.jsp";
							}
					%>
                    <tr>
                      <td align="center" background="images/tablebian.gif">
						<table width="99%" border="0" cellspacing="0" cellpadding="0" class="mc1">
                          <tr> 
                        
                          	<td class='select' width="5%" style='text-align: center; '> 
                          	<input type='checkbox' class='checkbox' name='idaaa' value='<%=questionId%>' id=''>
                          	<input type="hidden" value="<%=lore %>">
                          	 </td>
                            <input type="hidden" id= "type" name="type" value=<%=type%>>
                            <input type="hidden" id= "title" name="title" value=<%=title%>>
                             <td width="6%" align="center"  class="td1"><%=title%></td>
                            <td width="25%"  align="center" class="td1" ><a href="<%=url%>?id=<%=questionId%>"  title="<%=questionDetail %>" target=_blank><%=questionDetail%></a></td>
                            <!--  
                             <td width="10%" align="center"  class="td1">
                             <%=creatuser %>
                             </td>
                             --> 
                            <td width="13%" align="center"  class="td1"><%=creatdate%></td>
                            <td width="13%" align="center"  class="td1"><%=type_str%></td>
                            <td width="7%" align="center"  class="td1"><%=num%></td>
                            <td width="11%" align="center"  class="td1"><%=referencescore%></td>
                            <td width="13%" align="center"  class="td1" title="<%=remark%>"><%=stRemark%></td>
                            <td width="10%" align="center"  class="td1"><A href="<%=edit_url%>?id=<%=questionId%>&loreId=<%=lore%>&loreDirId=<%=loreDirId%>&titleVal=<%=java.net.URLEncoder.encode(titleVal,"UTF-8") %>&pageInt=<%=pageInt %>">编辑</A>&nbsp;
                            <A href="javascript:if(confirm('确定删除吗？')) window.location.href='store_question_delexe.jsp?id=<%=questionId%>&loreId=<%=id%>&loreDirId=<%=loreDirId%>&pageInt=<%=pageInt %>&titleVal=<%=titleVal%>'">删除</A></td>
                          </tr>
                         
                        </table>
                      </td>
                    </tr>
                    <%
                    	}
                    %> 
                   
                    <tr>
                          <td align="center" background="images/tablebian.gif"><a href="#" onclick="javascript:deleteaa()">批量删除</a>
                          <a href="store_question_delall.jsp?loreDirId=<%=loreDirId %>">删除全部</a>
                          <a href="#" onclick="javascript:download()">下载选中</a>
                          <a href="store_question_download_all.jsp?loreDirId=<%=loreDirId %>">下载全部</a>
                          <a href="lore_dir_list.jsp" >&nbsp;&nbsp;返&nbsp;&nbsp;回</a>
                          </td>
                          </tr>
                           <tr>
                      <td><img src="images/tablebottom.gif" width="812" height="4"></td>
                    </tr>
                  </table><br/>
                </td>
              </tr>
          </form>
              <tr>
                <td align="center">
<table width="806" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="images/bottomtop.gif" width="806" height="7"></td>
                    </tr>
                    <tr>
                      <td background="images/bottom02.gif">
                      	<!--<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr> 
                            <td align="center" valign="middle" class="mc1">页次:1/1页 
                              共:3条 </td>
                            <td align="center" valign="middle" class="mc1"> 上一页 
                              下一页 </td>
                            <td align="center" valign="middle" class="mc1">转到第 页</td>
                            <td valign="m??????ā8?iddle" class="mc1"><a href="#"><img src="images/go.gif" width="52" height="22" border="0"></a></td>
                            <td align="center" valign="middle" class="mc1">当前每页显示 
                              条</td>
                          </tr>
                        </table>
                       -->
                       <%@ include file="../pub/dividepage1.jsp" %>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="images/bottom03.gif" width="806" height="7"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table></td>
        </tr>
      </table>
<script>


    function   setdiv(divname)   {   
       var   obj   =   document.getElementById(divname);
       if(document.getElementById('type') != null) {
	       var  type = document.getElementById('type').value;
	       if(type=='<%=TestQuestionType.YUEDU%>'){
	       
	       }else{
	       		   obj.style.display='block';
	       }
       }
  	}   
   setdiv('link');
</script>
</body>

<%
	} catch (Exception e) {
		out.print(e.toString());
	}
%>

</html>
